package org.forkjoin.scrat.apikit.tool.impl;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.core.Ignore;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.MessageAnalyse;
import org.forkjoin.scrat.apikit.tool.info.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassPathMessageAnalyse implements MessageAnalyse {
    private static final Logger log = LoggerFactory.getLogger(ClassPathMessageAnalyse.class);

    private Context context;
    private Set<ClassInfo> messagesSet = new HashSet<>();
    private ArrayDeque<ClassInfo> analysDeque = new ArrayDeque<>();
    private List<MessageInfo> messageInfos = new ArrayList<>();
    private HashMap<ClassInfo, MessageInfo> messageMap = new HashMap<>();

    private Set<TypeInfo> enumTypes = new LinkedHashSet<>();

    private Set<Class> TYPE_BACK = ImmutableSet.of(
            Class.class,
            Object.class, void.class, Void.class, Mono.class, Flux.class,
            FormFieldPart.class, FilePart.class, Part.class
    );
    private Set<String> TYPE_BACK_FULLNAME = TYPE_BACK.stream().map(Class::getName).collect(Collectors.toSet());

    @Override
    public void analyse(Context context) {
        this.context = context;
        List<ClassInfo> list = Flux
                .fromIterable(context.getApis().getValues())
                .flatMapIterable(ApiInfo::getMethodInfos)
                .flatMapIterable(m -> {
                    List<TypeInfo> types = new ArrayList<>();
                    types.add(m.getResultDataType());
                    m.getParams().forEach(p -> types.add(p.getTypeInfo()));
                    return types;
                })
                .flatMapIterable(this::findAllByTypeArguments)
                .filter(this::filterTypes)
                .map(typeInfo -> new ClassInfo(typeInfo.getPackageName(), typeInfo.getName()))
                .distinct()
                .collectList()
                .block();

        messagesSet.addAll(list);
        analysDeque.addAll(list);

        handler();

        messageMap.entrySet().forEach(e -> {
            context.addMessage(e.getKey(), e.getValue());
        });
    }


    private boolean filterTypes(TypeInfo typeInfo) {
        if (typeInfo.isEnum()) {
            enumTypes.add(typeInfo);
        }
        if (TYPE_BACK_FULLNAME.contains(typeInfo.getFullName())) {
            return false;
        }

        return typeInfo.getType().equals(TypeInfo.Type.OTHER)
                && (!typeInfo.isCollection())
                && (!typeInfo.isGeneric())
                && (!typeInfo.isObject())
                && (!typeInfo.isEnum())
                && (!typeInfo.isMap());
    }

    public Set<TypeInfo> getEnumTypes() {
        return enumTypes;
    }

    public void handler() {
        ClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            MessageInfo messageInfo = analyseMessage(classInfo);
            add(classInfo, messageInfo);


            List<ClassInfo> offspringList = Flux.concat(Flux
                    .fromIterable(messageInfo.getProperties())
                    .map(FieldInfo::getTypeInfo), Mono.justOrEmpty(messageInfo.getSuperType()))
                    .flatMapIterable(this::findAllByTypeArguments)
                    .filter(this::filterTypes)
                    .map(typeInfo -> new ClassInfo(typeInfo.getPackageName(), typeInfo.getName()))
                    .distinct()
                    .collectList()
                    .block();


            offspringList.forEach(ci -> {
                if (messagesSet.add(ci)) {
                    analysDeque.addFirst(ci);
                }
            });
        }
    }

    private void add(ClassInfo classInfo, MessageInfo messageInfo) {
        messageMap.put(classInfo, messageInfo);
        messageInfos.add(messageInfo);
    }

    private MessageInfo analyseMessage(ClassInfo classInfo) {
        try {
            Class cls = Class.forName(classInfo.getPackageName() + "." + classInfo.getName());

            Optional<JdtClassWappper> jdtClassWappperOpt = JdtClassWappper.check(cls, context.getPath());

            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setPackageName(classInfo.getPackageName());
            messageInfo.setName(classInfo.getName());
            messageInfo.setMessageClass(cls);
            jdtClassWappperOpt.ifPresent(j -> messageInfo.setComment(j.getClassComment()));

            Type genericSuperclass = cls.getGenericSuperclass();
            if (genericSuperclass != null && !genericSuperclass.equals(Object.class)) {
                TypeInfo superTypeInfo = TypeInfo.form(genericSuperclass);
                messageInfo.setSuperType(superTypeInfo);
            }

            TypeVariable[] typeParameters = cls.getTypeParameters();
            for (TypeVariable typeParameter : typeParameters) {
                messageInfo.addTypeParameter(typeParameter.getName());
            }

            Map<String, Field> fieldMap = Stream.of(cls.getFields()).collect(Collectors.toMap(Field::getName, t -> t));

            Set<String> nameSet = new HashSet<>();
            for (Method method : cls.getMethods()) {
                if (Modifier.isPublic(method.getModifiers())
                        && !isBackType(method)
                        && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                ) {
                    try {
                        TypeInfo typeInfo = TypeInfo.form(method.getGenericReturnType());
                        PropertyDescriptor propertyDescriptor = BeanUtils.findPropertyForMethod(method);

                        if (propertyDescriptor != null) {
                            String name = propertyDescriptor.getName();

                            Field field = fieldMap.get(name);


                            Ignore annotation = AnnotationUtils.getAnnotation(method, Ignore.class);
                            if (annotation != null) {
                                continue;
                            }
                            if (field != null) {
                                annotation = AnnotationUtils.getAnnotation(field, Ignore.class);
                                if (annotation != null) {
                                    continue;
                                }
                            }

                            if (typeInfo == null) {
                                throw new AnalyseException("类型解析失败!错误的字段:" + method.getGenericReturnType());
                            }
                            Class<?> declaringClass = propertyDescriptor.getReadMethod().getDeclaringClass();

                            if (!nameSet.contains(name)) {
                                PropertyInfo propertyInfo = new PropertyInfo(name, typeInfo, !cls.equals(declaringClass));

                                jdtClassWappperOpt.ifPresent(j -> {
                                    JavadocInfo fieldComment = j.getFieldComment(name);
                                    if (fieldComment == null) {
                                        fieldComment = j.getMethodComment(method.getName());
                                        if (fieldComment == null) {
                                            Method commentMethod = propertyDescriptor.getWriteMethod();
                                            if (commentMethod == null) {
                                                commentMethod = propertyDescriptor.getReadMethod();
                                            }
                                            if (commentMethod != null) {
                                                fieldComment = j.getMethodComment(commentMethod.getName());
                                            }
                                        }
                                    }
                                    if (fieldComment != null) {
                                        propertyInfo.setComment(fieldComment);
                                    }
                                });
                                messageInfo.add(propertyInfo);
                                nameSet.add(name);
                            }
                        }
                    } catch (AnalyseException ex) {
                        log.info("错误,忽略属性继续:{}", method);
                    }
                }
            }

            if (jdtClassWappperOpt.isPresent()) {
                jdtClassWappperOpt.get().sort(messageInfo.getProperties());
            } else {
                messageInfo.sortPropertys();
            }
            return messageInfo;
        } catch (Throwable th) {
            log.info("分析message错误,classInfo:{}", classInfo, th);
            throw new RuntimeException(th);
        }
    }

    private boolean isBackType(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            return TYPE_BACK.contains(genericReturnType) || TYPE_BACK.contains(((ParameterizedType) genericReturnType).getRawType());
        }
        return TYPE_BACK.contains(genericReturnType);
    }


    private Iterable<? extends TypeInfo> findAllByTypeArguments(TypeInfo type) {
        List<TypeInfo> types = new ArrayList<>();
        findTypes(type, types);
        return types;
    }

    public void findTypes(TypeInfo type, List<TypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }
}

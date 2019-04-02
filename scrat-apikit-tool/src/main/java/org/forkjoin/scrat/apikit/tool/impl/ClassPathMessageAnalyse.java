package org.forkjoin.scrat.apikit.tool.impl;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.MessageAnalyse;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.ClassInfo;
import org.forkjoin.scrat.apikit.tool.info.FieldInfo;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.info.PropertyInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Flux;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ClassPathMessageAnalyse implements MessageAnalyse {
    private static final Logger log = LoggerFactory.getLogger(ClassPathMessageAnalyse.class);

    private Context context;
    private Set<ClassInfo> messagesSet = new HashSet<>();
    private ArrayDeque<ClassInfo> analysDeque = new ArrayDeque<>();
    private List<MessageInfo> messageInfos = new ArrayList<>();
    private HashMap<ClassInfo, MessageInfo> messageMap = new HashMap<>();
    private Set<Class> TYPE_BACK = ImmutableSet.of(
            Class.class, Object.class, void.class, Void.class
    );

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
                .flatMapIterable(type -> {
                    List<TypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types;
                })
                .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .filter(typeInfo -> !typeInfo.isObject())
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

    public void handler() {
        ClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            MessageInfo messageInfo = analyseMessage(classInfo);
            add(classInfo, messageInfo);

            List<ClassInfo> offspringList = Flux
                    .fromIterable(messageInfo.getProperties())
                    .map(FieldInfo::getTypeInfo)
                    .flatMapIterable(type -> {
                        List<TypeInfo> types = new ArrayList<>();
                        findTypes(type, types);
                        if (messageInfo.getSuperType() != null) {
                            findTypes(messageInfo.getSuperType(), types);
                        }
                        return types;
                    })
                    .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                    .filter(typeInfo -> !typeInfo.isCollection())
                    .filter(typeInfo -> !typeInfo.isGeneric())
                    .filter(typeInfo -> !typeInfo.isObject())
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
            jdtClassWappperOpt.ifPresent(j-> messageInfo.setComment(j.getClassComment()));

            Type genericSuperclass = cls.getGenericSuperclass();
            if (genericSuperclass != null && !genericSuperclass.equals(Object.class)) {
                TypeInfo superTypeInfo = TypeInfo.form(genericSuperclass);
                messageInfo.setSuperType(superTypeInfo);
            }

            TypeVariable[] typeParameters = cls.getTypeParameters();
            for (TypeVariable typeParameter : typeParameters) {
                messageInfo.addTypeParameter(typeParameter.getName());
            }

            Set<String> nameSet = new HashSet<>();
            for (Method method : cls.getMethods()) {
                if (Modifier.isPublic(method.getModifiers())
                        && !TYPE_BACK.contains(method.getGenericReturnType())
                        && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                ) {
                    try{
                        TypeInfo typeInfo = TypeInfo.form(method.getGenericReturnType());
                        PropertyDescriptor propertyDescriptor = BeanUtils.findPropertyForMethod(method);
                        if (propertyDescriptor != null) {
                            if (typeInfo == null) {
                                throw new AnalyseException("类型解析失败!错误的字段:" + method.getGenericReturnType());
                            }

                            String name = propertyDescriptor.getName();
                            if (!nameSet.contains(name)) {
                                PropertyInfo propertyInfo = new PropertyInfo(name, typeInfo);

                                jdtClassWappperOpt
                                        .ifPresent(j-> propertyInfo.setComment(j.getFieldComment(name)));
                                messageInfo.add(propertyInfo);
                                nameSet.add(name);
                            }
                        }
                    }catch (AnalyseException ex){
                        log.info("错误,忽略属性继续:{}",method,ex);
                    }
                }
            }

            messageInfo.sortPropertys();
            return messageInfo;
        } catch (Throwable th) {
            log.info("分析message错误,classInfo:{}", classInfo, th);
            throw new RuntimeException(th);
        }
    }


    public void findTypes(TypeInfo type, List<TypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }
}

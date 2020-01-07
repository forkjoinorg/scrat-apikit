package org.forkjoin.scrat.apikit.tool.impl;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.core.Ignore;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.EnumAnalyse;
import org.forkjoin.scrat.apikit.tool.info.ClassInfo;
import org.forkjoin.scrat.apikit.tool.info.EnumElementInfo;
import org.forkjoin.scrat.apikit.tool.info.EnumInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassPathEnumAnalyse implements EnumAnalyse {
    private static final Logger log = LoggerFactory.getLogger(ClassPathEnumAnalyse.class);

    private Context context;
    private Set<ClassInfo> classInfoSet = new HashSet<>();
    private ArrayDeque<ClassInfo> analysDeque = new ArrayDeque<>();
    private List<EnumInfo> enumInfos = new ArrayList<>();
    private HashMap<ClassInfo, EnumInfo> enumMap = new HashMap<>();
    private Set<Class> TYPE_BACK = ImmutableSet.of(
            Class.class, Object.class, void.class, Void.class
    );

    @Override
    public void analyse(Context context, Set<TypeInfo> enumTypes) {
        this.context = context;
        List<ClassInfo> list = Flux
                .fromIterable(enumTypes)
                .map(typeInfo -> new ClassInfo(typeInfo.getPackageName(), typeInfo.getName()))
                .distinct()
                .collectList()
                .block();

        classInfoSet.addAll(list);
        analysDeque.addAll(list);

        handler();

        enumMap.entrySet().forEach(e -> {
            context.addMessage(e.getKey(), e.getValue());
        });
    }

    public void handler() {
        ClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            EnumInfo enumInfo = analyseEnum(classInfo);
            add(classInfo, enumInfo);
        }
    }

    private void add(ClassInfo classInfo, EnumInfo enumInfo) {
        enumMap.put(classInfo, enumInfo);
        enumInfos.add(enumInfo);
    }

    private EnumInfo analyseEnum(ClassInfo classInfo) {
        try {
            Class<? extends Enum> cls = (Class<? extends Enum>) Class.forName(classInfo.getPackageName() + "." + classInfo.getName());

            Optional<JdtClassWappper> jdtClassWappperOpt = JdtClassWappper.check(cls, context.getPath());

            EnumInfo enumInfo = new EnumInfo();
            enumInfo.setPackageName(classInfo.getPackageName());
            enumInfo.setName(classInfo.getName());
            enumInfo.setEnumClass(cls);
            jdtClassWappperOpt.ifPresent(j -> enumInfo.setComment(j.getClassComment()));

            Enum[] enumConstants = cls.getEnumConstants();

            Map<String, Field> fieldMap = Stream.of(cls.getFields())
                    .filter(Field::isEnumConstant)
                    .collect(Collectors.toMap(Field::getName, t -> t));

            List<EnumElementInfo> collect = Stream.of(enumConstants)
                    .filter(e -> {
                        Field field = fieldMap.get(e.name());
                        Ignore annotation = AnnotationUtils.getAnnotation(field, Ignore.class);
                        return annotation == null;
                    })
                    .map(e -> {
                        EnumElementInfo enumElementInfo = new EnumElementInfo(e.name(), e.ordinal());
                        jdtClassWappperOpt.map(p -> p.getEnumElementComment(enumElementInfo.getName())).ifPresent(enumElementInfo::setJavadocInfo);
                        return enumElementInfo;
                    })
                    .sorted(Comparator.comparingInt(EnumElementInfo::getOrdinal))
                    .collect(Collectors.toList());

            enumInfo.setEnumElementInfos(collect);


//            cls.isEnum()
//
//
//            Set<String> nameSet = new HashSet<>();
//            for (Method method : cls.getMethods()) {
//                if (Modifier.isPublic(method.getModifiers())
//                        && !isBackType(method)
//                        && (method.getName().startsWith("get") || method.getName().startsWith("is"))
//                ) {
//                    try {
//                        TypeInfo typeInfo = TypeInfo.form(method.getGenericReturnType());
//                        PropertyDescriptor propertyDescriptor = BeanUtils.findPropertyForMethod(method);
//                        if (propertyDescriptor != null) {
//
//                            if (typeInfo == null) {
//                                throw new AnalyseException("类型解析失败!错误的字段:" + method.getGenericReturnType());
//                            }
//                            Class<?> declaringClass = propertyDescriptor.getReadMethod().getDeclaringClass();
//                            String name = propertyDescriptor.getName();
//                            if (!nameSet.contains(name)) {
//                                PropertyInfo propertyInfo = new PropertyInfo(name, typeInfo, !cls.equals(declaringClass));
//
//                                enumInfo.add(propertyInfo);
//                                nameSet.add(name);
//                            }
//                        }
//                    } catch (AnalyseException ex) {
//                        log.info("错误,忽略属性继续:{}", method, ex);
//                    }
//                }
//            }
//
//            enumInfo.sortPropertys();
            return enumInfo;
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


    public void findTypes(TypeInfo type, List<TypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }
}

package org.forkjoin.scrat.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/6/15.
 */
public class JavaWrapper<T extends ModuleInfo> extends BuilderWrapper<T> {
    public JavaWrapper(Context context, T moduleInfo, String rootPackage) {
        super(context, moduleInfo, rootPackage);


        addClassMap(new ClassInfo(FilePart.class), new ClassInfo(HttpEntity.class));
        addClassMap(new ClassInfo(FormFieldPart.class), new ClassInfo(HttpEntity.class));
    }

    private Set<Class> TYPE_BACK = ImmutableSet.of(
            Mono.class, Flux.class,
            FormFieldPart.class, FilePart.class, Part.class, HttpEntity.class
    );

    private Set<ClassInfo> TYPE_BACK_FULLNAME = TYPE_BACK.stream()
            .map(c -> new ClassInfo(c.getPackage().getName(), c.getSimpleName()))
            .collect(Collectors.toSet());


    @Override
    public void init() {
        super.init();
    }

    protected Iterable<? extends String> toImports(ClassInfo classInfo) {
        /**
         * getMessageWrapper 返回结果已经过滤了，所以先处理非过滤的
         */
        BuilderWrapper<MessageInfo> w = context.getMessageWrapper(classInfo.getFullName());
        if (w != null) {
            if (!w.getDistPackage().equals(getDistPackage())) {
                return Arrays.asList("import ", w.getDistPack(), ".", w.getDistName(), ";\n");
            }
        }

        BuilderWrapper<EnumInfo> e = context.getEnumWrapper(classInfo.getFullName());
        if (e != null) {
            if (!e.getDistPackage().equals(getDistPackage())) {
                return Arrays.asList("import ", e.getDistPack(), ".", e.getDistName(), ";\n");
            }
        }

        if (CollectionUtils.isNotEmpty(this.filterList)) {
            for (ClassInfo i : filterList) {
                if (classInfo.equals(i)) {
                    return Arrays.asList("import ", i.getPackageName(), ".", i.getName(), ";\n");
                }
            }
        }

        if (TYPE_BACK_FULLNAME.contains(classInfo)) {
            return Arrays.asList("import ", classInfo.getPackageName(), ".", classInfo.getName(), ";\n");
        }
        return Collections.emptyList();
    }

    protected boolean filterType(TypeInfo typeInfo) {
        return typeInfo.getType().equals(TypeInfo.Type.OTHER) && (!typeInfo.isCollection()) && (!typeInfo.isGeneric());
    }

    public String toJavaTypeStringNotArray(TypeInfo typeInfo) {
        return toJavaTypeString(typeInfo, false, false, false);
    }

    public String toJavaTypeString(TypeInfo typeInfo, boolean isWrap, boolean isArrayList) {
        return toJavaTypeString(typeInfo, isWrap, isArrayList, true);
    }


    public String toJavaTypeString(TypeInfo typeInfo) {
        return toJavaTypeString(typeInfo, true, true, true);
    }

    public String toJavaTypeString(TypeInfo typeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments) {
        return toJavaTypeString(typeInfo, isWrap, isArrayList, isTypeArguments, isArrayList);
    }

    /**
     * @param isChildArrayList 参数类型是否处理数组
     */
    public String toJavaTypeString(TypeInfo typeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments, boolean isChildArrayList) {
        typeInfo = map(typeInfo);
        StringBuilder sb = new StringBuilder();
        TypeInfo.Type type = typeInfo.getType();
        if (type == TypeInfo.Type.BYTE && typeInfo.isArray()) {
            sb.append("byte[]");
        } else if (isArrayList && typeInfo.isArray()) {
            toJavaArrayTypeString(typeInfo, sb, isWrap, true);
            return sb.toString();
        } else if (typeInfo.isOtherType()) {
            sb.append(typeInfo.getName());
        } else if (isWrap) {
            sb.append(toJavaWrapString(type));
        } else {
            sb.append(toJavaString(type));
        }
        List<TypeInfo> typeArguments = typeInfo.getTypeArguments();
        if (!typeArguments.isEmpty() && isTypeArguments) {
            sb.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                TypeInfo typeArgument = typeArguments.get(i);
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(toJavaTypeString(typeArgument, true, isChildArrayList));
            }
            sb.append('>');
        }
        return sb.toString();
    }

    /**
     * 处理数组!
     */
    protected void toJavaArrayTypeString(TypeInfo typeInfo, StringBuilder sb, boolean isWrap, boolean isArrayList) {
        sb.append("java.util.ArrayList");
        sb.append('<');
        sb.append(toJavaTypeString(typeInfo, true, false));
        sb.append('>');
    }


    // @Length(max = 255)
    public String formatAnnotations(List<AnnotationInfo> annotations, String start) {
        StringBuilder sb = new StringBuilder();
        for (AnnotationInfo annotation : annotations) {
            sb.append(start);
            sb.append("@");
            sb.append(toJavaTypeString(annotation.getTypeInfo(), true, true));

            List<String> args = annotation.getArgs();
            if (!args.isEmpty()) {
                sb.append("(");
                for (int i = 0; i < args.size(); i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    String arg = args.get(i);
                    sb.append(arg);
                }
                sb.append(")");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

//    @Override
//    public void initImport() {
////        ImportsInfo imports = moduleInfo.getImports();
////
////        for (Import importItem : imports.getImports()) {
////            if (importItem.isInside()) {
////                String fullName = getPack(importItem.getPackageName()) + "." + importItem.getName();
////                if (!exclude(fullName)) {
////                    addImport(fullName);
////                }
////            } else {
////                String fullName = importItem.getFullName();
////                if (importItem.isOnDemand()) {
////                    fullName += ".*";
////                }
////                if (!exclude(fullName)) {
////                    addImport(fullName);
////                }
////            }
////        }
//    }

    private static final ImmutableMap<TypeInfo.Type, Class> typeMap
            = ImmutableMap.<TypeInfo.Type, Class>builder()
            .put(TypeInfo.Type.VOID, void.class)
            .put(TypeInfo.Type.BOOLEAN, boolean.class)
            .put(TypeInfo.Type.BYTE, byte.class)
            .put(TypeInfo.Type.SHORT, short.class)
            .put(TypeInfo.Type.INT, int.class)
            .put(TypeInfo.Type.LONG, long.class)
            .put(TypeInfo.Type.FLOAT, float.class)
            .put(TypeInfo.Type.DOUBLE, double.class)
            .put(TypeInfo.Type.DATE, Date.class)
            .put(TypeInfo.Type.STRING, String.class)
            .build();

    private static final ImmutableMap<TypeInfo.Type, Class> typeWrapMap
            = ImmutableMap.<TypeInfo.Type, Class>builder()
            .put(TypeInfo.Type.VOID, Void.class)
            .put(TypeInfo.Type.BOOLEAN, Boolean.class)
            .put(TypeInfo.Type.BYTE, Byte.class)
            .put(TypeInfo.Type.SHORT, Short.class)
            .put(TypeInfo.Type.INT, Integer.class)
            .put(TypeInfo.Type.LONG, Long.class)
            .put(TypeInfo.Type.FLOAT, Float.class)
            .put(TypeInfo.Type.DOUBLE, Double.class)
            .put(TypeInfo.Type.DATE, Date.class)
            .put(TypeInfo.Type.STRING, String.class)
            .build();

    public static String toJavaWrapString(TypeInfo.Type type) {
        return typeWrapMap.get(type).getSimpleName();
    }

    public static String toJavaString(TypeInfo.Type type) {
        return typeMap.get(type).getSimpleName();
    }
}

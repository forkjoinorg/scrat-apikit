package org.forkjoin.scrat.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.ClassInfo;
import org.forkjoin.scrat.apikit.tool.info.ModuleInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
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
public class JSWrapper<T extends ModuleInfo> extends BuilderWrapper<T> {
    public enum Type {
        TypeScript
    }

    private Type type = Type.TypeScript;

    private Set<Class> TYPE_BACK = ImmutableSet.of(
            Mono.class, Flux.class,
            FormFieldPart.class, FilePart.class, Part.class, HttpEntity.class
    );

    private Set<ClassInfo> TYPE_BACK_FULLNAME = TYPE_BACK.stream()
            .map(c -> new ClassInfo(c.getPackage().getName(), c.getSimpleName()))
            .collect(Collectors.toSet());


    public JSWrapper(Context context, T moduleInfo, String rootPackage) {
        super(context, moduleInfo, rootPackage);

        addClassMap(new ClassInfo(FilePart.class), new ClassInfo(null, "Blob"));
        addClassMap(new ClassInfo(FormFieldPart.class), new ClassInfo(null, "string"));
        addClassMap(new ClassInfo("org.bson.types", "ObjectId"), new ClassInfo(null, "string"));
    }

    @Override
    public void init() {
        super.init();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    protected boolean filterType(TypeInfo typeInfo) {
        return typeInfo.getType().equals(TypeInfo.Type.OTHER)
                && (!typeInfo.isCollection())
                && (!typeInfo.isGeneric())
                && (!typeInfo.isMap());
    }

    protected Iterable<? extends String> toImports(ClassInfo classInfo) {
        /**
         * String distPackage = getDistPackage();
         *                     String proTypeName = r.getDistName();
         *                     if (r.getDistPackage().equals(distPackage)) {
         *                         sb.append("import ")
         *                                 .append(proTypeName)
         *                                 .append(" from './").append(proTypeName).append("'\n");
         *                     } else {
         *                         int level = distPackage.split("\\.").length;
         *                         sb.append("import ")
         *                                 .append(proTypeName)
         *                                 .append(" from '")
         *                                 .append(StringUtils.repeat("../", level))
         *                                 .append(r.getDistPackage())
         *                                 .append("/")
         *                                 .append(proTypeName)
         *                                 .append("'\n");
         *                     }
         */
        BuilderWrapper<? extends ModuleInfo> w = context.getMessageWrapper(classInfo.getFullName());
        if (w == null) {
            w = context.getEnumWrapper(classInfo.getFullName());
        }
        if (w != null) {
            String sourceDistPackage = getDistPackage();
            String distPackage = w.getDistPackage();

            String distName = w.getDistName();

            int level = StringUtils.isEmpty(sourceDistPackage) ? 0 : sourceDistPackage.split("\\.").length;

            boolean isSelfPack = Objects.equals(w.getDistPackage(), getDistPackage());

            if (isSelfPack || level == 0) {
                if (StringUtils.isNotEmpty(distPackage) && !isSelfPack) {
                    return Arrays.asList("import ", distName, " from './" + distPackage.replace(".", "/"), "/", distName, "';\n");
                } else {
                    return Arrays.asList("import ", distName, " from './", distName, "';\n");
                }
            } else {
                return Arrays.asList("import ", distName, " from '",
                        StringUtils.repeat("../", level),
                        distPackage.replace(".", "/"),
                        "/",
                        distName,
                        "';\n"
                );
            }
        }

//        if (CollectionUtils.isNotEmpty(this.filterList)) {
//            for (ClassInfo i : filterList) {
//                if (classInfo.equals(i)) {
//                    return Arrays.asList("import ", i.getPackageName(), ".", i.getName(), ";\n");
//                }
//            }
//        }
//
//        if (TYPE_BACK_FULLNAME.contains(classInfo)) {
//            return Arrays.asList("import ", classInfo.getPackageName(), ".", classInfo.getName(), ";\n");
//        }
        return Collections.emptyList();
    }
//
//    public String toTypeString(TypeInfo typeInfo) {
//        return toTypeString(typeInfo, true);
//    }

//    protected void toArrayTypeString(TypeInfo typeInfo, StringBuilder sb) {
//        sb.append(toTypeString(typeInfo, false));
//        sb.append("[]");
//    }

    public String toTypeString(TypeInfo typeInfo) {
        typeInfo = map(typeInfo);

        if ((typeInfo.isCollection())) {
            List<TypeInfo> typeArguments = typeInfo.getTypeArguments();
            if (typeArguments.size() != 1) {
                throw new RuntimeException("List，必须有泛型参数");
            }
            TypeInfo typeInfo1 = typeArguments.get(0).clone();
            typeInfo1.setArray(true);
            return toTypeString(typeInfo1);
        }
        StringBuilder sb = new StringBuilder();
        TypeInfo.Type type = typeInfo.getType();
        if (typeInfo.isMap()) {
//
            List<TypeInfo> typeArguments = typeInfo.getTypeArguments();
            if (typeArguments.size() != 2) {
                throw new RuntimeException("Map，必须有2个泛型参数");
            }
            TypeInfo keyTypeInfo = typeInfo.getTypeArguments().get(0);
            TypeInfo valueTypeInfo = typeInfo.getTypeArguments().get(1);
            String key;
            if (keyTypeInfo.isEnum()) {
                key = "string";
            } else {
                key = toTypeString(keyTypeInfo);
            }
            sb.append("{[key: ").append(key).append("]: ").append(toTypeString(valueTypeInfo)).append("}");
            return sb.toString();
        } else if (type == TypeInfo.Type.BYTE && typeInfo.isArray()) {
            sb.append("string");
        } else if (typeInfo.isOtherType()) {
            sb.append(typeInfo.getName());
        } else {
            sb.append(toJavaScriptString(type));
        }
        List<TypeInfo> typeArguments = typeInfo.getTypeArguments();
        if (!typeArguments.isEmpty()) {
            sb.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                TypeInfo typeArgument = typeArguments.get(i);
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(toTypeString(typeArgument));
            }
            sb.append('>');
        } else if (typeInfo.getTypeParametersSize() > 0) {
            sb.append('<');
            for (int i = 0; i < typeInfo.getTypeParametersSize(); i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append("any");
            }
            sb.append('>');
        }
        if (type == TypeInfo.Type.BYTE && typeInfo.isArray()) {
            return sb.toString();
        }
        if (typeInfo.isArray()) {
            sb.append("[]");
        }
        return sb.toString();
    }

    private static final ImmutableMap<TypeInfo.Type, String> typeMap
            = ImmutableMap.<TypeInfo.Type, String>builder()
            .put(TypeInfo.Type.VOID, "void")
            .put(TypeInfo.Type.BOOLEAN, "boolean")
            .put(TypeInfo.Type.BYTE, "number")
            .put(TypeInfo.Type.SHORT, "number")
            .put(TypeInfo.Type.INT, "number")
            .put(TypeInfo.Type.LONG, "number")
            .put(TypeInfo.Type.FLOAT, "number")
            .put(TypeInfo.Type.DOUBLE, "number")
            .put(TypeInfo.Type.DATE, "Date")
            .put(TypeInfo.Type.CHAR, "string")
            .put(TypeInfo.Type.STRING, "string")
            .build();


    public String toJavaScriptString(TypeInfo.Type type) {
        return typeMap.get(type);
    }

}

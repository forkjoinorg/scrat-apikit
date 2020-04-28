package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.Utils;
import org.forkjoin.scrat.apikit.tool.codebuilder.JavaEncodeCodeBuilder;
import org.forkjoin.scrat.apikit.tool.info.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JavaMessageWrapper extends JavaWrapper<MessageInfo> {
    private boolean isAnnotations = false;


    public JavaMessageWrapper(Context context, MessageInfo messageInfo, String rootPackage) {
        super(context, messageInfo, rootPackage);
    }

    @Override
    public String formatAnnotations(List<AnnotationInfo> annotations, String start) {
        return null;
    }

    @Override
    public void init() {

    }

    public String getImports() {
        return Flux
                .fromIterable(moduleInfo.getProperties())
                .map(FieldInfo::getTypeInfo)
                .flatMapIterable(type -> {
                    List<TypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    if (moduleInfo.getSuperType() != null) {
                        findTypes(moduleInfo.getSuperType(), types);
                    }
                    return types;
                })
                .filter(this::filterType)
                .map(ClassInfo::new)
                .distinct()
                .sort(Comparator.naturalOrder())
                .flatMapIterable(this::toImports)
                .collect(Collectors.joining()).block();
    }

    public String getSuperInfo() {
        TypeInfo superType = moduleInfo.getSuperType();
        if (superType != null) {
            return "extends " + toJavaTypeString(superType, false, true);
        } else {
            return "";
        }
    }

    public boolean isHasSuper() {
        return moduleInfo.getSuperType() != null;
    }

    public String typeParameters() {
        List<String> typeParameters = moduleInfo.getTypeParameters();
        if (CollectionUtils.isNotEmpty(typeParameters)) {
            StringBuilder sb = new StringBuilder("<");
            for (String typeParameter : typeParameters) {
                if (sb.length() > 1) {
                    sb.append(",");
                }
                sb.append(typeParameter).append("");
            }
            sb.append(">");
            return sb.toString();
        } else {
            return "";
        }
    }

    public String getTypeParametersField(String start) {
        List<String> typeParameters = moduleInfo.getTypeParameters();
        StringBuilder sb = new StringBuilder();
        for (String typeParameter : typeParameters) {
            sb.append(start);
            sb.append("private Class<").append(typeParameter).append(">");
            sb.append(" ");
            sb.append(typeParameter.toLowerCase()).append("Cls;\n");
        }
        return sb.toString();
    }

    public String getTypeParametersAssign(String start) {
        List<String> typeParameters = moduleInfo.getTypeParameters();
        StringBuilder sb = new StringBuilder();
        for (String typeParameter : typeParameters) {
            sb.append(start);
            String typeParameterLowerCase = typeParameter.toLowerCase();
            sb.append("this.").append(typeParameterLowerCase).append("Cls = ");
            sb.append(" ");
            sb.append(typeParameterLowerCase).append("Cls;\n");
        }
        return sb.toString();
    }

    public String getTypeParametersConstructorString() {
        List<String> typeParameters = moduleInfo.getTypeParameters();
        StringBuilder sb = new StringBuilder();
        for (String typeParameter : moduleInfo.getTypeParameters()) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append("Class<").append(typeParameter).append(">");
            sb.append(" ");
            sb.append(typeParameter.toLowerCase()).append("Cls");
        }
        return sb.toString();
    }

    public String getToString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\"');
        sb.append(moduleInfo.getName());
        sb.append(" [");

        for (PropertyInfo attr : moduleInfo.getProperties()) {
            if (attr.isSuperProperty()) {
                continue;
            }
            sb.append(attr.getName());
            if (attr.getTypeInfo().isArrayOrCollection()) {

                if (attr.getTypeInfo().isArray()) {
                    sb.append("=length:\" + ");

                    sb.append("(").append(attr.getName())
                            .append(" == null?-1:")
                            .append(attr.getName())
                            .append(".length)");
                } else {
                    sb.append("=size:\" + ");

                    sb.append("(").append(attr.getName())
                            .append(" == null?-1:")
                            .append(attr.getName())
                            .append(".size())");
                }
                sb.append(" + \"");
            } else {
                sb.append("=\" + ");
                sb.append(attr.getName());
                sb.append(" + \"");
            }

            sb.append(',');
        }
        sb.append(" ]\"");
        return sb.toString();
    }

    public String getConstructorString() {
        StringBuilder sb = new StringBuilder();
        for (PropertyInfo attr : moduleInfo.getProperties()) {
            if (attr.isSuperProperty()) {
                continue;
            }
            if (sb.length() != 0) {
                sb.append(", ");
            }
            TypeInfo typeInfo = attr.getTypeInfo();
            sb.append(toJavaTypeString(typeInfo, true, false));
            sb.append(" ");
            sb.append(attr.getName());
        }
        return sb.toString();
    }


    public String getEncodeCode(String start, String parentName) {
        if (moduleInfo.hasGenerics()) {
            return "throw new RuntimeException(\"不支持泛型\");";
        }
        return new JavaEncodeCodeBuilder(this, start, parentName).build();
    }


    public String toJavaAddName(PropertyInfo propertyInfo) {
        return "add" + Utils.toClassName(propertyInfo.getName());
    }

    public String toJavaSetName(PropertyInfo propertyInfo) {

        return "set" + Utils.toClassName(propertyInfo.getName());
    }

    public String toJavaGetName(PropertyInfo propertyInfo) {
        if (propertyInfo.getTypeInfo().getType().equals(TypeInfo.Type.BOOLEAN)) {
            return "is" + Utils.toClassName(propertyInfo.getName());
        }
        return "get" + Utils.toClassName(propertyInfo.getName());
    }


    public boolean isAnnotations() {
        return isAnnotations;
    }

    public void setAnnotations(boolean annotations) {
        isAnnotations = annotations;
    }


    //    if (id != null) {
//			$list.add(new SimpleImmutableEntry<String, Object>(parent + "id",id));
//		}
//		$list.add(new SimpleImmutableEntry<String, Object>(parent+ "booleanValue", booleanValue));
//
//		if (bytesValue != null) {
//			$list.add(new SimpleImmutableEntry<String, Object>(parent
//					+ "bytesValue", bytesValue));
//		}
//
//		if (booleanValueArray != null && (!booleanValueArray.isEmpty())) {
//			for (int i = 0; i < booleanValueArray.size(); i++) {
//				$list.add(new SimpleImmutableEntry<String, Object>(parent
//						+ "booleanValueArray", booleanValueArray.get(i)));
//			}
//		}
//
//		if (user != null) {
//			user.encode(parent + "user.", $list);
//		}
//
//		if (users != null && (!users.isEmpty())) {
//			for (int i = 0; i < users.size(); i++) {
//				users.get(i).encode(parent + "users" + "[" + i + "].", $list);
//			}
//		}
//
}

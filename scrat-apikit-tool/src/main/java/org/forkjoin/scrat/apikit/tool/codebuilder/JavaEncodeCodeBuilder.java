package org.forkjoin.scrat.apikit.tool.codebuilder;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.info.PropertyInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.forkjoin.scrat.apikit.tool.wrapper.JavaMessageWrapper;

import java.util.Stack;
import java.util.stream.Collectors;

public class JavaEncodeCodeBuilder {
    private StringBuilder sb = new StringBuilder();
    private JavaMessageWrapper javaMessageWrapper;
    private MessageInfo moduleInfo;
    private String start;
    private String parentName;
    private boolean isBasePrefix;
    private MutableInt level = new MutableInt();
    Stack<String> nameStack = new Stack<>();

    public JavaEncodeCodeBuilder(JavaMessageWrapper javaMessageWrapper, String start, String parentName) {
        this.javaMessageWrapper = javaMessageWrapper;
        this.start = start;
        this.parentName = parentName;
        this.moduleInfo = javaMessageWrapper.getModuleInfo();
    }

    public String build() {

        for (PropertyInfo attr : moduleInfo.getProperties()) {
            if (attr.isSuperProperty()) {
                continue;
            }
            sb.append('\n');
            TypeInfo typeInfo = attr.getTypeInfo();


            nameStack.push(parentName);
            nameStack.push("\"" + attr.getName() + "\"");

            encodeCode(typeInfo, attr.getName());

            nameStack.pop();
            nameStack.pop();
        }
        sb.append(start).append("return $list;");
        return sb.toString();
    }

    private void encodeCode(TypeInfo typeInfo, String varName) {
        if (typeInfo.isMap()) {
            innerMapEncodeCode(typeInfo, varName);
        } else if (typeInfo.isCollection() && !typeInfo.isEnum()) {
            innerListEncodeCode(typeInfo, varName);
        } else if (typeInfo.isOtherType() && !typeInfo.isEnum() && typeInfo.isArray()) {
            innerArrayEncodeCode(typeInfo, varName);
        } else {
            innerEncodeCode(typeInfo, varName);
        }
    }

    private void innerEncodeCode(TypeInfo typeInfo, String varName) {
        boolean isOtherType = typeInfo.isOtherType();
        if (isOtherType) {
            sb.append(start).append(" if (").append(varName).append(" != null) {\n");

            getEncodeCodeItem(start + "    ", typeInfo, varName);

            sb.append(start).append("}\n");
        } else {
            getEncodeCodeItem(start + "    ", typeInfo, varName);
        }
    }

    private void innerArrayEncodeCode(TypeInfo typeInfo, String varName) {
        int index = incrementAndGet();

        sb.append(start).append(" if (").append(varName).append(" != null && ").append(varName).append(".length > 0) {\n");
        sb.append(start).append("for (int $i" + index + " = 0; $i" + index + " < ").append(varName).append(".length; $i" + index + "++) {\n");
        TypeInfo itemType = typeInfo.clone();
        itemType.setArray(false);

        sb.append(
                start + "        "
                        + javaMessageWrapper.toJavaTypeString(itemType, true, false)
                        + " $item" + index + " = " + varName + "[$i" + index + "];"
        );


        nameStack.push("\"[\"");
        nameStack.push("$i" + index);
        nameStack.push("\"]\"");


        encodeCode(itemType, "$item" + index);

        nameStack.pop();
        nameStack.pop();
        nameStack.pop();
        decrement();

        sb.append(start).append("    }\n");
        sb.append(start).append("}\n");
    }

    private void innerListEncodeCode(TypeInfo typeInfo, String varName) {
        if (CollectionUtils.isEmpty(typeInfo.getTypeArguments())) {
            throw new AnalyseException("List 类型参数不明确:" + typeInfo);
        }
        TypeInfo childTypeInfo = typeInfo.getTypeArguments().get(0);
        if (childTypeInfo == null) {
            throw new AnalyseException("List 类型参数不明确:" + typeInfo);
        }
        int index = incrementAndGet();

        sb.append(start).append(" if (").append(varName).append(" != null && !").append(varName).append(".isEmpty()) {\n");
        sb.append(start).append("    AtomicInteger $i" + index + " = new AtomicInteger();\n");
        sb.append(start).append("    ").append(varName).append(".forEach($").append("item" + index + " -> {\n");


        String name = "[\"+$i" + index + ".getAndIncrement()+\"].\"";


        nameStack.push("\"[\"");
        nameStack.push("$i" + index + ".getAndIncrement()");
        nameStack.push("\"]\"");


        encodeCode(childTypeInfo, "$item" + index);

        nameStack.pop();
        nameStack.pop();
        nameStack.pop();
        decrement();

        sb.append(start).append("    });\n");
        sb.append(start).append("}\n");
    }


    private void innerMapEncodeCode(TypeInfo typeInfo, String varName) {


        if (CollectionUtils.isEmpty(typeInfo.getTypeArguments()) || typeInfo.getTypeArguments().size() != 2) {
            throw new AnalyseException("List 类型参数不明确:" + varName);
        }
        TypeInfo keyTypeInfo = typeInfo.getTypeArguments().get(0);
        TypeInfo valueTypeInfo = typeInfo.getTypeArguments().get(1);

        int index = incrementAndGet();

        sb.append(start).append("if (" + varName + " != null) {\n" +
                start + "    " + varName + ".forEach(($k" + index + ",$v" + index + ")-> {");

        nameStack.push("\".\"");
        nameStack.push("$k" + index);
        //$parent + "stringStatusTypeMap." + $k
        encodeCode(valueTypeInfo, "$v" + index);

        nameStack.pop();
        nameStack.pop();
        decrement();

        sb.append(start).append("    });\n").append(start).append("}");
    }

    /**
     * 基本类型
     */
    private void getEncodeCodeItem(String start, TypeInfo typeInfo, String varName) {
        String name = nameStack.stream().collect(Collectors.joining("+"));
        if (javaMessageWrapper.isContainsFilterList(typeInfo)) {
            if (javaMessageWrapper.isHasEncode(typeInfo)) {
                sb.append(start).append("EncodeUtils.encode($list,").append(name).append("+\".\"").append(" ,").append(varName).append("::encode);\n");
            } else {
                sb.append(start).append("EncodeUtils.encodeSingle($list,").append(name).append(" ,").append(varName).append(");\n");
            }
        } else if (!typeInfo.isOtherType() || typeInfo.isEnum()) {
            if (typeInfo.isArrayOrCollection()) {
                sb.append(start).append("EncodeUtils.encodeSingle($list,").append(name).append(" ,").append(varName).append(");\n");
            } else {
                sb.append(start).append("EncodeUtils.encode($list,").append(name).append(" ,").append(varName).append(");\n");
            }
        } else {
            sb.append(start).append("EncodeUtils.encode($list,").append(name).append("+\".\"").append(" ,").append(varName).append("::encode);\n");
        }
        sb.append("//");
        sb.append(nameStack.stream().collect(Collectors.joining("+")));
        sb.append("\n");
    }

//    /**
//     * 基本类型
//     */
//    private void getEncodeCodeItemBase(String name, String parentName) {
//        sb.append(start).append("    ")
//                .append("EncodeUtils.encode($list,").append(parentName).append(" + \"").append(name).append("\", ").append(name).append(");\n");
//    }


    int incrementAndGet() {
        return level.incrementAndGet();
    }

    void decrement() {
        level.decrement();
    }
}

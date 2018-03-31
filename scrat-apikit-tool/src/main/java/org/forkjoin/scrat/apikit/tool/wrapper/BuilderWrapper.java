package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.JavadocInfo;
import org.forkjoin.scrat.apikit.tool.info.ModuleInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.forkjoin.scrat.apikit.tool.utils.CommentUtils;

import java.util.List;

/**
 * @author zuoge85 on 15/6/15.
 */
public class BuilderWrapper<T extends ModuleInfo> {
    protected T moduleInfo;
    private String rootPackage;
    protected Context context;
    private String distPackage;
    private String distName;

    public BuilderWrapper(Context context, T moduleInfo, String rootPackage) {
        this.context = context;
        this.moduleInfo = moduleInfo;
        this.rootPackage = rootPackage;
    }

    public void init() {

    }


    protected void findTypes(TypeInfo type, List<TypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }

    public T getModuleInfo() {
        return moduleInfo;
    }

    public String comment(String start) {
        JavadocInfo comment = moduleInfo.getComment();
        return CommentUtils.formatComment(comment, start);
    }


    public String getFullName(TypeInfo typeInfo) {
        return getPack(typeInfo.getPackageName()) + "." + typeInfo.getName();
    }

    public String getPack(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            if (rootPackage == null) {
                return "";
            }
            return rootPackage;
        } else {
            if (StringUtils.isEmpty(rootPackage)) {
                return packageName;
            } else {
                return rootPackage + "." + packageName;
            }
        }
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public String getPack() {
        String packageName = moduleInfo.getPackageName();
        String sourceRootPackage = context.getRootPackage();
        String childPackageName = packageName.replace(sourceRootPackage, "");
        if (childPackageName.startsWith(".")) {
            childPackageName = childPackageName.substring(1);
        }
        return getPack(childPackageName);
    }

    public String getDistPack() {
        return getPack(getDistPackage());
    }

    public String getFullName() {
        return getPack() + "." + getName();
    }

    public String getSourceFullName() {
        return moduleInfo.getPackageName() + "." + moduleInfo.getName();
    }

//
//    public void initImport() {
//
//    }
//
//
//    public String getTypeString(SupportType supportType) {
//        return getTypeString(supportType, false);
//    }
//
//    public String getTypeString(SupportType supportType, boolean isWrap) {
//        return supportType.getJavaTypeString(isWrap, false);
//    }
//
//    public String getTypeString(SupportType supportType, boolean isWrap, boolean isArrayList) {
//        return supportType.getJavaTypeString(isWrap, isArrayList);
//    }
//
//    public String getTypeStringNoArray(SupportType supportType) {
//        AttributeType attributeType = supportType.getType();
//        if (attributeType == AttributeType.OTHER) {
//            return supportType.getName();
//        } else {
//            return AttributeType.toJavaType(attributeType);
//        }
//    }

    public String getName() {
        return moduleInfo.getName();
    }


    public String getSimpleByFullName(String fullName) {
        int i = fullName.lastIndexOf(".");
        return i > -1 ? fullName.substring(i + 1) : fullName;
    }

    public String getDistPackage() {
        return distPackage;
    }

    public void setDistPackage(String distPackage) {
        this.distPackage = distPackage;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public static String formatBaseComment(JavadocInfo comment, String start) {
        return CommentUtils.formatBaseComment(comment, start);
    }

    public static String formatComment(JavadocInfo comment, String start) {
        return CommentUtils.formatComment(comment, start);
    }
}

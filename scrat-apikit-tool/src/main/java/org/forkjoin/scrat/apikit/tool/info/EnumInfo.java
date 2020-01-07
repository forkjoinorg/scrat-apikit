package org.forkjoin.scrat.apikit.tool.info;

import java.util.ArrayList;
import java.util.List;

public final class EnumInfo extends ModuleInfo {

    private Class<? extends Enum> enumClass;

    private List<EnumElementInfo> enumElementInfos;

    public EnumInfo() {

    }

    public Class<? extends Enum> getEnumClass() {
        return enumClass;
    }

    public void setEnumClass(Class<? extends Enum> enumClass) {
        this.enumClass = enumClass;
    }

    public List<EnumElementInfo> getEnumElementInfos() {
        return enumElementInfos;
    }

    public void setEnumElementInfos(List<EnumElementInfo> enumElementInfos) {
        this.enumElementInfos = enumElementInfos;
    }

    @Override
    public String toString() {
        return "EnumInfo{" +
                "enumClass=" + enumClass +
                ", enumElementInfos=" + enumElementInfos +
                "} " + super.toString();
    }
}

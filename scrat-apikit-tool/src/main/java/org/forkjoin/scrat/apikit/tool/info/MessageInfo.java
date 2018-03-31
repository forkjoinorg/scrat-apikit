package org.forkjoin.scrat.apikit.tool.info;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MessageInfo extends ModuleInfo {
    private ArrayList<PropertyInfo> properties = new ArrayList<>();
    private Map<String, PropertyInfo> propertiesMap = new HashMap<>();

    private List<String> typeParameters = new ArrayList<>();
    private TypeInfo superType;

    private Class messageClass;

    public MessageInfo() {

    }

    public void add(PropertyInfo attributeInfo) {
        properties.add(attributeInfo);
        propertiesMap.put(attributeInfo.getName(), attributeInfo);
    }

    public PropertyInfo getProperty(String name) {
        return propertiesMap.get(name);
    }

    public ArrayList<PropertyInfo> getProperties() {
        return properties;
    }

    public void addTypeParameter(String typeParameter) {
        typeParameters.add(typeParameter);
    }


    public void addTypeParameters(Collection<String> collections) {
        typeParameters.addAll(collections);
    }

    public List<String> getTypeParameters() {
        return typeParameters;
    }

    public Class getMessageClass() {
        return messageClass;
    }

    public void setMessageClass(Class messageClass) {
        this.messageClass = messageClass;
    }

    public TypeInfo getSuperType() {
        return superType;
    }

    public void setSuperType(TypeInfo superType) {
        this.superType = superType;
    }

    @Override
    public String toString() {
        return super.toString() + ",MessageInfo{" +
                "properties=" + properties.size() +
                ", propertiesMap=" + propertiesMap.size() +
                ", typeParameters=" + typeParameters.size() +
                '}';
    }

    public boolean hasGenerics() {
        return CollectionUtils.isNotEmpty(getTypeParameters());
    }

    public void sortPropertys() {
        properties.sort(Comparator.comparing(FieldInfo::getName));
    }
}

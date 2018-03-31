package org.forkjoin.scrat.apikit.tool;

import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.ClassInfo;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.info.PackageInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.forkjoin.scrat.apikit.tool.wrapper.BuilderWrapper;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class Context {
    protected PackageInfo<ApiInfo> apis = new PackageInfo<>();
    private TreeMap<ClassInfo, MessageInfo> messageMap = new TreeMap<>(Comparator.comparing(ClassInfo::getFullName));
    private TreeMap<String, MessageInfo> fullNameMessageMap = new TreeMap<>(Comparator.comparing(r -> r));
    private Map<String, BuilderWrapper<MessageInfo>> messageWrapperMap;

    private String path;
    private String rootPackage;
    private File rootDir;


    public void addApi(ApiInfo apiInfo) {
        apis.add(apiInfo.getPackageName(), apiInfo);
    }

    public void addMessage(ClassInfo key, MessageInfo messageInfo) {
        messageMap.put(key, messageInfo);
        fullNameMessageMap.put(key.getFullName(), messageInfo);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRootPackage() {
        return rootPackage;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public PackageInfo<ApiInfo> getApis() {
        return apis;
    }

    public MessageInfo getMessage(TypeInfo typeInfo) {
        return fullNameMessageMap.get(typeInfo.getFullName());
    }

    public Collection<MessageInfo> getMessages() {
        return messageMap.values();
    }

    public void setRootDir(File rootDir) {
        this.rootDir = rootDir;
    }

    public File getRootDir() {
        return rootDir;
    }

    public BuilderWrapper<MessageInfo> getMessageWrapper(String fullName) {
        return messageWrapperMap.get(fullName);
    }

    public void setMessageWrapperMap(Map<String, BuilderWrapper<MessageInfo>> messageWrapperMap) {
        this.messageWrapperMap = messageWrapperMap;
    }
}

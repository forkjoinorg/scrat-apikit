package org.forkjoin.scrat.apikit.tool;

import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.tool.generator.*;
import org.forkjoin.scrat.apikit.tool.info.*;
import org.forkjoin.scrat.apikit.tool.wrapper.BuilderWrapper;
import reactor.core.publisher.Flux;

import java.util.*;

/**
 *
 */
public abstract class AbstractGenerator extends AbstractFileGenerator {
    protected Context context;

    protected MessageNameMapper messageNameMapper = new DefaultMessageNameMapper();
    protected MessagePackageNameMapper messagePackageNameMapper = new DefaultMessagePackageNameMapper();
    protected List<BuilderWrapper<MessageInfo>> messageBuilderWrappers;
    protected List<BuilderWrapper<EnumInfo>> enumBuilderWrappers;

    protected List<ClassInfo> filterList = new ArrayList<>();

    protected NameMaper apiNameMaper = new PatternNameMaper(
            "(?<name>.*)Controller", "${name}"
    );

    public void generateMessages(Context context) {
        for (BuilderWrapper<MessageInfo> builderWrapper : messageBuilderWrappers) {
            try {
                generateMessage(builderWrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void generateEnum(Context context) {
        for (BuilderWrapper<EnumInfo> builderWrapper : enumBuilderWrappers) {
            try {
                generateEnum(builderWrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private List<BuilderWrapper<MessageInfo>> initMessageWrapper(Context context) {
        Collection<MessageInfo> messages = context.getMessages();
        String sourceRootPackage = context.getRootPackage();

        Map<String, Set<String>> names = new HashMap<>();
        Map<String, BuilderWrapper<MessageInfo>> messageMap = new HashMap<>();

        List<BuilderWrapper<MessageInfo>> builderWrappers = Flux
                .fromIterable(messages)
                .filter(this::filterModule)
                .map(messageInfo -> {
                    String packageName = messageInfo.getPackageName();

                    String distPackage = messagePackageNameMapper.apply(sourceRootPackage, packageName);

                    Set<String> nameSet = names.computeIfAbsent(distPackage, k -> new HashSet<>());

                    String distName = messageNameMapper.apply(nameSet, messageInfo.getPackageName(), messageInfo.getName());
                    nameSet.add(distName);

                    BuilderWrapper<MessageInfo> messageWarpper = createMessageWarpper(messageInfo, distPackage, distName);
                    if (CollectionUtils.isNotEmpty(filterList)) {
                        messageWarpper.setFilterList(filterList);
                    }
                    return messageWarpper;
                }).collectList().block();

        builderWrappers.forEach(item -> messageMap.put(item.getSourceFullName(), item));


        context.setMessageWrapperMap(messageMap);
        return builderWrappers;
    }

    private List<BuilderWrapper<EnumInfo>> initEnumWrapper(Context context) {
        Collection<EnumInfo> enums = context.getEnums();
        String sourceRootPackage = context.getRootPackage();

        Map<String, Set<String>> names = new HashMap<>();
        Map<String, BuilderWrapper<EnumInfo>> enumMap = new HashMap<>();

        List<BuilderWrapper<EnumInfo>> builderWrappers = Flux
                .fromIterable(enums)
                .filter(this::filterModule)
                .map(enumInfo -> {
                    String packageName = enumInfo.getPackageName();

                    String distPackage = messagePackageNameMapper.apply(sourceRootPackage, packageName);

                    Set<String> nameSet = names.computeIfAbsent(distPackage, k -> new HashSet<>());

                    String distName = messageNameMapper.apply(nameSet, enumInfo.getPackageName(), enumInfo.getName());
                    nameSet.add(distName);

                    BuilderWrapper<EnumInfo> enumWarpper = createEnumWarpper(enumInfo, distPackage, distName);
                    if (CollectionUtils.isNotEmpty(filterList)) {
                        enumWarpper.setFilterList(filterList);
                    }
                    return enumWarpper;
                })
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());

        builderWrappers.forEach(item -> enumMap.put(item.getSourceFullName(), item));


        context.setEnumWrapperMap(enumMap);
        return builderWrappers;
    }

    private boolean filterModule(ModuleInfo moduleInfo) {
        if (CollectionUtils.isNotEmpty(filterList)) {
            for (ClassInfo ci : filterList) {
                if (ci.equals(moduleInfo.getPackageName(), moduleInfo.getName())) {
                    return false;
                }
            }
        }
        return true;
    }


    public void generate(Context context) throws Exception {
        this.context = context;
        this.messageBuilderWrappers = initMessageWrapper(context);
        this.enumBuilderWrappers = initEnumWrapper(context);

        for (ApiInfo apiInfo : context.apis.getValues()) {
            try {
                generateApi(apiInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        generateMessages(context);

        generateEnum(context);

        generateTool();
    }


    public List<ClassInfo> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<ClassInfo> filterList) {
        this.filterList = filterList;
    }

    protected abstract BuilderWrapper<MessageInfo> createMessageWarpper(MessageInfo messageInfo, String distPackage, String distName);

    protected abstract BuilderWrapper<EnumInfo> createEnumWarpper(EnumInfo enumInfo, String distPackage, String distName);

    public abstract void generateApi(ApiInfo apiInfo) throws Exception;

    public abstract void generateMessage(BuilderWrapper<MessageInfo> builderWrapper) throws Exception;

    public abstract void generateEnum(BuilderWrapper<EnumInfo> builderWrapper) throws Exception;

    public abstract void generateTool() throws Exception;

}

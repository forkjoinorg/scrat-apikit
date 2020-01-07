package org.forkjoin.scrat.apikit.tool;

import org.forkjoin.scrat.apikit.tool.generator.DefaultMessageNameMapper;
import org.forkjoin.scrat.apikit.tool.generator.DefaultMessagePackageNameMapper;
import org.forkjoin.scrat.apikit.tool.generator.MessageNameMapper;
import org.forkjoin.scrat.apikit.tool.generator.MessagePackageNameMapper;
import org.forkjoin.scrat.apikit.tool.generator.NameMaper;
import org.forkjoin.scrat.apikit.tool.generator.PatternNameMaper;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.wrapper.BuilderWrapper;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public abstract class AbstractGenerator extends AbstractFileGenerator {
    protected Context context;

    protected MessageNameMapper messageNameMapper = new DefaultMessageNameMapper();
    protected MessagePackageNameMapper messagePackageNameMapper = new DefaultMessagePackageNameMapper();
    protected List<BuilderWrapper<MessageInfo>> builderWrappers;

    protected NameMaper apiNameMaper = new PatternNameMaper(
            "(?<name>.*)Controller", "${name}"
    );

    public void generateMessages(Context context) {
        for (BuilderWrapper<MessageInfo> builderWrapper : builderWrappers) {
            try {
                generateMessage(builderWrapper);
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
                .map(messageInfo -> {
                    String packageName = messageInfo.getPackageName();

                    String distPackage = messagePackageNameMapper.apply(sourceRootPackage, packageName);

                    Set<String> nameSet = names.computeIfAbsent(distPackage, k -> new HashSet<>());

                    String distName = messageNameMapper.apply(nameSet, messageInfo.getPackageName(), messageInfo.getName());
                    nameSet.add(distName);

                    return createMessageWarpper(messageInfo, distPackage, distName);
                }).collectList().block();

        builderWrappers.forEach(item -> messageMap.put(item.getSourceFullName(), item));


        context.setMessageWrapperMap(messageMap);
        return builderWrappers;
    }


    public void generate(Context context) throws Exception {
        this.context = context;
        this.builderWrappers = initMessageWrapper(context);

        for (ApiInfo apiInfo : context.apis.getValues()) {
            try {
                generateApi(apiInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        generateMessages(context);

        generateTool();
    }

    protected abstract BuilderWrapper<MessageInfo> createMessageWarpper(MessageInfo messageInfo, String distPackage, String distName);

    public abstract void generateApi(ApiInfo apiInfo) throws Exception;

    public abstract void generateMessage(BuilderWrapper<MessageInfo> builderWrapper) throws Exception;

    public abstract void generateTool() throws Exception;

}

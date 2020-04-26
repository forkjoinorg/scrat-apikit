package org.forkjoin.scrat.apikit.tool.generator;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.tool.Utils;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.EnumInfo;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.utils.JsonUtils;
import org.forkjoin.scrat.apikit.tool.wrapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 这个版本还不能 ,解开泛型或者支持泛型
 */
public class JavaScriptGenerator extends AbstractHttlGenerator {
    private static final Logger log = LoggerFactory.getLogger(JavaScriptGenerator.class);

    private JSWrapper.Type type = JSWrapper.Type.TypeScript;
    private String jsPackageName;
    protected NameMapper apiNameMapper = new PatternNameMapper(
            "(?<name>.*)Controller|Service", "${name}Api"
    );

    public JavaScriptGenerator(String jsPackageName) {
        this.jsPackageName = jsPackageName;
    }

    protected File getFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, "src", utils.getPack(), utils.getName(), getFileSuffix());
    }

    protected File getMessageFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, "src", utils.getPack(utils.getDistPackage()), utils.getName(), getFileSuffix());
    }

    protected String getFileSuffix() {
        if (type.equals(JSWrapper.Type.TypeScript)) {
            return ".ts";
        } else {
            return ".js";
        }
    }


    protected String getTempl(String name) {
        return "/org/forkjoin/scrat/apikit/tool/generator/js/" + type.name() + "/" + name;
    }

    @Override
    public void generateApi(ApiInfo apiInfo) throws Exception {
        JSApiWrapper utils = new JSApiWrapper(context, apiInfo, rootPackage, jsPackageName, apiNameMapper);
        String packageName = apiInfo.getPackageName();
        String sourceRootPackage = context.getRootPackage();
        String distPackage = messagePackageNameMapper.apply(sourceRootPackage, packageName);
        utils.setDistName(apiInfo.getName());
        utils.setDistPackage(distPackage);

        if (CollectionUtils.isNotEmpty(filterList)) {
            utils.setFilterList(filterList);
        }
        File file = getFileName(utils);
        utils.setType(type);
        utils.init();


        executeModule(
                utils,
                getTempl("ApiItem.httl"),
                file
        );
    }

    @Override
    protected BuilderWrapper<MessageInfo> createMessageWrapper(MessageInfo messageInfo, String distPackage, String distName) {
        JSMessageWrapper jsMessageWrapper = new JSMessageWrapper(context, messageInfo, rootPackage);
        jsMessageWrapper.setDistName(distName);
        jsMessageWrapper.setDistPackage(distPackage);
        jsMessageWrapper.setType(type);
        return jsMessageWrapper;
    }

    @Override
    protected BuilderWrapper<EnumInfo> createEnumWrapper(EnumInfo enumInfo, String distPackage, String distName) {
        JSEnumWrapper jsEnumWrapper = new JSEnumWrapper(context, enumInfo, rootPackage);
        jsEnumWrapper.setDistName(distName);
        jsEnumWrapper.setDistPackage(distPackage);
        jsEnumWrapper.setType(type);
        return jsEnumWrapper;
    }


    @Override
    public void generateMessage(BuilderWrapper<MessageInfo> utils) throws Exception {
        File file = getMessageFileName(utils);

        utils.init();
        executeModule(
                utils,
                getTempl("Message.httl"),
                file
        );
    }

    @Override
    public void generateEnum(BuilderWrapper<EnumInfo> utils) throws Exception {
        File file = getMessageFileName(utils);

        utils.init();
        executeModule(
                utils,
                getTempl("Enum.httl"),
                file
        );
    }


    @Override
    public void generateTool() throws Exception {
        {
            Map<String, Object> parameters = new HashMap<>();

            List<Map.Entry> apis = context.getApis()
                    .getValues()
                    .stream()
                    .map(apiInfo -> {
                        JSApiWrapper utils = new JSApiWrapper(context, apiInfo, rootPackage, jsPackageName, apiNameMapper);
                        String value = utils.getPack().replace(".", "/") + '/' + utils.getName();
                        return new AbstractMap.SimpleImmutableEntry<>(utils.getName(), value);
                    })
                    .collect(Collectors.toList());

            parameters.put("apis", apis);

            File file = Utils.packToPath(outPath, "src", "", "index", ".js");

            execute(
                    parameters,
                    getTempl("index.httl"),
                    file
            );
        }
        {
            boolean isEmpty = context.getApis().getAll().isEmpty() && context.getEnums().isEmpty() && context.getMessages().isEmpty();
            File packageFile = Utils.packToPath(outPath, "", "package", ".json");
            ObjectNode packageJson = null;
            if (packageFile.exists()) {
                packageJson = (ObjectNode) JsonUtils.mapper.readTree(packageFile);
            } else {
                if (!isEmpty) {
                    try (InputStream inputStream = JavaScriptGenerator.class.getResourceAsStream(getTempl("package.json"))) {
                        packageJson = (ObjectNode) JsonUtils.mapper.readTree(inputStream);
                    }
                }
            }

            if (packageJson != null) {
                packageJson.put("name", jsPackageName);
                if (this.version != null) {
                    String prevVersionText = packageJson.get("version").asText();
                    if (prevVersionText != null) {
                        prevVersionText = prevVersionText.replaceAll("([^.]+)\\.([^.]+)\\.([^.^-]+)(-{0,1})(.*)", "$1.$2." + version + "$4$5");
                    } else {
                        prevVersionText = "1.0." + version;
                    }
                    log.info("写入版本:{}", prevVersionText);
                    packageJson.put("version", prevVersionText);
                }
                JsonUtils.mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile, packageJson);
            }
        }
    }

    public JSWrapper.Type getType() {
        return type;
    }

    public void setType(JSWrapper.Type type) {
        this.type = type;
    }

    public String getJsPackageName() {
        return jsPackageName;
    }

    public void setJsPackageName(String jsPackageName) {
        this.jsPackageName = jsPackageName;
    }

    public NameMapper getApiNameMapper() {
        return apiNameMapper;
    }

    public void setApiNameMapper(NameMapper apiNameMapper) {
        this.apiNameMapper = apiNameMapper;
    }
}

package org.forkjoin.scrat.apikit.tool.generator;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.forkjoin.scrat.apikit.tool.Utils;
import org.forkjoin.scrat.apikit.tool.info.ApiInfo;
import org.forkjoin.scrat.apikit.tool.info.EnumInfo;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.utils.JsonUtils;
import org.forkjoin.scrat.apikit.tool.wrapper.*;

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
public class JavaScriptGeneratorAbstract extends AbstractHttlGenerator {
    private JSWrapper.Type type = JSWrapper.Type.CommonJS;
    private String jsPackageName;
    protected NameMaper apiNameMaper = new PatternNameMaper(
            "(?<name>.*)Controller", "${name}"
    );

    public JavaScriptGeneratorAbstract(String jsPackageName) {
        this.jsPackageName = jsPackageName;
    }

    protected File getFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, utils.getPack(), utils.getName(), ".js");
    }

    protected File getTsDFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, utils.getPack(), utils.getName(), ".d.ts");
    }


    protected File getMessageFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, utils.getPack(utils.getDistPackage()), utils.getName(), ".js");
    }

    protected File getTsDMessageFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, utils.getPack(utils.getDistPackage()), utils.getName(), ".d.ts");
    }

    protected String getTempl(String name) {
        return "/org/forkjoin/scrat/apikit/tool/generator/js/" + type.name() + "/" + name;
    }

    @Override
    public void generateApi(ApiInfo apiInfo) throws Exception {
        JSApiWrapper utils = new JSApiWrapper(context, apiInfo, rootPackage, jsPackageName, apiNameMaper);
        if(CollectionUtils.isNotEmpty(filterList)){
            utils.setFilterList(filterList);
        }
        File dFile = getTsDFileName(utils);
        File file = getFileName(utils);
        utils.setType(type);
        utils.init();

        executeModule(
                utils,
                getTempl("ApiItem.httl"),
                file
        );

        executeModule(
                utils,
                getTempl("ApiItem.d.httl"),
                dFile
        );
    }

    @Override
    protected BuilderWrapper<MessageInfo> createMessageWarpper(MessageInfo messageInfo, String distPackage, String distName) {
        JSMessageWrapper jsMessageWrapper = new JSMessageWrapper(context, messageInfo, rootPackage);
        jsMessageWrapper.setDistName(distName);
        jsMessageWrapper.setDistPackage(distPackage);
        jsMessageWrapper.setType(type);
        return jsMessageWrapper;
    }

    @Override
    protected BuilderWrapper<EnumInfo> createEnumWarpper(EnumInfo enumInfo, String distPackage, String distName) {
        JSEnumWrapper jsEnumWrapper = new JSEnumWrapper(context, enumInfo, rootPackage);
        jsEnumWrapper.setDistName(distName);
        jsEnumWrapper.setDistPackage(distPackage);
        jsEnumWrapper.setType(type);
        return jsEnumWrapper;
    }


    @Override
    public void generateMessage(BuilderWrapper<MessageInfo> utils) throws Exception {
        File dFile = getTsDMessageFileName(utils);
        File file = getMessageFileName(utils);

        utils.init();
        executeModule(
                utils,
                getTempl("Message.d.httl"),
                dFile
        );

        FileUtils.write(file, "", "utf8");
    }

    @Override
    public void generateEnum(BuilderWrapper<EnumInfo> builderWrapper) throws Exception {

    }

    public void copyTool(String name) throws Exception {
        File file = new File(outPath, name);
        FileUtils.copyInputStreamToFile(
                JavaScriptGeneratorAbstract.class.getResourceAsStream(
                        "/org/forkjoin/scrat/apikit/tool/generator/js/" + type.name() + "/tool/" + name
                ),
                file
        );
    }

    @Override
    public void generateTool() throws Exception {
        {
            Map<String, Object> parameters = new HashMap<>();


//            parameters.put("values", context.getMessages());

//

            List<Map.Entry> apis = context.getApis().getValues().stream().map(apiInfo -> {
                JSApiWrapper utils = new JSApiWrapper(context, apiInfo, rootPackage, jsPackageName, apiNameMaper);
                String value = utils.getPack().replace(".", "/") + '/' + utils.getName();
                return new AbstractMap.SimpleImmutableEntry<>(utils.getName(), value);
            }).collect(Collectors.toList());

            parameters.put("apis", apis);

            File dFile = Utils.packToPath(outPath, "", "index", ".d.ts");
            File file = Utils.packToPath(outPath, "", "index", ".js");

            execute(
                    parameters,
                    getTempl("index.d.httl"),
                    dFile
            );

            execute(
                    parameters,
                    getTempl("index.httl"),
                    file
            );
        }
        {
            File packageFile = Utils.packToPath(outPath, "", "package", ".json");
            ObjectNode packageJson;
            if (packageFile.exists()) {
                packageJson = (ObjectNode) JsonUtils.mapper.readTree(packageFile);
            } else {
                try (InputStream inputStream = JavaScriptGeneratorAbstract.class.getResourceAsStream(getTempl("package.json"))) {
                    packageJson = (ObjectNode) JsonUtils.mapper.readTree(inputStream);
                }
            }

            packageJson.put("name", jsPackageName);
            if (this.version != null) {
                String prevVersionText = packageJson.get("version").asText();
                if (prevVersionText != null) {
                    prevVersionText = prevVersionText.replaceAll("([^.]+)\\.([^.]+)\\.([^.]+)", "$1.$2." + version);
                } else {
                    prevVersionText = "1.0." + version;
                }
                packageJson.put("version", prevVersionText);
            }
            JsonUtils.mapper.writerWithDefaultPrettyPrinter().writeValue(packageFile, packageJson);
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

    public NameMaper getApiNameMaper() {
        return apiNameMaper;
    }

    public void setApiNameMaper(NameMaper apiNameMaper) {
        this.apiNameMaper = apiNameMaper;
    }
}

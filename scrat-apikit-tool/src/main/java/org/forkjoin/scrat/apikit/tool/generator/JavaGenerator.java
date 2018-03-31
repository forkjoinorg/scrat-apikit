package org.forkjoin.scrat.apikit.tool.generator;

import org.forkjoin.scrat.apikit.tool.Utils;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.wrapper.BuilderWrapper;
import org.forkjoin.scrat.apikit.tool.wrapper.JavaMessageWrapper;

import java.io.File;

;

/**
 *
 */
public abstract class JavaGenerator extends HttlGenerator {
    protected File getFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, utils.getPack(), utils.getName(), ".java");
    }


    protected File getMessageFileName(BuilderWrapper utils) {
        return Utils.packToPath(outPath, utils.getPack(utils.getDistPackage()), utils.getName(), ".java");
    }

    @Override
    public void generateMessage(BuilderWrapper<MessageInfo> utils) throws Exception {
        File file = getMessageFileName(utils);
        utils.init();
        executeModule(
                utils,
                "/org/forkjoin/scrat/apikit/tool/generator/JavaMessage.httl",
                file
        );
    }

    @Override
    protected JavaMessageWrapper createMessageWarpper(MessageInfo messageInfo, String distPackage, String distName) {
        JavaMessageWrapper javaMessageWrapper = new JavaMessageWrapper(context, messageInfo, rootPackage);
        javaMessageWrapper.setDistPackage(distPackage);
        javaMessageWrapper.setDistName(distName);
        return javaMessageWrapper;
    }
}

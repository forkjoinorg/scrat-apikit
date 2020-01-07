package org.forkjoin.scrat.apikit.tool;

import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

/**
 * @author zuoge85 on 15/11/8.
 */
public class Manager {
    private static final Logger log = LoggerFactory.getLogger(Manager.class);

    private String path;
    private String rootPackage;
    private String fileCharset = "utf8";
    private String fileSuffix = ".java";
    private String[] srcPaths;

    //    private Analyse analyse;
    private ObjectFactory objectFactory;
    //    private List<Generator> messageGenerators = new ArrayList<>();
    private File rootDir;
    private String rootDirPath;
    private Context context;

    public void analyse() {
        rootDir = Utils.packToPath(path, rootPackage);
        rootDirPath = rootDir.getAbsolutePath();
        context = objectFactory.createContext();
        context.setPath(path);
        context.setRootPackage(rootPackage);
        context.setRootDir(rootDir);
        context.setSrcPaths(srcPaths);

        analyseNext();
    }

    private void analyseNext() {
        ApiAnalyse apiAnalyse = objectFactory.createApiAnalyse();
        apiAnalyse.analyse(context);

        MessageAnalyse messageAnalyse = objectFactory.createMessageAnalyse();
        messageAnalyse.analyse(context);

        Set<TypeInfo> enumTypes = messageAnalyse.getEnumTypes();


        EnumAnalyse enumAnalyse = objectFactory.createEnumAnalyse();
        enumAnalyse.analyse(context, enumTypes);
    }

    public void generate(Generator generator) throws Exception {
        generator.generate(context);
    }

    private String analysePack(File f) {
        String path = f.getParent();

        return path.length() > rootDirPath.length() ?
                Utils.pathToPack(path.substring(rootDirPath.length() + 1)) : "";
    }

    public void setFileCharset(String fileCharset) {
        this.fileCharset = fileCharset;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }


    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getSrcPaths() {
        return srcPaths;
    }

    public void setSrcPaths(String[] srcPaths) {
        this.srcPaths = srcPaths;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "path='" + path + '\'' +
                ", rootPackage='" + rootPackage + '\'' +
                ", fileCharset='" + fileCharset + '\'' +
                ", fileSuffix='" + fileSuffix + '\'' +
                ", objectFactory=" + objectFactory +
                ", rootDir=" + rootDir +
                ", rootDirPath='" + rootDirPath + '\'' +
                ", context=" + context +
                '}';
    }
}

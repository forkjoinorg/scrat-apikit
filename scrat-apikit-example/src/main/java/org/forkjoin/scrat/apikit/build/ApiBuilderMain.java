package org.forkjoin.scrat.apikit.build;


import org.forkjoin.scrat.apikit.tool.Analyse;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.Manager;
import org.forkjoin.scrat.apikit.tool.MessageAnalyse;
import org.forkjoin.scrat.apikit.tool.ObjectFactory;
import org.forkjoin.scrat.apikit.tool.generator.JavaClientGenerator;
import org.forkjoin.scrat.apikit.tool.generator.JavaScriptGenerator;
import org.forkjoin.scrat.apikit.tool.generator.PatternNameMaper;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathAnalyse;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathMessageAnalyse;
import org.forkjoin.scrat.apikit.tool.wrapper.JSWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.File;


@SpringBootConfiguration
public class ApiBuilderMain implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ApiBuilderMain.class);


    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiBuilderMain.class).web(WebApplicationType.NONE).run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        final String version = "v1";

        File root = new File("scrat-apikit-example");
        if (!root.exists()) {
            root = new File(".");
        }

        root = new File("scrat-apikit/scrat-apikit-example");
        if (!root.exists()) {
            root = new File(".");
        }

        File dir = new File(root, "src/main/java/");
        File apiDocDir = new File(root, "src/main/resources/");

        File apiDoc = new File(root, "src/main/resources/static/apidoc");

        File javaClientDir = new File(root, "src/test/java/");
        File jsClientDir = new File(root, "src/test/js/");


        // TODO 修改下面的乱七八糟的路径
        log.info("代码路径:{}", dir.getAbsolutePath());


        Manager manager = new Manager();
        manager.setPath(dir.getAbsolutePath());
        manager.setRootPackage("org.forkjoin.scrat.apikit.example");
        manager.setObjectFactory(objectFactory);

        //开始处理
        manager.analyse();

        {
            JavaClientGenerator generator = new JavaClientGenerator();
            generator.setOutPath(javaClientDir.getAbsolutePath());
            generator.setApiNameMaper(new PatternNameMaper(
                    "(?<name>.*)Service", "${name}Api"
            ));
            generator.setRootPackage("org.forkjoin.scrat.apikit.example.client");
            manager.generate(generator);
        }
        {
            JavaScriptGenerator generator = new JavaScriptGenerator("test");
            generator.setType(JSWrapper.Type.ES6);
            generator.setOutPath(jsClientDir.getAbsolutePath());
            generator.setVersion("2");
            manager.generate(generator);
        }
    }

    private static ObjectFactory objectFactory = new ObjectFactory() {
        @Override
        public Analyse createAnalyse() {
            return new ClassPathAnalyse();
        }

        @Override
        public MessageAnalyse createMessageAnalyse() {
            return new ClassPathMessageAnalyse();
        }

        @Override
        public Context createContext() {
            return new Context();
        }
    };

}

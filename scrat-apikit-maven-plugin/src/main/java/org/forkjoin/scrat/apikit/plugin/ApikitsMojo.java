package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.forkjoin.scrat.apikit.plugin.bean.Group;
import org.forkjoin.scrat.apikit.plugin.bean.JavaClientTask;
import org.forkjoin.scrat.apikit.plugin.bean.JavaScriptTask;
import org.forkjoin.scrat.apikit.plugin.bean.Task;
import org.forkjoin.scrat.apikit.tool.Analyse;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.Generator;
import org.forkjoin.scrat.apikit.tool.Manager;
import org.forkjoin.scrat.apikit.tool.MessageAnalyse;
import org.forkjoin.scrat.apikit.tool.ObjectFactory;
import org.forkjoin.scrat.apikit.tool.generator.JavaClientGenerator;
import org.forkjoin.scrat.apikit.tool.generator.JavaScriptGenerator;
import org.forkjoin.scrat.apikit.tool.generator.PatternNameMaper;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathAnalyse;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathMessageAnalyse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mojo(name = "apis")
public class ApikitsMojo extends AbstractMojo {
    @Parameter
    private List<Group> groups;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Map pluginContext = getPluginContext();
        MavenProject project = (MavenProject) pluginContext.get("project");
        List<String> compileSourceRoots = project
                .getCompileSourceRoots()
                .stream()
                .filter(str -> !str.contains("generated-sources/annotations"))
                .collect(Collectors.toList());

        if (compileSourceRoots.size() > 1) {
            throw new RuntimeException("Multiple compileSourceRoot is not supported");
        }
        String sourcePath = compileSourceRoots.get(0);


        getLog().info("开始执行全部任务" + groups);

        for (Group group:groups) {
            getLog().info("开始执行第一组" + groups + pluginContext);
            GenerateUtils.generate(group, sourcePath, getLog());
            getLog().info("结束第一组" + groups + pluginContext);
        }

    }

    private Generator createGenerator(Manager manager, Task task) throws Exception {
        if (task instanceof JavaClientTask) {
            JavaClientTask javaClientTask = (JavaClientTask) task;
            JavaClientGenerator generator = new JavaClientGenerator();
            generator.setOutPath(javaClientTask.getOutPath());
            generator.setApiNameMaper(new PatternNameMaper(
                    javaClientTask.getNameMaperSource(), javaClientTask.getNameMaperDist()
            ));
            generator.setRootPackage(javaClientTask.getRootPackage());
            return generator;
        } else if (task instanceof JavaScriptTask) {
            JavaScriptTask javaScriptTask = (JavaScriptTask) task;
            JavaScriptGenerator generator = new JavaScriptGenerator("test");
            generator.setType(javaScriptTask.getType());
            generator.setOutPath(javaScriptTask.getOutPath());
            generator.setVersion("2");
            return generator;
        } else {
            throw new RuntimeException("错误的额任务类型:" + task.getClass());
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

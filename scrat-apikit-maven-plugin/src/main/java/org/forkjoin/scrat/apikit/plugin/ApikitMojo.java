package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.forkjoin.scrat.apikit.plugin.bean.Group;
import org.forkjoin.scrat.apikit.plugin.bean.Task;
import reactor.core.publisher.Hooks;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mojo(name = "api", requiresDependencyCollection = ResolutionScope.COMPILE)
public class ApikitMojo extends AbstractMojo {
    static {
        Hooks.onOperatorDebug();
    }
    @Parameter
    private List<Task> tasks;

    @Parameter
    private String rootPackage;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Map pluginContext = getPluginContext();
        MavenProject project = (MavenProject) pluginContext.get("project");
        String[] compileSourceRoots = project
                .getCompileSourceRoots()
                .stream()
                .filter(str -> !str.contains("generated-sources/annotations"))
                .toArray(String[]::new);


        if (compileSourceRoots.length > 1) {
            throw new RuntimeException("Multiple compileSourceRoot is not supported");
        }
        String sourcePath = compileSourceRoots[0];


        getLog().info("开始执行全部任务" + tasks + pluginContext);

        MavenUtils.generate(project, new Group(tasks, rootPackage), sourcePath, compileSourceRoots);
//        project.getsour
    }
}

package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.forkjoin.scrat.apikit.plugin.bean.Group;
import org.forkjoin.scrat.apikit.plugin.bean.Task;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mojo(name = "api")
public class ApikitMojo extends AbstractMojo {

    @Parameter
    private List<Task> tasks;

    @Parameter
    private String rootPackage;

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


        getLog().info("开始执行全部任务" + tasks + pluginContext);

        GenerateUtils.generate(new Group(tasks, rootPackage), sourcePath, getLog());
//        project.getsour
    }
}

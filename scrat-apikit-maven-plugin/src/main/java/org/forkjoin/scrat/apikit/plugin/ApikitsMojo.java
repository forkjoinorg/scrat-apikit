package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.forkjoin.scrat.apikit.plugin.bean.Group;

import java.util.List;
import java.util.stream.Collectors;

@Mojo(
        name = "apis",
        requiresDependencyCollection = ResolutionScope.RUNTIME,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
public class ApikitsMojo extends AbstractMojo {
    @Parameter
    private List<Group> groups;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenProject project = session.getCurrentProject();
        List<String> compileSourceRoots = project
                .getCompileSourceRoots()
                .stream()
                .filter(str -> !str.contains("generated-sources/annotations"))
                .collect(Collectors.toList());

        if (compileSourceRoots.size() > 1) {
            throw new RuntimeException("Multiple compileSourceRoot is not supported");
        }
        String sourcePath = compileSourceRoots.get(0);

        try {

            getLog().info("开始执行全部任务" + groups);

            for (Group group : groups) {
                getLog().info("开始执行第一组" + groups);

                MavenUtils.generate(project, group, sourcePath);

                getLog().info("结束第一组" + groups);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

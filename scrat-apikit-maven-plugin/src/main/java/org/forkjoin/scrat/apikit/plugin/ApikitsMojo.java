package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.forkjoin.scrat.apikit.plugin.bean.GitInfo;
import org.forkjoin.scrat.apikit.plugin.bean.Group;
import org.forkjoin.scrat.apikit.plugin.bean.Task;
import reactor.core.publisher.Hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Mojo(
        name = "apis",
        requiresDependencyCollection = ResolutionScope.RUNTIME,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
public class ApikitsMojo extends AbstractMojo {
    static {
        Hooks.onOperatorDebug();
    }

    @Parameter
    private List<Group> groups;

    @Parameter
    private GitInfo git;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    @Parameter(defaultValue = "更新SDK版本:%s", readonly = true, required = true)
    private String commitTemplate;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenProject project = session.getCurrentProject();
        String[] compileSourceRoots = project
                .getCompileSourceRoots()
                .stream()
                .filter(str -> !str.contains("generated-sources/annotations"))
                .toArray(String[]::new);


        if (compileSourceRoots.length > 1) {
            throw new RuntimeException("Multiple compileSourceRoot is not supported");
        }
        String sourcePath = compileSourceRoots[0];

        if (git != null) {
            gitGenerate(project, compileSourceRoots, sourcePath);
        } else {
            generate(project, compileSourceRoots, sourcePath);
        }
    }

    private void gitGenerate(MavenProject project, String[] compileSourceRoots, String sourcePath) {
        try {
            Path tempDir = Files.createTempDirectory("apikit-git");
            getLog().info("git clone 成功");
            String dir = tempDir.toAbsolutePath().toString();
            getLog().info("git临时目录:" + dir);

//            File src = new File(tempDir.toFile(), srcUri);

            getLog().info("开始git clone" + groups);

            GitUtils.clone(git.getUrl(), git.getBranch(), dir, getLog());
            GitUtils.setNameAndEmail(dir, git.getName(), git.getEmail(), getLog());
            int version = GitUtils.getVersion(dir, getLog());
            getLog().info("开始git version:" + version);
            getLog().info("开始执行全部任务" + groups);

            for (Group group : groups) {
                List<Task> tasks = group.getTasks();
                tasks.forEach(task -> {
                    String outPath = tempDir.resolve(task.getOutPath()).toAbsolutePath().toString();
                    task.setOutPath(outPath);
                    task.setVersion(Integer.toString(version + 1));
                    if (task.isClean()) {
                        try {
                            FileUtils.deleteDirectory(new File(outPath), task.getCleanExcludes() == null
                                    ? Collections.emptyList() : task.getCleanExcludes(), getLog());

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                getLog().info("开始执行第一组" + groups);

                MavenUtils.generate(project, group, sourcePath, compileSourceRoots, git);

                getLog().info("结束第一组" + groups);
            }

            //开始git 提交

            GitUtils.add(dir, getLog());
            String message = String.format(commitTemplate, Integer.toString(version));
            boolean commit = GitUtils.commit(dir, message, getLog());
            if(commit){
                GitUtils.push(dir, git.getBranch(), getLog());
            }else{
                getLog().info("不需要提交");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generate(MavenProject project, String[] compileSourceRoots, String sourcePath) {
        try {

            getLog().info("开始执行全部任务" + groups);

            for (Group group : groups) {
                getLog().info("开始执行第一组" + groups);

                MavenUtils.generate(project, group, sourcePath, compileSourceRoots, git);

                getLog().info("结束第一组" + groups);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

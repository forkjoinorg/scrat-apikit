package org.forkjoin.scrat.apikit.plugin;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.maven.plugin.logging.Log;
import org.forkjoin.scrat.apikit.plugin.bean.Group;
import org.forkjoin.scrat.apikit.plugin.bean.GitTask;
import org.forkjoin.scrat.apikit.plugin.bean.JavaClientTask;
import org.forkjoin.scrat.apikit.plugin.bean.JavaScriptTask;
import org.forkjoin.scrat.apikit.plugin.bean.Task;
import org.forkjoin.scrat.apikit.tool.AbstractFileGenerator;
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
import org.forkjoin.scrat.apikit.tool.jgit.GitGenerator;

import java.util.List;

public class GenerateUtils {
    public static void generate(Group group, String sourcePath, Log log) {
        String rootPackage = group.getRootPackage();
        List<Task> tasks = group.getTasks();
        if (CollectionUtils.isEmpty(tasks)) {
            log.info("当前没有任务");
            return;
        }

        Manager manager = new Manager();
        manager.setPath(sourcePath);
        manager.setRootPackage(rootPackage);
        manager.setObjectFactory(objectFactory);
        manager.analyse();
        try {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                log.info(
                        "开始执行任务:"
                                + (i + 1)
                                + "type:"
                                + task.getClass().getSimpleName()
                );
                if (task instanceof GitTask) {
                    GitTask gitTask = (GitTask) task;
                    GitGenerator gitGenerator = new GitGenerator();
                    gitGenerator.setGitUrl(gitTask.getUrl());

                    gitGenerator.setGitUser(gitTask.getUser());
                    gitGenerator.setGetPassword(gitTask.getPassword());
                    gitGenerator.setGitEmail(gitTask.getAuthorEmail());
                    gitGenerator.setGitName(gitTask.getAuthorName());

                    gitGenerator.setGenerator((AbstractFileGenerator) createGenerator(manager, task));
                    manager.generate(gitGenerator);
                } else {
                    Generator generator = createGenerator(manager, task);
                    manager.generate(generator);
                }
                log.info(
                        "结束执行任务:"
                                + (i + 1)
                                + "type:"
                                + task.getClass().getSimpleName()
                );
            }
            log.info(
                    "所有任务执行完毕"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Generator createGenerator(Manager manager, Task task) throws Exception {
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

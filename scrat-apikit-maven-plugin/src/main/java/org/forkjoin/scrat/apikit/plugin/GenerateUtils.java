package org.forkjoin.scrat.apikit.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.forkjoin.scrat.apikit.plugin.bean.*;
import org.forkjoin.scrat.apikit.tool.*;
import org.forkjoin.scrat.apikit.tool.generator.JavaClientGeneratorAbstract;
import org.forkjoin.scrat.apikit.tool.generator.JavaScriptGeneratorAbstract;
import org.forkjoin.scrat.apikit.tool.generator.PatternNameMaper;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathApiAnalyse;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathEnumAnalyse;
import org.forkjoin.scrat.apikit.tool.impl.ClassPathMessageAnalyse;
import org.forkjoin.scrat.apikit.tool.jgit.GitGenerator;

import java.util.List;

public class GenerateUtils {

    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            TypeFactory tf = TypeFactory.defaultInstance()
                    .withClassLoader(GenerateUtils.class.getClassLoader());
            objectMapper.setTypeFactory(tf);
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void generate(String groupJson, String gitJson, String sourcePath, String[] srcPaths) {
        Group group = deserialize(groupJson, Group.class);
        GitInfo git = gitJson == null ? null : deserialize(gitJson, GitInfo.class);
        generate(group, git, sourcePath, srcPaths, new SystemStreamLog());
    }

    public static void generate(Group group, GitInfo git, String sourcePath, String[] srcPaths, Log log) {
        String rootPackage = group.getRootPackage();
        List<Task> tasks = group.getTasks();
        if (CollectionUtils.isEmpty(tasks)) {
            log.info("当前没有任务");
            return;
        }

        Manager manager = new Manager();
        manager.setPath(sourcePath);
        manager.setRootPackage(rootPackage);
        manager.setSrcPaths(srcPaths);
        manager.setObjectFactory(objectFactory);
        manager.analyse();

        log.info(
                "分析结果"
                        + manager
        );
        try {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                log.info(
                        "开始执行任务,number:"
                                + (i + 1)
                                + ",type:"
                                + task.getClass().getSimpleName()
                                + ",task:"
                                + task
                );
                if (task instanceof GitTask) {
                    GitTask gitTask = (GitTask) task;
                    GitGenerator gitGenerator = new GitGenerator();
                    gitGenerator.setGitUrl(gitTask.getUrl());

                    gitGenerator.setGitUser(gitTask.getUser());
                    gitGenerator.setGetPassword(gitTask.getPassword());
                    gitGenerator.setGitEmail(gitTask.getAuthorEmail());
                    gitGenerator.setGitName(gitTask.getAuthorName());

                    gitGenerator.setGenerator((AbstractFileGenerator) createGenerator(manager, gitTask.getTask()));

                    gitGenerator.setSrcUri(gitTask.getSrcUri());
                    gitGenerator.setGitBranch(gitTask.getBranch());
                    gitGenerator.setDeleteUris(gitTask.getDeleteUris());
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
            JavaClientGeneratorAbstract generator = new JavaClientGeneratorAbstract();

            if (CollectionUtils.isNotEmpty(task.getFilterList())) {
                generator.setFilterList(task.getFilterList());
            }

            generator.setOutPath(javaClientTask.getOutPath());
            generator.setApiNameMaper(new PatternNameMaper(
                    javaClientTask.getNameMaperSource(), javaClientTask.getNameMaperDist()
            ));
            generator.setRootPackage(javaClientTask.getRootPackage());
            return generator;
        } else if (task instanceof JavaScriptTask) {
            JavaScriptTask javaScriptTask = (JavaScriptTask) task;
            JavaScriptGeneratorAbstract generator = new JavaScriptGeneratorAbstract("test");

            if (CollectionUtils.isNotEmpty(task.getFilterList())) {
                generator.setFilterList(task.getFilterList());
            }

            generator.setType(javaScriptTask.getType());
            generator.setOutPath(javaScriptTask.getOutPath());
            generator.setJsPackageName(javaScriptTask.getJsPackageName());
            generator.setVersion("2");
            generator.setApiNameMaper(new PatternNameMaper(
                    javaScriptTask.getNameMaperSource(), javaScriptTask.getNameMaperDist()
            ));
            return generator;
        } else {
            throw new RuntimeException("错误的额任务类型:" + task.getClass() + ",task:" + task);
        }
    }

    private static ObjectFactory objectFactory = new ObjectFactory() {
        @Override
        public ApiAnalyse createApiAnalyse() {
            return new ClassPathApiAnalyse();
        }

        @Override
        public MessageAnalyse createMessageAnalyse() {
            return new ClassPathMessageAnalyse();
        }

        @Override
        public EnumAnalyse createEnumAnalyse() {
            return new ClassPathEnumAnalyse();
        }

        @Override
        public Context createContext() {
            return new Context();
        }
    };
}

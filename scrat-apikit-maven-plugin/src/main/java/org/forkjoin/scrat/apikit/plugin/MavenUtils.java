package org.forkjoin.scrat.apikit.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.forkjoin.scrat.apikit.plugin.bean.GitInfo;
import org.forkjoin.scrat.apikit.plugin.bean.Group;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MavenUtils {
    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public static void generate(MavenProject project, Group group, String sourcePath, String[] srcPaths, GitInfo git) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader loader = getUrlClassLoader(project);
            @SuppressWarnings("unchecked")
            Class<GenerateUtils> generateUtilsClass = (Class<GenerateUtils>) loader
                    .loadClass(GenerateUtils.class.getName());

            Method generateMethod = generateUtilsClass.getMethod(
                    "generate",
                    String.class,
                    String.class,
                    String.class,
                    String[].class
            );

            String groupJson = serialize(group);
            String gitJson = (git == null ? null : serialize(git));

            /*
             * 切换后续操作的classLoad加载器
             */
            Thread.currentThread().setContextClassLoader(loader);
            generateMethod.invoke(null, groupJson, gitJson, sourcePath, srcPaths);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //还原
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
//        generateMethod.invoke(null, group, sourcePath, getLog());
    }


    public final static String serialize(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private static URLClassLoader getUrlClassLoader(MavenProject project) throws DependencyResolutionRequiredException, DependencyTreeBuilderException {
        List<String> compileClasspathElements = project.getCompileClasspathElements();
        List<String> systemClasspathElements = project.getSystemClasspathElements();
        ClassRealm classLoader = (ClassRealm) MavenUtils.class.getClassLoader();
//
        Optional<ClassRealm> apiClassRealm = classLoader.getImportRealms().stream().filter((p) -> p.getId().equals("maven.api")).findFirst();

        URL[] urls = Stream.concat(
                Stream.concat(
                        compileClasspathElements.stream().map(MavenUtils::toUrl),
                        systemClasspathElements.stream().map(MavenUtils::toUrl)
                ),
                Stream.of(classLoader.getURLs())
        ).toArray(URL[]::new);


        SystemStreamLog log = new SystemStreamLog();
        log.info("urls:");
        log.info(Arrays.toString(urls));

        return apiClassRealm
                .map(classRealm -> new URLClassLoader(urls, classRealm))
                .orElseGet(() -> new URLClassLoader(urls));
    }


    private static URL toUrl(String spec) {
        try {
            return Paths.get(spec).toUri().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}

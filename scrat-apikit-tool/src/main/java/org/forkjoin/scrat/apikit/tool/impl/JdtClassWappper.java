package org.forkjoin.scrat.apikit.tool.impl;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.info.ImportsInfo;
import org.forkjoin.scrat.apikit.tool.info.JavadocInfo;
import org.forkjoin.scrat.apikit.tool.info.PropertyInfo;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/12/8.
 */
public class JdtClassWappper {
    protected CompilationUnit node;
    protected TypeDeclaration type;

    private Map<String, Tuple2<Long, FieldDeclaration>> filedMap;
    private Map<String, Tuple2<Long, MethodDeclaration>> methodMap;

    protected ImportsInfo importsInfo = new ImportsInfo();
    private List<String> typeParameters = new ArrayList<>();

    public JdtClassWappper(String path, Class cls) throws IOException {
        this(Paths.get(path, (cls.getPackage().getName()).split("\\.")).resolve(cls.getSimpleName() + ".java"), cls);
    }

    public JdtClassWappper(Path javaFilePath, Class cls) throws IOException {
        String code = IOUtils.toString(javaFilePath.toUri(), "UTF-8");
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8); //or newer version


        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(code.toCharArray());
        parser.setCompilerOptions(options);
        CompilationUnit node = (CompilationUnit) parser.createAST(null);

        Optional first = node
                .types()
                .stream()
                .filter(this::isPublicType)
                .findFirst();

        if (!first.isPresent()) {
            throw new AnalyseException("未找到主类");
        }

        TypeDeclaration type = (TypeDeclaration) first.get();
        this.node = node;
        this.type = type;

        filedMap = Flux.just(type.getFields()).index()
                .collect(Collectors.toMap(f -> f.getT2().fragments().get(0).toString(), r -> r))
                .block();


        methodMap = Flux.just(type.getMethods()).index()
                .collect(Collectors.toMap(m -> m.getT2().getName().getIdentifier(), r -> r))
                .block();

    }

    private boolean isPublicType(Object t) {
        return ((t instanceof TypeDeclaration) || (t instanceof EnumDeclaration)) && Modifier.isPublic(((AbstractTypeDeclaration) t).getModifiers());
    }

    public static Optional<JdtClassWappper> check(Class cls, String path) throws IOException {
        Path javaFilePath = Paths.get(path, (cls.getPackage().getName()).split("\\.")).resolve(cls.getSimpleName() + ".java");
        if (Files.exists(javaFilePath)) {
            return Optional.of(new JdtClassWappper(javaFilePath, cls));
        }
        return Optional.empty();
    }


    public JavadocInfo getMethodComment(String name) {
        if (methodMap.containsKey(name)) {
            MethodDeclaration methodDeclaration = methodMap.get(name).getT2();
            return transform(methodDeclaration.getJavadoc());
        }
        return null;
    }

    public void sort(ArrayList<PropertyInfo> properties) {
        properties.sort(this::com);
    }

    private int com(PropertyInfo o1, PropertyInfo o2) {
        long p1 = getPriority(o1);
        long p2 = getPriority(o2);
        if (p1 == 0 || p2 == 0) {
            return o2.getName().compareTo(o1.getName());
        }
        return Long.compare(p2, p1);
    }

    private long getPriority(PropertyInfo propertyInfo) {
        long priority = 0;
        Tuple2<Long, FieldDeclaration> fieldTuple2 = filedMap.get(propertyInfo.getName());
        Tuple2<Long, MethodDeclaration> methodTuple2;
        if (fieldTuple2 != null) {
            long l = (0xFF - (fieldTuple2.getT1() & 0xFF));
            priority = priority | l << 48;
        } else if ((methodTuple2 = methodMap.get(propertyInfo.getWriteName())) != null) {
            long l = (0xFF - (methodTuple2.getT1() & 0xFF));
            priority = priority | l << 32;
        } else if ((methodTuple2 = methodMap.get(propertyInfo.getReadName())) != null) {
            long l = (0xFF - (methodTuple2.getT1() & 0xFF));
            priority = priority | l << 16;
        }
        return priority;
    }

//
//    public JavadocInfo getFieldComment(String name) {
////        Optional<FieldDeclaration> methodOpt = Arrays
////                .stream(((TypeDeclaration)type).getFields())
//
//
////        ArrayList<PropertyInfo> properties
//    }

    public JavadocInfo getFieldComment(String name) {
        Tuple2<Long, FieldDeclaration> fieldDeclarationTuple2 = filedMap.get(name);
        if (fieldDeclarationTuple2 == null) {
            return null;
        }
        FieldDeclaration fieldDeclaration = fieldDeclarationTuple2.getT2();
        return transform(fieldDeclaration.getJavadoc());
    }

    public JavadocInfo getClassComment() {
        return transform(type.getJavadoc());
    }

    protected static JavadocInfo transform(Javadoc javadoc) {
        if (javadoc == null) {
            return null;
        }
        JavadocInfo javadocInfo = new JavadocInfo();
        List tags = javadoc.tags();
        for (Object tag : tags) {
            TagElement tagElement = (TagElement) tag;
            String tagName = tagElement.getTagName();

            List fragments = tagElement.fragments();
            ArrayList<String> fragmentsInfo = new ArrayList<>();
            for (Object fragment : fragments) {
                if (fragment instanceof TextElement) {
                    TextElement textElement = (TextElement) fragment;
                    fragmentsInfo.add(textElement.getText());
                } else {
                    fragmentsInfo.add(fragment.toString());
                }
            }
            javadocInfo.add(tagName, fragmentsInfo);
        }
        return javadocInfo;
    }


}

package org.forkjoin.scrat.apikit.tool.impl;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.info.ImportsInfo;
import org.forkjoin.scrat.apikit.tool.info.JavadocInfo;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zuoge85 on 15/12/8.
 */
public class JdtClassWappper {
    protected CompilationUnit node;
    protected TypeDeclaration type;

    protected ImportsInfo importsInfo = new ImportsInfo();
    private List<String> typeParameters = new ArrayList<>();

    public JdtClassWappper(String path, Class cls) throws IOException {
        this(Paths.get(path, (cls.getPackage().getName()).split("\\.")).resolve(cls.getSimpleName() + ".java"), cls);
    }

    public JdtClassWappper(Path javaFilePath, Class cls) throws IOException {
        String code = IOUtils.toString(javaFilePath.toUri(), "UTF-8");

        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(code.toCharArray());
        CompilationUnit node = (CompilationUnit) parser.createAST(null);

        Optional first = node
                .types()
                .stream()
                .filter(t -> {
                    TypeDeclaration type = (TypeDeclaration) t;
                    return Modifier.isPublic(type.getModifiers());
                })
                .findFirst();

        if (!first.isPresent()) {
            throw new AnalyseException("未找到主类");
        }

        TypeDeclaration type = (TypeDeclaration) first.get();
        this.node = node;
        this.type = type;
    }

    public static Optional<JdtClassWappper> check(Class cls, String path) throws IOException {
        Path javaFilePath = Paths.get(path, (cls.getPackage().getName()).split("\\.")).resolve(cls.getSimpleName() + ".java");
        if (Files.exists(javaFilePath)) {
            return Optional.of(new JdtClassWappper(javaFilePath, cls));
        }
        return Optional.empty();
    }


    public JavadocInfo getMethodComment(String name) {
        Optional<MethodDeclaration> methodOpt = Arrays
                .stream(type.getMethods())
                .filter(methodDeclaration -> Objects.equals(methodDeclaration.getName().getIdentifier(), name))
                .findFirst();

        if (!methodOpt.isPresent()) {
            throw new AnalyseException("没有在源文件中找到方法:" + name);
        }

        MethodDeclaration methodDeclaration = methodOpt.get();
        return transform(methodDeclaration.getJavadoc());
    }

    public JavadocInfo getFieldComment(String name) {
        Optional<FieldDeclaration> methodOpt = Arrays
                .stream(type.getFields())
                .filter(fieldDeclaration -> Objects.equals(fieldDeclaration.fragments().get(0).toString(), name))
                .findFirst();

        if (methodOpt.isPresent()) {
            FieldDeclaration fieldDeclaration = methodOpt.get();
            return transform(fieldDeclaration.getJavadoc());
        } else {
            return null;
        }
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

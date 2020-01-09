package org.forkjoin.scrat.apikit.tool.impl;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.info.ImportsInfo;
import org.forkjoin.scrat.apikit.tool.info.JavadocInfo;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author zuoge85 on 15/12/8.
 */
public class JdtClassWappper {
    protected CompilationUnit node;
    protected AbstractTypeDeclaration type;

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

        AbstractTypeDeclaration type = (AbstractTypeDeclaration) first.get();
        this.node = node;
        this.type = type;
    }

    private boolean isPublicType(Object t) {
        return ((t instanceof TypeDeclaration) || (t instanceof EnumDeclaration)) && Modifier.isPublic(((AbstractTypeDeclaration)t).getModifiers());
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
                .stream(((TypeDeclaration)type).getMethods())
                .filter(methodDeclaration -> Objects.equals(methodDeclaration.getName().getIdentifier(), name))
                .findFirst();

        if (!methodOpt.isPresent()) {
            return null;
        }

        MethodDeclaration methodDeclaration = methodOpt.get();
        return transform(methodDeclaration.getJavadoc());
    }

    public JavadocInfo getFieldComment(String name) {
        Optional<FieldDeclaration> methodOpt = Arrays
                .stream(((TypeDeclaration)type).getFields())
                .filter(fieldDeclaration -> Objects.equals(fieldDeclaration.fragments().get(0).toString(), name))
                .findFirst();

        if (methodOpt.isPresent()) {
            FieldDeclaration fieldDeclaration = methodOpt.get();
            return transform(fieldDeclaration.getJavadoc());
        } else {
            return null;
        }
    }

    public JavadocInfo getEnumElementComment(String name) {
        EnumDeclaration type = (EnumDeclaration) this.type;
        List<EnumConstantDeclaration> list = (List<EnumConstantDeclaration>)type.enumConstants();
        Optional<EnumConstantDeclaration> methodOpt = list
                .stream()
                .filter(enumConstantDeclaration -> Objects.equals(enumConstantDeclaration.getName().getFullyQualifiedName(), name))
                .findFirst();

        if (methodOpt.isPresent()) {
            EnumConstantDeclaration enumConstantDeclaration = methodOpt.get();
            return transform(enumConstantDeclaration.getJavadoc());
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

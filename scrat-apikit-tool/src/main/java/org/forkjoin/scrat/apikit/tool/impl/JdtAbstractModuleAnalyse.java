package org.forkjoin.scrat.apikit.tool.impl;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.forkjoin.scrat.apikit.tool.info.AnnotationInfo;
import org.forkjoin.scrat.apikit.tool.info.FieldInfo;
import org.forkjoin.scrat.apikit.tool.info.JavadocInfo;
import org.forkjoin.scrat.apikit.tool.info.ModuleInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zuoge85 on 15/11/16.
 */
public abstract class JdtAbstractModuleAnalyse {
    protected JdtInfo jdtInfo;

    public JdtAbstractModuleAnalyse(JdtInfo jdtInfo) {
        this.jdtInfo = jdtInfo;
    }

    protected void initModuleInfo(ModuleInfo moduleInfo) {
        Javadoc javadoc = jdtInfo.getType().getJavadoc();

        moduleInfo.init(
                jdtInfo.getName(),
                jdtInfo.getPackageName(),
                transform(javadoc)
        );
    }

    protected AnnotationInfo transform(Annotation annotation) {
        TypeInfo typeInfo = jdtInfo.analyseType(annotation.getTypeName());
        AnnotationInfo annotationInfo = new AnnotationInfo(typeInfo);
        if (annotation instanceof NormalAnnotation) {
            NormalAnnotation normalAnnotation = (NormalAnnotation) annotation;
            List values = normalAnnotation.values();
            for (Object obj : values) {
                annotationInfo.addArgs(obj.toString());
            }
        } else if (annotation instanceof SingleMemberAnnotation) {
            SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation) annotation;
            annotationInfo.addArgs(singleMemberAnnotation.getValue().toString());
        }
        return annotationInfo;
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

    protected void transformAnnotations(FieldInfo fieldInfo, List modifiers) {
        for (Object o : modifiers) {
            if (o instanceof Annotation) {
                Annotation annotation = (Annotation) o;

                AnnotationInfo annotationInfo = transform(annotation);
                fieldInfo.addAnnotation(annotationInfo);
            }
        }
    }

    public abstract ModuleInfo analyse();

    public boolean equalsType(Name typeName, Class<?> apiMethodClass) {
        String fullTypeName = jdtInfo.getFullTypeName(typeName);
        return apiMethodClass.getName().equals(fullTypeName);
    }
}

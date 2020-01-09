package org.forkjoin.scrat.apikit.tool.utils;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import java.util.Map;

/**
 * @author zuoge85 on 15/6/16.
 */
public class JavaFileFormat {

    public static final CodeFormatter CODE_FORMATTER = createCodeFormatter();

    @SuppressWarnings("unchecked")
    private static CodeFormatter createCodeFormatter() {
        Map options = JavaCore.getOptions();
        JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
        options.put("org.eclipse.jdt.core.formatter.lineSplit", "120");
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, "1.8");
        options.put(JavaCore.COMPILER_COMPLIANCE, "1.8");
        options.put(JavaCore.COMPILER_SOURCE, "1.8");
        options.put(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
//        options.put(DefaultCodeFormatterConstants.FORMATTER_BRACE_POSITION_FOR_METHOD_DECLARATION,
//                DefaultCodeFormatterConstants.NEXT_LINE);
        return ToolFactory.createCodeFormatter(options);
    }

    public synchronized static String formatCode(String contents) {
        IDocument doc = new Document(contents);
        int kind = CodeFormatter.K_COMPILATION_UNIT;

        TextEdit edit = CODE_FORMATTER.format(
                kind, doc.get(), 0, doc.get()
                        .length(), 0, null);


        if (edit != null) {
            try {
                edit.apply(doc);
                contents = doc.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return contents;
    }
}

package org.forkjoin.scrat.apikit.tool;


import org.forkjoin.scrat.apikit.tool.info.TypeInfo;

import java.util.Set;

/**
 * 代码分析器
 * @author zuoge85 on 15/11/8.
 */
public interface EnumAnalyse {
    void analyse(Context context, Set<TypeInfo> enumTypes);
}

package org.forkjoin.scrat.apikit.tool;

/**
 * 生成器
 *
 * @author zuoge85 on 15/11/8.
 */
public interface Generator {
    void generate(Context context) throws Exception;
//    String getOutPath();

    void setVersion(String version);
}

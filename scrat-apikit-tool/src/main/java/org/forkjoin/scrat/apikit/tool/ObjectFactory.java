package org.forkjoin.scrat.apikit.tool;

/**
 * @author zuoge85 on 15/11/15.
 */
public interface ObjectFactory {
    ApiAnalyse createApiAnalyse();
    MessageAnalyse createMessageAnalyse();
    EnumAnalyse createEnumAnalyse();
    Context createContext();
}

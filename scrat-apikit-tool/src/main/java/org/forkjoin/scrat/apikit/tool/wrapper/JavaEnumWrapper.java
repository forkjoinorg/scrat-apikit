package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.forkjoin.scrat.apikit.tool.AnalyseException;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.Utils;
import org.forkjoin.scrat.apikit.tool.info.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JavaEnumWrapper extends JavaWrapper<EnumInfo> {
    private boolean isAnnotations = false;


    public JavaEnumWrapper(Context context, EnumInfo enumInfo, String rootPackage) {
        super(context, enumInfo, rootPackage);
    }

    @Override
    public String formatAnnotations(List<AnnotationInfo> annotations, String start) {
        return null;
    }

    @Override
    public void init() {

    }

}

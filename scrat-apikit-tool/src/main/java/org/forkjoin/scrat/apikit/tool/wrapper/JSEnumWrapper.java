package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JSEnumWrapper extends JSWrapper<EnumInfo> {
    public JSEnumWrapper(Context context, EnumInfo enumInfo, String rootPackage) {
        super(context, enumInfo, rootPackage);
    }

    @Override
    public void init() {
        super.init();
    }

}

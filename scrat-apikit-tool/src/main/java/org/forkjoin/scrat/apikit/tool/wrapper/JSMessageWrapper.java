package org.forkjoin.scrat.apikit.tool.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.forkjoin.scrat.apikit.tool.Context;
import org.forkjoin.scrat.apikit.tool.info.FieldInfo;
import org.forkjoin.scrat.apikit.tool.info.MessageInfo;
import org.forkjoin.scrat.apikit.tool.info.PropertyInfo;
import org.forkjoin.scrat.apikit.tool.info.TypeInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author zuoge85 on 15/6/14.
 */
public class JSMessageWrapper extends JSWrapper<MessageInfo> {
    public JSMessageWrapper(Context context, MessageInfo messageInfo, String rootPackage) {
        super(context, messageInfo, rootPackage);
    }

    @Override
    public void init() {
        super.init();
    }

    private Flux<PropertyInfo> getUpper() {
        return Mono
                .justOrEmpty(moduleInfo.getSuperType())
                .map(TypeInfo::getFullName)
                .filter(fullName -> context.getMessageWrapper(fullName)!=null)
                .map(fullName -> context.getMessageWrapper(fullName))
                .flatMapMany(w -> {
                    TypeInfo superType = w.getModuleInfo().getSuperType();
                    Flux<PropertyInfo> flux = Flux.fromIterable(w.getModuleInfo().getProperties());
                    if (superType != null) {
                        flux.mergeWith(getUpper());
                    }
                    return flux;
                });
    }

    public List<PropertyInfo> getProperties() {
        return Flux
                .fromIterable(moduleInfo.getProperties())
                .mergeWith(getUpper())
                .distinct(FieldInfo::getName)
                .collectList()
                .block();
    }

    public String getImports() {
        StringBuilder sb = new StringBuilder();
        Flux
                .fromIterable(moduleInfo.getProperties())
                .mergeWith(getUpper())
                .distinct(FieldInfo::getName)
                .map(FieldInfo::getTypeInfo)
                .flatMapIterable(type -> {
                    List<TypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    if (moduleInfo.getSuperType() != null) {
                        findTypes(moduleInfo.getSuperType(), types);
                    }
                    return types;
                })
                .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .map(TypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
                .filter(fullName -> context.getMessageWrapper(fullName) != null)
                .map(fullName -> context.getMessageWrapper(fullName))
                .doOnNext(r -> {
                    String distPackage = getDistPackage();
                    String proTypeName = r.getDistName();
                    if (r.getDistPackage().equals(distPackage)) {
                        sb.append("import ")
                                .append(proTypeName)
                                .append(" from './").append(proTypeName).append("'\n");
                    } else {
                        int level = distPackage.split("\\.").length;
                        sb.append("import ")
                                .append(proTypeName)
                                .append(" from '")
                                .append(StringUtils.repeat("../", level))
                                .append(r.getDistPackage())
                                .append("/")
                                .append(proTypeName)
                                .append("'\n");
                    }
                })
                .collectList()
                .block();

        return sb.toString();
    }


//    @Override
//    public String getImports() {
//        Set<String> containsSet = new HashSet<>();
//
//        StringBuilder sb = new StringBuilder();
//        //自己的目录级别
//        int i = 0;
//        for (PropertyInfo pro : moduleInfo.getProperties()) {
//            if (pro.getTypeInfo().isInside()) {
//                if (moduleInfo.getPackageName().equals(pro.getTypeInfo().getPackageName())) {
//                    if (i == 0) {
//                        sb.append("\n\n");
//                    }
//                    String proTypeName = pro.getTypeInfo().getName();
//                    if (!this.getName().equals(proTypeName)) {
//                        if (!containsSet.contains(proTypeName)) {
//                            containsSet.add(proTypeName);
//                            sb.append("import ")
//                                    .append(proTypeName)
//                                    .append(" from './").append(proTypeName).append("'\n");
//                            i++;
//                        }
//                    }
//                } else {
//                    if (i == 0) {
//                        sb.append("\n\n");
//                    }
//                    String proTypeName = pro.getTypeInfo().getName();
//                    int level = moduleInfo.getPackageName().split("\\.").length;
//                    if (!this.getName().equals(proTypeName)) {
//                        if (!containsSet.contains(proTypeName)) {
//                            containsSet.add(proTypeName);
//                            sb.append("import ")
//                                    .append(proTypeName)
//                                    .append(" from '")
//                                    .append(StringUtils.repeat("../", level))
//                                    .append(pro.getTypeInfo().getPackageName())
//                                    .append("/")
//                                    .append(proTypeName)
//                                    .append("'\n");
//                            i++;
//                        }
//                    }
//                }
//            }
//        }
//        return sb.toString();
//    }

    public String toObjectArgs() {
        StringBuilder sb = new StringBuilder();
        ArrayList<PropertyInfo> properties = moduleInfo.getProperties();
        for (int i = 0; i < properties.size(); i++) {
            PropertyInfo info = properties.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append(info.getName());
        }
        return sb.toString();
    }

    public String toObjectArgsType() {
        StringBuilder sb = new StringBuilder();
        ArrayList<PropertyInfo> properties = moduleInfo.getProperties();
        for (int i = 0; i < properties.size(); i++) {
            PropertyInfo info = properties.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append(info.getName());
            sb.append(":");
            sb.append(toTypeString(info.getTypeInfo()));
        }
        return sb.toString();
    }
}

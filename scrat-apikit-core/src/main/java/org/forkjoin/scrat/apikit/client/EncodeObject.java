package org.forkjoin.scrat.apikit.client;

import java.util.List;
import java.util.Map;

public interface EncodeObject {
    List<Map.Entry<String, Object>> encode(String $parent, List<Map.Entry<String, Object>> $list);
}

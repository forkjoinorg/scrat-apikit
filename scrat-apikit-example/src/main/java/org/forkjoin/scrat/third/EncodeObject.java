package org.forkjoin.scrat.third;

import lombok.Data;
import org.forkjoin.scrat.apikit.client.EncodeUtils;

import java.util.List;
import java.util.Map;

@Data
public class EncodeObject {
    private String title;
    public void encode(String $parent, List<Map.Entry<String, Object>> $list) {
        EncodeUtils.encode($list, $parent + "title", title);
    }
}

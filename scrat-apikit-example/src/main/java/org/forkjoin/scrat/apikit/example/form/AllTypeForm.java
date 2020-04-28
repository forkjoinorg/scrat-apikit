package org.forkjoin.scrat.apikit.example.form;

import lombok.Data;
import org.forkjoin.scrat.apikit.example.type.StatusType;
import org.forkjoin.scrat.third.EncodeObject;
import org.forkjoin.scrat.third.SysObject;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class AllTypeForm {
    private int intValue;
    private Integer integerValue;
    private String stringValue;
    private LocalDateTime localDateTime;
    private BaseForm objectForm;
    private PageRequest pageRequest;
    private StatusType statusType;
    private EncodeObject encodeObject;
    private SysObject sysObject;
//
//
    private int[] intValues;
    private Integer[] integerValues;
    private String[] stringValues;
    private LocalDateTime[] localDateTimes;
    private BaseForm[] objectForms;
    private PageRequest[] pageRequests;
    private StatusType[] statusTypes;
    private EncodeObject[] encodeObjects;
    private SysObject[] sysObjects;
//
    private List<Integer> integerValueList;
    private List<String> stringValueList;
    private List<LocalDateTime> localDateTimeList;
    private List<BaseForm> objectFormList;
    private List<PageRequest> pageRequestList;
    private List<StatusType> statusTypesList;
    private List<EncodeObject> encodeObjectList;
    private List<SysObject> sysObjectList;
//
    private Map<String,String> stringMap;
    private Map<String,BaseForm> baseFormMap;
    private Map<String,Integer> integerMap;
    private Map<String,StatusType> stringStatusTypeMap;
    private Map<StatusType,StatusType> statusTypeStatusTypeMap;
    private Map<String,EncodeObject> stringEncodeObjectMap;
    private Map<String,SysObject> stringSysObjectMap;


    private Map<String,List<EncodeObject>> stringListEncodeObjectMap;
    private Map<String,List<SysObject>> stringListSysObjectMap;

    private Map<String,EncodeObject[]> stringsEncodeObjectMap;
    private Map<String,SysObject[]> stringsSysObjectMap;


    private List<Map<String, EncodeObject>> listStringEncodeObject;
    private List<Map<String, SysObject>> listStringSysObject;

    private List<List<Map<String, Map<String, EncodeObject>>>> listListStringEncodeObject;
    private List<List<Map<String, Map<String, SysObject>>>> listListStringSysObject;

    private SysObject[] sb;

    public int getInt1(){
        return 0;
    }

    public int getInt2(){
        return 0;
    }

    public int getInt3(){
        return 0;
    }
}

package org.forkjoin.scrat.apikit.example.client.form;


import org.forkjoin.scrat.apikit.example.client.type.StatusType;
import org.springframework.data.domain.PageRequest;

import org.forkjoin.scrat.apikit.client.*;

import java.time.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;

import java.util.concurrent.atomic.*;

/**
 *
 */
 class AllTypeForm1 {
    private Map<String, BaseForm> baseFormMap;
    private int intValue;
    private int[] intValues;
    private Map<String, Integer> integerMap;
    private Integer integerValue;
    private List<Integer> integerValueList;
    private Integer[] integerValues;
    private LocalDateTime localDateTime;
    private List<LocalDateTime> localDateTimeList;
    private LocalDateTime[] localDateTimes;
    private BaseForm objectForm;
    private List<BaseForm> objectFormList;
    private BaseForm[] objectForms;
    private PageRequest pageRequest;
    private List<PageRequest> pageRequestList;
    private PageRequest[] pageRequests;
    private StatusType statusType;
    private Map<StatusType, StatusType> statusTypeStatusTypeMap;
    private StatusType[] statusTypes;
    private List<StatusType> statusTypesList;
    private Map<String, String> stringMap;
    private Map<String, StatusType> stringStatusTypeMap;
    private String stringValue;
    private List<String> stringValueList;
    private String[] stringValues;

    public Map<String, BaseForm> getBaseFormMap() {
        return baseFormMap;
    }

    public void setBaseFormMap(Map<String, BaseForm> baseFormMap) {
        this.baseFormMap = baseFormMap;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public int[] getIntValues() {
        return intValues;
    }

    public void setIntValues(int[] intValues) {
        this.intValues = intValues;
    }

    public Map<String, Integer> getIntegerMap() {
        return integerMap;
    }

    public void setIntegerMap(Map<String, Integer> integerMap) {
        this.integerMap = integerMap;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public List<Integer> getIntegerValueList() {
        return integerValueList;
    }

    public void setIntegerValueList(List<Integer> integerValueList) {
        this.integerValueList = integerValueList;
    }

    public Integer[] getIntegerValues() {
        return integerValues;
    }

    public void setIntegerValues(Integer[] integerValues) {
        this.integerValues = integerValues;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<LocalDateTime> getLocalDateTimeList() {
        return localDateTimeList;
    }

    public void setLocalDateTimeList(List<LocalDateTime> localDateTimeList) {
        this.localDateTimeList = localDateTimeList;
    }

    public LocalDateTime[] getLocalDateTimes() {
        return localDateTimes;
    }

    public void setLocalDateTimes(LocalDateTime[] localDateTimes) {
        this.localDateTimes = localDateTimes;
    }

    public BaseForm getObjectForm() {
        return objectForm;
    }

    public void setObjectForm(BaseForm objectForm) {
        this.objectForm = objectForm;
    }

    public List<BaseForm> getObjectFormList() {
        return objectFormList;
    }

    public void setObjectFormList(List<BaseForm> objectFormList) {
        this.objectFormList = objectFormList;
    }

    public BaseForm[] getObjectForms() {
        return objectForms;
    }

    public void setObjectForms(BaseForm[] objectForms) {
        this.objectForms = objectForms;
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public List<PageRequest> getPageRequestList() {
        return pageRequestList;
    }

    public void setPageRequestList(List<PageRequest> pageRequestList) {
        this.pageRequestList = pageRequestList;
    }

    public PageRequest[] getPageRequests() {
        return pageRequests;
    }

    public void setPageRequests(PageRequest[] pageRequests) {
        this.pageRequests = pageRequests;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Map<StatusType, StatusType> getStatusTypeStatusTypeMap() {
        return statusTypeStatusTypeMap;
    }

    public void setStatusTypeStatusTypeMap(Map<StatusType, StatusType> statusTypeStatusTypeMap) {
        this.statusTypeStatusTypeMap = statusTypeStatusTypeMap;
    }

    public StatusType[] getStatusTypes() {
        return statusTypes;
    }

    public void setStatusTypes(StatusType[] statusTypes) {
        this.statusTypes = statusTypes;
    }

    public List<StatusType> getStatusTypesList() {
        return statusTypesList;
    }

    public void setStatusTypesList(List<StatusType> statusTypesList) {
        this.statusTypesList = statusTypesList;
    }

    public Map<String, String> getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public Map<String, StatusType> getStringStatusTypeMap() {
        return stringStatusTypeMap;
    }

    public void setStringStatusTypeMap(Map<String, StatusType> stringStatusTypeMap) {
        this.stringStatusTypeMap = stringStatusTypeMap;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public List<String> getStringValueList() {
        return stringValueList;
    }

    public void setStringValueList(List<String> stringValueList) {
        this.stringValueList = stringValueList;
    }

    public String[] getStringValues() {
        return stringValues;
    }

    public void setStringValues(String[] stringValues) {
        this.stringValues = stringValues;
    }

    public List<Entry<String, Object>> encode(String $parent, List<Entry<String, Object>> $list) {

        if (baseFormMap != null) {
            baseFormMap.forEach(($k, $v) -> {
                if (baseFormMap != null) {
                    EncodeUtils.encode($list, $parent + "baseFormMap." + $k, $v::encode);
                }
            });
        }
        EncodeUtils.encode($list, $parent + "intValue", intValue);

        EncodeUtils.encodeSingle($list, $parent + "intValues", intValues);

        if (integerMap != null) {
            integerMap.forEach(($k, $v) -> {
                EncodeUtils.encode($list, $parent + "integerMap." + $k, $v);
            });
        }
        EncodeUtils.encode($list, $parent + "integerValue", integerValue);

        EncodeUtils.encodeSingle($list, $parent + "integerValueList", integerValueList);

        EncodeUtils.encodeSingle($list, $parent + "integerValues", integerValues);

        EncodeUtils.encode($list, $parent + "localDateTime", localDateTime);

        EncodeUtils.encodeSingle($list, $parent + "localDateTimeList", localDateTimeList);

        EncodeUtils.encodeSingle($list, $parent + "localDateTimes", localDateTimes);

        if (objectForm != null) {
            EncodeUtils.encode($list, $parent + "objectForm.", objectForm::encode);
        }

        if (objectFormList != null && !objectFormList.isEmpty()) {
            AtomicInteger $i = new AtomicInteger();
            objectFormList.forEach($objectFormListItem -> {
                EncodeUtils.encode($list, $parent + "objectFormList" + "[" + $i.getAndIncrement() + "].",
                        $objectFormListItem::encode);
            });
        }

        if (objectForms != null && objectForms.length > 0) {
            for (int $i = 0; $i < objectForms.length; $i++) {
                EncodeUtils.encode($list, $parent + "objectForms" + "[" + $i + "].", objectForms[$i]::encode);
            }
        }

        EncodeUtils.encodeSingle($list, $parent + "pageRequest", pageRequest);

        if (pageRequestList != null && !pageRequestList.isEmpty()) {
            AtomicInteger $i = new AtomicInteger();
            pageRequestList.forEach($pageRequestListItem -> {
//                EncodeUtils.encode($list, $parent + "pageRequestList" + "[" + $i.getAndIncrement() + "].",
//                        $pageRequestListItem::encode);
            });
        }

        EncodeUtils.encodeSingle($list, $parent + "pageRequests", pageRequests);

        EncodeUtils.encode($list, $parent + "statusType", statusType);

        if (statusTypeStatusTypeMap != null) {
            statusTypeStatusTypeMap.forEach(($k, $v) -> {
                EncodeUtils.encode($list, $parent + "statusTypeStatusTypeMap." + $k, $v);
            });
        }
        EncodeUtils.encodeSingle($list, $parent + "statusTypes", statusTypes);

        EncodeUtils.encodeSingle($list, $parent + "statusTypesList", statusTypesList);

        if (stringMap != null) {
            stringMap.forEach(($k, $v) -> {
                EncodeUtils.encode($list, $parent + "stringMap." + $k, $v);
            });
        }
        if (stringStatusTypeMap != null) {
            stringStatusTypeMap.forEach(($k, $v) -> {
                EncodeUtils.encode($list, $parent + "stringStatusTypeMap." + $k, $v);
            });
        }
        EncodeUtils.encode($list, $parent + "stringValue", stringValue);

        EncodeUtils.encodeSingle($list, $parent + "stringValueList", stringValueList);

        EncodeUtils.encodeSingle($list, $parent + "stringValues", stringValues);
        return $list;
    }

    @Override
    public String toString() {
        return "AllTypeForm [baseFormMap=" + baseFormMap + ",intValue=" + intValue + ",intValues=length:"
                + (intValues == null ? -1 : intValues.length) + ",integerMap=" + integerMap + ",integerValue="
                + integerValue + ",integerValueList=size:" + (integerValueList == null ? -1 : integerValueList.size())
                + ",integerValues=length:" + (integerValues == null ? -1 : integerValues.length) + ",localDateTime="
                + localDateTime + ",localDateTimeList=size:"
                + (localDateTimeList == null ? -1 : localDateTimeList.size()) + ",localDateTimes=length:"
                + (localDateTimes == null ? -1 : localDateTimes.length) + ",objectForm=" + objectForm
                + ",objectFormList=size:" + (objectFormList == null ? -1 : objectFormList.size())
                + ",objectForms=length:" + (objectForms == null ? -1 : objectForms.length) + ",pageRequest="
                + pageRequest + ",pageRequestList=size:" + (pageRequestList == null ? -1 : pageRequestList.size())
                + ",pageRequests=length:" + (pageRequests == null ? -1 : pageRequests.length) + ",statusType="
                + statusType + ",statusTypeStatusTypeMap=" + statusTypeStatusTypeMap + ",statusTypes=length:"
                + (statusTypes == null ? -1 : statusTypes.length) + ",statusTypesList=size:"
                + (statusTypesList == null ? -1 : statusTypesList.size()) + ",stringMap=" + stringMap
                + ",stringStatusTypeMap=" + stringStatusTypeMap + ",stringValue=" + stringValue
                + ",stringValueList=size:" + (stringValueList == null ? -1 : stringValueList.size())
                + ",stringValues=length:" + (stringValues == null ? -1 : stringValues.length) + ", ]";
    }
}
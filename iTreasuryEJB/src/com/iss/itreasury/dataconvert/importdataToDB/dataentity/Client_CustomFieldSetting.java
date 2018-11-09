/*
 * Created on 2006-3-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Client_CustomFieldSetting {
    private long id=-1;
    //字段
    private long fieldId=-1;
    //自定义字段名称
    private String name="";
    //录入人
    private long inputUserId=-1;
    //录入时间
    private Timestamp inputDate=null;
    //状态
    private long statusId=-1;

    public long getFieldId() {
        return fieldId;
    }
    public void setFieldId(long fieldId) {
        this.fieldId = fieldId;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Timestamp getInputDate() {
        return inputDate;
    }
    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
    }
    public long getInputUserId() {
        return inputUserId;
    }
    public void setInputUserId(long inputUserId) {
        this.inputUserId = inputUserId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getStatusId() {
        return statusId;
    }
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
}

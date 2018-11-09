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
public class Client_ClientInfo {
    //客户id
    private long id=-1;
    //所属办事处
    private long officeId=-1;
    //客户编号
    private String code="";
    //客户中文名称
    private String name="";
    //客户中文简称
    private String simpleName="";
    //客户中文别名
    private String name2="";
    //客户英文名称
    private String engName="";
    //客户英文简称
    private String simpleEngName="";
    //客户英文别名
    private String engName2="";
    //客户基本类型
    private String clientBaseType="";
    //系统标示
    private String systemIdentify="";
    //查询密码
    private String queryPassword="";
    //所属客户经理id
    private long customerManagerUserId=-1;
    //国家
    private String country="";
    //建立关系日期
    private Timestamp inputDate=null;
    //服务级别
    private long serviceLevel=-1;
    //录入人
    private long inputUserId=-1;
    //录入时间
    private Timestamp inputTime=null;
    //修改人
    private long modifyUserId=-1;
    //修改时间
    private Timestamp modifyTime=null;
    //状态
    private long statusId=-1;
    //级别id
    private long levelId=-1;
    //级别码
    private String levelCode="";
    
    
    public String getLevelCode() {
        return levelCode;
    }
    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }
    public long getLevelId() {
        return levelId;
    }
    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public long getCustomerManagerUserId() {
        return customerManagerUserId;
    }
    public void setCustomerManagerUserId(long customerManagerUserId) {
        this.customerManagerUserId = customerManagerUserId;
    }
    public String getEngName() {
        return engName;
    }
    public void setEngName(String engName) {
        this.engName = engName;
    }
    public String getEngName2() {
        return engName2;
    }
    public void setEngName2(String engName2) {
        this.engName2 = engName2;
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
    public Timestamp getInputTime() {
        return inputTime;
    }
    public void setInputTime(Timestamp inputTime) {
        this.inputTime = inputTime;
    }
    public long getInputUserId() {
        return inputUserId;
    }
    public void setInputUserId(long inputUserId) {
        this.inputUserId = inputUserId;
    }
    public Timestamp getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }
    public long getModifyUserId() {
        return modifyUserId;
    }
    public void setModifyUserId(long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName2() {
        return name2;
    }
    public void setName2(String name2) {
        this.name2 = name2;
    }
    public long getOfficeId() {
        return officeId;
    }
    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }
    public String getQueryPassword() {
        return queryPassword;
    }
    public void setQueryPassword(String queryPassword) {
        this.queryPassword = queryPassword;
    }
    public long getServiceLevel() {
        return serviceLevel;
    }
    public void setServiceLevel(long serviceLevel) {
        this.serviceLevel = serviceLevel;
    }
    public String getSimpleEngName() {
        return simpleEngName;
    }
    public void setSimpleEngName(String simpleEngName) {
        this.simpleEngName = simpleEngName;
    }
    public String getSimpleName() {
        return simpleName;
    }
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }
    public long getStatusId() {
        return statusId;
    }
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
    public String getSystemIdentify() {
        return systemIdentify;
    }
    public void setSystemIdentify(String systemIdentify) {
        this.systemIdentify = systemIdentify;
    }
    public String getClientBaseType() {
        return clientBaseType;
    }
    public void setClientBaseType(String clientBaseType) {
        this.clientBaseType = clientBaseType;
    }
}

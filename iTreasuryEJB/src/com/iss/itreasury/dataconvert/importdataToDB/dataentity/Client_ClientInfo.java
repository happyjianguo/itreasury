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
    //�ͻ�id
    private long id=-1;
    //�������´�
    private long officeId=-1;
    //�ͻ����
    private String code="";
    //�ͻ���������
    private String name="";
    //�ͻ����ļ��
    private String simpleName="";
    //�ͻ����ı���
    private String name2="";
    //�ͻ�Ӣ������
    private String engName="";
    //�ͻ�Ӣ�ļ��
    private String simpleEngName="";
    //�ͻ�Ӣ�ı���
    private String engName2="";
    //�ͻ���������
    private String clientBaseType="";
    //ϵͳ��ʾ
    private String systemIdentify="";
    //��ѯ����
    private String queryPassword="";
    //�����ͻ�����id
    private long customerManagerUserId=-1;
    //����
    private String country="";
    //������ϵ����
    private Timestamp inputDate=null;
    //���񼶱�
    private long serviceLevel=-1;
    //¼����
    private long inputUserId=-1;
    //¼��ʱ��
    private Timestamp inputTime=null;
    //�޸���
    private long modifyUserId=-1;
    //�޸�ʱ��
    private Timestamp modifyTime=null;
    //״̬
    private long statusId=-1;
    //����id
    private long levelId=-1;
    //������
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

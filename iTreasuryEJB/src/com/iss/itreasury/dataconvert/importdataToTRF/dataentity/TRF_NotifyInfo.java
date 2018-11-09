/*
 * Created on 2005-01-04
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 */
public class TRF_NotifyInfo{
	//ID
	private long id = -1;
	//�˻���	sAccountNo	VARCHAR2(100)
	private String sAccountNo = "";
	//���´����	officeCode	VARCHAR2(100)
	private String officeCode = "";
	//����	currency VARCHAR2(100)
	private String currency = "";
	//�˻�����	accountType	VARCHAR2(100)
	private String accountType = "";
	//�ͻ���	clientCode	VARCHAR2(100)
	private String clientCode = "";
	//�˻�����	sname	VARCHAR2(100)
	private String sName = "";
	//��������	dtOpen	date
	private Timestamp dtOpen = null;
	//�廧����	dtFinish	date
	private Timestamp dtFinish = null;
	//�˻�״̬	status	VARCHAR2(100)
	private String status = "";
	//�浥��	af_SDepositNo	VARCHAR2(100)
	private String af_SDepositNo = "";
	//�浥��������	mOpenAmount	number(21,6)
	private double mOpenAmount = 0.0;
	//�浥���	mBalance	number(21,6)
	private double mBalance = 0.0;
	//��ʼ����	af_DtStart	date
	private Timestamp af_DtStart = null;
	//��������	af_DtEnd	date
	private Timestamp af_DtEnd = null;
	//֪ͨ���Ʒ�֣��죩	af_NNoticeDay	number
	private long af_NNoticeDay = -1;
	//¼����	inputUser	VARCHAR2(100)
	private String inputUser = "";
	//¼��ʱ��	dtInput	date
	private Timestamp dtInput = null;
	//������	checkUser  VARCHAR2(100)
	private String checkUser = "";
	//����ʱ��	dtCheck	date
	private Timestamp dtCheck = null;
	//����״̬	checkStatus	VARCHAR2(100)
	private String checkStatus = "";
	//��ע	sAbstract	VARCHAR2(100)
	private String sAbstract = "";
	//��Ӧ��Ŀ��	sSubject	VARCHAR2(100)
	private String sSubject = "";

    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public Timestamp getAf_DtEnd() {
        return af_DtEnd;
    }
    public void setAf_DtEnd(Timestamp af_DtEnd) {
        this.af_DtEnd = af_DtEnd;
    }
    public Timestamp getAf_DtStart() {
        return af_DtStart;
    }
    public void setAf_DtStart(Timestamp af_DtStart) {
        this.af_DtStart = af_DtStart;
    }
    public long getAf_NNoticeDay() {
        return af_NNoticeDay;
    }
    public void setAf_NNoticeDay(long af_NNoticeDay) {
        this.af_NNoticeDay = af_NNoticeDay;
    }
    public String getAf_SDepositNo() {
        return af_SDepositNo;
    }
    public void setAf_SDepositNo(String af_SDepositNo) {
        this.af_SDepositNo = af_SDepositNo;
    }
    public String getCheckStatus() {
        return checkStatus;
    }
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
    public String getCheckUser() {
        return checkUser;
    }
    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }
    public String getClientCode() {
        return clientCode;
    }
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Timestamp getDtCheck() {
        return dtCheck;
    }
    public void setDtCheck(Timestamp dtCheck) {
        this.dtCheck = dtCheck;
    }
    public Timestamp getDtFinish() {
        return dtFinish;
    }
    public void setDtFinish(Timestamp dtFinish) {
        this.dtFinish = dtFinish;
    }
    public Timestamp getDtInput() {
        return dtInput;
    }
    public void setDtInput(Timestamp dtInput) {
        this.dtInput = dtInput;
    }
    public Timestamp getDtOpen() {
        return dtOpen;
    }
    public void setDtOpen(Timestamp dtOpen) {
        this.dtOpen = dtOpen;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getInputUser() {
        return inputUser;
    }
    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }
    public double getMBalance() {
        return mBalance;
    }
    public void setMBalance(double balance) {
        mBalance = balance;
    }
    public double getMOpenAmount() {
        return mOpenAmount;
    }
    public void setMOpenAmount(double openAmount) {
        mOpenAmount = openAmount;
    }
    public String getSAbstract() {
        return sAbstract;
    }
    public void setSAbstract(String abstract1) {
        sAbstract = abstract1;
    }
    public String getSAccountNo() {
        return sAccountNo;
    }
    public void setSAccountNo(String accountNo) {
        sAccountNo = accountNo;
    }
    public String getSName() {
        return sName;
    }
    public void setSName(String name) {
        sName = name;
    }
    public String getSSubject() {
        return sSubject;
    }
    public void setSSubject(String subject) {
        sSubject = subject;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getOfficeCode() {
        return officeCode;
    }
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
}
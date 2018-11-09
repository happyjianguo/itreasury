/*
 * Created on 2006-4-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TRF_AssureAccountInfo {
    
	//ID
	private long id = -1;
	//账户号	sAccountNo	VARCHAR2(100)
	private String sAccountNo = "";
	//办事处编号	officeCode	VARCHAR2(100)
	private String officeCode = "";
	//币种	currency VARCHAR2(100)
	private String currency = "";
	//账户类型	accountType	VARCHAR2(100)
	private String accountType = "";
	//客户	clientCode	VARCHAR2(100)
	private String clientCode = "";
	//账户名称	sname	VARCHAR2(100)
	private String sName = "";
	//开户日期	dtOpen	date
	private Timestamp dtOpen = null;
	//清户日期	dtFinish	date
	private Timestamp dtFinish = null;
	//账户状态	status	VARCHAR2(100)
	private String status = "";
    //担保合同号
	private String contractCode="";
	//担保收款通知单号
	private String payFormCode="";
	//账户余额	mBalance	number(21,6)
	private double mBalance = 0.0;
	//存单状态
	private String payFormStatus="";
	//录入人	inputUser	VARCHAR2(100)
	private String inputUser = "";
	//录入时间	dtInput	date
	private Timestamp dtInput = null;
	//复核人	checkUser  VARCHAR2(100)
	private String checkUser = "";
	//复核时间	dtCheck	date
	private Timestamp dtCheck = null;
	//复核状态	checkStatus	VARCHAR2(100)
	private String checkStatus = "";
	//备注	sAbstract	VARCHAR2(100)
	private String sAbstract = "";
	//对应科目号	sSubject	VARCHAR2(100)
	private String sSubject = "";
	//利率
	private double mRate=0.0;
	//积数1	standBalance1	number(21,6)
	private double standBalance1 = 0.0;
	
	
	public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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
    public String getOfficeCode() {
        return officeCode;
    }
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }
    public String getPayFormCode() {
        return payFormCode;
    }
    public void setPayFormCode(String payFormCode) {
        this.payFormCode = payFormCode;
    }
    public String getPayFormStatus() {
        return payFormStatus;
    }
    public void setPayFormStatus(String payFormStatus) {
        this.payFormStatus = payFormStatus;
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
    public double getMRate() {
        return mRate;
    }
    public void setMRate(double rate) {
        mRate = rate;
    }
    public double getStandBalance1() {
        return standBalance1;
    }
    public void setStandBalance1(double standBalance1) {
        this.standBalance1 = standBalance1;
    }
}

/*
 * Created on 2005-08-31
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

import java.sql.Timestamp;

/**
 * @author kewen hu
 *
 */
public class Sett_AccountInfo{
	//ID	NOT NULL	NUMBER
	private long id = -1;
	//SACCOUNTNO	NOT NULL	VARCHAR2(30)
	private String sAccountNo = "";
	//NOFFICEID	NOT NULL	NUMBER
	private long nOfficeId = -1;
	//NCURRENCYID	NOT NULL	NUMBER
	private long nCurrencyId = -1;
	//NACCOUNTTYPEID	NOT NULL	NUMBER
	private long nAccountTypeId = -1;
	//NCLIENTID	NOT NULL	NUMBER
	private long nClientId = -1;
	//SNAME		VARCHAR2(100)
	private String sName = "";
	//DTOPEN		DATE
	private Timestamp dtOpen = null;
	//NINPUTUSERID		NUMBER
	private long nInputUserId = -1;
	//DTINPUT		DATE
	private Timestamp dtInput = null;
	//NCHECKUSERID		NUMBER
	private long nCheckUserId = -1;
	//DTCHECK		DATE
	private Timestamp dtCheck = null;
	//NCHECKSTATUSID	NOT NULL	NUMBER
	private long nCheckStatusId = -1;
	//NSTATUSID	NOT NULL	NUMBER
	private long nStatusId = -1;
	//DTFINISH		DATE
	private Timestamp dtFinish = null;
	//SABSTRACT		VARCHAR2(100)
	private String sAbstract = "";
	//SSUBJECT		VARCHAR2(100)
	private String sSubject = "";
	//MMAXSINGLEPAYAMOUNT		NUMBER(21,6)
	private double mMaxSinglePayAmount = 0.0;
	//MMINSINGLEPAYAMOUNT		NUMBER(21,6)
	private double mMinSinglePayAmount = 0.0;
	//NBATCHUPDATEID		NUMBER
	private long nBatchUpdateId = -1;
	//NBATCHUPDATETYPEID		NUMBER
	private long nBatchUpdateTypeId = -1;

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
    public double getMMaxSinglePayAmount() {
        return mMaxSinglePayAmount;
    }
    public void setMMaxSinglePayAmount(double maxSinglePayAmount) {
        mMaxSinglePayAmount = maxSinglePayAmount;
    }
    public double getMMinSinglePayAmount() {
        return mMinSinglePayAmount;
    }
    public void setMMinSinglePayAmount(double minSinglePayAmount) {
        mMinSinglePayAmount = minSinglePayAmount;
    }
    public long getNAccountTypeId() {
        return nAccountTypeId;
    }
    public void setNAccountTypeId(long accountTypeId) {
        nAccountTypeId = accountTypeId;
    }
    public long getNBatchUpdateId() {
        return nBatchUpdateId;
    }
    public void setNBatchUpdateId(long batchUpdateId) {
        nBatchUpdateId = batchUpdateId;
    }
    public long getNBatchUpdateTypeId() {
        return nBatchUpdateTypeId;
    }
    public void setNBatchUpdateTypeId(long batchUpdateTypeId) {
        nBatchUpdateTypeId = batchUpdateTypeId;
    }
    public long getNCheckStatusId() {
        return nCheckStatusId;
    }
    public void setNCheckStatusId(long checkStatusId) {
        nCheckStatusId = checkStatusId;
    }
    public long getNCheckUserId() {
        return nCheckUserId;
    }
    public void setNCheckUserId(long checkUserId) {
        nCheckUserId = checkUserId;
    }
    public long getNClientId() {
        return nClientId;
    }
    public void setNClientId(long clientId) {
        nClientId = clientId;
    }
    public long getNCurrencyId() {
        return nCurrencyId;
    }
    public void setNCurrencyId(long currencyId) {
        nCurrencyId = currencyId;
    }
    public long getNInputUserId() {
        return nInputUserId;
    }
    public void setNInputUserId(long inputUserId) {
        nInputUserId = inputUserId;
    }
    public long getNOfficeId() {
        return nOfficeId;
    }
    public void setNOfficeId(long officeId) {
        nOfficeId = officeId;
    }
    public long getNStatusId() {
        return nStatusId;
    }
    public void setNStatusId(long statusId) {
        nStatusId = statusId;
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
}
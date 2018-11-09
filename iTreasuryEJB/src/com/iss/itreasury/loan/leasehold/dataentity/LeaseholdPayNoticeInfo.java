package com.iss.itreasury.loan.leasehold.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.Constant;

/**
 *
 * <p>Title:融资租赁收款通知单 </p>
 *
 * <p>Description: 信贷管理－融资租赁</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: iSoftStone</p>
 *
 * @author liuxz
 * @version 1.0
 */
public class LeaseholdPayNoticeInfo extends LoanBaseDataEntity implements
        Serializable {
	long id = -1l;
    /** Loan_AssureChargeForm表中有的字段 */
    // Loan_AssureChargeForm字段界面上要显示
    private Timestamp executeDate; //收款日期
    private String code; //收款通知单编号
    private long recognizanceAccountId; //付保证金账户编号
    private long recrecognizanceAccountId = -1; //收保证金账户编号
    private double recognizanceAmount = 0d; //保证金金额
    private long isInteRest = 1l; //是否计息
    private long assureChargeAccountId; //付手续费账户编号
    private double assureChargeAmount = 0d; //手续费金额
    //Loan_AssureChargeForm字段界面上不显示
    private long currencyId = -1l; //币种
    private long officeId = -1l; //办事处
    private long contractId = -1l; //合同ID
    private double assureAmount = 0; //承保金额
    private Timestamp startDate = null; //担保开始日期
    private Timestamp endDate = null; //担保结束日期
    private double rate = 0d; //利率
    private long statusId = -1l; //状态
    private long inputUserId = -1l; //录入人
    private Timestamp inputDate = null; //录入时间
    private long nextCheckUserId = -1l; //下一级审核人
    private long nextCheckLevel = -1l; //下一个审核级别
    private long isLowLevel = -1l; //是否走最低级审批流
    private long intervalNum = -1l; //担保期限
    //Loan_AssureChargeForm表中没有的字段
    private long clientId = -1l;
    private String clientName = "";
    private String contractCode = "";
    private String inputUserName = ""; //录入人名称
    private String nextCheckUserName = ""; //下一级审核人名称
    //查询用
    private long contractIDStart = -1;//融资租赁合同ID
    private long contractIDEnd = -1;//融资租赁合同ID
    private String contractCodeStart = "";//融资租赁合同编号
    private String contractCodeEnd = "";//融资租赁合同编号
    private long clientIDStart=-1l;//承租单位ID
    private long clientIDEnd=-1l;  //承租单位ID
    private String clientCodeStart = "";//承租单位编号
    private String clientCodeEnd = "";  //承租单位编号
    private Timestamp queryEndDate;//租赁日期
    private Timestamp queryStartDate;//租赁日期
    private long desc = Constant.PageControl.CODE_ASCORDESC_ASC;//排序方式
    private long orderParam = -1l;
    private String orderParamString = "";
    private long queryPurpose = 1l;
    //查询显示用
    private long recordCount = 0l;
    private long pageCount = 0l;
    private long pageLineCount;
    private long pageNo;
    private long rowNumStart = 0;
    private long rowNumEnd = 0;
    //其他（审核等）
    private long userID = -1;
    private String strUser = "";
    
    //added by qhzou 2007.07.06
    private InutParameterInfo inutParameterInfo = null;
    
    //add by yunchang 2010-05-17 20:09
    private long collectionRentAccountID = -1;  //收取租金账号ID
    
    //add by yunchang 2010-06-28 18:06
    private double surplusRecognizanceAmount = 0d;

    public double getSurplusRecognizanceAmount() {
		return surplusRecognizanceAmount;
	}

	public void setSurplusRecognizanceAmount(double surplusRecognizanceAmount) {
		this.surplusRecognizanceAmount = surplusRecognizanceAmount;
	}

	public double getAssureAmount() {
        return assureAmount;
    }

    public long getAssureChargeAccountId() {
        return assureChargeAccountId;
    }

    public double getAssureChargeAmount() {
        return assureChargeAmount;
    }

    public long getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCode() {
        return code;
    }

    public String getContractCode() {
        return contractCode;
    }

    public long getContractId() {
        return contractId;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public Timestamp getExecuteDate() {
        return executeDate;
    }

    public Timestamp getInputDate() {
        return inputDate;
    }

    public long getInputUserId() {
        return inputUserId;
    }

    public String getInputUserName() {
        return inputUserName;
    }

    public long getIsInteRest() {
        return isInteRest;
    }

    public long getIsLowLevel() {
        return isLowLevel;
    }

    public long getNextCheckLevel() {
        return nextCheckLevel;
    }

    public long getNextCheckUserId() {
        return nextCheckUserId;
    }

    public String getNextCheckUserName() {
        return nextCheckUserName;
    }

    public long getPageCount() {
        return pageCount;
    }

    public long getRecognizanceAccountId() {
        return recognizanceAccountId;
    }

    public double getRecognizanceAmount() {
        return recognizanceAmount;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public long getRecrecognizanceAccountId() {
        return recrecognizanceAccountId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public long getStatusId() {
        return statusId;
    }

    public double getRate() {
        return rate;
    }

    public long getOfficeId() {
        return officeId;
    }

    public long getContractIDStart() {
        return contractIDStart;
    }

    public long getContractIDEnd() {
        return contractIDEnd;
    }

    public String getContractCodeStart() {
        return contractCodeStart;
    }

    public String getContractCodeEnd() {
        return contractCodeEnd;
    }

    public long getPageLineCount() {
        return pageLineCount;
    }

    public long getPageNo() {
        return pageNo;
    }

    public long getOrderParam() {
        return orderParam;
    }

    public long getDesc() {
        return desc;
    }

    public String getOrderParamString() {
        return orderParamString;
    }

    public long getQueryPurpose() {
        return queryPurpose;
    }

    public long getIntervalNum() {
        return intervalNum;
    }

    public Timestamp getQueryEndDate() {
        return queryEndDate;
    }

    public Timestamp getQueryStartDate() {
        return queryStartDate;
    }

    public long getRowNumEnd() {
        return rowNumEnd;
    }

    public long getRowNumStart() {
        return rowNumStart;
    }

    public String getStrUser() {
        return strUser;
    }

    public long getUserID() {
        return userID;
    }

    public long getClientIDStart() {
        return clientIDStart;
    }

    public long getClientIDEnd() {
        return clientIDEnd;
    }

    public String getClientCodeEnd() {
        return clientCodeEnd;
    }

    public String getClientCodeStart() {
        return clientCodeStart;
    }


    public void setAssureAmount(double assureAmount) {
        this.assureAmount = assureAmount;
        putUsedField("assureAmount", this.assureAmount);
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
        putUsedField("statusId", this.statusId);
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
        putUsedField("startDate", this.startDate);
    }

    public void setRecrecognizanceAccountId(long recrecognizanceAccountId) {
        this.recrecognizanceAccountId = recrecognizanceAccountId;
        putUsedField("recrecognizanceAccountId", this.recrecognizanceAccountId);
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public void setRecognizanceAmount(double recognizanceAmount) {
        this.recognizanceAmount = recognizanceAmount;
        putUsedField("recognizanceAmount", this.recognizanceAmount);
    }

    public void setRecognizanceAccountId(long recognizanceAccountId) {
        this.recognizanceAccountId = recognizanceAccountId;
        putUsedField("recognizanceAccountId", this.recognizanceAccountId);
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public void setNextCheckUserName(String nextCheckUserName) {
        this.nextCheckUserName = nextCheckUserName;
    }

    public void setNextCheckUserId(long nextCheckUserId) {
        this.nextCheckUserId = nextCheckUserId;
        putUsedField("nextCheckUserId", this.nextCheckUserId);
    }

    public void setNextCheckLevel(long nextCheckLevel) {
        this.nextCheckLevel = nextCheckLevel;
        putUsedField("nextCheckLevel", this.nextCheckLevel);
    }

    public void setIsLowLevel(long isLowLevel) {
        this.isLowLevel = isLowLevel;
        putUsedField("isLowLevel", this.isLowLevel);
    }

    public void setIsInteRest(long isInteRest) {
        this.isInteRest = isInteRest;
        putUsedField("isInteRest", this.isInteRest);
    }

    public void setInputUserName(String inputUserName) {
        this.inputUserName = inputUserName;
    }

    public void setInputUserId(long inputUserId) {
        this.inputUserId = inputUserId;
        putUsedField("inputUserId", this.inputUserId);
    }

    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
        putUsedField("inputDate", this.inputDate);
    }

    public void setExecuteDate(Timestamp executeDate) {
        this.executeDate = executeDate;
        putUsedField("executeDate", this.executeDate);
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
        putUsedField("endDate", this.endDate);
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
        putUsedField("currencyId", this.currencyId);
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
        putUsedField("contractId", this.contractId);
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public void setCode(String code) {
        this.code = code;
        putUsedField("code", this.code);
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setAssureChargeAmount(double assureChargeAmount) {
        this.assureChargeAmount = assureChargeAmount;
        putUsedField("assureChargeAmount", this.assureChargeAmount);
    }

    public void setAssureChargeAccountId(long assureChargeAccountId) {
        this.assureChargeAccountId = assureChargeAccountId;
        putUsedField("assureChargeAccountId", this.assureChargeAccountId);
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
        putUsedField("officeId", this.officeId);
    }

    public void setRate(double rate) {
        this.rate = rate;
        putUsedField("rate", this.rate);
    }

    public void setContractIDStart(long contractIDStart) {
        this.contractIDStart = contractIDStart;
    }

    public void setContractIDEnd(long contractIDEnd) {
        this.contractIDEnd = contractIDEnd;
    }

    public void setContractCodeStart(String contractCodeStart) {
        this.contractCodeStart = contractCodeStart;
    }

    public void setContractCodeEnd(String contractCodeEnd) {
        this.contractCodeEnd = contractCodeEnd;
    }

    public void setPageLineCount(long pageLineCount) {
        this.pageLineCount = pageLineCount;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public void setOrderParam(long orderParam) {
        this.orderParam = orderParam;
    }

    public void setDesc(long desc) {
        this.desc = desc;
    }

    public void setOrderParamString(String orderParamString) {
        this.orderParamString = orderParamString;
    }

    public void setQueryPurpose(long queryPurpose) {
        this.queryPurpose = queryPurpose;
    }

    public void setIntervalNum(long intervalNum) {
        this.intervalNum = intervalNum;
    }

    public void setQueryEndDate(Timestamp queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public void setQueryStartDate(Timestamp queryStartDate) {
        this.queryStartDate = queryStartDate;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public void setRowNumStart(long rowNumStart) {
        this.rowNumStart = rowNumStart;
    }

    public void setRowNumEnd(long rowNumEnd) {
        this.rowNumEnd = rowNumEnd;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setClientIDStart(long clientIDStart) {
        this.clientIDStart = clientIDStart;
    }

    public void setClientIDEnd(long clientIDEnd) {
        this.clientIDEnd = clientIDEnd;
    }

    public void setClientCodeEnd(String clientCodeEnd) {
        this.clientCodeEnd = clientCodeEnd;
    }

    public void setClientCodeStart(String clientCodeStart) {
        this.clientCodeStart = clientCodeStart;
    }
    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
     */
    public long getId()
    {
        // TODO Auto-generated method stub
        return id;
    }

    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
     */
    public void setId(long id)
    {
        // TODO Auto-generated method stub
        this.id = id;
        putUsedField("ID", id);
    }

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long getCollectionRentAccountID() 
	{
		return collectionRentAccountID;
	}

	public void setCollectionRentAccountID(long collectionRentAccountID) 
	{
		this.collectionRentAccountID = collectionRentAccountID;
		putUsedField("collectionrentaccountid", collectionRentAccountID);
	}
}

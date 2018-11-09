package com.iss.itreasury.loan.leasehold.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 *
 * <p>Title: 融资租赁还款通知单</p>
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
public class LeaseholdRepayNoticeInfo extends LoanBaseDataEntity implements
        Serializable {
	long id =-1l; 
    private String code = ""; //编号
    private long currencyId = -1l; //币种
    private long officeId = -1l; //办事处
    private long contractId = -1l; //合同ID
    private double amount = 0d; //本金金额
    private double recognizanceAmount = 0d; //保证金扣除金额
    private double interestAmount = 0d; //利息
    private long recognizanceAccountId = -1l; //保证金扣除账户
    private long inputUserId = -1l; //录入人
    private Timestamp inputDate; //录入时间
    private long isLowLevel = -1l; //是否走最低审批级别
    private long nextCheckUserId = -1l; //下一级审核人
    private long nextCheckLevel = -1l; //下一级审核级别
    private long nStatusId = -1l; //状态
    //LOAN_LEASEHOLDREPAYFORM表中没有的字段
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
    private Timestamp executeDate;//执行日期
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
    //其他（审核、结算等）
    private long userID = -1;
    private String strUser = "";
    private long assureChargeFormID = -1;//对应收款通知单id
    
    //added by qhzou 2007.07.06
    private InutParameterInfo inutParameterInfo = null;
    
    //added by xiong fei 2010-07-25 对应已还期数
    private long issueD = -1;
    

    public long getIssueD() {
		return issueD;
	}

	public void setIssueD(long issueD) {
		this.issueD = issueD;
		putUsedField("issue", this.issueD);
	}

	public long getAssureChargeFormID() {
		return assureChargeFormID;
	}

	public void setAssureChargeFormID(long assureChargeFormID) {
		this.assureChargeFormID = assureChargeFormID;
	}

	public LeaseholdRepayNoticeInfo() {
    }

    public void setCode(String code) {
        this.code = code;
        putUsedField("code", this.code);
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
        putUsedField("currencyId", this.currencyId);
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
        putUsedField("officeId", this.officeId);
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
        putUsedField("contractId", this.contractId);
    }

    public void setAmount(double amount) {
        this.amount = amount;
        putUsedField("amount", this.amount);
    }

    public void setRecognizanceAmount(double recognizanceAmount) {
        this.recognizanceAmount = recognizanceAmount;
        putUsedField("recognizanceAmount", this.recognizanceAmount);
    }

    public void setInterestAmount(double interestAmount) {
        this.interestAmount = interestAmount;
        putUsedField("interestAmount", this.interestAmount);
    }

    public void setRecognizanceAccountId(long recognizanceAccountId) {
        this.recognizanceAccountId = recognizanceAccountId;
        putUsedField("recognizanceAccountId", this.recognizanceAccountId);
    }

    public void setInputUserId(long inputUserId) {
        this.inputUserId = inputUserId;
        putUsedField("inputUserId", this.inputUserId);
    }

    public void setInputDate(Timestamp inputDate) {
        this.inputDate = inputDate;
        putUsedField("inputDate", this.inputDate);
    }

    public void setIsLowLevel(long isLowLevel) {
        this.isLowLevel = isLowLevel;
        putUsedField("isLowLevel", this.isLowLevel);
    }

    public void setNextCheckUserId(long nextCheckUserId) {
        this.nextCheckUserId = nextCheckUserId;
        putUsedField("nextCheckUserId", this.nextCheckUserId);
    }

    public void setNextCheckLevel(long nextCheckLevel) {
        this.nextCheckLevel = nextCheckLevel;
        putUsedField("nextCheckLevel", this.nextCheckLevel);
    }

    public void setNStatusId(long nStatusId) {
        this.nStatusId = nStatusId;
        putUsedField("nStatusId", this.nStatusId);
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public void setQueryPurpose(long queryPurpose) {
        this.queryPurpose = queryPurpose;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageLineCount(long pageLineCount) {
        this.pageLineCount = pageLineCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public void setOrderParamString(String orderParamString) {
        this.orderParamString = orderParamString;
    }

    public void setOrderParam(long orderParam) {
        this.orderParam = orderParam;
    }

    public void setNextCheckUserName(String nextCheckUserName) {
        this.nextCheckUserName = nextCheckUserName;
    }

    public void setInputUserName(String inputUserName) {
        this.inputUserName = inputUserName;
    }

    public void setDesc(long desc) {
        this.desc = desc;
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

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }



    public void setQueryEndDate(Timestamp queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public void setQueryStartDate(Timestamp queryStartDate) {
        this.queryStartDate = queryStartDate;
    }

    public void setRowNumEnd(long rowNumEnd) {
        this.rowNumEnd = rowNumEnd;
    }

    public void setRowNumStart(long rowNumStart) {
        this.rowNumStart = rowNumStart;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setClientCodeEnd(String clientCodeEnd) {
        this.clientCodeEnd = clientCodeEnd;
    }

    public void setClientCodeStart(String clientCodeStart) {
        this.clientCodeStart = clientCodeStart;
    }

    public void setClientIDEnd(long clientIDEnd) {
        this.clientIDEnd = clientIDEnd;
    }

    public void setClientIDStart(long clientIDStart) {
        this.clientIDStart = clientIDStart;
    }

    public String getCode() {
        return code;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public long getOfficeId() {
        return officeId;
    }

    public long getContractId() {
        return contractId;
    }

    public double getAmount() {
        return amount;
    }

    public double getRecognizanceAmount() {
        return recognizanceAmount;
    }

    public double getInterestAmount() {
        return interestAmount;
    }

    public long getRecognizanceAccountId() {
        return recognizanceAccountId;
    }

    public long getInputUserId() {
        return inputUserId;
    }

    public Timestamp getInputDate() {
        return inputDate;
    }

    public long getIsLowLevel() {
        return isLowLevel;
    }

    public long getNextCheckUserId() {
        return nextCheckUserId;
    }

    public long getNextCheckLevel() {
        return nextCheckLevel;
    }

    public long getNStatusId() {
        return nStatusId;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public long getQueryPurpose() {
        return queryPurpose;
    }

    public long getPageNo() {
        return pageNo;
    }

    public long getPageLineCount() {
        return pageLineCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public String getOrderParamString() {
        return orderParamString;
    }

    public long getOrderParam() {
        return orderParam;
    }

    public String getNextCheckUserName() {
        return nextCheckUserName;
    }

    public String getInputUserName() {
        return inputUserName;
    }

    public long getDesc() {
        return desc;
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

    public String getContractCode() {
        return contractCode;
    }

    public String getClientName() {
        return clientName;
    }

    public long getClientId() {
        return clientId;
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

    public String getClientCodeStart() {
        return clientCodeStart;
    }

    public String getClientCodeEnd() {
        return clientCodeEnd;
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

	public Timestamp getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Timestamp executeDate) {
		this.executeDate = executeDate;
		putUsedField("executeDate",executeDate);
	}
}

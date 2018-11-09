/*
 * Created on 2004-11-24
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalance.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         OffBalanceParam页面传入的参数
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              kewen hu 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceParam extends ITreasuryBaseDataEntity implements Serializable{
	//ID	Number	ID	主健
	private long id = -1;
	//transNo	VARCHAR2(30)	交易号
	private String transNo = "";
	//transactionType	Number	业务类型	1—贷款未收利息类表外业务、2—贷款未收利息类表外业务、2—商业汇票贴现类表外业务、3—开出保函凭信类表外业务
	private long transactionType = -1;
	//subjectCode	VARCHAR2(30)	科目号	
	private String subjectCode = "";	
	//contractID	Number	合同ID
	private long contractID = -1;
	//loanNoteID	Number	放款通知单ID、贴现凭证ID	
	private long loanNoteID = -1;	
	//billID	Number	汇票ID
	private long billID = -1;	
	//borrowClientID	Number	借款客户编号ID
	private long borrowClientID = -1;
	//consignClientID	Number	委托客户编号ID
	private long consignClientID = -1;
	//loanAccountID	Number	借款人活期账户ID
	private long loanAccountID = -1;
	//consignAccountID	Number	委托人活期账户ID
	private long consignAccountID = -1;
	//direction	Number	交易方向	1—收入、2—付出
	private long direction = -1;
	//executeDate	DATE	执行日期
	private Timestamp executeDate = null;
	private Timestamp executeDateEnd = null;//查询-终止日期
	//consignDate	DATE	托收日期
	private Timestamp consignDate = null;
	//startDate	DATE	保函开立日期
	private Timestamp startDate = null;
	//endDate	DATE	担保到期日期、票面到期日
	private Timestamp endDate = null;
	//draftType	Number	汇票种类
	private long draftType = -1;
	//assureType	Number	担保类型
	private long assureType = -1;
	//intervalNum	Number	担保期限
	private long intervalNum = -1;
	//isLocal	Number	是否本地
	private long isLocal = -1;
	//draftAmount	NUMBER(21,6)	担保合同金额
	private double draftAmount = 0.0;
	//depositAmount	NUMBER(21,6)	保证金金额
	private double depositAmount = 0.0;
	//amount	NUMBER(21,6)	金额
	private double amount = 0.0;
	//inputUserID	Number	录入人ID
	private long inputUserID = -1;
	//inputDate	DATE	录入日期
	private Timestamp inputDate = null;
	//checkUserID	Number	复核人ID
	private long checkUserID = -1;
	//checkDate	DATE	复核日期
	private Timestamp checkDate = null;
	//modifyDate	DATE	修改日期	精确到时分秒
	private Timestamp modifyDate = null;
	//statusID	Number	状态	0—已删除、2—已保存、3—已复核
	private long statusID = -1;
	//abstract	VARCHAR2(1000)	备注
	private String Abstract = "";
	
	private long AscOrDesc = -1;//升序or降序
	private long OrderByType = -1;

	private long currencyID = -1;
	private long officeID = -1;

	/**
	 * @return Returns the abstract.
	 */
	public String getAbstract() {
		return Abstract;
	}
	/**
	 * @param abstract1 The abstract to set.
	 */
	public void setAbstract(String Abstract) {
		putUsedField("abstract", Abstract);
		this.Abstract = Abstract;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		putUsedField("amount", amount);
		this.amount = amount;
	}
	/**
	 * @return Returns the assureType.
	 */
	public long getAssureType() {
		return assureType;
	}
	/**
	 * @param assureType The assureType to set.
	 */
	public void setAssureType(long assureType) {
		putUsedField("assureType", assureType);
		this.assureType = assureType;
	}	
	/**
	 * @return Returns the billID.
	 */
	public long getBillID() {
		return billID;
	}
	/**
	 * @param billID The billID to set.
	 */
	public void setBillID(long billID) {
		putUsedField("billID", billID);
		this.billID = billID;
	}
	/**
	 * @return Returns the borrowClientID.
	 */
	public long getBorrowClientID() {
		return borrowClientID;
	}
	/**
	 * @param borrowClientID The borrowClientID to set.
	 */
	public void setBorrowClientID(long borrowClientID) {
		putUsedField("borrowClientID", borrowClientID);
		this.borrowClientID = borrowClientID;
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate() {
		return checkDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate) {
		putUsedField("checkDate", checkDate);
		this.checkDate = checkDate;
	}
	/**
	 * @return Returns the checkUserID.
	 */
	public long getCheckUserID() {
		return checkUserID;
	}
	/**
	 * @param checkUserID The checkUserID to set.
	 */
	public void setCheckUserID(long checkUserID) {
		putUsedField("checkUserID", checkUserID);
		this.checkUserID = checkUserID;
	}
	/**
	 * @return Returns the consignAccountID.
	 */
	public long getConsignAccountID() {
		return consignAccountID;
	}
	/**
	 * @param consignAccountID The consignAccountID to set.
	 */
	public void setConsignAccountID(long consignAccountID) {
		putUsedField("consignAccountID", consignAccountID);
		this.consignAccountID = consignAccountID;
	}
	/**
	 * @return Returns the consignClientID.
	 */
	public long getConsignClientID() {
		return consignClientID;
	}
	/**
	 * @param consignClientID The consignClientID to set.
	 */
	public void setConsignClientID(long consignClientID) {
		putUsedField("consignClientID", consignClientID);
		this.consignClientID = consignClientID;
	}
	/**
	 * @return Returns the consignDate.
	 */
	public Timestamp getConsignDate() {
		return consignDate;
	}
	/**
	 * @param consignDate The consignDate to set.
	 */
	public void setConsignDate(Timestamp consignDate) {
		putUsedField("consignDate", consignDate);
		this.consignDate = consignDate;
	}	
	/**
	 * @return Returns the contractID.
	 */
	public long getContractID() {
		return contractID;
	}
	/**
	 * @param contractID The contractID to set.
	 */
	public void setContractID(long contractID) {
		putUsedField("contractID", contractID);
		this.contractID = contractID;
	}
	/**
	 * @return Returns the depositAmount.
	 */
	public double getDepositAmount() {
		return depositAmount;
	}
	/**
	 * @param depositAmount The depositAmount to set.
	 */
	public void setDepositAmount(double depositAmount) {
		putUsedField("depositAmount", depositAmount);
		this.depositAmount = depositAmount;
	}
	/**
	 * @return Returns the direction.
	 */
	public long getDirection() {
		return direction;
	}
	/**
	 * @param direction The direction to set.
	 */
	public void setDirection(long direction) {
		putUsedField("direction", direction);
		this.direction = direction;
	}
	/**
	 * @return Returns the draftAmount.
	 */
	public double getDraftAmount() {
		return draftAmount;
	}
	/**
	 * @param draftAmount The draftAmount to set.
	 */
	public void setDraftAmount(double draftAmount) {
		putUsedField("draftAmount", draftAmount);
		this.draftAmount = draftAmount;
	}
	/**
	 * @return Returns the draftType.
	 */
	public long getDraftType() {
		return draftType;
	}
	/**
	 * @param draftType The draftType to set.
	 */
	public void setDraftType(long draftType) {
		putUsedField("draftType", draftType);
		this.draftType = draftType;
	}
	/**
	 * @return Returns the endDate.
	 */
	public Timestamp getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Timestamp endDate) {
		putUsedField("endDate", endDate);
		this.endDate = endDate;
	}
	/**
	 * @return Returns the executeDate.
	 */
	public Timestamp getExecuteDate() {
		return executeDate;
	}
	/**
	 * @param executeDate The executeDate to set.
	 */
	public void setExecuteDate(Timestamp executeDate) {
		putUsedField("executeDate", executeDate);
		this.executeDate = executeDate;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		putUsedField("inputDate", inputDate);
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		putUsedField("inputUserID", inputUserID);
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the intervalNum.
	 */
	public long getIntervalNum() {
		return intervalNum;
	}
	/**
	 * @param intervalNum The intervalNum to set.
	 */
	public void setIntervalNum(long intervalNum) {
		putUsedField("intervalNum", intervalNum);
		this.intervalNum = intervalNum;
	}
	/**
	 * @return Returns the isLocal.
	 */
	public long getIsLocal() {
		return isLocal;
	}
	/**
	 * @param isLocal The isLocal to set.
	 */
	public void setIsLocal(long isLocal) {
		putUsedField("isLocal", isLocal);
		this.isLocal = isLocal;
	}
	/**
	 * @return Returns the loanAccountID.
	 */
	public long getLoanAccountID() {
		return loanAccountID;
	}
	/**
	 * @param loanAccountID The loanAccountID to set.
	 */
	public void setLoanAccountID(long loanAccountID) {
		putUsedField("loanAccountID", loanAccountID);
		this.loanAccountID = loanAccountID;
	}	
	/**
	 * @return Returns the loanNoteID.
	 */
	public long getLoanNoteID() {
		return loanNoteID;
	}
	/**
	 * @param loanNoteID The loanNoteID to set.
	 */
	public void setLoanNoteID(long loanNoteID) {
		putUsedField("loanNoteID", loanNoteID);
		this.loanNoteID = loanNoteID;
	}
	/**
	 * @return Returns the modifyDate.
	 */
	public Timestamp getModifyDate() {
		return modifyDate;
	}
	/**
	 * @param modifyDate The modifyDate to set.
	 */
	public void setModifyDate(Timestamp modifyDate) {
		putUsedField("modifyDate", modifyDate);
		this.modifyDate = modifyDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public Timestamp getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Timestamp startDate) {
		putUsedField("startDate", startDate);
		this.startDate = startDate;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		putUsedField("statusID", statusID);
		this.statusID = statusID;
	}
	/**
	 * @return Returns the subjectCode.
	 */
	public String getSubjectCode() {
		return subjectCode;
	}
	/**
	 * @param subjectCode The subjectCode to set.
	 */
	public void setSubjectCode(String subjectCode) {
		putUsedField("subjectCode", subjectCode);
		this.subjectCode = subjectCode;
	}	
	/**
	 * @return Returns the transactionType.
	 */
	public long getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(long transactionType) {
		putUsedField("transactionType", transactionType);
		this.transactionType = transactionType;
	}
	/**
	 * @return Returns the transNo.
	 */
	public String getTransNo() {
		return transNo;
	}
	/**
	 * @param transNo The transNo to set.
	 */
	public void setTransNo(String transNo) {
		putUsedField("transNo", transNo);
		this.transNo = transNo;
	}
    /**
     * @return Returns the ascOrDesc.
     */
    public long getAscOrDesc()
    {
        return AscOrDesc;
    }
    /**
     * @param ascOrDesc The ascOrDesc to set.
     */
    public void setAscOrDesc(long ascOrDesc)
    {
        AscOrDesc = ascOrDesc;
    }
    /**
     * @return Returns the orderByType.
     */
    public long getOrderByType()
    {
        return OrderByType;
    }
    /**
     * @param orderByType The orderByType to set.
     */
    public void setOrderByType(long orderByType)
    {
        OrderByType = orderByType;
    }
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		putUsedField("currencyID", currencyID);
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		putUsedField("officeID", officeID);
		this.officeID = officeID;
	}
    /**
     * @return Returns the executeDateEnd.
     */
    public Timestamp getExecuteDateEnd()
    {
        return executeDateEnd;
    }
    /**
     * @param executeDateEnd The executeDateEnd to set.
     */
    public void setExecuteDateEnd(Timestamp executeDateEnd)
    {
        this.executeDateEnd = executeDateEnd;
    }
}
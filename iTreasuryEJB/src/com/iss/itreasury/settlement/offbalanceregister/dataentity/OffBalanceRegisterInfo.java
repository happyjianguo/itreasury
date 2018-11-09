/*
 * Created on 2004-11-24
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.offbalanceregister.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         OffBalanceInfo查询得到的结果集
 *  Copyright           (c) 2004 Company: iSoftStone
 * @author              kewen hu 
 * @version
 *  Date of Creation    2004-11-23
 */
public class OffBalanceRegisterInfo extends ITreasuryBaseDataEntity implements Serializable{
	//ID	Number	ID	主健
	private long id = -1;
	//transNo	VARCHAR2(30)	交易号
	private String transNo = "";
	//transactionType	Number	业务类型	1—贷款未收利息类表外业务、2—贷款未收利息类表外业务、2—商业汇票贴现类表外业务、3—开出保函凭信类表外业务
	private long transactionType = -1;
	//Subjectcode	VARCHAR2(30)	科目号
	private String subjectCode = "";
	//clientID	Number	客户号ID
	private long clientID = -1;
	//contractID	Number	合同号ID
	private long contractID = -1;
	//loanNoteID	Number	放款通知单ID、贴现凭证ID
	private long loanNoteID = -1;
	//billID	Number	汇票ID
	private long billID = -1;
	//registeDate	DATE	登记日期
	private Timestamp registeDate = null;
	//writeoffDate	DATE	销账日期
	private Timestamp writeoffDate = null;
	//consignDate	DATE	托收日期
	private Timestamp consignDate = null;
	//amount	NUMBER(21,6)	金额
	private double amount = 0.0;
	//balance	NUMBER(21,6)	余额
	private double balance = 0.0;
	//direction	Number	交易方向	1—收入、2—付出
	private long direction = -1;
	//statusID	Number	状态	0—已删除、2—正常
	private long statusID = -1;
	
	//loanAccountID	Number	借款人活期账户ID
	private long loanAccountID = -1;
	//draftType	Number	汇票种类
	private long draftType = -1;
	//endDate	DATE	担保到期日期、票面到期日
	private Timestamp endDate = null;
	//isLocal	Number	是否本地
	private long isLocal = -1;

	private long officeID = -1;
	private long currencyID = -1;
	
	
	//////////////////////
	//开始日期
	private Timestamp startDate = null;
	//担保类型
	private long assureType = -1;
	//担保开始期日
	private Timestamp executeDate = null;
	//担保状态 0--已销账  1--未销账
	private long assureStatus = -1;
	//保证金金额
	private double depositAmount = 0.0;
	//担保期限
	private long intervalNum = -1;
	//////////////////////
	
	
	
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
	 * @return Returns the balance.
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance) {
		putUsedField("balance", balance);
		this.balance = balance;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID) {
		putUsedField("clientID", clientID);
		this.clientID = clientID;
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
	 * @return Returns the registeDate.
	 */
	public Timestamp getRegisteDate() {
		return registeDate;
	}
	/**
	 * @param registeDate The registeDate to set.
	 */
	public void setRegisteDate(Timestamp registeDate) {
		putUsedField("registeDate", registeDate);
		this.registeDate = registeDate;
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
	 * @return Returns the writeoffDate.
	 */
	public Timestamp getWriteoffDate() {
		return writeoffDate;
	}
	/**
	 * @param writeoffDate The writeoffDate to set.
	 */
	public void setWriteoffDate(Timestamp writeoffDate) {
		putUsedField("writeoffDate", writeoffDate);
		this.writeoffDate = writeoffDate;
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
     * @return Returns the draftType.
     */
    public long getDraftType()
    {
        return draftType;
    }
    /**
     * @param draftType The draftType to set.
     */
    public void setDraftType(long draftType)
    {
        this.draftType = draftType;
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        this.endDate = endDate;
    }
    /**
     * @return Returns the isLocal.
     */
    public long getIsLocal()
    {
        return isLocal;
    }
    /**
     * @param isLocal The isLocal to set.
     */
    public void setIsLocal(long isLocal)
    {
        this.isLocal = isLocal;
    }
    /**
     * @return Returns the loanAccountID.
     */
    public long getLoanAccountID()
    {
        return loanAccountID;
    }
    /**
     * @param loanAccountID The loanAccountID to set.
     */
    public void setLoanAccountID(long loanAccountID)
    {
        this.loanAccountID = loanAccountID;
    }
    
    ///////////////////////////////////////////////////////
   
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
        this.assureType = assureType;
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
        this.depositAmount = depositAmount;
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
        this.executeDate = executeDate;
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
        this.intervalNum = intervalNum;
    }
    
    ///////////////////////////////////////////////////////
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
        this.startDate = startDate;
    }
    /**
     * @return Returns the assureStatus.
     */
    public long getAssureStatus() {
        return assureStatus;
    }
    /**
     * @param assureStatus The assureStatus to set.
     */
    public void setAssureStatus(long assureStatus) {
        this.assureStatus = assureStatus;
    }
}
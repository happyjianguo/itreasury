/*
 * 创建日期 2003-10-15
 */
package com.iss.itreasury.settlement.transloan.dataentity;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * 贷款条件信息
 * @author yqwu
 */
public class LoanPayFormDetailInfo  implements Serializable    
{
	/**
	 * 发放至活期账户ID 
	 */
	private long GrantCurrentAccountID = -1; 
	/**
	 * 开户行ID 
	 */
	private long AccountBankID = -1;
	
	/**
	 * 放款方式
	 */
	private long GrantTypeID = -1;
	/**
	 * 汇入收款单位账户号
	 */
	private String ReceiveAccountNo = "";
	/**
	 * 汇入收款单位名称
	 */
	private String ReceiveAccountName = "";
	/**
	 * 汇入地（省）
	 */
	private String RemitInProvince = "";
	/**
	 * 汇入地（市）
	 */
	private String RemitInCity = "";
	/**
	 * 汇入行名称
	 */
	private String RemitBank = "";
	
	/**
     * 客户编号
     */
    private long clientId = -1;
    /**
     * 客户编号
     */
    String clientCode;

    /**
     * 客户名称
     */
    String clientName;

    /**
     * 贷款账户号
     */
    String loanAccountCode;

    /**
     * 合同号
     */
    String contractCode;

    /**
     * 放款通知单号
     */
    String loanNoteCode;

    /**
     * 放款通知单id
     */
    long loadNoteID;

    /**
     * 起始日期
     */
    Timestamp start;

    /**
     * 到期日期
     */
    Timestamp end;
    
    /**
     * 放款通知单起始日期
     */
    Timestamp DTSTART;

    /**
     * 放款通知单到期日期
     */
    Timestamp DTEND;
    
    /**
     * 录入日期
     */
    Timestamp inputDate;
	/**
 	* 放款日期
 	*/
	Timestamp outDate;
    /**
     * 贷款种类
     */
    long loanType;

    /**
     * 贷款期限
     */
    long loanTerm;

    /**
     * 利率
     */
    double interestRate;
    
    //罚息利率
    double overDueInterestRate;
    
    long isRemitOverDueInterest;

    /**
     * 担保费率
     */
    double assureRate;
    
	/**
	 * 手续费率
	 */
	double commissionRate;
    

    /**
     * 金额
     */
    double amount;

    /**
     * 起息日
     */
    Timestamp interestStart;

    /**
     * 手续费
     */
    double poundage;
	
	/**
	 * 贷款类型，可判断是否是循环贷款，常量类中的SettLoanType子类的6和7是循环贷款
	 */
	long typeID;
	
	long subTypeID;  //贷款子类型
	
	/**
	 * 手续费支付方式
	 */
	long chargeRateTypeID;
	
	/**
	 * 地区分类
	 */
	private long TypeID1 = -1;
	/**
	 * 行业分类1
	 */
	private long  TypeID2 = -1;
	/**
	 * 行业分类2
	 */
	private long  TypeID3 = -1;
	
	public long getSubTypeID() {
		return subTypeID;
	}
	public void setSubTypeID(long subTypeID) {
		this.subTypeID = subTypeID;
	}
	public long getClientId() 
	{
		return clientId;
	}
	public void setClientId(long clientId) 
	{
		this.clientId = clientId;
	}
	/**
	 * @return Returns the chargeRateTypeID.
	 */
	public long getChargeRateTypeID()
	{
		return chargeRateTypeID;
	}

	/**
	 * @param chargeRateTypeID The chargeRateTypeID to set.
	 */
	public void setChargeRateTypeID(long chargeRateTypeID)
	{
		this.chargeRateTypeID = chargeRateTypeID;
	}

	/**
	 * @return Returns the typeID.
	 */
	public long getTypeID()
	{
		return typeID;
	}

	/**
	 * @param typeID The typeID to set.
	 */
	public void setTypeID(long typeID)
	{
		this.typeID = typeID;
	}

    /**
     * @return
     */
    public double getAssureRate()
    {
        return assureRate;
    }

    /**
     * @return
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * @return
     */
    public Timestamp getEnd()
    {
        return end;
    }

    /**
     * @return
     */
    public double getInterestRate()
    {
        return interestRate;
    }

    /**
     * @return
     */
    public String getLoanAccountID()
    {
        return loanAccountCode;
    }

    /**
     * @return
     */
    public String getLoanNoteID()
    {
        return loanNoteCode;
    }

    /**
     * @return
     */
    public long getLoanTerm()
    {
        return loanTerm;
    }

    /**
     * @return
     */
    public long getLoanType()
    {
        return loanType;
    }

    /**
     * @return
     */
    public Timestamp getStart()
    {
        return start;
    }

    /**
     * @param d
     */
    public void setAssureRate(double d)
    {
        assureRate = d;
    }

    /**
     * @param string
     */
    public void setClientName(String string)
    {
        clientName = string;
    }

    /**
     * @param timestamp
     */
    public void setEnd(Timestamp timestamp)
    {
        end = timestamp;
    }

    /**
     * @param d
     */
    public void setInterestRate(double d)
    {
        interestRate = d;
    }

    /**
     * @param string
     */
    public void setLoanAccountID(String string)
    {
        loanAccountCode = string;
    }

    /**
     * @param string
     */
    public void setLoanNoteID(String string)
    {
        loanNoteCode = string;
    }

    /**
     * @param l
     */
    public void setLoanTerm(long l)
    {
        loanTerm = l;
    }

    /**
     * @param l
     */
    public void setLoanType(long l)
    {
        loanType = l;
    }

    /**
     * @param timestamp
     */
    public void setStart(Timestamp timestamp)
    {
        start = timestamp;
    }

    /**
     * @return
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @return
     */
    public Timestamp getInterestStart()
    {
        return interestStart;
    }

    /**
     * @param d
     */
    public void setAmount(double d)
    {
        amount = d;
    }

    /**
     * @param timestamp
     */
    public void setInterestStart(Timestamp timestamp)
    {
        interestStart = timestamp;
    }

    /**
     * @return
     */
    public String getClientCode()
    {
        return clientCode;
    }

    /**
     * @return
     */
    public long getLoadNoteID()
    {
        return loadNoteID;
    }

    /**
     * @return
     */
    public String getLoanAccountCode()
    {
        return loanAccountCode;
    }

    /**
     * @return
     */
    public String getLoanNoteCode()
    {
        return loanNoteCode;
    }

    /**
     * @param string
     */
    public void setClientCode(String string)
    {
        clientCode = string;
    }

    /**
     * @param l
     */
    public void setLoadNoteID(long l)
    {
        loadNoteID = l;
    }

    /**
     * @param string
     */
    public void setLoanAccountCode(String string)
    {
        loanAccountCode = string;
    }

    /**
     * @param string
     */
    public void setLoanNoteCode(String string)
    {
        loanNoteCode = string;
    }

    /**
     * @return
     */
    public double getPoundage()
    {
        return poundage;
    }

    /**
     * @param d
     */
    public void setPoundage(double d)
    {
        poundage = d;
    }

    /**
     * @return
     */
    public String getContractCode()
    {
        return contractCode;
    }

    /**
     * @param string
     */
    public void setContractCode(String string)
    {
        contractCode = string;
    }

	/**
	 * @return
	 */
	public long getGrantTypeID()
	{
		return GrantTypeID;
	}

	/**
	 * @return
	 */
	public String getReceiveAccountNo()
	{
		return ReceiveAccountNo;
	}

	/**
	 * @return
	 */
	public String getRemitBank()
	{
		return RemitBank;
	}

	/**
	 * @return
	 */
	public String getRemitInCity()
	{
		return RemitInCity;
	}

	/**
	 * @return
	 */
	public String getRemitInProvince()
	{
		return RemitInProvince;
	}

	/**
	 * @param l
	 */
	public void setGrantTypeID(long l)
	{
		GrantTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setReceiveAccountNo(String string)
	{
		ReceiveAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setRemitBank(String string)
	{
		RemitBank = string;
	}

	/**
	 * @param string
	 */
	public void setRemitInCity(String string)
	{
		RemitInCity = string;
	}

	/**
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{
		RemitInProvince = string;
	}

	/**
	 * @return
	 */
	public String getReceiveAccountName()
	{
		return ReceiveAccountName;
	}

	/**
	 * @param string
	 */
	public void setReceiveAccountName(String string)
	{
		ReceiveAccountName = string;
	}

	/**
	 * @return
	 */
	public long getAccountBankID()
	{
		return AccountBankID;
	}

	/**
	 * @return
	 */
	public long getGrantCurrentAccountID()
	{
		return GrantCurrentAccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountBankID(long l)
	{
		AccountBankID = l;
	}

	/**
	 * @param l
	 */
	public void setGrantCurrentAccountID(long l)
	{
		GrantCurrentAccountID = l;
	}

	/**
	 * Returns the commissionRate.
	 * @return double
	 */
	public double getCommissionRate() {
		return commissionRate;
	}

	/**
	 * Sets the commissionRate.
	 * @param commissionRate The commissionRate to set
	 */
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * Returns the outDate.
	 * @return Timestamp
	 */
	public Timestamp getOutDate() {
		return outDate;
	}

	/**
	 * Sets the outDate.
	 * @param outDate The outDate to set
	 */
	public void setOutDate(Timestamp outDate) {
		this.outDate = outDate;
	}

	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
	}

	/**
	 * @return
	 */
	public long getTypeID1()
	{
		return TypeID1;
	}

	/**
	 * @return
	 */
	public long getTypeID2()
	{
		return TypeID2;
	}

	/**
	 * @return
	 */
	public long getTypeID3()
	{
		return TypeID3;
	}

	/**
	 * @param l
	 */
	public void setTypeID1(long l)
	{
		TypeID1 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID2(long l)
	{
		TypeID2 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID3(long l)
	{
		TypeID3 = l;
	}

	public Timestamp getDTEND() {
		return DTEND;
	}

	public void setDTEND(Timestamp dtend) {
		DTEND = dtend;
	}

	public Timestamp getDTSTART() {
		return DTSTART;
	}

	public void setDTSTART(Timestamp dtstart) {
		DTSTART = dtstart;
	}
	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}
	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
	}
	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}
	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}
	
	

}

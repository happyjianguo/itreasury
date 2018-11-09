/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-12
 */
package com.iss.itreasury.treasuryplan.etl.extract.dataentity.loan;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yehuang
 *
 * 贷款参数类
 */
public class LoanContractPlanInfo extends ITreasuryBaseDataEntity {

	private String contractCode = null;
	private long typeID = -1;
	private long borrowClientID = -1;
	private long consignClientID = -1;
	private double amount = 0.0;
	private Timestamp planDate = null;
	private double chargeRate = 0.0;
	private long currencyID = -1;
	private long id = -1; //合同或申请ID
	private long source = -1; //数据来源 1: 合同 2: 申请
	
	private double interestRate = 0.0;
	
	/**
	 * @return Returns the interestRate.
	 */
	public double getInterestRate() {
		return interestRate;
	}
	/**
	 * @param interestRate The interestRate to set.
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	//贴现使用
	private double examineAmount = 0.0;  //批准金额
	private double checkAmount = 0.0;    //汇总贴现核定金额
	private Timestamp discountDate = null; //贴现时间
	
	//银团贷款使用
	private double rate = 0.0;           //承贷比例
	
 
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @return Returns the chargeRate.
	 */
	public double getChargeRate() {
		return chargeRate;
	}
	/**
	 * @param chargeRate The chargeRate to set.
	 */
	public void setChargeRate(double chargeRate) {
		putUsedField("chargeRate", chargeRate);
		this.chargeRate = chargeRate;
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
	 * @return Returns the contractCode.
	 */
	public String getContractCode() {
		return contractCode;
	}
	/**
	 * @param contractCode The contractCode to set.
	 */
	public void setContractCode(String contractCode) {
		putUsedField("contractCode", contractCode);
		this.contractCode = contractCode;
	}
	/**
	 * @return Returns the planDate.
	 */
	public Timestamp getPlanDate() {
		return planDate;
	}
	/**
	 * @param planDate The planDate to set.
	 */
	public void setPlanDate(Timestamp planDate) {
		putUsedField("planDate", planDate);
		this.planDate = planDate;
	}
	/**
	 * @return Returns the typeID.
	 */
	public long getTypeID() {
		return typeID;
	}
	/**
	 * @param typeID The typeID to set.
	 */
	public void setTypeID(long typeID) {
		putUsedField("typeID", typeID);
		this.typeID = typeID;
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
	 * @return Returns the checkAmount.
	 */
	public double getCheckAmount() {
		return checkAmount;
	}
	/**
	 * @param checkAmount The checkAmount to set.
	 */
	public void setCheckAmount(double checkAmount) {
		putUsedField("checkAmount", checkAmount);
		this.checkAmount = checkAmount;
	}
	/**
	 * @return Returns the examineAmount.
	 */
	public double getExamineAmount() {
		return examineAmount;
	}
	/**
	 * @param examineAmount The examineAmount to set.
	 */
	public void setExamineAmount(double examineAmount) {
		putUsedField("examineAmount", examineAmount);
		this.examineAmount = examineAmount;
	}
	/**
	 * @return Returns the discountDate.
	 */
	public Timestamp getDiscountDate() {
		return discountDate;
	}
	/**
	 * @param discountDate The discountDate to set.
	 */
	public void setDiscountDate(Timestamp discountDate) {
		putUsedField("discountDate", discountDate);
		this.discountDate = discountDate;
	}
	/**
	 * @return Returns the rate.
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate The rate to set.
	 */
	public void setRate(double rate) {
		putUsedField("rate", rate);
		this.rate = rate;
	}
	/**
	 * @return Returns the source.
	 */
	public long getSource() {
		return source;
	}
	/**
	 * @param source The source to set.
	 */
	public void setSource(long source) {
		putUsedField("source", source);
		this.source = source;
	}
	
	/**
	 * 重载此方法比较plandate
	 * */
	public boolean equals(Object obj){
		LoanContractPlanInfo comp = (LoanContractPlanInfo) obj;
		if(comp.getPlanDate().equals(planDate))
			return true;
		else 
			return false;
	}

}

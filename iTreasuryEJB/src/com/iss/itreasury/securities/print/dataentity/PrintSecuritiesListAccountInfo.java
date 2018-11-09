/*
 * Created on 2004-08-20
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              kewen hu
 * @version
 *  Date of Creation    2004-08-20
 */
public class PrintSecuritiesListAccountInfo extends SECBaseDataEntity implements Serializable
{
	private long id                        = -1;
	//securitiesId
	private long securitiesId 			   = -1;
	//证券类别Id
	private long securitiesTypeId          = -1;
	//交割单code
	private String deliveryCode            = "";
	//交割日期
	private Timestamp deliveryDate         = null;
	//交易对手名称(证券名称)
	private String securitiesName          = "";
    //交易对手
	private long counterpartId 			   = -1;
	//借方期初金额
	private double startDebitBalance 	   = 0.0;
	//贷方期初金额
	private double startCreditBalance 	   = 0.0;
	//借方金额累计
	private double debitAmount 			   = 0.0;
	//贷方金额累计
	private double creditAmount 		   = 0.0;
	//借方金额余额
	private double endDebitBalance 		   = 0.0;
	//贷方金额余额
	private double endCreditBalance 	   = 0.0;
	//开户营业部期初余额
	private double startCounterPartBalance = 0.00;
    //开户营业部期末余额
	private double endCounterPartBalance   = 0.00;
	//金额方向
	private long capitaldirection          = -1;
	//交易所还是银行间(1,交易所；2,银行间)
	private long exchangeCenterOrBank      = -1;
	//科目类型Id
	private long subjectTypeId             = -1;
	//科目类型名称
	private String subjectName             = "";
	//业务类型
	private String businessType            = "";
	//摘要
	private String remark                  = "";
	//交易对手开户行种类
	private long siteType                  = -1;
    //凭证号
	private String  svoucherno              = "";
	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return Returns the businessType.
	 */
	public String getBusinessType() {
		return businessType;
	}
	/**
	 * @param businessType The businessType to set.
	 */
	public void setBusinessType(String businessType) {
		putUsedField("businessType", businessType);
		this.businessType = businessType;
	}
	/**
	 * @return Returns the capitaldirection.
	 */
	public long getCapitaldirection() {
		return capitaldirection;
	}
	/**
	 * @param capitaldirection The capitaldirection to set.
	 */
	public void setCapitaldirection(long capitaldirection) {
		putUsedField("capitaldirection", capitaldirection);
		this.capitaldirection = capitaldirection;
	}
	/**
	 * @return Returns the counterpartId.
	 */
	public long getCounterpartId() {
		return counterpartId;
	}
	/**
	 * @param counterpartId The counterpartId to set.
	 */
	public void setCounterpartId(long counterpartId) {
		putUsedField("counterpartId", counterpartId);
		this.counterpartId = counterpartId;
	}
	/**
	 * @return Returns the creditAmount.
	 */
	public double getCreditAmount() {
		return creditAmount;
	}
	/**
	 * @param creditAmount The creditAmount to set.
	 */
	public void setCreditAmount(double creditAmount) {
		putUsedField("creditAmount", creditAmount);
		this.creditAmount = creditAmount;
	}
	/**
	 * @return Returns the debitAmount.
	 */
	public double getDebitAmount() {
		return debitAmount;
	}
	/**
	 * @param debitAmount The debitAmount to set.
	 */
	public void setDebitAmount(double debitAmount) {
		putUsedField("debitAmount", debitAmount);
		this.debitAmount = debitAmount;
	}
	/**
	 * @return Returns the deliveryCode.
	 */
	public String getDeliveryCode() {
		return deliveryCode;
	}
	/**
	 * @param deliveryCode The deliveryCode to set.
	 */
	public void setDeliveryCode(String deliveryCode) {
		putUsedField("deliveryCode", deliveryCode);
		this.deliveryCode = deliveryCode;
	}
	/**
	 * @return Returns the deliveryDate.
	 */
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate The deliveryDate to set.
	 */
	public void setDeliveryDate(Timestamp deliveryDate) {
		putUsedField("deliveryDate", deliveryDate);
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return Returns the endCounterPartBalance.
	 */
	public double getEndCounterPartBalance() {
		return endCounterPartBalance;
	}
	/**
	 * @param endCounterPartBalance The endCounterPartBalance to set.
	 */
	public void setEndCounterPartBalance(double endCounterPartBalance) {
		putUsedField("endCounterPartBalance", endCounterPartBalance);
		this.endCounterPartBalance = endCounterPartBalance;
	}
	/**
	 * @return Returns the endCreditBalance.
	 */
	public double getEndCreditBalance() {
		return endCreditBalance;
	}
	/**
	 * @param endCreditBalance The endCreditBalance to set.
	 */
	public void setEndCreditBalance(double endCreditBalance) {
		putUsedField("endCreditBalance", endCreditBalance);
		this.endCreditBalance = endCreditBalance;
	}
	/**
	 * @return Returns the endDebitBalance.
	 */
	public double getEndDebitBalance() {
		return endDebitBalance;
	}
	/**
	 * @param endDebitBalance The endDebitBalance to set.
	 */
	public void setEndDebitBalance(double endDebitBalance) {
		putUsedField("endDebitBalance", endDebitBalance);
		this.endDebitBalance = endDebitBalance;
	}
	/**
	 * @return Returns the exchangeCenterOrBank.
	 */
	public long getExchangeCenterOrBank() {
		return exchangeCenterOrBank;
	}
	/**
	 * @param exchangeCenterOrBank The exchangeCenterOrBank to set.
	 */
	public void setExchangeCenterOrBank(long exchangeCenterOrBank) {
		putUsedField("exchangeCenterOrBank", exchangeCenterOrBank);
		this.exchangeCenterOrBank = exchangeCenterOrBank;
	}
	/**
	 * @return Returns the securitiesId.
	 */
	public long getSecuritiesId() {
		return securitiesId;
	}
	/**
	 * @param securitiesId The securitiesId to set.
	 */
	public void setSecuritiesId(long securitiesId) {
		putUsedField("securitiesId", securitiesId);
		this.securitiesId = securitiesId;
	}
	/**
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}
	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		putUsedField("securitiesName", securitiesName);
		this.securitiesName = securitiesName;
	}
	/**
	 * @return Returns the securitiesTypeId.
	 */
	public long getSecuritiesTypeId() {
		return securitiesTypeId;
	}
	/**
	 * @param securitiesTypeId The securitiesTypeId to set.
	 */
	public void setSecuritiesTypeId(long securitiesTypeId) {
		putUsedField("securitiesTypeId", securitiesTypeId);
		this.securitiesTypeId = securitiesTypeId;
	}
	/**
	 * @return Returns the startCounterPartBalance.
	 */
	public double getStartCounterPartBalance() {
		return startCounterPartBalance;
	}
	/**
	 * @param startCounterPartBalance The startCounterPartBalance to set.
	 */
	public void setStartCounterPartBalance(double startCounterPartBalance) {
		putUsedField("startCounterPartBalance", startCounterPartBalance);
		this.startCounterPartBalance = startCounterPartBalance;
	}
	/**
	 * @return Returns the startCreditBalance.
	 */
	public double getStartCreditBalance() {
		return startCreditBalance;
	}
	/**
	 * @param startCreditBalance The startCreditBalance to set.
	 */
	public void setStartCreditBalance(double startCreditBalance) {
		putUsedField("startCreditBalance", startCreditBalance);
		this.startCreditBalance = startCreditBalance;
	}
	/**
	 * @return Returns the startDebitBalance.
	 */
	public double getStartDebitBalance() {
		return startDebitBalance;
	}
	/**
	 * @param startDebitBalance The startDebitBalance to set.
	 */
	public void setStartDebitBalance(double startDebitBalance) {
		putUsedField("startDebitBalance", startDebitBalance);
		this.startDebitBalance = startDebitBalance;
	}
	/**
	 * @return Returns the subjectName.
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * @param subjectName The subjectName to set.
	 */
	public void setSubjectName(String subjectName) {
		putUsedField("subjectName", subjectName);
		this.subjectName = subjectName;
	}
	/**
	 * @return Returns the subjectTypeId.
	 */
	public long getSubjectTypeId() {
		return subjectTypeId;
	}
	/**
	 * @param subjectTypeId The subjectTypeId to set.
	 */
	public void setSubjectTypeId(long subjectTypeId) {
		putUsedField("subjectTypeId", subjectTypeId);
		this.subjectTypeId = subjectTypeId;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the siteType.
	 */
	public long getSiteType() {
		return siteType;
	}
	/**
	 * @param siteType The siteType to set.
	 */
	public void setSiteType(long siteType) {
		putUsedField("siteType", siteType);
		this.siteType = siteType;
	}
	
	/**
	 * @return Returns the svoucherno.
	 */
	public String getSvoucherno() {
		return svoucherno;
	}
	/**
	 * @param svoucherno The svoucherno to set.
	 */
	public void setSvoucherno(String svoucherno) {
		putUsedField("svoucherno", svoucherno);
		this.svoucherno = svoucherno;
	}
}
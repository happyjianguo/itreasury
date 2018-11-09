package com.iss.itreasury.settlement.generalledger.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;


/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-25
 */
public class GenerateGLEntryParam implements Serializable {
//	办事处编号ID
	private long officeID = -1;
//  付方办事处ID	
	private long payofficeID = -1;
//  收方办事处ID	
	private long receiveofficeID = -1;
//  总部办事处ID	
	private long parentofficeID = -1;
//	交易的币种
	private long currencyID = -1;
//	交易类型
	private long transactionTypeID = -1;
//	执行日
	private Timestamp executeDate = null;	  
//	起息日
	private Timestamp interestStartDate = null;
//	交易编号
	private String transNo = "";
//	摘要
	private String abstractStr = "";
//	录入人
	private long inputUserID = -1;
//	复核人
	private long checkUserID = -1;
	  								
    //本金流向，可空1内部转账2银行 
    private long principalType = -1;
    //利息流向，可空1内部转账2银行
    private long interestType = -1;
    //费用流向，可空1内部转账2银行
	private long commisionType = -1;
	//分录类型,可空0无关1合并2分拆3反冲
	private long entryType = -1;
	//多维码
	private String multiCode = "";
	//交易子类型，缺省值是0，表示无关
	private long subTransactionType = 0;	
	
	//账户
	private long receiveAccountID = -1;//收款方账户ID，可空
	private long payAccountID = -1; //付款方账户ID，可空  
	private long receiveInterestAccountID = -1; //收息账户ID，可空
	private long payInterestAccountID = -1; //付息账户ID，可空    
	private long vouchReceiveAccountID = -1; //委托收款方账户ID，可空
	private long vouchPayAccountID = -1; //委托付款方账户ID，可空
	private long receieveSuertyFeeAccountID = -1; //收担保费账户ID，可空
	private long paySuertyFeeAccountID = -1; //付担保费账户ID，可空 
	private long payCommissionAccountID = -1; //付手续费账户ID，可空
	private long principalBankID = -1; //本金开户行ID，可空
	private long interestBankID = -1; //利息开户行ID，可空
	private long feeBankID = -1; //费用开户行ID，可空
	private long GenaralLedgerTypeID = -1; //总账类类型ID，可空
	
	private long receiveFinanceAccountID = -1;//融资租赁保证金收款方账户ID，可空		add by feiye 2006.5.26
	private long payFinanceAccountID = -1; //融资租赁保证金付款方账户ID，可空  			add by feiye 2006.5.26
	
	private long receiveBakAccountID = -1;//收款方备付金账户ID，可空
	private long payBakAccountID = -1; //付款方备付金账户ID，可空  
	
	//金额
	private double principalOrTransAmount = -1; //本金/交易金额
	private double totalInterest = 0.0; //利息合计，可空
	private double preDrawInterest = 0.0; //计提利息，可空
	private double unPreDrawInterest = 0.0; //未计提利息，可空
	private double overTimeInterest = 0.0; //逾期利息，可空
	private double overFee = 0.0; //罚息，可空	
	private double compoundInterest = 0.0; //复利，可空
	private double suretyFee = 0.0; //担保费，可空
	private double commissionFee = 0.0; //手续费，可空
	private double interestTaxFee = 0.0; //利息税费，可空
	private double totalPrincipalAndInterest = 0.0; //本息合计，可空	
	private double remissionInterest = 0.0; //豁免利息，可空
	private double reallyReceiveInterest = 0.0; //实收利息/税后利息，可空
	private double remitAmount = 0.0;  //豁免本金
	
	private double financeAllAmount = 0.0;  //融资租赁保证金本息合计 		add by feiye 2006.5.26   
	
	private double currentPrincipalAndInterest = 0.0 ;//融资租赁活期本息合计 add by zwxiao 2010-08-17
	
	private double bankPrincipalAndInterest = 0.0 ;//融资租赁开户行本息合计 add by zwxiao 2010-08-17
	
	private boolean AutoCreateBankInstruction = true;//	是否生成银行指令
	/**是否试算平衡，缺省为是，目前只有多笔贷款收回为否*/ 
	private boolean isTrialBalance = true;
	
	private ArrayList list=null;  //银团贷款,银团详细信息
	
	private long craContractID = -1;//转让合同ID
	
	//方正同业资产转让生成会计分录增加
	private double marginAmount = 0.00;//资产转让买入买断【差额】 = 【转让价格】 - 【转让金额】
	private double attornAmount = 0.00;//资产转让【转让金额】
	private double adjustFee    = 0.00;//资产转让【费用调整】
	private double attornPrice  = 0.00;//资产转让【转让价格】
	
	//方正同业生成会计分录增加
	private long craBusinessType = -1;//同业业务类型【6.转贴现、2.资金拆借、1.资产转让】
	private long counterpartId = -1;//同业交易对手ID
	private ArrayList attornmentContractList = null;//信贷资产转让，直营贷款放款单转让详细信息
	private ArrayList transDiscountContractBillList = null;//转贴现票据信息
	
	public long getCraBusinessType() {
		return craBusinessType;
	}

	public void setCraBusinessType(long lCraBusinessType) {
		this.craBusinessType = lCraBusinessType;
	}
	
	public long getCounterpartId() {
		return counterpartId;
	}

	public void setCounterpartId(long lCounterpartId) {
		this.counterpartId = lCounterpartId;
	}
	
	public ArrayList getAttornmentContractList() {
		return attornmentContractList;
	}

	public void setAttornmentContractList(ArrayList attornmentContractList) {
		this.attornmentContractList = attornmentContractList;
	}
    

	public ArrayList getTransDiscountContractBillList() {
		return transDiscountContractBillList;
	}

	public void setTransDiscountContractBillList(
			ArrayList transDiscountContractBillList) {
		this.transDiscountContractBillList = transDiscountContractBillList;
	}
	
	public double getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(double marginAmount) {
		this.marginAmount = marginAmount;
	}

	public double getAttornAmount() {
		return attornAmount;
	}

	public void setAttornAmount(double attornAmount) {
		this.attornAmount = attornAmount;
	}
	
	public double getAttornPrice() {
		return attornPrice;
	}

	public void setAttornPrice(double attornPrice) {
		this.attornPrice = attornPrice;
	}

	public double getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(double adjustFee) {
		this.adjustFee = adjustFee;
	}
	
	
	public long getCraContractID() {
		return craContractID;
	}

	public void setCraContractID(long craContractID) {
		this.craContractID = craContractID;
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	/**
	 * Returns the commissionFee.
	 * @return double
	 */
	public double getCommissionFee() {
		return commissionFee;
	}

	/**
	 * Returns the compoundInterest.
	 * @return double
	 */
	public double getCompoundInterest() {
		return compoundInterest;
	}

	/**
	 * Returns the feeBankID.
	 * @return long
	 */
	public long getFeeBankID() {
		return feeBankID;
	}

	/**
	 * Returns the interestBankID.
	 * @return long
	 */
	public long getInterestBankID() {
		return interestBankID;
	}

	/**
	 * Returns the interestTaxFee.
	 * @return double
	 */
	public double getInterestTaxFee() {
		return interestTaxFee;
	}

	/**
	 * Returns the lCommisionType.
	 * @return long
	 */
	public long getCommisionType() {
		return commisionType;
	}

	/**
	 * Returns the lEntryType.
	 * @return long
	 */
	public long getEntryType() {
		return entryType;
	}

	/**
	 * Returns the lInterestType.
	 * @return long
	 */
	public long getInterestType() {
		return interestType;
	}

	/**
	 * Returns the lPrincipalType.
	 * @return long
	 */
	public long getPrincipalType() {
		return principalType;
	}

	/**
	 * Returns the overFee.
	 * @return double
	 */
	public double getOverFee() {
		return overFee;
	}

	/**
	 * Returns the overTimeInterest.
	 * @return double
	 */
	public double getOverTimeInterest() {
		return overTimeInterest;
	}

	/**
	 * Returns the payAccountID.
	 * @return long
	 */
	public long getPayAccountID() {
		return payAccountID;
	}

	/**
	 * Returns the payCommissionAccountID.
	 * @return long
	 */
	public long getPayCommissionAccountID() {
		return payCommissionAccountID;
	}

	/**
	 * Returns the payInterestAccountID.
	 * @return long
	 */
	public long getPayInterestAccountID() {
		return payInterestAccountID;
	}

	/**
	 * Returns the paySuertyFeeAccountID.
	 * @return long
	 */
	public long getPaySuertyFeeAccountID() {
		return paySuertyFeeAccountID;
	}

	/**
	 * Returns the preDrawInterest.
	 * @return double
	 */
	public double getPreDrawInterest() {
		return preDrawInterest;
	}

	/**
	 * Returns the principalBankID.
	 * @return long
	 */
	public long getPrincipalBankID() {
		return principalBankID;
	}

	/**
	 * Returns the principalOrTransAmount.
	 * @return double
	 */
	public double getPrincipalOrTransAmount() {
		return principalOrTransAmount;
	}

	/**
	 * Returns the reallyReceiveInterest.
	 * @return double
	 */
	public double getReallyReceiveInterest() {
		return reallyReceiveInterest;
	}

	/**
	 * Returns the receieveSuertyFeeAccountID.
	 * @return long
	 */
	public long getReceieveSuertyFeeAccountID() {
		return receieveSuertyFeeAccountID;
	}

	/**
	 * Returns the receiveAccountID.
	 * @return long
	 */
	public long getReceiveAccountID() {
		return receiveAccountID;
	}

	/**
	 * Returns the receiveInterestAccountID.
	 * @return long
	 */
	public long getReceiveInterestAccountID() {
		return receiveInterestAccountID;
	}

	/**
	 * Returns the remissionInterest.
	 * @return double
	 */
	public double getRemissionInterest() {
		return remissionInterest;
	}

	/**
	 * Returns the sMultiCode.
	 * @return String
	 */
	public String getMultiCode() {
		return multiCode;
	}

	/**
	 * Returns the suretyFee.
	 * @return double
	 */
	public double getSuretyFee() {
		return suretyFee;
	}

	/**
	 * Returns the totalInterest.
	 * @return double
	 */
	public double getTotalInterest() {
		return totalInterest;
	}

	/**
	 * Returns the totalPrincipalAndInterest.
	 * @return double
	 */
	public double getTotalPrincipalAndInterest() {
		return totalPrincipalAndInterest;
	}



	/**
	 * Returns the unPreDrawInterest.
	 * @return double
	 */
	public double getUnPreDrawInterest() {
		return unPreDrawInterest;
	}

	/**
	 * Returns the vouchPayAccountID.
	 * @return long
	 */
	public long getVouchPayAccountID() {
		return vouchPayAccountID;
	}

	/**
	 * Returns the vouchReceiveAccountID.
	 * @return long
	 */
	public long getVouchReceiveAccountID() {
		return vouchReceiveAccountID;
	}

	/**
	 * Sets the commissionFee.
	 * @param commissionFee The commissionFee to set
	 */
	public void setCommissionFee(double commissionFee) {
		this.commissionFee = commissionFee;
	}

	/**
	 * Sets the compoundInterest.
	 * @param compoundInterest The compoundInterest to set
	 */
	public void setCompoundInterest(double compoundInterest) {
		this.compoundInterest = compoundInterest;
	}

	/**
	 * Sets the feeBankID.
	 * @param feeBankID The feeBankID to set
	 */
	public void setFeeBankID(long feeBankID) {
		this.feeBankID = feeBankID;
	}

	/**
	 * Sets the interestBankID.
	 * @param interestBankID The interestBankID to set
	 */
	public void setInterestBankID(long interestBankID) {
		this.interestBankID = interestBankID;
	}

	/**
	 * Sets the interestTaxFee.
	 * @param interestTaxFee The interestTaxFee to set
	 */
	public void setInterestTaxFee(double interestTaxFee) {
		this.interestTaxFee = interestTaxFee;
	}

	/**
	 * Sets the lCommisionType.
	 * @param lCommisionType The lCommisionType to set
	 */
	public void setCommisionType(long lCommisionType) {
		this.commisionType = lCommisionType;
	}

	/**
	 * Sets the lEntryType.
	 * @param lEntryType The lEntryType to set
	 */
	public void setEntryType(long lEntryType) {
		this.entryType = lEntryType;
	}

	/**
	 * Sets the lInterestType.
	 * @param lInterestType The lInterestType to set
	 */
	public void setInterestType(long lInterestType) {
		this.interestType = lInterestType;
	}

	/**
	 * Sets the lPrincipalType.
	 * @param lPrincipalType The lPrincipalType to set
	 */
	public void setPrincipalType(long lPrincipalType) {
		this.principalType = lPrincipalType;
	}

	/**
	 * Sets the overFee.
	 * @param overFee The overFee to set
	 */
	public void setOverFee(double overFee) {
		this.overFee = overFee;
	}

	/**
	 * Sets the overTimeInterest.
	 * @param overTimeInterest The overTimeInterest to set
	 */
	public void setOverTimeInterest(double overTimeInterest) {
		this.overTimeInterest = overTimeInterest;
	}

	/**
	 * Sets the payAccountID.
	 * @param payAccountID The payAccountID to set
	 */
	public void setPayAccountID(long payAccountID) {
		this.payAccountID = payAccountID;
	}

	/**
	 * Sets the payCommissionAccountID.
	 * @param payCommissionAccountID The payCommissionAccountID to set
	 */
	public void setPayCommissionAccountID(long payCommissionAccountID) {
		this.payCommissionAccountID = payCommissionAccountID;
	}

	/**
	 * Sets the payInterestAccountID.
	 * @param payInterestAccountID The payInterestAccountID to set
	 */
	public void setPayInterestAccountID(long payInterestAccountID) {
		this.payInterestAccountID = payInterestAccountID;
	}

	/**
	 * Sets the paySuertyFeeAccountID.
	 * @param paySuertyFeeAccountID The paySuertyFeeAccountID to set
	 */
	public void setPaySuertyFeeAccountID(long paySuertyFeeAccountID) {
		this.paySuertyFeeAccountID = paySuertyFeeAccountID;
	}

	/**
	 * Sets the preDrawInterest.
	 * @param preDrawInterest The preDrawInterest to set
	 */
	public void setPreDrawInterest(double preDrawInterest) {
		this.preDrawInterest = preDrawInterest;
	}

	/**
	 * Sets the principalBankID.
	 * @param principalBankID The principalBankID to set
	 */
	public void setPrincipalBankID(long principalBankID) {
		this.principalBankID = principalBankID;
	}

	/**
	 * Sets the principalOrTransAmount.
	 * @param principalOrTransAmount The principalOrTransAmount to set
	 */
	public void setPrincipalOrTransAmount(double principalOrTransAmount) {
		this.principalOrTransAmount = principalOrTransAmount;
	}

	/**
	 * Sets the reallyReceiveInterest.
	 * @param reallyReceiveInterest The reallyReceiveInterest to set
	 */
	public void setReallyReceiveInterest(double reallyReceiveInterest) {
		this.reallyReceiveInterest = reallyReceiveInterest;
	}

	/**
	 * Sets the receieveSuertyFeeAccountID.
	 * @param receieveSuertyFeeAccountID The receieveSuertyFeeAccountID to set
	 */
	public void setReceieveSuertyFeeAccountID(long receieveSuertyFeeAccountID) {
		this.receieveSuertyFeeAccountID = receieveSuertyFeeAccountID;
	}

	/**
	 * Sets the receiveAccountID.
	 * @param receiveAccountID The receiveAccountID to set
	 */
	public void setReceiveAccountID(long receiveAccountID) {
		this.receiveAccountID = receiveAccountID;
	}

	/**
	 * Sets the receiveInterestAccountID.
	 * @param receiveInterestAccountID The receiveInterestAccountID to set
	 */
	public void setReceiveInterestAccountID(long receiveInterestAccountID) {
		this.receiveInterestAccountID = receiveInterestAccountID;
	}

	/**
	 * Sets the remissionInterest.
	 * @param remissionInterest The remissionInterest to set
	 */
	public void setRemissionInterest(double remissionInterest) {
		this.remissionInterest = remissionInterest;
	}

	/**
	 * Sets the sMultiCode.
	 * @param sMultiCode The sMultiCode to set
	 */
	public void setMultiCode(String sMultiCode) {
		this.multiCode = sMultiCode;
	}

	/**
	 * Sets the suretyFee.
	 * @param suretyFee The suretyFee to set
	 */
	public void setSuretyFee(double suretyFee) {
		this.suretyFee = suretyFee;
	}

	/**
	 * Sets the totalInterest.
	 * @param totalInterest The totalInterest to set
	 */
	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	/**
	 * Sets the totalPrincipalAndInterest.
	 * @param totalPrincipalAndInterest The totalPrincipalAndInterest to set
	 */
	public void setTotalPrincipalAndInterest(double totalPrincipalAndInterest) {
		this.totalPrincipalAndInterest = totalPrincipalAndInterest;
	}



	/**
	 * Sets the unPreDrawInterest.
	 * @param unPreDrawInterest The unPreDrawInterest to set
	 */
	public void setUnPreDrawInterest(double unPreDrawInterest) {
		this.unPreDrawInterest = unPreDrawInterest;
	}

	/**
	 * Sets the vouchPayAccountID.
	 * @param vouchPayAccountID The vouchPayAccountID to set
	 */
	public void setVouchPayAccountID(long vouchPayAccountID) {
		this.vouchPayAccountID = vouchPayAccountID;
	}

	/**
	 * Sets the vouchReceiveAccountID.
	 * @param vouchReceiveAccountID The vouchReceiveAccountID to set
	 */
	public void setVouchReceiveAccountID(long vouchReceiveAccountID) {
		this.vouchReceiveAccountID = vouchReceiveAccountID;
	}
	
	

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer result = new StringBuffer(255);
		result.append("CommissionFee=");
		result.append(this.getCommissionFee());
		result.append("\r\n");
		result.append("CompoundInterest=");
		result.append(this.getCompoundInterest());
		result.append("\r\n");
		result.append("FeeBankID=");
		result.append(this.getFeeBankID());
		result.append("\r\n");
		result.append("InterestBankID=");
		result.append(this.getInterestBankID());
		result.append("\r\n");
		result.append("InterestTaxFee=");
		result.append(this.getInterestTaxFee());
		result.append("\r\n");
		result.append("CommisionType=");
		result.append(this.getCommisionType());
		result.append("\r\n");
		result.append("EntryType=");
		result.append(this.getEntryType());
		result.append("\r\n");
		result.append("InterestType=");
		result.append(this.getInterestType());
		result.append("\r\n");
		result.append("PrincipalType=");
		result.append(this.getPrincipalType());
		result.append("\r\n");
		result.append("OverFee=");
		result.append(this.getOverFee());
		result.append("\r\n");
		result.append("OverTimeInterest=");
		result.append(this.getOverTimeInterest());
		result.append("\r\n");
		result.append("PayAccountID=");
		result.append(this.getPayAccountID());
		result.append("\r\n");
		result.append("PayCommissionAccountID=");
		result.append(this.getPayCommissionAccountID());
		result.append("\r\n");
		result.append("PayInterestAccountID=");
		result.append(this.getPayInterestAccountID());
		result.append("\r\n");
		result.append("PaySuertyFeeAccountID=");
		result.append(this.getPaySuertyFeeAccountID());
		result.append("\r\n");
		result.append("PreDrawInterest=");
		result.append(this.getPreDrawInterest());
		result.append("\r\n");
		result.append("PrincipalBankID=");
		result.append(this.getPrincipalBankID());
		result.append("\r\n");
		result.append("PrincipalOrTransAmount=");
		result.append(this.getPrincipalOrTransAmount());
		result.append("\r\n");
		result.append("ReallyReceiveInterest=");
		result.append(this.getReallyReceiveInterest());
		result.append("\r\n");
		result.append("ReceieveSuertyFeeAccountID=");
		result.append(this.getReceieveSuertyFeeAccountID());
		result.append("\r\n");
		result.append("ReceiveAccountID=");
		result.append(this.getReceiveAccountID());
		result.append("\r\n");
		result.append("ReceiveInterestAccountID=");
		result.append(this.getReceiveInterestAccountID());
		result.append("\r\n");
		result.append("RemissionInterest=");
		result.append(this.getRemissionInterest());
		result.append("\r\n");
		result.append("MultiCode=");
		result.append(this.getMultiCode());
		result.append("\r\n");
		result.append("SuretyFee=");
		result.append(this.getSuretyFee());
		result.append("\r\n");
		result.append("TotalInterest=");
		result.append(this.getTotalInterest());
		result.append("\r\n");
		result.append("TotalPrincipalAndInterest=");
		result.append(this.getTotalPrincipalAndInterest());
		result.append("\r\n");
		//result.append("TransInfo=");
		//result.append(this.getTransInfo());
		//result.append("\r\n");
		result.append("UnPreDrawInterest=");
		result.append(this.getUnPreDrawInterest());
		result.append("\r\n");
		result.append("VouchPayAccountID=");
		result.append(this.getVouchPayAccountID());
		result.append("\r\n");
		result.append("VouchReceiveAccountID=");
		result.append(this.getVouchReceiveAccountID());
		result.append("\r\n");
		
		return result.toString();

	}

/**
 * Returns the abstractStr.
 * @return String
 */
public String getAbstractStr() {
	return abstractStr;
}

/**
 * Returns the checkUserID.
 * @return long
 */
public long getCheckUserID() {
	return checkUserID;
}

/**
 * Returns the currencyID.
 * @return long
 */
public long getCurrencyID() {
	return currencyID;
}

/**
 * Returns the executeDate.
 * @return Timestamp
 */
public Timestamp getExecuteDate() {
	return executeDate;
}

/**
 * Returns the inputUserID.
 * @return long
 */
public long getInputUserID() {
	return inputUserID;
}

/**
 * Returns the interestStartDate.
 * @return Timestamp
 */
public Timestamp getInterestStartDate() {
	return interestStartDate;
}

/**
 * Returns the officeID.
 * @return long
 */
public long getOfficeID() {
	return officeID;
}

/**
 * Returns the transactionTypeID.
 * @return long
 */
public long getTransactionTypeID() {
	return transactionTypeID;
}

/**
 * Returns the transNo.
 * @return String
 */
public String getTransNo() {
	return transNo;
}

/**
 * Sets the abstractStr.
 * @param abstractStr The abstractStr to set
 */
public void setAbstractStr(String abstractStr) {
	this.abstractStr = abstractStr;
}

/**
 * Sets the checkUserID.
 * @param checkUserID The checkUserID to set
 */
public void setCheckUserID(long checkUserID) {
	this.checkUserID = checkUserID;
}

/**
 * Sets the currencyID.
 * @param currencyID The currencyID to set
 */
public void setCurrencyID(long currencyID) {
	this.currencyID = currencyID;
}

/**
 * Sets the executeDate.
 * @param executeDate The executeDate to set
 */
public void setExecuteDate(Timestamp executeDate) {
	this.executeDate = executeDate;
}

/**
 * Sets the inputUserID.
 * @param inputUserID The inputUserID to set
 */
public void setInputUserID(long inputUserID) {
	this.inputUserID = inputUserID;
}

/**
 * Sets the interestStartDate.
 * @param interestStartDate The interestStartDate to set
 */
public void setInterestStartDate(Timestamp interestStartDate) {
	this.interestStartDate = interestStartDate;
}

/**
 * Sets the officeID.
 * @param officeID The officeID to set
 */
public void setOfficeID(long officeID) {
	this.officeID = officeID;
}

/**
 * Sets the transactionTypeID.
 * @param transactionTypeID The transactionTypeID to set
 */
public void setTransactionTypeID(long transactionTypeID) {
	this.transactionTypeID = transactionTypeID;
}

/**
 * Sets the transNo.
 * @param transNo The transNo to set
 */
public void setTransNo(String transNo) {
	this.transNo = transNo;
}

	/**
	 * Returns the isTrialBalance.
	 * @return boolean
	 */
	public boolean isTrialBalance() {
		return isTrialBalance;
	}

	/**
	 * Sets the isTrialBalance.
	 * @param isTrialBalance The isTrialBalance to set
	 */
	public void setTrialBalance(boolean isTrialBalance) {
		this.isTrialBalance = isTrialBalance;
	}

	/**
	 * Returns the remitAmount.
	 * @return double
	 */
	public double getRemitAmount() {
		return remitAmount;
	}

	/**
	 * Sets the remitAmount.
	 * @param remitAmount The remitAmount to set
	 */
	public void setRemitAmount(double remitAmount) {
		this.remitAmount = remitAmount;
	}

	/**
	 * Returns the subTransactionType.
	 * @return long
	 */
	public long getSubTransactionType() {
		return subTransactionType;
	}

	/**
	 * Sets the subTransactionType.
	 * @param subTransactionType The subTransactionType to set
	 */
	public void setSubTransactionType(long subTransactionType) {
		this.subTransactionType = subTransactionType;
	}

	/**
	 * @return Returns the autoCreateBankInstruction.
	 */
	public boolean isAutoCreateBankInstruction()
	{
		return AutoCreateBankInstruction;
	}
	/**
	 * @param autoCreateBankInstruction The autoCreateBankInstruction to set.
	 */
	public void setAutoCreateBankInstruction(boolean autoCreateBankInstruction)
	{
		AutoCreateBankInstruction = autoCreateBankInstruction;
	}
	/**
	 * @return Returns the genaralLedgerTypeID.
	 */
	public long getGenaralLedgerTypeID()
	{
		return GenaralLedgerTypeID;
	}
	/**
	 * @param genaralLedgerTypeID The genaralLedgerTypeID to set.
	 */
	public void setGenaralLedgerTypeID(long genaralLedgerTypeID)
	{
		GenaralLedgerTypeID = genaralLedgerTypeID;
	}
	/**
	 * @return Returns the payFinanceAccountID.
	 */
	public long getPayFinanceAccountID() {
		return payFinanceAccountID;
	}
	/**
	 * @param payFinanceAccountID The payFinanceAccountID to set.
	 */
	public void setPayFinanceAccountID(long payFinanceAccountID) {
		this.payFinanceAccountID = payFinanceAccountID;
	}
	/**
	 * @return Returns the receiveFinanceAccountID.
	 */
	public long getReceiveFinanceAccountID() {
		return receiveFinanceAccountID;
	}
	/**
	 * @param receiveFinanceAccountID The receiveFinanceAccountID to set.
	 */
	public void setReceiveFinanceAccountID(long receiveFinanceAccountID) {
		this.receiveFinanceAccountID = receiveFinanceAccountID;
	}
	/**
	 * @return Returns the financeAllAmount.
	 */
	public double getFinanceAllAmount() {
		return financeAllAmount;
	}
	/**
	 * @param financeAllAmount The financeAllAmount to set.
	 */
	public void setFinanceAllAmount(double financeAllAmount) {
		this.financeAllAmount = financeAllAmount;
	}

	public double getCurrentPrincipalAndInterest() {
		return currentPrincipalAndInterest;
	}

	public void setCurrentPrincipalAndInterest(double currentPrincipalAndInterest) {
		this.currentPrincipalAndInterest = currentPrincipalAndInterest;
	}

	public double getBankPrincipalAndInterest() {
		return bankPrincipalAndInterest;
	}

	public void setBankPrincipalAndInterest(double bankPrincipalAndInterest) {
		this.bankPrincipalAndInterest = bankPrincipalAndInterest;
	}

	public long getPayofficeID() {
		return payofficeID;
	}

	public void setPayofficeID(long payofficeID) {
		this.payofficeID = payofficeID;
	}

	public long getReceiveofficeID() {
		return receiveofficeID;
	}

	public void setReceiveofficeID(long receiveofficeID) {
		this.receiveofficeID = receiveofficeID;
	}

	public long getParentofficeID() {
		return parentofficeID;
	}

	public void setParentofficeID(long parentofficeID) {
		this.parentofficeID = parentofficeID;
	}

	public long getReceiveBakAccountID() {
		return receiveBakAccountID;
	}

	public void setReceiveBakAccountID(long receiveBakAccountID) {
		this.receiveBakAccountID = receiveBakAccountID;
	}

	public long getPayBakAccountID() {
		return payBakAccountID;
	}

	public void setPayBakAccountID(long payBakAccountID) {
		this.payBakAccountID = payBakAccountID;
	}

}

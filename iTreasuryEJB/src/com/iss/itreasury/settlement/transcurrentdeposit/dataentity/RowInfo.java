/*
 * Created on 2004-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transcurrentdeposit.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RowInfo implements Serializable {
	
	/**
	 * 行号
	 */
	private int rowNum=-1;
	
	/**
	 * 起息日
	 *
	 */
	private String interestStartDate = null ;
	
	/**
	 * 付款人账号
	 */
	private String payAccountCode ="";
	
	/**
	 * 收款人名称
	 */
	private String extClientName =""; 
	/**
	 * 收款人账号
	 */
	private String extAccountCode ="";  
	 
	/**
	 * 汇入地(省)
	 */
	private String remitInProvince =""; 
	/**
	 * 汇入地(市)
	 */
	private String remitInCity ="";    
	/**
	 * 汇入行名称
	 */
	private String remitInBank ="";  
	/**
	 * 金 额
	 */
	private String amount ="";
	

	
	/**
	 * 摘要 
	 */
	private String strAbstract ="";
	
	/**
	 * 本行有错误信息
	 */
	private Hashtable errors = new Hashtable(); 
	
	/**
	 * 结息时期clearInterest CLEAR INTEREST
	 */
	private Timestamp clearInterest;
	
	/**
	 * 开户日期
	 */
	private Timestamp open;
	
//	付款账户ID
	private long payAccountID = -1;
	//账户余额
	private double accountBalance = 0.0;
//	付款客户ID
	private long payClientID = -1;
	
	/**
	 * 
	 */
	public RowInfo() {
	}

	public static void main(String[] args) {
	}
	
	/**
	 * @return Returns the dAmount.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount The dAmount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the errors.
	 */
	public Hashtable getErrors() {
		return errors;
	}
	/**
	 * @param errors The errors to set.
	 */
	public void setErrors(Hashtable errors) {
		this.errors = errors;
	}
	/**
	 * @return Returns the extAccountCode.
	 */
	public String getExtAccountCode() {
		return extAccountCode;
	}
	/**
	 * @param extAccountCode The extAccountCode to set.
	 */
	public void setExtAccountCode(String extAccountCode) {
		this.extAccountCode = extAccountCode;
	}
	/**
	 * @return Returns the extClientName.
	 */
	public String getExtClientName() {
		return extClientName;
	}
	/**
	 * @param extClientName The extClientName to set.
	 */
	public void setExtClientName(String extClientName) {
		this.extClientName = extClientName;
	}
	/**
	 * @return Returns the interestStartDate.
	 */
	public String getInterestStartDate() {
		return interestStartDate;
	}
	/**
	 * @param interestStartDate The interestStartDate to set.
	 */
	public void setInterestStartDate(String interestStartDate) {
		this.interestStartDate = interestStartDate;
	}
	/**
	 * @return Returns the payAccountCode.
	 */
	public String getPayAccountCode() {
		return payAccountCode;
	}
	/**
	 * @param payAccountCode The payAccountCode to set.
	 */
	public void setPayAccountCode(String payAccountCode) {
		this.payAccountCode = payAccountCode;
	}
	/**
	 * @return Returns the remitInBank.
	 */
	public String getRemitInBank() {
		return remitInBank;
	}
	/**
	 * @param remitInBank The remitInBank to set.
	 */
	public void setRemitInBank(String remitInBank) {
		this.remitInBank = remitInBank;
	}
	/**
	 * @return Returns the remitInCity.
	 */
	public String getRemitInCity() {
		return remitInCity;
	}
	/**
	 * @param remitInCity The remitInCity to set.
	 */
	public void setRemitInCity(String remitInCity) {
		this.remitInCity = remitInCity;
	}
	/**
	 * @return Returns the remitInProvince.
	 */
	public String getRemitInProvince() {
		return remitInProvince;
	}
	/**
	 * @param remitInProvince The remitInProvince to set.
	 */
	public void setRemitInProvince(String remitInProvince) {
		this.remitInProvince = remitInProvince;
	}
	/**
	 * @return Returns the rowNo.
	 */
	public int getRowNum() {
		return rowNum;
	}
	/**
	 * @param rowNo The rowNo to set.
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	/**
	 * @return Returns the strAbstract.
	 */
	public String getStrAbstract() {
		return strAbstract;
	}
	/**
	 * @param strAbstract The strAbstract to set.
	 */
	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
	}
	
	public void putError(String param,String msg){
		if(param==null || param.equals("")||msg==null || msg.equals("")){
			
		}else{
			errors.put(param,msg);
		}
	}
	
	/**
	 * @return Returns the accountBalance.
	 */
	public double getAccountBalance() {
		return accountBalance;
	}
	/**
	 * @param accountBalance The accountBalance to set.
	 */
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
/**
 * @return Returns the payAccountID.
 */
public long getPayAccountID() {
	return payAccountID;
}
/**
 * @param payAccountID The payAccountID to set.
 */
public void setPayAccountID(long payAccountID) {
	this.payAccountID = payAccountID;
}
/**
 * @return Returns the payClientID.
 */
public long getPayClientID() {
	return payClientID;
}
/**
 * @param payClientID The payClientID to set.
 */
public void setPayClientID(long payClientID) {
	this.payClientID = payClientID;
}

	/**
	 * @return Returns the clearInterest.
	 */
	public Timestamp getClearInterest() {
		return clearInterest;
	}
	/**
	 * @param clearInterest The clearInterest to set.
	 */
	public void setClearInterest(Timestamp clearInterest) {
		this.clearInterest = clearInterest;
	}
	/**
	 * @return Returns the open.
	 */
	public Timestamp getOpen() {
		return open;
	}
	/**
	 * @param open The open to set.
	 */
	public void setOpen(Timestamp open) {
		this.open = open;
	}
}

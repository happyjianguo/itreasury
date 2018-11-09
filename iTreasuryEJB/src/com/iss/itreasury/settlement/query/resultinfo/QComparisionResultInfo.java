/*
 * Created on 2005-8-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.query.resultinfo;

/**
 * @author hkzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QComparisionResultInfo {
	
	private String AccountNo = "";//账户号
	private String ClientCode = "";//客户号
	private String AccountName = "";//账户名称
	private long CurrencyID = -1;//币种
	private String BankType = "";//银行类型
	private String BankAccountNo = "";//银行账户号
	private String BankName = "";//银行账户名称
	
	
	

	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName() {
		return AccountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return AccountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}
	/**
	 * @return Returns the bankAccountNo.
	 */
	public String getBankAccountNo() {
		return BankAccountNo;
	}
	/**
	 * @param bankAccountNo The bankAccountNo to set.
	 */
	public void setBankAccountNo(String bankAccountNo) {
		BankAccountNo = bankAccountNo;
	}
	/**
	 * @return Returns the bankName.
	 */
	public String getBankName() {
		return BankName;
	}
	/**
	 * @param bankName The bankName to set.
	 */
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	/**
	 * @return Returns the bankType.
	 */
	public String getBankType() {
		return BankType;
	}
	/**
	 * @param bankType The bankType to set.
	 */
	public void setBankType(String bankType) {
		BankType = bankType;
	}
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode() {
		return ClientCode;
	}
	/**
	 * @param clientCode The clientCode to set.
	 */
	public void setClientCode(String clientCode) {
		ClientCode = clientCode;
	}
	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
}

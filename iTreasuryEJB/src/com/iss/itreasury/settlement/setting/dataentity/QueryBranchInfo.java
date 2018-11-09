package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryBranchInfo implements Serializable
{
	/**
	 * 
	 */
	public QueryBranchInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long OrderValue = -1;
	private long DescValue = -1;
	private long OfficeID = -1; 
	private long CurrencyID = -1;
	private long IsSingleBank = -1;
	private long BranchStartID = -1;
	private long BranchEndID = -1;
	private long BankType = -1;
	private String BANKACCOUNTCODE = "";
	private String BranchName = "";
	private long AccountModule = -1;//账户类型
	private long StrbankSubjectType =-1;//开户行科目类型
	public long getStrbankSubjectType() {
		return StrbankSubjectType;
	}

	public void setStrbankSubjectType(long strbankSubjectType) {
		StrbankSubjectType = strbankSubjectType;
	}

	public long getAccountModule() {
		return AccountModule;
	}

	public void setAccountModule(long accountModule) {
		AccountModule = accountModule;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getIsSingleBank() {
		return IsSingleBank;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}


	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setIsSingleBank(long l) {
		IsSingleBank = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getBranchEndID() {
		return BranchEndID;
	}

	/**
	 * @return
	 */
	public long getBranchStartID() {
		return BranchStartID;
	}

	/**
	 * @param l
	 */
	public void setBranchEndID(long l) {
		BranchEndID = l;
	}

	/**
	 * @param l
	 */
	public void setBranchStartID(long l) {
		BranchStartID = l;
	}

	/**
	 * @return
	 */
	public long getBankType() {
		return BankType;
	}

	/**
	 * @param l
	 */
	public void setBankType(long l) {
		BankType = l;
	}

	/**
	 * @return
	 */
	public String getBANKACCOUNTCODE() {
		return BANKACCOUNTCODE;
	}

	/**
	 * @param string
	 */
	public void setBANKACCOUNTCODE(String string) {
		BANKACCOUNTCODE = string;
	}

	/**
	 * @return
	 */
	public long getDescValue() {
		return DescValue;
	}

	/**
	 * @return
	 */
	public long getOrderValue() {
		return OrderValue;
	}

	/**
	 * @param l
	 */
	public void setDescValue(long l) {
		DescValue = l;
	}

	/**
	 * @param l
	 */
	public void setOrderValue(long l) {
		OrderValue = l;
	}

	/**
	 * @return
	 */
	public String getBranchName() {
		return BranchName;
	}

	/**
	 * @param string
	 */
	public void setBranchName(String string) {
		BranchName = string;
	}

}
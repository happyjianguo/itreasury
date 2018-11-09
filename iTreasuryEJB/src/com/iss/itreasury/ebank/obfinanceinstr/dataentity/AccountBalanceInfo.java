/*
 * Created on 2003-9-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import com.iss.itreasury.util.*;

public class AccountBalanceInfo implements java.io.Serializable
{

	/** Creates new AccountSetting */
	public AccountBalanceInfo()
	{
	}

	private double dCurrentBalance = 0.0; // 当前金额
	private double dUsableBalance = 0.0; // 可用金额
	private double dOverdraftAmount = 0.0; // 透支限额
	private double dMaxUsableAmount = 0.0; // 最大可发放金额
	private long lIsCycleAccount = 0; // 是否是循环贷款账户
	/**
	 * @return
	 */
	public double getCurrentBalance() {
		return dCurrentBalance;
	}
	
	public String getFormatCurrentBalance()
	{
		return DataFormat.formatDisabledAmount(dCurrentBalance);
	}
	/**
	 * @return
	 */
	public double getMaxUsableAmount() {
		return dMaxUsableAmount;
	}
	
	public String getFormatMaxUsableAmount()
	{
		return DataFormat.formatDisabledAmount(dMaxUsableAmount);
	}

	/**
	 * @return
	 */
	public double getOverdraftAmount() {
		return dOverdraftAmount;
	}
	
	public String getFormatOverdraftAmount()
	{
		return DataFormat.formatDisabledAmount(dOverdraftAmount);
	}

	/**
	 * @return
	 */
	public double getUsableBalance() {
		return dUsableBalance;
	}

	public String getFormatUsableBalance()
	{
		return DataFormat.formatDisabledAmount(dUsableBalance);
	}
	/**
	 * @return
	 */
	public long getIsCycleAccount() {
		return lIsCycleAccount;
	}

	/**
	 * @param d
	 */
	public void setCurrentBalance(double d) {
		dCurrentBalance = d;
	}

	/**
	 * @param d
	 */
	public void setMaxUsableAmount(double d) {
		dMaxUsableAmount = d;
	}

	/**
	 * @param d
	 */
	public void setOverdraftAmount(double d) {
		dOverdraftAmount = d;
	}

	/**
	 * @param d
	 */
	public void setUsableBalance(double d) {
		dUsableBalance = d;
	}

	/**
	 * @param l
	 */
	public void setIsCycleAccount(long l) {
		lIsCycleAccount = l;
	}

}

/*
 * Created on 2004-11-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.DataFormat;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DestinationBalanceInfo {

	private String bank_code=" ";
	private String account = " ";
	private Timestamp deal_date = null ;
	private String currence_code =" ";
	private double credit = 0.0;        //当日贷方发生额
	private double debt = 0.0;          //当日借方发生额
	private double balance = 0.0;       //余额
	private String tlrno="0";
	private double lasttimeBalance = 0.0;  //上一日的余额
	
	
	/**
	 * 
	 */
	public DestinationBalanceInfo() {

	}

	/**
	 * @return Returns the account.
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account The account to set.
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return Returns the balance.
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @return Returns the bank_code.
	 */
	public String getBank_code() {
		return bank_code;
	}
	/**
	 * @param bank_code The bank_code to set.
	 */
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	/**
	 * @return Returns the credit.
	 */
	public double getCredit() {
		return credit;
	}
	/**
	 * 收入(贷)
	 * @param credit The credit to set.
	 */
	public void setCredit(double credit) {
		this.credit = this.credit + credit;
		this.balance = this.balance + credit;
	}
	/**
	 * @return Returns the currence_code.
	 */
	public String getCurrence_code() {
		return currence_code;
	}
	/**
	 * @param currence_code The currence_code to set.
	 */
	public void setCurrence_code(String currence_code) {
		this.currence_code = currence_code;
	}
	/**
	 * @return Returns the deal_date.
	 */
	public Timestamp getDeal_date() {
		return deal_date;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStringDeal_date(){
		return DataFormat.formatDate(deal_date);
	}
	/**
	 * @param deal_date The deal_date to set.
	 */
	public void setDeal_date(Timestamp deal_date) {
		this.deal_date = deal_date;
	}
	/**
	 * @return Returns the debt.
	 */
	public double getDebt() {
		return debt;
	}
	/**
	 * 支出（借）
	 * @param debt The debt to set.
	 */
	public void setDebt(double debt) {
		this.debt = this.debt + debt;
		this.balance = this.balance - debt;
	}
	/**
	 * @return Returns the lasttimeBalance.
	 */
	public double getLasttimeBalance() {
		return lasttimeBalance;
	}
	/**
	 * @param lasttimeBalance The lasttimeBalance to set.
	 */
	public void setLasttimeBalance(double lasttimeBalance) {
		if(this.lasttimeBalance==0.0){
			this.lasttimeBalance = lasttimeBalance;
			this.balance = this.lasttimeBalance;
		}else if(this.lasttimeBalance!=lasttimeBalance){
			this.balance = this.balance 
			                - this.lasttimeBalance 
							+ lasttimeBalance;
			this.lasttimeBalance = lasttimeBalance;
		}
	}
	/**
	 * @return Returns the tlrno.
	 */
	public String getTlrno() {
		return tlrno;
	}
	/**
	 * @param tlrno The tlrno to set.
	 */
	public void setTlrno(String tlrno) {
		this.tlrno = tlrno;
	}

}

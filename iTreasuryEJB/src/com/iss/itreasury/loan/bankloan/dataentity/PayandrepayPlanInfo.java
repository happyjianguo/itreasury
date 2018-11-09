package com.iss.itreasury.loan.bankloan.dataentity;
/**
 * @author yyhe
 * Created on 2006-10-20
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PayandrepayPlanInfo implements java.io.Serializable{
	
	private long id = -1; 
	private double amount = 0.0;//金额（本金）
	private long bankLoanID = -1;//银行台账ID
	private String executeDate = "";//日期
	private long statusID = -1; //状态
	public long getBankLoanID() {
		return bankLoanID;
	}
	public void setBankLoanID(long bankLoanID) {
		this.bankLoanID = bankLoanID;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(String executeDate) {
		this.executeDate = executeDate;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	
}

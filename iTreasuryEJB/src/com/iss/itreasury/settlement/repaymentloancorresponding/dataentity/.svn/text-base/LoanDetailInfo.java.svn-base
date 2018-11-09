/*
 * LoanDetailInfo.java
 *
 * Created on 2004年10月25日; 
 */

package com.iss.itreasury.settlement.repaymentloancorresponding.dataentity;

import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.util.*;

/**
 *
 * @author  pepsi
 * @version
 */
public class LoanDetailInfo extends SettlementBaseDataEntity
{
    private long id = -1;			//id
    private long transId = -1;		//对应的交易id
    private double balance = 0.0;	//余额
    private Timestamp loanDate = null;//时间
    private long status = -1;		//状态id
 
	/**
	 * @return Returns the balance.
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance) {
		this.balance = balance;
		putUsedField("balance", balance);
	}
	/**
	 * @return Returns the loanDate.
	 */
	public Timestamp getLoanDate() {
		return loanDate;
	}
	/**
	 * @param loanDate The loanDate to set.
	 */
	public void setLoanDate(Timestamp loanDate) {
		this.loanDate = loanDate;
		putUsedField("loanDate", loanDate);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	/**
	 * @return Returns the status.
	 */
	public long getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(long status) {
		this.status = status;
		putUsedField("status", status);
	}
	/**
	 * @return Returns the transId.
	 */
	public long getTransId() {
		return transId;
	}
	/**
	 * @param transId The transId to set.
	 */
	public void setTransId(long transId) {
		this.transId = transId;
		putUsedField("transId", transId);
	}
}

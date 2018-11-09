/*
 * RepaymentDetailInfo.java
 *
 * Created on 2004年10月25日; 
 */

package com.iss.itreasury.settlement.repaymentloancorresponding.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.*;

/**
 *
 * @author  pepsi
 * @version
 */
public class RepaymentDetailInfo extends SettlementBaseDataEntity
{
    private long id = -1;			//id
    private long transId = -1;		//对应的交易id
    private long loanDetailId = -1;	//对应收款明细id
    private double amount = 0.0;	//金额
    private long status = -1;		//状态id
    private Timestamp repaymentDate = null;//时间
 

	/**
	 * @return Returns the repaymentDate.
	 */
	public Timestamp getRepaymentDate() {
		return repaymentDate;
	}
	/**
	 * @param repaymentDate The repaymentDate to set.
	 */
	public void setRepaymentDate(Timestamp repaymentDate) {
		this.repaymentDate = repaymentDate;
		putUsedField("repaymentDate", repaymentDate);
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
		this.amount = amount;
		putUsedField("amount", amount);
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
	 * @return Returns the loanDetailId.
	 */
	public long getLoanDetailId() {
		return loanDetailId;
	}
	/**
	 * @param loanDetailId The loanDetailId to set.
	 */
	public void setLoanDetailId(long loanDetailId) {
		this.loanDetailId = loanDetailId;
		putUsedField("loanDetailId", loanDetailId);
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

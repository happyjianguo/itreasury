/*
 * LoanDetailInfo.java
 *
 * Created on 2004年10月27日; 
 */

package com.iss.itreasury.settlement.repaymentloancorresponding.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.util.*;

/**
 *
 * @author  pepsi
 * @version
 */
public class ReportInfo implements Serializable 
{
	private long loanDetailId = -1;				//贷款明细id
    private String typeName = "";				//类型名:银行收款/银行付款
    private String sTransNo = "";				//交易号
    private long clientId = -1;					//客户id
    private String clientName = "";				//客户名称
    private long accountId = -1;				//账户id
    private String accountNo = "";				//账户号
    private double amount = 0;					//金额
    private Timestamp dtInterestStart = null;	//起息日
    private Timestamp dtExecute = null;			//执行日
    
	/**
	 * @return Returns the loanDetailId.
	 */
	public long getLoanDetailId()
	{
		return loanDetailId;
	}
	/**
	 * @param loanDetailId The loanDetailId to set.
	 */
	public void setLoanDetailId(long loanDetailId)
	{
		this.loanDetailId=loanDetailId;
	}
	/**
	 * @return Returns the accountId.
	 */
	public long getAccountId()
	{
		return accountId;
	}
	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(long accountId)
	{
		this.accountId=accountId;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo=accountNo;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount()
	{
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount)
	{
		this.amount=amount;
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId()
	{
		return clientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId)
	{
		this.clientId=clientId;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName()
	{
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName)
	{
		this.clientName=clientName;
	}
	/**
	 * @return Returns the dtExecute.
	 */
	public Timestamp getDtExecute()
	{
		return dtExecute;
	}
	/**
	 * @param dtExecute The dtExecute to set.
	 */
	public void setDtExecute(Timestamp dtExecute)
	{
		this.dtExecute=dtExecute;
	}
	/**
	 * @return Returns the dtInterestStart.
	 */
	public Timestamp getDtInterestStart()
	{
		return dtInterestStart;
	}
	/**
	 * @param dtInterestStart The dtInterestStart to set.
	 */
	public void setDtInterestStart(Timestamp dtInterestStart)
	{
		this.dtInterestStart=dtInterestStart;
	}
	/**
	 * @return Returns the sTransNo.
	 */
	public String getSTransNo()
	{
		return sTransNo;
	}
	/**
	 * @param transNo The sTransNo to set.
	 */
	public void setSTransNo(String transNo)
	{
		sTransNo=transNo;
	}
	/**
	 * @return Returns the typeName.
	 */
	public String getTypeName()
	{
		return typeName;
	}
	/**
	 * @param typeName The typeName to set.
	 */
	public void setTypeName(String typeName)
	{
		this.typeName=typeName;
	}
}
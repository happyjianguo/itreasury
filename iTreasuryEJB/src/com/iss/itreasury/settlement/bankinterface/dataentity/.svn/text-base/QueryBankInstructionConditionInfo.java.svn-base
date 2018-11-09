package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryBankInstructionConditionInfo implements Serializable
{
	private long[] status = null;

	private long transactionType = -1;

	private long receiveBankType = -1;

	private String createBeginDate = null;

	private String createEndDate = null;

	private String sendBeginDate = null;

	private String sendEndDate = null;

	private int orderByType = -1;

	private boolean isDesc = false;
	
	//新增查询字段
	private String payAccountNo = null;//付款账户号
	private String receiveAccountNo = null;//收款账户号
	private double amount = 0.0;//金额
	private String strAbstract = null;////摘要	
	//杨帆增
	private long statusID = -1;		//
	//杨荣增加，中行接口下匹配用
	private String IDOfBankSeg2 = null;
	
	/**
	 * @return Returns the payAccountNo.
	 */
	public String getPayAccountNo()
	{
		return payAccountNo;
	}
	/**
	 * @param payAccountNo The payAccountNo to set.
	 */
	public void setPayAccountNo(String payAccountNo)
	{
		this.payAccountNo = payAccountNo;
	}
	/**
	 * Constructor for QueryBankInstructionConditionInfo.
	 */
	public QueryBankInstructionConditionInfo()
	{
		super();
	}

	/**
	 * Returns the isDesc.
	 * @return boolean
	 */
	public boolean isDesc()
	{
		return isDesc;
	}

	/**
	 * Returns the orderByType.
	 * @return int
	 */
	public int getOrderByType()
	{
		return orderByType;
	}

	/**
	 * Returns the status.
	 * @return long[]
	 */
	public long[] getStatus()
	{
		return status;
	}

	/**
	 * Sets the isDesc.
	 * @param isDesc The isDesc to set
	 */
	public void setDesc(boolean isDesc)
	{
		this.isDesc = isDesc;
	}

	/**
	 * Sets the orderByType.
	 * @param orderByType The orderByType to set
	 */
	public void setOrderByType(int orderByType)
	{
		this.orderByType = orderByType;
	}

	/**
	 * Sets the status.
	 * @param status The status to set
	 */
	public void setStatus(long[] status)
	{
		if (status != null && status.length <= 0)
			status = null;

		this.status = status;
	}

	/**
	 * Returns the createBeginDate.
	 * @return String
	 */
	public String getCreateBeginDate()
	{
		return createBeginDate;
	}

	/**
	 * Returns the createEndDate.
	 * @return String
	 */
	public String getCreateEndDate()
	{
		return createEndDate;
	}

	/**
	 * Returns the receiveBankType.
	 * @return long
	 */
	public long getReceiveBankType()
	{
		return receiveBankType;
	}

	/**
	 * Returns the sendBeginDate.
	 * @return String
	 */
	public String getSendBeginDate()
	{
		return sendBeginDate;
	}

	/**
	 * Returns the sendEndDate.
	 * @return String
	 */
	public String getSendEndDate()
	{
		return sendEndDate;
	}

	/**
	 * Returns the transactionType.
	 * @return long
	 */
	public long getTransactionType()
	{
		return transactionType;
	}

	/**
	 * Sets the createBeginDate.
	 * @param createBeginDate The createBeginDate to set
	 */
	public void setCreateBeginDate(String createBeginDate)
	{
		if (createBeginDate != null)
		{
			createBeginDate = createBeginDate.trim();
			if (createBeginDate.length() <= 0)
			{
				createBeginDate = null;
			}
		}

		this.createBeginDate = createBeginDate;
	}

	/**
	 * Sets the createEndDate.
	 * @param createEndDate The createEndDate to set
	 */
	public void setCreateEndDate(String createEndDate)
	{
		if (createEndDate != null)
		{
			createEndDate = createEndDate.trim();
			if (createEndDate.length() <= 0)
			{
				createEndDate = null;
			}
		}

		this.createEndDate = createEndDate;
	}

	/**
	 * Sets the receiveBankType.
	 * @param receiveBankType The receiveBankType to set
	 */
	public void setReceiveBankType(long receiveBankType)
	{
		this.receiveBankType = receiveBankType;
	}

	/**
	 * Sets the sendBeginDate.
	 * @param sendBeginDate The sendBeginDate to set
	 */
	public void setSendBeginDate(String sendBeginDate)
	{
		if (sendBeginDate != null)
		{
			sendBeginDate = sendBeginDate.trim();
			if (sendBeginDate.length() <= 0)
			{
				sendBeginDate = null;
			}
		}
		this.sendBeginDate = sendBeginDate;
	}

	/**
	 * Sets the sendEndDate.
	 * @param sendEndDate The sendEndDate to set
	 */
	public void setSendEndDate(String sendEndDate)
	{
		if (sendEndDate != null)
		{
			sendEndDate = sendEndDate.trim();
			if (sendEndDate.length() <= 0)
			{
				sendEndDate = null;
			}
		}
		this.sendEndDate = sendEndDate;
	}

	/**
	 * Sets the transactionType.
	 * @param transactionType The transactionType to set
	 */
	public void setTransactionType(long transactionType)
	{
		this.transactionType = transactionType;
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
	}
	/**
	 * @return Returns the iDOfBankSeg2.
	 */
	public String getIDOfBankSeg2() {
		return IDOfBankSeg2;
	}
	/**
	 * @param ofBankSeg2 The iDOfBankSeg2 to set.
	 */
	public void setIDOfBankSeg2(String ofBankSeg2) {
		IDOfBankSeg2 = ofBankSeg2;
	}
	/**
	 * @return Returns the receiveAccountNo.
	 */
	public String getReceiveAccountNo() {
		return receiveAccountNo;
	}
	/**
	 * @param receiveAccountNo The receiveAccountNo to set.
	 */
	public void setReceiveAccountNo(String receiveAccountNo) {
		this.receiveAccountNo = receiveAccountNo;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
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

}

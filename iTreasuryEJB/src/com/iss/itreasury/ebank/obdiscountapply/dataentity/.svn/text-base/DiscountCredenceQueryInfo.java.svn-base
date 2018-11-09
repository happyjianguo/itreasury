/*
 * DiscountCredenceInfo.java
 *
 * Created on 2003年2月5日, 上午10:41
 */

package com.iss.itreasury.ebank.obdiscountapply.dataentity;

import java.sql.Timestamp;

/**
 *
 * @author  fanyang
 * @version
 */
public class DiscountCredenceQueryInfo
	implements java.io.Serializable
{

	/** Creates new DiscountCredenceInfo */
	public DiscountCredenceQueryInfo()
	{
		super();
	}

	private long DiscountCredenceID; //贴现凭证ID
	private long ContractID; //合同ID
	private long ClientID;   //借款单位ID 可从session中取得
	private String BillsID;  //贴现票据ID列表

	public String getBillsID()
	{
		return BillsID;
	}

	public long getClientID()
	{
		return ClientID;
	}

	public long getContractID()
	{
		return ContractID;
	}

	public long getDiscountCredenceID()
	{
		return DiscountCredenceID;
	}

	public void setBillsID(String BillsID)
	{
		this.BillsID = BillsID;
	}

	public void setClientID(long ClientID)
	{
		this.ClientID = ClientID;
	}

	public void setContractID(long ContractID)
	{
		this.ContractID = ContractID;
	}

	public void setDiscountCredenceID(long DiscountCredenceID)
	{
		this.DiscountCredenceID = DiscountCredenceID;
	}

}

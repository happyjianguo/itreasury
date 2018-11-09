package com.iss.itreasury.settlement.upgathering.dataentity;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;


/**
 * @author ygzhao
 * 2005-8-26
 */
public class UpGatheringQueryInfo extends SettlementBaseDataEntity
{
	private long PolicyID = -1;		//上收策略标识 (暂时不用)
	private long OfficeID = -1;		//办事处标识
	private long CurrencyID = -1;	//币种标识
	private long ReceiveClientID = -1;//已勾账收款方客户标识
	private long PayClientID = -1;//已勾账付款方客户标识
	
	
	public long getPayClientID()
	{
		return PayClientID;
	}

	
	public void setPayClientID(long payClientID)
	{
		PayClientID = payClientID;
	}

	
	public long getReceiveClientID()
	{
		return ReceiveClientID;
	}

	
	public void setReceiveClientID(long receiveClientID)
	{
		ReceiveClientID = receiveClientID;
	}

	public long getCurrencyID()
	{
		return CurrencyID;
	}
	
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}
	
	public long getOfficeID()
	{
		return OfficeID;
	}
	
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}
	
	public long getPolicyID()
	{
		return PolicyID;
	}
	
	public void setPolicyID(long policyID)
	{
		PolicyID = policyID;
	}
	
	
	
}

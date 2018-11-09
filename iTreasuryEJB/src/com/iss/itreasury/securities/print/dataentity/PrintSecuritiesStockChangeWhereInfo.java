/*
 * Created on 2004-6-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesStockChangeWhereInfo extends  SECBaseDataEntity	implements Serializable
{
	//查询起始日期
	private Timestamp searchInputDateStart = null;
	//查询结束日期
	private Timestamp searchInputDateEnd = null;
	//证券类别
	private long securitiesType = -1;
	//证券名称
	private String[] selsecuritiesName = null;
	//业务单位名称
	private String clientName = null;
	//股东帐户名称
	private String[] selstockHolderAccountName = null;
	//开户营业部名称
	private String bankOfDepositName = null;
	//资金账号
	private String[] selAccountIds = null;
	//基金管理公司名称
	private String[] selFundManagerCoIds = null;
	
	
	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id)
	{
		
	}
	
	/**
	 * @return Returns the bankOfDepositName.
	 */
	public String getBankOfDepositName()
	{
		return bankOfDepositName;
	}
	/**
	 * @param bankOfDepositName The bankOfDepositName to set.
	 */
	public void setBankOfDepositName(String bankOfDepositName)
	{
		this.bankOfDepositName = bankOfDepositName;
		putUsedField("bankOfDepositName", bankOfDepositName);
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
		this.clientName = clientName;
		putUsedField("clientName", clientName);
	}

	/**
	 * @return Returns the selAccountIds.
	 */
	public String[] getSelAccountIds()
	{
		return selAccountIds;
	}
	/**
	 * @param selAccountIds The selAccountIds to set.
	 */
	public void setSelAccountIds(String[] selAccountIds)
	{
		this.selAccountIds = selAccountIds;
		putUsedField("selAccountIds", selAccountIds);
	}
	/**
	 * @return Returns the selFundManagerCoIds.
	 */
	public String[] getSelFundManagerCoIds()
	{
		return selFundManagerCoIds;
	}
	/**
	 * @param selFundManagerCoIds The selFundManagerCoIds to set.
	 */
	public void setSelFundManagerCoIds(String[] selFundManagerCoIds)
	{
		this.selFundManagerCoIds = selFundManagerCoIds;
		putUsedField("selFundManagerCoIds", selFundManagerCoIds);
	}
	/**
	 * @return Returns the selsecuritiesName.
	 */
	public String[] getSelsecuritiesName()
	{
		return selsecuritiesName;
	}
	/**
	 * @param selsecuritiesName The selsecuritiesName to set.
	 */
	public void setSelsecuritiesName(String[] selsecuritiesName)
	{
		this.selsecuritiesName = selsecuritiesName;
		putUsedField("selsecuritiesName", selsecuritiesName);
	}
	/**
	 * @return Returns the selstockHolderAccountName.
	 */
	public String[] getSelstockHolderAccountName()
	{
		return selstockHolderAccountName;
	}
	/**
	 * @param selstockHolderAccountName The selstockHolderAccountName to set.
	 */
	public void setSelstockHolderAccountName(String[] selstockHolderAccountName)
	{
		this.selstockHolderAccountName = selstockHolderAccountName;
		putUsedField("selstockHolderAccountName", selstockHolderAccountName);
	}
	/**
	 * @return Returns the searchInputDateEnd.
	 */
	public Timestamp getSearchInputDateEnd()
	{
		return searchInputDateEnd;
	}
	/**
	 * @param searchInputDateEnd The searchInputDateEnd to set.
	 */
	public void setSearchInputDateEnd(Timestamp searchInputDateEnd)
	{
		this.searchInputDateEnd = searchInputDateEnd;
		putUsedField("searchInputDateEnd", searchInputDateEnd);
	}
	/**
	 * @return Returns the searchInputDateStart.
	 */
	public Timestamp getSearchInputDateStart()
	{
		return searchInputDateStart;
	}
	/**
	 * @param searchInputDateStart The searchInputDateStart to set.
	 */
	public void setSearchInputDateStart(Timestamp searchInputDateStart)
	{
		this.searchInputDateStart = searchInputDateStart;
		putUsedField("searchInputDateStart", searchInputDateStart);
	}
	/**
	 * @param securitiesType The securitiesType to set.
	 */
	public void setSecuritiesType(long securitiesType)
	{
		this.securitiesType = securitiesType;
		putUsedField("securitiesType", securitiesType);
	}
	/**
	 * @return Returns the securitiesType.
	 */
	public long getSecuritiesType()
	{
		return securitiesType;
	}
}

/*
 * Created on 2004-5-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SecuritiesProfitAndLossParam extends SECBaseDataEntity implements Serializable
{   
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	
	//日结日期：该参数为必选项，各查询部分所公用
	private Timestamp dailyAccountDate   = null;
	
	private long clientId = -1;    //业务单位ID
	private String[] stockHolderAccountIds = null; //股东帐户
	
	private long counterPartId = -1;  //交易对手ID
	
	private long securitiesTypeId = -1;//证券类别ID
	private String[] securitiesIds = null;// 证券名称
	
	private long bourseCounterPartId = -1;  //开发营业部ID
	private String[] accountIds = null;//	资金账号

	private String[] fundCounterPartIds = null;  //基金管理公司

	private long officeId = -1;
	private long currencyId = -1;
	

	/**
	 * @return
	 */
	public String[] getAccountIds() {
		return accountIds;
	}

	/**
	 * @return
	 */
	public long getBourseCounterPartId() {
		return bourseCounterPartId;
	}

	/**
	 * @return
	 */
	public long getClientId() {
		return clientId;
	}

	/**
	 * @return
	 */
	public long getCounterPartId() {
		return counterPartId;
	}

	/**
	 * @return
	 */
	public Timestamp getDailyAccountDate() {
		return dailyAccountDate;
	}

	/**
	 * @return
	 */
	public String[] getFundCounterPartIds() {
		return fundCounterPartIds;
	}

	/**
	 * @return
	 */
	public String[] getSecuritiesIds() {
		return securitiesIds;
	}

	/**
	 * @return
	 */
	public long getSecuritiesTypeId() {
		return securitiesTypeId;
	}

	/**
	 * @return
	 */
	public String[] getStockHolderAccountIds() {
		return stockHolderAccountIds;
	}

	/**
	 * @param strings
	 */
	public void setAccountIds(String[] strings) {
		accountIds = strings;
	}

	/**
	 * @param l
	 */
	public void setBourseCounterPartId(long l) {
		bourseCounterPartId = l;
	}

	/**
	 * @param l
	 */
	public void setClientId(long l) {
		clientId = l;
	}

	/**
	 * @param l
	 */
	public void setCounterPartId(long l) {
		counterPartId = l;
	}

	/**
	 * @param timestamp
	 */
	public void setDailyAccountDate(Timestamp timestamp) {
		dailyAccountDate = timestamp;
	}

	/**
	 * @param strings
	 */
	public void setFundCounterPartIds(String[] strings) {
		fundCounterPartIds = strings;
	}

	/**
	 * @param strings
	 */
	public void setSecuritiesIds(String[] strings) {
		securitiesIds = strings;
	}

	/**
	 * @param l
	 */
	public void setSecuritiesTypeId(long l) {
		securitiesTypeId = l;
	}

	/**
	 * @param strings
	 */
	public void setStockHolderAccountIds(String[] strings) {
		stockHolderAccountIds = strings;
	}

	/**
	 * @return Returns the currencyId.
	 */
	public long getCurrencyId() {
		return currencyId;
	}
	/**
	 * @param currencyId The currencyId to set.
	 */
	public void setCurrencyId(long currencyId) {
		putUsedField("currencyId", currencyId);
		this.currencyId = currencyId;
	}
	/**
	 * @return Returns the officeId.
	 */
	public long getOfficeId() {
		return officeId;
	}
	/**
	 * @param officeId The officeId to set.
	 */
	public void setOfficeId(long officeId) {
		putUsedField("officeId", officeId);
		this.officeId = officeId;
	}
}

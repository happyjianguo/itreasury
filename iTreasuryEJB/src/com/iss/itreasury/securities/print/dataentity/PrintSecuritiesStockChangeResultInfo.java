/*
 * Created on 2004-6-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesStockChangeResultInfo  extends  SECBaseDataEntity	implements Serializable
{
	
	//业务单位名称
	private String clientName = null;
	//股东帐户名称
	private String[] selstockHolderAccountName = null;
	//资金账号
	private String[] selAccountIds = null;
	//证券名称
	private String[] selsecuritiesName = null;
	
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
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
		putUsedField("clientName", clientName);
		this.clientName = clientName;
	}
	/**
	 * @return Returns the selAccountIds.
	 */
	public String[] getSelAccountIds() {
		return selAccountIds;
	}
	/**
	 * @param selAccountIds The selAccountIds to set.
	 */
	public void setSelAccountIds(String[] selAccountIds) {
		this.selAccountIds = selAccountIds;
		putUsedField("selAccountIds", selAccountIds);
		this.selAccountIds = selAccountIds;
	}
	/**
	 * @return Returns the selsecuritiesName.
	 */
	public String[] getSelsecuritiesName() {
		return selsecuritiesName;
	}
	/**
	 * @param selsecuritiesName The selsecuritiesName to set.
	 */
	public void setSelsecuritiesName(String[] selsecuritiesName) {
		this.selsecuritiesName = selsecuritiesName;
		putUsedField("selsecuritiesName", selsecuritiesName);
		this.selsecuritiesName = selsecuritiesName;
	}
	/**
	 * @return Returns the selstockHolderAccountName.
	 */
	public String[] getSelstockHolderAccountName() {
		return selstockHolderAccountName;
	}
	/**
	 * @param selstockHolderAccountName The selstockHolderAccountName to set.
	 */
	public void setSelstockHolderAccountName(String[] selstockHolderAccountName) {
		this.selstockHolderAccountName = selstockHolderAccountName;
		putUsedField("selstockHolderAccountName", selstockHolderAccountName);
		this.selstockHolderAccountName = selstockHolderAccountName;
	}
}

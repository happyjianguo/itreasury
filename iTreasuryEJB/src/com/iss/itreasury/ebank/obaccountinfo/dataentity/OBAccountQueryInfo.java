/*
 * Created on 2004-2-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.ebank.obaccountinfo.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OBAccountQueryInfo implements Serializable
{	
	
	private long AccountID=-1;                //账户ID	
	private long OfficeID=-1;                //办事处ID
	private long CurrencyID=-1;                //币种ID
	private long SubAccountID=-1;                //子账户ID
	private long ContractID=-1;                //合同ID
	private Timestamp ExecuteDate=null;          //执行日期
	
	
	

	/**
	 * @return
	 */
	public long getAccountID() {
		return AccountID;
	}

	/**
	 * @return
	 */
	public long getContractID() {
		return ContractID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getSubAccountID() {
		return SubAccountID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l) {
		AccountID = l;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l) {
		ContractID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp) {
		ExecuteDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setSubAccountID(long l) {
		SubAccountID = l;
	}

}

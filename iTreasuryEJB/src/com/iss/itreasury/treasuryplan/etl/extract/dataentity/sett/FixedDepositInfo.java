/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-7-12
 */
package com.iss.itreasury.treasuryplan.etl.extract.dataentity.sett;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yehuang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FixedDepositInfo extends ITreasuryBaseDataEntity {
	private long id = -1;     //定期子账户ID
	private double balance = 0.0;  //定期存单余额
	private String depositNo = null;  //定期存单号
	private long clientID = -1;    //客户ID
	private String accountNo = null;  //账户号
	private long accountTypeID = -1;  //账户类型ID
 

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		putUsedField("accountNo", accountNo);
		this.accountNo = accountNo;
	}
	/**
	 * @return Returns the accountTypeID.
	 */
	public long getAccountTypeID() {
		return accountTypeID;
	}
	/**
	 * @param accountTypeID The accountTypeID to set.
	 */
	public void setAccountTypeID(long accountTypeID) {
		putUsedField("accountTypeID", accountTypeID);
		this.accountTypeID = accountTypeID;
	}
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
		putUsedField("balance", balance);
		this.balance = balance;
	}

	/**
	 * @return Returns the depositNo.
	 */
	public String getDepositNo() {
		return depositNo;
	}
	/**
	 * @param depositNo The depositNo to set.
	 */
	public void setDepositNo(String depositNo) {
		putUsedField("depositNo", depositNo);
		this.depositNo = depositNo;
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
		putUsedField("id", id);
		this.id = id;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param clientID The clientID to set.
	 */
	public void setClientID(long clientID) {
		putUsedField("clientID", clientID);
		this.clientID = clientID;
	}
}

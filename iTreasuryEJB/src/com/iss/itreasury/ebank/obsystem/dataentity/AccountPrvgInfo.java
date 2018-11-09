
package com.iss.itreasury.ebank.obsystem.dataentity;


/**
 * <p>Title: iTreasury </p> 
 * <p>Description: 交易类型设置信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: 曾海燕
 * @version 1.0
 * @Date: 2003-08-26
 */

import com.iss.itreasury.ebank.util.OBConstant;

public class AccountPrvgInfo implements java.io.Serializable
{
	private long lAccountID = -1;//银行账户ID(必填)
	private long lInputUserID = -1;//录入人ID(必填)
	
	private long lGroupID = -1;//交易类型组ID

	//页面交易类型对应属性
	private boolean  bValue = false;//交易类型是否被设置
	private long  lTypeID = -1;//交易类型ID
	/**
	 * @return 
	 */
	public boolean getValue() { 
		return bValue;
	}

	/**
	 * @return
	 */
	public long getTypeID() {
		return lTypeID;
	}

	/**
	 * @return
	 */
	public String getFormatType() {
		return OBConstant.SettInstrType.getName(lTypeID);
	}

	/**
	 * @param b
	 */
	public void setValue(boolean b) {
		bValue = b;
	}

	/**
	 * @param l
	 */
	public void setTypeID(long l) {
		lTypeID = l;
	}

	/**
	 * @return
	 */
	public long getAccountID() {
		return lAccountID;
	}

	/**
	 * @return
	 */
	public long getGroupID() {
		return lGroupID;
	}

	/**
	 * @return
	 */
	public String getFormatGroup() {
		return OBConstant.TransTypeGroupSet.getName(lGroupID);
	}


	/**
	 * @return
	 */
	public long getInputUserID() {
		return lInputUserID;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l) {
		lAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setGroupID(long l) {
		lGroupID = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l) {
		lInputUserID = l;
	}

}
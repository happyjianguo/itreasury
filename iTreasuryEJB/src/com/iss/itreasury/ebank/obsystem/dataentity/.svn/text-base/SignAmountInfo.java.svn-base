package com.iss.itreasury.ebank.obsystem.dataentity;

/**
 * <p>Title: iTreasury </p> 
 * <p>Description: 签认金额信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: 曾海燕
 * @version 1.0
 * @Date: 2003-08-13
 */

import com.iss.itreasury.util.DataFormat;

public class SignAmountInfo implements java.io.Serializable
{

	private long lClientID = -1 ;//交易提交单位
	private long lCurrencyID = -1 ; // 币种
	private long lInputUserID = -1 ;//录入人ID

	//页面显示内容
	private double dAmount1 = 0.00 ; //签认金额1
	private double dAmount2 = 0.00 ; //签认金额2
	private double dAmount3 = 0.00 ; //签认金额3

	private long lSignUserID1 = -1 ; //签认人1
	private long lSignUserID2 = -1 ; //签认人2
	private long lSignUserID3 = -1 ; //签认人3



	/**
	 * @return
	 */
	public double getAmount1() {
		return dAmount1;
	}

	/**
	 * @return
	 */
	public double getAmount2() {
		return dAmount2;
	}

	/**
	 * @return
	 */
	public double getAmount3() {
		return dAmount3;
	}

	/**
	 * @return
	 */
	public String getFormatAmount1() {
		return DataFormat.formatDisabledAmount(dAmount1);
	}

	/**
	 * @return
	 */
	public String getFormatAmount2() {
		return DataFormat.formatDisabledAmount(dAmount2);
	}

	/**
	 * @return
	 */
	public String getFormatAmount3() {
		return DataFormat.formatDisabledAmount(dAmount3);
	}


	/**
	 * @return
	 */
	public long getClientID() {
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * @return
	 */
	public long getInputUserID() {
		return lInputUserID;
	}

	/**
	 * @return
	 */
	public long getSignUserID1() {
		return lSignUserID1;
	}

	/**
	 * @return
	 */
	public long getSignUserID2() {
		return lSignUserID2;
	}

	/**
	 * @return
	 */
	public long getSignUserID3() {
		return lSignUserID3;
	}

	/**
	 * @param d
	 */
	public void setAmount1(double d) {
		dAmount1 = d;
	}

	/**
	 * @param d
	 */
	public void setAmount2(double d) {
		dAmount2 = d;
	}

	/**
	 * @param d
	 */
	public void setAmount3(double d) {
		dAmount3 = d;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		lCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l) {
		lInputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setSignUserID1(long l) {
		lSignUserID1 = l;
	}

	/**
	 * @param l
	 */
	public void setSignUserID2(long l) {
		lSignUserID2 = l;
	}

	/**
	 * @param l
	 */
	public void setSignUserID3(long l) {
		lSignUserID3 = l;
	}

}
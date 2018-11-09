/*
 * Created on 2003-10-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TransCurrentClearInterestInfo implements Serializable
{

	private long ID = -1; //唯一标识
	private long AccountID = -1;	             //账户ID
	private long OfficeID = -1;	             //办事处
	private long CurrencyID = -1;	             //币种ID
	private String TransNo = "";	             //交易号
	private double Interest = 0.0;	             //利息
	private long ReceiveInterestAccountID = -1; //收息账户号
	private Timestamp StartDate = null;	                  //开始日期
	private Timestamp ClearInterestDate = null;	 	         //结息日
	private Timestamp InterestStartDate = null;	        //结息记录起息日
	private Timestamp ExecuteDate = null;	            //结息记录执行日
	private long InputUserID=-1;	        //录入人
	private long CheckUserID=-1;	        //复核人
	private long SignUserID=-1;	            //签认人
	private long ConfirmUserID=-1;	        //确认人
	private long ConfirmOfficeID=-1;	    //通存通兑对方办事处
	private String Abstract = "";	            //摘要
	private String  CheckAbstract = "";		        //取消复核摘要
	private String  ConfirmAbstract = "";		    //确认摘要
	private long StatusID = -1;	            //交易状态
	private long IsKeepAccount = -1;	        //是否记账
	/**
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @return
	 */
	public String getCheckAbstract()
	{
		return CheckAbstract;
	}

	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @return
	 */
	public Timestamp getClearInterestDate()
	{
		return ClearInterestDate;
	}

	/**
	 * @return
	 */
	public String getConfirmAbstract()
	{
		return ConfirmAbstract;
	}

	/**
	 * @return
	 */
	public long getConfirmOfficeID()
	{
		return ConfirmOfficeID;
	}

	/**
	 * @return
	 */
	public long getConfirmUserID()
	{
		return ConfirmUserID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}

	/**
	 * @return
	 */
	public long getIsKeepAccount()
	{
		return IsKeepAccount;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getReceiveInterestAccountID()
	{
		return ReceiveInterestAccountID;
	}

	/**
	 * @return
	 */
	public long getSignUserID()
	{
		return SignUserID;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}

	/**
	 * @param string
	 */
	public void setCheckAbstract(String string)
	{
		CheckAbstract = string;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setClearInterestDate(Timestamp timestamp)
	{
		ClearInterestDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setConfirmAbstract(String string)
	{
		ConfirmAbstract = string;
	}

	/**
	 * @param l
	 */
	public void setConfirmOfficeID(long l)
	{
		ConfirmOfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setConfirmUserID(long l)
	{
		ConfirmUserID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{
		InterestStartDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setIsKeepAccount(long l)
	{
		IsKeepAccount = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l)
	{
		ReceiveInterestAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setSignUserID(long l)
	{
		SignUserID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

}

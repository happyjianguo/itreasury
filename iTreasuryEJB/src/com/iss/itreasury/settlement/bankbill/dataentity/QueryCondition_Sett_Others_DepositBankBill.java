////////////////////////////////////////////////////////////////////////////
//
// COPYRIGHT (C) 2003 ISS CORPORATION
//
// ALL RIGHTS RESERVED BY ISS CORPORATION, THIS PROGRAM
// MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS
// FURNISHED BY ISS CORPORATION, NO PART OF THIS PROGRAM
// MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
// WITHOUT THE PRIOR WRITTEN PERMISSION OF ISS CORPORATION.
// USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
// OF THE PROGRAM
//
//            ISS CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////

/**
 * BankAllowedBillTypeInfo.java
 * 查询重新签发新证实书的查询条件实体类：
 * @author  Ryan
 * @version 1.0
*/

package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.*;
import java.io.Serializable;

public class QueryCondition_Sett_Others_DepositBankBill implements Serializable
{
	private long lOrderID = -1;//排序条件
	private long lDesc = -1;//正反序
	
	private long lTypeID = -1; //票据类型ID
	private long lBankID = -1; //发票银行ID
	private long lOldBillID = -1; //旧票据ID
	private String strOldBillNo = ""; //旧票据编号
	private long lNewBillID = -1; //旧票据ID
	private String strNewBillNo = ""; //新票据编号
	private long lRequireClientID = -1; //申领客户ID
	private String strRequireClientNo = ""; //申领客户编号
	private String strRequireClientName = ""; //申领客户名称
	private long lResignUserID = -1; //签发人
	private Timestamp tsRegisterStart = null; //签发日期

	/**
	 * 排序条件
	 * @param lValue
	 */
	public void setOrderID(long lValue)
	{
		this.lOrderID = lValue;
	}
	/**
	 * 正反序
	 * @param lValue
	 */
	public void setDesc(long lValue)
	{
		this.lDesc = lValue;
	}	
	/**
	 * 排序条件
	 * @param lValue
	 */
	public long getOrderID()
	{
		return this.lOrderID;
	}
	/**
	 * 正反序
	 * @param lValue
	 */
	public long getDesc()
	{
		return this.lDesc;
	}	
	
	public void setTypeID(long lTypeID)
	{
		this.lTypeID = lTypeID;
	}
	public long getTypeID()
	{
		return lTypeID;
	}

	public void setBankID(long lBankID)
	{
		this.lBankID = lBankID;
	}
	public long getBankID()
	{
		return lBankID;
	}

	public void setOldBillID(long lOldBillID)
	{
		this.lOldBillID = lOldBillID;
	}
	public long getOldBillID()
	{
		return lOldBillID;
	}

	public void setOldBillNo(String strOldBillNo)
	{
		this.strOldBillNo = strOldBillNo;
	}
	public String getOldBillNo()
	{
		return strOldBillNo;
	}

	public void setNewBillID(long lNewBillID)
	{
		this.lNewBillID = lNewBillID;
	}
	public long getNewBillID()
	{
		return lNewBillID;
	}

	public void setNewBillNo(String strNewBillNo)
	{
		this.strNewBillNo = strNewBillNo;
	}
	public String getNewBillNo()
	{
		return strNewBillNo;
	}

	public void setRequireClientID(long lRequireClientID)
	{
		this.lRequireClientID = lRequireClientID;
	}
	public long getRequireClientID()
	{
		return lRequireClientID;
	}

	public void setRequireClientNo(String strRequireClientNo)
	{
		this.strRequireClientNo = strRequireClientNo;
	}
	public String getRequireClientNo()
	{
		return strRequireClientNo;
	}

	public void setRequireClientName(String strRequireClientName)
	{
		this.strRequireClientName = strRequireClientName;
	}
	public String getRequireClientName()
	{
		return strRequireClientName;
	}

	public void setResignUserID(long lResignUserID)
	{
		this.lResignUserID = lResignUserID;
	}
	public long getResignUserID()
	{
		return lResignUserID;
	}

	public void setRegisterStart(Timestamp tsRegisterStart)
	{
		this.tsRegisterStart = tsRegisterStart;
	}
	public Timestamp getRegisterStart()
	{
		return tsRegisterStart;
	}

}

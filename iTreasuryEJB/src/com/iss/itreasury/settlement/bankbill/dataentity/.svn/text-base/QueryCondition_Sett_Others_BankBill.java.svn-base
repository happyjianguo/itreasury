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
 * 银行票据的查询条件实体类：
 * @author  Ryan
 * @version 1.0
*/

package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.*;
import java.io.Serializable;

public class QueryCondition_Sett_Others_BankBill implements Serializable
{
	private long lOrderID = -1;//排序条件
	private long lDesc = -1;//正反序
	
	private String strBillNoStart = ""; //开始票据编号
	private String strBillNoEnd = ""; //截止票据编号
	private long lTypeID = -1; //票据类型ID
	private long lBankID = -1; //发票银行ID
	private long lRequireClientID = -1; //申领客户ID
	private String strRequireClientNo = ""; //申领客户编号
	private String strRequireClientName = ""; //申领客户名称
	private long lIsReportLoss = -1; //是否已挂失
	private long lStatusID = -1; //状态ID
	private Timestamp tsRegisterStart = null; //录入（注册）开始日期
	private Timestamp tsRegisterEnd = null; //录入（注册）截止日期
	private long lRegisterUserID = -1; //注册人ID
	private String strRegisterUserName = ""; //注册人姓名


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
	/**
	 * 
	 * @param strBillNoStart
	 */	
	public void setBillNoStart(String strBillNoStart)
	{
		this.strBillNoStart = strBillNoStart;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillNoStart()
	{
		return strBillNoStart;
	}
	/**
	 * 
	 * @param strBillNoEnd
	 */
	public void setBillNoEnd(String strBillNoEnd)
	{
		this.strBillNoEnd = strBillNoEnd;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillNoEnd()
	{
		return strBillNoEnd;
	}
	/**
	 * 
	 * @param lTypeID
	 */
	public void setTypeID(long lTypeID)
	{
		this.lTypeID = lTypeID;
	}
	/**
	 * 
	 * @return
	 */
	public long getTypeID()
	{
		return lTypeID;
	}
	/**
	 * 
	 * @param lBankID
	 */
	public void setBankID(long lBankID)
	{
		this.lBankID = lBankID;
	}
	/**
	 * 
	 * @return
	 */
	public long getBankID()
	{
		return lBankID;
	}
	/**
	 * 
	 * @param lRequireClientID
	 */
	public void setRequireClientID(long lRequireClientID)
	{
		this.lRequireClientID = lRequireClientID;
	}
	/**
	 * 
	 * @return
	 */
	public long getRequireClientID()
	{
		return lRequireClientID;
	}
	/**
	 * 
	 * @param strRequireClientNo
	 */
	public void setRequireClientNo(String strRequireClientNo)
	{
		this.strRequireClientNo = strRequireClientNo;
	}
	/**
	 * 
	 * @return
	 */
	public String getRequireClientNo()
	{
		return strRequireClientNo;
	}
	/**
	 * 
	 * @param strRequireClientName
	 */
	public void setRequireClientName(String strRequireClientName)
	{
		this.strRequireClientName = strRequireClientName;
	}
	/**
	 * 
	 * @return
	 */
	public String getRequireClientName()
	{
		return strRequireClientName;
	}
	/**
	 * 
	 * @param lIsReportLoss
	 */
	public void setIsReportLoss(long lIsReportLoss)
	{
		this.lIsReportLoss = lIsReportLoss;
	}
	/**
	 * 
	 * @return
	 */
	public long getIsReportLoss()
	{
		return lIsReportLoss;
	}
	/**
	 * 
	 * @param lStatusID
	 */
	public void setStatusID(long lStatusID)
	{
		this.lStatusID = lStatusID;
	}
	/**
	 * 
	 * @return
	 */
	public long getStatusID()
	{
		return lStatusID;
	}
	/**
	 * 
	 * @param tsRegisterStart
	 */
	public void setRegisterStart(Timestamp tsRegisterStart)
	{
		this.tsRegisterStart = tsRegisterStart;
	}
	/**
	 * 
	 * @return
	 */
	public Timestamp getRegisterStart()
	{
		return tsRegisterStart;
	}
	/**
	 * 
	 * @param tsRegisterEnd
	 */
	public void setRegisterEnd(Timestamp tsRegisterEnd)
	{
		this.tsRegisterEnd = tsRegisterEnd;
	}
	/**
	 * 
	 * @return
	 */
	public Timestamp getRegisterEnd()
	{
		return tsRegisterEnd;
	}
	/**
	 * 
	 * @param lRegisterUserID
	 */
	public void setRegisterUserID(long lRegisterUserID)
	{
		this.lRegisterUserID = lRegisterUserID;
	}
	/**
	 * 
	 * @return
	 */
	public long getRegisterUserID()
	{
		return lRegisterUserID;
	}
	/**
	 * 
	 * @param strRegisterUserName
	 */
	public void setRegisterUserName(String strRegisterUserName)
	{
		this.strRegisterUserName = strRegisterUserName;
	}
	/**
	 * 
	 * @return
	 */
	public String getRegisterUserName()
	{
		return strRegisterUserName;
	}

}

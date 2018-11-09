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
 * 银行票据实体类：
 * @author  Ryan
 * @version 1.0
*/

package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.*;
import java.io.Serializable;

public class BankBillInfo implements Serializable
{
	private long lID = -1; //唯一标识
	private String strBillNo = ""; //票据编号
	private long lTypeID = -1; //票据类型ID
	private String strTypeDesc = ""; //票据类型描述
	private long lBankID = -1; //发票银行ID
	private String strBankName = ""; //发票银行名称
	private long lRequireClientID = -1; //申领客户ID
	private String strRequireClientName = ""; //申领客户名称
	private long lIsReportLoss = -1; //是否已挂失
	private long lStatusID = -1; //状态ID
	private Timestamp tsRegisterDate = null; //注册日期
	private long lRegisterUserID = -1; //注册人ID
	private String strRegisterUserName = ""; //注册人姓名
	private Timestamp tsDeleteDate = null; //注销日期
	private long lDeleteUserID = -1; //注销人ID
	private String strDeleteUserName = ""; //注销人姓名
	private Timestamp tsRequireDate = null; //申领日期
	private long lRequireUserID = -1; //申领人ID
	private String strRequireUserName = ""; //申领人姓名
	private String strRequireClientUserNamestrBillNo = ""; //申领客户的人名
	private Timestamp tsCancelRequireDate = null; //取消申领日期
	private long lCancelRequireUserID = -1; //取消申领人ID
	private String strCancelRequireUserName = ""; //取消申领人姓名
	private Timestamp tsReportLossDate = null; //挂失日期
	private long lReportLossUserID = -1; //挂失人ID
	private String strReportLossUserName = ""; //挂失人姓名
	private Timestamp tsCancelReportLossDate = null; //取消挂失日期
	private long lCancelReportLossUserID = -1; //取消挂失人ID
	private String strCancelReportLossUserName = ""; //取消挂失人姓名
	//如果是证实书，还有下面的
	private long lNewBankBillID = -1; //对应的新证实书ID

	private long lTotalPageNum=1;		//总页数
	
	/**
	 * @return Returns the lTotalPageNum.
	 */
	public long getLTotalPageNum() {
		return lTotalPageNum;
	}

	/**
	 * @param totalPageNum The lTotalPageNum to set.
	 */
	public void setLTotalPageNum(long totalPageNum) {
		lTotalPageNum = totalPageNum;
	}

	public void setID(long lID)
	{
		this.lID = lID;
	}
	public long getID()
	{
		return lID;
	}

	public void setBillNo(String strBillNo)
	{
		this.strBillNo = strBillNo;
	}
	public String getBillNo()
	{
		return strBillNo;
	}

	public void setTypeID(long lTypeID)
	{
		this.lTypeID = lTypeID;
	}
	public long getTypeID()
	{
		return lTypeID;
	}

	public void setTypeDesc(String strTypeDesc)
	{
		this.strTypeDesc = strTypeDesc;
	}
	public String getTypeDesc()
	{
		return strTypeDesc;
	}

	public void setBankID(long lBankID)
	{
		this.lBankID = lBankID;
	}
	public long getBankID()
	{
		return lBankID;
	}

	public void setBankName(String strBankName)
	{
		this.strBankName = strBankName;
	}
	public String getBankName()
	{
		return strBankName;
	}

	public void setRequireClientID(long lRequireClientID)
	{
		this.lRequireClientID = lRequireClientID;
	}
	public long getRequireClientID()
	{
		return lRequireClientID;
	}

	public void setRequireClientName(String strRequireClientName)
	{
		this.strRequireClientName = strRequireClientName;
	}
	public String getRequireClientName()
	{
		return strRequireClientName;
	}

	public void setIsReportLoss(long lIsReportLoss)
	{
		this.lIsReportLoss = lIsReportLoss;
	}
	public long getIsReportLoss()
	{
		return lIsReportLoss;
	}

	public void setStatusID(long lStatusID)
	{
		this.lStatusID = lStatusID;
	}
	public long getStatusID()
	{
		return lStatusID;
	}

	public void setRegisterDate(Timestamp tsRegister)
	{
		this.tsRegisterDate = tsRegister;
	}
	public Timestamp getRegisterDate()
	{
		return tsRegisterDate;
	}

	public void setRegisterUserID(long lRegisterUserID)
	{
		this.lRegisterUserID = lRegisterUserID;
	}
	public long getRegisterUserID()
	{
		return lRegisterUserID;
	}

	public void setRegisterUserName(String strRegisterUserName)
	{
		this.strRegisterUserName = strRegisterUserName;
	}
	public String getRegisterUserName()
	{
		return strRegisterUserName;
	}

	public void setDeleteDate(Timestamp tsDelete)
	{
		this.tsDeleteDate = tsDelete;
	}
	public Timestamp getDeleteDate()
	{
		return tsDeleteDate;
	}

	public void setDeleteUserID(long lDeleteUserID)
	{
		this.lDeleteUserID = lDeleteUserID;
	}
	public long getDeleteUserID()
	{
		return lDeleteUserID;
	}

	public void setDeleteUserName(String strDeleteUserName)
	{
		this.strDeleteUserName = strDeleteUserName;
	}
	public String getDeleteUserName()
	{
		return strDeleteUserName;
	}

	public void setRequireDate(Timestamp tsRequire)
	{
		this.tsRequireDate = tsRequire;
	}
	public Timestamp getRequireDate()
	{
		return tsRequireDate;
	}

	public void setRequireUserID(long lRequireUserID)
	{
		this.lRequireUserID = lRequireUserID;
	}
	public long getRequireUserID()
	{
		return lRequireUserID;
	}

	public void setRequireUserName(String strRequireUserName)
	{
		this.strRequireUserName = strRequireUserName;
	}
	public String getRequireUserName()
	{
		return strRequireUserName;
	}

	public void setRequireClientUserNamestrBillNo(String strRequireClientUserNamestrBillNo)
	{
		this.strRequireClientUserNamestrBillNo = strRequireClientUserNamestrBillNo;
	}
	public String getRequireClientUserNamestrBillNo()
	{
		return strRequireClientUserNamestrBillNo;
	}

	public void setCancelRequire(Timestamp tsCancelRequire)
	{
		this.tsCancelRequireDate = tsCancelRequire;
	}
	public Timestamp getCancelRequire()
	{
		return tsCancelRequireDate;
	}

	public void setCancelRequireUserID(long lCancelRequireUserID)
	{
		this.lCancelRequireUserID = lCancelRequireUserID;
	}
	public long getCancelRequireUserID()
	{
		return lCancelRequireUserID;
	}

	public void setCancelRequireUserName(String strCancelRequireUserName)
	{
		this.strCancelRequireUserName = strCancelRequireUserName;
	}
	public String getCancelRequireUserName()
	{
		return strCancelRequireUserName;
	}

	public void setReportLossDate(Timestamp tsReportLoss)
	{
		this.tsReportLossDate = tsReportLoss;
	}
	public Timestamp getReportLossDate()
	{
		return tsReportLossDate;
	}

	public void setReportLossUserID(long lReportLossUserID)
	{
		this.lReportLossUserID = lReportLossUserID;
	}
	public long getReportLossUserID()
	{
		return lReportLossUserID;
	}

	public void setReportLossUserName(String strReportLossUserName)
	{
		this.strReportLossUserName = strReportLossUserName;
	}
	public String getReportLossUserName()
	{
		return strReportLossUserName;
	}

	public void setCancelReportLossDate(Timestamp tsCancelReportLoss)
	{
		this.tsCancelReportLossDate = tsCancelReportLoss;
	}
	public Timestamp getCancelReportLossDate()
	{
		return tsCancelReportLossDate;
	}

	public void setCancelReportLossUserID(long lCancelReportLossUserID)
	{
		this.lCancelReportLossUserID = lCancelReportLossUserID;
	}
	public long getCancelReportLossUserID()
	{
		return lCancelReportLossUserID;
	}

	public void setCancelReportLossUserName(String strCancelReportLossUserName)
	{
		this.strCancelReportLossUserName = strCancelReportLossUserName;
	}
	public String getCancelReportLossUserName()
	{
		return strCancelReportLossUserName;
	}

	public void setNewBankBillID(long lNewBankBillID)
	{
		this.lNewBankBillID = lNewBankBillID;
	}
	public long getNewBankBillID()
	{
		return lNewBankBillID;
	}

}

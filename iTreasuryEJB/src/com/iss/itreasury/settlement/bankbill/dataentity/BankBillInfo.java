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
 * ����Ʊ��ʵ���ࣺ
 * @author  Ryan
 * @version 1.0
*/

package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.*;
import java.io.Serializable;

public class BankBillInfo implements Serializable
{
	private long lID = -1; //Ψһ��ʶ
	private String strBillNo = ""; //Ʊ�ݱ��
	private long lTypeID = -1; //Ʊ������ID
	private String strTypeDesc = ""; //Ʊ����������
	private long lBankID = -1; //��Ʊ����ID
	private String strBankName = ""; //��Ʊ��������
	private long lRequireClientID = -1; //����ͻ�ID
	private String strRequireClientName = ""; //����ͻ�����
	private long lIsReportLoss = -1; //�Ƿ��ѹ�ʧ
	private long lStatusID = -1; //״̬ID
	private Timestamp tsRegisterDate = null; //ע������
	private long lRegisterUserID = -1; //ע����ID
	private String strRegisterUserName = ""; //ע��������
	private Timestamp tsDeleteDate = null; //ע������
	private long lDeleteUserID = -1; //ע����ID
	private String strDeleteUserName = ""; //ע��������
	private Timestamp tsRequireDate = null; //��������
	private long lRequireUserID = -1; //������ID
	private String strRequireUserName = ""; //����������
	private String strRequireClientUserNamestrBillNo = ""; //����ͻ�������
	private Timestamp tsCancelRequireDate = null; //ȡ����������
	private long lCancelRequireUserID = -1; //ȡ��������ID
	private String strCancelRequireUserName = ""; //ȡ������������
	private Timestamp tsReportLossDate = null; //��ʧ����
	private long lReportLossUserID = -1; //��ʧ��ID
	private String strReportLossUserName = ""; //��ʧ������
	private Timestamp tsCancelReportLossDate = null; //ȡ����ʧ����
	private long lCancelReportLossUserID = -1; //ȡ����ʧ��ID
	private String strCancelReportLossUserName = ""; //ȡ����ʧ������
	//�����֤ʵ�飬���������
	private long lNewBankBillID = -1; //��Ӧ����֤ʵ��ID

	private long lTotalPageNum=1;		//��ҳ��
	
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

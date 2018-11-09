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
 * ����Ʊ�ݵĲ�ѯ����ʵ���ࣺ
 * @author  Ryan
 * @version 1.0
*/

package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.*;
import java.io.Serializable;

public class QueryCondition_Sett_Others_BankBill implements Serializable
{
	private long lOrderID = -1;//��������
	private long lDesc = -1;//������
	
	private String strBillNoStart = ""; //��ʼƱ�ݱ��
	private String strBillNoEnd = ""; //��ֹƱ�ݱ��
	private long lTypeID = -1; //Ʊ������ID
	private long lBankID = -1; //��Ʊ����ID
	private long lRequireClientID = -1; //����ͻ�ID
	private String strRequireClientNo = ""; //����ͻ����
	private String strRequireClientName = ""; //����ͻ�����
	private long lIsReportLoss = -1; //�Ƿ��ѹ�ʧ
	private long lStatusID = -1; //״̬ID
	private Timestamp tsRegisterStart = null; //¼�루ע�ᣩ��ʼ����
	private Timestamp tsRegisterEnd = null; //¼�루ע�ᣩ��ֹ����
	private long lRegisterUserID = -1; //ע����ID
	private String strRegisterUserName = ""; //ע��������


	/**
	 * ��������
	 * @param lValue
	 */
	public void setOrderID(long lValue)
	{
		this.lOrderID = lValue;
	}
	/**
	 * ������
	 * @param lValue
	 */
	public void setDesc(long lValue)
	{
		this.lDesc = lValue;
	}	
	/**
	 * ��������
	 * @param lValue
	 */
	public long getOrderID()
	{
		return this.lOrderID;
	}
	/**
	 * ������
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

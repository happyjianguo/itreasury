/*
 * Created on 2003-9-1
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.dataentity;

import java.sql.Timestamp;
import java.io.Serializable;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DepositRelatedBankBillInfo implements Serializable
{
	private long lBillID = -1; //ǩ�������֤ʵ��ID
	private long lOldBillID = -1; //ǩ��ǰ����֤ʵ��ID
	private Timestamp tsSignDate = null; //ǩ������
	private long lSignUserID = -1; //ǩ����ID
	private long lType = -1; //Ʊ������
	private long lBankID = -1;
	private String strTranNo = ""; //���׺�
	private String strOldBillNo = ""; //��Ʊ�ݱ��
	private String strNewBillNo = ""; //��Ʊ�ݱ��
	private long lRequireClientID = -1; //�ͻ�ID
	private String strRequireClientNo = ""; //�ͻ����
	private String strRequireClientName = ""; //�ͻ�����

	public void setBillID(long lBillID)
	{
		this.lBillID = lBillID;
	}
	public long getBillID()
	{
		return lBillID;
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

	public void setSignDate(Timestamp tsSignDate)
	{
		this.tsSignDate = tsSignDate;
	}
	public Timestamp getSignDate()
	{
		return tsSignDate;
	}

	public void setSignUserID(long lSignUserID)
	{
		this.lSignUserID = lSignUserID;
	}
	public long getSignUserID()
	{
		return lSignUserID;
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

	public void setNewBillNo(String strNewBillNo)
	{
		this.strNewBillNo = strNewBillNo;
	}
	public String getNewBillNo()
	{
		return strNewBillNo;
	}

	public void setOldBillNo(String strOldBillNo)
	{
		this.strOldBillNo = strOldBillNo;
	}
	public String getOldBillNo()
	{
		return strOldBillNo;
	}

	public void setType(long lType)
	{
		this.lType = lType;
	}
	public long getType()
	{
		return lType;
	}

	public void setTranNo(String strTranNo)
	{
		this.strTranNo = strTranNo;
	}
	public String getTranNo()
	{
		return strTranNo;
	}

	/**
	 * @return
	 */
	public long getRequireClientID()
	{
		return lRequireClientID;
	}

	/**
	 * @param l
	 */
	public void setRequireClientID(long l)
	{
		lRequireClientID = l;
	}

}

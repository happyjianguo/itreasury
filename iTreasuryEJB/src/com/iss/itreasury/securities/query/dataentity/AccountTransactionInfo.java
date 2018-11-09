/*
 * Created on 2004-4-9
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * �˴���������˵����
 * �������ڣ�(2004-4-09 13:40:10)
 * @author��gqfang
 */
public  class AccountTransactionInfo extends SECBaseDataEntity implements Serializable
{
	public long typeId = -1;//����
	public Timestamp execute=null;//ִ������
	public Timestamp startDate = null; //ִ��������
	public Timestamp endDate = null; //ִ������ֹ
	public long transactionNoID=-1;//���ױ�ʶ
	public String transNo="";//���׺�
	public long transactionTypeID=-1;//�������ͱ�ʶ
	public String transactionType="";//��������
	public String account="";//��Ŀ��
	public double amount=0;//���
	public long directionID=-1;//�����ʶ
	public String direction="";//�������
	public String otherAccountRecord="";//�Է���Ŀ��
	public long statusID=-1;//״̬
	public String status="";//״̬����
	public String strAbstract="";//ժҪ
	public String inputUser="";//¼����
	public String checkUser="";//������ 
	public long pageCount=0;//ҳ��
	//
	public double totalAmount = 0.0;
	public long totalRecordes = 0;
	//�������д����ϸ��ѯ
	public String name = "";
	public String name2 = "";
	public long   record = -1;//�к�
	public double dRAmount=0;//�跽���
	public double cRAmount=0;//�������
	public String currencyCode="";//��������
	public String source = "";//��Դ
	public String description = "";
	public String accountDescription = "";
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id)
	{
		// TODO Auto-generated method stub
		
	}//�ʻ�����
    
	/**
	 * @return
	 */
	public String getAccountDescription()
	{
		return accountDescription;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @return
	 */
	public String getCheckUser()
	{
		return checkUser;
	}

	/**
	 * @return
	 */
	public double getCRAmount()
	{
		return cRAmount;
	}

	/**
	 * @return
	 */
	public String getCurrencyCode()
	{
		return currencyCode;
	}

	/**
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return
	 */
	public String getDirection()
	{
		return direction;
	}

	/**
	 * @return
	 */
	public long getDirectionID()
	{
		return directionID;
	}

	/**
	 * @return
	 */
	public double getDRAmount()
	{
		return dRAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getExecute()
	{
		return execute;
	}

	/**
	 * @return
	 */
	public String getInputUser()
	{
		return inputUser;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getName2()
	{
		return name2;
	}

	/**
	 * @return
	 */
	public String getOtherAccountRecord()
	{
		return otherAccountRecord;
	}

	/**
	 * @return
	 */
	public long getPageCount()
	{
		return pageCount;
	}

	/**
	 * @return
	 */
	public long getRecord()
	{
		return record;
	}

	/**
	 * @return
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @return
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @return
	 */
	public String getStrAbstract()
	{
		return strAbstract;
	}

	/**
	 * @return
	 */
	public double getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * @return
	 */
	public long getTotalRecordes()
	{
		return totalRecordes;
	}

	/**
	 * @return
	 */
	public long getTransactionNoID()
	{
		return transactionNoID;
	}

	/**
	 * @return
	 */
	public String getTransactionType()
	{
		return transactionType;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}

	/**
	 * @return
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * @param string
	 */
	public void setAccountDescription(String string)
	{
		accountDescription = string;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
	}

	/**
	 * @param string
	 */
	public void setCheckUser(String string)
	{
		checkUser = string;
	}

	/**
	 * @param d
	 */
	public void setCRAmount(double d)
	{
		cRAmount = d;
	}

	/**
	 * @param string
	 */
	public void setCurrencyCode(String string)
	{
		currencyCode = string;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string)
	{
		description = string;
	}

	/**
	 * @param string
	 */
	public void setDirection(String string)
	{
		direction = string;
	}

	/**
	 * @param l
	 */
	public void setDirectionID(long l)
	{
		directionID = l;
	}

	/**
	 * @param d
	 */
	public void setDRAmount(double d)
	{
		dRAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setExecute(Timestamp timestamp)
	{
		execute = timestamp;
	}

	/**
	 * @param string
	 */
	public void setInputUser(String string)
	{
		inputUser = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @param string
	 */
	public void setName2(String string)
	{
		name2 = string;
	}

	/**
	 * @param string
	 */
	public void setOtherAccountRecord(String string)
	{
		otherAccountRecord = string;
	}

	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		pageCount = l;
	}

	/**
	 * @param l
	 */
	public void setRecord(long l)
	{
		record = l;
	}

	/**
	 * @param string
	 */
	public void setSource(String string)
	{
		source = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string)
	{
		status = string;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @param string
	 */
	public void setStrAbstract(String string)
	{
		strAbstract = string;
	}

	/**
	 * @param d
	 */
	public void setTotalAmount(double d)
	{
		totalAmount = d;
	}

	/**
	 * @param l
	 */
	public void setTotalRecordes(long l)
	{
		totalRecordes = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionNoID(long l)
	{
		transactionNoID = l;
	}

	/**
	 * @param string
	 */
	public void setTransactionType(String string)
	{
		transactionType = string;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		transactionTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		transNo = string;
	}

	/**
	 * @return
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * @param string
	 */
	public void setAccount(String string)
	{
		account = string;
	}

	/**
	 * @return
	 */
	public long getTypeId()
	{
		return typeId;
	}

	/**
	 * @param l
	 */
	public void setTypeId(long l)
	{
		typeId = l;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return startDate;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		startDate = timestamp;
	}

}

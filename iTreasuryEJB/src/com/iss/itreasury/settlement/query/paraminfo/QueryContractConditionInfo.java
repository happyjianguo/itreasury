/*
 * Created on 2004-9-20
 * 
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author weilu
 * ��ѯ��ͬdataentity
 */
public class QueryContractConditionInfo implements Serializable
{
	/**
	 * 
	 */

	public QueryContractConditionInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

    //��ͬ��ʾ
    private long contractID=-1;
    
	//��������
	public long settLoanTypeID=-1;
	
	//��ͬ��ſ�ʼ
	public String minContractCode="";    
	
	//��ͬ��Ž���
	public String maxContractCode="";
	
	//��ͬ״̬
	public long statusID=-1;	                         
    
    //���뵥λ
	long borrowClientID=-1;
		
	//ί�е�λ
	long consignClientID=-1;
	
	//��ʼ
	double minLoanAmount=0;
	
	//������
	double maxLoanAmount=0;
	
	//�������ڿ�ʼ
	Timestamp minStartDate=null;
	
	//�������ڽ���
	Timestamp maxStartDate=null;
	
	//����
	long intervalNum=-1;

	//��ͬ������
	private long inputUserID=-1;
		
	//�����ֶ�
	private long orderParam=-1;
	
	//desc
	private long desc=-1;	
	
	//��ѯ����
	private String queryLevel="";
	
	
	/**
	 * @return Returns the borrowClientID.
	 */
	public long getBorrowClientID()
	{
		return borrowClientID;
	}
	/**
	 * @param borrowClientID The borrowClientID to set.
	 */
	public void setBorrowClientID(long borrowClientID)
	{
		this.borrowClientID = borrowClientID;
	}
	/**
	 * @return Returns the consignClientID.
	 */
	public long getConsignClientID()
	{
		return consignClientID;
	}
	/**
	 * @param consignClientID The consignClientID to set.
	 */
	public void setConsignClientID(long consignClientID)
	{
		this.consignClientID = consignClientID;
	}
	/**
	 * @return Returns the desc.
	 */
	public long getDesc()
	{
		return desc;
	}
	/**
	 * @param desc The desc to set.
	 */
	public void setDesc(long desc)
	{
		this.desc = desc;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID()
	{
		return inputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID)
	{
		this.inputUserID = inputUserID;
	}
	/**
	 * @return Returns the intervalNum.
	 */
	public long getIntervalNum()
	{
		return intervalNum;
	}
	/**
	 * @param intervalNum The intervalNum to set.
	 */
	public void setIntervalNum(long intervalNum)
	{
		this.intervalNum = intervalNum;
	}
	/**
	 * @return Returns the maxContractCode.
	 */
	public String getMaxContractCode()
	{
		return maxContractCode;
	}
	/**
	 * @param maxContractCode The maxContractCode to set.
	 */
	public void setMaxContractCode(String maxContractCode)
	{
		this.maxContractCode = maxContractCode;
	}
	/**
	 * @return Returns the maxLoanAmount.
	 */
	public double getMaxLoanAmount()
	{
		return maxLoanAmount;
	}
	/**
	 * @param maxLoanAmount The maxLoanAmount to set.
	 */
	public void setMaxLoanAmount(double maxLoanAmount)
	{
		this.maxLoanAmount = maxLoanAmount;
	}
	/**
	 * @return Returns the maxStartDate.
	 */
	public Timestamp getMaxStartDate()
	{
		return maxStartDate;
	}
	/**
	 * @param maxStartDate The maxStartDate to set.
	 */
	public void setMaxStartDate(Timestamp maxStartDate)
	{
		this.maxStartDate = maxStartDate;
	}
	/**
	 * @return Returns the minContractCode.
	 */
	public String getMinContractCode()
	{
		return minContractCode;
	}
	/**
	 * @param minContractCode The minContractCode to set.
	 */
	public void setMinContractCode(String minContractCode)
	{
		this.minContractCode = minContractCode;
	}
	/**
	 * @return Returns the minLoanAmount.
	 */
	public double getMinLoanAmount()
	{
		return minLoanAmount;
	}
	/**
	 * @param minLoanAmount The minLoanAmount to set.
	 */
	public void setMinLoanAmount(double minLoanAmount)
	{
		this.minLoanAmount = minLoanAmount;
	}
	/**
	 * @return Returns the minStartDate.
	 */
	public Timestamp getMinStartDate()
	{
		return minStartDate;
	}
	/**
	 * @param minStartDate The minStartDate to set.
	 */
	public void setMinStartDate(Timestamp minStartDate)
	{
		this.minStartDate = minStartDate;
	}
	/**
	 * @return Returns the orderParam.
	 */
	public long getOrderParam()
	{
		return orderParam;
	}
	/**
	 * @param orderParam The orderParam to set.
	 */
	public void setOrderParam(long orderParam)
	{
		this.orderParam = orderParam;
	}
	/**
	 * @return Returns the queryLevel.
	 */
	public String getQueryLevel()
	{
		return queryLevel;
	}
	/**
	 * @param queryLevel The queryLevel to set.
	 */
	public void setQueryLevel(String queryLevel)
	{
		this.queryLevel = queryLevel;
	}
	/**
	 * @return Returns the settLoanTypeID.
	 */
	public long getSettLoanTypeID()
	{
		return settLoanTypeID;
	}
	/**
	 * @param settLoanTypeID The settLoanTypeID to set.
	 */
	public void setSettLoanTypeID(long settLoanTypeID)
	{
		this.settLoanTypeID = settLoanTypeID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID()
	{
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID)
	{
		this.statusID = statusID;
	}
	/**
	 * @return Returns the contractID.
	 */
	public long getContractID()
	{
		return contractID;
	}
	/**
	 * @param contractID The contractID to set.
	 */
	public void setContractID(long contractID)
	{
		this.contractID = contractID;
	}
}

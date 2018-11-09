package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author gqfang
 * 
 * �ۺ�����ʹ�������ѯ
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryCreditLineInfo extends SECBaseDataEntity implements Serializable
{
	private long securitiesId                  = -1;  //֤ȯId
	private long securitiesTypeId              = -1;  //֤ȯ����Id
	private double creditLine                  = 0.0; //���Ŷ��
	private double useAbleCreditLine           = 0.0; //���ö��
	
	private long applyFormId                   = -1;  //������Id
	private String applyFormCode               = "";  //��������
	private long businessTypeId                = -1;  //ҵ������Id
	private long transactionTypeId             = -1;  //��������Id
	private long applyFormStatus               = -1;  //������״̬
	private double applyUseCreditLine          = 0.0; //����ռ�ö��
	private double deliveryOrderUseCreditLine  = 0.0; //���ռ�ö��
	
	/**
	 * @return
	 */
	public double getCreditLine()
	{
		return creditLine;
	}

	/**
	 * @return
	 */
	public long getSecuritiesId()
	{
		return securitiesId;
	}

	/**
	 * @return
	 */
	public long getSecuritiesTypeId()
	{
		return securitiesTypeId;
	}

	/**
	 * @return
	 */
	public double getUseAbleCreditLine()
	{
		return useAbleCreditLine;
	}

	/**
	 * @param d
	 */
	public void setCreditLine(double d)
	{
		creditLine = d;
	}

	/**
	 * @param l
	 */
	public void setSecuritiesId(long l)
	{
		securitiesId = l;
	}

	/**
	 * @param l
	 */
	public void setSecuritiesTypeId(long l)
	{
		securitiesTypeId = l;
	}

	/**
	 * @param d
	 */
	public void setUseAbleCreditLine(double d)
	{
		useAbleCreditLine = d;
	}

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
		
	}

	/**
	 * @return
	 */
	public String getApplyFormCode()
	{
		return applyFormCode;
	}

	/**
	 * @return
	 */
	public long getApplyFormId()
	{
		return applyFormId;
	}

	/**
	 * @return
	 */
	public long getApplyFormStatus()
	{
		return applyFormStatus;
	}

	/**
	 * @param string
	 */
	public void setApplyFormCode(String string)
	{
		applyFormCode = string;
	}

	/**
	 * @param l
	 */
	public void setApplyFormId(long l)
	{
		applyFormId = l;
	}

	/**
	 * @param l
	 */
	public void setApplyFormStatus(long l)
	{
		applyFormStatus = l;
	}

	/**
	 * @return
	 */
	public double getApplyUseCreditLine()
	{
		return applyUseCreditLine;
	}

	/**
	 * @return
	 */
	public double getDeliveryOrderUseCreditLine()
	{
		return deliveryOrderUseCreditLine;
	}

	/**
	 * @param d
	 */
	public void setApplyUseCreditLine(double d)
	{
		applyUseCreditLine = d;
	}

	/**
	 * @param d
	 */
	public void setDeliveryOrderUseCreditLine(double d)
	{
		deliveryOrderUseCreditLine = d;
	}

	/**
	 * @return
	 */
	public long getBusinessTypeId()
	{
		return businessTypeId;
	}

	/**
	 * @param l
	 */
	public void setBusinessTypeId(long l)
	{
		businessTypeId = l;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeId()
	{
		return transactionTypeId;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l)
	{
		transactionTypeId = l;
	}

}
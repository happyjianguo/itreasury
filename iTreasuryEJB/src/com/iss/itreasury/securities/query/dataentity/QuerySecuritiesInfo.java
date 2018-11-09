/*
 * Created on 2004-7-22
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author qgfang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QuerySecuritiesInfo extends SECBaseDataEntity implements Serializable
{
	private String securitiesCode1    = "";   //֤ȯ���루һ����
	private String securitiesCode2    = "";   //֤ȯ���루������
	private String shortName          = "";   //֤ȯ����
	private long   typeId             = -1;   //֤ȯ���Id
	private long   exchangeCenterId    = -1;   //֤ȯ�����г�Id
	private String promotor           = "";   //������
	private double interestRate       = 0.0;  //Ʊ������
	private double term               = 0.0;   //��������
	private Timestamp isSueStartDate  = null; //��������
	private Timestamp isSueEndDate    = null; //����ֹ��
	private Timestamp listingDate     = null; //������
	private Timestamp valueDate       = null; //��Ϣ��
	private String interestCycle      = "";   //��ϢƵ��
	
	
	/**
	 * @return
	 */
	public String getInterestCycle()
	{
		return interestCycle;
	}

	/**
	 * @return
	 */
	public double getInterestRate()
	{
		return interestRate;
	}

	/**
	 * @return
	 */
	public Timestamp getIsSueEndDate()
	{
		return isSueEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getIsSueStartDate()
	{
		return isSueStartDate;
	}

	/**
	 * @return
	 */
	public Timestamp getListingDate()
	{
		return listingDate;
	}

	/**
	 * @return
	 */
	public String getPromotor()
	{
		return promotor;
	}

	/**
	 * @return
	 */
	public String getSecuritiesCode1()
	{
		return securitiesCode1;
	}

	/**
	 * @return
	 */
	public String getSecuritiesCode2()
	{
		return securitiesCode2;
	}

	/**
	 * @return
	 */
	public String getShortName()
	{
		return shortName;
	}

	
	/**
	 * @return
	 */
	public long getTypeId()
	{
		return typeId;
	}

	/**
	 * @return
	 */
	public Timestamp getValueDate()
	{
		return valueDate;
	}

	/**
	 * @param string
	 */
	public void setInterestCycle(String string)
	{
		interestCycle = string;
	}

	/**
	 * @param d
	 */
	public void setInterestRate(double d)
	{
		interestRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setIsSueEndDate(Timestamp timestamp)
	{
		isSueEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setIsSueStartDate(Timestamp timestamp)
	{
		isSueStartDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setListingDate(Timestamp timestamp)
	{
		listingDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setPromotor(String string)
	{
		promotor = string;
	}

	/**
	 * @param string
	 */
	public void setSecuritiesCode1(String string)
	{
		securitiesCode1 = string;
	}

	/**
	 * @param string
	 */
	public void setSecuritiesCode2(String string)
	{
		securitiesCode2 = string;
	}

	/**
	 * @param string
	 */
	public void setShortName(String string)
	{
		shortName = string;
	}

	
	/**
	 * @param l
	 */
	public void setTypeId(long l)
	{
		typeId = l;
	}

	/**
	 * @param timestamp
	 */
	public void setValueDate(Timestamp timestamp)
	{
		valueDate = timestamp;
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
	public double getTerm()
	{
		return term;
	}

	/**
	 * @param d
	 */
	public void setTerm(double d)
	{
		term = d;
	}

	/**
	 * @return
	 */
	public long getExchangeCenterId()
	{
		return exchangeCenterId;
	}

	/**
	 * @param l
	 */
	public void setExchangeCenterId(long l)
	{
		exchangeCenterId = l;
	}

}
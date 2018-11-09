/*
 * Created on 2004-4-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.securities.securitiescontract.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractBondTypeInfo extends SECBaseDataEntity
{

	private long id;
	private long contractFormId;
	private String name;
	private long term;
	private double amount;
	private String rate;
	private long statusId;
	
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
	public long getId()
	{
		return id;
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
	public String getRate()
	{
		return rate;
	}

	/**
	 * @return
	 */
	public long getStatusId()
	{
		return statusId;
	}

	/**
	 * @return
	 */
	public long getTerm()
	{
		return term;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
		putUsedField("amount", amount);
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
		putUsedField("name", name);
	}

	/**
	 * @param d
	 */
	public void setRate(String d)
	{
		rate = d;
		putUsedField("rate", rate);
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l)
	{
		statusId = l;
		putUsedField("statusId", statusId);
	}

	/**
	 * @param l
	 */
	public void setTerm(long l)
	{
		term = l;
		putUsedField("term", term);
	}

	/**
	 * @return
	 */
	public long getContractFormId()
	{
		return contractFormId;
	}

	/**
	 * @param l
	 */
	public void setContractFormId(long l)
	{
		contractFormId = l;
		putUsedField("contractFormId", contractFormId);
	}

}

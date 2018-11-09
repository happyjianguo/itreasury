/*
 * Created on 2004-12-2
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;

import java.sql.Timestamp;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class LossInfo
{
	//客户号、名称、账户号、存单号、冻结方式、冻结金额、解冻金额、冻结执行单位、申请执行单位、日期;
	private String ClientCode = "";
	private String ClientName = "";
	private String AccountNo = "";
	private String DepositNo = "";
	private String NewDepositNo = "";
	private long FreezeType = -1;
	private double FreezeAmount = 0.0;
	private String ExecuteGovernment = "";
	private String ApplyCompany = "";
	private Timestamp EffectiveDate = null;
	
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		AccountNo = accountNo;
	}
	/**
	 * @return Returns the applyCompany.
	 */
	public String getApplyCompany()
	{
		return ApplyCompany;
	}
	/**
	 * @param applyCompany The applyCompany to set.
	 */
	public void setApplyCompany(String applyCompany)
	{
		ApplyCompany = applyCompany;
	}
	/**
	 * @return Returns the clientCode.
	 */
	public String getClientCode()
	{
		return ClientCode;
	}
	/**
	 * @param clientCode The clientCode to set.
	 */
	public void setClientCode(String clientCode)
	{
		ClientCode = clientCode;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName()
	{
		return ClientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName)
	{
		ClientName = clientName;
	}
	/**
	 * @return Returns the depositNo.
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}
	/**
	 * @param depositNo The depositNo to set.
	 */
	public void setDepositNo(String depositNo)
	{
		DepositNo = depositNo;
	}
	/**
	 * @return Returns the effectiveDate.
	 */
	public Timestamp getEffectiveDate()
	{
		return EffectiveDate;
	}
	/**
	 * @param effectiveDate The effectiveDate to set.
	 */
	public void setEffectiveDate(Timestamp effectiveDate)
	{
		EffectiveDate = effectiveDate;
	}
	/**
	 * @return Returns the executeGovernment.
	 */
	public String getExecuteGovernment()
	{
		return ExecuteGovernment;
	}
	/**
	 * @param executeGovernment The executeGovernment to set.
	 */
	public void setExecuteGovernment(String executeGovernment)
	{
		ExecuteGovernment = executeGovernment;
	}
	/**
	 * @return Returns the freezeAmount.
	 */
	public double getFreezeAmount()
	{
		return FreezeAmount;
	}
	/**
	 * @param freezeAmount The freezeAmount to set.
	 */
	public void setFreezeAmount(double freezeAmount)
	{
		FreezeAmount = freezeAmount;
	}
	/**
	 * @return Returns the freezeType.
	 */
	public long getFreezeType()
	{
		return FreezeType;
	}
	/**
	 * @param freezeType The freezeType to set.
	 */
	public void setFreezeType(long freezeType)
	{
		FreezeType = freezeType;
	}
	/**
	 * @return Returns the newDepositNo.
	 */
	public String getNewDepositNo()
	{
		return NewDepositNo;
	}
	/**
	 * @param newDepositNo The newDepositNo to set.
	 */
	public void setNewDepositNo(String newDepositNo)
	{
		NewDepositNo = newDepositNo;
	}
}

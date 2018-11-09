package com.iss.itreasury.settlement.setting.dataentity;

import java.io.Serializable;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author rxie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DepositLoanSearchSettingInfo extends SettlementBaseDataEntity implements Serializable
{
    private long ID = -1;         //PK  
    
    private long CurrencyID = -1; 
    private long OfficeID 	= -1;
    private String Name = "";     
	private String AccountTypeID = "";//账户类型
	private String AccountTypeName = "";//账户类型

	
	/**
	 * @return
	 */
	public String getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return Name;
	}

	/**
	 * @param string
	 */
	public void setAccountTypeID(String string)
	{
		AccountTypeID = string;
		putUsedField("sAccountTypeID", string);
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
		putUsedField("nCurrencyID", l);
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
		putUsedField("id", l);
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		Name = string;
		putUsedField("sname", string);
	}

	/**
	 * @return
	 */
	public String getAccountTypeName()
	{
		return AccountTypeName;
	}

	/**
	 * @param string
	 */
	public void setAccountTypeName(String string)
	{
		AccountTypeName = string;
	}

	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
		putUsedField("nOfficeID", officeID);
	}
}
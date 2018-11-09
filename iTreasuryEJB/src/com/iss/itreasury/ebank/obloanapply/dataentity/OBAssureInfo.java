/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dataentity;

import com.iss.itreasury.ebank.obdataentity.*;
import java.io.Serializable;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBAssureInfo  implements Serializable
{
	private long    ID=-1;
	private long    loanID=-1;
	private long    assureTypeID=-1;
	private long    fillQuestionary=-1;
	private long    clientID=-1;
	private double  amount=0;
	private String  impawName="";
	private String  impawQuality="";
	private String  impawStatus="";
	private double  pledgeAmount=0;
	private double  pledgeRate=0;
	private long    statusID=-1;
	private OBSecurityInfo securityInfo=null;			//安全信息   	
	private String  assureCode="";
	private String clientName="";
	private String clientCode="";
	private String clientPhone="";
	private String clientContacter="";
	private String clientFax="";
	private String clientProvince="";
	private String clientCity="";
	private String clientAddress="";
	private String clientBank1="";
	private String clientBankAccount1="";
    
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
	public String getAssureCode()
	{
		return assureCode;
	}

	/**
	 * @return
	 */
	public long getAssureTypeID()
	{
		return assureTypeID;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return clientID;
	}

	/**
	 * @return
	 */
	public long getFillQuestionary()
	{
		return fillQuestionary;
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
	public String getImpawName()
	{
		return impawName;
	}

	/**
	 * @return
	 */
	public String getImpawQuality()
	{
		return impawQuality;
	}

	/**
	 * @return
	 */
	public String getImpawStatus()
	{
		return impawStatus;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return loanID;
	}

	/**
	 * @return
	 */
	public double getPledgeAmount()
	{
		return pledgeAmount;
	}

	/**
	 * @return
	 */
	public double getPledgeRate()
	{
		return pledgeRate;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
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
	public void setAssureCode(String string)
	{
		assureCode = string;
	}

	/**
	 * @param l
	 */
	public void setAssureTypeID(long l)
	{
		assureTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		clientID = l;
	}

	/**
	 * @param l
	 */
	public void setFillQuestionary(long l)
	{
		fillQuestionary = l;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param string
	 */
	public void setImpawName(String string)
	{
		impawName = string;
	}

	/**
	 * @param string
	 */
	public void setImpawQuality(String string)
	{
		impawQuality = string;
	}

	/**
	 * @param string
	 */
	public void setImpawStatus(String string)
	{
		impawStatus = string;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		loanID = l;
	}

	/**
	 * @param d
	 */
	public void setPledgeAmount(double d)
	{
		pledgeAmount = d;
	}

	/**
	 * @param d
	 */
	public void setPledgeRate(double d)
	{
		pledgeRate = d;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @return
	 */
	public String getClientCode()
	{
		return clientCode;
	}

	/**
	 * @return
	 */
	public String getClientContacter()
	{
		return clientContacter;
	}

	/**
	 * @return
	 */
	public String getClientFax()
	{
		return clientFax;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return clientName;
	}

	/**
	 * @return
	 */
	public String getClientPhone()
	{
		return clientPhone;
	}

	/**
	 * @param string
	 */
	public void setClientCode(String string)
	{
		clientCode = string;
	}

	/**
	 * @param string
	 */
	public void setClientContacter(String string)
	{
		clientContacter = string;
	}

	/**
	 * @param string
	 */
	public void setClientFax(String string)
	{
		clientFax = string;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		clientName = string;
	}

	/**
	 * @param string
	 */
	public void setClientPhone(String string)
	{
		clientPhone = string;
	}

	/**
	 * @return
	 */
	public String getClientAddress()
	{
		return clientAddress;
	}

	/**
	 * @return
	 */
	public String getClientCity()
	{
		return clientCity;
	}

	/**
	 * @return
	 */
	public String getClientProvince()
	{
		return clientProvince;
	}

	/**
	 * @param string
	 */
	public void setClientAddress(String string)
	{
		clientAddress = string;
	}

	/**
	 * @param string
	 */
	public void setClientCity(String string)
	{
		clientCity = string;
	}

	/**
	 * @param string
	 */
	public void setClientProvince(String string)
	{
		clientProvince = string;
	}

	/**
	 * @return
	 */
	public String getClientBank1()
	{
		return clientBank1;
	}

	/**
	 * @return
	 */
	public String getClientBankAccount1()
	{
		return clientBankAccount1;
	}

	/**
	 * @param string
	 */
	public void setClientBank1(String string)
	{
		clientBank1 = string;
	}

	/**
	 * @param string
	 */
	public void setClientBankAccount1(String string)
	{
		clientBankAccount1 = string;
	}

	/**
	 * @return
	 */
	public OBSecurityInfo getSecurityInfo()
	{
		return securityInfo;
	}

	/**
	 * @param info
	 */
	public void setSecurityInfo(OBSecurityInfo info)
	{
		securityInfo = info;
	}

}

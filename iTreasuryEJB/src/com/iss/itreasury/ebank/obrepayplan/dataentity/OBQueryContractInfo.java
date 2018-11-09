/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obrepayplan.dataentity;

import java.io.Serializable;

/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBQueryContractInfo implements Serializable
{
//Ñî·«2004-2-11¼Ó
	long UserID = -1;
	long CurrencyID = -1;
	long OfficeID = -1;
	long TypeID = -1;
	long ContractIDFrom = -1;
	long ContractIDTo = -1;
	long ClientID = -1;
	long PageLineCount = -1;
	long PageNo = -1;
	long OrderParam = -1;
	long Desc= -1;

	String ContractNameFrom = "";
	String ContractNameTo = "";
	String ClientName = "";


	public void setClientID(long ClientID)
	{
		this.ClientID = ClientID;
	}

	public void setContractIDFrom(long ContractIDFrom)
	{
		this.ContractIDFrom = ContractIDFrom;
	}

	public void setContractIDTo(long ContractIDTo)
	{
		this.ContractIDTo = ContractIDTo;
	}

	public void setCurrencyID(long CurrencyID)
	{
		this.CurrencyID = CurrencyID;
	}

	public void setDesc(long Desc)
	{
		this.Desc = Desc;
	}

	public void setOfficeID(long OfficeID)
	{
		this.OfficeID = OfficeID;
	}

	public void setOrderParam(long OrderParam)
	{
		this.OrderParam = OrderParam;
	}

	public void setPageLineCount(long PageLineCount)
	{
		this.PageLineCount = PageLineCount;
	}

	public void setPageNo(long PageNo)
	{
		this.PageNo = PageNo;
	}

	public void setTypeID(long TypeID)
	{
		this.TypeID = TypeID;
	}

	public void setUserID(long UserID)
	{
		this.UserID = UserID;
	}

	public long getClientID()
	{
		return ClientID;
	}

	public long getContractIDFrom()
	{
		return ContractIDFrom;
	}

	public long getContractIDTo()
	{
		return ContractIDTo;
	}

	public long getCurrencyID()
	{
		return CurrencyID;
	}

	public long getDesc()
	{
		return Desc;
	}

	public long getOfficeID()
	{
		return OfficeID;
	}

	public long getOrderParam()
	{
		return OrderParam;
	}

	public long getPageLineCount()
	{
		return PageLineCount;
	}

	public long getPageNo()
	{
		return PageNo;
	}

	public long getTypeID()
	{
		return TypeID;
	}

	public long getUserID()
	{
		return UserID;
    }

	public String getClientName()
	{
		return ClientName;
	}

	public void setClientName(String ClientName)
	{
		this.ClientName = ClientName;
	}

	public String getContractNameFrom()
	{
		return ContractNameFrom;
	}

	public void setContractNameFrom(String ContractNameFrom)
	{
		this.ContractNameFrom = ContractNameFrom;
	}

	public String getContractNameTo()
	{
		return ContractNameTo;
	}

	public void setContractNameTo(String ContractNameTo)
	{
		this.ContractNameTo = ContractNameTo;
    }

}

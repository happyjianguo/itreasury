/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FreeFormInfo implements java.io.Serializable
{
	private long FreeFormID = -1;//免换通知单ID
	private String FreeFormNo = "";//免换通知单编号

	private long ContractID = -1;//对应合同ID
	private String ContractNo = "";//对应合同编号
	private long ContractTypeID = -1;//对应合同类型ID
	private long PayFormID = -1;//对应放款通知单ID
	private String PayFormNo = "";//对应放款通知单编号

	private long ClientID = -1;//对应贷款客户ID
	private String ClientNo = "";//对应贷款客户编号
	private String ClientName = "";//对应贷款客户名称
	
	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @return
	 */
	public String getContractNo()
	{
		return ContractNo;
	}

	/**
	 * @return
	 */
	public long getPayFormID()
	{
		return PayFormID;
	}

	/**
	 * @return
	 */
	public String getPayFormNo()
	{
		return PayFormNo;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @param string
	 */
	public void setContractNo(String string)
	{
		ContractNo = string;
	}

	/**
	 * @param l
	 */
	public void setPayFormID(long l)
	{
		PayFormID = l;
	}

	/**
	 * @param string
	 */
	public void setPayFormNo(String string)
	{
		PayFormNo = string;
	}

	/**
	 * @return
	 */
	public long getContractTypeID()
	{
		return ContractTypeID;
	}

	/**
	 * @param l
	 */
	public void setContractTypeID(long l)
	{
		ContractTypeID = l;
	}

	/**
	 * @return
	 */
	public long getFreeFormID()
	{
		return FreeFormID;
	}

	/**
	 * @return
	 */
	public String getFreeFormNo()
	{
		return FreeFormNo;
	}

	/**
	 * @param l
	 */
	public void setFreeFormID(long l)
	{
		FreeFormID = l;
	}

	/**
	 * @param string
	 */
	public void setFreeFormNo(String string)
	{
		FreeFormNo = string;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @return
	 */
	public String getClientNo()
	{
		return ClientNo;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

	/**
	 * @param string
	 */
	public void setClientNo(String string)
	{
		ClientNo = string;
	}

}

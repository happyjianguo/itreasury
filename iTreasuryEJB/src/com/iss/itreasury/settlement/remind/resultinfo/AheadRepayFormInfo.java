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
public class AheadRepayFormInfo implements java.io.Serializable
{
	private long AheadRepayFormID = -1;//��ǰ����֪ͨ��ID
	private String AheadRepayFormNo = "";//��ǰ����֪ͨ�����

	private long ContractID = -1;//��Ӧ��ͬID
	private String ContractNo = "";//��Ӧ��ͬ���
	private long ContractTypeID = -1;//��Ӧ��ͬ����ID
	private long PayFormID = -1;//��Ӧ�ſ�֪ͨ��ID
	private String PayFormNo = "";//��Ӧ�ſ�֪ͨ�����
	
	private long ClientID = -1;//��Ӧ����ͻ�ID
	private String ClientNo = "";//��Ӧ����ͻ����
	private String ClientName = "";//��Ӧ����ͻ�����
	
	private double Mamount = 0.00;		//������


	/**
	 * @return ���� mamount��
	 */
	public double getMamount() {
		return Mamount;
	}

	/**
	 * @param mamount Ҫ���õ� mamount��
	 */
	public void setMamount(double mamount) {
		Mamount = mamount;
	}

	/**
	 * @return
	 */
	public long getAheadRepayFormID()
	{
		return AheadRepayFormID;
	}

	/**
	 * @return
	 */
	public String getAheadRepayFormNo()
	{
		return AheadRepayFormNo;
	}

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
	public void setAheadRepayFormID(long l)
	{
		AheadRepayFormID = l;
	}

	/**
	 * @param string
	 */
	public void setAheadRepayFormNo(String string)
	{
		AheadRepayFormNo = string;
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
	public long getClientID()
	{
		return ClientID;
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
	public void setClientNo(String string)
	{
		ClientNo = string;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}

}

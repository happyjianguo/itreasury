/*
 * Created on 2003-10-27
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contractcontent.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractContentInfo implements java.io.Serializable
{
	private long ID = 0;                                                                  
	private long ParentID = -1;// ��ͬ������ID                              
	private long ContractID = -1;//��ͬID 
	private long SerialNo = -1;//���к�                                    
	private long ContractTypeID = -1;//��ͬ����  
	private String ContractType = "";//��ͬ��������                              
	private String DocName = "";//�ļ�����                                  
	private String Code = "";//��֤��ͬ���  
	private long AssureTypeID = -1;//��֤����  
	private String ClientName = ""; //�ͻ�����  
	private String PageName = "";//��ͬ�ı���Ӧ��JSPҳ�������ı��ļ���
	      

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
	public String getCode()
	{
		return Code;
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
	public long getContractTypeID()
	{
		return ContractTypeID;
	}

	/**
	 * @return
	 */
	public String getDocName()
	{
		return DocName;
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
	public long getParentID()
	{
		return ParentID;
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
	public void setCode(String string)
	{
		Code = string;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @param l
	 */
	public void setContractTypeID(long l)
	{
		ContractTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setDocName(String string)
	{
		DocName = string;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setParentID(long l)
	{
		ParentID = l;
	}

	/**
	 * @return
	 */
	public long getSerialNo()
	{
		return SerialNo;
	}

	/**
	 * @param l
	 */
	public void setSerialNo(long l)
	{
		SerialNo = l;
	}

	/**
	 * @return
	 */
	public String getContractType()
	{
		return ContractType;
	}

	/**
	 * @param string
	 */
	public void setContractType(String string)
	{
		ContractType = string;
	}

	/**
	 * @return
	 */
	public long getAssureTypeID()
	{
		return AssureTypeID;
	}

	/**
	 * @param l
	 */
	public void setAssureTypeID(long l)
	{
		AssureTypeID = l;
	}

	/**
	 * @return
	 */
	public String getPageName()
	{
		return PageName;
	}

	/**
	 * @param string
	 */
	public void setPageName(String string)
	{
		PageName = string;
	}

}

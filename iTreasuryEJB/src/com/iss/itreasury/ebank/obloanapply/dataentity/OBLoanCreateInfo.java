/*
 * Created on 2003-12-30
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obloanapply.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.ebank.obdataentity.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBLoanCreateInfo implements Serializable
{

	private long        loanID=-1;             //��ˮ��
	private long        typeID=-1;             //��������
	private String      applyCode="";          //���������
	private long        consignClientID=-1;    //ί�е�λ����
	private long        borrowClientID=-1;     //��λ
	private Timestamp   inputDate=null;        //¼��ʱ��
	private OBSecurityInfo securityInfo=null;			//��ȫ��Ϣ   
 

	/**
	 * ���ô����������ˮ��
	 * @param loanID long,�����������ˮ��
	 */
	public void setLoanID(long loanID)
	{
		 this.loanID=loanID;
	}
	/**
	 * ��ȡ�����������ˮ��
	 * @return long �����������ˮ��
	 */
	public long getLoanID()
	{
		return loanID;
	}
	/**
	 * ���ô�������ͺ�
	 * @param typeID long ��������ʹ���
	 */
	public void setTypeID(long typeID)
	{
		this.typeID=typeID;
	}
	/**
	 * ��ȡ��������ʹ���
	 * @return long ��������ʹ���
	 */
	public long getTypeID()
	{
		return typeID;
	}
    
    
	/**
	 * ���ô��������
	 * @param String applyCode
	 */
	public void setApplyCode(String applyCode)
	{
		this.applyCode=applyCode;
	}
	/**
	 * ��ô���������
	 * @return String ����������
	 */
	public String getApplyCode()
	{
		return this.applyCode;
	}
    
	/**
	 * ����ί�е�λ
	 * @param long consignClientID ί�е�λ����
	 */
	public void setConsignClientID(long consignClientID)
	{
		this.consignClientID=consignClientID;
	}
	/**
	 * ��ȡί�е�λ
	 * @return long ί�е�λ����
	 */
	public long getConsignClientID()
	{
		return this.consignClientID ;
	}
    
	/**
	 * ���ý�λ����
	 * @param long borrowClientID ��λ����
	 */
	public void setBorrowClientID(long borrowClientID)
	{
		this.borrowClientID=borrowClientID;
	}
	/**
	 * ���ؽ�λ����
	 * @return long ��λ����
	 */
	public long getBorrowClientID()
	{
		return this.borrowClientID;
	}
    
	/**
	 * ����¼������
	 * @param inputDate Timestamp
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate=inputDate;
	}
    
	/**
	 * ��ȡ¼������
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return this.inputDate;
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

package com.iss.itreasury.settlement.upgathering.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


/**
 * @author ygzhao
 * 2005-8-16
 */
public class UpGatheringPolicyInfo extends ITreasuryBaseDataEntity
{
	private long Id = -1;			//����
	private long OfficeId = -1;		//���´���ʶ
	private long CurrencyId	= -1;	//���ֱ�ʶ
	private String Code = "";		//���ղ��Ա��
	private String Name	= "";		//���ղ�������
	private long UpClientId	= -1;	//���տͻ���ʶ
	private long PayeeAccountId	= -1;//�����˻���ʶ
	private long UpOrder= -1;			//���մ���
	private long UpType= -1;			//���շ�ʽ
	private long InputUserId= -1;	//¼����
	private Timestamp InputDate	= null;//¼������
	private long ModifyUserId= -1;		//�޸���
	private Timestamp ModifyDate = null;//�޸�����
	private long StatusId= -1;			//״̬
	private long PayerAccounts = 0;//�ò����¸���˻���Ŀ
	private double UpGatcheringAmount = 0.0;////�ò����·�������������ÿ���¼��˻����ս����ܺ�
	
	public String getCode()
	{
		return Code;
	}
	
	public void setCode(String code)
	{
		Code = code;
		putUsedField("Code",Code);
	}
	
	public long getCurrencyId()
	{
		return CurrencyId;
	}
	
	public void setCurrencyId(long currencyId)
	{
		CurrencyId = currencyId;
		putUsedField("CurrencyId",CurrencyId);
	}
	
	public long getId()
	{
		return Id;
	}
	
	public void setId(long id)
	{
		Id = id;
		putUsedField("Id",Id);
	}
	
	public Timestamp getInputDate()
	{
		return InputDate;
	}
	
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
		putUsedField("InputDate",InputDate);
	}
	
	public long getInputUserId()
	{
		return InputUserId;
	}
	
	public void setInputUserId(long inputUserId)
	{
		InputUserId = inputUserId;
		putUsedField("InputUserId",InputUserId);
	}
	
	public Timestamp getModifyDate()
	{
		return ModifyDate;
	}
	
	public void setModifyDate(Timestamp modifyDate)
	{
		ModifyDate = modifyDate;
		putUsedField("ModifyDate",ModifyDate);
	}
	
	public long getModifyUserId()
	{
		return ModifyUserId;
	}
	
	public void setModifyUserId(long modifyUserId)
	{
		ModifyUserId = modifyUserId;
		putUsedField("ModifyUserId",ModifyUserId);
	}
	
	public String getName()
	{
		return Name;
	}
	
	public void setName(String name)
	{
		Name = name;
		putUsedField("Name",Name);
	}
	
	public long getOfficeId()
	{
		return OfficeId;
	}
	
	public void setOfficeId(long officeId)
	{
		OfficeId = officeId;
		putUsedField("OfficeId",OfficeId);
	}
	
	public long getPayeeAccountId()
	{
		return PayeeAccountId;
	}
	
	public void setPayeeAccountId(long payeeAccountId)
	{
		PayeeAccountId = payeeAccountId;
		putUsedField("PayeeAccountId",PayeeAccountId);
	}
	
	public long getPayerAccounts()
	{
		return PayerAccounts;
	}
	
	public void setPayerAccounts(long payerAccounts)
	{
		PayerAccounts = payerAccounts;
	}
	
	public long getStatusId()
	{
		return StatusId;
	}
	
	public void setStatusId(long statusId)
	{
		StatusId = statusId;
		putUsedField("StatusId",StatusId);
	}
	
	public long getUpClientId()
	{
		return UpClientId;
	}
	
	public void setUpClientId(long upClientId)
	{
		UpClientId = upClientId;
		putUsedField("UpClientId",UpClientId);
	}
	
	public long getUpOrder()
	{
		return UpOrder;
	}
	
	public void setUpOrder(long upOrder)
	{
		UpOrder = upOrder;
		putUsedField("UpOrder",UpOrder);
	}
	
	public long getUpType()
	{
		return UpType;
	}
	
	public void setUpType(long upType)
	{
		UpType = upType;
		putUsedField("UpType",UpType);
	}

	
	public double getUpGatcheringAmount()
	{
		return UpGatcheringAmount;
	}

	
	public void setUpGatcheringAmount(double upGatcheringAmount)
	{
		UpGatcheringAmount = upGatcheringAmount;
	}

}

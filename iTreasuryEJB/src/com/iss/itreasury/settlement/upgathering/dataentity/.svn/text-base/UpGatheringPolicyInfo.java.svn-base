package com.iss.itreasury.settlement.upgathering.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


/**
 * @author ygzhao
 * 2005-8-16
 */
public class UpGatheringPolicyInfo extends ITreasuryBaseDataEntity
{
	private long Id = -1;			//主键
	private long OfficeId = -1;		//办事处标识
	private long CurrencyId	= -1;	//币种标识
	private String Code = "";		//上收策略编号
	private String Name	= "";		//上收策略名称
	private long UpClientId	= -1;	//上收客户标识
	private long PayeeAccountId	= -1;//上收账户标识
	private long UpOrder= -1;			//上收次序
	private long UpType= -1;			//上收方式
	private long InputUserId= -1;	//录入人
	private Timestamp InputDate	= null;//录入日期
	private long ModifyUserId= -1;		//修改人
	private Timestamp ModifyDate = null;//修改日期
	private long StatusId= -1;			//状态
	private long PayerAccounts = 0;//该策略下付款方账户数目
	private double UpGatcheringAmount = 0.0;////该策略下符合上收条件的每个下级账户上收金额的总和
	
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

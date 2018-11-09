package com.iss.itreasury.settlement.upgathering.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


/**
 * @author ygzhao
 * 2005-8-16
 */
public class UpGatheringDetailInfo extends ITreasuryBaseDataEntity
{
	private long Id = -1;			//主键
	private long OfficeId = -1;		//办事处标识
	private long CurrencyId = -1;	//币种标识
	private long PolicyId = -1;		//上收策略标识
	private long PayerClientId = -1;//付款方客户标识
	private long PayerAccountId = -1;//付款方账户号
	private double Limit = 0.0;		 //账户最低余额限制
	private double Unit = 0.0;		 //上收金额单位
	private double MaxAmount = 0.0;	 //最高上收金额
	private long SerialNo = -1;	 //序号
	private String Abstract = "";	 //摘要
	private long InputUserId = -1;	 //录入人
	private Timestamp InputDate	= null; //录入日期
	private long ModifyUserId = -1;		//修改人
	private Timestamp ModifyDate = null;//修改日期
	private long StatusId = -1;			//状态
	
	private double AccountBalance = 0.0;//账户余额
	private double UpGatcheringAmount = 0.0;//账户上收到上级账户的金额
	
	public String getAbstract()
	{
		return Abstract;
	}
	
	public void setAbstract(String abstract1)
	{
		Abstract = abstract1;
		putUsedField("Abstract",Abstract);
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
	
	public double getLimit()
	{
		return Limit;
	}
	
	public void setLimit(double limit)
	{
		Limit = limit;
		putUsedField("Limit",Limit);
	}
	
	public double getMaxAmount()
	{
		return MaxAmount;
	}
	
	public void setMaxAmount(double maxAmount)
	{
		MaxAmount = maxAmount;
		putUsedField("MaxAmount",MaxAmount);
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
	
	public long getOfficeId()
	{
		return OfficeId;
	}
	
	public void setOfficeId(long officeId)
	{
		OfficeId = officeId;
		putUsedField("OfficeId",OfficeId);
	}
	
	public long getPayerAccountId()
	{
		return PayerAccountId;
	}
	
	public void setPayerAccountId(long payerAccountId)
	{
		PayerAccountId = payerAccountId;
		putUsedField("PayerAccountId",PayerAccountId);
	}
	
	public long getPayerClientId()
	{
		return PayerClientId;
	}
	
	public void setPayerClientId(long payerClientId)
	{
		PayerClientId = payerClientId;
		putUsedField("PayerClientId",PayerClientId);
	}
	
	public long getPolicyId()
	{
		return PolicyId;
	}
	
	public void setPolicyId(long policyId)
	{
		PolicyId = policyId;
		putUsedField("PolicyId",PolicyId);
	}
	
	public long getSerialNo()
	{
		return SerialNo;
	}
	
	public void setSerialNo(long serialNo)
	{
		SerialNo = serialNo;
		putUsedField("SerialNo",SerialNo);
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
	
	public double getUnit()
	{
		return Unit;
	}
	
	public void setUnit(double unit)
	{
		Unit = unit;
		putUsedField("Unit",Unit);
	}

	
	public double getAccountBalance()
	{
		return AccountBalance;
	}

	
	public void setAccountBalance(double accountBalance)
	{
		AccountBalance = accountBalance;
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

/*
 * 创建日期 2003-10-8
 */
package com.iss.itreasury.settlement.transloan.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.settlement.util.SETTConstant;
/**
 * 委托贷款发放信息
 * @author yqwu
 */
public class GrantConsignLoanConditionInfo  implements Serializable
{
	private TransGrantLoanInfo info;
	
	public GrantConsignLoanConditionInfo()
	{
		info=new TransGrantLoanInfo(); 		
	}
	
	public GrantConsignLoanConditionInfo(TransGrantLoanInfo info)
	{
		this.info=info;
	}
	
	/**
	 * @return
	 */
	public TransGrantLoanInfo getInfo()
	{
		return info;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return info.getAmount();
	}

	/**
	 * @return
	 */
	public long getBankID()
	{
		return info.getBankID();
	}

	/**
	 * @return
	 */
	public long getConsignDepositAccountID()
	{
		return info.getConsignDepositAccountID();
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return info.getCurrencyID();
	}

	/**
	 * @return
	 */
	public long getDepositAccountID()
	{
		return info.getDepositAccountID();
	}

	/**
	 * @return
	 */
	public Timestamp getExecute()
	{
		return info.getExecute();
	}
	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return info.getInputUserID();
	}
	
	/**
	 * @return
	 */
	public double getInterestTaxRate()
	{
		return info.getInterestTaxRate();
	}
	
	/**
	 * @return
	 */
	public long getInterestTaxPlanId()
	{
		return info.getInterestTaxPlanId();
	}

	/**
	 * @return
	 */
	public long getLoanAccountID()
	{
		return info.getLoanAccountID();
	}

	/**
	 * @return
	 */
	public long getLoanContractID()
	{
		return info.getLoanContractID();
	}

	/**
	 * @return
	 */
	public long getLoanNoteID()
	{
		return info.getLoanNoteID();
	}
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return info.getOfficeID();
	}

	/**
	 * @return
	 */
	public long getPayCommisionAccountID()
	{
		return info.getPayCommisionAccountID();
	}

	/**
	 * @return
	 */
	public long getPayInterestAccountID()
	{
		return info.getPayInterestAccountID();
	}

	/**
	 * @return
	 */
	public long getPayTypeID()
	{
		return info.getPayTypeID();
	}

	/**
	 * @return
	 */
	public long getReceiveInterestAccountID()
	{
		return info.getReceiveInterestAccountID();
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return info.getStatusID();
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return info.getTransactionTypeID();
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		info.setAmount(d);
	}

	/**
	 * @param l
	 */
	public void setBankID(long l)
	{
		info.setBankID(l);
	}

	/**
	 * @param l
	 */
	public void setConsignDepositAccountID(long l)
	{
		info.setConsignDepositAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		info.setCurrencyID(l);
	}

	/**
	 * @param l
	 */
	public void setDepositAccountID(long l)
	{
		info.setDepositAccountID(l);
	}

	/**
	 * @param Timestamp
	 */
	public void setExecute(Timestamp Timestamp)
	{
		info.setExecute(Timestamp);
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		info.setInputUserID(l);
	}


	/**
	 * @param d
	 */
	public void setInterestTaxRate(double d)
	{
		info.setInterestTaxRate(d);
	}
	
	/**
	 * @param l
	 */
	public void setInterestTaxPlanId(long l)
	{
		info.setInterestTaxPlanId(l);
	}

	/**
	 * @param l
	 */
	public void setLoanAccountID(long l)
	{
		info.setLoanAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setLoanContractID(long l)
	{
		info.setLoanContractID(l);
	}

	/**
	 * @param l
	 */
	public void setLoanNoteID(long l)
	{
		info.setLoanNoteID(l);
	}


	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		info.setOfficeID(l);
	}

	/**
	 * @param l
	 */
	public void setPayCommisionAccountID(long l)
	{
		info.setPayCommisionAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l)
	{
		info.setPayInterestAccountID(l);
	}


	/**
	 * @param l
	 */
	public void setPayTypeID(long l)
	{
		info.setPayTypeID(l);
	}

	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l)
	{
		info.setReceiveInterestAccountID(l);
	}


	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		info.setStatusID(l);
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		info.setTransactionTypeID(l);
	}

	public long getIsKeepAccount(){
		
		return info.isKeepAccount()?SETTConstant.BooleanValue.ISTRUE:SETTConstant.BooleanValue.ISFALSE;
	}
	public void setIsKeepAccount(long lIsKeepAccount){
		info.setKeepAccount(lIsKeepAccount == SETTConstant.BooleanValue.ISTRUE?true:false);
	}
    
}

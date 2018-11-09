package com.iss.itreasury.settlement.transcommission.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @name: TransCommissionInfo
 * @description: 手续费收取
 * @author: gqfang
 * @create: 2005-8-25
 */
public class TransCommissionInfo extends SettlementBaseDataEntity
{
    private long      officeId          = -1;  // 办事处标识

    private long      currencyId        = -1;  // 币种标识

    private String    transNo           = null; // 手续费收取的交易号

    private long      transactionTypeId = -1;  // 产生手续费的交易类型

    private long      accountId         = -1;  // 手续费账户号

    private double    commissionAmount  = 0.00; // 手续费金额
    
    private double    rebate  = 0.00; 			// 折扣率
    
    private double    rebatecommissionAmount  = 0.00; // 实收手续费金额

    private Timestamp executeDate       = null; // 收取手续费的日期

    private long      inputUserId       = -1;  // 交易操作人

    private long      statusId          = -1;  // 状态
    
    
    //表中没有的字段
    private long mainTransactionId      = -1;  //产生手续费的交易的ID
    private long mainTransactionType    = -1;  //产生手续费的交易的交易类型
    private Timestamp interestStartDate = null;//起息日，默认是系统的开机日
    
    public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
		putUsedField("rebate", rebate);
	}

	public double getRebatecommissionAmount() {
		return rebatecommissionAmount;
	}

	public void setRebatecommissionAmount(double rebatecommissionAmount) {
		this.rebatecommissionAmount = rebatecommissionAmount;
		putUsedField("rebatecommissionAmount", rebatecommissionAmount);
	}

	/**
     * @return Returns the interestStartDate.
     */
    public Timestamp getInterestStartDate()
    {
        return interestStartDate;
    }

    /**
     * @param interestStartDate The interestStartDate to set.
     */
    public void setInterestStartDate(Timestamp interestStartDate)
    {
        this.interestStartDate = interestStartDate;
    }

    /**
     * @return Returns the mainTransactionId.
     */
    public long getMainTransactionId()
    {
        return mainTransactionId;
    }

    /**
     * @param mainTransactionId The mainTransactionId to set.
     */
    public void setMainTransactionId(long mainTransactionId)
    {
        this.mainTransactionId = mainTransactionId;
    }

    /**
     * @return Returns the mainTransactionType.
     */
    public long getMainTransactionType()
    {
        return mainTransactionType;
    }

    /**
     * @param mainTransactionType The mainTransactionType to set.
     */
    public void setMainTransactionType(long mainTransactionType)
    {
        this.mainTransactionType = mainTransactionType;
    }

    /**
     * @return Returns the accountId.
     */
    public long getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId The accountId to set.
     */
    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
        putUsedField("accountId", accountId);
    }

    /**
     * @return Returns the commissionAmount.
     */
    public double getCommissionAmount()
    {
        return commissionAmount;
    }

    /**
     * @param commissionAmount The commissionAmount to set.
     */
    public void setCommissionAmount(double commissionAmount)
    {
        this.commissionAmount = commissionAmount;
        putUsedField("commissionAmount", commissionAmount);
    }

    /**
     * @return Returns the currencyId.
     */
    public long getCurrencyId()
    {
        return currencyId;
    }

    /**
     * @param currencyId The currencyId to set.
     */
    public void setCurrencyId(long currencyId)
    {
        this.currencyId = currencyId;
        putUsedField("currencyId", currencyId);
    }

    /**
     * @return Returns the executeDate.
     */
    public Timestamp getExecuteDate()
    {
        return executeDate;
    }

    /**
     * @param executeDate The executeDate to set.
     */
    public void setExecuteDate(Timestamp executeDate)
    {
        this.executeDate = executeDate;
        putUsedField("executeDate", executeDate);
    }

    /**
     * @return Returns the inputUserId.
     */
    public long getInputUserId()
    {
        return inputUserId;
    }

    /**
     * @param inputUserId The inputUserId to set.
     */
    public void setInputUserId(long inputUserId)
    {
        this.inputUserId = inputUserId;
        putUsedField("inputUserId", inputUserId);
    }

    /**
     * @return Returns the officeId.
     */
    public long getOfficeId()
    {
        return officeId;
    }

    /**
     * @param officeId The officeId to set.
     */
    public void setOfficeId(long officeId)
    {
        this.officeId = officeId;
        putUsedField("officeId", officeId);
    }

    /**
     * @return Returns the statusId.
     */
    public long getStatusId()
    {
        return statusId;
    }

    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId)
    {
        this.statusId = statusId;
        putUsedField("statusId", statusId);
    }

    /**
     * @return Returns the transactionTypeId.
     */
    public long getTransactionTypeId()
    {
        return transactionTypeId;
    }

    /**
     * @param transactionTypeId The transactionTypeId to set.
     */
    public void setTransactionTypeId(long transactionTypeId)
    {
        this.transactionTypeId = transactionTypeId;
        putUsedField("transactionTypeId", transactionTypeId);
    }

    /**
     * @return Returns the transNo.
     */
    public String getTransNo()
    {
        return transNo;
    }

    /**
     * @param transNo The transNo to set.
     */
    public void setTransNo(String transNo)
    {
        this.transNo = transNo;
        putUsedField("transNo", transNo);
    }
}

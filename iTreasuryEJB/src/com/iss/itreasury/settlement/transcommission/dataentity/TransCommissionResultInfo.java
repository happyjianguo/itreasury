package com.iss.itreasury.settlement.transcommission.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @name: TransCommissionInfo
 * @description: ��������ȡ
 * @author: gqfang
 * @create: 2005-8-25
 */
public class TransCommissionResultInfo extends SettlementBaseDataEntity
{
    private long      id                = -1;  // ����

    private long      officeId          = -1;  // ���´���ʶ

    private long      currencyId        = -1;  // ���ֱ�ʶ

    private String    transNo           = null; // ��������ȡ�Ľ��׺�

    private long      transactionTypeId = -1;  // ���������ѵĽ�������

    private long      accountId         = -1;  // �������˻���

    private double    commissionAmount  = 0.00; // �����ѽ��
    
    private double    rebate  = 0.00; 			// �ۿ���
    
    private double    rebatecommissionAmount  = 0.00; // ʵ�������ѽ��

    private Timestamp executeDate       = null; // ��ȡ�����ѵ�����

    private long      inputUserId       = -1;  // ���ײ�����

    private long      statusId          = -1;  // ״̬

    // ��sett_TransCommission��û�е��ֶΣ�

    private double    amount            = 0.00; // ���������ѵĽ��׵Ľ��

    private long      countNum          = -1;   // һ�ʽ��׻����˼���������

    private Timestamp maxDate           = null; // �������������ѵ��У����������ѵĽ��׵���������

    private Timestamp minDate           = null; // �������������ѵ��У����������ѵĽ��׵���������

    private String    year              = "";

    private String    month             = "";

    private String    day               = "";

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

    /**
     * @return Returns the amount.
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * @param amount The amount to set.
     */
    public void setAmount(double amount)
    {
        this.amount = amount;
        putUsedField("amount", amount);
    }

    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
        putUsedField("id", id);
    }

    /**
     * @return Returns the maxDate.
     */
    public Timestamp getMaxDate()
    {
        return maxDate;
    }

    /**
     * @param maxDate The maxDate to set.
     */
    public void setMaxDate(Timestamp maxDate)
    {
        this.maxDate = maxDate;
    }

    /**
     * @return Returns the minDate.
     */
    public Timestamp getMinDate()
    {
        return minDate;
    }

    /**
     * @param minDate The minDate to set.
     */
    public void setMinDate(Timestamp minDate)
    {
        this.minDate = minDate;
    }

    /**
     * @return Returns the day.
     */
    public String getDay()
    {
        return day;
    }

    /**
     * @param day The day to set.
     */
    public void setDay(String day)
    {
        this.day = day;
    }

    /**
     * @return Returns the month.
     */
    public String getMonth()
    {
        return month;
    }

    /**
     * @param month The month to set.
     */
    public void setMonth(String month)
    {
        this.month = month;
    }

    /**
     * @return Returns the year.
     */
    public String getYear()
    {
        return year;
    }

    /**
     * @param year The year to set.
     */
    public void setYear(String year)
    {
        this.year = year;
    }

    /**
     * @return Returns the countNum.
     */
    public long getCountNum()
    {
        return countNum;
    }

    /**
     * @param countNum The countNum to set.
     */
    public void setCountNum(long countNum)
    {
        this.countNum = countNum;
    }

}

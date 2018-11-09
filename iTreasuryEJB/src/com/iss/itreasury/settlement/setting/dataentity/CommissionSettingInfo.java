package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class CommissionSettingInfo extends SettlementBaseDataEntity
{

    private long      commissionType   = -1;  // 1�����أ�2�����

    private double    amountFrom       = 0.00; // �����

    private double    amountTo         = 0.00; // ���ֹ

    private double    commissionRate   = 0.00; // ��������

    private double    commissionAmount = 0.00; // �����ѽ��

    private long      inputUserId      = -1;  // ¼����

    private Timestamp inputDate        = null; // ¼������

    private long      modifyUserId     = -1;  // �޸���

    private Timestamp modifyDate       = null; // �޸�����

    private long      statusId         = -1;  // ״̬ ��1��������2��ɾ��
    
    private long      isUrgent       = -1;  //  �Ƿ�Ӽ� 1����ͨ 2���Ӽ�
    
    // ƾ֤���� for ��� 2010-10-29
    private long voucherTypeID = -1 ;       // ƾ֤���ͱ��
    
    private long officeID = -1;
    
    private long currencyID = -1;
    
    public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		 putUsedField("currencyID",currencyID);
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		 putUsedField("officeID",officeID);
	}

	/**
     * @return Returns the amountFrom.
     */
    public double getAmountFrom()
    {
        return amountFrom;
    }

    /**
     * @param amountFrom The amountFrom to set.
     */
    public void setAmountFrom(double amountFrom)
    {
        this.amountFrom = amountFrom;
        putUsedField("amountFrom",amountFrom);
    }

    /**
     * @return Returns the amountTo.
     */
    public double getAmountTo()
    {
        return amountTo;
    }

    /**
     * @param amountTo The amountTo to set.
     */
    public void setAmountTo(double amountTo)
    {
        this.amountTo = amountTo;
        putUsedField("amountTo",amountTo);
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
        putUsedField("commissionAmount",commissionAmount);
    }

    /**
     * @return Returns the commissionRate.
     */
    public double getCommissionRate()
    {
        return commissionRate;
    }

    /**
     * @param commissionRate The commissionRate to set.
     */
    public void setCommissionRate(double commissionRate)
    {
        this.commissionRate = commissionRate;
        putUsedField("commissionRate",commissionRate);
    }

    /**
     * @return Returns the commissionType.
     */
    public long getCommissionType()
    {
        return commissionType;
    }

    /**
     * @param commissionType The commissionType to set.
     */
    public void setCommissionType(long commissionType)
    {
        this.commissionType = commissionType;
        putUsedField("commissionType",commissionType);
    }

    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }

    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        this.inputDate = inputDate;
        putUsedField("inputDate",inputDate);
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
        putUsedField("inputUserId",inputUserId);
    }

    /**
     * @return Returns the modifyDate.
     */
    public Timestamp getModifyDate()
    {
        return modifyDate;
    }

    /**
     * @param modifyDate The modifyDate to set.
     */
    public void setModifyDate(Timestamp modifyDate)
    {
        this.modifyDate = modifyDate;
        putUsedField("modifyDate",modifyDate);
    }

    /**
     * @return Returns the modifyUserId.
     */
    public long getModifyUserId()
    {
        return modifyUserId;
    }

    /**
     * @param modifyUserId The modifyUserId to set.
     */
    public void setModifyUserId(long modifyUserId)
    {
        this.modifyUserId = modifyUserId;
        putUsedField("modifyUserId",modifyUserId);
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
        putUsedField("statusId",statusId);
    }

	public long getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(long isUrgent) {
		this.isUrgent = isUrgent;
		putUsedField("isUrgent",isUrgent);
	}

	public long getVoucherTypeID() {
		return voucherTypeID;
	}

	public void setVoucherTypeID(long voucherTypeID) {
		this.voucherTypeID = voucherTypeID;
		putUsedField("voucherTypeID",voucherTypeID);
	}



    

    
}

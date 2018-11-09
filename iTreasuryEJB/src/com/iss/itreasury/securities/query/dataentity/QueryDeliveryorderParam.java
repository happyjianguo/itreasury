/*
 * Created on 2004-4-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author chluo
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryDeliveryorderParam extends SECBaseDataEntity	implements Serializable
{
//  ������¼�����ڿ�ʼ��
	private Timestamp noticeInputDateStart   = null;
	//������¼�����ڽ�����
	private Timestamp noticeInputDateEnd     = null;
	
	//�ɽ�������
	private Timestamp transactionDateStart   = null;
	
	//�ɽ�����ֹ
	private Timestamp transactionDateEnd     = null;
	
	//ҵ������ID
	private long businessTypeId             = -1;
	
    //��������ID
	private String[] transactionTypeIds      = null;
	
	
	//ҵ��λID
	private long clientId  = -1;
	
	
	//�ɶ��ʻ�ID
	private String[] stockHolderAccountIds = null;
	
	//���׶���ID --- ��������Ŀ�Ĳ�ѯ����
	private String[] counterPartIds = null;
	
	//���׶���ID --- ��������Ŀ�Ĳ�ѯ����
	private String[] interBankCounterPartIds = null;

    //����Ӫҵ��ID
	private long bankOfDepositId = -1;
	//�ʽ��˺�
	private String[] accountIds = null;
	
	//�������˾ID --- ��������Ŀ�Ĳ�ѯ����
	private String[] fundManagerCoIds = null;
	
	//֤ȯ����
	private String[] securitiesIds = null;
	
    //���״̬ID
	private long statusId = -1;
	
    //¼����
	private long inputUserId = -1;
	
    //�Ƿ�Υ��
	private long isViolation = -1;
	
	//��Ӧ�����뵥id
	//�����뵥����ϸ�����ť���뽻���ѯר��
	private long applyFormId = -1;
	
	
	//����ʽ
	private long desc = -1;
	//�����ֶ�
	private long orderField = -1;
	
	
	
	
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	} 
	

	

    /**
     * @return Returns the accountIds.
     */
    public String[] getAccountIds() {
        return accountIds;
    }
    /**
     * @param accountIds The accountIds to set.
     */
    public void setAccountIds(String[] accountIds) {
        this.accountIds = accountIds;
    }
    /**
     * @return Returns the bankOfDepositId.
     */
    public long getBankOfDepositId() {
        return bankOfDepositId;
    }
    /**
     * @param bankOfDepositId The bankOfDepositId to set.
     */
    public void setBankOfDepositId(long bankOfDepositId) {
        this.bankOfDepositId = bankOfDepositId;
    }
    /**
     * @return Returns the businessTypeId.
     */
    public long getBusinessTypeId() {
        return businessTypeId;
    }
    /**
     * @param businessTypeId The businessTypeId to set.
     */
    public void setBusinessTypeId(long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }
    /**
     * @return Returns the clientId.
     */
    public long getClientId() {
        return clientId;
    }
    /**
     * @param clientId The clientId to set.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
    /**
     * @return Returns the desc.
     */
    public long getDesc() {
        return desc;
    }
    /**
     * @param desc The desc to set.
     */
    public void setDesc(long desc) {
        this.desc = desc;
    }
    /**
     * @return Returns the fundManagerCoIds.
     */
    public String[] getFundManagerCoIds() {
        return fundManagerCoIds;
    }
    /**
     * @param fundManagerCoIds The fundManagerCoIds to set.
     */
    public void setFundManagerCoIds(String[] fundManagerCoIds) {
        this.fundManagerCoIds = fundManagerCoIds;
    }
    /**
     * @return Returns the inputUserId.
     */
    public long getInputUserId() {
        return inputUserId;
    }
    /**
     * @param inputUserId The inputUserId to set.
     */
    public void setInputUserId(long inputUserId) {
        this.inputUserId = inputUserId;
    }
    /**
     * @return Returns the interBankCounterPartIds.
     */
    public String[] getInterBankCounterPartIds() {
        return interBankCounterPartIds;
    }
    /**
     * @param interBankCounterPartIds The interBankCounterPartIds to set.
     */
    public void setInterBankCounterPartIds(String[] interBankCounterPartIds) {
        this.interBankCounterPartIds = interBankCounterPartIds;
    }
    /**
     * @return Returns the isViolation.
     */
    public long getIsViolation() {
        return isViolation;
    }
    /**
     * @param isViolation The isViolation to set.
     */
    public void setIsViolation(long isViolation) {
        this.isViolation = isViolation;
    }
    /**
     * @return Returns the noticeInputDateEnd.
     */
    public Timestamp getNoticeInputDateEnd() {
        return noticeInputDateEnd;
    }
    /**
     * @param noticeInputDateEnd The noticeInputDateEnd to set.
     */
    public void setNoticeInputDateEnd(Timestamp noticeInputDateEnd) {
        this.noticeInputDateEnd = noticeInputDateEnd;
    }
/**
 * @return Returns the noticeInputDateStart.
 */
public Timestamp getNoticeInputDateStart() {
    return noticeInputDateStart;
}
/**
 * @param noticeInputDateStart The noticeInputDateStart to set.
 */
public void setNoticeInputDateStart(Timestamp noticeInputDateStart) {
    this.noticeInputDateStart = noticeInputDateStart;
}
    /**
     * @return Returns the orderField.
     */
    public long getOrderField() {
        return orderField;
    }
    /**
     * @param orderField The orderField to set.
     */
    public void setOrderField(long orderField) {
        this.orderField = orderField;
    }
    /**
     * @return Returns the securitiesIds.
     */
    public String[] getSecuritiesIds() {
        return securitiesIds;
    }
    /**
     * @param securitiesIds The securitiesIds to set.
     */
    public void setSecuritiesIds(String[] securitiesIds) {
        this.securitiesIds = securitiesIds;
    }
    /**
     * @return Returns the statusId.
     */
    public long getStatusId() {
        return statusId;
    }
    /**
     * @param statusId The statusId to set.
     */
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
    /**
     * @return Returns the stockHolderAccountIds.
     */
    public String[] getStockHolderAccountIds() {
        return stockHolderAccountIds;
    }
    /**
     * @param stockHolderAccountIds The stockHolderAccountIds to set.
     */
    public void setStockHolderAccountIds(String[] stockHolderAccountIds) {
        this.stockHolderAccountIds = stockHolderAccountIds;
    }
    /**
     * @return Returns the transactionTypeIds.
     */
    public String[] getTransactionTypeIds() {
        return transactionTypeIds;
    }
    /**
     * @param transactionTypeIds The transactionTypeIds to set.
     */
    public void setTransactionTypeIds(String[] transactionTypeIds) {
        this.transactionTypeIds = transactionTypeIds;
    }
	/**
	 * @return
	 */
	public long getApplyFormId() {
		return applyFormId;
	}

	/**
	 * @param l
	 */
	public void setApplyFormId(long l) {
		applyFormId = l;
	}

	/**
	 * @return
	 */
	public String[] getCounterPartIds()
	{
		return counterPartIds;
	}

	/**
	 * @param strings
	 */
	public void setCounterPartIds(String[] strings)
	{
		counterPartIds = strings;
	}

	/**
	 * @return Returns the transactionDateEnd.
	 */
	public Timestamp getTransactionDateEnd() {
		return transactionDateEnd;
	}
	/**
	 * @param transactionDateEnd The transactionDateEnd to set.
	 */
	public void setTransactionDateEnd(Timestamp transactionDateEnd) {
		this.transactionDateEnd = transactionDateEnd;
	}
	/**
	 * @return Returns the transactionDateStart.
	 */
	public Timestamp getTransactionDateStart() {
		return transactionDateStart;
	}
	/**
	 * @param transactionDateStart The transactionDateStart to set.
	 */
	public void setTransactionDateStart(Timestamp transactionDateStart) {
		this.transactionDateStart = transactionDateStart;
	}
}

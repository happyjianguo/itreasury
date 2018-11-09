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
 * @author wangyi
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryStockHolderAccountFormParam extends SECBaseDataEntity	implements Serializable {
	//ҵ��֪ͨ��¼�����ڿ�ʼ��
	private Timestamp noticeInputDateStart   = null;
	//ҵ��֪ͨ��¼�����ڽ�����
	private Timestamp noticeInputDateEnd     = null;
	//ҵ��֪ͨ���ո�������
	private Timestamp noticeExecuteDate      = null;
	//ҵ��λID
	private String[] clientIds               = null;
	//ҵ������ID
	private long businessTypeId              = -1;
	//��������ID
	private String[] transactionTypeIds      = null;
	//���׶���ID
	private String[] interBankCounterPartIds = null;
	//����Ӫҵ��ID
	private String[] bankOfDepositIds        = null;
	//�������˾ID
	private String[] fundManagerCoIds        = null;
	//�ʽ��˺�
	private String[] accountIds              = null;
	//֤ȯ����
	private String[] securitiesIds           = null;
	//����ʽ
	private long desc                       = -1;
	//�����ֶ�
	private long orderField                 = -1;
	//ҵ��״̬ID
	private long businessAttributeId        = -1;
	
	
	
	
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
	 * @return Returns the noticeExecuteDate.
	 */
	public Timestamp getNoticeExecuteDate() {
		return noticeExecuteDate;
	}

	/**
	 * @param noticeExecuteDate The noticeExecuteDate to set.
	 */
	public void setNoticeExecuteDate(Timestamp noticeExecuteDate) {
		this.noticeExecuteDate = noticeExecuteDate;
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
	 * @return Returns the bankOfDepositIds.
	 */
	public String[] getBankOfDepositIds() {
		return bankOfDepositIds;
	}

	/**
	 * @param bankOfDepositIds The bankOfDepositIds to set.
	 */
	public void setBankOfDepositIds(String[] bankOfDepositIds) {
		this.bankOfDepositIds = bankOfDepositIds;
	}

	/**
	 * @return Returns the clientIds.
	 */
	public String[] getClientIds() {
		return clientIds;
	}

	/**
	 * @param clientIds The clientIds to set.
	 */
	public void setClientIds(String[] clientIds) {
		this.clientIds = clientIds;
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
	 * @return Returns the transactionTypeIda.
	 */
	public String[] getTransactionTypeIds() {
		return transactionTypeIds;
	}

	/**
	 * @param transactionTypeIda The transactionTypeIda to set.
	 */
	public void setTransactionTypeIds(String[] transactionTypeIds) {
		this.transactionTypeIds = transactionTypeIds;
	}

	/**
	 * @return Returns the businessAttributeId.
	 */
	public long getBusinessAttributeId() {
		return businessAttributeId;
	}

	/**
	 * @param businessAttributeId The businessAttributeId to set.
	 */
	public void setBusinessAttributeId(long businessAttributeId) {
		this.businessAttributeId = businessAttributeId;
	}

}

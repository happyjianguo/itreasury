/*
 * �������� 2004-5-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
/** 
 * @author ����
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSecuritiesTransferParam extends SECBaseDataEntity	implements Serializable {
    //ҵ��֪ͨ��¼�����ڿ�ʼ��
	private Timestamp noticeInputDateStart   = null;
	//ҵ��֪ͨ��¼�����ڽ�����
	private Timestamp noticeInputDateEnd     = null;
	//ҵ��֪ͨ���ո�������
	private Timestamp noticeExecuteDate      = null;
	
	// bank ҵ��λID
	private long clientIds_bank               = -1;
	// exchange ҵ��λID
	private long clientIds_exchange               = -1;
	
	// �ɶ��ʻ�����
	private String[] stockHolderAccountIds_bank		= null;
	// �ɶ��ʻ�����
	private String[] stockHolderAccountIds_exchange		= null;

	// exchange ����Ӫҵ��ID1
	private long bankOfDepositIds_bank        = -1;
    // exchange ����Ӫҵ��ID2
	private long bankOfDepositIds_exchange        = -1;
	// exchange �ʽ��˺�1
	private String[] accountIds_bank              = null;
	// exchange �ʽ��˺�2
	private String[] accountIds_exchange              = null;
	
	//֤ȯ����1
	private String[] securitiesIds_bank           = null;
	//֤ȯ����2
	private String[] securitiesIds_exchange           = null;
	
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
	 * @return
	 */
	public String[] getAccountIds_bank() {
		return accountIds_bank;
	}

	/**
	 * @return
	 */
	public String[] getAccountIds_exchange() {
		return accountIds_exchange;
	}

	/**
	 * @return
	 */
	public long getBankOfDepositIds_bank() {
		return bankOfDepositIds_bank;
	}

	/**
	 * @return
	 */
	public long getBankOfDepositIds_exchange() {
		return bankOfDepositIds_exchange;
	}

	/**
	 * @return
	 */
	public long getBusinessAttributeId() {
		return businessAttributeId;
	}

	/**
	 * @return
	 */
	public long getClientIds_bank() {
		return clientIds_bank;
	}

	/**
	 * @return
	 */
	public long getClientIds_exchange() {
		return clientIds_exchange;
	}

	/**
	 * @return
	 */
	public long getDesc() {
		return desc;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeExecuteDate() {
		return noticeExecuteDate;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeInputDateEnd() {
		return noticeInputDateEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getNoticeInputDateStart() {
		return noticeInputDateStart;
	}

	/**
	 * @return
	 */
	public long getOrderField() {
		return orderField;
	}

	/**
	 * @return
	 */
	public String[] getSecuritiesIds_bank() {
		return securitiesIds_bank;
	}

	/**
	 * @return
	 */
	public String[] getSecuritiesIds_exchange() {
		return securitiesIds_exchange;
	}

	/**
	 * @return
	 */
	public String[] getStockHolderAccountIds_bank() {
		return stockHolderAccountIds_bank;
	}

	/**
	 * @return
	 */
	public String[] getStockHolderAccountIds_exchange() {
		return stockHolderAccountIds_exchange;
	}

	/**
	 * @param strings
	 */
	public void setAccountIds_bank(String[] strings) {
		accountIds_bank = strings;
	}

	/**
	 * @param strings
	 */
	public void setAccountIds_exchange(String[] strings) {
		accountIds_exchange = strings;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositIds_bank(long l) {
		bankOfDepositIds_bank = l;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositIds_exchange(long l) {
		bankOfDepositIds_exchange = l;
	}

	/**
	 * @param l
	 */
	public void setBusinessAttributeId(long l) {
		businessAttributeId = l;
	}

	/**
	 * @param l
	 */
	public void setClientIds_bank(long l) {
		clientIds_bank = l;
	}

	/**
	 * @param l
	 */
	public void setClientIds_exchange(long l) {
		clientIds_exchange = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		desc = l;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeExecuteDate(Timestamp timestamp) {
		noticeExecuteDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeInputDateEnd(Timestamp timestamp) {
		noticeInputDateEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setNoticeInputDateStart(Timestamp timestamp) {
		noticeInputDateStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l) {
		orderField = l;
	}

	/**
	 * @param strings
	 */
	public void setSecuritiesIds_bank(String[] strings) {
		securitiesIds_bank = strings;
	}

	/**
	 * @param strings
	 */
	public void setSecuritiesIds_exchange(String[] strings) {
		securitiesIds_exchange = strings;
	}

	/**
	 * @param strings
	 */
	public void setStockHolderAccountIds_bank(String[] strings) {
		stockHolderAccountIds_bank = strings;
	}

	/**
	 * @param strings
	 */
	public void setStockHolderAccountIds_exchange(String[] strings) {
		stockHolderAccountIds_exchange = strings;
	}

}
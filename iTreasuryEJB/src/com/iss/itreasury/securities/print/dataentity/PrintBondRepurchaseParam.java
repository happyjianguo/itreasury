/*
 * Created on 2004-5-13
 * @author ygzhao
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.securities.print.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;

public class PrintBondRepurchaseParam extends SECBaseDataEntity implements Serializable
{
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id)
	{
		
	}
	
    //�ɽ����ڿ�ʼ��
	private Timestamp noticeInputDateStart   = null;
	//�ɽ����ڽ�����
	private Timestamp noticeInputDateEnd     = null;
	
	private Timestamp deliveryOrderInputDateStart   = null;
	private Timestamp deliveryOrderInputDateEnd   = null;
	
	//ҵ��λID
	private long clientIds               = -1;
	
	 // �ɶ��ʻ�����
	 private String[] stockHolderAccountIds		= null;
	
	 // bank ��������ID
	 private String[] transactionTypeIds      = null;
	 
	//	���׶���
	private String[] counterpartIds = null;

	 // exchange ����Ӫҵ��ID
	 private long bankOfDepositIds        = -1;
	 
	 // exchange �ʽ��˺�
	 private String[] accountIds              = null;	

	



	/**
	 * @return
	 */
	public String[] getAccountIds() {
		return accountIds;
	}

	/**
	 * @return
	 */
	public long getBankOfDepositIds() {
		return bankOfDepositIds;
	}

	/**
	 * @return
	 */
	public long getClientIds() {
		return clientIds;
	}

	/**
	 * @return
	 */
	public String[] getCounterpartIds() {
		return counterpartIds;
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
	public String[] getStockHolderAccountIds() {
		return stockHolderAccountIds;
	}

	/**
	 * @return
	 */
	public String[] getTransactionTypeIds() {
		return transactionTypeIds;
	}

	/**
	 * @param strings
	 */
	public void setAccountIds(String[] strings) {
		accountIds = strings;
	}

	/**
	 * @param l
	 */
	public void setBankOfDepositIds(long l) {
		bankOfDepositIds = l;
	}

	/**
	 * @param l
	 */
	public void setClientIds(long l) {
		clientIds = l;
	}

	/**
	 * @param strings
	 */
	public void setCounterpartIds(String[] strings) {
		counterpartIds = strings;
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
	 * @param strings
	 */
	public void setStockHolderAccountIds(String[] strings) {
		stockHolderAccountIds = strings;
	}

	/**
	 * @param strings
	 */
	public void setTransactionTypeIds(String[] strings) {
		transactionTypeIds = strings;
	}

	/**
	 * @return Returns the deliveryOrderInputDateEnd.
	 */
	public Timestamp getDeliveryOrderInputDateEnd() {
		return deliveryOrderInputDateEnd;
	}
	/**
	 * @param deliveryOrderInputDateEnd The deliveryOrderInputDateEnd to set.
	 */
	public void setDeliveryOrderInputDateEnd(Timestamp deliveryOrderInputDateEnd) {
		this.deliveryOrderInputDateEnd = deliveryOrderInputDateEnd;
	}
	/**
	 * @return Returns the deliveryOrderInputDateStart.
	 */
	public Timestamp getDeliveryOrderInputDateStart() {
		return deliveryOrderInputDateStart;
	}
	/**
	 * @param deliveryOrderInputDateStart The deliveryOrderInputDateStart to set.
	 */
	public void setDeliveryOrderInputDateStart(
			Timestamp deliveryOrderInputDateStart) {
		this.deliveryOrderInputDateStart = deliveryOrderInputDateStart;
	}
}



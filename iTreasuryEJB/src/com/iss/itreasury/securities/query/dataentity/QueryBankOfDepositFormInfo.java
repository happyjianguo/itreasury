/*
 * Created on 2004-4-13
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author wangyi
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class QueryBankOfDepositFormInfo extends SECBaseDataEntity	implements Serializable {

	//ҵ��֪ͨ�����
	private String noticeCode          = "";
	//��Ӧ������
	private String deliveryOrderCode   = "";
	//ҵ��λ����
	private String clientName          = "";
	//ҵ������
	private String businessTypeName    = "";
	//ҵ������
	private long businessTypeId        = -1;
	//��������
	private String transactionTypeName = "";
	//��������Id
	private long transactionTypeID     = -1;
	//ҵ��֪ͨ��¼������
	private Timestamp noticeInputDate  = null;
	
	//����ɽ���ʼ��
	private Timestamp transactionStartDate  = null;
	//����ɽ�������
	private Timestamp transactionEndDate  = null;
	
	
	//֪ͨ���ɽ�����
	private Timestamp transactionDate  = null;
	//���׶������� (����֮һ������Ӫҵ�����������˾�����׶���)
	private String counterPartName     = "";
	//�ʽ��˻�
	private String accountcode         = "";
	//֤ȯ����
	private String securitiesName      = "";
	//ʵ���ո�
	private double netIncome           = 0.0;
	//ҵ��֪ͨ��״̬
	private long noticeStatusId        = -1;
	//¼����
	private String userName            = "";
	//ҵ������ID 
	//           businessAttributeId��1ʱ���׶����ǽ��׶���
    //           businessAttributeId��2ʱ���׶����ǿ���Ӫҵ��
	//           businessAttributeId��3ʱ���׶����ǻ������˾
	private long  businessAttributeId  = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Returns the accountcode.
	 */
	public String getAccountcode() {
		return accountcode;
	}

	/**
	 * @param accountcode The accountcode to set.
	 */
	public void setAccountcode(String accountcode) {
		this.accountcode = accountcode;
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
	 * @return Returns the businessTypeName.
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName The businessTypeName to set.
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return Returns the counterPartName.
	 */
	public String getCounterPartName() {
		return counterPartName;
	}

	/**
	 * @param counterPartName The counterPartName to set.
	 */
	public void setCounterPartName(String counterPartName) {
		this.counterPartName = counterPartName;
	}

	/**
	 * @return Returns the deliveryOrderCode.
	 */
	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}

	/**
	 * @param deliveryOrderCode The deliveryOrderCode to set.
	 */
	public void setDeliveryOrderCode(String deliveryOrderCode) {
		this.deliveryOrderCode = deliveryOrderCode;
	}

	/**
	 * @return Returns the netIncome.
	 */
	public double getNetIncome() {
		return netIncome;
	}

	/**
	 * @param netIncome The netIncome to set.
	 */
	public void setNetIncome(double netIncome) {
		this.netIncome = netIncome;
	}

	/**
	 * @return Returns the noticeCode.
	 */
	public String getNoticeCode() {
		return noticeCode;
	}

	/**
	 * @param noticeCode The noticeCode to set.
	 */
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
	}

	/**
	 * @return Returns the noticeInputDate.
	 */
	public Timestamp getNoticeInputDate() {
		return noticeInputDate;
	}

	/**
	 * @param noticeInputDate The noticeInputDate to set.
	 */
	public void setNoticeInputDate(Timestamp noticeInputDate) {
		this.noticeInputDate = noticeInputDate;
	}

	/**
	 * @return Returns the noticeStatusId.
	 */
	public long getNoticeStatusId() {
		return noticeStatusId;
	}

	/**
	 * @param noticeStatusId The noticeStatusId to set.
	 */
	public void setNoticeStatusId(long noticeStatusId) {
		this.noticeStatusId = noticeStatusId;
	}

	/**
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return securitiesName;
	}

	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		this.securitiesName = securitiesName;
	}

	/**
	 * @return Returns the transactionDate.
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return transactionTypeID;
	}

	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		this.transactionTypeID = transactionTypeID;
	}

	/**
	 * @return Returns the transactionTypeName.
	 */
	public String getTransactionTypeName() {
		return transactionTypeName;
	}

	/**
	 * @param transactionTypeName The transactionTypeName to set.
	 */
	public void setTransactionTypeName(String transactionTypeName) {
		this.transactionTypeName = transactionTypeName;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the businessAttributeID.
	 */
	public long getBusinessAttributeId() {
		return businessAttributeId;
	}

	/**
	 * @param businessAttributeID The businessAttributeID to set.
	 */
	public void setBusinessAttributeId(long businessAttributeId) {
		this.businessAttributeId = businessAttributeId;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionEndDate() {
		return transactionEndDate;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionStartDate() {
		return transactionStartDate;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionEndDate(Timestamp timestamp) {
		transactionEndDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionStartDate(Timestamp timestamp) {
		transactionStartDate = timestamp;
	}

}

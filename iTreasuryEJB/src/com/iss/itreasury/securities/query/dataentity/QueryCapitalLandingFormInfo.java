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
public class QueryCapitalLandingFormInfo extends SECBaseDataEntity	implements Serializable {
	
	//ҵ��������ID
	private long applyId              = -1;
	//ҵ����������
	private String applyCode          = "";
	//ҵ����������ID
	private long counterpartId		= -1;
	//���׶��ֱ��
	private String counterpartCode              = "";
	//���׶�������
	private String counterpartName          = "";
	
	//ҵ����������
	private String businessTypeName    = "";
	//ҵ�����ͱ��
	private long businessTypeId        = -1;
	
	//���ſ�ʼ����
	private Timestamp transactionStartDate  = null;
	//���Ž�������
	private Timestamp transactionEndDate  = null;
	//��������
	private long transactionTypeId = -1;
	//���Ŷ��
	private double pledgeSecuritiesAmount = 0.0;
	
	private double  outPledgeSecuritiesAmount =0.0;//�ʽ������Ŷ��
	private double  inPledgeSecuritiesAmount =0.0;//���׶����ṩ�����Ŷ��
	//��������
	private long term = -1;
	//���ӵ�״̬
	private long statusId = -1;
	//¼����
	private String inputUserName = "";


	/* ���� Javadoc��
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId() {
		// TODO �Զ����ɷ������
		return 0;
	}

	/* ���� Javadoc��
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id) {
		// TODO �Զ����ɷ������
		
	}





	/**
	 * @return
	 */
	public String getApplyCode() {
		return applyCode;
	}

	/**
	 * @return
	 */
	public long getApplyId() {
		return applyId;
	}

	/**
	 * @return
	 */
	public long getBusinessTypeId() {
		return businessTypeId;
	}

	/**
	 * @return
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @return
	 */
	public String getCounterpartCode() {
		return counterpartCode;
	}

	/**
	 * @return
	 */
	public long getCounterpartId() {
		return counterpartId;
	}

	/**
	 * @return
	 */
	public String getCounterpartName() {
		return counterpartName;
	}

	/**
	 * @return
	 */
	public double getInPledgeSecuritiesAmount() {
		return inPledgeSecuritiesAmount;
	}

	/**
	 * @return
	 */
	public String getInputUserName() {
		return inputUserName;
	}

	/**
	 * @return
	 */
	public double getOutPledgeSecuritiesAmount() {
		return outPledgeSecuritiesAmount;
	}

	/**
	 * @return
	 */
	public double getPledgeSecuritiesAmount() {
		return pledgeSecuritiesAmount;
	}

	/**
	 * @return
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * @return
	 */
	public long getTerm() {
		return term;
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
	 * @return
	 */
	public long getTransactionTypeId() {
		return transactionTypeId;
	}

	/**
	 * @param string
	 */
	public void setApplyCode(String string) {
		applyCode = string;
	}

	/**
	 * @param l
	 */
	public void setApplyId(long l) {
		applyId = l;
	}

	/**
	 * @param l
	 */
	public void setBusinessTypeId(long l) {
		businessTypeId = l;
	}

	/**
	 * @param string
	 */
	public void setBusinessTypeName(String string) {
		businessTypeName = string;
	}

	/**
	 * @param string
	 */
	public void setCounterpartCode(String string) {
		counterpartCode = string;
	}

	/**
	 * @param l
	 */
	public void setCounterpartId(long l) {
		counterpartId = l;
	}

	/**
	 * @param string
	 */
	public void setCounterpartName(String string) {
		counterpartName = string;
	}

	/**
	 * @param d
	 */
	public void setInPledgeSecuritiesAmount(double d) {
		inPledgeSecuritiesAmount = d;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string) {
		inputUserName = string;
	}

	/**
	 * @param d
	 */
	public void setOutPledgeSecuritiesAmount(double d) {
		outPledgeSecuritiesAmount = d;
	}

	/**
	 * @param d
	 */
	public void setPledgeSecuritiesAmount(double d) {
		pledgeSecuritiesAmount = d;
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l) {
		statusId = l;
	}

	/**
	 * @param l
	 */
	public void setTerm(long l) {
		term = l;
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

	/**
	 * @param l
	 */
	public void setTransactionTypeId(long l) {
		transactionTypeId = l;
	}

}

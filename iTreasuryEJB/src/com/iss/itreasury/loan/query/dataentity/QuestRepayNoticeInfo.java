package com.iss.itreasury.loan.query.dataentity;

import java.io.Serializable;

public class QuestRepayNoticeInfo extends Object implements Serializable {
	
	
	private long  contractID;		  //��ѯ����֪ͨ����ϸʱ�Ĳ���
	
	private String  contractCodeFrom;	//��ͬ�����ʼ
	private String  contractCodeTo;		//��ͬ�����ֹ
	
	private long  loanClientIDFrom;		//��λ��ʼ
	private long  loanClientIDTo;		//��λ��ֹ
	
	private long  consignIDFrom;		//ί�е�λ��ʼ
	private long  consignIDTo;		    //ί�е�λ��ֹ
	
	private double mRePayAmountFrom;		//��������ʼ
	private double mRePayAmountTo;		//��������ֹ
	
	private double DrawAmountInterestFrom;//�黹��Ϣ��ʼ
	private double DrawAmountInterestTo;  //�黹��Ϣ��ֹ
	
	private String dtInputDateFrom;	    //¼��������ʼ
	private String dtInputDateTo;	    //¼��������ֹ
	
	private String dtRepayDateFrom;	     //����������ʼ
	private String dtRepayDateTo;	     //����������ֹ
	
	private String nRePayNoticeStatus;    //����֪ͨ��״̬�б�
	
	private String nLoanType;            //���������б�
	
	private long   IsHead;
	
	private long nOfficeID;
	
	private long nCurrencyID;

	public String getContractCodeFrom() {
		return contractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return contractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}

	public long getLoanClientIDFrom() {
		return loanClientIDFrom;
	}

	public void setLoanClientIDFrom(long loanClientIDFrom) {
		this.loanClientIDFrom = loanClientIDFrom;
	}

	public long getLoanClientIDTo() {
		return loanClientIDTo;
	}

	public void setLoanClientIDTo(long loanClientIDTo) {
		this.loanClientIDTo = loanClientIDTo;
	}

	public long getConsignIDFrom() {
		return consignIDFrom;
	}

	public void setConsignIDFrom(long consignIDFrom) {
		this.consignIDFrom = consignIDFrom;
	}

	public long getConsignIDTo() {
		return consignIDTo;
	}

	public void setConsignIDTo(long consignIDTo) {
		this.consignIDTo = consignIDTo;
	}

	
	public double getMRePayAmountFrom() {
		return mRePayAmountFrom;
	}

	public void setMRePayAmountFrom(double rePayAmountFrom) {
		mRePayAmountFrom = rePayAmountFrom;
	}

	public double getMRePayAmountTo() {
		return mRePayAmountTo;
	}

	public void setMRePayAmountTo(double rePayAmountTo) {
		mRePayAmountTo = rePayAmountTo;
	}

	public double getDrawAmountInterestFrom() {
		return DrawAmountInterestFrom;
	}

	public void setDrawAmountInterestFrom(double drawAmountInterestFrom) {
		DrawAmountInterestFrom = drawAmountInterestFrom;
	}

	public double getDrawAmountInterestTo() {
		return DrawAmountInterestTo;
	}

	public void setDrawAmountInterestTo(double drawAmountInterestTo) {
		DrawAmountInterestTo = drawAmountInterestTo;
	}

	public String getDtInputDateFrom() {
		return dtInputDateFrom;
	}

	public void setDtInputDateFrom(String dtInputDateFrom) {
		this.dtInputDateFrom = dtInputDateFrom;
	}

	public String getDtInputDateTo() {
		return dtInputDateTo;
	}

	public void setDtInputDateTo(String dtInputDateTo) {
		this.dtInputDateTo = dtInputDateTo;
	}

	public String getDtRepayDateFrom() {
		return dtRepayDateFrom;
	}

	public void setDtRepayDateFrom(String dtRepayDateFrom) {
		this.dtRepayDateFrom = dtRepayDateFrom;
	}

	public String getDtRepayDateTo() {
		return dtRepayDateTo;
	}

	public void setDtRepayDateTo(String dtRepayDateTo) {
		this.dtRepayDateTo = dtRepayDateTo;
	}

	public String getNRePayNoticeStatus() {
		return nRePayNoticeStatus;
	}

	public void setNRePayNoticeStatus(String rePayNoticeStatus) {
		nRePayNoticeStatus = rePayNoticeStatus;
	}

	public String getNLoanType() {
		return nLoanType;
	}

	public void setNLoanType(String loanType) {
		nLoanType = loanType;
	}

	public long getIsHead() {
		return IsHead;
	}

	public void setIsHead(long isHead) {
		IsHead = isHead;
	}

	public long getNOfficeID() {
		return nOfficeID;
	}

	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}

	public long getNCurrencyID() {
		return nCurrencyID;
	}

	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}
	

}

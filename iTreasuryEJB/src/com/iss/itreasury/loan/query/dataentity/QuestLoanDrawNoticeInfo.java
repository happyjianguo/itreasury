package com.iss.itreasury.loan.query.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuestLoanDrawNoticeInfo extends Object implements Serializable {
	
	
	private String  contractCodeFrom;	//���ź�ͬ�����ʼ
	private String  contractCodeTo;		//���ź�ͬ�����ֹ
	
	private long  loanClientIDFrom;		//���Ž�λ��ʼ
	private long  loanClientIDTo;		//���Ž�λ��ֹ
	
	private double mDrawAmountFrom;		//�����������ʼ
	private double mDrawAmountTo;		//�����������ֹ
	
	private String dtInputDateFrom;	    //����¼��������ʼ
	private String dtInputDateTo;	    //����¼��������ֹ
	

	private String nDrawNoticeStatus;   //�������֪ͨ��״̬�б�
	
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

	
	public double getMDrawAmountFrom() {
		return mDrawAmountFrom;
	}

	public void setMDrawAmountFrom(double drawAmountFrom) {
		mDrawAmountFrom = drawAmountFrom;
	}

	public double getMDrawAmountTo() {
		return mDrawAmountTo;
	}

	public void setMDrawAmountTo(double drawAmountTo) {
		mDrawAmountTo = drawAmountTo;
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

	public String getNDrawNoticeStatus() {
		return nDrawNoticeStatus;
	}

	public void setNDrawNoticeStatus(String drawNoticeStatus) {
		nDrawNoticeStatus = drawNoticeStatus;
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
	

}

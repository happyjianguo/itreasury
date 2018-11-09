package com.iss.itreasury.loan.loancontract_test.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class LoanContractFormInfo extends ITreasuryBaseDataEntity {
	private long id = -1;                  //����ID
	private long officeID = -1 ;          //���´�
	private long currencyID = -1;         //����
	private String contractCode = "";     //�����ͬ��
	private long contractType = -1 ;     //�����ͬ����
	private String clientName = "";      //��λ
	private long inputuserID = -1;      //¼����
	private Timestamp inputdate = null;//¼������
	private long statusID = -1;        //״̬
	private double contractAmount = 0.0;//��ͬ���
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
		putUsedField ( "contractAmount" , contractAmount );
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
		putUsedField ( "clientName" , clientName );
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
		putUsedField ( "contractCode" , contractCode );
	}
	public long getContractType() {
		return contractType;
	}
	public void setContractType(long contractType) {
		this.contractType = contractType;
		putUsedField ( "contractType" , contractType );
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField ( "currencyID" , currencyID );
	}
	public Timestamp getInputdate() {
		return inputdate;
	}
	public void setInputdate(Timestamp inputdate) {
		this.inputdate = inputdate;
		putUsedField ( "inputdate" , inputdate );
	}
	public long getInputuserID() {
		return inputuserID;
	}
	public void setInputuserID(long inputuserID) {
		this.inputuserID = inputuserID;
		putUsedField ( "inputuserID" , inputuserID );
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField ( "officeID" , officeID );
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField ( "statusID" , statusID );
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id" , id);
	}
	

}
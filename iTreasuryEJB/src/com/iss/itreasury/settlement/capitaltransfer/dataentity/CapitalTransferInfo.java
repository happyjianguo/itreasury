package com.iss.itreasury.settlement.capitaltransfer.dataentity;

import java.sql.Timestamp;

public class CapitalTransferInfo {

	private long ID = -1;
	private long lOfficeID = -1;//���´�ID
	private long lCurrencyID = -1;//����
	private long ApplyNo = -1;//������
	private long PayBankID = -1;//���������ID
	private long ReceiveBankID = -1;//�տ������
	private double Amount = 0.0;//������
	private Timestamp DtInput = null;//¼��ʱ��
	private long InputUserID = -1;//¼����
	private Timestamp DtRemit = null;//�������
	private Timestamp DtReceive = null;//��������
	private Timestamp DtLimitStart = null;//��ʼ����
	private Timestamp DtLimitEnd = null;//��������
	private String Abstract = "";//��ע
	private long StatusID = -1;//״̬ID
	private double InrerestRate = 0.0;//����
	private double Interest = 0.0;//��Ϣ
	private double Commission = 0.0;//������
	private String Purpose = "";//��;
	
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public long getApplyNo() {
		return ApplyNo;
	}
	public void setApplyNo(long applyNo) {
		ApplyNo = applyNo;
	}
	public double getCommission() {
		return Commission;
	}
	public void setCommission(double commission) {
		Commission = commission;
	}
	public Timestamp getDtInput() {
		return DtInput;
	}
	public void setDtInput(Timestamp dtInput) {
		DtInput = dtInput;
	}
	public Timestamp getDtLimitEnd() {
		return DtLimitEnd;
	}
	public void setDtLimitEnd(Timestamp dtLimitEnd) {
		DtLimitEnd = dtLimitEnd;
	}
	public Timestamp getDtLimitStart() {
		return DtLimitStart;
	}
	public void setDtLimitStart(Timestamp dtLimitStart) {
		DtLimitStart = dtLimitStart;
	}
	public Timestamp getDtReceive() {
		return DtReceive;
	}
	public void setDtReceive(Timestamp dtReceive) {
		DtReceive = dtReceive;
	}
	public Timestamp getDtRemit() {
		return DtRemit;
	}
	public void setDtRemit(Timestamp dtRemit) {
		DtRemit = dtRemit;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public long getInputUserID() {
		return InputUserID;
	}
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}
	public double getInrerestRate() {
		return InrerestRate;
	}
	public void setInrerestRate(double inrerestRate) {
		InrerestRate = inrerestRate;
	}
	public double getInterest() {
		return Interest;
	}
	public void setInterest(double interest) {
		Interest = interest;
	}
	public long getLCurrencyID() {
		return lCurrencyID;
	}
	public void setLCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}
	public long getLOfficeID() {
		return lOfficeID;
	}
	public void setLOfficeID(long officeID) {
		lOfficeID = officeID;
	}
	public long getPayBankID() {
		return PayBankID;
	}
	public void setPayBankID(long payBankID) {
		PayBankID = payBankID;
	}
	public String getPurpose() {
		return Purpose;
	}
	public void setPurpose(String purpose) {
		Purpose = purpose;
	}
	public long getReceiveBankID() {
		return ReceiveBankID;
	}
	public void setReceiveBankID(long receiveBankID) {
		ReceiveBankID = receiveBankID;
	}
	public long getStatusID() {
		return StatusID;
	}
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	
	
}

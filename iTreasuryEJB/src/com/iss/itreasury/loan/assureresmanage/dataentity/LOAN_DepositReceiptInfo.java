package com.iss.itreasury.loan.assureresmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LOAN_DepositReceiptInfo implements Serializable {

	private long      ID	= -1;		
	private String    warrantCode = "";//Ȩ֤���
	private String    warrantName = "";//Ȩ֤����
	private long      warrantType = -1;//Ȩ֤����
	private long      gageID      = -1;//����Ʒ��ϢID
	private String    depositReceiptNumber = "";//���ڴ浥��
	private String    bankName = "";//�����������
	private String    regularAccountName = "";//�����˻�����
	private String    regularAccountNumber = "";//�����˺�
	private String    timeLimit = "";//����
	private double    rate = 0;//����
	private double    depositReceiptAmount = 0;//���
	private Timestamp startDate = null;//�����
	private Timestamp endDate = null;//������
	private long      status = -1;//״̬
	private long      inputUserID = -1;//¼����
	private Timestamp inputDate = null;//¼������
	private long      officeID = -1;//���´�
	private long      currencyID = -1;//����
	private String    lendPerson = "";//����/������
	private Timestamp lendDate = null;//����/����ʱ��
	private Timestamp anticipatedDate = null;//����/����Ԥ�ƹ黹ʱ��
	private String    lendCause = "";//����/����ԭ��
	private Timestamp realityDate = null;//ʵ�ʹ黹ʱ��
	public Timestamp getAnticipatedDate() {
		return anticipatedDate;
	}
	public void setAnticipatedDate(Timestamp anticipatedDate) {
		this.anticipatedDate = anticipatedDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public long getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	public double getDepositReceiptAmount() {
		return depositReceiptAmount;
	}
	public void setDepositReceiptAmount(double depositReceiptAmount) {
		this.depositReceiptAmount = depositReceiptAmount;
	}
	public String getDepositReceiptNumber() {
		return depositReceiptNumber;
	}
	public void setDepositReceiptNumber(String depositReceiptNumber) {
		this.depositReceiptNumber = depositReceiptNumber;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public long getGageID() {
		return gageID;
	}
	public void setGageID(long gageID) {
		this.gageID = gageID;
	}
	public long getID() {
		return ID;
	}
	public void setID(long id) {
		ID = id;
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}
	public long getInputUserID() {
		return inputUserID;
	}
	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public String getLendCause() {
		return lendCause;
	}
	public void setLendCause(String lendCause) {
		this.lendCause = lendCause;
	}
	public Timestamp getLendDate() {
		return lendDate;
	}
	public void setLendDate(Timestamp lendDate) {
		this.lendDate = lendDate;
	}
	public String getLendPerson() {
		return lendPerson;
	}
	public void setLendPerson(String lendPerson) {
		this.lendPerson = lendPerson;
	}
	public long getOfficeID() {
		return officeID;
	}
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Timestamp getRealityDate() {
		return realityDate;
	}
	public void setRealityDate(Timestamp realityDate) {
		this.realityDate = realityDate;
	}
	public String getRegularAccountName() {
		return regularAccountName;
	}
	public void setRegularAccountName(String regularAccountName) {
		this.regularAccountName = regularAccountName;
	}
	public String getRegularAccountNumber() {
		return regularAccountNumber;
	}
	public void setRegularAccountNumber(String regularAccountNumber) {
		this.regularAccountNumber = regularAccountNumber;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getWarrantCode() {
		return warrantCode;
	}
	public void setWarrantCode(String warrantCode) {
		this.warrantCode = warrantCode;
	}
	public String getWarrantName() {
		return warrantName;
	}
	public void setWarrantName(String warrantName) {
		this.warrantName = warrantName;
	}
	public long getWarrantType() {
		return warrantType;
	}
	public void setWarrantType(long warrantType) {
		this.warrantType = warrantType;
	}
}

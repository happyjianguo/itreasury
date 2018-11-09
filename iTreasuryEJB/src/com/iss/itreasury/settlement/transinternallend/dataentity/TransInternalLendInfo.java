package com.iss.itreasury.settlement.transinternallend.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TransInternalLendInfo implements Serializable{
	
	private long Id=-1;
	private long OfficeID=-1; //����ID
	private long CurrencyID=-1;//����
	private String TransNO="";//���׺�
	private long TransActionTypeID=-1;//��������
	private double Amount=0.0;//���׽��
	private long LendingAccountID=-1;//����˻�ID
	private double LendingAccountBalance=0.0;//����˻����
	private long BranchOfficeID=-1;//����˻�������֧������ID
	private long ReserveAccountID=-1;//�������˻�ID
	private double ReserveAccountBalance=0.0;//�������˻����
	private Timestamp InterestStart;//��Ϣ��
	private Timestamp Execute;		//ִ����
	private Timestamp Modify;//�޸�ʱ�䣺������ʱ����
	private Timestamp InputDate;//¼������
	private long InputUserID = -1;	//¼����
	private long CheckUserID = -1;	//������
	private String Abstract = "";	//ժҪ
	private long StatusID=-1;//����״̬
	private String CheckAbstract = "";	//ȡ������ժҪ
	private long OfficeClientID=-1;//�������Ϳͻ�id	
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public long getCurrencyID() {
		return CurrencyID;
	}
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	public String getTransNO() {
		return TransNO;
	}
	public void setTransNO(String transNO) {
		TransNO = transNO;
	}
	public long getTransActionTypeID() {
		return TransActionTypeID;
	}
	public void setTransActionTypeID(long transActionTypeID) {
		TransActionTypeID = transActionTypeID;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public long getLendingAccountID() {
		return LendingAccountID;
	}
	public void setLendingAccountID(long lendingAccountID) {
		LendingAccountID = lendingAccountID;
	}
	public double getLendingAccountBalance() {
		return LendingAccountBalance;
	}
	public void setLendingAccountBalance(double lendingAccountBalance) {
		LendingAccountBalance = lendingAccountBalance;
	}
	public long getBranchOfficeID() {
		return BranchOfficeID;
	}
	public void setBranchOfficeID(long branchOfficeID) {
		BranchOfficeID = branchOfficeID;
	}
	public long getReserveAccountID() {
		return ReserveAccountID;
	}
	public void setReserveAccountID(long reserveAccountID) {
		ReserveAccountID = reserveAccountID;
	}
	public double getReserveAccountBalance() {
		return ReserveAccountBalance;
	}
	public void setReserveAccountBalance(double reserveAccountBalance) {
		ReserveAccountBalance = reserveAccountBalance;
	}
	public Timestamp getInterestStart() {
		return InterestStart;
	}
	public void setInterestStart(Timestamp interestStart) {
		InterestStart = interestStart;
	}
	public Timestamp getExecute() {
		return Execute;
	}
	public void setExecute(Timestamp execute) {
		Execute = execute;
	}
	public Timestamp getModify() {
		return Modify;
	}
	public void setModify(Timestamp modify) {
		Modify = modify;
	}
	public Timestamp getInputDate() {
		return InputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}
	public long getInputUserID() {
		return InputUserID;
	}
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}
	public long getCheckUserID() {
		return CheckUserID;
	}
	public void setCheckUserID(long checkUserID) {
		CheckUserID = checkUserID;
	}
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public long getStatusID() {
		return StatusID;
	}
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	public String getCheckAbstract() {
		return CheckAbstract;
	}
	public void setCheckAbstract(String checkAbstract) {
		CheckAbstract = checkAbstract;
	}
	public long getOfficeClientID() {
		return OfficeClientID;
	}
	public void setOfficeClientID(long officeClientID) {
		OfficeClientID = officeClientID;
	}

	
	
	

}

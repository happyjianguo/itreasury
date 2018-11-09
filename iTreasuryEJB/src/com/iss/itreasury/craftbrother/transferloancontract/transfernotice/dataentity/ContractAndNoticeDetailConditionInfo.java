package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author zcwang
 * @version 1.1
 */
public class ContractAndNoticeDetailConditionInfo extends ITreasuryBaseDataEntity{
	 private long officeID = -1;    // ���´�
	 private long currencyID = -1;  //����
	 private long transferNoticeFormId = -1;  // �տ�֪ͨ������ID
	 private long  transferContractFormID = -1; //ת�ú�ͬ����ID
	 private long  loanContractId = -1;   	   //�����ͬID 
	 private long  loanNoteId = -1;  		  //����ſ�֪ͨ��ID
	 private Timestamp clearInterestDate = null; //��Ϣ����
	 private long  statusID = -1;    			//״̬
	 private long  transTypeID = -1;            //ҵ������
	 
	 private Timestamp dtstartDate = null ;//�ſʼ����
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}

	public long getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(long loanContractId) {
		this.loanContractId = loanContractId;
	}

	public long getLoanNoteId() {
		return loanNoteId;
	}

	public void setLoanNoteId(long loanNoteId) {
		this.loanNoteId = loanNoteId;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}

	public long getTransferContractFormID() {
		return transferContractFormID;
	}

	public void setTransferContractFormID(long transferContractFormID) {
		this.transferContractFormID = transferContractFormID;
	}

	public long getTransferNoticeFormId() {
		return transferNoticeFormId;
	}

	public void setTransferNoticeFormId(long transferNoticeFormId) {
		this.transferNoticeFormId = transferNoticeFormId;
	}

	public Timestamp getClearInterestDate() {
		return clearInterestDate;
	}

	public void setClearInterestDate(Timestamp clearInterestDate) {
		this.clearInterestDate = clearInterestDate;
	}

	public long getTransTypeID() {
		return transTypeID;
	}

	public void setTransTypeID(long transTypeID) {
		this.transTypeID = transTypeID;
	}

	public Timestamp getDtstartDate() {
		return dtstartDate;
	}

	public void setDtstartDate(Timestamp dtstartDate) {
		this.dtstartDate = dtstartDate;
	}


}

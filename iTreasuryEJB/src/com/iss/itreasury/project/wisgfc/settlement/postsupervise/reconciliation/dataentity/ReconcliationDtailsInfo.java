package com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class ReconcliationDtailsInfo extends ITreasuryBaseDataEntity {
	private long id = -1;             //����
	private long isChecked =-1;      //�Ƿ񱻹�ѡ
	private String accountNO = "";    //�˻���
	private String accountName ="";   //�˻���
	private long accountType =-1;     //�˻�����
	private long transCount =-1;       //���״���
	private double startBalance =0.0;  //�ڳ����
	private double payAmount =0.0;     //������
	private double recAmount=0.0;     //�տ���
	private double endBalance=0.0;    //��ĩ���
	private long reconciliationID=-1;//���ڱ����ID
	private long accountID =-1;       //�˻�ID
	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
		putUsedField("accountID", accountID);
	}

	public long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		this.id = id;
		putUsedField("id", id);
	}

	public long getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(long isChecked) {
		this.isChecked = isChecked;
		putUsedField("isChecked", isChecked);
	}

	public String getAccountNO() {
		return accountNO;
	}

	public void setAccountNO(String accountNO) {
		this.accountNO = accountNO;
		putUsedField("accountNO", accountNO);
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
		putUsedField("accountName", accountName);
	}

	public long getAccountType() {
		return accountType;
	}

	public void setAccountType(long accountType) {
		this.accountType = accountType;
		putUsedField("accountType", accountType);
	}


	public long getTransCount() {
		return transCount;
	}

	public void setTransCount(long transCount) {
		this.transCount = transCount;
		putUsedField("transCount", transCount);
	}

	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
		putUsedField("startBalance", startBalance);
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
		putUsedField("payAmount", payAmount);
	}

	public double getRecAmount() {
		return recAmount;
	}

	public void setRecAmount(double recAmount) {
		this.recAmount = recAmount;
		putUsedField("recAmount", recAmount);
	}

	public double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
		putUsedField("endBalance", endBalance);
	}

	public long getReconciliationID() {
		return reconciliationID;
	}

	public void setReconciliationID(long reconciliationID) {
		this.reconciliationID = reconciliationID;
		putUsedField("reconciliationID", reconciliationID);
	}

}

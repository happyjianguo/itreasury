package com.iss.itreasury.loan.creditext.dataentity;

import java.sql.Date;
import java.util.HashMap;

/**
* �������ŷ����������Ϣ
* @author mayongming
* @version 1.0
*/
public class BankCreditExtAllotInfo {
	private long id;				//id
	private String contractNo;		//���ź�ͬ��
	private String bankName;		//��������
	private String year;			//�������
	private String startDate;		//��ʼ����
	private String endDate;			//��������
	private String officeCode;		//�������Ĵ���
	private long officeid ;         //��������id
	private String officeName;		//������������
	private long variety;			//����Ʒ��
	private double balance;			//�������
	private long currencyType;		//����
	private double amount;			//���
	private long isValid;			//���߼�ɾ���ı�־λ
	private long lastModifier;	    //����޸���id
	private String lastModifyDate;	//����޸����ڣ�
	private String remark;			//��ע
	private HashMap balances = new HashMap(); //����Ʒ�ֵ�����
	
	public HashMap getBalances(){
		return this.balances;
	}
	public void setBalances(HashMap balances){
		this.balances = balances;
	}
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getOfficeCode() {
		return this.officeCode;
	}
	
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	public String getOfficeName() {
		return this.officeName;
	}
	
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	public long getVariety() {
		return this.variety;
	}
	
	public void setVariety(long variety) {
		this.variety = variety;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public long getCurrencyType() {
		return this.currencyType;
	}
	
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIsValid() {
		return isValid;
	}

	public void setIsValid(long isValid) {
		this.isValid = isValid;
	}

	public long getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(long lastModifier) {
		this.lastModifier = lastModifier;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}
}
/*
 * Created on 2004-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.dataentity;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DestinationReceivePayInfo {
	
	//���з�֧��������
	private String bank_code="00000";
	//ҵ��ο��ţ�����Ψһ��
	private String ref_number ="";
	//��������
	private Timestamp deal_date = null;
	//������������
	private String op_bank = "";
	//����������
	private String op_name = "";
	//�������˻�
	private String op_account = "";
	//�տ�������
	private String en_name = "";
	//�տ��˴���
	private String en_code = "";
	//�տ����˻�
	private String account = "";
	//���ִ���
	private String Currence_code = "";
	//���
	private double Amount = 0.0;
	//���㷽ʽ
	private String Paymethod = "";
	//���ױ���
	private String Transact_code = "";
	//���������
	private String Authority_code = "";
	//�걨����
	private String Ap_number = "";
	//�����˹���
	private String Country_code = "";
	//¼���ʶ��1-��¼�룬0-δ¼��
	private String Successed = "";
	//¼���Ա
	private String Tlrno = "";

	/**
	 * @return Returns the account.
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account The account to set.
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return Amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		Amount = amount;
	}
	/**
	 * @return Returns the ap_number.
	 */
	public String getAp_number() {
		return Ap_number;
	}
	/**
	 * @param ap_number The ap_number to set.
	 */
	public void setAp_number(String ap_number) {
		Ap_number = ap_number;
	}
	/**
	 * @return Returns the authority_code.
	 */
	public String getAuthority_code() {
		return Authority_code;
	}
	/**
	 * @param authority_code The authority_code to set.
	 */
	public void setAuthority_code(String authority_code) {
		Authority_code = authority_code;
	}
	/**
	 * @return Returns the bank_code.
	 */
	public String getBank_code() {
		return bank_code;
	}
	/**
	 * @param bank_code The bank_code to set.
	 */
	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}
	/**
	 * @return Returns the country_code.
	 */
	public String getCountry_code() {
		return Country_code;
	}
	/**
	 * @param country_code The country_code to set.
	 */
	public void setCountry_code(String country_code) {
		Country_code = country_code;
	}
	/**
	 * @return Returns the currence_code.
	 */
	public String getCurrence_code() {
		return Currence_code;
	}
	/**
	 * @param currence_code The currence_code to set.
	 */
	public void setCurrence_code(String currence_code) {
		Currence_code = currence_code;
	}
	/**
	 * @return Returns the deal_date.
	 */
	public Timestamp getDeal_date() {
		return deal_date;
	}
	/**
	 * @param deal_date The deal_date to set.
	 */
	public void setDeal_date(Timestamp deal_date) {
		this.deal_date = deal_date;
	}
	/**
	 * @return Returns the en_code.
	 */
	public String getEn_code() {
		return en_code;
	}
	/**
	 * @param en_code The en_code to set.
	 */
	public void setEn_code(String en_code) {
		this.en_code = en_code;
	}
	/**
	 * @return Returns the en_name.
	 */
	public String getEn_name() {
		return en_name;
	}
	/**
	 * @param en_name The en_name to set.
	 */
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	/**
	 * @return Returns the op_account.
	 */
	public String getOp_account() {
		return op_account;
	}
	/**
	 * @param op_account The op_account to set.
	 */
	public void setOp_account(String op_account) {
		this.op_account = op_account;
	}
	/**
	 * @return Returns the op_bank.
	 */
	public String getOp_bank() {
		return op_bank;
	}
	/**
	 * @param op_bank The op_bank to set.
	 */
	public void setOp_bank(String op_bank) {
		this.op_bank = op_bank;
	}
	/**
	 * @return Returns the op_name.
	 */
	public String getOp_name() {
		return op_name;
	}
	/**
	 * @param op_name The op_name to set.
	 */
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	/**
	 * @return Returns the paymethod.
	 */
	public String getPaymethod() {
		return Paymethod;
	}
	/**
	 * @param paymethod The paymethod to set.
	 */
	public void setPaymethod(String paymethod) {
		Paymethod = paymethod;
	}
	/**
	 * @return Returns the ref_number.
	 */
	public String getRef_number() {
		return ref_number;
	}
	/**
	 * @param ref_number The ref_number to set.
	 */
	public void setRef_number(String ref_number) {
		this.ref_number = ref_number;
	}
	/**
	 * @return Returns the successed.
	 */
	public String getSuccessed() {
		return Successed;
	}
	/**
	 * @param successed The successed to set.
	 */
	public void setSuccessed(String successed) {
		Successed = successed;
	}
	/**
	 * @return Returns the tlrno.
	 */
	public String getTlrno() {
		return Tlrno;
	}
	/**
	 * @param tlrno The tlrno to set.
	 */
	public void setTlrno(String tlrno) {
		Tlrno = tlrno;
	}
	/**
	 * @return Returns the transact_code.
	 */
	public String getTransact_code() {
		return Transact_code;
	}
	/**
	 * @param transact_code The transact_code to set.
	 */
	public void setTransact_code(String transact_code) {
		Transact_code = transact_code;
	}
}

/*
 * Created on 2003-10-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.transloan.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RepaymentConsignLoanConditionInfo implements Serializable {
	private TransRepaymentLoanInfo info = null;
	/**
	 * Ĭ�Ϲ�����
	 *
	 */
	public RepaymentConsignLoanConditionInfo() {
		this.info = new TransRepaymentLoanInfo();
	}
	/**
	 * ������
	 * @param info
	 */
	public RepaymentConsignLoanConditionInfo(TransRepaymentLoanInfo info) {
		this.info = info;
	}
	/**
	 * �����Ŵ�������Ϣ
	 * @return
	 */
	public TransRepaymentLoanInfo getTransRepaymentLoanInfo() {
		return this.info;
	}
	//	----------------------------Ĭ�ϲ�ѯ����  1�����´� 2������ 3��¼���� 4��

	/**����
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return this.info.getCurrencyID();
	}

	/**����
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.info.setCurrencyID(currencyID);
	}

	/**���´�ID
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return this.info.getOfficeID();
	}

	/**���´�ID
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.info.setOfficeID(officeID);
	}
	/**������
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return this.info.getInputUserID();
	}

	/**������
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		this.info.setInputUserID(inputUserID);
	}

	/**ִ����
	 * @return Returns the execute.
	 */
	public Timestamp getExecute() {
		return this.info.getExecute();
	}

	/**ִ����
	 * @param execute The execute to set.
	 */
	public void setExecute(Timestamp execute) {
		this.info.setExecute(execute);
	}

	/**
		 * ״̬
		 * @param statusID
		 */
	public void setStatusID(long statusID) {
		this.info.setStatusID(statusID);
	}
	/**
	 * ״̬
	 * @param statusID
	 */
	public long getStatusID() {
		return this.info.getStatusID();
	}

	//	----------------------------Ĭ�ϲ�ѯ����

	/**
	 * @return
	 */
	public long getClientID() //����ͻ����
	{
		return info.getClientID();
	}

	/**
	 * @return
	 */
	public long getBankID() //�տ�����
	{
		return info.getBankID();
	}

	/**
	 * @return
	 */
	public long getCommissionAccountID() //���������˻���
	{
		return info.getCommissionAccountID();
	}

	/**
	 * @return
	 */
	public long getCommissionBankID() //������������
	{
		return info.getCommissionBankID();
	}

	/**
	 * @return
	 */
	public long getConsignAccountID() //ί�д���˻���
	{
		return info.getConsignAccountID();
	}

	/**
	 * @return
	 */
	public long getConsignDepositAccountID() //ί�з����ڴ���˻���
	{
		return info.getConsignDepositAccountID();
	}

	/**
	 * @return
	 */
	public long getPreFormID() //��ǰ����֪ͨ��
	{
		return info.getPreFormID();
	}

	/**
	 * @return
	 */
	public long getDepositAccountID() //��������˻���
	{
		return info.getDepositAccountID();
	}

	/**
	 * @return
	 */
	public long getFreeFormID() //�⻹֪ͨ��
	{
		return info.getFreeFormID();
	}

	/**
	 * @return
	 */
	public long getInterestBankID() //��Ϣ����
	{
		return info.getInterestBankID();
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStart() //��Ϣ��
	{
		return info.getInterestStart();
	}

	/**
	 * @return
	 */
	public long getIsRemitCommission() //�Ƿ��⻹������
	{
		return info.getIsRemitCommission();
	}

	/**
	 * @return
	 */
	public long getIsRemitCompoundInterest() //�Ƿ��⻹����
	{
		return info.getIsRemitCompoundInterest();
	}

	/**
	 * @return
	 */
	public long getIsRemitInterest() //�Ƿ��⻹��Ϣ
	{
		return info.getIsRemitInterest();
	}

	/**
	 * @return
	 */
	public long getIsRemitOverDueInterest() //�Ƿ��⻹���ڷ�Ϣ
	{
		return info.getIsRemitOverDueInterest();
	}

	/**
	 * @return
	 */
	public long getLoanAccountID() //ί�д����˻���
	{
		return info.getLoanAccountID();
	}

	/**
	 * @return
	 */
	public long getPayInterestAccountID() //����Ϣ�˻���
	{
		return info.getPayInterestAccountID();
	}

	/**
	 * @return
	 */
	public double getRealCommission() //ʵ��֧��������
	{
		return info.getRealCommission();
	}

	/**
	 * @return
	 */
	public double getRealCompoundInterest() //ʵ��֧������
	{
		return info.getRealCompoundInterest();
	}

	/**
	 * @return
	 */
	public double getRealInterest() //ʵ��֧����Ϣ
	{
		return info.getRealInterest();
	}

	/**
	 * @return
	 */
	public double getRealOverDueInterest() //ʵ��֧�����ڷ�Ϣ
	{
		return info.getRealOverDueInterest();
	}

	/**
	 * @return
	 */
	public long getReceiveInterestAccountID() //��Ϣ�����˻���
	{
		return info.getReceiveInterestAccountID();
	}

	/**
	 * @return
	 */
	public double getAmount() //���
	{
		return info.getAmount();
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		info.setAmount(d);
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		info.setClientID(l);
	}

	/**�����ͬID
	 * @return Returns the loanContractID.
	 */
	public long getLoanContractID() {
		return this.info.getLoanContractID();
	}

	/**�����ͬID
	 * @param loanContractID The loanContractID to set.
	 */
	public void setLoanContractID(long loanContractID) {
		this.info.setLoanContractID(loanContractID);
	}
	
	/**�ſ�֪ͨ��
	 * @return Returns the loanNoteID.
	 */
	public long getLoanNoteID() {
		return this.info.getLoanNoteID();
	}

	/**�ſ�֪ͨ��
	 * @param loanNoteID The loanNoteID to set.
	 */
	public void setLoanNoteID(long loanNoteID) {
		this.info.setLoanNoteID(loanNoteID);
	}
	
	/**
	 * @param l
	 */
	public void setCommissionAccountID(long l) {
		info.setCommissionAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setCommissionBankID(long l) {
		info.setCommissionBankID(l);
	}

	/**
	 * @param l
	 */
	public void setConsignAccountID(long l) {
		info.setConsignAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setConsignDepositAccountID(long l) {
		info.setConsignDepositAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setPreFormID(long l) {
		info.setPreFormID(l);
	}

	/**
	 * @param l
	 */
	public void setDepositAccountID(long l) {
		info.setDepositAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setFreeFormID(long l) {
		info.setFreeFormID(l);
	}

	/**
	 * @param l
	 */
	public void setInterestBankID(long l) {
		info.setInterestBankID(l);
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStart(Timestamp timestamp) {
		info.setInterestStart(timestamp);
	}

	/**
	 * @param l
	 */
	public void setIsRemitCommission(long l) {
		info.setIsRemitCommission(l);
	}

	/**
	 * @param l
	 */
	public void setIsRemitCompoundInterest(long l) {
		info.setIsRemitCompoundInterest(l);
	}

	/**
	 * @param l
	 */
	public void setIsRemitInterest(long l) {
		info.setIsRemitInterest(l);
	}

	/**
	 * @param l
	 */
	public void setIsRemitOverDueInterest(long l) {
		info.setIsRemitOverDueInterest(l);
	}

	/**
	 * @param l
	 */
	public void setLoanAccountID(long l) {
		info.setLoanAccountID(l);
	}



	/**
	 * @param l
	 */
	public void setPayInterestAccountID(long l) {
		info.setPayInterestAccountID(l);
	}

	/**
	 * @param d
	 */
	public void setRealCommission(double d) {
		info.setRealCommission(d);
	}

	/**
	 * @param d
	 */
	public void setRealCompoundInterest(double d) {
		info.setRealCompoundInterest(d);
	}

	/**
	 * @param d
	 */
	public void setRealInterest(double d) {
		info.setRealInterest(d);
	}

	/**
	 * @param d
	 */
	public void setRealOverDueInterest(double d) {
		info.setRealOverDueInterest(d);
	}

	/**
	 * @param l
	 */
	public void setReceiveInterestAccountID(long l) {
		info.setReceiveInterestAccountID(l);
	}

	/**
	 * @param l
	 */
	public void setBankID(long l) {
		info.setBankID(l);
	}

	/**
		 * ����/��Ϣ����ʽ
		 * @return
		 */
	public long getCapitalAndInterestDealWay() {
		return info.getCapitalAndInterstDealway();
	}

	/**
	 * ����/��Ϣ����ʽ
	 * @param capitalAndInterestDealWay
	 */
	public void setCapitalAndInterestDealWay(long capitalAndInterestDealWay) {
		info.setCapitalAndInterstDealway(capitalAndInterestDealWay);
	}
	/**
	 * ���׷���
	 * @param lMultiLoanType
	 */
	public void setTransDirectionID(long lMultiLoanType){
		this.info.setTransDirectionID(lMultiLoanType);
	}
	/**
	 * ���׷���
	 * @return
	 */
	public long getTransDirectionID(){
		return this.info.getTransDirectionID();
	}
	/**
	 * ������
	 * @param strDeclarationNo
	 */
	public void setDeclarationNo(String strDeclarationNo){
		this.info.setDeclarationNo(strDeclarationNo);
	}
	/**
	 * ������
	 * @return
	 */
	public String getDeclarationNo(){
		return this.info.getDeclarationNo();
	}
}

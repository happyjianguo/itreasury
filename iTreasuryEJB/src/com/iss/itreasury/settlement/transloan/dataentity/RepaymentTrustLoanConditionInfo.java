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
public class RepaymentTrustLoanConditionInfo implements Serializable
{
	private TransRepaymentLoanInfo info=null;
	/**
	 * Ĭ�Ϲ�����
	 *
	 */
	public RepaymentTrustLoanConditionInfo(){
		this.info = new TransRepaymentLoanInfo();
	}
	/**
	 * ������
	 * @param info
	 */
	public RepaymentTrustLoanConditionInfo(TransRepaymentLoanInfo info){
		this.info=info;
	}
	/**
	 * �����Ŵ�������Ϣ
	 * @return
	 */
	public TransRepaymentLoanInfo getTransRepaymentLoanInfo(){
		return this.info;
	}
//----------------------------Ĭ�ϲ�ѯ����  1�����´� 2������ 3��¼���� 4��

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

//----------------------------Ĭ�ϲ�ѯ����
	
	
  	/**
  	 * ����ͻ����
  	 */
  	public void setClientID(long lClientID){
  		info.setClientID(lClientID);
  	}
  
	/**����ͻ����
	 * @return
	 */
	public long getClientID() 
	{
		return info.getClientID();
	}
	/**������
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return this.info.getAmount();
	}

	/**������
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.info.setAmount(amount);
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

	/**�տ�����ID
	 * @return Returns the bankID.
	 */
	public long getBankID() {
		return this.info.getBankID();
	}

	/**�տ�����ID
	 * @param bankID The bankID to set.
	 */
	public void setBankID(long bankID) {
		this.info.setBankID(bankID);
	}

	/**�����˻���
	 * @return Returns the depositAccountID.
	 */
	public long getDepositAccountID() {
		return this.info.getDepositAccountID();
	}

	/**�����˻���
	 * @param depositAccountID The depositAccountID to set.
	 */
	public void setDepositAccountID(long depositAccountID) {
		this.info.setDepositAccountID(depositAccountID);
	}


	/**	��Ϣ��
	 * @return Returns the interestStart.
	 */
	public Timestamp getInterestStart() {
		return this.info.getInterestStart();
	}

	/**��Ϣ��
	 * @param interestStart The interestStart to set.
	 */
	public void setInterestStart(Timestamp interestStart) {
		this.info.setInterestStart(interestStart);
	}

	/**�����˻�
	 * @return Returns the loanAccountID.
	 */
	public long getLoanAccountID() {
		return this.info.getLoanAccountID();
	}

	/**�����˻�
	 * @param loanAccountID The loanAccountID to set.
	 */
	public void setLoanAccountID(long loanAccountID) {
		this.info.setLoanAccountID(loanAccountID);
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


	/**��Ϣ�˻���
	 * @return Returns the payInterestAccountID.
	 */
	public long getPayInterestAccountID() {
		return this.info.getPayInterestAccountID();
	}

	/**��Ϣ�˻���
	 * @param payInterestAccountID The payInterestAccountID to set.
	 */
	public void setPayInterestAccountID(long payInterestAccountID) {
		this.info.setPayInterestAccountID(payInterestAccountID);
	}

	/**��ǰ����֪ͨ��
	 * @return Returns the preFormID.
	 */
	public long getPreFormID() {
		return this.info.getPreFormID();
	}

	/**��ǰ����֪ͨ��
	 * @param preFormID The preFormID to set.
	 */
	public void setPreFormID(long preFormID) {
		this.info.setPreFormID(preFormID);
	}
	
	/**
	 * ��Ϣ������
	 * @return
	 */
	public long getInterestBankID()
	{
		return info.getInterestBankID();
	}
	
	/**
	 * ��Ϣ������
	 * @return
	 */
	public void setInterestBankID(long interestBankDI)
	{
		info.setInterestBankID(interestBankDI);
	}
	
	/**
	 * �����շ��˺�
	 * @return
	 */
	public long getPaySuertyFeeAccountID(){
		return info.getPaySuretyAccountID();
	}
	
	/**
	 * �����շ��˺�
	 * @return
	 */
	public void setPaySuertyFeeAccountID(long paySuertyFeeAccountID){
		info.setPaySuretyAccountID(paySuertyFeeAccountID);
	}
	
	/**
	 * �����շѿ�����
	 * @return
	 */
	public long getSuertyFeeBankID(){
		return info.getSuretyBankID();
	}
	
	/**
	 * �����շѿ�����
	 * @param suretyBankID
	 */
	public void setSuertyFeeBankID(long suretyBankID){
		info.setSuretyBankID(suretyBankID);
	}
	
	/**
	 * �����������˻���
	 * @return
	 */
	public long getReceiveSuertyFeeAccountID(){
		return info.getReceiveSuretyAccountID();
	}
	
	/**
	 * �������������˻���
	 * @param receiveSuretyAccountID
	 */
	public void setReceiveSuertyFeeAccountID(long receiveSuretyAccountID){
		info.setReceiveSuretyAccountID(receiveSuretyAccountID);
	}
	
	/**
	 * ��Ϣ
	 * @return
	 */
	public double getRealInterest(){
		return info.getRealInterest();
	}
	
	/**
	 * ��Ϣ
	 * @return
	 */
	public void setRealInterest(double realInterest){
		info.setRealInterest(realInterest);
	}
	
	/**
	 * �Ƿ��⻹ʣ����Ϣ
	 * @return
	 */
	public long getIsRemitInterest(){
		return info.getIsRemitInterest();
	}
	
	/**
	 * �Ƿ��⻹ʣ����Ϣ
	 * @param isRemitInterest
	 */
	public void setIsRemitInterest(long isRemitInterest){
		info.setIsRemitInterest(isRemitInterest);
	}
	
	/**
	 * �Ƿ��⻹ʣ�ิ��
	 * @return
	 */
	public long getIsRemitCompoundInterest(){
		return info.getIsRemitCompoundInterest();
	}
	
	/**
	 * �Ƿ��⻹ʣ�ิ��
	 * @param isRemitCompoundInterest
	 */
	public void setIsRemitCompoundInterest(long isRemitCompoundInterest){
		info.setIsRemitCompoundInterest(isRemitCompoundInterest);
	}
	
	/**
	 * �Ƿ��⻹ʣ�����ڷ�Ϣ
	 * @return
	 */
	public long getIsRemitOverDueInterest(){
		return info.getIsRemitOverDueInterest();
	}
	
	/**
	 * �Ƿ��⻹ʣ�����ڷ�Ϣ
	 * @param isRemitOverDueInterest
	 */
	public void setIsRemitOverDueInterest(long isRemitOverDueInterest){
		info.setIsRemitOverDueInterest(isRemitOverDueInterest);
	}
	
	/**
	 * �Ƿ��⻹ʣ�ൣ����
	 * @return
	 */
	public long getIsRemitSuretyFee(){
		return info.getIsRemitSuretyFee();
	}
	
	/**
	 * �Ƿ��⻹ʣ�ൣ����
	 * @param isRemitSuretyFee
	 */
	public void setIsRemitSuretyFee(long isRemitSuretyFee){
		info.setIsRemitSuretyFee(isRemitSuretyFee);
	}
	
	/**
	 * ����
	 * @return
	 */
	public double getRealCompoundInterest(){
		return info.getRealCompoundInterest();
	}
	
	/**
	 * ����
	 * @param compoundInterest
	 */
	public void setRealCompoundInterest(double realCompoundInterest){
		info.setRealCompoundInterest(realCompoundInterest);
	}
	
	/**
	 * ���ڷ�Ϣ
	 * @return
	 */
	public double getRealOverDueInterest(){
		return info.getRealOverDueInterest();
	}
	
	/**
	 * ���ڷ�Ϣ
	 * @param overDueInterest
	 */
	public void setRealOverDueInterest(double realOverDueInterest){
		info.setRealOverDueInterest(realOverDueInterest);
	}
	
	/**
	 * ������
	 * @return
	 */
	public double getRealSuretyFee(){
		return info.getRealSuretyFee();
	}
	
	/**
	 * ������
	 * @param suretyFee
	 */
	public void setRealSuretyFee(double realSuretyFee){
		info.setRealSuretyFee(realSuretyFee);
	}
	
	/**
	 * ����/��Ϣ����ʽ
	 * @return
	 */
	public long getCapitalAndInterestDealWay(){
		return info.getCapitalAndInterstDealway();
	}
	
	/**
	 * ����/��Ϣ����ʽ
	 * @param capitalAndInterestDealWay
	 */
	public void setCapitalAndInterestDealWay(long capitalAndInterestDealWay){
		info.setCapitalAndInterstDealway(capitalAndInterestDealWay);
	}
	
	/**
	 * ״̬
	 * @param statusID
	 */
	public void setStatusID(long statusID){
		this.info.setStatusID(statusID);
	}
	/**
	 * ״̬
	 * @param statusID
	 */
	public long getStatusID(){
		return this.info.getStatusID();
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

package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class QCapitalPaymentResultInfo extends ITreasuryBaseDataEntity implements Serializable
{
	long   clientId          = -1;   //�ͻ�ID
	String counterPartNo     = "";   //���׶���
	String strAbstract       = "";   //ժҪ
	double drawing           = 0.00; //��ҵ���
	double consignLoan       = 0.00; //ί�д����
	double consignPay        = 0.00; //ί�и���
	double trustLoan         = 0.00; //��Ӫ�����
	double discount          = 0.00; //���ַſ�
	double discountPay       = 0.00; //ת���ֳ���
	double loanRepurchase    = 0.00; //����ع�����
	double bankPay           = 0.00; //ͬҵ������
	double longInvest        = 0.00; //����Ͷ��
	double securitiesInvest  = 0.00; //֤ȯͶ��
	double currencyInvest    = 0.00; //����Ͷ��
	double depositPrepare    = 0.00; //���׼���𲹽�
	double businessPay       = 0.00; //ҵ��֧��
	double otherReceive      = 0.00; //����Ӧ�տ�
	double otherPay          = 0.00; //��������
	double totalAmount       = 0.00; //�ϼ�
	
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId()
	{
		return clientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId)
	{
		this.clientId = clientId;
		putUsedField("clientId", clientId);
	}
	/**
	 * @return Returns the consignLoan.
	 */
	public double getConsignLoan()
	{
		return consignLoan;
	}
	/**
	 * @param consignLoan The consignLoan to set.
	 */
	public void setConsignLoan(double consignLoan)
	{
		this.consignLoan = consignLoan;
		putUsedField("consignLoan", consignLoan);
	}
	/**
	 * @return Returns the discount.
	 */
	public double getDiscount()
	{
		return discount;
	}
	/**
	 * @param discount The discount to set.
	 */
	public void setDiscount(double discount)
	{
		this.discount = discount;
		putUsedField("discount", discount);
	}
	/**
	 * @return Returns the drawing.
	 */
	public double getDrawing()
	{
		return drawing;
	}
	/**
	 * @param drawing The drawing to set.
	 */
	public void setDrawing(double drawing)
	{
		this.drawing = drawing;
		putUsedField("drawing", drawing);
	}
	
	/**
	 * @return Returns the trustLoan.
	 */
	public double getTrustLoan()
	{
		return trustLoan;
	}
	/**
	 * @param trustLoan The trustLoan to set.
	 */
	public void setTrustLoan(double trustLoan)
	{
		this.trustLoan = trustLoan;
		putUsedField("trustLoan", trustLoan);
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId(long id)
	{
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return Returns the bankPay.
	 */
	public double getBankPay() {
		return bankPay;
	}
	/**
	 * @param bankPay The bankPay to set.
	 */
	public void setBankPay(double bankPay) {
		this.bankPay = bankPay;
	}
	/**
	 * @return Returns the businessPay.
	 */
	public double getBusinessPay() {
		return businessPay;
	}
	/**
	 * @param businessPay The businessPay to set.
	 */
	public void setBusinessPay(double businessPay) {
		this.businessPay = businessPay;
	}
	/**
	 * @return Returns the consignPay.
	 */
	public double getConsignPay() {
		return consignPay;
	}
	/**
	 * @param consignPay The consignPay to set.
	 */
	public void setConsignPay(double consignPay) {
		this.consignPay = consignPay;
	}
	/**
	 * @return Returns the currencyInvest.
	 */
	public double getCurrencyInvest() {
		return currencyInvest;
	}
	/**
	 * @param currencyInvest The currencyInvest to set.
	 */
	public void setCurrencyInvest(double currencyInvest) {
		this.currencyInvest = currencyInvest;
	}
	/**
	 * @return Returns the depositPrepare.
	 */
	public double getDepositPrepare() {
		return depositPrepare;
	}
	/**
	 * @param depositPrepare The depositPrepare to set.
	 */
	public void setDepositPrepare(double depositPrepare) {
		this.depositPrepare = depositPrepare;
	}
	/**
	 * @return Returns the discountPay.
	 */
	public double getDiscountPay() {
		return discountPay;
	}
	/**
	 * @param discountPay The discountPay to set.
	 */
	public void setDiscountPay(double discountPay) {
		this.discountPay = discountPay;
	}
	/**
	 * @return Returns the loanRepurchase.
	 */
	public double getLoanRepurchase() {
		return loanRepurchase;
	}
	/**
	 * @param loanRepurchase The loanRepurchase to set.
	 */
	public void setLoanRepurchase(double loanRepurchase) {
		this.loanRepurchase = loanRepurchase;
	}
	/**
	 * @return Returns the longInvest.
	 */
	public double getLongInvest() {
		return longInvest;
	}
	/**
	 * @param longInvest The longInvest to set.
	 */
	public void setLongInvest(double longInvest) {
		this.longInvest = longInvest;
	}
	/**
	 * @return Returns the otherPay.
	 */
	public double getOtherPay() {
		return otherPay;
	}
	/**
	 * @param otherPay The otherPay to set.
	 */
	public void setOtherPay(double otherPay) {
		this.otherPay = otherPay;
	}
	/**
	 * @return Returns the otherReceive.
	 */
	public double getOtherReceive() {
		return otherReceive;
	}
	/**
	 * @param otherReceive The otherReceive to set.
	 */
	public void setOtherReceive(double otherReceive) {
		this.otherReceive = otherReceive;
	}
	/**
	 * @return Returns the securitiesInvest.
	 */
	public double getSecuritiesInvest() {
		return securitiesInvest;
	}
	/**
	 * @param securitiesInvest The securitiesInvest to set.
	 */
	public void setSecuritiesInvest(double securitiesInvest) {
		this.securitiesInvest = securitiesInvest;
	}
	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return Returns the counterPartNo.
	 */
	public String getCounterPartNo() {
		return counterPartNo;
	}
	/**
	 * @param counterPartNo The counterPartNo to set.
	 */
	public void setCounterPartNo(String counterPartNo) {
		this.counterPartNo = counterPartNo;
	}
	/**
	 * @return Returns the strAbstract.
	 */
	public String getStrAbstract() {
		return strAbstract;
	}
	/**
	 * @param strAbstract The strAbstract to set.
	 */
	public void setStrAbstract(String strAbstract) {
		this.strAbstract = strAbstract;
	}
}
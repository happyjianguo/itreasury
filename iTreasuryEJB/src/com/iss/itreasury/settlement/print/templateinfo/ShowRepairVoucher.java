/*
 * Created on 2006-04-10
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
import java.io.Serializable;
import java.util.Vector;
/**
 * @author feiye
 * ����˵��:�ַ�����ƾ֤info��
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowRepairVoucher implements Serializable
{
	public ShowRepairVoucher()
	{
		super();
	}
	
	private String Year = "";//��
	private String Month = "";//��
	private String Day = "";//��
	private String PayAccountNo = "";//����˺�
	private String PayAccountName = "";//������ȫ��
	private String PayBankName = "";//�������������
	private String ReceiveAccountName = "";//�տ���ȫ��
	private String ReceiveAccountNo = "";//�տ���˺�
	private String ReceiveBankName = "";//�տ����������
	
	private String Amount = "";//������
	private String ChineseAmount = "";//�������д��
	private String InputUserName = "";//¼����(ƾ֤�о���)
	private String CheckUserName = "";//������

	private String ClientName = ""; // �ͻ���λ����
	private String TranscationTypeName ="";	//ҵ����������
	
	private String CurrencyName="";	//��������

	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return Amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(String amount) {
		Amount = amount;
	}
	/**
	 * @return Returns the checkUserName.
	 */
	public String getCheckUserName() {
		return CheckUserName;
	}
	/**
	 * @param checkUserName The checkUserName to set.
	 */
	public void setCheckUserName(String checkUserName) {
		CheckUserName = checkUserName;
	}
	/**
	 * @return Returns the chineseAmount.
	 */
	public String getChineseAmount() {
		return ChineseAmount;
	}
	/**
	 * @param chineseAmount The chineseAmount to set.
	 */
	public void setChineseAmount(String chineseAmount) {
		ChineseAmount = chineseAmount;
	}
	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return ClientName;
	}
	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		ClientName = clientName;
	}
	/**
	 * @return Returns the day.
	 */
	public String getDay() {
		return Day;
	}
	/**
	 * @param day The day to set.
	 */
	public void setDay(String day) {
		Day = day;
	}
	/**
	 * @return Returns the inputUserName.
	 */
	public String getInputUserName() {
		return InputUserName;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return Month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		Month = month;
	}
	/**
	 * @return Returns the payAccountName.
	 */
	public String getPayAccountName() {
		return PayAccountName;
	}
	/**
	 * @param payAccountName The payAccountName to set.
	 */
	public void setPayAccountName(String payAccountName) {
		PayAccountName = payAccountName;
	}
	/**
	 * @return Returns the payAccountNo.
	 */
	public String getPayAccountNo() {
		return PayAccountNo;
	}
	/**
	 * @param payAccountNo The payAccountNo to set.
	 */
	public void setPayAccountNo(String payAccountNo) {
		PayAccountNo = payAccountNo;
	}
	/**
	 * @return Returns the payBankName.
	 */
	public String getPayBankName() {
		return PayBankName;
	}
	/**
	 * @param payBankName The payBankName to set.
	 */
	public void setPayBankName(String payBankName) {
		PayBankName = payBankName;
	}
	/**
	 * @return Returns the receiveAccountName.
	 */
	public String getReceiveAccountName() {
		return ReceiveAccountName;
	}
	/**
	 * @param receiveAccountName The receiveAccountName to set.
	 */
	public void setReceiveAccountName(String receiveAccountName) {
		ReceiveAccountName = receiveAccountName;
	}
	/**
	 * @return Returns the receiveAccountNo.
	 */
	public String getReceiveAccountNo() {
		return ReceiveAccountNo;
	}
	/**
	 * @param receiveAccountNo The receiveAccountNo to set.
	 */
	public void setReceiveAccountNo(String receiveAccountNo) {
		ReceiveAccountNo = receiveAccountNo;
	}
	/**
	 * @return Returns the receiveBankName.
	 */
	public String getReceiveBankName() {
		return ReceiveBankName;
	}
	/**
	 * @param receiveBankName The receiveBankName to set.
	 */
	public void setReceiveBankName(String receiveBankName) {
		ReceiveBankName = receiveBankName;
	}
	/**
	 * @return Returns the transcationTypeName.
	 */
	public String getTranscationTypeName() {
		return TranscationTypeName;
	}
	/**
	 * @param transcationTypeName The transcationTypeName to set.
	 */
	public void setTranscationTypeName(String transcationTypeName) {
		TranscationTypeName = transcationTypeName;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return Year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		Year = year;
	}
	/**
	 * @return Returns the currencyName.
	 */
	public String getCurrencyName() {
		return CurrencyName;
	}
	/**
	 * @param currencyName The currencyName to set.
	 */
	public void setCurrencyName(String currencyName) {
		CurrencyName = currencyName;
	}
}

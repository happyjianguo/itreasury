/*
 * Created on 2003-4-25
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;

import java.io.Serializable;

/**
 * @author yanliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PayerOrPayeeInfo implements Serializable
{
	private long lAccountID = -1;
	private String sAccountNo = "";
	private String sAccountName = ""; //�ա���������
	private long lClientID = -1;
	private long lStatus = -1;	
	private long lOfficeID = -1;
	private long lCurrencyID = -1;
	private long lAccountType = -1;
	private String sBankNo = ""; // ���б��
	private String sProv = ""; // ����ʡ
	private String sCity = ""; // ������
	private String sBankName = ""; // ���������� 
	
	//����ӿ�����
	private String sPayeeBankExchangeNO = "";  //���������к�
	private String sPayeeBankCNAPSNO = "";  //������CNAPS��
	private String sPayeeBankOrgNO = "";  //�����л�����
	
	private String bankAllName = "";  //������ȫ��
	public String getBankAllName() {
		return bankAllName;
	}

	public void setBankAllName(String bankAllName) {
		this.bankAllName = bankAllName;
	}

	/**
	 * @return
	 */
	public long getAccountID() {
		return lAccountID;
	}

	/**
	 * @return
	 */
	public long getAccountType() {
		return lAccountType;
	}

	/**
	 * @return
	 */
	public long getClientID() {
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return lCurrencyID;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return lOfficeID;
	}

	/**
	 * @return
	 */
	public long getStatus() {
		return lStatus;
	}

	/**
	 * @return
	 */
	public String getAccountName() {
		return sAccountName;
	}

	/**
	 * @return
	 */
	public String getAccountNo() {
		return sAccountNo;
	}

	/**
	 * @return
	 */
	public String getBankName() {
		return sBankName;
	}

	/**
	 * @return
	 */
	public String getBankNo() {
		return sBankNo;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return sCity;
	}

	/**
	 * @return
	 */
	public String getProv() {
		return sProv;
	}

	/**
	 * @param l
	 */
	public void setAccountID(long l) {
		lAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setAccountType(long l) {
		lAccountType = l;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l) {
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		lCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		lOfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		lStatus = l;
	}

	/**
	 * @param string
	 */
	public void setAccountName(String string) {
		sAccountName = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string) {
		sAccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string) {
		sBankName = string;
	}

	/**
	 * @param string
	 */
	public void setBankNo(String string) {
		sBankNo = string;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		sCity = string;
	}

	/**
	 * @param string
	 */
	public void setProv(String string) {
		sProv = string;
	}

	public String getSPayeeBankExchangeNO() {
		return sPayeeBankExchangeNO;
	}

	public void setSPayeeBankExchangeNO(String payeeBankExchangeNO) {
		sPayeeBankExchangeNO = payeeBankExchangeNO;
	}

	public String getSPayeeBankCNAPSNO() {
		return sPayeeBankCNAPSNO;
	}

	public void setSPayeeBankCNAPSNO(String payeeBankCNAPSNO) {
		sPayeeBankCNAPSNO = payeeBankCNAPSNO;
	}

	public String getSPayeeBankOrgNO() {
		return sPayeeBankOrgNO;
	}

	public void setSPayeeBankOrgNO(String payeeBankOrgNO) {
		sPayeeBankOrgNO = payeeBankOrgNO;
	}

}
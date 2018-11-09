/*
 * Created on 2004-11-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;
import java.io.Serializable;
/**
 * ���Ա��嵥���ݶ���
 * 
 * @author ytcui
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class ContrastAccountBalanceBillInfo implements Serializable {
	private long accountId = -1;//����˾�˻�Id
	private long bankAccountId = -1;//�����˻�Id
	private String bankAccountNo = ""; //�����˻����
	private String accountName = ""; //����˾�˻�����
	private String accountNo = ""; //����˾�˻����
	private double accountBalance = 0.0; //����˾�˻����
	private double debitMoney = 0.0; //δ���˽跽������
	private double creditMoney = 0.0 ;//δ���˴���������
	private double bankAccountBalance = 0.0;//�����˻����

	/**
	 * ������ͬ�������Ƿ�ʧ�ܣ�true ʧ��
	 */
	private boolean isOperateFailed = false;
	/**
	 * ������ͬ������ʧ��ԭ��
	 */
	private String m_strErrorMessage = null;
	/**
	 * @return Returns the accountBalance.
	 */
	public double getAccountBalance()
	{
		return accountBalance;
	}
	/**
	 * @param accountBalance The accountBalance to set.
	 */
	public void setAccountBalance(double accountBalance)
	{
		this.accountBalance = accountBalance;
	}
	/**
	 * @return Returns the accountId.
	 */
	public long getAccountId()
	{
		return accountId;
	}
	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}
	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return accountName;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}
	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo()
	{
		return accountNo;
	}
	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}
	/**
	 * @return Returns the bankAccountBalance.
	 */
	public double getBankAccountBalance()
	{
		return bankAccountBalance;
	}
	/**
	 * @param bankAccountBalance The bankAccountBalance to set.
	 */
	public void setBankAccountBalance(double bankAccountBalance)
	{
		this.bankAccountBalance = bankAccountBalance;
	}
	/**
	 * @return Returns the bankAccountNo.
	 */
	public String getBankAccountNo()
	{
		return bankAccountNo;
	}
	/**
	 * @param bankAccountNo The bankAccountNo to set.
	 */
	public void setBankAccountNo(String bankAccountNo)
	{
		this.bankAccountNo = bankAccountNo;
	}
	/**
	 * @return Returns the creditMoney.
	 */
	public double getCreditMoney()
	{
		return creditMoney;
	}
	/**
	 * @param creditMoney The creditMoney to set.
	 */
	public void setCreditMoney(double creditMoney)
	{
		this.creditMoney = creditMoney;
	}
	/**
	 * @return Returns the debitMoney.
	 */
	public double getDebitMoney()
	{
		return debitMoney;
	}
	/**
	 * @param debitMoney The debitMoney to set.
	 */
	public void setDebitMoney(double debitMoney)
	{
		this.debitMoney = debitMoney;
	}
	/**
	 * @return Returns the isOperateFailed.
	 */
	public boolean isOperateFailed()
	{
		return isOperateFailed;
	}
	/**
	 * @param isOperateFailed The isOperateFailed to set.
	 */
	public void setOperateFailed(boolean isOperateFailed)
	{
		this.isOperateFailed = isOperateFailed;
	}
	/**
	 * @return Returns the m_strErrorMessage.
	 */
	public String getM_strErrorMessage()
	{
		return m_strErrorMessage;
	}
	/**
	 * @param errorMessage The m_strErrorMessage to set.
	 */
	public void setM_strErrorMessage(String errorMessage)
	{
		m_strErrorMessage = errorMessage;
	}
	/**
	 * @return Returns the bankAccountId.
	 */
	public long getBankAccountId()
	{
		return bankAccountId;
	}
	/**
	 * @param bankAccountId The bankAccountId to set.
	 */
	public void setBankAccountId(long bankAccountId)
	{
		this.bankAccountId = bankAccountId;
	}

}

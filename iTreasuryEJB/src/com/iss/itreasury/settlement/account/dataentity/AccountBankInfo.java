/*
 * Created on 2003-9-4
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;
import java.io.Serializable;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AccountBankInfo  implements Serializable{

	private long AccountID = -1; //�˻�ID
	private long BankID = -1; //���д���
	private String BankAccountNo = ""; //�����˻����
	/**
	 * @return
	 */
	public long getAccountID()
	{
		return this.AccountID;
	}

	/**
	 * @param accountID
	 */
	public void setAccountID(long accountID)
	{
		this.AccountID = accountID;
	}

	/**
	 * @return
	 */
	public String getBankAccountNo()
	{
		return this.BankAccountNo;
	}

	/**
	 * @param bankAccountNo
	 */
	public void setBankAccountNo(String bankAccountNo)
	{
		this.BankAccountNo = bankAccountNo;
	}

	/**
	 * @return
	 */
	public long getBankID()
	{
		return this.BankID;
	}

	/**
	 * @param bankID
	 */
	public void setBankID(long bankID)
	{
		this.BankID = bankID;
	}

}

/*
 * Created on 2004-4-9
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.query.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.securities.util.SECBaseDataEntity;
/**
 * �˴���������˵����
 * �������ڣ�(2004-4-09 13:40:10)
 * @author��gqfang
 */
public class AccountRecordInfo extends SECBaseDataEntity implements Serializable
{
	public String account = ""; //��Ŀ��
	public String name = "";
	public double startBalance = 0; //�ڳ����
	public double endBalance = 0; //��ĩ���
	public double debitAmount = 0; //�跽�ϼ�
	public double loanAmount = 0; //�����ϼ�
	public long number = 0; //ƾ֤���� 
	public long debitNumber = 0; //�跽ƾ֤
	public long creditNumber = 0; //����ƾ֤
	public long pageCount = 0; //ҳ��
	public double debitsSumAmount = 0; //��������ȫ���跽�ϼ�,
	public double creditSumAmount = 0; //��������ȫ�������ϼƣ�
	public String subject = "";
	public long currencyID = -1;
	public long officeID = -1;
	public long accountID = -1;
	public long transactionTypeID = -1; //�������ͱ�ʶ
	public String transactionType = ""; //��������
	public double debit = 0; //����ϼ�
	public double loan = 0; //�տ�ϼ�
	//public boolean isRoot = false;
	public Timestamp startDate = null; //ִ��������
	public Timestamp endDate = null; //ִ������ֹ
	private long pageLineCount = -1; //ÿҳ����
	private long pageNo = -1; //�ڼ�ҳ
	private long orderParam = -1; //����
	private long desc = -1; //����.����
	/**
	 * @return
	 */
	public long getAccountID()
	{
		return accountID;
	}
	/**
	 * @return
	 */
	public long getCreditNumber()
	{
		return creditNumber;
	}
	/**
	 * @return
	 */
	public double getCreditSumAmount()
	{
		return creditSumAmount;
	}
	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}
	/**
	 * @return
	 */
	public double getDebitAmount()
	{
		return debitAmount;
	}
	/**
	 * @return
	 */
	public long getDebitNumber()
	{
		return debitNumber;
	}
	/**
	 * @return
	 */
	public double getDebitsSumAmount()
	{
		return debitsSumAmount;
	}
	/**
	 * @return
	 */
	public double getEndBalance()
	{
		return endBalance;
	}
	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}
	/**
	 * @return
	 */
	public double getLoanAmount()
	{
		return loanAmount;
	}
	/**
	 * @return
	 */
	public long getNumber()
	{
		return number;
	}
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return officeID;
	}
	/**
	 * @return
	 */
	public long getPageCount()
	{
		return pageCount;
	}
	/**
	 * @return
	 */
	public double getStartBalance()
	{
		return startBalance;
	}
	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return startDate;
	}
	/**
	
	/**
	 * @return
	 */
	public String getSubject()
	{
		return subject;
	}
	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		accountID = l;
	}
	/**
	 * @param l
	 */
	public void setCreditNumber(long l)
	{
		creditNumber = l;
	}
	/**
	 * @param d
	 */
	public void setCreditSumAmount(double d)
	{
		creditSumAmount = d;
	}
	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		currencyID = l;
	}
	/**
	 * @param d
	 */
	public void setDebitAmount(double d)
	{
		debitAmount = d;
	}
	/**
	 * @param l
	 */
	public void setDebitNumber(long l)
	{
		debitNumber = l;
	}
	/**
	 * @param d
	 */
	public void setDebitsSumAmount(double d)
	{
		debitsSumAmount = d;
	}
	/**
	 * @param d
	 */
	public void setEndBalance(double d)
	{
		endBalance = d;
	}
	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}
	/**
	 * @param d
	 */
	public void setLoanAmount(double d)
	{
		loanAmount = d;
	}
	/**
	 * @param l
	 */
	public void setNumber(long l)
	{
		number = l;
	}
	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		officeID = l;
	}
	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		pageCount = l;
	}
	/**
	 * @param d
	 */
	public void setStartBalance(double d)
	{
		startBalance = d;
	}
	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		startDate = timestamp;
	}
	/**
	 * @param string
	 */
	public void setSubject(String string)
	{
		subject = string;
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
	 * @return
	 */
	public String getAccount()
	{
		return account;
	}
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param string
	 */
	public void setAccount(String string)
	{
		account = string;
	}
	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}
	/**
	 * @return
	 */
	public long getDesc()
	{
		return desc;
	}
	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return orderParam;
	}
	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return pageLineCount;
	}
	/**
	 * @return
	 */
	public long getPageNo()
	{
		return pageNo;
	}
	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		desc = l;
	}
	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		orderParam = l;
	}
	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		pageLineCount = l;
	}
	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		pageNo = l;
	}
	/**
	 * @return
	 */
	public double getDebit()
	{
		return debit;
	}
	/**
	 * @return
	 */
	public double getLoan()
	{
		return loan;
	}
	/**
	 * @return
	 */
	public String getTransactionType()
	{
		return transactionType;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return transactionTypeID;
	}
	/**
	 * @param d
	 */
	public void setDebit(double d)
	{
		debit = d;
	}
	/**
	 * @param d
	 */
	public void setLoan(double d)
	{
		loan = d;
	}
	/**
	 * @param string
	 */
	public void setTransactionType(String string)
	{
		transactionType = string;
	}
	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		transactionTypeID = l;
	}
}

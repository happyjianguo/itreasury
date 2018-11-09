package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 * 定期存款查询结果
 * 创建日期：(2003-11-03 17:20:10)
 * @author：xrli
 */
public class QueryTransFixedResultInfo implements java.io.Serializable
{
	private long AccountID = -1;           //账户ID    
	private long AccountTypeID = -1;       //账户类型
	private long SubAccountID = -1;        //子账户ID
	private String AccountNo = "";         //账户编号   	   	
	private String ClientName = "";        //客户名称
	private String DepositNo = "";         //存款单据号
	private double Amount = 0.0;           //金额
	private double Balance = 0.0;          //存款余额
	private long SubAccountStatusID = -1;  //子账户状态
	private double Rate = 0.0;             //利率
	private double Interest = 0.0;         //累计利息
	private double PreDrawInterest = 0.0;  //计提利息
	private String TransNo = "";           //交易编号	
	private long TransactionTypeID = -1;   //交易类型
	private Timestamp StartDate = null;    //开始日期
	private Timestamp EndDate = null;      //结束日期
	private long DepositTerm = -1;         //定期存款期限
	private long NoticeDay = -1;           //通知存款日期（天）
	private String DepositBillNo ="";		//换开定期存单
	/**
	 * @return
	 */
	public String getAccountNo()
	{
		return AccountNo;
	}
	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
	}
	/**
	 * @return
	 */
	public double getBalance()
	{
		return Balance;
	}
	/**
	 * @return
	 */
	public String getClientName()
	{
		return ClientName;
	}
	/**
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}
	/**
	 * @return
	 */
	public double getInterest()
	{
		return Interest;
	}
	/**
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}
	/**
	 * @param string
	 */
	public void setAccountNo(String string)
	{
		AccountNo = string;
	}
	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
	}
	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		Balance = d;
	}
	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		ClientName = string;
	}
	/**
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		DepositNo = string;
	}
	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		Interest = d;
	}
	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}
	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}
	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}
	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}
	/**
	 * @param l
	 */
	public void setAccountID(long l)
	{
		AccountID = l;
	}
	/**
	 * @param l
	 */
	public void setAccountTypeID(long l)
	{
		AccountTypeID = l;
	}
	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		SubAccountID = l;
	}
	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}
	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}
	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}
	/**
	 * @return
	 */
	public long getSubAccountStatusID()
	{
		return SubAccountStatusID;
	}
	/**
	 * @param l
	 */
	public void setSubAccountStatusID(long l)
	{
		SubAccountStatusID = l;
	}
	/**
	 * @return
	 */
	public double getPreDrawInterest()
	{
		return PreDrawInterest;
	}
	/**
	 * @param d
	 */
	public void setPreDrawInterest(double d)
	{
		PreDrawInterest = d;
	}
	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return EndDate;
	}
	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}
	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}
	/**
	 * @return
	 */
	public long getDepositTerm()
	{
		return DepositTerm;
	}
	/**
	 * @param l
	 */
	public void setDepositTerm(long l)
	{
		DepositTerm = l;
	}
	/**
	 * @return
	 */
	public long getNoticeDay()
	{
		return NoticeDay;
	}

	/**
	 * @param l
	 */
	public void setNoticeDay(long l)
	{
		NoticeDay = l;
	}

	/**
	 * @return Returns the depositBillNo.
	 */
	public String getDepositBillNo() {
		return DepositBillNo;
	}
	/**
	 * @param depositBillNo The depositBillNo to set.
	 */
	public void setDepositBillNo(String depositBillNo) {
		DepositBillNo = depositBillNo;
	}
}
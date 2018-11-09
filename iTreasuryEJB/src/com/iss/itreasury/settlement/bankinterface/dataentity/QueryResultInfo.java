package com.iss.itreasury.settlement.bankinterface.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryResultInfo implements Serializable
{
	//排序列编号
	private long Order = 0;
	//升降序标识
	private long Desc =-1;
	private long ID = -1;                      // ID
	private String AccountNo = "";                // 账户号  
	private String AccountName = "";                // 账户名称  
	private String BankName = "";                //银行名称	
	private String OppAccountNo = "";             // 对方账户号
	private String OppAccountName = "";             // 对方账户名称
	private String OppBankName = "";            //对方银行名称    
	private Timestamp TransactionDate = null;        //交易日期	    
	private double Amount = 0.0;                 //金额      
	private long TransDirection = -1;           // 交易方向
	private String Abstract = "";               //摘要
	
	
	
	/**
	 * Constructor for QueryBankAccontTransConditionInfo.
	 */
	public QueryResultInfo()
	{
		super();
	}

	/**
	 * @return
	 */
	public String getAbstract() {
		return Abstract;
	}

	/**
	 * @return
	 */
	public double getAmount() {
		return Amount;
	}

	/**
	 * @return
	 */
	public String getBankName() {
		return BankName;
	}

	/**
	 * @return
	 */
	public long getDesc() {
		return Desc;
	}

	
	
	/**
	 * @return
	 */
	public String getOppBankName() {
		return OppBankName;
	}

	/**
	 * @return
	 */
	public long getOrder() {
		return Order;
	}

	
	
	/**
	 * @param string
	 */
	public void setAbstract(String string) {
		Abstract = string;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		Amount = d;
	}

	/**
	 * @param string
	 */
	public void setBankName(String string) {
		BankName = string;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l) {
		Desc = l;
	}

	

	/**
	 * @param string
	 */
	public void setOppBankName(String string) {
		OppBankName = string;
	}

	/**
	 * @param l
	 */
	public void setOrder(long l) {
		Order = l;
	}	

	

	

	/**
	 * @return
	 */
	public long getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public Timestamp getTransactionDate() {
		return TransactionDate;
	}

	

	/**
	 * @param l
	 */
	public void setID(long l) {
		ID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setTransactionDate(Timestamp timestamp) {
		TransactionDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getTransDirection() {
		return TransDirection;
	}

	/**
	 * @param l
	 */
	public void setTransDirection(long l) {
		TransDirection = l;
	}

	/**
	 * @return
	 */
	public String getAccountName() {
		return AccountName;
	}

	/**
	 * @return
	 */
	public String getAccountNo() {
		return AccountNo;
	}

	/**
	 * @return
	 */
	public String getOppAccountName() {
		return OppAccountName;
	}

	/**
	 * @return
	 */
	public String getOppAccountNo() {
		return OppAccountNo;
	}

	/**
	 * @param string
	 */
	public void setAccountName(String string) {
		AccountName = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNo(String string) {
		AccountNo = string;
	}

	/**
	 * @param string
	 */
	public void setOppAccountName(String string) {
		OppAccountName = string;
	}

	/**
	 * @param string
	 */
	public void setOppAccountNo(String string) {
		OppAccountNo = string;
	}

}

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
 * 此处插入类型说明。
 * 创建日期：(2004-4-09 13:40:10)
 * @author：gqfang
 */
public  class AccountTransactionInfo extends SECBaseDataEntity implements Serializable
{
	public long typeId = -1;//借或贷
	public Timestamp execute=null;//执行日期
	public Timestamp startDate = null; //执行日期起
	public Timestamp endDate = null; //执行日期止
	public long transactionNoID=-1;//交易标识
	public String transNo="";//交易号
	public long transactionTypeID=-1;//交易类型标识
	public String transactionType="";//交易类型
	public String account="";//科目号
	public double amount=0;//金额
	public long directionID=-1;//借贷标识
	public String direction="";//借贷名称
	public String otherAccountRecord="";//对方科目号
	public long statusID=-1;//状态
	public String status="";//状态名称
	public String strAbstract="";//摘要
	public String inputUser="";//录入人
	public String checkUser="";//复核人 
	public long pageCount=0;//页号
	//
	public double totalAmount = 0.0;
	public long totalRecordes = 0;
	//用于银行存款明细查询
	public String name = "";
	public String name2 = "";
	public long   record = -1;//行号
	public double dRAmount=0;//借方金额
	public double cRAmount=0;//贷方金额
	public String currencyCode="";//币种名称
	public String source = "";//来源
	public String description = "";
	public String accountDescription = "";
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
		
	}//帐户名称
    
	/**
	 * @return
	 */
	public String getAccountDescription()
	{
		return accountDescription;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @return
	 */
	public String getCheckUser()
	{
		return checkUser;
	}

	/**
	 * @return
	 */
	public double getCRAmount()
	{
		return cRAmount;
	}

	/**
	 * @return
	 */
	public String getCurrencyCode()
	{
		return currencyCode;
	}

	/**
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return
	 */
	public String getDirection()
	{
		return direction;
	}

	/**
	 * @return
	 */
	public long getDirectionID()
	{
		return directionID;
	}

	/**
	 * @return
	 */
	public double getDRAmount()
	{
		return dRAmount;
	}

	/**
	 * @return
	 */
	public Timestamp getExecute()
	{
		return execute;
	}

	/**
	 * @return
	 */
	public String getInputUser()
	{
		return inputUser;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getName2()
	{
		return name2;
	}

	/**
	 * @return
	 */
	public String getOtherAccountRecord()
	{
		return otherAccountRecord;
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
	public long getRecord()
	{
		return record;
	}

	/**
	 * @return
	 */
	public String getSource()
	{
		return source;
	}

	/**
	 * @return
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * @return
	 */
	public String getStrAbstract()
	{
		return strAbstract;
	}

	/**
	 * @return
	 */
	public double getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * @return
	 */
	public long getTotalRecordes()
	{
		return totalRecordes;
	}

	/**
	 * @return
	 */
	public long getTransactionNoID()
	{
		return transactionNoID;
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
	 * @return
	 */
	public String getTransNo()
	{
		return transNo;
	}

	/**
	 * @param string
	 */
	public void setAccountDescription(String string)
	{
		accountDescription = string;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
	}

	/**
	 * @param string
	 */
	public void setCheckUser(String string)
	{
		checkUser = string;
	}

	/**
	 * @param d
	 */
	public void setCRAmount(double d)
	{
		cRAmount = d;
	}

	/**
	 * @param string
	 */
	public void setCurrencyCode(String string)
	{
		currencyCode = string;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string)
	{
		description = string;
	}

	/**
	 * @param string
	 */
	public void setDirection(String string)
	{
		direction = string;
	}

	/**
	 * @param l
	 */
	public void setDirectionID(long l)
	{
		directionID = l;
	}

	/**
	 * @param d
	 */
	public void setDRAmount(double d)
	{
		dRAmount = d;
	}

	/**
	 * @param timestamp
	 */
	public void setExecute(Timestamp timestamp)
	{
		execute = timestamp;
	}

	/**
	 * @param string
	 */
	public void setInputUser(String string)
	{
		inputUser = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @param string
	 */
	public void setName2(String string)
	{
		name2 = string;
	}

	/**
	 * @param string
	 */
	public void setOtherAccountRecord(String string)
	{
		otherAccountRecord = string;
	}

	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		pageCount = l;
	}

	/**
	 * @param l
	 */
	public void setRecord(long l)
	{
		record = l;
	}

	/**
	 * @param string
	 */
	public void setSource(String string)
	{
		source = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string)
	{
		status = string;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		statusID = l;
	}

	/**
	 * @param string
	 */
	public void setStrAbstract(String string)
	{
		strAbstract = string;
	}

	/**
	 * @param d
	 */
	public void setTotalAmount(double d)
	{
		totalAmount = d;
	}

	/**
	 * @param l
	 */
	public void setTotalRecordes(long l)
	{
		totalRecordes = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionNoID(long l)
	{
		transactionNoID = l;
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

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		transNo = string;
	}

	/**
	 * @return
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * @param string
	 */
	public void setAccount(String string)
	{
		account = string;
	}

	/**
	 * @return
	 */
	public long getTypeId()
	{
		return typeId;
	}

	/**
	 * @param l
	 */
	public void setTypeId(long l)
	{
		typeId = l;
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
	public Timestamp getStartDate()
	{
		return startDate;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		startDate = timestamp;
	}

}

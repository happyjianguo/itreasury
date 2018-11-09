/*
 * Created on 2003-10-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contract.dataentity;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import java.sql.Timestamp;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractQueryInfo implements java.io.Serializable
{
	private long lTypeID = -1; //贷款类型
	private long lCurrencyID = -1; //币种标识
	private long lOfficeID = -1; //办事处标识 
	private long lContractTypeID = -1; //合同类型标识
	private long lActionID = -1; //动作：修改或者复核
	private long lUserID = -1; //操作人标识
	private long lContractIDFrom = -1; //合同标识起始
	private long lContractIDTo = -1; //合同标识结束
	private String sContractNoFrom = ""; //合同编号起始
	private String sContractNoTo = ""; //合同编号结束
	private long lConsignClientID = -1; //委托单位标识
	private long lClientID = -1; //借款单位标识
	private String sConsignClientName = ""; //委托单位名称
	private String sClientName = ""; //借款单位名称
	private double dAmountFrom = 0; //金额起始
	private double dAmountTo = 0; //金额结束 
	private Timestamp tsLoanStart = null; //贷款日期起始
	private Timestamp tsLoanEnd = null; //贷款日期结束
	private long lIntervalNum = -1; //贷款期限
	private Timestamp tsInputStart = null; //合同录入日期起始
	private Timestamp tsInputEnd = null; //合同录入日期结束
	private long lStatusID = -1; //合同状态
 
	//分页变量
	private long lPageLineCount = 0; //
	private long lPageNo = 1;
	private long lOrderParam = 1;
	private long lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;

	/**
	 * @return
	 */
	public double getAmountFrom()
	{
		return dAmountFrom;
	}

	/**
	* @return
	*/
	public String getFormatAmountFrom()
	{
		return DataFormat.formatDisabledAmount(dAmountFrom);
	}

	/**
	 * @return
	 */
	public double getAmountTo()
	{
		return dAmountTo;
	}

	/**
	* @return
	*/
	public String getFormatAmountTo()
	{
		return DataFormat.formatDisabledAmount(dAmountTo);
	}

	/**
	 * @return
	 */
	public long getActionID()
	{
		return lActionID;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return lClientID;
	}

	/**
	 * @return
	 */
	public long getContractIDFrom()
	{
		return lContractIDFrom;
	}

	/**
	 * @return
	 */
	public long getContractIDTo()
	{
		return lContractIDTo;
	}

	/**
	 * @return
	 */
	public long getContractTypeID()
	{
		return lContractTypeID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return lCurrencyID;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return lDesc;
	}

	/**
	 * @return
	 */
	public long getIntervalNum()
	{
		return lIntervalNum;
	}

	/**
	* @return
	*/
	public String getFormatIntervalNum()
	{
		if (lIntervalNum > 0)
		{
			return String.valueOf(lIntervalNum);
		}
		else
		{
			return "";
		}

	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return lOfficeID;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return lOrderParam;
	}

	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return lPageLineCount;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return lPageNo;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return lStatusID;
	}

	/**
	 * @return
	 */
	public long getTypeID()
	{
		return lTypeID;
	}

	/**
	 * @return
	 */
	public long getUserID()
	{
		return lUserID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputEnd()
	{
		return tsInputEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getInputStart()
	{
		return tsInputStart;
	}

	/**
		 * @return
		 */
	public String getFormatInputEnd()
	{
		return DataFormat.getDateString(tsInputEnd);
	}

	/**
	 * @return
	 */
	public String getFormatInputStart()
	{
		return DataFormat.getDateString(tsInputStart);
	}

	/**
	 * @return
	 */
	public Timestamp getLoanEnd()
	{
		return tsLoanEnd;
	}

	public String getFormatLoanEnd()
	{
		return DataFormat.getDateString(tsLoanEnd);
	}

	public String getFormatLoanStart()
	{
		return DataFormat.getDateString(tsLoanStart);
	}

	/**
	 * @return
	 */
	public Timestamp getLoanStart()
	{
		return tsLoanStart;
	}

	/**
	 * @param d
	 */
	public void setAmountFrom(double d)
	{
		dAmountFrom = d;
	}

	/**
	 * @param d
	 */
	public void setAmountTo(double d)
	{
		dAmountTo = d;
	}

	/**
	 * @param l
	 */
	public void setActionID(long l)
	{
		lActionID = l;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		lClientID = l;
	}

	/**
	 * @param l
	 */
	public void setContractIDFrom(long l)
	{
		lContractIDFrom = l;
	}

	/**
	 * @param l
	 */
	public void setContractIDTo(long l)
	{
		lContractIDTo = l;
	}

	/**
	 * @param l
	 */
	public void setContractTypeID(long l)
	{
		lContractTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		lCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		lDesc = l;
	}

	/**
	 * @param l
	 */
	public void setIntervalNum(long l)
	{
		lIntervalNum = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		lOfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		lOrderParam = l;
	}

	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		lPageLineCount = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		lPageNo = l;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		lStatusID = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		lTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setUserID(long l)
	{
		lUserID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputEnd(Timestamp timestamp)
	{
		tsInputEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInputStart(Timestamp timestamp)
	{
		tsInputStart = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanEnd(Timestamp timestamp)
	{
		tsLoanEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setLoanStart(Timestamp timestamp)
	{
		tsLoanStart = timestamp;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return lConsignClientID;
	}

	/**
	 * @param l
	 */
	public void setConsignClientID(long l)
	{
		lConsignClientID = l;
	}

	/**
	 * @return
	 */
	public String getContractNoFrom()
	{
		return sContractNoFrom;
	}

	/**
	 * @return
	 */
	public String getContractNoTo()
	{
		return sContractNoTo;
	}

	/**
	 * @param string
	 */
	public void setContractNoFrom(String string)
	{
		sContractNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setContractNoTo(String string)
	{
		sContractNoTo = string;
	}

	/**
	 * @return
	 */
	public String getClientName()
	{
		return sClientName;
	}

	/**
	 * @return
	 */
	public String getConsignClientName()
	{
		return sConsignClientName;
	}

	/**
	 * @param string
	 */
	public void setClientName(String string)
	{
		sClientName = string;
	}

	/**
	 * @param string
	 */
	public void setConsignClientName(String string)
	{
		sConsignClientName = string;
	}

}

package com.iss.itreasury.settlement.interest.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 利息费用设置查询条件对象，对应查询页面上的栏位信息
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QueryInterestFeeSettingInfo implements Serializable
{
	private long officeID = -1; //is '机构ID';                          
	private long currencyID = -1; //is '币种ID';                   
	private Timestamp calculateDateStart = null; //is '开始计算日期时间';  
	private Timestamp calculateDateEnd = null; //is '结束计算日期时间'; 	                   
	private long[] interestTypes = null; //is '利息类型';               
	private String accountNoStart = ""; //is '起始账户号';                  
	private String accountNoEnd = ""; //is '终止账户号';  
	private String contractNoStart = ""; //is '起始合同号';                
	private String contractNoEnd = ""; //is '终止合同号';                 
	private String loanNotesNoStart = ""; //is '起始放款通知单号';             
	private String loanNotesNoEnd = ""; //is '终止放款通知单号';                
	private long[] loanTypeIDs = null; //is '贷款种类';                    
	private long loanIntervalNumMonth = -1; //is '贷款期限';          
	private long loanIntervalNumYear = -1; //is '贷款年期'; 	          
	private long consignClientID = -1; //is '委托方客户ID';               
	private String consignerName = ""; //is '委托方客户号', 供页面显示用;  
	private long statusID = -1; //is '状态';  

	/**
	 * Constructor for QInterestFeeSettingInfo.
	 */
	public QueryInterestFeeSettingInfo()
	{
		super();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sbResult = new StringBuffer(128);

		sbResult.append(this.getClass().getName() + " instance (hashCode=" + this.hashCode() + ")\r\n");
		sbResult.append("=========================================\r\n");

		//获得当前对象指定名称的Field对象
		java.lang.reflect.Field[] field = null;
		try
		{
			field = this.getClass().getDeclaredFields();

			if (field != null)
			{
				for (int i = 0; i < field.length; i++)
				{
					field[i].setAccessible(true);

					sbResult.append(field[i].getName() + " = ");
					sbResult.append(field[i].get(this) + ";\r\n");
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}

		return sbResult.toString();
	}
	/**
	 * Returns the accountNoEnd.
	 * @return String
	 */
	public String getAccountNoEnd()
	{
		return accountNoEnd;
	}

	/**
	 * Returns the accountNoStart.
	 * @return String
	 */
	public String getAccountNoStart()
	{
		return accountNoStart;
	}

	/**
	 * Returns the calculateDateEnd.
	 * @return Timestamp
	 */
	public Timestamp getCalculateDateEnd()
	{
		return calculateDateEnd;
	}

	/**
	 * Returns the calculateDateStart.
	 * @return Timestamp
	 */
	public Timestamp getCalculateDateStart()
	{
		return calculateDateStart;
	}

	/**
	 * Returns the consignClientID.
	 * @return long
	 */
	public long getConsignClientID()
	{
		return consignClientID;
	}

	/**
	 * Returns the consignerName.
	 * @return String
	 */
	public String getConsignerName()
	{
		return consignerName;
	}

	/**
	 * Returns the contractNoEnd.
	 * @return String
	 */
	public String getContractNoEnd()
	{
		return contractNoEnd;
	}

	/**
	 * Returns the contractNoStart.
	 * @return String
	 */
	public String getContractNoStart()
	{
		return contractNoStart;
	}

	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID()
	{
		return currencyID;
	}

	/**
	 * Returns the interestType.
	 * @return long
	 */
	public long getInterestType()
	{
		long lResult = -1;
		if (this.interestTypes != null && this.interestTypes.length > 0)
		{
			lResult = this.interestTypes[0];
		}

		return lResult;
	}

	public long[] getInterestTypes()
	{
		return this.interestTypes;
	}

	/**
	 * Returns the loanIntervalNumMonth.
	 * @return long
	 */
	public long getLoanIntervalNumMonth()
	{
		return loanIntervalNumMonth;
	}

	/**
	 * Returns the loanIntervalNumYear.
	 * @return long
	 */
	public long getLoanIntervalNumYear()
	{
		return loanIntervalNumYear;
	}

	/**
	 * Returns the loanNotesNoEnd.
	 * @return String
	 */
	public String getLoanNotesNoEnd()
	{
		return loanNotesNoEnd;
	}

	/**
	 * Returns the loanNotesNoStart.
	 * @return String
	 */
	public String getLoanNotesNoStart()
	{
		return loanNotesNoStart;
	}

	/**
	 * Returns the loanTypeID.
	 * @return long
	 */
	public long getLoanTypeID()
	{
		long lResult = -1;
		if (this.loanTypeIDs != null && this.loanTypeIDs.length > 0)
		{
			lResult = this.loanTypeIDs[0];
		}

		return lResult;
	}

	public long[] getLoanTypeIDs()
	{
		return this.loanTypeIDs;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return officeID;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return statusID;
	}

	/**
	 * Sets the accountNoEnd.
	 * @param accountNoEnd The accountNoEnd to set
	 */
	public void setAccountNoEnd(String accountNoEnd)
	{
		this.accountNoEnd = accountNoEnd;
	}

	/**
	 * Sets the accountNoStart.
	 * @param accountNoStart The accountNoStart to set
	 */
	public void setAccountNoStart(String accountNoStart)
	{
		this.accountNoStart = accountNoStart;
	}

	/**
	 * Sets the calculateDateEnd.
	 * @param calculateDateEnd The calculateDateEnd to set
	 */
	public void setCalculateDateEnd(Timestamp calculateDateEnd)
	{
		this.calculateDateEnd = calculateDateEnd;
	}

	/**
	 * Sets the calculateDateStart.
	 * @param calculateDateStart The calculateDateStart to set
	 */
	public void setCalculateDateStart(Timestamp calculateDateStart)
	{
		this.calculateDateStart = calculateDateStart;
	}

	/**
	 * Sets the consignClientID.
	 * @param consignClientID The consignClientID to set
	 */
	public void setConsignClientID(long consignClientID)
	{
		this.consignClientID = consignClientID;
	}

	/**
	 * Sets the consignerName.
	 * @param consignerName The consignerName to set
	 */
	public void setConsignerName(String consignerName)
	{
		this.consignerName = consignerName;
	}

	/**
	 * Sets the contractNoEnd.
	 * @param contractNoEnd The contractNoEnd to set
	 */
	public void setContractNoEnd(String contractNoEnd)
	{
		this.contractNoEnd = contractNoEnd;
	}

	/**
	 * Sets the contractNoStart.
	 * @param contractNoStart The contractNoStart to set
	 */
	public void setContractNoStart(String contractNoStart)
	{
		this.contractNoStart = contractNoStart;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID)
	{
		this.currencyID = currencyID;
	}

	/**
	 * Sets the interestType.
	 * @param interestType The interestType to set
	 */
	public void setInterestType(long interestType)
	{
		this.interestTypes = new long[] { interestType };
	}

	public void setInterestTypes(long[] interestTypes)
	{
		this.interestTypes = interestTypes;
	}

	/**
	 * Sets the loanIntervalNumMonth.
	 * @param loanIntervalNumMonth The loanIntervalNumMonth to set
	 */
	public void setLoanIntervalNumMonth(long loanIntervalNumMonth)
	{
		this.loanIntervalNumMonth = loanIntervalNumMonth;
	}

	/**
	 * Sets the loanIntervalNumYear.
	 * @param loanIntervalNumYear The loanIntervalNumYear to set
	 */
	public void setLoanIntervalNumYear(long loanIntervalNumYear)
	{
		this.loanIntervalNumYear = loanIntervalNumYear;
	}

	/**
	 * Sets the loanNotesNoEnd.
	 * @param loanNotesNoEnd The loanNotesNoEnd to set
	 */
	public void setLoanNotesNoEnd(String loanNotesNoEnd)
	{
		this.loanNotesNoEnd = loanNotesNoEnd;
	}

	/**
	 * Sets the loanNotesNoStart.
	 * @param loanNotesNoStart The loanNotesNoStart to set
	 */
	public void setLoanNotesNoStart(String loanNotesNoStart)
	{
		this.loanNotesNoStart = loanNotesNoStart;
	}

	/**
	 * Sets the loanTypeID.
	 * @param loanTypeID The loanTypeID to set
	 */
	public void setLoanTypeID(long loanTypeID)
	{
		this.loanTypeIDs = new long[] { loanTypeID };
	}

	public void setLoanTypeIDs(long[] loanTypeIDs)
	{
		this.loanTypeIDs = loanTypeIDs;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		this.officeID = officeID;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		this.statusID = statusID;
	}

}

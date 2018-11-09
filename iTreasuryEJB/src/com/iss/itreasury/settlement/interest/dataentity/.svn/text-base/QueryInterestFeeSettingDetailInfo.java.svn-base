package com.iss.itreasury.settlement.interest.dataentity;

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
public class QueryInterestFeeSettingDetailInfo implements Serializable
{
	private long officeID = -1; //is '机构ID';                          
	private long currencyID = -1; //is '币种ID';                         
	private String accountNoStart = ""; //is '起始账户号';                  
	private String accountNoEnd = ""; //is '终止账户号';  
	private String contractNoStart = ""; //is '起始合同号';                
	private String contractNoEnd = ""; //is '终止合同号';                 
	private String loanNotesNoStart = ""; //is '起始放款通知单号';             
	private String loanNotesNoEnd = ""; //is '终止放款通知单号';                
	private long loanTypeID = -1; //is '贷款种类';                    
	private long loanIntervalNumMonth = -1; //is '贷款期限';          
	private String loanIntervalNumYear = ""; //is '贷款年期'; 	          
	private long consignClientID = -1; //is '委托方客户ID';               
	private String consignerName = ""; //is '委托方客户号', 供页面显示用;  
	private long grantStatusID = -1; //is '发放状态';
	private long isKeepAccount = -1; //is '是否记账';
	private long isSave = -1; //is '是否保存';
	private long accountStatusID = -1; //is '账户状态';	  
	private long loanStatusID = -1; //is '贷款状态'; 
	private long isClearNull = -1; //is '是否滤空';
	private long statusID = -1; //is '记录状态';
	private long interestFeeSettingID = -1; //对应设置条件ID 
	private long accountID = -1; //主账户ID        
	private long subAccountID = -1; //子账户ID  
	private long interestType = -1; //利息类型    
	private Timestamp interestDate = null; //is '结息日期';  

	/**
	 * Constructor for QueryInterestFeeSettingDetailInfo.
	 */
	public QueryInterestFeeSettingDetailInfo()
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
	 * Returns the accountStatusID.
	 * @return long
	 */
	public long getAccountStatusID()
	{
		return accountStatusID;
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
	 * Returns the grantStatusID.
	 * @return long
	 */
	public long getGrantStatusID()
	{
		return grantStatusID;
	}

	/**
	 * Returns the isClearNull.
	 * @return long
	 */
	public long getIsClearNull()
	{
		return isClearNull;
	}

	/**
	 * Returns the isKeepAccount.
	 * @return long
	 */
	public long getIsKeepAccount()
	{
		return isKeepAccount;
	}

	/**
	 * Returns the isSave.
	 * @return long
	 */
	public long getIsSave()
	{
		return isSave;
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
	public String getLoanIntervalNumYear()
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
	 * Returns the loanStatusID.
	 * @return long
	 */
	public long getLoanStatusID()
	{
		return loanStatusID;
	}

	/**
	 * Returns the loanTypeID.
	 * @return long
	 */
	public long getLoanTypeID()
	{
		return loanTypeID;
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
	 * Sets the accountStatusID.
	 * @param accountStatusID The accountStatusID to set
	 */
	public void setAccountStatusID(long accountStatusID)
	{
		this.accountStatusID = accountStatusID;
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
	 * Sets the grantStatusID.
	 * @param grantStatusID The grantStatusID to set
	 */
	public void setGrantStatusID(long grantStatusID)
	{
		this.grantStatusID = grantStatusID;
	}

	/**
	 * Sets the isClearNull.
	 * @param isClearNull The isClearNull to set
	 */
	public void setIsClearNull(long isClearNull)
	{
		this.isClearNull = isClearNull;
	}

	/**
	 * Sets the isKeepAccount.
	 * @param isKeepAccount The isKeepAccount to set
	 */
	public void setIsKeepAccount(long isKeepAccount)
	{
		this.isKeepAccount = isKeepAccount;
	}

	/**
	 * Sets the isSave.
	 * @param isSave The isSave to set
	 */
	public void setIsSave(long isSave)
	{
		this.isSave = isSave;
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
	public void setLoanIntervalNumYear(String loanIntervalNumYear)
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
	 * Sets the loanStatusID.
	 * @param loanStatusID The loanStatusID to set
	 */
	public void setLoanStatusID(long loanStatusID)
	{
		this.loanStatusID = loanStatusID;
	}

	/**
	 * Sets the loanTypeID.
	 * @param loanTypeID The loanTypeID to set
	 */
	public void setLoanTypeID(long loanTypeID)
	{
		this.loanTypeID = loanTypeID;
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

	/**
	 * Returns the interestFeeSettingID.
	 * @return long
	 */
	public long getInterestFeeSettingID()
	{
		return interestFeeSettingID;
	}

	/**
	 * Sets the interestFeeSettingID.
	 * @param interestFeeSettingID The interestFeeSettingID to set
	 */
	public void setInterestFeeSettingID(long interestFeeSettingID)
	{
		this.interestFeeSettingID = interestFeeSettingID;
	}

	/**
	 * Returns the interestType.
	 * @return long
	 */
	public long getInterestType()
	{
		return interestType;
	}

	/**
	 * Sets the interestType.
	 * @param interestType The interestType to set
	 */
	public void setInterestType(long interestType)
	{
		this.interestType = interestType;
	}

	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return accountID;
	}

	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getSubAccountID()
	{
		return subAccountID;
	}

	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		this.accountID = accountID;
	}

	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID)
	{
		this.subAccountID = subAccountID;
	}

	/**
	 * Returns the interestDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestDate()
	{
		return interestDate;
	}

	/**
	 * Sets the interestDate.
	 * @param interestDate The interestDate to set
	 */
	public void setInterestDate(Timestamp interestDate)
	{
		this.interestDate = interestDate;
	}

}

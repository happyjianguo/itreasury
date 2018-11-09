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
public class InterestFeeSettingDetailInfo implements Serializable
{
	private long ID = -1; //主键              
	private long officeID = -1; //机构ID            
	private long currencyID = -1; //币种ID                   
	private long accountID = -1; //主账户ID        
	private long subAccountID = -1; //子账户ID   
	private long interestType = -1; //利息类型          
	private long operationType = -1; //操作类型                    
	private Timestamp interestStartDate = null; //起息日            
	private Timestamp interestEndDate = null; //终息日            
	private long interestDays = -1; //计息天数          
	private double baseBalance = 0.0; //计息金额          
	private double rate = 0.0; //执行利率          
	private double pecisionInterest = 0.0; //精确的利息值      
	private double interest = 0.0; //利息值            
	private double negotiateBalance = 0.0; //协定存款计息金额  
	private double negotiateRate = 0.0; //协定利率          
	private double negotiatePecisionInterest = 0.0; //精确的协定利息值  
	private double negotiateInterest = 0.0; //协定利息值        
	private double interestTaxRate = 0.0; //利息税率          
	private double interestTax = 0.0; //利息税                
	private long isSave = -1; //是否保存/是否结息 
	private long isKeepAccount = -1; //是否记账          
	private long isSuccess = -1; //执行是否成功      
	private String faultReason = ""; //失败原因          
	private long interestFeeSettingID = -1; //对应条件ID  
	private long statusID = -1; //状态   
	
	//算息时用
	private long accountTypeID = -1; //账户类型 
	
	//辅助列表页面显示用属性
	private String accountNo = "";//账户号
	private String accountName = ""; //账户名称
	private String contractNo = ""; //合同号
	private String loanNotesNo = ""; //借据号
	private long loanTypeID = -1;//贷款类型
	private String loanIntervalNumMonth = "";//贷款期限
	private String loanIntervalNumYear = ""; //贷款年期
	private long consignClientID = -1;//委托方id
	
	/**
	 * Constructor for InterestFeeSettingDetailInfo.
	 */
	public InterestFeeSettingDetailInfo()
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
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return accountID;
	}

	/**
	 * Returns the baseBalance.
	 * @return double
	 */
	public double getBaseBalance()
	{
		return baseBalance;
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
	 * Returns the faultReason.
	 * @return String
	 */
	public String getFaultReason()
	{
		return faultReason;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return interest;
	}

	/**
	 * Returns the interestDays.
	 * @return long
	 */
	public long getInterestDays()
	{
		return interestDays;
	}

	/**
	 * Returns the interestEndDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestEndDate()
	{
		return interestEndDate;
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
	 * Returns the interestStartDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestStartDate()
	{
		return interestStartDate;
	}

	/**
	 * Returns the interestTax.
	 * @return double
	 */
	public double getInterestTax()
	{
		return interestTax;
	}

	/**
	 * Returns the interestTaxRate.
	 * @return double
	 */
	public double getInterestTaxRate()
	{
		return interestTaxRate;
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
	 * Returns the isSuccess.
	 * @return long
	 */
	public long getIsSuccess()
	{
		return isSuccess;
	}

	/**
	 * Returns the negotiateBalance.
	 * @return double
	 */
	public double getNegotiateBalance()
	{
		return negotiateBalance;
	}

	/**
	 * Returns the negotiateInterest.
	 * @return double
	 */
	public double getNegotiateInterest()
	{
		return negotiateInterest;
	}

	/**
	 * Returns the negotiatePecisionInterest.
	 * @return double
	 */
	public double getNegotiatePecisionInterest()
	{
		return negotiatePecisionInterest;
	}

	/**
	 * Returns the negotiateRate.
	 * @return double
	 */
	public double getNegotiateRate()
	{
		return negotiateRate;
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
	 * Returns the operationType.
	 * @return long
	 */
	public long getOperationType()
	{
		return operationType;
	}

	/**
	 * Returns the pecisionInterest.
	 * @return double
	 */
	public double getPecisionInterest()
	{
		return pecisionInterest;
	}

	/**
	 * Returns the rate.
	 * @return double
	 */
	public double getRate()
	{
		return rate;
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
	 * Sets the baseBalance.
	 * @param baseBalance The baseBalance to set
	 */
	public void setBaseBalance(double baseBalance)
	{
		this.baseBalance = baseBalance;
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
	 * Sets the faultReason.
	 * @param faultReason The faultReason to set
	 */
	public void setFaultReason(String faultReason)
	{
		this.faultReason = faultReason;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}

	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		this.interest = interest;
	}

	/**
	 * Sets the interestDays.
	 * @param interestDays The interestDays to set
	 */
	public void setInterestDays(long interestDays)
	{
		this.interestDays = interestDays;
	}

	/**
	 * Sets the interestEndDate.
	 * @param interestEndDate The interestEndDate to set
	 */
	public void setInterestEndDate(Timestamp interestEndDate)
	{
		this.interestEndDate = interestEndDate;
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
	 * Sets the interestStartDate.
	 * @param interestStartDate The interestStartDate to set
	 */
	public void setInterestStartDate(Timestamp interestStartDate)
	{
		this.interestStartDate = interestStartDate;
	}

	/**
	 * Sets the interestTax.
	 * @param interestTax The interestTax to set
	 */
	public void setInterestTax(double interestTax)
	{
		this.interestTax = interestTax;
	}

	/**
	 * Sets the interestTaxRate.
	 * @param interestTaxRate The interestTaxRate to set
	 */
	public void setInterestTaxRate(double interestTaxRate)
	{
		this.interestTaxRate = interestTaxRate;
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
	 * Sets the isSuccess.
	 * @param isSuccess The isSuccess to set
	 */
	public void setIsSuccess(long isSuccess)
	{
		this.isSuccess = isSuccess;
	}

	/**
	 * Sets the negotiateBalance.
	 * @param negotiateBalance The negotiateBalance to set
	 */
	public void setNegotiateBalance(double negotiateBalance)
	{
		this.negotiateBalance = negotiateBalance;
	}

	/**
	 * Sets the negotiateInterest.
	 * @param negotiateInterest The negotiateInterest to set
	 */
	public void setNegotiateInterest(double negotiateInterest)
	{
		this.negotiateInterest = negotiateInterest;
	}

	/**
	 * Sets the negotiatePecisionInterest.
	 * @param negotiatePecisionInterest The negotiatePecisionInterest to set
	 */
	public void setNegotiatePecisionInterest(double negotiatePecisionInterest)
	{
		this.negotiatePecisionInterest = negotiatePecisionInterest;
	}

	/**
	 * Sets the negotiateRate.
	 * @param negotiateRate The negotiateRate to set
	 */
	public void setNegotiateRate(double negotiateRate)
	{
		this.negotiateRate = negotiateRate;
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
	 * Sets the operationType.
	 * @param operationType The operationType to set
	 */
	public void setOperationType(long operationType)
	{
		this.operationType = operationType;
	}

	/**
	 * Sets the pecisionInterest.
	 * @param pecisionInterest The pecisionInterest to set
	 */
	public void setPecisionInterest(double pecisionInterest)
	{
		this.pecisionInterest = pecisionInterest;
	}

	/**
	 * Sets the rate.
	 * @param rate The rate to set
	 */
	public void setRate(double rate)
	{
		this.rate = rate;
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
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID)
	{
		this.subAccountID = subAccountID;
	}

	/**
	 * Returns the accountName.
	 * @return String
	 */
	public String getAccountName()
	{
		return accountName;
	}

	/**
	 * Returns the accountNo.
	 * @return String
	 */
	public String getAccountNo()
	{
		return accountNo;
	}

	/**
	 * Returns the contractNo.
	 * @return String
	 */
	public String getContractNo()
	{
		return contractNo;
	}

	/**
	 * Returns the loanIntervalNumMonth.
	 * @return String
	 */
	public String getLoanIntervalNumMonth()
	{
		return loanIntervalNumMonth;
	}

	/**
	 * Returns the loanIntervalNumYear.
	 * @return String
	 */
	public String getLoanIntervalNumYear()
	{
		return loanIntervalNumYear;
	}

	/**
	 * Returns the loanNotesNo.
	 * @return String
	 */
	public String getLoanNotesNo()
	{
		return loanNotesNo;
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
	 * Sets the accountName.
	 * @param accountName The accountName to set
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	/**
	 * Sets the accountNo.
	 * @param accountNo The accountNo to set
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}

	/**
	 * Sets the contractNo.
	 * @param contractNo The contractNo to set
	 */
	public void setContractNo(String contractNo)
	{
		this.contractNo = contractNo;
	}

	/**
	 * Sets the loanIntervalNumMonth.
	 * @param loanIntervalNumMonth The loanIntervalNumMonth to set
	 */
	public void setLoanIntervalNumMonth(String loanIntervalNumMonth)
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
	 * Sets the loanNotesNo.
	 * @param loanNotesNo The loanNotesNo to set
	 */
	public void setLoanNotesNo(String loanNotesNo)
	{
		this.loanNotesNo = loanNotesNo;
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
	 * Returns the consignClientID.
	 * @return long
	 */
	public long getConsignClientID()
	{
		return consignClientID;
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
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID()
	{
		return accountTypeID;
	}

	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		this.accountTypeID = accountTypeID;
	}

}

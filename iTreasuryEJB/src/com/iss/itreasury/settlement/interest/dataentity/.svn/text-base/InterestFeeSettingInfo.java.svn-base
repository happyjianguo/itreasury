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
public class InterestFeeSettingInfo implements Serializable
{
	private long ID = -1; //is '主键';                            
	private long officeID = -1; //is '机构ID';                          
	private long currencyID = -1; //is '币种ID';                   
	private Timestamp calculateDate = null; //is '计算日期时间';                    
	private Timestamp interestDate = null; //is '结息日期';                     
	private Timestamp executeDate = null; //is '执行日期';                      
	private long interestType = -1; //is '利息类型';                  
	private long accountStatusID = -1; //is '账户状态';               
	private long accountIDStart = -1; //is '起始账户ID';                
	private String accountNoStart = ""; //is '起始账户号';               
	private long accountIDEnd = -1; //is '终止账户ID';                  
	private String accountNoEnd = ""; //is '终止账户号';                  
	private long fixedDepositIDStart = -1; //is '起始存单ID';           
	private String fixedDepositFormNoStart = ""; //is '起始存单号';       
	private long fixedDepositIDEnd = -1; //is '终止存单ID';             
	private String fixedDepositFormNoEnd = ""; //is '终止存单号';         
	private long fixedDepositIntervalNum = -1; //is '定期期限';       
	private long contractIDStart = -1; //is '起始合同ID';               
	private String contractNoStart = ""; //is '起始合同号';               
	private long contractIDEnd = -1; //is '终止合同ID';                 
	private String contractNoEnd = ""; //is '终止合同号';                 
	private long loanNoteIDStart = -1; //is '起始放款通知单ID';               
	private String loanNotesNoStart = ""; //is '起始放款通知单号';              
	private long loanNoteIDEnd = -1; //is '终止放款通知单ID';                 
	private String loanNotesNoEnd = ""; //is '终止放款通知单号';                
	private long loanTypeID = -1; //is '贷款种类';                    
	private long loanIntervalNumMonth = -1; //is '贷款期限';          
	private long loanIntervalNumYear = -1; //is '贷款年期';           
	private long consignClientID = -1; //is '委托方客户ID';               
	private String consignerName = ""; //is '委托方客户号';                 
	private long loanStatusID = -1; //is '贷款状态';                  
	private long isClear = -1; //is '是否滤空';                       
	private long inputUserID = -1; //is '录入人';                   
	private Timestamp inputDate = null; //is '录入日期';                        
	private long isExecute = -1; //is '是否已执行';  
	private long statusID = -1; //is '状态';                      

	/**
	 * Constructor for TransInterestFeeInfo.
	 */
	public InterestFeeSettingInfo()
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
	 * Returns the accountIDEnd.
	 * @return long
	 */
	public long getAccountIDEnd()
	{
		return accountIDEnd;
	}

	/**
	 * Returns the accountIDStart.
	 * @return long
	 */
	public long getAccountIDStart()
	{
		return accountIDStart;
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
	 * Returns the calculateDate.
	 * @return Timestamp
	 */
	public Timestamp getCalculateDate()
	{
		return calculateDate;
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
	 * Returns the contractIDEnd.
	 * @return long
	 */
	public long getContractIDEnd()
	{
		return contractIDEnd;
	}

	/**
	 * Returns the contractIDStart.
	 * @return long
	 */
	public long getContractIDStart()
	{
		return contractIDStart;
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
	 * Returns the executeDate.
	 * @return Timestamp
	 */
	public Timestamp getExecuteDate()
	{
		return executeDate;
	}

	/**
	 * Returns the fixedDepositFormNoEnd.
	 * @return String
	 */
	public String getFixedDepositFormNoEnd()
	{
		return fixedDepositFormNoEnd;
	}

	/**
	 * Returns the fixedDepositFormNoStart.
	 * @return String
	 */
	public String getFixedDepositFormNoStart()
	{
		return fixedDepositFormNoStart;
	}

	/**
	 * Returns the fixedDepositIDEnd.
	 * @return long
	 */
	public long getFixedDepositIDEnd()
	{
		return fixedDepositIDEnd;
	}

	/**
	 * Returns the fixedDepositIDStart.
	 * @return long
	 */
	public long getFixedDepositIDStart()
	{
		return fixedDepositIDStart;
	}

	/**
	 * Returns the fixedDepositIntervalNum.
	 * @return long
	 */
	public long getFixedDepositIntervalNum()
	{
		return fixedDepositIntervalNum;
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
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return inputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return inputUserID;
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
	 * Returns the interestType.
	 * @return long
	 */
	public long getInterestType()
	{
		return interestType;
	}

	/**
	 * Returns the isClear.
	 * @return long
	 */
	public long getIsClear()
	{
		return isClear;
	}

	/**
	 * Returns the isExecute.
	 * @return long
	 */
	public long getIsExecute()
	{
		return isExecute;
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
	 * Returns the loanNoteIDEnd.
	 * @return long
	 */
	public long getLoanNoteIDEnd()
	{
		return loanNoteIDEnd;
	}

	/**
	 * Returns the loanNoteIDStart.
	 * @return long
	 */
	public long getLoanNoteIDStart()
	{
		return loanNoteIDStart;
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
	 * Sets the accountIDEnd.
	 * @param accountIDEnd The accountIDEnd to set
	 */
	public void setAccountIDEnd(long accountIDEnd)
	{
		this.accountIDEnd = accountIDEnd;
	}

	/**
	 * Sets the accountIDStart.
	 * @param accountIDStart The accountIDStart to set
	 */
	public void setAccountIDStart(long accountIDStart)
	{
		this.accountIDStart = accountIDStart;
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
	 * Sets the calculateDate.
	 * @param calculateDate The calculateDate to set
	 */
	public void setCalculateDate(Timestamp calculateDate)
	{
		this.calculateDate = calculateDate;
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
	 * Sets the contractIDEnd.
	 * @param contractIDEnd The contractIDEnd to set
	 */
	public void setContractIDEnd(long contractIDEnd)
	{
		this.contractIDEnd = contractIDEnd;
	}

	/**
	 * Sets the contractIDStart.
	 * @param contractIDStart The contractIDStart to set
	 */
	public void setContractIDStart(long contractIDStart)
	{
		this.contractIDStart = contractIDStart;
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
	 * Sets the executeDate.
	 * @param executeDate The executeDate to set
	 */
	public void setExecuteDate(Timestamp executeDate)
	{
		this.executeDate = executeDate;
	}

	/**
	 * Sets the fixedDepositFormNoEnd.
	 * @param fixedDepositFormNoEnd The fixedDepositFormNoEnd to set
	 */
	public void setFixedDepositFormNoEnd(String fixedDepositFormNoEnd)
	{
		this.fixedDepositFormNoEnd = fixedDepositFormNoEnd;
	}

	/**
	 * Sets the fixedDepositFormNoStart.
	 * @param fixedDepositFormNoStart The fixedDepositFormNoStart to set
	 */
	public void setFixedDepositFormNoStart(String fixedDepositFormNoStart)
	{
		this.fixedDepositFormNoStart = fixedDepositFormNoStart;
	}

	/**
	 * Sets the fixedDepositIDEnd.
	 * @param fixedDepositIDEnd The fixedDepositIDEnd to set
	 */
	public void setFixedDepositIDEnd(long fixedDepositIDEnd)
	{
		this.fixedDepositIDEnd = fixedDepositIDEnd;
	}

	/**
	 * Sets the fixedDepositIDStart.
	 * @param fixedDepositIDStart The fixedDepositIDStart to set
	 */
	public void setFixedDepositIDStart(long fixedDepositIDStart)
	{
		this.fixedDepositIDStart = fixedDepositIDStart;
	}

	/**
	 * Sets the fixedDepositIntervalNum.
	 * @param fixedDepositIntervalNum The fixedDepositIntervalNum to set
	 */
	public void setFixedDepositIntervalNum(long fixedDepositIntervalNum)
	{
		this.fixedDepositIntervalNum = fixedDepositIntervalNum;
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
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		this.inputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		this.inputUserID = inputUserID;
	}

	/**
	 * Sets the interestDate.
	 * @param interestDate The interestDate to set
	 */
	public void setInterestDate(Timestamp interestDate)
	{
		this.interestDate = interestDate;
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
	 * Sets the isClear.
	 * @param isClear The isClear to set
	 */
	public void setIsClear(long isClear)
	{
		this.isClear = isClear;
	}

	/**
	 * Sets the isExecute.
	 * @param isExecute The isExecute to set
	 */
	public void setIsExecute(long isExecute)
	{
		this.isExecute = isExecute;
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
	 * Sets the loanNoteIDEnd.
	 * @param loanNoteIDEnd The loanNoteIDEnd to set
	 */
	public void setLoanNoteIDEnd(long loanNoteIDEnd)
	{
		this.loanNoteIDEnd = loanNoteIDEnd;
	}

	/**
	 * Sets the loanNoteIDStart.
	 * @param loanNoteIDStart The loanNoteIDStart to set
	 */
	public void setLoanNoteIDStart(long loanNoteIDStart)
	{
		this.loanNoteIDStart = loanNoteIDStart;
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
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return statusID;
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

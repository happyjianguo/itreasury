/*
 * Created on 2004-10-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.settadjustinterestrate.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 * @author jinchen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SettAdjustInterestConditionInfo extends SettlementBaseDataEntity
{
    
    private long lID;                    //贷款条件标示
    private long lInterestID;            //贷款利率标示
    private String strInterestNo;        //贷款利率标号
    private String strInterestName;      //贷款利率名称
    private double dInterestRate;        //银行利率
    private Timestamp tsUseDate;         //生效日起

    private long lContractID;            //合同标示
    private String strContractNo;         //合同编号
    private long lLoanType;              //贷款类型
    private long lConsignClientID;     //委托单位标示
    private String strConsignClientName; //委托单位名称
    private long lPeriod;                  //贷款期限
    private String strAdjustReason;        //利率调整原因
    private String strRefuseReason;        //取消复核原因
    private long lInputUserID;			  //录入人ID
    private String strInputUserName;       //录入人姓名
    private Timestamp tsInputDate;         //录入日期
    private long lCheckUserID;           //利率调整复核人ID
    private String strCheckUserName;      //利率调整复核人姓名
    private Timestamp tsCheck;           //利率调整复核时间
    private long lOfficeID;              //办事处标识

    //Fan Yi Add in 8.27
    private String strClientName;			//借款单位
    private double mExamineAmount;			//贷款金额
    private double mBalance;					//贷款余额
    private double fInterestRate;				//贷款利率(合同的，以前的)
    private Timestamp tsStartDate;			//贷款开始日期
    private Timestamp tsEndDate;				//贷款结束日期
    private long lIntervalNum;				//贷款期限

    private long lBankLoanInterestRateID;		//贷款利率标示
    private String strBankLoanInterestRateNo;	//贷款利率编号
    private String strBankLoanInterestRateName;	//贷款利率名称
    private double mRate;							//利率值(现在的)
    private double dAdjustRate;					//浮动利率（现在的）
    private double dStaidAdjustRate;				//固定浮动点（现在的）
    private Timestamp tsValidate;				//合同利率调整生效日
	private Timestamp tsRateValidate;            //利率生效日
    private String strReason;					//利率调整原因
	private long lStatusID;						//利率调整状态

	//Fan Yi Add in 1.3
	private long lLoanPayNoticeID;			//放款通知单标示
	
	private long lNextCheckLevel = -1;		//下一个审核级别

	private long lCount;

    /**
     * @return Returns the adjustRate.
     */
    public double getAdjustRate()
    {
        return dAdjustRate;
    }
    /**
     * @param adjustRate The adjustRate to set.
     */
    public void setAdjustRate(double adjustRate)
    {
        dAdjustRate = adjustRate;
    }
    /**
     * @return Returns the interestRate.
     */
    public double getInterestRate()
    {
        return dInterestRate;
    }
    /**
     * @param interestRate The interestRate to set.
     */
    public void setInterestRate(double interestRate)
    {
        dInterestRate = interestRate;
    }
    /**
     * @return Returns the staidAdjustRate.
     */
    public double getStaidAdjustRate()
    {
        return dStaidAdjustRate;
    }
    /**
     * @param staidAdjustRate The staidAdjustRate to set.
     */
    public void setStaidAdjustRate(double staidAdjustRate)
    {
        dStaidAdjustRate = staidAdjustRate;
    }
    /**
     * @return Returns the fInterestRate.
     */
    public double getFInterestRate()
    {
        return fInterestRate;
    }
    /**
     * @param interestRate The fInterestRate to set.
     */
    public void setFInterestRate(double interestRate)
    {
        fInterestRate = interestRate;
    }
    /**
     * @return Returns the fRate.
     */
    public double getRate()
    {
        return mRate;
    }
    /**
     * @param rate The fRate to set.
     */
    public void setRate(double rate)
    {
        mRate = rate;
    }
    /**
     * @return Returns the bankLoanInterestRateID.
     */
    public long getBankLoanInterestRateID()
    {
        return lBankLoanInterestRateID;
    }
    /**
     * @param bankLoanInterestRateID The bankLoanInterestRateID to set.
     */
    public void setBankLoanInterestRateID(long bankLoanInterestRateID)
    {
        lBankLoanInterestRateID = bankLoanInterestRateID;
    }
    /**
     * @return Returns the checkUserID.
     */
    public long getCheckUserID()
    {
        return lCheckUserID;
    }
    /**
     * @param checkUserID The checkUserID to set.
     */
    public void setCheckUserID(long checkUserID)
    {
        lCheckUserID = checkUserID;
    }
    /**
     * @return Returns the consignClientID.
     */
    public long getConsignClientID()
    {
        return lConsignClientID;
    }
    /**
     * @param consignClientID The consignClientID to set.
     */
    public void setConsignClientID(long consignClientID)
    {
        lConsignClientID = consignClientID;
    }
    /**
     * @return Returns the contractID.
     */
    public long getContractID()
    {
        return lContractID;
    }
    /**
     * @param contractID The contractID to set.
     */
    public void setContractID(long contractID)
    {
        lContractID = contractID;
    }
    /**
     * @return Returns the count.
     */
    public long getCount()
    {
        return lCount;
    }
    /**
     * @param count The count to set.
     */
    public void setCount(long count)
    {
        lCount = count;
    }
    /**
     * @return Returns the iD.
     */
    public long getID()
    {
        return lID;
    }
    /**
     * @param id The iD to set.
     */
    public void setID(long id)
    {
        lID = id;
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return lInputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        lInputUserID = inputUserID;
    }
    /**
     * @return Returns the interestID.
     */
    public long getInterestID()
    {
        return lInterestID;
    }
    /**
     * @param interestID The interestID to set.
     */
    public void setInterestID(long interestID)
    {
        lInterestID = interestID;
    }
    /**
     * @return Returns the intervalNum.
     */
    public long getIntervalNum()
    {
        return lIntervalNum;
    }
    /**
     * @param intervalNum The intervalNum to set.
     */
    public void setIntervalNum(long intervalNum)
    {
        lIntervalNum = intervalNum;
    }
    /**
     * @return Returns the loanPayNoticeID.
     */
    public long getLoanPayNoticeID()
    {
        return lLoanPayNoticeID;
    }
    /**
     * @param loanPayNoticeID The loanPayNoticeID to set.
     */
    public void setLoanPayNoticeID(long loanPayNoticeID)
    {
        lLoanPayNoticeID = loanPayNoticeID;
    }
    /**
     * @return Returns the loanType.
     */
    public long getLoanType()
    {
        return lLoanType;
    }
    /**
     * @param loanType The loanType to set.
     */
    public void setLoanType(long loanType)
    {
        lLoanType = loanType;
    }
    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return lNextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        lNextCheckLevel = nextCheckLevel;
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return lOfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        lOfficeID = officeID;
    }
    /**
     * @return Returns the period.
     */
    public long getPeriod()
    {
        return lPeriod;
    }
    /**
     * @param period The period to set.
     */
    public void setPeriod(long period)
    {
        lPeriod = period;
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return lStatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        lStatusID = statusID;
    }
    /**
     * @return Returns the balance.
     */
    public double getBalance()
    {
        return mBalance;
    }
    /**
     * @param balance The balance to set.
     */
    public void setBalance(double balance)
    {
        mBalance = balance;
    }
    /**
     * @return Returns the examineAmount.
     */
    public double getExamineAmount()
    {
        return mExamineAmount;
    }
    /**
     * @param examineAmount The examineAmount to set.
     */
    public void setExamineAmount(double examineAmount)
    {
        mExamineAmount = examineAmount;
    }
    /**
     * @return Returns the adjustReason.
     */
    public String getAdjustReason()
    {
        return strAdjustReason;
    }
    /**
     * @param adjustReason The adjustReason to set.
     */
    public void setAdjustReason(String adjustReason)
    {
        strAdjustReason = adjustReason;
    }
    /**
     * @return Returns the bankLoanInterestRateName.
     */
    public String getBankLoanInterestRateName()
    {
        return strBankLoanInterestRateName;
    }
    /**
     * @param bankLoanInterestRateName The bankLoanInterestRateName to set.
     */
    public void setBankLoanInterestRateName(String bankLoanInterestRateName)
    {
        strBankLoanInterestRateName = bankLoanInterestRateName;
    }
    /**
     * @return Returns the bankLoanInterestRateNo.
     */
    public String getBankLoanInterestRateNo()
    {
        return strBankLoanInterestRateNo;
    }
    /**
     * @param bankLoanInterestRateNo The bankLoanInterestRateNo to set.
     */
    public void setBankLoanInterestRateNo(String bankLoanInterestRateNo)
    {
        strBankLoanInterestRateNo = bankLoanInterestRateNo;
    }
    /**
     * @return Returns the checkUserName.
     */
    public String getCheckUserName()
    {
        return strCheckUserName;
    }
    /**
     * @param checkUserName The checkUserName to set.
     */
    public void setCheckUserName(String checkUserName)
    {
        strCheckUserName = checkUserName;
    }
    /**
     * @return Returns the clientName.
     */
    public String getClientName()
    {
        return strClientName;
    }
    /**
     * @param clientName The clientName to set.
     */
    public void setClientName(String clientName)
    {
        strClientName = clientName;
    }
    /**
     * @return Returns the consignClientName.
     */
    public String getConsignClientName()
    {
        return strConsignClientName;
    }
    /**
     * @param consignClientName The consignClientName to set.
     */
    public void setConsignClientName(String consignClientName)
    {
        strConsignClientName = consignClientName;
    }
    /**
     * @return Returns the contractNo.
     */
    public String getContractNo()
    {
        return strContractNo;
    }
    /**
     * @param contractNo The contractNo to set.
     */
    public void setContractNo(String contractNo)
    {
        strContractNo = contractNo;
    }
    /**
     * @return Returns the inputUserName.
     */
    public String getInputUserName()
    {
        return strInputUserName;
    }
    /**
     * @param inputUserName The inputUserName to set.
     */
    public void setInputUserName(String inputUserName)
    {
        strInputUserName = inputUserName;
    }
    /**
     * @return Returns the interestName.
     */
    public String getInterestName()
    {
        return strInterestName;
    }
    /**
     * @param interestName The interestName to set.
     */
    public void setInterestName(String interestName)
    {
        strInterestName = interestName;
    }
    /**
     * @return Returns the interestNo.
     */
    public String getInterestNo()
    {
        return strInterestNo;
    }
    /**
     * @param interestNo The interestNo to set.
     */
    public void setInterestNo(String interestNo)
    {
        strInterestNo = interestNo;
    }
    /**
     * @return Returns the reason.
     */
    public String getReason()
    {
        return strReason;
    }
    /**
     * @param reason The reason to set.
     */
    public void setReason(String reason)
    {
        strReason = reason;
    }
    /**
     * @return Returns the refuseReason.
     */
    public String getRefuseReason()
    {
        return strRefuseReason;
    }
    /**
     * @param refuseReason The refuseReason to set.
     */
    public void setRefuseReason(String refuseReason)
    {
        strRefuseReason = refuseReason;
    }
    /**
     * @return Returns the check.
     */
    public Timestamp getCheck()
    {
        return tsCheck;
    }
    /**
     * @param check The check to set.
     */
    public void setCheck(Timestamp check)
    {
        tsCheck = check;
    }
    /**
     * @return Returns the endDate.
     */
    public Timestamp getEndDate()
    {
        return tsEndDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        tsEndDate = endDate;
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return tsInputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        tsInputDate = inputDate;
    }
    /**
     * @return Returns the rateValidate.
     */
    public Timestamp getRateValidate()
    {
        return tsRateValidate;
    }
    /**
     * @param rateValidate The rateValidate to set.
     */
    public void setRateValidate(Timestamp rateValidate)
    {
        tsRateValidate = rateValidate;
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return tsStartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        tsStartDate = startDate;
    }
    /**
     * @return Returns the useDate.
     */
    public Timestamp getUseDate()
    {
        return tsUseDate;
    }
    /**
     * @param useDate The useDate to set.
     */
    public void setUseDate(Timestamp useDate)
    {
        tsUseDate = useDate;
    }
    /**
     * @return Returns the validate.
     */
    public Timestamp getValidate()
    {
        return tsValidate;
    }
    /**
     * @param validate The validate to set.
     */
    public void setValidate(Timestamp validate)
    {
        tsValidate = validate;
    }


    public static void main(String[] args)
    {
    }
}

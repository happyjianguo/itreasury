/*
 * AdjustInterestConditionInfo.java
 *
 * Created on 2002年3月11日, 上午10:13
 */

package com.iss.itreasury.loan.loaninterestsetting.dataentity;
import java.sql.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;


/**
 *
 * @author  yzhang
 * @version
 */
public class AdjustInterestConditionInfo implements java.io.Serializable {

    public double getDAdjustRate() {
		return dAdjustRate;
	}

	public void setDAdjustRate(double adjustRate) {
		dAdjustRate = adjustRate;
	}

	public double getDInterestRate() {
		return dInterestRate;
	}

	public void setDInterestRate(double interestRate) {
		dInterestRate = interestRate;
	}

	public double getDStaidAdjustRate() {
		return dStaidAdjustRate;
	}

	public void setDStaidAdjustRate(double staidAdjustRate) {
		dStaidAdjustRate = staidAdjustRate;
	}

	public double getFInterestRate() {
		return fInterestRate;
	}

	public void setFInterestRate(double interestRate) {
		fInterestRate = interestRate;
	}

	public double getFRate() {
		return fRate;
	}

	public void setFRate(double rate) {
		fRate = rate;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long getLBankLoanInterestRateID() {
		return lBankLoanInterestRateID;
	}

	public void setLBankLoanInterestRateID(long bankLoanInterestRateID) {
		lBankLoanInterestRateID = bankLoanInterestRateID;
	}

	public long getLConsignClientID() {
		return lConsignClientID;
	}

	public void setLConsignClientID(long consignClientID) {
		lConsignClientID = consignClientID;
	}

	public long getLContractID() {
		return lContractID;
	}

	public void setLContractID(long contractID) {
		lContractID = contractID;
	}

	public long getLCount() {
		return lCount;
	}

	public void setLCount(long count) {
		lCount = count;
	}

	public long getLID() {
		return lID;
	}

	public void setLID(long lid) {
		lID = lid;
	}

	public long getLInputUserID() {
		return lInputUserID;
	}

	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}

	public long getLInterestID() {
		return lInterestID;
	}

	public void setLInterestID(long interestID) {
		lInterestID = interestID;
	}

	public long getLIntervalNum() {
		return lIntervalNum;
	}

	public void setLIntervalNum(long intervalNum) {
		lIntervalNum = intervalNum;
	}

	public long getLLoanPayNoticeID() {
		return lLoanPayNoticeID;
	}

	public void setLLoanPayNoticeID(long loanPayNoticeID) {
		lLoanPayNoticeID = loanPayNoticeID;
	}

	public long getLLoanType() {
		return lLoanType;
	}

	public void setLLoanType(long loanType) {
		lLoanType = loanType;
	}

	public long getLNextCheckLevel() {
		return lNextCheckLevel;
	}

	public void setLNextCheckLevel(long nextCheckLevel) {
		lNextCheckLevel = nextCheckLevel;
	}

	public long getLPeriod() {
		return lPeriod;
	}

	public void setLPeriod(long period) {
		lPeriod = period;
	}

	public long getLStatusID() {
		return lStatusID;
	}

	public void setLStatusID(long statusID) {
		lStatusID = statusID;
	}

	public long getM_lCheckUserID() {
		return m_lCheckUserID;
	}

	public void setM_lCheckUserID(long checkUserID) {
		m_lCheckUserID = checkUserID;
	}

	public long getM_lOfficeID() {
		return m_lOfficeID;
	}

	public void setM_lOfficeID(long officeID) {
		m_lOfficeID = officeID;
	}

	public String getM_strCheckUserName() {
		return m_strCheckUserName;
	}

	public void setM_strCheckUserName(String checkUserName) {
		m_strCheckUserName = checkUserName;
	}

	public Timestamp getM_tsCheck() {
		return m_tsCheck;
	}

	public void setM_tsCheck(Timestamp check) {
		m_tsCheck = check;
	}

	public double getMBalance() {
		return mBalance;
	}

	public void setMBalance(double balance) {
		mBalance = balance;
	}

	public double getMExamineAmount() {
		return mExamineAmount;
	}

	public void setMExamineAmount(double examineAmount) {
		mExamineAmount = examineAmount;
	}

	public String getStrAdjustReason() {
		return strAdjustReason;
	}

	public void setStrAdjustReason(String strAdjustReason) {
		this.strAdjustReason = strAdjustReason;
	}

	public String getStrBankLoanInterestRateName() {
		return strBankLoanInterestRateName;
	}

	public void setStrBankLoanInterestRateName(String strBankLoanInterestRateName) {
		this.strBankLoanInterestRateName = strBankLoanInterestRateName;
	}

	public String getStrBankLoanInterestRateNo() {
		return strBankLoanInterestRateNo;
	}

	public void setStrBankLoanInterestRateNo(String strBankLoanInterestRateNo) {
		this.strBankLoanInterestRateNo = strBankLoanInterestRateNo;
	}

	public String getStrClientName() {
		return strClientName;
	}

	public void setStrClientName(String strClientName) {
		this.strClientName = strClientName;
	}

	public String getStrConsignClientName() {
		return strConsignClientName;
	}

	public void setStrConsignClientName(String strConsignClientName) {
		this.strConsignClientName = strConsignClientName;
	}

	public String getStrContractNo() {
		return strContractNo;
	}

	public void setStrContractNo(String strContractNo) {
		this.strContractNo = strContractNo;
	}

	public String getStrInputUserName() {
		return strInputUserName;
	}

	public void setStrInputUserName(String strInputUserName) {
		this.strInputUserName = strInputUserName;
	}

	public String getStrInterestName() {
		return strInterestName;
	}

	public void setStrInterestName(String strInterestName) {
		this.strInterestName = strInterestName;
	}

	public String getStrInterestNo() {
		return strInterestNo;
	}

	public void setStrInterestNo(String strInterestNo) {
		this.strInterestNo = strInterestNo;
	}

	public String getStrReason() {
		return strReason;
	}

	public void setStrReason(String strReason) {
		this.strReason = strReason;
	}

	public String getStrRefuseReason() {
		return strRefuseReason;
	}

	public void setStrRefuseReason(String strRefuseReason) {
		this.strRefuseReason = strRefuseReason;
	}

	public Timestamp getTsEndDate() {
		return tsEndDate;
	}

	public void setTsEndDate(Timestamp tsEndDate) {
		this.tsEndDate = tsEndDate;
	}

	public Timestamp getTsInputDate() {
		return tsInputDate;
	}

	public void setTsInputDate(Timestamp tsInputDate) {
		this.tsInputDate = tsInputDate;
	}

	public Timestamp getTsRateValidate() {
		return tsRateValidate;
	}

	public void setTsRateValidate(Timestamp tsRateValidate) {
		this.tsRateValidate = tsRateValidate;
	}

	public Timestamp getTsStartDate() {
		return tsStartDate;
	}

	public void setTsStartDate(Timestamp tsStartDate) {
		this.tsStartDate = tsStartDate;
	}

	public Timestamp getTsUseDate() {
		return tsUseDate;
	}

	public void setTsUseDate(Timestamp tsUseDate) {
		this.tsUseDate = tsUseDate;
	}

	public Timestamp getTsValidate() {
		return tsValidate;
	}

	public void setTsValidate(Timestamp tsValidate) {
		this.tsValidate = tsValidate;
	}

	public AdjustInterestConditionInfo() {
	    super();
    }

    public long lID;                    //贷款条件标示
    public long lInterestID;            //贷款利率标示
   
    public String strInterestNo;        //贷款利率标号
    public String strInterestName;      //贷款利率名称
    public double dInterestRate;        //银行利率
    public Timestamp tsUseDate;         //生效日起
    public long lCurrency;              //币种
    public long lContractID;			//合同标示
    public String strContractNo;         //合同编号
    public long lLoanType;              //贷款类型
    public long lConsignClientID;     //委托单位标示
   
    public String strConsignClientName; //委托单位名称
    public long lPeriod;                  //贷款期限
    public String strAdjustReason;        //利率调整原因
    public String strRefuseReason;        //取消复核原因
    public long lInputUserID;			  //录入人ID
    public String strInputUserName;       //录入人姓名
    public Timestamp tsInputDate;         //录入日期
    public long m_lCheckUserID;           //利率调整复核人ID
    public String m_strCheckUserName;      //利率调整复核人姓名
    public Timestamp m_tsCheck;           //利率调整复核时间
    public long m_lOfficeID;              //办事处标识

    //Fan Yi Add in 8.27
    public String strClientName;			//借款单位
    public double mExamineAmount;			//贷款金额
    public double mBalance;					//贷款余额
    public double fInterestRate;				//贷款利率(合同的，以前的)
    public Timestamp tsStartDate;			//贷款开始日期
    public Timestamp tsEndDate;				//贷款结束日期
    public long lIntervalNum;				//贷款期限

    public long lBankLoanInterestRateID;		//贷款利率标示
    public String strBankLoanInterestRateNo;	//贷款利率编号
    public String strBankLoanInterestRateName;	//贷款利率名称
    public double fRate;							//利率值(现在的)
    public double dAdjustRate;					//浮动利率（现在的）
    public double dStaidAdjustRate;				//固定浮动点（现在的）
    public Timestamp tsValidate;				//合同利率调整生效日
	public Timestamp tsRateValidate;            //利率生效日
    public String strReason;					//利率调整原因
	public long lStatusID;						//利率调整状态

	//Fan Yi Add in 1.3
	public long lLoanPayNoticeID;			//放款通知单标示
	
	public long lNextCheckLevel = -1;		//下一个审核级别

	public long lCount;
	
    public InutParameterInfo inutParameterInfo = null;
    public String ContractCodeFrom;     //合同编号起始
    public String ContractCodeTo;       //合同编号结束
    public long consignClientIDFrom;	//委托单位起始
    public long consignClientIDTo;		//委托单位结束
    public long borrowClientIDFrom;		//借款单位起始
    public long borrowClientIDTo;		//借款单位结束
    public String loanTypeIDsList;		//贷款状态列表
    public String loanStatusTypeIDsList;//审批状态列表
    public Timestamp tsValidateFrom;	//合同利率调整生效日起始
    public Timestamp tsValidateTo;  	//合同利率调整生效日结束
    public Timestamp tsInputDateFrom;   //录入日期起始
    public Timestamp tsInputDateTo;     //录入日期结束
    public long lLoanPayNoticeIDFrom;	//放款通知单id起始
    public long lLoanPayNoticeIDTo;	    //放款通知单id结束
    
    public String lLoanPayNoticeCodeFrom;//放款通知单code起始
    public String lLoanPayNoticeCodeTo; //放款通知单code结束
    public double tsAdjustRateFrom;		//调整后利率起始
    public double tsAdjustRateTo;		//调整后利率起始
    
    /*public Timestamp getTsRateValidate()
    {
	return tsRateValidate;
    }
    public void setTsRateValidate(Timestamp tsRateValidate)
    {
	this.tsRateValidate = tsRateValidate;
    }*/

	
	public Timestamp getTsInputDateFrom() {
		return tsInputDateFrom;
	}

	public void setTsInputDateFrom(Timestamp tsInputDateFrom) {
		this.tsInputDateFrom = tsInputDateFrom;
	}

	public Timestamp getTsInputDateTo() {
		return tsInputDateTo;
	}

	public void setTsInputDateTo(Timestamp tsInputDateTo) {
		this.tsInputDateTo = tsInputDateTo;
	}

	public long getLLoanPayNoticeIDFrom() {
		return lLoanPayNoticeIDFrom;
	}

	public void setLLoanPayNoticeIDFrom(long loanPayNoticeIDFrom) {
		lLoanPayNoticeIDFrom = loanPayNoticeIDFrom;
	}

	public long getLLoanPayNoticeIDTo() {
		return lLoanPayNoticeIDTo;
	}

	public void setLLoanPayNoticeIDTo(long loanPayNoticeIDTo) {
		lLoanPayNoticeIDTo = loanPayNoticeIDTo;
	}

	public long getLCurrency() {
		return lCurrency;
	}

	public void setLCurrency(long currency) {
		lCurrency = currency;
	}

	public String getContractCodeFrom() {
		return ContractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		ContractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return ContractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		ContractCodeTo = contractCodeTo;
	}

	public long getBorrowClientIDFrom() {
		return borrowClientIDFrom;
	}

	public void setBorrowClientIDFrom(long borrowClientIDFrom) {
		this.borrowClientIDFrom = borrowClientIDFrom;
	}

	public long getBorrowClientIDTo() {
		return borrowClientIDTo;
	}

	public void setBorrowClientIDTo(long borrowClientIDTo) {
		this.borrowClientIDTo = borrowClientIDTo;
	}

	public long getConsignClientIDFrom() {
		return consignClientIDFrom;
	}

	public void setConsignClientIDFrom(long consignClientIDFrom) {
		this.consignClientIDFrom = consignClientIDFrom;
	}

	public long getConsignClientIDTo() {
		return consignClientIDTo;
	}

	public void setConsignClientIDTo(long consignClientIDTo) {
		this.consignClientIDTo = consignClientIDTo;
	}

	public String getLoanTypeIDsList() {
		return loanTypeIDsList;
	}

	public void setLoanTypeIDsList(String loanTypeIDsList) {
		this.loanTypeIDsList = loanTypeIDsList;
	}

	public String getLoanStatusTypeIDsList() {
		return loanStatusTypeIDsList;
	}

	public void setLoanStatusTypeIDsList(String loanStatusTypeIDsList) {
		this.loanStatusTypeIDsList = loanStatusTypeIDsList;
	}

	public Timestamp getTsValidateFrom() {
		return tsValidateFrom;
	}

	public void setTsValidateFrom(Timestamp tsValidateFrom) {
		this.tsValidateFrom = tsValidateFrom;
	}

	public Timestamp getTsValidateTo() {
		return tsValidateTo;
	}

	public void setTsValidateTo(Timestamp tsValidateTo) {
		this.tsValidateTo = tsValidateTo;
	}

	public double getTsAdjustRateFrom() {
		return tsAdjustRateFrom;
	}

	public void setTsAdjustRateFrom(double tsAdjustRateFrom) {
		this.tsAdjustRateFrom = tsAdjustRateFrom;
	}

	public double getTsAdjustRateTo() {
		return tsAdjustRateTo;
	}

	public void setTsAdjustRateTo(double tsAdjustRateTo) {
		this.tsAdjustRateTo = tsAdjustRateTo;
	}

	public String getLLoanPayNoticeCodeFrom() {
		return lLoanPayNoticeCodeFrom;
	}

	public void setLLoanPayNoticeCodeFrom(String loanPayNoticeCodeFrom) {
		lLoanPayNoticeCodeFrom = loanPayNoticeCodeFrom;
	}

	public String getLLoanPayNoticeCodeTo() {
		return lLoanPayNoticeCodeTo;
	}

	public void setLLoanPayNoticeCodeTo(String loanPayNoticeCodeTo) {
		lLoanPayNoticeCodeTo = loanPayNoticeCodeTo;
	}
}

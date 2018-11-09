package com.iss.itreasury.loan.repayplan.dataentity;

import java.io.Serializable;
import java.sql.*;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RepayPlanInfo extends BaseDataEntity implements Serializable
{

	public static void main(String[] args)
	{
	}
	/**
	 * RepayPlanInfo 构造子注解。
	 */
	public RepayPlanInfo()
	{
		super();
	}

	public long lID; // 计划标示

	public long lCurrencyID; //币种标示

	public long lLoanID; //  贷款标示
	public long lVersionNo; //  版本号
	public Timestamp tsPlanDate; // 原始计划日期
	public int nLoanOrRepay; // 贷/还
	public double dAmount; //  金额
	public String sType; //  类型（本金）
	public Timestamp tsInputDate; //  新增时间/修改时间
	public double fExecuteInterestRate; //  执行利率
	public String sExecuteInterestRate; //  执行利率
	public long lContractPayPlanVersionID; // 版本标识

	public long lInputUserID; //录入人标示
	public String sInputUserName; //录入人名称
	public long lCheckUserID; //复核人标示
	public String sCheckUserName; //复核人名称
	public long lNextCheckLevel = -1;	//下一个审核级别

	public long lContractID; //  委托通知单编号
	public String sContractNo; // 委托合同标示

	public long lCount; //  记录数
	public double dPayCounter; //  放款记数
	public double dRePayCounter; //  还款记数

	public double dPlanPayAmount; //计划还款金额
	public double dPlanBalance; //计划余额
	public double dFineAmount; //罚息金额
	public Timestamp tsFineDate; //罚息日期
	public double dFineInterestRate; //罚息利率
	public long lOverdueStatusID; //逾期申请状态
	public long lOVERDUEINFONEWID; //逾期表的标识ID

	public long lUserType; //修改来源的类型
	public long lLastVersionPlanID; //对应的上一版本的计划明细标示

	public long lisOverDue; // 是否逾期
	public long lLastOverDueID; // 对应逾期标识

	public long lLastExtendID; // 对应的展期标识
	public Timestamp tsExtendStartDate; // 展期起始日期
	public Timestamp tsExtendEndDate; // 展期结束日期
	public long lExtendPeriod; //展期明细中的展期月数
	public long lExtendListID; // 展期明细标识

	public double dHiddenBalance; // 计划余额 
	public String lateRateString = "";         //LIBOR利率，字符串格式
	public double mINTERESTAMOUNT;//利息金额
	public double mRECOGNIZANCEAMOUNT;//保证金抵押额
	
	//added by xiong fei 2010-07-16
	public long issue;//期数
	
	public long getLID() {
		return lID;
	}
	public void setLID(long lid) {
		lID = lid;
	}
	public long getLCurrencyID() {
		return lCurrencyID;
	}
	public void setLCurrencyID(long currencyID) {
		lCurrencyID = currencyID;
	}
	public long getLLoanID() {
		return lLoanID;
	}
	public void setLLoanID(long loanID) {
		lLoanID = loanID;
	}
	public long getLVersionNo() {
		return lVersionNo;
	}
	public void setLVersionNo(long versionNo) {
		lVersionNo = versionNo;
	}
	public Timestamp getTsPlanDate() {
		return tsPlanDate;
	}
	public void setTsPlanDate(Timestamp tsPlanDate) {
		this.tsPlanDate = tsPlanDate;
	}
	public int getNLoanOrRepay() {
		return nLoanOrRepay;
	}
	public void setNLoanOrRepay(int loanOrRepay) {
		nLoanOrRepay = loanOrRepay;
	}
	public double getDAmount() {
		return dAmount;
	}
	public void setDAmount(double amount) {
		dAmount = amount;
	}
	public String getSType() {
		return sType;
	}
	public void setSType(String type) {
		sType = type;
	}
	public Timestamp getTsInputDate() {
		return tsInputDate;
	}
	public void setTsInputDate(Timestamp tsInputDate) {
		this.tsInputDate = tsInputDate;
	}
	public double getFExecuteInterestRate() {
		return fExecuteInterestRate;
	}
	public void setFExecuteInterestRate(double executeInterestRate) {
		fExecuteInterestRate = executeInterestRate;
	}
	public String getSExecuteInterestRate() {
		return sExecuteInterestRate;
	}
	public void setSExecuteInterestRate(String executeInterestRate) {
		sExecuteInterestRate = executeInterestRate;
	}
	public long getLContractPayPlanVersionID() {
		return lContractPayPlanVersionID;
	}
	public void setLContractPayPlanVersionID(long contractPayPlanVersionID) {
		lContractPayPlanVersionID = contractPayPlanVersionID;
	}
	public long getLInputUserID() {
		return lInputUserID;
	}
	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}
	public String getSInputUserName() {
		return sInputUserName;
	}
	public void setSInputUserName(String inputUserName) {
		sInputUserName = inputUserName;
	}
	public long getLCheckUserID() {
		return lCheckUserID;
	}
	public void setLCheckUserID(long checkUserID) {
		lCheckUserID = checkUserID;
	}
	public String getSCheckUserName() {
		return sCheckUserName;
	}
	public void setSCheckUserName(String checkUserName) {
		sCheckUserName = checkUserName;
	}
	public long getLNextCheckLevel() {
		return lNextCheckLevel;
	}
	public void setLNextCheckLevel(long nextCheckLevel) {
		lNextCheckLevel = nextCheckLevel;
	}
	public long getLContractID() {
		return lContractID;
	}
	public void setLContractID(long contractID) {
		lContractID = contractID;
	}
	public String getSContractNo() {
		return sContractNo;
	}
	public void setSContractNo(String contractNo) {
		sContractNo = contractNo;
	}
	public long getLCount() {
		return lCount;
	}
	public void setLCount(long count) {
		lCount = count;
	}
	public double getDPayCounter() {
		return dPayCounter;
	}
	public void setDPayCounter(double payCounter) {
		dPayCounter = payCounter;
	}
	public double getDRePayCounter() {
		return dRePayCounter;
	}
	public void setDRePayCounter(double rePayCounter) {
		dRePayCounter = rePayCounter;
	}
	public double getDPlanPayAmount() {
		return dPlanPayAmount;
	}
	public void setDPlanPayAmount(double planPayAmount) {
		dPlanPayAmount = planPayAmount;
	}
	public double getDPlanBalance() {
		return dPlanBalance;
	}
	public void setDPlanBalance(double planBalance) {
		dPlanBalance = planBalance;
	}
	public double getDFineAmount() {
		return dFineAmount;
	}
	public void setDFineAmount(double fineAmount) {
		dFineAmount = fineAmount;
	}
	public Timestamp getTsFineDate() {
		return tsFineDate;
	}
	public void setTsFineDate(Timestamp tsFineDate) {
		this.tsFineDate = tsFineDate;
	}
	public double getDFineInterestRate() {
		return dFineInterestRate;
	}
	public void setDFineInterestRate(double fineInterestRate) {
		dFineInterestRate = fineInterestRate;
	}
	public long getLOverdueStatusID() {
		return lOverdueStatusID;
	}
	public void setLOverdueStatusID(long overdueStatusID) {
		lOverdueStatusID = overdueStatusID;
	}
	public long getLOVERDUEINFONEWID() {
		return lOVERDUEINFONEWID;
	}
	public void setLOVERDUEINFONEWID(long loverdueinfonewid) {
		lOVERDUEINFONEWID = loverdueinfonewid;
	}
	public long getLUserType() {
		return lUserType;
	}
	public void setLUserType(long userType) {
		lUserType = userType;
	}
	public long getLLastVersionPlanID() {
		return lLastVersionPlanID;
	}
	public void setLLastVersionPlanID(long lastVersionPlanID) {
		lLastVersionPlanID = lastVersionPlanID;
	}
	public long getLisOverDue() {
		return lisOverDue;
	}
	public void setLisOverDue(long lisOverDue) {
		this.lisOverDue = lisOverDue;
	}
	public long getLLastOverDueID() {
		return lLastOverDueID;
	}
	public void setLLastOverDueID(long lastOverDueID) {
		lLastOverDueID = lastOverDueID;
	}
	public long getLLastExtendID() {
		return lLastExtendID;
	}
	public void setLLastExtendID(long lastExtendID) {
		lLastExtendID = lastExtendID;
	}
	public Timestamp getTsExtendStartDate() {
		return tsExtendStartDate;
	}
	public void setTsExtendStartDate(Timestamp tsExtendStartDate) {
		this.tsExtendStartDate = tsExtendStartDate;
	}
	public Timestamp getTsExtendEndDate() {
		return tsExtendEndDate;
	}
	public void setTsExtendEndDate(Timestamp tsExtendEndDate) {
		this.tsExtendEndDate = tsExtendEndDate;
	}
	public long getLExtendPeriod() {
		return lExtendPeriod;
	}
	public void setLExtendPeriod(long extendPeriod) {
		lExtendPeriod = extendPeriod;
	}
	public long getLExtendListID() {
		return lExtendListID;
	}
	public void setLExtendListID(long extendListID) {
		lExtendListID = extendListID;
	}
	public double getDHiddenBalance() {
		return dHiddenBalance;
	}
	public void setDHiddenBalance(double hiddenBalance) {
		dHiddenBalance = hiddenBalance;
	}
	public String getLateRateString() {
		return lateRateString;
	}
	public void setLateRateString(String lateRateString) {
		this.lateRateString = lateRateString;
	}
	public double getMINTERESTAMOUNT() {
		return mINTERESTAMOUNT;
	}
	public void setMINTERESTAMOUNT(double minterestamount) {
		mINTERESTAMOUNT = minterestamount;
	}
	public double getMRECOGNIZANCEAMOUNT() {
		return mRECOGNIZANCEAMOUNT;
	}
	public void setMRECOGNIZANCEAMOUNT(double mrecognizanceamount) {
		mRECOGNIZANCEAMOUNT = mrecognizanceamount;
	}
	public long getIssue() {
		return issue;
	}
	public void setIssue(long issue) {
		this.issue = issue;
	}
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	
}

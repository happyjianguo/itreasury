/*
 * ContractInfo.java
 *
 * Created on 2002年2月20日, 上午9:39
 */

package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 *
 * @author  yzhang
 * @version
 */
public class ContractInfo extends BaseDataEntity implements Serializable
{

	/**
	 * ContractInfo 构造子注解。
	 */
	public ContractInfo()
	{
		super();
	}

	//合同信息
	//ID:合同标识
	private long ContractID;

	//ID:合同标识
	private long LoanID;

	//nCurrencyID:币种标识
	private long CurrencyID;

	//nOfficeID:办事处标识
	private long OfficeID;

	//sContractCode:合同编号
	private String ContractCode;

	//nLoanTypeID:贷款种类
	private long LoanTypeID;
	private String LoanTypeName;
	private long SubTypeID;

	//nBorrowClientID:贷款单位ID
	private long BorrowClientID;

	//niscircle:是否循环贷款
	private long isCircle;
	//风险等级
	private long riskLevel;

	//sName:贷款单位名称
	private String BorrowClientName;

	//sName:贷款单位编号
	private String BorrowClientCode;

	//nClientID 委托单位ID
	private long ClientID;

	//sName:委托单位名称
	private String ClientName;

	//委托单位借款单位账户
	private String ClientAccount;

	//借款单位账户
	private String LoanAccount;

	//mLoanAmount 金额
	private double LoanAmount;

	//ExamineAmount 批准金额
	private double ExamineAmount;
	
	//批准财务公司承贷金额
	private double mExamineSelfAmount;

	//汇总贴现核定金额
	private double CheckAmount;

	//贴现利息
	private double DiscountInterest;

	//AssureAmount 担保金额
	private double AssureAmount;

	//AssureRate 担保金额之和占审批金额的比例
	private double AssureRate;

	//CreditRate信用贷款金额占审批金额的比例
	private double CreditRate;

	//信用贷款总额
	private double CreditAmount;

	//已发放金额
	private double AmountDone;

	//fInterestRate:利率
	private double InterestRate;

	//fInterestRate:基准利率
	private double BasicInterestRate;

	//fInterestRate:银行利率ID
	private long BankInterestID = -1;
	
	//InterestTypeID:利率类型
	private long InterestTypeID = 1;
	
	//LiborRateID:Libor利率ID
	private long LiborRateID = -1;

	//nIntervalNum:贷款期限
	private long IntervalNum;

	//nInputUserID:录入人标识 亦是合同管理人
	private long lInputUserID;

	//sInputUserName 录入人姓名
	private String InputUserName;
	private Timestamp InputDate; //合同录入日期

	//DiscountRate 贴现利率
	private double DiscountRate;

	//nStatusID:合同状态
	private long StatusID;
	private String Status;
	
	//nDraftTypeID:贴现汇票种类
	private long tsDiscountTypeID;
	private String tsDiscountType;

	

	//分页显示总页数
	private long PageCount;

	// 申请编号
	private String ApplyCode;

	//总纪录数,仅在贷款申请查询时用到
	private long AllRecord;

	//总金额
	private double AllAmount;

	//贷款余额
	private ContractAmountInfo aInfo = null;

	//合同当前余额
	private double Balance = 0;

	//指定余额日期的合同余额
	private double dailyBalance = 0;
	
	//dtLoanStart借款起始日期
	private Timestamp LoanStart;
	
	//dtDiscountDate贴现日期
	private Timestamp discountDate;

	//dtLoanStart借款结束日期
	private Timestamp LoanEnd;

	//借款用途
	private String LoanPurpose;

	//借款原因
	private String LoanReason;

	//放款日期
	private Timestamp OutDate;

	//借款单位邮编
	private String LoanZipCode;

	//借款单位电话
	private String LoanPhone;

	//借款单位地址
	private String LoanAddress;

	//合同执行计划是否可以修改
	private long IsPlanModifying = -1;

	//合同执行计划版本号ID
	private long PlanVersionID = -1;

	//是否有计划在展期之中
	private long IsOverDueModifying = -1;

	//初始利率值
	private double Rate = -1;

	//浮动利率正负标识（正1负2）
	//private long AddOrDecrease = 1;

	//浮动利率
	private double AdjustRate = 0;

	//审核人ID
	private long CheckUserID = -1;

	//审核人名称
	private String CheckUserName = "";

	//按地区分类
	private long AreaType = -1;

	//按行业分类1
	private long IndustryType1 = -1;

	//按行业分类2
	private long IndustryType2 = -1;
	
	//按行业分类3
	private long IndustryType3 = -1;

	//保证信息
	private Collection cAssure = null;

	//合同文本信息
	private Collection cContractContent = null;

	//银团信息
	private Collection cYT = null;

	//银团信息
	private YTFormatInfo YTInfo = null;
    
    //承贷比例
    private double Scale = 0;

	//合同计划修改日期
	private Timestamp PlanModifyDate = null;

	//还款来源
	private String sOther = "";

	//手续费率
	private double ChargeRate = 0;

	//手续费率类型
	private long ChargeRateType = -1;

	//信用
	private long IsCredit = -1;

	//保证
	private long IsAssure = -1;

	//抵押
	private long IsImpawn = -1;

	//质押
	private long IsPledge = -1;
	
	//保证金
    private long IsRecognizance = -1;
    
    //added by xiong fei 2010/05/25 
    //回购
    private long IsRepurchase = -1;

	//以下字段因打印需要添加
	//委托名称
	private String ConsignName = "";

	//委托住所
	private String ConsignAddress = "";

	//委托开户行
	private String ConsignBank = "";

	//委托账号
	private String ConsignCode = "";

    //委托账号
    private String ConsignAccount = "";

    //委托邮编
    private String ConsignZip = "";

	//借款名称
	private String BorrowName = "";

	//借款住所
	private String BorrowAddress = "";

	//借款开户行
	private String BorrowBank = "";

	//借款账号
	private String BorrowCode = "";

    //委托账号
    private String BorrowAccount = "";

    //委托邮编
    private String BorrowZip = "";

	//担保名称
	private String AssureName = "";

	//担保住所
	private String AssureAddress = "";

	//担保开户行
	private String AssureBank = "";

	//担保账号
	private String AssureCode = "";

    //委托账号
    private String AssureAccount = "";

    //委托邮编
    private String AssureZip = "";

	//展期数目
	private long extendCount = -1;

	//免还数目
	private long freeCount = -1;

	//逾期余额
	private double overdueAmount=0.0;

	//表外利息
	private double punishInterest=0.0;

	//已转让债券金额
	private double lastAttornmentAmount = 0;
	
	//下一个审核级别
	private long nextCheckLevel = -1;
	
	//合同本金减去提前还款申请后的余额
	private double balanceForAttornment = 0;
    
    //固定浮动利率
    private double staidAdjustRate=0;

	//占用授信额度金额
	private double useCreditAmount=0;
	
	//买方付息
	private long isPurchaserInterest = 2;			//是否买方付息
    private long discountClientID = -1;				//出票人
    private String discountClientName = "";			//实际贴现人名称
    private double purchaserInterestRate = 0;		//实际贴现人
    private double discountPurchaserInterest = 0;	//买方付贴现利息
    
    private double mDiscountAccrual=0;              //贴现人付息
    
    //担保
	private double assureChargeRate = 0; 		//担保费率
	private long assureChargeTypeID = -1;		//担保费收取方式
	private String beneficiary = "";	 		//受益人
	private long assureTypeID1 = -1;	 		//担保类型1
	private long assureTypeID2 = -1;	 		//担保类型2
	private double recognizanceAmount = 0.0;	//保证金金额
	//担保--收款通知单、保后处理显示用
	private double sumAssureAmount = 0.0;		//该合同的被担保人所有的担保合同的总金额
	private double sumAssureBanlance = 0.0;		//该合同的被担保人所有的担保合同的总余额
	private double currentRecognizanceAmount = 0.0;	//该合同下累计已收保证金金额（已收-已还）
	private double AssureBalance = 0.0;//担保余额
    //private double receiveAssureChargeAmount = 0.0; //该合同下当前已收手续费
	//private double receiveRecognizanceAmount = 0.0; //该合同下当前已收保证金
	private String lateRateString = "";         //LIBOR利率，字符串格式
	
	//合同结束日期 2006-3-15
	private Timestamp lastExecDate = null;
	
	//融资租赁新增
	private long interestCountTypeID = -1;	//利息计算方式
	private double chargeAmount = 0.0;		//手续费金额
	//private double recognizanceAmount = 0.0;//保证金金额
	private double matureNominalAmount = 0.0;//到期名义货价
	private double receivedRecognizanceAmount = 0.0;	//该合同下已收保证金总额（融资租赁）
	private double returnedRecognizanceAmount = 0.0;	//该合同下已还保证金总额（融资租赁）
	
	//added by xiong fei 2010/05/26 融资租赁新增
	private double origionAmount = 0.0;//租赁物原价
	private double preAmount = 0.0;//首付款
	private double chargeAmountRate = 0.0;//手续费率
	
	
	private double recognizanceAmounDeductible = 0.0d;//已扣除保证金金额
	private double rePayAmount = 0.0d;//已还本金金额
	private long   nPayType=-1;//租金偿还方式
	private long   lInterestCountType=-1;//利息计算方式
	//融资租赁新增结束
	
	private InutParameterInfo inutParameterInfo=null;
	private String subTypeName = "";//贷款子类型 mzh_fu 2007/06/12
	
	private double leftoversAttornmentAmount = 0.00;//未转让债权余额 added by qhzhou 2008/04/17
	
	private long payID = -1;  //放款单ID
	private String payCode = "";  //放款单编号
	
	private long loanacountid=-1;//贷款内部账户id。信贷资产转让使用自营贷款账户号
	
	//add by 	yunchang
	//date 		2010-08-19
	//function 	结算--贷款--融资租赁还款--业务处理
	private double ContractHireAmountForYSALL = 0.0d;
	
	private double discountAccrual = 0;
	private double PurchaseAmount = 0;
	
	private String dailyDate;
	
	private long isRemitCompoundInterest 	= -1; //是否计算复利
	private long isRemitOverDueInterest 	= -1; //是否计算罚息
    private double overDueAdjustRate = 0.0 ; //比例浮动
    private double overDueStaidAdjustRate = 0.0 ; //固定浮动点
    private double overDueInterestRate = 0.0 ; //利率	
    
    private long isBuyInto = -1; //是否买入资产
    
    // 申请编号-从
	private String ApplyCodeFrom;
	// 申请编号-到
	private String ApplyCodeTo;
	
	//mLoanAmount 金额-从
	private double LoanAmountFrom;
	
	//mLoanAmount 金额-到
	private double LoanAmountTo;
	
	//sContractCode:合同编号-从
	private String ContractCodeFrom;

	//sContractCode:合同编号-到
	private String ContractCodeTo;
	
	
	public long getIsBuyInto() {
		return isBuyInto;
	}

	public void setIsBuyInto(long isBuyInto) {
		this.isBuyInto = isBuyInto;
	}

	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}

	public double getDiscountAccrual() {
		return discountAccrual;
	}

	public void setDiscountAccrual(double discountAccrual) {
		this.discountAccrual = discountAccrual;
	}

	public double getPurchaseAmount() {
		return PurchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount) {
		PurchaseAmount = purchaseAmount;
	}

	public double getContractHireAmountForYSALL() {
		return ContractHireAmountForYSALL;
	}

	public void setContractHireAmountForYSALL(double contractHireAmountForYSALL) {
		ContractHireAmountForYSALL = contractHireAmountForYSALL;
	}

	public long getLoanacountid() {
		return loanacountid;
	}

	public void setLoanacountid(long loanacountid) {
		this.loanacountid = loanacountid;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public long getPayID() {
		return payID;
	}

	public void setPayID(long payID) {
		this.payID = payID;
	}

	public double getLeftoversAttornmentAmount() {
		return leftoversAttornmentAmount;
	}

	public void setLeftoversAttornmentAmount(double leftoversAttornmentAmount) {
		this.leftoversAttornmentAmount = leftoversAttornmentAmount;
	}

	public long getLInterestCountType() {
		return lInterestCountType;
	}

	public void setLInterestCountType(long interestCountType) {
		lInterestCountType = interestCountType;
	}

	public double getRecognizanceAmounDeductible() {
		return recognizanceAmounDeductible;
	}

	public void setRecognizanceAmounDeductible(double recognizanceAmounDeductible) {
		this.recognizanceAmounDeductible = recognizanceAmounDeductible;
	}

	public double getRePayAmount() {
		return rePayAmount;
	}

	public void setRePayAmount(double rePayAmount) {
		this.rePayAmount = rePayAmount;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public long getInterestCountTypeID() {
		return interestCountTypeID;
	}

	public void setInterestCountTypeID(long interestCountTypeID) {
		this.interestCountTypeID = interestCountTypeID;
	}

	public double getMatureNominalAmount() {
		return matureNominalAmount;
	}

	public void setMatureNominalAmount(double matureNominalAmount) {
		this.matureNominalAmount = matureNominalAmount;
	}

	/**
	 * function 得到合同标识
	 * return ContractID
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @param lID
	 * function 设置合同标识
	 * return void
	 */
	public void setContractID(long lID)
	{
		this.ContractID = lID;
	}

	/**
	 * function 得到合同编号
	 * return ContractCode
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @param ContractCode
	 * function 设置合同编号
	 * return void
	 */
	public void setContractCode(String ContractCode)
	{
		this.ContractCode = ContractCode;
	}

	/**
	 * function 得到贷款单位名称
	 * return BorrowClientName
	 */
	public String getBorrowClientName()
	{
		return BorrowClientName;
	}

	/**
	 * @param BorrowClientName
	 * function 设置贷款单位名称
	 * return void
	 */
	public void setBorrowClientName(String BorrowClientName)
	{
		this.BorrowClientName = BorrowClientName;
	}

	/**
	 * function 得到贷款单位编号
	 * return BorrowClientCode
	 */
	public String getBorrowClientCode()
	{
		return BorrowClientCode;
	}

	/**
	 * @param BorrowClientCode
	 * function 设置贷款单位编号
	 * return void
	 */
	public void setBorrowClientCode(String BorrowClientCode)
	{
		this.BorrowClientCode = BorrowClientCode;
	}

	/**
	 * function 得到委托单位名称
	 * return ClientName
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @param ClientName
	 * function 设置委托单位名称
	 * return ClientName
	 */
	public void setClientName(String ClientName)
	{
		this.ClientName = ClientName;
	}

	/**
	 * function 得到贷款金额
	 * return lLoanAmount
	 */
	public double getLoanAmount()
	{
		return LoanAmount;
	}

	/**
	* function 得到格式化后的贷款金额
	* return lLoanAmount
	*/
	public String getFormatLoanAmount()
	{
		return DataFormat.formatDisabledAmount(LoanAmount);
	}

	/**
	 * @param dLoanAmount
	 * function 设置贷款金额
	 * return void
	 */
	public void setLoanAmount(double dLoanAmount)
	{
		this.LoanAmount = dLoanAmount;
	}

	/**
	 * function 得到利率
	 * return dInterestRate
	 */
	public double getInterestRate()
	{
		return InterestRate;
	}

	/**
	* function 得到利率
	* return dInterestRate
	*/
	public String getFormatInterestRate()
	{
		return DataFormat.formatRate(InterestRate);
	}

	/**
	 * @param dInterestRate
	 * function 设置利率
	 * return void
	 */
	public void setInterestRate(double dInterestRate)
	{
		this.InterestRate = dInterestRate;
	}

	/**
	 * function 得到贷款期限
	 * return lIntervalNum
	 */
	public long getIntervalNum()
	{
		return IntervalNum;
	}

	/**
	 * @param IntervalNum
	 * function 设置贷款期限
	 * return void
	 */
	public void setIntervalNum(long IntervalNum)
	{
		this.IntervalNum = IntervalNum;
	}

	/**
	 * function 得到合同状态
	 * return lStatusID
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param lStatusID
	 * function 设置合同状态
	 * return void
	 */
	public void setStatusID(long lStatusID)
	{
		this.StatusID = lStatusID;
	}

	/**
	 * function 得到合同风险等级
	 * return lStatusID
	 */
	public long getRiskLevel()
	{
		return riskLevel;
	}

	/**
	 * @param lStatusID
	 * function 设置合同风险等级
	 * return void
	 */
	public void setRiskLevel(long lRiskLevel)
	{
		this.riskLevel = lRiskLevel;
	}

	/**
	 * function 得到合同管理人
	 * return InputUserName
	 */
	public String getInputUserName()
	{
		if (InputUserName != null)
		{
			return InputUserName;
		}
		else
		{
			return "";
		}
	}

	/**
	 * @param InputUserName
	 * function 设置合同管理人
	 * return void
	 */
	public void setInputUserName(String InputUserName)
	{
		this.InputUserName = InputUserName;
	}

	/**
	 * function 得到贷款类型
	 * return lTypeID
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @param lTypeID
	 * function 设置贷款类型
	 * return void
	 */
	public void setLoanTypeID(long lTypeID)
	{
		this.LoanTypeID = lTypeID;
	}

	/**
	 * function 得到贷款单位ID
	 * return lBorrowClientID
	 */
	public long getBorrowClientID()
	{
		return BorrowClientID;
	}

	/**
	 * @param lBorrowClientID
	 * function 设置贷款单位ID
	 * return void
	 */
	public void setBorrowClientID(long lBorrowClientID)
	{
		this.BorrowClientID = lBorrowClientID;
	}

	/**
	 * function 得到委托单位ID
	 * return lClientID
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param lClientID
	 * function 设置委托单位ID
	 * return void
	 */
	public void setClientID(long lClientID)
	{
		this.ClientID = lClientID;
	}

	/**
	 * function 得到状态描述
	 * return Status
	 */
	public String getStatus()
	{
		return Status;
	}

	/**
	 * @param Status
	 * function 设置状态描述
	 * return void
	 */
	public void setStatus(String Status)
	{
		this.Status = Status;
	}

	/**
	 * function 得到贷款类型描述
	 * return LoanTypeName
	 */
	public String getLoanTypeName()
	{
		return LoanTypeName;
	}

	/**
	 * @param LoanTypeName
	 * function 设置贷款类型描述
	 * return void
	 */
	public void setLoanTypeName(String LoanTypeName)
	{
		this.LoanTypeName = LoanTypeName;
	}

	/**
	 * function 得到总金额
	 * return double
	 */
	public double getAllAmount()
	{
		return AllAmount;
	}

	/**
	 * @param dAllAmount
	 * function 设置总金额
	 * return void
	 */
	public void setAllAmount(double AllAmount)
	{
		this.AllAmount = AllAmount;
	}

	/**
	 * function 得到申请书编号
	 * return ApplyCode
	 */
	public String getApplyCode()
	{
		return ApplyCode;
	}

	/**
	 * @param ApplyCode
	 * function 设置申请书编号
	 * return void
	 */
	public void setApplyCode(String ApplyCode)
	{
		this.ApplyCode = ApplyCode;
	}

	public Timestamp getLoanStart()
	{
		return LoanStart;
	}

	public String getFormatLoanStart()
	{
		return DataFormat.getDateString(LoanStart);
	}

	public void setLoanStart(Timestamp LoanStart)
	{
		this.LoanStart = LoanStart;
	}

	public Timestamp getLoanEnd()
	{
		return LoanEnd;
	}

	public String getFormatLoanEnd()
	{
		return DataFormat.getDateString(LoanEnd);
	}

	public void setLoanEnd(Timestamp LoanEnd)
	{
		this.LoanEnd = LoanEnd;
	}

	/**
	 * function 得到分页显示总页数
	 * return PageCount
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @param lPageCount
	 * function 设置分页显示总页数
	 * return void
	 */
	public void setPageCount(long lPageCount)
	{
		this.PageCount = lPageCount;
	}

	/**
	 * function 得到总纪录数
	 * return AllRecord
	 */
	public long getAllRecord()
	{
		return AllRecord;
	}

	/**
	 * @param lAllRecord
	 * function 设置总纪录数
	 * return void
	 */
	public void setAllRecord(long lAllRecord)
	{
		this.AllRecord = lAllRecord;
	}

	public double getAmountDone()
	{
		return AmountDone;
	}

	public void setAmountDone(double AmountDone)
	{
		this.AmountDone = AmountDone;
	}

	public String getLoanPurpose()
	{
		return LoanPurpose;
	}

	public void setLoanPurpose(String LoanPurpose)
	{
		this.LoanPurpose = LoanPurpose;
	}

	public Timestamp getOutDate()
	{
		return OutDate;
	}

	public void setOutDate(Timestamp OutDate)
	{
		this.OutDate = OutDate;
	}

	public String getLoanAccount()
	{
		return LoanAccount;
	}

	public void setLoanAccount(String LoanAccount)
	{
		this.LoanAccount = LoanAccount;
	}

	public String getLoanAddress()
	{
		return LoanAddress;
	}

	public void setLoanAddress(String LoanAddress)
	{
		this.LoanAddress = LoanAddress;
	}

	public String getLoanPhone()
	{
		return LoanPhone;
	}

	public void setLoanPhone(String LoanPhone)
	{
		this.LoanPhone = LoanPhone;
	}

	public String getLoanZipCode()
	{
		return LoanZipCode;
	}

	public void setLoanZipCode(String LoanZipCode)
	{
		this.LoanZipCode = LoanZipCode;
	}

	public String getClientAccount()
	{
		return ClientAccount;
	}

	public void setClientAccount(String ClientAccount)
	{
		this.ClientAccount = ClientAccount;
	}

	/**
	 * function 得到合同执行计划是否在修改
	 * return IsPlanModifying
	 */
	public long getIsPlanModifying()
	{
		return IsPlanModifying;
	}

	/**
	 * @param IsPlanModifying
	 * function设置合同执行计划是否在修改
	 * return void
	 */
	public void setIsPlanModifying(long lIsPlanModifying)
	{
		this.IsPlanModifying = lIsPlanModifying;
	}

	/**
	 * function 得到合同执行计划版本号ID
	 * return PlanVersionID
	 */
	public long getPlanVersionID()
	{
		return PlanVersionID;
	}

	/**
	 * @param lPlanVersionID
	 * function 设置合同执行计划版本号ID
	 * return void
	 */
	public void setPlanVersionID(long lPlanVersionID)
	{
		this.PlanVersionID = lPlanVersionID;
	}

	/**
	 * function 得到是否有计划在展期之中
	 * return IsOverDueModifying
	 */
	public long getIsOverDueModifying()
	{
		return IsOverDueModifying;
	}

	/**
	 * @param lIsOverDueModifying
	 * function 设置是否有计划在展期之中
	 * return void
	 */
	public void setIsOverDueModifying(long lIsOverDueModifying)
	{
		this.IsOverDueModifying = lIsOverDueModifying;
	}

	public double getRate()
	{
		return Rate;
	}

	public void setRate(double Rate)
	{
		this.Rate = Rate;
	}

	public double getAdjustRate()
	{
		return AdjustRate;
	}

	public void setAdjustRate(double AdjustRate)
	{
		this.AdjustRate = AdjustRate;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	public String getFormatInputDate()
	{
		return DataFormat.getDateString(InputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		InputDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		lInputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		LoanID = l;
	}

	/**
	 * @return
	 */
	public double getExamineAmount()
	{
		return ExamineAmount;
	}

	/**
	* @return
	*/
	public String getFormatExamineAmount()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount);
	}

	/**
	 * @param d
	 */
	public void setExamineAmount(double d)
	{
		ExamineAmount = d;
	}

	/**
	 * @return
	 */
	public double getAssureAmount()
	{
		return AssureAmount;
	}

	/**
	* @return
	*/
	public String getFormatAssureAmount()
	{
		return DataFormat.formatDisabledAmount(AssureAmount);
	}

	/**
	 * @param d
	 */
	public void setAssureAmount(double d)
	{
		AssureAmount = d;
	}

	/**
	 * @return
	 */
	public double getAssureRate()
	{
		return AssureRate;
	}

	/**
	* @return
	*/
	public String getFormatAssureRate()
	{
		return DataFormat.formatDisabledAmount(AssureRate * 100);
	}

	/**
	 * @param d
	 */
	public void setAssureRate(double d)
	{
		AssureRate = d;
	}

	/**
	 * @return
	 */
	public double getCreditAmount()
	{
		return CreditAmount;
	}

	/**
	* @return
	*/
	public String getFormatCreditAmount()
	{
		return DataFormat.formatDisabledAmount(CreditAmount);
	}

	/**
	 * @param d
	 */
	public void setCreditAmount(double d)
	{
		CreditAmount = d;
	}

	/**
	 * @return
	 */
	public double getCreditRate()
	{
		return CreditRate;
	}

	/**
	* @return
	*/
	public String getFormatCreditRate()
	{
		return DataFormat.formatDisabledAmount(CreditRate * 100);
	}

	/**
	 * @param d
	 */
	public void setCreditRate(double d)
	{
		CreditRate = d;
	}

	/**
	 * @return
	 */
	public long getAreaType()
	{
		return AreaType;
	}

	/**
	 * @return
	 */
	public long getIndustryType1()
	{
		return IndustryType1;
	}

	/**
	 * @return
	 */
	public long getIndustryType2()
	{
		return IndustryType2;
	}

	/**
	 * @param l
	 */
	public void setAreaType(long l)
	{
		AreaType = l;
	}

	/**
	 * @param l
	 */
	public void setIndustryType1(long l)
	{
		IndustryType1 = l;
	}

	/**
	 * @param l
	 */
	public void setIndustryType2(long l)
	{
		IndustryType2 = l;
	}

	/**
	 * @return
	 */
	public Collection getAssure()
	{
		return cAssure;
	}

	/**
	 * @param collection
	 */
	public void setAssure(Collection collection)
	{
		cAssure = collection;
	}

	/**
	 * @return
	 */
	public String getCheckUserName()
	{
		if (CheckUserName != null)
		{
			return CheckUserName;
		}
		else
		{
			return "";
		}

	}

	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		CheckUserName = string;
	}

	/**
	 * @return
	 */
	public YTFormatInfo getYTInfo()
	{
		return YTInfo;
	}

	/**
	 * @param info
	 */
	public void setYTInfo(YTFormatInfo info)
	{
		YTInfo = info;
	}

	/**
	 * @return
	 */
	public Collection getYT()
	{
		return cYT;
	}

	/**
	 * @param collection
	 */
	public void setYT(Collection collection)
	{
		cYT = collection;
	}

	/**
	 * Returns the cAssure.
	 * @return Collection
	 */
	public Collection getCAssure()
	{
		return cAssure;
	}

	/**
	 * Returns the cYT.
	 * @return Collection
	 */
	public Collection getCYT()
	{
		return cYT;
	}

	/**
	 * Sets the cAssure.
	 * @param cAssure The cAssure to set
	 */
	public void setCAssure(Collection cAssure)
	{
		this.cAssure = cAssure;
	}

	/**
	 * Sets the cYT.
	 * @param cYT The cYT to set
	 */
	public void setCYT(Collection cYT)
	{
		this.cYT = cYT;
	}

	/**
	 * Returns the planModifyDate.
	 * @return Timestamp
	 */
	public Timestamp getPlanModifyDate()
	{
		return PlanModifyDate;
	}

	/**
	 * Sets the planModifyDate.
	 * @param planModifyDate The planModifyDate to set
	 */
	public void setPlanModifyDate(Timestamp planModifyDate)
	{
		PlanModifyDate = planModifyDate;
	}

	/**
	 * @return
	 */
	public String getLoanReason()
	{
		return LoanReason;
	}

	/**
	 * @param string
	 */
	public void setLoanReason(String string)
	{
		LoanReason = string;
	}

	/**
	 * @return
	 */
	public String getOther()
	{
		return sOther;
	}

	/**
	 * @param string
	 */
	public void setOther(String string)
	{
		sOther = string;
	}

	/**
	 * @return
	 */
	public Collection getContractContent()
	{
		return cContractContent;
	}

	/**
	 * @param collection
	 */
	public void setContractContent(Collection collection)
	{
		cContractContent = collection;
	}

	/**
	 * Returns the chargeRate.
	 * @return double
	 */
	public double getChargeRate()
	{
		return ChargeRate;
	}

	public String getFormatChargeRate()
	{
		return DataFormat.formatRate(ChargeRate);
	}

	/**
	 * Sets the chargeRate.
	 * @param chargeRate The chargeRate to set
	 */
	public void setChargeRate(double chargeRate)
	{
		ChargeRate = chargeRate;
	}

	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * @return
	 */
	public long getChargeRateType()
	{
		return ChargeRateType;
	}

	/**
	 * @param l
	 */
	public void setChargeRateType(long l)
	{
		ChargeRateType = l;
	}

	/**
	 * @return
	 */
	public long getBankInterestID()
	{
		return BankInterestID;
	}

	/**
	 * @param l
	 */
	public void setBankInterestID(long l)
	{
		BankInterestID = l;
	}

	/**
	 * @return
	 */
	public double getCheckAmount()
	{
		return CheckAmount;
	}

	/**
	* @return
	*/
	public String getFormatCheckAmount()
	{
		return DataFormat.formatDisabledAmount(CheckAmount);
	}

	/**
	 * @param d
	 */
	public void setCheckAmount(double d)
	{
		CheckAmount = d;
	}

	/**
	 * @return
	 */
	public double getDiscountRate()
	{
		return DiscountRate;
	}

	/**
	* @return
	*/
	public String getFormatDiscountRate()
	{
		return DataFormat.formatRate(DiscountRate);
	}

	/**
	 * @param d
	 */
	public void setDiscountRate(double d)
	{
		DiscountRate = d;
	}

	/**
	 * @return
	 */
	public double getDiscountInterest()
	{
		return ExamineAmount - CheckAmount;
	}

	/**
	* @return
	*/
	public String getFormatDiscountInterest()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount - CheckAmount);
	}

	/**
	 * @return
	 */
	public double getBasicInterestRate()
	{
		return BasicInterestRate;
	}

	/**
	 * @param d
	 */
	public void setBasicInterestRate(double d)
	{
		BasicInterestRate = d;
	}

	/**
	 * @param d
	 */
	public void setDiscountInterest(double d)
	{
		DiscountInterest = d;
	}

	/**
	 * Returns the assureAddress.
	 * @return String
	 */
	public String getAssureAddress()
	{
		return AssureAddress;
	}

	/**
	 * Returns the assureBank.
	 * @return String
	 */
	public String getAssureBank()
	{
		return AssureBank;
	}

	/**
	 * Returns the assureCode.
	 * @return String
	 */
	public String getAssureCode()
	{
		return AssureCode;
	}

	/**
	 * Returns the assureName.
	 * @return String
	 */
	public String getAssureName()
	{
		return AssureName;
	}

	/**
	 * Returns the borrowAddress.
	 * @return String
	 */
	public String getBorrowAddress()
	{
		return BorrowAddress;
	}

	/**
	 * Returns the borrowBank.
	 * @return String
	 */
	public String getBorrowBank()
	{
		return BorrowBank;
	}

	/**
	 * Returns the borrowCode.
	 * @return String
	 */
	public String getBorrowCode()
	{
		return BorrowCode;
	}

	/**
	 * Returns the borrowName.
	 * @return String
	 */
	public String getBorrowName()
	{
		return BorrowName;
	}

	/**
	 * Returns the consignAddress.
	 * @return String
	 */
	public String getConsignAddress()
	{
		return ConsignAddress;
	}

	/**
	 * Returns the consignBank.
	 * @return String
	 */
	public String getConsignBank()
	{
		return ConsignBank;
	}

	/**
	 * Returns the consignCode.
	 * @return String
	 */
	public String getConsignCode()
	{
		return ConsignCode;
	}

	/**
	 * Returns the consignName.
	 * @return String
	 */
	public String getConsignName()
	{
		return ConsignName;
	}

	/**
	 * Returns the isAssure.
	 * @return long
	 */
	public long getIsAssure()
	{
		return IsAssure;
	}

	/**
	 * Returns the isCredit.
	 * @return long
	 */
	public long getIsCredit()
	{
		return IsCredit;
	}

	/**
	 * Returns the isImpawn.
	 * @return long
	 */
	public long getIsImpawn()
	{
		return IsImpawn;
	}

	/**
	 * Returns the isPledge.
	 * @return long
	 */
	public long getIsPledge()
	{
		return IsPledge;
	}

	/**
	 * Sets the assureAddress.
	 * @param assureAddress The assureAddress to set
	 */
	public void setAssureAddress(String assureAddress)
	{
		AssureAddress = assureAddress;
	}

	/**
	 * Sets the assureBank.
	 * @param assureBank The assureBank to set
	 */
	public void setAssureBank(String assureBank)
	{
		AssureBank = assureBank;
	}

	/**
	 * Sets the assureCode.
	 * @param assureCode The assureCode to set
	 */
	public void setAssureCode(String assureCode)
	{
		AssureCode = assureCode;
	}

	/**
	 * Sets the assureName.
	 * @param assureName The assureName to set
	 */
	public void setAssureName(String assureName)
	{
		AssureName = assureName;
	}

	/**
	 * Sets the borrowAddress.
	 * @param borrowAddress The borrowAddress to set
	 */
	public void setBorrowAddress(String borrowAddress)
	{
		BorrowAddress = borrowAddress;
	}

	/**
	 * Sets the borrowBank.
	 * @param borrowBank The borrowBank to set
	 */
	public void setBorrowBank(String borrowBank)
	{
		BorrowBank = borrowBank;
	}

	/**
	 * Sets the borrowCode.
	 * @param borrowCode The borrowCode to set
	 */
	public void setBorrowCode(String borrowCode)
	{
		BorrowCode = borrowCode;
	}

	/**
	 * Sets the borrowName.
	 * @param borrowName The borrowName to set
	 */
	public void setBorrowName(String borrowName)
	{
		BorrowName = borrowName;
	}

	/**
	 * Sets the consignAddress.
	 * @param consignAddress The consignAddress to set
	 */
	public void setConsignAddress(String consignAddress)
	{
		ConsignAddress = consignAddress;
	}

	/**
	 * Sets the consignBank.
	 * @param consignBank The consignBank to set
	 */
	public void setConsignBank(String consignBank)
	{
		ConsignBank = consignBank;
	}

	/**
	 * Sets the consignCode.
	 * @param consignCode The consignCode to set
	 */
	public void setConsignCode(String consignCode)
	{
		ConsignCode = consignCode;
	}

	/**
	 * Sets the consignName.
	 * @param consignName The consignName to set
	 */
	public void setConsignName(String consignName)
	{
		ConsignName = consignName;
	}

	/**
	 * Sets the isAssure.
	 * @param isAssure The isAssure to set
	 */
	public void setIsAssure(long isAssure)
	{
		IsAssure = isAssure;
	}

	/**
	 * Sets the isCredit.
	 * @param isCredit The isCredit to set
	 */
	public void setIsCredit(long isCredit)
	{
		IsCredit = isCredit;
	}

	/**
	 * Sets the isImpawn.
	 * @param isImpawn The isImpawn to set
	 */
	public void setIsImpawn(long isImpawn)
	{
		IsImpawn = isImpawn;
	}

	/**
	 * Sets the isPledge.
	 * @param isPledge The isPledge to set
	 */
	public void setIsPledge(long isPledge)
	{
		IsPledge = isPledge;
	}

	/**
	 * @return
	 */
	public Collection getCContractContent()
	{
		return cContractContent;
	}

	/**
	 * @return
	 */
	public long getExtendCount()
	{
		return extendCount;
	}

	/**
	 * @return
	 */
	public long getFreeCount()
	{
		return freeCount;
	}

	/**
	 * @param collection
	 */
	public void setCContractContent(Collection collection)
	{
		cContractContent = collection;
	}

	/**
	 * @param l
	 */
	public void setExtendCount(long l)
	{
		extendCount = l;
	}

	/**
	 * @param l
	 */
	public void setFreeCount(long l)
	{
		freeCount = l;
	}

	/**
	 * @return
	 */
	public ContractAmountInfo getAInfo()
	{
		return aInfo;
	}

	/**
	 * @param info
	 */
	public void setAInfo(ContractAmountInfo info)
	{
		aInfo = info;
	}

	/**
	 * @return
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		Balance = d;
	}

    /**
     * function 得到/设置
     * return double
     */
    public double getScale()
    {
        return Scale;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setScale(double d)
    {
        this.Scale = d;
    }

	/**
	 * Returns the overdueAmount.
	 * @return double
	 */
	public double getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * Returns the punishInterest.
	 * @return double
	 */
	public double getPunishInterest() {
		return punishInterest;
	}

	/**
	 * Sets the overdueAmount.
	 * @param overdueAmount The overdueAmount to set
	 */
	public void setOverdueAmount(double overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * Sets the punishInterest.
	 * @param punishInterest The punishInterest to set
	 */
	public void setPunishInterest(double punishInterest) {
		this.punishInterest = punishInterest;
	}

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getAssureAccount()
    {
        return AssureAccount;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getAssureZip()
    {
        return AssureZip;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getBorrowAccount()
    {
        return BorrowAccount;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getBorrowZip()
    {
        return BorrowZip;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getConsignAccount()
    {
        return ConsignAccount;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getConsignZip()
    {
        return ConsignZip;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAssureAccount(String string)
    {
        AssureAccount = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAssureZip(String string)
    {
        AssureZip = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBorrowAccount(String string)
    {
        BorrowAccount = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBorrowZip(String string)
    {
        BorrowZip = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setConsignAccount(String string)
    {
        ConsignAccount = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setConsignZip(String string)
    {
        ConsignZip = string;
    }

	/**
	 * @return
	 */
	public double getLastAttornmentAmount()
	{
		return lastAttornmentAmount;
	}

	/**
	 * @param d
	 */
	public void setLastAttornmentAmount(double d)
	{
		lastAttornmentAmount = d;
	}

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return nextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        this.nextCheckLevel = nextCheckLevel;
    }
	/**
	 * @return
	 */
	public double getBalanceForAttornment()
	{
		return balanceForAttornment;
	}

	/**
	 * @param d
	 */
	public void setBalanceForAttornment(double d)
	{
		balanceForAttornment = d;
	}

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStaidAdjustRate()
    {
        return staidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        staidAdjustRate = d;
    }

	/**
	 * @return
	 */
	public double getUseCreditAmount()
	{
		return useCreditAmount;
	}

	/**
	 * @param d
	 */
	public void setUseCreditAmount(double d)
	{
		useCreditAmount = d;
	}

	/**
	 * @return
	 */
	public double getExamineSelfAmount()
	{
		return mExamineSelfAmount;
	}

	/**
	 * @param d
	 */
	public void setExamineSelfAmount(double d)
	{
		mExamineSelfAmount = d;
	}

    /**
     * @return Returns the discountClientID.
     */
    public long getDiscountClientID()
    {
        return discountClientID;
    }
    /**
     * @param discountClientID The discountClientID to set.
     */
    public void setDiscountClientID(long discountClientID)
    {
        this.discountClientID = discountClientID;
    }
    /**
     * @return Returns the purchaserInterestRate.
     */
    public double getPurchaserInterestRate()
    {
        return purchaserInterestRate;
    }
    /**
     * @param purchaserInterestRate The purchaserInterestRate to set.
     */
    public void setPurchaserInterestRate(double purchaserInterestRate)
    {
        this.purchaserInterestRate = purchaserInterestRate;
    }
    /**
     * @return Returns the discountClientName.
     */
    public String getDiscountClientName()
    {
        return discountClientName;
    }
    /**
     * @param discountClientName The discountClientName to set.
     */
    public void setDiscountClientName(String discountClientName)
    {
        this.discountClientName = discountClientName;
    }
    /**
     * @return Returns the isPurchaserInterest.
     */
    public long getIsPurchaserInterest()
    {
        return isPurchaserInterest;
    }
    /**
     * @param isPurchaserInterest The isPurchaserInterest to set.
     */
    public void setIsPurchaserInterest(long isPurchaserInterest)
    {
        this.isPurchaserInterest = isPurchaserInterest;
    }
    /**
     * @return Returns the assureChargeRate.
     */
    public double getAssureChargeRate()
    {
        return assureChargeRate;
    }
    /**
     * @param assureChargeRate The assureChargeRate to set.
     */
    public void setAssureChargeRate(double assureChargeRate)
    {
        this.assureChargeRate = assureChargeRate;
    }
    /**
     * @return Returns the assureChargeTypeID.
     */
    public long getAssureChargeTypeID()
    {
        return assureChargeTypeID;
    }
    /**
     * @param assureChargeTypeID The assureChargeTypeID to set.
     */
    public void setAssureChargeTypeID(long assureChargeTypeID)
    {
        this.assureChargeTypeID = assureChargeTypeID;
    }
    /**
     * @return Returns the assureTypeID1.
     */
    public long getAssureTypeID1()
    {
        return assureTypeID1;
    }
    /**
     * @param assureTypeID1 The assureTypeID1 to set.
     */
    public void setAssureTypeID1(long assureTypeID1)
    {
        this.assureTypeID1 = assureTypeID1;
    }
    /**
     * @return Returns the assureTypeID2.
     */
    public long getAssureTypeID2()
    {
        return assureTypeID2;
    }
    /**
     * @param assureTypeID2 The assureTypeID2 to set.
     */
    public void setAssureTypeID2(long assureTypeID2)
    {
        this.assureTypeID2 = assureTypeID2;
    }
    /**
     * @return Returns the beneficiary.
     */
    public String getBeneficiary()
    {
        return beneficiary;
    }
    /**
     * @param beneficiary The beneficiary to set.
     */
    public void setBeneficiary(String beneficiary)
    {
        this.beneficiary = beneficiary;
    }
    /**
     * @return Returns the isRecognizance.
     */
    public long getIsRecognizance()
    {
        return IsRecognizance;
    }
    /**
     * @param isRecognizance The isRecognizance to set.
     */
    public void setIsRecognizance(long isRecognizance)
    {
        IsRecognizance = isRecognizance;
    }
	/**
	 * @return
	 */
	public double getRecognizanceAmount()
	{
		return recognizanceAmount;
	}

	/**
	 * @param d
	 */
	public void setRecognizanceAmount(double d)
	{
		recognizanceAmount = d;
	}

	/**
	 * @return
	 */
	public double getCurrentRecognizanceAmount()
	{
		return currentRecognizanceAmount;
	}

	/**
	 * @return
	 */
	public double getSumAssureAmount()
	{
		return sumAssureAmount;
	}

	/**
	 * @return
	 */
	public double getSumAssureBanlance()
	{
		return sumAssureBanlance;
	}

	/**
	 * @param d
	 */
	public void setCurrentRecognizanceAmount(double d)
	{
		currentRecognizanceAmount = d;
	}

	/**
	 * @param d
	 */
	public void setSumAssureAmount(double d)
	{
		sumAssureAmount = d;
	}

	/**
	 * @param d
	 */
	public void setSumAssureBanlance(double d)
	{
		sumAssureBanlance = d;
	}

    /**
     * @return Returns the industryType3.
     */
    public long getIndustryType3()
    {
        return IndustryType3;
    }
    /**
     * @param industryType3 The industryType3 to set.
     */
    public void setIndustryType3(long industryType3)
    {
        IndustryType3 = industryType3;
    }



	/**
	 * @return Returns the assureBalance.
	 */
	public double getAssureBalance()
	{
		return AssureBalance;
	}
	/**
	 * @param assureBalance The assureBalance to set.
	 */
	public void setAssureBalance(double assureBalance)
	{
		AssureBalance = assureBalance;
	}
    /**
     * @return Returns the interestTypeID.
     */
    public long getInterestTypeID()
    {
        return InterestTypeID;
    }
    /**
     * @param interestTypeID The interestTypeID to set.
     */
    public void setInterestTypeID(long interestTypeID)
    {
        InterestTypeID = interestTypeID;
    }
    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return LiborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        LiborRateID = liborRateID;
    }
    /**
     * @return Returns the discountPurchaserInterest.
     */
    public double getDiscountPurchaserInterest()
    {
        return discountPurchaserInterest;
    }
    /**
     * @param discountPurchaserInterest The discountPurchaserInterest to set.
     */
    public void setDiscountPurchaserInterest(double discountPurchaserInterest)
    {
        this.discountPurchaserInterest = discountPurchaserInterest;
    }
	/**
	 * @return Returns the lateRateString.
	 */
	public String getLateRateString() {
		return lateRateString;
	}
	/**
	 * @param lateRateString The lateRateString to set.
	 */
	public void setLateRateString(String lateRateString) {
		this.lateRateString = lateRateString;
	}
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getLInputUserID() {
		return lInputUserID;
	}
	/**
	 * @param inputUserID The lInputUserID to set.
	 */
	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}
	/**
	 * @return Returns the mExamineSelfAmount.
	 */
	public double getMExamineSelfAmount() {
		return mExamineSelfAmount;
	}
	/**
	 * @param examineSelfAmount The mExamineSelfAmount to set.
	 */
	public void setMExamineSelfAmount(double examineSelfAmount) {
		mExamineSelfAmount = examineSelfAmount;
	}
	/**
	 * @return Returns the sOther.
	 */
	public String getSOther() {
		return sOther;
	}
	/**
	 * @param other The sOther to set.
	 */
	public void setSOther(String other) {
		sOther = other;
	}

	/**
	 * Returns the subTypeID.
	 * @return long
	 */
	public long getSubTypeID() {
		return SubTypeID;
	}

	/**
	 * Sets the subTypeID.
	 * @param subTypeID The subTypeID to set
	 */
	public void setSubTypeID(long subTypeID) {
		SubTypeID = subTypeID;
	}
 
	/**
	 * 合同结束日期 2006-3-15
	 * Returns the lastExecDate.
	 * @return Timestamp
	 */
	public Timestamp getLastExecDate() {
		return lastExecDate;
	}

	/**
	 * 合同结束日期 2006-3-15
	 * Sets the lastExecDate.
	 * @param lastExecDate The lastExecDate to set
	 */
	public void setLastExecDate(Timestamp lastExecDate) {
		this.lastExecDate = lastExecDate;
	}

	public double getReceivedRecognizanceAmount() {
		return receivedRecognizanceAmount;
	}

	public void setReceivedRecognizanceAmount(double receivedRecognizanceAmount) {
		this.receivedRecognizanceAmount = receivedRecognizanceAmount;
	}

	public double getReturnedRecognizanceAmount() {
		return returnedRecognizanceAmount;
	}

	public void setReturnedRecognizanceAmount(double returnedRecognizanceAmount) {
		this.returnedRecognizanceAmount = returnedRecognizanceAmount;
	}

	public long getNPayType() {
		return nPayType;
	}

	public void setNPayType(long payType) {
		nPayType = payType;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public Timestamp getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Timestamp discountDate) {
		this.discountDate = discountDate;
	}

	public long getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(long isCircle) {
		this.isCircle = isCircle;
	}

	public String getTsDiscountType() {
		return tsDiscountType;
	}

	public void setTsDiscountType(String tsDiscountType) {
		this.tsDiscountType = tsDiscountType;
	}

	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}

	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}

	public long getIsRepurchase() {
		return IsRepurchase;
	}

	public void setIsRepurchase(long isRepurchase) {
		IsRepurchase = isRepurchase;
	}

	public double getOrigionAmount() {
		return origionAmount;
	}

	public void setOrigionAmount(double origionAmount) {
		this.origionAmount = origionAmount;
	}

	public double getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(double preAmount) {
		this.preAmount = preAmount;
	}

	public double getChargeAmountRate() {
		return chargeAmountRate;
	}

	public void setChargeAmountRate(double chargeAmountRate) {
		this.chargeAmountRate = chargeAmountRate;
	}

	public double getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(double dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	public double getMDiscountAccrual() {
		return mDiscountAccrual;
	}

	public void setMDiscountAccrual(double discountAccrual) {
		mDiscountAccrual = discountAccrual;
	}

	public long getIsRemitCompoundInterest() {
		return isRemitCompoundInterest;
	}

	public void setIsRemitCompoundInterest(long isRemitCompoundInterest) {
		this.isRemitCompoundInterest = isRemitCompoundInterest;
	}

	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}

	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}

	public double getOverDueAdjustRate() {
		return overDueAdjustRate;
	}

	public void setOverDueAdjustRate(double overDueAdjustRate) {
		this.overDueAdjustRate = overDueAdjustRate;
	}

	public double getOverDueStaidAdjustRate() {
		return overDueStaidAdjustRate;
	}

	public void setOverDueStaidAdjustRate(double overDueStaidAdjustRate) {
		this.overDueStaidAdjustRate = overDueStaidAdjustRate;
	}

	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}

	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
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

	public String getApplyCodeFrom() {
		return ApplyCodeFrom;
	}

	public void setApplyCodeFrom(String applyCodeFrom) {
		ApplyCodeFrom = applyCodeFrom;
	}

	public String getApplyCodeTo() {
		return ApplyCodeTo;
	}

	public void setApplyCodeTo(String applyCodeTo) {
		ApplyCodeTo = applyCodeTo;
	}

	public double getLoanAmountFrom() {
		return LoanAmountFrom;
	}

	public void setLoanAmountFrom(double loanAmountFrom) {
		LoanAmountFrom = loanAmountFrom;
	}

	public double getLoanAmountTo() {
		return LoanAmountTo;
	}

	public void setLoanAmountTo(double loanAmountTo) {
		LoanAmountTo = loanAmountTo;
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
}

/*
 * Created on 2003-9-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dataentity;
/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.Timestamp;
import java.text.DecimalFormat;

import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.*;
import com.iss.itreasury.ebank.util.*;

public class FinanceInfo implements java.io.Serializable
{
	/** Creates new FinaceInfo */
	public FinanceInfo()
	{
	}
	
	double d = 0.00 ;
	Double d1 = new Double( new DecimalFormat( ".00" ).format( d ) );

	
	
	private long lID = -1; // 指令序号
	private String sBatchNo = "";	//批次号
	private long lTransType = -1; // 网上交易类型
	private long lStatus = -1; // 指令状态
	private long lOperationTypeID=-1;//根据页面菜单，取值来自Notes
	private long lClientID = -1; // 交易提交单位 
	private long lCurrencyID = -1; // 交易币种
	private long lChildClientID = -1;//下属单位ID
	private String sChildClientNo = ""; // 下属单位编号
	private String sChildClientName = ""; // 下属单位名称
	private String sPayerBankNo = ""; // 付款方银行编号
	private String sPayerAcctNo = ""; // 付款方账号 
	private long lPayerAcctID = -1; // 付款方账户ID
	private String sPayerName = ""; // 付款方名称

	private String PayClientName = ""; // 付款方客户名称
	private long lRemitType = -1; // 汇款方式
	private String sPayeeName = ""; // 收款方名称 
	private String sPayeeBankNo = ""; // 收款方银行编号
	private long lIsCpfAcct = 1; // 是否中油账户
	private String sPayeeAcctNo = ""; // 收款方账号
	private long lPayeeAcctID = -1; // 收款方账户ID
	private String sPayeeProv = ""; // 汇入省
	private String sPayeeCity = ""; // 汇入市
	private String sPayeeBankName = ""; // 汇入行名称
	private double dAmount = 0.0; // 交易金额
	private Timestamp tsExecuteDate = null; // 执行日 
	private String sNote = ""; // 汇款用途/摘要
	private long lFixedDepositTime = -1; // 定期存款期限（月）
	private long lNoticeDay = -1; // 通知存款品种（天）
	private long lContractID = -1; //贷款合同id
	private String sLoanContractNo = ""; // 贷款合同编号
	private Timestamp tsLoanEndDate = null; // 贷款合同到期日期
	private Timestamp tsPayDate = null; // 贷款放款日期
	private String sLetOutCode = ""; // 放款通知单编号
	private long lLoanNoteID = -1; //放款通知单id
	private String sDepositNo = ""; //定期（通知）存款单据号
	private long lSubAccountID = -1; //子账户ID
	private Timestamp tsDepositStart = null; //定期（通知）存款起始日
	private double dDepositRate = 0.0; //定期存单利率
	private double dDepositAmount = 0.0; //存单金额（开立金额）
	private double dDepositBalance = 0.0; //存单余额
	private long lConfirmUserID = -1; // 确认人ID
	private Timestamp tsConfirmDate = null; // 确认时间
	private long lCheckUserID = -1; // 复核人ID
	private Timestamp tsCheckDate = null; // 复核时间
	private long lSignUserID = -1; // 签认人ID
	private Timestamp tsSignDate = null; // 签认时间
	private long lDeleteUserID = -1; // 删除人ID
	private Timestamp tsDeleteDate = null; // 删除时间
	private long lOfficeID = -1; // CPF-默认办事处ID
	private long lDefaultTransType = -1; // CPF-默认业务类型
	private long lDealTransType = -1; // CPF-处理业务类型
	private long lDealUserID = -1; // CPF-处理人
	private Timestamp tsDealDate = null; // CPF-处理时间
	private String sTransNo = ""; // CPF-交易号
	private Timestamp tsFinishDate = null; // CPF-完成时间
	private String sReject = ""; // CPF-拒绝原因
	private long lIsCanAccept = -1; //能否接受
	private Timestamp tsClearInterest = null; //结息日期
	private long lInterestPayeeAcctID = -1; //利息收款方账户ID
	private String sInterestPayeeName = ""; // 利息收款方名称 
	private String sInterestPayeeBankNo = ""; // 利息收款方银行编号
	private long lInterestIsCpfAcct = 1; // 利息是否中油账户
	private String sInterestPayeeAcctNo = ""; // 利息收款方账号
	private String sInterestPayeeProv = ""; // 利息汇入省
	private String sInterestPayeeCity = ""; // 利息汇入市
	private String sInterestPayeeBankName = ""; // 利息汇入行名称
	private long lInterestRemitType = -1; // 利息付款方式
	private double dInterest = 0.0; // 应付贷款利息
	private double dCompoundInterest = 0.0; // 应付复利
	private double dOverdueInterest = 0.0; // 应付逾期罚息
	private double dSuretyFee = 0.0; // 应付担保费
	private double dCommission = 0.0; // 应付手续费
	private double dRealInterest = 0.0; // 实付贷款利息
	private double dRealCompoundInterest = 0.0; // 实付复利
	private double dRealOverdueInterest = 0.0; // 实付逾期罚息
	private double dRealSuretyFee = 0.0; // 实付担保费
	private double dRealCommission = 0.0; // 实付手续费
	private Timestamp tsInterestStart = null; // 利息起息日(贷款清还用)   
	private double dInterestRate = 0.00; // 贷款利率(贷款清还用)
	private Timestamp tsCompoundStart = null; // 复利起息日(贷款清还用)   
	private double dCompoundRate = 0.00; // 复利利率(贷款清还用)     
	private Timestamp tsOverDueStart = null; // 罚息起息日(贷款清还用)   
	private double dOverDueRate = 0.00; // 罚息利率(贷款清还用)     
	private Timestamp tsSuretyStart = null; // 担保费起息日(贷款清还用) 
	private double dSuretyRate = 0.00; // 担保费率(贷款清还用)     
	private Timestamp tsCommissionStart = null; // 手续费起息日(贷款清还用) 
	private double dCommissionRate = 0.00; // 手续费率(贷款清还用)     
	private double dCompoundAmount = 0.00; // 复利本金(贷款清还用)     
	private double dOverDueAmount = 0.00; // 逾期罚息本金(贷款清还用) 
	private double dInterestReceiveable = 0.00; //计提利息(贷款清还 使用)         
	private double dInterestIncome = 0.00; //本次利息(贷款清还 使用)         
	private double dRealInterestReceiveable = 0.00; //实际支付计提利息(贷款清还 使用) 
	private double dRealInterestIncome = 0.00; //实际支付本次利息(贷款清还 使用) 
	private double dInterestTax = 0.00; // 利息税费(贷款清还使用)
	private double dRealInterestTax = 0.00; //实际支付利息税费(贷款清还使用)
	private long SpecialOperationTypeID = -1;    //特殊交易类型

	private String saccountno_zhubi=null;       //付款方账户（逐笔付款-业务办理放大镜专用）
	private String currentbalance_zhubi=null;   //可用余额  （逐笔付款-业务办理放大镜专用）
	private String n_balance_zhubi=null;       //当前余额   （逐笔付款-业务办理放大镜专用）
	private long nAccountID=-1;                // 账户ID    （逐笔付款-业务办理放大镜专用）
	
	 
	//  关联名称
	private String sStatus = ""; // 指令状态
	private double dUsableUpSaveAmount = 0.0; //可用上存金额
	private String sRemitType = ""; // 汇款方式
	private String sTransType = ""; // 网上交易类型名称
	private String sConfirmUserName = ""; // 确认人姓名
	private String sCheckUserName = ""; // 复核人姓名
	private String sSignUserName = ""; // 签认人姓名
	private String sDeleteUserName = ""; // 删除人姓名
	private String sOfficeName = ""; // CPF-默认办事处名称
	private String sDefaultTransType = ""; // CPF-默认业务类型名称
	private String sDealTransType = ""; // CPF-处理业务类型名称
	private String sDealUserName = ""; // CPF-处理人姓名
	private double dCurrentBalance = 0.0; // 当前金额
	private double dUsableBalance = 0.0; // 可用金额
	private double dBalance = 0.0; //贷款余额
	private double dOverdraftAmount = 0.0; // 透支限额
	private double dMaxUsableAmount = 0.0; // 最大可发放金额
	private long lIsCycleAccount = 0; // 是否是循环贷款账户
	//查询结果关联
	private boolean bIsNeedSign = false; //是否需要该用户签认
	private double dRate = 0;//放款单的执行利率
	private String sReturnMsg = "";//失败原因（返回错误信息）
	
	//异步结算自动接受操作标志： 保存和复核操作
	public final static long Auto_OperationType_SaveAndCheck = 1;
	//异步结算自动接受操作标志： 取消保存和复核操作
	public final static long Auto_OperationType_CancelSaveAndCheck = 2;
	//自动收款付款的操作类型表示，标示是正向操作(saveAndCheckAutomatically)还是逆向操作（cancelSaveAndCheckAutomatically）
	private long autoOperationType = -1;
	
	
	//新增字段
	private long budgetItemID = -1;  //预算项目id
	private String sDepositBillNo = "";//定期存单号
	private Timestamp sDepositBillStartDate = null;//新子账户开始日
	private Timestamp sDepositBillEndDate = null;//新子账户结束日
	private long sDepositBillPeriod = -1;//新子账户的期限
	private long sDepositInterestDeal = -1;//新定期存款处理方式
	private long sDepositInterestToAccountID = -1;//利息转至活期账户ID
	private long nDepositBillStatusId = -1;//换开定期存单交易状态
	private long nDepositBillInputuserId = -1;//换开定期存单录入人
	private long nDepositBillCheckuserId = -1;//换开定期存单复核人
	private Timestamp dtDepositBillInputdate = null;//换开定期存单录入日期
	private Timestamp dtDepositBillCheckdate = null;//换开定期存单复核日期
	private String sDepositBillAbstract  = "";//换开定期存单录入摘要
	private String sDepositBillCheckAbstract = "";//换开定期存单复核摘要
	private long nDepositBillSignUserID = -1;//换开定期存单网银签认人ID
	private Timestamp dtDepositBillSignDate = null;//换开定期存单网银签认人复核日期
	private long userID = -1;
	private long isFixContinue = -1;//到期是否续存
	private double mamOuntForTrans = 0.0;//定期留存金额
	private String fixEdremark = "";//备注（定期是否续存）
	// 华联财务网银批量付款增加字段
    private long isSameBank  	= -1; //华联新加，是否同行
    
    private long isDiffLocal  	= -1; //华联新加，是否同城
	//一汽财务网银批量付款增加字段
	private long nCount = -1;//总笔数
	private InutParameterInfo inutParameterInfo = null;
	private String Abstract = ""; //摘要
	private String signatureValue = ""; 
	private String signatureOriginalValue = "";
	private long actionStatus = -1; //与审批、复核、签名有关的状态（签名时用到）
	private long nextLevel = -1;		//下一级审批级别（审批流用，供签名时区别）
	private boolean isRefused = false;	// 是否被拒绝过
    private long source = -1; //数据来源
    private String applyCode = ""; //业务申请编号
    private double advanceRate = 0.0;  //定期支取活期利率
    private long bankID = -1 ;//银行ID
    private double commissionAmount = 0.0; //手续费金额
    private long  commissionType  = -1; //手续费类型
    private Timestamp dtModify = null;//修改日期
	//排序字段
	private String OrderByCode = "";
	
	private long nUserID=-1;//用户ID
	private long nEbankStatus = -1; //银行指令状态
	private long isAutoContinue = -1;//是否自动转续存
	private long autocontinuetype = -1;//自动转续存类型（本金or本息）
	private long autocontinueaccountid = -1;//利息转至活期账户号
	
	//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
	private long remitArea = -1;	//汇款区域
	private long remitSpeed = Constant.remitSpeedType.GENERAL;	//汇款速度
	
	
	//财企接口新增
	private String sPayeeBankExchangeNO = "";  //汇入行联行号
	private String sPayeeBankCNAPSNO = "";  //汇入行CNAPS号
	private String sPayeeBankOrgNO = "";  //汇入行机构号
	
	private String sInterestPayeeBankExchangeNO = "";   //利息汇入行联行号
	private String sInterestPayeeBankCNAPSNO = "";  //利息汇入行CNAPS号
	private String sInterestPayeeBankOrgNO = "";  //利息汇入行机构号
	
	private String bankName = "";   //汇入行全称
	private String sInterestBankName = "";  //利息汇入行全称
	
	private Timestamp tsNoticeDate = null; //通知日期
	
	private long lSource = -1; //数据来源
	
	private long timestamp = -1;   //时间戳
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getLSource() {
		return lSource;
	}
	public void setLSource(long source) {
		lSource = source;
	}
	public String getSInterestBankName() {
		return sInterestBankName;
	}
	public void setSInterestBankName(String interestBankName) {
		sInterestBankName = interestBankName;
	}
	public String getSPayeeBankExchangeNO() {
		return sPayeeBankExchangeNO;
	}
	public void setSPayeeBankExchangeNO(String sPayeeBankExchangeNO) {
		this.sPayeeBankExchangeNO = sPayeeBankExchangeNO;
	}
	public String getSPayeeBankCNAPSNO() {
		return sPayeeBankCNAPSNO;
	}
	public void setSPayeeBankCNAPSNO(String sPayeeBankCNAPSNO) {
		this.sPayeeBankCNAPSNO = sPayeeBankCNAPSNO;
	}
	public String getSPayeeBankOrgNO() {
		return sPayeeBankOrgNO;
	}
	public void setSPayeeBankOrgNO(String sPayeeBankOrgNO) {
		this.sPayeeBankOrgNO = sPayeeBankOrgNO;
	}

	public long getRemitArea() {
		return remitArea;
	}
	public void setRemitArea(long remitArea) {
		this.remitArea = remitArea;
	}
	public long getRemitSpeed() {
		return remitSpeed;
	}
	public void setRemitSpeed(long remitSpeed) {
		this.remitSpeed = remitSpeed;
	}
	
	public long getNAccountID() {
		return nAccountID;
	}
	public void setNAccountID(long nAccountID) {
		this.nAccountID = nAccountID;
	}
	public String getSaccountno_zhubi() {
		return saccountno_zhubi;
	}
	public void setSaccountno_zhubi(String saccountno_zhubi) {
		this.saccountno_zhubi = saccountno_zhubi;
	}
	public String getCurrentbalance_zhubi() {
		return currentbalance_zhubi;
	}
	public void setCurrentbalance_zhubi(String currentbalance_zhubi) {
		this.currentbalance_zhubi = currentbalance_zhubi;
	}	

	public String getN_balance_zhubi() {
		return n_balance_zhubi;
	}
	public void setN_balance_zhubi(String n_balance_zhubi) {
		this.n_balance_zhubi = n_balance_zhubi;
	}	
	
	public String getSRemitType() {
		return sRemitType;
	}
	public void setSRemitType(String sRemitType) {
		this.sRemitType = sRemitType;
	}	
	public String getSTransType() {
		return sTransType;
	}
	public void setSTransType(String sTransType) {
		this.sTransType = sTransType;
	}	
	public String getSDefaultTransType() {
		return sDefaultTransType;
	}
	public void setSDefaultTransType(String sDefaultTransType) {
		this.sDefaultTransType = sDefaultTransType;
	}	
	public String getSDealTransType() {
		return sDealTransType;
	}
	public void setSDealTransType(String sDealTransType) {
		this.sDealTransType = sDealTransType;
	}	
	
	public Timestamp getSDepositBillStartDate() {
		return sDepositBillStartDate;
	}
	public void setSDepositBillStartDate(Timestamp sDepositBillStartDate) {
		this.sDepositBillStartDate = sDepositBillStartDate;
	}
	public Timestamp getSDepositBillEndDate() {
		return sDepositBillEndDate;
	}
	public void setSDepositBillEndDate(Timestamp sDepositBillEndDate) {
		this.sDepositBillEndDate = sDepositBillEndDate;
	}
	public long getSDepositBillPeriod() {
		return sDepositBillPeriod;
	}
	public void setSDepositBillPeriod(long sDepositBillPeriod) {
		this.sDepositBillPeriod = sDepositBillPeriod;
	}
	public long getSDepositInterestDeal() {
		return sDepositInterestDeal;
	}
	public void setSDepositInterestDeal(long sDepositInterestDeal) {
		this.sDepositInterestDeal = sDepositInterestDeal;
	}
	public long getSDepositInterestToAccountID() {
		return sDepositInterestToAccountID;
	}
	public void setSDepositInterestToAccountID(long sDepositInterestToAccountID) {
		this.sDepositInterestToAccountID = sDepositInterestToAccountID;
	}
	public long getNDepositBillSignUserID() {
		return nDepositBillSignUserID;
	}
	public void setNDepositBillSignUserID(long nDepositBillSignUserID) {
		this.nDepositBillSignUserID = nDepositBillSignUserID;
	}	
	
	public long getNCount() {
		return nCount;
	}
	public void setNCount(long Count) {
		this.nCount = Count;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String Abstract) {
		this.Abstract = Abstract;
	}	
	
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}	
	
	public String getSignatureOriginalValue() {
		return signatureOriginalValue;
	}
	public void setSignatureOriginalValue(String signatureOriginalValue) {
		this.signatureOriginalValue = signatureOriginalValue;
	}
	
	public long getNextLevel() {
		return nextLevel;
	}
	public void setNextLevel(long nextLevel) {
		this.nextLevel = nextLevel;
	}
	
	public Timestamp getDtModify(){
		return dtModify;
	}
	public void setDtModify(Timestamp dtModify){
		
		this.dtModify=dtModify;
	}
	public long getNUserID() {
		return nUserID;
	}
	public void setNUserID(long nUserID) {
		this.nUserID = nUserID;
	}	

	
	public String getSBatchNo() {
		return sBatchNo;
	}
	public void setSBatchNo(String BatchNo) {
		sBatchNo = BatchNo;
	}
	
	
	
	
	public long getEbankStatus() {
		return nEbankStatus;
	}
	public void setEbankStatus(long ebankStatus) {
		nEbankStatus = ebankStatus;
	}
	public double getAdvanceRate() {
		return advanceRate;
	}
	public void setAdvanceRate(double advanceRate) {
		this.advanceRate = advanceRate;
	}
	public String getOrderByCode()
	{
		return OrderByCode;
	}
	public void setOrderByCode(String orderByCode)
	{
		OrderByCode = orderByCode;
	}
	public Timestamp getDtDepositBillCheckdate()
	{
		return dtDepositBillCheckdate;
	}
	public void setDtDepositBillCheckdate(Timestamp dtDepositBillCheckdate)
	{
		this.dtDepositBillCheckdate = dtDepositBillCheckdate;
	}
	public Timestamp getDtDepositBillInputdate()
	{
		return dtDepositBillInputdate;
	}
	public void setDtDepositBillInputdate(Timestamp dtDepositBillInputdate)
	{
		this.dtDepositBillInputdate = dtDepositBillInputdate;
	}
	public long getNDepositBillCheckuserId()
	{
		return nDepositBillCheckuserId;
	}
	public void setNDepositBillCheckuserId(long depositBillCheckuserId)
	{
		nDepositBillCheckuserId = depositBillCheckuserId;
	}
	public long getNDepositBillInputuserId()
	{
		return nDepositBillInputuserId;
	}
	public void setNDepositBillInputuserId(long depositBillInputuserId)
	{
		nDepositBillInputuserId = depositBillInputuserId;
	}
	public String getSDepositBillAbstract()
	{
		return sDepositBillAbstract;
	}
	public void setSDepositBillAbstract(String depositBillAbstract)
	{
		sDepositBillAbstract = depositBillAbstract;
	}
	public String getSDepositBillCheckAbstract()
	{
		return sDepositBillCheckAbstract;
	}
	public void setSDepositBillCheckAbstract(String depositBillCheckAbstract)
	{
		sDepositBillCheckAbstract = depositBillCheckAbstract;
	}
	public String getSDepositBillNo()
	{
		return sDepositBillNo;
	}
	public void setSDepositBillNo(String depositBillNo)
	{
		sDepositBillNo = depositBillNo;
	}
	public long getNDepositBillStatusId()
	{
		return nDepositBillStatusId;
	}
	public void setNDepositBillStatusId(long nDepositBillStatusId)
	{
		this.nDepositBillStatusId = nDepositBillStatusId;
	}

	
	/**
	 * @return
	 */
	public double getAmount()
	{
		return dAmount;
	}
	public String getFormatAmount()
	{
		return DataFormat.formatDisabledAmount(dAmount);
	}
	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return lCheckUserID;
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
	public long getConfirmUserID()
	{
		return lConfirmUserID;
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
	public long getDealTransType()
	{
		return lDealTransType;
	}
	/**
	 * @return
	 */
	public long getDealUserID()
	{
		return lDealUserID;
	}
	/**
	 * @return
	 */
	public long getDefaultTransType()
	{
		return lDefaultTransType;
	}
	/**
	 * @return
	 */
	public long getDeleteUserID()
	{
		return lDeleteUserID;
	}
	/**
	 * @return
	 */
	public long getFixedDepositTime()
	{
		return lFixedDepositTime;
	}
	/**
	 * @return
	 */
	public long getID()
	{
		return lID;
	}
	/**
	 * @return
	 */
	public long getIsCpfAcct()
	{
		return lIsCpfAcct;
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
	public long getRemitType()
	{
		return lRemitType;
	}
	public String getFormatRemitType()
	{
		return OBConstant.SettRemitType.getName(lRemitType);
	}
	/**
	 * @return
	 */
	public long getSignUserID()
	{
		return lSignUserID;
	}
	/**
	 * @return
	 */
	public long getStatus()
	{
		return lStatus;
	}
	/**
	 * @return
	 */
	public long getTransType()
	{
		return lTransType;
	}
	/**
	 * @return
	 */
	public String getLetOutCode()
	{
		return sLetOutCode;
	}
	/**
	 * @return
	 */
	public String getLoanContractNo()
	{
		return sLoanContractNo;
	}
	/**
	 * @return
	 */
	public String getNote()
	{
		return sNote;
	}
	/**
	 * @return
	 */
	public String getPayeeAcctNo()
	{
		return sPayeeAcctNo;
	}
	/**
	 * @return
	 */
	public String getPayeeBankName()
	{
		return sPayeeBankName;
	}
	/**
	 * @return
	 */
	public String getPayeeBankNo()
	{
		return sPayeeBankNo;
	}
	/**
	 * @return
	 */
	public String getPayeeCity()
	{
		return sPayeeCity;
	}
	/**
	 * @return
	 */
	public String getPayeeName()
	{
		return sPayeeName;
	}
	/**
	 * @return
	 */
	public String getPayeeProv()
	{
		return sPayeeProv;
	}
	/**
	 * @return
	 */
	public String getPayerAcctNo()
	{
		return sPayerAcctNo;
	}
	/**
	 * @return
	 */
	public String getPayerBankNo()
	{
		return sPayerBankNo;
	}
	/**
	 * @return
	 */
	public String getPayerName()
	{
		return sPayerName;
	}
	/**
	 * @return
	 */
	public String getReject()
	{
		return sReject;
	}
	/**
	 * @return
	 */
	public String getTransNo()
	{
		return sTransNo;
	}
	/**
	 * @return
	 */
	public Timestamp getCheckDate()
	{
		return tsCheckDate;
	}
	public String getFormatCheckDate()
	{
		return DataFormat.getDateTimeString(tsCheckDate);
	}
	/**
	 * @return
	 */
	public Timestamp getConfirmDate()
	{
		return tsConfirmDate;
	}
	public String getFormatConfirmDate()
	{
		return DataFormat.getDateTimeString(tsConfirmDate);
	}
	/**
	 * @return
	 */
	public Timestamp getDealDate()
	{
		return tsDealDate;
	}
	public String getFormatDealDate()
	{
		return DataFormat.getDateTimeString(tsDealDate);
	}
	/**
	 * @return
	 */
	public Timestamp getDeleteDate()
	{
		return tsDeleteDate;
	}
	public String getFormatDeleteDate()
	{
		return DataFormat.getDateTimeString(tsDeleteDate);
	}
	/**
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return tsExecuteDate;
	}
	public String getFormatExecuteDate()
	{
		return DataFormat.getDateString(tsExecuteDate);
	}
	/**
	 * @return
	 */
	public Timestamp getFinishDate()
	{
		return tsFinishDate;
	}
	public String getFormatFinishDate()
	{
		return DataFormat.getDateTimeString(tsFinishDate);
	}
	/**
	 * @return
	 */
	public Timestamp getLoanEndDate()
	{
		return tsLoanEndDate;
	}
	public String getFormatLoanEndDate()
	{
		return DataFormat.getDateTimeString(tsLoanEndDate);
	}
	/**
	 * @return
	 */
	public Timestamp getPayDate()
	{
		return tsPayDate;
	}
	public String getFormatPayDate()
	{
		return DataFormat.getDateTimeString(tsPayDate);
	}
	/**
	 * @return
	 */
	public Timestamp getSignDate()
	{
		return tsSignDate;
	}
	public String getFormatSignDate()
	{
		return DataFormat.getDateTimeString(tsSignDate);
	}
	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		dAmount = d;
	}
	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		lCheckUserID = l;
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
	public void setConfirmUserID(long l)
	{
		lConfirmUserID = l;
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
	public void setDealTransType(long l)
	{
		lDealTransType = l;
	}
	/**
	 * @param l
	 */
	public void setDealUserID(long l)
	{
		lDealUserID = l;
	}
	/**
	 * @param l
	 */
	public void setDefaultTransType(long l)
	{
		lDefaultTransType = l;
	}
	/**
	 * @param l
	 */
	public void setDeleteUserID(long l)
	{
		lDeleteUserID = l;
	}
	/**
	 * @param l
	 */
	public void setFixedDepositTime(long l)
	{
		lFixedDepositTime = l;
	}
	/**
	 * @param l
	 */
	public void setID(long l)
	{
		lID = l;
	}
	/**
	 * @param l
	 */
	public void setIsCpfAcct(long l)
	{
		lIsCpfAcct = l;
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
	public void setRemitType(long l)
	{
		lRemitType = l;
	}
	/**
	 * @param l
	 */
	public void setSignUserID(long l)
	{
		lSignUserID = l;
	}
	/**
	 * @param l
	 */
	public void setStatus(long l)
	{
		lStatus = l;
	}
	/**
	 * @param l
	 */
	public void setTransType(long l)
	{
		lTransType = l;
	}
	/**
	 * @param string
	 */
	public void setLetOutCode(String string)
	{
		sLetOutCode = string;
	}
	/**
	 * @param string
	 */
	public void setLoanContractNo(String string)
	{
		sLoanContractNo = string;
	}
	/**
	 * @param string
	 */
	public void setNote(String string)
	{
		sNote = string;
	}
	/**
	 * @param string
	 */
	public void setPayeeAcctNo(String string)
	{
		sPayeeAcctNo = string;
	}
	/**
	 * @param string
	 */
	public void setPayeeBankName(String string)
	{
		sPayeeBankName = string;
	}
	/**
	 * @param string
	 */
	public void setPayeeBankNo(String string)
	{
		sPayeeBankNo = string;
	}
	/**
	 * @param string
	 */
	public void setPayeeCity(String string)
	{
		sPayeeCity = string;
	}
	/**
	 * @param string
	 */
	public void setPayeeName(String string)
	{
		sPayeeName = string;
	}
	/**
	 * @param string
	 */
	public void setPayeeProv(String string)
	{
		sPayeeProv = string;
	}
	public long getOperationTypeID() {
		return lOperationTypeID;
	}
	public void setOperationTypeID(long l) {
		lOperationTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setPayerAcctNo(String string)
	{
		sPayerAcctNo = string;
	}
	/**
	 * @param string
	 */
	public void setPayerBankNo(String string)
	{
		sPayerBankNo = string;
	}
	/**
	 * @param string
	 */
	public void setPayerName(String string)
	{
		sPayerName = string;
	}
	/**
	 * @param string
	 */
	public void setReject(String string)
	{
		sReject = string;
	}
	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		sTransNo = string;
	}
	/**
	 * @param timestamp
	 */
	public void setCheckDate(Timestamp timestamp)
	{
		tsCheckDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setConfirmDate(Timestamp timestamp)
	{
		tsConfirmDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setDealDate(Timestamp timestamp)
	{
		tsDealDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setDeleteDate(Timestamp timestamp)
	{
		tsDeleteDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		tsExecuteDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setFinishDate(Timestamp timestamp)
	{
		tsFinishDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setLoanEndDate(Timestamp timestamp)
	{
		tsLoanEndDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setPayDate(Timestamp timestamp)
	{
		tsPayDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setSignDate(Timestamp timestamp)
	{
		tsSignDate = timestamp;
	}
	/**
	 * @return
	 */
	public double getCurrentBalance()
	{
		return dCurrentBalance;
	}
	public String getFormatCurrentBalance()
	{
		return DataFormat.formatDisabledAmount(dCurrentBalance);
	}
	/**
	 * @return
	 */
	public double getMaxUsableAmount()
	{
		return dMaxUsableAmount;
	}
	public String getFormatMaxUsableAmount()
	{
		return DataFormat.formatDisabledAmount(dMaxUsableAmount);
	}
	/**
	 * @return
	 */
	public double getOverdraftAmount()
	{
		return dOverdraftAmount;
	}
	public String getFormatOverdraftAmount()
	{
		return DataFormat.formatDisabledAmount(dOverdraftAmount);
	}
	/**
	 * @return
	 */
	public double getUsableBalance()
	{
		return dUsableBalance;
	}
	public String getFormatUsableBalance()
	{
		return DataFormat.formatDisabledAmount(dUsableBalance);
	}
	/**
	 * @return
	 */
	public long getIsCycleAccount()
	{
		return lIsCycleAccount;
	}
	/**
	 * @param d
	 */
	public void setCurrentBalance(double d)
	{
		dCurrentBalance = d;
	}
	/**
	 * @param d
	 */
	public void setMaxUsableAmount(double d)
	{
		dMaxUsableAmount = d;
	}
	/**
	 * @param d
	 */
	public void setOverdraftAmount(double d)
	{
		dOverdraftAmount = d;
	}
	/**
	 * @param d
	 */
	public void setUsableBalance(double d)
	{
		dUsableBalance = d;
	}
	/**
	 * @param l
	 */
	public void setIsCycleAccount(long l)
	{
		lIsCycleAccount = l;
	}
	/**
	 * @return
	 */
	public long getPayeeAcctID()
	{
		return lPayeeAcctID;
	}
	/**
	 * @return
	 */
	public long getPayerAcctID()
	{
		return lPayerAcctID;
	}
	/**
	 * @param l
	 */
	public void setPayeeAcctID(long l)
	{
		lPayeeAcctID = l;
	}
	/**
	 * @param l
	 */
	public void setPayerAcctID(long l)
	{
		lPayerAcctID = l;
	}
	/**
	 * @return
	 */
	public double getUsableUpSaveAmount()
	{
		return dUsableUpSaveAmount;
	}
	/**
	 * @return
	 */
	public String getCheckUserName()
	{
		return sCheckUserName;
	}
	/**
	 * @return
	 */
	public String getConfirmUserName()
	{
		return sConfirmUserName;
	}
	/**
	 * @return
	 */
	public String getDealUserName()
	{
		return sDealUserName;
	}
	/**
	 * @return
	 */
	public String getDeleteUserName()
	{
		return sDeleteUserName;
	}
	/**
	 * @return
	 */
	public String getOfficeName()
	{
		return sOfficeName;
	}
	/**
	 * @return
	 */
	public String getSignUserName()
	{
		return sSignUserName;
	}
	/**
	 * @param d
	 */
	public void setUsableUpSaveAmount(double d)
	{
		dUsableUpSaveAmount = d;
	}
	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		sCheckUserName = string;
	}
	/**
	 * @param string
	 */
	public void setConfirmUserName(String string)
	{
		sConfirmUserName = string;
	}
	/**
	 * @param string
	 */
	public void setDealTransType(String string)
	{
		sDealTransType = string;
	}
	/**
	 * @param string
	 */
	public void setDealUserName(String string)
	{
		sDealUserName = string;
	}
	/**
	 * @param string
	 */
	public void setDefaultTransType(String string)
	{
		sDefaultTransType = string;
	}
	/**
	 * @param string
	 */
	public void setDeleteUserName(String string)
	{
		sDeleteUserName = string;
	}
	/**
	 * @param string
	 */
	public void setOfficeName(String string)
	{
		sOfficeName = string;
	}
	/**
	 * @param string
	 */
	public void setRemitType(String string)
	{
		sRemitType = string;
	}
	/**
	 * @param string
	 */
	public void setSignUserName(String string)
	{
		sSignUserName = string;
	}
	/**
	 * @param string
	 */
	public void setSStatus(String sStatus)
	{
		this.sStatus = sStatus;
	}
	public String getSStatus()
	{
		return sStatus;
	}
	/**
	 * @param string
	 */
	public void setTransType(String string)
	{
		sTransType = string;
	}
	/**
	 * @return
	 */
	public long getNoticeDay()
	{
		return lNoticeDay;
	}
	/**
	 * @param l
	 */
	public void setNoticeDay(long l)
	{
		lNoticeDay = l;
	}
	/**
	 * @return
	 */
	public double getDepositAmount()
	{
		return dDepositAmount;
	}
	/**
	 * @return
	 */
	public double getDepositBalance()
	{
		return dDepositBalance;
	}
	/**
	 * @return
	 */
	public double getDepositRate()
	{
		return dDepositRate;
	}
	/**
	 * @return
	 */
	public String getDepositNo()
	{
		return sDepositNo;
	}
	/**
	 * @return
	 */
	public Timestamp getDepositStart()
	{
		return tsDepositStart;
	}
	/**
	 * @param d
	 */
	public void setDepositAmount(double d)
	{
		dDepositAmount = d;
	}
	/**
	 * @param d
	 */
	public void setDepositBalance(double d)
	{
		dDepositBalance = d;
	}
	/**
	 * @param d
	 */
	public void setDepositRate(double d)
	{
		dDepositRate = d;
	}
	/**
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		sDepositNo = string;
	}
	/**
	 * @param timestamp
	 */
	public void setDepositStart(Timestamp timestamp)
	{
		tsDepositStart = timestamp;
	}
	/**
	 * @return
	 */
	public double getCommission()
	{
		return dCommission;
	}
	/**
	 * @return
	 */
	public double getCompoundInterest()
	{
		return dCompoundInterest;
	}
	/**
	 * @return
	 */
	public double getInterest()
	{
		return dInterest;
	}
	/**
	 * @return
	 */
	public double getOverdueInterest()
	{
		return dOverdueInterest;
	}
	/**
	 * @return
	 */
	public double getRealCommission()
	{
		return dRealCommission;
	}
	/**
	 * @return
	 */
	public double getRealCompoundInterest()
	{
		return dRealCompoundInterest;
	}
	/**
	 * @return
	 */
	public double getRealInterest()
	{
		return dRealInterest;
	}
	/**
	 * @return
	 */
	public double getRealOverdueInterest()
	{
		return dRealOverdueInterest;
	}
	/**
	 * @return
	 */
	public double getRealSuretyFee()
	{
		return dRealSuretyFee;
	}
	/**
	 * @return
	 */
	public double getSuretyFee()
	{
		return dSuretyFee;
	}
	/**
	 * @return
	 */
	public long getInterestPayeeAcctID()
	{
		return lInterestPayeeAcctID;
	}
	/**
	 * @return
	 */
	public long getInterestRemitType()
	{
		return lInterestRemitType;
	}
	/**
	 * @param d
	 */
	public void setCommission(double d)
	{
		dCommission = d;
	}
	/**
	 * @param d
	 */
	public void setCompoundInterest(double d)
	{
		dCompoundInterest = d;
	}
	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		dInterest = d;
	}
	/**
	 * @param d
	 */
	public void setOverdueInterest(double d)
	{
		dOverdueInterest = d;
	}
	/**
	 * @param d
	 */
	public void setRealCommission(double d)
	{
		dRealCommission = d;
	}
	/**
	 * @param d
	 */
	public void setRealCompoundInterest(double d)
	{
		dRealCompoundInterest = d;
	}
	/**
	 * @param d
	 */
	public void setRealInterest(double d)
	{
		dRealInterest = d;
	}
	/**
	 * @param d
	 */
	public void setRealOverdueInterest(double d)
	{
		dRealOverdueInterest = d;
	}
	/**
	 * @param d
	 */
	public void setRealSuretyFee(double d)
	{
		dRealSuretyFee = d;
	}
	/**
	 * @param d
	 */
	public void setSuretyFee(double d)
	{
		dSuretyFee = d;
	}
	/**
	 * @param l
	 */
	public void setInterestPayeeAcctID(long l)
	{
		lInterestPayeeAcctID = l;
	}
	/**
	 * @param l
	 */
	public void setInterestRemitType(long l)
	{
		lInterestRemitType = l;
	}
	/**
	 * @return
	 */
	public String getInterestPayeeAcctNo()
	{
		return sInterestPayeeAcctNo;
	}
	/**
	 * @return
	 */
	public String getInterestPayeeBankName()
	{
		return sInterestPayeeBankName;
	}
	/**
	 * @return
	 */
	public String getInterestPayeeBankNo()
	{
		return sInterestPayeeBankNo;
	}
	/**
	 * @return
	 */
	public String getInterestPayeeCity()
	{
		return sInterestPayeeCity;
	}
	/**
	 * @return
	 */
	public String getInterestPayeeName()
	{
		return sInterestPayeeName;
	}
	/**
	 * @param string
	 */
	public void setInterestPayeeAcctNo(String string)
	{
		sInterestPayeeAcctNo = string;
	}
	/**
	 * @param string
	 */
	public void setInterestPayeeBankName(String string)
	{
		sInterestPayeeBankName = string;
	}
	/**
	 * @param string
	 */
	public void setInterestPayeeBankNo(String string)
	{
		sInterestPayeeBankNo = string;
	}
	/**
	 * @param string
	 */
	public void setInterestPayeeCity(String string)
	{
		sInterestPayeeCity = string;
	}
	/**
	 * @param string
	 */
	public void setInterestPayeeName(String string)
	{
		sInterestPayeeName = string;
	}
	/**
	 * @return
	 */
	public String getInterestPayeeProv()
	{
		return sInterestPayeeProv;
	}
	/**
	 * @param string
	 */
	public void setInterestPayeeProv(String string)
	{
		sInterestPayeeProv = string;
	}
	/**
	 * Returns the balance.
	 * @return double
	 */
	public double getBalance()
	{
		return dBalance;
	}
	/**
	 * Sets the balance.
	 * @param balance The balance to set
	 */
	public void setBalance(double balance)
	{
		dBalance = balance;
	}
	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getLoanNoteID()
	{
		return lLoanNoteID;
	}
	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setLoanNoteID(long loanNoteID)
	{
		lLoanNoteID = loanNoteID;
	}
	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return lContractID;
	}
	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		lContractID = contractID;
	}
	public String getFormatInterestRemitType()
	{
		return OBConstant.SettRemitType.getName(lInterestRemitType);
	}
	/**
	 * @return
	 */
	public long getSubAccountID()
	{
		return lSubAccountID;
	}
	/**
	 * @param l
	 */
	public void setSubAccountID(long l)
	{
		lSubAccountID = l;
	}
	/**
	 * @return
	 */
	public Timestamp getClearInterest()
	{
		return tsClearInterest;
	}
	/**
	 * @param timestamp
	 */
	public void setClearInterest(Timestamp timestamp)
	{
		tsClearInterest = timestamp;
	}
	/**
	 * Returns the commissionRate.
	 * @return double
	 */
	public double getCommissionRate()
	{
		return dCommissionRate;
	}
	/**
	 * Returns the compoundAmount.
	 * @return double
	 */
	public double getCompoundAmount()
	{
		return dCompoundAmount;
	}
	/**
	 * Returns the compoundRate.
	 * @return double
	 */
	public double getCompoundRate()
	{
		return dCompoundRate;
	}
	/**
	 * Returns the interestRate.
	 * @return double
	 */
	public double getInterestRate()
	{
		return dInterestRate;
	}
	/**
	 * Returns the overDueRate.
	 * @return double
	 */
	public double getOverDueRate()
	{
		return dOverDueRate;
	}
	/**
	 * Returns the suretyRate.
	 * @return double
	 */
	public double getSuretyRate()
	{
		return dSuretyRate;
	}
	/**
	 * Returns the interestIsCpfAcct.
	 * @return long
	 */
	public long getInterestIsCpfAcct()
	{
		return lInterestIsCpfAcct;
	}

	/**
	 * Returns the commissionStart.
	 * @return Timestamp
	 */
	public Timestamp getCommissionStart()
	{
		return tsCommissionStart;
	}
	/**
	 * Returns the compoundStart.
	 * @return Timestamp
	 */
	public Timestamp getCompoundStart()
	{
		return tsCompoundStart;
	}
	/**
	 * Returns the interestStart.
	 * @return Timestamp
	 */
	public Timestamp getInterestStart()
	{
		return tsInterestStart;
	}
	/**
	 * Returns the overDueStart.
	 * @return Timestamp
	 */
	public Timestamp getOverDueStart()
	{
		return tsOverDueStart;
	}
	/**
	 * Returns the suretyStart.
	 * @return Timestamp
	 */
	public Timestamp getSuretyStart()
	{
		return tsSuretyStart;
	}
	/**
	 * Sets the commissionRate.
	 * @param commissionRate The commissionRate to set
	 */
	public void setCommissionRate(double commissionRate)
	{
		dCommissionRate = commissionRate;
	}
	/**
	 * Sets the compoundAmount.
	 * @param compoundAmount The compoundAmount to set
	 */
	public void setCompoundAmount(double compoundAmount)
	{
		dCompoundAmount = compoundAmount;
	}
	/**
	 * Sets the compoundRate.
	 * @param compoundRate The compoundRate to set
	 */
	public void setCompoundRate(double compoundRate)
	{
		dCompoundRate = compoundRate;
	}
	/**
	 * Sets the interestRate.
	 * @param interestRate The interestRate to set
	 */
	public void setInterestRate(double interestRate)
	{
		dInterestRate = interestRate;
	}
	/**
	 * Sets the overDueRate.
	 * @param overDueRate The overDueRate to set
	 */
	public void setOverDueRate(double overDueRate)
	{
		dOverDueRate = overDueRate;
	}
	/**
	 * Sets the suretyRate.
	 * @param suretyRate The suretyRate to set
	 */
	public void setSuretyRate(double suretyRate)
	{
		dSuretyRate = suretyRate;
	}
	/**
	 * Sets the interestIsCpfAcct.
	 * @param interestIsCpfAcct The interestIsCpfAcct to set
	 */
	public void setInterestIsCpfAcct(long interestIsCpfAcct)
	{
		lInterestIsCpfAcct = interestIsCpfAcct;
	}

	/**
	 * Sets the commissionStart.
	 * @param commissionStart The commissionStart to set
	 */
	public void setCommissionStart(Timestamp commissionStart)
	{
		tsCommissionStart = commissionStart;
	}
	/**
	 * Sets the compoundStart.
	 * @param compoundStart The compoundStart to set
	 */
	public void setCompoundStart(Timestamp compoundStart)
	{
		tsCompoundStart = compoundStart;
	}
	/**
	 * Sets the interestStart.
	 * @param interestStart The interestStart to set
	 */
	public void setInterestStart(Timestamp interestStart)
	{
		tsInterestStart = interestStart;
	}
	/**
	 * Sets the overDueStart.
	 * @param overDueStart The overDueStart to set
	 */
	public void setOverDueStart(Timestamp overDueStart)
	{
		tsOverDueStart = overDueStart;
	}
	/**
	 * Sets the suretyStart.
	 * @param suretyStart The suretyStart to set
	 */
	public void setSuretyStart(Timestamp suretyStart)
	{
		tsSuretyStart = suretyStart;
	}
	/**
	 * Returns the overDueAmount.
	 * @return double
	 */
	public double getOverDueAmount()
	{
		return dOverDueAmount;
	}
	/**
	 * Sets the overDueAmount.
	 * @param overDueAmount The overDueAmount to set
	 */
	public void setOverDueAmount(double overDueAmount)
	{
		dOverDueAmount = overDueAmount;
	}
	/**
	 * @return
	 */
	public boolean getIsNeedSign()
	{
		return bIsNeedSign;
	}
	/**
	 * @param b
	 */
	public void setIsNeedSign(boolean b)
	{
		bIsNeedSign = b;
	}
	/**
	 * @return
	 */
	public long getIsCanAccept()
	{
		return lIsCanAccept;
	}
	/**
	 * @param l
	 */
	public void setIsCanAccept(long l)
	{
		lIsCanAccept = l;
	}

	/**
	 * Returns the interestIncome.
	 * @return double
	 */
	public double getInterestIncome()
	{
		return dInterestIncome;
	}
	/**
	 * Returns the interestReceiveable.
	 * @return double
	 */
	public double getInterestReceiveable()
	{
		return dInterestReceiveable;
	}
	/**
	 * Returns the realInterestIncome.
	 * @return double
	 */
	public double getRealInterestIncome()
	{
		return dRealInterestIncome;
	}
	/**
	 * Returns the realInterestReceiveable.
	 * @return double
	 */
	public double getRealInterestReceiveable()
	{
		return dRealInterestReceiveable;
	}

	/**
	 * Sets the interestIncome.
	 * @param interestIncome The interestIncome to set
	 */
	public void setInterestIncome(double interestIncome)
	{
		dInterestIncome = interestIncome;
	}
	/**
	 * Sets the interestReceiveable.
	 * @param interestReceiveable The interestReceiveable to set
	 */
	public void setInterestReceiveable(double interestReceiveable)
	{
		dInterestReceiveable = interestReceiveable;
	}
	/**
	 * Sets the realInterestIncome.
	 * @param realInterestIncome The realInterestIncome to set
	 */
	public void setRealInterestIncome(double realInterestIncome)
	{
		dRealInterestIncome = realInterestIncome;
	}
	/**
	 * Sets the realInterestReceiveable.
	 * @param realInterestReceiveable The realInterestReceiveable to set
	 */
	public void setRealInterestReceiveable(double realInterestReceiveable)
	{
		dRealInterestReceiveable = realInterestReceiveable;
	}
	/**
	 * Returns the interestTax.
	 * @return double
	 */
	public double getInterestTax()
	{
		return dInterestTax;
	}

	/**
	 * Returns the realInterestTax.
	 * @return double
	 */
	public double getRealInterestTax()
	{
		return dRealInterestTax;
	}

	/**
	 * Sets the interestTax.
	 * @param interestTax The interestTax to set
	 */
	public void setInterestTax(double interestTax)
	{
		dInterestTax = interestTax;
	}

	/**
	 * Sets the realInterestTax.
	 * @param realInterestTax The realInterestTax to set
	 */
	public void setRealInterestTax(double realInterestTax)
	{
		dRealInterestTax = realInterestTax;
	}

	/**
	 * @return
	 */
	public String getPayClientName() {
		return PayClientName;
	}

	/**
	 * @param string
	 */
	public void setPayClientName(String string) {
		PayClientName = string;
	}

	/**
	 * @return
	 */
	public long getSpecialOperationTypeID() {
		return SpecialOperationTypeID;
	}

	/**
	 * @param l
	 */
	public void setSpecialOperationTypeID(long l) {
		SpecialOperationTypeID = l;
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return dRate;
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		dRate = d;
	}

	/**
	 * @return
	 */
	public long getChildClientID()
	{
		return lChildClientID;
	}

	/**
	 * @param l
	 */
	public void setChildClientID(long l)
	{
		lChildClientID = l;
	}

	/**
	 * @return
	 */
	public String getChildClientName()
	{
		return sChildClientName;
	}

	/**
	 * @return
	 */
	public String getChildClientNo()
	{
		return sChildClientNo;
	}

	/**
	 * @param string
	 */
	public void setChildClientName(String string)
	{
		sChildClientName = string;
	}

	/**
	 * @param string
	 */
	public void setChildClientNo(String string)
	{
		sChildClientNo = string;
	}

	/**
	 * @return Returns the sReturnMsg.
	 */
	public String getReturnMsg() {
		return sReturnMsg;
	}
	/**
	 * @param returnMsg The sReturnMsg to set.
	 */
	public void setReturnMsg(String returnMsg) {
		sReturnMsg = returnMsg;
	}
	/**
	 * @return Returns the autoOperationType.
	 */
	public long getAutoOperationType()
	{
		return autoOperationType;
	}
	/**
	 * @param autoOperationType The autoOperationType to set.
	 */
	public void setAutoOperationType(long autoOperationType)
	{
		this.autoOperationType = autoOperationType;
	}
	/**
	 * @return Returns the budgetItemID.
	 */
	public long getBudgetItemID() {
		return budgetItemID;
	}
	/**
	 * @param budgetItemID The budgetItemID to set.
	 */
	public void setBudgetItemID(long budgetItemID) {
		this.budgetItemID = budgetItemID;
	}
	/**
	 * @return Returns the dtDepositBillSignDate.
	 */
	public Timestamp getDtDepositBillSignDate() {
		return dtDepositBillSignDate;
	}
	/**
	 * @param dtDepositBillSignDate The dtDepositBillSignDate to set.
	 */
	public void setDtDepositBillSignDate(Timestamp dtDepositBillSignDate) {
		this.dtDepositBillSignDate = dtDepositBillSignDate;
	}
	/**
	 * @return Returns the nDepositBillSignUserID.
	 */
	public long getUserID()
	{
		return userID;
	}
	public void setUserID(long userID)
	{
		this.userID = userID;
	}
	public long getIsFixContinue() {
		return isFixContinue;
	}
	public void setIsFixContinue(long isFixContinue) {
		this.isFixContinue = isFixContinue;
	}
	public String getFixEdremark() {
		return fixEdremark;
	}
	public void setFixEdremark(String fixEdremark) {
		this.fixEdremark = fixEdremark;
	}
	public double getMamOuntForTrans() {
		return mamOuntForTrans;
	}
	public void setMamOuntForTrans(double mamOuntForTrans) {
		this.mamOuntForTrans = mamOuntForTrans;
	}
	public boolean isRefused() {
		return isRefused;
	}
	public void setRefused(boolean isRefused) {
		this.isRefused = isRefused;
	}
	public long getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(long actionStatus) {
		this.actionStatus = actionStatus;
	}
    /**
     * @return Returns the source.
     */
    public long getSource() {
        return source;
    }
    /**
     * @param source The source to set.
     */
    public void setSource(long source) {
        this.source = source;
    }
    /**
     * @return Returns the applyCode.
     */
    public String getApplyCode() {
        return applyCode;
    }
    /**
     * @param applyCode The applyCode to set.
     */
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }
	public long getIsDiffLocal() {
		return isDiffLocal;
	}
	public void setIsDiffLocal(long isDiffLocal) {
		this.isDiffLocal = isDiffLocal;
	}
	public long getIsSameBank() {
		return isSameBank;
	}
	public void setIsSameBank(long isSameBank) {
		this.isSameBank = isSameBank;
	}
	public long getBankID() {
		return bankID;
	}
	public void setBankID(long bankID) {
		this.bankID = bankID;
	}
	public double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public long getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(long commissionType) {
		this.commissionType = commissionType;
	}
	public long getIsAutoContinue() {
		return isAutoContinue;
	}

	public void setIsAutoContinue(long isAutoContinue) {
		this.isAutoContinue = isAutoContinue;
	}

	public long getAutocontinuetype() {
		return autocontinuetype;
	}

	public void setAutocontinuetype(long autocontinuetype) {
		this.autocontinuetype = autocontinuetype;
	}

	public long getAutocontinueaccountid() {
		return autocontinueaccountid;
	}

	public void setAutocontinueaccountid(long autocontinueaccountid) {
		this.autocontinueaccountid = autocontinueaccountid;
	}
	public String getSInterestPayeeBankExchangeNO() {
		return sInterestPayeeBankExchangeNO;
	}
	
	public void setSInterestPayeeBankExchangeNO(String interestPayeeBankExchangeNO) {
		sInterestPayeeBankExchangeNO = interestPayeeBankExchangeNO;
	}
	public String getSInterestPayeeBankCNAPSNO() {
		return sInterestPayeeBankCNAPSNO;
	}
	public void setSInterestPayeeBankCNAPSNO(String interestPayeeBankCNAPSNO) {
		sInterestPayeeBankCNAPSNO = interestPayeeBankCNAPSNO;
	}
	public String getSInterestPayeeBankOrgNO() {
		return sInterestPayeeBankOrgNO;
	}
	public void setSInterestPayeeBankOrgNO(String interestPayeeBankOrgNO) {
		sInterestPayeeBankOrgNO = interestPayeeBankOrgNO;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Timestamp getTsNoticeDate() {
		return tsNoticeDate;
	}
	public void setTsNoticeDate(Timestamp tsNoticeDate) {
		this.tsNoticeDate = tsNoticeDate;
	}
	
	
}
	
/*
 * Created on 2003-11-17
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintInfo implements Serializable
{
	public PrintInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	//共用信息
	private long OfficeID = -1;
	private long CurrencyID = -1;
	private Timestamp ExecuteDate = null; //执行日
	private Timestamp InterestStartDate = null; //起息日
	private String TransNo = ""; //交易号
	private long TransTypeID = -1; //交易类型
	private long PayClientID = -1; //付款方客户ID
	private long PayAccountID = -1; //付款方账户ID
	private long PayBankID = -1; //付款方开户行ID
	private long ReceiveClientID = -1; //收款方客户ID
	private long ReceiveAccountID = -1; //收款方账户ID
	private long ReceiveBankID = -1; //收款方开户行ID
	private String ExtAccountNo = ""; //外部付款方账户编号
	private String ExtClientName = ""; //外部付款客户编号
	private String ExtRemitInBank = ""; //非财务公司银行名称
	private String ExtRemitInProvince = ""; //省
	private String ExtRemitInCity = ""; //市
	private double Amount = 0.0; //本金金额
	private String Abstract = ""; //摘要
	private long AbstractID = -1; //标准摘要ID
	private long InputUserID = -1; //录入人
	private long CheckUserID = -1; //复核人
	private String ManagerName = ""; //单位主管
	private Timestamp StartDate = null; //开始日期
	private Timestamp EndDate = null; //结束日期
	private Timestamp NewStartDate = null; //新的开始日期(为到期续存使用)
	private Timestamp NewEndDate = null; //新的结束日期(为到期续存使用)
	private double Rate = 0.0; //利率
	private double NewRate = 0.0; //新存单利率
	//利息通知单专用，标识当前利息通知单贷款或存款
	private boolean PayOrReceive = true; //true--存入，false--支取
	//定期(通知)
	private double FixedInterestAmount = 0.0; //定期收回利息
	private double PayInterestAmount = 0.0; //利息支出
	private double BookInterestAmount = 0.0; //挂账利息
	private String FixedDepositNo = ""; //定期存款单据号
	private long FixedDepositTerm = -1; //定期期限
	private long NoticeDay = -1; //通知存款支取日期（天）/通知存款类型（1天/7天）
	private long SubAccountID = -1; //子定期账户id
	private double PayableInterest = 0.0; //利息支出
	private double CurrentInterest = 0.0; //活期利息
	private double OtherInterest = 0.0; //其它利息
	private double TotalInterest = 0.0; //利息合计
	private double DrawAmount =0.0;  //提前支取金额
	private double DepositBalance = 0.0; //存款余额
	private String strDepositBillNO="";		//换开定期存单号 2006.3.27 为换开定期存单
	private long isAutoContinue=-1;//是否自动续存 add by kevin(刘连凯)2011-09-14
	/*
	 * the info below was added or edited by gqzhang,if problem here please
	 * contact me.extends 351
	 */
	//贷款
	private long ConsignClientID = -1; //委托方客户id
	private long ConsignAccountID = -1; //委托方
	private long ConsignDepositAccountID = -1; //委托活期账户号
	private long LoanAccountID = -1; //贷款方
	private long ContractID = -1; //合同号
	private long LoanNoteID = -1; //放款通知单号
	private double LoanBalance = 0.0; //贷款余额
	private double SumReceiveAmount = 0.0; //累计还款金额
	private double CurrentBalance = 0.0; //余额
	private long LoanTypeID = -1; //放款类型
	private long ExtendFormID = -1; //转期通知单
	private long PayInterestAccountID = -1; //付息账户号
	private long ReceiveInterestAccountID = -1; //收息账户号
	private long PaySuretyFeeAccountID = -1; //付担保费账户号（贷款方）
	private long ReceiveSuretyFeeAccountID = -1; //收担保费账户号（担保方）
	private long PayCommissionAccountID = -1; //付手续费账户号
	private double InterestTaxRate = 0.0; //利息税费率
	private Timestamp InterestTaxRateVauleDate = null; //利息税费生效日期
	private long PayTypeID = -1; //放款方式
	private long FreeFormID = -1; //免还通知单
	private String BillNo = ""; //票据号
	private long BillTypeID = -1; //票据类型ID
	private long BillBankID = -1; //发票银行ID
	private String ExtBankNo = ""; //提出行号
	private long PreFormID = -1; //提前还款通知单
	private long PayInterestBankID = -1; //付息银行ID
	private long PaySuertyFeeBankID = -1; //付担保费银行ID
	private long PayCommissionBankID = -1; //付手续费银行ID
	private double Interest = 0.0; //贷款利息
	private double InterestReceivable = 0.0; //计提利息
	private double InterestIncome = 0.0; //本次利息
	private double InterestTax = 0.0; //利息税费
	private Timestamp NormalInterestStart = null; //正常利息起息日
	private Timestamp NormalInterestEnd = null; //正常利息终息日
	private double CompoundInterest = 0.0; //复利
	private Timestamp CompoundInterestStart = null; //复利起息日
	private Timestamp CompoundInterestEnd = null; //复利终息日
	private double CompoundAmount = 0.0; //复利本金
	private double CompoundRate = 0.0; //复利利率
	private double OverDueInterest = 0.0; //逾期罚息
	private Timestamp OverDueStart = null; //逾期罚息起息日
	private Timestamp OverDueEnd = null; //逾期罚息终息日
	private double OverDueAmount = 0.0; //逾期罚息本金
	private double OverDueRate = 0.0; //逾期罚息利率
	private double SuretyFee = 0.0; //担保费
	private Timestamp SuretyFeeStart = null; //逾担保费起息日
	private Timestamp SuretyFeeEnd = null; //担保费息终息日
	private double SuretyFeeAmount = 0.0; //担保费本金
	private double SuretyFeeRate = 0.0; //担保费率
	private double Commission = 0.0; //手续费
	private Timestamp CommissionStart = null; //手续费起息日
	private Timestamp CommissionFeeEnd = null; //手续费息终息日
	private double CommissionAmount = 0.0; //手续费本金
	private double CommissionRate = 0.0; //手续费率
	private String AdjustInterestReason = ""; //利息调整原因
	private double AdjustInterest = 0.0; //利息调整金额
	private double AheadReceiveInterest = 0.0; //提前还款利息金额
	private double RealInterest = 0.0; //实际支付贷款利息
	private double RealInterestReceivable = 0.0; //实际支付计提利息
	private double RealInterestIncome = 0.0; //实际支付本次利息
	private double RealInterestTax = 0.0; //实际支付利息税费
	private double RealCompoundInterest = 0.0; //实际支付复利
	private double RealOverDueInterest = 0.0; //实际支付逾期罚息
	private double RealSuretyFee = 0.0; //实际支付担保费
	private double RealCommission = 0.0; //实际支付手续费
	private Timestamp InterestClearDate = null; //结息日
	private Timestamp LatestInterestClearDate = null; //上次结息日期
	//add by xwhe 2008-08-07
    private String ExamUserName = "";//审批人
    //add by xwhe 2008-08-21 活期分段计息
    private Timestamp startDate1 = null; //开始日
    private Timestamp startDate2 = null; //开始日
    private Timestamp startDate3 = null; //开始日
    private Timestamp endDate1 = null; //结束日
    private Timestamp endDate2 = null; //结束日
    private Timestamp endDate3= null; //结束日
    private double rate1 = 0.0; //利率
    private double rate2= 0.0; //利率
    private double rate3 = 0.0; //利率
    private double interest1 = 0.0;
    private double interest2 = 0.0;
    private double interest3 = 0.0;
    
    //add by leiyang3 2011-01-17 复利分段计息
    private Timestamp compoundStartDate1 = null; //开始日
    private Timestamp compoundStartDate2 = null; //开始日
    private Timestamp compoundStartDate3 = null; //开始日
    private Timestamp compoundEndDate1 = null; //结束日
    private Timestamp compoundEndDate2 = null; //结束日
    private Timestamp compoundEndDate3= null; //结束日
    private double compoundRate1 = 0.0; //利率
    private double compoundRate2= 0.0; //利率
    private double compoundRate3 = 0.0; //利率
    private double compoundInterest1 = 0.0;
    private double compoundInterest2 = 0.0;
    private double compoundInterest3 = 0.0;
    //add by leiyang3 2011-01-17 罚息分段计息
    private Timestamp overDueStartDate1 = null; //开始日
    private Timestamp overDueStartDate2 = null; //开始日
    private Timestamp overDueStartDate3 = null; //开始日
    private Timestamp overDueEndDate1 = null; //结束日
    private Timestamp overDueEndDate2 = null; //结束日
    private Timestamp overDueEndDate3= null; //结束日
    private double overDueRate1 = 0.0; //利率
    private double overDueRate2= 0.0; //利率
    private double overDueRate3 = 0.0; //利率
    private double overDueInterest1 = 0.0;
    private double overDueInterest2 = 0.0;
    private double overDueInterest3 = 0.0;
    
    private Timestamp NegotiateStartDate1 = null ;//协定开始日期
    private Timestamp NegotiateEndtDate1 = null ;//协定结束日期
    private Timestamp NegotiateStartDate2 = null ;//协定开始日期
    private Timestamp NegotiateEndtDate2 = null ;//协定结束日期
    private double NegotiateRate1 = 0.0; //协定利率
    private double NegotiateRate2= 0.0; //协定利率
    private double NegotiateInterest1 = 0.0;
    private double NegotiateInterest2 = 0.0;
    private double sumBlanceAmount1 = 0.0;
    private double sumBlanceAmount2 = 0.0;
    private double sumBlanceAmount3 = 0.0;
    
    private double sumNegoBlanceAmount1 = 0.0;//协定
    private double sumNegoBlanceAmount2 = 0.0;
    private double sumNegoBlanceAmount3 = 0.0;
 
	/*
	* the info above was added or edited by gqzhang,if problem here please
	* contact me.extends 351
	*/
	//贴现
	private double DiscountBillAmount = 0.0; //汇票金额
	private double DiscountInterestAmount = 0.0; //贴现利息
	private double DiscountAmount = 0.0; //贴现金额
	private long DiscountNoteID = -1; //凭证ID
	private long DiscountBillID = -1; //汇票ID
	
	//for 转贴现台账,add by ygzhao 2004-11-20 --start
	private String DiscountBillNo = ""; //汇票号码
	private String BillAcceptanceUser = ""; //汇票承兑人
    private String AcceptanceUserBank = ""; //承兑人开户银行
    private String AcceptanceUserAccount = ""; //承兑人账号
    private String ApplicantName = "";//申请人名称
    private String ApplicantAccountBankNo = ""; //申请人开户银行
    private String ApplicantAccountNo = ""; //申请人账号
	//for 转贴现台账,add by ygzhao 2004-11-20 --end
    
	//活期
	private long payGL = -1; //付方总账科目
	private long receiveGL = -1; //收方科目
	//一付多收
	private String PayExtAccountNo = ""; //外部付款方账户编号
	private String PayExtClientName = ""; //外部付款客户编号
	private String PayExtRemitInBank = ""; //非财务公司银行名称
	private String PayExtRemitInProvince = ""; //省
	private String PayExtRemitInCity = ""; //市
	private String ReceiveExtAccountNo = ""; //外部收款款方账户编号
	private String ReceiveExtClientName = ""; //外部收款客户编号
	private String ReceiveExtRemitInBank = ""; //非财务公司银行名称
	private String ReceiveExtRemitInProvince = ""; //省
	private String ReceiveExtRemitInCity = ""; //市
	//协定利息本金
	private double AccordInterestAmount = 0.0;
	//协定利息利率
	private double AccordInterestRate = 0.0;
	//协定利息
	private double AccordInterest = 0.0;
	//通知书
	private String FormYear = ""; //通知书年度
	private String FormNo = ""; //通知书单号
	private long FormNum = -1;
	private String BorrowClientName = ""; //借款人名称
	private long BorrowClientID = -1; //借款客户ID
	private String AssureName = ""; //但保人名称
	private long AssureContractID = -1; //担保合同号
	private Timestamp clearInterestDate = null; //结息日
	private double ContractRate = 0.0; //合同利率
	private double ExecuteRate = 0.0; //执行利率
	private double ExecuteRateNew = 0.0; //调整后的执行利率
	private Timestamp AdjustRateDate = null; //利率调整日期
	private long LoanTerm = -1; //贷款期限
	//利息通知书中表头账号专用
	private long SpecialAccountID = -1;
	private long AccountTypeID = -1; //账户类型
	private long StatusID = -1; //交易状态
	
	private long TransactionTypeID = -1;
	private long AccountID = -1;
	private long InterestType = -1;
	private long InterestFeeSettingDetailID = -1;
	private long BatchNo = -1;
	private Timestamp AutoExecute = null;
	private String CheckAbstract = "";
	private long IsSave = -1;
	private long IsKeepAccount = -1;
	private long IsSuccess = -1;
	private String FaultReason = "";
	private long NegotiateInterest = -1;
	private long NegotiatePecisionInterest = -1;
	private long NegotiateBalance = -1;
	private long PecisionInterest = -1;
	private double NegotiateRate = 0.0;
	private long BaseBalance = -1;
	private long InterestDays = -1;
	private Timestamp InterestEnd = null;
	private Timestamp InterestStart = null;
	private Timestamp InterestSettment = null;
	private long OperationType = -1;
	//银团贷款专用
	Vector vctDetail = null;
	private long ClientId = -1;
	private String OldDepositNo = "";
	private String NewDepositNo = "";
	private Timestamp EffectiveDate = null;
	private long FreezeTerm = -1;
	private Timestamp FreezeEndDate = null;
	private double FreezeAmount = 0.0;
	private long SubAccountOldStatus = -1;
	private long SubAccountNewStatus = -1;
	private String ExecuteGovernment = "";
	private String ApplyCompany = "";
	private String LawWritNo = "";
	private Timestamp InputDate = null;
	private Timestamp CheckDate = null;
	private Timestamp ModifyDate = null;
	private long FreezeType = -1;
	private double advanceRate = 0.0;//活期利率
	
	//add by feiye 2006.5.17
	private long IsCapitalAndInterestTransfer = -1;
	//add by wjliu --2007-5-14
	//定期
	private String accountNo = "";  //定期账号
	private String accountName;//户名
	
	//增加通知支取收息外部账户号
	private String interestextaccountno = "";
	
	//总账业务
	private double accountAmount = 0.0;
	
	private long payGL1 = -1;  //付方总账科目1
	private long receiveGL1 = -1;  //收方总账科目1
	private double amount1 = 0.0;  //金额1
	
	private long payGL2 = -1;  //付方总账科目2
	private long receiveGL2 = -1;  //收方总账科目2
	private double amount2 = 0.0;  //金额2
	
	private long payGL3 = -1;  //付方总账科目3
	private long receiveGL3 = -1;  //收方总账科目3
	private double amount3 = 0.0;  //金额3
	
	private long payGL4 = -1;  //付方总账科目4
	private long receiveGL4 = -1;  //收方总账科目4
	private double amount4 = 0.0;  //金额4

	private long CommissionBankID = -1;
	private long CommissionCashFlowID = -1;
	private long CommissionCurrentAccountID = -1;
	
	private long FeeTypeID = -1;  //交易费用类型
	
	private String instructionNo = "";  //标识非结算系统产生的流水号
	//modify by xwhe 2008-10-27
	private double PecisionInterestAmount = 0.0 ;//活期精确利息
    //modify by xwhe 2008-12-16
	private long nerceivedirection = -1; //收款方向
	private long npaydirection = -1;//付款方向
	
	/*
	 *  modified 20121113 zk BUG #16275 电子单据柜，总账业务打印，借方和贷方科目都没有取出来值
	 *  添加账户类型和对应科目
	 */
	private long nAccountTypeID = -1;	//账户类型;
	private String sSubject = null; 	//账户对应科目号;
	
	public long getNerceivedirection() {
		return nerceivedirection;
	}
	public void setNerceivedirection(long nerceivedirection) {
		this.nerceivedirection = nerceivedirection;
	}
	public long getNpaydirection() {
		return npaydirection;
	}
	public void setNpaydirection(long npaydirection) {
		this.npaydirection = npaydirection;
	}
	public double getSumBlanceAmount1() {
		return sumBlanceAmount1;
	}
	public void setSumBlanceAmount1(double sumBlanceAmount1) {
		this.sumBlanceAmount1 = sumBlanceAmount1;
	}
	public double getSumBlanceAmount2() {
		return sumBlanceAmount2;
	}
	public void setSumBlanceAmount2(double sumBlanceAmount2) {
		this.sumBlanceAmount2 = sumBlanceAmount2;
	}
	public double getSumBlanceAmount3() {
		return sumBlanceAmount3;
	}
	public void setSumBlanceAmount3(double sumBlanceAmount3) {
		this.sumBlanceAmount3 = sumBlanceAmount3;
	}
	public String getInstructionNo() 
	{
		return instructionNo;
	}
	public void setInstructionNo(String instructionNo) 
	{
		this.instructionNo = instructionNo;
	}
	public double getDepositBalance() 
	{
		return DepositBalance;
	}
	public void setDepositBalance(double depositBalance) 
	{
		DepositBalance = depositBalance;
	}
	public long getFeeTypeID() 
	{
		return FeeTypeID;
	}
	public void setFeeTypeID(long feeTypeID) 
	{
		FeeTypeID = feeTypeID;
	}
	public long getCommissionBankID() 
	{
		return CommissionBankID;
	}
	public void setCommissionBankID(long commissionBankID) 
	{
		CommissionBankID = commissionBankID;
	}
	public long getCommissionCashFlowID() 
	{
		return CommissionCashFlowID;
	}
	public void setCommissionCashFlowID(long commissionCashFlowID) 
	{
		CommissionCashFlowID = commissionCashFlowID;
	}
	public long getCommissionCurrentAccountID() 
	{
		return CommissionCurrentAccountID;
	}
	public void setCommissionCurrentAccountID(long commissionCurrentAccountID) 
	{
		CommissionCurrentAccountID = commissionCurrentAccountID;
	}
	public double getDrawAmount() 
	{
		return DrawAmount;
	}
	public void setDrawAmount(double drawAmount)
	{
		DrawAmount = drawAmount;
	}
	public Timestamp getNewStartDate() 
	{
		return NewStartDate;
	}
	public void setNewStartDate(Timestamp newStartDate) 
	{
		NewStartDate = newStartDate;
	}
	public Timestamp getNewEndDate() 
	{
		return NewEndDate;
	}
	public void setNewEndDate(Timestamp newEndDate) 
	{
		NewEndDate = newEndDate;
	}
	public double getAccountAmount() 
	{
		return accountAmount;
	}
	public void setAccountAmount(double accountAmount) 
	{
		this.accountAmount = accountAmount;
	}
	public double getAmount1() {
		return amount1;
	}
	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}
	public double getAmount2() {
		return amount2;
	}
	public void setAmount2(double amount2) {
		this.amount2 = amount2;
	}
	public double getAmount3() {
		return amount3;
	}
	public void setAmount3(double amount3) {
		this.amount3 = amount3;
	}
	public double getAmount4() {
		return amount4;
	}
	public void setAmount4(double amount4) {
		this.amount4 = amount4;
	}
	public long getPayGL1() {
		return payGL1;
	}
	public void setPayGL1(long payGL1) {
		this.payGL1 = payGL1;
	}
	public long getPayGL2() {
		return payGL2;
	}
	public void setPayGL2(long payGL2) {
		this.payGL2 = payGL2;
	}
	public long getPayGL3() {
		return payGL3;
	}
	public void setPayGL3(long payGL3) {
		this.payGL3 = payGL3;
	}
	public long getPayGL4() {
		return payGL4;
	}
	public void setPayGL4(long payGL4) {
		this.payGL4 = payGL4;
	}
	public long getReceiveGL1() {
		return receiveGL1;
	}
	public void setReceiveGL1(long receiveGL1) {
		this.receiveGL1 = receiveGL1;
	}
	public long getReceiveGL2() {
		return receiveGL2;
	}
	public void setReceiveGL2(long receiveGL2) {
		this.receiveGL2 = receiveGL2;
	}
	public long getReceiveGL3() {
		return receiveGL3;
	}
	public void setReceiveGL3(long receiveGL3) {
		this.receiveGL3 = receiveGL3;
	}
	public long getReceiveGL4() {
		return receiveGL4;
	}
	public void setReceiveGL4(long receiveGL4) {
		this.receiveGL4 = receiveGL4;
	}
	public String getInterestextaccountno() {
		return interestextaccountno;
	}
	public void setInterestextaccountno(String interestextaccountno) {
		this.interestextaccountno = interestextaccountno;
	}
	/**
	 * @return Returns the freezeType.
	 */
	public long getFreezeType() {
		return FreezeType;
	}
	/**
	 * @param freezeType The freezeType to set.
	 */
	public void setFreezeType(long freezeType) {
		FreezeType = freezeType;
	}
	/**
	 * @return Returns the modifyDate.
	 */
	public Timestamp getModifyDate() {
		return ModifyDate;
	}
	/**
	 * @param modifyDate The modifyDate to set.
	 */
	public void setModifyDate(Timestamp modifyDate) {
		ModifyDate = modifyDate;
	}
	/**
	 * @return Returns the checkDate.
	 */
	public Timestamp getCheckDate() {
		return CheckDate;
	}
	/**
	 * @param checkDate The checkDate to set.
	 */
	public void setCheckDate(Timestamp checkDate) {
		CheckDate = checkDate;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}
	/**
	 * @return Returns the lawWritNo.
	 */
	public String getLawWritNo() {
		return LawWritNo;
	}
	/**
	 * @param lawWritNo The lawWritNo to set.
	 */
	public void setLawWritNo(String lawWritNo) {
		LawWritNo = lawWritNo;
	}
	/**
	 * @return Returns the applyCompany.
	 */
	public String getApplyCompany() {
		return ApplyCompany;
	}
	/**
	 * @param applyCompany The applyCompany to set.
	 */
	public void setApplyCompany(String applyCompany) {
		ApplyCompany = applyCompany;
	}
	/**
	 * @return Returns the executeGovernment.
	 */
	public String getExecuteGovernment() {
		return ExecuteGovernment;
	}
	/**
	 * @param executeGovernment The executeGovernment to set.
	 */
	public void setExecuteGovernment(String executeGovernment) {
		ExecuteGovernment = executeGovernment;
	}
	/**
	 * @return Returns the subAccountNewStatus.
	 */
	public long getSubAccountNewStatus() {
		return SubAccountNewStatus;
	}
	/**
	 * @param subAccountNewStatus The subAccountNewStatus to set.
	 */
	public void setSubAccountNewStatus(long subAccountNewStatus) {
		SubAccountNewStatus = subAccountNewStatus;
	}
	/**
	 * @return Returns the subAccountOldStatus.
	 */
	public long getSubAccountOldStatus() {
		return SubAccountOldStatus;
	}
	/**
	 * @param subAccountOldStatus The subAccountOldStatus to set.
	 */
	public void setSubAccountOldStatus(long subAccountOldStatus) {
		SubAccountOldStatus = subAccountOldStatus;
	}
	/**
	 * @return Returns the freezeAmount.
	 */
	public double getFreezeAmount() {
		return FreezeAmount;
	}
	/**
	 * @param freezeAmount The freezeAmount to set.
	 */
	public void setFreezeAmount(double freezeAmount) {
		FreezeAmount = freezeAmount;
	}
	/**
	 * @return Returns the freezeEndDate.
	 */
	public Timestamp getFreezeEndDate() {
		return FreezeEndDate;
	}
	/**
	 * @param freezeEndDate The freezeEndDate to set.
	 */
	public void setFreezeEndDate(Timestamp freezeEndDate) {
		FreezeEndDate = freezeEndDate;
	}
	/**
	 * @return Returns the freezeTerm.
	 */
	public long getFreezeTerm() {
		return FreezeTerm;
	}
	/**
	 * @param freezeTerm The freezeTerm to set.
	 */
	public void setFreezeTerm(long freezeTerm) {
		FreezeTerm = freezeTerm;
	}
	/**
	 * @return Returns the effectiveDate.
	 */
	public Timestamp getEffectiveDate() {
		return EffectiveDate;
	}
	/**
	 * @param effectiveDate The effectiveDate to set.
	 */
	public void setEffectiveDate(Timestamp effectiveDate) {
		EffectiveDate = effectiveDate;
	}
	/**
	 * @return Returns the clientId.
	 */
	public long getClientId() {
		return ClientId;
	}
	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(long clientId) {
		ClientId = clientId;
	}
	/**
	 * @return Returns the newDepositNo.
	 */
	public String getNewDepositNo() {
		return NewDepositNo;
	}
	/**
	 * @param newDepositNo The newDepositNo to set.
	 */
	public void setNewDepositNo(String newDepositNo) {
		NewDepositNo = newDepositNo;
	}
	/**
	 * @return Returns the oldDepositNo.
	 */
	public String getOldDepositNo() {
		return OldDepositNo;
	}
	/**
	 * @param oldDepositNo The oldDepositNo to set.
	 */
	public void setOldDepositNo(String oldDepositNo) {
		OldDepositNo = oldDepositNo;
	}
	/**
	 * @return Returns the negotiateRate.
	 */
	public double getNegotiateRate() {
		return NegotiateRate;
	}
	/**
	 * @param negotiateRate The negotiateRate to set.
	 */
	public void setNegotiateRate(double negotiateRate) {
		NegotiateRate = negotiateRate;
	}
	/**
	 * @return Returns the autoExecute.
	 */
	public Timestamp getAutoExecute() {
		return AutoExecute;
	}
	/**
	 * @param autoExecute The autoExecute to set.
	 */
	public void setAutoExecute(Timestamp autoExecute) {
		AutoExecute = autoExecute;
	}
	/**
	 * @return Returns the baseBalance.
	 */
	public long getBaseBalance() {
		return BaseBalance;
	}
	/**
	 * @param baseBalance The baseBalance to set.
	 */
	public void setBaseBalance(long baseBalance) {
		BaseBalance = baseBalance;
	}
	/**
	 * @return Returns the batchNo.
	 */
	public long getBatchNo() {
		return BatchNo;
	}
	/**
	 * @param batchNo The batchNo to set.
	 */
	public void setBatchNo(long batchNo) {
		BatchNo = batchNo;
	}
	/**
	 * @return Returns the checkAbstract.
	 */
	public String getCheckAbstract() {
		return CheckAbstract;
	}
	/**
	 * @param checkAbstract The checkAbstract to set.
	 */
	public void setCheckAbstract(String checkAbstract) {
		CheckAbstract = checkAbstract;
	}
	/**
	 * @return Returns the faultReason.
	 */
	public String getFaultReason() {
		return FaultReason;
	}
	/**
	 * @param faultReason The faultReason to set.
	 */
	public void setFaultReason(String faultReason) {
		FaultReason = faultReason;
	}
	/**
	 * @return Returns the interestDays.
	 */
	public long getInterestDays() {
		return InterestDays;
	}
	/**
	 * @param interestDays The interestDays to set.
	 */
	public void setInterestDays(long interestDays) {
		InterestDays = interestDays;
	}
	/**
	 * @return Returns the interestEnd.
	 */
	public Timestamp getInterestEnd() {
		return InterestEnd;
	}
	/**
	 * @param interestEnd The interestEnd to set.
	 */
	public void setInterestEnd(Timestamp interestEnd) {
		InterestEnd = interestEnd;
	}
	/**
	 * @return Returns the interestFeeSettingDetailID.
	 */
	public long getInterestFeeSettingDetailID() {
		return InterestFeeSettingDetailID;
	}
	/**
	 * @param interestFeeSettingDetailID The interestFeeSettingDetailID to set.
	 */
	public void setInterestFeeSettingDetailID(long interestFeeSettingDetailID) {
		InterestFeeSettingDetailID = interestFeeSettingDetailID;
	}
	/**
	 * @return Returns the interestSettment.
	 */
	public Timestamp getInterestSettment() {
		return InterestSettment;
	}
	/**
	 * @param interestSettment The interestSettment to set.
	 */
	public void setInterestSettment(Timestamp interestSettment) {
		InterestSettment = interestSettment;
	}
	/**
	 * @return Returns the interestStart.
	 */
	public Timestamp getInterestStart() {
		return InterestStart;
	}
	/**
	 * @param interestStart The interestStart to set.
	 */
	public void setInterestStart(Timestamp interestStart) {
		InterestStart = interestStart;
	}
	/**
	 * @return Returns the isKeepAccount.
	 */
	public long getIsKeepAccount() {
		return IsKeepAccount;
	}
	/**
	 * @param isKeepAccount The isKeepAccount to set.
	 */
	public void setIsKeepAccount(long isKeepAccount) {
		IsKeepAccount = isKeepAccount;
	}
	/**
	 * @return Returns the isSave.
	 */
	public long getIsSave() {
		return IsSave;
	}
	/**
	 * @param isSave The isSave to set.
	 */
	public void setIsSave(long isSave) {
		IsSave = isSave;
	}
	/**
	 * @return Returns the isSuccess.
	 */
	public long getIsSuccess() {
		return IsSuccess;
	}
	/**
	 * @param isSuccess The isSuccess to set.
	 */
	public void setIsSuccess(long isSuccess) {
		IsSuccess = isSuccess;
	}
	/**
	 * @return Returns the negotiateBalance.
	 */
	public long getNegotiateBalance() {
		return NegotiateBalance;
	}
	/**
	 * @param negotiateBalance The negotiateBalance to set.
	 */
	public void setNegotiateBalance(long negotiateBalance) {
		NegotiateBalance = negotiateBalance;
	}
	/**
	 * @return Returns the negotiateInterest.
	 */
	public long getNegotiateInterest() {
		return NegotiateInterest;
	}
	/**
	 * @param negotiateInterest The negotiateInterest to set.
	 */
	public void setNegotiateInterest(long negotiateInterest) {
		NegotiateInterest = negotiateInterest;
	}
	/**
	 * @return Returns the negotiatePecisionInterest.
	 */
	public long getNegotiatePecisionInterest() {
		return NegotiatePecisionInterest;
	}
	/**
	 * @param negotiatePecisionInterest The negotiatePecisionInterest to set.
	 */
	public void setNegotiatePecisionInterest(long negotiatePecisionInterest) {
		NegotiatePecisionInterest = negotiatePecisionInterest;
	}
	/**
	 * @return Returns the operationType.
	 */
	public long getOperationType() {
		return OperationType;
	}
	/**
	 * @param operationType The operationType to set.
	 */
	public void setOperationType(long operationType) {
		OperationType = operationType;
	}
	/**
	 * @return Returns the pecisionInterest.
	 */
	public long getPecisionInterest() {
		return PecisionInterest;
	}
	/**
	 * @param pecisionInterest The pecisionInterest to set.
	 */
	public void setPecisionInterest(long pecisionInterest) {
		PecisionInterest = pecisionInterest;
	}
	/**
	 * @return Returns the interestType.
	 */
	public long getInterestType() {
		return InterestType;
	}
	/**
	 * @param interestType The interestType to set.
	 */
	public void setInterestType(long interestType) {
		InterestType = interestType;
	}
	/**
	 * @return Returns the accountID.
	 */
	public long getAccountID() {
		return AccountID;
	}
	/**
	 * @param accountID The accountID to set.
	 */
	public void setAccountID(long accountID) {
		AccountID = accountID;
	}
	/**
	 * @return Returns the transactionTypeID.
	 */
	public long getTransactionTypeID() {
		return TransactionTypeID;
	}
	/**
	 * @param transactionTypeID The transactionTypeID to set.
	 */
	public void setTransactionTypeID(long transactionTypeID) {
		TransactionTypeID = transactionTypeID;
	}
	private String DetailTitle = "";
	/**
	 * @return
	 */
	public String getAbstract()
	{
		return Abstract;
	}
	/**
	 * @return
	 */
	public double getAmount()
	{
		return Amount;
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
	public Timestamp getEndDate()
	{
		return EndDate;
	}
	/**
	 * @return
	 */
	public Timestamp getExecuteDate()
	{
		return ExecuteDate;
	}
	/**
	 * @return
	 */
	public String getFixedDepositNo()
	{
		return FixedDepositNo;
	}
	/**
	 * @return
	 */
	public Timestamp getInterestStartDate()
	{
		return InterestStartDate;
	}
	/**
	 * @return
	 */
	public double getLoanBalance()
	{
		return LoanBalance;
	}
	/**
	 * @return
	 */
	public String getManagerName()
	{
		return ManagerName;
	}
	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}
	/**
	 * @return
	 */
	public long getPayAccountID()
	{
		return PayAccountID;
	}
	/**
	 * @return
	 */
	public long getPayBankID()
	{
		return PayBankID;
	}
	/**
	 * @return
	 */
	public double getRate()
	{
		return Rate;
	}
	/**
	 * @return
	 */
	public long getReceiveAccountID()
	{
		return ReceiveAccountID;
	}
	/**
	 * @return
	 */
	public long getReceiveBankID()
	{
		return ReceiveBankID;
	}
	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return StartDate;
	}
	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}
	/**
	 * @param string
	 */
	public void setAbstract(String string)
	{
		Abstract = string;
	}
	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		Amount = d;
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
	public void setEndDate(Timestamp timestamp)
	{
		EndDate = timestamp;
	}
	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp)
	{
		ExecuteDate = timestamp;
	}
	/**
	 * @param string
	 */
	public void setFixedDepositNo(String string)
	{
		FixedDepositNo = string;
	}
	/**
	 * @param timestamp
	 */
	public void setInterestStartDate(Timestamp timestamp)
	{
		InterestStartDate = timestamp;
	}
	/**
	 * @param d
	 */
	public void setLoanBalance(double d)
	{
		LoanBalance = d;
	}
	/**
	 * @param string
	 */
	public void setManagerName(String string)
	{
		ManagerName = string;
	}
	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}
	/**
	 * @param l
	 */
	public void setPayAccountID(long l)
	{
		PayAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setPayBankID(long l)
	{
		PayBankID = l;
	}
	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		Rate = d;
	}
	/**
	 * @param l
	 */
	public void setReceiveAccountID(long l)
	{
		ReceiveAccountID = l;
	}
	/**
	 * @param l
	 */
	public void setReceiveBankID(long l)
	{
		ReceiveBankID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setStartDate(Timestamp timestamp)
	{
		StartDate = timestamp;
	}
	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}
	/**
	 * @return
	 */
	public long getPayClientID()
	{
		return PayClientID;
	}
	/**
	 * @return
	 */
	public long getReceiveClientID()
	{
		return ReceiveClientID;
	}
	/**
	 * @param l
	 */
	public void setPayClientID(long l)
	{
		PayClientID = l;
	}
	/**
	 * @param l
	 */
	public void setReceiveClientID(long l)
	{
		ReceiveClientID = l;
	}
	/**
	 * @return
	 */
	public long getAbstractID()
	{
		return AbstractID;
	}
	/**
	 * @param l
	 */
	public void setAbstractID(long l)
	{
		AbstractID = l;
	}
	/**
	 * @return
	 */
	public String getExtAccountNo()
	{
		return ExtAccountNo;
	}
	/**
	 * @return
	 */
	public String getExtClientName()
	{
		return ExtClientName;
	}
	/**
	 * @return
	 */
	public String getExtRemitInCity()
	{
		return ExtRemitInCity;
	}
	/**
	 * @return
	 */
	public String getExtRemitInProvince()
	{
		return ExtRemitInProvince;
	}
	/**
	 * @param string
	 */
	public void setExtAccountNo(String string)
	{
		ExtAccountNo = string;
	}
	/**
	 * @param string
	 */
	public void setExtClientName(String string)
	{
		ExtClientName = string;
	}
	/**
	 * @param string
	 */
	public void setExtRemitInCity(String string)
	{
		ExtRemitInCity = string;
	}
	/**
	 * @param string
	 */
	public void setExtRemitInProvince(String string)
	{
		ExtRemitInProvince = string;
	}
	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}
	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}
	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}
	/**
	 * @return
	 */
	public String getExtRemitInBank()
	{
		return ExtRemitInBank;
	}
	/**
	 * @param string
	 */
	public void setExtRemitInBank(String string)
	{
		ExtRemitInBank = string;
	}
	/**
	 * @return
	 */
	public double getBookInterestAmount()
	{
		return BookInterestAmount;
	}
	/**
	 * @return
	 */
	public double getDiscountAmount()
	{
		return DiscountAmount;
	}
	/**
	 * @return
	 */
	public double getDiscountBillAmount()
	{
		return DiscountBillAmount;
	}
	/**
	 * @return
	 */
	public double getDiscountInterestAmount()
	{
		return DiscountInterestAmount;
	}
	/**
	 * @return
	 */
	public long getFixedDepositTerm()
	{
		return FixedDepositTerm;
	}
	/**
	 * @return
	 */
	public double getFixedInterestAmount()
	{
		return FixedInterestAmount;
	}
	/**
	 * @return
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}
	/**
	 * @return
	 */
	public double getPayInterestAmount()
	{
		return PayInterestAmount;
	}
	/**
	 * @param d
	 */
	public void setBookInterestAmount(double d)
	{
		BookInterestAmount = d;
	}
	/**
	 * @param d
	 */
	public void setDiscountAmount(double d)
	{
		DiscountAmount = d;
	}
	/**
	 * @param d
	 */
	public void setDiscountBillAmount(double d)
	{
		DiscountBillAmount = d;
	}
	/**
	 * @param d
	 */
	public void setDiscountInterestAmount(double d)
	{
		DiscountInterestAmount = d;
	}
	/**
	 * @param l
	 */
	public void setFixedDepositTerm(long l)
	{
		FixedDepositTerm = l;
	}
	/**
	 * @param d
	 */
	public void setFixedInterestAmount(double d)
	{
		FixedInterestAmount = d;
	}
	/**
	 * @param l
	 */
	public void setLoanTypeID(long l)
	{
		LoanTypeID = l;
	}
	/**
	 * @param d
	 */
	public void setPayInterestAmount(double d)
	{
		PayInterestAmount = d;
	}
	/**
	 * Returns the adjustInterest.
	 * @return double
	 */
	public double getAdjustInterest()
	{
		return AdjustInterest;
	}
	/**
	 * Returns the adjustInterestReason.
	 * @return String
	 */
	public String getAdjustInterestReason()
	{
		return AdjustInterestReason;
	}
	/**
	 * Returns the aheadReceiveInterest.
	 * @return double
	 */
	public double getAheadReceiveInterest()
	{
		return AheadReceiveInterest;
	}
	/**
	 * Returns the billBankID.
	 * @return long
	 */
	public long getBillBankID()
	{
		return BillBankID;
	}
	/**
	 * Returns the billNo.
	 * @return String
	 */
	public String getBillNo()
	{
		return BillNo;
	}
	/**
	 * Returns the billTypeID.
	 * @return long
	 */
	public long getBillTypeID()
	{
		return BillTypeID;
	}
	/**
	 * Returns the commission.
	 * @return double
	 */
	public double getCommission()
	{
		return Commission;
	}
	/**
	 * Returns the commissionBankID.
	 * @return long
	 */
	public long getPayCommissionBankID()
	{
		return PayCommissionBankID;
	}
	/**
	 * Returns the compoundInterest.
	 * @return double
	 */
	public double getCompoundInterest()
	{
		return CompoundInterest;
	}
	/**
	 * Returns the interest.
	 * @return double
	 */
	public double getInterest()
	{
		return Interest;
	}
	/**
	 * Returns the interestbankID.
	 * @return long
	 */
	public long getPayInterestBankID()
	{
		return PayInterestBankID;
	}
	/**
	 * Returns the interestIncome.
	 * @return double
	 */
	public double getInterestIncome()
	{
		return InterestIncome;
	}
	/**
	 * Returns the interestReceivable.
	 * @return double
	 */
	public double getInterestReceivable()
	{
		return InterestReceivable;
	}
	/**
	 * Returns the interestTax.
	 * @return double
	 */
	public double getInterestTax()
	{
		return InterestTax;
	}
	/**
	 * Returns the interestTaxRate.
	 * @return double
	 */
	public double getInterestTaxRate()
	{
		return InterestTaxRate;
	}
	/**
	 * Returns the interestTaxRateVauleDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestTaxRateVauleDate()
	{
		return InterestTaxRateVauleDate;
	}
	/**
	 * Returns the overDueInterest.
	 * @return double
	 */
	public double getOverDueInterest()
	{
		return OverDueInterest;
	}
	/**
	 * Returns the payCommisionAccountID.
	 * @return long
	 */
	public long getPayCommissionAccountID()
	{
		return PayCommissionAccountID;
	}
	/**
	 * Returns the payInterestAccountID.
	 * @return long
	 */
	public long getPayInterestAccountID()
	{
		return PayInterestAccountID;
	}
	/**
	 * Returns the paySuretyFeeAccountID.
	 * @return long
	 */
	public long getPaySuretyFeeAccountID()
	{
		return PaySuretyFeeAccountID;
	}
	/**
	 * Returns the payTypeID.
	 * @return long
	 */
	public long getPayTypeID()
	{
		return PayTypeID;
	}
	/**
	 * Returns the preFormID.
	 * @return long
	 */
	public long getPreFormID()
	{
		return PreFormID;
	}
	/**
	 * Returns the realCommission.
	 * @return double
	 */
	public double getRealCommission()
	{
		return RealCommission;
	}
	/**
	 * Returns the realCompoundInterest.
	 * @return double
	 */
	public double getRealCompoundInterest()
	{
		return RealCompoundInterest;
	}
	/**
	 * Returns the realInterest.
	 * @return double
	 */
	public double getRealInterest()
	{
		return RealInterest;
	}
	/**
	 * Returns the realInterestIncome.
	 * @return double
	 */
	public double getRealInterestIncome()
	{
		return RealInterestIncome;
	}
	/**
	 * Returns the realInterestReceivable.
	 * @return double
	 */
	public double getRealInterestReceivable()
	{
		return RealInterestReceivable;
	}
	/**
	 * Returns the realInterestTax.
	 * @return double
	 */
	public double getRealInterestTax()
	{
		return RealInterestTax;
	}
	/**
	 * Returns the realOverDueInterest.
	 * @return double
	 */
	public double getRealOverDueInterest()
	{
		return RealOverDueInterest;
	}
	/**
	 * Returns the realSuretyFee.
	 * @return double
	 */
	public double getRealSuretyFee()
	{
		return RealSuretyFee;
	}
	/**
	 * Returns the receiveInterestAccountID.
	 * @return long
	 */
	public long getReceiveInterestAccountID()
	{
		return ReceiveInterestAccountID;
	}
	/**
	 * Returns the receiveSuretyFeeAccountID.
	 * @return long
	 */
	public long getReceiveSuretyFeeAccountID()
	{
		return ReceiveSuretyFeeAccountID;
	}
	/**
	 * Returns the suertyFeeBankID.
	 * @return long
	 */
	public long getPaySuertyFeeBankID()
	{
		return PaySuertyFeeBankID;
	}
	/**
	 * Returns the suretyFee.
	 * @return double
	 */
	public double getSuretyFee()
	{
		return SuretyFee;
	}
	/**
	 * Sets the adjustInterest.
	 * @param adjustInterest The adjustInterest to set
	 */
	public void setAdjustInterest(double adjustInterest)
	{
		AdjustInterest = adjustInterest;
	}
	/**
	 * Sets the adjustInterestReason.
	 * @param adjustInterestReason The adjustInterestReason to set
	 */
	public void setAdjustInterestReason(String adjustInterestReason)
	{
		AdjustInterestReason = adjustInterestReason;
	}
	/**
	 * Sets the aheadReceiveInterest.
	 * @param aheadReceiveInterest The aheadReceiveInterest to set
	 */
	public void setAheadReceiveInterest(double aheadReceiveInterest)
	{
		AheadReceiveInterest = aheadReceiveInterest;
	}
	/**
	 * Sets the billBankID.
	 * @param billBankID The billBankID to set
	 */
	public void setBillBankID(long billBankID)
	{
		BillBankID = billBankID;
	}
	/**
	 * Sets the billNo.
	 * @param billNo The billNo to set
	 */
	public void setBillNo(String billNo)
	{
		BillNo = billNo;
	}
	/**
	 * Sets the billTypeID.
	 * @param billTypeID The billTypeID to set
	 */
	public void setBillTypeID(long billTypeID)
	{
		BillTypeID = billTypeID;
	}
	/**
	 * Sets the commission.
	 * @param commission The commission to set
	 */
	public void setCommission(double commission)
	{
		Commission = commission;
	}
	/**
	 * Sets the commissionBankID.
	 * @param commissionBankID The commissionBankID to set
	 */
	public void setPayCommissionBankID(long commissionBankID)
	{
		PayCommissionBankID = commissionBankID;
	}
	/**
	 * Sets the compoundInterest.
	 * @param compoundInterest The compoundInterest to set
	 */
	public void setCompoundInterest(double compoundInterest)
	{
		CompoundInterest = compoundInterest;
	}
	/**
	 * Sets the freeFormID.
	 * @param freeFormID The freeFormID to set
	 */
	public void setFreeFormID(long freeFormID)
	{
		FreeFormID = freeFormID;
	}
	/**
	 * Sets the interest.
	 * @param interest The interest to set
	 */
	public void setInterest(double interest)
	{
		Interest = interest;
	}
	/**
	 * Sets the interestbankID.
	 * @param interestbankID The interestbankID to set
	 */
	public void setPayInterestBankID(long interestbankID)
	{
		PayInterestBankID = interestbankID;
	}
	/**
	 * Sets the interestIncome.
	 * @param interestIncome The interestIncome to set
	 */
	public void setInterestIncome(double interestIncome)
	{
		InterestIncome = interestIncome;
	}
	/**
	 * Sets the interestReceivable.
	 * @param interestReceivable The interestReceivable to set
	 */
	public void setInterestReceivable(double interestReceivable)
	{
		InterestReceivable = interestReceivable;
	}
	/**
	 * Sets the interestTax.
	 * @param interestTax The interestTax to set
	 */
	public void setInterestTax(double interestTax)
	{
		InterestTax = interestTax;
	}
	/**
	 * Sets the interestTaxRate.
	 * @param interestTaxRate The interestTaxRate to set
	 */
	public void setInterestTaxRate(double interestTaxRate)
	{
		InterestTaxRate = interestTaxRate;
	}
	/**
	 * Sets the interestTaxRateVauleDate.
	 * @param interestTaxRateVauleDate The interestTaxRateVauleDate to set
	 */
	public void setInterestTaxRateVauleDate(Timestamp interestTaxRateVauleDate)
	{
		InterestTaxRateVauleDate = interestTaxRateVauleDate;
	}
	/**
	 * Sets the overDueInterest.
	 * @param overDueInterest The overDueInterest to set
	 */
	public void setOverDueInterest(double overDueInterest)
	{
		OverDueInterest = overDueInterest;
	}
	/**
	 * Sets the payCommisionAccountID.
	 * @param payCommisionAccountID The payCommisionAccountID to set
	 */
	public void setPayCommissionAccountID(long payCommissionAccountID)
	{
		PayCommissionAccountID = payCommissionAccountID;
	}
	/**
	 * Sets the payInterestAccountID.
	 * @param payInterestAccountID The payInterestAccountID to set
	 */
	public void setPayInterestAccountID(long payInterestAccountID)
	{
		PayInterestAccountID = payInterestAccountID;
	}
	/**
	 * Sets the paySuretyFeeAccountID.
	 * @param paySuretyFeeAccountID The paySuretyFeeAccountID to set
	 */
	public void setPaySuretyFeeAccountID(long paySuretyFeeAccountID)
	{
		PaySuretyFeeAccountID = paySuretyFeeAccountID;
	}
	/**
	 * Sets the payTypeID.
	 * @param payTypeID The payTypeID to set
	 */
	public void setPayTypeID(long payTypeID)
	{
		PayTypeID = payTypeID;
	}
	/**
	 * Sets the preFormID.
	 * @param preFormID The preFormID to set
	 */
	public void setPreFormID(long preFormID)
	{
		PreFormID = preFormID;
	}
	/**
	 * Sets the realCommission.
	 * @param realCommission The realCommission to set
	 */
	public void setRealCommission(double realCommission)
	{
		RealCommission = realCommission;
	}
	/**
	 * Sets the realCompoundInterest.
	 * @param realCompoundInterest The realCompoundInterest to set
	 */
	public void setRealCompoundInterest(double realCompoundInterest)
	{
		RealCompoundInterest = realCompoundInterest;
	}
	/**
	 * Sets the realInterest.
	 * @param realInterest The realInterest to set
	 */
	public void setRealInterest(double realInterest)
	{
		RealInterest = realInterest;
	}
	/**
	 * Sets the realInterestIncome.
	 * @param realInterestIncome The realInterestIncome to set
	 */
	public void setRealInterestIncome(double realInterestIncome)
	{
		RealInterestIncome = realInterestIncome;
	}
	/**
	 * Sets the realInterestReceivable.
	 * @param realInterestReceivable The realInterestReceivable to set
	 */
	public void setRealInterestReceivable(double realInterestReceivable)
	{
		RealInterestReceivable = realInterestReceivable;
	}
	/**
	 * Sets the realInterestTax.
	 * @param realInterestTax The realInterestTax to set
	 */
	public void setRealInterestTax(double realInterestTax)
	{
		RealInterestTax = realInterestTax;
	}
	/**
	 * Sets the realOverDueInterest.
	 * @param realOverDueInterest The realOverDueInterest to set
	 */
	public void setRealOverDueInterest(double realOverDueInterest)
	{
		RealOverDueInterest = realOverDueInterest;
	}
	/**
	 * Sets the realSuretyFee.
	 * @param realSuretyFee The realSuretyFee to set
	 */
	public void setRealSuretyFee(double realSuretyFee)
	{
		RealSuretyFee = realSuretyFee;
	}
	/**
	 * Sets the receiveInterestAccountID.
	 * @param receiveInterestAccountID The receiveInterestAccountID to set
	 */
	public void setReceiveInterestAccountID(long receiveInterestAccountID)
	{
		ReceiveInterestAccountID = receiveInterestAccountID;
	}
	/**
	 * Sets the receiveSuretyFeeAccountID.
	 * @param receiveSuretyFeeAccountID The receiveSuretyFeeAccountID to set
	 */
	public void setReceiveSuretyFeeAccountID(long receiveSuretyFeeAccountID)
	{
		ReceiveSuretyFeeAccountID = receiveSuretyFeeAccountID;
	}
	/**
	 * Sets the suertyFeeBankID.
	 * @param suertyFeeBankID The suertyFeeBankID to set
	 */
	public void setPaySuertyFeeBankID(long suertyFeeBankID)
	{
		PaySuertyFeeBankID = suertyFeeBankID;
	}
	/**
	 * Sets the suretyFee.
	 * @param suretyFee The suretyFee to set
	 */
	public void setSuretyFee(double suretyFee)
	{
		SuretyFee = suretyFee;
	}
	/**
	 * Returns the interestClearDate.
	 * @return Timestamp
	 */
	public Timestamp getInterestClearDate()
	{
		return InterestClearDate;
	}
	/**
	 * Returns the latestInterestClearDate.
	 * @return Timestamp
	 */
	public Timestamp getLatestInterestClearDate()
	{
		return LatestInterestClearDate;
	}
	/**
	 * Sets the interestClearDate.
	 * @param interestClearDate The interestClearDate to set
	 */
	public void setInterestClearDate(Timestamp interestClearDate)
	{
		InterestClearDate = interestClearDate;
	}
	/**
	 * Sets the latestInterestClearDate.
	 * @param latestInterestClearDate The latestInterestClearDate to set
	 */
	public void setLatestInterestClearDate(Timestamp latestInterestClearDate)
	{
		LatestInterestClearDate = latestInterestClearDate;
	}
	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return ContractID;
	}
	/**
	 * Returns the extBankNo.
	 * @return String
	 */
	public String getExtBankNo()
	{
		return ExtBankNo;
	}
	/**
	 * Returns the extendFormID.
	 * @return long
	 */
	public long getExtendFormID()
	{
		return ExtendFormID;
	}
	/**
	 * Returns the freeFormID.
	 * @return long
	 */
	public long getFreeFormID()
	{
		return FreeFormID;
	}
	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		ContractID = contractID;
	}
	/**
	 * Sets the extBankNo.
	 * @param extBankNo The extBankNo to set
	 */
	public void setExtBankNo(String extBankNo)
	{
		ExtBankNo = extBankNo;
	}
	/**
	 * Sets the extendFormID.
	 * @param extendFormID The extendFormID to set
	 */
	public void setExtendFormID(long extendFormID)
	{
		ExtendFormID = extendFormID;
	}
	/**
	 * Returns the loanNoteID.
	 * @return long
	 */
	public long getLoanNoteID()
	{
		return LoanNoteID;
	}
	/**
	 * Sets the loanNoteID.
	 * @param loanNoteID The loanNoteID to set
	 */
	public void setLoanNoteID(long loanNoteID)
	{
		LoanNoteID = loanNoteID;
	}
	/**
	 * Returns the sumReceiveAmount.
	 * @return double
	 */
	public double getSumReceiveAmount()
	{
		return SumReceiveAmount;
	}
	/**
	 * Sets the sumReceiveAmount.
	 * @param sumReceiveAmount The sumReceiveAmount to set
	 */
	public void setSumReceiveAmount(double sumReceiveAmount)
	{
		SumReceiveAmount = sumReceiveAmount;
	}
	/**
	 * Returns the discountNoteID.
	 * @return long
	 */
	public long getDiscountNoteID()
	{
		return DiscountNoteID;
	}
	/**
	 * Sets the discountNoteID.
	 * @param discountNoteID The discountNoteID to set
	 */
	public void setDiscountNoteID(long discountNoteID)
	{
		DiscountNoteID = discountNoteID;
	}
	/**
	 * Returns the payOrReceive.
	 * @return boolean
	 */
	public boolean isPayOrReceive()
	{
		return PayOrReceive;
	}
	/**
	 * Sets the payOrReceive.
	 * @param payOrReceive The payOrReceive to set
	 */
	public void setPayOrReceive(boolean payOrReceive)
	{
		PayOrReceive = payOrReceive;
	}
	/**
	 * Returns the discountBillID.
	 * @return long
	 */
	public long getDiscountBillID()
	{
		return DiscountBillID;
	}
	/**
	 * Sets the discountBillID.
	 * @param discountBillID The discountBillID to set
	 */
	public void setDiscountBillID(long discountBillID)
	{
		DiscountBillID = discountBillID;
	}
	/**
	 * Returns the transTypeID.
	 * @return long
	 */
	public long getTransTypeID()
	{
		return TransTypeID;
	}
	/**
	 * Sets the transTypeID.
	 * @param transTypeID The transTypeID to set
	 */
	public void setTransTypeID(long transTypeID)
	{
		TransTypeID = transTypeID;
	}
	/**
	 * Returns the noticeDay.
	 * @return long
	 */
	public long getNoticeDay()
	{
		return NoticeDay;
	}
	/**
	 * Sets the noticeDay.
	 * @param noticeDay The noticeDay to set
	 */
	public void setNoticeDay(long noticeDay)
	{
		NoticeDay = noticeDay;
	}
	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}
	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID)
	{
		SubAccountID = subAccountID;
	}
	/**
	 * Returns the currentBalance.
	 * @return double
	 */
	public double getCurrentBalance()
	{
		return CurrentBalance;
	}
	/**
	 * Sets the currentBalance.
	 * @param currentBalance The currentBalance to set
	 */
	public void setCurrentBalance(double currentBalance)
	{
		CurrentBalance = currentBalance;
	}
	/**
	 * Returns the payGL.
	 * @return long
	 */
	public long getPayGL()
	{
		return payGL;
	}
	/**
	 * Returns the receiveGL.
	 * @return long
	 */
	public long getReceiveGL()
	{
		return receiveGL;
	}
	/**
	 * Sets the payGL.
	 * @param payGL The payGL to set
	 */
	public void setPayGL(long payGL)
	{
		this.payGL = payGL;
	}
	/**
	 * Sets the receiveGL.
	 * @param receiveGL The receiveGL to set
	 */
	public void setReceiveGL(long receiveGL)
	{
		this.receiveGL = receiveGL;
	}
	/**
	 * Returns the consignAccountID.
	 * @return long
	 */
	public long getConsignAccountID()
	{
		return ConsignAccountID;
	}
	/**
	 * Sets the consignAccountID.
	 * @param consignAccountID The consignAccountID to set
	 */
	public void setConsignAccountID(long consignAccountID)
	{
		ConsignAccountID = consignAccountID;
	}
	/**
	 * Returns the consignDepositAccountID.
	 * @return long
	 */
	public long getConsignDepositAccountID()
	{
		return ConsignDepositAccountID;
	}
	/**
	 * Sets the consignDepositAccountID.
	 * @param consignDepositAccountID The consignDepositAccountID to set
	 */
	public void setConsignDepositAccountID(long consignDepositAccountID)
	{
		ConsignDepositAccountID = consignDepositAccountID;
	}
	/**
	 * Returns the loanAccountID.
	 * @return long
	 */
	public long getLoanAccountID()
	{
		return LoanAccountID;
	}
	/**
	 * Sets the loanAccountID.
	 * @param loanAccountID The loanAccountID to set
	 */
	public void setLoanAccountID(long loanAccountID)
	{
		LoanAccountID = loanAccountID;
	}
	/**
	 * Returns the currentInterest.
	 * @return double
	 */
	public double getCurrentInterest()
	{
		return CurrentInterest;
	}
	/**
	 * Returns the otherInterest.
	 * @return double
	 */
	public double getOtherInterest()
	{
		return OtherInterest;
	}
	/**
	 * Returns the payableInterest.
	 * @return double
	 */
	public double       getPayableInterest()
	{
		return PayableInterest;
	}
	/**
	 * Sets the currentInterest.
	 * @param currentInterest The currentInterest to set
	 */
	public void setCurrentInterest(double currentInterest)
	{
		CurrentInterest = currentInterest;
	}
	/**
	 * Sets the otherInterest.
	 * @param otherInterest The otherInterest to set
	 */
	public void setOtherInterest(double otherInterest)
	{
		OtherInterest = otherInterest;
	}
	/**
	 * Sets the payableInterest.
	 * @param payableInterest The payableInterest to set
	 */
	public void setPayableInterest(double payableInterest)
	{
		PayableInterest = payableInterest;
	}
	/**
	 * Returns the payExtAccountNo.
	 * @return String
	 */
	public String getPayExtAccountNo()
	{
		return PayExtAccountNo;
	}
	/**
	 * Returns the payExtClientName.
	 * @return String
	 */
	public String getPayExtClientName()
	{
		return PayExtClientName;
	}
	/**
	 * Returns the payExtRemitInBank.
	 * @return String
	 */
	public String getPayExtRemitInBank()
	{
		return PayExtRemitInBank;
	}
	/**
	 * Returns the payExtRemitInCity.
	 * @return String
	 */
	public String getPayExtRemitInCity()
	{
		return PayExtRemitInCity;
	}
	/**
	 * Returns the payExtRemitInProvince.
	 * @return String
	 */
	public String getPayExtRemitInProvince()
	{
		return PayExtRemitInProvince;
	}
	/**
	 * Returns the receiveExtAccountNo.
	 * @return String
	 */
	public String getReceiveExtAccountNo()
	{
		return ReceiveExtAccountNo;
	}
	/**
	 * Returns the receiveExtClientName.
	 * @return String
	 */
	public String getReceiveExtClientName()
	{
		return ReceiveExtClientName;
	}
	/**
	 * Returns the receiveExtRemitInBank.
	 * @return String
	 */
	public String getReceiveExtRemitInBank()
	{
		return ReceiveExtRemitInBank;
	}
	/**
	 * Returns the receiveExtRemitInCity.
	 * @return String
	 */
	public String getReceiveExtRemitInCity()
	{
		return ReceiveExtRemitInCity;
	}
	/**
	 * Returns the receiveExtRemitInProvince.
	 * @return String
	 */
	public String getReceiveExtRemitInProvince()
	{
		return ReceiveExtRemitInProvince;
	}
	/**
	 * Sets the payExtAccountNo.
	 * @param payExtAccountNo The payExtAccountNo to set
	 */
	public void setPayExtAccountNo(String payExtAccountNo)
	{
		PayExtAccountNo = payExtAccountNo;
	}
	/**
	 * Sets the payExtClientName.
	 * @param payExtClientName The payExtClientName to set
	 */
	public void setPayExtClientName(String payExtClientName)
	{
		PayExtClientName = payExtClientName;
	}
	/**
	 * Sets the payExtRemitInBank.
	 * @param payExtRemitInBank The payExtRemitInBank to set
	 */
	public void setPayExtRemitInBank(String payExtRemitInBank)
	{
		PayExtRemitInBank = payExtRemitInBank;
	}
	/**
	 * Sets the payExtRemitInCity.
	 * @param payExtRemitInCity The payExtRemitInCity to set
	 */
	public void setPayExtRemitInCity(String payExtRemitInCity)
	{
		PayExtRemitInCity = payExtRemitInCity;
	}
	/**
	 * Sets the payExtRemitInProvince.
	 * @param payExtRemitInProvince The payExtRemitInProvince to set
	 */
	public void setPayExtRemitInProvince(String payExtRemitInProvince)
	{
		PayExtRemitInProvince = payExtRemitInProvince;
	}
	/**
	 * Sets the receiveExtAccountNo.
	 * @param receiveExtAccountNo The receiveExtAccountNo to set
	 */
	public void setReceiveExtAccountNo(String receiveExtAccountNo)
	{
		ReceiveExtAccountNo = receiveExtAccountNo;
	}
	/**
	 * Sets the receiveExtClientName.
	 * @param receiveExtClientName The receiveExtClientName to set
	 */
	public void setReceiveExtClientName(String receiveExtClientName)
	{
		ReceiveExtClientName = receiveExtClientName;
	}
	/**
	 * Sets the receiveExtRemitInBank.
	 * @param receiveExtRemitInBank The receiveExtRemitInBank to set
	 */
	public void setReceiveExtRemitInBank(String receiveExtRemitInBank)
	{
		ReceiveExtRemitInBank = receiveExtRemitInBank;
	}
	/**
	 * Sets the receiveExtRemitInCity.
	 * @param receiveExtRemitInCity The receiveExtRemitInCity to set
	 */
	public void setReceiveExtRemitInCity(String receiveExtRemitInCity)
	{
		ReceiveExtRemitInCity = receiveExtRemitInCity;
	}
	/**
	 * Sets the receiveExtRemitInProvince.
	 * @param receiveExtRemitInProvince The receiveExtRemitInProvince to set
	 */
	public void setReceiveExtRemitInProvince(String receiveExtRemitInProvince)
	{
		ReceiveExtRemitInProvince = receiveExtRemitInProvince;
	}
	/**
	 * Returns the accordInterestAmount.
	 * @return double
	 */
	public double getAccordInterestAmount()
	{
		return AccordInterestAmount;
	}
	/**
	 * Sets the accordInterestAmount.
	 * @param accordInterestAmount The accordInterestAmount to set
	 */
	public void setAccordInterestAmount(double accordInterestAmount)
	{
		AccordInterestAmount = accordInterestAmount;
	}
	/**
	 * Returns the accordInterestRate.
	 * @return double
	 */
	public double getAccordInterestRate()
	{
		return AccordInterestRate;
	}
	/**
	 * Sets the accordInterestRate.
	 * @param accordInterestRate The accordInterestRate to set
	 */
	public void setAccordInterestRate(double accordInterestRate)
	{
		AccordInterestRate = accordInterestRate;
	}
	/**
	 * Returns the accordInterest.
	 * @return double
	 */
	public double getAccordInterest()
	{
		return AccordInterest;
	}
	/**
	 * Sets the accordInterest.
	 * @param accordInterest The accordInterest to set
	 */
	public void setAccordInterest(double accordInterest)
	{
		AccordInterest = accordInterest;
	}
	/**
	 * Returns the adjustRateDate.
	 * @return Timestamp
	 */
	public Timestamp getAdjustRateDate()
	{
		return AdjustRateDate;
	}
	/**
	 * Returns the clearInterestDate.
	 * @return Timestamp
	 */
	public Timestamp getClearInterestDate()
	{
		return clearInterestDate;
	}
	/**
	 * Returns the commissionRate.
	 * @return double
	 */
	public double getCommissionRate()
	{
		return CommissionRate;
	}
	/**
	 * Returns the contractRate.
	 * @return double
	 */
	public double getContractRate()
	{
		return ContractRate;
	}
	/**
	 * Returns the executeRate.
	 * @return double
	 */
	public double getExecuteRate()
	{
		return ExecuteRate;
	}
	/**
	 * Returns the executeRateNew.
	 * @return double
	 */
	public double getExecuteRateNew()
	{
		return ExecuteRateNew;
	}
	/**
	 * Returns the formNo.
	 * @return String
	 */
	public String getFormNo()
	{
		return FormNo;
	}
	/**
	 * Returns the formYear.
	 * @return String
	 */
	public String getFormYear()
	{
		return FormYear;
	}
	/**
	 * Returns the loanTerm.
	 * @return long
	 */
	public long getLoanTerm()
	{
		return LoanTerm;
	}
	/**
	 * Sets the adjustRateDate.
	 * @param adjustRateDate The adjustRateDate to set
	 */
	public void setAdjustRateDate(Timestamp adjustRateDate)
	{
		AdjustRateDate = adjustRateDate;
	}
	/**
	 * Sets the clearInterestDate.
	 * @param clearInterestDate The clearInterestDate to set
	 */
	public void setClearInterestDate(Timestamp clearInterestDate)
	{
		this.clearInterestDate = clearInterestDate;
	}
	/**
	 * Sets the commissionRate.
	 * @param commissionRate The commissionRate to set
	 */
	public void setCommissionRate(double commissionRate)
	{
		CommissionRate = commissionRate;
	}
	/**
	 * Sets the contractRate.
	 * @param contractRate The contractRate to set
	 */
	public void setContractRate(double contractRate)
	{
		ContractRate = contractRate;
	}
	/**
	 * Sets the executeRate.
	 * @param executeRate The executeRate to set
	 */
	public void setExecuteRate(double executeRate)
	{
		ExecuteRate = executeRate;
	}
	/**
	 * Sets the executeRateNew.
	 * @param executeRateNew The executeRateNew to set
	 */
	public void setExecuteRateNew(double executeRateNew)
	{
		ExecuteRateNew = executeRateNew;
	}
	/**
	 * Sets the formNo.
	 * @param formNo The formNo to set
	 */
	public void setFormNo(String formNo)
	{
		FormNo = formNo;
	}
	/**
	 * Sets the formYear.
	 * @param formYear The formYear to set
	 */
	public void setFormYear(String formYear)
	{
		FormYear = formYear;
	}
	/**
	 * Sets the loanTerm.
	 * @param loanTerm The loanTerm to set
	 */
	public void setLoanTerm(long loanTerm)
	{
		LoanTerm = loanTerm;
	}
	/**
	 * Returns the borrowClientName.
	 * @return String
	 */
	public String getBorrowClientName()
	{
		return BorrowClientName;
	}
	/**
	 * Sets the borrowClientName.
	 * @param borrowClientName The borrowClientName to set
	 */
	public void setBorrowClientName(String borrowClientName)
	{
		BorrowClientName = borrowClientName;
	}
	/**
	 * Returns the specialAccountID.
	 * @return long
	 */
	public long getSpecialAccountID()
	{
		return SpecialAccountID;
	}
	/**
	 * Sets the specialAccountID.
	 * @param specialAccountID The specialAccountID to set
	 */
	public void setSpecialAccountID(long specialAccountID)
	{
		SpecialAccountID = specialAccountID;
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
	 * Sets the assureName.
	 * @param assureName The assureName to set
	 */
	public void setAssureName(String assureName)
	{
		AssureName = assureName;
	}
	/**
	 * Returns the assureContractID.
	 * @return long
	 */
	public long getAssureContractID()
	{
		return AssureContractID;
	}
	/**
	 * Sets the assureContractID.
	 * @param assureContractID The assureContractID to set
	 */
	public void setAssureContractID(long assureContractID)
	{
		AssureContractID = assureContractID;
	}
	/**
	 * Returns the formNum.
	 * @return long
	 */
	public long getFormNum()
	{
		return FormNum;
	}
	/**
	 * Sets the formNum.
	 * @param formNum The formNum to set
	 */
	public void setFormNum(long formNum)
	{
		FormNum = formNum;
	}
	/**
	 * Returns the compoundAmount.
	 * @return double
	 */
	public double getCompoundAmount()
	{
		return CompoundAmount;
	}
	/**
	 * Returns the compoundInterestStart.
	 * @return Timestamp
	 */
	public Timestamp getCompoundInterestStart()
	{
		return CompoundInterestStart;
	}
	/**
	 * Returns the compoundRate.
	 * @return double
	 */
	public double getCompoundRate()
	{
		return CompoundRate;
	}
	/**
	 * Returns the overDueAmount.
	 * @return double
	 */
	public double getOverDueAmount()
	{
		return OverDueAmount;
	}
	/**
	 * Returns the overDueRate.
	 * @return double
	 */
	public double getOverDueRate()
	{
		return OverDueRate;
	}
	/**
	 * Returns the overDueStart.
	 * @return Timestamp
	 */
	public Timestamp getOverDueStart()
	{
		return OverDueStart;
	}
	/**
	 * Sets the compoundAmount.
	 * @param compoundAmount The compoundAmount to set
	 */
	public void setCompoundAmount(double compoundAmount)
	{
		CompoundAmount = compoundAmount;
	}
	/**
	 * Sets the compoundInterestStart.
	 * @param compoundInterestStart The compoundInterestStart to set
	 */
	public void setCompoundInterestStart(Timestamp compoundInterestStart)
	{
		CompoundInterestStart = compoundInterestStart;
	}
	/**
	 * Sets the compoundRate.
	 * @param compoundRate The compoundRate to set
	 */
	public void setCompoundRate(double compoundRate)
	{
		CompoundRate = compoundRate;
	}
	/**
	 * Sets the overDueAmount.
	 * @param overDueAmount The overDueAmount to set
	 */
	public void setOverDueAmount(double overDueAmount)
	{
		OverDueAmount = overDueAmount;
	}
	/**
	 * Sets the overDueRate.
	 * @param overDueRate The overDueRate to set
	 */
	public void setOverDueRate(double overDueRate)
	{
		OverDueRate = overDueRate;
	}
	/**
	 * Sets the overDueStart.
	 * @param overDueStart The overDueStart to set
	 */
	public void setOverDueStart(Timestamp overDueStart)
	{
		OverDueStart = overDueStart;
	}
	/**
	 * Returns the accountTypeID.
	 * @return long
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}
	/**
	 * Sets the accountTypeID.
	 * @param accountTypeID The accountTypeID to set
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}
	/**
	 * Returns the consignClientID.
	 * @return long
	 */
	public long getConsignClientID()
	{
		return ConsignClientID;
	}
	/**
	 * Sets the consignClientID.
	 * @param consignClientID The consignClientID to set
	 */
	public void setConsignClientID(long consignClientID)
	{
		ConsignClientID = consignClientID;
	}
	/**
	 * Returns the borrowClientID.
	 * @return long
	 */
	public long getBorrowClientID()
	{
		return BorrowClientID;
	}
	/**
	 * Sets the borrowClientID.
	 * @param borrowClientID The borrowClientID to set
	 */
	public void setBorrowClientID(long borrowClientID)
	{
		BorrowClientID = borrowClientID;
	}
	/**
	 * Returns the compoundInterestEnd.
	 * @return Timestamp
	 */
	public Timestamp getCompoundInterestEnd()
	{
		return CompoundInterestEnd;
	}
	/**
	 * Sets the compoundInterestEnd.
	 * @param compoundInterestEnd The compoundInterestEnd to set
	 */
	public void setCompoundInterestEnd(Timestamp compoundInterestEnd)
	{
		CompoundInterestEnd = compoundInterestEnd;
	}
	/**
	 * Returns the normalInterestEnd.
	 * @return Timestamp
	 */
	public Timestamp getNormalInterestEnd()
	{
		return NormalInterestEnd;
	}
	/**
	 * Returns the normalInterestStart.
	 * @return Timestamp
	 */
	public Timestamp getNormalInterestStart()
	{
		return NormalInterestStart;
	}
	/**
	 * Sets the normalInterestEnd.
	 * @param normalInterestEnd The normalInterestEnd to set
	 */
	public void setNormalInterestEnd(Timestamp normalInterestEnd)
	{
		NormalInterestEnd = normalInterestEnd;
	}
	/**
	 * Sets the normalInterestStart.
	 * @param normalInterestStart The normalInterestStart to set
	 */
	public void setNormalInterestStart(Timestamp normalInterestStart)
	{
		NormalInterestStart = normalInterestStart;
	}
	/**
	 * Returns the commissionAmount.
	 * @return double
	 */
	public double getCommissionAmount()
	{
		return CommissionAmount;
	}
	/**
	 * Returns the commissionFeeEnd.
	 * @return Timestamp
	 */
	public Timestamp getCommissionFeeEnd()
	{
		return CommissionFeeEnd;
	}
	/**
	 * Returns the commissionStart.
	 * @return Timestamp
	 */
	public Timestamp getCommissionStart()
	{
		return CommissionStart;
	}
	/**
	 * Returns the overDueEnd.
	 * @return Timestamp
	 */
	public Timestamp getOverDueEnd()
	{
		return OverDueEnd;
	}
	/**
	 * Returns the suretyFeeAmount.
	 * @return double
	 */
	public double getSuretyFeeAmount()
	{
		return SuretyFeeAmount;
	}
	/**
	 * Returns the suretyFeeEnd.
	 * @return Timestamp
	 */
	public Timestamp getSuretyFeeEnd()
	{
		return SuretyFeeEnd;
	}
	/**
	 * Returns the suretyFeeRate.
	 * @return double
	 */
	public double getSuretyFeeRate()
	{
		return SuretyFeeRate;
	}
	/**
	 * Returns the suretyFeeStart.
	 * @return Timestamp
	 */
	public Timestamp getSuretyFeeStart()
	{
		return SuretyFeeStart;
	}
	/**
	 * Sets the commissionAmount.
	 * @param commissionAmount The commissionAmount to set
	 */
	public void setCommissionAmount(double commissionAmount)
	{
		CommissionAmount = commissionAmount;
	}
	/**
	 * Sets the commissionFeeEnd.
	 * @param commissionFeeEnd The commissionFeeEnd to set
	 */
	public void setCommissionFeeEnd(Timestamp commissionFeeEnd)
	{
		CommissionFeeEnd = commissionFeeEnd;
	}
	/**
	 * Sets the commissionStart.
	 * @param commissionStart The commissionStart to set
	 */
	public void setCommissionStart(Timestamp commissionStart)
	{
		CommissionStart = commissionStart;
	}
	/**
	 * Sets the overDueEnd.
	 * @param overDueEnd The overDueEnd to set
	 */
	public void setOverDueEnd(Timestamp overDueEnd)
	{
		OverDueEnd = overDueEnd;
	}
	/**
	 * Sets the suretyFeeAmount.
	 * @param suretyFeeAmount The suretyFeeAmount to set
	 */
	public void setSuretyFeeAmount(double suretyFeeAmount)
	{
		SuretyFeeAmount = suretyFeeAmount;
	}
	/**
	 * Sets the suretyFeeEnd.
	 * @param suretyFeeEnd The suretyFeeEnd to set
	 */
	public void setSuretyFeeEnd(Timestamp suretyFeeEnd)
	{
		SuretyFeeEnd = suretyFeeEnd;
	}
	/**
	 * Sets the suretyFeeRate.
	 * @param suretyFeeRate The suretyFeeRate to set
	 */
	public void setSuretyFeeRate(double suretyFeeRate)
	{
		SuretyFeeRate = suretyFeeRate;
	}
	/**
	 * Sets the suretyFeeStart.
	 * @param suretyFeeStart The suretyFeeStart to set
	 */
	public void setSuretyFeeStart(Timestamp suretyFeeStart)
	{
		SuretyFeeStart = suretyFeeStart;
	}
	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}
	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}
	/**
	 * Returns the detail.
	 * @return Vector
	 */
	public Vector getDetail()
	{
		return vctDetail;
	}

	/**
	 * Sets the detail.
	 * @param detail The detail to set
	 */
	public void setDetail(Vector detail)
	{
		vctDetail = detail;
	}

	/**
	 * @return Returns the detailTitle.
	 */
	public String getDetailTitle() {
		return DetailTitle;
	}
	/**
	 * @param detailTitle The detailTitle to set.
	 */
	public void setDetailTitle(String detailTitle) {
		DetailTitle = detailTitle;
	}
    /**
     * @return Returns the acceptanceUserAccount.
     */
    public String getAcceptanceUserAccount()
    {
        return AcceptanceUserAccount;
    }
    /**
     * @param acceptanceUserAccount The acceptanceUserAccount to set.
     */
    public void setAcceptanceUserAccount(String acceptanceUserAccount)
    {
        AcceptanceUserAccount = acceptanceUserAccount;
    }
    /**
     * @return Returns the acceptanceUserBank.
     */
    public String getAcceptanceUserBank()
    {
        return AcceptanceUserBank;
    }
    /**
     * @param acceptanceUserBank The acceptanceUserBank to set.
     */
    public void setAcceptanceUserBank(String acceptanceUserBank)
    {
        AcceptanceUserBank = acceptanceUserBank;
    }
    /**
     * @return Returns the applicantAccountBankNo.
     */
    public String getApplicantAccountBankNo()
    {
        return ApplicantAccountBankNo;
    }
    /**
     * @param applicantAccountBankNo The applicantAccountBankNo to set.
     */
    public void setApplicantAccountBankNo(String applicantAccountBankNo)
    {
        ApplicantAccountBankNo = applicantAccountBankNo;
    }
    /**
     * @return Returns the applicantAccountNo.
     */
    public String getApplicantAccountNo()
    {
        return ApplicantAccountNo;
    }
    /**
     * @param applicantAccountNo The applicantAccountNo to set.
     */
    public void setApplicantAccountNo(String applicantAccountNo)
    {
        ApplicantAccountNo = applicantAccountNo;
    }
    /**
     * @return Returns the applicantName.
     */
    public String getApplicantName()
    {
        return ApplicantName;
    }
    /**
     * @param applicantName The applicantName to set.
     */
    public void setApplicantName(String applicantName)
    {
        ApplicantName = applicantName;
    }
    /**
     * @return Returns the billAcceptanceUser.
     */
    public String getBillAcceptanceUser()
    {
        return BillAcceptanceUser;
    }
    /**
     * @param billAcceptanceUser The billAcceptanceUser to set.
     */
    public void setBillAcceptanceUser(String billAcceptanceUser)
    {
        BillAcceptanceUser = billAcceptanceUser;
    }
    /**
     * @return Returns the discountBillNo.
     */
    public String getDiscountBillNo()
    {
        return DiscountBillNo;
    }
    /**
     * @param discountBillNo The discountBillNo to set.
     */
    public void setDiscountBillNo(String discountBillNo)
    {
        DiscountBillNo = discountBillNo;
    }
	/**
	 * @return Returns the newRate.
	 */
	public double getNewRate()
	{
		return NewRate;
	}
	/**
	 * @param newRate The newRate to set.
	 */
	public void setNewRate(double newRate)
	{
		NewRate = newRate;
	}
	/**
	 * @return Returns the strDepositBillNO.
	 */
	public String getDepositBillNO() {
		return strDepositBillNO;
	}
	/**
	 * @param strDepositBillNO The strDepositBillNO to set.
	 */
	public void setDepositBillNO(String strDepositBillNO) {
		this.strDepositBillNO = strDepositBillNO;
	}

	/**
	 * @return Returns the isCapitalAndInterestTransfer.
	 */
	public long getIsCapitalAndInterestTransfer() {
		return IsCapitalAndInterestTransfer;
	}
	
	/**
	 * 	@param isCapitalAndInterestTransfer The isCapitalAndInterestTransfer to set.
	 */
	public void setIsCapitalAndInterestTransfer(long isCapitalAndInterestTransfer) {
		IsCapitalAndInterestTransfer = isCapitalAndInterestTransfer;
	}
	public double getTotalInterest() {
		return TotalInterest;
	}
	public void setTotalInterest(double totalInterest) {
		TotalInterest = totalInterest;
	}

	public String getAccountName() {
		return accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public double getAdvanceRate()
	{
	
		return advanceRate;
	}
	
	public void setAdvanceRate(double advanceRate)
	{
	
		this.advanceRate = advanceRate;
	}
	public String getExamUserName() {
		return ExamUserName;
	}
	public void setExamUserName(String examUserName) {
		ExamUserName = examUserName;
	}
	public double getInterest1() {
		return interest1;
	}
	public void setInterest1(double interest1) {
		this.interest1 = interest1;
	}
	public double getInterest2() {
		return interest2;
	}
	public void setInterest2(double interest2) {
		this.interest2 = interest2;
	}
	public double getInterest3() {
		return interest3;
	}
	public void setInterest3(double interest3) {
		this.interest3 = interest3;
	}
	public Timestamp getEndDate1() {
		return endDate1;
	}
	public void setEndDate1(Timestamp endDate1) {
		this.endDate1 = endDate1;
	}
	public Timestamp getEndDate2() {
		return endDate2;
	}
	public void setEndDate2(Timestamp endDate2) {
		this.endDate2 = endDate2;
	}
	public Timestamp getEndDate3() {
		return endDate3;
	}
	public void setEndDate3(Timestamp endDate3) {
		this.endDate3 = endDate3;
	}
	public double getRate1() {
		return rate1;
	}
	public void setRate1(double rate1) {
		this.rate1 = rate1;
	}
	public double getRate2() {
		return rate2;
	}
	public void setRate2(double rate2) {
		this.rate2 = rate2;
	}
	public double getRate3() {
		return rate3;
	}
	public void setRate3(double rate3) {
		this.rate3 = rate3;
	}
	public Timestamp getStartDate1() {
		return startDate1;
	}
	public void setStartDate1(Timestamp startDate1) {
		this.startDate1 = startDate1;
	}
	public Timestamp getStartDate2() {
		return startDate2;
	}
	public void setStartDate2(Timestamp startDate2) {
		this.startDate2 = startDate2;
	}
	public Timestamp getStartDate3() {
		return startDate3;
	}
	public void setStartDate3(Timestamp startDate3) {
		this.startDate3 = startDate3;
	}
	public Timestamp getNegotiateEndtDate1() {
		return NegotiateEndtDate1;
	}
	public void setNegotiateEndtDate1(Timestamp negotiateEndtDate1) {
		NegotiateEndtDate1 = negotiateEndtDate1;
	}
	public Timestamp getNegotiateEndtDate2() {
		return NegotiateEndtDate2;
	}
	public void setNegotiateEndtDate2(Timestamp negotiateEndtDate2) {
		NegotiateEndtDate2 = negotiateEndtDate2;
	}
	public double getNegotiateInterest1() {
		return NegotiateInterest1;
	}
	public void setNegotiateInterest1(double negotiateInterest1) {
		NegotiateInterest1 = negotiateInterest1;
	}
	public double getNegotiateInterest2() {
		return NegotiateInterest2;
	}
	public void setNegotiateInterest2(double negotiateInterest2) {
		NegotiateInterest2 = negotiateInterest2;
	}
	public double getNegotiateRate1() {
		return NegotiateRate1;
	}
	public void setNegotiateRate1(double negotiateRate1) {
		NegotiateRate1 = negotiateRate1;
	}
	public double getNegotiateRate2() {
		return NegotiateRate2;
	}
	public void setNegotiateRate2(double negotiateRate2) {
		NegotiateRate2 = negotiateRate2;
	}
	public Timestamp getNegotiateStartDate1() {
		return NegotiateStartDate1;
	}
	public void setNegotiateStartDate1(Timestamp negotiateStartDate1) {
		NegotiateStartDate1 = negotiateStartDate1;
	}
	public Timestamp getNegotiateStartDate2() {
		return NegotiateStartDate2;
	}
	public void setNegotiateStartDate2(Timestamp negotiateStartDate2) {
		NegotiateStartDate2 = negotiateStartDate2;
	}
	public double getPecisionInterestAmount() {
		return PecisionInterestAmount;
	}
	public void setPecisionInterestAmount(double pecisionInterestAmount) {
		PecisionInterestAmount = pecisionInterestAmount;
	}
	public double getSumNegoBlanceAmount1() {
		return sumNegoBlanceAmount1;
	}
	public void setSumNegoBlanceAmount1(double sumNegoBlanceAmount1) {
		this.sumNegoBlanceAmount1 = sumNegoBlanceAmount1;
	}
	public double getSumNegoBlanceAmount2() {
		return sumNegoBlanceAmount2;
	}
	public void setSumNegoBlanceAmount2(double sumNegoBlanceAmount2) {
		this.sumNegoBlanceAmount2 = sumNegoBlanceAmount2;
	}
	public double getSumNegoBlanceAmount3() {
		return sumNegoBlanceAmount3;
	}
	public void setSumNegoBlanceAmount3(double sumNegoBlanceAmount3) {
		this.sumNegoBlanceAmount3 = sumNegoBlanceAmount3;
	}
	public double getCompoundRate1() {
		return compoundRate1;
	}
	public void setCompoundRate1(double compoundRate1) {
		this.compoundRate1 = compoundRate1;
	}
	public double getCompoundRate2() {
		return compoundRate2;
	}
	public void setCompoundRate2(double compoundRate2) {
		this.compoundRate2 = compoundRate2;
	}
	public double getCompoundRate3() {
		return compoundRate3;
	}
	public void setCompoundRate3(double compoundRate3) {
		this.compoundRate3 = compoundRate3;
	}
	public double getCompoundInterest1() {
		return compoundInterest1;
	}
	public void setCompoundInterest1(double compoundInterest1) {
		this.compoundInterest1 = compoundInterest1;
	}
	public double getCompoundInterest2() {
		return compoundInterest2;
	}
	public void setCompoundInterest2(double compoundInterest2) {
		this.compoundInterest2 = compoundInterest2;
	}
	public double getCompoundInterest3() {
		return compoundInterest3;
	}
	public void setCompoundInterest3(double compoundInterest3) {
		this.compoundInterest3 = compoundInterest3;
	}
	public double getOverDueRate1() {
		return overDueRate1;
	}
	public void setOverDueRate1(double overDueRate1) {
		this.overDueRate1 = overDueRate1;
	}
	public double getOverDueRate2() {
		return overDueRate2;
	}
	public void setOverDueRate2(double overDueRate2) {
		this.overDueRate2 = overDueRate2;
	}
	public double getOverDueRate3() {
		return overDueRate3;
	}
	public void setOverDueRate3(double overDueRate3) {
		this.overDueRate3 = overDueRate3;
	}
	public double getOverDueInterest1() {
		return overDueInterest1;
	}
	public void setOverDueInterest1(double overDueInterest1) {
		this.overDueInterest1 = overDueInterest1;
	}
	public double getOverDueInterest2() {
		return overDueInterest2;
	}
	public void setOverDueInterest2(double overDueInterest2) {
		this.overDueInterest2 = overDueInterest2;
	}
	public double getOverDueInterest3() {
		return overDueInterest3;
	}
	public void setOverDueInterest3(double overDueInterest3) {
		this.overDueInterest3 = overDueInterest3;
	}
	public Timestamp getCompoundStartDate1() {
		return compoundStartDate1;
	}
	public void setCompoundStartDate1(Timestamp compoundStartDate1) {
		this.compoundStartDate1 = compoundStartDate1;
	}
	public Timestamp getCompoundStartDate2() {
		return compoundStartDate2;
	}
	public void setCompoundStartDate2(Timestamp compoundStartDate2) {
		this.compoundStartDate2 = compoundStartDate2;
	}
	public Timestamp getCompoundStartDate3() {
		return compoundStartDate3;
	}
	public void setCompoundStartDate3(Timestamp compoundStartDate3) {
		this.compoundStartDate3 = compoundStartDate3;
	}
	public Timestamp getCompoundEndDate1() {
		return compoundEndDate1;
	}
	public void setCompoundEndDate1(Timestamp compoundEndDate1) {
		this.compoundEndDate1 = compoundEndDate1;
	}
	public Timestamp getCompoundEndDate2() {
		return compoundEndDate2;
	}
	public void setCompoundEndDate2(Timestamp compoundEndDate2) {
		this.compoundEndDate2 = compoundEndDate2;
	}
	public Timestamp getCompoundEndDate3() {
		return compoundEndDate3;
	}
	public void setCompoundEndDate3(Timestamp compoundEndDate3) {
		this.compoundEndDate3 = compoundEndDate3;
	}
	public Timestamp getOverDueStartDate1() {
		return overDueStartDate1;
	}
	public void setOverDueStartDate1(Timestamp overDueStartDate1) {
		this.overDueStartDate1 = overDueStartDate1;
	}
	public Timestamp getOverDueStartDate2() {
		return overDueStartDate2;
	}
	public void setOverDueStartDate2(Timestamp overDueStartDate2) {
		this.overDueStartDate2 = overDueStartDate2;
	}
	public Timestamp getOverDueStartDate3() {
		return overDueStartDate3;
	}
	public void setOverDueStartDate3(Timestamp overDueStartDate3) {
		this.overDueStartDate3 = overDueStartDate3;
	}
	public Timestamp getOverDueEndDate1() {
		return overDueEndDate1;
	}
	public void setOverDueEndDate1(Timestamp overDueEndDate1) {
		this.overDueEndDate1 = overDueEndDate1;
	}
	public Timestamp getOverDueEndDate2() {
		return overDueEndDate2;
	}
	public void setOverDueEndDate2(Timestamp overDueEndDate2) {
		this.overDueEndDate2 = overDueEndDate2;
	}
	public Timestamp getOverDueEndDate3() {
		return overDueEndDate3;
	}
	public void setOverDueEndDate3(Timestamp overDueEndDate3) {
		this.overDueEndDate3 = overDueEndDate3;
	}
	public long getIsAutoContinue() {
		return isAutoContinue;
	}
	public void setIsAutoContinue(long isAutoContinue) {
		this.isAutoContinue = isAutoContinue;
	}
	public long getNAccountTypeID() {
		return nAccountTypeID;
	}
	public void setNAccountTypeID(long accountTypeID) {
		nAccountTypeID = accountTypeID;
	}
	public String getSSubject() {
		return sSubject;
	}
	public void setSSubject(String subject) {
		sSubject = subject;
	}
	
}

/*
 * Created on 2003-12-23
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.remind.resultinfo;

import java.util.ArrayList;
import java.util.HashMap;

import com.iss.itreasury.loan.extendapply.dataentity.ExtendContractInfo;
import com.iss.itreasury.loan.query.dataentity.QueryPayformOverdue;

/**
 * @author wlming
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class RemindAssemble implements java.io.Serializable {
	private long OfficeID = -1;// 办事处标识

	private long CurrencyID = -1;// 币种标识

	// 未复核、未勾账提醒处理
	private long IsNeedUncheckRemind = -1;// 是否需要未复核、未勾账提醒

	private long UncheckRecordCount = -1;// 未复核、未勾账记录数量

	private UncheckTransactionInfo[] UncheckTransInfos = null;// 未复核、未勾账记录信息

	// 透支账户提醒
	private long IsNeedOverDraftRemind = -1;// 是否需要透支账户的提醒

	private long OverDraftAccountCount = -1;// 透支账户总数量

	private OverDraftAccountInfo[] OverDraftAccounts = null;// 透支账户信息

	// 未放款通知单提醒
	private long IsNeedUngrantPayFormRemind = -1;// 是否需要未放款通知单的提醒

	private long UngrantPayFormCount = -1;// 未放款通知单总数量

	private UngrantPayFormInfo[] UngrantPayForms = null;// 未放款通知单信息

	// 未处理免换通知单提醒
	private long IsNeedFreeFormRemind = -1;// 是否需要免换通知单的提醒

	private long FreeFormCount = -1;// 免换通知单总数量

	private FreeFormInfo[] FreeForms = null;// 免换通知单信息

	// 未处理提前还款通知单提醒
	private long IsNeedAheadRepayFormRemind = -1;// 是否需要提前还款通知单的提醒

	private long AheadRepayFormCount = -1;// 提前还款通知单总数量

	private AheadRepayFormInfo[] AheadRepayForms = null;// 提前还款通知单信息

	// 到期定期存款提醒
	private long FixedDepositAheadDays = -1;// 定期存款到期前多少天提醒（0，表示不须提醒）

	private long FixedDepositCount = -1;// 即将到期存单总数量

	private AtTermFixedDepositInfo[] FixedDeposits = null;// 即将到期存单信息

	// 到期贷款提醒
	private long LoanAheadDays = -1;// 贷款到期前多少天提醒（0，表示不须提醒）

	private long LoanCount = -1;// 即将到期贷款总数量

	private AtTermLoanInfo[] Loans = null;// 即将到期贷款信息

	// 展期合同设置
	private long ExhibitionDays = -1; // 是否需要展期合同提醒（0，表示不须提醒）

	private long SecExhibitionCount = -1; // 展期合同业务总数量

	private ExtendContractInfo[] SecExhibitions = null; // 展期合同业务信息

	// 网上业务提醒
	private long IsNeedOBTransactionRemind = -1;// 是否需要网上业务的提醒

	private long OBTransactionCount = -1;// 网上业务总数量

	private OBTransactionInfo[] OBTransactions = null;// 网上业务信息

	// 证券业务提醒
	private long IsNeedSecTransactionRemind = -1;// 是否需要证券业务的提醒

	private long SecTransactionCount = -1;// 证券业务总数量

	private SecTransactionInfo[] SecTransactions = null;// 证券业务信息

	// 证券业务未复核、暂存提醒
	private long IsNeedSecUncheckRemind = -1;// 是否需要证券业务未复核、暂存提醒

	private long SecUncheckRecordCount = -1;// 证券业务未复核、暂存记录数量

	private SecUncheckTransInfo[] SecUncheckTransInfos = null;// 证券业务未复核、暂存记录信息

	// 业务提醒信息
	public static HashMap RemintMSG = new HashMap();

	public static HashMap OBCountForSoundRemind = new HashMap();// 网上业务总数量(为声音提醒新加的)

	private static long IsOBSoundRemind = -1;// 是否需要网上业务的声音提醒(为声音提醒新加的)

	// 汇票到期提醒及提前天数
	private long IsNeedBillRemindDay = -1;// 是否需要汇票到期提醒

	private long BillRemindDay = -1;// 汇票到期提醒提前天数

	private BillRemindInfo[] BillRemindInfos = null;

	// 汇票托收提醒及提前天数
	private long IsNeedBillConsignReceiveDay = -1;// 汇票托收提醒及提前天数

	private long BillConsignReceiveDay = -1;// 汇票托收提醒及提前天数

	private BillConsignReceiveInfo[] BillConsignReceiveInfos = null;

	// 冻结到期提醒及提前天数
	private long IsNeedFreezeDay = -1;// nFreezeDay

	private long FreezeDay = -1;//

	private FreezeInfo[] FreezeInfos = null;

	// 挂失到期提醒及挂失天数
	private long IsNeedLossDay = -1;// nLossDay

	private long LossDay = -1;//

	private LossInfo[] LossInfos = null;

	// 贷款呆滞提醒及到期后N天提醒
	private long IsNeedPrimnessDay = -1;// nPrimnessDay

	private long PrimnessDay = -1;//

	private PrimnessInfo[] PrimnessInfos = null;

	// 每日银行余额
	private double IsNeedBankBalance = 0.0; // 每日银行余额提醒金额

	private long BankBalanceCount = -1; // 每日实际银行余额低于最低笔数

	private BankBalanceInfo[] BankBalances = null;

	// 银行头寸与活期存款之比
	private double IsNeedRateOfBankAndSett = 0.0; // 银行头寸与活期存款提醒比例

	private double RateOfBankAndSett = 0.0; // 银行头寸与活期存款实际比例

	// 账户限额设置
	private long IsNeedAccountDeadLine = -1; // 是否需要限额底线提醒

	private ArrayList arrl_BalanceLimited = null; // 需要提醒的逾越余额底线的账号ID

	private ArrayList arrl_DebitLimited = null; // 需要提醒的周期内逾越借方累计发生额的账户ID

	private ArrayList arrl_CreditLimited = null; // 需要提醒的周期内逾越贷方累计发生额的账户ID

	private ArrayList arrl_TranspayLimited = null; // 需要提醒的逾过单笔支付发生额上限的账户ID

	// 协定存款
	private long lNegotiation = -1; // 协定存款到期提前提醒的天数

	private long lSumNegotiation = 0; // 需要协定存款提醒的账户ID的数量

	private String str_NegotiationAccountId = null; // 需要协定存款提醒的账户ID的字符串

	private ArrayList arrl_Negotiation = null; // 需要提醒的协定存款详细信息

	// 保证金存款提醒
	private long lEnsureDeposit = -1; // 保证金存款到期提前提醒的天数

	private long lSumEnsureDeposit = 0; // 需要提醒的保证金存款账户ID的数量

	private String str_EnsureDeposit = null; // 需要提醒的保证金账户ID字符串

	private ArrayList arrl_EnsureDeposit = null; // 需要提醒的保证金存款账户的详细信息
	
	private long IsNeedMatureRemind = -1; //是否需要结息日提醒
	
	private long IsNeedShouRemind=-1;//是否需要手工收款
	
	//放款通知单逾期转表外提醒
	private QueryPayformOverdue[] overdue = null;
	private long OFFBALANCEACCOUNT = -1;// 需要逾期转表外的放款通知单条数
	private long IsNeedOffBalanceFormRemind = -1;// 是否需要放款单逾期转表外提醒

	/**
	 * 2006-10-16
	 * 设置是否结息日提醒
	 * @return IsNeedMatrueRemind
	 */
		

	public long getIsNeedMatureRemind() {
		return IsNeedMatureRemind;
	}

	public void setIsNeedMatureRemind(long IsNeedMatureRemind) {
		this.IsNeedMatureRemind = IsNeedMatureRemind;
	}

	

	/**
	 * 需要提醒的保证金存款账户的详细信息
	 * @return 返回 arrl_EnsureDeposit。
	 */
	public ArrayList getArrl_EnsureDeposit() {
		return arrl_EnsureDeposit;
	}

	/**
	 * 需要提醒的保证金存款账户的详细信息
	 * @param arrl_EnsureDeposit
	 *            要设置的 arrl_EnsureDeposit。
	 */
	public void setArrl_EnsureDeposit(ArrayList arrl_EnsureDeposit) {
		this.arrl_EnsureDeposit = arrl_EnsureDeposit;
	}

	/**
	 * 需要提醒的保证金存款账户ID的数量
	 * @return 返回 lSumEnsureDeposit。
	 */
	public long getLSumEnsureDeposit() {
		return lSumEnsureDeposit;
	}

	/**
	 * 需要提醒的保证金存款账户ID的数量
	 * @param sumEnsureDeposit
	 *            要设置的 lSumEnsureDeposit。
	 */
	public void setLSumEnsureDeposit(long sumEnsureDeposit) {
		lSumEnsureDeposit = sumEnsureDeposit;
	}

	/**
	 * 需要提醒的保证金账户ID字符串
	 * @return 返回 str_EnsureDeposit。
	 */
	public String getStr_EnsureDeposit() {
		return str_EnsureDeposit;
	}

	/**
	 * 需要提醒的保证金账户ID字符串
	 * @param str_EnsureDeposit
	 *            要设置的 str_EnsureDeposit。
	 */
	public void setStr_EnsureDeposit(String str_EnsureDeposit) {
		this.str_EnsureDeposit = str_EnsureDeposit;
	}

	/**
	 * 需要提醒的协定存款详细信息
	 * 
	 * @return 返回 arrl_Negotiation。
	 */
	public ArrayList getArrl_Negotiation() {
		return arrl_Negotiation;
	}

	/**
	 * 需要提醒的协定存款详细信息
	 * 
	 * @param arrl_Negotiation
	 *            要设置的 arrl_Negotiation。
	 */
	public void setArrl_Negotiation(ArrayList arrl_Negotiation) {
		this.arrl_Negotiation = arrl_Negotiation;
	}

	/**
	 * 需要协定存款提醒的账户ID的数量
	 * 
	 * @return 返回 lSumNegotiation。
	 */
	public long getLSumNegotiation() {
		return lSumNegotiation;
	}

	/**
	 * 需要协定存款提醒的账户ID的数量
	 * 
	 * @param sumNegotiation
	 *            要设置的 lSumNegotiation。
	 */
	public void setLSumNegotiation(long sumNegotiation) {
		lSumNegotiation = sumNegotiation;
	}

	/**
	 * 需要协定存款提醒的账户ID的字符串
	 * 
	 * @return 返回 str_NegotiationAccountId。
	 */
	public String getStr_NegotiationAccountId() {
		return str_NegotiationAccountId;
	}

	/**
	 * 需要协定存款提醒的账户ID的字符串
	 * 
	 * @param str_NegotiationAccountId
	 *            要设置的 str_NegotiationAccountId。
	 */
	public void setStr_NegotiationAccountId(String str_NegotiationAccountId) {
		this.str_NegotiationAccountId = str_NegotiationAccountId;
	}

	/**
	 * 保证金存款到期提前提醒的天数
	 * @return 返回 lNeedEnsureDeposit。
	 */
	public long getLEnsureDeposit() {
		return lEnsureDeposit;
	}

	/**
	 * 保证金存款到期提前提醒的天数
	 * @param needEnsureDeposit 要设置的 lNeedEnsureDeposit。
	 */
	public void setLEnsureDeposit(long needEnsureDeposit) {
		lEnsureDeposit = needEnsureDeposit;
	}

	/**
	 * 协定存款到期提前提醒的天数
	 * @return 返回 lNeedNegotiation。
	 */
	public long getLNegotiation() {
		return lNegotiation;
	}

	/**
	 * 协定存款到期提前提醒的天数
	 * @param needNegotiation 要设置的 lNeedNegotiation。
	 */
	public void setLNegotiation(long needNegotiation) {
		lNegotiation = needNegotiation;
	}

	/**
	 * @return Returns the exhibitionDays.
	 */
	public long getExhibitionDays() {
		return ExhibitionDays;
	}

	/**
	 * @param exhibitionDays
	 *            The exhibitionDays to set.
	 */
	public void setExhibitionDays(long exhibitionDays) {
		ExhibitionDays = exhibitionDays;
	}

	/**
	 * @return Returns the secExhibitionCount.
	 */
	public long getSecExhibitionCount() {
		return SecExhibitionCount;
	}

	/**
	 * @param secExhibitionCount
	 *            The secExhibitionCount to set.
	 */
	public void setSecExhibitionCount(long secExhibitionCount) {
		SecExhibitionCount = secExhibitionCount;
	}

	/**
	 * @return Returns the secExhibitions.
	 */
	public ExtendContractInfo[] getSecExhibitions() {
		return SecExhibitions;
	}

	/**
	 * @param secExhibitions
	 *            The secExhibitions to set.
	 */
	public void setSecExhibitions(ExtendContractInfo[] secExhibitions) {
		SecExhibitions = secExhibitions;
	}

	/**
	 * @return 返回 isNeedAccountDeadLine。
	 */
	public long getIsNeedAccountDeadLine() {
		return IsNeedAccountDeadLine;
	}

	/**
	 * @param isNeedAccountDeadLine
	 *            要设置的 isNeedAccountDeadLine。
	 */
	public void setIsNeedAccountDeadLine(long isNeedAccountDeadLine) {
		IsNeedAccountDeadLine = isNeedAccountDeadLine;
	}

	/**
	 * @return
	 */
	public long getIsNeedUncheckRemind() {
		return IsNeedUncheckRemind;
	}

	/**
	 * @return
	 */
	public long getUncheckRecordCount() {
		return UncheckRecordCount;
	}

	/**
	 * @return
	 */
	public UncheckTransactionInfo[] getUncheckTransInfoS() {
		return UncheckTransInfos;
	}

	/**
	 * @param l
	 */
	public void setIsNeedUncheckRemind(long l) {
		IsNeedUncheckRemind = l;
	}

	/**
	 * @param l
	 */
	public void setUncheckRecordCount(long l) {
		UncheckRecordCount = l;
	}

	/**
	 * @param infos
	 */
	public void setUncheckTransInfoS(UncheckTransactionInfo[] infos) {
		UncheckTransInfos = infos;
	}

	/**
	 * @return
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getOfficeID() {
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l) {
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l) {
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getIsNeedOverDraftRemind() {
		return IsNeedOverDraftRemind;
	}

	/**
	 * @return
	 */
	public long getOverDraftAccountCount() {
		return OverDraftAccountCount;
	}

	/**
	 * @return
	 */
	public OverDraftAccountInfo[] getOverDraftAccounts() {
		return OverDraftAccounts;
	}

	/**
	 * @return
	 */
	public UncheckTransactionInfo[] getUncheckTransInfos() {
		return UncheckTransInfos;
	}

	/**
	 * @param l
	 */
	public void setIsNeedOverDraftRemind(long l) {
		IsNeedOverDraftRemind = l;
	}

	/**
	 * @param l
	 */
	public void setOverDraftAccountCount(long l) {
		OverDraftAccountCount = l;
	}

	/**
	 * @param infos
	 */
	public void setOverDraftAccounts(OverDraftAccountInfo[] infos) {
		OverDraftAccounts = infos;
	}

	/**
	 * @param infos
	 */
	public void setUncheckTransInfos(UncheckTransactionInfo[] infos) {
		UncheckTransInfos = infos;
	}

	/**
	 * @return
	 */
	public long getIsNeedUngrantPayFormRemind() {
		return IsNeedUngrantPayFormRemind;
	}

	/**
	 * @return
	 */
	public long getUngrantPayFormCount() {
		return UngrantPayFormCount;
	}

	/**
	 * @return
	 */
	public UngrantPayFormInfo[] getUngrantPayForms() {
		return UngrantPayForms;
	}

	/**
	 * @param l
	 */
	public void setIsNeedUngrantPayFormRemind(long l) {
		IsNeedUngrantPayFormRemind = l;
	}

	/**
	 * @param l
	 */
	public void setUngrantPayFormCount(long l) {
		UngrantPayFormCount = l;
	}

	/**
	 * @param infos
	 */
	public void setUngrantPayForms(UngrantPayFormInfo[] infos) {
		UngrantPayForms = infos;
	}

	/**
	 * @return
	 */
	public long getFixedDepositAheadDays() {
		return FixedDepositAheadDays;
	}

	/**
	 * @return
	 */
	public long getFixedDepositCount() {
		return FixedDepositCount;
	}

	/**
	 * @return
	 */
	public AtTermFixedDepositInfo[] getFixedDeposits() {
		return FixedDeposits;
	}

	/**
	 * @return
	 */
	public long getLoanAheadDays() {
		return LoanAheadDays;
	}

	/**
	 * @return
	 */
	public long getLoanCount() {
		return LoanCount;
	}

	/**
	 * @return
	 */
	public AtTermLoanInfo[] getLoans() {
		return Loans;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositAheadDays(long l) {
		FixedDepositAheadDays = l;
	}

	/**
	 * @param l
	 */
	public void setFixedDepositCount(long l) {
		FixedDepositCount = l;
	}

	/**
	 * @param infos
	 */
	public void setFixedDeposits(AtTermFixedDepositInfo[] infos) {
		FixedDeposits = infos;
	}

	/**
	 * @param l
	 */
	public void setLoanAheadDays(long l) {
		LoanAheadDays = l;
	}

	/**
	 * @param l
	 */
	public void setLoanCount(long l) {
		LoanCount = l;
	}

	/**
	 * @param infos
	 */
	public void setLoans(AtTermLoanInfo[] infos) {
		Loans = infos;
	}

	/**
	 * @return
	 */
	public long getAheadPayFormCount() {
		return AheadRepayFormCount;
	}

	/**
	 * @return
	 */
	public AheadRepayFormInfo[] getAheadPayForms() {
		return AheadRepayForms;
	}

	/**
	 * @return
	 */
	public long getIsNeedAheadPayFormRemind() {
		return IsNeedAheadRepayFormRemind;
	}

	/**
	 * @param l
	 */
	public void setAheadPayFormCount(long l) {
		AheadRepayFormCount = l;
	}

	/**
	 * @param infos
	 */
	public void setAheadPayForms(AheadRepayFormInfo[] infos) {
		AheadRepayForms = infos;
	}

	/**
	 * @param l
	 */
	public void setIsNeedAheadPayFormRemind(long l) {
		IsNeedAheadRepayFormRemind = l;
	}

	/**
	 * @return
	 */
	public long getAheadRepayFormCount() {
		return AheadRepayFormCount;
	}

	/**
	 * @return
	 */
	public AheadRepayFormInfo[] getAheadRepayForms() {
		return AheadRepayForms;
	}

	/**
	 * @return
	 */
	public long getFreeFormCount() {
		return FreeFormCount;
	}

	/**
	 * @return
	 */
	public FreeFormInfo[] getFreeForms() {
		return FreeForms;
	}

	/**
	 * @return
	 */
	public long getIsNeedAheadRepayFormRemind() {
		return IsNeedAheadRepayFormRemind;
	}

	/**
	 * @return
	 */
	public long getIsNeedFreeFormRemind() {
		return IsNeedFreeFormRemind;
	}

	/**
	 * @param l
	 */
	public void setAheadRepayFormCount(long l) {
		AheadRepayFormCount = l;
	}

	/**
	 * @param infos
	 */
	public void setAheadRepayForms(AheadRepayFormInfo[] infos) {
		AheadRepayForms = infos;
	}

	/**
	 * @param l
	 */
	public void setFreeFormCount(long l) {
		FreeFormCount = l;
	}

	/**
	 * @param infos
	 */
	public void setFreeForms(FreeFormInfo[] infos) {
		FreeForms = infos;
	}

	/**
	 * @param l
	 */
	public void setIsNeedAheadRepayFormRemind(long l) {
		IsNeedAheadRepayFormRemind = l;
	}

	/**
	 * @param l
	 */
	public void setIsNeedFreeFormRemind(long l) {
		IsNeedFreeFormRemind = l;
	}

	/**
	 * @return Returns the isNeedOBTransactionRemind.
	 */
	public long getIsNeedOBTransactionRemind() {
		return IsNeedOBTransactionRemind;
	}

	/**
	 * @param isNeedOBTransactionRemind
	 *            The isNeedOBTransactionRemind to set.
	 */
	public void setIsNeedOBTransactionRemind(long isNeedOBTransactionRemind) {
		IsNeedOBTransactionRemind = isNeedOBTransactionRemind;
	}

	/**
	 * @return Returns the oBTransactionCount.
	 */
	public long getOBTransactionCount() {
		return OBTransactionCount;
	}

	/**
	 * @param transactionCount
	 *            The oBTransactionCount to set.
	 */
	public void setOBTransactionCount(long transactionCount) {
		OBTransactionCount = transactionCount;
	}

	/**
	 * @return Returns the obtransactions.
	 */
	public OBTransactionInfo[] getOBTransactions() {
		return OBTransactions;
	}

	/**
	 * @param obtransactions
	 *            The obtransactions to set.
	 */
	public void setOBTransactions(OBTransactionInfo[] obtransactions) {
		this.OBTransactions = obtransactions;
	}

	/**
	 * @return Returns the isNeedSecTransactionRemind.
	 */
	public long getIsNeedSecTransactionRemind() {
		return IsNeedSecTransactionRemind;
	}

	/**
	 * @param isNeedSecTransactionRemind
	 *            The isNeedSecTransactionRemind to set.
	 */
	public void setIsNeedSecTransactionRemind(long isNeedSecTransactionRemind) {
		IsNeedSecTransactionRemind = isNeedSecTransactionRemind;
	}

	/**
	 * @return Returns the secTransactionCount.
	 */
	public long getSecTransactionCount() {
		return SecTransactionCount;
	}

	/**
	 * @param secTransactionCount
	 *            The secTransactionCount to set.
	 */
	public void setSecTransactionCount(long secTransactionCount) {
		SecTransactionCount = secTransactionCount;
	}

	/**
	 * @return Returns the secTransactions.
	 */
	public SecTransactionInfo[] getSecTransactions() {
		return SecTransactions;
	}

	/**
	 * @param secTransactions
	 *            The secTransactions to set.
	 */
	public void setSecTransactions(SecTransactionInfo[] secTransactions) {
		SecTransactions = secTransactions;
	}

	/**
	 * @return Returns the isNeedSecUncheckRemind.
	 */
	public long getIsNeedSecUncheckRemind() {
		return IsNeedSecUncheckRemind;
	}

	/**
	 * @param isNeedSecUncheckRemind
	 *            The isNeedSecUncheckRemind to set.
	 */
	public void setIsNeedSecUncheckRemind(long isNeedSecUncheckRemind) {
		IsNeedSecUncheckRemind = isNeedSecUncheckRemind;
	}

	/**
	 * @return Returns the secUncheckRecordCount.
	 */
	public long getSecUncheckRecordCount() {
		return SecUncheckRecordCount;
	}

	/**
	 * @param secUncheckRecordCount
	 *            The secUncheckRecordCount to set.
	 */
	public void setSecUncheckRecordCount(long secUncheckRecordCount) {
		SecUncheckRecordCount = secUncheckRecordCount;
	}

	/**
	 * @return Returns the secUncheckTransInfos.
	 */
	public SecUncheckTransInfo[] getSecUncheckTransInfos() {
		return SecUncheckTransInfos;
	}

	/**
	 * @param secUncheckTransInfos
	 *            The secUncheckTransInfos to set.
	 */
	public void setSecUncheckTransInfos(SecUncheckTransInfo[] secUncheckTransInfos) {
		SecUncheckTransInfos = secUncheckTransInfos;
	}

	/**
	 * @return Returns the isOBSoundRemind.
	 */
	public static long getIsOBSoundRemind() {
		return IsOBSoundRemind;
	}

	/**
	 * @param isOBSoundRemind
	 *            The isOBSoundRemind to set.
	 */
	public static void setIsOBSoundRemind(long isOBSoundRemind) {
		IsOBSoundRemind = isOBSoundRemind;
	}

	/**
	 * @return Returns the oBCountForSoundRemind.
	 */
	public static HashMap getOBCountForSoundRemind() {
		return OBCountForSoundRemind;
	}

	/**
	 * @param countForSoundRemind
	 *            The oBCountForSoundRemind to set.
	 */
	public static void setOBCountForSoundRemind(HashMap countForSoundRemind) {
		OBCountForSoundRemind = countForSoundRemind;
	}

	/**
	 * @return Returns the remintMSG.
	 */
	public static HashMap getRemintMSG() {
		return RemintMSG;
	}

	/**
	 * @param remintMSG
	 *            The remintMSG to set.
	 */
	public static void setRemintMSG(HashMap remintMSG) {
		RemintMSG = remintMSG;
	}

	/**
	 * @return Returns the billConsignReceiveDay.
	 */
	public long getBillConsignReceiveDay() {
		return BillConsignReceiveDay;
	}

	/**
	 * @param billConsignReceiveDay
	 *            The billConsignReceiveDay to set.
	 */
	public void setBillConsignReceiveDay(long billConsignReceiveDay) {
		BillConsignReceiveDay = billConsignReceiveDay;
	}

	/**
	 * @return Returns the billRemindDay.
	 */
	public long getBillRemindDay() {
		return BillRemindDay;
	}

	/**
	 * @param billRemindDay
	 *            The billRemindDay to set.
	 */
	public void setBillRemindDay(long billRemindDay) {
		BillRemindDay = billRemindDay;
	}

	public long getIsNeedBillRemindDay() {
		return IsNeedBillRemindDay;
	}

	/**
	 * @param isNeedBillRemindDay
	 *            The isNeedBillRemindDay to set.
	 */
	public void setIsNeedBillRemindDay(long isNeedBillRemindDay) {
		IsNeedBillRemindDay = isNeedBillRemindDay;
	}

	/**
	 * @return Returns the isNeedLossDay.
	 */
	public long getIsNeedLossDay() {
		return IsNeedLossDay;
	}

	/**
	 * @param isNeedLossDay
	 *            The isNeedLossDay to set.
	 */
	public void setIsNeedLossDay(long isNeedLossDay) {
		IsNeedLossDay = isNeedLossDay;
	}

	/**
	 * @return Returns the isNeedPrimnessDay.
	 */
	public long getIsNeedPrimnessDay() {
		return IsNeedPrimnessDay;
	}

	/**
	 * @param isNeedPrimnessDay
	 *            The isNeedPrimnessDay to set.
	 */
	public void setIsNeedPrimnessDay(long isNeedPrimnessDay) {
		IsNeedPrimnessDay = isNeedPrimnessDay;
	}

	/**
	 * @return Returns the lossDay.
	 */
	public long getLossDay() {
		return LossDay;
	}

	/**
	 * @param lossDay
	 *            The lossDay to set.
	 */
	public void setLossDay(long lossDay) {
		LossDay = lossDay;
	}

	/**
	 * @return Returns the primnessDay.
	 */
	public long getPrimnessDay() {
		return PrimnessDay;
	}

	/**
	 * @param primnessDay
	 *            The primnessDay to set.
	 */
	public void setPrimnessDay(long primnessDay) {
		PrimnessDay = primnessDay;
	}

	/**
	 * @return Returns the billConsignReceiveInfos.
	 */
	public BillConsignReceiveInfo[] getBillConsignReceiveInfos() {
		return BillConsignReceiveInfos;
	}

	/**
	 * @param billConsignReceiveInfos
	 *            The billConsignReceiveInfos to set.
	 */
	public void setBillConsignReceiveInfos(BillConsignReceiveInfo[] billConsignReceiveInfos) {
		BillConsignReceiveInfos = billConsignReceiveInfos;
	}

	/**
	 * @return Returns the billRemindInfos.
	 */
	public BillRemindInfo[] getBillRemindInfos() {
		return BillRemindInfos;
	}

	/**
	 * @param billRemindInfos
	 *            The billRemindInfos to set.
	 */
	public void setBillRemindInfos(BillRemindInfo[] billRemindInfos) {
		BillRemindInfos = billRemindInfos;
	}

	/**
	 * @return Returns the lossInfos.
	 */
	public LossInfo[] getLossInfos() {
		return LossInfos;
	}

	/**
	 * @param lossInfos
	 *            The lossInfos to set.
	 */
	public void setLossInfos(LossInfo[] lossInfos) {
		LossInfos = lossInfos;
	}

	/**
	 * @return Returns the primnessInfos.
	 */
	public PrimnessInfo[] getPrimnessInfos() {
		return PrimnessInfos;
	}

	/**
	 * @param primnessInfos
	 *            The primnessInfos to set.
	 */
	public void setPrimnessInfos(PrimnessInfo[] primnessInfos) {
		PrimnessInfos = primnessInfos;
	}

	/**
	 * @return Returns the isNeedBillConsignReceiveDay.
	 */
	public long getIsNeedBillConsignReceiveDay() {
		return IsNeedBillConsignReceiveDay;
	}

	/**
	 * @param isNeedBillConsignReceiveDay
	 *            The isNeedBillConsignReceiveDay to set.
	 */
	public void setIsNeedBillConsignReceiveDay(long isNeedBillConsignReceiveDay) {
		IsNeedBillConsignReceiveDay = isNeedBillConsignReceiveDay;
	}

	/**
	 * @return Returns the freezeDay.
	 */
	public long getFreezeDay() {
		return FreezeDay;
	}

	/**
	 * @param freezeDay
	 *            The freezeDay to set.
	 */
	public void setFreezeDay(long freezeDay) {
		FreezeDay = freezeDay;
	}

	/**
	 * @return Returns the freezeInfos.
	 */
	public FreezeInfo[] getFreezeInfos() {
		return FreezeInfos;
	}

	/**
	 * @param freezeInfos
	 *            The freezeInfos to set.
	 */
	public void setFreezeInfos(FreezeInfo[] freezeInfos) {
		FreezeInfos = freezeInfos;
	}

	/**
	 * @return Returns the isNeedFreezeDay.
	 */
	public long getIsNeedFreezeDay() {
		return IsNeedFreezeDay;
	}

	/**
	 * @param isNeedFreezeDay
	 *            The isNeedFreezeDay to set.
	 */
	public void setIsNeedFreezeDay(long isNeedFreezeDay) {
		IsNeedFreezeDay = isNeedFreezeDay;
	}

	public long getBankBalanceCount() {
		return BankBalanceCount;
	}

	public void setBankBalanceCount(long bankBalanceCount) {
		BankBalanceCount = bankBalanceCount;
	}

	public double getIsNeedBankBalance() {
		return IsNeedBankBalance;
	}

	public void setIsNeedBankBalance(double isNeedBankBalance) {
		IsNeedBankBalance = isNeedBankBalance;
	}

	public double getIsNeedRateOfBankAndSett() {
		return IsNeedRateOfBankAndSett;
	}

	public void setIsNeedRateOfBankAndSett(double isNeedRateOfBankAndSett) {
		IsNeedRateOfBankAndSett = isNeedRateOfBankAndSett;
	}

	public double getRateOfBankAndSett() {
		return RateOfBankAndSett;
	}

	public void setRateOfBankAndSett(double rateOfBankAndSett) {
		RateOfBankAndSett = rateOfBankAndSett;
	}

	public BankBalanceInfo[] getBankBalances() {
		return BankBalances;
	}

	public void setBankBalances(BankBalanceInfo[] bankBalances) {
		BankBalances = bankBalances;
	}

	/**
	 * @return 返回 arrl_BalanceLimited。 需要提醒的逾越余额底线的账号ID
	 */
	public ArrayList getArrl_BalanceLimited() {
		return arrl_BalanceLimited;
	}

	/**
	 * @param arrl_BalanceLimited
	 *            要设置的 arrl_BalanceLimited。 需要提醒的逾越余额底线的账号ID
	 */
	public void setArrl_BalanceLimited(ArrayList arrl_BalanceLimited) {
		this.arrl_BalanceLimited = arrl_BalanceLimited;
	}

	/**
	 * @return 返回 arrl_CreditLimited。 需要提醒的周期内逾越贷方累计发生额的账户ID
	 */
	public ArrayList getArrl_CreditLimited() {
		return arrl_CreditLimited;
	}

	/**
	 * @param arrl_CreditLimited
	 *            要设置的 arrl_CreditLimited。 需要提醒的周期内逾越贷方累计发生额的账户ID
	 */
	public void setArrl_CreditLimited(ArrayList arrl_CreditLimited) {
		this.arrl_CreditLimited = arrl_CreditLimited;
	}

	/**
	 * @return 返回 arrl_DebitLimited。 需要提醒的周期内逾越借方累计发生额的账户ID
	 */
	public ArrayList getArrl_DebitLimited() {
		return arrl_DebitLimited;
	}

	/**
	 * @param arrl_DebitLimited
	 *            要设置的 arrl_DebitLimited。 需要提醒的周期内逾越借方累计发生额的账户ID
	 */
	public void setArrl_DebitLimited(ArrayList arrl_DebitLimited) {
		this.arrl_DebitLimited = arrl_DebitLimited;
	}

	/**
	 * @return 返回 arrl_TranspayLimited。 需要提醒的逾过单笔支付发生额上限的账户ID
	 */
	public ArrayList getArrl_TranspayLimited() {
		return arrl_TranspayLimited;
	}

	/**
	 * @param arrl_TranspayLimited
	 *            要设置的 arrl_TranspayLimited。 需要提醒的逾过单笔支付发生额上限的账户ID
	 */
	public void setArrl_TranspayLimited(ArrayList arrl_TranspayLimited) {
		this.arrl_TranspayLimited = arrl_TranspayLimited;
	}

	public long getIsNeedShouRemind() {
		return IsNeedShouRemind;
	}

	public void setIsNeedShouRemind(long isNeedShouRemind) {
		IsNeedShouRemind = isNeedShouRemind;
	}

	public QueryPayformOverdue[] getOverdue() {
		return overdue;
	}

	public void setOverdue(QueryPayformOverdue[] infos) {
		overdue = infos;
		
	}

	public long getOFFBALANCEACCOUNT() {
		return OFFBALANCEACCOUNT;
	}

	public void setOFFBALANCEACCOUNT(long offbalanceaccount) {
		OFFBALANCEACCOUNT = offbalanceaccount;
	}

	public long getIsNeedOffBalanceFormRemind() {
		return IsNeedOffBalanceFormRemind;
	}

	public void setIsNeedOffBalanceFormRemind(long isNeedOffBalanceFormRemind) {
		IsNeedOffBalanceFormRemind = isNeedOffBalanceFormRemind;
	}

	
}

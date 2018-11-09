package com.iss.itreasury.settlement.transfinancialmargin.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class TransFinancialMarginInfo extends SettlementBaseDataEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9026433440546616197L;

	// 主信息
	private long		id									= -1;	// 唯一标识

	private long		nOfficeID							= -1;	// 办事处

	private long		nCurrencyID							= -1;	// 币种

	private String		sTransNo							= "";	// 交易号

	private long		typeID					            = -1;	// 交易类型(保证金处理类型 1:到期/提前撤销 2：回购 )

	private long		nClientID							= -1;	// 保证金客户ID

	private long		nAccountID							= -1;	// 保证金帐户ID

	private long		nContractID							= -1;	// 合同ID

	private long		nLoanNoticeID						= -1;	// 收款通知单ID

	private String		code						        = "";	// 收款通知单编号

	private long		nSubAccountID						= -1;	// 子账户ID

	private long		nCurrentAccountID					= -1;	// 付活期账户ID

	private long		nBankID								= -1;	// 开户银ID

	private long		nExtBankID							= -1;	// (外部账户)银行ID
	
	private String		sExtAcctNo							= "";	// 非财务公司账户号（收款方账户）
	
	private long        nExtAcctID                          = -1;   //非财务公司账户（收款方账户号ID）

	private String		sExtClientName						= "";	// 非财务公司客户名称

	private String		sRemitInBank						= "";	// 非财务公司银行名称

	private String		sRemitInProvince					= "";	// 省

	private String		sRemitInCity						= "";	// 市

	private double		amount								= 0.0;	// 保证金金额

	private Timestamp	dtInterestStartDate					= null; // 起息日

	private Timestamp	dtExecute							= null; // 执行日

	private double		minterest							= 0.0;	// 利息
	
	private Timestamp	dtModify							= null; // 修改时间：时分秒
	
	private Timestamp	dtInput							    = null; // 录入日期

	private long		nInputUserID						= -1;	// 录入人ID

	private long		nCheckUserID						= -1;	// 复核人ID

	private long		nAbstractID							= -1;	// 摘要ID

	private String		sAbstract							= "";	// 摘要
	
	private String		sCheckAbstract						= "";	// 复核/取消复核备注

	private long		nStatusID							= -1;	// 交易状态

	private long        transtypeID                         = -1;   //业务类型 （保证金保后处理）
	
	private Timestamp	dtCheck							    = null; // 复核日期

	

	public long getNOfficeID() {
		return nOfficeID;
	}

	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
		putUsedField("nOfficeID", nOfficeID);
	}

	public long getNCurrencyID() {
		return nCurrencyID;
	}

	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
		putUsedField("nCurrencyID", nCurrencyID);
	}

	public String getSTransNo() {
		return sTransNo;
	}

	public void setSTransNo(String transNo) {
		sTransNo = transNo;
		putUsedField("sTransNo", sTransNo);
	}

	

	public long getNClientID() {
		return nClientID;
	}

	public void setNClientID(long clientID) {
		nClientID = clientID;
		putUsedField("nClientID", nClientID);
	}

	public long getNAccountID() {
		return nAccountID;
	}

	public void setNAccountID(long accountID) {
		nAccountID = accountID;
		putUsedField("nAccountID", nAccountID);
	}

	public long getNContractID() {
		return nContractID;
	}

	public void setNContractID(long contractID) {
		nContractID = contractID;
		putUsedField("nContractID", nContractID);
	}

	public long getNLoanNoticeID() {
		return nLoanNoticeID;
	}

	public void setNLoanNoticeID(long loanNoticeID) {
		nLoanNoticeID = loanNoticeID;
		putUsedField("nLoanNoticeID", nLoanNoticeID);
	}


	public long getNSubAccountID() {
		return nSubAccountID;
	}

	public void setNSubAccountID(long subAccountID) {
		nSubAccountID = subAccountID;
		putUsedField("nSubAccountID", nSubAccountID);
	}

	public long getNCurrentAccountID() {
		return nCurrentAccountID;
	}

	public void setNCurrentAccountID(long currentAccountID) {
		nCurrentAccountID = currentAccountID;
		putUsedField("nCurrentAccountID", nCurrentAccountID);
	}

	public long getNBankID() {
		return nBankID;
	}

	public void setNBankID(long bankID) {
		nBankID = bankID;
		putUsedField("nBankID", nBankID);
	}

	public long getNExtBankID() {
		return nExtBankID;
	}

	public void setNExtBankID(long extBankID) {
		nExtBankID = extBankID;
		putUsedField("nExtBankID", nExtBankID);
	}

	public String getSExtAcctNo() {
		return sExtAcctNo;
	}

	public void setSExtAcctNo(String extAcctNo) {
		sExtAcctNo = extAcctNo;
		putUsedField("sExtAcctNo", sExtAcctNo);
	}

	public String getSExtClientName() {
		return sExtClientName;
	}

	public void setSExtClientName(String extClientName) {
		sExtClientName = extClientName;
		putUsedField("sExtClientName", sExtClientName);
	}

	public String getSRemitInBank() {
		return sRemitInBank;
	}

	public void setSRemitInBank(String remitInBank) {
		sRemitInBank = remitInBank;
		putUsedField("sRemitInBank", sRemitInBank);
	}

	public String getSRemitInProvince() {
		return sRemitInProvince;
	}

	public void setSRemitInProvince(String remitInProvince) {
		sRemitInProvince = remitInProvince;
		putUsedField("sRemitInProvince", sRemitInProvince);
	}

	public String getSRemitInCity() {
		return sRemitInCity;
	}

	public void setSRemitInCity(String remitInCity) {
		sRemitInCity = remitInCity;
		putUsedField("sRemitInCity", sRemitInCity);
	}


	public long getNInputUserID() {
		return nInputUserID;
	}

	public void setNInputUserID(long inputUserID) {
		nInputUserID = inputUserID;
		putUsedField("nInputUserID", nInputUserID);
	}

	public long getNCheckUserID() {
		return nCheckUserID;
	}

	public void setNCheckUserID(long checkUserID) {
		nCheckUserID = checkUserID;
		putUsedField("nCheckUserID", nCheckUserID);
	}

	public long getNAbstractID() {
		return nAbstractID;
	}

	public void setNAbstractID(long abstractID) {
		nAbstractID = abstractID;
		putUsedField("nAbstractID", nAbstractID);
	}

	public String getSAbstract() {
		return sAbstract;
	}

	public void setSAbstract(String abstract1) {
		sAbstract = abstract1;
		putUsedField("sAbstract", sAbstract);
	}

	public String getSCheckAbstract() {
		return sCheckAbstract;
	}

	public void setSCheckAbstract(String checkAbstract) {
		sCheckAbstract = checkAbstract;
		putUsedField("sCheckAbstract", sCheckAbstract);
	}

	public long getNStatusID() {
		return nStatusID;
	}

	public void setNStatusID(long statusID) {
		nStatusID = statusID;
		putUsedField("nStatusID", nStatusID);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	public long getTypeID() {
		return typeID;
	}

	public void setTypeID(long typeID) {
		this.typeID = typeID;
		putUsedField("typeID", typeID);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		putUsedField("code", code);
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField("amount", amount);
	}

	public Timestamp getDtInterestStartDate() {
		return dtInterestStartDate;
	}

	public void setDtInterestStartDate(Timestamp dtInterestStartDate) {
		this.dtInterestStartDate = dtInterestStartDate;
		//putUsedField("dtInterestStartDate", dtInterestStartDate);
		/*
		 * modify by yunchang
		 * date 2010-09-08 14:20
		 * function 国机数据库存储此日期字段为：dtInterestStart
		 */
		putUsedField("dtInterestStart", dtInterestStartDate);
	}

	public Timestamp getDtExecute() {
		return dtExecute;
	}

	public void setDtExecute(Timestamp dtExecute) {
		this.dtExecute = dtExecute;
		putUsedField("dtExecute", dtExecute);
	}

	public double getMinterest() {
		return minterest;
	}

	public void setMinterest(double minterest) {
		this.minterest = minterest;
		putUsedField("minterest", minterest);
	}

	public Timestamp getDtModify() {
		return dtModify;
	}

	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
		putUsedField("dtModify", dtModify);
	}

	public Timestamp getDtInput() {
		return dtInput;
	}

	public void setDtInput(Timestamp dtInput) {
		this.dtInput = dtInput;
		putUsedField("dtInput", dtInput);
	}

	public Timestamp getDtCheck() {
		return dtCheck;
	}

	public void setDtCheck(Timestamp dtCheck) {
		this.dtCheck = dtCheck;
		putUsedField("dtCheck", dtCheck);
	}
	
	public long getTranstypeID() {
		return transtypeID;
	}

	public void setTranstypeID(long transtypeID) {
		this.transtypeID = transtypeID;
		putUsedField("transtypeID", transtypeID);
	}

	public long getNExtAcctID() {
		return nExtAcctID;
	}

	public void setNExtAcctID(long extAcctID) {
		nExtAcctID = extAcctID;
		putUsedField("nExtAcctID", extAcctID);
	}

	

}

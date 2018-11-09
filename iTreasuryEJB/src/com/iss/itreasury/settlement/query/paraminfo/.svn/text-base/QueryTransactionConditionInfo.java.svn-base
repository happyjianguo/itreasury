/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryTransactionConditionInfo extends BaseDataEntity implements Serializable
{
	/**
	 * 查询类型
	 * 100，是从账户金额查询进入；
	 * 其它默认从交易记录查询进入。
	 */
	private long QueryType = -1;//查询类型：
	
	private long OfficeID = -1;//办事处
	private long CurrencyID = -1;//币种
	
	private long TransactionTypeID = -1;//业务类型
	private long AccountTransTypeID = -1;//账户交易类型
	
	private long PayClientIDStart = -1;//付款方客户ID（由）
	private String PayClientNoStart = "";//付款方客户编号（由）
	private long PayClientIDEnd = -1;//付款方客户ID（至）
	private String PayClientNoEnd = "";//付款方客户编号（至）
	private long PayAccountIDStart = -1;//付款方账户（由）
	private String PayAccountNoStart = "";//付款方账户号（由）
	private long PayAccountIDEnd = -1;//付款方账户（至）
	private String PayAccountNoEnd = "";//付款方账户号（至）
	private double PayAmountStart = 0.0;//付款方金额（由）
	private double PayAmountEnd = 0.0;//付款方金额（至）
	
	private long ReceiveClientIDStart = -1;//收款方客户ID（由）
	private String ReceiveClientNoStart = "";//收款方客户编号（由）
	private long ReceiveClientIDEnd = -1;//收款方客户ID（至）
	private String ReceiveClientNoEnd = "";//收款方客户编号（至）
	private long ReceiveAccountIDStart = -1;//收款方账户（由）
	private String ReceiveAccountNoStart = "";//收款方账户号（由）
	private long ReceiveAccountIDEnd = -1;//收款方账户（至）
	private String ReceiveAccountNoEnd = "";//收款方账户号（至）
	private double ReceiveAmountStart = 0.0;//收款方金额（由）
	private double ReceiveAmountEnd = 0.0;//收款方金额（至）
	
	private long BankID = -1;//开户行
	private String TransNoStart = "";//交易号（由）
	private String TransNoEnd = "";//交易号（至）
	private String DeclarationNO = "";//报单号
	private String BankChequeNO = "";//支票号
	private Timestamp InterestStartStart = null;//起息日（由）
	private Timestamp InterestStartEnd = null;//起息日（至）
	private Timestamp ExecuteStart = null;//执行日（由）
	private Timestamp ExecuteEnd = null;//执行日（至）
	private Timestamp SystemDate = null;//系统开机日
	private long StatusID = -1;//交易纪录状态
	private long InputuserID = -1;//录入人
	private long CheckuserID = -1;//复核人

	private long OrderID = -1;//排序类型ID
	private long DESC = -1;//升降序ID
	private long PageCount = -1;//每页纪录条数
	
	private String DepositNo = "";//存单号
	private long ContractID = -1;//合同ID
	private long PayFormID = -1;//放款通知单(贴现凭证)ID
	
	private String TransactionTypeIDs = "";//多业务类型拼接字符串
	
	private String Abstract = "";//摘要 addby zyyao 2007-6-7
	private long signer=-1;//过滤已授权电子签章客户的交易，如果为1则为过滤，否则不过滤
	
	private long NSTATUSID =-1;     ////差错交易状态lidi 20101125
	
	private String applyCode = "";//交易-申请指令编号
	private long source=-1;//交易-数据来源（1：柜台 2：网银 3：银行 4以上：SAP等外部系统）
	
	private long difoffice=-1;//是否显示通存通兑交易
	
	private long clientId = -1;  //当前成员单位id
	
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	//add by 2012-05-17 添加付款方指定编号
	private String payAppointAccountNo = "";
	//add by 2012-05-17 添加收款方指定编号
	private String receiveAppointAccountNo = "";
	//判断是否输入金额
	private String payMoneyStartBlank = "";
	private String payMoneyEndBlank = "";
	private String receiveMoneyStartBlank = "";
	private String receiveMoneyEndBlank = "";

	private long unit = 1;
	/**
	 * @return
	 */
	public long getAccountTransTypeID()
	{
		return AccountTransTypeID;
	}

	/**
	 * @return
	 */
	public long getBankID()
	{
		return BankID;
	}

	/**
	 * @return
	 */
	public long getCheckuserID()
	{
		return CheckuserID;
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
	public Timestamp getExecuteEnd()
	{
		return ExecuteEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteStart()
	{
		return ExecuteStart;
	}

	/**
	 * @return
	 */
	public long getInputuserID()
	{
		return InputuserID;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStartEnd()
	{
		return InterestStartEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getInterestStartStart()
	{
		return InterestStartStart;
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
	public long getPayAccountIDEnd()
	{
		return PayAccountIDEnd;
	}

	/**
	 * @return
	 */
	public long getPayAccountIDStart()
	{
		return PayAccountIDStart;
	}

	/**
	 * @return
	 */
	public String getPayAccountNoEnd()
	{
		return PayAccountNoEnd;
	}

	/**
	 * @return
	 */
	public String getPayAccountNoStart()
	{
		return PayAccountNoStart;
	}

	/**
	 * @return
	 */
	public double getPayAmountEnd()
	{
		return PayAmountEnd;
	}

	/**
	 * @return
	 */
	public double getPayAmountStart()
	{
		return PayAmountStart;
	}

	/**
	 * @return
	 */
	public String getPayClientNoEnd()
	{
		return PayClientNoEnd;
	}

	/**
	 * @return
	 */
	public long getPayClientIDEnd()
	{
		return PayClientIDEnd;
	}

	/**
	 * @return
	 */
	public long getPayClientIDStart()
	{
		return PayClientIDStart;
	}

	/**
	 * @return
	 */
	public String getPayClientNoStart()
	{
		return PayClientNoStart;
	}

	/**
	 * @return
	 */
	public long getReceiveAccountIDEnd()
	{
		return ReceiveAccountIDEnd;
	}

	/**
	 * @return
	 */
	public long getReceiveAccountIDStart()
	{
		return ReceiveAccountIDStart;
	}

	/**
	 * @return
	 */
	public String getReceiveAccountNoEnd()
	{
		return ReceiveAccountNoEnd;
	}

	/**
	 * @return
	 */
	public String getReceiveAccountNoStart()
	{
		return ReceiveAccountNoStart;
	}

	/**
	 * @return
	 */
	public double getReceiveAmountEnd()
	{
		return ReceiveAmountEnd;
	}

	/**
	 * @return
	 */
	public double getReceiveAmountStart()
	{
		return ReceiveAmountStart;
	}

	/**
	 * @return
	 */
	public long getReceiveClientIDEnd()
	{
		return ReceiveClientIDEnd;
	}

	/**
	 * @return
	 */
	public long getReceiveClientIDStart()
	{
		return ReceiveClientIDStart;
	}

	/**
	 * @return
	 */
	public String getReceiveClientNoEnd()
	{
		return ReceiveClientNoEnd;
	}

	/**
	 * @return
	 */
	public String getReceiveClientNoStart()
	{
		return ReceiveClientNoStart;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @return
	 */
	public long getTransactionTypeID()
	{
		return TransactionTypeID;
	}

	/**
	 * @return
	 */
	public String getTransNoEnd()
	{
		return TransNoEnd;
	}

	/**
	 * @return
	 */
	public String getTransNoStart()
	{
		return TransNoStart;
	}

	/**
	 * @param l
	 */
	public void setAccountTransTypeID(long l)
	{
		AccountTransTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
	}

	/**
	 * @param l
	 */
	public void setCheckuserID(long l)
	{
		CheckuserID = l;
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
	public void setExecuteEnd(Timestamp timestamp)
	{
		ExecuteEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteStart(Timestamp timestamp)
	{
		ExecuteStart = timestamp;
	}

	/**
	 * @param l
	 */
	public void setInputuserID(long l)
	{
		InputuserID = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStartEnd(Timestamp timestamp)
	{
		InterestStartEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setInterestStartStart(Timestamp timestamp)
	{
		InterestStartStart = timestamp;
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
	public void setPayAccountIDEnd(long l)
	{
		PayAccountIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setPayAccountIDStart(long l)
	{
		PayAccountIDStart = l;
	}

	/**
	 * @param string
	 */
	public void setPayAccountNoEnd(String string)
	{
		PayAccountNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setPayAccountNoStart(String string)
	{
		PayAccountNoStart = string;
	}

	/**
	 * @param d
	 */
	public void setPayAmountEnd(double d)
	{
		PayAmountEnd = d;
	}

	/**
	 * @param d
	 */
	public void setPayAmountStart(double d)
	{
		PayAmountStart = d;
	}

	/**
	 * @param string
	 */
	public void setPayClientNoEnd(String string)
	{
		PayClientNoEnd = string;
	}

	/**
	 * @param l
	 */
	public void setPayClientIDEnd(long l)
	{
		PayClientIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setPayClientIDStart(long l)
	{
		PayClientIDStart = l;
	}

	/**
	 * @param string
	 */
	public void setPayClientNoStart(String string)
	{
		PayClientNoStart = string;
	}

	/**
	 * @param l
	 */
	public void setReceiveAccountIDEnd(long l)
	{
		ReceiveAccountIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setReceiveAccountIDStart(long l)
	{
		ReceiveAccountIDStart = l;
	}

	/**
	 * @param string
	 */
	public void setReceiveAccountNoEnd(String string)
	{
		ReceiveAccountNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveAccountNoStart(String string)
	{
		ReceiveAccountNoStart = string;
	}

	/**
	 * @param d
	 */
	public void setReceiveAmountEnd(double d)
	{
		ReceiveAmountEnd = d;
	}

	/**
	 * @param d
	 */
	public void setReceiveAmountStart(double d)
	{
		ReceiveAmountStart = d;
	}

	/**
	 * @param l
	 */
	public void setReceiveClientIDEnd(long l)
	{
		ReceiveClientIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setReceiveClientIDStart(long l)
	{
		ReceiveClientIDStart = l;
	}

	/**
	 * @param string
	 */
	public void setReceiveClientNoEnd(String string)
	{
		ReceiveClientNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setReceiveClientNoStart(String string)
	{
		ReceiveClientNoStart = string;
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		StatusID = l;
	}

	/**
	 * @param l
	 */
	public void setTransactionTypeID(long l)
	{
		TransactionTypeID = l;
	}

	/**
	 * @param string
	 */
	public void setTransNoEnd(String string)
	{
		TransNoEnd = string;
	}

	/**
	 * @param string
	 */
	public void setTransNoStart(String string)
	{
		TransNoStart = string;
	}

	/**
	 * @return
	 */
	public long getDESC()
	{
		return DESC;
	}

	/**
	 * @return
	 */
	public long getOrderID()
	{
		return OrderID;
	}

	/**
	 * @return
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @param l
	 */
	public void setDESC(long l)
	{
		DESC = l;
	}

	/**
	 * @param l
	 */
	public void setOrderID(long l)
	{
		OrderID = l;
	}

	/**
	 * @param l
	 */
	public void setPageCount(long l)
	{
		PageCount = l;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @return
	 */
	public String getDepositNo()
	{
		return DepositNo;
	}

	/**
	 * @return
	 */
	public long getPayFormID()
	{
		return PayFormID;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		ContractID = l;
	}

	/**
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		DepositNo = string;
	}

	/**
	 * @param l
	 */
	public void setPayFormID(long l)
	{
		PayFormID = l;
	}

	/**
	 * @return
	 */
	public long getQueryType()
	{
		return QueryType;
	}

	/**
	 * @param l
	 */
	public void setQueryType(long l)
	{
		QueryType = l;
	}

	/**
	 * @return Returns the sTransactionTypeIDs.
	 */
	public String getTransactionTypeIDs() {
		return TransactionTypeIDs;
	}
	/**
	 * @param transactionTypeIDs The sTransactionTypeIDs to set.
	 */
	public void setTransactionTypeIDs(String transactionTypeIDs) {
		TransactionTypeIDs = transactionTypeIDs;
	}

	public String getAbstract() {
		return Abstract;
	}

	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}

	public long getSigner() {
		return signer;
	}

	public void setSigner(long signer) {
		this.signer = signer;
	}

	public long getNSTATUSID() {
		return NSTATUSID;
	}

	public void setNSTATUSID(long nstatusid) {
		NSTATUSID = nstatusid;
	}

	public String getDeclarationNO() {
		return DeclarationNO;
	}

	public void setDeclarationNO(String declarationNO) {
		DeclarationNO = declarationNO;
	}

	public String getBankChequeNO() {
		return BankChequeNO;
	}

	public void setBankChequeNO(String bankChequeNO) {
		BankChequeNO = bankChequeNO;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public long getSource() {
		return source;
	}

	public void setSource(long source) {
		this.source = source;
	}

	public Timestamp getSystemDate() {
		return SystemDate;
	}

	public void setSystemDate(Timestamp systemDate) {
		SystemDate = systemDate;
	}

	public long getDifoffice() {
		return difoffice;
	}

	public void setDifoffice(long difoffice) {
		this.difoffice = difoffice;
	}

	//add by 2012-05-17 添加付款方指定编号
	public String getPayAppointAccountNo() {
		return payAppointAccountNo;
	}

	public void setPayAppointAccountNo(String payAppointAccountNo) {
		this.payAppointAccountNo = payAppointAccountNo;
	}
	//add by 2012-05-17 添加收款方指定编号
	public String getReceiveAppointAccountNo() {
		return receiveAppointAccountNo;
	}

	public void setReceiveAppointAccountNo(String receiveAppointAccountNo) {
		this.receiveAppointAccountNo = receiveAppointAccountNo;
	}

	public String getPayMoneyStartBlank() {
		return payMoneyStartBlank;
	}

	public void setPayMoneyStartBlank(String payMoneyStartBlank) {
		this.payMoneyStartBlank = payMoneyStartBlank;
	}

	public String getPayMoneyEndBlank() {
		return payMoneyEndBlank;
	}

	public void setPayMoneyEndBlank(String payMoneyEndBlank) {
		this.payMoneyEndBlank = payMoneyEndBlank;
	}

	public String getReceiveMoneyStartBlank() {
		return receiveMoneyStartBlank;
	}

	public void setReceiveMoneyStartBlank(String receiveMoneyStartBlank) {
		this.receiveMoneyStartBlank = receiveMoneyStartBlank;
	}

	public String getReceiveMoneyEndBlank() {
		return receiveMoneyEndBlank;
	}

	public void setReceiveMoneyEndBlank(String receiveMoneyEndBlank) {
		this.receiveMoneyEndBlank = receiveMoneyEndBlank;
	}
	
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	public long getUnit() {
		return unit;
	}

	public void setUnit(long unit) {
		this.unit = unit;
	}
}

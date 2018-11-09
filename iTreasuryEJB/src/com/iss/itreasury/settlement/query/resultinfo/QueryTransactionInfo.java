/*
 * Created on 2003-11-7
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryTransactionInfo implements Serializable
{
	private long ID = -1; ////记录ID
	private long SerialNo = -1; ///记录序列号
	private long OfficeID = -1; ////交易办事处
	private long CurrencyID = -1; ////交易币种
	private String TransNo = ""; ////交易号
	private long TransactionTypeID = -1; ////交易类型
	private long Operationtypeid = -1;  //特殊交易类型
	private Timestamp InterestStart = null; ////交易起息日
	private Timestamp Execute = null; ////交易执行日
	private long StatusID = -1; ////交易状态
	private long InputUserID = -1; ////交易录入人
	private long CheckUserID = -1; ////交易复核人
	private String Abstract = ""; ////交易摘要
	private long PayClientID = -1; ////付款方客户
	private long PayAccountID = -1; ////付款方账户号ID
	private double PayAmount = 0.0; ////交易付款金额
	private double ReceiveAmount = 0.0; ////交易收款金额
	private long ReceiveClientID = -1; ////收款方客户
	private long ReceiveAccountID = -1; ////交易收款方账户ID
	private long BankID = -1; ////开户行ID
	private long ContractID = -1; ///合同号ID
	private long LoanFormID = -1; ///通知单号ID
	private String DepositNo = ""; ///存单号
	private String DeclarationNO = "";//报单号
	private String BankChequeNO = "";//支票号
	private String PayAccountNo = ""; ////付款方账户编码
	private String PayAccountName = ""; ////付款方账户名称
	private String ReceiveAccountNo = ""; ////收款方账户编码
	private String ReceiveAccountName = ""; ////收款方账户名称
	private String PayClientCode = ""; ////付款方客户编码
	private String PayClientName = ""; ////付款方客户名称
	private String ReceiveClientCode = ""; ////收款方客户编码
	private String ReceiveClientName = ""; ////收款方客户编码
	
	private String BankAccountCode = "";////银行账户编号
	private long NSTATUSID =-1;     ////差错交易状态
	
	private long Source = -1;//交易-数据来源（1：柜台 2：网银 3：银行 4以上：SAP等外部系统）
	private String ApplyCode = "";//交易-申请指令编号
	
	private String PayBakAccountNo = ""; ////付方备付金账户编码
	private String ReceiveBakAccountNo = ""; ////收方备付金账户编码
	
	public long getOperationtypeid() {
		return Operationtypeid;
	}
	public void setOperationtypeid(long operationtypeid) {
		Operationtypeid = operationtypeid;
	}
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
	public long getBankID()
	{
		return BankID;
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
	public long getContractID()
	{
		return ContractID;
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
	public String getDepositNo()
	{
		return DepositNo;
	}
	/**
	 * @return
	 */
	public Timestamp getExecute()
	{
		return Execute;
	}
	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}
	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}
	/**
	 * @return
	 */
	public Timestamp getInterestStart()
	{
		return InterestStart;
	}
	/**
	 * @return
	 */
	public long getLoanFormID()
	{
		return LoanFormID;
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
	public String getPayAccountName()
	{
		return PayAccountName;
	}
	/**
	 * @return
	 */
	public String getPayAccountNo()
	{
		return PayAccountNo;
	}
	/**
	 * @return
	 */
	public double getPayAmount()
	{
		return PayAmount;
	}
	/**
	 * @return
	 */
	public String getPayClientCode()
	{
		return PayClientCode;
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
	public String getPayClientName()
	{
		return PayClientName;
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
	public String getReceiveAccountName()
	{
		return ReceiveAccountName;
	}
	/**
	 * @return
	 */
	public String getReceiveAccountNo()
	{
		return ReceiveAccountNo;
	}
	/**
	 * @return
	 */
	public double getReceiveAmount()
	{
		return ReceiveAmount;
	}
	/**
	 * @return
	 */
	public String getReceiveClientCode()
	{
		return ReceiveClientCode;
	}
	/**
	 * @return
	 */
	public long getReceiveClientID()
	{
		return ReceiveClientID;
	}
	/**
	 * @return
	 */
	public String getReceiveClientName()
	{
		return ReceiveClientName;
	}
	/**
	 * @return
	 */
	public long getSerialNo()
	{
		return SerialNo;
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
	 * @param l
	 */
	public void setBankID(long l)
	{
		BankID = l;
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
	public void setContractID(long l)
	{
		ContractID = l;
	}
	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}
	/**
	 * @param string
	 */
	public void setDepositNo(String string)
	{
		DepositNo = string;
	}
	/**
	 * @param timestamp
	 */
	public void setExecute(Timestamp timestamp)
	{
		Execute = timestamp;
	}
	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}
	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		InputUserID = l;
	}
	/**
	 * @param timestamp
	 */
	public void setInterestStart(Timestamp timestamp)
	{
		InterestStart = timestamp;
	}
	/**
	 * @param l
	 */
	public void setLoanFormID(long l)
	{
		LoanFormID = l;
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
	 * @param string
	 */
	public void setPayAccountName(String string)
	{
		PayAccountName = string;
	}
	/**
	 * @param string
	 */
	public void setPayAccountNo(String string)
	{
		PayAccountNo = string;
	}
	/**
	 * @param d
	 */
	public void setPayAmount(double d)
	{
		PayAmount = d;
	}
	/**
	 * @param string
	 */
	public void setPayClientCode(String string)
	{
		PayClientCode = string;
	}
	/**
	 * @param l
	 */
	public void setPayClientID(long l)
	{
		PayClientID = l;
	}
	/**
	 * @param string
	 */
	public void setPayClientName(String string)
	{
		PayClientName = string;
	}
	/**
	 * @param l
	 */
	public void setReceiveAccountID(long l)
	{
		ReceiveAccountID = l;
	}
	/**
	 * @param string
	 */
	public void setReceiveAccountName(String string)
	{
		ReceiveAccountName = string;
	}
	/**
	 * @param string
	 */
	public void setReceiveAccountNo(String string)
	{
		ReceiveAccountNo = string;
	}
	/**
	 * @param d
	 */
	public void setReceiveAmount(double d)
	{
		ReceiveAmount = d;
	}
	/**
	 * @param string
	 */
	public void setReceiveClientCode(String string)
	{
		ReceiveClientCode = string;
	}
	/**
	 * @param l
	 */
	public void setReceiveClientID(long l)
	{
		ReceiveClientID = l;
	}
	/**
	 * @param string
	 */
	public void setReceiveClientName(String string)
	{
		ReceiveClientName = string;
	}
	/**
	 * @param l
	 */
	public void setSerialNo(long l)
	{
		SerialNo = l;
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
	public void setTransNo(String string)
	{
		TransNo = string;
	}
	/**
	 * @return Returns the BankAccountCode.
	 */
	public String getBankAccountCode()
	{
		return BankAccountCode;
	}
	/**
	 * @param bankAccountCode The bankAccountCode to set.
	 */
	public void setBankAccountCode(String bankAccountCode)
	{
		BankAccountCode=bankAccountCode;
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
	public long getSource() {
		return Source;
	}
	public void setSource(long source) {
		Source = source;
	}
	public String getApplyCode() {
		return ApplyCode;
	}
	public void setApplyCode(String applyCode) {
		ApplyCode = applyCode;
	}
	public String getPayBakAccountNo() {
		return PayBakAccountNo;
	}
	public void setPayBakAccountNo(String payBakAccountNo) {
		PayBakAccountNo = payBakAccountNo;
	}
	public String getReceiveBakAccountNo() {
		return ReceiveBakAccountNo;
	}
	public void setReceiveBakAccountNo(String receiveBakAccountNo) {
		ReceiveBakAccountNo = receiveBakAccountNo;
	}
	
	
}

/*
 * Created on 2003-9-8
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.obinstruction.dataentity;
/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.sql.Timestamp;
public class OBFinanceInfo implements java.io.Serializable
{
	/** Creates new FinaceInfo */
	public OBFinanceInfo()
	{
	}
	private long ID = -1; // 指令序号	
	private long TransType = -1; // 网上交易类型	 
	private long PayerAcctID = -1; // 付款方账户ID	
	private String PayClientName = ""; // 付款方客户名称
	private long PayeeAcctID = -1; // 收款方账户ID	
	private long RemitType = -1; // 汇款方式
	private double Amount = 0.0; // 交易金额
	private long DealUserID = -1; // CPF-处理人
	private double Interest = 0.0; // 应付贷款利息
	private Timestamp ExecuteDate = null; // 执行日  
	private String Note = ""; // 汇款用途/摘要
	private long Status = -1; // 指令状态
	private long DefaultTransType = -1; // CPF-默认业务类型
	private String TransNo = ""; // CPF-交易号
	private Timestamp CheckDate = null; //复核日期
	
	private long DepositBillStatusID = -1;	//换开定期存单状态ID
	
	private long inputUserID  = -1;  //录入人ID
	private Timestamp inputDate = null;  //录入日期
	private long checkUserID = -1;  //复核人ID
	private long signUserID = -1;   //签认人ID
	private Timestamp signSignDate = null;  //签认日期
	
    private long isSameBank  	= -1; //华联新加，是否同行
    private long isDiffLocal  	= -1; //华联新加，是否同城
    
    private Timestamp dtModify = null; //修改日期

    private long remitArea  	= -1; //汇款区域
    private long remitSpeed  	= -1; //汇款速度
    
    private long Source = -1;//数据来源（1：柜台 2：网银 3：银行 4以上：SAP等外部系统）
    private String ApplyCode = "";//申请指令编号
    
    private String bankName = "";  //汇入行全称
    
    private String payeeBankName = "";//会入行名称
    
	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	/**
	 * @return
	 */
	public double getAmount() {
		return Amount;
	}

	/**
	 * @return
	 */
	public long getDealUserID() {
		return DealUserID;
	}

	/**
	 * @return
	 */
	public long getDefaultTransType() {
		return DefaultTransType;
	}

	/**
	 * @return
	 */
	public Timestamp getExecuteDate() {
		return ExecuteDate;
	}

	/**
	 * @return
	 */
	public long getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public double getInterest() {
		return Interest;
	}

	/**
	 * @return
	 */
	public String getNote() {
		return Note;
	}

	/**
	 * @return
	 */
	public String getPayClientName() {
		return PayClientName;
	}

	/**
	 * @return
	 */
	public long getPayeeAcctID() {
		return PayeeAcctID;
	}

	/**
	 * @return
	 */
	public long getPayerAcctID() {
		return PayerAcctID;
	}

	/**
	 * @return
	 */
	public long getRemitType() {
		return RemitType;
	}

	/**
	 * @return
	 */
	public long getStatus() {
		return Status;
	}

	/**
	 * @return
	 */
	public long getDepositBillStatusID() {
		return DepositBillStatusID;
	}
	
	/**
	 * @return
	 */
	public String getTransNo() {
		return TransNo;
	}

	/**
	 * @return
	 */
	public long getTransType() {
		return TransType;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d) {
		Amount = d;
	}

	/**
	 * @param l
	 */
	public void setDealUserID(long l) {
		DealUserID = l;
	}

	/**
	 * @param l
	 */
	public void setDefaultTransType(long l) {
		DefaultTransType = l;
	}

	/**
	 * @param timestamp
	 */
	public void setExecuteDate(Timestamp timestamp) {
		ExecuteDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setID(long l) {
		ID = l;
	}

	/**
	 * @param d
	 */
	public void setInterest(double d) {
		Interest = d;
	}

	/**
	 * @param string
	 */
	public void setNote(String string) {
		Note = string;
	}

	/**
	 * @param string
	 */
	public void setPayClientName(String string) {
		PayClientName = string;
	}

	/**
	 * @param l
	 */
	public void setPayeeAcctID(long l) {
		PayeeAcctID = l;
	}

	/**
	 * @param l
	 */
	public void setPayerAcctID(long l) {
		PayerAcctID = l;
	}

	/**
	 * @param l
	 */
	public void setRemitType(long l) {
		RemitType = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		Status = l;
	}
	
	/**
	 * @param l
	 */
	public void setDepositBillStatusID(long l) {
		DepositBillStatusID = l;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string) {
		TransNo = string;
	}

	/**
	 * @param l
	 */
	public void setTransType(long l) {
		TransType = l;
	}

	public Timestamp getCheckDate() {
		return CheckDate;
	}

	public void setCheckDate(Timestamp checkDate) {
		CheckDate = checkDate;
	}

	public long getCheckUserID() {
		return checkUserID;
	}

	public void setCheckUserID(long checkUserID) {
		this.checkUserID = checkUserID;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public long getInputUserID() {
		return inputUserID;
	}

	public void setInputUserID(long inputUserID) {
		this.inputUserID = inputUserID;
	}
	public Timestamp getSignSignDate() {
		return signSignDate;
	}

	public void setSignSignDate(Timestamp signSignDate) {
		this.signSignDate = signSignDate;
	}

	public long getSignUserID() {
		return signUserID;
	}

	public void setSignUserID(long signUserID) {
		this.signUserID = signUserID;
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

	public Timestamp getDtModify() {
		return dtModify;
	}

	public void setDtModify(Timestamp dtModify) {
		this.dtModify = dtModify;
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

}

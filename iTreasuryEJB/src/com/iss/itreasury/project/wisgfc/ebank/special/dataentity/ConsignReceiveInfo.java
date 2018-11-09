package com.iss.itreasury.project.wisgfc.ebank.special.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author xlchang 2010-11-25
 *  委托收款实体
 */
public class ConsignReceiveInfo extends ITreasuryBaseDataEntity{
	
	private static final long serialVersionUID = -2678169538367413657L;
	
	private long id;
	private long NOfficeID;    //办事处id
	private long NCurrencyID;  //币种id
	private long NTransType;      //交易类型
	private long NPayerClientID;      //付款单位id
	private long NPayerAcctID;      //付款账号id
	private long NPayeeClientID;      //收款单位id
	private long NPayeeAcctID;      //收款账号id
	private double MAmount;         //金额
	private Timestamp DTExecute;     //执行日期
	private long NAbstractID;        //用途
	private String SContractCode;           //合同号
	private long NStatus;    //状态
	private long NInputUserID;   //录入人
	private Timestamp DTInput;    //录入时间
	private Timestamp DTModify;    //录入时间
	private long NConfirmUserID;   //确认人
	private Timestamp DTConfirm;    //确认时间
	private long NInstrID;          //ob_financeinstr表ID
	private String SMemo;           //备注
	
	private long q_NStatus = -1;   //查询条件-状态
	private String q_inputStart = "";//查询条件-提交日期 由
	private String q_inputEnd = "";  //查询条件-提交日期 至
	private long q_payeeClientIDStart = 0; //查询条件-收款方 由
	private long q_payeeClientIDEnd = 0; //查询条件-收款方 至
	private String q_payeeClientCodeStart = ""; //查询条件-收款方 由
	private String q_payeeClientCodeEnd = ""; //查询条件-收款方 至
	
	private String strStatus; //以逗号分隔的状态字符串 如"1,2"
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	public long getNOfficeID() {
		return NOfficeID;
	}
	public void setNOfficeID(long officeID) {
		NOfficeID = officeID;
		putUsedField("NOfficeID",NOfficeID);
	}
	public long getNCurrencyID() {
		return NCurrencyID;
	}
	public void setNCurrencyID(long currencyID) {
		NCurrencyID = currencyID;
		putUsedField("NCurrencyID",NCurrencyID);
	}	
	public long getNTransType() {
		return NTransType;
	}
	public void setNTransType(long transType) {
		NTransType = transType;
		putUsedField("NTransType",NTransType);
	}
	public long getNPayerClientID() {
		return NPayerClientID;
	}
	public void setNPayerClientID(long payerClientID) {
		NPayerClientID = payerClientID;
		putUsedField("NPayerClientID",NPayerClientID);
	}
	public long getNPayerAcctID() {
		return NPayerAcctID;
	}
	public void setNPayerAcctID(long payerAcctID) {
		NPayerAcctID = payerAcctID;
		putUsedField("NPayerAcctID",NPayerAcctID);
	}
	public long getNPayeeClientID() {
		return NPayeeClientID;
	}
	public void setNPayeeClientID(long payeeClientID) {
		NPayeeClientID = payeeClientID;
		putUsedField("NPayeeClientID",NPayeeClientID);
	}
	public long getNPayeeAcctID() {
		return NPayeeAcctID;
	}
	public void setNPayeeAcctID(long payeeAcctID) {
		NPayeeAcctID = payeeAcctID;
		putUsedField("NPayeeAcctID",NPayeeAcctID);
	}	
	public double getMAmount() {
		return MAmount;
	}
	public void setMAmount(double amount) {
		MAmount = amount;
		putUsedField("MAmount",MAmount);
	}
	public Timestamp getDTExecute() {
		return DTExecute;
	}
	public void setDTExecute(Timestamp execute) {
		DTExecute = execute;
		putUsedField("DTExecute",DTExecute);
	}	
	public long getNAbstractID() {
		return NAbstractID;
	}
	public void setNAbstractID(long abstractID) {
		NAbstractID = abstractID;
		putUsedField("NAbstractID",NAbstractID);
	}
	public String getSContractCode() {
		return SContractCode;
	}
	public void setSContractCode(String contractCode) {
		SContractCode = contractCode;
		putUsedField("SContractCode",SContractCode);
	}
	public long getNStatus() {
		return NStatus;
	}
	public void setNStatus(long status) {
		NStatus = status;
		putUsedField("NStatus",NStatus);
	}
	public long getNInputUserID() {
		return NInputUserID;
	}
	public void setNInputUserID(long inputUserID) {
		NInputUserID = inputUserID;
		putUsedField("NInputUserID",NInputUserID);
	}
	public Timestamp getDTInput() {
		return DTInput;
	}
	public void setDTInput(Timestamp input) {
		DTInput = input;
		putUsedField("DTInput",DTInput);
	}	
	public Timestamp getDTModify() {
		return DTModify;
	}
	public void setDTModify(Timestamp modify) {
		DTModify = modify;
		putUsedField("DTModify",DTModify);
	}
	public long getNConfirmUserID() {
		return NConfirmUserID;
	}
	public void setNConfirmUserID(long confirmUserID) {
		NConfirmUserID = confirmUserID;
		putUsedField("NConfirmUserID",NConfirmUserID);
	}
	public Timestamp getDTConfirm() {
		return DTConfirm;
	}
	public void setDTConfirm(Timestamp confirm) {
		DTConfirm = confirm;
		putUsedField("DTConfirm",DTConfirm);
	}
	public long getNInstrID() {
		return NInstrID;
	}
	public void setNInstrID(long instrID) {
		NInstrID = instrID;
		putUsedField("NInstrID",NInstrID);
	}
	public long getQ_NStatus() {
		return q_NStatus;
	}
	public void setQ_NStatus(long status) {
		q_NStatus = status;
		putUsedField("NStatus",q_NStatus);
	}
	public String getSMemo() {
		return SMemo;
	}
	public void setSMemo(String memo) {
		SMemo = memo;
		putUsedField("SMemo",SMemo);
	}
	public String getStrStatus() {
		return strStatus;
	}
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}	
	public String getQ_inputStart() {
		return q_inputStart;
	}
	public void setQ_inputStart(String start) {
		q_inputStart = start;
	}
	public String getQ_inputEnd() {
		return q_inputEnd;
	}
	public void setQ_inputEnd(String end) {
		q_inputEnd = end;
	}	
	public long getQ_payeeClientIDStart() {
		return q_payeeClientIDStart;
	}
	public void setQ_payeeClientIDStart(long clientIDStart) {
		q_payeeClientIDStart = clientIDStart;
	}
	public long getQ_payeeClientIDEnd() {
		return q_payeeClientIDEnd;
	}
	public void setQ_payeeClientIDEnd(long clientIDEnd) {
		q_payeeClientIDEnd = clientIDEnd;
	}
	public String getQ_payeeClientCodeStart() {
		return q_payeeClientCodeStart;
	}
	public void setQ_payeeClientCodeStart(String clientCodeStart) {
		q_payeeClientCodeStart = clientCodeStart;
	}
	public String getQ_payeeClientCodeEnd() {
		return q_payeeClientCodeEnd;
	}
	public void setQ_payeeClientCodeEnd(String clientCodeEnd) {
		q_payeeClientCodeEnd = clientCodeEnd;
	}	
	
	
}

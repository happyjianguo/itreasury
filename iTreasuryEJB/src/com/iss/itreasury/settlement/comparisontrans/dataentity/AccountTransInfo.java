/*
 * Created on 2005-6-15
 */
package com.iss.itreasury.settlement.comparisontrans.dataentity;

import java.util.Date;


/**
 * ������Ϣ
 * @author mxzhou
 */
public class AccountTransInfo
{
	private long id	                        = -1;//���	Id
	private long accountId	                = -1;//�����˻�ID	Id
	private long currencyType	        = -1;//����	Type
	private double amount	                = Double.NaN;//���׽��	Amount
	private long direction	                = -1;//�������	Type
	private String oppAccountNO	        = null;//�Է��˻���	Code
	private String oppAccountName	        = null;//�Է��˻�����	String
	private String oppCorpName	        = null;//�Է���λ����	String
	private long oppBankId	                = -1;//�Է�������������	ID
	private String oppBranchName	        = null;//�Է�����������	String
	private String oppBranchSwiftCode	= null;//�Է�������SWIFT����	Code
	private long oppCountryId	        = -1;//�Է��������ڹ�	ID
	private String oppAddress	        = null;//�Է��������ڵ�	
	private String checkNO	                = null;//ƾ֤��	String
	private String checkType	        = null;//ƾ֤����	String
	private Date transactionTime	        = null;//���׷�������	DateTime
	private Date valueDate	                = null;//��Ϣ��	Date
	private String transactionType	        = null;//��������	String
	private String abstractInfo	        = null;//ժҪ��Ϣ	String
	private String useInfo	                = null;//��;��Ϣ	LongString
	private String remarkInfo	        = null;//��ע��Ϣ	LongString
	private long bankId	                = -1;//��������	Type
	private String interBranchName	        = null;//��ת���е�����	String
	private String interBranchSwiftCode	= null;//��ת��SWIFT����	Code
	private String transNoOfBank	        = null;//���׵�����Ψһ��ʶ	Code
	private String transNoOfUpAcct	        = null;//��Ӧ���˻��Ľ��׺�	Code
	private long instructionId	        = -1;//��Ӧ����ת��ָ��ı�ʶ	ID
	private long isPrintVoucher	        = -1;//�Ƿ��Ѵ�ӡƾ֤	Type
	private long isDeletedByBank	        = -1;//�����Ƿ���ɾ��	Type
	private Date modifyTime	                = null;//�޸�ʱ��	DateTime
	
	private long isToTurnIn				= -1;//�Ƿ���Ҫ����
	private long turnInResult			= -1;//���˽��
	private String turnInRemind			= null;//���˽������
	private Date turnIn					= null;//��������
	private String transactionNo		= null;//��Ӧ�ⲿϵͳ�Ľ��׺�
	
	private long rowNum = -1;//excel�����(�ڵ���ʱʹ��)

	/**
	 * @return the abstractInfo
	 */
	public String getAbstractInfo() {
		return abstractInfo;
	}

	/**
	 * @param abstractInfo the abstractInfo to set
	 */
	public void setAbstractInfo(String abstractInfo) {
		this.abstractInfo = abstractInfo;
	}

	/**
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the bankId
	 */
	public long getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the checkNO
	 */
	public String getCheckNO() {
		return checkNO;
	}

	/**
	 * @param checkNO the checkNO to set
	 */
	public void setCheckNO(String checkNO) {
		this.checkNO = checkNO;
	}

	/**
	 * @return the checkType
	 */
	public String getCheckType() {
		return checkType;
	}

	/**
	 * @param checkType the checkType to set
	 */
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	/**
	 * @return the currencyType
	 */
	public long getCurrencyType() {
		return currencyType;
	}

	/**
	 * @param currencyType the currencyType to set
	 */
	public void setCurrencyType(long currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * @return the direction
	 */
	public long getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(long direction) {
		this.direction = direction;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the instructionId
	 */
	public long getInstructionId() {
		return instructionId;
	}

	/**
	 * @param instructionId the instructionId to set
	 */
	public void setInstructionId(long instructionId) {
		this.instructionId = instructionId;
	}

	/**
	 * @return the interBranchName
	 */
	public String getInterBranchName() {
		return interBranchName;
	}

	/**
	 * @param interBranchName the interBranchName to set
	 */
	public void setInterBranchName(String interBranchName) {
		this.interBranchName = interBranchName;
	}

	/**
	 * @return the interBranchSwiftCode
	 */
	public String getInterBranchSwiftCode() {
		return interBranchSwiftCode;
	}

	/**
	 * @param interBranchSwiftCode the interBranchSwiftCode to set
	 */
	public void setInterBranchSwiftCode(String interBranchSwiftCode) {
		this.interBranchSwiftCode = interBranchSwiftCode;
	}

	/**
	 * @return the isDeletedByBank
	 */
	public long getIsDeletedByBank() {
		return isDeletedByBank;
	}

	/**
	 * @param isDeletedByBank the isDeletedByBank to set
	 */
	public void setIsDeletedByBank(long isDeletedByBank) {
		this.isDeletedByBank = isDeletedByBank;
	}

	/**
	 * @return the isPrintVoucher
	 */
	public long getIsPrintVoucher() {
		return isPrintVoucher;
	}

	/**
	 * @param isPrintVoucher the isPrintVoucher to set
	 */
	public void setIsPrintVoucher(long isPrintVoucher) {
		this.isPrintVoucher = isPrintVoucher;
	}

	/**
	 * @return the isToTurnIn
	 */
	public long getIsToTurnIn() {
		return isToTurnIn;
	}

	/**
	 * @param isToTurnIn the isToTurnIn to set
	 */
	public void setIsToTurnIn(long isToTurnIn) {
		this.isToTurnIn = isToTurnIn;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the oppAccountName
	 */
	public String getOppAccountName() {
		return oppAccountName;
	}

	/**
	 * @param oppAccountName the oppAccountName to set
	 */
	public void setOppAccountName(String oppAccountName) {
		this.oppAccountName = oppAccountName;
	}

	/**
	 * @return the oppAccountNO
	 */
	public String getOppAccountNO() {
		return oppAccountNO;
	}

	/**
	 * @param oppAccountNO the oppAccountNO to set
	 */
	public void setOppAccountNO(String oppAccountNO) {
		this.oppAccountNO = oppAccountNO;
	}

	/**
	 * @return the oppAddress
	 */
	public String getOppAddress() {
		return oppAddress;
	}

	/**
	 * @param oppAddress the oppAddress to set
	 */
	public void setOppAddress(String oppAddress) {
		this.oppAddress = oppAddress;
	}

	/**
	 * @return the oppBankId
	 */
	public long getOppBankId() {
		return oppBankId;
	}

	/**
	 * @param oppBankId the oppBankId to set
	 */
	public void setOppBankId(long oppBankId) {
		this.oppBankId = oppBankId;
	}

	/**
	 * @return the oppBranchName
	 */
	public String getOppBranchName() {
		return oppBranchName;
	}

	/**
	 * @param oppBranchName the oppBranchName to set
	 */
	public void setOppBranchName(String oppBranchName) {
		this.oppBranchName = oppBranchName;
	}

	/**
	 * @return the oppBranchSwiftCode
	 */
	public String getOppBranchSwiftCode() {
		return oppBranchSwiftCode;
	}

	/**
	 * @param oppBranchSwiftCode the oppBranchSwiftCode to set
	 */
	public void setOppBranchSwiftCode(String oppBranchSwiftCode) {
		this.oppBranchSwiftCode = oppBranchSwiftCode;
	}

	/**
	 * @return the oppCorpName
	 */
	public String getOppCorpName() {
		return oppCorpName;
	}

	/**
	 * @param oppCorpName the oppCorpName to set
	 */
	public void setOppCorpName(String oppCorpName) {
		this.oppCorpName = oppCorpName;
	}

	/**
	 * @return the oppCountryId
	 */
	public long getOppCountryId() {
		return oppCountryId;
	}

	/**
	 * @param oppCountryId the oppCountryId to set
	 */
	public void setOppCountryId(long oppCountryId) {
		this.oppCountryId = oppCountryId;
	}

	/**
	 * @return the remarkInfo
	 */
	public String getRemarkInfo() {
		return remarkInfo;
	}

	/**
	 * @param remarkInfo the remarkInfo to set
	 */
	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}

	/**
	 * @return the rowNum
	 */
	public long getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}

	/**
	 * @return the transactionNo
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * @param transactionNo the transactionNo to set
	 */
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	/**
	 * @return the transactionTime
	 */
	public Date getTransactionTime() {
		return transactionTime;
	}

	/**
	 * @param transactionTime the transactionTime to set
	 */
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the transNoOfBank
	 */
	public String getTransNoOfBank() {
		return transNoOfBank;
	}

	/**
	 * @param transNoOfBank the transNoOfBank to set
	 */
	public void setTransNoOfBank(String transNoOfBank) {
		this.transNoOfBank = transNoOfBank;
	}

	/**
	 * @return the transNoOfUpAcct
	 */
	public String getTransNoOfUpAcct() {
		return transNoOfUpAcct;
	}

	/**
	 * @param transNoOfUpAcct the transNoOfUpAcct to set
	 */
	public void setTransNoOfUpAcct(String transNoOfUpAcct) {
		this.transNoOfUpAcct = transNoOfUpAcct;
	}

	/**
	 * @return the turnIn
	 */
	public Date getTurnIn() {
		return turnIn;
	}

	/**
	 * @param turnIn the turnIn to set
	 */
	public void setTurnIn(Date turnIn) {
		this.turnIn = turnIn;
	}

	/**
	 * @return the turnInRemind
	 */
	public String getTurnInRemind() {
		return turnInRemind;
	}

	/**
	 * @param turnInRemind the turnInRemind to set
	 */
	public void setTurnInRemind(String turnInRemind) {
		this.turnInRemind = turnInRemind;
	}

	/**
	 * @return the turnInResult
	 */
	public long getTurnInResult() {
		return turnInResult;
	}

	/**
	 * @param turnInResult the turnInResult to set
	 */
	public void setTurnInResult(long turnInResult) {
		this.turnInResult = turnInResult;
	}

	/**
	 * @return the useInfo
	 */
	public String getUseInfo() {
		return useInfo;
	}

	/**
	 * @param useInfo the useInfo to set
	 */
	public void setUseInfo(String useInfo) {
		this.useInfo = useInfo;
	}

	/**
	 * @return the valueDate
	 */
	public Date getValueDate() {
		return valueDate;
	}

	/**
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

}

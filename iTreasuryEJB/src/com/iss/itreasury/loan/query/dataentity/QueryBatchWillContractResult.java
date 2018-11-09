package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

public class QueryBatchWillContractResult {
	private long id;					//主键
	private long lContractType;			//贷款类型
	private long nSubTypeId;			//贷款子类型id(存入主表使用)
	private long ltid;					//贷款子类型id
	private String typeName;			//贷款子类型名称
	private long idc;					//合同id
	private String contractCode;		//合同编号
	private double contractMoney;		//合同金额
	private double loanBalance=0.0;			//贷款余额
	private long idp;					//放款通知单id
	private String notificationCode;	//放款通知单编号
	private double lendMoney = 0.0;				//放款金额
	private double balance = 0.0;				//放款余额
	private long term;					//期限
	private Timestamp timeFrom;			//开始日期
	private Timestamp timeTo;			//结束日期
	private double checkBaseRate;		//基准利率
	private double excuteRate;			//执行利率
	private long contractStatus;		//合同状态
	private String responOfficer;		//业务负责人
	public InutParameterInfo inutParameterInfo = null;
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public long getLContractType() {
		return lContractType;
	}
	public void setLContractType(long contractType) {
		lContractType = contractType;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public double getLoanBalance() {
		return loanBalance;
	}
	public void setLoanBalance(double loanBalance) {
		this.loanBalance = loanBalance;
	}
	public String getNotificationCode() {
		return notificationCode;
	}
	public void setNotificationCode(String notificationCode) {
		this.notificationCode = notificationCode;
	}
	public double getLendMoney() {
		return lendMoney;
	}
	public void setLendMoney(double lendMoney) {
		this.lendMoney = lendMoney;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public long getTerm() {
		return term;
	}
	public void setTerm(long term) {
		this.term = term;
	}
	public Timestamp getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(Timestamp timeFrom) {
		this.timeFrom = timeFrom;
	}
	public Timestamp getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(Timestamp timeTo) {
		this.timeTo = timeTo;
	}
	public double getCheckBaseRate() {
		return checkBaseRate;
	}
	public void setCheckBaseRate(double checkBaseRate) {
		this.checkBaseRate = checkBaseRate;
	}
	public double getExcuteRate() {
		return excuteRate;
	}
	public void setExcuteRate(double excuteRate) {
		this.excuteRate = excuteRate;
	}
	public long getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(long contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getResponOfficer() {
		return responOfficer;
	}
	public void setResponOfficer(String responOfficer) {
		this.responOfficer = responOfficer;
	}

	public long getIdc() {
		return idc;
	}
	public void setIdc(long idc) {
		this.idc = idc;
	}
	public long getIdp() {
		return idp;
	}
	public void setIdp(long idp) {
		this.idp = idp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public long getLtid() {
		return ltid;
	}
	public void setLtid(long ltid) {
		this.ltid = ltid;
	}
	public long getNSubTypeId() {
		return nSubTypeId;
	}
	public void setNSubTypeId(long subTypeId) {
		nSubTypeId = subTypeId;
	}
	public double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(double contractMoney) {
		this.contractMoney = contractMoney;
	}

}

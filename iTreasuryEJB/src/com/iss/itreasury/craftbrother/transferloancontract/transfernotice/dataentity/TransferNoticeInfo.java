package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity;

import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class TransferNoticeInfo extends ITreasuryBaseDataEntity
{
	private long id = -1;   //ID
	private long officeId = -1;  //办事处
	private long currencyId = -1;  //币种
	private String noticeCode = "";  //通知单编号
	private long noticeTypeId = -1;   //通知单类型
	private long contractId = -1;   //合同ID	
	private long isurrogatePay = -1;  //是否代理收款
	private long isdirectSett = -1;   //是否结算直接清算
	private Timestamp dtclearInterest = null;  //结息日期
	private long isdeductCharge = -1;  //是否扣收手续费
	private long commissionPaymentType = -1; //手续费收取方式
	private double mcommission = 0.00;  //手续费金额
	private long cracontractId = -1; //转让合同ID
	private String cracontractCode = "";  //转让合同编号
	private Timestamp lastClearInterest = null;  //上次结息日
	private double amount = 0.00;   //转让本金
	private double interest = 0.00;   //转让利息
	private double rate = 0.0000;     //转让利率
	private long bankId = -1;   //银行账户ID
	private String sremark = "";  //备注
	private long statusId = -1;  //状态
	private long inputuserId = -1;  //录入人
	private String inputuserName = "";  //录入人姓名
	private Timestamp inputDate = null;  //录入日期
	private InutParameterInfo inutParameterInfo = null;
	private Collection coll = null;
	private String bankNo = "";
	private long transtypeid=-1;//交易类型
	private long agentType = -1;//代收方式
	private long payType = -1;//付款方式
	
	//以下信息用于查询---------------------------------------------
	private double  commissionRate = 0.0; //手续费率
	
	private Collection colNoticeDetailInfos = null;  //通知单明细信息
	
	public long getAgentType() {
		return agentType;
	}
	public void setAgentType(long agentType) {
		this.agentType = agentType;
		putUsedField ( "agentType" , agentType );
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public Collection getColl() {
		return coll;
	}
	public void setColl(Collection coll) {
		this.coll = coll;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public double getAmount() {
		return amount;		
	}
	public void setAmount(double amount) {
		this.amount = amount;
		putUsedField ( "amount" , amount );
	}
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
		putUsedField ( "bankId" , bankId );
	}
	public long getCracontractId() {
		return cracontractId;
	}
	public void setCracontractId(long cracontractId) {
		this.cracontractId = cracontractId;
		putUsedField ( "cracontractId" , cracontractId );
	}
	public Timestamp getDtclearInterest() {
		return dtclearInterest;
	}
	public void setDtclearInterest(Timestamp dtclearInterest) {
		this.dtclearInterest = dtclearInterest;
		putUsedField ( "dtclearInterest" , dtclearInterest );
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField ( "id" , id );
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
		putUsedField ( "interest" , interest );
	}
	public long getIsdeductCharge() {
		return isdeductCharge;
	}
	public void setIsdeductCharge(long isdeductCharge) {
		this.isdeductCharge = isdeductCharge;
		putUsedField ( "isdeductCharge" , isdeductCharge );
	}
	public long getIsdirectSett() {
		return isdirectSett;
	}
	public void setIsdirectSett(long isdirectSett) {
		this.isdirectSett = isdirectSett;
		putUsedField ( "isdirectSett" , isdirectSett );
	}
	public long getIsurrogatePay() {
		return isurrogatePay;
	}
	public void setIsurrogatePay(long isurrogatePay) {
		this.isurrogatePay = isurrogatePay;
		putUsedField ( "isurrogatePay" , isurrogatePay );
	}
	public Timestamp getLastClearInterest() {
		return lastClearInterest;
	}
	public void setLastClearInterest(Timestamp lastClearInterest) {
		this.lastClearInterest = lastClearInterest;
		putUsedField ( "lastClearInterest" , lastClearInterest );
	}
	public double getMcommission() {
		return mcommission;
	}
	public void setMcommission(double mcommission) {
		this.mcommission = mcommission;
		putUsedField ( "mcommission" , mcommission );
	}
	public long getNoticeTypeId() {
		return noticeTypeId;
	}
	public void setNoticeTypeId(long noticeTypeId) {
		this.noticeTypeId = noticeTypeId;
		putUsedField ( "noticeTypeId" , noticeTypeId );
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
		putUsedField ( "rate" , rate );
	}
	public String getSremark() {
		return sremark;
	}
	public void setSremark(String sremark) {
		this.sremark = sremark;
		putUsedField ( "sremark" , sremark );
	}
	public long getContractId() {
		return contractId;
	}
	public void setContractId(long contractId) {
		this.contractId = contractId;
		putUsedField ( "contractId" , contractId );
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField ( "currencyId" , currencyId );
	}
	public Timestamp getInputDate() {
		return inputDate;
	}
	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
		putUsedField ( "inputDate" , inputDate );
	}
	public long getInputuserId() {
		return inputuserId;
	}
	public void setInputuserId(long inputuserId) {
		this.inputuserId = inputuserId;
		putUsedField ( "inputuserId" , inputuserId );
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField ( "officeId" , officeId );
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField ( "statusId" , statusId );
	}
	public String getNoticeCode() {
		return noticeCode;
	}
	public void setNoticeCode(String noticeCode) {
		this.noticeCode = noticeCode;
		putUsedField ( "noticeCode" , noticeCode );
	}
	public String getCracontractCode() {
		return cracontractCode;
	}
	public void setCracontractCode(String cracontractCode) {
		this.cracontractCode = cracontractCode;
	}
	public String getInputuserName() {
		return inputuserName;
	}
	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
	}
	public long getCommissionPaymentType() {
		return commissionPaymentType;
	}
	public void setCommissionPaymentType(long commissionPaymentType) {
		this.commissionPaymentType = commissionPaymentType;
		putUsedField ( "commissionPaymentType" , commissionPaymentType );
	}
	public long getTranstypeid() {
		return transtypeid;
	}
	public void setTranstypeid(long transtypeid) {
		this.transtypeid = transtypeid;
		putUsedField ("transtypeid" , transtypeid );
	}
	
	public Collection getColNoticeDetailInfos() {
		return colNoticeDetailInfos;
	}
	
	public void setColNoticeDetailInfos(Collection colNoticeDetailInfos) {
		this.colNoticeDetailInfos = colNoticeDetailInfos;
	}
	public double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public long getPayType() {
		return payType;
	}
	public void setPayType(long payType) {
		this.payType = payType;
		putUsedField ( "payType" , payType );
	}
}

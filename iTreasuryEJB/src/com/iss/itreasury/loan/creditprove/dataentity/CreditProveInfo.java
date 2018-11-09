/*
 * Created on 2006-10-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.creditprove.dataentity;

import java.sql.Date;

/**
 * @author yyhe
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreditProveInfo implements java.io.Serializable{
	
	private long id = -1; 
	private long officeId = -1; //办事处id
	private String code = "";//内部流水号
	private long conferContractNo = -1;//授信合同号id
	private String contractno = "";  //授信合同号，查询出来后显示时候用
	private String applyClient = "";//申请单位编号
	private String ClientName = "";//申请单位名称
	private String certificateBank ="";//开证银行
	private String creditProveID = "";//信贷证明编号
	private double balance = 0; //余额
	private long bCurrencyID = -1; //余额币种类型
	private String beneficiaryName = ""; //受益人名称
	private String projectName = ""; //项目名称
	private double money = 0; //金额
	private long mCurrencyID = -1; //金额币种类型
	private double exchangeRate = 0; //与人民币汇率
	private double convertRMB = 0; //折合人民币
	private long applyMonth = -1; //申请期限
	private String startDate = ""; //起始日期
	private String endDate = ""; //到期日期
	private double charge = 0; //手续费
	private long statusID = -1; //状态
	private String applyPurpose = ""; //申请用途
	private String remark = ""; //备注
	
	//为修改的时候，进行额度控制使用
	private double originalCoverRMB = 0; //修改前的折合人人民币
	
    //为了查询而设的属性
	String fromStartDate ="";//起始日期由
	String endStartDate ="";//起始日期到
	
	String fromEndDate ="";//到期日期由
	String endEndDate ="";//到期日期到
	
	public long getApplyMonth() {
		return applyMonth;
	}
	public void setApplyMonth(long applyMonth) {
		this.applyMonth = applyMonth;
	}
	public String getApplyPurpose() {
		return applyPurpose;
	}
	public void setApplyPurpose(String applyPurpose) {
		this.applyPurpose = applyPurpose;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getCreditProveID() {
		return creditProveID;
	}
	public void setCreditProveID(String creditProveID) {
		this.creditProveID = creditProveID;
	}
	public double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getStatusID() {
		return statusID;
	}
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	public String getApplyClient() {
		return applyClient;
	}
	public void setApplyClient(String applyClient) {
		this.applyClient = applyClient;
	}
	public long getBCurrencyID() {
		return bCurrencyID;
	}
	public void setBCurrencyID(long currencyID) {
		bCurrencyID = currencyID;
	}
	public String getCertificateBank() {
		return certificateBank;
	}
	public void setCertificateBank(String certificateBank) {
		this.certificateBank = certificateBank;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getConferContractNo() {
		return conferContractNo;
	}
	public void setConferContractNo(long conferContractNo) {
		this.conferContractNo = conferContractNo;
	}
	public double getConvertRMB() {
		return convertRMB;
	}
	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}
	public long getMCurrencyID() {
		return mCurrencyID;
	}
	public void setMCurrencyID(long currencyID) {
		mCurrencyID = currencyID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndEndDate() {
		return endEndDate;
	}
	public void setEndEndDate(String endEndDate) {
		this.endEndDate = endEndDate;
	}
	public String getEndStartDate() {
		return endStartDate;
	}
	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}
	public String getFromEndDate() {
		return fromEndDate;
	}
	public void setFromEndDate(String fromEndDate) {
		this.fromEndDate = fromEndDate;
	}
	public String getFromStartDate() {
		return fromStartDate;
	}
	public void setFromStartDate(String fromStartDate) {
		this.fromStartDate = fromStartDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate.toString();
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate.toString();
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public String getContractno() {
		return contractno;
	}
	public void setContractno(String contractno) {
		this.contractno = contractno;
	}
	public String getClientName() {
		return ClientName;
	}
	public void setClientName(String clientName) {
		ClientName = clientName;
	}
	public double getOriginalCoverRMB() {
		return originalCoverRMB;
	}
	public void setOriginalCoverRMB(double originalCoverRMB) {
		this.originalCoverRMB = originalCoverRMB;
	}
	
}

package com.iss.itreasury.loan.loanletterguarantee.dataentity;

import java.sql.Date;

public class LetterGuaranteeInfo implements java.io.Serializable{
	
	private long id = -1; 
	private long sOfficeCode = -1;
	private String sInnerCode = "";//内部流水号
	private long nBankCreditExtId = -1;//银行授信ID
	private String sApplyCompanyCode = "";//申请单位
	private String sAgreementNo ="";//合同,协议号
	private String sProjectName = "";//项目名称
	private String sLetterGuaranteeNo = ""; //保函编号
	private String sEBeneficiary = ""; //受益人英文名称
	private String sCBeneficiary = ""; //受益人中文名称
	private String sLinkMan = ""; //联系人
	private long nContractCurrencyType = -1; //合同币种
	private double mContractAmount = 0.0; //合同金额
	private long nCurrencyType = -1; //保函币种
	private double mAmount = 0.0; //保函金额
	private double mExchangeRate = 0.0; //汇率
	private String dStartDate = ""; //保函起始日期
	private String dEndDate = ""; //保函到期日期
	private String nVariety = ""; //保函种类
	private long nStatus = -1; //状态
	//private long nIsValid = -1; //是否有效
	private long sLastModifier = -1; //最后修改人
	private String dLastModifyDate = ""; //最后修改时间
	private long nIsImport = -1; //是否导入
	private String sRemark = ""; //备注
	
    //	为修改的时候，进行额度控制使用
	private double originalCoverRMB = 0; //修改前的折合人人民币
	
	//转换参数
	private String sBankname = ""; //开立银行
	private String scontractNo = ""; //授信合同号
	private String sName = ""; //保函种类名称
	private String clientName = "";   //单位名称
	
    //为了查询而设的属性
	String fromdStartDate ="";//起始日期由
	String enddStartDate ="";//起始日期到
	
	String fromdEndDate ="";//到期日期由
	String enddEndDate ="";//到期日期到	
	
	public String getDEndDate() {
		return dEndDate;
	}
	public void setDEndDate(String endDate) {
		dEndDate = endDate;
	}
	public String getDLastModifyDate() {
		return dLastModifyDate;
	}
	public void setDLastModifyDate(String lastModifyDate) {
		dLastModifyDate = lastModifyDate;
	}
	public String getDStartDate() {
		return dStartDate;
	}
	public void setDStartDate(String startDate) {
		dStartDate = startDate;
	}
	public void setDStartDate(Date startDate) {
		dStartDate = startDate.toString();
	}
	public String getEnddEndDate() {
		return enddEndDate;
	}
	public void setEnddEndDate(String enddEndDate) {
		this.enddEndDate = enddEndDate;
	}
	public String getEnddStartDate() {
		return enddStartDate;
	}
	public void setEnddStartDate(String enddStartDate) {
		this.enddStartDate = enddStartDate;
	}
	public String getFromdEndDate() {
		return fromdEndDate;
	}
	public void setFromdEndDate(String fromdEndDate) {
		this.fromdEndDate = fromdEndDate;
	}
	public String getFromdStartDate() {
		return fromdStartDate;
	}
	public void setFromdStartDate(String fromdStartDate) {
		this.fromdStartDate = fromdStartDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getMAmount() {
		return mAmount;
	}
	public void setMAmount(double amount) {
		mAmount = amount;
	}
	public double getMContractAmount() {
		return mContractAmount;
	}
	public void setMContractAmount(double contractAmount) {
		mContractAmount = contractAmount;
	}
	public double getMExchangeRate() {
		return mExchangeRate;
	}
	public void setMExchangeRate(double exchangeRate) {
		mExchangeRate = exchangeRate;
	}
	public long getNBankCreditExtId() {
		return nBankCreditExtId;
	}
	public void setNBankCreditExtId(long bankCreditExtId) {
		nBankCreditExtId = bankCreditExtId;
	}
	public long getNContractCurrencyType() {
		return nContractCurrencyType;
	}
	public void setNContractCurrencyType(long contractCurrencyType) {
		nContractCurrencyType = contractCurrencyType;
	}
	public long getNCurrencyType() {
		return nCurrencyType;
	}
	public void setNCurrencyType(long currencyType) {
		nCurrencyType = currencyType;
	}
	public long getNIsImport() {
		return nIsImport;
	}
	public void setNIsImport(long isImport) {
		nIsImport = isImport;
	}

	public long getNStatus() {
		return nStatus;
	}
	public void setNStatus(long status) {
		nStatus = status;
	}
	public String getNVariety() {
		return nVariety;
	}
	public void setNVariety(String variety) {
		nVariety = variety;
	}
	
	public String getSAgreementNo() {
		return sAgreementNo;
	}
	public void setSAgreementNo(String agreementNo) {
		sAgreementNo = agreementNo;
	}
	public String getSApplyCompanyCode() {
		return sApplyCompanyCode;
	}
	public void setSApplyCompanyCode(String applyCompanyCode) {
		sApplyCompanyCode = applyCompanyCode;
	}
	public String getSCBeneficiary() {
		return sCBeneficiary;
	}
	public void setSCBeneficiary(String beneficiary) {
		sCBeneficiary = beneficiary;
	}
	public String getSEBeneficiary() {
		return sEBeneficiary;
	}
	public void setSEBeneficiary(String beneficiary) {
		sEBeneficiary = beneficiary;
	}
	public String getSInnerCode() {
		return sInnerCode;
	}
	public void setSInnerCode(String innerCode) {
		sInnerCode = innerCode;
	}
	public long getSLastModifier() {
		return sLastModifier;
	}
	public void setSLastModifier(long lastModifier) {
		sLastModifier = lastModifier;
	}
	public String getSLetterGuaranteeNo() {
		return sLetterGuaranteeNo;
	}
	public void setSLetterGuaranteeNo(String letterGuaranteeNo) {
		sLetterGuaranteeNo = letterGuaranteeNo;
	}
	public String getSLinkMan() {
		return sLinkMan;
	}
	public void setSLinkMan(String linkMan) {
		sLinkMan = linkMan;
	}
	public long getSOfficeCode() {
		return sOfficeCode;
	}
	public void setSOfficeCode(long officeCode) {
		sOfficeCode = officeCode;
	}
	public String getSProjectName() {
		return sProjectName;
	}
	public void setSProjectName(String projectName) {
		sProjectName = projectName;
	}
	public String getSRemark() {
		return sRemark;
	}
	public void setSRemark(String sRemark) {
		this.sRemark = sRemark;
	}
	public String getScontractNo() {
		return scontractNo;
	}
	public void setScontractNo(String scontractNo) {
		this.scontractNo = scontractNo;
	}
	public String getSBankname() {
		return sBankname;
	}
	public void setSBankname(String bankname) {
		sBankname = bankname;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String name) {
		sName = name;
	}
	public double getOriginalCoverRMB() {
		return originalCoverRMB;
	}
	public void setOriginalCoverRMB(double originalCoverRMB) {
		this.originalCoverRMB = originalCoverRMB;
	}
	

}

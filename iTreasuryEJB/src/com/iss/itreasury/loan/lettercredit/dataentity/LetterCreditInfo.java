package com.iss.itreasury.loan.lettercredit.dataentity;

import java.sql.Date;
import java.util.Collection;

public class LetterCreditInfo {
	private String innerCode;		//内部流水号
	private long officeId;
	private String applyCompanyCode;//开证申请单位编码
	private String applyCompanyName;//开证申请单位名称
	private String agreementNo;		//合同协议号
	private String projectName;		//项目名称
	private String letterCreditNo;	//信用证号
	private long bankCreditExtId;	//银行授信ID
	private String contractNo;		//授信合同号
	private String startDate;		//开证日期
	private String beneficiary;		//受益人
	private String bankName;		//开证银行
	private String enabledDate;		//效期
	private String shippingDate;	//装期
	private String negotationDate;	//议付日期
	private String disabledPlace;	//失效地
	private int currencyType;		//币种
	private double applyAmount;		//申请金额
	private double paymentAmount;	//实际支付金额
	private double exchangeRate;	//汇率
	private double convertRMB;		//折合人民币
	private int paymentMode;		//支付方式
	private int type;				//种类
	private int status;				//状态
	private double handlingCharge;	//手续费
	private int isValid;			//是否有效
	private String lastModifier;	//最后修改人
	private Date lastModifyDate;	//最后修改时间
	private String remark;			//备注
	private Collection payment;		//支付情况
	
	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public double getConvertRMB() {
		return convertRMB;
	}

	public void setConvertRMB(double convertRMB) {
		this.convertRMB = convertRMB;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}

	public String getApplyCompanyCode() {
		return this.applyCompanyCode;
	}
	
	public void setApplyCompanyCode(String applyCompanyCode) {
		this.applyCompanyCode = applyCompanyCode;
	}
	
	public String getAgreementNo() {
		return this.agreementNo;
	}
	
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	
	public String getLetterCreditNo() {
		return this.letterCreditNo;
	}
	
	public void setLetterCreditNo(String letterCreditNo) {
		this.letterCreditNo = letterCreditNo;
	}
	
	public long getBankCreditExtId() {
		return this.bankCreditExtId;
	}
	
	public void setBankCreditExtId(long bankCreditExtId) {
		this.bankCreditExtId = bankCreditExtId;
	}
	
	public String getStartDate() {
		return this.startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public int getPaymentMode() {
		return this.paymentMode;
	}
	
	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getInnerCode() {
		return this.innerCode;
	}
	
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	
	public String getApplyCompanyName() {
		return this.applyCompanyName;
	}
	
	public void setApplyCompanyName(String applyCompanyName) {
		this.applyCompanyName = applyCompanyName;
	}	
	
	public int getCurrencyType() {
		return this.currencyType;
	}
	
	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}
	
	public double getApplyAmount() {
		return this.applyAmount;
	}
	
	public void setApplyAmount(double applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBeneficiary() {
		return this.beneficiary;
	}
	
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	public String getEnabledDate() {
		return this.enabledDate;
	}
	
	public void setEnabledDate(String enabledDate) {
		this.enabledDate = enabledDate;
	}
	
	public String getShippingDate() {
		return this.shippingDate;
	}
	
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	
	public String getNegotationDate() {
		return this.negotationDate;
	}
	
	public void setNegotationDate(String negotationDate) {
		this.negotationDate = negotationDate;
	}
	
	public String getDisabledPlace() {
		return this.disabledPlace;
	}
	
	public void setDisabledPlace(String disabledPlace) {
		this.disabledPlace = disabledPlace;
	}
	
	public double getExchangeRate() {
		return this.exchangeRate;
	}
	
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public double getHandlingCharge() {
		return this.handlingCharge;
	}
	
	public void setHandlingCharge(double handlingCharge) {
		this.handlingCharge = handlingCharge;
	}
	
	public Collection getPayment() {
		return this.payment;
	}
	
	public void setPayment(Collection payment) {
		this.payment = payment;
	}
	
	public int getIsValid() {
		return this.isValid;
	}
	
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	
	public String getLastModifier() {
		return this.lastModifier;
	}
	
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	
	public Date getLastModifyDate() {
		return this.lastModifyDate;
	}
	
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
}

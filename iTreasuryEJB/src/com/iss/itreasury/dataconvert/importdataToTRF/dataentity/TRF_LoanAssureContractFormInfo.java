/*
 * Created on 2006-4-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TRF_LoanAssureContractFormInfo {
    //id
    private long id=-1;
    //合同编号
    private String sContractCode="";
    //币种
    private String currency="";
    //财务公司
    private String office="";
    //申请号
    private String sApplyCode="";
    //被担保单位
    private String assuredClientName="";
    //被担保单位客户编号
    private String assuredClientCode="";
    //担保金额
    private double mLoanAmount=0.0;
    //担保开始时间
    private Timestamp dtStartDate=null;
    //担保结束时间
    private Timestamp dtEndDate=null;
    //担保原因
    private String sLoanReason="";
    //担保用途
    private String sLoanPurpose="";
    //合同管理人
    private String contractManagerPerson="";
    //信用反担保金额
    private double creditContraryAmount=0.0;
    //保证金额
    private double assureAmount=0.0;
    //保证单位名称1
    private String assureClientName1="";
    //保证单位1客户编号
    private String assureClientCode1="";
    //保证单位名称2
    private String assureClientName2="";
    //保证单位2客户编号
    private String assureClientCode2="";
    //保证单位名称3
    private String assureClientName3="";
    //保证单位3客户编号
    private String assureClientCode3="";
    //质押金额
    private double impawAmount=0.0;
    //质押单位名称
    private String impawClientName="";
    //质押单位客户编号
    private String impawClientCode="";
    //抵押金额
    private double pledgeAmount=0.0;
    //抵押单位名称
    private String pledgeClientName="";
    //抵押单位客户编号
    private String pledgeClientCode="";
    //担保费率
    private double mChargeRate=0.0;
    //状态
    private String status="";
    //备注
    private String remark="";
    //风险状态
    private String riskLevel="";
    //按地区分类
    private String type1="";
    //按行业分类1
    private String type2="";
    //按行业分类2
    private String type3="";
    //担保费率收取方式
    private String chargeAssureType="";
    //受益人
    private String beneficiary="";
    //担保类型1
    private String assureType1="";
    //担保类型2
    private String assureType2="";
    //自付保证金额
    private double mSelfAmount=0.0;
    
    
    public double getAssureAmount() {
        return assureAmount;
    }
    public void setAssureAmount(double assureAmount) {
        this.assureAmount = assureAmount;
    }
    public String getAssuredClientCode() {
        return assuredClientCode;
    }
    public void setAssuredClientCode(String assuredClientCode) {
        this.assuredClientCode = assuredClientCode;
    }
    public String getAssureClientCode1() {
        return assureClientCode1;
    }
    public void setAssureClientCode1(String assureClientCode1) {
        this.assureClientCode1 = assureClientCode1;
    }
    public String getAssureClientCode2() {
        return assureClientCode2;
    }
    public void setAssureClientCode2(String assureClientCode2) {
        this.assureClientCode2 = assureClientCode2;
    }
    public String getAssureClientCode3() {
        return assureClientCode3;
    }
    public void setAssureClientCode3(String assureClientCode3) {
        this.assureClientCode3 = assureClientCode3;
    }
    public String getAssuredClientName() {
        return assuredClientName;
    }
    public void setAssuredClientName(String assuredClientName) {
        this.assuredClientName = assuredClientName;
    }
    public String getAssureClientName1() {
        return assureClientName1;
    }
    public void setAssureClientName1(String assureClientName1) {
        this.assureClientName1 = assureClientName1;
    }
    public String getAssureClientName2() {
        return assureClientName2;
    }
    public void setAssureClientName2(String assureClientName2) {
        this.assureClientName2 = assureClientName2;
    }
    public String getAssureClientName3() {
        return assureClientName3;
    }
    public void setAssureClientName3(String assureClientName3) {
        this.assureClientName3 = assureClientName3;
    }
    public String getAssureType1() {
        return assureType1;
    }
    public void setAssureType1(String assureType1) {
        this.assureType1 = assureType1;
    }
    public String getAssureType2() {
        return assureType2;
    }
    public void setAssureType2(String assureType2) {
        this.assureType2 = assureType2;
    }
    public String getBeneficiary() {
        return beneficiary;
    }
    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }
    public String getChargeAssureType() {
        return chargeAssureType;
    }
    public void setChargeAssureType(String chargeAssureType) {
        this.chargeAssureType = chargeAssureType;
    }
    public String getContractManagerPerson() {
        return contractManagerPerson;
    }
    public void setContractManagerPerson(String contractManagerPerson) {
        this.contractManagerPerson = contractManagerPerson;
    }
    public double getCreditContraryAmount() {
        return creditContraryAmount;
    }
    public void setCreditContraryAmount(double creditContraryAmount) {
        this.creditContraryAmount = creditContraryAmount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Timestamp getDtEndDate() {
        return dtEndDate;
    }
    public void setDtEndDate(Timestamp dtEndDate) {
        this.dtEndDate = dtEndDate;
    }
    public Timestamp getDtStartDate() {
        return dtStartDate;
    }
    public void setDtStartDate(Timestamp dtStartDate) {
        this.dtStartDate = dtStartDate;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getImpawAmount() {
        return impawAmount;
    }
    public void setImpawAmount(double impawAmount) {
        this.impawAmount = impawAmount;
    }
    public String getImpawClientCode() {
        return impawClientCode;
    }
    public void setImpawClientCode(String impawClientCode) {
        this.impawClientCode = impawClientCode;
    }
    public String getImpawClientName() {
        return impawClientName;
    }
    public void setImpawClientName(String impawClientName) {
        this.impawClientName = impawClientName;
    }
    public double getMChargeRate() {
        return mChargeRate;
    }
    public void setMChargeRate(double chargeRate) {
        mChargeRate = chargeRate;
    }
    public double getMLoanAmount() {
        return mLoanAmount;
    }
    public void setMLoanAmount(double loanAmount) {
        mLoanAmount = loanAmount;
    }
    public double getMSelfAmount() {
        return mSelfAmount;
    }
    public void setMSelfAmount(double selfAmount) {
        mSelfAmount = selfAmount;
    }
    public String getOffice() {
        return office;
    }
    public void setOffice(String office) {
        this.office = office;
    }
    public double getPledgeAmount() {
        return pledgeAmount;
    }
    public void setPledgeAmount(double pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }
    public String getPledgeClientCode() {
        return pledgeClientCode;
    }
    public void setPledgeClientCode(String pledgeClientCode) {
        this.pledgeClientCode = pledgeClientCode;
    }
    public String getPledgeClientName() {
        return pledgeClientName;
    }
    public void setPledgeClientName(String pledgeClientName) {
        this.pledgeClientName = pledgeClientName;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    public String getSApplyCode() {
        return sApplyCode;
    }
    public void setSApplyCode(String applyCode) {
        sApplyCode = applyCode;
    }
    public String getSContractCode() {
        return sContractCode;
    }
    public void setSContractCode(String contractCode) {
        sContractCode = contractCode;
    }
    public String getSLoanPurpose() {
        return sLoanPurpose;
    }
    public void setSLoanPurpose(String loanPurpose) {
        sLoanPurpose = loanPurpose;
    }
    public String getSLoanReason() {
        return sLoanReason;
    }
    public void setSLoanReason(String loanReason) {
        sLoanReason = loanReason;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getType1() {
        return type1;
    }
    public void setType1(String type1) {
        this.type1 = type1;
    }
    public String getType2() {
        return type2;
    }
    public void setType2(String type2) {
        this.type2 = type2;
    }
    public String getType3() {
        return type3;
    }
    public void setType3(String type3) {
        this.type3 = type3;
    }
}

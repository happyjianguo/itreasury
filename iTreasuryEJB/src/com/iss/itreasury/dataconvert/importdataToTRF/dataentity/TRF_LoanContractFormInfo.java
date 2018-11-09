/*
 * Created on 2005-01-04
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.dataconvert.importdataToTRF.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 */
public class TRF_LoanContractFormInfo {
    //id
    private long id=-1;
    //��ͬ���
    private String sContractCode="";
    //��������
    private String loanType="";
    //����������
    private String subLoanType="";
    //����
    private String currency="";
    //����˾
    private String office="";
    //�����
    private String sApplyCode="";
    //ί�д���ͻ�����
    private String consignClientName="";
    //ί�д���ͻ����
    private String consignClientCode="";
    //��λ����
    private String borrowClientName="";
    //��λ�ͻ����
    private String borrowClientCode="";
    //������
    private double mLoanAmount=0.0;
    //������ޣ��£�
    private long nIntervalNum=-1;
    //���ʼʱ��
    private Timestamp dtStartDate=null;
    //�������ʱ��
    private Timestamp dtEndDate=null;
    //���ԭ��
    private String sLoanReason="";
    //�����;
    private String sLoanPurpose="";
    //��ͬ������
    private String contractManagerPerson="";
    //���ô�����
    private double creditLoanAmount=0.0;
    //��֤���
    private double assureAmount=0.0;
    //��֤��λ����1
    private String assureClientName1="";
    //��֤��λ1�ͻ����
    private String assureClientCode1="";
    //��֤��λ����2
    private String assureClientName2="";
    //��֤��λ2�ͻ����
    private String assureClientCode2="";
    //��֤��λ����3
    private String assureClientName3="";
    //��֤��λ3�ͻ����
    private String assureClientCode3="";
    //��Ѻ���
    private double impawAmount=0.0;
    //��Ѻ��λ����
    private String impawClientName="";
    //��Ѻ��λ�ͻ����
    private String impawClientCode="";
    //��Ѻ��������
    private String sImpawName="";
    //��Ѻ��������
    private String sImpawQuality="";
    //��Ѻ��״��
    private String sImpawStatus="";
    //��Ѻ���
    private double pledgeAmount=0.0;
    //��Ѻ��λ����
    private String pledgeClientName="";
    //��Ѻ��λ�ͻ����
    private String pledgeClientCode="";
    //��Ѻ�Ʋ��ܼ�
    private double mPledgeAmount=0.0;
    //��Ѻ��
    private double mPledgeRate=0.0;
    //��Ѻ��״��
    private String pledgeGoodsCondition="";
    //�����ͬ����%
    private double loanContractInerestRate=0.0;
    //����ִ������%
    private double loanExecuteInterestRate=0.0;
    //״̬
    private String status="";
    //��������%
    private double mChargeRate=0.0;
    //����������ȡ��ʽ
    private String chargeAssureType="";
    //��λ������
    private String sClientInfo="";
    //����״̬
    private String riskLevel="";
    //����������
    private String type1="";
    //����ҵ����1
    private String type2="";
    //����ҵ����2
    private String type3="";
    //����ʱ��
    private Timestamp returnDate=null;
    //������
    private double returnAmount=0.0;
    //��ע
    private String remark="";
    
    public double getAssureAmount() {
        return assureAmount;
    }
    public void setAssureAmount(double assureAmount) {
        this.assureAmount = assureAmount;
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
    public String getBorrowClientCode() {
        return borrowClientCode;
    }
    public void setBorrowClientCode(String borrowClientCode) {
        this.borrowClientCode = borrowClientCode;
    }
    public String getBorrowClientName() {
        return borrowClientName;
    }
    public void setBorrowClientName(String borrowClientName) {
        this.borrowClientName = borrowClientName;
    }
    public String getChargeAssureType() {
        return chargeAssureType;
    }
    public void setChargeAssureType(String chargeAssureType) {
        this.chargeAssureType = chargeAssureType;
    }
    public String getConsignClientCode() {
        return consignClientCode;
    }
    public void setConsignClientCode(String consignClientCode) {
        this.consignClientCode = consignClientCode;
    }
    public String getConsignClientName() {
        return consignClientName;
    }
    public void setConsignClientName(String consignClientName) {
        this.consignClientName = consignClientName;
    }
    public String getContractManagerPerson() {
        return contractManagerPerson;
    }
    public void setContractManagerPerson(String contractManagerPerson) {
        this.contractManagerPerson = contractManagerPerson;
    }
    public double getCreditLoanAmount() {
        return creditLoanAmount;
    }
    public void setCreditLoanAmount(double creditLoanAmount) {
        this.creditLoanAmount = creditLoanAmount;
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
    public double getLoanContractInerestRate() {
        return loanContractInerestRate;
    }
    public void setLoanContractInerestRate(double loanContractInerestRate) {
        this.loanContractInerestRate = loanContractInerestRate;
    }
    public double getLoanExecuteInterestRate() {
        return loanExecuteInterestRate;
    }
    public void setLoanExecuteInterestRate(double loanExecuteInterestRate) {
        this.loanExecuteInterestRate = loanExecuteInterestRate;
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
    public double getMPledgeAmount() {
        return mPledgeAmount;
    }
    public void setMPledgeAmount(double pledgeAmount) {
        mPledgeAmount = pledgeAmount;
    }
    public long getNIntervalNum() {
        return nIntervalNum;
    }
    public void setNIntervalNum(long intervalNum) {
        nIntervalNum = intervalNum;
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
    public String getPledgeGoodsCondition() {
        return pledgeGoodsCondition;
    }
    public void setPledgeGoodsCondition(String pledgeGoodsCondition) {
        this.pledgeGoodsCondition = pledgeGoodsCondition;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public double getReturnAmount() {
        return returnAmount;
    }
    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }
    public Timestamp getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
    public String getSApplyCode() {
        return sApplyCode;
    }
    public void setSApplyCode(String applyCode) {
        sApplyCode = applyCode;
    }
    public String getSClientInfo() {
        return sClientInfo;
    }
    public void setSClientInfo(String clientInfo) {
        sClientInfo = clientInfo;
    }
    public String getSContractCode() {
        return sContractCode;
    }
    public void setSContractCode(String contractCode) {
        sContractCode = contractCode;
    }
    public String getSImpawName() {
        return sImpawName;
    }
    public void setSImpawName(String impawName) {
        sImpawName = impawName;
    }
    public String getSImpawQuality() {
        return sImpawQuality;
    }
    public void setSImpawQuality(String impawQuality) {
        sImpawQuality = impawQuality;
    }
    public String getSImpawStatus() {
        return sImpawStatus;
    }
    public void setSImpawStatus(String impawStatus) {
        sImpawStatus = impawStatus;
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
    public String getLoanType() {
        return loanType;
    }
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    public String getRiskLevel() {
        return riskLevel;
    }
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    public String getSubLoanType() {
        return subLoanType;
    }
    public void setSubLoanType(String subLoanType) {
        this.subLoanType = subLoanType;
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
    public double getMPledgeRate() {
        return mPledgeRate;
    }
    public void setMPledgeRate(double pledgeRate) {
        mPledgeRate = pledgeRate;
    }
}
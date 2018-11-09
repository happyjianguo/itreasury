/*
 * Created on 2006-4-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

import java.sql.Timestamp;

/**
 * @author yinghu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Loan_ContractFormInfo {
    
    private long id=-1;
    private long nLoanId=-1;
    private String sContractCode="";
    private long isExtend=-1;
    private Timestamp dtActive=null;
    private long nTypeId=-1;
    private long nCurrencyId=-1;
    private long nOfficeId=-1;
    private String sApplyCode="";
    private long nConsignClientId=-1;
    private long nBorrowClientId=-1;
    private double mLoanAmount=0.0;
    private String sLoanReason="";
    private String sLoanPurpose="";
    private String sOther="";
    private long nIsCircle=-1;
    private long nIsSaleBuy=-1;
    private long nIsTechnical=-1;
    private long nInputUserId=-1;
    private Timestamp dtInputDate=null;
    private long nIsCredit=-1;
    private long nIsAssure=-1;
    private long nIsImpawn=-1;
    private long nIsPledge=-1;
    private long nInterestTypeId=-1;
    private double mExamineAmount=0.0;
    private long nInterValnum=-1;
    private long nBankInterestId=-1;
    private long nStatusId=-1;
    private long nNextCheckUserId=-1;
    private double mChargeRate=0.0;
    private Timestamp dtStartDate=null;
    private Timestamp dtEndDate=null;
    private long isCanModify=-1;
    private long nChargeRateTypeId=-1;
    private String sClientInfo="";
    private double mSelfAmount=0.0;
    private long nRiskLevel=-1;
    private long nTypeId1=-1;
    private long nTypeId2=-1;
    private long nTypeId3=-1;
    private long nBankAcceptPo=-1;
    private long nBizAcceptPo=-1;
    private double mCheckAmount=0.0;
    private double mDiscountRate=0.0;
    private Timestamp dtDiscountDate=null;
    private double mInterestRate=0.0;
    private double mAdjustRate=0.0;
    private double lastAttornmentAmount=0.0;
    private long nNextCheckLevel=-1;
    private double mStaidAdjustRate=0.0;
    private long nInorout=-1;
    private long nDiscountTypeId=-1;
    private long nRepurchaseTypeId=-1;
    private double mExamineSelfAmount=0.0;
    private long discountClientId=-1;
    private double purchaserInterestRate=0.0;
    private long isLowLevel=-1;
    private String discountClientName="";
    private long isPurchaserInterest=-1;
    private double assureChargeRate=0.0;
    private long assureChargeTypeId=-1;
    private String beneficiary="";
    private long assureTypeId1=-1;
    private long assureTypeId2=-1;
    private long isRecognizance=-1;
    private String projectInfo="";
    private long isRepurchase=-1;
    private long sellClientId=-1;
    private double selfRate=0.0;
    private double sellContractAmount=0.0;
    private long nTypeId4=-1;
    private long nLiborRateId=-1;
    private long nSubTypeId=-1;
    private long nCreditCheckReportId=-1;
    private long nInterestCountTypeId=-1;
    private double mChargeAmount=0.0;
    private double mRecognizanceAmount=0.0;
    private double mMatureNominalAmount=0.0;

    public double getAssureChargeRate() {
        return assureChargeRate;
    }
    public void setAssureChargeRate(double assureChargeRate) {
        this.assureChargeRate = assureChargeRate;
    }
    public long getAssureChargeTypeId() {
        return assureChargeTypeId;
    }
    public void setAssureChargeTypeId(long assureChargeTypeId) {
        this.assureChargeTypeId = assureChargeTypeId;
    }
    public long getAssureTypeId1() {
        return assureTypeId1;
    }
    public void setAssureTypeId1(long assureTypeId1) {
        this.assureTypeId1 = assureTypeId1;
    }
    public long getAssureTypeId2() {
        return assureTypeId2;
    }
    public void setAssureTypeId2(long assureTypeId2) {
        this.assureTypeId2 = assureTypeId2;
    }
    public String getBeneficiary() {
        return beneficiary;
    }
    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }
    public long getDiscountClientId() {
        return discountClientId;
    }
    public void setDiscountClientId(long discountClientId) {
        this.discountClientId = discountClientId;
    }
    public String getDiscountClientName() {
        return discountClientName;
    }
    public void setDiscountClientName(String discountClientName) {
        this.discountClientName = discountClientName;
    }
    public Timestamp getDtActive() {
        return dtActive;
    }
    public void setDtActive(Timestamp dtActive) {
        this.dtActive = dtActive;
    }
    public Timestamp getDtDiscountDate() {
        return dtDiscountDate;
    }
    public void setDtDiscountDate(Timestamp dtDiscountDate) {
        this.dtDiscountDate = dtDiscountDate;
    }
    public Timestamp getDtEndDate() {
        return dtEndDate;
    }
    public void setDtEndDate(Timestamp dtEndDate) {
        this.dtEndDate = dtEndDate;
    }
    public Timestamp getDtInputDate() {
        return dtInputDate;
    }
    public void setDtInputDate(Timestamp dtInputDate) {
        this.dtInputDate = dtInputDate;
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
    public long getIsCanModify() {
        return isCanModify;
    }
    public void setIsCanModify(long isCanModify) {
        this.isCanModify = isCanModify;
    }
    public long getIsExtend() {
        return isExtend;
    }
    public void setIsExtend(long isExtend) {
        this.isExtend = isExtend;
    }
    public long getIsLowLevel() {
        return isLowLevel;
    }
    public void setIsLowLevel(long isLowLevel) {
        this.isLowLevel = isLowLevel;
    }
    public long getIsPurchaserInterest() {
        return isPurchaserInterest;
    }
    public void setIsPurchaserInterest(long isPurchaserInterest) {
        this.isPurchaserInterest = isPurchaserInterest;
    }
    public long getIsRecognizance() {
        return isRecognizance;
    }
    public void setIsRecognizance(long isRecognizance) {
        this.isRecognizance = isRecognizance;
    }
    public long getIsRepurchase() {
        return isRepurchase;
    }
    public void setIsRepurchase(long isRepurchase) {
        this.isRepurchase = isRepurchase;
    }
    public double getLastAttornmentAmount() {
        return lastAttornmentAmount;
    }
    public void setLastAttornmentAmount(double lastAttornmentAmount) {
        this.lastAttornmentAmount = lastAttornmentAmount;
    }
    public double getMAdjustRate() {
        return mAdjustRate;
    }
    public void setMAdjustRate(double adjustRate) {
        mAdjustRate = adjustRate;
    }
    public double getMChargeAmount() {
        return mChargeAmount;
    }
    public void setMChargeAmount(double chargeAmount) {
        mChargeAmount = chargeAmount;
    }
    public double getMChargeRate() {
        return mChargeRate;
    }
    public void setMChargeRate(double chargeRate) {
        mChargeRate = chargeRate;
    }
    public double getMCheckAmount() {
        return mCheckAmount;
    }
    public void setMCheckAmount(double checkAmount) {
        mCheckAmount = checkAmount;
    }
    public double getMDiscountRate() {
        return mDiscountRate;
    }
    public void setMDiscountRate(double discountRate) {
        mDiscountRate = discountRate;
    }
    public double getMExamineAmount() {
        return mExamineAmount;
    }
    public void setMExamineAmount(double examineAmount) {
        mExamineAmount = examineAmount;
    }
    public double getMExamineSelfAmount() {
        return mExamineSelfAmount;
    }
    public void setMExamineSelfAmount(double examineSelfAmount) {
        mExamineSelfAmount = examineSelfAmount;
    }
    public double getMInterestRate() {
        return mInterestRate;
    }
    public void setMInterestRate(double interestRate) {
        mInterestRate = interestRate;
    }
    public double getMLoanAmount() {
        return mLoanAmount;
    }
    public void setMLoanAmount(double loanAmount) {
        mLoanAmount = loanAmount;
    }
    public double getMMatureNominalAmount() {
        return mMatureNominalAmount;
    }
    public void setMMatureNominalAmount(double matureNominalAmount) {
        mMatureNominalAmount = matureNominalAmount;
    }
    public double getMRecognizanceAmount() {
        return mRecognizanceAmount;
    }
    public void setMRecognizanceAmount(double recognizanceAmount) {
        mRecognizanceAmount = recognizanceAmount;
    }
    public double getMSelfAmount() {
        return mSelfAmount;
    }
    public void setMSelfAmount(double selfAmount) {
        mSelfAmount = selfAmount;
    }
    public double getMStaidAdjustRate() {
        return mStaidAdjustRate;
    }
    public void setMStaidAdjustRate(double staidAdjustRate) {
        mStaidAdjustRate = staidAdjustRate;
    }
    public long getNBankAcceptPo() {
        return nBankAcceptPo;
    }
    public void setNBankAcceptPo(long bankAcceptPo) {
        nBankAcceptPo = bankAcceptPo;
    }
    public long getNBankInterestId() {
        return nBankInterestId;
    }
    public void setNBankInterestId(long bankInterestId) {
        nBankInterestId = bankInterestId;
    }
    public long getNBizAcceptPo() {
        return nBizAcceptPo;
    }
    public void setNBizAcceptPo(long bizAcceptPo) {
        nBizAcceptPo = bizAcceptPo;
    }
    public long getNBorrowClientId() {
        return nBorrowClientId;
    }
    public void setNBorrowClientId(long borrowClientId) {
        nBorrowClientId = borrowClientId;
    }
    public long getNChargeRateTypeId() {
        return nChargeRateTypeId;
    }
    public void setNChargeRateTypeId(long chargeRateTypeId) {
        nChargeRateTypeId = chargeRateTypeId;
    }
    public long getNConsignClientId() {
        return nConsignClientId;
    }
    public void setNConsignClientId(long consignClientId) {
        nConsignClientId = consignClientId;
    }
    public long getNCreditCheckReportId() {
        return nCreditCheckReportId;
    }
    public void setNCreditCheckReportId(long creditCheckReportId) {
        nCreditCheckReportId = creditCheckReportId;
    }
    public long getNCurrencyId() {
        return nCurrencyId;
    }
    public void setNCurrencyId(long currencyId) {
        nCurrencyId = currencyId;
    }
    public long getNDiscountTypeId() {
        return nDiscountTypeId;
    }
    public void setNDiscountTypeId(long discountTypeId) {
        nDiscountTypeId = discountTypeId;
    }
    public long getNInorout() {
        return nInorout;
    }
    public void setNInorout(long inorout) {
        nInorout = inorout;
    }
    public long getNInputUserId() {
        return nInputUserId;
    }
    public void setNInputUserId(long inputUserId) {
        nInputUserId = inputUserId;
    }
    public long getNInterestCountTypeId() {
        return nInterestCountTypeId;
    }
    public void setNInterestCountTypeId(long interestCountTypeId) {
        nInterestCountTypeId = interestCountTypeId;
    }
    public long getNInterestTypeId() {
        return nInterestTypeId;
    }
    public void setNInterestTypeId(long interestTypeId) {
        nInterestTypeId = interestTypeId;
    }
    public long getNInterValnum() {
        return nInterValnum;
    }
    public void setNInterValnum(long interValnum) {
        nInterValnum = interValnum;
    }
    public long getNIsAssure() {
        return nIsAssure;
    }
    public void setNIsAssure(long isAssure) {
        nIsAssure = isAssure;
    }
    public long getNIsCircle() {
        return nIsCircle;
    }
    public void setNIsCircle(long isCircle) {
        nIsCircle = isCircle;
    }
    public long getNIsCredit() {
        return nIsCredit;
    }
    public void setNIsCredit(long isCredit) {
        nIsCredit = isCredit;
    }
    public long getNIsImpawn() {
        return nIsImpawn;
    }
    public void setNIsImpawn(long isImpawn) {
        nIsImpawn = isImpawn;
    }
    public long getNIsPledge() {
        return nIsPledge;
    }
    public void setNIsPledge(long isPledge) {
        nIsPledge = isPledge;
    }
    public long getNIsSaleBuy() {
        return nIsSaleBuy;
    }
    public void setNIsSaleBuy(long isSaleBuy) {
        nIsSaleBuy = isSaleBuy;
    }
    public long getNIsTechnical() {
        return nIsTechnical;
    }
    public void setNIsTechnical(long isTechnical) {
        nIsTechnical = isTechnical;
    }
    public long getNLiborRateId() {
        return nLiborRateId;
    }
    public void setNLiborRateId(long liborRateId) {
        nLiborRateId = liborRateId;
    }
    public long getNLoanId() {
        return nLoanId;
    }
    public void setNLoanId(long loanId) {
        nLoanId = loanId;
    }
    public long getNNextCheckLevel() {
        return nNextCheckLevel;
    }
    public void setNNextCheckLevel(long nextCheckLevel) {
        nNextCheckLevel = nextCheckLevel;
    }
    public long getNNextCheckUserId() {
        return nNextCheckUserId;
    }
    public void setNNextCheckUserId(long nextCheckUserId) {
        nNextCheckUserId = nextCheckUserId;
    }
    public long getNOfficeId() {
        return nOfficeId;
    }
    public void setNOfficeId(long officeId) {
        nOfficeId = officeId;
    }
    public long getNRepurchaseTypeId() {
        return nRepurchaseTypeId;
    }
    public void setNRepurchaseTypeId(long repurchaseTypeId) {
        nRepurchaseTypeId = repurchaseTypeId;
    }
    public long getNRiskLevel() {
        return nRiskLevel;
    }
    public void setNRiskLevel(long riskLevel) {
        nRiskLevel = riskLevel;
    }
    public long getNStatusId() {
        return nStatusId;
    }
    public void setNStatusId(long statusId) {
        nStatusId = statusId;
    }
    public long getNSubTypeId() {
        return nSubTypeId;
    }
    public void setNSubTypeId(long subTypeId) {
        nSubTypeId = subTypeId;
    }
    public long getNTypeId() {
        return nTypeId;
    }
    public void setNTypeId(long typeId) {
        nTypeId = typeId;
    }
    public long getNTypeId1() {
        return nTypeId1;
    }
    public void setNTypeId1(long typeId1) {
        nTypeId1 = typeId1;
    }
    public long getNTypeId2() {
        return nTypeId2;
    }
    public void setNTypeId2(long typeId2) {
        nTypeId2 = typeId2;
    }
    public long getNTypeId3() {
        return nTypeId3;
    }
    public void setNTypeId3(long typeId3) {
        nTypeId3 = typeId3;
    }
    public long getNTypeId4() {
        return nTypeId4;
    }
    public void setNTypeId4(long typeId4) {
        nTypeId4 = typeId4;
    }
    public String getProjectInfo() {
        return projectInfo;
    }
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }
    public double getPurchaserInterestRate() {
        return purchaserInterestRate;
    }
    public void setPurchaserInterestRate(double purchaserInterestRate) {
        this.purchaserInterestRate = purchaserInterestRate;
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
    public double getSelfRate() {
        return selfRate;
    }
    public void setSelfRate(double selfRate) {
        this.selfRate = selfRate;
    }
    public long getSellClientId() {
        return sellClientId;
    }
    public void setSellClientId(long sellClientId) {
        this.sellClientId = sellClientId;
    }
    public double getSellContractAmount() {
        return sellContractAmount;
    }
    public void setSellContractAmount(double sellContractAmount) {
        this.sellContractAmount = sellContractAmount;
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
    public String getSOther() {
        return sOther;
    }
    public void setSOther(String other) {
        sOther = other;
    }
}

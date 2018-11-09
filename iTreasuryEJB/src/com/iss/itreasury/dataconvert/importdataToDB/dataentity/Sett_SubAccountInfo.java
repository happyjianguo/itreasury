/*
 * Created on 2005-08-31
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.dataconvert.importdataToDB.dataentity;

import java.sql.Timestamp;

/**
 * @author kewen hu
 *
 */
public class Sett_SubAccountInfo{
	//ID	NOT NULL	NUMBER
	private long id = -1;
	//NACCOUNTID		NUMBER
	private long nAccountId = -1;
	//MINTEREST		NUMBER(21,6)
	private double mInterest = 0.0;
	//MBALANCE		NUMBER(21,6)
	private double mBalance = 0.0;
	//MOPENAMOUNT		NUMBER(21,6)
	private double mOpenAmount = 0.0;
	//DTOPEN		DATE
	private Timestamp dtOpen = null;
	//DTFINISH		DATE
	private Timestamp dtFinish = null;
	//NISINTEREST		NUMBER
	private long nIsInterest = -1;
	//DTCLEARINTEREST		DATE
	private Timestamp dtClearInterest = null;
	//NSTATUSID		NUMBER
	private long nStatusId = -1;
	//MUNCHECKPAYMENTAMOUNT		NUMBER(21,6)
	private double mUncheckPaymentAmount = 0.0;
	//AC_MMONTHLIMITAMOUNT		NUMBER(21,6)
	private double ac_MMonthLimitAmount = 0.0;
	//AC_NINTERESTACCOUNTID		NUMBER
	private long ac_NInterestAccountId = -1;
	//AC_NISOVERDRAFT		NUMBER
	private long ac_NIsOverDraft = -1;
	//AC_NFIRSTLIMITTYPEID		NUMBER
	private long ac_NFirstLimitTypeId = -1;
	//AC_MFIRSTLIMITAMOUNT		NUMBER(21,6)
	private double ac_MFirstLimitAmount = 0.0;
	//AC_NSECONDLIMITTYPEID		NUMBER
	private long ac_NSecondLimitTypeId = -1;
	//AC_MSECONDLIMITAMOUNT		NUMBER(21,6)
	private double ac_MSecondLimitAmount = 0.0;
	//AC_NTHIRDLIMITTYPEID		NUMBER
	private long ac_NThirdLimitTypeId = -1; 
	//AC_MTHIRDLIMITAMOUNT		NUMBER(21,6)
	private double ac_MThirdLimitAmount = 0.0;
	//AC_NINTERESTRATEPLANID		NUMBER
	private long ac_NInterestRatePlanId = -1;
	//AC_DTINTERESTRATEPLAN		DATE
	private Timestamp ac_DtInterestRatePlan = null;
	//AC_MCAPITALLIMITAMOUNT		NUMBER(21,6)
	private double ac_MCapitalLimitAmount = 0.0;
	//AC_NISNEGOTIATE		NUMBER
	private long ac_NIsNegotiate = -1;
	//AC_MNEGOTIATEAMOUNT		NUMBER(21,6)
	private double ac_MNegotiateAmount = 0.0;
	//AC_MNEGOTIATEUNIT		NUMBER(21,6)
	private double ac_MNegotiateUnit = 0.0;
	//AC_MNEGOTIATERATE		NUMBER(11,8)
	private double ac_MNegotiateRate = 0.0;
	//AC_DTNEGOTIATERATE		DATE
	private Timestamp ac_DtNegotiateRate = null;
	//AC_MNEGOTIATEINTEREST		NUMBER(21,6)
	private double ac_MNegotiateInterest = 0.0;
	//AC_SSEALNO		VARCHAR2(30)
	private String ac_SSealNo = "";
	//AC_NSEALBANKID		NUMBER
	private long ac_NSealBankId = -1;
	//AF_SDEPOSITNO		VARCHAR2(30)
	private String af_SDepositNo = "";
	//AF_MRATE		NUMBER(11,8)
	private double af_MRate = 0.0;
	//AF_DTSTART		DATE
	private Timestamp af_DtStart = null;
	//AF_DTEND		DATE
	private Timestamp af_DtEnd = null;
	//AF_MPREDRAWINTEREST		NUMBER(21,6)
	private double af_MPredrawInterest = 0.0;
	//AF_DTPREDRAW		DATE
	private Timestamp af_DtPredraw = null;
	//AF_NDEPOSITTERM		NUMBER
	private long af_NDepositTerm = -1;
	//AF_NINTERESTPLANID		NUMBER
	private long af_NInterestPlanId = -1;
	//AF_NNOTICEDAY		NUMBER
	private long af_NNoticeDay = -1;
	//AF_NINTERESTACCOUNTID		NUMBER
	private long af_NInterestAccountId = -1;
	//AF_SSEALNO		VARCHAR2(30)
	private String af_SSealNo = "";
	//AF_NSEALBANKID		NUMBER
	private long af_NSealBankId = -1;
	//AL_NLOANNOTEID		NUMBER
	private long al_NLoanNoteId = -1;
	//AL_NISCYCLOAN		NUMBER
	private long al_NIsCycLoan = -1;
	//AL_DTCALCULATEINTEREST		DATE
	private Timestamp al_DtCalculateInterest = null;
	//AL_NPAYINTERESTACCOUNTID		NUMBER
	private long al_NPayInterestAccountId = -1;
	//AL_NRECEIVEINTERESTACCOUNTID		NUMBER
	private long al_NReceiveInterestAccountId = -1;
	//AL_MPREDRAWINTEREST		NUMBER(21,6)
	private double al_MPredrawInterest = 0.0;
	//AL_DTPREDRAW		DATE
	private Timestamp al_DtPredraw = null;
	//AL_NPAYSURETYACCOUNTID		NUMBER
	private long al_NPaySuretyAccountId = -1;
	//AL_NRECEIVESURETYACCOUNTID		NUMBER
	private long al_NReceiveSuretyAccountId = -1;
	//AL_NCOMMISSIONACCOUNTID		NUMBER
	private long al_NCommissionAccountId = -1;
	//AL_MSURETYFEE		NUMBER(21,6)
	private double al_MSuretyFee = 0.0;
	//AL_DTCLEARSUREFEE		DATE
	private Timestamp al_DtClearSureFee = null;
	//AL_MCOMMISSION		NUMBER(21,6)
	private double al_MCommission = 0.0;
	//AL_DTCLEARCOMMISSION		DATE
	private Timestamp al_DtClearCommission = null;
	//AL_NINTERESTTAXACCOUNTID		NUMBER
	private long al_NInterestTaxAccountId = -1;
	//AL_MINTERESTTAX		NUMBER(21,6)
	private double al_MInterestTax = 0.0;
	//AL_MINTERESTTAXRATE		NUMBER(11,8)
	private double al_MInterestTaxRate = 0.0;
	//AL_DTCLEARINTERESTTAX		DATE
	private Timestamp al_DtClearInterestTax = null;
	//AL_DTEFFECTIVETAX		DATE
	private Timestamp al_DtEffectiveTax = null;
	//AL_NOVERDUEACCOUNTID		NUMBER
	private long al_NOverdueAccountId = -1;
	//AL_MOVERDUEINTEREST		NUMBER(21,6)
	private double al_MOverdueInterest = 0.0;
	//AL_DTCLEAROVERDUE		DATE
	private Timestamp al_DtClearOverdue = null;
	//AL_NCOMPOUNDACCOUNTID		NUMBER
	private long al_NCompoundAccountId = -1;
	//AL_MCOMPOUNDINTEREST		NUMBER(21,6)
	private double al_MCompoundInterest = 0.0;
	//AL_DTCLEARCOMPOUND		DATE
	private Timestamp al_DtClearCompound = null;
	//AL_MARREARAGEINTEREST		NUMBER(21,6)
	private double al_MArrearageInterest =0.0;
	//AL_NCONSIGNACCOUNTID		NUMBER
	private long al_NConsignAccountId = -1;
	//AC_NISALLBRANCH		NUMBER
	private long ac_NIsAllBranch = -1;
	//AL_MOVERDUEARREARAGEINTEREST		NUMBER(21,6)
	private double al_MOverdueArrearageInterest =0.0;
	//SSUBJECT		VARCHAR2(100)
	private String sSubject = "";
	//NINPUTUSERID		NUMBER
	private long nInputUserId = -1;
	//NCHECKUSERID		NUMBER
	private long nCheckUserId = -1;
	//AC_DTNEGOTIATIONSTARTDATE
	private Timestamp ac_DtNegotiationStartDate=null;
	//AC_DTNEGOTIATIONENDDATE date
	private Timestamp ac_DtNegotiationEndDate=null;
	
	
    public Timestamp getAc_DtNegotiationStartDate() {
        return ac_DtNegotiationStartDate;
    }
    public void setAc_DtNegotiationStartDate(Timestamp ac_DtNegotiationStartDate) {
        this.ac_DtNegotiationStartDate = ac_DtNegotiationStartDate;
    }
    public Timestamp getAc_DtNegotiationEndDate() {
        return ac_DtNegotiationEndDate;
    }
    public void setAc_DtNegotiationEndDate(Timestamp ac_DtNegotiationEndDate) {
        this.ac_DtNegotiationEndDate = ac_DtNegotiationEndDate;
    }
    public double getAc_MCapitalLimitAmount() {
        return ac_MCapitalLimitAmount;
    }
    public void setAc_MCapitalLimitAmount(double ac_MCapitalLimitAmount) {
        this.ac_MCapitalLimitAmount = ac_MCapitalLimitAmount;
    }
    public double getAc_MFirstLimitAmount() {
        return ac_MFirstLimitAmount;
    }
    public void setAc_MFirstLimitAmount(double ac_MFirstLimitAmount) {
        this.ac_MFirstLimitAmount = ac_MFirstLimitAmount;
    }
    public double getAc_MMonthLimitAmount() {
        return ac_MMonthLimitAmount;
    }
    public void setAc_MMonthLimitAmount(double ac_MMonthLimitAmount) {
        this.ac_MMonthLimitAmount = ac_MMonthLimitAmount;
    }
    public double getAc_MNegotiateAmount() {
        return ac_MNegotiateAmount;
    }
    public void setAc_MNegotiateAmount(double ac_MNegotiateAmount) {
        this.ac_MNegotiateAmount = ac_MNegotiateAmount;
    }
    public double getAc_MNegotiateInterest() {
        return ac_MNegotiateInterest;
    }
    public void setAc_MNegotiateInterest(double ac_MNegotiateInterest) {
        this.ac_MNegotiateInterest = ac_MNegotiateInterest;
    }
    public double getAc_MNegotiateRate() {
        return ac_MNegotiateRate;
    }
    public void setAc_MNegotiateRate(double ac_MNegotiateRate) {
        this.ac_MNegotiateRate = ac_MNegotiateRate;
    }
    public double getAc_MNegotiateUnit() {
        return ac_MNegotiateUnit;
    }
    public void setAc_MNegotiateUnit(double ac_MNegotiateUnit) {
        this.ac_MNegotiateUnit = ac_MNegotiateUnit;
    }
    public double getAc_MSecondLimitAmount() {
        return ac_MSecondLimitAmount;
    }
    public void setAc_MSecondLimitAmount(double ac_MSecondLimitAmount) {
        this.ac_MSecondLimitAmount = ac_MSecondLimitAmount;
    }
    public double getAc_MThirdLimitAmount() {
        return ac_MThirdLimitAmount;
    }
    public void setAc_MThirdLimitAmount(double ac_MThirdLimitAmount) {
        this.ac_MThirdLimitAmount = ac_MThirdLimitAmount;
    }
    public long getAc_NFirstLimitTypeId() {
        return ac_NFirstLimitTypeId;
    }
    public void setAc_NFirstLimitTypeId(long ac_NFirstLimitTypeId) {
        this.ac_NFirstLimitTypeId = ac_NFirstLimitTypeId;
    }
    public long getAc_NInterestAccountId() {
        return ac_NInterestAccountId;
    }
    public void setAc_NInterestAccountId(long ac_NInterestAccountId) {
        this.ac_NInterestAccountId = ac_NInterestAccountId;
    }
    public long getAc_NInterestRatePlanId() {
        return ac_NInterestRatePlanId;
    }
    public void setAc_NInterestRatePlanId(long ac_NInterestRatePlanId) {
        this.ac_NInterestRatePlanId = ac_NInterestRatePlanId;
    }
    public long getAc_NIsAllBranch() {
        return ac_NIsAllBranch;
    }
    public void setAc_NIsAllBranch(long ac_NIsAllBranch) {
        this.ac_NIsAllBranch = ac_NIsAllBranch;
    }
    public long getAc_NIsNegotiate() {
        return ac_NIsNegotiate;
    }
    public void setAc_NIsNegotiate(long ac_NIsNegotiate) {
        this.ac_NIsNegotiate = ac_NIsNegotiate;
    }
    public long getAc_NIsOverDraft() {
        return ac_NIsOverDraft;
    }
    public void setAc_NIsOverDraft(long ac_NIsOverDraft) {
        this.ac_NIsOverDraft = ac_NIsOverDraft;
    }
    public long getAc_NSealBankId() {
        return ac_NSealBankId;
    }
    public void setAc_NSealBankId(long ac_NSealBankId) {
        this.ac_NSealBankId = ac_NSealBankId;
    }
    public long getAc_NSecondLimitTypeId() {
        return ac_NSecondLimitTypeId;
    }
    public void setAc_NSecondLimitTypeId(long ac_NSecondLimitTypeId) {
        this.ac_NSecondLimitTypeId = ac_NSecondLimitTypeId;
    }
    public long getAc_NThirdLimitTypeId() {
        return ac_NThirdLimitTypeId;
    }
    public void setAc_NThirdLimitTypeId(long ac_NThirdLimitTypeId) {
        this.ac_NThirdLimitTypeId = ac_NThirdLimitTypeId;
    }
    public String getAc_SSealNo() {
        return ac_SSealNo;
    }
    public void setAc_SSealNo(String ac_SSealNo) {
        this.ac_SSealNo = ac_SSealNo;
    }
    public Timestamp getAf_DtEnd() {
        return af_DtEnd;
    }
    public void setAf_DtEnd(Timestamp af_DtEnd) {
        this.af_DtEnd = af_DtEnd;
    }
    public Timestamp getAf_DtPredraw() {
        return af_DtPredraw;
    }
    public void setAf_DtPredraw(Timestamp af_DtPredraw) {
        this.af_DtPredraw = af_DtPredraw;
    }
    public Timestamp getAf_DtStart() {
        return af_DtStart;
    }
    public void setAf_DtStart(Timestamp af_DtStart) {
        this.af_DtStart = af_DtStart;
    }
    public double getAf_MPredrawInterest() {
        return af_MPredrawInterest;
    }
    public void setAf_MPredrawInterest(double af_MPredrawInterest) {
        this.af_MPredrawInterest = af_MPredrawInterest;
    }
    public double getAf_MRate() {
        return af_MRate;
    }
    public void setAf_MRate(double af_MRate) {
        this.af_MRate = af_MRate;
    }
    public long getAf_NDepositTerm() {
        return af_NDepositTerm;
    }
    public void setAf_NDepositTerm(long af_NDepositTerm) {
        this.af_NDepositTerm = af_NDepositTerm;
    }
    public long getAf_NInterestAccountId() {
        return af_NInterestAccountId;
    }
    public void setAf_NInterestAccountId(long af_NInterestAccountId) {
        this.af_NInterestAccountId = af_NInterestAccountId;
    }
    public long getAf_NInterestPlanId() {
        return af_NInterestPlanId;
    }
    public void setAf_NInterestPlanId(long af_NInterestPlanId) {
        this.af_NInterestPlanId = af_NInterestPlanId;
    }
    public long getAf_NNoticeDay() {
        return af_NNoticeDay;
    }
    public void setAf_NNoticeDay(long af_NNoticeDay) {
        this.af_NNoticeDay = af_NNoticeDay;
    }
    public long getAf_NSealBankId() {
        return af_NSealBankId;
    }
    public void setAf_NSealBankId(long af_NSealBankId) {
        this.af_NSealBankId = af_NSealBankId;
    }
    public String getAf_SDepositNo() {
        return af_SDepositNo;
    }
    public void setAf_SDepositNo(String af_SDepositNo) {
        this.af_SDepositNo = af_SDepositNo;
    }
    public String getAf_SSealNo() {
        return af_SSealNo;
    }
    public void setAf_SSealNo(String af_SSealNo) {
        this.af_SSealNo = af_SSealNo;
    }
    public Timestamp getAl_DtCalculateInterest() {
        return al_DtCalculateInterest;
    }
    public void setAl_DtCalculateInterest(Timestamp al_DtCalculateInterest) {
        this.al_DtCalculateInterest = al_DtCalculateInterest;
    }
    public Timestamp getAl_DtClearCommission() {
        return al_DtClearCommission;
    }
    public void setAl_DtClearCommission(Timestamp al_DtClearCommission) {
        this.al_DtClearCommission = al_DtClearCommission;
    }
    public Timestamp getAl_DtClearCompound() {
        return al_DtClearCompound;
    }
    public void setAl_DtClearCompound(Timestamp al_DtClearCompound) {
        this.al_DtClearCompound = al_DtClearCompound;
    }
    public Timestamp getAl_DtClearInterestTax() {
        return al_DtClearInterestTax;
    }
    public void setAl_DtClearInterestTax(Timestamp al_DtClearInterestTax) {
        this.al_DtClearInterestTax = al_DtClearInterestTax;
    }
    public Timestamp getAl_DtClearOverdue() {
        return al_DtClearOverdue;
    }
    public void setAl_DtClearOverdue(Timestamp al_DtClearOverdue) {
        this.al_DtClearOverdue = al_DtClearOverdue;
    }
    public Timestamp getAl_DtClearSureFee() {
        return al_DtClearSureFee;
    }
    public void setAl_DtClearSureFee(Timestamp al_DtClearSureFee) {
        this.al_DtClearSureFee = al_DtClearSureFee;
    }
    public Timestamp getAl_DtEffectiveTax() {
        return al_DtEffectiveTax;
    }
    public void setAl_DtEffectiveTax(Timestamp al_DtEffectiveTax) {
        this.al_DtEffectiveTax = al_DtEffectiveTax;
    }
    public Timestamp getAl_DtPredraw() {
        return al_DtPredraw;
    }
    public void setAl_DtPredraw(Timestamp al_DtPredraw) {
        this.al_DtPredraw = al_DtPredraw;
    }
    public double getAl_MArrearageInterest() {
        return al_MArrearageInterest;
    }
    public void setAl_MArrearageInterest(double al_MArrearageInterest) {
        this.al_MArrearageInterest = al_MArrearageInterest;
    }
    public double getAl_MCommission() {
        return al_MCommission;
    }
    public void setAl_MCommission(double al_MCommission) {
        this.al_MCommission = al_MCommission;
    }
    public double getAl_MCompoundInterest() {
        return al_MCompoundInterest;
    }
    public void setAl_MCompoundInterest(double al_MCompoundInterest) {
        this.al_MCompoundInterest = al_MCompoundInterest;
    }
    public double getAl_MInterestTax() {
        return al_MInterestTax;
    }
    public void setAl_MInterestTax(double al_MInterestTax) {
        this.al_MInterestTax = al_MInterestTax;
    }
    public double getAl_MInterestTaxRate() {
        return al_MInterestTaxRate;
    }
    public void setAl_MInterestTaxRate(double al_MInterestTaxRate) {
        this.al_MInterestTaxRate = al_MInterestTaxRate;
    }
    public double getAl_MOverdueArrearageInterest() {
        return al_MOverdueArrearageInterest;
    }
    public void setAl_MOverdueArrearageInterest(
            double al_MOverdueArrearageInterest) {
        this.al_MOverdueArrearageInterest = al_MOverdueArrearageInterest;
    }
    public double getAl_MOverdueInterest() {
        return al_MOverdueInterest;
    }
    public void setAl_MOverdueInterest(double al_MOverdueInterest) {
        this.al_MOverdueInterest = al_MOverdueInterest;
    }
    public double getAl_MPredrawInterest() {
        return al_MPredrawInterest;
    }
    public void setAl_MPredrawInterest(double al_MPredrawInterest) {
        this.al_MPredrawInterest = al_MPredrawInterest;
    }
    public double getAl_MSuretyFee() {
        return al_MSuretyFee;
    }
    public void setAl_MSuretyFee(double al_MSuretyFee) {
        this.al_MSuretyFee = al_MSuretyFee;
    }
    public long getAl_NCommissionAccountId() {
        return al_NCommissionAccountId;
    }
    public void setAl_NCommissionAccountId(long al_NCommissionAccountId) {
        this.al_NCommissionAccountId = al_NCommissionAccountId;
    }
    public long getAl_NCompoundAccountId() {
        return al_NCompoundAccountId;
    }
    public void setAl_NCompoundAccountId(long al_NCompoundAccountId) {
        this.al_NCompoundAccountId = al_NCompoundAccountId;
    }
    public long getAl_NConsignAccountId() {
        return al_NConsignAccountId;
    }
    public void setAl_NConsignAccountId(long al_NConsignAccountId) {
        this.al_NConsignAccountId = al_NConsignAccountId;
    }
    public long getAl_NInterestTaxAccountId() {
        return al_NInterestTaxAccountId;
    }
    public void setAl_NInterestTaxAccountId(long al_NInterestTaxAccountId) {
        this.al_NInterestTaxAccountId = al_NInterestTaxAccountId;
    }
    public long getAl_NIsCycLoan() {
        return al_NIsCycLoan;
    }
    public void setAl_NIsCycLoan(long al_NIsCycLoan) {
        this.al_NIsCycLoan = al_NIsCycLoan;
    }
    public long getAl_NLoanNoteId() {
        return al_NLoanNoteId;
    }
    public void setAl_NLoanNoteId(long al_NLoanNoteId) {
        this.al_NLoanNoteId = al_NLoanNoteId;
    }
    public long getAl_NOverdueAccountId() {
        return al_NOverdueAccountId;
    }
    public void setAl_NOverdueAccountId(long al_NOverdueAccountId) {
        this.al_NOverdueAccountId = al_NOverdueAccountId;
    }
    public long getAl_NPayInterestAccountId() {
        return al_NPayInterestAccountId;
    }
    public void setAl_NPayInterestAccountId(long al_NPayInterestAccountId) {
        this.al_NPayInterestAccountId = al_NPayInterestAccountId;
    }
    public long getAl_NPaySuretyAccountId() {
        return al_NPaySuretyAccountId;
    }
    public void setAl_NPaySuretyAccountId(long al_NPaySuretyAccountId) {
        this.al_NPaySuretyAccountId = al_NPaySuretyAccountId;
    }
    public long getAl_NReceiveInterestAccountId() {
        return al_NReceiveInterestAccountId;
    }
    public void setAl_NReceiveInterestAccountId(
            long al_NReceiveInterestAccountId) {
        this.al_NReceiveInterestAccountId = al_NReceiveInterestAccountId;
    }
    public long getAl_NReceiveSuretyAccountId() {
        return al_NReceiveSuretyAccountId;
    }
    public void setAl_NReceiveSuretyAccountId(long al_NReceiveSuretyAccountId) {
        this.al_NReceiveSuretyAccountId = al_NReceiveSuretyAccountId;
    }
    public Timestamp getDtClearInterest() {
        return dtClearInterest;
    }
    public void setDtClearInterest(Timestamp dtClearInterest) {
        this.dtClearInterest = dtClearInterest;
    }
    public Timestamp getDtFinish() {
        return dtFinish;
    }
    public void setDtFinish(Timestamp dtFinish) {
        this.dtFinish = dtFinish;
    }
    public Timestamp getDtOpen() {
        return dtOpen;
    }
    public void setDtOpen(Timestamp dtOpen) {
        this.dtOpen = dtOpen;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getMBalance() {
        return mBalance;
    }
    public void setMBalance(double balance) {
        mBalance = balance;
    }
    public double getMInterest() {
        return mInterest;
    }
    public void setMInterest(double interest) {
        mInterest = interest;
    }
    public double getMOpenAmount() {
        return mOpenAmount;
    }
    public void setMOpenAmount(double openAmount) {
        mOpenAmount = openAmount;
    }
    public double getMUncheckPaymentAmount() {
        return mUncheckPaymentAmount;
    }
    public void setMUncheckPaymentAmount(double uncheckPaymentAmount) {
        mUncheckPaymentAmount = uncheckPaymentAmount;
    }
    public long getNAccountId() {
        return nAccountId;
    }
    public void setNAccountId(long accountId) {
        nAccountId = accountId;
    }
    public long getNCheckUserId() {
        return nCheckUserId;
    }
    public void setNCheckUserId(long checkUserId) {
        nCheckUserId = checkUserId;
    }
    public long getNInputUserId() {
        return nInputUserId;
    }
    public void setNInputUserId(long inputUserId) {
        nInputUserId = inputUserId;
    }
    public long getNIsInterest() {
        return nIsInterest;
    }
    public void setNIsInterest(long isInterest) {
        nIsInterest = isInterest;
    }
    public long getNStatusId() {
        return nStatusId;
    }
    public void setNStatusId(long statusId) {
        nStatusId = statusId;
    }
    public String getSSubject() {
        return sSubject;
    }
    public void setSSubject(String subject) {
        sSubject = subject;
    }
    public Timestamp getAc_DtNegotiateRate() {
        return ac_DtNegotiateRate;
    }
    public void setAc_DtNegotiateRate(Timestamp ac_DtNegotiateRate) {
        this.ac_DtNegotiateRate = ac_DtNegotiateRate;
    }
    public Timestamp getAc_DtInterestRatePlan() {
        return ac_DtInterestRatePlan;
    }
    public void setAc_DtInterestRatePlan(Timestamp ac_DtInterestRatePlan) {
        this.ac_DtInterestRatePlan = ac_DtInterestRatePlan;
    }
}
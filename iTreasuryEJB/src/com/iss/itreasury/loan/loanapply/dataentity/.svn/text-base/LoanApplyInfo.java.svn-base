package com.iss.itreasury.loan.loanapply.dataentity;

import java.io.Serializable;

/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
public class LoanApplyInfo  implements Cloneable,Serializable 
{
/*
    1   "ID"  NUMBER  Y
	2	"NLOANID"  NUMBER not null	贷款ID
	3	"SCONTRACTCODE" VARCHAR2(100) 合同编号
	4	"ISEXTEND" NUMBER 是否展期
	5	"DTACTIVE" DATE 激活日期
    2   "nTypeID"   NUMBER  Y   贷款类型
    9   "nsubTypeID  NUMBER  贷款子类型
    3   "nCurrencyID"   NUMBER      币种
    4   "nOfficeID" NUMBER      办事处
    5   "sApplyCode"    VARCHAR2(100)       申请号
    6   "nConsignClientID"  NUMBER      委托单位
    7   "nBorrowClientID"   NUMBER      借款单位
    8   "mLoanAmount"   NUMBER(21,6)        贷款金额
	10  "sLoanReason"   VARCHAR2(500)       借款原因
    11  "sLoanPurpose"  VARCHAR2(500)       借款目的
    12  "sOther"    VARCHAR2(500)       其他:1.还款来源及措施,2.项目批准机关及文号
    13  "nIsCircle" NUMBER      是否循环
    14  "nIsSaleBuy"    NUMBER      是否是卖方贷款 0--否 1--是 -1--不适用
    15  "nIsTechnical"  NUMBER      是否技改贷款 0--否 1--是
    16  "nInputUserID"  NUMBER      录入人
    17  "dtInputDate"   DATE        录入时间
    18  "nIsCredit" NUMBER      是否信用保证 0--否 1--是
    19  "nIsAssure" NUMBER      是否担保 0--否 1--是
    20  "nIsImpawn" NUMBER      是否质押 0--否 1--是
    21  "nIsPledge" NUMBER      是否抵押 0--否 1--是
    22  "nInterestTypeID"   NUMBER      利息类型
    23  "mExamineAmount"    NUMBER(21,6)        批准金额
    24  "nIntervalNum"  NUMBER      期限
    25  "nBankInterestID"   NUMBER      银行利率ID
    26  "nStatusID" NUMBER  Y   状态
    27  "nNextCheckUserID"  NUMBER      审核人
    28  "mChargeRate"   NUMBER(15,12)       手续费率（银团：代理费率）
    29  "dtStartDate"   DATE        贷款开始时间
    30  "dtEndDate" DATE        贷款结束时间
    31  "IsCanModify"   NUMBER      是否能被修改
    32  "nChargeRateTypeID" NUMBER      手续费率类型
    33  "sClientInfo"   VARCHAR2(500)       借款单位情况简介
    34  "mSelfAmount"   NUMBER(21,6)        财务公司承贷金额
    35  "nRiskLevel"    NUMBER      风险状态
    36  "nTypeID1"  NUMBER      按地区分类-华能
    37  "nTypeID2"  NUMBER      按行业分类1-华能
    38  "nTypeID3"  NUMBER      按行业分类2-华能
    39  "nBankAcceptPO" NUMBER      银行承兑汇票（张数）
    40  "nBizAcceptPO"  NUMBER      商业承兑汇票（张数）
    41  "mCheckAmount"  NUMBER(21,6)        汇总贴现核定金额
    42  "mDiscountRate" NUMBER(15,12)       贴现利率
    43  "dtDiscountDate"    DATE        贴现时间
    44  "mInterestRate" NUMBER(15,12)       贷款利率(委托贷款使用)
    50  "ProjectInfo"   VARCHAR2(1000)       项目简介
*/

    private long ID=-1;
    private long LoanID=-1;
    private String ContractCode="";
    private long IsExtend=-1;
    private Timestamp Active=null;
    private long typeID=-1;
    private long subTypeId= -1;
    private long currencyID=-1;
    private long officeID=-1;
    private String applyCode="";
    private long consignClientID=-1;
    private long borrowClientID=-1;
    private String consignClientName="";
    private String borrowClientName="";
    private double loanAmount=0;
    private String loanReason="";
    private String loanPurpose="";
    private String other="";
    private long isCircle=-1;
    private long isSaleBuy=-1;
    private long isTechnical=-1;
    private long inputUserID=-1;
    private Timestamp inputDate=null;
    private long isCredit=-1;
    private long isAssure=-1;
    private long isImpawn=-1;
    private long isPledge=-1;
    private long isRecognizance=-1;
    private long interestTypeID=-1;
    private double examineAmount=0;
    private long intervalNum=-1;
    private long bankInterestID=-1;
    private long liborRateID=-1;
    private long statusID=-1;
    private long nextCheckUserID=-1;
    private long nextCheckLevel=-1;
    private double chargeRate=0;
    private Timestamp startDate=null;
    private Timestamp endDate=null;
    private long isCanModify=-1;
    private long chargeRateTypeID=-1;
    private String clientInfo="";
	private String projectInfo="";//项目简介
    private double selfAmount=0;//
	private double examineSelfAmount=0;//
    
    private double selfScale=0;//承贷比例
    private long riskLevel=-1;
    private long typeID1=-1;
    private long typeID2=-1;
    private long typeID3=-1;
    private long nBankAcceptPO=-1;
    private long nBizAcceptPO=-1;
    private double checkAmount=0;
    private double discountRate=0;
    private Timestamp discountDate=null;
    private double interestRate=0;
    private double adjustRate=0;
    private Collection assureInfo=null;
    private long planDetailCount=-1;
    private long planVersion=-1;
    private double planPayAmount=0;
    private double planRepayAmount=0;
    private String inputUserName="";
    private String officeName="";
    private String lastCheckUserName="";
    private double staidAdjustRate=0;//固定浮动利率
    private long checkReportId=-1;
    //担保
	private double assureChargeRate = 0; //担保费率
	private long assureChargeTypeID = -1;//担保费收取方式
	private String beneficiary = "";	 //受益人
	private long assureTypeID1 = -1;	 //担保类型1
	private long assureTypeID2 = -1;	 //担保类型2
   
    private long isLowLevel = -1;
    
    private double useCreditAmount = 0;

	private long sellClientID=-1;
	private String sellClientName="";
	private double sellContractAmount = 0; //买方信贷合同金额
	private double selfRate = 0; //
	private long   isRepurchase=0;	    //是否回购
	
	//以下为融资租赁新增--add by yanliu 2006-03-29
	private long interestCountTypeID = -1;	//利息计算方式
	private double chargeAmount = 0.0;		//手续费金额
	private double recognizanceAmount = 0.0;//保证金金额
	private double matureNominalAmount = 0.0;//到期名义货价
	
	//以下为融资租赁新增,后面相应增加了get,set方法--added by xiong fei 2010/05/17
	private double chargeAmountRate = 0.0;//手续费率
	private double origionAmount = 0.0;//租赁物原价
	private double preAmount = 0.0;//首付款
	
	//added by mzh_fu 2007/05/23审批流
	private InutParameterInfo inutParameterInfo = null;
	private String checkOpinion = ""; //
	private String subTypeName = "";//贷款子类型 mzh_fu 2007/06/12
		
//	 贴现种类（商业承兑汇票、银行承兑汇票）	
	private long tsDiscountTypeID =-1;	
	
	private int adjustRateTerm = 0;
	
	private long isRemitCompoundInterest 	= -1; //是否计算复利
	private long isRemitOverDueInterest 	= -1; //是否计算罚息
    private double overDueAdjustRate = 0.0 ; //比例浮动
    private double overDueStaidAdjustRate = 0.0 ; //固定浮动点
    private double overDueInterestRate = 0.0 ; //利率
    
    private long isBuyInto = -1;  //是否买入资产
    
	
	public long getIsBuyInto() {
		return isBuyInto;
	}
	public void setIsBuyInto(long isBuyInto) {
		this.isBuyInto = isBuyInto;
	}
	/**
	 * @return the isRemitCompoundInterest
	 */
	public long getIsRemitCompoundInterest() {
		return isRemitCompoundInterest;
	}
	/**
	 * @param isRemitCompoundInterest the isRemitCompoundInterest to set
	 */
	public void setIsRemitCompoundInterest(long isRemitCompoundInterest) {
		this.isRemitCompoundInterest = isRemitCompoundInterest;
	}
	/**
	 * @return the isRemitOverDueInterest
	 */
	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}
	/**
	 * @param isRemitOverDueInterest the isRemitOverDueInterest to set
	 */
	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}
	/**
	 * @return the overDueAdjustRate
	 */
	public double getOverDueAdjustRate() {
		return overDueAdjustRate;
	}
	/**
	 * @param overDueAdjustRate the overDueAdjustRate to set
	 */
	public void setOverDueAdjustRate(double overDueAdjustRate) {
		this.overDueAdjustRate = overDueAdjustRate;
	}
	/**
	 * @return the overDueStaidAdjustRate
	 */
	public double getOverDueStaidAdjustRate() {
		return overDueStaidAdjustRate;
	}
	/**
	 * @param overDueStaidAdjustRate the overDueStaidAdjustRate to set
	 */
	public void setOverDueStaidAdjustRate(double overDueStaidAdjustRate) {
		this.overDueStaidAdjustRate = overDueStaidAdjustRate;
	}
	/**
	 * @return the overDueInterestRate
	 */
	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}
	/**
	 * @param overDueInterestRate the overDueInterestRate to set
	 */
	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
	}
	public int getAdjustRateTerm() {
		return adjustRateTerm;
	}
	public void setAdjustRateTerm(int adjustRateTerm) {
		this.adjustRateTerm = adjustRateTerm;
	}
	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}
	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}
	public static void main(String[] args)
	{
	}
    /**
     * @return
     */
    public String getApplyCode()
    {
        return applyCode;
    }

    /**
     * @return
     */
    public long getBankInterestID()
    {
        return bankInterestID;
    }

    /**
     * @return
     */
    public long getBorrowClientID()
    {
        return borrowClientID;
    }
 
    /**
     * @return
     */
    public double getChargeRate()
    {
        return chargeRate;
    }

    /**
     * @return
     */
    public long getChargeRateTypeID()
    {
        return chargeRateTypeID;
    }

    /**
     * @return
     */
    public double getCheckAmount()
    {
        return checkAmount;
    }

    /**
     * @return
     */
    public String getClientInfo()
    {
        return clientInfo;
    }

    /**
     * @return
     */
    public long getConsignClientID()
    {
        return consignClientID;
    }

    /**
     * @return
     */
    public long getCurrencyID()
    {
        return currencyID;
    }

    /**
     * @return
     */
    public Timestamp getDiscountDate()
    {
        return discountDate;
    }

    /**
     * @return
     */
    public double getDiscountRate()
    {
        return discountRate;
    }

    /**
     * @return
     */
    public Timestamp getEndDate()
    {
        return endDate;
    }

    /**
     * @return
     */
    public double getExamineAmount()
    {
        return examineAmount;
    }

/**
 * @return
 */
public long getID()
{
    return ID;
}

    /**
     * @return
     */
    public Timestamp getInputDate()
    {
        return inputDate;
    }

    /**
     * @return
     */
    public long getInputUserID()
    {
        return inputUserID;
    }

    /**
     * @return
     */
    public double getInterestRate()
    {
        return interestRate;
    }

    /**
     * @return
     */
    public long getInterestTypeID()
    {
        return interestTypeID;
    }

    /**
     * @return
     */
    public long getIntervalNum()
    {
        return intervalNum;
    }

    /**
     * @return
     */
    public long getIsAssure()
    {
        return isAssure;
    }

    /**
     * @return
     */
    public long getIsCanModify()
    {
        return isCanModify;
    }

    /**
     * @return
     */
    public long getIsCircle()
    {
        return isCircle;
    }

    /**
     * @return
     */
    public long getIsCredit()
    {
        return isCredit;
    }

    /**
     * @return
     */
    public long getIsImpawn()
    {
        return isImpawn;
    }

    /**
     * @return
     */
    public long getIsPledge()
    {
        return isPledge;
    }

    /**
     * @return
     */
    public long getIsSaleBuy()
    {
        return isSaleBuy;
    }

    /**
     * @return
     */
    public long getIsTechnical()
    {
        return isTechnical;
    }

    /**
     * @return
     */
    public double getLoanAmount()
    {
        return loanAmount;
    }

     /**
     * @return
     */
    public String getLoanPurpose()
    {
        return loanPurpose;
    }

    /**
     * @return
     */
    public String getLoanReason()
    {
        return loanReason;
    }

    /**
     * @return
     */
    public long getNBankAcceptPO()
    {
        return nBankAcceptPO;
    }

    /**
     * @return
     */
    public long getNBizAcceptPO()
    {
        return nBizAcceptPO;
    }

    /**
     * @return
     */
    public long getNextCheckUserID()
    {
        return nextCheckUserID;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
        return officeID;
    }

    /**
     * @return
     */
    public String getOther()
    {
        return other;
    }

    /**
     * @return
     */
    public long getRiskLevel()
    {
        return riskLevel;
    }

    /**
     * @return
     */
    public double getSelfAmount()
    {
        return selfAmount;
    }

    /**
     * @return
     */
    public Timestamp getStartDate()
    {
        return startDate;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return statusID;
    }

    /**
     * @return
     */
    public long getTypeID()
    {
        return typeID;
    }

    /**
     * @return
     */
    public long getTypeID1()
    {
        return typeID1;
    }

    /**
     * @return
     */
    public long getTypeID2()
    {
        return typeID2;
    }

    /**
     * @return
     */
    public long getTypeID3()
    {
        return typeID3;
    }

    /**
     * @param string
     */
    public void setApplyCode(String string)
    {
        applyCode = string;
    }

    /**
     * @param l
     */
    public void setBankInterestID(long l)
    {
        bankInterestID = l;
    }

    /**
     * @param l
     */
    public void setBorrowClientID(long l)
    {
        borrowClientID = l;
    }

    /**
     * @param d
     */
    public void setChargeRate(double d)
    {
        chargeRate = d;
    }

    /**
     * @param l
     */
    public void setChargeRateTypeID(long l)
    {
        chargeRateTypeID = l;
    }

    /**
     * @param d
     */
    public void setCheckAmount(double d)
    {
        checkAmount = d;
    }

    /**
     * @param string
     */
    public void setClientInfo(String string)
    {
        clientInfo = string;
    }

    /**
     * @param l
     */
    public void setConsignClientID(long l)
    {
        consignClientID = l;
    }

    /**
     * @param l
     */
    public void setCurrencyID(long l)
    {
        currencyID = l;
    }

    /**
     * @param timestamp
     */
    public void setDiscountDate(Timestamp timestamp)
    {
        discountDate = timestamp;
    }

    /**
     * @param d
     */
    public void setDiscountRate(double d)
    {
        discountRate = d;
    }

    /**
     * @param timestamp
     */
    public void setEndDate(Timestamp timestamp)
    {
        endDate = timestamp;
    }

    /**
     * @param d
     */
    public void setExamineAmount(double d)
    {
        examineAmount = d;
    }

/**
 * @param l
 */
public void setID(long l)
{
    ID = l;
}

    /**
     * @param timestamp
     */
    public void setInputDate(Timestamp timestamp)
    {
        inputDate = timestamp;
    }

    /**
     * @param l
     */
    public void setInputUserID(long l)
    {
        inputUserID = l;
    }

    /**
     * @param d
     */
    public void setInterestRate(double d)
    {
        interestRate = d;
    }

    /**
     * @param l
     */
    public void setInterestTypeID(long l)
    {
        interestTypeID = l;
    }

    /**
     * @param l
     */
    public void setIntervalNum(long l)
    {
        intervalNum = l;
    }

    /**
     * @param l
     */
    public void setIsAssure(long l)
    {
        isAssure = l;
    }

    /**
     * @param l
     */
    public void setIsCanModify(long l)
    {
        isCanModify = l;
    }

    /**
     * @param l
     */
    public void setIsCircle(long l)
    {
        isCircle = l;
    }

    /**
     * @param l
     */
    public void setIsCredit(long l)
    {
        isCredit = l;
    }

    /**
     * @param l
     */
    public void setIsImpawn(long l)
    {
        isImpawn = l;
    }

    /**
     * @param l
     */
    public void setIsPledge(long l)
    {
        isPledge = l;
    }

    /**
     * @param l
     */
    public void setIsSaleBuy(long l)
    {
        isSaleBuy = l;
    }

    /**
     * @param l
     */
    public void setIsTechnical(long l)
    {
        isTechnical = l;
    }

    /**
     * @param d
     */
    public void setLoanAmount(double d)
    {
        loanAmount = d;
    }

    /**
     * @param string
     */
    public void setLoanPurpose(String string)
    {
        loanPurpose = string;
    }

    /**
     * @param string
     */
    public void setLoanReason(String string)
    {
        loanReason = string;
    }

    /**
     * @param l
     */
    public void setNBankAcceptPO(long l)
    {
        nBankAcceptPO = l;
    }

    /**
     * @param l
     */
    public void setNBizAcceptPO(long l)
    {
        nBizAcceptPO = l;
    }

    /**
     * @param l
     */
    public void setNextCheckUserID(long l)
    {
        nextCheckUserID = l;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        officeID = l;
    }

    /**
     * @param string
     */
    public void setOther(String string)
    {
        other = string;
    }

    /**
     * @param l
     */
    public void setRiskLevel(long l)
    {
        riskLevel = l;
    }

    /**
     * @param d
     */
    public void setSelfAmount(double d)
    {
        selfAmount = d;
    }

    /**
     * @param timestamp
     */
    public void setStartDate(Timestamp timestamp)
    {
        startDate = timestamp;
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        statusID = l;
    }

    /**
     * @param l
     */
    public void setTypeID(long l)
    {
        typeID = l;
    }

    /**
     * @param l
     */
    public void setTypeID1(long l)
    {
        typeID1 = l;
    }

    /**
     * @param l
     */
    public void setTypeID2(long l)
    {
        typeID2 = l;
    }

    /**
     * @param l
     */
    public void setTypeID3(long l)
    {
        typeID3 = l;
    }

    /**
     * @return
     */
    public Collection getAssureInfo()
    {
        return assureInfo;
    }

    /**
     * @param collection
     */
    public void setAssureInfo(Collection collection)
    {
        assureInfo = collection;
    }

	/**
	 * @return
	 */
	public long getPlanDetailCount()
	{
		return planDetailCount;
	}

	/**
	 * @param l
	 */
	public void setPlanDetailCount(long l)
	{
		planDetailCount = l;
	}

	/**
	 * @return
	 */
	public long getPlanVersion()
	{
		return planVersion;
	}

	/**
	 * @param l
	 */
	public void setPlanVersion(long l)
	{
		planVersion = l;
	}

	/**
	 * @return
	 */
	public double getPlanPayAmount()
	{
		return planPayAmount;
	}
 
	/**
	 * @return
	 */
	public double getPlanRepayAmount()
	{
		return planRepayAmount;
	}

	/**
	 * @param d
	 */
	public void setPlanPayAmount(double d)
	{
		planPayAmount = d;
	}

	/**
	 * @param d
	 */
	public void setPlanRepayAmount(double d)
	{
		planRepayAmount = d;
	}

	/**
	 * @return
	 */
	public String getInputUserName()
	{
		return inputUserName;
	}

	/**
	 * @return
	 */
	public String getOfficeName()
	{
		return officeName;
	}

	/**
	 * @param string
	 */
	public void setInputUserName(String string)
	{
		inputUserName = string;
	}

	/**
	 * @param string
	 */
	public void setOfficeName(String string)
	{
		officeName = string;
	}

	/**
	 * @return
	 */
	public String getBorrowClientName()
	{
		return borrowClientName;
	}

	/**
	 * @return
	 */
	public String getConsignClientName()
	{
		return consignClientName;
	}

	/**
	 * @param string
	 */
	public void setBorrowClientName(String string)
	{
		borrowClientName = string;
	}

	/**
	 * @param string
	 */
	public void setConsignClientName(String string)
	{
		consignClientName = string;
	}

	/**
	 * @return
	 */
	public double getAdjustRate()
	{
		return adjustRate;
	}

	/**
	 * @param d
	 */
	public void setAdjustRate(double d)
	{
		adjustRate = d;
	}

	/**
	 * @return
	 */
	public String getLastCheckUserName()
	{
		return lastCheckUserName;
	}

	/**
	 * @param string
	 */
	public void setLastCheckUserName(String string)
	{
		lastCheckUserName = string;
	}
    /**
     * function 得到/设置承贷比例
     * return double
     */
    public double getSelfScale()
    {
        return selfScale;
    }

    /**
     * @param d
     * function 得到/设置承贷比例
     * return void
     */
    public void setSelfScale(double d)
    {
        this.selfScale = d;
    }

	/**
	 * Returns the active.
	 * @return Timestamp
	 */
	public Timestamp getActive() {
		return Active;
	}

	/**
	 * Returns the contractCode.
	 * @return String
	 */
	public String getContractCode() {
		return ContractCode;
	}

	/**
	 * Returns the isExtend.
	 * @return long
	 */
	public long getIsExtend() {
		return IsExtend;
	}

	/**
	 * Returns the loanID.
	 * @return long
	 */
	public long getLoanID() {
		return LoanID;
	}

	/**
	 * Sets the active.
	 * @param active The active to set
	 */
	public void setActive(Timestamp active) {
		Active = active;
	}

	/**
	 * Sets the contractCode.
	 * @param contractCode The contractCode to set
	 */
	public void setContractCode(String contractCode) {
		ContractCode = contractCode;
	}

	/**
	 * Sets the isExtend.
	 * @param isExtend The isExtend to set
	 */
	public void setIsExtend(long isExtend) {
		IsExtend = isExtend;
	}

	/**
	 * Sets the loanID.
	 * @param loanID The loanID to set
	 */
	public void setLoanID(long loanID) {
		LoanID = loanID;
	}

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return nextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        this.nextCheckLevel = nextCheckLevel;
    }
    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStaidAdjustRate()
    {
        return staidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        staidAdjustRate = d;
    }

    /**
     * @return Returns the isLowLevel.
     */
    public long getIsLowLevel()
    {
        return isLowLevel;
    }
    /**
     * @param isLowLevel The isLowLevel to set.
     */
    public void setIsLowLevel(long isLowLevel)
    {
        this.isLowLevel = isLowLevel;
    }
	/**
	 * @return
	 */
	public double getUseCreditAmount()
	{
		return useCreditAmount;
	}

	/**
	 * @param d
	 */
	public void setUseCreditAmount(double d)
	{
		useCreditAmount = d;
	}

	/**
	 * @return
	 */
	public double getExamineSelfAmount()
	{
		return examineSelfAmount;
	}

	/**
	 * @param d
	 */
	public void setExamineSelfAmount(double d)
	{
		examineSelfAmount = d;
	}

	/**
	 * @return
	 */
	public String getProjectInfo() {
		return projectInfo;
	}

	/**
	 * @param string
	 */
	public void setProjectInfo(String string) {
		projectInfo = string;
	}

    /**
     * @return Returns the assureChargeRate.
     */
    public double getAssureChargeRate()
    {
        return assureChargeRate;
    }
    /**
     * @param assureChargeRate The assureChargeRate to set.
     */
    public void setAssureChargeRate(double assureChargeRate)
    {
        this.assureChargeRate = assureChargeRate;
    }
    /**
     * @return Returns the assureChargeTypeID.
     */
    public long getAssureChargeTypeID()
    {
        return assureChargeTypeID;
    }
    /**
     * @param assureChargeTypeID The assureChargeTypeID to set.
     */
    public void setAssureChargeTypeID(long assureChargeTypeID)
    {
        this.assureChargeTypeID = assureChargeTypeID;
    }
    /**
     * @return Returns the assureTypeID1.
     */
    public long getAssureTypeID1()
    {
        return assureTypeID1;
    }
    /**
     * @param assureTypeID1 The assureTypeID1 to set.
     */
    public void setAssureTypeID1(long assureTypeID1)
    {
        this.assureTypeID1 = assureTypeID1;
    }
    /**
     * @return Returns the assureTypeID2.
     */
    public long getAssureTypeID2()
    {
        return assureTypeID2;
    }
    /**
     * @param assureTypeID2 The assureTypeID2 to set.
     */
    public void setAssureTypeID2(long assureTypeID2)
    {
        this.assureTypeID2 = assureTypeID2;
    }
    /**
     * @return Returns the beneficiary.
     */
    public String getBeneficiary()
    {
        return beneficiary;
    }
    /**
     * @param beneficiary The beneficiary to set.
     */
    public void setBeneficiary(String beneficiary)
    {
        this.beneficiary = beneficiary;
    }
    /**
     * @return Returns the isRecognizance.
     */
    public long getIsRecognizance()
    {
        return isRecognizance;
    }
    /**
     * @param isRecognizance The isRecognizance to set.
     */
    public void setIsRecognizance(long isRecognizance)
    {
        this.isRecognizance = isRecognizance;
    }
	/**
	 * @return
	 */
	public double getSelfRate() {
		return selfRate;
	}

	/**
	 * @return
	 */
	public double getSellContractAmount() {
		return sellContractAmount;
	}

	/**
	 * @param d
	 */
	public void setSelfRate(double d) {
		selfRate = d;
	}

	/**
	 * @param d
	 */
	public void setSellContractAmount(double d) {
		sellContractAmount = d;
	}

	/**
	 * @return
	 */
	public long getIsRepurchase() {
		return isRepurchase;
	}

	/**
	 * @param l
	 */
	public void setIsRepurchase(long l) {
		isRepurchase = l;
	}

	/**
	 * @return
	 */
	public long getSellClientID() {
		return sellClientID;
	}

	/**
	 * @return
	 */
	public String getSellClientName() {
		return sellClientName;
	}

	/**
	 * @param l
	 */
	public void setSellClientID(long l) {
		sellClientID = l;
	}

	/**
	 * @param string
	 */
	public void setSellClientName(String string) {
		sellClientName = string;
	}

    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return liborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        this.liborRateID = liborRateID;
    }
    /**
     * @return Returns the subTypeId.
     */
    public long getSubTypeId() {
        return subTypeId;
    }
    /**
     * @param subTypeId The subTypeId to set.
     */
    public void setSubTypeId(long subTypeId) {
        this.subTypeId = subTypeId;
    }
    /**
     * @return Returns the checkReportId.
     */
    public long getCheckReportId() {
        return checkReportId;
    }
    /**
     * @param checkReportId The checkReportId to set.
     */
    public void setCheckReportId(long checkReportId) {
        this.checkReportId = checkReportId;
    }
    
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public long getInterestCountTypeID() {
		return interestCountTypeID;
	}
	public void setInterestCountTypeID(long interestCountTypeID) {
		this.interestCountTypeID = interestCountTypeID;
	}
	public double getMatureNominalAmount() {
		return matureNominalAmount;
	}
	public void setMatureNominalAmount(double matureNominalAmount) {
		this.matureNominalAmount = matureNominalAmount;
	}
	public double getRecognizanceAmount() {
		return recognizanceAmount;
	}
	public void setRecognizanceAmount(double recognizanceAmount) {
		this.recognizanceAmount = recognizanceAmount;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public String getCheckOpinion() {
		return checkOpinion;
	}
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	//add by zwxiao 2008-4-18 作用是加入克隆该对象的方法
	public Object clone() { 
		LoanApplyInfo applyInfo = null;
		try{ 
			applyInfo = (LoanApplyInfo)super.clone(); 
		}catch (CloneNotSupportedException e){ 
			throw new InternalError(e.toString()); 
		} 
		return applyInfo; 
	}
	public double getChargeAmountRate() {
		return chargeAmountRate;
	}
	public void setChargeAmountRate(double chargeAmountRate) {
		this.chargeAmountRate = chargeAmountRate;
	}
	public double getOrigionAmount() {
		return origionAmount;
	}
	public void setOrigionAmount(double origionAmount) {
		this.origionAmount = origionAmount;
	}
	public double getPreAmount() {
		return preAmount;
	}
	public void setPreAmount(double preAmount) {
		this.preAmount = preAmount;
	}

}

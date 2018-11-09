package com.iss.itreasury.loan.obinterface.dataentity;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.*;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OBLoanInfo implements java.io.Serializable
{
    /**
     * OBApplyInfo 构造子注解。
     */
    public OBLoanInfo()
    {
        super();
    }
    
    //ID:标识
    private long ID;  
    private long InApplyID;    
    private long OfficeID = -1;    
    private long CurrencyID = -1;    
    private long TypeID = -1;    
    private String InstructionNo = "";    
    private String ApplyCode = "";    
    private long ConsignClientID = -1;    
    private long BorrowClientID = -1;
    private String ConsignClientName = "";
    private String ConsignClientCode = "";
    private String ConsignClientContacter = "";
    private String BorrowClientName = "";
    private double LoanAmount = 0;
    private String LoanReason = "";
    private String LoanPurpose = "";
    private String Other = "";
    private long IsCircle = -1;
    private long IsSaleBuy = -1;
    private long IsTechnical = -1;
    private long InputUserID = -1;
    private Timestamp InputDate = null;
    private long IsCredit = -1;
    private long IsAssure = -1;
    private long IsImpawn = -1;
    private long IsPledge = -1;
    private long InterestTypeID = -1;
    private double ExamineAmount = 0;
    private long IntervalNum = -1;
    private long BankInterestID = -1;
    private long StatusID=-1;
    private long NextCheckUserID = -1;
    private double ChargeRate = 0;
    private Timestamp StartDate = null;
    private Timestamp EndDate = null;
    private long IsCanModify = -1;
    private long ChargeRateTypeID = -1;
    private String ClientInfoDesc = "";
    private ClientInfo ClientInfo = null;
    private double SelfAmount = 0;//
    private double SelfScale = 0;//
    private long RiskLevel = -1;
    private long TypeID1 = -1;
    private long TypeID2 = -1;
    private long TypeID3 = -1;
    private long BankAcceptPO = -1;
    private long BizAcceptPO = -1;
    private double CheckAmount = 0;
    private double DiscountRate = 0;
    private Timestamp DiscountDate = null;
    private double InterestRate = 0;
    private double AdjustRate = 0;
    private Collection AssureInfo=null;
    private long PlanDetailCount = -1;
    private long PlanVersion = -1;
    private double PlanPayAmount = 0;
    private double PlanRepayAmount = 0;
    private String InputUserName =" ";
    private String OfficeName = "";
    private String DocumentType = "";
    private long nSubTypeId=-1;
    private double assureChargeRate = 0; //担保费率
	private long assureChargeTypeID = -1;//担保费收取方式
	private String beneficiary = "";	 //受益人
	private long assureTypeID1 = -1;	 //担保类型1
	private long assureTypeID2 = -1;	 //担保类型2
	private long isRecognizance=-1;//是否保证金
	private long isPurchaserInterest = 2;	//是否买方付息
    private long discountClientID = -1;		//出票人
    private String discountClientName = "";	//出票人名称
    
    public long getIsRecognizance() {
		return isRecognizance;
	}

	public void setIsRecognizance(long isRecognizance) {
		this.isRecognizance = isRecognizance;
	}

	public double getAssureChargeRate() {
		return assureChargeRate;
	}

	public void setAssureChargeRate(double assureChargeRate) {
		this.assureChargeRate = assureChargeRate;
	}

	public long getAssureChargeTypeID() {
		return assureChargeTypeID;
	}

	public void setAssureChargeTypeID(long assureChargeTypeID) {
		this.assureChargeTypeID = assureChargeTypeID;
	}

	public long getAssureTypeID1() {
		return assureTypeID1;
	}

	public void setAssureTypeID1(long assureTypeID1) {
		this.assureTypeID1 = assureTypeID1;
	}

	public long getAssureTypeID2() {
		return assureTypeID2;
	}

	public void setAssureTypeID2(long assureTypeID2) {
		this.assureTypeID2 = assureTypeID2;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public long getNSubTypeId() {
		return nSubTypeId;
	}

	public void setNSubTypeId(long subTypeId) {
		nSubTypeId = subTypeId;
	}

	/**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getAdjustRate()
    {
        return AdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getApplyCode()
    {
        return ApplyCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBankAcceptPO()
    {
        return BankAcceptPO;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBankInterestID()
    {
        return BankInterestID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBizAcceptPO()
    {
        return BizAcceptPO;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBorrowClientID()
    {
        return BorrowClientID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getBorrowClientName()
    {
        return BorrowClientName;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getChargeRate()
    {
        return ChargeRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getChargeRateTypeID()
    {
        return ChargeRateTypeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getCheckAmount()
    {
        return CheckAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getConsignClientID()
    {
        return ConsignClientID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getConsignClientName()
    {
        return ConsignClientName;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getDiscountDate()
    {
        return DiscountDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getDiscountRate()
    {
        return DiscountRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getEndDate()
    {
        return EndDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getExamineAmount()
    {
        return ExamineAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getInputDate()
    {
        return InputDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInputUserID()
    {
        return InputUserID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getInterestRate()
    {
        return InterestRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInterestTypeID()
    {
        return InterestTypeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIntervalNum()
    {
        return IntervalNum;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsAssure()
    {
        return IsAssure;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsCanModify()
    {
        return IsCanModify;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsCircle()
    {
        return IsCircle;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsCredit()
    {
        return IsCredit;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsImpawn()
    {
        return IsImpawn;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsPledge()
    {
        return IsPledge;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsSaleBuy()
    {
        return IsSaleBuy;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getIsTechnical()
    {
        return IsTechnical;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getLoanAmount()
    {
        return LoanAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getLoanPurpose()
    {
        return LoanPurpose;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getLoanReason()
    {
        return LoanReason;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getNextCheckUserID()
    {
        return NextCheckUserID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getOther()
    {
        return Other;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPlanDetailCount()
    {
        return PlanDetailCount;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getPlanPayAmount()
    {
        return PlanPayAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getPlanRepayAmount()
    {
        return PlanRepayAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getPlanVersion()
    {
        return PlanVersion;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getRiskLevel()
    {
        return RiskLevel;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getSelfAmount()
    {
        return SelfAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getSelfScale()
    {
        return SelfScale;
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getStartDate()
    {
        return StartDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getTypeID()
    {
        return TypeID;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getTypeID1()
    {
        return TypeID1;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getTypeID2()
    {
        return TypeID2;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getTypeID3()
    {
        return TypeID3;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAdjustRate(double d)
    {
        AdjustRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setApplyCode(String string)
    {
        ApplyCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBankAcceptPO(long l)
    {
        BankAcceptPO = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBankInterestID(long l)
    {
        BankInterestID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBizAcceptPO(long l)
    {
        BizAcceptPO = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBorrowClientID(long l)
    {
        BorrowClientID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBorrowClientName(String string)
    {
        BorrowClientName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setChargeRate(double d)
    {
        ChargeRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setChargeRateTypeID(long l)
    {
        ChargeRateTypeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCheckAmount(double d)
    {
        CheckAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientInfo(ClientInfo cInfo)
    {
        ClientInfo = cInfo;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setConsignClientID(long l)
    {
        ConsignClientID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setConsignClientName(String string)
    {
        ConsignClientName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDiscountDate(Timestamp timestamp)
    {
        DiscountDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDiscountRate(double d)
    {
        DiscountRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setEndDate(Timestamp timestamp)
    {
        EndDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setExamineAmount(double d)
    {
        ExamineAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        InputDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputUserID(long l)
    {
        InputUserID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputUserName(String string)
    {
        InputUserName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInterestRate(double d)
    {
        InterestRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInterestTypeID(long l)
    {
        InterestTypeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIntervalNum(long l)
    {
        IntervalNum = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsAssure(long l)
    {
        IsAssure = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsCanModify(long l)
    {
        IsCanModify = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsCircle(long l)
    {
        IsCircle = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsCredit(long l)
    {
        IsCredit = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsImpawn(long l)
    {
        IsImpawn = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsPledge(long l)
    {
        IsPledge = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsSaleBuy(long l)
    {
        IsSaleBuy = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setIsTechnical(long l)
    {
        IsTechnical = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanAmount(double d)
    {
        LoanAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanPurpose(String string)
    {
        LoanPurpose = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanReason(String string)
    {
        LoanReason = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setNextCheckUserID(long l)
    {
        NextCheckUserID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeName(String string)
    {
        OfficeName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOther(String string)
    {
        Other = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPlanDetailCount(long l)
    {
        PlanDetailCount = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPlanPayAmount(double d)
    {
        PlanPayAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPlanRepayAmount(double d)
    {
        PlanRepayAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPlanVersion(long l)
    {
        PlanVersion = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setRiskLevel(long l)
    {
        RiskLevel = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setSelfAmount(double d)
    {
        SelfAmount = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setSelfScale(double d)
    {
        SelfScale = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStartDate(Timestamp timestamp)
    {
        StartDate = timestamp;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setTypeID(long l)
    {
        TypeID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setTypeID1(long l)
    {
        TypeID1 = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setTypeID2(long l)
    {
        TypeID2 = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setTypeID3(long l)
    {
        TypeID3 = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatusID(long l)
    {
        StatusID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getInstructionNo()
    {
        return InstructionNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInstructionNo(String string)
    {
        InstructionNo = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInApplyID()
    {
        return InApplyID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInApplyID(long l)
    {
        InApplyID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getConsignClientCode()
    {
        return ConsignClientCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setConsignClientCode(String string)
    {
        ConsignClientCode = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getConsignClientContacter()
    {
        return ConsignClientContacter;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setConsignClientContacter(String string)
    {
        ConsignClientContacter = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getDocumentType()
    {
        return DocumentType;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDocumentType(String string)
    {
        DocumentType = string;
    }   

    /**
     * @param 
     * function 得到/设置
     * return Collection
     */
    public Collection getAssureInfo()
    {
        return AssureInfo;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAssureInfo(Collection collection)
    {
        AssureInfo = collection;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getClientInfoDesc()
    {
        return ClientInfoDesc;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientInfoDesc(String string)
    {
        ClientInfoDesc = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return ClientInfo
     */
    public ClientInfo getClientInfo()
    {
        return ClientInfo;
    }

	public long getDiscountClientID() {
		return discountClientID;
	}

	public void setDiscountClientID(long discountClientID) {
		this.discountClientID = discountClientID;
	}

	public String getDiscountClientName() {
		return discountClientName;
	}

	public void setDiscountClientName(String discountClientName) {
		this.discountClientName = discountClientName;
	}

	public long getIsPurchaserInterest() {
		return isPurchaserInterest;
	}

	public void setIsPurchaserInterest(long isPurchaserInterest) {
		this.isPurchaserInterest = isPurchaserInterest;
	}

}

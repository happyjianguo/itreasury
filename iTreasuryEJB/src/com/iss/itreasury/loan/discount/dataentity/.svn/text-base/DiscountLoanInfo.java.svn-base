/*
 * DiscountLoanInfo.java
 *
 * Created on 2002年4月8日, 上午10:41
 */

package com.iss.itreasury.loan.discount.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 *
 * @author  yfan
 * @version 
 */
public class DiscountLoanInfo implements java.io.Serializable {

    /** Creates new DiscountLoanInfo */
    public DiscountLoanInfo() {
        super();
    }
    
    private long ID;                        //贴现ID标识
    private String DiscountCode;            //贴现编号
    private String ContractCode;            //贴现合同编号

    private long ApplyClientID;             //申请单位编号
    private String ApplyClientName;         //申请单位名称
    private String ApplyAccount;            //申请单位账户号
    private String ApplyBank;               //申请单位开户银行
    private long ApplyOfficeID;             //申请单位开户办事处标示
    private String ApplyOfficeName;         //申请单位开户办事处名称
    private String ApplyLOfficeAccount;     //申请单位开户办事处账号
    
    private long TypeID;                    //贷款类型
    private long subTypeId;                //贷款子类型
    private long CurrencyID;                //币种
    private long OfficeID;                  //办事处标示
    private String OfficeName;              //办事处名称
    private String LOfficeAccount;          //办事处账号
    private long StatusID;                  //状态
    
    private long  InputUserID;              //录入人标示
    private String InputUserName;           //录入人名称
    private Timestamp InputDate;            //录入时间
    
    private long NextCheckUserID;           //下一个审核人标示
    private long NextCheckLevel;            //下一个审核级别
    private long LsReviewUserID;            //最后审核人ID
    private String LsReviewUserName;        //最后审核人名称
    private long ReviewStatusID;            //最后审核状态
    private long IsCheck;                   //是否审核过
   
    private double ApplyDiscountAmount;     //申请贴现金额
    private double ExamineAmount;           //批准金额
    private double CheckAmount;             //核定金额
    private double DiscountRate;            //利率
    private String DiscountPurpose;         //贴现用途
    private String DiscountReason;          //贴现原因
            
    private Timestamp DiscountDate;         //贴现计息时间
    private Timestamp DiscountStartDate;    //贴现开始时间
    private Timestamp DiscountEndDate;      //贴现到期时间
  
    private long ApplyDiscountPO;           //申请贴现汇票（张数）
    private long BankAccepPO;               //银行承兑汇票（张数）
    private long BizAcceptPO;               //商业承兑汇票（张数）
    private long BankCount;                	//银行承兑汇票（张数）
    private long BizCount;                	//商业承兑汇票（张数）
    private double Interest;                //贴现利息
 	private long BillCount;                	//汇票总张数
 	private long DistinctBillCount;         //汇票总张数（编号不重复）
    private double BillAmount;              //汇票总金额
                
    private long Count;               		//记录数
    
    private long isLowLevel = -1;
    
    private long isPurchaserInterest = 2;	//是否买方付息
    private long discountClientID = -1;		//出票人
    private String discountClientName = "";	//出票人名称
    private double purchaserInterestRate = 0;//买方付息比例
    

	//added by xwhe 2007/06/14审批流
	private InutParameterInfo inutParameterInfo = null;
	private String checkOpinion = ""; //
	private String subTypeName = "";//贷款子类型 xwhe 2007/06/14
	
	private double DiscountAccrual;              //贴现人付息
	private double PurchaserAmount;              //买方付息

    public double getDiscountAccrual() {
		return DiscountAccrual;
	}
	public void setDiscountAccrual(double discountAccrual) {
		DiscountAccrual = discountAccrual;
	}
	public double getPurchaserAmount() {
		return PurchaserAmount;
	}
	public void setPurchaserAmount(double purchaserAmount) {
		PurchaserAmount = purchaserAmount;
	}
	/**
     * @return Returns the discountClientName.
     */
    public String getDiscountClientName()
    {
        return discountClientName;
    }
    /**
     * @param discountClientName The discountClientName to set.
     */
    public void setDiscountClientName(String discountClientName)
    {
        this.discountClientName = discountClientName;
    }
    /**
     * @return Returns the discountClientID.
     */
    public long getDiscountClientID()
    {
        return discountClientID;
    }
    /**
     * @param discountClientID The discountClientID to set.
     */
    public void setDiscountClientID(long discountClientID)
    {
        this.discountClientID = discountClientID;
    }
    /**
     * @return Returns the purchaserInterestRate.
     */
    public double getPurchaserInterestRate()
    {
        return purchaserInterestRate;
    }
    /**
     * @param purchaserInterestRate The purchaserInterestRate to set.
     */
    public void setPurchaserInterestRate(double purchaserInterestRate)
    {
        this.purchaserInterestRate = purchaserInterestRate;
    }
    /**
     * @return
     */
    public String getApplyAccount()
    {
        return ApplyAccount;
    }

    /**
     * @return
     */
    public String getApplyBank()
    {
        return ApplyBank;
    }

    /**
     * @return
     */
    public long getApplyClientID()
    {
        return ApplyClientID;
    }

    /**
     * @return
     */
    public String getApplyClientName()
    {
        return ApplyClientName;
    }

    /**
     * @return
     */
    public double getApplyDiscountAmount()
    {
        return ApplyDiscountAmount;
    }

    /**
     * @return
     */
    public long getApplyDiscountPO()
    {
        return ApplyDiscountPO;
    }

    /**
     * @return
     */
    public String getApplyLOfficeAccount()
    {
        return ApplyLOfficeAccount;
    }

    /**
     * @return
     */
    public long getApplyOfficeID()
    {
        return ApplyOfficeID;
    }

    /**
     * @return
     */
    public String getApplyOfficeName()
    {
        return ApplyOfficeName;
    }

    /**
     * @return
     */
    public long getBankAccepPO()
    {
        return BankAccepPO;
    }
    
    /**
     * @return
     */
    public long getBankCount()
    {
        return BankCount;
    }

    /**
     * @return
     */
    public double getBillAmount()
    {
        return BillAmount;
    }

    /**
     * @return
     */
    public long getBillCount()
    {
        return BillCount;
    }

    /**
     * @return
     */
    public long getBizAcceptPO()
    {
        return BizAcceptPO;
    }
    
    /**
     * @return
     */
    public long getBizCount()
    {
        return BizCount;
    }

    /**
     * @return
     */
    public double getCheckAmount()
    {
        return CheckAmount;
    }

    /**
     * @return
     */
    public long getCount()
    {
        return Count;
    }

    /**
     * @return
     */
    public long getCurrencyID()
    {
        return CurrencyID;
    }

    /**
     * @return
     */
    public String getDiscountCode()
    {
        return DiscountCode;
    }
    
    /**
     * @return
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @return
     */
    public Timestamp getDiscountDate()
    {
        return DiscountDate;
    }

    /**
     * @return
     */
    public Timestamp getDiscountEndDate()
    {
        return DiscountEndDate;
    }

    /**
     * @return
     */
    public String getDiscountPurpose()
    {
        return DiscountPurpose;
    }

    /**
     * @return
     */
    public double getDiscountRate()
    {
        return DiscountRate;
    }

    /**
     * @return
     */
    public String getDiscountReason()
    {
        return DiscountReason;
    }

    /**
     * @return
     */
    public Timestamp getDiscountStartDate()
    {
        return DiscountStartDate;
    }

    /**
     * @return
     */
    public double getExamineAmount()
    {
        return ExamineAmount;
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
        return InputDate;
    }

    /**
     * @return
     */
    public long getInputUserID()
    {
        return InputUserID;
    }

    /**
     * @return
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @return
     */
    public double getInterest()
    {
        return Interest;
    }

    /**
     * @return
     */
    public long getIsCheck()
    {
        return IsCheck;
    }

    /**
     * @return
     */
    public String getLOfficeAccount()
    {
        return LOfficeAccount;
    }

    /**
     * @return
     */
    public long getLsReviewUserID()
    {
        return LsReviewUserID;
    }

    /**
     * @return
     */
    public String getLsReviewUserName()
    {
        return LsReviewUserName;
    }

    /**
     * @return
     */
    public long getNextCheckUserID()
    {
        return NextCheckUserID;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
        return OfficeID;
    }

    /**
     * @return
     */
    public String getOfficeName()
    {
        return OfficeName;
    }

    /**
     * @return
     */
    public long getReviewStatusID()
    {
        return ReviewStatusID;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @return
     */
    public long getTypeID()
    {
        return TypeID;
    }

    /**
     * @param string
     */
    public void setApplyAccount(String string)
    {
        ApplyAccount = string;
    }

    /**
     * @param string
     */
    public void setApplyBank(String string)
    {
        ApplyBank = string;
    }

    /**
     * @param l
     */
    public void setApplyClientID(long l)
    {
        ApplyClientID = l;
    }

    /**
     * @param string
     */
    public void setApplyClientName(String string)
    {
        ApplyClientName = string;
    }

    /**
     * @param d
     */
    public void setApplyDiscountAmount(double d)
    {
        ApplyDiscountAmount = d;
    }

    /**
     * @param l
     */
    public void setApplyDiscountPO(long l)
    {
        ApplyDiscountPO = l;
    }

    /**
     * @param string
     */
    public void setApplyLOfficeAccount(String string)
    {
        ApplyLOfficeAccount = string;
    }

    /**
     * @param l
     */
    public void setApplyOfficeID(long l)
    {
        ApplyOfficeID = l;
    }

    /**
     * @param string
     */
    public void setApplyOfficeName(String string)
    {
        ApplyOfficeName = string;
    }

    /**
     * @param l
     */
    public void setBankAccepPO(long l)
    {
        BankAccepPO = l;
    }
    
    /**
     * @param l
     */
    public void setBankCount(long l)
    {
        BankCount = l;
    }

    /**
     * @param d
     */
    public void setBillAmount(double d)
    {
        BillAmount = d;
    }

    /**
     * @param l
     */
    public void setBillCount(long l)
    {
        BillCount = l;
    }

    /**
     * @param l
     */
    public void setBizAcceptPO(long l)
    {
        BizAcceptPO = l;
    }
    
    /**
     * @param l
     */
    public void setBizCount(long l)
    {
        BizCount = l;
    }

    /**
     * @param d
     */
    public void setCheckAmount(double d)
    {
        CheckAmount = d;
    }

    /**
     * @param l
     */
    public void setCount(long l)
    {
        Count = l;
    }

    /**
     * @param l
     */
    public void setCurrencyID(long l)
    {
        CurrencyID = l;
    }

    /**
     * @param string
     */
    public void setDiscountCode(String string)
    {
        DiscountCode = string;
    }
    
    /**
     * @param string
     */
    public void setContractCode(String string)
    {
        ContractCode = string;
    }

    /**
     * @param timestamp
     */
    public void setDiscountDate(Timestamp timestamp)
    {
        DiscountDate = timestamp;
    }

    /**
     * @param timestamp
     */
    public void setDiscountEndDate(Timestamp timestamp)
    {
        DiscountEndDate = timestamp;
    }

    /**
     * @param string
     */
    public void setDiscountPurpose(String string)
    {
        DiscountPurpose = string;
    }

    /**
     * @param d
     */
    public void setDiscountRate(double d)
    {
        DiscountRate = d;
    }

    /**
     * @param string
     */
    public void setDiscountReason(String string)
    {
        DiscountReason = string;
    }

    /**
     * @param timestamp
     */
    public void setDiscountStartDate(Timestamp timestamp)
    {
        DiscountStartDate = timestamp;
    }

    /**
     * @param d
     */
    public void setExamineAmount(double d)
    {
        ExamineAmount = d;
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
        InputDate = timestamp;
    }

    /**
     * @param l
     */
    public void setInputUserID(long l)
    {
        InputUserID = l;
    }

    /**
     * @param string
     */
    public void setInputUserName(String string)
    {
        InputUserName = string;
    }

    /**
     * @param d
     */
    public void setInterest(double d)
    {
        Interest = d;
    }

    /**
     * @param l
     */
    public void setIsCheck(long l)
    {
        IsCheck = l;
    }

    /**
     * @param string
     */
    public void setLOfficeAccount(String string)
    {
        LOfficeAccount = string;
    }

    /**
     * @param l
     */
    public void setLsReviewUserID(long l)
    {
        LsReviewUserID = l;
    }

    /**
     * @param string
     */
    public void setLsReviewUserName(String string)
    {
        LsReviewUserName = string;
    }

    /**
     * @param l
     */
    public void setNextCheckUserID(long l)
    {
        NextCheckUserID = l;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
        OfficeID = l;
    }

    /**
     * @param string
     */
    public void setOfficeName(String string)
    {
        OfficeName = string;
    }

    /**
     * @param l
     */
    public void setReviewStatusID(long l)
    {
        ReviewStatusID = l;
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        StatusID = l;
    }

    /**
     * @param l
     */
    public void setTypeID(long l)
    {
        TypeID = l;
    }

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return NextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        NextCheckLevel = nextCheckLevel;
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
     * @return Returns the isPurchaserInterest.
     */
    public long getIsPurchaserInterest()
    {
        return isPurchaserInterest;
    }
    /**
     * @param isPurchaserInterest The isPurchaserInterest to set.
     */
    public void setIsPurchaserInterest(long isPurchaserInterest)
    {
        this.isPurchaserInterest = isPurchaserInterest;
    }
    /**
     * @return Returns the distinctBillCount.
     */
    public long getDistinctBillCount()
    {
        return DistinctBillCount;
    }
    /**
     * @param distinctBillCount The distinctBillCount to set.
     */
    public void setDistinctBillCount(long distinctBillCount)
    {
        DistinctBillCount = distinctBillCount;
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
	public String getCheckOpinion() {
		return checkOpinion;
	}
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
}

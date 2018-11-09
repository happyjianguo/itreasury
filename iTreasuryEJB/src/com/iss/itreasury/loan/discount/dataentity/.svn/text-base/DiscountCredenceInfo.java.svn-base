/*
 * DiscountCredenceInfo.java
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
public class DiscountCredenceInfo implements java.io.Serializable {

    /** Creates new DiscountCredenceInfo */
    public DiscountCredenceInfo() {
        super();
    }
    
    //贴现申请    
    private long DiscountApplyID;           //贴现申请ID标识
    private String DiscountApplyCode;       //贴现申请编号
    private long DiscountApplyStatusID;     //贴现申请状态
    private long subTypeId;                 //贷款类型子类Id
    
    //贴现合同    
    private long DiscountContractID;        //贴现合同ID标识
    private String DiscountContractCode;    //贴现合同编号
    private long DiscountContractStatusID;  //贴现合同状态

    private long ApplyClientID;             //申请单位标示
    private String ApplyClientName;         //申请单位名称
    private String ApplyAccount;            //申请单位账户号
    private String ApplyBank;               //申请单位开户银行
           
    private double ApplyDiscountAmount;     //申请贴现金额
    private double ExamineAmount;           //批准金额
    private double CheckAmount;             //核定金额
    private double DiscountRate;            //利率
    private String DiscountPurpose;         //贴现用途
    private String DiscountReason;          //贴现原因
        
    private Timestamp DiscountDate;        //贴现计息时间
    private Timestamp DiscountStartDate;   //贴现开始时间
    private Timestamp DiscountEndDate;     //贴现到期时间
    
    private long isPurchaserInterest = 2;	//是否买方付息
    private long discountClientID = -1;		//出票人
    private String discountClientName = "";	//出票人名称
    private double purchaserInterestRate = 0;//买方付息比例
        
    //贴现凭证
    private long 		ID;                //贴现凭证标识
    private String 		Code;		       	//贴现凭证编号
    private Timestamp 	FillDate;  			//填写日期
    private long 		DraftTypeID;       //贴现汇票种类标示
    private String 		DraftCode;       	//贴现汇票号码
    private Timestamp 	PublicDate;  		//发票日
    private Timestamp 	AtTerm;      		//到期日
    private long 		AcceptClientID;		//承兑单位标示
    private String 		AcceptClientName;	//承兑单位名称
    private String 		AcceptAccount;   	//承兑单位账户号
    private String 		AcceptBank;      	//承兑单位银行
    
    private long        GrantTypeID = -1;       //放款方式
    private long        AccountBankID = -1;     //开户银行
    private String      AccountBankCode = ""; 	//开户银行
    private String      AccountBankName = "";   //开户银行
    private String      ReceiveClientCode = ""; //收款单位账号
    private String      ReceiveClientName = ""; //收款单位名称
    private String      RemitBank = "";         //汇入行
    private String      RemitInProvince = "";   	//汇入地（省）
    private String      RemitInCity = "";       	//汇入地（市）
    private long        GrantCurrentAccountID = -1; //发放至活期账户
    private String      GrantCurrentAccountCode = ""; //发放至活期账户
    
    private double 		BillAmount;        //凭证金额
    private double 		BillInterest;      //凭证利息
    private double 		purchaserInterest; //买方支付凭证利息
    private double 		BillCheckAmount;   //凭证核定金额
    private double      purchaserCheckAmount; //买方支付利息凭证核定金额
    private long[]      lBillIDArray=null; 
    
    private long 		OfficeID;          //办事处标示
	private long 		CurrencyID;         //
    private long 		StatusID;          //状态
    
    private long  		InputUserID;       //录入人标示
    private String 		InputUserName;     //录入人名称
	private Timestamp 	InputDate;        //录入时间
    private long 		CheckNum;          //修改次数
    private Timestamp 	ModifyDate;       //修改时间
    
    private long 		NextCheckUserID;   //下一个审核人标示
    private String 		NextCheckUser="";
    private long		LastCheckUserID;   //最后审核人ID
    private String 		LastCheckUserName; //最后审核人名称
    private long 		CheckStatusID;     //最后审核状态
    private long 		NextCheckLevel;    //下一个审核级别
        
    private long Count;               //记录数
    
    private InutParameterInfo inutParameterInfo = null;
    
    private String amounts=""; //汇票金额
    private String purchaserAccruals=""; //买方付息
    private String discountAccruals=""; //贴现人付息
    private String draftcodes = "";//票据号码
    private String ids = "";//票据号码
    private long discountpayform =-1;//放款通知单ID
    private boolean isPayForm =false;

    public String getAmounts() {
		return amounts;
	}

	public void setAmounts(String amounts) {
		this.amounts = amounts;
	}

	public String getPurchaserAccruals() {
		return purchaserAccruals;
	}

	public void setPurchaserAccruals(String purchaserAccruals) {
		this.purchaserAccruals = purchaserAccruals;
	}

	public String getDiscountAccruals() {
		return discountAccruals;
	}

	public void setDiscountAccruals(String discountAccruals) {
		this.discountAccruals = discountAccruals;
	}

	public String getDraftcodes() {
		return draftcodes;
	}

	public void setDraftcodes(String draftcodes) {
		this.draftcodes = draftcodes;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public long getDiscountpayform() {
		return discountpayform;
	}

	public void setDiscountpayform(long discountpayform) {
		this.discountpayform = discountpayform;
	}

	public boolean isPayForm() {
		return isPayForm;
	}

	public void setPayForm(boolean isPayForm) {
		this.isPayForm = isPayForm;
	}

	/**
     * @return
     */
    public String getAcceptAccount()
    {
        return AcceptAccount;
    }

    /**
     * @return
     */
    public String getAcceptBank()
    {
        return AcceptBank;
    }

    /**
     * @return
     */
    public String getAcceptClientName()
    {
        return AcceptClientName;
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
    public Timestamp getAtTerm()
    {
        return AtTerm;
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
    public double getBillCheckAmount()
    {
        return BillCheckAmount;
    }

    /**
     * @return
     */
    public double getBillInterest()
    {
        return BillInterest;
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
    public long getCheckNum()
    {
        return CheckNum;
    }

    /**
     * @return
     */
    public long getCheckStatusID()
    {
        return CheckStatusID;
    }

    /**
     * @return
     */
    public String getCode()
    {
        return Code;
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
    public String getDiscountApplyCode()
    {
        return DiscountApplyCode;
    }

    /**
     * @return
     */
    public long getDiscountApplyID()
    {
        return DiscountApplyID;
    }

    /**
     * @return
     */
    public long getDiscountApplyStatusID()
    {
        return DiscountApplyStatusID;
    }

    /**
     * @return
     */
    public String getDiscountContractCode()
    {
        return DiscountContractCode;
    }

    /**
     * @return
     */
    public long getDiscountContractID()
    {
        return DiscountContractID;
    }

    /**
     * @return
     */
    public long getDiscountContractStatusID()
    {
        return DiscountContractStatusID;
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
    public String getDraftCode()
    {
        return DraftCode;
    }

    /**
     * @return
     */
    public long getDraftTypeID()
    {
        return DraftTypeID;
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
    public Timestamp getFillDate()
    {
        return FillDate;
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
    public long getLastCheckUserID()
    {
        return LastCheckUserID;
    }

    /**
     * @return
     */
    public String getLastCheckUserName()
    {
        return LastCheckUserName;
    }

    /**
     * @return
     */
    public Timestamp getModifyDate()
    {
        return ModifyDate;
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
    public Timestamp getPublicDate()
    {
        return PublicDate;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param string
     */
    public void setAcceptAccount(String string)
    {
        AcceptAccount = string;
    }

    /**
     * @param string
     */
    public void setAcceptBank(String string)
    {
        AcceptBank = string;
    }

    /**
     * @param string
     */
    public void setAcceptClientName(String string)
    {
        AcceptClientName = string;
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
     * @param timestamp
     */
    public void setAtTerm(Timestamp timestamp)
    {
        AtTerm = timestamp;
    }

    /**
     * @param d
     */
    public void setBillAmount(double d)
    {
        BillAmount = d;
    }

    /**
     * @param d
     */
    public void setBillCheckAmount(double d)
    {
        BillCheckAmount = d;
    }

    /**
     * @param d
     */
    public void setBillInterest(double d)
    {
        BillInterest = d;
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
    public void setCheckNum(long l)
    {
        CheckNum = l;
    }

    /**
     * @param l
     */
    public void setCheckStatusID(long l)
    {
        CheckStatusID = l;
    }

    /**
     * @param string
     */
    public void setCode(String string)
    {
        Code = string;
    }

    /**
     * @param l
     */
    public void setCount(long l)
    {
        Count = l;
    }

    /**
     * @param string
     */
    public void setDiscountApplyCode(String string)
    {
        DiscountApplyCode = string;
    }

    /**
     * @param l
     */
    public void setDiscountApplyID(long l)
    {
        DiscountApplyID = l;
    }

    /**
     * @param l
     */
    public void setDiscountApplyStatusID(long l)
    {
        DiscountApplyStatusID = l;
    }

    /**
     * @param string
     */
    public void setDiscountContractCode(String string)
    {
        DiscountContractCode = string;
    }

    /**
     * @param l
     */
    public void setDiscountContractID(long l)
    {
        DiscountContractID = l;
    }

    /**
     * @param l
     */
    public void setDiscountContractStatusID(long l)
    {
        DiscountContractStatusID = l;
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
     * @param string
     */
    public void setDraftCode(String string)
    {
        DraftCode = string;
    }

    /**
     * @param l
     */
    public void setDraftTypeID(long l)
    {
        DraftTypeID = l;
    }

    /**
     * @param d
     */
    public void setExamineAmount(double d)
    {
        ExamineAmount = d;
    }

    /**
     * @param timestamp
     */
    public void setFillDate(Timestamp timestamp)
    {
        FillDate = timestamp;
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
     * @param l
     */
    public void setLastCheckUserID(long l)
    {
        LastCheckUserID = l;
    }

    /**
     * @param string
     */
    public void setLastCheckUserName(String string)
    {
        LastCheckUserName = string;
    }

    /**
     * @param timestamp
     */
    public void setModifyDate(Timestamp timestamp)
    {
        ModifyDate = timestamp;
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
     * @param timestamp
     */
    public void setPublicDate(Timestamp timestamp)
    {
        PublicDate = timestamp;
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        StatusID = l;
    }

    /**
     * @return
     */
    public long getAcceptClientID()
    {
        return AcceptClientID;
    }

    /**
     * @return
     */
    public long getAccountBankID()
    {
        return AccountBankID;
    }
    
    /**
     * @return
     */
    public String getAccountBankCode()
    {
        return AccountBankCode;
    }

    /**
     * @return
     */
    public String getAccountBankName()
    {
        return AccountBankName;
    }

    /**
     * @return
     */
    public long getGrantCurrentAccountID()
    {
        return GrantCurrentAccountID;
    }
    
    /**
     * @return
     */
    public String getGrantCurrentAccountCode()
    {
        return GrantCurrentAccountCode;
    }

    /**
     * @return
     */
    public long getGrantTypeID()
    {
        return GrantTypeID;
    }

    /**
     * @return
     */
    public String getReceiveClientCode()
    {
        return ReceiveClientCode;
    }

    /**
     * @return
     */
    public String getReceiveClientName()
    {
        return ReceiveClientName;
    }

    /**
     * @return
     */
    public String getRemitBank()
    {
        return RemitBank;
    }

    /**
     * @return
     */
    public String getRemitInCity()
    {
        return RemitInCity;
    }

    /**
     * @return
     */
    public String getRemitInProvince()
    {
        return RemitInProvince;
    }

    /**
     * @param l
     */
    public void setAcceptClientID(long l)
    {
        AcceptClientID = l;
    }

    /**
     * @param l
     */
    public void setAccountBankID(long l)
    {
        AccountBankID = l;
    }
    
    /**
     * @param string
     */
    public void setAccountBankCode(String string)
    {
        AccountBankCode = string;
    }

    /**
     * @param string
     */
    public void setAccountBankName(String string)
    {
        AccountBankName = string;
    }

    /**
     * @param l
     */
    public void setGrantCurrentAccountID(long l)
    {
        GrantCurrentAccountID = l;
    }
    
    /**
     * @param string
     */
    public void setGrantCurrentAccountCode(String string)
    {
        GrantCurrentAccountCode = string;
    }

    /**
     * @param l
     */
    public void setGrantTypeID(long l)
    {
        GrantTypeID = l;
    }

    /**
     * @param string
     */
    public void setReceiveClientCode(String string)
    {
        ReceiveClientCode = string;
    }

    /**
     * @param string
     */
    public void setReceiveClientName(String string)
    {
        ReceiveClientName = string;
    }

    /**
     * @param string
     */
    public void setRemitBank(String string)
    {
        RemitBank = string;
    }

    /**
     * @param string
     */
    public void setRemitInCity(String string)
    {
        RemitInCity = string;
    }

    /**
     * @param string
     */
    public void setRemitInProvince(String string)
    {
        RemitInProvince = string;
    }

	/**
	 * @return
	 */
	public String getNextCheckUser() {
		return NextCheckUser;
	}

	/**
	 * @param string
	 */
	public void setNextCheckUser(String string) {
		NextCheckUser = string;
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
     * @return Returns the purchaserInterest.
     */
    public double getPurchaserInterest()
    {
        return purchaserInterest;
    }
    /**
     * @param purchaserInterest The purchaserInterest to set.
     */
    public void setPurchaserInterest(double purchaserInterest)
    {
        this.purchaserInterest = purchaserInterest;
    }
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}

	
	public long getSubTypeId()
	{
	
		return subTypeId;
	}

	
	public void setSubTypeId(long subTypeId)
	{
	
		this.subTypeId = subTypeId;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long[] getLBillIDArray() {
		return lBillIDArray;
	}

	public void setLBillIDArray(long[] billIDArray) {
		this.lBillIDArray = billIDArray;
	}
	public double getPurchaserCheckAmount() {
		return purchaserCheckAmount;
	}

	public void setPurchaserCheckAmount(double purchaserCheckAmount) {
		this.purchaserCheckAmount = purchaserCheckAmount;
	}

}

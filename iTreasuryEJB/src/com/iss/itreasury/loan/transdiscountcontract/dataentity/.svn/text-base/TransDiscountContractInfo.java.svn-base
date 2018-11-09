/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yfan 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcontract.dataentity;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import java.sql.Timestamp;
import java.util.*;
/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountContractInfo extends ITreasuryBaseDataEntity
{
    private long id = -1;//ID:合同标识
    private long nLoanId;//申请Id
    private String sContractCode;//sContractCode:合同编号
    private String sApplyCode;// 申请编号
    private long nTypeId;//nLoanTypeID:贷款种类
    private long nSubtypeid;  //贷款子类型
    private long nCurrencyId;//nCurrencyID:币种标识
    private long nOfficeId;//nOfficeID:办事处标识
    private long nConsignClientId;//nClientID 委托单位ID
    private long nBorrowClientId;//nBorrowClientID:贷款单位ID
    private long nIsExtend;//是否展期
    
    private Timestamp dtActive;//激活日期
    private double mLoanAmount;//mLoanAmount 金额
    private double mExamineAmount;//ExamineAmount 批准金额
    private double mCheckAmount;//汇总贴现核定金额
    private double mDiscountInterest;//贴现利息
    private String sLoanReason;//借款原因
    private String sLoanPurpose;//借款用途
    private String sOther = "";//还款来源
    private long nInputUserID;//nInputUserID:录入人标识 亦是合同管理人
    private Timestamp dtInputDate; //合同录入日期
    
    private long nIsCredit = -1;//信用
    private long nIsAssure = -1;//保证
    private long nIsImpawn = -1;//抵押
    private long nIsPledge = -1;//质押
    private long nIntervalNum;//nIntervalNum:贷款期限
    private long nBankInterestTypeId;//银行利率ID
    private long nStatusId;//nStatusID:合同状态
    private long nNextCheckUserId = -1;//审核人ID
    private double mChargeRate = 0;//手续费率
    private long nChargeRateTypeId = -1;//手续费率类型
    private long nInterestTypeId = -1;//利息类型，做计息类型用
    
    private Timestamp dtStartDate;//dtLoanStart借款起始日期
    private Timestamp dtEndDate;//dtLoanStart借款结束日期
    private long nRisklevel;//风险等级
    private long nTypeId1 = -1;//按地区分类
    private long nTypeId2 = -1;//按行业分类1
    private long nTypeId3 = -1;//按行业分类2
    //private long ApplyDiscountPO;//申请贴现汇票（张数）
    private long nBankAcceptPO;//银行承兑汇票（张数）
    private long nBizAcceptPO;//商业承兑汇票（张数）
    private double mDiscountRate;//DiscountRate 贴现利率
    private Timestamp dtDiscountDate; //DiscountRate日期
    
    private double mInterestRate;//利率
    private double mBasicInterestRate;//基准利率
    private double mAdjustRate = 0;//浮动利率
    private long nNextCheckLevel = -1;//下一个审核级别
    private double mStaidAdjustRate=0;//固定浮动利率
    //private long PageCount;//分页显示总页数
    //private long AllRecord;//总纪录数,仅在贷款申请查询时用到
    //private double AllAmount;//总金额
    private Collection DiscountBill = null;//该合同的票据
    private long nInOrOut = -1;//买入还是卖出
    private long nDiscountTypeId = -1;//买断还是回购
    private long nRepurchaseTypeId = -1;//回购类型
    private long isLowLevel = -1;//审批流程
    
    
    private long recordCount = 0;//总记录数
    private long pageCount = 0;//总页数
    private long pageNo = 0;//当前页码
    private double totalAmount = 0;//总金额
    private Timestamp repurchasedate ;//回购日期   为同业转贴现提醒业务增加
    
    private Collection cContractContent = null;//合同文本信息
    //modify by xwhe date:2007-09-13
    private InutParameterInfo inutParameterInfo = null;
    
    //方正简化转贴现流程合同增加交易对手开户行账户字段
	long nCounterpartAcctID = -1;
	
    public long getCounterpartAcctID() {
		return nCounterpartAcctID;
	}
	public void setCounterpartAcctID(long lCounterpartAcctID) {
		this.nCounterpartAcctID = lCounterpartAcctID;
		putUsedField("nCounterpartAcctId", lCounterpartAcctID);
	}
	
	private long attachId = -1;		//附件关联ID
	
	public long getAttachId() {
		return attachId;
	}
	public void setAttachId(long attachId) {
		this.attachId = attachId;
		putUsedField("attachId", attachId);
	}

    public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}
	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
	/**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
        putUsedField("id", id);
    }
    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getLoanId()
    {
        return nLoanId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanId(long l)
    {
        nLoanId = l;
        putUsedField("nLoanId", nLoanId);
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getContractCode()
    {
        return sContractCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setContractCode(String string)
    {
        sContractCode = string;
        putUsedField("sContractCode", sContractCode);
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getApplyCode()
    {
        return sApplyCode;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setApplyCode(String string)
    {
        sApplyCode = string;
        putUsedField("sApplyCode", sApplyCode);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getTypeId()
    {
        return nTypeId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setTypeId(long l)
    {
        nTypeId = l;
        putUsedField("nTypeId", nTypeId);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getCurrencyId()
    {
        return nCurrencyId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCurrencyId(long l)
    {
        nCurrencyId = l;
        putUsedField("nCurrencyId", nCurrencyId);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getOfficeId()
    {
        return nOfficeId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setOfficeId(long l)
    {
        nOfficeId = l;
        putUsedField("nOfficeId", nOfficeId);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBorrowClientId()
    {
        return nBorrowClientId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBorrowClientId(long l)
    {
        nBorrowClientId = l;
        putUsedField("nBorrowClientId", nBorrowClientId);
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getLoanAmount()
    {
        return mLoanAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanAmount(double d)
    {
        mLoanAmount = d;
        putUsedField("mLoanAmount", mLoanAmount);
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getCheckAmount()
    {
        return mCheckAmount;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCheckAmount(double d)
    {
        mCheckAmount = d;
        putUsedField("mCheckAmount", mCheckAmount);
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getLoanReason()
    {
        return sLoanReason;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanReason(String string)
    {
        sLoanReason = string;
        putUsedField("sLoanReason", sLoanReason);
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getLoanPurpose()
    {
        return sLoanPurpose;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setLoanPurpose(String string)
    {
        sLoanPurpose = string;
        putUsedField("sLoanPurpose", sLoanPurpose);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getInputUserID()
    {
        return nInputUserID;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputUserID(long l)
    {
        nInputUserID = l;
        putUsedField("nInputUserID", nInputUserID);
    }

    /**
     * @param 
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getInputDate()
    {
        return dtInputDate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        dtInputDate = timestamp;
        putUsedField("dtInputDate", dtInputDate);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getStatusId()
    {
        return nStatusId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStatusId(long l)
    {
        nStatusId = l;
        putUsedField("nStatusID", nStatusId);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getNextCheckUserId()
    {
        return nNextCheckUserId;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setNextCheckUserId(long l)
    {
        nNextCheckUserId = l;
        putUsedField("nNextCheckUserId", nNextCheckUserId);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBankAcceptPO()
    {
        return nBankAcceptPO;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBankAcceptPO(long l)
    {
        nBankAcceptPO = l;
        putUsedField("nBankAcceptPO", nBankAcceptPO);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getBizAcceptPO()
    {
        return nBizAcceptPO;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setBizAcceptPO(long l)
    {
        nBizAcceptPO = l;
        putUsedField("nBizAcceptPO", nBizAcceptPO);
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getDiscountRate()
    {
        return mDiscountRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDiscountRate(double d)
    {
        mDiscountRate = d;
        putUsedField("mDiscountRate", mDiscountRate);
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getNextCheckLevel()
    {
        return nNextCheckLevel;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setNextCheckLevel(long l)
    {
        nNextCheckLevel = l;
        putUsedField("nNextCheckLevel", nNextCheckLevel);
    }

    /**
     * @param 
     * function 得到/设置
     * return Collection
     */
    public Collection getDiscountBill()
    {
        return DiscountBill;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setDiscountBill(Collection collection)
    {
        DiscountBill = collection;
    }

    /**
     * @param 
     * return Timestamp
     */
    public Timestamp getActive()
    {
        return dtActive;
    }

    /**
     * @param 
     * return Timestamp
     */
    public Timestamp getEndDate()
    {
        return dtEndDate;
    }

    /**
     * @param 
     * return Timestamp
     */
    public Timestamp getStartDate()
    {
        return dtStartDate;
    }

    /**
     * @param 
     * return double
     */
    public double getAdjustRate()
    {
        return mAdjustRate;
    }

    /**
     * @param 
     * return double
     */
    public double getBasicInterestRate()
    {
        return mBasicInterestRate;
    }

    /**
     * @param 
     * return double
     */
    public double getChargeRate()
    {
        return mChargeRate;
    }

    /**
     * @param 
     * return double
     */
    public double getDiscountInterest()
    {
        return mDiscountInterest;
    }

    /**
     * @param 
     * return double
     */
    public double getExamineAmount()
    {
        return mExamineAmount;
    }

    /**
     * @param 
     * return double
     */
    public double getInterestRate()
    {
        return mInterestRate;
    }

    /**
     * @param 
     * return double
     */
    public double getStaidAdjustRate()
    {
        return mStaidAdjustRate;
    }

    /**
     * @param 
     * return long
     */
    public long getBankInterestTypeId()
    {
        return nBankInterestTypeId;
    }

    /**
     * @param 
     * return long
     */
    public long getChargeRateTypeId()
    {
        return nChargeRateTypeId;
    }

    /**
     * @param 
     * return long
     */
    public long getConsignClientId()
    {
        return nConsignClientId;
    }

    /**
     * @param 
     * return long
     */
    public long getIntervalNum()
    {
        return nIntervalNum;
    }

    /**
     * @param 
     * return long
     */
    public long getIsAssure()
    {
        return nIsAssure;
    }

    /**
     * @param 
     * return long
     */
    public long getIsCredit()
    {
        return nIsCredit;
    }

    /**
     * @param 
     * return long
     */
    public long getIsExtend()
    {
        return nIsExtend;
    }

    /**
     * @param 
     * return long
     */
    public long getIsImpawn()
    {
        return nIsImpawn;
    }

    /**
     * @param 
     * return long
     */
    public long getIsPledge()
    {
        return nIsPledge;
    }

    /**
     * @param 
     * return long
     */
    public long getRisklevel()
    {
        return nRisklevel;
    }

    /**
     * @param 
     * return long
     */
    public long getTypeId1()
    {
        return nTypeId1;
    }

    /**
     * @param 
     * return long
     */
    public long getTypeId2()
    {
        return nTypeId2;
    }

    /**
     * @param 
     * return long
     */
    public long getTypeId3()
    {
        return nTypeId3;
    }

    /**
     * @param 
     * return String
     */
    public String getOther()
    {
        return sOther;
    }

    /**
     * @param 
     * return void
     */
    public void setActiveDate(Timestamp timestamp)
    {
        dtActive = timestamp;
        putUsedField("dtActive", dtActive);
    }

    /**
     * @param 
     * return void
     */
    public void setEndDate(Timestamp timestamp)
    {
        dtEndDate = timestamp;
        putUsedField("dtEndDate", dtEndDate);
    }

    /**
     * @param 
     * return void
     */
    public void setStartDate(Timestamp timestamp)
    {
        dtStartDate = timestamp;
        putUsedField("dtStartDate", dtStartDate);
    }

    /**
     * @param 
     * return void
     */
    public void setAdjustRate(double d)
    {
        mAdjustRate = d;
        putUsedField("mAdjustRate", mAdjustRate);
    }

    /**
     * @param 
     * return void
     */
    public void setBasicInterestRate(double d)
    {
        mBasicInterestRate = d;
        putUsedField("mBasicInterestRate", mBasicInterestRate);
    }

    /**
     * @param 
     * return void
     */
    public void setChargeRate(double d)
    {
        mChargeRate = d;
        putUsedField("mChargeRate", mChargeRate);
    }

    /**
     * @param 
     * return void
     */
    public void setDiscountInterest(double d)
    {
        mDiscountInterest = d;
        //putUsedField("mDiscountInterest", mDiscountInterest);
    }

    /**
     * @param 
     * return void
     */
    public void setExamineAmount(double d)
    {
        mExamineAmount = d;
        putUsedField("mExamineAmount", mExamineAmount);
    }

    /**
     * @param 
     * return void
     */
    public void setInterestRate(double d)
    {
        mInterestRate = d;
        putUsedField("mInterestRate", mInterestRate);
    }

    /**
     * @param 
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        mStaidAdjustRate = d;
        putUsedField("mStaidAdjustRate", mStaidAdjustRate);
    }

    /**
     * @param 
     * return void
     */
    public void setBankInterestTypeId(long l)
    {
        nBankInterestTypeId = l;
        putUsedField("nBankInterestTypeId", nBankInterestTypeId);
    }

    /**
     * @param 
     * return void
     */
    public void setChargeRateTypeId(long l)
    {
        nChargeRateTypeId = l;
        putUsedField("nChargeRateTypeId", nChargeRateTypeId);
    }

    /**
     * @param 
     * return void
     */
    public void setConsignClientId(long l)
    {
        nConsignClientId = l;
        putUsedField("nConsignClientId", nConsignClientId);
    }

    /**
     * @param 
     * return void
     */
    public void setIntervalNum(long l)
    {
        nIntervalNum = l;
        putUsedField("nIntervalNum", nIntervalNum);
    }

    /**
     * @param 
     * return void
     */
    public void setIsAssure(long l)
    {
        nIsAssure = l;
        putUsedField("nIsAssure", nIsAssure);
    }

    /**
     * @param 
     * return void
     */
    public void setIsCredit(long l)
    {
        nIsCredit = l;
        putUsedField("nIsCredit", nIsCredit);
    }

    /**
     * @param 
     * return void
     */
    public void setIsExtend(long l)
    {
        nIsExtend = l;
        putUsedField("nIsExtend", nIsExtend);
    }

    /**
     * @param 
     * return void
     */
    public void setIsImpawn(long l)
    {
        nIsImpawn = l;
        putUsedField("nIsImpawn", nIsImpawn);
    }

    /**
     * @param 
     * return void
     */
    public void setIsPledge(long l)
    {
        nIsPledge = l;
        putUsedField("nIsPledge", nIsPledge);
    }

    /**
     * @param 
     * return void
     */
    public void setRisklevel(long l)
    {
        nRisklevel = l;
        putUsedField("nRisklevel", nRisklevel);
    }

    /**
     * @param 
     * return void
     */
    public void setTypeId1(long l)
    {
        nTypeId1 = l;
        putUsedField("nTypeId1", nTypeId1);
    }

    /**
     * @param 
     * return void
     */
    public void setTypeId2(long l)
    {
        nTypeId2 = l;
        putUsedField("nTypeId2", nTypeId2);
    }

    /**
     * @param 
     * return void
     */
    public void setTypeId3(long l)
    {
        nTypeId3 = l;
        putUsedField("nTypeId3", nTypeId3);
    }

    /**
     * @param 
     * return void
     */
    public void setOther(String string)
    {
        sOther = string;
        putUsedField("sOther", sOther);
    }

	/**
	 * @return
	 */
	public Timestamp getDiscountDate()
	{
		return dtDiscountDate;
	}

	/**
	 * @param timestamp
	 */
	public void setDiscountDate(Timestamp timestamp)
	{
		dtDiscountDate = timestamp;
		putUsedField("dtDiscountDate", dtDiscountDate);
	}

    /**
     * @param 
     * return long
     */
    public long getDiscountTypeId()
    {
        return nDiscountTypeId;
    }

    /**
     * @param 
     * return long
     */
    public long getInOrOut()
    {
        return nInOrOut;
    }

    /**
     * @param 
     * return long
     */
    public long getRepurchaseTypeId()
    {
        return nRepurchaseTypeId;
    }

    /**
     * @param 
     * return void
     */
    public void setDiscountTypeId(long l)
    {
        nDiscountTypeId = l;
        putUsedField("nDiscountTypeId", nDiscountTypeId);
    }

    /**
     * @param 
     * return void
     */
    public void setInOrOut(long l)
    {
        nInOrOut = l;
        putUsedField("nInOrOut",nInOrOut);
    }

    /**
     * @param 
     * return void
     */
    public void setRepurchaseTypeId(long l)
    {
        nRepurchaseTypeId = l;
        putUsedField("nRepurchaseTypeId",nRepurchaseTypeId);
    }

	/**
	 * @return Returns the cContractContent.
	 */
	public Collection getContractContent() {
		return cContractContent;
	}
	/**
	 * @param contractContent The cContractContent to set.
	 */
	public void setContractContent(Collection contractContent) {
		cContractContent = contractContent;
	}
	/**
	 * @return Returns the isLowLevel.
	 */
	public long getIsLowLevel() {
		return isLowLevel;
	}
	/**
	 * @param isLowLevel The isLowLevel to set.
	 */
	public void setIsLowLevel(long isLowLevel) {
		this.isLowLevel = isLowLevel;
		putUsedField("isLowLevel",isLowLevel);
	}
    /**
     * @param 
     * return long
     */
    public long getInterestTypeId()
    {
        return nInterestTypeId;
    }

    /**
     * @param 
     * return void
     */
    public void setInterestTypeId(long l)
    {
        nInterestTypeId = l;
        putUsedField("nInterestTypeId",nInterestTypeId);
    }

	/**
	 * @return Returns the pageCount.
	 */
	public long getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount The pageCount to set.
	 */
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return Returns the pageNo.
	 */
	public long getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return Returns the recordCount.
	 */
	public long getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount The recordCount to set.
	 */
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
    /**
     * @return Returns the nSubtypeid.
     */
    public long getNSubtypeid() {
        return nSubtypeid;
    }
    /**
     * @param subtypeid The nSubtypeid to set.
     */
    public void setNSubtypeid(long subtypeid) {
        this.nSubtypeid = subtypeid;
		putUsedField("nSubtypeid",nSubtypeid);
    }
	public void setRepurchasedate(Timestamp repurchasedate) {
		this.repurchasedate = repurchasedate;
	}
	public Timestamp getRepurchasedate() {
		return repurchasedate;
	}
}

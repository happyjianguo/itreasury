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
    private long id = -1;//ID:��ͬ��ʶ
    private long nLoanId;//����Id
    private String sContractCode;//sContractCode:��ͬ���
    private String sApplyCode;// ������
    private long nTypeId;//nLoanTypeID:��������
    private long nSubtypeid;  //����������
    private long nCurrencyId;//nCurrencyID:���ֱ�ʶ
    private long nOfficeId;//nOfficeID:���´���ʶ
    private long nConsignClientId;//nClientID ί�е�λID
    private long nBorrowClientId;//nBorrowClientID:���λID
    private long nIsExtend;//�Ƿ�չ��
    
    private Timestamp dtActive;//��������
    private double mLoanAmount;//mLoanAmount ���
    private double mExamineAmount;//ExamineAmount ��׼���
    private double mCheckAmount;//�������ֺ˶����
    private double mDiscountInterest;//������Ϣ
    private String sLoanReason;//���ԭ��
    private String sLoanPurpose;//�����;
    private String sOther = "";//������Դ
    private long nInputUserID;//nInputUserID:¼���˱�ʶ ���Ǻ�ͬ������
    private Timestamp dtInputDate; //��ͬ¼������
    
    private long nIsCredit = -1;//����
    private long nIsAssure = -1;//��֤
    private long nIsImpawn = -1;//��Ѻ
    private long nIsPledge = -1;//��Ѻ
    private long nIntervalNum;//nIntervalNum:��������
    private long nBankInterestTypeId;//��������ID
    private long nStatusId;//nStatusID:��ͬ״̬
    private long nNextCheckUserId = -1;//�����ID
    private double mChargeRate = 0;//��������
    private long nChargeRateTypeId = -1;//������������
    private long nInterestTypeId = -1;//��Ϣ���ͣ�����Ϣ������
    
    private Timestamp dtStartDate;//dtLoanStart�����ʼ����
    private Timestamp dtEndDate;//dtLoanStart����������
    private long nRisklevel;//���յȼ�
    private long nTypeId1 = -1;//����������
    private long nTypeId2 = -1;//����ҵ����1
    private long nTypeId3 = -1;//����ҵ����2
    //private long ApplyDiscountPO;//�������ֻ�Ʊ��������
    private long nBankAcceptPO;//���гжһ�Ʊ��������
    private long nBizAcceptPO;//��ҵ�жһ�Ʊ��������
    private double mDiscountRate;//DiscountRate ��������
    private Timestamp dtDiscountDate; //DiscountRate����
    
    private double mInterestRate;//����
    private double mBasicInterestRate;//��׼����
    private double mAdjustRate = 0;//��������
    private long nNextCheckLevel = -1;//��һ����˼���
    private double mStaidAdjustRate=0;//�̶���������
    //private long PageCount;//��ҳ��ʾ��ҳ��
    //private long AllRecord;//�ܼ�¼��,���ڴ��������ѯʱ�õ�
    //private double AllAmount;//�ܽ��
    private Collection DiscountBill = null;//�ú�ͬ��Ʊ��
    private long nInOrOut = -1;//���뻹������
    private long nDiscountTypeId = -1;//��ϻ��ǻع�
    private long nRepurchaseTypeId = -1;//�ع�����
    private long isLowLevel = -1;//��������
    
    
    private long recordCount = 0;//�ܼ�¼��
    private long pageCount = 0;//��ҳ��
    private long pageNo = 0;//��ǰҳ��
    private double totalAmount = 0;//�ܽ��
    private Timestamp repurchasedate ;//�ع�����   Ϊͬҵת��������ҵ������
    
    private Collection cContractContent = null;//��ͬ�ı���Ϣ
    //modify by xwhe date:2007-09-13
    private InutParameterInfo inutParameterInfo = null;
    
    //������ת�������̺�ͬ���ӽ��׶��ֿ������˻��ֶ�
	long nCounterpartAcctID = -1;
	
    public long getCounterpartAcctID() {
		return nCounterpartAcctID;
	}
	public void setCounterpartAcctID(long lCounterpartAcctID) {
		this.nCounterpartAcctID = lCounterpartAcctID;
		putUsedField("nCounterpartAcctId", lCounterpartAcctID);
	}
	
	private long attachId = -1;		//��������ID
	
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
     * function �õ�/����
     * return long
     */
    public long getLoanId()
    {
        return nLoanId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setLoanId(long l)
    {
        nLoanId = l;
        putUsedField("nLoanId", nLoanId);
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getContractCode()
    {
        return sContractCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setContractCode(String string)
    {
        sContractCode = string;
        putUsedField("sContractCode", sContractCode);
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getApplyCode()
    {
        return sApplyCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setApplyCode(String string)
    {
        sApplyCode = string;
        putUsedField("sApplyCode", sApplyCode);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getTypeId()
    {
        return nTypeId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setTypeId(long l)
    {
        nTypeId = l;
        putUsedField("nTypeId", nTypeId);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getCurrencyId()
    {
        return nCurrencyId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCurrencyId(long l)
    {
        nCurrencyId = l;
        putUsedField("nCurrencyId", nCurrencyId);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getOfficeId()
    {
        return nOfficeId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setOfficeId(long l)
    {
        nOfficeId = l;
        putUsedField("nOfficeId", nOfficeId);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getBorrowClientId()
    {
        return nBorrowClientId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBorrowClientId(long l)
    {
        nBorrowClientId = l;
        putUsedField("nBorrowClientId", nBorrowClientId);
    }

    /**
     * @param 
     * function �õ�/����
     * return double
     */
    public double getLoanAmount()
    {
        return mLoanAmount;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setLoanAmount(double d)
    {
        mLoanAmount = d;
        putUsedField("mLoanAmount", mLoanAmount);
    }

    /**
     * @param 
     * function �õ�/����
     * return double
     */
    public double getCheckAmount()
    {
        return mCheckAmount;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCheckAmount(double d)
    {
        mCheckAmount = d;
        putUsedField("mCheckAmount", mCheckAmount);
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getLoanReason()
    {
        return sLoanReason;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setLoanReason(String string)
    {
        sLoanReason = string;
        putUsedField("sLoanReason", sLoanReason);
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getLoanPurpose()
    {
        return sLoanPurpose;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setLoanPurpose(String string)
    {
        sLoanPurpose = string;
        putUsedField("sLoanPurpose", sLoanPurpose);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getInputUserID()
    {
        return nInputUserID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInputUserID(long l)
    {
        nInputUserID = l;
        putUsedField("nInputUserID", nInputUserID);
    }

    /**
     * @param 
     * function �õ�/����
     * return Timestamp
     */
    public Timestamp getInputDate()
    {
        return dtInputDate;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInputDate(Timestamp timestamp)
    {
        dtInputDate = timestamp;
        putUsedField("dtInputDate", dtInputDate);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getStatusId()
    {
        return nStatusId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setStatusId(long l)
    {
        nStatusId = l;
        putUsedField("nStatusID", nStatusId);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getNextCheckUserId()
    {
        return nNextCheckUserId;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setNextCheckUserId(long l)
    {
        nNextCheckUserId = l;
        putUsedField("nNextCheckUserId", nNextCheckUserId);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getBankAcceptPO()
    {
        return nBankAcceptPO;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBankAcceptPO(long l)
    {
        nBankAcceptPO = l;
        putUsedField("nBankAcceptPO", nBankAcceptPO);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getBizAcceptPO()
    {
        return nBizAcceptPO;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBizAcceptPO(long l)
    {
        nBizAcceptPO = l;
        putUsedField("nBizAcceptPO", nBizAcceptPO);
    }

    /**
     * @param 
     * function �õ�/����
     * return double
     */
    public double getDiscountRate()
    {
        return mDiscountRate;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setDiscountRate(double d)
    {
        mDiscountRate = d;
        putUsedField("mDiscountRate", mDiscountRate);
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getNextCheckLevel()
    {
        return nNextCheckLevel;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setNextCheckLevel(long l)
    {
        nNextCheckLevel = l;
        putUsedField("nNextCheckLevel", nNextCheckLevel);
    }

    /**
     * @param 
     * function �õ�/����
     * return Collection
     */
    public Collection getDiscountBill()
    {
        return DiscountBill;
    }

    /**
     * @param 
     * function �õ�/����
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

/*
 * ContractInfo.java
 *
 * Created on 2002��2��20��; ����9:39
 */

package com.iss.itreasury.settlement.settcontract.dataentity;

import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.util.*;

/**
 *
 * @author  yzhang
 * @version
 */
public class SettContractInfo extends SettlementBaseDataEntity
{
	//��ͬID
    private long Id = -1;
    //����ID
    private long nLoanID = -1;
    //��ͬ���
    private String sContractCode = "";
    //�Ƿ�չ��
    private long isExtend = -1;
    //��������
    private Timestamp dtActive = null;
    //��������
    private long nTypeID = -1;
    //����С��
    private long nSubTypeID = -1;
    //����
    private long nCurrencyID = -1;
    //���´�
    private long nOfficeID = -1;
    //�����
    private String sApplyCode = "";
    //ί�е�λ
    private long nConsignClientID = -1;
    //��λ
    private long nBorrowClientID = -1;
    //������
    private double mLoanAmount = 0;
    //���ԭ��
    private String sLoanReason = "";
    //���Ŀ��
    private String sLoanPurpose = "";
    //����
    private String sOther = "";
    //�Ƿ�ѭ��
    private long nIsCircle = -1;
    //�Ƿ�����������? ���ǡ�������
    private long nIsSaleBuy = -1;
    //�Ƿ񼼸Ĵ���  ����
    private long nIsTechnical = -1;
    //¼����
    private long nInputUserID = -1;
    //¼��ʱ��
    private Timestamp dtInputDate = null;
    //�Ƿ����ñ�֤  ����
    private long nIsCredit = -1;
    //�Ƿ񵣱�
    private long nIsAssure = -1;
    //�Ƿ���Ѻ
    private long nIsImpawn = -1;
    //�Ƿ��Ѻ
    private long nIsPledge = -1;
    //��Ϣ����
    private long nInterestTypeID = -1;
    //��׼���
    private double mExamineAmount = 0;
    //����
    private long nIntervalNum = -1;
    //��������ID
    private long nBankInterestID = -1;
    //״̬
    private long nStatusID = -1;
    //�����
    private long nNextCheckUserID = -1;
    //�������ʣ����ţ�������ʣ�
    private double mChargeRate = 0;
    //���ʼʱ��
    private Timestamp dtStartDate = null;
    //�������ʱ��
    private Timestamp dtEndDate = null;
    //�Ƿ��ܱ��޸�
    private long isCanModify = -1;
    //������������
    private long nChargeRateTypeID = -1;
    //��λ������
    private String sClientInfo = "";
    //����˾�д����
    private double mSelfAmount = 0;
    //����״̬
    private long nRiskLevel = -1;
    //����������-����
    private long nTypeID1 = -1;
    //����ҵ����1-����
    private long nTypeID2 = -1;
    //����ҵ����2-����
    private long nTypeID3 = -1;
    //���гжһ�Ʊ��������
    private long nBankAcceptPo = -1;
    //��ҵ�жһ�Ʊ��������
    private long nBizAcceptPo = -1;
    //�������ֺ˶����
    private double mCheckAmount = 0;
    //����������Ϣ
    private double mInterestAmount = 0;
    //��������
    private double mDiscountRate = 0;
    //����ʱ��
    private Timestamp dtDiscountDate = null;
    //��������(ί�д���ʹ��)
    private double mInterestRate = 0;
    //��������
    private double mAdjustRate = 0;
    //��ת��ծȨ���(֤ȯϵͳʹ��)
    private double lastAttornmentAmount = 0;
    //��һ����������
    private long nNextCheckLevel = -1;
    //�̶���������
    private double mStaidAdjustRate = 0;
    //ת�������뻹������
    private long nInOrOut = -1;
    //ת��������
    private long nDiscountTypeID = -1;
    //�ع�����
    private long nRepurchaseTypeID = -1;
    //��������
    private long isLowLevel = -1;
    //ִ������
    private double executeInterestRate = 0;
    //�Ƿ��򷽸�Ϣ
    private long isPurchaserInterest = -1;
    //�򷽸�Ϣ����
    private double purchaserInterestRate = 0.0;
    //��ͬ�����Ϣ
    private SettContractAmountInfo settContractAmountInfo = new SettContractAmountInfo();
    
    private long recordCount = 0; //��¼��
	private long pageCount = 0; //ҳ��
	    
    /**
     * @return Returns the active.
     */
    public Timestamp getActive()
    {
        return dtActive;
    }
    /**
     * @param active The active to set.
     */
    public void setActive(Timestamp active)
    {
        dtActive = active;
        putUsedField("dtActive", dtActive);
    }
    /**
     * @return Returns the discountDate.
     */
    public Timestamp getDiscountDate()
    {
        return dtDiscountDate;
    }
    /**
     * @param discountDate The discountDate to set.
     */
    public void setDiscountDate(Timestamp discountDate)
    {
        dtDiscountDate = discountDate;
        putUsedField("dtDiscountDate", dtDiscountDate);
    }
    /**
     * @return Returns the endDdate.
     */
    public Timestamp getEndDate()
    {
        return dtEndDate;
    }
    /**
     * @param endDdate The endDdate to set.
     */
    public void setEndDate(Timestamp endDate)
    {
        dtEndDate = endDate;
        putUsedField("dtEndDate", dtEndDate);
    }
    /**
     * @return Returns the inputDate.
     */
    public Timestamp getInputDate()
    {
        return dtInputDate;
    }
    /**
     * @param inputDate The inputDate to set.
     */
    public void setInputDate(Timestamp inputDate)
    {
        dtInputDate = inputDate;
        putUsedField("dtInputDate", dtInputDate);
    }
    /**
     * @return Returns the startDate.
     */
    public Timestamp getStartDate()
    {
        return dtStartDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Timestamp startDate)
    {
        dtStartDate = startDate;
        putUsedField("dtStartDate", dtStartDate);
    }
    /**
     * @return Returns the iD.
     */
    public long getId()
    {
        return Id;
    }
    /**
     * @param id The iD to set.
     */
    public void setId(long id)
    {
        Id = id;
        putUsedField("Id", Id);
    }
    /**
     * @return Returns the isCanModify.
     */
    public long getIsCanModify()
    {
        return isCanModify;
    }
    /**
     * @param isCanModify The isCanModify to set.
     */
    public void setIsCanModify(long isCanModify)
    {
        this.isCanModify = isCanModify;
        putUsedField("isCanModify", this.isCanModify);
    }
    /**
     * @return Returns the isExtend.
     */
    public long getIsExtend()
    {
        return isExtend;
    }
    /**
     * @param isExtend The isExtend to set.
     */
    public void setIsExtend(long isExtend)
    {
        this.isExtend = isExtend;
        putUsedField("isExtend", this.isExtend);
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
        putUsedField("isLowLevel", this.isLowLevel);
    }
    /**
     * @return Returns the lastAttornmentAmount.
     */
    public double getLastAttornmentAmount()
    {
        return lastAttornmentAmount;
    }
    /**
     * @param lastAttornmentAmount The lastAttornmentAmount to set.
     */
    public void setLastAttornmentAmount(double lastAttornmentAmount)
    {
        this.lastAttornmentAmount = lastAttornmentAmount;
        putUsedField("lastAttornmentAmount", this.lastAttornmentAmount);
    }
    /**
     * @return Returns the adjustRate.
     */
    public double getAdjustRate()
    {
        return mAdjustRate;
    }
    /**
     * @param adjustRate The adjustRate to set.
     */
    public void setAdjustRate(double adjustRate)
    {
        mAdjustRate = adjustRate;
        putUsedField("mAdjustRate", mAdjustRate);
    }
    /**
     * @return Returns the chargeRate.
     */
    public double getChargeRate()
    {
        return mChargeRate;
    }
    /**
     * @param chargeRate The chargeRate to set.
     */
    public void setChargeRate(double chargeRate)
    {
        mChargeRate = chargeRate;
        putUsedField("mChargeRate", mChargeRate);
    }
    /**
     * @return Returns the checkAmount.
     */
    public double getCheckAmount()
    {
        return mCheckAmount;
    }
    /**
     * @param checkAmount The checkAmount to set.
     */
    public void setCheckAmount(double checkAmount)
    {
        mCheckAmount = checkAmount;
        putUsedField("mCheckAmount", mCheckAmount);
    }
    /**
     * @return Returns the discountRate.
     */
    public double getDiscountRate()
    {
        return mDiscountRate;
    }
    /**
     * @param discountRate The discountRate to set.
     */
    public void setDiscountRate(double discountRate)
    {
        mDiscountRate = discountRate;
        putUsedField("mDiscountRate", mDiscountRate);
    }
    /**
     * @return Returns the examineAmount.
     */
    public double getExamineAmount()
    {
        return mExamineAmount;
    }
    /**
     * @param examineAmount The examineAmount to set.
     */
    public void setExamineAmount(double examineAmount)
    {
        mExamineAmount = examineAmount;
        putUsedField("mExamineAmount", mExamineAmount);
    }
    /**
     * @return Returns the interestRate.
     */
    public double getInterestRate()
    {
        return mInterestRate;
    }
    /**
     * @param interestRate The interestRate to set.
     */
    public void setInterestRate(double interestRate)
    {
        mInterestRate = interestRate;
        putUsedField("mInterestRate", mInterestRate);
    }
    /**
     * @return Returns the loanAmount.
     */
    public double getLoanAmount()
    {
        return mLoanAmount;
    }
    /**
     * @param loanAmount The loanAmount to set.
     */
    public void setLoanAmount(double loanAmount)
    {
        mLoanAmount = loanAmount;
        putUsedField("mLoanAmount", mLoanAmount);
    }
    /**
     * @return Returns the selfAmount.
     */
    public double getSelfAmount()
    {
        return mSelfAmount;
    }
    /**
     * @param selfAmount The selfAmount to set.
     */
    public void setSelfAmount(double selfAmount)
    {
        mSelfAmount = selfAmount;
        putUsedField("mSelfAmount", mSelfAmount);
    }
    /**
     * @return Returns the staidAdjustRate.
     */
    public double getStaidAdjustRate()
    {
        return mStaidAdjustRate;
    }
    /**
     * @param staidAdjustRate The staidAdjustRate to set.
     */
    public void setStaidAdjustRate(double staidAdjustRate)
    {
        mStaidAdjustRate = staidAdjustRate;
        putUsedField("mStaidAdjustRate", mStaidAdjustRate);
    }
    /**
     * @return Returns the bankAcceptPo.
     */
    public long getBankAcceptPo()
    {
        return nBankAcceptPo;
    }
    /**
     * @param bankAcceptPo The bankAcceptPo to set.
     */
    public void setBankAcceptPo(long bankAcceptPo)
    {
        nBankAcceptPo = bankAcceptPo;
        putUsedField("nBankAcceptPo", nBankAcceptPo);
    }
    /**
     * @return Returns the bankInterestID.
     */
    public long getBankInterestID()
    {
        return nBankInterestID;
    }
    /**
     * @param bankInterestID The bankInterestID to set.
     */
    public void setBankInterestID(long bankInterestID)
    {
        nBankInterestID = bankInterestID;
        putUsedField("nBankInterestID", nBankInterestID);
    }
    /**
     * @return Returns the bizAcceptPo.
     */
    public long getBizAcceptPo()
    {
        return nBizAcceptPo;
    }
    /**
     * @param bizAcceptPo The bizAcceptPo to set.
     */
    public void setBizAcceptPo(long bizAcceptPo)
    {
        nBizAcceptPo = bizAcceptPo;
        putUsedField("nBizAcceptPo", nBizAcceptPo);
    }
    /**
     * @return Returns the borrowClientID.
     */
    public long getBorrowClientID()
    {
        return nBorrowClientID;
    }
    /**
     * @param borrowClientID The borrowClientID to set.
     */
    public void setBorrowClientID(long borrowClientID)
    {
        nBorrowClientID = borrowClientID;
        putUsedField("nBorrowClientID", nBorrowClientID);
    }
    /**
     * @return Returns the chargeRateTypeID.
     */
    public long getChargeRateTypeID()
    {
        return nChargeRateTypeID;
    }
    /**
     * @param chargeRateTypeID The chargeRateTypeID to set.
     */
    public void setChargeRateTypeID(long chargeRateTypeID)
    {
        nChargeRateTypeID = chargeRateTypeID;
        putUsedField("nChargeRateTypeID", nChargeRateTypeID);
    }
    /**
     * @return Returns the consignClientID.
     */
    public long getConsignClientID()
    {
        return nConsignClientID;
    }
    /**
     * @param consignClientID The consignClientID to set.
     */
    public void setConsignClientID(long consignClientID)
    {
        nConsignClientID = consignClientID;
        putUsedField("nConsignClientID", nConsignClientID);
    }
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return nCurrencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        nCurrencyID = currencyID;
        putUsedField("nCurrencyID", nCurrencyID);
    }
    /**
     * @return Returns the discountTypeID.
     */
    public long getDiscountTypeID()
    {
        return nDiscountTypeID;
    }
    /**
     * @param discountTypeID The discountTypeID to set.
     */
    public void setDiscountTypeID(long discountTypeID)
    {
        nDiscountTypeID = discountTypeID;
        putUsedField("nDiscountTypeID", nDiscountTypeID);
    }
    /**
     * @return Returns the inOrOut.
     */
    public long getInOrOut()
    {
        return nInOrOut;
    }
    /**
     * @param inOrOut The inOrOut to set.
     */
    public void setInOrOut(long inOrOut)
    {
        nInOrOut = inOrOut;
        putUsedField("nInOrOut", nInOrOut);
    }
    /**
     * @return Returns the inputUserID.
     */
    public long getInputUserID()
    {
        return nInputUserID;
    }
    /**
     * @param inputUserID The inputUserID to set.
     */
    public void setInputUserID(long inputUserID)
    {
        nInputUserID = inputUserID;
        putUsedField("nInputUserID", nInputUserID);
    }
    /**
     * @return Returns the interestTypeID.
     */
    public long getInterestTypeID()
    {
        return nInterestTypeID;
    }
    /**
     * @param interestTypeID The interestTypeID to set.
     */
    public void setInterestTypeID(long interestTypeID)
    {
        nInterestTypeID = interestTypeID;
        putUsedField("nInterestTypeID", nInterestTypeID);
    }
    /**
     * @return Returns the intervalNum.
     */
    public long getIntervalNum()
    {
        return nIntervalNum;
    }
    /**
     * @param intervalNum The intervalNum to set.
     */
    public void setIntervalNum(long intervalNum)
    {
        nIntervalNum = intervalNum;
        putUsedField("nIntervalNum", nIntervalNum);
    }
    /**
     * @return Returns the isAssure.
     */
    public long getIsAssure()
    {
        return nIsAssure;
    }
    /**
     * @param isAssure The isAssure to set.
     */
    public void setIsAssure(long isAssure)
    {
        nIsAssure = isAssure;
        putUsedField("nIsAssure", nIsAssure);
    }
    /**
     * @return Returns the isCircle.
     */
    public long getIsCircle()
    {
        return nIsCircle;
    }
    /**
     * @param isCircle The isCircle to set.
     */
    public void setIsCircle(long isCircle)
    {
        nIsCircle = isCircle;
        putUsedField("nIsCircle", nIsCircle);
    }
    /**
     * @return Returns the isCredit.
     */
    public long getIsCredit()
    {
        return nIsCredit;
    }
    /**
     * @param isCredit The isCredit to set.
     */
    public void setIsCredit(long isCredit)
    {
        nIsCredit = isCredit;
        putUsedField("nIsCredit", nIsCredit);
    }
    /**
     * @return Returns the isImpawn.
     */
    public long getIsImpawn()
    {
        return nIsImpawn;
    }
    /**
     * @param isImpawn The isImpawn to set.
     */
    public void setIsImpawn(long isImpawn)
    {
        nIsImpawn = isImpawn;
        putUsedField("nIsImpawn", nIsImpawn);
    }
    /**
     * @return Returns the isPledge.
     */
    public long getIsPledge()
    {
        return nIsPledge;
    }
    /**
     * @param isPledge The isPledge to set.
     */
    public void setIsPledge(long isPledge)
    {
        nIsPledge = isPledge;
        putUsedField("nIsPledge", nIsPledge);
    }
    /**
     * @return Returns the isSaleBuy.
     */
    public long getIsSaleBuy()
    {
        return nIsSaleBuy;
    }
    /**
     * @param isSaleBuy The isSaleBuy to set.
     */
    public void setIsSaleBuy(long isSaleBuy)
    {
        nIsSaleBuy = isSaleBuy;
        putUsedField("nIsSaleBuy", nIsSaleBuy);
    }
    /**
     * @return Returns the isTechnical.
     */
    public long getIsTechnical()
    {
        return nIsTechnical;
    }
    /**
     * @param isTechnical The isTechnical to set.
     */
    public void setIsTechnical(long isTechnical)
    {
        nIsTechnical = isTechnical;
        putUsedField("nIsTechnical", nIsTechnical);
    }
    /**
     * @return Returns the loanID.
     */
    public long getLoanID()
    {
        return nLoanID;
    }
    /**
     * @param loanID The loanID to set.
     */
    public void setLoanID(long loanID)
    {
        nLoanID = loanID;
        putUsedField("nLoanID", nLoanID);
    }
    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return nNextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        nNextCheckLevel = nextCheckLevel;
        putUsedField("nNextCheckLevel", nNextCheckLevel);
    }
    /**
     * @return Returns the nextCheckUserID.
     */
    public long getNextCheckUserID()
    {
        return nNextCheckUserID;
    }
    /**
     * @param nextCheckUserID The nextCheckUserID to set.
     */
    public void setNextCheckUserID(long nextCheckUserID)
    {
        nNextCheckUserID = nextCheckUserID;
        putUsedField("nNextCheckUserID", nNextCheckUserID);
    }
    /**
     * @return Returns the officeID.
     */
    public long getOfficeID()
    {
        return nOfficeID;
    }
    /**
     * @param officeID The officeID to set.
     */
    public void setOfficeID(long officeID)
    {
        nOfficeID = officeID;
        putUsedField("nOfficeID", nOfficeID);
    }
    /**
     * @return Returns the repurchaseTypeID.
     */
    public long getRepurchaseTypeID()
    {
        return nRepurchaseTypeID;
    }
    /**
     * @param repurchaseTypeID The repurchaseTypeID to set.
     */
    public void setRepurchaseTypeID(long repurchaseTypeID)
    {
        nRepurchaseTypeID = repurchaseTypeID;
        putUsedField("nRepurchaseTypeID", nRepurchaseTypeID);
    }
    /**
     * @return Returns the riskLevel.
     */
    public long getRiskLevel()
    {
        return nRiskLevel;
    }
    /**
     * @param riskLevel The riskLevel to set.
     */
    public void setRiskLevel(long riskLevel)
    {
        nRiskLevel = riskLevel;
        putUsedField("nRiskLevel", nRiskLevel);
    }
    /**
     * @return Returns the statusID.
     */
    public long getStatusID()
    {
        return nStatusID;
    }
    /**
     * @param statusID The statusID to set.
     */
    public void setStatusID(long statusID)
    {
        nStatusID = statusID;
        putUsedField("nStatusID", nStatusID);
    }
    /**
     * @return Returns the typeID.
     */
    public long getTypeID()
    {
        return nTypeID;
    }
    /**
     * @param typeID The typeID to set.
     */
    public void setTypeID(long typeID)
    {
        nTypeID = typeID;
        putUsedField("nTypeID", nTypeID);
    }
    /**
     * @return Returns the typeID1.
     */
    public long getTypeID1()
    {
        return nTypeID1;
    }
    /**
     * @param typeID1 The typeID1 to set.
     */
    public void setTypeID1(long typeID1)
    {
        nTypeID1 = typeID1;
        putUsedField("nTypeID1", nTypeID1);
    }
    /**
     * @return Returns the typeID2.
     */
    public long getTypeID2()
    {
        return nTypeID2;
    }
    /**
     * @param typeID2 The typeID2 to set.
     */
    public void setTypeID2(long typeID2)
    {
        nTypeID2 = typeID2;
        putUsedField("nTypeID2", nTypeID2);
    }
    /**
     * @return Returns the typeID3.
     */
    public long getTypeID3()
    {
        return nTypeID3;
    }
    /**
     * @param typeID3 The typeID3 to set.
     */
    public void setTypeID3(long typeID3)
    {
        nTypeID3 = typeID3;
        putUsedField("nTypeID3", nTypeID3);
    }
    /**
     * @return Returns the applyCode.
     */
    public String getApplyCode()
    {
        return sApplyCode;
    }
    /**
     * @param applyCode The applyCode to set.
     */
    public void setApplyCode(String applyCode)
    {
        sApplyCode = applyCode;
        putUsedField("sApplyCode", sApplyCode);
    }
    /**
     * @return Returns the clientInfo.
     */
    public String getClientInfo()
    {
        return sClientInfo;
    }
    /**
     * @param clientInfo The clientInfo to set.
     */
    public void setClientInfo(String clientInfo)
    {
        sClientInfo = clientInfo;
        putUsedField("sClientInfo", sClientInfo);
    }
    /**
     * @return Returns the contractCode.
     */
    public String getContractCode()
    {
        return sContractCode;
    }
    /**
     * @param contractCode The contractCode to set.
     */
    public void setContractCode(String contractCode)
    {
        sContractCode = contractCode;
        putUsedField("sContractCode", sContractCode);
    }
    /**
     * @return Returns the loanPurpose.
     */
    public String getLoanPurpose()
    {
        return sLoanPurpose;
    }
    /**
     * @param loanPurpose The loanPurpose to set.
     */
    public void setLoanPurpose(String loanPurpose)
    {
        sLoanPurpose = loanPurpose;
        putUsedField("sLoanPurpose", sLoanPurpose);
    }
    /**
     * @return Returns the loanReason.
     */
    public String getLoanReason()
    {
        return sLoanReason;
    }
    /**
     * @param loanReason The loanReason to set.
     */
    public void setLoanReason(String loanReason)
    {
        sLoanReason = loanReason;
        putUsedField("sLoanReason", sLoanReason);
    }
    /**
     * @return Returns the other.
     */
    public String getOther()
    {
        return sOther;
    }
    /**
     * @param other The other to set.
     */
    public void setOther(String other)
    {
        sOther = other;
        putUsedField("sOther", sOther);
    }
    /**
     * @return Returns the pageCount.
     */
    public long getPageCount()
    {
        return pageCount;
    }
    /**
     * @param pageCount The pageCount to set.
     */
    public void setPageCount(long pageCount)
    {
        this.pageCount = pageCount;
    }
    /**
     * @return Returns the recordCount.
     */
    public long getRecordCount()
    {
        return recordCount;
    }
    /**
     * @param recordCount The recordCount to set.
     */
    public void setRecordCount(long recordCount)
    {
        this.recordCount = recordCount;
    }
    /**
     * @return Returns the executeInterestRate.
     */
    public double getExecuteInterestRate()
    {
        return executeInterestRate;
    }
    /**
     * @param executeInterestRate The executeInterestRate to set.
     */
    public void setExecuteInterestRate(double executeInterestRate)
    {
        this.executeInterestRate = executeInterestRate;
    }
    /**
     * @return Returns the settContractAmountInfo.
     */
    public SettContractAmountInfo getSettContractAmountInfo()
    {
        return settContractAmountInfo;
    }
    /**
     * @param settContractAmountInfo The settContractAmountInfo to set.
     */
    public void setSettContractAmountInfo(
            SettContractAmountInfo settContractAmountInfo)
    {
        this.settContractAmountInfo = settContractAmountInfo;
    }
    /**
     * @return Returns the interestAmount.
     */
    public double getInterestAmount()
    {
        return mInterestAmount;
    }
    /**
     * @param interestAmount The interestAmount to set.
     */
    public void setInterestAmount(double interestAmount)
    {
        mInterestAmount = interestAmount;
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
		putUsedField("isPurchaserInterest", isPurchaserInterest);
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
		putUsedField("purchaserInterestRate", purchaserInterestRate);
	}
    /**
     * @return Returns the nSubTypeID.
     */
    public long getSubTypeID() {
        return nSubTypeID;
    }
    /**
     * @param subTypeID The nSubTypeID to set.
     */
    public void setSubTypeID(long subTypeID) {
        nSubTypeID = subTypeID;
        putUsedField("nSubTypeID", subTypeID);
    }
}

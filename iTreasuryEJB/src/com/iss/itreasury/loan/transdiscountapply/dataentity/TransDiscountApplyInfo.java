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
package com.iss.itreasury.loan.transdiscountapply.dataentity;

import java.sql.Timestamp;
import java.util.Collection;
import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 * @author yfan
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TransDiscountApplyInfo extends LoanBaseDataEntity {
	private long id = -1;

	// NTYPEID
	private long nTypeId = -1;

	// NCURRENCYID
	private long nSubtypeid = -1;// 贷款子类id

	private long nCurrencyId = -1;

	// NOFFICEID
	private long nOfficeId = -1;

	// SAPPLYCODE
	private String sApplyCode = "";

	// NCONSIGNCLIENTID
	private long nConsignClientId = -1;

	// NBORROWCLIENTID
	private long nBorrowClientId = -1;

	// MLOANAMOUNT
	private double mLoanAmount = 0;

	// SLOANREASON
	private String sLoanReason = "";

	// SLOANPURPOSE
	private String sLoanPurpose = "";

	// SOTHER
	private String sOther = "";

	// NISCIRCLE
	private long nIsCircle = -1;

	// NISSALEBUY
	private long nIsSaleBuy = -1;

	// NISTECHNICAL
	private long nIsTechnical = -1;

	// NINPUTUSERID
	private long nInputUserId = -1;

	// DTINPUTDATE
	private Timestamp dtInputDate = null;

	// NISCREDIT
	private long nIsCredit = -1;

	// NISASSURE
	private long nIsAssure = -1;

	// NISIMPAWN
	private long nIsImpawn = -1;

	// NISPLEDGE
	private long nIsPledgge = -1;

	// NINTERESTTYPEID
	private long nInterestTypeId = -1;

	// MEXAMINEAMOUNT
	private double mExamineAmount = 0;

	// NINTERVALNUM
	private long nIntervalNum = -1;

	// NBANKINTERESTID
	private long nBankInterestId = -1;

	// NSTATUSID
	private long nStatusId = -1;

	// NNEXTCHECKUSERID
	private long nNextCheckUserId = -1;

	// MCHARGERATE
	private double mChargeRate = 0;

	// DTSTARTDATE
	private Timestamp dtStartDate = null;

	// DTENDDATE
	private Timestamp dtEndDate = null;

	// ISCANMODIFY
	private long nIsCanModify = -1;

	// NCHARGERATETYPEID
	private long nChargeRateTypeId = -1;

	// SCLIENTINFO
	private String sClientInfo = "";

	// MSELFAMOUNT
	private double mSelfAmount = 0;

	// NRISKLEVEL
	private long nRiskLevel = -1;

	// NTYPEID1
	private long nTypeId1 = -1;

	// NTYPEID2
	private long nTypeId2 = -1;

	// NTYPEID3
	private long nTypeId3 = -1;

	//
	private long nAcceptPO = -1;

	// NBANKACCEPTPO
	private long nBankAcceptPO = -1;

	// NBIZACCEPTPO
	private long nBizAcceptPO = -1;

	// MCHECKAMOUNT
	private double mCheckAmount = 0;

	// MDISCOUNTRATE
	private double mDiscountRate = 0;

	// DTDISCOUNTDATE
	private Timestamp dtDiscountDate = null;

	// MINTERESTRATE
	private double mInterestRate = 0;

	// MADJUSTRATE
	private double mAdjustRate = 0;

	// NNEXTCHECKLEVEL
	private long nNextCheckLevel = -1;

	private long IsLowLevel = -1;

	// MSTAIDADJUSTRATE
	private double mStaidAdjustRate = 0;

	// NINOROUT
	private long nInOrOut = -1;

	// NDISCOUNTTYPEID
	private long nDiscountTypeId = -1;

	// NREPURCHASETYPEID
	private long nRepurchaseTypeId = -1;

	// 最终审核人－－中油
	private long sLastCheckUserId = -1;

	// private Collection Bill = null;

	private long recordCount = 0;// 总记录数

	private long pageCount = 0;// 总页数

	private long pageNo = 0;// 当前页码

	private double totalAmount = 0;// 总金额

	private long nIsLocal = 0;// 承兑方所在地(是否本地)

	private long nRepurchaseTerm = 0;// 回购期限

	private Timestamp dtRepurchaseDate = null;// 回购日期

	private long nIsChoose = 0;// 是否已对票据做处理

	private long nAddDays = 0;// 节假日增加计息天数

	private long nBillStatus = 0;// 票据模块使用的状态位

	private long nTransActionTypeId = 0;

	// added by xwhe 2007/09/10审批流
	private InutParameterInfo inutParameterInfo = null;

	// added by qhzhou 2007-09-25 关联附件
	private long billAttachId = -1; // 票据附件关联ID

	private long attachId = -1; // 附件关联ID
	
	private long creditId = -1;//授信关联ID
	
	private long actionId = -1; //交易类型id
	
	private long moduleId = -1;  //模块id

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public long getAttachId() {
		return attachId;
	}

	public void setAttachId(long attachId) {
		this.attachId = attachId;
		putUsedField("attachId", attachId);
	}

	/**
	 * @return Returns the id.
	 */
	public long getTransActionTypeId() {
		return nTransActionTypeId;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setTransActionTypeId(long nTransActionTypeId) {
		this.nTransActionTypeId = nTransActionTypeId;
		putUsedField("nTransActionTypeId", nTransActionTypeId);
	}

	/**
	 * @return Returns the id.
	 */
	public long getBillStatus() {
		return nBillStatus;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setBillStatus(long nBillStatus) {
		this.nBillStatus = nBillStatus;
		putUsedField("nBillStatus", nBillStatus);
	}

	/**
	 * @return Returns the id.
	 */
	public long getAddDays() {
		return nAddDays;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setAddDays(long nAddDays) {
		this.nAddDays = nAddDays;
		putUsedField("nAddDays", nAddDays);
	}

	/**
	 * @return Returns the id.
	 */
	public long getIsLocal() {
		return nIsLocal;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setIsLocal(long nIsLocal) {
		this.nIsLocal = nIsLocal;
		putUsedField("nIsLocal", nIsLocal);
	}

	/**
	 * @return Returns the id.
	 */
	public long getRepurchaseTerm() {
		return nRepurchaseTerm;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setRepurchaseTerm(long nRepurchaseTerm) {
		this.nRepurchaseTerm = nRepurchaseTerm;
		putUsedField("nRepurchaseTerm", nRepurchaseTerm);
	}

	/**
	 * @return Returns the id.
	 */
	public long getIsChoose() {
		return nIsChoose;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setIsChoose(long nIsChoose) {
		this.nIsChoose = nIsChoose;
		putUsedField("nIsChoose", nIsChoose);
	}

	/**
	 * @return Returns the id.
	 */
	public Timestamp getRepurchaseDate() {
		return dtRepurchaseDate;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setRepurchaseDate(Timestamp dtRepurchaseDate) {
		this.dtRepurchaseDate = dtRepurchaseDate;
		putUsedField("dtRepurchaseDate", dtRepurchaseDate);
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getTypeId() {
		return nTypeId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setTypeId(long l) {
		this.nTypeId = l;
		putUsedField("nTypeId", nTypeId);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getCurrencyId() {
		return nCurrencyId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setCurrencyId(long l) {
		this.nCurrencyId = l;
		putUsedField("nCurrencyId", nCurrencyId);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getOfficeId() {
		return nOfficeId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setOfficeId(long l) {
		this.nOfficeId = l;
		putUsedField("nOfficeId", nOfficeId);
	}

	/**
	 * @param function
	 *            得到/设置 return String
	 */
	public String getApplyCode() {
		return sApplyCode;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setApplyCode(String string) {
		this.sApplyCode = string;
		putUsedField("sApplyCode", sApplyCode);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getBorrowClientId() {
		return nBorrowClientId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setBorrowClientId(long l) {
		this.nBorrowClientId = l;
		putUsedField("nBorrowClientId", nBorrowClientId);
	}

	/**
	 * @param function
	 *            得到/设置 return double
	 */
	public double getLoanAmount() {
		return mLoanAmount;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setLoanAmount(double d) {
		this.mLoanAmount = d;
		putUsedField("mLoanAmount", mLoanAmount);
	}

	/**
	 * @param function
	 *            得到/设置 return String
	 */
	public String getLoanReason() {
		return sLoanReason;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setLoanReason(String string) {
		this.sLoanReason = string;
		putUsedField("sLoanReason", sLoanReason);
	}

	/**
	 * @param function
	 *            得到/设置 return String
	 */
	public String getLoanPurpose() {
		return sLoanPurpose;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setLoanPurpose(String string) {
		this.sLoanPurpose = string;
		putUsedField("sLoanPurpose", sLoanPurpose);
	}

	/**
	 * @param function
	 *            得到/设置 return Timestamp
	 */
	public Timestamp getDiscountDate() {
		return dtDiscountDate;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setDiscountDate(Timestamp timestamp) {
		this.dtDiscountDate = timestamp;
		putUsedField("dtDiscountDate", dtDiscountDate);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getBankAcceptPO() {
		return nBankAcceptPO;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setBankAcceptPO(long l) {
		this.nBankAcceptPO = l;
		putUsedField("nBankAcceptPO", nBankAcceptPO);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getBizAcceptPO() {
		return nBizAcceptPO;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setBizAcceptPO(long l) {
		this.nBizAcceptPO = l;
		putUsedField("nBizAcceptPO", nBizAcceptPO);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getInOrOut() {
		return nInOrOut;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setInOrOut(long l) {
		this.nInOrOut = l;
		putUsedField("nInOrOut", nInOrOut);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getDiscountTypeId() {
		return nDiscountTypeId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setDiscountTypeId(long l) {
		this.nDiscountTypeId = l;
		putUsedField("nDiscountTypeId", nDiscountTypeId);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getRepurchaseTypeId() {
		return nRepurchaseTypeId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setRepurchaseTypeId(long l) {
		this.nRepurchaseTypeId = l;
		putUsedField("nRepurchaseTypeId", nRepurchaseTypeId);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getInputUserId() {
		return nInputUserId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setInputUserId(long l) {
		nInputUserId = l;
		putUsedField("nInputUserId", nInputUserId);
	}

	/**
	 * @param function
	 *            得到/设置 return Timestamp
	 */
	public Timestamp getInputDate() {
		return dtInputDate;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setInputDate(Timestamp timestamp) {
		dtInputDate = timestamp;
		putUsedField("dtInputDate", dtInputDate);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getStatusId() {
		return nStatusId;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setStatusId(long l) {
		nStatusId = l;
		putUsedField("nStatusId", nStatusId);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getNextCheckUserId() {
		return nNextCheckUserId;
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getNextCheckLevel() {
		return nNextCheckLevel;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setNextCheckLevel(long l) {
		this.nNextCheckLevel = l;
		putUsedField("nNextCheckLevel", nNextCheckLevel);
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setNextCheckUserId(long l) {
		this.nNextCheckUserId = l;
		putUsedField("nNextCheckUserId", nNextCheckUserId);
	}

	/**
	 * @param function
	 *            得到/设置 return long
	 */
	public long getAcceptPO() {
		return nAcceptPO;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setAcceptPO(long l) {
		nAcceptPO = l;
	}

	/**
	 * @param function
	 *            得到/设置 return Collection / public Collection getBill() { return
	 *            Bill; }
	 * 
	 * /**
	 * @param function
	 *            得到/设置 return void / public void setBill(Collection collection) {
	 *            Bill = collection; }
	 * 
	 * /**
	 * @param function
	 *            得到/设置 return Timestamp
	 */
	public Timestamp getStartDate() {
		return dtStartDate;
	}

	/**
	 * @param function
	 *            得到/设置 return void
	 */
	public void setStartDate(Timestamp timestamp) {
		dtStartDate = timestamp;
		putUsedField("dtStartDate", dtStartDate);
	}

	/**
	 * @param return
	 *            Timestamp
	 */
	public Timestamp getEndDate() {
		return dtEndDate;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getAdjustRate() {
		return mAdjustRate;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getChargeRate() {
		return mChargeRate;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getCheckAmount() {
		return mCheckAmount;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getDiscountRate() {
		return mDiscountRate;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getExamineAmount() {
		return mExamineAmount;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getInterestRate() {
		return mInterestRate;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getSelfAmount() {
		return mSelfAmount;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getStaidAdjustRate() {
		return mStaidAdjustRate;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getBankInterestId() {
		return nBankInterestId;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getChargeRateTypeId() {
		return nChargeRateTypeId;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getConsignClientId() {
		return nConsignClientId;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getInterestTypeId() {
		return nInterestTypeId;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIntervalNum() {
		return nIntervalNum;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsAssure() {
		return nIsAssure;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsCanModify() {
		return nIsCanModify;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsCircle() {
		return nIsCircle;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsCredit() {
		return nIsCredit;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsImpawn() {
		return nIsImpawn;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsPledgge() {
		return nIsPledgge;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsSaleBuy() {
		return nIsSaleBuy;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getIsTechnical() {
		return nIsTechnical;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getRiskLevel() {
		return nRiskLevel;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getTypeId1() {
		return nTypeId1;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getTypeId2() {
		return nTypeId2;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getTypeId3() {
		return nTypeId3;
	}

	/**
	 * @param return
	 *            String
	 */
	public String getClientInfo() {
		return sClientInfo;
	}

	/**
	 * @param return
	 *            String
	 */
	public String getOther() {
		return sOther;
	}

	/**
	 * @param return
	 *            void
	 */
	public void setEndDate(Timestamp timestamp) {
		dtEndDate = timestamp;
		putUsedField("dtEndDate", dtEndDate);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setAdjustRate(double d) {
		mAdjustRate = d;
		putUsedField("mAdjustRate", mAdjustRate);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setChargeRate(double d) {
		mChargeRate = d;
		putUsedField("mChargeRate", mChargeRate);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setCheckAmount(double d) {
		mCheckAmount = d;
		putUsedField("mCheckAmount", mCheckAmount);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setDiscountRate(double d) {
		mDiscountRate = d;
		putUsedField("mDiscountRate", mDiscountRate);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setExamineAmount(double d) {
		mExamineAmount = d;
		putUsedField("mExamineAmount", mExamineAmount);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setInterestRate(double d) {
		mInterestRate = d;
		putUsedField("mInterestRate", mInterestRate);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setSelfAmount(double d) {
		mSelfAmount = d;
		putUsedField("mSelfAmount", mSelfAmount);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setStaidAdjustRate(double d) {
		mStaidAdjustRate = d;
		putUsedField("mStaidAdjustRate", mStaidAdjustRate);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setBankInterestId(long l) {
		nBankInterestId = l;
		putUsedField("nBankInterestId", nBankInterestId);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setChargeRateTypeId(long l) {
		nChargeRateTypeId = l;
		putUsedField("nChargeRateTypeId", nChargeRateTypeId);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setConsignClientId(long l) {
		nConsignClientId = l;
		putUsedField("nConsignClientId", nConsignClientId);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setInterestTypeId(long l) {
		nInterestTypeId = l;
		putUsedField("nInterestTypeId", nInterestTypeId);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIntervalNum(long l) {
		nIntervalNum = l;
		putUsedField("nIntervalNum", nIntervalNum);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsAssure(long l) {
		nIsAssure = l;
		putUsedField("nIsAssure", nIsAssure);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsCanModify(long l) {
		nIsCanModify = l;
		putUsedField("nIsCanModify", nIsCanModify);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsCircle(long l) {
		nIsCircle = l;
		putUsedField("nIsCircle", nIsCircle);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsCredit(long l) {
		nIsCredit = l;
		putUsedField("nIsCredit", nIsCredit);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsImpawn(long l) {
		nIsImpawn = l;
		putUsedField("nIsImpawn", nIsImpawn);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsPledgge(long l) {
		nIsPledgge = l;
		putUsedField("nIsPledgge", nIsPledgge);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsSaleBuy(long l) {
		nIsSaleBuy = l;
		putUsedField("nIsSaleBuy", nIsSaleBuy);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setIsTechnical(long l) {
		nIsTechnical = l;
		putUsedField("nIsTechnical", nIsTechnical);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setRiskLevel(long l) {
		nRiskLevel = l;
		putUsedField("nRiskLevel", nRiskLevel);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setTypeId1(long l) {
		nTypeId1 = l;
		putUsedField("nTypeId1", nTypeId1);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setTypeId2(long l) {
		nTypeId2 = l;
		putUsedField("nTypeId2", nTypeId2);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setTypeId3(long l) {
		nTypeId3 = l;
		putUsedField("nTypeId3", nTypeId3);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setClientInfo(String string) {
		sClientInfo = string;
		putUsedField("sClientInfo", sClientInfo);
	}

	/**
	 * @param return
	 *            void
	 */
	public void setOther(String string) {
		sOther = string;
		putUsedField("sOther", sOther);
	}

	/**
	 * @param return
	 *            long
	 */
	public long getPageCount() {
		return pageCount;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getPageNo() {
		return pageNo;
	}

	/**
	 * @param return
	 *            double
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param return
	 *            void
	 */
	public void setPageCount(long l) {
		pageCount = l;
	}

	/**
	 * @param return
	 *            void
	 */
	public void setPageNo(long l) {
		pageNo = l;
	}

	/**
	 * @param return
	 *            void
	 */
	public void setTotalAmount(double d) {
		totalAmount = d;
	}

	/**
	 * @param return
	 *            long
	 */
	public long getRecordCount() {
		return recordCount;
	}

	/**
	 * @param return
	 *            void
	 */
	public void setRecordCount(long l) {
		recordCount = l;
	}

	/**
	 * @return Returns the sLastCheckUserId.
	 */
	public long getLastCheckUserId() {
		return sLastCheckUserId;
	}

	/**
	 * @param lastCheckUserId
	 *            The sLastCheckUserId to set.
	 */
	public void setLastCheckUserId(long lastCheckUserId) {
		sLastCheckUserId = lastCheckUserId;
	}

	/**
	 * @return Returns the subTypeId.
	 */
	public long getNSubtypeid() {
		return nSubtypeid;
	}

	/**
	 * @param subTypeId
	 *            The subTypeId to set.
	 */
	public void setNSubtypeid(long subTypeId) {
		this.nSubtypeid = subTypeId;
		putUsedField("nSubTypeid", nSubtypeid);
	}

	/**
	 * @return Returns the isLowLevel.
	 */
	public long getIsLowLevel() {
		return IsLowLevel;
	}

	/**
	 * @param isLowLevel
	 *            The isLowLevel to set.
	 */
	public void setIsLowLevel(long isLowLevel) {
		IsLowLevel = isLowLevel;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	/**
	 * @return the billAttachId
	 */
	public long getBillAttachId() {
		return billAttachId;
	}

	/**
	 * @param billAttachId
	 *            the billAttachId to set
	 */
	public void setBillAttachId(long billAttachId) {
		this.billAttachId = billAttachId;
		putUsedField("billAttachId", billAttachId);
	}

	public long getCreditId() {
		return creditId;
	}

	public void setCreditId(long creditId) {
		this.creditId = creditId;
		putUsedField("creditId", creditId);
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
		//putUsedField("actionId", actionId);
	}
}

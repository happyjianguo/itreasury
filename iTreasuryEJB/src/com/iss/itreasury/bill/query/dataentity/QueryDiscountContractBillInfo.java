/*
 * Created on 2006-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.bill.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryDiscountContractBillInfo extends ITreasuryBaseDataEntity
{
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	private long id	= -1; //主键
	private Timestamp dtCreate = null;//出票日
	private Timestamp dtEnd    = null;//到期日
	private String sCode     = "";    //票据编号
	private double mAmount    = 0.0;  //票面金额
	private long nStatusID   = -1;    //票据有效、无效状态
	private long nOfficeID	 = -1;    //办事处
	private long nCurrencyID = -1;    //币种
	private long nModuleSourceID = -1; //票据信息来源，见常量Constant.ModuleType
	private long nDraftTypeID    = -1; //汇票类型
	private String strAcceptorName = "";//承兑人名称	
	private String strAcceptorBank	 = ""; //承兑人开户行
	private String strAcceptorAccount = ""; //承兑人帐号
	private long nStorageStatusID   = -1;   //出入入库状态，见常量BILLConstant.DraftInOrOut
	private long nStorageTransID    = -1;   //出库入库状态对应的出库入库交易id
	private long nQueryStatusID	    = -1;   //查复状态
	private long nConsignStatusID   = -1;   //托收状态
	private long nConsignTime       = -1;   //托收次数
	private long nInputUserID       = -1;   //录入人
	private Timestamp dtInputDate	 = null; //录入日期
	private long nModifyUserID      = -1;    //修改人
	private Timestamp dtModifyDate   = null; //修改日期
	private long nFormerConstatusID  = -1; //修改日期
	private long nBillStatusID = -1;
	
	private long nBillSourceTypeID = -1;

	/**
	 * @return Returns the dtCreate.
	 */
	public Timestamp getDtCreate() {
		return dtCreate;
	}
	/**
	 * @return Returns the dtEnd.
	 */
	public Timestamp getDtEnd() {
		return dtEnd;
	}
	/**
	 * @return Returns the dtInputDate.
	 */
	public Timestamp getDtInputDate() {
		return dtInputDate;
	}
	/**
	 * @return Returns the dtModifyDate.
	 */
	public Timestamp getDtModifyDate() {
		return dtModifyDate;
	}
	/**
	 * @return Returns the mAmount.
	 */
	public double getMAmount() {
		return mAmount;
	}
	/**
	 * @return Returns the nConsignStatusID.
	 */
	public long getNConsignStatusID() {
		return nConsignStatusID;
	}
	/**
	 * @return Returns the nConsignTime.
	 */
	public long getNConsignTime() {
		return nConsignTime;
	}
	/**
	 * @return Returns the nCurrencyID.
	 */
	public long getNCurrencyID() {
		return nCurrencyID;
	}
	/**
	 * @return Returns the nDraftTypeID.
	 */
	public long getNDraftTypeID() {
		return nDraftTypeID;
	}
	/**
	 * @return Returns the nInputUserID.
	 */
	public long getNInputUserID() {
		return nInputUserID;
	}
	/**
	 * @return Returns the nModifyUserID.
	 */
	public long getNModifyUserID() {
		return nModifyUserID;
	}
	/**
	 * @return Returns the nModuleSourceID.
	 */
	public long getNModuleSourceID() {
		return nModuleSourceID;
	}
	/**
	 * @return Returns the nOfficeID.
	 */
	public long getNOfficeID() {
		return nOfficeID;
	}
	/**
	 * @return Returns the nQueryStatusID.
	 */
	public long getNQueryStatusID() {
		return nQueryStatusID;
	}
	/**
	 * @return Returns the nStatusID.
	 */
	public long getNStatusID() {
		return nStatusID;
	}
	/**
	 * @return Returns the nStorageStatusID.
	 */
	public long getNStorageStatusID() {
		return nStorageStatusID;
	}
	/**
	 * @return Returns the nStorageTransID.
	 */
	public long getNStorageTransID() {
		return nStorageTransID;
	}
	/**
	 * @return Returns the sCode.
	 */
	public String getSCode() {
		return sCode;
	}
	/**
	 * @return Returns the strAcceptorAccount.
	 */
	public String getStrAcceptorAccount() {
		return strAcceptorAccount;
	}
	/**
	 * @return Returns the strAcceptorBank.
	 */
	public String getStrAcceptorBank() {
		return strAcceptorBank;
	}
	/**
	 * @return Returns the strAcceptorName.
	 */
	public String getStrAcceptorName() {
		return strAcceptorName;
	}

	/**
	 * @return Returns the nFormerConstatusID.
	 */
	public long getNFormerConstatusID() {
		return nFormerConstatusID;
	}
	/**
	 * @param dtCreate The dtCreate to set.
	 */
	public void setDtCreate(Timestamp dtCreate) {
		this.dtCreate = dtCreate;
		putUsedField("dtCreate", dtCreate);
	}
	/**
	 * @param dtEnd The dtEnd to set.
	 */
	public void setDtEnd(Timestamp dtEnd) {
		this.dtEnd = dtEnd;
		putUsedField("dtEnd", dtEnd);
	}
	/**
	 * @param dtInputDate The dtInputDate to set.
	 */
	public void setDtInputDate(Timestamp dtInputDate) {
		this.dtInputDate = dtInputDate;
		putUsedField("dtInputDate", dtInputDate);
	}
	/**
	 * @param dtModifyDate The dtModifyDate to set.
	 */
	public void setDtModifyDate(Timestamp dtModifyDate) {
		this.dtModifyDate = dtModifyDate;
		putUsedField("dtModifyDate", dtModifyDate);
	}
	/**
	 * @param amount The mAmount to set.
	 */
	public void setMAmount(double amount) {
		mAmount = amount;
		putUsedField("mAmount", mAmount);
	}
	/**
	 * @param consignStatusID The nConsignStatusID to set.
	 */
	public void setNConsignStatusID(long consignStatusID) {
		nConsignStatusID = consignStatusID;
		putUsedField("nConsignStatusID", nConsignStatusID);
	}
	/**
	 * @param consignTime The nConsignTime to set.
	 */
	public void setNConsignTime(long consignTime) {
		nConsignTime = consignTime;
		putUsedField("nConsignTime", nConsignTime);
	}
	/**
	 * @param currencyID The nCurrencyID to set.
	 */
	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
		putUsedField("nCurrencyID", nCurrencyID);
	}
	/**
	 * @param draftTypeID The nDraftTypeID to set.
	 */
	public void setNDraftTypeID(long draftTypeID) {
		nDraftTypeID = draftTypeID;
		putUsedField("nDraftTypeID", nDraftTypeID);
	}
	/**
	 * @param inputUserID The nInputUserID to set.
	 */
	public void setNInputUserID(long inputUserID) {
		nInputUserID = inputUserID;
		putUsedField("nInputUserID", nInputUserID);
	}
	/**
	 * @param modifyUserID The nModifyUserID to set.
	 */
	public void setNModifyUserID(long modifyUserID) {
		nModifyUserID = modifyUserID;
		putUsedField("nModifyUserID", nModifyUserID);
	}
	/**
	 * @param moduleSourceID The nModuleSourceID to set.
	 */
	public void setNModuleSourceID(long moduleSourceID) {
		nModuleSourceID = moduleSourceID;
		putUsedField("nModuleSourceID", nModuleSourceID);
	}
	/**
	 * @param officeID The nOfficeID to set.
	 */
	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
		putUsedField("nOfficeID", nOfficeID);
	}
	/**
	 * @param queryStatusID The nQueryStatusID to set.
	 */
	public void setNQueryStatusID(long queryStatusID) {
		nQueryStatusID = queryStatusID;
		putUsedField("nQueryStatusID", nQueryStatusID);
	}
	/**
	 * @param statusID The nStatusID to set.
	 */
	public void setNStatusID(long statusID) {
		nStatusID = statusID;
		putUsedField("nStatusID", nStatusID);
	}
	/**
	 * @param storageStatusID The nStorageStatusID to set.
	 */
	public void setNStorageStatusID(long storageStatusID) {
		nStorageStatusID = storageStatusID;
		putUsedField("nStorageStatusID", nStorageStatusID);
	}
	/**
	 * @param storageTransID The nStorageTransID to set.
	 */
	public void setNStorageTransID(long storageTransID) {
		nStorageTransID = storageTransID;
		putUsedField("nStorageTransID", nStorageTransID);
	}
	/**
	 * @param code The sCode to set.
	 */
	public void setSCode(String code) {
		sCode = code;
		putUsedField("sCode", sCode);
	}
	/**
	 * @param strAcceptorAccount The strAcceptorAccount to set.
	 */
	public void setStrAcceptorAccount(String strAcceptorAccount) {
		this.strAcceptorAccount = strAcceptorAccount;
		putUsedField("strAcceptorAccount", strAcceptorAccount);
	}
	/**
	 * @param strAcceptorBank The strAcceptorBank to set.
	 */
	public void setStrAcceptorBank(String strAcceptorBank) {
		this.strAcceptorBank = strAcceptorBank;
		putUsedField("strAcceptorBank", strAcceptorBank);
	}
	/**
	 * @param strAcceptorName The strAcceptorName to set.
	 */
	public void setStrAcceptorName(String strAcceptorName) {
		this.strAcceptorName = strAcceptorName;
		putUsedField("strAcceptorName", strAcceptorName);
	}
	/**
	 * @param formerConstatusID The nFormerConstatusID to set.
	 */
	public void setNFormerConstatusID(long formerConstatusID) {
		nFormerConstatusID = formerConstatusID;
		putUsedField("nFormerConstatusID", nFormerConstatusID);
	}
	public long getNBillStatusID() {
		return nBillStatusID;
	}
	public void setNBillStatusID(long billStatusID) {
		nBillStatusID = billStatusID;
		putUsedField("nBillStatusID", nBillStatusID);
	}
	public long getnBillSourceTypeID() {
		return nBillSourceTypeID;
	}
	public void setnBillSourceTypeID(long nBillSourceTypeID) {
		this.nBillSourceTypeID = nBillSourceTypeID;
	}
	
}

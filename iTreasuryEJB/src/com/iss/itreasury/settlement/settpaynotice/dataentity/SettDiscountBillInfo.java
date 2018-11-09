/*
 * SettDiscountBillInfo.java
 *
 * Created on 2002年4月8日, 下午2:40
 */

package com.iss.itreasury.settlement.settpaynotice.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
 *
 * @author  yzhang
 * @version 
 */
public class SettDiscountBillInfo extends SettlementBaseDataEntity
{
    /** Creates new SettDiscountBillInfo */
    public SettDiscountBillInfo() {
        super (  );   
    }     
    private long nContractID = -1;
    private long nSerialNo;           //序列号
    private String sUserName;        //原始出票人
    private String sBank;            //承兑银行
    private long nIsLocal;          //承兑银行所在地（是否北京）
    private Timestamp dtCreate = null;//出票日
    private Timestamp dtEnd    = null;//到期日
    private String sCode     = "";    //票据编号
    private double mAmount    = 0.0;  //票面金额
    private long nStatusID   = -1;    //票据有效、无效状态
    private long nAddDays	 = -1;
    private long nDiscountCredenceID = -1;
    private long ob_nDiscountCredenceID = -1;
    private long nAcceptPOTypeID = -1;
    private String sFormerOwner = "";
    private double mCheckAmount = 0.0;
    private long nBillSourceTypeID = -1;
    
    private long nOfficeID	 = -1;    //办事处
    private long nCurrencyID = -1;    //币种
    private long nModuleSourceID = -1; //票据信息来源，见常量Constant.ModuleType
    private long nDraftTypeID    = -1; //汇票类型
    private String strAcceptorName = "";//承兑人名称	
    private String strAcceptorBank	 = ""; //承兑人开户行
    private String strAcceptorAccount = ""; //承兑人账号
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
	/**
	 * @return Returns the dtCreate.
	 */
	public Timestamp getCreate()
	{
		return dtCreate;
	}
	/**
	 * @return Returns the dtEnd.
	 */
	public Timestamp getEnd()
	{
		return dtEnd;
	}
	/**
	 * @return Returns the dtInputDate.
	 */
	public Timestamp getInputDate()
	{
		return dtInputDate;
	}
	/**
	 * @return Returns the dtModifyDate.
	 */
	public Timestamp getModifyDate()
	{
		return dtModifyDate;
	}
	/**
	 * @return Returns the mAmount.
	 */
	public double getAmount()
	{
		return mAmount;
	}
	/**
	 * @return Returns the mCheckAmount.
	 */
	public double getCheckAmount()
	{
		return mCheckAmount;
	}
	/**
	 * @return Returns the nAcceptpoTypeID.
	 */
	public long getAcceptPOTypeID()
	{
		return nAcceptPOTypeID;
	}
	/**
	 * @return Returns the nAddDays.
	 */
	public long getAddDays()
	{
		return nAddDays;
	}
	/**
	 * @return Returns the nBillSourceTypeID.
	 */
	public long getBillSourceTypeID()
	{
		return nBillSourceTypeID;
	}
	/**
	 * @return Returns the nConsignStatusID.
	 */
	public long getConsignStatusID()
	{
		return nConsignStatusID;
	}
	/**
	 * @return Returns the nConsignTime.
	 */
	public long getConsignTime()
	{
		return nConsignTime;
	}
	/**
	 * @return Returns the nContractID.
	 */
	public long getContractID()
	{
		return nContractID;
	}
	/**
	 * @return Returns the nCurrencyID.
	 */
	public long getCurrencyID()
	{
		return nCurrencyID;
	}
	/**
	 * @return Returns the nDiscountCredenceID.
	 */
	public long getDiscountCredenceID()
	{
		return nDiscountCredenceID;
	}
	/**
	 * @return Returns the nDraftTypeID.
	 */
	public long getDraftTypeID()
	{
		return nDraftTypeID;
	}
	/**
	 * @return Returns the nFormerConstatusID.
	 */
	public long getFormerConstatusID()
	{
		return nFormerConstatusID;
	}
	/**
	 * @return Returns the nInputUserID.
	 */
	public long getInputUserID()
	{
		return nInputUserID;
	}
	/**
	 * @return Returns the nIsLocal.
	 */
	public long getIsLocal()
	{
		return nIsLocal;
	}
	/**
	 * @return Returns the nModifyUserID.
	 */
	public long getModifyUserID()
	{
		return nModifyUserID;
	}
	/**
	 * @return Returns the nModuleSourceID.
	 */
	public long getModuleSourceID()
	{
		return nModuleSourceID;
	}
	/**
	 * @return Returns the nOfficeID.
	 */
	public long getOfficeID()
	{
		return nOfficeID;
	}
	/**
	 * @return Returns the nQueryStatusID.
	 */
	public long getQueryStatusID()
	{
		return nQueryStatusID;
	}
	/**
	 * @return Returns the nSerialNo.
	 */
	public long getSerialNo()
	{
		return nSerialNo;
	}
	/**
	 * @return Returns the nStatusID.
	 */
	public long getStatusID()
	{
		return nStatusID;
	}
	/**
	 * @return Returns the nStorageStatusID.
	 */
	public long getStorageStatusID()
	{
		return nStorageStatusID;
	}
	/**
	 * @return Returns the nStorageTransID.
	 */
	public long getStorageTransID()
	{
		return nStorageTransID;
	}
	/**
	 * @return Returns the ob_nDiscountCredenceID.
	 */
	public long getOb_nDiscountCredenceID()
	{
		return ob_nDiscountCredenceID;
	}
	/**
	 * @return Returns the sBank.
	 */
	public String getBank()
	{
		return sBank;
	}
	/**
	 * @return Returns the sCode.
	 */
	public String getCode()
	{
		return sCode;
	}
	/**
	 * @return Returns the sFormerOwner.
	 */
	public String getFormerOwner()
	{
		return sFormerOwner;
	}
	/**
	 * @return Returns the strAcceptorAccount.
	 */
	public String gettrAcceptorAccount()
	{
		return strAcceptorAccount;
	}
	/**
	 * @return Returns the strAcceptorBank.
	 */
	public String gettrAcceptorBank()
	{
		return strAcceptorBank;
	}
	/**
	 * @return Returns the strAcceptorName.
	 */
	public String gettrAcceptorName()
	{
		return strAcceptorName;
	}
	/**
	 * @return Returns the sUserName.
	 */
	public String getUserName()
	{
		return sUserName;
	}
	/**
	 * @param dtCreate The dtCreate to set.
	 */
	public void setCreate(Timestamp dtCreate)
	{
		this.dtCreate = dtCreate;
		putUsedField("dtCreate", dtCreate);
	}
	/**
	 * @param dtEnd The dtEnd to set.
	 */
	public void setEnd(Timestamp dtEnd)
	{
		this.dtEnd = dtEnd;
		putUsedField("dtEnd", dtEnd);
	}
	/**
	 * @param dtInputDate The dtInputDate to set.
	 */
	public void setInputDate(Timestamp dtInputDate)
	{
		this.dtInputDate = dtInputDate;
		putUsedField("dtInputDate", dtInputDate);
	}
	/**
	 * @param dtModifyDate The dtModifyDate to set.
	 */
	public void setModifyDate(Timestamp dtModifyDate)
	{
		this.dtModifyDate = dtModifyDate;
		putUsedField("dtModifyDate", dtModifyDate);
	}
	/**
	 * @param amount The mAmount to set.
	 */
	public void setAmount(double amount)
	{
		mAmount = amount;
		putUsedField("mAmount", amount);
	}
	/**
	 * @param checkAmount The mCheckAmount to set.
	 */
	public void setCheckAmount(double checkAmount)
	{
		mCheckAmount = checkAmount;
		putUsedField("mCheckAmount", checkAmount);
	}
	/**
	 * @param acceptpoTypeID The nAcceptpoTypeID to set.
	 */
	public void setAcceptPOTypeID(long acceptpoTypeID)
	{
		nAcceptPOTypeID = acceptpoTypeID;
		putUsedField("nAcceptPOTypeID", acceptpoTypeID);
	}
	/**
	 * @param addDays The nAddDays to set.
	 */
	public void setAddDays(long addDays)
	{
		nAddDays = addDays;
		putUsedField("nAddDays", addDays);
	}
	/**
	 * @param billSourceTypeID The nBillSourceTypeID to set.
	 */
	public void setBillSourceTypeID(long billSourceTypeID)
	{
		nBillSourceTypeID = billSourceTypeID;
		putUsedField("nBillSourceTypeID", billSourceTypeID);
	}
	/**
	 * @param consignStatusID The nConsignStatusID to set.
	 */
	public void setConsignStatusID(long consignStatusID)
	{
		nConsignStatusID = consignStatusID;
		putUsedField("nConsignStatusID", consignStatusID);
	}
	/**
	 * @param consignTime The nConsignTime to set.
	 */
	public void setConsignTime(long consignTime)
	{
		nConsignTime = consignTime;
		putUsedField("nConsignTime", consignTime);
	}
	/**
	 * @param contractID The nContractID to set.
	 */
	public void setContractID(long contractID)
	{
		nContractID = contractID;
		putUsedField("nContractID", contractID);
	}
	/**
	 * @param currencyID The nCurrencyID to set.
	 */
	public void setCurrencyID(long currencyID)
	{
		nCurrencyID = currencyID;
		putUsedField("nCurrencyID", currencyID);
	}
	/**
	 * @param discountCredenceID The nDiscountCredenceID to set.
	 */
	public void setDiscountCredenceID(long discountCredenceID)
	{
		nDiscountCredenceID = discountCredenceID;
		putUsedField("nDiscountCredenceID", discountCredenceID);
	}
	/**
	 * @param draftTypeID The nDraftTypeID to set.
	 */
	public void setDraftTypeID(long draftTypeID)
	{
		nDraftTypeID = draftTypeID;
		putUsedField("nDraftTypeID", draftTypeID);
	}
	/**
	 * @param formerConstatusID The nFormerConstatusID to set.
	 */
	public void setFormerConstatusID(long formerConstatusID)
	{
		nFormerConstatusID = formerConstatusID;
		putUsedField("nFormerConstatusID", formerConstatusID);
	}
	/**
	 * @param inputUserID The nInputUserID to set.
	 */
	public void setInputUserID(long inputUserID)
	{
		nInputUserID = inputUserID;
		putUsedField("nInputUserID", inputUserID);
	}
	/**
	 * @param isLocal The nIsLocal to set.
	 */
	public void setIsLocal(long isLocal)
	{
		nIsLocal = isLocal;
		putUsedField("nIsLocal", isLocal);
	}
	/**
	 * @param modifyUserID The nModifyUserID to set.
	 */
	public void setModifyUserID(long modifyUserID)
	{
		nModifyUserID = modifyUserID;
		putUsedField("nModifyUserID", modifyUserID);
	}
	/**
	 * @param moduleSourceID The nModuleSourceID to set.
	 */
	public void setModuleSourceID(long moduleSourceID)
	{
		nModuleSourceID = moduleSourceID;
		putUsedField("nModuleSourceID", moduleSourceID);
	}
	/**
	 * @param officeID The nOfficeID to set.
	 */
	public void setOfficeID(long officeID)
	{
		nOfficeID = officeID;
		putUsedField("nOfficeID", officeID);
	}
	/**
	 * @param queryStatusID The nQueryStatusID to set.
	 */
	public void setQueryStatusID(long queryStatusID)
	{
		nQueryStatusID = queryStatusID;
		putUsedField("nQueryStatusID", queryStatusID);
	}
	/**
	 * @param serialNo The nSerialNo to set.
	 */
	public void setSerialNo(long serialNo)
	{
		nSerialNo = serialNo;
		putUsedField("nSerialNo", serialNo);
	}
	/**
	 * @param statusID The nStatusID to set.
	 */
	public void setStatusID(long statusID)
	{
		nStatusID = statusID;
		putUsedField("nStatusID", statusID);
	}
	/**
	 * @param storageStatusID The nStorageStatusID to set.
	 */
	public void setStorageStatusID(long storageStatusID)
	{
		nStorageStatusID = storageStatusID;
		putUsedField("nStorageStatusID", storageStatusID);
	}
	/**
	 * @param storageTransID The nStorageTransID to set.
	 */
	public void setStorageTransID(long storageTransID)
	{
		nStorageTransID = storageTransID;
		putUsedField("nStorageTransID", storageTransID);
	}
	/**
	 * @param ob_nDiscountCredenceID The ob_nDiscountCredenceID to set.
	 */
	public void setOb_nDiscountCredenceID(long ob_nDiscountCredenceID)
	{
		this.ob_nDiscountCredenceID = ob_nDiscountCredenceID;
		putUsedField("ob_nDiscountCredenceID", ob_nDiscountCredenceID);
	}
	/**
	 * @param bank The sBank to set.
	 */
	public void setBank(String bank)
	{
		sBank = bank;
		putUsedField("sBank", bank);
	}
	/**
	 * @param code The sCode to set.
	 */
	public void setCode(String code)
	{
		sCode = code;
		putUsedField("sCode", code);
	}
	/**
	 * @param formerOwner The sFormerOwner to set.
	 */
	public void setFormerOwner(String formerOwner)
	{
		sFormerOwner = formerOwner;
		putUsedField("sFormerOwner", formerOwner);
	}
	/**
	 * @param strAcceptorAccount The strAcceptorAccount to set.
	 */
	public void setStrAcceptorAccount(String strAcceptorAccount)
	{
		this.strAcceptorAccount = strAcceptorAccount;
		putUsedField("strAcceptorAccount", strAcceptorAccount);
	}
	/**
	 * @param strAcceptorBank The strAcceptorBank to set.
	 */
	public void setStrAcceptorBank(String strAcceptorBank)
	{
		this.strAcceptorBank = strAcceptorBank;
		putUsedField("strAcceptorBank", strAcceptorBank);
	}
	/**
	 * @param strAcceptorName The strAcceptorName to set.
	 */
	public void setStrAcceptorName(String strAcceptorName)
	{
		this.strAcceptorName = strAcceptorName;
		putUsedField("strAcceptorName", strAcceptorName);
	}
	/**
	 * @param userName The sUserName to set.
	 */
	public void setUserName(String userName)
	{
		sUserName = userName;
		putUsedField("sUserName", userName);
	}
}

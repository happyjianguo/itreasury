/*
 * SettDiscountBillInfo.java
 *
 * Created on 2002��4��8��, ����2:40
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
    private long nSerialNo;           //���к�
    private String sUserName;        //ԭʼ��Ʊ��
    private String sBank;            //�ж�����
    private long nIsLocal;          //�ж��������ڵأ��Ƿ񱱾���
    private Timestamp dtCreate = null;//��Ʊ��
    private Timestamp dtEnd    = null;//������
    private String sCode     = "";    //Ʊ�ݱ��
    private double mAmount    = 0.0;  //Ʊ����
    private long nStatusID   = -1;    //Ʊ����Ч����Ч״̬
    private long nAddDays	 = -1;
    private long nDiscountCredenceID = -1;
    private long ob_nDiscountCredenceID = -1;
    private long nAcceptPOTypeID = -1;
    private String sFormerOwner = "";
    private double mCheckAmount = 0.0;
    private long nBillSourceTypeID = -1;
    
    private long nOfficeID	 = -1;    //���´�
    private long nCurrencyID = -1;    //����
    private long nModuleSourceID = -1; //Ʊ����Ϣ��Դ��������Constant.ModuleType
    private long nDraftTypeID    = -1; //��Ʊ����
    private String strAcceptorName = "";//�ж�������	
    private String strAcceptorBank	 = ""; //�ж��˿�����
    private String strAcceptorAccount = ""; //�ж����˺�
    private long nStorageStatusID   = -1;   //�������״̬��������BILLConstant.DraftInOrOut
    private long nStorageTransID    = -1;   //�������״̬��Ӧ�ĳ�����⽻��id
    private long nQueryStatusID	    = -1;   //�鸴״̬
    private long nConsignStatusID   = -1;   //����״̬
    private long nConsignTime       = -1;   //���մ���
    private long nInputUserID       = -1;   //¼����
    private Timestamp dtInputDate	 = null; //¼������
    private long nModifyUserID      = -1;    //�޸���
    private Timestamp dtModifyDate   = null; //�޸�����
    private long nFormerConstatusID  = -1; //�޸�����
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

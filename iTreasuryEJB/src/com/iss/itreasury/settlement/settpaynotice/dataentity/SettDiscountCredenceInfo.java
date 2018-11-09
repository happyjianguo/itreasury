/*
 * Created on 2004-9-10
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.settpaynotice.dataentity;

import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.settlement.base.*;
import com.iss.itreasury.util.*;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettDiscountCredenceInfo extends ITreasuryBaseDataEntity
{
	private long id=-1;							
	private long nContractID=-1;			//����id
	private Timestamp dtInputDate=null;		//¼��ʱ��
	private long nDraftTypeID=-1;			//���ֻ�Ʊ����id
	private String sDraftCode="";			//���ֻ�Ʊ����
	private Timestamp dtPublicDate=null;	//��Ʊ��
	private Timestamp dtAtTerm=null;		//������
	private String sApplyClient="";			//��λ
	private String sApplyAccount="";		//���뵥λ�˻���
	private String sApplyBank="";			//���뵥λ��������
	private String sAcceptClient="";		//�жҵ�λ����
	private String sAcceptAccount="";		//�жҵ�λ�˻���
	private String sAcceptBank="";			//�жҵ�λ����
	private double mAmount=0;				//����ƾ֤���
	private double mRate=0;					//����
	private double mInterest=0;				//����ƾ֤��Ϣ
	private long nStatusID=-1;				//״̬
	private long nInputUserID=-1;			//¼����id
	private long nNextCheckUserID=-1;		//�����
	private Timestamp dtFillDate=null;		//��д����
	private String sCode="";				//��Ʊ����
	private long nGrantTypeID=-1;			//�ſʽ
	private long nAccountBankID=-1;			//��������id
	private String sReceiveAccount="";		//�տλ�˻���
	private String sReceiveClientName="";	//�տλ����
	private String sRemitBank="";			//������
	private String sRemitInProvince="";		//�����(ʡ)
	private String sRemitInCity="";			//�����(��)
	private long nGrantCurrentAccountID=-1; //�����������˻�
	private long nNextCheckLevel=-1;		//��һ����������
	private long nTypeID=-1;				//	
	private long nBillSourceTypeID=-1;		//	
	private long nOfficeID=-1;				//	
	private long nCurrencyID=-1;	
	private Timestamp dtRepurchaseDate=null;//		
	private long nTransDiscountCredenceID=-1;//		
	private long nRepurchaseTerm=-1;		//	
	
	private String sContractCode="";
	private double mContractAmount=0;
	private long nBorrowClientID=-1;
	private double purchaserInterest = 0;	// ��֧��ƾ֤��Ϣ 
		
	/**
	 * @return
	 */
	public Timestamp getAtTerm()
	{
		return dtAtTerm;
	}

	/**
	 * @return
	 */
	public Timestamp getFillDate()
	{
		return dtFillDate;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return dtInputDate;
	}

	/**
	 * @return
	 */
	public Timestamp getPublicDate()
	{
		return dtPublicDate;
	}

	/**
	 * @return
	 */
	public Timestamp getRepurchaseDate()
	{
		return dtRepurchaseDate;
	}



	/**
	 * @return
	 */
	public double getAmount()
	{
		return mAmount;
	}

	/**
	 * @return
	 */
	public double getInterest()
	{
		return mInterest;
	}

	/**
	 * @return
	 */
	public double getRate()
	{
		return mRate;
	}

	/**
	 * @return
	 */
	public long getAccountBankID()
	{
		return nAccountBankID;
	}

	/**
	 * @return
	 */
	public long getBillSourceTypeID()
	{
		return nBillSourceTypeID;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return nContractID;
	}

	/**
	 * @return
	 */
	public long getDraftTypeID()
	{
		return nDraftTypeID;
	}

	/**
	 * @return
	 */
	public long getGrantCurrentAccountID()
	{
		return nGrantCurrentAccountID;
	}

	/**
	 * @return
	 */
	public long getGrantTypeID()
	{
		return nGrantTypeID;
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return nInputUserID;
	}

	/**
	 * @return
	 */
	public long getNextCheckLevel()
	{
		return nNextCheckLevel;
	}

	/**
	 * @return
	 */
	public long getNextCheckUserID()
	{
		return nNextCheckUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return nOfficeID;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTerm()
	{
		return nRepurchaseTerm;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return nStatusID;
	}

	/**
	 * @return
	 */
	public long getTransDiscountCredenceID()
	{
		return nTransDiscountCredenceID;
	}

	/**
	 * @return
	 */
	public long getTypeID()
	{
		return nTypeID;
	}

	/**
	 * @return
	 */
	public String getAcceptAccount()
	{
		return sAcceptAccount;
	}

	/**
	 * @return
	 */
	public String getAcceptBank()
	{
		return sAcceptBank;
	}

	/**
	 * @return
	 */
	public String getAcceptClient()
	{
		return sAcceptClient;
	}

	/**
	 * @return
	 */
	public String getApplyAccount()
	{
		return sApplyAccount;
	}

	/**
	 * @return
	 */
	public String getApplyBank()
	{
		return sApplyBank;
	}

	/**
	 * @return
	 */
	public String getApplyClient()
	{
		return sApplyClient;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return sCode;
	}

	/**
	 * @return
	 */
	public String getDraftCode()
	{
		return sDraftCode;
	}

	/**
	 * @return
	 */
	public String getReceiveAccount()
	{
		return sReceiveAccount;
	}

	/**
	 * @return
	 */
	public String getReceiveClientName()
	{
		return sReceiveClientName;
	}

	/**
	 * @return
	 */
	public String getRemitBank()
	{
		return sRemitBank;
	}

	/**
	 * @return
	 */
	public String getRemitInCity()
	{
		return sRemitInCity;
	}

	/**
	 * @return
	 */
	public String getRemitInProvince()
	{
		return sRemitInProvince;
	}

	/**
	 * @param timestamp
	 */
	public void setAtTerm(Timestamp timestamp)
	{
		dtAtTerm = timestamp;
		putUsedField("dtAtTerm", dtAtTerm);
	}

	/**
	 * @param timestamp
	 */
	public void setFillDate(Timestamp timestamp)
	{
		dtFillDate = timestamp;
		putUsedField("dtFillDate", dtFillDate);
	}

	/**
	 * @param timestamp
	 */
	public void setInputDate(Timestamp timestamp)
	{
		dtInputDate = timestamp;
		putUsedField("dtInputDate", dtInputDate);
	}

	/**
	 * @param timestamp
	 */
	public void setPublicDate(Timestamp timestamp)
	{
		dtPublicDate = timestamp;
		putUsedField("dtPublicDate", dtPublicDate);
	}

	/**
	 * @param timestamp
	 */
	public void setRepurchaseDate(Timestamp timestamp)
	{
		dtRepurchaseDate = timestamp;
		putUsedField("dtRepurchaseDate", dtRepurchaseDate);
	}


	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		mAmount = d;
		putUsedField("mAmount", mAmount);
	}

	/**
	 * @param d
	 */
	public void setInterest(double d)
	{
		mInterest = d;
		putUsedField("mInterest", mInterest);
	}

	/**
	 * @param d
	 */
	public void setRate(double d)
	{
		mRate = d;
		putUsedField("mRate", mRate);
	}

	/**
	 * @param l
	 */
	public void setAccountBankID(long l)
	{
		nAccountBankID = l;
		putUsedField("nAccountBankID", nAccountBankID);
	}

	/**
	 * @param l
	 */
	public void setBillSourceTypeID(long l)
	{
		nBillSourceTypeID = l;
		putUsedField("nBillSourceTypeID", nBillSourceTypeID);
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		nContractID = l;
		putUsedField("nContractID", nContractID);
	}

	/**
	 * @param l
	 */
	public void setDraftTypeID(long l)
	{
		nDraftTypeID = l;
		putUsedField("nDraftTypeID", nDraftTypeID);
	}

	/**
	 * @param l
	 */
	public void setGrantCurrentAccountID(long l)
	{
		nGrantCurrentAccountID = l;
		putUsedField("nGrantCurrentAccountID", nGrantCurrentAccountID);
	}

	/**
	 * @param l
	 */
	public void setGrantTypeID(long l)
	{
		nGrantTypeID = l;
		putUsedField("nGrantTypeID", nGrantTypeID);
	}

	/**
	 * @param l
	 */
	public void setInputUserID(long l)
	{
		nInputUserID = l;
		putUsedField("nInputUserID", nInputUserID);
	}

	/**
	 * @param l
	 */
	public void setNextCheckLevel(long l)
	{
		nNextCheckLevel = l;
		putUsedField("nNextCheckLevel", nNextCheckLevel);
	}

	/**
	 * @param l
	 */
	public void setNextCheckUserID(long l)
	{
		nNextCheckUserID = l;
		putUsedField("nNextCheckUserID", nNextCheckUserID);
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		nOfficeID = l;
		putUsedField("nOfficeID", nOfficeID);
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTerm(long l)
	{
		nRepurchaseTerm = l;
		putUsedField("nRepurchaseTerm", nRepurchaseTerm);
	}

	/**
	 * @param l
	 */
	public void setStatusID(long l)
	{
		nStatusID = l;
		putUsedField("nStatusID", nStatusID);
	}

	/**
	 * @param l
	 */
	public void setTransDiscountCredenceID(long l)
	{
		nTransDiscountCredenceID = l;
		putUsedField("nTransDiscountCredenceID", nTransDiscountCredenceID);
	}

	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		nTypeID = l;
		putUsedField("nTypeID", nTypeID);
	}

	/**
	 * @param string
	 */
	public void setAcceptAccount(String string)
	{
		sAcceptAccount = string;
		putUsedField("sAcceptAccount", sAcceptAccount);
	}

	/**
	 * @param string
	 */
	public void setAcceptBank(String string)
	{
		sAcceptBank = string;
		putUsedField("sAcceptBank", sAcceptBank);
	}

	/**
	 * @param string
	 */
	public void setAcceptClient(String string)
	{
		sAcceptClient = string;
		putUsedField("sAcceptClient", sAcceptClient);
	}

	/**
	 * @param string
	 */
	public void setApplyAccount(String string)
	{
		sApplyAccount = string;
		putUsedField("sApplyAccount", sApplyAccount);
	}

	/**
	 * @param string
	 */
	public void setApplyBank(String string)
	{
		sApplyBank = string;
		putUsedField("sApplyBank", sApplyBank);
	}

	/**
	 * @param string
	 */
	public void setApplyClient(String string)
	{
		sApplyClient = string;
		putUsedField("sApplyClient", sApplyClient);
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		sCode = string;
		putUsedField("sCode", sCode);
	}

	/**
	 * @param string
	 */
	public void setDraftCode(String string)
	{
		sDraftCode = string;
		putUsedField("sDraftCode", sDraftCode);
	}

	/**
	 * @param string
	 */
	public void setReceiveAccount(String string)
	{
		sReceiveAccount = string;
		putUsedField("sReceiveAccount", sReceiveAccount);
	}

	/**
	 * @param string
	 */
	public void setReceiveClientName(String string)
	{
		sReceiveClientName = string;
		putUsedField("sReceiveClientName", sReceiveClientName);
	}

	/**
	 * @param string
	 */
	public void setRemitBank(String string)
	{
		sRemitBank = string;
		putUsedField("sRemitBank", sRemitBank);
	}

	/**
	 * @param string
	 */
	public void setRemitInCity(String string)
	{
		sRemitInCity = string;
		putUsedField("sRemitInCity", sRemitInCity);
	}

	/**
	 * @param string
	 */
	public void setRemitInProvince(String string)
	{
		sRemitInProvince = string;
		putUsedField("sRemitInProvince", sRemitInProvince);
	}

	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param l
	 */
	public void setId(long l)
	{
		id = l;
		putUsedField("id", id);
	}

	/**
	 * @return
	 */
	public double getContractAmount()
	{
		return mContractAmount;
	}

	/**
	 * @return
	 */
	public long getBorrowClientID()
	{
		return nBorrowClientID;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return sContractCode;
	}

	/**
	 * @param d
	 */
	public void setContractAmount(double d)
	{
		mContractAmount = d;
	}

	/**
	 * @param l
	 */
	public void setBorrowClientID(long l)
	{
		nBorrowClientID = l;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		sContractCode = string;
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
		putUsedField("purchaserInterest", purchaserInterest);
	}

	/**
	 * @return Returns the nCurrencyID.
	 */
	public long getCurrencyID() {
		return nCurrencyID;
	}

	/**
	 * @param currencyID The nCurrencyID to set.
	 */
	public void setCurrencyID(long l) {
		this.nCurrencyID=l;
		putUsedField("nCurrencyID", nCurrencyID);
	}
}

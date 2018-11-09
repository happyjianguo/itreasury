/*
 * Copyright (c) 1999-2002 ISoftStone. All Rights Reserved.
 *
 * ˵������������ƾ֤��Ϣ
 *
 * ���ߣ�ninh
 *
 * ������Ա��
 *
 */

package com.iss.itreasury.loan.obinterface.dataentity;

import java.beans.*;
import java.sql.*;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;

/**
 *
 * @author  yfan
 */
public class OBDiscountCredenceInfo extends Object implements java.io.Serializable {
    
    //������Ϣ
    private long DiscountID;                //����ID��ʶ
    private String DiscountCode;            //���ֱ��

    private long ApplyClientID;             //���뵥λ���
    private long ApplyOfficeID;             //���뵥λ�������´���ʾ
    private String ApplyOfficeName;         //���뵥λ�������´�����
    private String ApplyLOfficeAccount;     //���뵥λ�������´��˺�
    
    private ClientInfo ClientInfo;        //�ͻ���Ϣ
    
    private long DiscountStatusID;          //״̬
   
    private double DiscountApplyAmount;     //�������ֽ��
    private double DiscountExamineAmount;   //��׼���
    private double DiscountCheckAmount;     //�˶����
    private double DiscountRate;            //����
    private String DiscountPurpose;         //������;
    private String DiscountReason;          //����ԭ��
        
    private Timestamp DiscountDate;        	//���ּ�Ϣʱ��
    private Timestamp DiscountStartDate;   	//���ֿ�ʼʱ��
    private Timestamp DiscountEndDate;     	//���ֵ���ʱ��
  
    private long ApplyDiscountPO;           //�������ֻ�Ʊ��������
    private long BankAccepPO;               //���гжһ�Ʊ��������
    private long BizAcceptPO;               //��ҵ�жһ�Ʊ��������
    private double DiscountInterest;        //������Ϣ
 	private long DiscountBillCount;     	//��Ʊ������
    private double DiscountBillAmount;      //��Ʊ�ܽ��
    
    //����ƾ֤��Ϣ   
    private long 		ID;                 //����ƾ֤��ʶ
    private String      Code;               //����ƾ֤���
    private long        ContractID;         //����ƾ֤�ĺ�ͬ��ʶ
    private String      ContractCode;       //��ͬ���
    private String      LoanApplyCode;      //����������
    private long        InID;               //����ƾ֤�ڲ���ʶ
    private String      InCode;             //����ƾ֤�ڲ����
    private long 		DraftTypeID;        //���ֻ�Ʊ�����ʾ
    private String 		DraftTypeName;   	//���ֻ�Ʊ����
    private String 		DraftCode;       	//���ֻ�Ʊ����
    private Timestamp   FillDate;           //��д����
    private Timestamp 	PublicDate;  		//��Ʊ��
    private Timestamp 	AtTerm;      		//������
    private String      ApplyClientName;    //���뵥λ����
    private String      ApplyAccount;       //���뵥λ�˻���
    private String      ApplyBank;          //���뵥λ��������
    private long 		AcceptClientID;     //�жҵ�λ��ʾ
    private String 		AcceptClientName;	//�жҵ�λ����
    private String 		AcceptAccount;   	//�жҵ�λ�˻���
    private String 		AcceptBank;      	//�жҵ�λ����
    
    private double 		Amount;        	    //ƾ֤���
    private double      Rate;               //ƾ֤����
    private double 		Interest;      	    //ƾ֤��Ϣ
    private double 		CheckAmount;   	    //ƾ֤�˶����
    
    private int 		CurrencyID;        	//����
    private long 		OfficeID;          	//���´���ʾ
    private String 		OfficeName;        	//���´�����
    private String 		OfficeAccount;    	//���´��˺�
    private long 		StatusID;          	//״̬
    
    private long  		InputUserID;       	//¼���˱�ʾ
    private String 		InputUserName;     	//¼��������
	private Timestamp 	InputDate;        	//¼��ʱ��
    private long        NextCheckUserID;    //����˱�ʾ

    private long        GrantTypeID;        //�ſʽ
    private long        GrantCurrentAccountID;  //�����������˻�ID
    private String      GrantCurrentAccountCode;//�����������˻�
    private long        AccountBankID;      //��������ID
    private String      AccountBankCode;    //���������˻�
    private String      AccountBankName;    //��������

    private String      ReceiveAccount;     //�տλ�˺�
    private String      ReceiveClientName;  //�տλ
    
    private String      RemitBank;          //������
    private String      RemitProvince;      //����� ʡ
    private String      RemitCity;          //����� ��
    
	private long Desc;
	private String OrderParam;
	private long PageNo;
   

	/**
	 * Returns the acceptAccount.
	 * @return String
	 */
	public String getAcceptAccount()
	{
		return AcceptAccount;
	}

	/**
	 * Returns the acceptBank.
	 * @return String
	 */
	public String getAcceptBank()
	{
		return AcceptBank;
	}

	/**
	 * Returns the acceptClientID.
	 * @return long
	 */
	public long getAcceptClientID()
	{
		return AcceptClientID;
	}

	/**
	 * Returns the acceptClientName.
	 * @return String
	 */
	public String getAcceptClientName()
	{
		return AcceptClientName;
	}

	/**
	 * Returns the applyAccount.
	 * @return String
	 */
	public String getApplyAccount()
	{
		return ApplyAccount;
	}

	/**
	 * Returns the applyBank.
	 * @return String
	 */
	public String getApplyBank()
	{
		return ApplyBank;
	}

	/**
	 * Returns the applyClientID.
	 * @return long
	 */
	public long getApplyClientID()
	{
		return ApplyClientID;
	}

	/**
	 * Returns the applyClientName.
	 * @return String
	 */
	public String getApplyClientName()
	{
		return ApplyClientName;
	}

	/**
	 * Returns the applyDiscountPO.
	 * @return long
	 */
	public long getApplyDiscountPO()
	{
		return ApplyDiscountPO;
	}

	/**
	 * Returns the applyLOfficeAccount.
	 * @return String
	 */
	public String getApplyLOfficeAccount()
	{
		return ApplyLOfficeAccount;
	}

	/**
	 * Returns the applyOfficeID.
	 * @return long
	 */
	public long getApplyOfficeID()
	{
		return ApplyOfficeID;
	}

	/**
	 * Returns the applyOfficeName.
	 * @return String
	 */
	public String getApplyOfficeName()
	{
		return ApplyOfficeName;
	}

	/**
	 * Returns the atTerm.
	 * @return Timestamp
	 */
	public Timestamp getAtTerm()
	{
		return AtTerm;
	}

	/**
	 * Returns the bankAccepPO.
	 * @return long
	 */
	public long getBankAccepPO()
	{
		return BankAccepPO;
	}

	/**
	 * Returns the billAmount.
	 * @return double
	 */
	public double getAmount()
	{
		return Amount;
	}

	/**
	 * Returns the billCheckAmount.
	 * @return double
	 */
	public double getCheckAmount()
	{
		return CheckAmount;
	}

	/**
	 * Returns the billInterest.
	 * @return double
	 */
	public double getInterest()
	{
		return Interest;
	}

	/**
	 * Returns the bizAcceptPO.
	 * @return long
	 */
	public long getBizAcceptPO()
	{
		return BizAcceptPO;
	}

	/**
	 * Returns the clientInfo.
	 * @return OBClientInfo
	 */
	public ClientInfo getClientInfo()
	{
		return ClientInfo;
	}

	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode()
	{
		return Code;
	}

	/**
	 * Returns the currencyID.
	 * @return int
	 */
	public int getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * Returns the desc.
	 * @return long
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * Returns the discountApplyAmount.
	 * @return double
	 */
	public double getDiscountApplyAmount()
	{
		return DiscountApplyAmount;
	}

	/**
	 * Returns the discountBillAmount.
	 * @return double
	 */
	public double getDiscountBillAmount()
	{
		return DiscountBillAmount;
	}

	/**
	 * Returns the discountBillCount.
	 * @return long
	 */
	public long getDiscountBillCount()
	{
		return DiscountBillCount;
	}

	/**
	 * Returns the discountCheckAmount.
	 * @return double
	 */
	public double getDiscountCheckAmount()
	{
		return DiscountCheckAmount;
	}

	/**
	 * Returns the discountCode.
	 * @return String
	 */
	public String getDiscountCode()
	{
		return DiscountCode;
	}

	/**
	 * Returns the discountDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountDate()
	{
		return DiscountDate;
	}

	/**
	 * Returns the discountEndDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountEndDate()
	{
		return DiscountEndDate;
	}

	/**
	 * Returns the discountExamineAmount.
	 * @return double
	 */
	public double getDiscountExamineAmount()
	{
		return DiscountExamineAmount;
	}

	/**
	 * Returns the discountID.
	 * @return long
	 */
	public long getDiscountID()
	{
		return DiscountID;
	}

	/**
	 * Returns the discountInterest.
	 * @return double
	 */
	public double getDiscountInterest()
	{
		return DiscountInterest;
	}

	/**
	 * Returns the discountPurpose.
	 * @return String
	 */
	public String getDiscountPurpose()
	{
		return DiscountPurpose;
	}

	/**
	 * Returns the discountRate.
	 * @return double
	 */
	public double getDiscountRate()
	{
		return DiscountRate;
	}

	/**
	 * Returns the discountReason.
	 * @return String
	 */
	public String getDiscountReason()
	{
		return DiscountReason;
	}

	/**
	 * Returns the discountStartDate.
	 * @return Timestamp
	 */
	public Timestamp getDiscountStartDate()
	{
		return DiscountStartDate;
	}

	/**
	 * Returns the discountStatusID.
	 * @return long
	 */
	public long getDiscountStatusID()
	{
		return DiscountStatusID;
	}

	/**
	 * Returns the draftCode.
	 * @return String
	 */
	public String getDraftCode()
	{
		return DraftCode;
	}

	/**
	 * Returns the draftTypeID.
	 * @return int
	 */
	public long getDraftTypeID()
	{
		return DraftTypeID;
	}

	/**
	 * Returns the draftTypeName.
	 * @return String
	 */
	public String getDraftTypeName()
	{
		return DraftTypeName;
	}

	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Returns the inputDate.
	 * @return Timestamp
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	/**
	 * Returns the inputUserID.
	 * @return long
	 */
	public long getInputUserID()
	{
		return InputUserID;
	}

	/**
	 * Returns the inputUserName.
	 * @return String
	 */
	public String getInputUserName()
	{
		return InputUserName;
	}

	/**
	 * Returns the officeAccount.
	 * @return String
	 */
	public String getOfficeAccount()
	{
		return OfficeAccount;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * Returns the officeName.
	 * @return String
	 */
	public String getOfficeName()
	{
		return OfficeName;
	}

	/**
	 * Returns the orderParam.
	 * @return String
	 */
	public String getOrderParam()
	{
		return OrderParam;
	}

	/**
	 * Returns the pageNo.
	 * @return long
	 */
	public long getPageNo()
	{
		return PageNo;
	}

	/**
	 * Returns the publicDate.
	 * @return Timestamp
	 */
	public Timestamp getPublicDate()
	{
		return PublicDate;
	}

	/**
	 * Returns the statusID.
	 * @return long
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * Sets the acceptAccount.
	 * @param acceptAccount The acceptAccount to set
	 */
	public void setAcceptAccount(String acceptAccount)
	{
		AcceptAccount = acceptAccount;
	}

	/**
	 * Sets the acceptBank.
	 * @param acceptBank The acceptBank to set
	 */
	public void setAcceptBank(String acceptBank)
	{
		AcceptBank = acceptBank;
	}

	/**
	 * Sets the acceptClientID.
	 * @param acceptClientID The acceptClientID to set
	 */
	public void setAcceptClientID(long acceptClientID)
	{
		AcceptClientID = acceptClientID;
	}

	/**
	 * Sets the acceptClientName.
	 * @param acceptClientName The acceptClientName to set
	 */
	public void setAcceptClientName(String acceptClientName)
	{
		AcceptClientName = acceptClientName;
	}

	/**
	 * Sets the applyAccount.
	 * @param applyAccount The applyAccount to set
	 */
	public void setApplyAccount(String applyAccount)
	{
		ApplyAccount = applyAccount;
	}

	/**
	 * Sets the applyBank.
	 * @param applyBank The applyBank to set
	 */
	public void setApplyBank(String applyBank)
	{
		ApplyBank = applyBank;
	}

	/**
	 * Sets the applyClientID.
	 * @param applyClientID The applyClientID to set
	 */
	public void setApplyClientID(long applyClientID)
	{
		ApplyClientID = applyClientID;
	}

	/**
	 * Sets the applyClientName.
	 * @param applyClientName The applyClientName to set
	 */
	public void setApplyClientName(String applyClientName)
	{
		ApplyClientName = applyClientName;
	}

	/**
	 * Sets the applyDiscountPO.
	 * @param applyDiscountPO The applyDiscountPO to set
	 */
	public void setApplyDiscountPO(long applyDiscountPO)
	{
		ApplyDiscountPO = applyDiscountPO;
	}

	/**
	 * Sets the applyLOfficeAccount.
	 * @param applyLOfficeAccount The applyLOfficeAccount to set
	 */
	public void setApplyLOfficeAccount(String applyLOfficeAccount)
	{
		ApplyLOfficeAccount = applyLOfficeAccount;
	}

	/**
	 * Sets the applyOfficeID.
	 * @param applyOfficeID The applyOfficeID to set
	 */
	public void setApplyOfficeID(long applyOfficeID)
	{
		ApplyOfficeID = applyOfficeID;
	}

	/**
	 * Sets the applyOfficeName.
	 * @param applyOfficeName The applyOfficeName to set
	 */
	public void setApplyOfficeName(String applyOfficeName)
	{
		ApplyOfficeName = applyOfficeName;
	}

	/**
	 * Sets the atTerm.
	 * @param atTerm The atTerm to set
	 */
	public void setAtTerm(Timestamp atTerm)
	{
		AtTerm = atTerm;
	}

	/**
	 * Sets the bankAccepPO.
	 * @param bankAccepPO The bankAccepPO to set
	 */
	public void setBankAccepPO(long bankAccepPO)
	{
		BankAccepPO = bankAccepPO;
	}

	/**
	 * Sets the billAmount.
	 * @param billAmount The billAmount to set
	 */
	public void setAmount(double billAmount)
	{
		Amount = billAmount;
	}

	/**
	 * Sets the billCheckAmount.
	 * @param billCheckAmount The billCheckAmount to set
	 */
	public void setCheckAmount(double billCheckAmount)
	{
		CheckAmount = billCheckAmount;
	}

	/**
	 * Sets the billInterest.
	 * @param billInterest The billInterest to set
	 */
	public void setInterest(double billInterest)
	{
		Interest = billInterest;
	}

	/**
	 * Sets the bizAcceptPO.
	 * @param bizAcceptPO The bizAcceptPO to set
	 */
	public void setBizAcceptPO(long bizAcceptPO)
	{
		BizAcceptPO = bizAcceptPO;
	}

	/**
	 * Sets the clientInfo.
	 * @param clientInfo The clientInfo to set
	 */
	public void setClientInfo(ClientInfo clientInfo)
	{
		ClientInfo = clientInfo;
	}

	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code)
	{
		Code = code;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(int currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * Sets the desc.
	 * @param desc The desc to set
	 */
	public void setDesc(long desc)
	{
		Desc = desc;
	}

	/**
	 * Sets the discountApplyAmount.
	 * @param discountApplyAmount The discountApplyAmount to set
	 */
	public void setDiscountApplyAmount(double discountApplyAmount)
	{
		DiscountApplyAmount = discountApplyAmount;
	}

	/**
	 * Sets the discountBillAmount.
	 * @param discountBillAmount The discountBillAmount to set
	 */
	public void setDiscountBillAmount(double discountBillAmount)
	{
		DiscountBillAmount = discountBillAmount;
	}

	/**
	 * Sets the discountBillCount.
	 * @param discountBillCount The discountBillCount to set
	 */
	public void setDiscountBillCount(long discountBillCount)
	{
		DiscountBillCount = discountBillCount;
	}

	/**
	 * Sets the discountCheckAmount.
	 * @param discountCheckAmount The discountCheckAmount to set
	 */
	public void setDiscountCheckAmount(double discountCheckAmount)
	{
		DiscountCheckAmount = discountCheckAmount;
	}

	/**
	 * Sets the discountCode.
	 * @param discountCode The discountCode to set
	 */
	public void setDiscountCode(String discountCode)
	{
		DiscountCode = discountCode;
	}

	/**
	 * Sets the discountDate.
	 * @param discountDate The discountDate to set
	 */
	public void setDiscountDate(Timestamp discountDate)
	{
		DiscountDate = discountDate;
	}

	/**
	 * Sets the discountEndDate.
	 * @param discountEndDate The discountEndDate to set
	 */
	public void setDiscountEndDate(Timestamp discountEndDate)
	{
		DiscountEndDate = discountEndDate;
	}

	/**
	 * Sets the discountExamineAmount.
	 * @param discountExamineAmount The discountExamineAmount to set
	 */
	public void setDiscountExamineAmount(double discountExamineAmount)
	{
		DiscountExamineAmount = discountExamineAmount;
	}

	/**
	 * Sets the discountID.
	 * @param discountID The discountID to set
	 */
	public void setDiscountID(long discountID)
	{
		DiscountID = discountID;
	}

	/**
	 * Sets the discountInterest.
	 * @param discountInterest The discountInterest to set
	 */
	public void setDiscountInterest(double discountInterest)
	{
		DiscountInterest = discountInterest;
	}

	/**
	 * Sets the discountPurpose.
	 * @param discountPurpose The discountPurpose to set
	 */
	public void setDiscountPurpose(String discountPurpose)
	{
		DiscountPurpose = discountPurpose;
	}

	/**
	 * Sets the discountRate.
	 * @param discountRate The discountRate to set
	 */
	public void setDiscountRate(double discountRate)
	{
		DiscountRate = discountRate;
	}

	/**
	 * Sets the discountReason.
	 * @param discountReason The discountReason to set
	 */
	public void setDiscountReason(String discountReason)
	{
		DiscountReason = discountReason;
	}

	/**
	 * Sets the discountStartDate.
	 * @param discountStartDate The discountStartDate to set
	 */
	public void setDiscountStartDate(Timestamp discountStartDate)
	{
		DiscountStartDate = discountStartDate;
	}

	/**
	 * Sets the discountStatusID.
	 * @param discountStatusID The discountStatusID to set
	 */
	public void setDiscountStatusID(long discountStatusID)
	{
		DiscountStatusID = discountStatusID;
	}

	/**
	 * Sets the draftCode.
	 * @param draftCode The draftCode to set
	 */
	public void setDraftCode(String draftCode)
	{
		DraftCode = draftCode;
	}

	/**
	 * Sets the draftTypeID.
	 * @param draftTypeID The draftTypeID to set
	 */
	public void setDraftTypeID(long draftTypeID)
	{
		DraftTypeID = draftTypeID;
	}

	/**
	 * Sets the draftTypeName.
	 * @param draftTypeName The draftTypeName to set
	 */
	public void setDraftTypeName(String draftTypeName)
	{
		DraftTypeName = draftTypeName;
	}

	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setID(long iD)
	{
		ID = iD;
	}
    
	/**
	 * Sets the inputDate.
	 * @param inputDate The inputDate to set
	 */
	public void setInputDate(Timestamp inputDate)
	{
		InputDate = inputDate;
	}

	/**
	 * Sets the inputUserID.
	 * @param inputUserID The inputUserID to set
	 */
	public void setInputUserID(long inputUserID)
	{
		InputUserID = inputUserID;
	}

	/**
	 * Sets the inputUserName.
	 * @param inputUserName The inputUserName to set
	 */
	public void setInputUserName(String inputUserName)
	{
		InputUserName = inputUserName;
	}

	/**
	 * Sets the officeAccount.
	 * @param officeAccount The officeAccount to set
	 */
	public void setOfficeAccount(String officeAccount)
	{
		OfficeAccount = officeAccount;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * Sets the officeName.
	 * @param officeName The officeName to set
	 */
	public void setOfficeName(String officeName)
	{
		OfficeName = officeName;
	}

	/**
	 * Sets the orderParam.
	 * @param orderParam The orderParam to set
	 */
	public void setOrderParam(String orderParam)
	{
		OrderParam = orderParam;
	}

	/**
	 * Sets the pageNo.
	 * @param pageNo The pageNo to set
	 */
	public void setPageNo(long pageNo)
	{
		PageNo = pageNo;
	}

	/**
	 * Sets the publicDate.
	 * @param publicDate The publicDate to set
	 */
	public void setPublicDate(Timestamp publicDate)
	{
		publicDate = publicDate;
	}

	/**
	 * Sets the statusID.
	 * @param statusID The statusID to set
	 */
	public void setStatusID(long statusID)
	{
		StatusID = statusID;
	}

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getAccountBankID()
    {
        return AccountBankID;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param 
     * function �õ�/����
     * return Timestamp
     */
    public Timestamp getFillDate()
    {
        return FillDate;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getGrantTypeID()
    {
        return GrantTypeID;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getNextCheckUserID()
    {
        return NextCheckUserID;
    }

    /**
     * @param 
     * function �õ�/����
     * return double
     */
    public double getRate()
    {
        return Rate;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getReceiveAccount()
    {
        return ReceiveAccount;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getReceiveClientName()
    {
        return ReceiveClientName;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getRemitBank()
    {
        return RemitBank;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getRemitCity()
    {
        return RemitCity;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getRemitProvince()
    {
        return RemitProvince;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAccountBankID(long l)
    {
        AccountBankID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setContractID(long l)
    {
        ContractID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setFillDate(Timestamp timestamp)
    {
        FillDate = timestamp;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setGrantTypeID(long l)
    {
        GrantTypeID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setNextCheckUserID(long l)
    {
        NextCheckUserID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setRate(double d)
    {
        Rate = d;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setReceiveAccount(String string)
    {
        ReceiveAccount = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setReceiveClientName(String string)
    {
        ReceiveClientName = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setRemitBank(String string)
    {
        RemitBank = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setRemitCity(String string)
    {
        RemitCity = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setRemitProvince(String string)
    {
        RemitProvince = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getGrantCurrentAccountID()
    {
        return GrantCurrentAccountID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setGrantCurrentAccountID(long l)
    {
        GrantCurrentAccountID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getInCode()
    {
        return InCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getInID()
    {
        return InID;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInCode(String string)
    {
        InCode = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setInID(long l)
    {
        InID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getAccountBankCode()
    {
        return AccountBankCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getAccountBankName()
    {
        return AccountBankName;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getGrantCurrentAccountCode()
    {
        return GrantCurrentAccountCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAccountBankCode(String string)
    {
        AccountBankCode = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAccountBankName(String string)
    {
        AccountBankName = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setGrantCurrentAccountCode(String string)
    {
        GrantCurrentAccountCode = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getLoanApplyCode()
    {
        return LoanApplyCode;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setContractCode(String string)
    {
        ContractCode = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setLoanApplyCode(String string)
    {
        LoanApplyCode = string;
    }

}

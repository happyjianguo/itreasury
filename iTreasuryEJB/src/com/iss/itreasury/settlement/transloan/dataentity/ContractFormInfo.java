package com.iss.itreasury.settlement.transloan.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.util.DataFormat;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-11-18
 */
public class ContractFormInfo implements Serializable {
	//��ͬ��Ϣ
	//ID:��ͬ��ʶ
	private long ContractID;

	//ID:��ͬ��ʶ
	private long LoanID;

	//nCurrencyID:���ֱ�ʶ
	private long CurrencyID;

	//nOfficeID:���´���ʶ
	private long OfficeID;

	//sContractCode:��ͬ���
	private String ContractCode;

	//nLoanTypeID:��������
	private long LoanTypeID;
	private String LoanTypeName;

	//nBorrowClientID:���λID
	private long BorrowClientID;

	//sName:���λ����
	private String BorrowClientName;

	//sName:���λ���
	private String BorrowClientCode;

	//nClientID ί�е�λID
	private long ClientID;

	//sName:ί�е�λ����
	private String ClientName;

	//ί�е�λ��λ�˻�
	private String ClientAccount;

	//��λ�˻�
	private String LoanAccount;

	//mLoanAmount ���
	private double LoanAmount;

	//ExamineAmount ��׼���
	private double ExamineAmount;

	//�������ֺ˶����
	private double CheckAmount;

	//������Ϣ
	private double DiscountInterest;

	//AssureAmount �������
	private double AssureAmount;

	//AssureRate �������֮��ռ�������ı���
	private double AssureRate;

	//CreditRate���ô�����ռ�������ı���
	private double CreditRate;

	//���ô����ܶ�
	private double CreditAmount;

	//�ѷ��Ž��
	private double AmountDone;

	//fInterestRate:����
	private double InterestRate;

	//fInterestRate:��������ID
	private long BankInterestID;

	//nIntervalNum:��������
	private long IntervalNum;

	//nInputUserID:¼���˱�ʶ ���Ǻ�ͬ������
	private long lInputUserID;

	//sInputUserName ¼��������
	private String InputUserName;
	private Timestamp InputDate; //��ͬ¼������

	//DiscountRate ��������
	private double DiscountRate;

	//nStatusID:��ͬ״̬
	private long StatusID;
	private String Status;

	//��ҳ��ʾ��ҳ��
	private long PageCount;

	// ������
	private String ApplyCode;

	//�ܼ�¼��,���ڴ��������ѯʱ�õ�
	private long AllRecord;

	//�ܽ��
	private double AllAmount;

	//�������
	private double Balance;

	//dtLoanStart�����ʼ����
	private Timestamp LoanStart;

	//dtLoanStart����������
	private Timestamp LoanEnd;

	//�����;
	private String LoanPurpose;

	//���ԭ��
	private String LoanReason;

	//�ſ�����
	private Timestamp OutDate;

	//��λ�ʱ�
	private String LoanZipCode;

	//��λ�绰
	private String LoanPhone;

	//��λ��ַ
	private String LoanAddress;

	//��ִͬ�мƻ��Ƿ�����޸�
	private long IsPlanModifying = -1;

	//��ִͬ�мƻ��汾��ID
	private long PlanVersionID = -1;

	//�Ƿ��мƻ���չ��֮��
	private long IsOverDueModifying = -1;

	//����ֵ
	private double Rate = -1;

	//��������������ʶ����1��2��
	private long AddOrDecrease = 1;

	//��������
	private double AdjustRate = 0;

	//�����ID
	private long CheckUserID = -1;

	//���������
	private String CheckUserName = "";

	//����������
	private long AreaType = -1;

	//����ҵ����1
	private long IndustryType1 = -1;

	//����ҵ����2
	private long IndustryType2 = -1;

	//��֤��Ϣ
	private Collection cAssure = null;

	//��ͬ�ı���Ϣ
	private Collection cContractContent = null;

	//������Ϣ
	private Collection cYT = null;

	//������Ϣ--�ڽ�������ʱ��ʹ�� by Huang Ye
	//private YTFormatInfo YTInfo = null;

	//��ͬ�ƻ��޸�����
	private Timestamp PlanModifyDate = null;

	//������Դ
	private String sOther = "";

	//��������
	private double ChargeRate = 0;

	//������������
	private long ChargeRateType = -1;
	
	//ת��������
	private long DiscountTypeID = -1;
	
	//ת����������������
	private long ReDiscountInOrOut = -1;

	/**
	 * function �õ���ͬ��ʶ
	 * return ContractID
	 */
	public long getContractID()
	{
		return ContractID;
	}

	/**
	 * @param lID
	 * function ���ú�ͬ��ʶ
	 * return void
	 */
	public void setContractID(long lID)
	{
		this.ContractID = lID;
	}

	/**
	 * function �õ���ͬ���
	 * return ContractCode
	 */
	public String getContractCode()
	{
		return ContractCode;
	}

	/**
	 * @param ContractCode
	 * function ���ú�ͬ���
	 * return void
	 */
	public void setContractCode(String ContractCode)
	{
		this.ContractCode = ContractCode;
	}

	/**
	 * function �õ����λ����
	 * return BorrowClientName
	 */
	public String getBorrowClientName()
	{
		return BorrowClientName;
	}

	/**
	 * @param BorrowClientName
	 * function ���ô��λ����
	 * return void
	 */
	public void setBorrowClientName(String BorrowClientName)
	{
		this.BorrowClientName = BorrowClientName;
	}

	/**
	 * function �õ����λ���
	 * return BorrowClientCode
	 */
	public String getBorrowClientCode()
	{
		return BorrowClientCode;
	}

	/**
	 * @param BorrowClientCode
	 * function ���ô��λ���
	 * return void
	 */
	public void setBorrowClientCode(String BorrowClientCode)
	{
		this.BorrowClientCode = BorrowClientCode;
	}

	/**
	 * function �õ�ί�е�λ����
	 * return ClientName
	 */
	public String getClientName()
	{
		return ClientName;
	}

	/**
	 * @param ClientName
	 * function ����ί�е�λ����
	 * return ClientName
	 */
	public void setClientName(String ClientName)
	{
		this.ClientName = ClientName;
	}

	/**
	 * function �õ�������
	 * return lLoanAmount
	 */
	public double getLoanAmount()
	{
		return LoanAmount;
	}

	/**
	* function �õ���ʽ����Ĵ�����
	* return lLoanAmount
	*/
	public String getFormatLoanAmount()
	{
		return DataFormat.formatDisabledAmount(LoanAmount);
	}

	/**
	 * @param dLoanAmount
	 * function ���ô�����
	 * return void
	 */
	public void setLoanAmount(double dLoanAmount)
	{
		this.LoanAmount = dLoanAmount;
	}

	/**
	 * function �õ�����
	 * return dInterestRate
	 */
	public double getInterestRate()
	{
		return InterestRate;
	}

	/**
	* function �õ�����
	* return dInterestRate
	*/
	public String getFormatInterestRate()
	{
		return DataFormat.formatRate(InterestRate);
	}

	/**
	 * @param dInterestRate
	 * function ��������
	 * return void
	 */
	public void setInterestRate(double dInterestRate)
	{
		this.InterestRate = dInterestRate;
	}

	/**
	 * function �õ���������
	 * return lIntervalNum
	 */
	public long getIntervalNum()
	{
		return IntervalNum;
	}

	/**
	 * @param IntervalNum
	 * function ���ô�������
	 * return void
	 */
	public void setIntervalNum(long IntervalNum)
	{
		this.IntervalNum = IntervalNum;
	}

	/**
	 * function �õ���ͬ״̬
	 * return lStatusID
	 */
	public long getStatusID()
	{
		return StatusID;
	}

	/**
	 * @param lStatusID
	 * function ���ú�ͬ״̬
	 * return void
	 */
	public void setStatusID(long lStatusID)
	{
		this.StatusID = lStatusID;
	}

	/**
	 * function �õ���ͬ������
	 * return InputUserName
	 */
	public String getInputUserName()
	{
		if (InputUserName != null)
		{
			return InputUserName;
		}
		else
		{
			return "";
		}
	}

	/**
	 * @param InputUserName
	 * function ���ú�ͬ������
	 * return void
	 */
	public void setInputUserName(String InputUserName)
	{
		this.InputUserName = InputUserName;
	}

	/**
	 * function �õ���������
	 * return lTypeID
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @param lTypeID
	 * function ���ô�������
	 * return void
	 */
	public void setLoanTypeID(long lTypeID)
	{
		this.LoanTypeID = lTypeID;
	}

	/**
	 * function �õ����λID
	 * return lBorrowClientID
	 */
	public long getBorrowClientID()
	{
		return BorrowClientID;
	}

	/**
	 * @param lBorrowClientID
	 * function ���ô��λID
	 * return void
	 */
	public void setBorrowClientID(long lBorrowClientID)
	{
		this.BorrowClientID = lBorrowClientID;
	}

	/**
	 * function �õ�ί�е�λID
	 * return lClientID
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param lClientID
	 * function ����ί�е�λID
	 * return void
	 */
	public void setClientID(long lClientID)
	{
		this.ClientID = lClientID;
	}

	/**
	 * function �õ�״̬����
	 * return Status
	 */
	public String getStatus()
	{
		return Status;
	}

	/**
	 * @param Status
	 * function ����״̬����
	 * return void
	 */
	public void setStatus(String Status)
	{
		this.Status = Status;
	}

	/**
	 * function �õ�������������
	 * return LoanTypeName
	 */
	public String getLoanTypeName()
	{
		return LoanTypeName;
	}

	/**
	 * @param LoanTypeName
	 * function ���ô�����������
	 * return void
	 */
	public void setLoanTypeName(String LoanTypeName)
	{
		this.LoanTypeName = LoanTypeName;
	}

	/**
	 * function �õ��ܽ��
	 * return double
	 */
	public double getAllAmount()
	{
		return AllAmount;
	}

	/**
	 * @param dAllAmount
	 * function �����ܽ��
	 * return void
	 */
	public void setAllAmount(double AllAmount)
	{
		this.AllAmount = AllAmount;
	}

	/**
	 * function �õ���������
	 * return ApplyCode
	 */
	public String getApplyCode()
	{
		return ApplyCode;
	}

	/**
	 * @param ApplyCode
	 * function ������������
	 * return void
	 */
	public void setApplyCode(String ApplyCode)
	{
		this.ApplyCode = ApplyCode;
	}

	public double getBalance()
	{
		return Balance;
	}

	public void setBalance(double Balance)
	{
		this.Balance = Balance;
	}

	public Timestamp getLoanStart()
	{
		return LoanStart;
	}

	public String getFormatLoanStart()
	{
		return DataFormat.getDateString(LoanStart);
	}

	public void setLoanStart(Timestamp LoanStart)
	{
		this.LoanStart = LoanStart;
	}

	public Timestamp getLoanEnd()
	{
		return LoanEnd;
	}

	public String getFormatLoanEnd()
	{
		return DataFormat.getDateString(LoanEnd);
	}

	public void setLoanEnd(Timestamp LoanEnd)
	{
		this.LoanEnd = LoanEnd;
	}

	/**
	 * function �õ���ҳ��ʾ��ҳ��
	 * return PageCount
	 */
	public long getPageCount()
	{
		return PageCount;
	}

	/**
	 * @param lPageCount
	 * function ���÷�ҳ��ʾ��ҳ��
	 * return void
	 */
	public void setPageCount(long lPageCount)
	{
		this.PageCount = lPageCount;
	}

	/**
	 * function �õ��ܼ�¼��
	 * return AllRecord
	 */
	public long getAllRecord()
	{
		return AllRecord;
	}

	/**
	 * @param lAllRecord
	 * function �����ܼ�¼��
	 * return void
	 */
	public void setAllRecord(long lAllRecord)
	{
		this.AllRecord = lAllRecord;
	}

	public double getAmountDone()
	{
		return AmountDone;
	}

	public void setAmountDone(double AmountDone)
	{
		this.AmountDone = AmountDone;
	}

	public String getLoanPurpose()
	{
		return LoanPurpose;
	}

	public void setLoanPurpose(String LoanPurpose)
	{
		this.LoanPurpose = LoanPurpose;
	}

	public Timestamp getOutDate()
	{
		return OutDate;
	}

	public void setOutDate(Timestamp OutDate)
	{
		this.OutDate = OutDate;
	}

	public String getLoanAccount()
	{
		return LoanAccount;
	}

	public void setLoanAccount(String LoanAccount)
	{
		this.LoanAccount = LoanAccount;
	}

	public String getLoanAddress()
	{
		return LoanAddress;
	}

	public void setLoanAddress(String LoanAddress)
	{
		this.LoanAddress = LoanAddress;
	}

	public String getLoanPhone()
	{
		return LoanPhone;
	}

	public void setLoanPhone(String LoanPhone)
	{
		this.LoanPhone = LoanPhone;
	}

	public String getLoanZipCode()
	{
		return LoanZipCode;
	}

	public void setLoanZipCode(String LoanZipCode)
	{
		this.LoanZipCode = LoanZipCode;
	}

	public String getClientAccount()
	{
		return ClientAccount;
	}

	public void setClientAccount(String ClientAccount)
	{
		this.ClientAccount = ClientAccount;
	}

	/**
	 * function �õ���ִͬ�мƻ��Ƿ����޸�
	 * return IsPlanModifying
	 */
	public long getIsPlanModifying()
	{
		return IsPlanModifying;
	}

	/**
	 * @param IsPlanModifying
	 * function���ú�ִͬ�мƻ��Ƿ����޸�
	 * return void
	 */
	public void setIsPlanModifying(long lIsPlanModifying)
	{
		this.IsPlanModifying = lIsPlanModifying;
	}

	/**
	 * function �õ���ִͬ�мƻ��汾��ID
	 * return PlanVersionID
	 */
	public long getPlanVersionID()
	{
		return PlanVersionID;
	}

	/**
	 * @param lPlanVersionID
	 * function ���ú�ִͬ�мƻ��汾��ID
	 * return void
	 */
	public void setPlanVersionID(long lPlanVersionID)
	{
		this.PlanVersionID = lPlanVersionID;
	}

	/**
	 * function �õ��Ƿ��мƻ���չ��֮��
	 * return IsOverDueModifying
	 */
	public long getIsOverDueModifying()
	{
		return IsOverDueModifying;
	}

	/**
	 * @param lIsOverDueModifying
	 * function �����Ƿ��мƻ���չ��֮��
	 * return void
	 */
	public void setIsOverDueModifying(long lIsOverDueModifying)
	{
		this.IsOverDueModifying = lIsOverDueModifying;
	}

	public double getRate()
	{
		return Rate;
	}

	public void setRate(double Rate)
	{
		this.Rate = Rate;
	}

	public double getAdjustRate()
	{
		return AdjustRate;
	}

	public void setAdjustRate(double AdjustRate)
	{
		this.AdjustRate = AdjustRate;
	}

	public long getAddOrDecrease()
	{
		return AddOrDecrease;
	}

	public void setAddOrDecrease(long AddOrDecrease)
	{
		this.AddOrDecrease = AddOrDecrease;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public Timestamp getInputDate()
	{
		return InputDate;
	}

	public String getFormatInputDate()
	{
		return DataFormat.getDateString(InputDate);
	}

	/**
	 * @return
	 */
	public long getInputUserID()
	{
		return lInputUserID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
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
		lInputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getLoanID()
	{
		return LoanID;
	}

	/**
	 * @param l
	 */
	public void setLoanID(long l)
	{
		LoanID = l;
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
	public String getFormatExamineAmount()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount);
	}

	/**
	 * @param d
	 */
	public void setExamineAmount(double d)
	{
		ExamineAmount = d;
	}

	/**
	 * @return
	 */
	public double getAssureAmount()
	{
		return AssureAmount;
	}

	/**
	* @return
	*/
	public String getFormatAssureAmount()
	{
		return DataFormat.formatDisabledAmount(AssureAmount);
	}

	/**
	 * @param d
	 */
	public void setAssureAmount(double d)
	{
		AssureAmount = d;
	}

	/**
	 * @return
	 */
	public double getAssureRate()
	{
		return AssureRate;
	}

	/**
	* @return
	*/
	public String getFormatAssureRate()
	{
		return DataFormat.formatDisabledAmount(AssureRate * 100);
	}

	/**
	 * @param d
	 */
	public void setAssureRate(double d)
	{
		AssureRate = d;
	}

	/**
	 * @return
	 */
	public double getCreditAmount()
	{
		return CreditAmount;
	}

	/**
	* @return
	*/
	public String getFormatCreditAmount()
	{
		return DataFormat.formatDisabledAmount(CreditAmount);
	}

	/**
	 * @param d
	 */
	public void setCreditAmount(double d)
	{
		CreditAmount = d;
	}

	/**
	 * @return
	 */
	public double getCreditRate()
	{
		return CreditRate;
	}

	/**
	* @return
	*/
	public String getFormatCreditRate()
	{
		return DataFormat.formatDisabledAmount(CreditRate * 100);
	}

	/**
	 * @param d
	 */
	public void setCreditRate(double d)
	{
		CreditRate = d;
	}

	/**
	 * @return
	 */
	public long getAreaType()
	{
		return AreaType;
	}

	/**
	 * @return
	 */
	public long getIndustryType1()
	{
		return IndustryType1;
	}

	/**
	 * @return
	 */
	public long getIndustryType2()
	{
		return IndustryType2;
	}

	/**
	 * @param l
	 */
	public void setAreaType(long l)
	{
		AreaType = l;
	}

	/**
	 * @param l
	 */
	public void setIndustryType1(long l)
	{
		IndustryType1 = l;
	}

	/**
	 * @param l
	 */
	public void setIndustryType2(long l)
	{
		IndustryType2 = l;
	}

	/**
	 * @return
	 */
	public Collection getAssure()
	{
		return cAssure;
	}

	/**
	 * @param collection
	 */
	public void setAssure(Collection collection)
	{
		cAssure = collection;
	}

	/**
	 * @return
	 */
	public String getCheckUserName()
	{
		if (CheckUserName != null)
		{
			return CheckUserName;
		}
		else
		{
			return "";
		}

	}

	/**
	 * @param string
	 */
	public void setCheckUserName(String string)
	{
		CheckUserName = string;
	}

	/**
//	 * @return
//	 */
//	public YTFormatInfo getYTInfo()
//	{
//		return YTInfo;
//	}
//
//	/**
//	 * @param info
//	 */
//	public void setYTInfo(YTFormatInfo info)
//	{
//		YTInfo = info;
//	}

	/**
	 * @return
	 */
	public Collection getYT()
	{
		return cYT;
	}

	/**
	 * @param collection
	 */
	public void setYT(Collection collection)
	{
		cYT = collection;
	}

	/**
	 * Returns the cAssure.
	 * @return Collection
	 */
	public Collection getCAssure()
	{
		return cAssure;
	}

	/**
	 * Returns the cYT.
	 * @return Collection
	 */
	public Collection getCYT()
	{
		return cYT;
	}

	/**
	 * Sets the cAssure.
	 * @param cAssure The cAssure to set
	 */
	public void setCAssure(Collection cAssure)
	{
		this.cAssure = cAssure;
	}

	/**
	 * Sets the cYT.
	 * @param cYT The cYT to set
	 */
	public void setCYT(Collection cYT)
	{
		this.cYT = cYT;
	}

	/**
	 * Returns the planModifyDate.
	 * @return Timestamp
	 */
	public Timestamp getPlanModifyDate()
	{
		return PlanModifyDate;
	}

	/**
	 * Sets the planModifyDate.
	 * @param planModifyDate The planModifyDate to set
	 */
	public void setPlanModifyDate(Timestamp planModifyDate)
	{
		PlanModifyDate = planModifyDate;
	}

	/**
	 * @return
	 */
	public String getLoanReason()
	{
		return LoanReason;
	}

	/**
	 * @param string
	 */
	public void setLoanReason(String string)
	{
		LoanReason = string;
	}

	/**
	 * @return
	 */
	public String getOther()
	{
		return sOther;
	}

	/**
	 * @param string
	 */
	public void setOther(String string)
	{
		sOther = string;
	}

	/**
	 * @return
	 */
	public Collection getContractContent()
	{
		return cContractContent;
	}

	/**
	 * @param collection
	 */
	public void setContractContent(Collection collection)
	{
		cContractContent = collection;
	}

	/**
	 * Returns the chargeRate.
	 * @return double
	 */
	public double getChargeRate()
	{
		return ChargeRate;
	}

	/**
	 * Sets the chargeRate.
	 * @param chargeRate The chargeRate to set
	 */
	public void setChargeRate(double chargeRate)
	{
		ChargeRate = chargeRate;
	}

	/**
	 * @return
	 */
	public long getCheckUserID()
	{
		return CheckUserID;
	}

	/**
	 * @param l
	 */
	public void setCheckUserID(long l)
	{
		CheckUserID = l;
	}

	/**
	 * @return
	 */
	public long getChargeRateType()
	{
		return ChargeRateType;
	}

	/**
	 * @param l
	 */
	public void setChargeRateType(long l)
	{
		ChargeRateType = l;
	}

	/**
	 * @return
	 */
	public long getBankInterestID()
	{
		return BankInterestID;
	}

	/**
	 * @param l
	 */
	public void setBankInterestID(long l)
	{
		BankInterestID = l;
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
	public String getFormatCheckAmount()
	{
		return DataFormat.formatDisabledAmount(CheckAmount);
	}

	/**
	 * @param d
	 */
	public void setCheckAmount(double d)
	{
		CheckAmount = d;
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
	public String getFormatDiscountRate()
	{
		return DataFormat.formatRate(DiscountRate);
	}

	/**
	 * @param d
	 */
	public void setDiscountRate(double d)
	{
		DiscountRate = d;
	}

	/**
	 * @return
	 */
	public double getDiscountInterest()
	{
		return DiscountInterest;
	}

	/**
	* @return
	*/
	public String getFormatDiscountInterest()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount-CheckAmount);
	}

	/**
	 * @param d
	 */
	public void setDiscountInterest(double d)
	{
		DiscountInterest = d;
	}
	/**
	 * Returns the discountTypeID.
	 * @return long
	 */
	public long getDiscountTypeID()
	{
		return DiscountTypeID;
	}

	/**
	 * Sets the discountTypeID.
	 * @param discountTypeID The discountTypeID to set
	 */
	public void setDiscountTypeID(long discountTypeID)
	{
		DiscountTypeID = discountTypeID;
	}

	/**
	 * Returns the reDiscountInOrOut.
	 * @return long
	 */
	public long getReDiscountInOrOut()
	{
		return ReDiscountInOrOut;
	}

	/**
	 * Sets the reDiscountInOrOut.
	 * @param reDiscountInOrOut The reDiscountInOrOut to set
	 */
	public void setReDiscountInOrOut(long reDiscountInOrOut)
	{
		ReDiscountInOrOut = reDiscountInOrOut;
	}

}

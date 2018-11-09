package com.iss.itreasury.loan.contract.dataentity;

import java.sql.Timestamp;
import java.util.Collection;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;

public class ContractDetailInfo implements java.io.Serializable {


 


	/**
	 * ContractInfo ������ע�⡣
	 */
	public ContractDetailInfo()
	{
		super();
	}

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
	private long SubTypeID;

	//nBorrowClientID:���λID
	private long BorrowClientID;

	//niscircle:�Ƿ�ѭ������
	private long isCircle;
	//���յȼ�
	private long riskLevel;

	//sName:���λ����
	private String BorrowClientName;

	//nClientID ί�е�λID
	private long ClientID;

	//sName:ί�е�λ����
	private String ClientName;


	//mLoanAmount ���
	private double LoanAmount;

	//ExamineAmount ��׼���
	private double ExamineAmount;
	
	
	

	//fInterestRate:����
	private double InterestRate;

	//fInterestRate:��׼����
	private double BasicInterestRate;

	//fInterestRate:��������ID
	private long BankInterestID = -1;
	
	//InterestTypeID:��������
	private long InterestTypeID = 1;
	
	//LiborRateID:Libor����ID
	private long LiborRateID = -1;

	//nIntervalNum:��������
	private long IntervalNum;

	//sInputUserName ¼��������
	private String InputUserName;
	private Timestamp InputDate; //��ͬ¼������

	//DiscountRate ��������
	private double DiscountRate;

	//nStatusID:��ͬ״̬
	private long StatusID;
	
	
	//nDraftTypeID:���ֻ�Ʊ����
	private long tsDiscountTypeID;
	private String tsDiscountType;
	
	//��������
	private double ChargeRate = 0;

	

	//��ҳ��ʾ��ҳ��
	private long PageCount;

	// ������
	private String ApplyCode;

	//��ͬ��ǰ���
	private double Balance = 0;

	//ָ��������ڵĺ�ͬ���
	private double dailyBalance = 0;
	
	//dtLoanStart�����ʼ����
	private Timestamp LoanStart;
	
	//dtDiscountDate��������
	private Timestamp discountDate;

	//dtLoanStart����������
	private Timestamp LoanEnd;

	//��ʼ����ֵ
	private double Rate = -1;

	//��������������ʶ����1��2��
	//private long AddOrDecrease = 1;

	//��������
	private double AdjustRate = 0;

	

	//����������
	private long AreaType = -1;

	//����ҵ����1
	private long IndustryType1 = -1;

	//����ҵ����2
	private long IndustryType2 = -1;
	
	//����ҵ����3
	private long IndustryType3 = -1;


	//����
	private long IsCredit = -1;

	//��֤
	private long IsAssure = -1;

	//��Ѻ
	private long IsImpawn = -1;

	//��Ѻ
	private long IsPledge = -1;
	
	//��֤��
    private long IsRecognizance = -1;
    
 
    //�̶���������
    private double staidAdjustRate=0;

	private String subTypeName = "";//���������� mzh_fu 2007/06/12
	

	
	
	
	private String dailyDate;
	
	
	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}

	
	

	
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
	 * function �õ���ͬ���յȼ�
	 * return lStatusID
	 */
	public long getRiskLevel()
	{
		return riskLevel;
	}

	/**
	 * @param lStatusID
	 * function ���ú�ͬ���յȼ�
	 * return void
	 */
	public void setRiskLevel(long lRiskLevel)
	{
		this.riskLevel = lRiskLevel;
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
	 * Returns the chargeRate.
	 * @return double
	 */
	public double getChargeRate()
	{
		return ChargeRate;
	}

	public String getFormatChargeRate()
	{
		return DataFormat.formatRate(ChargeRate);
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
	public double getBasicInterestRate()
	{
		return BasicInterestRate;
	}

	/**
	 * @param d
	 */
	public void setBasicInterestRate(double d)
	{
		BasicInterestRate = d;
	}



	
	/**
	 * Returns the isAssure.
	 * @return long
	 */
	public long getIsAssure()
	{
		return IsAssure;
	}

	/**
	 * Returns the isCredit.
	 * @return long
	 */
	public long getIsCredit()
	{
		return IsCredit;
	}

	/**
	 * Returns the isImpawn.
	 * @return long
	 */
	public long getIsImpawn()
	{
		return IsImpawn;
	}

	/**
	 * Returns the isPledge.
	 * @return long
	 */
	public long getIsPledge()
	{
		return IsPledge;
	}

	/**
	 * Sets the isAssure.
	 * @param isAssure The isAssure to set
	 */
	public void setIsAssure(long isAssure)
	{
		IsAssure = isAssure;
	}

	/**
	 * Sets the isCredit.
	 * @param isCredit The isCredit to set
	 */
	public void setIsCredit(long isCredit)
	{
		IsCredit = isCredit;
	}

	/**
	 * Sets the isImpawn.
	 * @param isImpawn The isImpawn to set
	 */
	public void setIsImpawn(long isImpawn)
	{
		IsImpawn = isImpawn;
	}

	/**
	 * Sets the isPledge.
	 * @param isPledge The isPledge to set
	 */
	public void setIsPledge(long isPledge)
	{
		IsPledge = isPledge;
	}



	/**
	 * @return
	 */
	public double getBalance()
	{
		return Balance;
	}

	/**
	 * @param d
	 */
	public void setBalance(double d)
	{
		Balance = d;
	}



    /**
     * @param 
     * function �õ�/����
     * return double
     */
    public double getStaidAdjustRate()
    {
        return staidAdjustRate;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        staidAdjustRate = d;
    }



	

   
    /**
     * @return Returns the isRecognizance.
     */
    public long getIsRecognizance()
    {
        return IsRecognizance;
    }
    /**
     * @param isRecognizance The isRecognizance to set.
     */
    public void setIsRecognizance(long isRecognizance)
    {
        IsRecognizance = isRecognizance;
    }
	
    /**
     * @return Returns the industryType3.
     */
    public long getIndustryType3()
    {
        return IndustryType3;
    }
    /**
     * @param industryType3 The industryType3 to set.
     */
    public void setIndustryType3(long industryType3)
    {
        IndustryType3 = industryType3;
    }




    /**
     * @return Returns the interestTypeID.
     */
    public long getInterestTypeID()
    {
        return InterestTypeID;
    }
    /**
     * @param interestTypeID The interestTypeID to set.
     */
    public void setInterestTypeID(long interestTypeID)
    {
        InterestTypeID = interestTypeID;
    }
    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return LiborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        LiborRateID = liborRateID;
    }
   
	
	
	/**
	 * Returns the subTypeID.
	 * @return long
	 */
	public long getSubTypeID() {
		return SubTypeID;
	}

	/**
	 * Sets the subTypeID.
	 * @param subTypeID The subTypeID to set
	 */
	public void setSubTypeID(long subTypeID) {
		SubTypeID = subTypeID;
	}
 
	


	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public Timestamp getDiscountDate() {
		return discountDate;
	}

	public void setDiscountDate(Timestamp discountDate) {
		this.discountDate = discountDate;
	}

	public long getIsCircle() {
		return isCircle;
	}

	public void setIsCircle(long isCircle) {
		this.isCircle = isCircle;
	}

	public String getTsDiscountType() {
		return tsDiscountType;
	}

	public void setTsDiscountType(String tsDiscountType) {
		this.tsDiscountType = tsDiscountType;
	}

	public long getTsDiscountTypeID() {
		return tsDiscountTypeID;
	}

	public void setTsDiscountTypeID(long tsDiscountTypeID) {
		this.tsDiscountTypeID = tsDiscountTypeID;
	}

	
	

	public double getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(double dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	
}

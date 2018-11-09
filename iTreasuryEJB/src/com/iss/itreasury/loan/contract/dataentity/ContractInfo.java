/*
 * ContractInfo.java
 *
 * Created on 2002��2��20��, ����9:39
 */

package com.iss.itreasury.loan.contract.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 *
 * @author  yzhang
 * @version
 */
public class ContractInfo extends BaseDataEntity implements Serializable
{

	/**
	 * ContractInfo ������ע�⡣
	 */
	public ContractInfo()
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
	
	//��׼����˾�д����
	private double mExamineSelfAmount;

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
	
	//nDraftTypeID:���ֻ�Ʊ����
	private long tsDiscountTypeID;
	private String tsDiscountType;

	

	//��ҳ��ʾ��ҳ��
	private long PageCount;

	// ������
	private String ApplyCode;

	//�ܼ�¼��,���ڴ��������ѯʱ�õ�
	private long AllRecord;

	//�ܽ��
	private double AllAmount;

	//�������
	private ContractAmountInfo aInfo = null;

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

	//��ʼ����ֵ
	private double Rate = -1;

	//��������������ʶ����1��2��
	//private long AddOrDecrease = 1;

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
	
	//����ҵ����3
	private long IndustryType3 = -1;

	//��֤��Ϣ
	private Collection cAssure = null;

	//��ͬ�ı���Ϣ
	private Collection cContractContent = null;

	//������Ϣ
	private Collection cYT = null;

	//������Ϣ
	private YTFormatInfo YTInfo = null;
    
    //�д�����
    private double Scale = 0;

	//��ͬ�ƻ��޸�����
	private Timestamp PlanModifyDate = null;

	//������Դ
	private String sOther = "";

	//��������
	private double ChargeRate = 0;

	//������������
	private long ChargeRateType = -1;

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
    
    //added by xiong fei 2010/05/25 
    //�ع�
    private long IsRepurchase = -1;

	//�����ֶ����ӡ��Ҫ���
	//ί������
	private String ConsignName = "";

	//ί��ס��
	private String ConsignAddress = "";

	//ί�п�����
	private String ConsignBank = "";

	//ί���˺�
	private String ConsignCode = "";

    //ί���˺�
    private String ConsignAccount = "";

    //ί���ʱ�
    private String ConsignZip = "";

	//�������
	private String BorrowName = "";

	//���ס��
	private String BorrowAddress = "";

	//������
	private String BorrowBank = "";

	//����˺�
	private String BorrowCode = "";

    //ί���˺�
    private String BorrowAccount = "";

    //ί���ʱ�
    private String BorrowZip = "";

	//��������
	private String AssureName = "";

	//����ס��
	private String AssureAddress = "";

	//����������
	private String AssureBank = "";

	//�����˺�
	private String AssureCode = "";

    //ί���˺�
    private String AssureAccount = "";

    //ί���ʱ�
    private String AssureZip = "";

	//չ����Ŀ
	private long extendCount = -1;

	//�⻹��Ŀ
	private long freeCount = -1;

	//�������
	private double overdueAmount=0.0;

	//������Ϣ
	private double punishInterest=0.0;

	//��ת��ծȯ���
	private double lastAttornmentAmount = 0;
	
	//��һ����˼���
	private long nextCheckLevel = -1;
	
	//��ͬ�����ȥ��ǰ�������������
	private double balanceForAttornment = 0;
    
    //�̶���������
    private double staidAdjustRate=0;

	//ռ�����Ŷ�Ƚ��
	private double useCreditAmount=0;
	
	//�򷽸�Ϣ
	private long isPurchaserInterest = 2;			//�Ƿ��򷽸�Ϣ
    private long discountClientID = -1;				//��Ʊ��
    private String discountClientName = "";			//ʵ������������
    private double purchaserInterestRate = 0;		//ʵ��������
    private double discountPurchaserInterest = 0;	//�򷽸�������Ϣ
    
    private double mDiscountAccrual=0;              //�����˸�Ϣ
    
    //����
	private double assureChargeRate = 0; 		//��������
	private long assureChargeTypeID = -1;		//��������ȡ��ʽ
	private String beneficiary = "";	 		//������
	private long assureTypeID1 = -1;	 		//��������1
	private long assureTypeID2 = -1;	 		//��������2
	private double recognizanceAmount = 0.0;	//��֤����
	//����--�տ�֪ͨ������������ʾ��
	private double sumAssureAmount = 0.0;		//�ú�ͬ�ı����������еĵ�����ͬ���ܽ��
	private double sumAssureBanlance = 0.0;		//�ú�ͬ�ı����������еĵ�����ͬ�������
	private double currentRecognizanceAmount = 0.0;	//�ú�ͬ���ۼ����ձ�֤�������-�ѻ���
	private double AssureBalance = 0.0;//�������
    //private double receiveAssureChargeAmount = 0.0; //�ú�ͬ�µ�ǰ����������
	//private double receiveRecognizanceAmount = 0.0; //�ú�ͬ�µ�ǰ���ձ�֤��
	private String lateRateString = "";         //LIBOR���ʣ��ַ�����ʽ
	
	//��ͬ�������� 2006-3-15
	private Timestamp lastExecDate = null;
	
	//������������
	private long interestCountTypeID = -1;	//��Ϣ���㷽ʽ
	private double chargeAmount = 0.0;		//�����ѽ��
	//private double recognizanceAmount = 0.0;//��֤����
	private double matureNominalAmount = 0.0;//�����������
	private double receivedRecognizanceAmount = 0.0;	//�ú�ͬ�����ձ�֤���ܶ�������ޣ�
	private double returnedRecognizanceAmount = 0.0;	//�ú�ͬ���ѻ���֤���ܶ�������ޣ�
	
	//added by xiong fei 2010/05/26 ������������
	private double origionAmount = 0.0;//������ԭ��
	private double preAmount = 0.0;//�׸���
	private double chargeAmountRate = 0.0;//��������
	
	
	private double recognizanceAmounDeductible = 0.0d;//�ѿ۳���֤����
	private double rePayAmount = 0.0d;//�ѻ�������
	private long   nPayType=-1;//��𳥻���ʽ
	private long   lInterestCountType=-1;//��Ϣ���㷽ʽ
	//����������������
	
	private InutParameterInfo inutParameterInfo=null;
	private String subTypeName = "";//���������� mzh_fu 2007/06/12
	
	private double leftoversAttornmentAmount = 0.00;//δת��ծȨ��� added by qhzhou 2008/04/17
	
	private long payID = -1;  //�ſID
	private String payCode = "";  //�ſ���
	
	private long loanacountid=-1;//�����ڲ��˻�id���Ŵ��ʲ�ת��ʹ����Ӫ�����˻���
	
	//add by 	yunchang
	//date 		2010-08-19
	//function 	����--����--�������޻���--ҵ����
	private double ContractHireAmountForYSALL = 0.0d;
	
	private double discountAccrual = 0;
	private double PurchaseAmount = 0;
	
	private String dailyDate;
	
	private long isRemitCompoundInterest 	= -1; //�Ƿ���㸴��
	private long isRemitOverDueInterest 	= -1; //�Ƿ���㷣Ϣ
    private double overDueAdjustRate = 0.0 ; //��������
    private double overDueStaidAdjustRate = 0.0 ; //�̶�������
    private double overDueInterestRate = 0.0 ; //����	
    
    private long isBuyInto = -1; //�Ƿ������ʲ�
    
    // ������-��
	private String ApplyCodeFrom;
	// ������-��
	private String ApplyCodeTo;
	
	//mLoanAmount ���-��
	private double LoanAmountFrom;
	
	//mLoanAmount ���-��
	private double LoanAmountTo;
	
	//sContractCode:��ͬ���-��
	private String ContractCodeFrom;

	//sContractCode:��ͬ���-��
	private String ContractCodeTo;
	
	
	public long getIsBuyInto() {
		return isBuyInto;
	}

	public void setIsBuyInto(long isBuyInto) {
		this.isBuyInto = isBuyInto;
	}

	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}

	public double getDiscountAccrual() {
		return discountAccrual;
	}

	public void setDiscountAccrual(double discountAccrual) {
		this.discountAccrual = discountAccrual;
	}

	public double getPurchaseAmount() {
		return PurchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount) {
		PurchaseAmount = purchaseAmount;
	}

	public double getContractHireAmountForYSALL() {
		return ContractHireAmountForYSALL;
	}

	public void setContractHireAmountForYSALL(double contractHireAmountForYSALL) {
		ContractHireAmountForYSALL = contractHireAmountForYSALL;
	}

	public long getLoanacountid() {
		return loanacountid;
	}

	public void setLoanacountid(long loanacountid) {
		this.loanacountid = loanacountid;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public long getPayID() {
		return payID;
	}

	public void setPayID(long payID) {
		this.payID = payID;
	}

	public double getLeftoversAttornmentAmount() {
		return leftoversAttornmentAmount;
	}

	public void setLeftoversAttornmentAmount(double leftoversAttornmentAmount) {
		this.leftoversAttornmentAmount = leftoversAttornmentAmount;
	}

	public long getLInterestCountType() {
		return lInterestCountType;
	}

	public void setLInterestCountType(long interestCountType) {
		lInterestCountType = interestCountType;
	}

	public double getRecognizanceAmounDeductible() {
		return recognizanceAmounDeductible;
	}

	public void setRecognizanceAmounDeductible(double recognizanceAmounDeductible) {
		this.recognizanceAmounDeductible = recognizanceAmounDeductible;
	}

	public double getRePayAmount() {
		return rePayAmount;
	}

	public void setRePayAmount(double rePayAmount) {
		this.rePayAmount = rePayAmount;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public long getInterestCountTypeID() {
		return interestCountTypeID;
	}

	public void setInterestCountTypeID(long interestCountTypeID) {
		this.interestCountTypeID = interestCountTypeID;
	}

	public double getMatureNominalAmount() {
		return matureNominalAmount;
	}

	public void setMatureNominalAmount(double matureNominalAmount) {
		this.matureNominalAmount = matureNominalAmount;
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
	 * @return
	 */
	public YTFormatInfo getYTInfo()
	{
		return YTInfo;
	}

	/**
	 * @param info
	 */
	public void setYTInfo(YTFormatInfo info)
	{
		YTInfo = info;
	}

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
		return ExamineAmount - CheckAmount;
	}

	/**
	* @return
	*/
	public String getFormatDiscountInterest()
	{
		return DataFormat.formatDisabledAmount(ExamineAmount - CheckAmount);
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
	 * @param d
	 */
	public void setDiscountInterest(double d)
	{
		DiscountInterest = d;
	}

	/**
	 * Returns the assureAddress.
	 * @return String
	 */
	public String getAssureAddress()
	{
		return AssureAddress;
	}

	/**
	 * Returns the assureBank.
	 * @return String
	 */
	public String getAssureBank()
	{
		return AssureBank;
	}

	/**
	 * Returns the assureCode.
	 * @return String
	 */
	public String getAssureCode()
	{
		return AssureCode;
	}

	/**
	 * Returns the assureName.
	 * @return String
	 */
	public String getAssureName()
	{
		return AssureName;
	}

	/**
	 * Returns the borrowAddress.
	 * @return String
	 */
	public String getBorrowAddress()
	{
		return BorrowAddress;
	}

	/**
	 * Returns the borrowBank.
	 * @return String
	 */
	public String getBorrowBank()
	{
		return BorrowBank;
	}

	/**
	 * Returns the borrowCode.
	 * @return String
	 */
	public String getBorrowCode()
	{
		return BorrowCode;
	}

	/**
	 * Returns the borrowName.
	 * @return String
	 */
	public String getBorrowName()
	{
		return BorrowName;
	}

	/**
	 * Returns the consignAddress.
	 * @return String
	 */
	public String getConsignAddress()
	{
		return ConsignAddress;
	}

	/**
	 * Returns the consignBank.
	 * @return String
	 */
	public String getConsignBank()
	{
		return ConsignBank;
	}

	/**
	 * Returns the consignCode.
	 * @return String
	 */
	public String getConsignCode()
	{
		return ConsignCode;
	}

	/**
	 * Returns the consignName.
	 * @return String
	 */
	public String getConsignName()
	{
		return ConsignName;
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
	 * Sets the assureAddress.
	 * @param assureAddress The assureAddress to set
	 */
	public void setAssureAddress(String assureAddress)
	{
		AssureAddress = assureAddress;
	}

	/**
	 * Sets the assureBank.
	 * @param assureBank The assureBank to set
	 */
	public void setAssureBank(String assureBank)
	{
		AssureBank = assureBank;
	}

	/**
	 * Sets the assureCode.
	 * @param assureCode The assureCode to set
	 */
	public void setAssureCode(String assureCode)
	{
		AssureCode = assureCode;
	}

	/**
	 * Sets the assureName.
	 * @param assureName The assureName to set
	 */
	public void setAssureName(String assureName)
	{
		AssureName = assureName;
	}

	/**
	 * Sets the borrowAddress.
	 * @param borrowAddress The borrowAddress to set
	 */
	public void setBorrowAddress(String borrowAddress)
	{
		BorrowAddress = borrowAddress;
	}

	/**
	 * Sets the borrowBank.
	 * @param borrowBank The borrowBank to set
	 */
	public void setBorrowBank(String borrowBank)
	{
		BorrowBank = borrowBank;
	}

	/**
	 * Sets the borrowCode.
	 * @param borrowCode The borrowCode to set
	 */
	public void setBorrowCode(String borrowCode)
	{
		BorrowCode = borrowCode;
	}

	/**
	 * Sets the borrowName.
	 * @param borrowName The borrowName to set
	 */
	public void setBorrowName(String borrowName)
	{
		BorrowName = borrowName;
	}

	/**
	 * Sets the consignAddress.
	 * @param consignAddress The consignAddress to set
	 */
	public void setConsignAddress(String consignAddress)
	{
		ConsignAddress = consignAddress;
	}

	/**
	 * Sets the consignBank.
	 * @param consignBank The consignBank to set
	 */
	public void setConsignBank(String consignBank)
	{
		ConsignBank = consignBank;
	}

	/**
	 * Sets the consignCode.
	 * @param consignCode The consignCode to set
	 */
	public void setConsignCode(String consignCode)
	{
		ConsignCode = consignCode;
	}

	/**
	 * Sets the consignName.
	 * @param consignName The consignName to set
	 */
	public void setConsignName(String consignName)
	{
		ConsignName = consignName;
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
	public Collection getCContractContent()
	{
		return cContractContent;
	}

	/**
	 * @return
	 */
	public long getExtendCount()
	{
		return extendCount;
	}

	/**
	 * @return
	 */
	public long getFreeCount()
	{
		return freeCount;
	}

	/**
	 * @param collection
	 */
	public void setCContractContent(Collection collection)
	{
		cContractContent = collection;
	}

	/**
	 * @param l
	 */
	public void setExtendCount(long l)
	{
		extendCount = l;
	}

	/**
	 * @param l
	 */
	public void setFreeCount(long l)
	{
		freeCount = l;
	}

	/**
	 * @return
	 */
	public ContractAmountInfo getAInfo()
	{
		return aInfo;
	}

	/**
	 * @param info
	 */
	public void setAInfo(ContractAmountInfo info)
	{
		aInfo = info;
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
     * function �õ�/����
     * return double
     */
    public double getScale()
    {
        return Scale;
    }

    /**
     * @param d
     * function �õ�/����
     * return void
     */
    public void setScale(double d)
    {
        this.Scale = d;
    }

	/**
	 * Returns the overdueAmount.
	 * @return double
	 */
	public double getOverdueAmount() {
		return overdueAmount;
	}

	/**
	 * Returns the punishInterest.
	 * @return double
	 */
	public double getPunishInterest() {
		return punishInterest;
	}

	/**
	 * Sets the overdueAmount.
	 * @param overdueAmount The overdueAmount to set
	 */
	public void setOverdueAmount(double overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	/**
	 * Sets the punishInterest.
	 * @param punishInterest The punishInterest to set
	 */
	public void setPunishInterest(double punishInterest) {
		this.punishInterest = punishInterest;
	}

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getAssureAccount()
    {
        return AssureAccount;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getAssureZip()
    {
        return AssureZip;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getBorrowAccount()
    {
        return BorrowAccount;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getBorrowZip()
    {
        return BorrowZip;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getConsignAccount()
    {
        return ConsignAccount;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getConsignZip()
    {
        return ConsignZip;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAssureAccount(String string)
    {
        AssureAccount = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setAssureZip(String string)
    {
        AssureZip = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBorrowAccount(String string)
    {
        BorrowAccount = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setBorrowZip(String string)
    {
        BorrowZip = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setConsignAccount(String string)
    {
        ConsignAccount = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setConsignZip(String string)
    {
        ConsignZip = string;
    }

	/**
	 * @return
	 */
	public double getLastAttornmentAmount()
	{
		return lastAttornmentAmount;
	}

	/**
	 * @param d
	 */
	public void setLastAttornmentAmount(double d)
	{
		lastAttornmentAmount = d;
	}

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return nextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        this.nextCheckLevel = nextCheckLevel;
    }
	/**
	 * @return
	 */
	public double getBalanceForAttornment()
	{
		return balanceForAttornment;
	}

	/**
	 * @param d
	 */
	public void setBalanceForAttornment(double d)
	{
		balanceForAttornment = d;
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
	 * @return
	 */
	public double getUseCreditAmount()
	{
		return useCreditAmount;
	}

	/**
	 * @param d
	 */
	public void setUseCreditAmount(double d)
	{
		useCreditAmount = d;
	}

	/**
	 * @return
	 */
	public double getExamineSelfAmount()
	{
		return mExamineSelfAmount;
	}

	/**
	 * @param d
	 */
	public void setExamineSelfAmount(double d)
	{
		mExamineSelfAmount = d;
	}

    /**
     * @return Returns the discountClientID.
     */
    public long getDiscountClientID()
    {
        return discountClientID;
    }
    /**
     * @param discountClientID The discountClientID to set.
     */
    public void setDiscountClientID(long discountClientID)
    {
        this.discountClientID = discountClientID;
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
    }
    /**
     * @return Returns the discountClientName.
     */
    public String getDiscountClientName()
    {
        return discountClientName;
    }
    /**
     * @param discountClientName The discountClientName to set.
     */
    public void setDiscountClientName(String discountClientName)
    {
        this.discountClientName = discountClientName;
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
    }
    /**
     * @return Returns the assureChargeRate.
     */
    public double getAssureChargeRate()
    {
        return assureChargeRate;
    }
    /**
     * @param assureChargeRate The assureChargeRate to set.
     */
    public void setAssureChargeRate(double assureChargeRate)
    {
        this.assureChargeRate = assureChargeRate;
    }
    /**
     * @return Returns the assureChargeTypeID.
     */
    public long getAssureChargeTypeID()
    {
        return assureChargeTypeID;
    }
    /**
     * @param assureChargeTypeID The assureChargeTypeID to set.
     */
    public void setAssureChargeTypeID(long assureChargeTypeID)
    {
        this.assureChargeTypeID = assureChargeTypeID;
    }
    /**
     * @return Returns the assureTypeID1.
     */
    public long getAssureTypeID1()
    {
        return assureTypeID1;
    }
    /**
     * @param assureTypeID1 The assureTypeID1 to set.
     */
    public void setAssureTypeID1(long assureTypeID1)
    {
        this.assureTypeID1 = assureTypeID1;
    }
    /**
     * @return Returns the assureTypeID2.
     */
    public long getAssureTypeID2()
    {
        return assureTypeID2;
    }
    /**
     * @param assureTypeID2 The assureTypeID2 to set.
     */
    public void setAssureTypeID2(long assureTypeID2)
    {
        this.assureTypeID2 = assureTypeID2;
    }
    /**
     * @return Returns the beneficiary.
     */
    public String getBeneficiary()
    {
        return beneficiary;
    }
    /**
     * @param beneficiary The beneficiary to set.
     */
    public void setBeneficiary(String beneficiary)
    {
        this.beneficiary = beneficiary;
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
	 * @return
	 */
	public double getRecognizanceAmount()
	{
		return recognizanceAmount;
	}

	/**
	 * @param d
	 */
	public void setRecognizanceAmount(double d)
	{
		recognizanceAmount = d;
	}

	/**
	 * @return
	 */
	public double getCurrentRecognizanceAmount()
	{
		return currentRecognizanceAmount;
	}

	/**
	 * @return
	 */
	public double getSumAssureAmount()
	{
		return sumAssureAmount;
	}

	/**
	 * @return
	 */
	public double getSumAssureBanlance()
	{
		return sumAssureBanlance;
	}

	/**
	 * @param d
	 */
	public void setCurrentRecognizanceAmount(double d)
	{
		currentRecognizanceAmount = d;
	}

	/**
	 * @param d
	 */
	public void setSumAssureAmount(double d)
	{
		sumAssureAmount = d;
	}

	/**
	 * @param d
	 */
	public void setSumAssureBanlance(double d)
	{
		sumAssureBanlance = d;
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
	 * @return Returns the assureBalance.
	 */
	public double getAssureBalance()
	{
		return AssureBalance;
	}
	/**
	 * @param assureBalance The assureBalance to set.
	 */
	public void setAssureBalance(double assureBalance)
	{
		AssureBalance = assureBalance;
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
     * @return Returns the discountPurchaserInterest.
     */
    public double getDiscountPurchaserInterest()
    {
        return discountPurchaserInterest;
    }
    /**
     * @param discountPurchaserInterest The discountPurchaserInterest to set.
     */
    public void setDiscountPurchaserInterest(double discountPurchaserInterest)
    {
        this.discountPurchaserInterest = discountPurchaserInterest;
    }
	/**
	 * @return Returns the lateRateString.
	 */
	public String getLateRateString() {
		return lateRateString;
	}
	/**
	 * @param lateRateString The lateRateString to set.
	 */
	public void setLateRateString(String lateRateString) {
		this.lateRateString = lateRateString;
	}
	/**
	 * @return Returns the lInputUserID.
	 */
	public long getLInputUserID() {
		return lInputUserID;
	}
	/**
	 * @param inputUserID The lInputUserID to set.
	 */
	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}
	/**
	 * @return Returns the mExamineSelfAmount.
	 */
	public double getMExamineSelfAmount() {
		return mExamineSelfAmount;
	}
	/**
	 * @param examineSelfAmount The mExamineSelfAmount to set.
	 */
	public void setMExamineSelfAmount(double examineSelfAmount) {
		mExamineSelfAmount = examineSelfAmount;
	}
	/**
	 * @return Returns the sOther.
	 */
	public String getSOther() {
		return sOther;
	}
	/**
	 * @param other The sOther to set.
	 */
	public void setSOther(String other) {
		sOther = other;
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
 
	/**
	 * ��ͬ�������� 2006-3-15
	 * Returns the lastExecDate.
	 * @return Timestamp
	 */
	public Timestamp getLastExecDate() {
		return lastExecDate;
	}

	/**
	 * ��ͬ�������� 2006-3-15
	 * Sets the lastExecDate.
	 * @param lastExecDate The lastExecDate to set
	 */
	public void setLastExecDate(Timestamp lastExecDate) {
		this.lastExecDate = lastExecDate;
	}

	public double getReceivedRecognizanceAmount() {
		return receivedRecognizanceAmount;
	}

	public void setReceivedRecognizanceAmount(double receivedRecognizanceAmount) {
		this.receivedRecognizanceAmount = receivedRecognizanceAmount;
	}

	public double getReturnedRecognizanceAmount() {
		return returnedRecognizanceAmount;
	}

	public void setReturnedRecognizanceAmount(double returnedRecognizanceAmount) {
		this.returnedRecognizanceAmount = returnedRecognizanceAmount;
	}

	public long getNPayType() {
		return nPayType;
	}

	public void setNPayType(long payType) {
		nPayType = payType;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
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

	public long getIsRepurchase() {
		return IsRepurchase;
	}

	public void setIsRepurchase(long isRepurchase) {
		IsRepurchase = isRepurchase;
	}

	public double getOrigionAmount() {
		return origionAmount;
	}

	public void setOrigionAmount(double origionAmount) {
		this.origionAmount = origionAmount;
	}

	public double getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(double preAmount) {
		this.preAmount = preAmount;
	}

	public double getChargeAmountRate() {
		return chargeAmountRate;
	}

	public void setChargeAmountRate(double chargeAmountRate) {
		this.chargeAmountRate = chargeAmountRate;
	}

	public double getDailyBalance() {
		return dailyBalance;
	}

	public void setDailyBalance(double dailyBalance) {
		this.dailyBalance = dailyBalance;
	}

	public double getMDiscountAccrual() {
		return mDiscountAccrual;
	}

	public void setMDiscountAccrual(double discountAccrual) {
		mDiscountAccrual = discountAccrual;
	}

	public long getIsRemitCompoundInterest() {
		return isRemitCompoundInterest;
	}

	public void setIsRemitCompoundInterest(long isRemitCompoundInterest) {
		this.isRemitCompoundInterest = isRemitCompoundInterest;
	}

	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}

	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}

	public double getOverDueAdjustRate() {
		return overDueAdjustRate;
	}

	public void setOverDueAdjustRate(double overDueAdjustRate) {
		this.overDueAdjustRate = overDueAdjustRate;
	}

	public double getOverDueStaidAdjustRate() {
		return overDueStaidAdjustRate;
	}

	public void setOverDueStaidAdjustRate(double overDueStaidAdjustRate) {
		this.overDueStaidAdjustRate = overDueStaidAdjustRate;
	}

	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}

	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	public String getApplyCodeFrom() {
		return ApplyCodeFrom;
	}

	public void setApplyCodeFrom(String applyCodeFrom) {
		ApplyCodeFrom = applyCodeFrom;
	}

	public String getApplyCodeTo() {
		return ApplyCodeTo;
	}

	public void setApplyCodeTo(String applyCodeTo) {
		ApplyCodeTo = applyCodeTo;
	}

	public double getLoanAmountFrom() {
		return LoanAmountFrom;
	}

	public void setLoanAmountFrom(double loanAmountFrom) {
		LoanAmountFrom = loanAmountFrom;
	}

	public double getLoanAmountTo() {
		return LoanAmountTo;
	}

	public void setLoanAmountTo(double loanAmountTo) {
		LoanAmountTo = loanAmountTo;
	}

	public String getContractCodeFrom() {
		return ContractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		ContractCodeFrom = contractCodeFrom;
	}

	public String getContractCodeTo() {
		return ContractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		ContractCodeTo = contractCodeTo;
	}
}

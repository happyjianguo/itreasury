/*
 * Created on 2003-10-30
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryAccountWhereInfo extends BaseDataEntity implements Serializable
{

	/**
	 * 
	 */
	public QueryAccountWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	// ���ò�ѯ����
	private long EnterpriseTypeID1 = -1;//�ͻ�����1
	private long EnterpriseTypeID2 = -1;//�ͻ�����2
	private long EnterpriseTypeID3 = -1;//�ͻ�����3
	private long EnterpriseTypeID4 = -1;//�ͻ�����4
	private long EnterpriseTypeID5 = -1;//�ͻ�����5
	private long EnterpriseTypeID6 = -1;//�ͻ�����6
	
	private long unit = 1;
	
	public long getUnit() {
		return unit;
	}
	public void setUnit(long unit) {
		this.unit = unit;
	}
	/**
	 * @return Returns the enterpriseTypeID1.
	 */
	public long getEnterpriseTypeID1() {
		return EnterpriseTypeID1;
	}
	/**
	 * @param enterpriseTypeID1 The enterpriseTypeID1 to set.
	 */
	public void setEnterpriseTypeID1(long enterpriseTypeID1) {
		EnterpriseTypeID1 = enterpriseTypeID1;
	}
	/**
	 * @return Returns the enterpriseTypeID2.
	 */
	public long getEnterpriseTypeID2() {
		return EnterpriseTypeID2;
	}
	/**
	 * @param enterpriseTypeID2 The enterpriseTypeID2 to set.
	 */
	public void setEnterpriseTypeID2(long enterpriseTypeID2) {
		EnterpriseTypeID2 = enterpriseTypeID2;
	}
	/**
	 * @return Returns the enterpriseTypeID3.
	 */
	public long getEnterpriseTypeID3() {
		return EnterpriseTypeID3;
	}
	/**
	 * @param enterpriseTypeID3 The enterpriseTypeID3 to set.
	 */
	public void setEnterpriseTypeID3(long enterpriseTypeID3) {
		EnterpriseTypeID3 = enterpriseTypeID3;
	}
	/**
	 * @return Returns the enterpriseTypeID4.
	 */
	public long getEnterpriseTypeID4() {
		return EnterpriseTypeID4;
	}
	/**
	 * @param enterpriseTypeID4 The enterpriseTypeID4 to set.
	 */
	public void setEnterpriseTypeID4(long enterpriseTypeID4) {
		EnterpriseTypeID4 = enterpriseTypeID4;
	}
	/**
	 * @return Returns the enterpriseTypeID5.
	 */
	public long getEnterpriseTypeID5() {
		return EnterpriseTypeID5;
	}
	/**
	 * @param enterpriseTypeID5 The enterpriseTypeID5 to set.
	 */
	public void setEnterpriseTypeID5(long enterpriseTypeID5) {
		EnterpriseTypeID5 = enterpriseTypeID5;
	}
	/**
	 * @return Returns the enterpriseTypeID6.
	 */
	public long getEnterpriseTypeID6() {
		return EnterpriseTypeID6;
	}
	/**
	 * @param enterpriseTypeID6 The enterpriseTypeID6 to set.
	 */
	public void setEnterpriseTypeID6(long enterpriseTypeID6) {
		EnterpriseTypeID6 = enterpriseTypeID6;
	}
	private long OfficeID = -1; // ���´�
	private long CurrencyID = -1; // ����
	private String StartClientCode = ""; // �ͻ���-��
	private String EndClientCode = ""; // �ͻ���-��
	private String StartAccountNo = ""; // �˻���-��
	private String EndAccountNo = ""; // �˻���=��
	
	private boolean isSearchSum = false;//add by fxzhang 2012-6-8 �Ƿ�Ϊ������ѯ  ��������ѯʱΪtrue��������ѯΪfalse
		
	//add by 2012-05-15 ���ָ�����
	private String appointAccountNo = "";
	private Timestamp OpenDateFrom = null;
	private Timestamp OpenDateTo = null;
	private Timestamp FinishDateFrom = null;
	private Timestamp FinishDateTo = null;
	
	private long AccountTypeID = -1; // �˻�����
	private String AccountTypeSet = ""; // �˻������� 
	private long AccountStatusID = -1; // �˻�״̬
	private long ClientID = -1; // �ͻ���
	private long AccountID = -1; // �˻���
	private long ClientManager = -1;//�ͻ�����id
		
	//// ��ѯ���ڣ�����ǵ��գ�������Ϣ��Դ�ڱ�sett_subAccount;
	// ������ǵ��գ�������Ϣ��Դ�ڱ�sett_DailyAccountBalance��

	private long IsFilterNull = -1; // �Ƿ��˿գ����˵��ﺬ���ǲ������廧���˻���
	private long IsValidAccount = -1; // �Ƿ���Ч�˻�
	private long IsNegotiate = -1; // �Ƿ�Э�����
	private double NegotiateRate = -1; //Э���������
	//
	private Timestamp QueryDate = null;
	private Timestamp StartQueryDate = null;
	private Timestamp EndQueryDate = null;
    private long IntervalDays = 0; // �������
	//
	private long RemarkDetailID = -1;
	private long TransDirecttion = -1;
	//
	private long Desc = 1;
	private long OrderField = 1;
	// ���ڲ�ѯ����
	private String StartFixFormNo = ""; // ���ڴ浥��-��
	private String EndFixFormNo = ""; // ���ڴ浥��-��
	private long StartFixPeriod = -1; // ���ڴ�����ޣ��£�-��
	private long EndFixPeriod = -1; // ���ڴ�����ޣ��£�-��
	// �����ѯ����
	private long LoanType = -1; // �������� 
	private long ContractStatusID = -1; // ��ͬ״̬ 
	private double StartContractAmount = 0.0; // �����ͬ���-��
	private double EndContractAmount = 0.0; // �����ͬ���-��
	private double StartLoanpayAmount = 0.0; // �ſ���-��
	private double EndLoanpayAmount = 0.0; // �ſ���-��
	private long StartContractPeriod = -1; // �������ޣ��£�-�� 
	private long EndContractPeriod = -1; // �������ޣ��£�-�� 
	private long ContractYear = -1; // ��������
	private String StartContractCode = ""; // ��ͬ��-�� 
	private String EndContractCode = ""; // ��ͬ��-�� 
	private String StartLoanPayCode = ""; // �ſ��-�� 
	private String EndLoanPayCode = ""; // �ſ��-�� 
	private long LoanPayStatusID = -1; // �ſ״̬
	private long ConsignClientID = -1; // ί�з� 
	private Timestamp StartContractStartDate = null; // ��ͬ��ʼ����-�� 
	private Timestamp EndContractStartDate = null; // ��ͬ��ʼ����-�� 
	private Timestamp StartContractEndDate = null; //  ��ͬ��������-��
	private Timestamp EndContractEndDate = null; //  ��ͬ��������-��
	private double StartContractInterestRate = 0.0; // ��ͬ����-�� 
	private double EndContractInterestRate = 0.0; // ��ͬ����-�� 
	private Timestamp StartLoanPayStartDate = null; // �ſʼ����-�� 
	private Timestamp EndLoanPayStartDate = null; // �ſʼ����-�� 
	private Timestamp StartLoanPayEnddate = null; // �ſ��������-�� 
	private Timestamp EndLoanPayEnddate = null; // �ſ��������-�� 
	private long Industrytype1 = -1; // ��ͬ��ҵ����1 
	private long Industrytype2 = -1; // ��ͬ��ҵ����2
	//2008-11-4���� kaishao
	private long Industrytype3 = -1; // �Ƿ�����˻����
	//���ӽ��� ���set��get������ͬ���Զ�����
	private long RegiontypeID = -1; // ��ͬ�������� 
	private long Risklevel = -1; // ��ͬ����״̬ 
	private double StartLoanPayRate = 0.0; // �ſ�����-�� 
	private double EndLoanPayRate = 0.0; // �ſ�����-�� 
	// �ͻ���Ϣ
	private long EnterpriseTypeID = -1; // ��ҵ����
	private long ClientTypeID = -1; // �ͻ����� 
	private long IndustryTypeID = -1; // ��ҵ����
	private long ParentCorpID = -1; // ĸ��˾ 
	//
	private long LoanPayAccountID = -1; // ������������˻���
	private long LoanPayBankID = -1; // �������/�ջ������п�����
	
	//�����ֶ�����Ԫ�����
	private long IndustryType1 = -1; //��ͬ��ҵ����1
	private long IndustryType2 = -1; //��ͬ��ҵ����2
	private long DiscountCredenceIDStart = -1; //����Ʊ�ݺ�-��
	private long DiscountCredenceIDEnd = -1; //����Ʊ�ݺ�-��
	private Timestamp DtFinish = null; //��������
	private double ContractAmountStart=0.0; //���-��  �����˻��еĿ������ƥ��
	private double ContractAmountEnd=0.0; //���-�� 
	private long ParentCorpID1 = -1; //�ϼ���λ1
	private long ParentCorpID2 = -1; //�ϼ���λ2
	private long ExtendAttribute1 = -1;					//��չ����1
    private long ExtendAttribute2 = -1;					//��չ����2
    private long ExtendAttribute3 = -1;					//��չ����3
    private long ExtendAttribute4 = -1;					//��չ����4
    
    private long lIsInterestBalance = -1;//����Ϣ������
	
    //������ѡ����������	by ����
    private String[] AccountTypeIDArray = null;
    private String _AccountTypeIDArray = null;
    
    private long lUnit = 1;

	public String toString()
	{
		StringBuffer sbResult = new StringBuffer(128);

		sbResult.append(this.getClass().getName() + " instance (hashCode=" + this.hashCode() + ")\r\n");
		sbResult.append("=========================================\r\n");

		//��õ�ǰ����ָ�����Ƶ�Field����
		java.lang.reflect.Field[] field = null;
		try
		{
			field = this.getClass().getDeclaredFields();

			if (field != null)
			{
				for (int i = 0; i < field.length; i++)
				{
					field[i].setAccessible(true);

					sbResult.append(field[i].getName() + " = ");
					sbResult.append(field[i].get(this) + ";\r\n");
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
		}

		return sbResult.toString();
	}

	/**
	 * @return
	 */
	public long getAccountStatusID()
	{
		return AccountStatusID;
	}

	/**
	 * @param accountStatusID
	 */
	public void setAccountStatusID(long accountStatusID)
	{
		AccountStatusID = accountStatusID;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @param accountTypeID
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}

	/**
	 * @return
	 */
	public long getClientTypeID()
	{
		return ClientTypeID;
	}

	/**
	 * @param clientTypeID
	 */
	public void setClientTypeID(long clientTypeID)
	{
		ClientTypeID = clientTypeID;
	}

	/**
	 * @return
	 */
	public long getConsignClientID()
	{
		return ConsignClientID;
	}

	/**
	 * @param consignClientID
	 */
	public void setConsignClientID(long consignClientID)
	{
		ConsignClientID = consignClientID;
	}

	/**
	 * @return
	 */
	public long getContractStatusID()
	{
		return ContractStatusID;
	}

	/**
	 * @param contractStatusID
	 */
	public void setContractStatusID(long contractStatusID)
	{
		ContractStatusID = contractStatusID;
	}

	/**
	 * @return
	 */
	public long getContractYear()
	{
		return ContractYear;
	}

	/**
	 * @param contractYear
	 */
	public void setContractYear(long contractYear)
	{
		ContractYear = contractYear;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @param currencyID
	 */
	public void setCurrencyID(long currencyID)
	{
		CurrencyID = currencyID;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return Desc;
	}

	/**
	 * @param desc
	 */
	public void setDesc(long desc)
	{
		Desc = desc;
	}

	/**
	 * @return
	 */
	public String getEndAccountNo()
	{
		return EndAccountNo;
	}

	/**
	 * @param endAccountNo
	 */
	public void setEndAccountNo(String endAccountNo)
	{
		EndAccountNo = endAccountNo;
	}
	
	/**
	 * add by 2012-05-15 ���ָ�����
	 * @param appointAccountNo
	 */
	public String getAppointAccountNo() {
		return appointAccountNo;
	}
	/**
	 * @param appointAccountNo
	 */
	public void setAppointAccountNo(String appointAccountNo) {
		this.appointAccountNo = appointAccountNo;
	}
	
	/**
	 * @return
	 */
	public String getEndClientCode()
	{
		return EndClientCode;
	}

	/**
	 * @param endClientCode
	 */
	public void setEndClientCode(String endClientCode)
	{
		EndClientCode = endClientCode;
	}

	/**
	 * @return
	 */
	public double getEndContractAmount()
	{
		return EndContractAmount;
	}

	/**
	 * @param endContractAmount
	 */
	public void setEndContractAmount(double endContractAmount)
	{
		EndContractAmount = endContractAmount;
	}

	/**
	 * @return Returns the clientManage.
	 */
	public long getClientManager() {
		return ClientManager;
	}
	/**
	 * @param clientManage The clientManage to set.
	 */
	public void setClientManager(long clientManager) {
		ClientManager = clientManager;
	}
	
	/**
	 * @return
	 */
	public String getEndContractCode()
	{
		return EndContractCode;
	}

	/**
	 * @param endContractCode
	 */
	public void setEndContractCode(String endContractCode)
	{
		EndContractCode = endContractCode;
	}

	/**
	 * @return
	 */
	public Timestamp getEndContractEndDate()
	{
		return EndContractEndDate;
	}

	/**
	 * @param endContractEndDate
	 */
	public void setEndContractEndDate(Timestamp endContractEndDate)
	{
		EndContractEndDate = endContractEndDate;
	}
	
	/**
	 * @return
	 */
	public double getEndContractInterestRate()
	{
		return EndContractInterestRate;
	}

	/**
	 * @param endContractInterestRate
	 */
	public void setEndContractInterestRate(double endContractInterestRate)
	{
		EndContractInterestRate = endContractInterestRate;
	}

	/**
	 * @return
	 */
	public long getEndContractPeriod()
	{
		return EndContractPeriod;
	}

	/**
	 * @param endContractPeriod
	 */
	public void setEndContractPeriod(long endContractPeriod)
	{
		EndContractPeriod = endContractPeriod;
	}

	/**
	 * @return
	 */
	public Timestamp getEndContractStartDate()
	{
		return EndContractStartDate;
	}

	/**
	 * @param endContractStartDate
	 */
	public void setEndContractStartDate(Timestamp endContractStartDate)
	{
		EndContractStartDate = endContractStartDate;
	}
	/**
	 * @return
	 */
	public Timestamp getEndPayNoteStartDate() {
		return EndLoanPayStartDate;
	}
	/**
	 * @return
	 */
	public Timestamp getEndLoanPayStartDate() {
		return EndLoanPayStartDate;
	}
	/**
	 * @param endPayNoteStartDate
	 */
	public void setEndLoanPayStartDate(Timestamp endPayNoteStartDate) {
		EndLoanPayStartDate = endPayNoteStartDate;
	}
	/**
	 * @return
	 */
	public String getEndFixFormNo()
	{
		return EndFixFormNo;
	}

	/**
	 * @param endFixFormNo
	 */
	public void setEndFixFormNo(String endFixFormNo)
	{
		EndFixFormNo = endFixFormNo;
	}

	/**
	 * @return
	 */
	public long getEndFixPeriod()
	{
		return EndFixPeriod;
	}

	/**
	 * @param endFixPeriod
	 */
	public void setEndFixPeriod(long endFixPeriod)
	{
		EndFixPeriod = endFixPeriod;
	}

	/**
	 * @return
	 */
	public double getEndLoanpayAmount()
	{
		return EndLoanpayAmount;
	}

	/**
	 * @param endLoanpayAmount
	 */
	public void setEndLoanpayAmount(double endLoanpayAmount)
	{
		EndLoanpayAmount = endLoanpayAmount;
	}

	/**
	 * @return
	 */
	public String getEndLoanPayCode()
	{
		return EndLoanPayCode;
	}

	/**
	 * @param endLoanPayCode
	 */
	public void setEndLoanPayCode(String endLoanPayCode)
	{
		EndLoanPayCode = endLoanPayCode;
	}

	/**
	 * @return
	 */
	public Timestamp getEndLoanPayEnddate()
	{
		return EndLoanPayEnddate;
	}

	/**
	 * @param endLoanPayEnddate
	 */
	public void setEndLoanPayEnddate(Timestamp endLoanPayEnddate)
	{
		EndLoanPayEnddate = endLoanPayEnddate;
	}

	/**
	 * @return
	 */
	public double getEndLoanPayRate()
	{
		return EndLoanPayRate;
	}

	/**
	 * @param endLoanPayRate
	 */
	public void setEndLoanPayRate(double endLoanPayRate)
	{
		EndLoanPayRate = endLoanPayRate;
	}

	

	/**
	 * @return
	 */
	public long getEnterpriseTypeID()
	{
		return EnterpriseTypeID;
	}

	/**
	 * @param enterpriseTypeID
	 */
	public void setEnterpriseTypeID(long enterpriseTypeID)
	{
		EnterpriseTypeID = enterpriseTypeID;
	}

	/**
	 * @return
	 */
	public long getIndustrytype1()
	{
		return Industrytype1;
	}

	/**
	 * @param industrytype1
	 */
	public void setIndustrytype1(long industrytype1)
	{
		Industrytype1 = industrytype1;
	}

	/**
	 * @return
	 */
	public long getIndustrytype2()
	{
		return Industrytype2;
	}

	/**
	 * @param industrytype2
	 */
	public void setIndustrytype2(long industrytype2)
	{
		Industrytype2 = industrytype2;
	}

	/**
	 * @return
	 */
	public long getIndustryTypeID()
	{
		return IndustryTypeID;
	}

	/**
	 * @param industryTypeID
	 */
	public void setIndustryTypeID(long industryTypeID)
	{
		IndustryTypeID = industryTypeID;
	}

	/**
	 * @return
	 */
	public long getIsFilterNull()
	{
		return IsFilterNull;
	}

	/**
	 * @param isFilterNull
	 */
	public void setIsFilterNull(long isFilterNull)
	{
		IsFilterNull = isFilterNull;
	}

	/**
	 * @return
	 */
	public long getIsNegotiate()
	{
		return IsNegotiate;
	}

	/**
	 * @param isNegotiate
	 */
	public void setIsNegotiate(long isNegotiate)
	{
		IsNegotiate = isNegotiate;
	}

	/**
	 * @return
	 */
	public long getIsValidAccount()
	{
		return IsValidAccount;
	}

	/**
	 * @param isValidAccount
	 */
	public void setIsValidAccount(long isValidAccount)
	{
		IsValidAccount = isValidAccount;
	}

	/**
	 * @return
	 */
	public long getLoanPayAccountID()
	{
		return LoanPayAccountID;
	}

	/**
	 * @param loanPayAccountID
	 */
	public void setLoanPayAccountID(long loanPayAccountID)
	{
		LoanPayAccountID = loanPayAccountID;
	}

	/**
	 * @return
	 */
	public long getLoanPayBankID()
	{
		return LoanPayBankID;
	}

	/**
	 * @param loanPayBankID
	 */
	public void setLoanPayBankID(long loanPayBankID)
	{
		LoanPayBankID = loanPayBankID;
	}

	/**
	 * @return
	 */
	public long getLoanPayStatusID()
	{
		return LoanPayStatusID;
	}

	/**
	 * @param loanPayStatusID
	 */
	public void setLoanPayStatusID(long loanPayStatusID)
	{
		LoanPayStatusID = loanPayStatusID;
	}

	/**
	 * @return
	 */
	public long getLoanType()
	{
		return LoanType;
	}

	/**
	 * @param loanType
	 */
	public void setLoanType(long loanType)
	{
		LoanType = loanType;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param officeID
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * @return
	 */
	public long getOrderField()
	{
		return OrderField;
	}

	/**
	 * @param orderField
	 */
	public void setOrderField(long orderField)
	{
		OrderField = orderField;
	}

	/**
	 * @return
	 */
	public long getParentCorpID()
	{
		return ParentCorpID;
	}

	/**
	 * @param parentCorpID
	 */
	public void setParentCorpID(long parentCorpID)
	{
		ParentCorpID = parentCorpID;
	}

	/**
	 * @return
	 */
	public Timestamp getQueryDate()
	{
		return QueryDate;
	}

	/**
	 * @param queryDate
	 */
	public void setQueryDate(Timestamp queryDate)
	{
		QueryDate = queryDate;
	}

	/**
	 * @return
	 */
	public long getRegiontypeID()
	{
		return RegiontypeID;
	}

	/**
	 * @param regiontypeID
	 */
	public void setRegiontypeID(long regiontypeID)
	{
		RegiontypeID = regiontypeID;
	}

	/**
	 * @return
	 */
	public long getRisklevel()
	{
		return Risklevel;
	}

	/**
	 * @param risklevel
	 */
	public void setRisklevel(long risklevel)
	{
		Risklevel = risklevel;
	}

	/**
	 * @return
	 */
	public String getStartAccountNo()
	{
		return StartAccountNo;
	}

	/**
	 * @param startAccountNo
	 */
	public void setStartAccountNo(String startAccountNo)
	{
		StartAccountNo = startAccountNo;
	}

	/**
	 * @return
	 */
	public String getStartClientCode()
	{
		return StartClientCode;
	}

	/**
	 * @param startClientCode
	 */
	public void setStartClientCode(String startClientCode)
	{
		StartClientCode = startClientCode;
	}

	/**
	 * @return
	 */
	public double getStartContractAmount()
	{
		return StartContractAmount;
	}

	/**
	 * @param startContractAmount
	 */
	public void setStartContractAmount(double startContractAmount)
	{
		StartContractAmount = startContractAmount;
	}

	/**
	 * @return
	 */
	public String getStartContractCode()
	{
		return StartContractCode;
	}

	/**
	 * @param startContractCode
	 */
	public void setStartContractCode(String startContractCode)
	{
		StartContractCode = startContractCode;
	}

	/**
	 * @return
	 */
	public Timestamp getStartContractEndDate()
	{
		return StartContractEndDate;
	}

	/**
	 * @param startContractEndDate
	 */
	public void setStartContractEndDate(Timestamp startContractEndDate)
	{
		StartContractEndDate = startContractEndDate;
	}
	
	/**
	 * @return
	 */
	public double getStartContractInterestRate()
	{
		return StartContractInterestRate;
	}

	/**
	 * @param startContractInterestRate
	 */
	public void setStartContractInterestRate(double startContractInterestRate)
	{
		StartContractInterestRate = startContractInterestRate;
	}

	/**
	 * @return
	 */
	public long getStartContractPeriod()
	{
		return StartContractPeriod;
	}

	/**
	 * @param startContractPeriod
	 */
	public void setStartContractPeriod(long startContractPeriod)
	{
		StartContractPeriod = startContractPeriod;
	}

	/**
	 * @return
	 */
	public Timestamp getStartContractStartDate()
	{
		return StartContractStartDate;
	}

	/**
	 * @param startContractStartDate
	 */
	public void setStartContractStartDate(Timestamp startContractStartDate)
	{
		StartContractStartDate = startContractStartDate;
	}
	
	/**
	 * @return
	 */
	public String getStartFixFormNo()
	{
		return StartFixFormNo;
	}

	/**
	 * @param startFixFormNo
	 */
	public void setStartFixFormNo(String startFixFormNo)
	{
		StartFixFormNo = startFixFormNo;
	}

	/**
	 * @return
	 */
	public long getStartFixPeriod()
	{
		return StartFixPeriod;
	}

	/**
	 * @param startFixPeriod
	 */
	public void setStartFixPeriod(long startFixPeriod)
	{
		StartFixPeriod = startFixPeriod;
	}

	/**
	 * @return
	 */
	public double getStartLoanpayAmount()
	{
		return StartLoanpayAmount;
	}

	/**
	 * @param startLoanpayAmount
	 */
	public void setStartLoanpayAmount(double startLoanpayAmount)
	{
		StartLoanpayAmount = startLoanpayAmount;
	}

	/**
	 * @return
	 */
	public String getStartLoanPayCode()
	{
		return StartLoanPayCode;
	}

	/**
	 * @param startLoanPayCode
	 */
	public void setStartLoanPayCode(String startLoanPayCode)
	{
		StartLoanPayCode = startLoanPayCode;
	}

	/**
	 * @return
	 */
	public Timestamp getStartLoanPayEnddate()
	{
		return StartLoanPayEnddate;
	}

	/**
	 * @param startLoanPayEnddate
	 */
	public void setStartLoanPayEnddate(Timestamp startLoanPayEnddate)
	{
		StartLoanPayEnddate = startLoanPayEnddate;
	}

	/**
	 * @return
	 */
	public double getStartLoanPayRate()
	{
		return StartLoanPayRate;
	}

	/**
	 * @param startLoanPayRate
	 */
	public void setStartLoanPayRate(double startLoanPayRate)
	{
		StartLoanPayRate = startLoanPayRate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartLoanPayStartDate()
	{
		return StartLoanPayStartDate;
	}

	/**
	 * @param startLoanPayStartDate
	 */
	public void setStartLoanPayStartDate(Timestamp startLoanPayStartDate)
	{
		StartLoanPayStartDate = startLoanPayStartDate;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @param accountID
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param clientID
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
	}

	/**
	 * @return
	 */
	public String getAccountTypeSet()
	{
		return AccountTypeSet;
	}

	/**
	 * @param accountTypeSet
	 */
	public void setAccountTypeSet(String accountTypeSet)
	{
		AccountTypeSet = accountTypeSet;
	}

	/**
	 * @return
	 */
	public Timestamp getEndQueryDate()
	{
		return EndQueryDate;
	}

	/**
	 * @param endQueryDate
	 */
	public void setEndQueryDate(Timestamp endQueryDate)
	{
		EndQueryDate = endQueryDate;
	}

	/**
	 * @return
	 */
	public Timestamp getStartQueryDate()
	{
		return StartQueryDate;
	}

	/**
	 * @param startQueryDate
	 */
	public void setStartQueryDate(Timestamp startQueryDate)
	{
		StartQueryDate = startQueryDate;
	}

	/**
	 * @return
	 */
	public long getRemarkDetailID()
	{
		return RemarkDetailID;
	}

	/**
	 * @param remarkDetailID
	 */
	public void setRemarkDetailID(long remarkDetailID)
	{
		RemarkDetailID = remarkDetailID;
	}

	/**
	 * @return
	 */
	public long getTransDirecttion()
	{
		return TransDirecttion;
	}

	/**
	 * @param transDirecttion
	 */
	public void setTransDirecttion(long transDirecttion)
	{
		TransDirecttion = transDirecttion;
	}

	/**
	 * @return
	 */
	public long getDiscountCredenceIDEnd()
	{
		return DiscountCredenceIDEnd;
	}

	/**
	 * @return
	 */
	public long getDiscountCredenceIDStart()
	{
		return DiscountCredenceIDStart;
	}

	/**
	 * @return
	 */
	public Timestamp getDtFinish()
	{
		return DtFinish;
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
	public void setDiscountCredenceIDEnd(long l)
	{
		DiscountCredenceIDEnd = l;
	}

	/**
	 * @param l
	 */
	public void setDiscountCredenceIDStart(long l)
	{
		DiscountCredenceIDStart = l;
	}

	/**
	 * @param timestamp
	 */
	public void setDtFinish(Timestamp timestamp)
	{
		DtFinish = timestamp;
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
	public double getContractAmountEnd()
	{
		return ContractAmountEnd;
	}

	/**
	 * @return
	 */
	public double getContractAmountStart()
	{
		return ContractAmountStart;
	}

	/**
	 * @return
	 */
	public long getParentCorpID1()
	{
		return ParentCorpID1;
	}

	/**
	 * @return
	 */
	public long getParentCorpID2()
	{
		return ParentCorpID2;
	}

	/**
	 * @param d
	 */
	public void setContractAmountEnd(double d)
	{
		ContractAmountEnd = d;
	}

	/**
	 * @param d
	 */
	public void setContractAmountStart(double d)
	{
		ContractAmountStart = d;
	}

	/**
	 * @param l
	 */
	public void setParentCorpID1(long l)
	{
		ParentCorpID1 = l;
	}

	/**
	 * @param l
	 */
	public void setParentCorpID2(long l)
	{
		ParentCorpID2 = l;
	}
    public void copy(QueryAccountWhereInfo qawi)
    {
    	this.ClientManager = qawi.getClientManager();
        this.AccountStatusID = qawi.getAccountStatusID();
        this.AccountTypeID = qawi.getAccountTypeID();
        this.ClientTypeID = qawi.getClientTypeID();
        this.ConsignClientID = qawi.getConsignClientID();
        this.ContractStatusID = qawi.getContractStatusID();
        this.ContractYear = qawi.getContractYear();
        this.CurrencyID = qawi.getCurrencyID();
        this.Desc = qawi.getDesc();
        this.EndAccountNo = qawi.getEndAccountNo();
        this.EndClientCode = qawi.getEndClientCode();
        this.EndContractAmount = qawi.getEndContractAmount();
        this.EndContractCode = qawi.getEndContractCode();
        this.EndContractEndDate = qawi.getEndContractEndDate();
        this.EndContractInterestRate = qawi.getEndContractInterestRate();
        this.EndContractPeriod = qawi.getEndContractPeriod();
        this.EndContractStartDate = qawi.getEndContractStartDate();
        this.EndLoanPayStartDate = qawi.getEndPayNoteStartDate();
        this.EndFixFormNo = qawi.getEndFixFormNo();
        this.EndFixPeriod = qawi.getEndFixPeriod();
        this.EndLoanpayAmount = qawi.getEndLoanpayAmount();
        this.EndLoanPayCode = qawi.getEndLoanPayCode();
        this.EndLoanPayEnddate = qawi.getEndLoanPayEnddate();
        this.EndLoanPayRate = qawi.getEndLoanPayRate();
        this.EnterpriseTypeID = qawi.getEnterpriseTypeID();
        this.Industrytype1 = qawi.getIndustrytype1();
        this.Industrytype2 = qawi.getIndustrytype2();
        //2008-11-4���� kaishao
        this.Industrytype3=qawi.getIndustrytype3();
        //���ӽ���
        this.IndustryTypeID = qawi.getIndustryTypeID();
        this.IsFilterNull = qawi.getIsFilterNull();
        this.IsNegotiate = qawi.getIsNegotiate();
        this.IsValidAccount = qawi.getIsValidAccount();
        this.LoanPayAccountID = qawi.getLoanPayAccountID();
        this.LoanPayBankID = qawi.getLoanPayBankID();
        this.LoanPayStatusID = qawi.getLoanPayStatusID();
        this.LoanType = qawi.getLoanType();
        this.OfficeID = qawi.getOfficeID();
        this.OrderField = qawi.getOrderField();
        this.ParentCorpID = qawi.getParentCorpID();
        this.QueryDate = qawi.getQueryDate();
        this.RegiontypeID = qawi.getRegiontypeID();
        this.Risklevel = qawi.getRisklevel();
        this.StartAccountNo = qawi.getStartAccountNo();
        this.StartClientCode = qawi.getStartClientCode();
        this.StartContractAmount = qawi.getStartContractAmount();
        this.StartContractCode = qawi.getStartContractCode();
        this.StartContractEndDate = qawi.getStartContractEndDate();
        this.StartContractInterestRate = qawi.getStartContractInterestRate();
        this.StartContractPeriod = qawi.getStartContractPeriod();
        this.StartContractStartDate = qawi.getStartContractStartDate();
        this.StartLoanPayStartDate = qawi.getStartLoanPayStartDate();
        this.StartFixFormNo = qawi.getStartFixFormNo();
        this.StartFixPeriod = qawi.getStartFixPeriod();
        this.StartLoanpayAmount = qawi.getStartLoanpayAmount();
        this.StartLoanPayCode = qawi.getStartLoanPayCode();
        this.StartLoanPayEnddate = qawi.getStartLoanPayEnddate();
        this.StartLoanPayRate = qawi.getStartLoanPayRate();
        this.StartLoanPayStartDate = qawi.getStartLoanPayStartDate();
        this.AccountID = qawi.getAccountID();
        this.ClientID = qawi.getClientID();
        this.AccountTypeSet = qawi.getAccountTypeSet();
        this.EndQueryDate = qawi.getEndQueryDate();
        this.StartQueryDate = qawi.getStartQueryDate();
        this.RemarkDetailID = qawi.getRemarkDetailID();
        this.TransDirecttion = qawi.getTransDirecttion();
        this.DiscountCredenceIDEnd = qawi.getDiscountCredenceIDEnd();
        this.DiscountCredenceIDStart = qawi.getDiscountCredenceIDStart();
        this.DtFinish = qawi.getDtFinish();
        this.IndustryType1 = qawi.getIndustryType1();
        this.IndustryType2 = qawi.getIndustryType2();
        this.ContractAmountEnd = qawi.getContractAmountEnd();
        this.ContractAmountStart = qawi.getContractAmountStart();
        this.ParentCorpID1 = qawi.getParentCorpID1();
        this.ParentCorpID2 = qawi.getParentCorpID2();
        this.IntervalDays = qawi.getIntervalDays();
        
        
        this.EnterpriseTypeID1 = qawi.getEnterpriseTypeID1();
        this.EnterpriseTypeID2 = qawi.getEnterpriseTypeID2();
        this.EnterpriseTypeID3 = qawi.getEnterpriseTypeID3();
        this.EnterpriseTypeID4 = qawi.getEnterpriseTypeID4();
        this.EnterpriseTypeID5 = qawi.getEnterpriseTypeID5();
        this.EnterpriseTypeID6 = qawi.getEnterpriseTypeID6();
        
        this.AccountTypeIDArray = qawi.getAccountTypeIDArray();
        //add by 2012-05-15 ���ָ�����
        this.appointAccountNo = qawi.getAppointAccountNo();
    }         
	/**
	 * @return
	 */
	public long getIntervalDays()
	{
		return IntervalDays;
	}

	/**
	 * @param intervalDays
	 */
	public void setIntervalDays(long intervalDays)
	{
		IntervalDays = intervalDays;
	}

    /**
     * @return Returns the finishDateFrom.
     */
    public Timestamp getFinishDateFrom()
    {
        return FinishDateFrom;
    }
    /**
     * @param finishDateFrom The finishDateFrom to set.
     */
    public void setFinishDateFrom(Timestamp finishDateFrom)
    {
        FinishDateFrom = finishDateFrom;
    }
    /**
     * @return Returns the finishDateTo.
     */
    public Timestamp getFinishDateTo()
    {
        return FinishDateTo;
    }
    /**
     * @param finishDateTo The finishDateTo to set.
     */
    public void setFinishDateTo(Timestamp finishDateTo)
    {
        FinishDateTo = finishDateTo;
    }
    /**
     * @return Returns the openDateFrom.
     */
    public Timestamp getOpenDateFrom()
    {
        return OpenDateFrom;
    }
    /**
     * @param openDateFrom The openDateFrom to set.
     */
    public void setOpenDateFrom(Timestamp openDateFrom)
    {
        OpenDateFrom = openDateFrom;
    }
    /**
     * @return Returns the openDateTo.
     */
    public Timestamp getOpenDateTo()
    {
        return OpenDateTo;
    }
    /**
     * @param openDateTo The openDateTo to set.
     */
    public void setOpenDateTo(Timestamp openDateTo)
    {
        OpenDateTo = openDateTo;
    }
	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1()
	{
		return ExtendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1)
	{
		ExtendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2()
	{
		return ExtendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2)
	{
		ExtendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3()
	{
		return ExtendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3)
	{
		ExtendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4()
	{
		return ExtendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4)
	{
		ExtendAttribute4 = extendAttribute4;
	}
	public double getNegotiateRate() {
		return NegotiateRate;
	}
	public void setNegotiateRate(double negotiateRate) {
		NegotiateRate = negotiateRate;
	}
	public long getIndustrytype3() {
		return Industrytype3;
	}
	public void setIndustrytype3(long industrytype3) {
		Industrytype3 = industrytype3;
	}
	public String[] getAccountTypeIDArray() {
		return AccountTypeIDArray;
	}
	public void setAccountTypeIDArray(String[] accountTypeIDArray) {
		AccountTypeIDArray = accountTypeIDArray;
	}
	/**
	 * @return the lIsInterestBalance
	 */
	public long getLIsInterestBalance() {
		return lIsInterestBalance;
	}
	/**
	 * @param isInterestBalance the lIsInterestBalance to set
	 */
	public void setLIsInterestBalance(long isInterestBalance) {
		lIsInterestBalance = isInterestBalance;
	}
	public boolean isSearchSum() {
		return isSearchSum;
	}
	public void setSearchSum(boolean isSearchSum) {
		this.isSearchSum = isSearchSum;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	public long getLUnit() {
		return lUnit;
	}
	public void setLUnit(long unit) {
		lUnit = unit;
	}
	public String get_AccountTypeIDArray() {
		return _AccountTypeIDArray;
	}
	public void set_AccountTypeIDArray(String accountTypeIDArray) {
		_AccountTypeIDArray = accountTypeIDArray;
	}
	
}

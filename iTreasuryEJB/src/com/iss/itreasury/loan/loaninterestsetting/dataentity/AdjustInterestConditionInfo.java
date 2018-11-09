/*
 * AdjustInterestConditionInfo.java
 *
 * Created on 2002��3��11��, ����10:13
 */

package com.iss.itreasury.loan.loaninterestsetting.dataentity;
import java.sql.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;


/**
 *
 * @author  yzhang
 * @version
 */
public class AdjustInterestConditionInfo implements java.io.Serializable {

    public double getDAdjustRate() {
		return dAdjustRate;
	}

	public void setDAdjustRate(double adjustRate) {
		dAdjustRate = adjustRate;
	}

	public double getDInterestRate() {
		return dInterestRate;
	}

	public void setDInterestRate(double interestRate) {
		dInterestRate = interestRate;
	}

	public double getDStaidAdjustRate() {
		return dStaidAdjustRate;
	}

	public void setDStaidAdjustRate(double staidAdjustRate) {
		dStaidAdjustRate = staidAdjustRate;
	}

	public double getFInterestRate() {
		return fInterestRate;
	}

	public void setFInterestRate(double interestRate) {
		fInterestRate = interestRate;
	}

	public double getFRate() {
		return fRate;
	}

	public void setFRate(double rate) {
		fRate = rate;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public long getLBankLoanInterestRateID() {
		return lBankLoanInterestRateID;
	}

	public void setLBankLoanInterestRateID(long bankLoanInterestRateID) {
		lBankLoanInterestRateID = bankLoanInterestRateID;
	}

	public long getLConsignClientID() {
		return lConsignClientID;
	}

	public void setLConsignClientID(long consignClientID) {
		lConsignClientID = consignClientID;
	}

	public long getLContractID() {
		return lContractID;
	}

	public void setLContractID(long contractID) {
		lContractID = contractID;
	}

	public long getLCount() {
		return lCount;
	}

	public void setLCount(long count) {
		lCount = count;
	}

	public long getLID() {
		return lID;
	}

	public void setLID(long lid) {
		lID = lid;
	}

	public long getLInputUserID() {
		return lInputUserID;
	}

	public void setLInputUserID(long inputUserID) {
		lInputUserID = inputUserID;
	}

	public long getLInterestID() {
		return lInterestID;
	}

	public void setLInterestID(long interestID) {
		lInterestID = interestID;
	}

	public long getLIntervalNum() {
		return lIntervalNum;
	}

	public void setLIntervalNum(long intervalNum) {
		lIntervalNum = intervalNum;
	}

	public long getLLoanPayNoticeID() {
		return lLoanPayNoticeID;
	}

	public void setLLoanPayNoticeID(long loanPayNoticeID) {
		lLoanPayNoticeID = loanPayNoticeID;
	}

	public long getLLoanType() {
		return lLoanType;
	}

	public void setLLoanType(long loanType) {
		lLoanType = loanType;
	}

	public long getLNextCheckLevel() {
		return lNextCheckLevel;
	}

	public void setLNextCheckLevel(long nextCheckLevel) {
		lNextCheckLevel = nextCheckLevel;
	}

	public long getLPeriod() {
		return lPeriod;
	}

	public void setLPeriod(long period) {
		lPeriod = period;
	}

	public long getLStatusID() {
		return lStatusID;
	}

	public void setLStatusID(long statusID) {
		lStatusID = statusID;
	}

	public long getM_lCheckUserID() {
		return m_lCheckUserID;
	}

	public void setM_lCheckUserID(long checkUserID) {
		m_lCheckUserID = checkUserID;
	}

	public long getM_lOfficeID() {
		return m_lOfficeID;
	}

	public void setM_lOfficeID(long officeID) {
		m_lOfficeID = officeID;
	}

	public String getM_strCheckUserName() {
		return m_strCheckUserName;
	}

	public void setM_strCheckUserName(String checkUserName) {
		m_strCheckUserName = checkUserName;
	}

	public Timestamp getM_tsCheck() {
		return m_tsCheck;
	}

	public void setM_tsCheck(Timestamp check) {
		m_tsCheck = check;
	}

	public double getMBalance() {
		return mBalance;
	}

	public void setMBalance(double balance) {
		mBalance = balance;
	}

	public double getMExamineAmount() {
		return mExamineAmount;
	}

	public void setMExamineAmount(double examineAmount) {
		mExamineAmount = examineAmount;
	}

	public String getStrAdjustReason() {
		return strAdjustReason;
	}

	public void setStrAdjustReason(String strAdjustReason) {
		this.strAdjustReason = strAdjustReason;
	}

	public String getStrBankLoanInterestRateName() {
		return strBankLoanInterestRateName;
	}

	public void setStrBankLoanInterestRateName(String strBankLoanInterestRateName) {
		this.strBankLoanInterestRateName = strBankLoanInterestRateName;
	}

	public String getStrBankLoanInterestRateNo() {
		return strBankLoanInterestRateNo;
	}

	public void setStrBankLoanInterestRateNo(String strBankLoanInterestRateNo) {
		this.strBankLoanInterestRateNo = strBankLoanInterestRateNo;
	}

	public String getStrClientName() {
		return strClientName;
	}

	public void setStrClientName(String strClientName) {
		this.strClientName = strClientName;
	}

	public String getStrConsignClientName() {
		return strConsignClientName;
	}

	public void setStrConsignClientName(String strConsignClientName) {
		this.strConsignClientName = strConsignClientName;
	}

	public String getStrContractNo() {
		return strContractNo;
	}

	public void setStrContractNo(String strContractNo) {
		this.strContractNo = strContractNo;
	}

	public String getStrInputUserName() {
		return strInputUserName;
	}

	public void setStrInputUserName(String strInputUserName) {
		this.strInputUserName = strInputUserName;
	}

	public String getStrInterestName() {
		return strInterestName;
	}

	public void setStrInterestName(String strInterestName) {
		this.strInterestName = strInterestName;
	}

	public String getStrInterestNo() {
		return strInterestNo;
	}

	public void setStrInterestNo(String strInterestNo) {
		this.strInterestNo = strInterestNo;
	}

	public String getStrReason() {
		return strReason;
	}

	public void setStrReason(String strReason) {
		this.strReason = strReason;
	}

	public String getStrRefuseReason() {
		return strRefuseReason;
	}

	public void setStrRefuseReason(String strRefuseReason) {
		this.strRefuseReason = strRefuseReason;
	}

	public Timestamp getTsEndDate() {
		return tsEndDate;
	}

	public void setTsEndDate(Timestamp tsEndDate) {
		this.tsEndDate = tsEndDate;
	}

	public Timestamp getTsInputDate() {
		return tsInputDate;
	}

	public void setTsInputDate(Timestamp tsInputDate) {
		this.tsInputDate = tsInputDate;
	}

	public Timestamp getTsRateValidate() {
		return tsRateValidate;
	}

	public void setTsRateValidate(Timestamp tsRateValidate) {
		this.tsRateValidate = tsRateValidate;
	}

	public Timestamp getTsStartDate() {
		return tsStartDate;
	}

	public void setTsStartDate(Timestamp tsStartDate) {
		this.tsStartDate = tsStartDate;
	}

	public Timestamp getTsUseDate() {
		return tsUseDate;
	}

	public void setTsUseDate(Timestamp tsUseDate) {
		this.tsUseDate = tsUseDate;
	}

	public Timestamp getTsValidate() {
		return tsValidate;
	}

	public void setTsValidate(Timestamp tsValidate) {
		this.tsValidate = tsValidate;
	}

	public AdjustInterestConditionInfo() {
	    super();
    }

    public long lID;                    //����������ʾ
    public long lInterestID;            //�������ʱ�ʾ
   
    public String strInterestNo;        //�������ʱ��
    public String strInterestName;      //������������
    public double dInterestRate;        //��������
    public Timestamp tsUseDate;         //��Ч����
    public long lCurrency;              //����
    public long lContractID;			//��ͬ��ʾ
    public String strContractNo;         //��ͬ���
    public long lLoanType;              //��������
    public long lConsignClientID;     //ί�е�λ��ʾ
   
    public String strConsignClientName; //ί�е�λ����
    public long lPeriod;                  //��������
    public String strAdjustReason;        //���ʵ���ԭ��
    public String strRefuseReason;        //ȡ������ԭ��
    public long lInputUserID;			  //¼����ID
    public String strInputUserName;       //¼��������
    public Timestamp tsInputDate;         //¼������
    public long m_lCheckUserID;           //���ʵ���������ID
    public String m_strCheckUserName;      //���ʵ�������������
    public Timestamp m_tsCheck;           //���ʵ�������ʱ��
    public long m_lOfficeID;              //���´���ʶ

    //Fan Yi Add in 8.27
    public String strClientName;			//��λ
    public double mExamineAmount;			//������
    public double mBalance;					//�������
    public double fInterestRate;				//��������(��ͬ�ģ���ǰ��)
    public Timestamp tsStartDate;			//���ʼ����
    public Timestamp tsEndDate;				//�����������
    public long lIntervalNum;				//��������

    public long lBankLoanInterestRateID;		//�������ʱ�ʾ
    public String strBankLoanInterestRateNo;	//�������ʱ��
    public String strBankLoanInterestRateName;	//������������
    public double fRate;							//����ֵ(���ڵ�)
    public double dAdjustRate;					//�������ʣ����ڵģ�
    public double dStaidAdjustRate;				//�̶������㣨���ڵģ�
    public Timestamp tsValidate;				//��ͬ���ʵ�����Ч��
	public Timestamp tsRateValidate;            //������Ч��
    public String strReason;					//���ʵ���ԭ��
	public long lStatusID;						//���ʵ���״̬

	//Fan Yi Add in 1.3
	public long lLoanPayNoticeID;			//�ſ�֪ͨ����ʾ
	
	public long lNextCheckLevel = -1;		//��һ����˼���

	public long lCount;
	
    public InutParameterInfo inutParameterInfo = null;
    public String ContractCodeFrom;     //��ͬ�����ʼ
    public String ContractCodeTo;       //��ͬ��Ž���
    public long consignClientIDFrom;	//ί�е�λ��ʼ
    public long consignClientIDTo;		//ί�е�λ����
    public long borrowClientIDFrom;		//��λ��ʼ
    public long borrowClientIDTo;		//��λ����
    public String loanTypeIDsList;		//����״̬�б�
    public String loanStatusTypeIDsList;//����״̬�б�
    public Timestamp tsValidateFrom;	//��ͬ���ʵ�����Ч����ʼ
    public Timestamp tsValidateTo;  	//��ͬ���ʵ�����Ч�ս���
    public Timestamp tsInputDateFrom;   //¼��������ʼ
    public Timestamp tsInputDateTo;     //¼�����ڽ���
    public long lLoanPayNoticeIDFrom;	//�ſ�֪ͨ��id��ʼ
    public long lLoanPayNoticeIDTo;	    //�ſ�֪ͨ��id����
    
    public String lLoanPayNoticeCodeFrom;//�ſ�֪ͨ��code��ʼ
    public String lLoanPayNoticeCodeTo; //�ſ�֪ͨ��code����
    public double tsAdjustRateFrom;		//������������ʼ
    public double tsAdjustRateTo;		//������������ʼ
    
    /*public Timestamp getTsRateValidate()
    {
	return tsRateValidate;
    }
    public void setTsRateValidate(Timestamp tsRateValidate)
    {
	this.tsRateValidate = tsRateValidate;
    }*/

	
	public Timestamp getTsInputDateFrom() {
		return tsInputDateFrom;
	}

	public void setTsInputDateFrom(Timestamp tsInputDateFrom) {
		this.tsInputDateFrom = tsInputDateFrom;
	}

	public Timestamp getTsInputDateTo() {
		return tsInputDateTo;
	}

	public void setTsInputDateTo(Timestamp tsInputDateTo) {
		this.tsInputDateTo = tsInputDateTo;
	}

	public long getLLoanPayNoticeIDFrom() {
		return lLoanPayNoticeIDFrom;
	}

	public void setLLoanPayNoticeIDFrom(long loanPayNoticeIDFrom) {
		lLoanPayNoticeIDFrom = loanPayNoticeIDFrom;
	}

	public long getLLoanPayNoticeIDTo() {
		return lLoanPayNoticeIDTo;
	}

	public void setLLoanPayNoticeIDTo(long loanPayNoticeIDTo) {
		lLoanPayNoticeIDTo = loanPayNoticeIDTo;
	}

	public long getLCurrency() {
		return lCurrency;
	}

	public void setLCurrency(long currency) {
		lCurrency = currency;
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

	public long getBorrowClientIDFrom() {
		return borrowClientIDFrom;
	}

	public void setBorrowClientIDFrom(long borrowClientIDFrom) {
		this.borrowClientIDFrom = borrowClientIDFrom;
	}

	public long getBorrowClientIDTo() {
		return borrowClientIDTo;
	}

	public void setBorrowClientIDTo(long borrowClientIDTo) {
		this.borrowClientIDTo = borrowClientIDTo;
	}

	public long getConsignClientIDFrom() {
		return consignClientIDFrom;
	}

	public void setConsignClientIDFrom(long consignClientIDFrom) {
		this.consignClientIDFrom = consignClientIDFrom;
	}

	public long getConsignClientIDTo() {
		return consignClientIDTo;
	}

	public void setConsignClientIDTo(long consignClientIDTo) {
		this.consignClientIDTo = consignClientIDTo;
	}

	public String getLoanTypeIDsList() {
		return loanTypeIDsList;
	}

	public void setLoanTypeIDsList(String loanTypeIDsList) {
		this.loanTypeIDsList = loanTypeIDsList;
	}

	public String getLoanStatusTypeIDsList() {
		return loanStatusTypeIDsList;
	}

	public void setLoanStatusTypeIDsList(String loanStatusTypeIDsList) {
		this.loanStatusTypeIDsList = loanStatusTypeIDsList;
	}

	public Timestamp getTsValidateFrom() {
		return tsValidateFrom;
	}

	public void setTsValidateFrom(Timestamp tsValidateFrom) {
		this.tsValidateFrom = tsValidateFrom;
	}

	public Timestamp getTsValidateTo() {
		return tsValidateTo;
	}

	public void setTsValidateTo(Timestamp tsValidateTo) {
		this.tsValidateTo = tsValidateTo;
	}

	public double getTsAdjustRateFrom() {
		return tsAdjustRateFrom;
	}

	public void setTsAdjustRateFrom(double tsAdjustRateFrom) {
		this.tsAdjustRateFrom = tsAdjustRateFrom;
	}

	public double getTsAdjustRateTo() {
		return tsAdjustRateTo;
	}

	public void setTsAdjustRateTo(double tsAdjustRateTo) {
		this.tsAdjustRateTo = tsAdjustRateTo;
	}

	public String getLLoanPayNoticeCodeFrom() {
		return lLoanPayNoticeCodeFrom;
	}

	public void setLLoanPayNoticeCodeFrom(String loanPayNoticeCodeFrom) {
		lLoanPayNoticeCodeFrom = loanPayNoticeCodeFrom;
	}

	public String getLLoanPayNoticeCodeTo() {
		return lLoanPayNoticeCodeTo;
	}

	public void setLLoanPayNoticeCodeTo(String loanPayNoticeCodeTo) {
		lLoanPayNoticeCodeTo = loanPayNoticeCodeTo;
	}
}

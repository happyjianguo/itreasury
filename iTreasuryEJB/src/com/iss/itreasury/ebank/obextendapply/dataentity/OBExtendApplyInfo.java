/*
 * Created on 2004-1-13
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obextendapply.dataentity;

import java.beans.*;
import java.sql.*;
import java.util.*;
import com.iss.itreasury.loan.contract.dataentity.*;
import java.io.Serializable;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBExtendApplyInfo  implements Serializable
{ 
	/**
	 * ExtendApplyInfo ������ע�⡣
	 */
	public OBExtendApplyInfo()
	{
		super();
	}

	public long ID; //չ�������ʾ

	public long CurrencyID; //����

	public long ContractID; //�����ͬ��ʾ
	public String ContractCode; //�����ͬ��ʾ
	public double LoanAmount; // ��ͬ���
	public Timestamp LoanDate; // ��������
	public long LoanIntervalNum; // ��������
	public long IsExtend; // �Ƿ�չ��

	public String ClientName;
	public long SerialNo; //չ�����
	public double LoanBalance; //�������
	public double ExtendAmount; //չ�ڽ��
	public Timestamp ExtendBeginDate; //չ����ʼ����
	public Timestamp ExtendEndDate; //չ�ڽ�������
	public long ExtendIntervalNum; //չ������
	public String ExtendReason; //չ��ԭ��
	public String ReturnPostPend; //�黹���ڻ��Ϣ�ʽ�
	public String OtherContent; //��������
	public double ExamineAmount; //��˽��
	public long IntervalNum; //����
	public double InterestRate; //����
	public long StatusID; //״̬
	public long CheckNum;

	public long InputUserID; //¼���˱�ʾ
	public String InputUserName; //¼��������
	public Timestamp InputDate;
	public long CheckUserID; //�����˱�ʾ
	public String CheckUserName; //����������

	public String ExCode; // չ�ں�ͬ���
	public long ExContractID; // չ�ں�ͬ��ʶ
	public long NextUserID; //��һ�����˱�ʶ
	public Timestamp CheckDate; // ��������
	public long InterestTypeID; // ��������
	public double LiborAdjust; // ���ʵ���
	public long LiborRateID;
	public long BankRateTypeID; // ������������
	public double DefaultBankRate; //ȱʡֵ

	public long LoanID; // LOANINFO.ID
	public Timestamp StartDate_loaninfo; // ������ʼ����
	public Timestamp EndDate_loaninfo; // ������ֹ����
	public double ExamineAmount_loaninfo; // ������
	public double FinterestRate_ioaninfo; // ִ������
	public long ExamineIntervalNum_loaninfo;
	public double LoanApplyAmount_loaninfo;
	public long ConsignContractID_loaninfo;
	public long ConsignClientID;
	//Fan Yi
	public float ChargeRate; //��������
	public double ContractBalance;

	public long LoanTypeID; // ������������
	//public String      ClientName;          // ��λ
	public String EconomyType;
	public String ConsignClientName; // ί�е�λ
	public String ConsignCode; // ί�к�ͬ���
	public String LastUserName; // ��������
	public String ApplyCode; // ����������

	public long Extendtype; // չ������
	public long PlanVersionID; // �ƻ��汾��ʶ
	public long PageCount; //��¼��

	public Collection ExtendList; // չ����ϸ��
	public Collection ExtendContractList; // չ�ں�ͬ��

	public String Opinion1 = ""; //�������
	public String Opinion2 = "";
	public String Opinion3 = "";

	ContractInfo C_Info = new ContractInfo();

	//Ϊ��ѯ����
	private long ExtendTypeID = -1;
	private double ExtendRate = 0.0;
	private long UserID = -1;
	private double AdjustValue = 0.0;
	private String InstructionNo = "";
	private String BasicInterest = "";

	public void setApplyCode(String ApplyCode)
	{
		this.ApplyCode = ApplyCode;
	}

	public void setBankRateTypeID(long BankRateTypeID)
	{
		this.BankRateTypeID = BankRateTypeID;
	}

	public void setChargeRate(float ChargeRate)
	{
		this.ChargeRate = ChargeRate;
	}

	public void setCheckDate(Timestamp CheckDate)
	{
		this.CheckDate = CheckDate;
	}

	public void setCheckNum(long CheckNum)
	{
		this.CheckNum = CheckNum;
	}

	public void setCheckUserID(long CheckUserID)
	{
		this.CheckUserID = CheckUserID;
	}

	public void setCheckUserName(String CheckUserName)
	{
		this.CheckUserName = CheckUserName;
	}

	public void setClientName(String ClientName)
	{
		this.ClientName = ClientName;
	}

	public void setConsignClientID(long ConsignClientID)
	{
		this.ConsignClientID = ConsignClientID;
	}

	public void setConsignClientName(String ConsignClientName)
	{
		this.ConsignClientName = ConsignClientName;
	}

	public void setConsignCode(String ConsignCode)
	{
		this.ConsignCode = ConsignCode;
	}

	public void setConsignContractID_loaninfo(long ConsignContractID_loaninfo)
	{
		this.ConsignContractID_loaninfo = ConsignContractID_loaninfo;
	}

	public void setContractBalance(double ContractBalance)
	{
		this.ContractBalance = ContractBalance;
	}

	public void setContractCode(String ContractCode)
	{
		this.ContractCode = ContractCode;
	}

	public void setContractID(long ContractID)
	{
		this.ContractID = ContractID;
	}

	public void setCurrencyID(long CurrencyID)
	{
		this.CurrencyID = CurrencyID;
	}

	public void setDefaultBankRate(double DefaultBankRate)
	{
		this.DefaultBankRate = DefaultBankRate;
	}

	public void setEconomyType(String EconomyType)
	{
		this.EconomyType = EconomyType;
	}

	public void setEndDate_loaninfo(Timestamp EndDate_loaninfo)
	{
		this.EndDate_loaninfo = EndDate_loaninfo;
	}

	public void setExamineAmount(double ExamineAmount)
	{
		this.ExamineAmount = ExamineAmount;
	}

	public void setExamineAmount_loaninfo(double ExamineAmount_loaninfo)
	{
		this.ExamineAmount_loaninfo = ExamineAmount_loaninfo;
	}

	public void setExamineIntervalNum_loaninfo(long ExamineIntervalNum_loaninfo)
	{
		this.ExamineIntervalNum_loaninfo = ExamineIntervalNum_loaninfo;
	}

	public void setExCode(String ExCode)
	{
		this.ExCode = ExCode;
	}

	public void setExContractID(long ExContractID)
	{
		this.ExContractID = ExContractID;
	}

	public void setExtendAmount(double ExtendAmount)
	{
		this.ExtendAmount = ExtendAmount;
	}

	public void setExtendBeginDate(Timestamp ExtendBeginDate)
	{
		this.ExtendBeginDate = ExtendBeginDate;
	}

	public void setExtendContractList(Collection ExtendContractList)
	{
		this.ExtendContractList = ExtendContractList;
	}

	public void setExtendEndDate(Timestamp ExtendEndDate)
	{
		this.ExtendEndDate = ExtendEndDate;
	}

	public void setExtendIntervalNum(long ExtendIntervalNum)
	{
		this.ExtendIntervalNum = ExtendIntervalNum;
	}

	public void setExtendList(Collection ExtendList)
	{
		this.ExtendList = ExtendList;
	}

	public void setExtendReason(String ExtendReason)
	{
		this.ExtendReason = ExtendReason;
	}

	public void setExtendtype(long Extendtype)
	{
		this.Extendtype = Extendtype;
	}

	public void setFinterestRate_ioaninfo(double FinterestRate_ioaninfo)
	{
		this.FinterestRate_ioaninfo = FinterestRate_ioaninfo;
	}

	public void setID(long ID)
	{
		this.ID = ID;
	}

	public void setInputDate(Timestamp InputDate)
	{
		this.InputDate = InputDate;
	}

	public void setInputUserID(long InputUserID)
	{
		this.InputUserID = InputUserID;
	}

	public void setInputUserName(String InputUserName)
	{
		this.InputUserName = InputUserName;
	}

	public void setInterestRate(double InterestRate)
	{
		this.InterestRate = InterestRate;
	}

	public void setInterestTypeID(long InterestTypeID)
	{
		this.InterestTypeID = InterestTypeID;
	}

	public void setIntervalNum(long IntervalNum)
	{
		this.IntervalNum = IntervalNum;
	}

	public void setIsExtend(long IsExtend)
	{
		this.IsExtend = IsExtend;
	}

	public void setLastUserName(String LastUserName)
	{
		this.LastUserName = LastUserName;
	}

	public void setLiborAdjust(double LiborAdjust)
	{
		this.LiborAdjust = LiborAdjust;
	}

	public void setLiborRateID(long LiborRateID)
	{
		this.LiborRateID = LiborRateID;
	}

	public void setLoanAmount(double LoanAmount)
	{
		this.LoanAmount = LoanAmount;
	}

	public void setLoanApplyAmount_loaninfo(double LoanApplyAmount_loaninfo)
	{
		this.LoanApplyAmount_loaninfo = LoanApplyAmount_loaninfo;
	}

	public void setLoanBalance(double LoanBalance)
	{
		this.LoanBalance = LoanBalance;
	}

	public void setLoanDate(Timestamp LoanDate)
	{
		this.LoanDate = LoanDate;
	}

	public void setLoanID(long LoanID)
	{
		this.LoanID = LoanID;
	}

	public void setLoanIntervalNum(long LoanIntervalNum)
	{
		this.LoanIntervalNum = LoanIntervalNum;
	}

	public void setLoanTypeID(long LoanTypeID)
	{
		this.LoanTypeID = LoanTypeID;
	}

	public void setNextUserID(long NextUserID)
	{
		this.NextUserID = NextUserID;
	}

	public void setOpinion1(String Opinion1)
	{
		this.Opinion1 = Opinion1;
	}

	public void setOpinion2(String Opinion2)
	{
		this.Opinion2 = Opinion2;
	}

	public void setOpinion3(String Opinion3)
	{
		this.Opinion3 = Opinion3;
	}

	public void setOtherContent(String OtherContent)
	{
		this.OtherContent = OtherContent;
	}

	public void setPageCount(long PageCount)
	{
		this.PageCount = PageCount;
	}

	public void setPlanVersionID(long PlanVersionID)
	{
		this.PlanVersionID = PlanVersionID;
	}

	public void setReturnPostPend(String ReturnPostPend)
	{
		this.ReturnPostPend = ReturnPostPend;
	}

	public void setSerialNo(long SerialNo)
	{
		this.SerialNo = SerialNo;
	}

	public void setStartDate_loaninfo(Timestamp StartDate_loaninfo)
	{
		this.StartDate_loaninfo = StartDate_loaninfo;
	}

	public void setStatusID(long StatusID)
	{
		this.StatusID = StatusID;
	}

	public String getApplyCode()
	{
		return ApplyCode;
	}

	public long getBankRateTypeID()
	{
		return BankRateTypeID;
	}

	public float getChargeRate()
	{
		return ChargeRate;
	}

	public Timestamp getCheckDate()
	{
		return CheckDate;
	}

	public long getCheckNum()
	{
		return CheckNum;
	}

	public long getCheckUserID()
	{
		return CheckUserID;
	}

	public String getCheckUserName()
	{
		return CheckUserName;
	}

	public String getClientName()
	{
		return ClientName;
	}

	public long getConsignClientID()
	{
		return ConsignClientID;
	}

	public String getConsignClientName()
	{
		return ConsignClientName;
	}

	public String getConsignCode()
	{
		return ConsignCode;
	}

	public long getConsignContractID_loaninfo()
	{
		return ConsignContractID_loaninfo;
	}

	public double getContractBalance()
	{
		return ContractBalance;
	}

	public String getContractCode()
	{
		return ContractCode;
	}

	public long getContractID()
	{
		return ContractID;
	}

	public long getCurrencyID()
	{
		return CurrencyID;
	}

	public double getDefaultBankRate()
	{
		return DefaultBankRate;
	}

	public String getEconomyType()
	{
		return EconomyType;
	}

	public Timestamp getEndDate_loaninfo()
	{
		return EndDate_loaninfo;
	}

	public double getExamineAmount()
	{
		return ExamineAmount;
	}

	public double getExamineAmount_loaninfo()
	{
		return ExamineAmount_loaninfo;
	}

	public long getExamineIntervalNum_loaninfo()
	{
		return ExamineIntervalNum_loaninfo;
	}

	public String getExCode()
	{
		return ExCode;
	}

	public long getExContractID()
	{
		return ExContractID;
	}

	public double getExtendAmount()
	{
		return ExtendAmount;
	}

	public Timestamp getExtendBeginDate()
	{
		return ExtendBeginDate;
	}

	public Collection getExtendContractList()
	{
		return ExtendContractList;
	}

	public Timestamp getExtendEndDate()
	{
		return ExtendEndDate;
	}

	public long getExtendIntervalNum()
	{
		return ExtendIntervalNum;
	}

	public Collection getExtendList()
	{
		return ExtendList;
	}

	public String getExtendReason()
	{
		return ExtendReason;
	}

	public long getExtendtype()
	{
		return Extendtype;
	}

	public double getFinterestRate_ioaninfo()
	{
		return FinterestRate_ioaninfo;
	}

	public long getID()
	{
		return ID;
	}

	public Timestamp getInputDate()
	{
		return InputDate;
	}

	public long getInputUserID()
	{
		return InputUserID;
	}

	public String getInputUserName()
	{
		return InputUserName;
	}

	public double getInterestRate()
	{
		return InterestRate;
	}

	public long getInterestTypeID()
	{
		return InterestTypeID;
	}

	public long getIntervalNum()
	{
		return IntervalNum;
	}

	public long getIsExtend()
	{
		return IsExtend;
	}

	public String getLastUserName()
	{
		return LastUserName;
	}

	public double getLiborAdjust()
	{
		return LiborAdjust;
	}

	public long getLiborRateID()
	{
		return LiborRateID;
	}

	public double getLoanAmount()
	{
		return LoanAmount;
	}

	public double getLoanApplyAmount_loaninfo()
	{
		return LoanApplyAmount_loaninfo;
	}

	public double getLoanBalance()
	{
		return LoanBalance;
	}

	public Timestamp getLoanDate()
	{
		return LoanDate;
	}

	public long getLoanID()
	{
		return LoanID;
	}

	public long getLoanIntervalNum()
	{
		return LoanIntervalNum;
	}

	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	public long getNextUserID()
	{
		return NextUserID;
	}

	public String getOpinion1()
	{
		return Opinion1;
	}

	public String getOpinion2()
	{
		return Opinion2;
	}

	public String getOpinion3()
	{
		return Opinion3;
	}

	public String getOtherContent()
	{
		return OtherContent;
	}

	public long getPageCount()
	{
		return PageCount;
	}

	public long getPlanVersionID()
	{
		return PlanVersionID;
	}

	public String getReturnPostPend()
	{
		return ReturnPostPend;
	}

	public long getSerialNo()
	{
		return SerialNo;
	}

	public Timestamp getStartDate_loaninfo()
	{
		return StartDate_loaninfo;
	}

	public long getStatusID()
	{
		return StatusID;
	}

	public ContractInfo getC_Info()
	{
		return C_Info;
	}

	public void setC_Info(ContractInfo C_Info)
	{
		this.C_Info = C_Info;
    }
    public double getExtendRate()
    {
	return ExtendRate;
    }
    public void setExtendRate(double ExtendRate)
    {
	this.ExtendRate = ExtendRate;
    }
    public long getUserID()
    {
	return UserID;
    }
    public void setUserID(long UserID)
    {
	this.UserID = UserID;
    }
    public long getExtendTypeID()
    {
	return ExtendTypeID;
    }
    public void setExtendTypeID(long ExtendTypeID)
    {
	this.ExtendTypeID = ExtendTypeID;
    }
    public double getAdjustValue()
    {
	return AdjustValue;
    }
    public void setAdjustValue(double AdjustValue)
    {
	this.AdjustValue = AdjustValue;
    }
    public String getInstructionNo()
    {
	return InstructionNo;
    }
    public void setInstructionNo(String InstructionNo)
    {
	this.InstructionNo = InstructionNo;
    }
    public String getBasicInterest()
    {
	return BasicInterest;
    }
    public void setBasicInterest(String BasicInterest)
    {
	this.BasicInterest = BasicInterest;
    }

}

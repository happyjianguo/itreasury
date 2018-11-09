package com.iss.itreasury.ebank.obaheadrepaynotice.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *����Ʊ����Ϣ
 * To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class AheadRepayNoticeInfo implements java.io.Serializable
{
	public AheadRepayNoticeInfo()
	{
		super();
	}
	    private long ID = -1; //��ǰ����֪ͨ��ID��ʶ
		private String Code = ""; //��ǰ����֪ͨ�����
		private double Amount = 0; //��ǰ������
		private long CurrencyID = -1; //����
		private long OfficeID = -1; //���´���ʾ
		private long StatusID = -1; //״̬
		private String Status = "";
		private long isAhead = -1;//�Ƿ���ǰ����
		

		private long InputUserID = -1; //¼���˱�ʾ
		private String InputUserName = ""; //¼��������
		private Timestamp InputDate; //¼��ʱ��

		private long NextCheckUserID = -1; //��һ������˱�ʾ
		private long LsReviewUserID = -1; //��������ID
		private String LsReviewUserName = ""; //������������
		private long ReviewStatusID = -1; //������״̬

		private long ContractID = -1; //��ͬ��ʶ
		private String ContractCode = ""; //��ͬ���
		private double ContractAmount = 0; //��ͬ���
		private double ContractBalance = 0; //��ͬ���
		private long ClientID = -1; //��λ��ʶ
		private String ClientName = ""; //��λ����
		private long LoanID = -1; //�����ʶ
		private long IntervalNum = 0; //��������

		private long PlanID = -1; //�ƻ���ʾ

		private long LetoutNoticeID = -1; //�ſ�֪ͨ����ʶ
		private String LetoutNoticeCode = ""; //�ſ�֪ͨ�����
		private double LetoutNoticeAmount = 0; //�ſ�֪ͨ�����
		private double LetoutNoticeRate = 0; //�ſ�֪ͨ������
		private long LetoutNoticeIntervalNum = 0; //��������
		private double LetoutNoticeBalance = 0; //�ſ�֪ͨ�����

		private long PageCount = 0;
    public void setAmount(double Amount)
    {
	this.Amount = Amount;
    }
    public void setClientID(long ClientID)
    {
	this.ClientID = ClientID;
    }
    public void setClientName(String ClientName)
    {
	this.ClientName = ClientName;
    }
    public void setCode(String Code)
    {
	this.Code = Code;
    }
    public void setContractAmount(double ContractAmount)
    {
	this.ContractAmount = ContractAmount;
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
    public void setIntervalNum(long IntervalNum)
    {
	this.IntervalNum = IntervalNum;
    }
    public void setLetoutNoticeAmount(double LetoutNoticeAmount)
    {
	this.LetoutNoticeAmount = LetoutNoticeAmount;
    }
    public void setLetoutNoticeBalance(double LetoutNoticeBalance)
    {
	this.LetoutNoticeBalance = LetoutNoticeBalance;
    }
    public void setLetoutNoticeCode(String LetoutNoticeCode)
    {
	this.LetoutNoticeCode = LetoutNoticeCode;
    }
    public void setLetoutNoticeID(long LetoutNoticeID)
    {
	this.LetoutNoticeID = LetoutNoticeID;
    }
    public void setLetoutNoticeIntervalNum(long LetoutNoticeIntervalNum)
    {
	this.LetoutNoticeIntervalNum = LetoutNoticeIntervalNum;
    }
    public void setLetoutNoticeRate(double LetoutNoticeRate)
    {
	this.LetoutNoticeRate = LetoutNoticeRate;
    }
    public void setLoanID(long LoanID)
    {
	this.LoanID = LoanID;
    }
    public void setLsReviewUserID(long LsReviewUserID)
    {
	this.LsReviewUserID = LsReviewUserID;
    }
    public void setLsReviewUserName(String LsReviewUserName)
    {
	this.LsReviewUserName = LsReviewUserName;
    }
    public void setNextCheckUserID(long NextCheckUserID)
    {
	this.NextCheckUserID = NextCheckUserID;
    }
    public void setOfficeID(long OfficeID)
    {
	this.OfficeID = OfficeID;
    }
    public void setPageCount(long PageCount)
    {
	this.PageCount = PageCount;
    }
    public void setPlanID(long PlanID)
    {
	this.PlanID = PlanID;
    }
    public void setReviewStatusID(long ReviewStatusID)
    {
	this.ReviewStatusID = ReviewStatusID;
    }
    public void setStatus(String Status)
    {
	this.Status = Status;
    }
    public void setStatusID(long StatusID)
    {
	this.StatusID = StatusID;
    }
    public double getAmount()
    {
	return Amount;
    }
    public long getClientID()
    {
	return ClientID;
    }
    public String getClientName()
    {
	return ClientName;
    }
    public String getCode()
    {
	return Code;
    }
    public double getContractAmount()
    {
	return ContractAmount;
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
    public long getIntervalNum()
    {
	return IntervalNum;
    }
    public double getLetoutNoticeAmount()
    {
	return LetoutNoticeAmount;
    }
    public double getLetoutNoticeBalance()
    {
	return LetoutNoticeBalance;
    }
    public String getLetoutNoticeCode()
    {
	return LetoutNoticeCode;
    }
    public long getLetoutNoticeID()
    {
	return LetoutNoticeID;
    }
    public long getLetoutNoticeIntervalNum()
    {
	return LetoutNoticeIntervalNum;
    }
    public double getLetoutNoticeRate()
    {
	return LetoutNoticeRate;
    }
    public long getLoanID()
    {
	return LoanID;
    }
    public long getLsReviewUserID()
    {
	return LsReviewUserID;
    }
    public String getLsReviewUserName()
    {
	return LsReviewUserName;
    }
    public long getNextCheckUserID()
    {
	return NextCheckUserID;
    }
    public long getOfficeID()
    {
	return OfficeID;
    }
    public long getPageCount()
    {
	return PageCount;
    }
    public long getPlanID()
    {
	return PlanID;
    }
    public long getReviewStatusID()
    {
	return ReviewStatusID;
    }
    public String getStatus()
    {
	return Status;
    }
    public long getStatusID()
    {
	return StatusID;
    } //��¼��
	public long getIsAhead() {
		return isAhead;
	}
	public void setIsAhead(long isAhead) {
		this.isAhead = isAhead;
	}



}

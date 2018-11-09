package com.iss.itreasury.ebank.obaheadrepaynotice.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *贴现票据信息
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
	    private long ID = -1; //提前还款通知单ID标识
		private String Code = ""; //提前还款通知单编号
		private double Amount = 0; //提前还款金额
		private long CurrencyID = -1; //币种
		private long OfficeID = -1; //办事处标示
		private long StatusID = -1; //状态
		private String Status = "";
		private long isAhead = -1;//是否提前还款
		

		private long InputUserID = -1; //录入人标示
		private String InputUserName = ""; //录入人名称
		private Timestamp InputDate; //录入时间

		private long NextCheckUserID = -1; //下一个审核人标示
		private long LsReviewUserID = -1; //最后审核人ID
		private String LsReviewUserName = ""; //最后审核人名称
		private long ReviewStatusID = -1; //最后审核状态

		private long ContractID = -1; //合同标识
		private String ContractCode = ""; //合同编号
		private double ContractAmount = 0; //合同金额
		private double ContractBalance = 0; //合同余额
		private long ClientID = -1; //借款单位标识
		private String ClientName = ""; //借款单位名称
		private long LoanID = -1; //贷款标识
		private long IntervalNum = 0; //贷款期限

		private long PlanID = -1; //计划标示

		private long LetoutNoticeID = -1; //放款通知单标识
		private String LetoutNoticeCode = ""; //放款通知单编号
		private double LetoutNoticeAmount = 0; //放款通知单金额
		private double LetoutNoticeRate = 0; //放款通知单利率
		private long LetoutNoticeIntervalNum = 0; //贷款期限
		private double LetoutNoticeBalance = 0; //放款通知单余额

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
    } //记录数
	public long getIsAhead() {
		return isAhead;
	}
	public void setIsAhead(long isAhead) {
		this.isAhead = isAhead;
	}



}

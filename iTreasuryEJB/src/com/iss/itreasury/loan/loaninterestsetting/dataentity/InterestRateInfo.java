package com.iss.itreasury.loan.loaninterestsetting.dataentity;
import java.sql.Timestamp;

/**
* 此处插入类型说明。
* 创建日期：(2002-1-15 17:50:47)
* @author：Administrator
*/
public class InterestRateInfo implements java.io.Serializable {
    /**
     * InterestRateInfo 构造子注解。
     */
    public InterestRateInfo() {
        super();
    }


	//public long m_lID;                     //ID
	public long ID;
	//public long m_lRateID;
	public long BankInterestTypeID;          //贷款利率标识
    //public long m_lIRS_ID;                 //利率调整序列号  added by wLi
    //public String m_strCode;               //贷款利率编号
	public String InterestRateCode;
    //public String m_strName ;
	public String InterestRateName;          //贷款利率名称
    //public Timestamp m_tsEffective;
    public Timestamp ValiDate;               //生效日期
    //public long m_lInputUserID;
	public long InputUserID;                 //利率调整提交录入人ID
	public String InputUserName;             //利率调整提交录入人姓名
    //public Timestamp m_tsInput;
    public Timestamp InputDate;              //利率调整提交录入时间
    //public String m_strInputUserName;      //利率调整录入人姓名
    //public long m_lCheckUserID;
    public long CheckUserID;                 //利率调整复核人ID
    //public Timestamp m_tsCheck;
    public Timestamp CheckDate;              //利率调整复核时间
    //public String m_strCheckUserName;
    public String CheckUserName;             //利率调整复核人姓名
    //public double m_dRate;
    public double InterestRate;              //贷款利率
	public long CurrencyID;                  //币种
	//public long m_lOfficeID;               //办事处标识
    //public int m_nStatusID;
    //public long m_lPageCount;
	public long PageCount;
    //public long m_lContractID;             //合同标示
    //public long m_lLoanType;               //贷款类型
    //public long m_lConsignCompanyID;       //委托单位
    //public long m_lPeriod;                 //贷款期限
    //public String strReason;               //调整原因
    //public String strContractCode;         //合同编号
    //public String strConsignClientName;    //委托单位
    //public long m_lBankInterestID;         //BANKLOANINTERESTRATE的ID
    //public long m_lSerialNo = -1;
	
	public long adjustratetypeid = -1;		 //基准利率调整类型
	
	public long getAdjustratetypeid() {
		return adjustratetypeid;
	}

	public void setAdjustratetypeid(long adjustratetypeid) {
		this.adjustratetypeid = adjustratetypeid;
	}

	public long getID()
	{
		return ID;
	}

	public void setID(long ID)
	{
		this.ID = ID;
	}

	public long getCurrencyID()
	{
		return CurrencyID;
	}

	public void setCurrencyID(long CurrencyID)
	{
		this.CurrencyID = CurrencyID;
	}

	public Timestamp getInputDate()
	{
		return InputDate;
	}

	public void setInputDate(Timestamp InputDate)
	{
		this.InputDate = InputDate;
	}

	public long getInputUserID()
	{
		return InputUserID;
	}

	public void setInputUserID(long InputUserID)
	{
		this.InputUserID = InputUserID;
	}

	public double getInterestRate()
	{
		return InterestRate;
	}

	public void setInterestRate(double InterestRate)
	{
		this.InterestRate = InterestRate;
	}

	public String getInterestRateCode()
	{
		return InterestRateCode;
	}

	public void setInterestRateCode(String InterestRateCode)
	{
		this.InterestRateCode = InterestRateCode;
	}

	public String getInterestRateName()
	{
		return InterestRateName;
	}

	public void setInterestRateName(String InterestRateName)
	{
		this.InterestRateName = InterestRateName;
	}

	public Timestamp getValiDate()
	{
		return ValiDate;
	}

	public void setValiDate(Timestamp ValiDate)
	{
		this.ValiDate = ValiDate;
    }

	public long getBankInterestTypeID()
	{
		return BankInterestTypeID;
	}

	public void setBankInterestTypeID(long BankInterestTypeID)
	{
		this.BankInterestTypeID = BankInterestTypeID;
    }

	public String getInputUserName()
	{
		return InputUserName;
	}

	public void setInputUserName(String InputUserName)
	{
		this.InputUserName = InputUserName;
    }

	public long getPageCount()
	{
		return PageCount;
	}

	public void setPageCount(long PageCount)
	{
		this.PageCount = PageCount;
    }

	public long getCheckUserID()
	{
		return CheckUserID;
	}

	public void setCheckUserID(long CheckUserID)
	{
		this.CheckUserID = CheckUserID;
	}

	public String getCheckUserName()
	{
		return CheckUserName;
	}

	public void setCheckUserName(String CheckUserName)
	{
		this.CheckUserName = CheckUserName;
    }

	public Timestamp getCheckDate()
	{
		return CheckDate;
	}

	public void setCheckDate(Timestamp CheckDate)
	{
		this.CheckDate = CheckDate;
    }
}

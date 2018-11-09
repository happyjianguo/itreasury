package com.iss.itreasury.loan.loaninterestsetting.dataentity;
import java.sql.Timestamp;

/**
* �˴���������˵����
* �������ڣ�(2002-1-15 17:50:47)
* @author��Administrator
*/
public class InterestRateInfo implements java.io.Serializable {
    /**
     * InterestRateInfo ������ע�⡣
     */
    public InterestRateInfo() {
        super();
    }


	//public long m_lID;                     //ID
	public long ID;
	//public long m_lRateID;
	public long BankInterestTypeID;          //�������ʱ�ʶ
    //public long m_lIRS_ID;                 //���ʵ������к�  added by wLi
    //public String m_strCode;               //�������ʱ��
	public String InterestRateCode;
    //public String m_strName ;
	public String InterestRateName;          //������������
    //public Timestamp m_tsEffective;
    public Timestamp ValiDate;               //��Ч����
    //public long m_lInputUserID;
	public long InputUserID;                 //���ʵ����ύ¼����ID
	public String InputUserName;             //���ʵ����ύ¼��������
    //public Timestamp m_tsInput;
    public Timestamp InputDate;              //���ʵ����ύ¼��ʱ��
    //public String m_strInputUserName;      //���ʵ���¼��������
    //public long m_lCheckUserID;
    public long CheckUserID;                 //���ʵ���������ID
    //public Timestamp m_tsCheck;
    public Timestamp CheckDate;              //���ʵ�������ʱ��
    //public String m_strCheckUserName;
    public String CheckUserName;             //���ʵ�������������
    //public double m_dRate;
    public double InterestRate;              //��������
	public long CurrencyID;                  //����
	//public long m_lOfficeID;               //���´���ʶ
    //public int m_nStatusID;
    //public long m_lPageCount;
	public long PageCount;
    //public long m_lContractID;             //��ͬ��ʾ
    //public long m_lLoanType;               //��������
    //public long m_lConsignCompanyID;       //ί�е�λ
    //public long m_lPeriod;                 //��������
    //public String strReason;               //����ԭ��
    //public String strContractCode;         //��ͬ���
    //public String strConsignClientName;    //ί�е�λ
    //public long m_lBankInterestID;         //BANKLOANINTERESTRATE��ID
    //public long m_lSerialNo = -1;
	
	public long adjustratetypeid = -1;		 //��׼���ʵ�������
	
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

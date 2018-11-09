package com.iss.itreasury.loan.overdueapply.dataentity;
import java.sql.*;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
/**
 * @author yzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OverDueInfo implements java.io.Serializable
{

    /**
     * OverDueInfo ������ע�⡣
     */
    public OverDueInfo()
    {
        super();
    }
    
    //ID:��ʶ
    private long ID;
    
    //sContractCode:���ڱ��
    private String OverDueApplyCode;

    //ID:��ͬ��ʶ
    private long ContractID;
    
    //sContractCode:��ͬ���
    private String ContractCode;
    
    //��ִͬ�мƻ�ID
    private long PlanID = -1;
    
    //��ִͬ�мƻ��汾��ID
    private long PlanVersionID = -1;
    
    //�ƻ����
    private double Amount;
    
    //�ƻ����
    private double PlanBalance;
    
    //�ƻ�����
    private Timestamp PlanDate;
    
    //��Ϣ���
    private double FineAmount;
    
    //��Ϣ����
    private double FineInterestRate;
    
    //�Ƿ���
    private long IsCompoundInterest;
    
    //��Ϣ����
    private Timestamp FineDate;
    
    //����ԭ��
    private String OverDueReason;

    //nInputUserID:¼���˱�ʶ ���Ǻ�ͬ������
    private long lInputUserID;

    //¼��������
    private String InputUserName;

    //����˱�ʶ 
    private long lCheckUserID;

    //���������
    private String CheckUserName;

    //�Ƿ���
    private long lIsCompoundInterest;
    
    //nStatusID:����״̬
    private long StatusID;
    private String Status;
    
    //��������
    private long LoanTypeID;
    private String LoanTypeName;//������������
    
    private long loanSubTypeID;

    //sName:���λ����
    private String BorrowClientName;

    //��ҳ��ʾ��ҳ��
    private long PageCount;

    //�ſ�֪ͨ�����
    private String LoanPayCode;

    //�ſ�֪ͨ��ID
    private long LoanPayID;
    
    //��һ����˼���
    private long NextCheckLevel = -1;

    InutParameterInfo inutParameterInfo = null;
    //--------------------------------
    /**
     * function �õ���ͬ��ʶ
     * return ContractID
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param lID
     * function �������ڱ�ʶ
     * return void
     */
    public void setID(long lID)
    {
        this.ID = lID;
    }

    /**
     * function �õ����ڱ��
     * return ContractCode
     */
    public String getOverDueApplyCode()
    {
        return OverDueApplyCode;
    }

    /**
     * @param ContractCode
     * function ���ú�ͬ���
     * return void
     */
    public void setOverDueApplyCode(String Code)
    {
        this.OverDueApplyCode = Code;
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
     * function �õ��ƻ����
     * return PlanBalance
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @param amount
     * function ���üƻ����
     * return void
     */
    public void setAmount(double amount)
    {
        this.Amount = amount;
    }

    /**
     * function �õ��ƻ����
     * return PlanBalance
     */
    public double getPlanBalance()
    {
        return PlanBalance;
    }

    /**
     * @param Amount
     * function ���üƻ����
     * return void
     */
    public void setPlanBalance(double Amount)
    {
        this.PlanBalance = Amount;
    }

    /**
     * function �õ��ƻ����� 
     * return Timestamp
     */
    public Timestamp getPlanDate()
    {
        return PlanDate;
    }

    /**
     * @param timestamp
     * function ���üƻ����� 
     * return void
     */
    public void setPlanDate(Timestamp timestamp)
    {
        this.PlanDate = timestamp;
    }
    
    /**
     * function �õ���Ϣ���
     * return FineAmount
     */
    public double getFineAmount()
    {
        return FineAmount;
    }

    /**
     * @param Amount
     * function ���÷�Ϣ���
     * return void
     */
    public void setFineAmount(double Amount)
    {
        this.FineAmount = Amount;
    }
 
    /**
     * function �õ���Ϣ����
     * return FineInterestRate
     */
    public double getFineInterestRate()
    {
        return FineInterestRate;
    }

    /**
     * @param Amount
     * function ���÷�Ϣ����
     * return void
     */
    public void setFineInterestRate(double dRate)
    {
        this.FineInterestRate = dRate;
    }

    /**
     * function �õ���Ϣ���� 
     * return Timestamp
     */
    public Timestamp getFineDate()
    {
        return FineDate;
    }

    /**
     * @param timestamp
     * function ���÷�Ϣ���� 
     * return void
     */
    public void setFineDate(Timestamp timestamp)
    {
        this.FineDate = timestamp;
    }

    /**
     * function �õ�����ԭ��
     * return OverDueReason
     */
    public String getOverDueReason()
    {
        return OverDueReason;
    }

    /**
     * @param strOverDueReason
     * function ��������ԭ��
     * return void
     */
    public void setOverDueReason(String strOverDueReason)
    {
        this.OverDueReason = strOverDueReason;
    }

    /**
     * function �õ�״̬ID
     * return StatusID
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param StatusID
     * function ����״̬ID
     * return void
     */
    public void setStatusID(long StatusID)
    {
        this.StatusID = StatusID;
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
     * function �õ�¼��������
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param strInputUserName
     * function ����¼��������
     * return void
     */
    public void setInputUserName(String strName)
    {
        this.InputUserName = strName;
    }
    
    /**
     * function �õ����������
     * return CheckUserName
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }

    /**
     * @param strCheckUserName
     * function �������������
     * return void
     */
    public void setCheckUserName(String strName)
    {
        this.CheckUserName = strName;
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
     * function �õ��Ƿ���
     * return IsCompoundInterest
     */
    public long getIsCompoundInterest()
    {
        return IsCompoundInterest;
    }

    /**
     * @param 
     * function �����Ƿ���
     * return void
     */
    public void setIsCompoundInterest(long lIsCompoundInterest)
    {
        this.IsCompoundInterest = lIsCompoundInterest;
    }

    /**
     * function �õ�¼����ID
     * return lInputUserID
     */
    public long getInputUserID()
    {
        return lInputUserID;
    }

    /**
     * @param lInputUserID
     * function ����¼����ID
     * return void
     */
    public void setInputUserID(long lInputUserID)
    {
        this.lInputUserID = lInputUserID;
    }

    /**
     * function �õ������ID
     * return long
     */
    public long getCheckUserID()
    {
        return lCheckUserID;
    }

    /**
     * @param lCheckUserID
     * function ���������ID
     * return void
     */
    public void setCheckUserID(long lCheckUserID)
    {
        this.lCheckUserID = lCheckUserID;
    }

    /**
     * function �õ��ſ�֪ͨ�����
     * return LoanPayCode
     */
    public String getLoanPayCode()
    {
        return LoanPayCode;
    }

    /**
     * @param strLoanPayCode
     * function ���÷ſ�֪ͨ�����
     * return void
     */
    public void setLoanPayCode(String strLoanPayCode)
    {
        this.LoanPayCode = strLoanPayCode;
    }

    /**
     * function �õ��ſ�֪ͨ�����
     * return String
     */
    public long getLoanPayID()
    {
        return LoanPayID;
    }

    /**
     * @param lLoanPayID
     * function ���÷ſ�֪ͨ��ID
     * return void
     */
    public void setLoanPayID(long lLoanPayID)
    {
        this.LoanPayID = lLoanPayID;
    }

    /**
     * function �õ�/���ú�ִͬ�мƻ�ID
     * return long
     */
    public long getPlanID()
    {
        return PlanID;
    }

    /**
     * @param l
     * function �õ�/���ú�ִͬ�мƻ�ID
     * return void
     */
    public void setPlanID(long l)
    {
        this.PlanID = l;
    }

    /**
     * @return Returns the nextCheckLevel.
     */
    public long getNextCheckLevel()
    {
        return NextCheckLevel;
    }
    /**
     * @param nextCheckLevel The nextCheckLevel to set.
     */
    public void setNextCheckLevel(long nextCheckLevel)
    {
        NextCheckLevel = nextCheckLevel;
    }

	public long getLoanSubTypeID() {
		return loanSubTypeID;
	}

	public void setLoanSubTypeID(long loanSubTypeID) {
		this.loanSubTypeID = loanSubTypeID;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}
}

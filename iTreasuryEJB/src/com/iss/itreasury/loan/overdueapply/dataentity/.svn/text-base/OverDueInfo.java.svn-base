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
     * OverDueInfo 构造子注解。
     */
    public OverDueInfo()
    {
        super();
    }
    
    //ID:标识
    private long ID;
    
    //sContractCode:逾期编号
    private String OverDueApplyCode;

    //ID:合同标识
    private long ContractID;
    
    //sContractCode:合同编号
    private String ContractCode;
    
    //合同执行计划ID
    private long PlanID = -1;
    
    //合同执行计划版本号ID
    private long PlanVersionID = -1;
    
    //计划余额
    private double Amount;
    
    //计划余额
    private double PlanBalance;
    
    //计划日期
    private Timestamp PlanDate;
    
    //罚息金额
    private double FineAmount;
    
    //罚息利率
    private double FineInterestRate;
    
    //是否复利
    private long IsCompoundInterest;
    
    //罚息日期
    private Timestamp FineDate;
    
    //逾期原因
    private String OverDueReason;

    //nInputUserID:录入人标识 亦是合同管理人
    private long lInputUserID;

    //录入人姓名
    private String InputUserName;

    //审核人标识 
    private long lCheckUserID;

    //审核人姓名
    private String CheckUserName;

    //是否复利
    private long lIsCompoundInterest;
    
    //nStatusID:逾期状态
    private long StatusID;
    private String Status;
    
    //贷款类型
    private long LoanTypeID;
    private String LoanTypeName;//贷款类型描述
    
    private long loanSubTypeID;

    //sName:贷款单位名称
    private String BorrowClientName;

    //分页显示总页数
    private long PageCount;

    //放款通知单编号
    private String LoanPayCode;

    //放款通知单ID
    private long LoanPayID;
    
    //下一个审核级别
    private long NextCheckLevel = -1;

    InutParameterInfo inutParameterInfo = null;
    //--------------------------------
    /**
     * function 得到合同标识
     * return ContractID
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @param lID
     * function 设置逾期标识
     * return void
     */
    public void setID(long lID)
    {
        this.ID = lID;
    }

    /**
     * function 得到逾期编号
     * return ContractCode
     */
    public String getOverDueApplyCode()
    {
        return OverDueApplyCode;
    }

    /**
     * @param ContractCode
     * function 设置合同编号
     * return void
     */
    public void setOverDueApplyCode(String Code)
    {
        this.OverDueApplyCode = Code;
    }

    /**
     * function 得到合同标识
     * return ContractID
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param lID
     * function 设置合同标识
     * return void
     */
    public void setContractID(long lID)
    {
        this.ContractID = lID;
    }

    /**
     * function 得到合同编号
     * return ContractCode
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param ContractCode
     * function 设置合同编号
     * return void
     */
    public void setContractCode(String ContractCode)
    {
        this.ContractCode = ContractCode;
    }

    /**
     * function 得到合同执行计划版本号ID
     * return PlanVersionID
     */
    public long getPlanVersionID()
    {
        return PlanVersionID;
    }

    /**
     * @param lPlanVersionID
     * function 设置合同执行计划版本号ID
     * return void
     */
    public void setPlanVersionID(long lPlanVersionID)
    {
        this.PlanVersionID = lPlanVersionID;
    }

    /**
     * function 得到计划金额
     * return PlanBalance
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @param amount
     * function 设置计划金额
     * return void
     */
    public void setAmount(double amount)
    {
        this.Amount = amount;
    }

    /**
     * function 得到计划余额
     * return PlanBalance
     */
    public double getPlanBalance()
    {
        return PlanBalance;
    }

    /**
     * @param Amount
     * function 设置计划余额
     * return void
     */
    public void setPlanBalance(double Amount)
    {
        this.PlanBalance = Amount;
    }

    /**
     * function 得到计划日期 
     * return Timestamp
     */
    public Timestamp getPlanDate()
    {
        return PlanDate;
    }

    /**
     * @param timestamp
     * function 设置计划日期 
     * return void
     */
    public void setPlanDate(Timestamp timestamp)
    {
        this.PlanDate = timestamp;
    }
    
    /**
     * function 得到罚息金额
     * return FineAmount
     */
    public double getFineAmount()
    {
        return FineAmount;
    }

    /**
     * @param Amount
     * function 设置罚息金额
     * return void
     */
    public void setFineAmount(double Amount)
    {
        this.FineAmount = Amount;
    }
 
    /**
     * function 得到罚息利率
     * return FineInterestRate
     */
    public double getFineInterestRate()
    {
        return FineInterestRate;
    }

    /**
     * @param Amount
     * function 设置罚息利率
     * return void
     */
    public void setFineInterestRate(double dRate)
    {
        this.FineInterestRate = dRate;
    }

    /**
     * function 得到罚息日期 
     * return Timestamp
     */
    public Timestamp getFineDate()
    {
        return FineDate;
    }

    /**
     * @param timestamp
     * function 设置罚息日期 
     * return void
     */
    public void setFineDate(Timestamp timestamp)
    {
        this.FineDate = timestamp;
    }

    /**
     * function 得到逾期原因
     * return OverDueReason
     */
    public String getOverDueReason()
    {
        return OverDueReason;
    }

    /**
     * @param strOverDueReason
     * function 设置逾期原因
     * return void
     */
    public void setOverDueReason(String strOverDueReason)
    {
        this.OverDueReason = strOverDueReason;
    }

    /**
     * function 得到状态ID
     * return StatusID
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param StatusID
     * function 设置状态ID
     * return void
     */
    public void setStatusID(long StatusID)
    {
        this.StatusID = StatusID;
    }
     
    /**
     * function 得到状态描述
     * return Status
     */
    public String getStatus()
    {
        return Status;
    }

    /**
     * @param Status
     * function 设置状态描述
     * return void
     */
    public void setStatus(String Status)
    {
        this.Status = Status;
    }
    
    /**
     * function 得到贷款类型
     * return lTypeID
     */
    public long getLoanTypeID()
    {
        return LoanTypeID;
    }

    /**
     * @param lTypeID
     * function 设置贷款类型
     * return void
     */
    public void setLoanTypeID(long lTypeID)
    {
        this.LoanTypeID = lTypeID;
    }

    /**
     * function 得到贷款类型描述
     * return LoanTypeName
     */
    public String getLoanTypeName()
    {
        return LoanTypeName;
    }

    /**
     * @param LoanTypeName
     * function 设置贷款类型描述
     * return void
     */
    public void setLoanTypeName(String LoanTypeName)
    {
        this.LoanTypeName = LoanTypeName;
    }

    /**
     * function 得到录入人名称
     * return InputUserName
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

    /**
     * @param strInputUserName
     * function 设置录入人名称
     * return void
     */
    public void setInputUserName(String strName)
    {
        this.InputUserName = strName;
    }
    
    /**
     * function 得到审核人名称
     * return CheckUserName
     */
    public String getCheckUserName()
    {
        return CheckUserName;
    }

    /**
     * @param strCheckUserName
     * function 设置审核人名称
     * return void
     */
    public void setCheckUserName(String strName)
    {
        this.CheckUserName = strName;
    }

    /**
     * function 得到贷款单位名称
     * return BorrowClientName
     */
    public String getBorrowClientName()
    {
        return BorrowClientName;
    }

    /**
     * @param BorrowClientName
     * function 设置贷款单位名称
     * return void
     */
    public void setBorrowClientName(String BorrowClientName)
    {
        this.BorrowClientName = BorrowClientName;
    }

    /**
     * function 得到分页显示总页数
     * return PageCount
     */
    public long getPageCount()
    {
        return PageCount;
    }

    /**
     * @param lPageCount
     * function 设置分页显示总页数
     * return void
     */
    public void setPageCount(long lPageCount)
    {
        this.PageCount = lPageCount;
    }

    /**
     * function 得到是否复利
     * return IsCompoundInterest
     */
    public long getIsCompoundInterest()
    {
        return IsCompoundInterest;
    }

    /**
     * @param 
     * function 设置是否复利
     * return void
     */
    public void setIsCompoundInterest(long lIsCompoundInterest)
    {
        this.IsCompoundInterest = lIsCompoundInterest;
    }

    /**
     * function 得到录入人ID
     * return lInputUserID
     */
    public long getInputUserID()
    {
        return lInputUserID;
    }

    /**
     * @param lInputUserID
     * function 设置录入人ID
     * return void
     */
    public void setInputUserID(long lInputUserID)
    {
        this.lInputUserID = lInputUserID;
    }

    /**
     * function 得到审核人ID
     * return long
     */
    public long getCheckUserID()
    {
        return lCheckUserID;
    }

    /**
     * @param lCheckUserID
     * function 设置审核人ID
     * return void
     */
    public void setCheckUserID(long lCheckUserID)
    {
        this.lCheckUserID = lCheckUserID;
    }

    /**
     * function 得到放款通知单编号
     * return LoanPayCode
     */
    public String getLoanPayCode()
    {
        return LoanPayCode;
    }

    /**
     * @param strLoanPayCode
     * function 设置放款通知单编号
     * return void
     */
    public void setLoanPayCode(String strLoanPayCode)
    {
        this.LoanPayCode = strLoanPayCode;
    }

    /**
     * function 得到放款通知单编号
     * return String
     */
    public long getLoanPayID()
    {
        return LoanPayID;
    }

    /**
     * @param lLoanPayID
     * function 设置放款通知单ID
     * return void
     */
    public void setLoanPayID(long lLoanPayID)
    {
        this.LoanPayID = lLoanPayID;
    }

    /**
     * function 得到/设置合同执行计划ID
     * return long
     */
    public long getPlanID()
    {
        return PlanID;
    }

    /**
     * @param l
     * function 得到/设置合同执行计划ID
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

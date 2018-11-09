/*
 * queryExtendInfo.java
 *
 * Created on 2002年4月9日, 下午4:33
 */

package com.iss.itreasury.loan.query.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.util.LOANConstant;

/**
 *
 * @author  yzhang
 * @version
 */
public class QuestExtendInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new queryExtendInfo */
	public QuestExtendInfo() {
		super();
	}


	//展期申请标示
	private long ExtendID = -1;

    private long TypeID = -1; // 12种贷款类型

	//贷款种类
    private String LoanType = "";
    
    //贷款类型名称
    private String LoanTypeName = "";

    //合同标示
    private long ContractID = -1;

	//合同号
    private String ContractCode = "";

	//借款单位
    private String ClientName = "";

	//委托单位
    private String ConsignClientName = "";

    //下一级审核人
    private String nextCheckUserName = "";

    //展期申请号
    private long ExtendNo = -1;
    
    private String applyCode = "";//申请编号,说明:采用新的编码规则进行配置生成

	//贷款金额
    private double Amount = 0;

    //展期金额
    private double ExtendAmount = 0;

	//合同利率
    private double ContractRate = 0;

    //执行利率
    private double Rate = 0;

    //展期利率
    private double ExtendRate = 0;

    //浮动比例
    private double AdjustRate = 0;

    //固定浮动利率
    private double StaidAdjustRate = 0;

	//起始贷款日期
	public Timestamp DateFrom = null;

	//截至贷款日期
    private Timestamp DateTo = null;

	//展期状态
    private long StatusID = -1;

	//-------------------------------------------//
    //beg
    private long ContractIDBeg = -1;

	//end
    private long ContractIDEnd = -1;

    //code beg
    private String ContractCodeBeg="";

    //code end
    private String ContractCodeEnd="";

    //
    private long ClientID = -1;

    //
    private long ConsignClientID = -1;

    //
    private long AccountID = -1;

    //
    private long ConsignAccountID = -1;

    //贷款展期金额beg
    private double ExtendAmountBeg = 0;

    //贷款展期金额End
    private double ExtendAmountEnd = 0;
    
    private long nOfficeId=-1; //区分办事处mzh_fu 2007-3-14 added
    
    private long nCurrencyId=-1;//区分币种 zhanglei 2010-08-16 add

	/**add by wmzheng at 2010-10-14 贷款查询优化**/
	//贷款种类(复选)
    private String loanTypeIDs="";
	//展期状态(复选)
    private String statusIDs = "";
	//借款单位由
	private long borrowClientIDFrom=-1;
	//借款单位至
	private long borrowClientIDTo=-1;
	//委托单位由
	private long consignClientIDFrom=-1;
	//委托单位至
	private long consignClientIDTo=-1;	
	//展期利率由
	private double extendRateFrom = 0.0;
	//展期利率至
	private double extendRateTo = 0.0;	
	//录入开始时间
	private Timestamp maxInputDate=null;
	//录入结束时间
	private Timestamp minInputDate=null;
    //贷款金额beg
    private double loanAmountBeg = 0;
    //贷款金额End
    private double loanAmountEnd = 0;	
    /**add by wmzheng at 2010-10-14 贷款查询优化**/
    
	private String inputUserName = ""; //录入人
	
	private Timestamp inputDate = null; //录入日期
	
    //--------------------------------------------//
    /**
     * function 得到/设置
     * return long
     */
    public long getExtendID()
    {
        return ExtendID;
    }

    public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	/**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setExtendID(long l)
    {
        this.ExtendID = l;
    }

    /**
     * function 得到/设置
     * return String
     */
    public String getLoanType()
    {
        if(getTypeID() > 0)
        {
            LoanType=LOANConstant.LoanType.getName(getTypeID());
        }
        return LoanType;
    }

    /**
     * @param string
     * function 得到/设置
     * return void
     */
    public void setLoanType(String string)
    {
        this.LoanType = string;
    }

    /**
     * function 得到/设置
     * return String
     */
    public String getContractCode()
    {
        return ContractCode;
    }

    /**
     * @param string
     * function 得到/设置
     * return void
     */
    public void setContractCode(String string)
    {
        this.ContractCode = string;
    }

    /**
     * function 得到/设置
     * return String
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param string
     * function 得到/设置
     * return void
     */
    public void setClientName(String string)
    {
        this.ClientName = string;
    }

    /**
     * function 得到/设置
     * return String
     */
    public String getConsignClientName()
    {
        return ConsignClientName;
    }

    /**
     * @param string
     * function 得到/设置
     * return void
     */
    public void setConsignClientName(String string)
    {
        this.ConsignClientName = string;
    }

    /**
     * function 得到/设置
     * return String
     */
    public long getExtendNo()
    {
        return ExtendNo;
    }

    /**
     * @param string
     * function 得到/设置
     * return void
     */
    public void setExtendNo(long l)
    {
        this.ExtendNo = l;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setAmount(double d)
    {
        this.Amount = d;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getExtendAmount()
    {
        return ExtendAmount;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setExtendAmount(double d)
    {
        this.ExtendAmount = d;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getContractRate()
    {
        ContractDao dao = new ContractDao();
        RateInfo ri=new RateInfo();
        try
        {

            ri=dao.getLatelyRate(-1,getContractID(),null);
            ContractRate=ri.getRate();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ContractRate;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setContractRate(double d)
    {
        this.ContractRate = d;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getRate()
    {
        ContractDao dao = new ContractDao();
        RateInfo ri=new RateInfo();
        try
        {

            ri=dao.getLatelyRate(-1,getContractID(),null);
            Rate=ri.getLateRate();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Rate;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setRate(double d)
    {
        this.Rate = d;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getExtendRate()
    {
        return ExtendRate;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setExtendRate(double d)
    {
        this.ExtendRate = d;
    }

    /**
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getDateFrom()
    {
        return DateFrom;
    }

    /**
     * @param timestamp
     * function 得到/设置
     * return void
     */
    public void setDateFrom(Timestamp timestamp)
    {
        this.DateFrom = timestamp;
    }

    /**
     * function 得到/设置
     * return Timestamp
     */
    public Timestamp getDateTo()
    {
        return DateTo;
    }

    /**
     * @param timestamp
     * function 得到/设置
     * return void
     */
    public void setDateTo(Timestamp timestamp)
    {
        this.DateTo = timestamp;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setStatusID(long l)
    {
        this.StatusID = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getContractID()
    {
        return ContractID;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setContractID(long l)
    {
        this.ContractID = l;
    }

    /**
     * @param
     * function 得到/设置
     * return long
     */
    public long getTypeID()
    {
        return TypeID;
    }

    /**
     * @param
     * function 得到/设置
     * return void
     */
    public void setTypeID(long l)
    {
        this.TypeID = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getContractIDBeg()
    {
        return ContractIDBeg;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setContractIDBeg(long l)
    {
        this.ContractIDBeg = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getContractIDEnd()
    {
        return ContractIDEnd;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setContractIDEnd(long l)
    {
        this.ContractIDEnd = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setClientID(long l)
    {
        this.ClientID = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getConsignClientID()
    {
        return ConsignClientID;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setConsignClientID(long l)
    {
        this.ConsignClientID = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getAccountID()
    {
        return AccountID;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setAccountID(long l)
    {
        this.AccountID = l;
    }

    /**
     * function 得到/设置
     * return long
     */
    public long getConsignAccountID()
    {
        return ConsignAccountID;
    }

    /**
     * @param l
     * function 得到/设置
     * return void
     */
    public void setConsignAccountID(long l)
    {
        this.ConsignAccountID = l;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getExtendAmountBeg()
    {
        return ExtendAmountBeg;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setExtendAmountBeg(double d)
    {
        this.ExtendAmountBeg = d;
    }

    /**
     * function 得到/设置
     * return double
     */
    public double getExtendAmountEnd()
    {
        return ExtendAmountEnd;
    }

    /**
     * @param d
     * function 得到/设置
     * return void
     */
    public void setExtendAmountEnd(double d)
    {
        this.ExtendAmountEnd = d;
    }

	/**
	 * @return
	 */
	public String getContractCodeBeg()
	{
		return ContractCodeBeg;
	}

	/**
	 * @return
	 */
	public String getContractCodeEnd()
	{
		return ContractCodeEnd;
	}

	/**
	 * @param string
	 */
	public void setContractCodeBeg(String string)
	{
		ContractCodeBeg = string;
	}

	/**
	 * @param string
	 */
	public void setContractCodeEnd(String string)
	{
		ContractCodeEnd = string;
	}

        /**
         * @return
         */
        public String getNextCheckUserName()
        {
                return nextCheckUserName;
        }

        /**
         * @param string
         */
        public void setNextCheckUserName(String string)
        {
                nextCheckUserName = string;
        }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getAdjustRate()
    {
        return AdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return double
     */
    public double getStaidAdjustRate()
    {
        return StaidAdjustRate;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setAdjustRate(double d)
    {
        AdjustRate = d;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setStaidAdjustRate(double d)
    {
        StaidAdjustRate = d;
    }

	public long getNOfficeId() {
		return nOfficeId;
	}

	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public long getNCurrencyId() {
		return nCurrencyId;
	}

	public void setNCurrencyId(long currencyId) {
		nCurrencyId = currencyId;
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

	public double getExtendRateFrom() {
		return extendRateFrom;
	}

	public void setExtendRateFrom(double extendRateFrom) {
		this.extendRateFrom = extendRateFrom;
	}

	public double getExtendRateTo() {
		return extendRateTo;
	}

	public void setExtendRateTo(double extendRateTo) {
		this.extendRateTo = extendRateTo;
	}

	public String getLoanTypeIDs() {
		return loanTypeIDs;
	}

	public void setLoanTypeIDs(String loanTypeIDs) {
		this.loanTypeIDs = loanTypeIDs;
	}

	public Timestamp getMaxInputDate() {
		return maxInputDate;
	}

	public void setMaxInputDate(Timestamp maxInputDate) {
		this.maxInputDate = maxInputDate;
	}

	public Timestamp getMinInputDate() {
		return minInputDate;
	}

	public void setMinInputDate(Timestamp minInputDate) {
		this.minInputDate = minInputDate;
	}

	public String getStatusIDs() {
		return statusIDs;
	}

	public void setStatusIDs(String statusIDs) {
		this.statusIDs = statusIDs;
	}

	public double getLoanAmountBeg() {
		return loanAmountBeg;
	}

	public void setLoanAmountBeg(double loanAmountBeg) {
		this.loanAmountBeg = loanAmountBeg;
	}

	public double getLoanAmountEnd() {
		return loanAmountEnd;
	}

	public void setLoanAmountEnd(double loanAmountEnd) {
		this.loanAmountEnd = loanAmountEnd;
	}

	public String getLoanTypeName() {
		return LoanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		LoanTypeName = loanTypeName;
	}

}
/*
 * LoanPayNoticeInfo.java
 *
 * Created on 2002年4月15日
 */

package com.iss.itreasury.loan.loanpaynotice.dataentity ;

import java.rmi.RemoteException ;
import java.beans.* ;
import java.sql.* ;

import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;

/**
 *
 * @author
 * @version
 */
public class LoanPayNoticeInfo
      implements java.io.Serializable
{
    /** Creates new LoanPayNoticeInfo */
    public LoanPayNoticeInfo()
    {
        super() ;
    }

    private long ID = -1 ; //标识
    private String Code = "" ; //代码，放款通知单编号
    private long ContractID = -1 ; //合同ID
    private Timestamp OutDate = null ; //放款日期
    private double Amount = 0.0 ; //金额
    private String ConsignAccount = "" ; //委托方账户号
    private long InterestTypeID = 1 ; //利率类型ID
    private long BankInterestID = -1 ; //银行利率ID
    private long LiborRateID = -1 ; //Libor利率ID
    private double CommissionRate = 0.0 ; //手续费率
    private double SuretyFeeRate = 0.0 ; //担保费率
    private Timestamp Start = null ; //贷款起始日期
    private Timestamp End = null ; //贷款结束日期
    private String ReceiveClientName = "" ; //收款单位名称
    private String ReceiveAccount = "" ; //收款单位账户
    private String RemitBank = "" ; //汇入行
    private String CompanyLeader = "" ; //公司领导
    private String HandlingPerson = "" ; //经办人
    private String DepartmentLeader = "" ; //部门领导
    private long StatusID = -1 ; //状态
    private long InputUserID = -1 ; //录入用户ID
    private String InputUserName = "";
    private Timestamp InputDate = null ; //录入日期
    private long NextCheckUserID = -1 ; //下一审批人
    private String nextCheckUser="";
    private long NextCheckLevel = -1;	//下一个审核级别
    private long GrantCurrentAccountID = -1 ; //发放至活期账户
    private long GrantTypeID = -1 ; //放款方式
    private String RemitinProvince = "" ; //汇入地（省）
    private String RemitinCity = "" ; //汇入地（市）
    private String LoanAccount = "" ; //借款单位账户
    private String CheckPerson = "" ; //复核人
    private long AccountBankID = -1 ; //开户银行ID
    private double WTInterestRate = 0.0 ; //贷款利率，委托贷款使用
    private Timestamp interestRateValidate = null;//贷款利率生效日期
    private long DrawNoticeID = -1 ; //银团提款通知单标示
    private String strDrawNoticeCode = "" ; //银团提款通知单单号
    //新增
    private Timestamp InDate = null ; //还款日期
    private String LoanClientName = "" ; //借款单位名称
    private String LoanClientCode = "" ; //借款单位编码
    private long LoanClientID = -1 ; //借款单位ID
    private String LoanZipCode = "" ; //借款单位邮编
    private String LoanPhone = "" ; //借款单位电话
    private String LoanAddress = "" ; //借款单位地址
    private String LoanKind = "" ; //放款种类
    private double LoanAmount = 0.0 ; //合同金额
    private String ConsignClientName = "" ; //委托方客户名称
    private long AddOrDecrease = 1 ; //浮动利率正负标识（正1负2）
    private double AdjustRate = 0.0 ; //浮动利率
    /*add by yanliu*/
    private double BasicInterestRate = 0.0;//基准利率
    private double StaidAdjustRate = 0.0;//固定浮动利率
    private double PreBasicInterestRate = 0.0;//调整前基准利率
    private double PreAdjustRate = 0.0;//调整前浮动利率
    private double PreStaidAdjustRate = 0.0;//调整前固定浮动利率
	/*add by yanliu*/
    private double ContractRate = 0.0 ; //合同利率
    private long IntervalNum = -1 ; //贷款期限
    private String LoanPurpose = "" ; //原因
    private String ContractCode = "" ; //贷款合同编号
    private double Mbalance = 0.0 ; //贷款余额
    private long Count = -1 ; // 记录数
    private long PageCount = -1 ; // Page Count
    private long AllPage = 0 ; //记录总页数
    private double InterestRate = 0.0 ; //利率
    private String GrantCurrentAccount = "" ; //活期账户号
    private String GrantCurrentName = "" ; //活期账户单位名称
    private long ModuleID = -1 ; //模块ID
    private long LoanTypeID = -1 ; //类型ID
    private long ActionID = -1 ; //操作ID
    private long ApprovalID = -1 ; //lApprovalID
    private long lOfficeID = -1 ; //lApprovalID
    private double Interest = 0.0 ; //当前利息
    private double Balance = 0.0 ; //账户当前余额
    private String AccountBankName = "" ; //开户银行
    private String AccountBankCode = "" ; //银行账户编号
    private String ReceiveClientCode = "" ; //收款单位账号
    //private long RemitoutBankID = -1; //汇出行ID
    //private String RemitinAccountNo = ""; //汇入收款单位账户号
    //private long RemitinBankID = -1; //汇入行ID
    //private long CashFlowID = -1; //现金流向
    
	private long CurrencyID= -1;
	//private long OfficeID= -1;
	InutParameterInfo inutParameterInfo = null;	
	String loanSubTypeName = "";
	String borrowClientName = "";
    //modify by xwhe 2008-06-10
	private double IntervalNoticeNum = 0.0 ; //放款单期限
	
	private String loanTypeName; //贷款类型子类型名称
	
	//Boxu Add 2008年12月18日
	private Timestamp rateadjustdate = null;  //利率调整日期
	
	private long isRemitCompoundInterest 	= -1; //是否计算复利
	private long isRemitOverDueInterest 	= -1; //是否计算罚息
    private double overDueAdjustRate = 0.0 ; //比例浮动
    private double overDueStaidAdjustRate = 0.0 ; //固定浮动点
    private double overDueInterestRate = 0.0 ; //利率
	
	
	
    public Timestamp getRateadjustdate() {
		return rateadjustdate;
	}
	public void setRateadjustdate(Timestamp rateadjustdate) {
		this.rateadjustdate = rateadjustdate;
	}

	public long getActionID()
    {
        return ActionID ;
    }

    public long getOfficeID()
    {
        return lOfficeID ;
    }

    public long getAllPage()
    {
        return AllPage ;
    }

    public double getAmount()
    {
        return Amount ;
    }

    public long getApprovalID()
    {
        return ApprovalID ;
    }

    public long getBankInterestID()
    {
        return BankInterestID ;
    }

    public String getCode()
    {
        return Code == null ? "" : Code ;
    }

    public double getCommissionRate()
    {
        return CommissionRate ;
    }

    public String getCompanyLeader()
    {
        return CompanyLeader == null ? "" : CompanyLeader ;
    }

    public String getConsignAccount()
    {
        return ConsignAccount == null ? "" : ConsignAccount ;
    }

    public String getConsignClientName()
    {
        return ConsignClientName == null ? "" : ConsignClientName ;
    }

    public String getContractCode()
    {
        return ContractCode == null ? "" : ContractCode ;
    }

    public long getContractID()
    {
        return ContractID ;
    }

    public long getCount()
    {
        return Count ;
    }

    public String getDepartmentLeader()
    {
        return DepartmentLeader == null ? "" : DepartmentLeader ;
    }

    public long getDrawNoticeID()
    {
        return DrawNoticeID ;
    }

    public String getDrawNoticeCode()
    {
        return strDrawNoticeCode ;
    }

    public void setDrawNoticeCode( String strDrawNoticeCode )
    {
        this.strDrawNoticeCode = strDrawNoticeCode ;
    }

    public Timestamp getEnd()
    {
        return End ;
    }

    public long getGrantCurrentAccountID()
    {
        return GrantCurrentAccountID ;
    }

    public long getGrantTypeID()
    {
        return GrantTypeID ;
    }

    public String getHandlingPerson()
    {
        return HandlingPerson == null ? "" : HandlingPerson ;
    }

    public long getID()
    {
        return ID ;
    }

    public Timestamp getInputDate()
    {
        return InputDate ;
    }

    public long getInputUserID()
    {
        return InputUserID ;
    }

    public double getInterestRate()
    {
        return InterestRate ;
    }

    public long getIntervalNum()
    {
        return IntervalNum ;
    }

    public String getLoanAccount()
    {
        return LoanAccount == null ? "" : LoanAccount ;
    }

    public String getLoanAddress()
    {
        return LoanAddress == null ? "" : LoanAddress ;
    }

    public String getLoanClientCode()
    {
        return LoanClientCode == null ? "" : LoanClientCode ;
    }

    public long getLoanClientID()
    {
        return LoanClientID ;
    }

    public String getLoanClientName()
    {
        return LoanClientName == null ? "" : LoanClientName ;
    }

    public String getLoanKind()
    {
        return LoanKind == null ? "" : LoanKind ;
    }

    public String getLoanPhone()
    {
        return LoanPhone == null ? "" : LoanPhone ;
    }

    public long getLoanTypeID()
    {
        return LoanTypeID ;
    }

    public String getLoanZipCode()
    {
        return LoanZipCode == null ? "" : LoanZipCode ;
    }

    public double getMbalance()
    {
        return Mbalance ;
    }

    public long getModuleID()
    {
        return ModuleID ;
    }

    public long getNextCheckUserID()
    {
        return NextCheckUserID ;
    }

    public Timestamp getOutDate()
    {
        return OutDate ;
    }

    public long getPageCount()
    {
        return PageCount ;
    }

    public String getLoanPurpose()
    {
        return LoanPurpose == null ? "" : LoanPurpose ;
    }

    public String getReceiveClientName()
    {
        return ReceiveClientName == null ? "" : ReceiveClientName ;
    }

    public String getRemitBank()
    {
        return RemitBank == null ? "" : RemitBank ;
    }

    public String getRemitinCity()
    {
        return RemitinCity == null ? "" : RemitinCity ;
    }

    public String getRemitinProvince()
    {
        return RemitinProvince == null ? "" : RemitinProvince ;
    }

    public Timestamp getStart()
    {
        return Start ;
    }

    public long getStatusID()
    {
        return StatusID ;
    }

    public double getSuretyFeeRate()
    {
        return SuretyFeeRate ;
    }

    public void setActionID( long ActionID )
    {
        this.ActionID = ActionID ;
    }

    public void setAllPage( long AllPage )
    {
        this.AllPage = AllPage ;
    }

    public void setAmount( double Amount )
    {
        this.Amount = Amount ;
    }

    public void setApprovalID( long ApprovalID )
    {
        this.ApprovalID = ApprovalID ;
    }

    public void setBankInterestID( long BankInterestID )
    {
        this.BankInterestID = BankInterestID ;
    }

    public void setCode( String Code )
    {
        this.Code = Code ;
    }

    public void setCommissionRate( double CommissionRate )
    {
        this.CommissionRate = CommissionRate ;
    }

    public void setCompanyLeader( String CompanyLeader )
    {
        this.CompanyLeader = CompanyLeader ;
    }

    public void setConsignAccount( String ConsignAccount )
    {
        this.ConsignAccount = ConsignAccount ;
    }

    public void setConsignClientName( String ConsignClientName )
    {
        this.ConsignClientName = ConsignClientName ;
    }

    public void setContractCode( String ContractCode )
    {
        this.ContractCode = ContractCode ;
    }

    public void setContractID( long ContractID )
    {
        this.ContractID = ContractID ;
    }

    public void setCount( long Count )
    {
        this.Count = Count ;
    }

    public void setDepartmentLeader( String DepartmentLeader )
    {
        this.DepartmentLeader = DepartmentLeader ;
    }

    public void setDrawNoticeID( long DrawNoticeID )
    {
        this.DrawNoticeID = DrawNoticeID ;
    }

    public void setEnd( Timestamp End )
    {
        this.End = End ;
    }

    public void setGrantCurrentAccountID( long GrantCurrentAccountID )
    {
        this.GrantCurrentAccountID = GrantCurrentAccountID ;
    }

    public void setGrantTypeID( long GrantTypeID )
    {
        this.GrantTypeID = GrantTypeID ;
    }

    public void setHandlingPerson( String HandlingPerson )
    {
        this.HandlingPerson = HandlingPerson ;
    }

    public void setID( long ID )
    {
        this.ID = ID ;
    }

    public void setInputDate( Timestamp InputDate )
    {
        this.InputDate = InputDate ;
    }

    public void setInputUserID( long InputUserID )
    {
        this.InputUserID = InputUserID ;
    }

    public void setInterestRate( double InterestRate )
    {
        this.InterestRate = InterestRate ;
    }

    public void setIntervalNum( long IntervalNum )
    {
        this.IntervalNum = IntervalNum ;
    }

    public void setLoanAccount( String LoanAccount )
    {
        this.LoanAccount = LoanAccount ;
    }

    public void setLoanAddress( String LoanAddress )
    {
        this.LoanAddress = LoanAddress ;
    }

    public void setLoanClientCode( String LoanClientCode )
    {
        this.LoanClientCode = LoanClientCode ;
    }

    public void setLoanClientID( long LoanClientID )
    {
        this.LoanClientID = LoanClientID ;
    }

    public void setLoanClientName( String LoanClientName )
    {
        this.LoanClientName = LoanClientName ;
    }

    public void setLoanKind( String LoanKind )
    {
        this.LoanKind = LoanKind ;
    }

    public void setLoanPhone( String LoanPhone )
    {
        this.LoanPhone = LoanPhone ;
    }

    public void setLoanTypeID( long LoanTypeID )
    {
        this.LoanTypeID = LoanTypeID ;
    }

    public void setLoanZipCode( String LoanZipCode )
    {
        this.LoanZipCode = LoanZipCode ;
    }

    public void setMbalance( double Mbalance )
    {
        this.Mbalance = Mbalance ;
    }

    public void setOfficeID( long lOfficeID )
    {
        this.lOfficeID = lOfficeID ;
    }

    public void setModuleID( long ModuleID )
    {
        this.ModuleID = ModuleID ;
    }

    public void setNextCheckUserID( long NextCheckUserID )
    {
        this.NextCheckUserID = NextCheckUserID ;
    }

    public void setOutDate( Timestamp OutDate )
    {
        this.OutDate = OutDate ;
    }

    public void setPageCount( long PageCount )
    {
        this.PageCount = PageCount ;
    }

    public void setLoanPurpose( String LoanPurpose )
    {
        this.LoanPurpose = LoanPurpose ;
    }

    public void setReceiveClientName( String ReceiveClientName )
    {
        this.ReceiveClientName = ReceiveClientName ;
    }

    public void setRemitBank( String RemitBank )
    {
        this.RemitBank = RemitBank ;
    }

    public void setRemitinCity( String RemitinCity )
    {
        this.RemitinCity = RemitinCity ;
    }

    public void setRemitinProvince( String RemitinProvince )
    {
        this.RemitinProvince = RemitinProvince ;
    }

    public void setStart( Timestamp Start )
    {
        this.Start = Start ;
    }

    public void setStatusID( long StatusID )
    {
        this.StatusID = StatusID ;
    }

    public void setSuretyFeeRate( double SuretyFeeRate )
    {
        this.SuretyFeeRate = SuretyFeeRate ;
    }

    public String getCheckPerson()
    {
        return CheckPerson ;
    }

    public void setCheckPerson( String CheckPerson )
    {
        this.CheckPerson = CheckPerson ;
    }

    public double getLoanAmount()
    {
        return LoanAmount ;
    }

    public void setLoanAmount( double LoanAmount )
    {
        this.LoanAmount = LoanAmount ;
    }

    public double getContractRate()
    {
        return ContractRate ;
    }

    public void setContractRate( double ContractRate )
    {
        this.ContractRate = ContractRate ;
    }

    public String getGrantCurrentAccount()
    {
        return GrantCurrentAccount ;
    }

    public void setGrantCurrentAccount( String GrantCurrentAccount )
    {
        this.GrantCurrentAccount = GrantCurrentAccount ;
    }

    public long getAddOrDecrease()
    {
        return AddOrDecrease ;
    }

    public void setAddOrDecrease( long AddOrDecrease )
    {
        this.AddOrDecrease = AddOrDecrease ;
    }

    public double getAdjustRate()
    {
        return AdjustRate ;
    }

    public void setAdjustRate( double AdjustRate )
    {
        this.AdjustRate = AdjustRate ;
    }

    /**
     * Returns the inDate.
     * @return Timestamp
     */
    public Timestamp getInDate()
    {
        return InDate ;
    }

    /**
     * Sets the inDate.
     * @param inDate The inDate to set
     */
    public void setInDate( Timestamp inDate )
    {
        InDate = inDate ;
    }

    /**
     * Returns the accountBankID.
     * @return long
     */
    public long getAccountBankID()
    {
        return AccountBankID ;
    }

    /**
     * Sets the accountBankID.
     * @param accountBankID The accountBankID to set
     */
    public void setAccountBankID( long accountBankID )
    {
        AccountBankID = accountBankID ;
    }

    /**
     * Returns the balance.
     * @return double
     */
    public double getBalance()
    {
        return Balance ;
    }

    /**
     * Returns the interest.
     * @return double
     */
    public double getInterest()
    {
        return Interest ;
    }

    /**
     * Sets the balance.
     * @param balance The balance to set
     */
    public void setBalance( double balance )
    {
        Balance = balance ;
    }

    /**
     * Sets the interest.
     * @param interest The interest to set
     */
    public void setInterest( double interest )
    {
        Interest = interest ;
    }

    /**
     * Returns the accountBankCode.
     * @return String
     */
    public String getAccountBankCode()
    {
        return AccountBankCode ;
    }

    /**
     * Returns the accountBankName.
     * @return String
     */
    public String getAccountBankName()
    {
        return AccountBankName ;
    }

    /**
     * Sets the accountBankCode.
     * @param accountBankCode The accountBankCode to set
     */
    public void setAccountBankCode( String accountBankCode )
    {
        AccountBankCode = accountBankCode ;
    }

    /**
     * Sets the accountBankName.
     * @param accountBankName The accountBankName to set
     */
    public void setAccountBankName( String accountBankName )
    {
        AccountBankName = accountBankName ;
    }

    /**
     * Returns the receiveClientCode.
     * @return String
     */
    public String getReceiveClientCode()
    {
        return ReceiveClientCode ;
    }

    /**
     * Sets the receiveClientCode.
     * @param receiveClientCode The receiveClientCode to set
     */
    public void setReceiveClientCode( String receiveClientCode )
    {
        ReceiveClientCode = receiveClientCode ;
    }

    /**
     * Returns the grantCurrentName.
     * @return String
     */
    public String getGrantCurrentName()
    {
        return GrantCurrentName ;
    }

    /**
     * Sets the grantCurrentName.
     * @param grantCurrentName The grantCurrentName to set
     */
    public void setGrantCurrentName( String grantCurrentName )
    {
        GrantCurrentName = grantCurrentName ;
    }

    /**
     * Returns the wTInterestRate.
     * @return double
     */
    public double getWTInterestRate()
    {
        return WTInterestRate ;
    }

    /**
     * Sets the wTInterestRate.
     * @param wTInterestRate The wTInterestRate to set
     */
    public void setWTInterestRate( double wTInterestRate )
    {
        WTInterestRate = wTInterestRate ;
    }

    /**
     * Returns the receiveAccount.
     * @return String
     */
    public String getReceiveAccount()
    {
        return ReceiveAccount ;
    }

    /**
     * Sets the receiveAccount.
     * @param receiveAccount The receiveAccount to set
     */
    public void setReceiveAccount( String receiveAccount )
    {
        ReceiveAccount = receiveAccount ;
    }
    /**
     * @param s
     * function 得到/设置录入人
     * return void
     */
    public void setInputUserName(String s)
    {
        this.InputUserName = s;
    }

    /**
     * function 得到/设置录入人
     * return String
     */
    public String getInputUserName()
    {
        return InputUserName;
    }

	/**
	 * @return
	 */
	public long getLOfficeID() {
		return lOfficeID;
	}

	/**
	 * @return
	 */
	public String getNextCheckUser() {
		return nextCheckUser;
	}

	/**
	 * @return
	 */
	public String getStrDrawNoticeCode() {
		return strDrawNoticeCode;
	}

	/**
	 * @param l
	 */
	public void setLOfficeID(long l) {
		lOfficeID = l;
	}

	/**
	 * @param string
	 */
	public void setNextCheckUser(String string) {
		nextCheckUser = string;
	}

	/**
	 * @param string
	 */
	public void setStrDrawNoticeCode(String string) {
		strDrawNoticeCode = string;
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
	/**
	 * @return
	 */
	public double getStaidAdjustRate()
	{
		return StaidAdjustRate;
	}

	/**
	 * @param d
	 */
	public void setStaidAdjustRate(double d)
	{
		StaidAdjustRate = d;
	}

	/**
	 * @return
	 */
	public double getBasicInterestRate()
	{
		return BasicInterestRate;
	}

	/**
	 * @param d
	 */
	public void setBasicInterestRate(double d)
	{
		BasicInterestRate = d;
	}

	/**
	 * @return
	 */
	public double getPreAdjustRate()
	{
		return PreAdjustRate;
	}

	/**
	 * @return
	 */
	public double getPreBasicInterestRate()
	{
		return PreBasicInterestRate;
	}

	/**
	 * @return
	 */
	public double getPreStaidAdjustRate()
	{
		return PreStaidAdjustRate;
	}

	/**
	 * @param d
	 */
	public void setPreAdjustRate(double d)
	{
		PreAdjustRate = d;
	}

	/**
	 * @param d
	 */
	public void setPreBasicInterestRate(double d)
	{
		PreBasicInterestRate = d;
	}

	/**
	 * @param d
	 */
	public void setPreStaidAdjustRate(double d)
	{
		PreStaidAdjustRate = d;
	}

    /**
     * @return Returns the interestTypeID.
     */
    public long getInterestTypeID()
    {
        return InterestTypeID;
    }
    /**
     * @param interestTypeID The interestTypeID to set.
     */
    public void setInterestTypeID(long interestTypeID)
    {
        InterestTypeID = interestTypeID;
    }
    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return LiborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        LiborRateID = liborRateID;
    }
	/**
	 * Returns the currencyID.
	 * @return long
	 */
	public long getCurrencyID() {
		return CurrencyID;
	}

	/**
	 * Sets the currencyID.
	 * @param currencyID The currencyID to set
	 */
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}

	public InutParameterInfo getInutParameterInfo() {
		return inutParameterInfo;
	}

	public void setInutParameterInfo(InutParameterInfo inutParameterInfo) {
		this.inutParameterInfo = inutParameterInfo;
	}

	public String getLoanSubTypeName() {
		return loanSubTypeName;
	}

	public double getIntervalNoticeNum() {
		return IntervalNoticeNum;
	}

	public void setIntervalNoticeNum(double intervalNoticeNum) {
		IntervalNoticeNum = intervalNoticeNum;
	}

	public void setLoanSubTypeName(String loanSubTypeName) {
		this.loanSubTypeName = loanSubTypeName;
	}

	public String getBorrowClientName() {
		return borrowClientName;
	}

	public void setBorrowClientName(String borrowClientName) {
		this.borrowClientName = borrowClientName;
	}

	public Timestamp getInterestRateValidate() {
		return interestRateValidate;
	}

	public void setInterestRateValidate(Timestamp interestRateValidate) {
		this.interestRateValidate = interestRateValidate;
	}
	public String getLoanTypeName() {
		return loanTypeName;
	}
	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}
	public long getIsRemitCompoundInterest() {
		return isRemitCompoundInterest;
	}
	public void setIsRemitCompoundInterest(long isRemitCompoundInterest) {
		this.isRemitCompoundInterest = isRemitCompoundInterest;
	}
	public long getIsRemitOverDueInterest() {
		return isRemitOverDueInterest;
	}
	public void setIsRemitOverDueInterest(long isRemitOverDueInterest) {
		this.isRemitOverDueInterest = isRemitOverDueInterest;
	}
	public double getOverDueAdjustRate() {
		return overDueAdjustRate;
	}
	public void setOverDueAdjustRate(double overDueAdjustRate) {
		this.overDueAdjustRate = overDueAdjustRate;
	}
	public double getOverDueStaidAdjustRate() {
		return overDueStaidAdjustRate;
	}
	public void setOverDueStaidAdjustRate(double overDueStaidAdjustRate) {
		this.overDueStaidAdjustRate = overDueStaidAdjustRate;
	}
	public double getOverDueInterestRate() {
		return overDueInterestRate;
	}
	public void setOverDueInterestRate(double overDueInterestRate) {
		this.overDueInterestRate = overDueInterestRate;
	}

	

}
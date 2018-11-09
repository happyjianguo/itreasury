package com.iss.itreasury.loan.loanapply.dataentity;

/**
 * <p>Title: iTreasury </p> 
 * <p>Description: 贷款基本信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: gump
 * @version 1.0
 * @Date: 2003-09-25
 * 有下列字段被认为是基本信息
 * loanID           long    贷款的申请流水号
 * loanType         long    贷款种类
 * loanAmount       double  借款金额
 * loanReason       String  借款原因
 * loanPurpose      String  借款目的
 * other            String  其他，还款来源和措施
 * bankInterestID   long    银行利率
 * chargeRate       double  手续费率
 * intervalNum      long    期限
 * startDate        Timestamp 贷款开始日期
 * endDate          Timestamp 贷款结束日期
 * clientInfo       String    借款单位情况介绍
 * saleAmount       double    财务公司承贷额度
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;
import com.iss.itreasury.util.DataFormat;

public class LoanBasicInfo implements Serializable
{
	private long loanID = -1; //流水号
	private long loanType = -1; //贷款种类
	private double loanAmount = 0; //借款金额
	// private long    loanIntervalNum=0;     //借款期限
	private String loanReason = ""; //借款原因
	private String loanPurpose = ""; //借款目的
	private String other = ""; //其他，还款来源及措施,        
	private long bankInterestID = -1; //银行利率
	private double chargeRate = 0; //手续费率
	private long intervalNum = -1; //期限
	private Timestamp startDate; //贷款开始日期
	private Timestamp endDate; //贷款结束日期
	private String clientInfo = ""; //借款单位介绍
	private String projectInfo="";//项目简介
	private double saleAmount = 0; //财务公司承贷额度
	private double saleScale = 0; //财务公司承贷比例
	private BigDecimal mRate = null; //财务公司承贷比例
	private double interestRate = 0; //委托用贷款利率
	private double staidAdjustRate = 0; //固定浮动利率
	//担保
	private double assureChargeRate = 0; //担保费率
	private long assureChargeTypeID = -1;//担保费收取方式
	private String beneficiary = "";	 //受益人
	private long assureTypeID1 = -1;	 //担保类型1
	private long assureTypeID2 = -1;	 //担保类型2
	private double sellContractAmount = 0; //买方信贷合同金额
	private double selfRate = 0; //
	
	private long nInterestTypeID = 1;	//利率类型
	private long nLiborRateID = -1;		//Libor利率ID
	
	//	以下为融资租赁新增--add by yanliu 2006-03-29
	private long interestCountTypeID = -1;	//利息计算方式
	private double chargeAmount = 0.0;		//手续费金额
	private double recognizanceAmount = 0.0;//保证金金额
	private double matureNominalAmount = 0.0;//到期名义货价
	
	//以下为融资租赁新增--added by Xiong Fei 2010/05/17
	private double chargeAmountRate = 0.0;//手续费率
	private double origionAmount = 0.0;//租赁物原价
	private double preAmount = 0.0;//首付款
	
	/**
	 * 构造贷款基本信息。
	 */
	public LoanBasicInfo()
	{
		super();
	}
	/**
	 * 设置贷款申请的流水号
	 * @param loanID long,贷款申请的流水号
	 */
	public void setLoanID(long loanID)
	{
		this.loanID = loanID;
	}
	/**
	 * 获取贷款申请的流水号
	 * @return long 贷款申请的流水号
	 */
	public long getLoanID()
	{
		return loanID;
	}
	public void setLoanType(long loanType)
	{
		this.loanType = loanType;
	}
	public long getLoanType()
	{
		return this.loanType;
	}
	/**
	 * 设置贷款金额
	 * @param double loanAmount 贷款金额
	 */
	public void setLoanAmount(double loanAmount)
	{
		this.loanAmount = loanAmount;
	}
	/**
	 * 获取贷款金额
	 * @return double 贷款金额
	 */
	public double getLoanAmount()
	{
		return this.loanAmount;
	}

	/**
	 * 设置借款原因
	 * @param String loanReason 借款原因
	 */
	public void setLoanReason(String loanReason)
	{
		this.loanReason = loanReason;
	}
	/**
	 * 获取借款原因
	 * @return String 借款原因
	 */
	public String getLoanReason()
	{
		return this.loanReason;
	}

	/**
	 * 设置借款目的
	 * @param String loanPurpose 借款目的
	 */
	public void setLoanPurpose(String loanPurpose)
	{
		this.loanPurpose = loanPurpose;
	}
	/**
	 * 获取贷款目的
	 * @return String 借款目的
	 */
	public String getLoanPurpose()
	{
		return this.loanPurpose;
	}

	/**
	 * 设置环款来源和措施
	 * @param String other 还款来源和措施
	 */
	public void setOther(String other)
	{
		this.other = other;
	}
	/**
	 * 获得还款来源和措施
	 * @return String 还款来源和措施
	 */
	public String getOther()
	{
		return this.other;
	}
	public void setBankInterestID(long interestID)
	{
		this.bankInterestID = interestID;
	}
	public long getBankInterestID()
	{
		return this.bankInterestID;
	}

	public void setChargeRate(double cRate)
	{
		this.chargeRate = cRate;
	}
	public double getChargeRate()
	{
		return this.chargeRate;
	}
	public void setIntervalNum(long iNum)
	{
		this.intervalNum = iNum;
	}
	public long getIntervalNum()
	{
		return this.intervalNum;
	}
	public void setStartDate(Timestamp sDate)
	{
		this.startDate = sDate;
	}
	public Timestamp getStartDate()
	{
		return this.startDate;
	}
	public void setEndDate(Timestamp eDate)
	{
		this.endDate = eDate;
	}
	public Timestamp getEndDate()
	{
		return this.endDate;
	}
	public void setClientInfo(String cInfo)
	{
		this.clientInfo = cInfo;
	}
	public String getClientInfo()
	{
		return this.clientInfo;
	}
	public void setSaleAmount(double saleAmount)
	{
		this.saleAmount = saleAmount;
	}
	public double getSaleAmount()
	{
		return this.saleAmount;
	}
	public void setInterestRate(double irate)
	{
		this.interestRate = irate;
	}
	public double getInterestRate()
	{
		return this.interestRate;
	}
	public static void main(String[] args)
	{
	}
	/**
	 * function 得到/设置财务公司承贷比例
	 * return double
	 */
	public double getSaleScale()
	{
		return saleScale;
	}

	/**
	 * @param d
	 * function 得到/设置财务公司承贷比例
	 * return void
	 */
	public void setSaleScale(double d)
	{
		this.saleScale = d;
	}

	/**
	 * @param 
	 * function 得到/设置
	 * return double
	 */
	public double getStaidAdjustRate()
	{
		return staidAdjustRate;
	}

	/**
	 * @param 
	 * function 得到/设置
	 * return void
	 */
	public void setStaidAdjustRate(double d)
	{
		staidAdjustRate = d;
	}

	/**
	 * @return
	 */
	public BigDecimal getRate()
	{
		return mRate;
	}

	/**
	 * @param decimal
	 */
	public void setRate(BigDecimal decimal)
	{
		mRate = decimal;
	}

	public void setRate()
	{
		mRate = DataFormat.div(saleAmount*100,loanAmount,20);
	}
	/**
	 * @return
	 */
	public String getProjectInfo() {
		return projectInfo;
	}

	/**
	 * @param string
	 */
	public void setProjectInfo(String string) {
		projectInfo = string;
	}

    /**
     * @return Returns the assureChargeRate.
     */
    public double getAssureChargeRate()
    {
        return assureChargeRate;
    }
    /**
     * @param assureChargeRate The assureChargeRate to set.
     */
    public void setAssureChargeRate(double assureChargeRate)
    {
        this.assureChargeRate = assureChargeRate;
    }
    /**
     * @return Returns the assureChargeTypeID.
     */
    public long getAssureChargeTypeID()
    {
        return assureChargeTypeID;
    }
    /**
     * @param assureChargeTypeID The assureChargeTypeID to set.
     */
    public void setAssureChargeTypeID(long assureChargeTypeID)
    {
        this.assureChargeTypeID = assureChargeTypeID;
    }
    /**
     * @return Returns the assureTypeID1.
     */
    public long getAssureTypeID1()
    {
        return assureTypeID1;
    }
    /**
     * @param assureTypeID1 The assureTypeID1 to set.
     */
    public void setAssureTypeID1(long assureTypeID1)
    {
        this.assureTypeID1 = assureTypeID1;
    }
    /**
     * @return Returns the assureTypeID2.
     */
    public long getAssureTypeID2()
    {
        return assureTypeID2;
    }
    /**
     * @param assureTypeID2 The assureTypeID2 to set.
     */
    public void setAssureTypeID2(long assureTypeID2)
    {
        this.assureTypeID2 = assureTypeID2;
    }
    /**
     * @return Returns the beneficiary.
     */
    public String getBeneficiary()
    {
        return beneficiary;
    }
    /**
     * @param beneficiary The beneficiary to set.
     */
    public void setBeneficiary(String beneficiary)
    {
        this.beneficiary = beneficiary;
    }
	/**
	 * @return
	 */
	public double getSelfRate() {
		return selfRate;
	}

	/**
	 * @return
	 */
	public double getSellContractAmount() {
		return sellContractAmount;
	}

	/**
	 * @param d
	 */
	public void setSelfRate(double d) {
		selfRate = d;
	}

	/**
	 * @param d
	 */
	public void setSellContractAmount(double d) {
		sellContractAmount = d;
	}

    /**
     * @return Returns the interestTypeID.
     */
    public long getInterestTypeID()
    {
        return nInterestTypeID;
    }
    /**
     * @param interestTypeID The interestTypeID to set.
     */
    public void setInterestTypeID(long interestTypeID)
    {
        nInterestTypeID = interestTypeID;
    }
    /**
     * @return Returns the liborRateID.
     */
    public long getLiborRateID()
    {
        return nLiborRateID;
    }
    /**
     * @param liborRateID The liborRateID to set.
     */
    public void setLiborRateID(long liborRateID)
    {
        nLiborRateID = liborRateID;
    }
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public long getInterestCountTypeID() {
		return interestCountTypeID;
	}
	public void setInterestCountTypeID(long interestCountTypeID) {
		this.interestCountTypeID = interestCountTypeID;
	}
	public double getMatureNominalAmount() {
		return matureNominalAmount;
	}
	public void setMatureNominalAmount(double matureNominalAmount) {
		this.matureNominalAmount = matureNominalAmount;
	}
	public double getRecognizanceAmount() {
		return recognizanceAmount;
	}
	public void setRecognizanceAmount(double recognizanceAmount) {
		this.recognizanceAmount = recognizanceAmount;
	}
	public double getChargeAmountRate() {
		return chargeAmountRate;
	}
	public void setChargeAmountRate(double chargeAmountRate) {
		this.chargeAmountRate = chargeAmountRate;
	}
	public double getOrigionAmount() {
		return origionAmount;
	}
	public void setOrigionAmount(double origionAmount) {
		this.origionAmount = origionAmount;
	}
	public double getPreAmount() {
		return preAmount;
	}
	public void setPreAmount(double preAmount) {
		this.preAmount = preAmount;
	}
}

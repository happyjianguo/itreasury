/*
 * Created on 2003-11-20
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.resultinfo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yfsu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QuerySubConsignInfo implements Serializable
{
	private long ID=-1; //子账户号               
    private long AccountID = -1; // 
	private long AL_nLoanNoteID=-1;  //放款通知单
	private Timestamp AF_dtStart=null; //起始日期
	private Timestamp AF_dtEnd=null;  //到期日期
	private long nYear=-1;      //年期  截起始日期的前四位     
	private long nStatusID=-1;  //状态
	private double AF_mRate=0.0; //利率
	private long AL_nPayInterestAccountID=-1;  //贷款方付息账户
	private double AL_mCommissionRate=0.0;  //手续费率
	private double AL_mCommission=0.0;  //手续费
	private long AL_nCommissionAccountID=-1;  //支付手续费账户号
	private double AL_mInterestTaxRate=0.0;  //利息税费率                   
	private Timestamp AL_dtEffectiveTax=null;  //生效日期
	private long AL_nInterestTaxAccountID=-1;  //支付利息税费账户号
	private long ConsignAccountID=-1;  //委托存款账户号
	private long AL_nReceiveInterestAccountID=-1;  //委托方收息账户
	private long AL_nIsInterest=-1;  //   是否计息(checkBox)

	private double mBalance=0.0;  //当前余额
	private double AF_mPreDrawInterest=0.0;  //已计提利息
	private Timestamp dtOpen=null;  //开户日期
	private Timestamp dtOut=null;  //放款日期
	private Timestamp dtFinish=null;  //清户日期
	private double AC_mNegotiateInterest=0.0; //累计利息
	                      
    //下列字段从 loan_payform 中取得      LoanPayFormDetailInfo
	private String contractCode=""; //合同号   
	private String clientCode="";  //委托方
	//Added by zwsun, 2007/7/4 ,委托方名称
	private String clientName="";
	private long loanType=-1;  //贷款种类
	private long loanTerm=-1;  //贷款期限
	private double amount=0.0;  //贷款金额
	private double LoanInterestRate = 0.0;
	private long ChargeRateTypeID=-1; //手续费收取方式
	
	private double TotalRepaymentInterest = 0.0;//累计还息
	
	private long TypeID1 = -1;//地区分类
	private long TypeID2 = -1;//行业分类1
	private long TypeID3 = -1;//行业分类2
	
	/**
	 * @return
	 */
	public double getAC_mNegotiateInterest()
	{
		return AC_mNegotiateInterest;
	}

	/**
	 * @return
	 */
	public Timestamp getAF_dtEnd()
	{
		return AF_dtEnd;
	}

	/**
	 * @return
	 */
	public Timestamp getAF_dtStart()
	{
		return AF_dtStart;
	}

	/**
	 * @return
	 */
	public double getAF_mPreDrawInterest()
	{
		return AF_mPreDrawInterest;
	}

	/**
	 * @return
	 */
	public double getAF_mRate()
	{
		return AF_mRate;
	}

	/**
	 * @return
	 */
	public Timestamp getAL_dtEffectiveTax()
	{
		return AL_dtEffectiveTax;
	}

	/**
	 * @return
	 */
	public double getAL_mCommissionRate()
	{
		return AL_mCommissionRate;
	}

	/**
	 * @return
	 */
	public double getAL_mInterestTaxRate()
	{
		return AL_mInterestTaxRate;
	}

	/**
	 * @return
	 */
	public long getAL_nCommissionAccountID()
	{
		return AL_nCommissionAccountID;
	}

	/**
	 * @return
	 */
	public long getAL_nInterestTaxAccountID()
	{
		return AL_nInterestTaxAccountID;
	}

	/**
	 * @return
	 */
	public long getAL_nIsInterest()
	{
		return AL_nIsInterest;
	}

	/**
	 * @return
	 */
	public long getAL_nLoanNoteID()
	{
		return AL_nLoanNoteID;
	}

	/**
	 * @return
	 */
	public long getAL_nPayInterestAccountID()
	{
		return AL_nPayInterestAccountID;
	}

	/**
	 * @return
	 */
	public long getAL_nReceiveInterestAccountID()
	{
		return AL_nReceiveInterestAccountID;
	}

	/**
	 * @return
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * @return
	 */
	public String getClientCode()
	{
		return clientCode;
	}

	/**
	 * @return
	 */
	public long getConsignAccountID()
	{
		return ConsignAccountID;
	}

	/**
	 * @return
	 */
	public String getContractCode()
	{
		return contractCode;
	}

	/**
	 * @return
	 */
	public Timestamp getDtFinish()
	{
		return dtFinish;
	}

	/**
	 * @return
	 */
	public Timestamp getDtOpen()
	{
		return dtOpen;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * @return
	 */
	public long getLoanTerm()
	{
		return loanTerm;
	}

	/**
	 * @return
	 */
	public long getLoanType()
	{
		return loanType;
	}

	/**
	 * @return
	 */
	public double getMBalance()
	{
		return mBalance;
	}

	/**
	 * @return
	 */
	public long getNStatusID()
	{
		return nStatusID;
	}

	/**
	 * @return
	 */
	public long getNYear()
	{
		return nYear;
	}

	/**
	 * @param d
	 */
	public void setAC_mNegotiateInterest(double d)
	{
		AC_mNegotiateInterest = d;
	}

	/**
	 * @param timestamp
	 */
	public void setAF_dtEnd(Timestamp timestamp)
	{
		AF_dtEnd = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setAF_dtStart(Timestamp timestamp)
	{
		AF_dtStart = timestamp;
	}

	/**
	 * @param d
	 */
	public void setAF_mPreDrawInterest(double d)
	{
		AF_mPreDrawInterest = d;
	}

	/**
	 * @param d
	 */
	public void setAF_mRate(double d)
	{
		AF_mRate = d;
	}

	/**
	 * @param timestamp
	 */
	public void setAL_dtEffectiveTax(Timestamp timestamp)
	{
		AL_dtEffectiveTax = timestamp;
	}

	/**
	 * @param d
	 */
	public void setAL_mCommissionRate(double d)
	{
		AL_mCommissionRate = d;
	}

	/**
	 * @param d
	 */
	public void setAL_mInterestTaxRate(double d)
	{
		AL_mInterestTaxRate = d;
	}

	/**
	 * @param l
	 */
	public void setAL_nCommissionAccountID(long l)
	{
		AL_nCommissionAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setAL_nInterestTaxAccountID(long l)
	{
		AL_nInterestTaxAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setAL_nIsInterest(long d)
	{
		AL_nIsInterest = d;
	}

	/**
	 * @param l
	 */
	public void setAL_nLoanNoteID(long l)
	{
		AL_nLoanNoteID = l;
	}

	/**
	 * @param l
	 */
	public void setAL_nPayInterestAccountID(long l)
	{
		AL_nPayInterestAccountID = l;
	}

	/**
	 * @param l
	 */
	public void setAL_nReceiveInterestAccountID(long l)
	{
		AL_nReceiveInterestAccountID = l;
	}

	/**
	 * @param d
	 */
	public void setAmount(double d)
	{
		amount = d;
	}

	/**
	 * @param string
	 */
	public void setClientCode(String string)
	{
		clientCode = string;
	}

	/**
	 * @param l
	 */
	public void setConsignAccountID(long l)
	{
		ConsignAccountID = l;
	}

	/**
	 * @param string
	 */
	public void setContractCode(String string)
	{
		contractCode = string;
	}

	/**
	 * @param timestamp
	 */
	public void setDtFinish(Timestamp timestamp)
	{
		dtFinish = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setDtOpen(Timestamp timestamp)
	{
		dtOpen = timestamp;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		ID = l;
	}

	/**
	 * @param l
	 */
	public void setLoanTerm(long l)
	{
		loanTerm = l;
	}

	/**
	 * @param l
	 */
	public void setLoanType(long l)
	{
		loanType = l;
	}

	/**
	 * @param d
	 */
	public void setMBalance(double d)
	{
		mBalance = d;
	}

	/**
	 * @param l
	 */
	public void setNStatusID(long l)
	{
		nStatusID = l;
	}

	/**
	 * @param l
	 */
	public void setNYear(long l)
	{
		nYear = l;
	}

	/**
	 * @return
	 */
	public double getLoanInterestRate()
	{
		return LoanInterestRate;
	}

	/**
	 * @param loanInterestRate
	 */
	public void setLoanInterestRate(double loanInterestRate)
	{
		LoanInterestRate = loanInterestRate;
	}

	/**
	 * @return
	 */
	public double getAL_mCommission()
	{
		return AL_mCommission;
	}

	/**
	 * @param d
	 */
	public void setAL_mCommission(double d)
	{
		AL_mCommission = d;
	}

	/**
	 * @return
	 */
	public long getChargeRateTypeID()
	{
		return ChargeRateTypeID;
	}

	/**
	 * @param l
	 */
	public void setChargeRateTypeID(long l)
	{
		ChargeRateTypeID = l;
	}

	/**
	 * @return
	 */
	public long getAccountID()
	{
		return AccountID;
	}

	/**
	 * @param accountID
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
	}

	/**
	 * @return
	 */
	public Timestamp getDtOut()
	{
		return dtOut;
	}

	/**
	 * @param timestamp
	 */
	public void setDtOut(Timestamp timestamp)
	{
		dtOut = timestamp;
	}

	/**
	 * @return
	 */
	public double getTotalRepaymentInterest()
	{
		return TotalRepaymentInterest;
	}

	/**
	 * @param d
	 */
	public void setTotalRepaymentInterest(double d)
	{
		TotalRepaymentInterest = d;
	}

	/**
	 * @return
	 */
	public long getTypeID1()
	{
		return TypeID1;
	}

	/**
	 * @return
	 */
	public long getTypeID2()
	{
		return TypeID2;
	}

	/**
	 * @return
	 */
	public long getTypeID3()
	{
		return TypeID3;
	}

	/**
	 * @param l
	 */
	public void setTypeID1(long l)
	{
		TypeID1 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID2(long l)
	{
		TypeID2 = l;
	}

	/**
	 * @param l
	 */
	public void setTypeID3(long l)
	{
		TypeID3 = l;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}

package com.iss.itreasury.settlement.interest.bizlogic;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-29
 */
import java.sql.Connection;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestRate;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class InterestOperation
{

	private boolean isUseDatapool = false;
	private InterestCalculation interestCalculation = null;
	private Connection connection = null;
	/**
	 * 构造方法一：新建一个连接connection 或者从连接池中取出一个，或者利用JDBC新建一个连接！
	 * @param isUseDatapool
	 */
	public InterestOperation()
	{
		try
		{
			connection = Database.getConnection();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		interestCalculation = new InterestCalculation(connection);
	}
	/**
	 * 构造方法二：利用外部调用者传入的连接
	 * @param conn_Out
	 */
	public InterestOperation(Connection conn_Out)
	{
		this.connection = conn_Out;
		interestCalculation = new InterestCalculation(conn_Out);

	}

	/**
	 * 关闭连接
	 */
	public void closeConnection(){
		try{
			if(connection != null && connection.isClosed() == false) //added by mzh_fu 2008/02/22
				connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 功能说明：本算法中，到期支取和逾期支取中的定期利息按每月30天计算，提前支取和逾期支取中的活期利息按实际天数计算；日利率均按年利率除以360计算。
	 * @param subFixAccountID
	 * @param withdrawAmout
	 * @param withdrawDate
	 * @return
	 * @throws IException
	 */
	public InterestsInfo calculateFixedDepositAccountInterest(long subFixAccountID, double withdrawAmout, Timestamp withdrawDate) throws IException
	{
		return interestCalculation.calculateFixedDepositAccountInterest(subFixAccountID, withdrawAmout, withdrawDate);
	}
	/**
	 * 注 释：此方法为中交系统提前支取利率专用！
	 * 功能说明：功能说明：本算法中，到期支取和逾期支取中的定期利息按每月30天计算，提前支取和逾期支取中的活期利息按实际天数计算；日利率均按年利率除以360计算。
	 */
	public InterestsInfo calculateFixedDepositAccountInterest(long subFixAccountID, double withdrawAmout, Timestamp withdrawDate,double advanceRate ) throws IException
	{
		return interestCalculation.calculateFixedDepositAccountInterest(subFixAccountID, withdrawAmout, withdrawDate,advanceRate);
	}
	

	/**
	 * 功能说明：计算通知存款账户在一段时间内产生的各种利息。
	 * @param currencyID
	 * @param interestRate
	 * @param interestRateType
	 * @param interestBalance
	 * @param sDate
	 * @param eDate
	 * @return
	 * @throws IException
	 */
	public double calculateNoticeDepositAccountInterest(long currencyID, double interestRate, long interestRateType, double interestBalance, Timestamp sDate, Timestamp eDate) throws IException
	{
		return interestCalculation.calculateNoticeDepositAccountInterest(currencyID, interestRate, interestRateType, interestBalance, sDate, eDate);
	}

	/**
	 * 功能说明：计算活期存款账户在一段时间内产生的各种利息，含协定存款账户。
	 * @param subAccountID
	 * @param eDate
	 * @param execDate
	 * @return
	 * @throws IException
	 */

	public CurrentDepositAccountInterestInfo getCurrentDepositAccountInterest(long lOfficeID, long lCurrencyID, long subAccountID, Timestamp eDate, Timestamp execDate) throws IException
	{
		return interestCalculation.getCurrentDepositAccountInterest(this.connection, lOfficeID, lCurrencyID, subAccountID, eDate, execDate);
		//calculateCurrentDepositAccountInterest
	}

	/**
	 * 功能说明：计算定期存款账户的各种利息。
	 * @param accountID
	 * @param subFixAccountID
	 * @param interestDate
	 * @return
	 * @throws IException
	 */
	public FixedDepositAccountPayableInterestInfo getFixedDepositAccountPayableInterest(long accountID, long subFixAccountID, Timestamp interestDate) throws IException
	{
		System.out.println("liuhuijun debug info =================主账户是:" + accountID);
		System.out.println("liuhuijun debug info =============定期子账户是:" + subFixAccountID);
		System.out.println("liuhuijun debug info =================结息日是:" + interestDate);
		return interestCalculation.getFixedDepositAccountPayableInterest(accountID, subFixAccountID, interestDate);
	}
	
	/**
	 * 功能说明：计算定期和通知存款账户的各种利息。
	 * @param accountID
	 * @param subFixAccountID
	 * @param interestDate
	 * @return
	 * @throws IException
	 */
	public FixedDepositAccountPayableInterestInfo getNewDepositAccountInterest(long accountID, long subFixAccountID, Timestamp interestDate) throws IException
	{
		System.out.println("liuhuijun debug info =================通知和定期主账户是:" + accountID);
		System.out.println("liuhuijun debug info =================通知和定期子账户是:" + subFixAccountID);
		System.out.println("liuhuijun debug info =================通知和定期结息日是:" + interestDate);
		return interestCalculation.getNewDepositAccountInterest(accountID, subFixAccountID, interestDate);
	}
	
	/**
	 * 功能说明：计算贴现账户的各种利息。
	 * @param accountID
	 * @param subFixAccountID
	 * @param interestDate
	 * @return
	 * @throws IException
	 */
	public FixedDepositAccountPayableInterestInfo getDiscountAccountPayableInterest(long accountID, long subFixAccountID, Timestamp interestDate, double dInterestRate, Timestamp enddate, long contractID, long discreID) throws Exception
	{
		System.out.println("liuhuijun debug info =================贴现主账户是:" + accountID);
		System.out.println("liuhuijun debug info =================贴现子账户是:" + subFixAccountID);
		System.out.println("liuhuijun debug info =================贴现结息日是:" + interestDate);
		return interestCalculation.getDiscountAccountPayableInterest(accountID, subFixAccountID, interestDate, dInterestRate, enddate, contractID, discreID);
	}
	
	/**
	 * 功能说明：根据利率计划和日期取利率。
	 *Get Interest Rate By Interest Rate Plan
	 *@param ID of Interest Plan
	 *@param Balance of Interest
	 *@param Date of Validation
	 *@return value of interest
	 *
	*/
	public InterestRate getInterestRateByInterestRatePlan(long lOfficeID, long lCurrencyID, long interestRatePlanID, double interestBalance, Timestamp validDate) throws IException
	{
		return interestCalculation.getInterestRateByInterestRatePlan(lOfficeID, lCurrencyID, interestRatePlanID, interestBalance, validDate);
	}

	/**
	 * 功能说明：计算委托贷款账户的利息税费以及税后利息各种利息（只在结息时候才扣税）。
	 * @param accountID
	 * @param subLoanAccountID
	 * @param interestAmount
	 * @return
	 * @throws IException
	 */

	public InterestTaxInfo getInterestTax(long accountID, long subLoanAccountID, double interestAmount) throws IException
	{
		System.out.println(" 应缴税利息：" + interestAmount);
		return interestCalculation.getInterestTax(accountID, subLoanAccountID, interestAmount);
	}
	
	public InterestTaxInfo getInterestTaxByPlan(long accountID, long subLoanAccountID, double interestAmount) throws IException
	{
		System.out.println(" 应缴税利息：" + interestAmount);
		return interestCalculation.getInterestTaxByPlan(accountID, subLoanAccountID, interestAmount);
	}

	/**
	 * 功能说明：计算贷款账户的各种利息，不含贴现账户。
	 * @param accountID
	 * @param subLoanAccountID
	 * @param interestDate
	 * @param execDate
	 * @param interestType
	 * @return
	 * @throws IException
	 */
	public LoanAccountInterestInfo getLoanAccountFee(long lOfficeID, long lCurrencyID, long accountID, long subLoanAccountID, Timestamp interestDate, Timestamp execDate, long interestType)
		throws IException
	{
		//		System.out.println("liuhuijun debug info =================主账户是:" + accountID);
		//		System.out.println("liuhuijun debug info =============贷款子账户是:" +subLoanAccountID);
		//		System.out.println("liuhuijun debug info ===========输入的结息日是:" +interestDate);
		//		System.out.println("liuhuijun debug info ===========系统当前日期是:" + execDate);
		//		System.out.println("liuhuijun debug info ===============利率类型是:" +interestType);

		return interestCalculation.getLoanAccountFee(this.connection, lOfficeID, lCurrencyID, accountID, subLoanAccountID, interestDate, execDate, interestType);
	}

	/**
	 * 功能说明：调用中国国电贷款逾期复利罚息计算方法 Boxu 2008-10-06
	 * @param accountID
	 * @param subLoanAccountID
	 * @param interestDate
	 * @param execDate
	 * @param interestType
	 * @return
	 * @throws IException
	 */
	public LoanAccountInterestInfo getLoanGuoDianAccountFee(long lOfficeID, long lCurrencyID, long accountID, long subLoanAccountID, Timestamp interestDate, Timestamp execDate, long interestType)
		throws IException
	{
		return interestCalculation.getLoanGuoDianAccountFee(this.connection, lOfficeID, lCurrencyID, accountID, subLoanAccountID, interestDate, execDate, interestType);
	}
	
	/**
	 * 功能说明：计算贷款账户的各种利息，不含贴现账户。
	 * @param accountID
	 * @param subLoanAccountID
	 * @param interestDate
	 * @param exectDate
	 * @return
	 * @throws IException
	 */
	public LoanAccountInterestInfo GetLoanAccountInterest(long lOfficeID, long lCurrencyID, long accountID, long subLoanAccountID, Timestamp interestDate, Timestamp exectDate) throws IException
	{
		return interestCalculation.getLoanAccountInterest(this.connection, lOfficeID, lCurrencyID, accountID, subLoanAccountID, interestDate, exectDate);
	}
	
	public SubAccountPredrawInterestInfo getLoanAccountPredrawInterest(long AccountID, long SubAccountID, long AccountType, Timestamp clearDate) throws IException
	{
		return interestCalculation.getLoanAccountPredrawInterest(AccountID, SubAccountID, AccountType, clearDate);
	}
	public SubAccountPredrawInterestInfo getFixedAccountPredrawInterest(long AccountID, long SubAccountID, long AccountType, Timestamp clearDate) throws IException
	{
		return interestCalculation.getFixedAccountPredrawInterest(AccountID, SubAccountID, AccountType, clearDate);
	}

	/**
	 * 匡算/估算通知存款账户的利息。
	 * @param lOfficeID    办事处
	 * @param lCurrencyID  币种
	 * @param accountID    主账户
	 * @param subAccountID 子账户
	 * @param sDate        起息日
	 * @param clearDate    结息日
	 * @param interestRate 年利率
	 * @param execDate     执行日
	 * @param InterestFlag 结息标志（1：未结息部分，null 或 0 ：总积数）
	 * @return FixedDepositAccountPayableInterestInfo
	 * @throws IException
	 */
	public FixedDepositAccountPayableInterestInfo EstimateNoticeDepositAccountInterest(
		long lOfficeID,
		long lCurrencyID,
		long accountID,
		long subAccountID,
		Timestamp sDate,
		Timestamp clearDate,
		double interestRate,
		Timestamp execDate,
		long InterestFlag)
		throws IException
	{
		FixedDepositAccountPayableInterestInfo fixedDepositAccountPayableInterestInfo =
			interestCalculation.EstimateNoticeDepositAccountInterest(lOfficeID, lCurrencyID, accountID, subAccountID, sDate, clearDate, interestRate, execDate, InterestFlag);
		System.out.println("通知存款匡算后==起息日: " + fixedDepositAccountPayableInterestInfo.getSDate());
		System.out.println("通知存款匡算后==止息日: " + fixedDepositAccountPayableInterestInfo.getEDate());
		System.out.println("通知存款匡算后==余额是: " + fixedDepositAccountPayableInterestInfo.getBalance());
		System.out.println("通知存款匡算后==利率日: " + fixedDepositAccountPayableInterestInfo.getRate());
		System.out.println("通知存款匡算后==天数日: " + fixedDepositAccountPayableInterestInfo.getDays());
		System.out.println("通知存款匡算后==利息日: " + fixedDepositAccountPayableInterestInfo.getInterest());
		return fixedDepositAccountPayableInterestInfo;
	}
	/**
	 * 匡算/估算定期存款账户的利息。
	 * @param lOfficeID    办事处
	 * @param lCurrencyID  币种
	 * @param accountID    主账户
	 * @param subAccountID 子账户
	 * @param sDate        起息日
	 * @param clearDate    结息日
	 * @param interestRate 年利率
	 * @param execDate     执行日
	 * @param InterestFlag 结息标志（1：未结息部分，null 或 0 ：总积数）
	 * @return FixedDepositAccountPayableInterestInfo
	 * @throws IException
	 */
	public FixedDepositAccountPayableInterestInfo EstimateFixedDepositAccountInterest(
		long lOfficeID,
		long lCurrencyID,
		long accountID,
		long subAccountID,
		Timestamp sDate,
		Timestamp clearDate, 
		double interestRate,
		Timestamp execDate,
		long InterestFlag)
		throws IException
	{
		FixedDepositAccountPayableInterestInfo fixedDepositAccountPayableInterestInfo =
			interestCalculation.EstimateFixedDepositAccountInterest(lOfficeID, lCurrencyID, accountID, subAccountID, sDate, clearDate, interestRate, execDate, InterestFlag);
		System.out.println("定期存款匡算后==起息日: " + fixedDepositAccountPayableInterestInfo.getSDate());
		System.out.println("定期存款匡算后==止息日: " + fixedDepositAccountPayableInterestInfo.getEDate());
		System.out.println("定期存款匡算后==余额是: " + fixedDepositAccountPayableInterestInfo.getBalance());
		System.out.println("定期存款匡算后==利率日: " + fixedDepositAccountPayableInterestInfo.getRate());
		System.out.println("定期存款匡算后==天数日: " + fixedDepositAccountPayableInterestInfo.getDays());
		System.out.println("定期存款匡算后==利息日: " + fixedDepositAccountPayableInterestInfo.getInterest());
		return fixedDepositAccountPayableInterestInfo;
	}
}

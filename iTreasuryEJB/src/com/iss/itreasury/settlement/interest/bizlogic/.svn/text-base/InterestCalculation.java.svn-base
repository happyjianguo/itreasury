package com.iss.itreasury.settlement.interest.bizlogic;
/*
 * Created on 2003-10-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.discount.dataentity.DiscountBillInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.PayNoticeRateInfo;
import com.iss.itreasury.loan.overdueapply.dataentity.OverDueInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_AccountDAO;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountFixedInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.account.dataentity.TransAccountDetailInfo;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.settlement.interest.dao.Sett_DailyAccountBalanceDAO;
import com.iss.itreasury.settlement.interest.dao.Sett_TransInterestSettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.CurrentDepositAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.DailyAccountBalanceInfo;
import com.iss.itreasury.settlement.interest.dataentity.FixedDepositAccountPayableInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestCalculationModeQueryEntity;
import com.iss.itreasury.settlement.interest.dataentity.InterestCalculationParameterInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestRate;
import com.iss.itreasury.settlement.interest.dataentity.InterestRatePlanItemInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.setting.bizlogic.SettTaxRatePlanSettingBiz;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.settlement.setting.bizlogic.SubAccountTypeSettingBiz;
import com.iss.itreasury.settlement.setting.dao.Sett_InterestRateDAO;

public class InterestCalculation
{

	public final static long INTERVALDAYSFLAG_FACTDAY = 1;
	public final static long INTERVALDAYSFLAG_30TDAY = 2;

	private final static String ERRCODE_SQLEXCEPTION_RATE = "无法获取对应的银行利率，交易失败";
	private final static String ERRCODE_SQLEXCEPTION_SUBACCOUNT = "无法获取对应的子账户信息，交易失败";
	private final static String ERRCODE_SQLEXCEPTION_ACCOUNT = "无法获取对应的主账户信息，交易失败";

	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	private double CURRENT_INTEREST_RATE =  0.98; //目前是固定的值
	private Connection conn = null;
	/**
	 * constructor 1：the connection is translated by caller,and it is never null !!
	 * @param conn
	 */
	public InterestCalculation(Connection conn)
	{
		this.conn = conn;
		CURRENT_INTEREST_RATE=Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.00);
	}
	/**
	 * constructor no parameter
	 */
	public InterestCalculation()
	{
		CURRENT_INTEREST_RATE=Config.getDouble(ConfigConstant.CURRENCY_INTEREST_RATE,0.00);
	}

	public static void main(String[] args)
	{
		//		Connection con = null;
		//		try
		//		{
		//			con = Database.getConnection();
		//		}
		//		catch (Exception sqle)
		//		{
		//			sqle.printStackTrace();
		//		}		
		//		InterestCalculation ic = new InterestCalculation();
		//		long days;
		//		try {
		//			//Date sD = new Date();
		//			days =
		//				ic.getIntervalDays(
		//					new Timestamp(101, 1, 27, 0, 0, 0, 0),
		//					new Timestamp(101, 2,  1, 0, 0, 0,0),
		//					INTERVALDAYSFLAG_FACTDAY);
		//			log.info("间隔日期是:" + days);					
		//		} catch (IException e) {
		//			e.printStackTrace();
		//		}

	}
	/**
	 * @param sDate 	Date of Start 
	 * @param eDate 	Date of End
	 * @param intervalDaysFlag  flag for caculating interval: 
	 *                          1: caculating as fact days. 
	 *                          2: caculating as 30 days per month
	 * @exception IException   throw it while business exception occur and transaction need rollback
	 * @return  Interval Days
	*/
	public long getIntervalDays(Timestamp sDate, Timestamp eDate, long intervalDaysFlag) throws IException
	{
		long resIntervalDays = -1;
		
		log.print("起息日：" + sDate.toString());
		log.print("止息日：" + eDate.toString());
		
		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(sDate);
		
		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(eDate);

		if (sDate.after(eDate))
		{
			//throw new IException("起息日晚于止息日，交易失败");
		}

		//计算实际间隔日期
		if (intervalDaysFlag == SETTConstant.InterestCalculationMode.FACTDAY)
		{
			resIntervalDays = getFactIntervalDays(sCalendar, eCalendar);
		}
		//30天
		else if (intervalDaysFlag == SETTConstant.InterestCalculationMode.TDAY30)
		{
			//跨越的年份数
			int intervalYears = 0;
			//跨越的月份数
			int intervalMonths = 0;
			//多余的天数
			int intervalDays = 0;
			//起息日的天
			int sDay = 0;
			//止息日的天
			int eDay = 0;

			if (sCalendar.get(Calendar.DAY_OF_MONTH) != eCalendar.get(Calendar.DAY_OF_MONTH))
			{
				sDay = sCalendar.get(Calendar.DAY_OF_MONTH);
				eDay = eCalendar.get(Calendar.DAY_OF_MONTH);
				
				if (sCalendar.get(Calendar.DAY_OF_MONTH) > 30)
				{
					sDay = 30; 
				}

				if (eCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY && (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29))
				{
					if (sCalendar.get(Calendar.DAY_OF_MONTH) == 29 && eCalendar.get(Calendar.DAY_OF_MONTH) == 28)
					{
						eDay = 29;
					}
					else
					{
						eDay = 30;
					}
				}
				
				//多余的天数
				intervalDays = eDay - sDay;
			}
			
			//跨越的年份数
			intervalYears = eCalendar.get(Calendar.YEAR) - sCalendar.get(Calendar.YEAR);
			
			//跨越的月份数
			intervalMonths = eCalendar.get(Calendar.MONTH) - sCalendar.get(Calendar.MONTH);
			
			//按照每月30天计算的间隔总天数
			resIntervalDays = intervalYears * 360 + intervalMonths * 30 + intervalDays;

			//Boxu Add 这里的计算会有一个问题,如果时间不足一个月且跨月刚好是31的时候天数会少一天
			if(resIntervalDays < 30
			&& !(eCalendar.get(Calendar.MONTH) == Calendar.FEBRUARY && (eCalendar.get(Calendar.DAY_OF_MONTH) == 28 || eCalendar.get(Calendar.DAY_OF_MONTH) == 29)))
			{
				//按实际天数获得天数
				resIntervalDays = getFactIntervalDays(sCalendar, eCalendar);
			}
		}

		return resIntervalDays;
		
	}

	/**
	 *接口名称：根据利率计划取出活期利率
	 *功能描述：1：根据利率计划ID 和日期来取利率。
	 *           2：本算法只考虑按余额设置利率的情况。
	 *           3：本系统的结息系统利率均指年利率。
	 *@param conn 本接口的外部调用者传入的Connection 不可为空null!
	 *@param ID of Interest Plan 利率计划ID
	 *@param Balance of Interest 计息金额
	 *@param Date of Validation  生效日期
	 *@return value of interestRate 利率及利率类型 
	 *@throws IException
		*/
	public InterestRate getInterestRateByInterestRatePlan(long lOfficeID, long lCurrencyID, long interestRatePlanID, double interestBalance, Timestamp validDate) throws IException
	{
		InterestRate interestRate = null;

		Connection connection = this.conn;

		InterestRatePlanItemInfo irpiInfo;
		double rate = 0.0;
		//		try {
		//			//取银行利率ID和浮动利率
		//			sett_InterestRatePlanItemDAO irpiDAO = new sett_InterestRatePlanItemDAO(conn);
		//			irpiInfo = irpiDAO.getInfoByInterestPlanIDAndBalance(connection,interestRatePlanID,interestBalance);
		//			
		//			if(irpiInfo == null || irpiInfo.getID() == -1)
		//				throw new IException(true, ERRCODE_SQLEXCEPTION_RATE,null);
		//			//取银行利率值	
		//			sett_InterestRateDAO irDAO = new sett_InterestRateDAO(conn);
		//			rate = irDAO.getRateByValidDateAndRateID(connection,irpiInfo.getInterestRateID(),validDate);
		//		} catch (SQLException e) {
		//			throw new IException(true, ERRCODE_SQLEXCEPTION_RATE,e);
		//		} catch (IException e) {
		//			throw e;
		//		}
		//		//银行利率值+浮动利率 
		//		rate =  irpiInfo.getInterestRate() + rate;

		//     lhj 修改 调Utiloperation中的方法
		UtilOperation utilOperation = new UtilOperation();
		long lDays = -1;
		try
		{
			rate = utilOperation.getCurrentInterestRate(connection, lOfficeID, interestRatePlanID, interestBalance, validDate, lDays);
		}
		catch (Exception e)
		{
			log.info("取利率错误！");
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return new InterestRate(rate, SETTConstant.InterestRateTypeFlag.YEAR);
	}

	/**
	 * 接口名称：算息公式
	 * 功能描述：caculate interest at the condition that baseAmount and interestRate are not change
	 * 2003-11-16
	 * @param baseAmount 计息金额
	 * @param startInterestDate 起息日
	 * @param endInterestDate   结息日
	 * @param intervalDaysFlag  天数标志1：天2：每月30天
	 * @param interestRate      利率
	 * @param interestRateTypeFlag 利率类型 
	 * @param interestRateDaysFlag 年日利率转换基数标志
	 * @return 所计算出来的利息值
	 * @throws IException
	 */
	public double caculateInterest(
		double baseAmount,
		Timestamp startInterestDate,
		Timestamp endInterestDate,
		long intervalDaysFlag,
		double interestRate,
		long interestRateTypeFlag,
		long interestRateDaysFlag)
		throws IException
	{
		double interest = 0.0;
		
		System.out.println("----------开始计算定期利息------------中交"+startInterestDate);
		System.out.println("----------开始计算定期利息------------中交"+endInterestDate);
		System.out.println("----------开始计算定期利息------------中交"+intervalDaysFlag);
		
		long intervalDays = getIntervalDays(startInterestDate, endInterestDate, intervalDaysFlag);
		
		System.out.println("----------开始计算定期利息------------中交"+baseAmount);
		System.out.println("----------开始计算定期利息------------中交"+interestRate);
		System.out.println("----------开始计算定期利息------------中交"+intervalDays);
		
		if (interestRateTypeFlag == SETTConstant.InterestRateTypeFlag.YEAR)
		{
			//			//log.print("---------输入利率是年利率----------");

			if (interestRateDaysFlag == SETTConstant.InterestRateDaysFlag.DAYS_360)
				interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 36000);
			if (interestRateDaysFlag == SETTConstant.InterestRateDaysFlag.DAYS_365)
				interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 36500);
			if (interestRateDaysFlag == SETTConstant.InterestRateDaysFlag.DAYS_366)
				interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 36600);

		}
		else if (interestRateTypeFlag == SETTConstant.InterestRateTypeFlag.MONTH)
		{
			//			//log.print("---------输入利率是月利率----------");

			interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 30000);
		}
		else
		{
			//			//log.print("---------输入利率是日利率----------");

			interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 10000);
		}

		//log.print("------利息是:" + interest + "---------");
		////log.print("-------结束计算利息caculateInterest-----------");
		return interest;
	}

	/**
	 *	注：本算法中，到期支取和逾期支取中的定期利息按每月30天计算，提前支取和逾期支取中的活期利息按实际天数计算；日利率均按年利率除以360计算。
	 * @param subFixAccountID
	 * @param withdrawAmout
	 * @param withdrawDate
	 * @return InterestsInfo
	 * @throws IException
	 */
	public InterestsInfo calculateFixedDepositAccountInterest(long subFixAccountID, double withdrawAmout, Timestamp withdrawDate) throws IException
	{
		InterestsInfo interestInfo = new InterestsInfo();
		double CURRENT_RATE=-1;
		//活期利率读配置文件 add by zcwang
		if(Config.getDouble("currency_interest_rate",-1) != -1)
		{
			CURRENT_RATE = Double.valueOf(Config.getProperty("currency_interest_rate")).doubleValue();
		}
		else
		{
			CURRENT_RATE = CURRENT_INTEREST_RATE;
		}
		log.debug("----------开始计算定期利息------------");
		Sett_SubAccountDAO subAccountDAo = new Sett_SubAccountDAO(this.conn);
		SubAccountFixedInfo subAccountFixedInfo = null;
		try
		{
			subAccountFixedInfo = subAccountDAo.findByID(subFixAccountID).getSubAccountFixedInfo();
			if (subAccountFixedInfo == null)
			{
				throw new IException(true, ERRCODE_SQLEXCEPTION_SUBACCOUNT, null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException(true, ERRCODE_SQLEXCEPTION_SUBACCOUNT, e);
		}
		if (subAccountFixedInfo != null && subAccountFixedInfo.getID() > 0)
		{
			if (withdrawDate.before(subAccountFixedInfo.getEndDate()))
			{ //predraw
				log.debug("---------开始提前支取------------公共");
				interestInfo.setPreDrawInterest(0.0);
				interestInfo.setStrikePreDrawInterest(subAccountFixedInfo.getPreDrawInterest());
				interestInfo.setInterestPayment(0.0);
				double currentInterest =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getStartDate(),
						withdrawDate,
						INTERVALDAYSFLAG_FACTDAY,
						CURRENT_RATE,
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360);
				//round to second digital after decimal point
				currentInterest = UtilOperation.Arith.round(currentInterest, 2);
				interestInfo.setCurrentInterest(currentInterest);
				interestInfo.setTotalInterest(currentInterest);
				log.debug("---------结束提前支取------------");
			}
			else if (withdrawDate.equals(subAccountFixedInfo.getEndDate()))
			{ // draw at term
				log.debug("---------开始到期支取------------");
				interestInfo.setPreDrawInterest(UtilOperation.Arith.round(subAccountFixedInfo.getPreDrawInterest(), 2));
				interestInfo.setStrikePreDrawInterest(0.0);
				double totalInterest =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getStartDate(),
						subAccountFixedInfo.getEndDate(),
						INTERVALDAYSFLAG_30TDAY,
						subAccountFixedInfo.getRate(),
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360);
				totalInterest = UtilOperation.Arith.round(totalInterest, 2);
				interestInfo.setTotalInterest(totalInterest);
				interestInfo.setInterestPayment(UtilOperation.Arith.sub(totalInterest, interestInfo.getPreDrawInterest()));
				interestInfo.setCurrentInterest(0.0);
				log.debug("---------结束到期支取------------");
			}
			else
			{
				log.debug("---------开始逾期支取------------");
				interestInfo.setPreDrawInterest(UtilOperation.Arith.round(subAccountFixedInfo.getPreDrawInterest(), 2));
				interestInfo.setStrikePreDrawInterest(0.0);
				double currentInterest =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getEndDate(),
						withdrawDate,
						INTERVALDAYSFLAG_FACTDAY,
						CURRENT_RATE,
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360);
				currentInterest = UtilOperation.Arith.round(currentInterest, 2);
				interestInfo.setCurrentInterest(currentInterest);
				double interestPayment =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getStartDate(),
						subAccountFixedInfo.getEndDate(),
						INTERVALDAYSFLAG_30TDAY,
						subAccountFixedInfo.getRate(),
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360)
						- subAccountFixedInfo.getPreDrawInterest();

				interestPayment = UtilOperation.Arith.round(interestPayment, 2);
				interestInfo.setInterestPayment(interestPayment);
				double totalInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(subAccountFixedInfo.getPreDrawInterest(), currentInterest), interestPayment);
				interestInfo.setTotalInterest(UtilOperation.Arith.round(totalInterest, 2));
				log.debug("---------结束逾期支取------------");
			}
		}
		else
		{
			throw new IException(true, ERRCODE_SQLEXCEPTION_SUBACCOUNT, null);
		}

		log.debug("----------结束计算定期利息，利息为:------------");
		log.debug(UtilOperation.dataentityToString(interestInfo));

		return interestInfo;
	}
	
	/**
	 * 说 明： 中交项目需要 重载了上边的方法，多加了参数advanceRate
	 * @param subFixAccountID
	 * @param withdrawAmout
	 * @param withdrawDate
	 * @param advanceRate     客户填写的活期利率
	 * @return
	 * @throws IException
	 */
	
	public InterestsInfo calculateFixedDepositAccountInterest(long subFixAccountID, double withdrawAmout, Timestamp withdrawDate ,double advanceRate) throws IException
	{
		InterestsInfo interestInfo = new InterestsInfo();
		System.out.println("----------开始计算定期利息------------中交"+advanceRate);
		System.out.println("----------开始计算定期利息------------中交"+withdrawAmout);
		
		log.debug("----------开始计算定期利息------------中交");
		Sett_SubAccountDAO subAccountDAo = new Sett_SubAccountDAO(this.conn);
		SubAccountFixedInfo subAccountFixedInfo = null;
		try
		{
			subAccountFixedInfo = subAccountDAo.findByID(subFixAccountID).getSubAccountFixedInfo();
			if (subAccountFixedInfo == null)
			{
				throw new IException(true, ERRCODE_SQLEXCEPTION_SUBACCOUNT, null);
			}
		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException(true, ERRCODE_SQLEXCEPTION_SUBACCOUNT, e);
		}
		if (subAccountFixedInfo != null && subAccountFixedInfo.getID() > 0)
		{
			
			if (withdrawDate.before(subAccountFixedInfo.getEndDate()))
			{ //predraw
				log.debug("---------开始提前支取------------中交");
				interestInfo.setPreDrawInterest(0.0);
				interestInfo.setStrikePreDrawInterest(subAccountFixedInfo.getPreDrawInterest());
				interestInfo.setInterestPayment(0.0);
				if(advanceRate>=0.0){
					log.debug("------------------进入if 利率："+advanceRate+"------------------");
					double currentInterest =
						caculateInterest(
							withdrawAmout,
							subAccountFixedInfo.getStartDate(),
							withdrawDate,
							INTERVALDAYSFLAG_FACTDAY,
							advanceRate,
							SETTConstant.InterestRateTypeFlag.YEAR,
							SETTConstant.InterestRateDaysFlag.DAYS_360);
					//round to second digital after decimal point
					currentInterest = UtilOperation.Arith.round(currentInterest, 2);
					interestInfo.setCurrentInterest(currentInterest);
					interestInfo.setTotalInterest(currentInterest);
					log.debug("---------结束提前支取------------");
				}
				else{
					log.debug("------------------进入else 利率："+advanceRate+"------------------");
					double currentInterest =
						caculateInterest(
							withdrawAmout,
							subAccountFixedInfo.getStartDate(),
							withdrawDate,
							INTERVALDAYSFLAG_FACTDAY,
							CURRENT_INTEREST_RATE,
							SETTConstant.InterestRateTypeFlag.YEAR,
							SETTConstant.InterestRateDaysFlag.DAYS_360);
					//round to second digital after decimal point
					currentInterest = UtilOperation.Arith.round(currentInterest, 2);
					interestInfo.setCurrentInterest(currentInterest);
					interestInfo.setTotalInterest(currentInterest);
					log.debug("---------结束提前支取------------");
				}
				
			}
			else if (withdrawDate.equals(subAccountFixedInfo.getEndDate()))
			{ // draw at term
				log.debug("---------开始到期支取------------");
				interestInfo.setPreDrawInterest(UtilOperation.Arith.round(subAccountFixedInfo.getPreDrawInterest(), 2));
				interestInfo.setStrikePreDrawInterest(0.0);
				double totalInterest =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getStartDate(),
						subAccountFixedInfo.getEndDate(),
						INTERVALDAYSFLAG_30TDAY,
						subAccountFixedInfo.getRate(),
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360);
				totalInterest = UtilOperation.Arith.round(totalInterest, 2);
				interestInfo.setTotalInterest(totalInterest);
				interestInfo.setInterestPayment(UtilOperation.Arith.sub(totalInterest, interestInfo.getPreDrawInterest()));
				interestInfo.setCurrentInterest(0.0);
				log.debug("---------结束到期支取------------");
			}
			else
			{
				log.debug("---------开始逾期支取------------");
				interestInfo.setPreDrawInterest(UtilOperation.Arith.round(subAccountFixedInfo.getPreDrawInterest(), 2));
				interestInfo.setStrikePreDrawInterest(0.0);
				double currentInterest =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getEndDate(),
						withdrawDate,
						INTERVALDAYSFLAG_FACTDAY,
						advanceRate,
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360);
				currentInterest = UtilOperation.Arith.round(currentInterest, 2);
				interestInfo.setCurrentInterest(currentInterest);
				double interestPayment =
					caculateInterest(
						withdrawAmout,
						subAccountFixedInfo.getStartDate(),
						subAccountFixedInfo.getEndDate(),
						INTERVALDAYSFLAG_30TDAY,
						subAccountFixedInfo.getRate(),
						SETTConstant.InterestRateTypeFlag.YEAR,
						SETTConstant.InterestRateDaysFlag.DAYS_360)
						- subAccountFixedInfo.getPreDrawInterest();

				interestPayment = UtilOperation.Arith.round(interestPayment, 2);
				interestInfo.setInterestPayment(interestPayment);
				double totalInterest = UtilOperation.Arith.add(UtilOperation.Arith.add(subAccountFixedInfo.getPreDrawInterest(), currentInterest), interestPayment);
				interestInfo.setTotalInterest(UtilOperation.Arith.round(totalInterest, 2));
				log.debug("---------结束逾期支取------------");
			}
		}
		else
			throw new IException(true, ERRCODE_SQLEXCEPTION_SUBACCOUNT, null);

		log.debug("----------结束计算定期利息，利息为:------------");
		log.debug(UtilOperation.dataentityToString(interestInfo));

		return interestInfo;

	}
	
	

	/**
	 * 功能说明：计算通知存款账户在一段时间内产生的各种利息，主要应用于通知存款支取等交易。
	 * @param currencyID 币种
	 * @param interestRate 利率
	 * @param interestRateType 利率类型：1：年利率，2：月利率 3：日利率
	 * @param interestBalance  计息余额
	 * @param sDate  起息日
	 * @param eDate  结息日
	 * @return 产生的利息值
	 * @throws IException
	 */
	public double calculateNoticeDepositAccountInterest(long currencyID, double interestRate, long interestRateType, double interestBalance, Timestamp sDate, Timestamp eDate) throws IException
	{
		double noticeInterest = 0.0;

		noticeInterest = caculateInterest(interestBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, interestRate, interestRateType, SETTConstant.InterestRateDaysFlag.DAYS_360);
		return noticeInterest;
	}

	/**
	 * 功能说明：1：计算贷款账户在一段时间内产生的各种利息，不含贴现户。
	 *           2：主要应用于“应付利息”，“费用匡算”，“每日算息”等交易。
	 * @param currencyID 币种
	 * @param interestRate 利率
	 * @param interestRateType 利率类型：1：年利率，2：月利率 3：日利率
	 * @param interestBalance  计息余额
	 * @param sDate  起息日
	 * @param eDate  结息日
	 * @return 产生的利息值
	 * @throws IException
	 */
	public double calculateLoanAccountInterest(long currencyID, double interestRate, long interestRateType, double interestBalance, Timestamp sDate, Timestamp eDate) throws IException
	{
		double loanInterest = 0.0;

		long daysFlag = INTERVALDAYSFLAG_FACTDAY;
		long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;

		loanInterest = caculateInterest(interestBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, interestRate, interestRateType, SETTConstant.InterestRateDaysFlag.DAYS_360);

		return loanInterest;
	}



	/**
	 * 接口名称：计算某一时段活期账户利息（基本公式）
	 * 功能说明：1：计算活期存款账户在一段时间内发生的各种利息，含协定户。
	 *           2：本接口主要应用于应付利息和费用匡算、每日算息等交易。
	 *           3：调用本接口，要确定在起息日到结息日之间，计息金额、利率计划等要素不变化。
	 * Calculate Current Deposit Account Interest
	 *2003-11-10 param:negotiationRatePlanID 修改为:
	 * @param currencyID   币种
	 * @param interestRatePlanID  利率计划ID
	 * @param interestBalance   计息金额
	 * @param sDate   起息日
	 * @param eDate   结息日
	 * @param negotiationAmount 协定金额。0.0 means that it is not negoiation deposit
	 * @param negotiationUnit   协定存款单位（元）
	 * @param negotiationRate   协定存款利率计划ID
	 * @param IsNegotiate 是否协定存款
	 * @return  currentDepositAccountInterestInfo
	 * @throws IException
	 */

	public CurrentDepositAccountInterestInfo calculateCurrentDepositAccountInterest(
		long officeID,
		long currencyID,
		long interestRatePlanID,
		double interestBalance,
		Timestamp sDate,
		Timestamp eDate,
		double negotiationAmount,
		double negotiationUnit,
		double negotiationRate,
		long IsNegotiate)
		throws IException
	{
		CurrentDepositAccountInterestInfo resInfo = new CurrentDepositAccountInterestInfo();
		//eDate = getNextNDay(eDate, -1);
		//天数标志
		long daysFlag = INTERVALDAYSFLAG_FACTDAY;
		//利率标志
		long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;
		//正常利息
		double commonInterest = 0.0;
		//协定利息
		double negotiationInterest = 0.0;
		//double negotiationRate = 0.0;
		//协定余额
		double negotiationBalance = 0.0;
		//正常余额
		double commonBalance = 0.0;
		//正常利率
		double normalInterestRate = 0.0;

		//Modify by leiyang 2008-07-03 更改校验协定存款的方式
		//IsNegotiate 为 1 是为协定存款, 否则为活期
		if(IsNegotiate == 1)
		{
//			//是协定存款----------------------------------------------------------------------
//						log.info("lhj debug info ==========================================");
//						log.info("lhj debug info 计息余额是：" + interestBalance);
//						log.info("lhj debug info 协定数目是：" + negotiationAmount);
//			
//						log.info("lhj debug info ==========================================");
			if (interestBalance > negotiationAmount)
			{
				//协定部分余额	
				//Boxu Update 2008年3月16日 账户余额减去协定金额剩余部分除以协定单位取整数倍
				negotiationBalance = 
					UtilOperation.Arith.mul(
						Math.floor(
							UtilOperation.Arith.div(
									UtilOperation.Arith.sub(
											interestBalance, negotiationAmount), negotiationUnit)), negotiationUnit);
				//普通余额
				commonBalance = UtilOperation.Arith.sub(interestBalance, negotiationBalance);
			}
			else
			{
				//当前余额小于协定金额的协定存款
				negotiationBalance = 0;
				commonBalance = interestBalance;
			}

						log.info("lhj debug info =======协定计息余额是：" + negotiationBalance);
						log.info("lhj debug info =======普通计息金额是：" + commonBalance);

			//InterestRate negoiationIR = getInterestRateByInterestRatePlan(interestRatePlanID, negoiationBalance, eDate);
			//根据利率计划ID，普通金额取出活期利率

			//Modify by leiyang 2008-10-15
		    //修改关于活期利率计划生效日的问题
			//对eDate日期减1
			InterestRate commonIR = getInterestRateByInterestRatePlan(officeID, currencyID, interestRatePlanID, commonBalance, getNextNDay(eDate, -1));
			normalInterestRate = commonIR.getRate();

			commonInterest = caculateInterest(commonBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, commonIR.getRate(), commonIR.getType(), INTERVALDAYSFLAG_FACTDAY);
			//
			negotiationInterest = caculateInterest(negotiationBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, negotiationRate, SETTConstant.InterestRateTypeFlag.YEAR, INTERVALDAYSFLAG_FACTDAY);
						log.info("lhj debug info =======新的协定利息是：" + negotiationInterest);
						log.info("lhj debug info =======新的正常利息是：" + commonInterest);
		}
		else
		{
			//根据办事处，币种，利率计划ID取出活期存款利率.
			InterestRate commonInterestRate = getInterestRateByInterestRatePlan(officeID, currencyID, interestRatePlanID, interestBalance, getNextNDay(eDate, -1));
			//正常利率
			normalInterestRate = commonInterestRate.getRate();
			//计算利息 
			commonInterest = caculateInterest(interestBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, commonInterestRate.getRate(), commonInterestRate.getType(), INTERVALDAYSFLAG_FACTDAY);
			//正常余额
			commonBalance = interestBalance;
		}
		
		
		//间隔天数 lhj 2003-11-26 add  -------
		long intervalDays = this.getIntervalDays(sDate, eDate, INTERVALDAYSFLAG_FACTDAY);
		resInfo.setIntervalDays(intervalDays); //-------
		resInfo.setNegotiationBalance(negotiationBalance);
		resInfo.setNegotiationInterest(negotiationInterest);
		resInfo.setNormalBalance(commonBalance);
		resInfo.setNormalInterest(commonInterest);
		resInfo.setNormalInterestRate(normalInterestRate);
		resInfo.setNegotiationInterestRate(negotiationRate);

		//resInfo.setTermNegotiationInterest();
		//resInfo.setTermNormalInterest();

		return resInfo;
	}
	
	
	/**
	 * 接口名称：计算某一时段活期账户利息（基本公式）
	 * 功能说明：1：计算活期存款账户在一段时间内发生的各种利息，含协定户。
	 *           2：本接口主要应用于应付利息和费用匡算、每日算息等交易。
	 *           3：调用本接口，要确定在起息日到结息日之间，计息金额、利率计划等要素不变化。
	 * Calculate Current Deposit Account Interest
	 * Added by mzh_fu 2008/01/03 为解决利率变更,取不到利率的问题
	 * @param interestRate      利率
	 * @param interestBalance   计息金额
	 * @param sDate   起息日
	 * @param eDate   结息日
	 * @param negotiationAmount 协定金额。0.0 means that it is not negoiation deposit
	 * @param negotiationUnit   协定存款单位（元）
	 * @param negotiationRate   协定存款利率计划ID
	 * @param IsNegotiate 是否协定存款
	 * @return  currentDepositAccountInterestInfo
	 * @throws IException
	 */

	public CurrentDepositAccountInterestInfo calculateCurrentDepositAccountInterest(
		double interestRate,
		double interestBalance,
		Timestamp sDate,
		Timestamp eDate,
		double negotiationAmount,
		double negotiationUnit,
		double negotiationRate,
		long IsNegotiate)
		throws IException
	{
		CurrentDepositAccountInterestInfo resInfo = new CurrentDepositAccountInterestInfo();
		//eDate = getNextNDay(eDate, -1);
		//天数标志
		long daysFlag = INTERVALDAYSFLAG_FACTDAY;
		//利率标志
		long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;
		
		//利率类型
		long interestRateTypeFlag = SETTConstant.InterestRateTypeFlag.YEAR;
		
		//正常利息
		double commonInterest = 0.0;
		//协定利息
		double negotiationInterest = 0.0;
		//double negotiationRate = 0.0;
		//协定余额
		double negotiationBalance = 0.0;
		//正常余额
		double commonBalance = 0.0;
		//正常利率
		double normalInterestRate = 0.0;

		//Modify by leiyang 2008-07-03 更改校验协定存款的方式
		//IsNegotiate 为 1 是为协定存款, 否则为活期
		if(IsNegotiate == 1)
		{
//			//是协定存款----------------------------------------------------------------------
//						log.info("lhj debug info ==========================================");
//						log.info("lhj debug info 计息余额是：" + interestBalance);
//						log.info("lhj debug info 协定数目是：" + negotiationAmount);
//			
//						log.info("lhj debug info ==========================================");
			if (interestBalance > negotiationAmount)
			{
				//协定部分余额	
				negotiationBalance = UtilOperation.Arith.mul(UtilOperation.Arith.div(UtilOperation.Arith.sub(interestBalance, negotiationAmount), negotiationUnit), negotiationUnit);
				//普通余额
				commonBalance = UtilOperation.Arith.sub(interestBalance, negotiationBalance);
			}
			else
			{
				//当前余额小于协定金额的协定存款
				negotiationBalance = 0;
				commonBalance = interestBalance;
			}

						log.info("lhj debug info =======协定计息余额是：" + negotiationBalance);
						log.info("lhj debug info =======普通计息金额是：" + commonBalance);

			//InterestRate negoiationIR = getInterestRateByInterestRatePlan(interestRatePlanID, negoiationBalance, eDate);
			//根据利率计划ID，普通金额取出活期利率

			normalInterestRate = interestRate;

			commonInterest = caculateInterest(commonBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, interestRate, interestRateTypeFlag, INTERVALDAYSFLAG_FACTDAY);
			//
			negotiationInterest = caculateInterest(negotiationBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, negotiationRate, SETTConstant.InterestRateTypeFlag.YEAR, INTERVALDAYSFLAG_FACTDAY);
						log.info("lhj debug info =======新的协定利息是：" + negotiationInterest);
						log.info("lhj debug info =======新的正常利息是：" + commonInterest);
		}
		else
		{
			//正常利率
			normalInterestRate = interestRate;
			//计算利息 
			commonInterest = caculateInterest(interestBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, interestRate, interestRateTypeFlag, INTERVALDAYSFLAG_FACTDAY);
			//正常余额
			commonBalance = interestBalance;
		}
		
		
		//间隔天数 lhj 2003-11-26 add  -------
		long intervalDays = this.getIntervalDays(sDate, eDate, INTERVALDAYSFLAG_FACTDAY);
		resInfo.setIntervalDays(intervalDays); //-------
		resInfo.setNegotiationBalance(negotiationBalance);
		resInfo.setNegotiationInterest(negotiationInterest);
		resInfo.setNormalBalance(commonBalance);
		resInfo.setNormalInterest(commonInterest);
		resInfo.setNormalInterestRate(normalInterestRate);
		resInfo.setNegotiationInterestRate(negotiationRate);

		//resInfo.setTermNegotiationInterest();
		//resInfo.setTermNormalInterest();

		return resInfo;
	}
	
	
	/**
	 * 为日均余额结息法加入此方法。
	 * add by yanliu 20050714
	 * 接口名称：计算某一时段活期账户利息（基本公式）
	 * 功能说明：1：计算活期存款账户在一段时间内发生的各种利息，含协定户，利率计划为日均余额结息。
	 *           2：本接口主要应用于日均余额计息法的应付利息和费用匡算、每日算息等交易。
	 *           3：调用本接口，要确定在起息日到结息日之间，计息金额（日均）、利率计划等要素不变化。
	 * Calculate Current Deposit Account Interest
	 *2003-11-10 param:negotiationRatePlanID 修改为:
	 * @param currencyID   币种
	 * @param interestRatePlanID  利率计划ID
	 * @param avergeBalance   从上个结息日到关机日的日均余额
	 * @param interestBalance   计息总金额
	 * @param sDate   起息日
	 * @param eDate   结息日
	 * @param negotiationAmount 协定金额。0.0 means that it is not negoiation deposit
	 * @param negotiationUnit   协定存款单位（元）
	 * @param negotiationRate   协定存款利率计划ID
	 * @param IsNegotiate 是否协定存款
	 * @return  currentDepositAccountInterestInfo
	 * @throws IException
	 */

	public CurrentDepositAccountInterestInfo calculateCurrentDepositAccountInterestForAverageBalance (
		long officeID,
		long currencyID,
		long interestRatePlanID,
		double averageBalance,
		double interestBalance,
		Timestamp sDate,
		Timestamp eDate,
		double negotiationAmount,
		double negotiationUnit,
		double negotiationRate,
		long IsNegotiate)
		throws IException
	{
		CurrentDepositAccountInterestInfo resInfo = new CurrentDepositAccountInterestInfo();
		//eDate = getNextNDay(eDate, -1);
		//天数标志
		long daysFlag = INTERVALDAYSFLAG_FACTDAY;
		//利率标志
		long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;
		//正常利息
		double commonInterest = 0.0;
		//协定利息
		double negotiationInterest = 0.0;
		//double negotiationRate = 0.0;
		//协定余额
		double negotiationBalance = 0.0;
		//正常余额
		double commonBalance = 0.0;
		//正常利率
		double normalInterestRate = 0.0;

		
		//Modify by leiyang 2008-07-03 更改校验协定存款的方式
		//IsNegotiate 为 1 是为协定存款, 否则为活期
		if(IsNegotiate == 1)
		{
//			//是协定存款----------------------------------------------------------------------
//						log.info("lhj debug info ==========================================");
//						log.info("lhj debug info 计息余额是：" + interestBalance);
//						log.info("lhj debug info 协定数目是：" + negotiationAmount);
//			
//						log.info("lhj debug info ==========================================");
			if (interestBalance > negotiationAmount)
			{
				//协定部分余额	
				//negotiationBalance = UtilOperation.Arith.mul(UtilOperation.Arith.div(UtilOperation.Arith.sub(interestBalance, negotiationAmount), negotiationUnit), negotiationUnit);
				
				//协定部分余额	
				//Boxu Update 2008年3月16日 账户余额减去协定金额剩余部分除以协定单位取整数倍
				negotiationBalance = 
					UtilOperation.Arith.mul(
						Math.floor(
							UtilOperation.Arith.div(
									UtilOperation.Arith.sub(
											interestBalance, negotiationAmount), negotiationUnit)), negotiationUnit);
				
				//普通余额
				commonBalance = UtilOperation.Arith.sub(interestBalance, negotiationBalance);
			}
			else
			{
				//当前余额小于协定金额的协定存款
				negotiationBalance = 0;
				commonBalance = interestBalance;
			}

						log.info("lhj debug info =======协定计息余额是：" + negotiationBalance);
						log.info("lhj debug info =======普通计息金额是：" + commonBalance);

			//InterestRate negoiationIR = getInterestRateByInterestRatePlan(interestRatePlanID, negoiationBalance, eDate);
			//根据利率计划ID，普通金额取出活期利率

			InterestRate commonIR = getInterestRateByInterestRatePlan(officeID, currencyID, interestRatePlanID, commonBalance, eDate);
			normalInterestRate = commonIR.getRate();

			commonInterest = caculateInterest(commonBalance, DataFormat.getPreviousDate(eDate), eDate, INTERVALDAYSFLAG_FACTDAY, commonIR.getRate(), commonIR.getType(), INTERVALDAYSFLAG_FACTDAY);
			//
			negotiationInterest = caculateInterest(negotiationBalance, DataFormat.getPreviousDate(eDate), eDate, INTERVALDAYSFLAG_FACTDAY, negotiationRate, SETTConstant.InterestRateTypeFlag.YEAR, INTERVALDAYSFLAG_FACTDAY);
						log.info("lhj debug info =======新的协定利息是：" + negotiationInterest);
						log.info("lhj debug info =======新的正常利息是：" + commonInterest);
		}
		else
		{
			//根据办事处，币种，利率计划ID取出活期存款利率.
			InterestRate commonInterestRate = getInterestRateByInterestRatePlan(officeID, currencyID, interestRatePlanID, averageBalance, getNextNDay(eDate, -1));
			//正常利率
			normalInterestRate = commonInterestRate.getRate();
			//计算利息 
			commonInterest = caculateInterest(averageBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, commonInterestRate.getRate(), commonInterestRate.getType(), INTERVALDAYSFLAG_FACTDAY);
			//正常余额
			commonBalance = interestBalance;
		}
		
		//间隔天数 lhj 2003-11-26 add  -------
		long intervalDays = this.getIntervalDays(sDate, eDate, INTERVALDAYSFLAG_FACTDAY);
		resInfo.setIntervalDays(intervalDays); //-------
		resInfo.setNegotiationBalance(negotiationBalance);
		resInfo.setNegotiationInterest(negotiationInterest);
		resInfo.setNormalBalance(commonBalance);
		resInfo.setNormalInterest(commonInterest);
		resInfo.setNormalInterestRate(normalInterestRate);
		resInfo.setNegotiationInterestRate(negotiationRate);

		//resInfo.setTermNegotiationInterest();
		//resInfo.setTermNormalInterest();

		return resInfo;
	}
	
	
	/**
	 * 为工行联通项目加入此方法。
	 * add by Forest.20050128
	 * @throws IException
	 */

	public CurrentDepositAccountInterestInfo calculateCurrentDepositAccountInterestForICBC(
		long officeID,
		long currencyID,
		long interestRatePlanID,
		double interestBalance,
		Timestamp sDate,
		Timestamp eDate,
		double negotiationAmount,
		double negotiationUnit,
		double negotiationRate,
		long lSubAccountID,
		HashMap hmDepositBalance)
		throws IException
	{
		CurrentDepositAccountInterestInfo resInfo = new CurrentDepositAccountInterestInfo();
		//天数标志
		long daysFlag = INTERVALDAYSFLAG_FACTDAY;
		//利率标志
		long interestRateDaysFlag = SETTConstant.InterestRateDaysFlag.DAYS_360;
		//正常利息
		double commonInterest = 0.0;
		//协定利息
		double negotiationInterest = 0.0;
		//double negotiationRate = 0.0;
		//协定余额
		double negotiationBalance = 0.0;
		//正常余额
		double commonBalance = 0.0;
		//正常利率
		double normalInterestRate = 0.0;

		if (hmDepositBalance != null && hmDepositBalance.size() > 0)
		{
			if (hmDepositBalance.get(String.valueOf(lSubAccountID)) != null)
			{
				//协定部分余额	
				negotiationBalance = Double.parseDouble(String.valueOf(hmDepositBalance.get(String.valueOf(lSubAccountID))));
				//普通余额
				commonBalance = UtilOperation.Arith.sub(interestBalance, negotiationBalance);
			}
			else
			{
				//当前余额小于协定金额的协定存款
				negotiationBalance = 0;
				commonBalance = interestBalance;
			}
			InterestRate commonIR = getInterestRateByInterestRatePlan(officeID, currencyID, interestRatePlanID, commonBalance, eDate);
			normalInterestRate = commonIR.getRate();
			commonInterest = caculateInterest(commonBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, commonIR.getRate(), commonIR.getType(), INTERVALDAYSFLAG_FACTDAY);
			negotiationInterest = caculateInterest(negotiationBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, negotiationRate, SETTConstant.InterestRateTypeFlag.YEAR, INTERVALDAYSFLAG_FACTDAY);
		}
		else
		{
			//没有存款计息余额
			//根据办事处，币种，利率计划ID取出活期存款利率.
			InterestRate commonInterestRate = getInterestRateByInterestRatePlan(officeID, currencyID, interestRatePlanID, interestBalance, getNextNDay(eDate, -1));
			//正常利率
			normalInterestRate = commonInterestRate.getRate();
			//计算利息 
			commonInterest = caculateInterest(interestBalance, sDate, eDate, INTERVALDAYSFLAG_FACTDAY, commonInterestRate.getRate(), commonInterestRate.getType(), INTERVALDAYSFLAG_FACTDAY);
			//正常余额
			commonBalance = interestBalance;
		}

		//间隔天数 lhj 2003-11-26 add  -------
		long intervalDays = this.getIntervalDays(sDate, eDate, INTERVALDAYSFLAG_FACTDAY);
		resInfo.setIntervalDays(intervalDays); //-------
		resInfo.setNegotiationBalance(negotiationBalance);
		resInfo.setNegotiationInterest(negotiationInterest);
		resInfo.setNormalBalance(commonBalance);
		resInfo.setNormalInterest(commonInterest);
		resInfo.setNormalInterestRate(normalInterestRate);
		resInfo.setNegotiationInterestRate(negotiationRate);

		return resInfo;
	}
	
	/**
	 * 功能说明：1：计算活期存款账户的各种利息，含协定户。
	 *           2：主要应用于“利息费用计算设置”、“利息费用结算处理”、“应付利息”和“费用匡算”等交易。
	 *           3：结息日可以是任一时点，包括大于、等于或小于当前开机日期，但不能小于该账户的上次结息日。
	 * @param conn 调用者船入的数据库连接 不可为空！
	 * @param lOfficeID 办事处
	 * @param lCurrencyID 币种
	 * @param subAccountID 子账户ID
	 * @param InterestDate        输入的结息日
	 * @param execDate     系统的当前日期
	 * @return  CurrentDepositAccountInterestInfo
	 * @throws IException
	 */
	public CurrentDepositAccountInterestInfo getCurrentDepositAccountInterest(Connection conn, long lOfficeID, long lCurrencyID, long subAccountID, Timestamp InterestDate, Timestamp execDate)
		throws IException
	{
		CurrentDepositAccountInterestInfo resInfo = new CurrentDepositAccountInterestInfo();

		try
		{

			Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(this.conn);
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
			SubAccountCurrentInfo subAccInfo = null;
			AccountInfo accInfo = null;
			
			subAccInfo = sett_SubAccountDAO.findByID(subAccountID).getSubAccountCurrenctInfo();
			
			accInfo = sett_AccountDAO.findByID(subAccInfo.getAccountID());
			
			if (subAccInfo.getID() < 0 || accInfo.getAccountID() < 0)
			{
				throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
			}
			
			//止息日
			Timestamp eDate = this.getNextNDay(InterestDate, -1);
			
			//起息日
			// = subAccInfo.getClearInterestDate();
			
			//起息日（定期子账户的上次计提日期）
			//Timestamp sDate = subAccInfo.getPreDrawDate();
			
			//活期计提起息日修改为上次计提日期
			//Timestamp sPredrawDate = null;
			Timestamp sDate = null;
			if(subAccInfo.getPreDrawDate() != null)
			{
				sDate = subAccInfo.getPreDrawDate();
			}
			else
			{
				sDate = subAccInfo.getClearInterestDate();
			}
			
			if (sDate == null)
			{
				throw new IException(true, "无法找到子账户对应的结息日,交易失败", null);
			}

			//天数(活期按实际天数计算)
			long intervalDays = this.getIntervalDays(sDate, InterestDate, INTERVALDAYSFLAG_FACTDAY);
			
			if (intervalDays < 0)
			{
				throw new IException(true, "间隔日期小于或等于0天，无法计算平均值，交易失败", null);
			}
			
			//起息日	
			resInfo.setSDate(sDate);
			//止息日
			resInfo.setEDate(eDate);
			
			//resInfo.setDrawStartDate(sPredrawDate);
			//log.info("lhj debug info == 上一次结息日：" + sDate);
			//log.info("lhj debug info == 本次的止息日：" + eDate);
			//log.info("lhj debug info == 输入的结息日：" + InterestDate);			
			//log.info("lhj debug info == 系统当前日期：" + execDate);

			if (!InterestDate.after(execDate))
			{
				//查找以前的累计利息或计算当前累计利息
				log.info("lhj debug info == 输入的结息日早于系统当前日期...");
				
				Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
				//lhj modify 2003-11-26 sDate->endDate
				DailyAccountBalanceInfo balanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subAccountID, lOfficeID, lCurrencyID, eDate);
				
				if (balanceInfo != null)
				{
					resInfo.setNormalInterestRate(balanceInfo.getInterestRate());
					
					//正常余额
					//resInfo.setNormalBalance(balanceInfo.getBalance());
					//2008年3月7日 修改为取计息余额字段
					resInfo.setNormalBalance(balanceInfo.getInterestBalance());
					
					//协定余额
					resInfo.setNegotiationBalance(balanceInfo.getAc_mNegotiateBalance());
					
					resInfo.setIntervalDays(intervalDays);
					
					//活期从帐户余额表中获得了累计利息数据
					/*这个地方需要修改,在结息之后关机操作才会处理利息,所以利息取子账户表中的利息字段*/
					//resInfo.setNormalInterest(subAccInfo.getInterest());
					
					//2008年1月25日 取结息余额表累计利息字段
					resInfo.setNormalInterest(balanceInfo.getInterest());
					
					resInfo.setNegotiationInterestRate(balanceInfo.getAc_mNegotiateRate());
					
					/*这个地方需要修改,在结息之后关机操作才会处理利息,所以利息取子账户表中的协定利息字段*/
					//resInfo.setNegotiationInterest(subAccInfo.getNegotiateInterest());
					
					//2008年1月25日 最新修改取结息余额表
					resInfo.setNegotiationInterest(balanceInfo.getAc_mNegotiateInterest());
					
					//计提利息
					resInfo.setDrawInterest(subAccInfo.getDrawInterest());
					
					if(sDate.after(eDate))
					{
						//正常余额
						double sumNormalBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(subAccountID, eDate, eDate);
						
						if(sumNormalBalance == 0)
						{
							resInfo.setAverageNormalBalance(0);
						}
						else if(intervalDays == 0)
						{
							resInfo.setAverageNormalBalance(sumNormalBalance);
						}
						else
						{
							//平均正常余额
							resInfo.setAverageNormalBalance(UtilOperation.Arith.div(sumNormalBalance, intervalDays));
						}
						
						//协定余额
						double sumNegotiationBalance = dailyBalanceDAO.sumNegotiateBalanceBySubAccountIDAndDate(subAccountID, eDate, eDate);
						if(sumNegotiationBalance == 0)
						{
							resInfo.setAverageNegotiationBalance(0);
						}
						else if(intervalDays == 0)
						{
							resInfo.setAverageNegotiationBalance(sumNegotiationBalance);
						}
						else
						{
							//平均协定余额
							resInfo.setAverageNegotiationBalance(UtilOperation.Arith.div(sumNegotiationBalance, intervalDays));
						}
					}
					else
					{
						//正常余额
						double sumNormalBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(subAccountID, sDate, eDate);
						if(sumNormalBalance == 0)
						{
							resInfo.setAverageNormalBalance(0);
						}
						else if(intervalDays == 0)
						{
							resInfo.setAverageNormalBalance(sumNormalBalance);
						}
						else
						{
							//平均正常余额
							resInfo.setAverageNormalBalance(UtilOperation.Arith.div(sumNormalBalance, intervalDays));
						}
						
						//协定余额
						double sumNegotiationBalance = dailyBalanceDAO.sumNegotiateBalanceBySubAccountIDAndDate(subAccountID, sDate, eDate);
						if(sumNegotiationBalance == 0)
						{
							resInfo.setAverageNegotiationBalance(0);
						}
						else if(intervalDays == 0)
						{
							resInfo.setAverageNegotiationBalance(sumNegotiationBalance);
						}
						else
						{
							//平均协定余额
							resInfo.setAverageNegotiationBalance(UtilOperation.Arith.div(sumNegotiationBalance, intervalDays));
						}
					}

				}
			}
			
			else
			{
				//匡算将来的利息
				
				//log.info("lhj debug info == 输入的结息日晚于系统当前日期...");
				//Modify by leiyang 更改校验协定存款的方式
				//增加 IsNegotiate 参数，是否为协定存款
				CurrentDepositAccountInterestInfo furtureInterest = calculateCurrentDepositAccountInterest(
						lOfficeID,
						lCurrencyID,
						subAccInfo.getInterestRatePlanID(),
						subAccInfo.getBalance(),
						execDate,
						InterestDate,
						subAccInfo.getNegotiateAmount(),
						subAccInfo.getNegotiateUnit(),
						subAccInfo.getNegotiateRate(),
						subAccInfo.getIsNegotiate());

				resInfo.setNormalInterest(UtilOperation.Arith.add(furtureInterest.getNormalInterest(), subAccInfo.getInterest()));
				resInfo.setNegotiationInterest(UtilOperation.Arith.add(furtureInterest.getNegotiationInterest(), subAccInfo.getNegotiateInterest()));
				resInfo.setIntervalDays(intervalDays);
				resInfo.setNormalBalance(furtureInterest.getNormalBalance());
				resInfo.setNegotiationBalance(furtureInterest.getNegotiationBalance());
				resInfo.setNormalInterestRate(furtureInterest.getNormalInterestRate());
				resInfo.setNegotiationInterestRate(furtureInterest.getNegotiationInterestRate());

			}
		}
		catch (IException ie)
		{
			throw ie;
		}

		catch (SQLException e)
		{
			throw new IException(true, "子账户查询发生SQLException，交易失败", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "主账户查询发生SQLException，交易失败", null);
		}
		//log.info("活期结息后的各个属性----------------------------------------------");

		//log.info("活期结息后的正常余额---------" + resInfo.getNormalBalance());
		//log.info("活期结息后的正常利率---------" + resInfo.getNormalInterestRate());
		//log.info("活期结息后的正常利息---------" + resInfo.getNormalInterest());
		//log.info("活期结息后的协定余额---------" + resInfo.getNegotiationBalance());
		//log.info("活期结息后的协定利率---------" + resInfo.getNegotiationInterestRate());
		//log.info("活期结息后的协定利息---------" + resInfo.getNegotiationInterest());

		//log.info("活期结息后的各个属性----------------------------------------------");

		return resInfo;
	}

	/**
	 * 功能说明：1：不考虑提前、到期、逾期支取等情况，一律按定期存款账户利率计息。
	 *           2：主要应用于“定期存款计提应付利息”等交易。结息日可以是任一时点，包括大于、等于或小于当前开机日期，必须大于上一计提日。
	 * @param accountID     主账户ID
	 * @param subFixAccountID 贷款子账户ID
	 * @param interestDate   输入的结息日
	 * @return
	 * @throws IException
	 */
	public FixedDepositAccountPayableInterestInfo getFixedDepositAccountPayableInterest(long accountID, long subFixAccountID, Timestamp InterestDate) throws IException
	{
		FixedDepositAccountPayableInterestInfo resInfo = null;

		SubAccountFixedInfo subAccInfo = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
		try
		{
			//根据子账户ID，得到子账户信息
			//定期子账户实体类
			subAccInfo = sett_SubAccountDAO.findByID(subFixAccountID).getSubAccountFixedInfo();
			
			if (subAccInfo == null)
			{
				throw new IException(true, "子账户表中没有对应记录,交易失败", null);
			}
			
		}
		catch (IException ie)
		{
			throw ie;
		}
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		
		//子账户信息
		if ( subAccInfo != null && subAccInfo.getID() > -1 )
		{

			resInfo = new FixedDepositAccountPayableInterestInfo();
			
			//止息日	
			Timestamp eDate = null;
			Timestamp date1 = subAccInfo.getEndDate();
			
			log.info("----------------------------------结息日--------"+InterestDate);
			log.info("--------------日乐--------"+date1);
			
			if ( date1 == null )
			{
				log.info("到期日期是null!! 子账户 " + subFixAccountID + " 是通知存款账户！");
			}
				
			if ( subAccInfo.getEndDate() != null && subAccInfo.getEndDate().before(InterestDate) )
			{
				//定期子账户的到期日早于输入的结息日
				eDate = getNextNDay( subAccInfo.getEndDate(), -1 );
			}
			else
			{
				//定期子账户的到期日晚于输入的结息日
				eDate = getNextNDay( InterestDate, -1 );
			}

			//起息日（定期子账户的上次计提日期）
			Timestamp sDate = subAccInfo.getPreDrawDate();

			log.info("------------------------------------------"+eDate);
			
			//天数
			long days = getIntervalDays(sDate, getNextNDay(eDate, 1), INTERVALDAYSFLAG_FACTDAY);
			//log.info("lhj debug info == 起息日与止息日的间隔是:" + days);
			
			//余额
			double balance = subAccInfo.getBalance();
			
			//利率
			double rate = subAccInfo.getRate();
			
			//INTERVALDAYSFLAG_FACTDAY == 1
			//YEAR = 1;  //之下
			//DAYS_360 = 1;  // 按每年360天
			double interest = this.caculateInterest(
					balance
					, sDate
					, getNextNDay(eDate, 1)
					, INTERVALDAYSFLAG_FACTDAY
					, rate
					, SETTConstant.InterestRateTypeFlag.YEAR
					, SETTConstant.InterestRateDaysFlag.DAYS_360 );

			resInfo.setSDate(sDate);
			resInfo.setEDate(eDate);
			resInfo.setDays(days);
			resInfo.setBalance(balance);
			resInfo.setRate(rate);
			resInfo.setInterest(interest);

		}
		
		else
		{
			throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		
		return resInfo;
	}
	
	/**
	 * 功能说明：计算定期和通知存款账户的各种利息。
	 * 定期和通知算息调用计息方式接口
	 * @throws Exception 
	 */
	public FixedDepositAccountPayableInterestInfo getNewDepositAccountInterest(long accountID, long subFixAccountID, Timestamp InterestDate) throws IException
	{
		FixedDepositAccountPayableInterestInfo resInfo = null;
		long intervaldaysflag = -1;
		InterestCalculationModeQueryEntity InterestInfo = null;
		SubAccountFixedInfo subAccInfo = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
		SubAccountTypeSettingBiz subAccountTypeSettingBiz = new SubAccountTypeSettingBiz();
		try
		{
			//根据子账户ID，得到子账户信息
			//定期子账户实体类
			subAccInfo = sett_SubAccountDAO.findByID(subFixAccountID).getSubAccountFixedInfo();
			
			if (subAccInfo == null)
			{
				throw new IException(true, "子账户表中没有对应记录,交易失败", null);
			}
			
		}
		catch (IException ie)
		{
			throw ie;
		}
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		
		//子账户信息
		if ( subAccInfo != null && subAccInfo.getID() > -1 )
		{
			resInfo = new FixedDepositAccountPayableInterestInfo();
			
			/**
			 * 调用查询计息方式
			 */
			try
			{
				InterestInfo = new InterestCalculationModeQueryEntity();
				InterestInfo.setAccountId(accountID);
				InterestInfo.setSubAccountId(subAccInfo.getID());
				intervaldaysflag = subAccountTypeSettingBiz.getInterestCalculationMode(InterestInfo);
				
				if (intervaldaysflag == -1)
				{
					throw new IException(true, "计息方式未设置,交易失败", null);
				}
			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw new IException(true, "计息方式未设置,交易失败", null);
			}
			
			//止息日	
			Timestamp eDate = null;
			Timestamp date1 = subAccInfo.getEndDate();
			
			log.info("----------------------------------结息日--------"+InterestDate);
			log.info("--------------日乐--------"+date1);
			
			if ( date1 == null )
			{
				log.info("到期日期是null!! 子账户 " + subFixAccountID + " 是通知存款账户！");
			}
				
			if ( subAccInfo.getEndDate() != null && subAccInfo.getEndDate().before(InterestDate) )
			{
				//定期子账户的到期日早于输入的结息日
				eDate = getNextNDay( subAccInfo.getEndDate(), -1 );
			}
			else
			{
				//定期子账户的到期日晚于输入的结息日
				eDate = getNextNDay( InterestDate, -1 );
			}

			//起息日（定期子账户的上次计提日期）
			Timestamp sDate = null;
			if(subAccInfo.getPreDrawDate() != null)
			{
				sDate = subAccInfo.getPreDrawDate();
			}
			else
			{
				sDate = subAccInfo.getStartDate();
			}
			
			log.info("------------------------------------------"+eDate);
			
			/**
			 * 天数
			 * FACTDAY 按实际天数处理
			 * TDAY30 按30天处理
			 */
			long days = -1;
			if(intervaldaysflag == SETTConstant.InterestCalculationMode.FACTDAY)
			{
				days = getIntervalDays(sDate, getNextNDay(eDate, 1), SETTConstant.InterestCalculationMode.FACTDAY);
			}
			else if(intervaldaysflag == SETTConstant.InterestCalculationMode.TDAY30)
			{
				days = getIntervalDays(sDate, getNextNDay(eDate, 1), SETTConstant.InterestCalculationMode.TDAY30);
			}
			
			log.info("lhj debug info == 起息日与止息日的间隔是:" + days);
			
			//余额
			double balance = subAccInfo.getBalance();
			//利率
			double rate = subAccInfo.getRate();
			double interest = 0.0;
			
			if(intervaldaysflag == SETTConstant.InterestCalculationMode.FACTDAY)
			{
				interest = this.caculateInterest(
						  balance
						, sDate
						, getNextNDay(eDate, 1)
						, SETTConstant.InterestCalculationMode.FACTDAY
						, rate
						, SETTConstant.InterestRateTypeFlag.YEAR
						, SETTConstant.InterestRateDaysFlag.DAYS_360 );
			}
			else if(intervaldaysflag == SETTConstant.InterestCalculationMode.TDAY30)
			{
				interest = this.caculateInterest(
						  balance
						, sDate
						, getNextNDay(eDate, 1)
						, SETTConstant.InterestCalculationMode.TDAY30
						, rate
						, SETTConstant.InterestRateTypeFlag.YEAR
						, SETTConstant.InterestRateDaysFlag.DAYS_360 );
			}

			resInfo.setSDate(sDate);
			resInfo.setEDate(eDate);
			resInfo.setDays(days);
			resInfo.setBalance(balance);
			resInfo.setRate(rate);
			
			//显示为计算的利息减去计提的利息
			if (days == 0 || days == -1)
			{
				interest = 0.0;
			}
			
			resInfo.setInterest(interest);
			
			//保存计提利息在页面上显示
			resInfo.setDrawinterest(subAccInfo.getPreDrawInterest());
		}
		
		else
		{
			throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		
		return resInfo;
	}
	
	/**
	 * 功能说明：计算贴现利息账户的各种利息。
	 * 贴现算息调用计息方式接口
	 * @throws Exception 
	 */
	public FixedDepositAccountPayableInterestInfo getDiscountAccountPayableInterest(long accountID, long subFixAccountID, Timestamp InterestDate, double dInterestRate, Timestamp enddate, long contractID, long discreID) throws Exception
	{
		FixedDepositAccountPayableInterestInfo resInfo = null;
		SubAccountLoanInfo subAccInfo = null;
		InterestCalculationModeQueryEntity InterestInfo = null;
		long intervaldaysflag = -1;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
		SubAccountTypeSettingBiz subAccountTypeSettingBiz = new SubAccountTypeSettingBiz();
		try
		{
			//根据子账户ID，得到子账户信息
			//定期子账户实体类
			subAccInfo = sett_SubAccountDAO.findByID(subFixAccountID).getSubAccountLoanInfo();
			
			if (subAccInfo == null)
			{
				throw new IException(true, "子账户表中没有对应记录,交易失败", null);
			}
		}
		catch (IException ie)
		{
			throw ie;
		}
		
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		
		//子账户信息
		if ( subAccInfo != null && subAccInfo.getID() > -1 )
		{
			resInfo = new FixedDepositAccountPayableInterestInfo();
			
			/**
			 * 调用查询计息方式
			 */
			try
			{
				InterestInfo = new InterestCalculationModeQueryEntity();
				InterestInfo.setAccountId(accountID);
				InterestInfo.setSubAccountId(subAccInfo.getID());
				intervaldaysflag = subAccountTypeSettingBiz.getInterestCalculationMode(InterestInfo);
				
				if (intervaldaysflag == -1)
				{
					throw new IException(true, "贴现贷款计息方式未设置,交易失败", null);
				}
			}
			catch (IException ie)
			{
				ie.printStackTrace();
				throw new IException(true, "贴现贷款计息方式未设置,交易失败", null);
			}
			
			//止息日	
			Timestamp eDate = null;
			Timestamp date1 = enddate;
			
			log.info("----------------------------------结息日--------"+InterestDate);
			
			if ( date1 == null )
			{
				log.info("到期日期是null!! 子账户 " + subFixAccountID + " 是通知存款账户！");
			}
				
			if ( enddate != null && enddate.before(InterestDate) )
			{
				//贴现子账户的到期日早于输入的结息日
				eDate = getNextNDay( enddate, -1 );
			}
			else
			{
				//贴现子账户的到期日晚于输入的结息日
				eDate = getNextNDay( InterestDate, -1 );
			}

			//起息日（贴现子账户的上次计提日期）
			Timestamp sDate = subAccInfo.getPreDrawDate();

			log.info("------------------------------------------"+eDate);
			
			/**
			 * 天数
			 * FACTDAY 按实际天数处理
			 * TDAY30 按30天处理
			 */
			long days = -1;
			if(intervaldaysflag == SETTConstant.InterestCalculationMode.FACTDAY)
			{
				days = getIntervalDays(sDate, getNextNDay(eDate, 1), SETTConstant.InterestCalculationMode.FACTDAY);
			}
			else if(intervaldaysflag == SETTConstant.InterestCalculationMode.TDAY30)
			{
				days = getIntervalDays(sDate, getNextNDay(eDate, 1), SETTConstant.InterestCalculationMode.TDAY30);
			}
			
			log.info("起息日与止息日的间隔是:" + days);
			
			//余额
			//double balance = subAccInfo.getBalance();
			
			//利率
			//从贴现合同中得到的利率
			double rate = dInterestRate;
			
			Sett_TransInterestSettlementDAO dao = new Sett_TransInterestSettlementDAO();
			Vector discountVec = null;
			double interest = 0.0;
			double sumInterest = 0.0;
			double balance = 0.0;
			double sumBalance = 0.0;
			
			discountVec = dao.selectDiscountBillRecords(this.conn, contractID, discreID);
			
			if (discountVec != null && discountVec.size() > 0) 
			{
				log.info("-------开始处理利息查询数据--------");
				
				for (int i = 0; i < discountVec.size(); i++) 
				{
					DiscountBillInfo dbill = new DiscountBillInfo();
					dbill = (DiscountBillInfo) discountVec.elementAt(i);
					
					if (dbill.getEnd().after(InterestDate))
					{
						if(intervaldaysflag == SETTConstant.InterestCalculationMode.FACTDAY)
						{
							interest = this.caculateInterest(
									  dbill.getAmount()
									, sDate
									, getNextNDay(eDate, 1)
									, SETTConstant.InterestCalculationMode.FACTDAY
									, rate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
						}
						else if(intervaldaysflag == SETTConstant.InterestCalculationMode.TDAY30)
						{
							interest = this.caculateInterest(
									  dbill.getAmount()
									, sDate
									, getNextNDay(eDate, 1)
									, SETTConstant.InterestCalculationMode.TDAY30
									, rate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
						}
						
						balance = dbill.getAmount();
					}
					//票到期了但没有收回
					//else if(dbill.getEnd().before(InterestDate) && dbill.getNbillStatusId() != SETTConstant.TransactionStatus.CHECK)
					else if(dbill.getNbillStatusId() != SETTConstant.TransactionStatus.CHECK)
					{
						if(intervaldaysflag == SETTConstant.InterestCalculationMode.FACTDAY)
						{
							interest = this.caculateInterest(
									  dbill.getAmount()
									, sDate
									, dbill.getEnd()
									, SETTConstant.InterestCalculationMode.FACTDAY
									, rate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
						}
						else if(intervaldaysflag == SETTConstant.InterestCalculationMode.TDAY30)
						{
							interest = this.caculateInterest(
									  dbill.getAmount()
									, sDate
									, dbill.getEnd()
									, SETTConstant.InterestCalculationMode.TDAY30 
									, rate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
						}
						
						balance = dbill.getAmount();
					}
					
					sumInterest = UtilOperation.Arith.add(sumInterest, interest);
					sumBalance = UtilOperation.Arith.add(sumBalance, balance);
					interest = 0;
					balance = 0;
				}
			}

			resInfo.setSDate(sDate);
			resInfo.setEDate(eDate);
			resInfo.setDays(days);
			
			//不取子帐户中的余额字段,取没有到期票的总金额
			resInfo.setBalance(sumBalance);
			
			resInfo.setRate(rate);
			
			//取没有到期票的总利息
			resInfo.setInterest(sumInterest);
			
			//保存计提利息在页面上显示
			resInfo.setDrawinterest(subAccInfo.getPreDrawInterest());
		}
		
		else
		{
			throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		
		return resInfo;
	}
	
	/**
	 * 功能说明：1：计算贷款账户在一段时间内产生的各种利息。不含贴现账户。
	 *           2：主要应用于“自营贷款收回”，“委托贷款收回”，“多笔贷款收回”，“利息费用支付”，
	 *              “利息费用计算设置”，“利息费用结算处理”，“应付利息和费用匡算”等交易。
	 *           3：结息日可以是任意时点，包含大于、等于、小于当前开机日期，
	 *              但是不能小于该账户的上一次结息日。
	 * @param conn 外部调用者传入的数据库连接 不可为空！
	 * @param lOfficeID 办事处
	 * @param lCurrencyID 币种
	 * @param accountID 主账户ID
	 * @param subLoanAccountID 贷款子账户ID
	 * @param InterestDate 输入的结息日
	 * @param execDate  系统的当前日期     
	 * @return LoanAccountInterestInfo
	 * @throws IException
	 */
	public LoanAccountInterestInfo getLoanAccountInterest(Connection conn, long lOfficeID, long lCurrencyID, long accountID, long subLoanAccountID, Timestamp InterestDate, Timestamp execDate)
		throws IException
	{
		LoanAccountInterestInfo resInfo = new LoanAccountInterestInfo();
		SubAccountLoanInfo subAccInfo = null;
		AccountInfo accInfo = null;
		Timestamp sDate = null;
		Timestamp eDate = null;
		UtilOperation utilOperation = new UtilOperation(conn);
		long days = -1;
		try
		{

			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(conn);
			Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(conn);
			
			//主账户
			accInfo = sett_AccountDAO.findByID(accountID);
			
			//子账户
			subAccInfo = sett_SubAccountDAO.findByID(subLoanAccountID).getSubAccountLoanInfo();

			if (accInfo == null)
			{
				throw new IException(true, "没有对应主账户记录,交易失败", null);
			}
			if (subAccInfo == null)
			{
				throw new IException(true, "没有对应子账户记录,交易失败", null);
			}
			if (subAccInfo.getID() < 0 || accInfo.getAccountID() < 0)
			{
				throw new IException(true, "没有对应主账户或子账户记录,交易失败", null);
			}
			
			//止息日
			eDate = this.getNextNDay(InterestDate, -1);
			
			/**
			 * 修改日期，没有计提日期取结息日
			 */
			//起息日(上次结息日)
			if (subAccInfo.getPreDrawDate() != null)
			{
				sDate = subAccInfo.getPreDrawDate();
			}
			else
			{
				sDate = subAccInfo.getClearInterestDate();
			}
			
			if (sDate == null)
			{
				throw new IException(true, "账户起息日为null,数据有误,交易失败", null);
			}
			
			//天数
			days = getIntervalDays(sDate, InterestDate, INTERVALDAYSFLAG_FACTDAY);

		}
		catch (IException ie)
		{
			ie.printStackTrace();
			throw ie;
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException(true, "无法找到对应的子账户,交易失败", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法找到对应的主账户,交易失败", null);
		}
		
		log.debug("输入结息日： " + InterestDate);
		log.debug("上次结息日： " + sDate);
		log.debug("系统日期是： " + execDate);
		
		//判断该贷款账户是否逾期
		long loanNoteID = subAccInfo.getLoanNoteID();
		OverDueInfo overDueInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);
		
		if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
		{
			log.debug("〖 " + subLoanAccountID+" 〗该贷款子账户是逾期账户！");
			resInfo.setBalance(subAccInfo.getBalance());  //帐户当前余额
			resInfo.setInterest(subAccInfo.getInterest());  //当前利息
			resInfo.setRate(0.0);
		}
		else
		{
			log.debug("〖 " + subLoanAccountID +"〗 该贷款子账户正常账户，没有逾期！！！！");

			if (InterestDate.before(execDate))
			{
				//计算当前累计利息或查找以前的累计利息
				log.debug("lhj debug info == 输入的结息日早于系统日期，则查找账户余额表记录... ");
				
				Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
				DailyAccountBalanceInfo balanceInfo = null;
				
                /** added by mzh_fu 2008/03/22 解决关机后框算利息的问题，应用于“应付利息和费用匡算”功能 settlemetn/other/control/c201.jsp */
                try {
					if(EndDayProcess.getSystemStatusID (lOfficeID, lCurrencyID ) != Constant.SystemStatus.OPEN){
						eDate = InterestDate;
					}
				} catch (Exception e) {
					throw new IException(true, "应付利息和费用匡算错误", null);
				}
				
				try
				{					
					
					/**** 
					 * Boxu Add 2008年2月22日 解决问题:计提之后选择计提日之前的日期,计算出现负利息的问题
					 * *****/
					//if(!InterestDate.after(sDate))
					//{
						//如果输入的结息日小于等于上次结息日，则算出利息应为零！！！
					//	balanceInfo = new DailyAccountBalanceInfo();
					//}
					//else
					//{
						//获得账户余额表中对应时间的数据
					    balanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID, eDate);
					//}
					
					if (balanceInfo == null)
					{
						throw new IException(true, "账户余额表中没有对应记录，请检查起息日期，交易失败", null);
					}
				}
				catch (IException ie)
				{
					throw ie;
				}
				catch (SQLException e)
				{
					throw new IException(true, "发生SQLException，交易失败", null);
				}

				if (balanceInfo != null)
				{
					resInfo.setBalance(balanceInfo.getInterestBalance());  //帐户计息余额
					resInfo.setRate(balanceInfo.getInterestRate());  //执行利率/正常利率
					
					resInfo.setInterest(balanceInfo.getInterest());  //累计利息/累计正常利息
					
					log.debug("账户余额日结表中余额：" + balanceInfo.getBalance());
					log.debug("利率：" + balanceInfo.getInterestRate());
					log.debug("利息：" + balanceInfo.getInterest());
				}	
			}
			else
			{
				log.info("lhj debug info == 输入的结息日晚于等于系统日期，则需要计算利息..... ");
				
				//InterestRate interestRate = getInterestRateByPayForm(subAccInfo.getLoanNoteID(), InterestDate); //本方法还没有实现2003-11-10//已经实现..
				/*Modify by bingliu 20120615 匡算未来利息时，利率不取未来生效日期 */
				InterestRate interestRate = getInterestRateByPayForm(subAccInfo.getLoanNoteID(), execDate);
				
				double rate = 0.0;
				long rateType = -1;
				
				rate = interestRate.getRate();
				rateType = interestRate.getType();
				
				//算息公式, 按每年360天
				double interest = calculateLoanAccountInterest(accInfo.getCurrencyID(), rate, rateType, subAccInfo.getBalance(), execDate, InterestDate);
				
				//算息 + 子帐户利息(关机之后计算新利息后修改此字段)
				double loanInterest = UtilOperation.Arith.add(interest, subAccInfo.getInterest());
				
				resInfo.setBalance(subAccInfo.getBalance());
				resInfo.setRate(rate);
				resInfo.setInterest(loanInterest);
				
				log.debug("子账户余额：" + subAccInfo.getBalance());
				log.debug("利率：" + rate);
				log.debug("利息：" + loanInterest);
				log.debug("其中：  子账户利息：" + subAccInfo.getInterest());
				log.debug("未来产生利息：" + interest);
			}
		}
		
		resInfo.setSDate(sDate);
		resInfo.setEDate(eDate);
		resInfo.setDays(days);
		
		//保存计提利息
		resInfo.setDrawInterest(subAccInfo.getPreDrawInterest());
		
		return resInfo;

	}

	
	/**
	 * 功能说明：1：计算贷款账户在一段时间内产生的各种利息。不含贴现账户。
	 *           2：主要应用于“自营贷款收回”，“委托贷款收回”，“多笔贷款收回”，“利息费用支付”，
	 *              “利息费用计算设置”，“利息费用结算处理”，“应付利息和费用匡算”等交易。
	 *           3：结息日可以是任意时点，包含大于、等于、小于当前开机日期，
	 *              但是不能小于该账户的上一次结息日。
	 * @param accountID 主账户ID
	 * @param subLoanAccountID 贷款子账户ID
	 * @param interestDate 结息日
	 * @param exectDate    执行日
	 * @param interestType 费用类型3：担保费；2：手续费；4：复利；5：罚息。
	 * @return LoanAccountInterestInfo
	 * @throws IException
	 */
	public LoanAccountInterestInfo getLoanAccountFee(long lOfficeID, long lCurrencyID, long accountID, long subLoanAccountID, Timestamp interestDate, Timestamp execDate, long interestType)
		throws IException
	{
		Connection con = null;
		LoanAccountInterestInfo info = null;
		try
		{
			con = Database.getConnection();
			info = getLoanAccountFee(con, lOfficeID, lCurrencyID, accountID, subLoanAccountID, interestDate, execDate, interestType);
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			throw new IException("Gen_E001");
		}
		return info;
	}
	
	
	/**
	 * 功能说明：1：计算贷款账户在一段时间内产生的各种利息。不含贴现账户。
	 *           2：主要应用于“自营贷款收回”，“委托贷款收回”，“多笔贷款收回”，“利息费用支付”，
	 *              “利息费用计算设置”，“利息费用结算处理”，“应付利息和费用匡算”等交易。
	 *           3：结息日可以是任意时点，包含大于、等于、小于当前开机日期，
	 *              但是不能小于该账户的上一次结息日。
	 * @param accountID 主账户ID
	 * @param subLoanAccountID 贷款子账户ID
	 * @param interestDate 结息日
	 * @param exectDate    执行日
	 * @param interestType 费用类型3：担保费；2：手续费；4：复利；5：罚息。
	 * @return LoanAccountInterestInfo
	 * @throws IException
	 */
	public LoanAccountInterestInfo getLoanAccountFee(
		Connection conn,
		long lOfficeID,
		long lCurrencyID,
		long accountID,
		long subLoanAccountID,
		Timestamp interestDate,
		Timestamp execDate,
		long interestType)
		throws IException
	{
		LoanAccountInterestInfo resInfo = new LoanAccountInterestInfo();
		//log.info("lhj debug info =======在interestCalculation中的getLoanAccountFee方法中的interestType是：" + interestType);
		SubAccountLoanInfo subAccInfo = null;
		AccountInfo accInfo = null;
		AccountTypeInfo accountTypeInfo = null;
		LoanPayNoticeInfo loanPayNoticeInfo = null;
		DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
		Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO(conn);
		Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		UtilOperation uo = new UtilOperation(conn);
		//返回主账户和子账户----------------------------------------------------------start----
		try
		{
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
			Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(this.conn);
			accInfo = sett_AccountDAO.findByID(accountID);
			subAccInfo = sett_SubAccountDAO.findByID(subLoanAccountID).getSubAccountLoanInfo();
			
			accountTypeInfo = sett_AccountTypeDAO.findByID(accInfo.getAccountTypeID());

			if (accInfo == null || subAccInfo == null || subAccInfo.getID() < 0 || accInfo.getAccountID() < 0)
				throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
			//返回主账户和子账户----------------------------------------------------------end----
			//返回放款通知单记录----------------------------------------------------------start----
			log.info("lhj debug info --------------Sett_AccountID=" + accInfo.getAccountID());
			log.info("lhj debug info --------------Sett_SubAccountID=" + subAccInfo.getID());
			log.info("lhj debug info --------------LoanNoteID=" + subAccInfo.getLoanNoteID());
			loanPayNoticeInfo = getLoanPayNoticeByID(subAccInfo.getLoanNoteID()); //方法是空 2003-11-21
			//返回放款通知单记录----------------------------------------------------------end----
			if (loanPayNoticeInfo == null || loanPayNoticeInfo.getID() < 0)
			{
				throw new IException(true, "无法找到对应放款通知单记录,交易失败", null);
			}

			//返回interestDate的账户余额表记录

			if (interestDate.before(execDate))
			{
				dailyAccountBalanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID, interestDate);

				if (dailyAccountBalanceInfo == null)
				{
					throw new IException(true, "没有对应的账户余额表记录！，交易失败", null);
				}
			}

		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException(true, "无法找到对应的子账户,交易失败", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "无法找到对应的账户,交易失败", null);
		}

		//起息日
		Timestamp sDate = null;
		//天数
		long days = -1;
		//利率
		double rate = 0.0;
		//利息
		double interest = 0.0;
		//余额
		double balance = 0.0;
		//止息日
		Timestamp eDate = null;
		//已计积数
		double sumOfBalance = 0.0;
		
        //已计积数×利率
		double sumOfBalance_Rate = 0.0;
		if (interestType == SETTConstant.InterestFeeType.ASSURE)
		{
			//log.info("（interestCalculation 926）lhj debug info ===============进入ASSURE");
			//担保费起息日
			sDate = subAccInfo.getClearSureFeeDate();
			if (sDate != null)
			{
				//log.info("lhj debug info ==担保费起息日： " + sDate);
			}
			else
			{
				//log.info("lhj debug info ==担保费起息日是null!!!!!!!!!!!!!!!!!!!!!1! ");
				return resInfo;
			}
			//担保费止息日
			eDate = getNextNDay(interestDate, -1);
			//担保费结息天数
			days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
			//担保费利率
			rate = loanPayNoticeInfo.getSuretyFeeRate();
			//担保费已记积数
			try
			{
				sumOfBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
			}
			catch (SQLException e)
			{
				throw new IException(true, "积数查询发生SQLException，交易失败", null);
			}
			//log.info("lhj debug info ==担保费已计积数是 : " + sumOfBalance);
			//担保费已计利息
			double hasRecordInterest = UtilOperation.Arith.mul(sumOfBalance, UtilOperation.Arith.div(rate, 36000));
			if (interestDate.before(execDate))
			{ //是查找以前的累计利息或计算当前累计利息
				//log.info("lhj debug info == 贷款担保费 == 输入结息日小于等于系统当前日期!");
				interest = UtilOperation.Arith.add(hasRecordInterest, subAccInfo.getSuretyFee()); // 根据2..3-11-26 董春莉算法改动
				balance = dailyAccountBalanceInfo.getInterestBalance();
			}
			else
			{
				//log.info("lhj debug info == 贷款担保费 == 输入结息日大于系统当前日期!");
				balance = subAccInfo.getBalance();
				double furtureInterest = this.calculateLoanAccountInterest(accInfo.getCurrencyID(), rate, SETTConstant.InterestRateTypeFlag.YEAR, balance, execDate, interestDate);
				interest = UtilOperation.Arith.add(UtilOperation.Arith.add(hasRecordInterest, furtureInterest), subAccInfo.getSuretyFee()); // 根据2..3-11-26 董春莉算法改动
			}
		}
		else if (interestType == SETTConstant.InterestFeeType.COMMISION)
		{
			//log.info("（interestCalculation 1025）lhj debug info ===============进入COMMISION");
			//手续费起息日
			sDate = subAccInfo.getClearCommissionDate();
			if (sDate != null)
			{
				//log.info("lhj debug info ==手续费起息日是： " + sDate);
			}
			else
			{
				sDate = loanPayNoticeInfo.getStart();
				//log.info("lhj debug info ==手续费起息日是null!! ");
				//return resInfo;
			}
			//手续费利率
			rate = loanPayNoticeInfo.getCommissionRate();
			// 2003-11-30 lhj this method has achived ! //返回合同信息
			//log.info("lhj debug info == 手续费率是:" + rate);
			//log.info("lhj debug info == 合同号ID是:" + loanPayNoticeInfo.getContractID());
			ContractInfo contractInfo = getContractInfoByID(loanPayNoticeInfo.getContractID());
			if (contractInfo != null && contractInfo.getContractID() > -1)
			{

				if (contractInfo.getChargeRateType() == LOANConstant.ChargeRatePayType.ONETIME)
				{ //一次性收取手续费
					//log.info("lhj debug info == 贷款手续费 == 一次性收取手续费");

					sDate = loanPayNoticeInfo.getStart();
					//手续费止息日
					eDate = loanPayNoticeInfo.getEnd();
					//手续费结息天数
					days = getIntervalDays(sDate, eDate, INTERVALDAYSFLAG_FACTDAY);
					//-------------------------------------------------------------------------------------
					//余额
					balance = subAccInfo.getOpenAmount();
					if (subAccInfo.getCommission() > 0)
					{
						interest = subAccInfo.getCommission();
					}
					//-------------------------------------------------------------------------------------
				}
				else
				{
					//Modify by leiyang 2008/06/19 为国电委托贷款修改手续费算法
					/* 新方法暂不使用
					if (contractInfo.getLoanTypeID() == LOANConstant.LoanType.WT) {
						
							//手续费止息日
							eDate = getNextNDay(interestDate, -1);
							//手续费结息天数
							days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
							
							if (interestDate.before(execDate))
							{
								//输入结息日早于系统当前日期
								//计息余额
								balance = dailyAccountBalanceInfo.getInterestBalance();
								//手续费
								interest = UtilOperation.Arith.mul(balance, UtilOperation.Arith.div(rate, 100));
							}
							else
							{
								//输入结息日等于系统当前日期
								//子帐户余额
								balance = subAccInfo.getBalance();
								//手续费
								interest = UtilOperation.Arith.mul(balance, UtilOperation.Arith.div(rate, 100));
							}
							//按年支付手续费 每年只能结剩余手续费
							Timestamp tempOutDate = loanPayNoticeInfo.getOutDate();
							Timestamp temp = null;
							int year = 0;
							if(tempOutDate.getYear()==sDate.getYear())
							{
								temp = DataFormat.getNextYear(tempOutDate, 1);
							}
							else
							{
								temp = DataFormat.getDateTime(sDate.getYear(),tempOutDate.getMonth(),tempOutDate.getDate(),0,0,0);
							}
							if(temp.compareTo(interestDate)>=0)
							{
								interest = subAccInfo.getCommission();
							}
							else
							{
								interest = interest + subAccInfo.getCommission();
							}
							
					}
					*/
					//Modify by leiyang 2008/06/19 为国电委托贷款修改手续费算法
//					if (contractInfo.getLoanTypeID() == LOANConstant.LoanType.WT) {
//						//手续费止息日
//						eDate = getNextNDay(interestDate, -1);
//						//手续费结息天数
//						days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
//						
//						if (interestDate.before(execDate))
//						{
//							//输入结息日早于系统当前日期
//							//计息余额
//							balance = dailyAccountBalanceInfo.getInterestBalance();
//							//手续费
//							interest = UtilOperation.Arith.mul(balance, UtilOperation.Arith.div(rate, 100));
//						}
//						else
//						{
//							//输入结息日等于系统当前日期
//							//子帐户余额
//							balance = subAccInfo.getBalance();
//							//手续费
//							interest = UtilOperation.Arith.mul(balance, UtilOperation.Arith.div(rate, 100));
//						}
//					}
//					else {
						//log.info("lhj debug info == 贷款手续费 == 按季/年收取手续费");
						/*
						 * added by bingliu 2012-06-25
						 * 重新获取算手续费开始日期sDate的值，解决部分结手续费的问题。
						 * 例如：2012-01-01发放贷款，假设1天的手续费是100，则开机到2012-01-02时续费假设为100元，此时部分结手续费60元
						 * 再次关机开机到2012-01-03，此时从利息费用结算功能中计算手续费时算出40+200=240元，应该为40+100=140元。
						 * 原因是部分结手续费时不会去更新子账户中上次结手续费的日期。
						 * 修改方法：重新获取sDate，查询最近一次结手续费的交易日期，不论是部分还是全额
						 */
						
						Sett_TransInterestSettlementDAO sDao = new Sett_TransInterestSettlementDAO();
						try
						{
							Timestamp beforeCommissionDate = sDao.getMaxCommissionDate(null, subLoanAccountID);
							if(beforeCommissionDate != null && beforeCommissionDate.after(sDate))
							{//如果上次结手续费日之后有部分结手续费，则取最新日期
								sDate = beforeCommissionDate;
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							throw new IException(true, "取最近一次结手续费日期失败", null);
						}
					
						//手续费止息日
						eDate = getNextNDay(interestDate, -1);
						//手续费结息天数
						days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
						//手续费已计积数
						try
						{
							sumOfBalance = dailyBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
						}
						catch (SQLException e)
						{
							throw new IException(true, "积数查询发生SQLException，交易失败", null);
						}
						//log.info("lhj debug info ==手续费已计积数是 : " + sumOfBalance);
						//手续费已计利息
						//double hasRecordInterest = UtilOperation.Arith.mul(sumOfBalance, UtilOperation.Arith.div(rate, 36000));
						double hasRecordInterest = UtilOperation.Arith.div(UtilOperation.Arith.mul(sumOfBalance, rate), 36000);
						//log.info("lhj debug info ==手续费已计利息是 : " + hasRecordInterest);
						//
						if (interestDate.before(execDate))
						{
							//log.info("lhj debug info == 按季/年收取手续费==输入结息日早于系统当前日期");
	
							//余额
							balance = dailyAccountBalanceInfo.getInterestBalance();
							//log.info("lhj debug info ==手续费计息余额是 : " + balance);
							//log.info("lhj debug info = 子账户手续费是 : " + subAccInfo.getCommission());
							//利息
							interest = UtilOperation.Arith.add(subAccInfo.getCommission(), hasRecordInterest); // 根据2..3-11-26 董春莉算法改动
	
						}
						else
						{
							//log.info("lhj debug info == 按季/年收取手续费==输入结息日晚于系统当前日期");
							//余额
							balance = subAccInfo.getBalance();
							double futureInterest = calculateLoanAccountInterest(lCurrencyID, rate, 1, balance, execDate, interestDate);
							interest = UtilOperation.Arith.add(UtilOperation.Arith.add(subAccInfo.getCommission(), hasRecordInterest), futureInterest); // 根据2..3-11-26 董春莉算法改动					
							//log.info("lhj debug info == 原来手续费" + subAccInfo.getCommission());
							//log.info("lhj debug info == 已计手续费" + hasRecordInterest);
							//log.info("lhj debug info == 未来手续费" + futureInterest);
	
						}
					//}
				}
			}
		}
		else if (interestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
		{ //计算复利
			log.info("（interestCalculation 987）lhj debug info ===============进入COMPOUNDINTEREST");
			InterestCalculationParameterInfo  ParameterInfo = new InterestCalculationParameterInfo();
			ParameterInfo.setLoanNoteID(loanPayNoticeInfo.getID());
			ParameterInfo.setValidDate(interestDate);
			InterestRate rateInfo = this.getCompoundInterestRate(ParameterInfo);
			rate = rateInfo.getRate();
			if (interestDate.before(execDate))
			{ //查找dailyaccountblance
				DailyAccountBalanceInfo predailyAccountBalanceInfo = null;
				try {
					predailyAccountBalanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID,getNextNDay( interestDate,-1));
				} catch (Exception e) {
					throw new IException(e.getMessage());
				}
				log.debug("lhj debug info == 贷款复利结息==输入结息日早于系统当前日期");
				interest = predailyAccountBalanceInfo.getMcompoundinterest(); 
				balance = predailyAccountBalanceInfo.getAl_mArrearageInterest();
				log.debug("lhj debug info == 累计欠息余额  ：" + balance);
				log.debug("lhj debug info == 计算复利      ：" + interest);
			}
			else
			{
				log.debug("lhj debug info == 贷款复利结息==输入结息日晚于等于系统当前日期");
				interest = subAccInfo.getCompoundInterest();
				balance = subAccInfo.getArrearageInterest();
				double furtureInterest = calculateLoanAccountInterest(lCurrencyID, rate, 1, balance, execDate, interestDate);
				interest = UtilOperation.Arith.add(furtureInterest,interest);
				log.debug("lhj debug info == 累计欠息余额  ：" + balance);
				log.debug("lhj debug info == 计算复利      ：" + interest);
			}
			//复利起息日
			//注意！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
			//2003-12-02 如果子账户中的复利结算日期是null ,那么取放款通知单loan_PayForm中的贷款起始日期dtStart
			if (subAccInfo.getClearCompoundDate() != null)
			{
				Timestamp time = uo.getStartCompoundinterestDay(subAccInfo.getID());
				if(time!=null&&!(subAccInfo.getClearCompoundDate().before(time)&&interestDate.before(time)))
				{
					sDate = (time);
				}
				else
				{
					sDate = (subAccInfo.getClearCompoundDate());
				}
				log.info("取得是：  == 子账户复利结息日期是： " + subAccInfo.getClearCompoundDate());
			}
			else
			{
				log.info("取得是： == 放款通知单贷款开始日期是： " + loanPayNoticeInfo.getStart());
				sDate = loanPayNoticeInfo.getStart();
			}
			
			//复利止息日
			eDate = getNextNDay(interestDate, -1);
			log.info("复利起息日 : " + sDate);
			log.info("复利止息日 : " + eDate);
			//天数
			days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
//			//余额
//
//			//InterestRate interestRate = getInterestRateByInterestRatePlan(lOfficeID, lCurrencyID, subAccInfo.getLoanNoteID(), balance, eDate);
//			InterestRate interestRate = this.getInterestRateByPayForm(subAccInfo.getLoanNoteID(), eDate);
//			rate = interestRate.getRate();
//			//已计复利积数
//			double hasRecordInterest = 0.0;
//			try
//			{
//				//sumOfBalance_Rate = dailyBalanceDAO.sumArrearageInterestBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
//				hasRecordInterest =  dailyBalanceDAO.sumArrearageInterestBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
//			}
//			catch (SQLException e)
//			{
//				throw new IException(true, "积数查询发生SQLException，交易失败", null);
//			}
//			log.debug("lhj debug info ==复利已计积数是 : " + hasRecordInterest);
//			//double hasRecordInterest = UtilOperation.Arith.mul(sumOfBalance_Rate, rate / 36000);
//			if (interestDate.before(execDate))
//			{ //查找以前或当前的累计利息
//				log.debug("lhj debug info == 贷款复利结息==输入结息日早于系统当前日期");
//				interest = UtilOperation.Arith.add(hasRecordInterest, subAccInfo.getCompoundInterest()); // 根据2..3-11-26 董春莉算法改动
//				balance = dailyAccountBalanceInfo.getAl_mArrearageInterest();
//				log.debug("lhj debug info == 累计欠息余额  ：" + balance);
//				log.debug("lhj debug info == 复利已计利息  ：" + hasRecordInterest);
//				log.debug("lhj debug info == 子账户原来复利：" + subAccInfo.getCompoundInterest());
//				log.debug("lhj debug info == 计算复利      ：" + interest);
//			}
//			else
//			{
//				log.debug("lhj debug info == 贷款复利结息==输入结息日晚于等于系统当前日期");
//				balance = subAccInfo.getArrearageInterest();
//				double furtureInterest = calculateLoanAccountInterest(lCurrencyID, rate, 1, balance, execDate, interestDate);
//				interest = UtilOperation.Arith.add(UtilOperation.Arith.add(hasRecordInterest, furtureInterest), subAccInfo.getCompoundInterest()); // 根据2..3-11-26 董春莉算法改动
//
//				log.debug("lhj debug info == 累计欠息余额  ：" + balance);
//				log.debug("lhj debug info == 复利已计利息  ：" + hasRecordInterest);
//				log.debug("lhj debug info == 未来复利利息  ：" + furtureInterest);
//				log.debug("lhj debug info == 子账户原来复利：" + subAccInfo.getCompoundInterest());
//				log.debug("lhj debug info == 计算复利      ：" + interest);
//			}
		}
		else if (interestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
		{
			
			//modify by leiyang3
			//2010/11/28
			//用于计算自营贷款到期后的罚息
			if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST)
			{				
				Timestamp loadEndDate  = loanPayNoticeInfo.getEnd();
				//loanPayNoticeInfo
				
				//-- 罚息计算开始  --
				//是否进行罚息
				if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE) //通过放款单是否时行罚息属性，判断是否进行罚息
				{
	
					//判断贷款到期日是滞在结息日之后，从而判断是否进行罚息计算
					if(!loadEndDate.after(interestDate))
					{
						//罚息利率
						InterestCalculationParameterInfo paramInfo = new InterestCalculationParameterInfo();
						paramInfo.setLoanNoteID(loanPayNoticeInfo.getID());
						paramInfo.setValidDate(interestDate);
						InterestRate overDueRate = this.getOverDueInterestRate(paramInfo);
						DailyAccountBalanceInfo prevDailyAccountBalanceInfo = null;
						
						try
						{
							prevDailyAccountBalanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID, getNextNDay(interestDate, -1));
						}
						catch (SQLException e)
						{
							e.printStackTrace();
							throw new IException("Gen_E001");
						}

						if(interestDate.before(execDate))
						{
							interest = prevDailyAccountBalanceInfo.getMforfeitinterest();
							balance = prevDailyAccountBalanceInfo.getInterestBalance();
							
							//interest = dailyAccountBalanceInfo.getMforfeitinterest();
							//balance = dailyAccountBalanceInfo.getInterestBalance();
						}
						else
						{
							double furtureInterest = calculateLoanAccountInterest(lCurrencyID, overDueRate.getRate(), overDueRate.getType(), balance, execDate, interestDate);
							interest = UtilOperation.Arith.add(prevDailyAccountBalanceInfo.getMforfeitinterest(), furtureInterest);
							balance = subAccInfo.getBalance();
						}
						
						//罚息起息日
						sDate = loadEndDate;
						//罚息止息日
						eDate = getNextNDay(interestDate, -1);
						log.info("复利起息日 : " + sDate);
						log.info("复利止息日 : " + eDate);
						//天数
						days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
						rate = overDueRate.getRate();

					}
					else
					{
						interest = 0.0;
						balance = 0.0;
					}
					
				}
				//-- 罚息计算结束 --
			}
		}
		else
		{
			//log.info("lhj debug info ===============没有匹配的交易类型");
			throw new IException(true, "计算费用类型错误，交易失败", null);
		}
		////		//log.info("lhj debug info ===================getLoanAccountFee=====================");
		////		//log.info("lhj debug info 利率是:" + rate);
		////		//log.info("lhj debug info 余额是:" + balance);
		////		//log.info("lhj debug info 利息是:" + interest);
		//		if (sDate != null)
		//			//log.info("lhj debug info 起息日是:" + sDate);
		//		else
		//			//log.info("lhj debug info 起息日是null!");
		//		if (eDate != null)
		//			//log.info("lhj debug info 止息日是:" + eDate);
		//		else
		//			//log.info("lhj debug info 止息日是null!");
		//		//log.info("lhj debug info 天数是:" + days);
		//		//log.info("lhj debug info ===================getLoanAccountFee=====================");
		resInfo.setBalance(balance);
		resInfo.setDays(days);
		resInfo.setEDate(eDate);
		resInfo.setInterest(interest);
		resInfo.setRate(rate);
		resInfo.setSDate(sDate);

		return resInfo;

	}
	
	/**
	 * 接口名称：计算利息税以及税后利息。
	 * 功能说明：1：计算委托贷款账户的利息税费以及税后利息各种利息。
	 *           2：主要应用于“委托贷款收回”，“多笔贷款收回”，“利息费用支付”，“利息费用计算设置”，”利息费用结算“等交易。
	 *           3：只有结息时候才会扣税。
	 * @param accountID 贷款主账户
	 * @param subLoanAccountID 贷款子账户
	 * @param interestAmount 应税利息，对于委托委托贷款，包括贷款利息＋复利＋罚息
	 * @return InterestTaxInfo
	 * @throws IException
	 */
	public InterestTaxInfo getInterestTax(long accountID, long subLoanAccountID, double interestAmount) throws IException
	{
		InterestTaxInfo interestTaxInfo = new InterestTaxInfo();

		SubAccountLoanInfo subAccInfo = null;
		AccountInfo accInfo = null;
		try
		{
			//			AccountOperation accOperation = new AccountOperation();
			//			subAccInfo = accOperation.findSubAccountByID(subLoanAccountID).getSubAccountLoanInfo();	
			//			accInfo = accOperation.findAccountByID(accountID);
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
			Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(this.conn);
			accInfo = sett_AccountDAO.findByID(accountID);
			subAccInfo = sett_SubAccountDAO.findByID(subLoanAccountID).getSubAccountLoanInfo();

			if (subAccInfo.getID() < 0 || accInfo.getAccountID() < 0)
				throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//利息税率
		interestTaxInfo.setInterestTaxRate(subAccInfo.getInterestTaxRate());

		if (subAccInfo.getInterestTaxRate() > 0)
		{
			double interestTax = UtilOperation.Arith.round(UtilOperation.Arith.div(UtilOperation.Arith.mul(interestAmount, subAccInfo.getInterestTaxRate()), 100), 2);
			//利息税
			interestTaxInfo.setInterestTax(interestTax);
			//税后利息
			double interestAfterTax = UtilOperation.Arith.sub(interestAmount, interestTax);
			interestTaxInfo.setInterestAfterTax(interestAfterTax);
			//log.info("lhj debug info[interestCalculation(1198)] ==罚息==应缴税利息：" + interestAmount);
			//log.info("lhj debug info[interestCalculation(1198)] ==罚息==利息税率是：" + subAccInfo.getInterestTaxRate());
			//log.info("lhj debug info[interestCalculation(1199)] ==罚息====利息税是：" + interestTax);
			//log.info("lhj debug info[interestCalculation(1200)] ==罚息==税后利息是：" + interestAfterTax);
		}
		return interestTaxInfo;
	}	
	
	/**
	 * 接口名称：计算利息税以及税后利息。
	 * 功能说明：1：计算委托贷款账户的利息税费以及税后利息各种利息。
	 *           2：主要应用于“委托贷款收回”，“多笔贷款收回”，“利息费用支付”，“利息费用计算设置”，”利息费用结算“等交易。
	 *           3：只有结息时候才会扣税。
	 * @param accountID 贷款主账户
	 * @param subLoanAccountID 贷款子账户
	 * @param interestAmount 应税利息，对于委托委托贷款，包括贷款利息＋复利＋罚息
	 * @return InterestTaxInfo
	 * @throws IException
	 * 
	 * 通过利息税费率计划计算利息税费的值
	 */
	public InterestTaxInfo getInterestTaxByPlan(long accountID, long subLoanAccountID, double interestAmount) throws IException
	{
		InterestTaxInfo interestTaxInfo = new InterestTaxInfo();

		SubAccountLoanInfo subAccInfo = null;
		AccountInfo accInfo = null;
		try
		{
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
			Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(this.conn);
			accInfo = sett_AccountDAO.findByID(accountID);
			
			System.out.println("通过利息税费率计划计算利息税费的值 accountID is : "+accountID);
			System.out.println("通过利息税费率计划计算利息税费的值 subLoanAccountID is : "+subLoanAccountID);
			
			subAccInfo = sett_SubAccountDAO.findByID(subLoanAccountID).getSubAccountLoanInfo();
			
			if (subAccInfo.getID() < 0 || accInfo.getAccountID() < 0)
			{
				throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//利息税率计划
		System.out.println("************* InterestCalculation:利息税率计划 is : "+subAccInfo.getInterestTaxRatePlanID());
		
		interestTaxInfo.setInterestTaxPlanId(subAccInfo.getInterestTaxRatePlanID());

		if (subAccInfo.getInterestTaxRatePlanID() > 0)
		{
			SettTaxRatePlanSettingBiz planBiz = new SettTaxRatePlanSettingBiz();
			
			double interestTax = 0.00;
			try 
			{
				interestTax = planBiz.findInterestTaxByConditions( interestAmount , subAccInfo.getInterestTaxRatePlanID() );
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//double interestTax = UtilOperation.Arith.round(((UtilOperation.Arith.mul(interestAmount, subAccInfo.getInterestTaxRate())) / 100), 2);
			
			//利息税
			interestTaxInfo.setInterestTax(interestTax);
			//税后利息
			double interestAfterTax = UtilOperation.Arith.sub(interestAmount, interestTax);
			
			interestTaxInfo.setInterestAfterTax(interestAfterTax);
			//log.info("lhj debug info[interestCalculation(1198)] ==罚息==应缴税利息：" + interestAmount);
			//log.info("lhj debug info[interestCalculation(1198)] ==罚息==利息税率是：" + subAccInfo.getInterestTaxRate());
			//log.info("lhj debug info[interestCalculation(1199)] ==罚息====利息税是：" + interestTax);
			//log.info("lhj debug info[interestCalculation(1200)] ==罚息==税后利息是：" + interestAfterTax);
		}
		
		return interestTaxInfo;
	}

	/**
	 * @author hjliu
	 * 2003-11-21
	 * 通过放款通知单ID返回放款通知记录
	 * 调用utilOperation中的方法
	 * @param id 放款通知单ID
	 * @return 放款通知记录
	 * @throws IException
	 */
	private LoanPayNoticeInfo getLoanPayNoticeByID(long id) throws IException
	{
		LoanPayNoticeInfo loanPayNoticeInfo = new LoanPayNoticeInfo();
		UtilOperation utilOperation = new UtilOperation(this.conn);
		loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(id);
		return loanPayNoticeInfo;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private ContractInfo getContractInfoByID(long id) throws IException
	{
		//log.info("lhj debug info == 开始查询合同信息！");
		ContractInfo resInfo = null;
		UtilOperation utilOperation = new UtilOperation(conn);
		resInfo = utilOperation.findContractByID(id);

		return resInfo;
	}

	/**
	 * 
	 * @param loanNoteID
	 * @return
	 */
	private OverDueInfo getOverDueInfoByLoanNoteID(Connection conn, long loanNoteID) throws IException
	{
		OverDueInfo resInfo = new OverDueInfo();
		//调用settlement.util.UtilOperation下的2003-11-30 hjliu 
		UtilOperation utilOperation = new UtilOperation(conn);
		resInfo = utilOperation.getOverDueInfoByLoanNoteID(loanNoteID);
		return resInfo;
	}

	/**
	 * 2003-11-20 hjLiu 
	 * 在 settlement.util.utilOperation增加一个方法
	 * @param loanNoteID
	 * @param validDate
	 * @return
	 */
	public InterestRate getInterestRateByPayForm(long loanNoteID, Timestamp validDate) throws IException
	{
		//		//log.info("lhj debug info == 通过放款单取贷款利率开始------- ");
		//		//log.info("lhj debug info == 放款单ID是：" + loanNoteID);
		//		//log.info("lhj debug info == 时间是：" + validDate);
		PayNoticeRateInfo payNoticeRateInfo = null;
		InterestRate interestRate = null;

		UtilOperation utilOperation = new UtilOperation(this.conn);
		try
		{
			payNoticeRateInfo = utilOperation.getRateValue(1, loanNoteID, validDate);
		}
		catch (IException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		if (payNoticeRateInfo != null)
		{
			interestRate = new InterestRate(payNoticeRateInfo.getInterestRate(), payNoticeRateInfo.getRateStyle());
		}
		//log.info("lhj debug info == 通过放款单取贷款利率结束------- ");
		return interestRate;
	}
	
	/**
	 * modify by leiyang3
	 * date 2010/11/28
	 * 获取货款逾期后罚息利率通用方法
	 * @param paramInfo
	 * @return
	 * @throws IException
	 */
	public InterestRate getOverDueInterestRate(InterestCalculationParameterInfo paramInfo) throws IException
	{
		InterestRate interestRate = null;
		PayNoticeRateInfo payNoticeRateInfo = null;
		UtilOperation utilOperation = null;
		
		try
		{
			utilOperation = new UtilOperation(this.conn);
			payNoticeRateInfo = utilOperation.getRateValue(1, paramInfo.getLoanNoteID(), paramInfo.getValidDate());
			
			if (payNoticeRateInfo != null)
			{
				interestRate = new InterestRate(payNoticeRateInfo.getOverDueInterestRate(), payNoticeRateInfo.getRateStyle());
			}
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return interestRate;
	}
	
	/**
	 * modify by leiyang3
	 * date 2010/11/28
	 * 获取复息利率通用方法
	 * @param paramInfo
	 * @return
	 * @throws IException
	 */
	public InterestRate getCompoundInterestRate(InterestCalculationParameterInfo paramInfo) throws IException
	{
		InterestRate interestRate = null;
		PayNoticeRateInfo payNoticeRateInfo = null;
		UtilOperation utilOperation = null;
		
		try
		{
			utilOperation = new UtilOperation(this.conn);
			LoanPayNoticeInfo loanPayNoticeInfo = utilOperation.findLoanPayNoticeByID(paramInfo.getLoanNoteID());
			payNoticeRateInfo = utilOperation.getRateValue(1, paramInfo.getLoanNoteID(), paramInfo.getValidDate());
			
			if (payNoticeRateInfo != null)
			{
				if(loanPayNoticeInfo.getIsRemitOverDueInterest()==Constant.TRUE&&!loanPayNoticeInfo.getEnd().after(paramInfo.getValidDate()))
				{
					interestRate = new InterestRate(payNoticeRateInfo.getOverDueInterestRate(), payNoticeRateInfo.getRateStyle());
				}
				else
				{
					interestRate = new InterestRate(payNoticeRateInfo.getInterestRate(), payNoticeRateInfo.getRateStyle());
				}
			}
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return interestRate;
	}
	/**
	 * 开始计算实际间隔日期
	 * @param sG 起息日 GregorianCalendar
	 * @param eG 结息日 GregorianCalendar
	 * @return
	 */
	private synchronized long getFactIntervalDays(GregorianCalendar sG, GregorianCalendar eG)
	{
		//log.debug("---------开始计算实际间隔日期------------");
		long elapsed = 0;
		GregorianCalendar gc1, gc2;

		gc2 = (GregorianCalendar) eG.clone();
		gc1 = (GregorianCalendar) sG.clone();

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);

		while (gc1.before(gc2))
		{
			//Adds the specified (signed) amount of time to the given time field, based on the calendar's rules.
			gc1.add(Calendar.DATE, 1);
			elapsed++;
		}
		return elapsed;
	}

	/**get next n day's timesamp
	 * @param n>0 means get furture day, else means get past day
	*/
	public Timestamp getNextNDay(Timestamp currentDay, int n)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDay);
		calendar.add(Calendar.DATE, n);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
	}

	/**
	 * 
	 * @return conn
	 */
	private Connection getConnection()
	{
		return conn;
	}

	/**
	 * 设置活期存款利率
	 * @param currentInterest The currentInterest to set
	 */
	public void setCurrentInterestRate(double currentInterestRate)
	{
		this.CURRENT_INTEREST_RATE = currentInterestRate;
	}

	/**
	 * 功能说明：取回贷款账户一段时间内的计提利息。
	 * @param AccountID    主账户ID
	 * @param SubAccountID 子账户ID
	 * @param clearDate    结息日
	 * @return SubAccountPredrawInterestInfo  贷款/定期子账户计提利息信息
	 */
	public SubAccountPredrawInterestInfo getLoanAccountPredrawInterest(long AccountID, long SubAccountID, long AccountType, Timestamp clearDate) throws IException
	{
		SubAccountPredrawInterestInfo subAccountPredrawInterestInfo = null;
		UtilOperation utilOperation = new UtilOperation(this.conn);
		try
		{
			subAccountPredrawInterestInfo = utilOperation.getPredrawInterestBySubAccountIDAndAccountType(SubAccountID, AccountType);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return subAccountPredrawInterestInfo;
	}

	/**
	 * 功能说明：取回定期账户一段时间内的计提利息。
	 * @param AccountID    主账户ID
	 * @param SubAccountID 子账户ID
	 * @param clearDate    计提日
	 * @return SubAccountPredrawInterestInfo  贷款/定期子账户计提利息信息
	 */
	public SubAccountPredrawInterestInfo getFixedAccountPredrawInterest(long AccountID, long SubAccountID, long AccountType, Timestamp clearDate) throws IException
	{
		SubAccountPredrawInterestInfo subAccountPredrawInterestInfo = null;
		UtilOperation utilOperation = new UtilOperation(this.conn);

		try
		{

			subAccountPredrawInterestInfo = utilOperation.getPredrawInterestBySubAccountIDAndAccountType(SubAccountID, AccountType);

		}

		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return subAccountPredrawInterestInfo;
	}
	/**
	* 匡算/估算通知存款账户的利息。
	* @param lOfficeID    办事处
	* @param lCurrencyID  币种
	* @param accountID    主账户
	* @param subAccountID 子账户
	* @param startDate    起息日
	* @param endDate    止息日
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
		Timestamp startDate,
		Timestamp endDate,
		double interestRate,
		Timestamp execDate,
		long InterestFlag)
		throws IException
	{
		Connection connection = null;
		connection = this.conn;
		FixedDepositAccountPayableInterestInfo fixedDepositAccountPayableInterestInfo = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(connection);
		Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(connection);

		//返回子账户
		try
		{

			SubAccountAssemblerInfo subAccount = sett_SubAccountDAO.findByID(subAccountID);
			if (subAccount == null)
			{

				throw new IException(true, "子账户表中没有对应记录，交易失败", null);
			}
			//定期子账户对象
			SubAccountFixedInfo subFixedInfo = subAccount.getSubAccountFixedInfo();
			//
			Timestamp subStartDate = subFixedInfo.getStartDate();
			Timestamp subFinishDate = subFixedInfo.getFinishDate();
			double subBalance = subFixedInfo.getBalance();

			//起息日
			Timestamp sDate = null;
			//止息日
			Timestamp eDate = null;
			//余额
			double balance = 0.0;
			//天数
			long days = 0;
			//利息
			double interest = 0.0;
			//已计积数
			double hasRecord = 0.0;
			if (subStartDate.before(startDate))
				sDate = startDate;
			else
				sDate = subStartDate;

			if (subFinishDate == null)
			{
				eDate = this.getNextNDay(endDate, -1);
			}
			else
			{

				if (subFinishDate.before(endDate))
					eDate = this.getNextNDay(subFinishDate, -1);
				else
					eDate = this.getNextNDay(endDate, -1);
			}
			days = this.getIntervalDays(sDate, this.getNextNDay(eDate, 1), 1);

			if (!endDate.before(execDate))
			{
				balance = subBalance;

			}
			else
			{
				DailyAccountBalanceInfo dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(subAccountID, lOfficeID, lCurrencyID, eDate);
				balance = dailyAccountBalanceInfo.getBalance();

			}
			if (InterestFlag != 1)
			{
				//计算指定期间所有已经产生的利息
				if (endDate.before(execDate))
				{
					//表明查询之前的累计积数
					hasRecord = sett_DailyAccountBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(subAccountID, sDate, eDate);
				}
				else
				{
					//表明查询查找之前的累计积数 + 未来的累计积数
					hasRecord = sett_DailyAccountBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(subAccountID, sDate, execDate);
					long futureDays = this.getIntervalDays(execDate, this.getNextNDay(eDate, 1), 1);

					double futureRecord = balance * futureDays;
					hasRecord = UtilOperation.Arith.add(hasRecord, futureRecord);
				}

			}
			else
			{
				//计算指定期间所有的未结的利息		
				hasRecord = balance * days;
			}

			interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(hasRecord, interestRate), 36000);

			fixedDepositAccountPayableInterestInfo = new FixedDepositAccountPayableInterestInfo();

			//赋值
			fixedDepositAccountPayableInterestInfo.setBalance(balance);
			fixedDepositAccountPayableInterestInfo.setDays(days);
			fixedDepositAccountPayableInterestInfo.setEDate(eDate);
			fixedDepositAccountPayableInterestInfo.setSDate(sDate);
			fixedDepositAccountPayableInterestInfo.setInterest(interest);
			fixedDepositAccountPayableInterestInfo.setRate(interestRate);

		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "无法找到对应的子账户或账户余额表记录,交易失败", null);
		}

		return fixedDepositAccountPayableInterestInfo;
	}

	/**
	 * 计算定期存款账户在某一段是内所产生的利息，而且不论是否已经结息。主要应用于估算定期存款账户利息，定期存款计提应付利息等交易。
	 * @param AccountID    主账户
	 * @param SubAccountID 子账户
	 * @param OfficeID     办事处
	 * @param CurrencyID   币种
	 * @param StartDate    起息日
	 * @param EndDate      结息日
	 * @throws IException
	 * @return FixedDepositAccountPayableInterestInfo
	 */

	public FixedDepositAccountPayableInterestInfo EstimateFixedDepositAccountInterest(long AccountID, long SubAccountID, long OfficeID, long CurrencyID, Timestamp StartDate, Timestamp EndDate)
		throws IException
	{
		FixedDepositAccountPayableInterestInfo fixedDepositAccountPayableInterestInfo = null;
		Connection connection = this.conn;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(connection);
		SubAccountAssemblerInfo subAccountInfo = new SubAccountAssemblerInfo();
		try
		{
			subAccountInfo = sett_SubAccountDAO.findByID(SubAccountID);
			if (subAccountInfo == null)
			{
				throw new IException(true, "无法找到对应的子账户,交易失败", null);

			}

			SubAccountFixedInfo subFixedInfo = subAccountInfo.getSubAccountFixedInfo();
			Timestamp sDate = null; //起息日
			Timestamp eDate = null; //止息日
			Timestamp AF_Start = subFixedInfo.getStartDate(); //定期子账户开始日期
			Timestamp AF_End = subFixedInfo.getEndDate(); //定期子账户结束日期
			if (AF_Start == null)
			{

				sDate = StartDate;
			}
			else
			{
				if (AF_Start.before(StartDate))
					sDate = StartDate;
				else
					sDate = AF_Start;
			}

			if (AF_End == null)
			{
				eDate = this.getNextNDay(EndDate, -1);
			}
			else
			{
				if (AF_End.before(EndDate))
					eDate = this.getNextNDay(AF_End, -1);
				else
					eDate = this.getNextNDay(EndDate, -1);
			}

			//天数
			long days = this.getIntervalDays(sDate, this.getNextNDay(eDate, 1), INTERVALDAYSFLAG_30TDAY);
			//当前余额
			double balance = subFixedInfo.getBalance();
			//利率
			double rate = subFixedInfo.getRate();
			//利息
			double interest =
				this.caculateInterest(balance, sDate, this.getNextNDay(eDate, 1), INTERVALDAYSFLAG_30TDAY, rate, SETTConstant.InterestRateTypeFlag.YEAR, SETTConstant.InterestRateDaysFlag.DAYS_360);
			fixedDepositAccountPayableInterestInfo = new FixedDepositAccountPayableInterestInfo();
			fixedDepositAccountPayableInterestInfo.setBalance(balance);
			fixedDepositAccountPayableInterestInfo.setDays(days);
			fixedDepositAccountPayableInterestInfo.setEDate(eDate);
			fixedDepositAccountPayableInterestInfo.setSDate(sDate);
			fixedDepositAccountPayableInterestInfo.setInterest(interest);
			fixedDepositAccountPayableInterestInfo.setRate(rate);

		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException(true, "无法找到对应的子账户,交易失败", null);
		}

		return fixedDepositAccountPayableInterestInfo;

	}

	/**
	 * 估算贷款账户在某一段时间内所产生的各种利息，而不论是否已经结息，主要应用于贷款利息的估算。
	 * @param AccountID     主账户ID
	 * @param SubAccountID  子账户ID
	 * @param OfficeID      办事处
	 * @param CurrencyID    币种
	 * @param StartDate     起息日
	 * @param EndDate       结息日
	 * @param ExecuteDate   执行日
	 * @param InterestType  利息类型（利息，复利，手续费，担保费，罚息）
	 * @return
	 * @throws IException
	 */
	public LoanAccountInterestInfo EstimateLoanAccountInterest(
		long AccountID,
		long SubAccountID,
		long OfficeID,
		long CurrencyID,
		Timestamp StartDate,
		Timestamp EndDate,
		Timestamp ExecuteDate,
		long InterestType)
		throws IException
	{

		LoanAccountInterestInfo loanAccountInterestInfo = null;

		Connection connection = this.conn;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(connection);
		Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(connection);
		SubAccountAssemblerInfo subAccountInfo = new SubAccountAssemblerInfo();
		try
		{
			Timestamp sDate = null;
			Timestamp eDate = null;
			double balance = 0.0;
			long days = -1;
			double rate = 0.0;
			double interest = 0.0;
			DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
			subAccountInfo = sett_SubAccountDAO.findByID(SubAccountID);
			if (subAccountInfo == null)
			{
				throw new IException(true, "无法找到对应的子账户,交易失败", null);

			}
			//贷款子账户对象
			SubAccountLoanInfo subLoanInfo = subAccountInfo.getSubAccountLoanInfo();
			//放款通知单ID
			long loanNoteID = subLoanInfo.getLoanNoteID();
			//放款通知单对象
			LoanPayNoticeInfo loanPayNoticeInfo = this.getLoanPayNoticeByID(loanNoteID);

			//放款通知单——贷款起始日期
			Timestamp AL_Start = loanPayNoticeInfo.getStart();
			//放款通知单——贷款结束日期
			Timestamp AL_End = loanPayNoticeInfo.getEnd();
			if (AL_Start == null)
			{
				sDate = StartDate;
			}
			else
			{
				if (AL_Start.before(StartDate))
				{
					sDate = StartDate;
				}
				else
				{
					sDate = AL_Start;
				}

			}
			if (AL_End == null)
			{
				eDate = this.getNextNDay(EndDate, -1);
			}
			else
			{
				if (AL_End.before(EndDate))
				{
					eDate = this.getNextNDay(AL_End, -1);
				}
				else
				{
					eDate = this.getNextNDay(EndDate, -1);
				}

			}
			//天数
			days = this.getIntervalDays(sDate, this.getNextNDay(eDate, 1), 1);
			//余额
			if (EndDate.before(ExecuteDate))
			{
				dailyAccountBalanceInfo = sett_DailyAccountBalanceDAO.findAllBySubAccountIDAndDate(SubAccountID, OfficeID, CurrencyID, EndDate);
				if (dailyAccountBalanceInfo == null)
				{
					throw new IException(true, "无法找到对应的账户余额表记录,交易失败", null);
				}
				balance = dailyAccountBalanceInfo.getInterestBalance();
			}
			else
			{
				balance = subLoanInfo.getBalance();

			}
			InterestRate interestRate = null;
			//利率
			if (InterestType == SETTConstant.InterestFeeType.INTEREST)
			{
				interestRate = this.getInterestRateByPayForm(loanNoteID, eDate);

				rate = interestRate.getRate();

			}
			if (InterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				interestRate = this.getInterestRateByPayForm(loanNoteID, eDate);

				rate = interestRate.getRate();
			}
			if (InterestType == SETTConstant.InterestFeeType.ASSURE)
			{
				rate = loanPayNoticeInfo.getSuretyFeeRate();
			}
			if (InterestType == SETTConstant.InterestFeeType.COMMISION)
			{
				rate = loanPayNoticeInfo.getCommissionRate();
			}
			if (InterestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
			{
				OverDueInfo overDueInfo = this.getOverDueInfoByLoanNoteID(connection, loanNoteID);
				rate = overDueInfo.getFineInterestRate();
			}
			//利息
			//已计积数
			double hasRecord = 0.0;
			//			复利已计积数
			if (InterestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				hasRecord = sett_DailyAccountBalanceDAO.sumArrearageInterestBySubAccountIDAndDate(SubAccountID, sDate, eDate);

			}
			else
			{
				hasRecord = sett_DailyAccountBalanceDAO.sumInterestBalanceBySubAccountIDAndDate(SubAccountID, sDate, eDate);
			}
			//如果结息日早于执行日，那么利息 等于 以前的利息
			if (EndDate.before(ExecuteDate))
			{

				interest = UtilOperation.Arith.mul(hasRecord, rate) / 36000;

			}
			//利息 等于 以前的利息 + 将来的利息
			else
			{
				double futureInterest = 0.0;
				futureInterest = this.calculateLoanAccountInterest(CurrencyID, rate, SETTConstant.InterestRateTypeFlag.YEAR, balance, ExecuteDate, this.getNextNDay(eDate, 1));
				interest = UtilOperation.Arith.add(futureInterest, UtilOperation.Arith.div(UtilOperation.Arith.mul(hasRecord, rate), 36000));
			}

			loanAccountInterestInfo = new LoanAccountInterestInfo();
			loanAccountInterestInfo.setBalance(balance);
			loanAccountInterestInfo.setDays(days);
			loanAccountInterestInfo.setEDate(eDate);
			loanAccountInterestInfo.setInterest(interest);
			loanAccountInterestInfo.setRate(rate);
			loanAccountInterestInfo.setSDate(sDate);
		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException(true, "无法找到对应的子账户,交易失败", null);
		}

		return loanAccountInterestInfo;
	}
	public double getCurrentInterestRate() {
		return CURRENT_INTEREST_RATE;
	}

	/**
	 * 调用中国国电贷款逾期复利罚息计算方法 Boxu 2008-10-06
	 * @param accountID 主账户ID
	 * @param subLoanAccountID 贷款子账户ID
	 * @param interestDate 结息日
	 * @param exectDate    执行日
	 * @param interestType 费用类型 4：复利；5：罚息。
	 * @return LoanAccountInterestInfo
	 * @throws IException
	 */
	public LoanAccountInterestInfo getLoanGuoDianAccountFee(
		Connection conn,
		long lOfficeID,
		long lCurrencyID,
		long accountID,
		long subLoanAccountID,
		Timestamp interestDate,
		Timestamp execDate,
		long interestType)
		throws IException
	{
		LoanAccountInterestInfo resInfo = new LoanAccountInterestInfo();
		SubAccountLoanInfo subAccInfo = null;
		AccountInfo accInfo = null;
		LoanPayNoticeInfo loanPayNoticeInfo = null;
		DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
		OverDueInfo overDueInfo = null;
		Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		//UtilOperation utilOperation = new UtilOperation();
		
		try {
			//返回主账户和子账户
			try {
				Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(this.conn);
				Sett_AccountDAO sett_AccountDAO = new Sett_AccountDAO(this.conn);
				accInfo = sett_AccountDAO.findByID(accountID);
				subAccInfo = sett_SubAccountDAO.findByID(subLoanAccountID).getSubAccountLoanInfo();
	
				if (accInfo == null || subAccInfo == null || subAccInfo.getID() < 0 || accInfo.getAccountID() < 0)
					throw new IException(true, "无法找到对应的账户或子账户,交易失败", null);
				
				//返回主账户和子账户
				//返回放款通知单记录
				log.info("lhj debug info --------------Sett_AccountID=" + accInfo.getAccountID());
				log.info("lhj debug info --------------Sett_SubAccountID=" + subAccInfo.getID());
				log.info("lhj debug info --------------LoanNoteID=" + subAccInfo.getLoanNoteID());
				loanPayNoticeInfo = getLoanPayNoticeByID(subAccInfo.getLoanNoteID()); //方法是空 2003-11-21
				
				//返回放款通知单记录----------------------------------------------------------end----
				if (loanPayNoticeInfo == null || loanPayNoticeInfo.getID() < 0)
				{
					throw new IException(true, "无法找到对应放款通知单记录,交易失败", null);
				}
				
				//查询逾期申请单信息
				overDueInfo = getOverDueInfoByLoanNoteID(conn, subAccInfo.getLoanNoteID());
				if (overDueInfo != null && overDueInfo.getLoanPayID() > 0)
				{
					//返回interestDate的账户余额表记录
					if (interestDate.before(execDate))
					{
						dailyAccountBalanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID, getNextNDay(interestDate, -1));
		
						if (dailyAccountBalanceInfo == null)
						{
							throw new IException(true, "没有对应的账户余额表记录！，交易失败", null);
						}
					}
					else
					{
						dailyAccountBalanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID, getNextNDay(execDate, -1));
		
						if (dailyAccountBalanceInfo == null)
						{
							throw new IException(true, "没有对应的账户余额表记录！，交易失败", null);
						}
					}
				}
				else
				{
					System.out.println("没有逾期申请单,不计算罚息和复利");
				}
			}
			catch (IException ie)
			{
				throw ie;
			}
			catch (SQLException sqlE)
			{
				sqlE.printStackTrace();
				throw new IException(true, "无法找到对应的子账户,交易失败", null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException(true, "无法找到对应的账户,交易失败", null);
			}
			
			Timestamp sDate = null;  			//起息日
			long days = -1;  					//天数
			double rate = 0.0;  				//利率
			double interest = 0.0;  			//利息
			double balance = 0.0;  				//余额
			Timestamp eDate = null;  			//止息日
			//double sumOfBalance = 0.0;  		//已计积数
			//double sumOfBalance_Rate = 0.0;  	//已计积数×利率
			
			//计算罚息
			if (overDueInfo != null && interestType == SETTConstant.InterestFeeType.FORFEITINTEREST)
			{
				//罚息起息日
				if(overDueInfo.getFineDate() != null)
				{
					sDate = overDueInfo.getFineDate();
				}
				else
				{
					sDate = subAccInfo.getClearOverDueDate();
				}
				
				//罚息利率
				rate = overDueInfo.getFineInterestRate();
	
				//罚息止息日
				eDate = getNextNDay(interestDate, -1);
				
				//天数
				days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
				
				//已计罚息积数
				//try
				//{
				//	sumOfBalance = dailyBalanceDAO.sumOverdueAmountBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
				//}
				//catch (SQLException e)
				//{
				//	throw new IException(true, "积数查询发生SQLException，交易失败", null);
				//}
				//double hasRecordInterest = UtilOperation.Arith.mul(sumOfBalance, UtilOperation.Arith.div(rate, 36000));
	
				if (interestDate.before(execDate))
				{
					//查找以前或当前的累计利息
					//log.info("贷款罚息计算==输入结息日早于系统当前日期");
					//interest = UtilOperation.Arith.add(hasRecordInterest, subAccInfo.getOverDueInterest());
					//balance = dailyAccountBalanceInfo.getAl_mOverDueAmount();
					
					interest = dailyAccountBalanceInfo.getMforfeitinterest();
					//interest = UtilOperation.Arith.add(hasRecordInterest, subAccInfo.getCompoundInterest());
					
					//计息余额
					if(dailyAccountBalanceInfo.getInterestBalance() > 0) 
					{
						balance = dailyAccountBalanceInfo.getInterestBalance();
					} 
					else 
					{
						//系统做了逾期处理,不在计算正常利息
						balance = dailyAccountBalanceInfo.getBalance();
					}
				}
				else
				{
					//log.info("贷款罚息计算==输入结息日晚于等于系统当前日期");
					balance = subAccInfo.getBalance();
					double furtureInterest = calculateLoanAccountInterest(lCurrencyID, rate, 1, balance, execDate, interestDate);
					interest = UtilOperation.Arith.add(dailyAccountBalanceInfo.getMforfeitinterest(), furtureInterest);
				}
			}
			//计算复利
			else if (overDueInfo != null 
				&& overDueInfo.getIsCompoundInterest() == SETTConstant.BooleanValue.ISTRUE 
				&& interestType == SETTConstant.InterestFeeType.COMPOUNDINTEREST)
			{
				log.info("（interestCalculation 987）lhj debug info ===============进入COMPOUNDINTEREST");
				//复利起息日
				if(overDueInfo.getFineDate() != null)
				{
					sDate = overDueInfo.getFineDate();
				}
				else
				{
					sDate = subAccInfo.getClearCompoundDate();
				}
				
				//复利止息日
				eDate = getNextNDay(interestDate, -1);
				log.info("复利起息日 : " + sDate);
				log.info("复利止息日 : " + eDate);
				
				//天数
				days = getIntervalDays(sDate, interestDate, INTERVALDAYSFLAG_FACTDAY);
				
				//复利利率
				rate = overDueInfo.getFineInterestRate();
				
				//已计复利积数
				//double hasRecordInterest = 0.0;
				//try
				//{
					//sumOfBalance_Rate = dailyBalanceDAO.sumArrearageInterestBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
				//	hasRecordInterest =  dailyBalanceDAO.sumArrearageInterestBySubAccountIDAndDate(subLoanAccountID, sDate, eDate);
				//}
				//catch (SQLException e)
				//{
				//	throw new IException(true, "积数查询发生SQLException，交易失败", null);
				//}
				
				if (interestDate.before(execDate))
				{
					//查找以前或当前的累计利息
					log.debug("lhj debug info == 贷款复利结息==输入结息日早于系统当前日期");
					if(dailyAccountBalanceInfo.getInterest() > 0) 
					{
						interest = dailyAccountBalanceInfo.getMcompoundinterest();
					}
					//interest = UtilOperation.Arith.add(hasRecordInterest, subAccInfo.getCompoundInterest());
					
					//计息余额
					if(dailyAccountBalanceInfo.getInterest() > 0) 
					{
						balance = dailyAccountBalanceInfo.getInterest();
					} 
					else 
					{
						//系统做了逾期处理,不在计算正常利息
						balance = subAccInfo.getInterest();
					}
					//balance = dailyAccountBalanceInfo.getAl_mArrearageInterest();
				}
				else
				{
					log.debug("lhj debug info == 贷款复利结息==输入结息日晚于等于系统当前日期");
					balance = subAccInfo.getInterest();
					//balance = subAccInfo.getArrearageInterest();
					
					double furtureInterest = calculateLoanAccountInterest(lCurrencyID, rate, 1, balance, execDate, interestDate);
					interest = UtilOperation.Arith.add(dailyAccountBalanceInfo.getMcompoundinterest(), furtureInterest);
				}
			}
			else
			{
				System.out.println("中国国电贷款逾期类型错误");
			}
			
			resInfo.setBalance(balance);
			resInfo.setDays(days);
			resInfo.setEDate(eDate);
			resInfo.setInterest(interest);
			resInfo.setRate(rate);
			resInfo.setSDate(sDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException(true, "计算罚息,复利失败", null);
		}

		return resInfo;
	}
	
	/**
	* 匡算/估算定期存款账户的利息。
	* @param lOfficeID    办事处
	* @param lCurrencyID  币种
	* @param accountID    主账户
	* @param subAccountID 子账户
	* @param startDate    起息日
	* @param endDate    止息日
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
		Timestamp startDate,
		Timestamp endDate,
		double interestRate,
		Timestamp execDate,
		long InterestFlag)
		throws IException
	{
  		Connection connection = null;
		connection = this.conn;
		FixedDepositAccountPayableInterestInfo fixedDepositAccountPayableInterestInfo = null;
		Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO(connection);
		Sett_DailyAccountBalanceDAO sett_DailyAccountBalanceDAO = new Sett_DailyAccountBalanceDAO(connection);

		//返回子账户
		try
		{
			Sett_InterestRateDAO sett_InterestRateDAO=new Sett_InterestRateDAO();
			SubAccountAssemblerInfo subAccount = sett_SubAccountDAO.findByID(subAccountID);
			if (subAccount == null)
			{

				throw new IException(true, "子账户表中没有对应记录，交易失败", null);
			}
			//定期子账户对象
			SubAccountFixedInfo subFixedInfo = subAccount.getSubAccountFixedInfo();
			//
			Timestamp subStartDate = subFixedInfo.getStartDate(); //定期存单开始日
			Timestamp subFinishDate = subFixedInfo.getFinishDate(); //定期存单清户日
			Timestamp subEndDate = subFixedInfo.getEndDate();   //定期存单结束日
			
			double subBalance = subFixedInfo.getBalance();

			//起息日
			Timestamp sDate = null;
			//止息日
			Timestamp eDate = null;
			//余额
			double balance = 0.0;
			//天数  
			long days = 0;
			long totalDays = 0;
			//利息
			double interest = 0.0;
			double strInterestRate=0.0; //新利率
			long nfixeddepositmonthID=subFixedInfo.getDepositTerm();
			
			String strStartDate="";
			String strEndDate="";
			String strDays="";
			String strRate="";
			String strInterest="";
		    double varInterest=0.0;
			balance = subBalance;
			totalDays = getIntervalDays(startDate, getNextNDay(endDate,0), SETTConstant.InterestCalculationMode.TDAY30);
			if (subFinishDate == null) {
				// A:startDate 输入的匡算开始日,B:endDate 输入的匡算结束日,S:subStartDate
				// 存单的开始日,E:subeEndDate 存单的结束日

				if ((startDate.before(subStartDate) || startDate.equals(subStartDate) )
						&& subStartDate.before(endDate)
						&& (endDate.before(subEndDate) || endDate
								.equals(subEndDate)))
				{
					// 第一种情况 A<=S<B<=E
				  sDate = subStartDate;
				  eDate = endDate;

					interest = this.caculateInterest(
							  balance
							, sDate
							, getNextNDay(eDate, 0)
							, SETTConstant.InterestCalculationMode.TDAY30
						    ,interestRate
							, SETTConstant.InterestRateTypeFlag.YEAR
							, SETTConstant.InterestRateDaysFlag.DAYS_360 );
					strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
					strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
					strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
					strRate=strRate+DataFormat.formatRate(interestRate)+"/";
					strInterest=strInterest+DataFormat.formatRate(interest)+"/";
				}else if(subStartDate.before(startDate) && (endDate.before(subEndDate) || endDate.equals(subEndDate))){
					// 第二种情况 S<=A<B<=E
			      sDate=startDate;
			      eDate=endDate;
			      interest = this.caculateInterest(
						  balance
						, sDate
						, getNextNDay(eDate, 0)
						, SETTConstant.InterestCalculationMode.TDAY30  
					    ,interestRate
						, SETTConstant.InterestRateTypeFlag.YEAR
						, SETTConstant.InterestRateDaysFlag.DAYS_360 );
			      
					strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
					strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
					strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
					strRate=strRate+DataFormat.formatRate(interestRate)+"/";
					strInterest=strInterest+DataFormat.formatRate(interest)+"/";
				}else if(subStartDate.before(startDate)&& (startDate.before(subEndDate)||startDate.equals(subEndDate)) && subEndDate.before(endDate)){
				    // 第三种情况 S<A<E<B 
					// 由于利率可能不同 分为两段. 1)A-E,2)E-B	
					//1)A-E
					  sDate=startDate;
				      eDate=subEndDate;
//				      //处理A=E的情况，如果A=E 则不用算息了 add by xfma3(马现福)
				      //这种情况出现在当定期存单的结束日期等于匡算的开始日期的情况
				      days = getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30);
				      if(days > 0){
				    	  interest = this.caculateInterest(
				    			  balance
				    			  , sDate
				    			  , getNextNDay(eDate, 0)
				    			  , SETTConstant.InterestCalculationMode.TDAY30 
				    			  ,interestRate
				    			  , SETTConstant.InterestRateTypeFlag.YEAR
				    			  , SETTConstant.InterestRateDaysFlag.DAYS_360 );
				    	  strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
				    	  strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
				    	  strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
				    	  strRate=strRate+DataFormat.formatRate(interestRate)+"/";
				    	  strInterest=strInterest+DataFormat.formatRate(interest)+"/";   
				      }
				     // 2)E-B
				      sDate=subEndDate;
				      eDate=endDate;
				      days = getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30);
				      long j=0;
				      if(days%(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))==0){
				    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm())); 
				      }else{
				    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))+1;
				      }
				      for(int i=1;i<=j;i++){
				    	  if(i==j){
				    		  sDate=subEndDate;
				    		  eDate=endDate; 
				
				    	  }else{	  
				    		    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				    			GregorianCalendar sCalendar = new GregorianCalendar();				    			
				    		    sDate=subEndDate;
				    			sCalendar.setTime(sDate);
				    			sCalendar.add(Calendar.MONTH, (int)nfixeddepositmonthID);
				    		eDate=Timestamp.valueOf(df.format(sCalendar.getTime())+" 00:00:00");	
				    		subEndDate=eDate;
				    	  }
				    	  strInterestRate=sett_InterestRateDAO.getInterestRate(sDate,lCurrencyID,lOfficeID,nfixeddepositmonthID);
				    	  if(strInterestRate==0.0)
				    		  strInterestRate=interestRate;
				    	  varInterest= this.caculateInterest(
								  balance
									, sDate
									, getNextNDay(eDate, 0)
									, SETTConstant.InterestCalculationMode.TDAY30 
								    , strInterestRate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
				    	     interest = interest+ varInterest;
							strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
							strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
							strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
							strRate=strRate+DataFormat.formatRate(strInterestRate)+"/";
							strInterest=strInterest+DataFormat.formatRate(varInterest)+"/";
				    	  }
			    }else if(!startDate.after(subStartDate) && subEndDate.before(endDate) ){
				   //第四种情况 A<=S<E<B
			       //由于利率可能不同 分为两段.   1)S-E,2)E-B	
//			    	1)S-E
					  sDate=subStartDate;
				      eDate=subEndDate;
				      interest = this.caculateInterest(
							  balance
							, sDate
							, getNextNDay(eDate, 0)
							, SETTConstant.InterestCalculationMode.TDAY30 
						    ,interestRate
							, SETTConstant.InterestRateTypeFlag.YEAR
							, SETTConstant.InterestRateDaysFlag.DAYS_360 );
						strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
						strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
						strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
						strRate=strRate+DataFormat.formatRate(interestRate)+"/";
						strInterest=strInterest+DataFormat.formatRate(interest)+"/";
				     // 2)E-B
				      sDate=subEndDate;
				      eDate=endDate;
	
				      days = getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30);
				      long j=0;
				      if(days%(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))==0){
				    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm())); 
				      }else{
				    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))+1;
				      }
				      for(int i=1;i<=j;i++){
				    	  if(i==j){
				    		  sDate=subEndDate;
				    		  eDate=endDate; 
				    	  }else{	  
				    		    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				    			GregorianCalendar sCalendar = new GregorianCalendar();				    			
				    		    sDate=subEndDate;
				    			sCalendar.setTime(sDate);
				    			sCalendar.add(Calendar.MONTH, (int)nfixeddepositmonthID);
				    		eDate=Timestamp.valueOf(df.format(sCalendar.getTime())+" 00:00:00");	
				    		subEndDate=eDate;
				    	  }
	
				    	  strInterestRate=sett_InterestRateDAO.getInterestRate(sDate,lCurrencyID,lOfficeID,nfixeddepositmonthID);
				    	  if(strInterestRate==0.0)
				    		  strInterestRate=interestRate;
				    	  varInterest= this.caculateInterest(
								  balance
									, sDate
									, getNextNDay(eDate, 0)
									, SETTConstant.InterestCalculationMode.TDAY30 
								    , strInterestRate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
				    	     interest = interest+ varInterest;
							strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
							strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
							strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
							strRate=strRate+DataFormat.formatRate(strInterestRate)+"/";
							strInterest=strInterest+DataFormat.formatRate(varInterest)+"/";
				    	  }
			    }else if(subEndDate.before(startDate)){
                   //第五种情况 E<A
			    	 days = getIntervalDays(subEndDate, getNextNDay(startDate,0), SETTConstant.InterestCalculationMode.TDAY30);
			    	    long j=0;
					      if(days%(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))==0){
					    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm())); 
					      }else{
					    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))+1;
					      }
			    	  for(int i=1;i<=j;i++){
			    		  SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			    			GregorianCalendar sCalendar = new GregorianCalendar();				    			
			    		    sDate=subEndDate;
			    		    sCalendar.setTime(sDate);
			    			sCalendar.add(Calendar.MONTH, (int)nfixeddepositmonthID);
			    		    eDate=Timestamp.valueOf(df.format(sCalendar.getTime())+" 00:00:00");	
			    		    subEndDate=eDate;
			    	  }
			    	  subStartDate=sDate;
			    	  subEndDate=eDate;
			    	  
			    	 if(endDate.before(subEndDate) || endDate.equals(subEndDate)){
							// 第二种情况 S<=A<B<=E
					      sDate=startDate;
					      eDate=endDate;
					      strInterestRate=sett_InterestRateDAO.getInterestRate(sDate,lCurrencyID,lOfficeID,nfixeddepositmonthID);
				    	  if(strInterestRate==0.0)
				    		  strInterestRate=interestRate;
					      interest = this.caculateInterest(
								  balance
								, sDate
								, getNextNDay(eDate, 0)
								, SETTConstant.InterestCalculationMode.TDAY30   
							    ,strInterestRate
								, SETTConstant.InterestRateTypeFlag.YEAR
								, SETTConstant.InterestRateDaysFlag.DAYS_360 );
							strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
							strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
							strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
							strRate=strRate+DataFormat.formatRate(strInterestRate)+"/";
							strInterest=strInterest+DataFormat.formatRate(interest)+"/";
			    	 }else if(subEndDate.before(endDate)){
						    // 可能会出现第三种情况 S<A<E<B 
							// 由于利率可能不同 分为两段. 1)A-E,2)E-B	
							//1)A-E
							  sDate=startDate;
						      eDate=subEndDate;
						      strInterestRate=sett_InterestRateDAO.getInterestRate(sDate,lCurrencyID,lOfficeID,nfixeddepositmonthID);
					    	  if(strInterestRate==0.0)
					    		  strInterestRate=interestRate;
						      interest = this.caculateInterest(
									  balance
									, sDate
									, getNextNDay(eDate, 0)
									, SETTConstant.InterestCalculationMode.TDAY30 
								    , strInterestRate
									, SETTConstant.InterestRateTypeFlag.YEAR
									, SETTConstant.InterestRateDaysFlag.DAYS_360 );
								strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
								strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
								strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
								strRate=strRate+DataFormat.formatRate(strInterestRate)+"/";
								strInterest=strInterest+DataFormat.formatRate(interest)+"/";
						     // 2)E-B
						      sDate=subEndDate;
						      eDate=endDate;
						      days = getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30);
						 
						      if(days%(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))==0){
						    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm())); 
						      }else{
						    	  j=days/(30*(subAccount.getSubAccountFixedInfo().getDepositTerm()))+1;
						      }
						      for(int i=1;i<=j;i++){
						    	  if(i==j){
						    		  sDate=subEndDate;
						    		  eDate=endDate; 

						    	  }else{	  
						    		    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
						    			GregorianCalendar sCalendar = new GregorianCalendar();				    			
						    		    sDate=subEndDate;
						    			sCalendar.setTime(sDate);
						    			sCalendar.add(Calendar.MONTH, (int)nfixeddepositmonthID);
						    		eDate=Timestamp.valueOf(df.format(sCalendar.getTime())+" 00:00:00");	
						    		subEndDate=eDate;
						    	  }
				
						    	  strInterestRate=sett_InterestRateDAO.getInterestRate(sDate,lCurrencyID,lOfficeID,nfixeddepositmonthID);
						    	  if(strInterestRate==0.0)
						    		  strInterestRate=interestRate;
						    	  varInterest= this.caculateInterest(
										  balance
											, sDate
											, getNextNDay(eDate, 0)
											, SETTConstant.InterestCalculationMode.TDAY30 
										    , strInterestRate
											, SETTConstant.InterestRateTypeFlag.YEAR
											, SETTConstant.InterestRateDaysFlag.DAYS_360 );
						    	     interest = interest+ varInterest;
									strDays=strDays+getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30)+"/";
									strStartDate=strStartDate+DataFormat.formatString(DataFormat.formatDate(sDate))+"/";
									strEndDate=strEndDate+DataFormat.formatString(DataFormat.formatDate(eDate))+"/";
									strRate=strRate+DataFormat.formatRate(strInterestRate)+"/";
									strInterest=strInterest+DataFormat.formatRate(varInterest)+"/";
						    	  }
			    	 }
			    	  

			    }
				}
	//		days = getIntervalDays(sDate, getNextNDay(eDate,0), SETTConstant.InterestCalculationMode.TDAY30);
			
			
			


			fixedDepositAccountPayableInterestInfo = new FixedDepositAccountPayableInterestInfo();

			//赋值
			fixedDepositAccountPayableInterestInfo.setBalance(balance);
			fixedDepositAccountPayableInterestInfo.setDays(totalDays);
			fixedDepositAccountPayableInterestInfo.setEDate(eDate);
			fixedDepositAccountPayableInterestInfo.setSDate(sDate);
			fixedDepositAccountPayableInterestInfo.setInterest(interest);
			fixedDepositAccountPayableInterestInfo.setRate(interestRate);
			fixedDepositAccountPayableInterestInfo.setStrStartDate(strStartDate);
			fixedDepositAccountPayableInterestInfo.setStrEndDate(strEndDate);
			fixedDepositAccountPayableInterestInfo.setStrDays(strDays);
			fixedDepositAccountPayableInterestInfo.setStrRate(strRate);
			fixedDepositAccountPayableInterestInfo.setStrInterest(strInterest);

		}
		catch (IException ie)
		{
			throw ie;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(true, "无法找到对应的子账户或账户余额表记录,交易失败", null);
		}

		return fixedDepositAccountPayableInterestInfo;
	}
	
	public InterestsInfo  calculationLoanAccountCompoundInterest(Connection conn,
			long lOfficeID,
			long lCurrencyID,
			long accountID,
			long subLoanAccountID,
			Timestamp interestDate,
			Timestamp execDate,
			double RealCompoundInterest,
			long interestType) throws IException
	{
		//查询交易
		DailyAccountBalanceInfo dailyAccountBalanceInfo = null;
		Sett_DailyAccountBalanceDAO dailyBalanceDAO = new Sett_DailyAccountBalanceDAO(conn);
		sett_TransAccountDetailDAO transDetail = new sett_TransAccountDetailDAO(conn);
		UtilOperation utilOperation = new UtilOperation(conn);
		InterestsInfo returnInfo = new InterestsInfo();
		Collection coll=null;
		double tmpInterest                 = 0; //利息
		double tmpCompoundInterest         = 0; //复利
		double tmpOverDueInterest          = 0; //罚息
		double tmpArrearageInterest        = 0; //累计欠息		
		try
		{
			if (interestDate.before(execDate))
			{
				dailyAccountBalanceInfo = dailyBalanceDAO.findAllBySubAccountIDAndDate(subLoanAccountID, lOfficeID, lCurrencyID, getNextNDay(execDate, -1) );

				if (dailyAccountBalanceInfo == null)
				{
					throw new IException(true, "没有对应的账户余额表记录！，交易失败", null);
				}
			}
			tmpInterest                 = dailyAccountBalanceInfo.getInterest(); //利息
			tmpCompoundInterest         = dailyAccountBalanceInfo.getMcompoundinterest(); //复利
			tmpOverDueInterest          = dailyAccountBalanceInfo.getMforfeitinterest(); //罚息
			tmpArrearageInterest        = dailyAccountBalanceInfo.getAl_mArrearageInterest(); //累计欠息	
			InterestsInfo interestsInfo = null;
			InterestsInfo payinterestsInfo = null;
			try
			{
				interestsInfo = utilOperation.findPayInterestAmount(subLoanAccountID,execDate);
			}
			catch(Exception e)
			{
				throw new IException(e.getMessage());
			}
			 //查询交易
			payinterestsInfo = utilOperation.findAllClearCompoundInterset( subLoanAccountID, execDate);
				if(payinterestsInfo.getPaymcompoundinterest()>0&&interestsInfo!=null)
				{
					if(interestsInfo.getMcompoundinterest()-interestsInfo.getPaymcompoundinterest()>payinterestsInfo.getPaymcompoundinterest())
					{
						tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payinterestsInfo.getPaymcompoundinterest());
					}
					else if(interestsInfo.getMcompoundinterest()-interestsInfo.getPaymcompoundinterest()<payinterestsInfo.getPaymcompoundinterest()&&interestsInfo.getMcompoundinterest()>interestsInfo.getPaymcompoundinterest())
					{
						tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(interestsInfo.getMcompoundinterest(),interestsInfo.getPaymcompoundinterest()));
					}
					tmpCompoundInterest= UtilOperation.Arith.sub(UtilOperation.Arith.sub(tmpCompoundInterest,payinterestsInfo.getPaymcompoundinterest()),RealCompoundInterest);
				}
				if(payinterestsInfo.getPaymforfeitinterest()>0&&interestsInfo!=null)
				{
					if(interestsInfo.getMforfeitinterest()-interestsInfo.getPaymforfeitinterest()>payinterestsInfo.getPaymforfeitinterest())
					{
						tmpArrearageInterest =UtilOperation.Arith.sub( tmpArrearageInterest ,payinterestsInfo.getPaymforfeitinterest());
					}
					else if(interestsInfo.getMforfeitinterest()-interestsInfo.getPaymforfeitinterest()<payinterestsInfo.getPaymforfeitinterest()&&interestsInfo.getMforfeitinterest()>interestsInfo.getPaymforfeitinterest())
					{
						tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(interestsInfo.getMforfeitinterest(),interestsInfo.getPaymforfeitinterest()));
					}
				}
				if(payinterestsInfo.getPayminterest()>0&&interestsInfo!=null)
				{
					if(interestsInfo.getMinterest()-interestsInfo.getPayminterest()>payinterestsInfo.getPayminterest())
					{
						tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,payinterestsInfo.getPayminterest());
					}
					else if(interestsInfo.getMinterest()-interestsInfo.getPayminterest()<payinterestsInfo.getPayminterest()&&interestsInfo.getMinterest()>interestsInfo.getPayminterest())
					{
						tmpArrearageInterest = UtilOperation.Arith.sub(tmpArrearageInterest ,UtilOperation.Arith.sub(interestsInfo.getMinterest(),interestsInfo.getPayminterest()));
					}
				}
			 if(tmpArrearageInterest<0)
			 {
			 	tmpArrearageInterest = 0;
			 }
			 returnInfo.setMcompoundinterest(tmpCompoundInterest);
			 returnInfo.setArrearageInterest(tmpArrearageInterest);
		}
		catch(Exception e)
		{
			throw new IException(e.getMessage());
		}
		
		return returnInfo;
	}
}
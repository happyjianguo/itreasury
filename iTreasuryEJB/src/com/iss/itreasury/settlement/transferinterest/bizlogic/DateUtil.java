/**
 * 
 */
package com.iss.itreasury.settlement.transferinterest.bizlogic;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.iss.itreasury.settlement.util.UtilOperation;


/**
 * @author xintan
 *
 */
public class DateUtil {

    /** 获得该日期指定天数之后的日期
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date after(Date dtDate, long lDays)
    {
        long lCurrentDate = 0;
        lCurrentDate = dtDate.getTime() + lDays * 24 * 60 * 60 * 1000;
        Date dtAfter = new Date(lCurrentDate);
        return dtAfter;
    }
    
    /**get next n day's timesamp
	 * @param n>0 means get furture day, else means get past day
	*/
	public static Timestamp getNextNDay(Timestamp currentDay, int n)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDay);
		calendar.add(Calendar.DATE, n);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
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
	public static long getIntervalDays(Timestamp sDate, Timestamp eDate, long intervalDaysFlag)
	{
		long resIntervalDays = -1;
		
		System.out.println("起息日：" + sDate.toString());
		System.out.println("止息日：" + eDate.toString());
		
		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(sDate);
		
		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(eDate);

		if (sDate.after(eDate))
		{
			//throw new IException("起息日晚于止息日，交易失败");
		}

		//计算实际间隔日期
		if (intervalDaysFlag == InterestCalculationMode.FACTDAY)
		{
			resIntervalDays = getFactIntervalDays(sCalendar, eCalendar);
		}
		//30天
		else if (intervalDaysFlag == InterestCalculationMode.TDAY30)
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

		System.out.println("天数：" + resIntervalDays);
		return resIntervalDays;
	}
	
	/**
	 * 开始计算实际间隔日期
	 * @param sG 起息日 GregorianCalendar
	 * @param eG 结息日 GregorianCalendar
	 * @return
	 */
	private static long getFactIntervalDays(GregorianCalendar sG, GregorianCalendar eG)
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
	
	/**
	 * 2007/06/18 计息方式类型选择
	 * @author leiyang
	 *
	 */
	public static class InterestCalculationMode 
	{
		public static final long FACTDAY  = 1; //按实际天数处理
		public static final long TDAY30  = 2; //按30天处理
		
		public static final String getName(long lCode) 
		{
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) FACTDAY:
				strReturn = "按实际天数处理";
				break;
			case (int) TDAY30:
				strReturn = "按30天处理";
				break;
			}
			return strReturn;
		}
	}
	
	/** 利率类型 */
	public static class InterestRateTypeFlag 
	{
		public static final long DAY = 3; // 之上

		public static final long MONTH = 2; // 之下

		public static final long YEAR = 1; // 之下
	}
	
	/** 年日利率转换基数标志 */
	public static class InterestRateDaysFlag 
	{
		public static final long DAYS_360 = 1; // 按每年360天

		public static final long DAYS_365 = 2; // 按每年365天

		public static final long DAYS_366 = 3; // 按每年366天

		static public long getDaysByFlag(long interestRateDaysFlag) 
		{
			if (interestRateDaysFlag == DAYS_360)
				return 360;
			else if (interestRateDaysFlag == DAYS_365)
				return 365;
			else
				return 366;
		}
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
	public static double caculateInterest (
		double baseAmount,
		Timestamp startInterestDate,
		Timestamp endInterestDate,
		long intervalDaysFlag,
		double interestRate,
		long interestRateTypeFlag,
		long interestRateDaysFlag)
	{
		double interest = 0.0;
		
		System.out.println("----------开始计算利息------------"+startInterestDate);
		System.out.println("----------开始计算利息------------"+endInterestDate);
		System.out.println("----------开始计算利息------------"+intervalDaysFlag);
		
		long intervalDays = getIntervalDays(startInterestDate, endInterestDate, intervalDaysFlag);
		
		System.out.println("----------开始计算利息------------"+baseAmount);
		System.out.println("----------开始计算利息------------"+interestRate);
		System.out.println("----------开始计算利息------------"+intervalDays);
		
		if (interestRateTypeFlag == InterestRateTypeFlag.YEAR)
		{
			if (interestRateDaysFlag == InterestRateDaysFlag.DAYS_360)
				interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 36000);
			if (interestRateDaysFlag == InterestRateDaysFlag.DAYS_365)
				interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 36500);
			if (interestRateDaysFlag == InterestRateDaysFlag.DAYS_366)
				interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 36600);

		}
		else if (interestRateTypeFlag == InterestRateTypeFlag.MONTH)
		{
			interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 30000);
		}
		else
		{
			interest = UtilOperation.Arith.div(UtilOperation.Arith.mul(UtilOperation.Arith.mul(baseAmount, interestRate), intervalDays), 10000);
		}
		
		return interest;
	}
	
}
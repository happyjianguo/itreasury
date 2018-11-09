/*
 * IDate.java
 *
 * Created on 2001年12月17日, 上午9:22
 */
package com.iss.itreasury.util;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
/**
 *
 * @version
 */
public class IDate
{
    /** Creates new IDate */
    public IDate()
    {
    }
    /** 获得该日期指定天数之前的日期
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date before(Date dtDate, long lDays)
    {
        long lCurrentDate = 0;
        lCurrentDate = dtDate.getTime() - lDays * 24 * 60 * 60 * 1000;
        Date dtBefor = new Date(lCurrentDate);
        return dtBefor;
    }
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
    /** 获得两个日期之间有多少天
     * @param dtBeginDate
     * @param dtEndDate
     * @return
     */
    public static long intervalDays(Date dtBeginDate, Date dtEndDate)
    {
//        long lInterval = 0;
//        lInterval = dtEndDate.getTime() - dtBeginDate.getTime();
//        lInterval = lInterval / (24 * 60 * 60 * 1000);
//        return lInterval;

		GregorianCalendar gc1, gc2;

		gc1 = new GregorianCalendar();
		gc1.setTime(dtBeginDate);
	    gc2 = new GregorianCalendar();
	    gc2.setTime(dtEndDate);


		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);


		long lInterval = 0;
		lInterval = gc2.getTime().getTime() - gc1.getTime().getTime();
		lInterval = lInterval / (24 * 60 * 60 * 1000);
		return lInterval;


    }
    /** 获得该日期指定天数之后的日期
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date after(Timestamp tsDate, long lDays)
    {
        long lCurrentDate = 0;
        lCurrentDate = tsDate.getTime() + lDays * 24 * 60 * 60 * 1000;
        Date dtAfter = new Date(lCurrentDate);
        return dtAfter;
    }
    /** 获得该日期指定天数之前的日期
     * @param dtDate
     * @param lDays
     * @return 返回日期
     */
    public static Date before(Timestamp tsDate, long lDays)
    {
        long lCurrentDate = 0;
        lCurrentDate = tsDate.getTime() - lDays * 24 * 60 * 60 * 1000;
        Date dtBefor = new Date(lCurrentDate);
        return dtBefor;
    }
    /**
     * 此处插入方法说明。
     * 创建日期：(2002-3-28 12:26:56)
     * @return java.lang.String
     * @param dtVar java.sql.Date
     * @param lType long
     * lType = 1  format  is  yyyy.MM.dd
     * lType = 2  format  is  yyyy.MM.dd hh:mm:ss
     * lType = 3  format  is  yyyy.MM.dd hh:mm a  //// am/pm
     * lType = 4  format  is  yyyy.MM.dd
     * lType = 1  format  is  yyyy.MM.dd
     * lType = 1  format  is  yyyy.MM.dd
     */
    public static String formatDate(Date dtVar, long lType)
    {
        // Format the current time.
        String dateString = "";
        try
        {
            SimpleDateFormat formatter = null;
            switch ((int) lType)
            {
                case 1 :
                    formatter = new SimpleDateFormat("yyyy.MM.dd");
                    break;
                case 2 :
                    formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                    break;
                case 3 :
                    formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
                    break;
                default :
                    formatter = new SimpleDateFormat("yyyy.MM.dd");
                    break;
            }
            dateString = formatter.format(dtVar);
        }
        catch (Exception e)
        {
            dateString = "";
        }
        return dateString;
    }
    /**
     * 此处插入方法说明。
     * 创建日期：(2002-3-28 12:26:56)
     * @return java.lang.String
     * @param dtVar java.sql.Date
     * @param lType long
     * lType = 1  format  is  yyyy.MM.dd
     * lType = 2  format  is  yyyy.MM.dd hh:mm:ss
     * lType = 3  format  is  yyyy.MM.dd hh:mm a  //// am/pm
     * lType = 4  format  is  yyyy.MM.dd
     * lType = 1  format  is  yyyy.MM.dd
     * lType = 1  format  is  yyyy.MM.dd
     */
    public static String formatDate(Timestamp tsVar, long lType)
    {
        // Format the current time.
        String dateString = "";
        try
        {
            SimpleDateFormat formatter = null;
            switch ((int) lType)
            {
                case 1 :
                    formatter = new SimpleDateFormat("yyyy.MM.dd");
                    break;
                case 2 :
                    formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
                    break;
                case 3 :
                    formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
                    break;
                default :
                    formatter = new SimpleDateFormat("yyyy.MM.dd");
                    break;
            }
            dateString = formatter.format(tsVar);
        }
        catch (Exception e)
        {
            dateString = "";
        }
        return dateString;
    }
    /** 获得两个日期之间有多少天
     * @param dtBeginDate
     * @param dtEndDate
     * @return
     */
    public static long intervalDays(Timestamp tsBeginDate, Timestamp tsEndDate)
    {
        long lInterval = 0;
        lInterval = tsEndDate.getTime() - tsBeginDate.getTime();
        lInterval = lInterval / (24 * 60 * 60 * 1000);
        return lInterval;
    }
    public static HashMap getDaysPerMonth()
    {
        HashMap hm = new HashMap();
        hm.put("1", "31");
        hm.put("2", "28");
        hm.put("3", "31");
        hm.put("4", "30");
        hm.put("5", "31");
        hm.put("6", "30");
        hm.put("7", "31");
        hm.put("8", "31");
        hm.put("9", "30");
        hm.put("10", "31");
        hm.put("11", "30");
        hm.put("12", "31");
        return hm;
    }
    /**
     * 获得给定月份的天数。
     * @param nYear
     * @param nMonth
     * @return
     */
    public static int getDaysOfMonth(int nYear, int nMonth)
    {
        int nDay = Integer.parseInt(getDaysPerMonth().get(String.valueOf(nMonth)).toString());
        if (nMonth == 2)
        {
            if ((nYear % 4 == 0) && (nYear % 100 > 0)) // 闰年
                nDay = 29;
        }
        return nDay;
       
    }
    /**
	 * 比较两个日期是否相等，按照年，月，日比较。
	 * @param dtOne
	 * @param dtOther
	 * @return int the value 0 if the argument Date is equal to this other Date; a value less than 0 if this Date is
	 * before the other Date; and a value greater than 0 if this Date is after the other Date.
	 */
	public static int compareDate(java.util.Date dtOne, java.util.Date dtOther)
	{
		int result = 0;

		if (dtOne != null && dtOther != null)
		{
			Calendar calOne = Calendar.getInstance();
			calOne.clear();
			calOne.setTime(dtOne);

			Calendar calOther = Calendar.getInstance();
			calOther.clear();
			calOther.setTime(dtOther);

			if (calOne.get(Calendar.YEAR) == calOther.get(Calendar.YEAR))
			{
				if (calOne.get(Calendar.MONTH) == calOther.get(Calendar.MONTH))
				{
					if (calOne.get(Calendar.DATE)
						== calOther.get(Calendar.DATE))
					{
						//日期相等
						result = 0;
					}
					else if (
						calOne.get(Calendar.DATE)
							> calOther.get(Calendar.DATE))
					{
						//第一个晚于第二个
						result = 1;
					}
					else if (
						calOne.get(Calendar.DATE)
							< calOther.get(Calendar.DATE))
					{
						//第一个早于第二个
						result = -1;
					}
				}
				else if (
					calOne.get(Calendar.MONTH) > calOther.get(Calendar.MONTH))
				{
					//第一个晚于第二个
					result = 1;
				}
				else if (
					calOne.get(Calendar.MONTH) < calOther.get(Calendar.MONTH))
				{
					//第一个早于第二个
					result = -1;
				}
			}
			else if (calOne.get(Calendar.YEAR) > calOther.get(Calendar.YEAR))
			{
				//第一个晚于第二个
				result = 1;
			}
			else if (calOne.get(Calendar.YEAR) < calOther.get(Calendar.YEAR))
			{
				//第一个早于第二个
				result = -1;
			}

		}

		return result;
	}
}
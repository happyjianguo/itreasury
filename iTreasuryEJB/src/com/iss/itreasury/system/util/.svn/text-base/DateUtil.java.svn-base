/*
 * Created on 2005-5-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.setting.bizlogic.SysCalendarBean;
import com.iss.itreasury.system.setting.dataentity.SysCalendarInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DateUtil
{
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
		GregorianCalendar gc1, gc2;

		gc1 = new GregorianCalendar();
		gc1.setTime(dtBeginDate);
	    gc2 = new GregorianCalendar();
	    gc2.setTime(dtEndDate);

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc1.clear(Calendar.HOUR);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.HOUR);


		long lInterval = 0;
		lInterval = gc2.getTime().getTime() - gc1.getTime().getTime();
		lInterval = lInterval / (24 * 60 * 60 * 1000);
		return lInterval;
    }

    private static HashMap getDaysPerMonth()
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
    
    /**
     * 比较两个时间是否相等。比较年，月，日，时，分，秒。
     * 
     * @param dateOne
     * @param dateOther
     * @return 当dateOne和dateOther相等时，返回0；
     *         当dateOne在dateOther之前时，返回-1；
     *         当dateOne在dateOther之后时，返回1。
     *         当dateOne和dateOther都为null时，返回0。
     *         当dateOne为null，dateOther不为null时，返回-1，反之，返回1。
     */
    public static int compareDateTime(Date dateOne, Date dateOther)
    {
        int result = 0;
        
        if(dateOne!=null && dateOther!=null)
        {
            long lDateOne = dateOne.getTime()/1000*1000;//去掉时间的毫秒位
            long lDateOther = dateOther.getTime()/1000*1000;
            
            long lResult = lDateOne-lDateOther;
            
            if(lResult>0) result=1;
            else if(lResult<0) result = -1;
            
        }else if(dateOne==null && dateOther!=null)
        {
            return -1;
        }else if(dateOne!=null && dateOther==null){
            return 1;
        }
        return result;
    }
	/**
	 * 得到下几天
	 * 
	 * @param dtDate
	 *            日期
	 */
	static public Date getNextDate ( Date dtDate ,
			int nDay )
	{
		if (null == dtDate)
			return null ;		
		GregorianCalendar calendar = new GregorianCalendar ( ) ;
		calendar.setTime ( dtDate ) ;
		calendar.add ( Calendar.DATE , nDay ) ;
		return calendar.getTime() ;		
	}
	/**
	 * 得到下几月
	 * 
	 * @param dtDate
	 *            日期
	 */
	static public Date getNextMonth (Date dtDate,
			int nMonth )
	{
		if (null == dtDate)
			return null ;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime (dtDate);		
		return getDateTime ( calendar.get ( Calendar.YEAR ) , calendar
				.get ( Calendar.MONTH )
				+ 1 + nMonth , calendar.get ( Calendar.DATE ) , 0 , 0 , 0 ) ;
	}
	/**
	 * construct a Date through year,month,day,hour,minute,second
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	static public Date getDateTime ( int year , int month ,
			int day , int hour , int minute , int second )
	{
		java.util.Date dt = null ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.set ( year , month - 1 , day , hour , minute , second ) ;
		return calendar.getTime ( ) ;		
	}
	/**
	 * 得到当月第一天
	 * @param dtDate
	 * @return
	 */
	static public Date getFirstDateOfMonth ( Date dtDate )
	{
		if (null == dtDate)
			return null ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.setTime ( dtDate ) ;
		return getDateTime ( calendar.get ( Calendar.YEAR ) , calendar
				.get ( Calendar.MONTH ) + 1 ,
				1 , 0 , 0 , 0 ) ;
	}
	/**
	 * 得到当月最后一天
	 * @param dtDate
	 * @return
	 */
	static public Date getLastDateOfMonth (Date dtDate)
	{
		if (null == dtDate)
			return null ;
		Date firstday=getFirstDateOfMonth(dtDate);
		Date nextMonth=getNextMonth(firstday,1);
		return getPreviousDate(nextMonth);
	}
	/**
	 * 得到上一天
	 * 
	 * @param dtDate
	 *            日期
	 */
	static public Date getPreviousDate ( Date dtDate )
	{
		if (null == dtDate)
			return null ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.setTime ( dtDate ) ;		
		return getDateTime ( calendar.get ( Calendar.YEAR ) , calendar
				.get ( Calendar.MONTH ) + 1 ,
				calendar.get ( Calendar.DATE ) - 1 , 0 , 0 , 0 ) ;
	}	
	/**
	 * 得到季的最后一天
	 * 
	 * @param dtDate
	 *            日期
	 */
	static public Date getLastDateOfSeason(Date dtDate)
	{
		if (null == dtDate)
			return null ;
		Date dtRes = null;
        int nMonth = getMonth(dtDate);
        if(nMonth <= Calendar.MARCH)//第一季
        {
        	dtRes = getLastDateOfMonth(getNextMonth(dtDate,Calendar.MARCH-nMonth));
        }        
        else
        {
        	if(nMonth <= Calendar.JUNE)//第二季
        	{
        		dtRes = getLastDateOfMonth(getNextMonth(dtDate,Calendar.JUNE-nMonth));
        	}
        	else
        	{
        		if(nMonth <= Calendar.SEPTEMBER)//第三季
        		{
        			dtRes = getLastDateOfMonth(getNextMonth(dtDate,Calendar.SEPTEMBER-nMonth));
        		}
        		else//第四季
        		{
        			dtRes = getLastDateOfMonth(getNextMonth(dtDate,Calendar.DECEMBER-nMonth));
        		}
        	}
        }
		return dtRes;
	}	
	/**
	 * 得到半年的最后一天
	 * 
	 * @param dtDate
	 *            日期
	 */
	static public Date getLastDateOfSemiYear(Date dtDate)
	{
		if (null == dtDate)
			return null ;
		Date dtRes = null;
        int nMonth = getMonth(dtDate);
        if(nMonth <= Calendar.JUNE)//第一个半年
        {
        	dtRes = getLastDateOfMonth(getNextMonth(dtDate,Calendar.JUNE-nMonth));
        }        
        else//第二个半年
        {
        	dtRes = getLastDateOfMonth(getNextMonth(dtDate,Calendar.DECEMBER-nMonth));  	
        }
		return dtRes;
	}	
	/**
	 * 得到一个日期的月份,用0-11表示
	 */
	public static int getMonth(Date dtDate )
	{
		if (null == dtDate)
			return -1 ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.setTime ( dtDate ) ;
		return calendar.get ( Calendar.MONTH );
	}
	/**
	 * 得到一个日期号数，比如2005-08-11，返回11
	 */
	public static int getDay(Date dtDate )
	{
		if (null == dtDate)
			return -1 ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.setTime ( dtDate ) ;
		return calendar.get ( Calendar.DATE );
	}
	
	
	static public boolean isOffWorkDay(String currentDate) throws ITreasuryDAOException, SQLException, ParseException{
		SysCalendarBean sysCalendarDAOAll = new SysCalendarBean();
		Collection collectionAll = sysCalendarDAOAll.getAll();
		Iterator iteratorAll = collectionAll.iterator();
		SysCalendarInfo sysCalendarInfoAll = new SysCalendarInfo();
		int t=0;
		String offWorkDayAll = "";
		while (iteratorAll.hasNext())
		{
			sysCalendarInfoAll = (SysCalendarInfo) iteratorAll.next();
			String offWorkDay = sysCalendarInfoAll.getDayId();
			offWorkDayAll = offWorkDayAll+offWorkDay+":";
			t++;
		}
		
		
		
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
            Date cd = sdf.parse(currentDate);   

        /*
		if(offWorkDayAll.contains(currentDate)&&!isWeedned(cd)){
			return true;
		}
		if(!offWorkDayAll.contains(currentDate)&&isWeedned(cd)){
			return true;
		}*/
            if(offWorkDayAll.indexOf(currentDate)>=0&&!isWeedned(cd)){
            	System.out.print("---------gggggggggggggggg-------------");
    			return true;
    		}
    		if(offWorkDayAll.indexOf(currentDate)<0&&isWeedned(cd)){
    			System.out.print("---------hhhhhhhhhhhhh-------------");
    			return true; 
    		}
		
		return false;
	}
	
	static public boolean isWeedned(Date dtDate)
	{
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(dtDate);		
		int i =  calendar.get(Calendar.DAY_OF_WEEK);
		
		if(i==1||i==7){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据传入日期判断当前是星期几
	 * @return 
	 * Calendar.SUNDAY
	 * Calendar.MONDAY
	 * Calendar.TUESDAY
	 * ......
	 * Calendar.SATURDAY
	 * */
	static public int getWeekDay(Date dtDate)
	{
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(dtDate);		
		return calendar.get(Calendar.DAY_OF_WEEK);		
	}
	/**
	 * 按周期分段，统计报表里要用到
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param nType	 *
	 * @return Date[] 依次记录了每个时间段的开始日期和结束日期            
	 */
	public static final int BYWEEK = 1;//周
	public static final int BYMONTH = 2;//月
	public static final int BYSEASON = 3;//季
	public static final int BYSEMIYEAR = 4;//半年
	static public Date[] splitIntoSections(Date beginDate,Date endDate,int nType)
	{
		Date[] timeTable= null;
		ArrayList lstTimeTable = new ArrayList();
    	Date dtTemp = null;
		switch (nType)
        {
            case BYWEEK:
            	dtTemp = beginDate;
            	//第一周
            	lstTimeTable.add(dtTemp);//周的第一天
            	int nWeekDay = getWeekDay(dtTemp);	                
                dtTemp = getNextDate(dtTemp,Calendar.SATURDAY-nWeekDay);
                if(dtTemp.getTime()>=endDate.getTime())
                {
                	dtTemp = endDate;
                }
                lstTimeTable.add(dtTemp);//周的最后一天
                //下一周
                while(dtTemp.getTime()<endDate.getTime())
            	{   
                	dtTemp = getNextDate(dtTemp,1);
                	lstTimeTable.add(dtTemp);//周的第一天
	                nWeekDay = getWeekDay(dtTemp);	                
	                dtTemp = getNextDate(dtTemp,Calendar.SATURDAY-nWeekDay);
	                if(dtTemp.getTime()>=endDate.getTime())
	                {
	                	dtTemp = endDate;
	                }
	                lstTimeTable.add(dtTemp);//周的最后一天
            	};                
                break;
            case BYMONTH:
            	dtTemp = beginDate;
            	//第一月
            	lstTimeTable.add(dtTemp);//月的第一天      		                
                dtTemp = getLastDateOfMonth(dtTemp);
                if(dtTemp.getTime()>=endDate.getTime())
                {
                	dtTemp = endDate;
                }
                lstTimeTable.add(dtTemp);//月的最后一天
                //下一个月
                while(dtTemp.getTime()<endDate.getTime())
            	{   
                	dtTemp = getNextDate(dtTemp,1);
                	lstTimeTable.add(dtTemp);//月的第一天
                	dtTemp = getLastDateOfMonth(dtTemp);
	                if(dtTemp.getTime()>=endDate.getTime())
	                {
	                	dtTemp = endDate;
	                }
	                lstTimeTable.add(dtTemp);//月的最后一天
            	};    
                break;
            case BYSEASON:
            	dtTemp = beginDate;
            	//第一季
            	lstTimeTable.add(dtTemp);//季的第一天      		                
                dtTemp = getLastDateOfSeason(dtTemp);
                if(dtTemp.getTime()>=endDate.getTime())
                {
                	dtTemp = endDate;
                }
                lstTimeTable.add(dtTemp);//季的最后一天
                //下一个季
                while(dtTemp.getTime()<endDate.getTime())
            	{   
                	dtTemp = getNextDate(dtTemp,1);
                	lstTimeTable.add(dtTemp);//季的第一天
                	dtTemp = getLastDateOfSeason(dtTemp);
	                if(dtTemp.getTime()>=endDate.getTime())
	                {
	                	dtTemp = endDate;
	                }
	                lstTimeTable.add(dtTemp);//季的最后一天
            	};    
                break;      
            case BYSEMIYEAR:            	
            	dtTemp = beginDate;
            	//第一个半年
            	lstTimeTable.add(dtTemp);//半年的第一天      		                
                dtTemp = getLastDateOfSemiYear(dtTemp);
                if(dtTemp.getTime()>=endDate.getTime())
                {
                	dtTemp = endDate;
                }
                lstTimeTable.add(dtTemp);//半年的最后一天
                //下一个半年
                while(dtTemp.getTime()<endDate.getTime())
            	{   
                	dtTemp = getNextDate(dtTemp,1);
                	lstTimeTable.add(dtTemp);//半年的第一天
                	dtTemp = getLastDateOfSemiYear(dtTemp);
	                if(dtTemp.getTime()>=endDate.getTime())
	                {
	                	dtTemp = endDate;
	                }
	                lstTimeTable.add(dtTemp);//半年的最后一天
            	};    
                break;
        }
		timeTable = new Date[0];
		timeTable =
			(Date[]) lstTimeTable.toArray(
					timeTable);		
		return timeTable;
	}
	/**
	 * 获取地区的当地时间，参照UTC时间
	 * @param regionUTCTime地区的UTC时差，单位是分钟
	 * @return
	 */
	public static Date getLocationDateTime(long curUTCTime,long regionUTCTime) throws Exception
	{
		return new Date(curUTCTime + regionUTCTime*60*1000);
	}
	/**
	 * 根据传入日期，获取节假日天数
	 * @param myDate
	 * @return
	 * @throws ParseException
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public static long getAddedDays(String myDate) throws ParseException, ITreasuryDAOException, SQLException{
		long addDaysCounter = 0;
		String temp = myDate;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        Date cd = sdf.parse(temp);   
        
        
		while(DateUtil.isOffWorkDay(temp)){
			
			temp =  sdf.format(DateUtil.getNextDate(cd,1));
			cd = sdf.parse(temp);
			
			addDaysCounter++;
		}
		return addDaysCounter;
	}
	
	public static void main(String[] args)
	{
		Date date1 = null;
		Date date2 = null;
		try
		{   
			//new DateUtil().isOffWorkDay();
//			date1 = DataFormat.parseDate("2004-6-6",1);
			date2 = DataFormat.parseDate("2005-6-5",1);
			//new DateUtil().getAddedDays("2010-10-01");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
//		java.util.Collection temp = new java.util.ArrayList();
//		temp.add(date3);
//
//		temp.add(date2);
//		temp.add(date1);
//		Date[] results = new Date[0];
//		results =
//			(Date[]) temp.toArray(
//				results);
//		
//		for(int i=0;i<results.length;i++)
//		{
//			System.out.println(results[i]);
//		}
//		System.out.println("nextDay:"+getNextDate(date1,0));
//		System.out.println("nextMonth:"+getNextMonth(date1,1));
//		System.out.println("firstDayOfMonth:"+getFirstDateOfMonth(date1));
//		System.out.println("lastDayOfMonth:"+getLastDateOfMonth(date1));
//		System.out.println("week:"+getWeekDay(date1));
//		splitIntoSections(date1,date2,BYSEASON);	
        System.out.println(compareDateTime(date1, date2));
	}
}

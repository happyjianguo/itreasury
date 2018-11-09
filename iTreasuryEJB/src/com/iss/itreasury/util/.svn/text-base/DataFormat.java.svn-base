/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * @author yiwang
 */
/**
 * @author xintan
 *
 */
public class DataFormat {
	/*******************************************************************************************************************
	 * Settlement DateFormat********************** *********************************************************************
	 * Modify by yychen 2003-09-22 *********************************************************************
	 ******************************************************************************************************************/
	/**
	 * 格式化数字，例如：12345转化为12,345
	 * 
	 * @param dValue
	 *            被格式化的数值
	 * @param iScale
	 *            小数点后保留位数,不足补0
	 * @return
	 */
	public static String formatNumber(double dValue, int iScale) {
		DecimalFormat df = null;
		StringBuffer sPattern = new StringBuffer(",##0");
		if (iScale > 0) {
			sPattern.append(".");
			for (int i = 0; i < iScale; i++) {
				sPattern.append("0");
			}
		}
		df = new DecimalFormat(sPattern.toString());
		return df.format(dValue);
	}

	public static String formatNumber(long lValue) {
		return formatNumber((double) lValue, 0);
	}

	/**
	 * 解析格式化的字符串，转化为数值，例如：12,345.00转化为12345
	 * 
	 * @param text
	 *            被格式化的数值
	 * @return
	 */
	public static double parseNumber(String text) {
		int index = text.indexOf(",");
		String sbNumber = "";
		while (index != -1) {
			sbNumber += text.substring(0, index);
			text = text.substring(index + 1, text.length());
			index = text.indexOf(",");
		}
		sbNumber += text;
		return Double.parseDouble(sbNumber);
	}

	public static long parseLong(String text) {
		int index = text.indexOf(",");
		String sbNumber = "";
		while (index != -1) {
			sbNumber += text.substring(0, index);
			text = text.substring(index + 1, text.length());
			index = text.indexOf(",");
		}
		sbNumber += text;
		System.out.println(sbNumber);
		return Long.parseLong(sbNumber);
	}

	/**
	 * 格式化数字，例如：将5转化为4位字符，则得到0005
	 * 
	 * @param dValue
	 *            被格式化的数值
	 * @param nWidth
	 *            需要转换的位数
	 * @return
	 */
	public static String formatInt(long lValue, int nWidth) {
		String strReturn = "" + lValue;
		int initLength = strReturn.length();
		for (int i = nWidth; i > initLength; i--) {
			strReturn = "0" + strReturn;
		}
		return strReturn;
	}

	/**
	 * 格式化数字，例如：将5转化为4位字符，则得到0005
	 * 
	 * @param dValue
	 *            被格式化的数字字符串
	 * @return
	 * 
	 * Created by liliang at 2012-7-18
	 */
	public static String formatInt_1(String sValue) {
		String strReturn = sValue;
		if(strReturn!=null && !strReturn.equals(""))
		{				
			int initLength = strReturn.length();
			for (int i = 4; i > initLength; i--) {
				strReturn = "0" + strReturn;
			}
		}
		else
		{
			strReturn = "0001";				
		}
		return strReturn;
	}
	
	/**
	 * format a int value int a string with width and default group width is 3..
	 * 
	 * @param nValue
	 *            the value to be formatted.
	 * @param nWidth
	 * @param bZeroPreffixed
	 *            indicate that whether the number preffixed with zero , if the real length of result string is less
	 *            than nWidth.
	 * @return return a formmate string depend on request format.
	 */
	public static String formatInt(long lValue, int nWidth, boolean bZeroPreffixed) {
		return formatInt(lValue, nWidth, bZeroPreffixed, 3);
	}

	/**
	 * format a int value int a string with spicified width.
	 * 
	 * @param nValue
	 *            the value to be formatted.
	 * @param nWidth
	 * @param bZeroPreffixed
	 *            indicate that whether the number preffixed with zero , if the real length of result string is less
	 *            than nWidth.
	 * @param nGroupWidth
	 *            group width is to be more than 0, 0 indicate no group.
	 * @return return a formmate string depend on request format.
	 */
	public static String formatInt(long lValue, int nWidth, boolean bZeroPreffixed, int nGroupWidth) {
		StringBuffer sbPattern = new StringBuffer();
		char chStub = bZeroPreffixed ? '0' : '#';
		for (int i = 0; i < nWidth; i++) {
			sbPattern.insert(0, chStub);
			if (nGroupWidth > 0 && i > 0 && (i % nGroupWidth) == 0) {
				sbPattern.insert(0, ',');
			}
		}
		DecimalFormat dfDouble = new DecimalFormat(sbPattern.toString());
		return dfDouble.format(lValue);
	}

	/**
	 * format a double into string .
	 * 
	 * @param dbValue
	 *            double value to be formatted.
	 * @param nWidth
	 *            total width of output string
	 * @param nFraction
	 *            fraction part' width, '.' is included.
	 * @param bZeroPreffixed
	 *            set true if preffix is to be '0', instead space is filled in to pack width.
	 * @param nGroupWidth
	 *            group width is to be more than 0, 0 indicate no group.
	 * @return formatted string.
	 */
	/*
	 * public static String formatDouble(double dfValue, int nWidth, int nFraction, boolean bZeroPreffixed, int
	 * nGroupWidth ) { StringBuffer sbPattern = new StringBuffer(); char chStub = bZeroPreffixed ? '0':'#'; for( int i =
	 * 0; i <nWidth-nFraction ;i++ ) { sbPattern.insert( 0 , chStub ) ; if( nGroupWidth>0 && i>0 && (i%nGroupWidth)==0 ) {
	 * sbPattern.insert( 0 , ',' ); } } sbPattern.append( '.' ) ; for( int i = 0 ; i < nFraction-1 ; i++ ) {
	 * sbPattern.append( '0' ); } DecimalFormat dfDouble = new DecimalFormat( sbPattern.toString() ); return
	 * dfDouble.format( dfValue );
	 */
	/**
	 * I wrote a wrong version of this Function, for compatability, I have to follow previos rule. that means, "specify
	 * nGroupWidth argument one less you want, nFraction one more as you want, specify bZeroPreffixed false if you want
	 * no zero preffixed, versa contra." <br>
	 * I offer my sorry for my mistake here, Ping Liu
	 */
	public static String formatDouble(double dfValue, int nWidth, int nFraction,
			boolean bZeroPreffixed, int nGroupWidth) {
		DecimalFormat dfDouble = new DecimalFormat();
		if (nGroupWidth > 0) {
			dfDouble.setGroupingSize(nGroupWidth + 1);
			dfDouble.setGroupingUsed(true);
		}
		if (bZeroPreffixed) {
			dfDouble.setMinimumIntegerDigits(1);
		} else {
			// dfDouble.setMinimumIntegerDigits( nWidth - nFraction )
		}
		dfDouble.setMaximumFractionDigits(nFraction - 1);
		dfDouble.setMinimumFractionDigits(nFraction - 1);
		return dfDouble.format(dfValue);
	}

	/**
	 * simple version of formatDobule. considerring no group, no zero preffix and variant width. the parameter sequense
	 * is just for avoiding name corrision with old deprecated function.
	 * 
	 * @param dbValue
	 *            double value to be formatted.
	 * @param nWidth
	 *            total width of output string
	 * @return formatted string.
	 */
	public static String formatDouble(int nFraction, double dfValue) {
		return formatDouble(dfValue, nFraction + 1, nFraction, false, 0);
	}

	/**
	 * 格式化字符串，将空字符串或null转换为"&nbsp;"
	 * 
	 * @param strData
	 *            需要格式化的字符串
	 * @return String
	 */
	public static String formatEmptyString(String strData) {
		if (strData == null || strData.length() == 0) {
			return "&nbsp;";
		} else {
			return strData;
		}
	}

	/**
	 * 格式化字符串，将空字符串或null转换为""
	 * 
	 * @param strData
	 *            需要格式化的字符串
	 * @return String
	 */
	public static String formatNullString(String strData) {
		if (strData == null || strData.length() == 0) {
			return "";
		} else {
			return strData;
		}
	}

	/**
	 * the following const is to define date format.
	 */
	public static final int FMT_DATE_YYYYMMDD = 1;

	public static final int FMT_DATE_YYYYMMDD_HHMMSS = 2;

	public static final int FMT_DATE_HHMMSS = 3;

	public static final int FMT_DATE_HHMM = 4;

	public static final int FMT_DATE_SPECIAL = 5;

	public static final int FMT_DATE_YYYYMMDDHHMMSS = 6;
	
	public static final int FMT_DATE_YYYY = 7;
	/**
	 * this function is to format date into a string @ param date the date to be formatted.
	 * 
	 * @param nFmt
	 *            FMT_DATE_YYYYMMDD to format string like 'yyyy-mm-dd' or to format string like 'yyyy-mm-dd hh:mm:ss'
	 * @return String
	 */
	public static String formatDate(Date date, int nFmt) {
		SimpleDateFormat fmtDate = new SimpleDateFormat();
		switch (nFmt) {
		default:
		case DataFormat.FMT_DATE_YYYYMMDD:
			fmtDate.applyPattern("yyyy-MM-dd");
			break;
		case DataFormat.FMT_DATE_YYYYMMDD_HHMMSS:
			fmtDate.applyPattern("yyyy-MM-dd HH:mm:ss");
			break;
		case DataFormat.FMT_DATE_HHMM:
			fmtDate.applyPattern("HH:mm");
			break;
		case DataFormat.FMT_DATE_HHMMSS:
			fmtDate.applyPattern("HH:mm:ss");
			break;
		case DataFormat.FMT_DATE_SPECIAL:
			fmtDate.applyPattern("yyyyMMdd");
			break;		
		case DataFormat.FMT_DATE_YYYYMMDDHHMMSS:
			fmtDate.applyPattern("yyyyMMddHHmmssSSS");
			break;
		case DataFormat.FMT_DATE_YYYY:
			fmtDate.applyPattern("yyyy");
			break;
		}
		return fmtDate.format(date);
	}

	/**
	 * parse date from string with specific format.
	 * 
	 * @param strDate
	 *            a date string
	 * @param nFmtDate
	 *            specific date string format defined in this class.
	 * @exception raise
	 *                ParseException, if string format dismathed.
	 * @return Date,
	 */
	public static Date parseDate(String strDate, int nFmtDate) throws Exception {
		SimpleDateFormat fmtDate = new SimpleDateFormat();
		switch (nFmtDate) {
		default:
		case DataFormat.FMT_DATE_YYYYMMDD:
			fmtDate.applyPattern("yyyy-MM-dd");
			break;
		case DataFormat.FMT_DATE_YYYYMMDD_HHMMSS:
			fmtDate.applyPattern("yyyy-MM-dd HH:mm:ss");
			break;
		case DataFormat.FMT_DATE_HHMM:
			fmtDate.applyPattern("HH:mm");
			break;
		case DataFormat.FMT_DATE_HHMMSS:
			fmtDate.applyPattern("HH:mm:ss");
			break;
		}
		return fmtDate.parse(strDate);
	}

	/**
	 * parse date from string with specific format.
	 * 
	 * @param strDate
	 *            a date string
	 * @param nFmtDate
	 *            specific date string format defined in this class.
	 * @exception raise
	 *                ParseException, if string format dismathed.
	 * @return Date,
	 */
	public static Date parseUtilDate(String strDate, int nFmtDate) throws Exception {
		SimpleDateFormat fmtDate = null;
		switch (nFmtDate) {
		default:
		case DataFormat.FMT_DATE_YYYYMMDD:
			fmtDate = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case DataFormat.FMT_DATE_YYYYMMDD_HHMMSS:
			fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		case DataFormat.FMT_DATE_HHMM:
			fmtDate = new SimpleDateFormat("HH:mm");
			break;
		case DataFormat.FMT_DATE_HHMMSS:
			fmtDate = new SimpleDateFormat("HH:mm:ss");
			break;
		}
		return fmtDate.parse(strDate);
	}

	/**
	 * get oracle database system time.
	 * 
	 * @param con
	 * @exception java.sql.SQLException
	 * @return
	 */
	static public java.sql.Timestamp getDateTime(Connection con) throws SQLException {
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.sql.Timestamp ts = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select sysdate from dual");
			// select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') from dual
			if (rs.next())
				ts = rs.getTimestamp(1);
			rs.close();
			stmt.close();
		} catch (SQLException sqe) {
			System.out.println("SQLExcepiton");
			sqe.printStackTrace();
			throw sqe;
		}
		return ts;
	}

	/**
	 * construct a timestamp through year,month,day,hour,minute,second
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	static public java.sql.Timestamp getDateTime(int year, int month, int day, int hour,
			int minute, int second) {
		java.sql.Timestamp ts = null;
		java.util.Date dt = null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hour, minute, second);
		dt = calendar.getTime();
		ts = new java.sql.Timestamp(dt.getTime());
		return ts;
	}

	/**
	 * 得到下一天
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getNextDate(java.sql.Timestamp tsDate) {
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		// return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) + 1,
		// calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
		// calendar.get(Calendar.SECOND));
		java.sql.Timestamp tsIntervalTime = getDateTime(calendar.get(Calendar.YEAR), calendar
				.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) + 1, 0, 0, 0);
		tsIntervalTime.setNanos(0);

		return tsIntervalTime;

	}

	/**
	 * 得到当月第一天
	 * 
	 * @param tsDate
	 * @return
	 */
	static public java.sql.Timestamp getFirstDateOfMonth(java.sql.Timestamp tsDate) {
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		return getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1, 0, 0,
				0);
	}

	/**
	 * 得到当月最后一天
	 * 
	 * @param tsDate
	 * @return
	 */
	static public java.sql.Timestamp getLastDateOfMonth(java.sql.Timestamp tsDate) {
		if (null == tsDate)
			return null;
		java.sql.Timestamp firstday = getFirstDateOfMonth(tsDate);
		java.sql.Timestamp nextMonth = getNextMonth(firstday, 1);
		return getPreviousDate(nextMonth);
	}

	/**
	 * 得到下一天
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getPreviousDate(java.sql.Timestamp tsDate) {
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		// return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) - 1,
		// calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
		// calendar.get(Calendar.SECOND));
		return getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar
				.get(Calendar.DATE) - 1, 0, 0, 0);
	}

	/**
	 * 得到下几天
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getNextDate(java.sql.Timestamp tsDate, int nDay) {
		if (null == tsDate)
			return null;
		// Comment and Replaced by Huang Ye
		// java.util.Calendar calendar = Calendar.getInstance();
		// calendar.setTime(tsDate);
		// //return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) +
		// nDay, calendar.get(Calendar.HOUR_OF_DAY),
		// calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		// return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) +
		// nDay, 0, 0, 0);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(tsDate);
		calendar.add(Calendar.DATE, nDay);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
	}

	/*
	 * 计算某日期所在的周期区间(包含起始日期和结束日期) @param tmValidStart 起始日期 @param lLengthPeriod 周期长度 @param tmDateCurrent 当前日期
	 * 
	 * @result "Start"-区间起始日期 "End"-区间结束日期
	 */
	public static Hashtable getDateSection(Timestamp tmValidStart, long lLengthPeriod,
			Timestamp tmDateCurrent) {

		// 定义变量
		Hashtable dtSection = new Hashtable();
		Timestamp tmStart = null;
		Timestamp tmEnd = null;

		// 计算起始日期，结束日期,从有效起始日期开始算起
		tmStart = tmValidStart;
		// 周期间隔天数
		lLengthPeriod = lLengthPeriod - 1;
		// 如果统计有效日期在业务日期之后，则不需要考虑这条记录
		if (!tmStart.after(tmDateCurrent) && lLengthPeriod >= 0) {

			// 计算结束日期
			tmEnd = DataFormat.getNextDate(tmStart, (int) lLengthPeriod);
			// 循环计算当前业务日期属于那个周期间
			while (tmEnd.before(tmDateCurrent)) {
				// 获取下一个起始日期
				tmStart = DataFormat.getNextDate(tmEnd, 1);
				tmEnd = DataFormat.getNextDate(tmStart, (int) lLengthPeriod);
			}

			// 存储计算结果
			dtSection.put("Start", tmStart);
			dtSection.put("End", tmEnd);
		}

		// 返回函数值
		return dtSection;

	}

	// 测试函数
	private static void test_getDateSection() {

		// 定义变量
		Timestamp tmValidStart = new Timestamp((new Date(6, 0, 1)).getTime());
		long lLengthPeriod = 4;
		Timestamp tmDateCurrent = new Timestamp((new Date(6, 0, 3)).getTime());

		Hashtable dtSection = null;
		Timestamp tmStart = null;
		Timestamp tmEnd = null;

		System.out.println("有效日期是：" + tmValidStart.toString());
		System.out.println("周期长度是：" + lLengthPeriod);
		System.out.println("当前日期是：" + tmDateCurrent.toString());

		dtSection = getDateSection(tmValidStart, lLengthPeriod, tmDateCurrent);
		tmStart = (Timestamp) dtSection.get("Start");
		tmEnd = (Timestamp) dtSection.get("End");
		System.out.println("计算所得的区间是：[" + DataFormat.getDateString(tmStart) + ","
				+ DataFormat.getDateString(tmEnd) + "].");

	}

	/**
	 * 得到下几年
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getNextYear(java.sql.Timestamp tsDate, int nYear) {
		if (null == tsDate)
			return null;
		// Comment and Replaced by Huang Ye
		// java.util.Calendar calendar = Calendar.getInstance();
		// calendar.setTime(tsDate);
		// //return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) +
		// nDay, calendar.get(Calendar.HOUR_OF_DAY),
		// calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		// return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) +
		// nDay, 0, 0, 0);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(tsDate);
		calendar.add(Calendar.YEAR, nYear);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());
	}

	/**
	 * 开始计算实际间隔日期 add by Forest
	 * 
	 * @return
	 */
	static public int getIntervalDays(Timestamp tsStart, Timestamp tsEnd) {
		int lIntervalDays = 0;

		if (tsStart != null) {
			// while (tsStart.compareTo(tsEnd) <=0)
			while (!tsStart.after(tsEnd))// changed by Huang ye
			{
				tsStart = getNextDate(tsStart);
				lIntervalDays++;
			}
		}
		return lIntervalDays;
	}

	/**
	 * 得到下几月
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getNextMonth(java.sql.Timestamp tsDate, int nMonth) {
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		// return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1 + nMonth,
		// calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY),
		// calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		return getDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1 + nMonth,
				calendar.get(Calendar.DATE), 0, 0, 0);
	}

	/**
	 * add by zwxiao 2010-06-28 重载getNextMonth方法
	 * 得到下几月
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getNewNextMonth(java.sql.Timestamp tsDate, int nMonth) {
		if (null == tsDate)
			return null;
		Timestamp endDate = null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		// return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1 + nMonth,
		// calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY),
		// calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		int year = tsDate.getYear() + 1900;
		int month = tsDate.getMonth() + 1;
		int day = tsDate.getDate();
		if (month + nMonth > 12) {
			year = (month + nMonth + 12) / 12 + year - 1;
			month = (month + nMonth + 12) % 12;
			if (month == 2) {
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
					if (day > 29) {
						day = 29;
					}
				} else {
					if (day > 28) {
						day = 28;
					}
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					day = 30;
				}
			}
		} else {
			month = (month + nMonth);
			if (month == 2) {
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
					if (day > 29) {
						day = 29;
					}
				} else {
					if (day > 28) {
						day = 28;
					}
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					day = 30;
				}
			}
		}
		endDate = DataFormat.getDateTime(year, month, day, 0, 0, 0);
		return endDate;
	}
	
	/**
	 * 得到传入日期的前或后的某个日期。
	 * 
	 * @param intDay,
	 *            为负，得到之前的日期。为正，得到之后的日期。
	 */
	public static java.sql.Date getPreviousOrNextDate(Date date, int intDay) {
		java.sql.Date dtTemp = null;
		Timestamp tsTemp = null;
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			tsTemp = getDateTime(calendar.get(1), calendar.get(2) + 1, calendar.get(5) + intDay,
					calendar.get(11), calendar.get(12), calendar.get(13));
			dtTemp = java.sql.Date.valueOf(getDateString(tsTemp));
			return dtTemp;
		}
	}

	/**
	 * 得到前几月(保证月末对月末，例如，3.31的前一个月对应日期为2.29或2.28) modify by Forest,20040517
	 * 
	 * @param tsDate
	 *            日期
	 */
	static public java.sql.Timestamp getPreviousMonth(java.sql.Timestamp tsDate, int nMonth) {
		if (null == tsDate)
			return null;
		Timestamp endDate = null;
		int year = tsDate.getYear() + 1900;
		int month = tsDate.getMonth() + 1;
		int day = tsDate.getDate();
		if (month - nMonth < 0) {
			year = (month - nMonth + 12) / 12 + year - 1;
			month = (month - nMonth + 12) % 12;
			if (month == 2) {
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
					if (day > 29) {
						day = 29;
					}
				} else {
					if (day > 28) {
						day = 28;
					}
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					day = 30;
				}
			}
		} else {
			month = (month - nMonth);
			if (month == 2) {
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
					if (day > 29) {
						day = 29;
					}
				} else {
					if (day > 28) {
						day = 28;
					}
				}
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30) {
					day = 30;
				}
			}
		}
		endDate = DataFormat.getDateTime(year, month, day, 0, 0, 0);
		return endDate;
	}

	/**
	 * 得到几年前的日期
	 */
	public static java.sql.Timestamp getPreviousYear(java.sql.Timestamp tsDate, int nYear) {
		if (null == tsDate)
			return null;
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(tsDate);
		// return getDateTime(calendar.get(Calendar.YEAR) - nYear,
		// calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE),
		// calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
		// calendar.get(Calendar.SECOND));
		return getDateTime(calendar.get(Calendar.YEAR) - nYear, calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DATE), 0, 0, 0);
	}

	/**
	 * @param con
	 * @exception java.sql.SQLException
	 * @return
	 */
	static public java.sql.Date getDate(Connection con) throws SQLException {
		String strDate = getDateString(getDateTime(con));
		return java.sql.Date.valueOf(strDate);
	}

	/**
	 * construct a Date through a string like "yyyy-mm-dd"
	 * 
	 * @param con
	 * @exception java.sql.SQLException
	 * @return
	 */
	static public java.sql.Date getDate(String strDate) throws SQLException {
		return java.sql.Date.valueOf(strDate);
	}

	// construct a timestamp through a string like "yyyy-mm-dd" or "yyyy-mm-dd
	// hh:mm:ss"
	/**
	 * @param sDt
	 * @return
	 */
	static public java.sql.Timestamp getDateTime(String sDt) {
		try {
			
			sDt = sDt.trim();
			String[] strDt = sDt.split("-");
			if(strDt!=null && strDt.length ==3){
				if(strDt[1].length()==1){
					strDt[1] = "0" + strDt[1];
				}
				if(strDt[2].length()==1){
					strDt[2] = "0" + strDt[2];
				}
				sDt = strDt[0]+"-"+strDt[1]+"-"+strDt[2];
			}
			
			return java.sql.Timestamp.valueOf(sDt); // sDt
			// format:yyyy-mm-dd
			// hh:mm:ss.fffffffff
		} catch (IllegalArgumentException iae) {
			sDt = sDt.trim() + " 00:00:00";
			try {
				return java.sql.Timestamp.valueOf(sDt);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * convert Timestamp to string "yyyy-mm-dd hh:mm:ss.fffffffff"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getDateTimeString(java.sql.Timestamp ts) {
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		/*
		 * return calendar.get ( Calendar.YEAR ) + "-" + (calendar.get ( Calendar.MONTH ) + 1) + "-" + calendar.get (
		 * Calendar.DATE ) + " " + calendar.get ( Calendar.HOUR_OF_DAY ) + ":" + calendar.get ( Calendar.MINUTE ) + ":" +
		 * calendar.get ( Calendar.SECOND ) ;
		 */

		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		String strHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		if (strHour.length() == 1) {
			strHour = "0" + strHour;
		}
		String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
		if (strMinute.length() == 1) {
			strMinute = "0" + strMinute;
		}
		String strSecond = String.valueOf(calendar.get(Calendar.SECOND));
		if (strSecond.length() == 1) {
			strSecond = "0" + strSecond;
		}
		return calendar.get(Calendar.YEAR) + "-" + strMonth + "-" + strDay + " " + strHour + ":"
				+ strMinute + ":" + strSecond;
		// return ts.toString();
	}

	/**
	 * convert Timestamp to string "yyyy-mm-dd hh:mm:ss.fffffffff"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getTimeString(java.sql.Timestamp ts) {
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
		// return ts.toString();
	}

	/**
	 * convert Timestamp to string "hh:mm:ss"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getFullTimeString(java.sql.Timestamp ts) {
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String temp1 = calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
				+ calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY) + "";
		String temp2 = calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE)
				: calendar.get(Calendar.MINUTE) + "";
		String temp3 = calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND)
				: calendar.get(Calendar.SECOND) + "";
		return temp1 + ":" + temp2 + ":" + temp3;
	}

	/**
	 * convert oracle's system time to string "yyyy-mm-dd hh:mm:ss.fffffffff"
	 * 
	 * @param con
	 * @exception java.sql.SQLException
	 * @return
	 */
	static public String getDateTimeString(Connection con) throws SQLException {
		return getDateTimeString(getDateTime(con)).toString();
	}

	/**
	 * convert Timestamp to string "yyyy-mm-dd"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getDateString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		return calendar.get(Calendar.YEAR) + "-" + strMonth + "-" + strDay;
	}

	/**
	 * convert Timestamp to string "mm/dd/yyyy"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getDateString1(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		return strMonth + "/" + strDay + "/" + calendar.get(Calendar.YEAR);
	}

	/**
	 * convert Timestamp to string "yyyy-mm-dd"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getChineseDateString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		return calendar.get(Calendar.YEAR) + "年" + strMonth + "月" + strDay + "日";
	}

	/**
	 * convert Timestamp to string "yyyy-mm-dd hh:mm:ss"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getChineseTimeString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		String strHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		if(strHour.length() == 1){
			strHour = "0" + strHour;
		}
		String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
		if(strMinute.length() == 1){
			strMinute = "0" + strMinute;
		}
		String strSecond = String.valueOf(calendar.get(Calendar.SECOND));
		if(strSecond.length() == 1){
			strSecond = "0" + strSecond;
		}
		return calendar.get(Calendar.YEAR) + "年" + strMonth + "月" + strDay + "日" + strHour + "时" + strMinute + "分" + strSecond + "秒";
	}	
	
	/**
	 * convert Timestamp to string "yyyy-mm-dd"
	 * 
	 * @param ts
	 * @return
	 */
	static public String getChineseMonthString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		return calendar.get(Calendar.YEAR) + "年" + strMonth + "月";
	}

	/**
	 * //将传入的数字变为中国大写数字
	 * 
	 * @param ts
	 * @return
	 */
	static public String getChineseNumberString(long lMonth) {
		if (lMonth < 10000)
			return getChineseNumBelowTenThousand(String.valueOf(lMonth));
		else
			return "";
	}

	static public String getChineseNumBelowTenThousand(String strNum) {
		Log.print("传入strNum=" + strNum);
		String strTmp = "";
		if (strNum.length() >= 1)
			strTmp = getChineseNumSingle(strNum.substring(strNum.length() - 1, strNum.length()));
		if (strNum.length() >= 2)
			strTmp = strTmp
					+ "十"
					+ getChineseNumSingle(strNum
							.substring(strNum.length() - 2, strNum.length() - 1));
		if (strNum.length() >= 3)
			strTmp = strTmp
					+ "百"
					+ getChineseNumSingle(strNum
							.substring(strNum.length() - 3, strNum.length() - 2));
		if (strNum.length() >= 4)
			strTmp = strTmp
					+ "千"
					+ getChineseNumSingle(strNum
							.substring(strNum.length() - 4, strNum.length() - 3));
		if (strTmp.equals(""))
			strTmp = "零";
		// Log.print("传出strNum=" + strTmp);
		return strTmp;
	}
	
	static public String getChineseNumBelowTenThousand(String strNum, int type) {
		Log.print("传入strNum=" + strNum);
		String strTmp = "";
		if (strNum.length() >= 1)
			strTmp = getChineseNumSingle(strNum.substring(strNum.length() - 1, strNum.length()));
		if (strNum.length() >= 2)
		{
			if(strNum.length()==2 && "1".equals(strNum.substring(0,1)))
			{
				strTmp = "十" + strTmp;
			}else{
				strTmp = getChineseNumSingle(strNum.substring(strNum.length() - 2, strNum.length() - 1))
						+ "十"
						+ strTmp;
			}
		}
		if (strNum.length() >= 3)
			strTmp = getChineseNumSingle(strNum
					.substring(strNum.length() - 3, strNum.length() - 2))
					+ "百"
					+ strTmp;
		if (strNum.length() >= 4)
			strTmp = getChineseNumSingle(strNum
					.substring(strNum.length() - 4, strNum.length() - 3))
					+ "千"
					+ strTmp;
		if (strTmp.equals(""))
			strTmp = "零";
		// Log.print("传出strNum=" + strTmp);
		return strTmp;
	}	

	static public String getChineseNumSingle(String strSingleNum) {
		String strTmpTmp = "";
		// Log.print("strSingleNum=" + strSingleNum);
		switch ((int) Long.parseLong(strSingleNum)) {
		case 0:
			strTmpTmp = "";
			break;
		case 1:
			strTmpTmp = "一";
			break;
		case 2:
			strTmpTmp = "二";
			break;
		case 3:
			strTmpTmp = "三";
			break;
		case 4:
			strTmpTmp = "四";
			break;
		case 5:
			strTmpTmp = "五";
			break;
		case 6:
			strTmpTmp = "六";
			break;
		case 7:
			strTmpTmp = "七";
			break;
		case 8:
			strTmpTmp = "八";
			break;
		case 9:
			strTmpTmp = "九";
			break;
		}
		return strTmpTmp;
	}

	/**
	 * convert oracle's system time to string "yyyy-mm-dd"
	 * 
	 * @param con
	 * @exception java.sql.SQLException
	 * @return
	 */
	static public String getDateString(Connection con) throws SQLException {
		return getDateString(getDateTime(con));
	}

	/**
	 * get Current Date String
	 * 
	 * @return String
	 */
	static public String getDateString() throws SQLException {
		java.util.Date dt = null;
		java.util.Calendar calendar = Calendar.getInstance();
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		return calendar.get(Calendar.YEAR) + "-" + strMonth + "-" + strDay;
	}

	/**
	 * 取与交割单编号符合的日期格式，例如：2002－10－21转换成021021
	 * 
	 * @return
	 */
	public static String getTransDateString(Timestamp ts) {
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		String strYear = (calendar.get(Calendar.YEAR) + "").substring(2, 4);
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) {
			strDay = "0" + strDay;
		}
		return strYear + strMonth + strDay;
	}

	/**
	 * 入库字符窜限制长度
	 * 
	 * @param strData
	 *            字符
	 * @param nLength
	 *            显示的长度
	 */
	public static String inLibString(String strData, int nLength) throws Exception {
		return subAsciiString(strData, nLength);
	}

	/**
	 * 将字符串的日期＋
	 */
	public static String addDate(String strTime, int lTime, String strType) throws SQLException {
		try {
			if (strTime == null)
				strTime = "";
			if (strTime.equals(""))
				return "";
			Calendar m = Calendar.getInstance();
			m.setTime(getDate(strTime));
			if (strType.equalsIgnoreCase("d"))
				m.add(5, lTime);
			else if (strType.equalsIgnoreCase("m"))
				m.add(2, lTime);
			else if (strType.equalsIgnoreCase("y"))
				m.add(1, lTime);
			return m.get(1) + "-" + (m.get(2) + 1) + "-" + m.get(5);
		} catch (Exception e) {
			throw new SQLException(e.toString());
		}
	}

	/**
	 * 计算开始日期后几天， 例如：开始日期为2003-09-05,如果计算后一天 因为2003-09-06、2003-09-07为休息日，所以返回2003-09-08
	 * 
	 * @param dtBase
	 *            开始日期
	 * @param lNext
	 *            开始日期后几天
	 * @return
	 */
	public static java.sql.Date getWorkDate(java.sql.Date dtBase, long lNext) {
		java.sql.Date dtDate = null;
		long lDay = 0l;
		Calendar clDate = Calendar.getInstance();
		clDate.setTime(dtBase);
		long lOneTime = 24 * 3600 * 1000l;
		if (lNext == 0) {
			lDay = clDate.get(Calendar.DAY_OF_WEEK);
			if (lDay == Calendar.SATURDAY || lDay == Calendar.SUNDAY) {
				lNext = 1;
			}
		}
		while (lNext != 0) {
			long lTime = clDate.getTime().getTime();
			if (lNext < 0) {
				lTime = lTime - lOneTime;
			} else {
				lTime = lTime + lOneTime;
			}
			clDate.setTime(new java.sql.Date(lTime));
			lDay = clDate.get(Calendar.DAY_OF_WEEK);
			// 如果不是周末
			if (lDay != Calendar.SATURDAY && lDay != Calendar.SUNDAY) {
				if (lNext < 0) {
					lNext++;
				} else {
					lNext--;
				}
			}
		}
		return new java.sql.Date(clDate.getTime().getTime());
	}

	/**
	 * 检验是否是工作日
	 * 
	 * @param dtDate
	 *            日期
	 * @return boolean
	 */
	public static boolean isWorkDate(java.sql.Date dtDate) {
		long lDay = 0;
		Calendar clDate = Calendar.getInstance();
		clDate.setTime(dtDate);
		lDay = clDate.get(Calendar.DAY_OF_WEEK);
		if (lDay == Calendar.SATURDAY || lDay == Calendar.SUNDAY) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 取购回日期
	 * 
	 * @param dtStart
	 *            成交日期
	 * @param lTerm
	 *            购回期限
	 * @return
	 */
	public static java.sql.Date getGHDate(java.sql.Date dtStart, long lTerm) {
		return getWorkDate(dtStart, lTerm);
	}

	/**
	 * 取资金占用天数
	 * 
	 * @param dtStart
	 *            成交日期
	 * @param dtEnd
	 *            购回日期
	 * @return
	 */
	public static long getOccupy(java.sql.Date dtStart, java.sql.Date dtEnd) {
		if (dtStart == null || dtEnd == null) {
			return -1;
		}
		// 取交易资金清算日
		java.sql.Date dtAccount = getWorkDate(dtStart, 1);
		// 取购回交易资金清算日
		java.sql.Date dtGHAmount = getWorkDate(dtEnd, 1);
		return getTime(dtAccount, dtGHAmount);
	}

	/**
	 * 计算工期,总天数减去周末时间， 例如：开始日期为2003-09-05结束日期为2003-09-08， 因为2003-09-06、2003-09-07为休息日，所以返回一天
	 * 
	 * @param dtBegin
	 *            开始时间
	 * @param dtEnd
	 *            结束时间
	 * @return
	 */
	public static long getWorkTime(java.sql.Date dtBegin, java.sql.Date dtEnd) {
		if (dtBegin == null || dtEnd == null) {
			return -1;
		}
		if (dtBegin.after(dtEnd)) {
			return -1;
		}
		// 总天数
		long lTotalTime = getTime(dtBegin, dtEnd);
		long lRelaxTime = 0; // 休息天数
		Calendar temp = Calendar.getInstance();
		temp.setTime(dtBegin);
		long lBeginWeek = temp.get(Calendar.DAY_OF_WEEK);
		temp.setTime(dtEnd);
		long lEndWeek = temp.get(Calendar.DAY_OF_WEEK);
		// 取星期数
		long lWeek = (lTotalTime) / 7;
		lRelaxTime = lWeek * 2;
		lRelaxTime += getRelaxTime(lBeginWeek, lEndWeek);
		return lTotalTime - lRelaxTime + 1;
	}

	private static long getRelaxTime(long lBeginWeek, long lEndWeek) {
		long lRelax = 0;
		while (lBeginWeek != lEndWeek) {
			if (lBeginWeek == Calendar.SATURDAY || lBeginWeek == Calendar.SUNDAY) {
				lRelax++;
			}
			if (lBeginWeek == Calendar.SATURDAY) {
				lBeginWeek = Calendar.SUNDAY;
			} else {
				lBeginWeek++;
			}
		}
		return lRelax;
	}

	/**
	 * 取两时间段的天数,例如：2003-12-02到2003-12-01为一天
	 * 
	 * @param dtBegin
	 *            开始日期(Date类型)
	 * @param dtEnd
	 *            结束日期(Date类型)
	 * @return
	 */
	public static long getTime(java.sql.Date dtBegin, java.sql.Date dtEnd) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(dtBegin);
		temp.set(Calendar.HOUR, 0);
		temp.set(Calendar.MINUTE, 0);
		temp.set(Calendar.SECOND, 0);
		temp.set(Calendar.MILLISECOND, 0);
		long lBegin = temp.getTime().getTime();
		temp.setTime(dtEnd);
		temp.set(Calendar.HOUR, 0);
		temp.set(Calendar.MINUTE, 0);
		temp.set(Calendar.SECOND, 0);
		temp.set(Calendar.MILLISECOND, 0);
		long lEnd = temp.getTime().getTime();
		long lTime = (lEnd - lBegin) / (24 * 60 * 60 * 1000);
		return lTime;
	}

	/**
	 * 取两时间段的天数,例如：2003-12-02到2003-12-01为一天
	 * 
	 * @param dtBegin
	 *            开始日期(Date类型)
	 * @param dtEnd
	 *            结束日期(Date类型)
	 * @return
	 */
	public static long getTime(java.util.Date dtBegin, java.util.Date dtEnd) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(dtBegin);
		temp.set(Calendar.HOUR, 0);
		temp.set(Calendar.MINUTE, 0);
		temp.set(Calendar.SECOND, 0);
		temp.set(Calendar.MILLISECOND, 0);
		long lBegin = temp.getTime().getTime();
		temp.setTime(dtEnd);
		temp.set(Calendar.HOUR, 0);
		temp.set(Calendar.MINUTE, 0);
		temp.set(Calendar.SECOND, 0);
		temp.set(Calendar.MILLISECOND, 0);
		long lEnd = temp.getTime().getTime();
		long lTime = (lEnd - lBegin) / (24 * 60 * 60 * 1000);
		return lTime;
	}

	// the following const is to define double format
	public static final int FMT_NUM_NORMAL = 1;

	public static final int FMT_NUM_NODELIMA = 2;

	public static final int FMT_NUM_COMMADELIMA = 3;

	/**
	 * get file name from full file path.
	 * 
	 * @param strFilePath
	 *            file path to be processed.
	 * @return file name.
	 */
	public static String getFileName(String strFilePath) {
		int nIndex = 0;
		if ((nIndex = strFilePath.lastIndexOf('\\')) == -1)
			nIndex = strFilePath.lastIndexOf('/');
		return strFilePath.substring(nIndex + 1, strFilePath.length());
	}

	/**
	 * 判断是否是合法的 email 地址 (按长度,'@')
	 * 
	 * @param strEMail
	 * @return
	 */
	public static boolean isEmail(String strEMail) {
		int nDelima = strEMail.indexOf("@");
		int nDot = strEMail.lastIndexOf(".");
		if (nDelima != -1 && nDot > nDelima) {
			return true;
		}
		return false;
	}

	/**
	 * filterHTML: 显示页面时,须将如下字符过滤: " => &quot; , > => &gt; , < => &lt; '\n' => <br>, '\r' =><br>
	 * 
	 * @param strHTML
	 *            input html string.
	 * @return String 返回格式化后的字符串。
	 */
	public static String filterHTML(String strHTML) {
		if (strHTML == null)
			return "";
		StringBuffer sbResult = new StringBuffer();
		int nLen = strHTML.length();
		char chCur;
		for (int i = 0; i < nLen; i++) {
			chCur = strHTML.charAt(i);
			switch (chCur) {
			case '\"':
				sbResult.append("&quot;");
				break;
			case '>':
				sbResult.append("&gt;");
				break;
			case '<':
				sbResult.append("&lt;");
				break;
			case '\r':
			case '\n':
				sbResult.append("<br>");
				break;
			default:
				sbResult.append(chCur);
				break;
			}
		}
		return sbResult.toString();
	}

	/**
	 * 
	 */
	public static String normalizeString(String strValue) {
		return ((strValue == null) ? "" : strValue.trim());
	}

	public static String transFormat(String str) throws java.io.UnsupportedEncodingException {
		return new String(str.getBytes("ISO8859_1"), "GBK");
	}

	public static Enumeration locatePage(Enumeration enums, int nPageSize, int nPage) {
		for (int i = 0; enums.hasMoreElements() && i < nPageSize * nPage; i++) {
			enums.nextElement();
		}
		return enums;
	}

	/**
	 * 
	 * convert string encodeing into gbk
	 */
	public static String toChinese(String str) {
		try {
			if (Env.getInstance().isCHINESE_ENCODE() == true)
				return new String(normalizeString(str).getBytes("ISO8859_1"), "GBK");
			else
				return str;
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
			return null;
		}
	}

	public static String subAsciiString(String strSRC, int nLen) {
		if (strSRC == null) {
			return "";
		} else {
			byte[] byTemp = strSRC.getBytes();
			if (nLen < byTemp.length) {
				boolean bDouble = false;
				int i;
				for (i = 0; i < nLen; i++) {
					if (bDouble) {
						bDouble = (false);
					} else {
						bDouble = (((short) byTemp[i] & 0xff) > 0x80);
					}
				}
				if (bDouble)
					i--;
				return new String(byTemp, 0, i);
			}
			return strSRC;
		}
	}

	/**
	 * convert a string encode with 'iso-8859-1', which is used to transfer in network wiht http, to 'GBK' encoding.
	 * 
	 * @param strGBK
	 *            a string encoded with 'GBK'
	 * @return return a new string encoded with 'iso-8859-1'.
	 */
	public static String toAscii(String strGBK) {
		try {
			return new String(DataFormat.normalizeString(strGBK).getBytes("GBK"), "iso-8859-1");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		return null;
	}

	// 定义无效数据值
	public static String W_STRING = "";

	public static long W_ID = -1;

	public static double W_AMOUNT = 0;

	public static float W_RATE = 0;

	public static java.sql.Timestamp W_DATE = null;

	// 定义调试信息的
	public static String m_strBeChangedTxt[] = { "<", "[b]", "[/b]", "[br]", "[I]", "[/I]", "[P]",
			"[/P]", "[SUP]", "[/SUP]", "[SUB]", "[/SUB]", "[BR]", "[A", "[/A]", "[STRIKE]",
			"[/STRIKE]", "[UL]", "[LI]", "[/UL]", "[OL]", "[LI]", "[/OL]", "[FONT", "[/FONT]" };

	public static String m_strChangeTxt[] = { "&lt;", "<b>", "</b>", "<br>", "<I>", "</I>", "<P>",
			"</P>", "<SUP>", "</SUP>", "<SUB>", "</SUB>", "<BR>", "<A", "</A>", "<STRIKE>",
			"</STRIKE>", "<UL>", "<LI>", "</UL>", "<OL>", "<LI>", "</OL>", "<FONT", "</FONT>" };

	/**
	 * set dest array with special char
	 * 
	 * @param dest
	 * @param ch
	 */
	public static void memSet(char dest[], char ch) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] = ch;
		}
	}

	/**
	 * copy src array into dest array
	 * 
	 * @param dest
	 *            to where
	 * @param from
	 *            where
	 * @return the byte copied, the length is minmum of dest length and src length.
	 */
	public static int memCopy(char dest[], char src[]) {
		int nLen = Math.min(dest.length, src.length);
		for (int i = 0; i < nLen; i++) {
			dest[i] = src[i];
		}
		return nLen;
	}

	public static String txtStore(String strText) {
		// convert a text when it will store text to db
		// parameter: cText, the text which will be converted.
		if (strText == null) {
			strText = "";
		} else {
			for (int i = 0; i < m_strBeChangedTxt.length; i++) {
				String strBeChangedTxt = m_strBeChangedTxt[i];
				String strChangeTxt = m_strChangeTxt[i];
				int nLength = strBeChangedTxt.length();
				while (strText.indexOf(strBeChangedTxt) >= 0) {
					int nPlace = strText.indexOf(strBeChangedTxt);
					strText = strText.substring(0, nPlace) + strChangeTxt
							+ strText.substring(nPlace + nLength, strText.length());
				}
			}
		}
		return strText;
	}

	/**
	 * 将输入字符串中_、%和'转意
	 * 
	 * @param strIn
	 *            输入字符串
	 * @param cTransferChar
	 *            转意符，建议使用'\'或'/'
	 * @param lType
	 *            转意类型 0 用于LIKE语句 1 用于非LIKE语句
	 * @return 转意后的字符串
	 */
	public static String transString(String strIn, char cTransferChar, long lType) {
		String strReturn = "";
		for (int nIndex = 0; nIndex < strIn.length(); nIndex++) {
			char cChar = strIn.charAt(nIndex);
			switch (cChar) {
			case '\'':
				strReturn += "'";
				break;
			case '%':
				if (lType == 0) {
					strReturn += new Character(cTransferChar);
					break;
				}
			case '_':
				if (lType == 0) {
					strReturn += new Character(cTransferChar);
					break;
				}
			}
			if (cChar == cTransferChar && lType == 0) {
				strReturn += new Character(cTransferChar);
			}
			strReturn += new Character(cChar);
		}
		return strReturn;
	}

	/**
	 * 得到随机数字符
	 * 
	 * @return 返回随机数
	 */
	public static String getRnd() {
		return String
				.valueOf((long) ((10000000000l - 1000000000l + 1) * Math.random()) + 1000000000l);
	}

	/**
	 * 将一个用","分开的串分解为一个Vector的数组
	 * 
	 * @param strParam
	 *            需要拆分的参数
	 * @return 返回一个Vector，里面是Long型
	 */
	public static Vector changeStringGroup(String strParam) {
		Vector vcReturn = new Vector();
		int nIndex; // ","的索引位置
		nIndex = strParam.indexOf(",");
		String strData = ""; // 拆出的数字串
		while (nIndex > 0) {
			strData = strParam.substring(0, nIndex);
			vcReturn.add(new Long(strData));
			strParam = strParam.substring(nIndex + 1, strParam.length());
			nIndex = strParam.indexOf(",");
		}
		if (strParam != "") {
			vcReturn.add(new Long(strParam));
		}
		return vcReturn;
	}

	// 生成密码
	public static final String m_strEncodeKeys = "qwertyuiop[]asdfghjkl;;'zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>?`1234567890-=\\~!@#$%^&*()_+|";

	public static String randomPassword(int nLen) {
		if (nLen <= 0)
			nLen = 8;
		char charyKeys[] = new char[nLen];
		char charyEncodeKeys[] = m_strEncodeKeys.toCharArray();
		java.util.Random rndSequence = new java.util.Random();
		int nSequence;
		int nKeyLen = m_strEncodeKeys.length();
		for (int i = 0; i < nLen; i++) {
			nSequence = Math.abs(rndSequence.nextInt()) % nKeyLen;
			charyKeys[i] = charyEncodeKeys[nSequence];
		}
		return charyKeys.toString();
	}

	/**
	 * 得到数字随机数
	 * 
	 * @param nLen随机数长度
	 */
	public static String randomNumberPassword(int nLen) {
		long lNum = 1;
		for (int i = 0; i < nLen - 1; i++) {
			lNum = lNum * 10;
		}
		return String.valueOf((long) ((lNum * 10 - lNum + 1) * Math.random()) + lNum);
	}

	public static String format(java.math.BigDecimal bValue, int lScale) {
		if (bValue != null) {
			bValue = bValue.setScale(lScale, java.math.BigDecimal.ROUND_HALF_UP);
			return bValue.toString();
		} else {
			return "";
		}
	}

	public static double BigToDouble(java.math.BigDecimal bValue, int lScale) {
		if (bValue != null) {
			bValue = bValue.setScale(lScale, java.math.BigDecimal.ROUND_HALF_UP);
			return bValue.doubleValue();
		} else {
			return 0;
		}
	}

	public static String format(double dValue, int lScale) {
		// ////负数，则装化为正数后进行四舍五入
		boolean bFlag = false;
		if (dValue < 0) {
			bFlag = true;
			dValue = -dValue;
		}
		long lPrecision = 10000; // 精度值，为了避免double型出现偏差，增加校验位
		long l45Value = lPrecision / 2 - 1; // 四舍五入的判断值
		long lLength = 1; // 乘的数字
		for (int i = 0; i < lScale; i++) {
			lLength = lLength * 10; // 比如保留2位，乘以100
		}
		long lValue = (long) dValue; // 值的整数部分
		long lValue1 = (long) ((dValue - lValue) * lLength); // 乘以保留位数
		long lValue2 = (long) ((dValue - lValue) * lLength * lPrecision); //
		long lLastValue = lValue2 - (lValue2 / lPrecision) * lPrecision;
		if (lLastValue >= l45Value) {
			lValue1++;
		}
		dValue = lValue + (double) lValue1 / lLength; // 四舍五入后的值
		if (bFlag) {
			dValue = -dValue;
		}
		java.math.BigDecimal bd = new java.math.BigDecimal(dValue);
		bd = bd.setScale(lScale, java.math.BigDecimal.ROUND_HALF_UP);
		return bd.toString();
		// Replace by Huang Ye
		// double d = UtilOperation.Arith.round(dValue, lScale);
		// return String.valueOf(dValue);
	}

	public static String format(float fValue, int lScale) {
		/*
		 * java.math.BigDecimal bd; bd = new java.math.BigDecimal(fValue); bd = bd.setScale(lScale,
		 * java.math.BigDecimal.ROUND_HALF_UP);
		 */
		java.math.BigDecimal bd, bdup, bddown;
		bd = new java.math.BigDecimal(fValue);
		bdup = bd.setScale(lScale, java.math.BigDecimal.ROUND_UP);
		bddown = bd.setScale(lScale, java.math.BigDecimal.ROUND_DOWN);
		if ((bdup.doubleValue() - bd.doubleValue()) <= (bd.doubleValue() - bddown.doubleValue())) {
			return bdup.toString();
		} else {
			return bddown.toString();
		}
	}

	/**
	 * 格式化利率(float)不建议使用，存在精度问题
	 * 
	 * @param fValue
	 *            利率值
	 */
	public static String formatRate(float fValue) {
		if (fValue == 0) {
			return "0.00";
		} else {
			return format(fValue, 4);
		}
	}

	/**
	 * 格式化利率(double)
	 * 
	 * @param fValue
	 *            利率值
	 */
	public static String formatRate(double dValue) {
		if (dValue == 0) {
			return "0.000000";
		} else {
			return format(dValue, 6);
		}
	}

	/**
	 * 格式化利率(double)
	 * 
	 * @param fValue
	 *            利率值
	 */
	public static String formatRate(double dValue, int lScale) {
		if (dValue == 0 || lScale < 0) {
			return "0.00";
		} else {
			return format(dValue, lScale);
		}
	}

	/**
	 * 格式化利率(double) 去掉利率小数点后面的“0“
	 * 
	 * @param dValue
	 *            利率值
	 */
	public static String formatRate(String dValue) {
		int nLength = 0;
		int nCount = 0;
		int nIsTrue = 0;
		String strOrder = "";
		String strReturn = dValue;
		nLength = dValue.length();
		for (int nOrder = 1; nOrder <= nLength; nOrder++) {
			strOrder = dValue.substring(nLength - nOrder, nLength - nOrder + 1);
			if (strOrder.equals(".")) {
				if (nCount == nLength - dValue.indexOf(".") - 1)// 6 )
				{
					nCount++;
				}
				return strReturn.substring(0, nLength - nCount);
			} else {
				if (strOrder.equals("0") && (nIsTrue <= 0)) {
					nCount++;
				} else {
					nIsTrue = 1;
					return strReturn.substring(0, nLength - nCount);
				}
			}
		}
		return strReturn;
	}

	/**
	 * 格式化金额
	 * 
	 * @param dValue
	 *            金额
	 */
	public static String formatAmount(double dValue) {
		if (dValue == 0)
			return "";
		else if (dValue > 0)
			return format(dValue, 2);
		else
			return "-" + format(java.lang.Math.abs(dValue), 2);
	}

	/**
	 * 格式化金额包括0
	 * 
	 * @param dValue
	 * @return
	 */
	public static String formatAmountUseZero(double dValue) {
		if (dValue == 0)
			return "0.00";
		else if (dValue > 0)
			return format(dValue, 2);
		else
			return "-" + format(java.lang.Math.abs(dValue), 2);
	}

	/**
	 * 反向格式化金额，将","去掉
	 * 
	 * @param strData
	 *            数据
	 */
	public static String reverseFormatAmount(String strData) {
		int i;
		String strTemp;
		// 去掉所有的","
		strTemp = new String(strData);
		strData = "";
		for (i = 0; i < strTemp.length(); i++) {
			char cData;
			cData = strTemp.charAt(i);
			if (cData != ',') {
				strData = strData + cData;
			}
		}
		return strData;
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @return 返回格式化不戴小数点没有逗号分割符的金额
	 */
	public static String formatAmountNotDot(double dAmount) {
		String strData = formatAmount(dAmount);
		if (strData.equals("")) {
			return strData;
		} else {
			// 将小数点前和后的数据分别取出来
			int nPoint;
			nPoint = strData.indexOf(".");
			String strFront = new String(strData), strEnd = "";
			if (nPoint != -1) {
				strFront = strData.substring(0, nPoint);
				strEnd = strData.substring(nPoint + 1, strData.length());
			}
			// 补或者截小数点后面的值，保持两位
			if (strEnd.length() > 2) {
				strEnd = strEnd.substring(0, 2);
			} else {
				if (strEnd.length() == 1) {
					strEnd = strEnd + "0";
				} else {
					if (strEnd.length() == 0) {
						strEnd = "00";
					}
				}
			}
			strData = strFront + strEnd;
			for (int ii = 18; ii < strData.length(); ii--) {
				strData = "&nbsp;" + strData;
			}
		}
		return strData;
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @param nType
	 *            1-将0转换，2-不将0转换
	 * @return
	 */
	public static String formatDisabledAmount(double dAmount, int nType) {
		String strData = "";
		if(dAmount==0)
			return "0.00";
		if (nType == 1) {
			strData = formatAmount(dAmount);
		} else {
			strData = formatAmountUseZero(dAmount);
		}
		if (dAmount < 0)
			strData = formatAmount(java.lang.Math.abs(dAmount));
		if (strData.equals("")) {
			return strData;
		} else {
			// 将小数点前和后的数据分别取出来
			int nPoint;
			nPoint = strData.indexOf(".");
			String strFront = new String(strData), strEnd = "";
			if (nPoint != -1) {
				strFront = strData.substring(0, nPoint);
				strEnd = strData.substring(nPoint + 1, strData.length());
			}
			String strTemp = "";
			// 小数点前面的数据加","
			strTemp = new String(strFront);
			strFront = "";
			int nNum, i;
			nNum = 0;
			for (i = strTemp.length() - 1; i >= 0; i--) {
				if (nNum == 3) {
					strFront = "," + strFront;
					nNum = 0;
				}
				nNum++;
				char cData;
				cData = strTemp.charAt(i);
				strFront = cData + strFront;
			}
			// 补或者截小数点后面的值，保持两位
			if (strEnd.length() > 2) {
				strEnd = strEnd.substring(0, 2);
			} else {
				if (strEnd.length() == 1) {
					strEnd = strEnd + "0";
				} else {
					if (strEnd.length() == 0) {
						strEnd = "00";
					}
				}
			}
			strData = strFront + "." + strEnd;
		}
		if (dAmount < 0 && !strData.equals("0.00"))
			strData = "-" + strData;
		return strData;
	}

	/**
	 * 格式化金额
	 * 
	 * @param dValue
	 *            金额
	 * @param nDigit
	 *            小数点后的位数
	 */
	public static String formatAmount(double dValue, int nDigit) {
		//
		if (dValue == 0)
			return "";
		else if (dValue > 0)
			return format(dValue, nDigit);
		else
			return "-" + format(java.lang.Math.abs(dValue), nDigit);
	}

	/**
	 * 格式化金额包括0
	 * 
	 * @param dValue
	 * @param nDigit
	 *            小数点后的位数
	 * @return
	 */
	public static String formatAmountUseZero(double dValue, int nDigit) {
		if (dValue == 0) {
			return "0." + formatInt(0, nDigit);
		} else if (dValue > 0) {
			return format(dValue, nDigit);
		} else {
			return "-" + format(java.lang.Math.abs(dValue), nDigit);
		}
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @param nType
	 *            1-将0转换，2-不将0转换
	 * @param nDigit
	 *            小数点后的位数
	 * @return
	 */
	public static String formatDisabledAmount(double dAmount, int nType, int nDigit) {
		String strData = "";
		if (nType == 1) {
			strData = formatAmount(dAmount, nDigit);
		} else {
			strData = formatAmountUseZero(dAmount, nDigit);
		}
		// if (dAmount < 0)
		// strData = formatAmount(java.lang.Math.abs(dAmount), nDigit);
		if (strData.equals("")) {
			return strData;
		} else {
			System.out.print("=======strData=" + strData);
			// 将小数点前和后的数据分别取出来
			int nPoint;
			nPoint = strData.indexOf(".");
			String strFront = new String(strData), strEnd = "";
			if (nPoint != -1) {
				strFront = strData.substring(0, nPoint);
				strEnd = strData.substring(nPoint + 1, strData.length());
			}
			String strTemp = "";
			// 小数点前面的数据加","
			strTemp = new String(strFront);
			strFront = "";
			int nNum, i;
			nNum = 0;
			for (i = strTemp.length() - 1; i >= 0; i--) {
				if (nNum == 3) {
					strFront = "," + strFront;
					nNum = 0;
				}
				nNum++;
				char cData;
				cData = strTemp.charAt(i);
				strFront = cData + strFront;
			}
			// 补或者截小数点后面的值，保持小数点后的位数
			if (strEnd.length() >= nDigit) {
				strEnd = strEnd.substring(0, nDigit);
			} else {
				strEnd = strEnd + formatInt(0, nDigit - strEnd.length());
			}
			if (nDigit != 0) {
				strData = strFront + "." + strEnd;
			} else {
				strData = strFront;
			}
		}
		// System.out.print("=======strData="+strData);
		// if (dAmount < 0 && !strData.equals("0.00"))
		// strData = "-" + strData;
		return strData;
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @param nDigit
	 *            小数点后的位数
	 * @return 返回格式化的金额
	 */
	public static String formatDisabledAmount(int nDigit, double dAmount) {
		return formatDisabledAmount(dAmount, 1, nDigit);
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @return 返回格式化的金额
	 */
	public static String formatDisabledAmount(double dAmount) {
		return formatDisabledAmount(dAmount, 1);
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @return 返回格式化的金额
	 */
	public static String formatListAmount(double dAmount) {
		String strData = formatDisabledAmount(dAmount);
		if (strData == null || strData.length() <= 0) {
			//modified by mzh_fu 2007/03/22
			//strData = "&nbsp;";
			strData = "0.00";
		}
		return strData;
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @param dAmount
	 *            lTypeID -将0转换，2-不将0转换
	 * @return 返回格式化的金额
	 */
	public static String formatListAmount(double dAmount, int lTypeID) {
		String strData = formatDisabledAmount(dAmount, lTypeID);
		if (strData == null || strData.length() <= 0) {
			//modified by mzh_fu 2007/03/22
			//strData = "&nbsp;";
			strData = "0.00";
		}
		return strData;
	}

	/**
	 * 格式化列表的整数
	 * 
	 * @param lCount
	 *            整数
	 * @return 返回格式化的整数
	 */
	public static String formatListLong(long lCount) {
		String strTemp = "";
		if (lCount >= 0) {
			strTemp = String.valueOf(lCount);
		}
		String strFront = "";
		int nNum, i;
		nNum = 0;
		for (i = strTemp.length() - 1; i >= 0; i--) {
			if (nNum == 3) {
				strFront = "," + strFront;
				nNum = 0;
			}
			nNum++;
			char cData;
			cData = strTemp.charAt(i);
			strFront = cData + strFront;
		}
		return strFront;
	}

	//四舍五入
	public static double roundDouble(double amount,int precision)
	{
		double val;
		double factor = Math.pow(10, precision);
		val = Math.floor(amount*factor+0.5)/factor;
		return val;
	}
	/**
	 * 格式化列表的金额
	 * 
	 * @param sessionMng
	 * @param dAmount
	 *            金额
	 * @return 返回格式化的金额
	 */
	public static String formatListAmount(SessionMng sessionMng, double dAmount) {
		return sessionMng.m_strCurrencySymbol + formatListAmount(dAmount);
	}

	/**
	 * 格式化字符串
	 * 
	 * @param strData
	 *            字符串数据
	 */
	public static String formatString(String strData) {
		if (strData == null || strData.length() == 0) {
			return "";
		} else {
			return strData;
		}
	}

	/**
	 * 格式化字符串 for print ninh 2004-11-24
	 * 
	 * @param strData
	 *            字符串数据
	 */
	public static String formatStringForPrint(String strData) {
		if (strData == null || strData.length() == 0) {
			return "&nbsp;";
		} else {
			return strData;
		}
	}

	/**
	 * @param dfValue
	 *            the double value to be format
	 * @param nFmt
	 *            format is requested.
	 * @return String
	 */
	/**
	 * @deprecated this format fucntion is unsafe and uncompatible.
	 */
	public static double formatDouble(double dValue, int nScale) {
		return new Double(format(dValue, nScale)).doubleValue();
	}

	public static double formatDouble(double dValue) {
		return formatDouble(dValue, 2);
	}

	/*
	 * 将数据转化成固定长度的字符串，如果位数不够，则前面补零！ 例如：transCode(123,6); 结果为：000123
	 */
	public static String transCode(long ID, int length) {
		String returnString = "";
		if (ID >= 0) {
			returnString = String.valueOf(ID);
			for (int i = returnString.length(); i < length; i++) {
				returnString = "0" + returnString;
			}
		}
		return returnString;
	}
	 /**
     * 将科学计数法的金额转化成一般的金额
     * @param double amount
     * @return String 
     * @author xwhe
     */
    public static String dataFormat(double amount)
    {
    	String dataFromatAmount ="";
    	DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    	if(amount == 0)
    	{   
    		dataFromatAmount = "0.0";
    	}
    	else if(amount > 0)
    	{
    		dataFromatAmount = decimalFormat.format(amount);
   
    	}
    	else
    	{
    		dataFromatAmount = "-"+decimalFormat.format(amount);
    	}
    	return dataFromatAmount;
    }
	/**
	 * 解析非上传格式的InputString
	 * 
	 * @param value
	 * @return
	 */
	public static HashMap parseCommonFormatIS(String strValue) {
		HashMap hmValue = new HashMap();
		String str = ""; // 始终存放一对名字和值对
		String strBigSep = "$ $"; // 分割每个提交的参数的符号
		String strSmallSep = "! !"; // 分割每个提交参数中name和value的符号
		int i = strValue.indexOf(strBigSep);
		int j = 0;
		int h = 0;
		String sName = null;
		String sValue = null;
		for (; i < strValue.length() && i >= 0;) {
			str = strValue.substring(0, i);
			j = str.indexOf(strSmallSep);
			sName = str.substring(0, j);
			sValue = str.substring(j + strSmallSep.length());
			hmValue.put(sName, sValue);
			strValue = strValue.substring(i + strBigSep.length());
			i = strValue.indexOf(strBigSep);
		}
		str = strValue;
		j = str.indexOf(strSmallSep);
		if (!str.trim().equals("")) {
			sName = str.substring(0, j);
			sValue = str.substring(j + strSmallSep.length());
			hmValue.put(sName, sValue);
		}
		return hmValue;
	}

	public static String encodeDate(String strBegin, String strEnd) {
		Timestamp dtBegin = null;
		Timestamp dtEnd = null;
		if (strBegin == null) {
			strBegin = "";
		}
		if (strEnd == null) {
			strEnd = "";
		}
		String[] str = new String[2];
		str[0] = strBegin;
		str[1] = strEnd;
		return encodeString(str);
	}

	public static String encodeString(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			sb.append(objs[i].toString()).append("$");
		}
		return sb.toString();
	}

	public static String[] decode2String(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char ch = str.charAt(str.length() - 1);
		if (ch != '$') {
			str += "$";
		}
		int index = str.indexOf("$");
		Vector vt = new Vector(10);
		String sTemp = null;
		while (index != -1) {
			sTemp = str.substring(0, index);
			str = str.substring(index + 1, str.length());
			index = str.indexOf("$");
			vt.addElement(sTemp);
		}
		return (String[]) vt.toArray(new String[0]);
	}

	public static long[] decode2Long(String str) {
		String[] sValues = decode2String(str);
		if (sValues == null) {
			return null;
		}
		long[] lValues = new long[sValues.length];
		try {
			for (int i = 0; i < sValues.length; i++) {
				lValues[i] = Long.parseLong(sValues[i]);
			}
		} catch (Exception e) {
			return null;
		}
		return lValues;
	}

	/**
	 * 格式化日期
	 * 
	 * @param tsData
	 *            日期数据
	 */
	public static String formatDate(java.sql.Timestamp tsData) {
		if (tsData == null) {
			return "";
		} else {
			return getDateString(tsData);
		}
	}

	/*
	 * 得到该月的最后一天
	 */
	public static String getLastDateString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		String strMonth = String.valueOf(iMonth);
		if (strMonth.length() == 1) {
			strMonth = "0" + strMonth;
		}
		return calendar.get(Calendar.YEAR) + "-" + strMonth + "-"
				+ IDate.getDaysOfMonth(calendar.get(Calendar.YEAR), iMonth);
	}

	/*
	 * 得到一个日期的月份,用0-11表示
	 */
	public static String getMonthString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		return String.valueOf(calendar.get(Calendar.MONTH));
	}

	/*
	 * 得到一个日期的月份，用1-12表示
	 */
	public static String getRealMonthString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		return String.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	/*
	 * 得到一个日期的年份
	 */
	public static String getYearString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	/*
	 * 得到一个日期的日子
	 */
	public static String getDayString(java.sql.Timestamp ts) {
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 格式化数字添加后缀,例如:把3.6格式化成3.600000,小数点后六位
	 * 
	 * @param double
	 *            需要格式化的数字
	 * @param char
	 *            添加后缀的字符
	 * @param int
	 *            小数点后位数
	 * @return String 返回格式化后的字符串
	 */
	public static String formatTax(double taxRate, char suffix, int decimal) {
		String strReturnValue = "" + taxRate; // 返回值
		int intDotPosition = -1; // 小数点位置
		int intNumberLen = -1; // 数字长度
		String strTmp = ""; // 中转变量
		String strSuffix = ""; // 后缀
		for (int n = 0; n < decimal; n++) { // 初始化后缀
			strSuffix += suffix;
		}
		intDotPosition = strReturnValue.indexOf(".");
		if (intDotPosition < 0) { // 如果需要格式化的数字没有小数
			strReturnValue += ".";
			intNumberLen = strReturnValue.length() + decimal;
		} else {
			intNumberLen = intDotPosition + decimal + 1; // 取得格式化后数字的长度
		}
		strReturnValue += strSuffix; // 添加后缀
		strReturnValue = strReturnValue.substring(0, intNumberLen);
		return strReturnValue;
	}

	/**
	 * 数组转化为用某一符号分开的字符串
	 * 
	 * @param nothing
	 * @return: such as 17,18,19,20,21,22,23
	 */
	public static String getStringWithTagByLongArray(long[] args, String tag) {
		String str = "";
		for (int i = 0; args != null && i < args.length; i++)
			str = str + args[i] + tag;
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * 数组转化为用','分开的字符串
	 * 
	 * @param nothing
	 * @return: such as 17,18,19,20,21,22,23
	 */
	public static String getStringWithTagByLongArray(long[] args) {
		return getStringWithTagByLongArray(args, ",");
	}

	/**
	 * 完成类似JDK1.4 String::split的功能
	 * 
	 * @param1 splitedStr 需要被分割的String
	 * @param2 splitConditon 分割的条件字符
	 */
	public static String[] splitString(String splitedStr, String splitConditon) {
		int start = 0;
		int end = 0;
		ArrayList list = new ArrayList();
		String tmp = null;
		while (true) {
			end = splitedStr.indexOf(splitConditon, start);
			if (end == -1) {
				tmp = splitedStr.substring(start, splitedStr.length());
				list.add(tmp);
				break;
			}
			tmp = splitedStr.substring(start, end);
			list.add(tmp);
			start = end + splitConditon.length();
		}
		String[] res = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			res[i] = (String) list.get(i);
		}
		return res;
	}

	/**
	 * 格式化列表的金额
	 * 
	 * @param dAmount
	 *            金额
	 * @return(四舍五入)
	 */
	public static String roundAmount(double dAmount) {
		// 四舍五入数据
		BigDecimal bigDecimal = new BigDecimal(dAmount);
		double dBigDecimal = bigDecimal.doubleValue();
		String sBigDecimal = formatAmount(dBigDecimal, 0);
		if ("0".equals(sBigDecimal)) {
			// modified by mzh_fu 2007/03/22
			//return "&nbsp;&nbsp;";
			return "0.00";
		}
		int iLength = 0;
		String sReturn = "";
		for (int i = sBigDecimal.length() - 1; i >= 0; i--) {
			if (iLength == 3) {
				sReturn = "," + sReturn;
				iLength = 0;
			}
			iLength++;
			char cData = sBigDecimal.charAt(i);
			sReturn = cData + sReturn;
		}
		return sReturn;
	}

	/**
	 * 根据传入日期判断当前是星期几
	 * 
	 * @return Calendar.MONDAY Calendar.TUESDAY ...... Calendar.SUNDAY
	 */
	static public int getWeekDay(Timestamp date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据当前服务器时间计算 获取时间策略为如果当前小时在AM 0-6点，系统认为当前获取时间的程序是每晚定时数据采集 转换程序，因此返回执行时间为前一天，否则返回目前时间
	 */
	public static Timestamp getTreasuryPlanExecuteDate() {
		// for Debug @2005-03-09
		// Timestamp currentTime = Timestamp.valueOf("2005-03-09 00:00:00.000000000");
		// return currentTime;

		Timestamp currentTime = Env.getSystemDateTime();
		Calendar c = Calendar.getInstance();

		c.setTime(currentTime);
		Timestamp executeDate = null;
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if (hour >= 0 && hour <= 10) {
			// 第二天凌晨定时程序仍然在运行，执行日为前一天
			// 暂时改到10点
			executeDate = DataFormat.getNextDate(currentTime, -1);
		} else {
			executeDate = currentTime;
		}
		System.out.println("=======executeDate" + executeDate);
		String formatDate = DataFormat.formatDate(executeDate, DataFormat.FMT_DATE_YYYYMMDD);
		System.out.println("=======formatDate" + formatDate);
		formatDate += " 00:00:00.000000000";
		return Timestamp.valueOf(formatDate);
	}

	// 精确的除法
	public static BigDecimal div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 截掉double数后小数部分，不进行四舍五入
	 */
	public static double cutNumberAfterDecimal(double beforeDouble) {
		Double before = new Double(beforeDouble);
		double afterDouble = (double) before.longValue();
		return afterDouble;
	}

	/**
	 * 检查文件中数据是否合法
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(HSSFCell cell) {
		String str = "";
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK
					|| cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
				// HSSFDateUtil.isCellDateFormatted(cell)
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					java.util.Date date = cell.getDateCellValue();
					str = DataFormat.formatDate(date, DataFormat.FMT_DATE_YYYYMMDD);
				} else {
					if (cell.getNumericCellValue() != 0.0)
						str = String.valueOf(cell.getNumericCellValue());
				}
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				str = cell.getStringCellValue() == null ? "" : cell.getStringCellValue();
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				str = String.valueOf(cell.getBooleanCellValue());
			}
		}

		return str.trim();
	}
	/**
	 * 取当期日期的前天日期
	 * 
	 * @param cal
	 * @return
	 */
	public static java.sql.Date incDate(java.util.Date date, int day) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return new java.sql.Date(cal.getTime().getTime());
	}
	
	/**
	 * get oracle database system time.
	 * 
	 * @param con
	 * @exception java.sql.SQLException
	 * @return
	 */
	public static  String getStringDateTime() throws SQLException {
	    Connection con = null;
	    java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.sql.Timestamp ts = null;
		try {
		    con = Database.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("select sysdate from dual");
			// select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') from dual
			if (rs.next())
				ts = rs.getTimestamp(1);
			rs.close();  
			stmt.close();
			con.close();
		} catch (SQLException sqe) {
			System.out.println("SQLExcepiton");
			sqe.printStackTrace();
			throw sqe;
		} catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return ts.toString();
	}
	/**
	 * 检验是否是工作日
	 * 
	 * @param dtDate
	 *            日期
	 * @return boolean
	 */
	public static String getWeekDate(Timestamp dtTime,int DayofWeek) {
		long lDay = 0;
		dtTime = getNextDate(dtTime, 1);
		Calendar clDate = Calendar.getInstance();
		clDate.setTime(dtTime);
		lDay = clDate.get(Calendar.DAY_OF_WEEK);
		while(lDay!=DayofWeek)
		{
		    dtTime = getNextDate(dtTime, 1);
		    clDate.setTime(dtTime);
		    lDay = clDate.get(Calendar.DAY_OF_WEEK);
		}
		 return dtTime.toString();
	}
	
	/**
	 * 将字符串（形如：2005银团001号）格式化成指定长度的字符串，用于打印。一个汉字按两个字符计算。
	 * 
	 * @param charLength 格式化后的字符串的长度，一个中文字长度为2，字符或数字长度为1
	 * @param strPrint 待格式化的字符串，可为中、英文、数字，或中、英、数字混合。
	 * @param isCenter 字符串是否居中
	 * @return
	 */
	public static String formatStringForPrint(long charLength, String strPrint, boolean isCenter)
	{
		long lengthOfOriginalStr = 0; //字符串的字符数,一个汉字按两个字符计算
		long preBlankNumber = 0; //在字符串前应添加的空格数
		long endBlankNumber = 0; //在字符串结尾应添加的空格数
		lengthOfOriginalStr = (strPrint==null)?0:strPrint.getBytes().length;
		
		StringBuffer sbBuffer = new StringBuffer();
		
		if(lengthOfOriginalStr< charLength) {
			if(isCenter){
				endBlankNumber = Math.abs((charLength - lengthOfOriginalStr)/2);
				preBlankNumber = charLength - lengthOfOriginalStr - endBlankNumber;
				
				for(long index=0; index<preBlankNumber; index++)
				{
					sbBuffer.append("&nbsp;");
				}
				sbBuffer.append(strPrint);
				for(long index=0; index<endBlankNumber; index++)
				{
					sbBuffer.append("&nbsp;");
				}
			}else{
				preBlankNumber = charLength - lengthOfOriginalStr;
				
				for(int index=0; index<preBlankNumber; index++)
				{
					sbBuffer.append("&nbsp;");
				}
				sbBuffer.append(strPrint);
			}	
			return sbBuffer.toString();
		}else{
			return strPrint;
		}
	}
	
	public static Timestamp getFirstDateOfNextWeek(Date curDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(curDate);
		int curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1;
		//由于中国的一周的第一天和西方不一样，转一下
		if(curDayOfWeek==0) curDayOfWeek = 7;
		int intervalDay = (7 - curDayOfWeek)+1;
		calendar.add(Calendar.DATE, intervalDay);
		
		return new Timestamp(calendar.getTime().getTime());
	}
	
	/**
	 * 获得本周的星期一的日期,采用的是中国的星期方式，本周的第一天为星期一，最后一天为星期日
	 * @param Date curDate
	 * @return Timestamp，本星期的第一天的日期
	 */
	public static Timestamp getFirstDateOfThisWeek(Date curDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(curDate);
		int curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1;//中国的当前星期，星期三？curDayOfWeek就为3
		//由于中国的一周的第一天和西方不一样，转一下
		if(curDayOfWeek==0) curDayOfWeek = 7;
		int intervalDay = 1-curDayOfWeek;
		calendar.add(Calendar.DATE, intervalDay);
		
		return new Timestamp(calendar.getTime().getTime());
	}
	/**
	 * 获得上周五的日期,采用的是中国的星期方式，本周的第一天为星期一，最后一天为星期日
	 * @param Date curDate
	 * @return Timestamp,上周五的日期
	 */
	public static Timestamp getFridayDateOfLastWeek(Date curDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(curDate);
		int curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -1;//中国的当前星期，星期三？curDayOfWeek就为3
		//由于中国的一周的第一天和西方不一样，转一下
		if(curDayOfWeek==0) curDayOfWeek = 7;
		int intervalDay = -2-curDayOfWeek;
		calendar.add(Calendar.DATE, intervalDay);
		
		return new Timestamp(calendar.getTime().getTime());
	}
	// 测试函数
	public static void main(String[] args) {
		try {
			System.out.println("this is a test:" + DataFormat.formatStringForPrint(24,"2005合同银团23字", false));
			System.out.println("number is:" + "302,122.34".getBytes().length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 单据打印使用,获得金额单位的数值
	 * @param strAmount  四舍五入保留两位小数
	 * @param iOrderID 【1：分，2：角，3，元，4：十，5：百，6：千,7：万，8：十万，9：百万，10，千万】
	 * @return
	 * @throws Exception
	 */
	
	public static String getAmountByOrder(String strAmount, int iOrderID){
		String strReturn = "&nbsp;";
		int nAmountLength = 11;
		try
		{
			if (iOrderID < strAmount.length())
			{
				if (iOrderID <= 2)
				{
					return (strAmount.substring(strAmount.length() - iOrderID, strAmount.length() - iOrderID + 1));
				}
				else
				{
					return (strAmount.substring(strAmount.length() - iOrderID - 1, strAmount.length() - iOrderID));
				}
			}
			if((iOrderID == strAmount.length()) && (iOrderID <= nAmountLength))
			{
					return "￥";//sessionMng.m_strCurrencySymbol;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strReturn;
	}
	public static String getAmountByOrder(double dAmount, int iOrderID)
	{
		String strAmount = formatAmount(dAmount);
		return getAmountByOrder(strAmount,iOrderID);
	}
	
	public static String convertClobToString(Clob clob) throws Exception
	{
		String strReturn = "";
		char[] ch = null;
		Reader read = null;
		try
		{
			read = clob.getCharacterStream();
			ch = new char[(int)clob.length()];
			read.read(ch);
			strReturn = new String(ch);
			read.close();
			read = null;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("clob类型转换出错!",e);
		}
		finally
		{
			if(read!=null)
			{
				read.close();
				read = null;				
			}
		}
		return strReturn;
	}
	
}
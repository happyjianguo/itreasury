/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.fcinterface.bankportal.constant.LanguageType;

/**
 * 数据格式类<br>
 * 提供常用的数据格式转换工具
 * @author mxzhou
 */
public class DataFormat
{
	/**
	 * 格式化数字，例如：12345转化为12345
	 * @param dValue 被格式化的数值 
	 * @param iScale 小数点后保留位数,不足补0
	 * @return
	 */
	public static String formatNumberToString(double dValue ,int iScale)
	{
		if(Double.isNaN(dValue))
		{
			return "";
		}
		
		DecimalFormat df = null ;
		StringBuffer sPattern = new StringBuffer("##0");
		if (iScale > 0)
		{
			sPattern.append ( "." ) ;
			for (int i = 0; i < iScale; i++)
			{
				sPattern.append("0");
			}
		}
		df = new DecimalFormat(sPattern.toString( )) ;
		return df.format(dValue) ;
	}
	/**
	 * 格式化数字，例如：12345转化为12,345
	 * @param dValue 被格式化的数值 
	 * @param iScale 小数点后保留位数,不足补0
	 * @return
	 */
	public static String formatNumber(double dValue ,int iScale)
	{
		if(Double.isNaN(dValue))
		{
			return "";
		}
		
		DecimalFormat df = null ;
		StringBuffer sPattern = new StringBuffer(",##0");
		if (iScale > 0)
		{
			sPattern.append ( "." ) ;
			for (int i = 0; i < iScale; i++)
			{
				sPattern.append("0");
			}
		}
		df = new DecimalFormat(sPattern.toString( )) ;
		return df.format(dValue) ;
	}
	
	/**
	 * 该方法不能对数据库中的id值做转换，当id>1000时会出现以下问题
	 * id=12000时，该方法返回12,000 
	 */
	public static String formatNumber(long lValue)
	{
		return formatNumber((double)lValue ,0);
	}
	
	/**
	 * 解析格式化的字符串，转化为数值，例如：12,345.00转化为12345
	 * @param text 被格式化的数值
	 * @return
	 */
	public static double parseDouble(String text)
	{
		int index = text.indexOf(",");
		String sbNumber = "" ;
		while (index != -1)
		{
			sbNumber += text.substring(0 ,index);
			text = text.substring (index + 1 ,text.length());
			index = text.indexOf(",");
		}
		sbNumber += text ;
		//System.out.println(sbNumber);
		return Double.parseDouble(sbNumber);
	}
	public static long parseLong(String text)
	{
		int index = text.indexOf(",");
		String sbNumber = "";
		while (index != -1)
		{
			sbNumber += text.substring(0 , index);
			text = text.substring(index + 1 , text.length());
			index = text.indexOf(",");
		}
		sbNumber += text;		
		return Long.parseLong(sbNumber);
	}
	
	
	/**已定义的日期格式常量*/
	public static final int	DT_YYYYMMDD			= 1 ;
	public static final int	DT_YYYYMMDD_HHMMSS	= 2 ;
	public static final int	DT_HHMMSS			= 3 ;
	public static final int	DT_HHMM				= 4 ;
	public static final int DT_YYYY             = 5 ;
	public static final int DT_MMDDYYYYHHMMSS   = 6 ;
    public static final int DT_YYYYMMDD_EEEE    = 7 ; //ex:2007-06-15 星期五
    public static final int DT_YYMMDD    = 8 ; //ex:070602
	
	/**
	 * 按指定的模板获得对应的时间串
	 * @param date
	 * @param nFmt
	 * @return
	 */
	public static String formatDate(Date date, int nFmt)
	{
		if(date == null)
		{
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat() ;
		switch (nFmt)
		{
			default :
			case DataFormat.DT_YYYYMMDD :
				dateFormat.applyPattern("yyyy-MM-dd");
				break ;
			case DataFormat.DT_YYYYMMDD_HHMMSS :
				dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
				break ;
			case DataFormat.DT_HHMMSS :
				dateFormat.applyPattern("HH:mm:ss");
				break ;
			case DataFormat.DT_HHMM :
				dateFormat.applyPattern("HH:mm");
				break ;
			case DataFormat.DT_YYYY :
				dateFormat.applyPattern("yyyy");
				break ;
			case DataFormat.DT_MMDDYYYYHHMMSS:
				dateFormat.applyPattern("MMddyyyy:HH:mm:ss");
				break ;
            case DataFormat.DT_YYYYMMDD_EEEE:
                dateFormat.applyPattern("yyyy-MM-dd EEEE");
                break;
            case DataFormat.DT_YYMMDD:
                dateFormat.applyPattern("yyMMdd");
                break;
		}
		return dateFormat.format(date);
	}
	public static String formatDate(Date date)
	{
		return formatDate(date, DT_YYYYMMDD);
	}
	public static String formatDate(Date date, String strFmt)
	{
		if(date == null)
		{
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFmt);
		return dateFormat.format(date);
	}
	/**
	 * 按指定的模板转换为相应的时间
	 * @param strDate
	 * @param nFmt
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String strDate, int nFmt) throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		switch (nFmt)
		{
			default :
			case DataFormat.DT_YYYYMMDD :
				dateFormat.applyPattern("yyyy-MM-dd");
				break;
			case DataFormat.DT_YYYYMMDD_HHMMSS :
				dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
				break;
			case DataFormat.DT_HHMMSS :
				dateFormat.applyPattern("HH:mm:ss");
				break;
			case DataFormat.DT_HHMM :
				dateFormat.applyPattern("HH:mm");
				break;
		}
		return dateFormat.parse(strDate);
	}
	public static Date parseDate(String strDate, String strFmt) throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(strFmt);
		return dateFormat.parse(strDate);
	}

	/**
	 * 按指定的分隔符分隔字符串<br>
	 * 完成类似JDK1.4 String::split的功能
	 * @param1 splitedStr 需要被分割的String
	 * @param2 splitConditon 分割的条件字符
	 * @return String[] 分隔后的数组
	 */
	public static String[] splitString(String splitedStr, String splitConditon)
	{
		int start = 0;
		int end = 0;
		ArrayList list = new ArrayList();
		String tmp = null;
		while (true)
		{
            if(start == splitedStr.length())
            {
                break;
            }
            
			end = splitedStr.indexOf(splitConditon, start);
			if (end == -1)
			{
				tmp = splitedStr.substring(start, splitedStr.length());
				list.add(tmp);
				break;
			}
            if(start != end)
            {
                tmp = splitedStr.substring(start, end);
                list.add(tmp);
            }
			start = end + splitConditon.length();
		}
		String[] res = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			res[i] = (String) list.get(i);
		}
		return res;
	}
	/**
	 * 
	 * convert string encodeing into gbk
	 */
	public static String toChinese ( String str )
	{
		try
		{
			if (LanguageType.CHINESE.equals(Env.getEnvConfigItem(Env.G_LANGUAGE)))
				return new String(formatString(str).getBytes("ISO8859_1"),"GBK");
			else
				return str ;
		} catch (UnsupportedEncodingException uee)
		{
			uee.printStackTrace ( ) ;
			return null ;
		}
	}
	/**
	 *  
	 */
	public static String formatString ( String strData )
	{
		if (strData == null || strData.trim().length ( ) <= 0)
		{
			return "" ;
		} else
		{
			return strData.trim() ;
		}
	}
	public static String formatStringForHtml( String strData )
	{
		if (strData == null || strData.trim().length ( ) <= 0)
		{
			return "&nbsp;" ;
		} else
		{
			return strData.trim() ;
		}
	}
	
    /**
     * 判断字符串非空(不为null，且不是空字符串)
     * 
     * @param strInput
     * @return
     */
    public static boolean isNotBlank(String strInput)
    {
        return!isBlank(strInput);
    }
    
    /**
     * 判断字符串为空(为null，或是空字符串)
     * 
     * @param strInput
     * @return
     */
    public static boolean isBlank(String strInput)
    {
        if(strInput==null
                || strInput.trim().length()<=0)
        {
            return true;
        }
        return false;
    }
    
    
	public static void main(String[] args)
	{
		try
		{
//			SimpleDateFormat dateFormat = new SimpleDateFormat();
//			dateFormat.applyLocalizedPattern("yyyy-MM-dd HH:mm:ss");
//			dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
//			System.out.print(formatDate(new Date(), DT_YYMMDD));
//			System.out.print(parseDate("2005-04-01 00:00:00", "yyyy-MM-dd HH:MM:SS"));
            String[] str = splitString("good   good ", "oo");
            System.out.println("length is:" + str.length);
            for(int i=0; i<str.length; i++)
            {
                System.out.println("begin:" + str[i]+ ":end");
            }
            
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

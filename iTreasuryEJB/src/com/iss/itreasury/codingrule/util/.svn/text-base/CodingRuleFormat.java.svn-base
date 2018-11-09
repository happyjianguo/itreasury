package com.iss.itreasury.codingrule.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CodingRuleFormat 
{
	static public String formatTimestamp(java.sql.Timestamp ts,String formatpara) 
	{
		String strReturn = "";
		java.util.Date dt = null;
		if (null == ts)
			return "";
		java.util.Calendar calendar = Calendar.getInstance();
		calendar.setTime(ts);
		
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		if (strMonth.length() == 1) 
		{
			strMonth = "0" + strMonth;
		}
		
		String strDay = String.valueOf(calendar.get(Calendar.DATE));
		if (strDay.length() == 1) 
		{
			strDay = "0" + strDay;
		}
		
		String strHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		if(strHour.length() == 1)
		{
			strHour = "0" + strHour;
		}
		
		String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
		if(strMinute.length() == 1)
		{
			strMinute = "0" + strMinute;
		}
		
		String strSecond = String.valueOf(calendar.get(Calendar.SECOND));
		if(strSecond.length() == 1)
		{
			strSecond = "0" + strSecond;
		}
		
		if(formatpara.equalsIgnoreCase("yyyy-mm-dd"))
		{
			strReturn = calendar.get(Calendar.YEAR) + "-" + strMonth + "-" + strDay;
		}
		else if (formatpara.equalsIgnoreCase("yyyy-mm-dd hh:mm:ss"))
		{
			strReturn = calendar.get(Calendar.YEAR) + "-" + strMonth + "-" + strDay + " " + strHour + ":" + strMinute + ":" + strSecond + ":";
		}
		else if (formatpara.equalsIgnoreCase("yyyymmdd"))
		{
			strReturn = calendar.get(Calendar.YEAR) +  strMonth + strDay;
		}
		else if (formatpara.equalsIgnoreCase("ddmmyyyy"))
		{
			strReturn = strDay + strMonth + calendar.get(Calendar.YEAR);
		}
		else if (formatpara.equalsIgnoreCase("yyyy/mm/dd"))
		{
			strReturn = calendar.get(Calendar.YEAR) + "/" + strMonth + "/" + strDay;
		}
		else if (formatpara.equalsIgnoreCase("dd-mm-yyyy"))
		{
			strReturn = strDay + "-" + strMonth + "-" + calendar.get(Calendar.YEAR);
		}
		else if (formatpara.equalsIgnoreCase("yyyy"))
		{
			strReturn = ""+calendar.get(Calendar.YEAR);
		}
		else if (formatpara.equalsIgnoreCase("yy"))
		{
			strReturn = ""+String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4);
		}
		else if (formatpara.equalsIgnoreCase("mm"))
		{
			strReturn = strMonth;
		}
		else if (formatpara.equalsIgnoreCase("dd"))
		{
			strReturn = strDay;
		}
		
		return strReturn;
	}
	
	static public String formatDate(java.sql.Date date,String formatpara) 
	{
		SimpleDateFormat fmtDate = new SimpleDateFormat();
		fmtDate.applyPattern(formatpara);
		return fmtDate.format(date);
	}
	
	static public String formatStringLength(String strData,int nWidth) 
	{
		String strReturn = strData;
		if (strData == null || strData.length() == 0) 
		{
			return "";
		} 
		else 
		{
			int initLength = strReturn.length();
			for (int i = nWidth; i > initLength; i--) 
			{
				strReturn = "0" + strReturn;
			}
			return strReturn;
		}
	}
	
	static public long parseLong(String strData) 
	{
		long lReturn = -1;
		if(strData==null || strData.length()==0)
		{
			return lReturn;
		}
		else
		{
			lReturn = Long.parseLong(strData);
			return lReturn;
		}	
	}
}

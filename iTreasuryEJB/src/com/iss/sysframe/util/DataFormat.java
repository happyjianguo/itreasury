/**
 * Created on 2008-05-15
 */
package com.iss.sysframe.util;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @author leiyang3
 *
 */
public class DataFormat {
	
	/**
	 * ���ڸ�ʽ����
	 */
	public static final int DT_YYYY_MM_DD = 1; //yyyy-MM-dd
	public static final int DT_YYYYMMDDHHMMSS = 2; //yyyyMMddHHmmss
	public static final int DT_YYYYMMDD_T_HHMMSS = 3; //yyyy-MM-dd'T'HH:mm:ss
	public static final int DT_YYYY = 4; //yyyy
	public static final int DT_YYYYMMDD_HHMMSS_SSSS  = 5; //yyyy-MM-dd HH:mm:ss.S
	public static final int DT_YYYYMMDD = 6; //yyyyMMdd
	public static final int DT_HH_MM_SS = 7; //hh:mm:ss
	public static final int DT_YYYYMMDD_HHMMSS = 8; //yyyy-MM-dd HH:mm:ss
	public static final int DT_HHMMSS = 9; //HHmmss
	
	/**
	 * ���ڸ�ʽ������Ӧ���ַ���
	 * @param type
	 * @return
	 */
	public static String getDateConvertString(int type)
	{
		String strFormatString = "";
		switch(type)
		{
			case DataFormat.DT_YYYY_MM_DD:
				strFormatString = "yyyy-MM-dd";
				break;
			case DataFormat.DT_YYYYMMDDHHMMSS:
				strFormatString = "yyyyMMddHHmmss";
				break;
			case DataFormat.DT_YYYYMMDD_T_HHMMSS:
				strFormatString = "yyyy-MM-dd'T'HH:mm:ss";
				break;
			case DataFormat.DT_YYYY:
				strFormatString = "yyyy";
				break;
			case DataFormat.DT_YYYYMMDD_HHMMSS_SSSS:
				strFormatString = "yyyy-MM-dd HH:mm:ss.S";
				break;
			case DataFormat.DT_YYYYMMDD:
				strFormatString = "yyyyMMdd";
				break;
			case DataFormat.DT_HH_MM_SS:
				strFormatString = "HH:mm:ss";
				break;
			case DataFormat.DT_YYYYMMDD_HHMMSS:
				strFormatString = "yyyy-MM-dd HH:mm:ss";
				break;
			case DataFormat.DT_HHMMSS:
				strFormatString = "HHmmss";
				break;
			default:
				strFormatString = "yyyy-MM-dd";
				break;
		}
		return strFormatString;
	}
	
	/**
	 * ������ʽ�����ַ�����ת��Ϊ��ֵ�����磺12,345.00ת��Ϊ12345
	 * 
	 * @param text
	 *            ����ʽ������ֵ
	 * @return
	 */
	public static double parseNumber(String text)
	{
		int index = text.indexOf ( "," ) ;
		String sbNumber = "" ;
		while (index != -1)
		{
			sbNumber += text.substring ( 0 , index ) ;
			text = text.substring ( index + 1 , text.length ( ) ) ;
			index = text.indexOf ( "," ) ;
		}
		sbNumber += text ;
		System.out.println ( sbNumber ) ;
		return Double.parseDouble ( sbNumber ) ;
	}
	
	/**
	 * @param sDt
	 * @return
	 */
	static public java.sql.Timestamp getDateTime (String sDt)
	{
		try
		{
			return java.sql.Timestamp.valueOf ( sDt ) ; //sDt
														// format:yyyy-mm-dd
														// hh:mm:ss.fffffffff
		} catch (IllegalArgumentException iae)
		{
			sDt = sDt + " 00:00:00" ;
			try
			{
				return java.sql.Timestamp.valueOf ( sDt ) ;
			} catch (Exception e)
			{
				return null ;
			}
		}
	}
	
	public static Date parseDate(String strDate, int type) throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(DataFormat.getDateConvertString(type));
		return dateFormat.parse(strDate);
	}
	
	public static Date parseDate(String strDate) throws Exception
	{
		return parseDate(strDate, DT_YYYY_MM_DD);
	}
	
	public static String formatDate(Date date, int type)
	{
		if(date == null){
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(DataFormat.getDateConvertString(type));
		return dateFormat.format(date);
	}
	
	public static String formatDate(Timestamp date, int type){
		if(date == null){
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.applyPattern(DataFormat.getDateConvertString(type));
		return dateFormat.format(date);
	}
	
    /**
     * ��ʽ�����֣����磺12345ת��Ϊ12345
     * 
     * @param dValue
     *            ����ʽ������ֵ
     * @param iScale
     *            С�������λ��,���㲹0
     * @return
     */
    public static String formatNumber(double dValue, int iScale)
    {
        if(Double.isNaN(dValue))
        {
            return "";
        }
        if(dValue<0)
        {
        	dValue=0;
        }
        DecimalFormat df = null;
        StringBuffer sPattern = new StringBuffer("##0");
        if(iScale > 0)
        {
            sPattern.append(".");
            for (int i = 0; i < iScale; i++)
            {
                sPattern.append("0");
            }
        }
        df = new DecimalFormat(sPattern.toString());
        return df.format(dValue);
    }	
    
    /**
     * ��ʽ�����֣����磺12345ת��Ϊ12345
     * 
     * @param dValue
     *            ����ʽ������ֵ
     * @param iScale
     *            С�������λ��,���㲹0
     * @return
     */
    public static double formatNumberDouble(double dValue, int iScale)
    {
        if(Double.isNaN(dValue))
        {
            return 0;
        }
        if(dValue<0)
        {
        	dValue=0;
        }
        DecimalFormat df = null;
        StringBuffer sPattern = new StringBuffer("##0");
        if(iScale > 0)
        {
            sPattern.append(".");
            for (int i = 0; i < iScale; i++)
            {
                sPattern.append("0");
            }
        }
        df = new DecimalFormat(sPattern.toString());
        return dValue;
    }
    
    public static String formatAmount(double dValue){
        return formatNumber(dValue, 2);
    }

    public static double formatAmountDouble(double dValue){
        return formatNumberDouble(dValue, 2);
    }
	
	/**
	 * 
	 * convert string encodeing into gbk
	 */
	public static String toChinese ( String str )
	{
		try
		{
				return new String(normalizeString (str).getBytes("ISO8859_1") , "GBK" ) ;
		} 
		catch(UnsupportedEncodingException uee)
		{
			uee.printStackTrace();
			return null ;
		}
	}
	
	/**
	 * get Current Date String
	 * 
	 * @return String
	 */
	static public String getAccountantInterval ( ) throws SQLException
	{
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		String strMonth = String.valueOf ( calendar.get ( Calendar.MONTH ) + 1 ) ;
		if (strMonth.length ( ) == 1)
		{
			strMonth = "0" + strMonth ;
		}
		String strDay = String.valueOf ( calendar.get ( Calendar.DATE ) ) ;
		if (strDay.length ( ) == 1)
		{
			strDay = "0" + strDay ;
		}
		return calendar.get ( Calendar.YEAR ) + "��" + strMonth + "��";
	}
	
	/**
	 * get Current Date String
	 * 
	 * @return String
	 */
	static public String getCurrentDate ( ) throws SQLException
	{
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		String strMonth = String.valueOf ( calendar.get ( Calendar.MONTH ) + 1 ) ;
		if (strMonth.length ( ) == 1)
		{
			strMonth = "0" + strMonth ;
		}
		String strDay = String.valueOf ( calendar.get ( Calendar.DATE ) ) ;
		if (strDay.length ( ) == 1)
		{
			strDay = "0" + strDay ;
		}
		return calendar.get ( Calendar.YEAR ) + "-" + strMonth + "-" + strDay ;
	}
	
	/**
	 *  
	 */
	public static String normalizeString ( String strValue )
	{
		return ((strValue == null) ? "" : strValue.trim ( )) ;
	}
	/**
	 * �������JDK1.4 String::split�Ĺ��� @param1 splitedStr ��Ҫ���ָ��String @param2
	 * splitConditon �ָ�������ַ�
	 */
	public static String[] splitString ( String splitedStr ,
			String splitConditon )
	{
		int start = 0 ;
		int end = 0 ;
		ArrayList list = new ArrayList ( ) ;
		String tmp = null ;
		while (true)
		{
			end = splitedStr.indexOf ( splitConditon , start ) ;
			if (end == -1)
			{
				tmp = splitedStr.substring ( start , splitedStr.length ( ) ) ;
				list.add ( tmp ) ;
				break ;
			}
			tmp = splitedStr.substring ( start , end ) ;
			list.add ( tmp ) ;
			start = end + splitConditon.length ( ) ;
		}
		String[] res = new String[list.size ( )] ;
		for (int i = 0; i < list.size ( ); i++)
		{
			res[i] = (String) list.get ( i ) ;
		}
		return res ;
	}
	/**
	 * ��ʽ��Ʊ�ݺ���
	 * 
	 * @param sDraftCode
	 *  Ʊ�ݺ��빲30λ��Ʊ�ݺ����һλ��ڶ�λ֮�䡢��ʮ��λ���ʮ��λ֮�䡢�ڶ�ʮһλ��ڶ�ʮ��λ֮�䡢�ڶ�ʮ��λ�����ʮλ֮��ֱ��һ��
	 */
	public static String formatSDraftCode(String sDraftCode) {
		if (sDraftCode == null || sDraftCode.length() == 0) {
			return "";
		} else {
			
			String formatSDraftCode = sDraftCode.substring(0, 1)+" "+sDraftCode.substring(1, 13)+" "+sDraftCode.substring(13, 21)+" "+sDraftCode.substring(21, 29)+" "+sDraftCode.substring(29, 30);
			return formatSDraftCode;
		}
	}
	public static String getCurrentUpperDate()
	{
		StringBuffer currentDate = new StringBuffer();
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		String strYear = String.valueOf(calendar.get(Calendar.YEAR));
		String strMonth = String.valueOf ( calendar.get ( Calendar.MONTH ) + 1 ) ;
		if (strMonth.length ( ) == 1)
		{
			strMonth = "0" + strMonth ;
		}
		String strDay = String.valueOf ( calendar.get ( Calendar.DATE ) ) ;
		if (strDay.length ( ) == 1)
		{
			strDay = "0" + strDay ;
		}
		
		//YEAR
		currentDate.append(lowerToUpper(strYear.toCharArray(),false));
		currentDate.append("��");
		//Month
		currentDate.append(lowerToUpper(strMonth.toCharArray(),true));
		currentDate.append("��");
		//Day
		currentDate.append(lowerToUpper(strDay.toCharArray(),true));
		currentDate.append("��");
		return  currentDate.toString();
	}
	/**
	 * ����ת��������
	 * @param date
	 * @return
	 */
	public static String getCurrentUpperDate(Timestamp date)
	{
		StringBuffer transformDate = new StringBuffer();
		String sTempDate = "";
		String[] sTempDateArray = new String[3];
		sTempDate = DataFormat.formatDate(date,1);
		sTempDateArray = splitString(sTempDate,"-");
		
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		String strYear = sTempDateArray[0];
		String strMonth = sTempDateArray[1];
		if (strMonth.length ( ) == 1)
		{
			strMonth = "0" + strMonth ;
		}
		String strDay = sTempDateArray[2] ;
		if (strDay.length ( ) == 1)
		{
			strDay = "0" + strDay ;
		}
		
		//YEAR
		transformDate.append(lowerToUpper(strYear.toCharArray(),false));
		transformDate.append("��");
		//Month
		transformDate.append(lowerToUpper(strMonth.toCharArray(),true));
		transformDate.append("��");
		//Day
		transformDate.append(lowerToUpper(strDay.toCharArray(),true));
		transformDate.append("��");
		return  transformDate.toString();
	}
	
	private static String lowerToUpper(char[] c,boolean isMonthOrDay)
	{
		String str = "";
		try
		{
			for(int i=0;i<c.length;i++)
			{
				if(isMonthOrDay && i == 0)
				{
					if(c[i] == '0')
						continue;
				}
				switch(c[i])
				{
					case '0':
						str += "��";
						break;
					case '1':
						str += "Ҽ";
						break;
					case '2':
						str += "��";
						break;
					case '3':
						str += "��";
						break;
					case '4':
						str += "��";
						break;
					case '5':
						str += "��";
						break;
					case '6':
						str += "½";
						break;
					case '7':
						str += "��";
						break;
					case '8':
						str += "��";
						break;
					case '9':
						str += "��";
						break;
					}
					if(isMonthOrDay && i == 0)
						str += "ʰ";
				}	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * @param timeStr
	 * @param type
	 * @return
	 * ת��   2007-10-01 08:08:08.296
	 */
	static public java.sql.Timestamp FormatTime(String timeStr,int type)
	{
		java.sql.Timestamp ts = null;
		java.util.Date dt = null;
		java.util.Calendar calendar = Calendar.getInstance();
		
		int year = 1;
		int month = 1;
		int day =1;
		int hour =1;
		int minute =1;
		int second =1;
		
		if(type == DT_YYYYMMDD_T_HHMMSS)
		{
		   year = Integer.parseInt(timeStr.substring(0,4));
		   month =Integer.parseInt(timeStr.substring(5,7));
		   day = Integer.parseInt(timeStr.substring(8,10));

		   hour =Integer.parseInt(timeStr.substring(11,13));
		   minute =Integer.parseInt(timeStr.substring(14,16));
		   second = Integer.parseInt(timeStr.substring(17,19));
		}
		
	    calendar.set(year, month - 1, day, hour, minute, second);
	    dt = calendar.getTime();
	    ts = new java.sql.Timestamp(dt.getTime());
		
		return ts;
	}
	
	
	/**
	 * @throws ParseException 
	 * ���ܣ����ڲ�ͬ�ָ�ʽ��ת��Timestamp
	 */
	static public Timestamp parseTimestamp(String timeStr, int type) throws Exception
	{
		Timestamp timestamp = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		
		try {
			if(timeStr == null || timeStr.equals("")){
				return null;
			}
			
			dateFormat.applyPattern(DataFormat.getDateConvertString(type));
			Date date = dateFormat.parse(timeStr);
			timestamp = new Timestamp(date.getTime());
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ת��Timestamp���ڴ���");
		}
		return timestamp;
	}
	
	/**
	 * ��ʽ�����֣����磺12345ת��Ϊ12,345
	 * 
	 * @param dValue
	 *            ����ʽ������ֵ
	 * @param iScale
	 *            С�������λ��,���㲹0
	 * @return
	 */
	public static String formatAmount ( double dValue , int iScale )
	{
		DecimalFormat df = null ;
		StringBuffer sPattern = new StringBuffer ( ",##0" ) ;
		if (iScale > 0)
		{
			sPattern.append ( "." ) ;
			for (int i = 0; i < iScale; i++)
			{
				sPattern.append ( "0" ) ;
			}
		}
		df = new DecimalFormat ( sPattern.toString ( ) ) ;
		return df.format ( dValue ) ;
	}
	
	/**
	 * ��ʽ�����֣����磺��5ת��Ϊ4λ�ַ�����õ�0005
	 * 
	 * @param dValue
	 *            ����ʽ������ֵ
	 * @param nWidth
	 *            ��Ҫת����λ��
	 * @return
	 */
	public static String formatInt ( long lValue , int nWidth )
	{
		String strReturn = "" + lValue ;
		int initLength = strReturn.length ( ) ;
		for (int i = nWidth; i > initLength; i--)
		{
			strReturn = "0" + strReturn ;
		}
		return strReturn ;
	}
	/**
	 * ��ʽ���ַ���
	 * 
	 * @param strData
	 *            �ַ�������
	 */
	public static String formatString(String strData) {
		if (strData == null || strData.length() == 0) {
			return "";
		} else {
			return strData;
		}
	}

	/**
	 * ��ʽ���ַ��� for print ninh 2004-11-24
	 * 
	 * @param strData
	 *            �ַ�������
	 */
	public static String formatStringForPrint(String strData) {
		if (strData == null || strData.length() == 0) {
			return "&nbsp;";
		} else {
			return strData;
		}
	}
	
	/**
	 * ��ʼ����ʵ�ʼ������
	 * add by Forest
	 * @return
	 */
	static public int getIntervalDays(Timestamp tsStart,Timestamp tsEnd)
	{
		int lIntervalDays = 0;
		
		//while (tsStart.compareTo(tsEnd) <=0)
		while (!tsStart.after(tsEnd))//changed by Huang ye
		{
			tsStart = getNextDate(tsStart,1);
			lIntervalDays++;
		}
		return lIntervalDays;
	}
	/**
	 * �õ��¼���
	 * 
	 * @param tsDate
	 *            ����
	 */
	static public java.sql.Timestamp getNextDate ( java.sql.Timestamp tsDate ,
			int nDay )
	{
		if (null == tsDate)
			return null ;
		GregorianCalendar calendar = new GregorianCalendar ( ) ;
		calendar.setTime ( tsDate ) ;
		calendar.add ( Calendar.DATE , nDay ) ;
		java.util.Date resDate = calendar.getTime ( ) ;
		return new Timestamp ( resDate.getTime ( ) ) ;
	}
  	/**
	 * �õ��¼���
	 * 
	 * @param tsDate ����,nMonth ����
	 *         
	 */
	 public static java.sql.Timestamp getNextMonth ( java.sql.Timestamp tsDate ,
			int nMonth )
	{
		if (null == tsDate)
			return null ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.setTime ( tsDate ) ;
		//return getDateTime(calendar.get(Calendar.YEAR),
		// calendar.get(Calendar.MONTH) + 1 + nMonth,
		// calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY),
		// calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		return getDateTime ( calendar.get ( Calendar.YEAR ) , calendar
				.get ( Calendar.MONTH )
				+ 1 + nMonth , calendar.get ( Calendar.DATE ) , 0 , 0 , 0 ) ;
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
	public static java.sql.Timestamp getDateTime ( int year , int month ,
				int day , int hour , int minute , int second )
	{
		java.sql.Timestamp ts = null ;
		java.util.Date dt = null ;
		java.util.Calendar calendar = Calendar.getInstance ( ) ;
		calendar.set ( year , month - 1 , day , hour , minute , second ) ;
		calendar.set(Calendar.MILLISECOND,0);
		dt = calendar.getTime ( ) ;
		ts = new java.sql.Timestamp ( dt.getTime ( ) ) ;
		return ts ;
	}
	
	/**
	* �µ����� YYYY/MM/DD �ո�ȡ��
	* 
	* @param dt_st
	*            YYYY/MM/DD �_ʼ��
	* @param monthCount
	*            ����
	* @return String
	*/
	public static String addMonth(String dt_st, int monthCount) {
	   if (monthCount <= 0) {
	    return dt_st;
	   }
	   int year = Integer.parseInt(dt_st.substring(0, 4));
	   int month = Integer.parseInt(dt_st.substring(5, 7)) - 1;
	   int date = Integer.parseInt(dt_st.substring(8, 10));

	   GregorianCalendar calendar = new GregorianCalendar(year, month, date);

	   calendar.add(Calendar.MONTH, monthCount);
	   //calendar.add(Calendar.DAY_OF_MONTH, -1);

	   java.sql.Date dateOfEft = new java.sql.Date(calendar.getTimeInMillis());

	   return dateOfEft.toString();//.replace('-', '/')
	}
	
	/**
	 * �õ��¼���
	 * 
	 * @param tsDate
	 *            ����
	 */
	static public java.sql.Timestamp getNextYear ( java.sql.Timestamp tsDate ,
			int nYear )
	{
		if (null == tsDate)
			return null ;
		GregorianCalendar calendar = new GregorianCalendar ( ) ;
		calendar.setTime ( tsDate ) ;
		calendar.add ( Calendar.YEAR , nYear ) ;
		java.util.Date resDate = calendar.getTime ( ) ;
		return new Timestamp ( resDate.getTime ( ) ) ;
	}
	
	/**
	 * ����˾������Ϣ��ʽ
	 * 
	 * @param rentryId	�����˺�
	 * @param rParticipantBankNo �����к�
	 * @param rphone	��ϵ�绰
	 * @param rdwcRmrk	��ע
	 *            
	 */
	public static String getEntry(String rentryId,String rParticipantBankNo,String rphone,String rdwcRmrk)
	{
		String str="";
		if(rentryId!=null&&rentryId.length()>0)
		{
			str="#"+rParticipantBankNo+"@"+rentryId+"#";
		}
		if(rphone!=null&&rphone.length()>0)
		{
			str=str+"�绰"+rphone;
		}
		if(rdwcRmrk!=null&&rdwcRmrk.length()>0)
		{
			if(rentryId!=null&&rentryId.length()>0)
			{
				str=str+"*"+rdwcRmrk;
			}else{
				str=rdwcRmrk;
			}
		}
		return str;
	}
	/**
	 * �ֽ����˾������Ϣ��ʽ
	 * @param rdwcRmrks	�����˺ţ������кţ���ϵ�绰����ע��Ϣ�ļ���
	 * @param rentryId	�����˺�
	 * @param rParticipantBankNo �����к�
	 * @param rphone	��ϵ�绰
	 * @param rdwcRmrk	��ע
	 *            
	 */
	public static String[] getBreakEntry(String rdwcRmrks)
	{
		String rdwcRmrk = "";
		String rentryId="";
		String rParticipantBankNo="";
		String rphone="";
		if(rdwcRmrks!=null&&rdwcRmrks.length()>0)
		{
			if(rdwcRmrks.indexOf("#")!=-1)
			{
				rParticipantBankNo=rdwcRmrks.substring(1,rdwcRmrks.indexOf("@"));
				 rentryId=rdwcRmrks.substring(rdwcRmrks.indexOf("@")+1,rdwcRmrks.lastIndexOf("#"));
				if(rdwcRmrks.length()>rdwcRmrks.lastIndexOf("#"))
				{
					if(rdwcRmrks.substring(rdwcRmrks.lastIndexOf("#")+1,rdwcRmrks.length()).length()>2)
					{
						if(rdwcRmrks.substring(rdwcRmrks.lastIndexOf("#")+1,rdwcRmrks.lastIndexOf("#")+3).equals("�绰"))
						{
							if(rdwcRmrks.indexOf("*")!=-1)
							{
								rphone=rdwcRmrks.substring(rdwcRmrks.lastIndexOf("#")+3, rdwcRmrks.indexOf("*"));
								if(rdwcRmrks.length()>rdwcRmrks.indexOf("*"))
								{
									rdwcRmrk=rdwcRmrks.substring(rdwcRmrks.lastIndexOf("*")+1,rdwcRmrks.length());	
								}
							}
							else
							{
								rphone=rdwcRmrks.substring(rdwcRmrks.lastIndexOf("#")+3, rdwcRmrks.length());
							}
						}
					}
				}
			}else
			{
				rdwcRmrk=rdwcRmrks;
			}
		}
		String[] str= new  String[]{rentryId,rParticipantBankNo,rphone,rdwcRmrk};
		return str;
	}
	
	public static String[] formatFrontDraftAmount(double amount) throws Exception
	{
		String[] strAmount = new String[12];
		try {
			String strDraftAmount = DataFormat.formatNumber(amount, 2);
			String draftIsseAmtArray = strDraftAmount.split("\\.")[0].concat(strDraftAmount.split("\\.")[1] == null ? "00" : strDraftAmount.split("\\.")[1]);
			String _zeroArray = new String();
			for(int i = 0; i < (12 - draftIsseAmtArray.length()); i++)
			{
			   if(i == 11 - draftIsseAmtArray.length())   
			   {
					_zeroArray = _zeroArray.concat("��");
			   }
			   else
			   {
			   		_zeroArray = _zeroArray.concat(" ");
			   }
			}
			String[] strDraftIsseAmtArray = _zeroArray.concat(draftIsseAmtArray).split("");
			for(int i = 0; i < 12; i++)
			{
				strAmount[i]=new String();
				strAmount[i] = strDraftIsseAmtArray[i+1];
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("��ֽ�����");
		}
		return strAmount;
	}
	
	/**
	 * ��double��ת����Stirng�ͣ���ȥ�������".0";
	 * add by xiangzhou 20120807
	 * @param dValue
	 * @param iScale
	 * @return
	 */
	public static String toString (double dValue)
	{
		String result = String.valueOf(dValue);
		if(result.indexOf(".0")>0){
			result = result.substring(0, result.indexOf(".0"));
		}
		return result ;
	}
	
	public static void main(String[] args){
		try {
			//System.out.println(DataFormat.parseDate("", 1));
			//System.out.println(DataFormat.getCurrentUpperDate());
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[0]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[1]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[2]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[3]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[4]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[5]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[6]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[7]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[8]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[9]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[10]);
//			System.out.println(""+DataFormat.formatFrontDraftAmount(160000.00)[11]);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

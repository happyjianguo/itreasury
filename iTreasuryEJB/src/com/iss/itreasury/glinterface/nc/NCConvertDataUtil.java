package com.iss.itreasury.glinterface.nc;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.settlement.util.SETTConstant;


/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class NCConvertDataUtil
{
	/**
	 * 默认时间格式，处理时间用到
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * Constructor for CommonUtil.
	 */
	public NCConvertDataUtil()
	{
		super();
	}

	public static java.util.Date convertStringToDate(String date, String format, Locale locale) throws Exception
	{
		if (date == null)
			throw new Exception("offer date String is null.");
		if (format == null || "".equals(format))
			throw new Exception("offer format is null.");

		SimpleDateFormat dateFormat = null;

		if (locale == null)
		{
			dateFormat = new SimpleDateFormat(format);
		}
		else
		{
			dateFormat = new SimpleDateFormat(format, locale);
		}

		//解析出的对象包含毫秒值
		java.util.Date dt = dateFormat.parse(date);
		return new java.sql.Timestamp(dt.getTime());
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return Date
	 * @throws Exception
	 */
	public static java.util.Date convertStringToDate(String date, String format) throws Exception
	{
		return convertStringToDate(date, format, null);
	}

	public static java.util.Date convertStringToDate(String date) throws Exception
	{
		return convertStringToDate(date, DEFAULT_DATE_FORMAT, null);
	}

	public static String couvertLongCodeToCurrencyName(long lCode)
	{
		//chenge wxsu 20080605
		//return "人民币";
		return "CNY";
	}

	public static String convertDateToString(java.util.Date date, String format, Locale locale) throws Exception
	{
		if (date == null)
			throw new Exception("offer date is null.");
		if (format == null || "".equals(format))
			throw new Exception("offer format is null.");

		SimpleDateFormat dateFormat = null;

		if (locale == null)
		{
			dateFormat = new SimpleDateFormat(format);
		}
		else
		{
			dateFormat = new SimpleDateFormat(format, locale);
		}

		return dateFormat.format(date);
	}

	public static String convertDateToString(java.util.Date date, String format) throws Exception
	{
		return convertDateToString(date, format, null);
	}

	public static String convertDateToString(java.util.Date date) throws Exception
	{
		return convertDateToString(date, DEFAULT_DATE_FORMAT, null);
	}

	public static long convertNCSubject(String strType)
	{
		long lTypeID = -1;
		if (strType != null)
		{
			if (strType.equals("ZC"))
			{
				lTypeID = SETTConstant.SubjectAttribute.ASSET;
			}
			if (strType.equals("FZ"))
			{
				lTypeID = SETTConstant.SubjectAttribute.DEBT;
			}
			if (strType.equals("QY"))
			{
				lTypeID = SETTConstant.SubjectAttribute.RIGHT;
			}
			if (strType.equals("SY"))
			{
				lTypeID = SETTConstant.SubjectAttribute.INCOME;
			}
			if (strType.equals("CB"))
			{
				lTypeID = SETTConstant.SubjectAttribute.PAYOUT;
			}
		}
		return lTypeID;
	}

	public static String convertVoucherIDToNC(String transNo)
	{
		String result = null;
		if (transNo != null)
		{
			long day = Long.parseLong(transNo.substring(6, 8));

			long number = Long.parseLong(transNo.substring(12, 17));

			if (number > 1999)
			{
				day = day + 33 * 2;
			}
			else if (number > 999)
			{
				day = day + 33;
			}

			result = MessageFormat.format("{0,number,#00}", new Object[] { new Long(day)}) + transNo.substring(14, 17);
		}

		return result;
	}

	public static String convertVoucherIDFromNC(String transNo, String id)
	{
		String result = null;
		if (transNo != null && id != null)
		{
			String strTemp = transNo.substring(0, 12);

			long day = Long.parseLong(id.substring(0, 2));

			long number = Long.parseLong(id.substring(2, 5));
			
			//System.out.println("u850:" + id + " " + number);

			if (day > 33 * 2)
			{
				day = day - 33 * 2;

				number = number + 2000;
			}
			else if (day > 33)
			{
				day = day - 33;

				number = number + 1000;
			}

			result = strTemp + MessageFormat.format("{0,number,#00000}", new Object[] { new Long(number)});
		}

		return result;
	}

	public static void main(String[] args)
	{
		try
		{
			java.sql.Timestamp date = (java.sql.Timestamp) convertStringToDate("12:08:56 999", "HH:mm:ss SSS");
			System.out.println(date);
			System.out.println(convertStringToDate("12:08:56", "HH:mm:ss").getTime());
			System.out.println(convertDateToString(Env.getCurrentSystemDate(), "MM"));
			System.out.println(date.getTime());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

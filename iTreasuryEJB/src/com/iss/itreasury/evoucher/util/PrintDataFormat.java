package com.iss.itreasury.evoucher.util;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.iss.itreasury.util.ChineseCurrency;
import com.iss.itreasury.util.DataFormat;

public class PrintDataFormat {

	//格式化金额,处理科学计数法产生的问题
	public static String getFormatAmount(double amount) throws Exception
	{
		String strAmount = "0.00";
		
		if(amount != 0.0)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			strAmount = decimalFormat.format(amount);
			strAmount = DataFormat.formatListAmount(Double.parseDouble(strAmount), 2);
		}
		
		return strAmount;
	}
	
	//格式化金额,处理科学计数法产生的问题
	public static String getChineseFormatAmount(double amount, long currencyID) throws Exception
	{
		String strChineseAmount = "零元";
		
		if(amount != 0.0)
		{
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			strChineseAmount = decimalFormat.format(amount);
			strChineseAmount = ChineseCurrency.showChinese(strChineseAmount, currencyID);
		}
		
		return strChineseAmount;
	}

}

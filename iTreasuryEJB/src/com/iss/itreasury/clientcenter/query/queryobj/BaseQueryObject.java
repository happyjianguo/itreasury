/*
 * Created on 2004-01-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.clientcenter.query.queryobj;

import java.sql.Timestamp;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;

/**
 * @author rxie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BaseQueryObject extends SettlementDAO
{
	public BaseQueryObject()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 存款账户类型编码，包括活期和定期 
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getDepositAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getDepositAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}
	/**
	 * 活期存款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getCurrentAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getCurrentAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}
	/**
	 * 定期存款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getFixAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getFixAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}
	/**
	 * 贷款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getLoanAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getLoanAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 
	 * @param lCurrencyID
	 * @param lExcludeFlag : 1 - 排除贴现
	 * @return
	 */
	public String getLoanAccountType(long lCurrencyID,long lOfficeID, long lExcludeFlag)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getLoanAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
		{
			if (lExcludeFlag == 1 && (!SETTConstant.AccountType.isDiscountAccountType(array[i])))
			{
				str = str + array[i] + ",";
			}
		}
		return str.substring(0, str.length() - 1);
	}
	public boolean isToday(long lOfficeID, long lCurrencyID, Timestamp tsQueryDate)
	{
		boolean b = true;
		Timestamp tsOpenDate = Env.getSystemDate(lOfficeID, lCurrencyID);
		if (tsOpenDate != null && tsQueryDate != null)
		{
			if (tsOpenDate.toString().substring(0, 10).equals(tsQueryDate.toString().substring(0, 10)))
				b = true;
			else
				b = false;
		}
		return b;
	}
	/**
	 * 取得当天的活期利率
	 * @param AccountOpenDate ： 活期账户开户日
	 * @param QueryDate ： 查询时间
	 * @param InterestPlanID： 利率计划
	 * @param Balance： 账户余额
	 * @return
	 */
	public static double getCurrentInterestRate(Timestamp AccountOpenDate, Timestamp QueryDate, long InterestPlanID, double Balance)
	{

		double CurrentInterestRate = 0.72;
		try
		{
			long nDays = 100;
			if (AccountOpenDate != null && QueryDate != null)
			{
				nDays = IDate.intervalDays(AccountOpenDate, QueryDate);
			}
			CurrentInterestRate = UtilOperation.getCurrentInterestRate(null, InterestPlanID, Balance, AccountOpenDate, nDays);

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			CurrentInterestRate = 0.72;
		}
		return CurrentInterestRate;
	}
}

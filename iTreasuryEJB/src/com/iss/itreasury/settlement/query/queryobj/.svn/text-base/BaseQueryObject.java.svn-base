/*
 * Created on 2003-11-1
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Timestamp;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.Log;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BaseQueryObject extends SettlementDAO
{

	/**
	 * 
	 */
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
		System.out.println("进入 getDepositAccountType");
		
		String str = "";
		long[] array = SETTConstant.AccountType.getDepositAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		
		System.out.println("出去 getDepositAccountType:"+str);
		return (str.length()!=0?str.substring(0, str.length() - 1):str);
		
	}
	/**
	 * 存款账户类型编码，包括活期和定期，通知( 去掉活期中的 委托存款 )
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 * jiangqi 2011-01-28
	 */
	public String getDepositAccountTypeWithoutWTDeposit(long lCurrencyID,long lOfficeID)
	{
		System.out.println("进入 getDepositAccountTypeWithoutWTDeposit");
		
		String str = "";
		long[] array = SETTConstant.AccountType.getDepositAccountTypeCodeForAudit(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		
		System.out.println("出去 getDepositAccountTypeWithoutWTDeposit:"+str);
		return (str.length()!=0?str.substring(0, str.length() - 1):str);
		
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
	 * 活期存款账户类型编码(不包括委托存款账户)
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getCurrentAccountType(long lCurrencyID,long lOfficeID,long type)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getCurrentAccountTypeCode(lCurrencyID,lOfficeID,type);
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
		if(str!=null&&str.length()>0)
		{
			str=str.substring(0, str.length() - 1);
		}
		return str;
	}
	/**
	 * 通知存款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getNotifyAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getNotifyAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		if(str!=null&&str.length()>0)
		{
			str=str.substring(0, str.length() - 1);
		}
		return str;
	}
	/**
	 * 保证金存款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getMarginAccountType()
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getMarginAccountTypeCode();
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		if(str.length()>0)
		{
			str= str.substring(0, str.length() - 1);
		}
		
		return str;
		
	}
	/**
	 *  信托贷款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getTrustAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getTrustAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}
	/**
	 *  委托贷款账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getConsignAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getConsignAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}
	/**
	 *  贴现账户类型编码
	 * @param lCurrencyID
	 * @return: such as 1,2,3,4
	 */
	public String getDiscountAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getDiscountAccountTypeCode(lCurrencyID,lOfficeID);
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
		System.out.println("进入1 getLoanAccountType");
		String str = "";
		long[] array = SETTConstant.AccountType.getLoanAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		System.out.println("出去1 getLoanAccountType:"+str);
		return (str.length()!=0?str.substring(0, str.length() - 1):str);
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
			Log.print("AccountOpenDate = "+AccountOpenDate);
			Log.print("QueryDate = "+QueryDate);
			
			long nDays = 100;
			if (AccountOpenDate != null && QueryDate != null)
			{
				nDays = IDate.intervalDays(AccountOpenDate, QueryDate);
			}
			CurrentInterestRate = UtilOperation.getCurrentInterestRate(null, InterestPlanID, Balance, QueryDate, nDays);

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			CurrentInterestRate = 0.72;
		}
		return CurrentInterestRate;
	}
	/**
	 * 贷款交易类型编码，信托贷款发放、信托贷款收回、信托贷款收回、
	 * 委托贷款发放、委托贷款收回,贴现发放,贴现收回,多笔贷款收回
	 * 
	 * @param nothing
	 * @return long[]
	 */
	protected long[] getLoanTransactionTypeCode() {
		long[] arrayReturn = { 
			SETTConstant.TransactionType.TRUSTLOANGRANT, 
			SETTConstant.TransactionType.TRUSTLOANRECEIVE, 
			SETTConstant.TransactionType.CONSIGNLOANGRANT, 
			SETTConstant.TransactionType.CONSIGNLOANRECEIVE, 
			SETTConstant.TransactionType.DISCOUNTGRANT, 
			SETTConstant.TransactionType.DISCOUNTRECEIVE, 
			SETTConstant.TransactionType.MULTILOANRECEIVE 
		};
		return arrayReturn;
	}
	/**
	 * 贷款交易类型编码
	 * @param nothing
	 * @return: such as 17,18,19,20,21,22,23
	 */
	protected String getLoanTransactionType() {
		String str = "";
		long[] array = this.getLoanTransactionTypeCode();
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 将金额为负的值-Value转化为(Value),为正的值Value转化为Value
	 *  -123456.00-->(123,456.00),123456.00-->123,456.00
	 * @param nothing
	 * @return long[]
	 */
	protected static String amountTrim(double dAmount, int iNum) {
		String sAmount = "";
		if(dAmount<0.0) {
			dAmount=dAmount*(-1);
			sAmount = DataFormat.formatDisabledAmount(dAmount);
			sAmount = sAmount.substring(0,sAmount.length()-iNum);
			sAmount = "("+sAmount+")";
		} else if (dAmount>0.0) {
			sAmount = DataFormat.formatDisabledAmount(dAmount);
			sAmount = sAmount.substring(0,sAmount.length()-iNum);
		} else {
			sAmount="&nbsp;&nbsp;";
		}
		return sAmount;
	}
	/**
	 * 
	 * @param nothing
	 * @return long[]
	 */
	protected long[] getAccountTypeCode() {
		long[] arrayReturn = SETTConstant.AccountType.getDepositAccountTypeCode();
		return arrayReturn;
	}
}

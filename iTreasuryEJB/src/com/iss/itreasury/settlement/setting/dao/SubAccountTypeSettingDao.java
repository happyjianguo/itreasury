package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

public class SubAccountTypeSettingDao {
	
public String querySubAccountSQL(long lAccountTypeID, long lOfficeID, long lCurrencyID){
		
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer strSQLBuff = null;
		
		try
		{
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select * from Sett_SubAccountType_Current \n");
			strSQLBuff.append(" where nOfficeID = " + lOfficeID + " and nCurrencyID = " + lCurrencyID + " and nStatusID = " + Constant.RecordStatus.VALID + " \n");
			if (lAccountTypeID > 0)
			{
				strSQLBuff.append(" and nAccountTypeID = " + lAccountTypeID);
			}
			strSQLBuff.append(" order by id ");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return strSQLBuff.toString();
	}
public String findAllSubAccountTypeFixedDepositByAccountType(long lAccountTypeID,long lOfficeID, long lCurrencyID){
	
	Log.print("**********************in findAccountTransactionType*********************");
	StringBuffer strBuff = null;
	
	try
	{
		strBuff = new StringBuffer();
		strBuff.append("  SELECT A.*,ROWNUM NUM " );
		strBuff.append(" FROM ( SELECT s.ID, s.nFixedDepositMonthID, s.nAccountTypeID,s.sSubjectCode,s.sPayInterestSubject,s.sBookedInterestSubject,s.nClientID,s.nAccountID ,s.sdepositno , b.sAccountType from sett_SubAccountType_Fixed s,sett_AccountType b WHERE ") ;
		strBuff.append(" s.nAccountTypeID = b.ID and s.nStatusID=1  AND  s.nAccountTypeID= "+lAccountTypeID+" and s.nOfficeID="+lOfficeID+" and s.nCurrencyID = "+lCurrencyID);
		strBuff.append(" ORDER BY s.ID");
		strBuff.append(" )  A ");
		System.out.println("sql is :"+strBuff.toString());
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	return strBuff.toString();
}
public String findAllAccountTypeByGroupID(long lAccountGroupID,long lOfficeID, long lCurrencyID){
	
	Log.print("**********************in findAccountTransactionType*********************");
	StringBuffer strSQLBuff = null;
	
	try
	{
		strSQLBuff = new StringBuffer();
		strSQLBuff.append(" SELECT * \n");
		strSQLBuff.append(" FROM   sett_AccountType \n");
		strSQLBuff.append(" WHERE  nStatusID="+Constant.RecordStatus.VALID+" \n");
		if (lAccountGroupID > 0)
		{
			strSQLBuff.append(" AND  nAccountGroupID="+lAccountGroupID);
			strSQLBuff.append(" AND  OFFICEID="+lOfficeID);
			strSQLBuff.append(" AND  CURRENCYID="+lCurrencyID);
		}
		System.out.println(strSQLBuff.toString());
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	return strSQLBuff.toString();
}
public String findByAccountTypeID(long lAccountTypeID,long lOfficeID,long lCurrencyID){
	
	Log.print("**********************in findAccountTransactionType*********************");
	StringBuffer strSQLBuff = null;
	
	try
	{
		strSQLBuff = new StringBuffer();
		strSQLBuff.append(" select * from SETT_SUBACCOUNTTYPE_LOAN \n");
		strSQLBuff.append(" where nOfficeID = "+lOfficeID+" and nCurrencyID = "+lCurrencyID+" and nStatusID = "+Constant.RecordStatus.VALID+" \n");
		if(lAccountTypeID > 0)
		{
			strSQLBuff.append(" and nAccountTypeID = "+lAccountTypeID );
		}
		strSQLBuff.append(" order by nSerialNo ");
		System.out.println(strSQLBuff.toString());
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	return strSQLBuff.toString();
}
	

}

package com.iss.itreasury.settlement.setting.dao;

import com.iss.itreasury.util.Log;

/**
 * 特殊业务类型设置
 * @author 宋雯霄
 *
 */

public class Sett_SpecialOperationQueryDAO {
	
	public String querySpecialOperationSql(long lcurrencyID,long lOfficeID, long lStartID, long lEndID, String strContext) 
	{
		String strTmpSQL = "";
		String strSQL = "";
		try
		{
			strSQL = " select * from (select a.* from Sett_SpecialOperation a where a.nStatusID=1  and nOfficeID= "+lOfficeID+" and ncurrencyid="+lcurrencyID;
			if (lStartID > 0)
			{
				strSQL = strSQL + " and ID>=" + lStartID;
			}
			if (lEndID > 0)
			{
				strSQL = strSQL + " and ID<=" + lEndID;
			}
			if (strContext != null && !strContext.equals("") && !strContext.equals("null"))
			{
				strSQL = strSQL + " and trim(sContent)='" + strContext.trim() + "'";
			}
			strSQL = strSQL + strTmpSQL + " ) a ";
			Log.print(strSQL);
			
			System.out.println("sql=" + strSQL);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strSQL;
	}

}

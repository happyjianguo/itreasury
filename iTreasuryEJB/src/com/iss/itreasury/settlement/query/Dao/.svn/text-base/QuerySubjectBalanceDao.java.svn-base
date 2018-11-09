package com.iss.itreasury.settlement.query.Dao;

import java.sql.Timestamp;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 *  ¿ÆÄ¿Óà¶î²éÑ¯
 * @author songwenxiao
 *
 */
public class QuerySubjectBalanceDao {
	
	Log4j logger = null;
	
	public QuerySubjectBalanceDao()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	public String querySubjectBalanceSQL(long lOfficeID,long lCurrencyID,Timestamp tsStart,Timestamp tsEnd,String strSubjectStart,String strSubjectEnd, long lUnit){
		
		Log.print("**********************in findAccountTransactionType*********************");
		String strSQL = "";
		String strSQLRecord="";
		
		try
		{
			strSQL = " select a.id,a.dtgldate,a.nofficeid,a.ncurrencyid,a.sglsubjectcode,a.nbalancedirection,a.mdebitbalance/"+lUnit+" mdebitbalance,a.mcreditbalance/"+lUnit+" mcreditbalance,a.mdebitamount/"+lUnit+" mdebitamount,a.mcreditamount/"+lUnit+" mcreditamount,a.ndebitnumber,a.ncreditnumber,b.sSubjectName,b.nSubjectType from Sett_GlBalance a,Sett_VGlSubjectDefinition b "
		         +"where a.sGlSubjectCode=b.sSubjectCode(+) and a.nOfficeID = "+lOfficeID+" and a.nCurrencyID =  "+lCurrencyID+"  and b.nofficeid="+lOfficeID+" and b.ncurrencyid="+lCurrencyID+""; 
		if (!strSubjectStart.equals(""))
		{
			strSQL = strSQL + " and a.sGlSubjectCode>='" + strSubjectStart + "'";
		} 
		if (!strSubjectEnd.equals(""))
		{
			strSQL = strSQL + " and a.sGlSubjectCode<='" + strSubjectEnd + "'";
		} 
		if(tsStart  != null)
		{
		   strSQL = strSQL + " and a.dtGlDate>=to_date('" +DataFormat.getDateString(tsStart)+" ','yyyy-mm-dd') ";
		}
		if(tsEnd  != null)
		{
		   strSQL = strSQL + " and a.dtGlDate<=to_date('" +DataFormat.getDateString(tsEnd)+" ','yyyy-mm-dd')";
		}
		strSQL = strSQL + "  order by a.dtGlDate asc,a.sGlSubjectCode asc ";
		Log.print(" select * from (select a.*,ROWNUM r from (" + strSQL+") a )");
		
		strSQLRecord=" select a.*,ROWNUM r from (" + strSQL+") a ";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return strSQLRecord;
	}




	
	
	
	

}

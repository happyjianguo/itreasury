package com.iss.itreasury.settlement.query.Dao;

import java.sql.Timestamp;
import java.text.DateFormat;

import com.iss.itreasury.settlement.query.paraminfo.QCounterTransInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;

/**
 * 业务量统计
 * @author songwenxiao
 *
 */
public class QCounterTransDao {
	
	Log4j logger = null;
	
	public QCounterTransDao()
	{
		super();
		logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}
	
	public String queryCounterTransSQL(QCounterTransInfo paramInfo){
		
		Log.print("**********************in findAccountTransactionType*********************");
		StringBuffer sbSQL = new StringBuffer();
		String strSQLRecord="";
		
		try
		{
			
			sbSQL.setLength(0);
			
			sbSQL.append("    select TransactionTypeID,sum(PayAmount) SumPay,sum(ReceiveAmount) SumReceive,count(TransNo) nCount \n ");
			sbSQL.append("    from Sett_VTransaction \n ");
			sbSQL.append("    where OfficeID=" + paramInfo.getOfficeID() + " and CurrencyID=" + paramInfo.getCurrencyID() + " \n ");
			sbSQL.append("         and StatusID  in (3,4,5,6,7) "+" \n ");
			sbSQL.append("	and Execute between to_date('" +DataFormat.getDateString(paramInfo.getStartDate())+" ','yyyy-mm-dd')  " +
					"and  to_date('"+ DataFormat.getDateString(paramInfo.getEndDate())+"','yyyy-mm-dd') ");
			if (paramInfo.getUserID() > 0)
			{
				sbSQL.append("         and InputUserID=" + paramInfo.getUserID() + " \n ");
			}
			sbSQL.append("    group by TransactionTypeID  \n ");
			
			//查询记录
			strSQLRecord = "select * from   ( " + sbSQL.toString();
			strSQLRecord = strSQLRecord + " ) ";
			
			Log.print(sbSQL.toString());
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return strSQLRecord;
	}

}

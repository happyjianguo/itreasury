package com.iss.itreasury.settlement.transspecial.dao;

import com.iss.itreasury.settlement.transspecial.dataentity.QueryBySubSpecialTypeAndStatusInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * 特殊业务处理 链接查找方法SQL
 * @author liangli5
 *
 */
public class TransspecialDao {
	
	public String queryTransspecialSQL(QueryBySubSpecialTypeAndStatusInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		long[] statusids = null;
		statusids = qInfo.getStatusIDs();
		
		sbSQL.append("select * from (select id,stransno,noperationtypeid,npayaccountid,npaybankid,npaygeneralledgertypeid,nreceiveaccountid,nreceivebankid,nreceivegeneralledgertypeid,mpayamount,dtintereststart,dtexecute,sabstract,nstatusid");
		sbSQL.append(" from sett_transspecialoperation where npaydirection = 1 and Nreceivedirection = 2 and nofficeid= "+ qInfo.getOfficeID());
		sbSQL.append(" and ncurrencyid=" + qInfo.getCurrencyID());
		if (statusids != null && statusids.length == 1)
		{
			sbSQL.append(" and nstatusid="+statusids[0]);
		}
		else if (statusids != null && statusids.length == 2)
		{
			sbSQL.append(" and (nstatusid=" + statusids[0] + " or nstatusid=" + statusids[1] + ")");
		}
		
		if (qInfo.getTypeID() == 0)
		{ //处理查找
			sbSQL.append(" and ninputuserid="+qInfo.getUserID());
		}
		else if (qInfo.getUserID() > 0)
		{ //复核查找
			sbSQL.append(" and ncheckuserid="+qInfo.getUserID());
			if (qInfo.getDate() != null)
			{
				//复核日期
				sbSQL.append(" and dtexecute=TO_DATE('" + DataFormat.getDateString(qInfo.getDate()) + "','yyyy-mm-dd')");
			}
		}
		
		if(qInfo.getTransactiontypeid()>0 )
		{
			sbSQL.append(" and NTRANSACTIONTYPEID="+qInfo.getTransactiontypeid());
		}
		
		if(qInfo.getSubTransactiontypeid()>0)
		{
			sbSQL.append(" and NOPERATIONTYPEID="+qInfo.getSubTransactiontypeid());
		}
		
		sbSQL.append(" union ");
		
		sbSQL.append("select id,stransno,noperationtypeid,npayaccountid as nreceiveaccountid,npaybankid as nreceivebankid,npaygeneralledgertypeid as nreceivegeneralledgertypeid,nreceiveaccountid as npayaccountid,nreceivebankid as npaybankid,nreceivegeneralledgertypeid as npaygeneralledgertypeid,   mpayamount,dtintereststart,dtexecute,sabstract,nstatusid");
		sbSQL.append(" from sett_transspecialoperation where npaydirection = 2 and Nreceivedirection = 1 and nofficeid= "+ qInfo.getOfficeID());
		sbSQL.append(" and ncurrencyid=" + qInfo.getCurrencyID());
		if (statusids != null && statusids.length == 1)
		{
			sbSQL.append(" and nstatusid="+statusids[0]);
		}
		else if (statusids != null && statusids.length == 2)
		{
			sbSQL.append(" and (nstatusid=" + statusids[0] + " or nstatusid=" + statusids[1] + ")");
		}
		
		if (qInfo.getUserID() == 0)
		{ //处理查找
			sbSQL.append(" and ninputuserid="+qInfo.getUserID());
		}
		else if (qInfo.getTypeID() == 1)
		{ //复核查找
			sbSQL.append(" and ncheckuserid="+qInfo.getUserID());
			if (qInfo.getDate() != null)
			{
				//复核日期
				sbSQL.append(" and dtexecute=TO_DATE('" + DataFormat.getDateString(qInfo.getDate()) + "','yyyy-mm-dd')");
			}
		}
		
		if(qInfo.getTransactiontypeid()>0 )
		{
			sbSQL.append(" and NTRANSACTIONTYPEID="+qInfo.getTransactiontypeid());
		}
		
		if(qInfo.getSubTransactiontypeid()>0)
		{
			sbSQL.append(" and NOPERATIONTYPEID="+qInfo.getSubTransactiontypeid());
		}
		sbSQL.append(")");
		return sbSQL.toString();
	}

}

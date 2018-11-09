package com.iss.itreasury.settlement.query.Dao;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * 账户查询数据层
 * @author xiang
 *
 */
public class QTransAccountDao {
	
	public String queryAccountSQL(QueryTransAccountDetailWhereInfo qInfo){
		
		StringBuffer sbSQL= new StringBuffer();
		
		//add by zcwang 2008-04-01  判断是否查询当天的账户信息
		boolean isSelectToday = false;
		if(qInfo !=null)
		{
			Timestamp tempToday = Env.getSystemDate(qInfo.getOfficeID(),qInfo.getCurrencyID());
			if( DataFormat.formatDate(qInfo.getEndDate()).compareTo(DataFormat.formatDate(tempToday))==0)
			{
				isSelectToday = true;
			}
		}
		// select 
		sbSQL.append("select sett_Account.ID AccountID,sett_Account.sAccountNo AccountNo,Client.sCode ClientCode,Client.sName ClientName, \n");
		sbSQL.append( "'"+DataFormat.formatDate(qInfo.getStartDate())+"' StartDate,'"+DataFormat.formatDate(qInfo.getEndDate()) +"' EndDate, \n");
		if(!isSelectToday)
		{
			sbSQL.append(" sum(sett_SubAccount.mBalance) CurrentBalance,nvl(sum(sett_DailyAccountBalance.mBalance),0.00) HistoryBalance \n");
		}
		else
		{
			sbSQL.append(" sum(sett_SubAccount.mBalance) CurrentBalance,sum(sett_SubAccount.mBalance) HistoryBalance \n");
		}
		// from 
		if(!isSelectToday)
		{
			sbSQL.append(" from sett_Account ,sett_SubAccount,Client,sett_DailyAccountBalance \n");
		}
		else
		{
			sbSQL.append(" from sett_Account ,sett_SubAccount,Client \n");
		}
		// where 
		sbSQL.append(" where sett_SubAccount.nAccountID = sett_Account.ID and sett_Account.nClientID = Client.ID \n");
		if(!isSelectToday)
		{
			sbSQL.append(" and sett_SubAccount.ID = sett_DailyAccountBalance.nSubAccountID(+) \n");
		}
		sbSQL.append(" and sett_Account.nOfficeID = "+qInfo.getOfficeID()+" and sett_Account.nCurrencyID = "+qInfo.getCurrencyID()+" \n");
		sbSQL.append(" and sett_Account.nCheckStatusID="+SETTConstant.AccountCheckStatus.CHECK+" \n");
		if(!isSelectToday)
		{
			sbSQL.append(" and sett_DailyAccountBalance.dtDate(+) = to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd') \n");
		}
		if (qInfo.getStartClientCode() != null && qInfo.getStartClientCode().length() > 0)
			sbSQL.append(" and client.scode>='" + qInfo.getStartClientCode() + "'");
		if (qInfo.getEndClientCode() != null && qInfo.getEndClientCode().length() > 0)
			sbSQL.append(" and client.scode<='" + qInfo.getEndClientCode() + "'");
		if (qInfo.getStartAccountNo() != null && qInfo.getStartAccountNo().length() > 0)
			sbSQL.append(" and sett_Account.sAccountNo>='" + qInfo.getStartAccountNo() + "'");
		if (qInfo.getEndAccountNo() != null && qInfo.getEndAccountNo().length() > 0)
			sbSQL.append(" and sett_Account.sAccountNo<='" + qInfo.getEndAccountNo() + "'");
		//add by 2012-05-15 添加指定编号
		if(qInfo.getAccountnos()!= null && qInfo.getAccountnos().length() > 0){
			sbSQL.append(" and sett_Account.sAccountNo in ('"+qInfo.getAccountnos()+"')");
		}
		//add by xfma 2008-12-2 账户状态
		if (!qInfo.getAccountStatusIDs().equals("") && qInfo.getAccountStatusIDs() != null )
		{
			sbSQL.append(" and sett_Account.Nstatusid in ("+qInfo.getAccountStatusIDs()+")");
		}		
		 		
		//滤空表示在sett_transaccountdetail内是否有对应当前帐户的记录
		sbSQL.append(" and sett_Account.ID in (select distinct ntransaccountid from sett_transaccountdetail where nstatusID="+SETTConstant.TransactionStatus.CHECK);
		if(qInfo.getIsFilterNull() == 1)
		{
			//sbSQL.append(" and sett_Account.nStatusID <> " + SETTConstant.AccountStatus.CLOSE);
			//add by xfma 2008-12-2 按起息日统计
			if(qInfo.getIsIntrDate() == 1)
			{
				if(qInfo.getStartDate()!=null){
					sbSQL.append(" and dtintereststart>=to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')");
				}
				if(qInfo.getStartDate()!=null){
					sbSQL.append(" and dtintereststart<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')");
				}
			}
		}else
		{
			if(qInfo.getStartDate()!=null){
				sbSQL.append(" and DTEXECUTE>=to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')");
			}
			if(qInfo.getStartDate()!=null){
				sbSQL.append(" and DTEXECUTE<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')");
			}
			
		}
		sbSQL.append(")");
		
		sbSQL.append(" \n group by sett_Account.ID,sett_Account.sAccountNo,client.sCode,client.sName \n");
		//order by
		sbSQL.append(" \n order by AccountNo asc");
		
		return sbSQL.toString();
	}

}

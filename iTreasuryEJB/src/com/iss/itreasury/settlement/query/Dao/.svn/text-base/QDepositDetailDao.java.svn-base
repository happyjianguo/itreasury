package com.iss.itreasury.settlement.query.Dao;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.query.paraminfo.QueryDepositDetailWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;

/**
 * 账户查询数据层
 * @author xiang
 *
 */
public class QDepositDetailDao {
	
	public String getDepositDetaiSQL(QueryDepositDetailWhereInfo qdwi){
		
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		Timestamp tsToday = Env.getSystemDate(qdwi.getOfficeID(),qdwi.getCurrencyID());
			m_sbSQL.append(" select sett_Account.ID,sett_Account.Saccountno,Client.Scode,sett_Account.Sname,sett_accounttype.saccounttypecode,sett_account.naccounttypeid,sett_account.nstatusid,sett_account.dtopen \n");
			m_sbSQL.append(" ,AccountBalance.* \n");
			m_sbSQL.append(" from Client,sett_Account,sett_accounttype, \n");
			m_sbSQL.append(" (select sett_subaccount.nAccountID AccountID,sett_dailyaccountbalance.naccountid,sett_subaccount.ac_ninterestrateplanid,sum(round(sett_subaccount.mBalance,2)) sumAccountBalance,sum(sett_dailyaccountbalance.mbalance) sumAccountHistoryBalance \n");
			if(tsToday.toString().substring(0, 10).equals(qdwi.getDate().toString().substring(0, 10))){
				m_sbSQL.append(" from sett_subaccount,sett_dailyaccountbalance where sett_subaccount.ID = sett_dailyaccountbalance.nSubAccountid(+) and sett_dailyaccountbalance.dtdate(+)=to_date('"+DataFormat.formatDate(qdwi.getDate())+"','yyyy-mm-dd') \n");
			}else {
				m_sbSQL.append(" from sett_subaccount,sett_dailyaccountbalance where sett_subaccount.ID = sett_dailyaccountbalance.nSubAccountid and sett_dailyaccountbalance.dtdate=to_date('"+DataFormat.formatDate(qdwi.getDate())+"','yyyy-mm-dd') \n");
			}
			m_sbSQL.append(" group by sett_subaccount.nAccountID,sett_dailyaccountbalance.naccountid,sett_subaccount.ac_ninterestrateplanid) AccountBalance \n");
			m_sbSQL.append(" where Client.ID = sett_Account.nClientID \n");
			m_sbSQL.append(" and Accountbalance.AccountID(+) = Sett_Account.ID \n");
			m_sbSQL.append(" and sett_Account.Naccounttypeid = sett_accounttype.id \n");
			m_sbSQL.append(" and sett_Account.nOfficeID="+qdwi.getOfficeID()+" and sett_Account.nCurrencyID="+qdwi.getCurrencyID()+" \n");
			if(qdwi.getStartClientCode() != null && qdwi.getEndClientCode().length() >0 && qdwi.getEndClientCode() != null && qdwi.getEndClientCode().length() >0)
			{
				m_sbSQL.append(" and Client.sCode between '"+qdwi.getStartClientCode()+"' and '"+qdwi.getEndClientCode() +"' \n");
			}
			if(qdwi.getAccountType() != null && !"".equals(qdwi.getAccountType()) && qdwi.getAccountType().length()>0)
			{
				m_sbSQL.append(" and sett_Account.nAccountTypeID in ("+qdwi.getAccountType()+") \n");
			}
			if(qdwi.getAccountStatusID() > 0)
			{
			    m_sbSQL.append(" and sett_Account.nStatusID = "+qdwi.getAccountStatusID()+" \n");
			}
			
		return m_sbSQL.toString();
	}

}

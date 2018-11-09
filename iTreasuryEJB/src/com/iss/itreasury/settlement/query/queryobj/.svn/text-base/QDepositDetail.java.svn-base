/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

import com.iss.itreasury.settlement.query.paraminfo.QueryDepositDetailWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryDepositDetailResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QDepositDetail extends BaseQueryObject
{
	public static void main(java.lang.String[] args) throws Exception
	{
		try
		{
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public final static int OrderBy_TransNo = 1;

	//
	Log4j logger = null;

	
	public Collection queryDepositDetail(QueryDepositDetailWhereInfo qdwi) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		Timestamp tsToday = Env.getSystemDate(qdwi.getOfficeID(),qdwi.getCurrencyID());
		
		try
		{
			// select 
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
			
			//order by
			String strDesc = qdwi.getDesc() == 1 ? " asc " : " desc ";
			switch ((int) qdwi.getOrderField())
			{
				case OrderBy_TransNo :
					m_sbSQL.append(" \n order by sett_account.saccountno " + strDesc);
					break;
				case 2:
					m_sbSQL.append(" \n order by sett_account.saccountno " + strDesc);
					break;
					
			}

			conn = getConnection();
			log.print("´æ¿îÃ÷Ï¸²éÑ¯ SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				QueryDepositDetailResultInfo qdri = new QueryDepositDetailResultInfo();
				
				qdri.setAccountID(rs.getLong("ID"));
				qdri.setAccountNo(rs.getString("sAccountNo"));
				qdri.setClientCode(rs.getString("sCode"));
				qdri.setAccountName(rs.getString("sName"));
				qdri.setAccountTypeID(rs.getLong("nAccountTypeID"));
				qdri.setAccountTypeCode(rs.getString("sAccountTypeCode"));
				qdri.setAccountType(SETTConstant.AccountType.getName(rs.getLong("nAccountTypeID")));
				qdri.setAccountStatus(SETTConstant.AccountStatus.getName(rs.getLong("nStatusID")));
				qdri.setOpenDate(rs.getTimestamp("dtOpen"));
				qdri.setInterestRatePlanName(NameRef.getInterestRatePlanNameByID(rs.getLong("AC_nInterestRatePlanID")));
				qdri.setAccountCurrentBalance(rs.getDouble("sumAccountBalance"));
				qdri.setAccountHistoryBalance(rs.getDouble("sumAccountHistoryBalance"));
				if(Env.getSystemDate(qdwi.getOfficeID(),qdwi.getCurrencyID()).equals(qdwi.getDate()))
				{
					qdri.setAccountBalance(qdri.getAccountCurrentBalance());
				}
				else
				{
					qdri.setAccountBalance(qdri.getAccountHistoryBalance());
				}
				v.add(qdri);
			}
				
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return v.size() > 0 ? v : null;
	}

}
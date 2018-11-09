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

import com.iss.itreasury.settlement.query.paraminfo.QueryAccountBalanceDetailWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceDetailResultInfo;
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
 * 明细余额对账单 和 汇总余额对账单打印的查询
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QAccountBalanceDetail extends BaseQueryObject
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

	public Collection queryAccountBalanceDetail(QueryAccountBalanceDetailWhereInfo qawi) throws Exception
	{
 		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select sett_accounttype.nAccountGroupID,sett_Account.ID,sett_Account.sAccountNo,sett_Account.sName,sett_SubAccount.mBalance,sett_dailyaccountbalance.mbalance mHisBalance \n");
			m_sbSQL.append(" ,sett_subaccount.AF_sDepositNo,sett_subaccount.AL_nLoanNoteID \n");
			m_sbSQL.append(" from sett_Account,sett_SubAccount,sett_DailyAccountBalance,sett_accounttype  \n");
			m_sbSQL.append(" where Sett_Account.ID = Sett_SubAccount.nAccountID and sett_SubAccount.ID = Sett_Dailyaccountbalance.nSubAccountID(+) \n");
			m_sbSQL.append(" and sett_Account.ID = "+qawi.getAccountID()+" and sett_dailyaccountbalance.dtDate(+) = to_date('"+DataFormat.formatDate(qawi.getDate())+"','yyyy-mm-dd') \n");
			m_sbSQL.append(" and decode(sett_SubAccount.dtFinish,null,to_date('3000-01-01','yyyy-mm-dd'),sett_SubAccount.dtFinish) \n");
			m_sbSQL.append(" >= to_date('"+DataFormat.formatDate(qawi.getDate())+"','yyyy-mm-dd') \n");
			m_sbSQL.append(" and sett_SubAccount.Nstatusid<>0 \n");//增加了状态 Added by ylguo at 2009-01-06
			m_sbSQL.append(" and  sett_Account.nAccountTypeID=sett_accounttype.id(+)\n");
			m_sbSQL.append(" order by sett_account.saccountno,AF_sDepositNo ,al_nloannoteid \n");//增加了排序 Added by ylguo at 2009-01-05
			conn = getConnection();
			log.print("明细余额对账单 SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				QueryAccountBalanceDetailResultInfo qari = new QueryAccountBalanceDetailResultInfo();
				
				qari.setAccountID(rs.getLong("ID"));
				qari.setAccountName(rs.getString("sName"));
				qari.setAccountNo(rs.getString("sAccountNo"));
				qari.setNAccountGroupID(rs.getLong("nAccountGroupID"));
				
				if(SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.FIXED
						||SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.NOTIFY)
				{//定期存款
					qari.setDepositNo(rs.getString("AF_sDepositNo"));
				}
				else if (SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.TRUST
						||SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.CONSIGN
						
						||SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.OTHERLOAN
						||SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.YT)
				{//贷款
					qari.setDepositNo(NameRef.getPayFormNoByID(rs.getLong("AL_nLoanNoteID")));
					qari.setContractcode(NameRef.getContractNoByNoticeID(rs.getLong("AL_nLoanNoteID")));
					
					
				}
				else if (SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(qawi.getAccountTypeID()) == SETTConstant.AccountGroupType.DISCOUNT)
				{//贴现
					qari.setDepositNo(NameRef.getDiscountCredenceNoByID(rs.getLong("AL_nLoanNoteID")));
					qari.setContractcode(NameRef.getContractNoByDiscountNoticeID(rs.getLong("AL_nLoanNoteID")));
				}
					
				if(Env.getSystemDate(qawi.getOfficeID(),qawi.getCurrencyID()).equals(qawi.getDate()))
				{
					qari.setBalance(rs.getDouble("mBalance"));
				}
				else
				{
					qari.setBalance(rs.getDouble("mHisBalance"));
				}
				qari.setDate(qawi.getDate());
				
				v.add(qari);
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
		finally
		{
		    cleanup(rs);
			cleanup(ps);
			cleanup(conn);    
		}
		return v.size() > 0 ? v : null;
	}

}
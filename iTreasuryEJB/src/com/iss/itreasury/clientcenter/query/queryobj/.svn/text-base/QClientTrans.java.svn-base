/*
 * Created on 2004-2-6
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.clientcenter.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

import com.iss.itreasury.clientcenter.query.paraminfo.QueryClientTransWhereInfo;
import com.iss.itreasury.clientcenter.query.resultinfo.QueryClientTransResultInfo;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QClientTrans extends BaseQueryObject
{
	public Collection queryClientTrans(QueryClientTransWhereInfo qctwi) throws Exception
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
			m_sbSQL.append(" select sett_Account.ID AccountID,sett_subAccount.AC_nInterestRatePlanID \n");
			m_sbSQL.append(" ,sett_Account.sAccountNo,sett_Account.nAccountTypeID,sett_Account.dtOpen,sett_Account.dtFinish,sett_Account.nStatusID \n");
			if(qctwi.getIsDepositOrLoan() == 1)
			{
				m_sbSQL.append(" ,sett_subAccount.AC_mCapitalLimitAmount,sum(round(sett_subAccount.Ac_mNegotiateAmount,2)) NegotiateAmount \n ");
			}
			m_sbSQL.append(" ,sum(round(sett_subAccount.mBalance,2)) AccountBalance \n");
			m_sbSQL.append(" from sett_Account,sett_SubAccount \n");
			m_sbSQL.append(" where sett_Account.ID = sett_SubAccount.nAccountID(+) \n");
			m_sbSQL.append(" and sett_Account.nOfficeID = "+ qctwi.getOfficeID()+" and sett_Account.nCurrencyID = "+ qctwi.getCurrencyID()+ " and sett_Account.nClientID="+ qctwi.getClientID()+ " \n");
			if (qctwi.getIsDepositOrLoan() == 1)
			{
				m_sbSQL.append(" and sett_Account.nAccountTypeID in (" + getDepositAccountType(qctwi.getCurrencyID(),qctwi.getOfficeID()) + ")");
			}
			else
			{
				m_sbSQL.append(" and sett_Account.nAccountTypeID in (" + getLoanAccountType(qctwi.getCurrencyID(),qctwi.getOfficeID()) + ")");
			}
			m_sbSQL.append(" group by sett_Account.ID,sett_subAccount.AC_nInterestRatePlanID \n");
			m_sbSQL.append(" ,sett_Account.sAccountNo,sett_Account.nAccountTypeID,sett_Account.dtOpen,sett_Account.dtFinish,sett_Account.nStatusID \n");
			if(qctwi.getIsDepositOrLoan() == 1)
			{
				m_sbSQL.append(" ,sett_subAccount.AC_mCapitalLimitAmount \n");
			}
			m_sbSQL.append(" order by sAccountNo \n");

			conn = getConnection();
			log.print("IsDepositOrLoan=" + qctwi.getIsDepositOrLoan());
			log.print("客户交易明细 SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			//中间变量
			long lInterestRatePlanID = -1;
			Timestamp tsSystemDate = Env.getSystemDate(qctwi.getOfficeID(),qctwi.getCurrencyID());
			
			while (rs.next())
			{
				QueryClientTransResultInfo qctri = new QueryClientTransResultInfo();

				qctri.setAccountID(rs.getLong("AccountID"));
				//qctri.setSubAccountID(rs.getLong("subAccountID"));
				qctri.setAccountNo(rs.getString("sAccountNo"));
				qctri.setAccountTypeID(rs.getLong("nAccountTypeID"));
				qctri.setAccountType(SETTConstant.AccountType.getName(qctri.getAccountTypeID()));
				qctri.setOpenDate(rs.getTimestamp("dtOpen"));
				qctri.setFinishDate(rs.getTimestamp("dtFinish"));
				qctri.setAccountStatusID(rs.getLong("nStatusID"));
				qctri.setAccountStatus(SETTConstant.AccountStatus.getName(qctri.getAccountStatusID()));
				if(qctwi.getIsDepositOrLoan() ==1)
				{
					qctri.setLimitBalance(rs.getDouble("AC_mCapitalLimitAmount"));
					qctri.setNegotiateAmount(rs.getDouble("NegotiateAmount"));
				}
				
				qctri.setAccountBalance(rs.getDouble("AccountBalance"));
				//取得账户的利率
				lInterestRatePlanID = rs.getLong("AC_nInterestRatePlanID");
				qctri.setAccountInterestRate(UtilOperation.getCurrentInterestRate(null,lInterestRatePlanID,qctri.getAccountBalance(),tsSystemDate,-1));
				//取得账户的利率结束
				v.add(qctri);
				
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

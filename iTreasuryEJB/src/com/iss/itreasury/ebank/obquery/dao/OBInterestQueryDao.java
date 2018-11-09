/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.obquery.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.ebank.obquery.dataentity.OBInterestInfo;
import com.iss.itreasury.ebank.obquery.dataentity.OBInterestWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OBInterestQueryDao {
	
	private static Log4j log4j = null;
	
	public OBInterestQueryDao()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	
	public Collection queryInterest(OBInterestWhereInfo qi) throws Exception
	{
		OBInterestInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT acct.id AccountID,acct.saccountno AccountNo, \n");
			/******´æ¿î******/
			sbSQL.append(" daily.mInterestBalance InterestBalance, \n");
			sbSQL.append(" daily.mInterestRate, \n");
			sbSQL.append(" daily.mInterest Interest, \n");
			/******Ð­¶¨*****/
			sbSQL.append(" daily.ac_mNegotiateBalance  NegotiateBalance, \n");
			sbSQL.append(" daily.ac_mNegotiateRate NegotiateRate, \n");
			sbSQL.append(" daily.ac_mNegotiateInterest NegotiateInterest \n");
			
			sbSQL.append(" FROM sett_account acct, sett_DailyAccountBalance daily \n");	
			sbSQL.append(" WHERE acct.id=daily.nAccountID \n");
			
			if (qi.getEndDate()!= null && qi.getEndDate().trim().length()>=8)
			{
				sbSQL.append(" AND daily.dtDate=to_date('"+qi.getEndDate().trim()+"','yyyy-mm-dd')-1 \n");
			}
			
			if (qi.getAcctId()>0)
			{
				sbSQL.append(" AND acct.id=" + qi.getAcctId());
			}
						
			log4j.info("OBInterestQueryDao.queryInterest():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			
			rs = ps.executeQuery();

			while (rs.next())
			{
				info = new OBInterestInfo();
				info.setAcctNo(rs.getString("AccountNo"));
				
				info.setDeposiInterest(rs.getDouble("NegotiateInterest"));
				info.setDepositBalance(rs.getDouble("NegotiateBalance"));
				info.setDepositRate(rs.getDouble("NegotiateRate"));
				
				info.setFormatDeposiInterest(DataFormat.formatDisabledAmount(info.getDeposiInterest()));
				info.setFormatDepositBalance(DataFormat.formatDisabledAmount(info.getDepositBalance()));
				info.setFormatDepositRate(DataFormat.formatRate(info.getDepositRate()));
				
				info.setLoanBalance(rs.getDouble("InterestBalance"));
				info.setLoanInterest(rs.getDouble("Interest"));
				info.setLoanRate(rs.getDouble("mInterestRate"));
				
				info.setFormatLoanBalance(DataFormat.formatDisabledAmount(info.getLoanBalance()));
				info.setFormatLoanInterest(DataFormat.formatDisabledAmount(info.getLoanInterest()));
				info.setFormatLoanRate(DataFormat.formatRate(info.getLoanRate()));
				
				vReturn.addElement(info);
			}
			
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
			log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}

}

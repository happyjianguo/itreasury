package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.settlement.query.paraminfo.QueryStatementInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryStatementResultInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class QStatement extends BaseQueryObject
{

	/**
	 * Constructor for QStatement.
	 */
	public QStatement()
	{
		super();
	}

	public Collection getStatementInfo(QueryStatementInfo conditionInfo) throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Collection result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			strSQLBuffer.append("SELECT * FROM sett_vextbanknotransaction \n");

			strSQLBuffer.append("WHERE 1 = 1 \n");

			if (conditionInfo.getDate() != null)
			{
				strSQLBuffer.append(
					" AND dtExecute = to_date('"
						+ DataFormat.getDateString(conditionInfo.getDate())
						+ "', 'yyyy-mm-dd') \n");
			}

			if (conditionInfo.getRemitType() != -1)
			{
				strSQLBuffer.append(" AND nDirectionID = " + conditionInfo.getRemitType() + " \n");
			}

			String strSQL = strSQLBuffer.toString();

			log.info(strSQL);
			ps = con.prepareStatement(strSQL);

			rs = ps.executeQuery();

			ArrayList list = new ArrayList();
			while (rs.next())
			{
				QueryStatementResultInfo info = new QueryStatementResultInfo();

				info.setID(rs.getLong("ID"));
				info.setTransNo(rs.getString("sTransNo"));
				info.setTransactionTypeID(rs.getLong("nTransactionTypeID"));
				info.setExtBankNo(rs.getString("sExtBankNo"));
				info.setDirectionID(rs.getLong("nDirectionID"));
				info.setExtAccountNo(rs.getString("sExtAccountNo"));
				info.setExtClientName(rs.getString("sExtClientName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setExecuteDate(rs.getTimestamp("dtExecute"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setCheckUserID(rs.getLong("nCheckUserID"));
				info.setAccountID(rs.getLong("nAccountID"));
				info.setAccountNo(rs.getString("sAccountNo"));
				
				list.add(info);
			}
			
			result = (list.size()>0)?list:null;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new IException("Query Failed:", e);
		}

		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				log.error("close connection failed!");
			}
		}
		return result;
	}

}

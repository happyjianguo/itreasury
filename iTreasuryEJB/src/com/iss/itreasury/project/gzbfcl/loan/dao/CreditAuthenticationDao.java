package com.iss.itreasury.project.gzbfcl.loan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.inut.base.BaseDao;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.project.gzbfcl.loan.dataentity.CreditAuthenticationInfo;
import com.iss.itreasury.project.gzbfcl.loan.dataentity.CreditAuthenticationQueryInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * 
 * @author sunjing
 * 资信鉴证业务
 *
 */
public class CreditAuthenticationDao extends LoanDAO
{	
	public CreditAuthenticationDao(String tableName)
	{
		 super(tableName);
		 super.setUseMaxID();
	}
	
	public String getQueryInfo(CreditAuthenticationQueryInfo info)
	{
		String strSelect ="";
		String strFrom = "";
		
		
		strSelect = "( select a.* ,b.sName clientname,c.sname inputuser,d.sname modifyuser " ;
		
		strFrom = " from LOAN_CREDITAUTHENTICATION a,client b,userinfo c,userinfo d where 1 = 1 ";
		strFrom += " and a.clientid=b.id ";
		strFrom += " and a.inputuserid=c.id " ;
		strFrom += " and a.modifyuserid=d.id " ;
		strFrom += " and a.currencyid = " + info.getCurrencyId();
		strFrom += " and a.officeid = " + info.getOfficeId();
		if(info.getClientIdFrom() > 0)
		{
			strFrom += " and clientid >= " + info.getClientIdFrom() ;
		}
		if(info.getClientIdTo() > 0)
		{
			strFrom += " and clientid <= " + info.getClientIdTo() ;
		}
		if(info.getInputTimeFrom() != null )
		{
			strFrom += " and TRUNC(modifytime)>= To_Date('" + DataFormat.getDateString(info.getInputTimeFrom()) + "','yyyy-mm-dd') " ;
		}
		if(info.getInputTimeTo() != null )
		{
			strFrom += " and TRUNC(modifytime)<= To_Date('" + DataFormat.getDateString(info.getInputTimeTo()) + "','yyyy-mm-dd') " ;
		}
		if(info.getStatusId() > 0 )
		{
			strFrom += " and statusid = " + info.getStatusId() ;
		}else if(info.getStatusId() == -1){
			strFrom += " and statusid > 0 " ;
		}else {
			strFrom += " and statusid = 1 " ;
		}
		
		
		
		return strSelect + strFrom+")";
	}
	public double queryAmountSum(CreditAuthenticationQueryInfo info) throws Exception
	{
		double sumAmount = 0 ;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = Database.getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select sum(a.commissionamount) sumAmount from LOAN_CREDITAUTHENTICATION a,client b,userinfo c,userinfo d");
			sbSQL.append(" where 1=1 ");
			sbSQL.append(" and a.clientid=b.id ");
			sbSQL.append(" and a.inputuserid=c.id " );
			sbSQL.append(" and a.modifyuserid=d.id " );
			sbSQL.append(" and a.currencyid = " + info.getCurrencyId());
			sbSQL.append(" and a.officeid = " + info.getOfficeId());
			if(info.getClientIdFrom() > 0)
			{
				sbSQL.append(" and clientid >= " + info.getClientIdFrom() );
			}
			if(info.getClientIdTo() > 0)
			{
				sbSQL.append(" and clientid <= " + info.getClientIdTo() );
			}
			if(info.getInputTimeFrom() != null )
			{
				sbSQL.append(" and TRUNC(modifytime)>= To_Date('" + DataFormat.getDateString(info.getInputTimeFrom()) + "','yyyy-mm-dd') ") ;
			}
			if(info.getInputTimeTo() != null )
			{
				sbSQL.append(" and TRUNC(modifytime)<= To_Date('" + DataFormat.getDateString(info.getInputTimeTo()) + "','yyyy-mm-dd') ") ;
			}			
			if(info.getStatusId() > 0 )
			{
				sbSQL.append(" and statusid = " + info.getStatusId());
			}else if(info.getStatusId() < 0) {
				sbSQL.append(" and statusid > 0  ");				
			}

			
			System.out.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumAmount = rs.getDouble("sumAmount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}		
		return sumAmount;		
	}

	protected void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}	
	protected void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
    protected void cleanup(Statement stmt) throws SQLException
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
                stmt = null;
            }
        }
        catch (SQLException sqle)
        {
			Log.print(sqle.toString());
        }
    }
	protected void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
			Log.print(sqle.toString());
		}
	}
	
}

package com.iss.itreasury.integratedCredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;


import com.iss.itreasury.credit.setting.dataentity.AmountFormViewInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;

import com.iss.itreasury.integratedCredit.dataentity.CreditFrameInfo;
import com.iss.itreasury.integratedCredit.dataentity.CreditGradeQueryInfo;
import com.iss.itreasury.integratedCredit.dataentity.LoanCreditgradeInfo;
import com.iss.itreasury.integratedCredit.dataentity.LoanCreditgradedetail;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

public class LoanCreditgradeInfoQueryDao extends SettlementDAO{

	private Log4j log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	public Collection findCreditGradeInfo(LoanCreditgradeInfo creditgradeInfo)throws Exception
	{
		ArrayList resList = new ArrayList();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		String sql = "";
		try {
        	
			sql = "select * "+
			" from LOAN_CREDITGRADE G" +
			" where  (G.STATUS ='1'  or G.STATUS ='2')" +
			" and G.INPUTUSERID='"+creditgradeInfo.getInputuserid()+"' " +
			" order by CREDITCODE";
			System.out.println("=-------  print sql" + sql);
			log4j.print("打印sql --------------------"+sql);
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				LoanCreditgradeInfo info = new LoanCreditgradeInfo();
				info.setId(rs.getLong("id"));
                info.setCreditcode(rs.getString("CREDITCODE"));
                info.setClientid(rs.getLong("CLIENTid"));
                info.setCreditlevel(rs.getString("CREDITLEVEL"));
                info.setGradedate(rs.getTimestamp("GRADEDATE"));
                info.setInputuserid(rs.getLong("INPUTUSERid"));
                info.setStatus(rs.getLong("STATUS"));
                info.setNdepartmentid(rs.getLong("NDEPARTMENTID"));
                info.setCreditlevelvalue(rs.getDouble("CREDITLEVELVALUE"));
                
                resList.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return resList;
	}
	
	
	public Collection findCreditInfo(LoanCreditgradeInfo creditgradeInfo)throws Exception
	{
		ArrayList resList = new ArrayList();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		String sql = "";
		try {
        	
			sql = "select * "+
			" from LOAN_CREDITGRADE G" +
			" where   G.STATUS ='2'" +
			" and G.INPUTUSERID <> '"+creditgradeInfo.getInputuserid()+"' " +
			" order by CREDITCODE";
			System.out.println("=-------  print sql" + sql);
			log4j.print("打印sql --------------------"+sql);
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				LoanCreditgradeInfo info = new LoanCreditgradeInfo();
				info.setId(rs.getLong("id"));
                info.setCreditcode(rs.getString("CREDITCODE"));
                info.setClientid(rs.getLong("CLIENTid"));
                info.setCreditlevel(rs.getString("CREDITLEVEL"));
                info.setGradedate(rs.getTimestamp("GRADEDATE"));
                info.setInputuserid(rs.getLong("INPUTUSERid"));
                info.setStatus(rs.getLong("STATUS"));
                info.setNdepartmentid(rs.getLong("NDEPARTMENTID"));
                info.setCreditlevelvalue(rs.getDouble("CREDITLEVELVALUE"));
                
                resList.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return resList;
	}
	public Collection findCreditInfo2(LoanCreditgradeInfo creditgradeInfo)throws Exception
	{
		ArrayList resList = new ArrayList();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		
		String sql = "";
		try {
        	
			sql = "select * "+
			" from LOAN_CREDITGRADE G" +
			" where   G.STATUS ='7'" +
			" and G.APPLICATIONUSERID = '"+creditgradeInfo.getApplicationuserid()+"' " +
			" order by CREDITCODE";
			System.out.println("=-------  print sql" + sql);
			log4j.print("打印sql --------------------"+sql);
			conn = Database.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				LoanCreditgradeInfo info = new LoanCreditgradeInfo();
				info.setId(rs.getLong("id"));
                info.setCreditcode(rs.getString("CREDITCODE"));
                info.setClientid(rs.getLong("CLIENTid"));
                info.setCreditlevel(rs.getString("CREDITLEVEL"));
                info.setGradedate(rs.getTimestamp("GRADEDATE"));
                info.setInputuserid(rs.getLong("INPUTUSERid"));
                info.setStatus(rs.getLong("STATUS"));
                info.setNdepartmentid(rs.getLong("NDEPARTMENTID"));
                info.setCreditlevelvalue(rs.getDouble("CREDITLEVELVALUE"));
                
                resList.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return resList;
	}
	
	/**
	 * 
	 * 
	 */
	public Collection findByCondition(long clientId,Timestamp startDate,Timestamp endDate)
	throws Exception
{
	
	ArrayList coll = new ArrayList();
	StringBuffer strSQL = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	Connection conn = null;
	
	        
        try {
        	
        	strSQL = new StringBuffer();
        	strSQL.append(" select * from LOAN_CREDITGRADE where 1=1 and status =3 " );
        	if(clientId>0)
        	{
        		strSQL.append("   and clientId='"+clientId+"' " );	
        	}
        	if(startDate != null && endDate == null)
    		{
        		
        		strSQL.append(" AND GRADEDATE  >= TO_DATE('"+DataFormat.getDateString(startDate)+"','yyyy-mm-dd')");
    		}
    		if(startDate == null && endDate != null)
    		{
    			strSQL.append(" AND GRADEDATE  <= TO_DATE('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')");
    		}
    		if(startDate != null && endDate != null)
    		{
    			strSQL.append(" and ((GRADEDATE >= to_date('"+ DataFormat.getDateString(startDate) +"', 'yyyy-mm-dd')");
    			strSQL.append("      and GRADEDATE  <= to_date('"+ DataFormat.getDateString(endDate) +"', 'yyyy-mm-dd')))");
    	
    		}
    		strSQL.append(" order by creditcode desc");
        	
        	conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				LoanCreditgradeInfo info = new LoanCreditgradeInfo();
				info.setId(rs.getLong("id"));
                info.setCreditcode(rs.getString("CREDITCODE"));
                info.setClientid(rs.getLong("CLIENTid"));
                info.setCreditlevel(rs.getString("CREDITLEVEL"));
                info.setGradedate(rs.getTimestamp("GRADEDATE"));
                info.setInputuserid(rs.getLong("INPUTUSERid"));
                info.setStatus(rs.getLong("STATUS"));
                info.setNdepartmentid(rs.getLong("NDEPARTMENTID"));
                info.setCreditlevelvalue(rs.getDouble("CREDITLEVELVALUE"));
                coll.add(info);
			}
        }catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		} finally {
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return coll;
	
}
	
	/**
	 * 通过信用等级明细属性清单
	 * @param creditgradeid
	 * @param targetid
	 * @return
	 * @throws Exception
	 */
	public LoanCreditgradeInfo findCreditgrade(long clientId) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanCreditgradeInfo info = null;
		

		try {
			conn = Database.getConnection();
			strSQL = " select *   from loan_creditgrade  " + " where (status =5 or status =7) "
					+ " and clientId=" + clientId;
	
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new LoanCreditgradeInfo();
				info.setId(rs.getLong("ID"));
				info.setClientid(rs.getLong("clientid"));
				info.setCreditcode(rs.getString("creditcode"));
				info.setCreditlevel(rs.getString("creditlevel"));
				info.setCreditlevelvalue(rs.getDouble("creditlevelvalue"));
				info.setGradedate(rs.getTimestamp("gradedate"));
				info.setOfficeid(rs.getLong("officeid"));
				info.setCurrencyid(rs.getLong("currencyid"));
				info.setStatus(rs.getLong("status"));
				info.setInputuserid(rs.getLong("inputuserid"));
				info.setInputdate(rs.getTimestamp("inputdate"));
				info.setCycleYear(rs.getString("CYCLEYEAR"));
				info.setCycleMonth(rs.getString("CYCLEMONTH"));
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		return info;
	}
	
	
}

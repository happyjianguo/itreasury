package com.iss.itreasury.integratedCredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.integratedCredit.dataentity.LoanCreditgradedetail;
import com.iss.itreasury.integratedCredit.dataentity.LoanFinanceitemdetail;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

public class LoanCreditGradedetailDao {

	
	
	private static Log4j log4j = null;

	public LoanCreditGradedetailDao() {
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	
	
	public List LoanCreditGradedetail(long clientid,long CreditgradeID) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanCreditgradedetail info = null;
		List lista = new ArrayList();
		try {
			conn = Database.getConnection();
			strSQL = " select l.*  from LOAN_CREDITGRADEDETAIL l ,LOAN_CREDITGRADE e " +
					" where l.creditgradeid=e.id and  e.ID = (select max(d.ID) from loan_creditgrade d where d.clientid="
					+ clientid
					+ " and d.ID!="
					+ CreditgradeID
					+ " and d.status in (5,3,7))order by l.targetid";
			log4j.debug(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new LoanCreditgradedetail();
				info.setTargetid(rs.getLong("TARGETID"));
				info.setScore(rs.getDouble("SCORE"));
				lista.add(info);
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
		return lista;
	}

	public List queryLoanCreditGradedetail(long CreditgradeID) throws Exception {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		LoanCreditgradedetail info = null;
		List lista = new ArrayList();
		try {
			conn = Database.getConnection();
			strSQL = " select l.*  from LOAN_CREDITGRADEDETAIL l where l.CREDITGRADEID="+ CreditgradeID+" order by l.targetid " ;
					
			log4j.debug(strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new LoanCreditgradedetail();
				info.setId(rs.getLong("ID"));
				info.setTargetid(rs.getLong("TARGETID"));
				info.setScore(rs.getDouble("SCORE"));
				info.setDescribe(rs.getLong("DESCRIBE"));
				info.setThatadjustment(rs.getString("THATADJUSTMENT"));
				info.setInitialPoints(rs.getDouble("initialPoints"));
				info.setAdjustmentPoints(rs.getDouble("adjustmentPoints"));
				info.setOperationtype(rs.getLong("operationtype"));
				info.setDiversityexplain(rs.getString("diversityexplain"));
				info.setDesc(rs.getString("DESCRIPT"));
				lista.add(info);
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
		return lista;
	}
}

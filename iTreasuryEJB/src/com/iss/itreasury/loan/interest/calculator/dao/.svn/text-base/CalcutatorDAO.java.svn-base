package com.iss.itreasury.loan.interest.calculator.dao;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import com.iss.itreasury.loan.interest.calculator.dataentity.*;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

public class CalcutatorDAO {
	/**
	 * 获得贷款合同对应的放贷信息，放款为正，还款为负
	 * @param startContractNo
	 * @param endContractNo
	 * @return
	 * @throws Exception
	 */
	public HashMap getPayment(String startContractNo, String endContractNo) throws Exception {
		HashMap map = new HashMap();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select b.loancontractno, (case when a.statusid=1 then a.amount else (-1*a.amount) end),a.executedate ");
		sql.append("from loan_payandrepayfact a,loan_bankloan b ");
		sql.append("where a.bankloanid=b.id and b.loancontractno>=? and b.loancontractno<=? and b.statusid<>0 ");
		sql.append("order by b.loancontractno,a.executedate");

		try {
			conn = Database.getConnection();
//			conn = getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setString(1, startContractNo);
			pst.setString(2, endContractNo);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				putPayment(map, rs.getString(1), rs.getDouble(2),rs.getDate(3));				
			}
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
		
		return map;
	}
	
	public Connection getConnection() throws SQLException
    {
        Connection conn = null;
        	try {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbURL = "jdbc:oracle:thin:@10.10.26.41:1521:db9i";
            Class.forName(jdbcDriver).newInstance();
            conn = DriverManager.getConnection(dbURL, "ccgrp", "ccgrp");
        	}
        	catch (Exception e) {
        		throw new SQLException(e.getMessage());
        	}
        	
        return conn;
    }
	
	/**
	 * 返回贷款合同对应的利率列表列表
	 * @param startContractNo
	 * @param endContractNo
	 * @return
	 * @throws Exception
	 */
	public HashMap getInterest(String startContractNo, String endContractNo) throws Exception {
		HashMap map = new HashMap();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select b.loancontractno,a.rate,a.startdate ");
		sql.append("from loan_interestdetail a,loan_bankloan b ");
		sql.append("where a.bankloanid=b.id and b.loancontractno>=? and b.loancontractno<=? and b.statusid<>0 ");
		sql.append("order by b.loancontractno,a.startdate");

		try {
			conn = Database.getConnection();
//			conn = getConnection();
			pst = conn.prepareStatement(sql.toString());
			pst.setString(1, startContractNo);
			pst.setString(2, endContractNo);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				putInterest(map, rs.getString(1), rs.getDouble(2),rs.getDate(3));				
			}
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
		catch (SQLException sqle) {
			throw sqle;
		}
		finally {
			closeResultSet(rs);
			closeStatement(pst);
			closeConnection(conn);
		}
		return map;
	}
	
	/**
	 * 
	 * @param map
	 * @param contractno
	 * @param amount
	 * @param date
	 */
	private void putPayment(HashMap map, String contractno, double amount, Date date) {
		Object temp = map.get(contractno);
		ArrayList list = null;
		
		if (temp == null) {
			list = new ArrayList();
			map.put(contractno, list);
		}
		else {
			list = (ArrayList)temp;
		}
		
		LoanPaymentInfo info = new LoanPaymentInfo();
		info.setAmount(amount);
		info.setExecuteDate(date);
		list.add(info);
	}
	
	private void putInterest(HashMap map, String contractno, double rate, Date date) {
		Object temp = map.get(contractno);
		ArrayList list = null;
		
		if (temp == null) {
			list = new ArrayList();
			map.put(contractno, list);
		}
		else {
			list = (ArrayList)temp;
		}
		
		LoanInterestInfo info = new LoanInterestInfo();
		info.setRate(rate);
		info.setStartDate(date);
		list.add(info);
	}
	
	
	
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}

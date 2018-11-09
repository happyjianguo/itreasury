package com.iss.itreasury.loan.repayplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iss.itreasury.util.Database;

public class RepayPlanDao_new {
	
	/**
	 * 查询免还申请处理执行计划信息dao
	 * @author zk 2012-12-24
	 *
	 */
	public String queryRepayPlanByContractPayPlanVersionID(long contractPayPlanVersionID){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select lcpd.*,lcp.ncontractid from loan_loanContractPlanDetail lcpd,loan_loanContractPlan lcp ");
		sql.append(" where 1=1 and lcpd.ncontractplanid = lcp.id and lcp.id = " + contractPayPlanVersionID);
		
		return sql.toString();
	}
	public String queryRepayPlanByContractID(long contractID){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		long lhasMAX = -1;
		
		try {
			conn = Database.getConnection();
			sql.append("select max(nplanversion) from loan_loancontractplan where ncontractid = ? and nisused = 2");
			ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, contractID);
			rs = ps.executeQuery();
			if (rs.next()){
				lhasMAX = rs.getLong(1);
			}
			ps.close();
			ps = null;
			rs.close();
			rs = null;
			conn.close();
			conn = null;
			sql.setLength(0);
			if(lhasMAX > 0){
				sql.append("select aa.*,bb.ncontractid from loan_loancontractplanDetail aa,loan_loancontractplan bb,");
				sql.append(" (select max(nplanversion) maxid from loan_loancontractplan where ncontractid = "+contractID+" and nisused = 2) cc");
				sql.append(" where bb.ncontractid = "+contractID+" and aa.ncontractplanid = bb.id and bb.nplanversion = cc.maxid");
			}else{
				sql.append("select aa.*,bb.ncontractid from loan_loancontractplandetail aa,loan_loancontractplan bb where bb.ncontractid = "+contractID+" and aa.ncontractplanid = bb.id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				if (rs != null){
					rs.close();
					rs = null;
				}
				if (ps != null){
					ps.close();
					ps = null;
				}
				if (conn != null){
					conn.close();
					conn = null;
				}
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return sql.toString();
	}

}

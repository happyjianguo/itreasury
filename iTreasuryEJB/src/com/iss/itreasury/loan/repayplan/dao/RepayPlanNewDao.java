package com.iss.itreasury.loan.repayplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

public class RepayPlanNewDao {

	public String findPlan(Map map) throws Exception {
		long lyu = Long.valueOf(map.get("lYU")+"");
		String sql = "";
		if (lyu == 2) {
			sql = findPlanByVer(map);
		} else {
			sql = findPlanByContract(map);
		}
		return sql;
	}

	private String findPlanByContract(Map map) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		String strCondition = "";
		long lhasMAX = -1;
		conn = Database.getConnection();
		sb
				.append("select max(NPLANVERSION) from loan_loancontractplan where nContractID = ? and NISUSED = 2");
		Log.print(sb.toString());
		ps = conn.prepareStatement(sb.toString());
		long lContractID = Long.valueOf(map.get("lid")+"");
		ps.setLong(1, lContractID );
		rs = ps.executeQuery();
		if (rs.next()) {
			lhasMAX = rs.getLong(1);
		}
		rs.close();
		rs = null;
		ps.close();
		ps = null;
		if (lhasMAX > 0) {
			strCondition = " from  loan_loancontractplanDetail aa,loan_loancontractplan bb, (select max(NPLANVERSION) maxid from loan_loancontractplan where nContractID = "
					+ lContractID
					+ "  and NISUSED = 2) cc where bb.nContractID = "
					+ lContractID
					+ " and aa.nContractPlanID = bb.ID and bb.NPLANVERSION in cc.maxid";
		} else {
			strCondition = " from  loan_loancontractplanDetail aa,loan_loancontractplan bb where bb.nContractID = "
					+ lContractID + " and aa.nContractPlanID = bb.ID";
		}
		return "select aa.* "+ strCondition;
	}

	private String findPlanByVer(Map map)
			throws Exception {
		long ContractPayPlanVersionID = Long.valueOf(map.get("lid")+"");
		String sql = "select * from  loan_loanContractPlanDetail aa, "
				+ "(select sum(MAMOUNT) as TOTAL1 from  loan_loanContractPlanDetail where nContractPlanID = "
				+ ContractPayPlanVersionID
				+ " and npaytypeid = 1) bb, "
				+ "(select sum(MAMOUNT) as TOTAL2 from  loan_loanContractPlanDetail where nContractPlanID = "
				+ ContractPayPlanVersionID + " and npaytypeid = 2) cc "
				+ "where aa.nContractPlanID = " + ContractPayPlanVersionID;
		return sql;
	}

}

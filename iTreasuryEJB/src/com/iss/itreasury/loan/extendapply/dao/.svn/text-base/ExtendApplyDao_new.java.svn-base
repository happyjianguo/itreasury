package com.iss.itreasury.loan.extendapply.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;

public class ExtendApplyDao_new {
	
	/**
	 * 合同查找，根据条件查询loan_ContractForm和loan_LoanForm表。
	 * lContractIDFrom和lContractIDTo，同一类型的合同的流水号的部分作为查询范围
	 * @param lInputUserID 登录人标识
	 * @param lCurrencyID 币种标识
	 * @param lOfficeID 办事处标识
	 * @param lTypeID 贷款申请类型标识
	 * @param lContractIDFrom 合同编号起始
	 * @param lContractIDTo 合同编号结束
	 * @param lClientID 借款单位标识
	 * @return PagerInfo
	 * @exception Exception
	 * @author zk 2012-12-31
	 * 
	 */
	public String queryContractByMultiOption(long lInputUserID,long lCurrencyID,long lOfficeID,long lTypeID,
			long lContractIDFrom,long lContractIDTo,long lClientID) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select aa.*,cc.sname from loan_ContractForm aa,client cc \n");
		sql.append(" where aa.nstatusid in ( " + LOANConstant.ContractStatus.ACTIVE + "," + LOANConstant.ContractStatus.EXTEND + " ) \n");
		sql.append("  and cc.id = aa.nborrowclientid and aa.ntypeid <> " + LOANConstant.LoanType.TX + " \n");
		sql.append("  and aa.ntypeid <> " + LOANConstant.LoanType.ZTX + " and aa.ntypeid <> " + LOANConstant.LoanType.DB + " \n");
		
		if (lInputUserID > 0){
			sql.append(" and aa.ninputuserid = " + lInputUserID + " \n");
		}
		if (lCurrencyID > 0){
			sql.append(" and aa.ncurrencyid = " + lCurrencyID + " \n");
		}
		if (lTypeID > 0){
			sql.append(" and aa.ntypeid = " + lTypeID + " \n");
		}
		if (lContractIDFrom > 0){
			sql.append(" and aa.id >=  " + lContractIDFrom + " \n");
		}
		if (lContractIDTo > 0){
			sql.append(" and aa.id <=  " + lContractIDTo + " \n");
		}
		if (lClientID > 0){
			sql.append(" and aa.nborrowclientid =  " + lClientID + " \n");
		}
		if (lOfficeID > 0){
			sql.append(" and aa.nofficeid =  " + lOfficeID + " \n");
		}
		
		return sql.toString();
	}
	/**
	 * 查找合同的最新版本还款计划的个数
	 * @author zk 2012-12-31
	 * 
	 */
	public long getNumForPlan(long lContractID){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		String mainSql = "";
		long count = 0;
		try {
			mainSql = this.queryPlanByContract(lContractID);
			if(mainSql != null && !"".equals(mainSql)){
				conn = Database.getConnection();
				sql.append("select count(*) from ("+mainSql+") \n");
				ps = conn.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				while(rs.next()){
					count = rs.getLong(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return count;
	}
	/**
	 * 查找合同的最新版本还款计划
	 * @param lContractID 合同标识
	 * @return PagerInfo
	 * @exception Exception
	 * @author zk 2012-12-31
	 * 
	 */
	public String queryPlanByContract(long lContractID) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		long nStatusID = -1;
		long nIsUsed = -1;
		long lhasMAX = -1;
		try {
			conn = Database.getConnection();
			//查最新计划是否可以做展期
			sql.append("select NSTATUSID,NISUSED,NUSERTYPE from loan_LoanContractPlan ");
			sql.append(" where NCONTRACTID = ? and NPLANVERSION in (select max(NPLANVERSION) from loan_LoanContractPlan where NCONTRACTID = ?)");
			ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, lContractID);
			ps.setLong(2, lContractID);
			rs = ps.executeQuery();
			if (rs.next()){
				nStatusID = rs.getLong("NSTATUSID");
				nIsUsed = rs.getLong("NISUSED");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sql.setLength(0);
			
			if (nStatusID == Constant.RecordStatus.VALID && nIsUsed != Constant.YesOrNo.YES){
				sql.append("select max(NPLANVERSION) from loan_loancontractplan where nContractID = ?");
				ps = conn.prepareStatement(sql.toString());
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				if(rs.next()){
					lhasMAX = rs.getLong(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				sql.setLength(0);
				if(lhasMAX > 0){
					sql.append("select aa.*,bb.nContractID from loan_loancontractplanDetail aa,loan_loancontractplan bb \n");
					sql.append(" where bb.nContractID = "+lContractID+" and aa.nPayTypeID = "+LOANConstant.PlanType.REPAY+" \n");
					sql.append(" and aa.nContractPlanID = bb.ID and bb.nplanversion = "+lhasMAX+" \n");
				}else{
					sql.append("select aa.*,bb.nContractID from loan_loancontractplanDetail aa,loan_loancontractplan bb \n");
					sql.append(" where bb.nContractID = "+lContractID+" and aa.nPayTypeID = "+LOANConstant.PlanType.REPAY+" and aa.nContractPlanID = bb.ID \n");
				}
			}
			
		} catch(Exception e) {
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
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return sql.toString();
	}

}

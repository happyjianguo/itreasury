package com.iss.itreasury.loan.repayplan.biz;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.repayplan.dao.RepayPlanDao;
import com.iss.itreasury.loan.repayplan.dao.RepayPlanNewDao;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class RepayPlanBiz {

	private RepayPlanNewDao dao = new RepayPlanNewDao();

	private void colseRs(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
	}

	public PagerInfo findPlan(long lid, long userID, long officeID, long lYU,String Symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		Map map = new HashMap();
		map.put("lYU", lYU); 
		map.put("lid", lid);map.put("currencysymbol", Symbol);
		String sql = dao.findPlan(map);
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(RepayPlanBiz.class,
					"findPlanResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList findPlanResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		long lYU = Long.valueOf(map.get("lYU") + "");
		if (lYU == 2) {
			return findPlanByVer(rs, map);
		} else {
			return findPlanByContract(rs, map);
		}

	}

	private ArrayList findPlanByContract(ResultSet rs, Map map)
			throws Exception {
		ContractDao contractDao = new ContractDao();
		long lContractID = Long.valueOf(map.get("lid") + "");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String symbol = map.get("currencysymbol") + "";

		while (rs.next()) {
			RepayPlanInfo rp_info = new RepayPlanInfo();
			rp_info.lID = rs.getLong("ID");
			rp_info.tsPlanDate = rs.getTimestamp("DTPLANDATE");
			rp_info.sExecuteInterestRate = getPlanRate(lContractID,
					rp_info.tsPlanDate);
			rp_info.fExecuteInterestRate = contractDao.getLatelyRate(0,
					lContractID, rp_info.tsPlanDate).getLateRate();
			rp_info.lateRateString = contractDao.getLatelyRate(0, lContractID,
					rp_info.tsPlanDate).getLateRateString();
			rp_info.nLoanOrRepay = rs.getInt("NPAYTYPEID");
			rp_info.dAmount = rs.getDouble("MAMOUNT");
			rp_info.sType = rs.getString("STYPE");
			rp_info.tsInputDate = rs.getTimestamp("DTMODIFYDATE");
			rp_info.lContractPayPlanVersionID = rs.getLong("nContractPlanID");
			rp_info.lLastExtendID = rs.getLong("NLASTEXTENDID");
			rp_info.lLastOverDueID = rs.getLong("NLASTOVERDUEID");
			rp_info.lLastVersionPlanID = rs.getLong("nLastVersionPlanID");
			rp_info.mINTERESTAMOUNT = rs.getDouble("MINTERESTAMOUNT");
			rp_info.mRECOGNIZANCEAMOUNT = rs.getDouble("MRECOGNIZANCEAMOUNT");
			String showRepay = LOANConstant.PlanType
					.getName(rp_info.nLoanOrRepay);

			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, DataFormat
					.formatDate(rp_info.tsPlanDate));
			PagerTools.returnCellList(cellList, showRepay);
			PagerTools.returnCellList(cellList, symbol
					+ DataFormat.formatListAmount(rp_info.dAmount));
			PagerTools.returnCellList(cellList, rp_info.sType);
			PagerTools.returnCellList(cellList, rp_info.lateRateString + "%");
			PagerTools.returnCellList(cellList, DataFormat
					.getDateString(rp_info.tsInputDate));

			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	private ArrayList findPlanByVer(ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行

		Connection conn = Database.getConnection();
		PreparedStatement ps = null;
		ResultSet rs1 = null;

		StringBuffer sb = new StringBuffer();

		long lContractID = -1;
		String symbol = map.get("currencysymbol") + "";
		long ContractPayPlanVersionID = Long.valueOf(map.get("lid") + "");

		RepayPlanDao dao = new RepayPlanDao();
		ContractDao contractdao = new ContractDao();

		sb
				.append("select nContractID from loan_loanContractPlan  where ID = ? ");
		ps = conn.prepareStatement(sb.toString());
		ps.setLong(1, ContractPayPlanVersionID);
		rs1 = ps.executeQuery();

		if (rs1.next()) {
			lContractID = rs1.getLong(1);
		}
		conn.close();
		conn =null;
		rs1.close();
		rs1 = null;
		ps.close();
		ps = null;

		while (rs != null & rs.next()) {
			RepayPlanInfo rp_info = new RepayPlanInfo();
			rp_info.lID = rs.getLong("ID");
			rp_info.tsPlanDate = rs.getTimestamp("DTPLANDATE");
			if (contractdao.findByID(lContractID).getLoanTypeID() == LOANConstant.LoanType.RZZL) {
				rp_info.fExecuteInterestRate = contractdao
						.getLatelyRateForRZZLPlan(lContractID,
								rp_info.tsPlanDate).getRate();
				rp_info.lateRateString = DataFormat
						.formatRate(rp_info.fExecuteInterestRate);
			} else {
				rp_info.fExecuteInterestRate = contractdao.getLatelyRate(0,
						lContractID, rp_info.tsPlanDate).getLateRate();
				rp_info.lateRateString = contractdao.getLatelyRate(0,
						lContractID, rp_info.tsPlanDate).getLateRateString();
			}
			rp_info.sExecuteInterestRate = dao.getPlanRate(lContractID,
					rp_info.tsPlanDate);
			rp_info.nLoanOrRepay = rs.getInt("NPAYTYPEID");
			rp_info.dAmount = rs.getDouble("MAMOUNT");
			rp_info.mINTERESTAMOUNT = rs.getDouble("MINTERESTAMOUNT");
			rp_info.sType = rs.getString("STYPE");
			rp_info.tsInputDate = rs.getTimestamp("DTMODIFYDATE");
			rp_info.lVersionNo = getPlanVersion(ContractPayPlanVersionID);
			rp_info.dPayCounter = rs.getDouble("TOTAL1");
			rp_info.dRePayCounter = rs.getDouble("TOTAL2");
			rp_info.lLastExtendID = rs.getLong("NLASTEXTENDID");
			rp_info.lLastOverDueID = rs.getLong("NLASTOVERDUEID");
			// rp_info.lisOverDue = rs.getLong("NISOVERDUE");
			rp_info.lLastVersionPlanID = rs.getLong("nLastVersionPlanID");
			String showRepay = LOANConstant.PlanType
					.getName(rp_info.nLoanOrRepay);

			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, DataFormat
					.formatDate(rp_info.tsPlanDate));
			PagerTools.returnCellList(cellList, showRepay);
			PagerTools.returnCellList(cellList, symbol
					+ DataFormat.formatListAmount(rp_info.dAmount));
			PagerTools.returnCellList(cellList, rp_info.sType);
			PagerTools.returnCellList(cellList, rp_info.lateRateString + "%");
			PagerTools.returnCellList(cellList, DataFormat
					.getDateString(rp_info.tsInputDate));
			// 存储行数据
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// 返回数据
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;

	}

	private long getPlanVersion(long lID) throws RemoteException {

		long lResult = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			conn = Database.getConnection();
			sb
					.append("select max(bb.NPLANVERSION) from loan_loancontractplan aa,loan_loancontractplan bb where bb.NCONTRACTID = aa.NCONTRACTID and aa.ID = ?");
			System.out.println(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next()) {
				lResult = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			sb.setLength(0);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RemoteException(ex.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				throw new RemoteException(ex.getMessage());
			}
		}
		return lResult;
	}

	public String getPlanRate(long lContractID, Timestamp tsDate)
			throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sb = new StringBuffer();
		String sInterestRate = ""; // for Libor 因为Libor没有值
		// 查找银行利率
		long lRateType = LOANConstant.InterestRateType.BANK; // bank rate
		double dInterestRate = 0;

		try {
			conn = Database.getConnection();

			sb
					.append("select NINTERESTTYPEID from loan_contractform where ID = "
							+ lContractID);
			System.out.println(sb.toString());
			System.out.println("1-------------");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				lRateType = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sb.setLength(0);

			if (lRateType != LOANConstant.InterestRateType.LIBOR) {
				ContractDao contractDao = new ContractDao();
				dInterestRate = contractDao.getLatelyRate(0, lContractID,
						tsDate).getLateRate();
				sInterestRate = DataFormat.formatRate(dInterestRate);
			}

		} catch (Exception e) {
			Log.print(e.toString());
			throw e;
		} finally {
			try {
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
			} catch (Exception e) {
				throw e;
			}
		}
		return sInterestRate;

	}
}
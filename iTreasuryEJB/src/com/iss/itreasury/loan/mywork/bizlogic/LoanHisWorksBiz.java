package com.iss.itreasury.loan.mywork.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.inut.workflow.ws.IWorkflowSpecial;
import com.iss.itreasury.loan.mywork.dao.LoanHisWorkDao;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanHisWorksBiz {

	private LoanHisWorkDao dao = new LoanHisWorkDao();

	public PagerInfo queryHisWork(LoanMyWorkInfo info, String currencysymbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.getQueryHisWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"resultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;

	}

	public ArrayList resultSetHandle(ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs
					.getDouble("amount"));
			strFormatedAmount = strFormatedAmount == null
					|| strFormatedAmount.equals("") ? "0.00"
					: strFormatedAmount;
			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("entryid"));
			// 存储列数据
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("EntryID") + "&osActionId="
					+ rs.getLong("ActionID") + "&osStepId=" + "-1"
					+ "&actionFrom=historyWorkList";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs.getString("ActionName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, rs.getString("ContractCode"));
			PagerTools.returnCellList(cellList, rs.getString("Code"));
			PagerTools.returnCellList(cellList, currencysymbol
					+ strFormatedAmount);
			PagerTools.returnCellList(cellList, NameRef.getUserNameByID(rs
					.getLong("Owner")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	public PagerInfo queryLoanContractStatusChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.getQueryLoanContractStatusChangeWorkSql(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryLoanContractStatusChangeWorkSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;

	}

	public ArrayList queryLoanContractStatusChangeWorkSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs
					.getDouble("amount"));
			strFormatedAmount = strFormatedAmount == null
					|| strFormatedAmount.equals("") ? "0.00"
					: strFormatedAmount;
			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("entryid"));
			// 存储列数据
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("EntryID") + "&osActionId="
					+ "-1" + "&osStepId=" + "-1"
					+ "&actionFrom=historyWorkList";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, rs.getString("ContractCode"));
			PagerTools.returnCellList(cellList, rs.getString("Code"));
			PagerTools.returnCellList(cellList, currencysymbol
					+ strFormatedAmount);

			PagerTools.returnCellList(cellList, LOANConstant.ContractStatus
					.getName(rs.getLong("OldStatus")));
			PagerTools.returnCellList(cellList, LOANConstant.ContractStatus
					.getName(rs.getLong("ChangeStatus")));

			PagerTools.returnCellList(cellList, NameRef.getUserNameByID(rs
					.getLong("Owner")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	public PagerInfo queryLoanContractRisklevelChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao
				.getQueryLoanContractRisklevelChangeWorkSetHandleSql(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryLoanContractRisklevelChangeWorkSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryLoanContractRisklevelChangeWorkSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs
					.getDouble("amount"));
			strFormatedAmount = strFormatedAmount == null
					|| strFormatedAmount.equals("") ? "0.00"
					: strFormatedAmount;
			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("entryid"));
			// 存储列数据
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("EntryID") + "&osActionId="
					+ "-1" + "&osStepId=" + "-1"
					+ "&actionFrom=historyWorkList";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, rs.getString("ContractCode"));
			PagerTools.returnCellList(cellList, rs.getString("Code"));
			PagerTools.returnCellList(cellList, currencysymbol
					+ strFormatedAmount);

			PagerTools.returnCellList(cellList, LOANConstant.VentureLevel
					.getName(rs.getLong("OldStatus")));
			PagerTools.returnCellList(cellList, LOANConstant.VentureLevel
					.getName(rs.getLong("ChangeStatus")));

			PagerTools.returnCellList(cellList, NameRef.getUserNameByID(rs
					.getLong("Owner")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	public PagerInfo queryLoanContractPlanChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.getQueryLoanContractPlanChangeWorkSql(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryLoanContractPlanChangeWorkSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}

		return pagerInfo;
	}

	public ArrayList queryLoanContractPlanChangeWorkSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs
					.getDouble("amount"));
			strFormatedAmount = strFormatedAmount == null
					|| strFormatedAmount.equals("") ? "0.00"
					: strFormatedAmount;
			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("entryid"));
			// 存储列数据
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("EntryID") + "&osActionId="
					+ "-1" + "&osStepId=" + "-1"
					+ "&actionFrom=historyWorkList";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, rs.getString("ContractCode"));
			PagerTools.returnCellList(cellList, currencysymbol
					+ strFormatedAmount);

			PagerTools.returnCellList(cellList, LOANConstant.ContractStatus
					.getName(rs.getLong("reason")));
			PagerTools.returnCellList(cellList, LOANConstant.ContractStatus
					.getName(rs.getLong("planversion")));

			PagerTools.returnCellList(cellList, NameRef.getUserNameByID(rs
					.getLong("Owner")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	public PagerInfo queryLoanContractRateChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.getQueryLoanContractRateChangeWorkSql(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryLoanContractRateChangeWorkSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryLoanContractRateChangeWorkSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
	
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("entryid"));
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("EntryID") + "&osActionId="
					+ "-1" + "&osStepId=" + "-1"
					+ "&actionFrom=historyWorkList";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, rs.getString("ContractCode"));
			PagerTools.returnCellList(cellList, rs.getString("reason"));
			PagerTools.returnCellList(cellList, DataFormat.formatRate(rs.getLong("Rate")));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("dtValiDate")));

			PagerTools.returnCellList(cellList, NameRef.getUserNameByID(rs
					.getLong("Owner")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	public PagerInfo queryLoanContractRateChangeWorkBatch(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.getQueryLoanContractRateChangeWorkSql(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryLoanContractRateChangeWorkBatchSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}

		return pagerInfo;
	}

	public ArrayList queryLoanContractRateChangeWorkBatchSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";

		while (rs.next()) {
			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("entryid"));
			// 存储列数据
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ ",-1," + rs.getLong("EntryID")
					+ "," + rs.getLong("ActionCode") + ","
					+ rs.getLong("StepCode") + "," + rs.getLong("Batchid")
					+ ",-1");

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, rs.getLong("Batchid"));

			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	public PagerInfo queryLoanCredit(LoanMyWorkInfo info, String currencysymbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryLoanCredit(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryLoanCreditSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}

		return pagerInfo;

	}

	public ArrayList queryLoanCreditSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			double creditAmount = rs.getDouble("Amount");

			String strOperationType = LOANConstant.OperationType.getName(rs
					.getLong("operationType"));

			if (rs.getLong("operationType") == LOANConstant.OperationType.CHANGE) {
				strOperationType = strOperationType
						+ "("
						+ LOANConstant.OperationSign.getName(rs
								.getLong("operationSign")) + ")";
			}

			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("EntryID"));
			// 存储列数据
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("entryID") + "&osActionId=" + "-1"
					+ "&osStepId=" + "-1" + "&strActionMyWork=historyWork";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, rs.getString("Code") == null
					|| "".equals(rs.getString("Code")) ? "" : rs
					.getString("Code"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, LOANConstant.CreditModel
					.getName(rs.getLong("creditModel")));
			PagerTools.returnCellList(cellList, strOperationType);
			PagerTools.returnCellList(cellList, DataFormat
					.formatListAmount(creditAmount));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("StartDate")));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("endDate")));

			PagerTools.returnCellList(cellList, NameRef.getUserNameByID(rs
					.getLong("Owner")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

	private void colseRs(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
	}

	public PagerInfo queryAfterCreditWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryAfterCreditWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanHisWorksBiz.class,
					"queryAfterCreditWorkSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}

		return pagerInfo;
	}

	public ArrayList queryAfterCreditWorkSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {


			String strNextApprover = ((IWorkflowSpecial) Class.forName(
					"com.iss.inut.workflow.ws.IWorkflowSpecialImp")
					.newInstance()).getNextApprovalUserName(rs
					.getLong("EntryID"));
			// 存储列数据
			cellList = new ArrayList();
			String href = rs.getString("LinkUrl") + "&&osentryId="
					+ rs.getLong("entryID") + "&osActionId=" + "-1"
					+ "&osStepId=" + "-1" + "&actionFrom=historyWorkList";
			String[] params = href.split("\\?");
			PagerTools.returnCellList(cellList, rs.getString("WfDefineName")
					+ "," + params[0] + "," + params[1]);

			PagerTools.returnCellList(cellList, rs.getString("StepName"));
			PagerTools.returnCellList(cellList, rs.getString("checkReportCode") == null
					|| "".equals(rs.getString("checkReportCode")) ? "" : rs
					.getString("checkReportCode"));
			PagerTools.returnCellList(cellList, rs
					.getString("clientName"));
			PagerTools.returnCellList(cellList, rs.getString("contractCode"));
			PagerTools.returnCellList(cellList, LOANConstant.AfterCheckType.getName(rs.getLong("checkType")));
			PagerTools.returnCellList(cellList, rs.getString("checkYear"));
			PagerTools.returnCellList(cellList, LOANConstant.VentureLevel.getName(rs.getLong("advice")));
			PagerTools.returnCellList(cellList, strNextApprover == null ? ""
					: strNextApprover);
			PagerTools.returnCellList(cellList,
					rs.getString("InputUserName") == null ? "" : rs
							.getString("InputUserName"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")) == null ? "" : DataFormat
					.getDateString(rs.getTimestamp("InputDate")));
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

}
package com.iss.itreasury.loan.mywork.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.mywork.dao.LoanCalWorkDao;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.overdueapply.bizlogic.OverDueApply;
import com.iss.itreasury.loan.overdueapply.bizlogic.OverDueApplyHome;
import com.iss.itreasury.loan.overdueapply.dataentity.OverDueInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBObject;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanCalWorksBiz {

	private LoanCalWorkDao dao = new LoanCalWorkDao();


	private void colseRs(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
	}

	public PagerInfo queryLoanTransActionWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryLoanTransActionWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanTransActionWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanTransActionWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs.getDouble("Amount"));
			strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, LOANConstant.LoanType.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList, rs.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs.getString("ActionName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList,rs.getString("ContractCode"));
			
			PagerTools.returnCellList(cellList,rs.getString("code")+","+rs.getLong("ApprovalEntryID")+","+params[0]+","+params[1]);
			PagerTools.returnCellList(cellList,currencysymbol+strFormatedAmount);
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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
		String sql = dao.queryLoanContractPlanChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanContractPlanChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanContractPlanChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs.getDouble("Amount"));
			strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, LOANConstant.LoanType.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList, rs.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			
			PagerTools.returnCellList(cellList,rs.getString("ContractCode")+","+rs.getLong("ApprovalEntryID")+","+params[0]+","+params[1]);
			PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(rs.getDouble("amount")) );
			
			PagerTools.returnCellList(cellList,rs.getString("Reason"));
			PagerTools.returnCellList(cellList,rs.getString("planVersion"));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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
		String sql = dao.queryLoanContractStatusChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanContractStatusChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList queryLoanContractStatusChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs.getDouble("Amount"));
			strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, LOANConstant.LoanType.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList, rs.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			
			PagerTools.returnCellList(cellList,rs.getString("ContractCode")+","+rs.getLong("ApprovalEntryID")+","+params[0]+","+params[1]);
			
			PagerTools.returnCellList(cellList,rs.getString("Code"));
			PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(rs.getDouble("amount")) );
			

			PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(rs.getLong("OldStatus")));
			PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(rs.getLong("ChangeStatus")));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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
		String sql = dao.queryLoanContractRisklevelChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanContractRisklevelChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanContractRisklevelChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs.getDouble("Amount"));
			strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, LOANConstant.LoanType.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList, rs.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			
			PagerTools.returnCellList(cellList,rs.getString("ContractCode")+","+rs.getLong("ApprovalEntryID")+","+params[0]+","+params[1]);
			
			PagerTools.returnCellList(cellList,rs.getString("Code"));
			PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(rs.getDouble("amount")) );
			

			PagerTools.returnCellList(cellList,LOANConstant.VentureLevel.getName(rs.getLong("OldStatus")));
			PagerTools.returnCellList(cellList,LOANConstant.VentureLevel.getName(rs.getLong("ChangeStatus")));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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
		String sql = dao.queryLoanContractRateChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanContractRateChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList queryLoanContractRateChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs.getDouble("Amount"));
			strFormatedAmount = strFormatedAmount==null || strFormatedAmount.equals("") ?"0.00":strFormatedAmount;
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, LOANConstant.LoanType.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList, rs.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			
			PagerTools.returnCellList(cellList,rs.getString("ContractCode")+","+rs.getLong("ApprovalEntryID")+","+params[0]+","+params[1]);
			
			PagerTools.returnCellList(cellList,rs.getString("Reason"));
			
			PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(rs.getLong("Rate")));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("DtValiDate")));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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
		String sql = dao.queryLoanContractRateChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanContractRateChangeWorkBatchResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanContractRateChangeWorkBatchResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList,rs.getString("Batchid")+","+rs.getLong("Batchid")+","+rs.getLong("LoanSubTypeId")+","+rs.getLong("ApprovalEntryID"));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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

	public PagerInfo queryLoanCredit(LoanMyWorkInfo info, String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryLoanCredit(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryLoanCreditResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryLoanCreditResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			double creditAmount = rs.getDouble("Amount");
			String strOperationType = LOANConstant.OperationType.getName(rs.getLong("OperationType"));
			if(rs.getLong("OperationType") == LOANConstant.OperationType.CHANGE){
				strOperationType = strOperationType + "("+ LOANConstant.OperationSign.getName(rs.getLong("OperationSign")) +")";
			}
			
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();

			PagerTools.returnCellList(cellList,rs.getString("Code")+","+rs.getLong("ApprovalEntryID")+","+params[0]+","+params[1]);
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));

			PagerTools.returnCellList(cellList,LOANConstant.CreditModel.getName(rs.getLong("CreditModel")));
			PagerTools.returnCellList(cellList,strOperationType);
			
			
			PagerTools.returnCellList(cellList,DataFormat.formatListAmount(creditAmount));
			
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("StartDate")));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("EndDate")));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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

			pagerInfo.setExtensionMothod(LoanCalWorksBiz.class,
					"queryAfterCreditWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public ArrayList queryAfterCreditWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String currencysymbol = map.get("currencysymbol") + "";
		while (rs.next()) {
			
			
			String url = rs.getString("LinkUrl");
			String []params = url.split("\\?");
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,rs.getString("checkReportCode"));

			PagerTools.returnCellList(cellList,rs.getString("ClientName")+","+rs.getLong("id")+","+rs.getLong("LoanSubTypeId")+","+rs.getLong("Clientid")+","+rs.getLong("ContractID")+","+rs.getLong("ApprovalEntryID"));
			PagerTools.returnCellList(cellList, rs
					.getString("ContractCode"));

			PagerTools.returnCellList(cellList,LOANConstant.AfterCheckType.getName(rs.getLong("CheckType")));
			PagerTools.returnCellList(cellList,rs.getString("CheckYear"));
			
			
			PagerTools.returnCellList(cellList,LOANConstant.VentureLevel.getName(rs.getLong("Advice")));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
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
package com.iss.itreasury.loan.mywork.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.mywork.dao.LoanRefWorkDao;
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

public class LoanRefWorksBiz {

	private LoanRefWorkDao dao = new LoanRefWorkDao();

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

			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
		OverDueApplyHome home = (OverDueApplyHome) EJBObject
				.getEJBHome("OverDueApplyHome");
		OverDueApply overDueApplyEjb = home.create();
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		String a = "", b = "", c = "";
		while (rs.next()) {
			String strFormatedAmount = DataFormat.formatDisabledAmount(rs
					.getDouble("amount"));
			strFormatedAmount = strFormatedAmount == null
					|| strFormatedAmount.equals("") ? "0.00"
					: strFormatedAmount;
			String strUrl = "";
			long lLoanType = rs.getLong("loanTypeId");
			long lActionId = rs.getLong("ActionID");
			long id = rs.getLong("ID");
			long contractid = rs.getLong("ContractID");
			long statusid = rs.getLong("statusID");

			// 申请
			if (lActionId == Constant.ApprovalAction.LOAN_APPLY) {
				a = id + "";
				b = lLoanType + "";
				// 合同
			} else if (lActionId == Constant.ApprovalAction.LOAN_CONTRACT) {
				a = contractid + "";
				b = lLoanType + "";
				// 放款通知单
			} else if (lActionId == Constant.ApprovalAction.LOANPAY_NOTICE) {
				a = lLoanType+"";
				if (lLoanType == LOANConstant.LoanType.TX) {
					b = id + "";
				} else if (lLoanType == LOANConstant.LoanType.YT) {
					long lYtDrawNoticeId = workBiz
							.findYTDrawNoticeIdByPayNoticeId(id);
					b = id + "";
					c = lYtDrawNoticeId + "";
				} else {
					b = contractid + "";
					c = id + "";
				}
			}

			// 还款通知单
			else if (lActionId == Constant.ApprovalAction.AHEADREPAY_NOTICE) {
				a = id + "";
			}

			// 贴现凭证
			else if (lActionId == Constant.ApprovalAction.DISCOUNT_CREDENCE) {
				a = id + "";
			}

			// 贴现凭证
			else if (lActionId == Constant.ApprovalAction.DISCOUNT_PAYFORM) {
				a = id + "";
			}
			// 展期申请
			else if (lActionId == Constant.ApprovalAction.EXTEND_APPLY) {
				a = id + "";
			}
			// 展期合同

			else if (lActionId == Constant.ApprovalAction.EXTEND_CONTRACT) {
				long lExtendApplyId = workBiz
						.getExtendApplyIdByExtendContractId(id);
				a = lExtendApplyId + "";
				b = contractid + "";
				c = lLoanType + "";
			}
			// 担保收款通知单
			else if (lActionId == Constant.ApprovalAction.ASSURE_CHARGE_NOTICE) {
				a = id + "";
				b = contractid + "";
				c = statusid + "";
			}
			// 融资租赁收款通知单

			else if (lActionId == Constant.ApprovalAction.LEASEHOLDPAY_NOTICE) {
				a = id + "";
				b = contractid + "";
				c = statusid + "";
			}
			// 免还申请
			else if (lActionId == Constant.ApprovalAction.FREE_APPLY) {
				a = id + "";
				b = contractid + "";
				c = statusid + "";
			}
			// 融资租赁还款通知单
			else if (lActionId == Constant.ApprovalAction.LEASEHOLDREPAY_NOTICE) {
				a = id + "";
				b = contractid + "";
				c = statusid + "";
			}
			// 银团贷款提款通知单
			else if (lActionId == Constant.ApprovalAction.LOANDRAW_NOTICE) {
				a = id + "";
				b = contractid + "";
			}
			// 担保保后处理通知单
			else if (lActionId == Constant.ApprovalAction.ASSURE_MANAGEMENT_NOTICE) {
				a = id + "";
				b = contractid + "";
			}
			// 保证金保后处理通知单
			else if (lActionId == Constant.ApprovalAction.RECOG_NOTIC) {
				a = id + "";
				b = contractid + "";
			}
			// 贷款逾期
			else if (lActionId == Constant.ApprovalAction.OVERDUE_APPLY) {
				OverDueInfo overDueInfo = overDueApplyEjb
						.findOverDueApplyBaseInfoByID(id);
				a = id + "";
				b = overDueInfo.getPlanID() + "";
				c = contractid + "";
			}
			// 贷款合同转让申请
			else if (lActionId == Constant.ApprovalAction.LOANCONTRACT_APPLY) {
				a = id + "";
				b = contractid + "";
			}
			// 批量还款通知单
			else if (lActionId == Constant.ApprovalAction.BATCHREPAY_NOTICE) {
				a = id + "";
			}
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("CODE") + ","
					+ lActionId + "," + a + "," + b + "," + c);
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs.getString("ActionName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList, rs.getString("ContractCode"));
			PagerTools.returnCellList(cellList,currencysymbol+strFormatedAmount);
			
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

			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("ContractCode")+"," +rs.getString("contractID")+"," +rs.getString("id"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList,currencysymbol+strFormatedAmount);
			
			PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(rs.getLong("OldStatus")));
			PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(rs.getLong("changeStatus")));
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
		String sql = dao.queryLoanContractRisklevelChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("ContractCode")+"," +rs.getString("contractID")+"," +rs.getString("id"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList,currencysymbol+strFormatedAmount);
			
			PagerTools.returnCellList(cellList,LOANConstant.VentureLevel.getName(rs.getLong("OldStatus")));
			PagerTools.returnCellList(cellList,LOANConstant.VentureLevel.getName(rs.getLong("changeStatus")));
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
		String sql = dao.queryLoanContractPlanChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("ContractCode")+"," +rs.getString("contractID")+"," +rs.getString("id"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList,currencysymbol+strFormatedAmount);
			
			PagerTools.returnCellList(cellList,rs.getLong("Reason"));
			PagerTools.returnCellList(cellList,rs.getLong("planVersion"));
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
		String sql = dao.queryLoanContractRateChangeWork(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			pagerInfo.setSqlString(sql);
			map.put("currencysymbol", currencysymbol);
			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
		while (rs.next()) {
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("ContractCode")+"," +rs.getString("id")+"," +rs.getString("contractID")+","+rs.getLong("LoanTypeId"));
			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loanTypeId")));
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList,DataFormat.formatRate(rs.getLong("Rate")));
			
			PagerTools.returnCellList(cellList,rs.getString("Reason"));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("DtValiDate")));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("inputdate")));
			PagerTools.returnCellList(cellList,LOANConstant.InterestRateSettingStatus.getName(rs.getLong("StatusID")));
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
			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
		while (rs.next()) {
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList,
					rs.getString("LoanSubTypeName") == null ? "" : rs
							.getString("LoanSubTypeName"));
			PagerTools.returnCellList(cellList, rs
					.getString("Batchid")+","+rs
					.getString("Batchid")+","+rs
					.getString("loanSubTypeId"));
			PagerTools.returnCellList(cellList,rs.getString("inputUserName"));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("InputDate")));
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
			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
		while (rs.next()) {
			double creditAmount = rs.getDouble("Amount");
			
			String strOperationType = LOANConstant.OperationType.getName(rs.getLong("operationType"));
			
			if(rs.getLong("operationType") == LOANConstant.OperationType.CHANGE){
				strOperationType = strOperationType + "("+ LOANConstant.OperationSign.getName(rs.getLong("operationSign")) +")";
			}
			
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs.getString("Code")+"," +rs.getLong("id")+"," +rs.getLong("operationType")+","+rs.getLong("creditModel"));
			PagerTools.returnCellList(cellList, rs
					.getString("BorrowClientName"));
			PagerTools.returnCellList(cellList,LOANConstant.CreditModel.getName(rs.getLong("creditModel")));
			
			PagerTools.returnCellList(cellList,strOperationType);
			PagerTools.returnCellList(cellList,DataFormat.formatListAmount(creditAmount));

			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("StartDate")));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("EndDate")));
			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("InputDate")));
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
			pagerInfo.setExtensionMothod(LoanRefWorksBiz.class,
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
		while (rs.next()) {
			
			
			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, rs
					.getString("CheckReportCode"));
			PagerTools.returnCellList(cellList, rs.getString("ClientName")+"," +rs.getLong("id")+"," +rs.getLong("LoanSubTypeId")+","+rs.getLong("Clientid")+","+rs.getLong("ContractID"));
			PagerTools.returnCellList(cellList, rs
					.getString("ContractCode"));
			PagerTools.returnCellList(cellList,LOANConstant.AfterCheckType.getName(rs.getLong("CheckType")));
			
			PagerTools.returnCellList(cellList,rs.getString("checkYear"));
			PagerTools.returnCellList(cellList,LOANConstant.VentureLevel.getName(rs.getLong("Advice")));

			
			PagerTools.returnCellList(cellList,rs.getString("InputUserName"));
			PagerTools.returnCellList(cellList,DataFormat.getDateString(rs.getTimestamp("InputDate")));
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
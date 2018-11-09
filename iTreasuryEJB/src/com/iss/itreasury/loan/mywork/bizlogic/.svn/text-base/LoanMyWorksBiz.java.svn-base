package com.iss.itreasury.loan.mywork.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.loan.mywork.dataentity.AfterCreditWorkInfo;
import com.iss.itreasury.loan.mywork.dataentity.LoanMyWorkInfo;
import com.iss.itreasury.loan.overdueapply.bizlogic.OverDueApply;
import com.iss.itreasury.loan.overdueapply.bizlogic.OverDueApplyHome;
import com.iss.itreasury.loan.overdueapply.dataentity.OverDueInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanMyWorksBiz {

	public PagerInfo queryMyWork(LoanMyWorkInfo info, String currencysymbol)
			throws Exception {
		// TODO Auto-generated method stub
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
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
		LoanMyWorkInfo info = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");
		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection c_waitDealWithWork = (Vector) workBiz
				.queryLoanTransActionWork(info);

		String currencysymbol = map.get("currencysymbol") + "";
		try {
			if (c_waitDealWithWork != null && c_waitDealWithWork.size() > 0) {
				Iterator it = c_waitDealWithWork.iterator();
				while (it.hasNext()) {
					// 获取数据
					LoanMyWorkInfo workInfo = (LoanMyWorkInfo) it.next();
					String strFormatedAmount = DataFormat
							.formatDisabledAmount(workInfo.getAmount());
					strFormatedAmount = strFormatedAmount == null
							|| strFormatedAmount.equals("") ? "0.00"
							: strFormatedAmount;

					String strUrl = "";
					long lLoanType = workInfo.getLoanTypeId();
					long lActionId = workInfo.getActionID();

					// 申请
					if (lActionId == Constant.ApprovalAction.LOAN_APPLY) {
						strUrl = workInfo.getId() + "," + lLoanType + ",";

						// 合同
					} else if (lActionId == Constant.ApprovalAction.LOAN_CONTRACT) {
						strUrl = workInfo.getContractID() + "," + lLoanType
								+ ",";
						// 放款通知单
					} else if (lActionId == Constant.ApprovalAction.LOANPAY_NOTICE) {
						if (workInfo.getLoanTypeId() == LOANConstant.LoanType.TX) {
							strUrl = workInfo.getId() + "" + "," + ",";
						} else if (workInfo.getLoanTypeId() == LOANConstant.LoanType.YT) {
							long lYtDrawNoticeId = workBiz
									.findYTDrawNoticeIdByPayNoticeId(workInfo
											.getId());
							strUrl = workInfo.getId() + "," + lYtDrawNoticeId
									+ ",";

						} else {
							strUrl = workInfo.getContractID() + ","
									+ workInfo.getId() + ",";
						}
					}

					// 还款通知单
					else if (lActionId == Constant.ApprovalAction.AHEADREPAY_NOTICE) {

						strUrl = workInfo.getId() + "" + "," + ",";
					}
					// 批量还款通知单
					else if (lActionId == Constant.ApprovalAction.BATCHREPAY_NOTICE) {

						strUrl = workInfo.getId() + "," + ",";
					}

					// 贴现凭证
					else if (lActionId == Constant.ApprovalAction.DISCOUNT_CREDENCE) {

						strUrl = workInfo.getId() + "," + ",";
					}
					// 贴现凭证
					else if (lActionId == Constant.ApprovalAction.DISCOUNT_PAYFORM) {

						strUrl = workInfo.getId() + "," + ",";
					}
					// 展期申请
					else if (lActionId == Constant.ApprovalAction.EXTEND_APPLY) {

						strUrl = workInfo.getId() + "," + ",";
					}
					// 展期合同

					else if (lActionId == Constant.ApprovalAction.EXTEND_CONTRACT) {
						long lExtendApplyId = workBiz
								.getExtendApplyIdByExtendContractId(workInfo
										.getId());
						strUrl = lExtendApplyId + ","
								+ workInfo.getContractID() + ","
								+ workInfo.getLoanTypeId();
					}
					// 担保收款通知单
					else if (lActionId == Constant.ApprovalAction.ASSURE_CHARGE_NOTICE) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ","
								+ workInfo.getStatusID();
					}
					// 保证金收款通知单
					else if (lActionId == Constant.ApprovalAction.RECOG_NOTIC) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ","
								+ workInfo.getStatusID();
					}
					// 融资租赁收款通知单

					else if (lActionId == Constant.ApprovalAction.LEASEHOLDPAY_NOTICE) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ","
								+ workInfo.getStatusID();
					}
					// 免还申请
					else if (lActionId == Constant.ApprovalAction.FREE_APPLY) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ","
								+ workInfo.getStatusID();
					}
					// 融资租赁还款通知单
					else if (lActionId == Constant.ApprovalAction.LEASEHOLDREPAY_NOTICE) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ","
								+ workInfo.getStatusID();
					}
					// 银团贷款提款通知单
					else if (lActionId == Constant.ApprovalAction.LOANDRAW_NOTICE) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ",";
					}
					// 担保保后处理通知单
					else if (lActionId == Constant.ApprovalAction.ASSURE_MANAGEMENT_NOTICE) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ",";
					}
					// 贷款逾期
					else if (lActionId == Constant.ApprovalAction.OVERDUE_APPLY) {

						// 贷款逾期EJB
						OverDueApplyHome home = (OverDueApplyHome) EJBObject
								.getEJBHome("OverDueApplyHome");
						OverDueApply overDueApplyEjb = home.create();
						OverDueInfo overDueInfo = overDueApplyEjb
								.findOverDueApplyBaseInfoByID(workInfo.getId());
						strUrl = workInfo.getId() + ","
								+ overDueInfo.getPlanID() + ","
								+ workInfo.getContractID();
					}
					// 贷款合同转让申请
					else if (lActionId == Constant.ApprovalAction.LOANCONTRACT_APPLY) {

						strUrl = workInfo.getId() + ","
								+ workInfo.getContractID() + ",";
					}

					// 存储列数据
					cellList = new ArrayList();

					PagerTools.returnCellList(cellList,
							workInfo.getCode() == null
									|| "".equals(workInfo.getCode()) ? ""
									: workInfo.getCode() + "," + strUrl + ","
											+ lActionId);
					PagerTools.returnCellList(cellList, workInfo
							.getLoanTypeName());
					PagerTools.returnCellList(cellList, workInfo
							.getLoanSubTypeName() == null ? "" : workInfo
							.getLoanSubTypeName());
					PagerTools.returnCellList(cellList, workInfo
							.getActionName());
					PagerTools.returnCellList(cellList, workInfo
							.getBorrowClientName() == null ? "" : workInfo
							.getBorrowClientName());
					PagerTools.returnCellList(cellList, workInfo
							.getContractCode() == null ? "" : workInfo
							.getContractCode());
					PagerTools.returnCellList(cellList, currencysymbol
							+ strFormatedAmount);
					PagerTools.returnCellList(cellList, workInfo
							.getInputUserName() == null ? "" : workInfo
							.getInputUserName());
					PagerTools.returnCellList(cellList, DataFormat
							.getDateString(workInfo.getInputDate()));

					// 存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(0));

					// 返回数据
					resultList.add(rowInfo);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}

		return resultList;
	}

	public PagerInfo queryLoanContractStatusChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"loanContractStatusChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList loanContractStatusChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qinfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection cltContractInfo = (Vector) workBiz
				.queryLoanContractStatusChangeWork(qinfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (cltContractInfo != null && cltContractInfo.size() > 0) {
			Iterator it = cltContractInfo.iterator();
			while (it.hasNext()) {
				// 获取数据
				LoanMyWorkInfo info = (LoanMyWorkInfo) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(info
						.getAmount());
				strFormatedAmount = strFormatedAmount == null
						|| strFormatedAmount.equals("") ? "0.00"
						: strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, info.getCode());
				PagerTools.returnCellList(cellList,
						info.getContractCode() == null
								|| "".equals(info.getContractCode()) ? ""
								: info.getContractCode() + ","
										+ info.getContractID() + ","
										+ info.getId());
				PagerTools.returnCellList(cellList, LOANConstant.LoanType
						.getName(info.getLoanTypeId()));
				PagerTools.returnCellList(cellList, info.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, info.getBorrowClientName());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, LOANConstant.ContractStatus
						.getName(info.getOldStatus()));
				PagerTools.returnCellList(cellList, LOANConstant.ContractStatus
						.getName(info.getChangeStatus()));
				PagerTools.returnCellList(cellList, info.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(info.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractRisklevelChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"loanContractRisklevelChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList loanContractRisklevelChangeWorkResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qinfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection cltContractInfo = (Vector) workBiz
				.queryLoanContractRisklevelChangeWork(qinfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (cltContractInfo != null && cltContractInfo.size() > 0) {
			Iterator it = cltContractInfo.iterator();
			while (it.hasNext()) {
				// 获取数据
				LoanMyWorkInfo info = (LoanMyWorkInfo) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(info
						.getAmount());
				strFormatedAmount = strFormatedAmount == null
						|| strFormatedAmount.equals("") ? "0.00"
						: strFormatedAmount;

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, info.getCode());
				PagerTools.returnCellList(cellList,
						info.getContractCode() == null
								|| "".equals(info.getContractCode()) ? ""
								: info.getContractCode() + ","
										+ info.getContractID() + ","
										+ info.getId());
				PagerTools.returnCellList(cellList, LOANConstant.LoanType
						.getName(info.getLoanTypeId()));
				PagerTools.returnCellList(cellList, info.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, info.getBorrowClientName());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, LOANConstant.VentureLevel
						.getName(info.getOldStatus()));
				PagerTools.returnCellList(cellList, LOANConstant.VentureLevel
						.getName(info.getChangeStatus()));
				PagerTools.returnCellList(cellList, info.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(info.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractPlanChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"loanContractPlanChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	                 
	public ArrayList loanContractPlanChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qinfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection cltContractInfo = (Vector) workBiz
				.queryLoanContractPlanChangeWork(qinfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (cltContractInfo != null && cltContractInfo.size() > 0) {
			Iterator it = cltContractInfo.iterator();
			String _tmp = null;

			while (it.hasNext()) {
				// 获取数据
				LoanMyWorkInfo info = (LoanMyWorkInfo) it.next();
				String strFormatedAmount = DataFormat.formatDisabledAmount(info
						.getAmount());
				strFormatedAmount = strFormatedAmount == null
						|| strFormatedAmount.equals("") ? "0.00"
						: strFormatedAmount;

				if ((_tmp != null && _tmp.equals(info.getContractCode()))
						|| info.getContractPlanVersion() == 0)
					continue;

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,
						info.getContractCode() == null
								|| "".equals(info.getContractCode()) ? ""
								: info.getContractCode() + ","
										+ info.getContractID() + ","
										+ info.getContractCode() + ","
										+ info.getLoanSubTypeId());
				PagerTools.returnCellList(cellList, LOANConstant.LoanType
						.getName(info.getLoanTypeId()));
				PagerTools.returnCellList(cellList, info.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, info.getBorrowClientName());
				PagerTools.returnCellList(cellList, currencysymbol
						+ strFormatedAmount);
				PagerTools.returnCellList(cellList, info
						.getContractChangeReason() == null ? "" : info
						.getContractChangeReason());
				PagerTools.returnCellList(cellList, info
						.getContractPlanVersion());
				PagerTools.returnCellList(cellList, info.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(info.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);
				_tmp = info.getContractCode();
			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractRateChangeWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"loanContractRateChangeWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	public ArrayList loanContractRateChangeWorkResultSetHandle(ResultSet rs,
			Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qInfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection cltContractInfo = (Vector) workBiz
				.queryLoanContractRateChangeWork(qInfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (cltContractInfo != null && cltContractInfo.size() > 0) {
			Iterator it = cltContractInfo.iterator();

			while (it.hasNext()) {
				// 获取数据
				LoanMyWorkInfo info = (LoanMyWorkInfo) it.next();
				String url = info.getId() + "," + info.getContractID() + ","
						+ info.getLoanTypeId();

				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList,
						info.getContractCode() == null
								|| "".equals(info.getContractCode()) ? ""
								: info.getContractCode() + "," + url);
				PagerTools.returnCellList(cellList, LOANConstant.LoanType
						.getName(info.getLoanTypeId()));
				PagerTools.returnCellList(cellList, info.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, info.getBorrowClientName());
				PagerTools.returnCellList(cellList, DataFormat.formatRate(info
						.getRate()));
				PagerTools.returnCellList(cellList,
						info.getReason() == null ? "" : info.getReason());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(info.getDtValiDate()));
				PagerTools.returnCellList(cellList,
						info.getInputDate() == null ? "" : DataFormat
								.getDateString(info.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);
			}
		}

		return resultList;
	}

	public PagerInfo queryLoanContractRateChangeWorkBatch(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"loanContractPlanChangeWorkBatchResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList loanContractPlanChangeWorkBatchResultSetHandle(
			ResultSet rs, Map map) throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qInfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection cltContractInfo = (Vector) workBiz
				.queryLoanContractRateChangeWork(qInfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (cltContractInfo != null && cltContractInfo.size() > 0) {
			Iterator it = cltContractInfo.iterator();

			while (it.hasNext()) {
				// 获取数据
				LoanMyWorkInfo workInfo = (LoanMyWorkInfo) it.next();
				String url = workInfo.getBatchid() + ","
						+ workInfo.getLoanSubTypeId();

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo
						.getLoanSubTypeName());
				PagerTools.returnCellList(cellList, workInfo.getBatchid() + ","
						+ url);
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);
			}
		}

		return resultList;
	}

	public PagerInfo queryLoanCredit(LoanMyWorkInfo info, String currencysymbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"queryLoanCreditResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryLoanCreditResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qInfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection creditInfo = (Vector) workBiz.queryLoanCredit(qInfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (creditInfo != null && creditInfo.size() > 0) {
			Iterator it = creditInfo.iterator();

			while (it.hasNext()) {
				// 获取数据
				LoanMyWorkInfo workInfo = (LoanMyWorkInfo) it.next();
				double creditAmount = workInfo.getAmount();

				String strOperationType = LOANConstant.OperationType
						.getName(workInfo.getOperationType());

				if (workInfo.getOperationType() == LOANConstant.OperationType.CHANGE) {
					strOperationType = strOperationType
							+ "("
							+ LOANConstant.OperationSign.getName(workInfo
									.getOperationSign()) + ")";
				}
				String url = workInfo.getId() + ","
						+ workInfo.getOperationType() + ","
						+ workInfo.getCreditModel();

				// 存储列数据
				cellList = new ArrayList();

				PagerTools.returnCellList(cellList, workInfo.getCode() == null
						|| "".equals(workInfo.getCode()) ? "" : workInfo
						.getCode()
						+ "," + url);
				PagerTools.returnCellList(cellList, workInfo
						.getBorrowClientName());
				PagerTools.returnCellList(cellList, LOANConstant.CreditModel
						.getName(workInfo.getCreditModel()));
				PagerTools.returnCellList(cellList, strOperationType);
				PagerTools.returnCellList(cellList, DataFormat
						.formatListAmount(creditAmount));
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getStartDate()));
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getEndDate()));
				PagerTools
						.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat
						.getDateString(workInfo.getInputDate()));

				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);
			}
		}

		return resultList;
	}

	public PagerInfo queryAfterCreditWork(LoanMyWorkInfo info,
			String currencysymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// 得到查询SQL
			sql = "select 1 from dual";
			pagerInfo.setSqlString(sql);

			map.put("LoanMyWorkInfo", info);
			map.put("currencysymbol", currencysymbol);

			pagerInfo.setExtensionMothod(LoanMyWorksBiz.class,
					"queryAfterCreditWorkResultSetHandle", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList queryAfterCreditWorkResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		LoanMyWorkInfo qInfo = (LoanMyWorkInfo) map.get("LoanMyWorkInfo");

		LoanMyWorkBiz workBiz = new LoanMyWorkBiz();
		Collection cltContractInfo =(Vector)workBiz.queryAfterCreditWork(qInfo);

		String currencysymbol = map.get("currencysymbol") + "";
		if (cltContractInfo != null && cltContractInfo.size() > 0) {
			Iterator it = cltContractInfo.iterator();

			while (it.hasNext()) {
				// 获取数据
				AfterCreditWorkInfo workInfo = (AfterCreditWorkInfo)it.next();
				// 存储列数据
				cellList = new ArrayList();
				PagerTools.returnCellList(cellList, workInfo.getCheckReportCode());
				PagerTools.returnCellList(cellList, workInfo.getClientName()+","+workInfo.getId()+","+workInfo.getLoanSubTypeId());
				PagerTools.returnCellList(cellList, workInfo.getContractCode() );
				PagerTools.returnCellList(cellList, LOANConstant.AfterCheckType.getName(workInfo.getCheckType()) );
				PagerTools.returnCellList(cellList, workInfo.getCheckYear());
				PagerTools.returnCellList(cellList, LOANConstant.VentureLevel.getName(workInfo.getAdvice()) );
				PagerTools.returnCellList(cellList, workInfo.getInputUserName());
				PagerTools.returnCellList(cellList, DataFormat.getDateString(workInfo.getInputDate()) );
				
				// 存储行数据
				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(0));

				// 返回数据
				resultList.add(rowInfo);

			}
		}

		return resultList;
	}
}

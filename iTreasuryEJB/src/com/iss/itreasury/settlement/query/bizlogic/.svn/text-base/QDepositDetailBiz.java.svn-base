package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.settlement.query.Dao.QDepositDetailDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryDepositDetailWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.query.queryobj.QTransaction;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 账户查询业务层
 * 
 * @author xiang
 * 
 */
public class QDepositDetailBiz {

	private QDepositDetailDao dao = new QDepositDetailDao();
	private QueryDepositDetailWhereInfo qInfo;

	public PagerInfo queryDepositDetail(QueryDepositDetailWhereInfo qInfo,
			String strAssistAction) throws Exception {
		// TODO Auto-generated method stub
		{
			this.qInfo = qInfo;
			PagerInfo pagerInfo = null;
			String sql = null;

			try {
				pagerInfo = new PagerInfo();
				// 得到查询SQL
				sql = dao.getDepositDetaiSQL(qInfo);
				pagerInfo.setSqlString(sql);
				Map params = new HashMap();
				params.put("param", qInfo);
				params.put("strAssistAction", strAssistAction);
				pagerInfo.setExtensionMothod(QDepositDetailBiz.class,
						"resultSetHandle", params);
				pagerInfo.setTotalExtensionMothod(QDepositDetailBiz.class, "totalResultSetHandle", params);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}

	}

	public ArrayList resultSetHandle(ResultSet rs, Map params)
			throws IException {

		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		String strAssistAction = (String) params.get("strAssistAction");
		try {
			if (rs != null) {
				while (rs.next()) {
					// 获取数据
					String id = rs.getString("ID");

					String naccounttypeid = SETTConstant.AccountType.getName(rs
							.getLong("NACCOUNTTYPEID"));

					String saccountno = rs.getString("SACCOUNTNO");

					String scode = rs.getString("SCODE");

					String sname = rs.getString("SNAME");

					String nstatusid = SETTConstant.AccountStatus.getName(rs
							.getLong("NSTATUSID"));

					String dtopen = DataFormat.formatDate(rs
							.getTimestamp("DTOPEN"));

					String ac_ninterestrateplanid = NameRef
							.getInterestRatePlanNameByID(rs
									.getLong("AC_NINTERESTRATEPLANID"));

					double sumaccountbalance = rs
							.getDouble("SUMACCOUNTBALANCE");

					double sumaccounthistorybalance = rs
							.getDouble("SUMACCOUNTHISTORYBALANCE");

					double accountbalance = 0.00;

					QueryDepositDetailWhereInfo qInfo = (QueryDepositDetailWhereInfo) params
							.get("param");
					// 处理数据

					if (Env.getSystemDate(qInfo.getOfficeID(),
							qInfo.getCurrencyID()).equals(qInfo.getDate())) {
						accountbalance = sumaccountbalance;
					} else {
						accountbalance = sumaccounthistorybalance;
					}
					String amount = DataFormat.formatAmount(Double
							.parseDouble(accountbalance + ""), 2);
					String strURL = "";
					String param = "";
					
					if (SETTConstant.AccountType.isCurrentAccountType(rs
							.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType
									.isOtherDepositAccountType(rs
											.getLong("NACCOUNTTYPEID"))) {// 活期
						strURL = "/settlement/account/control/c024.jsp";
						param = "lAccountID="
								+ rs.getString("ID")
								+ "&strTransHistoryDate="
 								+ "&strAction=CurrentAccountQuery&strSuccessPageURL=../query/view/v044.jsp&strFailPageURL=../query/view/v044.jsp";
						
					}	
					
					
					if (SETTConstant.AccountType.isFixAccountType(rs
							.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isNotifyAccountType(rs
									.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isMarginAccountType(rs
									.getLong("NACCOUNTTYPEID"))) {// 定期
						String ControlSource = "";
						if (SETTConstant.AccountType.isFixAccountType(rs
								.getLong("NACCOUNTTYPEID"))) {
							ControlSource = String
									.valueOf(SETTConstant.AccountGroupType.FIXED);
						}
						if (SETTConstant.AccountType.isNotifyAccountType(rs
								.getLong("NACCOUNTTYPEID"))) {
							ControlSource = String
									.valueOf(SETTConstant.AccountGroupType.NOTIFY);
						}
						if (SETTConstant.AccountType.isMarginAccountType(rs
								.getLong("NACCOUNTTYPEID"))) {
							ControlSource = String
									.valueOf(SETTConstant.AccountGroupType.MARGIN);
						}
						strURL = "/settlement/query/control/c021.jsp";
						param ="ControlSource="
								+ ControlSource
								+ "&lAccountIDFromCtrl="
								+ rs.getString("SACCOUNTNO")
								+ "&lAccountIDToCtrl="
								+ rs.getString("SACCOUNTNO")
								+ "&lFixedDepositStatus="
								+ SETTConstant.SubAccountStatus.NORMAL
								+ "&strFixedEndDate="
								+ qInfo.getDate()
								+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v022.jsp&strFailPageURL=/settlement/query/view/v022.jsp";
						
						if ("clientGather".equals(strAssistAction)) {
							strURL = "/settlement/query/control/c021.jsp";
							param = "ControlSource="
									+ ControlSource
									+ "&lAccountIDFromCtrl="
									+ rs.getString("SACCOUNTNO")
									+ "&lAccountIDToCtrl="
									+ rs.getString("SACCOUNTNO")
									+ "&lFixedDepositStatus="
									+ SETTConstant.SubAccountStatus.NORMAL
									+ "&strFixedEndDate="
									+ qInfo.getDate()
									+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v022.jsp&strFailPageURL=error500.jsp";
						}

					}
					
					
					if (SETTConstant.AccountType.isLoanAccountType(rs
							.getLong("NACCOUNTTYPEID"))) {// 贷款
						strURL = "/settlement/account/control/c061.jsp";
						param = "strStartAccountNo="
								+ rs.getString("ID")
								+ "&strEndAccountNo="
								+ rs.getString("ID")
								+ "&strStartAccountNoCtrl="
								+ rs.getString("SACCOUNTNO")
								+ "&strEndAccountNoCtrl="
								+ rs.getString("SACCOUNTNO")
								+ "&lLoanPayStatusID="
								+ SETTConstant.SubAccountStatus.NORMAL
								+ "&DtFinish="
								+ qInfo.getDate()
								+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/account/view/v061-1.jsp&strFailPageURL=/settlement/account/view/v061.jsp";
						if ("clientGather".equals(strAssistAction)) {
							strURL = "/settlement/account/control/c061.jsp";
							param = "strStartAccountNo="
									+ rs.getString("ID")
									+ "&strEndAccountNo="
									+ rs.getString("ID")
									+ "&strStartAccountNoCtrl="
									+ rs.getString("SACCOUNTNO")
									+ "&strEndAccountNoCtrl="
									+ rs.getString("SACCOUNTNO")
									+ "&lLoanPayStatusID="
									+ SETTConstant.SubAccountStatus.NORMAL
									+ "&DtFinish="
									+ qInfo.getDate()
									+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/account/view/v061-1.jsp&strFailPageURL=error500.jsp";
						}

					}
					if (SETTConstant.AccountType.isBakAccountType(rs
							.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isReserveAccountType(rs
									.getLong("NACCOUNTTYPEID"))) {// 备付金、准备金
						strURL = "/settlement/account/control/c024.jsp";
						param = "lAccountID="
								+ rs.getLong("ID")
								+ "&strTransHistoryDate="
								+ qInfo.getDate()
								+ "&strAction=AccountQuery&strSuccessPageURL=../query/view/v044.jsp&strFailPageURL=../query/view/v044.jsp";

					}
					if (SETTConstant.AccountType.isLendingAccountType(rs
							.getLong("NACCOUNTTYPEID"))) {// 拆借账户特殊处理，跳转页面同活期，但是记贷款方
						strURL = "/settlement/account/control/c024.jsp";
						param = "lAccountID="
								+ rs.getString("ID")
								+ "&strTransHistoryDate="
								+ qInfo.getDate()
								+ "&strAction=AccountQuery&strSuccessPageURL=../../query/view/v044.jsp&strFailPageURL=../../query/view/v044.jsp";

					}

					// 存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList, id);
					PagerTools.returnCellList(cellList, rs
							.getLong("NACCOUNTTYPEID"));
					PagerTools.returnCellList(cellList, "");
					PagerTools.returnCellList(cellList, saccountno+","+strURL+","+param);
					PagerTools.returnCellList(cellList, scode);
					PagerTools.returnCellList(cellList, sname);
					PagerTools.returnCellList(cellList, naccounttypeid);
					PagerTools.returnCellList(cellList, nstatusid);
					PagerTools.returnCellList(cellList, dtopen);
					PagerTools.returnCellList(cellList, ac_ninterestrateplanid);
					PagerTools.returnCellList(cellList,
							accountbalance > 0 ? amount : "0.00");

					// 存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

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
	
	public ArrayList totalResultSetHandle(ResultSet rs, Map params)throws Exception {
		
		ArrayList list = new ArrayList(); // 最终返回结果
		double dSumDeposit = 0.00;//存款合计
		double dSumLoan = 0.00;//贷款合计
		try {
			if (rs != null) {
				while (rs.next()) {
					// 获取数据
					double sumaccountbalance = rs.getDouble("SUMACCOUNTBALANCE");
					double sumaccounthistorybalance = rs.getDouble("SUMACCOUNTHISTORYBALANCE");
					double accountbalance = 0.00;

					QueryDepositDetailWhereInfo qInfo = (QueryDepositDetailWhereInfo) params.get("param");
					
					// 处理数据
					if (Env.getSystemDate(qInfo.getOfficeID(),
							qInfo.getCurrencyID()).equals(qInfo.getDate())) {
						accountbalance = sumaccountbalance;
					} else {
						accountbalance = sumaccounthistorybalance;
					}
					String amount = DataFormat.formatAmount(Double
							.parseDouble(accountbalance + ""), 2);
					String strURL = "";
					String param = "";
					
					if (SETTConstant.AccountType.isCurrentAccountType(rs.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isOtherDepositAccountType(rs.getLong("NACCOUNTTYPEID"))) {// 活期
						
						String accountTypeCode = Config.getProperty(ConfigConstant.SETTLEMENT_WTDEPOSIT_ACCOUNTTYPECODE,"2");
						if(!rs.getString("sAccountTypeCode").equals(accountTypeCode) && !rs.getString("sAccountTypeCode").equals(accountTypeCode)){
							dSumDeposit = dSumDeposit + accountbalance;
						}
					}	
					
					if (SETTConstant.AccountType.isFixAccountType(rs.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isNotifyAccountType(rs.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isMarginAccountType(rs.getLong("NACCOUNTTYPEID"))) {// 定期
						
						dSumDeposit = dSumDeposit + accountbalance;
					}
					
					if (SETTConstant.AccountType.isLoanAccountType(rs.getLong("NACCOUNTTYPEID"))) {// 贷款
					
						dSumLoan = dSumLoan + accountbalance;
					}
					if (SETTConstant.AccountType.isBakAccountType(rs.getLong("NACCOUNTTYPEID"))
							|| SETTConstant.AccountType.isReserveAccountType(rs.getLong("NACCOUNTTYPEID"))) {// 备付金、准备金
						
					 	dSumDeposit = dSumDeposit + accountbalance;
					}
					if (SETTConstant.AccountType.isLendingAccountType(rs.getLong("NACCOUNTTYPEID"))) {// 拆借账户特殊处理，跳转页面同活期，但是记贷款方
						
						dSumLoan = dSumLoan + accountbalance;
					}
				}
				list.add("存款合计{" + DataFormat.formatDisabledAmount( dSumDeposit ) + "}");
				list.add("贷款合计{" + DataFormat.formatDisabledAmount( dSumLoan ) + "}");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return list;
	}
}

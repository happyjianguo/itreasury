package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.settlement.query.Dao.QDepositDetailsDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.queryobj.BaseQueryObject;
import com.iss.itreasury.settlement.query.queryobj.QAccount;
import com.iss.itreasury.settlement.query.queryobj.QAverageAccountBalance;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountSumInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QDepositDetailsBiz {

	QDepositDetailsDao dao = new QDepositDetailsDao();

	public PagerInfo queryQueryAccount(QueryAccountWhereInfo info, long unit)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.getDepositLoanSQL(info);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);
			Map params = new HashMap();
			params.put("info", info);
			params.put("unit", unit);
			pagerInfo.setExtensionMothod(QDepositDetailsBiz.class,
					"accountDetailResultSetHandle", params);
			pagerInfo.setTotalExtensionMothod(QDepositDetailsBiz.class,
					"totalAccountDetailResultSetHandle", params);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public PagerInfo queryAccountInfo(QueryAccountWhereInfo info, long unit,String CurrencySymbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.getDepositLoanSQL(info);
			// 得到查询SQL
			pagerInfo.setSqlString(sql);
			Map params = new HashMap();
			params.put("info", info);
			params.put("unit", unit);
			params.put("CurrencySymbol", CurrencySymbol);
			pagerInfo.setExtensionMothod(QDepositDetailsBiz.class,
					"accountInfoResultSetHandle", params);
			pagerInfo.setTotalExtensionMothod(QDepositDetailsBiz.class,
					"totalAccountInfoResultSetHandle", params);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList totalAccountDetailResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QueryAccountWhereInfo info = (QueryAccountWhereInfo) map.get("info");
		long unit = Long.valueOf(map.get("unit") + "").longValue();
		QAverageAccountBalance qaab = new QAverageAccountBalance();
		QueryAccountSumInfo qasi = qaab
				.queryAverageAccountBalanceSumByAccountID(info);
		ArrayList list = new ArrayList();
		list.add("活期存款合计{"
				+ (qasi.getCurrentBalanceSum() > 0 ? DataFormat
						.formatDisabledAmount(qasi.getCurrentBalanceSum()
								/ unit) : "0.00") + "}");
		list.add("定期存款合计{"
				+ (qasi.getFixBalanceSum() > 0 ? DataFormat
						.formatDisabledAmount(qasi.getFixBalanceSum() / unit)
						: "0.00") + "}");
		list
				.add("通知存款合计{"
						+ (qasi.getNoticeBalanceSum() > 0 ? DataFormat
								.formatDisabledAmount(qasi
										.getNoticeBalanceSum()
										/ unit) : "0.00") + "}");

		return list;
	}

	public ArrayList accountDetailResultSetHandle(ResultSet rs, Map map)
			throws Exception {

		QueryAccountWhereInfo info = (QueryAccountWhereInfo) map.get("info");
		long unit = Long.valueOf(map.get("unit") + "").longValue();
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		QAccount queryObj = new QAccount();
		try {

			if (rs != null) {
				while (rs.next()) {
					info.setClientID(rs.getLong("ClientID"));
					double dCurrentBalance = queryObj
							.getCurrentBalanceOfClient(info);
					double dFixBalance = queryObj.getFixBalanceOfClient(info);
					double dNoticeBalance = queryObj
							.getNoticeBalanceOfClient(info);
					//
					double dPrevDayBalanceSum = queryObj
							.getPrevDayDepositAccountBalanceOfClient(info);
					double dPrevMonthBalanceSum = queryObj
							.getPrevMonthDepositAccountBalanceOfClient(info);
					double dPrevYearBalanceSum = queryObj
							.getPrevYearDepositAccountBalanceOfClient(info);
					//
					double dPrevDayBalanceDiff = dCurrentBalance + dFixBalance
							+ dNoticeBalance - dPrevDayBalanceSum;
					double dPrevMonthBalanceDiff = dCurrentBalance
							+ dFixBalance + dNoticeBalance
							- dPrevMonthBalanceSum;
					double dPrevYearBalanceDiff = dCurrentBalance + dFixBalance
							+ dNoticeBalance - dPrevYearBalanceSum;

					String url = "/settlement/query/control/c002.jsp";
					String params = "lClientIDStartCtrl="
							+ rs.getString("ClientCode")
							+ "&lClientIDEndCtrl="
							+ rs.getString("ClientCode")
							+ "&QueryDate="
							+ DataFormat.formatDate(info.getQueryDate())
							+ "&strSuccessPageURL=/settlement/query/view/v005.jsp&strFailPageURL=/settlement/query/view/v005.jsp&strFromPage=v007-1.jsp&amount="
							+ unit;

					// 存储列数据
					cellList = new ArrayList();

					PagerTools.returnCellList(cellList, rs
							.getString("ClientCode")
							+ "," + url + "," + params);
					PagerTools.returnCellList(cellList, rs
							.getString("ClientName"));
					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(dCurrentBalance / unit, 2));
					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(dFixBalance / unit, 2));
					PagerTools.returnCellList(cellList, DataFormat
							.formatDisabledAmount(dNoticeBalance / unit, 2));
					PagerTools.returnCellList(cellList,
							DataFormat.formatDisabledAmount((dNoticeBalance
									+ dFixBalance + dCurrentBalance)
									/ unit, 2));
					PagerTools.returnCellList(cellList, DataFormat
							.formatListAmount(dPrevDayBalanceDiff / unit));
					PagerTools.returnCellList(cellList, DataFormat
							.formatListAmount(dPrevMonthBalanceDiff / unit));
					PagerTools.returnCellList(cellList, DataFormat
							.formatListAmount(dPrevYearBalanceDiff / unit));

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

	public ArrayList totalAccountInfoResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QueryAccountWhereInfo info = (QueryAccountWhereInfo) map.get("info");
		long unit = Long.valueOf(map.get("unit") + "").longValue();
		double depositBalanceSum = 0.0;
		double loanBalanceSum = 0.0;
		double depositCanUseBalanceSum = 0.0;
		double loanCanUseBalanceSum = 0.0;
		ArrayList list = new ArrayList();
		QAccount qobj = new QAccount();

		String strDepositAccountType = qobj.getDepositAccountType(info
				.getCurrencyID(), info.getOfficeID());
		String strLoanAccountType = qobj.getLoanAccountType(info
				.getCurrencyID(), info.getOfficeID());

		String[] sDepositAccountType = strDepositAccountType.split(",");
		long[] lDepositAccountType = new long[sDepositAccountType.length];
		String[] sLoanAccountType;
		long[] lLoanAccountType;
		if (sDepositAccountType.length > 1) {
			for (int i = 0; i < sDepositAccountType.length; i++) {
				lDepositAccountType[i] = Long.parseLong(sDepositAccountType[i]);
			}
		}
		sLoanAccountType = strLoanAccountType.split(",");

		lLoanAccountType = new long[sLoanAccountType.length];
		if (sLoanAccountType.length > 1) {
			for (int i = 0; i < sLoanAccountType.length; i++) {
				lLoanAccountType[i] = Long.parseLong(sLoanAccountType[i]);
			}
		}
		while (rs.next()) {
			double dFirstLimitAmount = 0.0;
			double dSecondLimitAmount = 0.0;
			double dThirdLimitAmount = 0.0;
			double dCapitalLimitAmount = 0.0;

			double dCanUseBalance = rs.getDouble("availableBalance");

			// added by mzh_fu 2008/05/08 与账户余额处的逻辑保持一致
			if (rs.getLong("firstLimitTypeId") > 0) {
				dFirstLimitAmount = rs.getDouble("firstLimitAmount");
			}
			if (rs.getLong("secondLimitTypeId") > 0) {
				dSecondLimitAmount = rs.getDouble("secondLimitAmount");
			}
			if (rs.getLong("thirdLimitTypeId") > 0) {
				dThirdLimitAmount = rs.getDouble("thirdLimitAmount");
			}
			if (rs.getDouble("capitalLimitAmount") > 0) {
				dCapitalLimitAmount = rs.getDouble("capitalLimitAmount");
			}

			if (dFirstLimitAmount > 0) {
				dCanUseBalance = dCanUseBalance + dFirstLimitAmount
						- dCapitalLimitAmount;
			} else if (dSecondLimitAmount > 0) {
				dCanUseBalance = dCanUseBalance + dSecondLimitAmount
						- dCapitalLimitAmount;
			} else if (dThirdLimitAmount > 0) {
				dCanUseBalance = dCanUseBalance + dThirdLimitAmount
						- dCapitalLimitAmount;
			} else {
				dCanUseBalance = dCanUseBalance - dCapitalLimitAmount;
			}

			for (int g = 0; g < lDepositAccountType.length; g++) {
				if (rs.getLong("AccountTypeID") == lDepositAccountType[g]) {
					depositCanUseBalanceSum = depositCanUseBalanceSum
							+ dCanUseBalance;
					break;
				}
			}
			for (int g = 0; g < lLoanAccountType.length; g++) {
				if (rs.getLong("AccountTypeID") == lLoanAccountType[g]) {
					loanCanUseBalanceSum = loanCanUseBalanceSum
							+ dCanUseBalance;
					break;
				}
			}
			for (int g = 0; g < lDepositAccountType.length; g++) {
				if (rs.getLong("AccountTypeID") == lDepositAccountType[g]) {
					depositBalanceSum = depositBalanceSum
							+ rs.getDouble("balance");
					break;
				}
			}
			for (int g = 0; g < lLoanAccountType.length; g++) {
				if (rs.getLong("AccountTypeID") == lLoanAccountType[g]) {
					loanBalanceSum = loanBalanceSum + rs.getDouble("balance");
					break;
				}
			}
		}
		list.add("存款余额合计{"
				+ (depositBalanceSum != 0.0 ? DataFormat
						.formatDisabledAmount(depositBalanceSum / unit)
						: "0.00") + "}");
		list.add("贷款余额合计{"
				+ (loanBalanceSum != 0.0 ? DataFormat
						.formatDisabledAmount(loanBalanceSum / unit) : "0.00")
				+ "}");
		list.add("存款可用余额合计{"
				+ (depositCanUseBalanceSum != 0.0 ? DataFormat
						.formatDisabledAmount(depositCanUseBalanceSum / unit)
						: "0.00") + "}");
		list.add("贷款可用余额合计{"
				+ (loanCanUseBalanceSum != 0.0 ? DataFormat
						.formatDisabledAmount(loanCanUseBalanceSum / unit)
						: "0.00") + "}");

		return list;
	}

	public ArrayList accountInfoResultSetHandle(ResultSet rs, Map map)
			throws Exception {

		QueryAccountWhereInfo info = (QueryAccountWhereInfo) map.get("info");
		long unit = Long.valueOf(map.get("unit") + "").longValue();
		String CurrencySymbol = map.get("CurrencySymbol") + "";
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		QAccount queryObj = new QAccount();
		try {

			if (rs != null) {
				while (rs.next()) {
					double dFirstLimitAmount = 0.0;
					double dSecondLimitAmount = 0.0;
					double dThirdLimitAmount = 0.0;
					double dCapitalLimitAmount = 0.0;

					double dCanUseBalance = rs.getDouble("availableBalance");

					// added by mzh_fu 2008/05/08 与账户余额处的逻辑保持一致
					if (rs.getLong("firstLimitTypeId") > 0) {
						dFirstLimitAmount = rs.getDouble("firstLimitAmount");
					}
					if (rs.getLong("secondLimitTypeId") > 0) {
						dSecondLimitAmount = rs.getDouble("secondLimitAmount");
					}
					if (rs.getLong("thirdLimitTypeId") > 0) {
						dThirdLimitAmount = rs.getDouble("thirdLimitAmount");
					}
					if (rs.getDouble("capitalLimitAmount") > 0) {
						dCapitalLimitAmount = rs
								.getDouble("capitalLimitAmount");
					}

					if (dFirstLimitAmount > 0) {
						dCanUseBalance = dCanUseBalance + dFirstLimitAmount
								- dCapitalLimitAmount;
					} else if (dSecondLimitAmount > 0) {
						dCanUseBalance = dCanUseBalance + dSecondLimitAmount
								- dCapitalLimitAmount;
					} else if (dThirdLimitAmount > 0) {
						dCanUseBalance = dCanUseBalance + dThirdLimitAmount
								- dCapitalLimitAmount;
					} else {
						dCanUseBalance = dCanUseBalance - dCapitalLimitAmount;
					}

					info.setClientID(rs.getLong("ClientID"));
					String url = "/settlement/account/control/c024.jsp";
					String params = "lAccountID=" + rs.getLong("AccountID")
							+ "&strAction=AccountQuery";

					// 存储列数据
					cellList = new ArrayList();

					PagerTools.returnCellList(cellList, rs
							.getString("AccountNo")
							+ "," + url + "," + params);
					PagerTools.returnCellList(cellList, rs
							.getString("ClientCode"));
					PagerTools.returnCellList(cellList, rs
							.getString("AccountName"));
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountType.getName(rs
									.getLong("AccountTypeID")));
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountStatus.getName(rs
									.getLong("MainAccountStatusID")));
					PagerTools.returnCellList(cellList, rs
							.getTimestamp("OpenDate") == null ? "" : DataFormat
							.formatDate(rs.getTimestamp("OpenDate")));
					PagerTools.returnCellList(cellList, CurrencySymbol
							+ (rs.getDouble("balance") != 0.0 ? DataFormat
									.formatDisabledAmount(rs
											.getDouble("balance")
											/ unit) : "0.00"));

					PagerTools
							.returnCellList(
									cellList,
									CurrencySymbol
											+ (dCanUseBalance != 0.0 ? DataFormat
													.formatDisabledAmount(dCanUseBalance
															/ unit)
													: "0.00"));

					if (SETTConstant.AccountType.isCurrentAccountType(rs
							.getLong("AccountTypeID"))
							|| SETTConstant.AccountType
									.isOtherDepositAccountType(rs
											.getLong("AccountTypeID"))) {
						Timestamp tsToday = Env.getSystemDate(info
								.getOfficeID(), info.getCurrencyID());

						PagerTools.returnCellList(cellList, DataFormat
								.formatAmountUseZero(this.getInterestRate(
										tsToday, rs), 6));
					}

					PagerTools.returnCellList(cellList, CurrencySymbol
							+ (rs.getLong("Interest") != 0.0 ? DataFormat
									.formatDisabledAmount(rs
											.getLong("Interest")
											/ unit) : "0.00"));
					PagerTools.returnCellList(cellList, rs
							.getTimestamp("ClearDate") == null ? ""
							: DataFormat.formatDate(rs
									.getTimestamp("ClearDate")));
					PagerTools.returnCellList(cellList, rs
							.getLong("IsNegotiate") > 0 ? "是" : "否");
					PagerTools.returnCellList(cellList, rs
							.getString("sabstract"));

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

	private double getInterestRate(Timestamp tsTerminateInterestRateDate,
			ResultSet rs) throws SQLException {
		if (SETTConstant.AccountType.isCurrentAccountType(rs
				.getLong("AccountTypeID"))
				|| SETTConstant.AccountType.isOtherDepositAccountType(rs
						.getLong("AccountTypeID")))
			return getCurrentInterestRate(tsTerminateInterestRateDate, rs);
		else if (SETTConstant.AccountType.isFixAccountType(rs
				.getLong("AccountTypeID"))
				|| SETTConstant.AccountType.isNotifyAccountType(rs
						.getLong("AccountTypeID")))
			return rs.getDouble("FixInterestRate");
		else
			return rs.getDouble("LoanInterestRate");
	}

	private double getCurrentInterestRate(Timestamp tsTerminateRateDate,
			ResultSet rs) throws SQLException {
		double CurrentInterestRate = 0.0;
		if (NameRef.getInterestRatePlanTypeIDByID(rs.getLong("interestPlanID")) == SETTConstant.InterestRatePlanType.BALANCE_AVERAGE) {
			CurrentInterestRate = BaseQueryObject.getCurrentInterestRate(rs
					.getTimestamp("OpenDate"), tsTerminateRateDate, rs
					.getLong("interestPlanID"), rs.getDouble("Balance"));
		} else {
			CurrentInterestRate = BaseQueryObject.getCurrentInterestRate(rs
					.getTimestamp("OpenDate"), tsTerminateRateDate, rs
					.getLong("interestPlanID"), rs.getDouble("Balance"));
		}
		return CurrentInterestRate;
	}
}

package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.Dao.QDepositLoanDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryOtherDepositInfo;
import com.iss.itreasury.settlement.query.queryobj.QFixedDeposit;
import com.iss.itreasury.settlement.query.queryobj.QOtherDeposit;
import com.iss.itreasury.settlement.query.resultinfo.QueryDepositLoanResultInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryFixedDepositSumInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryOtherDepositSumInfo;
import com.iss.itreasury.settlement.setting.dataentity.DepositLoanSearchSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * 账户查询业务层
 * 
 * @author xiang
 * 
 */
public class QDepositLoanBiz {

	QDepositLoanDao dao = new QDepositLoanDao();
	
	public PagerInfo queryDepositLoan(QueryDepositLoanWhereInfo info, long unit)
			throws Exception {
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				pagerInfo = new PagerInfo();
				sql = dao.getDepositLoanSQL(info);
				pagerInfo.setSqlString(sql);
				Map map = new HashMap();
				map.put("info", info);
				map.put("unit", unit);
				pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
						"resultSetHandle", map);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}

	}

	public PagerInfo queryDepositLoan_Deposit(QueryDepositLoanWhereInfo info,
			long unit) throws Exception {
		{
			PagerInfo pagerInfo = null;
			String sql = null;
			try {
				pagerInfo = new PagerInfo();
				sql = dao.getDepositLoanSQL_Deposit(info);
				pagerInfo.setSqlString(sql);
				Map map = new HashMap();
				map.put("info", info);
				map.put("unit", unit);
				pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
						"resultSetHandle_Deposit", map);

			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
		}

	}

	public PagerInfo queryTransAccountDetail(QueryDepositLoanWhereInfo info,
			long unit) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.getDepositLoanSQL_AccountDetail(info);
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("info", info);
			map.put("unit", unit);
			pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
					"resultSetHandle_AccountDetail", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public PagerInfo queryCosignLoanSumByLoanTypeID(
			QueryDepositLoanWhereInfo info, long unit) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.queryCosignLoanSumByLoanTypeID(info);
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("info", info);
			map.put("unit", unit);
			pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
					"resultSetHandle_CosignLoanSum", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public PagerInfo queryOtherDepositInfo(QueryOtherDepositInfo info, long unit)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.getOtherDepositSQL(info);
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("info", info);
			map.put("unit", unit);

			pagerInfo.setTotalExtensionMothod(QDepositLoanBiz.class,
					"totalResultSetHandle_OtherDeposit", map);

			pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
					"resultSetHandle_OtherDeposit", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public PagerInfo queryFixedDepositInfo(QueryFixedDepositInfo info) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.getFixedDepositSQL(info);
			pagerInfo.setSqlString(sql);
			
			Map<String,QueryFixedDepositInfo> map = new HashMap<String,QueryFixedDepositInfo>();
			map.put("QueryFixedDepositInfo", info);
			
			pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
					"resultSetHandle_FixDeposit", map);
					
			pagerInfo.setTotalExtensionMothod(QDepositLoanBiz.class,
					"totalResultSetHandle_FixDeposit", map);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public PagerInfo queryDepositLoan_FixedNotice(
			QueryDepositLoanWhereInfo info, long unit,String CurrencySymbol) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.getDepositLoan_FixedNoticeSQL(info);
			pagerInfo.setSqlString(sql);
			Map map = new HashMap();
			map.put("info", info);
			map.put("unit", unit);
			map.put("CurrencySymbol", CurrencySymbol);

			pagerInfo.setExtensionMothod(QDepositLoanBiz.class,
					"resultSetHandle_FixedNotice", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}

	public ArrayList resultSetHandle(ResultSet rs, Map param) throws Exception {
		long unit = Long.valueOf(param.get("unit") + "").longValue();
		Connection conn = Database.getConnection();
		PreparedStatement ps = null;
		QueryDepositLoanWhereInfo qdlwi = (QueryDepositLoanWhereInfo) param
				.get("info");
		String CurrencySymbol = (String)param .get("CurrencySymbol");
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		StringBuffer m_sbSQL = new StringBuffer();

		if (rs != null) {
			while (rs.next()) {
				DepositLoanSearchSettingInfo dlsi = new DepositLoanSearchSettingInfo();
				dlsi.setID(rs.getLong("ID"));
				dlsi.setCurrencyID(rs.getLong("nCurrencyID"));
				dlsi.setName(rs.getString("sName"));
				dlsi.setAccountTypeID(rs.getString("sAccountTypeID"));
				m_sbSQL.setLength(0);
				// select
				if (DataFormat.formatDate(
						Env.getSystemDate(qdlwi.getOfficeID(), qdlwi
								.getCurrencyID())).equals(
						DataFormat.formatDate(qdlwi.getDate()))) {
					m_sbSQL
							.append(" select sum(round(sett_subaccount.mbalance,2)) mBalance from Client,sett_account,sett_subaccount \n");
					m_sbSQL
							.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_subaccount.naccountid \n");
					// add by xfma(马现福) 2008-7-9
					// 查找出来的存款结果中，把删除的账户也显示出来了，不对，应该过滤删除账户
					m_sbSQL
							.append(" and sett_account.nstatusid !=0 and sett_subaccount.nstatusid !=0 \n");
					m_sbSQL.append(" and sett_account.nofficeid = "
							+ qdlwi.getOfficeID()
							+ " and sett_account.ncurrencyid = "
							+ qdlwi.getCurrencyID() + "\n");
					if (qdlwi.getEnterpriseTypeID1() > 0) {
						m_sbSQL.append(" and client.nClienttypeID1 = "
								+ qdlwi.getEnterpriseTypeID1() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID2() > 0) {
						m_sbSQL.append(" and client.nClienttypeID2 = "
								+ qdlwi.getEnterpriseTypeID2() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID3() > 0) {
						m_sbSQL.append(" and client.nClienttypeID3 = "
								+ qdlwi.getEnterpriseTypeID3() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID4() > 0) {
						m_sbSQL.append(" and client.nClienttypeID4 = "
								+ qdlwi.getEnterpriseTypeID4() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID5() > 0) {
						m_sbSQL.append(" and client.nClienttypeID5 = "
								+ qdlwi.getEnterpriseTypeID5() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID6() > 0) {
						m_sbSQL.append(" and client.nClienttypeID6 = "
								+ qdlwi.getEnterpriseTypeID6() + " \n");
					}
					m_sbSQL.append(" and sett_account.naccounttypeid in ("
							+ dlsi.getAccountTypeID() + ") \n");
				} else {
					// 去掉了sett_subaccount 表的关联，可以修改了汇总余额数据不对的BUG
					m_sbSQL
							.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance \n");
					m_sbSQL
							.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid  \n");
					// add by xfma(马现福) 2008-7-9
					// 查找出来的存款结果中，把删除的账户也显示出来了，不对，应该过滤删除账户
					m_sbSQL.append(" and sett_account.nstatusid !=0 \n");
					m_sbSQL.append(" and sett_account.nofficeid = "
							+ qdlwi.getOfficeID()
							+ " and sett_account.ncurrencyid = "
							+ qdlwi.getCurrencyID() + " \n");
					if (qdlwi.getEnterpriseTypeID1() > 0) {
						m_sbSQL.append(" and client.nClienttypeID1 = "
								+ qdlwi.getEnterpriseTypeID1() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID2() > 0) {
						m_sbSQL.append(" and client.nClienttypeID2 = "
								+ qdlwi.getEnterpriseTypeID2() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID3() > 0) {
						m_sbSQL.append(" and client.nClienttypeID3 = "
								+ qdlwi.getEnterpriseTypeID3() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID4() > 0) {
						m_sbSQL.append(" and client.nClienttypeID4 = "
								+ qdlwi.getEnterpriseTypeID4() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID5() > 0) {
						m_sbSQL.append(" and client.nClienttypeID5 = "
								+ qdlwi.getEnterpriseTypeID5() + " \n");
					}
					if (qdlwi.getEnterpriseTypeID6() > 0) {
						m_sbSQL.append(" and client.nClienttypeID6 = "
								+ qdlwi.getEnterpriseTypeID6() + " \n");
					}
					m_sbSQL.append(" and sett_account.naccounttypeid in ("
							+ dlsi.getAccountTypeID() + ") \n");
					m_sbSQL
							.append(" and sett_dailyaccountbalance.dtdate = to_date('"
									+ DataFormat.formatDate(qdlwi.getDate())
									+ "','yyyy-mm-dd') \n");
				}
				if (qdlwi.getClientManager() > 0) {
					m_sbSQL.append("  and client.nCustomerManagerUserId = "
							+ qdlwi.getClientManager() + " \n");
				}
				// 上海电气新增扩展属性条件
				if (qdlwi.getExtendAttribute1() != -1
						&& qdlwi.getExtendAttribute1() != 0) {
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = "
							+ qdlwi.getExtendAttribute1() + " \n");
				}
				if (qdlwi.getExtendAttribute2() != -1
						&& qdlwi.getExtendAttribute2() != 0) {
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = "
							+ qdlwi.getExtendAttribute2() + " \n");
				}
				if (qdlwi.getExtendAttribute3() != -1
						&& qdlwi.getExtendAttribute3() != 0) {
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = "
							+ qdlwi.getExtendAttribute3() + " \n");
				}
				if (qdlwi.getExtendAttribute4() != -1
						&& qdlwi.getExtendAttribute4() != 0) {
					m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = "
							+ qdlwi.getExtendAttribute4() + " \n");
				}

				// 后台打印SQL
				System.out.println("存款贷款汇总查询SQL:" + m_sbSQL.toString());
				ps = conn.prepareStatement(m_sbSQL.toString());
				ResultSet rs1 = ps.executeQuery();
				String params = "";
				String strAccountDetailsURL = "";
				while (rs1.next()) {
					QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
					qdlri.setAccountTypeName(rs.getString("sName"));
					qdlri.setSumBalance(rs1.getDouble("mBalance"));
					qdlri.setDepositLoanSearchID(rs.getLong("id"));

					cellList = new ArrayList();

					String strDate = qdlwi.getDate() == null ? "" : DataFormat
							.getDateString(qdlwi.getDate());
					if (qdlri.getAccountTypeName().equals("信托存款")) {
						strAccountDetailsURL = "/settlement/query/control/v103.jsp";
						params = "strDate="
								+ strDate
								+ "&lClientManager="
								+ qdlwi.getClientManager()
								+ "&EnterpriseTypeID1="
								+ qdlwi.getEnterpriseTypeID1()
								+ "&EnterpriseTypeID2="
								+ qdlwi.getEnterpriseTypeID2()
								+ "&EnterpriseTypeID3="
								+ qdlwi.getEnterpriseTypeID3()
								+ "&EnterpriseTypeID4="
								+ qdlwi.getEnterpriseTypeID4()
								+ "&EnterpriseTypeID5="
								+ qdlwi.getEnterpriseTypeID5()
								+ "&EnterpriseTypeID6="
								+ qdlwi.getEnterpriseTypeID6()
								+ "&SelExtendAttribute1="
								+ qdlwi.getExtendAttribute1()
								+ "&SelExtendAttribute2="
								+ qdlwi.getExtendAttribute2()
								+ "&SelExtendAttribute3="
								+ qdlwi.getExtendAttribute3()
								+ "&SelExtendAttribute4="
								+ qdlwi.getExtendAttribute4()
								+ "&DepositLoanSearchID="
								+ qdlri.getDepositLoanSearchID()
								+ "&strSuccessPageURL=/settlement/query/view/v103.jsp&strFailPageURL=/settlement/query/view/v103.jsp&amount="
								+ unit;
					}
					if (qdlri.getAccountTypeName().equals("委托贷款")) {
						strAccountDetailsURL = "/settlement/query/control/c131.jsp";
						params = "strDate=" + strDate + "&lClientManager="
								+ qdlwi.getClientManager()
								+ "&EnterpriseTypeID1="
								+ qdlwi.getEnterpriseTypeID1()
								+ "&EnterpriseTypeID2="
								+ qdlwi.getEnterpriseTypeID2()
								+ "&EnterpriseTypeID3="
								+ qdlwi.getEnterpriseTypeID3()
								+ "&EnterpriseTypeID4="
								+ qdlwi.getEnterpriseTypeID4()
								+ "&EnterpriseTypeID5="
								+ qdlwi.getEnterpriseTypeID5()
								+ "&EnterpriseTypeID6="
								+ qdlwi.getEnterpriseTypeID6()
								+ "&SelExtendAttribute1="
								+ qdlwi.getExtendAttribute1()
								+ "&SelExtendAttribute2="
								+ qdlwi.getExtendAttribute2()
								+ "&SelExtendAttribute3="
								+ qdlwi.getExtendAttribute3()
								+ "&SelExtendAttribute4="
								+ qdlwi.getExtendAttribute4() + "&amount="
								+ unit;
					}
					if (qdlri.getAccountTypeName().equals("自营贷款")) {
						strAccountDetailsURL = "/settlement/query/view/v121.jsp";
						params = "strDate=" + strDate + "&lClientManager="
								+ qdlwi.getClientManager()
								+ "&EnterpriseTypeID1="
								+ qdlwi.getEnterpriseTypeID1()
								+ "&EnterpriseTypeID2="
								+ qdlwi.getEnterpriseTypeID2()
								+ "&EnterpriseTypeID3="
								+ qdlwi.getEnterpriseTypeID3()
								+ "&EnterpriseTypeID4="
								+ qdlwi.getEnterpriseTypeID4()
								+ "&EnterpriseTypeID5="
								+ qdlwi.getEnterpriseTypeID5()
								+ "&EnterpriseTypeID6="
								+ qdlwi.getEnterpriseTypeID6()
								+ "&SelExtendAttribute1="
								+ qdlwi.getExtendAttribute1()
								+ "&SelExtendAttribute2="
								+ qdlwi.getExtendAttribute2()
								+ "&SelExtendAttribute3="
								+ qdlwi.getExtendAttribute3()
								+ "&SelExtendAttribute4="
								+ qdlwi.getExtendAttribute4()
								+ "&DepositLoanSearchID="
								+ qdlri.getDepositLoanSearchID() + "&amount="
								+ unit;
					}

					if (strAccountDetailsURL.length() == 0) {
						strAccountDetailsURL = "/settlement/query/view/v103.jsp";
						params = "strDate="
								+ strDate
								+ "&lClientManager="
								+ qdlwi.getClientManager()
								+ "&EnterpriseTypeID1="
								+ qdlwi.getEnterpriseTypeID1()
								+ "&EnterpriseTypeID2="
								+ qdlwi.getEnterpriseTypeID2()
								+ "&EnterpriseTypeID3="
								+ qdlwi.getEnterpriseTypeID3()
								+ "&EnterpriseTypeID4="
								+ qdlwi.getEnterpriseTypeID4()
								+ "&EnterpriseTypeID5="
								+ qdlwi.getEnterpriseTypeID5()
								+ "&EnterpriseTypeID6="
								+ qdlwi.getEnterpriseTypeID6()
								+ "&SelExtendAttribute1="
								+ qdlwi.getExtendAttribute1()
								+ "&SelExtendAttribute2="
								+ qdlwi.getExtendAttribute2()
								+ "&SelExtendAttribute3="
								+ qdlwi.getExtendAttribute3()
								+ "&SelExtendAttribute4="
								+ qdlwi.getExtendAttribute4()
								+ "&DepositLoanSearchID="
								+ qdlri.getDepositLoanSearchID()
								+ "&strSuccessPageURL=/settlement/query/view/v103.jsp&strFailPageURL=/settlement/query/view/v103.jsp&amount="
								+ unit;
					}

					PagerTools.returnCellList(cellList, qdlri
							.getAccountTypeName()
							+ "," + strAccountDetailsURL + "," + params);
					PagerTools.returnCellList(cellList,
							(qdlri.getSumBalance() > 0 ? DataFormat
									.formatDisabledAmount(qdlri.getSumBalance()
											/ unit) : "0.00"));

					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

					resultList.add(rowInfo);
				}

			}
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return resultList;
	}

	public ArrayList resultSetHandle_AccountDetail(ResultSet rs, Map param)
			throws Exception {
		QueryDepositLoanWhereInfo info = (QueryDepositLoanWhereInfo) param
				.get("info");
		StringBuffer m_sbSQL;
		long unit = Long.valueOf(param.get("unit") + "").longValue();
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		Connection conn = Database.getConnection();
		PreparedStatement ps = null;
		// select
		while (rs.next()) {
			m_sbSQL = new StringBuffer("");
			cellList = new ArrayList();
			if (DataFormat
					.formatDate(
							Env.getSystemDate(info.getOfficeID(), info
									.getCurrencyID())).equals(
							DataFormat.formatDate(info.getDate()))) {
				m_sbSQL
						.append(" select sum(round(sett_subaccount.mbalance,2)) mBalance from Client,sett_account,sett_subaccount \n");
				m_sbSQL
						.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_subaccount.naccountid \n");
				m_sbSQL.append(" and sett_account.nofficeid = "
						+ info.getOfficeID()
						+ " and sett_account.ncurrencyid = "
						+ info.getCurrencyID() + "\n");
				if (info.getEnterpriseTypeID1() > 0) {
					m_sbSQL.append(" and client.nclienttypeid1 = "
							+ info.getEnterpriseTypeID1() + " \n");
				}
				if (info.getEnterpriseTypeID2() > 0) {
					m_sbSQL.append(" and client.nclienttypeid2 = "
							+ info.getEnterpriseTypeID2() + " \n");
				}
				if (info.getEnterpriseTypeID3() > 0) {
					m_sbSQL.append(" and client.nclienttypeid3 = "
							+ info.getEnterpriseTypeID3() + " \n");
				}
				if (info.getEnterpriseTypeID4() > 0) {
					m_sbSQL.append(" and client.nclienttypeid4 = "
							+ info.getEnterpriseTypeID4() + " \n");
				}
				if (info.getEnterpriseTypeID5() > 0) {
					m_sbSQL.append(" and client.nclienttypeid5 = "
							+ info.getEnterpriseTypeID5() + " \n");
				}
				if (info.getEnterpriseTypeID6() > 0) {
					m_sbSQL.append(" and client.nclienttypeid6 = "
							+ info.getEnterpriseTypeID6() + " \n");
				}
				if (info.getClientManager() > 0) {
					m_sbSQL.append(" and client.ncustomermanageruserid = "
							+ info.getClientManager() + " \n");
				}
				m_sbSQL.append(" and sett_account.naccounttypeid in ("
						+ rs.getLong("ID") + ")  \n");
			} else {
				m_sbSQL
						.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance \n");
				m_sbSQL
						.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid \n");
				m_sbSQL.append(" and sett_account.nofficeid = "
						+ info.getOfficeID()
						+ " and sett_account.ncurrencyid = "
						+ info.getCurrencyID() + " \n");
				if (info.getEnterpriseTypeID1() > 0) {
					m_sbSQL.append(" and client.nclienttypeid1 = "
							+ info.getEnterpriseTypeID1() + " \n");
				}
				if (info.getEnterpriseTypeID2() > 0) {
					m_sbSQL.append(" and client.nclienttypeid2 = "
							+ info.getEnterpriseTypeID2() + " \n");
				}
				if (info.getEnterpriseTypeID3() > 0) {
					m_sbSQL.append(" and client.nclienttypeid3 = "
							+ info.getEnterpriseTypeID3() + " \n");
				}
				if (info.getEnterpriseTypeID4() > 0) {
					m_sbSQL.append(" and client.nclienttypeid4 = "
							+ info.getEnterpriseTypeID4() + " \n");
				}
				if (info.getEnterpriseTypeID5() > 0) {
					m_sbSQL.append(" and client.nclienttypeid5 = "
							+ info.getEnterpriseTypeID5() + " \n");
				}
				if (info.getEnterpriseTypeID6() > 0) {
					m_sbSQL.append(" and client.nclienttypeid6 = "
							+ info.getEnterpriseTypeID6() + " \n");
				}
				if (info.getClientManager() > 0) {
					m_sbSQL.append(" and client.ncustomermanageruserid = "
							+ info.getClientManager() + " \n");
				}
				m_sbSQL.append(" and sett_account.naccounttypeid in ("
						+ rs.getLong("ID") + ")  \n");
				m_sbSQL
						.append(" and sett_dailyaccountbalance.dtdate = to_date('"
								+ DataFormat.formatDate(info.getDate())
								+ "','yyyy-mm-dd') \n");
			}
			// 上海电气新增扩展属性条件
			if (info.getExtendAttribute1() != -1
					&& info.getExtendAttribute1() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = "
						+ info.getExtendAttribute1() + " \n");
			}
			if (info.getExtendAttribute2() != -1
					&& info.getExtendAttribute2() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = "
						+ info.getExtendAttribute2() + " \n");
			}
			if (info.getExtendAttribute3() != -1
					&& info.getExtendAttribute3() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = "
						+ info.getExtendAttribute3() + " \n");
			}
			if (info.getExtendAttribute4() != -1
					&& info.getExtendAttribute4() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = "
						+ info.getExtendAttribute4() + " \n");
			}
			ps = conn.prepareStatement(m_sbSQL.toString());
			ResultSet rs_1 = ps.executeQuery();

			while (rs_1.next()) {
				String url = "";
				String params = "";
				String url_1 = "";
				String params_1 = "";
				QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
				qdlri.setAccountTypeName(SETTConstant.AccountType.getName(rs
						.getLong("ID")));
				qdlri.setAccountTypeID(rs.getLong("ID"));
				qdlri.setSumBalance(rs_1.getDouble("mBalance"));

				if (qdlri.getAccountTypeName().equals("自营贷款")) {
					url = "/settlement/query/view/v027.jsp";
					params = "strDate="
							+ DataFormat.getDateString(info.getDate())
							+ "&lClientManager="
							+ info.getClientManager()
							+ "&EnterpriseTypeID1="
							+ info.getEnterpriseTypeID1()
							+ "&EnterpriseTypeID2="
							+ info.getEnterpriseTypeID2()
							+ "&EnterpriseTypeID3="
							+ info.getEnterpriseTypeID3()
							+ "&EnterpriseTypeID4="
							+ info.getEnterpriseTypeID4()
							+ "&EnterpriseTypeID5="
							+ info.getEnterpriseTypeID5()
							+ "&EnterpriseTypeID6="
							+ info.getEnterpriseTypeID6()
							+ "&SelExtendAttribute1="
							+ info.getExtendAttribute1()
							+ "&SelExtendAttribute2="
							+ info.getExtendAttribute2()
							+ "&SelExtendAttribute3="
							+ info.getExtendAttribute3()
							+ "&SelExtendAttribute4="
							+ info.getExtendAttribute4()
							+ "&AccountTypeID="
							+ qdlri.getAccountTypeID()
							+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v027.jsp&strFailPageURL=/settlement/query/view/v027.jsp&amount="
							+ unit;
				}
				if (qdlri.getAccountTypeName().equals("离岸信托贷款")) {
					url = "/settlement/account/control/c061.jsp";
					params = "DtFinish="
							+ DataFormat.getDateString(info.getDate())
							+ "&strAction=LinkQuery"
							+ "&EnterpriseTypeID1="
							+ info.getEnterpriseTypeID1()
							+ "&EnterpriseTypeID2="
							+ info.getEnterpriseTypeID2()
							+ "&EnterpriseTypeID3="
							+ info.getEnterpriseTypeID3()
							+ "&EnterpriseTypeID4="
							+ info.getEnterpriseTypeID4()
							+ "&EnterpriseTypeID5="
							+ info.getEnterpriseTypeID5()
							+ "&EnterpriseTypeID6="
							+ info.getEnterpriseTypeID6()
							+ "&SelExtendAttribute1="
							+ info.getExtendAttribute1()
							+ "&SelExtendAttribute2="
							+ info.getExtendAttribute2()
							+ "&SelExtendAttribute3="
							+ info.getExtendAttribute3()
							+ "&SelExtendAttribute4="
							+ info.getExtendAttribute4()
							+ "&lAccountTypeID="
							+ qdlri.getAccountTypeID()
							+ "&strSuccessPageURL=/settlement/account/view/v061-1.jsp"
							+ "&strFailPageURL=/settlement/account/view/v061.jsp"
							+ "&amount=" + unit;
				}
				url_1 = "/settlement/query/view/v027.jsp";
				params_1 = "strDate="
						+ DataFormat.getDateString(info.getDate())
						+ "&lClientType="
						+ info.getEnterpriseTypeID()
						+ "&SelExtendAttribute1="
						+ info.getExtendAttribute1()
						+ "&SelExtendAttribute2="
						+ info.getExtendAttribute2()
						+ "&SelExtendAttribute3="
						+ info.getExtendAttribute3()
						+ "&SelExtendAttribute4="
						+ info.getExtendAttribute4()
						+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v027.jsp&strFailPageURL=/settlement/query/view/v027.jsp"
						+ "&AccountTypeID=" + qdlri.getAccountTypeID()
						+ "&amount=" + unit;

				if (qdlri.getAccountTypeName().equals("离岸信托贷款"))
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountType.getName(rs.getLong("ID"))
									+ "," + url + "," + params);
				else if (qdlri.getAccountTypeName().equals("自营贷款"))
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountType.getName(rs.getLong("ID"))
									+ "," + url + "," + params);
				else
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountType.getName(rs.getLong("ID"))
									+ "," + url_1 + "," + params_1);
				PagerTools.returnCellList(cellList,
						(qdlri.getSumBalance() > 0 ? DataFormat
								.formatDisabledAmount(qdlri.getSumBalance()
										/ unit) : "0.00"));

				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

				resultList.add(rowInfo);
			}
		}
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return resultList;
	}

	public ArrayList resultSetHandle_Deposit(ResultSet rs, Map param)
			throws Exception {
		long unit = Long.valueOf(param.get("unit") + "").longValue();
		Connection conn = Database.getConnection();
		PreparedStatement ps = null;
		QueryDepositLoanWhereInfo qdlwi = (QueryDepositLoanWhereInfo) param
				.get("info");
		ResultSet rs_1 = null;
		ArrayList resultList = new ArrayList(); // 最终返回结果
		ArrayList cellList = null;// 列
		ResultPagerRowInfo rowInfo = null;// 行
		StringBuffer m_sbSQL = new StringBuffer();
		String params = "";
		String params_1 = "";
		String url = "";
		String url_1 = "";

		while (rs.next()) {
			m_sbSQL.setLength(0);
			// select
			if (DataFormat.formatDate(
					Env.getSystemDate(qdlwi.getOfficeID(), qdlwi
							.getCurrencyID())).equals(
					DataFormat.formatDate(qdlwi.getDate()))) {
				m_sbSQL
						.append(" select sum(round(sett_subaccount.mbalance,2)) mBalance from Client,sett_account,sett_subaccount \n");
				m_sbSQL
						.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_subaccount.naccountid \n");
				m_sbSQL.append(" and sett_account.nofficeid = "
						+ qdlwi.getOfficeID()
						+ " and sett_account.ncurrencyid = "
						+ qdlwi.getCurrencyID() + "\n");
				if (qdlwi.getEnterpriseTypeID1() > 0) {
					m_sbSQL.append(" and client.nclienttypeid1 = "
							+ qdlwi.getEnterpriseTypeID1() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID2() > 0) {
					m_sbSQL.append(" and client.nclienttypeid2 = "
							+ qdlwi.getEnterpriseTypeID2() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID3() > 0) {
					m_sbSQL.append(" and client.nclienttypeid3 = "
							+ qdlwi.getEnterpriseTypeID3() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID4() > 0) {
					m_sbSQL.append(" and client.nclienttypeid4 = "
							+ qdlwi.getEnterpriseTypeID4() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID5() > 0) {
					m_sbSQL.append(" and client.nclienttypeid5 = "
							+ qdlwi.getEnterpriseTypeID5() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID6() > 0) {
					m_sbSQL.append(" and client.nclienttypeid6 = "
							+ qdlwi.getEnterpriseTypeID6() + " \n");
				}
				if (qdlwi.getClientManager() > 0) {
					m_sbSQL.append(" and client.ncustomermanageruserid = "
							+ qdlwi.getClientManager() + " \n");
				}
				m_sbSQL.append(" and sett_account.naccounttypeid in ("
						+ rs.getLong("ID") + ") \n");
			} else {
				m_sbSQL
						.append(" select sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance from Client,sett_account,sett_dailyaccountbalance \n");
				m_sbSQL
						.append(" where Client.id = sett_account.nclientid and sett_account.id = sett_dailyaccountbalance.naccountid \n");
				m_sbSQL.append(" and sett_account.nofficeid = "
						+ qdlwi.getOfficeID()
						+ " and sett_account.ncurrencyid = "
						+ qdlwi.getCurrencyID() + " \n");
				if (qdlwi.getEnterpriseTypeID1() > 0) {
					m_sbSQL.append(" and client.nclienttypeid1 = "
							+ qdlwi.getEnterpriseTypeID1() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID2() > 0) {
					m_sbSQL.append(" and client.nclienttypeid2 = "
							+ qdlwi.getEnterpriseTypeID2() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID3() > 0) {
					m_sbSQL.append(" and client.nclienttypeid3 = "
							+ qdlwi.getEnterpriseTypeID3() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID4() > 0) {
					m_sbSQL.append(" and client.nclienttypeid4 = "
							+ qdlwi.getEnterpriseTypeID4() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID5() > 0) {
					m_sbSQL.append(" and client.nclienttypeid5 = "
							+ qdlwi.getEnterpriseTypeID5() + " \n");
				}
				if (qdlwi.getEnterpriseTypeID6() > 0) {
					m_sbSQL.append(" and client.nclienttypeid6 = "
							+ qdlwi.getEnterpriseTypeID6() + " \n");
				}
				if (qdlwi.getClientManager() > 0) {
					m_sbSQL.append(" and client.ncustomermanageruserid = "
							+ qdlwi.getClientManager() + " \n");
				}
				m_sbSQL.append(" and sett_account.naccounttypeid in ("
						+ rs.getLong("ID") + ") \n");
				m_sbSQL
						.append(" and sett_dailyaccountbalance.dtdate = to_date('"
								+ DataFormat.formatDate(qdlwi.getDate())
								+ "','yyyy-mm-dd') \n");
			}
			// 上海电气新增扩展属性条件
			if (qdlwi.getExtendAttribute1() != -1
					&& qdlwi.getExtendAttribute1() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE1 = "
						+ qdlwi.getExtendAttribute1() + " \n");
			}
			if (qdlwi.getExtendAttribute2() != -1
					&& qdlwi.getExtendAttribute2() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE2 = "
						+ qdlwi.getExtendAttribute2() + " \n");
			}
			if (qdlwi.getExtendAttribute3() != -1
					&& qdlwi.getExtendAttribute3() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE3 = "
						+ qdlwi.getExtendAttribute3() + " \n");
			}
			if (qdlwi.getExtendAttribute4() != -1
					&& qdlwi.getExtendAttribute4() != 0) {
				m_sbSQL.append(" and client.NEXTENDATTRIBUTE4 = "
						+ qdlwi.getExtendAttribute4() + " \n");
			}

			ps = conn.prepareStatement(m_sbSQL.toString());
			rs_1 = ps.executeQuery();

			while (rs_1.next()) {
				cellList = new ArrayList();
				QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
				qdlri.setAccountTypeID(rs.getLong("ID"));
				qdlri.setAccountTypeName(SETTConstant.AccountType.getName(rs
						.getLong("ID")));
				qdlri.setSumBalance(rs_1.getDouble("mBalance"));
				qdlri.setDepositLoanSearchID(qdlwi.getDepositLoanSearchID());

				url = "/settlement/query/view/v104.jsp";
				params = "strDate="
						+ DataFormat.getDateString(qdlwi.getDate())
						+ "&lClientManager="
						+ qdlwi.getClientManager()
						+ "&EnterpriseTypeID1="
						+ qdlwi.getEnterpriseTypeID1()
						+ "&EnterpriseTypeID2="
						+ qdlwi.getEnterpriseTypeID2()
						+ "&EnterpriseTypeID3="
						+ qdlwi.getEnterpriseTypeID3()
						+ "&EnterpriseTypeID4="
						+ qdlwi.getEnterpriseTypeID4()
						+ "&EnterpriseTypeID5="
						+ qdlwi.getEnterpriseTypeID5()
						+ "&EnterpriseTypeID6="
						+ qdlwi.getEnterpriseTypeID6()
						+ "&SelExtendAttribute1="
						+ qdlwi.getExtendAttribute1()
						+ "&SelExtendAttribute2="
						+ qdlwi.getExtendAttribute2()
						+ "&SelExtendAttribute3="
						+ qdlwi.getExtendAttribute3()
						+ "&SelExtendAttribute4="
						+ qdlwi.getExtendAttribute4()
						+ "&DepositAccountTypeID="
						+ qdlri.getAccountTypeID()
						+ "&DepositLoanSearchID="
						+ qdlri.getDepositLoanSearchID()
						+ "&strSuccessPageURL=/settlement/query/view/v104.jsp&strFailPageURL=/settlement/query/view/v104.jsp&amount="
						+ unit;

				url_1 = "/settlement/query/view/v027.jsp";
				params_1 = "strDate="
						+ DataFormat.getDateString(qdlwi.getDate())
						+ "&lClientManager="
						+ qdlwi.getClientManager()
						+ "&EnterpriseTypeID1="
						+ qdlwi.getEnterpriseTypeID1()
						+ "&EnterpriseTypeID2="
						+ qdlwi.getEnterpriseTypeID2()
						+ "&EnterpriseTypeID3="
						+ qdlwi.getEnterpriseTypeID3()
						+ "&EnterpriseTypeID4="
						+ qdlwi.getEnterpriseTypeID4()
						+ "&EnterpriseTypeID5="
						+ qdlwi.getEnterpriseTypeID5()
						+ "&EnterpriseTypeID6="
						+ qdlwi.getEnterpriseTypeID6()
						+ "&SelExtendAttribute1="
						+ qdlwi.getExtendAttribute1()
						+ "&SelExtendAttribute2="
						+ qdlwi.getExtendAttribute2()
						+ "&SelExtendAttribute3="
						+ qdlwi.getExtendAttribute3()
						+ "&SelExtendAttribute4="
						+ qdlwi.getExtendAttribute4()
						+ "&AccountTypeID="
						+ qdlri.getAccountTypeID()
						+ "&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v027.jsp&strFailPageURL=/settlement/query/view/v027.jsp&amount="
						+ unit;
				//
				if (SETTConstant.AccountType.isFixAccountType(qdlri
						.getAccountTypeID())
						|| SETTConstant.AccountType.isNotifyAccountType(qdlri
								.getAccountTypeID()))
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountType.getName(rs.getLong("ID"))
									+ "," + url + "," + params);
				else
					PagerTools.returnCellList(cellList,
							SETTConstant.AccountType.getName(rs.getLong("ID"))
									+ "," + url_1 + "," + params_1);
				PagerTools.returnCellList(cellList,
						(rs_1.getDouble("mBalance") > 0 ? DataFormat
								.formatDisabledAmount(rs_1
										.getDouble("mBalance")
										/ unit) : "0.00"));

				rowInfo = new ResultPagerRowInfo();
				rowInfo.setCell(cellList);
				rowInfo.setId(String.valueOf(rs.getLong("rownum1")));

				resultList.add(rowInfo);
			}
		}
		cleanup(rs);
		cleanup(rs_1);
		cleanup(ps);
		cleanup(conn);
		return resultList;
	}

	public ArrayList resultSetHandle_CosignLoanSum(ResultSet rs, Map param)
			throws SQLException {
		long unit = Long.valueOf(param.get("unit") + "").longValue();
		QueryDepositLoanWhereInfo qdlwi = (QueryDepositLoanWhereInfo) param
				.get("info");
		String url = "";
		String params = "";
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			ArrayList cellList = new ArrayList();
			url = "/settlement/query/control/c132.jsp";

			params = "trDate=" + DataFormat.getDateString(qdlwi.getDate())
					+ "&lClientManager=" + qdlwi.getClientManager()
					+ "&EnterpriseTypeID1=" + qdlwi.getEnterpriseTypeID1()
					+ "&EnterpriseTypeID2=" + qdlwi.getEnterpriseTypeID2()
					+ "&EnterpriseTypeID3=" + qdlwi.getEnterpriseTypeID3()
					+ "&EnterpriseTypeID4=" + qdlwi.getEnterpriseTypeID4()
					+ "&EnterpriseTypeID5=" + qdlwi.getEnterpriseTypeID5()
					+ "&EnterpriseTypeID6=" + qdlwi.getEnterpriseTypeID6()
					+ "&SelExtendAttribute1=" + qdlwi.getExtendAttribute1()
					+ "&SelExtendAttribute2=" + qdlwi.getExtendAttribute2()
					+ "&SelExtendAttribute3=" + qdlwi.getExtendAttribute3()
					+ "&SelExtendAttribute4=" + qdlwi.getExtendAttribute4()
					+ "&LoanTypeID=" + rs.getLong("loantypeid");

			PagerTools.returnCellList(cellList, LOANConstant.LoanType
					.getName(rs.getLong("loantypeid"))
					+ "," + url + "," + params);

			PagerTools.returnCellList(cellList,
					(rs.getDouble("Balance") > 0 ? DataFormat
							.formatDisabledAmount(rs.getDouble("Balance")
									/ unit) : "0.00"));

			ResultPagerRowInfo rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			resultList.add(rowInfo);
		}
		return resultList;
	}

	public ArrayList resultSetHandle_OtherDeposit(ResultSet rs, Map param)
			throws SQLException {
		long unit = Long.valueOf((param.get("unit") + "")).longValue();
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			ArrayList cellList = new ArrayList();

			PagerTools.returnCellList(cellList, DataFormat.formatEmptyString(rs
					.getString("AccountNo")));
			PagerTools.returnCellList(cellList, DataFormat.formatEmptyString(rs
					.getString("ClientName")));
			PagerTools.returnCellList(cellList, DataFormat
					.formatDisabledAmount(rs.getDouble("Amount") / unit));
			PagerTools.returnCellList(cellList, DataFormat
					.formatDisabledAmount(rs.getDouble("Balance") / unit));
			PagerTools.returnCellList(cellList, SETTConstant.SubAccountStatus
					.getName(rs.getLong("subAccountStatusID")));
			PagerTools.returnCellList(cellList, DataFormat
					.formatDisabledAmount(rs.getDouble("Interest") / unit));

			ResultPagerRowInfo rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			resultList.add(rowInfo);
		}
		return resultList;

	}

	public ArrayList resultSetHandle_FixedNotice(ResultSet rs, Map param)
			throws Exception {
		long unit = Long.valueOf((param.get("unit") + "")).longValue();
		ArrayList resultList = new ArrayList();
		QueryDepositLoanWhereInfo info = (QueryDepositLoanWhereInfo) param
				.get("info");
		while (rs.next()) {
			QueryDepositLoanResultInfo qdlri = new QueryDepositLoanResultInfo();
			qdlri.setAccountTypeID(info.getDepositAccountTypeID());
			qdlri.setFixedTerm(rs.getLong("nfixeddepositmonthid"));
			if (qdlri.getFixedTerm() > 10000) {
				qdlri.setFixedTermName(qdlri.getFixedTerm() - 10000 + "天");
			} else {
				qdlri.setFixedTermName(qdlri.getFixedTerm() + "个月");
			}
			qdlri.setAccountTypeName(SETTConstant.AccountType.getName(info
					.getDepositAccountTypeID())
					+ "-" + qdlri.getFixedTermName());
			qdlri.setSumBalance(rs.getDouble("mBalance"));
			qdlri.setDepositLoanSearchID(info.getDepositLoanSearchID());

			String url = "";
			String params = "";
			if (SETTConstant.AccountType.isFixAccountType(qdlri
					.getAccountTypeID())) {
				url = "/settlement/query/view/v022.jsp";
				params = "strFixedEndDate="
						+ DataFormat.getDateString(info.getDate())
						+ "&lFixedDepositTermFrom="
						+ qdlri.getFixedTerm()
						+ "&lFixedDepositTermTo="
						+ qdlri.getFixedTerm()
						+ "&lClientType="
						+ info.getEnterpriseTypeID()
						+ "&lAccountType="
						+ qdlri.getAccountTypeID()
						+ "&lClientManager="
						+ info.getClientManager()
						+ "&ControlSource="
						+ String.valueOf(SETTConstant.AccountGroupType.FIXED)
						+ "&EnterpriseTypeID1="
						+ info.getEnterpriseTypeID1()
						+ "&EnterpriseTypeID2="
						+ info.getEnterpriseTypeID2()
						+ "&EnterpriseTypeID3="
						+ info.getEnterpriseTypeID3()
						+ "&EnterpriseTypeID4="
						+ info.getEnterpriseTypeID4()
						+ "&EnterpriseTypeID5="
						+ info.getEnterpriseTypeID5()
						+ "&EnterpriseTypeID6="
						+ info.getEnterpriseTypeID6()
						+ "&SelExtendAttribute1="
						+ info.getExtendAttribute1()
						+ "&SelExtendAttribute2="
						+ info.getExtendAttribute2()
						+ "&SelExtendAttribute3="
						+ info.getExtendAttribute3()
						+ "&SelExtendAttribute4="
						+ info.getExtendAttribute4()
						+ "&lIsFixedDeposit=1&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v022.jsp&strFailPageURL=/settlement/query/view/v104.jsp&amount="
						+ unit;
			}
			if (SETTConstant.AccountType.isNotifyAccountType(qdlri
					.getAccountTypeID())) {
				url = "/settlement/query/view/v022.jsp";
				params = "strNoticeEndDate="
						+ DataFormat.getDateString(info.getDate())
						+ "&lClientType="
						+ info.getEnterpriseTypeID()
						+ "&lAccountType="
						+ qdlri.getAccountTypeID()
						+ "&lClientManager="
						+ info.getClientManager()
						+ "&ControlSource="
						+ String.valueOf(SETTConstant.AccountGroupType.NOTIFY)
						+ "&EnterpriseTypeID1="
						+ info.getEnterpriseTypeID1()
						+ "&EnterpriseTypeID2="
						+ info.getEnterpriseTypeID2()
						+ "&EnterpriseTypeID3="
						+ info.getEnterpriseTypeID3()
						+ "&EnterpriseTypeID4="
						+ info.getEnterpriseTypeID4()
						+ "&EnterpriseTypeID5="
						+ info.getEnterpriseTypeID5()
						+ "&EnterpriseTypeID6="
						+ info.getEnterpriseTypeID6()
						+ "&SelExtendAttribute1="
						+ info.getExtendAttribute1()
						+ "&SelExtendAttribute2="
						+ info.getExtendAttribute2()
						+ "&SelExtendAttribute3="
						+ info.getExtendAttribute3()
						+ "&SelExtendAttribute4="
						+ info.getExtendAttribute4()
						+ "&lNoticeDays="
						+ qdlri.getFixedTerm()
						+ "&lIsNoticeDeposit=1&strAction=LinkQuery&strSuccessPageURL=/settlement/query/view/v022.jsp&strFailPageURL=/settlement/query/view/v104.jsp&amount="
						+ unit;
			}

			ArrayList cellList = new ArrayList();

			PagerTools.returnCellList(cellList, qdlri.getAccountTypeName()
					+ "," + url + "," + params);
			PagerTools.returnCellList(cellList,
					(qdlri.getSumBalance() > 0 ? DataFormat
							.formatDisabledAmount(qdlri.getSumBalance() / unit)
							: "0.00"));

			ResultPagerRowInfo rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			resultList.add(rowInfo);
		}
		return resultList;

	}

	public ArrayList resultSetHandle_FixDeposit(ResultSet rs, Map<String,QueryFixedDepositInfo> param)
			throws Exception {
		
		QueryFixedDepositInfo info = param.get("QueryFixedDepositInfo");
		long unit = info.getUnit();
		String ControlSource = info.getControlSource();
		ArrayList resultList = new ArrayList();
		
		while (rs.next()) {
			String url = "";
			String params = "";
			String strAccountNo = DataFormat.formatString(rs
					.getString("AccountNo"));
			String strClientName = DataFormat.formatString(rs
					.getString("ClientName"));
			String strDepositNo = DataFormat.formatString(rs
					.getString("DepositNo"));
			String strDepositBillNo = DataFormat.formatString(rs
					.getString("DepositBillNo"));
			String strAmount = DataFormat.formatDisabledAmount(rs
					.getDouble("Amount")
					/ unit, 2);
			String strBalance = DataFormat.formatDisabledAmount(rs
					.getDouble("Balance")
					/ unit, 2);
			String strStatus = DataFormat
					.formatString(SETTConstant.SubAccountStatus.getName(rs
							.getLong("subAccountStatusID")));
			// 利率小数点后显示6位
			String strRate = DataFormat.formatAmountUseZero(rs
					.getDouble("Rate"), 6)
					+ "%";
			String strPreDrawInterest = DataFormat.formatDisabledAmount(rs
					.getDouble("PreDrawInterest")
					/ unit, 2);
			String strStartDate = DataFormat.getDateString(rs
					.getTimestamp("StartDate"));
			String strEndDate = DataFormat.getDateString(rs
					.getTimestamp("EndDate"));
			long DepositTerm = rs.getLong("depositterm");// 定期存款的期限，如果是通知，则改为存款品种，见下面的strDepositterm
			// add by ylguo(郭英亮)通知存款不显示期限，而是显示存款品种
			String strDepositterm = "";// 通知存款的品种
			if (ControlSource.equals(String
					.valueOf(SETTConstant.AccountGroupType.NOTIFY))) {
				if (rs.getLong("NoticeDay") <= 0)
					strDepositterm = "&nbsp;";
				else {
					if (rs.getLong("NoticeDay") > 10000) {
						strDepositterm = String
								.valueOf(rs.getLong("NoticeDay") - 10000)
								+ "天";
					} else {
						strDepositterm = String
								.valueOf(rs.getLong("NoticeDay"))
								+ "个月";
					}
				}

			}

			if (SETTConstant.AccountType.isFixAccountType(rs.getLong("AccountTypeID"))) {
				url = "/settlement/query/control/c031.jsp";
				params = "ID="
						+ rs.getLong("SubAccountID")
						+ "&strSuccessPageURL=../view/v031.jsp&strFailPageURL=../view/v022.jsp";
			} else if (SETTConstant.AccountType.isMarginAccountType(rs.getLong("AccountTypeID"))) {
				url = "/settlement/query/control/c031.jsp";
				params = "ID="
						+ rs.getLong("SubAccountID")
						+ "&strSuccessPageURL=../view/v037.jsp&strFailPageURL=../view/v022.jsp";
			} else {
				url = "/settlement/query/control/c031.jsp";
				params = "ID="
						+ rs.getLong("SubAccountID")
						+ "&strSuccessPageURL=../view/v032.jsp&strFailPageURL=../view/v022.jsp";
			}

			ArrayList cellList = new ArrayList();

	
	
			PagerTools.returnCellList(cellList, strAccountNo
					+ "," + url + "," + params);
			PagerTools.returnCellList(cellList,strClientName);
			
			PagerTools.returnCellList(cellList,strDepositNo);
			PagerTools.returnCellList(cellList,strDepositBillNo);
			PagerTools.returnCellList(cellList,strStartDate);
			if(!ControlSource.equals(String.valueOf(SETTConstant.AccountGroupType.NOTIFY)))
		 	{
				PagerTools.returnCellList(cellList,strEndDate);
				PagerTools.returnCellList(cellList,DepositTerm);
		 	}
			//如果是通知存款，则将期限显示为存款品种
		 	if(ControlSource.equals(String.valueOf(SETTConstant.AccountGroupType.NOTIFY)))
		 	{
		 		PagerTools.returnCellList(cellList,strDepositterm);
		 	}
			PagerTools.returnCellList(cellList,strAmount);
			PagerTools.returnCellList(cellList,strBalance);
			PagerTools.returnCellList(cellList,strStatus);
			
			PagerTools.returnCellList(cellList,strRate);
			PagerTools.returnCellList(cellList,strPreDrawInterest);

			ResultPagerRowInfo rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
			resultList.add(rowInfo);
		}
		return resultList;
	}

	public ArrayList totalResultSetHandle_OtherDeposit(ResultSet rs, Map param)
			throws Exception {
		QueryOtherDepositInfo info = (QueryOtherDepositInfo) param.get("info");
		long unit = Long.valueOf((param.get("unit") + ""));
		QOtherDeposit qobj = new QOtherDeposit();
		QueryOtherDepositSumInfo qasi = qobj.queryOtherDepositSum(info);

		String strAmountSum = DataFormat.formatListAmount(qasi
				.getDepositAmountSum()
				/ unit);
		String strBalanceSum = DataFormat.formatListAmount(qasi
				.getDepositBalanceSum()
				/ unit);
		String strInterestSum = DataFormat.formatListAmount(qasi
				.getDepositInterestSum()
				/ unit);
		ArrayList list = new ArrayList();
		list.add("金额合计{" + strAmountSum + "}");
		list.add("余额合计{" + strBalanceSum + "}");
		list.add("利息合计{" + strInterestSum + "}");
		return list;
	}

	public ArrayList totalResultSetHandle_FixDeposit(ResultSet rs,
			Map<String,QueryFixedDepositInfo> param) throws Exception {
		
		QueryFixedDepositInfo info = param.get("QueryFixedDepositInfo");
		
		long unit = info.getUnit();
		QFixedDeposit qobj = new QFixedDeposit();
		QueryFixedDepositSumInfo qasi = qobj.queryFixedDepositSum(info);

		String strAmountSum = DataFormat.formatListAmount(qasi
				.getDepositAmountSum()
				/ unit);
		String strBalanceSum = DataFormat.formatListAmount(qasi
				.getDepositBalanceSum()
				/ unit);
		String strInterestSum = DataFormat.formatListAmount(qasi
				.getDepositInterestSum()
				/ unit);
		ArrayList list = new ArrayList();
		list.add("金额合计{" + strAmountSum + "}");
		list.add("余额合计{" + strBalanceSum + "}");
		list.add("利息合计{" + strInterestSum + "}");
		return list;
	}

	private void cleanup(ResultSet rs) throws SQLException {
		try {
			// Log.print("进入关闭RS方法");
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
			Log.print(sqle.toString());
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			// Log.print("进入关闭PS方法");
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
			Log.print(sqle.toString());
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			// Log.print("进入关闭连接方法");
			/**
			 * transConn 不为空表明这个数据库连接相关的事务不是容器维护的，因此不在此处关闭 即 Assert(con ==
			 * transConn)
			 */
			// Log.print("con ="+con);
			// Log.print("transConn ="+transConn);
			if (con != null && con.isClosed() == false) {
				// Log.print("关闭连接--开始");
				con.close();
				con = null;
				// Log.print("关闭连接--结束");
			}
		} catch (SQLException sqle) {
			Log.print(sqle.toString());
		}
	}

}

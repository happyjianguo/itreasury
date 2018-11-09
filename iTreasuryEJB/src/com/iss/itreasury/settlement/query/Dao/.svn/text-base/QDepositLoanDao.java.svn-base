package com.iss.itreasury.settlement.query.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.transaction.SystemException;

import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.query.paraminfo.QueryDepositLoanWhereInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryFixedDepositInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryOtherDepositInfo;
import com.iss.itreasury.settlement.setting.dataentity.DepositLoanSearchSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

/**
 * 账户查询数据层
 * 
 * @author xiang
 * 
 */
public class QDepositLoanDao {

	public String getDepositLoanSQL(QueryDepositLoanWhereInfo info)
			throws Exception {
		String strSQL = " select * from sett_DEPOSITLOANSEARCHSETTING where  nCurrencyID="
				+ info.getCurrencyID()
				+ " and nOfficeID="
				+ info.getOfficeID()
				+ " order by ID";
		return strSQL.toString();
	}

	public String getDepositLoanSQL_Deposit(QueryDepositLoanWhereInfo info)
			throws Exception {
		Connection conn = Database.getConnection();
		String strSQL = " select * from sett_DEPOSITLOANSEARCHSETTING where ID="
				+ info.getDepositLoanSearchID();
		PreparedStatement ps = null;
		ps = conn.prepareStatement(strSQL);
		ResultSet rs = ps.executeQuery();
		DepositLoanSearchSettingInfo returnInfo = new DepositLoanSearchSettingInfo();

		while (rs.next()) {
			returnInfo.setID(rs.getLong("ID"));
			returnInfo.setCurrencyID(rs.getLong("nCurrencyID"));
			returnInfo.setName(rs.getString("sName"));
			returnInfo.setAccountTypeID(rs.getString("sAccountTypeID"));
		}

		String strAccountTypeID = "";
		String strAccountTypeName = "";
		if (returnInfo.getAccountTypeID() != null
				&& !returnInfo.getAccountTypeID().equals("")) {
			strSQL = " select ID,sAccountType from sett_AccountType where ID in ("
					+ returnInfo.getAccountTypeID() + ")";

			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				strAccountTypeID = strAccountTypeID + rs.getLong("ID") + ",";
				strAccountTypeName = strAccountTypeName
						+ rs.getString("sAccountType") + ",";
			}
			if (strAccountTypeID != null && strAccountTypeID.length() > 0) {
				strAccountTypeID = strAccountTypeID.substring(0,
						strAccountTypeID.length() - 1);
			}
			if (strAccountTypeName != null && strAccountTypeName.length() > 0) {
				strAccountTypeName = strAccountTypeName.substring(0,
						strAccountTypeName.length() - 1);
			}
		}
		returnInfo.setAccountTypeID(strAccountTypeID);
		returnInfo.setAccountTypeName(strAccountTypeName);
		strSQL = " select * from sett_AccountType where ID in ("
				+ returnInfo.getAccountTypeID() + ") order by ID";
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return strSQL.toString();
	}

	public String getDepositLoanSQL_AccountDetail(QueryDepositLoanWhereInfo info)
			throws Exception {
		DepositLoanSearchSettingInfo returnInfo = new DepositLoanSearchSettingInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Database.getConnection();

			String strSQL = " select * from sett_DEPOSITLOANSEARCHSETTING where ID="
					+ info.getDepositLoanSearchID();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				returnInfo.setID(rs.getLong("ID"));
				returnInfo.setCurrencyID(rs.getLong("nCurrencyID"));
				returnInfo.setName(rs.getString("sName"));
				returnInfo.setAccountTypeID(rs.getString("sAccountTypeID"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			String strAccountTypeID = "";
			String strAccountTypeName = "";
			if (returnInfo.getAccountTypeID() != null
					&& !returnInfo.getAccountTypeID().equals("")) {
				strSQL = " select ID,sAccountType from sett_AccountType where ID in ("
						+ returnInfo.getAccountTypeID() + ")";

				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				while (rs.next()) {
					strAccountTypeID = strAccountTypeID + rs.getLong("ID")
							+ ",";
					strAccountTypeName = strAccountTypeName
							+ rs.getString("sAccountType") + ",";
				}
				if (strAccountTypeID != null && strAccountTypeID.length() > 0) {
					strAccountTypeID = strAccountTypeID.substring(0,
							strAccountTypeID.length() - 1);
				}
				if (strAccountTypeName != null
						&& strAccountTypeName.length() > 0) {
					strAccountTypeName = strAccountTypeName.substring(0,
							strAccountTypeName.length() - 1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			returnInfo.setAccountTypeID(strAccountTypeID);
			returnInfo.setAccountTypeName(strAccountTypeName);

		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(e.getMessage());
		} finally {
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		String strSQL = " select * from sett_AccountType where ID in ("
				+ returnInfo.getAccountTypeID() + ") order by ID";
		return strSQL.toString();
	}

	public String queryCosignLoanSumByLoanTypeID(QueryDepositLoanWhereInfo qdlwi) {
		StringBuffer sb = new StringBuffer();
		if (isToday(qdlwi.getOfficeID(), qdlwi.getCurrencyID(), qdlwi.getDate())) {
			sb
					.append(" select vc.loantypeid loantypeid, sum(round(sa.mbalance,2)) Balance \n");
			sb
					.append(" from Client c,sett_account a,sett_subaccount sa,sett_vcontractinfo vc,sett_accountType sat \n");
			sb.append(" where c.id = a.nclientid and a.id = sa.naccountid \n");
			sb.append(" and a.nofficeid = " + qdlwi.getOfficeID()
					+ " and a.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
			sb.append(" and a.naccounttypeid=sat.id and sat.nAccountGroupID = "
					+ SETTConstant.AccountGroupType.CONSIGN
					+ " and sa.al_nLoanNoteID = vc.loanPayID \n");
			sb.append(" and vc.loantypeid in(" + LOANConstant.LoanType.WT + ","
					+ LOANConstant.LoanType.WT + ") \n");
			if (qdlwi.getEnterpriseTypeID1() > 0)
				sb.append(" and c.nClienttypeID1="
						+ qdlwi.getEnterpriseTypeID1());
			if (qdlwi.getEnterpriseTypeID2() > 0)
				sb.append(" and c.nClienttypeID2="
						+ qdlwi.getEnterpriseTypeID2());
			if (qdlwi.getEnterpriseTypeID3() > 0)
				sb.append(" and c.nClienttypeID3="
						+ qdlwi.getEnterpriseTypeID3());
			if (qdlwi.getEnterpriseTypeID4() > 0)
				sb.append(" and c.nClienttypeID4="
						+ qdlwi.getEnterpriseTypeID4());
			if (qdlwi.getEnterpriseTypeID5() > 0)
				sb.append(" and c.nClienttypeID5="
						+ qdlwi.getEnterpriseTypeID5());
			if (qdlwi.getEnterpriseTypeID6() > 0)
				sb.append(" and c.nClienttypeID6="
						+ qdlwi.getEnterpriseTypeID6());
			if (qdlwi.getClientManager() > 0)
				sb.append(" and c.ncustomermanageruserid="
						+ qdlwi.getClientManager());
			// 上海电气新增扩展属性条件
			if (qdlwi.getExtendAttribute1() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE1 = "
						+ qdlwi.getExtendAttribute1() + " \n");
			}
			if (qdlwi.getExtendAttribute2() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE2 = "
						+ qdlwi.getExtendAttribute2() + " \n");
			}
			if (qdlwi.getExtendAttribute3() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE3 = "
						+ qdlwi.getExtendAttribute3() + " \n");
			}
			if (qdlwi.getExtendAttribute4() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE4 = "
						+ qdlwi.getExtendAttribute4() + " \n");
			}
			sb.append(" group by vc.loantypeid \n");
		} else {
			sb
					.append(" select vc.loantypeid loantypeid, sum(round(da.mbalance,2)) Balance \n");
			sb
					.append(" from Client c,sett_account a,sett_subaccount sa,sett_dailyaccountbalance da,sett_vcontractinfo vc,sett_accountType sat \n");
			sb.append(" where c.id = a.nclientid and a.id = sa.naccountid \n");
			sb.append(" and a.nofficeid = " + qdlwi.getOfficeID()
					+ " and a.ncurrencyid = " + qdlwi.getCurrencyID() + " \n");
			sb.append(" and a.naccounttypeid=sat.id and sat.nAccountGroupID = "
					+ SETTConstant.AccountGroupType.CONSIGN
					+ " and sa.al_nLoanNoteID = vc.loanPayID \n");
			sb.append(" and vc.loantypeid in(" + LOANConstant.LoanType.WT + ","
					+ LOANConstant.LoanType.WT + ") \n");
			sb.append(" and sa.id=da.nsubaccountid and da.dtdate=to_date('"
					+ DataFormat.getDateString(qdlwi.getDate())
					+ "','yyyy-mm-dd') \n");
			if (qdlwi.getEnterpriseTypeID1() > 0)
				sb.append(" and c.nClienttypeID1="
						+ qdlwi.getEnterpriseTypeID1());
			if (qdlwi.getEnterpriseTypeID2() > 0)
				sb.append(" and c.nClienttypeID2="
						+ qdlwi.getEnterpriseTypeID2());
			if (qdlwi.getEnterpriseTypeID3() > 0)
				sb.append(" and c.nClienttypeID3="
						+ qdlwi.getEnterpriseTypeID3());
			if (qdlwi.getEnterpriseTypeID4() > 0)
				sb.append(" and c.nClienttypeID4="
						+ qdlwi.getEnterpriseTypeID4());
			if (qdlwi.getEnterpriseTypeID5() > 0)
				sb.append(" and c.nClienttypeID5="
						+ qdlwi.getEnterpriseTypeID5());
			if (qdlwi.getEnterpriseTypeID6() > 0)
				sb.append(" and c.nClienttypeID6="
						+ qdlwi.getEnterpriseTypeID6());
			if (qdlwi.getClientManager() > 0)
				sb.append(" and c.ncustomermanageruserid="
						+ qdlwi.getClientManager());
			// 上海电气新增扩展属性条件
			if (qdlwi.getExtendAttribute1() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE1 = "
						+ qdlwi.getExtendAttribute1() + " \n");
			}
			if (qdlwi.getExtendAttribute2() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE2 = "
						+ qdlwi.getExtendAttribute2() + " \n");
			}
			if (qdlwi.getExtendAttribute3() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE3 = "
						+ qdlwi.getExtendAttribute3() + " \n");
			}
			if (qdlwi.getExtendAttribute4() > 0) {
				sb.append(" and c.NEXTENDATTRIBUTE4 = "
						+ qdlwi.getExtendAttribute4() + " \n");
			}
			sb.append(" group by vc.loantypeid \n");
		}
		return sb.toString();
	}

	public String getOtherDepositSQL(QueryOtherDepositInfo qInfo) {
		StringBuffer m_sbSelect = new StringBuffer("");
		StringBuffer m_sbFrom = new StringBuffer("");
		StringBuffer m_sbWhere = new StringBuffer("");
		// select
		m_sbSelect.append("acct.sAccountNo as AccountNo, \n");
		m_sbSelect.append("client.sname ClientName, \n");
		m_sbSelect.append("acct.id as AccountID, \n");
		m_sbSelect.append("subAcct.id as SubAccountID, \n");
		m_sbSelect.append("acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("subAcct.mOpenAmount as Amount, \n");
		m_sbSelect.append("round(nvl(subAcct.mBalance,0),2) as Balance, \n");
		m_sbSelect.append("subAcct.nStatusID as subAccountStatusID,  \n");
		m_sbSelect.append("subAcct.mInterest as Interest \n");
		// from
		if (DataFormat.formatDate(
				Env.getSystemDate(qInfo.getOfficeID(), qInfo.getCurrencyID()))
				.equals(DataFormat.formatDate(qInfo.getDate()))) {
			m_sbFrom
					.append("sett_account acct, sett_subAccount subAcct, client \n");
			m_sbWhere
					.append("subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");
		} else {
			m_sbFrom
					.append("sett_account acct, sett_subAccount subAcct, client,sett_dailyaccountbalance \n");
			m_sbWhere
					.append(" Client.id = acct.nclientid and  acct.id = subAcct.naccountid and acct.id = sett_dailyaccountbalance.naccountid \n");
			m_sbWhere.append(" and sett_dailyaccountbalance.dtdate = to_date('"
					+ DataFormat.formatDate(qInfo.getDate())
					+ "','yyyy-mm-dd') \n");
		}

		m_sbWhere.append("and acct.nAccountTypeID in ("
				+ qInfo.getAccountTypeID() + ")  \n");

		m_sbWhere.append(" and acct.nofficeid=" + qInfo.getOfficeID() + " \n");
		m_sbWhere.append(" and acct.nCurrencyID=" + qInfo.getCurrencyID()
				+ " \n");
		m_sbWhere.append(" and acct.nStatusID != 0 \n");// add by xfma
		m_sbWhere.append(" and subAcct.nStatusID <> 0 \n");
		m_sbWhere.append(" and subAcct.nStatusID <> 2 \n");
		if (qInfo.getEnterpriseTypeID1() > 0) {
			m_sbWhere.append(" and client.nclienttypeid1 = "
					+ qInfo.getEnterpriseTypeID1() + " \n");
		}
		if (qInfo.getEnterpriseTypeID2() > 0) {
			m_sbWhere.append(" and client.nclienttypeid2 = "
					+ qInfo.getEnterpriseTypeID2() + " \n");
		}
		if (qInfo.getEnterpriseTypeID3() > 0) {
			m_sbWhere.append(" and client.nclienttypeid3 = "
					+ qInfo.getEnterpriseTypeID3() + " \n");
		}
		if (qInfo.getEnterpriseTypeID4() > 0) {
			m_sbWhere.append(" and client.nclienttypeid4 = "
					+ qInfo.getEnterpriseTypeID4() + " \n");
		}
		if (qInfo.getEnterpriseTypeID5() > 0) {
			m_sbWhere.append(" and client.nclienttypeid5 = "
					+ qInfo.getEnterpriseTypeID5() + " \n");
		}
		if (qInfo.getEnterpriseTypeID6() > 0) {
			m_sbWhere.append(" and client.nclienttypeid6 = "
					+ qInfo.getEnterpriseTypeID6() + " \n");
		}
		if (qInfo.getLClientManager() > 0) {
			m_sbWhere.append(" and client.ncustomermanageruserid = "
					+ qInfo.getLClientManager() + " \n");
		}
		// 上海电气新增扩展属性条件
		if (qInfo.getExtendAttribute1() != -1
				&& qInfo.getExtendAttribute1() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE1 = "
					+ qInfo.getExtendAttribute1() + " \n");
		}
		if (qInfo.getExtendAttribute2() != -1
				&& qInfo.getExtendAttribute2() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE2 = "
					+ qInfo.getExtendAttribute2() + " \n");
		}
		if (qInfo.getExtendAttribute3() != -1
				&& qInfo.getExtendAttribute3() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE3 = "
					+ qInfo.getExtendAttribute3() + " \n");
		}
		if (qInfo.getExtendAttribute4() != -1
				&& qInfo.getExtendAttribute4() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE4 = "
					+ qInfo.getExtendAttribute4() + " \n");
		}
		return " SELECT " + m_sbSelect + " FROM " + m_sbFrom + " WHERE "
				+ m_sbWhere;
	}

	public String getDepositLoan_FixedNoticeSQL(QueryDepositLoanWhereInfo qdlwi) {
		StringBuffer m_sbSQL = new StringBuffer("");
		m_sbSQL.setLength(0);
		// select
		if (DataFormat.formatDate(
				Env.getSystemDate(qdlwi.getOfficeID(), qdlwi.getCurrencyID()))
				.equals(DataFormat.formatDate(qdlwi.getDate()))) {
			m_sbSQL
					.append(" select set_fix.nfixeddepositmonthid,sum(round(sett_subaccount.mbalance,2)) mBalance \n");
			m_sbSQL
					.append(" from client,sett_account,sett_subaccount,(select a.transtime nfixeddepositmonthid, a.officeid nofficeid, a.currencyid ncurrencyid, b.id naccounttypeid from sett_fixeddepositmonth a, sett_accounttype b where 1 = 1 and a.statusid <> 0 and a.transtype = b.naccountgroupid and b.nstatusid <>0) set_fix \n");
			m_sbSQL
					.append(" where sett_account.id = sett_subaccount.naccountid  \n");
			m_sbSQL.append(" and client.ID = sett_Account.nClientID \n");
			m_sbSQL
					.append(" and sett_account.naccounttypeid= set_fix.naccounttypeid(+)  \n");
			//
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
			if (SETTConstant.AccountType.isFixAccountType(qdlwi
					.getDepositAccountTypeID())) {
				// m_sbSQL.append(" and sett_subaccount.af_ndepositterm(+) =
				// sett_subaccounttype_fixed.nfixeddepositmonthid \n");
				// modify by zcwang 2008-4-18
				m_sbSQL
						.append(" and sett_subaccount.af_ndepositterm(+) = set_fix.nfixeddepositmonthid \n");
				//
			} else if (SETTConstant.AccountType.isNotifyAccountType(qdlwi
					.getDepositAccountTypeID())) {
				// m_sbSQL.append(" and sett_subaccount.af_nnoticeday(+) =
				// sett_subaccounttype_fixed.nfixeddepositmonthid \n");
				// modify by zcwang 2008-4-18
				m_sbSQL
						.append(" and sett_subaccount.af_nnoticeday(+) = set_fix.nfixeddepositmonthid \n");
				//
			}
			m_sbSQL.append(" and sett_account.naccounttypeid = "
					+ qdlwi.getDepositAccountTypeID() + " \n");
			// m_sbSQL.append(" and sett_subaccounttype_fixed.nOfficeID =
			// sett_account.nOfficeID \n");
			// m_sbSQL.append(" and sett_subaccounttype_fixed.nCurrencyID =
			// sett_account.nCurrencyID \n");
			// modify by zcwang 2008-4-18
			m_sbSQL
					.append(" and  set_fix.nOfficeID = sett_account.nOfficeID \n");
			m_sbSQL
					.append(" and  set_fix.nCurrencyID = sett_account.nCurrencyID \n");
			//
			m_sbSQL.append(" and sett_account.nOfficeID = "
					+ qdlwi.getOfficeID() + " \n");
			m_sbSQL.append(" and sett_account.nCurrencyID = "
					+ qdlwi.getCurrencyID() + " \n");
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

			m_sbSQL.append(" group by set_fix.nfixeddepositmonthid \n");
		} else {
			m_sbSQL
					.append(" select set_fix.nfixeddepositmonthid,sum(round(sett_dailyaccountbalance.mbalance,2)) mBalance \n");
			m_sbSQL
					.append(" from client,sett_account,sett_subaccount,(select a.transtime nfixeddepositmonthid, a.officeid nofficeid, a.currencyid ncurrencyid, b.id naccounttypeid from sett_fixeddepositmonth a, sett_accounttype b where 1 = 1 and a.statusid <> 0 and a.transtype = b.naccountgroupid and b.nstatusid <>0) set_fix,sett_dailyaccountbalance \n");
			m_sbSQL
					.append(" where sett_account.id = sett_subaccount.naccountid and sett_subaccount.id = sett_dailyaccountbalance.nsubaccountid \n");
			m_sbSQL.append(" and client.ID = sett_Account.nClientID \n");
			m_sbSQL
					.append(" and sett_account.naccounttypeid= set_fix.naccounttypeid(+)  \n");
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
			if (SETTConstant.AccountType.isFixAccountType(qdlwi
					.getDepositAccountTypeID())) {
				m_sbSQL
						.append(" and sett_subaccount.af_ndepositterm(+) = set_fix.nfixeddepositmonthid \n");
			} else if (SETTConstant.AccountType.isNotifyAccountType(qdlwi
					.getDepositAccountTypeID())) {
				m_sbSQL
						.append(" and sett_subaccount.af_nnoticeday(+) = set_fix.nfixeddepositmonthid \n");
			}
			m_sbSQL.append(" and sett_account.naccounttypeid = "
					+ qdlwi.getDepositAccountTypeID() + "  \n");
			m_sbSQL
					.append(" and sett_dailyaccountbalance.dtdate(+) = to_date('"
							+ DataFormat.formatDate(qdlwi.getDate())
							+ "','yyyy-mm-dd') \n");
			m_sbSQL
					.append(" and  set_fix.nOfficeID = sett_account.nOfficeID \n");
			m_sbSQL
					.append(" and  set_fix.nCurrencyID = sett_account.nCurrencyID \n");
			//
			m_sbSQL.append(" and sett_account.nOfficeID = "
					+ qdlwi.getOfficeID() + " \n");
			m_sbSQL.append(" and sett_account.nCurrencyID = "
					+ qdlwi.getCurrencyID() + " \n");
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

			m_sbSQL.append(" group by set_fix.nfixeddepositmonthid \n");
		}
		return m_sbSQL.toString();
	}

	public String getFixedDepositSQL(QueryFixedDepositInfo qInfo) {
		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append("acct.sAccountNo as AccountNo, \n");
		m_sbSelect.append("client.sname ClientName, \n");
		m_sbSelect.append("subAcct.AF_sDepositNo as DepositNo, \n");
		m_sbSelect.append("acct.id as AccountID, \n");
		m_sbSelect.append("subAcct.id as SubAccountID, \n");
		m_sbSelect.append("acct.nAccountTypeID as AccountTypeID, \n");
		m_sbSelect.append("subAcct.mOpenAmount as Amount, \n");
		m_sbSelect.append("round(nvl(subAcct.mBalance,0),2) as Balance, \n");
		m_sbSelect.append("subAcct.nStatusID as subAccountStatusID,  \n");
		m_sbSelect.append("subAcct.AF_mRate as Rate,  \n");
		m_sbSelect.append("subAcct.AF_dtStart as StartDate,  \n");
		m_sbSelect.append("subAcct.AF_dtEnd as EndDate,  \n");
		m_sbSelect.append("subAcct.mInterest as Interest, \n");
		m_sbSelect.append("subAcct.af_ndepositterm as depositterm, \n");
		m_sbSelect.append("subAcct.Af_Nnoticeday as NoticeDay, \n");
		m_sbSelect.append("subAcct.Af_sDepositBillNo as DepositBillNo, \n"); // 换开定期存单号
																				// 2006.3.29
																				// feiye
																				// add

		if (qInfo.getIsMarginDeposit() > 0) {
			m_sbSelect
					.append("subAcct.Al_Mpredrawinterest as PreDrawInterest \n");
		} else {
			m_sbSelect
					.append("subAcct.AF_mPreDrawInterest as PreDrawInterest \n");
		}

		StringBuffer m_sbFrom = new StringBuffer();
		m_sbFrom
				.append("sett_account acct, sett_subAccount subAcct, client,sett_accountType acctType \n");

		StringBuffer m_sbWhere = new StringBuffer();
		m_sbWhere
				.append("subAcct.nAccountID=acct.id and acct.nclientid=client.id  \n");
		if (qInfo.getClientManager() > 0)
			m_sbWhere.append(" and client.nCustomerManagerUserId = "
					+ qInfo.getClientManager() + "  \n");
		// 定期存款查询
		String strFixedTmp = "-1";
		if (qInfo.getIsFixedDeposit() > 0) {
			strFixedTmp = getFixAccountType(qInfo.getCurrencyID(), qInfo
					.getOfficeID());
		}
		// 通知存款查询
		String strNoticeTmp = "-1";
		if (qInfo.getIsNoticeDeposit() > 0) {
			strNoticeTmp = getNotifyAccountType(qInfo.getCurrencyID(), qInfo
					.getOfficeID());
		}
		// 保证金存款查询
		String strMarginTmp = "-1";
		if (qInfo.getIsMarginDeposit() > 0) {
			strMarginTmp = getMarginAccountType();
		}
		// 组合
		if (strFixedTmp.equals("-1") && strNoticeTmp.equals("-1")
				&& strMarginTmp.equals("-1")) {
			m_sbWhere.append(" and acct.nAccountTypeID in (");
			String strtemp = "";
			String str = "";

			strtemp = getFixAccountType(qInfo.getCurrencyID(), qInfo
					.getOfficeID());
			if (!strtemp.equals("")) {
				str += strtemp + ",";
			}
			strtemp = getNotifyAccountType(qInfo.getCurrencyID(), qInfo
					.getOfficeID());
			if (!strtemp.equals("")) {
				str += strtemp + ",";
			}
			strtemp = getMarginAccountType();
			if (!strtemp.equals("")) {
				str += strtemp + ",";
			}

			m_sbWhere.append(str.substring(0, str.length() - 1) + ")");
		} else {
			m_sbWhere.append(" and acct.nAccountTypeID in (" + strFixedTmp
					+ "," + strNoticeTmp + "," + strMarginTmp + ")  \n");
		}
		m_sbWhere.append("and acct.nofficeid=" + qInfo.getOfficeID() + " \n");
		m_sbWhere.append("and acct.nCurrencyID=" + qInfo.getCurrencyID()
				+ " \n");
		m_sbWhere.append("and subAcct.nStatusID <> 0 \n");
		m_sbWhere.append("and acct.nAccountTypeID=acctType.id \n");

		if (qInfo.getClientNoFrom() != null
				&& qInfo.getClientNoFrom().length() > 0)
			m_sbWhere.append("and client.scode>='" + qInfo.getClientNoFrom()
					+ "'");
		if (qInfo.getClientNoTo() != null && qInfo.getClientNoTo().length() > 0)
			m_sbWhere.append("and client.scode<='" + qInfo.getClientNoTo()
					+ "'");
		if (qInfo.getAccountNoFrom() != null
				&& qInfo.getAccountNoFrom().length() > 0)
			m_sbWhere.append("and acct.sAccountNo>='"
					+ qInfo.getAccountNoFrom() + "'");
		if (qInfo.getAccountNoTo() != null
				&& qInfo.getAccountNoTo().length() > 0)
			m_sbWhere.append("and acct.sAccountNo<='" + qInfo.getAccountNoTo()
					+ "'");
		if (qInfo.getAppointAccountNo() != null
				&& qInfo.getAppointAccountNo().length() > 0) {
			m_sbWhere.append(" and acct.sAccountNo in ('"
					+ qInfo.getAppointAccountNo() + "')");
		}

		if (qInfo.getEnterpriseTypeID1() > 0)
			m_sbWhere.append("and client.nClienttypeID1 = "
					+ qInfo.getEnterpriseTypeID1() + " \n");
		if (qInfo.getEnterpriseTypeID2() > 0)
			m_sbWhere.append("and client.nClienttypeID2 = "
					+ qInfo.getEnterpriseTypeID2() + " \n");
		if (qInfo.getEnterpriseTypeID3() > 0)
			m_sbWhere.append("and client.nClienttypeID3 = "
					+ qInfo.getEnterpriseTypeID3() + " \n");
		if (qInfo.getEnterpriseTypeID4() > 0)
			m_sbWhere.append("and client.nClienttypeID4 = "
					+ qInfo.getEnterpriseTypeID4() + " \n");
		if (qInfo.getEnterpriseTypeID5() > 0)
			m_sbWhere.append("and client.nClienttypeID5 = "
					+ qInfo.getEnterpriseTypeID5() + " \n");
		if (qInfo.getEnterpriseTypeID6() > 0)
			m_sbWhere.append("and client.nClienttypeID6 = "
					+ qInfo.getEnterpriseTypeID6() + " \n");

		if (qInfo.getClientSort() > 0)
			m_sbWhere.append("and client.nSettClientTypeID="
					+ qInfo.getClientSort() + " \n");
		if (qInfo.getClientType() > 0)
			m_sbWhere.append("and client.NCORPNATUREID="
					+ qInfo.getClientType() + " \n");
		if (qInfo.getAccountType() > 0)
			m_sbWhere.append("and acctType.id=" + qInfo.getAccountType()
					+ " \n");
		if (qInfo.getParentCompany1() > 0)
			m_sbWhere.append("and client.nParentCorpID1="
					+ qInfo.getParentCompany1() + " \n");
		if (qInfo.getParentCompany2() > 0)
			m_sbWhere.append("and client.nParentCorpID2="
					+ qInfo.getParentCompany2() + " \n");
		if (qInfo.getIsFixedDeposit() > 0) // 针对定期来说
		{
			if (qInfo.getDepositNoChoose() == 0) {
			}
			if (qInfo.getDepositNoChoose() == 1) {
				m_sbWhere
						.append("  and ( subAcct.Af_sDepositBillNo is null or subAcct.Af_sDepositBillNo='')  \n");
			}
			if (qInfo.getDepositNoChoose() == 2) {
				m_sbWhere
						.append("  and subAcct.Af_sDepositBillNo is not null  \n");
			}
			// **********************************************************************

			if (qInfo.getFixedDepositNoFrom() != null
					&& qInfo.getFixedDepositNoFrom().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo>='"
						+ qInfo.getFixedDepositNoFrom() + "'");
			if (qInfo.getFixedDepositNoTo() != null
					&& qInfo.getFixedDepositNoTo().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo<='"
						+ qInfo.getFixedDepositNoTo() + "'");
			if (qInfo.getFixedStartDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtStart>=to_date('"
						+ DataFormat.formatDate(qInfo.getFixedStartDateFrom())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getFixedStartDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtStart<=to_date('"
						+ DataFormat.formatDate(qInfo.getFixedStartDateTo())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getFixedEndDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"
						+ DataFormat.formatDate(qInfo.getFixedEndDateFrom())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getFixedEndDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd<=to_date('"
						+ DataFormat.formatDate(qInfo.getFixedEndDateTo())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getFixedDepositStatus() > 0)
				m_sbWhere.append("and subAcct.nStatusID="
						+ qInfo.getFixedDepositStatus() + " \n");
			if (qInfo.getFixedDepositTermFrom() > 0)
				m_sbWhere.append("and subAcct.AF_nDepositTerm>="
						+ qInfo.getFixedDepositTermFrom() + " \n");
			if (qInfo.getFixedDepositTermTo() > 0)
				m_sbWhere.append("and subAcct.AF_nDepositTerm<="
						+ qInfo.getFixedDepositTermTo() + " \n");
			if (qInfo.getFixedAmountFrom() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount>="
						+ qInfo.getFixedAmountFrom() + " \n");
			if (qInfo.getFixedAmountTo() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount<="
						+ qInfo.getFixedAmountTo() + " \n");
			if (qInfo.getFixedRate() > 0)
				m_sbWhere.append("and subAcct.AF_mRate=" + qInfo.getFixedRate()
						+ " \n");
			if (qInfo.getFixedEndDate() != null) {
				// 处理截止日期
				if (qInfo.getFixedEndDate().after(
						Env.getSystemDate(qInfo.getOfficeID(), qInfo
								.getCurrencyID()))) {
					m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"
							+ DataFormat.formatDate(qInfo.getFixedEndDate())
							+ "','yyyy-mm-dd') ");
				} else {
					m_sbWhere.append("and (subAcct.dtFinish>=to_date('"
							+ DataFormat.formatDate(qInfo.getFixedEndDate())
							+ "','yyyy-mm-dd') ");
					m_sbWhere.append("or subAcct.dtFinish is null ) ");
				}
			}
		}
		// 查询通知存款
		if (qInfo.getIsNoticeDeposit() > 0) {
			if (qInfo.getNoticeDepositNoFrom() != null
					&& qInfo.getNoticeDepositNoFrom().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo>='"
						+ qInfo.getNoticeDepositNoFrom() + "'");
			if (qInfo.getNoticeDepositNoTo() != null
					&& qInfo.getNoticeDepositNoTo().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo<='"
						+ qInfo.getNoticeDepositNoTo() + "'");
			if (qInfo.getNoticeStartDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtStart>=to_date('"
						+ DataFormat.formatDate(qInfo.getNoticeStartDateFrom())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getNoticeStartDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtStart<=to_date('"
						+ DataFormat.formatDate(qInfo.getNoticeStartDateTo())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getNoticeDepositStatus() > 0)
				m_sbWhere.append("and subAcct.nStatusID="
						+ qInfo.getNoticeDepositStatus() + " \n");
			if (qInfo.getNoticeBalanceFrom() != 0.0)
				m_sbWhere.append("and subAcct.mBalance>="
						+ qInfo.getNoticeBalanceFrom() + " \n");
			if (qInfo.getNoticeBalanceTo() != 0.0)
				m_sbWhere.append("and subAcct.mBalance<="
						+ qInfo.getNoticeBalanceTo() + " \n");
			if (qInfo.getNoticeDrawAmountFrom() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount>="
						+ qInfo.getNoticeDrawAmountFrom() + " \n");
			if (qInfo.getNoticeDrawAmountTo() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount<="
						+ qInfo.getNoticeDrawAmountTo() + " \n");
			if (qInfo.getNoticeDays() > 0)
				m_sbWhere.append("and subAcct.AF_nNoticeDay="
						+ qInfo.getNoticeDays() + " \n");
			if (qInfo.getNoticeEndDate() != null) {
				// 处理截止日期
				if (qInfo.getNoticeEndDate().after(Env.getSystemDate())) {
					m_sbWhere
							.append("and decode(acctType.nAccountGroupID,"
									+ SETTConstant.AccountGroupType.NOTIFY
									+ ",to_date('1000-01-01','yyyy-mm-dd'),subAcct.AF_dtEnd) <= to_date('"
									+ DataFormat.formatDate(qInfo
											.getNoticeEndDate())
									+ "','yyyy-mm-dd') and subAcct.AF_dtEnd is not null ");
				} else {
					m_sbWhere.append("and (subAcct.dtFinish<=to_date('"
							+ DataFormat.formatDate(qInfo.getNoticeEndDate())
							+ "','yyyy-mm-dd') ");
					m_sbWhere.append("and subAcct.dtFinish is not null ) ");
				}
			}
		}

		// 针对保证金存款查询
		if (qInfo.getIsMarginDeposit() > 0) {
			if (qInfo.getMarginDepositNoFrom() != null
					&& qInfo.getMarginDepositNoFrom().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo>='"
						+ qInfo.getMarginDepositNoFrom() + "'");
			if (qInfo.getMarginDepositNoTo() != null
					&& qInfo.getMarginDepositNoTo().length() > 0)
				m_sbWhere.append("and subAcct.AF_sDepositNo<='"
						+ qInfo.getMarginDepositNoTo() + "'");
			if (qInfo.getMarginStartDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtStart>=to_date('"
						+ DataFormat.formatDate(qInfo.getMarginStartDateFrom())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getMarginStartDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtStart<=to_date('"
						+ DataFormat.formatDate(qInfo.getMarginStartDateTo())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getMarginEndDateFrom() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"
						+ DataFormat.formatDate(qInfo.getMarginEndDateFrom())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getMarginEndDateTo() != null)
				m_sbWhere.append("and subAcct.AF_dtEnd<=to_date('"
						+ DataFormat.formatDate(qInfo.getMarginEndDateTo())
						+ "','yyyy-mm-dd') ");
			if (qInfo.getMarginDepositStatus() > 0)
				m_sbWhere.append("and subAcct.nStatusID="
						+ qInfo.getMarginDepositStatus() + " \n");
			if (qInfo.getMarginAmountFrom() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount>="
						+ qInfo.getMarginAmountFrom() + " \n");
			if (qInfo.getMarginAmountTo() != 0.0)
				m_sbWhere.append("and subAcct.mOpenAmount<="
						+ qInfo.getMarginAmountTo() + " \n");
			if (qInfo.getMarginRate() > 0)
				m_sbWhere.append("and subAcct.AF_mRate="
						+ qInfo.getMarginRate() + " \n");
			if (qInfo.getMarginEndDate() != null) {
				// 处理截止日期
				if (qInfo.getMarginEndDate().after(
						Env.getSystemDate(qInfo.getOfficeID(), qInfo
								.getCurrencyID()))) {
					m_sbWhere.append("and subAcct.AF_dtEnd>=to_date('"
							+ DataFormat.formatDate(qInfo.getMarginEndDate())
							+ "','yyyy-mm-dd') ");
				} else {
					m_sbWhere.append("and (subAcct.dtFinish>=to_date('"
							+ DataFormat.formatDate(qInfo.getMarginEndDate())
							+ "','yyyy-mm-dd') ");
					m_sbWhere.append("or subAcct.dtFinish is null ) ");
				}
			}
		}

		if (qInfo.getIsLeaching() > 0)
			m_sbWhere.append(" and subAcct.mBalance<> 0 ");
		//

		// 上海电气新增扩展属性条件
		if (qInfo.getExtendAttribute1() != -1
				&& qInfo.getExtendAttribute1() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE1 = "
					+ qInfo.getExtendAttribute1() + " \n");
		}
		if (qInfo.getExtendAttribute2() != -1
				&& qInfo.getExtendAttribute2() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE2 = "
					+ qInfo.getExtendAttribute2() + " \n");
		}
		if (qInfo.getExtendAttribute3() != -1
				&& qInfo.getExtendAttribute3() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE3 = "
					+ qInfo.getExtendAttribute3() + " \n");
		}
		if (qInfo.getExtendAttribute4() != -1
				&& qInfo.getExtendAttribute4() != 0) {
			m_sbWhere.append(" and client.NEXTENDATTRIBUTE4 = "
					+ qInfo.getExtendAttribute4() + " \n");
		}
		return "SELECT " + m_sbSelect.toString() + " FROM " + m_sbFrom.toString() + " WHERE "
				+ m_sbWhere.toString();
	}
	public String getFixAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getFixAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		if(str!=null&&str.length()>0)
		{
			str=str.substring(0, str.length() - 1);
		}
		return str;
	}
	public String getNotifyAccountType(long lCurrencyID,long lOfficeID)
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getNotifyAccountTypeCode(lCurrencyID,lOfficeID);
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		if(str!=null&&str.length()>0)
		{
			str=str.substring(0, str.length() - 1);
		}
		return str;
	}
	public String getMarginAccountType()
	{
		String str = "";
		long[] array = SETTConstant.AccountType.getMarginAccountTypeCode();
		for (int i = 0; i < array.length; i++)
			str = str + array[i] + ",";
		if(str.length()>0)
		{
			str= str.substring(0, str.length() - 1);
		}
		
		return str;
		
	}
	public boolean isToday(long lOfficeID, long lCurrencyID,
			Timestamp tsQueryDate) {
		boolean b = true;
		Timestamp tsOpenDate = Env.getSystemDate(lOfficeID, lCurrencyID);
		if (tsOpenDate != null && tsQueryDate != null) {
			if (tsOpenDate.toString().substring(0, 10).equals(
					tsQueryDate.toString().substring(0, 10)))
				b = true;
			else
				b = false;
		}
		return b;
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

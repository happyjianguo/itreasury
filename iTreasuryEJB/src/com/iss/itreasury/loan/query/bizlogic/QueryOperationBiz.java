package com.iss.itreasury.loan.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.loan.contract.bizlogic.ContractOperation;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.loanapply.dao.FormAssureDao;
import com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo;
import com.iss.itreasury.loan.loaninterestsetting.bizlogic.LoanInterestSetting;
import com.iss.itreasury.loan.loaninterestsetting.bizlogic.LoanInterestSettingHome;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.InterestRateInfo;
import com.iss.itreasury.loan.loanpaynotice.bizlogic.PayNoticeOperation;
import com.iss.itreasury.loan.query.dao.QueryOperationDao;
import com.iss.itreasury.loan.query.dataentity.QueryLoanApplyInfo;
import com.iss.itreasury.loan.query.dataentity.QueryPerformInfo;
import com.iss.itreasury.loan.query.dataentity.QuerySumInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractPlanInfo;
import com.iss.itreasury.loan.query.dataentity.QuestExtendInfo;
import com.iss.itreasury.loan.query.dataentity.QuestPayNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QuestRepayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBObject;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class QueryOperationBiz {

	private QueryOperationDao dao = new QueryOperationDao();

	private void colseRs(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
			rs = null;
		}
	}

	public PagerInfo queryLoanApply(QueryLoanApplyInfo qInfo, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryLoanApply(qInfo);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", symbol);
			map.put("qInfo", qInfo);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryLoanApplyResultSetHandle", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"totalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryLoanApplyResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		String symbol = map.get("currencysymbol") + "";

		QueryLoanApplyInfo qInfo = (QueryLoanApplyInfo) map.get("qInfo");
		while (rs.next()) {

			String strType = "";
			String applyCode = "";
			String borrowClientName = "";
			String consignClientName = "";
			String strAmount = "";
			String strRate = "";
			String strDate = "";
			String strInterval = "";
			String strInputDate = "";
			String strStatus = "";
			String inputUserName = "";
			String strNextCheckUser = "";
			double adjustRate = 0;
			long bankInterestID = -1;
			double interestRate = 0;
			double baseRate = 0;
			long lID = -1;
			double staidAdjustRate = 0;
			long lTypeID = -1;

			lID = rs.getLong("ID");
			lTypeID = rs.getLong("TypeID");
			strType = rs.getString("subTypeName");
			applyCode = rs.getString("applyCode");
			borrowClientName = rs.getString("borrowClientName");
			consignClientName = rs.getString("consignClientName");
			strAmount = DataFormat.formatListAmount(rs.getDouble("loanAmount"));
			interestRate = rs.getDouble("interestRate");
			bankInterestID = rs.getLong("bankInterestID");
			adjustRate = rs.getDouble("adjustRate");
			staidAdjustRate = rs.getDouble("staidAdjustRate");

			// ��ʾ���ʵ�ҳ�����Ӷ�Libor���ʵ�֧��,Jason Fang,2005-02-24 -----START
			if (qInfo.getCurrencyID() == 1) {
				// �����ҵ��
				if (bankInterestID > 0) {
					LoanInterestSettingHome home = (LoanInterestSettingHome) EJBObject
							.getEJBHome("LoanInterestSettingHome");
					LoanInterestSetting interestSet = home.create();
					InterestRateInfo rateInfo = interestSet
							.findInterestRateByID(bankInterestID);
					baseRate = rateInfo.getInterestRate();
					System.out.println("" + baseRate + "---" + adjustRate);
					interestRate = baseRate * (1 + adjustRate / 100)
							+ staidAdjustRate; // ִ������
					// =ninh 2004-06-22 ������ ���ӹ̶���������===�ı�ִ�����ʵ��㷨//
				}
				strRate = DataFormat.formatRate(interestRate, 6);
			} else {
				// ���ҵ��
				ContractOperation operation = new ContractOperation();
				RateInfo liborInfo = operation.getLoanLatelyRate(lID);
				if (liborInfo != null) {
					strRate = DataFormat.formatString(liborInfo
							.getLateRateString());
					if (liborInfo.getLateRateString() == null
							|| liborInfo.getLateRateString() == "") {
						strRate = "0.000000";
					}
				}
			}
			LoanApplyInfo appInfo = null;

			if (lTypeID == 3 || lTypeID == 4) {
				strRate = DataFormat.formatRate(rs.getDouble("InterestRate")
						* (1 + adjustRate / 100) + staidAdjustRate, 6);
			}
			// ��ʾ���ʵ�ҳ�����Ӷ�Libor���ʵ�֧��,Jason Fang,2005-02-24 -----END

			strDate = DataFormat.getDateString(rs.getTimestamp("StartDate"))
					+ "-"
					+ DataFormat.getDateString(rs.getTimestamp("EndDate"));
			if (rs.getLong("IntervalNum") <= 0)
				strInterval = "";
			else
				strInterval = String.valueOf(rs.getLong("intervalNum"));
			strInputDate = DataFormat.getDateString(rs
					.getTimestamp("inputDate"));
			strStatus = LOANConstant.LoanStatus.getName(rs.getLong("statusID"));
			inputUserName = rs.getString("inputUserName");

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, strType);
			PagerTools.returnCellList(cellList, applyCode + "," + lTypeID + ","
					+ lTypeID + "," + lID);
			PagerTools.returnCellList(cellList, (borrowClientName == null) ? ""
					: borrowClientName);
			PagerTools.returnCellList(cellList, LOANConstant.DraftType
					.getName(rs.getLong("tsDiscountTypeID")));
			PagerTools.returnCellList(cellList,
					(consignClientName == null) ? "" : consignClientName);
			PagerTools.returnCellList(cellList,
					(rs.getDouble("LoanAmount") > 0 ? symbol : "") + strAmount);
			PagerTools.returnCellList(cellList, strRate + "%");
			PagerTools.returnCellList(cellList, strDate);
			PagerTools.returnCellList(cellList, strInterval);
			PagerTools.returnCellList(cellList, strInputDate);
			PagerTools.returnCellList(cellList, strStatus);
			PagerTools.returnCellList(cellList, inputUserName);
			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList totalResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QueryOperation operation = new QueryOperation();
		String symbol = map.get("currencysymbol") + "";
		QueryLoanApplyInfo qInfo = (QueryLoanApplyInfo) map.get("qInfo");

		QuerySumInfo sumInfo = operation.queryLoanApplySum(qInfo);
		ArrayList list = new ArrayList();
		list.add("<B>������</B>������{" + symbol
				+ DataFormat.formatListAmount(sumInfo.getTotalApplyAmount())
				+ "}");
		return list;
	}

	public PagerInfo queryContract(QuestContractInfo qInfo, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryContract(qInfo);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", symbol);
			map.put("qInfo", qInfo);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryContractResultSetHandle", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"queryContractTotalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public PagerInfo queryContract1(QuestContractInfo qInfo, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryContract(qInfo);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", symbol);
			map.put("qInfo", qInfo);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryContractResultSetHandle1", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"queryContractTotalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryContractResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		String symbol = map.get("currencysymbol") + "";

		QuestContractInfo qInfo = (QuestContractInfo) map.get("qInfo");
		String sysOpenDate = DataFormat.getDateString(Env.getSystemDate(qInfo
				.getOfficeID(), qInfo.getCurrencyID())); // ϵͳ����ʱ��
		ContractOperation operation = new ContractOperation();
		while (rs.next()) {

			String strType = "";
			String applyCode = "";
			String borrowClientName = "";
			String consignClientName = "";
			String strAmount = "";
			String strDailyBalance = "";
			String strRate = "";
			String strBasicRate = "";
			String strStartDate = "";
			String strEndDate = "";
			String strInterval = "";
			String strStatus = "";
			String strRiskLevel = "";
			String strAssureType = "";
			String inputUserName = "";
			String assureCompanyName = "";// ������λ����
			long nLoanID = -1;// ��������ID
			String strBalanceAmount = "";
			long lID = -1;
			String strContractRate = "";// ��ͬ���ʣ��ַ�����
			RateInfo rateInfo = null;
			FormAssureDao fAssure = new FormAssureDao();
			ContractAmountInfo amountInfo = null;
			lID = rs.getLong("ContractID");
			rateInfo = operation.getLatelyRate(lID);
			amountInfo = operation.getLateAmount(lID);
			nLoanID = rs.getLong("LoanID");// ��ô�������ID
			assureCompanyName = fAssure.findClientNameByLoanID(nLoanID);// ������λ����
			strType = rs.getString("LoanTypeName");
			applyCode = rs.getString("ContractCode");
			borrowClientName = rs.getString("BorrowClientName");
			consignClientName = rs.getString("ClientName");
			strAmount = DataFormat.formatListAmount(rs
					.getDouble("ExamineAmount"));
			strBalanceAmount = DataFormat.formatListAmount(rs
					.getDouble("Balance"));// �������
			strDailyBalance = DataFormat.formatListAmount(rs
					.getDouble("DailyBalance"));// ��������������
			String selectedTime = rs.getString("DailyDate");
			System.out.println("" + amountInfo.getBalanceAmount());
			// û�ж�rateInfo==null�����ж� 2006-3-15
			if (rateInfo == null) {
				rateInfo = new RateInfo();
				rateInfo.setLateRate(0d);
				rateInfo.setRate(0d);
			}

			strRate = DataFormat.formatRate(rateInfo.getLateRate());
			strBasicRate = DataFormat.formatRate(rateInfo.getRate());
			strStartDate = DataFormat.getDateString(rs
					.getTimestamp("LoanStart"));
			strEndDate = DataFormat.getDateString(rs.getTimestamp("LoanEnd"));
			if (rs.getLong("IntervalNum") <= 0)
				strInterval = "";
			else
				strInterval = String.valueOf(rs.getLong("IntervalNum"));
			strStatus = LOANConstant.ContractStatus.getName(rs
					.getLong("StatusID"));
			strRiskLevel = LOANConstant.VentureLevel.getName(rs
					.getLong("RiskLevel"));
			// ������ʽ
			strAssureType = "";
			if (rs.getLong("IsCredit") > 0)
				strAssureType += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT);
			if (rs.getLong("IsAssure") > 0)
				strAssureType += "��"
						+ LOANConstant.AssureType
								.getName(LOANConstant.AssureType.ASSURE);
			if (rs.getLong("IsImpawn") > 0)
				strAssureType += "��"
						+ LOANConstant.AssureType
								.getName(LOANConstant.AssureType.IMPAWN);
			if (rs.getLong("IsPledge") > 0)
				strAssureType += "��"
						+ LOANConstant.AssureType
								.getName(LOANConstant.AssureType.PLEDGE);
			if (strAssureType != null && strAssureType.startsWith("��"))
				strAssureType = strAssureType.substring(1);

			inputUserName = rs.getString("InputUserName");

			// ��ͬ����
			if (rs.getLong("ContractID") > 0) {
				if (rs.getLong("InterestTypeID") == 1) {
					strContractRate = rateInfo.getLateRate() + "";
					if (rateInfo.getLateRate() == 0.0) {
						strContractRate = "0.000000";
					}
				} else if (rs.getLong("InterestTypeID") == 2) {
					strContractRate = rateInfo.getLateRateString();
				}
			}

			if (qInfo.getGather()) {
				strRate = "";
				strBasicRate = "";
				strContractRate = "0.000000";
			}

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, strType);
			if (!qInfo.getGather()) {
				PagerTools.returnCellList(cellList, applyCode + "," + lID);
			} else {
				PagerTools.returnCellList(cellList, (applyCode == null
						|| applyCode.equals("") ? ""
						: applyCode)
						+ ",''");

			}
			PagerTools.returnCellList(cellList, (borrowClientName == null) ? ""
					: borrowClientName);
			PagerTools.returnCellList(cellList,
					(consignClientName == null) ? "" : consignClientName);
			PagerTools.returnCellList(cellList, (assureCompanyName == null
					|| assureCompanyName.equals("") ? ""
					: (assureCompanyName + "," + nLoanID)));
			PagerTools.returnCellList(cellList,
					(rs.getDouble("ExamineAmount") > 0 ? symbol : "")
							+ strAmount);
			if ((selectedTime.compareTo(sysOpenDate)) >= 0) {
				PagerTools.returnCellList(cellList, symbol + strBalanceAmount);
			} else {
				PagerTools.returnCellList(cellList, (rs
						.getDouble("DailyBalance") > 0 ? symbol : "")
						+ strDailyBalance);
			}
			PagerTools.returnCellList(cellList,
					rs.getLong("InterestTypeID") == 1 ? strBasicRate
							: strContractRate + "%");
			PagerTools.returnCellList(cellList,
					rs.getLong("InterestTypeID") == 1 ? strRate
							: strContractRate + "%");
			PagerTools.returnCellList(cellList, strStartDate);
			PagerTools.returnCellList(cellList, strEndDate);
			PagerTools.returnCellList(cellList, strInterval);
			PagerTools.returnCellList(cellList, strStatus);
			PagerTools.returnCellList(cellList, strRiskLevel);
			PagerTools.returnCellList(cellList, strAssureType);
			PagerTools.returnCellList(cellList, inputUserName + "," + lID);

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList queryContractResultSetHandle1(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		String symbol = map.get("currencysymbol") + "";

		QuestContractInfo qInfo = (QuestContractInfo) map.get("qInfo");
		String sysOpenDate = DataFormat.getDateString(Env.getSystemDate(qInfo
				.getOfficeID(), qInfo.getCurrencyID())); // ϵͳ����ʱ��
		ContractOperation operation = new ContractOperation();
		while (rs.next()) {

			String strType = "";
			String applyCode = "";
			String borrowClientName = "";
			String consignClientName = "";
			String strAmount = "";
			String strDailyBalance = "";
			String strRate = "";
			String strBasicRate = "";
			String strStartDate = "";
			String strEndDate = "";
			String strInterval = "";
			String strStatus = "";
			String strRiskLevel = "";
			String strAssureType = "";
			String inputUserName = "";
			String assureCompanyName = "";// ������λ����
			long nLoanID = -1;// ��������ID
			String strBalanceAmount = "";
			long lID = -1;
			String strContractRate = "";// ��ͬ���ʣ��ַ�����
			RateInfo rateInfo = null;
			FormAssureDao fAssure = new FormAssureDao();
			ContractAmountInfo amountInfo = null;
			lID = rs.getLong("ContractID");
			rateInfo = operation.getLatelyRate(lID);
			amountInfo = operation.getLateAmount(lID);
			nLoanID = rs.getLong("LoanID");// ��ô�������ID
			assureCompanyName = fAssure.findClientNameByLoanID(nLoanID);// ������λ����
			strType = rs.getString("LoanTypeName");
			applyCode = rs.getString("ContractCode");
			borrowClientName = rs.getString("BorrowClientName");
			consignClientName = rs.getString("ClientName");
			strAmount = DataFormat.formatListAmount(rs
					.getDouble("ExamineAmount"));
			strBalanceAmount = DataFormat.formatListAmount(rs
					.getDouble("Balance"));// �������
			strDailyBalance = DataFormat.formatListAmount(rs
					.getDouble("DailyBalance"));// ��������������
			String selectedTime = rs.getString("DailyDate");
			System.out.println("" + amountInfo.getBalanceAmount());
			// û�ж�rateInfo==null�����ж� 2006-3-15
			if (rateInfo == null) {
				rateInfo = new RateInfo();
				rateInfo.setLateRate(0d);
				rateInfo.setRate(0d);
			}

			strRate = DataFormat.formatRate(rateInfo.getLateRate());
			strBasicRate = DataFormat.formatRate(rateInfo.getRate());
			strStartDate = DataFormat.getDateString(rs
					.getTimestamp("LoanStart"));
			strEndDate = DataFormat.getDateString(rs.getTimestamp("LoanEnd"));
			if (rs.getLong("IntervalNum") <= 0)
				strInterval = "";
			else
				strInterval = String.valueOf(rs.getLong("IntervalNum"));
			strStatus = LOANConstant.ContractStatus.getName(rs
					.getLong("StatusID"));
			strRiskLevel = LOANConstant.VentureLevel.getName(rs
					.getLong("RiskLevel"));
			// ������ʽ
			strAssureType = "";
			if (rs.getLong("IsCredit") > 0)
				strAssureType += LOANConstant.AssureType
						.getName(LOANConstant.AssureType.CREDIT);
			if (rs.getLong("IsAssure") > 0)
				strAssureType += "��"
						+ LOANConstant.AssureType
								.getName(LOANConstant.AssureType.ASSURE);
			if (rs.getLong("IsImpawn") > 0)
				strAssureType += "��"
						+ LOANConstant.AssureType
								.getName(LOANConstant.AssureType.IMPAWN);
			if (rs.getLong("IsPledge") > 0)
				strAssureType += "��"
						+ LOANConstant.AssureType
								.getName(LOANConstant.AssureType.PLEDGE);
			if (strAssureType != null && strAssureType.startsWith("��"))
				strAssureType = strAssureType.substring(1);

			inputUserName = rs.getString("InputUserName");

			// ��ͬ����
			if (rs.getLong("ContractID") > 0) {
				if (rs.getLong("InterestTypeID") == 1) {
					strContractRate = rateInfo.getLateRate() + "";
					if (rateInfo.getLateRate() == 0.0) {
						strContractRate = "0.000000";
					}
				} else if (rs.getLong("InterestTypeID") == 2) {
					strContractRate = rateInfo.getLateRateString();
				}
			}

			if (qInfo.getGather()) {
				strRate = "";
				strBasicRate = "";
				strContractRate = "0.000000";
			}

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, strType);
			if (!qInfo.getGather()) {
				PagerTools.returnCellList(cellList, applyCode + ","+ applyCode +"," + lID);
			} else {
				PagerTools.returnCellList(cellList, (applyCode == null
						|| applyCode.equals("") ? "" + "," + "\" \""+ "," + "\" \""
						: applyCode)
						+ ",\" \""+ "," + "\" \"");

			}
			PagerTools.returnCellList(cellList, (borrowClientName == null) ? ""
					: borrowClientName);
			PagerTools.returnCellList(cellList,
					(consignClientName == null) ? "" : consignClientName);
			PagerTools.returnCellList(cellList,
					(rs.getDouble("ExamineAmount") > 0 ? symbol : "")
							+ strAmount);
			if ((selectedTime.compareTo(sysOpenDate)) >= 0) {
				PagerTools.returnCellList(cellList, symbol + strBalanceAmount);
			} else {
				PagerTools.returnCellList(cellList, (rs
						.getDouble("DailyBalance") > 0 ? symbol : "")
						+ strDailyBalance);
			}
			PagerTools.returnCellList(cellList,
					rs.getLong("InterestTypeID") == 1 ? strBasicRate
							: strContractRate + "%");
			PagerTools.returnCellList(cellList,
					rs.getLong("InterestTypeID") == 1 ? strRate
							: strContractRate + "%");
			PagerTools.returnCellList(cellList, strStartDate);
			PagerTools.returnCellList(cellList, strEndDate);
			PagerTools.returnCellList(cellList, strInterval);
			PagerTools.returnCellList(cellList, strStatus);
			PagerTools.returnCellList(cellList, strRiskLevel);
			PagerTools.returnCellList(cellList, inputUserName );

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList queryContractTotalResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QueryOperation operation = new QueryOperation();
		String symbol = map.get("currencysymbol") + "";
		QuestContractInfo qInfo = (QuestContractInfo) map.get("qInfo");

		QuerySumInfo sumInfo = operation.queryContractSum(qInfo);
		String strSumDailyBalance = DataFormat.formatListAmount(sumInfo
				.getTotalDailyBalance());
		String strSumBalance = DataFormat.formatListAmount(sumInfo
				.getTotalBalance());
		String selectedTime = "";
		if (rs.next())
			selectedTime = rs.getString("dailyDate");
		String sysOpenDate = DataFormat.getDateString(Env.getSystemDate(qInfo
				.getOfficeID(), qInfo.getCurrencyID())); // ϵͳ����ʱ��

		ArrayList list = new ArrayList();
		list.add("<B>������</B>{" + symbol
				+ DataFormat.formatListAmount(sumInfo.getTotalApplyAmount())
				+ "}");
		list.add("<B>��������������</B>{"
				+ symbol
				+ (selectedTime.compareTo(sysOpenDate) >= 0 ? strSumBalance
						: strSumDailyBalance) + "}");
		return list;
	}

	public PagerInfo queryContractPlanResult(QuestContractPlanInfo qInfo)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = " select 1 from dual";
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("qInfo", qInfo);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryContractPlanResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryContractPlanResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		QueryOperation operation = new QueryOperation();
		QuestContractPlanInfo qInfo = (QuestContractPlanInfo) map.get("qInfo");
		Vector queryResults = (Vector) operation.queryContractPlanResult(qInfo);
		for (int i = 0; i < queryResults.size(); i++) {
			QuestContractPlanInfo info = null;
			info = (QuestContractPlanInfo) queryResults.get(i);

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, info.getPlanVersion() + ","
					+ info.getPlanID());
			PagerTools.returnCellList(cellList, DataFormat.getDateString(info
					.getInputDate()));
			PagerTools.returnCellList(cellList, DataFormat.formatString(info
					.getModifier()));

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public PagerInfo queryPerform(long contractID, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = " select 1 from dual";
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("contractID", contractID);
			map.put("currencysymbol", symbol);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryPerformResultSetHandle", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"queryPerformTotalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryPerformResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		QueryOperation operation = new QueryOperation();
		long contractID = Long.valueOf(map.get("contractID") + "");
		Vector v = (Vector) operation.queryPerform(contractID, -1, -1);
		int count = v.size();
		String strPayDate = "";
		String strPayType = "";
		String strAmount = "";
		String strInterest = "";
		String strInterestRate = "";
		String strChargeAmount = "";

		for (int i = 0; i < count; i++) {
			QueryPerformInfo info = (QueryPerformInfo) v.get(i);
			strPayDate = DataFormat.getDateString(info.getPerformDate());
			if (info.getPayType() == 1) {
				strPayType = "�ſ�";
			} else {
				strPayType = "����";
			}
			strAmount = DataFormat.formatListAmount(info.getPerformAmount());
			strInterest = DataFormat.formatListAmount(info.getInterest());
			strInterestRate = DataFormat.formatRate(info.getPerformRate());
			strChargeAmount = DataFormat
					.formatListAmount(info.getMcommission());

			cellList = new ArrayList();
			PagerTools.returnCellList(cellList, strPayDate);
			PagerTools.returnCellList(cellList, strPayType);
			PagerTools.returnCellList(cellList, strAmount);
			PagerTools.returnCellList(cellList, strInterest);
			PagerTools.returnCellList(cellList, strChargeAmount);
			PagerTools.returnCellList(cellList, strInterestRate);
			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList queryPerformTotalResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		String symbol = map.get("currencysymbol") + "";
		double TotalPayAmount = 0.0;
		double TotalRepayAmount = 0.0;
		double HInterest = 0.0;
		double HchargeAmount = 0.0;

		long contractID = Long.valueOf(map.get("contractID") + "");

		QueryOperation operation = new QueryOperation();
		QuerySumInfo sumInfo = operation.queryPerformSum(contractID);
		Vector v = (Vector) operation.queryPerform(contractID, -1, -1);
		int count = v.size();

		for (int i = 0; i < count; i++) {
			QueryPerformInfo info = (QueryPerformInfo) v.get(i);
			if (info.getPayType() == 1) {
				TotalPayAmount += info.getPerformAmount();
			} else {
				TotalRepayAmount += info.getPerformAmount();
			}
			HInterest += info.getInterest();
			HchargeAmount += info.getMcommission();
		}

		ArrayList list = new ArrayList();
		list.add("�ſ�ϼ�{" + symbol + DataFormat.formatListAmount(TotalPayAmount)
				+ "}");
		list.add("����ϼ�{" + symbol
				+ DataFormat.formatListAmount(TotalRepayAmount) + "}");
		list.add("�Ѹ���Ϣ�ϼ�{" + symbol + DataFormat.formatListAmount(HInterest)
				+ "}");
		list.add("�Ѹ������Ѻϼ�{" + symbol
				+ DataFormat.formatListAmount(HchargeAmount) + "}");
		list.add("��Ϣ�ϼ�{" + symbol
				+ DataFormat.formatListAmount(sumInfo.getTotalInterestAmount())
				+ "}");
		list.add("�����Ѻϼ�{" + symbol
				+ DataFormat.formatListAmount(sumInfo.getTotalChargeAmount())
				+ "}");
		list.add("�����Ѻϼ�{" + symbol
				+ DataFormat.formatListAmount(sumInfo.getTotalCreditAmount())
				+ "}");
		return list;
	}

	public PagerInfo queryPayNotice(QuestPayNoticeInfo info, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryPayNotice(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", symbol);
			map.put("info", info);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryPayNoticeResultSetHandle", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"queryPayNoticeTotalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryPayNoticeResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		String symbol = map.get("currencysymbol") + "";
		QuestPayNoticeInfo info = (QuestPayNoticeInfo) map.get("info");
		while (rs.next()) {
			long id = rs.getLong("ID");
			long drawnoticeid = rs.getLong("DrawNoticeID");
			String code = rs.getString("Code");
			long contractid = rs.getLong("ContractID");
			String contractCode = rs.getString("ContractCode");
			String clientName = rs.getString("LoanClientName");
			String consignClientName = rs.getString("ConsignClientName");
			String inputUserName = rs.getString("InputUserName");
			String strLoanAmount = DataFormat.formatListAmount(rs
					.getDouble("LoanAmount"));
			String strPayAmount = DataFormat.formatListAmount(rs
					.getDouble("Amount"));

			PayNoticeOperation operation = new PayNoticeOperation();
			String strPayRate = DataFormat.formatRate(operation.getLatelyRate(
					rs.getLong("ID")).getLateRate());

			strPayRate += "%";
			String strLoanType = rs.getString("LoanTypeName");
			String strOutDate = DataFormat.getDateString(rs
					.getTimestamp("OutDate"));
			String strInDate = DataFormat.getDateString(rs
					.getTimestamp("InDate"));
			String strInputDate = DataFormat.getDateString(rs
					.getTimestamp("InputDate"));
			String strStatus = LOANConstant.LoanPayNoticeStatus.getName(rs
					.getLong("StatusID"));

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, code + "," + id + ","
					+ contractid + "," + drawnoticeid);
			PagerTools.returnCellList(cellList, strLoanType);
			PagerTools.returnCellList(cellList, contractCode);
			PagerTools.returnCellList(cellList, clientName);
			PagerTools.returnCellList(cellList, consignClientName);
			PagerTools.returnCellList(cellList, symbol + strLoanAmount);
			PagerTools.returnCellList(cellList, symbol + strPayAmount);
			PagerTools.returnCellList(cellList, strPayRate);
			PagerTools.returnCellList(cellList, strOutDate);
			PagerTools.returnCellList(cellList, strInDate);
			PagerTools.returnCellList(cellList, strStatus);
			PagerTools.returnCellList(cellList, strInputDate);
			PagerTools.returnCellList(cellList, inputUserName);

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList queryPayNoticeTotalResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QuestPayNoticeInfo info = (QuestPayNoticeInfo) map.get("info");
		String symbol = map.get("currencysymbol") + "";
		QueryOperation operation = new QueryOperation();

		QuerySumInfo sumInfo = operation.queryLoanPayNoticeSum(info);
		String TotalPayNoticeAmount = DataFormat.formatListAmount(sumInfo
				.getTotalPayAmount());// �ſ��ܶ�

		ArrayList list = new ArrayList();
		list.add("�ſ���{" + symbol + TotalPayNoticeAmount + "}");
		return list;
	}

	public PagerInfo queryRepayNotice(QuestRepayNoticeInfo info, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryRepayNotice(info);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			map.put("currencysymbol", symbol);
			map.put("info", info);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryRepayNoticeResultSetHandle", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"queryRepayNoticeTotalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryRepayNoticeResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		String symbol = map.get("currencysymbol") + "";
		QuestRepayNoticeInfo info = (QuestRepayNoticeInfo) map.get("info");
		while (rs.next()) {

			long id = rs.getLong("ID");// ����֪ͨ������
			String code = rs.getString("Code");// ����֪ͨ�����
			String contractCode = rs.getString("ContractCode");// ��ͬ���
			String clientName = rs.getString("ClientName");// ��λ
			String consignClientName = rs.getString("ConsignClientName");// ί�е�λ
			String strLetoutNoticeAmount = DataFormat.formatListAmount(rs
					.getDouble("LetoutNoticeAmount"));// �ſ���

			String strAmount = DataFormat.formatListAmount(rs
					.getDouble("Amount"));// ������
			String strBalanceAmount = DataFormat.formatListAmount(rs
					.getDouble("BalanceAmount"));// �黹��Ϣ
			String strLoanType = rs.getString("LoanTypeName");// ��������
			String strPBackDate = DataFormat.getDateString(rs
					.getTimestamp("PBackDate"));// ��������
			String mBalance = rs.getTimestamp("PBackDate") == null ? ""
					: Constant.CurrencyType.getSymbol(info.getNCurrencyID())
							+ strBalanceAmount;
			String strIsHead = LOANConstant.YesOrNo.getName(rs
					.getLong("IsAhead"));// �Ƿ���ǰ����״̬
			String strInputDate = DataFormat.getDateString(rs
					.getTimestamp("InputDate"));// ¼������
			String strInputUserName = rs.getString("InputUserName");// ¼��������
			String strLetoutNoticeCode = rs.getString("LetoutNoticeCode");// �ſ�֪ͨ�����
			String strStatus = LOANConstant.AheadRepayStatus.getName(rs
					.getLong("StatusID"));// ����֪ͨ��״̬

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, code + "," + id);
			PagerTools.returnCellList(cellList, contractCode);
			PagerTools.returnCellList(cellList, strLoanType);
			PagerTools.returnCellList(cellList, clientName);
			PagerTools.returnCellList(cellList, consignClientName);
			PagerTools.returnCellList(cellList, strLetoutNoticeCode);
			PagerTools.returnCellList(cellList, symbol + strLetoutNoticeAmount);
			PagerTools.returnCellList(cellList, symbol + strAmount);
			PagerTools.returnCellList(cellList, mBalance);
			PagerTools.returnCellList(cellList, strPBackDate);
			PagerTools.returnCellList(cellList, strIsHead);
			PagerTools.returnCellList(cellList, strStatus);
			PagerTools.returnCellList(cellList, strInputDate);
			PagerTools.returnCellList(cellList, strInputUserName);

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList queryRepayNoticeTotalResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QuestRepayNoticeInfo info = (QuestRepayNoticeInfo) map.get("info");
		String symbol = map.get("currencysymbol") + "";

		QueryOperation operation = new QueryOperation();
		QuerySumInfo sumInfo = operation.queryRepayNoticeSum(info);

		String TotalRepayAmount = DataFormat.formatListAmount(sumInfo
				.getTotalRepayAmount());// �����ܶ�
		String TotalPayNoticeAmount = DataFormat.formatListAmount(sumInfo
				.getTotalPayAmount());// �ſ��ܶ�
		String TotalInterestAmount = DataFormat.formatListAmount(sumInfo
				.getTotalInterestAmount());// �ſ��ܶ�

		ArrayList list = new ArrayList();
		list.add("�ſ��ܽ��{" + symbol + TotalPayNoticeAmount + "}");
		list.add("�����ܽ��{" + symbol + TotalRepayAmount + "}");
		list.add("�黹��Ϣ�ܽ��{" + symbol + TotalInterestAmount + "}");
		return list;
	}

	public PagerInfo queryAdjustUser(long contractID) throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryAdjustUser(contractID);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryAdjustUserResultSetHandle");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryAdjustUserResultSetHandle(ResultSet rs)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		while (rs.next()) {

			HashMap map1 = new HashMap();

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("numberRow")));
			PagerTools.returnCellList(cellList, DataFormat.formatDate(rs
					.getTimestamp("adjustDate")));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("beforeUser")));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("afterUser")));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("reason")));

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public PagerInfo queryQuestContractPlanInfo(QuestContractPlanInfo queryInfo)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryQuestContractPlanInfo(queryInfo);
		Map map = new HashMap();
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryQuestContractPlanInfoResultSetHandle");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryQuestContractPlanInfoResultSetHandle(ResultSet rs)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		while (rs.next()) {

			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, rs.getLong("PlanVersion") + ","
					+ rs.getLong("PlanID"));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("Modifier")));

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public PagerInfo queryExtend(QuestExtendInfo queryInfo, String symbol)
			throws Exception {
		PagerInfo pagerInfo = null;
		String sql = dao.queryExtend(queryInfo);
		Map map = new HashMap();
		map.put("symbol", symbol);
		map.put("queryInfo", queryInfo);
		try {
			pagerInfo = new PagerInfo();
			// �õ���ѯSQL
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(QueryOperationBiz.class,
					"queryExtendResultSetHandle", map);
			pagerInfo.setTotalExtensionMothod(QueryOperationBiz.class,
					"queryExtendTotalResultSetHandle", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}

	public ArrayList queryExtendResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		ArrayList resultList = new ArrayList(); // ���շ��ؽ��
		ArrayList cellList = null;// ��
		ResultPagerRowInfo rowInfo = null;// ��
		ContractDao dao = new ContractDao();

		String symbol = map.get("symbol") + "";

		while (rs.next()) {
			double ContractRate = 0;
			double Rate = 0;
			RateInfo ri = new RateInfo();
			try {
				ri = dao.getLatelyRate(-1, rs.getLong("ContractID"), null);
				ContractRate = ri.getRate();
				Rate = ri.getLateRate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cellList = new ArrayList();

			PagerTools.returnCellList(cellList, rs.getString("LoanTypeName"));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("ContractCode")));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("ClientName")));
			PagerTools.returnCellList(cellList, DataFormat.formatString(rs
					.getString("ConsignClientName")));
			PagerTools.returnCellList(cellList, rs.getString("applyCode") + ","
					+ rs.getLong("ExtendID") + "," + rs.getLong("TypeID"));
			PagerTools.returnCellList(cellList, symbol
					+ DataFormat.formatListAmount(rs.getDouble("Amount")));
			PagerTools
					.returnCellList(cellList, symbol
							+ DataFormat.formatListAmount(rs
									.getDouble("ExtendAmount")));
			PagerTools.returnCellList(cellList, DataFormat
					.formatRate(ContractRate));
			PagerTools.returnCellList(cellList, DataFormat.formatRate(Rate));
			PagerTools.returnCellList(cellList, DataFormat.formatRate(rs
					.getDouble("ExtendRate")));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("DateFrom")));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("DateTo")));
			PagerTools.returnCellList(cellList, LOANConstant.ExtendStatus
					.getName(rs.getLong("StatusID")));
			PagerTools.returnCellList(cellList, DataFormat.getDateString(rs
					.getTimestamp("InputDate")));
			PagerTools.returnCellList(cellList, rs.getString("InputUserName"));

			// �洢������
			rowInfo = new ResultPagerRowInfo();
			rowInfo.setCell(cellList);
			rowInfo.setId(String.valueOf(0));

			// ��������
			resultList.add(rowInfo);

		}
		colseRs(rs);

		return resultList;
	}

	public ArrayList queryExtendTotalResultSetHandle(ResultSet rs, Map map)
			throws Exception {
		QueryOperation query = new QueryOperation();

		String symbol = map.get("symbol") + "";
		QuerySumInfo sumInfo = new QuerySumInfo();
		QuestExtendInfo queryInfo = (QuestExtendInfo) map.get("queryInfo");
		sumInfo = query.queryExtendSum(queryInfo);
		String strSumAmount = DataFormat.formatDisabledAmount(sumInfo
				.getTotalApplyAmount(), 0);
		String strSumExtendAmount = DataFormat.formatDisabledAmount(sumInfo
				.getTotalExtendAmount(), 0);
		ArrayList list = new ArrayList();
		list.add("<B>������</B>{" + symbol + strSumAmount + "}");
		list.add("<B>չ�ڽ��</B>{" + symbol + strSumExtendAmount + "}");
		return list;
	}

}
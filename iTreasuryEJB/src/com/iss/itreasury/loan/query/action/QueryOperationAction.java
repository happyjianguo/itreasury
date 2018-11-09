package com.iss.itreasury.loan.query.action;

import java.sql.Timestamp;
import java.util.Map;

import com.iss.itreasury.loan.query.bizlogic.QueryOperationBiz;
import com.iss.itreasury.loan.query.dataentity.QueryLoanApplyInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractPlanInfo;
import com.iss.itreasury.loan.query.dataentity.QuestExtendInfo;
import com.iss.itreasury.loan.query.dataentity.QuestPayNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QuestRepayNoticeInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.sysframe.pager.dataentity.PagerInfo;

public class QueryOperationAction {

	QueryOperationBiz biz = new QueryOperationBiz();

	public PagerInfo queryLoanApply(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			String tmp = "";
			QueryLoanApplyInfo qInfo = new QueryLoanApplyInfo();
			// 贷款类型
			tmp = (String) map.get("ltypeid");
			long tmpLong;
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanType(tmpLong);
			}

			// 贷款类型(多选)
			tmp = (String) map.get("stypeids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setLoanTypes(tmp);
			}

			// //获得从q001-v.jsp 页面传过来的贴现汇票种类
			tmp = (String) map.get("lacceptpotypeid");
			if ((tmp != null) && (tmp.length() > 0)) {
				long lAcceptPOTypeID = Long.parseLong(tmp);
				if (lAcceptPOTypeID > 0) {
					qInfo.setTsDiscountTypeID(lAcceptPOTypeID);
				}

			}
			// 申请书编号开始
			tmp = (String) map.get("applycodefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setMinApplyCode(tmp);
			}

			// 申请书编号结束
			tmp = (String) map.get("applycodeto");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setMaxApplyCode(tmp);
			}

			// 申请书状态
			tmp = (String) map.get("lstatusid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanStatusID(tmpLong);
			}

			// 申请书状态(多选)
			tmp = (String) map.get("sstatusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setLoanStatusIDs(tmp);
			}

			// 借款单位
			tmp = (String) map.get("lclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientID(tmpLong);
			}
			
			// 借款单位由
			tmp = (String) map.get("lclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientIDFrom(tmpLong);
			}
			// 借款单位至
			tmp = (String) map.get("lclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientIDTo(tmpLong);
			}

			// 借款单位账号
			tmp = (String) map.get("clientaccount");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setBorrowAccount(tmp);
			}

			// 客户分类
			tmp = (String) map.get("lclienttypeid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanClientTypeID(tmpLong);
			}

			// 主管单位
			tmp = (String) map.get("cparentcorpid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setParentCorpID(tmpLong);
			}

			// 委托单位
			tmp = (String) map.get("wtclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientID(tmpLong);
			}

			// 委托单位由
			tmp = (String) map.get("wtclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientIDFrom(tmpLong);
			}
			// 委托单位至
			tmp = (String) map.get("wtclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientIDTo(tmpLong);
			}

			// 委托单位账号
			tmp = (String) map.get("wtclientaccount");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setConsignAccount(tmp);
			}

			// 贷款金额开始
			tmp = (String) map.get("damountfrom");
			double tmpDouble;
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinLoanAmount(tmpDouble);
			}

			// 贷款金额结束
			tmp = (String) map.get("damountto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxLoanAmount(tmpDouble);
			}

			// 执行利率开始
			tmp = (String) map.get("minexecuterate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setExecuteRateFrom(tmpDouble);
			}
			// 执行利率结束
			tmp = (String) map.get("maxexecuterate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setExecuteRateTo(tmpDouble);
			}

			// 贷款开始时间
			Timestamp maxLoanDate = null;
			tmp = (String) map.get("tsdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMinLoanDate(maxLoanDate);
			}

			// 贷款结束时间
			Timestamp minLoanDate = null;
			tmp = (String) map.get("tsdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				minLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxLoanDate(minLoanDate);
			}

			// 贷款期限
			tmp = (String) map.get("lperiod");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIntervalNum(tmpLong);
			}

			// 贷款期限由
			tmp = (String) map.get("lperiodfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setPeriodFrom(tmpLong);
			}
			// 贷款期限至
			tmp = (String) map.get("lperiodto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setPeriodTo(tmpLong);
			}

			// 贷款提交开始时间
			Timestamp maxInputDate = null;
			tmp = (String) map.get("mininputdate");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxInputDate = DataFormat.getDateTime(tmp);
				qInfo.setMinInputDate(maxInputDate);
			}

			// 贷款提交结束时间
			Timestamp minInputDate = null;
			tmp = (String) map.get("maxinputdate");	
			if ((tmp != null) && (tmp.length() > 0)) {
				minInputDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxInputDate(minInputDate);
			}

			// 保证类型
			tmp = (String) map.get("selassuretype");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setAssureTypeID(tmpLong);
			}

			// 信用等级
			tmp = (String) map.get("selcreditlevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setCreditLevel(tmpLong);
			}

			// 是否技改贷款
			tmp = (String) map.get("selistechnical");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsTechnical(tmpLong);
			}

			// 是否循环
			tmp = (String) map.get("seliscircle");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsCircle(tmpLong);
			}

			// 是否股东
			tmp = (String) map.get("selpartner");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsPartner(tmpLong);
			}

			tmp = map.get("straction") + "";
			if ((tmp.toString() != null) && (tmp.length() > 0)) {
				if (tmp.equals("DB")) {
					qInfo.setQueryPurpose(QueryLoanApplyInfo.DB);
				} else if (tmp.equals("RZZL")) {
					qInfo.setQueryPurpose(QueryLoanApplyInfo.RZZL);
				} else {

					qInfo.setQueryPurpose(QueryLoanApplyInfo.LOAN);

				}

			}

			// 申请书管理人
			tmp = (String) map.get("linputuserid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setInputUserID(tmpLong);
			}

			qInfo.setOfficeID(Long.valueOf(map.get("lofficeid") + "")
					.longValue());
			qInfo.setCurrencyID(Long.valueOf(map.get("lcurrencyid") + "")
					.longValue());

			tmp = (String) map.get("querylevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				String strQueryLevel = tmp;
				qInfo.setQueryLevel(strQueryLevel);
			}
			String symbol = (String) map.get("symbol");
			pagerInfo = biz.queryLoanApply(qInfo, symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

	public PagerInfo queryContract(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			String tmp = "";
			QuestContractInfo qInfo = new QuestContractInfo();
			tmp = (String) map.get("ltypeid");
			long tmpLong;
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanTypeID(tmpLong);
			}

			// 出票人
			tmp = (String) map.get("discountclient");
			if ((tmp != null) && (tmp.length() > 0)) {
				// tmp = URLEncoder.encode(tmp);
				qInfo.setBillDrawer(tmp);
			}
			// 出票人ID
			tmp = (String) map.get("discountclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				// qInfo.setBillDrawer(tmp);
			}

			// 贷款类型(复选)
			tmp = (String) map.get("stypeids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setLoanTypeIDs(tmp);
			}
			// 贷款子类型（复选）
			tmp = (String) map.get("ssubtypeids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setLoanSubTypeIDs(tmp);
			}

			// 合同编号开始
			tmp = (String) map.get("contractcodefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setMinContractCode(tmp);
			}

			// 合同编号结束
			tmp = (String) map.get("contractcodeto");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setMaxContractCode(tmp);
			}

			// 申请书状态(贷款合同查询使用)
			tmp = (String) map.get("statusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setStatusIDs(tmp);
			}

			// 贴现多状态查询
			tmp = (String) map.get("_discountstatusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setStatusIDs(tmp);
			}

			// 合同风险状态(贷款合同查询使用)
			tmp = (String) map.get("venturelevelstatusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setRiskLevels(tmp);
			}

			// 申请书状态（贴现，担保合同查询使用）
			tmp = (String) map.get("lstatusid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setStatusID(tmpLong);
			}
			// 贴现申请单位

			tmp = (String) map.get("lapplyclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setMinborrowClientID(tmpLong);
			}

			tmp = (String) map.get("lapplyclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setMaxborrowClientID(tmpLong);
			}

			// 贴现汇票种类
			tmp = (String) map.get("tsdiscounttypeid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTsDiscountTypeID(tmpLong);
			}

			// 借款单位
			tmp = (String) map.get("lclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientID(tmpLong);
			}

			// 借款单位账号
			tmp = (String) map.get("clientaccount");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setBorrowAccount(tmp);
			}

			// 借款单位由
			tmp = (String) map.get("lclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientIDFrom(tmpLong);
			}
			// 借款单位至
			tmp = (String) map.get("lclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientIDTo(tmpLong);
			}

			// 客户分类
			tmp = (String) map.get("lclienttypeid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanClientTypeID(tmpLong);
			}

			// 主管单位
			tmp = (String) map.get("cparentcorpid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setParentCorpID(tmpLong);
			}

			// 委托单位
			tmp = (String) map.get("wtclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientID(tmpLong);
			}

			// 委托单位由
			tmp = (String) map.get("wtclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientIDFrom(tmpLong);
			}
			// 委托单位至
			tmp = (String) map.get("wtclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientIDTo(tmpLong);
			}

			// 委托单位账号
			tmp = (String) map.get("wtclientaccount");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setConsignAccount(tmp);
			}

			// 贷款金额开始
			tmp = (String) map.get("damountfrom");
			double tmpDouble;
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinLoanAmount(tmpDouble);
			}

			// 贷款金额结束
			tmp = (String) map.get("damountto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxLoanAmount(tmpDouble);
			}

			// 贷款余额开始
			tmp = (String) map.get("damountbalancefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinLoanBalanceAmount(tmpDouble);
			}

			// 贷款余额结束
			tmp = (String) map.get("damountbalanceto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxLoanBalanceAmount(tmpDouble);
			}

			// 实付金额开始
			tmp = (String) map.get("actualamountfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinCheckAmount(tmpDouble);
			}

			// 实付金额结束
			tmp = (String) map.get("actualamountto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxCheckAmount(tmpDouble);
			}

			// /////////////////////////////////////////////////////////
			// //贴现利率开始
			tmp = (String) map.get("minrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinRate(tmpDouble);
			}

			// //贴现利率结束
			tmp = (String) map.get("maxrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxRate(tmpDouble);
			}

			// 合同利率开始
			tmp = (String) map.get("mincontractrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setContractRateFrom(tmpDouble);
			}
			// 合同利率结束
			tmp = (String) map.get("maxcontractrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setContractRateTo(tmpDouble);
			}

			// /////////////////////////////////////////////////////////
			// 买方付息开始
			tmp = (String) map.get("payerratefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinPayerRate(tmpDouble);
			}

			// 买方付息结束
			tmp = (String) map.get("payerrateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxPayerRate(tmpDouble);
			}

			// ///////////////////////////////////////////////////////////////

			// 贷款开始时间
			Timestamp maxLoanDate = null;
			tmp = (String) map.get("tsdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMinStartDate(maxLoanDate);
			}

			// 贷款结束时间
			Timestamp minLoanDate = null;
			tmp = (String) map.get("tsdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				minLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxStartDate(minLoanDate);
			}
			// /////////////////////////////////////////////////

			// added by xiong fei 2010/05/24 合同查询时的开始时间的起始时间
			// 合同开始时间的起始时间
			Timestamp tsStartDateFrom = null;
			tmp = (String) map.get("tsstartdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				tsStartDateFrom = DataFormat.getDateTime(tmp);
				qInfo.setMinStartDate(tsStartDateFrom);
			}

			// 合同开始时间的结束时间
			Timestamp tsStartDateTo = null;
			tmp = (String) map.get("tsstartdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				tsStartDateTo = DataFormat.getDateTime(tmp);
				qInfo.setMaxStartDate(tsStartDateTo);
			}
			// 担保方式
			String iscredit = "";// 信用
			tmp = (String) map.get("iscredit");
			if ((tmp != null) && (tmp.length() > 0)) {
				iscredit = tmp;
				qInfo.setIscredit(Long.valueOf(iscredit).longValue());
			}

			String isrecognizance = "";// 保证金
			tmp = (String) map.get("isrecognizance");
			if ((tmp != null) && (tmp.length() > 0)) {
				isrecognizance = tmp;
				qInfo.setIsrecognizance(Long.valueOf(isrecognizance)
						.longValue());
			}

			String isassure = "";// 保证
			tmp = (String) map.get("isassure");
			if ((tmp != null) && (tmp.length() > 0)) {
				isassure = tmp;
				qInfo.setIsassure(Long.valueOf(isassure).longValue());
			}

			String isimpawn = "";// 质押
			tmp = (String) map.get("isimpawn");
			if ((tmp != null) && (tmp.length() > 0)) {
				isimpawn = tmp;
				qInfo.setIsimpawn(Long.valueOf(isimpawn).longValue());
			}

			String ispledge = "";// 抵押
			tmp = (String) map.get("ispledge");
			if ((tmp != null) && (tmp.length() > 0)) {
				ispledge = tmp;
				qInfo.setIspledge(Long.valueOf(ispledge).longValue());
			}

			String isrepurchase = "";// 回购
			tmp = (String) map.get("isrepurchase");
			if ((tmp != null) && (tmp.length() > 0)) {
				isrepurchase = tmp;
				qInfo.setIsrepurchase(Long.valueOf(isrepurchase).longValue());
			}
			// 担保方式添加结束

			// 贷款结束时间的开始时间
			Timestamp maxEndLoanDate = null;
			tmp = (String) map.get("tsenddatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxEndLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMinEndDate(maxEndLoanDate);
			}

			// 贷款结束时间的结束时间
			Timestamp minEndLoanDate = null;
			tmp = (String) map.get("tsenddateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				minEndLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxEndDate(minEndLoanDate);
			}
			// /////////////////////////////////////////////////
			// 贴现日期时间的开始时间
			Timestamp minDisccountDate = null;
			tmp = (String) map.get("tsdiscountdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				minDisccountDate = DataFormat.getDateTime(tmp);
				qInfo.setMinDiscountDate(minDisccountDate);
			}

			// 贴现日期时间的结束时间
			Timestamp maxDiscountDate = null;
			tmp = (String) map.get("tsdiscountdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxDiscountDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxDiscountDate(maxDiscountDate);
			}

			// 贴现录入日期时间的开始时间
			Timestamp MinDisccountInputDate = null;
			tmp = (String) map.get("tsinputdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				MinDisccountInputDate = DataFormat.getDateTime(tmp);
				qInfo.setMinDisccountInputDate(MinDisccountInputDate);
			}

			// 贴现录入日期时间的结束时间
			Timestamp MaxDisccountInputDate = null;
			tmp = (String) map.get("tsinputdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				MaxDisccountInputDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxDisccountInputDate(MaxDisccountInputDate);
			}

			// 贴现客户名称
			tmp = (String) map.get("discountclientname");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setDiscountClientName(tmp);
			}
			// 贷款期限
			tmp = (String) map.get("lperiod");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIntervalNum(tmpLong);
			}

			// 贷款期限由
			tmp = (String) map.get("lperiodfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setPeriodFrom(tmpLong);
			}
			// 贷款期限至
			tmp = (String) map.get("lperiodto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setPeriodTo(tmpLong);
			}

			// 保证类型
			tmp = (String) map.get("selassuretype");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setAssureTypeID(tmpLong);
			}

			// 信用等级
			tmp = (String) map.get("selcreditlevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setCreditLevel(tmpLong);
			}

			// 是否技改贷款
			tmp = (String) map.get("selistechnical");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsTechnical(tmpLong);
			}

			// 是否循环
			tmp = (String) map.get("seliscircle");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsCircle(tmpLong);
			}

			// 是否股东
			tmp = (String) map.get("selpartner");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsPartner(tmpLong);
			}

			// 贷款风险状态
			tmp = (String) map.get("venturelevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setRiskLevel(tmpLong);
			}

			// 按地区分类
			tmp = (String) map.get("areatype");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTypeID1(tmpLong);
			}

			// 按行业分类1
			tmp = (String) map.get("industrytype1");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTypeID2(tmpLong);
			}

			// 按行业分类2
			tmp = (String) map.get("industrytype2");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTypeID3(tmpLong);
			}

			// 管理人
			tmp = (String) map.get("linputuserid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setInputUserID(tmpLong);
			}

			/** *************添加国机的变更 2003-3-30 qqgd************** */
			tmp = (String) map.get("showend"); // 标示是否显示结束状态的合同
			if ((tmp != null) && (tmp.length() > 0)) {
				if (tmp.equals("on"))
					qInfo.setShowEnd(false);
				else
					qInfo.setShowEnd(true);
			}
			tmp = (String) map.get("isgather"); // 标示是否显示汇总信息
			if ((tmp != null) && (tmp.length() > 0)) {
				if (tmp.equals("on"))
					qInfo.setGather(true);

			}
			/** ***************end of qqgd's adding******************** */

			tmp = (String) map.get("querylevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				String strQueryLevel = tmp;
				qInfo.setQueryLevel(strQueryLevel);
			}

			tmp = (String) map.get("straction");
			if ((tmp != null) && (tmp.length() > 0)) {
				String strAction = tmp;
				if (strAction.equals("TX"))
					qInfo.setQueryPurpose(QuestContractInfo.TX);
				else if (strAction.equals("ZTX"))
					qInfo.setQueryPurpose(QuestContractInfo.ZTX);
				else if (strAction.equals("DB"))
					qInfo.setQueryPurpose(QuestContractInfo.DB);
				else if (strAction.equals("RZZL"))
					qInfo.setQueryPurpose(QuestContractInfo.RZZL);
				else
					qInfo.setQueryPurpose(QuestContractInfo.LOAN);
			}

			// 客户属性
			tmp = (String) map.get("clienttypeid1");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID1(tmpLong);
			}
			tmp = (String) map.get("clienttypeid2");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID2(tmpLong);
			}
			tmp = (String) map.get("clienttypeid3");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID3(tmpLong);
			}
			tmp = (String) map.get("clienttypeid4");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID4(tmpLong);
			}
			tmp = (String) map.get("clienttypeid5");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID5(tmpLong);
			}
			tmp = (String) map.get("clienttypeid6");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID6(tmpLong);
			}

			long lcurrencyid = Long.valueOf(map.get("lcurrencyid") + "");
			long lofficeid = Long.valueOf(map.get("lofficeid") + "");
			// 余额日期
			Timestamp tsBalanceDate = null;
			tmp = (String) map.get("tsbalancedate");
			if ((tmp != null) && (tmp.length() > 0)) {
				tsBalanceDate = DataFormat.getDateTime(tmp);
			} else// 结算开机日期
			{
				tsBalanceDate = Env.getSystemDate(lofficeid, lcurrencyid);
			}
			qInfo.setBalanceDate(tsBalanceDate);

			String symbol = (String) map.get("symbol");
			pagerInfo = biz.queryContract(qInfo, symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

	public PagerInfo queryContractPlanResult(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		QuestContractPlanInfo qInfo = new QuestContractPlanInfo();
		long contractID = Long.valueOf(map.get("contractid") + "");
		qInfo.setContractID(contractID);
		try {
			pagerInfo = biz.queryContractPlanResult(qInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

	public PagerInfo queryPerform(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long contractID = Long.valueOf(map.get("contractid") + "");
		String symbol = (String) map.get("symbol");

		try {
			pagerInfo = biz.queryPerform(contractID, symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}

	public PagerInfo queryPayNotice(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long contractID = Long.valueOf(map.get("contractid") + "");
		long lcurrencyid = Long.valueOf(map.get("lcurrencyid") + "");
		long lofficeid = Long.valueOf(map.get("lofficeid") + "");
		String symbol = (String) map.get("symbol");

		QuestPayNoticeInfo pInfo = new QuestPayNoticeInfo();
		pInfo.setContractID(contractID);
		pInfo.setNOfficeID(lofficeid);
		pInfo.setNCurrencyID(lcurrencyid);
		try {
			pagerInfo = biz.queryPayNotice(pInfo, symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryRepayNotice(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long contractID = Long.valueOf(map.get("contractid") + "");
		long lcurrencyid = Long.valueOf(map.get("lcurrencyid") + "");
		long lofficeid = Long.valueOf(map.get("lofficeid") + "");
		String symbol = (String) map.get("symbol");

		QuestRepayNoticeInfo pInfo=new QuestRepayNoticeInfo();
		pInfo.setContractID(contractID);
		pInfo.setNOfficeID(lofficeid);
		pInfo.setNCurrencyID(lcurrencyid);
		try {
			pagerInfo = biz.queryRepayNotice(pInfo, symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	public PagerInfo queryAdjustUser(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long contractID = Long.valueOf(map.get("contractid") + "");
		try {
			pagerInfo = biz.queryAdjustUser(contractID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	public PagerInfo queryQuestContractPlanInfo(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		long contractID = Long.valueOf(map.get("lcontractid") + "");
		String txtcontractcode = map.get("txtcontractcode") + "";
		try {
			QuestContractPlanInfo queryInfo = new QuestContractPlanInfo();
			queryInfo.setContractID(contractID);
			queryInfo.setContractCode(txtcontractcode);
			
			pagerInfo = biz.queryQuestContractPlanInfo(queryInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	public PagerInfo queryExtend(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			QuestExtendInfo queryInfo = new QuestExtendInfo();

			String strTmp = (String)map.get("stypeids");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				queryInfo.setLoanTypeIDs(strTmp);
			}
			
			strTmp = (String)map.get("inputuser");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				queryInfo.setInputUserName(strTmp);
			}
			
			
			strTmp=(String)map.get("txtcontractcodebeg");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				String strContractCodeBeg = strTmp;
				queryInfo.setContractCodeBeg(strContractCodeBeg);
			}
			strTmp=(String)map.get("txtcontractcodeend");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				String strContractCodeEnd = strTmp;
				queryInfo.setContractCodeEnd(strContractCodeEnd);
			}

			strTmp=(String)map.get("lcontractidbeg");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				long lContractIDBeg = Long.parseLong(strTmp);
				queryInfo.setContractIDBeg(lContractIDBeg);
			}
			strTmp=(String)map.get("lstatusid");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				long lStatusID = Long.parseLong(strTmp);
				queryInfo.setStatusID(lStatusID);
			}
			////展期状态(复选)
			strTmp=(String)map.get("statusids");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				queryInfo.setStatusIDs(strTmp);
			}	
			strTmp=(String)map.get("lclientid");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				long lClientID = Long.parseLong(strTmp);
				queryInfo.setClientID(lClientID);
			}
			//借款单位由
			strTmp=(String)map.get("lclientidfrom");								
			long tmpLong;
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				try
				{
					tmpLong = Long.parseLong(strTmp);
				}
				catch (Exception e)
				{
					tmpLong = -1;
				}
				queryInfo.setBorrowClientIDFrom(tmpLong);
			}	
			//借款单位至
			strTmp=(String)map.get("lclientidto");								
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				try
				{
					tmpLong = Long.parseLong(strTmp);
				}
				catch (Exception e)
				{
					tmpLong = -1;
				}
				queryInfo.setBorrowClientIDTo(tmpLong);
			}
			//委托单位由
			strTmp=(String)map.get("wtclientidfrom");								
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				try
				{
					tmpLong = Long.parseLong(strTmp);
				}
				catch (Exception e)
				{
					tmpLong = -1;
				}
				queryInfo.setConsignClientIDFrom(tmpLong);
			}
			//委托单位至
			strTmp=(String)map.get("wtclientidto");								
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				try
				{
					tmpLong = Long.parseLong(strTmp);
				}
				catch (Exception e)
				{
					tmpLong = -1;
				}
				queryInfo.setConsignClientIDTo(tmpLong);
			}
			//利率开始
			strTmp=(String)map.get("minrate");								
			double tmpDouble;
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				try
				{
					strTmp=DataFormat.reverseFormatAmount(strTmp);
					tmpDouble = Double.parseDouble(strTmp);
				}
				catch (Exception e)
				{
					tmpDouble = 0;
				}
				queryInfo.setExtendRateFrom(tmpDouble);
			}
	
			//利率结束
			strTmp=(String)map.get("maxrate");
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				try
				{
					strTmp=DataFormat.reverseFormatAmount(strTmp);
					tmpDouble = Double.parseDouble(strTmp);
				}
				catch (Exception e)
				{
					tmpDouble = 0;
				}
				queryInfo.setExtendRateTo(tmpDouble);
			}	
			//录入开始时间
			Timestamp maxInputDate=null;
			strTmp=(String)map.get("sqdatefrom");								
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				maxInputDate=DataFormat.getDateTime(strTmp);
				queryInfo.setMinInputDate(maxInputDate);
			}
		
			//录入结束时间
			Timestamp minInputDate=null;
			strTmp=(String)map.get("sqdateto");								
			if ( (strTmp!=null)&&(strTmp.length()>0) )		
			{
				minInputDate=DataFormat.getDateTime(strTmp);
				queryInfo.setMaxInputDate(minInputDate);
			}								
					
			strTmp=(String)map.get("lconsignclientid");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				long lConsignClientID = Long.parseLong(strTmp);
				queryInfo.setConsignClientID(lConsignClientID);
			}
	
			strTmp=(String)map.get("dloanamountfrom");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				try
				{
					double dLoanAmountFrom = Double.valueOf(DataFormat.reverseFormatAmount(strTmp.trim())).doubleValue();
					queryInfo.setLoanAmountBeg(dLoanAmountFrom);
				}
				catch(Exception e)
				{
					;
				}
			}
			strTmp=(String)map.get("dloanamountto");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				try
				{
					double dLoanAmountTo = Double.valueOf(DataFormat.reverseFormatAmount(strTmp.trim())).doubleValue();
					queryInfo.setLoanAmountEnd(dLoanAmountTo);
				}
				catch(Exception e)
				{
					;
				}
			}
			strTmp=(String)map.get("dextendamountfrom");
			double dExtendAmountFrom;
			if (strTmp!= null&&!strTmp.equals(""))
			{
				try
				{
					dExtendAmountFrom=Double.valueOf(DataFormat.reverseFormatAmount(strTmp.trim())).doubleValue();
					queryInfo.setExtendAmountBeg(dExtendAmountFrom);
				}
				catch(Exception e)
				{
					;
				}
			}
			strTmp=(String)map.get("dextendamountto");
			if (strTmp!= null&&!strTmp.equals(""))
			{
				try
				{
					dExtendAmountFrom=Double.valueOf(DataFormat.reverseFormatAmount(strTmp.trim())).doubleValue();
					queryInfo.setExtendAmountEnd(dExtendAmountFrom);
				}
				catch(Exception e)
				{
					;
				}
			}
			queryInfo.setNOfficeId(Long.valueOf(map.get("lofficeid")+""));
			queryInfo.setNCurrencyId(Long.valueOf(map.get("lcurrencyid")+""));
			String Symbol = map.get("symbol")+"";
			pagerInfo = biz.queryExtend(queryInfo,Symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
	
	
	public PagerInfo queryContract1(Map map) throws Exception {
		PagerInfo pagerInfo = null;
		try {
			String tmp = "";
			QuestContractInfo qInfo = new QuestContractInfo();
			tmp = (String) map.get("ltypeid");
			long tmpLong;
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanTypeID(tmpLong);
			}

			// 出票人
			tmp = (String) map.get("discountclient");
			if ((tmp != null) && (tmp.length() > 0)) {
				// tmp = URLEncoder.encode(tmp);
				qInfo.setBillDrawer(tmp);
			}
			// 出票人ID
			tmp = (String) map.get("discountclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				// qInfo.setBillDrawer(tmp);
			}

			// 贷款类型(复选)
			tmp = (String) map.get("stypeids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setLoanTypeIDs(tmp);
			}
			// 贷款子类型（复选）
			tmp = (String) map.get("ssubtypeids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setLoanSubTypeIDs(tmp);
			}

			// 合同编号开始
			tmp = (String) map.get("contractcodefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setMinContractCode(tmp);
			}

			// 合同编号结束
			tmp = (String) map.get("contractcodeto");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setMaxContractCode(tmp);
			}

			// 申请书状态(贷款合同查询使用)
			tmp = (String) map.get("statusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setStatusIDs(tmp);
			}

			// 贴现多状态查询
			tmp = (String) map.get("_discountstatusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setStatusIDs(tmp);
			}

			// 合同风险状态(贷款合同查询使用)
			tmp = (String) map.get("venturelevelstatusids");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setRiskLevels(tmp);
			}

			// 申请书状态（贴现，担保合同查询使用）
			tmp = (String) map.get("lstatusid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setStatusID(tmpLong);
			}
			// 贴现申请单位

			tmp = (String) map.get("lapplyclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setMinborrowClientID(tmpLong);
			}

			tmp = (String) map.get("lapplyclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setMaxborrowClientID(tmpLong);
			}

			// 贴现汇票种类
			tmp = (String) map.get("tsdiscounttypeid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTsDiscountTypeID(tmpLong);
			}

			// 借款单位
			tmp = (String) map.get("lclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientID(tmpLong);
			}

			// 借款单位账号
			tmp = (String) map.get("clientaccount");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setBorrowAccount(tmp);
			}

			// 借款单位由
			tmp = (String) map.get("lclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientIDFrom(tmpLong);
			}
			// 借款单位至
			tmp = (String) map.get("lclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setBorrowClientIDTo(tmpLong);
			}

			// 客户分类
			tmp = (String) map.get("lclienttypeid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setLoanClientTypeID(tmpLong);
			}

			// 主管单位
			tmp = (String) map.get("cparentcorpid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setParentCorpID(tmpLong);
			}

			// 委托单位
			tmp = (String) map.get("wtclientid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientID(tmpLong);
			}

			// 委托单位由
			tmp = (String) map.get("wtclientidfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientIDFrom(tmpLong);
			}
			// 委托单位至
			tmp = (String) map.get("wtclientidto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setConsignClientIDTo(tmpLong);
			}

			// 委托单位账号
			tmp = (String) map.get("wtclientaccount");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setConsignAccount(tmp);
			}

			// 贷款金额开始
			tmp = (String) map.get("damountfrom");
			double tmpDouble;
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinLoanAmount(tmpDouble);
			}

			// 贷款金额结束
			tmp = (String) map.get("damountto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxLoanAmount(tmpDouble);
			}

			// 贷款余额开始
			tmp = (String) map.get("damountbalancefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinLoanBalanceAmount(tmpDouble);
			}

			// 贷款余额结束
			tmp = (String) map.get("damountbalanceto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxLoanBalanceAmount(tmpDouble);
			}

			// 实付金额开始
			tmp = (String) map.get("actualamountfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinCheckAmount(tmpDouble);
			}

			// 实付金额结束
			tmp = (String) map.get("actualamountto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxCheckAmount(tmpDouble);
			}

			// /////////////////////////////////////////////////////////
			// //贴现利率开始
			tmp = (String) map.get("minrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinRate(tmpDouble);
			}

			// //贴现利率结束
			tmp = (String) map.get("maxrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxRate(tmpDouble);
			}

			// 合同利率开始
			tmp = (String) map.get("mincontractrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setContractRateFrom(tmpDouble);
			}
			// 合同利率结束
			tmp = (String) map.get("maxcontractrate");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setContractRateTo(tmpDouble);
			}

			// /////////////////////////////////////////////////////////
			// 买方付息开始
			tmp = (String) map.get("payerratefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMinPayerRate(tmpDouble);
			}

			// 买方付息结束
			tmp = (String) map.get("payerrateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmp = DataFormat.reverseFormatAmount(tmp);
					tmpDouble = Double.parseDouble(tmp);
				} catch (Exception e) {
					tmpDouble = 0;
				}
				qInfo.setMaxPayerRate(tmpDouble);
			}

			// ///////////////////////////////////////////////////////////////

			// 贷款开始时间
			Timestamp maxLoanDate = null;
			tmp = (String) map.get("tsdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMinStartDate(maxLoanDate);
			}

			// 贷款结束时间
			Timestamp minLoanDate = null;
			tmp = (String) map.get("tsdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				minLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxStartDate(minLoanDate);
			}
			// /////////////////////////////////////////////////

			// added by xiong fei 2010/05/24 合同查询时的开始时间的起始时间
			// 合同开始时间的起始时间
			Timestamp tsStartDateFrom = null;
			tmp = (String) map.get("tsstartdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				tsStartDateFrom = DataFormat.getDateTime(tmp);
				qInfo.setMinStartDate(tsStartDateFrom);
			}

			// 合同开始时间的结束时间
			Timestamp tsStartDateTo = null;
			tmp = (String) map.get("tsstartdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				tsStartDateTo = DataFormat.getDateTime(tmp);
				qInfo.setMaxStartDate(tsStartDateTo);
			}
			// 担保方式
			String iscredit = "";// 信用
			tmp = (String) map.get("iscredit");
			if ((tmp != null) && (tmp.length() > 0)) {
				iscredit = tmp;
				qInfo.setIscredit(Long.valueOf(iscredit).longValue());
			}

			String isrecognizance = "";// 保证金
			tmp = (String) map.get("isrecognizance");
			if ((tmp != null) && (tmp.length() > 0)) {
				isrecognizance = tmp;
				qInfo.setIsrecognizance(Long.valueOf(isrecognizance)
						.longValue());
			}

			String isassure = "";// 保证
			tmp = (String) map.get("isassure");
			if ((tmp != null) && (tmp.length() > 0)) {
				isassure = tmp;
				qInfo.setIsassure(Long.valueOf(isassure).longValue());
			}

			String isimpawn = "";// 质押
			tmp = (String) map.get("isimpawn");
			if ((tmp != null) && (tmp.length() > 0)) {
				isimpawn = tmp;
				qInfo.setIsimpawn(Long.valueOf(isimpawn).longValue());
			}

			String ispledge = "";// 抵押
			tmp = (String) map.get("ispledge");
			if ((tmp != null) && (tmp.length() > 0)) {
				ispledge = tmp;
				qInfo.setIspledge(Long.valueOf(ispledge).longValue());
			}

			String isrepurchase = "";// 回购
			tmp = (String) map.get("isrepurchase");
			if ((tmp != null) && (tmp.length() > 0)) {
				isrepurchase = tmp;
				qInfo.setIsrepurchase(Long.valueOf(isrepurchase).longValue());
			}
			// 担保方式添加结束

			// 贷款结束时间的开始时间
			Timestamp maxEndLoanDate = null;
			tmp = (String) map.get("tsenddatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxEndLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMinEndDate(maxEndLoanDate);
			}

			// 贷款结束时间的结束时间
			Timestamp minEndLoanDate = null;
			tmp = (String) map.get("tsenddateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				minEndLoanDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxEndDate(minEndLoanDate);
			}
			// /////////////////////////////////////////////////
			// 贴现日期时间的开始时间
			Timestamp minDisccountDate = null;
			tmp = (String) map.get("tsdiscountdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				minDisccountDate = DataFormat.getDateTime(tmp);
				qInfo.setMinDiscountDate(minDisccountDate);
			}

			// 贴现日期时间的结束时间
			Timestamp maxDiscountDate = null;
			tmp = (String) map.get("tsdiscountdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				maxDiscountDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxDiscountDate(maxDiscountDate);
			}

			// 贴现录入日期时间的开始时间
			Timestamp MinDisccountInputDate = null;
			tmp = (String) map.get("tsinputdatefrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				MinDisccountInputDate = DataFormat.getDateTime(tmp);
				qInfo.setMinDisccountInputDate(MinDisccountInputDate);
			}

			// 贴现录入日期时间的结束时间
			Timestamp MaxDisccountInputDate = null;
			tmp = (String) map.get("tsinputdateto");
			if ((tmp != null) && (tmp.length() > 0)) {
				MaxDisccountInputDate = DataFormat.getDateTime(tmp);
				qInfo.setMaxDisccountInputDate(MaxDisccountInputDate);
			}

			// 贴现客户名称
			tmp = (String) map.get("discountclientname");
			if ((tmp != null) && (tmp.length() > 0)) {
				qInfo.setDiscountClientName(tmp);
			}
			// 贷款期限
			tmp = (String) map.get("lperiod");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIntervalNum(tmpLong);
			}

			// 贷款期限由
			tmp = (String) map.get("lperiodfrom");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setPeriodFrom(tmpLong);
			}
			// 贷款期限至
			tmp = (String) map.get("lperiodto");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setPeriodTo(tmpLong);
			}

			// 保证类型
			tmp = (String) map.get("selassuretype");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setAssureTypeID(tmpLong);
			}

			// 信用等级
			tmp = (String) map.get("selcreditlevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setCreditLevel(tmpLong);
			}

			// 是否技改贷款
			tmp = (String) map.get("selistechnical");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsTechnical(tmpLong);
			}

			// 是否循环
			tmp = (String) map.get("seliscircle");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsCircle(tmpLong);
			}

			// 是否股东
			tmp = (String) map.get("selpartner");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setIsPartner(tmpLong);
			}

			// 贷款风险状态
			tmp = (String) map.get("venturelevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setRiskLevel(tmpLong);
			}

			// 按地区分类
			tmp = (String) map.get("areatype");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTypeID1(tmpLong);
			}

			// 按行业分类1
			tmp = (String) map.get("industrytype1");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTypeID2(tmpLong);
			}

			// 按行业分类2
			tmp = (String) map.get("industrytype2");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setTypeID3(tmpLong);
			}

			// 管理人
			tmp = (String) map.get("linputuserid");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setInputUserID(tmpLong);
			}

			/** *************添加国机的变更 2003-3-30 qqgd************** */
			tmp = (String) map.get("showend"); // 标示是否显示结束状态的合同
			if ((tmp != null) && (tmp.length() > 0)) {
				if (tmp.equals("on"))
					qInfo.setShowEnd(false);
				else
					qInfo.setShowEnd(true);
			}
			tmp = (String) map.get("isgather"); // 标示是否显示汇总信息
			if ((tmp != null) && (tmp.length() > 0)) {
				if (tmp.equals("on"))
					qInfo.setGather(true);

			}
			/** ***************end of qqgd's adding******************** */

			tmp = (String) map.get("querylevel");
			if ((tmp != null) && (tmp.length() > 0)) {
				String strQueryLevel = tmp;
				qInfo.setQueryLevel(strQueryLevel);
			}

			tmp = (String) map.get("straction");
			if ((tmp != null) && (tmp.length() > 0)) {
				String strAction = tmp;
				if (strAction.equals("TX"))
					qInfo.setQueryPurpose(QuestContractInfo.TX);
				else if (strAction.equals("ZTX"))
					qInfo.setQueryPurpose(QuestContractInfo.ZTX);
				else if (strAction.equals("DB"))
					qInfo.setQueryPurpose(QuestContractInfo.DB);
				else if (strAction.equals("RZZL"))
					qInfo.setQueryPurpose(QuestContractInfo.RZZL);
				else
					qInfo.setQueryPurpose(QuestContractInfo.LOAN);
			}

			// 客户属性
			tmp = (String) map.get("clienttypeid1");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID1(tmpLong);
			}
			tmp = (String) map.get("clienttypeid2");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID2(tmpLong);
			}
			tmp = (String) map.get("clienttypeid3");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID3(tmpLong);
			}
			tmp = (String) map.get("clienttypeid4");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID4(tmpLong);
			}
			tmp = (String) map.get("clienttypeid5");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID5(tmpLong);
			}
			tmp = (String) map.get("clienttypeid6");
			if ((tmp != null) && (tmp.length() > 0)) {
				try {
					tmpLong = Long.parseLong(tmp);
				} catch (Exception e) {
					tmpLong = -1;
				}
				qInfo.setClientTypeID6(tmpLong);
			}

			long lcurrencyid = Long.valueOf(map.get("lcurrencyid") + "");
			long lofficeid = Long.valueOf(map.get("lofficeid") + "");
			// 余额日期
			Timestamp tsBalanceDate = null;
			tmp = (String) map.get("tsbalancedate");
			if ((tmp != null) && (tmp.length() > 0)) {
				tsBalanceDate = DataFormat.getDateTime(tmp);
			} else// 结算开机日期
			{
				tsBalanceDate = Env.getSystemDate(lofficeid, lcurrencyid);
			}
			qInfo.setBalanceDate(tsBalanceDate);

			String symbol = (String) map.get("symbol");
			pagerInfo = biz.queryContract1(qInfo, symbol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return pagerInfo;
	}
}

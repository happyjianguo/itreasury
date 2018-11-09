package com.iss.itreasury.loan.query.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.loan.query.dataentity.QueryLoanApplyInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractPlanInfo;
import com.iss.itreasury.loan.query.dataentity.QuestExtendInfo;
import com.iss.itreasury.loan.query.dataentity.QuestPayNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QuestRepayNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;

public class QueryOperationDao {

	public String queryLoanApply(QueryLoanApplyInfo qInfo) {
		String sql = null;
		if (qInfo.getQueryPurpose() == QueryLoanApplyInfo.LOAN) {

			sql = getLoanApplySQL(qInfo);

		} else if (qInfo.getQueryPurpose() == QueryLoanApplyInfo.DB) {

			sql = getDBLoanApplySQL(qInfo);

		} else if (qInfo.getQueryPurpose() == QueryLoanApplyInfo.RZZL) {

			sql = getRZZLLoanApplySQL(qInfo);

		}
		return sql;
	}

	private String getLoanApplySQL(QueryLoanApplyInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		// select
		sql[0] = " w.nacceptpotypeid as tsDiscountTypeID,l.ID,l.nTypeID as TypeID,l.nStatusID as statusID,l.sApplyCode as applyCode,"
				+ " c.Name as borrowClientName,c2.Name as consignClientName,l.mLoanAmount as loanAmount,"
				+ " l.nIntervalNum as intervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
				+ " l.nBankInterestID as bankInterestID, l.mAdjustRate as adjustRate ,l.mStaidAdjustRate as staidAdjustRate ,"
				+ " DECODE(l.nTypeID,"
				+ LOANConstant.LoanType.TX
				+ ",mDiscountRate,"
				+ LOANConstant.LoanType.ZTX
				+ ",mDiscountRate,mInterestRate) as interestRate ,l.assureChargeRate as assureChargeRate,lt.name as subTypeName,"
				+ " l.dtStartDate as startDate,l.dtEndDate as endDate, l.dtInputDate as inputDate ";

		// from
		sql[1] = " (select DISTINCT nloanid,nacceptpotypeid,nofficeid,ncurrencyid from loan_discountformbill) w ,loan_loanform l,Client_clientinfo c,Client_clientinfo c2,userInfo u,userInfo u2,loan_loantypesetting lt";

		// where
		sql[2] = "  w.nloanid(+)=l.id and c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID and lt.id=l.nsubTypeID and lt.loantypeid=l.ntypeid";

		/** ********处理查询条件************ */
		if (qInfo.getLoanTypes() == null || qInfo.getLoanTypes().equals("")
				|| qInfo.getLoanTypes().equals("-1")) {
			sqlBuf
					.append(" and l.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and "
							+ "  a.loantypeid  in ("
							+ LOANConstant.LoanType.WT
							+ ","
							+ LOANConstant.LoanType.YT
							+ ","
							+ LOANConstant.LoanType.ZY
							+ ","
							+ LOANConstant.LoanType.TX
							+ ") and a.id=b.subloantypeid "
							+ " and b.currencyid="
							+ qInfo.getCurrencyID()
							+ " and b.officeid=" + qInfo.getOfficeID() + ")");
		}
		// 贷款种类（复选）add by wmzheng at 2010-10-13
		if (qInfo.getLoanTypes() != null && qInfo.getLoanTypes().length() > 0)
			sqlBuf
					.append(" and l.nsubTypeID in (" + qInfo.getLoanTypes()
							+ ")");

		// 如果是转贴现（查询条件增加）
		if (qInfo.getLoanType() > 0
				&& qInfo.getLoanType() == LOANConstant.LoanType.ZTX) {
			if (qInfo.getInOrOut() > 0)
				sqlBuf.append(" and l.NINOROUT=" + qInfo.getInOrOut());
			if (qInfo.getTransDiscountType() > 0)
				sqlBuf.append(" and l.NDISCOUNTTYPEID="
						+ qInfo.getTransDiscountType());
			if (qInfo.getTransDiscountTerm() > 0)
				sqlBuf.append(" and l.DTENDDATE - l.DTSTARTDATE ="
						+ qInfo.getTransDiscountTerm());
		}
		if (qInfo.getTsDiscountTypeID() > 0) {
			System.out.println("TsDiscountTypeID="
					+ qInfo.getTsDiscountTypeID());
			sqlBuf.append(" and w.nacceptpotypeid="
					+ qInfo.getTsDiscountTypeID() + " and l.ntypeid="
					+ LOANConstant.LoanType.TX);
		}
		// 申请书编号开始
		if ((qInfo.getMaxApplyCode() != null)
				&& (qInfo.getMaxApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode<='" + qInfo.getMaxApplyCode()
					+ "'");

		// 申请书编号结束
		if ((qInfo.getMinApplyCode() != null)
				&& (qInfo.getMinApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode>='" + qInfo.getMinApplyCode()
					+ "'");

		// 申请书状态
		if ((qInfo.getLoanStatusID() > 0)
				|| (qInfo.getLoanStatusID() == LOANConstant.LoanStatus.REFUSE))
			sqlBuf.append(" and l.nStatusID=" + qInfo.getLoanStatusID());

		long loanStatusVal[] = LOANConstant.LoanStatus.getAllCode(qInfo
				.getOfficeID(), qInfo.getCurrencyID());
		String loanTypeList = "";
		for (int i = 0; i < loanStatusVal.length; i++) {

			if (loanStatusVal[i] == LOANConstant.LoanStatus.REFUSE) {
				continue;
			}
			loanTypeList += loanStatusVal[i];
			if ((loanStatusVal.length - i) > 1) {

				loanTypeList += ",";
			}

		}

		// 申请书状态(复选) add by wmzheng at 2010-10-13
		if (qInfo.getLoanStatusIDs() == null
				|| qInfo.getLoanStatusIDs().equals("-1")) {

			sqlBuf.append(" and l.nStatusID in (" + loanTypeList + ")");

		} else {
			sqlBuf.append(" and l.nStatusID in (" + qInfo.getLoanStatusIDs()
					+ ")");
		}
		// 借款单位
		if (qInfo.getBorrowClientID() > 0)
			sqlBuf
					.append(" and l.nBorrowClientID="
							+ qInfo.getBorrowClientID());

		// 借款单位(复选) add by wmzheng at 2010-10-13
		if (qInfo.getBorrowClientIDFrom() > 0)
			sqlBuf.append(" and l.nBorrowClientID >= "
					+ qInfo.getBorrowClientIDFrom());
		if (qInfo.getBorrowClientIDTo() > 0)
			sqlBuf.append(" and l.nBorrowClientID <= "
					+ qInfo.getBorrowClientIDTo());

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			sqlBuf.append(" and c.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// sqlBuf.append(" and c.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			sqlBuf.append(" and c.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位
		if (qInfo.getConsignClientID() > 0)
			sqlBuf.append(" and l.nConsignClientID="
					+ qInfo.getConsignClientID());

		// 委托单位(复选) add by wmzheng at 2010-10-13
		if (qInfo.getConsignClientIDFrom() > 0)
			sqlBuf.append(" and l.nConsignClientID >= "
					+ qInfo.getConsignClientIDFrom() + " and l.ntypeid="
					+ LOANConstant.LoanType.WT);
		if (qInfo.getConsignClientIDTo() > 0)
			sqlBuf.append(" and l.nConsignClientID <= "
					+ qInfo.getConsignClientIDTo() + " and l.ntypeid="
					+ LOANConstant.LoanType.WT);
		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			sqlBuf.append(" and c2.sAccount='" + qInfo.getConsignAccount()
					+ "'");

		// 贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		// 贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		// 执行利率 add by wmzheng at 2010-10-13
		if (qInfo.getExecuteRateFrom() > 0)
			sqlBuf.append(" and DECODE(l.nTypeID, " + LOANConstant.LoanType.TX
					+ ", l.mDiscountRate, " + LOANConstant.LoanType.ZTX
					+ ", l.mDiscountRate, l.mInterestRate) >= "
					+ DataFormat.formatRate(qInfo.getExecuteRateFrom()));
		if (qInfo.getExecuteRateTo() > 0)
			sqlBuf.append(" and DECODE(l.nTypeID, " + LOANConstant.LoanType.TX
					+ ", l.mDiscountRate, " + LOANConstant.LoanType.ZTX
					+ ", l.mDiscountRate, l.mInterestRate) <= "
					+ DataFormat.formatRate(qInfo.getExecuteRateTo()));

		// 贷款日期开始
		if (qInfo.getMaxLoanDate() != null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxLoanDate())
					+ "','yyyy-mm-dd') ");

		// 贷款日期结束
		if (qInfo.getMinLoanDate() != null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinLoanDate())
					+ "','yyyy-mm-dd') ");

		// 贷款期限
		if (qInfo.getIntervalNum() > 0)
			sqlBuf.append(" and l.nIntervalNum=" + qInfo.getIntervalNum());

		// 期限 add by wmzheng at 2010-10-13
		if (qInfo.getPeriodFrom() > 0)
			sqlBuf.append(" and l.nIntervalNum >= " + qInfo.getPeriodFrom());
		if (qInfo.getPeriodTo() > 0)
			sqlBuf.append(" and l.nIntervalNum <= " + qInfo.getPeriodTo());

		// 提交日期开始
		if (qInfo.getMaxInputDate() != null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxInputDate())
					+ "','yyyy-mm-dd') ");

		// 提交日期结束
		if (qInfo.getMinInputDate() != null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinInputDate())
					+ "','yyyy-mm-dd') ");

		// 保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			sqlBuf.append(" and l.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			sqlBuf.append(" and l.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			sqlBuf.append(" and l.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			sqlBuf.append(" and l.nIsPledge=1");

		// 信用等级
		if (qInfo.getCreditLevel() > 0)
			sqlBuf.append(" and c.NCREDITLEVELID=" + qInfo.getCreditLevel());

		// 是否股东
		if (qInfo.getIsPartner() > 0)
			sqlBuf.append(" and c.NISPARTNER=" + qInfo.getIsPartner());

		// 是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			sqlBuf.append(" and l.NISTECHNICAL=" + qInfo.getIsTechnical());

		// 是否循环贷款
		if (qInfo.getIsCircle() > 0)
			sqlBuf.append(" and l.NISCIRCLE=" + qInfo.getIsCircle());

		// 管理人
		if (qInfo.getInputUserID() > 0)
			sqlBuf.append(" and l.nInputUserID=" + qInfo.getInputUserID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			sqlBuf.append(" and l.nCurrencyID=" + qInfo.getCurrencyID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			sqlBuf.append(" and l.nOfficeID=" + qInfo.getOfficeID());

		return "select " + sql[0] + " from " + sql[1] + " where " + sql[2]
				+ sqlBuf.toString();

	}

	private String getDBLoanApplySQL(QueryLoanApplyInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		// select
		sql[0] = " l.ID,l.nTypeID as TypeID,l.nStatusID as statusID,l.sApplyCode as applyCode,"
				+ " c.Name as borrowClientName,c2.Name as consignClientName,l.mLoanAmount as loanAmount,"
				+ " l.nIntervalNum as intervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
				+ " l.nBankInterestID as bankInterestID, l.mAdjustRate as adjustRate ,l.mStaidAdjustRate as staidAdjustRate ,"
				+ "l.assureChargeRate as assureChargeRate,lt.name as subTypeName,"
				+ " l.dtStartDate as startDate,l.dtEndDate as endDate, l.dtInputDate as inputDate ";

		// from
		sql[1] = " (select DISTINCT nloanid,nacceptpotypeid,nofficeid,ncurrencyid from loan_discountformbill) w ,loan_loanform l,Client_clientinfo c,Client_clientinfo c2,userInfo u,userInfo u2,loan_loantypesetting lt";

		// where
		sql[2] = "  w.nloanid(+)=l.id and c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID and lt.id=l.nsubTypeID and lt.loantypeid=l.ntypeid and nTypeID="
				+ LOANConstant.LoanType.DB;

		// 申请书编号开始
		if ((qInfo.getMaxApplyCode() != null)
				&& (qInfo.getMaxApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode<='" + qInfo.getMaxApplyCode()
					+ "'");

		// 申请书编号结束
		if ((qInfo.getMinApplyCode() != null)
				&& (qInfo.getMinApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode>='" + qInfo.getMinApplyCode()
					+ "'");

		// 申请书状态
		if ((qInfo.getLoanStatusID() > 0)
				|| (qInfo.getLoanStatusID() == LOANConstant.LoanStatus.REFUSE))
			sqlBuf.append(" and l.nStatusID=" + qInfo.getLoanStatusID());

		// 申请书状态(复选) add by wmzheng at 2010-10-13
		if (qInfo.getLoanStatusIDs() != null
				&& qInfo.getLoanStatusIDs().length() > 0)
			sqlBuf.append(" and l.nStatusID in (" + qInfo.getLoanStatusIDs()
					+ ")");

		// 借款单位
		if (qInfo.getBorrowClientID() > 0)
			sqlBuf
					.append(" and l.nBorrowClientID="
							+ qInfo.getBorrowClientID());

		// 借款单位(复选) add by wmzheng at 2010-10-13
		if (qInfo.getBorrowClientIDFrom() > 0)
			sqlBuf.append(" and l.nBorrowClientID >= "
					+ qInfo.getBorrowClientIDFrom());
		if (qInfo.getBorrowClientIDTo() > 0)
			sqlBuf.append(" and l.nBorrowClientID <= "
					+ qInfo.getBorrowClientIDTo());

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			sqlBuf.append(" and c.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// sqlBuf.append(" and c.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			sqlBuf.append(" and c.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			sqlBuf.append(" and c2.sAccount='" + qInfo.getConsignAccount()
					+ "'");

		// 贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		// 贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		// 管理人
		if (qInfo.getInputUserID() > 0)
			sqlBuf.append(" and l.nInputUserID=" + qInfo.getInputUserID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			sqlBuf.append(" and l.nCurrencyID=" + qInfo.getCurrencyID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			sqlBuf.append(" and l.nOfficeID=" + qInfo.getOfficeID());

		sql[2] = sql[2] + sqlBuf.toString();

		return "select " + sql[0] + " from " + sql[1] + " where " + sql[2];

	}

	private String getRZZLLoanApplySQL(QueryLoanApplyInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		// select
		sql[0] = " w.nacceptpotypeid as tsDiscountTypeID,l.ID,l.nTypeID as TypeID,l.nStatusID as statusID,l.sApplyCode as applyCode,"
				+ " c.Name as borrowClientName,c2.Name as consignClientName,l.mLoanAmount as loanAmount,"
				+ " l.nIntervalNum as intervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
				+ " l.nBankInterestID as bankInterestID, l.mAdjustRate as adjustRate ,l.mStaidAdjustRate as staidAdjustRate ,"
				+ " DECODE(l.nTypeID,"
				+ LOANConstant.LoanType.TX
				+ ",mDiscountRate,"
				+ LOANConstant.LoanType.ZTX
				+ ",mDiscountRate,mInterestRate) as interestRate ,l.assureChargeRate as assureChargeRate,"
				+ " l.dtStartDate as startDate,l.dtEndDate as endDate, l.dtInputDate as inputDate ";

		// from
		sql[1] = " (select DISTINCT nloanid,nacceptpotypeid,nofficeid,ncurrencyid from loan_discountformbill) w ,loan_loanform l,Client_clientinfo c,Client_clientinfo c2,userInfo u,userInfo u2";

		// where
		sql[2] = "  w.nloanid(+)=l.id and c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID  and l.ntypeid= "
				+ LOANConstant.LoanType.RZZL;

		// 申请书编号开始
		if ((qInfo.getMaxApplyCode() != null)
				&& (qInfo.getMaxApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode<='" + qInfo.getMaxApplyCode()
					+ "'");

		// 申请书编号结束
		if ((qInfo.getMinApplyCode() != null)
				&& (qInfo.getMinApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode>='" + qInfo.getMinApplyCode()
					+ "'");

		// 申请书状态
		if ((qInfo.getLoanStatusID() > 0)
				|| (qInfo.getLoanStatusID() == LOANConstant.LoanStatus.REFUSE))
			sqlBuf.append(" and l.nStatusID=" + qInfo.getLoanStatusID());

		// 申请书状态(复选) add by wmzheng at 2010-10-13
		if (qInfo.getLoanStatusIDs() != null
				&& qInfo.getLoanStatusIDs().length() > 0)
			sqlBuf.append(" and l.nStatusID in (" + qInfo.getLoanStatusIDs()
					+ ")");

		// 借款单位
		if (qInfo.getBorrowClientID() > 0)
			sqlBuf
					.append(" and l.nBorrowClientID="
							+ qInfo.getBorrowClientID());

		// 借款单位(复选) add by wmzheng at 2010-10-13
		if (qInfo.getBorrowClientIDFrom() > 0)
			sqlBuf.append(" and l.nBorrowClientID >= "
					+ qInfo.getBorrowClientIDFrom());
		if (qInfo.getBorrowClientIDTo() > 0)
			sqlBuf.append(" and l.nBorrowClientID <= "
					+ qInfo.getBorrowClientIDTo());

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			sqlBuf.append(" and c.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// sqlBuf.append(" and c.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			sqlBuf.append(" and c.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位
		if (qInfo.getConsignClientID() > 0)
			sqlBuf.append(" and l.nConsignClientID="
					+ qInfo.getConsignClientID());

		// 委托单位(复选) add by wmzheng at 2010-10-13
		if (qInfo.getConsignClientIDFrom() > 0)
			sqlBuf.append(" and l.nConsignClientID >= "
					+ qInfo.getConsignClientIDFrom());
		if (qInfo.getConsignClientIDTo() > 0)
			sqlBuf.append(" and l.nConsignClientID <= "
					+ qInfo.getConsignClientIDTo());

		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			sqlBuf.append(" and c2.sAccount='" + qInfo.getConsignAccount()
					+ "'");

		// 贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		// 贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		// 执行利率 add by wmzheng at 2010-10-13
		if (qInfo.getExecuteRateFrom() > 0)
			sqlBuf.append(" and DECODE(l.nTypeID, " + LOANConstant.LoanType.TX
					+ ", l.mDiscountRate, " + LOANConstant.LoanType.ZTX
					+ ", l.mDiscountRate, l.mInterestRate) >= "
					+ DataFormat.formatRate(qInfo.getExecuteRateFrom()));
		if (qInfo.getExecuteRateTo() > 0)
			sqlBuf.append(" and DECODE(l.nTypeID, " + LOANConstant.LoanType.TX
					+ ", l.mDiscountRate, " + LOANConstant.LoanType.ZTX
					+ ", l.mDiscountRate, l.mInterestRate) <= "
					+ DataFormat.formatRate(qInfo.getExecuteRateTo()));

		// 贷款日期开始
		if (qInfo.getMaxLoanDate() != null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxLoanDate())
					+ "','yyyy-mm-dd') ");

		// 贷款日期结束
		if (qInfo.getMinLoanDate() != null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinLoanDate())
					+ "','yyyy-mm-dd') ");

		// 贷款期限
		if (qInfo.getIntervalNum() > 0)
			sqlBuf.append(" and l.nIntervalNum=" + qInfo.getIntervalNum());

		// 期限 add by wmzheng at 2010-10-13
		if (qInfo.getPeriodFrom() > 0)
			sqlBuf.append(" and l.nIntervalNum >= " + qInfo.getPeriodFrom());
		if (qInfo.getPeriodTo() > 0)
			sqlBuf.append(" and l.nIntervalNum <= " + qInfo.getPeriodTo());

		// 提交日期开始
		if (qInfo.getMaxInputDate() != null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxInputDate())
					+ "','yyyy-mm-dd') ");

		// 提交日期结束
		if (qInfo.getMinInputDate() != null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinInputDate())
					+ "','yyyy-mm-dd') ");

		// 保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			sqlBuf.append(" and l.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			sqlBuf.append(" and l.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			sqlBuf.append(" and l.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			sqlBuf.append(" and l.nIsPledge=1");

		// 信用等级
		if (qInfo.getCreditLevel() > 0)
			sqlBuf.append(" and c.NCREDITLEVELID=" + qInfo.getCreditLevel());

		// 是否股东
		if (qInfo.getIsPartner() > 0)
			sqlBuf.append(" and c.NISPARTNER=" + qInfo.getIsPartner());

		// 是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			sqlBuf.append(" and l.NISTECHNICAL=" + qInfo.getIsTechnical());

		// 是否循环贷款
		if (qInfo.getIsCircle() > 0)
			sqlBuf.append(" and l.NISCIRCLE=" + qInfo.getIsCircle());

		// 管理人
		if (qInfo.getInputUserID() > 0)
			sqlBuf.append(" and l.nInputUserID=" + qInfo.getInputUserID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			sqlBuf.append(" and l.nCurrencyID=" + qInfo.getCurrencyID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			sqlBuf.append(" and l.nOfficeID=" + qInfo.getOfficeID());

		sql[2] = sql[2] + sqlBuf.toString();

		return "select " + sql[0] + " from " + sql[1] + " where " + sql[2];
	}

	public String queryContract(QuestContractInfo qInfo) {
		String[] sql = null;
		if (qInfo.getQueryPurpose() == QuestContractInfo.TX) {
			sql = getContractSQL_wlx(qInfo);// 贴现SQL
		} else if (qInfo.getQueryPurpose() == QuestContractInfo.RZZL) {
			sql = getRZZLContractSQL(qInfo);// 融资租赁SQL
		} else if (qInfo.getQueryPurpose() == QuestContractInfo.DB) {

			sql = getDBContractSQL(qInfo);// 担保SQL

		} else {

			sql = getContractSQL(qInfo);// 贷款合同查询

		}
		/** **********为了国机的汇总而加入 2003-3-30 qqgd ************ */
		if (qInfo.getGather()) {
			String strTmp = "(select \n" + sql[0] + " \nfrom " + sql[1]
					+ " \nwhere " + sql[2] + ") sz";
			sql[0] = "*";
			sql[1] = " (select borrowClientName,sum(nvl(examineAmount,0)) as examineAmount,sum(nvl(balance,0)) as balance, "
					+ " sum(nvl(overdueAmount,0)) as overdueAmount,sum(nvl(punishInterest,0)) as punishInterest, "
					+ " sum(nvl(discountPurchaserInterest,0)) as discountPurchaserInterest,"
					+ " sum(nvl(discountInterest,0)) as discountInterest"
					+ " from " + strTmp + " group by sz.borrowClientName ) ";
			sql[2] = "";
		}

		return "select " + sql[0] + " from " + sql[1]
				+ (sql[2].equals("") ? "" : (" WHERE " + sql[2]));
	}

	public String[] getContractSQL_wlx(QuestContractInfo qInfo) {
		System.out.println("welcome to getContractSQL_wlx");
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// select
		// 修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		// modified by fxzhang 2012-6-6
		// 对于初始化数据，loan_DiscountFormBill中没有相关记录，可直接取合同中的
		sql[0] += " distinct nvl(w.NACCEPTPOTYPEID, (case when(c.nbankacceptpo > 0) then 1 else  2 end)) as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"
				// sql[0] +=" distinct w.NACCEPTPOTYPEID as
				// tsDiscountTypeID,c.ID as contractID,c.nTypeID as
				// loanTypeID,c.sContractCode as contractCode,c.NINOROUT as
				// inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"
				+ " c1.Name as borrowClientName,c2.Name as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,c.mPurchaserAmount as discountPurchaserInterest,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,c.DTINPUTDATE as InputDate,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest1,"
				+ " d.sApplyCode as applyCode,lp.balance,c3.Name as discountClientName"
				+ ", a.overdueAmount as overdueAmount"
				+ ", b.punishInterest as punishInterest";

		// from
		// sql[1] = "loan_DiscountCredence w,Loan_DiscountContractBill
		// m,loan_contractForm c,client c1,client c2,client c3,userInfo
		// u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		sql[1] = "(select distinct nLoanID,NACCEPTPOTYPEID from loan_DiscountFormBill) w ,loan_contractForm c,client_clientinfo c1,client_clientinfo c2,client_clientinfo c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		// 从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		// 胡志强修改(2004-03-09)
		sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client_clientinfo c1,client_clientinfo c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + LOANConstant.ContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client_clientinfo c1,client_clientinfo c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; // 上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b";

		// where

		// 胡志强修改(2004-03-09)
		sql[2] = " c.nLoanId = w.nLoanID(+)";
		// sql[2] += " and m.nAcceptpotypeID =w.ndrafttypeid(+)";
		// sql[2] += " and w.ndrafttypeid>0";
		sql[2] += " and nvl(w.NACCEPTPOTYPEID,9999) > 0 ";
		// sql[2] += " and m.ncontractid=w.ncontractid(+) ";
		if (qInfo.getQueryPurpose() == QuestContractInfo.TX) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.TX;
			if (qInfo.getMinRate() > 0) {
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if (qInfo.getMaxRate() > 0) {
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			/*
			 * //贴现客户名称 if ((qInfo.getDiscountClientName() != null) &&
			 * (qInfo.getDiscountClientName().length() > 0)) { buf.append(" and
			 * c.discountClientName like '%" + qInfo.getDiscountClientName() +
			 * "%'"); }
			 */

			// 贴现汇票种类
			if (qInfo.getTsDiscountTypeID() > 0) {
				System.out.println("TsDiscountTypeID="
						+ qInfo.getTsDiscountTypeID());
				buf.append(" and w.NACCEPTPOTYPEID="
						+ qInfo.getTsDiscountTypeID());
			}
		}

		else if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.ZTX;
			if (qInfo.getMinRate() > 0) {
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if (qInfo.getMaxRate() > 0) {
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}

		} else {
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.TX;
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.ZTX;
			if (qInfo.getMinRate() > 0) {
				sql[2] += " and y.lateRate >= " + qInfo.getMinRate();
			}
			if (qInfo.getMaxRate() > 0) {
				sql[2] += " and y.lateRate <= " + qInfo.getMaxRate();
			}
		}
		sql[2] += " and                                                 ";
		sql[2] += " c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID"
				+ " and lp.nContractID(+) =c.id";
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+)";

		// （上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose() == QuestContractInfo.DB) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		} else {
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.DB;
		}

		// 贷款种类
		if (qInfo.getLoanTypeID() > 0)
			buf.append(" and c.nTypeID=" + qInfo.getLoanTypeID());
		if (qInfo.getLoanTypeID() <= 0)
			buf.append(" and c.nTypeID<>" + LOANConstant.LoanType.RZZL);

		// 如果是转贴现（查询条件增加）
		if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX) {
			if (qInfo.getInOrOut() > 0)
				buf.append(" and c.NINOROUT=" + qInfo.getInOrOut());
			if (qInfo.getTransDiscountType() > 0)
				buf.append(" and c.NDISCOUNTTYPEID="
						+ qInfo.getTransDiscountType());
			if (qInfo.getTransDiscountTerm() > 0)
				buf.append(" and c.DTENDDATE - c.DTSTARTDATE ="
						+ qInfo.getTransDiscountTerm());
		}

		// 合同编号开始
		if (qInfo.getMinContractCode() != null
				&& qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode()
					+ "'");

		// 合同编号结束
		if (qInfo.getMaxContractCode() != null
				&& qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode()
					+ "'");

		/** *************添加国机的变更 2003-3-30 qqgd************** */
		// 合同状态
		if (!qInfo.isShowEnd()) {
			buf.append(" and c.nStatusID<>"
					+ LOANConstant.ContractStatus.FINISH);
		}

		/** **********为了国机加的判断，限制状态 2004-4-23 qqgd ******** */
		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo
				.getOfficeID(), qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++) {
			// 去掉已提交
			if (contractStatusVal[i] == LOANConstant.ContractStatus.SUBMIT) {
				continue;
			}
			// 去掉呆滞
			if (contractStatusVal[i] == LOANConstant.ContractStatus.DELAYDEBT) {
				continue;
			}
			// 去掉呆账
			if (contractStatusVal[i] == LOANConstant.ContractStatus.BADDEBT) {
				continue;
			}
			// 去掉已拒绝
			if (contractStatusVal[i] == LOANConstant.ContractStatus.REFUSE) {
				continue;
			}

			// 去掉已逾期
			if (contractStatusVal[i] == LOANConstant.ContractStatus.EXTEND) {
				continue;
			}
			// 去掉已展期
			if (contractStatusVal[i] == LOANConstant.ContractStatus.OVERDUE) {
				continue;
			}
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ",";
		}
		if (qInfo.getStatusIDs() == null || qInfo.getStatusIDs().equals("")
				|| qInfo.getStatusIDs().trim().equals("-1")) {
			buf.append(" and c.nStatusID in (" + strStatus + ")");
		} else {
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs() + ")");

		}

		/*
		 * if (qInfo.getStatusID() > 0) {
		 * System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
		 * buf.append(" and c.nStatusID=" + qInfo.getStatusID()); }
		 */
		// minzhao20090505修改将合同状态增加为多状态查询
		// 出票人
		if (qInfo.getBillDrawer() != null && qInfo.getBillDrawer().length() > 0) {
			String test = qInfo.getBillDrawer();

			try {
				test = new String(test.getBytes("ISO-8859-1"), "GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buf.append(" and c.DISCOUNTCLIENTNAME like '%" + test + "%'");
		}
		// 借款单位
		if (qInfo.getBorrowClientID() > 0) {
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		}
		// 贴现申请单位结束
		if (qInfo.getMaxborrowClientID() > 0) {
			buf.append(" and c.nBorrowClientID <="
					+ qInfo.getMaxborrowClientID());
		}

		// 贴现申请单位开始
		if (qInfo.getMinborrowClientID() > 0) {
			buf.append(" and c.nBorrowClientID >="
					+ qInfo.getMinborrowClientID());
		}

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// buf.append(" and c1.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());

		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		// (贴现)贷款金额结束
		if (qInfo.getMaxLoanAmount() > 0) {
			buf.append(" and c.mExamineAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));
		}
		// (贴现)贷款金额开始
		if (qInfo.getMinLoanAmount() > 0) {
			buf.append(" and c.mExamineAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));
		}

		// 贴现实付金额结束
		if (qInfo.getMaxCheckAmount() > 0) {
			buf.append(" and c.mCheckAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxCheckAmount()));
		}
		// 贴现实付金额开始
		if (qInfo.getMinCheckAmount() > 0) {
			buf.append(" and c.mCheckAmount>="
					+ DataFormat.formatAmount(qInfo.getMinCheckAmount()));
		}

		// 贷款日期开始
		if (qInfo.getMaxStartDate() != null) {
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxStartDate())
					+ "','yyyy-mm-dd') ");
		}
		// 贷款日期结束
		if (qInfo.getMinStartDate() != null) {
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinStartDate())
					+ "','yyyy-mm-dd') ");
		}
		// 贷款结束日期结束
		if (qInfo.getMaxEndDate() != null) {
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxEndDate())
					+ "','yyyy-mm-dd') ");
		}
		// 贷款结束日期开始
		if (qInfo.getMinEndDate() != null) {
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinEndDate())
					+ "','yyyy-mm-dd') ");
		}
		// 贴现日期结束
		if (qInfo.getMaxDiscountDate() != null) {
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxDiscountDate())
					+ "','yyyy-mm-dd') ");
		}

		// 贴现日期开始
		if (qInfo.getMinDiscountDate() != null) {
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinDiscountDate())
					+ "','yyyy-mm-dd') ");
		}
		// 录入日期结束
		if (qInfo.getMaxDisccountInputDate() != null) {
			buf.append(" and TRUNC(c.dtInputDate) <= To_Date('"
					+ DataFormat
							.getDateString(qInfo.getMaxDisccountInputDate())
					+ "','yyyy-mm-dd') ");
		}
		// 录入日期开始
		if (qInfo.getMinDisccountInputDate() != null) {
			buf.append(" and TRUNC(c.dtInputDate) >= To_Date('"
					+ DataFormat
							.getDateString(qInfo.getMinDisccountInputDate())
					+ "','yyyy-mm-dd') ");
		}
		// 买方付息结束
		if (qInfo.getMaxPayerRate() > 0) {
			buf.append(" and c.mPurchaserAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxPayerRate()));
		}

		// 买方付息开始
		if (qInfo.getMinPayerRate() > 0) {

			buf.append(" and c.mPurchaserAmount>="
					+ DataFormat.formatAmount(qInfo.getMinPayerRate()));

		}

		// 贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum=" + qInfo.getIntervalNum());

		// 保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");

		// 信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		// 是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		// 是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		// 是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		// 贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());

		// 按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		// 按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		// 按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		// 管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());

		sql[2] = sql[2] + buf.toString();

		sql[3] = "";

		return sql;
	}

	public String[] getRZZLContractSQL(QuestContractInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// select
		// 修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		sql[0] += "c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
				+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
				+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
				+ " ,db.dailybalance "; // 指定余额日期的贷款余额 add by wmzheng at
		// 2010-10-14

		// 胡志强修改(2004-03-09)
		sql[0] += ",a.overdueAmount as overdueAmount,";
		sql[0] += "b.punishInterest as punishInterest";
		// added by xiong fei 2010/05/25查出各单位的担保信息
		sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
		sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
		sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";

		// add by wmzheng at 2010-09-25 合同风险状态
		sql[0] += ",c.nrisklevel as risklevel";

		// from
		sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
		// 从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp "
				+ "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		// 指定余额日期的贷款余额 add by wmzheng at 2010-10-14
		sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
		sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
		sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"
				+ DataFormat.getDateString(qInfo.getBalanceDate())
				+ "','yyyy-mm-dd')";
		sql[1] += " group by db.nCOntractID ) db";

		// 胡志强修改(2004-03-09)
		sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client c1,client c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + LOANConstant.ContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client c1,client c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; // 上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

		// where
		sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID"
				+ " and lp.nContractID(+) =c.id"
				+ " and db.ncontractid(+) = c.id";

		// 胡志强修改(2004-03-09)
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";

		buf.append(" and c.nTypeID=" + LOANConstant.LoanType.RZZL);

		// 合同编号开始
		if (qInfo.getMinContractCode() != null
				&& qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode()
					+ "'");

		// 合同编号结束
		if (qInfo.getMaxContractCode() != null
				&& qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode()
					+ "'");

		/** *************添加国机的变更 2003-3-30 qqgd************** */
		// 合同状态
		if (!qInfo.isShowEnd()) {
			buf.append(" and c.nStatusID<>"
					+ LOANConstant.ContractStatus.FINISH);
		}

		/** **********为了国机加的判断，限制状态 2004-4-23 qqgd ******** */

		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo
				.getOfficeID(), qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++) {
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if (qInfo.getStatusID() > 0) {
			System.out.println("qInfo.getStatusID()================="
					+ qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
		}
		if (qInfo.getStatusIDs() == null || qInfo.getStatusIDs().equals("")
				|| qInfo.getStatusIDs().equals("-1")) {
			buf.append(" and c.nStatusID in (" + strStatus + ")");

		}

		// minzhao20090505修改将合同状态增加为多状态查询
		else {
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs() + ")");
		}

		// add by wmzheng at 2010-09-26 多个贷款种类、子种类
		if (!qInfo.getLoanTypeIDs().equals("")
				&& !qInfo.getLoanTypeIDs().equals("-1")) {
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs() + ")");
		}
		/*
		 * if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1")) {
		 * buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")"); }
		 */

		// 借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		// 借款单位开始
		if (qInfo.getBorrowClientIDFrom() > 0)
			buf.append(" and c.nBorrowClientID>="
					+ qInfo.getBorrowClientIDFrom());
		// 借款单位结束
		if (qInfo.getBorrowClientIDTo() > 0)
			buf
					.append(" and c.nBorrowClientID<="
							+ qInfo.getBorrowClientIDTo());

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// buf.append(" and c1.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
		// 委托单位开始
		if (qInfo.getConsignClientIDFrom() > 0)
			buf.append(" and c.nConsignClientID>="
					+ qInfo.getConsignClientIDFrom());
		// 委托单位结束
		if (qInfo.getConsignClientIDTo() > 0)
			buf.append(" and c.nConsignClientID<="
					+ qInfo.getConsignClientIDTo());

		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		// 贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		// 贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		// add by wmzheng at 2010-10-14 贷款余额
		// 贷款余额开始
		if (qInfo.getMinLoanBalanceAmount() > 0)
			buf.append(" and lp.balance >= "
					+ DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
		// 贷款余额结束
		if (qInfo.getMaxLoanBalanceAmount() > 0)
			buf.append(" and lp.balance <= "
					+ DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));

		// 贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxStartDate())
					+ "','yyyy-mm-dd') ");

		// 贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinStartDate())
					+ "','yyyy-mm-dd') ");

		// 贷款结束日期结束
		if (qInfo.getMaxEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxEndDate())
					+ "','yyyy-mm-dd') ");

		// 贷款结束日期开始
		if (qInfo.getMinEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinEndDate())
					+ "','yyyy-mm-dd') ");

		// 贴现日期结束
		if (qInfo.getMaxDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxDiscountDate())
					+ "','yyyy-mm-dd') ");

		// 贴现日期开始
		if (qInfo.getMinDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinDiscountDate())
					+ "','yyyy-mm-dd') ");

		// 合同利率 add by wmzheng at 2010-10-14
		if (qInfo.getContractRateFrom() > 0)
			buf
					.append(" and (DECODE(c.nTypeID, "
							+ LOANConstant.LoanType.TX
							+ ", c.mDiscountRate, "
							+ LOANConstant.LoanType.ZTX
							+ ", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= "
							+ DataFormat
									.formatRate(qInfo.getContractRateFrom()));
		if (qInfo.getContractRateTo() > 0)
			buf
					.append(" and (DECODE(c.nTypeID, "
							+ LOANConstant.LoanType.TX
							+ ", c.mDiscountRate, "
							+ LOANConstant.LoanType.ZTX
							+ ", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= "
							+ DataFormat.formatRate(qInfo.getContractRateTo()));

		// 贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

		// 贷款期限由
		if (qInfo.getPeriodFrom() > 0)
			buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
		// 贷款期限至
		if (qInfo.getPeriodTo() > 0)
			buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());

		// 保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");

		// added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
		if (qInfo.getIsassure() == 1 || qInfo.getIscredit() == 1
				|| qInfo.getIsimpawn() == 1 || qInfo.getIspledge() == 1
				|| qInfo.getIsrecognizance() == 1
				|| qInfo.getIsrepurchase() == 1) {
			buf.append(" and ( ");
			if (qInfo.getIscredit() == 1) {
				buf.append(" c.nIsCredit=1");
				if (qInfo.getIsassure() == 1 || qInfo.getIsimpawn() == 1
						|| qInfo.getIspledge() == 1
						|| qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsassure() == 1) {
				buf.append(" c.nIsAssure=1");
				if (qInfo.getIsimpawn() == 1 || qInfo.getIspledge() == 1
						|| qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsimpawn() == 1) {
				buf.append(" c.nIsImpawn=1");
				if (qInfo.getIspledge() == 1 || qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIspledge() == 1) {
				buf.append(" c.nIsPledge=1");
				if (qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsrecognizance() == 1) {
				buf.append(" c.IsRecognizance=1");
				if (qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsrepurchase() == 1) {
				buf.append(" c.IsRepurchase=1");
			}
			buf.append(" ) ");
		}

		// 信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		// 是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		// 是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		// 是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		// 贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());

		// add by wmzheng at 2010-10-14 贷款风险状态（复选）
		if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
			buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels() + ")");

		// 按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		// 按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		// 按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		// 管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());

		// add by wmzheng at 2010-09-21
		// 客户属性一
		if (qInfo.getClientTypeID1() > 0)
			buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());

		// 客户属性二
		if (qInfo.getClientTypeID2() > 0)
			buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());

		// 客户属性三
		if (qInfo.getClientTypeID3() > 0)
			buf.append(" and c1.nClienttypeID3=" + qInfo.getClientTypeID3());

		// 客户属性四
		if (qInfo.getClientTypeID4() > 0)
			buf.append(" and c1.nClienttypeID4=" + qInfo.getClientTypeID4());

		// 客户属性五
		if (qInfo.getClientTypeID5() > 0)
			buf.append(" and c1.nClienttypeID5=" + qInfo.getClientTypeID5());

		// 客户属性六
		if (qInfo.getClientTypeID6() > 0)
			buf.append(" and c1.nClienttypeID6=" + qInfo.getClientTypeID6());

		sql[2] = sql[2] + buf.toString();

		sql[3] = "";

		return sql;
	}

	public String[] getDBContractSQL(QuestContractInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// select
		// 修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		sql[0] += "c.ID as contractID,c.nTypeID as loanTypeID,'"
				+ qInfo.getBalanceDate()
				+ "' as dailyDate,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
				+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
				+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
				+ " ,db.dailybalance "; // 指定余额日期的贷款余额 add by wmzheng at
		// 2010-10-14

		// 胡志强修改(2004-03-09)
		sql[0] += ",a.overdueAmount as overdueAmount,";
		sql[0] += "b.punishInterest as punishInterest";
		// added by xiong fei 2010/05/25查出各单位的担保信息
		sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
		sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
		sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";

		// add by wmzheng at 2010-09-25 合同风险状态
		sql[0] += ",c.nrisklevel as risklevel";

		// from
		sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
		// 从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp "
				+ "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		// 指定余额日期的贷款余额 add by wmzheng at 2010-10-14
		sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
		sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
		sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"
				+ DataFormat.getDateString(qInfo.getBalanceDate())
				+ "','yyyy-mm-dd')";
		sql[1] += " group by db.nCOntractID ) db";

		// 胡志强修改(2004-03-09)
		sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client c1,client c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + LOANConstant.ContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client c1,client c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; // 上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

		// where
		sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID"
				+ " and lp.nContractID(+) =c.id"
				+ " and db.ncontractid(+) = c.id";

		// 胡志强修改(2004-03-09)
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";

		// （上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose() == QuestContractInfo.DB) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		}

		// 合同编号开始
		if (qInfo.getMinContractCode() != null
				&& qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode()
					+ "'");

		// 合同编号结束
		if (qInfo.getMaxContractCode() != null
				&& qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode()
					+ "'");

		/** *************添加国机的变更 2003-3-30 qqgd************** */
		// 合同状态
		if (!qInfo.isShowEnd()) {
			buf.append(" and c.nStatusID<>"
					+ LOANConstant.ContractStatus.FINISH);
		}

		/** **********为了国机加的判断，限制状态 2004-4-23 qqgd ******** */

		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo
				.getOfficeID(), qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++) {
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if (qInfo.getStatusID() > 0) {
			System.out.println("qInfo.getStatusID()================="
					+ qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
		}
		if (qInfo.getStatusIDs() == null || qInfo.getStatusIDs().equals("")
				|| qInfo.getStatusIDs().equals("-1")) {
			buf.append(" and c.nStatusID in (" + strStatus + ")");

		}

		// minzhao20090505修改将合同状态增加为多状态查询
		else {
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs() + ")");
		}

		// add by wmzheng at 2010-09-26 多个贷款种类、子种类
		if (!qInfo.getLoanTypeIDs().equals("")
				&& !qInfo.getLoanTypeIDs().equals("-1")) {
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs() + ")");
		}
		/*
		 * if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1")) {
		 * buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")"); }
		 */

		// 借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		// 借款单位开始
		if (qInfo.getBorrowClientIDFrom() > 0)
			buf.append(" and c.nBorrowClientID>="
					+ qInfo.getBorrowClientIDFrom());
		// 借款单位结束
		if (qInfo.getBorrowClientIDTo() > 0)
			buf
					.append(" and c.nBorrowClientID<="
							+ qInfo.getBorrowClientIDTo());

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// buf.append(" and c1.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
		// 委托单位开始
		if (qInfo.getConsignClientIDFrom() > 0)
			buf.append(" and c.nConsignClientID>="
					+ qInfo.getConsignClientIDFrom());
		// 委托单位结束
		if (qInfo.getConsignClientIDTo() > 0)
			buf.append(" and c.nConsignClientID<="
					+ qInfo.getConsignClientIDTo());

		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		// 贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		// 贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		// add by wmzheng at 2010-10-14 贷款余额
		// 贷款余额开始
		if (qInfo.getMinLoanBalanceAmount() > 0)
			buf.append(" and lp.balance >= "
					+ DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
		// 贷款余额结束
		if (qInfo.getMaxLoanBalanceAmount() > 0)
			buf.append(" and lp.balance <= "
					+ DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));

		// 贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxStartDate())
					+ "','yyyy-mm-dd') ");

		// 贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinStartDate())
					+ "','yyyy-mm-dd') ");

		// 贷款结束日期结束
		if (qInfo.getMaxEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxEndDate())
					+ "','yyyy-mm-dd') ");

		// 贷款结束日期开始
		if (qInfo.getMinEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinEndDate())
					+ "','yyyy-mm-dd') ");

		// 贴现日期结束
		if (qInfo.getMaxDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxDiscountDate())
					+ "','yyyy-mm-dd') ");

		// 贴现日期开始
		if (qInfo.getMinDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinDiscountDate())
					+ "','yyyy-mm-dd') ");

		// 合同利率 add by wmzheng at 2010-10-14
		if (qInfo.getContractRateFrom() > 0)
			buf
					.append(" and (DECODE(c.nTypeID, "
							+ LOANConstant.LoanType.TX
							+ ", c.mDiscountRate, "
							+ LOANConstant.LoanType.ZTX
							+ ", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= "
							+ DataFormat
									.formatRate(qInfo.getContractRateFrom()));
		if (qInfo.getContractRateTo() > 0)
			buf
					.append(" and (DECODE(c.nTypeID, "
							+ LOANConstant.LoanType.TX
							+ ", c.mDiscountRate, "
							+ LOANConstant.LoanType.ZTX
							+ ", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= "
							+ DataFormat.formatRate(qInfo.getContractRateTo()));

		// 贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

		// 贷款期限由
		if (qInfo.getPeriodFrom() > 0)
			buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
		// 贷款期限至
		if (qInfo.getPeriodTo() > 0)
			buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());

		// 保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");

		// added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
		if (qInfo.getIsassure() == 1 || qInfo.getIscredit() == 1
				|| qInfo.getIsimpawn() == 1 || qInfo.getIspledge() == 1
				|| qInfo.getIsrecognizance() == 1
				|| qInfo.getIsrepurchase() == 1) {
			buf.append(" and ( ");
			if (qInfo.getIscredit() == 1) {
				buf.append(" c.nIsCredit=1");
				if (qInfo.getIsassure() == 1 || qInfo.getIsimpawn() == 1
						|| qInfo.getIspledge() == 1
						|| qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsassure() == 1) {
				buf.append(" c.nIsAssure=1");
				if (qInfo.getIsimpawn() == 1 || qInfo.getIspledge() == 1
						|| qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsimpawn() == 1) {
				buf.append(" c.nIsImpawn=1");
				if (qInfo.getIspledge() == 1 || qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIspledge() == 1) {
				buf.append(" c.nIsPledge=1");
				if (qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsrecognizance() == 1) {
				buf.append(" c.IsRecognizance=1");
				if (qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsrepurchase() == 1) {
				buf.append(" c.IsRepurchase=1");
			}
			buf.append(" ) ");
		}

		// 信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		// 是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		// 是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		// 是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		// 贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());

		// add by wmzheng at 2010-10-14 贷款风险状态（复选）
		if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
			buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels() + ")");

		// 按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		// 按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		// 按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		// 管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());

		// add by wmzheng at 2010-09-21
		// 客户属性一
		if (qInfo.getClientTypeID1() > 0)
			buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());

		// 客户属性二
		if (qInfo.getClientTypeID2() > 0)
			buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());

		// 客户属性三
		if (qInfo.getClientTypeID3() > 0)
			buf.append(" and c1.nClienttypeID3=" + qInfo.getClientTypeID3());

		// 客户属性四
		if (qInfo.getClientTypeID4() > 0)
			buf.append(" and c1.nClienttypeID4=" + qInfo.getClientTypeID4());

		// 客户属性五
		if (qInfo.getClientTypeID5() > 0)
			buf.append(" and c1.nClienttypeID5=" + qInfo.getClientTypeID5());

		// 客户属性六
		if (qInfo.getClientTypeID6() > 0)
			buf.append(" and c1.nClienttypeID6=" + qInfo.getClientTypeID6());

		sql[2] = sql[2] + buf.toString();

		sql[3] = "";

		return sql;
	}

	public String[] getContractSQL(QuestContractInfo qInfo) {
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// select
		// 修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		sql[0] += "c.ID as contractID,c.nTypeID as loanTypeID,'"
				+ qInfo.getBalanceDate()
				+ "' as dailyDate,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
				+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
				+ " c.mInterestRate as interestRate,c.nloanID as LoanID,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
				+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
				+ " ,db.dailybalance "; // 指定余额日期的贷款余额 add by wmzheng at
		// 2010-10-14

		// 胡志强修改(2004-03-09)
		sql[0] += ",a.overdueAmount as overdueAmount,";
		sql[0] += "b.punishInterest as punishInterest";
		// added by xiong fei 2010/05/25查出各单位的担保信息
		sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
		sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
		sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";

		// add by wmzheng at 2010-09-25 合同风险状态
		sql[0] += ",c.nrisklevel as risklevel";

		// from
		sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
		// 从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp "
				+ "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		// 指定余额日期的贷款余额 add by wmzheng at 2010-10-14
		sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
		sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
		sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"
				+ DataFormat.getDateString(qInfo.getBalanceDate())
				+ "','yyyy-mm-dd')";
		sql[1] += " group by db.nCOntractID ) db";

		// 胡志强修改(2004-03-09)
		sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client c1,client c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + LOANConstant.ContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c,client c1,client c2,userInfo u,userInfo u2";
		sql[1] += " WHERE c1.id(+)=a.nBorrowClientID";
		sql[1] += " and c2.id(+)=a.nconsignclientid";
		sql[1] += " and u.id(+)=a.nInputUserID";
		sql[1] += " and u2.id(+)=a.nNextCheckUserID";
		sql[1] += " and a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; // 上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

		// where
		sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID"
				+ " and lp.nContractID(+) =c.id"
				+ " and db.ncontractid(+) = c.id";

		// 胡志强修改(2004-03-09)
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";

		if (qInfo.getQueryPurpose() == QuestContractInfo.TX) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.TX;
			if (qInfo.getMinRate() > 0) {
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if (qInfo.getMaxRate() > 0) {
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			// 贴现客户名称
			if ((qInfo.getDiscountClientName() != null)
					&& (qInfo.getDiscountClientName().length() > 0)) {
				buf.append(" and c.discountClientName like '%"
						+ qInfo.getDiscountClientName() + "%'");
			}
		} else if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.ZTX;
			if (qInfo.getMinRate() > 0) {
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if (qInfo.getMaxRate() > 0) {
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			// 贴现客户名称
			if ((qInfo.getDiscountClientName() != null)
					&& (qInfo.getDiscountClientName().length() > 0)) {
				buf.append(" and c.discountClientName like '%"
						+ qInfo.getDiscountClientName() + "%'");
			}
		} else {
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.TX;
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.ZTX;
			if (qInfo.getMinRate() > 0) {
				sql[2] += " and y.lateRate >= " + qInfo.getMinRate();
			}
			if (qInfo.getMaxRate() > 0) {
				sql[2] += " and y.lateRate <= " + qInfo.getMaxRate();
			}
		}

		// （上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose() == QuestContractInfo.DB) {
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		} else {
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.DB;
		}

		// 贷款种类
		/*
		 * if (qInfo.getLoanTypeID() > 0) buf.append(" and c.nsubTypeID=" +
		 * qInfo.getLoanTypeID());
		 */
		if (qInfo.getLoanTypeID() <= 0)
			buf.append(" and c.nTypeID<>" + LOANConstant.LoanType.RZZL);

		// 如果是转贴现（查询条件增加）
		if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX) {
			if (qInfo.getInOrOut() > 0)
				buf.append(" and c.NINOROUT=" + qInfo.getInOrOut());
			if (qInfo.getTransDiscountType() > 0)
				buf.append(" and c.NDISCOUNTTYPEID="
						+ qInfo.getTransDiscountType());
			if (qInfo.getTransDiscountTerm() > 0)
				buf.append(" and c.DTENDDATE - c.DTSTARTDATE ="
						+ qInfo.getTransDiscountTerm());
		}

		// 合同编号开始
		if (qInfo.getMinContractCode() != null
				&& qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode()
					+ "'");

		// 合同编号结束
		if (qInfo.getMaxContractCode() != null
				&& qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode()
					+ "'");

		/** *************添加国机的变更 2003-3-30 qqgd************** */
		// 合同状态
		if (!qInfo.isShowEnd()) {
			buf.append(" and c.nStatusID<>"
					+ LOANConstant.ContractStatus.FINISH);
		}

		/** **********为了国机加的判断，限制状态 2004-4-23 qqgd ******** */

		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo
				.getOfficeID(), qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++) {

			if (contractStatusVal[i] == LOANConstant.ContractStatus.SUBMIT)
				continue;
			if (contractStatusVal[i] == LOANConstant.ContractStatus.REFUSE)
				continue;
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if (qInfo.getStatusID() > 0) {
			System.out.println("qInfo.getStatusID()================="
					+ qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
		}
		if (qInfo.getStatusIDs() == null || qInfo.getStatusIDs().equals("")
				|| qInfo.getStatusIDs().equals("-1")) {
			buf.append(" and c.nStatusID in (" + strStatus + ")");

		}

		// minzhao20090505修改将合同状态增加为多状态查询
		else {
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs() + ")");
		}

		// add by wmzheng at 2010-09-26 多个贷款种类、子种类
		if (!qInfo.getLoanTypeIDs().equals("")
				&& !qInfo.getLoanTypeIDs().equals("-1")) {
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs() + ")");
		}
		/*
		 * if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1")) {
		 * buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")"); }
		 */

		// 借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		// 借款单位开始
		if (qInfo.getBorrowClientIDFrom() > 0)
			buf.append(" and c.nBorrowClientID>="
					+ qInfo.getBorrowClientIDFrom());
		// 借款单位结束
		if (qInfo.getBorrowClientIDTo() > 0)
			buf
					.append(" and c.nBorrowClientID<="
							+ qInfo.getBorrowClientIDTo());

		// 借款单位账号
		if ((qInfo.getBorrowAccount() != null)
				&& (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		// 客户分类
		// if (qInfo.getLoanClientTypeID() > 0)
		// buf.append(" and c1.nLoanClientTypeID=" +
		// qInfo.getLoanClientTypeID());

		// 主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		// 委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
		// 委托单位开始
		if (qInfo.getConsignClientIDFrom() > 0)
			buf.append(" and c.nConsignClientID>="
					+ qInfo.getConsignClientIDFrom() + " and c.ntypeid="
					+ LOANConstant.LoanType.WT);
		// 委托单位结束
		if (qInfo.getConsignClientIDTo() > 0)
			buf.append(" and c.nConsignClientID<="
					+ qInfo.getConsignClientIDTo() + " and c.ntypeid="
					+ LOANConstant.LoanType.WT);

		// 委托单位账号
		if ((qInfo.getConsignAccount() != null)
				&& (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		// 贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<="
					+ DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		// 贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>="
					+ DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		// add by wmzheng at 2010-10-14 贷款余额
		// 贷款余额开始
		if (qInfo.getMinLoanBalanceAmount() > 0)
			buf.append(" and lp.balance >= "
					+ DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
		// 贷款余额结束
		if (qInfo.getMaxLoanBalanceAmount() > 0)
			buf.append(" and lp.balance <= "
					+ DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));

		// 贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxStartDate())
					+ "','yyyy-mm-dd') ");

		// 贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinStartDate())
					+ "','yyyy-mm-dd') ");

		// 贷款结束日期结束
		if (qInfo.getMaxEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxEndDate())
					+ "','yyyy-mm-dd') ");

		// 贷款结束日期开始
		if (qInfo.getMinEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinEndDate())
					+ "','yyyy-mm-dd') ");

		// 贴现日期结束
		if (qInfo.getMaxDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('"
					+ DataFormat.getDateString(qInfo.getMaxDiscountDate())
					+ "','yyyy-mm-dd') ");

		// 贴现日期开始
		if (qInfo.getMinDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('"
					+ DataFormat.getDateString(qInfo.getMinDiscountDate())
					+ "','yyyy-mm-dd') ");

		// 合同利率 add by wmzheng at 2010-10-14
		if (qInfo.getContractRateFrom() > 0)
			buf
					.append(" and (DECODE(c.nTypeID, "
							+ LOANConstant.LoanType.TX
							+ ", c.mDiscountRate, "
							+ LOANConstant.LoanType.ZTX
							+ ", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= "
							+ DataFormat
									.formatRate(qInfo.getContractRateFrom()));
		if (qInfo.getContractRateTo() > 0)
			buf
					.append(" and (DECODE(c.nTypeID, "
							+ LOANConstant.LoanType.TX
							+ ", c.mDiscountRate, "
							+ LOANConstant.LoanType.ZTX
							+ ", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= "
							+ DataFormat.formatRate(qInfo.getContractRateTo()));

		// 贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

		// 贷款期限由
		if (qInfo.getPeriodFrom() > 0)
			buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
		// 贷款期限至
		if (qInfo.getPeriodTo() > 0)
			buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());

		// 保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");

		// added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
		if (qInfo.getIsassure() == 1 || qInfo.getIscredit() == 1
				|| qInfo.getIsimpawn() == 1 || qInfo.getIspledge() == 1
				|| qInfo.getIsrecognizance() == 1
				|| qInfo.getIsrepurchase() == 1) {
			buf.append(" and ( ");
			if (qInfo.getIscredit() == 1) {
				buf.append(" c.nIsCredit=1");
				if (qInfo.getIsassure() == 1 || qInfo.getIsimpawn() == 1
						|| qInfo.getIspledge() == 1
						|| qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsassure() == 1) {
				buf.append(" c.nIsAssure=1");
				if (qInfo.getIsimpawn() == 1 || qInfo.getIspledge() == 1
						|| qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsimpawn() == 1) {
				buf.append(" c.nIsImpawn=1");
				if (qInfo.getIspledge() == 1 || qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIspledge() == 1) {
				buf.append(" c.nIsPledge=1");
				if (qInfo.getIsrecognizance() == 1
						|| qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsrecognizance() == 1) {
				buf.append(" c.IsRecognizance=1");
				if (qInfo.getIsrepurchase() == 1) {
					buf.append(" or");
				}
			}
			if (qInfo.getIsrepurchase() == 1) {
				buf.append(" c.IsRepurchase=1");
			}
			buf.append(" ) ");
		}

		// 信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		// 是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		// 是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		// 是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		// 贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());

		// add by wmzheng at 2010-10-14 贷款风险状态（复选）
		if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
			buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels() + ")");

		// 按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		// 按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		// 按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		// 管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());

		// 办事处
		if (qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());

		// 币种
		if (qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());

		// add by wmzheng at 2010-09-21
		// 客户属性一
		if (qInfo.getClientTypeID1() > 0)
			buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());

		// 客户属性二
		if (qInfo.getClientTypeID2() > 0)
			buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());

		// 客户属性三 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE1字段。
		if (qInfo.getClientTypeID3() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE1=" + qInfo.getClientTypeID3());

		// 客户属性四 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE2字段。
		if (qInfo.getClientTypeID4() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE2=" + qInfo.getClientTypeID4());

		// 客户属性五 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE3字段。
		if (qInfo.getClientTypeID5() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE3=" + qInfo.getClientTypeID5());

		// 客户属性六 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE4字段。
		if (qInfo.getClientTypeID6() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE4=" + qInfo.getClientTypeID6());

		sql[2] = sql[2] + buf.toString();

		sql[3] = "";

		return sql;
	}

	public String queryPayNotice(QuestPayNoticeInfo payNInfo) {
		String[] sql = getPayNoticeSQL1(payNInfo);// 自营和银团贷款
		String[] sql2 = getPayNoticeSQL2(payNInfo);// 委托贷款
		String[] sql3 = new String[4];

		String NLoanType = payNInfo.getNLoanType();

		boolean flag = isLoan_WT(NLoanType, payNInfo.getNOfficeID(), payNInfo
				.getNCurrencyID());

		// 贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
		if (!flag && payNInfo.getConsignIDFrom() < 0
				&& payNInfo.getConsignIDTo() < 0) {
			sql3[0] = "*";
			sql3[1] = "(select " + sql[0] + " from " + sql[1] + " where "
					+ sql[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";
		} else if (payNInfo.getConsignIDFrom() > 0
				|| payNInfo.getConsignIDTo() > 0) {
			sql3[0] = "*";
			sql3[1] = "(select " + sql2[0] + " from " + sql2[1] + " where "
					+ sql2[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";

		} else {
			sql3[0] = "*";
			sql3[1] = "(select " + sql[0] + " from " + sql[1] + " where "
					+ sql[2] + " union " + "select " + sql2[0] + " from "
					+ sql2[1] + " where " + sql2[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";

		}

		return " select " + sql3[0] + " from " + sql3[1] + " where " + sql3[2];
	}

	private String[] getPayNoticeSQL1(QuestPayNoticeInfo payNoticeInfo) {
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// 贷款合同、业务汇总查询 放款通知单明细的合同ID

		if (payNoticeInfo.getContractID() > 0) {

			buf.append(" and c.id=" + payNoticeInfo.getContractID());

		}

		// 合同编号
		if (payNoticeInfo.getContractCodeFrom() != null
				&& payNoticeInfo.getContractCodeFrom().length() > 0) {

			buf.append(" and c.sContractCode>='"
					+ payNoticeInfo.getContractCodeFrom() + "'");

		}

		if (payNoticeInfo.getContractCodeTo() != null
				&& payNoticeInfo.getContractCodeTo().length() > 0) {

			buf.append(" and c.sContractCode<='"
					+ payNoticeInfo.getContractCodeTo() + "'");

		}

		// 借款单位
		if (payNoticeInfo.getLoanClientIDFrom() > 0) {

			buf.append(" and c.nBorrowClientID>="
					+ payNoticeInfo.getLoanClientIDFrom());

		}
		if (payNoticeInfo.getLoanClientIDTo() > 0) {

			buf.append(" and c.nBorrowClientID<="
					+ payNoticeInfo.getLoanClientIDTo());

		}

		/*
		 * //委托单位 if(payNoticeInfo.getConsignIDFrom()>0){
		 * 
		 * buf.append(" and
		 * c.NCONSIGNCLIENTID>="+payNoticeInfo.getConsignIDFrom()); }
		 * if(payNoticeInfo.getConsignIDTo()>0){
		 * 
		 * buf.append(" and c.NCONSIGNCLIENTID<="+payNoticeInfo.getConsignIDTo()); }
		 */

		// 放款金额
		if (payNoticeInfo.getMPayAmountFrom() > 0) {

			buf.append(" and l.mAmount>="
					+ DataFormat
							.formatAmount(payNoticeInfo.getMPayAmountFrom()));

		}
		if (payNoticeInfo.getMPayAmountTo() > 0) {

			buf.append(" and l.mAmount<="
					+ DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));

		}

		// 放款利率
		if (payNoticeInfo.getMPayInterestFrom() > 0) {

			buf.append(" and l.minterestrate>="
					+ DataFormat.formatAmount(payNoticeInfo
							.getMPayInterestFrom()));

		}
		if (payNoticeInfo.getMPayInterestTo() > 0) {

			buf.append(" and l.minterestrate<="
					+ DataFormat
							.formatAmount(payNoticeInfo.getMPayInterestTo()));

		}

		// 放贷日期开始
		if (payNoticeInfo.getDtLoanPayDateFrom() != null) {
			buf
					.append(" and TRUNC(l.DTOUTDATE) >= To_Date('"
							+ payNoticeInfo.getDtLoanPayDateFrom()
							+ "','yyyy-mm-dd') ");
		}

		if (payNoticeInfo.getDtLoanPayDateTo() != null) {
			buf.append(" and TRUNC(l.DTOUTDATE) <= To_Date('"
					+ payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
		}

		// 还款日期
		if (payNoticeInfo.getDtRepayDateFrom() != null) {
			buf.append(" and TRUNC(l.dtEnd) >= To_Date('"
					+ payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
		}

		if (payNoticeInfo.getDtRepayDateTo() != null) {
			buf.append(" and TRUNC(l.dtEnd) <= To_Date('"
					+ payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}

		// 录入日期
		if (payNoticeInfo.getDtInputDateFrom() != null) {
			buf.append(" and TRUNC(l.dtInputDate) >= To_Date('"
					+ payNoticeInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}

		if (payNoticeInfo.getDtInputDateTo() != null) {
			buf.append(" and TRUNC(l.dtInputDate) <= To_Date('"
					+ payNoticeInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}
		//
		// 贷款类型
		if (payNoticeInfo.getNLoanType() == null
				|| payNoticeInfo.getNLoanType().trim().equals("-1")) {
			buf
					.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and"
							+ "  a.loantypeid  in (1,5) and a.id=b.subloantypeid "
							+ "and b.currencyid="
							+ payNoticeInfo.getNCurrencyID()
							+ " and b.officeid="
							+ payNoticeInfo.getNOfficeID()
							+ ")");
		}

		else {

			buf
					.append(" and c.nSubTypeID in ("
							+ payNoticeInfo.getNLoanType()
							+ ") and c.nSubTypeID not in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and"
							+ "  a.loantypeid =2 and a.id=b.subloantypeid "
							+ "and b.currencyid="
							+ payNoticeInfo.getNCurrencyID()
							+ " and b.officeid=" + payNoticeInfo.getNOfficeID()
							+ ")");

		}

		// 放款通知单状态

		long loanPayNoticeStatus[] = LOANConstant.LoanPayNoticeStatus
				.getAllCode(payNoticeInfo.getNOfficeID(), payNoticeInfo
						.getNCurrencyID());
		String loanTypeList = "";
		for (int i = 0; i < loanPayNoticeStatus.length; i++) {

			if (loanPayNoticeStatus[i] == LOANConstant.LoanPayNoticeStatus.REFUSE) {
				continue;
			}
			loanTypeList += loanPayNoticeStatus[i];
			if ((loanPayNoticeStatus.length - i) > 1) {

				loanTypeList += ",";
			}

		}
		if (payNoticeInfo.getNPayNoticeStatus() == null
				|| payNoticeInfo.getNPayNoticeStatus().trim().equals("-1")) {
			buf.append(" and l.nStatusID in (" + loanTypeList + ")");
		}

		else {

			buf.append(" and l.nStatusID in ("
					+ payNoticeInfo.getNPayNoticeStatus() + ")");

		}

		// select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,"
				+ "d.name  as loanClientName,'' as ConsignClientName,c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName, "
				+ " l.mAmount as amount,l.dtOutDate as outDate,l.dtEnd as inDate,"
				+ " l.minterestrate as InterestRate,l.nContractid as contractID,"
				+ " l.nDrawNoticeID as DrawNoticeID,"
				+ " c.nTypeID as LoanTypeID,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

		// from
		sql[1] = " loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt";

		// where
		sql[2] = " u.id=l.NINPUTUSERID and lt.id = c.nsubtypeid and c.ID=l.nContractID and d.id =c.nBorrowClientID  and l.NOFFICEID="
				+ payNoticeInfo.getNOfficeID()
				+ "and l.NCURRENCYID="
				+ payNoticeInfo.getNCurrencyID() + buf.toString();

		return sql;

	}

	private String[] getPayNoticeSQL2(QuestPayNoticeInfo payNoticeInfo) {
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// 贷款合同、业务汇总查询 放款通知单明细的合同ID

		if (payNoticeInfo.getContractID() > 0) {

			buf.append(" and c.id=" + payNoticeInfo.getContractID());

		}

		// 合同编号
		if (payNoticeInfo.getContractCodeFrom() != null
				&& payNoticeInfo.getContractCodeFrom().length() > 0) {

			buf.append(" and c.sContractCode>='"
					+ payNoticeInfo.getContractCodeFrom() + "'");

		}

		if (payNoticeInfo.getContractCodeTo() != null
				&& payNoticeInfo.getContractCodeTo().length() > 0) {

			buf.append(" and c.sContractCode<='"
					+ payNoticeInfo.getContractCodeTo() + "'");

		}

		// 借款单位
		if (payNoticeInfo.getLoanClientIDFrom() > 0) {

			buf.append(" and c.nBorrowClientID>="
					+ payNoticeInfo.getLoanClientIDFrom());

		}
		if (payNoticeInfo.getLoanClientIDTo() > 0) {

			buf.append(" and c.nBorrowClientID<="
					+ payNoticeInfo.getLoanClientIDTo());

		}

		// 委托单位
		if (payNoticeInfo.getConsignIDFrom() > 0) {

			buf.append(" and c.NCONSIGNCLIENTID>="
					+ payNoticeInfo.getConsignIDFrom());

		}
		if (payNoticeInfo.getConsignIDTo() > 0) {

			buf.append(" and c.NCONSIGNCLIENTID<="
					+ payNoticeInfo.getConsignIDTo());

		}

		// 放款金额
		if (payNoticeInfo.getMPayAmountFrom() > 0) {

			buf.append(" and l.mAmount>="
					+ DataFormat
							.formatAmount(payNoticeInfo.getMPayAmountFrom()));

		}
		if (payNoticeInfo.getMPayAmountTo() > 0) {

			buf.append(" and l.mAmount<="
					+ DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));

		}

		// 放款利率
		if (payNoticeInfo.getMPayInterestFrom() > 0) {

			buf.append(" and l.minterestrate>="
					+ DataFormat.formatAmount(payNoticeInfo
							.getMPayInterestFrom()));

		}
		if (payNoticeInfo.getMPayInterestTo() > 0) {

			buf.append(" and l.minterestrate<="
					+ DataFormat
							.formatAmount(payNoticeInfo.getMPayInterestTo()));

		}

		// 放贷日期开始
		if (payNoticeInfo.getDtLoanPayDateFrom() != null) {
			buf
					.append(" and TRUNC(l.DTOUTDATE) >= To_Date('"
							+ payNoticeInfo.getDtLoanPayDateFrom()
							+ "','yyyy-mm-dd') ");
		}

		if (payNoticeInfo.getDtLoanPayDateTo() != null) {
			buf.append(" and TRUNC(l.DTOUTDATE) <= To_Date('"
					+ payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
		}

		// 还款日期
		if (payNoticeInfo.getDtRepayDateFrom() != null) {
			buf.append(" and TRUNC(l.dtEnd) >= To_Date('"
					+ payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
		}

		if (payNoticeInfo.getDtRepayDateTo() != null) {
			buf.append(" and TRUNC(l.dtEnd) <= To_Date('"
					+ payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}

		// 录入日期
		if (payNoticeInfo.getDtInputDateFrom() != null) {
			buf.append(" and TRUNC(l.dtInputDate) >= To_Date('"
					+ payNoticeInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}

		if (payNoticeInfo.getDtInputDateTo() != null) {
			buf.append(" and TRUNC(l.dtInputDate) <= To_Date('"
					+ payNoticeInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}

		//
		// 贷款类型
		if (payNoticeInfo.getNLoanType() == null
				|| payNoticeInfo.getNLoanType().trim().equals("-1")) {
			buf
					.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and"
							+ "  a.loantypeid  in (2) and a.id=b.subloantypeid "
							+ "and b.currencyid="
							+ payNoticeInfo.getNCurrencyID()
							+ " and b.officeid="
							+ payNoticeInfo.getNOfficeID()
							+ ")");
		}

		else {

			buf.append(" and c.nSubTypeID in (" + payNoticeInfo.getNLoanType()
					+ ")");

		}

		// 放款通知单状态

		long loanPayNoticeStatus[] = LOANConstant.LoanPayNoticeStatus
				.getAllCode(payNoticeInfo.getNOfficeID(), payNoticeInfo
						.getNCurrencyID());
		String loanTypeList = "";
		for (int i = 0; i < loanPayNoticeStatus.length; i++) {

			if (loanPayNoticeStatus[i] == LOANConstant.LoanPayNoticeStatus.REFUSE) {
				continue;
			}
			loanTypeList += loanPayNoticeStatus[i];
			if ((loanPayNoticeStatus.length - i) > 1) {

				loanTypeList += ",";
			}

		}
		if (payNoticeInfo.getNPayNoticeStatus() == null
				|| payNoticeInfo.getNPayNoticeStatus().equals("-1")) {
			buf.append(" and l.nStatusID in (" + loanTypeList + ")");
		} else {

			buf.append(" and l.nStatusID in ("
					+ payNoticeInfo.getNPayNoticeStatus() + ")");

		}

		// select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,d.name as LoanClientName,"
				+ " e.name as ConsignClientName,c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName,"
				+ " l.mAmount as amount,l.dtOutDate as outDate,l.dtEnd as inDate,"
				+ " l.minterestrate as InterestRate,l.nContractid as contractID,"
				+ " l.nDrawNoticeID as DrawNoticeID,"
				+ " c.nTypeID as LoanTypeID,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

		// from
		sql[1] = " loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt,client_clientinfo e";

		// where
		sql[2] = " u.id=l.NINPUTUSERID and lt.id = c.nsubtypeid and c.ID=l.nContractID and d.id=c.nborrowclientID  and e.id =c.nConsignClientID  and l.NOFFICEID="
				+ payNoticeInfo.getNOfficeID()
				+ "and l.NCURRENCYID="
				+ payNoticeInfo.getNCurrencyID() + buf.toString();

		return sql;
	}

	public boolean isLoan_WT(String subLoanType, long officeID, long currencyID) {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql = null;
		if (subLoanType == null || ("").equals(subLoanType)) {
			return true;
		}
		try {

			sql = "select  loantypeid  from loan_loantypesetting  where statusid=1 and currencyid="
					+ currencyID
					+ " and officeid="
					+ officeID
					+ " and id in ("
					+ subLoanType + ")";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getLong("loantypeid") == LOANConstant.LoanType.WT) {

					return true;

				}

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				cleanup(ps);
				cleanup(con);
				cleanup(rs);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {

			try {
				cleanup(ps);
				cleanup(con);
				cleanup(rs);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return false;

	}

	private void cleanup(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(PreparedStatement ps) throws SQLException {
		try {
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException sqle) {
		}
	}

	private void cleanup(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException sqle) {
		}
	}

	public String queryRepayNotice(QuestRepayNoticeInfo repayInfo) {
		String[] sql1 = getRepayNoticeSQl1(repayInfo);
		String[] sql2 = getRepayNoticeSQl2(repayInfo);
		String[] sql3 = new String[4];

		String NLoanType = repayInfo.getNLoanType();

		boolean flag = isLoan_WT(NLoanType, repayInfo.getNOfficeID(), repayInfo
				.getNCurrencyID());

		// 贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
		if (!flag && repayInfo.getConsignIDFrom() < 0
				&& repayInfo.getConsignIDTo() < 0) {
			sql3[0] = "*";
			sql3[1] = "(select " + sql1[0] + " from " + sql1[1] + " where "
					+ sql1[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";
		} else if (repayInfo.getConsignIDFrom() > 0
				|| repayInfo.getConsignIDTo() > 0) {
			sql3[0] = "*";
			sql3[1] = "(select " + sql2[0] + " from " + sql2[1] + " where "
					+ sql2[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";

		} else {
			sql3[0] = "*";
			sql3[1] = "(select " + sql1[0] + " from " + sql1[1] + " where "
					+ sql1[2] + " union " + "select " + sql2[0] + " from "
					+ sql2[1] + " where " + sql2[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";

		}
		return "select " + sql3[0] + " from " + sql3[1] + " where " + sql3[2];
	}

	private String[] getRepayNoticeSQl1(QuestRepayNoticeInfo repayInfo) {

		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// 查询合同号下还款通知单明细
		if (repayInfo.getContractID() > 0) {

			buf.append(" and l.NCONTRACTID =" + repayInfo.getContractID());

		}

		// 合同编号
		if (repayInfo.getContractCodeFrom() != null
				&& repayInfo.getContractCodeFrom().length() > 0) {

			buf.append(" and c.sContractCode>='"
					+ repayInfo.getContractCodeFrom() + "'");

		}

		if (repayInfo.getContractCodeTo() != null
				&& repayInfo.getContractCodeTo().length() > 0) {

			buf.append(" and c.sContractCode<='"
					+ repayInfo.getContractCodeTo() + "'");

		}

		// 借款单位
		if (repayInfo.getLoanClientIDFrom() > 0) {

			buf.append(" and c.nBorrowClientID>="
					+ repayInfo.getLoanClientIDFrom());

		}
		if (repayInfo.getLoanClientIDTo() > 0) {

			buf.append(" and c.nBorrowClientID<="
					+ repayInfo.getLoanClientIDTo());

		}

		// 还款金额
		if (repayInfo.getMRePayAmountFrom() > 0) {

			buf.append(" and l.mAmount>="
					+ DataFormat.formatAmount(repayInfo.getMRePayAmountFrom()));

		}
		if (repayInfo.getMRePayAmountTo() > 0) {

			buf.append(" and l.mAmount<="
					+ DataFormat.formatAmount(repayInfo.getMRePayAmountTo()));

		}

		// 归还利息
		if (repayInfo.getDrawAmountInterestFrom() > 0) {

			buf.append(" and l.INTERESTAMOUNT>="
					+ DataFormat.formatAmount(repayInfo
							.getDrawAmountInterestFrom()));

		}
		if (repayInfo.getDrawAmountInterestTo() > 0) {

			buf.append(" and l.INTERESTAMOUNT<="
					+ DataFormat.formatAmount(repayInfo
							.getDrawAmountInterestTo()));

		}

		// 还款日期开始
		if (repayInfo.getDtRepayDateFrom() != null) {
			buf.append(" and TRUNC(l.PAYDATE)>= To_Date('"
					+ repayInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
		}

		if (repayInfo.getDtRepayDateTo() != null) {
			buf.append(" and TRUNC(l.PAYDATE)<= To_Date('"
					+ repayInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}

		// 录入日期开始
		if (repayInfo.getDtInputDateFrom() != null) {
			buf.append(" and TRUNC(l.DTINPUTDATE)>= To_Date('"
					+ repayInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}

		if (repayInfo.getDtInputDateTo() != null) {
			buf.append(" and TRUNC(l.DTINPUTDATE)<= To_Date('"
					+ repayInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}

		// 贷款类型
		if (repayInfo.getNLoanType() == null
				|| repayInfo.getNLoanType().trim().equals("-1")) {
			buf
					.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and"
							+ "  a.loantypeid  in (1,5) and a.id=b.subloantypeid "
							+ "and b.currencyid="
							+ repayInfo.getNCurrencyID()
							+ " and b.officeid="
							+ repayInfo.getNOfficeID()
							+ ")");
		}

		else {

			buf
					.append(" and c.nSubTypeID in ("
							+ repayInfo.getNLoanType()
							+ ") and c.nSubTypeID not in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and "
							+ "  a.loantypeid=2 and a.id=b.subloantypeid "
							+ "and b.currencyid=" + repayInfo.getNCurrencyID()
							+ " and b.officeid=" + repayInfo.getNOfficeID()
							+ ")");

		}
		// 还款通知单状态

		long loanRePayNoticeStatus[] = LOANConstant.AheadRepayStatus
				.getAllCode(repayInfo.getNOfficeID(), repayInfo
						.getNCurrencyID());
		String loanTypeList = "";
		for (int i = 0; i < loanRePayNoticeStatus.length; i++) {

			if (loanRePayNoticeStatus[i] == LOANConstant.LoanPayNoticeStatus.REFUSE) {
				continue;
			}
			loanTypeList += loanRePayNoticeStatus[i];
			if ((loanRePayNoticeStatus.length - i) > 1) {

				loanTypeList += ",";
			}

		}

		if (repayInfo.getNRePayNoticeStatus() == null
				|| repayInfo.getNRePayNoticeStatus().trim().equals("-1")) {

			buf.append(" and l.nStatusID in (" + loanTypeList + ")");
		} else {

			buf.append(" and l.nStatusID in ("
					+ repayInfo.getNRePayNoticeStatus() + ")");

		}
		// 是否提前还款
		if (repayInfo.getIsHead() > 0) {

			buf.append("and l.NISAHEAD="
					+ String.valueOf(repayInfo.getIsHead()));

		}

		// select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,lp.SCODE as LetoutNoticeCode,"
				+ " d.name  ClientName ,'' as ConsignClientName,lp.MAMOUNT as LetoutNoticeAmount,u.sName as InputUserName,lt.name as loanTypeName,"
				+ " l.mAmount as amount,l.PAYDATE as PBackDate,l.nIsAhead as IsAhead,"
				+ " l.INTERESTAMOUNT as balanceAmount,l.nContractid as contractID,"
				+ " c.nTypeID as loanType,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

		// from
		sql[1] = " loan_aheadrepayform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_Payform lp,loan_loantypesetting lt";

		// where
		sql[2] = " lt.id = c.nsubtypeid and u.id=l.NINPUTUSERID and c.ID(+)=l.nContractID and d.id(+)=c.nBorrowClientID and lp.id(+)=l.NLOANPAYNOTICEID and l.NOFFICEID="
				+ repayInfo.getNOfficeID()
				+ "and l.NCURRENCYID="
				+ repayInfo.getNCurrencyID() + buf.toString();

		sql[3] = " order by l.ID ";

		return sql;

	}

	private String[] getRepayNoticeSQl2(QuestRepayNoticeInfo repayInfo) {

		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		// 查询合同号下还款通知单明细
		if (repayInfo.getContractID() > 0) {

			buf.append(" and l.NCONTRACTID=" + repayInfo.getContractID());

		}

		// 合同编号
		if (repayInfo.getContractCodeFrom() != null
				&& repayInfo.getContractCodeFrom().length() > 0) {

			buf.append(" and c.sContractCode>='"
					+ repayInfo.getContractCodeFrom() + "'");

		}

		if (repayInfo.getContractCodeTo() != null
				&& repayInfo.getContractCodeTo().length() > 0) {

			buf.append(" and c.sContractCode<='"
					+ repayInfo.getContractCodeTo() + "'");

		}

		// 借款单位
		if (repayInfo.getLoanClientIDFrom() > 0) {

			buf.append(" and c.nBorrowClientID>="
					+ repayInfo.getLoanClientIDFrom());

		}
		if (repayInfo.getLoanClientIDTo() > 0) {

			buf.append(" and c.nBorrowClientID<="
					+ repayInfo.getLoanClientIDTo());

		}

		// 委托单位
		if (repayInfo.getConsignIDFrom() > 0) {

			buf.append(" and c.nConsignClientid>="
					+ repayInfo.getConsignIDFrom());

		}
		if (repayInfo.getConsignIDTo() > 0) {

			buf
					.append(" and c.nConsignClientid<="
							+ repayInfo.getConsignIDTo());

		}

		// 还款金额
		if (repayInfo.getMRePayAmountFrom() > 0) {

			buf.append(" and l.mAmount>="
					+ DataFormat.formatAmount(repayInfo.getMRePayAmountFrom()));

		}
		if (repayInfo.getMRePayAmountTo() > 0) {

			buf.append(" and l.mAmount<="
					+ DataFormat.formatAmount(repayInfo.getMRePayAmountTo()));

		}

		// 归还利息
		if (repayInfo.getDrawAmountInterestFrom() > 0) {

			buf.append(" and l.INTERESTAMOUNT>="
					+ DataFormat.formatAmount(repayInfo
							.getDrawAmountInterestFrom()));

		}
		if (repayInfo.getDrawAmountInterestTo() > 0) {

			buf.append(" and l.INTERESTAMOUNT<="
					+ DataFormat.formatAmount(repayInfo
							.getDrawAmountInterestTo()));

		}

		// 还款日期开始
		if (repayInfo.getDtRepayDateFrom() != null) {
			buf.append(" and TRUNC(l.PAYDATE)>= To_Date('"
					+ repayInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
		}

		if (repayInfo.getDtRepayDateTo() != null) {
			buf.append(" and TRUNC(l.PAYDATE)<= To_Date('"
					+ repayInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
		}

		// 录入日期开始
		if (repayInfo.getDtInputDateFrom() != null) {
			buf.append(" and TRUNC(l.DTINPUTDATE)>= To_Date('"
					+ repayInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}

		if (repayInfo.getDtInputDateTo() != null) {
			buf.append(" and TRUNC(l.DTINPUTDATE)<= To_Date('"
					+ repayInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
		}

		// 贷款类型
		if (repayInfo.getNLoanType() == null
				|| repayInfo.getNLoanType().trim().equals("-1")) {
			buf
					.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and"
							+ "  a.loantypeid  in (2) and a.id=b.subloantypeid "
							+ "and b.currencyid="
							+ repayInfo.getNCurrencyID()
							+ " and b.officeid="
							+ repayInfo.getNOfficeID()
							+ ")");
		}

		else {

			buf.append(" and c.nSubTypeID in (" + repayInfo.getNLoanType()
					+ ")");

		}

		// 还款通知单状态

		long loanRePayNoticeStatus[] = LOANConstant.AheadRepayStatus
				.getAllCode(repayInfo.getNOfficeID(), repayInfo
						.getNCurrencyID());
		String loanTypeList = "";
		for (int i = 0; i < loanRePayNoticeStatus.length; i++) {

			if (loanRePayNoticeStatus[i] == LOANConstant.LoanPayNoticeStatus.REFUSE) {
				continue;
			}
			loanTypeList += loanRePayNoticeStatus[i];
			if ((loanRePayNoticeStatus.length - i) > 1) {

				loanTypeList += ",";
			}

		}
		buf.append(" and l.nStatusID in (" + loanTypeList + ")");

		if (repayInfo.getNRePayNoticeStatus() != null
				&& repayInfo.getNRePayNoticeStatus().length() > 0) {

			buf.append(" and l.nStatusID in ("
					+ repayInfo.getNRePayNoticeStatus() + ")");

		}
		// 是否提前还款
		if (repayInfo.getIsHead() > 0) {

			buf.append("and l.NISAHEAD="
					+ String.valueOf(repayInfo.getIsHead()));

		}

		// select
		sql[0] = " l.ID,l.sCode as Code,c.sContractCode as ContractCode,lp.SCODE as LetoutNoticeCode,"
				+ " d.name as ClientName, e.name as ConsignClientName,lp.MAMOUNT as LetoutNoticeAmount,u.sName as InputUserName,lt.name as loanTypeName,"
				+ " l.mAmount as amount,l.PAYDATE as PBackDate,l.nIsAhead as IsAhead,"
				+ " l.INTERESTAMOUNT as balanceAmount,l.nContractid as contractID,"
				+ " c.nTypeID as loanType,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

		// from
		sql[1] = " loan_aheadrepayform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_Payform lp,client_clientinfo e,loan_loantypesetting lt";

		// where
		sql[2] = " lt.id = c.nsubtypeid and e.id=c.nConsignClientID and u.id(+)=l.NINPUTUSERID and c.ID(+)=l.nContractID and d.id(+)=c.nBorrowClientID and lp.id(+)=l.NLOANPAYNOTICEID and l.NOFFICEID="
				+ repayInfo.getNOfficeID()
				+ "and l.NCURRENCYID="
				+ repayInfo.getNCurrencyID() + buf.toString();

		sql[3] = " order by l.ID ";

		return sql;

	}

	public String queryAdjustUser(long contractID) {
		String strSQL = "select lpad(rownum, 3,'00' ) as numberRow, b.sname as beforeUser, b1.sname as afterUser  ,a.modifydate as  adjustDate, a.reason as reason "
				+ " from  LOAN_LOANMNGERMODIFY_HST a,userInfo b,userInfo b1"
				+ "  where a.SCONTRACTCODE="
				+ contractID
				+ "and b.id=a.olduserid and b1.id=a.newuserid";

		return strSQL;
	}

	public String queryQuestContractPlanInfo(QuestContractPlanInfo queryInfo) {
		String[] sql = getContractPlanSQL1(queryInfo);
		return " select " + sql[1] + " from " + sql[0] + " where " + sql[2];
	}

	public String[] getContractPlanSQL1(QuestContractPlanInfo qInfo) {
		String[] strSQL = { "", "", "", "" };

		//from 
		//20090409 minzhao修改修改人取值方式
		strSQL[0] = " loan_loancontractplan a,loan_contractform b,UserInfo c,LOAN_PLANMODIFYFORM d,UserInfo c1,UserInfo c2  ";

		//select
		strSQL[1] = " a.id as PlanID,a.nPlanVersion as PlanVersion ";
		strSQL[1] += " ,nvl(c.sName,c2.sName) as Modifier,c1.sName as nextCheckUserName ";
		strSQL[1] += " ,b.sContractCode as ContractCode ";
		strSQL[1] += " ,a.dtInputDate as InputDate ";

		//where
		strSQL[2] = " a.nContractID=b.ID(+) ";
		strSQL[2] += " and b.nInputUserID=c2.ID(+) ";
		strSQL[2] += " and d.NNEXTCHECKUSERID = c1.ID(+) ";
		strSQL[2] += " and d.ninputuserid = c.ID(+) ";
		strSQL[2] += " and d.NCONTRACTID(+) = a.nContractID ";
		strSQL[2] += " and a.nStatusID = " + Constant.RecordStatus.VALID;
		strSQL[2] += " and a.id =d.nplanid(+)";
		strSQL[2] += " and (d.nstatusid >0  or d.nstatusid is null  )";

		if (qInfo.getContractID() > 0) {
			strSQL[2] += " and a.nContractID= " + qInfo.getContractID();
		}

		return strSQL;
	}

	public String queryExtend(QuestExtendInfo qInfo) throws Exception {
		String[] sql1 = getExtendSQL1(qInfo);
		String[] sql2 = getExtendSQL2(qInfo);
		String[] sql3 = new String[4];

		String NLoanType = qInfo.getLoanTypeIDs();

		boolean flag = isLoan_WT(NLoanType, qInfo.getNOfficeId(), qInfo
				.getNCurrencyId());

		//贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
		if (!flag && qInfo.getConsignClientIDFrom() < 0
				&& qInfo.getConsignClientIDTo() < 0) {
			sql3[0] = "*";
			sql3[1] = "(select " + sql1[1] + " from " + sql1[0] + " where "
					+ sql1[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";
		} else if (qInfo.getConsignClientIDFrom() > 0
				|| qInfo.getConsignClientIDTo() > 0) {
			sql3[0] = "*";
			sql3[1] = "(select " + sql2[1] + " from " + sql2[0] + " where "
					+ sql2[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";

		} else {
			sql3[0] = "*";
			sql3[1] = "(select " + sql1[1] + " from " + sql1[0] + " where "
					+ sql1[2] + " union " + "select " + sql2[1] + " from "
					+ sql2[0] + " where " + sql2[2] + ")";
			sql3[2] = "1=1";
			sql3[3] = "";

		}

		return "select " + sql3[0] + " from " + sql3[1] + " where " + sql3[2];
	}
	
	public String[] getExtendSQL1(QuestExtendInfo qInfo) throws Exception
	{
		String[] strSQL = { "", "", "", "" };
		StringBuffer sb = new StringBuffer();

		//from
		strSQL[0] = " loan_extendform a,loan_contractform b " + " ,client_clientinfo c,loan_extenddetail e,userInfo u,userInfo u2, loan_loantypesetting lt";

		//select
		strSQL[1] =
			" a.id as ExtendID,b.ntypeid as TypeID,lt.name as LoanTypeName "
				+ " ,b.id as ContractID,b.scontractcode as ContractCode "
				+ " ,c.name as ClientName,'' as ConsignClientName "
				+ " ,a.nserialno as ExtendNo "
				//added by mzh_fu 2007/10/17
				+ " ,a.sapplycode as applycode "
				
				+ " ,b.MEXAMINEAMOUNT as Amount "
				+ " ,e.mextendamount as ExtendAmount "
				+ " ,0 as ContractRate,0 as Rate "
				+ " ,a.minterestadjust as ExtendRate "
				+ " ,a.mAdjustRate as AdjustRate "
				+ " ,a.mStaidAdjustRate as StaidAdjustRate "
				+ " ,b.dtstartdate as DateFrom,b.dtenddate as DateTo "
				+ " ,a.nstatusid as StatusID "
				+ " ,u.sName as nextCheckUserName "
				+ " ,u2.sName as inputUserName" //录入人 
				+ " ,a.DTINPUTDATE as inputDate"; //录入日期 
				
		//where
		strSQL[2] =
			" a.nContractID=b.ID "
				+ " and b.nborrowclientid=c.id(+) "
				+ " and e.nextendformid=a.id "
				+ " and u.id(+)=a.NNEXTCHECKUSERID"
				+ " and u2.id(+)=a.NINPUTUSERID and lt.id=b.nsubTypeid and lt.loantypeid=b.ntypeid";
		
		
		 
		//贷款类型
	       if(qInfo.getLoanTypeIDs()==null || qInfo.getLoanTypeIDs().equals("")||qInfo.getLoanTypeIDs().trim().equals("-1") ){
	    	   sb.append(" and b.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
	        		"  a.loantypeid  in (1,5) and a.id=b.subloantypeid " +
	        		"and b.currencyid="+qInfo.getNCurrencyId()+" and b.officeid="+qInfo.getNOfficeId()+")");
	       } 
	  		
	       else{
	  			
	  			sb.append(" and b.nSubTypeID in (" + qInfo.getLoanTypeIDs() + ") and b.nSubTypeID not in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and "+
	  					"  a.loantypeid=2 and a.id=b.subloantypeid " +
		        		"and b.currencyid="+qInfo.getNCurrencyId()+" and b.officeid="+qInfo.getNOfficeId()+")");
	  			
	  		}
	       if (qInfo.getStatusID() > 0)
	       {
			sb.append(" and a.nStatusID = " + qInfo.getStatusID());
	       }
		//展期申请状态(复选) add by wmzheng at 2010-10-15
		if(qInfo.getStatusIDs() != null && qInfo.getStatusIDs().length() > 0)
		{
			sb.append(" and a.nStatusID in (" + qInfo.getStatusIDs()+")");
		}		
		if ((qInfo.getContractCodeBeg() != null) && (qInfo.getContractCodeBeg().length() > 0))
		{
			sb.append(" and b.sContractCode >= '" + qInfo.getContractCodeBeg() + "'");
		}
		if ((qInfo.getContractCodeEnd() != null) && (qInfo.getContractCodeEnd().length() > 0))
		{
			sb.append(" and b.sContractCode <= '" + qInfo.getContractCodeEnd() + "'");
		}

		//借款单位(复选) add by wmzheng at 2010-10-15 
		if (qInfo.getBorrowClientIDFrom() > 0)
		{	
			sb.append(" and b.nBorrowClientID >= " + qInfo.getBorrowClientIDFrom());
		}	
		if (qInfo.getBorrowClientIDTo() > 0)
		{
			sb.append(" and b.nBorrowClientID <= " + qInfo.getBorrowClientIDTo());
		}
		
		
		if (qInfo.getLoanAmountBeg() >0)
		{
			sb.append(" and b.MEXAMINEAMOUNT >= " + qInfo.getLoanAmountBeg());
		}
		if (qInfo.getLoanAmountEnd() > 0)
		{
			sb.append(" and b.MEXAMINEAMOUNT <= " + qInfo.getLoanAmountEnd());
		}
		if (qInfo.getExtendAmountBeg() > 0)
		{
			sb.append(" and e.mExtendAmount >= " + qInfo.getExtendAmountBeg());
		}
		if (qInfo.getExtendAmountEnd() >0)
		{
			sb.append(" and e.mExtendAmount <= " + qInfo.getExtendAmountEnd());
		}
		if (qInfo.getDateFrom() != null) //
		{
			sb.append(" and (b.DTSTARTDATE " + " >= to_date('" + DataFormat.getDateString(qInfo.getDateFrom()) + "','yyyy-mm-dd') ) ");
		}
		if (qInfo.getDateTo() != null) //
		{
			sb.append(" and (b.DTENDDATE " + " <= to_date('" + DataFormat.getDateString(qInfo.getDateTo()) + "','yyyy-mm-dd') ) ");
		}
		
		//展期利率 add by wmzheng at 2010-10-15
		if (qInfo.getExtendRateFrom() > 0)
		{	
			sb.append(" and a.minterestadjust >= " + DataFormat.formatRate(qInfo.getExtendRateFrom()));
		}	
		if (qInfo.getExtendRateTo() >0)
		{
			sb.append(" and a.minterestadjust <= " + DataFormat.formatRate(qInfo.getExtendRateTo()));
		}
		
		//录入日期结束
		if (qInfo.getMaxInputDate() != null)
		{
			sb.append(" and TRUNC(a.dtInputDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxInputDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期开始
		if (qInfo.getMinInputDate() != null)
		{
			sb.append(" and TRUNC(a.dtInputDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinInputDate()) + "','yyyy-mm-dd') ");
		}
		//区分办事处
		if(qInfo.getNOfficeId()!=-1){
			sb.append("  and ( a.nofficeid = "+qInfo.getNOfficeId()+") ");			
		}
		
		//区分币种
		if(qInfo.getNCurrencyId()!=-1){
			sb.append(" and (a.NCURRENCYID="+qInfo.getNCurrencyId()+") ");
		}
		
		
		strSQL[2] += sb.toString();

		//order by
		strSQL[3] = " order by b.ntypeid,b.scontractcode ";

		/**********处理查询条件*************/
		return strSQL;
	}
	
	public String[] getExtendSQL2(QuestExtendInfo qInfo) throws Exception
	{
		String[] strSQL = { "", "", "", "" };
		StringBuffer sb = new StringBuffer();

		//from
		strSQL[0] = " loan_extendform a,loan_contractform b, " + "client_clientinfo c, client_clientinfo d,loan_extenddetail e,userInfo u,userInfo u2, loan_loantypesetting lt";

		//select
		strSQL[1] =
			" a.id as ExtendID,b.ntypeid as TypeID,lt.name as LoanTypeName "
				+ " ,b.id as ContractID,b.scontractcode as ContractCode, "
				+ "c.name as ClientName,d.name as ConsignClientName "
				+ " ,a.nserialno as ExtendNo "
				//added by mzh_fu 2007/10/17
				+ " ,a.sapplycode as applycode "
				
				+ " ,b.MEXAMINEAMOUNT as Amount "
				+ " ,e.mextendamount as ExtendAmount "
				+ " ,0 as ContractRate,0 as Rate "
				+ " ,a.minterestadjust as ExtendRate "
				+ " ,a.mAdjustRate as AdjustRate "
				+ " ,a.mStaidAdjustRate as StaidAdjustRate "
				+ " ,b.dtstartdate as DateFrom,b.dtenddate as DateTo "
				+ " ,a.nstatusid as StatusID "
				+ " ,u.sName as nextCheckUserName "
				+ " ,u2.sName as inputUserName" //录入人 
				+ " ,a.DTINPUTDATE as inputDate"; //录入日期 
				
		//where
		strSQL[2] =
			" a.nContractID=b.Id "
				+ " and b.nborrowclientid=c.id(+) "
				+ " and b.nconsignclientid=d.id(+) "
				+ " and e.nextendformid=a.id "
				+ " and u.id(+)=a.NNEXTCHECKUSERID"
				+ " and u2.id(+)=a.NINPUTUSERID and lt.id=b.nsubTypeid and lt.loantypeid=b.ntypeid";
		
		//贷款类型
	       if(qInfo.getLoanTypeIDs()==null ||qInfo.getLoanTypeIDs().equals("")|| qInfo.getLoanTypeIDs().trim().equals("-1") ){
	        sb.append(" and b.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
	        		"  a.loantypeid  in (2) and a.id=b.subloantypeid " +
	        		"and b.currencyid="+qInfo.getNCurrencyId()+" and b.officeid="+qInfo.getNOfficeId()+")");
	       } 
	        
	  		else{
	  			
	  			sb.append(" and b.nSubTypeID in (" + qInfo.getLoanTypeIDs() + ")");
	  			
	  		}
			
		if (qInfo.getStatusID() > 0)
		{
			sb.append(" and a.nStatusID = " + qInfo.getStatusID());
		}
		//展期申请状态(复选) add by wmzheng at 2010-10-15
		if(qInfo.getStatusIDs() != null && qInfo.getStatusIDs().length() > 0)
		{
			sb.append(" and a.nStatusID in (" + qInfo.getStatusIDs()+")");
		}		
		if ((qInfo.getContractCodeBeg() != null) && (qInfo.getContractCodeBeg().length() > 0))
		{
			sb.append(" and b.sContractCode >= '" + qInfo.getContractCodeBeg() + "'");
		}
		if ((qInfo.getContractCodeEnd() != null) && (qInfo.getContractCodeEnd().length() > 0))
		{
			sb.append(" and b.sContractCode <= '" + qInfo.getContractCodeEnd() + "'");
		}

		//借款单位(复选) add by wmzheng at 2010-10-15 
		if (qInfo.getBorrowClientIDFrom() >0)
		{	
			sb.append(" and b.nBorrowClientID >= " + qInfo.getBorrowClientIDFrom());
		}	
		if (qInfo.getBorrowClientIDTo() >= 0)
		{
			sb.append(" and b.nBorrowClientID <= " + qInfo.getBorrowClientIDTo());
		}
		//委托单位(复选) add by wmzheng at 2010-10-15
		if (qInfo.getConsignClientIDFrom() > 0)
		{
			sb.append(" and b.nConsignClientID >= " + qInfo.getConsignClientIDFrom());
		}
		if (qInfo.getConsignClientIDTo() > 0)
		{
			sb.append(" and b.nConsignClientID <= " + qInfo.getConsignClientIDTo());		
		}
		
		if (qInfo.getLoanAmountBeg() > 0)
		{
			sb.append(" and b.MEXAMINEAMOUNT >= " + qInfo.getLoanAmountBeg());
		}
		if (qInfo.getLoanAmountEnd() > 0)
		{
			sb.append(" and b.MEXAMINEAMOUNT <= " + qInfo.getLoanAmountEnd());
		}
		if (qInfo.getExtendAmountBeg() > 0)
		{
			sb.append(" and e.mExtendAmount >= " + qInfo.getExtendAmountBeg());
		}
		if (qInfo.getExtendAmountEnd() > 0)
		{
			sb.append(" and e.mExtendAmount <= " + qInfo.getExtendAmountEnd());
		}
		if (qInfo.getDateFrom() != null) //
		{
			sb.append(" and (b.DTSTARTDATE " + " >= to_date('" + DataFormat.getDateString(qInfo.getDateFrom()) + "','yyyy-mm-dd') ) ");
		}
		if (qInfo.getDateTo() != null) //
		{
			sb.append(" and (b.DTENDDATE " + " <= to_date('" + DataFormat.getDateString(qInfo.getDateTo()) + "','yyyy-mm-dd') ) ");
		}
		
		//展期利率 add by wmzheng at 2010-10-15
		if (qInfo.getExtendRateFrom() > 0)
		{	
			sb.append(" and a.minterestadjust >= " + DataFormat.formatRate(qInfo.getExtendRateFrom()));
		}	
		if (qInfo.getExtendRateTo() > 0)
		{
			sb.append(" and a.minterestadjust <= " + DataFormat.formatRate(qInfo.getExtendRateTo()));
		}
		
		//录入日期结束
		if (qInfo.getMaxInputDate() != null)
		{
			sb.append(" and TRUNC(a.dtInputDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxInputDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期开始
		if (qInfo.getMinInputDate() != null)
		{
			sb.append(" and TRUNC(a.dtInputDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinInputDate()) + "','yyyy-mm-dd') ");
		}
		//区分办事处
		if(qInfo.getNOfficeId()!=-1){
			sb.append("  and ( a.nofficeid = "+qInfo.getNOfficeId()+") ");			
		}
		
		//区分币种
		if(qInfo.getNCurrencyId()!=-1){
			sb.append(" and (a.NCURRENCYID="+qInfo.getNCurrencyId()+") ");
		}
		
		
		strSQL[2] += sb.toString();

		//order by
		strSQL[3] = " order by b.ntypeid,b.scontractcode ";

		/**********处理查询条件*************/
		return strSQL;
	}
}

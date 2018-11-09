/*
 * Created on 2004-9-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.queryobj;
import java.sql.*;

import com.iss.itreasury.settlement.query.paraminfo.QueryContractConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryContractSumInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.system.dao.PageLoader;
/**
 * @author weilu
 *
 */
public class QContractSearchDao extends BaseQueryObject
{
	/**
	 * 
	 */
	public QContractSearchDao()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public String[] getContractSQL(QueryContractConditionInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		//select
		sql[0] =
			" c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.mLoanAmount as loanAmount,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBorrowClientID as borrowClientID,c.nconsignclientid as clientID,c.nBankInterestID as bankInterestID ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,c.nInputUserID as inputUserID,c.NNEXTCHECKUSERID as checkUserID,"
				+ " c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,lp.balance ";
		//from
		sql[1] = " loan_contractForm c,";
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,loan_payform lp " + "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		/*sql[1] += ",(SELECT a.id,sum(NVL(c.mBalance,0.0)) as overdueAmount";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c";
		sql[1] += " WHERE a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and a.nStatusID = " + SETTConstant.SettContractStatus.OVERDUE;
		sql[1] += " GROUP BY a.id) a";
		sql[1] += ",(SELECT a.id,sum(NVL(c.mInterest,0.0)) as punishInterest";
		sql[1] += " FROM loan_contractForm a,loan_payForm b,sett_subAccount c";
		sql[1] += " WHERE a.id = b.nContractID(+)";
		sql[1] += " and a.id = c.al_nLoanNoteID(+)";
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b";*/

		//where
		sql[2] = " lp.nContractID(+) =c.id";

		//sql[2] += " and c.id = a.id(+)";
		//sql[2] += " and c.id = b.id(+)";

		//贷款种类
		if (qInfo.getSettLoanTypeID() > 0)
			sql[2] += " and c.nTypeID=" + qInfo.getSettLoanTypeID();

		//合同编号开始
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//合同编号结束
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		//合同状态
		if (qInfo.getStatusID() > 0)
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());

		//合同管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());

		//借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());

		//委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());

		//贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		//贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		//贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");

		//贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum=" + qInfo.getIntervalNum());

		sql[2] = sql[2] + buf.toString();
		//order
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) qInfo.getOrderParam())
		{
			case 1 :
				oBuf.append(" \n order by c.nTypeID" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c.nconsignclientid" + strDesc);
				break;
			case 5 :
				oBuf.append(" \n order by c.mLoanAmount" + strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by c.mInterestRate" + strDesc);
				break;
			case 7 :
				oBuf.append(" \n order by c.nIntervalNum" + strDesc);
				break;
			case 8 :
				oBuf.append(" \n order by c.dtStartDate" + strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by c.nStatusID" + strDesc);
				break;
			case 10 :
				oBuf.append(" \n order by c.nInputUserID" + strDesc);
				break;
			case 12 :
				oBuf.append(" \n order by c.mExamineAmount" + strDesc);
				break;
			case 13 :
				oBuf.append(" \n order by c.mCheckAmount" + strDesc);
				break;
			case 14 :
				oBuf.append(" \n order by c.mDiscountRate" + strDesc);
				break;
			case 15 :
				oBuf.append(" \n order by c.dtEndDate" + strDesc);
				break;

			/*case 16 :
				oBuf.append(" \n order by a.overdueAmount" + strDesc);
				break;
			case 17 :
				oBuf.append(" \n order by b.punishInterest" + strDesc);
				break;*/

			case 20 :
				oBuf.append(" \n order by c.NNEXTCHECKUSERID" + strDesc);
				break;
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		sql[3] = oBuf.toString();
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}
	/**
	 * 执行合同查询，（使用PageLoader方式）
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryContract(QueryContractConditionInfo qInfo) throws Exception
	{
		String[] sql = getContractSQL(qInfo);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.query.resultinfo.QueryContractInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	
	/**
	 * 执行合同查询的汇总信息查询
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QueryContractSumInfo queryContractSum(QueryContractConditionInfo qInfo) throws Exception
	{
		QueryContractSumInfo sumInfo = new QueryContractSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String[] sql = getContractSQL(qInfo);
		String strSQL = "";

		try
		{
			sql[0] = "sum(NVL(c.mExamineAmount,0.0)) as sumLoanAmount,";
			sql[0] += " sum(lp.balance) as sumBalance";
			strSQL = "select " + sql[0] + " from " + sql[1] + " where " + sql[2];
			log.print(strSQL);

			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setTotalApplyAmount(rs.getDouble("sumLoanAmount"));
				sumInfo.setTotalBalance(rs.getDouble("sumBalance"));
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumInfo;
	}
	
	public String getContractOrderSQL(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 :
				oBuf.append(" \n order by c.nTypeID" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c.nconsignclientid" + strDesc);
				break;
			case 5 :
				oBuf.append(" \n order by c.mLoanAmount" + strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by c.mInterestRate" + strDesc);
				break;
			case 7 :
				oBuf.append(" \n order by c.nIntervalNum" + strDesc);
				break;
			case 8 :
				oBuf.append(" \n order by c.dtStartDate" + strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by c.nStatusID" + strDesc);
				break;
			case 10 :
				oBuf.append(" \n order by c.nInputUserID" + strDesc);
				break;
			case 12 :
				oBuf.append(" \n order by c.mExamineAmount" + strDesc);
				break;
			case 13 :
				oBuf.append(" \n order by c.mCheckAmount" + strDesc);
				break;
			case 14 :
				oBuf.append(" \n order by c.mDiscountRate" + strDesc);
				break;
			case 15 :
				oBuf.append(" \n order by c.dtEndDate" + strDesc);
				break;

			/*case 16 :
				oBuf.append(" \n order by a.overdueAmount" + strDesc);
				break;
			case 17 :
				oBuf.append(" \n order by b.punishInterest" + strDesc);
				break;*/

			case 20 :
				oBuf.append(" \n order by c.NNEXTCHECKUSERID" + strDesc);
				break;
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	//放款通知单==============================================================================
	
	public PageLoader queryPayNotice(long contractID) throws Exception
	{
		String[] sql = getPayNoticeSQL(contractID);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.settpaynotice.dataentity.SettPayNoticeInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	private String[] getPayNoticeSQL(long cID)
	{
		String[] sql = new String[4];

		//select
		sql[0] =
			" l.ID,l.sCode as Code,c.sContractCode as ContractCode,"
				+ " c.nBorrowClientID as borrowClientID,c.mExamineAmount as contractAmount,"
				+ " l.mAmount as Amount,l.dtOutDate as outDate,l.dtEnd as inDate,"
				+ " l.minterestrate as InterestRate,l.nContractid as contractID,"
				+ " l.nDrawNoticeID as DrawNoticeID,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID,c.NNEXTCHECKUSERID as nNextCheckUserID ";

		//from
		sql[1] = " loan_payform l,loan_contractForm c";

		//where
		sql[2] = " c.ID(+)=l.nContractID and c.ID=" + cID;

		sql[3] = " order by l.ID ";
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		return sql;
	}
	
	public String getPayNoticeOrder(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //放款通知单编号
				oBuf.append(" \n order by l.sCode" + strDesc);
				break;
			case 2 : //合同编号
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 : //借款单位
				oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
				break;
			case 5 : //放款金额
				oBuf.append(" \n order by l.mAmount" + strDesc);
				break;
			case 11 : //放款利率
				oBuf.append(" \n order by l.minterestrate" + strDesc);
				break;
			case 4 : //合同金额
				oBuf.append(" \n order by c.mExamineAmount" + strDesc);
				break;
			case 6 : //放宽日期
				oBuf.append(" \n order by l.dtOutDate" + strDesc);
				break;
			case 7 : //还款日期
				oBuf.append(" \n order by l.dtEnd" + strDesc);
				break;
			case 8 : //提交日期
				oBuf.append(" \n order by l.dtInputDate" + strDesc);
				break;
			case 9 : //放款通知单状态
				oBuf.append(" \n order by l.nStatusID" + strDesc);
				break;
			case 10 : //下一个审核人
				oBuf.append(" \n order by l.NNEXTCHECKUSERID" + strDesc);
				break;
			default :
				oBuf.append(" \n order by l.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
	//放款通知单==============================================================================
	
	public PageLoader queryDiscountCredence(long contractID) throws Exception
	{
		String[] sql = getDiscountCredenceSQL(contractID);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.settlement.query.resultinfo.QueryContractInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getDiscountCredenceSQL(long cID)
	{
		String[] sql = new String[4];

		//select
		sql[0] =
			" l.ID,c.sContractCode as ContractCode,"
				+ " c.nborrowClientID as borrowClientID,l.mAmount as BillAmount,"
				+ " l.mRate as DiscountRate,l.mInterest as BillInterest,"
				+ " l.DTInputDate as InputDate,l.nStatusID as statusID,c.NNEXTCHECKUSERID as CheckUserID ";

		//from
		sql[1] = " Loan_DiscountCredence l,loan_contractForm c";

		//where
		sql[2] = " c.ID(+)=l.nContractID and c.ID=" + cID;

		sql[3] = " order by l.ID ";
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		return sql;
	}
	public String getDiscountCredenceOrder(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //贴现合同编号
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 2 : //申请单位
				oBuf.append(" \n order by l.sApplyClient" + strDesc);
				break;
			case 3 : //贴现金额
				oBuf.append(" \n order by l.mAmount" + strDesc);
				break;
			case 4 : //贴现利率
				oBuf.append(" \n order by l.mRate" + strDesc);
				break;
			case 5 : //贴现日期
				oBuf.append(" \n order by l.dtPublicDate" + strDesc);
				break;
			case 6 : //凭证状态
				oBuf.append(" \n order by l.nStatusID" + strDesc);
				break;
			case 7 : //下个审核人
				oBuf.append(" \n order by l.nNextCheckUserID" + strDesc);
				break;
			default :
				oBuf.append(" \n order by l.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
	public static void main(String[] args)
	{
		QueryContractConditionInfo qInfo = new QueryContractConditionInfo();
		QContractSearchDao dao = new QContractSearchDao();
		try
		{
			dao.getDiscountCredenceSQL(344);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

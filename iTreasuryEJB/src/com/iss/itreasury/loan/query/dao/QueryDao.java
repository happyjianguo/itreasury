package com.iss.itreasury.loan.query.dao;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentQueryInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.loan.query.dataentity.ContractInfoAdjustQuery;
import com.iss.itreasury.loan.query.dataentity.QueryAssureNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QueryLoanApplyInfo;
import com.iss.itreasury.loan.query.dataentity.QueryPayformOverdue;
import com.iss.itreasury.loan.query.dataentity.QueryPerformInfo;
import com.iss.itreasury.loan.query.dataentity.QuerySumInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractInfo;
import com.iss.itreasury.loan.query.dataentity.QuestContractPlanInfo;
import com.iss.itreasury.loan.query.dataentity.QuestCredenceInfo;
import com.iss.itreasury.loan.query.dataentity.QuestExtendInfo;
import com.iss.itreasury.loan.query.dataentity.QuestLoanDrawNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QuestPayNoticeInfo;
import com.iss.itreasury.loan.query.dataentity.QuestRepayNoticeInfo;
import com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;


public class QueryDao extends ITreasuryDAO
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
    
	//	///////////////////////////////////////////////////////////////////////
	private Log4j log = new Log4j(Constant.ModuleType.LOAN, this);

	private void cleanup(ResultSet rs) throws SQLException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(PreparedStatement ps) throws SQLException
	{
		try
		{
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	private void cleanup(Connection con) throws SQLException
	{
		try
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (SQLException sqle)
		{
		}
	}
	/////////////////////////////////////////////////////////////////////////////

	private String[] getAttornmentSQL(AttornmentQueryInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] = " * ";
		//from
		sql[1] = " sec_attornmentApplyForm ";

		//where
		sql[2] = " 1=1 ";

		/**********处理查询条件*************/
		if (qInfo.getUserId() > 0)
		{
			sqlBuf.append(" and inputUserID=" + qInfo.getUserId());
		}
		if (qInfo.getStatusId() > 0)
		{
			sqlBuf.append(" and statusid=" + qInfo.getStatusId());
		}
		if (qInfo.getStartRepurchaseApplyId() > 0)
		{
			sqlBuf.append(" and repurchaseApplyId >=" + qInfo.getStartRepurchaseApplyId());
		}

		if (qInfo.getEndRepurchaseApplyId() > 0)
		{
			sqlBuf.append(" and repurchaseApplyId <=" + qInfo.getEndRepurchaseApplyId());
		}

		if (qInfo.getStartAttornmentApplyId() > 0)
		{
			sqlBuf.append(" and ID>= " + qInfo.getStartAttornmentApplyId());
		}

		if (qInfo.getEndAttornmentApplyId() > 0)
		{
			sqlBuf.append(" and ID<= " + qInfo.getEndAttornmentApplyId());
		}

		if (qInfo.getStartRepurchaseAmount() > 0)
		{
			sqlBuf.append(" and AttornmentAmount >=" + qInfo.getStartRepurchaseAmount());
		}

		if (qInfo.getEndRepurchaseAmount() > 0)
		{
			sqlBuf.append(" and attornmentAmount<= " + qInfo.getEndRepurchaseAmount());
		}

		if (qInfo.getStartInputDate() != null)
		{
			sqlBuf.append(" and to_char(InputDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(qInfo.getStartInputDate()) + "'");
		}

		if (qInfo.getEndInputDate() != null)
		{
			sqlBuf.append(" and to_char(InputDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(qInfo.getEndInputDate()) + "'");
		}

		sql[2] = sql[2] + sqlBuf.toString();

		//order
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) qInfo.getOrderParam())
		{
			case 1 : //贷款种类
				oBuf.append(" \n order by code" + strDesc);
				break;
			case 2 : //贷款种类
				oBuf.append(" \n order by AttornmentAmount" + strDesc);
				break;
			case 3 : //贷款种类
				oBuf.append(" \n order by RepurchaseStartDate" + strDesc);
				break;
			case 4 : //贷款种类
				oBuf.append(" \n order by RepurchaseEndDate" + strDesc);
				break;
			case 5 : //贷款种类
				oBuf.append(" \n order by InputDate" + strDesc);
				break;
			case 6 : //贷款种类
				oBuf.append(" \n order by StatusId" + strDesc);
				break;
			default :
				oBuf.append(" \n order by ID" + strDesc);
				break;
		}
		sql[3] = oBuf.toString();
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");

		return sql;
	}
	
	public String[] getContractSQL_wlx(QuestContractInfo qInfo)
	{
		System.out.println("welcome to getContractSQL_wlx");
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		//select
		//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		// modified by fxzhang 2012-6-6 对于初始化数据，loan_DiscountFormBill中没有相关记录，可直接取合同中的
		sql[0] +=" distinct nvl(w.NACCEPTPOTYPEID, (case when(c.nbankacceptpo > 0) then 1 else  2 end)) as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"			
//		sql[0] +=" distinct w.NACCEPTPOTYPEID as tsDiscountTypeID,c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,c.mdiscountaccrual as mDiscountAccrual ,"
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
				

		//from
		//sql[1] = "loan_DiscountCredence w,Loan_DiscountContractBill m,loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		sql[1] = "(select distinct nLoanID,NACCEPTPOTYPEID from loan_DiscountFormBill) w ,loan_contractForm c,client_clientinfo c1,client_clientinfo c2,client_clientinfo c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y,";
		//从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		//胡志强修改(2004-03-09)
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
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b";

		//where
		

		//胡志强修改(2004-03-09)
		sql[2]  = " c.nLoanId = w.nLoanID(+)";
		//sql[2] += " and m.nAcceptpotypeID =w.ndrafttypeid(+)";
		//sql[2] += " and w.ndrafttypeid>0";
		sql[2] += " and nvl(w.NACCEPTPOTYPEID,9999) > 0 ";
	//	sql[2] += " and m.ncontractid=w.ncontractid(+) ";
		if (qInfo.getQueryPurpose() == QuestContractInfo.TX)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.TX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			/*//贴现客户名称
			if ((qInfo.getDiscountClientName() != null) && (qInfo.getDiscountClientName().length() > 0))
			{
				buf.append(" and c.discountClientName like '%" + qInfo.getDiscountClientName() + "%'");
			}*/

			//贴现汇票种类
			if (qInfo.getTsDiscountTypeID()>0)
				{
				System.out.println("TsDiscountTypeID="+qInfo.getTsDiscountTypeID());		
				buf.append(" and w.NACCEPTPOTYPEID="+qInfo.getTsDiscountTypeID() );
				}
		}

		else if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			
		}
		else
		{
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.TX;
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and y.lateRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and y.lateRate <= " + qInfo.getMaxRate();
			}
		}
		sql[2] += " and                                                 ";
		sql[2] += " c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id";
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+)";
		
		//（上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose()== QuestContractInfo.DB)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		}
		else
		{
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.DB;
		}

		//贷款种类
		if (qInfo.getLoanTypeID() > 0)
			buf.append(" and c.nTypeID=" + qInfo.getLoanTypeID());
		if  (qInfo.getLoanTypeID() <= 0)
	         buf.append(" and c.nTypeID<>" +LOANConstant.LoanType.RZZL );
		
		
		//如果是转贴现（查询条件增加）
		if( qInfo.getQueryPurpose() == QuestContractInfo.ZTX )
		{		
			if(qInfo.getInOrOut() > 0)
				buf.append(" and c.NINOROUT=" + qInfo.getInOrOut());
			if(qInfo.getTransDiscountType() > 0)
				buf.append(" and c.NDISCOUNTTYPEID=" + qInfo.getTransDiscountType());
			if(qInfo.getTransDiscountTerm() > 0)
				buf.append(" and c.DTENDDATE - c.DTSTARTDATE =" + qInfo.getTransDiscountTerm());			
		}
		
		//合同编号开始
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//合同编号结束
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		/***************添加国机的变更 2003-3-30 qqgd***************/
		//合同状态
		if (!qInfo.isShowEnd())
		{
			buf.append(" and c.nStatusID<>" + LOANConstant.ContractStatus.FINISH);
		}

		/************为了国机加的判断，限制状态 2004-4-23 qqgd *********/
		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++)
		{
			//去掉已提交
			if(contractStatusVal[i]==LOANConstant.ContractStatus.SUBMIT){
				continue;
			}
			//去掉呆滞
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.DELAYDEBT){
  				continue;
  			}
  			//去掉呆账
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.BADDEBT){
  				continue;
  			}
  			//去掉已拒绝
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.REFUSE){
  				continue;
  			}
  			
  		    //去掉已逾期
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.EXTEND){
  				continue;
  			}
  			//去掉已展期
  			if(contractStatusVal[i]==LOANConstant.ContractStatus.OVERDUE){
  				continue;
  			}
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ",";
		}
	if(qInfo.getStatusIDs()== null ||qInfo.getStatusIDs().equals("") || qInfo.getStatusIDs().trim().equals("-1")){
		    buf.append(" and c.nStatusID in (" + strStatus + ")");
		}
		else{
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
			
	}

		/*if (qInfo.getStatusID() > 0)
			{
			System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
			}*/
		//minzhao20090505修改将合同状态增加为多状态查询
		//出票人
		if (qInfo.getBillDrawer()!=null && qInfo.getBillDrawer().length()>0 ){
			String test = qInfo.getBillDrawer();
			
			try {
				test = new String(test.getBytes("ISO-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			buf.append(" and c.DISCOUNTCLIENTNAME like '%" +test+"%'");
		}
		//借款单位
		if (qInfo.getBorrowClientID() > 0){
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		}
		//贴现申请单位结束
		if (qInfo.getMaxborrowClientID() > 0){
			buf.append(" and c.nBorrowClientID <=" + qInfo.getMaxborrowClientID());
		}
		
		//贴现申请单位开始
		if (qInfo.getMinborrowClientID() > 0){
			buf.append(" and c.nBorrowClientID >=" + qInfo.getMinborrowClientID());
		}
		
		//借款单位账号
		if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		//客户分类
//		if (qInfo.getLoanClientTypeID() > 0)
//			buf.append(" and c1.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

		//主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		//委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());

		//委托单位账号
		if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		//(贴现)贷款金额结束
		if (qInfo.getMaxLoanAmount() > 0){
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));
		}
		//(贴现)贷款金额开始
		if (qInfo.getMinLoanAmount() > 0){
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));
		}
		
		//贴现实付金额结束
		if (qInfo.getMaxCheckAmount() >0){
			buf.append(" and c.mCheckAmount<=" + DataFormat.formatAmount(qInfo.getMaxCheckAmount()));
		}
		//贴现实付金额开始
		if (qInfo.getMinCheckAmount() > 0){
			buf.append(" and c.mCheckAmount>=" + DataFormat.formatAmount(qInfo.getMinCheckAmount()));
		}
		
		//贷款日期开始
		if (qInfo.getMaxStartDate() != null){
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");
		}
		//贷款日期结束
		if (qInfo.getMinStartDate() != null){
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");
		}
		//贷款结束日期结束
		if (qInfo.getMaxEndDate() != null){
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");
		}
		//贷款结束日期开始
		if (qInfo.getMinEndDate() != null){
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
		}
		//贴现日期结束
		if (qInfo.getMaxDiscountDate() != null){
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");
		}

		//贴现日期开始
		if (qInfo.getMinDiscountDate() != null){
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期结束
		if (qInfo.getMaxDisccountInputDate() != null){
			buf.append(" and TRUNC(c.dtInputDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDisccountInputDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期开始
		if (qInfo.getMinDisccountInputDate() != null){
			buf.append(" and TRUNC(c.dtInputDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDisccountInputDate()) + "','yyyy-mm-dd') ");
		}
		//买方付息结束
		if (qInfo.getMaxPayerRate() > 0){
			buf.append(" and c.mPurchaserAmount<=" + DataFormat.formatAmount(qInfo.getMaxPayerRate()));
		}

		//买方付息开始
		if (qInfo.getMinPayerRate() > 0){
			
			buf.append(" and c.mPurchaserAmount>=" + DataFormat.formatAmount(qInfo.getMinPayerRate()));
			
		}
		
		//贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum=" + qInfo.getIntervalNum());

		//保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");

		//信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		//是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		//是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		//是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		//贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());

		//按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		//按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		//按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		//管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
		
		//办事处
		if(qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
		
        //币种
		if(qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
		
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
				oBuf.append(" \n order by c1.Name" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c2.Name" + strDesc);
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
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode" + strDesc);
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

				//胡志强修改(2004-03-09)
			case 16 :
				oBuf.append(" \n order by a.overdueAmount" + strDesc);
				break;
			case 17 :
				oBuf.append(" \n order by b.punishInterest" + strDesc);
				break;

			case 20 :
				oBuf.append(" \n order by u2.sName" + strDesc);
				break;
			case 21 :
				oBuf.append(" \n order by c3.sName" + strDesc);
				break;
			case 23 :
				oBuf.append(" \n order by c.mPurchaserAmount" + strDesc);
				break;
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		sql[3] = oBuf.toString();
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		System.out.println("sql[0]==========="+sql[0]);
		System.out.println("sql[1]==========="+sql[1]);
		System.out.println("sql[2]==========="+sql[2]);
		System.out.println("sql[3]==========="+sql[3]);
		
		
		return sql;
	}
	
	public String getAttornmentOrderSQL(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //贷款种类
				oBuf.append(" \n order by code" + strDesc);
				break;
			case 2 : //贷款种类
				oBuf.append(" \n order by AttornmentAmount" + strDesc);
				break;
			case 3 : //贷款种类
				oBuf.append(" \n order by RepurchaseStartDate" + strDesc);
				break;
			case 4 : //贷款种类
				oBuf.append(" \n order by RepurchaseEndDate" + strDesc);
				break;
			case 5 : //贷款种类
				oBuf.append(" \n order by InputDate" + strDesc);
				break;
			case 6 : //贷款种类
				oBuf.append(" \n order by StatusId" + strDesc);
				break;
			default :
				oBuf.append(" \n order by ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	public PageLoader queryAttornment(AttornmentQueryInfo qInfo) throws Exception
	{
		String[] sql = getAttornmentSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			sql[1],
			sql[0],
			sql[2],
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.loan.attornmentapply.dataentity.AttornmentApplyInfo",
			null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	public QuerySumInfo queryAttornmentSum(AttornmentQueryInfo qInfo) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		QuerySumInfo sumInfo = new QuerySumInfo();
		return sumInfo;
	}

	public Collection queryPerform(long lID, long lParam, long lDesc) throws Exception
	{
		Vector v = new Vector();
		String strSQL = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanPayNoticeDao noticeDao = new LoanPayNoticeDao();

		try
		{
			Timestamp tsToday = Env.getSystemDate();
			con = Database.getConnection();
			strSQL =
				"select mAmount, 0 as minterest ,dtExecute,dtModify, 1 as payType,1 as type,NLOANNOTEID,0 as mcommission "
					+ " from sett_transgrantloan where nStatusID>="
					+ SETTConstant.TransactionStatus.CHECK
					+ " and nLoancontractID="
					+ lID
					+ " union all "
					+ " select mAmount, MREALINTEREST as mInterest ,dtExecute,dtModify, 0 as payType,1 as type,NLOANNOTEID,mrealcommission as mcommission "
					+ " from sett_transRepaymentloan where nStatusID>="
					+ SETTConstant.TransactionStatus.CHECK
					+ " and nLoancontractID="
					+ lID
					+ " union all "
					+ " select 0 as mAmount,a.mInterest,a.dtExecute,a.dtExecute as dtModify,0 as payType,1 as type,b.Al_nLoanNoteID as nLoanNoteID,0 as mcommission "
					+ " from sett_TransInterestSettlement a,sett_SubAccount b "
					+ " where a.nSubAccountID=b.ID and a.nInterestType = 1 and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)"
					+ " and b.Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
					+ " union all "
					+ " select mAmount,0 as mInterest,dtExecute,dtModify,0 as payType,1 as type,-1 as nLoanNoteID,0 as mcommission "
					+ " from Sett_Transrepaymentdiscount "
					+ " where nStatusID>="
					+ SETTConstant.TransactionStatus.CHECK
					+ " and nDisCountContractID="
					+ lID
					+ " union all "
					+ " select mDisCountAmount as mAmount, 0 as mInterest,dtExecute,dtModify,1 as payType,1 as type,-1 as nLoanNoteID,0 as mcommission "
					+ " from Sett_TransGrantdiscount "
					+ " where nStatusID>="
					+ SETTConstant.TransactionStatus.CHECK
					+ " and nDisCountContractID="
					+ lID
					+ " union all "
					+ " select 0 as mAmount,0 mInterest,a.dtExecute,a.dtExecute as dtModify,0 as payType,1 as type,b.Al_nLoanNoteID as nLoanNoteID,a.mInterest as mcommission "
					+ " from sett_TransInterestSettlement a,sett_SubAccount b "
					+ " where a.nSubAccountID=b.ID and a.nInterestType = "+SETTConstant.InterestFeeType.COMMISION+" and a.nIsKeepAccount = 1 "
					+ " and a.nStatusID in  ("+SETTConstant.TransactionStatus.CHECK+","+SETTConstant.TransactionStatus.NOTSIGN+","+SETTConstant.TransactionStatus.SIGN+","+SETTConstant.TransactionStatus.CONFIRM+")"
					+ " and b.Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") ";
			
			String strDesc = (lDesc == 1) ? " desc " : "";

			switch ((int) lParam)
			{
				case 1 : //贷款种类
					strSQL += " \n order by mAmount" + strDesc;
					break;
				case 2 : //贷款种类
					strSQL += " \n order by mInterest" + strDesc;
					break;
				case 3 : //贷款种类
					strSQL += " \n order by dtExecute" + strDesc;
					break;
				case 4 : //贷款种类
					strSQL += " \n order by mcommission" + strDesc;
					break;
				case 5 : //贷款种类
					strSQL += " \n order by payType" + strDesc;
					break;
				default :
					strSQL += " \n order by dtExecute " ;
					break;
			}
			log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				QueryPerformInfo info = new QueryPerformInfo();
				info.setPerformAmount(rs.getDouble("mAmount"));
				info.setInterest(rs.getDouble("mInterest"));
				info.setPerformDate(rs.getTimestamp("dtExecute"));
				info.setModifyDate(rs.getTimestamp("dtModify"));
				info.setPayType(rs.getLong("payType"));
				info.setType(rs.getLong("Type"));
				info.setLoanNoteID(rs.getLong("nLoanNoteID"));
				info.setMcommission(rs.getDouble("mcommission"));
				double interestRate = noticeDao.getRateValue(Constant.RateType.INTEREST, rs.getLong("nLoanNoteID"), tsToday).getLateRate();//取得合同的执行利率
				info.setPerformRate(interestRate);
				v.add(info);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		catch (Exception e)
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			e.printStackTrace();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return v;
	}
	public PageLoader queryDiscountCredence(QuestCredenceInfo cInfo) throws Exception
	{
		String[] sql = getDiscountCredenceSQL(cInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.discount.dataentity.DiscountCredenceInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	
	public PageLoader queryTransDiscountCredence(long contractID, long nTypeID) throws Exception
	{
		String[] sql = getTransDiscountCredenceSQL(contractID,nTypeID);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.transdiscountcredence.dataentity.TransDiscountCredenceInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getDiscountCredenceSQL(QuestCredenceInfo cInfo)
	{
		String[] sql = new String[4];
		
		StringBuffer queryCondition = new StringBuffer();
		
		
		//查询凭证时的合同ID
		
		if (cInfo.getContractID() > 0  ){
			queryCondition.append(" and  l.NCONTRACTID='" + cInfo.getContractID() + "'");
		}
		
		//合同编号开始
		if (cInfo.getMinContractCode() != null && cInfo.getMinContractCode().length() > 0){
			queryCondition.append(" and c.sContractCode>='" + cInfo.getMinContractCode() + "'");
		}
		//合同编号结束
		if (cInfo.getMaxContractCode() != null && cInfo.getMaxContractCode().length() > 0){
			queryCondition.append(" and c.sContractCode<='" + cInfo.getMaxContractCode() + "'");
		}
		
		
		//贴现申请单位开始
		if (cInfo.getMinborrowClientID() > 0  ){
			queryCondition.append(" and  c.nborrowclientid>='" + cInfo.getMinborrowClientID() + "'");
		}
		
		//贴现申请单位结束
		if (cInfo.getMaxborrowClientID() > 0  ){
			queryCondition.append(" and  c.nborrowclientid<='" + cInfo.getMaxborrowClientID() + "'");
		}
		//贴现日期开始
		if (cInfo.getMinDiscountDate() != null){
			queryCondition.append(" and TRUNC(c.dtDiscountDate)>= To_Date('" + DataFormat.getDateString(cInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");
		}
		//贴现日期结束
		if (cInfo.getMaxDiscountDate() != null){
			queryCondition.append(" and TRUNC(c.dtDiscountDate)<= To_Date('" + DataFormat.getDateString(cInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");
		}
		
		//录入日期开始
		if (cInfo.getMinDisccountCredenceInputDate() != null){
			queryCondition.append(" and TRUNC(l.DTINPUTDATE) >= To_Date('" + DataFormat.getDateString(cInfo.getMinDisccountCredenceInputDate()) + "','yyyy-mm-dd') ");
		}
		//录入日期结束
		if (cInfo.getMaxDisccountCredenceInputDate() != null){
			queryCondition.append(" and TRUNC(l.DTINPUTDATE) <= To_Date('" + DataFormat.getDateString(cInfo.getMaxDisccountCredenceInputDate()) + "','yyyy-mm-dd') ");
		}
		
		long credenceStatusVal[] = LOANConstant.DiscountCredenceStatus.getAllCode(cInfo.getOfficeID(),cInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < credenceStatusVal.length; i++)
		{	
			if(credenceStatusVal[i]==LOANConstant.DiscountCredenceStatus.SUBMIT){
				
				continue;
			}
			strStatus += credenceStatusVal[i];
			if ((credenceStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if(cInfo.getCredenceStatusIDs()==null || cInfo.getCredenceStatusIDs().equals("") || cInfo.getCredenceStatusIDs().equals("-1")){
			
			queryCondition.append(" and l.nStatusID in (" + strStatus + ")");
		
		}
		
		else{
			
			queryCondition.append(" and l.nStatusID in (" + cInfo.getCredenceStatusIDs()+")");
			
		}
		
		
		

		//select
		sql[0] =
			" l.ID,l.sCode as code,c.sContractCode as DiscountContractCode,"
				+ " l.sApplyClient as applyClientName,l.mAmount as BillAmount,"
				+ " c.mDiscountRate as DiscountRate,l.mInterest as BillInterest,l.PURCHASERINTEREST as PurchaserInterest ,l.dtInputDate as InputDate,"
				+ " c.dtDiscountDate as DiscountDate,l.nStatusID as statusID,u.sName as InputUserName ";

		//from
		sql[1] = " Loan_DiscountCredence l,loan_contractForm c,userInfo u";
		

		//where
		sql[2] = " c.ID(+)=l.nContractID and u.id(+)=l.nInputUserId and c.ntypeid!=6 and l.nofficeid="+cInfo.getOfficeID()+"and l.ncurrencyid="+cInfo.getCurrencyID()+ queryCondition.toString();

		sql[3] = " order by DiscountContractCode ";
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
				oBuf.append(" \n order by DISCOUNTCONTRACTCODE" + strDesc);
				break;
			case 2 : //申请单位
				oBuf.append(" \n order by APPLYCLIENTNAME " + strDesc);
				break;
			case 3 : //贴现金额
				oBuf.append(" \n order by BILLAMOUNT" + strDesc);
				break;
			case 4 : //贴现利率
				oBuf.append(" \n order by DISCOUNTRATE" + strDesc);
				break;
			case 5 : //贴现日期
				oBuf.append(" \n order by DISCOUNTDATE" + strDesc);
				break;
			case 6 : //凭证状态
				oBuf.append(" \n order by STATUSID " + strDesc);
				break;
			case 7 : //贴现利息
				oBuf.append(" \n order by BILLINTEREST"  + strDesc);
				break;
			case 8 : //买方付息
				oBuf.append(" \n order by PURCHASERINTEREST " + strDesc);
				break;
			case 9 : //录入人
				oBuf.append(" \n order by INPUTUSERNAME " + strDesc);
				break;
			case 10 : //录入日期
				oBuf.append(" \n order by INPUTDATE " + strDesc);
				break;
			case 11 : //贴现凭证号
				oBuf.append(" \n order by CODE " + strDesc);
				break;
			default :
				oBuf.append(" \n order by ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}

	private String[] getTransDiscountCredenceSQL(long cID,long nTypeID)
	{
		String[] sql = new String[4];

		//select
		sql[0] =
			" l.ID,c.sContractCode as ContractCode,l.SCODE as Code,"
				+ " l.sApplyClient as ApplyClient,l.mAmount as Amount,"
				+ " c.mDiscountRate as DiscountRate,l.mInterest as Interest,l.NTYPEID as TypeID,"
				+ " c.dtDiscountDate as DiscountDate,l.nStatusID as StatusID,l.NNEXTCHECKUSERID as NextCheckUserID ";

		//from
		sql[1] = " Loan_DiscountCredence l,loan_contractForm c,userInfo u";

		//where
		sql[2] = " c.ID = l.nContractID and u.id(+)=l.NNEXTCHECKUSERID and l.nStatusID>0 " 
			   + " and c.ID=" + cID 
			   + " and l.NTYPEID=" + nTypeID ;

		sql[3] = " order by l.ID ";
		return sql;
	}
	
	public String getTransDiscountCredenceOrder(long lOrder, long lDesc)
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
				oBuf.append(" \n order by c.dtDiscountDate" + strDesc);
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
	/**
	 * 查询放款通知单通知单
	 * @param payNInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryPayNotice(QuestPayNoticeInfo payNInfo) throws Exception
	{
		String[] sql = getPayNoticeSQL1(payNInfo);//自营和银团贷款
		String[] sql2 =getPayNoticeSQL2(payNInfo);//委托贷款
		
		String [] sql3= new String [4];
		
		
		String NLoanType = payNInfo.getNLoanType();
		
		boolean flag = isLoan_WT(NLoanType, payNInfo.getNOfficeID(), payNInfo.getNCurrencyID());
		
		
		//贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
		if(!flag && payNInfo.getConsignIDFrom()<0 && payNInfo.getConsignIDTo()<0){
			sql3[0]="*";
			sql3[1] = "(select " + sql[0] +" from " +sql[1] +" where " + sql[2]+")";
			sql3[2]="1=1";
			sql3[3]="";
		}else if(payNInfo.getConsignIDFrom()>0 || payNInfo.getConsignIDTo()>0){
			sql3[0]="*";
			sql3[1] = "(select " + sql2[0] +" from " +sql2[1] +" where " + sql2[2]+")";
			sql3[2]="1=1";
			sql3[3]="";
			
		}
		else 
		{ sql3[0]="*";
		  sql3[1] = "(select " + sql[0] +" from " +sql[1] +" where "  + sql[2] + " union "+"select " + sql2[0] +" from " +sql2[1] +" where "  + sql2[2]+")";
		  sql3[2]="1=1";
		  sql3[3]="";
		  
		}
		
	
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql3[1], sql3[0], sql3[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo", null);
		pageLoader.setOrderBy(" " + sql3[3] + " ");
		return pageLoader;
	}
	
	
	/**
	 * 判断子类型是否为委托贷款
	 * @param subLoanType 贷款子类型
	 * @param officeID  办事处
	 * @param currencyID  币种
	 * @return
	 */
	public boolean isLoan_WT(String subLoanType, long officeID, long currencyID){
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String sql= null;
		if(subLoanType==null||("").equals(subLoanType)){
			return true;
		}
		try{
		
			sql="select  loantypeid  from loan_loantypesetting  where statusid=1 and currencyid="+currencyID+" and officeid="+officeID+" and id in ("+subLoanType+")";
			con = Database.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{	
			  if(rs.getLong("loantypeid")== LOANConstant.LoanType.WT){
				  
				  return true;
				 
			  }
			
		   }
		
	    }catch (Exception e){
		
		  // TODO Auto-generated catch block
		   e.printStackTrace();
		  try {
			cleanup(ps);
			cleanup(con);
			cleanup(rs);
		   } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
		

	}finally{
		
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

	private String[] getPayNoticeSQL1(QuestPayNoticeInfo payNoticeInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();
		
		//贷款合同、业务汇总查询 放款通知单明细的合同ID
		
		if(payNoticeInfo.getContractID()>0){
			
				buf.append(" and c.id="+payNoticeInfo.getContractID());
		
		}
		
		//合同编号
		if(payNoticeInfo.getContractCodeFrom()!=null && payNoticeInfo.getContractCodeFrom().length()>0 ){
			
				buf.append(" and c.sContractCode>='"+payNoticeInfo.getContractCodeFrom()+"'");
		
		}
		
		if(payNoticeInfo.getContractCodeTo()!=null && payNoticeInfo.getContractCodeTo().length()>0 ){
			
			buf.append(" and c.sContractCode<='"+payNoticeInfo.getContractCodeTo()+"'");
	
	    }
		
		//借款单位
		if(payNoticeInfo.getLoanClientIDFrom()>0){
			
			buf.append(" and c.nBorrowClientID>="+payNoticeInfo.getLoanClientIDFrom());
	
	    }
        if(payNoticeInfo.getLoanClientIDTo()>0){
			
			buf.append(" and c.nBorrowClientID<="+payNoticeInfo.getLoanClientIDTo());
	
	    }
        
       /* //委托单位
		if(payNoticeInfo.getConsignIDFrom()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID>="+payNoticeInfo.getConsignIDFrom());
	
	    }
        if(payNoticeInfo.getConsignIDTo()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID<="+payNoticeInfo.getConsignIDTo());
	
	    }*/
        
        //放款金额
        if(payNoticeInfo.getMPayAmountFrom()>0){
			
			buf.append(" and l.mAmount>="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountFrom()));
	
	    }
        if(payNoticeInfo.getMPayAmountTo()>0){
			
			buf.append(" and l.mAmount<="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));
	
	    }
     
       //放款利率
        if(payNoticeInfo.getMPayInterestFrom()>0){
			
			buf.append(" and l.minterestrate>="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestFrom()));
	
	    }
        if(payNoticeInfo.getMPayInterestTo()>0){
			
			buf.append(" and l.minterestrate<="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestTo()));
	
	    }
        
        
        //放贷日期开始
  		if (payNoticeInfo.getDtLoanPayDateFrom()!= null){
  			buf.append(" and TRUNC(l.DTOUTDATE) >= To_Date('" + payNoticeInfo.getDtLoanPayDateFrom() + "','yyyy-mm-dd') ");
  		}
  		
  		if (payNoticeInfo.getDtLoanPayDateTo() != null){
  			buf.append(" and TRUNC(l.DTOUTDATE) <= To_Date('" +payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
  		}
  		
  		 //还款日期
  		if (payNoticeInfo.getDtRepayDateFrom()!= null){
  			buf.append(" and TRUNC(l.dtEnd) >= To_Date('" + payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
  		}
  		
  		if (payNoticeInfo.getDtRepayDateTo() != null){
  			buf.append(" and TRUNC(l.dtEnd) <= To_Date('" +payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
  		}

        //录入日期
  		if (payNoticeInfo.getDtInputDateFrom()!= null){
  			buf.append(" and TRUNC(l.dtInputDate) >= To_Date('" + payNoticeInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
  		}
  		
  		if (payNoticeInfo.getDtInputDateTo() != null){
  			buf.append(" and TRUNC(l.dtInputDate) <= To_Date('" +payNoticeInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
  		}
        //
		//贷款类型
       if(payNoticeInfo.getNLoanType()==null || payNoticeInfo.getNLoanType().trim().equals("-1") ){
        buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
        		"  a.loantypeid  in (1,5) and a.id=b.subloantypeid " +
        		"and b.currencyid="+payNoticeInfo.getNCurrencyID()+" and b.officeid="+payNoticeInfo.getNOfficeID()+")");
       } 
  		
       else{
  			
  			buf.append(" and c.nSubTypeID in (" + payNoticeInfo.getNLoanType() + ") and c.nSubTypeID not in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
  	        		"  a.loantypeid =2 and a.id=b.subloantypeid " +
  	        		"and b.currencyid="+payNoticeInfo.getNCurrencyID()+" and b.officeid="+payNoticeInfo.getNOfficeID()+")");
  			
  		}
  		
  		//放款通知单状态
  		
  	    long loanPayNoticeStatus[]=LOANConstant.LoanPayNoticeStatus.getAllCode(payNoticeInfo.getNOfficeID(),payNoticeInfo.getNCurrencyID());
  		String loanTypeList="";
  		for ( int i=0;i<loanPayNoticeStatus.length;i++ )
  		{
  			
  			if(loanPayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
  				continue;
  			}
  			loanTypeList+=loanPayNoticeStatus[i];
  			if((loanPayNoticeStatus.length-i)>1){
  				
  				loanTypeList+=",";
  			}
  			
  		}
  		if(payNoticeInfo.getNPayNoticeStatus()==null ||payNoticeInfo.getNPayNoticeStatus().trim().equals("-1")){
  			buf.append(" and l.nStatusID in (" + loanTypeList + ")");
  		}
  		
  		else{
  			
  			buf.append(" and l.nStatusID in (" + payNoticeInfo.getNPayNoticeStatus() + ")");
  			
  		}
  		
  		
		//select
		sql[0] =
			" l.ID,l.sCode as Code,c.sContractCode as ContractCode,"
				+ "d.name  as loanClientName,'' as ConsignClientName,c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName, "
				+ " l.mAmount as amount,l.dtOutDate as outDate,l.dtEnd as inDate,"
				+ " l.minterestrate as InterestRate,l.nContractid as contractID,"
				+ " l.nDrawNoticeID as DrawNoticeID,"
				+ " c.nTypeID as LoanTypeID,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

		//from
		sql[1] = " loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt";

		//where
		sql[2] = " u.id=l.NINPUTUSERID and lt.id = c.nsubtypeid and c.ID=l.nContractID and d.id =c.nBorrowClientID  and l.NOFFICEID="+payNoticeInfo.getNOfficeID()+"and l.NCURRENCYID="+payNoticeInfo.getNCurrencyID()+buf.toString();

		
		
		return sql;
		
	}
	
	
	
	
	private String[] getPayNoticeSQL2(QuestPayNoticeInfo payNoticeInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();
		

		//贷款合同、业务汇总查询 放款通知单明细的合同ID
		
		if(payNoticeInfo.getContractID()>0){
			
				buf.append(" and c.id="+payNoticeInfo.getContractID());
		
		}
		
		
		//合同编号
		if(payNoticeInfo.getContractCodeFrom()!=null && payNoticeInfo.getContractCodeFrom().length()>0 ){
			
				buf.append(" and c.sContractCode>='"+payNoticeInfo.getContractCodeFrom()+"'");
		
		}
		
		if(payNoticeInfo.getContractCodeTo()!=null && payNoticeInfo.getContractCodeTo().length()>0 ){
			
			buf.append(" and c.sContractCode<='"+payNoticeInfo.getContractCodeTo()+"'");
	
	    }
		
		//借款单位
		if(payNoticeInfo.getLoanClientIDFrom()>0){
			
			buf.append(" and c.nBorrowClientID>="+payNoticeInfo.getLoanClientIDFrom());
	
	    }
        if(payNoticeInfo.getLoanClientIDTo()>0){
			
			buf.append(" and c.nBorrowClientID<="+payNoticeInfo.getLoanClientIDTo());
	
	    }
        
        //委托单位
		if(payNoticeInfo.getConsignIDFrom()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID>="+payNoticeInfo.getConsignIDFrom());
	
	    }
        if(payNoticeInfo.getConsignIDTo()>0){
			
			buf.append(" and c.NCONSIGNCLIENTID<="+payNoticeInfo.getConsignIDTo());
	
	    }
        
        //放款金额
        if(payNoticeInfo.getMPayAmountFrom()>0){
			
			buf.append(" and l.mAmount>="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountFrom()));
	
	    }
        if(payNoticeInfo.getMPayAmountTo()>0){
			
			buf.append(" and l.mAmount<="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));
	
	    }
     
       //放款利率
        if(payNoticeInfo.getMPayInterestFrom()>0){
			
			buf.append(" and l.minterestrate>="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestFrom()));
	
	    }
        if(payNoticeInfo.getMPayInterestTo()>0){
			
			buf.append(" and l.minterestrate<="+DataFormat.formatAmount(payNoticeInfo.getMPayInterestTo()));
	
	    }
        
        
        //放贷日期开始
  		if (payNoticeInfo.getDtLoanPayDateFrom()!= null){
  			buf.append(" and TRUNC(l.DTOUTDATE) >= To_Date('" + payNoticeInfo.getDtLoanPayDateFrom() + "','yyyy-mm-dd') ");
  		}
  		
  		if (payNoticeInfo.getDtLoanPayDateTo() != null){
  			buf.append(" and TRUNC(l.DTOUTDATE) <= To_Date('" +payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
  		}
  		
  		 //还款日期
  		if (payNoticeInfo.getDtRepayDateFrom()!= null){
  			buf.append(" and TRUNC(l.dtEnd) >= To_Date('" + payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
  		}
  		
  		if (payNoticeInfo.getDtRepayDateTo() != null){
  			buf.append(" and TRUNC(l.dtEnd) <= To_Date('" +payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
  		}

        //录入日期
  		if (payNoticeInfo.getDtInputDateFrom()!= null){
  			buf.append(" and TRUNC(l.dtInputDate) >= To_Date('" + payNoticeInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
  		}
  		
  		if (payNoticeInfo.getDtInputDateTo() != null){
  			buf.append(" and TRUNC(l.dtInputDate) <= To_Date('" +payNoticeInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
  		}
       
  		 //
		//贷款类型
       if(payNoticeInfo.getNLoanType()==null || payNoticeInfo.getNLoanType().trim().equals("-1") ){
        buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
        		"  a.loantypeid  in (2) and a.id=b.subloantypeid " +
        		"and b.currencyid="+payNoticeInfo.getNCurrencyID()+" and b.officeid="+payNoticeInfo.getNOfficeID()+")");
       } 
        
  		else{
  			
  			buf.append(" and c.nSubTypeID in (" + payNoticeInfo.getNLoanType() + ")");
  			
  		}
  		
  		//放款通知单状态
  		
  	    long loanPayNoticeStatus[]=LOANConstant.LoanPayNoticeStatus.getAllCode(payNoticeInfo.getNOfficeID(),payNoticeInfo.getNCurrencyID());
  		String loanTypeList="";
  		for ( int i=0;i<loanPayNoticeStatus.length;i++ )
  		{
  			
  			if(loanPayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
  				continue;
  			}
  			loanTypeList+=loanPayNoticeStatus[i];
  			if((loanPayNoticeStatus.length-i)>1){
  				
  				loanTypeList+=",";
  			}
  			
  		}
  		if(payNoticeInfo.getNPayNoticeStatus()==null ||payNoticeInfo.getNPayNoticeStatus().equals("-1") ){
  				buf.append(" and l.nStatusID in (" + loanTypeList + ")");
  		}
  		else{
  			
  			buf.append(" and l.nStatusID in (" + payNoticeInfo.getNPayNoticeStatus() + ")");
  			
  		}
  		
  		
		//select
		sql[0] =
			" l.ID,l.sCode as Code,c.sContractCode as ContractCode,d.name as LoanClientName,"
				+ " e.name as ConsignClientName,c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName,"
				+ " l.mAmount as amount,l.dtOutDate as outDate,l.dtEnd as inDate,"
				+ " l.minterestrate as InterestRate,l.nContractid as contractID,"
				+ " l.nDrawNoticeID as DrawNoticeID,"
				+ " c.nTypeID as LoanTypeID,"
				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

		//from
		sql[1] = " loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt,client_clientinfo e";

		//where
		sql[2] = " u.id=l.NINPUTUSERID and lt.id = c.nsubtypeid and c.ID=l.nContractID and d.id=c.nborrowclientID  and e.id =c.nConsignClientID  and l.NOFFICEID="+payNoticeInfo.getNOfficeID()+"and l.NCURRENCYID="+payNoticeInfo.getNCurrencyID()+buf.toString();

		
		
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
				oBuf.append(" \n order by CODE" + strDesc);
				break;
			case 2 : //合同编号
				oBuf.append(" \n order by CONTRACTCODE" + strDesc);
				break;
			case 3 : //借款单位
				oBuf.append(" \n order by LOANCLIENTNAME" + strDesc);
				break;
			case 12 : //委托单位
				oBuf.append(" \n order by CONSIGNCLIENTNAME" + strDesc);
				break;
			case 5 : //放款金额
				oBuf.append(" \n order by AMOUNT " + strDesc);
				break;
			case 11 : //放款利率
				oBuf.append(" \n order by INTERESTRATE" + strDesc);
				break;
			case 4 : //合同金额
				oBuf.append(" \n order by LOANAMOUNT" + strDesc);
				break;
			case 6 : //放贷日期
				oBuf.append(" \n order by OUTDATE" + strDesc);
				break;
			case 7 : //还款日期
				oBuf.append(" \n order by INDATE " + strDesc);
				break;
			case 8 : //提交日期
				oBuf.append(" \n order by INPUTDATE" + strDesc);
				break;
			case 9 : //放款通知单状态
				oBuf.append(" \n order by STATUSID " + strDesc);
				break;
			case 10 : //录入人
				oBuf.append(" \n order by INPUTUSERNAME" + strDesc);
				break;
			case 13 : //贷款类型
				oBuf.append(" \n order by LOANTYPENAME" + strDesc);
				break;	
			default :
				oBuf.append(" \n order by ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
		/**
		 * 查询放款通知单合同总额和放款总额
		 * @param payNoticeInfo
		 * @return
		 * @throws Exception
		 */
		public QuerySumInfo queryLoanPayNoticeSum(QuestPayNoticeInfo payNoticeInfo)throws Exception{
			
			
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			QuerySumInfo sumInfo = new QuerySumInfo();
			String[] sql = getPayNoticeSQL1(payNoticeInfo);
			String[] sql1 = getPayNoticeSQL2(payNoticeInfo);
			String strSQL = "";
			String NLoanType = payNoticeInfo.getNLoanType();
			boolean flag = isLoan_WT(NLoanType, payNoticeInfo.getNOfficeID(), payNoticeInfo.getNCurrencyID());
			try
			{
				if(!flag && payNoticeInfo.getConsignIDFrom()<0 && payNoticeInfo.getConsignIDTo()<0){
					strSQL = "select sum(c.mExamineAmount) as TotalLoanAmount,sum(l.mAmount) as TotalPayNoticeAmount from " + sql[1] + " where " + sql[2];
				}
				else if (payNoticeInfo.getConsignIDFrom()>0 || payNoticeInfo.getConsignIDTo()>0){
					
					strSQL = "select sum(c.mExamineAmount) as TotalLoanAmount,sum(l.mAmount) as TotalPayNoticeAmount from " + sql1[1] + " where " + sql1[2];
					
				}else {
					
					strSQL="select nvl(sum(a.ToTalLoanAmount),0) as ToTalLoanAmount, nvl(sum(a.TotalPayNoticeAmount),0) as TotalPayNoticeAmount  from (select sum(c.mExamineAmount) as ToTalLoanAmount,sum(l.mAmount) as TotalPayNoticeAmount from "+sql[1]+" where "+ sql[2]+" union "+"select sum(c.mExamineAmount) as ToTalLoanAmount,sum(l.mAmount) as TotalPayNoticeAmount from "+sql1[1]+" where "+ sql1[2]+") a";
					
				}
				log.print(strSQL);

				con = Database.getConnection();
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					sumInfo.setAllRecord(rs.getLong(1));
					sumInfo.setTotalApplyAmount(rs.getDouble("TotalLoanAmount"));
					sumInfo.setTotalPayAmount(rs.getDouble("TotalPayNoticeAmount"));
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
	
	
	
	public PageLoader queryAssureChargeNotice(QueryAssureNoticeInfo qInfo) throws Exception
	{
		String[] sql = getAssureChargeNoticeSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getAssureChargeNoticeSQL(QueryAssureNoticeInfo qInfo)
	{
		String[] sql = new String[4];

		//select
		sql[0] =
			" l.ID,l.currencyId as currencyId,l.officeId as officeId,l.executeDate as executeDate," 
				+"c.sContractCode as ContractCode,c.nBorrowClientId as clientID,c.nInterValNum as intervalNum,"
				+ " c2.Name as clientName,ui.sName as inputUserName,l.inputDate as inputDate,"
				+ " l.RecognizanceAmount as recognizanceAmount,"
				+ " l.StartDate as startDate,l.endDate as endDate,l.Contractid as contractID,"
				+ " l.code as code,l.AssureChargeAmount as assureChargeAmount,l.InputUserID as inputUserID,"
				+ " l.NextCheckLevel as nextCheckLevel,l.StatusID as statusID,l.IsLowLevel as isLowLevel,un.sName as nextCheckUserName ";

		//from
		sql[1] = " LOAN_ASSURECHARGEFORM l,Client_clientinfo c2,loan_contractForm c,userInfo ui,userInfo un";

		//where
		 if(qInfo.getContractId()>0){
		sql[2] = " c.ID(+)=l.ContractID and c2.id(+)=c.nBorrowClientID and un.id(+)=l.NEXTCHECKUSERID and ui.id(+)=l.inputUserId " + " and c.ID=" + qInfo.getContractId();
		 }
		 else{
		 sql[2] = " c.ID(+)=l.ContractID and c2.id(+)=c.nBorrowClientID and un.id(+)=l.NEXTCHECKUSERID and ui.id(+)=l.inputUserId and l.officeid=" + qInfo.getOfficeId(); 
		 }
		sql[3] = " order by l.ID ";
		return sql;
	}
	
	public String getAssureChargeNoticeOrder(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //收款通知单编号
				oBuf.append(" \n order by l.Code" + strDesc);
				break;
			case 2 : //合同编号
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 : //借款单位
				oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
				break;
			case 5 : //保证金金额
				oBuf.append(" \n order by l.recognizanceAmount" + strDesc);
				break;
			case 4 : //担保费金额
				oBuf.append(" \n order by l.assureChargeAmount" + strDesc);
				break;
			case 6 : //开始日期
				oBuf.append(" \n order by l.StartDate" + strDesc);
				break;
			case 7 : //结束日期
				oBuf.append(" \n order by l.EndDate" + strDesc);
				break;
			case 8 : //提交日期
				oBuf.append(" \n order by l.executeDate" + strDesc);
				break;
			case 9 : //放款通知单状态
				oBuf.append(" \n order by l.StatusID" + strDesc);
				break;
			case 10 : //下一个审核人
				oBuf.append(" \n order by l.NEXTCHECKUSERID" + strDesc);
				break;
			case 11 : //期限
				oBuf.append(" \n order by c.nInterValNum" + strDesc);
				break;
			default :
				oBuf.append(" \n order by l.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
	public PageLoader queryAssureManagementNotice(QueryAssureNoticeInfo qInfo) throws Exception
	{
		String[] sql = getAssureManagementNoticeSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.assuremanagementnotice.dataentity.AssureManagementNoticeInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	private String[] getAssureManagementNoticeSQL(QueryAssureNoticeInfo qInfo)
	{
		String[] sql = new String[4];

		//select
		sql[0] =
			" l.ID,l.currencyId as currencyId,l.officeId as officeId,l.executeDate as executeDate," 
				+"c.sContractCode as ContractCode,c.nBorrowClientId as clientID,c.nInterValNum as intervalNum,"
				+ " c2.Name as clientName,ui.sName as inputUserName,l.inputDate as inputDate,"
				+ " l.RecognizanceAmount as recognizanceAmount,"
				+ " l.TypeID as typeID,l.AssureChargeAmount as assureChargeAmount,"
				+ " l.ReceiveAccount as receiveAccount,l.ReceiveClientName as receiveClientName,"
				+ " l.StartDate as startDate,l.endDate as endDate,l.Contractid as contractID,"
				+ " l.RemitInProvince as remitInProvince,l.RemitInCity as remitInCity,l.RemitInBank as remitInBank,"
				+ " l.code as code,l.CurrentAccountID as currentAccountID,l.InputUserID as inputUserID,"
				+ " l.NextCheckLevel as nextCheckLevel,l.StatusID as statusID,l.IsLowLevel as isLowLevel,un.sName as nextCheckUserName ";

		//from
		sql[1] = " LOAN_ASSUREMANAGEMENTFORM l,Client_clientinfo c2,loan_contractForm c,userInfo ui,userInfo un";

		//where
		sql[2] = " c.ID(+)=l.ContractID and c2.id(+)=c.nBorrowClientID and un.id(+)=l.NEXTCHECKUSERID and ui.id(+)=l.inputUserId " + " and c.ID=" + qInfo.getContractId();
		if (qInfo.getTypeId()>0)	sql[2] += " and l.TypeId=" + qInfo.getTypeId(); 

		sql[3] = " order by l.ID ";
		return sql;
	}
	
	public String getAssureManagementNoticeOrder(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //保后处理通知单编号
				oBuf.append(" \n order by l.Code" + strDesc);
				break;
			case 2 : //合同编号
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 : //借款单位
				oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
				break;
			case 5 : //保证金金额
				oBuf.append(" \n order by l.recognizanceAmount" + strDesc);
				break;
			case 4 : //担保费金额
				oBuf.append(" \n order by l.assureChargeAmount" + strDesc);
				break;
			case 6 : //开始日期
				oBuf.append(" \n order by l.StartDate" + strDesc);
				break;
			case 7 : //结束日期
				oBuf.append(" \n order by l.EndDate" + strDesc);
				break;
			case 8 : //提交日期
				oBuf.append(" \n order by l.executeDate" + strDesc);
				break;
			case 9 : //通知单状态
				oBuf.append(" \n order by l.StatusID" + strDesc);
				break;
			case 10 : //下一个审核人
				oBuf.append(" \n order by l.NEXTCHECKUSERID" + strDesc);
				break;
			case 11 : //期限
				oBuf.append(" \n order by c.nInterValNum" + strDesc);
				break;
			default :
				oBuf.append(" \n order by l.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	/**
	 * 银团贷款提款通知单查询
	 * @param drawInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryDrawNotice(QuestLoanDrawNoticeInfo drawInfo) throws Exception
	{
		String[] sql = getDrawNoticeSQL(drawInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.loandrawnotice.dataentity.LoanDrawNoticeInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}
	
	/**
	 * 银团贷款提款通知单各种总额合计
	 * @param drawInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryYTDrawNoticeSum (QuestLoanDrawNoticeInfo drawInfo) throws Exception{
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		QuerySumInfo sumInfo = new QuerySumInfo();
		String[] sql = getDrawNoticeSQL(drawInfo);
		String strSQL = "";

		try
		{
			strSQL = "select sum(con.mExamineAmount) as YTTotalLoanAmount,sum(d.mAmount) as TotalYTDrawPayNoticeAmount,sum(d.mAmount*con.mChargeRate/1000) as YTChargeTotalAmount from " + sql[1] + " where " + sql[2];
			log.print(strSQL);

			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setAllRecord(rs.getLong(1));	
				sumInfo.setTotalApplyAmount(rs.getDouble("YTTotalLoanAmount"));
														
				sumInfo.setTotalPayAmount(rs.getDouble("TotalYTDrawPayNoticeAmount"));
				sumInfo.setTotalChargeAmount(rs.getDouble("YTChargeTotalAmount"));
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
	
	

	private String[] getDrawNoticeSQL(QuestLoanDrawNoticeInfo  drawInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();
		
		//合同编号开始
		if (drawInfo.getContractCodeFrom() != null && drawInfo.getContractCodeFrom().length() > 0){
			buf.append(" and con.sContractCode>='" + drawInfo.getContractCodeFrom() + "'");
		}

		//合同编号结束
		if (drawInfo.getContractCodeTo() != null && drawInfo.getContractCodeTo().length() > 0){
			buf.append(" and con.sContractCode<='" + drawInfo.getContractCodeTo() + "'");
		}
		//借款单位开始
		if (drawInfo.getLoanClientIDFrom() > 0  ){
			buf.append(" and  con.nborrowclientid>='" + drawInfo.getLoanClientIDFrom() + "'");
		}
		
		//借款单位结束
		if (drawInfo.getLoanClientIDTo() > 0  ){
			buf.append(" and  con.nborrowclientid<='" + drawInfo.getLoanClientIDTo() + "'");
		}
		
		//提款金额开始
		if (drawInfo.getMDrawAmountFrom() > 0 ){
			buf.append(" and d.MAMOUNT>=" +drawInfo.getMDrawAmountFrom());
		}
		
		//提款金额结束
 		if (drawInfo.getMDrawAmountTo() > 0){
			buf.append(" and d.MAMOUNT<=" + drawInfo.getMDrawAmountTo());
		}
		
		
		//录入日期开始
		if (drawInfo.getDtInputDateFrom() != null && drawInfo.getDtInputDateFrom().length()>0){
			buf.append(" and TRUNC(d.dtInput) >= To_Date('" +drawInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
		}
		//录入日期结束
		if (drawInfo.getDtInputDateTo() != null && drawInfo.getDtInputDateTo().length()>0){
			buf.append(" and TRUNC(d.dtInput) <= To_Date('" + drawInfo.getDtInputDateTo()+ "','yyyy-mm-dd') ");
		}
		
		long drawNoticeStatusVal[] = LOANConstant.LoanDrawNoticeStatus.getAllCode(drawInfo.getNOfficeID(),drawInfo.getNCurrencyID());
		String strStatus = "";
		for (int i = 0; i < drawNoticeStatusVal.length; i++)
		{
			if(drawNoticeStatusVal[i]==LOANConstant.LoanDrawNoticeStatus.REFUSE){
				continue;
			}
			
			strStatus += drawNoticeStatusVal[i];
			if ((drawNoticeStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if(drawInfo.getNDrawNoticeStatus()==null || drawInfo.getNDrawNoticeStatus().equals("")||drawInfo.getNDrawNoticeStatus().equals("-1")){
		buf.append(" and con.nStatusID in (" + strStatus + ")");
		
		}else{
	       buf.append(" and d.nStatusID in (" + drawInfo.getNDrawNoticeStatus() + ")");
		}
		
		
		
		//select
		sql[0] =" d.ID,d.sCode as Code,con.sContractCode as ContractCode,"
				+ " c.Name as ClientName,con.mExamineAmount as ContractAmount,"
				+ " d.mAmount as DrawAmount,con.mChargeRate AgentRate,d.dtInput as InputDate,"
				+ " d.nStatusID as StatusID,d.nContractid as contractID, u.sName as InputUserName,"
				+ " u.sName as CheckUserName ";
			
		//from
		sql[1] = " loan_yt_drawform d,loan_contractForm con,Client_clientinfo c,userInfo u";

		//where
		sql[2] = " con.ID=d.nContractID and c.id(+)=con.nBorrowClientID and u.id(+)=d.NNEXTCHECKUSERID and con.nCurrencyID="+drawInfo.getNCurrencyID()+"and con.nOfficeID="+drawInfo.getNOfficeID()+buf.toString();

		sql[3] = " order by d.ID ";
		return sql;
	}

	public String getDrawNoticeOrder(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //通知单编号
				oBuf.append(" \n order by d.sCode" + strDesc);
				break;
			case 2 : //合同编号
				oBuf.append(" \n order by con.sContractCode" + strDesc);
				break;
			case 3 : //借款单位
				oBuf.append(" \n order by c.sName" + strDesc);
				break;
			case 4 : //合同金额
				oBuf.append(" \n order by con.mExamineAmount" + strDesc);
				break;
			case 5 : //提款金额
				oBuf.append(" \n order by d.mAmount" + strDesc);
				break;
			case 6 : //代理费
				oBuf.append(" \n order by con.mChargeRate" + strDesc);
				break;
			case 7 : //提交日期
				oBuf.append(" \n order by d.dtInput" + strDesc);
				break;
			case 8 : //通知单状态
				oBuf.append(" \n order by d.nStatusID" + strDesc);
				break;
			case 9 : //下一个审核人
				oBuf.append(" \n order by u.id" + strDesc);
				break;
			default :
				oBuf.append(" \n order by d.sCode" + strDesc);
				break;
		}
		return (oBuf.toString());
	}

	public QuerySumInfo queryPerformSum(long lID) throws Exception
	{
		QuerySumInfo sumInfo = new QuerySumInfo();
		String strSQL = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();

			/*
			 * 银团贷款 华能自己的承贷比例
			 * 2003-07-21 ninh
			 * 对于银团贷款 要还原 放、还款合计、利息合计、担保费合计
			 */
			double mRate = 100;
			strSQL =
				" select b.mrate "
					+ " from loan_yt_loancontractbankassign b,loan_contractform c "
					+ " where 1=1 "
					+ " and c.id = b.ncontractid "
					+ " and b.nishead = "
					+ Constant.YesOrNo.YES
					+ " and (c.ntypeid = "
					+ LOANConstant.LoanType.YT
					+ ") and c.id = "
					+ lID
					+ " ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			log.print(":::" + strSQL);
			if (rs.next())
			{
				mRate = rs.getDouble(1);
			}
			cleanup(rs);
			cleanup(ps);

			//放款合计
			strSQL = "select sum(mOpenAmount) from sett_SubAccount where nStatusID >0 " + " and Al_nLoanNoteID in (select ID from loan_payform where nContractID=" + lID + ") ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			log.print(":::" + strSQL);
			if (rs.next())
			{
				sumInfo.setTotalPayAmount(rs.getDouble(1) * 100 / mRate);
			}
			cleanup(rs);
			cleanup(ps);

			//还款合计
			strSQL = "select sum(mOpenAmount)-sum(mBalance) from sett_SubAccount where nStatusID >0 " + " and Al_nLoanNoteID in (select ID from loan_payform where nContractID=" + lID + ") ";
			ps = con.prepareStatement(strSQL);
			log.print(":::" + strSQL);
			rs = ps.executeQuery();

			if (rs.next())
			{
				sumInfo.setTotalRepayAmount(rs.getDouble(1) * 100 / mRate);
			}
			cleanup(rs);
			cleanup(ps);

			//利息合计
			strSQL =
				" select sum(mInterest1),sum(mInterest2),sum(mInterest3) from ( "
					+ " select mInterest mInterest1,0 mInterest2,0 mInterest3 from sett_SubAccount where nStatusID >0 "
					+ " and Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
				//应收利息，不在列表显示
	+" union all"
		+ " select  0 mInterest1,MREALINTEREST as mInterest2,0 mInterest3 from sett_TransRepaymentLoan where nStatusID in (3,4,5,6) "
		+ " and nLoanNoteID in (select ID from loan_payform where nContractID="
		+ lID
		+ ") "
		+ " union all "
		+ " select 0 mInterest1,0 mInterest2,a.mInterest as mInterest3 from sett_TransInterestSettlement a,sett_SubAccount b "
		+ " where a.nSubAccountID=b.ID and a.nInterestType = 1 and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)"
		+ " and Al_nLoanNoteID in (select ID from loan_payform where nContractID="
		+ lID
		+ ") "
				//实收利息
	+" ) ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			if (rs.next())
			{
				//sumInfo.setTotalInterestAmount(rs.getDouble(1) * 100 / mRate + rs.getDouble(2) + rs.getDouble(3));
				sumInfo.setTotalInterestAmount(rs.getDouble(1) + rs.getDouble(2) + rs.getDouble(3));
			}
			cleanup(rs);
			cleanup(ps);

			//手续费合计
			strSQL =
				"   select sum(mInterest) from "
					+ " ( "
					+ " select Al_mCommission as mInterest  from sett_SubAccount where nStatusID =1 "
					+ " and Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ")"
					+ " union all "
					+ " select mRealCommission as mInterest from sett_TransRepaymentLoan where nStatusID in (3,4,5,6)"
					+ " and nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
					+ " union all "
					+ " select a.mInterest from sett_TransInterestSettlement a,sett_SubAccount b "
					+ " where a.nSubAccountID=b.ID   and a.nInterestType = 2 and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)"
					+ " and Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
					+ " ) ";
			ps = con.prepareStatement(strSQL);
			log.print(":::" + strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setTotalChargeAmount(rs.getDouble(1));
			}
			cleanup(rs);
			cleanup(ps);

			//担保费合计
			strSQL =
				"  select sum(mInterest1),sum(mInterest2),sum(mInterest3) from "
					+ " ( "
					+ " select Al_mSuretyFee as mInterest1,0 mInterest2,0 mInterest3 from sett_SubAccount where nStatusID =1 "
					+ " and Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
					+ " union all "
					+ " select mRealSuretyFee as  mInterest2,0 mInterest1,0 mInterest3 from sett_TransRepaymentLoan where nStatusID in (3,4,5,6) "
					+ "   and nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
					+ " union all "
					+ " select a.mInterest as mInterest3,0 mInterest1,0 mInterest2 from sett_TransInterestSettlement a,sett_SubAccount b "
					+ "   where a.nSubAccountID=b.ID  and a.nInterestType = 3 and a.nIsKeepAccount = 1 and a.nStatusID in  (3,4,5,6)"
					+ "   and Al_nLoanNoteID in (select ID from loan_payform where nContractID="
					+ lID
					+ ") "
					+ " ) ";
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setTotalCreditAmount(rs.getDouble(1) * 100 / mRate + rs.getDouble(2) + rs.getDouble(3));
			}
			cleanup(rs);
			cleanup(ps);

			cleanup(con);
		}
		catch (Exception e)
		{
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
	public String getLoanOrderSQL(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 : //贷款种类
				oBuf.append(" \n order by l.nTypeID" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by l.sApplyCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c.Name" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c2.Name" + strDesc);
				break;
			case 5 :
				oBuf.append(" \n order by l.mLoanAmount" + strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by l.mInterestRate" + strDesc);
				break;
			case 7 :
				oBuf.append(" \n order by l.nIntervalNum" + strDesc);
				break;
			case 8 :
				oBuf.append(" \n order by l.dtStartDate" + strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by l.dtInputDate" + strDesc);
				break;
			case 10 :
				oBuf.append(" \n order by l.nStatusID" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 12:
				oBuf.append(" \n order by l.AssureChargeRate" + strDesc);
				break;
			case 13:
				oBuf.append(" \n order by lt.name" + strDesc);
				break;
			case 14:
				oBuf.append(" \n order by w.nacceptpotypeid" + strDesc);
				break;
			default :
				oBuf.append(" \n order by l.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
		private String[] getLoanApplySQL(QueryLoanApplyInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer sqlBuf = new StringBuffer();

		//select
		sql[0] =
			   " w.nacceptpotypeid as tsDiscountTypeID,l.ID,l.nTypeID as TypeID,l.nStatusID as statusID,l.sApplyCode as applyCode,"
				+ " c.Name as borrowClientName,c2.Name as consignClientName,l.mLoanAmount as loanAmount,"
				+ " l.nIntervalNum as intervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
				+ " l.nBankInterestID as bankInterestID, l.mAdjustRate as adjustRate ,l.mStaidAdjustRate as staidAdjustRate ,"
				+ " DECODE(l.nTypeID,"
				+ LOANConstant.LoanType.TX
				+ ",mDiscountRate,"
				+ LOANConstant.LoanType.ZTX
				+ ",mDiscountRate,mInterestRate) as interestRate ,l.assureChargeRate as assureChargeRate,lt.name as subTypeName,"
				+ " l.dtStartDate as startDate,l.dtEndDate as endDate, l.dtInputDate as inputDate ";

		//from
		sql[1] = " (select DISTINCT nloanid,nacceptpotypeid,nofficeid,ncurrencyid from loan_discountformbill) w ,loan_loanform l,Client_clientinfo c,Client_clientinfo c2,userInfo u,userInfo u2,loan_loantypesetting lt";

		//where
		sql[2] = "  w.nloanid(+)=l.id and c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID and lt.id=l.nsubTypeID and lt.loantypeid=l.ntypeid";

		/**********处理查询条件*************/
		if(qInfo.getLoanTypes()==null ||qInfo.getLoanTypes().equals("")||qInfo.getLoanTypes().equals("-1")){
			sqlBuf.append(" and l.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and " +
    	        		"  a.loantypeid  in ("+LOANConstant.LoanType.WT+","+LOANConstant.LoanType.YT+","+LOANConstant.LoanType.ZY+","+LOANConstant.LoanType.TX+") and a.id=b.subloantypeid " +
    	        		" and b.currencyid="+qInfo.getCurrencyID()+" and b.officeid="+qInfo.getOfficeID()+")");
		}
		//贷款种类（复选）add by wmzheng at 2010-10-13 
		if(qInfo.getLoanTypes() != null && qInfo.getLoanTypes().length() > 0)
			sqlBuf.append(" and l.nsubTypeID in (" + qInfo.getLoanTypes()+")");
		
		//如果是转贴现（查询条件增加）
		if (qInfo.getLoanType() > 0 && qInfo.getLoanType()==LOANConstant.LoanType.ZTX)
		{
			if(qInfo.getInOrOut() > 0)
				sqlBuf.append(" and l.NINOROUT=" + qInfo.getInOrOut());
			if(qInfo.getTransDiscountType() > 0)
				sqlBuf.append(" and l.NDISCOUNTTYPEID=" + qInfo.getTransDiscountType());
			if(qInfo.getTransDiscountTerm() > 0)
				sqlBuf.append(" and l.DTENDDATE - l.DTSTARTDATE =" + qInfo.getTransDiscountTerm());
		}			
		if (qInfo.getTsDiscountTypeID()>0)
		{
		System.out.println("TsDiscountTypeID="+qInfo.getTsDiscountTypeID());		
		sqlBuf.append(" and w.nacceptpotypeid="+qInfo.getTsDiscountTypeID()+" and l.ntypeid="+LOANConstant.LoanType.TX );
		}
		//申请书编号开始
		if ((qInfo.getMaxApplyCode() != null) && (qInfo.getMaxApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode<='" + qInfo.getMaxApplyCode() + "'");

		//申请书编号结束
		if ((qInfo.getMinApplyCode() != null) && (qInfo.getMinApplyCode().length() > 0))
			sqlBuf.append(" and l.sApplyCode>='" + qInfo.getMinApplyCode() + "'");

		//申请书状态
		if ((qInfo.getLoanStatusID() > 0) || (qInfo.getLoanStatusID() == LOANConstant.LoanStatus.REFUSE))
			sqlBuf.append(" and l.nStatusID=" + qInfo.getLoanStatusID());
		
		long loanStatusVal[]=LOANConstant.LoanStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String loanTypeList="";
  		for ( int i=0;i<loanStatusVal.length;i++ )
  		{
  			
  			if(loanStatusVal[i]==LOANConstant.LoanStatus.REFUSE){
  				continue;
  			}
  			loanTypeList+=loanStatusVal[i];
  			if((loanStatusVal.length-i)>1){
  				
  				loanTypeList+=",";
  			}
  			
  		}
		
		//申请书状态(复选) add by wmzheng at 2010-10-13
		if(qInfo.getLoanStatusIDs()== null || qInfo.getLoanStatusIDs().equals("-1")){
			
			sqlBuf.append(" and l.nStatusID in (" + loanTypeList+")");
			
		}else{
			sqlBuf.append(" and l.nStatusID in (" + qInfo.getLoanStatusIDs()+")");
		}
		//借款单位
		if (qInfo.getBorrowClientID() > 0)
			sqlBuf.append(" and l.nBorrowClientID=" + qInfo.getBorrowClientID());

		//借款单位(复选) add by wmzheng at 2010-10-13 
		if (qInfo.getBorrowClientIDFrom() > 0)
			sqlBuf.append(" and l.nBorrowClientID >= " + qInfo.getBorrowClientIDFrom());
		if (qInfo.getBorrowClientIDTo() > 0)
			sqlBuf.append(" and l.nBorrowClientID <= " + qInfo.getBorrowClientIDTo());
		
		//借款单位账号
		if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
			sqlBuf.append(" and c.sAccount='" + qInfo.getBorrowAccount() + "'");

		//客户分类
//		if (qInfo.getLoanClientTypeID() > 0)
//			sqlBuf.append(" and c.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

		//主管单位
		if (qInfo.getParentCorpID() > 0)
			sqlBuf.append(" and c.nParentCorpID1=" + qInfo.getParentCorpID());

		//委托单位
		if (qInfo.getConsignClientID() > 0)
			sqlBuf.append(" and l.nConsignClientID=" + qInfo.getConsignClientID());

		//委托单位(复选) add by wmzheng at 2010-10-13
		if (qInfo.getConsignClientIDFrom() > 0)
			sqlBuf.append(" and l.nConsignClientID >= " + qInfo.getConsignClientIDFrom()+" and l.ntypeid="+LOANConstant.LoanType.WT);
		if (qInfo.getConsignClientIDTo() > 0)
			sqlBuf.append(" and l.nConsignClientID <= " + qInfo.getConsignClientIDTo()+" and l.ntypeid="+LOANConstant.LoanType.WT);
		//委托单位账号
		if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
			sqlBuf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		//贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		//贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			sqlBuf.append(" and l.mLoanAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		//执行利率 add by wmzheng at 2010-10-13
		if (qInfo.getExecuteRateFrom() > 0)
			sqlBuf.append(" and DECODE(l.nTypeID, "+LOANConstant.LoanType.TX+", l.mDiscountRate, "+LOANConstant.LoanType.ZTX+", l.mDiscountRate, l.mInterestRate) >= " + DataFormat.formatRate(qInfo.getExecuteRateFrom()));
		if (qInfo.getExecuteRateTo() > 0)
			sqlBuf.append(" and DECODE(l.nTypeID, "+LOANConstant.LoanType.TX+", l.mDiscountRate, "+LOANConstant.LoanType.ZTX+", l.mDiscountRate, l.mInterestRate) <= " + DataFormat.formatRate(qInfo.getExecuteRateTo()));
		
		//贷款日期开始
		if (qInfo.getMaxLoanDate() != null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxLoanDate()) + "','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getMinLoanDate() != null)
			sqlBuf.append(" and TRUNC(l.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinLoanDate()) + "','yyyy-mm-dd') ");

		//贷款期限
		if (qInfo.getIntervalNum() > 0)
			sqlBuf.append(" and l.nIntervalNum=" + qInfo.getIntervalNum());

		//期限 add by wmzheng at 2010-10-13
		if (qInfo.getPeriodFrom() > 0)
			sqlBuf.append(" and l.nIntervalNum >= " + qInfo.getPeriodFrom());
		if (qInfo.getPeriodTo() > 0)
			sqlBuf.append(" and l.nIntervalNum <= " + qInfo.getPeriodTo());
		
		//提交日期开始
		if (qInfo.getMaxInputDate() != null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxInputDate()) + "','yyyy-mm-dd') ");

		//提交日期结束
		if (qInfo.getMinInputDate() != null)
			sqlBuf.append(" and TRUNC(l.dtInputDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinInputDate()) + "','yyyy-mm-dd') ");

		//保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			sqlBuf.append(" and l.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			sqlBuf.append(" and l.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			sqlBuf.append(" and l.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			sqlBuf.append(" and l.nIsPledge=1");

		//信用等级
		if (qInfo.getCreditLevel() > 0)
			sqlBuf.append(" and c.NCREDITLEVELID=" + qInfo.getCreditLevel());

		//是否股东
		if (qInfo.getIsPartner() > 0)
			sqlBuf.append(" and c.NISPARTNER=" + qInfo.getIsPartner());

		//是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			sqlBuf.append(" and l.NISTECHNICAL=" + qInfo.getIsTechnical());

		//是否循环贷款
		if (qInfo.getIsCircle() > 0)
			sqlBuf.append(" and l.NISCIRCLE=" + qInfo.getIsCircle());

		//管理人
		if (qInfo.getInputUserID() > 0)
			sqlBuf.append(" and l.nInputUserID=" + qInfo.getInputUserID());
		
		//币种
		if(qInfo.getCurrencyID() > 0)
			sqlBuf.append(" and l.nCurrencyID=" + qInfo.getCurrencyID());
		
		//办事处
		if(qInfo.getOfficeID() > 0)
			sqlBuf.append(" and l.nOfficeID=" + qInfo.getOfficeID());

		sql[2] = sql[2] + sqlBuf.toString();

		
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" );

		return sql;
	}	
		
		private String[] getDBLoanApplySQL(QueryLoanApplyInfo qInfo)
		{
			String[] sql = new String[4];
			StringBuffer sqlBuf = new StringBuffer();

			//select
			sql[0] =
				   " l.ID,l.nTypeID as TypeID,l.nStatusID as statusID,l.sApplyCode as applyCode,"
					+ " c.Name as borrowClientName,c2.Name as consignClientName,l.mLoanAmount as loanAmount,"
					+ " l.nIntervalNum as intervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
					+ " l.nBankInterestID as bankInterestID, l.mAdjustRate as adjustRate ,l.mStaidAdjustRate as staidAdjustRate ,"
					+"l.assureChargeRate as assureChargeRate,lt.name as subTypeName,"
					+ " l.dtStartDate as startDate,l.dtEndDate as endDate, l.dtInputDate as inputDate ";

			//from
			sql[1] = " (select DISTINCT nloanid,nacceptpotypeid,nofficeid,ncurrencyid from loan_discountformbill) w ,loan_loanform l,Client_clientinfo c,Client_clientinfo c2,userInfo u,userInfo u2,loan_loantypesetting lt";

			//where
			sql[2] = "  w.nloanid(+)=l.id and c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID and lt.id=l.nsubTypeID and lt.loantypeid=l.ntypeid and nTypeID="+LOANConstant.LoanType.DB;

			
			//申请书编号开始
			if ((qInfo.getMaxApplyCode() != null) && (qInfo.getMaxApplyCode().length() > 0))
				sqlBuf.append(" and l.sApplyCode<='" + qInfo.getMaxApplyCode() + "'");

			//申请书编号结束
			if ((qInfo.getMinApplyCode() != null) && (qInfo.getMinApplyCode().length() > 0))
				sqlBuf.append(" and l.sApplyCode>='" + qInfo.getMinApplyCode() + "'");

			//申请书状态
			if ((qInfo.getLoanStatusID() > 0) || (qInfo.getLoanStatusID() == LOANConstant.LoanStatus.REFUSE))
				sqlBuf.append(" and l.nStatusID=" + qInfo.getLoanStatusID());
			
			//申请书状态(复选) add by wmzheng at 2010-10-13
			if(qInfo.getLoanStatusIDs() != null && qInfo.getLoanStatusIDs().length() > 0)
				sqlBuf.append(" and l.nStatusID in (" + qInfo.getLoanStatusIDs()+")");
			
			//借款单位
			if (qInfo.getBorrowClientID() > 0)
				sqlBuf.append(" and l.nBorrowClientID=" + qInfo.getBorrowClientID());

			//借款单位(复选) add by wmzheng at 2010-10-13 
			if (qInfo.getBorrowClientIDFrom() > 0)
				sqlBuf.append(" and l.nBorrowClientID >= " + qInfo.getBorrowClientIDFrom());
			if (qInfo.getBorrowClientIDTo() > 0)
				sqlBuf.append(" and l.nBorrowClientID <= " + qInfo.getBorrowClientIDTo());
			
			//借款单位账号
			if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
				sqlBuf.append(" and c.sAccount='" + qInfo.getBorrowAccount() + "'");

			//客户分类
//			if (qInfo.getLoanClientTypeID() > 0)
//				sqlBuf.append(" and c.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

			//主管单位
			if (qInfo.getParentCorpID() > 0)
				sqlBuf.append(" and c.nParentCorpID1=" + qInfo.getParentCorpID());

			//委托单位账号
			if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
				sqlBuf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

			//贷款金额开始
			if (qInfo.getMaxLoanAmount() > 0)
				sqlBuf.append(" and l.mLoanAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

			//贷款金额结束
			if (qInfo.getMinLoanAmount() > 0)
				sqlBuf.append(" and l.mLoanAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

			//管理人
			if (qInfo.getInputUserID() > 0)
				sqlBuf.append(" and l.nInputUserID=" + qInfo.getInputUserID());
			
			//币种
			if(qInfo.getCurrencyID() > 0)
				sqlBuf.append(" and l.nCurrencyID=" + qInfo.getCurrencyID());
			
			//办事处
			if(qInfo.getOfficeID() > 0)
				sqlBuf.append(" and l.nOfficeID=" + qInfo.getOfficeID());

			sql[2] = sql[2] + sqlBuf.toString();

			//order
	
			log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" );

			return sql;
		}	
		
		private String[] getRZZLLoanApplySQL(QueryLoanApplyInfo qInfo)
		{
			String[] sql = new String[4];
			StringBuffer sqlBuf = new StringBuffer();

			//select
			sql[0] =
				   " w.nacceptpotypeid as tsDiscountTypeID,l.ID,l.nTypeID as TypeID,l.nStatusID as statusID,l.sApplyCode as applyCode,"
					+ " c.Name as borrowClientName,c2.Name as consignClientName,l.mLoanAmount as loanAmount,"
					+ " l.nIntervalNum as intervalNum,u.sName as inputUserName,u2.sName as lastCheckUserName,"
					+ " l.nBankInterestID as bankInterestID, l.mAdjustRate as adjustRate ,l.mStaidAdjustRate as staidAdjustRate ,"
					+ " DECODE(l.nTypeID,"
					+ LOANConstant.LoanType.TX
					+ ",mDiscountRate,"
					+ LOANConstant.LoanType.ZTX
					+ ",mDiscountRate,mInterestRate) as interestRate ,l.assureChargeRate as assureChargeRate,"
					+ " l.dtStartDate as startDate,l.dtEndDate as endDate, l.dtInputDate as inputDate ";

			//from
			sql[1] = " (select DISTINCT nloanid,nacceptpotypeid,nofficeid,ncurrencyid from loan_discountformbill) w ,loan_loanform l,Client_clientinfo c,Client_clientinfo c2,userInfo u,userInfo u2";

			//where
			sql[2] = "  w.nloanid(+)=l.id and c.id(+)=l.nBorrowClientID and c2.id(+)=l.nconsignclientid and u.id(+)=l.nInputUserID and u2.id(+)=l.NNEXTCHECKUSERID  and l.ntypeid= "+LOANConstant.LoanType.RZZL;

			//申请书编号开始
			if ((qInfo.getMaxApplyCode() != null) && (qInfo.getMaxApplyCode().length() > 0))
				sqlBuf.append(" and l.sApplyCode<='" + qInfo.getMaxApplyCode() + "'");

			//申请书编号结束
			if ((qInfo.getMinApplyCode() != null) && (qInfo.getMinApplyCode().length() > 0))
				sqlBuf.append(" and l.sApplyCode>='" + qInfo.getMinApplyCode() + "'");

			//申请书状态
			if ((qInfo.getLoanStatusID() > 0) || (qInfo.getLoanStatusID() == LOANConstant.LoanStatus.REFUSE))
				sqlBuf.append(" and l.nStatusID=" + qInfo.getLoanStatusID());
			
			//申请书状态(复选) add by wmzheng at 2010-10-13
			if(qInfo.getLoanStatusIDs() != null && qInfo.getLoanStatusIDs().length() > 0)
				sqlBuf.append(" and l.nStatusID in (" + qInfo.getLoanStatusIDs()+")");
			
			//借款单位
			if (qInfo.getBorrowClientID() > 0)
				sqlBuf.append(" and l.nBorrowClientID=" + qInfo.getBorrowClientID());

			//借款单位(复选) add by wmzheng at 2010-10-13 
			if (qInfo.getBorrowClientIDFrom() > 0)
				sqlBuf.append(" and l.nBorrowClientID >= " + qInfo.getBorrowClientIDFrom());
			if (qInfo.getBorrowClientIDTo() > 0)
				sqlBuf.append(" and l.nBorrowClientID <= " + qInfo.getBorrowClientIDTo());
			
			//借款单位账号
			if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
				sqlBuf.append(" and c.sAccount='" + qInfo.getBorrowAccount() + "'");

			//客户分类
//			if (qInfo.getLoanClientTypeID() > 0)
//				sqlBuf.append(" and c.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

			//主管单位
			if (qInfo.getParentCorpID() > 0)
				sqlBuf.append(" and c.nParentCorpID1=" + qInfo.getParentCorpID());

			//委托单位
			if (qInfo.getConsignClientID() > 0)
				sqlBuf.append(" and l.nConsignClientID=" + qInfo.getConsignClientID());

			//委托单位(复选) add by wmzheng at 2010-10-13
			if (qInfo.getConsignClientIDFrom() > 0)
				sqlBuf.append(" and l.nConsignClientID >= " + qInfo.getConsignClientIDFrom());
			if (qInfo.getConsignClientIDTo() > 0)
				sqlBuf.append(" and l.nConsignClientID <= " + qInfo.getConsignClientIDTo());
			
			//委托单位账号
			if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
				sqlBuf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

			//贷款金额开始
			if (qInfo.getMaxLoanAmount() > 0)
				sqlBuf.append(" and l.mLoanAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

			//贷款金额结束
			if (qInfo.getMinLoanAmount() > 0)
				sqlBuf.append(" and l.mLoanAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

			//执行利率 add by wmzheng at 2010-10-13
			if (qInfo.getExecuteRateFrom() > 0)
				sqlBuf.append(" and DECODE(l.nTypeID, "+LOANConstant.LoanType.TX+", l.mDiscountRate, "+LOANConstant.LoanType.ZTX+", l.mDiscountRate, l.mInterestRate) >= " + DataFormat.formatRate(qInfo.getExecuteRateFrom()));
			if (qInfo.getExecuteRateTo() > 0)
				sqlBuf.append(" and DECODE(l.nTypeID, "+LOANConstant.LoanType.TX+", l.mDiscountRate, "+LOANConstant.LoanType.ZTX+", l.mDiscountRate, l.mInterestRate) <= " + DataFormat.formatRate(qInfo.getExecuteRateTo()));
			
			//贷款日期开始
			if (qInfo.getMaxLoanDate() != null)
				sqlBuf.append(" and TRUNC(l.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxLoanDate()) + "','yyyy-mm-dd') ");

			//贷款日期结束
			if (qInfo.getMinLoanDate() != null)
				sqlBuf.append(" and TRUNC(l.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinLoanDate()) + "','yyyy-mm-dd') ");

			//贷款期限
			if (qInfo.getIntervalNum() > 0)
				sqlBuf.append(" and l.nIntervalNum=" + qInfo.getIntervalNum());

			//期限 add by wmzheng at 2010-10-13
			if (qInfo.getPeriodFrom() > 0)
				sqlBuf.append(" and l.nIntervalNum >= " + qInfo.getPeriodFrom());
			if (qInfo.getPeriodTo() > 0)
				sqlBuf.append(" and l.nIntervalNum <= " + qInfo.getPeriodTo());
			
			//提交日期开始
			if (qInfo.getMaxInputDate() != null)
				sqlBuf.append(" and TRUNC(l.dtInputDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxInputDate()) + "','yyyy-mm-dd') ");

			//提交日期结束
			if (qInfo.getMinInputDate() != null)
				sqlBuf.append(" and TRUNC(l.dtInputDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinInputDate()) + "','yyyy-mm-dd') ");

			//保证方式
			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
				sqlBuf.append(" and l.nIsCredit=1");
			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
				sqlBuf.append(" and l.nIsAssure=1");
			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
				sqlBuf.append(" and l.nIsImpawn=1");
			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
				sqlBuf.append(" and l.nIsPledge=1");

			//信用等级
			if (qInfo.getCreditLevel() > 0)
				sqlBuf.append(" and c.NCREDITLEVELID=" + qInfo.getCreditLevel());

			//是否股东
			if (qInfo.getIsPartner() > 0)
				sqlBuf.append(" and c.NISPARTNER=" + qInfo.getIsPartner());

			//是否技改贷款
			if (qInfo.getIsTechnical() > 0)
				sqlBuf.append(" and l.NISTECHNICAL=" + qInfo.getIsTechnical());

			//是否循环贷款
			if (qInfo.getIsCircle() > 0)
				sqlBuf.append(" and l.NISCIRCLE=" + qInfo.getIsCircle());

			//管理人
			if (qInfo.getInputUserID() > 0)
				sqlBuf.append(" and l.nInputUserID=" + qInfo.getInputUserID());
			
			//币种
			if(qInfo.getCurrencyID() > 0)
				sqlBuf.append(" and l.nCurrencyID=" + qInfo.getCurrencyID());
			
			//办事处
			if(qInfo.getOfficeID() > 0)
				sqlBuf.append(" and l.nOfficeID=" + qInfo.getOfficeID());

			sql[2] = sql[2] + sqlBuf.toString();

			
			log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" );

			return sql;
		}	
		
		
	/**
	 * 查询贷款申请，担保申请，融资租赁申请
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */	
		
	public PageLoader queryLoanApply(QueryLoanApplyInfo qInfo) throws Exception
	{   		
			String[] sql = {""};
			if(qInfo.getQueryPurpose()==QueryLoanApplyInfo.LOAN){
				
				sql=getLoanApplySQL(qInfo);
				
			}
			else if(qInfo.getQueryPurpose()==QueryLoanApplyInfo.DB){
				
				sql=getDBLoanApplySQL(qInfo);
				
			}else if (qInfo.getQueryPurpose()==QueryLoanApplyInfo.RZZL){
				
				sql=getRZZLLoanApplySQL(qInfo);
				
			}
			
			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

			pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.loanapply.dataentity.LoanApplyInfo", null);
			
			return pageLoader;		
	}
	/**
	 * 查询贷款申请，担保申请，融资租赁申请 贷款总额
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryLoanApplySum(QueryLoanApplyInfo qInfo) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		QuerySumInfo sumInfo = new QuerySumInfo();
		
		String[] sql = {""};
		if(qInfo.getQueryPurpose()==QueryLoanApplyInfo.LOAN){
			
			sql=getLoanApplySQL(qInfo);
			
		}
		else if(qInfo.getQueryPurpose()==QueryLoanApplyInfo.DB){
			
			sql=getDBLoanApplySQL(qInfo);
			
		}else if (qInfo.getQueryPurpose()==QueryLoanApplyInfo.RZZL){
			
			sql=getRZZLLoanApplySQL(qInfo);
			
		}
		String strSQL = "";

		try
		{
			sql[0] = "sum(l.mLoanAmount) as sumLoanAmount";
			strSQL = "select " + sql[0] + " from " + sql[1] + " where " + sql[2];
			log.print(strSQL);

			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setTotalApplyAmount(rs.getDouble("sumLoanAmount"));
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
				oBuf.append(" \n order by loanTypeName" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by nvl(c1.Name,'')" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by nvl(c2.Name,'')" + strDesc);
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
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode" + strDesc);
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
			case 20 :
				oBuf.append(" \n order by nvl(u2.sName,'')" + strDesc);
				break;
			case 21:
				oBuf.append(" \n order by c.assureChargeRate" +strDesc);
				break;
			case 22 :
				oBuf.append(" \n order by c.nrisklevel" + strDesc);
				break;	
			case 23 :
				oBuf.append(" \n order by nvl(c.mpurchaseramount,0)" + strDesc);
				break;
			case 24 :
				oBuf.append(" \n order by nvl(w.NACCEPTPOTYPEID,0)" + strDesc);
				break;
			case 25 :
				oBuf.append(" \n order by nvl(c1.sName,'')" + strDesc);
				break;
			case 26 :
				oBuf.append(" \n order by nvl(c2.sName,'')" + strDesc);
				break;
			case 27 :
				oBuf.append(" \n order by nvl(c.DTINPUTDATE,'')" + strDesc);
				break;
			case 28 :
				oBuf.append(" \n order by nvl(c.mDiscountAccrual,'')" + strDesc);
				break;
			case 29 :
				oBuf.append(" \n order by lp.balance" + strDesc);
				break;
			case 30 :
				oBuf.append(" \n order by nvl(borrowClientName,'')" + strDesc);
				break;
			case 31 :
				oBuf.append(" \n order by nvl(ClientName,'')" + strDesc);
				break;
				
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
	/**
	 * 排序语句
	 * @author 马现福
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getPayFormOrderSQL(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 :
				oBuf.append(" \n order by tmp.DtoutDate" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by tmp.Code" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by tmp.Name" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by tmp.SContractCode" + strDesc);
				break;
			case 5 :
				oBuf.append(" \n order by tmp.SCode" + strDesc);
				break;
			case 6 :
				oBuf.append(" \n order by tmp.NIsCircle" + strDesc);
				break;
			case 7 :
				oBuf.append(" \n order by tmp.MAmount" + strDesc);
				break;
			case 8 :
				oBuf.append(" \n order by tmp.MInterestRate" + strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by tmp.NIntervalNoticeNum" + strDesc);
				break;
			default :
				oBuf.append(" \n order by tmp.DtoutDate" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
	public String getExtendOrderSQL(long lOrder, long lDesc)
	{
		//order
		String strDesc = lDesc == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) lOrder)
		{
			case 1 :
				oBuf.append(" \n order by LoanTypeName" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by ContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by ClientName" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by ConsignClientName" + strDesc);
				break;
			case 5 :
				oBuf.append(" \n order by applycode" + strDesc);
				break;				
			case 6 :
				oBuf.append(" \n order by Amount" + strDesc);
				break;
			case 7 :
				oBuf.append(" \n order by ExtendAmount" + strDesc);
				break;
			case 8 :
				oBuf.append(" \n order by ExtendRate" + strDesc);
				break;
			case 9 :
				oBuf.append(" \n order by DateFrom" + strDesc);
				break;
			case 10 :
				oBuf.append(" \n order by DateTo" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by StatusID" + strDesc);
				break;
			case 12 :
				oBuf.append(" \n order by inputDate" + strDesc);
				break;
			case 13 :
				oBuf.append(" \n order by inputUserName" + strDesc);
				break;
			default :
				oBuf.append(" \n order by ExtendID" + strDesc);
				break;
		}
		return (oBuf.toString());
	}
	
	public String[] getContractSQL(QuestContractInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		//select
		//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		sql[0] +=
			"c.ID as contractID,c.nTypeID as loanTypeID,'"+qInfo.getBalanceDate()+"' as dailyDate,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
				+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
				+ " c.mInterestRate as interestRate,c.nloanID as LoanID,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
				+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
				+ " ,db.dailybalance "; //指定余额日期的贷款余额 add by wmzheng at 2010-10-14
				
		//胡志强修改(2004-03-09)
		sql[0] += ",a.overdueAmount as overdueAmount,";
		sql[0] += "b.punishInterest as punishInterest";
		//added by xiong fei 2010/05/25查出各单位的担保信息
		sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
		sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
		sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";
		
		//add by wmzheng at 2010-09-25 合同风险状态
		sql[0] += ",c.nrisklevel as risklevel";
		
		//from
		sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
		//从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp " + "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		//指定余额日期的贷款余额 add by wmzheng at 2010-10-14
		sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
		sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
		sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"+DataFormat.getDateString(qInfo.getBalanceDate())+"','yyyy-mm-dd')";
		sql[1] += " group by db.nCOntractID ) db";
		
		//胡志强修改(2004-03-09)
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
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

		//where
		sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id"+" and db.ncontractid(+) = c.id";

		//胡志强修改(2004-03-09)
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";

		if (qInfo.getQueryPurpose() == QuestContractInfo.TX)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.TX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			//贴现客户名称
			if ((qInfo.getDiscountClientName() != null) && (qInfo.getDiscountClientName().length() > 0))
			{
				buf.append(" and c.discountClientName like '%" + qInfo.getDiscountClientName() + "%'");
			}
		}
		else if (qInfo.getQueryPurpose() == QuestContractInfo.ZTX)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
			}
			//贴现客户名称
			if ((qInfo.getDiscountClientName() != null) && (qInfo.getDiscountClientName().length() > 0))
			{
				buf.append(" and c.discountClientName like '%" + qInfo.getDiscountClientName() + "%'");
			}
		}
		else
		{
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.TX;
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.ZTX;
			if(qInfo.getMinRate()>0)
			{
				sql[2] += " and y.lateRate >= " + qInfo.getMinRate();
			}
			if(qInfo.getMaxRate()>0)
			{
				sql[2] += " and y.lateRate <= " + qInfo.getMaxRate();
			}
		}
		
		//（上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose()== QuestContractInfo.DB)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		}
		else
		{
			sql[2] += " and c.nTypeID<>" + LOANConstant.LoanType.DB;
		}

		//贷款种类
		/*if (qInfo.getLoanTypeID() > 0)
			buf.append(" and c.nsubTypeID=" + qInfo.getLoanTypeID());*/
		if  (qInfo.getLoanTypeID() <= 0)
	         buf.append(" and c.nTypeID<>" +LOANConstant.LoanType.RZZL );
			
		//如果是转贴现（查询条件增加）
		if( qInfo.getQueryPurpose() == QuestContractInfo.ZTX )
		{		
			if(qInfo.getInOrOut() > 0)
				buf.append(" and c.NINOROUT=" + qInfo.getInOrOut());
			if(qInfo.getTransDiscountType() > 0)
				buf.append(" and c.NDISCOUNTTYPEID=" + qInfo.getTransDiscountType());
			if(qInfo.getTransDiscountTerm() > 0)
				buf.append(" and c.DTENDDATE - c.DTSTARTDATE =" + qInfo.getTransDiscountTerm());			
		}
		
		//合同编号开始
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//合同编号结束
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		/***************添加国机的变更 2003-3-30 qqgd***************/
		//合同状态
		if (!qInfo.isShowEnd())
		{
			buf.append(" and c.nStatusID<>" + LOANConstant.ContractStatus.FINISH);
		}

		/************为了国机加的判断，限制状态 2004-4-23 qqgd *********/
		
		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++)
		{
			
      		if(contractStatusVal[i]==LOANConstant.ContractStatus.SUBMIT )
      			continue;
      		if(contractStatusVal[i]==LOANConstant.ContractStatus.REFUSE )
      		    continue;
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if (qInfo.getStatusID() > 0)
			{
			System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
			}
		if(qInfo.getStatusIDs()==null||qInfo.getStatusIDs().equals("")|| qInfo.getStatusIDs().equals("-1")){
		buf.append(" and c.nStatusID in (" + strStatus + ")");
		
		}

		//minzhao20090505修改将合同状态增加为多状态查询
		else
		{
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
		}

		//add by wmzheng at 2010-09-26 多个贷款种类、子种类
		if(!qInfo.getLoanTypeIDs().equals("")&&!qInfo.getLoanTypeIDs().equals("-1"))
		{
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs()+")");
		}
		/*if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1"))
		{
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")");
		}*/
		
		//借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		//借款单位开始
		if (qInfo.getBorrowClientIDFrom() > 0)
			buf.append(" and c.nBorrowClientID>=" + qInfo.getBorrowClientIDFrom());
		//借款单位结束
		if (qInfo.getBorrowClientIDTo() > 0)
			buf.append(" and c.nBorrowClientID<=" + qInfo.getBorrowClientIDTo());

		//借款单位账号
		if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		//客户分类
//		if (qInfo.getLoanClientTypeID() > 0)
//			buf.append(" and c1.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

		//主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		//委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
		//委托单位开始
		if (qInfo.getConsignClientIDFrom() > 0)
			buf.append(" and c.nConsignClientID>=" + qInfo.getConsignClientIDFrom()+" and c.ntypeid="+LOANConstant.LoanType.WT);
		//委托单位结束
		if (qInfo.getConsignClientIDTo() > 0)
			buf.append(" and c.nConsignClientID<=" + qInfo.getConsignClientIDTo()+" and c.ntypeid="+LOANConstant.LoanType.WT);

		//委托单位账号
		if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		//贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		//贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		//add by wmzheng at 2010-10-14 贷款余额
		//贷款余额开始
		if (qInfo.getMinLoanBalanceAmount() > 0)
			buf.append(" and lp.balance >= " + DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
		//贷款余额结束
		if (qInfo.getMaxLoanBalanceAmount() > 0)
			buf.append(" and lp.balance <= " + DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));
		
		//贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");

		//贷款结束日期结束
		if (qInfo.getMaxEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");

		//贷款结束日期开始
		if (qInfo.getMinEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
		
		//贴现日期结束
		if (qInfo.getMaxDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");

		//贴现日期开始
		if (qInfo.getMinDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");

		//合同利率 add by wmzheng at 2010-10-14
		if (qInfo.getContractRateFrom() > 0)
			buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= " + DataFormat.formatRate(qInfo.getContractRateFrom()));
		if (qInfo.getContractRateTo() > 0)
			buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= " + DataFormat.formatRate(qInfo.getContractRateTo()));
		
		//贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

		//贷款期限由
		if (qInfo.getPeriodFrom() > 0)
			buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
		//贷款期限至
		if (qInfo.getPeriodTo() > 0)
			buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());
		
		//保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");
		
		//added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
		if(qInfo.getIsassure()==1||qInfo.getIscredit()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
			buf.append(" and ( ");
			if(qInfo.getIscredit()==1){
				buf.append(" c.nIsCredit=1");
				if(qInfo.getIsassure()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsassure()==1){
				buf.append(" c.nIsAssure=1");
				if(qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsimpawn()==1){
				buf.append(" c.nIsImpawn=1");
				if(qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}	
			}
			if(qInfo.getIspledge()==1){
				buf.append(" c.nIsPledge=1");
				if(qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsrecognizance()==1){
				buf.append(" c.IsRecognizance=1");
				if(qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsrepurchase()==1){
				buf.append(" c.IsRepurchase=1");
			}
			buf.append(" ) ");
		}
			

		//信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		//是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		//是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		//是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		//贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());
		
		//add by wmzheng at 2010-10-14 贷款风险状态（复选）
		if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
			buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels()+")");
		
		//按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		//按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		//按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		//管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
		
		//办事处
		if(qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
		
        //币种
		if(qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
		
		//add by wmzheng at 2010-09-21
        //客户属性一
		if(qInfo.getClientTypeID1() > 0)
			buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());
		
        //客户属性二
		if(qInfo.getClientTypeID2() > 0)
			buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());
		
        //客户属性三  真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE1字段。
		if(qInfo.getClientTypeID3() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE1=" + qInfo.getClientTypeID3());
		
        //客户属性四 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE2字段。
		if(qInfo.getClientTypeID4() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE2=" + qInfo.getClientTypeID4());
		
        //客户属性五 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE3字段。
		if(qInfo.getClientTypeID5() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE3=" + qInfo.getClientTypeID5());
		
        //客户属性六 真正对应的是表CLIENT_CORPORATIONINFO中的NEXTENDATTRIBUTE4字段。
		if(qInfo.getClientTypeID6() > 0)
			buf.append(" and c1.NEXTENDATTRIBUTE4=" + qInfo.getClientTypeID6());
		
		sql[2] = sql[2] + buf.toString();

		//order
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) qInfo.getOrderParam())
		{
			case 1 :
				oBuf.append(" \n order by lt.id" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c1.sName" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c2.sName" + strDesc);
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
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode" + strDesc);
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

				//胡志强修改(2004-03-09)
			case 16 :
				oBuf.append(" \n order by a.overdueAmount" + strDesc);
				break;
			case 17 :
				oBuf.append(" \n order by b.punishInterest" + strDesc);
				break;

			case 20 :
				oBuf.append(" \n order by u2.sName" + strDesc);
				break;
			case 21 :
				oBuf.append(" \n order by c3.sName" + strDesc);
				break;
			case 22 :
				oBuf.append(" \n order by c.nrisklevel" + strDesc);
				break;				
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		sql[3] = oBuf.toString();
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		System.out.println("sql[0]==========="+sql[0]);
		System.out.println("sql[1]==========="+sql[1]);
		System.out.println("sql[2]==========="+sql[2]);
		System.out.println("sql[3]==========="+sql[3]);

		return sql;
	}
	
	public String[] getRZZLContractSQL(QuestContractInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		//select
		//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		sql[0] +=
			"c.ID as contractID,c.nTypeID as loanTypeID,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
				+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
				+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
				+ " ,db.dailybalance "; //指定余额日期的贷款余额 add by wmzheng at 2010-10-14
				
		//胡志强修改(2004-03-09)
		sql[0] += ",a.overdueAmount as overdueAmount,";
		sql[0] += "b.punishInterest as punishInterest";
		//added by xiong fei 2010/05/25查出各单位的担保信息
		sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
		sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
		sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";
		
		//add by wmzheng at 2010-09-25 合同风险状态
		sql[0] += ",c.nrisklevel as risklevel";
		
		//from
		sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
		//从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp " + "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		//指定余额日期的贷款余额 add by wmzheng at 2010-10-14
		sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
		sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
		sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"+DataFormat.getDateString(qInfo.getBalanceDate())+"','yyyy-mm-dd')";
		sql[1] += " group by db.nCOntractID ) db";
		
		//胡志强修改(2004-03-09)
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
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

		//where
		sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id"+" and db.ncontractid(+) = c.id";

		//胡志强修改(2004-03-09)
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";

		
		
	         buf.append(" and c.nTypeID=" +LOANConstant.LoanType.RZZL );
			
		
		//合同编号开始
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//合同编号结束
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		/***************添加国机的变更 2003-3-30 qqgd***************/
		//合同状态
		if (!qInfo.isShowEnd())
		{
			buf.append(" and c.nStatusID<>" + LOANConstant.ContractStatus.FINISH);
		}

		/************为了国机加的判断，限制状态 2004-4-23 qqgd *********/
		
		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++)
		{
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if (qInfo.getStatusID() > 0)
			{
			System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
			}
		if(qInfo.getStatusIDs()==null||qInfo.getStatusIDs().equals("")|| qInfo.getStatusIDs().equals("-1")){
		buf.append(" and c.nStatusID in (" + strStatus + ")");
		
		}

		//minzhao20090505修改将合同状态增加为多状态查询
		else
		{
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
		}

		//add by wmzheng at 2010-09-26 多个贷款种类、子种类
		if(!qInfo.getLoanTypeIDs().equals("")&&!qInfo.getLoanTypeIDs().equals("-1"))
		{
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs()+")");
		}
		/*if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1"))
		{
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")");
		}*/
		
		//借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		//借款单位开始
		if (qInfo.getBorrowClientIDFrom() > 0)
			buf.append(" and c.nBorrowClientID>=" + qInfo.getBorrowClientIDFrom());
		//借款单位结束
		if (qInfo.getBorrowClientIDTo() > 0)
			buf.append(" and c.nBorrowClientID<=" + qInfo.getBorrowClientIDTo());

		//借款单位账号
		if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		//客户分类
//		if (qInfo.getLoanClientTypeID() > 0)
//			buf.append(" and c1.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

		//主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		//委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
		//委托单位开始
		if (qInfo.getConsignClientIDFrom() > 0)
			buf.append(" and c.nConsignClientID>=" + qInfo.getConsignClientIDFrom());
		//委托单位结束
		if (qInfo.getConsignClientIDTo() > 0)
			buf.append(" and c.nConsignClientID<=" + qInfo.getConsignClientIDTo());

		//委托单位账号
		if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		//贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		//贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		//add by wmzheng at 2010-10-14 贷款余额
		//贷款余额开始
		if (qInfo.getMinLoanBalanceAmount() > 0)
			buf.append(" and lp.balance >= " + DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
		//贷款余额结束
		if (qInfo.getMaxLoanBalanceAmount() > 0)
			buf.append(" and lp.balance <= " + DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));
		
		//贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");

		//贷款结束日期结束
		if (qInfo.getMaxEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");

		//贷款结束日期开始
		if (qInfo.getMinEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
		
		//贴现日期结束
		if (qInfo.getMaxDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");

		//贴现日期开始
		if (qInfo.getMinDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");

		//合同利率 add by wmzheng at 2010-10-14
		if (qInfo.getContractRateFrom() > 0)
			buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= " + DataFormat.formatRate(qInfo.getContractRateFrom()));
		if (qInfo.getContractRateTo() > 0)
			buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= " + DataFormat.formatRate(qInfo.getContractRateTo()));
		
		//贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

		//贷款期限由
		if (qInfo.getPeriodFrom() > 0)
			buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
		//贷款期限至
		if (qInfo.getPeriodTo() > 0)
			buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());
		
		//保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");
		
		//added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
		if(qInfo.getIsassure()==1||qInfo.getIscredit()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
			buf.append(" and ( ");
			if(qInfo.getIscredit()==1){
				buf.append(" c.nIsCredit=1");
				if(qInfo.getIsassure()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsassure()==1){
				buf.append(" c.nIsAssure=1");
				if(qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsimpawn()==1){
				buf.append(" c.nIsImpawn=1");
				if(qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}	
			}
			if(qInfo.getIspledge()==1){
				buf.append(" c.nIsPledge=1");
				if(qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsrecognizance()==1){
				buf.append(" c.IsRecognizance=1");
				if(qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsrepurchase()==1){
				buf.append(" c.IsRepurchase=1");
			}
			buf.append(" ) ");
		}
			

		//信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		//是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		//是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		//是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		//贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());
		
		//add by wmzheng at 2010-10-14 贷款风险状态（复选）
		if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
			buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels()+")");
		
		//按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		//按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		//按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		//管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
		
		//办事处
		if(qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
		
        //币种
		if(qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
		
		//add by wmzheng at 2010-09-21
        //客户属性一
		if(qInfo.getClientTypeID1() > 0)
			buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());
		
        //客户属性二
		if(qInfo.getClientTypeID2() > 0)
			buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());
		
        //客户属性三
		if(qInfo.getClientTypeID3() > 0)
			buf.append(" and c1.nClienttypeID3=" + qInfo.getClientTypeID3());
		
        //客户属性四
		if(qInfo.getClientTypeID4() > 0)
			buf.append(" and c1.nClienttypeID4=" + qInfo.getClientTypeID4());
		
        //客户属性五
		if(qInfo.getClientTypeID5() > 0)
			buf.append(" and c1.nClienttypeID5=" + qInfo.getClientTypeID5());
		
        //客户属性六
		if(qInfo.getClientTypeID6() > 0)
			buf.append(" and c1.nClienttypeID6=" + qInfo.getClientTypeID6());
		
		sql[2] = sql[2] + buf.toString();

		//order
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) qInfo.getOrderParam())
		{
			case 1 :
				oBuf.append(" \n order by lt.id" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c1.sName" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c2.sName" + strDesc);
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
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode" + strDesc);
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

				//胡志强修改(2004-03-09)
			case 16 :
				oBuf.append(" \n order by a.overdueAmount" + strDesc);
				break;
			case 17 :
				oBuf.append(" \n order by b.punishInterest" + strDesc);
				break;

			case 20 :
				oBuf.append(" \n order by u2.sName" + strDesc);
				break;
			case 21 :
				oBuf.append(" \n order by c3.sName" + strDesc);
				break;
			case 22 :
				oBuf.append(" \n order by c.nrisklevel" + strDesc);
				break;				
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		sql[3] = oBuf.toString();
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		System.out.println("sql[0]==========="+sql[0]);
		System.out.println("sql[1]==========="+sql[1]);
		System.out.println("sql[2]==========="+sql[2]);
		System.out.println("sql[3]==========="+sql[3]);

		return sql;
	}
	
	public String[] getDBContractSQL(QuestContractInfo qInfo)
	{
		String[] sql = new String[4];
		StringBuffer buf = new StringBuffer();

		//select
		//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
		sql[0] = " /*+ INDEX_COMBINE(Y) */";
		sql[0] +=
			"c.ID as contractID,c.nTypeID as loanTypeID,'"+qInfo.getBalanceDate()+"' as dailyDate,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
				+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
				+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
				+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
				+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
				+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
				+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
				+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
				+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
				+ " ,db.dailybalance "; //指定余额日期的贷款余额 add by wmzheng at 2010-10-14
				
		//胡志强修改(2004-03-09)
		sql[0] += ",a.overdueAmount as overdueAmount,";
		sql[0] += "b.punishInterest as punishInterest";
		//added by xiong fei 2010/05/25查出各单位的担保信息
		sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
		sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
		sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";
		
		//add by wmzheng at 2010-09-25 合同风险状态
		sql[0] += ",c.nrisklevel as risklevel";
		
		//from
		sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
		//从子账户表中取得当前余额（包括贴现）
		sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp " + "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

		//指定余额日期的贷款余额 add by wmzheng at 2010-10-14
		sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
		sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
		sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"+DataFormat.getDateString(qInfo.getBalanceDate())+"','yyyy-mm-dd')";
		sql[1] += " group by db.nCOntractID ) db";
		
		//胡志强修改(2004-03-09)
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
		sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
		sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

		//where
		sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id"+" and db.ncontractid(+) = c.id";

		//胡志强修改(2004-03-09)
		sql[2] += " and c.id = a.id(+)";
		sql[2] += " and c.id = b.id(+)";
		sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";

		
		
		//（上海电气）担保类型 modified by zntan 2004-12-02
		if (qInfo.getQueryPurpose()== QuestContractInfo.DB)
		{
			sql[2] += " and c.nTypeID=" + LOANConstant.LoanType.DB;
		}
		

		
		
		//合同编号开始
		if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
			buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

		//合同编号结束
		if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
			buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

		/***************添加国机的变更 2003-3-30 qqgd***************/
		//合同状态
		if (!qInfo.isShowEnd())
		{
			buf.append(" and c.nStatusID<>" + LOANConstant.ContractStatus.FINISH);
		}

		/************为了国机加的判断，限制状态 2004-4-23 qqgd *********/
		
		long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
		String strStatus = "";
		for (int i = 0; i < contractStatusVal.length; i++)
		{
			strStatus += contractStatusVal[i];
			if ((contractStatusVal.length - i) > 1)
				strStatus += ", ";
		}
		if (qInfo.getStatusID() > 0)
			{
			System.out.println("qInfo.getStatusID()================="+qInfo.getStatusID());
			buf.append(" and c.nStatusID=" + qInfo.getStatusID());
			}
		if(qInfo.getStatusIDs()==null||qInfo.getStatusIDs().equals("")|| qInfo.getStatusIDs().equals("-1")){
		buf.append(" and c.nStatusID in (" + strStatus + ")");
		
		}

		//minzhao20090505修改将合同状态增加为多状态查询
		else
		{
			buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
		}

		//add by wmzheng at 2010-09-26 多个贷款种类、子种类
		if(!qInfo.getLoanTypeIDs().equals("")&&!qInfo.getLoanTypeIDs().equals("-1"))
		{
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs()+")");
		}
		/*if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1"))
		{
			buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")");
		}*/
		
		//借款单位
		if (qInfo.getBorrowClientID() > 0)
			buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
		//借款单位开始
		if (qInfo.getBorrowClientIDFrom() > 0)
			buf.append(" and c.nBorrowClientID>=" + qInfo.getBorrowClientIDFrom());
		//借款单位结束
		if (qInfo.getBorrowClientIDTo() > 0)
			buf.append(" and c.nBorrowClientID<=" + qInfo.getBorrowClientIDTo());

		//借款单位账号
		if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
			buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

		//客户分类
//		if (qInfo.getLoanClientTypeID() > 0)
//			buf.append(" and c1.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

		//主管单位
		if (qInfo.getParentCorpID() > 0)
			buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

		//委托单位
		if (qInfo.getConsignClientID() > 0)
			buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
		//委托单位开始
		if (qInfo.getConsignClientIDFrom() > 0)
			buf.append(" and c.nConsignClientID>=" + qInfo.getConsignClientIDFrom());
		//委托单位结束
		if (qInfo.getConsignClientIDTo() > 0)
			buf.append(" and c.nConsignClientID<=" + qInfo.getConsignClientIDTo());

		//委托单位账号
		if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
			buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

		//贷款金额开始
		if (qInfo.getMaxLoanAmount() > 0)
			buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

		//贷款金额结束
		if (qInfo.getMinLoanAmount() > 0)
			buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

		//add by wmzheng at 2010-10-14 贷款余额
		//贷款余额开始
		if (qInfo.getMinLoanBalanceAmount() > 0)
			buf.append(" and lp.balance >= " + DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
		//贷款余额结束
		if (qInfo.getMaxLoanBalanceAmount() > 0)
			buf.append(" and lp.balance <= " + DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));
		
		//贷款日期开始
		if (qInfo.getMaxStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");

		//贷款日期结束
		if (qInfo.getMinStartDate() != null)
			buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");

		//贷款结束日期结束
		if (qInfo.getMaxEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");

		//贷款结束日期开始
		if (qInfo.getMinEndDate() != null)
			buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
		
		//贴现日期结束
		if (qInfo.getMaxDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");

		//贴现日期开始
		if (qInfo.getMinDiscountDate() != null)
			buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");

		//合同利率 add by wmzheng at 2010-10-14
		if (qInfo.getContractRateFrom() > 0)
			buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= " + DataFormat.formatRate(qInfo.getContractRateFrom()));
		if (qInfo.getContractRateTo() > 0)
			buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= " + DataFormat.formatRate(qInfo.getContractRateTo()));
		
		//贷款期限
		if (qInfo.getIntervalNum() > 0)
			buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

		//贷款期限由
		if (qInfo.getPeriodFrom() > 0)
			buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
		//贷款期限至
		if (qInfo.getPeriodTo() > 0)
			buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());
		
		//保证方式
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
			buf.append(" and c.nIsCredit=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
			buf.append(" and c.nIsAssure=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
			buf.append(" and c.nIsImpawn=1");
		if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
			buf.append(" and c.nIsPledge=1");
		
		//added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
		if(qInfo.getIsassure()==1||qInfo.getIscredit()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
			buf.append(" and ( ");
			if(qInfo.getIscredit()==1){
				buf.append(" c.nIsCredit=1");
				if(qInfo.getIsassure()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsassure()==1){
				buf.append(" c.nIsAssure=1");
				if(qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsimpawn()==1){
				buf.append(" c.nIsImpawn=1");
				if(qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}	
			}
			if(qInfo.getIspledge()==1){
				buf.append(" c.nIsPledge=1");
				if(qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsrecognizance()==1){
				buf.append(" c.IsRecognizance=1");
				if(qInfo.getIsrepurchase()==1){
					buf.append(" or");
				}
			}
			if(qInfo.getIsrepurchase()==1){
				buf.append(" c.IsRepurchase=1");
			}
			buf.append(" ) ");
		}
			

		//信用等级
		if (qInfo.getCreditLevel() > 0)
			buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

		//是否股东
		if (qInfo.getIsPartner() > 0)
			buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

		//是否技改贷款
		if (qInfo.getIsTechnical() > 0)
			buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

		//是否循环贷款
		if (qInfo.getIsCircle() > 0)
			buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

		//贷款风险状态
		if (qInfo.getRiskLevel() > 0)
			buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());
		
		//add by wmzheng at 2010-10-14 贷款风险状态（复选）
		if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
			buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels()+")");
		
		//按地区分类
		if (qInfo.getTypeID1() > 0)
			buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

		//按行业分类1
		if (qInfo.getTypeID2() > 0)
			buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

		//按行业分类2
		if (qInfo.getTypeID3() > 0)
			buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

		//管理人
		if (qInfo.getInputUserID() > 0)
			buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
		
		//办事处
		if(qInfo.getOfficeID() > 0)
			buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
		
        //币种
		if(qInfo.getCurrencyID() > 0)
			buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
		
		//add by wmzheng at 2010-09-21
        //客户属性一
		if(qInfo.getClientTypeID1() > 0)
			buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());
		
        //客户属性二
		if(qInfo.getClientTypeID2() > 0)
			buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());
		
        //客户属性三
		if(qInfo.getClientTypeID3() > 0)
			buf.append(" and c1.nClienttypeID3=" + qInfo.getClientTypeID3());
		
        //客户属性四
		if(qInfo.getClientTypeID4() > 0)
			buf.append(" and c1.nClienttypeID4=" + qInfo.getClientTypeID4());
		
        //客户属性五
		if(qInfo.getClientTypeID5() > 0)
			buf.append(" and c1.nClienttypeID5=" + qInfo.getClientTypeID5());
		
        //客户属性六
		if(qInfo.getClientTypeID6() > 0)
			buf.append(" and c1.nClienttypeID6=" + qInfo.getClientTypeID6());
		
		sql[2] = sql[2] + buf.toString();

		//order
		String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
		StringBuffer oBuf = new StringBuffer();
		switch ((int) qInfo.getOrderParam())
		{
			case 1 :
				oBuf.append(" \n order by lt.id" + strDesc);
				break;
			case 2 :
				oBuf.append(" \n order by c.sContractCode" + strDesc);
				break;
			case 3 :
				oBuf.append(" \n order by c1.sName" + strDesc);
				break;
			case 4 :
				oBuf.append(" \n order by c2.sName" + strDesc);
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
				oBuf.append(" \n order by u.sName" + strDesc);
				break;
			case 11 :
				oBuf.append(" \n order by d.sApplyCode" + strDesc);
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

				//胡志强修改(2004-03-09)
			case 16 :
				oBuf.append(" \n order by a.overdueAmount" + strDesc);
				break;
			case 17 :
				oBuf.append(" \n order by b.punishInterest" + strDesc);
				break;

			case 20 :
				oBuf.append(" \n order by u2.sName" + strDesc);
				break;
			case 21 :
				oBuf.append(" \n order by c3.sName" + strDesc);
				break;
			case 22 :
				oBuf.append(" \n order by c.nrisklevel" + strDesc);
				break;				
			default :
				oBuf.append(" \n order by c.ID" + strDesc);
				break;
		}
		sql[3] = oBuf.toString();
		log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
		System.out.println("sql[0]==========="+sql[0]);
		System.out.println("sql[1]==========="+sql[1]);
		System.out.println("sql[2]==========="+sql[2]);
		System.out.println("sql[3]==========="+sql[3]);

		return sql;
	}
	
	
	
	public PageLoader queryContract(QuestContractInfo qInfo) throws Exception
	{
		String[] sql=null;
		if(qInfo.getQueryPurpose()==QuestContractInfo.TX)
		{
			sql = getContractSQL_wlx(qInfo);//贴现SQL
		}
		else if(qInfo.getQueryPurpose()==QuestContractInfo.RZZL)
		{
			sql = getRZZLContractSQL(qInfo);//融资租赁SQL
		}
		else if(qInfo.getQueryPurpose()==QuestContractInfo.DB){
			
			sql = getDBContractSQL(qInfo);//担保SQL
			
		}else 
		{
			
			sql = getContractSQL(qInfo);//贷款合同查询
			
		}
		/************为了国机的汇总而加入 2003-3-30 qqgd *************/
		if (qInfo.getGather())
		{
			String strTmp = "(select \n" + sql[0] + " \nfrom " + sql[1] + " \nwhere " + sql[2] + ") sz";
			sql[0] = "*";
			sql[1] =
				" (select borrowClientName,sum(nvl(examineAmount,0)) as examineAmount,sum(nvl(balance,0)) as balance, "
					+ " sum(nvl(overdueAmount,0)) as overdueAmount,sum(nvl(punishInterest,0)) as punishInterest, "
					+ " sum(nvl(discountPurchaserInterest,0)) as discountPurchaserInterest,"
					+ " sum(nvl(discountInterest,0)) as discountInterest"
					+ " from "
					+ strTmp
					+ " group by sz.borrowClientName ) ";
			sql[2] = "";
			sql[3] = "";
			System.out.println("-----------------------------");
			System.out.println(sql[1]);
			System.out.println("-----------------------------");
		}
		System.out.println("000000"+sql[0]);
		System.out.println("111111"+sql[1]);
		System.out.println("222222"+sql[2]);
		System.out.println("000000"+sql[3]);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		if(qInfo.getQueryPurpose()==QuestContractInfo.TX){	
		   
			 pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.contract.dataentity.TXContractDetailInfo", null);
		     pageLoader.setOrderBy(" " + sql[3] + " ");
		}
		else if(qInfo.getQueryPurpose()==QuestContractInfo.RZZL)
		{
			 pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.contract.dataentity.RZZLContractDetailInfo", null);
			 pageLoader.setOrderBy(" " + sql[3] + " ");//融资租赁SQL
		}
		else if(qInfo.getQueryPurpose()==QuestContractInfo.DB){
			
			 pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.contract.dataentity.DBContractDetailInfo", null);
			 pageLoader.setOrderBy(" " + sql[3] + " ");//担保SQL
			
		}
		else 
		{
			
			 pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.contract.dataentity.ContractDetailInfo", null);
			 pageLoader.setOrderBy(" " + sql[3] + " ");//贷款合同查询
			
		}
		
		return pageLoader;
	}
	
	/**
	 * 查询合同利率调整信息
	 * @author 马现福
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryContractAdjustInfo(ContractInfoAdjustQuery adjustQueryInfo) throws Exception
	{
		String sql0 = null;	
		StringBuffer sql1 = new StringBuffer();
		String sql2 = null;
		String sql3 = null;
		//需要查询的字段
		sql0 = " tmp.DtoutDate,tmp.Code ,tmp.Name,tmp.SContractCode,tmp.SCode,\n" +
				" tmp.NIsCircle,tmp.MAmount,tmp.MInterestRate,tmp.NIntervalNoticeNum,\n" +
				" tmp.ID,tmp.ContractID,tmp.DrawNoticeID \n";
		
		//进行查询的表
		sql1.append(" ((select lp.DtoutDate,cc.Code ,cc.Name,lc.SContractCode,lp.SCode,\n ");
		sql1.append(" lc.NIsCircle,lp.MAmount,lp.MInterestRate,lp.NIntervalNoticeNum, \n");
		sql1.append(" lp.ID,lp.ncontractid as ContractID,lp.ndrawnoticeid as DrawNoticeID \n");
		sql1.append(" from (select min(id) id ,nloancontractid \n");
		sql1.append("      from (select * \n");
		sql1.append("           from SETT_TRANSGRANTLOAN aa \n");
		sql1.append("           where aa.nofficeid = "+adjustQueryInfo.getOfficeID()+" \n");
		sql1.append("           and aa.ncurrencyid = "+adjustQueryInfo.getCurrencyID()+" \n");
		sql1.append("           and aa.nstatusid in ("+SETTConstant.TransactionStatus.CHECK+") \n");
		sql1.append("           and aa.nloancontractid not in \n");
		sql1.append("            (select nloancontractid \n");
		sql1.append("            from SETT_TRANSGRANTLOAN \n");
		sql1.append("            where dtexecute > to_date('"+DataFormat.getDateString(Env.getSystemDate(adjustQueryInfo.getOfficeID(),adjustQueryInfo.getCurrencyID()))+"', 'yyyy-mm-dd') \n");
		sql1.append("            and nofficeid = "+adjustQueryInfo.getOfficeID()+" \n");
		sql1.append("            and ncurrencyid = "+adjustQueryInfo.getCurrencyID()+" \n");
		sql1.append("            and nstatusid in ("+SETTConstant.TransactionStatus.CHECK+")) \n");
		sql1.append("            order by aa.nloancontractid, aa.dtmodify) d \n");
		sql1.append("            group by nloancontractid) dd, \n");
		sql1.append(" SETT_TRANSGRANTLOAN e,loan_payform lp,loan_contractform lc,client_clientinfo cc \n");
		sql1.append(" where e.id = dd.id \n");
		sql1.append(" and lp.id = e.nloannoteid \n");
		sql1.append(" and lp.ncontractid = lc.id \n");
		sql1.append(" and lc.nborrowclientid = cc.id \n");
		sql1.append(" and lp.nOfficeID = lc.nOfficeID \n");
		sql1.append(" and lc.nOfficeID = cc.OfficeID \n");
		sql1.append(" and lp.nCurrencyID = lc.nCurrencyID \n");
		sql1.append(" and lp.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED+" \n");
		sql1.append(" and lc.niscircle = 2 )\n");
		sql1.append(" union \n");
		sql1.append(" (select lp.DtoutDate,cc.Code ,cc.Name,lc.SContractCode,lp.SCode,\n ");
		sql1.append(" lc.NIsCircle,lp.MAmount,lp.MInterestRate,lp.NIntervalNoticeNum, \n");
		sql1.append(" lp.ID,lp.ncontractid as ContractID,lp.ndrawnoticeid as DrawNoticeID \n");
		sql1.append(" from (select id ,nloancontractid \n");
		sql1.append("      from (select * \n");
		sql1.append("           from SETT_TRANSGRANTLOAN aa \n");
		sql1.append("           where aa.nofficeid = "+adjustQueryInfo.getOfficeID()+" \n");
		sql1.append("           and aa.ncurrencyid = "+adjustQueryInfo.getCurrencyID()+" \n");
		sql1.append("           and aa.nstatusid in ("+SETTConstant.TransactionStatus.CHECK+") \n");
		sql1.append("           and aa.nloancontractid not in \n");
		sql1.append("            (select nloancontractid \n");
		sql1.append("            from SETT_TRANSGRANTLOAN \n");
		sql1.append("            where dtexecute > to_date('"+DataFormat.getDateString(Env.getSystemDate(adjustQueryInfo.getOfficeID(),adjustQueryInfo.getCurrencyID()))+"', 'yyyy-mm-dd') \n");
		sql1.append("            and nofficeid = "+adjustQueryInfo.getOfficeID()+" \n");
		sql1.append("            and ncurrencyid = "+adjustQueryInfo.getCurrencyID()+" \n");
		sql1.append("            and nstatusid in ("+SETTConstant.TransactionStatus.CHECK+")) \n");
		sql1.append("            order by aa.nloancontractid, aa.dtmodify) d \n");
		sql1.append("            group by id,nloancontractid) dd, \n");
		sql1.append(" SETT_TRANSGRANTLOAN e,loan_payform lp,loan_contractform lc,client_clientinfo cc \n");
		sql1.append(" where e.id = dd.id \n");
		sql1.append(" and lp.id = e.nloannoteid \n");
		sql1.append(" and lp.ncontractid = lc.id \n");
		sql1.append(" and lc.nborrowclientid = cc.id \n");
		sql1.append(" and lp.nOfficeID = lc.nOfficeID \n");
		sql1.append(" and lc.nOfficeID = cc.OfficeID \n");
		sql1.append(" and lp.nCurrencyID = lc.nCurrencyID \n");
		sql1.append(" and lp.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED+" \n");
		sql1.append(" and lc.niscircle = 1 )) tmp \n");
		
		//查询条件
		sql2 = " 1=1 \n";
		if(null != adjustQueryInfo){
			if(null != adjustQueryInfo.getContractCode() && !"".equals(adjustQueryInfo.getContractCode())){
				sql2 = sql2+" and tmp.SContractCode = '"+adjustQueryInfo.getContractCode()+"' \n";
			}
			if(null != adjustQueryInfo.getMinStartDate() && !"".equals(adjustQueryInfo.getMinStartDate())){
				sql2 = sql2+" and to_char(tmp.DtoutDate,'mm-dd') >= to_char(to_date('" + adjustQueryInfo.getMinStartDate()+"','yyyy-mm-dd'),'mm-dd') \n";
			}
			if(null != adjustQueryInfo.getMaxStartDate() && !"".equals(adjustQueryInfo.getMaxStartDate())){
				sql2 = sql2+" and to_char(tmp.DtoutDate,'mm-dd') <= to_char(to_date('" + adjustQueryInfo.getMaxStartDate()+"','yyyy-mm-dd'),'mm-dd') \n";
			}
		}
		sql2 = sql2+" group by tmp.DtoutDate,tmp.Code ,tmp.Name,tmp.SContractCode,tmp.SCode, \n" +
				" tmp.NIsCircle,tmp.MAmount,tmp.MInterestRate,tmp.NIntervalNoticeNum, \n" +
				" tmp.ID,tmp.ContractID,tmp.DrawNoticeID \n";
		//查询的排序字段
		sql3 = " order by tmp.DtoutDate ";
		System.out.println("---------------------------------------------------");
		System.out.println("select"+sql0+"from"+sql1+"where"+sql2+sql3);
		System.out.println("---------------------------------------------------");
		
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(new AppContext(), sql1.toString(), sql0, sql2, (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.query.dataentity.ContractInfoAdjustQuery", null);
		pageLoader.setOrderBy(" " + sql3 + " ");
		return pageLoader;
	}
	
	public QuerySumInfo QueryContractSum(QuestContractInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = new QuerySumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String[] sql = null;
		if(qInfo.getQueryPurpose()==QuestContractInfo.TX)
		{
			sql = getContractSQL_wlx(qInfo);//贴现SQL
		}
		else if(qInfo.getQueryPurpose()==QuestContractInfo.RZZL)
		{
			sql = getRZZLContractSQL(qInfo);//融资租赁SQL
		}
		else if(qInfo.getQueryPurpose()==QuestContractInfo.DB){
			
			sql = getDBContractSQL(qInfo);//担保SQL
			
		}else 
		{
			
			sql = getContractSQL(qInfo);//贷款合同查询
			
		}
		
		String[] SQL=new String[2];
		String strSQL = "";
		System.out.println("-------------Welcome to Method 'QueryContractSum'----------------");
		try
		{
			
			//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
			SQL[0] = " /*+ INDEX_COMBINE(Y) */";
			SQL[0] += " sum(NVL(q.LoanAmount,0)) as sumLoanAmount,";
			SQL[0] += " sum(nvl(q.OverdueAmount,0)) as sumOverdueAmount,";
			SQL[0] += " sum(nvl(q.PunishInterest,0)) as sumPunishInterest,";
			SQL[0] += " sum(nvl(q.Balance,0)) as sumBalance,";
			SQL[0] += " sum(nvl(q.CheckAmount,0)) as sumCheckAmount,";
			SQL[0] += " sum(nvl(q.PurchaserInterest,0)) as sumDiscountPurchaserInterest,";
			SQL[0] += " sum(nvl(q.DiscountInterest,0)) as sumDiscountInterest";
			if(qInfo.getQueryPurpose()!=QuestContractInfo.TX){
		    SQL[0] += ", sum(nvl(q.dailybalance, 0)) as sumDailyBalance";//add by wmzheng at 2010-10-14 指定余额日期贷款余额
			}
			//贷款金额计算错误 2008-12-15No.261修改 kaishao
			SQL[1] = " (select c.mExamineAmount as LoanAmount,";
			//No.261结束。删除原有的distinct
			SQL[1] +=" a.overdueAmount as OverdueAmount,"; 
			SQL[1] +=" b.punishInterest as PunishInterest,";
			SQL[1] +=" lp.balance as Balance, c.mCheckAmount as CheckAmount,";
			//SQL[1] +=" (c.mExamineAmount - c.mCheckAmount)/decode((1-c.purchaserInterestRate*0.01),0,1,(1-c.purchaserInterestRate*0.01))*c.purchaserInterestRate*0.01 as PurchaserInterest,";
			//SQL[1] +=" (c.mExamineAmount - c.mCheckAmount) as DiscountInterest ";
			SQL[1] +=" c.MPURCHASERAMOUNT as PurchaserInterest,";
			SQL[1] +=" c.MDISCOUNTACCRUAL as DiscountInterest ";
			if(qInfo.getQueryPurpose()!=QuestContractInfo.TX)
			SQL[1] +=", db.dailybalance as Dailybalance ";//add by wmzheng at 2010-10-14 指定余额日期贷款余额
	
			
			SQL[1] +=" from "+sql[1];
			SQL[1] +=" where "+sql[2];
			SQL[1] +=") q";
			strSQL =  " select " + SQL[0] + " from " + SQL[1]; 
			log.print("*&%%$$#$%$$$$"+strSQL);

			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setTotalApplyAmount(rs.getDouble("sumLoanAmount"));
				sumInfo.setSumOverdueAmount(rs.getDouble("sumOverdueAmount"));
				sumInfo.setSumPunishInterest(rs.getDouble("sumPunishInterest"));
				sumInfo.setTotalBalance(rs.getDouble("sumBalance"));
				if(qInfo.getQueryPurpose()!=QuestContractInfo.TX)
				sumInfo.setTotalDailyBalance(rs.getDouble("sumDailyBalance"));
				sumInfo.setSumCheckAmount(rs.getDouble("sumCheckAmount"));
				sumInfo.setSumDiscountInterest(rs.getDouble("sumDiscountInterest"));
				sumInfo.setSumDiscountPurchaserInterest(rs.getDouble("sumDiscountPurchaserInterest"));
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

	/*
	 *
	 * @author haoning
	 * @time 2003-11-18
	 * @param QuestContractPlanInfo
	 * function
	 * return String strSQL
	 */
	public String[] getContractPlanSQL(QuestContractPlanInfo qInfo) throws Exception
	{
		String[] strSQL = { "", "", "", "" };

		//from 
		strSQL[0] = " loan_loancontractplan a,loan_contractform b,UserInfo c,LOAN_PLANMODIFYFORM d,UserInfo c1 ";

		//select
		strSQL[1] = " a.id as PlanID,a.nPlanVersion as PlanVersion " + " ,c.sName as Modifier,c1.sName as nextCheckUserName " + " ,b.sContractCode as ContractCode " + " ,a.dtInputDate as InputDate ";

		//where
		strSQL[2] =
			" a.nContractID=b.ID(+) " + " and b.nInputUserID=c.ID(+) " + " and d.NNEXTCHECKUSERID = c1.ID(+) " + " and d.NCONTRACTID(+) = b.ID " + " and a.nStatusID = " + Constant.RecordStatus.VALID;
		if (qInfo.getContractID() > 0)
		{
			strSQL[2] += " and a.nContractID= " + qInfo.getContractID();
		}

		//order by
		strSQL[3] = " order by a.nPlanVersion ";

		/**********处理查询条件*************/
		return strSQL;
	}

	public String[] getContractPlanSQL1(QuestContractPlanInfo qInfo) throws Exception
	{
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
		strSQL[2] =	" a.nContractID=b.ID(+) ";
		strSQL[2] += " and b.nInputUserID=c2.ID(+) ";
		strSQL[2] += " and d.NNEXTCHECKUSERID = c1.ID(+) " ;
		strSQL[2] += " and d.ninputuserid = c.ID(+) " ;
		strSQL[2] += " and d.NCONTRACTID(+) = a.nContractID ";
		strSQL[2] += " and a.nStatusID = " + Constant.RecordStatus.VALID;
		strSQL[2] += " and a.id =d.nplanid(+)";
		strSQL[2] += " and (d.nstatusid >0  or d.nstatusid is null  )";
		
		
		if (qInfo.getContractID() > 0)
		{
			strSQL[2] += " and a.nContractID= " + qInfo.getContractID();
		}

		//order by
		strSQL[3] = " order by a.nPlanVersion ";

		/**********处理查询条件*************/
		return strSQL;
	}

	/*
	 * @author haoning
	 * @time 2003-11-18
	 * @param QuestContractPlanInfo
	 * function
	 */
	public PageLoader queryQuestContractPlanInfo(QuestContractPlanInfo qInfo) throws Exception
	{
		String[] sql = getContractPlanSQL1(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql[0], sql[1], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.query.dataentity.QuestContractPlanInfo", null);
		pageLoader.setOrderBy(" " + sql[3] + " ");
		return pageLoader;
	}

	public Collection queryContractPlanResult(QuestContractPlanInfo qInfo) throws Exception
	{
		Vector v = new Vector();
		String strSQL = "";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			String[] sql = getContractPlanSQL1(qInfo);
			strSQL = " select " + sql[1] + " from " + sql[0] + " where " + sql[2] + sql[3];
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				QuestContractPlanInfo info = new QuestContractPlanInfo();
				info.setPlanID(rs.getLong("planID"));
				info.setPlanVersion(rs.getLong("planVersion"));
				info.setContractCode(rs.getString("contractCode"));
				info.setModifier(rs.getString("modifier"));
				info.setInputDate(rs.getTimestamp("InputDate"));

				v.add(info);
			}
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
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		return v;
	}

/**
 * 贷款展期查询
 * @param qInfo
 * @return
 * @throws Exception
 */
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
	
	/*
	 *
	 * @author haoning
	 * @time 2003-11-20
	 * @param QueryDao
	 * function
	 */

	public QuerySumInfo QueryExtendSum(QuestExtendInfo qInfo) throws Exception
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		QuerySumInfo sumInfo = new QuerySumInfo();
		String[] sql1 = getExtendSQL1(qInfo);
		String[] sql2 = getExtendSQL2(qInfo);
		String strSQL = "";
		String NLoanType=qInfo.getLoanTypeIDs();
		boolean flag = isLoan_WT(NLoanType, qInfo.getNOfficeId(), qInfo.getNCurrencyId());
		try
		{	if(!flag && qInfo.getConsignClientIDFrom()<0 && qInfo.getConsignClientIDTo()<0){
			strSQL = "select count(*) ,sum(b.MEXAMINEAMOUNT) as TotalAmount,sum(e.mextendamount) as TotalExtendAmount from " + sql1[0] + " where " + sql1[2];
		    }
	       else if(qInfo.getConsignClientIDFrom()>0 || qInfo.getConsignClientIDTo()>0){
	    	   
	    	   strSQL = "select count(*) ,sum(b.MEXAMINEAMOUNT) as TotalAmount,sum(e.mextendamount) as TotalExtendAmount from " + sql2[0] + " where " + sql2[2];
	       }
	       else {
	    	   strSQL="select nvl(sum(a.TotalAmount),0) as TotalAmount, nvl(sum(a.TotalExtendAmount),0) as TotalExtendAmount   from (select sum(b.MEXAMINEAMOUNT) as TotalAmount,sum(e.mextendamount) as TotalExtendAmount from " + sql1[0] + " where " + sql1[2]+" union "+"select sum(b.MEXAMINEAMOUNT) as TotalAmount,sum(e.mextendamount) as TotalExtendAmount from " + sql2[0] + " where " + sql2[2]+") a";
	    	   
	    	   
	       }
			log.print(strSQL);

			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				sumInfo.setAllRecord(rs.getLong(1));
				sumInfo.setTotalApplyAmount(rs.getDouble("TotalAmount"));
				sumInfo.setTotalExtendAmount(rs.getDouble("TotalExtendAmount"));
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

	/*
	 *
	 * @author haoning
	 * @time 2003-11-20
	 * @param QueryDao
	 * function
	 */

	public PageLoader queryQuestExtendInfo(QuestExtendInfo qInfo) throws Exception
	{
		String[] sql1 = getExtendSQL1(qInfo);
		String[] sql2 = getExtendSQL2(qInfo);
		String[] sql3 = new String[4];
		
		String NLoanType = qInfo.getLoanTypeIDs();
		
		boolean flag = isLoan_WT(NLoanType, qInfo.getNOfficeId(), qInfo.getNCurrencyId());
		
		
		//贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
		if(!flag && qInfo.getConsignClientIDFrom()<0 && qInfo.getConsignClientIDTo()<0){
			sql3[0]="*";
			sql3[1] = "(select " + sql1[1] +" from " +sql1[0] +" where " + sql1[2]+")";
			sql3[2]="1=1";
			sql3[3]="";
		}else if(qInfo.getConsignClientIDFrom()>0 || qInfo.getConsignClientIDTo()>0){
			sql3[0]="*";
			sql3[1] = "(select " + sql2[1] +" from " +sql2[0] +" where " + sql2[2]+")";
			sql3[2]="1=1";
			sql3[3]="";
			
		}else 
		{ sql3[0]="*";
		  sql3[1] = "(select " + sql1[1] +" from " +sql1[0] +" where "  + sql1[2] + " union "+"select " + sql2[1] +" from " +sql2[0] +" where "  + sql2[2]+")";
		  sql3[2]="1=1";
		  sql3[3]="";
		  
		}
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(new AppContext(), sql3[1], sql3[0],sql3[2] , (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.query.dataentity.QuestExtendInfo", null);
		pageLoader.setOrderBy(" " + sql3[3] + " ");
		return pageLoader;
	}

	
	/*
	 *
	 * @author haoning
	 * @time 2003-11-21
	 * @param QueryDao
	 * function
	 */
	/**
	 * 根据UserID查询贷款申请书状态
	 * 操作数据库表<loan_Loanform>
	 * @param     long       lUserID            用户ID
	 * @param     long       lStatusID          查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param     long       lOfficeID          办事处标示(未用）
	 * @param     long       lCurrencyID        币种
	 * @return    long[]                        业务名称(借用)&
	     查询申请书状态总数(借用)
	 **/

	public long[] QueryLoanCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;

		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;

		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();

		//		业务类型
		long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		int len = lLoanType.length;
		long[] lResult = new long[len + 1];

		try
		{
			con = Database.getConnection();

			String sLoanType = "";
			long[] lTmp = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
			for (int i = 0; i < lTmp.length; i++)
			{
				if (i == 0)
				{
					sLoanType += "" + lTmp[i];
				}
				else
				{
					if (lTmp[i] == LOANConstant.LoanType.TX || lTmp[i] == LOANConstant.LoanType.ZTX)
					{
						sLoanType += "";
					}
					else
					{
						sLoanType += "," + lTmp[i];
					}
				}
			}
			strSQL_Count = " select  count(a.ID) ";
			strSQL_Table =
				" from loan_loanForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID in (" + sLoanType + " ) " + "";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;
			}
			/********2003-12-03 考虑虚拟用户*********************/
			log.info("=========QueryLoanApplyCount==========");
			for (int nType = (int) LOANConstant.LoanType.ZY; nType <= len; nType++)
			{
				if (lStatusID == 2) //2--待审核
				{
					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）

					strUserID = appBiz.findTheVeryUser(lModuleID, lLoanType[nType - 1], lActionID,lOfficeID,lCurrencyID, lUserID);

					strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
					strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;

				}
				strSQL_Order = " and a.nTypeID = " + lLoanType[nType - 1];
				strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
				//log.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult[nType] = rs.getLong(1);
					lResult[0] += lResult[nType];
					log.info(strSQL + "  lCount[" + nType + "]:" + lResult[nType]);
				}
				cleanup(rs);
				cleanup(ps);
			}
			log.info("=========QueryLoanApplyCount==========");
			//log.info("all:"+lResult[0]);
			/***************************************************************/
			/*/strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			log.info("=========QueryLoanApplyCount==========");
			log.info(strSQL);
			log.info("=========QueryLoanApplyCount==========");
			ps = con.prepareStatement(strSQL);
			rs=ps.executeQuery();
			while (rs != null && rs.next())
			{
			    lResult[(int)rs.getLong(1)] = rs.getLong(2);
			    lResult[0] += lResult[(int)rs.getLong(1)];
			}//*/
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	 * 根据UserID查询展期申请书状态
	 * 操作数据库表<loan_Extendform>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(2--待审核;0--撰写;1--提交 ; )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return    long                          业务名称(借用)&
	     查询申请书状态总数(借用)
	 **/
	public long[] QueryExtendApplyCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;

		//操作类型
		long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
		ApprovalDelegation appBiz = new ApprovalDelegation();
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		//strUserID = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lUserID);

		//long lResult =0;
		//		业务类型
		long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		int len = lLoanType.length;
		long[] lResult = new long[len + 1];

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table = " from  loan_extendform a ,loan_contractForm b " + " where a.nContractID=b.ID(+)  " + "   and b.nOfficeID = " + lOfficeID + "   and b.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ExtendStatus.SUBMIT;
			}
			//strSQL_Order = " group by b.nTypeID ";
			/********2004-07-21 考虑虚拟用户***** ninh *********/
			/* 展期申请审核查找时候 
			 * 不用 Constant.ApprovalLoanType.OTHER 
			 * 根据每种贷款类型 取其有审核权限的用户
			 * 但是填写审核意见时候 
			 * 还是用 Constant.ApprovalLoanType.OTHER
			 * 是为了兼容已有的审核意见
			 */
			for (int nType = (int) LOANConstant.LoanType.ZY; nType <= len; nType++)
			{
				if (lStatusID == 2) //2--待审核
				{
					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
					strUserID = appBiz.findTheVeryUser(lModuleID, lLoanType[nType - 1], lActionID,lOfficeID,lCurrencyID, lUserID);

					strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
					strSQL_Option += " and a.nStatusID= " + LOANConstant.ExtendStatus.SUBMIT;

				}
				strSQL_Order = " and b.nTypeID = " + lLoanType[nType - 1];
				strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
				log.info("=========QueryExtendApplyCount==========");
				//log.info(strSQL);
				//log.info("=========QueryExtendApplyCount==========");
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					//lResult = rs.getLong(1);
					lResult[nType] = rs.getLong(1);
					lResult[0] += lResult[nType];
					log.info(strSQL + " lCount[" + nType + "]:" + lResult[nType]);
				}
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/************************************************????????????***********************/
	/**
	 * 根据UserID查询免还申请书状态
	 * 操作数据库表<loan_Freeform>
	 * @param    long       lUserID            用户ID
	 * @param    long      lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID          办事处标示(未用）
	 * @param    long       lCurrencyID        币种(未用）
	 *
	 * @return    long[]                       业务名称(借用)&
	     查询申请书状态总数(借用)
	 **/
	public long[] QueryFreeApplyCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = LOANConstant.LoanType.WT;
		//操作类型
		long lActionID = Constant.ApprovalAction.FREE_APPLY;
		ApprovalDelegation appBiz = new ApprovalDelegation();
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";

		long[] lResult = { 0, 0, 0, 0, 0 };

		try
		{
			con = Database.getConnection();

			//strSQL_Count = " select b.nTypeID, count(a.ID) ";
			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from loan_freeForm a ,loan_contractForm b "
					+ " ,loan_payform c "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and a.nPayFormID=c.ID "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
				//+ "   and b.nTypeID in ("
		//+       LOANConstant.LoanType.WT
		//+ ","+  LOANConstant.LoanType.WTTJTH
		//+ "  ) "
	+"";
			//-----------委托贷款-------------//
			if (lStatusID == 1) //2--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.FreeApplyStatus.SUBMIT;
			}
			//strSQL_Order = " group by b.nTypeID ";
			for (int nType = (int) LOANConstant.LoanType.WT; nType <= (int) LOANConstant.LoanType.WT; nType++)
			{
				if (lStatusID == 2) //1--待审核
				{
					lLoanTypeID = (long) nType;
					strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

					strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
					strSQL_Option += " and a.nStatusID= " + LOANConstant.FreeApplyStatus.SUBMIT;

				}
				strSQL_Order = " and b.nTypeID = " + (long) nType;
				strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
				//log.info("=========QueryFreeApplyCount=========="+nType);
				//log.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult[nType] = rs.getLong(1);
					lResult[0] += lResult[nType];
					log.info(strSQL);
					log.info("" + nType + ":" + lResult[nType]);
				}
				//log.info("=========QueryFreeApplyCount=========="+nType);
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}

	/**
	 * 根据UserID查询贴现申请书状态
	 * 操作数据库表<loan_loanForm>
	 * @param     long       lUserID             用户ID
	 * @param     long      lStatusID            查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID            办事处标示(未用）
	 * @param    long       lCurrencyID          币种(未用）
	 *
	 * @return    long       lCount              查询申请书状态总数
	 **/
	public long QueryDiscountApplyCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.TX;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select  count(a.ID) ";
			strSQL_Table =
				" from loan_loanForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID = " + LOANConstant.LoanType.TX + "";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;

			}
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryDiscountApplyCount==========");
			//log.info(strSQL);
			log.info("=========QueryDiscountApplyCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询贴现凭证状态
	 * 操作数据库表<loan_discountcredence>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryDiscountCredenceCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.TX;
		//操作类型
		long lActionID = Constant.ApprovalAction.DISCOUNT_CREDENCE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_discountcredence a ,loan_contractForm b " + " where a.nContractID=b.ID(+)  " + "   and b.nOfficeID = " + lOfficeID + "   and b.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.DiscountCredenceStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.DiscountCredenceStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryDiscountCredenceCount==========");
			//log.info(strSQL);
			log.info("=========QueryDiscountCredenceCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询放款通知单状态
	 * 操作数据库表<loan_payform>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryPayCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOANPAY_NOTICE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_payform a ,loan_contractForm b "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and a.NDRAWNOTICEID <= 0 "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanPayNoticeStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanPayNoticeStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryLoanPayNoticeApplyCount==========");
			//log.info(strSQL);
			log.info("=========QueryLoanPayNoticeApplyCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询贷款逾期处理状态
	 * 操作数据库表<loan_overdueform>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待复核;2--撰写;3--提交 ;4--已复核 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID          币种
	 *
	 * @return    long                           业务名称(借用)&
	     查询 贷款逾期处理 总数(借用)
	 **/
	public long QueryOverDueCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.OVERDUE_APPLY;
		ApprovalDelegation appBiz = new ApprovalDelegation();
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table = " from  loan_overdueform a ,loan_contractForm b " + " where a.nContractID=b.ID(+)  " + "   and b.nOfficeID = " + lOfficeID + "   and b.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.OverDueStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.OverDueStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryOverDueApplyCount==========");
			//log.info(strSQL);
			log.info("=========QueryOverDueApplyCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询合同执行计划状态
	 * 操作数据库表<loan_PlanModifyForm>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryContractPlanCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.CONTRACT_PLAN;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table = " from  loan_PlanModifyForm a ,loan_contractForm b " + " where a.nContractID=b.ID(+)  " + "   and b.nOfficeID = " + lOfficeID + "   and b.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckLevel = 1 ";//不用加，用以前的方法
				strSQL_Option += " and a.nStatusID= " + LOANConstant.PlanModifyStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.PlanModifyStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryContractPlanCount==========");
			//log.info(strSQL);
			log.info("=========QueryContractPlanCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询利率调整状态
	 * 操作数据库表<DiscountCredence>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(2--待审核;0--撰写;1--提交 ; )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param   long       lCurrencyID          币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryRateAdjustCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.INTEREST_ADJUST;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_rateadjustpaycondition a "
					+ "        ,loan_contractForm b "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.InterestRateSettingStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.InterestRateSettingStatus.SUBMIT;

			} //*/
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryRateAdjustCount==========");
			//log.info(strSQL);
			log.info("=========QueryRateAdjustCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询提前还款状态
	 * 操作数据库表<DiscountCredence>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(2--待审核;0--撰写;1--提交 ;)
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryAheadRePayCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.AHEADREPAY_NOTICE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_aheadrepayform a " + "        ,loan_contractForm b " + " where a.nContractID=b.ID  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.AheadRepayStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.AheadRepayStatus.SUBMIT;

			} //*/
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			log.info("=========QueryAheadRePayCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询风险状态变更状态
	 * 操作数据库表<loan_risklevel>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(2--待审核;0--撰写;1--提交 ; )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryRiskStatusAdjustCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.CONTRACT_RISKLEVEL;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_risklevel a " + "        ,loan_contractForm b " + " where a.nContractID=b.ID(+)  " + "   and b.nOfficeID = " + lOfficeID + "   and b.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.InterestRateSettingStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.InterestRateSettingStatus.SUBMIT;

			} //*/
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryRiskStatusAdjustCount==========");
			//log.info(strSQL);
			log.info("=========QueryRiskStatusAdjustCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询合同状态变更状态
	 * 操作数据库表<DiscountCredence>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryContractStatusAdjustCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.CONTRACT_STATUS;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_contractstatus a "
					+ "        ,loan_contractForm b "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.RiskModifyStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.RiskModifyStatus.SUBMIT;

			} //*/
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryContractStatusAdjustCount==========");
			//log.info(strSQL);
			log.info("=========QueryContractStatusAdjustCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询银团放款通知单状态
	 * 操作数据库表<loan_payform>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(2--待审核  1--提交)
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryYTPayCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOANPAY_NOTICE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_payform a ,loan_contractForm b "
					+ "       ,loan_YT_drawform c  "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and c.ID = a.nDrawNoticeID "
					+ "   and a.nDrawNoticeID > 0 "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanPayNoticeStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanPayNoticeStatus.SUBMIT;

			}
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			log.info("=========QueryYTPayCount==========");
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询银团提款通知单状态
	 * 操作数据库表<loan_YT_drawform>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(2--待审核  1--提交)
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryDrawNoticeCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.YTDQ;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOANDRAW_NOTICE;

		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);


		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table = " from  loan_YT_drawform a ,loan_contractForm b " + " where a.nContractID=b.ID(+)  " + "   and b.nOfficeID = " + lOfficeID + "   and b.nCurrencyID = " + lCurrencyID + "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanDrawNoticeStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanDrawNoticeStatus.SUBMIT;

			}
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			log.info("=========QueryDrawNoticeCount==========\n" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/************************************************************************************************************/
	/**
	 * 根据UserID查询合同状态
	 * 操作数据库表<ContractInfo>
	 * @param     long       lUserID              用户ID
	 * @param     long       lStatusID            查询状态ID(2--待复核;0--撰写;1--提交;)
	 *  @param    long       lOfficeID            办事处标示(未用）
	 * @param     long       lCurrencyID          币种
	 *
	 * @return    long[]     lCount               查询合同总数
	 **/

	public long[] QueryContractCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		//long lLoanTypeID = -1;
		long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		int len = lLoanType.length;
		long[] lCount = new long[len + 1];
		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_CONTRACT;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();

		try
		{
			con = Database.getConnection();

			String sLoanType = "";
			long[] lTmp = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
			for (int i = 0; i < lTmp.length; i++)
			{
				if (i == 0)
				{
					sLoanType += "" + lTmp[i];
				}
				else
				{
					if (lTmp[i] == LOANConstant.LoanType.TX || lTmp[i] == LOANConstant.LoanType.ZTX)
					{
						sLoanType += "";
					}
					else
					{
						sLoanType += "," + lTmp[i];
					}
				}
			}
			strSQL_Count = " select  count(a.ID) ";
			strSQL_Table =
				" from loan_contractForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID in (" + sLoanType + " ) " + " ";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SUBMIT;
			} /*
						            if (lStatusID == 2) //2--待审核
						            {
						                strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
						                strSQL_Option += " and a.nStatusID= "
						                              + LOANConstant.ContractStatus.SUBMIT;
						
						            }
						            strSQL_Order = " group by a.nTypeID ";
						            strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
						            log.info("=========QueryContractCount==========");
						            log.info(strSQL);
						            log.info("=========QueryContractCount==========");
						            ps = con.prepareStatement(strSQL);
						            rs=ps.executeQuery();
						            while (rs != null && rs.next())
						            {
						                lCount[(int)rs.getLong(1)] = rs.getLong(2);
						                lCount[0] += lCount[(int)rs.getLong(1)];
						            }//*/
			/********2003-12-03 考虑虚拟用户*********************/
			for (int nType = (int) LOANConstant.LoanType.ZY; nType <= len; nType++)
			{
				if (lStatusID == 2) //2--待审核
				{
					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）

					strUserID = appBiz.findTheVeryUser(lModuleID, lLoanType[nType - 1], lActionID,lOfficeID,lCurrencyID, lUserID);

					strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
					strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;

				}
				strSQL_Order = " and a.nTypeID = " + lLoanType[nType - 1];
				strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
				log.info("=========QueryContractCount==========" + nType);
				//log.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lCount[nType] = rs.getLong(1);
					lCount[0] += lCount[nType];
					log.info(strSQL + " lCount[" + nType + "]:" + lCount[nType]);
				}
				cleanup(rs);
				cleanup(ps);
				log.info("=========QueryContractCount==========" + nType);
			}
			//log.info("QueryContractCount all:"+lCount[0]);
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		//return lResult;
		return lCount;
	}

	/**
	 * 根据UserID查询合同待激活状态
	 * 操作数据库表<ContractInfo>
	 * @param     long       lUserID             用户ID
	 * @param     long       lStatusID           查询状态ID(3--待激活 )
	 * @param     long       lOfficeID           办事处标示
	 * @param     long       lCurrencyID         币种
	 *
	 * @return    long       lCount              查询合同状态总数
	 **/
	public long QueryContractApplyForActiveCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件

		try
		{
			con = Database.getConnection();

			String sLoanType = "";
			long[] lTmp = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
			for (int i = 0; i < lTmp.length; i++)
			{
				if (i == 0)
				{
					sLoanType += "" + lTmp[i];
				}
				else
				{
					sLoanType += "," + lTmp[i];
				}
			}
			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from loan_contractForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID in (" + sLoanType + " ) " + "";
			/*/------------------------//
			if (lStatusID == 0) //0--撰写
			{
			    strSQL_Option = " and a.nInputUserID= " + lUserID;
			    strSQL_Option += " and a.nStatusID= "
			                  + LOANConstant.ContractStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
			    strSQL_Option = " and a.nInputUserID= " + lUserID;
			    strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
			    strSQL_Option += " and a.nStatusID= "
			                  + LOANConstant.ContractStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
			    strSQL_Option = " and a.nNextCheckUserID= " + lUserID;
			    strSQL_Option += " and a.nStatusID= "
			                  + LOANConstant.ContractStatus.SUBMIT;
			
			}//*/
			if (lStatusID == 3) //3--待激活
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.CHECK;
			}
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryContractForActiveCount==========");
			//log.info(strSQL);
			log.info("=========QueryContractForActiveCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 查询展期合同
	 * 操作数据库表<ContractInfo>
	 * @param     long       lUserID              用户ID
	 * @param     long       lStatusID            查询状态ID(2--待复核;0--撰写;1--提交 ;)
	 *  @param    long       lOfficeID            办事处标示(未用）
	 * @param     long       lCurrencyID          币种
	 *
	 * @return    long       lCount               查询合同总数
	 **/

	public long[] QueryExtendContractCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		//long lCount = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		//long lLoanTypeID = -1;//Constant.ApprovalLoanType.OTHER;
		long[] lLoanType = LOANConstant.LoanType.getAllCode(lOfficeID,lCurrencyID);
		int len = lLoanType.length;
		long[] lCount = new long[len + 1];

		//操作类型
		long lActionID = Constant.ApprovalAction.EXTEND_CONTRACT;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		//strUserID = appBiz.findTheVeryUser(lModuleID,lLoanTypeID,lActionID,lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_extendcontract a ,loan_contractForm b "
					+ " ,loan_extendform c "
					+ " where c.nContractID=b.ID(+)  "
					+ "   and a.nExtendID=c.id(+) "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ExtendStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ExtendStatus.SUBMIT;
			}
			/********2004-07-21 考虑虚拟用户***** ninh *********/
			/* 展期合同审核查找时候 
			 * 不用 Constant.ApprovalLoanType.OTHER 
			 * 根据每种贷款类型 取其有审核权限的用户
			 * 但是填写审核意见时候 
			 * 还是用 Constant.ApprovalLoanType.OTHER
			 * 是为了兼容已有的审核意见
			 */
			for (int nType = (int) LOANConstant.LoanType.ZY; nType <= len; nType++)
			{
				if (lStatusID == 2) //2--待审核
				{
					//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
					strUserID = appBiz.findTheVeryUser(lModuleID, lLoanType[nType - 1], lActionID,lOfficeID,lCurrencyID, lUserID);

					strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
					strSQL_Option += " and a.nStatusID= " + LOANConstant.ExtendStatus.SUBMIT;

				} //*/
				strSQL_Order = " and b.nTypeID = " + lLoanType[nType - 1];
				//strSQL_Order = " group by a.nTypeID ";
				strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
				//log.info("=========QueryExtendContractCount==========");
				//log.info(strSQL);
				log.info("=========QueryExtendContractCount==========");
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					//lCount = rs.getLong(1);
					lCount[nType] = rs.getLong(1);
					lCount[0] += lCount[nType];
					log.info(strSQL + " \n lCount[" + nType + "]:" + lCount[nType]);
				}
				cleanup(rs);
				cleanup(ps);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询贴现合同状态
	 * 操作数据库表<ContractPayPlanNew>
	 * @param     long       lUserID              用户ID
	 * @param     long       lStatusID            查询状态ID(2--待复核;0--撰写;1--提交 ;)
	 *  @param    long       lOfficeID            办事处标示(未用）
	 * @param     long       lCurrencyID          币种
	 *
	 * @return    Collection                      查询 贴现合同 总数
	 **/
	public long QueryDiscountContractCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.TX;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_CONTRACT;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_contractForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID = " + LOANConstant.LoanType.TX + "";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SUBMIT;

			} //*/
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryDisCountContractCount==========");
			//log.info(strSQL);
			log.info("=========QueryDisCountContractCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询审核贷款转让申请
	 * 操作数据库表<ContractPayPlanNew>
	 * @param     long       lUserID              用户ID
	 * @param     long       lStatusID            查询状态ID(2--待复核;0--撰写;1--提交 ;)
	 *  @param    long       lOfficeID            办事处标示(未用）
	 * @param     long       lCurrencyID          币种
	 *
	 * @return    Collection                      查询 贴现合同 总数
	 **/
	public long QueryAttornmentApplyCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.ATTORNMENT_APPLY;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);
		//*/

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table = " from  SEC_AttornmentApplyForm a "
				//,loan_contractForm b "
		//+ " ,loan_extendform c "
		+" where 1=1 " //a.nContractID=b.ID(+)  "
		//+ "   and a.nExtendID=c.id(+) "
		//+ "   and b.nOfficeID = " + lOfficeID
		//+ "   and b.nCurrencyID = " + lCurrencyID
	+"";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.InputUserID= " + lUserID;
				strSQL_Option += " and a.StatusID= " + LOANConstant.AttornmentStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.InputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.NextCheckLevel = 1 ";
				strSQL_Option += " and a.StatusID= " + LOANConstant.AttornmentStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.NextCheckUserID in " + strUserID;
				strSQL_Option += " and a.StatusID= " + LOANConstant.AttornmentStatus.SUBMIT;

			} //*/
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryAttornmentApplyCount==========");
			//log.info(strSQL);
			log.info("=========QueryAttornmentApplyCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " \n lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询指定贷款类型申请书状态（转贴现时新增该方法）
	 * 操作数据库表<loan_loanForm>
	 * @param   long       	lUserID             用户ID
	 * @param   long		lStatusID            查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param   long       	lOfficeID            办事处标示(未用）
	 * @param   long       	lCurrencyID          币种(未用）
	 * @param	long 		lLoanTypeID			贷款类型
	 * @param	long 		lActionID			审批操作类型
	 * @return    long       lCount              查询申请书状态总数
	 **/
	public long QueryLoanApplyCountByLoanType(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID, long lCheckLoanTypeID, long lLoanTypeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_APPLY;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lCheckLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select  count(a.ID) ";
			strSQL_Table = " from loan_loanForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID = " + lLoanTypeID + "";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.LoanStatus.SUBMIT;

			}
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryDiscountApplyCount==========");
			//log.info(strSQL);
			log.info("=========QueryDiscountApplyCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询凭证状态
	 * 操作数据库表<loan_discountcredence>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 * @param	long 		lCredenceTypeID		凭证类型
	 * @param	long 		lLoanTypeID			贷款类型
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long QueryCredenceCountByCredenceType(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID, long lLoanTypeID, long lCredenceTypeID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//操作类型
		long lActionID = Constant.ApprovalAction.DISCOUNT_CREDENCE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  loan_discountcredence a ,loan_contractForm b "
					+ " where a.nContractID=b.ID(+)  "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ " and a.NTYPEID = "
					+ lCredenceTypeID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.DiscountCredenceStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.DiscountCredenceStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryDiscountCredenceCount==========");
			//log.info(strSQL);
			log.info("=========QueryDiscountCredenceCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}

	/**
	 * 根据UserID查询贴现合同状态
	 * 操作数据库表<ContractPayPlanNew>
	 * @param     long       lUserID              用户ID
	 * @param     long       lStatusID            查询状态ID(2--待复核;0--撰写;1--提交 ;)
	 *  @param    long       lOfficeID            办事处标示(未用）
	 * @param     long       lCurrencyID          币种
	 *
	 * @return    Collection                      查询 贴现合同 总数
	 **/
	public long QueryContractCountByLoanType(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID, long lCheckLoanTypeID, long lLoanTypeID) throws Exception
	{
		long lCount = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//操作类型
		long lActionID = Constant.ApprovalAction.LOAN_CONTRACT;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lCheckLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table = " from  loan_contractForm a  " + " where 1=1  " + "   and a.nOfficeID = " + lOfficeID + "   and a.nCurrencyID = " + lCurrencyID + "   and a.nTypeID = " + lLoanTypeID + "";
			if (lStatusID == 0) //0--撰写
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SAVE;
			}
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.nInputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.nNextCheckLevel = 1 ";
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.nNextCheckUserID in " + strUserID;
				strSQL_Option += " and a.nStatusID= " + LOANConstant.ContractStatus.SUBMIT;

			} //*/
			//strSQL_Order = " group by a.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryDisCountContractCount==========");
			//log.info(strSQL);
			log.info("=========QueryDisCountContractCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}
	
	/*实收实付金额*/
	public double getTheRealAmountByContractID(long lContractID) throws Exception
	{
		double dAmount = 0.0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = "select sum(subaccount.mopenamount) sumbalance from LOAN_DISCOUNTCREDENCE credence, sett_subaccount subaccount"
					+" where subaccount.al_nloannoteid = credence.id and credence.ncontractid = " + lContractID;
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs != null && rs.next())
			{
				dAmount = rs.getDouble(1);
			}
						
			if( rs != null ) 
			{																
				rs.close();
				rs = null;
			}
			if( ps != null ) 
			{
				ps.close();
				ps = null;
			}
			if( con != null )
			{
				con.close();
				con = null;
			}
		}
		catch(Exception e)
		{
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally 
		{
			try 
			{
				if( rs != null ) {
					rs.close();
					rs = null;
				}
				if( ps != null ) {
					ps.close();
					ps = null;
				}
				if( con != null ){
					con.close();
					con = null;
				}
			}
			catch(Exception e) 
			{
				throw new RemoteException("remote exception : " + e.toString());
			}
		}		
		return dAmount;
	}
	

	public static void main(String args[])
	{
		QueryDao dao = new QueryDao();
		long lReturn = -1;
		try
		{
			lReturn = dao.queryAssureManagementNoticeCount(133, 1, 1, 1);
			if (lReturn > 0)
				Log.print("--------lReturn.length = " + lReturn);
			else
				Log.print("null");

		}
		catch (Exception e)
		{
			Log.print("erro");
		}
	}

	/**
	 * 根据UserID查询担保收款通知单
	 * 操作数据库表<LOAN_ASSURECHARGEFORM>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long queryAssureChargeNoticeCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.ASSURE_CHARGE_NOTICE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  LOAN_ASSURECHARGEFORM a ,loan_contractForm b "
					+ " where a.ContractID=b.ID(+)  "
					//+ "   and a.NDRAWNOTICEID <= 0 "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.InputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.NextCheckLevel = 1 ";
				strSQL_Option += " and a.StatusID= " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.NextCheckUserID in " + strUserID;
				strSQL_Option += " and a.StatusID= " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryLoanPayNoticeApplyCount==========");
			//log.info(strSQL);
			log.info("=========queryAssureChargeNoticeCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}
	/**
	 * 根据UserID查询保后处理通知单
	 * 操作数据库表<Loan_AssureManagementForm>
	 * @param    long       lUserID             用户ID
	 * @param    long       lStatusID           查询状态ID(1--待审核;2--撰写;3--提交 ;4--已审核;5--已拒绝;6--已取消 )
	 * @param    long       lOfficeID           办事处标示(未用）
	 * @param    long       lCurrencyID         币种(未用）
	 *
	 * @return   long       lCount              查询申请书状态总数
	 **/
	public long queryAssureManagementNoticeCount(long lUserID, long lStatusID, long lOfficeID, long lCurrencyID) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		String strSQL = ""; //主SQL语句
		String strSQL_Count = ""; //SQL计算语句
		String strSQL_Select = ""; //SQL查找语句
		String strSQL_Table = ""; //SQL的表以及其之间联系语句
		String strSQL_Option = ""; //SQL查找条件
		String strSQL_Order = ""; //SQL排序条件
		//贷款模块
		long lModuleID = Constant.ModuleType.LOAN;
		//业务类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER;
		//操作类型
		long lActionID = Constant.ApprovalAction.ASSURE_CHARGE_NOTICE;
		//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
		String strUserID = "";
		ApprovalDelegation appBiz = new ApprovalDelegation();
		strUserID = appBiz.findTheVeryUser(lModuleID, lLoanTypeID, lActionID,lOfficeID,lCurrencyID, lUserID);

		long lCount = 0;

		try
		{
			con = Database.getConnection();

			strSQL_Count = " select count(a.ID) ";
			strSQL_Table =
				" from  Loan_AssureManagementForm a ,loan_contractForm b "
					+ " where a.ContractID=b.ID(+)  "
					//+ "   and a.NDRAWNOTICEID <= 0 "
					+ "   and b.nOfficeID = "
					+ lOfficeID
					+ "   and b.nCurrencyID = "
					+ lCurrencyID
					+ "";
			if (lStatusID == 1) //1--已提交
			{
				strSQL_Option = " and a.InputUserID= " + lUserID;
				//strSQL_Option += " and a.nNextCheckUserID= " + lUserID;
				strSQL_Option += " and a.NextCheckLevel = 1 ";
				strSQL_Option += " and a.StatusID= " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;
			}
			if (lStatusID == 2) //2--待审核
			{
				strSQL_Option = " and a.NextCheckUserID in " + strUserID;
				strSQL_Option += " and a.StatusID= " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;

			}
			//strSQL_Order = " group by b.nTypeID ";
			strSQL = strSQL_Count + strSQL_Table + strSQL_Option + strSQL_Order;
			//log.info("=========QueryLoanPayNoticeApplyCount==========");
			//log.info(strSQL);
			log.info("=========queryAssureManagementNoticeCount==========");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lCount = rs.getLong(1);
				log.info(strSQL + " lCount:" + lCount);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception e)
		{
			log.error(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception ie)
			{
				log.error(ie.toString());
				throw new IException("Gen_E001");
			}
		}
		return lCount;
	}
	
	
	/**
	 * 查询一个贴现申请下的贴现票据并计息，操作DiscountBill表
	 * @param lContractID 贴现合同标识
	 * @return 返回一个贴现合同的全部贴现票据的利息
	 */
	public double findBillInterestByID(long lContractID) throws RemoteException,IException
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;

		long lRecordCount = -1;

		double dDiscountRate = 0; //利率
		Timestamp tsDiscountDate = null; //计息日
		double dExamineAmount = 0; //批准金额
		double dDiscountAccrual = 0; //利息
		double dCheckAmount = 0; //实付金额

		Timestamp tsEnd = null; //贴现日期
		String strEnd = ""; //贴现日期
		int nDays = 0; //实际贴现天数

		double dAmount = 0; //票据金额
		double dAccrual = 0; //贴现利息
		double dRealAmount = 0; //实付贴现金额
		double dTotalAmount = 0; //总票据金额
		double dTotalAccrual = 0; //总票据利息
		double dTotalRealAmount = 0; //总票据实付金额

		try 
		{
			con = Database.getConnection();

			Log.print("======进入贴现计息(findBillInterestByID)方法======");

			Log.print("合同标示：" + lContractID);

			if (lContractID > 0) 
			{
				strSQL = " select a.* from Loan_ContractForm a where a.ID = ? ";

				ps = con.prepareStatement(strSQL);
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				if (rs.next()) 
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); //批准金额
					dRealAmount = rs.getDouble("mCheckAmount"); //核定金额
					dAccrual = dExamineAmount - dRealAmount; //贴现利息
					dDiscountRate = rs.getDouble("mDiscountRate"); //贴现利率
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //贴现计息日
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;

				//strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lContractID;

			}

			Log.print("======开始查询票据总数和总金额======");

			/**
			 * 修改 by kenny(胡志强) (2007-03-21) 处理买方付息利息在贷款合同查询中不对的问题
			//计算记录总数
			strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
			strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lContractID;
			//strSQL = " from DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountApplyID=" + lDiscountApplyID;

			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();

			if (rs != null && rs.next()) 
			{
				lRecordCount = rs.getLong(1);
				//dTotalAmount = rs.getDouble(2);
				//dTotalRealAmount = rs.getDouble(3);
				//dTotalAccrual = dTotalAmount - dTotalRealAmount;
			}

			Log.print("==============");
			Log.print("票据总张数=" + lRecordCount);
			Log.print("票据总金额=" + dTotalAmount);
			Log.print("票据总利息=" + dTotalAccrual);
			Log.print("总实付金额=" + dTotalRealAmount);
			Log.print("==============");

			rs.close();
			rs = null;
			ps.close();
			ps = null;

			Log.print("======结束查询票据总数和总金额======");
			*/
			strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + lContractID;
			strSQL = " select * " + strSQL;
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();

			while (rs != null && rs.next()) 
			{
				dAmount = rs.getDouble("mAmount");
				tsEnd = rs.getTimestamp("dtEnd");
				nDays = 0;
				if (tsEnd != null && tsDiscountDate != null) 
				{
					strEnd = tsEnd.toString();
					tsEnd = new java.sql.Timestamp(new Integer(strEnd.substring(0, 4)).intValue() - 1900, new Integer(strEnd.substring(5, 7)).intValue() - 1, new Integer(strEnd.substring(8, 10)).intValue(), 0, 0, 0, 0);
					nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //加上节假日增加计息天数
				}
				if (nDays >= 0) 
				{
					if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
					{
						nDays = nDays + 3;
					}
					dAccrual = dAmount * (dDiscountRate / 360) * 0.01 * nDays;
					dAccrual = DataFormat.formatDouble(dAccrual, 2);
					dRealAmount = dAmount - dAccrual;
				}

				Log.print("========================");
				Log.print("贴现天数=" + nDays);
				Log.print("汇票金额=" + dAmount);
				Log.print("汇票利息=" + dAccrual);
				Log.print("实付金额=" + dRealAmount);
				Log.print("========================");
				dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2) + DataFormat.formatDouble(dAccrual, 2);
				dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2) + DataFormat.formatDouble(dRealAmount, 2);

			}

			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;

		}
		catch (Exception e) 
		{
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally 
		{
			try 
			{
				if (rs != null) 
				{
					rs.close();
					rs = null;
				}
				if (ps != null) 
				{
					ps.close();
					ps = null;
				}
				if (con != null) 
				{
					con.close();
					con = null;
				}
			}
			catch (Exception ex) 
			{
				log.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}

		Log.print("======退出贴现计息(findBillInterestByID)方法======");

		return dTotalAccrual;

	}
	
	/**
         * liuxz add  融资租赁放款
         * @param qInfo LeaseholdPayNoticeInfo
         * @return PageLoader
         * @throws Exception
         *
         */
        public PageLoader queryLeaseholdPayNotice(LeaseholdPayNoticeInfo qInfo) throws
                Exception {
            String[] sql = new String[4];
            //select
            sql[0] = " l.ID,l.currencyId as currencyId,l.officeId as officeId,l.executeDate as executeDate,"
                    + "c.sContractCode as ContractCode,c.nBorrowClientId as clientID,c.nInterValNum as intervalNum,"
                    +" c2.sName as clientName,ui.sName as inputUserName,l.inputDate as inputDate,"
                    + " l.RecognizanceAmount as recognizanceAmount,"
                    +"  c.dtStartDate as startDate,c.dtEndDate as endDate,l.Contractid as contractID,"
                    + " l.code as code,l.AssureChargeAmount as assureChargeAmount,l.InputUserID as inputUserID,"
                    + " l.NextCheckLevel as nextCheckLevel,l.StatusID as statusID,l.IsLowLevel as isLowLevel,un.sName as nextCheckUserName ";

            //from
            sql[1] = " LOAN_ASSURECHARGEFORM l,Client c2,loan_contractForm c,userInfo ui,userInfo un";

            //where
            sql[2] = " c.ID(+)=l.ContractID and c2.id(+)=c.nBorrowClientID and un.id(+)=l.NEXTCHECKUSERID and ui.id(+)=l.inputUserId "
                     + " and c.ID="
                     + qInfo.getContractId();

            sql[3] = " order by l.ID ";

            PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
                                    getBaseObject("com.iss.system.dao.PageLoader");
            pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2],
                                      (int) Constant.PageControl.CODE_PAGELINECOUNT,
                    "com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo", null);
            pageLoader.setOrderBy(" " + sql[3] + " ");
            return pageLoader;
        }

    	/**
         * quanshao add  融资租赁保证金保后处理查询
         * @param qInfo LeaseholdPayNoticeInfo
         * @return PageLoader
         * @throws Exception
         *
         */
        public PageLoader queryRecognizanceNotice(RecognizanceNoticeInfo rInfo) throws
                Exception {
            String[] sql = new String[4];
            //select
            sql[0] = " * ";

            //from
            sql[1] = " Loan_Recognizancenoticeform ";

            //where
            sql[2] = " CONTRACTID= "
                     + rInfo.getContractID();

            sql[3] = " order by ID ";

            PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
                                    getBaseObject("com.iss.system.dao.PageLoader");
            pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2],
                                      (int) Constant.PageControl.CODE_PAGELINECOUNT,
                    "com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo", null);
            pageLoader.setOrderBy(" " + sql[3] + " ");
            return pageLoader;
        }
        
        public String getLeaseholdPayNoticeOrder(long lOrder, long lDesc) {
            //order
            String strDesc = lDesc == 1 ? " desc " : "";
            StringBuffer oBuf = new StringBuffer();
            switch ((int) lOrder) {
                case 1: //通知单编号
                    oBuf.append(" \n order by l.Code" + strDesc);
                    break;
                case 2: //合同编号
                    oBuf.append(" \n order by c.sContractCode" + strDesc);
                    break;
                case 3: //承租单位
                    oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
                    break;
                case 4: //保证金金额
                    oBuf.append(" \n order by l.recognizanceAmount" + strDesc);
                    break;
                case 5: //手续费金额
                    oBuf.append(" \n order by l.assureChargeAmount" + strDesc);
                    break;
                case 6: //开始日期
                    oBuf.append(" \n order by l.StartDate" + strDesc);
                    break;
                case 7: //结束日期
                    oBuf.append(" \n order by l.EndDate" + strDesc);
                    break;
                case 8: //收款日期
                    oBuf.append(" \n order by l.executeDate" + strDesc);
                    break;
                case 9: //通知单状态
                    oBuf.append(" \n order by l.StatusID" + strDesc);
                    break;
                case 10: //下一个审核人
                    oBuf.append(" \n order by l.NEXTCHECKUSERID" + strDesc);
                    break;
                case 11: //期限
                    oBuf.append(" \n order by c.nInterValNum" + strDesc);
                    break;
                default:
                    oBuf.append(" \n order by l.ID" + strDesc);
                    break;
            }
            return (oBuf.toString());
        }

        /**
         * 融资租赁还款通知单查询
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryLeaseholdRepayNotice(LeaseholdRepayNoticeInfo qInfo) throws
                Exception {
            String[] sql = new String[4];

            //select
            sql[0] =
                    " l.ID,l.currencyId as currencyId,l.amount,l.interestamount,l.officeId as officeId,l.inputdate as executeDate,"
                    + "c.sContractCode as ContractCode,c.nBorrowClientId as clientID,c.nInterValNum as intervalNum,"
                    +" c2.sName as clientName,ui.sName as inputUserName,l.inputDate as inputDate,"
                    + " l.RecognizanceAmount as recognizanceAmount,"
                    +" c.dtStartDate as queryStartDate,c.dtEndDate as queryEndDate,l.Contractid as contractID,"
//                    + " l.code as code,l.AssureChargeAmount as assureChargeAmount,l.InputUserID as inputUserID,l.recognizanceamount,"
                    + " l.code as code,l.InputUserID as inputUserID,"
                    + " l.NextCheckLevel as nextCheckLevel,l.nStatusID,l.IsLowLevel as isLowLevel,un.sName as nextCheckUserName ";
            //from
            sql[1] =" LOAN_LEASEHOLDREPAYFORM l,Client c2,loan_contractForm c,userInfo ui,userInfo un";
            //where
            sql[2] = " c.ID(+)=l.ContractID and c2.id(+)=c.nBorrowClientID and un.id(+)=l.NEXTCHECKUSERID and ui.id(+)=l.inputUserId "
                     + " and c.ID="
                     + qInfo.getContractId();
            sql[3] = " order by l.ID ";
            PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
                                    getBaseObject("com.iss.system.dao.PageLoader");

            log.info(" 融资租赁收款通知单查询: select " + sql[0] + " from " + sql[1] +
                     " where " + sql[2] + sql[3]);
            pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2],
                                      (int) Constant.PageControl.CODE_PAGELINECOUNT,
                                      "com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo", null);
            pageLoader.setOrderBy(" " + sql[3] + " ");
            return pageLoader;
        }

        /**
         * 融资租赁还款通知单查询排序
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLeaseholdRepayNoticeOrder(long lOrder, long desc) {
            //order
            String strDesc = desc == 1 ? " desc " : "";
            StringBuffer oBuf = new StringBuffer();
            switch ((int) lOrder) {
                case 1: //通知单编号
                    oBuf.append(" \n order by l.Code" + strDesc);
                    break;
                case 2: //合同编号
                    oBuf.append(" \n order by c.sContractCode" + strDesc);
                    break;
                case 3: //承租单位
                    oBuf.append(" \n order by c.nBorrowClientID" + strDesc);
                    break;
                case 4: //本金金额
                    oBuf.append(" \n order by l.amount" + strDesc);
                    break;
                case 5: //保证金金
                   oBuf.append(" \n order by l.InterestAmount" + strDesc);
                    break;
                case 6: //利息金额
                   oBuf.append(" \n order by l.InterestAmount" + strDesc);
                    break;
                case 7: //期限
                    oBuf.append(" \n order by c.nInterValNum" + strDesc);
                    break;
                case 8: //开始日期
                    oBuf.append(" \n order by c.dtStartDate" + strDesc);
                    break;
                case 9: //结束日期
                    oBuf.append(" \n order by c.dtEndDate" + strDesc);
                    break;
                case 10: //通知单状态
                    oBuf.append(" \n order by l.nStatusID" + strDesc);
                    break;
                case 11: //下一个审核人
                    oBuf.append(" \n order by l.NEXTCHECKUSERID" + strDesc);
                    break;
                default:
                    oBuf.append(" \n order by l.ID" + strDesc);
                    break;
            }
            return (oBuf.toString());
        }
        
       
        /**
         * 查询还款通知单
         * @param repayInfo
         * @return
         * @throws Exception
         */
    	public PageLoader queryRepayNotice(QuestRepayNoticeInfo repayInfo) throws Exception
    	{
    		
    			String[] sql1 = getRepayNoticeSQl1(repayInfo);
    			String[] sql2 = getRepayNoticeSQl2(repayInfo);
    			String[] sql3 = new String[4];
    			
    			String NLoanType = repayInfo.getNLoanType();
    			
    			boolean flag = isLoan_WT(NLoanType, repayInfo.getNOfficeID(), repayInfo.getNCurrencyID());
    			
    			
    			//贷款类型不包含委托贷款且不选委托单位 走getPayNoticeSQL1(payNInfo)
    			if(!flag && repayInfo.getConsignIDFrom()<0 && repayInfo.getConsignIDTo()<0){
    				sql3[0]="*";
    				sql3[1] = "(select " + sql1[0] +" from " +sql1[1] +" where " + sql1[2]+")";
    				sql3[2]="1=1";
    				sql3[3]="";
    			}else if(repayInfo.getConsignIDFrom()>0 || repayInfo.getConsignIDTo()>0){
    				sql3[0]="*";
    				sql3[1] = "(select " + sql2[0] +" from " +sql2[1] +" where " + sql2[2]+")";
    				sql3[2]="1=1";
    				sql3[3]="";
    				
    			}else 
    			{ sql3[0]="*";
    			  sql3[1] = "(select " + sql1[0] +" from " +sql1[1] +" where "  + sql1[2] + " union "+"select " + sql2[0] +" from " +sql2[1] +" where "  + sql2[2]+")";
    			  sql3[2]="1=1";
    			  sql3[3]="";
    			  
    			}

    			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

    			pageLoader.initPageLoader(new AppContext(), sql3[1], sql3[0], sql3[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.aheadrepaynotice.dataentity.AheadRepayNoticeInfo", null);
    			pageLoader.setOrderBy(" " + sql3[3] + " ");
    			return pageLoader;
    			
    			
    	}
    	
    	private String[]  getRepayNoticeSQl1 (QuestRepayNoticeInfo repayInfo) {
    		
    		String[] sql = new String[4];
    		StringBuffer buf = new StringBuffer();
    		
    		//查询合同号下还款通知单明细
    		if(repayInfo.getContractID()>0){
    			
    			buf.append(" and l.NCONTRACTID ="+repayInfo.getContractID());
    	
    	    }
    		
    		
    		
    		//合同编号
    		if(repayInfo.getContractCodeFrom()!=null && repayInfo.getContractCodeFrom().length()>0 ){
    			
    				buf.append(" and c.sContractCode>='"+repayInfo.getContractCodeFrom()+"'");
    		
    		}
    		
    		if(repayInfo.getContractCodeTo()!=null && repayInfo.getContractCodeTo().length()>0 ){
    			
    			buf.append(" and c.sContractCode<='"+repayInfo.getContractCodeTo()+"'");
    	
    	    }
    		
    		//借款单位
    		if(repayInfo.getLoanClientIDFrom()>0){
    			
    			buf.append(" and c.nBorrowClientID>="+repayInfo.getLoanClientIDFrom());
    	
    	    }
            if(repayInfo.getLoanClientIDTo()>0){
    			
    			buf.append(" and c.nBorrowClientID<="+repayInfo.getLoanClientIDTo());
    	
    	    }
            
            
            //还款金额
            if(repayInfo.getMRePayAmountFrom()>0){
    			
    			buf.append(" and l.mAmount>="+DataFormat.formatAmount(repayInfo.getMRePayAmountFrom()));
    	
    	    }
            if(repayInfo.getMRePayAmountTo()>0){
    			
    			buf.append(" and l.mAmount<="+DataFormat.formatAmount(repayInfo.getMRePayAmountTo()));
    	
    	    }
            
            //归还利息
            if(repayInfo.getDrawAmountInterestFrom()>0){
    			
    			buf.append(" and l.INTERESTAMOUNT>="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestFrom()));
    	
    	    }
            if(repayInfo.getDrawAmountInterestTo()>0){
    			
    			buf.append(" and l.INTERESTAMOUNT<="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestTo()));
    	
    	    }
            

    		//还款日期开始
    		if (repayInfo.getDtRepayDateFrom() != null){
    			buf.append(" and TRUNC(l.PAYDATE)>= To_Date('" + repayInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
    		}
    		
    		if (repayInfo.getDtRepayDateTo() != null){
    			buf.append(" and TRUNC(l.PAYDATE)<= To_Date('" + repayInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
    		}
    		
    		//录入日期开始
    		if (repayInfo.getDtInputDateFrom() != null){
    			buf.append(" and TRUNC(l.DTINPUTDATE)>= To_Date('" + repayInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
    		}
    		
    		if (repayInfo.getDtInputDateTo() != null){
    			buf.append(" and TRUNC(l.DTINPUTDATE)<= To_Date('" + repayInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
    		}
    		
            
    		//贷款类型
    	       if(repayInfo.getNLoanType()==null || repayInfo.getNLoanType().trim().equals("-1") ){
    	        buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
    	        		"  a.loantypeid  in (1,5) and a.id=b.subloantypeid " +
    	        		"and b.currencyid="+repayInfo.getNCurrencyID()+" and b.officeid="+repayInfo.getNOfficeID()+")");
    	       } 
    	  		
    	       else{
    	  			
    	  			buf.append(" and c.nSubTypeID in (" + repayInfo.getNLoanType() + ") and c.nSubTypeID not in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and "+
    	  					"  a.loantypeid=2 and a.id=b.subloantypeid " +
        	        		"and b.currencyid="+repayInfo.getNCurrencyID()+" and b.officeid="+repayInfo.getNOfficeID()+")");
    	  			
    	  		}
      		//还款通知单状态
      		
      	    long loanRePayNoticeStatus[]=LOANConstant.AheadRepayStatus.getAllCode(repayInfo.getNOfficeID(),repayInfo.getNCurrencyID());
      		String loanTypeList="";
      		for ( int i=0;i<loanRePayNoticeStatus.length;i++ )
      		{
      			
      			if(loanRePayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
      				continue;
      			}
      			loanTypeList+=loanRePayNoticeStatus[i];
      			if((loanRePayNoticeStatus.length-i)>1){
      				
      				loanTypeList+=",";
      			}
      			
      		}
      		
        	if(repayInfo.getNRePayNoticeStatus()==null ||repayInfo.getNRePayNoticeStatus().trim().equals("-1")){
        		
      		   buf.append(" and l.nStatusID in (" + loanTypeList + ")");
      		}
      		else{
      			
      			buf.append(" and l.nStatusID in (" + repayInfo.getNRePayNoticeStatus() + ")");
      			
      		}
      		//是否提前还款
      		if(repayInfo.getIsHead()>0){
      			
      			buf.append("and l.NISAHEAD="+String.valueOf(repayInfo.getIsHead()));
      			
      		}
      		
    		//select
    		sql[0] =
    			" l.ID,l.sCode as Code,c.sContractCode as ContractCode,lp.SCODE as LetoutNoticeCode,"
    				+ " d.name  ClientName ,'' as ConsignClientName,lp.MAMOUNT as LetoutNoticeAmount,u.sName as InputUserName,lt.name as loanTypeName,"
    				+ " l.mAmount as amount,l.PAYDATE as PBackDate,l.nIsAhead as IsAhead,"
    				+ " l.INTERESTAMOUNT as balanceAmount,l.nContractid as contractID,"
    				+ " c.nTypeID as loanType,"
    				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

    		//from
    		sql[1] = " loan_aheadrepayform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_Payform lp,loan_loantypesetting lt";

    		//where
    		sql[2] = " lt.id = c.nsubtypeid and u.id=l.NINPUTUSERID and c.ID(+)=l.nContractID and d.id(+)=c.nBorrowClientID and lp.id(+)=l.NLOANPAYNOTICEID and l.NOFFICEID="+repayInfo.getNOfficeID()+"and l.NCURRENCYID="+repayInfo.getNCurrencyID()+buf.toString();

    		sql[3] = " order by l.ID ";
    		
    		return sql;
    		
    		
    		
    	}
    	
    	
private String[]  getRepayNoticeSQl2 (QuestRepayNoticeInfo repayInfo) {
    		
    		String[] sql = new String[4];
    		StringBuffer buf = new StringBuffer();
    		
    		//查询合同号下还款通知单明细
    		if(repayInfo.getContractID()>0){
    			
    			buf.append(" and l.NCONTRACTID="+repayInfo.getContractID());
    	
    	    }
    		
    		//合同编号
    		if(repayInfo.getContractCodeFrom()!=null && repayInfo.getContractCodeFrom().length()>0 ){
    			
    				buf.append(" and c.sContractCode>='"+repayInfo.getContractCodeFrom()+"'");
    		
    		}
    		
    		if(repayInfo.getContractCodeTo()!=null && repayInfo.getContractCodeTo().length()>0 ){
    			
    			buf.append(" and c.sContractCode<='"+repayInfo.getContractCodeTo()+"'");
    	
    	    }
    		
    		//借款单位
    		if(repayInfo.getLoanClientIDFrom()>0){
    			
    			buf.append(" and c.nBorrowClientID>="+repayInfo.getLoanClientIDFrom());
    	
    	    }
            if(repayInfo.getLoanClientIDTo()>0){
    			
    			buf.append(" and c.nBorrowClientID<="+repayInfo.getLoanClientIDTo());
    	
    	    }
            
            //委托单位
    		if(repayInfo.getConsignIDFrom()>0){
    			
    			buf.append(" and c.nConsignClientid>="+repayInfo.getConsignIDFrom());
    	
    	    }
            if(repayInfo.getConsignIDTo()>0){
    			
    			buf.append(" and c.nConsignClientid<="+repayInfo.getConsignIDTo());
    	
    	    }
            
            //还款金额
            if(repayInfo.getMRePayAmountFrom()>0){
    			
    			buf.append(" and l.mAmount>="+DataFormat.formatAmount(repayInfo.getMRePayAmountFrom()));
    	
    	    }
            if(repayInfo.getMRePayAmountTo()>0){
    			
    			buf.append(" and l.mAmount<="+DataFormat.formatAmount(repayInfo.getMRePayAmountTo()));
    	
    	    }
            
            //归还利息
            if(repayInfo.getDrawAmountInterestFrom()>0){
    			
    			buf.append(" and l.INTERESTAMOUNT>="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestFrom()));
    	
    	    }
            if(repayInfo.getDrawAmountInterestTo()>0){
    			
    			buf.append(" and l.INTERESTAMOUNT<="+DataFormat.formatAmount(repayInfo.getDrawAmountInterestTo()));
    	
    	    }
            

    		//还款日期开始
    		if (repayInfo.getDtRepayDateFrom() != null){
    			buf.append(" and TRUNC(l.PAYDATE)>= To_Date('" + repayInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
    		}
    		
    		if (repayInfo.getDtRepayDateTo() != null){
    			buf.append(" and TRUNC(l.PAYDATE)<= To_Date('" + repayInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
    		}
    		
    		//录入日期开始
    		if (repayInfo.getDtInputDateFrom() != null){
    			buf.append(" and TRUNC(l.DTINPUTDATE)>= To_Date('" + repayInfo.getDtInputDateFrom() + "','yyyy-mm-dd') ");
    		}
    		
    		if (repayInfo.getDtInputDateTo() != null){
    			buf.append(" and TRUNC(l.DTINPUTDATE)<= To_Date('" + repayInfo.getDtInputDateTo() + "','yyyy-mm-dd') ");
    		}
    		

    		//贷款类型
    	       if(repayInfo.getNLoanType()==null || repayInfo.getNLoanType().trim().equals("-1") ){
    	        buf.append(" and c.nSubTypeID in (select a.id as subTypeID  from loan_loantypesetting a, loan_loantyperelation b where a.statusid=1 and a.loantypeid= b.loantypeid and" +
    	        		"  a.loantypeid  in (2) and a.id=b.subloantypeid " +
    	        		"and b.currencyid="+repayInfo.getNCurrencyID()+" and b.officeid="+repayInfo.getNOfficeID()+")");
    	       } 
    	  		
    	       else{
    	  			
    	  			buf.append(" and c.nSubTypeID in (" + repayInfo.getNLoanType() + ")");
    	  			
    	  		}
      		
      		//还款通知单状态
      		
      	    long loanRePayNoticeStatus[]=LOANConstant.AheadRepayStatus.getAllCode(repayInfo.getNOfficeID(),repayInfo.getNCurrencyID());
      		String loanTypeList="";
      		for ( int i=0;i<loanRePayNoticeStatus.length;i++ )
      		{
      			
      			if(loanRePayNoticeStatus[i]==LOANConstant.LoanPayNoticeStatus.REFUSE){
      				continue;
      			}
      			loanTypeList+=loanRePayNoticeStatus[i];
      			if((loanRePayNoticeStatus.length-i)>1){
      				
      				loanTypeList+=",";
      			}
      			
      		}
      		buf.append(" and l.nStatusID in (" + loanTypeList + ")");
      		
      		if(repayInfo.getNRePayNoticeStatus()!=null && repayInfo.getNRePayNoticeStatus().length()>0){
      			
      			buf.append(" and l.nStatusID in (" + repayInfo.getNRePayNoticeStatus() + ")");
      			
      		}
      		//是否提前还款
      		if(repayInfo.getIsHead()>0){
      			
      			buf.append("and l.NISAHEAD="+String.valueOf(repayInfo.getIsHead()));
      			
      		}
      		
    		//select
    		sql[0] =
    			" l.ID,l.sCode as Code,c.sContractCode as ContractCode,lp.SCODE as LetoutNoticeCode,"
    				+ " d.name as ClientName, e.name as ConsignClientName,lp.MAMOUNT as LetoutNoticeAmount,u.sName as InputUserName,lt.name as loanTypeName,"
    				+ " l.mAmount as amount,l.PAYDATE as PBackDate,l.nIsAhead as IsAhead,"
    				+ " l.INTERESTAMOUNT as balanceAmount,l.nContractid as contractID,"
    				+ " c.nTypeID as loanType,"
    				+ " l.dtInputDate as inputDate,l.nStatusID as statusID";

    		//from
    		sql[1] = " loan_aheadrepayform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_Payform lp,client_clientinfo e,loan_loantypesetting lt";

    		//where
    		sql[2] = " lt.id = c.nsubtypeid and e.id=c.nConsignClientID and u.id(+)=l.NINPUTUSERID and c.ID(+)=l.nContractID and d.id(+)=c.nBorrowClientID and lp.id(+)=l.NLOANPAYNOTICEID and l.NOFFICEID="+repayInfo.getNOfficeID()+"and l.NCURRENCYID="+repayInfo.getNCurrencyID()+buf.toString();

    		sql[3] = " order by l.ID ";
    		
    		return sql;
    		
    		
    		
    	}
    	
    	 /**
         * 查询还款通知单各项总额
         * @param 
         */
      
        public QuerySumInfo queryRepayNoticeSum(QuestRepayNoticeInfo repayInfo)throws Exception{
     	   
        	
        	Connection con = null;
    		ResultSet rs = null;
    		PreparedStatement ps = null;
    		QuerySumInfo sumInfo = new QuerySumInfo();
    		String[] sql = getRepayNoticeSQl1(repayInfo);
			String[] sql1 = getRepayNoticeSQl2(repayInfo);
			String strSQL = "";
			String NLoanType = repayInfo.getNLoanType();
			boolean flag = isLoan_WT(NLoanType, repayInfo.getNOfficeID(), repayInfo.getNCurrencyID());

    		try
    		{  
    			if(!flag && repayInfo.getConsignIDFrom()<0 && repayInfo.getConsignIDTo()<0){
    			strSQL = "select sum(lp.MAMOUNT) as TotalLoanoutAmount,sum(l.mAmount) as TotalRePayNoticeAmount,sum(l.INTERESTAMOUNT) as TotalInterestAmount from " + sql[1] + " where " + sql[2];
    			}
    			else if (repayInfo.getConsignIDFrom()>0 || repayInfo.getConsignIDTo()>0){
    				strSQL = "select sum(lp.MAMOUNT) as TotalLoanoutAmount,sum(l.mAmount) as TotalRePayNoticeAmount,sum(l.INTERESTAMOUNT) as TotalInterestAmount from " + sql1[1] + " where " + sql1[2];
    			}
    			else{
    				
    			   strSQL ="select nvl(sum(a.TotalLoanoutAmount),0) as TotalLoanoutAmount, nvl(sum(a.TotalRePayNoticeAmount),0) as TotalRePayNoticeAmount,nvl(sum(a.TotalInterestAmount),0) as TotalInterestAmount from (select sum(lp.MAMOUNT) as TotalLoanoutAmount,sum(l.mAmount) as TotalRePayNoticeAmount,sum(l.INTERESTAMOUNT) as TotalInterestAmount from " + sql[1] + " where " + sql[2]+" union "+ "select sum(lp.MAMOUNT) as TotalLoanoutAmount,sum(l.mAmount) as TotalRePayNoticeAmount,sum(l.INTERESTAMOUNT) as TotalInterestAmount from " + sql1[1] + " where " + sql1[2]+") a";
    			}
    			
    			
    			log.print(strSQL);

    			con = Database.getConnection();
    			ps = con.prepareStatement(strSQL);
    			rs = ps.executeQuery();
    			if (rs.next())
    			{
    				sumInfo.setAllRecord(rs.getLong(1));	
    				sumInfo.setTotalPayAmount(rs.getDouble("TotalLoanoutAmount"));//放款总金额									
    				sumInfo.setTotalRepayAmount(rs.getDouble("TotalRePayNoticeAmount"));//还款总金额
    				sumInfo.setTotalInterestAmount(rs.getDouble("TotalInterestAmount"));//归还总利息
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
    		
    	/**
    	 * 获得还款通知单排序
    	 * @param lOrder
    	 * @param lDesc
    	 * @return
    	 */
    		public String getLoanRepayNoticeOrder(long lOrder, long lDesc)
    		{
    			//order
    			String strDesc = lDesc == 1 ? " desc " : "";
    			StringBuffer oBuf = new StringBuffer();
    			switch ((int) lOrder)
    			{
    			case 1 : //按合同编号排序
					oBuf.append( " ORDER BY CONTRACTCODE"+ strDesc);
					break;
				case 2 : //按借款单位排序
					oBuf.append( " ORDER BY CLIENTNAME "+ strDesc);
					break;
				case 3 : //按放款通知单排序
					oBuf.append( " ORDER BY LETOUTNOTICECODE"+ strDesc);
					break;
				case 4 : //按放款金额排序
					oBuf.append( " ORDER BY LETOUTNOTICEAMOUNT"+ strDesc);
					break;
				case 5 : //按提前还款金额排序
					oBuf.append( " ORDER BY AMOUNT"+ strDesc);
					break;
				case 6 : //按录入日期排序
					oBuf.append( " ORDER BY INPUTDATE"+ strDesc);
					break;
				case 7 : //按通知单状态排序
					oBuf.append( " ORDER BY STATUSID"+ strDesc);
					break;
				case 8 : //按是否提前还款
					oBuf.append( " ORDER BY ISAHEAD"+ strDesc);
					break;
				case 9 ://按还款通知单编号排序
					oBuf.append( " ORDER BY CODE"+ strDesc);
					break;
				case 10 ://按贷款类型排序
					oBuf.append( " ORDER BY LOANTYPENAME"+ strDesc);
					break;
				case 11 ://按还款日期
					oBuf.append( " ORDER BY PBACKDATE"+ strDesc);
					break;
				case 12 ://按归还利息
					oBuf.append( " ORDER BY BALANCEAMOUNT"+ strDesc);
					break;
				case 13 ://按录入人
					oBuf.append( " ORDER BY INPUTUSERNAME"+ strDesc);
					break;
				case 14: //按委托单位排序
					oBuf.append( " ORDER BY CONSIGNCLIENTNAME "+ strDesc);
					break;
		
				default :
					oBuf.append( " ORDER BY ID"+ strDesc);
    			}
    			return (oBuf.toString());
    		}
        
    	//Modify by leiyang 2007/06/27
        /**
         * 查询还款通知单的非保存状态
         */
    	/*public Collection queryRepayNoticeNotSave(QueryRepayNoticeInfo qInfo) throws Exception
    	{
    		Vector v = new Vector();

    		//分页变量
    		long lRecordCount = -1;
    		long lPageCount = -1;
    		long lRowNumStart = -1;
    		long lRowNumEnd = -1;
    		int lIndex = 1;

    		String strSQL = "";
    		String strSQL_pre = "";
    		String strSQL_con = "";
    		String strSQL_order = "";

    		Connection con = null;
    		PreparedStatement ps = null;
    		ResultSet rs = null;

    		try
    		{
    			//获得真正来审批这个记录的人（真实或传给的虚拟的人！）
    			ApprovalDelegation appBiz = new ApprovalDelegation();
    			long lModuleID = Constant.ModuleType.LOAN; //模块类型
    			long lActionID = Constant.ApprovalAction.AHEADREPAY_NOTICE; //提前还款通知单审核

    			con = Database.getConnection();

    			//计算记录总数
    			strSQL_pre = " SELECT COUNT(*) ";
    			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b,userinfo e";
    			//查询条件
    			strSQL_con = " WHERE b.id = a.nContractID AND a.ninputuserid = e.id";

    			LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
    			long[] loanTypeId = {LOANConstant.LoanType.ZY,LOANConstant.LoanType.WT,
    					LOANConstant.LoanType.ZGXE,LOANConstant.LoanType.MFXD,
    					LOANConstant.LoanType.OTHER
    			}; 
    			String strUser = null;
    			long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
    					qInfo.getOfficeID(),qInfo.getCurrencyID(), loanTypeId );
    			
    			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
    				strSQL_con += " AND ((( ";
    				for (int i = 0; i < a_SubLoanType.length; i++) {
    					strUser  =	 appBiz.findTheVeryUser(lModuleID,
    						a_SubLoanType[i], lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getInputUserID());
    					strSQL_con += " a.nNextCheckUserID IN " + strUser ;
    					if (i < a_SubLoanType.length - 1)
    						strSQL_con += " or ";
    					else
    						strSQL_con += " ) ";
    					}					
    			}else{
    				return null;
    			}
    			
                strSQL_con += "   AND a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
                strSQL_con += "     )";
                strSQL_con += " OR (a.nNextCheckUserID=-2 ";
                strSQL_con += " AND (a.nStatusID =" + LOANConstant.AheadRepayStatus.CHECK ;
                strSQL_con += " OR a.nStatusID =" + LOANConstant.AheadRepayStatus.USED + ")";
                strSQL_con += "     ))";
                *//*
    			strSQL_con += " AND a.nCurrencyID=?";
    			strSQL_con += " AND a.nOfficeID=?";

    			//lClientID借款单位ID
    			if (qInfo.getClientID() > 0)
    			{
    				strSQL_con += " AND b.nBorrowClientID = ?";
    			}

    			//lContractIDTo 合同编号
    			if (qInfo.getContractID() > 0)
    			{
    				strSQL_con += " AND b.ID = ?";
    			}

    			//dAmountFrom金额起
    			if (qInfo.getAmountFrom() > 0)
    			{
    				strSQL_con += " AND a.mAmount >= ?";
    			}

    			//dAmountTo金额止
    			if (qInfo.getAmountTo() > 0)
    			{
    				strSQL_con += " AND a.mAmount <= ?";
    			}

    			//录入日期起
    			if (qInfo.getInputDateFrom() != null)
    			{
    				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
    			}

    			//录入日期止
    			if (qInfo.getInputDateTo() != null)
    			{
    				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
    			}

    			//状态
    			if (qInfo.getStatusID() > 0)
    			{
    				strSQL_con += " AND a.nStatusID = ?";
    			}

    			strSQL = strSQL_pre + strSQL_con;
    			log.info(strSQL);
    			System.out.println(strSQL);
    			ps = con.prepareStatement(strSQL);

    			ps.setLong(lIndex++, qInfo.getCurrencyID());
    			ps.setLong(lIndex++, qInfo.getOfficeID());

    			//lClientID借款单位ID
    			if (qInfo.getClientID() > 0)
    			{
    				ps.setLong(lIndex++, qInfo.getClientID());
    			}

    			// 合同编号
    			if (qInfo.getContractID() > 0)
    			{
    				ps.setLong(lIndex++, qInfo.getContractID());
    			}

    			//dAmountFrom金额起
    			if (qInfo.getAmountFrom() > 0)
    			{
    				ps.setDouble(lIndex++, qInfo.getAmountFrom());
    			}

    			//dAmountTo金额止
    			if (qInfo.getAmountTo() > 0)
    			{
    				ps.setDouble(lIndex++, qInfo.getAmountTo());
    			}

    			//录入日期起
    			if (qInfo.getInputDateFrom() != null)
    			{
    				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
    			}

    			//录入日期止
    			if (qInfo.getInputDateTo() != null)
    			{
    				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
    			}

    			//状态
    			if (qInfo.getStatusID() > 0)
    			{
    				ps.setLong(lIndex++, qInfo.getStatusID());
    			}

    			rs = ps.executeQuery();

    			if (rs != null && rs.next())
    			{
    				lRecordCount = rs.getLong(1);
    			}
    			log.info("lRecordCount:" + lRecordCount);

    			if (rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
    			if (ps != null)
    			{
    				ps.close();
    				ps = null;
    			}

    			//计算总页数
    			if (lRecordCount > qInfo.getPageLineCount())
    			{
    				lPageCount = lRecordCount / qInfo.getPageLineCount();
    				if ((lRecordCount % qInfo.getPageLineCount()) != 0)
    				{
    					lPageCount++;
    				}
    			}
    			else if (lRecordCount > 0 && lRecordCount <= qInfo.getPageLineCount())
    			{
    				lPageCount = 1;
    			}
    			else
    			{
    				lPageCount = 0;
    			}

    			//返回需求的结果集

    			//分页显示，显示下一页
    			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
    			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

    			switch ((int) qInfo.getOrderParam())
    			{
    				case 1 : //按合同编号排序
    					strSQL_order += " ORDER BY b.sContractCode";
    					break;
    				case 2 : //按借款单位排序
    					strSQL_order += " ORDER BY c.sName";
    					break;
    				case 3 : //按放款通知单排序
    					strSQL_order += " ORDER BY d.sCode";
    					break;
    				case 4 : //按放款金额排序
    					strSQL_order += " ORDER BY d.mAmount";
    					break;
    				case 5 : //按提前还款金额排序
    					strSQL_order += " ORDER BY a.mAmount";
    					break;
    				case 6 : //按录入日期排序
    					strSQL_order += " ORDER BY a.dtInputDate";
    					break;
    				case 7 : //按通知单状态排序
    					strSQL_order += " ORDER BY a.nStatusID";
    					break;
    				case 8 : //按是否提前还款
    					strSQL_order += " ORDER BY a.nIsAhead";
    					break;
    				default :
    					strSQL_order += " ORDER BY a.dtInputDate DESC";
    			}

    			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
    			{
    				strSQL_order += " DESC";
    			}

    			//got the sql sentence
    			strSQL_pre = "SELECT * FROM ";
    			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
    			strSQL_pre += " ( SELECT a.*,";
    			strSQL_pre += " b.sContractCode,b.nTypeId,c.sName,";
    			strSQL_pre += " d.sCode as PayCode,d.mAmount as PayAmount,e.sName as inputUserName";
    			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b,";
    			strSQL_pre += " Client c,loan_PayForm d,userinfo e";

    			strSQL_con += " AND b.nBorrowClientID=c.ID(+)";
    			strSQL_con += " AND a.nLoanPayNoticeID=d.ID";
    			strSQL_con += strSQL_order;
    			strSQL_con += ")all_record ";
    			strSQL_con += ") WHERE num BETWEEN ? AND ?";

    			strSQL = strSQL_pre + strSQL_con;
    			log.info(strSQL);
    			ps = con.prepareStatement(strSQL);
    			lIndex = 1;
    			ps.setLong(lIndex++, qInfo.getCurrencyID());
    			ps.setLong(lIndex++, qInfo.getOfficeID());

    			//lClientID借款单位ID
    			if (qInfo.getClientID() > 0)
    			{
    				ps.setLong(lIndex++, qInfo.getClientID());
    			}

    			// 合同编号
    			if (qInfo.getContractID() > 0)
    			{
    				ps.setLong(lIndex++, qInfo.getContractID());
    			}

    			//dAmountFrom金额起
    			if (qInfo.getAmountFrom() > 0)
    			{
    				ps.setDouble(lIndex++, qInfo.getAmountFrom());
    			}

    			//dAmountTo金额止
    			if (qInfo.getAmountTo() > 0)
    			{
    				ps.setDouble(lIndex++, qInfo.getAmountTo());
    			}

    			if (qInfo.getInputDateFrom() != null)
    			{
    				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
    			}

    			if (qInfo.getInputDateTo() != null)
    			{
    				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
    			}

    			//状态
    			if (qInfo.getStatusID() > 0)
    			{
    				ps.setLong(lIndex++, qInfo.getStatusID());
    			}

    			ps.setLong(lIndex++, lRowNumStart); //给入起始行号
    			ps.setLong(lIndex++, lRowNumEnd); //给入结束行号

    			rs = ps.executeQuery();

    			while (rs != null && rs.next())
    			{
    				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
    				info.setID(rs.getLong("id")); //提前还款通知单ID
    				info.setCode(rs.getString("sCode"));//还款通知单编号
    				info.setContractCode(rs.getString("sContractCode")); //合同编号
    				info.setLoanType(rs.getLong("nTypeId"));//贷款类型
    				info.setClientName(rs.getString("sName")); //贷款单位
    				info.setLetoutNoticeCode(rs.getString("PayCode")); //放款通知单编号
    				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //放款金额
    				info.setAmount(rs.getDouble("mAmount")); //提前还款金额
    				info.setInputDate(rs.getTimestamp("dtInputDate")); //录入日期
    				info.setStatus(LOANConstant.AheadRepayStatus.getName(rs.getLong("nStatusID")));
    				info.setInputUserName(rs.getString("inputUserName")); //录入用户名称
    				//通知单状态
    				info.setIsAhead(rs.getLong("nIsAhead"));//是否提前还款
    				info.setPageCount(lPageCount); //记录总的页面数
    				v.addElement(info);
    			}
    			if (rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
    			if (ps != null)
    			{
    				ps.close();
    				ps = null;
    			}
    			if (con != null)
    			{
    				con.close();
    				con = null;
    			}
    		}

    		catch (Exception e)
    		{
    			log.error(e.toString());
    			throw e;
    		}
    		finally
    		{
    			try
    			{
    				if (rs != null)
    				{
    					rs.close();
    					rs = null;
    				}
    				if (ps != null)
    				{
    					ps.close();
    					ps = null;
    				}
    				if (con != null)
    				{
    					con.close();
    					con = null;
    				}
    			}
    			catch (Exception e)
    			{
    				log.error(e.toString());
    				throw e;
    			}
    		}
    		return (v.size() > 0 ? v : null);
    	}*/
    	
        
        
        /**
         * 查询还款通知单
         */
       /* public PageLoader queryRepayNotice(QueryRepayNoticeInfo rInfo){
        	
        	
        	String[] sql = getRepayNoticeSQL(rInfo);
    		//
    		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

    		pageLoader.initPageLoader(new AppContext(), sql[0], sql[1], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.aheadrepaynotice.dataentity.AheadRepayNoticeInfo", null);
    		pageLoader.setOrderBy(" " + sql[3] + " ");
    		return pageLoader;
        	
        }*/
        
        /**
         * 获得还款通知单查询SQL
         * @param rInfo
         * @return
         */
       /* private String[] getRepayNoticeSQL(QueryRepayNoticeInfo rInfo){
        	
        	String[] sql=new String[4]; 
        	//select
        	sql[0]=
        	//from
        	sql[1]="FROM loan_AheadRepayForm a,loan_ContractForm b,userinfo e";
        		
        	//where
        	sql[2]=
        		
        	sql[3]=	
        	
        	return sql;
        	
        	
        }
        */
        
        
        
    	
    	/**
    	 * 查询贴现凭证汇总信息
    	 * @param cInfo
    	 * @return
    	 * @throws Exception
    	 */
    	public QuerySumInfo queryDiscountCredenceSum(QuestCredenceInfo cInfo) throws Exception{
    		
    		QuerySumInfo sumInfo = new QuerySumInfo();
    		Connection con = null;
    		ResultSet rs = null;
    		PreparedStatement ps = null;
    		String[] sql = null;
    		
    			 sql = getDiscountCredenceSQL(cInfo);
    		
    		String[] SQL=new String[2];
    		String strSQL = "";
    		System.out.println("-------------Welcome to Method 'QueryDiscountCredenceSum'----------------");
    		try
    		{
    			//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
    			SQL[0] = " /*+ INDEX_COMBINE(Y) */";
    			SQL[0] += " sum(NVL(l.MAMOUNT,0)) as sumAmount,";//贴现金额
    			SQL[0] += " sum(nvl(l.MINTEREST,0)) as sumInterest,";//贴现利息
    			SQL[0] += " sum(nvl(l.PURCHASERINTEREST,0)) as sumPurchaserInterest";//买方付息金额
    			
    			strSQL =  " select " + SQL[0] + " from " + sql[1] +" where "+sql[2]; 
    			log.print(strSQL);

    			con = Database.getConnection();
    			ps = con.prepareStatement(strSQL);
    			rs = ps.executeQuery();
    			if (rs.next())
    			{
    				sumInfo.setTotalDiscountAmount(rs.getDouble("sumAmount"));
    				sumInfo.setSumAllDiscountInterest(rs.getDouble("sumInterest"));
    				sumInfo.setSumDiscountPurchaserInterest(rs.getDouble("sumPurchaserInterest"));
    				
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
    	
    	
    	/**
    	 *  业务汇总查询
    	 * @param allInfo
    	 * @return
    	 * @throws Exception
    	 */
    	 public PageLoader queryAllBusinesses(QuestContractInfo allInfo) throws Exception{
    		 
    		   
    		   	
    		   	String[] sql = getQueryAllBussinessSql(allInfo);
    		   	
    		   	
    		   	/************为了国机的汇总而加入 2003-3-30 qqgd *************/
    			if (allInfo.getGather())
    			{
    				String strTmp = "(select \n" + sql[0] + " \nfrom " + sql[1] + " \nwhere " + sql[2] + ") sz";
    				sql[0] = "*";
    				sql[1] =
    					" (select borrowClientName,sum(nvl(examineAmount,0)) as examineAmount,sum(nvl(balance,0)) as balance, "
    						+ " sum(nvl(overdueAmount,0)) as overdueAmount,sum(nvl(punishInterest,0)) as punishInterest, "
    						+ " sum(nvl(discountPurchaserInterest,0)) as discountPurchaserInterest,"
    						+ " sum(nvl(discountInterest,0)) as discountInterest"
    						+ " from "
    						+ strTmp
    						+ " group by sz.borrowClientName ) ";
    				sql[2] = "";
    				sql[3] = "";
    				System.out.println("-----------------------------");
    				System.out.println(sql[1]);
    				System.out.println("-----------------------------");
    			}
    			//
    			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

    			pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.contract.dataentity.ContractDetailInfo", null);
    			pageLoader.setOrderBy(" " + sql[3] + " ");
    			return pageLoader;
    		   	
    		   	
    		   
    	 }
    	/**
    	 * 业务汇总查询SQL
    	 * @param allInfo
    	 * @return
    	 * @throws Exception
    	 */
    	 private String[] getQueryAllBussinessSql (QuestContractInfo qInfo) throws Exception{
    		 
    		 String[] sql = new String[4];
    			StringBuffer buf = new StringBuffer();

    			//select
    			//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
    			sql[0] = " /*+ INDEX_COMBINE(Y) */";
    			sql[0] +=
    				"c.ID as contractID,c.nTypeID as loanTypeID,'"+qInfo.getBalanceDate()+"' as dailyDate,c.sContractCode as contractCode,c.NINOROUT as inOrOut,"
    					+ " c1.sName as borrowClientName,c2.sName as ClientName,c.mLoanAmount as loanAmount,c.assureChargeRate as assureChargeRate,"
    					+ " c.mInterestRate as interestRate,c.dtStartDate as LoanStart,c.dtEndDate as LoanEnd,"
    					+ " c.nBankInterestID as bankInterestID ,c.nInterestTypeID interestTypeID,c.mAdjustRate as adjustRate ,c.mStaidAdjustRate as staidAdjustRate ,"
    					+ " c.nIntervalNum as intervalNum,c.nStatusID as statusID,u.sName as inputUserName,u2.sName as checkUserName,"
    					+ " c.mDisCountRate as discountRate,c.mCheckAmount as CheckAmount,c.mExamineAmount as examineAmount,"
    					+ " nvl(c.purchaserInterestRate,0) as purchaserInterestRate,(nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0)) as discountInterest,"
    					+ " ((nvl(c.mExamineAmount,0) - nvl(c.mCheckAmount,0))/decode((1-nvl(c.purchaserInterestRate,0)*0.01),0,1,(1-nvl(c.purchaserInterestRate,0)*0.01))*nvl(c.purchaserInterestRate,0)*0.01) as discountPurchaserInterest,"
    					+ " d.sApplyCode as applyCode,lp.balance as Balance,c3.sName as discountClientName "
    					+ " ,db.dailybalance "; //指定余额日期的贷款余额 add by wmzheng at 2010-10-14
    					
    			//胡志强修改(2004-03-09)
    			sql[0] += ",a.overdueAmount as overdueAmount,";
    			sql[0] += "b.punishInterest as punishInterest";
    			//added by xiong fei 2010/05/25查出各单位的担保信息
    			sql[0] += ",c.niscredit as isCredit,c.NISASSURE as isAssure,";
    			sql[0] += "c.NISIMPAWN as isImpawn,c.NISPLEDGE as isPledge,";
    			sql[0] += "c.ISRECOGNIZANCE as isRecognizance,c.ISREPURCHASE as isRepurchase, lt.name as loanTypeName";
    			
    			//add by wmzheng at 2010-09-25 合同风险状态
    			sql[0] += ",c.nrisklevel as risklevel";
    			
    			//from
    			sql[1] = " loan_contractForm c,client c1,client c2,client c3,userInfo u,userInfo u2,loan_loanForm d, loan_lateRateView y, ";
    			//从子账户表中取得当前余额（包括贴现）
    			sql[1] += "(select lp.nCOntractID,sum(sa.mbalance) as balance from sett_subAccount sa,(select ID,nContractID from loan_PayForm union select ID,nContractID from loan_DiscountCredence) lp " + "where sa.AL_nLoanNoteID = lp.ID group by lp.nCOntractID) lp ";

    			//指定余额日期的贷款余额 add by wmzheng at 2010-10-14
    			sql[1] += ",(select db.nCOntractID,sum(da.mbalance) as dailybalance from sett_subAccount sa,sett_dailyaccountbalance da,sett_account a,";
    			sql[1] += " (select ID, nContractID from loan_PayForm union select ID, nContractID from loan_DiscountCredence) db";
    			sql[1] += " where sa.AL_nLoanNoteID = db.ID and da.nsubaccountid = sa.id and sa.naccountid = a.id and da.dtdate = to_date('"+DataFormat.getDateString(qInfo.getBalanceDate())+"','yyyy-mm-dd')";
    			sql[1] += " group by db.nCOntractID ) db";
    			
    			//胡志强修改(2004-03-09)
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
    			sql[1] += " and sysdate-c.dtClearInterest >= 90"; //上一结息日90天后的日期
    			sql[1] += " GROUP BY a.id) b,loan_loantypesetting lt";

    			//where
    			sql[2] = " lt.id = c.nsubtypeid and c1.id(+)=c.nBorrowClientID and c2.id(+)=c.nconsignclientid and c3.id(+)=c.DiscountClientID and u.id(+)=c.nInputUserID and u2.id(+)=c.NNEXTCHECKUSERID and d.id(+)=c.nLoanID" + " and lp.nContractID(+) =c.id"+" and db.ncontractid(+) = c.id";

    			//胡志强修改(2004-03-09)
    			sql[2] += " and c.id = a.id(+)";
    			sql[2] += " and c.id = b.id(+)";
    			sql[2] += " and c.id = y.contractID(+) and lt.loantypeid=c.nTypeID";
    			sql[2] += " and c.ntypeid != 6 ";
    			
    				
    				if(qInfo.getMinRate()>0)
    				{
    					sql[2] += " and c.mDiscountRate >= " + qInfo.getMinRate();
    				}
    				if(qInfo.getMaxRate()>0)
    				{
    					sql[2] += " and c.mDiscountRate <= " + qInfo.getMaxRate();
    				}
    				//贴现客户名称
    				if ((qInfo.getDiscountClientName() != null) && (qInfo.getDiscountClientName().length() > 0))
    				{
    					buf.append(" and c.discountClientName like '%" + qInfo.getDiscountClientName() + "%'");
    				}
    			
    			
    			//贷款种类
    			if (qInfo.getLoanTypeID() > 0)
    				buf.append(" and c.nsubTypeID=" + qInfo.getLoanTypeID());
    			
    	
    			//合同编号开始
    			if (qInfo.getMinContractCode() != null && qInfo.getMinContractCode().length() > 0)
    				buf.append(" and c.sContractCode>='" + qInfo.getMinContractCode() + "'");

    			//合同编号结束
    			if (qInfo.getMaxContractCode() != null && qInfo.getMaxContractCode().length() > 0)
    				buf.append(" and c.sContractCode<='" + qInfo.getMaxContractCode() + "'");

    			/***************添加国机的变更 2003-3-30 qqgd***************/
    			//合同状态
    			if (!qInfo.isShowEnd())
    			{
    				buf.append(" and c.nStatusID<>" + LOANConstant.ContractStatus.FINISH);
    			}

    			/************为了国机加的判断，限制状态 2004-4-23 qqgd *********/
    			
    			long contractStatusVal[] = LOANConstant.ContractStatus.getAllCode(qInfo.getOfficeID(),qInfo.getCurrencyID());
    			String strStatus = "";
    			for (int i = 0; i < contractStatusVal.length; i++)
    			{
    				strStatus += contractStatusVal[i];
    				if ((contractStatusVal.length - i) > 1)
    					strStatus += ", ";
    			}
    			
    			if(qInfo.getStatusIDs()==null||qInfo.getStatusIDs().equals("")|| qInfo.getStatusIDs().equals("-1")){
    			buf.append(" and c.nStatusID in (" + strStatus + ")");
    			
    			}

    			//minzhao20090505修改将合同状态增加为多状态查询
    			else
    			{
    				buf.append(" and c.nStatusID in (" + qInfo.getStatusIDs()+")");
    			}

    			//add by wmzheng at 2010-09-26 多个贷款种类、子种类
    			if(!qInfo.getLoanTypeIDs().equals("")&&!qInfo.getLoanTypeIDs().equals("-1"))
    			{
    				buf.append(" and c.nSubTypeID in (" + qInfo.getLoanTypeIDs()+")");
    			}
    			/*if(!qInfo.getLoanSubTypeIDs().equals("")&&!qInfo.getLoanSubTypeIDs().equals("-1"))
    			{
    				buf.append(" and c.nSubTypeID in (" + qInfo.getLoanSubTypeIDs()+")");
    			}*/
    			
    			//借款单位
    			if (qInfo.getBorrowClientID() > 0)
    				buf.append(" and c.nBorrowClientID=" + qInfo.getBorrowClientID());
    			//借款单位开始
    			if (qInfo.getBorrowClientIDFrom() > 0)
    				buf.append(" and c.nBorrowClientID>=" + qInfo.getBorrowClientIDFrom());
    			//借款单位结束
    			if (qInfo.getBorrowClientIDTo() > 0)
    				buf.append(" and c.nBorrowClientID<=" + qInfo.getBorrowClientIDTo());

    			//借款单位账号
    			if ((qInfo.getBorrowAccount() != null) && (qInfo.getBorrowAccount().length() > 0))
    				buf.append(" and c1.sAccount='" + qInfo.getBorrowAccount() + "'");

    			//客户分类
//    			if (qInfo.getLoanClientTypeID() > 0)
//    				buf.append(" and c1.nLoanClientTypeID=" + qInfo.getLoanClientTypeID());

    			//主管单位
    			if (qInfo.getParentCorpID() > 0)
    				buf.append(" and c1.nParentCorpID1=" + qInfo.getParentCorpID());

    			//委托单位
    			if (qInfo.getConsignClientID() > 0)
    				buf.append(" and c.nConsignClientID=" + qInfo.getConsignClientID());
    			//委托单位开始
    			if (qInfo.getConsignClientIDFrom() > 0)
    				buf.append(" and c.nConsignClientID>=" + qInfo.getConsignClientIDFrom());
    			//委托单位结束
    			if (qInfo.getConsignClientIDTo() > 0)
    				buf.append(" and c.nConsignClientID<=" + qInfo.getConsignClientIDTo());

    			//委托单位账号
    			if ((qInfo.getConsignAccount() != null) && (qInfo.getConsignAccount().length() > 0))
    				buf.append(" and c2.sAccount='" + qInfo.getConsignAccount() + "'");

    			//贷款金额开始
    			if (qInfo.getMaxLoanAmount() > 0)
    				buf.append(" and c.mExamineAmount<=" + DataFormat.formatAmount(qInfo.getMaxLoanAmount()));

    			//贷款金额结束
    			if (qInfo.getMinLoanAmount() > 0)
    				buf.append(" and c.mExamineAmount>=" + DataFormat.formatAmount(qInfo.getMinLoanAmount()));

    			//add by wmzheng at 2010-10-14 贷款余额
    			//贷款余额开始
    			if (qInfo.getMinLoanBalanceAmount() > 0)
    				buf.append(" and lp.balance >= " + DataFormat.formatAmount(qInfo.getMinLoanBalanceAmount()));
    			//贷款余额结束
    			if (qInfo.getMaxLoanBalanceAmount() > 0)
    				buf.append(" and lp.balance <= " + DataFormat.formatAmount(qInfo.getMaxLoanBalanceAmount()));
    			
    			//贷款日期开始
    			if (qInfo.getMaxStartDate() != null)
    				buf.append(" and TRUNC(c.dtStartDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxStartDate()) + "','yyyy-mm-dd') ");

    			//贷款日期结束
    			if (qInfo.getMinStartDate() != null)
    				buf.append(" and TRUNC(c.dtStartDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinStartDate()) + "','yyyy-mm-dd') ");

    			//贷款结束日期结束
    			if (qInfo.getMaxEndDate() != null)
    				buf.append(" and TRUNC(c.dtEndDate)<= To_Date('" + DataFormat.getDateString(qInfo.getMaxEndDate()) + "','yyyy-mm-dd') ");

    			//贷款结束日期开始
    			if (qInfo.getMinEndDate() != null)
    				buf.append(" and TRUNC(c.dtEndDate)>= To_Date('" + DataFormat.getDateString(qInfo.getMinEndDate()) + "','yyyy-mm-dd') ");
    			
    			//贴现日期结束
    			if (qInfo.getMaxDiscountDate() != null)
    				buf.append(" and TRUNC(c.dtDiscountDate) <= To_Date('" + DataFormat.getDateString(qInfo.getMaxDiscountDate()) + "','yyyy-mm-dd') ");

    			//贴现日期开始
    			if (qInfo.getMinDiscountDate() != null)
    				buf.append(" and TRUNC(c.dtDiscountDate) >= To_Date('" + DataFormat.getDateString(qInfo.getMinDiscountDate()) + "','yyyy-mm-dd') ");

    			//合同利率 add by wmzheng at 2010-10-14
    			if (qInfo.getContractRateFrom() > 0)
    				buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE >= " + DataFormat.formatRate(qInfo.getContractRateFrom()));
    			if (qInfo.getContractRateTo() > 0)
    				buf.append(" and (DECODE(c.nTypeID, "+LOANConstant.LoanType.TX+", c.mDiscountRate, "+LOANConstant.LoanType.ZTX+", c.mDiscountRate, c.mInterestRate)*(1+c.MADJUSTRATE*1/100))+c.MSTAIDADJUSTRATE <= " + DataFormat.formatRate(qInfo.getContractRateTo()));
    			
    			//贷款期限
    			if (qInfo.getIntervalNum() > 0)
    				buf.append(" and c.nIntervalNum = " + qInfo.getIntervalNum());

    			//贷款期限由
    			if (qInfo.getPeriodFrom() > 0)
    				buf.append(" and c.nIntervalNum >= " + qInfo.getPeriodFrom());
    			//贷款期限至
    			if (qInfo.getPeriodTo() > 0)
    				buf.append(" and c.nIntervalNum <= " + qInfo.getPeriodTo());
    			
    			//保证方式
    			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.CREDIT)
    				buf.append(" and c.nIsCredit=1");
    			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.ASSURE)
    				buf.append(" and c.nIsAssure=1");
    			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.IMPAWN)
    				buf.append(" and c.nIsImpawn=1");
    			if (qInfo.getAssureTypeID() == LOANConstant.AssureType.PLEDGE)
    				buf.append(" and c.nIsPledge=1");
    			
    			//added by xiong fei 2010/05/25 融资合同查询加了担保方式查询，勾选的都要查出来
    			if(qInfo.getIsassure()==1||qInfo.getIscredit()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
    				buf.append(" and ( ");
    				if(qInfo.getIscredit()==1){
    					buf.append(" c.nIsCredit=1");
    					if(qInfo.getIsassure()==1||qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
    						buf.append(" or");
    					}
    				}
    				if(qInfo.getIsassure()==1){
    					buf.append(" c.nIsAssure=1");
    					if(qInfo.getIsimpawn()==1||qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
    						buf.append(" or");
    					}
    				}
    				if(qInfo.getIsimpawn()==1){
    					buf.append(" c.nIsImpawn=1");
    					if(qInfo.getIspledge()==1||qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
    						buf.append(" or");
    					}	
    				}
    				if(qInfo.getIspledge()==1){
    					buf.append(" c.nIsPledge=1");
    					if(qInfo.getIsrecognizance()==1||qInfo.getIsrepurchase()==1){
    						buf.append(" or");
    					}
    				}
    				if(qInfo.getIsrecognizance()==1){
    					buf.append(" c.IsRecognizance=1");
    					if(qInfo.getIsrepurchase()==1){
    						buf.append(" or");
    					}
    				}
    				if(qInfo.getIsrepurchase()==1){
    					buf.append(" c.IsRepurchase=1");
    				}
    				buf.append(" ) ");
    			}
    				

    			//信用等级
    			if (qInfo.getCreditLevel() > 0)
    				buf.append(" and c1.NCREDITLEVELID=" + qInfo.getCreditLevel());

    			//是否股东
    			if (qInfo.getIsPartner() > 0)
    				buf.append(" and c1.NISPARTNER=" + qInfo.getIsPartner());

    			//是否技改贷款
    			if (qInfo.getIsTechnical() > 0)
    				buf.append(" and c.NISTECHNICAL=" + qInfo.getIsTechnical());

    			//是否循环贷款
    			if (qInfo.getIsCircle() > 0)
    				buf.append(" and c.NISCIRCLE=" + qInfo.getIsCircle());

    			//贷款风险状态
    			if (qInfo.getRiskLevel() > 0)
    				buf.append(" and c.nRiskLevel=" + qInfo.getRiskLevel());
    			
    			//add by wmzheng at 2010-10-14 贷款风险状态（复选）
    			if (qInfo.getRiskLevels() != null && qInfo.getRiskLevels().length() > 0)
    				buf.append(" and c.nRiskLevel in (" + qInfo.getRiskLevels()+")");
    			
    			//按地区分类
    			if (qInfo.getTypeID1() > 0)
    				buf.append(" and c.nTypeID1=" + qInfo.getTypeID1());

    			//按行业分类1
    			if (qInfo.getTypeID2() > 0)
    				buf.append(" and c.nTypeID2=" + qInfo.getTypeID2());

    			//按行业分类2
    			if (qInfo.getTypeID3() > 0)
    				buf.append(" and c.nTypeID3=" + qInfo.getTypeID3());

    			//管理人
    			if (qInfo.getInputUserID() > 0)
    				buf.append(" and c.nInputUserID=" + qInfo.getInputUserID());
    			
    			//办事处
    			if(qInfo.getOfficeID() > 0)
    				buf.append(" and c.nOfficeID=" + qInfo.getOfficeID());
    			
    	        //币种
    			if(qInfo.getCurrencyID() > 0)
    				buf.append(" and c.nCurrencyID=" + qInfo.getCurrencyID());
    			
    			//add by wmzheng at 2010-09-21
    	        //客户属性一
    			if(qInfo.getClientTypeID1() > 0)
    				buf.append(" and c1.nClienttypeID1=" + qInfo.getClientTypeID1());
    			
    	        //客户属性二
    			if(qInfo.getClientTypeID2() > 0)
    				buf.append(" and c1.nClienttypeID2=" + qInfo.getClientTypeID2());
    			
    	        //客户属性三
    			if(qInfo.getClientTypeID3() > 0)
    				buf.append(" and c1.nClienttypeID3=" + qInfo.getClientTypeID3());
    			
    	        //客户属性四
    			if(qInfo.getClientTypeID4() > 0)
    				buf.append(" and c1.nClienttypeID4=" + qInfo.getClientTypeID4());
    			
    	        //客户属性五
    			if(qInfo.getClientTypeID5() > 0)
    				buf.append(" and c1.nClienttypeID5=" + qInfo.getClientTypeID5());
    			
    	        //客户属性六
    			if(qInfo.getClientTypeID6() > 0)
    				buf.append(" and c1.nClienttypeID6=" + qInfo.getClientTypeID6());
    			
    			sql[2] = sql[2] + buf.toString();

    			//order
    			String strDesc = qInfo.getDesc() == 1 ? " desc " : "";
    			StringBuffer oBuf = new StringBuffer();
    			switch ((int) qInfo.getOrderParam())
    			{
    				case 1 :
    					oBuf.append(" \n order by lt.id" + strDesc);
    					break;
    				case 2 :
    					oBuf.append(" \n order by c.sContractCode" + strDesc);
    					break;
    				case 3 :
    					oBuf.append(" \n order by c1.sName" + strDesc);
    					break;
    				case 4 :
    					oBuf.append(" \n order by c2.sName" + strDesc);
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
    					oBuf.append(" \n order by u.sName" + strDesc);
    					break;
    				case 11 :
    					oBuf.append(" \n order by d.sApplyCode" + strDesc);
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

    					//胡志强修改(2004-03-09)
    				case 16 :
    					oBuf.append(" \n order by a.overdueAmount" + strDesc);
    					break;
    				case 17 :
    					oBuf.append(" \n order by b.punishInterest" + strDesc);
    					break;

    				case 20 :
    					oBuf.append(" \n order by u2.sName" + strDesc);
    					break;
    				case 21 :
    					oBuf.append(" \n order by c3.sName" + strDesc);
    					break;
    				case 22 :
    					oBuf.append(" \n order by c.nrisklevel" + strDesc);
    					break;				
    				default :
    					oBuf.append(" \n order by c.ID" + strDesc);
    					break;
    			}
    			sql[3] = oBuf.toString();
    			log.print("get QuerySQL:\n" + sql[0] + "\n" + sql[1] + "\n" + sql[2] + "\n" + sql[3] + "\n");
    			System.out.println("sql[0]==========="+sql[0]);
    			System.out.println("sql[1]==========="+sql[1]);
    			System.out.println("sql[2]==========="+sql[2]);
    			System.out.println("sql[3]==========="+sql[3]);

    			return sql;
    	 }
    	 
    	 
    	 /**
    	    * 业务汇总根据表头排序
    	    * @param orderParam
    	    * @param desc
    	    * @return
    	    */
    	  public String getAllBusinessesOrder(long orderParam, long desc)
    	   {
    		   String orderSQL = "";
    		   return orderSQL;
    	  }
    	  /**
    	    * 业务汇总各种总额
    	    * @param allInfo
    	    * @return
    	    * @throws Exception
    	    */
    	   public QuerySumInfo queryAllBusinessesSum(QuestContractInfo qInfo)throws Exception{
    		    
    		    QuerySumInfo sumInfo = new QuerySumInfo();
    			Connection con = null;
    			ResultSet rs = null;
    			PreparedStatement ps = null;
    			String[] sql = null;
    		    sql = getQueryAllBussinessSql(qInfo);
  
    			String[] SQL=new String[2];
    			String strSQL = "";
    			System.out.println("-------------Welcome to Method 'QueryContractSum'----------------");
    			try
    			{
    				
    				//修改by kenny(2007-03-06)，修改贷款合同查询时自动断开数据库连接的问题
    				SQL[0] = " /*+ INDEX_COMBINE(Y) */";
    				SQL[0] += " sum(NVL(q.LoanAmount,0)) as sumLoanAmount,";
    				SQL[0] += " sum(nvl(q.OverdueAmount,0)) as sumOverdueAmount,";
    				SQL[0] += " sum(nvl(q.PunishInterest,0)) as sumPunishInterest,";
    				SQL[0] += " sum(nvl(q.Balance,0)) as sumBalance,";
    				SQL[0] += " sum(nvl(q.CheckAmount,0)) as sumCheckAmount,";
    				SQL[0] += " sum(nvl(q.PurchaserInterest,0)) as sumDiscountPurchaserInterest,";
    				SQL[0] += " sum(nvl(q.DiscountInterest,0)) as sumDiscountInterest";
    				if(qInfo.getQueryPurpose()!=QuestContractInfo.TX){
    			    SQL[0] += ", sum(nvl(q.dailybalance, 0)) as sumDailyBalance";//add by wmzheng at 2010-10-14 指定余额日期贷款余额
    				}
    				//贷款金额计算错误 2008-12-15No.261修改 kaishao
    				SQL[1] = " (select c.mExamineAmount as LoanAmount,";
    				//No.261结束。删除原有的distinct
    				SQL[1] +=" a.overdueAmount as OverdueAmount,"; 
    				SQL[1] +=" b.punishInterest as PunishInterest,";
    				SQL[1] +=" lp.balance as Balance, c.mCheckAmount as CheckAmount,";
    				SQL[1] +=" (c.mExamineAmount - c.mCheckAmount)/decode((1-c.purchaserInterestRate*0.01),0,1,(1-c.purchaserInterestRate*0.01))*c.purchaserInterestRate*0.01 as PurchaserInterest,";
    				SQL[1] +=" (c.mExamineAmount - c.mCheckAmount) as DiscountInterest ";
    				if(qInfo.getQueryPurpose()!=QuestContractInfo.TX)
    				SQL[1] +=", db.dailybalance as Dailybalance ";//add by wmzheng at 2010-10-14 指定余额日期贷款余额
    		
    				
    				SQL[1] +=" from "+sql[1];
    				SQL[1] +=" where "+sql[2];
    				SQL[1] +=") q";
    				strSQL =  " select " + SQL[0] + " from " + SQL[1]; 
    				log.print(strSQL);

    				con = Database.getConnection();
    				ps = con.prepareStatement(strSQL);
    				rs = ps.executeQuery();
    				if (rs.next())
    				{
    					sumInfo.setTotalApplyAmount(rs.getDouble("sumLoanAmount"));
    					sumInfo.setSumOverdueAmount(rs.getDouble("sumOverdueAmount"));
    					sumInfo.setSumPunishInterest(rs.getDouble("sumPunishInterest"));
    					sumInfo.setTotalBalance(rs.getDouble("sumBalance"));
    					if(qInfo.getQueryPurpose()!=QuestContractInfo.TX)
    					sumInfo.setTotalDailyBalance(rs.getDouble("sumDailyBalance"));
    					sumInfo.setSumCheckAmount(rs.getDouble("sumCheckAmount"));
    					sumInfo.setSumDiscountInterest(rs.getDouble("sumDiscountInterest"));
    					sumInfo.setSumDiscountPurchaserInterest(rs.getDouble("sumDiscountPurchaserInterest"));
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
    	   
   
    	   /**
    	    * 
    	    * 点击操作员获得业务办理人员调整信息
    	    * @param contractID
    	    * @return
    	    * @throws Exception
    	    */
    	   
    	   public List queryAdjustUser(long contractID) throws Exception{
    	
    	
    	    List list=new ArrayList();
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			
			String strSQL = "";
			
			try
			{
				strSQL="select lpad(rownum, 3,'00' ) as numberRow, b.sname as beforeUser, b1.sname as afterUser  ,a.modifydate as  adjustDate, a.reason as reason " +
						" from  LOAN_LOANMNGERMODIFY_HST a,userInfo b,userInfo b1" +
						"  where a.SCONTRACTCODE="+contractID +"and b.id=a.olduserid and b1.id=a.newuserid";
				
				System.out.println("*****************业务人员调整sql开始**********************");
				
				System.out.println(strSQL);
				
				System.out.println("*******************************");
				log.print(strSQL);

				con = Database.getConnection();
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				while (rs.next())
				{
					HashMap map = new HashMap();
					
					map.put("numberRow", rs.getString("numberRow"));
					map.put("beforeUser", rs.getString("beforeUser"));
					map.put("afterUser", rs.getString("afterUser"));
					map.put("adjustDate", rs.getTimestamp("adjustDate"));
					map.put("reason", rs.getString("reason"));
					list.add(map);
					
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
			return list;
    }
    	   
    	   
//    		放款单逾期转表外查询
    	   public PageLoader queryPayform_Overdue(QuestPayNoticeInfo payNInfo) throws Exception
    		{
    		   getPayform_OverdueSQL(payNInfo);
    			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
    			pageLoader.initPageLoader(
    				new AppContext(),
    				m_sbFrom.toString(),
    				m_sbSelect.toString(),
    				m_sbWhere.toString(),
    				(int) Constant.PageControl.CODE_PAGELINECOUNT1,
    				"com.iss.itreasury.loan.query.dataentity.QueryPayformOverdue",
    				null);
    			pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
    			return pageLoader;
    		}
//   		放款单逾期转表外查询SQL
    	   public void getPayform_OverdueSQL(QuestPayNoticeInfo payNoticeInfo)
    		{
    			try{
    					m_sbSelect = new StringBuffer();
    					m_sbSelect.append(" * \n");
    					m_sbFrom = new StringBuffer();				
    					m_sbFrom.append(" SETT_PAYFORM_OVERDUE s \n");
    					m_sbWhere = new StringBuffer();
    					m_sbWhere.append(" 1=1");
    					m_sbWhere.append(" and s.statusid!=2 ");			
    					m_sbWhere.append(" and s.ncurrencyid="+payNoticeInfo.getNCurrencyID()+"");
    					m_sbWhere.append(" and s.nofficeid="+payNoticeInfo.getNOfficeID()+"");
    					//贷款类型
    				       if(payNoticeInfo.getNLoanType()!=null && !payNoticeInfo.getNLoanType().trim().equals("-1") ){
    				    	   m_sbWhere.append(" and s.ntypeid="+payNoticeInfo.getNLoanType()+"");
    				       } 
    				     //是否转表外
    				       if(payNoticeInfo.getNstatusid()!=-1){
    				    	   m_sbWhere.append(" and s.nstatusid="+payNoticeInfo.getNstatusid()+"");
    				       }    
    				    
    				     //合同编号
    						if(payNoticeInfo.getContractCodeFrom()!=null && payNoticeInfo.getContractCodeFrom().length()>0 ){
    							
    							m_sbWhere.append(" and s.ContractCode>='"+payNoticeInfo.getContractCodeFrom()+"'");
    						
    						}
    			
    					if(payNoticeInfo.getContractCodeTo()!=null && payNoticeInfo.getContractCodeTo().length()>0 ){
    						
    						m_sbWhere.append(" and s.ContractCode<='"+payNoticeInfo.getContractCodeTo()+"'");
    				
    				    }
    					//借款单位
    					if(payNoticeInfo.getLoanClientIDFrom()>0){
    						
    						m_sbWhere.append(" and s.nborrowclientid>="+payNoticeInfo.getLoanClientIDFrom());
    				
    				    }
    			        if(payNoticeInfo.getLoanClientIDTo()>0){
    						
    			        	m_sbWhere.append(" and s.nborrowclientid<="+payNoticeInfo.getLoanClientIDTo());
    				
    				    }
    			      //放款单金额
    			        if(payNoticeInfo.getMPayAmountFrom()>0){
    						
    			        	m_sbWhere.append(" and s.mamount>="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountFrom()));
    				
    				    }
    			        if(payNoticeInfo.getMPayAmountTo()>0){
    						
    			        	m_sbWhere.append(" and s.mamount<="+DataFormat.formatAmount(payNoticeInfo.getMPayAmountTo()));
    				
    				    }
    			      //放贷日期开始
    			  		if (payNoticeInfo.getDtLoanPayDateFrom()!= null){
    			  			m_sbWhere.append(" and TRUNC(s.dtoutdate) >= To_Date('" + payNoticeInfo.getDtLoanPayDateFrom() + "','yyyy-mm-dd') ");
    			  		}
    			  		
    			  		if (payNoticeInfo.getDtLoanPayDateTo() != null){
    			  			m_sbWhere.append(" and TRUNC(s.dtoutdate) <= To_Date('" +payNoticeInfo.getDtLoanPayDateTo() + "','yyyy-mm-dd') ");
    			  		}
    			  		
    			  		 //还款日期
    			  		if (payNoticeInfo.getDtRepayDateFrom()!= null){
    			  			m_sbWhere.append(" and TRUNC(s.dtend ) >= To_Date('" + payNoticeInfo.getDtRepayDateFrom() + "','yyyy-mm-dd') ");
    			  		}
    			  		
    			  		if (payNoticeInfo.getDtRepayDateTo() != null){
    			  			m_sbWhere.append(" and TRUNC(s.dtend ) <= To_Date('" +payNoticeInfo.getDtRepayDateTo() + "','yyyy-mm-dd') ");
    			  		}
    			  		if(payNoticeInfo.getNstatusid()==1){
    			  			m_sbWhere.append(" and s.nstatusid=1 ");
    			  		}
    			  		if(payNoticeInfo.getNstatusid()==2){
    			  			m_sbWhere.append(" and s.nstatusid=2 ");
    			  		}
    				m_sbOrderBy = new StringBuffer();
    				m_sbOrderBy.append(" ");
    			}catch(Exception e)
    			{
    				e.printStackTrace();
    			}
    		}
    		//转表外、撤销操作
    		public long updateStatus(long ID, long lStatus, long lUserID) throws Exception
    		{
    			Connection con = null;
    			PreparedStatement ps = null;
    			long lResult = -1;
    			int nIndex = 1;
    			long lUpdateStatus = -1;
    			lUpdateStatus = lStatus;
    			try
    			{
    				con = Database.getConnection();
    				StringBuffer sb = new StringBuffer();
    				sb.append("UPDATE SETT_PAYFORM_OVERDUE SET nstatusid = ? ");
				    sb.append(" , MODIFYUSERID=?, MODIFYDATE=sysdate ");
    				sb.append(" where id = ?");
    				ps = con.prepareStatement(sb.toString());
    				nIndex = 1;
    				ps.setLong(nIndex++, lUpdateStatus);
    				ps.setLong(nIndex++, lUserID);
    				ps.setLong(nIndex++, ID);
    				lResult = ps.executeUpdate();
    				// 关闭数据库资源
    				ps.close();
    				ps = null;
    				con.close();
    				con = null;
    			}
    			catch (IException ie)
    			{
    				throw ie;
    			}
    			catch (Exception e)
    			{
    				throw new IException("Gen_E001");
    			}
    			finally
    			{
    				try
    				{
    					if (ps != null)
    					{
    						ps.close();
    						ps = null;
    					}
    					if (con != null)
    					{
    						con.close();
    						con = null;
    					}
    				}
    				catch (Exception e)
    				{
    					throw new IException("Gen_E001");
    				}
    			}
    			return lResult;
    		}
    		//坏账记录查询
    		public Collection queryOffBalanceInfo(long office,long currency,String tsInterestStart) throws Exception
    		{   
    			QueryPayformOverdue info=null;
    			Connection con = null;
    			PreparedStatement ps = null;
    			ResultSet rs = null;
    			Collection vReturn = new ArrayList();
    			StringBuffer sbSQL = new StringBuffer();
    			try
    			{   
    			
    				con = Database.getConnection();
    				sbSQL.append(" select l.ID,l.sCode as Code,c.sContractCode as ContractCode,d.name as loanClientName,d.id as loanClientId,\n");
    				sbSQL.append("c.mExamineAmount as loanAmount,u.sName as InputUserName,lt.name as loanTypeName,lt.id as loanTypeId,l.mAmount as amount,\n");
    				sbSQL.append("l.dtOutDate as outDate,l.dtEnd as inDate,l.minterestrate as InterestRate,l.nContractid as contractID,ss.mbalance,c.nTypeID as LoanTypeID,l.dtInputDate as inputDate,\n");       
    				sbSQL.append("l.nStatusID as statusID,c.overduedate  overduedate ,l.nofficeid,l.ncurrencyid,l.ninputuserid,l.dtinputdate, \n");
    				sbSQL.append(" ss.al_mcompoundinterest,ss.al_moverdueinterest,ss.nstatusid,ss.mbalance\n");
    				sbSQL.append("from loan_payform l,client_clientinfo d,loan_contractForm c,userInfo u,loan_loantypesetting lt,sett_subaccount ss  \n");
    				sbSQL.append("where u.id = l.NINPUTUSERID and lt.id = c.nsubtypeid and ss.al_nloannoteid=l.id and c.ID = l.nContractID and d.id = c.nBorrowClientID and l.NOFFICEID = "+office+" \n"); 
    				sbSQL.append(" and  l.NCURRENCYID ="+currency+" and c.nSubTypeID in (select a.id as subTypeID from loan_loantypesetting  a,loan_loantyperelation b where a.statusid = 1 and a.loantypeid = b.loantypeid and a.loantypeid =1 and a.id = b.subloantypeid \n");
    				sbSQL.append("and b.currencyid ="+currency+"  and b.officeid ="+office+") and l.nStatusID in (1, 3, 4, 20) and to_date('"+tsInterestStart+"','yyyy-mm-dd')+1-dtEnd>=90 \n");
              
    				System.out.print("SQL="+sbSQL);
    				ps = con.prepareStatement(sbSQL.toString());
    				rs = ps.executeQuery();
    		         	
    				while(rs.next())
    				{     
    					info = new QueryPayformOverdue();
    					
    					info.setScode(rs.getString("Code"));
    					info.setNcontractid(rs.getLong("contractID"));
    					info.setMamount(rs.getLong("amount"));
    					info.setNdrawnoticeid(rs.getLong("ID"));
    					info.setDtoutdate(rs.getTimestamp("outDate"));
    					info.setDtend(rs.getTimestamp("inDate"));
    					info.setNofficeid(rs.getLong("nofficeid"));
    					info.setNcurrencyid(rs.getLong("ncurrencyid"));
    					info.setNinputuserid(rs.getLong("ninputuserid"));
    					info.setDtinputdate(rs.getTimestamp("dtinputdate"));
    					info.setNborrowclientid(rs.getLong("loanClientId"));
    					info.setNtypename(rs.getString("loanTypeName"));
    					info.setContractCode(rs.getString("ContractCode"));
    					info.setNborrowclientname(rs.getString("loanClientName"));
    					info.setNtypeid(rs.getLong("loanTypeId"));
    					info.setMcompoundinterest(rs.getDouble("al_mcompoundinterest"));
    					info.setMOVERDUEINTEREST(rs.getDouble("al_moverdueinterest"));
    					info.setMbalance(rs.getDouble("mbalance"));
    					info.setStatusid(rs.getLong("nstatusid"));
    				    vReturn.add(info);	
    				}
    				rs.close();
    				rs = null;
    				ps.close();
    				ps = null;
    				con.close();
    				con = null;
    			}
    			catch (Exception e)
    			{
    				throw new IException("查询出现异常");
    			}
    			finally
    			{
    				try
    				{
    					if (rs != null)
    					{
    						rs.close();
    						rs = null;
    					}
    					if (ps != null)
    					{
    						ps.close();
    						ps = null;
    					}
    					if (con != null)
    					{
    						con.close();
    						con = null;
    					}
    				}
    				catch (Exception e)
    				{
    					throw new IException("Gen_E001");
    				}
    			}
    			return vReturn;
    			
    			
    		}
    		
    		//将一条坏账记录插入到表SETT_PAYFORM_OVERDUE中
  		  public long addOffbalance(QueryPayformOverdue dataEntity) throws Exception {
  		    	long myid = -1;
  		    	try {
  		    		this.transConn = Database.getConnection();;
					QueryDao dao = new QueryDao();
					dao.setUseMaxID();
					dao.strTableName="SETT_PAYFORM_OVERDUE";
					myid = dao.add(dataEntity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  		    	
  		    	return myid;
  		    }
  		//查询SETT_PAYFORM_OVERDUE中是否已有该笔放款单记录
        public boolean isRepeat(QueryPayformOverdue dataEntity) throws ITreasuryException {
  	 		
  	 		boolean exists = false;
  	 		try {
  	 			initDAO();
  	 			StringBuffer sb = new StringBuffer();
  	 			sb.append(" select ndrawnoticeid");
  	 			sb.append(" from SETT_PAYFORM_OVERDUE ");
  	 			sb.append(" where ndrawnoticeid = '"+ dataEntity.getNdrawnoticeid());
  	 			sb.append("' ");
  	 			transPS = transConn.prepareStatement(sb.toString());
  	 			transRS = transPS.executeQuery();
  	 			if (transRS.next()) {
  	 				exists = true;
  	 			}
  	 		} catch (Exception e) {
  	 			log.error(e.toString());
  	 			throw new ITreasuryException();
  	 		}
  	 		finalizeDAO();

  	 		return exists;
  	 	}
      //更新表SETT_PAYFORM_OVERDUE
		public long updateOffBalance(QueryPayformOverdue dataEntity) throws Exception
		{
			PreparedStatement ps = null;
			Connection con = Database.getConnection();
			long lResult = -1;
			int nIndex = 1;
			double mcompoundinterest =dataEntity.getMcompoundinterest();
			double mbalance=dataEntity.getMbalance();
			double moverdueinterest=dataEntity.getMOVERDUEINTEREST();
			long ndrawnoticeid=dataEntity.getNdrawnoticeid();
			long nstatusid=dataEntity.getStatusid();
			try
			{
				StringBuffer sb = new StringBuffer();
				sb.append("UPDATE SETT_PAYFORM_OVERDUE SET mcompoundinterest = ? ");
			    sb.append(" , mbalance=?, moverdueinterest=?,statusid=? ");
				sb.append(" where ndrawnoticeid = ?");
				ps = con.prepareStatement(sb.toString());
				nIndex = 1;
				ps.setDouble(nIndex++, mcompoundinterest);
				ps.setDouble(nIndex++, mbalance);
				ps.setDouble(nIndex++, moverdueinterest);
				ps.setLong(nIndex++, nstatusid);
				ps.setLong(nIndex++, ndrawnoticeid);
				lResult = ps.executeUpdate();
				// 关闭数据库资源
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
			finally
			{
				try
				{
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
				catch (Exception e)
				{
					throw new IException("Gen_E001");
				}
			}
			return lResult;
		}
  		
}
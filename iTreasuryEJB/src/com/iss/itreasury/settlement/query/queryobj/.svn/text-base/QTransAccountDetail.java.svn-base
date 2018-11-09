/*
 * Created on 2003-10-30
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.query.queryobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao;
/**
 * @author rxie
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class QTransAccountDetail extends BaseQueryObject
{
	public static void main(java.lang.String[] args) throws Exception
	{
		try
		{
			QTransAccountDetail obj = new QTransAccountDetail();
			System.out.println(obj.getBankAccountNoByAccountID(2));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public final static int OrderBy_TransNo = 1;

	//
	Log4j logger = null;
	/**
	 * 取得账户的期初余额
	 */
	public double queryTransAccountBalance(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select sum(mBalance) Balance from sett_DailyAccountBalance where nAccountID=" + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(qtai.getStartDate())) + "','yyyy-mm-dd') \n");

			conn = getConnection();
			log.print("取得期初余额SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				dReturn = rs.getDouble("Balance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	
	/**
	 * @author 马现福
	 * 取得账户的期初计息余额
	 */
	public double queryTransAccountBalanceForIntr(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select sum(nvl(Minterestbalance,0)+nvl(ac_mnegotiatebalance,0)) Balance from sett_DailyAccountBalance where nAccountID=" + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(qtai.getStartDate())) + "','yyyy-mm-dd') \n");

			conn = getConnection();
			log.print("取得期初余额SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				dReturn = rs.getDouble("Balance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	
	/**
	 * 取得账户的 可用余额=在“查询终止日期”之前，该账户的当前余额-累计冻结金额+累计解冻金额。
	 */
	public double queryAccountCanUseBalance(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			if(qtai.getEndDate().equals(Env.getSystemDate(qtai.getOfficeID(),qtai.getCurrencyID())))
			{
				//取当天余额
				m_sbSQL.append(" select (mBalance-mUncheckPaymentAmount-AC_mCapitalLimitAmount) CanUseBalance,nStatusID nSubAccountStatusID from sett_SubAccount where nAccountID = "+qtai.getAccountID()+" \n");
				if(qtai.getSubAccountID() > 0)
				{
					m_sbSQL.append(" and ID = "+qtai.getSubAccountID());
				}
			}
			else
			{
				//取历史余额
				m_sbSQL.append(" select (mBalance-mFreezeAmount) CanUseBalance,nSubAccountStatusID from sett_DailyAccountBalance where nAccountID=" + qtai.getAccountID() + " \n");
				m_sbSQL.append(" and dtDate = to_date('" + DataFormat.formatDate(qtai.getEndDate()) + "','yyyy-mm-dd') \n");
				if(qtai.getSubAccountID() > 0)
				{
					m_sbSQL.append(" and nSubAccountID = "+qtai.getSubAccountID());
				}
			}

			conn = getConnection();
			log.print("取得可用余额SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				long lSubAccountStatusID = rs.getLong("nSubAccountStatusID");
				double dCanUseBalance = rs.getDouble("CanUseBalance");
				if(lSubAccountStatusID == SETTConstant.SubAccountStatus.ONLYPAYFREEZE 
						|| lSubAccountStatusID == SETTConstant.SubAccountStatus.ALLFREEZE)
				{
					//该账户做过只收不付冻结或不收不付冻结，且未解冻，则可用余额=0
					dCanUseBalance = 0.0;
				}
				dReturn = dReturn + dCanUseBalance;
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	/*
	 * 根据结算的账户ID 得到 账户所对应的银行账户号
	 */
	public String getBankAccountNoByAccountID(long lAccountID) throws Exception
	{
		String strReturn = "";
		try
		{
			String strBankAccountNo = "";
			Sett_FilialeAccountSetDAO bankAccountDAO = new Sett_FilialeAccountSetDAO();
			
			FilialeAccountInfo[] infos = bankAccountDAO.findRefFilialeAccountInfoBySettAccountId(lAccountID);
			
			if(infos != null)
			{
				FilialeAccountInfo info = new FilialeAccountInfo();
				info = infos[0];
				strReturn = info.getBankAccountNo();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
				
		
		return strReturn;
	}
	public Collection queryTransAccountDetail(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select * \n");
			m_sbSQL.append(" from ");
			m_sbSQL.append(" ( ");
			if(1 == qtai.getIsIntrDate()){
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtInterestStart,'dd') ExecuteDay,to_char(trans.dtInterestStart,'mm') ExecuteMonth,to_char(trans.dtInterestStart,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,1 Direction, \n");
			}
			else{
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtExecute,'dd') ExecuteDay,to_char(trans.dtExecute,'mm') ExecuteMonth,to_char(trans.dtExecute,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,1 Direction, \n");
			}
			m_sbSQL.append(" decode(trans.nTransactionTypeID, \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKRECEIVE+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKPAY+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.INTERNALVIREMENT+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(" subaccount.af_sdepositno) BillNo, \n");
			m_sbSQL.append(" trans.sBankChequeNo BankChequeNo,trans.mAmount PayAmount,0 ReceiveAmount,account.nAccountTypeID AccountTypeID, \n");
			//为账户对账单信息查询 所加
			m_sbSQL.append(" trans.OPPACCOUNTNO OPPACCOUNTNO,trans.OPPACCOUNTNAME OPPACCOUNTNAME \n");
			//
			m_sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
			m_sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
			m_sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 1 \n");
			m_sbSQL.append(" and account.nCurrencyID=" + qtai.getCurrencyID() + " \n");
			m_sbSQL.append(" and subaccount.nAccountID = " + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
			if(1 == qtai.getIsIntrDate())
			{
				m_sbSQL.append(
						" and trans.dtintereststart between to_date('"
							+ DataFormat.formatDate(qtai.getStartDate())
							+ "','yyyy-mm-dd') and to_date('"
							+ DataFormat.formatDate(qtai.getEndDate())
							+ "','yyyy-mm-dd') \n");
			}
			else
			{
				m_sbSQL.append(
					" and trans.dtExecute between to_date('"
						+ DataFormat.formatDate(qtai.getStartDate())
						+ "','yyyy-mm-dd') and to_date('"
						+ DataFormat.formatDate(qtai.getEndDate())
						+ "','yyyy-mm-dd') \n");
			}
			if(!qtai.getAccountStatusIDs().equals("")&&qtai.getAccountStatusIDs() != null)
			{
				m_sbSQL.append(" and account.nStatusID in (" + qtai.getAccountStatusIDs() + ")");
			}
			if (qtai.getContractCode() != null && qtai.getContractCode().length() > 0)
			{
				m_sbSQL.append(" and contractform.sContractCode ='" + qtai.getContractCode() + "'");
			}
			if (qtai.getLoanNoteNo() != null && qtai.getLoanNoteNo().length() > 0)
			{
				m_sbSQL.append(" and loanpayform.sCode ='" + qtai.getLoanNoteNo() + "'");
			}
			if (qtai.getSubAccountID() > 0)
			{
				m_sbSQL.append(" and subaccount.ID=" + qtai.getSubAccountID());
			}

			m_sbSQL.append(" union all \n");
			if(1 == qtai.getIsIntrDate()){
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtInterestStart,'dd') ExecuteDay,to_char(trans.dtInterestStart,'mm') ExecuteMonth,to_char(trans.dtInterestStart,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,2 Direction, \n");
			}
			else{
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtExecute,'dd') ExecuteDay,to_char(trans.dtExecute,'mm') ExecuteMonth,to_char(trans.dtExecute,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,2 Direction, \n");
			}
			m_sbSQL.append(" decode(trans.nTransactionTypeID, \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKRECEIVE+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKPAY+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.INTERNALVIREMENT+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(" subaccount.af_sdepositno) ||  \n");
			m_sbSQL.append(" decode(contractform.sContractCode,null,'',contractform.sContractCode || '(' || loanpayform.sCode || ')') BillNo, \n");
			m_sbSQL.append(" trans.sBankChequeNo BankChequeNo,0 PayAmount,trans.mAmount ReceiveAmount,account.nAccountTypeID AccountTypeID, \n");
			//为账户对账单信息查询 所加
			m_sbSQL.append(" trans.OPPACCOUNTNO OPPACCOUNTNO,trans.OPPACCOUNTNAME OPPACCOUNTNAME \n");
			//
			m_sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
			m_sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
			m_sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 2 \n");
			m_sbSQL.append(" and account.nCurrencyID=" + qtai.getCurrencyID() + " \n");
			m_sbSQL.append(" and subaccount.nAccountID = " + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
			//m_sbSQL.append(" and transCurrencyDeposit.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
			//m_sbSQL.append(" and transCurrencyDeposit.nCurrencyID = "+qtai.getOfficeID()+" \n");
			//m_sbSQL.append(" and transCurrencyDeposit.nOfficeID = "+qtai.getCurrencyID()+" \n");
			
			//Boxu Add 2008年12月10日 过滤交易方向为贷方，金额为"0"的交易，为了解决账户对账单-账户明细查询中出现贷方借方都为空的记录
			m_sbSQL.append(" and trans.mamount != 0 ");
			
			if(1 == qtai.getIsIntrDate())
			{
				m_sbSQL.append(
						" and trans.dtintereststart between to_date('"
							+ DataFormat.formatDate(qtai.getStartDate())
							+ "','yyyy-mm-dd') and to_date('"
							+ DataFormat.formatDate(qtai.getEndDate())
							+ "','yyyy-mm-dd') \n");
			}
			else
			{
				m_sbSQL.append(
					" and trans.dtExecute between to_date('"
						+ DataFormat.formatDate(qtai.getStartDate())
						+ "','yyyy-mm-dd') and to_date('"
						+ DataFormat.formatDate(qtai.getEndDate())
						+ "','yyyy-mm-dd') \n");
			}
			if(!qtai.getAccountStatusIDs().equals("")&&qtai.getAccountStatusIDs() != null)
			{
				m_sbSQL.append(" and account.nStatusID in (" + qtai.getAccountStatusIDs() + ")");
			}
			if (qtai.getContractCode() != null && qtai.getContractCode().length() > 0)
			{
				m_sbSQL.append(" and contractform.sContractCode ='" + qtai.getContractCode() + "'");
			}
			if (qtai.getLoanNoteNo() != null && qtai.getLoanNoteNo().length() > 0)
			{
				m_sbSQL.append(" and loanpayform.sCode ='" + qtai.getLoanNoteNo() + "'");
			}
			if (qtai.getSubAccountID() > 0)
			{
				m_sbSQL.append(" and subaccount.ID=" + qtai.getSubAccountID());
			}

			m_sbSQL.append(" ) \n");

			//order by
			String strDesc = qtai.getDesc() == 1 ? " asc " : "";
			switch ((int) qtai.getOrderField())
			{
				case OrderBy_TransNo :
					if(qtai.getIsIntrDate() == 1){
						m_sbSQL.append(" \n order by InterestStartDate,transNo" + strDesc);
					}
					else{
						m_sbSQL.append(" \n order by ExecuteDate,transNo" + strDesc);
					}
					break;
			}

			conn = getConnection();
			log.print("对账单SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			
			rs = ps.executeQuery();
			//get all the ResultSet elements
			//取得期初余额
			double dBalance = 0.0;
			OBAccountBalanceQueryDao bqDao = new OBAccountBalanceQueryDao();
			if(1 == qtai.getIsIntrDate() ){
				dBalance = queryTransAccountBalanceForIntr(qtai);
				
				if (qtai.getContractID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByContractIDForIntr(qtai); //合同的期初余额
				}
				if (qtai.getLoanNoteID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByLoanNoteIDForIntr(qtai); //放款单的期初余额
				}
			}
			else{
				dBalance = queryTransAccountBalance(qtai);
				
				if (qtai.getContractID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByContractID(qtai); //合同的期初余额
				}
				if (qtai.getLoanNoteID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByLoanNoteID(qtai); //放款单的期初余额
				}
			}

			
			//取得期初余额结束
			Timestamp tsLastDate = null; //每日合计 用
			double dDayPayBalance = 0.0; //每日付款合计
			double dDayReceiveBalance = 0.0; //每日收款合计
			while (rs.next())
			{
				if(1 == qtai.getIsIntrDate() ){
					if (tsLastDate != null && rs.getTimestamp("InterestStartDate") != null && !DataFormat.formatDate(tsLastDate).equals(DataFormat.formatDate(rs.getTimestamp("InterestStartDate"))))
					{
						log.print("in 每日合计");
						QueryTransAccountDetailResultInfo qtri2 = new QueryTransAccountDetailResultInfo();
						qtri2.setAbstract("<b>本日合计</b>");
						qtri2.setExecuteMonth(Long.valueOf(DataFormat.getMonthString(tsLastDate)).longValue() + 1);
						qtri2.setExecuteYear(Long.valueOf(DataFormat.getYearString(tsLastDate)).longValue());
						qtri2.setInterestStartDate(tsLastDate);
						qtri2.setTransTypeID(-1000); //表示为日合计
						qtri2.setPayAmount(dDayPayBalance);
						qtri2.setReceiveAmount(dDayReceiveBalance);
						qtri2.setBalance(dBalance);
						v.add(qtri2);
						dDayPayBalance = 0.0;
						dDayReceiveBalance = 0.0;
					}
				}
				else{
					if (tsLastDate != null && rs.getTimestamp("ExecuteDate") != null && !DataFormat.formatDate(tsLastDate).equals(DataFormat.formatDate(rs.getTimestamp("ExecuteDate"))))
					{
						log.print("in 每日合计");
						QueryTransAccountDetailResultInfo qtri2 = new QueryTransAccountDetailResultInfo();
						qtri2.setAbstract("<b>本日合计</b>");
						qtri2.setExecuteMonth(Long.valueOf(DataFormat.getMonthString(tsLastDate)).longValue() + 1);
						qtri2.setExecuteYear(Long.valueOf(DataFormat.getYearString(tsLastDate)).longValue());
						qtri2.setExecuteDate(tsLastDate);
						qtri2.setTransTypeID(-1000); //表示为日合计
						qtri2.setPayAmount(dDayPayBalance);
						qtri2.setReceiveAmount(dDayReceiveBalance);
						qtri2.setBalance(dBalance);
						v.add(qtri2);
						dDayPayBalance = 0.0;
						dDayReceiveBalance = 0.0;
					}
				}
				if(1 == qtai.getIsIntrDate() ){
					tsLastDate = rs.getTimestamp("InterestStartDate");
				}
				else{
					tsLastDate = rs.getTimestamp("ExecuteDate");
				}

				QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
				qtri.setAccountNo(qtai.getAccountNo());
				qtri.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				qtri.setExecuteDay(rs.getLong("ExecuteDay"));
				qtri.setExecuteMonth(rs.getLong("ExecuteMonth"));
				qtri.setExecuteYear(rs.getLong("ExecuteYear"));
				qtri.setSubAccountID(rs.getLong("NSUBACCOUNTID"));
				qtri.setTransNo(rs.getString("TransNo"));
				qtri.setTransTypeID(rs.getLong("TransTypeID"));
				qtri.setAbstract(rs.getString("Abstract"));
				qtri.setBillNo(rs.getString("BillNo"));
				qtri.setBankChequeNo(rs.getString("BankChequeNo"));
				qtri.setPayAmount(rs.getDouble("PayAmount"));
				qtri.setReceiveAmount(rs.getDouble("ReceiveAmount"));
				qtri.setGroupID(rs.getLong("GroupID"));
				qtri.setInterestStartDate(rs.getTimestamp("InterestStartDate"));
				qtri.setOppAccountID(rs.getLong("NOPPACCOUNTID"));
				//为账户对账单信息查询 所加
				qtri.setOppAccountNo(rs.getString("OPPACCOUNTNO"));
				qtri.setOppAccountName(rs.getString("OPPACCOUNTNAME"));
				//
				//qtri.setDepositNo(findDepositNoBySubAccountID(rs.getLong("NSUBACCOUNTID")));
				//qtri.setContractCode(findContractCodeBySubAccountID(rs.getLong("NSUBACCOUNTID")));
				qtri = findObNeedInfo(qtri);
				//取得每天的余额
				if (SETTConstant.AccountType.isLoanAccountType(rs.getLong("AccountTypeID")))
				{
					//如果是贷款的则借贷余额方向与其他账户类型相反
					dBalance = dBalance + DataFormat.formatDouble(qtri.getPayAmount(), 2) - DataFormat.formatDouble(qtri.getReceiveAmount(), 2);
				}
				else
				{
					dBalance = dBalance - DataFormat.formatDouble(qtri.getPayAmount(), 2) + DataFormat.formatDouble(qtri.getReceiveAmount(), 2);
				}
				dDayPayBalance = dDayPayBalance + DataFormat.formatDouble(qtri.getPayAmount(), 2);
				dDayReceiveBalance = dDayReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(), 2);
				qtri.setBalance(dBalance);

				v.add(qtri);
			}

			QueryTransAccountDetailResultInfo qtri3 = new QueryTransAccountDetailResultInfo();
			qtri3.setAbstract("<b>本日合计</b>");
			if(1 == qtai.getIsIntrDate() ){
				qtri3.setInterestStartDate(tsLastDate);
			}
			else{
				qtri3.setExecuteDate(tsLastDate);
			}
			if (tsLastDate != null)
			{
				long lExecuteMonth = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(5, 7)).longValue();
				long lExecuteYear = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(0, 4)).longValue();
				qtri3.setExecuteMonth(lExecuteMonth);
				qtri3.setExecuteYear(lExecuteYear);
				log.print("lExecuteMonth" + lExecuteMonth);
				log.print("setExecuteYear" + lExecuteYear);
				log.print("tsLastDate" + tsLastDate);
			}
			qtri3.setTransTypeID(-1000); //表示为日合计
			qtri3.setPayAmount(dDayPayBalance);
			qtri3.setReceiveAmount(dDayReceiveBalance);
			qtri3.setBalance(dBalance);
			log.print("本日合计");
			v.add(qtri3);

			/*TOCONFIG--TODELETE*/
			/*
			//国机财务 不需要 合计值
			if (!Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
			{
				v.add(qtri3);
			}
			*/
			/*TOCONFIG-END*/
			
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return v.size() > 0 ? v : null;
	}

	/**
	 * 查询网银所需数据
	 * Create Date: 2003-8-13
	 * @param qtri 查询结果集
	 * @return qtri 结果集
	 * @exception Exception
	 */
	public QueryTransAccountDetailResultInfo findObNeedInfo(QueryTransAccountDetailResultInfo qtri) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();

			String strSQL = "";
			strSQL = " select AF_SDEPOSITNO from SETT_SUBACCOUNT   where   ID = " + qtri.getSubAccountID();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				qtri.setDepositNo(rs.getString("AF_SDEPOSITNO"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			strSQL = "";
			strSQL =
				" select c.SCONTRACTCODE SCONTRACTCODE from SETT_SUBACCOUNT a,LOAN_PAYFORM b, LOAN_CONTRACTFORM c  where   a.ID = "
					+ qtri.getSubAccountID()
					+ " and a.AL_NLOANNOTEID = b.id and b.NCONTRACTID = c.id";

			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				qtri.setContractCode(rs.getString("SCONTRACTCODE"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;

			strSQL = "";
			strSQL = "select sextaccountno,sextclientname from SETT_TRANSCURRENTDEPOSIT  where stransno ='"+ qtri.getTransNo()+"'";

			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{	
				if(qtri.getOppAccountNo()==null||qtri.getOppAccountNo().equals(""))
				{
					qtri.setOppAccountNo(rs.getString("sextaccountno"));
					qtri.setOppAccountName(rs.getString("sextclientname"));
				}
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
			e.printStackTrace();
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
				e.printStackTrace();
				throw e;
			}
		}
		return qtri;
	}
	public Collection queryOrganizationTransAccountDetail(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select * \n");
			m_sbSQL.append(" from ");
			m_sbSQL.append(" ( ");
			if(1 == qtai.getIsIntrDate()){
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtInterestStart,'dd') ExecuteDay,to_char(trans.dtInterestStart,'mm') ExecuteMonth,to_char(trans.dtInterestStart,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,1 Direction, \n");
			}
			else{
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtExecute,'dd') ExecuteDay,to_char(trans.dtExecute,'mm') ExecuteMonth,to_char(trans.dtExecute,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,1 Direction, \n");
			}
			m_sbSQL.append(" decode(trans.nTransactionTypeID, \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKRECEIVE+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKPAY+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.INTERNALVIREMENT+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(" subaccount.af_sdepositno) BillNo, \n");
			m_sbSQL.append(" trans.sBankChequeNo BankChequeNo,trans.mAmount PayAmount,0 ReceiveAmount,account.nAccountTypeID AccountTypeID, \n");
			//为账户对账单信息查询 所加
			m_sbSQL.append(" trans.OPPACCOUNTNO OPPACCOUNTNO,trans.OPPACCOUNTNAME OPPACCOUNTNAME \n");
			//
			m_sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
			m_sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
			m_sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 1 \n");
			//m_sbSQL.append(" and account.nOfficeID=" + qtai.getOfficeID() + " and account.nCurrencyID=" + qtai.getCurrencyID() + " \n");
			m_sbSQL.append("  and account.nCurrencyID=" + qtai.getCurrencyID() + " \n");
			m_sbSQL.append(" and subaccount.nAccountID = " + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
			if(1 == qtai.getIsIntrDate())
			{
				m_sbSQL.append(
						" and trans.dtintereststart between to_date('"
							+ DataFormat.formatDate(qtai.getStartDate())
							+ "','yyyy-mm-dd') and to_date('"
							+ DataFormat.formatDate(qtai.getEndDate())
							+ "','yyyy-mm-dd') \n");
			}
			else
			{
				m_sbSQL.append(
					" and trans.dtExecute between to_date('"
						+ DataFormat.formatDate(qtai.getStartDate())
						+ "','yyyy-mm-dd') and to_date('"
						+ DataFormat.formatDate(qtai.getEndDate())
						+ "','yyyy-mm-dd') \n");
			}
			if(!qtai.getAccountStatusIDs().equals("")&&qtai.getAccountStatusIDs() != null)
			{
				m_sbSQL.append(" and account.nStatusID in (" + qtai.getAccountStatusIDs() + ")");
			}
			if (qtai.getContractCode() != null && qtai.getContractCode().length() > 0)
			{
				m_sbSQL.append(" and contractform.sContractCode ='" + qtai.getContractCode() + "'");
			}
			if (qtai.getLoanNoteNo() != null && qtai.getLoanNoteNo().length() > 0)
			{
				m_sbSQL.append(" and loanpayform.sCode ='" + qtai.getLoanNoteNo() + "'");
			}
			if (qtai.getSubAccountID() > 0)
			{
				m_sbSQL.append(" and subaccount.ID=" + qtai.getSubAccountID());
			}

			m_sbSQL.append(" union all \n");
			if(1 == qtai.getIsIntrDate()){
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtInterestStart,'dd') ExecuteDay,to_char(trans.dtInterestStart,'mm') ExecuteMonth,to_char(trans.dtInterestStart,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,2 Direction, \n");
			}
			else{
				m_sbSQL.append(" select trans.NSUBACCOUNTID NSUBACCOUNTID,trans.NOPPACCOUNTID NOPPACCOUNTID,trans.nGroup GroupID,trans.sTransNo TransNo,trans.nTransactionTypeID TransTypeID,trans.dtExecute ExecuteDate,to_char(trans.dtExecute,'dd') ExecuteDay,to_char(trans.dtExecute,'mm') ExecuteMonth,to_char(trans.dtExecute,'yyyy') ExecuteYear,trans.sAbstract Abstract,trans.dtInterestStart InterestStartDate,2 Direction, \n");
			}
			m_sbSQL.append(" decode(trans.nTransactionTypeID, \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKRECEIVE+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.BANKPAY+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(SETTConstant.TransactionType.INTERNALVIREMENT+",decode(trans.sBillNo,null,transCurrencyDeposit.SCONSIGNVOUCHERNO,trans.sBillNo), \n");
			m_sbSQL.append(" subaccount.af_sdepositno) ||  \n");
			m_sbSQL.append(" decode(contractform.sContractCode,null,'',contractform.sContractCode || '(' || loanpayform.sCode || ')') BillNo, \n");
			m_sbSQL.append(" trans.sBankChequeNo BankChequeNo,0 PayAmount,trans.mAmount ReceiveAmount,account.nAccountTypeID AccountTypeID, \n");
			//为账户对账单信息查询 所加
			m_sbSQL.append(" trans.OPPACCOUNTNO OPPACCOUNTNO,trans.OPPACCOUNTNAME OPPACCOUNTNAME \n");
			//
			m_sbSQL.append(" from sett_TransAccountDetail trans,sett_Account account,sett_SubAccount subaccount,Loan_Contractform contractform,Loan_PayForm loanpayform, SETT_TRANSCURRENTDEPOSIT transCurrencyDeposit \n");
			m_sbSQL.append(" where subaccount.nAccountID = account.ID and trans.nSubAccountID = subaccount.ID and subaccount.AL_NLOANNOTEID = loanpayform.ID(+) and loanpayform.nContractID = contractform.ID(+) \n");
			m_sbSQL.append(" and trans.nStatusID=" + SETTConstant.TransactionStatus.CHECK + " and trans.nTransDirection = 2 \n");
			//m_sbSQL.append(" and account.nOfficeID=" + qtai.getOfficeID() + " and account.nCurrencyID=" + qtai.getCurrencyID() + " \n");
			m_sbSQL.append("  and account.nCurrencyID=" + qtai.getCurrencyID() + " \n");
			m_sbSQL.append(" and subaccount.nAccountID = " + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and transCurrencyDeposit.sTransNo(+) = trans.sTransNo \n");
			//m_sbSQL.append(" and transCurrencyDeposit.nStatusID = "+SETTConstant.TransactionStatus.CHECK+" \n");
			//m_sbSQL.append(" and transCurrencyDeposit.nCurrencyID = "+qtai.getOfficeID()+" \n");
			//m_sbSQL.append(" and transCurrencyDeposit.nOfficeID = "+qtai.getCurrencyID()+" \n");
			
			//Boxu Add 2008年12月10日 过滤交易方向为贷方，金额为"0"的交易，为了解决账户对账单-账户明细查询中出现贷方借方都为空的记录
			m_sbSQL.append(" and trans.mamount != 0 ");
			
			if(1 == qtai.getIsIntrDate())
			{
				m_sbSQL.append(
						" and trans.dtintereststart between to_date('"
							+ DataFormat.formatDate(qtai.getStartDate())
							+ "','yyyy-mm-dd') and to_date('"
							+ DataFormat.formatDate(qtai.getEndDate())
							+ "','yyyy-mm-dd') \n");
			}
			else
			{
				m_sbSQL.append(
					" and trans.dtExecute between to_date('"
						+ DataFormat.formatDate(qtai.getStartDate())
						+ "','yyyy-mm-dd') and to_date('"
						+ DataFormat.formatDate(qtai.getEndDate())
						+ "','yyyy-mm-dd') \n");
			}
			if(!qtai.getAccountStatusIDs().equals("")&&qtai.getAccountStatusIDs() != null)
			{
				m_sbSQL.append(" and account.nStatusID in (" + qtai.getAccountStatusIDs() + ")");
			}
			if (qtai.getContractCode() != null && qtai.getContractCode().length() > 0)
			{
				m_sbSQL.append(" and contractform.sContractCode ='" + qtai.getContractCode() + "'");
			}
			if (qtai.getLoanNoteNo() != null && qtai.getLoanNoteNo().length() > 0)
			{
				m_sbSQL.append(" and loanpayform.sCode ='" + qtai.getLoanNoteNo() + "'");
			}
			if (qtai.getSubAccountID() > 0)
			{
				m_sbSQL.append(" and subaccount.ID=" + qtai.getSubAccountID());
			}

			m_sbSQL.append(" ) \n");

			//order by
			String strDesc = qtai.getDesc() == 1 ? " asc " : "";
			switch ((int) qtai.getOrderField())
			{
				case OrderBy_TransNo :
					if(qtai.getIsIntrDate() == 1){
						m_sbSQL.append(" \n order by InterestStartDate,transNo" + strDesc);
					}
					else{
						m_sbSQL.append(" \n order by ExecuteDate,transNo" + strDesc);
					}
					break;
			}

			conn = getConnection();
			log.print("对账单SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			
			rs = ps.executeQuery();
			//get all the ResultSet elements
			//取得期初余额
			double dBalance = 0.0;
			OBAccountBalanceQueryDao bqDao = new OBAccountBalanceQueryDao();
			if(1 == qtai.getIsIntrDate() ){
				dBalance = queryTransAccountBalanceForIntr(qtai);
				
				if (qtai.getContractID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByContractIDForIntr(qtai); //合同的期初余额
				}
				if (qtai.getLoanNoteID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByLoanNoteIDForIntr(qtai); //放款单的期初余额
				}
			}
			else{
				dBalance = queryOrganizationTransAccountBalance(qtai);
				
				if (qtai.getContractID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByContractID(qtai); //合同的期初余额
				}
				if (qtai.getLoanNoteID() > 0)
				{
					dBalance = bqDao.getLoanHisBalanceByLoanNoteID(qtai); //放款单的期初余额
				}
			}

			
			//取得期初余额结束
			Timestamp tsLastDate = null; //每日合计 用
			double dDayPayBalance = 0.0; //每日付款合计
			double dDayReceiveBalance = 0.0; //每日收款合计
			while (rs.next())
			{
				if(1 == qtai.getIsIntrDate() ){
					if (tsLastDate != null && rs.getTimestamp("InterestStartDate") != null && !DataFormat.formatDate(tsLastDate).equals(DataFormat.formatDate(rs.getTimestamp("InterestStartDate"))))
					{
						log.print("in 每日合计");
						QueryTransAccountDetailResultInfo qtri2 = new QueryTransAccountDetailResultInfo();
						qtri2.setAbstract("<b>本日合计</b>");
						qtri2.setExecuteMonth(Long.valueOf(DataFormat.getMonthString(tsLastDate)).longValue() + 1);
						qtri2.setExecuteYear(Long.valueOf(DataFormat.getYearString(tsLastDate)).longValue());
						qtri2.setInterestStartDate(tsLastDate);
						qtri2.setTransTypeID(-1000); //表示为日合计
						qtri2.setPayAmount(dDayPayBalance);
						qtri2.setReceiveAmount(dDayReceiveBalance);
						qtri2.setBalance(dBalance);
						v.add(qtri2);
						dDayPayBalance = 0.0;
						dDayReceiveBalance = 0.0;
					}
				}
				else{
					if (tsLastDate != null && rs.getTimestamp("ExecuteDate") != null && !DataFormat.formatDate(tsLastDate).equals(DataFormat.formatDate(rs.getTimestamp("ExecuteDate"))))
					{
						log.print("in 每日合计");
						QueryTransAccountDetailResultInfo qtri2 = new QueryTransAccountDetailResultInfo();
						qtri2.setAbstract("<b>本日合计</b>");
						qtri2.setExecuteMonth(Long.valueOf(DataFormat.getMonthString(tsLastDate)).longValue() + 1);
						qtri2.setExecuteYear(Long.valueOf(DataFormat.getYearString(tsLastDate)).longValue());
						qtri2.setExecuteDate(tsLastDate);
						qtri2.setTransTypeID(-1000); //表示为日合计
						qtri2.setPayAmount(dDayPayBalance);
						qtri2.setReceiveAmount(dDayReceiveBalance);
						qtri2.setBalance(dBalance);
						v.add(qtri2);
						dDayPayBalance = 0.0;
						dDayReceiveBalance = 0.0;
					}
				}
				if(1 == qtai.getIsIntrDate() ){
					tsLastDate = rs.getTimestamp("InterestStartDate");
				}
				else{
					tsLastDate = rs.getTimestamp("ExecuteDate");
				}

				QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
				qtri.setAccountNo(qtai.getAccountNo());
				qtri.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				qtri.setExecuteDay(rs.getLong("ExecuteDay"));
				qtri.setExecuteMonth(rs.getLong("ExecuteMonth"));
				qtri.setExecuteYear(rs.getLong("ExecuteYear"));
				qtri.setSubAccountID(rs.getLong("NSUBACCOUNTID"));
				qtri.setTransNo(rs.getString("TransNo"));
				qtri.setTransTypeID(rs.getLong("TransTypeID"));
				qtri.setAbstract(rs.getString("Abstract"));
				qtri.setBillNo(rs.getString("BillNo"));
				qtri.setBankChequeNo(rs.getString("BankChequeNo"));
				qtri.setPayAmount(rs.getDouble("PayAmount"));
				qtri.setReceiveAmount(rs.getDouble("ReceiveAmount"));
				qtri.setGroupID(rs.getLong("GroupID"));
				qtri.setInterestStartDate(rs.getTimestamp("InterestStartDate"));
				qtri.setOppAccountID(rs.getLong("NOPPACCOUNTID"));
				//为账户对账单信息查询 所加
				qtri.setOppAccountNo(rs.getString("OPPACCOUNTNO"));
				qtri.setOppAccountName(rs.getString("OPPACCOUNTNAME"));
				//
				//qtri.setDepositNo(findDepositNoBySubAccountID(rs.getLong("NSUBACCOUNTID")));
				//qtri.setContractCode(findContractCodeBySubAccountID(rs.getLong("NSUBACCOUNTID")));
				qtri = findObNeedInfo(qtri);
				//取得每天的余额
				if (SETTConstant.AccountType.isLoanAccountType(rs.getLong("AccountTypeID")))
				{
					//如果是贷款的则借贷余额方向与其他账户类型相反
					dBalance = dBalance + DataFormat.formatDouble(qtri.getPayAmount(), 2) - DataFormat.formatDouble(qtri.getReceiveAmount(), 2);
				}
				else
				{
					dBalance = dBalance - DataFormat.formatDouble(qtri.getPayAmount(), 2) + DataFormat.formatDouble(qtri.getReceiveAmount(), 2);
				}
				dDayPayBalance = dDayPayBalance + DataFormat.formatDouble(qtri.getPayAmount(), 2);
				dDayReceiveBalance = dDayReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(), 2);
				qtri.setBalance(dBalance);

				v.add(qtri);
			}

			QueryTransAccountDetailResultInfo qtri3 = new QueryTransAccountDetailResultInfo();
			qtri3.setAbstract("<b>本日合计</b>");
			if(1 == qtai.getIsIntrDate() ){
				qtri3.setInterestStartDate(tsLastDate);
			}
			else{
				qtri3.setExecuteDate(tsLastDate);
			}
			if (tsLastDate != null)
			{
				long lExecuteMonth = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(5, 7)).longValue();
				long lExecuteYear = Long.valueOf(DataFormat.formatDate(tsLastDate).substring(0, 4)).longValue();
				qtri3.setExecuteMonth(lExecuteMonth);
				qtri3.setExecuteYear(lExecuteYear);
				log.print("lExecuteMonth" + lExecuteMonth);
				log.print("setExecuteYear" + lExecuteYear);
				log.print("tsLastDate" + tsLastDate);
			}
			qtri3.setTransTypeID(-1000); //表示为日合计
			qtri3.setPayAmount(dDayPayBalance);
			qtri3.setReceiveAmount(dDayReceiveBalance);
			qtri3.setBalance(dBalance);
			log.print("本日合计");
			v.add(qtri3);

			/*TOCONFIG--TODELETE*/
			/*
			//国机财务 不需要 合计值
			if (!Env.getProjectName().equalsIgnoreCase(Constant.ProjectName.CNMEF))
			{
				v.add(qtri3);
			}
			*/
			/*TOCONFIG-END*/
			
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return v.size() > 0 ? v : null;
	}
	/**
	 * 取得账户的期初余额
	 */
	public double queryOrganizationTransAccountBalance(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			m_sbSQL.append(" select sum(mBalance) Balance from sett_DailyAccountBalance where nAccountID=" + qtai.getAccountID() + " \n");
			m_sbSQL.append(" and dtDate = to_date('" + DataFormat.formatDate(DataFormat.getPreviousDate(qtai.getStartDate())) + "','yyyy-mm-dd') \n");

			conn = getConnection();
			log.print("取得期初余额SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				dReturn = rs.getDouble("Balance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	/**
	 * 取得账户的 可用余额=在“查询终止日期”之前，该账户的当前余额-累计冻结金额+累计解冻金额。
	 */
	public double queryOrganizationAccountCanUseBalance(QueryTransAccountDetailWhereInfo qtai) throws Exception
	{
		double dReturn = 0.0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer m_sbSQL = null;
		m_sbSQL = new StringBuffer();
		try
		{
			// select 
			if(qtai.getEndDate().equals(Env.getSystemDate(qtai.getOfficeID(),qtai.getCurrencyID())))
			{
				//取当天余额
				m_sbSQL.append(" select (mBalance-mUncheckPaymentAmount-AC_mCapitalLimitAmount) CanUseBalance,nStatusID nSubAccountStatusID from sett_SubAccount where nAccountID = "+qtai.getAccountID()+" \n");
				if(qtai.getSubAccountID() > 0)
				{
					m_sbSQL.append(" and ID = "+qtai.getSubAccountID());
				}
			}
			else
			{
				//取历史余额
				m_sbSQL.append(" select (mBalance-mFreezeAmount) CanUseBalance,nSubAccountStatusID from sett_DailyAccountBalance where nAccountID=" + qtai.getAccountID() + " \n");
				m_sbSQL.append(" and dtDate = to_date('" + DataFormat.formatDate(qtai.getEndDate()) + "','yyyy-mm-dd') \n");
				if(qtai.getSubAccountID() > 0)
				{
					m_sbSQL.append(" and nSubAccountID = "+qtai.getSubAccountID());
				}
			}

			conn = getConnection();
			log.print("取得可用余额SQL:" + m_sbSQL.toString());
			ps = conn.prepareStatement(m_sbSQL.toString());
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				long lSubAccountStatusID = rs.getLong("nSubAccountStatusID");
				double dCanUseBalance = rs.getDouble("CanUseBalance");
				if(lSubAccountStatusID == SETTConstant.SubAccountStatus.ONLYPAYFREEZE 
						|| lSubAccountStatusID == SETTConstant.SubAccountStatus.ALLFREEZE)
				{
					//该账户做过只收不付冻结或不收不付冻结，且未解冻，则可用余额=0
					dCanUseBalance = 0.0;
				}
				dReturn = dReturn + dCanUseBalance;
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	

}
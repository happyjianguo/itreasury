
package com.iss.itreasury.settlement.bankinterface.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankinterface.dataentity.BankAccountTransInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryAccountTransSumInfo;
import com.iss.itreasury.settlement.bankinterface.dataentity.QueryBankAccountTransConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

/**
 * @author rongyang To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Sett_TransInfoOfBankAccountDAO extends SettlementDAO
{

	public final static int OrderBy_TRANSACCOUNTNO = 1; // 账号

	public final static int OrderBy_OPPACCOUNTNO = 2; // 对方账号

	public final static int OrderBy_DATE = 3; // 交易日期

	StringBuffer m_sbSelect = null;

	StringBuffer m_sbFrom = null;

	StringBuffer m_sbWhere = null;

	StringBuffer m_sbOrderBy = null;

	Log4j log = null;

	/**
	 * Constructor for Sett_TransInfoOfBankAccontDAO.
	 */
	public Sett_TransInfoOfBankAccountDAO()
	{
		super();
		log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		strTableName = "SETT_TRANSINFOOFBANKACCOUNT";
	}

	/**
	 * Constructor for Sett_TransInfoOfBankAccontDAO.
	 * 
	 * @param conn
	 */
	public Sett_TransInfoOfBankAccountDAO(Connection conn)
	{
		super(conn);
		log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
		strTableName = "SETT_TRANSINFOOFBANKACCOUNT";
	}

	/**
	 * 添加一条记录
	 * 
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long add(BankAccountTransInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement statement = null;
		ResultSet rs = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (ID, \n");
		buffer.append("SACCOUNTNO,\n");
		buffer.append("SACCOUNTNAME,\n");
		buffer.append("SBRANCHNAME,\n");
		buffer.append("SOPPOSITEACCOUNTNO,\n");
		buffer.append("SOPPOSITEACCOUNTNAME,\n");
		buffer.append("SOPPOSITEBRANCHNAME,\n");
		buffer.append("MAMOUNT,\n");
		buffer.append("NCURRENCYID,\n");
		buffer.append("NDIRECTION,\n");
		buffer.append("SCHECKNO,\n");
		buffer.append("SCHECKTYPE,\n");
		buffer.append("DTTRANSACTION,\n");
		buffer.append("STRANSACTIONTYPE,\n");
		buffer.append("SABSTRACT,\n");
		buffer.append("SCOMMENT,\n");
		buffer.append("NBANKTYPE, \n");

		// 新增字段
		buffer.append("STRANSNOOFBANK, \n");
		buffer.append("NISTOTURNIN, \n");
		buffer.append("NTURNINRESULT, \n");
		buffer.append("DTTURNIN, \n");
		buffer.append("NTURNINTRANSTYPE, \n");
		buffer.append("STRANSACTIONNO, \n");
		buffer.append("NISPRINTEDVOUCHER,\n");
		buffer.append("STURNINREMIND,\n");
		buffer.append("NISDELETEDBYBANK,\n");
		buffer.append("DTTURNINSEND )\n");

		buffer.append(" values(?,?,?,?,?,?,?,?,?,?,");
		buffer.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\n");

		log.info(buffer.toString());

		try
		{
			conn = this.getConnection();
			statement = conn.createStatement();
			pstmt = conn.prepareStatement(buffer.toString());

			long id = -1;
			// rs = conn.createStatement().executeQuery(
			// " select max(ID) ID from " + strTableName);

			rs = statement.executeQuery(" select max(ID) ID from "
					+ strTableName);
			if (rs.next())
			{
				id = (long) rs.getLong("ID");
				id++;
			}
			else
			{
				id = 1;
			}
			info.setID(id);

			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getID());
			pstmt.setString(nIndex++, info.getAccountNo());
			pstmt.setString(nIndex++, info.getAccountName());
			pstmt.setString(nIndex++, info.getBranchName());
			pstmt.setString(nIndex++, info.getOppositeAccountNo());
			pstmt.setString(nIndex++, info.getOppositeAccountName());
			pstmt.setString(nIndex++, info.getOppositeBranchName());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getDirection());
			pstmt.setString(nIndex++, info.getCheckNo());
			pstmt.setString(nIndex++, info.getCheckType());
			pstmt.setTimestamp(nIndex++, new Timestamp(info.getTransAction()
					.getTime()));
			pstmt.setString(nIndex++, info.getTransactionType());
			pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setString(nIndex++, info.getComment());
			pstmt.setLong(nIndex++, info.getBankType());

			// 新增字段设置
			pstmt.setString(nIndex++, info.getTransNoOfBank());
			pstmt.setLong(nIndex++, info.getIsToTurnIn());
			pstmt.setLong(nIndex++, info.getTurnInResult());
			pstmt.setTimestamp(nIndex++, info.getTurnInTime());
			pstmt.setLong(nIndex++, info.getTurnInTransType());
			pstmt.setString(nIndex++, info.getTransactionNO());
			pstmt.setLong(nIndex++, info.getIsPrintedVoucher());
			pstmt.setString(nIndex++, info.getSTurnInRemind());
			pstmt.setLong(nIndex++, info.getIsDeletedByBank());
			pstmt.setTimestamp(nIndex++, info.getTurnInSend());

			pstmt.execute();
			lReturn = id;
		}
		finally
		{
			this.cleanup(statement);
			this.cleanup(rs);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lReturn;
	}

	/**
	 * 根据条件查询符合条件的交易信息
	 * 
	 * @param condition
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByCondition(
			QueryBankAccountTransConditionInfo condition) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();
		try
		{
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			getAccountTransInfoSQL(condition);

			sbSQL = new StringBuffer();
			sbSQL.append(" select * ");
			sbSQL.append(" from " + this.m_sbFrom);
			sbSQL.append(" where " + this.m_sbWhere);
			sbSQL.append(this.m_sbOrderBy);

			log.info(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());

			rs = ps.executeQuery();
			while (rs.next())
			{
				BankAccountTransInfo tmp = getInfoFromRS(rs);
				list.add(tmp);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return list;
	}

	private void getAccountTransInfoSQL(QueryBankAccountTransConditionInfo qInfo)
	{
		m_sbSelect = new StringBuffer();

		// select
		m_sbSelect.append(" ID as id,  \n");
		m_sbSelect.append(" SACCOUNTNO as AccountNo,  \n");
		m_sbSelect.append(" SACCOUNTNAME as AccountName,  \n");
		m_sbSelect.append(" SBRANCHNAME as BankName,  \n");
		m_sbSelect.append(" SOPPOSITEACCOUNTNO as OppAccountNo,  \n");
		m_sbSelect.append(" SOPPOSITEACCOUNTNAME as OppAccountName,  \n");
		m_sbSelect.append(" SOPPOSITEBRANCHNAME as OppBankName,  \n");
		m_sbSelect.append(" MAMOUNT as Amount,  \n");
		m_sbSelect.append(" NDIRECTION as TransDirection,  \n");
		m_sbSelect.append(" DTTRANSACTION as TransactionDate,  \n");
		m_sbSelect.append(" SABSTRACT as Abstract,  \n");

		// 新增字段
		m_sbSelect.append(" STRANSNOOFBANK    as TransNoOfBank, \n");
		m_sbSelect.append(" NISTOTURNIN       as IsToTurnIn, \n");
		m_sbSelect.append(" NTURNINRESULT     as TurnInResult, \n");
		m_sbSelect.append(" DTTURNIN          as TurnInTime, \n");
		m_sbSelect.append(" NTURNINTRANSTYPE  as TurnInTransType, \n");
		m_sbSelect.append(" STRANSACTIONNO    as TransactionNO, \n");
		m_sbSelect.append(" NISPRINTEDVOUCHER as IsPrintedVoucher  \n");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(this.strTableName + " \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1=1 \n");
		if (qInfo.getCurrencyID() > 0)
		{
			m_sbWhere.append(" and nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		}
		if (qInfo.getBankType() > 0)
		{
			m_sbWhere.append(" and NBANKTYPE=" + qInfo.getBankType() + " \n");
		}
		if (qInfo.getTransDerection() > 0)
		{
			m_sbWhere.append(" and NDIRECTION=" + qInfo.getTransDerection()
					+ " \n");
		}
		if (qInfo.getAmountFrom() > 0)
		{
			m_sbWhere.append(" and mAmount>=" + qInfo.getAmountFrom() + " \n");
		}
		if (qInfo.getAmountTo() > 0)
		{
			m_sbWhere.append(" and mAmount<=" + qInfo.getAmountTo() + " \n");
		}
		if (qInfo.getStartDate() != null)
		{
			m_sbWhere
					.append(" and to_date(to_char(dtTransAction,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(qInfo.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (qInfo.getEndDate() != null)
		{
			m_sbWhere
					.append(" and to_date(to_char(dtTransAction,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(qInfo.getEndDate())
							+ "','yyyy-mm-dd')");
		}
		if (qInfo.getBankAccountNos() != null
				&& qInfo.getBankAccountNos().length > 0)
		{

			m_sbWhere.append(" and SACCOUNTNO in ( ");
			String strAccountNo = "";

			for (int i = 0; i < qInfo.getBankAccountNos().length; i++)
			{
				m_sbWhere.append("'" + qInfo.getBankAccountNos()[i] + "'");
				if (i < qInfo.getBankAccountNos().length - 1)
				{
					m_sbWhere.append(",");
				}
			}

			m_sbWhere.append(" )");
		}

		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = "";
		if (qInfo.getDesc() == 1)
		{
			strDesc = " desc ";
		}
		else
		{
			strDesc = " asc ";
		}
		switch ((int) qInfo.getOrder())
		{
			case OrderBy_TRANSACCOUNTNO :
				m_sbOrderBy.append(" \n order by ACCOUNTNO" + strDesc);
				break;
			case OrderBy_OPPACCOUNTNO :
				m_sbOrderBy.append(" \n order by OPPACCOUNTNO" + strDesc);
				break;
			case OrderBy_DATE :
				m_sbOrderBy.append(" \n order by TransactionDate" + strDesc);
				break;

		}
		log.debug("SQL=" + m_sbSelect.toString() + m_sbFrom.toString()
				+ m_sbWhere.toString());
	}

	/*
	 * 查询银行账户余额合计
	 */
	public QueryAccountTransSumInfo queryAccountTransInfoSum(
			QueryBankAccountTransConditionInfo qInfo) throws Exception
	{
		QueryAccountTransSumInfo sumObj = new QueryAccountTransSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//

		try
		{
			getAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select sum(round(MAMOUNT,2)) Amount \n";

			con = this.getConnection();
			m_sbWhere.append(" and NDIRECTION="
					+ SETTConstant.DebitOrCredit.DEBIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setDebitAmountSum(rs.getDouble("Amount"));

			}
			cleanup(rs);
			cleanup(ps);
			getAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select sum(round(MAMOUNT,2)) Amount \n";

			// con = this.getConnection();
			m_sbWhere.append(" and NDIRECTION="
					+ SETTConstant.DebitOrCredit.CREDIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setCreditAmountSum(rs.getDouble("Amount"));

			}
			cleanup(rs);
			cleanup(ps);
			getAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select count(id) id \n";

			// con = this.getConnection();
			m_sbWhere.append(" and NDIRECTION="
					+ SETTConstant.DebitOrCredit.DEBIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 笔数合计
				sumObj.setDebitCount(rs.getInt("id"));

			}
			cleanup(rs);
			cleanup(ps);
			getAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select count(id) id \n";

			// con = this.getConnection();
			m_sbWhere.append(" and NDIRECTION="
					+ SETTConstant.DebitOrCredit.CREDIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 笔数合计
				sumObj.setCreditCount(rs.getInt("id"));

			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;
	}

	/*
	 * 查询电厂账户余额合计
	 */
	public QueryAccountTransSumInfo queryFilialeAccountTransInfoSum(
			QueryBankAccountTransConditionInfo qInfo) throws Exception
	{
		QueryAccountTransSumInfo sumObj = new QueryAccountTransSumInfo();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String strSelect = "";
		String strDepositWhere = "";
		String strLoanWhere = "";
		String sql = "";
		//

		try
		{
			getFilialeAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select sum(round(a.MAMOUNT,2)) Amount \n";

			con = this.getConnection();
			m_sbWhere.append(" and a.NDIRECTION="
					+ SETTConstant.DebitOrCredit.DEBIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setDebitAmountSum(rs.getDouble("Amount"));

			}
			cleanup(rs);
			cleanup(ps);
			getFilialeAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select sum(round(a.MAMOUNT,2)) Amount \n";

			con = this.getConnection();
			m_sbWhere.append(" and a.NDIRECTION="
					+ SETTConstant.DebitOrCredit.CREDIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 金额合计
				sumObj.setCreditAmountSum(rs.getDouble("Amount"));

			}
			cleanup(rs);
			cleanup(ps);
			getFilialeAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select count(a.id) id \n";

			con = this.getConnection();
			m_sbWhere.append(" and a.NDIRECTION="
					+ SETTConstant.DebitOrCredit.DEBIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 笔数合计
				sumObj.setDebitCount(rs.getInt("id"));

			}
			cleanup(rs);
			cleanup(ps);
			getFilialeAccountTransInfoSQL(qInfo);
			// select
			strSelect = " select count(a.id) id \n";

			con = this.getConnection();
			m_sbWhere.append(" and a.NDIRECTION="
					+ SETTConstant.DebitOrCredit.CREDIT + " \n");
			sql = strSelect + " from " + m_sbFrom.toString() + " where "
					+ m_sbWhere.toString();

			log.info(sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				// 笔数合计
				sumObj.setCreditCount(rs.getInt("id"));

			}
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return sumObj;
	}

	/**
	 * 查询电厂账户历史明细
	 * 
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	private void getFilialeAccountTransInfoSQL(
			QueryBankAccountTransConditionInfo qInfo)
	{
		m_sbSelect = new StringBuffer();

		// select
		m_sbSelect.append(" a.ID as id,  \n");
		m_sbSelect.append(" a.SACCOUNTNO as AccountNo,  \n");
		// m_sbSelect.append(" b.sFilialeName as FilialeName, \n");
		m_sbSelect.append(" a.SACCOUNTNAME as AccountName,  \n");
		m_sbSelect.append(" a.SBRANCHNAME as BankName,  \n");
		m_sbSelect.append(" a.SOPPOSITEACCOUNTNO as OppAccountNo,  \n");
		m_sbSelect.append(" a.SOPPOSITEACCOUNTNAME as OppAccountName,  \n");
		m_sbSelect.append(" a.SOPPOSITEBRANCHNAME as OppBankName,  \n");
		m_sbSelect.append(" a.MAMOUNT as Amount,  \n");
		m_sbSelect.append(" a.NDIRECTION as TransDirection,  \n");
		m_sbSelect.append(" a.DTTRANSACTION as TransactionDate,  \n");
		m_sbSelect.append(" a.SABSTRACT as Abstract  \n");

		m_sbSelect.append(" a.STRANSNOOFBANK    as TransNoOfBank, \n");
		m_sbSelect.append(" a.NISTOTURNIN       as IsToTurnIn, \n");
		m_sbSelect.append(" a.NTURNINRESULT     as TurnInResult, \n");
		m_sbSelect.append(" a.DTTURNIN          as TurnInTime, \n");
		m_sbSelect.append(" a.NTURNINTRANSTYPE  as TurnInTransType, \n");
		m_sbSelect.append(" a.STRANSACTIONNO    as TransactionNO, \n");
		m_sbSelect.append(" a.NISPRINTEDVOUCHER as IsPrintedVoucher  \n");

		// from
		m_sbFrom = new StringBuffer();
		m_sbFrom
				.append(this.strTableName + " a,SETT_BANKACCOUNTOFFILIALE b \n");
		// where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" a.sAccountNo= b.sBankAccountNo \n");
		m_sbWhere.append(" and a.nCurrencyID=" + qInfo.getCurrencyID() + " \n");
		m_sbWhere.append(" and a.NBANKTYPE=" + qInfo.getBankType() + " \n");
		if (qInfo.getFilialeBankAccountType() > 0)
		{
			m_sbWhere.append(" and b.nAccountType="
					+ qInfo.getFilialeBankAccountType() + " \n");
		}
		if (qInfo.getUpClientID() > 0)
		{
			m_sbWhere.append(" and b.nUpClientID=" + qInfo.getUpClientID()
					+ " \n");
		}
		if (qInfo.getBankAccountNos() != null
				&& qInfo.getBankAccountNos().length > 0)
		{

			m_sbWhere.append(" and SACCOUNTNO in ( ");
			String strAccountNo = "";

			for (int i = 0; i < qInfo.getBankAccountNos().length; i++)
			{
				m_sbWhere.append("'" + qInfo.getBankAccountNos()[i] + "'");
				if (i < qInfo.getBankAccountNos().length - 1)
				{
					m_sbWhere.append(",");
				}
			}

			m_sbWhere.append(" )");
		}
		if (qInfo.getAmountFrom() > 0)
		{
			m_sbWhere
					.append(" and a.mAmount>=" + qInfo.getAmountFrom() + " \n");
		}
		if (qInfo.getAmountTo() > 0)
		{
			m_sbWhere.append(" and a.mAmount<=" + qInfo.getAmountTo() + " \n");
		}
		if (qInfo.getStartDate() != null)
		{
			m_sbWhere
					.append(" and to_date(to_char(a.dtTransaction,'yyyymmdd'),'yyyymmdd')>= to_date('"
							+ DataFormat.formatDate(qInfo.getStartDate())
							+ "','yyyy-mm-dd')");
		}
		if (qInfo.getEndDate() != null)
		{
			m_sbWhere
					.append(" and to_date(to_char(a.dtTransaction,'yyyymmdd'),'yyyymmdd')<= to_date('"
							+ DataFormat.formatDate(qInfo.getEndDate())
							+ "','yyyy-mm-dd')");
		}

		//
		m_sbOrderBy = new StringBuffer();
		String strDesc = "";
		if (qInfo.getDesc() == 1)
		{
			strDesc = " desc ";
		}
		else
		{
			strDesc = " asc ";
		}
		switch ((int) qInfo.getOrder())
		{
			case OrderBy_TRANSACCOUNTNO :
				m_sbOrderBy.append(" \n order by ACCOUNTNO" + strDesc);
				break;
			case OrderBy_OPPACCOUNTNO :
				m_sbOrderBy.append(" \n order by OPPACCOUNTNO" + strDesc);
				break;
			case OrderBy_DATE :
				m_sbOrderBy.append(" \n order by TransactionDate" + strDesc);
				break;

		}
		log.debug("SQL=" + m_sbSelect.toString() + m_sbFrom.toString()
				+ m_sbWhere.toString());
	}

	/**
	 * 查询银行账户（开户行）历史明细，返回PageLoader对象。 <br>
	 * 注意：返回结果的交易信息对象中属性不全，只页面使用，其他不应使用该方法。
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAccountTransInfo(
			QueryBankAccountTransConditionInfo qInfo) throws Exception
	{

		getAccountTransInfoSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.settlement.bankinterface.dataentity.QueryResultInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 得到电厂账户历史明细，返回PageLoader对象。 <br>
	 * 注意：返回结果的交易信息对象中属性不全，只页面使用，其他不应使用该方法。
	 * 
	 * @param qaci
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryFilialeAccountTransInfo(
			QueryBankAccountTransConditionInfo qInfo) throws Exception
	{

		getFilialeAccountTransInfoSQL(qInfo);
		//
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory
				.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader
				.initPageLoader(
						new AppContext(),
						m_sbFrom.toString(),
						m_sbSelect.toString(),
						m_sbWhere.toString(),
						(int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.settlement.bankinterface.dataentity.QueryResultInfo",
						null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	/**
	 * 查询指定账户，某一天的所有交易。
	 * 
	 * @param accountNo
	 * @param date
	 * @return BankAccontBalanceInfo
	 * @throws Exception
	 */
	/*
	 * public Collection findByAccountAndDate(String accountNo, Date date)
	 * throws Exception { Connection conn = null; PreparedStatement ps = null;
	 * ResultSet rs = null; StringBuffer sbSQL = null; ArrayList list = new
	 * ArrayList(); try { //get the connection from Database conn =
	 * getConnection(); //establish the query sql string sbSQL = new
	 * StringBuffer(); sbSQL.append(" select * from " + strTableName);
	 * sbSQL.append(" where SACCOUNTNO = ?"); sbSQL.append(" and DTTRANSACTION = ?
	 * "); log.info(sbSQL.toString()); ps =
	 * conn.prepareStatement(sbSQL.toString()); ps.setString(1, accountNo);
	 * ps.setDate(2, date); rs = ps.executeQuery(); while (rs.next()) {
	 * BankAccontTransInfo tmp = getInfoFromRS(rs); list.add(tmp); }
	 * cleanup(rs); cleanup(ps); cleanup(conn); } finally { cleanup(rs);
	 * cleanup(ps); cleanup(conn); } return list; }
	 */

	private BankAccountTransInfo getInfoFromRS(ResultSet rs) throws Exception
	{
		BankAccountTransInfo temp = new BankAccountTransInfo();

		temp.setID(rs.getLong("ID"));
		temp.setAccountNo(rs.getString("SACCOUNTNO"));
		temp.setAccountName(rs.getString("SACCOUNTNAME"));
		temp.setBranchName(rs.getString("SBRANCHNAME"));
		temp.setOppositeAccountNo(rs.getString("SOPPOSITEACCOUNTNO"));
		temp.setOppositeAccountName(rs.getString("SOPPOSITEACCOUNTNAME"));
		temp.setOppositeBranchName(rs.getString("SOPPOSITEBRANCHNAME"));
		temp.setAmount(rs.getDouble("MAMOUNT"));
		temp.setCurrencyID(rs.getLong("NCURRENCYID"));
		temp.setDirection(rs.getLong("NDIRECTION"));
		temp.setCheckNo(rs.getString("SCHECKNO"));
		temp.setCheckType(rs.getString("SCHECKTYPE"));
		temp
				.setTransAction(new Date(rs.getTimestamp("DTTRANSACTION")
						.getTime()));
		temp.setTransactionType(rs.getString("STRANSACTIONTYPE"));
		temp.setAbstract(rs.getString("SABSTRACT"));
		temp.setComment(rs.getString("SCOMMENT"));
		temp.setBankType(rs.getLong("NBANKTYPE"));

		// 新增字段
		temp.setTransNoOfBank(rs.getString("STRANSNOOFBANK"));
		temp.setIsToTurnIn(rs.getLong("NISTOTURNIN"));
		temp.setTurnInResult(rs.getLong("NTURNINRESULT"));
		temp.setTurnInTime(rs.getTimestamp("DTTURNIN"));
		temp.setTurnInTransType(rs.getLong("NTURNINTRANSTYPE"));
		temp.setTransactionNO(rs.getString("STRANSACTIONNO"));
		temp.setIsPrintedVoucher(rs.getLong("NISPRINTEDVOUCHER"));
		temp.setIsDeletedByBank(rs.getLong("NISDELETEDBYBANK"));
		temp.setSTurnInRemind(rs.getString("STURNINREMIND"));
		temp.setTurnInSend(rs.getTimestamp("DTTURNINSEND"));

		return temp;
	}

	/**
	 * 查询指定账户最早的交易日期。
	 * 
	 * @param accountNo
	 * @return BankAccontBalanceInfo
	 * @throws Exception
	 */
	public Date findBeginDateByAccount(String accountNo) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Date date = null;
		try
		{
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select min(DTTRANSACTION) DTTRANSACTION from "
					+ strTableName);
			sbSQL.append(" where SACCOUNTNO = ? ");

			log.info(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, accountNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				date = rs.getDate("DTTRANSACTION");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return date;
	}

	public BankAccountTransInfo findByID(long id) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankAccountTransInfo resultInfo = null;
		try
		{
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from " + strTableName);
			sbSQL.append(" where ID = ? ");

			log.info(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				resultInfo = getInfoFromRS(rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return resultInfo;
	}

	/**
	 * 删除指定账户、指定时间之后的所有交易记录
	 * 
	 * @param accountNo
	 * @param startDate
	 * @throws Exception
	 */
	public void delete(String accountNo, Date startDate) throws Exception
	{
		Connection conn = null;
		Statement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" delete " + strTableName);
			sbSQL.append(" where SACCOUNTNO = ");
			sbSQL.append(accountNo);
			sbSQL.append(" and DTTRANSACTION >= to_date('"
					+ DataFormat.formatDate(new Timestamp(startDate.getTime()))
					+ "','yyyy-mm-dd') ");

			log.info(sbSQL.toString());

			ps = conn.createStatement();

			ps.executeUpdate(sbSQL.toString());

			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
	}

	/**
	 * 删除指定id的交易记录
	 * 
	 * @param id
	 * @throws Exception
	 */
	public long delete(long id) throws Exception
	{
		Connection conn = null;
		Statement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" delete " + strTableName);
			sbSQL.append(" where ID  = " + id);

			log.info(sbSQL.toString());

			ps = conn.createStatement();

			ps.executeUpdate(sbSQL.toString());

			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return 0;
	}

	public static void main(String[] args)
	{
		try
		{
			// BankAccontTransInfo condition = new BankAccontTransInfo();
			//
			// condition.setAccountNo("0001");
			// condition.setAccountName("aaaa");
			// condition.setBranchName("bbbb");
			// condition.setOppositeAccountNo("0004");
			// condition.setOppositeAccountName("cccc");
			// condition.setOppositeBranchName("dddd");
			// condition.setAmount(500);
			// condition.setCurrencyID(1);
			// condition.setDirection(1);
			// condition.setCheckNo("abcd");
			// condition.setCheckType("004");
			// condition.setTransAction(new Date(104,3,30));
			// condition.setTransactionType("1");
			// condition.setAbstract("haha");
			// condition.setComment("success");
			// condition.setBankType(0);

			Sett_TransInfoOfBankAccountDAO dao = new Sett_TransInfoOfBankAccountDAO();
			BankAccountTransInfo[] resultinfo = null;
			resultinfo = dao.findTransInfoOfSend(1, 1, 2);

			// long flag = -1;
			// flag = dao.add(condition);
			// System.out.println(new Date(System.currentTimeMillis()));
			// System.out.println(
			// DataFormat.formatDate(
			// new Timestamp(
			// new Date(System.currentTimeMillis()).getTime())));
			// dao.delete(
			// "140644136221066002194",
			// new Date(System.currentTimeMillis()));

			// BankAccontTransInfo bai = dao.findByID(82);

			// Date date = dao.findBeginDateByAccount("0001");
			// System.out.println("\nAccountNO:0001 Date:"+date);

			// Collection col = dao.findByAccountAndDate("0001",new
			// Date(104,3,28));
			// BankAccontTransInfo[] inf = new BankAccontTransInfo[0];
			// inf = (BankAccontTransInfo[])col.toArray(inf);
			// if (inf != null && inf.length > 0)
			// {
			// for (int i = 0; i < inf.length; i++)
			// {
			// System.out.println("*Info"+(i+1)+": ");
			// System.out.println(" AccountNo: "+inf[i].getAccountNo());
			// System.out.println(" AccountName: "+inf[i].getAccountName());
			// System.out.println(" TransActionDate:
			// "+inf[i].getTransAction());
			// }
			// }
			// else
			// {
			// System.out.println("Info: null");
			// }

			// bai.setIsShadiness(2);
			// dao.update(bai);

			// bai = dao.findLast("0001");
			// dao.delete("0001",new Date(104,3,29));

			System.out.println("========= *success* =========");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/***************************************************************************
	 * 获取需要发送给计算系统的交易数据 判断条件是是否需要入账为入账、入账结果为初始值、入账日期为空、打印标志为未打印的交易
	 * 
	 * @param long
	 *            officeId,long currencyId
	 * @return BankAccontTransInfo[]
	 * @throws Exception
	 **************************************************************************/
	public BankAccountTransInfo[] findTransInfoOfSend(long officeId,
			long currencyId, long bankType) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		ResultSet rs = null;
		BankAccountTransInfo resultInfo = null;
		Vector v = new Vector();

		try
		{
			// get the connection from Database
			conn = getConnection();
			// 获取开机时间
			String systemDate = Env.getSystemDateString(officeId, currencyId);
			// establish the query sql string
			m_sbSelect = new StringBuffer();
			// select
			m_sbSelect.append(" select * from (");
			m_sbSelect.append(" select ");
			m_sbSelect.append(" t.ID ,  \n");
			m_sbSelect.append(" t.SACCOUNTNO  ,  \n");
			m_sbSelect.append(" t.SACCOUNTNAME ,  \n");
			m_sbSelect.append(" t.SBRANCHNAME ,  \n");
			m_sbSelect.append(" t.SOPPOSITEACCOUNTNO ,  \n");
			m_sbSelect.append(" t.SOPPOSITEACCOUNTNAME ,  \n");
			m_sbSelect.append(" t.SOPPOSITEBRANCHNAME ,  \n");
			m_sbSelect.append(" t.MAMOUNT ,  \n");
			m_sbSelect.append(" t.NCURRENCYID ,  \n");
			m_sbSelect.append(" t.NDIRECTION ,  \n");
			m_sbSelect.append(" t.SCHECKNO ,  \n");
			m_sbSelect.append(" t.SCHECKTYPE ,  \n");
			m_sbSelect.append(" t.DTTRANSACTION ,  \n");
			m_sbSelect.append(" t.STRANSACTIONTYPE ,  \n");
			m_sbSelect.append(" t.SABSTRACT ,  \n");
			m_sbSelect.append(" t.SCOMMENT ,  \n");
			m_sbSelect.append(" t.NBANKTYPE ,  \n");

			// 新增字段
			m_sbSelect.append(" t.STRANSNOOFBANK    , \n");
			m_sbSelect.append(" t.NISTOTURNIN       , \n");
			m_sbSelect.append(" t.NTURNINRESULT     , \n");
			m_sbSelect.append(" t.DTTURNIN          , \n");
			m_sbSelect.append(" t.NTURNINTRANSTYPE  , \n");
			m_sbSelect.append(" t.STRANSACTIONNO    , \n");
			m_sbSelect.append(" t.STURNINREMIND    , \n");
			m_sbSelect.append(" t.NISDELETEDBYBANK    , \n");
			m_sbSelect.append(" t.DTTURNINSEND    , \n");
			m_sbSelect.append(" t.NISPRINTEDVOUCHER   \n");
			m_sbSelect
					.append(" FROM SETT_TRANSINFOOFBANKACCOUNT t,SETT_BANKACCOUNTOFFILIALE b \n");
			m_sbSelect
					.append(" WHERE t.NISTOTURNIN = "
							+ Constant.TRUE
							+ " and t.nisdeletedbybank = "
							+ Constant.FALSE
							+ " and t.NTURNINRESULT = -1 and t.DTTURNIN is null and t.NISPRINTEDVOUCHER <> "
							+ Constant.TRUE
							+ " and t.SACCOUNTNO = b.SBANKACCOUNTNO and b.NCURRENCYID  = "
							+ currencyId + " and b.NOFFICEID  = " + officeId
							+ " and to_char(t.DTTRANSACTION,'yyyy-mm-dd') <= '"
							+ systemDate + "'");
			if (bankType > 0) {
				m_sbSelect.append(" and t.NBANKTYPE = "+ bankType + " \n");
			}
			m_sbSelect.append(" union \n");
			m_sbSelect.append(" select ");
			m_sbSelect.append(" s.ID ,  \n");
			m_sbSelect.append(" s.SACCOUNTNO ,  \n");
			m_sbSelect.append(" s.SACCOUNTNAME ,  \n");
			m_sbSelect.append(" s.SBRANCHNAME ,  \n");
			m_sbSelect.append(" s.SOPPOSITEACCOUNTNO ,  \n");
			m_sbSelect.append(" s.SOPPOSITEACCOUNTNAME ,  \n");
			m_sbSelect.append(" s.SOPPOSITEBRANCHNAME ,  \n");
			m_sbSelect.append(" s.MAMOUNT ,  \n");
			m_sbSelect.append(" s.NCURRENCYID ,  \n");
			m_sbSelect.append(" s.NDIRECTION ,  \n");
			m_sbSelect.append(" s.SCHECKNO ,  \n");
			m_sbSelect.append(" s.SCHECKTYPE ,  \n");
			m_sbSelect.append(" s.DTTRANSACTION ,  \n");
			m_sbSelect.append(" s.STRANSACTIONTYPE ,  \n");
			m_sbSelect.append(" s.SABSTRACT ,  \n");
			m_sbSelect.append(" s.SCOMMENT ,  \n");
			m_sbSelect.append(" s.NBANKTYPE ,  \n");

			// 新增字段
			m_sbSelect.append(" s.STRANSNOOFBANK    , \n");
			m_sbSelect.append(" s.NISTOTURNIN       , \n");
			m_sbSelect.append(" s.NTURNINRESULT     , \n");
			m_sbSelect.append(" s.DTTURNIN          , \n");
			m_sbSelect.append(" s.NTURNINTRANSTYPE  , \n");
			m_sbSelect.append(" s.STRANSACTIONNO    , \n");
			m_sbSelect.append(" s.STURNINREMIND    , \n");
			m_sbSelect.append(" s.NISDELETEDBYBANK    , \n");
			m_sbSelect.append(" s.DTTURNINSEND    , \n");
			m_sbSelect.append(" s.NISPRINTEDVOUCHER   \n");
			m_sbSelect
					.append(" FROM SETT_TRANSINFOOFBANKACCOUNT s,SETT_BRANCH d \n");
			m_sbSelect
					.append(" WHERE s.NISTOTURNIN = "
							+ Constant.TRUE
							+ " and s.nisdeletedbybank = "
							+ Constant.FALSE
							+ " and s.NTURNINRESULT = -1 and s.DTTURNIN is null and s.NISPRINTEDVOUCHER <> "
							+ Constant.TRUE
							+ " and d.NCURRENCYID  = "
							+ currencyId
							+ " and d.NOFFICEID  = "
							+ officeId
							+ " and to_char(s.DTTRANSACTION,'yyyy-mm-dd') <=  '"
							+ systemDate + "'");
			if (bankType > 0) {
				m_sbSelect.append(" and s.NBANKTYPE = "+ bankType + " \n");
			} 
			m_sbSelect.append(" ) order by NDIRECTION desc");

			log.info(m_sbSelect.toString());

			ps = conn.prepareStatement(m_sbSelect.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				// get the BankBillInfo from current ResultSet object
				resultInfo = getInfoFromRS(rs);
				v.add(resultInfo);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		if (v == null || v.size() == 0)
		{
			return null;
		}
		else
		{
			int size = v.size();
			BankAccountTransInfo[] infos = new BankAccountTransInfo[size];
			for (int i = 0; i < size; i++)
			{
				infos[i] = (BankAccountTransInfo) v.elementAt(i);
			}
			return infos;
		}
	}

	/**
	 * 更新数据库
	 * 
	 * @param BankAccontTransInfo
	 */
	public void update(BankAccountTransInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer m_sbSelect = new StringBuffer();
		m_sbSelect.append("update " + strTableName + " set \n");

		m_sbSelect.append(" SACCOUNTNO  = ? ,  \n");
		m_sbSelect.append(" SACCOUNTNAME  = ? ,  \n");
		m_sbSelect.append(" SBRANCHNAME  = ? ,  \n");
		m_sbSelect.append(" SOPPOSITEACCOUNTNO  = ? ,  \n");
		m_sbSelect.append(" SOPPOSITEACCOUNTNAME  = ? ,  \n");
		m_sbSelect.append(" SOPPOSITEBRANCHNAME  = ? ,  \n");
		m_sbSelect.append(" MAMOUNT  = ? ,  \n");
		m_sbSelect.append(" NCURRENCYID  = ? ,  \n");
		m_sbSelect.append(" NDIRECTION  = ? ,  \n");
		m_sbSelect.append(" SCHECKNO  = ? ,  \n");
		m_sbSelect.append(" SCHECKTYPE  = ? ,  \n");
		m_sbSelect.append(" DTTRANSACTION  = ? ,  \n");
		m_sbSelect.append(" STRANSACTIONTYPE  = ? ,  \n");
		m_sbSelect.append(" SABSTRACT  = ? ,  \n");
		m_sbSelect.append(" SCOMMENT  = ? ,  \n");
		m_sbSelect.append(" NBANKTYPE  = ? ,  \n");

		// 新增字段
		m_sbSelect.append(" STRANSNOOFBANK   = ? , \n");
		m_sbSelect.append(" NISTOTURNIN      = ?  , \n");
		m_sbSelect.append(" NTURNINRESULT    = ?  , \n");
		m_sbSelect.append(" DTTURNIN       = ?    , \n");
		m_sbSelect.append(" NTURNINTRANSTYPE  = ? , \n");
		m_sbSelect.append(" STRANSACTIONNO  = ?   , \n");
		m_sbSelect.append(" NISPRINTEDVOUCHER = ? , \n");
		m_sbSelect.append(" STURNINREMIND = ? ,  \n");
		m_sbSelect.append(" NISDELETEDBYBANK = ? ,  \n");
		m_sbSelect.append(" DTTURNINSEND =? \n");

		m_sbSelect.append("where ID=?\n");

		log.debug(m_sbSelect.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(m_sbSelect.toString());

			int nIndex = 1;

			pstmt.setString(nIndex++, info.getAccountNo());
			pstmt.setString(nIndex++, info.getAccountName());
			pstmt.setString(nIndex++, info.getBranchName());
			pstmt.setString(nIndex++, info.getOppositeAccountNo());
			pstmt.setString(nIndex++, info.getOppositeAccountName());
			pstmt.setString(nIndex++, info.getOppositeBranchName());
			pstmt.setDouble(nIndex++, info.getAmount());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setLong(nIndex++, info.getDirection());
			pstmt.setString(nIndex++, info.getCheckNo());
			pstmt.setString(nIndex++, info.getCheckType());
			pstmt.setTimestamp(nIndex++, new Timestamp(info.getTransAction()
					.getTime()));
			pstmt.setString(nIndex++, info.getTransactionType());
			pstmt.setString(nIndex++, info.getAbstract());
			pstmt.setString(nIndex++, info.getComment());
			pstmt.setLong(nIndex++, info.getBankType());
			pstmt.setString(nIndex++, info.getTransNoOfBank());
			pstmt.setLong(nIndex++, info.getIsToTurnIn());
			pstmt.setLong(nIndex++, info.getTurnInResult());
			if (info.getTurnInTime() != null)
			{
				pstmt.setDate(nIndex++,
						new Date(info.getTurnInTime().getTime()));
			}
			else
			{
				pstmt.setDate(nIndex++, null);
			}
			pstmt.setLong(nIndex++, info.getTurnInTransType());
			pstmt.setString(nIndex++, info.getTransactionNO());
			pstmt.setLong(nIndex++, info.getIsPrintedVoucher());
			pstmt.setString(nIndex++, info.getSTurnInRemind());
			pstmt.setLong(nIndex++, info.getIsDeletedByBank());
			pstmt.setTimestamp(nIndex++, info.getTurnInSend());

			pstmt.setLong(nIndex++, info.getID());

			pstmt.execute();

		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);

		}
	}
	
	
	/**
	 * 统计报表统计该银行账户下下属单位每天的上收笔数和总金额
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Collection bankAccountTransInfoReport(QueryBankAccountTransConditionInfo condition)throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		ArrayList list = new ArrayList();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			getAccountTransInfoSQL(condition);
			
			sbSQL = new StringBuffer();
		    sbSQL.append(" select to_date(to_char(dttransaction,'yyyy-mm-dd'),'yyyy-mm-dd') dttransaction,SOPPOSITEACCOUNTname,SOPPOSITEACCOUNTno,sum(mamount) sumamount,count(*) num ");
	        sbSQL.append(" from Sett_TransInfoOfBankAccount where nistoturnin = ").append(SETTConstant.TRUE).append(" and nbanktype = ").append(condition.getBankType());
	        if(condition.getBankType()>0)
	        {
	        	sbSQL.append(" and nBankType = " + condition.getBankType());
	        }
	        if(condition.getStartDate()!=null)
	        {
	        	sbSQL.append(" and to_date(to_char(dttransaction,'yyyymmdd'),'yyyymmdd') >= to_date('"+DataFormat.formatDate(condition.getStartDate())+"','yyyy-mm-dd')");
	        }
	        if(condition.getEndDate()!=null)
	        {
	        	sbSQL.append(" and to_date(to_char(dttransaction,'yyyymmdd'),'yyyymmdd') <= to_date('"+DataFormat.formatDate(condition.getEndDate())+"','yyyy-mm-dd')");
	        }
	        if(condition.getOppositeAccountNo()!=null&&!condition.getOppositeAccountNo().equals(""))
	        {
	        	sbSQL.append(" and SOPPOSITEACCOUNTno = '" + condition.getOppositeAccountNo() + "'");
	        }
	        sbSQL.append(" group by SOPPOSITEACCOUNTname,SOPPOSITEACCOUNTno,to_char(dttransaction,'yyyy-mm-dd') ");
	        sbSQL.append(" order by dttransaction ");

			log.info(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());
			
			rs = ps.executeQuery();
			while (rs.next())
			{
				BankAccountTransInfo tmp = new BankAccountTransInfo();
				tmp.setNumber(rs.getLong("num"));//数量
				tmp.setTransAction(rs.getDate("dtTransaction"));//交易时间
				tmp.setSumAmount(rs.getDouble("sumamount"));//金额总数
				tmp.setOppositeAccountName(rs.getString("SOPPOSITEACCOUNTname"));//电厂名称
				tmp.setOppositeAccountNo(rs.getString("SOPPOSITEACCOUNTno"));//电厂账户
				log.info("addaddadd"+tmp.getOppositeAccountNo());
				list.add(tmp);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return list;
	}

}
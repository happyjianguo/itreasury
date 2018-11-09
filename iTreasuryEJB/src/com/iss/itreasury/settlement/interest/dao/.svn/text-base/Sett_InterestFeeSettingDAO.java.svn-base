package com.iss.itreasury.settlement.interest.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.interest.dataentity.InterestFeeSettingInfo;
import com.iss.itreasury.settlement.interest.dataentity.QueryInterestFeeSettingInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

import com.iss.system.dao.PageLoader;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.AppContext;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_InterestFeeSettingDAO extends SettlementDAO
{
	private StringBuffer m_sbSelect = null;
	private StringBuffer m_sbFrom = null;
	private StringBuffer m_sbWhere = null;
	private StringBuffer m_sbOrderBy = null;

	/**
	 * Constructor for Sett_TransInterestFeeDAO.
	 */
	public Sett_InterestFeeSettingDAO()
	{
		super.strTableName = "SETT_INTERESTFEESETTING";
	}

	public Sett_InterestFeeSettingDAO(Connection conn)
	{
		super(conn);
		super.strTableName = "SETT_INTERESTFEESETTING";
	}

	public long add(InterestFeeSettingInfo info) throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			con = getConnection();

			info.setID(nextSequenceNo(con));

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(");

			strSQLBuffer.append("ID,");
			strSQLBuffer.append("NOFFICEID,");
			strSQLBuffer.append("NCURRENCYID,");
			strSQLBuffer.append("DTCALCULATE,");
			strSQLBuffer.append("DTINTEREST,");
			strSQLBuffer.append("DTEXECUTE,");
			strSQLBuffer.append("NINTERESTTYPE,");
			strSQLBuffer.append("NACCOUNTSTATUSID,");
			strSQLBuffer.append("NACCOUNTIDSTART,");
			strSQLBuffer.append("SACCOUNTNOSTART,");
			strSQLBuffer.append("NACCOUNTIDEND,");
			strSQLBuffer.append("SACCOUNTNOEND,");
			strSQLBuffer.append("NFIXEDDEPOSITIDSTART,");
			strSQLBuffer.append("SFIXEDDEPOSITFORMNOSTART,");
			strSQLBuffer.append("NFIXEDDEPOSITIDEND,");
			strSQLBuffer.append("SFIXEDDEPOSITFORMNOEND,");
			strSQLBuffer.append("NFIXEDDEPOSITINTERVALNUM,");
			strSQLBuffer.append("NCONTRACTIDSTART,");
			strSQLBuffer.append("SCONTRACTNOSTART,");
			strSQLBuffer.append("NCONTRACTIDEND,");
			strSQLBuffer.append("SCONTRACTNOEND,");
			strSQLBuffer.append("NLOANNOTEIDSTART,");
			strSQLBuffer.append("SLOANNOTESNOSTART,");
			strSQLBuffer.append("NLOANNOTEIDEND,");
			strSQLBuffer.append("SLOANNOTESNOEND,");
			strSQLBuffer.append("NLOANTYPEID,");
			strSQLBuffer.append("NLOANINTERVALNUMMONTH,");
			strSQLBuffer.append("NLOANINTERVALNUMYEAR,");
			strSQLBuffer.append("NCONSIGNCLIENTID,");
			strSQLBuffer.append("SCONSIGNERNAME,");
			strSQLBuffer.append("NLOANSTATUSID,");
			strSQLBuffer.append("NISCLEAR,");
			strSQLBuffer.append("NINPUTUSERID,");
			strSQLBuffer.append("DTINPUT,");
			strSQLBuffer.append("NISEXECUTE,");
			strSQLBuffer.append("NSTATUSID");

			strSQLBuffer.append(") VALUES \n");
			strSQLBuffer.append(
				"( ?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?) \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//ps will be modified in function addDatatoPrepareStatement 
			addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD);

			if (ps.executeUpdate() == 1)
			{
				lResult = info.getID();
			}

		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lResult;
	}

	private int addDatatoPrepareStatement(InterestFeeSettingInfo info, PreparedStatement stmt, int operation)
		throws Exception
	{

		PreparedStatement ps = stmt;
		int index = 1;

		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setTimestamp(index++, info.getCalculateDate());
		ps.setTimestamp(index++, info.getInterestDate());
		ps.setTimestamp(index++, info.getExecuteDate());
		ps.setLong(index++, info.getInterestType());
		ps.setLong(index++, info.getAccountStatusID());
		ps.setLong(index++, info.getAccountIDStart());
		ps.setString(index++, info.getAccountNoStart());
		ps.setLong(index++, info.getAccountIDEnd());
		ps.setString(index++, info.getAccountNoEnd());
		ps.setLong(index++, info.getFixedDepositIDStart());
		ps.setString(index++, info.getFixedDepositFormNoStart());
		ps.setLong(index++, info.getFixedDepositIDEnd());
		ps.setString(index++, info.getFixedDepositFormNoEnd());
		ps.setLong(index++, info.getFixedDepositIntervalNum());
		ps.setLong(index++, info.getContractIDStart());
		ps.setString(index++, info.getContractNoStart());
		ps.setLong(index++, info.getContractIDEnd());
		ps.setString(index++, info.getContractNoEnd());
		ps.setLong(index++, info.getLoanNoteIDStart());
		ps.setString(index++, info.getLoanNotesNoStart());
		ps.setLong(index++, info.getLoanNoteIDEnd());
		ps.setString(index++, info.getLoanNotesNoEnd());
		ps.setLong(index++, info.getLoanTypeID());
		ps.setLong(index++, info.getLoanIntervalNumMonth());
		ps.setLong(index++, info.getLoanIntervalNumYear());
		ps.setLong(index++, info.getConsignClientID());
		ps.setString(index++, info.getConsignerName());
		ps.setLong(index++, info.getLoanStatusID());
		ps.setLong(index++, info.getIsClear());
		ps.setLong(index++, info.getInputUserID());
		if (operation == DAO_OPERATION_ADD)
		{
			ps.setTimestamp(index++, info.getInputDate());
		}
		ps.setLong(index++, info.getIsExecute());
		ps.setLong(index++, info.getStatusID());

		return index;
	}

	public long delete(long id) throws Exception
	{
		//logic delete, so just update status as deleted
		this.updateStatus(id, SETTConstant.TransactionStatus.DELETED);
		return id;
	}

	/**
	 * 方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param sett_TransCurrentDepositInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findByConditions(InterestFeeSettingInfo conditionInfo, int orderByType, boolean isDesc)
		throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer(256);

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");

			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;

			StringBuffer strWhereSQLBuffer = new StringBuffer(128);

			if (conditionInfo.getID() != -1)
			{
				strWhereSQLBuffer.append(" AND ID = " + conditionInfo.getID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getOfficeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCurrencyID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCURRENCYID = " + conditionInfo.getCurrencyID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getExecuteDate() != null)
			{
				strWhereSQLBuffer.append(
					" AND DTEXECUTE = to_date('"
						+ DataFormat.getDateString(conditionInfo.getExecuteDate())
						+ "', 'yyyy-mm-dd') \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getInputUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NINPUTUSERID = " + conditionInfo.getInputUserID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getIsExecute() != -1)
			{
				strWhereSQLBuffer.append(" AND NISEXECUTE = " + conditionInfo.getIsExecute() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getStatusID() != -1)
			{
				strWhereSQLBuffer.append(" AND NSTATUSID = " + conditionInfo.getStatusID() + " \n");
				isNeedWhere = true;
			}

			if (isNeedWhere)
			{
				strSQLBuffer.append(" WHERE");
				//Cut first "AND"
				strSQLBuffer.append(strWhereSQLBuffer.substring(4));
			}

			boolean isNeedOrderBy = true;
			switch (orderByType)
			{
				default :
					{
						isNeedOrderBy = false;
					}
					break;
			}

			if (isNeedOrderBy)
			{
				if (isDesc)
					strSQLBuffer.append(" DESC \n");
				else
					strSQLBuffer.append(" ASC \n");
			}

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			result = this.getInfoFromResultSet(rs);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}

	public PageLoader findByConditions(QueryInterestFeeSettingInfo conditionInfo) throws Exception
	{
		this.getFindByConditionsSQL(conditionInfo, -1, true);
		PageLoader pageLoader =
			(PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");

		pageLoader.initPageLoader(
			new AppContext(),
			this.m_sbFrom.toString(),
			this.m_sbSelect.toString(),
			this.m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.settlement.interest.dataentity.InterestFeeSettingInfo",
			null);
		pageLoader.setOrderBy(m_sbOrderBy.toString());

		return pageLoader;

	}

	private void getFindByConditionsSQL(QueryInterestFeeSettingInfo conditionInfo, int orderByType, boolean isDesc)
	{
		this.m_sbSelect = new StringBuffer(64);
		this.m_sbFrom = new StringBuffer(32);
		this.m_sbWhere = new StringBuffer(64);
		this.m_sbOrderBy = new StringBuffer(32);

		this.m_sbSelect.append(
			"ID as ID, \n"
				+ "NOFFICEID as OfficeID, \n"
				+ "NCURRENCYID as CurrencyID, \n"
				+ "DTCALCULATE as CalculateDate, \n"
				+ "NINTERESTTYPE as InterestType, \n"
				+ "SACCOUNTNOSTART as AccountNoStart, \n"
				+ "SACCOUNTNOEND as AccountNoEnd, \n"
				+ "NINPUTUSERID as InputUserID, \n"
				+ "DTINPUT as InputDate, \n"
				+ "NISEXECUTE as IsExecute \n");

		this.m_sbFrom.append(strTableName);

		//flag for deciding whether there is WHERE in query string
		boolean isNeedWhere = false;

		if (conditionInfo.getOfficeID() != -1)
		{
			this.m_sbWhere.append(" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
			isNeedWhere = true;
		}

		if (conditionInfo.getCurrencyID() != -1)
		{
			this.m_sbWhere.append(" AND NCURRENCYID = " + conditionInfo.getCurrencyID() + " \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getCalculateDateStart() != null)
		{
			this.m_sbWhere.append(
				" AND DTCALCULATE >= to_date('"
					+ DataFormat.formatDate(conditionInfo.getCalculateDateStart(), DataFormat.FMT_DATE_YYYYMMDD_HHMMSS)
					+ "', 'yyyy-mm-dd hh24:MI:ss') \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getCalculateDateEnd() != null)
		{
			this.m_sbWhere.append(
				" AND DTCALCULATE <= to_date('"
					+ DataFormat.formatDate(conditionInfo.getCalculateDateEnd(), DataFormat.FMT_DATE_YYYYMMDD_HHMMSS)
					+ "', 'yyyy-mm-dd hh24:MI:ss') \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getInterestTypes() != null && conditionInfo.getInterestTypes().length > 0)
		{
			this.m_sbWhere.append(" AND NINTERESTTYPE in(" + conditionInfo.getInterestTypes()[0]);
			for (int i = 1; i < conditionInfo.getInterestTypes().length; i++)
			{
				this.m_sbWhere.append(" ," + conditionInfo.getInterestTypes()[i]);
			}
			this.m_sbWhere.append(") \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getAccountNoStart() != null && conditionInfo.getAccountNoStart().length() > 0)
		{
			this.m_sbWhere.append(" AND SACCOUNTNOSTART >= '" + conditionInfo.getAccountNoStart() + "' \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getAccountNoEnd() != null && conditionInfo.getAccountNoEnd().length() > 0)
		{
			this.m_sbWhere.append(" AND SACCOUNTNOEND <= '" + conditionInfo.getAccountNoEnd() + "' \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getContractNoStart() != null && conditionInfo.getContractNoStart().length() > 0)
		{
			this.m_sbWhere.append(" AND SCONTRACTNOSTART >= '" + conditionInfo.getContractNoStart() + "' \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getContractNoEnd() != null && conditionInfo.getContractNoEnd().length() > 0)
		{
			this.m_sbWhere.append(" AND SCONTRACTNOEND <= '" + conditionInfo.getContractNoEnd() + "' \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getLoanNotesNoStart() != null && conditionInfo.getLoanNotesNoStart().length() > 0)
		{
			this.m_sbWhere.append(" AND SLOANNOTESNOSTART >= '" + conditionInfo.getLoanNotesNoStart() + "' \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getLoanNotesNoEnd() != null && conditionInfo.getLoanNotesNoEnd().length() > 0)
		{
			this.m_sbWhere.append(" AND SLOANNOTESNOEND <= '" + conditionInfo.getLoanNotesNoEnd() + "' \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getLoanTypeIDs() != null && conditionInfo.getLoanTypeIDs().length > 0)
		{
			this.m_sbWhere.append(" AND NLOANTYPEID IN (" + conditionInfo.getLoanTypeIDs()[0]);
			for (int i = 1; i < conditionInfo.getLoanTypeIDs().length; i++)
			{
				this.m_sbWhere.append(" ," + conditionInfo.getLoanTypeIDs()[i]);
			}
			this.m_sbWhere.append(") \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getLoanIntervalNumMonth() != -1)
		{
			this.m_sbWhere.append(" AND NLOANINTERVALNUMMONTH = " + conditionInfo.getLoanIntervalNumMonth() + " \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getLoanIntervalNumYear() != -1)
		{
			this.m_sbWhere.append(" AND NLOANINTERVALNUMYEAR = " + conditionInfo.getLoanIntervalNumYear() + " \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getConsignClientID() != -1)
		{
			this.m_sbWhere.append(" AND NCONSIGNCLIENTID = " + conditionInfo.getConsignClientID() + " \n");
			isNeedWhere = true;
		}
		if (conditionInfo.getStatusID() != -1)
		{
			this.m_sbWhere.append(" AND NSTATUSID = " + conditionInfo.getStatusID() + " \n");
			isNeedWhere = true;
		}

		if (isNeedWhere)
		{
			//Cut first "AND"
			this.m_sbWhere.delete(0, 4);
		}

		boolean isNeedOrderBy = true;
		switch (orderByType)
		{
			default :
				{
					this.m_sbOrderBy.append(" ORDER BY ID \n");
				}
				break;
		}

		if (isNeedOrderBy)
		{
			if (isDesc)
				this.m_sbOrderBy.append(" DESC \n");
			else
				this.m_sbOrderBy.append(" ASC \n");
		}
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public InterestFeeSettingInfo findByID(long ltransID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InterestFeeSettingInfo result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, ltransID);
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs);

			if (collTemp != null && collTemp.size() > 0)
			{
				Iterator itTemp = collTemp.iterator();
				result = (InterestFeeSettingInfo) itTemp.next();
			}

		}

		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return result;
	}

	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{

		ArrayList list = new ArrayList();
		while (rs.next())
		{
			InterestFeeSettingInfo info = new InterestFeeSettingInfo();

			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("NOFFICEID"));
			info.setCurrencyID(rs.getLong("NCURRENCYID"));
			info.setCalculateDate(rs.getTimestamp("DTCALCULATE"));
			info.setInterestDate(rs.getTimestamp("DTINTEREST"));
			info.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
			info.setInterestType(rs.getLong("NINTERESTTYPE"));
			info.setAccountStatusID(rs.getLong("NACCOUNTSTATUSID"));
			info.setAccountIDStart(rs.getLong("NACCOUNTIDSTART"));
			info.setAccountNoStart(rs.getString("SACCOUNTNOSTART"));
			info.setAccountIDEnd(rs.getLong("NACCOUNTIDEND"));
			info.setAccountNoEnd(rs.getString("SACCOUNTNOEND"));
			info.setFixedDepositIDStart(rs.getLong("NFIXEDDEPOSITIDSTART"));
			info.setFixedDepositFormNoStart(rs.getString("SFIXEDDEPOSITFORMNOSTART"));
			info.setFixedDepositIDEnd(rs.getLong("NFIXEDDEPOSITIDEND"));
			info.setFixedDepositFormNoEnd(rs.getString("SFIXEDDEPOSITFORMNOEND"));
			info.setFixedDepositIntervalNum(rs.getLong("NFIXEDDEPOSITINTERVALNUM"));
			info.setContractIDStart(rs.getLong("NCONTRACTIDSTART"));
			info.setContractNoStart(rs.getString("SCONTRACTNOSTART"));
			info.setContractIDEnd(rs.getLong("NCONTRACTIDEND"));
			info.setContractNoEnd(rs.getString("SCONTRACTNOEND"));
			info.setLoanNoteIDStart(rs.getLong("NLOANNOTEIDSTART"));
			info.setLoanNotesNoStart(rs.getString("SLOANNOTESNOSTART"));
			info.setLoanNoteIDEnd(rs.getLong("NLOANNOTEIDEND"));
			info.setLoanNotesNoEnd(rs.getString("SLOANNOTESNOEND"));
			info.setLoanTypeID(rs.getLong("NLOANTYPEID"));
			info.setLoanIntervalNumMonth(rs.getLong("NLOANINTERVALNUMMONTH"));
			info.setLoanIntervalNumYear(rs.getLong("NLOANINTERVALNUMYEAR"));
			info.setConsignClientID(rs.getLong("NCONSIGNCLIENTID"));
			info.setConsignerName(rs.getString("SCONSIGNERNAME"));
			info.setLoanStatusID(rs.getLong("NLOANSTATUSID"));
			info.setIsClear(rs.getLong("NISCLEAR"));
			info.setInputUserID(rs.getLong("NINPUTUSERID"));
			info.setInputDate(rs.getTimestamp("DTINPUT"));
			info.setIsExecute(rs.getLong("NISEXECUTE"));
			info.setStatusID(rs.getLong("NSTATUSID"));

			list.add(info);
		}

		list = (list.size() > 0) ? list : null;

		return list;
	}

	/**
	 * 方法说明：修改活期交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回活期交易记录ID
	 * @throws IException
	 */
	public long update(InterestFeeSettingInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + "\n");
			strSQLBuffer.append(" SET \n");

			strSQLBuffer.append("ID=?,");
			strSQLBuffer.append("NOFFICEID=?,");
			strSQLBuffer.append("NCURRENCYID=?,");
			strSQLBuffer.append("DTCALCULATE=?,");
			strSQLBuffer.append("DTINTEREST=?,");
			strSQLBuffer.append("DTEXECUTE=?,");
			strSQLBuffer.append("NINTERESTTYPE=?,");
			strSQLBuffer.append("NACCOUNTSTATUSID=?,");
			strSQLBuffer.append("NACCOUNTIDSTART=?,");
			strSQLBuffer.append("SACCOUNTNOSTART=?,");
			strSQLBuffer.append("NACCOUNTIDEND=?,");
			strSQLBuffer.append("SACCOUNTNOEND=?,");
			strSQLBuffer.append("NFIXEDDEPOSITIDSTART=?,");
			strSQLBuffer.append("SFIXEDDEPOSITFORMNOSTART=?,");
			strSQLBuffer.append("NFIXEDDEPOSITIDEND=?,");
			strSQLBuffer.append("SFIXEDDEPOSITFORMNOEND=?,");
			strSQLBuffer.append("NFIXEDDEPOSITINTERVALNUM=?,");
			strSQLBuffer.append("NCONTRACTIDSTART=?,");
			strSQLBuffer.append("SCONTRACTNOSTART=?,");
			strSQLBuffer.append("NCONTRACTIDEND=?,");
			strSQLBuffer.append("SCONTRACTNOEND=?,");
			strSQLBuffer.append("NLOANNOTEIDSTART=?,");
			strSQLBuffer.append("SLOANNOTESNOSTART=?,");
			strSQLBuffer.append("NLOANNOTEIDEND=?,");
			strSQLBuffer.append("SLOANNOTESNOEND=?,");
			strSQLBuffer.append("NLOANTYPEID=?,");
			strSQLBuffer.append("NLOANINTERVALNUMMONTH=?,");
			strSQLBuffer.append("NLOANINTERVALNUMYEAR=?,");
			strSQLBuffer.append("NCONSIGNCLIENTID=?,");
			strSQLBuffer.append("SCONSIGNERNAME=?,");
			strSQLBuffer.append("NLOANSTATUSID=?,");
			strSQLBuffer.append("NISCLEAR=?,");
			strSQLBuffer.append("NINPUTUSERID=?,");
			//strSQLBuffer.append("DTINPUT=?,");                                
			strSQLBuffer.append("NISEXECUTE=?,");
			strSQLBuffer.append("NSTATUSID=?");

			strSQLBuffer.append(" WHERE ID = ?");

			String strSQL = strSQLBuffer.toString();

			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			int lastIndex = addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			//Set value of where condition
			System.out.println(lastIndex);
			ps.setLong(lastIndex, info.getID());
			ps.executeUpdate();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return info.getID();
	}

	/**
	 * 方法说明：修改记录状态
	 * @see com.iss.itreasury.dao.SettlementDAO#updateStatus(long, long)
	 */
	public long updateStatus(long id, long statusid) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("NSTATUSID = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, statusid);
			ps.setLong(2, id);
			ps.executeUpdate();

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	
	/**
	 * 方法说明：修改执行状态
	 * @see com.iss.itreasury.dao.SettlementDAO#updateStatus(long, long)
	 */
	public long updateIsExecuteStatus(long id, long isExecute) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("UPDATE \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" SET \n");
			strSQLBuffer.append("NISEXECUTE = ? \n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, isExecute);
			ps.setLong(2, id);
			ps.executeUpdate();

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	/**
	 * 获得SequenceNo
	 * @param conn
	 * @return long
	 */
	private long nextSequenceNo(Connection conn)
	{
		PreparedStatement psSK = null;
		ResultSet rsSK = null;
		long lResult = -1;
		try
		{

			psSK = conn.prepareStatement("SELECT MAX(ID) + 1  AS MaxSequenceNo FROM " + this.strTableName);
			rsSK = psSK.executeQuery();
			if (rsSK.next())
			{
				lResult = rsSK.getLong(1);
			}
			if (lResult <= 0)
				lResult = 1;
		}
		catch (Exception e)
		{
			try
			{
				cleanup(rsSK);
				cleanup(psSK);
			}
			catch (Exception exec)
			{
				System.out.println(exec.getMessage());
			}
		}
		return lResult;
	}
}

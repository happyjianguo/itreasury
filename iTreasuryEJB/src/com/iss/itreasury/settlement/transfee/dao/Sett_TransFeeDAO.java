package com.iss.itreasury.settlement.transfee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import java.sql.Connection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_TransFeeDAO extends SettlementDAO
{
	public final static int ORDERBY_TRANSACTIONNOID = 1;
	public final static int ORDERBY_ACCOUNTNO = 2;
	public final static int ORDERBY_CLIENTNAME = 3;
	public final static int ORDERBY_FEETYPEID = 4;
	public final static int ORDERBY_FEEBANKID = 5;
	public final static int ORDERBY_AMOUNT = 6;
	public final static int ORDERBY_INTERESTSTARTDATE = 7;
	public final static int ORDERBY_EXECUTEDATEDATE = 8;
	public final static int ORDERBY_ABSTRACT = 9;
	public final static int ORDERBY_STATUSID = 10;
	public final static int ORDERBY_ID = 11;

	public Sett_TransFeeDAO()
	{
		super.strTableName = "SETT_TRANSFEE";
	}

	public long add(TransFeeInfo info) throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			info.setID(getSett_TransFeeID());

			con = getConnection();

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(");

			strSQLBuffer.append("ID,");
			strSQLBuffer.append("NOFFICEID,");
			strSQLBuffer.append("NCURRENCYID,");
			strSQLBuffer.append("STRANSNO,");
			strSQLBuffer.append("NTRANSACTIONTYPEID,");
			strSQLBuffer.append("NRELATEDTRANSTYPEID,");
			strSQLBuffer.append("NRELATEDCLIENTID,");
			strSQLBuffer.append("NRELATEDACCOUNTID,");
			strSQLBuffer.append("SRELATEDFIXEDDEPOSITNO,");
			strSQLBuffer.append("NRELATEDCONTRACTID,");
			strSQLBuffer.append("NRELATEDLOANNOTEID,");
			strSQLBuffer.append("NRELATEDSUBACCOUNTID,");
			strSQLBuffer.append("NRELATEDTRANSNO,");
			strSQLBuffer.append("NFEETYPEID,");
			strSQLBuffer.append("NFEEBANKID,");
			strSQLBuffer.append("NACCOUNTID,");
			strSQLBuffer.append("SEXTACCTNO,");
			strSQLBuffer.append("SEXTACCTNAME,");
			strSQLBuffer.append("NREMITINBANKID,");
			strSQLBuffer.append("NCASHFLOWID,");
			strSQLBuffer.append("MAMOUNT,");
			strSQLBuffer.append("SBILLNO,");
			strSQLBuffer.append("NBILLTYPEID,");
			strSQLBuffer.append("NBILLBANKID,");
			strSQLBuffer.append("SPAYEXTBANKNO,");
			strSQLBuffer.append("DTINTERESTSTART,");
			strSQLBuffer.append("DTEXECUTE,");
			strSQLBuffer.append("DTMODIFY,");
			strSQLBuffer.append("NINPUTUSERID,");
			strSQLBuffer.append("DTINPUT,");
			strSQLBuffer.append("NCHECKUSERID,");
			strSQLBuffer.append("NSIGNUSERID,");
			strSQLBuffer.append("NCONFIRMUSERID,");
			strSQLBuffer.append("NCONFIRMOFFICEID,");
			strSQLBuffer.append("NABSTRACTID,");
			strSQLBuffer.append("SABSTRACT,");
			strSQLBuffer.append("SCHECKABSTRACT,");
			strSQLBuffer.append("SCANCLECHECKABSTRACT,");
			strSQLBuffer.append("SCONFIRMABSTRACT,");
			strSQLBuffer.append("NSTATUSID");

			strSQLBuffer.append(") VALUES \n");
			strSQLBuffer.append(
				"( ?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,sysdate,?,?,"
					+ "?,?,?,?,?,?,?,?,?,? )\n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//ps will be modified in function addDatatoPrepareStatement 
			System.out.println(addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD));

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

	private int addDatatoPrepareStatement(TransFeeInfo info, PreparedStatement stmt, int operation) throws Exception
	{

		PreparedStatement ps = stmt;
		int index = 1;

		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setString(index++, info.getTransNo());
		ps.setLong(index++, info.getTransactionTypeID());
		ps.setLong(index++, info.getRelatedTransTypeID());
		ps.setLong(index++, info.getRelatedClientID());
		ps.setLong(index++, info.getRelatedAccountID());
		ps.setString(index++, info.getRelatedFixedDepositNo());
		ps.setLong(index++, info.getRelatedContractID());
		ps.setLong(index++, info.getRelatedLoanNoteID());
		ps.setLong(index++, info.getRelatedSubAccountID());
		ps.setString(index++, info.getRelatedTransNo());
		ps.setLong(index++, info.getFeeTypeID());
		ps.setLong(index++, info.getFeeBankID());
		ps.setLong(index++, info.getAccountID());
		ps.setString(index++, info.getExtAcctNo());
		ps.setString(index++, info.getExtAcctName());
		ps.setLong(index++, info.getRemitInBankID());
		ps.setLong(index++, info.getCashFlowID());
		ps.setDouble(index++, info.getAmount());
		ps.setString(index++, info.getBillNo());
		ps.setLong(index++, info.getBillTypeID());
		ps.setLong(index++, info.getBillBankID());
		ps.setString(index++, info.getPayExtBankNo());
		ps.setTimestamp(index++, info.getInterestStartDate());
		ps.setTimestamp(index++, info.getExecuteDate());
		//ps.setTimestamp(index++, info.getModifyDate());                       
		ps.setLong(index++, info.getInputUserID());
		if (operation == DAO_OPERATION_ADD)
		{
			ps.setTimestamp(index++, info.getInputDate());
		}
		ps.setLong(index++, info.getCheckUserID());
		ps.setLong(index++, info.getSignUserID());
		ps.setLong(index++, info.getConfirmUserID());
		ps.setLong(index++, info.getConfirmOfficeID());
		ps.setLong(index++, info.getAbstractID());
		ps.setString(index++, info.getAbstract());
		ps.setString(index++, info.getCheckAbstract());
		ps.setString(index++, info.getCancleCheckAbstract());
		ps.setString(index++, info.getConfirmAbstract());
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
	public Collection findByConditions(TransFeeInfo conditionInfo, int orderByType, boolean isDesc) throws Exception
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

			StringBuffer strWhereSQLBuffer = new StringBuffer();

			if (conditionInfo.getID() != -1)
			{
				strWhereSQLBuffer.append(" AND ID = " + conditionInfo.getID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getCheckUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCHECKUSERID = " + conditionInfo.getCheckUserID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getInputUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NINPUTUSERID = " + conditionInfo.getInputUserID() + " \n");
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

			if (conditionInfo.getTransNo() != null && conditionInfo.getTransNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND STRANSNO = '" + conditionInfo.getTransNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getTransactionTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NTRANSACTIONTYPEID = " + conditionInfo.getTransactionTypeID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getFeeTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NFEETYPEID = " + conditionInfo.getFeeTypeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getFeeBankID() != -1)
			{
				strWhereSQLBuffer.append(" AND NFEEBANKID = " + conditionInfo.getFeeBankID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAccountID() != -1)
			{
				strWhereSQLBuffer.append(" AND NACCOUNTID = " + conditionInfo.getAccountID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getExtAcctNo() != null && conditionInfo.getExtAcctNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SEXTACCTNO = '" + conditionInfo.getExtAcctNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getRemitInBankID() != -1)
			{
				strWhereSQLBuffer.append(" AND NREMITINBANKID = " + conditionInfo.getRemitInBankID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAmount() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MAMOUNT = " + conditionInfo.getAmount() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getInterestStartDate() != null)
			{
				strWhereSQLBuffer.append(
					" AND DTINTERESTSTART = to_date('"
						+ DataFormat.getDateString(conditionInfo.getInterestStartDate())
						+ "', 'yyyy-mm-dd') \n");
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

			if (conditionInfo.getModifyDate() != null)
			{
				strWhereSQLBuffer.append(
					" AND DTMODIFY = to_date('"
						+ DataFormat.getDateString(conditionInfo.getModifyDate())
						+ "', 'yyyy-mm-dd') \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getStatusIDs() != null)
			{
				strWhereSQLBuffer.append(" AND NSTATUSID IN(" + conditionInfo.getStatusIDs()[0] + " \n");

				for (int i = 1; i < conditionInfo.getStatusIDs().length; i++)
				{
					strWhereSQLBuffer.append(" ," + conditionInfo.getStatusIDs()[i] + " \n");
				}

				strWhereSQLBuffer.append(")");

				isNeedWhere = true;
			}

			if (isNeedWhere)
			{
				strSQLBuffer.append(" WHERE 1=1 ");
				//Cut first "AND"
				strSQLBuffer.append(strWhereSQLBuffer.toString());
			}

			boolean isNeedOrderBy = true;
			switch (orderByType)
			{

				case ORDERBY_ID :
					{
						strSQLBuffer.append(" ORDER BY ID \n");
						break;
					}
					
				case ORDERBY_FEETYPEID :
				{
					strSQLBuffer.append(" ORDER BY NFEETYPEID \n");
					break;
				}
				case ORDERBY_FEEBANKID :
				{
					strSQLBuffer.append(" ORDER BY NFEEBANKID \n");
					break;
				}
				case ORDERBY_AMOUNT :
				{
					strSQLBuffer.append(" ORDER BY MAMOUNT \n");
					break;
				}
				case ORDERBY_INTERESTSTARTDATE :
				{
					strSQLBuffer.append(" ORDER BY DTINTERESTSTART \n");
					break;
				}
				case ORDERBY_EXECUTEDATEDATE :
				{
					strSQLBuffer.append(" ORDER BY DTEXECUTE \n");
					break;
				}
				case ORDERBY_ABSTRACT :
				{
					strSQLBuffer.append(" ORDER BY SABSTRACT \n");
					break;
				}
				case ORDERBY_STATUSID :
				{
					strSQLBuffer.append(" ORDER BY NSTATUSID \n");
					break;
				}
				default :
					{
						strSQLBuffer.append(" ORDER BY ID \n");
						break;
					}
			}

			if (isNeedOrderBy)
			{
				if (isDesc)
					strSQLBuffer.append(" DESC \n");
				else
					strSQLBuffer.append(" ASC \n");
			}
            System.out.println("************************ "+strSQLBuffer.toString());
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

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransFeeInfo findByID(long ltransID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransFeeInfo result = null;
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
				result = (TransFeeInfo) itTemp.next();
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
	
	/**
	 * 方法说明：根据交易编号，得到交易信息
	 * @param ltransID
	 * @return TransFeeInfo
	 * @throws IException
	 */
	public TransFeeInfo findByTransNo(String sTransNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransFeeInfo result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE sTransNo = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, sTransNo);
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs);

			if (collTemp != null && collTemp.size() > 0)
			{
				Iterator itTemp = collTemp.iterator();
				result = (TransFeeInfo) itTemp.next();
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

	/**
	 * 方法说明：根据ID查找修改时间
	 * @param transCurrentDepositID  
	 * @return Timestamp 
	 * @throws IException
	 */
	public Timestamp findTouchDate(long lTransID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			strSQLBuffer.append("SELECT dtModify FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lTransID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
			}

		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return res;
	}

	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{

		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransFeeInfo info = new TransFeeInfo();

			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("NOFFICEID"));
			info.setCurrencyID(rs.getLong("NCURRENCYID"));
			info.setTransNo(rs.getString("STRANSNO"));
			info.setTransactionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
			info.setRelatedTransTypeID(rs.getLong("NRELATEDTRANSTYPEID"));
			info.setRelatedClientID(rs.getLong("NRELATEDCLIENTID"));
			info.setRelatedAccountID(rs.getLong("NRELATEDACCOUNTID"));
			info.setRelatedFixedDepositNo(rs.getString("SRELATEDFIXEDDEPOSITNO"));
			info.setRelatedContractID(rs.getLong("NRELATEDCONTRACTID"));
			info.setRelatedLoanNoteID(rs.getLong("NRELATEDLOANNOTEID"));
			info.setRelatedSubAccountID(rs.getLong("NRELATEDSUBACCOUNTID"));
			info.setRelatedTransNo(rs.getString("NRELATEDTRANSNO"));
			info.setFeeTypeID(rs.getLong("NFEETYPEID"));
			info.setFeeBankID(rs.getLong("NFEEBANKID"));
			info.setAccountID(rs.getLong("NACCOUNTID"));
			info.setExtAcctNo(rs.getString("SEXTACCTNO"));
			info.setExtAcctName(rs.getString("SEXTACCTNAME"));
			info.setRemitInBankID(rs.getLong("NREMITINBANKID"));
			info.setCashFlowID(rs.getLong("NCASHFLOWID"));
			info.setAmount(rs.getDouble("MAMOUNT"));
			info.setBillNo(rs.getString("SBILLNO"));
			info.setBillTypeID(rs.getLong("NBILLTYPEID"));
			info.setBillBankID(rs.getLong("NBILLBANKID"));
			info.setPayExtBankNo(rs.getString("SPAYEXTBANKNO"));
			info.setInterestStartDate(rs.getTimestamp("DTINTERESTSTART"));
			info.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
			info.setModifyDate(rs.getTimestamp("DTMODIFY"));
			info.setInputUserID(rs.getLong("NINPUTUSERID"));
			info.setInputDate(rs.getTimestamp("DTINPUT"));
			info.setCheckUserID(rs.getLong("NCHECKUSERID"));
			info.setSignUserID(rs.getLong("NSIGNUSERID"));
			info.setConfirmUserID(rs.getLong("NCONFIRMUSERID"));
			info.setConfirmOfficeID(rs.getLong("NCONFIRMOFFICEID"));
			info.setAbstractID(rs.getLong("NABSTRACTID"));
			info.setAbstract(rs.getString("SABSTRACT"));
			info.setCheckAbstract(rs.getString("SCHECKABSTRACT"));
			info.setCancleCheckAbstract(rs.getString("SCANCLECHECKABSTRACT"));
			info.setConfirmAbstract(rs.getString("SCONFIRMABSTRACT"));
			info.setStatusID(rs.getLong("NSTATUSID"));

			list.add(info);
		}
		return list;
	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransFeeInfo match(TransFeeInfo conditionInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransFeeInfo result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer(256);

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");

			strSQLBuffer.append(" WHERE");
			strSQLBuffer.append(" NSTATUSID = " + SETTConstant.TransactionStatus.SAVE + " \n");
			strSQLBuffer.append(" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
			strSQLBuffer.append(" AND NCURRENCYID = " + conditionInfo.getCurrencyID() + " \n");
			strSQLBuffer.append(" AND NINPUTUSERID != " + conditionInfo.getInputUserID() + " \n");
			strSQLBuffer.append(" AND MAMOUNT = " + conditionInfo.getAmount() + " \n");
			if (conditionInfo.getInterestStartDate() != null)
			{
				strSQLBuffer.append(
					" AND DTINTERESTSTART = to_date('"
						+ DataFormat.getDateString(conditionInfo.getInterestStartDate())
						+ "', 'yyyy-mm-dd') \n");
			}
			else
			{
				strSQLBuffer.append(" AND DTINTERESTSTART IS NULL \n");
			}

			strSQLBuffer.append(" AND NACCOUNTID = " + conditionInfo.getAccountID() + " \n");

			if (conditionInfo.getExtAcctNo() != null && conditionInfo.getExtAcctNo().length() > 0)
			{
				strSQLBuffer.append(" AND SEXTACCTNO = '" + conditionInfo.getExtAcctNo() + "' \n");
			}
			else
			{
				strSQLBuffer.append(" AND SEXTACCTNO IS NULL \n");
			}

			strSQLBuffer.append(" AND NREMITINBANKID = " + conditionInfo.getRemitInBankID() + " \n");

			strSQLBuffer.append(" ORDER BY ID DESC \n");

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs);

			if (collTemp != null && collTemp.size() > 0)
			{
				Iterator itTemp = collTemp.iterator();
				result = (TransFeeInfo) itTemp.next();
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

	/**
	 * 方法说明：修改活期交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回活期交易记录ID
	 * @throws IException
	 */
	public long update(TransFeeInfo info) throws Exception
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
			strSQLBuffer.append("STRANSNO=?,");
			strSQLBuffer.append("NTRANSACTIONTYPEID=?,");
			strSQLBuffer.append("NRELATEDTRANSTYPEID=?,");
			strSQLBuffer.append("NRELATEDCLIENTID=?,");
			strSQLBuffer.append("NRELATEDACCOUNTID=?,");
			strSQLBuffer.append("SRELATEDFIXEDDEPOSITNO=?,");
			strSQLBuffer.append("NRELATEDCONTRACTID=?,");
			strSQLBuffer.append("NRELATEDLOANNOTEID=?,");
			strSQLBuffer.append("NRELATEDSUBACCOUNTID=?,");
			strSQLBuffer.append("NRELATEDTRANSNO=?,");
			strSQLBuffer.append("NFEETYPEID=?,");
			strSQLBuffer.append("NFEEBANKID=?,");
			strSQLBuffer.append("NACCOUNTID=?,");
			strSQLBuffer.append("SEXTACCTNO=?,");
			strSQLBuffer.append("SEXTACCTNAME=?,");
			strSQLBuffer.append("NREMITINBANKID=?,");
			strSQLBuffer.append("NCASHFLOWID=?,");
			strSQLBuffer.append("MAMOUNT=?,");
			strSQLBuffer.append("SBILLNO=?,");
			strSQLBuffer.append("NBILLTYPEID=?,");
			strSQLBuffer.append("NBILLBANKID=?,");
			strSQLBuffer.append("SPAYEXTBANKNO=?,");
			strSQLBuffer.append("DTINTERESTSTART=?,");
			strSQLBuffer.append("DTEXECUTE=?,");
			strSQLBuffer.append("DTMODIFY=sysdate,");
			strSQLBuffer.append("NINPUTUSERID=?,");
			//strSQLBuffer.append("DTINPUT=?,");                 
			strSQLBuffer.append("NCHECKUSERID=?,");
			strSQLBuffer.append("NSIGNUSERID=?,");
			strSQLBuffer.append("NCONFIRMUSERID=?,");
			strSQLBuffer.append("NCONFIRMOFFICEID=?,");
			strSQLBuffer.append("NABSTRACTID=?,");
			strSQLBuffer.append("SABSTRACT=?,");
			strSQLBuffer.append("SCHECKABSTRACT=?,");
			strSQLBuffer.append("SCANCLECHECKABSTRACT=?,");
			strSQLBuffer.append("SCONFIRMABSTRACT=?,");
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
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public long updateStatus(long id, long StatusID) throws Exception
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
			strSQLBuffer.append("nStatusID = ?, dtModify = sysdate \n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, StatusID);
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
	
	
	public Vector findByTransNo(String strTransNo,long lOfficeID, long lCurrencyID) throws SettlementDAOException{
		boolean isSelfManagedCon = false;
		ResultSet rs = null;
		Vector rtVector = new Vector();
		if (transConn != null)
		{
			isSelfManagedCon = true;
		}
		
		try
		{
			log.print("======Sett_TransaFeelDAO：findByTransNo() begin======");
			log.print("======sTransNo======:" + strTransNo);
			log.print("======lOfficeID======:" + lOfficeID);
			log.print("======lCurrencyID======:" + lCurrencyID);
			
			initDAO();
			
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE STRANSNO = '" + strTransNo +"' \n");
			strSQLBuffer.append(" and NOFFICEID = "+ lOfficeID +"\n");
			strSQLBuffer.append(" and NCURRENCYID = "+lCurrencyID );
			
			log.info(strSQLBuffer.toString());
			
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			if(rs==null ){
				
			} else{
				while(rs.next()){
					TransFeeInfo info = new TransFeeInfo();
					
					info.setID(rs.getLong("ID"));
					info.setOfficeID(rs.getLong("NOFFICEID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
					info.setTransNo(rs.getString("STRANSNO"));
					info.setTransactionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
					info.setRelatedTransTypeID(rs.getLong("NRELATEDTRANSTYPEID"));
					info.setRelatedClientID(rs.getLong("NRELATEDCLIENTID"));
					info.setRelatedAccountID(rs.getLong("NRELATEDACCOUNTID"));
					info.setRelatedFixedDepositNo(rs.getString("SRELATEDFIXEDDEPOSITNO"));
					info.setRelatedContractID(rs.getLong("NRELATEDCONTRACTID"));
					info.setRelatedLoanNoteID(rs.getLong("NRELATEDLOANNOTEID"));
					info.setRelatedSubAccountID(rs.getLong("NRELATEDSUBACCOUNTID"));
					info.setRelatedTransNo(rs.getString("NRELATEDTRANSNO"));
					info.setFeeTypeID(rs.getLong("NFEETYPEID"));
					info.setFeeBankID(rs.getLong("NFEEBANKID"));
					info.setAccountID(rs.getLong("NACCOUNTID"));
					info.setExtAcctNo(rs.getString("SEXTACCTNO"));
					info.setExtAcctName(rs.getString("SEXTACCTNAME"));
					info.setRemitInBankID(rs.getLong("NREMITINBANKID"));
					info.setCashFlowID(rs.getLong("NCASHFLOWID"));
					info.setAmount(rs.getDouble("MAMOUNT"));
					info.setBillNo(rs.getString("SBILLNO"));
					info.setBillTypeID(rs.getLong("NBILLTYPEID"));
					info.setBillBankID(rs.getLong("NBILLBANKID"));
					info.setPayExtBankNo(rs.getString("SPAYEXTBANKNO"));
					info.setInterestStartDate(rs.getTimestamp("DTINTERESTSTART"));
					info.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
					info.setModifyDate(rs.getTimestamp("DTMODIFY"));
					info.setInputUserID(rs.getLong("NINPUTUSERID"));
					info.setInputDate(rs.getTimestamp("DTINPUT"));
					info.setCheckUserID(rs.getLong("NCHECKUSERID"));
					info.setSignUserID(rs.getLong("NSIGNUSERID"));
					info.setConfirmUserID(rs.getLong("NCONFIRMUSERID"));
					info.setConfirmOfficeID(rs.getLong("NCONFIRMOFFICEID"));
					info.setAbstractID(rs.getLong("NABSTRACTID"));
					info.setAbstract(rs.getString("SABSTRACT"));
					info.setCheckAbstract(rs.getString("SCHECKABSTRACT"));
					info.setCancleCheckAbstract(rs.getString("SCANCLECHECKABSTRACT"));
					info.setConfirmAbstract(rs.getString("SCONFIRMABSTRACT"));
					info.setStatusID(rs.getLong("NSTATUSID"));

					rtVector.add(info);
				}
			}
			
			rs.close();

			log.print("======Sett_TransaAccountDetailDAO：findByTransNo() begin======");
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("findByTransNo产生错误", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("findByTransNo息产生错误", e);
		} 
		finally 
		{
			if (!isSelfManagedCon)
			{
				try
				{
					finalizeDAO();
				}
				catch (ITreasuryDAOException e)
				{
					throw new SettlementDAOException(e);
				}
			}
		}
		return rtVector;
	}

}

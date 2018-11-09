package com.iss.itreasury.settlement.transcurrentdeposit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Sett_TransOnePayMultiReceiveDAO extends SettlementDAO
{
	public final static int ORDERBY_TRANSACTIONNOID = 0;
	public final static int ORDERBY_ACCOUNTNO = 1;
	public final static int ORDERBY_PAYCLIENTNAME = 2;
	public final static int ORDERBY_RECEIVECLIENTNAME = 3;
	public final static int ORDERBY_CONSIGNVOUCHERNO = 4;
	public final static int ORDERBY_AMOUNT = 5;
	public final static int ORDERBY_INTERESTSTARTDATE = 6;
	public final static int ORDERBY_EXECUTEDATEDATE = 7;
	public final static int ORDERBY_ABSTRACT = 8;
	public final static int ORDERBY_STATUSID = 9;
	public final static int ORDERBY_TYPEID = 10;
	public final static int ORDERBY_ID = 11;

	public Sett_TransOnePayMultiReceiveDAO()
	{
		super.strTableName = "SETT_TRANSONEPAYMULTIRECEIVE";
	}

	private int addDatatoPrepareStatement(TransOnePayMultiReceiveInfo info, PreparedStatement stmt, int operation)
		throws Exception
	{

		PreparedStatement ps = stmt;
		int index = 1;

		ps.setLong(index++, info.getId());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setString(index++, info.getTransNo());
		ps.setString(index++, info.getEmptyTransNo());
		ps.setLong(index++, info.getSerialNo());
		ps.setLong(index++, info.getTransactionTypeID());
		ps.setLong(index++, info.getTypeID());
		ps.setLong(index++, info.getIsInternalVirement());
		ps.setLong(index++, info.getIsBank());
		ps.setLong(index++, info.getIsGL());
		ps.setString(index++, info.getConsignVoucherNo());
		ps.setString(index++, info.getConsignPassword());
		ps.setLong(index++, info.getPayClientID());
		ps.setLong(index++, info.getReceiveClientID());
		ps.setLong(index++, info.getAccountID());
		ps.setLong(index++, info.getPayGL());
		ps.setLong(index++, info.getReceiveGL());
		ps.setLong(index++, info.getBankID());
		ps.setString(index++, info.getDeclarationNo());
		ps.setString(index++, info.getExtAccountNo());
		ps.setString(index++, info.getExtClientName());
		ps.setString(index++, info.getRemitInBank());
		ps.setString(index++, info.getRemitInProvince());
		ps.setString(index++, info.getRemitInCity());
		ps.setLong(index++, info.getCashFlowID());
		ps.setDouble(index++, info.getAmount());
		ps.setTimestamp(index++, info.getInterestStartDate());
		ps.setTimestamp(index++, info.getExecuteDate());
		//ps.setTimestamp(index++, info.getModifyDate());
		if (operation == DAO_OPERATION_ADD)
		{
			ps.setTimestamp(index++, info.getInputDate());
		}
		ps.setLong(index++, info.getInputUserID());
		ps.setLong(index++, info.getCheckUserID());
		ps.setLong(index++, info.getSignUserID());
		ps.setLong(index++, info.getConfirmUserID());
		ps.setLong(index++, info.getConfirmOfficeID());
		ps.setLong(index++, info.getAbstractID());
		ps.setString(index++, info.getAbstract());
		ps.setString(index++, info.getCheckAbstract());
		ps.setString(index++, info.getConfirmAbstract());
		ps.setLong(index++, info.getStatusID());
		//add by xlchang 2010-11-04 武钢需求:多借多贷业务增加子类型
		ps.setLong(index++, info.getNSubTypeId());
		//add by cheny 2010-11-15 武钢需求:多借多贷业务增加特约委托收款合同号
		ps.setString(index++, info.getContractno());
		return index;
	}

	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{

		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransOnePayMultiReceiveInfo info = new TransOnePayMultiReceiveInfo();

			info.setId(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("NOFFICEID"));
			info.setCurrencyID(rs.getLong("NCURRENCYID"));
			info.setTransNo(rs.getString("STRANSNO"));
			info.setEmptyTransNo(rs.getString("SEMPTYTRANSNO"));
			info.setSerialNo(rs.getLong("NSERIALNO"));
			info.setTransactionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
			info.setTypeID(rs.getLong("NTYPEID"));
			info.setIsInternalVirement(rs.getLong("NISINTERNALVIREMENT"));
			info.setIsBank(rs.getLong("NISBANK"));
			info.setIsGL(rs.getLong("NISGL"));
			info.setConsignVoucherNo(rs.getString("SCONSIGNVOUCHERNO"));
			info.setConsignPassword(rs.getString("SCONSIGNPASSWORD"));
			info.setPayClientID(rs.getLong("NPAYCLIENTID"));
			info.setReceiveClientID(rs.getLong("NRECEIVECLIENTID"));
			info.setAccountID(rs.getLong("NACCOUNTID"));
			info.setPayGL(rs.getLong("NPAYGL"));
			info.setReceiveGL(rs.getLong("NRECEIVEGL"));
			info.setBankID(rs.getLong("NBANKID"));
			info.setDeclarationNo(rs.getString("SDECLARATIONNO"));
			info.setExtAccountNo(rs.getString("SEXTACCOUNTNO"));
			info.setExtClientName(rs.getString("SEXTCLIENTNAME"));
			info.setRemitInBank(rs.getString("SREMITINBANK"));
			info.setRemitInProvince(rs.getString("SREMITINPROVINCE"));
			info.setRemitInCity(rs.getString("SREMITINCITY"));
			info.setCashFlowID(rs.getLong("NCASHFLOWID"));
			info.setAmount(rs.getDouble("MAMOUNT"));
			info.setInterestStartDate(rs.getTimestamp("DTINTERESTSTART"));
			info.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
			info.setModifyDate(rs.getTimestamp("DTMODIFY"));
			info.setInputDate(rs.getTimestamp("DTINPUT"));
			info.setInputUserID(rs.getLong("NINPUTUSERID"));
			info.setCheckUserID(rs.getLong("NCHECKUSERID"));
			info.setSignUserID(rs.getLong("NSIGNUSERID"));
			info.setConfirmUserID(rs.getLong("NCONFIRMUSERID"));
			info.setConfirmOfficeID(rs.getLong("NCONFIRMOFFICEID"));
			info.setAbstractID(rs.getLong("NABSTRACTID"));
			info.setAbstract(rs.getString("SABSTRACT"));
			info.setCheckAbstract(rs.getString("SCHECKABSTRACT"));
			info.setConfirmAbstract(rs.getString("SCONFIRMABSTRACT"));
			info.setStatusID(rs.getLong("NSTATUSID"));
			//add by xlchang 2010-11-04 武钢需求:多借多贷业务增加子类型
			info.setNSubTypeId(rs.getLong("nsubTypeId"));
			//add by cheny 2010-11-15 武钢需求:多借多贷业务增加特约委托收款合同号
			info.setContractno(rs.getString("CONTRACTNO"));

			list.add(info);
		}
		return list;
	}

	public long add(TransOnePayMultiReceiveInfo info) throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			info.setId(getSett_TransOnePayMultiReceiveID());

			con = getConnection();

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(");

			strSQLBuffer.append("ID,");
			strSQLBuffer.append("NOFFICEID,");
			strSQLBuffer.append("NCURRENCYID,");
			strSQLBuffer.append("STRANSNO,");
			strSQLBuffer.append("SEMPTYTRANSNO,");
			strSQLBuffer.append("NSERIALNO,");
			strSQLBuffer.append("NTRANSACTIONTYPEID,");
			strSQLBuffer.append("NTYPEID,");
			strSQLBuffer.append("NISINTERNALVIREMENT,");
			strSQLBuffer.append("NISBANK,");
			strSQLBuffer.append("NISGL,");
			strSQLBuffer.append("SCONSIGNVOUCHERNO,");
			strSQLBuffer.append("SCONSIGNPASSWORD,");
			strSQLBuffer.append("NPAYCLIENTID,");
			strSQLBuffer.append("NRECEIVECLIENTID,");
			strSQLBuffer.append("NACCOUNTID,");
			strSQLBuffer.append("NPAYGL,");
			strSQLBuffer.append("NRECEIVEGL,");
			strSQLBuffer.append("NBANKID,");
			strSQLBuffer.append("SDECLARATIONNO,");
			strSQLBuffer.append("SEXTACCOUNTNO,");
			strSQLBuffer.append("SEXTCLIENTNAME,");
			strSQLBuffer.append("SREMITINBANK,");
			strSQLBuffer.append("SREMITINPROVINCE,");
			strSQLBuffer.append("SREMITINCITY,");
			strSQLBuffer.append("NCASHFLOWID,");
			strSQLBuffer.append("MAMOUNT,");
			strSQLBuffer.append("DTINTERESTSTART,");
			strSQLBuffer.append("DTEXECUTE,");
			strSQLBuffer.append("DTMODIFY,");
			strSQLBuffer.append("DTINPUT,");
			strSQLBuffer.append("NINPUTUSERID,");
			strSQLBuffer.append("NCHECKUSERID,");
			strSQLBuffer.append("NSIGNUSERID,");
			strSQLBuffer.append("NCONFIRMUSERID,");
			strSQLBuffer.append("NCONFIRMOFFICEID,");
			strSQLBuffer.append("NABSTRACTID,");
			strSQLBuffer.append("SABSTRACT,");
			strSQLBuffer.append("SCHECKABSTRACT,");
			strSQLBuffer.append("SCONFIRMABSTRACT,");
			strSQLBuffer.append("NSTATUSID,");
			//add by xlchang 2010-11-04 武钢需求:多借多贷业务增加子类型
			strSQLBuffer.append("NSUBTYPEID,");
			//add by cheny 2010-11-15   武钢需求：多借多贷增加特约委托收款合同号
			strSQLBuffer.append("CONTRACTNO");
			strSQLBuffer.append(") VALUES \n");
			strSQLBuffer.append(
				"( ?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,sysdate,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?) \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//ps will be modified in function addDatatoPrepareStatement 
			System.out.println(addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD));

			if (ps.executeUpdate() == 1)
			{
				lResult = info.getId();
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
	public Collection findByConditions(TransOnePayMultiReceiveInfo conditionInfo, int orderByType, boolean isDesc)
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

			if (conditionInfo.getId() != -1)
			{
				strWhereSQLBuffer.append(" AND ID = " + conditionInfo.getId() + " \n");
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

			if (conditionInfo.getEmptyTransNo() != null && conditionInfo.getEmptyTransNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SEMPTYTRANSNO = '" + conditionInfo.getEmptyTransNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getSerialNo() != -1)
			{
				strWhereSQLBuffer.append(" AND NSERIALNO = " + conditionInfo.getSerialNo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getTransactionTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NTRANSACTIONTYPEID = " + conditionInfo.getTransactionTypeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NTYPEID = " + conditionInfo.getTypeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getIsInternalVirement() != -1)
			{
				strWhereSQLBuffer.append(" AND NISINTERNALVIREMENT = " + conditionInfo.getIsInternalVirement() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getIsBank() != -1)
			{
				strWhereSQLBuffer.append(" AND NISBANK = " + conditionInfo.getIsBank() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getIsGL() != -1)
			{
				strWhereSQLBuffer.append(" AND NISGL = " + conditionInfo.getIsGL() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getConsignVoucherNo() != null && conditionInfo.getConsignVoucherNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SCONSIGNVOUCHERNO = '" + conditionInfo.getConsignVoucherNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getConsignPassword() != null && conditionInfo.getConsignPassword().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SCONSIGNPASSWORD = '" + conditionInfo.getConsignPassword() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getPayClientID() != -1)
			{
				strWhereSQLBuffer.append(" AND NPAYCLIENTID = " + conditionInfo.getPayClientID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getReceiveClientID() != -1)
			{
				strWhereSQLBuffer.append(" AND NRECEIVECLIENTID = " + conditionInfo.getReceiveClientID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAccountID() != -1)
			{
				strWhereSQLBuffer.append(" AND NACCOUNTID = " + conditionInfo.getAccountID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getPayGL() != -1)
			{
				strWhereSQLBuffer.append(" AND NPAYGL = " + conditionInfo.getPayGL() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getReceiveGL() != -1)
			{
				strWhereSQLBuffer.append(" AND NRECEIVEGL = " + conditionInfo.getReceiveGL() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getBankID() != -1)
			{
				strWhereSQLBuffer.append(" AND NBANKID = " + conditionInfo.getBankID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getDeclarationNo() != null && conditionInfo.getDeclarationNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SDECLARATIONNO = '" + conditionInfo.getDeclarationNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getExtAccountNo() != null && conditionInfo.getExtAccountNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SEXTACCOUNTNO = '" + conditionInfo.getExtAccountNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getExtClientName() != null && conditionInfo.getExtClientName().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SEXTCLIENTNAME = '" + conditionInfo.getExtClientName() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getRemitInBank() != null && conditionInfo.getRemitInBank().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SREMITINBANK = '" + conditionInfo.getRemitInBank() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getRemitInProvince() != null && conditionInfo.getRemitInProvince().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SREMITINPROVINCE = '" + conditionInfo.getRemitInProvince() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getRemitInCity() != null && conditionInfo.getRemitInCity().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SREMITINCITY = '" + conditionInfo.getRemitInCity() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCashFlowID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCASHFLOWID = " + conditionInfo.getCashFlowID() + " \n");
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
					" AND trunc(DTMODIFY) = trunc(to_date('"
						+ DataFormat.getDateString(conditionInfo.getModifyDate())
						+ "', 'yyyy-mm-dd')) \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getInputDate() != null)
			{
				strWhereSQLBuffer.append(
					" AND DTINPUT = to_date('"
						+ DataFormat.getDateString(conditionInfo.getInputDate())
						+ "', 'yyyy-mm-dd') \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getInputUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NINPUTUSERID = " + conditionInfo.getInputUserID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCheckUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND (NCHECKUSERID = " + conditionInfo.getCheckUserID() + " or \n");
				//2010-12月16日 可以查询出机核复核的记录
				strWhereSQLBuffer.append("NCHECKUSERID ="+Constant.MachineUser.CheckUser+")\n");
				
				isNeedWhere = true;
			}

			if (conditionInfo.getSignUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NSIGNUSERID = " + conditionInfo.getSignUserID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getConfirmUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCONFIRMUSERID = " + conditionInfo.getConfirmUserID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getConfirmOfficeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCONFIRMOFFICEID = " + conditionInfo.getConfirmOfficeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAbstractID() != -1)
			{
				strWhereSQLBuffer.append(" AND NABSTRACTID = " + conditionInfo.getAbstractID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAbstract() != null && conditionInfo.getAbstract().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SABSTRACT = '" + conditionInfo.getAbstract() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCheckAbstract() != null && conditionInfo.getCheckAbstract().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SCHECKABSTRACT = '" + conditionInfo.getCheckAbstract() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getConfirmAbstract() != null && conditionInfo.getConfirmAbstract().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SCONFIRMABSTRACT = '" + conditionInfo.getConfirmAbstract() + "' \n");
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
				strSQLBuffer.append(" WHERE");
				//Cut first "AND"
				strSQLBuffer.append(strWhereSQLBuffer.substring(4));
			}

			boolean isNeedOrderBy = true;
			switch (orderByType)
			{
				case ORDERBY_TRANSACTIONNOID :
					{
						strSQLBuffer.append(" ORDER BY sTransNo \n");
					}
					break;
				case ORDERBY_ACCOUNTNO :
					{
						strSQLBuffer.append(" ORDER BY NACCOUNTID \n");
					}
					break;
				case ORDERBY_PAYCLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nPayClientID \n");
					}
					break;
				case ORDERBY_RECEIVECLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY NRECEIVECLIENTID \n");
					}
					break;
				case ORDERBY_CONSIGNVOUCHERNO :
					{
						strSQLBuffer.append(" ORDER BY sConsignVoucherNo \n");
					}
					break;
				case ORDERBY_AMOUNT :
					{
						strSQLBuffer.append(" ORDER BY mAmount \n");
					}
					break;
				case ORDERBY_INTERESTSTARTDATE :
					{
						strSQLBuffer.append(" ORDER BY dtInterestStart \n");
					}
					break;
				case ORDERBY_EXECUTEDATEDATE :
					{
						strSQLBuffer.append(" ORDER BY dtExecute \n");
					}
					break;
				case ORDERBY_ABSTRACT :
					{
						strSQLBuffer.append(" ORDER BY sAbstract \n");
					}
					break;
				case ORDERBY_STATUSID :
					{
						strSQLBuffer.append(" ORDER BY nStatusID \n");
					}
					break;
				case ORDERBY_TYPEID :
					{
						strSQLBuffer.append(" ORDER BY nTypeID \n");
					}
					break;
				case ORDERBY_ID :
					{
						strSQLBuffer.append(" ORDER BY ID \n");
					}
					break;
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

	/**
	 * 勾账用查询
	 * @param conditionInfo
	 * @param orderByType
	 * @param isDesc
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByConditionsForSquareUp(
		TransOnePayMultiReceiveInfo conditionInfo,
		int orderByType,
		boolean isDesc)
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

			if (conditionInfo.getPayClientID() != -1)
			{
				strWhereSQLBuffer.append(" AND NPAYCLIENTID = " + conditionInfo.getPayClientID() + " \n");
				isNeedWhere = true;
			}
			
			if (conditionInfo.getReceiveClientID() != -1)
			{
				strWhereSQLBuffer.append(" AND NRECEIVECLIENTID = " + conditionInfo.getReceiveClientID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getTransactionTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NTransactiontypeid = " + conditionInfo.getTransactionTypeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getPayGL() != -1)
			{
				strWhereSQLBuffer.append(" AND (NPAYGL = " + conditionInfo.getPayGL() + " \n");
				strWhereSQLBuffer.append(" OR NRECEIVEGL = " + conditionInfo.getPayGL() + ") \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getReceiveGL() != -1)
			{

				isNeedWhere = true;
			}

			if (conditionInfo.getDeclarationNo() != null && conditionInfo.getDeclarationNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SDECLARATIONNO = '" + conditionInfo.getDeclarationNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getStatusIDs() != null)
			{
				strWhereSQLBuffer.append(" AND NSTATUSID =" + conditionInfo.getStatusID() + " \n");
				isNeedWhere = true;
			}
			
			//add by xlchang 2010-11-04 武钢需求，多借多贷业务必须匹配子类型
			if (conditionInfo.getNSubTypeId() > 0) {
				strWhereSQLBuffer.append(" AND NSUBTYPEID = " + conditionInfo.getNSubTypeId() + " \n");
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
				case ORDERBY_TRANSACTIONNOID :
					{
						strSQLBuffer.append(" ORDER BY sTransNo \n");
					}
					break;
				case ORDERBY_ACCOUNTNO :
					{
						strSQLBuffer.append(" ORDER BY NACCOUNTID \n");
					}
					break;
				case ORDERBY_PAYCLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nPayClientID \n");
					}
					break;
				case ORDERBY_RECEIVECLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY NRECEIVECLIENTID \n");
					}
					break;
				case ORDERBY_CONSIGNVOUCHERNO :
					{
						strSQLBuffer.append(" ORDER BY sConsignVoucherNo \n");
					}
					break;
				case ORDERBY_AMOUNT :
					{
						strSQLBuffer.append(" ORDER BY mAmount \n");
					}
					break;
				case ORDERBY_INTERESTSTARTDATE :
					{
						strSQLBuffer.append(" ORDER BY dtInterestStart \n");
					}
					break;
				case ORDERBY_EXECUTEDATEDATE :
					{
						strSQLBuffer.append(" ORDER BY dtExecute \n");
					}
					break;
				case ORDERBY_ABSTRACT :
					{
						strSQLBuffer.append(" ORDER BY sAbstract \n");
					}
					break;
				case ORDERBY_STATUSID :
					{
						strSQLBuffer.append(" ORDER BY nStatusID \n");
					}
					break;
				case ORDERBY_TYPEID :
					{
						strSQLBuffer.append(" ORDER BY nTypeID \n");
					}
					break;
				case ORDERBY_ID :
					{
						strSQLBuffer.append(" ORDER BY ID \n");
					}
					break;
				default :
					{
						isNeedOrderBy = false;
					}
					break;
			}

			if (isNeedOrderBy)
			{
				if (isDesc)
					strSQLBuffer.append(",nTypeID DESC \n");
				else
					strSQLBuffer.append(",nTypeID ASC \n");
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

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransOnePayMultiReceiveInfo findByID(long ltransID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransOnePayMultiReceiveInfo result = null;
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
				result = (TransOnePayMultiReceiveInfo) itTemp.next();
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

	//	/**
	//	 * 方法说明：根据状态查找
	//	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	//	 * @return Collection 
	//	 * @throws IException
	//	 */
	//	public Collection findByStatus(long statusID) throws Exception
	//	{
	//		return null;
	//	}

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

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransOnePayMultiReceiveInfo match(TransOnePayMultiReceiveInfo conditionInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransOnePayMultiReceiveInfo result = null;
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
			strSQLBuffer.append(" AND NTYPEID = " + conditionInfo.getTypeID() + " \n");
			//strSQLBuffer.append(" AND NISINTERNALVIREMENT = " + conditionInfo.getIsInternalVirement() + " \n");
			//strSQLBuffer.append(" AND NISBANK = " + conditionInfo.getIsBank() + " \n");
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

			strSQLBuffer.append(" AND NBANKID = " + conditionInfo.getBankID() + " \n");

			if (conditionInfo.getBankID() != -1)
			{
				if (conditionInfo.getDeclarationNo() != null && conditionInfo.getDeclarationNo().length() > 0)
				{
					strSQLBuffer.append(" AND SDECLARATIONNO = '" + conditionInfo.getDeclarationNo() + "' \n");
				}
				else
				{
					strSQLBuffer.append(" AND SDECLARATIONNO IS NULL \n");
				}
			}

			if (conditionInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)
			{
				strSQLBuffer.append(" AND NPAYGL = " + conditionInfo.getPayGL() + " \n");
			}
			else if (conditionInfo.getTypeID() == SETTConstant.ReceiveOrPay.RECEIVE)
			{
				strSQLBuffer.append(" AND NRECEIVEGL = " + conditionInfo.getPayGL() + " \n");
			}
			
			//add by xlchang 2010-11-04 武钢需求，多借多贷业务必须匹配子类型
			if (conditionInfo.getNSubTypeId() > 0) {
				strSQLBuffer.append(" AND NSUBTYPEID = " + conditionInfo.getNSubTypeId() + " \n");
			}

			strSQLBuffer.append(" ORDER BY ID DESC \n");

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs);

			if (collTemp != null && collTemp.size() > 0)
			{
				Iterator itTemp = collTemp.iterator();
				result = (TransOnePayMultiReceiveInfo) itTemp.next();
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
	public long update(TransOnePayMultiReceiveInfo info) throws Exception
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
			strSQLBuffer.append("SEMPTYTRANSNO=?,");
			strSQLBuffer.append("NSERIALNO=?,");
			strSQLBuffer.append("NTRANSACTIONTYPEID=?,");
			strSQLBuffer.append("NTYPEID=?,");
			strSQLBuffer.append("NISINTERNALVIREMENT=?,");
			strSQLBuffer.append("NISBANK=?,");
			strSQLBuffer.append("NISGL=?,");
			strSQLBuffer.append("SCONSIGNVOUCHERNO=?,");
			strSQLBuffer.append("SCONSIGNPASSWORD=?,");
			strSQLBuffer.append("NPAYCLIENTID=?,");
			strSQLBuffer.append("NRECEIVECLIENTID=?,");
			strSQLBuffer.append("NACCOUNTID=?,");
			strSQLBuffer.append("NPAYGL=?,");
			strSQLBuffer.append("NRECEIVEGL=?,");
			strSQLBuffer.append("NBANKID=?,");
			strSQLBuffer.append("SDECLARATIONNO=?,");
			strSQLBuffer.append("SEXTACCOUNTNO=?,");
			strSQLBuffer.append("SEXTCLIENTNAME=?,");
			strSQLBuffer.append("SREMITINBANK=?,");
			strSQLBuffer.append("SREMITINPROVINCE=?,");
			strSQLBuffer.append("SREMITINCITY=?,");
			strSQLBuffer.append("NCASHFLOWID=?,");
			strSQLBuffer.append("MAMOUNT=?,");
			strSQLBuffer.append("DTINTERESTSTART=?,");
			strSQLBuffer.append("DTEXECUTE=?,");
			strSQLBuffer.append("DTMODIFY=sysdate,");
			//strSQLBuffer.append("DTINPUT=?,");
			strSQLBuffer.append("NINPUTUSERID=?,");
			strSQLBuffer.append("NCHECKUSERID=?,");
			strSQLBuffer.append("NSIGNUSERID=?,");
			strSQLBuffer.append("NCONFIRMUSERID=?,");
			strSQLBuffer.append("NCONFIRMOFFICEID=?,");
			strSQLBuffer.append("NABSTRACTID=?,");
			strSQLBuffer.append("SABSTRACT=?,");
			strSQLBuffer.append("SCHECKABSTRACT=?,");
			strSQLBuffer.append("SCONFIRMABSTRACT=?,");
			strSQLBuffer.append("NSTATUSID=?,");
			//add by xlchang 2010-11-04 武钢需求:多借多贷业务增加子类型
			strSQLBuffer.append("NSUBTYPEID=?,");
			//add by cheny 2010-11-15 武钢需求:多借多贷增加特约委托收款合同号
			strSQLBuffer.append("CONTRACTNO=? ");
			strSQLBuffer.append(" WHERE ID = ?");

			String strSQL = strSQLBuffer.toString();

			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			int lastIndex = addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			//Set value of where condition
			ps.setLong(lastIndex, info.getId());
			ps.executeUpdate();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return info.getId();
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

	/**
	 * 方法说明：修改状态
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return long  
	 * @throws IException
	 */
	public void updateTransNo(long id, String stransNo) throws SQLException
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
			strSQLBuffer.append("STRANSNO = ?\n");
			strSQLBuffer.append(" WHERE ID = ? \n");

			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setString(1, stransNo);
			ps.setLong(2, id);
			ps.executeUpdate();

		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
	}

}

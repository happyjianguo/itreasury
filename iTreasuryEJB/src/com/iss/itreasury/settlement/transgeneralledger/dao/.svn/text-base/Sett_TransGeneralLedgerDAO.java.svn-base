package com.iss.itreasury.settlement.transgeneralledger.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import java.sql.Connection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
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
public class Sett_TransGeneralLedgerDAO extends SettlementDAO
{
	public final static int ORDERBY_TRANSACTIONNOID = 0;

	public final static int ORDERBY_ACCOUNTNO = 1;

	public final static int ORDERBY_CLIENTNAME = 2;

	public final static int ORDERBY_DIRECTION = 3;

	public final static int ORDERBY_AMOUNT = 4;

	public final static int ORDERBY_GLONENAME = 5;

	public final static int ORDERBY_DIRECTIONONE = 6;

	public final static int ORDERBY_AMOUNTONE = 7;

	public final static int ORDERBY_GLTWONAME = 8;

	public final static int ORDERBY_DIRECTIONTWO = 9;

	public final static int ORDERBY_AMOUNTTWO = 10;

	public final static int ORDERBY_GLTHREENAME = 11;

	public final static int ORDERBY_DIRECTIONTHREE = 12;

	public final static int ORDERBY_AMOUNTTHREE = 13;

	public final static int ORDERBY_EXECUTEDATEDATE = 14;

	public final static int ORDERBY_INTERESTSTARTDATE = 15;

	public final static int ORDERBY_ABSTRACT = 16;

	public final static int ORDERBY_STATUSID = 17;

	public final static int ORDERBY_ID = 18;
	
	
	//------------------------------中交增加---------------------------------
	public final static int ORDERBY_GLFOURNAME = 19;

	public final static int ORDERBY_DIRECTIONFOUR = 20;

	public final static int ORDERBY_AMOUNTFOUR = 21;

	
	
	//---------------------------------------------------------------------

	public Sett_TransGeneralLedgerDAO()
	{
		super.strTableName = "SETT_TRANSGENERALLEDGER";
	}

	public long add(TransGeneralLedgerInfo info) throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			info.setID(getSett_TransGeneralLedgerID());

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
			strSQLBuffer.append("NCLIENTID,");
			strSQLBuffer.append("NACCOUNTID,");
			strSQLBuffer.append("NDIRECTION,");
			strSQLBuffer.append("MAMOUNT,");
			strSQLBuffer.append("NGENERALLEDGERONE,");
			strSQLBuffer.append("NDIRONE,");
			strSQLBuffer.append("MONE,");
			strSQLBuffer.append("NGENERALLEDGERTWO,");
			strSQLBuffer.append("NDIRTWO,");
			strSQLBuffer.append("MTWO,");
			strSQLBuffer.append("NGENERALLEDGERTHREE,");
			strSQLBuffer.append("NDIRTHREE,");
			strSQLBuffer.append("MTHREE,");
			strSQLBuffer.append("SVOUCHERNO,");
			strSQLBuffer.append("SVOUCHERPWD,");
			strSQLBuffer.append("SBILLNO,");
			strSQLBuffer.append("NBILLTYPEID,");
			strSQLBuffer.append("NBILLBANKID,");
			strSQLBuffer.append("SPAYEXTBANKNO,");
			strSQLBuffer.append("SRECEIVEEXTBANKNO,");
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
			strSQLBuffer.append("NSTATUSID,");
			strSQLBuffer.append("MSUMFORSEARCH,");
			//---------------------------中交加入---------------------------------
			strSQLBuffer.append("NGENERALLEDGERFOUR,");
			strSQLBuffer.append("NDIRFOUR,");
			strSQLBuffer.append("MFOUR");
			//----------------------------------------------------------------------

			strSQLBuffer.append(") VALUES \n");
			strSQLBuffer.append(
				"( ?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,sysdate,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?) \n");

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

	private int addDatatoPrepareStatement(TransGeneralLedgerInfo info, PreparedStatement stmt, int operation)
		throws Exception
	{

		PreparedStatement ps = stmt;
		int index = 1;

		ps.setLong(index++, info.getID());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setString(index++, info.getTransNo());
		ps.setLong(index++, info.getTransActionTypeID());
		ps.setLong(index++, info.getClientID());
		ps.setLong(index++, info.getAccountID());
		ps.setLong(index++, info.getDirection());
		ps.setDouble(index++, info.getAmount());
		ps.setLong(index++, info.getGeneralLedgerOne());
		ps.setLong(index++, info.getDirOne());
		ps.setDouble(index++, info.getAmountOne());
		ps.setLong(index++, info.getGeneralLedgerTwo());
		ps.setLong(index++, info.getDirTwo());
		ps.setDouble(index++, info.getAmountTwo());
		ps.setLong(index++, info.getGeneralLedgerThree());
		ps.setLong(index++, info.getDirThree());
		ps.setDouble(index++, info.getAmountThree());
		ps.setString(index++, info.getVoucherNo());
		ps.setString(index++, info.getVoucherPWD());
		ps.setString(index++, info.getBillNo());
		ps.setLong(index++, info.getBillTypeID());
		ps.setLong(index++, info.getBillBankID());
		ps.setString(index++, info.getPayExtBankNo());
		ps.setString(index++, info.getReceiveExtBankNo());
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
		ps.setString(index++, info.getCancelCheckAbstract());
		ps.setString(index++, info.getConfirmAbstract());
		ps.setLong(index++, info.getStatusID());
		ps.setDouble(index++, info.getSumForSearch());
		//---------------------------中交项目----------------------------------
		ps.setLong(index++, info.getGeneralLedgerFour());
		ps.setLong(index++, info.getDirFour());
		ps.setDouble(index++, info.getAmountFour());
		
		//----------------------------------------------------------------

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
	public Collection findByConditions(TransGeneralLedgerInfo conditionInfo, int orderByType, boolean isDesc)
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

			if (conditionInfo.getTransNo() != null && conditionInfo.getTransNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND STRANSNO = '" + conditionInfo.getTransNo() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getTransActionTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NTRANSACTIONTYPEID = " + conditionInfo.getTransActionTypeID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getClientID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCLIENTID = " + conditionInfo.getClientID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getAccountID() != -1)
			{
				strWhereSQLBuffer.append(" AND NACCOUNTID = " + conditionInfo.getAccountID() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getDirection() != -1)
			{
				strWhereSQLBuffer.append(" AND NDIRECTION = " + conditionInfo.getDirection() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getAmount() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MAMOUNT = " + conditionInfo.getAmount() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getGeneralLedgerOne() != -1)
			{
				strWhereSQLBuffer.append(" AND NGENERALLEDGERONE = " + conditionInfo.getGeneralLedgerOne() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getDirOne() != -1)
			{
				strWhereSQLBuffer.append(" AND NDIRONE = " + conditionInfo.getDirOne() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getAmountOne() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MONE = " + conditionInfo.getAmountOne() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getGeneralLedgerTwo() != -1)
			{
				strWhereSQLBuffer.append(" AND NGENERALLEDGERTWO = " + conditionInfo.getGeneralLedgerTwo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getDirTwo() != -1)
			{
				strWhereSQLBuffer.append(" AND NDIRTWO = " + conditionInfo.getDirTwo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAmountTwo() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MTWO = " + conditionInfo.getAmountTwo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getGeneralLedgerThree() != -1)
			{
				strWhereSQLBuffer.append(" AND NGENERALLEDGERTHREE = " + conditionInfo.getGeneralLedgerThree() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getDirThree() != -1)
			{
				strWhereSQLBuffer.append(" AND NDIRTHREE = " + conditionInfo.getDirThree() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAmountThree() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MTHREE = " + conditionInfo.getAmountThree() + " \n");
				isNeedWhere = true;
			}
			//---------------------------中  交  加  入-------------------------------
			if (conditionInfo.getGeneralLedgerFour()!= -1)
			{
				strWhereSQLBuffer.append(" AND NGENERALLEDGERFOUR = " + conditionInfo.getGeneralLedgerFour() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getDirFour()!= -1)
			{
				strWhereSQLBuffer.append(" AND NDIRFOUR = " + conditionInfo.getDirFour() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getAmountFour() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MFOUR = " + conditionInfo.getAmountFour() + " \n");
				isNeedWhere = true;
			}
			//----------------------------------------------------------------------------

			if (conditionInfo.getVoucherNo() != null && conditionInfo.getVoucherNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SVOUCHERNO = " + conditionInfo.getVoucherNo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getVoucherPWD() != null && conditionInfo.getVoucherPWD().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SVOUCHERPWD = " + conditionInfo.getVoucherPWD() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getBillNo() != null && conditionInfo.getBillNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SBILLNO = " + conditionInfo.getBillNo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getBillTypeID() != -1)
			{
				strWhereSQLBuffer.append(" AND NBILLTYPEID = " + conditionInfo.getBillTypeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getBillBankID() != -1)
			{
				strWhereSQLBuffer.append(" AND NBILLBANKID = " + conditionInfo.getBillBankID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getPayExtBankNo() != null && conditionInfo.getPayExtBankNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SPAYEXTBANKNO = " + conditionInfo.getPayExtBankNo() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getReceiveExtBankNo() != null && conditionInfo.getReceiveExtBankNo().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SRECEIVEEXTBANKNO = " + conditionInfo.getReceiveExtBankNo() + " \n");
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

			if (conditionInfo.getInputUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NINPUTUSERID = " + conditionInfo.getInputUserID() + " \n");
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

			if (conditionInfo.getCheckUserID() != -1)
			{
				strWhereSQLBuffer.append(" AND NCHECKUSERID = " + conditionInfo.getCheckUserID() + " \n");
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
				strWhereSQLBuffer.append(" AND SABSTRACT = " + conditionInfo.getAbstract() + " \n");
				isNeedWhere = true;
			}
			if (conditionInfo.getCheckAbstract() != null && conditionInfo.getCheckAbstract().length() > 0)
			{
				strWhereSQLBuffer.append(" AND SCHECKABSTRACT = '" + conditionInfo.getCheckAbstract() + "' \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCancelCheckAbstract() != null && conditionInfo.getCancelCheckAbstract().length() > 0)
			{
				strWhereSQLBuffer.append(
					" AND SCANCLECHECKABSTRACT = '" + conditionInfo.getCancelCheckAbstract() + "' \n");
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

			if (conditionInfo.getSumForSearch() != 0.0)
			{
				strWhereSQLBuffer.append(" AND MSUMFORSEARCH = " + conditionInfo.getSumForSearch() + " \n");
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
				case ORDERBY_CLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nClientID \n");
					}
					break;
				case ORDERBY_DIRECTION :
					{
						strSQLBuffer.append(" ORDER BY NDIRECTION \n");
					}
					break;

				case ORDERBY_AMOUNT :
					{
						strSQLBuffer.append(" ORDER BY MAMOUNT \n");
					}
					break;
				case ORDERBY_GLONENAME :
					{
						strSQLBuffer.append(" ORDER BY NGENERALLEDGERONE \n");
					}
					break;
				case ORDERBY_DIRECTIONONE :
					{
						strSQLBuffer.append(" ORDER BY NDIRONE \n");
					}
					break;
				case ORDERBY_AMOUNTONE :
					{
						strSQLBuffer.append(" ORDER BY MONE \n");
					}
					break;
				case ORDERBY_GLTWONAME :
					{
						strSQLBuffer.append(" ORDER BY NGENERALLEDGERTWO \n");
					}
					break;
				case ORDERBY_DIRECTIONTWO :
					{
						strSQLBuffer.append(" ORDER BY NDIRTWO \n");
					}
					break;
				case ORDERBY_AMOUNTTWO :
					{
						strSQLBuffer.append(" ORDER BY MTWO \n");
					}
					break;
				case ORDERBY_GLTHREENAME :
					{
						strSQLBuffer.append(" ORDER BY NGENERALLEDGERTHREE \n");
					}
					break;
				case ORDERBY_DIRECTIONTHREE :
					{
						strSQLBuffer.append(" ORDER BY NDIRTHREE \n");
					}
					break;
				case ORDERBY_AMOUNTTHREE :
					{
						strSQLBuffer.append(" ORDER BY MTHREE \n");
					}
					break;
				//--------------------中交增加-------------------------------------
				case ORDERBY_GLFOURNAME :
				{
					strSQLBuffer.append(" ORDER BY NGENERALLEDGERFOUR \n");
				}
				break;
				case ORDERBY_DIRECTIONFOUR :
				{
					strSQLBuffer.append(" ORDER BY NDIRFOUR \n");
				}
				break;
				case ORDERBY_AMOUNTFOUR :
				{
					strSQLBuffer.append(" ORDER BY MFOUR \n");
				}
				break;
					
					
					
					
					
					
				//---------------------------------------------------------------	
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

			result = this.getInfoFromResultSet(rs,1);
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
	public TransGeneralLedgerInfo findByID(long ltransID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransGeneralLedgerInfo result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();

			/*
			 *  modified 20121113 zk BUG #16275 电子单据柜，总账业务打印，借方和贷方科目都没有取出来值
			 *  总账业务关联账户表查询出账户对应的科目号和账户类型
			 */
			strSQLBuffer.append("SELECT st.*,sa.naccounttypeid,sa.ssubject FROM \n");
			strSQLBuffer.append(strTableName + " st, sett_account sa \n");
			strSQLBuffer.append("WHERE st.naccountid = sa.id(+) and st.ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, ltransID);
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs,2);

			if (collTemp != null && collTemp.size() > 0)
			{
				Iterator itTemp = collTemp.iterator();
				result = (TransGeneralLedgerInfo) itTemp.next();
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

	private Collection getInfoFromResultSet(ResultSet rs,int type) throws Exception
	{

		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransGeneralLedgerInfo info = new TransGeneralLedgerInfo();

			info.setID(rs.getLong("ID"));
			info.setOfficeID(rs.getLong("NOFFICEID"));
			info.setCurrencyID(rs.getLong("NCURRENCYID"));
			info.setTransNo(rs.getString("STRANSNO"));
			info.setTransActionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
			info.setClientID(rs.getLong("NCLIENTID"));
			info.setAccountID(rs.getLong("NACCOUNTID"));
			info.setDirection(rs.getLong("NDIRECTION"));
			info.setAmount(rs.getDouble("MAMOUNT"));
			info.setGeneralLedgerOne(rs.getLong("NGENERALLEDGERONE"));
			info.setDirOne(rs.getLong("NDIRONE"));
			info.setAmountOne(rs.getDouble("MONE"));
			info.setGeneralLedgerTwo(rs.getLong("NGENERALLEDGERTWO"));
			info.setDirTwo(rs.getLong("NDIRTWO"));
			info.setAmountTwo(rs.getDouble("MTWO"));
			info.setGeneralLedgerThree(rs.getLong("NGENERALLEDGERTHREE"));
			info.setDirThree(rs.getLong("NDIRTHREE"));
			info.setAmountThree(rs.getDouble("MTHREE"));
			info.setVoucherNo(rs.getString("SVOUCHERNO"));
			info.setVoucherPWD(rs.getString("SVOUCHERPWD"));
			info.setBillNo(rs.getString("SBILLNO"));
			info.setBillTypeID(rs.getLong("NBILLTYPEID"));
			info.setBillBankID(rs.getLong("NBILLBANKID"));
			info.setPayExtBankNo(rs.getString("SPAYEXTBANKNO"));
			info.setReceiveExtBankNo(rs.getString("SRECEIVEEXTBANKNO"));
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
			info.setCancelCheckAbstract(rs.getString("SCANCLECHECKABSTRACT"));
			info.setConfirmAbstract(rs.getString("SCONFIRMABSTRACT"));
			info.setStatusID(rs.getLong("NSTATUSID"));
			info.setSumForSearch(rs.getDouble("MSUMFORSEARCH"));
			//------------------------中交加入----------------------------------
			info.setGeneralLedgerFour(rs.getLong("NGENERALLEDGERFOUR"));
			info.setAmountFour(rs.getDouble("MFOUR"));
			info.setDirFour(rs.getLong("NDIRFOUR"));
			
			if(type == 2){
				info.setNAccountTypeID(rs.getLong("NACCOUNTTYPEID"));
				info.setSSubject(rs.getString("SSUBJECT"));
			}

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
	public TransGeneralLedgerInfo match(TransGeneralLedgerInfo conditionInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransGeneralLedgerInfo result = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer(256);

			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");

			strSQLBuffer.append(" WHERE");
			strSQLBuffer.append(" NSTATUSID = " + conditionInfo.getStatusID()+ " \n");
			strSQLBuffer.append(" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
			strSQLBuffer.append(" AND NCURRENCYID = " + conditionInfo.getCurrencyID() + " \n");
			strSQLBuffer.append(" AND NINPUTUSERID != " + conditionInfo.getInputUserID() + " \n");

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

			strSQLBuffer.append(" AND NCLIENTID = " + conditionInfo.getClientID() + " \n");
			strSQLBuffer.append(" AND NACCOUNTID = " + conditionInfo.getAccountID() + " \n");
			strSQLBuffer.append(" AND NDIRECTION = " + conditionInfo.getDirection() + " \n");
			strSQLBuffer.append(" AND MAMOUNT = " + conditionInfo.getAmount() + " \n");
			strSQLBuffer.append(" AND NGENERALLEDGERONE = " + conditionInfo.getGeneralLedgerOne() + " \n");
			strSQLBuffer.append(" AND NDIRONE = " + conditionInfo.getDirOne() + " \n");
			strSQLBuffer.append(" AND MONE = " + conditionInfo.getAmountOne() + " \n");
			strSQLBuffer.append(" AND NGENERALLEDGERTWO = " + conditionInfo.getGeneralLedgerTwo() + " \n");
			strSQLBuffer.append(" AND NDIRTWO = " + conditionInfo.getDirTwo() + " \n");
			strSQLBuffer.append(" AND MTWO = " + conditionInfo.getAmountTwo() + " \n");
			//-------------------------------中交增加----------------------------------------
			strSQLBuffer.append(" AND NGENERALLEDGERFOUR = " + conditionInfo.getGeneralLedgerFour() + " \n");
			strSQLBuffer.append(" AND NDIRFOUR = " + conditionInfo.getDirFour() + " \n");
			strSQLBuffer.append(" AND MFOUR = " + conditionInfo.getAmountFour() + " \n");
			
			//-------------------------------------------------------------------------
			strSQLBuffer.append(" AND NGENERALLEDGERTHREE = " + conditionInfo.getGeneralLedgerThree() + " \n");
			strSQLBuffer.append(" AND NDIRTHREE = " + conditionInfo.getDirThree() + " \n");
			strSQLBuffer.append(" AND MTHREE = " + conditionInfo.getAmountThree() + " \n");

			strSQLBuffer.append(" ORDER BY ID DESC \n");

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			Collection collTemp = this.getInfoFromResultSet(rs,1);

			if (collTemp != null && collTemp.size() > 0)
			{
				Iterator itTemp = collTemp.iterator();
				result = (TransGeneralLedgerInfo) itTemp.next();
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
	public long update(TransGeneralLedgerInfo info) throws Exception
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
			strSQLBuffer.append("NCLIENTID=?,");
			strSQLBuffer.append("NACCOUNTID=?,");
			strSQLBuffer.append("NDIRECTION=?,");
			strSQLBuffer.append("MAMOUNT=?,");
			strSQLBuffer.append("NGENERALLEDGERONE=?,");
			strSQLBuffer.append("NDIRONE=?,");
			strSQLBuffer.append("MONE=?,");
			strSQLBuffer.append("NGENERALLEDGERTWO=?,");
			strSQLBuffer.append("NDIRTWO=?,");
			strSQLBuffer.append("MTWO=?,");
			strSQLBuffer.append("NGENERALLEDGERTHREE=?,");
			strSQLBuffer.append("NDIRTHREE=?,");
			strSQLBuffer.append("MTHREE=?,");
			strSQLBuffer.append("SVOUCHERNO=?,");
			strSQLBuffer.append("SVOUCHERPWD=?,");
			strSQLBuffer.append("SBILLNO=?,");
			strSQLBuffer.append("NBILLTYPEID=?,");
			strSQLBuffer.append("NBILLBANKID=?,");
			strSQLBuffer.append("SPAYEXTBANKNO=?,");
			strSQLBuffer.append("SRECEIVEEXTBANKNO=?,");
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
			strSQLBuffer.append("NSTATUSID=?,");
			strSQLBuffer.append("MSUMFORSEARCH=?,");
//			---------------------------中交加入---------------------------------
			strSQLBuffer.append("NGENERALLEDGERFOUR=?,");
			strSQLBuffer.append("NDIRFOUR=?,");
			strSQLBuffer.append("MFOUR=?");

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

}

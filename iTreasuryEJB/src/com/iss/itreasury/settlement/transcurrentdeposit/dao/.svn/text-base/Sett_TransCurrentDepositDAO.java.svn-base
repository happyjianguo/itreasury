package com.iss.itreasury.settlement.transcurrentdeposit.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.dataconvert.util.Log;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_BatchCheckDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositQueryInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-9-8
 */
public class Sett_TransCurrentDepositDAO extends SettlementDAO
{
	public final static int ORDERBY_TRANSACTIONNOID = 0;
	public final static int ORDERBY_PAYACCOUNTNO = 1;
	public final static int ORDERBY_PAYCLIENTNAME = 2;
	public final static int ORDERBY_CONSIGNVOUCHERNO = 3;
	public final static int ORDERBY_AMOUNT = 4;
	public final static int ORDERBY_INTERESTSTARTDATE = 5;
	public final static int ORDERBY_EXECUTEDATEDATE = 6;
	public final static int ORDERBY_ABSTRACT = 7;
	public final static int ORDERBY_STATUSID = 8;
	public final static int ORDERBY_BILLNO = 9;
	public final static int ORDERBY_BANKCHEQUENO = 10;
	public final static int ORDERBY_RECEIVEACCOUNTNO = 11;
	public final static int ORDERBY_RECEIVECLIENTNAME = 12;
	public final static int ORDERBY_SDECLARATIONNO = 13;
	private StringBuffer strSelectSQLBuffer = null;
	public Sett_TransCurrentDepositDAO()
	{
		super.strTableName = "sett_TransCurrentDeposit";
		strSelectSQLBuffer = new StringBuffer();
		strSelectSQLBuffer.append("SELECT id, nofficeid, ncurrencyid, stransno, ntransactiontypeid, nreceiveclientid,\n");
		strSelectSQLBuffer.append("nreceiveaccountid, npayclientid, npayaccountid, nbankid, \n");
		strSelectSQLBuffer.append("mamount, dtintereststart, dtexecute, dtmodify, dtinput, ncashflowid, ninputuserid, \n");
		strSelectSQLBuffer.append("ncheckuserid, nsignuserid, nconfirmuserid, nconfirmofficeid, sabstract, \n");
		strSelectSQLBuffer.append("scheckabstract, sconfirmabstract, nstatusid, nsinglebankaccounttypeid,\n");
		strSelectSQLBuffer.append("nconsignreceivetypeid, sinstructionno, sconsignvoucherno, sconsignpassword,\n");
		strSelectSQLBuffer.append("sbankchequeno, sdeclarationno, sbillno, nbilltypeid, nbillbankid, sextbankno,\n");
		strSelectSQLBuffer.append("sExtAccountNo, sExtClientName, sRemitInProvince, sRemitInCity, sRemitInBank,\n");
		//通存通兑增加
		strSelectSQLBuffer.append("IsDifOffice, PayOfficeID, ReceiveOfficeID, ParentOfficeID,\n");
		strSelectSQLBuffer.append("nSubClientID,nabstractid,nReckoningTypeID,sReckoningBillTypeDesc,BudgetItemID,mCommissionAmount,nCommissionType,sCommissionTransNo,isurgent,spayeebankexchangeno,spayeebankcnapsno,spayeebankorgno,lsource,sapplycode FROM \n");
	}
	/**
	 * 方法说明：新增活期交易记录
	 * @param info:Sett_TransCurrentDepositInfo
	 * @return : long - 返回新增活期交易记录ID
	 * @throws IException
	 */
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public long add(TransCurrentDepositInfo info) throws Exception
	{
		log.info("in Sett_TransCurrentDepositDAO.add()");
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			long id = getSett_TransCurrentDepositID();
			info.setId(id);
			con = getConnection();
			/**Important: If any field in database changed, please correct them at here and fucntion:addDatatoPrepareStatement*/
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("INSERT INTO  \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("(" + getFieldsSQLString(-1, DAO_OPERATION_ADD, null) + " \n");
			strSQLBuffer.append(") VALUES \n");
			//strSQLBuffer.append("( ?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
			//预算新增
			//strSQLBuffer.append("( ?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
			//通存通兑新增4个
			strSQLBuffer.append("( ?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//ps will be modified in function addDatatoPrepareStatement 
			addDatatoPrepareStatement(info, ps, DAO_OPERATION_ADD);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(rs);
			cleanup(con);
		}
		return info.getId();
	}
	/**
	 * 方法说明：修改活期交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回活期交易记录ID
	 * @throws IException
	 */
	public long update(TransCurrentDepositInfo info) throws Exception
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
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_UPDATE, null) + " \n");
			strSQLBuffer.append(" WHERE ID = ?");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			int lastIndex = addDatatoPrepareStatement(info, ps, DAO_OPERATION_UPDATE);
			//Set value of where condition
			ps.setLong(lastIndex, info.getId());
			ps.executeUpdate();
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
		return info.getId();
	}
	/**
	 * 方法说明：删除活期交易记录
	 * @param info :Sett_TransCurrentDepositInfo
	 * @return : long - 返回被删除活期交易记录ID
	 * @throws IException
	 */
	public long delete(long id) throws Exception
	{
		//logic delete, so just update status as deleted
		this.updateStatus(id, SETTConstant.TransactionStatus.DELETED);
		return id;
	}
	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * @param transCurrentDepositID
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransCurrentDepositInfo findByID(long transCurrentDepositID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransCurrentDepositInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(strSelectSQLBuffer);
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, transCurrentDepositID);
			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				//Must just have single macthed record
				res = (TransCurrentDepositInfo) ((ArrayList) c).get(0);
			}
			System.out.print(strSQL);
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
		return res;
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
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	/**
	 * 方法说明：修改复核人
	 * @param 
	 * @return long  
	 * @throws IException
	 */
	public long updateCheckUser(long id, long checkUserID) throws Exception
	{
		//long lReturn = -1;
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
			strSQLBuffer.append("ncheckuserid = ?, dtModify = sysdate \n");
			strSQLBuffer.append(" WHERE ID = ? \n");
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, checkUserID);
			ps.setLong(2, id);
			ps.executeUpdate();
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		finally
		{
			cleanup(ps);
			cleanup(con);
		}
		return id;
	}
	/**
	 * 方法说明：根据状态查找
	 * @param StatusID  0已删除1暂存2保存（未复核）3已复核4未签认5已签认6已确认7已勾账
	 * @return Collection 
	 * @throws IException
	 */
	public Collection findByStatus(long statusID) throws Exception
	{
		log.info("findByStatus");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(strSelectSQLBuffer);
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE nStatusID = ? \n");
			String strSQL = strSQLBuffer.toString();
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, statusID);
			log.info(strSQL);
			rs = ps.executeQuery();
			res = getInfoFromResultSet(rs);
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
		return res;
	}
	/**
	 * 方法说明：根据ID查找修改时间
	 * @param transCurrentDepositID  
	 * @return Timestamp 
	 * @throws IException
	 */
	public Timestamp findTouchDate(long transCurrentDepositID) throws Exception
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
			ps.setLong(1, transCurrentDepositID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
			}
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
		return res;
	}
	/**
	 * 方法说明：根据查询条件组合，查询出符合的记录
	 *  Method findByConditions.
	 * @param sett_TransCurrentDepositInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findByConditions(TransCurrentDepositInfo sett_TransCurrentDepositInfo, int orderByType, boolean isDesc) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList resList = new ArrayList();
		Collection res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT ");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, null));
			strSQLBuffer.append(" FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, sett_TransCurrentDepositInfo) + " \n");
			boolean isNeedOrderBy = true;
			switch (orderByType)
			{
				case ORDERBY_TRANSACTIONNOID :
					{
						strSQLBuffer.append(" ORDER BY sTransNo \n");
					}
					break;
				case ORDERBY_PAYACCOUNTNO :
					{
						strSQLBuffer.append(" ORDER BY nPayAccountID \n");
					}
					break;
				case ORDERBY_PAYCLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nPayClientID \n");
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
				case ORDERBY_BILLNO :
					{
						strSQLBuffer.append(" ORDER BY sBillNo \n");
					}
					break;
				case ORDERBY_BANKCHEQUENO :
					{
						strSQLBuffer.append(" ORDER BY sBankChequeNo \n");
					}
					break;
				case ORDERBY_RECEIVEACCOUNTNO :
					{
						strSQLBuffer.append(" ORDER BY nReceiveAccountID \n");
					}
					break;
				case ORDERBY_RECEIVECLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nReceiveClientID \n");
					}
					break;
				case ORDERBY_SDECLARATIONNO :
				{
					strSQLBuffer.append(" ORDER BY sdeclarationno \n");
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
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//				ps = this.addDatatoPrepareStatement(sett_TransCurrentDepositInfo, ps);
			rs = ps.executeQuery();
			res = getInfoFromResultSet(rs);
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
		return res;
	}
	/**
	 * 方法说明：批量复核查询，查询出符合的记录
	 *  Method findBatchByConditions.
	 * @param sett_TransCurrentDepositInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findBatchByConditions(TransCurrentDepositInfo sett_TransCurrentDepositInfo, int orderByType, boolean isDesc) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList resList = new ArrayList();
		Collection res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			Sett_BatchCheckDAO sett_batchcheckdao = new Sett_BatchCheckDAO();
			boolean lIsBatchCheck = sett_batchcheckdao.isBatchCheck(sett_TransCurrentDepositInfo.getTransactionTypeID());
			System.out.print("#######################        lIsBatchCheck="+lIsBatchCheck);
			if (lIsBatchCheck)
			{
				log.info("***批量方式***");
				strSQLBuffer.append("SELECT ");
				strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_BATCHCHECK, null));
				strSQLBuffer.append(" FROM \n");
				strSQLBuffer.append(strTableName + " \n");
				strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_BATCHCHECK, sett_TransCurrentDepositInfo) + " \n");
			}
			else
			{
				log.info("***普通方式***");
				strSQLBuffer.append("SELECT ");
				strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, null));
				strSQLBuffer.append(" FROM \n");
				strSQLBuffer.append(strTableName + " \n");
				strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, sett_TransCurrentDepositInfo) + " \n");
			}
			boolean isNeedOrderBy = true;
			switch (orderByType)
			{
				case ORDERBY_TRANSACTIONNOID :
					{
						strSQLBuffer.append(" ORDER BY sTransNo \n");
					}
					break;
				case ORDERBY_PAYACCOUNTNO :
					{
						strSQLBuffer.append(" ORDER BY nPayAccountID \n");
					}
					break;
				case ORDERBY_PAYCLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nPayClientID \n");
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
				case ORDERBY_BILLNO :
					{
						strSQLBuffer.append(" ORDER BY sBillNo \n");
					}
					break;
				case ORDERBY_BANKCHEQUENO :
					{
						strSQLBuffer.append(" ORDER BY sBankChequeNo \n");
					}
					break;
				case ORDERBY_RECEIVEACCOUNTNO :
					{
						strSQLBuffer.append(" ORDER BY nReceiveAccountID \n");
					}
					break;
				case ORDERBY_RECEIVECLIENTNAME :
					{
						strSQLBuffer.append(" ORDER BY nReceiveClientID \n");
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
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
			ps = con.prepareStatement(strSQL);
			//				ps = this.addDatatoPrepareStatement(sett_TransCurrentDepositInfo, ps);
			rs = ps.executeQuery();
			res = getInfoFromResultSet(rs);
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
		return res;
	}

	/**
	 * 方法说明：根据查询条件匹配
	 *  Method  match.
	 * @param Sett_TransCurrentDepositInfo info
	 * @return Sett_TransCurrentDepositInfo
	 */
	public TransCurrentDepositInfo match(long transactionType, TransCurrentDepositInfo info) throws Exception
	{
		Log.print("继续测试"+ info.getExtAccountNo());
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransCurrentDepositInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(strSelectSQLBuffer);
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" WHERE \n");
			String str = getFieldsSQLString(transactionType, DAO_OPERATION_MATCH, info);
			
			System.out.println("qlan***********************======test======"+str);
			if (str == null) //no need match condition
				return null;
			strSQLBuffer.append(str);
			String strSQL = strSQLBuffer.toString();
			log.info(strSQL);
		
			ps = con.prepareStatement(strSQL);
			
			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				//Must just have single macthed record
				res = (TransCurrentDepositInfo) ((ArrayList) c).get(0);
			}
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
		System.out.println("qlan***********************======restest======"+res);
		return res;
		
	}
	/**
	 * @return last index of PerpareStatement
	 * @param stmt this is a reference and param will be modified 
	 * 
	 */
	private int addDatatoPrepareStatement(TransCurrentDepositInfo info, PreparedStatement stmt, int operation) throws Exception
	{
		PreparedStatement ps = stmt;
		int index = 1;
		ps.setLong(index++, info.getId());
		ps.setLong(index++, info.getOfficeID());
		ps.setLong(index++, info.getCurrencyID());
		ps.setString(index++, info.getTransNo());
		ps.setLong(index++, info.getTransactionTypeID());
		ps.setLong(index++, info.getReceiveClientID());
		ps.setLong(index++, info.getReceiveAccountID());
		ps.setLong(index++, info.getPayClientID());
		ps.setLong(index++, info.getPayAccountID());
		//ps.setLong(index++, info.getExternalAccountID());
		ps.setLong(index++, info.getBankID());
		ps.setDouble(index++, info.getAmount());
		ps.setTimestamp(index++, info.getInterestStartDate());
		ps.setTimestamp(index++, info.getExecuteDate());
		//		ps.setTimestamp(index++, info.getModifyTime());
		if (operation == DAO_OPERATION_ADD)
		{
			ps.setTimestamp(index++, info.getInputDate());
		}
		ps.setLong(index++, info.getCashFlowID());
		ps.setLong(index++, info.getInputUserID());
		ps.setLong(index++, info.getCheckUserID());
		ps.setLong(index++, info.getCheckUserID());
		ps.setLong(index++, info.getSignUserID());
		ps.setLong(index++, info.getConfirmOfficeID());
		ps.setString(index++,info.getAbstractStr());	
		ps.setString(index++, info.getCheckAbstractStr());
		ps.setString(index++, info.getConfirmAbstractStr());
		ps.setLong(index++, info.getStatusID());
		ps.setLong(index++, info.getSingleBankAccountTypeID());
		ps.setLong(index++, info.getConsignReceiveTypeID());
		ps.setString(index++, info.getInstructionNo());
		ps.setString(index++, info.getConsignVoucherNo());
		ps.setString(index++, info.getConsignPassword());
		ps.setString(index++, info.getBankChequeNo());
		ps.setString(index++, info.getDeclarationNo());
		ps.setString(index++, info.getBillNo());
		ps.setLong(index++, info.getBillTypeID());
		ps.setLong(index++, info.getBillBankID());
		ps.setString(index++, info.getExtBankNo());
		ps.setLong(index++, info.getAbstractID());
		//外部客户新增字段
		ps.setString(index++, info.getExtAccountNo());
		ps.setString(index++, info.getExtClientName());
		ps.setString(index++, info.getRemitInProvince());
		ps.setString(index++, info.getRemitInCity());
		ps.setString(index++, info.getRemitInBank());
		//下属单位客户ID
		ps.setLong(index++, info.getSubClientID());
		
		//清算类型
		ps.setLong(index++, info.getReckoningTypeID());
		//清算表单的凭证类型
		ps.setString(index++, info.getReckoningBillTypeDesc());
		//预算新增
		ps.setLong(index++, info.getBudgetItemID());
        
        //手续费
        ps.setDouble(index++, info.getCommissionAmount());
        ps.setLong(index++, info.getCommissionType());
        ps.setString(index++, info.getCommissionTransNo());
        ps.setLong(index++, info.getIsUrgent());
        ps.setString(index++, info.getSpayeebankexchangeno());
        ps.setString(index++, info.getSpayeebankcnapsno());
        ps.setString(index++, info.getSpayeebankorgno());
        
        //通存通兑相关
        ps.setLong(index++, info.getIsDifOffice());//是否通存通兑
        ps.setLong(index++, info.getPayOfficeID());//付方办事处
        ps.setLong(index++, info.getReceiveOfficeID());//收方办事处
        ps.setLong(index++, info.getParentOfficeID());//总部
        
        //数据来源 xiang
        if(operation == DAO_OPERATION_ADD){
        	ps.setLong(index++, info.getLSource());
        	ps.setString(index++, info.getSApplyCode());
        }
        
		System.out.println("**********************===="+index);
		return index;
	}
	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransCurrentDepositInfo info = new TransCurrentDepositInfo();
			info.setId(rs.getLong("id"));
			info.setOfficeID(rs.getLong("nofficeid"));
			info.setCurrencyID(rs.getLong("ncurrencyid"));
			info.setTransNo(rs.getString("stransno"));
			info.setTransactionTypeID(rs.getLong("ntransactiontypeid"));
			info.setReceiveClientID(rs.getLong("nreceiveclientid"));
			info.setReceiveAccountID(rs.getLong("nreceiveaccountid"));
			info.setPayClientID(rs.getLong("npayclientid"));
			info.setPayAccountID(rs.getLong("npayaccountid"));
			//info.setExternalAccountID(rs.getLong("nexternalaccountid"));
			info.setBankID(rs.getLong("nbankid"));
			info.setAmount(rs.getDouble("mamount"));
			info.setInterestStartDate(rs.getTimestamp("dtintereststart"));
			info.setExecuteDate(rs.getTimestamp("dtexecute"));
			info.setModifyTime(rs.getTimestamp("dtmodify"));
			info.setInputDate(rs.getTimestamp("dtinput"));
			info.setCashFlowID(rs.getLong("ncashflowid"));
			info.setInputUserID(rs.getLong("ninputuserid"));
			info.setCheckUserID(rs.getLong("ncheckuserid"));
			info.setCheckUserID(rs.getLong("nsignuserid"));
			info.setSignUserID(rs.getLong("nconfirmuserid"));
			info.setConfirmOfficeID(rs.getLong("nconfirmofficeid"));
			info.setAbstractStr(rs.getString("sabstract"));
			info.setCheckAbstractStr(rs.getString("scheckabstract"));
			info.setConfirmAbstractStr(rs.getString("sconfirmabstract"));
			info.setStatusID(rs.getLong("nstatusid"));
			info.setSingleBankAccountTypeID(rs.getLong("nsinglebankaccounttypeid"));
			info.setConsignReceiveTypeID(rs.getLong("nconsignreceivetypeid"));
			info.setInstructionNo(rs.getString("sinstructionno"));
			info.setConsignVoucherNo(rs.getString("sconsignvoucherno"));
			info.setConsignPassword(rs.getString("sconsignpassword"));
			info.setBankChequeNo(rs.getString("sbankchequeno"));
			info.setDeclarationNo(rs.getString("sdeclarationno"));
			info.setBillNo(rs.getString("sbillno"));
			info.setBillTypeID(rs.getLong("nbilltypeid"));
			info.setBillBankID(rs.getLong("nbillbankid"));
			info.setExtBankNo(rs.getString("sextbankno"));
			info.setAbstractID(rs.getLong("nabstractid"));
			//外部客户新增字段
			info.setExtAccountNo(rs.getString("sExtAccountNo"));
			info.setExtClientName(rs.getString("sExtClientName"));
			info.setRemitInProvince(rs.getString("sRemitInProvince"));
			info.setRemitInCity(rs.getString("sRemitInCity"));
			info.setRemitInBank(rs.getString("sRemitInBank"));
			//下属单位客户ID
			info.setSubClientID(rs.getLong("nSubClientID"));
			
			info.setReckoningTypeID(rs.getLong("nReckoningTypeID"));//清算类型
			info.setReckoningBillTypeDesc(rs.getString("sReckoningBillTypeDesc"));//清算表单的凭证类型
			
			//预算新增
			info.setBudgetItemID(rs.getLong("BudgetItemID"));//预算项目ID
            
            //手续费
            info.setCommissionAmount(rs.getDouble("mCommissionAmount"));
            info.setCommissionType(rs.getLong("nCommissionType"));
            info.setCommissionTransNo(rs.getString("sCommissionTransNo"));
            info.setIsUrgent(rs.getLong("isurgent"));
            info.setSpayeebankexchangeno(rs.getString("spayeebankexchangeno"));
			info.setSpayeebankcnapsno(rs.getString("spayeebankcnapsno"));
			info.setSpayeebankorgno(rs.getString("spayeebankorgno"));
			
			//通存通兑
			info.setIsDifOffice(rs.getLong("isdifoffice"));
			info.setPayOfficeID(rs.getLong("payofficeid"));
			info.setReceiveOfficeID(rs.getLong("receiveofficeid"));
			info.setParentOfficeID(rs.getLong("parentofficeid"));
			
			//数据来源
			info.setLSource(rs.getLong("lsource"));
			info.setSApplyCode(rs.getString("sapplycode"));
			list.add(info);
		}
		return list;
	}
	private Collection getQueryInfoFromResultSet(ResultSet rs) throws Exception
	{
		ArrayList resList = new ArrayList();
		while (rs.next())
		{
			TransCurrentDepositQueryInfo info = new TransCurrentDepositQueryInfo();
			int index = 1;
			info.setId(rs.getLong(index++));
			info.setTransNo(rs.getString(index++));
			info.setTransactionTypeID(rs.getLong(index++));
			info.setPayAccountID(rs.getLong(index++));
			long clientID = rs.getLong(index++);
			info.setPayClientID(clientID);
			info.setPayClientName(NameRef.getClientNameByID(clientID));
			info.setConsignVoucherNo(rs.getString(index++));
			info.setAmount(rs.getDouble(index++));
			info.setInterestStartDate(rs.getTimestamp(index++));
			info.setExecuteDate(rs.getTimestamp(index++));
			info.setAbstractStr(rs.getString(index++));
			info.setStatusID(rs.getLong(index++));
			resList.add(info);
		}
		return resList;
	}
	/**
	 *@param1 transactionType transaction type, sucha as check payment, -1 mean
	 *that this function do not need know transaction type
	*/
	private String getFieldsSQLString(long transactionType, int operation, TransCurrentDepositInfo info)
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		String strRes = "";
		if ((operation == DAO_OPERATION_ADD || operation == DAO_OPERATION_FIND || operation == DAO_OPERATION_BATCHCHECK) && info == null)
		{
			
			strSQLBuffer.append(" ID, \n");
			strSQLBuffer.append("nOfficeID, \n");
			strSQLBuffer.append("nCurrencyID, \n");
			strSQLBuffer.append("sTransNo, \n");
			strSQLBuffer.append("nTransactionTypeID, \n");
			strSQLBuffer.append("nReceiveClientID, \n");
			strSQLBuffer.append("nReceiveAccountID, \n");
			strSQLBuffer.append("nPayClientID, \n");
			strSQLBuffer.append("nPayAccountID, \n");
			//strSQLBuffer.append("nExternalAccountID, \n");
			strSQLBuffer.append("nBankID, \n");
			strSQLBuffer.append("mAmount, \n");
			strSQLBuffer.append("dtInterestStart, \n");
			strSQLBuffer.append("dtExecute, \n");
			strSQLBuffer.append("dtModify, \n");
			strSQLBuffer.append("dtInput, \n");
			strSQLBuffer.append("nCashFlowID, \n");
			strSQLBuffer.append("nInputUserID, \n");
			strSQLBuffer.append("nCheckUserID, \n");
			strSQLBuffer.append("nSignUserID, \n");
			strSQLBuffer.append("nConfirmUserID, \n");
			strSQLBuffer.append("nConfirmOfficeID, \n");
			strSQLBuffer.append("sAbstract, \n");
			strSQLBuffer.append("sCheckAbstract, \n");
			strSQLBuffer.append("sConfirmAbstract, \n");
			strSQLBuffer.append("nStatusID, \n");
			strSQLBuffer.append("nSingleBankAccountTypeID, \n");
			strSQLBuffer.append("nConsignReceiveTypeID, \n");
			strSQLBuffer.append("sInstructionNo, \n");
			strSQLBuffer.append("sConsignVoucherNo, \n");
			strSQLBuffer.append("sConsignPassword, \n");
			strSQLBuffer.append("sBankChequeNo, \n");
			strSQLBuffer.append("sDeclarationNo, \n");
			strSQLBuffer.append("sBillNo, \n");
			strSQLBuffer.append("nBillTypeID, \n");
			strSQLBuffer.append("nBillBankID, \n");
			strSQLBuffer.append("sExtBankNo, \n");
			strSQLBuffer.append("nAbstractID, \n");
			//外部客户新增字段
			strSQLBuffer.append("sExtAccountNo, \n");
			strSQLBuffer.append("sExtClientName, \n");
			strSQLBuffer.append("sRemitInProvince, \n");
			strSQLBuffer.append("sRemitInCity, \n");
			strSQLBuffer.append("sRemitInBank,  \n");
			//下属单位客户ID
			strSQLBuffer.append("nSubClientID,  \n");
			
			//清算类型
			strSQLBuffer.append("nReckoningTypeID, \n");
			//清算表单的凭证类型
			//strSQLBuffer.append("sReckoningBillTypeDesc \n");
			//预算修改
			strSQLBuffer.append("sReckoningBillTypeDesc, \n");
			strSQLBuffer.append("budgetItemID, \n");
            
            //手续费
            strSQLBuffer.append("mCommissionAmount, \n");
            strSQLBuffer.append("nCommissionType, \n");
            strSQLBuffer.append("sCommissionTransNo, \n");
            strSQLBuffer.append("isurgent,\n");
            strSQLBuffer.append("spayeebankexchangeno, \n");
            strSQLBuffer.append("spayeebankcnapsno,  \n");
            strSQLBuffer.append("spayeebankorgno, \n");
            
            //通存通兑相关
            strSQLBuffer.append("IsDifOffice,\n");//是否通存通兑
            strSQLBuffer.append("PayOfficeID, \n");//付方办事处
            strSQLBuffer.append("ReceiveOfficeID,  \n");//收方办事处
            strSQLBuffer.append("ParentOfficeID, \n");//总部
            
            //数据来源
            strSQLBuffer.append("lsource, \n");
            strSQLBuffer.append("sapplycode \n");
            
			strRes = strSQLBuffer.toString();
		}
		else if (operation == DAO_OPERATION_UPDATE)
		{
			strSQLBuffer.append(" ID = ?, \n");
			strSQLBuffer.append("nOfficeID = ?, \n");
			strSQLBuffer.append("nCurrencyID = ?, \n");
			strSQLBuffer.append("sTransNo = ?, \n");
			strSQLBuffer.append("nTransactionTypeID = ?, \n");
			strSQLBuffer.append("nReceiveClientID = ?, \n");
			strSQLBuffer.append("nReceiveAccountID = ?, \n");
			strSQLBuffer.append("nPayClientID = ?, \n");
			strSQLBuffer.append("nPayAccountID = ?, \n");
			//			strSQLBuffer.append("nExternalAccountID = ?, \n");
			strSQLBuffer.append("nBankID = ?, \n");
			strSQLBuffer.append("mAmount = ?, \n");
			strSQLBuffer.append("dtInterestStart = ?, \n");
			strSQLBuffer.append("dtExecute = ?, \n");
			strSQLBuffer.append("dtModify = sysdate, \n");
			strSQLBuffer.append("nCashFlowID = ?, \n");
			strSQLBuffer.append("nInputUserID = ?, \n");
			strSQLBuffer.append("nCheckUserID = ?, \n");
			strSQLBuffer.append("nSignUserID = ?, \n");
			strSQLBuffer.append("nConfirmUserID = ?, \n");
			strSQLBuffer.append("nConfirmOfficeID = ?, \n");
			strSQLBuffer.append("sAbstract = ?, \n");
			strSQLBuffer.append("sCheckAbstract = ?, \n");
			strSQLBuffer.append("sConfirmAbstract = ?, \n");
			strSQLBuffer.append("nStatusID = ?, \n");
			strSQLBuffer.append("nSingleBankAccountTypeID = ?, \n");
			strSQLBuffer.append("nConsignReceiveTypeID = ?, \n");
			strSQLBuffer.append("sInstructionNo = ?, \n");
			strSQLBuffer.append("sConsignVoucherNo = ?, \n");
			strSQLBuffer.append("sConsignPassword = ?, \n");
			strSQLBuffer.append("sBankChequeNo = ?, \n");
			strSQLBuffer.append("sDeclarationNo = ?, \n");
			strSQLBuffer.append("sBillNo = ?, \n");
			strSQLBuffer.append("nBillTypeID = ?, \n");
			strSQLBuffer.append("nBillBankID = ?, \n");
			strSQLBuffer.append("sExtBankNo = ?,  \n");
			strSQLBuffer.append("nAbstractID = ?, \n");
			//外部客户新增字段
			strSQLBuffer.append("sExtAccountNo = ?, \n");
			strSQLBuffer.append("sExtClientName = ?, \n");
			strSQLBuffer.append("sRemitInProvince = ?, \n");
			strSQLBuffer.append("sRemitInCity = ?, \n");
			strSQLBuffer.append("sRemitInBank = ?, \n");
			//下属单位客户ID
			strSQLBuffer.append("nSubClientID = ?, \n");
			
			//清算类型
			strSQLBuffer.append("nReckoningTypeID=?, \n");
			//清算表单的凭证类型
			strSQLBuffer.append("sReckoningBillTypeDesc=?, \n");
			
			//预算修改			
			strSQLBuffer.append("budgetItemID = ?, \n");
			
            //手续费
            strSQLBuffer.append("mCommissionAmount = ?, \n");
            strSQLBuffer.append("nCommissionType = ?, \n");
            strSQLBuffer.append("sCommissionTransNo = ?, \n");
            strSQLBuffer.append("isurgent = ?, \n");
            strSQLBuffer.append("spayeebankexchangeno = ?, \n");
            strSQLBuffer.append("spayeebankcnapsno = ?,  \n");
            strSQLBuffer.append("spayeebankorgno = ?, \n");
            
            //通存通兑相关
            strSQLBuffer.append("IsDifOffice = ?,\n");//是否通存通兑
            strSQLBuffer.append("PayOfficeID = ?, \n");//付方办事处
            strSQLBuffer.append("ReceiveOfficeID = ?,  \n");//收方办事处
            strSQLBuffer.append("ParentOfficeID = ? \n");//总部
            
            strRes = strSQLBuffer.toString();
		}
		else if (operation == DAO_OPERATION_FIND && info != null)
		{
			
			//			if any fields is -1,empty string or 0.0, this field will be thought that this is not a finding condition and skip it
			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;
			long id = info.getId();
			if (id != -1)
			{
				strSQLBuffer.append(" AND ID = " + id + " \n");
				isNeedWhere = true;
			}
			long officeID = info.getOfficeID();
			if (officeID != -1)
			{
				strSQLBuffer.append(" AND nofficeID =" + officeID + " \n");
				isNeedWhere = true;
			}
			long currencyID = info.getCurrencyID();
			if (currencyID != -1)
			{
				strSQLBuffer.append(" AND nCurrencyID = " + currencyID + " \n");
				isNeedWhere = true;
			}
			String strTransNo = info.getTransNo();
			if (strTransNo != null && strTransNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sTransNo = '" + strTransNo + "' \n");
				isNeedWhere = true;
			}
			long transactionTypeID = info.getTransactionTypeID();
			if (transactionTypeID != -1)
			{
				strSQLBuffer.append(" AND nTransactionTypeID = " + transactionTypeID + " \n");
				isNeedWhere = true;
			}
			long receiveClientID = info.getReceiveClientID();
			if (receiveClientID != -1)
			{
				strSQLBuffer.append(" AND nReceiveClientID =" + receiveClientID + " \n");
				isNeedWhere = true;
			}
			long receiveAccountID = info.getReceiveAccountID();
			if (receiveAccountID != -1)
			{
				strSQLBuffer.append(" AND nReceiveAccountID =" + receiveAccountID + " \n");
				isNeedWhere = true;
			}
			long payClientID = info.getPayClientID();
			if (payClientID != -1)
			{
				strSQLBuffer.append(" AND nPayClientID =" + payClientID + " \n");
				isNeedWhere = true;
			}
			long payAccountID = info.getPayAccountID();
			if (payAccountID != -1)
			{
				strSQLBuffer.append(" AND nPayAccountID =" + payAccountID + " \n");
				isNeedWhere = true;
			}
			//			long externalAccountID = info.getExternalAccountID();
			//			if (externalAccountID != -1)
			//			{
			//				strSQLBuffer.append(
			//					" AND nExternalAccountID ='" + externalAccountID + "' \n");
			//				isNeedWhere = true;
			//			}
			long banckId = info.getBankID();
			if (banckId != -1)
			{
				strSQLBuffer.append(" AND nBankID =" + banckId + " \n");
				isNeedWhere = true;
			}
			double amount = info.getAmount();
			if (amount != 0.0)
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(amount);
				strSQLBuffer.append(" AND mAmount =" + strAmountTemp + " \n");
				isNeedWhere = true;
			}
			Timestamp interestStartDate = info.getInterestStartDate();
			if (interestStartDate != null)
			{
				String time = interestStartDate.toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND dtInterestStart = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			Timestamp execDate = info.getExecuteDate();
			if (execDate != null)
			{
				String time = execDate.toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND dtExecute = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			long cashFlowID = info.getCashFlowID();
			if (cashFlowID != -1)
			{
				strSQLBuffer.append(" AND nCashFlowID =" + cashFlowID + " \n");
				isNeedWhere = true;
			}
			long inputUserID = info.getInputUserID();
			if (inputUserID != -1)
			{
				strSQLBuffer.append(" AND nInputUserID =" + inputUserID + " \n");
				isNeedWhere = true;
			}
			long checkUserID = info.getCheckUserID();
			if (checkUserID != -1)
			{

				strSQLBuffer.append(" AND nCheckUserID  in (" + checkUserID + ","+Constant.MachineUser.CheckUser+") \n");
				isNeedWhere = true;

			}
			long signUserID = info.getSignUserID();
			if (signUserID != -1)
			{
				strSQLBuffer.append(" AND nSignUserID =" + signUserID + " \n");
				isNeedWhere = true;
			}
			long confirmUerID = info.getConfirmUserID();
			if (confirmUerID != -1)
			{
				strSQLBuffer.append(" AND nConfirmUserID =" + confirmUerID + " \n");
				isNeedWhere = true;
			}
			long confirmOfficeID = info.getConfirmOfficeID();
			if (confirmOfficeID != -1)
			{
				strSQLBuffer.append(" AND nConfirmOfficeID =" + confirmOfficeID + " \n");
				isNeedWhere = true;
			}
			String strAbs = info.getAbstractStr();
			if (strAbs != null && strAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sAbstract = '" + strAbs + "' \n");
				isNeedWhere = true;
			}
			String strCheckAbs = info.getCheckAbstractStr();
			if (strCheckAbs != null && strCheckAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sCheckAbstract = '" + strCheckAbs + "' \n");
				isNeedWhere = true;
			}
			String strConfirmAbs = info.getConfirmAbstractStr();
			if (strConfirmAbs != null && strConfirmAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sConfirmAbstract = '" + strConfirmAbs + "' \n");
				isNeedWhere = true;
			}
			long[] statusIDs = info.getStatusIDs();
			if (statusIDs != null)
			{
				strSQLBuffer.append(" AND (nStatusID =" + statusIDs[0] + " \n");
				for (int i = 1; i < statusIDs.length; i++)
				{
					strSQLBuffer.append(" OR nStatusID =" + statusIDs[i] + " \n");
				}
				strSQLBuffer.append(")");
				isNeedWhere = true;
			}
			long singleBankAccountTypeID = info.getSingleBankAccountTypeID();
			if (singleBankAccountTypeID != -1)
			{
				strSQLBuffer.append(" AND nSingleBankAccountTypeID =" + singleBankAccountTypeID + " \n");
				isNeedWhere = true;
			}
			long consignReceiveTypeID = info.getConsignReceiveTypeID();
			if (consignReceiveTypeID != -1)
			{
				strSQLBuffer.append(" AND nSingleBankAccountTypeID =" + consignReceiveTypeID + " \n");
				isNeedWhere = true;
			}
			String strInstructionNo = info.getInstructionNo();
			if (strInstructionNo != null && strInstructionNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sInstructionNo = '" + strInstructionNo + "' \n");
				isNeedWhere = true;
			}
			String consignVoucherNo = info.getConsignVoucherNo();
			if (consignVoucherNo != null && consignVoucherNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sConsignVoucherNo = '" + consignVoucherNo + "' \n");
				isNeedWhere = true;
			}
			String consignPS = info.getConsignPassword();
			if (consignPS != null && consignPS.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sConsignPassword = '" + consignPS + "' \n");
				isNeedWhere = true;
			}
			String bankChequeNo = info.getBankChequeNo();
			if (bankChequeNo != null && bankChequeNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sBankChequeNo = '" + bankChequeNo + "' \n");
				isNeedWhere = true;
			}
			String strDeclarationNo = info.getDeclarationNo();
			if (strDeclarationNo != null && strDeclarationNo.compareToIgnoreCase("") != 0)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND sDeclarationNo = '" + strDeclarationNo + "' \n");
			}
			String strBillNo = info.getBillNo();
			if (strBillNo != null && strBillNo.compareToIgnoreCase("") != 0)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND sBillNo = '" + strBillNo + "' \n");
			}
			long billTypeID = info.getBillTypeID();
			if (billTypeID != -1)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND nBillTypeID =" + billTypeID + " \n");
			}
			long billbankID = info.getBillBankID();
			if (billbankID != -1)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND nBillTypeID =" + billbankID + " \n");
			}
			String strExtBankNo = info.getExtBankNo();
			if (strExtBankNo != null && strExtBankNo.compareToIgnoreCase("") != 0)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND sExtBankNo = '" + strExtBankNo + "' \n");
			}
			strRes = strSQLBuffer.toString();
			//Cut first "AND" if there is
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
			if (isNeedWhere)
				strRes = " WHERE " + strRes;
		}
		else if (operation == DAO_OPERATION_MATCH && info != null)
		{
			
			System.out.println("qlantest4+++++++++++++++++++++++++++++");
			System.out.println("transactionType=="+transactionType);
			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;
			if (info.isNeedMatch(transactionType, "id"))
			{
				strSQLBuffer.append(" AND ID =" + info.getId() + " \n");
				isNeedWhere = true;
			}
//--------------------------中交加入---------------------------
//			匹配外部账户信息。
			
			//Boxu update 2007-9-18 银行付款落地处理业务复核不走下面的条件, 在业务复核页面没有外部账户的录入
			if(!(transactionType == SETTConstant.TransactionType.BANKPAY_NOTONLINE))
			{
				if (info.isNeedMatch(transactionType, "strExtAccountNo"))
				{
					if (info.getExtAccountNo().compareToIgnoreCase("") == 0)
						strSQLBuffer.append(" AND sExtAccountNo IS NULL \n");
					else
						strSQLBuffer.append(" AND sExtAccountNo ='" + info.getExtAccountNo() + "' \n");
					isNeedWhere = true;
				}
				log.print(info.getExtClientName());
				if (info.isNeedMatch(transactionType, "strExtClientName"))
				{
					if (info.getExtClientName().compareToIgnoreCase("") == 0)
						strSQLBuffer.append(" AND SEXTCLIENTNAME IS NULL \n");
					else
					strSQLBuffer.append(" AND SEXTCLIENTNAME ='" + info.getExtClientName() + "' \n");
					Log.print("再测试"+info.getExtClientName());
					isNeedWhere = true;
				}
			}
			
//------------------------------------------------------------------
			if (info.isNeedMatch(transactionType, "nOfficeID"))
			{
				strSQLBuffer.append(" AND nOfficeID = " + info.getOfficeID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nCurrencyID"))
			{
				strSQLBuffer.append(" AND nCurrencyID = " + info.getCurrencyID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sTransNo"))
			{
				String strTransNo = info.getTransNo();
				if (strTransNo == null || "".equals(strTransNo))
					strSQLBuffer.append(" AND sTransNo IS NULL \n");
				else
					strSQLBuffer.append(" AND sTransNo = '" + strTransNo + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nTransactionTypeID"))
			{
				strSQLBuffer.append(" AND nTransactionTypeID =" + info.getTransactionTypeID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nReceiveClientID"))
			{
				strSQLBuffer.append(" AND nReceiveClientID = " + info.getReceiveClientID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nReceiveAccountID"))
			{
				strSQLBuffer.append(" AND nReceiveAccountID = " + info.getReceiveAccountID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nPayClientID"))
			{
				strSQLBuffer.append(" AND nPayClientID = " + info.getPayClientID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nSubClientID"))
			{
				strSQLBuffer.append(" AND nSubClientID = " + info.getSubClientID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nPayAccountID"))
			{
				strSQLBuffer.append(" AND nPayAccountID =" + info.getPayAccountID() + " \n");
				isNeedWhere = true;
			}
			//			if (info.isNeedMatch(transactionType, "externalAccountID()"))
			//				strSQLBuffer.append(
			//					" AND nExternalAccountID ='"
			//						+ info.getExternalAccountID()
			//						+ "' ");
			if (info.isNeedMatch(transactionType, "nBankID"))
			{
				strSQLBuffer.append(" AND nBankID = " + info.getBankID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "mAmount"))
			{
				DecimalFormat df=new DecimalFormat("#.00");
				String strAmountTemp = df.format(info.getAmount());
				strSQLBuffer.append(" AND mAmount = " +strAmountTemp  + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "dtInterestStart"))
			{
				Timestamp interestStartDate = info.getInterestStartDate();
				if (interestStartDate == null)
					strSQLBuffer.append(" AND dtInterestStart IS NULL ");
				else
				{
					String time = interestStartDate.toString();
					time = time.substring(0, 10);
					strSQLBuffer.append(" AND dtInterestStart = to_date('" + time + "','yyyy-mm-dd') \n");
				}
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "dtExecute"))
			{
				Timestamp executeDate = info.getExecuteDate();
				String time = executeDate.toString();
				time = time.substring(0, 10);
				if (executeDate == null)
					strSQLBuffer.append(" AND dtExecute IS NULL ");
				else
					strSQLBuffer.append(" AND dtExecute = to_date('" + executeDate + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			//			+ "dtModify = ?,"
			if (info.isNeedMatch(transactionType, "nCashFlowID"))
			{
				strSQLBuffer.append(" AND nCashFlowID = " + info.getCashFlowID() + " \n");
				isNeedWhere = true;
			}
			//当前操作用户不能等于交易信息录入用户
			if (info.isNeedMatch(transactionType, "nInputUserID"))
			{
				strSQLBuffer.append(" AND nInputUserID != " + info.getInputUserID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nCheckUserID"))
			{
				strSQLBuffer.append(" AND nCheckUserID = " + info.getCheckUserID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nSignUserID"))
			{
				strSQLBuffer.append(" AND nSignUserID = " + info.getSignUserID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nConfirmUserID"))
			{
				strSQLBuffer.append(" AND nConfirmUserID = " + info.getConfirmUserID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nConfirmOfficeID"))
			{
				strSQLBuffer.append(" AND nConfirmOfficeID = " + info.getConfirmOfficeID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sAbstract"))
			{
				String abstractStr = info.getAbstractStr();
				if (abstractStr.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sAbstract IS NULL \n");
				else
					strSQLBuffer.append(" AND  sAbstract = '" + abstractStr + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sCheckAbstract"))
			{
				String checkAbstractStr = info.getCheckAbstractStr();
				if (checkAbstractStr.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sCheckAbstract IS NULL \n");
				else
					strSQLBuffer.append(" AND sCheckAbstract = '" + checkAbstractStr + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sConfirmAbstract"))
			{
				String confirmAbstractStr = info.getConfirmAbstractStr();
				if (confirmAbstractStr.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sConfirmAbstract IS NULL \n");
				else
					strSQLBuffer.append(" AND sConfirmAbstract = '" + confirmAbstractStr + "' \n");
				isNeedWhere = true;
			}
			//Match operation is for check operation so that statusID is always as "SAVE"  
			if (info.isNeedMatch(transactionType, "nStatusID"))
			{				
				strSQLBuffer.append(" AND nStatusID = " + info.getStatusID());
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nSingleBankAccountTypeID"))
			{
				strSQLBuffer.append(" AND nSingleBankAccountTypeID = " + info.getSingleBankAccountTypeID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nConsignReceiveTypeID"))
			{
				strSQLBuffer.append(" AND nConsignReceiveTypeID = " + info.getConsignReceiveTypeID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sInstructionNo"))
			{
				String instructionNo = info.getConfirmAbstractStr();
				if (instructionNo.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sInstructionNo IS NULL \n");
				else
					strSQLBuffer.append(" AND sInstructionNo = '" + instructionNo + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sConsignVoucherNo"))
			{
				String ConsignVoucherNo = info.getConsignVoucherNo();
				if (ConsignVoucherNo.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sConsignVoucherNo IS NULL \n");
				else
					strSQLBuffer.append(" AND sConsignVoucherNo = '" + ConsignVoucherNo + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sConsignPassword"))
			{
				String ConsignPassword = info.getConsignPassword();
				if (ConsignPassword.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sConsignPassword IS NULL \n");
				else
					strSQLBuffer.append(" AND sConsignPassword = '" + ConsignPassword + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sBankChequeNo"))
			{
				String bankChequeNo = info.getBankChequeNo();
				if (bankChequeNo.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sBankChequeNo IS NULL \n");
				else
					strSQLBuffer.append(" AND sBankChequeNo = '" + bankChequeNo + "' \n");
				isNeedWhere = true;
			}
			
			if (info.isNeedMatch(transactionType, "sDeclarationNo"))
			{
				String declarationNo = info.getDeclarationNo();
				
				if (declarationNo.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sDeclarationNo IS NULL \n");
				else
					strSQLBuffer.append(" AND sDeclarationNo = '" + declarationNo + "' \n");
//				if (declarationNo.compareToIgnoreCase("") != 0)
//                {
//				    //如果输入了报单号作为匹配条件，则校验；不输入则不作校验。
//                    strSQLBuffer.append(" AND sDeclarationNo = '" + declarationNo + "' \n");
//                }
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sBillNo"))
			{
				String billNo = info.getBillNo();
				if (billNo.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sBillNo IS NULL ");
				else
					strSQLBuffer.append(" AND sBillNo = '" + billNo + "' \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nBillTypeID"))
			{
				strSQLBuffer.append(" AND nBillTypeID = " + info.getBillTypeID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "nBillBankID"))
			{
				strSQLBuffer.append(" AND nBillBankID = " + info.getBillBankID() + " \n");
				isNeedWhere = true;
			}
			if (info.isNeedMatch(transactionType, "sExtBankNo"))
			{
				String extBankNo = info.getExtBankNo();
				if (extBankNo.compareToIgnoreCase("") == 0)
					strSQLBuffer.append(" AND sExtBankNo IS NULL \n");
				else
					strSQLBuffer.append(" AND sExtBankNo = '" + extBankNo + "' \n");
				isNeedWhere = true;
			}
			if (!isNeedWhere)
				return null; //Because there is no need match condition at here, query can not be continued
			strRes = strSQLBuffer.toString();
			//Cut first "AND" if there is
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
		}
		///////////////  Add By GQFang due to Batch Check Reqirement
		else if (operation == DAO_OPERATION_BATCHCHECK && info != null)
		{
			
			//			if any fields is -1,empty string or 0.0, this field will be thought that this is not a finding condition and skip it
			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;
			long id = info.getId();
			if (id != -1)
			{
				strSQLBuffer.append(" AND ID = " + id + " \n");
				isNeedWhere = true;
			}
			long officeID = info.getOfficeID();
			if (officeID != -1)
			{
				strSQLBuffer.append(" AND nofficeID =" + officeID + " \n");
				isNeedWhere = true;
			}
			long currencyID = info.getCurrencyID();
			if (currencyID != -1)
			{
				strSQLBuffer.append(" AND nCurrencyID = " + currencyID + " \n");
				isNeedWhere = true;
			}
			String strTransNo = info.getTransNo();
			if (strTransNo != null && strTransNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sTransNo = '" + strTransNo + "' \n");
				isNeedWhere = true;
			}
			long transactionTypeID = info.getTransactionTypeID();
			if (transactionTypeID != -1)
			{
				strSQLBuffer.append(" AND nTransactionTypeID = " + transactionTypeID + " \n");
				isNeedWhere = true;
			}
			long receiveClientID = info.getReceiveClientID();
			if (receiveClientID != -1)
			{
				strSQLBuffer.append(" AND nReceiveClientID =" + receiveClientID + " \n");
				isNeedWhere = true;
			}
			long receiveAccountID = info.getReceiveAccountID();
			if (receiveAccountID != -1)
			{
				strSQLBuffer.append(" AND nReceiveAccountID =" + receiveAccountID + " \n");
				isNeedWhere = true;
			}
			long payClientID = info.getPayClientID();
			if (payClientID != -1)
			{
				strSQLBuffer.append(" AND nPayClientID =" + payClientID + " \n");
				isNeedWhere = true;
			}
			long payAccountID = info.getPayAccountID();
			if (payAccountID != -1)
			{
				strSQLBuffer.append(" AND nPayAccountID =" + payAccountID + " \n");
				isNeedWhere = true;
			}
			//			long externalAccountID = info.getExternalAccountID();
			//			if (externalAccountID != -1)
			//			{
			//				strSQLBuffer.append(
			//					" AND nExternalAccountID ='" + externalAccountID + "' \n");
			//				isNeedWhere = true;
			//			}
			long banckId = info.getBankID();
			if (banckId != -1)
			{
				strSQLBuffer.append(" AND nBankID =" + banckId + " \n");
				isNeedWhere = true;
			}
			double amount = info.getAmount();
			if (amount != 0.0)
			{
				strSQLBuffer.append(" AND mAmount =" + amount + " \n");
				isNeedWhere = true;
			}
			Timestamp interestStartDate = info.getInterestStartDate();
			if (interestStartDate != null)
			{
				String time = interestStartDate.toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND dtInterestStart = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			Timestamp execDate = info.getExecuteDate();
			if (execDate != null)
			{
				String time = execDate.toString();
				time = time.substring(0, 10);
				strSQLBuffer.append(" AND dtExecute = to_date('" + time + "','yyyy-mm-dd') \n");
				isNeedWhere = true;
			}
			long cashFlowID = info.getCashFlowID();
			if (cashFlowID != -1)
			{
				strSQLBuffer.append(" AND nCashFlowID =" + cashFlowID + " \n");
				isNeedWhere = true;
			}
			long inputUserID = info.getInputUserID();
			if (inputUserID != -1)
			{
				strSQLBuffer.append(" AND nInputUserID <>" + inputUserID + " \n");
				isNeedWhere = true;
			}
			long checkUserID = info.getCheckUserID();
			if (checkUserID != -1)
			{
				strSQLBuffer.append(" AND nCheckUserID =" + checkUserID + " \n");
				isNeedWhere = true;
			}
			long signUserID = info.getSignUserID();
			if (signUserID != -1)
			{
				strSQLBuffer.append(" AND nSignUserID =" + signUserID + " \n");
				isNeedWhere = true;
			}
			long confirmUerID = info.getConfirmUserID();
			if (confirmUerID != -1)
			{
				strSQLBuffer.append(" AND nConfirmUserID =" + confirmUerID + " \n");
				isNeedWhere = true;
			}
			long confirmOfficeID = info.getConfirmOfficeID();
			if (confirmOfficeID != -1)
			{
				strSQLBuffer.append(" AND nConfirmOfficeID =" + confirmOfficeID + " \n");
				isNeedWhere = true;
			}
			String strAbs = info.getAbstractStr();
			if (strAbs != null && strAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sAbstract = '" + strAbs + "' \n");
				isNeedWhere = true;
			}
			String strCheckAbs = info.getCheckAbstractStr();
			if (strCheckAbs != null && strCheckAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sCheckAbstract = '" + strCheckAbs + "' \n");
				isNeedWhere = true;
			}
			String strConfirmAbs = info.getConfirmAbstractStr();
			if (strConfirmAbs != null && strConfirmAbs.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sConfirmAbstract = '" + strConfirmAbs + "' \n");
				isNeedWhere = true;
			}
			long[] statusIDs = info.getStatusIDs();
			if (statusIDs != null)
			{
				strSQLBuffer.append(" AND (nStatusID =" + statusIDs[0] + " \n");
				for (int i = 1; i < statusIDs.length; i++)
				{
					strSQLBuffer.append(" OR nStatusID =" + statusIDs[i] + " \n");
				}
				strSQLBuffer.append(")");
				isNeedWhere = true;
			}
			long singleBankAccountTypeID = info.getSingleBankAccountTypeID();
			if (singleBankAccountTypeID != -1)
			{
				strSQLBuffer.append(" AND nSingleBankAccountTypeID =" + singleBankAccountTypeID + " \n");
				isNeedWhere = true;
			}
			long consignReceiveTypeID = info.getConsignReceiveTypeID();
			if (consignReceiveTypeID != -1)
			{
				strSQLBuffer.append(" AND nSingleBankAccountTypeID =" + consignReceiveTypeID + " \n");
				isNeedWhere = true;
			}
			String strInstructionNo = info.getInstructionNo();
			if (strInstructionNo != null && strInstructionNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sInstructionNo = '" + strInstructionNo + "' \n");
				isNeedWhere = true;
			}
			String consignVoucherNo = info.getConsignVoucherNo();
			if (consignVoucherNo != null && consignVoucherNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sConsignVoucherNo = '" + consignVoucherNo + "' \n");
				isNeedWhere = true;
			}
			String consignPS = info.getConsignPassword();
			if (consignPS != null && consignPS.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sConsignPassword = '" + consignPS + "' \n");
				isNeedWhere = true;
			}
			String bankChequeNo = info.getBankChequeNo();
			if (bankChequeNo != null && bankChequeNo.compareToIgnoreCase("") != 0)
			{
				strSQLBuffer.append(" AND sBankChequeNo = '" + bankChequeNo + "' \n");
				isNeedWhere = true;
			}
			String strDeclarationNo = info.getDeclarationNo();
			if (strDeclarationNo != null && strDeclarationNo.compareToIgnoreCase("") != 0)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND sDeclarationNo = '" + strDeclarationNo + "' \n");
			}
			String strBillNo = info.getBillNo();
			if (strBillNo != null && strBillNo.compareToIgnoreCase("") != 0)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND sBillNo = '" + strBillNo + "' \n");
			}
			long billTypeID = info.getBillTypeID();
			if (billTypeID != -1)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND nBillTypeID =" + billTypeID + " \n");
			}
			long billbankID = info.getBillBankID();
			if (billbankID != -1)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND nBillTypeID =" + billbankID + " \n");
			}
			String strExtBankNo = info.getExtBankNo();
			if (strExtBankNo != null && strExtBankNo.compareToIgnoreCase("") != 0)
			{
				isNeedWhere = true;
				strSQLBuffer.append(" AND sExtBankNo = '" + strExtBankNo + "' \n");
			}
			strRes = strSQLBuffer.toString();
			//Cut first "AND" if there is
			if (strRes.startsWith(" AND"))
				strRes = strRes.substring(4);
			if (isNeedWhere)
				strRes = " WHERE " + strRes;
		}
		return strRes;
	}
	//	private String prepareParams(Sett_TransCurrentDepositInfo info, boolean isMatch, PreparedStatement ps){
	//		String res;
	//		StringBuffer sbFront = new StringBuffer();
	//		StringBuffer sbEnd = new StringBuffer();		
	//		
	//		String strAbs = info.getAbstractStr();
	//		double amount = info.getAmount();
	//		String bankChequeNo = info.getBankChequeNo();
	//		long banckId = info.getBankID(); 
	//		long billbankID = info.getBillBankID();
	//		String strBillNo = info.getBillNo();
	//		long billTypeID = info.getBillTypeID();
	//		long cashFlowID = info.getCashFlowID();
	//		String strCheckAbs = info.getCheckAbstractStr();
	//		long checkUserID = info.getCheckUserID();
	//		String strconfirmAbs = info.getConfirmAbstractStr();
	//		long confirmOfficeID = info.getConfirmOfficeID();
	//		long confirmUerID = info.getConfirmUserID();
	//		String consignPS = info.getConsignPassword();
	//		long consignReceiveTypeID = info.getConsignReceiveTypeID(); 
	//		String consignVoucherNo = info.getConsignVoucherNo(); 
	//		String strDeclarationNo = info.getDeclarationNo();
	//		Timestamp execDate = info.getExecuteDate();
	//		String strExtBankNo = info.getExtBankNo();
	//		long externalAccountID = info.getExternalAccountID();
	//		long inputUserID = info.getInputUserID();
	//		String strInstructionNo = info.getInstructionNo();
	//		Timestamp interestStartDate = info.getInterestStartDate();
	//		Timestamp modifyTime = info.getModifyTime();
	//		long officeID = info.getOfficeID();
	//		long payAccountID= info.getPayAccountID();
	//		long receiveAccountID = info.getReceiveAccountID();
	//		long signUserID = info.getSignUserID();
	//		long singleBankAccountTypeID = info.getSingleBankAccountTypeID();
	//		long statusID = info.getStatusID();
	//		long transactionTypeID = info.getTransactionTypeID();
	//		String strTransNo = info.getTransNo(); 
	//				
	//		if(isMatch){
	//			if(strAbs == null || strAbs.compareToIgnoreCase("") == 0){
	//			}
	//		}else{
	//		}
	//		
	//		return sbFront.toString(); 
	//		
	//	}
	public boolean isRepetitiveRecord(TransCurrentDepositInfo info) throws Exception
	{
		Collection c = this.findByConditions(info, -1, true);
		if (c != null && c.size() > 0)
			return true;
		else
			return false;
	}
	
	public TransCurrentDepositInfo findByTransNo(String strTransNo) throws Exception{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransCurrentDepositInfo res = null;
		try
		{
			con = getConnection();
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from "+ this.strTableName + " where nstatusid > 0 and stransno = '"+ strTransNo +"'");

			ps = con.prepareStatement(strSQLBuffer.toString());

			rs = ps.executeQuery();
			Collection c = getInfoFromResultSet(rs);
			if (c.size() == 0)
				return null;
			else
			{
				//Must just have single macthed record
				res = (TransCurrentDepositInfo) ((ArrayList) c).get(0);
			}
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
		return res;		
		
		
	}
	//add by zcwang 2007-3-29 网银指令接受后修改其状态
	public long updateStatusAndTransNo(FinanceInfo info) throws Exception
	{
	    long lReturn = -1;
	    Connection con = null;
		try
		{
			con = getConnection();
			OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
			lReturn = financeDao.updateStatusAndTransNo(con, info);
			cleanup(con);
		}
		finally
		{
			cleanup(con);
		}
		return lReturn;
		
	}
	//

	public long findstatusAndTransNO(FinanceInfo info) throws Exception
	{
	    long lReturn = -1;
	    Connection con = null;
		try
		{
			con = getConnection();
			OBFinanceInstrDao financeDao = new OBFinanceInstrDao();
			lReturn = financeDao.findstatusAndTransNO(con, info);
			cleanup(con);
		}
		finally
		{
			cleanup(con);
		}
		return lReturn;
		
	}
	
	public String queryTransCurrentDeposit(TransCurrentDepositInfo sett_TransCurrentDepositInfo){
		
		StringBuffer strSQLBuffer = new StringBuffer();
		
		strSQLBuffer.append("SELECT ");
		strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, null));
		strSQLBuffer.append(" FROM \n");
		strSQLBuffer.append(strTableName + " \n");
		strSQLBuffer.append(getFieldsSQLString(-1, DAO_OPERATION_FIND, sett_TransCurrentDepositInfo) + " \n");
		
		return strSQLBuffer.toString();
	}
}

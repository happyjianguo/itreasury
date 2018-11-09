/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.account.dataentity.AccountExtendInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.account.dataentity.QueryAccountConditionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.SETTConstant.AccountGroupType;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.FSWorkflowManager;
import com.iss.itreasury.util.Log4j;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sett_AccountDAO extends SettlementDAO {
	public final static int ORDERBY_ACCOUNTID = 1; // 账户ID

	public final static int ORDERBY_ACCOUNTNO = 2; // 账户编号

	public final static int ORDERBY_ACCOUNTTYPEID = 3; // 账户类型

	public final static int ORDERBY_CLIENTID = 4; // 客户

	public final static int ORDERBY_ACCOUNTNAME = 5; // 账户名称

	public final static int ORDERBY_OPENDATE = 6; // 开户日期

	public final static int ORDERBY_INPUTUSERID = 7; // 录入人

	public final static int ORDERBY_INPUTDATE = 8; // 录入日期

	public final static int ORDERBY_CHECKUSERID = 9; // 复核人

	public final static int ORDERBY_CHECKDATE = 10; // 复核日期

	public final static int ORDERBY_CHECKSTATUSID = 11; // 复核状态

	public final static int ORDERBY_STATUSID = 12; // 账户状态

	public final static int ORDERBY_ABSTRACT = 13; // 摘要

	public final static int ORDERBY_SUBJECT = 14; // 对应科目号

	public final static int ORDERBY_MAXSINGLEPAYAMOUNT = 15; // 单笔最高金额限制

	public final static int ORDERBY_MINSINGLEPAYAMOUNT = 16; // 单笔最低金额限制

	public final static int ORDERBY_STARTDATE = 17; // 开始日期

	public final static int ORDERBY_ENDDATE = 18; // 结束日期

	public final static int ORDERBY_BATCHUPDATEID = 19;// 批量修改序号

	public final static int ORDERBY_BATCHUPDATETYPEID = 20;// 批量修改内容

	Log4j log4j = null;

	public Sett_AccountDAO() {
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	public Sett_AccountDAO(Connection conn) {
		super(conn);
		log4j = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	}

	public static void main(java.lang.String[] args) throws Exception {
		// 在此处插入用来启动应用程序的代码。
		try {
			Sett_AccountDAO dao = new Sett_AccountDAO();
			AccountInfo info = new AccountInfo();
			//System.out.println(dao.updateCheckStatus(38, 100, 2, null));
		} catch (Exception e) {
		}
	}

	/**
	 * 方法说明：新增账户
	 * 
	 * @param ai:AccountInfo
	 * @return : long - 返回新增的账户ID
	 * @throws Exception
	 */
	public long add(AccountInfo ai) throws Exception {
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try {
			if (findSameAccountID(ai.getAccountNo(), ai.getOfficeID(), ai
					.getCurrencyID()) < 0) {// 说明没有重复的账户号
				// get the connection from Database
				conn = getConnection();
				// establish the insert sql string
				sbSQL = new StringBuffer();
				sbSQL
						.append(" insert into sett_Account( ID, sAccountNo, nOfficeID, nCurrencyID, nAccountTypeID, \n");
				sbSQL
						.append(" nClientID, sName, dtOpen, nInputUserID, dtInput, nCheckUserID,\n");
				sbSQL
						.append(" dtCheck, nCheckStatusID, nStatusID, sAbstract, sSubject,\n");
				sbSQL
						.append(" mMaxSinglePayAmount, mMinSinglePayAmount, nBatchUpdateID, nBatchUpdateTypeID, dtFinish ) \n");
				sbSQL.append(" values( ?,?,?,?,?,?,?, \n");
				sbSQL.append(" to_date('"
						+ DataFormat.getDateString(ai.getOpenDate())
						+ "','yyyy-mm-dd')  \n");
				sbSQL.append(" ,?, \n");
				sbSQL.append(" to_date('"
						+ DataFormat.getDateString(ai.getInputDate())
						+ "','yyyy-mm-dd')  \n");
				sbSQL.append(" ,?,?,?,?,?,?,?,?,?,?,?) \n");
				ps = conn.prepareStatement(sbSQL.toString());
				// get the maximum id
				lReturn = getNextID();
				ai.setAccountID(lReturn);
				// set the PreparedStatement arguments by the BankBillInfo
				// object
				setPrepareStatementByInfo(ai, ps, 1);
				ps.executeUpdate();
				log4j.info("add sett_account successfully. AccountID is "
						+ lReturn);
				cleanup(ps);

				// 修改 by leiyang (杨垒)(2007-06-11)
				AccountExtendInfo aei = ai.getAccountExtendInfo();
				if (aei != null) {
					long lnextId = getAccountExtendNextID();
					aei.setAccountExtendID(lnextId);
					aei.setAccountID(lReturn);
					sbSQL = new StringBuffer();
					sbSQL
							.append("insert into sett_AccountExtend(ID, AccountID, IsSoft, Status) \n");
					sbSQL.append(" values(?,?,?,?) \n");
					ps = conn.prepareStatement(sbSQL.toString());
					setPrepareStatementByInfo(aei, ps, 1);
					ps.executeUpdate();
					cleanup(ps);
				}
				cleanup(conn);
			}
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}

	/**
	 * 方法说明：修改账户信息
	 * 
	 * @param ai:AccountInfo
	 * @return : long - 返回账户ID
	 * @throws Exception
	 */
	public long update(AccountInfo ai) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL
					.append(" update sett_Account set sAccountNo = ?, nOfficeID = ?, nCurrencyID = ?, nAccountTypeID = ?, \n");
			sbSQL.append(" nClientID = ?, sName = ?, \n");
			sbSQL.append(" dtOpen = to_date('"
					+ DataFormat.getDateString(ai.getOpenDate())
					+ "','yyyy-mm-dd'), \n");
			sbSQL.append(" nInputUserID = ?, \n");
			sbSQL.append(" dtInput = to_date('"
					+ DataFormat.getDateString(ai.getInputDate())
					+ "','yyyy-mm-dd'), \n");
			sbSQL.append(" nCheckUserID = ?, \n");
			sbSQL
					.append(" dtCheck = ?, nCheckStatusID = ?, nStatusID = ?, sAbstract = ?, sSubject = ?,\n");
			sbSQL
					.append(" mMaxSinglePayAmount = ?, mMinSinglePayAmount = ?, nBatchUpdateID=?, nBatchUpdateTypeID=?, \n");
			sbSQL.append(" dtFinish = ? \n");
			sbSQL.append(" where ID = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			// set the PreparedStatement arguments by the BankBillInfo object
			setPrepareStatementByInfo(ai, ps, -1);
			ps.executeUpdate();
			cleanup(ps);

			// 修改 by leiyang (杨垒)(2007-06-11)
			AccountExtendInfo aei = ai.getAccountExtendInfo();
			if (aei != null) {
				sbSQL = new StringBuffer();
				sbSQL.append("select * from sett_AccountExtend \n");
				sbSQL.append(" where AccountID = ? \n");
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, aei.getAccountID());
				ResultSet rs = ps.executeQuery();
				long lAccountExtendId = -1;
				if (rs.next()) {
					lAccountExtendId = rs.getLong("ID");
				}
				cleanup(ps);
				cleanup(rs);

				if (lAccountExtendId == -1) {
					long lnextId = getAccountExtendNextID();
					aei.setAccountExtendID(lnextId);
					sbSQL = new StringBuffer();
					sbSQL
							.append("insert into sett_AccountExtend(ID, AccountID, IsSoft, Status) \n");
					sbSQL.append(" values(?,?,?,?) \n");
					ps = conn.prepareStatement(sbSQL.toString());
					setPrepareStatementByInfo(aei, ps, 1);
					ps.executeUpdate();
					cleanup(ps);
				} else {
					aei.setAccountExtendID(lAccountExtendId);
					sbSQL = new StringBuffer();
					sbSQL
							.append(" update sett_AccountExtend set AccountId = ?, IsSoft = ?, Status = ? \n");
					sbSQL.append(" where ID = ? \n");
					ps = conn.prepareStatement(sbSQL.toString());
					setPrepareStatementByInfo(aei, ps, -1);
					ps.executeUpdate();
					cleanup(ps);
				}
			}
			cleanup(conn);
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
		return ai.getClientID();
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	// public AccountInfo findByID(Connection conn,long lAccountID) throws
	// Exception
	// {
	// AccountInfo ai = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// StringBuffer sbSQL = null;
	// try
	// {
	// //get the connection from Database
	// //establish the query sql string
	// sbSQL = new StringBuffer();
	// sbSQL.append(" select * from sett_Account ");
	// sbSQL.append(" where ID = ? ");
	// log.info(sbSQL.toString());
	// ps = conn.prepareStatement(sbSQL.toString());
	// ps.setLong(1, lAccountID);
	// rs = ps.executeQuery();
	// if (rs.next())
	// {
	// //get the BankBillInfo from current ResultSet object
	// ai = new AccountInfo();
	// getInfoFromResultSet(ai, rs,conn);
	// }
	// cleanup(rs);
	// cleanup(ps);
	// }
	// finally
	// {
	// cleanup(rs);
	// cleanup(ps);
	// }
	// return ai != null ? ai : null;
	// }
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的账户
	 * 
	 * @param qaci
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByConditions(QueryAccountConditionInfo qaci) throws Exception 
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		AccountInfo ai = null;
		int iTag = 1;
		long lOrder = -1;
		long lDesc = 1;
		System.out.println("---------------findByConditions------accountType---------------:" + qaci.getAccountTypeID());
		System.out.println("---------------yes-------------------");
		try {
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			if ( (qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0)  
			  || (qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0 ) )
			{
				sbSQL.append(" select a.*,c.SCODE,c.isinstitutionalclient from sett_Account a, Client c ");
				/*
				sbSQL.append(" where a.nClientID = c.id and a.nStatusID in( "
																		+ SETTConstant.AccountStatus.NORMAL + ","
																		+ SETTConstant.AccountStatus.FREEZE + ","
																		+ SETTConstant.AccountStatus.SEALUP + ","
																		+ SETTConstant.AccountStatus.REPORTLOSS + ","
																		+ SETTConstant.AccountStatus.ALLFREEZE + ","
																		+ SETTConstant.AccountStatus.PARTFREEZE + " ) ");
				*/
				sbSQL.append(" where a.nClientID = c.id ");
				
			} 
			else 
			{
				sbSQL.append(" select * from sett_Account a ");
				sbSQL.append(" where 1 = 1 and a.nStatusID > 0 ");
			}
			//appends TypeID to the query where condition
			if (qaci.getOfficeID() > 0) 
			{
				sbSQL.append(" and a.nOfficeID = ? ");
			}
			if (qaci.getCurrencyID() > 0) 
			{
				sbSQL.append(" and a.nCurrencyID = ? ");
			}
			if (qaci.getCheckUserID() > 0) 
			{
				sbSQL.append(" and a.nInputUserID <> ? ");
			}
			if (qaci.getInputUserID() > 0 && qaci.getStrQuery().equals("")) 
			{
				sbSQL.append(" and a.nInputUserID = ? ");
			}
			
			if ( qaci.getStartClientCode() != null
			  && qaci.getEndClientCode() != null
			  && qaci.getStartClientCode().length() > 0
			  && qaci.getEndClientCode().length() > 0 ) 
			{
				sbSQL.append(" and ( c.SCODE between ? and ? )");
			}
			else if(qaci.getStartClientCode() != null && qaci.getStartClientCode().length() > 0 )
			{
				sbSQL.append(" and c.SCODE >= '"+qaci.getStartClientCode()+"' ");
			}
			else if(qaci.getEndClientCode() != null && qaci.getEndClientCode().length() > 0 )
			{
				sbSQL.append(" and c.SCODE <= '"+qaci.getEndClientCode()+"' ");
			}
			
			if ( qaci.getStartAccountCode() != null
			  && qaci.getEndAccountCode() != null
			  && qaci.getStartAccountCode().length() > 0
			  && qaci.getEndAccountCode().length() > 0) 
			{
				sbSQL.append(" and ( a.sAccountNo between ? and ? )");
			}
			else if(qaci.getStartAccountCode() != null && qaci.getStartAccountCode().length() > 0 )
			{
				sbSQL.append(" and a.sAccountNo >= '"+qaci.getStartAccountCode()+"' ");
			}
			else if(qaci.getEndAccountCode() != null && qaci.getEndAccountCode().length() > 0 )
			{
				sbSQL.append(" and a.sAccountNo <= '"+qaci.getEndAccountCode()+"' ");
			}
			
			if (qaci.getAccountTypeID() > 0) 
			{
				sbSQL.append(" and a.nAccountTypeID = ? ");
			}
			if (qaci.getCheckStatusID() > 0) 
			{
				//2007-11-12 Boxu update 增加页面显示"修改未复核"条件选择,只查询符合状态的数据
				if (qaci.getStrAction().equals("QueryAction"))
				{
					if (qaci.getCheckStatusID() == SETTConstant.AccountCheckStatus.OLDSAVE)
					{
						sbSQL.append(" and ( a.nCheckStatusID = ? and a.nInputUserID = "+qaci.getInputUserID()+" ) ");
					}
					else
					{
						sbSQL.append(" and a.nCheckStatusID = ? ");
					}
				}
				else if( qaci.getStrQuery().equals("MODIFYSAVE") )
				{
					sbSQL.append(" and ( a.nCheckStatusID = ? or ( a.nCheckStatusID= "
																+ SETTConstant.AccountCheckStatus.OLDSAVE
																+ " and a.nInputUserID= "
																+ qaci.getInputUserID() + " ) ) ");
				}
				else
				{
					sbSQL.append(" and a.nCheckStatusID = ? ");
				}
			}
			if (qaci.getStatusID() > 0) 
			{
				sbSQL.append(" and a.nStatusID = ? ");
			}
			
			//状态不等于0
			sbSQL.append(" and a.nStatusID != 0 ");
			
			if (qaci.getBatchUpdateID() > 0) 
			{
				sbSQL.append(" and a.nBatchUpdateID = ?");
			}
			lOrder = qaci.getOrder();
			lDesc = qaci.getDesc();

			switch ((int) lOrder) 
			{
				case ORDERBY_ACCOUNTNO:
					sbSQL.append(" order by sAccountNo ");
					break;
				case ORDERBY_ACCOUNTNAME:
					sbSQL.append(" order by sName ");
					break;
				case ORDERBY_ACCOUNTTYPEID:
					sbSQL.append(" order by nAccountTypeID ");
					break;
				case ORDERBY_OPENDATE:
					sbSQL.append(" order by dtOpen ");
					break;
				case ORDERBY_INPUTUSERID:
					sbSQL.append(" order by nInputUserID ");
					break;
				case ORDERBY_INPUTDATE:
					sbSQL.append(" order by dtInput ");
					break;
				case ORDERBY_CHECKSTATUSID:
					sbSQL.append(" order by nCheckStatusID ");
					break;
				case ORDERBY_STATUSID:
					sbSQL.append(" order by nStatusID");
					break;
				case ORDERBY_BATCHUPDATEID:
					sbSQL.append(" order by nBatchUpdateID ");
					break;
				case ORDERBY_BATCHUPDATETYPEID:
					sbSQL.append(" order by nBatchUpdateTypeID ");
					break;
				default:
					sbSQL.append(" order by sAccountNo ");
			}
			if (lDesc == -1) 
			{
				sbSQL.append(" asc ");
			}
			if (lDesc == 1) 
			{
				sbSQL.append(" desc ");
			}

			log.print("****************in Sett_AccountDAO" + sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			if (qaci.getOfficeID() > 0) 
			{
				ps.setLong(iTag++, qaci.getOfficeID());
			}
			if (qaci.getCurrencyID() > 0) 
			{
				ps.setLong(iTag++, qaci.getCurrencyID());
			}
			if (qaci.getCheckUserID() > 0) 
			{
				ps.setLong(iTag++, qaci.getCheckUserID());
			}
			if (qaci.getInputUserID() > 0 && qaci.getStrQuery().equals("")) 
			{
				ps.setLong(iTag++, qaci.getInputUserID());
			}
			if ( qaci.getStartClientCode() != null
			  && qaci.getEndClientCode() != null
			  && qaci.getStartClientCode().length() > 0
			  && qaci.getEndClientCode().length() > 0 ) 
			{
				ps.setString(iTag++, qaci.getStartClientCode());
				ps.setString(iTag++, qaci.getEndClientCode());
			}
			if ( qaci.getStartAccountCode() != null
			  && qaci.getEndAccountCode() != null
			  && qaci.getStartAccountCode().length() > 0
			  && qaci.getEndAccountCode().length() > 0 ) 
			{
				ps.setString(iTag++, qaci.getStartAccountCode());
				ps.setString(iTag++, qaci.getEndAccountCode());
			}
			if (qaci.getAccountTypeID() > 0) 
			{
				ps.setLong(iTag++, qaci.getAccountTypeID());
			}
			if (qaci.getCheckStatusID() > 0) 
			{
				ps.setLong(iTag++, qaci.getCheckStatusID());
			}
			if (qaci.getStatusID() > 0) 
			{
				ps.setLong(iTag++, qaci.getStatusID());
			}
			if (qaci.getBatchUpdateID() > 0) 
			{
				ps.setLong(iTag++, qaci.getBatchUpdateID());
			}
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next()) 
			{
				ai = new AccountInfo();
				//append one BankBillInfo to the LinkedList object
				getInfoFromResultSet(ai, rs);
				v.add(ai);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} 
		catch (Exception exp) 
		{
			throw exp;
		} 
		finally 
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return v.size() > 0 ? v : null;
	}
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的账户(备付金账户，准备金账户，拆借账户适用)
	 * 
	 * @param qaci
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findReserveAccountByCondition(QueryAccountConditionInfo qaci) throws Exception 
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		AccountInfo ai = null;
		long AccountGroupTypeID=-1;		
		System.out.println("---------------findByConditions------accountType---------------:" + qaci.getAccountTypeID());
		System.out.println("---------------yes-------------------");
		try {
			

			if(qaci.getAccountGroupTypeID() > 0){
				AccountGroupTypeID=qaci.getAccountGroupTypeID();
			}
			else if(qaci.getAccountTypeID()>0)
			{
				AccountTypeInfo accInfo=new AccountTypeInfo();
				accInfo=SETTConstant.AccountType.getAccountTypeInfoByAccountTypeID(qaci.getAccountTypeID()); 	
				AccountGroupTypeID=accInfo.getAccountGroupID();
			}
			if(AccountGroupTypeID==SETTConstant.AccountGroupType.BAK 
				|| AccountGroupTypeID==SETTConstant.AccountGroupType.LENDING 
				|| AccountGroupTypeID==SETTConstant.AccountGroupType.RESERVE )
			{
				
				conn = getConnection();
				//establish the query string
				sbSQL = new StringBuffer();
				
				sbSQL.append("  select a.*,c.id ClientID,c.SCODE,c.isinstitutionalclient,att.naccountgroupid from sett_Account a, Client c ,sett_accounttype att ");
				 
				sbSQL.append(" 	where a.naccounttypeid=att.id and a.nclientid=c.id  and a.nStatusID > 0 and att.naccountgroupid = ? ");
		
				if (qaci.getOfficeID() > 0) 
				{
					sbSQL.append(" and a.nOfficeID = ? ");
				}
				if (qaci.getCurrencyID() > 0) 
				{
					sbSQL.append(" and a.nCurrencyID = ? ");
				}
				if (qaci.getInputUserID() > 0 && qaci.getStrQuery().equals("")) 
				{
					sbSQL.append(" and a.nInputUserID = ? ");
				}
				if ( qaci.getQueryClientID()>0)
				{
					sbSQL.append(" and  a.nclientid=? ");	
				}
				
				log.print("****************in Sett_AccountDAO" + sbSQL.toString());
				ps = conn.prepareStatement(sbSQL.toString());
				
				int iTag = 1;
				
				ps.setLong(iTag++, AccountGroupTypeID);
				
				if (qaci.getOfficeID() > 0) 
				{
					ps.setLong(iTag++, qaci.getOfficeID());
				}
				if (qaci.getCurrencyID() > 0) 
				{
					ps.setLong(iTag++, qaci.getCurrencyID());
				}
				if (qaci.getInputUserID() > 0 ) 
				{
					ps.setLong(iTag++, qaci.getInputUserID());
				}
				
				if ( qaci.getQueryClientID()>0)
				{
					ps.setLong(iTag++, qaci.getQueryClientID());
				}
				
				rs = ps.executeQuery();
				//get all the ResultSet elements
				while (rs.next()) 
				{
					ai = new AccountInfo();
					//append one BankBillInfo to the LinkedList object
					getInfoFromResultSet(ai, rs);
					v.add(ai);
				}
				
			}else
			{
					throw new Exception("不是备付金，准备金或者拆借账户。");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} 
		catch (Exception exp) 
		{
			throw exp;
		} 
		finally 
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		
		return v.size() > 0 ? v : null;
	}
	public AccountInfo findByAccountNO(String accountNO) throws Exception {
		AccountInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_Account ");
			sbSQL.append(" where saccountno = ? ");
			// log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, accountNO);
			rs = ps.executeQuery();
			if (rs.next()) {
				// get the BankBillInfo from current ResultSet object
				ai = new AccountInfo();
				getInfoFromResultSet(ai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			throw (SQLException) e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return ai != null ? ai : null;
	}

	/**
	 * added by mzh_fu 2008/04/02
	 * 查找正常状的账户
	 * @param accountNO
	 * @return
	 * @throws Exception
	 */	
	public AccountInfo findNormalByAccountNO(String accountNO) throws Exception {
		AccountInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_Account ");
			sbSQL.append(" where saccountno = ? and ncheckstatusid = ? and nstatusid = ?");
			// log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setString(1, accountNO);
			ps.setLong(2,SETTConstant.AccountCheckStatus.CHECK) ;
			ps.setLong(3, SETTConstant.AccountStatus.NORMAL);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				// get the BankBillInfo from current ResultSet object
				ai = new AccountInfo();
				getInfoFromResultSet(ai, rs);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			throw (SQLException) e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return ai != null ? ai : null;
	}
	
	
	/**
	 * 方法说明：根据查询条件组合，查询出符合条件的账户
	 * 
	 * @param qaci
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByConditions(QueryAccountConditionInfo qaci,
			String clientCode) throws Exception {
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		AccountInfo ai = null;
		int iTag = 1;
		long lOrder = -1;
		long lDesc = 1;

		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();
			if (qaci.getStartClientCode() != null
					&& qaci.getEndClientCode() != null
					&& qaci.getStartClientCode().length() > 0
					&& qaci.getEndClientCode().length() > 0) {
				sbSQL
						.append(" select a.*,c.SCODE from sett_Account a, Client c ");
				sbSQL.append(" where a.nClientID = c.id and a.nStatusID > 0");
			} else {
				sbSQL.append(" select * from sett_Account a ");
				sbSQL.append(" where 1 = 1 and a.nStatusID > 0 ");
			}
			// appends TypeID to the query where condition
			if (qaci.getOfficeID() > 0) {
				sbSQL.append(" and a.nOfficeID = ? ");
			}
			if (qaci.getCurrencyID() > 0) {
				sbSQL.append(" and a.nCurrencyID = ? ");
			}
			if (qaci.getCheckUserID() > 0) {
				sbSQL.append(" and a.nInputUserID <> ? ");
			}
			if (qaci.getInputUserID() > 0) {
				sbSQL.append(" and a.nInputUserID = ? ");
			}
			if (qaci.getStartClientCode() != null
					&& qaci.getEndClientCode() != null
					&& qaci.getStartClientCode().length() > 0
					&& qaci.getEndClientCode().length() > 0) {
				sbSQL.append(" and ( c.SCODE between ? and ? )");
			}
			if (qaci.getStartAccountCode() != null
					&& qaci.getEndAccountCode() != null
					&& qaci.getStartAccountCode().length() > 0
					&& qaci.getEndAccountCode().length() > 0) {
				sbSQL.append(" and ( a.sAccountNo between ? and ? )");
			}
			if (qaci.getAccountTypeID() > 0) {
				sbSQL.append(" and a.nAccountTypeID = ? ");
			}
			if (qaci.getCheckStatusID() > 0) {
				sbSQL.append(" and a.nCheckStatusID = ? ");
			}
			if (qaci.getStatusID() > 0) {
				sbSQL.append(" and a.nStatusID = ? ");
			}
			if (qaci.getBatchUpdateID() > 0) {
				sbSQL.append(" and a.nBatchUpdateID = ?");
			}
			lOrder = qaci.getOrder();
			lDesc = qaci.getDesc();

			switch ((int) lOrder) {
			case ORDERBY_ACCOUNTNO:
				sbSQL.append(" order by sAccountNo ");
				break;
			case ORDERBY_ACCOUNTNAME:
				sbSQL.append(" order by sName ");
				break;
			case ORDERBY_ACCOUNTTYPEID:
				sbSQL.append(" order by nAccountTypeID ");
				break;
			case ORDERBY_OPENDATE:
				sbSQL.append(" order by dtOpen ");
				break;
			case ORDERBY_INPUTUSERID:
				sbSQL.append(" order by nInputUserID ");
				break;
			case ORDERBY_INPUTDATE:
				sbSQL.append(" order by dtInput ");
				break;
			case ORDERBY_CHECKSTATUSID:
				sbSQL.append(" order by nCheckStatusID ");
				break;
			case ORDERBY_STATUSID:
				sbSQL.append(" order by nStatusID");
				break;
			case ORDERBY_BATCHUPDATEID:
				sbSQL.append(" order by nBatchUpdateID ");
				break;
			case ORDERBY_BATCHUPDATETYPEID:
				sbSQL.append(" order by nBatchUpdateTypeID ");
				break;
			default:
				sbSQL.append(" order by sAccountNo ");
			}
			if (lDesc == -1) {
				sbSQL.append(" asc ");
			}
			if (lDesc == 1) {
				sbSQL.append(" desc ");
			}

			// log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			if (qaci.getOfficeID() > 0) {
				ps.setLong(iTag++, qaci.getOfficeID());
			}
			if (qaci.getCurrencyID() > 0) {
				ps.setLong(iTag++, qaci.getCurrencyID());
			}
			if (qaci.getCheckUserID() > 0) {
				ps.setLong(iTag++, qaci.getCheckUserID());
			}
			if (qaci.getInputUserID() > 0) {
				ps.setLong(iTag++, qaci.getInputUserID());
			}
			if (qaci.getStartClientCode() != null
					&& qaci.getEndClientCode() != null
					&& qaci.getStartClientCode().length() > 0
					&& qaci.getEndClientCode().length() > 0) {
				ps.setString(iTag++, qaci.getStartClientCode());
				ps.setString(iTag++, qaci.getEndClientCode());
			}
			if (qaci.getStartAccountCode() != null
					&& qaci.getEndAccountCode() != null
					&& qaci.getStartAccountCode().length() > 0
					&& qaci.getEndAccountCode().length() > 0) {
				ps.setString(iTag++, qaci.getStartAccountCode());
				ps.setString(iTag++, qaci.getEndAccountCode());
			}
			if (qaci.getAccountTypeID() > 0) {
				ps.setLong(iTag++, qaci.getAccountTypeID());
			}
			if (qaci.getCheckStatusID() > 0) {
				ps.setLong(iTag++, qaci.getCheckStatusID());
			}
			if (qaci.getStatusID() > 0) {
				ps.setLong(iTag++, qaci.getStatusID());
			}
			if (qaci.getBatchUpdateID() > 0) {
				ps.setLong(iTag++, qaci.getBatchUpdateID());
			}
			rs = ps.executeQuery();
			// get all the ResultSet elements
			while (rs.next()) {
				ai = new AccountInfo();
				// append one BankBillInfo to the LinkedList object
				getInfoFromResultSet(ai, rs);
				v.add(ai);
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return v.size() > 0 ? v : null;
	}

	/**
	 * 方法说明：取得账户编号
	 * 
	 * @param lOfficeID :
	 *            long
	 * @param lCurrencyID
	 *            :long
	 * @param lClientID
	 *            :long
	 * @return: String - 新增的账户编号
	 * @throws Exception
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID,
			long lAccountTypeID, long lClientID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		/*
		 * String strSQL = null; long lResult = -1; String strAccountNo = null;
		 * String strClientCode = ""; long lMaxClientNo = 0; long
		 * lMaxAccountNoPart3 = 0; StringBuffer sb = new StringBuffer();
		 */
		String checkNo = "";
		String clientCode = "";
		String accountNo = "";
		String currencyNo = "";
		String officeCode = "";
		String accountTypeNo = DataFormat.formatInt(lAccountTypeID, 2);
		try {
			conn = getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.setLength(0);
			sbSQL
					.append(" select lpad(saccounttypecode,2,'0') saccounttypecode from sett_accounttype where id=? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountTypeID);
			rs = ps.executeQuery();
			while (rs.next()) {
				accountTypeNo = rs.getString("saccounttypecode");
			}
			cleanup(rs);
			cleanup(ps);

			sbSQL.setLength(0);
			//sbSQL
			//		.append(" select scode from client where id=? and nofficeid=? and nstatusId=1 ");
			sbSQL
			.append(" select scode from client where id=?  and nstatusId=1 ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lClientID);
			//ps.setLong(2, lOfficeID);
			rs = ps.executeQuery();
			while (rs.next()) {
				clientCode = rs.getString("scode");
			}
			cleanup(rs);
			cleanup(ps);
			/**
			 * //取得office编号 sbSQL.setLength(0); sbSQL.append(" select scode from
			 * office where id=? and nstatusId=1"); ps =
			 * conn.prepareStatement(sbSQL.toString()); ps.setLong(1,
			 * lOfficeID); rs = ps.executeQuery(); while (rs.next()) {
			 * officeCode = rs.getString("scode"); } cleanup(rs); cleanup(ps);
			 */
			// 取得currency编号
			sbSQL.setLength(0);
			sbSQL
					.append(" select currencyno from currencyinfo where id=? and status=1");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lCurrencyID);
			rs = ps.executeQuery();
			while (rs.next()) {
				currencyNo = rs.getString("currencyno");
			}
			cleanup(rs);
			cleanup(ps);
			// 账户号的段数
			int accountField = Config.getInteger(
					ConfigConstant.GLOBAL_ACCOUNTNO_FIELD, 4);
			// 账户号的段间符号
			String tag = Config.getProperty(
					ConfigConstant.GLOBAL_ACCOUNTNO_TAG, "-");
			// 账户号的第一段的类型
			int firstFieldType = Config.getInteger(
					ConfigConstant.GLOBAL_ACCOUNTNO_FIRSTFIELDTYPE, 1);
			// 如果是活期账户，增加校验位
			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)) {
				sbSQL.setLength(0);
				sbSQL
						.append(" select max(saccountno) saccountno from sett_account where nclientid=? and ncurrencyid=? and nofficeid=? and naccounttypeid=?");
				sbSQL.append(" and nstatusid in ("
						+ SETTConstant.AccountStatus.NORMAL + ","
						+ SETTConstant.AccountStatus.FREEZE + ","
						+ SETTConstant.AccountStatus.SEALUP + ","
						+ SETTConstant.AccountStatus.CLOSE + ","
						+ SETTConstant.AccountStatus.REPORTLOSS + ","
						+ SETTConstant.AccountStatus.ALLFREEZE + ","
						+ SETTConstant.AccountStatus.PARTFREEZE + ")");
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, lClientID);
				ps.setLong(2, lCurrencyID);
				ps.setLong(3, lOfficeID);
				ps.setLong(4, lAccountTypeID);
				rs = ps.executeQuery();
				while (rs.next()) {
					accountNo = rs.getString("saccountno");
				}
				cleanup(rs);
				cleanup(ps);
				if (accountNo == null || "".equalsIgnoreCase(accountNo)) {
					checkNo = "1";
				} else {
					String[] accountNos = accountNo.split(tag);
					// 默认校验位的位数为2位
					//校验位数改为1位  add by wangzhen 2011-08-02
					checkNo = DataFormat
							.formatInt(
									Integer
											.parseInt(accountNos[accountNos.length - 1]) + 1,
									1);
				}
				// 将账户类型编号insert到客户编号中
				String code = clientCode.replaceAll(tag, tag + accountTypeNo
						+ tag);
				// 默认币种、账户类型的位数为2位
				if (firstFieldType == 1) {
					accountNo = currencyNo + tag + code + tag + checkNo;
				} else if (firstFieldType == 2) {
					// 将账户类型编号insert到客户编号中
					accountNo = code + tag + checkNo;
				} else {
					// 将账户类型编号insert到客户编号中
					accountNo = currencyNo + tag + code + tag + checkNo;
				}
			} else {
				// 将账户类型编号insert到客户编号中
				checkNo="1";  //定期、通知、贷款类账户，编号在三段的基础上增加一段校验位，值默认为"1" add by wangzhen 2011-08-02
				String code = clientCode.replaceAll(tag, tag + accountTypeNo
						+ tag);
				// 默认币种、账户类型的位数为2位
				if (firstFieldType == 1) {
					accountNo = currencyNo + tag + code;
				} else if (firstFieldType == 2) {
					accountNo = code+tag+checkNo;
				} else {
					accountNo = currencyNo + tag + code;
				}
			}
			
			/*账户办事处处理
			 *第一段编码应该根据开户地来取，而不应该从客户编号号中。
			 *这里对上面取到的客户编号进行处理，将第一段改成当前开户办事处编号 
			 */
			//取得office编号 
			 sbSQL.setLength(0); 
			 sbSQL.append(" select scode from office where id=? and nstatusId=1"); 
			 ps = conn.prepareStatement(sbSQL.toString()); 
			 ps.setLong(1, lOfficeID); 
			 rs = ps.executeQuery(); 
			 while (rs.next()) 
			 {
				 officeCode = rs.getString("scode"); 
			 } 
			 cleanup(rs); 
			 cleanup(ps); 
			 
			if(accountNo!=null && accountNo.length()>0)
			{
				//替换第一段
				int firsttag = accountNo.indexOf(tag);
				String end = accountNo.substring(firsttag);
				accountNo = officeCode + end;
			}
			
			/*
			 * 修改 by kenny (胡志强) (2007-03-22) 处理账户号生成规则问题 sb.setLength(0); //
			 * 获得可用的账户号 sb.setLength(0); sb.append(" select min(no) \n");
			 * sb.append(" from \n"); sb.append(" ( select id no from serialno
			 * \n"); sb.append(" minus \n"); sb.append(" select no \n");
			 * sb.append(" from \n"); sb.append(" ( select
			 * to_number(substr(saccountno,7,4)) no from sett_Account where
			 * nofficeid=? \n"); sb.append(" and ascii(substr(saccountno,7,1)) >
			 * 47 and ascii(substr(saccountno,7,1)) < 58 \n"); sb.append(" and
			 * ascii(substr(saccountno,8,1)) > 47 and
			 * ascii(substr(saccountno,8,1)) < 58 \n"); sb.append(" and
			 * ascii(substr(saccountno,9,1)) > 47 and
			 * ascii(substr(saccountno,9,1)) < 58 \n"); sb.append(" and
			 * ascii(substr(saccountno,10,1)) > 47 and
			 * ascii(substr(saccountno,10,1)) < 58 \n"); sb.append(" union all
			 * \n"); sb.append(" select to_number(substr(scode,4,4)) no \n");
			 * sb.append(" from client \n"); sb.append(" where nofficeid=? and
			 * ascii(substr(scode,4,1)) > 47 and ascii(substr(scode,4,1)) < 58
			 * \n"); sb.append(" and ascii(substr(scode,5,1)) > 47 and
			 * ascii(substr(scode,5,1)) < 58 \n"); sb.append(" and
			 * ascii(substr(scode,6,1)) > 47 and ascii(substr(scode,6,1)) < 58
			 * \n"); sb.append(" and ascii(substr(scode,7,1)) > 47 and
			 * ascii(substr(scode,7,1)) < 58 \n"); sb.append(" ) \n");
			 * sb.append(" ) \n"); ps = conn.prepareStatement(sb.toString());
			 * ps.setLong(1, lOfficeID); ps.setLong(2, lOfficeID); rs =
			 * ps.executeQuery(); if (rs.next()) { try { lMaxAccountNoPart3 =
			 * rs.getLong(1); } catch (Exception e) { lMaxAccountNoPart3 = 0; } }
			 * else { lMaxAccountNoPart3 = 0; } log4j.info("Max AccountPart3 No.
			 * is " + lMaxAccountNoPart3); cleanup(rs); cleanup(ps); ///
			 * 获得该客户已开立的账户号 sb.setLength(0); sb.append( //" select
			 * min(saccountno) from sett_Account where nStatusID > 0 " "select
			 * min(saccountno) from sett_Account a where" +" a.nStatusID
			 * in("+SETTConstant.AccountStatus.NORMAL+","+SETTConstant.AccountStatus.FREEZE+","+SETTConstant.AccountStatus.SEALUP+","+SETTConstant.AccountStatus.REPORTLOSS+","+SETTConstant.AccountStatus.ALLFREEZE+","+SETTConstant.AccountStatus.PARTFREEZE+")" +"
			 * and nofficeid=" + lOfficeID + " and naccounttypeid=" +
			 * lAccountTypeID + " and nclientid=" + lClientID + " and
			 * ncurrencyid=" + lCurrencyID + " order by saccountno "); ps =
			 * conn.prepareStatement(sb.toString());
			 * //log4j.info(sb.toString()); rs = ps.executeQuery(); boolean
			 * bHasAccount = false; if (rs.next()) { bHasAccount = true; try { //
			 * 该客户已经开立了此账户类型的账户 String str = rs.getString(1); long
			 * lAccountNoPart3 = Long.parseLong(str.substring(6, 10)); //
			 * 账户类型是活期 if
			 * (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)
			 * ||SETTConstant.AccountType.isOtherDepositAccountType(lAccountTypeID)) {
			 * lResult = lMaxAccountNoPart3; if (lResult == 0) lResult = -1; } //
			 * 账户类型不是活期存款，只能开立一个账户 else { lResult = -1; } } catch (Exception e) {
			 * bHasAccount = false; } } log4j.info("lResult : " + lResult);
			 * //关闭资源 cleanup(rs); cleanup(ps); // 该客户还没有开立此账户类型的账户,客户号与账户号一致 if
			 * (bHasAccount == false) { if
			 * (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)) {
			 * lResult = lMaxAccountNoPart3; } else { sb.setLength(0);
			 * sb.append(" select scode,nofficeid from client where id=? "); ps =
			 * conn.prepareStatement(sb.toString()); ps.setLong(1, lClientID);
			 * rs = ps.executeQuery(); long lDiffOfficeID = 0; if (rs.next()) {
			 * strClientCode = rs.getString("scode"); lDiffOfficeID =
			 * rs.getLong("nofficeid"); } cleanup(rs); cleanup(ps); try { if
			 * (lDiffOfficeID != lOfficeID) // 不同办事处的客户 { sb.append( " select id
			 * from sett_Account where nofficeid=" + lOfficeID + " and
			 * saccountno like '%" + strClientCode.substring(3, 7) + "%' \n");
			 * sb.append(" union all \n"); sb.append( " select id from client
			 * where nofficeid=" + lOfficeID + " and scode like '%" +
			 * strClientCode.substring(3, 7) + "%' \n"); //log4j.info("Temp
			 * Account max account part3 sql: " + sb.toString()); ps =
			 * conn.prepareStatement(sb.toString()); rs = ps.executeQuery(); if
			 * (rs.next()) lResult = lMaxAccountNoPart3; else lResult =
			 * Long.parseLong(strClientCode.substring(3, 7)); cleanup(rs);
			 * cleanup(ps); } else // 相同办事处的客户 lResult =
			 * Long.parseLong(strClientCode.substring(3, 7)); } catch (Exception
			 * e) { return null; } } } //// if (lResult > 0) { sb.setLength(0);
			 * sb.append(" select scode from office where id=? "); ps =
			 * conn.prepareStatement(sb.toString()); ps.setLong(1, lOfficeID);
			 * rs = ps.executeQuery(); String strOfficeCode = ""; if (rs.next()) {
			 * strOfficeCode = rs.getString("scode"); } cleanup(rs);
			 * cleanup(ps); strAccountNo = strOfficeCode + "-"; strAccountNo +=
			 * DataFormat.formatInt(lAccountTypeID, 2, true) + "-"; strAccountNo +=
			 * DataFormat.formatInt(lResult, 4, true); //如果是活期账户，增加随机位 if
			 * (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)) {
			 * //取得随机位值 long lRand = (long) (Math.random() * 10); strAccountNo +=
			 * "-" + DataFormat.formatInt(lRand, 1, true); } }
			 * log4j.info("新增账户号：" + strAccountNo); //关闭资源 cleanup(conn);
			 */
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception exp) {
				throw exp;
			}
		}
		// return strAccountNo;
		return accountNo;
	}
	/**
	 * 方法说明：取得账户编号
	 * 
	 * @param lOfficeID :
	 *            long
	 * @param lCurrencyID
	 *            :long
	 * @param lClientID
	 *            :long
	 * @return: String - 新增的账户编号
	 * @throws Exception
	 */
	public String getNewAccountNo(long lOfficeID, long lCurrencyID,
			long lAccountTypeID, long lClientID,long lAccountGroupTypeID,long belongOfficeID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String checkNo = "1";
		String clientCode = "";
		String accountNo = "";
		String lOfficeCode="";
		String accountTypeNo = DataFormat.formatInt(lAccountTypeID, 2);
		try {
			conn = getConnection();
			StringBuffer sbSQL = new StringBuffer();

			sbSQL.setLength(0);
			sbSQL
					.append(" select lpad(saccounttypecode,2,'0') saccounttypecode from sett_accounttype where id=? ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountTypeID);
			rs = ps.executeQuery();
			while (rs.next()) {
				accountTypeNo = rs.getString("saccounttypecode");
			}
			cleanup(rs);
			cleanup(ps);
			
			//客户编号
			sbSQL.setLength(0);
			sbSQL.append(" select scode from client where id=?  and nstatusId=1 ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();
			while (rs.next()) {
				clientCode = rs.getString("scode");
			}
			cleanup(rs);
			cleanup(ps);
			
			
			// 当前操作机构
			
			sbSQL.setLength(0);
			sbSQL.append(" select SCODE from office where id=? and nstatusId=1 ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lOfficeID);
			rs = ps.executeQuery();
			while (rs.next()) {
				lOfficeCode = rs.getString("scode");
			}
			cleanup(rs);
			cleanup(ps);

			// 账户号的段间符号
			String tag = Config.getProperty(
					ConfigConstant.GLOBAL_ACCOUNTNO_TAG, "-");
			if( lAccountGroupTypeID==SETTConstant.AccountGroupType.BAK 
					||lAccountGroupTypeID==SETTConstant.AccountGroupType.LENDING 
					||lAccountGroupTypeID==SETTConstant.AccountGroupType.RESERVE ) 
			{
			
				// 将账户类型编号insert到客户编号中

				accountNo = lOfficeCode+tag+accountTypeNo+tag+clientCode.split(tag)[1]+tag+checkNo;
				
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception exp) {
				throw exp;
			}
		}
		// return strAccountNo;
		return accountNo;
	}

	/**
	 * 方法说明: 根据账户ID修改扩展表状态
	 * 
	 * @param lAccountID
	 * @throws Exception
	 */
	private void updateAccountExtendStauts(long lAccountID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_AccountExtend set Status = 0 \n");
			sbSQL.append(" where AccountId = ? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
	}

	/**
	 * 方法说明：根据账户ID修改账户状态
	 * 
	 * @param qaci
	 * @return long - 返回账户ID
	 * @throws Exception
	 */
	public long updateCheckStatus(long lAccountID,long lActionID,long lCheckStatusID,long lCheckUserID,Timestamp tsCheckDate) throws Exception
	{
		long lReturn = -1;
		long lCheckStatus = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the update sql string
			sbSQL = new StringBuffer();
			if(lActionID == SETTConstant.Actions.DELETE)//删除 只有新增的可以删除
			{
				sbSQL = new StringBuffer();
				sbSQL.append(" update sett_Account set nStatusID = 0,nCheckStatusID = ?,nCheckUserID=?,dtCheck=? \n");
				sbSQL.append(" where ID = ? \n");				
				//sbSQL.append(" and dtInput = ? ");
				//sbSQL.append(" and nInputUserID = ? ");
				sbSQL.append(" and nCheckStatusID = ? ");
				updateAccountExtendStauts(lAccountID);
			}
			if(lActionID == SETTConstant.Actions.CANCELCHECK)
			//取消复核，状态变为1 但是只能是当天业务
			{
				sbSQL.append(" update sett_Account set nCheckStatusID = ?,nCheckUserID=?,dtCheck=? \n");
				sbSQL.append(" where ID = ? \n");
				sbSQL.append(" and nCheckStatusID = ? ");
			}
			if(lActionID == SETTConstant.Actions.CHECK)
			//复核4
			{
				sbSQL.append(" update sett_Account set nCheckStatusID = ?,nCheckUserID=?,dtCheck=? \n");
				sbSQL.append(" where ID = ? \n");
			}
			if(lActionID == SETTConstant.Actions.SAVEANDINITAPPROVAL 
					|| lActionID == SETTConstant.Actions.DOAPPRVOAL 
					|| lActionID == SETTConstant.Actions.INITAPPROVAL
					|| lActionID == SETTConstant.Actions.CANCELAPPROVAL)
			{
				sbSQL.append(" update sett_Account set nCheckStatusID = ? ,nCheckUserID=?,dtCheck=? where ID = ? \n");
				
			}
			
			ps = conn.prepareStatement(sbSQL.toString());
			
			if(lActionID == SETTConstant.Actions.SAVEANDINITAPPROVAL 
					|| lActionID == SETTConstant.Actions.DOAPPRVOAL 
					|| lActionID == SETTConstant.Actions.INITAPPROVAL
					|| lActionID == SETTConstant.Actions.CANCELAPPROVAL){
				ps.setLong(1,lCheckStatusID);
				ps.setLong(2,lCheckUserID);
				ps.setTimestamp(3,tsCheckDate);
				ps.setLong(4,lAccountID);
			}
		    if(lActionID == SETTConstant.Actions.DELETE 
		    		|| lActionID == SETTConstant.Actions.CHECK
		    		|| lActionID == SETTConstant.Actions.CANCELCHECK)
			{
				ps.setLong(1,lCheckStatusID);
				ps.setLong(2,lCheckUserID);
				ps.setTimestamp(3,tsCheckDate);
				ps.setLong(4,lAccountID);
				if(lActionID == SETTConstant.Actions.DELETE )//删除 只有新增的可以删除
				{
					//ps.setTimestamp(5,tsCheckDate);
					//ps.setLong(5,lCheckUserID);
					ps.setLong(5,SETTConstant.AccountCheckStatus.NEWSAVE);
				}
				if(lActionID == SETTConstant.Actions.CANCELCHECK)
				//取消复核，状态变为1 但是只能是当天业务
				{
					lCheckStatus = SETTConstant.AccountCheckStatus.CHECK;
					ps.setLong(5,lCheckStatus);
				}
			}
			
			long lResult = ps.executeUpdate();
			if (lResult >0)
			{
				lReturn = lAccountID;
			}
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}
	/**
	 * 方法说明：根据查询条件批量修改复核条件的账户的状态
	 * 
	 * @param qaci,ai
	 * @return long - 返回修改成功的标识
	 * @throws Exception
	 */
	public long batchUpdate(QueryAccountConditionInfo qaci, AccountInfo ai)
			throws Exception {
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psBatch = null;
		ResultSet rs = null;
		ResultSet rsBatch = null;
		StringBuffer sbSQL = null;
		StringBuffer sbSQLBatch = null;
		try {
			log.print("sett_AccountBatchUpdate Start");
			// get the connection from Database
			conn = getConnection();
			// establish the update sql string
			String strSQL = "";
			sbSQL = new StringBuffer();
			sbSQLBatch = new StringBuffer();
			// -------------------------

			long lAccountID = -1;
			sbSQL
					.append(" UPDATE sett_Account SET nCheckStatusID=?,nInputUserID=?,dtInput=? ");
			sbSQL.append(",nBatchUpdateID=? ");
			sbSQL.append(",nBatchUpdateTypeID=? ");
			if (ai.getStatusID() > 0) {
				sbSQL.append(",nStatusID=? ");
			}
			sbSQL.append(" WHERE ID=? ");
			// 从第一个账户开始循环的取得每个账户ID
			sbSQLBatch
					.append(" select a.ID from sett_Account a,Client c where a.nClientID = c.ID ");
			sbSQLBatch.append(" and a.nCheckStatusID=? ");
			if (qaci.getStartClientCode() != null
					&& qaci.getEndClientCode() != null
					&& qaci.getStartClientCode().length() > 0
					&& qaci.getEndClientCode().length() > 0) {
				sbSQLBatch.append(" and c.sCode between ? and ? ");
			}
			if (qaci.getStartAccountCode() != null
					&& qaci.getEndAccountCode() != null
					&& qaci.getStartAccountCode().length() > 0
					&& qaci.getEndAccountCode().length() > 0) {
				sbSQLBatch.append(" and a.sAccountNo between ? and ? ");
			}
			if (qaci.getAccountTypeID() > 0) {
				sbSQLBatch.append(" and a.nAccountTypeID=? ");
			}
			if (qaci.getBatchUpdateID() > 0) {
				sbSQLBatch.append(" and a.nBatchUpdateID=? ");
			}
			psBatch = conn.prepareStatement(sbSQLBatch.toString());
			int lIndex = 1;
			psBatch.setLong(lIndex++, qaci.getCheckStatusID());
			if (qaci.getStartClientCode() != null
					&& qaci.getEndClientCode() != null
					&& qaci.getStartClientCode().length() > 0
					&& qaci.getEndClientCode().length() > 0) {
				psBatch.setString(lIndex++, qaci.getStartClientCode());
				psBatch.setString(lIndex++, qaci.getEndClientCode());
			}
			if (qaci.getStartAccountCode() != null
					&& qaci.getEndAccountCode() != null
					&& qaci.getStartAccountCode().length() > 0
					&& qaci.getEndAccountCode().length() > 0) {
				psBatch.setString(lIndex++, qaci.getStartAccountCode());
				psBatch.setString(lIndex++, qaci.getEndAccountCode());
			}
			if (qaci.getAccountTypeID() > 0) {
				psBatch.setLong(lIndex++, qaci.getAccountTypeID());
			}
			if (qaci.getBatchUpdateID() > 0) {
				psBatch.setLong(lIndex++, qaci.getBatchUpdateID());
			}
			// log.print("sbSQLBatch:" + sbSQLBatch.toString());
			log.print("checkstatusid:" + qaci.getCheckStatusID()
					+ ":accounttype:" + qaci.getAccountTypeID()
					+ ":batchupdateid:" + qaci.getBatchUpdateID());
			rsBatch = psBatch.executeQuery();
			long lMaxBatchUpdateID = getMaxBatchUpdateID();
			lReturn = 0;// 返回的被修改的记录条数
			while (rsBatch.next()) {
				lAccountID = rsBatch.getLong("ID");
				// 更新sett_Account表记录
				ps = conn.prepareStatement(sbSQL.toString());
				int npos = 1;
				ps.setLong(npos++, ai.getCheckStatusID());
				ps.setLong(npos++, ai.getInputUserID());
				ps.setTimestamp(npos++, ai.getInputDate());
				if (ai.getBatchUpdateID() > 0)// 传入批量复核号 说明是复核批量修改
												// 则将批量复核序号update=0
				{
					ps.setLong(npos++, 0);
				} else {
					ps.setLong(npos++, lMaxBatchUpdateID);
				}
				if (ai.getStatusID() > 0)// 只有修改账户状态 时为 1
				{
					ps.setLong(npos++, 1);
				} else {
					ps.setLong(npos++, 0);// 当复核和修改利率计划时 都为 0
				}
				if (ai.getStatusID() > 0) {
					ps.setLong(npos++, ai.getStatusID());
				}

				ps.setLong(npos++, lAccountID);
				// log.print("update sql="+sbSQL.toString());

				long lResult = -1;
				lResult = ps.executeUpdate();
				// 操作失败
				if (lResult < 1) {
					lReturn = -1;
					break;
				} else {
					lReturn++;
				}
				// 更新sett_Account表结束
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(rsBatch);
			cleanup(psBatch);
			// 从第一个账户开始循环的取得每个账户ID End

			// 关闭资源
			cleanup(rs);
			cleanup(ps);
			cleanup(rsBatch);
			cleanup(psBatch);
			cleanup(conn);
			log.print("lReturn records::::::::::::::::::::::::::" + lReturn);
			log.print("sett_AccountBatchUpdate End");
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		} finally {
			cleanup(rsBatch);
			cleanup(psBatch);
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}

	/**
	 * get the current maximum nBatchUpdateID of table sett_Account
	 * 
	 * @return the current maximum nBatchUpdateID of table sett_Account
	 * @exception
	 */
	private long getMaxBatchUpdateID() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select max(nvl( nBatchUpdateID , 0 ))+1 as maxno ");
			sbSQL.append(" from sett_Account ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				lNextID = rs.getLong("maxno");
			}
			log.print("get the maxBatchUpdateID=" + lNextID);
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}

	private long getAccountExtendNextID() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxno ");
			sbSQL.append(" from sett_AccountExtend ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				lNextID = rs.getLong("maxno");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}

	/**
	 * get the current maximum id of table sett_BankBill
	 * 
	 * @return the current maximum id of table sett_BankBill
	 * @exception
	 */
	private long getNextID() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lNextID = -1;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxno ");
			sbSQL.append(" from sett_Account ");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				lNextID = rs.getLong("maxno");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lNextID;
	}

	/**
	 * 校验是否有相同的账户号，用于保存时的校验
	 * 
	 * @return 已有账户的ID
	 * @throws Exception
	 */
	private long findSameAccountID(String strAccountNo, long lOfficeID,
			long lCurrencyID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lID = -1;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select ID from sett_Account where nStatusID >0 and sAccountNo='"
							+ strAccountNo
							+ "' and nOfficeID = "
							+ lOfficeID
							+ " and nCurrencyID = " + lCurrencyID);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				lID = rs.getLong("ID");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}

	/**
	 * Add by zwsun, 2007-06-08 判断该帐户是否可以清户-查询当前账户的余额和利息，看是不是都为0
	 * 
	 * @return 如果都为0，则返回true；否则返回false
	 * @throws Exception
	 */
	public boolean checkDeleteCon(long lAccountID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select sum(mBalance) , sum(mInterest)  from sett_SubAccount where nAccountID = "
							+ lAccountID);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				// 判断返回值是否都为0，如果是则表示能清户，返回true
				if(rs.getDouble(1) >= 0 && rs.getDouble(2) >= 0)
				{
					if (rs.getDouble(1) < 0.01 && rs.getDouble(2) < 0.01) {
						
						return true;
					}
				}
			}
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return false;
	}

	/**
	 * 查询当前账户，或者在此客户下面的账户 的余额和利息，是不是为0 为0的不能清户
	 * 
	 * @return 已有账户的ID
	 * @throws Exception
	 */
	public double findAccountBalanceAndInterest(long lAccountID)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		double dReturn = 0.00;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query string
			sbSQL = new StringBuffer();

			AccountInfo ai = findByID(lAccountID);// 取得主账户的信息
			// 活期账户清户时，未校验账户余额是否为零，累计利息是否为零，该客户的其他账户（定期、通知、贷款账户）余额是否为0
			/*
			 * 注释日期:04-12-15,注释人:weilu,原因:活期户清户时只检查其余额和利息,不检查其客户所有账户的余额和利息
			 * if(ai.getAccountTypeID() ==
			 * SETTConstant.AccountType.CURRENTDEPOSIT || ai.getAccountTypeID() ==
			 * SETTConstant.AccountType.FOREIGN_CURRENTDEPOSIT) { sbSQL.append("
			 * select sum(mBalance) + sum(mInterest) as Amount ");
			 * sbSQL.append(" from sett_Account,sett_SubAccount where
			 * sett_Account.ID = sett_SubAccount.nAccountID "); sbSQL.append("
			 * and nClientID = " + ai.getClientID()); } else {
			 */
			sbSQL
					.append(" select sum(mBalance) + sum(mInterest) as Amount from sett_SubAccount where nAccountID = "
							+ lAccountID);
			// }
			// log.print("findAccountBalanceAndInterest SQL : " +
			// sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				dReturn = rs.getDouble("Amount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return dReturn;
	}

	// /**
	// * 得到月度累计发生额 只的到当月的数据
	// * 除了同一客户的活期、定期和通知存款账户之间的资金转移以外，其他情况需计入“当月累计发生额”
	// * @return
	// * @throws Exception
	// */
	// public double getMonthSumAmount(long lAccountID, long lOfficeID, long
	// lCurrencyID) throws Exception
	// {
	// Connection conn = null;
	// double dMonthSumAmount = 0.00;
	// try
	// {
	// //get the connection from Database
	// conn = getConnection();
	// dMonthSumAmount = getMonthSumAmount(lAccountID, lOfficeID, lCurrencyID,
	// conn);
	//
	// cleanup(conn);
	// }
	// catch (Exception exp)
	// {
	// throw exp;
	// }
	// finally
	// {
	// cleanup(conn);
	// }
	// return dMonthSumAmount;
	// }

	/**
	 * 得到月度累计发生额 只的到当月的数据 除了同一客户的活期、定期和通知存款账户之间的资金转移以外，其他情况需计入“当月累计发生额”
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getMonthSumAmount(long lAccountID, long lOfficeID,
			long lCurrencyID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		double dMonthSumAmount = 0.00;
		Connection conn = null;
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();

			Timestamp tsSystemDate = Env.getSystemDate(conn, lOfficeID,
					lCurrencyID);
			sbSQL
					.append(" SELECT SUM(MAMOUNT) SumAmount FROM sett_transaccountdetail WHERE ntransaccountid = "
							+ lAccountID);
			sbSQL
					.append(" AND ntransdirection = 1 AND nstatusid = 3 AND to_char(dtexecute,'yyyy')= "
							+ DataFormat.getYearString(tsSystemDate));
			sbSQL.append(" AND to_char(dtexecute,'mm')= "
					+ DataFormat.getRealMonthString(tsSystemDate));
			sbSQL
//					.append(" AND noppaccountid not in ( select id from sett_account where  naccounttypeid in (01,13,02,14,03,15,04,16) and nclientid= (select nclientid from sett_account where id = "
					.append(" AND noppaccountid not in ( select id from sett_account where  naccounttypeid in (select id from sett_accounttype  where naccountgroupid  in("+AccountGroupType.CURRENT+","+AccountGroupType.FIXED+","+AccountGroupType.NOTIFY+")) and nclientid= (select nclientid from sett_account where id = "
							+ lAccountID + "))");

			String strYearMonth = DataFormat.getYearString(tsSystemDate) + "-"
					+ DataFormat.getRealMonthString(tsSystemDate);
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				dMonthSumAmount = rs.getDouble("SumAmount");
			}
			cleanup(rs);
			cleanup(ps);

			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL
					.append("select mUncheckPaymentAmount from sett_subAccount where nAccountID="
							+ lAccountID + " and nStatusID>0");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				dMonthSumAmount = dMonthSumAmount
						+ rs.getDouble("mUncheckPaymentAmount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return dMonthSumAmount;
	}
	
	/**
	 * 得到日累计发生额 只的到当填的数据 除了同一客户的活期、定期和通知存款账户之间的资金转移以外，其他情况需计入“当月累计发生额”
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getDaySumAmount(long lAccountID, long lOfficeID,
			long lCurrencyID) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		double dDaySumAmount = 0.00;
		Connection conn = null;
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();

			Timestamp tsSystemDate = Env.getSystemDate(conn, lOfficeID,
					lCurrencyID);
			sbSQL
					.append(" SELECT SUM(MAMOUNT) SumAmount FROM sett_transaccountdetail WHERE ntransaccountid = "
							+ lAccountID);
			sbSQL
					.append(" AND ntransdirection = 1 AND nstatusid = 3 " +
							"AND dtexecute = to_date('"+DataFormat.formatDate(tsSystemDate)+"','yyyy-mm-dd')");
			sbSQL
//					.append(" AND noppaccountid not in ( select id from sett_account where  naccounttypeid in (01,13,02,14,03,15,04,16) and nclientid= (select nclientid from sett_account where id = "
					.append(" AND noppaccountid not in ( select id from sett_account where  naccounttypeid in (select id from sett_accounttype  where naccountgroupid  in("+AccountGroupType.CURRENT+","+AccountGroupType.FIXED+","+AccountGroupType.NOTIFY+")) and nclientid= (select nclientid from sett_account where id = "
							+ lAccountID + "))");

			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				dDaySumAmount = rs.getDouble("SumAmount");
			}
			cleanup(rs);
			cleanup(ps);

			sbSQL = null;
			sbSQL = new StringBuffer();
			sbSQL
					.append("select mUncheckPaymentAmount from sett_subAccount where nAccountID="
							+ lAccountID + " and nStatusID>0");
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				dDaySumAmount = dDaySumAmount
						+ rs.getDouble("mUncheckPaymentAmount");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception exp) {
			throw exp;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return dDaySumAmount;
	}

	// private void getInfoFromResultSet(AccountInfo ai, ResultSet rs,
	// Connection conn) throws Exception
	// {
	// try
	// {
	// ai.setAccountID(rs.getLong("ID")); // 账户ID
	// ai.setAccountNo(rs.getString("sAccountNo")); // 账户编号
	// ai.setOfficeID(rs.getLong("nOfficeID")); // 办事处
	// ai.setCurrencyID(rs.getLong("nCurrencyID")); //币种
	// ai.setAccountTypeID(rs.getLong("nAccountTypeID")); // 账户类型
	// ai.setClientID(rs.getLong("nClientID")); // 客户
	// ai.setAccountName(rs.getString("sName")); // 账户名称
	// ai.setOpenDate(rs.getTimestamp("dtOpen")); // 开户日期
	// ai.setInputUserID(rs.getLong("nInputUserID")); // 录入人
	// ai.setInputDate(rs.getTimestamp("dtInput")); // 录入日期
	// ai.setCheckUserID(rs.getLong("nCheckUserID")); // 复核人
	// ai.setCheckDate(rs.getTimestamp("dtCheck")); // 复核日期
	// ai.setCheckStatusID(rs.getLong("nCheckStatusID")); // 复核状态
	// ai.setStatusID(rs.getLong("nStatusID")); // 账户状态
	// ai.setAbstact(rs.getString("sAbstract")); // 摘要
	// ai.setSubject(rs.getString("sSubject")); // 对应科目号
	// ai.setMaxSinglePayAmount(rs.getDouble("mMaxSinglePayAmount"));
	// // 单笔最高金额限制
	// ai.setMinSinglePayAmount(rs.getDouble("mMinSinglePayAmount"));
	// // 单笔最低金额限制
	// ai.setBatchUpdateID(rs.getLong("nBatchUpdateID"));
	// ai.setBatchUpdateTypeID(rs.getLong("nBatchUpdateTypeID"));
	// ai.setFinishDate(rs.getTimestamp("dtFinish"));//清户日期
	// //ai.setMonthSumAmount(getMonthSumAmount(rs.getLong("ID"),rs.getLong("nOfficeID"),rs.getLong("nCurrencyID"),conn));//月度累计发生额
	// }
	// catch (Exception se)
	// {
	// throw se;
	// }
	// }
	public Double queryAccountBalance(String sql) throws Exception {
		Double dl = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			// get the connection from Database

			conn = getConnection();

			ps = conn.prepareStatement(sql);
			// ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Object obj = rs.getBigDecimal("Balance");
				if (obj != null && obj.toString().length() > 0) {
					dl = new Double(obj.toString());
				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			throw (SQLException) e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return dl;
	}

	private void getExtendInfoFromResultSet(AccountExtendInfo aei, ResultSet rs)
			throws Exception {
		try {
			aei.setAccountExtendID(rs.getLong("ID")); // 扩展ID
			aei.setAccountID(rs.getLong("AccountID")); // 账户ID
			aei.setIsSoft(rs.getLong("IsSoft")); // 是否柔控制
			aei.setStatus(rs.getLong("Status")); // 帐户状态
		} catch (Exception se) {
			throw se;
		}
	}

	private void getInfoFromResultSet(AccountInfo ai, ResultSet rs)
			throws Exception {
		try {
			ai.setAccountID(rs.getLong("ID")); // 账户ID
			ai.setAccountNo(rs.getString("sAccountNo")); // 账户编号
			ai.setOfficeID(rs.getLong("nOfficeID")); // 办事处
			ai.setCurrencyID(rs.getLong("nCurrencyID")); // 币种
			ai.setAccountTypeID(rs.getLong("nAccountTypeID")); // 账户类型
			ai.setClientID(rs.getLong("nClientID")); // 客户
			ai.setAccountName(rs.getString("sName")); // 账户名称
			ai.setOpenDate(rs.getTimestamp("dtOpen")); // 开户日期
			ai.setInputUserID(rs.getLong("nInputUserID")); // 录入人
			ai.setInputDate(rs.getTimestamp("dtInput")); // 录入日期
			ai.setCheckUserID(rs.getLong("nCheckUserID")); // 复核人
			ai.setCheckDate(rs.getTimestamp("dtCheck")); // 复核日期
			ai.setCheckStatusID(rs.getLong("nCheckStatusID")); // 复核状态
			ai.setStatusID(rs.getLong("nStatusID")); // 账户状态
			ai.setAbstact(rs.getString("sAbstract")); // 摘要
			ai.setSubject(rs.getString("sSubject")); // 对应科目号
			ai.setMaxSinglePayAmount(rs.getDouble("mMaxSinglePayAmount"));
			// 单笔最高金额限制
			ai.setMinSinglePayAmount(rs.getDouble("mMinSinglePayAmount"));
			// 单笔最低金额限制
			ai.setBatchUpdateID(rs.getLong("nBatchUpdateID"));
			ai.setBatchUpdateTypeID(rs.getLong("nBatchUpdateTypeID"));
			ai.setFinishDate(rs.getTimestamp("dtFinish"));// 清户日期
			// ai.setMonthSumAmount(getMonthSumAmount(rs.getLong("ID"),rs.getLong("nOfficeID"),rs.getLong("nCurrencyID")));//月度累计发生额
		} catch (Exception se) {
			throw se;
		}
	}

	private void setPrepareStatementByInfo(AccountExtendInfo aei,
			PreparedStatement ps, long lTag) throws Exception {
		int i = 1;
		try {
			if (lTag > 0) {
				ps.setLong(i++, aei.getAccountExtendID());
			}
			ps.setLong(i++, aei.getAccountID()); // 账户ID
			ps.setLong(i++, aei.getIsSoft()); // 是否柔性控制
			ps.setLong(i++, aei.getStatus()); // 账户状态
			if (lTag < 0) {
				ps.setLong(i++, aei.getAccountExtendID());
			}
		} catch (Exception se) {
			throw se;
		}
	}

	private void setPrepareStatementByInfo(AccountInfo ai,
			PreparedStatement ps, long lTag) throws Exception {
		int i = 1;
		try {
			if (lTag > 0) {
				ps.setLong(i++, ai.getAccountID());
			}
			ps.setString(i++, ai.getAccountNo()); // 账户编号
			ps.setLong(i++, ai.getOfficeID()); // 办事处
			ps.setLong(i++, ai.getCurrencyID()); // 币种
			ps.setLong(i++, ai.getAccountTypeID()); // 账户类型
			ps.setLong(i++, ai.getClientID()); // 客户
			ps.setString(i++, ai.getAccountName()); // 账户名称
			// ps.setTimestamp(i++, ai.getOpenDate()); // 开户日期
			ps.setLong(i++, ai.getInputUserID()); // 录入人
			// ps.setTimestamp(i++, ai.getInputDate()); // 录入日期
			ps.setLong(i++, ai.getCheckUserID()); // 复核人
			ps.setTimestamp(i++, ai.getCheckDate()); // 复核日期
			ps.setLong(i++, ai.getCheckStatusID()); // 复核状态
			ps.setLong(i++, ai.getStatusID()); // 账户状态
			ps.setString(i++, ai.getAbstact()); // 摘要
			ps.setString(i++, ai.getSubject()); // 对应科目号
			ps.setDouble(i++, ai.getMaxSinglePayAmount()); // 单笔最高金额限制
			ps.setDouble(i++, ai.getMinSinglePayAmount()); // 单笔最低金额限制
			ps.setLong(i++, ai.getBatchUpdateID());// 批量复核号
			ps.setLong(i++, ai.getBatchUpdateTypeID());// 批量复核内容
			ps.setTimestamp(i++, ai.getFinishDate());// 清户日期
			if (lTag < 0) {
				ps.setLong(i++, ai.getAccountID());
			}
		} catch (Exception se) {
			throw se;
		}
	}

	/**
	 * 方法说明：根据账户ID，得到账户信息
	 * 
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public AccountInfo findByID(long lAccountID) throws SQLException {
		AccountInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try {
			// get the connection from Database
			conn = getConnection();
			// establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_Account ");
			sbSQL.append(" where ID = ? ");
			// log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next()) {
				// get the BankBillInfo from current ResultSet object
				ai = new AccountInfo();
				getInfoFromResultSet(ai, rs);

				// 修改 by leiyang (杨垒)(2007-06-12)
				cleanup(rs);
				cleanup(ps);
				if (SETTConstant.AccountType.isFixAccountType(ai
						.getAccountTypeID())
						|| SETTConstant.AccountType.isNotifyAccountType(ai
								.getAccountTypeID())) {
					sbSQL = new StringBuffer();
					sbSQL.append(" select * from sett_AccountExtend ");
					sbSQL.append(" where AccountId = ? ");
					ps = conn.prepareStatement(sbSQL.toString());
					ps.setLong(1, lAccountID);
					rs = ps.executeQuery();
					if (rs.next()) {
						AccountExtendInfo aei = new AccountExtendInfo();
						getExtendInfoFromResultSet(aei, rs);
						ai.setAccountExtendInfo(aei);
					}
				}
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return ai != null ? ai : null;
	}

	public long updateStatus(long lSubAccountID, long lStatusID)
			throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lReturn = -1;
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" update sett_Account set nStatusID = ? where ID=? \n");
			ps = conn.prepareStatement(sbSQL.toString());
			log.print(sbSQL.toString());
			ps.setDouble(1, lStatusID);
			ps.setLong(2, lSubAccountID);
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		} finally {
			cleanup(ps);
			cleanup(conn);
		}
		return lReturn;
	}

	/**
	 * 方法说明：查询总户余额（即各分公司余额汇总，工行-联通项目）
	 * 
	 * @param lAccountTypeID
	 * @return double
	 * @throws Exception
	 */
	public double getTotalBalance(long lOfficeID, long lCurrencyID,
			long lAccountTypeID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		double dTotalBalance = 0.0;
		try {
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL
					.append(" select sum(sa.mBalance) mTotalBalance from sett_Account a,sett_SubAccount sa ");
			sbSQL
					.append(" where sa.nAccountID=a.ID and a.nOfficeID=? and a.nCurrencyID=? ");
			sbSQL
					.append(" and a.nAccountTypeID=? and sa.nStatusID>0 and a.nStatusID>0 ");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			ps.setLong(3, lAccountTypeID);
			rs = ps.executeQuery();
			if (rs.next()) {
				dTotalBalance = rs.getDouble("mTotalBalance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return dTotalBalance;
	}

	/**
	 * 方法说明：计算存款计息余额（工行-联通项目） 1、按存款余额大小排序。 2、根据余额大的先按照存款利率计算原则分配存款计息余额。
	 * 
	 * @param lAccountID
	 * @return AccountInfo 如果返回是空说明不需
	 * @throws Exception
	 */
	public HashMap findDepositBalanceInfo(long lOfficeID, long lCurrencyID,
			long lAccountTypeID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		HashMap hmResult = null;
		double dTotalBalance = 0.0;
		try {
			// 查询总户余额
			dTotalBalance = this.getTotalBalance(lOfficeID, lCurrencyID,
					lAccountTypeID);

			// 如果总户为存款，则需要计算
			if (dTotalBalance > 0.0) {
				// 查询所有余额为正的子账户，并按由大到小排序。
				conn = getConnection();
				sbSQL = new StringBuffer();
				sbSQL
						.append(" select sa.nAccountID AccountID,sa.ID SubAccountID,sa.mBalance Balance from sett_SubAccount sa,sett_Account a ");
				sbSQL
						.append(" where sa.nAccountID=a.ID and sa.mBalance>0 and a.nStatusID>0 and sa.nStatusID>0 ");
				sbSQL
						.append(" and a.nOfficeID=? and a.nCurrencyID=? and a.nAccountTypeID=? ");
				sbSQL.append(" order by sa.mBalance desc ");
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, lOfficeID);
				ps.setLong(2, lCurrencyID);
				ps.setLong(3, lAccountTypeID);
				rs = ps.executeQuery();

				hmResult = new HashMap();
				while (rs.next()) {
					long lSubAccountID = rs.getLong("SubAccountID");
					double dAccountBalance = rs.getDouble("Balance");

					if (dTotalBalance > 0) {
						if (dTotalBalance < dAccountBalance) {
							hmResult.put(String.valueOf(lSubAccountID), String
									.valueOf(dTotalBalance));
							dTotalBalance = 0.0;
						} else {
							hmResult.put(String.valueOf(lSubAccountID), String
									.valueOf(dAccountBalance));
							dTotalBalance = dTotalBalance - dAccountBalance;
						}
					} else {
						break;
					}
				}
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return hmResult;
	}

	/**
	 * 方法说明：取得账户编号
	 * 
	 * @param lOfficeID :
	 *            long
	 * @param lCurrencyID
	 *            :long
	 * @param lAccountTypeID
	 *            :long
	 * @param lClientID
	 *            :long
	 * @return: String - 新增的账户编号 新的编号方法
	 * @throws Exception
	 */
	public String getAccountNoNew(long lOfficeID, long lCurrencyID,
			long lAccountTypeID, long lClientID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lAccountNoPart3 = -1;
		long lAccountNoPart4 = -1;
		String strAccountNo = null;
		String strClientCode = "";
		long DiffCurrencyID = 0;
		StringBuffer sb = new StringBuffer();
		try {
			conn = getConnection();
			sb.setLength(0);
			sb.append(" select scode from client where id=? ");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, lClientID);
			rs = ps.executeQuery();

			if (rs.next()) {
				strClientCode = rs.getString("scode");
				// lDiffOfficeID = rs.getLong("nofficeid");
			}
			cleanup(rs);
			cleanup(ps);
			lAccountNoPart3 = Long.parseLong(strClientCode.substring(3, 7));
			// / 获得该客户已开立的账户号
			boolean bHasAccount = false;
			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)) {
				try {
					sb.setLength(0);
					sb
							.append("select max(to_number(substr(saccountno,12,length(saccountno)))) from sett_Account a  where"
									+ " a.nStatusID in("
									+ SETTConstant.AccountStatus.NORMAL
									+ ","
									+ SETTConstant.AccountStatus.FREEZE
									+ ","
									+ SETTConstant.AccountStatus.SEALUP
									+ ","
									+ SETTConstant.AccountStatus.REPORTLOSS
									+ ","
									+ SETTConstant.AccountStatus.ALLFREEZE
									+ ","
									+ SETTConstant.AccountStatus.PARTFREEZE
									+ ")"
									+ " and nofficeid="
									+ lOfficeID
									+ " and naccounttypeid="
									+ lAccountTypeID
									+ " and nclientid="
									+ lClientID
									+ " and ncurrencyid="
									+ lCurrencyID
									+ " order by saccountno ");
					System.out.println(sb.toString());
					ps = conn.prepareStatement(sb.toString());
					log4j.info(sb.toString());
					rs = ps.executeQuery();

					if (rs.next()) {
						bHasAccount = true;

						// 该客户已经开立了此账户类型的账户
						long str = rs.getLong(1);
						System.out.println(str);
						// 账户类型是活期

						lAccountNoPart4 = str + 1;
						System.out
								.println("lAccountNoPart4:" + lAccountNoPart4);
					}
				} catch (Exception e) {
					bHasAccount = false;
					e.printStackTrace();
				}
			}
			// 关闭资源
			cleanup(rs);
			cleanup(ps);
			// 该客户还没有开立此账户类型的账户,客户号与账户号一致
			strAccountNo = NameRef.getOfficeNoByID(lOfficeID) + "-";
			// DataFormat.formatInt(lOfficeID, 2, true) + "-";
			strAccountNo += DataFormat.formatInt(lAccountTypeID, 2, true) + "-";
			strAccountNo += DataFormat.formatInt(lAccountNoPart3, 4, true);
			// 如果是活期账户，增加随机位
			if (SETTConstant.AccountType.isCurrentAccountType(lAccountTypeID)) {
				if (bHasAccount == false) {
					// 取得随机位值
					strAccountNo += "-1";
				} else {
					strAccountNo += "-" + lAccountNoPart4;
				}
			}
			log4j.info("新增账户号：" + strAccountNo);
			// 关闭资源
			cleanup(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
		return strAccountNo;
	}

	
	/**
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long haveTrans(AccountInfo info) throws Exception
	{
		
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		int index=1;
		try
		{
		conn = getConnection();
		sbSQL = new StringBuffer();
		sbSQL.append("select id from sett_vtransaction t where 1=1 ");
			if(info.getOfficeID()>0)
			{
				sbSQL.append(" and t.OFFICEID=?");
			}
			if(info.getCurrencyID()>0)
			{
				sbSQL.append(" and t.CURRENCYID=?");
			}
			if(info.getAccountID()>0)
			{
				sbSQL.append(" and ( t.PAYACCOUNTID=? or t.RECEIVEACCOUNTID=? ) ");
			}
			sbSQL.append(" and t.STATUSID<>0 ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			if(info.getOfficeID()>0)
			{
				ps.setLong(index++, info.getOfficeID());
			}
			if(info.getCurrencyID()>0)
			{
				ps.setLong(index++, info.getCurrencyID());
			}
			if(info.getAccountID()>0)
			{
				ps.setLong(index++, info.getAccountID());
				ps.setLong(index++, info.getAccountID());
			}
			rs = ps.executeQuery();
			if(rs.next())
			{
				lReturn = rs.getLong("id");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			
		} catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return lReturn;
	
	}
	
	/**
	 * 账户余额查询
	 * @param lAccountID
	 * @param queryDate
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return
	 * @throws Exception
	 */
	public double queryAccountBalanceByDate(long lAccountID,Timestamp queryDate,long lOfficeID,long lCurrencyID) throws Exception {
		double balance = 0.00;
		StringBuffer buffer =null;
		
		//判断是否是今天
		boolean isToday = true;//queryDate 为空则按今天处理
		Timestamp tsOpenDate = Env.getSystemDate(lOfficeID, lCurrencyID);
		if (tsOpenDate != null && queryDate != null)
		{
			if (tsOpenDate.toString().substring(0, 10).equals(queryDate.toString().substring(0, 10)))
				isToday = true;
			else
				isToday = false;
		}
		if(isToday){
			buffer = new StringBuffer();
			
			buffer.append("select distinct a.naccountid,a.balance,a.interest,a.availableBalance,a.limitamount, aa.IsNegotiate \n");
			buffer.append(" from  (select sum(nvl(ac_nIsNegotiate,0)) IsNegotiate,naccountid from sett_subaccount group by naccountid) aa, \n");
			
			buffer.append("      (select acc.id　naccountid, sum(round(nvl(subAcct.mbalance,0),2)) balance,sum(round(nvl(subAcct.ac_mcapitallimitamount,0),2)) limitamount,sum(round(nvl(decode(subAcct.minterest,0,subAcct.af_mpredrawinterest,subAcct.minterest),0),2)+round(nvl(subAcct.ac_mNegotiateInterest,0),2)) Interest, \n");
			buffer.append("              sum(round(nvl(decode(subAcct.nstatusid,1,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,5,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,4,0,2,0,7,0,8,subAcct.mbalance-subAcct.MUNCHECKPAYMENTAMOUNT,0),0),2)) availableBalance \n");
			buffer.append("       from sett_account acc,sett_subaccount subAcct \n");
			buffer.append("       where acc.nofficeid=" + lOfficeID + " and acc.ncurrencyid=" + lCurrencyID + " and acc.id=subAcct.naccountid(+) \n");
			buffer.append("      group by acc.id) a \n");
			buffer.append(" where a.naccountid=aa.naccountid(+)  \n");
			buffer.append(" and a.naccountid= " + lAccountID +" \n");
		}else{
			buffer = new StringBuffer();
			
			buffer.append(" select distinct a.naccountid,a.balance,a.interest,a.UncheckPaymentAmount,ac_nInterestRatePlanID InterestPlanID,sett_subaccount.NSTATUSID SubAccountStatus,sett_subaccount.ac_mcapitallimitamount limitamount,ac_nIsNegotiate IsNegotiate \n");
			buffer.append("  from  sett_subaccount, \n");
			buffer.append("  (select tt.naccountid,sum(round(nvl(tt.balance,0),2)) balance,sum(round(nvl(decode(tt.Interest,0,tt.af_mpredrawinterest,tt.Interest),0),2)) Interest, 0.0 UncheckPaymentAmount from \n");
			buffer.append("       (select distinct daily.naccountid naccountid, daily.mbalance balance,daily.minterest Interest, subAcct.af_mpredrawinterest af_mpredrawinterest \n");		
			buffer.append("        from sett_account acct,sett_DailyAccountBalance daily,sett_subaccount subacct \n");
			buffer.append("        where acct.nofficeid=" + lOfficeID + " and acct.ncurrencyid=" + lCurrencyID + " and acct.id=daily.naccountid \n");
			buffer.append("          and daily.dtDate=to_date('" + DataFormat.getDateString(queryDate) + "','yyyy-mm-dd')  \n");
			buffer.append("          and acct.id=subacct.naccountid  \n");
			buffer.append("          and subacct.nstatusid="+SETTConstant.SubAccountStatus.NORMAL+" \n");
			buffer.append("	) tt  \n");
			buffer.append("       group by tt.naccountid) a \n");
			buffer.append("  where a.naccountid=sett_subaccount.naccountid \n");
			buffer.append("  and a.naccountid= " + lAccountID +" \n");
		}
		Connection conn = null;
		
		String sql = buffer.toString();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				balance = rs.getDouble("balance");
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		} catch (Exception e) {
			throw (SQLException) e;
		} finally {
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return balance;
	}
}

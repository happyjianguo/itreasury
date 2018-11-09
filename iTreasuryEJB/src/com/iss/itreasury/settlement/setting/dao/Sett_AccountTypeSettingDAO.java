/*
 * Created on 2004-9-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dataentity.CurrencySubjectInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.interest.dataentity.InterestCalculationModeQueryEntity;
import com.iss.itreasury.settlement.setting.dataentity.AccountTypeSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.SubAccountTypeFixedDepositInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_AccountTypeSettingDAO extends SettlementDAO
{
	
	public Sett_AccountTypeSettingDAO ()
	{
		super ("Sett_AccountType",true) ;
		setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	
	public Sett_AccountTypeSettingDAO (String tableName,boolean isNeedPrefix)
	{
		super(tableName,isNeedPrefix);
	}

	/**
	 * 查询某账户组所有账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询某账户组所有账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>返回Collection，包含类AccountTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lAccountGroupID
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID) throws SettlementException
	{
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" SELECT * \n");
			strSQLBuff.append(" FROM   sett_AccountType \n");
			strSQLBuff.append(" WHERE  nStatusID="+Constant.RecordStatus.VALID+" \n");
			if (lAccountGroupID > 0)
			{
				strSQLBuff.append(" AND  nAccountGroupID="+lAccountGroupID);
			}
			System.out.println(strSQLBuff.toString());
			
			prepareStatement(strSQLBuff.toString());
			executeQuery();
			AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo(); 
			Collection c = getDataEntitiesFromResultSet(accountTypeInfo.getClass());
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	//added by qhzhou 2007.6.18
	/**
	 * 查询某账户组所有账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询某账户组所有账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>返回Collection，包含类AccountTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lAccountGroupID
	 * @param lOfficeID    办事处
	 * @param lCurrencyID  币种
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID,long lOfficeID, long lCurrencyID) throws SettlementException
	{
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" SELECT * \n");
			strSQLBuff.append(" FROM   sett_AccountType \n");
			strSQLBuff.append(" WHERE  nStatusID="+Constant.RecordStatus.VALID+" \n");
			if (lAccountGroupID > 0)
			{
				strSQLBuff.append(" AND  nAccountGroupID="+lAccountGroupID);
				strSQLBuff.append(" AND  OFFICEID="+lOfficeID);
				strSQLBuff.append(" AND  CURRENCYID="+lCurrencyID);
			}
			System.out.println(strSQLBuff.toString());
			
			prepareStatement(strSQLBuff.toString());
			executeQuery();

			Collection c = getDataEntitiesFromResultSet(AccountTypeSettingInfo.class);
			finalizeDAO();
			return c;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 查询某账户组所有账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>查询某账户组所有账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>返回Collection，包含类AccountTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lAccountGroupID
	 * @param lPageLineCount  每页行数条件
	 * @param lPageNo         第几页条件
	 * @param lOrderParam     排序条件，根据此参数决定结果集排序条件
	 * @param lDesc           升序或降序
	 * @return Collection
	 * @exception Exception
	 */
	public Collection findAllAccountTypeByGroupID(long lAccountGroupID, long lOfficeID, long lCurrencyID, long lPageLineCount, long lPageNo, long lOrderParam, long lDesc) 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		StringBuffer strBuff = new StringBuffer();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		String[] subjectCodes =null;
		try
		{
			//返回需求的结果集
			con = Database.getConnection();
			strBuff = new StringBuffer();
			strBuff.append("SELECT ID  ,sAccountTypeCode,sAccountType ,nIsExistSubClass ,");
			strBuff.append(" nIsLoanType,nIsLoanMonth,nIsLoanYear,nIsConsign,nIsDraftType ,");
			strBuff.append(" sDefaultDocCode,nAutoClearAccount,nAccountGroupID,nIsClient,nIsAccount,nIsDeposit,nIsContract,sSubjectCode,sInterestSubjectCode,sBookedInterestSubjectCode,sNegotiateInterestSubjectCode,AccountModule,interestCalculationMode,payModule ");
			strBuff.append(" FROM ( ");
			strBuff.append(" SELECT ID,sAccountTypeCode,sAccountType ,nIsExistSubClass , nIsLoanType,nIsLoanMonth,nIsLoanYear,nIsConsign,nIsDraftType ,nIsClient, nIsAccount,nIsDeposit,nIsContract, \n");
			strBuff.append(" 	   sDefaultDocCode,nAutoClearAccount,nAccountGroupID,sett_currencysubject.ssubject sSubjectCode,sett_currencysubject.sInterestSubject sInterestSubjectCode,sett_currencysubject.sBookedInterestSubject sBookedInterestSubjectCode,sett_currencysubject.sNegotiateInterestSubject sNegotiateInterestSubjectCode,AccountModule ,payModule,interestCalculationMode\n");
			strBuff.append(" FROM   sett_currencysubject,( select * from sett_AccountType where officeid= "+lOfficeID+" and currencyid= "+lCurrencyID+" ) \n");
			strBuff.append(" WHERE  nbackofficeid(+)=? and ncurrencyid(+)=? and stablename(+)='Sett_accounttype'and nrecordid(+)=id \n");
			strBuff.append(" 	   and nStatusID=? \n");
			if (lAccountGroupID != 0)//!=Notes.CODE_RECORD_ALL
			{
				strBuff.append(" AND  nAccountGroupID=?");
			}
			switch ((int) lOrderParam)
			{
				case 1 :
					strBuff.append(" ORDER BY ID");
					break;
				case 2 :
					strBuff.append(" ORDER BY to_number(sAccountTypeCode)");
					break;
				case 3 :
					strBuff.append(" ORDER BY sAccountType");
					break;
				case 4 :
					strBuff.append(" ORDER BY nIsExistSubClass");
					break;
				case 5 :
					strBuff.append(" ORDER BY nIsLoanType");
					break;
				case 6 :
					strBuff.append(" ORDER BY nIsLoanMonth");
					break;
				case 7 :
					strBuff.append(" ORDER BY nIsLoanYear");
					break;
				case 8 :
					strBuff.append(" ORDER BY nIsConsign");
					break;
				case 9 :
					strBuff.append(" ORDER BY nIsDraftType");
					break;
				case 10 :
					strBuff.append(" ORDER BY sDefaultDocCode");
					break;
				case 11 :
					strBuff.append(" ORDER BY nAutoClearAccount");
					break;
				case 12 :
					strBuff.append(" ORDER BY nAccountGroupID");
					break;
				case 13 :
					strBuff.append(" ORDER BY sSubjectCode");
					break;
				default :
					}
			if (lDesc ==2 )//Notes.CODE_ASCORDESC_DESC
			{
				strBuff.append(" DESC ");
			}
			strBuff.append(" ) ");
			Log.print(strBuff.toString());
			ps = con.prepareStatement(strBuff.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, lOfficeID);
			ps.setLong(nIndex++, lCurrencyID);
			ps.setLong(nIndex++, 1);//Notes.CODE_RECORD_STATUS_VALID;
			if (lAccountGroupID != 0)//Notes.CODE_RECORD_ALL
			{
				ps.setLong(nIndex++, lAccountGroupID);
			}
			rs = ps.executeQuery();
			while (rs.next())
			{
				AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo();
				
				accountTypeInfo.setId(rs.getLong("ID"));
				accountTypeInfo.setAccountTypeCode(rs.getString("sAccountTypeCode"));
				accountTypeInfo.setAccountType( rs.getString("sAccountType"));
				accountTypeInfo.setIsExistSubClass( rs.getLong("nIsExistSubClass"));
				accountTypeInfo.setIsLoanType(rs.getLong("nIsLoanType"));
				accountTypeInfo.setIsLoanMonth( rs.getLong("nIsLoanMonth"));
				accountTypeInfo.setIsLoanYear(rs.getLong("nIsLoanYear"));
				accountTypeInfo.setIsConsign( rs.getLong("nIsConsign"));
				accountTypeInfo.setIsDraftType(rs.getLong("nIsDraftType"));
				accountTypeInfo.setDefaultDocCode(rs.getString("sDefaultDocCode"));
				accountTypeInfo.setAutoClearAccount( rs.getLong("nAutoClearAccount"));
				accountTypeInfo.setAccountGroupId(rs.getLong("nAccountGroupID"));
				accountTypeInfo.setIsClient( rs.getLong("nIsClient"));
				accountTypeInfo.setIsAccount(rs.getLong("nIsAccount"));//add by xwhe 2008-04-29
				accountTypeInfo.setIsDeposit(rs.getLong("nIsDeposit"));
				accountTypeInfo.setIsContract(rs.getLong("nIsContract"));//add by jywang 2009-7-15

				accountTypeInfo.setSubjectCode(rs.getString("sSubjectCode"));
				accountTypeInfo.setInterestSubjectCode( rs.getString("sInterestSubjectCode"));
				accountTypeInfo.setNegotiateInterestSubjectCode(rs.getString("sNegotiateInterestSubjectCode"));
				accountTypeInfo.setBookedInterestSubjectCode(rs.getString("sBookedInterestSubjectCode"));

				accountTypeInfo.setAccountModule(rs.getLong("AccountModule"));
				//accountTypeInfo.m_lPageCount = lPageCount;
				accountTypeInfo.setOfficeID(lOfficeID);//2007.6.14
				accountTypeInfo.setCurrencyID(lCurrencyID);//2007.6.14
                //Modify by leiyang  date 2007/06/19
				accountTypeInfo.setInterestCalculationMode(rs.getLong("interestCalculationMode"));
				accountTypeInfo.setpayModule( rs.getLong("PayModule")); //Modify by yeliao  date 2007/07/27
				
				v.add(accountTypeInfo);
			}
			//关闭资源 
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
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return (v.size() > 0 ? v : null);
	}	
	
	
	/**
	 * 保存活期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存活期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveCurrentAccountType(long lID, long lAccountGroupID, String strAccountTypeCode, String strAccountType,long lIsExistSubClass,long lIsClient,long lIsAccount, String strSubjectCode, long lOfficeID, long lCurrencyID,long AccountModule,long payModule)
	{
		long lResult = -1;
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		long lRecordID = lID;
		try
		{
			conn = Database.getConnection();
			if (strAccountTypeCode.equals("-1"))
			{   
				strAccountTypeCode = getNewAccountTypeCode();
				Log.print("enter the getNewAccountTypeCode():" + strAccountTypeCode);
			}
			else
			{
				Log.print("strAccountTypeCode not equals -1 " + strAccountTypeCode);
			}
			if (lID < 0) //增加记录
			{
				String strSQL = "SELECT ID FROM Sett_AccountType WHERE sAccountTypecode=? and nstatusid<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountTypeCode);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = 0;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = "SELECT ID FROM Sett_AccountType WHERE saccounttype=? and nstatusid<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountType);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = -1;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//得到最大的id
				strBuff.append(" SELECT NVL(MAX(id)+1,1) FROM Sett_AccountType ");
				ps = conn.prepareStatement(strBuff.toString());
				rs = ps.executeQuery();
				rs.next();
				lMaxID = rs.getLong(1);
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_AccountType(ID, nAccountGroupID, sAccountType ,sAccountTypeCode,nIsExistSubClass,nIsClient,nIsAccount,sSubjectCode, nStatusID)");
				strBuff.append(" VALUES(?,?,?,?,?,?,?,?)");
				Log.print(strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, lMaxID);
				ps.setLong(nIndex++, lAccountGroupID);
				ps.setString(nIndex++, strAccountType);
				ps.setString(nIndex++, strAccountTypeCode);
				ps.setLong(nIndex++,lIsExistSubClass);
				ps.setLong(nIndex++,lIsClient);
				ps.setLong(nIndex++,lIsAccount);
				ps.setString(nIndex++, strSubjectCode);
				ps.setLong(nIndex++, 1);//Notes.CODE_RECORD_STATUS_VALID
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{ //保证名称不重复
				String strSQL = "select * from Sett_AccountType where sAccountType=? and id<>? and nstatusid<>? and officeId=? and currencyId=?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountType);
				ps.setLong(2, lID);
				ps.setLong(3, 0);//Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(4, lOfficeID);
				ps.setLong(5, lCurrencyID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return -1;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//保证编码不重复
				strSQL = "select * from Sett_AccountType where sAccountTypeCode=? and id<>? and nstatusid<>? and officeId=? and currencyId=?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountTypeCode);
				ps.setLong(2, lID);
				ps.setLong(3, 0);//Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(4, lOfficeID);
				ps.setLong(5, lCurrencyID);				
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strBuff.append("UPDATE Sett_AccountType SET nAccountGroupID=?,  sAccountType=?");
				strBuff.append(", sAccountTypeCode=? ,nIsExistSubClass=?,nIsClient=?,nIsAccount=?,sSubjectCode=?, AccountModule=?,payModule=? WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, lAccountGroupID);
				ps.setString(nIndex++, strAccountType);
				ps.setString(nIndex++, strAccountTypeCode);
				ps.setLong(nIndex++, lIsExistSubClass);
				ps.setLong(nIndex++, lIsClient);
				ps.setLong(nIndex++, lIsAccount);
				ps.setString(nIndex++, strSubjectCode);
				ps.setLong(nIndex++, AccountModule);
				ps.setLong(nIndex++, payModule);
				ps.setLong(nIndex++, lID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}  
			saveCurrencySubject("Sett_accounttype", lRecordID, lOfficeID, lCurrencyID, strSubjectCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return lResult;
	}	
	/**
	 * 保存活期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存活期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long modifySaveReserveAccountType(AccountTypeSettingInfo info)
	{
		long lResult = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		CurrencySubjectInfo csInfo=new CurrencySubjectInfo();
		try
		{
			conn = Database.getConnection();
			//修改记录
			if (info.getId() > 0) 
			{ 
				strBuff.append("UPDATE Sett_AccountType SET nIsExistSubClass=?,nIsAccount=?,sSubjectCode=?,sInterestSubjectCode=?,sNegotiateInterestSubjectCode=?, sBookedInterestSubjectCode=?");
				strBuff.append("  WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, info.getIsExistSubClass());
				ps.setLong(nIndex++, info.getIsAccount());
				ps.setString(nIndex++, info.getSubjectCode());
				ps.setString(nIndex++, info.getInterestSubjectCode());
				ps.setString(nIndex++, info.getNegotiateInterestSubjectCode());
				ps.setString(nIndex++, info.getBookedInterestSubjectCode());
				ps.setLong(nIndex++, info.getId());
				lResult = ps.executeUpdate();
				
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				
				csInfo.setBackOfficeID(info.getOfficeID());
				csInfo.setCurrencyID(info.getCurrencyID());
				csInfo.setRecordID(info.getId());
				csInfo.setTableName("Sett_accounttype");
				csInfo.setSubject(info.getSubjectCode());
				csInfo.setInterestSubject(info.getInterestSubjectCode());
				csInfo.setNegotiateInterestSubject(info.getNegotiateInterestSubjectCode());
				csInfo.setBookedInterestSubject(info.getBookedInterestSubjectCode());
				
				lResult=this.saveCurrencySubject(csInfo);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return lResult;
	}
	/**
	 * 保存活期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存活期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveMarginAccountType(long lID, long lAccountGroupID, String strAccountTypeCode, String strAccountType,long lIsExistSubClass,long lIsClient,long lIsAccount,long lIsDeposit, String strSubjectCode, long lOfficeID, long lCurrencyID,long AccountModule,long payModule)
	{
		long lResult = -1;
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		long lRecordID = lID;
		try
		{
			conn = Database.getConnection();
			if (strAccountTypeCode.equals("-1"))
			{   
				strAccountTypeCode = getNewAccountTypeCode();
				Log.print("enter the getNewAccountTypeCode():" + strAccountTypeCode);
			}
			else
			{
				Log.print("strAccountTypeCode not equals -1 " + strAccountTypeCode);
			}
			if (lID < 0) //增加记录
			{
				String strSQL = "SELECT ID FROM Sett_AccountType WHERE sAccountTypecode=? and nstatusid<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountTypeCode);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = 0;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = "SELECT ID FROM Sett_AccountType WHERE saccounttype=? and nstatusid<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountType);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = -1;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//得到最大的id
				strBuff.append(" SELECT NVL(MAX(id)+1,1) FROM Sett_AccountType ");
				ps = conn.prepareStatement(strBuff.toString());
				rs = ps.executeQuery();
				rs.next();
				lMaxID = rs.getLong(1);
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_AccountType(ID, nAccountGroupID, sAccountType ,sAccountTypeCode,nIsExistSubClass,nIsClient,nIsAccount,nIsDeposit,sSubjectCode, nStatusID)");
				strBuff.append(" VALUES(?,?,?,?,?,?,?,?,?)");
				Log.print(strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, lMaxID);
				ps.setLong(nIndex++, lAccountGroupID);
				ps.setString(nIndex++, strAccountType);
				ps.setString(nIndex++, strAccountTypeCode);
				ps.setLong(nIndex++,lIsExistSubClass);
				ps.setLong(nIndex++,lIsClient);
				ps.setLong(nIndex++,lIsAccount);
				ps.setLong(nIndex++, lIsDeposit);
				ps.setString(nIndex++, strSubjectCode);
				ps.setLong(nIndex++, 1);//Notes.CODE_RECORD_STATUS_VALID
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{ //保证名称不重复
				String strSQL = "select * from Sett_AccountType where sAccountType=? and id<>? and nstatusid<>? and officeId=? and currencyId=?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountType);
				ps.setLong(2, lID);
				ps.setLong(3, 0);//Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(4, lOfficeID);
				ps.setLong(5, lCurrencyID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return -1;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//保证编码不重复
				strSQL = "select * from Sett_AccountType where sAccountTypeCode=? and id<>? and nstatusid<>? and officeId=? and currencyId=?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountTypeCode);
				ps.setLong(2, lID);
				ps.setLong(3, 0);//Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(4, lOfficeID);
				ps.setLong(5, lCurrencyID);				
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strBuff.append("UPDATE Sett_AccountType SET nAccountGroupID=?,  sAccountType=?");
				strBuff.append(", sAccountTypeCode=? ,nIsExistSubClass=?,nIsClient=?,nIsAccount=?,nIsDeposit=?,sSubjectCode=?, AccountModule=?,payModule=? WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, lAccountGroupID);
				ps.setString(nIndex++, strAccountType);
				ps.setString(nIndex++, strAccountTypeCode);
				ps.setLong(nIndex++, lIsExistSubClass);
				ps.setLong(nIndex++, lIsClient);
				ps.setLong(nIndex++, lIsAccount);
				ps.setLong(nIndex++, lIsDeposit);
				ps.setString(nIndex++, strSubjectCode);
				ps.setLong(nIndex++, AccountModule);
				ps.setLong(nIndex++, payModule);
				ps.setLong(nIndex++, lID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}  
			saveCurrencySubject("Sett_accounttype", lRecordID, lOfficeID, lCurrencyID, strSubjectCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return lResult;
	}	
	
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	private long saveCurrencySubject(String strTableName, long lRecordID, long lOfficeID, long lCurrencyID, String strSubject) 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		int nIndex = 1;
		boolean bIsNew = true;
		try
		{
			conn = Database.getConnection();
			// sett_currencysubject( STABLENAME, NRECORDID, NCURRENCYID, SSUBJECT )
			strSQL = "select STABLENAME from Sett_CURRENCYSUBJECT where STABLENAME=? and NRECORDID=? and NCURRENCYID=? and nbackofficeid=?";
			ps = conn.prepareStatement(strSQL);
			nIndex = 1;
			ps.setString(nIndex++, strTableName);
			ps.setLong(nIndex++, lRecordID);
			ps.setLong(nIndex++, lCurrencyID);
			ps.setLong(nIndex++, lOfficeID);
			rs = ps.executeQuery();
			if (rs.next())
				bIsNew = false;
			else
				bIsNew = true;
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//
			if (bIsNew)
			{
				strSQL = "insert into Sett_CURRENCYSUBJECT( STABLENAME, NRECORDID, NCURRENCYID, SSUBJECT,nbackofficeid ) values(?,?,?,?,?)";
				ps = conn.prepareStatement(strSQL);
				nIndex = 1;
				ps.setString(nIndex++, strTableName);
				ps.setLong(nIndex++, lRecordID);
				ps.setLong(nIndex++, lCurrencyID);
				ps.setString(nIndex++, strSubject);
				ps.setLong(nIndex++, lOfficeID);
				lResult = ps.executeUpdate();
			}
			else
			{
				strSQL = "update Sett_CURRENCYSUBJECT set SSUBJECT=? where STABLENAME=? and NRECORDID=? and NCURRENCYID=? and nbackofficeid=?";
				ps = conn.prepareStatement(strSQL);
				nIndex = 1;
				ps.setString(nIndex++, strSubject);
				ps.setString(nIndex++, strTableName);
				ps.setLong(nIndex++, lRecordID);
				ps.setLong(nIndex++, lCurrencyID);
				ps.setLong(nIndex++, lOfficeID);
				lResult = ps.executeUpdate();
			}
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	private long saveCurrencySubject(CurrencySubjectInfo csInfo) 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		int nIndex = 1;
		boolean bIsNew = true;
		try
		{
			conn = Database.getConnection();
			// sett_currencysubject( STABLENAME, NRECORDID, NCURRENCYID, SSUBJECT )
			strSQL = "select STABLENAME from Sett_CURRENCYSUBJECT where STABLENAME=? and NRECORDID=? and NCURRENCYID=? and nbackofficeid=?";
			ps = conn.prepareStatement(strSQL);
			nIndex = 1;
			ps.setString(nIndex++, csInfo.getTableName());
			ps.setLong(nIndex++, csInfo.getRecordID());
			ps.setLong(nIndex++, csInfo.getCurrencyID());
			ps.setLong(nIndex++, csInfo.getBackOfficeID());
			rs = ps.executeQuery();
			if (rs.next())
				bIsNew = false;
			else
				bIsNew = true;
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//
			if (bIsNew)
			{
				strSQL = "insert into Sett_CURRENCYSUBJECT( STABLENAME, NRECORDID, NCURRENCYID,nbackofficeid, SSUBJECT, SINTERESTSUBJECT,SNEGOTIATEINTERESTSUBJECT,SBOOKEDINTERESTSUBJECT) values(?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(strSQL);
				nIndex = 1;
				ps.setString(nIndex++, csInfo.getTableName());
				ps.setLong(nIndex++, csInfo.getRecordID());
				ps.setLong(nIndex++, csInfo.getCurrencyID());
				ps.setLong(nIndex++, csInfo.getBackOfficeID());
				
				ps.setString(nIndex++, csInfo.getSubject());
				ps.setString(nIndex++, csInfo.getInterestSubject());
				ps.setString(nIndex++, csInfo.getNegotiateInterestSubject());
				ps.setString(nIndex++, csInfo.getBookedInterestSubject());
				lResult = ps.executeUpdate();
			}
			else
			{
				strSQL = "update Sett_CURRENCYSUBJECT set SSUBJECT=? , SINTERESTSUBJECT=? , SNEGOTIATEINTERESTSUBJECT=? , SBOOKEDINTERESTSUBJECT=? where STABLENAME=? and NRECORDID=? and NCURRENCYID=? and nbackofficeid=?";
				ps = conn.prepareStatement(strSQL);
				nIndex = 1;

				ps.setString(nIndex++, csInfo.getSubject());
				ps.setString(nIndex++, csInfo.getInterestSubject());
				ps.setString(nIndex++, csInfo.getNegotiateInterestSubject());
				ps.setString(nIndex++, csInfo.getBookedInterestSubject());
				
				ps.setString(nIndex++, csInfo.getTableName());
				ps.setLong(nIndex++, csInfo.getRecordID());
				ps.setLong(nIndex++, csInfo.getCurrencyID());
				ps.setLong(nIndex++, csInfo.getBackOfficeID());
				
				lResult = ps.executeUpdate();
			}
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}

	/**
	 * 得到当前可用的新的账户类型编号
	 */
	public String getNewAccountTypeCode()
	{
		String strResult = "";
		StringBuffer sb = new StringBuffer();
		long lResult = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try
		{
			conn = Database.getConnection();
			sb.append(" select nvl(min(i),1) ming from (select (to_number(sAccountTypeCode)+1) i from Sett_AccountType b where (to_number(sAccountTypeCode)+1) not in (select to_number(a.sAccountTypeCode) from Sett_AccountType a where nStatusID <> ? ) )");
			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1,Constant.RecordStatus.INVALID);
			Log.print("strBuff=" + sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		if (lResult > 99)
		{
			lResult = 99;
		}
		return lResult + "";
	}	
	
	/**
	 * 根据账户类型表sett_accountType的ID和币种查询科目。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public String getSubjectCodeByAccountTypeID(long lCurrencyID,long lOfficeID,long lRecordID)
	{
		String strReturn = "";
		ResultSet rs = null;
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" select sSubject  from sett_currencysubject ");
			strSQLBuff.append(" where sTableName='Sett_accounttype' and nCurrencyID="+lCurrencyID+" and nBackOfficeID="+ lOfficeID +" and nRecordID="+lRecordID+" \n");
			
			System.out.println(strSQLBuff.toString());
			
			prepareStatement(strSQLBuff.toString());
			rs = executeQuery();
			while(rs.next())
			{
				strReturn = rs.getString("sSubject");
			}
			rs.close();
			rs = null;	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		    try {
                finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
		}
		return (strReturn != null ? strReturn : "");

	}	

	
	/**
	 * 根据账户类型表sett_accountType的ID和币种查询科目。
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	public String[] getSomeSubjectCodeByAccountTypeID(long lCurrencyID,long lOfficeID,long lRecordID)
	{
		String[] strReturn = new String[4];
		ResultSet rs = null;
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
            strSQLBuff.append(" select sSubject,sInterestSubject,sNegotiateInterestSubject,sBookedInterestSubject  from sett_currencysubject ");
            strSQLBuff.append(" where sTableName='Sett_accounttype' and nCurrencyID="+lCurrencyID+" and nBackOfficeID="+ lOfficeID +" and nRecordID="+lRecordID+" \n");
            
            System.out.println(strSQLBuff.toString());
            
            prepareStatement(strSQLBuff.toString());
            rs = executeQuery();
            while(rs.next())
            {
                    strReturn[0] = rs.getString("sSubject");
                    strReturn[1] = rs.getString("sInterestSubject");
                    strReturn[2] = rs.getString("sNegotiateInterestSubject");
                    strReturn[3] = rs.getString("sBookedInterestSubject");
            }
			rs.close();
			rs = null;	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		    try {
                finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
		}
		return strReturn;

	}	
	
	/**
	 * 根据标识查询账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType,AccountGroup
	 * <li>返回类AccountTypeInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return AccountTypeInfo
	 * @exception Exception
	 */
	public AccountTypeSettingInfo findAccountTypeByID(long lID, long lOfficeID, long lCurrencyID)
	{
		AccountTypeSettingInfo accountTypeInfo = new AccountTypeSettingInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		int nIndex = 1;
		try
		{
	
			
			conn = Database.getConnection();
			
			strBuff.append("SELECT t.ID,");
			strBuff.append("   t.sAccountTypeCode,");
			strBuff.append("   t.sAccountType,");
			strBuff.append("   t.nIsExistSubClass,");
			strBuff.append("   t.nIsLoanType,");
			strBuff.append("   t.nIsLoanMonth,");
			strBuff.append("   t.nIsLoanYear,");
			strBuff.append("   t.nIsConsign,");
			strBuff.append("   t.nIsDraftType,");
			strBuff.append("   t.nIsClient,");
			strBuff.append("   t.nIsAccount,");
			strBuff.append("   t.nIsDeposit,");
			strBuff.append("   t.sDefaultDocCode,");
			strBuff.append("   t.nAutoClearAccount,");
			strBuff.append("   t.AccountModule,");
			strBuff.append("   t.nAccountGroupID,");
			strBuff.append("   t.interestCalculationMode,");
			strBuff.append("   t.PayModule,");
			strBuff.append("   c.ssubject sSubjectCode,");
			strBuff.append("   c.sinterestsubject sInterestSubjectCode,");
			strBuff.append("   c.snegotiateinterestsubject sNegotiateInterestSubjectCode,");
			strBuff.append("   c.sbookedinterestsubject sBookedInterestSubjectCode");
			strBuff.append(" FROM Sett_AccountType t, sett_currencysubject c ");
			strBuff.append(" where t.id = ? ");
			strBuff.append("	and c.nrecordid(+) = t.id ");
			strBuff.append("	and c.nbackofficeid(+) = ? ");
			strBuff.append("	and c.ncurrencyid(+) = ? ");
			strBuff.append("	and c.STABLENAME(+) = 'Sett_accounttype' ");

			   
			ps = conn.prepareStatement(strBuff.toString());
			//		Log.print("sql =" + strBuff.toString());
			nIndex = 1;
			
			ps.setLong(nIndex++, lID);
			ps.setLong(nIndex++, lOfficeID);
			ps.setLong(nIndex++, lCurrencyID);
			
			rs = ps.executeQuery();
			if (rs.next())
			{
				accountTypeInfo.setId(rs.getLong("ID"));
				accountTypeInfo.setAccountTypeCode(rs.getString("sAccountTypeCode"));
				accountTypeInfo.setAccountType (rs.getString("sAccountType"));
				accountTypeInfo.setIsExistSubClass( rs.getLong("nIsExistSubClass"));
				accountTypeInfo.setIsLoanType( rs.getLong("nIsLoanType"));
				accountTypeInfo.setIsLoanMonth(rs.getLong("nIsLoanMonth"));
				accountTypeInfo.setIsLoanYear( rs.getLong("nIsLoanYear"));
				accountTypeInfo.setIsConsign(rs.getLong("nIsConsign"));
				accountTypeInfo.setIsDraftType(rs.getLong("nIsDraftType"));
				accountTypeInfo.setIsClient(rs.getLong("nIsClient"));
				accountTypeInfo.setIsAccount(rs.getLong("nIsAccount"));
				accountTypeInfo.setIsDeposit(rs.getLong("nIsDeposit"));
				accountTypeInfo.setDefaultDocCode(  rs.getString("sDefaultDocCode"));
				accountTypeInfo.setAutoClearAccount( rs.getLong("nAutoClearAccount"));
				accountTypeInfo.setAccountModule( rs.getLong("AccountModule"));
				accountTypeInfo.setAccountGroupId(rs.getLong("nAccountGroupID"));
				accountTypeInfo.setSubjectCode( rs.getString("sSubjectCode"));
				accountTypeInfo.setInterestSubjectCode( rs.getString("sInterestSubjectCode"));
				accountTypeInfo.setBookedInterestSubjectCode( rs.getString("sBookedInterestSubjectCode"));
				accountTypeInfo.setNegotiateInterestSubjectCode( rs.getString("sNegotiateInterestSubjectCode"));
				accountTypeInfo.setpayModule( rs.getLong("PayModule"));
				//Modify by leiyang  Date 2007/06/19
				accountTypeInfo.setInterestCalculationMode(rs.getLong("interestCalculationMode"));
				
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return accountTypeInfo;
	}
	
	/**
	 * 保存定期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>如果lID<0，则在AccountType表中新增一条记录
	 * <li>否则更新标识是lID的记录信息,及表SubAccountType_FixedDeposit中的对应信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountGroupID
	 * @param strAccountTypeCode
	 * @param strAccountType
	 * @param lIsExistSubClass
	 * @param strDefaultDocCode
	 * @param lIsAutoClearAccount
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveFixedAccountType(long lID, long lAccountGroupID, String strAccountTypeCode, String strAccountType, long lIsExistSubClass, String strDefaultDocCode, long lIsAutoClearAccount, String strSubjectCode,long lIsClient, long lOfficeID, long lCurrencyID) 
	{
		long lResult = -1;
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		long lRecordID = lID;
		try
		{
			conn = Database.getConnection();
			if (strAccountTypeCode.equals("-1"))
			{
				strAccountTypeCode = getNewAccountTypeCode();
				Log.print("enter the getNewAccountTypeCode():" + strAccountTypeCode);
			}
			else
			{
				Log.print("strAccountTypeCode not equals -1 " + strAccountTypeCode);
			}
			if (lID < 0) //增加记录
			{
				String strSQL = "SELECT * FROM Sett_AccountType WHERE sAccountTypecode=? and nstatusid<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountTypeCode);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = 0;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = "SELECT * FROM Sett_AccountType WHERE sAccountType=? and nstatusid<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountType);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = -1;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//得到最大的id
				strSQL = "SELECT NVL(MAX(id)+1,1) FROM Sett_accounttype ";
				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				rs.next();
				lMaxID = rs.getLong(1);
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_AccountType(ID, nAccountGroupID, sAccountType ,sAccountTypeCode,sSubjectCode, nStatusID,");
				strBuff.append(" nIsExistSubClass,sDefaultDocCode,nAutoClearAccount,nIsClient) ");
				strBuff.append(" VALUES(?,?,?,?,?,?,?,?,?,?)");
				Log.print(strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				ps.setLong(1, lMaxID);
				ps.setLong(2, lAccountGroupID);
				ps.setString(3, strAccountType);
				ps.setString(4, strAccountTypeCode);
				ps.setString(5, strSubjectCode);
				ps.setLong(6, 1);//Notes.CODE_RECORD_STATUS_VALID
				ps.setLong(7, lIsExistSubClass);
				ps.setString(8, strDefaultDocCode);
				ps.setLong(9, lIsAutoClearAccount);
				ps.setLong(10,lIsClient);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{
				String strSQL = "SELECT * FROM Sett_AccountType WHERE sAccountTypecode=? and nstatusid<>? and id<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountTypeCode);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(3, lID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = 0;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = "SELECT * FROM Sett_AccountType WHERE sAccountType=? and nstatusid<>? and id<>?";
				ps = conn.prepareStatement(strSQL);
				ps.setString(1, strAccountType);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(3, lID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lResult = -1;
					return lResult;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strBuff.append("UPDATE Sett_AccountType SET nAccountGroupID=?,  sAccountType=?");
				strBuff.append(", sAccountTypeCode=? ,sSubjectCode=? ,nIsExistSubClass=?,");
				strBuff.append(" sDefaultDocCode=?,nAutoClearAccount=? ,nIsClient = ? WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				ps.setLong(1, lAccountGroupID);
				ps.setString(2, strAccountType);
				ps.setString(3, strAccountTypeCode);
				ps.setString(4, strSubjectCode);
				ps.setLong(5, lIsExistSubClass);
				ps.setString(6, strDefaultDocCode);
				ps.setLong(7, lIsAutoClearAccount);
				ps.setLong(8, lIsClient);
				ps.setLong(9, lID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}
			saveCurrencySubject("Sett_accounttype", lRecordID, lOfficeID, lCurrencyID, strSubjectCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return lResult;
	}
	
	/**
	 * Modify by leiyang  date 2007/06/19
	 * 
	 * 
	 * 保存定期账户类型编码设置
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置</b>
	 * <ul>
	 * <li>操作数据库表AccountType
	 * <li>否则更新标识是lID的记录信息,及表SubAccountType_FixedDeposit中的对应信息
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2007, by iSoftStone Inc. All Rights Reserved
	 * @param info
	 * @return void
	 * @exception Exception
	 */
	public void saveFixedAccountType(AccountTypeSettingInfo info) 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		long lRecordID = info.getId();
		try
		{
			conn = Database.getConnection();
			if (info.getAccountTypeCode().equals("-1"))
			{
				info.setAccountTypeCode(getNewAccountTypeCode());
				Log.print("enter the getNewAccountTypeCode():" + info.getAccountTypeCode());
			}
			else
			{
				Log.print("strAccountTypeCode not equals -1 " + info.getAccountTypeCode());
			}
			
			strBuff.append("UPDATE Sett_AccountType SET nAccountGroupID=?,  sAccountType=?");
			strBuff.append(", sAccountTypeCode=? ,sSubjectCode=? ,nIsExistSubClass=?,");
			strBuff.append(" sDefaultDocCode=?,nAutoClearAccount=? ,nIsClient = ?,nIsAccount = ?,nIsDeposit=?,interestCalculationMode = ?  WHERE ID=?");
			ps = conn.prepareStatement(strBuff.toString());
			ps.setLong(1, info.getAccountGroupId());
			ps.setString(2, info.getAccountType());
			ps.setString(3, info.getAccountTypeCode());
			ps.setString(4, info.getSubjectCode());
			ps.setLong(5, info.getIsExistSubClass());
			ps.setString(6, info.getDefaultDocCode());
			ps.setLong(7, info.getAutoClearAccount());
			ps.setLong(8, info.getIsClient());
			ps.setLong(9, info.getIsAccount());
			ps.setLong(10, info.getIsDeposit());
			ps.setLong(11, info.getInterestCalculationMode());
			ps.setLong(12, info.getId());
			ps.executeUpdate();
			ps.close();
			conn.close();

			saveCurrencySubject("Sett_accounttype", lRecordID, info.getOfficeID(),info.getCurrencyID(),info.getSubjectCode());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存定期账户类型编码设置的下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>保存定期账户类型编码设置的下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>如果lID<0，则在SubAccountType_FixedDeposit表中新增一条记录
	 * <li>将状态置为正常
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @param lAccountTypeID
	 * @param lFixedDepositMonthID
	 * @param strSubjectCode
	 * @return void
	 * @exception Exception
	 */
	public long saveSubAccountTypeFixedDeposit(long lID, long lAccountTypeID, long lFixedDepositMonthID, String strSubjectCode, long lOfficeID, long lCurrencyID, java.lang.String strPayInterestSubjectCode, java.lang.String strBookedSubjectCode)
	{
		long lResult = -1;
		long lMaxID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		String strTemp = "";
		long lRecordID = lID;
		try
		{
			conn = Database.getConnection();
			if (lID < 0) //增加记录
			{
				strTemp = "select * from Sett_SubAccountType_Fixed where  NFIXEDDEPOSITMONTHID=? and nstatusid<>? and NACCOUNTTYPEID=?";
				ps = conn.prepareStatement(strTemp);
				ps.setLong(1, lFixedDepositMonthID);
				ps.setLong(2,0);// Notes.CODE_RECORD_STATUS_INVALID
				ps.setLong(3, lAccountTypeID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//得到最大的id
				strBuff.append(" SELECT NVL(MAX(id)+1,1) FROM Sett_SubAccountType_Fixed ");
				ps = conn.prepareStatement(strBuff.toString());
				rs = ps.executeQuery();
				rs.next();
				lMaxID = rs.getLong(1);
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				//insert the new record;
				strBuff = new StringBuffer();
				strBuff.append("INSERT INTO Sett_SubAccountType_Fixed(ID, nAccountTypeID, nFixedDepositMonthID ,sSubjectCode, nStatusID,sPayInterestSubject,sBookedInterestSubject)");
				strBuff.append(" VALUES(?,?,?,?,?,?,?)");
				Log.print(strBuff.toString());
				ps = conn.prepareStatement(strBuff.toString());
				ps.setLong(1, lMaxID);
				ps.setLong(2, lAccountTypeID);
				ps.setLong(3, lFixedDepositMonthID);
				ps.setString(4, strSubjectCode);
				ps.setLong(5, 1);//
				ps.setString(6, strPayInterestSubjectCode);//Notes.CODE_RECORD_STATUS_VALID
				ps.setString(7, strBookedSubjectCode);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
				lRecordID = lMaxID;
			}
			else //修改记录
				{
				strTemp = "select * from Sett_SubAccountType_Fixed where  NFIXEDDEPOSITMONTHID=? and nstatusid<>? and id<>? and  NACCOUNTTYPEID=?";
				ps = conn.prepareStatement(strTemp);
				ps.setLong(1, lFixedDepositMonthID);
				ps.setLong(2, 0);//Notes.CODE_RECORD_STATUS_INVALID;
				ps.setLong(3, lID);
				ps.setLong(4, lAccountTypeID);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					return 0;
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strBuff.append("UPDATE Sett_SubAccountType_Fixed SET nAccountTypeID=?,  nFixedDepositMonthID=?");
				strBuff.append(", sSubjectCode=?,sPayInterestSubject=?,sBookedInterestSubject=?  WHERE ID=?");
				ps = conn.prepareStatement(strBuff.toString());
				ps.setLong(1, lAccountTypeID);
				ps.setLong(2, lFixedDepositMonthID);
				ps.setString(3, strSubjectCode);
				ps.setString(4, strPayInterestSubjectCode);
				ps.setString(5, strBookedSubjectCode);
				ps.setLong(6, lID);
				lResult = ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
			}
			saveCurrencySubject("Sett_SubAccountType_Fixed", lRecordID, lOfficeID, lCurrencyID, strSubjectCode);
			saveCurrencySubject("Sett_SubAccountType_FixedDeposit_PayInterest", lRecordID, lOfficeID, lCurrencyID, strPayInterestSubjectCode);
			saveCurrencySubject("Sett_SubAccountType_FixedDeposit_BookedInterest", lRecordID, lOfficeID, lCurrencyID, strBookedSubjectCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return lResult;
	}	
	
	

	
	
	/**
	 * 根据标识查询定期存款账户类型编码设置的一条下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>根据标识查询定期存款账户类型编码设置的一条下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>返回类SubAccountTypeFixedDepositInfo
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return SubAccountTypeFixedDepositInfo
	 * @exception Exception
	 */
	public SubAccountTypeFixedDepositInfo findSubAccountTypeFixedDepositByID(long lID, long lOfficeID, long lCurrencyID) 
	{
		SubAccountTypeFixedDepositInfo subAccountTypeFixedDepositInfo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer strBuff = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			strBuff.append(" SELECT  ID, nFixedDepositMonthID, nAccountTypeID,sSubjectCode  ,sPayInterestSubject  , sBookedInterestSubject  ,nClientID ,nAccountID,sDepositNO ");
			strBuff.append(" FROM Sett_SubAccountType_Fixed  WHERE ID = ?");
			ps = conn.prepareStatement(strBuff.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			Log.print("kkkkk");
			if (rs.next())
			{
				subAccountTypeFixedDepositInfo = new SubAccountTypeFixedDepositInfo();
				subAccountTypeFixedDepositInfo.m_lID = rs.getLong(1);
				subAccountTypeFixedDepositInfo.m_lFixedDepositMonthID = rs.getLong(2);
				subAccountTypeFixedDepositInfo.m_lAccountTypeID = rs.getLong(3);
				subAccountTypeFixedDepositInfo.m_strSubjectCode = rs.getString(4);
				subAccountTypeFixedDepositInfo.m_strPayInterestSubjectCode = rs.getString(5);
				subAccountTypeFixedDepositInfo.m_strBookedInterestSubjectCode = rs.getString(6);
				
				/**增加客户Id ---2004-11-26 gqfang**/
				subAccountTypeFixedDepositInfo.m_lClientID = rs.getLong(7);
				//added by xwhe 2008-04-30
				subAccountTypeFixedDepositInfo.setAccountId(rs.getLong(8)) ;
				subAccountTypeFixedDepositInfo.setDepositNo(rs.getString(9));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return subAccountTypeFixedDepositInfo;
	}
	
	
	/**
	 * 删除定期账户类型编码设置的下级分类信息
	 * <p>
	 * <b>&nbsp;</b>
	 * <ol><b>删除定期账户类型编码设置的下级分类信息</b>
	 * <ul>
	 * <li>操作数据库表SubAccountType_FixedDeposit
	 * <li>将状态置为删除
	 * </ul>
	 * </ol>
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return void
	 * @exception Exception
	 */
	public long deleteSubAccountTypeFixedDeposit(long lID)
	{
		long lResult = -1;
		String strSQL = "UPDATE Sett_SubAccountType_Fixed SET  nStatusID = ? WHERE ID=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareCall(strSQL);
			ps.setLong(1, 0);//Notes.CODE_RECORD_STATUS_INVALID
			ps.setLong(2, lID);
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			lResult = 1;
			deleteCurrencySubject("Sett_SubAccountType_Fixed", lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}

	
	
	
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-7-2 12:31:14)
	 * @return long
	 * @param lID long
	 * @param strTableName java.lang.String
	 * @param lRecordID long
	 * @param lCurrencyID long
	 * @param strSubject java.lang.String
	 * @exception java.rmi.RemoteException 异常说明。
	 */
	private long deleteCurrencySubject(String strTableName, long lRecordID) throws java.rmi.RemoteException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		int nIndex = 1;
		try
		{
			conn = Database.getConnection();
			strSQL = "delete Sett_CURRENCYSUBJECT where STABLENAME=? and NRECORDID=?  ";
			ps = conn.prepareStatement(strSQL);
			nIndex = 1;
			ps.setString(nIndex++, strTableName);
			ps.setLong(nIndex++, lRecordID);
			lResult = ps.executeUpdate();
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}
	
	
	/*
	 * 
	 */
	public long updateSubjectCodeByAccountTypeID(long lCurrencyID,long lRecordID,String strSubject) throws SettlementException
	{
		long lReturn = -1;
		StringBuffer strSQLBuff = new StringBuffer();
		try
		{
			initDAO();
			//返回需求的结果集
			strSQLBuff = new StringBuffer();
			strSQLBuff.append(" update sett_currencysubject set sSubject = '"+strSubject+"' ");
			strSQLBuff.append(" where sTableName='Sett_accounttype' and nCurrencyID="+lCurrencyID+" and nRecordID="+lRecordID+" \n");
			
			System.out.println(strSQLBuff.toString());
			
			prepareStatement(strSQLBuff.toString());
			lReturn = executeUpdate();
			finalizeDAO();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			e.printStackTrace();throw new SettlementException();
		}
		return lReturn;

	}	
	//此方法作废,已不用
	public void delete(long lID)
	{
	    log.print("开始删除账户类型");

            //	    AccountTypeSettingInfo info = new AccountTypeSettingInfo();
//	    info.setId(lID);
//	    info.setStatusId(Constant.RecordStatus.INVALID);
//	    try {
//            update(info);
//        } catch (ITreasuryDAOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
	}
	
	public long add(AccountTypeSettingInfo info)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String sql = null;
		long lMaxID = -1;
		try
		{
			conn = Database.getConnection();
			sql = "select max(id) from sett_accounttype";
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()){
				lMaxID=rs.getLong(1);
				info.setId(lMaxID+1);
			}
			//关闭资源
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	    try {
            this.initDAO();
            String strSQL = "insert into sett_accountType("
            +"Id,"
        	+"nAccountGroupId,"
        	+"sAccountType,"
        	+"sAccountTypeCode,"
        	+"sSubjectCode,"
        	+"sInterestSubjectCode,"
        	+"sBookedInterestSubjectCode,"
        	+"sNegotiateInterestSubjectCode,"
        	+"nIsExistSubClass,"
        	+"nIsLoanType,"
        	+"nIsLoanMonth,"
        	+"nIsLoanYear,"
        	+"nIsConsign,"
        	+"nIsDraftType,"
        	+"sDefaultDocCode,"
        	+"nAutoClearAccount,"
        	+"nStatusId,"
        	+"nIsClient,"
        	+"nIsAccount, "//add by xwhe 2008-04-29
        	+"nIsDeposit, "
        	+"nIsContract, "
        	+"nIsLoanNote, "
        	+"nBalanceDirection,"
        	+"nIsTransDiscountType,"
        	+"nIsAssure,"
        	+"accountModule,"
        	+"officeID,"		//2007.6.14 qhzhou
        	+"currencyID,"		//2007.6.14 qhzhou
        	+"interestCalculationMode,"  //Modify by leiyang  date 2007/06/19
        	+"payModule)"  //added by yeliao  2007-7-19
        	+"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            transPS = this.prepareStatement(strSQL);
            int tmpInt = 1;
            transPS.setLong(tmpInt++,info.getId());
            transPS.setLong(tmpInt++,info.getAccountGroupId());
            transPS.setString(tmpInt++,info.getAccountType());
            transPS.setString(tmpInt++,info.getAccountTypeCode());
            transPS.setString(tmpInt++,info.getSubjectCode());
            transPS.setString(tmpInt++,info.getInterestSubjectCode());
            transPS.setString(tmpInt++,info.getBookedInterestSubjectCode());
            transPS.setString(tmpInt++,info.getNegotiateInterestSubjectCode());
            transPS.setLong(tmpInt++,info.getIsExistSubClass());
            transPS.setLong(tmpInt++,info.getIsLoanType());
            transPS.setLong(tmpInt++,info.getIsLoanMonth());
            transPS.setLong(tmpInt++,info.getIsLoanYear());
            transPS.setLong(tmpInt++,info.getIsConsign());
            transPS.setLong(tmpInt++,info.getIsDraftType());
            transPS.setString(tmpInt++,info.getDefaultDocCode());
            transPS.setLong(tmpInt++,info.getAutoClearAccount());
            transPS.setLong(tmpInt++,info.getStatusId());
            transPS.setLong(tmpInt++,info.getIsClient());
            transPS.setLong(tmpInt++, info.getIsAccount());//ADD by xwhe 2008-04-29
            transPS.setLong(tmpInt++, info.getIsDeposit());
            transPS.setLong(tmpInt++, info.getIsContract());
            transPS.setLong(tmpInt++, info.getIsLoanNote());
            transPS.setLong(tmpInt++,info.getBalanceDirection());
            transPS.setLong(tmpInt++,info.getIsTransDiscountType());
            transPS.setLong(tmpInt++,info.getIsAssure());
            transPS.setLong(tmpInt++,info.getAccountModule());
            transPS.setLong(tmpInt++,info.getOfficeID());//2007.6.14	增加帐户大类多办事处支持
            transPS.setLong(tmpInt++,info.getCurrencyID());//2007.6.14 	
            transPS.setLong(tmpInt++,info.getInterestCalculationMode()); //Modify by leiyang  date 2007/06/19
            transPS.setLong(tmpInt++,info.getpayModule());   // added by yeliao  增加付款方式   2007-7-19
            transPS.executeUpdate();
        } catch (ITreasuryDAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try {
                this.finalizeDAO();
            } catch (ITreasuryDAOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
	    return info.getId();
	}
	
	/**
	 * Modify by leiyang date 2007/06/20
	 * 
	 * @param icmqe
	 * @return
	 */
	public long findInterestCalculation(InterestCalculationModeQueryEntity icmqe) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lResult = -1;
		try
		{
			conn = Database.getConnection();
			if(icmqe.getAccountId() >0 ) {
				strSQL = "select b.InterestCalculationMode from SETT_ACCOUNT a,Sett_accounttype b where a.id=? and a.naccounttypeid =b.id"; 
			}
			else {
				strSQL = "select c.InterestCalculationMode from sett_subaccount a,SETT_ACCOUNT b, Sett_accounttype c where a.id =? and b.id = a.naccountid and c.id=b.naccounttypeid";
			}
			ps = conn.prepareStatement(strSQL);
			if(icmqe.getAccountId() >0 ) {
				ps.setLong(1, icmqe.getAccountId());
			}
			else {
				ps.setLong(1, icmqe.getSubAccountId());
			}
			
			rs = ps.executeQuery();
			while(rs.next()) {
				lResult = rs.getLong("InterestCalculationMode");
			}
			
			ps.close();
			rs.close();
			conn.close();
			
			ps = null;
			rs = null;
			conn = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return lResult;
	}
	
	public static void main(String args[]) throws Exception
	{
		Sett_AccountTypeSettingDAO dao = new Sett_AccountTypeSettingDAO();
		Collection resultColl = null; 
		String ss = dao.getSubjectCodeByAccountTypeID(1,1,1);
		System.out.println(ss);
	}

	
}

/*
 * Created on 2003-10-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountBankInfo;
import com.iss.itreasury.settlement.setting.dataentity.BranchInfo;
import com.iss.itreasury.settlement.setting.dataentity.*;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sett_BranchDAO extends SettlementDAO
{
	public final static int ORDERBY_ID = 1;
	public final static int ORDERBY_BANKTYPE = 2;

	/**
	 * Constructor for Sett_BranchDAO.
	 * @param conn
	 */
	public Sett_BranchDAO(Connection conn)
	{
		super(conn);
		this.strTableName = "sett_branch";
	}

	/**
	 * 
	 */
	public Sett_BranchDAO()
	{
		super();
		this.strTableName = "sett_branch";
	}

	public static void main(String[] args)
	{
		try
		{
			BranchInfo condition = new BranchInfo();
			condition.setBankTypeID(13);
			condition.setIsAutoVirementByBank(1);

			//Collection result = new Sett_BranchDAO().findByConditions(condition, 2, false);

			Collection result = new Sett_BranchDAO().getAllBank(1, 1);

			Iterator itTemp = result.iterator();

			while (itTemp.hasNext())
			{
				System.out.println(
					UtilOperation.dataentityToString(itTemp.next()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public long add(BranchInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" insert into \n");
		buffer.append(strTableName);
		buffer.append(" \n (ID, \n");
		buffer.append("NOFFICEID,\n");
		buffer.append("SCODE,\n");
		buffer.append("SNAME,\n");
		buffer.append("SSUBJECTCODE,\n");
		buffer.append("SBRANCHPROVINCE,\n");
		buffer.append("SBRANCHCITY,\n");
		buffer.append("NSTATUSID,\n");
		buffer.append("SBANKACCOUNTCODE,\n");
		buffer.append("SCREDITBOOKEDACCOUNT,\n");
		buffer.append("SDEBITBOOKEDACCOUNT,\n");
		buffer.append("NISSINGLE,\n");
		buffer.append("NCURRENCYID,\n");
		buffer.append("SCASHCREDITBOOKEDACCOUNT,\n");
		buffer.append("SCASHDEBITBOOKEDACCOUNT,\n");
		buffer.append("STRANSFERCREDITBOOKEDACCOUNT,\n");
		buffer.append("STRANSFERDEBITBOOKEDACCOUNT,\n");
		buffer.append("SPRINTNAME,\n");
		buffer.append("NBANKTYPE,\n");
		buffer.append("NISAUTOVIREMENTBYBANK,\n");
		buffer.append("SBANKSERVICENAME,\n");
		buffer.append("SENTERPRISENAME,\n");
		buffer.append("SBANKEXCHANGECODE,\n");   //新增联行号
		buffer.append("SBRANCHCODE, \n");  //新增机构号
		buffer.append("BANKSUBJECTTYPE,\n");// 新增开户行科目类型
		buffer.append("ACCOUNTMODULE )\n");//新增账户模式

		try
		{
			conn = this.getConnection();
			buffer.append(" values(?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?,?,?,?,?,");
			buffer.append("?,?,?,?,?,?)\n");
			log.info(buffer.toString());
			pstmt = conn.prepareStatement(buffer.toString());
			long id = this.getMaxID();
			info.setID(id);
			int nIndex = 1;
			pstmt.setLong(nIndex++, info.getID());

			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setString(nIndex++, info.getBranchCode());
			pstmt.setString(nIndex++, info.getBranchName());
			pstmt.setString(nIndex++, info.getSubjectCode());
			pstmt.setString(nIndex++, info.getBranchProvince());
			pstmt.setString(nIndex++, info.getBranchCity());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setString(nIndex++, info.getBankAccountCode());
			pstmt.setString(nIndex++, info.getCreditBookedAccount());
			pstmt.setString(nIndex++, info.getDebitBookedAccount());
			pstmt.setLong(nIndex++, info.getIsSingle());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setString(nIndex++, info.getCashCreditBookedAccount());
			pstmt.setString(nIndex++, info.getCashDebitBookedAccount());
			pstmt.setString(nIndex++, info.getTransferCreditBookedAccount());
			pstmt.setString(nIndex++, info.getTransferDebitBookedAccount());
			pstmt.setString(nIndex++, info.getPrintName());
			pstmt.setLong(nIndex++, info.getBankTypeID());
			pstmt.setLong(nIndex++, info.getIsAutoVirementByBank());
			pstmt.setString(nIndex++, info.getBankServiceName());
			pstmt.setString(nIndex++, info.getEnterpriseName());
			pstmt.setString(nIndex++, info.getBankExchangeCode());//新增联行号
			pstmt.setString(nIndex++, info.getBranchCodeOfBank());//新增机构号
			pstmt.setLong(nIndex++, info.getBankSubjectType());//新增开户行科目类型
			pstmt.setLong(nIndex++, info.getAccountModule());//新增账户模式
			pstmt.execute();
			lReturn = id;
		}
		finally
		{
			this.cleanup(pstmt);
			this.cleanup(conn);
		}
		return lReturn;
	}

	public void update(BranchInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement pstmt = null;

		StringBuffer buffer = new StringBuffer();
		buffer.append("update " + strTableName + " set \n");

		buffer.append("NOFFICEID = ?,\n");                           
		buffer.append("SCODE = ?,\n");                               
		buffer.append("SNAME = ?,\n");                               
		buffer.append("SSUBJECTCODE = ?,\n");                       
		buffer.append("SBRANCHPROVINCE = ?,\n");                  
		buffer.append("SBRANCHCITY = ?,\n");                        
		buffer.append("NSTATUSID = ?,\n");                           
		buffer.append("SBANKACCOUNTCODE = ?,\n");                
		buffer.append("SCREDITBOOKEDACCOUNT = ?,\n");           
		buffer.append("SDEBITBOOKEDACCOUNT = ?,\n");             
		buffer.append("NISSINGLE = ?,\n");                           
		buffer.append("NCURRENCYID = ?,\n");                       
		buffer.append("SCASHCREDITBOOKEDACCOUNT = ?,\n");      
		buffer.append("SCASHDEBITBOOKEDACCOUNT = ?,\n");       
		buffer.append("STRANSFERCREDITBOOKEDACCOUNT = ?,\n"); 
		buffer.append("STRANSFERDEBITBOOKEDACCOUNT = ?,\n");  
		buffer.append("SPRINTNAME = ?,\n");               
		buffer.append("NBANKTYPE = ?,\n");                
		buffer.append("NISAUTOVIREMENTBYBANK = ?,\n"); 
		buffer.append("SBANKSERVICENAME = ?,\n");      
		buffer.append("SENTERPRISENAME = ?, \n");
		buffer.append("SBANKEXCHANGECODE = ?, \n");//新增联行号
		buffer.append("SBRANCHCODE = ?, \n");//新增机构号
		buffer.append("BANKSUBJECTTYPE = ?, \n");//新增开户行科目类型
		buffer.append("ACCOUNTMODULE =? \n");//新增账户模式
		buffer.append("where ID=?\n");

		log.debug(buffer.toString());
		try
		{
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			int nIndex = 1;

			pstmt.setLong(nIndex++, info.getOfficeID());
			pstmt.setString(nIndex++, info.getBranchCode());
			pstmt.setString(nIndex++, info.getBranchName());
			pstmt.setString(nIndex++, info.getSubjectCode());
			pstmt.setString(nIndex++, info.getBranchProvince());
			pstmt.setString(nIndex++, info.getBranchCity());
			pstmt.setLong(nIndex++, info.getStatusID());
			pstmt.setString(nIndex++, info.getBankAccountCode());
			pstmt.setString(nIndex++, info.getCreditBookedAccount());
			pstmt.setString(nIndex++, info.getDebitBookedAccount());
			pstmt.setLong(nIndex++, info.getIsSingle());
			pstmt.setLong(nIndex++, info.getCurrencyID());
			pstmt.setString(nIndex++, info.getCashCreditBookedAccount());
			pstmt.setString(nIndex++, info.getCashDebitBookedAccount());
			pstmt.setString(nIndex++, info.getTransferCreditBookedAccount());
			pstmt.setString(nIndex++, info.getTransferDebitBookedAccount());
			pstmt.setString(nIndex++, info.getPrintName());
			pstmt.setLong(nIndex++, info.getBankTypeID());
			pstmt.setLong(nIndex++, info.getIsAutoVirementByBank());
			pstmt.setString(nIndex++, info.getBankServiceName());
			pstmt.setString(nIndex++, info.getEnterpriseName());
			pstmt.setString(nIndex++, info.getBankExchangeCode());//新增联行号
			pstmt.setString(nIndex++, info.getBranchCodeOfBank());//新增机构号
			pstmt.setLong(nIndex++, info.getBankSubjectType());//新增开户行科目类型
			pstmt.setLong(nIndex++, info.getAccountModule());//新增账户模式
			
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
	 * 方法说明：根据账户ID，得到账户信息
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public BranchInfo findByID(long lBranchID) throws Exception
	{
		BranchInfo ai = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from SETT_BRANCH ");
			sbSQL.append(" where ID = ? ");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBranchID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				ai = new BranchInfo();
				getInfoFromResultSet(ai, rs);
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
		return ai != null ? ai : null;
	}
	
	/**
	 * 判断编号是否重复，lReturn > 0 则重复
	 * added by ypxu
	 * 2007-05-10
	 */
	public long isSameCode(BranchInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lTemp = -1;
		long lID = -1;
		long lBranchCode = Long.parseLong(info.getBranchCode());
		long lReturn = -1;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" select a.ID,a.SCODE from SETT_BRANCH a ");
			sbSQL.append(" where a.nOfficeID=" + info.getOfficeID());
			sbSQL.append(" and a.nCurrencyID=" + info.getCurrencyID());
			sbSQL.append(" and a.nStatusID != " + Constant.RecordStatus.INVALID);
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				lTemp = rs.getLong("SCODE");
				lID = rs.getLong("ID");
				
				if(lBranchCode == lTemp)
				{
					if(info.getID() > 0 && lID == info.getID())
					{
					}
					else
					{
						lReturn = 1;
					}
				}
				
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
		return lReturn;
	}
	
	/**
	 * 方法说明：取得所有开户行的信息
	 * @param lAccountID
	 * @return AccountInfo
	 * @throws Exception
	 */
	public Vector getAllBank(long lOfficeID, long lCurrencyID) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(
				" select * from SETT_BRANCH where nStatusID >0 and nOfficeID = "
					+ lOfficeID
					+ " and nCurrencyID= "
					+ lCurrencyID
					+ " order by sCode");
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				long lAccountBankID = rs.getLong("ID");
				String strBankAccountCode = rs.getString("sBankAccountCode");

				if (lAccountBankID > 0 && strBankAccountCode != null)
				{
					AccountBankInfo abi = new AccountBankInfo();
					abi.setBankID(lAccountBankID);
					abi.setBankAccountNo(strBankAccountCode);
					v.add(abi);
				}
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
		return v != null ? v : null;
	}
		
	/**
	 * 方法说明：根据条件取得开户行的信息
	 * @param qbInfo QueryBranchInfo
	 * @return Vector（BranchInfo）
	 * @throws Exception
	 */
	public Vector queryBranch(QueryBranchInfo qbInfo) throws Exception
	{
		Vector v = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(
				" select * from SETT_BRANCH where nStatusID >0 and nOfficeID = "
					+ qbInfo.getOfficeID()
					+ " and nCurrencyID= "
					+ qbInfo.getCurrencyID());
			if(qbInfo.getIsSingleBank()==Constant.YesOrNo.YES || qbInfo.getIsSingleBank()==Constant.YesOrNo.NO )
				sbSQL.append(" and nIsSingle = "					
					+ qbInfo.getIsSingleBank());
			if(qbInfo.getBranchStartID()>=0)
				//sbSQL.append( " and ID >="
				//	+ qbInfo.getBranchStartID());
				sbSQL.append( " and SCODE >="
					+ qbInfo.getBranchStartID());
			if(qbInfo.getBranchEndID()>=0)
				//sbSQL.append( " and ID <="
				//	+ qbInfo.getBranchEndID());
				sbSQL.append( " and SCODE <="
					+ qbInfo.getBranchEndID());
			if(qbInfo.getBankType() > 0)
				sbSQL.append( " and NBANKTYPE ="
					+ qbInfo.getBankType());
			
		//	if(qbInfo.getAccountModule() > 0)
			//	sbSQL.append( " and ACCOUNTMODULE = "
				//	+qbInfo.getAccountModule());
				
			if(qbInfo.getBANKACCOUNTCODE() != null && qbInfo.getBANKACCOUNTCODE().trim().length() > 0)
				sbSQL.append( " and SBANKACCOUNTCODE = '"
					+ qbInfo.getBANKACCOUNTCODE() +"'");
			sbSQL.append( " order by sCode");
					
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				BranchInfo bi = new BranchInfo();
				getInfoFromResultSet(bi,rs);				
				v.add(bi);
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
		return v != null ? v : null;
	}
	
	private void getInfoFromResultSet(BranchInfo bi, ResultSet rs)
		throws Exception
	{
		try
		{
			bi.setID(rs.getLong("ID")); // 账户ID
			bi.setOfficeID(rs.getLong("nOfficeID")); // 办事处
			bi.setCurrencyID(rs.getLong("nCurrencyID")); //币种
			bi.setBranchCode(rs.getString("SCODE")); // 
			bi.setBranchName(rs.getString("SNAME")); // 
			bi.setSubjectCode(rs.getString("SSUBJECTCODE")); // 对应科目号
			bi.setBranchProvince(rs.getString("SBRANCHPROVINCE"));
			bi.setBranchCity(rs.getString("SBRANCHCITY"));
			bi.setBranchProvince(rs.getString("SBRANCHPROVINCE"));
			bi.setStatusID(rs.getLong("nStatusID")); // 状态
			bi.setBankAccountCode(rs.getString("SBANKACCOUNTCODE"));
			bi.setCreditBookedAccount(rs.getString("SCREDITBOOKEDACCOUNT"));
			bi.setDebitBookedAccount(rs.getString("SDEBITBOOKEDACCOUNT"));
			bi.setIsSingle(rs.getLong("NISSINGLE"));
			bi.setCashCreditBookedAccount(
				rs.getString("SCASHCREDITBOOKEDACCOUNT"));
			bi.setCashDebitBookedAccount(
				rs.getString("SCASHDEBITBOOKEDACCOUNT"));
			bi.setTransferCreditBookedAccount(
				rs.getString("STRANSFERCREDITBOOKEDACCOUNT"));
			bi.setTransferDebitBookedAccount(
				rs.getString("STRANSFERDEBITBOOKEDACCOUNT"));
			bi.setPrintName(rs.getString("SPRINTNAME"));
			//add by rongyang for interface of bankservice
			bi.setBankTypeID(rs.getLong("NBANKTYPE"));
			bi.setIsAutoVirementByBank(rs.getLong("NISAUTOVIREMENTBYBANK"));
			bi.setBankServiceName(rs.getString("SBANKSERVICENAME"));
			bi.setEnterpriseName(rs.getString("SENTERPRISENAME"));
			bi.setBankExchangeCode(rs.getString("SBANKEXCHANGECODE"));//联行号
			bi.setBranchCodeOfBank(rs.getString("SBRANCHCODE"));//机构号
			bi.setAccountModule(rs.getLong("ACCOUNTMODULE"));//账户模式
			bi.setBankSubjectType(rs.getLong("BANKSUBJECTTYPE"));//开户行科目类型

		}
		catch (Exception se)
		{
			throw se;
		}
	}

	/**
	 * 获取最大开户行代码
	 * 创建日期：(2002-2-5 18:35:09)
	 * @return long
	 * @param lOfficeID long
	 * @exception Exception 异常说明。
	 */
	public long getMaxBranchCode(long lOfficeID,long lCurrencyID) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long lResult = -1;

		StringBuffer sb = new StringBuffer();

		try
		{ 
			con = getConnection();
			// 如果有跳过的scode，通过下面方sql获取被跳过的scode；如果没有跳过的scode，数据库查询结果返回0。
			sb.append(" select nvl(min(id),0) maxcode \n");
			sb.append(" from (select id from SETT_BRANCH  where nofficeid=? and ncurrencyID=? \n");
			sb.append("       minus \n");
			sb.append("       select to_number(scode) scode from SETT_BRANCH  where nofficeid=? and ncurrencyID=? \n");
			sb.append("       ) \n");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			ps.setLong(3, lOfficeID);
			ps.setLong(4, lCurrencyID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				lResult = rs.getLong(1);
			}
			// 没有跳过的scode,通过下面sql获取最大的scode。
			if (lResult == 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sb.setLength(0);

				sb.append(" select max(to_number(scode))+1 as nMaxAccountType from SETT_BRANCH where nofficeid=? and ncurrencyID=? ");
				ps = con.prepareStatement(sb.toString());
				ps.setLong(1, lOfficeID);
				ps.setLong(2, lCurrencyID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					lResult = rs.getLong(1);
				}
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

		return lResult;
	}


	/**
	 * 
	* Title:        		iTreasury
	* Description:         根据银行类型和银行账户号查询
	* Copyright:                    Copyright (c) 2003 Company: iSoftStone
	* @author             yehuang 
	* @version
	*  Date of Creation    2004-1-15
	 */
	public Collection findByConditions(
		BranchInfo conditionInfo,
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

			strSQLBuffer.append("SELECT * FROM SETT_BRANCH \n");

			//flag for deciding whether there is WHERE in query string
			boolean isNeedWhere = false;

			StringBuffer strWhereSQLBuffer = new StringBuffer(128);

			if (conditionInfo.getID() != -1)
			{
				strWhereSQLBuffer.append(
					" AND ID = " + conditionInfo.getID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getOfficeID() != -1)
			{
				strWhereSQLBuffer.append(
					" AND NOFFICEID = " + conditionInfo.getOfficeID() + " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getCurrencyID() != -1)
			{
				strWhereSQLBuffer.append(
					" AND NCURRENCYID = "
						+ conditionInfo.getCurrencyID()
						+ " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getBankTypeID() != -1)
			{
				strWhereSQLBuffer.append(
					" AND NBANKTYPE = "
						+ conditionInfo.getBankTypeID()
						+ " \n");
				isNeedWhere = true;
			}
			
            if (conditionInfo.getBankAccountCode() != null && !"".equals(conditionInfo.getBankAccountCode()))
            {
                strWhereSQLBuffer.append(
                    " AND SBANKACCOUNTCODE = '"
                        + conditionInfo.getBankAccountCode()
                        + "' \n");
                isNeedWhere = true;
            }
            
            
            //added by mzh_fu 2007/04/19 开户行科目类型
           strWhereSQLBuffer.append(" AND bankSubjectType = '"
					+ conditionInfo.getBankSubjectType() + "' \n");
			isNeedWhere = true;
   
            

			if (conditionInfo.getIsAutoVirementByBank() != -1)
			{
				strWhereSQLBuffer.append(
					" AND NISAUTOVIREMENTBYBANK = "
						+ conditionInfo.getIsAutoVirementByBank()
						+ " \n");
				isNeedWhere = true;
			}

			if (conditionInfo.getStatusIDs() != null)
			{
				strWhereSQLBuffer.append(
					" AND NSTATUSID IN("
						+ conditionInfo.getStatusIDs()[0]
						+ " \n");

				for (int i = 1; i < conditionInfo.getStatusIDs().length; i++)
				{
					strWhereSQLBuffer.append(
						" ," + conditionInfo.getStatusIDs()[i] + " \n");
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
				case ORDERBY_ID :
					{
						strSQLBuffer.append(" ORDER BY ID \n");
					}
					break;
				case ORDERBY_BANKTYPE :
					{
						strSQLBuffer.append(" ORDER BY NBANKTYPE \n");
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

			log.info("Sett_BranchDAO.findByConditions():" + strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			rs = ps.executeQuery();

			result = new ArrayList(32);

			BranchInfo infoTemp = null;
			while (rs.next())
			{
				infoTemp = new BranchInfo();
				this.getInfoFromResultSet(infoTemp, rs);

				result.add(infoTemp);
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
	 * 删除开户行
	 * 操作数据库表Branch
	 * 将状态置为删除
	 * @Copyright (c) Jan. 2002, by iSoftStone Inc. All Rights Reserved
	 * @param lID
	 * @return long
	 * @exception Exception
	 */
	public long deleteBranch(long lID) throws Exception {
		long lResult = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String removeString = null;

		try
		{
			con = getConnection();

			removeString = " update  sett_branch  set nStatusID = ?  WHERE ID =?       ";

			ps = con.prepareStatement(removeString);

			ps.setLong(1, SETTConstant.RecordStatus.INVALID);
			ps.setLong(2, lID);

			lResult = ps.executeUpdate();
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}

		return lResult;

	}

	/**
	 * 获取最大ID
	 * 创建日期：(2002-2-5 18:35:09)
	 * @return long
	 * @exception Exception 异常说明。
	 */
	public long getMaxID() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long lResult = -1;

		StringBuffer sb = new StringBuffer();

		try
		{
			con = getConnection();
			sb.append(" select nvl(max(id),0)+1 id \n");
			sb.append(" from sett_branch  ");
			
			ps = con.prepareStatement(sb.toString());
			
			rs = ps.executeQuery();

			if (rs.next())
			{
				lResult = rs.getLong(1);
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

		return lResult;
	}

	/**
	 * 
	* Title:        		iTreasury
	* Description:         根据银行类型查询
	* Copyright:                    Copyright (c) 2003 Company: iSoftStone
	* @author             ytcui
	* @version
	*  Date of Creation    2004-11-22
	 */
	public BranchInfo[] findAllByBankType(int nBankType)	
		throws Exception
	{
		BranchInfo branchInfo;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		Vector v = new Vector();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from Sett_Branch");
			sbSQL.append(" where NBANKTYPE  = "+nBankType+"");
			sbSQL.append(" and NSTATUSID =  " +Constant.TRUE);
			log.info(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			
			rs = ps.executeQuery();
			while (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				branchInfo = new BranchInfo();
				getInfoFromResultSet(branchInfo, rs);
				v.add(branchInfo);
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
		if(v==null||v.size()==0)
		{
			return null;
		}
		else
		{
			int size = v.size();
			BranchInfo[] infos = new BranchInfo[size];
			for(int i=0;i<size;i++)
			{
				infos[i] = (BranchInfo)v.elementAt(i);
			}
			return infos;
		}
	}
	
	// 根据办事处和币种查找当前的最大编号，并加1返回
	public long getMaxCode(long lOfficeID,long lCurrencyID) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long lResult = -1;

		StringBuffer sb = new StringBuffer();

		try
		{ 
			con = getConnection();
			sb.append(" select nvl(to_number(max(scode))+1,0) from sett_branch where nofficeid=? and ncurrencyID=? ");
			sb.append(" and nstatusid = ");
			sb.append(String.valueOf(Constant.RecordStatus.VALID));
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lOfficeID);
			ps.setLong(2, lCurrencyID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				lResult = rs.getLong(1);
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

		return lResult;
	}
	
}

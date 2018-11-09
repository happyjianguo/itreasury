package com.iss.itreasury.settlement.bankbill.dao;

import java.util.*;
import java.sql.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankbill.dataentity.*;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.bankbill.dao.PageInfo;

////////////////////////////////////////////////////////////////////////////
//
// COPYRIGHT (C) 2003 ISS CORPORATION
//
// ALL RIGHTS RESERVED BY ISS CORPORATION, THIS PROGRAM
// MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS
// FURNISHED BY ISS CORPORATION, NO PART OF THIS PROGRAM
// MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
// WITHOUT THE PRIOR WRITTEN PERMISSION OF ISS CORPORATION.
// USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
// OF THE PROGRAM
//
//            ISS CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
/**
 * Sett_BankBillDAO.java
 * 银行票据的数据访问层
 * @author  Ryan
 * @version 1.0
*/
public class Sett_BankBillDAO extends SettlementDAO
{
	/** insert the specified BankBillInfo object information into the table sett_BankBill
	 * @param the specified BankBillInfo object
	 * @return the id that have been inserted into the table
	 * @exception
	 */
	public long add(BankBillInfo objBankBillInfo) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lID = -1;
		try
		{

			//get the connection from Database
			conn = getConnection();

			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" insert into sett_BankBill( ID, sBillNo, nTypeID, nBankID, nRequireClientID, ");
			sbSQL.append(" nIsReportLoss, nStatusID, dtRegister, nRegisterUserID, dtDelete,");
			sbSQL.append(" nDeleteUserID, dtRequire, nRequireUserID, sRequireClientUserName, dtCancelRequire,");
			sbSQL.append(" nCancelRequireUserID, dtReportLoss, nReportLossUserID, dtCancelReportLoss, nCancelReportLossUserID ) ");
			sbSQL.append(" values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

			ps = conn.prepareStatement(sbSQL.toString());

			//get the maximum id
			lID = getNextID();
			objBankBillInfo.setID(lID);

			//set the PreparedStatement arguments by the BankBillInfo object
			int i = 0;
			ps.setLong(++i, objBankBillInfo.getID());
			ps.setString(++i, objBankBillInfo.getBillNo());
			ps.setLong(++i, objBankBillInfo.getTypeID());
			ps.setLong(++i, objBankBillInfo.getBankID());
			ps.setLong(++i, objBankBillInfo.getRequireClientID());
			ps.setLong(++i, objBankBillInfo.getIsReportLoss());
			ps.setLong(++i, objBankBillInfo.getStatusID());
			ps.setTimestamp(++i, objBankBillInfo.getRegisterDate());
			ps.setLong(++i, objBankBillInfo.getRegisterUserID());
			ps.setTimestamp(++i, objBankBillInfo.getDeleteDate());
			ps.setLong(++i, objBankBillInfo.getDeleteUserID());
			ps.setTimestamp(++i, objBankBillInfo.getRequireDate());
			ps.setLong(++i, objBankBillInfo.getRequireUserID());
			ps.setString(++i, objBankBillInfo.getRequireUserName());
			ps.setTimestamp(++i, objBankBillInfo.getCancelRequire());
			ps.setLong(++i, objBankBillInfo.getCancelRequireUserID());
			ps.setTimestamp(++i, objBankBillInfo.getReportLossDate());
			ps.setLong(++i, objBankBillInfo.getReportLossUserID());
			ps.setTimestamp(++i, objBankBillInfo.getCancelReportLossDate());
			ps.setLong(++i, objBankBillInfo.getCancelReportLossUserID());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);

		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}

	/**
	 * 查询每日日报表的信息
	 * @param lOfficeID 办事处ID
	 * @param tsDate 报表日期
	 * @return
	 * @throws Exception
	 */
	public BankBillDailyReportInfo findDailyReportInfo(long lOfficeID, String strQueryDate,String strOpenDate) throws Exception
	{
		/*
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSql = null; //查询语句

		long[] lAllBillType = SETTConstant.BankBillType.getAllCode(); //票据类型

		QueryCondition_Sett_BankBill queryCondition = new QueryCondition_Sett_BankBill();
		//查询条件
		int iTag = 0; //
		BankBillDailyReportInfo dailyReport = new BankBillDailyReportInfo(lAllBillType.length); //日报信息		
		for (int n = 0; n < lAllBillType.length; n++)
		{ //添加票据类型
			dailyReport.reportItems[n].setLBankBillType(lAllBillType[n]);
		}

		try
		{
			conn = getConnection();
			Timestamp tsDate=DataFormat.getDateTime(strQueryDate);
			Timestamp tsCurrentDate=DataFormat.getDateTime(strOpenDate);
			if (tsDate == null || compareDate(tsDate, tsCurrentDate))
			{ //查询当天纪录
				StringBuffer sbOriginalNumSql = null; //查询初始张数
				Timestamp tsPreviousDate = DataFormat.getDateTime(DataFormat.getDateString(DataFormat.getPreviousDate(tsCurrentDate))); //得到前一天
				//-----------------查询初始张数，以上一天结余为准
				sbOriginalNumSql = new StringBuffer();
				sbOriginalNumSql.append("select nBalance as balance,NBILLITYPEID as typeid from sett_BankBillDailyReport");
				sbOriginalNumSql.append(" where dtdate=?");
				ps = conn.prepareStatement(sbOriginalNumSql.toString());
				ps.setTimestamp(++iTag, tsPreviousDate);
				rs = ps.executeQuery();
				Log.print(sbOriginalNumSql.toString());
				while (rs.next())
				{
					for (int n = 0; n < (dailyReport.reportItems.length - 1); n++)
					{ //多了合计项，要去掉
						if (rs.getInt("typeid") == dailyReport.reportItems[n].getLBankBillType())
						{ //如果类型相同，把前一天的结余作为今天的初始值
							dailyReport.reportItems[n].setLOriginalNum(rs.getLong("balance"));
							break;
						}
					}
				}
				cleanup(rs); //清除查询
				cleanup(ps);
				iTag = 0; //游标置位
				sbSql = new StringBuffer();

				sbSql.append("select sum(InCount) income,sum(OutCount) outcome,nTypeID typeid from ");
				sbSql.append("(select count(*) InCount,0 OutCount,nTypeID from sett_BankBill ");
				sbSql.append("where dtRegister=? and nStatusID not in(0) group by nTypeID ");
				sbSql.append("union ");
				sbSql.append("select 0 InCount,count(*) OutCount,nTypeID from sett_BankBill ");
				sbSql.append("where dtRequire=? and nStatusID not in(0,1) group by nTypeID) ");
				sbSql.append("group by nTypeID");
				Log.print(sbSql.toString());
				Log.print(tsCurrentDate.toString());
				ps = conn.prepareStatement(sbSql.toString());
				ps.setTimestamp(++iTag, tsCurrentDate); //收入和支出从今天的账目中查找
				ps.setTimestamp(++iTag, tsCurrentDate);
				rs = ps.executeQuery();
				while (rs.next())
				{
					for (int n = 0; n < (dailyReport.reportItems.length - 1); n++)
					{ //多了合计项，要去掉
						if (rs.getInt("typeid") == dailyReport.reportItems[n].getLBankBillType())
						{ //如果类型相同，记录入库和出库
							dailyReport.reportItems[n].setLTodayInCome(rs.getLong("income")); //入库
							dailyReport.reportItems[n].setLTodayOutCome(rs.getLong("outcome")); //出库
							break;
						}
					}
				}
				dailyReport.caculateBalance(); //计算结余
				dailyReport.caculateSum(); //计算合计
			}
			else
			{
				sbSql = new StringBuffer();
				sbSql.append("select * from sett_BankBillDailyReport ");
				sbSql.append("where dtDate=?");
				ps = conn.prepareStatement(sbSql.toString());
				ps.setTimestamp(++iTag, tsDate);
				rs = ps.executeQuery();
				while (rs.next())
				{
					for (int n = 0; n < (dailyReport.reportItems.length - 1); n++)
					{ //多了合计项，要去掉
						if (rs.getInt("NBILLITYPEID") == dailyReport.reportItems[n].getLBankBillType())
						{ //如果类型相同，记录入库和出库
							dailyReport.reportItems[n].setLOriginalNum(rs.getLong("nOriginalCount"));
							dailyReport.reportItems[n].setLTodayInCome(rs.getLong("nIncomeCount"));
							dailyReport.reportItems[n].setLTodayOutCome(rs.getLong("nOutCount"));
							dailyReport.reportItems[n].setLBalance(rs.getLong("nBalance"));
						}
					}
				}
				dailyReport.caculateSum(); //计算合计
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return dailyReport;
		*/
		return null;
	}

	/** update the specified BankBillInfo object information to the table sett_BankBill
	 * @param the specified BankBillInfo object
	 * @return the id that have been updated to the table
	 * @exception
	 */
	public long update(BankBillInfo objBankBillInfo) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		long lMaxID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the update sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" update sett_BankBill set sBillNo = ?, nTypeID = ?, nBankID = ?, nRequireClientID = ?, ");
			sbSQL.append(" nIsReportLoss = ?, nStatusID = ?, dtRegister = ?, nRegisterUserID = ?, dtDelete = ?,");
			sbSQL.append(" nDeleteUserID = ?, dtRequire = ?, nRequireUserID = ?, sRequireClientUserName = ?, dtCancelRequire = ?,");
			sbSQL.append(" nCancelRequireUserID = ?, dtReportLoss = ?, nReportLossUserID = ?, dtCancelReportLoss = ?, nCancelReportLossUserID = ? ");
			sbSQL.append(" where ID = ? ");

			ps = conn.prepareStatement(sbSQL.toString());

			//set the PreparedStatement arguments by the BankBillInfo object
			int i = 0;
			ps.setString(++i, objBankBillInfo.getBillNo());
			ps.setLong(++i, objBankBillInfo.getTypeID());
			ps.setLong(++i, objBankBillInfo.getBankID());
			ps.setLong(++i, objBankBillInfo.getRequireClientID());
			ps.setLong(++i, objBankBillInfo.getIsReportLoss());
			ps.setLong(++i, objBankBillInfo.getStatusID());
			ps.setTimestamp(++i, objBankBillInfo.getRegisterDate());
			ps.setLong(++i, objBankBillInfo.getRegisterUserID());
			ps.setTimestamp(++i, objBankBillInfo.getDeleteDate());
			ps.setLong(++i, objBankBillInfo.getDeleteUserID());
			ps.setTimestamp(++i, objBankBillInfo.getRequireDate());
			ps.setLong(++i, objBankBillInfo.getRequireUserID());
			ps.setString(++i, objBankBillInfo.getRequireUserName());
			ps.setTimestamp(++i, objBankBillInfo.getCancelRequire());
			ps.setLong(++i, objBankBillInfo.getCancelRequireUserID());
			ps.setTimestamp(++i, objBankBillInfo.getReportLossDate());
			ps.setLong(++i, objBankBillInfo.getReportLossUserID());
			ps.setTimestamp(++i, objBankBillInfo.getCancelReportLossDate());
			ps.setLong(++i, objBankBillInfo.getCancelReportLossUserID());
			ps.setLong(++i, objBankBillInfo.getID());
			ps.executeUpdate();
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return objBankBillInfo.getID();
	}

	/** get the appropriate BankBillInfo object by id from the table sett_BankBill
	 * @param the specified id
	 * @return the BankBillInfo object
	 * @exception
	 */
	public BankBillInfo findByID(long lID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankBillInfo objBankBillInfo = null;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_BankBill ");
			sbSQL.append(" where ID = ? ");

			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			if (rs.next())
			{

				//get the BankBillInfo from current ResultSet object
				objBankBillInfo = new BankBillInfo();
				objBankBillInfo.setID(rs.getLong("ID"));
				objBankBillInfo.setBillNo(rs.getString("sBillNo"));
				objBankBillInfo.setTypeID(rs.getLong("nTypeID"));
				objBankBillInfo.setBankID(rs.getLong("nBankID"));
				objBankBillInfo.setRequireClientID(rs.getLong("nRequireClientID"));
				objBankBillInfo.setIsReportLoss(rs.getLong("nIsReportLoss"));
				objBankBillInfo.setStatusID(rs.getLong("nStatusID"));
				objBankBillInfo.setRegisterDate(rs.getTimestamp("dtRegister"));
				objBankBillInfo.setRegisterUserID(rs.getLong("nRegisterUserID"));
				objBankBillInfo.setDeleteDate(rs.getTimestamp("dtDelete"));
				objBankBillInfo.setDeleteUserID(rs.getLong("nDeleteUserID"));
				objBankBillInfo.setRequireDate(rs.getTimestamp("dtRequire"));
				objBankBillInfo.setRequireUserID(rs.getLong("nRequireUserID"));
				objBankBillInfo.setRequireUserName(rs.getString("sRequireClientUserName"));
				objBankBillInfo.setCancelRequire(rs.getTimestamp("dtCancelRequire"));
				objBankBillInfo.setCancelRequireUserID(rs.getLong("nCancelRequireUserID"));
				objBankBillInfo.setReportLossDate(rs.getTimestamp("dtReportLoss"));
				objBankBillInfo.setReportLossUserID(rs.getLong("nReportLossUserID"));
				objBankBillInfo.setCancelReportLossDate(rs.getTimestamp("dtCancelReportLoss"));
				objBankBillInfo.setCancelReportLossUserID(rs.getLong("nCancelReportLossUserID"));
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
		return objBankBillInfo != null ? objBankBillInfo : null;
	}

	/** get the appropriate BankBillInfo object by the arguments that are equivalent to id from the table sett_BankBill
	 * the same function with findByID()
	 * @param the specified BankID, BankTypeID, BankBillNo
	 * @return the BankBillInfo object
	 * @exception
	 */
	public BankBillInfo findByCombKey(long lBankID, long lBankBillTypeID, String strBankBillNo) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankBillInfo objBankBillInfo = null;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_BankBill ");
			sbSQL.append(" where nTypeID = ? and nBankID = ? and sBillNo = ? ");
			sbSQL.append(" and nStatusID <> " + SETTConstant.BankBillStatus.DELETE);

			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBankBillTypeID);
			ps.setLong(2, lBankID);
			ps.setString(3, strBankBillNo);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//get the BankBillInfo from current ResultSet object
				objBankBillInfo = new BankBillInfo();
				objBankBillInfo.setID(rs.getLong("ID"));
				objBankBillInfo.setBillNo(rs.getString("sBillNo"));
				objBankBillInfo.setTypeID(rs.getLong("nTypeID"));
				objBankBillInfo.setBankID(rs.getLong("nBankID"));
				objBankBillInfo.setRequireClientID(rs.getLong("nRequireClientID"));
				objBankBillInfo.setIsReportLoss(rs.getLong("nIsReportLoss"));
				objBankBillInfo.setStatusID(rs.getLong("nStatusID"));
				objBankBillInfo.setRegisterDate(rs.getTimestamp("dtRegister"));
				objBankBillInfo.setRegisterUserID(rs.getLong("nRegisterUserID"));
				objBankBillInfo.setDeleteDate(rs.getTimestamp("dtDelete"));
				objBankBillInfo.setDeleteUserID(rs.getLong("nDeleteUserID"));
				objBankBillInfo.setRequireDate(rs.getTimestamp("dtRequire"));
				objBankBillInfo.setRequireUserID(rs.getLong("nRequireUserID"));
				objBankBillInfo.setRequireUserName(rs.getString("sRequireClientUserName"));
				objBankBillInfo.setCancelRequire(rs.getTimestamp("dtCancelRequire"));
				objBankBillInfo.setCancelRequireUserID(rs.getLong("nCancelRequireUserID"));
				objBankBillInfo.setReportLossDate(rs.getTimestamp("dtReportLoss"));
				objBankBillInfo.setReportLossUserID(rs.getLong("nReportLossUserID"));
				objBankBillInfo.setCancelReportLossDate(rs.getTimestamp("dtCancelReportLoss"));
				objBankBillInfo.setCancelReportLossUserID(rs.getLong("nCancelReportLossUserID"));

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
		return objBankBillInfo != null ? objBankBillInfo : null;
	}

	/**
	 * 获取所有的有效的注册状态的票据（申领时使用）
	 * @param lBankID
	 * @param lType
	 * @param strBillNoStart
	 * @param strBillNoEnd
	 * @return
	 * @throws Exception
	 */
	public Collection findAllBillForRequire(long lBankID, long lTypeID, String strBillNoStart, String strBillNoEnd) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankBillInfo objBankBillInfo = null;
		LinkedList lList = new LinkedList();
		try
		{
			//get the connection from Database
			conn = getConnection();
			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nBankID,nTypeID,sBillNo from sett_BankBill ");
			sbSQL.append(" where nStatusID in (" + SETTConstant.BankBillStatus.REGISTER + ") ");
			sbSQL.append(" and nBankID=? and nTypeID=? and sBillNo between ? and ? ");

			Log.print(sbSQL.toString());
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lBankID);
			ps.setLong(2, lTypeID);
			ps.setString(3, strBillNoStart);
			ps.setString(4, strBillNoEnd);
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				objBankBillInfo = new BankBillInfo();
				objBankBillInfo.setBillNo(rs.getString("sBillNo"));
				objBankBillInfo.setTypeID(rs.getLong("nTypeID"));
				objBankBillInfo.setBankID(rs.getLong("nBankID"));
				lList.add(objBankBillInfo);
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
		return lList.size() > 0 ? (Collection) lList : null;
	}

	/** get the Collection which contains BankBillInfo objects by the specified Query Condition
	 * @param the specified QueryCondition_Sett_Others_BankBill
	 * @return the Collection that contains the BankBillInfo objects
	 * @exception
	 */
	public Collection findDetail(QueryCondition_Sett_Others_BankBill objQueryCondition_Settlement_Setting_BankBill) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankBillInfo objBankBillInfo = null;
		LinkedList lList = new LinkedList();
		int iTag = 0;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_BankBill ");
			sbSQL.append(" where nStatusID not in (" + SETTConstant.BankBillStatus.DELETE + "," + SETTConstant.BankBillStatus.TERMINATE + ") ");
			//appends TypeID to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getTypeID() > 0)
			{
				sbSQL.append(" and nTypeID = ? ");
			}

			//appends BankID to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getBankID() > 0)
			{
				sbSQL.append(" and nBankID = ? ");
			}

			//appends RequireClientID to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getRequireClientID() > 0)
			{
				sbSQL.append(" and nRequireClientID = ? ");
			}

			//appends StatusID to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() > 0)
			{
				if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() == 12)
				{
					if (objQueryCondition_Settlement_Setting_BankBill.getIsReportLoss() != 1)
					{
						sbSQL.append(" and nStatusID  in ( " + SETTConstant.BankBillStatus.REGISTER + "," + SETTConstant.BankBillStatus.REQUIRE + " ) ");
						sbSQL.append(" and nIsReportLoss <> ? ");

					}
				}
				else if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() == 15)
				{

				}
				else if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() == 4)
				{
					sbSQL.append(" and nIsReportLoss = ? ");
				}
				else
				{
					sbSQL.append(" and nStatusID = ? ");
					sbSQL.append(" and nIsReportLoss <> ? ");
				}

			}

			//appends RegisterUserID to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getRegisterUserID() > 0)
			{
				sbSQL.append(" and nRegisterUserID = ? ");
			}

			//appends BillNo to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getBillNoStart() != null
				&& !objQueryCondition_Settlement_Setting_BankBill.getBillNoStart().equals("")
				&& objQueryCondition_Settlement_Setting_BankBill.getBillNoEnd() != null
				&& !objQueryCondition_Settlement_Setting_BankBill.getBillNoEnd().equals(""))
			{
				sbSQL.append(" and ( sBillNo between ? and ? ) ");
			}

			//appends RegisterDate to the query where condition
			if (objQueryCondition_Settlement_Setting_BankBill.getRegisterStart() != null && objQueryCondition_Settlement_Setting_BankBill.getRegisterEnd() != null)
			{
				sbSQL.append(" and ( dtRegister between ? and ? ) ");
			}

			switch ((int) objQueryCondition_Settlement_Setting_BankBill.getOrderID())
			{
				case 1 : //发票银行
					sbSQL.append(" order by nBankID ");
					break;
				case 2 : //票据类型
					sbSQL.append(" order by nTypeID ");
					break;
				case 3 : //票据号
					sbSQL.append(" order by sBillNo ");
					break;
				case 4 : //注册日期
					sbSQL.append(" order by dtRegister ");
					break;
				case 5 : //票据状态
					sbSQL.append(" order by nStatusID ");
					break;
				case 6 : //申领客户
					sbSQL.append(" order by nRequireClientID ");
					break;
				case 7 : //录入人
					sbSQL.append(" order by nRegisterUserID ");
					break;
				default :
					sbSQL.append(" order by nBankID,nTypeID,sBillNo ");
					break;
			}
			//正反排序
			if (objQueryCondition_Settlement_Setting_BankBill.getDesc() == SETTConstant.PageControl.CODE_ASCORDESC_DESC)
			{
				sbSQL.append(" desc ");
			}
			Log.print(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());

			//appends TypeID to the proper PreparedStatement argument
			if (objQueryCondition_Settlement_Setting_BankBill.getTypeID() > 0)
			{
				ps.setLong(++iTag, objQueryCondition_Settlement_Setting_BankBill.getTypeID());
			}

			//appends BankID to the proper PreparedStatement argument
			if (objQueryCondition_Settlement_Setting_BankBill.getBankID() > 0)
			{
				ps.setLong(++iTag, objQueryCondition_Settlement_Setting_BankBill.getBankID());
			}

			//appends RequireClientID to the proper PreparedStatement argument
			if (objQueryCondition_Settlement_Setting_BankBill.getRequireClientID() > 0)
			{
				ps.setLong(++iTag, objQueryCondition_Settlement_Setting_BankBill.getRequireClientID());
			}

			if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() > 0)
			{
				if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() == 12)
				{
					if (objQueryCondition_Settlement_Setting_BankBill.getIsReportLoss() != 1)
					{
						ps.setLong(++iTag, SETTConstant.BooleanValue.ISTRUE);
					}
				}
				else if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() == 15)
				{

				}
				else if (objQueryCondition_Settlement_Setting_BankBill.getStatusID() == 4)
				{
					ps.setLong(++iTag, SETTConstant.BooleanValue.ISTRUE);
				}
				else
				{
					ps.setLong(++iTag, objQueryCondition_Settlement_Setting_BankBill.getStatusID());
					ps.setLong(++iTag, SETTConstant.BooleanValue.ISTRUE);
				}

			}

			//appends RegisterUserID to the proper PreparedStatement argument
			if (objQueryCondition_Settlement_Setting_BankBill.getRegisterUserID() > 0)
			{
				ps.setLong(++iTag, objQueryCondition_Settlement_Setting_BankBill.getRegisterUserID());
			}

			//appends BillNo to the proper PreparedStatement argument
			if (objQueryCondition_Settlement_Setting_BankBill.getBillNoStart() != null
				&& !objQueryCondition_Settlement_Setting_BankBill.getBillNoStart().equals("")
				&& objQueryCondition_Settlement_Setting_BankBill.getBillNoEnd() != null
				&& !objQueryCondition_Settlement_Setting_BankBill.getBillNoEnd().equals(""))
			{
				ps.setString(++iTag, objQueryCondition_Settlement_Setting_BankBill.getBillNoStart());
				ps.setString(++iTag, objQueryCondition_Settlement_Setting_BankBill.getBillNoEnd());
			}

			//appends RegisterDate to the proper PreparedStatement argument
			if (objQueryCondition_Settlement_Setting_BankBill.getRegisterStart() != null && objQueryCondition_Settlement_Setting_BankBill.getRegisterEnd() != null)
			{
				ps.setTimestamp(++iTag, objQueryCondition_Settlement_Setting_BankBill.getRegisterStart());
				ps.setTimestamp(++iTag, objQueryCondition_Settlement_Setting_BankBill.getRegisterEnd());
			}
			rs = ps.executeQuery();
			//get all the ResultSet elements
			while (rs.next())
			{
				objBankBillInfo = new BankBillInfo();
				objBankBillInfo.setID(rs.getLong("ID"));
				objBankBillInfo.setBillNo(rs.getString("sBillNo"));
				objBankBillInfo.setTypeID(rs.getLong("nTypeID"));
				objBankBillInfo.setBankID(rs.getLong("nBankID"));
				objBankBillInfo.setRequireClientID(rs.getLong("nRequireClientID"));
				objBankBillInfo.setIsReportLoss(rs.getLong("nIsReportLoss"));
				objBankBillInfo.setStatusID(rs.getLong("nStatusID"));
				objBankBillInfo.setRegisterDate(rs.getTimestamp("dtRegister"));
				objBankBillInfo.setRegisterUserID(rs.getLong("nRegisterUserID"));
				objBankBillInfo.setDeleteDate(rs.getTimestamp("dtDelete"));
				objBankBillInfo.setDeleteUserID(rs.getLong("nDeleteUserID"));
				objBankBillInfo.setRequireDate(rs.getTimestamp("dtRequire"));
				objBankBillInfo.setRequireUserID(rs.getLong("nRequireUserID"));
				objBankBillInfo.setRequireUserName(rs.getString("sRequireClientUserName"));
				objBankBillInfo.setCancelRequire(rs.getTimestamp("dtCancelRequire"));
				objBankBillInfo.setCancelRequireUserID(rs.getLong("nCancelRequireUserID"));
				objBankBillInfo.setReportLossDate(rs.getTimestamp("dtReportLoss"));
				objBankBillInfo.setReportLossUserID(rs.getLong("nReportLossUserID"));
				objBankBillInfo.setCancelReportLossDate(rs.getTimestamp("dtCancelReportLoss"));
				objBankBillInfo.setCancelReportLossUserID(rs.getLong("nCancelReportLossUserID"));
				//append one BankBillInfo to the LinkedList object
				lList.add(objBankBillInfo);
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
		return lList.size() > 0 ? (Collection) lList : null;
	}

	/**
	 * get the collection that contain the result of querying sett_bankbill table
	 * @param queryCondition				a structure of the condition
	 * @return								bankbill collection
	 */
	public Collection findDetail(QueryCondition_Sett_BankBill queryCondition) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSql = null; //条件SQL语句
		StringBuffer sbCountRecordSql = null; //计算记录数量的SQL语句
		StringBuffer sbQuerySql = null; //查询分页后的纪录的SQL语句
		BankBillInfo objBankBillInfo = null;
		LinkedList lList = new LinkedList();
		int iTag = 0;
		long lPageNum=1;
		PageInfo pageInfo = new PageInfo();
		try
		{
			conn = getConnection();

			//-------------------Condition--------------------
			sbSql = new StringBuffer();
			sbSql.append(" select a.*,rownum as r from (select * from sett_BankBill s where 1=1 ");

			//if user specified a bankID,add it to query condition
			if (queryCondition.getLBankID() > 0)
			{
				sbSql.append("and s.nBankID=? ");
			}
			//bill type ID
			if (queryCondition.getLTypeID() > 0)
			{
				sbSql.append("and s.nTypeID=? ");
			}
			//bill number
			if (!queryCondition.getStrBillNoStart().equals("") && !queryCondition.getStrBillNoEnd().equals(""))
			{
				sbSql.append("and (to_number(s.sBillNo) between ? and ?) ");
			}
			//if user only filled in one end of range ,query the specified record
			else
			{
				if (!queryCondition.getStrBillNoStart().equals("") || !queryCondition.getStrBillNoEnd().equals(""))
				{
					sbSql.append("and s.sBillNo=? ");
				}
			}
			//customer ID
			if (queryCondition.getLClientID() > 0)
			{
				sbSql.append("and s.nRequireClientID=? ");
			}
			//registry input user
			if (queryCondition.getLRegisterInputUserID() > 0)
			{
				sbSql.append("and s.nRegisterUserID=? ");
			}
			//user who report loss
			if (queryCondition.getLLostInputUserID() > 0)
			{
				sbSql.append("and s.nReportLossUserID=? ");
			}
			//registry date
			if (queryCondition.getTsRegisterStart() != null && queryCondition.getTsRegisterEnd() != null)
			{
				sbSql.append("and (s.dtRegister between ? and ?) ");
			}
			else
			{
				if (queryCondition.getTsRegisterStart() != null || queryCondition.getTsRegisterEnd() != null)
				{
					sbSql.append("and s.dtREgister=? ");
				}
			}
			//require date
			if (queryCondition.getTsRequireStart() != null && queryCondition.getTsRequireEnd() != null)
			{
				sbSql.append("and (s.dtRequire between ? and ?) ");
			}
			else
			{
				if (queryCondition.getTsRequireStart() != null || queryCondition.getTsRequireEnd() != null)
				{
					sbSql.append("and s.dtRequire=? ");
				}
			}
			//report date
			if (queryCondition.getTsReportLostStart() != null && queryCondition.getTsReportLostEnd() != null)
			{
				sbSql.append("and (s.dtReportLoss between ? and ?) ");
			}
			else
			{
				if (queryCondition.getTsReportLostStart() != null || queryCondition.getTsReportLostEnd() != null)
				{
					sbSql.append("and s.dtReportLoss=? ");
				}
			}
			//bill status
			if (queryCondition.getLBillStatusID() > 0)
			{		//report loss is a special option of status
				if (queryCondition.getLBillStatusID()==SETTConstant.BankBillStatus.REPORTLOSS){
					sbSql.append("and s.NISREPORTLOSS=? ");
				}
				else{
					sbSql.append("and s.nStatusID=? and s.NISREPORTLOSS<>? ");
				}
			}
			else
			{
				sbSql.append("and s.nStatusID<>? and s.nStatusID<>? ");
			}
			//order by
			if (!queryCondition.getStrOrderBy().equals(""))
			{
				sbSql.append("order by s." + queryCondition.getStrOrderBy() + " ");
			}
			else
			{
				sbSql.append("order by s.ID ");
			}
			//order
			if (queryCondition.getLDesc() == SETTConstant.PageControl.CODE_ASCORDESC_DESC)
			{
				sbSql.append("DESC) a");
			}
			else
			{
				sbSql.append("ASC) a");
			}

			Log.print(sbSql.toString());

			sbCountRecordSql = new StringBuffer();
			sbCountRecordSql.append("select count(*) from (" + sbSql.toString() + ")");
			Log.print(sbCountRecordSql.toString());
			//--------------------condition--------------------

			ps = conn.prepareStatement(sbCountRecordSql.toString());
			//---------------------------------------
			setPs(ps, queryCondition);
			//--------------------execute query,count records match the condition------

			rs = ps.executeQuery();
			if (rs.next())
			{
				pageInfo.caculatePageInfo(rs.getLong(1), queryCondition.getLCurrentPageNo()); //计算页数信息
				lPageNum=pageInfo.getLPageCount(); //最大页数
				Log.print("total record num:"+rs.getLong(1));
				Log.print("total page num:  "+pageInfo.getLPageCount());
				Log.print("current page num:"+queryCondition.getLCurrentPageNo());
			}
			else
			{
				throw new IException("SQLException in Sett_BankBillDAO");
			}
			cleanup(rs);
			cleanup(ps);
			//---------------------------------------
			sbQuerySql = new StringBuffer();
			sbQuerySql.append("select * from (" + sbSql.toString() + ") where (r between " + pageInfo.getLRowNumStart() + " and " + pageInfo.getLRowNumEnd() + ")");
			ps = conn.prepareStatement(sbQuerySql.toString());
			setPs(ps, queryCondition);
			rs = ps.executeQuery();
			//---------------------------------------
			while (rs.next())
			{
				objBankBillInfo = new BankBillInfo();
				objBankBillInfo.setLTotalPageNum(lPageNum);
				objBankBillInfo.setID(rs.getLong("ID"));
				objBankBillInfo.setBillNo(rs.getString("sBillNo"));
				objBankBillInfo.setTypeID(rs.getLong("nTypeID"));
				objBankBillInfo.setBankID(rs.getLong("nBankID"));
				objBankBillInfo.setRequireClientID(rs.getLong("nRequireClientID"));
				objBankBillInfo.setIsReportLoss(rs.getLong("nIsReportLoss"));
				objBankBillInfo.setStatusID(rs.getLong("nStatusID"));
				objBankBillInfo.setRegisterDate(rs.getTimestamp("dtRegister"));
				objBankBillInfo.setRegisterUserID(rs.getLong("nRegisterUserID"));
				objBankBillInfo.setDeleteDate(rs.getTimestamp("dtDelete"));
				objBankBillInfo.setDeleteUserID(rs.getLong("nDeleteUserID"));
				objBankBillInfo.setRequireDate(rs.getTimestamp("dtRequire"));
				objBankBillInfo.setRequireUserID(rs.getLong("nRequireUserID"));
				objBankBillInfo.setRequireUserName(rs.getString("sRequireClientUserName"));
				objBankBillInfo.setCancelRequire(rs.getTimestamp("dtCancelRequire"));
				objBankBillInfo.setCancelRequireUserID(rs.getLong("nCancelRequireUserID"));
				objBankBillInfo.setReportLossDate(rs.getTimestamp("dtReportLoss"));
				objBankBillInfo.setReportLossUserID(rs.getLong("nReportLossUserID"));
				objBankBillInfo.setCancelReportLossDate(rs.getTimestamp("dtCancelReportLoss"));
				objBankBillInfo.setCancelReportLossUserID(rs.getLong("nCancelReportLossUserID"));
				//append one BankBillInfo to the LinkedList object
				lList.add(objBankBillInfo);
			}
			Log.print("record Num:" + lList.size());
			//---------------------------------------
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
		return lList.size() > 0 ? (Collection) lList : null;
	}
	/**
	 * 
	 * @param ps
	 * @param queryCondition
	 * @throws Exception
	 */
	private void setPs(PreparedStatement ps, QueryCondition_Sett_BankBill queryCondition) throws Exception
	{
		int iTag = 0;

		if (queryCondition.getLBankID() > 0)
		{
			ps.setLong(++iTag, queryCondition.getLBankID());
		}
		//bill type ID
		if (queryCondition.getLTypeID() > 0)
		{
			ps.setLong(++iTag, queryCondition.getLTypeID());
		}
		//bill number
		if (!queryCondition.getStrBillNoStart().equals("") && !queryCondition.getStrBillNoEnd().equals(""))
		{
			ps.setString(++iTag, queryCondition.getStrBillNoStart());
			ps.setString(++iTag, queryCondition.getStrBillNoEnd());
		}
		//if user only filled in one end of the range ,query the specified record
		else
		{
			if (!queryCondition.getStrBillNoStart().equals(""))
			{
				ps.setString(++iTag, queryCondition.getStrBillNoStart());
			}
			else if (!queryCondition.getStrBillNoEnd().equals(""))
			{
				ps.setString(++iTag, queryCondition.getStrBillNoEnd());
			}
		}
		//customer ID
		if (queryCondition.getLClientID() > 0)
		{
			ps.setLong(++iTag, queryCondition.getLClientID());
		}
		//registry input user
		if (queryCondition.getLRegisterInputUserID() > 0)
		{
			ps.setLong(++iTag, queryCondition.getLRegisterInputUserID());
		}
		//user who report loss
		if (queryCondition.getLLostInputUserID() > 0)
		{
			ps.setLong(++iTag, queryCondition.getLLostInputUserID());
		}
		//registry date
		if (queryCondition.getTsRegisterStart() != null && queryCondition.getTsRegisterEnd() != null)
		{
			ps.setTimestamp(++iTag, queryCondition.getTsRegisterStart());
			ps.setTimestamp(++iTag, queryCondition.getTsRegisterEnd());
		}
		else
		{
			if (queryCondition.getTsRegisterStart() != null)
			{
				ps.setTimestamp(++iTag, queryCondition.getTsRegisterStart());
			}
			else if (queryCondition.getTsRegisterEnd() != null)
			{
				ps.setTimestamp(++iTag, queryCondition.getTsRegisterEnd());
			}
		}
		//require date
		if (queryCondition.getTsRequireStart() != null && queryCondition.getTsRequireEnd() != null)
		{
			ps.setTimestamp(++iTag, queryCondition.getTsRequireStart());
			ps.setTimestamp(++iTag, queryCondition.getTsRequireEnd());
		}
		else
		{
			if (queryCondition.getTsRequireStart() != null)
			{
				ps.setTimestamp(++iTag, queryCondition.getTsRequireStart());
			}
			else if (queryCondition.getTsRequireEnd() != null)
			{
				ps.setTimestamp(++iTag, queryCondition.getTsRequireEnd());
			}
		}
		//report date
		if (queryCondition.getTsReportLostStart() != null && queryCondition.getTsReportLostEnd() != null)
		{
			ps.setTimestamp(++iTag, queryCondition.getTsReportLostStart());
			ps.setTimestamp(++iTag, queryCondition.getTsReportLostEnd());
		}
		else
		{
			if (queryCondition.getTsReportLostStart() != null)
			{
				ps.setTimestamp(++iTag, queryCondition.getTsReportLostStart());
			}
			else if (queryCondition.getTsReportLostEnd() != null)
			{
				ps.setTimestamp(++iTag, queryCondition.getTsReportLostEnd());
			}
		}
		//bill status
		if (queryCondition.getLBillStatusID() > 0)
		{
			if (queryCondition.getLBillStatusID()==SETTConstant.BankBillStatus.REPORTLOSS){
				ps.setLong(++iTag,SETTConstant.BooleanValue.ISTRUE);
			}
			else{
				ps.setLong(++iTag, queryCondition.getLBillStatusID());
				ps.setLong(++iTag,SETTConstant.BooleanValue.ISTRUE);
			}
		}
		else
		{
			ps.setLong(++iTag, SETTConstant.BankBillStatus.DELETE);
			ps.setLong(++iTag, SETTConstant.BankBillStatus.TERMINATE);
		}
	}

	/** get the current maximum id of table sett_BankBill
	 * @return the current maximum id of table sett_BankBill
	 * @exception
	 */
	private long getNextID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lMaxID = -1;
		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select nvl( max( id ) , 0 ) + 1 as maxno ");
			sbSQL.append(" from sett_BankBill ");

			ps = conn.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong("maxno");
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
		return lMaxID;
	}
	/**
	 * 判断两个"日期"的先后,注:忽略时间
	 * @param ts			 原本的时间戳 
	 * @param tsToCompare	 比较的时间戳
	 * @return	true为相等，false为不等
	 */
	public boolean compareDate(Timestamp ts, Timestamp tsToCompare)
	{

		String strTs = ts.toString().substring(0, 10); //截取日期
		String strTsToCompare = tsToCompare.toString().substring(0, 10); //截取时间
		return strTs.equals(strTsToCompare);
	}

	public static void main(String[] args) throws Exception
	{
		Sett_BankBillDAO billDao=new Sett_BankBillDAO();
		QueryCondition_Sett_BankBill bankBill=new QueryCondition_Sett_BankBill();
		//bankBill.setLBankID(1);
		//bankBill.setLClientID(10);
		bankBill.setLBankID(2);
		//bankBill.setLBillStatusID(4);
		
		Collection coll=billDao.findDetail(bankBill);
		Iterator ite=coll.iterator();
		while (ite.hasNext()){
			BankBillInfo b=(BankBillInfo)ite.next();
			Log.print(b.getStatusID());
		}
	}
}
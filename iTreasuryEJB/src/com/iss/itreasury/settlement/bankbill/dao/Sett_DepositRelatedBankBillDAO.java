package com.iss.itreasury.settlement.bankbill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankbill.dataentity.DepositRelatedBankBillInfo;
import com.iss.itreasury.settlement.bankbill.dataentity.QueryCondition_Sett_Others_DepositBankBill;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

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
 * Sett_DepositRelatedBankBillDAO.java
 * 存款相关证实书的数据访问层
 * @author  Ryan
 * @version 1.0
*/

public class Sett_DepositRelatedBankBillDAO extends SettlementDAO
{
	/** insert the specified DepositRelatedBankBillInfo object information into the table sett_DepositRelatedBankBill
	 * @param the specified DepositRelatedBankBillInfo object
	 * @return the id that have been inserted into the table
	 * @exception
	 */
	public long add(DepositRelatedBankBillInfo objDepositRelatedBankBillInfo) throws Exception
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
			sbSQL.append(" insert into sett_DepositRelatedBankBill( nBillID, nOldBillID, nSignUserID, dtSign ) ");
			sbSQL.append(" values( ?,?,?,?) ");

			ps = conn.prepareStatement(sbSQL.toString());

			//set the PreparedStatement arguments by the DepositRelatedBankBillInfo object
			int i = 0;
			ps.setLong(++i, objDepositRelatedBankBillInfo.getBillID());
			ps.setLong(++i, objDepositRelatedBankBillInfo.getOldBillID());
			ps.setLong(++i, objDepositRelatedBankBillInfo.getSignUserID());
			ps.setTimestamp(++i, objDepositRelatedBankBillInfo.getSignDate());
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

	public Collection findDetail(QueryCondition_Sett_Others_DepositBankBill objQueryCondition_Sett_Others_DepositBankBill) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		DepositRelatedBankBillInfo objDepositRelatedBankBillInfo = null;
		LinkedList lList = new LinkedList();
		int iTag = 1;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query string
			sbSQL = new StringBuffer();
			sbSQL.append(" select dr.nBillID as nBillID, dr.nOldBillID as nOldBillID, dr.dtSign as dtSign,bb.nRequireClientID as clientID, ");
			sbSQL.append(" dr.nSignUserID as nSignUserID,c.scode as clientNo, c.sname as clientName , bb.nTypeID as nTypeID,bb.sBillNo as sNewBillNo ");
			sbSQL.append(" from sett_BankBill bb,sett_DepositRelatedBankBill dr,client c ");
			sbSQL.append(" where bb.id = dr.nBillID and bb.nRequireClientID = c.id ");

			//appends nBillID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getNewBillID() > 0)
			{
				sbSQL.append(" and dr.nBillID = ? ");
			}

			//appends nOldBillID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getOldBillID() > 0)
			{
				sbSQL.append(" and dr.nOldBillID = ? ");
			}

			//appends nTypeID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getTypeID() > 0)
			{
				sbSQL.append(" and bb.nTypeID = ? ");
			}

			//appends nBankID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getBankID() > 0)
			{
				sbSQL.append(" and bb.nBankID = ? ");
			}

			//appends nRequireClientID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getRequireClientID() > 0)
			{
				sbSQL.append(" and bb.nRequireClientID = ? ");
			}

			//appends nSignUserID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getResignUserID() > 0)
			{
				sbSQL.append(" and dr.nSignUserID = ? ");
			}

			//appends dtSign to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getRegisterStart() != null)
			{
				sbSQL.append(" and dr.dtSign = ? ");
			}

			switch ((int) objQueryCondition_Sett_Others_DepositBankBill.getOrderID())
			{
				case 1 : //交易号
					sbSQL.append(" order by dr.nBillID, dr.nOldBillID ");
					break;
				case 2 : //客户编号
					sbSQL.append(" order by bb.nRequireClientID ");
					break;
				case 3 : //客户名称
					sbSQL.append(" order by bb.nRequireClientID ");
					break;
				case 4 : //票据类型
					sbSQL.append(" order by bb.nTypeID ");
					break;
				case 5 : //旧票据号
					sbSQL.append(" order by dr.nOldBillID ");
					break;
				case 6 : //新票据号
					sbSQL.append(" order by dr.nBillID ");
					break;
				case 7 : //重新签发日期
					sbSQL.append(" order by dr.dtSign ");
					break;
				case 8 : //签发人
					sbSQL.append(" order by dr.nSignUserID ");
					break;
				default :
					sbSQL.append(" order by dr.nBillID, dr.nOldBillID ");
					break;
			}
			//正反排序
			if (objQueryCondition_Sett_Others_DepositBankBill.getDesc() == SETTConstant.PageControl.CODE_ASCORDESC_DESC)
			{
				sbSQL.append(" desc ");
			}
			Log.print(sbSQL.toString());

			ps = conn.prepareStatement(sbSQL.toString());

			//appends nBillID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getNewBillID() > 0)
			{
				ps.setLong(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getNewBillID());
			}

			//appends nOldBillID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getOldBillID() > 0)
			{
				ps.setLong(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getOldBillID());
			}

			//appends nTypeID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getTypeID() > 0)
			{
				ps.setLong(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getTypeID());
			}

			//appends nBankID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getBankID() > 0)
			{
				ps.setLong(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getBankID());
			}

			//appends nRequireClientID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getRequireClientID() > 0)
			{
				ps.setLong(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getRequireClientID());
			}

			//appends nSignUserID to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getResignUserID() > 0)
			{
				ps.setLong(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getResignUserID());
			}

			//appends dtSign to the query where condition
			if (objQueryCondition_Sett_Others_DepositBankBill.getRegisterStart() != null)
			{
				ps.setTimestamp(iTag++, objQueryCondition_Sett_Others_DepositBankBill.getRegisterStart());
			}

			rs = ps.executeQuery();

			//get all the ResultSet elements
			while (rs.next())
			{
				objDepositRelatedBankBillInfo = new DepositRelatedBankBillInfo();
				objDepositRelatedBankBillInfo.setBillID(rs.getLong("nBillID"));
				objDepositRelatedBankBillInfo.setOldBillID(rs.getLong("nOldBillID"));
				objDepositRelatedBankBillInfo.setSignUserID(rs.getLong("nSignUserID"));
				objDepositRelatedBankBillInfo.setSignDate(rs.getTimestamp("dtSign"));
				objDepositRelatedBankBillInfo.setRequireClientNo(rs.getString("clientNo"));
				objDepositRelatedBankBillInfo.setRequireClientName(rs.getString("clientName"));
				objDepositRelatedBankBillInfo.setNewBillNo(rs.getString("sNewBillNo"));
				objDepositRelatedBankBillInfo.setRequireClientID(rs.getLong("clientID"));
				objDepositRelatedBankBillInfo.setType(rs.getLong("nTypeID"));
				objDepositRelatedBankBillInfo.setOldBillNo(getBillNoByID(rs.getLong("nOldBillID")));
				//append one BankBillInfo to the LinkedList object
				lList.add(objDepositRelatedBankBillInfo);
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

	/** get the sBillNo by ID
	 * @param the ID
	 * @return appoprivate sBillNo
	 * @exception
	 */
	private String getBillNoByID(long lID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		String strBillNo = "";
		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the insert sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select sBillNo as sOldBillNo from sett_BankBill where ID = ? ");

			ps = conn.prepareStatement(sbSQL.toString());

			//set the PreparedStatement arguments by the BankBillInfo object
			ps.setLong(1, lID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				strBillNo = rs.getString("sOldBillNo");
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
		return strBillNo;
	}

}
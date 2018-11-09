package com.iss.itreasury.settlement.bankbill.dao;

import java.sql.*;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bankbill.dataentity.*;
import com.iss.itreasury.util.Database;

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
 * Sett_BillTypeDAO.java
 * 票据类型的数据访问层
 * @author  Ryan
 * @version 1.0
*/

public class Sett_BillTypeDAO extends SettlementDAO
{
	/** get the appropriate BankBillTypeInfo object by id from the table sett_BankBillType
	 * @param the specified id
	 * @return the BankBillTypeInfo object
	 * @exception
	 */
	public BankBillTypeInfo findByID(long lID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		BankBillTypeInfo objBankBillTypeInfo = null;

		try
		{
			//get the connection from Database
			conn = getConnection();

			//establish the query sql string
			sbSQL = new StringBuffer();
			sbSQL.append(" select * from sett_BankBillType ");
			sbSQL.append(" where ID = ? ");

			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
			rs = ps.executeQuery();

			if (rs.next())
			{
				//get the BankBillTypeInfo from current ResultSet object
				objBankBillTypeInfo = new BankBillTypeInfo();
				objBankBillTypeInfo.setID(rs.getLong("ID"));
				objBankBillTypeInfo.setTypeDesc(rs.getString("sDesc"));
				objBankBillTypeInfo.setMaxLength(rs.getLong("nMaxLength"));
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
		return objBankBillTypeInfo != null ? objBankBillTypeInfo : null;
	}

}
package com.iss.itreasury.settlement.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.account.dataentity.CurrencySubjectInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2003-10-17
 */
public class Sett_CurrencySubjectDAO extends SettlementDAO
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);

	public Sett_CurrencySubjectDAO()
	{
		strTableName = "sett_currencysubject";
	}
	
	public Sett_CurrencySubjectDAO(Connection conn)
	{
		super(conn);
		strTableName = "sett_currencysubject";
	}	

	public String findSubjectCodeByTableNameAndAccoutTypeIDAndCurrencyIDAndOfficeID(String tableName, long accountTypeID, long CurrencyID, long OfficeID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String res = "";

		try
		{
			String strSQL =
				"SELECT SSUBJECT FROM "
					+ strTableName
					+ " WHERE STABLENAME = '"
					+ tableName
					+ "' AND NRECORDID = "
					+ accountTypeID
					+ " AND NCURRENCYID = "
					+ CurrencyID
					+ " AND NBACKOFFICEID = "
					+ OfficeID;
			log.info(strSQL);
			conn = this.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			rset = pstmt.executeQuery();

			if (rset.next())
			{
				res = rset.getString("SSUBJECT");
			}
            cleanup(rset);
            cleanup(pstmt);
            cleanup(conn);
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
            cleanup(rset);
            cleanup(pstmt);
            cleanup(conn);

		}
		return res;

	}
	
	public long add(CurrencySubjectInfo info)
	{
	    long lReturn = -1;
	    try {
            this.initDAO();
            String strSQL = "insert into "+ this.strTableName +" (sTableName,nRecordID,nCurrencyID,sSubject,nBackOfficeID,sInterestSubject,sNegotiateInterestSubject,sBookedInterestSubject) values (?,?,?,?,?,?,?,?)";
            transPS = prepareStatement(strSQL);
            transPS.setString(1,info.getTableName());
            transPS.setLong(2,info.getRecordID());
            transPS.setLong(3,info.getCurrencyID());
            transPS.setString(4,info.getSubject());
            transPS.setLong(5,info.getBackOfficeID());
            transPS.setString(6,info.getInterestSubject());
            transPS.setString(7,info.getNegotiateInterestSubject());
            transPS.setString(8,info.getBookedInterestSubject());
            
            lReturn = transPS.executeUpdate();
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
        return lReturn;
	}
	
	/**
	 * 物理删除账户类型与科目的对应
	 * @param info
	 * @return
	 */
	public long deletePhysically(CurrencySubjectInfo info)
	{
	    long tmpLong = -1;
	    try {
            this.initDAO();
            String strSQL = "delete from " + this.strTableName + " where sTableName=? and nRecordID=?";
            transPS = prepareStatement(strSQL);
            transPS.setString(1,info.getTableName());
            transPS.setLong(2,info.getRecordID());
            tmpLong = transPS.executeUpdate();
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
	    
	    return tmpLong;
	}
}

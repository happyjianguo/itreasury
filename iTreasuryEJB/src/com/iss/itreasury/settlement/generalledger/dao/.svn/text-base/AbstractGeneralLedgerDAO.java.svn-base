/*
 * Created on 2003-9-12
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.generalledger.dao;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import java.sql.*;

import com.iss.itreasury.util.*;
import org.apache.log4j.*;

/**
 * @author yqwu
 *
 */
public abstract class AbstractGeneralLedgerDAO extends SettlementDAO
{
    private static final String SQL_PREFIX = "SELECT MAX(ID) FROM ";
    private static Logger logger =
        Logger.getLogger(AbstractGeneralLedgerDAO.class);

    public String tableName="";

	public AbstractGeneralLedgerDAO(){
	}
	
	public AbstractGeneralLedgerDAO(Connection conn){
		super(conn);
	}	

	public AbstractGeneralLedgerDAO(Connection conn,boolean isSelfManagedConn){
		super(conn,isSelfManagedConn);
	}	
    /**
     * 得到一个新的记录id
     * @return 记录id
     * @throws SQLException
     */
    long getNextID() throws SQLException
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try
        {
            StringBuffer buffer = new StringBuffer(32);
            buffer.append(SQL_PREFIX);
            buffer.append(this.tableName);

            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            rset = pstmt.executeQuery();

            boolean hasResult = rset.next();

            if (hasResult)
            {
                lReturn = rset.getLong(1);
                lReturn++;
            }
            else
            {
                lReturn = 1;
            }

        }
        catch (SQLException sqle)
        {
            logger.error("得到一个新的记录id时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {
                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
				logger.error("关闭数据库连接时SQL异常", e);
				//throw new SQLException("Gen _E001");
				throw e;
            }

        }

        return lReturn;
    }
    
    
    /**
     * 得到一个新的记录id
     * @return 记录id
     * @throws SQLException
     */
    long getNextID2(Connection conn) throws SQLException
    {
        long lReturn = -1;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try
        {
        	
            StringBuffer buffer = new StringBuffer(32);
            buffer.append(SQL_PREFIX);
            buffer.append(this.tableName);

            pstmt = conn.prepareStatement(buffer.toString());
            
            rset = pstmt.executeQuery();

            boolean hasResult = rset.next();

            if (hasResult)
            {
                lReturn = rset.getLong(1);
                lReturn++;
            }
            else
            {
                lReturn = 1;
            }

        }
        catch (SQLException sqle)
        {
            logger.error("得到一个新的记录id时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {

            this.cleanup(rset);
            this.cleanup(pstmt);

        }

        return lReturn;
    }
}

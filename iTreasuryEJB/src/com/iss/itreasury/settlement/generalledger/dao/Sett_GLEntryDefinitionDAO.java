/*
 * Created on 2003-9-12
 * 
 */
package com.iss.itreasury.settlement.generalledger.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedger;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;

/**
 * 分录设置定义表DAO
 * @author yqwu
 *
 */
public class Sett_GLEntryDefinitionDAO extends AbstractGeneralLedgerDAO
{
    private static final String TABLE_NAME = "sett_GLEntryDefinition";
    private static Logger logger =
        Logger.getLogger(Sett_GLEntryDefinitionDAO.class);

    public Sett_GLEntryDefinitionDAO()
    {
        this.tableName = TABLE_NAME;
        this.strTableName = TABLE_NAME;
    }
    
	public Sett_GLEntryDefinitionDAO(Connection conn)
	{
		super(conn);
		this.tableName = TABLE_NAME;
		this.strTableName = TABLE_NAME;
	}    

	public Sett_GLEntryDefinitionDAO(Connection conn,boolean isSelfManagedConn)
	{
		super(conn,isSelfManagedConn);
		this.tableName = TABLE_NAME;
		this.strTableName = TABLE_NAME;
	}  
	
    /**
     * 新增分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return分录设置定义记录id
     * @throws SQLException
     */
    public long add( GLEntryDefinitionInfo info) throws SQLException
    {
        long lReturn = -1;

        try
        {

            StringBuffer buffer = new StringBuffer(64);
            buffer.append("insert into SETT_GLENTRYDEFINITION \n");
            buffer.append("(ID, \n");
            buffer.append("NOFFICEID,\n");
            buffer.append("NCURRENCYID,\n");
            buffer.append("NTRANSACTIONTYPE,\n ");
    		buffer.append("nSubTransactionType,\n ");        
            buffer.append("NCAPITALTYPE,\n");
            buffer.append("NENTRYTYPE,\n");
            buffer.append("NDIRECTION,\n");
            buffer.append("NSUBJECTTYPE,\n ");
            buffer.append("SSUBJECTCODE,\n");
            buffer.append("NAMOUNTDIRECTION,\n ");
            buffer.append("NAMOUNTTYPE,\n");
            buffer.append("NOFFICETYPE)\n");
            buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            

        	try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
			

        	long id = this.getNextID2(transConn);

            info.setID(id);

			transPS = transConn.prepareStatement(buffer.toString());

            int nIndex = 1;

            transPS.setLong(nIndex++, info.getID());
            transPS.setLong(nIndex++, info.getOfficeID());
            transPS.setLong(nIndex++, info.getCurrencyID());
            transPS.setLong(nIndex++, info.getTransactionType());
			transPS.setLong(nIndex++, info.getSubTransactionType());            
            transPS.setLong(nIndex++, info.getCapitalType());
            transPS.setLong(nIndex++, info.getEntryType());
            transPS.setLong(nIndex++, info.getDirection());
            transPS.setLong(nIndex++, info.getSubjectType());
            transPS.setString(nIndex++, info.getSubjectCode());
            transPS.setLong(nIndex++, info.getAmountDirection());
            transPS.setLong(nIndex++, info.getAmountType());
            transPS.setLong(nIndex++, info.getOfficeType());

            transPS.execute();

            lReturn = id;
        }
        catch (SQLException sqle)
        {
            logger.error("新增分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        catch (Exception e)
        {
            logger.error("新增分录设置定义记录时SQL异常", e);
            //throw new SQLException("Gen _E001");
            throw new SQLException(e.getMessage());
        }
        finally
        {
        	try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
        }

        return lReturn;
    }

    /**
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public long update(GLEntryDefinitionInfo info) throws SQLException
    {
        long lReturn = -1;



        try
        {

        	try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
			
	        StringBuffer buffer = new StringBuffer(64);
	        buffer.append("update SETT_GLENTRYDEFINITION set \n");
	        buffer.append("NOFFICEID=?,\n");
	        buffer.append("NCURRENCYID=?,\n");
	        buffer.append("NTRANSACTIONTYPE=?,\n ");
			buffer.append("nSubTransactionType=?,\n ");        
	        buffer.append("NCAPITALTYPE=?,\n");
	        buffer.append("NENTRYTYPE=?,\n");
	        buffer.append("NDIRECTION=?,\n");
	        buffer.append("NSUBJECTTYPE=?,\n ");
	        buffer.append("SSUBJECTCODE=?,\n");
	        buffer.append("NAMOUNTDIRECTION=?,\n ");
	        buffer.append("NAMOUNTTYPE=?, \n");
	        buffer.append("NOFFICETYPE=? \n");
	        buffer.append("where id=?\n");
	        
			transPS = transConn.prepareStatement(buffer.toString());

            int nIndex = 1;

            transPS.setLong(nIndex++, info.getOfficeID());
            transPS.setLong(nIndex++, info.getCurrencyID());
            transPS.setLong(nIndex++, info.getTransactionType());
			transPS.setLong(nIndex++, info.getSubTransactionType());            
            transPS.setLong(nIndex++, info.getCapitalType());
            transPS.setLong(nIndex++, info.getEntryType());
            transPS.setLong(nIndex++, info.getDirection());
            transPS.setLong(nIndex++, info.getSubjectType());
            transPS.setString(nIndex++, info.getSubjectCode());
            transPS.setLong(nIndex++, info.getAmountDirection());
            transPS.setLong(nIndex++, info.getAmountType());
            transPS.setLong(nIndex++, info.getOfficeType());
            transPS.setLong(nIndex++, info.getID());

            transPS.execute();

            lReturn = info.getID();

        }
        catch (SQLException sqle)
        {
            logger.error("更新分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
        	try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
        }

        return lReturn;
    }

    /**
     * 通过标识查询
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws SQLException
     */
    public GLEntryDefinitionInfo findByID(long id) throws SQLException
    {
        GLEntryDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append(
            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE, nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(
            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE \n");
        buffer.append("from SETT_GLENTRYDEFINITION where id=?");

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            pstmt.setLong(1, id);
            rset = pstmt.executeQuery();

            boolean hasResult = rset.next();

            if (hasResult)
            {
                info = new GLEntryDefinitionInfo();

                info.setID(id);
                info.setOfficeID(rset.getLong("NOFFICEID"));
                info.setCurrencyID(rset.getLong("NCURRENCYID"));
                info.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
				info.setSubTransactionType(rset.getLong("nSubTransactionType"));                
                info.setCapitalType(rset.getLong("NCAPITALTYPE"));
                info.setEntryType(rset.getLong("NENTRYTYPE"));
                info.setDirection(rset.getLong("NDIRECTION"));
                info.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
					info.setSubjectCode(subjectCode);
                }
                                
                info.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                info.setAmountType(rset.getLong("NAMOUNTTYPE"));
            }

        }
        catch (SQLException sqle)
        {
            logger.error("通过标识查询时SQL异常", sqle);
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

        return info;
    }

    /**
     * 查询所有分录设置定义表记录
     * @param officeID 机构ID 如果是-1忽略此条件
     * @param currencyID 币种 如果是-1忽略此条件
     * @param orderType 排序方式 如果是-1忽略此条件
     * @return 所有分录设置定义表记录集合
     * @throws SQLException
     */
    public Collection findAll(long officeID, long currencyID, long orderType)
        throws SQLException
    {
        Collection collection = new ArrayList(64);

        GLEntryDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append(
            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(
            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID \n");
        buffer.append("from SETT_GLENTRYDEFINITION ");

        boolean itemNotEmpty = false;

        if (officeID != -1)
        {
            itemNotEmpty = true;
            buffer.append(" where ");

            buffer.append(" nOfficeID=").append(officeID);
        }

        if (currencyID != -1)
        {
            if (itemNotEmpty)
            {
                buffer.append(" and ");
            }
            else
            {
                itemNotEmpty = true;
                buffer.append(" where ");
            }

            buffer.append(" nCurrencyID=").append(currencyID);
        }

        switch ((int) orderType)
        {
            case (GeneralLedger.TRANSACTION_TYPE) :
                buffer.append(" order by NTRANSACTIONTYPE");
                break;
            case (GeneralLedger.CAPITAL_TYPE) :
                buffer.append(" order by NCAPITALTYPE");
                break;
            case (GeneralLedger.ENTRY_TYPE) :
                buffer.append(" order by NENTRYTYPE");
                break;
            case (GeneralLedger.DIRECTION) :
                buffer.append(" order by NDIRECTION");
                break;
            case (GeneralLedger.SUBJECT_TYPE) :
                buffer.append(" order by NSUBJECTTYPE");
                break;
            case (GeneralLedger.SUBJECT_CODE) :
                buffer.append(" order by SSUBJECTCODE");
                break;
            case (GeneralLedger.AMOUNT_DIRECTION) :
                buffer.append(" order by NAMOUNTDIRECTION");
                break;
            case (GeneralLedger.AMOUNT_TYPE) :
                buffer.append(" order by NAMOUNTTYPE");
                break;

        }

        //sql较复杂，可打印到log中调试
        if (logger.isDebugEnabled())
        {
            logger.debug("\n查询所有分录设置定义表记录SQL语句:\n" + buffer.toString());
        }

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            rset = pstmt.executeQuery();

            while (rset.next())
            {
                info = new GLEntryDefinitionInfo();

                info.setOfficeID(rset.getLong("NOFFICEID"));
                info.setCurrencyID(rset.getLong("NCURRENCYID"));
                info.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
				info.setSubTransactionType(rset.getLong("nSubTransactionType"));                
                info.setCapitalType(rset.getLong("NCAPITALTYPE"));
                info.setEntryType(rset.getLong("NENTRYTYPE"));
                info.setDirection(rset.getLong("NDIRECTION"));
                info.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
					info.setSubjectCode(subjectCode);
                }
                                
                info.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                info.setAmountType(rset.getLong("NAMOUNTTYPE"));
                info.setOfficeType(rset.getLong("NOFFICETYPE"));
                info.setID(rset.getLong("ID"));

                collection.add(info);
            }
        }
        catch (SQLException sqle)
        {
            logger.error("查询所有分录设置定义表记录时SQL异常", sqle);
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

        return collection;
    }

   

    /**
      * 查询所有分录设置定义表记录
      * @param GLEntryDefinitionInfo info
      * @return 所有分录设置定义表记录集合
      * @throws SQLException
      */

    public Collection findAllByTransactionTypeIDAndEntryType(
        long lTransactionTypeID,
        long lEntryType,
        long lSubTransactionType,
        long lCurrencyID,
        long lOfficeID)
        throws SQLException
    {
        Collection collection = new ArrayList();

        GLEntryDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer();

        buffer.append(
            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(
            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID \n");
        buffer.append("from SETT_GLENTRYDEFINITION ");

        buffer.append(" where ");

        if (lEntryType == SETTConstant.EntryType.RECOIL) //反冲
        {
            buffer.append(" NTRANSACTIONTYPE =").append(lTransactionTypeID);
			buffer.append(" AND NENTRYTYPE = ").append(lEntryType);            
            buffer.append(" AND NOFFICEID = ").append(lOfficeID);
			buffer.append(" AND NCURRENCYID = ").append(lCurrencyID);
			buffer.append(" AND (nSubTransactionType = ").append(lSubTransactionType);
			buffer.append(" OR nSubTransactionType = ").append(0);
			buffer.append(" )");                        
        }
        else
        {
            buffer.append(" NTRANSACTIONTYPE =").append(lTransactionTypeID);
            buffer.append(" AND (NENTRYTYPE = ").append(lEntryType);
            buffer.append(" OR NENTRYTYPE = ").append(0);
            buffer.append(" )");
			buffer.append(" AND (nSubTransactionType = ").append(lSubTransactionType);
			buffer.append(" OR nSubTransactionType = ").append(0);
			buffer.append(" )");            
			buffer.append(" AND NOFFICEID = ").append(lOfficeID);
			buffer.append(" AND NCURRENCYID = ").append(lCurrencyID);            
        }

        try
        {
            log.info(buffer.toString());
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            rset = pstmt.executeQuery();

            while (rset.next())
            {
                info = new GLEntryDefinitionInfo();

                info.setOfficeID(rset.getLong("NOFFICEID"));
                info.setCurrencyID(rset.getLong("NCURRENCYID"));
                info.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
				info.setSubTransactionType(rset.getLong("nSubTransactionType"));
                info.setCapitalType(rset.getLong("NCAPITALTYPE"));
                info.setEntryType(rset.getLong("NENTRYTYPE"));
                info.setDirection(rset.getLong("NDIRECTION"));
                info.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                if(subjectCode!=null)
                {
					info.setSubjectCode(rset.getString("SSUBJECTCODE"));
                }
                                
                info.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                info.setAmountType(rset.getLong("NAMOUNTTYPE"));
                info.setOfficeType(rset.getLong("NOFFICETYPE"));
                info.setID(rset.getLong("ID"));

                collection.add(info);
            }
        }
        finally
        {
            this.cleanup(rset);
            this.cleanup(pstmt);
            this.cleanup(conn);

        }

        return collection;
    }
    public boolean batchCopy(long lOfficeIDSource, long lOfficeIDtarget) throws Exception
    {
    	boolean bRtn = true;
    	Collection dataSource=null;
    	StringBuffer sbSQL = null;
    	Connection conn=null;    	 
    	PreparedStatement ps=null;
    	try {
    		logger.info("进入批量复制会计分录设置");    		
    		dataSource=this.findAll(lOfficeIDSource, -1, GeneralLedger.TRANSACTION_TYPE);
    		if(dataSource!=null&&dataSource.size() > 0) {
    			conn=this.getConnection();
    			//取到当前表的最大ID
    			long lMaxId=this.getNextID();
    			if(lMaxId <= 0){
					throw new Exception("取最大ID出错");
				}
    			sbSQL=new StringBuffer();
    			sbSQL.append(" INSERT INTO "+strTableName);
				sbSQL.append(" (ID,NOFFICEID,NCURRENCYID,NTRANSACTIONTYPE,NCAPITALTYPE,NENTRYTYPE,NDIRECTION, ");
				sbSQL.append(" NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE, NSUBTRANSACTIONTYPE, NOFFICETYPE) ");
				sbSQL.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				logger.info("SQL = " + sbSQL.toString());
				ps=conn.prepareStatement(sbSQL.toString());
				for(Iterator it=dataSource.iterator();it.hasNext();){
					GLEntryDefinitionInfo toInfo=(GLEntryDefinitionInfo)it.next();
					ps.setLong(1, lMaxId++);
					ps.setLong(2, lOfficeIDtarget);
					ps.setLong(3, toInfo.getCurrencyID());
					ps.setLong(4, toInfo.getTransactionType());
					ps.setLong(5, toInfo.getCapitalType());
					ps.setLong(6, toInfo.getEntryType());
					ps.setLong(7, toInfo.getDirection());
					ps.setLong(8, toInfo.getSubjectType());
					ps.setString(9, toInfo.getSubjectCode());
					ps.setLong(10, toInfo.getAmountDirection());
					ps.setLong(11, toInfo.getAmountType());
					ps.setLong(12, toInfo.getSubTransactionType());
					ps.setLong(13, toInfo.getOfficeType());
					ps.addBatch();
				}
				int[] num=ps.executeBatch();
				if(num.length==0){
					bRtn=false;
				}    			
    		}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			bRtn = false;
			throw new Exception(" remote exception : "+ e.getMessage());
		}
		finally{			
            this.cleanup(ps);
            this.cleanup(conn);
		}
    	return bRtn;
    }
}

package com.iss.itreasury.settlement.generalledger.dao;

import java.rmi.RemoteException;
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
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.BaseObjectFactory;
import com.iss.system.dao.PageLoader;

public class Sett_GLEntryDefinitionTempDAO extends AbstractGeneralLedgerDAO {
    private static final String TABLE_NAME = "sett_GLEntryDefinition_temp";
    private static Logger logger =
        Logger.getLogger(Sett_GLEntryDefinitionDAO.class);

    public Sett_GLEntryDefinitionTempDAO()
    {
        this.tableName = TABLE_NAME;
        this.strTableName = TABLE_NAME;
    }
    
	public Sett_GLEntryDefinitionTempDAO(Connection conn)
	{
		super(conn);
		this.tableName = TABLE_NAME;
		this.strTableName = TABLE_NAME;
	}    

	public Sett_GLEntryDefinitionTempDAO(Connection conn,boolean isSelfManagedConn)
	{
		super(conn,isSelfManagedConn);
		this.tableName = TABLE_NAME;
		this.strTableName = TABLE_NAME;
	} 
    /**
     * 查询所有分录设置定义表记录
     * @param officeID 机构ID 如果是-1忽略此条件
     * @param currencyID 币种 如果是-1忽略此条件
     * @param orderType 排序方式 如果是-1忽略此条件
     * @return 所有分录设置定义表记录集合
     * @throws SQLException
     */
    public Collection findAllTemp(String strState,long officeID, long currencyID, long orderType)
        throws SQLException
    {
        Collection collection = new ArrayList(64);

        GLEntryDefinitionTempInfo tempInfo = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append(
            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(
            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID \n");
        buffer.append(
        " , NID ,INPUTUSERID,INPUTDATE,CHECKUSERID,CHECKDATE ,NOPERATETYPE ,NSTATUSID \n");
        buffer.append(
        " , (select count(*) from " + strTableName + " r where r.NTRANSACTIONTYPE = t.ntransactiontype and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = t. NSTATUSID ) rowspan \n");
        
        buffer.append("from " + strTableName + " t where 1 = 1  ");

        if(strState.trim().length()>0){
        	
        	buffer.append(" and  NSTATUSID in (").append(strState.trim()).append(")");
        }
        if (officeID != -1)
        {
            buffer.append(" and  nOfficeID=").append(officeID);
        }

        if (currencyID != -1)
        {
            buffer.append(" and  nCurrencyID=").append(currencyID);
        }

        buffer.append(" order by NTRANSACTIONTYPE ");
        
        switch ((int) orderType)
        {
            case (GeneralLedger.TRANSACTION_TYPE) :
                buffer.append(" ");
                break;
            case (GeneralLedger.CAPITAL_TYPE) :
                buffer.append(",NCAPITALTYPE");
                break;
            case (GeneralLedger.ENTRY_TYPE) :
                buffer.append(" ,NENTRYTYPE");
                break;
            case (GeneralLedger.DIRECTION) :
                buffer.append(" ,NDIRECTION");
                break;
            case (GeneralLedger.SUBJECT_TYPE) :
                buffer.append(" ,NSUBJECTTYPE");
                break;
            case (GeneralLedger.SUBJECT_CODE) :
                buffer.append(" ,SSUBJECTCODE");
                break;
            case (GeneralLedger.AMOUNT_DIRECTION) :
                buffer.append(" ,NAMOUNTDIRECTION");
                break;
            case (GeneralLedger.AMOUNT_TYPE) :
                buffer.append(" ,NAMOUNTTYPE");
                break;
            case (GeneralLedger.OPERATE_TYPE) :
                buffer.append(" ,NOPERATETYPE");
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
            	tempInfo = new GLEntryDefinitionTempInfo();

            	tempInfo.setOfficeID(rset.getLong("NOFFICEID"));
            	tempInfo.setCurrencyID(rset.getLong("NCURRENCYID"));
            	tempInfo.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
            	tempInfo.setSubTransactionType(rset.getLong("nSubTransactionType"));                
            	tempInfo.setCapitalType(rset.getLong("NCAPITALTYPE"));
            	tempInfo.setEntryType(rset.getLong("NENTRYTYPE"));
            	tempInfo.setDirection(rset.getLong("NDIRECTION"));
            	tempInfo.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                tempInfo.setAmountType(rset.getLong("NAMOUNTTYPE"));
                tempInfo.setOfficeType(rset.getLong("NOFFICETYPE"));
                tempInfo.setID(rset.getLong("ID"));
                tempInfo.setNID(rset.getLong("nID"));
                tempInfo.setNOperateType(rset.getLong("nOperateType"));
                tempInfo.setInputUserID(rset.getLong("inputUserID"));
                tempInfo.setInputDate(rset.getTimestamp("inputDate"));
                tempInfo.setCheckUserID(rset.getLong("checkUserID"));
                tempInfo.setCheckDate(rset.getTimestamp("checkDate"));
                tempInfo.setRowspan(rset.getLong("rowspan"));
                tempInfo.setNStatusID(rset.getLong("nStatusID"));
                
                collection.add(tempInfo);
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
     * @param officeID 机构ID 如果是-1忽略此条件
     * @param currencyID 币种 如果是-1忽略此条件
     * @param orderType 排序方式 如果是-1忽略此条件
     * @return 所有分录设置定义表记录集合
     * @throws SQLException
     */
    public Collection findAllUncheckAndUsedGLEntryDefinition(long officeID, long currencyID, long orderType)
        throws SQLException
    {
        Collection collection = new ArrayList(64);

        GLEntryDefinitionTempInfo tempInfo = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append(" select * from ( ");
        buffer.append(
        	"select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(
        	" NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID nID, -1 id,  2 nStatusID,0 NOPERATETYPE, \n");
        buffer.append(
    		" (select count(*) from SETT_GLENTRYDEFINITION r where r.NTRANSACTIONTYPE = t.ntransactiontype and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid  ) rowspan \n");
        buffer.append("from SETT_GLENTRYDEFINITION t \n");
    
        buffer.append(" union all  \n ");
    
        buffer.append(
            "select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(
            " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, nID,id,NSTATUSID, NOPERATETYPE, \n");
        buffer.append(
        " (select count(*) from " + strTableName + " r where r.NTRANSACTIONTYPE = t.ntransactiontype and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = t. NSTATUSID ) rowspan \n");
        
        buffer.append("from " + strTableName + " t where 1 = 1   \n ");

        buffer.append(" and  NSTATUSID = ").append(SETTConstant.GeneralLedgerStatus.UNCHECK);
        
        
        buffer.append(" ) tt where 1 = 1  ");
        
        if (officeID != -1)
        {
            buffer.append(" and  tt.nOfficeID=").append(officeID);
        }

        if (currencyID != -1)
        {
            buffer.append(" and  tt.nCurrencyID=").append(currencyID);
        }

        buffer.append(" order by NTRANSACTIONTYPE , NSTATUSID ");
        
        switch ((int) orderType)
        {
            case (GeneralLedger.TRANSACTION_TYPE) :
                buffer.append(" ");
                break;
            case (GeneralLedger.CAPITAL_TYPE) :
                buffer.append(",NCAPITALTYPE");
                break;
            case (GeneralLedger.ENTRY_TYPE) :
                buffer.append(" ,NENTRYTYPE");
                break;
            case (GeneralLedger.DIRECTION) :
                buffer.append(" ,NDIRECTION");
                break;
            case (GeneralLedger.SUBJECT_TYPE) :
                buffer.append(" ,NSUBJECTTYPE");
                break;
            case (GeneralLedger.SUBJECT_CODE) :
                buffer.append(" ,SSUBJECTCODE");
                break;
            case (GeneralLedger.AMOUNT_DIRECTION) :
                buffer.append(" ,NAMOUNTDIRECTION");
                break;
            case (GeneralLedger.AMOUNT_TYPE) :
                buffer.append(" ,NAMOUNTTYPE");
                break;
            case (GeneralLedger.OPERATE_TYPE) :
                buffer.append(" ,NOPERATETYPE");
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
            	tempInfo = new GLEntryDefinitionTempInfo();

            	tempInfo.setOfficeID(rset.getLong("NOFFICEID"));
            	tempInfo.setCurrencyID(rset.getLong("NCURRENCYID"));
            	tempInfo.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
            	tempInfo.setSubTransactionType(rset.getLong("nSubTransactionType"));                
            	tempInfo.setCapitalType(rset.getLong("NCAPITALTYPE"));
            	tempInfo.setEntryType(rset.getLong("NENTRYTYPE"));
            	tempInfo.setDirection(rset.getLong("NDIRECTION"));
            	tempInfo.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                tempInfo.setAmountType(rset.getLong("NAMOUNTTYPE"));
                tempInfo.setOfficeType(rset.getLong("NOFFICETYPE"));
                tempInfo.setID(rset.getLong("ID"));
                tempInfo.setNID(rset.getLong("nID"));
                tempInfo.setNOperateType(rset.getLong("nOperateType"));
                //tempInfo.setInputUserID(rset.getLong("inputUserID"));
                //tempInfo.setInputDate(rset.getTimestamp("inputDate"));
                //tempInfo.setCheckUserID(rset.getLong("checkUserID"));
                //tempInfo.setCheckDate(rset.getTimestamp("checkDate"));
                tempInfo.setRowspan(rset.getLong("rowspan"));
                tempInfo.setNStatusID(rset.getLong("nStatusID"));
                
                collection.add(tempInfo);
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
     * @param officeID 机构ID 如果是-1忽略此条件
     * @param currencyID 币种 如果是-1忽略此条件
     * @param orderType 排序方式 如果是-1忽略此条件
     * @return 所有分录设置定义表记录集合
     * @throws SQLException
     */
    public Collection findAllUnUseAndUsedGLEntryDefinition(long officeID, long currencyID, long orderType)
        throws SQLException
    {
        Collection collection = new ArrayList(64);

        GLEntryDefinitionTempInfo tempInfo = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append(" select * from ( ");
        
        buffer.append("select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
	    buffer.append(" NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, nID, id, noperatetype,nstatusid,inputUserID,  \n");
	    buffer.append(" ((select count(*) from Sett_Glentrydefinition r1 where r1.NTRANSACTIONTYPE = t.ntransactiontype and r1.nofficeid = t.nofficeid and r1.ncurrencyid = t.ncurrencyid)  \n");
	    buffer.append(" +(select count(*) from " + strTableName + " r2 where r2.NTRANSACTIONTYPE = t.ntransactiontype and r2.nofficeid = t.nofficeid and r2.ncurrencyid = t.ncurrencyid and r2.noperatetype = "+SETTConstant.GeneralLedgerOperationType.ADD+" and r2.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" ) ) rowspan  \n");
	    buffer.append(" from " + strTableName + "  t where t.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" \n");
    
	    buffer.append(" union \n");
	    
        buffer.append("select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
        buffer.append(" NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID nID, -1 id, -1 noperatetype, \n");
        buffer.append(" decode((select count(*) from " + strTableName + "  r  where r.NTRANSACTIONTYPE = t.ntransactiontype  and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = "+SETTConstant.GeneralLedgerStatus.UNCHECK+"),0,"+SETTConstant.GeneralLedgerStatus.CHECK+","+SETTConstant.GeneralLedgerStatus.UNCHECK+") nStatusID, \n");
        buffer.append(" decode((select count(*) from " + strTableName + "  r  where r.NTRANSACTIONTYPE = t.ntransactiontype  and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = "+SETTConstant.GeneralLedgerStatus.UNCHECK+"),0,-1,(select r.inputuserid from " + strTableName + "  r  where r.NTRANSACTIONTYPE = t.ntransactiontype  and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" and rownum = 1 )) inputUserID, \n");
	    buffer.append(" ((select count(*) from Sett_Glentrydefinition r1 where r1.NTRANSACTIONTYPE = t.ntransactiontype and r1.nofficeid = t.nofficeid and r1.ncurrencyid = t.ncurrencyid)  \n");
	    buffer.append(" +(select count(*) from " + strTableName + "  r2 where r2.NTRANSACTIONTYPE = t.ntransactiontype and r2.nofficeid = t.nofficeid and r2.ncurrencyid = t.ncurrencyid and r2.noperatetype = "+SETTConstant.GeneralLedgerOperationType.ADD+" and r2.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" ) ) rowspan  \n");
        buffer.append(" from SETT_GLENTRYDEFINITION t where t.id not in (select nID from " + strTableName + "  d where d.nstatusid = "+SETTConstant.GeneralLedgerStatus.UNCHECK+" and d.nofficeid = t.nOfficeID and d.ncurrencyid = t.ncurrencyid) \n");
        

        buffer.append(" ) tt where 1 = 1  ");
        
        if (officeID != -1)
        {
            buffer.append(" and  tt.nOfficeID=").append(officeID);
        }

        if (currencyID != -1)
        {
            buffer.append(" and  tt.nCurrencyID=").append(currencyID);
        }

        buffer.append(" order by NTRANSACTIONTYPE , NSTATUSID ");
        
        switch ((int) orderType)
        {
            case (GeneralLedger.TRANSACTION_TYPE) :
                buffer.append(" ");
                break;
            case (GeneralLedger.CAPITAL_TYPE) :
                buffer.append(",NCAPITALTYPE");
                break;
            case (GeneralLedger.ENTRY_TYPE) :
                buffer.append(" ,NENTRYTYPE");
                break;
            case (GeneralLedger.DIRECTION) :
                buffer.append(" ,NDIRECTION");
                break;
            case (GeneralLedger.SUBJECT_TYPE) :
                buffer.append(" ,NSUBJECTTYPE");
                break;
            case (GeneralLedger.SUBJECT_CODE) :
                buffer.append(" ,SSUBJECTCODE");
                break;
            case (GeneralLedger.AMOUNT_DIRECTION) :
                buffer.append(" ,NAMOUNTDIRECTION");
                break;
            case (GeneralLedger.AMOUNT_TYPE) :
                buffer.append(" ,NAMOUNTTYPE");
                break;
            case (GeneralLedger.OPERATE_TYPE) :
                buffer.append(" ,NOPERATETYPE");
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
            	tempInfo = new GLEntryDefinitionTempInfo();

            	tempInfo.setOfficeID(rset.getLong("NOFFICEID"));
            	tempInfo.setCurrencyID(rset.getLong("NCURRENCYID"));
            	tempInfo.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
            	tempInfo.setSubTransactionType(rset.getLong("nSubTransactionType"));                
            	tempInfo.setCapitalType(rset.getLong("NCAPITALTYPE"));
            	tempInfo.setEntryType(rset.getLong("NENTRYTYPE"));
            	tempInfo.setDirection(rset.getLong("NDIRECTION"));
            	tempInfo.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                tempInfo.setAmountType(rset.getLong("NAMOUNTTYPE"));
                tempInfo.setOfficeType(rset.getLong("NOFFICETYPE"));
                tempInfo.setID(rset.getLong("ID"));
                tempInfo.setNID(rset.getLong("nID"));
                tempInfo.setNOperateType(rset.getLong("nOperateType"));
                tempInfo.setInputUserID(rset.getLong("inputUserID"));
                //tempInfo.setInputDate(rset.getTimestamp("inputDate"));
                //tempInfo.setCheckUserID(rset.getLong("checkUserID"));
                //tempInfo.setCheckDate(rset.getTimestamp("checkDate"));
                tempInfo.setRowspan(rset.getLong("rowspan"));
                tempInfo.setNStatusID(rset.getLong("nStatusID"));
                
                collection.add(tempInfo);
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
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public Collection findUncheckGLEntryDefinitionTemp(String strTransactionType ,long OfficeID ,long currencyID) throws SQLException
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        Collection coll = new ArrayList();
        try
        {
            conn = this.getConnection();
            
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("select *  from   " + strTableName + "  \n");
            buffer.append(" where NTRANSACTIONTYPE in ("+strTransactionType+") \n");
            buffer.append(" and NSTATUSID =? \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;
			//pstmt.setString(nIndex++, strTransactionType);           
            pstmt.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            pstmt.setLong(nIndex++, OfficeID);
            pstmt.setLong(nIndex++, currencyID);
            rset = pstmt.executeQuery();
            
            while(rset.next()){
            	GLEntryDefinitionTempInfo info = new GLEntryDefinitionTempInfo();
            	
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
                info.setNID(rset.getLong("nID"));
                info.setNOperateType(rset.getLong("nOperateType"));
                info.setNStatusID(rset.getLong("nStatusID"));
                info.setInputUserID(rset.getLong("inputUserID"));
                info.setInputDate(rset.getTimestamp("inputDate"));
                info.setCheckUserID(rset.getLong("checkUserID"));
                info.setCheckDate(rset.getTimestamp("checkDate"));
            	
            	coll.add(info);
            }


        }
        catch (SQLException sqle)
        {
            logger.error("查找复核分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        
        finally
        {
            try
            {
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

        return coll;
    }
    
    
    /**
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public Collection findUncheckGLEntryDefinitionTempByIDs(String strIDs ,long OfficeID ,long currencyID) throws SQLException
    {
        long lReturn = -1;

        Collection coll = new ArrayList();
        try
        {

        	try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
			
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("select *  from   " + strTableName + "  \n");
            buffer.append(" where ID in ("+strIDs+") \n");
            buffer.append(" and NSTATUSID =? \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            
            transPS = transConn.prepareStatement(buffer.toString());

            int nIndex = 1;
			//transPS.setString(nIndex++, strTransactionType);           
            transPS.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            transPS.setLong(nIndex++, OfficeID);
            transPS.setLong(nIndex++, currencyID);
            transRS = transPS.executeQuery();
            
            while(transRS.next()){
            	GLEntryDefinitionTempInfo info = new GLEntryDefinitionTempInfo();
            	
                info.setOfficeID(transRS.getLong("NOFFICEID"));
                info.setCurrencyID(transRS.getLong("NCURRENCYID"));
                info.setTransactionType(transRS.getLong("NTRANSACTIONTYPE"));
				info.setSubTransactionType(transRS.getLong("nSubTransactionType"));                
                info.setCapitalType(transRS.getLong("NCAPITALTYPE"));
                info.setEntryType(transRS.getLong("NENTRYTYPE"));
                info.setDirection(transRS.getLong("NDIRECTION"));
                info.setSubjectType(transRS.getLong("NSUBJECTTYPE"));
                
                String subjectCode=transRS.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
					info.setSubjectCode(subjectCode);
                }
                                
                info.setAmountDirection(transRS.getLong("NAMOUNTDIRECTION"));
                info.setAmountType(transRS.getLong("NAMOUNTTYPE"));
                info.setOfficeType(transRS.getLong("NOFFICETYPE"));
                info.setID(transRS.getLong("ID"));
                info.setNID(transRS.getLong("nID"));
                info.setNOperateType(transRS.getLong("nOperateType"));
                info.setNStatusID(transRS.getLong("nStatusID"));
                info.setInputUserID(transRS.getLong("inputUserID"));
                info.setInputDate(transRS.getTimestamp("inputDate"));
                info.setCheckUserID(transRS.getLong("checkUserID"));
                info.setCheckDate(transRS.getTimestamp("checkDate"));
            	
            	coll.add(info);
            }


        }
        catch (SQLException sqle)
        {
            logger.error("查找复核分录设置定义记录时SQL异常", sqle);
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

        return coll;
    }
    
    /**
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public Collection findGLEntryDefinitionTempByCondition(GLEntryDefinitionTempInfo tempInfo) throws SQLException
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        Collection coll = new ArrayList();
        try
        {
            conn = this.getConnection();
            
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("select *  from   " + strTableName + "  where 1 = 1 \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            if(tempInfo.getTransactionType()>0){
            	buffer.append(" and NTRANSACTIONTYPE = ? \n");
            }
            if(tempInfo.getNStatusID()>0){
            	buffer.append(" and NSTATUSID =? \n");
            }
            if(tempInfo.getNID()>0){
            	buffer.append(" and NID =? \n");
            }
            if(tempInfo.getID()>0){
            	buffer.append(" and ID =? \n");
            }
           
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;
            
            pstmt.setLong(nIndex++, tempInfo.getOfficeID());
            pstmt.setLong(nIndex++, tempInfo.getCurrencyID());
            if(tempInfo.getTransactionType()>0){
            	pstmt.setLong(nIndex++, tempInfo.getTransactionType());
            }
            if(tempInfo.getNStatusID()>0){
            	pstmt.setLong(nIndex++, tempInfo.getNStatusID());
            }
            if(tempInfo.getNID()>0){
            	pstmt.setLong(nIndex++, tempInfo.getNID());
            }
            if(tempInfo.getID()>0){
            	pstmt.setLong(nIndex++, tempInfo.getID());
            }
            
            rset = pstmt.executeQuery();
            
            while(rset.next()){
            	GLEntryDefinitionTempInfo info = new GLEntryDefinitionTempInfo();
            	
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
                info.setNID(rset.getLong("nID"));
                info.setNOperateType(rset.getLong("nOperateType"));
                info.setNStatusID(rset.getLong("nStatusID"));
                info.setInputUserID(rset.getLong("inputUserID"));
                info.setInputDate(rset.getTimestamp("inputDate"));
                info.setCheckUserID(rset.getLong("checkUserID"));
                info.setCheckDate(rset.getTimestamp("checkDate"));
            	
            	coll.add(info);
            }


        }
        catch (SQLException sqle)
        {
            logger.error("查找复核分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        
        finally
        {
            try
            {
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

        return coll;
    }
    
    
    /**
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public GLEntryDefinitionTempInfo findUncheckGLEntryDefinitionByTransType(long nTransTypeID,long officeID, long currencyID) throws SQLException
    {
    	GLEntryDefinitionTempInfo resultInfo = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try
        {
            conn = this.getConnection();
            
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("select *  from   " + strTableName + "  where 1 = 1 \n");
        	buffer.append(" and NSTATUSID =? \n");
            buffer.append(" and NTRANSACTIONTYPE =? \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;
            pstmt.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            pstmt.setLong(nIndex++, nTransTypeID);
            pstmt.setLong(nIndex++, officeID);
            pstmt.setLong(nIndex++, currencyID);
            rset = pstmt.executeQuery();
            
            if(rset.next()){
            	resultInfo = new GLEntryDefinitionTempInfo();

            	//tempInfo.setID(id);
            	resultInfo.setOfficeID(rset.getLong("NOFFICEID"));
            	resultInfo.setCurrencyID(rset.getLong("NCURRENCYID"));
            	resultInfo.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
            	resultInfo.setSubTransactionType(rset.getLong("nSubTransactionType"));                
            	resultInfo.setCapitalType(rset.getLong("NCAPITALTYPE"));
            	resultInfo.setEntryType(rset.getLong("NENTRYTYPE"));
            	resultInfo.setDirection(rset.getLong("NDIRECTION"));
            	resultInfo.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	resultInfo.setSubjectCode(subjectCode);
                }
                                
                resultInfo.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                resultInfo.setAmountType(rset.getLong("NAMOUNTTYPE"));
                resultInfo.setID(rset.getLong("ID"));
                resultInfo.setNID(rset.getLong("nID"));
                resultInfo.setNOperateType(rset.getLong("nOperateType"));
                resultInfo.setNStatusID(rset.getLong("nStatusID"));
                resultInfo.setInputUserID(rset.getLong("inputUserID"));
                resultInfo.setInputDate(rset.getTimestamp("inputDate"));
                resultInfo.setCheckUserID(rset.getLong("checkUserID"));
                resultInfo.setCheckDate(rset.getTimestamp("checkDate"));
            }


        }
        catch (SQLException sqle)
        {
            logger.error("查找复核分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        
        finally
        {
            try
            {
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

        return resultInfo;
    }
    
    /**
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public Collection findUncheckGLEntryDefinitionsByTransType(long nTransTypeID,long officeID, long currencyID) throws SQLException
    {
    	Collection coll = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        try
        {
            conn = this.getConnection();
            
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("select *  from   " + strTableName + "  where 1 = 1 \n");
        	buffer.append(" and NSTATUSID =? \n");
            buffer.append(" and NTRANSACTIONTYPE =? \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;
            pstmt.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            pstmt.setLong(nIndex++, nTransTypeID);
            pstmt.setLong(nIndex++, officeID);
            pstmt.setLong(nIndex++, currencyID);
            rset = pstmt.executeQuery();
            
            coll = new ArrayList();
            while(rset.next()){
            	GLEntryDefinitionTempInfo resultInfo = new GLEntryDefinitionTempInfo();

            	//tempInfo.setID(id);
            	resultInfo.setOfficeID(rset.getLong("NOFFICEID"));
            	resultInfo.setCurrencyID(rset.getLong("NCURRENCYID"));
            	resultInfo.setTransactionType(rset.getLong("NTRANSACTIONTYPE"));
            	resultInfo.setSubTransactionType(rset.getLong("nSubTransactionType"));                
            	resultInfo.setCapitalType(rset.getLong("NCAPITALTYPE"));
            	resultInfo.setEntryType(rset.getLong("NENTRYTYPE"));
            	resultInfo.setDirection(rset.getLong("NDIRECTION"));
            	resultInfo.setSubjectType(rset.getLong("NSUBJECTTYPE"));
                
                String subjectCode=rset.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	resultInfo.setSubjectCode(subjectCode);
                }
                                
                resultInfo.setAmountDirection(rset.getLong("NAMOUNTDIRECTION"));
                resultInfo.setAmountType(rset.getLong("NAMOUNTTYPE"));
                resultInfo.setID(rset.getLong("ID"));
                resultInfo.setNID(rset.getLong("nID"));
                resultInfo.setNOperateType(rset.getLong("nOperateType"));
                resultInfo.setNStatusID(rset.getLong("nStatusID"));
                resultInfo.setInputUserID(rset.getLong("inputUserID"));
                resultInfo.setInputDate(rset.getTimestamp("inputDate"));
                resultInfo.setCheckUserID(rset.getLong("checkUserID"));
                resultInfo.setCheckDate(rset.getTimestamp("checkDate"));
                
                coll.add(resultInfo);
            }


        }
        catch (SQLException sqle)
        {
            logger.error("查找复核分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        
        finally
        {
            try
            {
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

        return coll;
    }
    /**
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public long checkGLEntryDefinitionTemp(String strTransactionType,long checkUserID,long officeID, long currencyID) throws SQLException
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try
        {
            conn = this.getConnection();
            
            StringBuffer buffer = new StringBuffer(64);
            buffer.append("update " + strTableName + " set \n");
            buffer.append(" NSTATUSID = ? ,\n");
            buffer.append("	CHECKUSERID=?,\n");
            buffer.append("	CHECKDATE = sysdate \n ");
            buffer.append(" where NTRANSACTIONTYPE in ("+strTransactionType+") \n");
            buffer.append(" and NSTATUSID =? \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;

            pstmt.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.CHECK);
            pstmt.setLong(nIndex++, checkUserID);
			//pstmt.setString(nIndex++, strTransactionType);           
            pstmt.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            pstmt.setLong(nIndex++, officeID);
            pstmt.setLong(nIndex++, currencyID);
            lReturn = pstmt.executeUpdate();

        }
        catch (SQLException sqle)
        {
            logger.error("复核分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {
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
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public long checkGLEntryDefinitionTempByIDs(String strIDs,long checkUserID,long officeID, long currencyID) throws SQLException
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
            buffer.append("update " + strTableName + " set \n");
            buffer.append(" NSTATUSID = ? ,\n");
            buffer.append("	CHECKUSERID=?,\n");
            buffer.append("	CHECKDATE = sysdate \n ");
            buffer.append(" where ID in ("+strIDs+") \n");
            buffer.append(" and NSTATUSID =? \n");
            buffer.append(" and NOFFICEID =? \n");
            buffer.append(" and NCURRENCYID =? \n");
            
            transPS = transConn.prepareStatement(buffer.toString());
            
            int nIndex = 1;

            transPS.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.CHECK);
            transPS.setLong(nIndex++, checkUserID);
			//transPS.setString(nIndex++, strTransactionType);           
            transPS.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            transPS.setLong(nIndex++, officeID);
            transPS.setLong(nIndex++, currencyID);
            lReturn = transPS.executeUpdate();

        }
        catch (SQLException sqle)
        {
            logger.error("复核分录设置定义记录时SQL异常", sqle);
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
     * 新增分录设置定义记录
     * @param info GLEntryDefinitionTempInfo
     * @return分录设置定义记录id
     * @throws SQLException
     */
    public long addTemp(GLEntryDefinitionTempInfo info) throws SQLException
    {
        long lReturn = -1;

        long id = this.getNextID();
        //info.setID(id);

        Connection conn = null;
        PreparedStatement pstmt = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("insert into " + strTableName + " \n");
        buffer.append("(ID, \n");
        buffer.append(" NID, \n");
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
        buffer.append("NOFFICETYPE,\n");
        buffer.append("NOPERATETYPE,\n");
        buffer.append("NSTATUSID,\n");
        buffer.append("INPUTUSERID,\n");
        buffer.append("INPUTDATE,\n");
        buffer.append("CHECKUSERID,\n");
        buffer.append("CHECKDATE)\n");
        buffer.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;

            pstmt.setLong(nIndex++, id);
            pstmt.setLong(nIndex++, info.getNID());
            pstmt.setLong(nIndex++, info.getOfficeID());
            pstmt.setLong(nIndex++, info.getCurrencyID());
            pstmt.setLong(nIndex++, info.getTransactionType());
			pstmt.setLong(nIndex++, info.getSubTransactionType());            
            pstmt.setLong(nIndex++, info.getCapitalType());
            pstmt.setLong(nIndex++, info.getEntryType());
            pstmt.setLong(nIndex++, info.getDirection());
            pstmt.setLong(nIndex++, info.getSubjectType());
            pstmt.setString(nIndex++, info.getSubjectCode());
            pstmt.setLong(nIndex++, info.getAmountDirection());
            pstmt.setLong(nIndex++, info.getAmountType());
            pstmt.setLong(nIndex++, info.getOfficeType());
            pstmt.setLong(nIndex++, info.getNOperateType());
            pstmt.setLong(nIndex++, info.getNStatusID());
            pstmt.setLong(nIndex++, info.getInputUserID());
            pstmt.setTimestamp(nIndex++, info.getInputDate());
            pstmt.setLong(nIndex++, info.getCheckUserID());
            pstmt.setTimestamp(nIndex++, info.getCheckDate());
            
            pstmt.execute();

            lReturn = id;
        }
        catch (SQLException sqle)
        {
            logger.error("新增分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {
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
     * 更新分录设置定义记录
     * @param info GLEntryDefinitionInfo
     * @return 分录设置定义记录id
     * @throws SQLException
     */
    public long updateTemp(GLEntryDefinitionTempInfo info) throws SQLException
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("update " + strTableName + " set \n");
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
        buffer.append("NOFFICETYPE=?, \n");
        buffer.append("NOPERATETYPE=?, \n");
        buffer.append("NSTATUSID=?, \n");
        buffer.append("INPUTUSERID=?, \n");
        buffer.append("INPUTDATE=? \n");
        buffer.append("where id =?  \n");
        ;

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());

            int nIndex = 1;

            pstmt.setLong(nIndex++, info.getOfficeID());
            pstmt.setLong(nIndex++, info.getCurrencyID());
            pstmt.setLong(nIndex++, info.getTransactionType());
			pstmt.setLong(nIndex++, info.getSubTransactionType());            
            pstmt.setLong(nIndex++, info.getCapitalType());
            pstmt.setLong(nIndex++, info.getEntryType());
            pstmt.setLong(nIndex++, info.getDirection());
            pstmt.setLong(nIndex++, info.getSubjectType());
            pstmt.setString(nIndex++, info.getSubjectCode());
            pstmt.setLong(nIndex++, info.getAmountDirection());
            pstmt.setLong(nIndex++, info.getAmountType());
            pstmt.setLong(nIndex++, info.getOfficeType());
            pstmt.setLong(nIndex++, info.getNOperateType());
            pstmt.setLong(nIndex++, info.getNStatusID());
            pstmt.setLong(nIndex++, info.getInputUserID());
            pstmt.setTimestamp(nIndex++, info.getInputDate());
            
            pstmt.setLong(nIndex++, info.getID());
            //pstmt.setLong(nIndex++, info.getNID());
            //pstmt.setLong(nIndex++, info.getNStatusID());
            
            pstmt.execute();

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
            try
            {
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
	 * 从数据库中根据id物理删除一条记录 使用时请确定是逻辑删除还是物理删除！！ 修改记录: 2008/03/26 mzh_fu 将关闭连接放到
	 * finally 中
	 */
	public void delUncheckTempInfoByNID(long nID,long officeID ,long currencyID) throws SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;
		try {

	        StringBuffer buffer = new StringBuffer(64);
	        buffer.append(" delete from " + strTableName + " where  nID = ? and NSTATUSID = ? and NOFFICEID=? and NCURRENCYID=? ");

            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
           
            int nIndex = 1;
            pstmt.setLong(nIndex++, nID);
            pstmt.setLong(nIndex++, SETTConstant.GeneralLedgerStatus.UNCHECK);
            pstmt.setLong(nIndex++, officeID);
            pstmt.setLong(nIndex++, currencyID);
            pstmt.execute();

		}catch (SQLException sqle)
        {
            logger.error("删除为复核分录设置定义记录时SQL异常", sqle);
            //throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {
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
	}
	
    /**
     * 通过标识查询
     * @param id long
     * @return GLEntryDefinitionInfo
     * @throws SQLException
     */
    public GLEntryDefinitionTempInfo findByTempID(long id) throws SQLException
    {
        GLEntryDefinitionTempInfo tempInfo = null;


        StringBuffer buffer = new StringBuffer(64);
        buffer.append(
        	"select NOFFICEID, NCURRENCYID, NTRANSACTIONTYPE,nSubTransactionType, NCAPITALTYPE, NENTRYTYPE,\n");
	    buffer.append(
	        " NDIRECTION, NSUBJECTTYPE, SSUBJECTCODE, NAMOUNTDIRECTION, NAMOUNTTYPE,NOFFICETYPE, ID \n");
	    buffer.append(
	    	" , NID ,INPUTUSERID,INPUTDATE,CHECKUSERID,CHECKDATE ,NOPERATETYPE ,NSTATUSID \n");

        buffer.append(" from " + strTableName + "  where id=? ");

        try
        {
        	try {
				initDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}

			transPS = transConn.prepareStatement(buffer.toString());
			transPS.setLong(1, id);
			transRS = transPS.executeQuery();

            boolean hasResult = transRS.next();

            if (hasResult)
            {
            	tempInfo = new GLEntryDefinitionTempInfo();

            	//tempInfo.setID(id);
            	tempInfo.setOfficeID(transRS.getLong("NOFFICEID"));
            	tempInfo.setCurrencyID(transRS.getLong("NCURRENCYID"));
            	tempInfo.setTransactionType(transRS.getLong("NTRANSACTIONTYPE"));
            	tempInfo.setSubTransactionType(transRS.getLong("nSubTransactionType"));                
            	tempInfo.setCapitalType(transRS.getLong("NCAPITALTYPE"));
            	tempInfo.setEntryType(transRS.getLong("NENTRYTYPE"));
            	tempInfo.setDirection(transRS.getLong("NDIRECTION"));
            	tempInfo.setSubjectType(transRS.getLong("NSUBJECTTYPE"));
                
                String subjectCode=transRS.getString("SSUBJECTCODE");
                
                if(subjectCode!=null)
                {
                	tempInfo.setSubjectCode(subjectCode);
                }
                                
                tempInfo.setAmountDirection(transRS.getLong("NAMOUNTDIRECTION"));
                tempInfo.setAmountType(transRS.getLong("NAMOUNTTYPE"));
                tempInfo.setID(transRS.getLong("ID"));
                tempInfo.setNID(transRS.getLong("nID"));
                tempInfo.setNOperateType(transRS.getLong("nOperateType"));
                tempInfo.setNStatusID(transRS.getLong("nStatusID"));
                tempInfo.setInputUserID(transRS.getLong("inputUserID"));
                tempInfo.setInputDate(transRS.getTimestamp("inputDate"));
                tempInfo.setCheckUserID(transRS.getLong("checkUserID"));
                tempInfo.setCheckDate(transRS.getTimestamp("checkDate"));
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
        	try {
				finalizeDAO();
			} catch (ITreasuryDAOException e) {
				e.printStackTrace();
				throw new SQLException(e.getMessage());
			}
        }

        return tempInfo;
    }
    
    
    /**
     * 查询所有分录设置定义表记录
     * @param officeID 机构ID 如果是-1忽略此条件
     * @param currencyID 币种 如果是-1忽略此条件
     * @param orderType 排序方式 如果是-1忽略此条件
     * @return 所有分录设置定义表记录集合
     * @throws SQLException
     */
    public PageLoader findAllPagerLoaderTemp(long nStatusID,long officeID, long currencyID, long orderType,String sort)
    throws RemoteException
    {
    	
		 PageLoader pageLoader =null;
		 StringBuffer sbSelect = new StringBuffer();
		 StringBuffer sbFrom = new StringBuffer();
		 StringBuffer sbWhere = new StringBuffer();
		 StringBuffer sbOrderby = new StringBuffer();
    	
		 try{
	        sbSelect.append(
	            " NOFFICEID OfficeID, NCURRENCYID CurrencyID, NTRANSACTIONTYPE TransactionType,nSubTransactionType SubTransactionType, NCAPITALTYPE CapitalType, NENTRYTYPE EntryType,\n");
	        sbSelect.append(
	            " NDIRECTION Direction, NSUBJECTTYPE SubjectType, SSUBJECTCODE SubjectCode, NAMOUNTDIRECTION AmountDirection, NAMOUNTTYPE AmountType,NOFFICETYPE OfficeType, ID \n");
	        sbSelect.append(
	        " , NID ,INPUTUSERID,INPUTDATE,CHECKUSERID,CHECKDATE ,NOPERATETYPE ,NSTATUSID \n");
	        sbSelect.append(
	        " , (select count(*) from " + strTableName + " r where r.NTRANSACTIONTYPE = t.ntransactiontype and r.nofficeid = t.nofficeid and r.ncurrencyid = t.ncurrencyid and r.NSTATUSID = t. NSTATUSID ) rowspan \n");
	        
	        sbFrom.append(" " + strTableName + " t  ");
	        
	        sbWhere.append(" 1 = 1 ");
	
	        if(nStatusID >0){
	        	
	        	sbWhere.append(" and  NSTATUSID = ").append(nStatusID);
	        }
	        if (officeID != -1)
	        {
	        	sbWhere.append(" and  nOfficeID=").append(officeID);
	        }
	
	        if (currencyID != -1)
	        {
	        	sbWhere.append(" and  nCurrencyID=").append(currencyID);
	        }
	        
	        if(nStatusID == SETTConstant.GeneralLedgerStatus.CHECK){
	        	
	        	if(orderType>0){
	        		
			        switch ((int) orderType)
			        {
			            case (GeneralLedger.TRANSACTION_TYPE) :
			            	sbOrderby.append(" order by  NTRANSACTIONTYPE  ").append(sort);
			                break;
			            case (GeneralLedger.CAPITAL_TYPE) :
			            	sbOrderby.append(" order by  NCAPITALTYPE ").append(sort);
			                break;
			            case (GeneralLedger.ENTRY_TYPE) :
			            	sbOrderby.append(" order by  NENTRYTYPE ").append(sort);
			                break;
			            case (GeneralLedger.DIRECTION) :
			            	sbOrderby.append(" order by  NDIRECTION ").append(sort);
			                break;
			            case (GeneralLedger.SUBJECT_TYPE) :
			            	sbOrderby.append(" order by  NSUBJECTTYPE ").append(sort);
			                break;
			            case (GeneralLedger.SUBJECT_CODE) :
			            	sbOrderby.append(" order by  SSUBJECTCODE ").append(sort);
			                break;
			            case (GeneralLedger.AMOUNT_DIRECTION) :
			            	sbOrderby.append(" order by  NAMOUNTDIRECTION ").append(sort);
			                break;
			            case (GeneralLedger.AMOUNT_TYPE) :
			            	sbOrderby.append(" order by  NAMOUNTTYPE ").append(sort);
			                break;
			            case (GeneralLedger.OPERATE_TYPE) :
			            	sbOrderby.append(" order by  NOPERATETYPE ").append(sort);
			                break;
			            case (GeneralLedger.INPUTDATE_TYPE) :
			            	sbOrderby.append(" order by  INPUTDATE ").append(sort);
			                break;
			            case (GeneralLedger.CHECKDATE_TYPE) :
			            	sbOrderby.append(" order by  CHECKDATE ").append(sort);
			                break;
			
			        }
	        	}else{
	        		
	        		sbOrderby.append("  order by  CHECKDATE desc " );
	        	}
	        	
	        }else {
	        	
	        	sbOrderby.append("  order by  NTRANSACTIONTYPE " );
	        	
		        switch ((int) orderType)
		        {
			        case (GeneralLedger.TRANSACTION_TYPE) :
		            	sbOrderby.append(" ").append(sort);
		                break;
		            case (GeneralLedger.CAPITAL_TYPE) :
		            	sbOrderby.append(",NCAPITALTYPE ").append(sort);
		                break;
		            case (GeneralLedger.ENTRY_TYPE) :
		            	sbOrderby.append(" ,NENTRYTYPE ").append(sort);
		                break;
		            case (GeneralLedger.DIRECTION) :
		            	sbOrderby.append(" ,NDIRECTION ").append(sort);
		                break;
		            case (GeneralLedger.SUBJECT_TYPE) :
		            	sbOrderby.append(" ,NSUBJECTTYPE ").append(sort);
		                break;
		            case (GeneralLedger.SUBJECT_CODE) :
		            	sbOrderby.append(" ,SSUBJECTCODE ").append(sort);
		                break;
		            case (GeneralLedger.AMOUNT_DIRECTION) :
		            	sbOrderby.append(" ,NAMOUNTDIRECTION ").append(sort);
		                break;
		            case (GeneralLedger.AMOUNT_TYPE) :
		            	sbOrderby.append(" ,NAMOUNTTYPE ").append(sort);
		                break;
		            case (GeneralLedger.OPERATE_TYPE) :
		            	sbOrderby.append(" ,NOPERATETYPE ").append(sort);
		                break;
		            case (GeneralLedger.INPUTDATE_TYPE) :
		            	sbOrderby.append(" ,INPUTDATE ").append(sort);
		                break;
		            case (GeneralLedger.CHECKDATE_TYPE) :
		            	sbOrderby.append(" ,CHECKDATE ").append(sort);
		                break;
		
		        }
	        }

	        
			 pageLoader = (PageLoader) BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			 pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.generalledger.dataentity.GLEntryDefinitionTempInfo",
				null);
			 pageLoader.setOrderBy(sbOrderby.toString());
		 
		 }
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
			return pageLoader;
    }

}

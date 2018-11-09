package com.iss.itreasury.treasuryplan.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;

/**
 *
 * @name: Trea_TPTemplateDAO
 * @description: 资金计划模板数据库访问类
 * @author: jason
 * @create: 2005-11-15
 *
 */
public class Trea_TPTemplateDAO extends TreasuryPlanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

    /**
     * Constructor without connection
     */
    public Trea_TPTemplateDAO()
    {
        super("Trea_TPTemplate");
        super.setUseMaxID();
    }

    /**
     * Constructor with a connection
     * @param conn
     */
    public Trea_TPTemplateDAO(Connection conn)
    {
        super("Trea_TPTemplate", conn);
		super.setUseMaxID();
    }
    
    /**
     *@Description: 查询资金计划模板数据
     *@param Trea_TPTemplateInfo
     *@return Collection
     *@throws Exception
     */
    public Vector queryTemplateData( Trea_TPTemplateInfo paramInfo) throws Exception
    {
        Vector dataVector    = new Vector();
        StringBuffer sbSQL   = new StringBuffer();
       
        PreparedStatement ps = null;
        ResultSet         rs = null;
        
        Trea_TPTemplateInfo resultInfo = null;
        
        try
        {
            //Init
            initDAO();
            
            
            sbSQL.append(" Select * From Trea_TPTemplate Where  1=1 \n");
            
            sbSQL.append(getSQLParameter(paramInfo).toString());
            
            sbSQL.append("\n   Order By lineNo ");
            
            ps = transConn.prepareStatement(sbSQL.toString());
            
            log4j.info(" 查询资金计划模板数据 ...............  \n"+sbSQL.toString());
            
            rs = ps.executeQuery();
            
            while( rs != null && rs.next() )
            {
                resultInfo = new Trea_TPTemplateInfo();
                
                resultInfo = transferToDataEntity(rs);
                
                dataVector.add(resultInfo);
            }
            
            //Finalize
            sbSQL.setLength(0);
            finalizeDAO();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        
        return dataVector;
    }
    
    /**
     *@Description:得到模板项目的最大级别
     *@param Trea_TPTemplateInfo
     *@return maxLevel
     *@throws Exception
     */
    public long getMaxLevel( Trea_TPTemplateInfo paramInfo ) throws Exception
    {
        long  maxLevel = -1;        
        
        PreparedStatement ps = null;
        ResultSet         rs = null;
        
        StringBuffer sbSQL   = new StringBuffer();
        
        Trea_TPTemplateInfo resultInfo = null;
        
        try
        {

            //Init
            initDAO();
            
            
            sbSQL.append(" Select max(lineLevel) maxLevel From Trea_TPTemplate Where  1=1 \n");
            
            sbSQL.append(getSQLParameter(paramInfo).toString());
            
            ps = transConn.prepareStatement(sbSQL.toString());
            
            log4j.info(" 得到模板项目的最大级别 ...............  \n"+sbSQL.toString());
            
            rs = ps.executeQuery();
            
            if( rs != null && rs.next() )
            {
                resultInfo = new Trea_TPTemplateInfo();
                
                maxLevel = rs.getLong("maxLevel");
                
            }
            
            //Finalize
            sbSQL.setLength(0);
            finalizeDAO();
            
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        
        return maxLevel;
    }
    
    /**
     *@Description: 拼写sql 语句的where 字句
     *@param Trea_TPTemplateInfo
     *@return StringBuffer
     *@throws Exception
     */
    private StringBuffer getSQLParameter( Trea_TPTemplateInfo paramInfo ) throws Exception
    {
        
        StringBuffer sbSQLParameter = new StringBuffer();
        
        try 
        {
            if( paramInfo.getOfficeId() > 0 )
            {
                sbSQLParameter.append("  AND  officeId = "+paramInfo.getOfficeId() + "  \n");
            }
            
            if( paramInfo.getCurrencyId() > 0 )
            {
                sbSQLParameter.append("  AND  currencyId = "+paramInfo.getCurrencyId() + "  \n");
            }
            
            /*if (paramInfo.getAuthorizedDepartment() != null && !paramInfo.getAuthorizedDepartment().equals("")) 
            {
                sbSQLParameter.append("  AND authorizedDepartment LIKE '%<"+paramInfo.getAuthorizedDepartment()+">%'  \n");
            } 
            else 
            {
                sbSQLParameter.append("  AND authorizedDepartment IS NULL  \n");
            }
            
            if (paramInfo.getAuthorizedUser() != null && !paramInfo.getAuthorizedUser().equals("")) 
            {
                sbSQLParameter.append("  AND authorizedUser LIKE '%<"+paramInfo.getAuthorizedUser()+">%' \n");
            } 
            else
            {
                sbSQLParameter.append("  AND authorizedUser IS NULL  \n");
            }*/
            
            sbSQLParameter.append("  AND  statusId = "+Constant.RecordStatus.VALID  +  "  \n");
        }
        catch ( RuntimeException e ) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return sbSQLParameter;
        
    }
    /**
     *@Description:Transfer ResultSet To DataEntity
     *@param ResultSet
     *@return Trea_TPTemplateInfo
     *@throws Exception
     */
    private Trea_TPTemplateInfo transferToDataEntity( ResultSet rs ) throws Exception
    {
        Trea_TPTemplateInfo info = null;
        
        try
        {
            info = new Trea_TPTemplateInfo();
            
            info.setId( rs.getLong("Id") );
            info.setOfficeId( rs.getLong("OfficeId") );
            info.setCurrencyId( rs.getLong("CurrencyId") );
            info.setLineLevel( rs.getLong("LineLevel") );
            info.setLineName( rs.getString("LineName") );
            info.setLineNo( rs.getString("LineNo") );
            info.setParentLineId( rs.getLong("ParentLineId") );
            info.setIsLeaf( rs.getLong("IsLeaf") );
            info.setIsReadonly( rs.getLong("IsReadonly") );
            info.setIsNeedSum(rs.getLong("IsNeedSum"));
            info.setAuthorizedDepartment( rs.getString("AuthorizedDepartment") );
            info.setAuthorizedUser( rs.getString("AuthorizedUser") );
            info.setMaintenanceFlag( rs.getLong("MaintenanceFlag") );
            info.setStatusId( rs.getLong("StatusId") );
            info.setInputUserId( rs.getLong("InputUserId") );
            info.setInputDate( rs.getTimestamp("InputDate") );
            info.setUpdateUserId( rs.getLong("UpdateUserId") );
            info.setUpdateDate( rs.getTimestamp("UpdateDate") );
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        return info;
    }
    
    /**
     *@Description:判断是否还有下级项目
     *@param Trea_TPTemplateInfo
     *@return boolean
     *              true :当前行项目还有下级项目
     *              false:当前行项目已经是叶子项目
     *@throws Exception
     */
    public boolean isHaveSubItem( Trea_TPTemplateInfo info ) throws Exception
    {
        boolean         flag = false;
        PreparedStatement ps = null;
        ResultSet         rs = null;
        StringBuffer   sbSQL = new StringBuffer();
        
        try
        {

            log4j.info(" IN Method :isHaveSubItem() ...............  \n");
            
            //Init
            initDAO();
            
            sbSQL.append(" Select * From Trea_TPTemplate Where  1=1 \n");
            sbSQL.append("     And  parentLineId  =  "+info.getId());
            sbSQL.append("\n   And  lineLevel  >  "+info.getLineLevel());
            sbSQL.append("\n   And  officeId   =  "+info.getOfficeId());
            sbSQL.append("\n   And  currencyId =  "+info.getCurrencyId());
            sbSQL.append("\n   And  statusId   =  "+Constant.RecordStatus.VALID);
            
            ps = transConn.prepareStatement(sbSQL.toString());
            
            log4j.info(" 判断是否还有下级项目 ...............  \n"+sbSQL.toString());
            
            rs = ps.executeQuery();
            
            if( rs != null && rs.next() )
            {
                flag = true;
            }
            
            //Finalize
            sbSQL.setLength(0);
            finalizeDAO();
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception(" 判断是否还有下级项目发生异常！");
        }
        
        return flag;
    }
    
}
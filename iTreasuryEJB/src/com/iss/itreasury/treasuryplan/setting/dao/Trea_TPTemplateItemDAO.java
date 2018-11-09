package com.iss.itreasury.treasuryplan.setting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateItemInfo;
import com.iss.itreasury.treasuryplan.util.TreasuryPlanDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

/**
 *
 * @name: Trea_TPTemplateDAO
 * @description: 资金计划模板数据库访问类
 * @author: jason
 * @create: 2005-11-15
 *
 */
public class Trea_TPTemplateItemDAO extends TreasuryPlanDAO
{
    protected Log4j log4j = new Log4j(Constant.ModuleType.PLAN, this);

    /**
     * Constructor without connection
     */
    public Trea_TPTemplateItemDAO()
    {
        super("Trea_TPTemplateItem");
        super.setUseMaxID();
    }

    /**
     * Constructor with a connection
     * @param conn
     */
    public Trea_TPTemplateItemDAO(Connection conn)
    {
        super("Trea_TPTemplateItem", conn);
		super.setUseMaxID();
    }
    
    
    /**
     *@Description: 删除一条取数逻辑（物理删除）
     *void
     *@param itemId
     *@throws Exception
     */
    public void deleteOneItem ( long itemId ) throws Exception
    {
        log4j.info(" 删除一条取数逻辑（物理删除）.....................................");
        PreparedStatement ps = null;
        String strSQL = "";
        try
        {
            initDAO();
            
            strSQL   = " delete from Trea_TPTemplateItem ";
            strSQL  += " where Id = "+itemId;
            log4j.info(" strSQL = "+strSQL);
            
            ps = transConn.prepareStatement(strSQL);
            
            ps.execute();
           
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            finalizeDAO();
            if (ps != null) 
            {
                ps.close();
                ps = null;
            }
        }
    }
    
    
    /**
     *@Description:修改取数逻辑
     *@param Trea_TPTemplateItemInfo
     *@return long
     *@throws Exception
     */
    public long updateItem(Trea_TPTemplateItemInfo itemInfo) throws Exception
    {
        PreparedStatement ps = null;
        StringBuffer sbSQL   = null;
        try
        {
            initDAO();
            
            sbSQL = new StringBuffer();
            sbSQL.append(" update trea_tptemplateitem set lineId = " + itemInfo.getLineId()+"  \n");
            sbSQL.append(" , moduleId        = "+itemInfo.getModuleId()+"  \n");
            sbSQL.append(" , clientCode      = '"+DataFormat.formatString(itemInfo.getClientCode())+"'  \n");
            sbSQL.append(" , accountCode     = '"+DataFormat.formatString(itemInfo.getAccountCode())+"'  \n");
            sbSQL.append(" , contractCode    = '"+DataFormat.formatString(itemInfo.getContractCode())+"'  \n");
            sbSQL.append(" , payformCode     = '"+DataFormat.formatString(itemInfo.getPayFormCode())+"'  \n");
            sbSQL.append(" , counterpartName = '"+DataFormat.formatString(itemInfo.getCounterPartName())+"'  \n");
            sbSQL.append(" , securitiesName  = '"+DataFormat.formatString(itemInfo.getSecuritiesName())+"'  \n");
            sbSQL.append(" , accountTypeId   = "+itemInfo.getAccountTypeId()+"  \n");
            sbSQL.append(" , glsubjectCode   = '"+DataFormat.formatString(itemInfo.getGlSubjectCode())+"'  \n");
            sbSQL.append(" , amountDirection = "+itemInfo.getAmountDirection()+"  \n");
            sbSQL.append(" , parameter       = '"+DataFormat.formatString(itemInfo.getParameter())+"'  \n");
            sbSQL.append(" , amountFlag      = "+itemInfo.getAmountFlag()+"  \n");
            sbSQL.append(" , calculateFlag   = '"+DataFormat.formatString(itemInfo.getCalculateFlag())+"'  \n");
            sbSQL.append(" where ID = "+itemInfo.getId()+"  \n");
            
            log4j.info(sbSQL.toString());
            
            ps = transConn.prepareStatement(sbSQL.toString());
           
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            finalizeDAO();
            if (ps != null) 
            {
                ps.close();
                ps = null;
            }
        }
        return itemInfo.getId();
    }
    
    /**
     *@Description:修改取数逻辑,根据行项目的项目类型进行修改
     *@param Trea_TPTemplateItemInfo
     *@return long
     *@throws Exception
     */
    public void updateItemByMaintenanceFlag(long lLineId, long lAmountFlag) throws Exception
    {
        PreparedStatement ps = null;
        StringBuffer sbSQL   = null;
        try
        {
            initDAO();
            
            sbSQL = new StringBuffer();
            sbSQL.append(" update trea_tptemplateitem set amountFlag = "+lAmountFlag+"  \n");
            sbSQL.append(" where lineId = "+lLineId+"  \n");
            
            log4j.info(sbSQL.toString());
            
            ps = transConn.prepareStatement(sbSQL.toString());
           
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (ps != null) 
            {
                ps.close();
                ps = null;
            }
        }
    }
}
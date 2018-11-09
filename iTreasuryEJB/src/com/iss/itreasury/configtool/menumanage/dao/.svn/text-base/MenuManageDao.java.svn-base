/*
 * Created on 2005-3-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.menumanage.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.configtool.menumanage.dataentity.PrivilegeInfo;
import com.iss.itreasury.util.Constant;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuManageDao extends ITreasuryDAO{
	
	public MenuManageDao()
    { 
        super("sys_privilege");
    } 

    public MenuManageDao(String tableName)
    {
        super(tableName);
    }

    public MenuManageDao(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public MenuManageDao(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public MenuManageDao(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public MenuManageDao(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }

    /**
     * 根据模块ID查询所有有效权限
     * 
     * @param moduleId
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesbyModuleId(long moduleId) throws SQLException, ITreasuryDAOException
    {
        ArrayList co = new ArrayList();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT * FROM sys_privilege WHERE moduleId=");
            sb.append(moduleId);
            sb.append(" AND plevel<>999 AND statusId=");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" ORDER BY privilegeNo ");
            transPS = transConn.prepareStatement(sb.toString());
            PrivilegeInfo privilege;
            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                privilege = new PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));
                co.add(privilege);
            }
            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return co.size() > 0 ? co : null;
    }
    
    /**
     * 根据模块ID查询所有无效权限
     * 
     * @param moduleId
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findInvalidPrivilegesbyModuleId(long moduleId) throws SQLException, ITreasuryDAOException
    {
        ArrayList co = new ArrayList();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT * FROM sys_privilege WHERE moduleId=");
            sb.append(moduleId);
            sb.append(" AND plevel<>999 AND statusId=");
            sb.append(Constant.RecordStatus.INVALID);
            sb.append(" ORDER BY privilegeNo ");
            transPS = transConn.prepareStatement(sb.toString());
            PrivilegeInfo privilege;
            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                privilege = new PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));
                co.add(privilege);
            }
            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return co.size() > 0 ? co : null;
    }
    /**
     * 根据模块ID办事处ID查询所有有效权限
     * 
     * @param moduleId,
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesbyModuleIdOfficeId(long moduleId,long OfficeId) throws SQLException, ITreasuryDAOException
    {
        ArrayList co = new ArrayList();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT * FROM sys_privilege WHERE moduleId=");
            sb.append(moduleId);
            sb.append(" AND officeid=");
            sb.append(OfficeId);
            sb.append(" AND plevel<>999 AND statusId=");
            sb.append(Constant.RecordStatus.VALID);
            sb.append(" ORDER BY privilegeNo ");
            transPS = transConn.prepareStatement(sb.toString());
            PrivilegeInfo privilege;
            transRS = transPS.executeQuery();
            while (transRS.next())
            {
                /*privilege = new PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));*/
                co.add(transRS.getString("id"));
            }
            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return co.size() > 0 ? co : null;
    }
    /**
     * 根据模块ID办事处ID查询所有权限
     * 
     * @param moduleId,
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findAllPrivilegesbyModuleIdOfficeId(long moduleId,long OfficeId) throws SQLException, ITreasuryDAOException
    {
        ArrayList co = new ArrayList();
        try
        {
            initDAO();
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT * FROM sys_privilege WHERE moduleId=");
            sb.append(moduleId);
            sb.append(" AND officeid=");
            sb.append(OfficeId);
            sb.append(" AND plevel<>999");
            //sb.append(Constant.RecordStatus.INVALID);
            sb.append(" ORDER BY privilegeNo ");
            transPS = transConn.prepareStatement(sb.toString());
            PrivilegeInfo privilege;
            transRS = transPS.executeQuery();
            
            while (transRS.next())
            {
                privilege = new PrivilegeInfo();
                privilege.setId(transRS.getLong("id"));
                privilege.setMenuUrl(transRS.getString("menuUrl"));
                privilege.setName(transRS.getString("name"));
                privilege.setOfficeID(transRS.getLong("officeId"));
                privilege.setPlevel(transRS.getLong("plevel"));
                privilege.setPrivilegeNo(transRS.getString("privilegeNo"));
                co.add(privilege);
            }
            finalizeDAO();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
        return co.size() > 0 ? co : null;
    } 

}

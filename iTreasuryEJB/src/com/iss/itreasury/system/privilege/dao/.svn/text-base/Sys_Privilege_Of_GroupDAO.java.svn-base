/**
 * 用户组权限关系数据访问层类实体
 * 
 * @author jinchen
 */

package com.iss.itreasury.system.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.privilege.dataentity.Sys_Privilege_Of_GroupInfo;
import java.sql.*;
import java.util.*;

public class Sys_Privilege_Of_GroupDAO extends ITreasuryDAO
{

    public Sys_Privilege_Of_GroupDAO()
    {
        super("sys_privilege_of_group");
    }

    public Sys_Privilege_Of_GroupDAO(String tableName)
    {
        super(tableName);
    }

    public Sys_Privilege_Of_GroupDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public Sys_Privilege_Of_GroupDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public Sys_Privilege_Of_GroupDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public Sys_Privilege_Of_GroupDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }

    /**
     * 删除某用户组对应的权限关系
     * 
     * @param groupId
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void del(long groupId) throws ITreasuryDAOException, SQLException
    {
        try
        {
            initDAO();
            StringBuffer sqlstr = new StringBuffer();
            sqlstr.append("delete sys_privilege_of_group where groupId=" + groupId);
            transPS = transConn.prepareStatement(sqlstr.toString());
            transPS.executeUpdate();
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
    }

    /**
     * 查询某一个用户组的所有权限
     * 
     * @param groupId
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findPrivilegeByGroupId(long groupId) throws ITreasuryDAOException
    {
        Vector v = new Vector();
        Sys_Privilege_Of_GroupInfo condition = new Sys_Privilege_Of_GroupInfo();
        condition.setGroupID(groupId);
        Collection c = findByCondition(condition);
        for (Iterator iterator = c.iterator(); iterator.hasNext(); v.add((new StringBuffer(String.valueOf(((Sys_Privilege_Of_GroupInfo) iterator.next()).getPrivilegeID()))).toString()))
            ;
        return v;
    }

}
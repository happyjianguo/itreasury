/**
 * 用户用户组关系数据访问层类
 * @author jinchen
 */

package com.iss.itreasury.system.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.privilege.dataentity.Sys_Group_Of_UserInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_Privilege_Of_GroupInfo;
import java.sql.*;
import java.util.*;

public class Sys_Group_Of_UserDAO extends ITreasuryDAO
{

    public Sys_Group_Of_UserDAO()
    {
        super("Sys_Group_of_user");
    }

    public Sys_Group_Of_UserDAO(String tableName)
    {
        super(tableName);
    }

    public Sys_Group_Of_UserDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public Sys_Group_Of_UserDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public Sys_Group_Of_UserDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public Sys_Group_Of_UserDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }
    /**
     * 删除某一个用户对应的用户组关系
     * @param userId
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void del(long userId)
        throws ITreasuryDAOException, SQLException
    {
        try
        {
	        initDAO();
	        StringBuffer sqlstr = new StringBuffer();
	        sqlstr.append("delete sys_group_of_user where userId=" + userId);
	        transPS = transConn.prepareStatement(sqlstr.toString());
	        transPS.executeQuery();
	        finalizeDAO();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            finalizeDAO();
        }
        finally
        {
            finalizeDAO();
        }
    }
    
    public void del(long userId,long lofficeId)throws ITreasuryDAOException, SQLException
	{
	    try
	    {
	        initDAO();
	        StringBuffer sqlstr = new StringBuffer();
	        sqlstr.append("delete sys_group_of_user where userId=" + userId);
	        if(lofficeId>0)
	        {
	        	sqlstr.append(" and officeid ="+lofficeId);
	        }
	        transPS = transConn.prepareStatement(sqlstr.toString());
	        transPS.executeQuery();
	        finalizeDAO();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        finalizeDAO();
	    }
	    finally
	    {
	        finalizeDAO();
	    }
	}

    public Collection findUserByGroupId(long groupId)
        throws ITreasuryDAOException
    {
        Sys_Group_Of_UserInfo condition = new Sys_Group_Of_UserInfo();
        condition.setGroupID(groupId);
        return findByCondition(condition);
    }

    public Collection findPrivilegeByGroupId(long groupId)
        throws ITreasuryDAOException
    {
        Sys_Privilege_Of_GroupInfo condition = new Sys_Privilege_Of_GroupInfo();
        condition.setGroupID(groupId);
        return findByCondition(condition);
    }

    public Collection findGroupByUserId(long userID,long lOfficeID)
        throws ITreasuryDAOException
    {
        Sys_Group_Of_UserInfo condition = new Sys_Group_Of_UserInfo();
        condition.setUserID(userID);
        condition.setOfficeID(lOfficeID);
        Vector v = new Vector();
        Collection c = findByCondition(condition);
        for(Iterator iterator = c.iterator(); iterator.hasNext(); v.add((new StringBuffer(String.valueOf(((Sys_Group_Of_UserInfo)iterator.next()).getGroupID()))).toString()));
        return v;
    }
}

/**
 * 用户用户组关系数据访问层类
 * @author jinchen
 */

package com.iss.itreasury.ebank.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dataentity.OB_Group_Of_UserInfo;
import com.iss.itreasury.ebank.privilege.dataentity.OB_Privilege_Of_GroupInfo;
import com.iss.itreasury.util.IException;

import java.sql.*;
import java.util.*;

public class OB_Group_Of_UserDAO extends ITreasuryDAO
{

    public OB_Group_Of_UserDAO()
    {
        super("OB_Group_of_user");
    }
    
    public OB_Group_Of_UserDAO(Connection conn)
    {
        super("OB_Group_of_user", conn);
    }

    public OB_Group_Of_UserDAO(String tableName)
    {
        super(tableName);
    }

    public OB_Group_Of_UserDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public OB_Group_Of_UserDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public OB_Group_Of_UserDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public OB_Group_Of_UserDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }
    /**
     * 删除某一个用户对应的用户组关系
     * @param userId
     * @throws SQLException
     * @throws IException
     */
    public void del(long userId)
        throws  IException
    {
        try
        {
        initDAO();
        StringBuffer sqlstr = new StringBuffer();
        sqlstr.append("delete OB_group_of_user where userId=" + userId);
        transPS = transConn.prepareStatement(sqlstr.toString());
        transPS.executeQuery();
        finalizeDAO();
        }catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("删除用户用户组关系发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("删除用户用户组关系发生数据库操作异常",e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
    }

    public Collection findUserByGroupId(long groupId)
        throws IException
    {
        Collection c = null;
        try
        {
        initDAO();
        OB_Group_Of_UserInfo condition = new OB_Group_Of_UserInfo();
        condition.setGroupID(groupId);
        c = this.findByCondition(condition);
        finalizeDAO();
        }catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户发生错误",e);
            //throw new IException(null,e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        return c;
        
    }

    public Collection findPrivilegeByGroupId(long groupId)
        throws IException
    {
        Collection c = null;
        try
        {
        initDAO();
        OB_Privilege_Of_GroupInfo condition = new OB_Privilege_Of_GroupInfo();
        condition.setGroupID(groupId);
        c = this.findByCondition(condition);
        finalizeDAO();
        }catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("根据用户组查找权限发生错误",e);
            //throw new IException(null,e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        return c;
    }

    public Collection findGroupByUserId(long userID)
        throws IException
    {
        Vector v = new Vector();
        try
        {
        this.initDAO();
        OB_Group_Of_UserInfo condition = new OB_Group_Of_UserInfo();
        condition.setUserID(userID);
        
        Collection c = findByCondition(condition);
        for(Iterator iterator = c.iterator(); iterator.hasNext(); v.add((new StringBuffer(String.valueOf(((OB_Group_Of_UserInfo)iterator.next()).getGroupID()))).toString()));
        this.finalizeDAO();
        }catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("根据用户ID查找用户组发生错误",e);
            //throw new IException(null,e);
        }
        catch(Exception e)
         {
             e.printStackTrace();
             throw new IException("其他异常",e);
         }
        finally
        {
          
                this.finalizeDAO();
           
        }
        
        return v.size()>0 ? v : null;
    }
    
    public boolean findSameGroupOfUser(OB_Group_Of_UserInfo group_userCondition)throws Exception
    {
    	boolean isSame = false;
    	try
    	{
    		StringBuffer sql = new StringBuffer();
    		this.initDAO();
    		sql.append(" select * from OB_Group_Of_User ");
    		sql.append(" where 2>1 ");
    		if(group_userCondition.getUserID()>-1)
    		{
    			sql.append(" and userid ="+group_userCondition.getUserID());
    		}
    		if(group_userCondition.getGroupID()>-1)
    		{
    			sql.append(" and groupid ="+group_userCondition.getGroupID());
    		}
    		if(group_userCondition.getClientID()>-1)
    		{
    			sql.append(" and clientid ="+group_userCondition.getClientID());
    		}
            transPS = transConn.prepareStatement(sql.toString());
            transRS = transPS.executeQuery();
            while(transRS.next())
            {
            	isSame = true;
            }
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		this.finalizeDAO();
    	}
    	return isSame;
    }
}

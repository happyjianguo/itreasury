/**
 * 用户组数据访问层类实体
 * 
 * @author jinchen
 */

package com.iss.itreasury.system.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.util.PrivilegeConstant;
import com.iss.itreasury.system.util.SYSConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class Sys_GroupDAO extends ITreasuryDAO
{

    public Sys_GroupDAO()
    {
        super("sys_group");
    }

    public Sys_GroupDAO(String tableName)
    {
        super(tableName);
    }

    public Sys_GroupDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public Sys_GroupDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public Sys_GroupDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public Sys_GroupDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }

    /**
     * 根据用户组ID删除一条用户组记录
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
            sqlstr.append("delete sys_group where id=" + groupId);
            transPS = transConn.prepareStatement(sqlstr.toString());
            transPS.executeQuery();
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
     * 根据用户组名查询用户组
     * 
     * @param groupName
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findGroupByName(String groupName) throws ITreasuryDAOException
    {
        Sys_GroupInfo condition = new Sys_GroupInfo();
        condition.setName(groupName);
        return findByCondition(condition);
    }
    /**
     * 根据用户组名和officeid查询用户组
     * 
     * @param groupName
     * @param lOfficeID
     * @return @throws
     * @author xgzhang
     * @create 2005-09-14
     *         ITreasuryDAOException
     */
    public Collection findGroupByName(String groupName,long lOfficeID) throws ITreasuryDAOException
    {
        Sys_GroupInfo condition = new Sys_GroupInfo();
        condition.setName(groupName);
        condition.setOfficeID(lOfficeID);
        return findByCondition(condition);
    }
    
    public ArrayList findGroupByName(Sys_GroupInfo condition) throws ITreasuryDAOException
    {
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer();
        Sys_GroupInfo info = null;
        try
        {
        	initDAO();
        	sql.append(" select * from Sys_group s ");
        	sql.append(" where s.nstatusid !="+SYSConstant.SysCheckStatus.DELETED);
        	if(condition.getName().trim().length()>0&&!condition.getName().trim().equals(""))
        	{
        		sql.append(" and s.name ='"+condition.getName().trim()+"'");
        	}
        	if(condition.getOfficeID()>0)
        	{
        		sql.append(" and s.officeid ="+condition.getOfficeID());
        	}
        	transPS = transConn.prepareStatement(sql.toString());
        	transRS = transPS.executeQuery();

        	while(transRS.next())
        	{
        		info = new Sys_GroupInfo();
        		info.setId(transRS.getLong("id"));
        		list.add(info);
        	}
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
            finalizeDAO();
        }        
        return list.size()>0?list:null;
    }

    public static void main(String args1[])
    {
        Sys_GroupDAO dao = new Sys_GroupDAO();
        Sys_GroupInfo en = new Sys_GroupInfo();
        en.setId(1);
        Vector v = null;
        try
        {
            v = (Vector) dao.findGroupbyCondition(en, 1, 1);
            // v = (Vector)dao.findUserByCondition(en,1,1);
            System.out.println(v.size());
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    
    public Collection findGroupByInfo(Sys_GroupInfo info) throws ITreasuryDAOException
    {
        Sys_GroupInfo condition = new Sys_GroupInfo();
        if(info.getId()>0)
        {
        	condition.setId(info.getId());        	
        }
        if(info.getCheckUserID()>0)
        {
        	condition.setInputUserID(info.getCheckUserID());
        }
        return findByCondition(condition);
    }

   
    /**
     * 根据用户组信息，排序条件查询用户组
     * 
     * @param groupinfo
     * @param orderCondition
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findGroupbyCondition(Sys_GroupInfo groupinfo, long orderCondition, long lCompositorCondition) throws ITreasuryDAOException, SQLException
    {
        Vector vectTemp = new Vector();
        try
        {
            String name = groupinfo.getName();
            long officeid = groupinfo.getOfficeID();
            long statusID = groupinfo.getStatusID();
            initDAO();
            StringBuffer sqlstr = new StringBuffer();
            sqlstr.append("select a.id,a.name,a.moduleId,a.officeid, b.sname,a.inputDate,a.CheckUserID,a.dtCheck,a.nstatusID from sys_group a,userinfo b where a.inputUserId=b.id(+)");
            if (name.length() > 0)
                sqlstr.append(" and a.name like '%" + name + "%'");
            if (officeid != -1L)
                sqlstr.append(" and a.officeid=" + officeid);
            if (statusID >=0)
            {
            	if(statusID==SYSConstant.SysCheckStatus.UNCHECK)
            	{
            		if(groupinfo.getCheckUserID()>0)
            		{
            			sqlstr.append(" and a.inputUserId != "+groupinfo.getCheckUserID()+"");
            		}
            	}
                sqlstr.append(" and a.nstatusID=" + statusID);
            }
            
            
            if (orderCondition < 0)
            {
                sqlstr.append(" order by a.id");
            }
            else if (orderCondition == PrivilegeConstant.GroupOrderCondition.GROUPNAME)
            {
                sqlstr.append(" order by a.name");
            }
            else if (orderCondition == PrivilegeConstant.GroupOrderCondition.MODULEID)
            {
                sqlstr.append(" order by a.moduleId");
            }
            else if (orderCondition == PrivilegeConstant.GroupOrderCondition.INPUTUSER)
            {
                sqlstr.append(" order by b.sname");
            }
            else if (orderCondition == PrivilegeConstant.GroupOrderCondition.INPUTDATE)
            {
                sqlstr.append(" order by a.inputDate");
            }

            if (lCompositorCondition == 1)
                sqlstr.append(" asc ");
            else
                sqlstr.append(" desc ");

            System.out.println(sqlstr);
            transPS = transConn.prepareStatement(sqlstr.toString());
            transRS = transPS.executeQuery();
            ResultSetMetaData rsmd = transRS.getMetaData();
            Vector oneGroup;
            for (; transRS.next(); vectTemp.addElement(oneGroup))
            {
                oneGroup = new Vector();
                for (int i = 0; i < rsmd.getColumnCount(); i++)
                    oneGroup.addElement(transRS.getString(i + 1));

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
        return vectTemp.size() > 0 ? vectTemp : null;
    }
    
    public void updateGroupCondition(Sys_GroupInfo groupInfo)throws ITreasuryDAOException, SQLException
    {
    	StringBuffer updateSQL = new StringBuffer();
    	StringBuffer setSQL = new StringBuffer();
    	StringBuffer whereSQL = new StringBuffer();
    	String strSetSQL = "";
    	try
    	{
    		updateSQL.append(" update Sys_group s set ");
    		if(!groupInfo.getName().trim().equals("")&&groupInfo.getName().trim().length()>0)
    		{
    			setSQL.append(" s.name='"+groupInfo.getName().trim()+"',");
    		}
    		if(groupInfo.getOfficeID()>-1)
    		{
    			setSQL.append(" s.officeid="+groupInfo.getOfficeID()+",");
    		}
    		if(groupInfo.getInputUserID()>-1)
    		{
    			setSQL.append(" s.inputuserid="+groupInfo.getInputUserID()+",");
    		}
    		if(groupInfo.getStatusID()>-1)
    		{
    			setSQL.append(" s.nstatusid="+groupInfo.getStatusID()+",");
    		}
    		if(groupInfo.getInputDate()!=null)
    		{
    			setSQL.append(" s.inputdate=to_timestamp('"+groupInfo.getInputDate()+"','yyyy-MM-dd hh24:mi:ss.ff'),");
    		}
    		strSetSQL = setSQL.substring(0, setSQL.length()-1);
    		whereSQL.append(" where s.id="+groupInfo.getId());
    		transPS = transConn.prepareStatement(updateSQL.toString()+strSetSQL+whereSQL.toString());
    	    transPS.executeUpdate();
    	    
    	}catch(Exception e) 
    	{
    		e.printStackTrace();
    	}
        finally
        {
            finalizeDAO();
        }
    }
}
/**
 * 用户组数据访问层类实体
 * @author jinchen
 */

package com.iss.itreasury.ebank.privilege.dao;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dataentity.OB_GroupInfo;
import com.iss.itreasury.ebank.privilege.util.PrivilegeConstant;
import com.iss.itreasury.util.IException;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class OB_GroupDAO extends ITreasuryDAO
{

    public OB_GroupDAO()
    {
        super("OB_group");
    }

    public OB_GroupDAO(String tableName)
    {
        super(tableName);
    }

    public OB_GroupDAO(String tableName, Connection conn)
    {
        super(tableName, conn);
    }

    public OB_GroupDAO(boolean isNeedPrefix)
    {
        super(isNeedPrefix);
    }

    public OB_GroupDAO(String tableName, boolean isNeedPrefix)
    {
        super(tableName, isNeedPrefix);
    }

    public OB_GroupDAO(String tableName, boolean isNeedPrefix, Connection conn)
    {
        super(tableName, isNeedPrefix, conn);
    }
    /**
     * 根据用户组ID删除一条用户组记录
     * @param groupId
     * @throws IException
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public void del(long groupId) throws IException 
        
    {
        try
        {
            initDAO();
       
        StringBuffer sqlstr = new StringBuffer();
        sqlstr.append("delete OB_group where id=" + groupId);
        transPS = transConn.prepareStatement(sqlstr.toString());
        transPS.executeQuery();
        this.finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("删除用户组发生错误",e);
            //throw new IException(null,e);
        } catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("删除用户组数据库操作发生异常",e);
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
    /**
     * 根据用户组名查询用户组
     * @param groupName
     * @return
     * @throws IException
     * @throws ITreasuryDAOException
     */
    public Collection findGroupByName(String groupName) throws IException
        
    {
        Collection c =null;
        try
        {
        this.initDAO();
        OB_GroupInfo condition = new OB_GroupInfo();
        condition.setName(groupName);
        c = this.findByCondition(condition);
        this.finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户组发生错误",e);
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
    
    /**
     * 根据用户组名查询用户组
     * @param groupName
     * @return
     * @throws ITreasuryDAOException
     */
    public Collection findGroupByName(String groupName , long clientId)
        throws IException
    {
        
        Collection c =null;
        try
        {
        this.initDAO();
        OB_GroupInfo condition = new OB_GroupInfo();
        condition.setName(groupName);
        condition.setClientId(clientId);
        c = this.findByCondition(condition);
        this.finalizeDAO();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("查找用户组发生错误",e);
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
  
    
   
  

    public static void main(String args1[])
    {
    	/* OB_GroupDAO dao = new OB_GroupDAO();
         OB_GroupInfo en = new OB_GroupInfo();
         en.setId(1);
         Collection v  = null;
         try
         {
             v =  dao.findGroupByName("系统管理组",1);
            // v = (Vector)dao.findUserByCondition(en,1,1);
             System.out.println(v.size());
         }
         catch(ITreasuryDAOException e)
         {
             e.printStackTrace();
         }*/
      
       
    }

	/**
	 *  根据用户组信息，排序条件查询用户组
	 * @param groupinfo
	 * @param orderCondition
	 * @return
	 * @throws IException
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findGroupbyCondition(OB_GroupInfo groupinfo, long orderCondition) throws IException 
	{
		 String name = groupinfo.getName();
	        long officeid = groupinfo.getOfficeID();
	        Vector vectTemp = new Vector();
	        try
	        {
	        initDAO();
	        StringBuffer sqlstr = new StringBuffer();
	        sqlstr.append("select a.id,a.name,a.moduleId, b.sname,a.inputDate from OB_group a,OB_user b where a.inputUserId=b.id(+)");
	        if(name.length() > 0)
	            sqlstr.append(" and a.name like '%" + name + "%'");
	        if(officeid != -1L)
	            sqlstr.append(" and a.officeid=" + officeid);
	        if(orderCondition<0)
	        {
	        sqlstr.append(" order by a.id");
	        }
	        else if(orderCondition == PrivilegeConstant.GroupOrderCondition.GROUPNAME)
	        {
	        	sqlstr.append(" order by a.name");
	        }
	        else if(orderCondition == PrivilegeConstant.GroupOrderCondition.MODULEID)
	        {
	        	sqlstr.append(" order by a.moduleId");
	        }
	        else if(orderCondition == PrivilegeConstant.GroupOrderCondition.INPUTUSER)
	        {
	        	sqlstr.append(" order by b.sname");
	        }
	        else if(orderCondition == PrivilegeConstant.GroupOrderCondition.INPUTDATE)
	        {
	        	sqlstr.append(" order by a.inputDate");
	        } 
	       
	       
	        System.out.println(sqlstr);
	        transPS = transConn.prepareStatement(sqlstr.toString());
	        transRS = transPS.executeQuery();
	        ResultSetMetaData rsmd = transRS.getMetaData();
	        Vector oneGroup;
	        for(; transRS.next(); vectTemp.addElement(oneGroup))
	        {
	            oneGroup = new Vector();
	            for(int i = 0; i < rsmd.getColumnCount(); i++)
	                oneGroup.addElement(transRS.getString(i + 1));

	        }

	        finalizeDAO();
	        } catch (ITreasuryDAOException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            throw new IException("查找用户组发生错误",e);
	            //throw new IException(null,e);
	        } catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new IException("查找用户组发生数据库操作异常",e);
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
	        return vectTemp.size()>0 ? vectTemp : null;
	}
	
	public ArrayList findGroupInformationByCondition(OB_GroupInfo queryGroupInfo) throws Exception
	{
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		OB_GroupInfo info = null;
		try
		{
			initDAO();
			sql.append(" select o.id,o.name from ob_group o ");
			sql.append(" where 2>1 ");
			if(queryGroupInfo.getClientId()>0)
			{
				sql.append(" and o.clientid ="+queryGroupInfo.getClientId());
			}
			if(queryGroupInfo.getIsAdminGroup()>0)
			{
				sql.append(" and o.isadmingroup ="+queryGroupInfo.getIsAdminGroup());
			}
			log.info("sql="+sql.toString());
	        transPS = transConn.prepareStatement(sql.toString());
	        transRS = transPS.executeQuery();		

	        while(transRS.next())
	        {
	        	info = new OB_GroupInfo();
	        	info.setId(transRS.getLong("id"));
	        	info.setName(transRS.getString("name"));
	        	list.add(info);
	        }
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception();
		}
		finally
		{
			this.finalizeDAO();
		}
		return list.size()>0?list:null;
	}
}

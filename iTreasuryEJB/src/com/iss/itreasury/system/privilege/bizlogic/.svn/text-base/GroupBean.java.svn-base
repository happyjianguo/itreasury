/**
 * 用户组管理业务逻辑类
 * 
 * @see com.iss.itreasury.system.privilege.bizlogic.UserDelegation
 */

package com.iss.itreasury.system.privilege.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.privilege.dao.Sys_GroupDAO;
import com.iss.itreasury.system.privilege.dao.Sys_Group_Of_UserDAO;
import com.iss.itreasury.system.privilege.dao.Sys_PrivilegeDAO;
import com.iss.itreasury.system.privilege.dao.Sys_Privilege_Of_GroupDAO;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_Privilege_Of_GroupInfo;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.system.util.SYSConstant;

public class GroupBean
{ 

    /**
     * 默认初始化方法 创建java.sql.Connection 创建数据访问层类实体
     *  
     */
    public GroupBean()
    {
        super();
    }

    /*
     * 创建数据库连接
     */
    private Connection initBean() throws ITreasuryDAOException
    {
        Connection transConn = null;
        try
        {

            transConn = Database.getConnection();
            transConn.setAutoCommit(false);

        }
        catch (Exception e)
        {
            throw new ITreasuryDAOException("数据库初使化异常发生", e);
        }
        return transConn;
    }

    /*
     * 释放数据库连接
     */
    private void finalizeBean(Connection transConn) throws ITreasuryDAOException
    {
        try
        {
            if (transConn != null)
            {
                transConn.close();
                transConn = null;
            }
        }
        catch (SQLException e)
        {
            throw new ITreasuryDAOException("数据库关闭异常发生", e);
        }
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupID
     * @return @throws
     *         ITreasuryDAOException
     */
    public Sys_GroupInfo findGroupInfoByID(long groupID) throws ITreasuryDAOException
    {
        Sys_GroupInfo groupinfo = null;
        Connection transConn = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group", true,transConn);
            groupinfo = (Sys_GroupInfo) groupDao.findByID(groupID, (new Sys_GroupInfo()).getClass());
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return groupinfo;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupID
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findGroupPrivilegeByID(long groupID) throws ITreasuryDAOException
    {
        Collection c = null;
        Connection transConn = null;
        try
        {
            transConn = this.initBean();
            Sys_Privilege_Of_GroupDAO privilege_groupDao = new Sys_Privilege_Of_GroupDAO("Sys_Privilege_Of_Group", transConn);
            c = privilege_groupDao.findPrivilegeByGroupId(groupID);
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupCondition
     * @param pgCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public long addGroup(Sys_GroupInfo groupCondition, Sys_Privilege_Of_GroupInfo pgCondition[]) throws SQLException, IException
    {

        long groupId = 0L;
        Connection transConn = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group", transConn);
            Sys_Privilege_Of_GroupDAO privilege_groupDao = new Sys_Privilege_Of_GroupDAO("Sys_Privilege_Of_Group", transConn);
            Sys_GroupInfo condition = new Sys_GroupInfo();
            condition.setName(groupCondition.getName());
            condition.setOfficeID(groupCondition.getOfficeID());
            ArrayList list = groupDao.findGroupByName(condition);
            if (list != null && list.size() > 0)
                throw new IException("已经存在此组名，请重新录入。");
            try
            {
                groupDao.setUseMaxID();
                groupCondition.setInputDate(Env.getSystemDateTime());
                groupCondition.setStatusID(SYSConstant.SysCheckStatus.UNCHECK);
                groupId = groupDao.add(groupCondition);
                int len = pgCondition.length;
                for (int i = 0; i < len; i++)
                {
                    pgCondition[i].setGroupID(groupId);
                    privilege_groupDao.setUseMaxID();
                    privilege_groupDao.add(pgCondition[i]);
                }

                transConn.commit();
            }
            catch (Exception e)
            {
                transConn.rollback();
                System.out.println("系统错误，回滚到更新前状态");
                e.printStackTrace();
                throw new IException("增加用户组操作失败，系统已经恢复到操作前状态。");
            }

            this.finalizeBean(transConn);
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return groupId;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupId
     * @throws SQLException
     * @throws IException
     */
    public int delGroup(long groupId) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group", transConn);
            Sys_Privilege_Of_GroupDAO privilege_groupDao = new Sys_Privilege_Of_GroupDAO("Sys_Privilege_Of_Group", transConn);
            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);

            Collection c = group_userDao.findUserByGroupId(groupId);
            if (c != null && c.size() > 0)
                throw new IException("此组正在被使用不能被删除。");
            try
            {
                //privilege_groupDao.del(groupId);
               // groupDao.del(groupId);
            	Sys_GroupInfo groupInfo = new Sys_GroupInfo();
            	groupInfo.setId(groupId);
            	groupInfo.setStatusID(SYSConstant.SysCheckStatus.DELETED);
            	groupDao.update(groupInfo);
                transConn.commit();
                nResult = 1;
            }
            catch (Exception e)
            {
                transConn.rollback();
                System.out.println("系统错误，回滚到更新前状态");
                e.printStackTrace();
                throw new IException("删除用户组操作失败，系统已经恢复到操作前状态。");
            }

            this.finalizeBean(transConn);
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return nResult;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupCondition
     * @param pgCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */

    public int updateGroup(Sys_GroupInfo groupCondition, Sys_Privilege_Of_GroupInfo pgCondition[]) throws SQLException, IException
    {
        int nResult = -1;

        Connection transConn = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group", transConn);
            Sys_Privilege_Of_GroupDAO privilege_groupDao = new Sys_Privilege_Of_GroupDAO("Sys_Privilege_Of_Group", transConn);
            Sys_GroupInfo condition = new Sys_GroupInfo();
            condition.setName(groupCondition.getName());
            condition.setOfficeID(groupCondition.getOfficeID());
            ArrayList list = groupDao.findGroupByName(condition);
            if (list != null && list.size() > 0)
            {
                long groupId = ((Sys_GroupInfo) list.iterator().next()).getId();
                if (groupId != groupCondition.getId())
                {
                    throw new IException("用户组名已经存在，请修改用户组名，更新用户组失败。");
                }
                else
                    try
                    {
                        privilege_groupDao.del(groupId);
                        int len = pgCondition.length;
                        for (int i = 0; i < len; i++)
                        {
                            pgCondition[i].setGroupID(groupId);
                            privilege_groupDao.setUseMaxID();
                            privilege_groupDao.add(pgCondition[i]);
                        }
                        groupCondition.setStatusID(SYSConstant.SysCheckStatus.UNCHECK);
                        groupCondition.setInputDate(Env.getSystemDateTime());
                        System.out.println(groupCondition.getInputUserID());
                       // groupDao.update(groupCondition);
                        groupDao.updateGroupCondition(groupCondition);
                        transConn.commit();
                        nResult = 1;
                        System.out.println("用户组信息更新成功");
                    }
                    catch (Exception e)
                    {
                        transConn.rollback();
                        System.out.println("系统错误，回滚到更新前状态");
                        e.printStackTrace();
                        throw new IException("更新用户组操作失败，系统已经恢复到操作前状态。");
                    }
            }
            else
            {
                long groupId = groupCondition.getId();
                System.out.println("要更新的组id为           " + groupId);
                try
                {
                    privilege_groupDao.del(groupId);
                    int len = pgCondition.length;
                    for (int i = 0; i < len; i++)
                    {
                        pgCondition[i].setGroupID(groupId);
                        privilege_groupDao.setUseMaxID();
                        privilege_groupDao.add(pgCondition[i]);
                    }
                    groupCondition.setStatusID(SYSConstant.SysCheckStatus.UNCHECK);
                   // groupDao.update(groupCondition);
                    groupDao.updateGroupCondition(groupCondition);
                    transConn.commit();
                    nResult = 1;
                    System.out.println("用户组更新成功");
                }
                catch (Exception e1)
                {
                    transConn.rollback();
                    System.out.println("系统错误，回滚到更新前状态");
                    e1.printStackTrace();
                    throw new IException("更新用户组操作失败，系统已经恢复到操作前状态。");
                }
            }
            this.finalizeBean(transConn);
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return nResult;
    }
    
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupCondition
     * @param pgCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */

    public int check(Sys_GroupInfo groupCondition) throws SQLException, IException
    {
        int nResult = -1;

        Connection transConn = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group", transConn);
            Collection c = groupDao.findGroupByInfo(groupCondition);
            if (c != null && c.size() > 0)
            {  
            	nResult = -1;
            	throw new IException("新增和复核不能为同一人！");
            }
            else
            {
                long groupId = groupCondition.getId();
                Sys_GroupInfo info = (Sys_GroupInfo) groupDao.findByID(groupId,Sys_GroupInfo.class);
                try
                {                    
                	info.setCheckUserID(groupCondition.getCheckUserID());
                	info.setDtCheck(Env.getSystemDateTime());
                	info.setStatusID(SYSConstant.SysCheckStatus.CHECK);
                    groupDao.update(info);
                    transConn.commit();
                    nResult = 1;
                    System.out.println("用户组复核成功");
                }
                catch (Exception e1)
                {
                    transConn.rollback();
                    System.out.println("系统错误，回滚到更新前状态");
                    e1.printStackTrace();
                    throw new IException("更新用户组操作失败，系统已经恢复到操作前状态。");
                }
            }
            this.finalizeBean(transConn);
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return nResult;
    } 
    
    
    
    
    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param moduleId
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findPrivilegesbyModuleId(long moduleId,long officeId) throws ITreasuryDAOException, SQLException
    {
        Connection transConn = null;
        Collection c = null;
        try
        {
            transConn = this.initBean();
            Sys_PrivilegeDAO privilegeDao = new Sys_PrivilegeDAO("Sys_Privilege", transConn);
            c = privilegeDao.findPrivilegesbyModuleId(moduleId,officeId);
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return c;
    }
    
    /**
     * Create by leiyang date 2007/08/09
     * 
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findPrivilegesByMain(ArrayList arg0,long lUserId,long lOfficeID) throws ITreasuryDAOException, SQLException
    {
        Connection transConn = null;
        Collection c = null;
        try
        {
            transConn = this.initBean();
            Sys_PrivilegeDAO privilegeDao = new Sys_PrivilegeDAO("Sys_Privilege", transConn);
            c = privilegeDao.findPrivilegesByMain(arg0,lUserId,lOfficeID);
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param moduleId
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findGroupByModuleId(long moduleId,long lOfficeID,long lCurrencyID) throws ITreasuryDAOException
    {
        Connection transConn = null;
        Collection c = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group", transConn);
            Sys_GroupInfo info = new Sys_GroupInfo();
            info.setModuleID(moduleId);
            info.setOfficeID( lOfficeID );
            info.setStatusID(SYSConstant.SysCheckStatus.CHECK);
           
            c = groupDao.findByCondition(info);
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param args
     * @throws SQLException
     * @throws IException
     */
    public static void main(String args[]) throws SQLException, IException
    {
        GroupBean bean = new GroupBean();
        Sys_GroupInfo groupinfo = new Sys_GroupInfo();
        groupinfo.setName("test2");
        groupinfo.setOfficeID(1L);
        groupinfo.setModuleID(2L);
        groupinfo.setRank("h");
        groupinfo.setInputUserID(1L);
        Sys_Privilege_Of_GroupInfo privilege_groupinfo = new Sys_Privilege_Of_GroupInfo();
        privilege_groupinfo.setPrivilegeID(33L);
        Sys_Privilege_Of_GroupInfo privilege_group[] =
        { privilege_groupinfo };
        try
        {
            bean.delGroup(23L);
        }
        catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.GroupDelegation
     * @param groupinfo
     * @param orderCondition
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findGroupByCondition(Sys_GroupInfo groupinfo, long orderCondition, long lCompositorCondition) throws ITreasuryDAOException, SQLException
    {
        // TODO Auto-generated method stub
        Connection transConn = null;
        Collection c = null;
        try
        {
            transConn = this.initBean();
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_group",  true,transConn);
            c = groupDao.findGroupbyCondition(groupinfo, orderCondition, lCompositorCondition);
            this.finalizeBean(transConn);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return c;
    }

    /**
     * 
     * @param groupId
     * @return @throws
     *         SQLException
     * @throws ITreasuryDAOException
     */
    public Collection findPrivilegesByGroupId(long groupId) throws SQLException, ITreasuryDAOException
    {
        Connection transConn = null;
        Collection c = null;
        try
        {
            transConn = this.initBean();
            Sys_PrivilegeDAO privilegeDao = new Sys_PrivilegeDAO("Sys_Privilege", transConn);
            c = privilegeDao.findPrivilegesByGroupId(groupId);
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.finalizeBean(transConn);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        return c;
    }
}
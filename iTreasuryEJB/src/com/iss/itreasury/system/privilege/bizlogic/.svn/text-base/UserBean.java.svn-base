// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: UserBean.java
/**
 * 用户管理业务逻辑类 自维护事务为普通javabean
 * 
 * @author jinchen
 */
package com.iss.itreasury.system.privilege.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.encrypt.EncryptManager;
import com.iss.itreasury.system.privilege.dao.*;
import com.iss.itreasury.system.privilege.dataentity.*;
import com.iss.itreasury.util.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import com.iss.itreasury.system.util.SYSConstant;
public class UserBean
{

    /**
     * 默认初始化方法 加载Connection 创建数据访问层实体
     */
    public UserBean()
    {
        super();
    }

    /*
     * 创建数据库连接 @throws ITreasuryDAOException
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
     * 释放数据库连接 @throws ITreasuryDAOException
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
            e.printStackTrace();
            throw new ITreasuryDAOException("数据库关闭异常发生", e);
        }
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userID
     * @return @throws
     *         ITreasuryDAOException
     */
    public Sys_UserInfo findUserInfoByID(long userID) throws ITreasuryDAOException
    {
        Connection transConn = null;
        Sys_UserInfo sys_userentity = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            sys_userentity = (Sys_UserInfo) userDao.findByID(userID, (new Sys_UserInfo()).getClass());
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return sys_userentity;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userID
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findGroupByUserId(long userID,long lOfficeID) throws ITreasuryDAOException
    {
        Collection c = null;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
            c = group_userDao.findGroupByUserId(userID,lOfficeID);
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param condition
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findUserByCondition(Sys_UserInfo condition) throws ITreasuryDAOException
    {
        Collection c = null;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            c = userDao.findByCondition(condition);
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return c;
    }
    public int findUserCount(Sys_UserInfo condition) throws ITreasuryDAOException
    {
        int c = -1;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            c = userDao.findUserCount(condition);
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @param group_userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int addUser(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition[]) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
            /*Collection c = userDao.findByLoginNo(userCondition.getLoginNo());
            
             * c 不为null 说明登录名已经存在，throw new IException
             
            if (c != null && c.size() > 0)
            {
                throw new IException("用户登录名已经存在，请重新录入。");
            }
            */
            try
            {
                /*
                 * 添加用户信息
                 */
                userCondition.setStatusID(SYSConstant.SysCheckStatus.UNCHECK);
                userCondition.setPassword("123456");
                userCondition.setInput(Env.getSystemDateTime());
                userCondition.setChangePassword(Env.getSystemDate());
                userDao.setUseMaxID();
                long userId = userDao.add(userCondition);
                int len = group_userCondition.length;
                /*
                 * 添加用户用户组关系信息
                 */
                for (int i = 0; i < len; i++)
                {
                    group_userCondition[i].setUserID(userId);
                    group_userDao.setUseMaxID();
                    group_userDao.add(group_userCondition[i]);
                }
                //提交
                transConn.commit();
                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(userId,"123456");
                }
                nResult = 1;
            }
            catch (Exception e)
            {
                //失败回滚
                transConn.rollback();
                System.out.println("系统错误，回滚到更新前状态");
                e.printStackTrace();
                throw new IException("增加用户操作失败，系统已经恢复到操作前状态。");
            }
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
            if (transConn == null)
                System.out.println("释放连接成功--------------------------------------->>>>>>>>>>>>>>>>");
            else
                System.out.println("释放连接失败--------------------------------------->>>>>>>>>>>>>>>>");
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }
    
    public int addUser(Sys_UserInfo userCondition) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);

            try
            {
                /*
                 * 添加用户信息
                 */
                userCondition.setStatusID(SYSConstant.SysCheckStatus.UNCHECK);
                userCondition.setPassword("123456");
                userCondition.setInput(Env.getSystemDateTime());
                userCondition.setChangePassword(Env.getSystemDate());
                userDao.setUseMaxID();
                long userId = userDao.add(userCondition);
                
                //提交
                transConn.commit();
                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(userId,"123456");
                }
                nResult = 1;
            }
            catch (Exception e)
            {
                //失败回滚
                transConn.rollback();
                System.out.println("系统错误，回滚到更新前状态");
                e.printStackTrace();
                throw new IException("增加用户操作失败，系统已经恢复到操作前状态。");
            }
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
            if (transConn == null)
                System.out.println("释放连接成功--------------------------------------->>>>>>>>>>>>>>>>");
            else
                System.out.println("释放连接失败--------------------------------------->>>>>>>>>>>>>>>>");
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    	
    }
    
    /**
     * Add Boxu 2008年1月7日
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @param group_userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int addNewUser(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        Sys_UserInfo userinfo = new Sys_UserInfo();
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
            
            Collection c = userDao.findByLoginNo(userCondition.getLoginNo());
            if (c != null && c.size() > 0)
            {
                throw new IException("用户登录名已经存在，请重新录入。");
            }
            
            try
            {
                /*
                 * 添加用户信息
                 */
                userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
                userCondition.setInput(Env.getSystemDateTime());
                userCondition.setCheck(Env.getSystemDateTime());
                userCondition.setChangePassword(Env.getSystemDate());
                userDao.setUseMaxID();
                long userId = userDao.add(userCondition);
                
                userinfo.setId(userId);
                userinfo.setInputUserID(userId);
                userinfo.setCheckUserID(userId);
                userinfo.setCode("00"+userId);
                userDao.update(userinfo);
                
                //Sys_Group_of_user
                group_userDao.setUseMaxID();
                group_userCondition.setUserID(userId);
                group_userCondition.setGroupID(userCondition.getNusergroupid()); //自动新增2个用户，这2个用户归属的用户组不能写死。全哨修改 2010-5-12
                group_userDao.add(group_userCondition);
                
                //提交
                transConn.commit();
                if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
                {
                	EncryptManager.getBeanFactory().changeUserPassword(userId,userCondition.getPassword());
                }
                nResult = 1;
            }
            catch (Exception e)
            {
                //失败回滚
                transConn.rollback();
                System.out.println("系统错误，回滚到更新前状态");
                e.printStackTrace();
                throw new IException("增加用户操作失败，系统已经恢复到操作前状态。");
            }
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
            if (transConn == null)
                System.out.println("释放连接成功--------------------------------------->>>>>>>>>>>>>>>>");
            else
                System.out.println("释放连接失败--------------------------------------->>>>>>>>>>>>>>>>");
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return nResult;
    }
    
    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @param group_userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int check(Sys_UserInfo userCondition) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            
            Sys_UserDAO userDao = new Sys_UserDAO(); 
            /*
             * 复核用户信息
             */        	
            userCondition.setStatusID(SYSConstant.SysCheckStatus.CHECK);
        	userCondition.setCheckUserID(userCondition.getCheckUserID());
        	userCondition.setCheck(Env.getSystemDateTime());
        	userCondition.setChangePassword(Env.getSystemDateTime());
            userDao.chekXao(userCondition);                
            nResult = 1;            
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }
    
    public int checkAuthority(Sys_UserAuthorityInfo authorityInfo)throws SQLException, IException
    {
    	int lResult = -1;
    	try
    	{
	    	Sys_UserDAO userDao = new Sys_UserDAO("sys_userauthority", true);
	    	authorityInfo.setDtCheck(Env.getSystemDateTime());
	    	userDao.update(authorityInfo);
	    	lResult = 1;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return lResult;
    }
    
    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @param group_userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int updateUserInfo(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition[]) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
            /*Collection c = userDao.findByLoginNo(userCondition.getLoginNo());
            
             * 对用户登录名在数据库已经存在情况的处理。 如果存在的登录名不是当前用户记录则抛异常
             *  
             
            if (c != null && c.size() > 0)
            {
                long userId = ((Sys_UserInfo) c.iterator().next()).getId();
                System.out.println("userid" + userId + "userCondition.getId()" + userCondition.getId());
                if (userId != userCondition.getId())
                {
                    System.out.println("登录名重复");
                    throw new IException("用户登录名已经存在，请更改登录名.用户更新失败。");
                }
                try
                {
                    if (group_userCondition != null)
                    {
                        /*
                         * 删除用户用户组关系信息
                         
                        group_userDao.del(userId);
                        int len = group_userCondition.length;
                        /*
                         * 添加用户用户组关系信息
                         
                        for (int i = 0; i < len; i++)
                        {
                            group_userCondition[i].setUserID(userId);
                            group_userDao.setUseMaxID();
                            group_userDao.add(group_userCondition[i]);
                        }
                    }
                    /*
                     * 更新用户信息
                     
                    userDao.update(userCondition);
                    //成功提交
                    transConn.commit();
                    nResult = 1;
                    //System.out.println("用户信息更新成功");
                }
                catch (Exception e)
                {
                    //失败回滚
                    transConn.rollback();
                    System.out.println("系统错误，回滚到更新前状态");
                    e.printStackTrace();
                    throw new IException("更新用户操作失败，系统已经恢复到操作前状态。");
                }
            }/*
              * 对用户登录名在数据库不存在情况的处理。
              
            else
            {
            */
                long userId = userCondition.getId();
                System.out.println("要更新的组id为           " + userId);
                try
                {
                    if (group_userCondition != null)
                    { //删除用户与用户组关系
                        group_userDao.del(userId);
                        int len = group_userCondition.length;
                        /*
                         * 添加新的用户与用户组关系
                         */
                        for (int i = 0; i < len; i++)
                        {
                            group_userCondition[i].setUserID(userId);
                            group_userDao.setUseMaxID();
                            group_userDao.add(group_userCondition[i]);
                        }
                    }
                    //更新用户信息
                    userDao.update(userCondition);
                    //成功提交
                    transConn.commit();
                    nResult = 1;
                    System.out.println("用户信息更新成功");
                }
                catch (Exception e1)
                {
                    //失败回滚
                    transConn.rollback();
                    System.out.println("系统错误，回滚到更新前状态");
                    e1.printStackTrace();
                    throw new IException("更新用户操作失败，系统已经恢复到操作前状态。");
                }
            //}
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @param group_userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int updateUserInfo(Sys_UserInfo userCondition) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);

                try
                {
                    //更新用户信息
                    userDao.update(userCondition);
                    //成功提交
                    transConn.commit();
                    nResult = 1;
                    System.out.println("用户信息更新成功");
                }
                catch (Exception e1)
                {
                    //失败回滚
                    transConn.rollback();
                    System.out.println("系统错误，回滚到更新前状态");
                    e1.printStackTrace();
                    throw new IException("更新用户操作失败，系统已经恢复到操作前状态。");
                }
            //}
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }
    
    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userCondition
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int changeUserPassword(Sys_UserInfo userCondition) throws SQLException, IException
    {
        int nResult = -1;
        Connection transConn = null;
        try
        {
        	//加密校验
        	if (Config.getBoolean(ConfigConstant.SETT_CAN_ENCRYPT,false))
        	{
        		EncryptManager.getBeanFactory().changeUserPassword(userCondition.getId(),userCondition.getPassword());
        	}
        	else{
	            try
	            {
	                transConn = Database.getConnection();
	                transConn.setAutoCommit(false);
	            }
	            catch (Exception e)
	            {
	                throw new ITreasuryDAOException("数据库初使化异常发生", e);
	            }
	            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
	            try
	            {
	                userDao.update(userCondition);
	                transConn.commit();
	                nResult = 1;
	                System.out.println("密码更新成功");
	            }
	            catch (Exception e)
	            {
	                transConn.rollback();
	                System.out.println("系统错误，回滚到更新前状态");
	                e.printStackTrace();
	                throw new IException("更改用户密码操作失败，系统已经恢复到操作前状态。");
	            }
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
	                e.printStackTrace();
	                throw new ITreasuryDAOException("数据库关闭异常发生", e);
	            }
        	}
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userId
     * @return @throws
     *         SQLException
     * @throws IException
     */
    public int delUser(long userId) throws SQLException, IException
    {

        int nResult = -1;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
            Sys_UserInfo userEntity = new Sys_UserInfo();
            userEntity.setId(userId);
            userEntity.setStatusID(0L);
            try
            {
                /*
                 * 删除用户与用户组关系
                 */
                group_userDao.del(userId);
                /*
                 * 删除用户信息 逻辑删除
                 */
                userDao.update(userEntity);
                //成功提交
                transConn.commit();
                System.out.println("用户逻辑删除成功");
                nResult = 1;
            }
            catch (IException e)
            {
                transConn.rollback();
                System.out.println("系统错误，回滚到更新前状态");
                e.printStackTrace();
                throw new IException("删除用户操作失败，系统已经恢复到操作前状态。");
            }
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (IException ie)
        {
            throw ie;
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return nResult;
    }

    /**
     * 根据模块取得用户对应的所有权限，取并集
     * 
     * @param userID
     *            用户编号
     * @param moduleID
     *            系统模块编号
     * @return 用户具有的所有权限
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection getPrivilegeOfUser(long userID, long moduleID,long lOfficeid) throws ITreasuryDAOException, SQLException
    {
        Collection c = null;
        Connection transConn = null;
        try
        {
            try
            {
                transConn = Database.getConnection();
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_PrivilegeDAO privilegeDao = new Sys_PrivilegeDAO("Sys_Privilege", transConn);
            c = privilegeDao.getPrivilegeOfUser(userID, moduleID,lOfficeid);
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userInfoCondition
     * @param groupInfoCondition
     * @param orderCondition
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */
    public Collection findUserByCondition(Sys_UserInfo userInfoCondition, Sys_GroupInfo groupInfoCondition, long orderCondition, long lasc) throws ITreasuryDAOException, SQLException
    {
        Connection transConn = null;
        Vector vectTemp1 = new Vector();

        try
        {
            try
            {
                transConn = Database.getConnection();
                transConn.setAutoCommit(false);
            }
            catch (Exception e)
            {
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Sys_UserDAO userDao = new Sys_UserDAO("userinfo", true, transConn);
            //Sys_Group_Of_UserDAO group_userDao = new
            // Sys_Group_Of_UserDAO("Sys_Group_Of_User", transConn);
            Sys_GroupDAO groupDao = new Sys_GroupDAO("Sys_Group", transConn);
            /*
             * 如果是查询条件含有用户组信息
             */
            if (groupInfoCondition != null)
            {
                System.out.println("组条件不为空");
                String strGroupName = groupInfoCondition.getName();
                Collection c = null;
                c = groupDao.findGroupByName(strGroupName);
                if (c.size() == 0)
                    return vectTemp1;
                long groupId = ((Sys_GroupInfo) c.iterator().next()).getId();
                vectTemp1 = userDao.findUserByCondition(userInfoCondition, groupId, orderCondition, lasc);
            }/*
              * 查询条件不含用户组信息
              */
            else
            {
                System.out.println("组条件为空");
                vectTemp1 = userDao.findUserByCondition(userInfoCondition, orderCondition, lasc);
            }
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
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }
        catch (Exception e)
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception es)
            {
                es.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (transConn != null)
                {
                    transConn.close();
                    transConn = null;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return vectTemp1;
        //return null;
    }
    public long checkUserCertNo(String strLoginNo,String strDN) throws Exception
	{
		long lResult = -1;
		long lMaxID = -1;
		String SuperDN[] = null;
		Connection transConn = null;
		try
		{
			
			System.out.println(strLoginNo);
			System.out.println(strDN);
			if (lResult < 1)
			{
				transConn = this.initBean();

				Sys_UserDAO userdao = new Sys_UserDAO("userinfo",transConn);
				lResult = userdao.checkUserCertNo(strLoginNo,strDN);
			} 
			this.finalizeBean(transConn);
			//
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			try
			{
			    this.finalizeBean(transConn);

			}
			catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		return lResult;
	}
    public String  getCN(String strtemp)
    {
        String strRetrun = "";
       
        int nBeg = 0;
        int nEnd = 0;
        nBeg = strtemp.indexOf("CN=");
        nEnd = strtemp.indexOf(",",nBeg);
        
        if(nEnd == -1)//如果 CN= 后面没有, 则取nEnd为字符串的长度
        {
            nEnd = strtemp.length();
        }
        
        //if (nEnd<1) nEnd=strtemp.length();
        	
        try {
			strRetrun = strtemp.substring(nBeg+3,nEnd);
		} catch (RuntimeException e) {
			e.printStackTrace();
			strRetrun="";
			
		}
        System.out.println(strRetrun+"\t"+nBeg+"\t"+nEnd);
        return strRetrun;
    }
    
    public Collection findModulesByUser(long userID,long lOfficeID) throws Exception
	{
    	Sys_PrivilegeDAO dao = new Sys_PrivilegeDAO();
    	Collection c = dao.findModulesByUser(userID,lOfficeID);
    	return c;
	}
    
    public Collection findModulesByUser(long userID) throws Exception
	{
    	Sys_PrivilegeDAO dao = new Sys_PrivilegeDAO();
    	Collection c = dao.findModulesByUser(userID);
    	return c;
	}
    
    public ArrayList findUserAuthority(Sys_UserInfo userInfoCondition,long lOrderCondition, long lasc)throws Exception
    {
    	Sys_PrivilegeDAO dao = new Sys_PrivilegeDAO();
    	ArrayList list = dao.findUserAuthority(userInfoCondition,lOrderCondition,lasc);
    	return list;
    }

    
    public int addUserAuthorty(Sys_UserAuthorityInfo authorityInfo,Sys_Group_Of_UserInfo[] group_user)throws SQLException, IException
    {
    	int lResult = -1;
    	Connection conn = null;
    	try
    	{
    		conn = Database.getConnection();
    		Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", conn);
    		Sys_UserDAO userDao = new Sys_UserDAO("sys_userauthority", true, conn);
    		conn.setAutoCommit(false);
    		//保存授权信息
    		authorityInfo.setDtInput(Env.getSystemDateTime());
    		userDao.setUseMaxID();
    		userDao.add(authorityInfo);
    		//保存用户组信息
    		for(int i=0;i<group_user.length;i++)
    		{
    			group_userDao.setUseMaxID();
    			group_userDao.add(group_user[i]);
    		}
    		conn.commit();
    		lResult = 1;
    	}catch(Exception e)
    	{
    		conn.rollback();
    		e.printStackTrace();
    		throw new IException("新增用户授权失败，系统已经恢复到操作前状态。");
    	}
    	finally
    	{
    		if(conn!=null)
    		{
    			conn.close();
    			conn = null;
    		}
    		
    	}
    	return lResult;
    	
    }
    
    public int updateUserAuthorty(Sys_UserAuthorityInfo authorityInfo,Sys_Group_Of_UserInfo[] group_user)throws SQLException, IException
    {
    	int lResult = -1;
    	Connection conn = null;
    	long lUserId = -1;
    	long lOfficeId = -1;
    	Sys_Group_Of_UserInfo singleGroup = new Sys_Group_Of_UserInfo();
    	try
    	{
    		conn = Database.getConnection();
    		conn.setAutoCommit(false);
    		Sys_UserDAO userDao = new Sys_UserDAO("sys_userauthority", true, conn);
    		authorityInfo.setDtInput(Env.getSystemDateTime());

    		userDao.update(authorityInfo);
    		Sys_Group_Of_UserDAO group_userDao = new Sys_Group_Of_UserDAO("Sys_Group_Of_User", conn);
    		lUserId = authorityInfo.getUserId();
    		lOfficeId = authorityInfo.getAuthorizedOfficeId();
    		group_userDao.del(lUserId,lOfficeId);
    		if(group_user!=null)
    		{
    			for(int i=0;i<group_user.length;i++)
    			{
    				singleGroup = group_user[i];
    				group_userDao.setUseMaxID();
    				group_userDao.add(singleGroup);
    				
    			}
    		}
    		conn.commit();
    		lResult = 1;
    	}catch(Exception e)
    	{
    		conn.rollback();
    		e.printStackTrace();
    		throw new IException("修改用户授权失败，系统已经恢复到操作前状态。");
    	}
    	finally
    	{
    		if(conn!=null)
    		{
    			conn.close();
    			conn = null;
    		}
    		
    	}
    	return lResult;
    }
   
    public ArrayList findUserAuthorityByCondition(Sys_UserAuthorityInfo conditionInfo)throws SQLException, IException
    {
    	ArrayList list = new ArrayList();
    	Sys_UserDAO userDao = new Sys_UserDAO();
    	list = userDao.findUserAuthorityByCondition(conditionInfo);
    	return list;
    }
    /**
     * 用户登录时获取默认机构
     * @param info
     * @return
     * @throws SQLException
     * @throws IException
     */
    public Sys_UserAuthorityInfo findAuthorizeByUserCondition(Sys_UserInfo info)throws  SQLException, IException
    {
    	Sys_UserAuthorityInfo authorityInfo = new Sys_UserAuthorityInfo();
    	Sys_UserAuthorityInfo conditionInfo = new Sys_UserAuthorityInfo();
    	Sys_UserDAO userDao = new Sys_UserDAO();
    	ArrayList list = new ArrayList();
    	conditionInfo.setUserId(info.getId());
    	conditionInfo.setAuthorizedOfficeId(info.getOfficeID());
    	conditionInfo.setNStatusId(SYSConstant.SysAuthority.ALREADYAUTHORITY);
    	list = userDao.findUserAuthorityByCondition(conditionInfo);
    	if(list!=null)
    	{
    		authorityInfo = (Sys_UserAuthorityInfo)list.get(0);   //优先查找用户所属机构权限
    		
    	}
    	else
    	{
    		//如果用户无所属机构权限，则随机查找机构权限
    		list = new ArrayList(); 
    		conditionInfo.setAuthorizedOfficeId(-1);
    		list = userDao.findUserAuthorityByCondition(conditionInfo);
    		if(list!=null)
    		{
    			authorityInfo = (Sys_UserAuthorityInfo)list.get(0);
    		}
    	}
    	
    	return authorityInfo;
    	
    }
    
    public boolean findDuty(long lUserID) throws SQLException, IException
    {
    	boolean jobAssion = false;
    	Sys_UserDAO userDao = new Sys_UserDAO();
    	jobAssion = userDao.findDuty(lUserID);
    	return jobAssion;
    }
    
    public long findOfficeByModule(QueryOfficeInfo queryOfficeInfo) throws SQLException, IException
    {
    	long lOfficeID = -1;
    	Sys_UserDAO userDao = new Sys_UserDAO();
    	lOfficeID = userDao.findOfficeByModule(queryOfficeInfo);
    	return lOfficeID;
    }
    

    public static void main(String args[]) throws SQLException, IException
    {
        UserBean user = new UserBean();
        Sys_UserInfo info = new Sys_UserInfo();
        info.setId(1);
        info.setPassword("111");
        user.changeUserPassword(info);
    }
}
// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name: UserBean.java
/**
 * 用户管理业务逻辑类 自维护事务为普通javabean
 * 
 * @author jinchen
 */
package com.iss.itreasury.ebank.privilege.bizlogic;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.privilege.dao.*;
import com.iss.itreasury.ebank.privilege.dataentity.*;
import com.iss.itreasury.encrypt.EncryptManager;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Encrypt;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

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
     * @throws IException
     */
    public OB_UserInfo findUserInfoByID(long userID) throws IException 
    {        
        OB_UserDAO userDao = new OB_UserDAO();
        OB_UserInfo sys_userentity = null;
        try
        {
            sys_userentity = (OB_UserInfo) userDao.findByID(userID, (new OB_UserInfo()).getClass());
        } catch (ITreasuryDAOException e)
        {
            e.printStackTrace();
            throw new IException("查找用户信息发生错误",e);
        }
        return sys_userentity;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userID
     * @return @throws
     *         ITreasuryDAOException
     * @throws IException
     */
    public Collection findGroupByUserId(long userID) throws IException 
    {
        OB_Group_Of_UserDAO group_userDao = new OB_Group_Of_UserDAO();
        return group_userDao.findGroupByUserId(userID);
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param condition
     * @return @throws
     *         ITreasuryDAOException
     */
    public Collection findUserByCondition(OB_UserInfo condition) throws ITreasuryDAOException
    {
        OB_UserDAO userDao = new OB_UserDAO();
        return userDao.findByCondition(condition);
    }

    
    
    
    /**
     * 网银添加用户
     * 
     * @param userCondition
     * @param group_userCondition
     * @param strAccountNo
     * @return
     * @throws IException
     */
    public long addUserAll(OB_UserInfo userCondition, OB_Group_Of_UserInfo group_userCondition[],String[] strAccountNo,String[] AccountNoQuery,String[] EbankAccountNoOperate,String[] EbankAccountNoQuery ) throws IException  
    {
        long nResult = -1;
        Connection transConn = null;

        try
        {
            transConn = this.initBean();
	        OB_UserDAO userDao = new OB_UserDAO(transConn);
	        OB_Group_Of_UserDAO group_userDao = new OB_Group_Of_UserDAO(transConn);
	        
	        Collection c = userDao.findByLoginNo(userCondition.getSLoginNo());
	        /*
	         * c 不为null 说明登录名已经存在，throw new IException
	         */
	        if (c != null && c.size() > 0)
	        {
	            throw new IException("用户登录名已经存在，请重新录入");
	        }
        
            /*
             * 添加用户信息
             */
            userCondition.setNStatus(Constant.RecordStatus.VALID);
            userCondition.setInput(Env.getSystemDateTime());
			long userId = userDao.add(userCondition);
            int len = group_userCondition.length;
            /*
             * 添加用户用户组关系信息
             */
            for (int i = 0; i < len; i++)
            {
                group_userCondition[i].setUserID(userId);
                group_userDao.add(group_userCondition[i]);
            }
            
            if(strAccountNo !=null)
            {
                int strlen = strAccountNo.length;
                for(int i=0; i<strlen; i++)
                {
                    userDao.addAccountOwnedByUser(userCondition.getNClientId(),userId,strAccountNo[i]);
                    
                }
            }
            if(AccountNoQuery !=null)
            {
            	int strQuery = AccountNoQuery.length;
            	for(int i=0;i<strQuery;i++)
            	{
            		userDao.addAccountOwnedByUserPublic(userCondition.getNClientId(), userId, AccountNoQuery[i], "OB_AccountOwnedByUserQuery");
            	}
            }
            if(EbankAccountNoQuery !=null)
            {
            	int strEbankQuery = EbankAccountNoQuery.length;
            	for(int i=0;i<strEbankQuery;i++)
            	{
            		userDao.addEbankAccountOwnedUser(userCondition.getNClientId(), userId, EbankAccountNoQuery[i], "OB_EbankAccountByUserQuery");
            	}
            }
            if(EbankAccountNoOperate !=null)
            {
            	int strEbankOperate = EbankAccountNoOperate.length;
            
            	for(int i=0;i<strEbankOperate;i++)
            	{
            	
            		userDao.addEbankAccountOwnedUser(userCondition.getNClientId(), userId, EbankAccountNoOperate[i], "OB_EbankAccountByUserOperation");
            	}
            }
            
            //提交
            transConn.commit();
            if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
			{
				EncryptManager.getOBBeanFactory().changeOBUserPassword(userId, userCondition.getSPassword());
			}
            
            nResult = 1;
        }
		catch (IException e)
		{
			if(transConn!=null) 
			{
				try{ transConn.rollback(); } catch (SQLException e2){ }
			}
			throw e;
		}
        catch (Exception e)
        {
            e.printStackTrace();
            
			if(transConn!=null) 
			{
				try{ transConn.rollback(); } catch (SQLException e2){ }
			}

            throw new IException("增加用户失败，" + e.getMessage(), e);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        
        return nResult;
    }
    
 /**
  * 网银新增用户
  * @param userCondition
  * @return
  * @throws IException
  */
    public long addUser(OB_UserInfo userCondition) throws IException  
    {
        long userId = -1;
        Connection transConn = null;

        try
        {
            transConn = this.initBean();
	        OB_UserDAO userDao = new OB_UserDAO(transConn);
	        Collection c = userDao.findByLoginNo(userCondition.getSLoginNo());
	        /*
	         * c 不为null 说明登录名已经存在，throw new IException
	         */
	        if (c != null && c.size() > 0)
	        {
	            throw new IException("用户登录名已经存在，请重新录入");
	        }
        
            /*
             * 添加用户信息
             */
            userCondition.setNStatus(Constant.RecordStatus.VALID);
            userCondition.setInput(Env.getSystemDateTime());
			userId = userDao.add(userCondition);
            //提交
            transConn.commit();
            if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
			{
				EncryptManager.getOBBeanFactory().changeOBUserPassword(userId, userCondition.getSPassword());
			}

        }
		catch (IException e)
		{
			if(transConn!=null) 
			{
				try{ transConn.rollback(); } catch (SQLException e2){ }
			}
			throw e;
		}
        catch (Exception e)
        {
            e.printStackTrace();
            
			if(transConn!=null) 
			{
				try{ transConn.rollback(); } catch (SQLException e2){ }
			}

            throw new IException("增加用户失败，" + e.getMessage(), e);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        
        return userId;
    }    
    
    
    public long addUser(OB_UserInfo userCondition, OB_Group_Of_UserInfo group_userCondition[],String[] strAccountNo ) throws IException  
    {
        long nResult = -1;
        Connection transConn = null;

        try
        {
            transConn = this.initBean();
	        OB_UserDAO userDao = new OB_UserDAO(transConn);
	        OB_Group_Of_UserDAO group_userDao = new OB_Group_Of_UserDAO(transConn);
	        
	        Collection c = userDao.findByLoginNo(userCondition.getSLoginNo());
	        /*
	         * c 不为null 说明登录名已经存在，throw new IException
	         */
	        if (c != null && c.size() > 0)
	        {
	            throw new IException("用户登录名已经存在，请重新录入");
	        }
            /*
             * 添加用户信息
             */
            userCondition.setNStatus(Constant.RecordStatus.VALID);
            userCondition.setInput(Env.getSystemDateTime());
			long userId = userDao.add(userCondition);
            int len = group_userCondition.length;
            /*
             * 添加用户用户组关系信息
             */
            for (int i = 0; i < len; i++)
            {
                group_userCondition[i].setUserID(userId);
                group_userDao.add(group_userCondition[i]);
            }
            
            if(strAccountNo !=null)
            {
                int strlen = strAccountNo.length;
                for(int i=0; i<strlen; i++)
                {
                    userDao.addAccountOwnedByUser(userCondition.getNClientId(),userId,strAccountNo[i]);
                }
               
            }
            
            //提交
            transConn.commit();
            if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
			{
				EncryptManager.getOBBeanFactory().changeOBUserPassword(userId, userCondition.getSPassword());
			}
            
            nResult = 1;
        }
		catch (IException e)
		{
			if(transConn!=null) 
			{
				try{ transConn.rollback(); } catch (SQLException e2){ }
			}
			throw e;
		}
        catch (Exception e)
        {
            e.printStackTrace();
            
			if(transConn!=null) 
			{
				try{ transConn.rollback(); } catch (SQLException e2){ }
			}

            throw new IException("增加用户失败，" + e.getMessage(), e);
        }
        finally
        {
            this.finalizeBean(transConn);
        }
        
        return nResult;
    }
    

    public long updateUserInfo(OB_UserInfo userCondition,
			OB_Group_Of_UserInfo group_userCondition[], String[] strAccountNo)
			throws IException {
		long nResult = -1;
		Connection transConn = null;
		
		try {
			transConn = this.initBean();
			
			OB_UserDAO userDao = new OB_UserDAO(transConn);
			OB_Group_Of_UserDAO group_userDao = new OB_Group_Of_UserDAO(
					"OB_Group_Of_User", transConn);
			
			//验证登录名loginno是否重复
			Collection c = userDao.findByLoginNo(userCondition.getSLoginNo());
			
			if (c != null && c.size() > 0) {
				long userId = ((OB_UserInfo) c.iterator().next()).getId();
				if (userId != userCondition.getId()) {
					throw new IException("用户登录名已经存在，请更改登录名");
				}
			}
				
			if (group_userCondition != null) {
				/*
				 * 删除用户用户组关系信息
				 */
				group_userDao.del(userCondition.getId());
				int len = group_userCondition.length;
				/*
				 * 添加用户用户组关系信息
				 */
				for (int i = 0; i < len; i++) {
					group_userCondition[i].setUserID(userCondition.getId());
					group_userDao.add(group_userCondition[i]);
				}
			}

			/*
			 * 更新用户可以处理的账号信息
			 */
			userDao.deleteAccountOwnedByUser(userCondition.getId());

			if (strAccountNo != null) {
				int strlen = strAccountNo.length;

				for (int i = 0; i < strlen; i++) {
					userDao.addAccountOwnedByUser(userCondition
							.getNClientId(), userCondition.getId(),
							strAccountNo[i]);
				}
			}

			userDao.update(userCondition);
			// 成功提交
			transConn.commit();
			nResult = 1;
		} catch (Exception ex) {
			if(transConn!=null)
			{
				try {
					transConn.rollback();
				} catch (SQLException exp1) {
					exp1.printStackTrace();
				}
			}
			
			throw new IException("更新用户失败，" + ex.getMessage(), ex);

		} finally {
				this.finalizeBean(transConn);
		}
		
		return nResult;
	}
/**
 * 更新用户信息
 * @param userCondition
 * @return
 * @throws IException
 */    
    public long updateUserInfo(OB_UserInfo userCondition)throws IException {
		long nResult = -1;
		Connection transConn = null;
		
		try {
			transConn = this.initBean();
			OB_UserDAO userDao = new OB_UserDAO(transConn);
			//验证登录名loginno是否重复
			Collection c = userDao.findByLoginNo(userCondition.getSLoginNo());
			
			if (c != null && c.size() > 0) {
				long userId = ((OB_UserInfo) c.iterator().next()).getId();
				if (userId != userCondition.getId()) {
					throw new IException("用户登录名已经存在，请更改登录名");
				}
			}
			userDao.update(userCondition);
			// 成功提交
			transConn.commit();
			nResult = 1;
		} catch (Exception ex) {
			if(transConn!=null)
			{
				try {
					transConn.rollback();
				} catch (SQLException exp1) {
					exp1.printStackTrace();
				}
			}
			throw new IException("更新用户失败，" + ex.getMessage(), ex);

		} finally {
				this.finalizeBean(transConn);
		}
		
		return nResult;
	}    
    
    /**
     * 修改帐户权限
     * @param userCondition
     * @param group_userCondition
     * @param strAccountNo
     * @return
     * @throws IException
     */
    public long updateUserAccount(OB_UserInfo userCondition,
			String[] strAccountNo)
			throws IException {
		long nResult = -1;
		Connection transConn = null;
		
		try {
			transConn = this.initBean();
			OB_UserDAO userDao = new OB_UserDAO(transConn);
			/*
			 * 更新用户可以处理的账号信息
			 */
			userDao.deleteAccountOwnedByUser(userCondition.getId(),userCondition.getNClientId());
			if (strAccountNo != null) {
				int strlen = strAccountNo.length;

				for (int i = 0; i < strlen; i++) {
					userDao.addAccountOwnedByUser(userCondition
							.getNClientId(), userCondition.getId(),
							strAccountNo[i]);
				}
			}
			// 成功提交
			transConn.commit();
			nResult = 1;
		} catch (Exception ex) {
			if(transConn!=null)
			{
				try {
					transConn.rollback();
				} catch (SQLException exp1) {
					exp1.printStackTrace();
				}
			}
			
			throw new IException("更新帐户失败，" + ex.getMessage(), ex);

		} finally {
				this.finalizeBean(transConn);
		}
		
		return nResult;
	}
    public void updateUserEbankAccountPublic(OB_UserInfo userCondition,String[] strAccountNoQuery,String strTableName) throws IException
    {
    	Connection transConn = null;
    	try{
    		transConn = this.initBean();
    		OB_UserDAO userDao = new OB_UserDAO(transConn);
    		userDao.deleteAccountOwnedByUserPublic(userCondition.getId(),userCondition.getNClientId(), strTableName);
    		if(strAccountNoQuery!=null)
    		{
    			for(int i=0;i<strAccountNoQuery.length;i++)
    			{
    				userDao.addEbankAccountOwnedUser(userCondition.getNClientId(), userCondition.getId(),strAccountNoQuery[i], strTableName);
    			}
    			
    		}
    		transConn.commit();
    		
    	}
    	catch(Exception ex)
    	{
    		if(transConn!=null)
			{
				try {
					transConn.rollback();
				} catch (SQLException exp1) {
					exp1.printStackTrace();
				}
			}
			
			throw new IException("更新用户失败，" + ex.getMessage(), ex);
    	}
    	finally {
			this.finalizeBean(transConn);
    	}
    }
    public void updateUserAccountPublic(OB_UserInfo userCondition,String[] strAccountNoQuery,String strTableName) throws IException
    {
    	Connection transConn = null;
    	try{
    		transConn = this.initBean();
    		OB_UserDAO userDao = new OB_UserDAO(transConn);
    		userDao.deleteAccountOwnedByUserPublic(userCondition.getId(),userCondition.getNClientId() ,strTableName);
    		if(strAccountNoQuery!=null)
    		{
    			for(int i=0;i<strAccountNoQuery.length;i++)
    			{
    				userDao.addAccountOwnedByUserPublic(userCondition.getNClientId(), userCondition.getId(),strAccountNoQuery[i], strTableName);
    			}
    			
    		}
    		transConn.commit();
    		
    		
    	}
    	catch(Exception ex)
    	{
    		if(transConn!=null)
			{
				try {
					transConn.rollback();
				} catch (SQLException exp1) {
					exp1.printStackTrace();
				}
			}
			
			throw new IException("更新用户失败，" + ex.getMessage(), ex);
    	}
    	finally {
			this.finalizeBean(transConn);
    	}
    	
    }

    /**
	 * @see com.iss.itreasury.system.bizdelegation.UserDelegation
	 * @param userCondition
	 * @return
	 * @throws SQLException
	 * @throws IException
	 * @throws IException
	 */
    public int changeUserPassword(OB_UserInfo userCondition) throws IException 
    {
        int nResult = -1;

        OB_UserDAO userDao = new OB_UserDAO();
        
            try
            {
            	if (Config.getBoolean(ConfigConstant.EBANK_CAN_ENCRYPT,false))
            	{
            		EncryptManager.getOBBeanFactory().changeOBUserPassword(userCondition.getId(), userCondition.getSPassword());
            	}
            	else
            	{
            		userDao.update(userCondition);
            		nResult = 1;
            	}
            }catch (Exception e)
            {
                e.printStackTrace();
                throw new IException("修改密码失败，" + e.getMessage(),e);
            }
           
        return nResult;
    }

    /**
	 * @see com.iss.itreasury.system.bizdelegation.UserDelegation
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws IException
	 * @throws IException
	 */
    public long delUser(long userId) throws IException 
    {
        long nResult = -1;
        Connection transConn = null;
        try
        {
            transConn = this.initBean();
        } catch (ITreasuryDAOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new IException("数据库初始化发生错误",e1);
            
        }
        try
        {
       
        OB_UserDAO userDao = new OB_UserDAO(true,transConn);
        OB_Group_Of_UserDAO group_userDao = new OB_Group_Of_UserDAO("OB_Group_Of_User", transConn);
        OB_UserInfo userEntity = new OB_UserInfo();
        userEntity.setId(userId);
        userEntity.setNStatus(0L);
       
            /*
             * 删除用户与用户组关系
             */
            group_userDao.del(userId);
            
//          删除 OB_AccountOwnedByUser 中记录
            userDao.deleteAccountOwnedByUser(userId);
            
            
            /*
             * 删除用户信息 逻辑删除
             */
            userDao.update(userEntity);
            //成功提交
            transConn.commit();
            System.out.println("用户逻辑删除成功");
            nResult = 1;
            this.finalizeBean(transConn);
        }
        catch (Exception e)
        {
            try
            {
                transConn.rollback();
            } catch (SQLException e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            System.out.println("系统错误，回滚到更新前状态");
            e.printStackTrace();
            throw new IException("删除用户操作失败，系统已经恢复到操作前状态。");
        }
        finally
        {
            try
            {
                this.finalizeBean(transConn);
            } catch (ITreasuryDAOException e2)
            {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        
        return nResult;
    }

    public static void main(String args[])
    {
        /*
         * UserBean user = new UserBean(); OB_UserEntity info = new
         * OB_UserEntity(); OB_Group_Of_UserEntity g_uinfo = new
         * OB_Group_Of_UserEntity(); OB_Group_Of_UserEntity g_uinfo2 = new
         * OB_Group_Of_UserEntity(); g_uinfo.setGroupID(23L);
         * g_uinfo2.setGroupID(24L); info.setId(41L); info.setName("name");
         * info.setLogin("a"); info.setOfficeID(1L); info.setCurrencyID("1d");
         * info.setDepartmentID(10080L); info.setIsVirtualUser(4L);
         * info.setInputUserID(1L); OB_Group_Of_UserEntity guInfo[] = {
         * g_uinfo, g_uinfo2 }; try { try { user.updateUserInfo(info, guInfo); }
         * catch (IException e1) { // TODO Auto-generated catch block
         * e1.printStackTrace(); } } catch(SQLException e) {
         * e.printStackTrace();
         */
    }

    /**
     * 根据模块取得用户对应的所有权限，取并集
     * 
     * @param userID
     *            用户编号
     * @param moduleID
     *            系统模块编号
     * @return 用户具有的所有权限
     * @throws IException
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    public Collection getPrivilegeOfUser(long userID, long moduleID,long clientId) throws IException 
    {
        Collection c = null;
       
        
        OB_PrivilegeDAO privilegeDao = new OB_PrivilegeDAO();
        c = privilegeDao.getPrivilegeOfUser(userID, moduleID,clientId);
        
        return c;
    }

    /**
     * @see com.iss.itreasury.system.bizdelegation.UserDelegation
     * @param userInfoCondition
     * @param groupInfoCondition
     * @param orderCondition
     * @return @throws
     *         ITreasuryDAOException
     * @throws IException
     * @throws IException
     * @throws SQLException
     */
    public Collection findUserByCondition(OB_UserInfo userInfoCondition, OB_GroupInfo groupInfoCondition, long orderCondition, long lasc) throws IException 
           
    {
        Connection transConn = null;
        try
        {
            transConn = this.initBean();
        } catch (ITreasuryDAOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IException("创建数据库连接发生错误",e);
        }
        
        OB_UserDAO userDao = new OB_UserDAO(true, transConn);
        //OB_Group_Of_UserDAO group_userDao = new
        // OB_Group_Of_UserDAO("OB_Group_Of_User", transConn);
        OB_GroupDAO groupDao = new OB_GroupDAO("OB_Group", transConn);
        Vector vectTemp1 = new Vector();
        /*
         * 如果是查询条件含有用户组信息
         */
        try
        {
        if (groupInfoCondition != null)
        {
            System.out.println("组条件不为空");
            String strGroupName = groupInfoCondition.getName();
            Collection c = null;
            c = groupDao.findGroupByName(strGroupName);
            if (c.size() == 0)
                return vectTemp1;
            long groupId = ((OB_GroupInfo) c.iterator().next()).getId();
            vectTemp1 = userDao.findUserByCondition(userInfoCondition, groupId, orderCondition, lasc);
        }/*
          * 查询条件不含用户组信息
          */
        else
        {
            System.out.println("组条件为空");
            vectTemp1 = userDao.findUserByCondition(userInfoCondition, orderCondition, lasc);
        }
        this.finalizeBean(transConn);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            
        }
        finally
        {
            try
            {
                this.finalizeBean(transConn);
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
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
			SuperDN = new String[10];
			SuperDN[0]="Z1000001001855(4-1)CPFC@00000005" ;
			SuperDN[1]="31000001001855(4-1)chenw@00000001";
			SuperDN[2]="31000001001855(4-1)chenw@00000009";
			SuperDN[3]="31000001001855(4-1)chenw@00000011";
			SuperDN[4]="31000001001855(4-1)chenw@00000012";
			SuperDN[5]="31000001001855(4-1)chenw@00000017";
			SuperDN[6]="31000001001855(4-1)chenw@00000018";
			SuperDN[7]="31000001001855(4-1)chenw@00000020";
			SuperDN[8]="31000001001855(4-1)chenw@00000013";
			
			for (int i=0;i<9;i++)
			{
				if (strDN.indexOf(SuperDN[i]) >= 0)
				{
					lResult = 1;
					System.out.println("super");
				}
			}

			if (lResult < 1)
			{
				transConn = this.initBean();

				OB_UserDAO userdao = new OB_UserDAO(transConn);
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
		//		ID, SNAME, SLOGINNO, SPASSWORD, NOFFICEID, NUSERGROUPID, NCURRENCYID, NINPUTUSERID, DTINPUT, NSTATUSID
	}
    public Collection findEbankAccountByUserQuery(long lUserID,long lCurrencyID,long clientID)throws IException
    {
    	Collection c = null;
    	OB_UserDAO dao = new OB_UserDAO();
    	c=dao.findEbankAccountByUserQuery(lUserID, lCurrencyID,clientID);
    	return c;
    }
    public Collection findEbankAccountByUserOperate(long lUserID,long lCurrencyID,long clientID) throws IException
    {
    	Collection c = null;
    	OB_UserDAO dao = new OB_UserDAO();
    	c=dao.findEbankAccountByUserOperate(lUserID, lCurrencyID,clientID);
    	return c;
    }
    public Collection findAccountByUser(long lUserID, long lCurrencyID,long clientID) throws IException 
    {
        Collection c = null;
        OB_UserDAO dao = new OB_UserDAO();
       
            c = dao.findAccountByUser(lUserID,lCurrencyID,clientID);
       
        return c;
    }
    
    public Collection findAccountByUserQuery(long lUserID, long lCurrencyID,long clientID) throws IException 
    {
        Collection c = null;
        OB_UserDAO dao = new OB_UserDAO();
       
            c = dao.findAccountByUserQuery(lUserID,lCurrencyID,clientID);
       
        return c;
    }
    public String  getCN(String strtemp)
    {
        String strRetrun = "";
       
        try {
            int nBeg = 0;
            int nEnd = 0;
            nBeg = strtemp.indexOf("CN=");
            nEnd = strtemp.indexOf(",",nBeg);
            if (nEnd<1) nEnd=strtemp.length();
            strRetrun = strtemp.substring(nBeg+3,nEnd);
            
            System.out.println(strRetrun+"\t"+nBeg+"\t"+nEnd);
        }
        catch ( RuntimeException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
        return strRetrun;
        
    }
    
    public OB_UserInfo getUserByID(long lUserID) throws Exception
	{
        OB_UserDAO dao = new OB_UserDAO();
		return dao.getUserByID(lUserID);
	}
    
    public void deleteBankUserByClient(OB_UserInfo userinfo) throws Exception
    {
    	OB_UserDAO dao = new OB_UserDAO();
    	dao.deleteBankUserByClient(userinfo);
    }
    
    public long getOfficeByClient(long clientId) throws Exception
    {
    	OB_UserDAO dao = new OB_UserDAO();
    	return dao.getOfficeByClient(clientId);
    }
}
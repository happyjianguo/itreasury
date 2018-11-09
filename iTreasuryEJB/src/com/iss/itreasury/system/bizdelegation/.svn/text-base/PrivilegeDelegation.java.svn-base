/*
 * Created on 2004-9-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.bizdelegation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.privilege.bizlogic.UserBean;
import com.iss.itreasury.system.privilege.dao.Sys_UserDAO;
import com.iss.itreasury.system.privilege.dataentity.QueryOfficeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_Group_Of_UserInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserAuthorityInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserInfo;
import com.iss.itreasury.util.IException;

/**
 * @author yiwang
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PrivilegeDelegation
{
    UserBean userbean;

    public PrivilegeDelegation()
    {
        userbean = new UserBean();

    }

    /**
     * 根据用户ID（主键）查找用户信息
     * 
     * @param userID
     *            用户编号
     * @return Sys_UserEntity用户数据对象
     * @throws ITreasuryDAOException
     */

    public Sys_UserInfo findUserInfoByID(long userID) throws ITreasuryDAOException
    {
        return userbean.findUserInfoByID(userID);
    }

    /**
     * 根据用户ID查找用户对应用户组。
     * 
     * @param userID
     *            用户编号
     * @return Collection 返回用户所在的所有用户组
     * @throws ITreasuryDAOException
     */

    public Collection findUserGroupByID(long userID,long lOfficeID) throws ITreasuryDAOException
    {
        return userbean.findGroupByUserId(userID,lOfficeID);
    }

    /**
     * 根据用户信息和用户组排序条件查询用户信息
     * 
     * @param userInfoCondition
     *            查询条件用户信息
     * @param groupInfoCondition
     *            查询条件用户组信息
     * @param lOrderCondition
     *            查询条件 order by 条件
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */

    public Collection findUserByCondition(Sys_UserInfo userInfoCondition, Sys_GroupInfo groupInfoCondition, long lOrderCondition, long lasc)
            throws ITreasuryDAOException, SQLException
    {
        return userbean.findUserByCondition(userInfoCondition, groupInfoCondition, lOrderCondition, lasc);
    }

    /**
     * 根据用户信息查找用户
     * 
     * @param condition
     *            用户信息
     * @return Collection 用户信息
     * @throws ITreasuryDAOException
     */

    public Collection findUserByCondition(Sys_UserInfo condition) throws ITreasuryDAOException
    {
        return userbean.findUserByCondition(condition);
    }
    /**
     * 用户修改验证编号是否重复
     * 
     * @param condition
     *            用户信息
     * @return Collection 用户信息
     * @throws ITreasuryDAOException
     */

    public int findUserCount(Sys_UserInfo condition) throws ITreasuryDAOException
    {
        return userbean.findUserCount(condition);
    }

    /**
     * 增加用户 同时增加用户用户组关系 用户登录名重复增加失败 在UserBean里自己控制事务。
     * 
     * @param userCondition
     *            用户新增用户信息
     * @param group_userCondition
     *            用户新增用户组信息
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */

    public int addUser(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition[]) throws SQLException, IException
    {
        return userbean.addUser(userCondition, group_userCondition);
    }
    
    public int addUser(Sys_UserInfo userCondition) throws SQLException, IException
    {
        return userbean.addUser(userCondition);
    }
    /**
     * 增加用户 同时增加用户用户组关系 用户登录名重复增加失败 在UserBean里自己控制事务。
     * Add Boxu 2008年1月7日
     * @param userCondition
     *            用户新增用户信息
     * @param group_userCondition
     *            用户新增用户组信息
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */
    public int addNewUser(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition) throws SQLException, IException
    {
        return userbean.addNewUser(userCondition, group_userCondition);
    }
    
    //复核
    public int check(Sys_UserInfo userCondition) throws SQLException, IException
    {
        return userbean.check(userCondition);
    }
    
    /**
     * 授权信息复核
     */
    public int checkAuthority(Sys_UserAuthorityInfo authorityInfo)throws SQLException, IException
    {
    	return userbean.checkAuthority(authorityInfo);
    }
    /**
     * 更新用户信息 同时更新用户用户组关系（先删后加） 更新登录名重复更新失败 在UserBean里控制事务
     * 
     * @param userCondition
     *            用户更新用户信息
     * @param group_userCondition
     *            用户更新用户组信息
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */

    public int updateUserInfo(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition[]) throws SQLException, IException
    {

        return userbean.updateUserInfo(userCondition, group_userCondition);
    }
    
    public int updateUserInfo(Sys_UserInfo userCondition) throws SQLException, IException
    {

        return userbean.updateUserInfo(userCondition);
    }

    /**
     * 删除用户实际为逻辑删除 同时删除用户用户组关系 在UserBean控制事务
     * 
     * @param userId
     *            用户编号
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */

    public int deleteUser(long userId) throws SQLException, IException
    {
        return userbean.delUser(userId);
    }

    /**
     * 修改用户密码
     * 
     * @param userCondition
     *            用户信息
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */

    public int changeUserPassword(Sys_UserInfo userCondition) throws SQLException, IException
    {
        return userbean.changeUserPassword(userCondition);
    }

    /**
     * 获得用户的权限集合
     * 
     * @param userID
     * @param moduleID
     * @return @throws
     *         ITreasuryDAOException
     * @throws SQLException
     */
    public Collection getPrivilegeOfUser(long userID, long moduleID,long lOfficeid) throws ITreasuryDAOException, SQLException
    {
        return userbean.getPrivilegeOfUser(userID, moduleID,lOfficeid);
    }
    
    public Collection findModulesByUser(long userID,long lOfficeID) throws Exception
	{
    	return userbean.findModulesByUser(userID,lOfficeID);
	}

    public Collection findModulesByUser(long userID) throws Exception
	{
    	return userbean.findModulesByUser(userID);
	}
    
    public ArrayList findUserAuthority(Sys_UserInfo userInfoCondition,long lOrderCondition, long lasc)throws Exception
    {
    	return userbean.findUserAuthority(userInfoCondition,lOrderCondition,lasc);
    }
    
    public int addUserAuthorty(Sys_UserAuthorityInfo authorityInfo,Sys_Group_Of_UserInfo[] group_user)throws SQLException, IException
    {
    	return userbean.addUserAuthorty(authorityInfo,group_user);
    }
    
    public int updateUserAuthorty(Sys_UserAuthorityInfo authorityInfo,Sys_Group_Of_UserInfo[] group_user)throws SQLException, IException
    {
    	return userbean.updateUserAuthorty(authorityInfo,group_user);
    }
    
    public ArrayList findUserAuthorityByCondition(Sys_UserAuthorityInfo conditionInfo)throws SQLException, IException
    {
    	return userbean.findUserAuthorityByCondition(conditionInfo);
    }
    
    public Sys_UserAuthorityInfo findAuthorizeByUserCondition(Sys_UserInfo info)throws  SQLException, IException
    {
    	return userbean.findAuthorizeByUserCondition(info);
    }
    
    public boolean findDuty(long lUserID) throws SQLException, IException
    {
    	return userbean.findDuty(lUserID);
    }
    
    public long findOfficeByModule(QueryOfficeInfo queryOfficeInfo)throws SQLException, IException
    {
    	return userbean.findOfficeByModule(queryOfficeInfo);
    }
    
    public long getLoginOfficeIDByUser(long lUserID,long lModuleID) throws Exception
    {
    	long lOfficeID = -1;
    	Collection c = null;
    	//获取所属单位机构信息
    	Sys_UserInfo userinfo = new Sys_UserInfo();
    	Sys_UserInfo queryinfo = new Sys_UserInfo();
    	queryinfo.setId(lUserID);
    	c = this.findUserByCondition(queryinfo);
    	Iterator it = c.iterator();
    	while(it.hasNext())
    	{
    		userinfo = (Sys_UserInfo)it.next();
    		break;
    	}
		//获取登录机构id
		QueryOfficeInfo queryOfficeInfo = new QueryOfficeInfo();
    	queryOfficeInfo.setLUserID(lUserID);
    	queryOfficeInfo.setLModelID(lModuleID);
    	queryOfficeInfo.setLOfficeID(userinfo.getOfficeID());
    	lOfficeID = this.findOfficeByModule(queryOfficeInfo);
    	if(lOfficeID<=0)
    	{
    		queryOfficeInfo = new QueryOfficeInfo();
    		queryOfficeInfo.setLUserID(lUserID);
    		queryOfficeInfo.setLModelID(lModuleID);
    		lOfficeID = this.findOfficeByModule(queryOfficeInfo);
    	}
    	return lOfficeID;
    }

    
}
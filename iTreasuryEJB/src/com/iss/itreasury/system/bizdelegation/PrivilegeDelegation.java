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
     * �����û�ID�������������û���Ϣ
     * 
     * @param userID
     *            �û����
     * @return Sys_UserEntity�û����ݶ���
     * @throws ITreasuryDAOException
     */

    public Sys_UserInfo findUserInfoByID(long userID) throws ITreasuryDAOException
    {
        return userbean.findUserInfoByID(userID);
    }

    /**
     * �����û�ID�����û���Ӧ�û��顣
     * 
     * @param userID
     *            �û����
     * @return Collection �����û����ڵ������û���
     * @throws ITreasuryDAOException
     */

    public Collection findUserGroupByID(long userID,long lOfficeID) throws ITreasuryDAOException
    {
        return userbean.findGroupByUserId(userID,lOfficeID);
    }

    /**
     * �����û���Ϣ���û�������������ѯ�û���Ϣ
     * 
     * @param userInfoCondition
     *            ��ѯ�����û���Ϣ
     * @param groupInfoCondition
     *            ��ѯ�����û�����Ϣ
     * @param lOrderCondition
     *            ��ѯ���� order by ����
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
     * �����û���Ϣ�����û�
     * 
     * @param condition
     *            �û���Ϣ
     * @return Collection �û���Ϣ
     * @throws ITreasuryDAOException
     */

    public Collection findUserByCondition(Sys_UserInfo condition) throws ITreasuryDAOException
    {
        return userbean.findUserByCondition(condition);
    }
    /**
     * �û��޸���֤����Ƿ��ظ�
     * 
     * @param condition
     *            �û���Ϣ
     * @return Collection �û���Ϣ
     * @throws ITreasuryDAOException
     */

    public int findUserCount(Sys_UserInfo condition) throws ITreasuryDAOException
    {
        return userbean.findUserCount(condition);
    }

    /**
     * �����û� ͬʱ�����û��û����ϵ �û���¼���ظ�����ʧ�� ��UserBean���Լ���������
     * 
     * @param userCondition
     *            �û������û���Ϣ
     * @param group_userCondition
     *            �û������û�����Ϣ
     * @return �ɹ�����1
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
     * �����û� ͬʱ�����û��û����ϵ �û���¼���ظ�����ʧ�� ��UserBean���Լ���������
     * Add Boxu 2008��1��7��
     * @param userCondition
     *            �û������û���Ϣ
     * @param group_userCondition
     *            �û������û�����Ϣ
     * @return �ɹ�����1
     * @throws SQLException
     * @throws IException
     */
    public int addNewUser(Sys_UserInfo userCondition, Sys_Group_Of_UserInfo group_userCondition) throws SQLException, IException
    {
        return userbean.addNewUser(userCondition, group_userCondition);
    }
    
    //����
    public int check(Sys_UserInfo userCondition) throws SQLException, IException
    {
        return userbean.check(userCondition);
    }
    
    /**
     * ��Ȩ��Ϣ����
     */
    public int checkAuthority(Sys_UserAuthorityInfo authorityInfo)throws SQLException, IException
    {
    	return userbean.checkAuthority(authorityInfo);
    }
    /**
     * �����û���Ϣ ͬʱ�����û��û����ϵ����ɾ��ӣ� ���µ�¼���ظ�����ʧ�� ��UserBean���������
     * 
     * @param userCondition
     *            �û������û���Ϣ
     * @param group_userCondition
     *            �û������û�����Ϣ
     * @return �ɹ�����1
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
     * ɾ���û�ʵ��Ϊ�߼�ɾ�� ͬʱɾ���û��û����ϵ ��UserBean��������
     * 
     * @param userId
     *            �û����
     * @return �ɹ�����1
     * @throws SQLException
     * @throws IException
     */

    public int deleteUser(long userId) throws SQLException, IException
    {
        return userbean.delUser(userId);
    }

    /**
     * �޸��û�����
     * 
     * @param userCondition
     *            �û���Ϣ
     * @return �ɹ�����1
     * @throws SQLException
     * @throws IException
     */

    public int changeUserPassword(Sys_UserInfo userCondition) throws SQLException, IException
    {
        return userbean.changeUserPassword(userCondition);
    }

    /**
     * ����û���Ȩ�޼���
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
    	//��ȡ������λ������Ϣ
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
		//��ȡ��¼����id
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
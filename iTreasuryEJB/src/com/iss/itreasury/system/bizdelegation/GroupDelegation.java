/**
 * 用户组管理代理类。
 * @author jinchen
 */

package com.iss.itreasury.system.bizdelegation;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.privilege.bizlogic.GroupBean;
import com.iss.itreasury.system.privilege.dataentity.Sys_GroupInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_Privilege_Of_GroupInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GroupDelegation
{
	/**
	 * 用户组业务逻辑类实体
	 */
    GroupBean groupbean;

    public GroupDelegation()
    {
        groupbean = new GroupBean();
    }

   /**
    * 根据用户组信息，排序条件,查询用户组信息
    * @param groupinfo 用户组信息
    * @param lOrderCondition 排序条件
    * @return 
    * @throws ITreasuryDAOException
    * @throws SQLException
    */
    public Collection findGroupByCondition(Sys_GroupInfo groupinfo,long lOrderCondition,long lCompositorCondition)
    throws ITreasuryDAOException, SQLException
	{
    	return groupbean.findGroupByCondition(groupinfo,lOrderCondition,lCompositorCondition);
    }
    /**
     * 根据用户组ID 查询一条用户组信息
     * @param groupID
     * @return 
     * @throws ITreasuryDAOException
     */
    public Sys_GroupInfo findGroupInfoByID(long groupID)
        throws ITreasuryDAOException
    {
        return groupbean.findGroupInfoByID(groupID);
    }
    /**
     * 根据用户组ID查询该组具有的所有权限
     * @param groupID
     * @return
     * @throws ITreasuryDAOException
     */
    public Collection findGroupPrivilegeByID(long groupID)
        throws ITreasuryDAOException
    {
        return groupbean.findGroupPrivilegeByID(groupID);
    }
    /**
     * 添加用户组，
     * 用户组名重复添加失败
     * @param groupCondition 用户组信息
     * @param pgCondition  用户组权限关系信息
     * @return 添加的用户组ID
     * @throws SQLException
     * @throws IException
     */ 
    public long addGroup(Sys_GroupInfo groupCondition, Sys_Privilege_Of_GroupInfo pgCondition[])
        throws SQLException, IException
    {
        return groupbean.addGroup(groupCondition, pgCondition);
    }
    /**
     * 删除用户组
     * 同时删除用户组对应的用户组权限关系
     * 如果用户组被用户使用删除失败
     * @param groupId 用户组编号
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */
    public int delGroup(long groupId)
        throws SQLException, IException
    {
        return groupbean.delGroup(groupId);
    }
    /**
     * 更新用户组信息
     * 同时更新用户组权限关系信息（先删后加）
     * 如果用户组名重复登录失败
     * @param groupCondition 
     * @param pgCondition
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */
    public int updateGroup(Sys_GroupInfo groupCondition, Sys_Privilege_Of_GroupInfo pgCondition[])
        throws SQLException, IException
    {
        return groupbean.updateGroup(groupCondition, pgCondition);
    }
    
    /**
     * 复核
     * @param groupCondition 
     * @param pgCondition
     * @return 成功返回1
     * @throws SQLException
     * @throws IException
     */
    public int check(Sys_GroupInfo groupCondition)
        throws SQLException, IException
    {
        return groupbean.check(groupCondition);
    }
    /**
     * 查询某个模块的所有权限
     * @param moduleId
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    
    public Collection findPrivilegesbyModuleId(long moduleId,long officeId)
        throws ITreasuryDAOException, SQLException
    {
        return groupbean.findPrivilegesbyModuleId(moduleId,officeId);
    }
    
    /**
     * 查询一级模块
     * @param moduleId
     * @return
     * @throws ITreasuryDAOException
     * @throws SQLException
     */
    
    public Collection findPrivilegesByMain(ArrayList arg0,long lUserId,long lOffice) throws ITreasuryDAOException, SQLException{
        return groupbean.findPrivilegesByMain(arg0,lUserId,lOffice);
    }
    
    /**
     * 查询某个模块的所有用户组
     * @param moduleId
     * @return
     * @throws ITreasuryDAOException
     */
    public Collection findGroupByModuleId(long moduleId,long lOfficeID,long lCurrencyID)
        throws ITreasuryDAOException
    {
        return groupbean.findGroupByModuleId(moduleId,lOfficeID,lCurrencyID);
    }
    public Collection findPrivilegesByGroupId(long groupId) throws SQLException, ITreasuryDAOException
	{
    	return groupbean.findPrivilegesByGroupId(groupId);
    }
    /**
     * Add by zwsun, 2007-6-12, 权限分离
     * 查询系统模块的代码
     * @param allCode为所有模块的代码
	**/
	public long[] getSysCode(long[] allCode){
		return new long[]{Constant.ModuleType.SYSTEM};
	}
    /**
     * Add by zwsun, 2007-6-12, 权限分离
     * 查询业务模块的代码
     * @param allCode为所有模块的代码
	**/
	public long[] getBusCode(long[] allCode){
		ArrayList arrayListTemp=new ArrayList();
		for(int i=0;i<allCode.length;i++){
			if(Constant.ModuleType.SYSTEM!=allCode[i]){
				arrayListTemp.add(new Long(allCode[i]));
			}
		}
		
		long[] busCode=new long[arrayListTemp.size()];
		for(int i=0;i<arrayListTemp.size();i++){
			busCode[i]=((Long)arrayListTemp.get(i)).longValue();
		}
		return busCode;
	}
    /**
     * Add by zwsun, 2007-6-12, 权限分离
     * 设置业务模块内容
     * @param arrayModelType为模块的代码
     * @param arrayModelName为模块标识
     * @param arraylistgroup为模块内组
	**/
	public void setModel(long[] arrayModelType, String[] arrayModelName, ArrayList[] arraylistgroup
		, GroupDelegation delegation
		, com.iss.itreasury.util.SessionMng sessionMng) throws Exception{
		int nModelLenth = arrayModelType.length;	 
		for(int i=0;i<nModelLenth;i++)
		{
			arrayModelName[i] = Constant.ModuleType.getName(arrayModelType[i]);
			arraylistgroup[i] = (ArrayList)delegation.findGroupByModuleId(arrayModelType[i],sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		}		
	} 	
   
}

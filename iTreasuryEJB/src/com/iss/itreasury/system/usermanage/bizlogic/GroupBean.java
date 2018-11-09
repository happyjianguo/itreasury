/*
 * Created on 2004-7-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.bizlogic;

import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.usermanage.dao.GroupDAO;
import com.iss.itreasury.system.usermanage.dao.PrivilegeDAO;
import com.iss.itreasury.system.usermanage.dao.PrivilegeOfGroupDAO;
import com.iss.itreasury.system.usermanage.dataentity.GroupInfo;
import com.iss.itreasury.system.usermanage.dataentity.PrivilegeInfo;
import com.iss.itreasury.system.usermanage.dataentity.PrivilgeOfGroupInfo;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GroupBean
{
	GroupDAO m_dao = null;
	/**
	 * 
	 */
	public GroupBean ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		m_dao = new GroupDAO();
	}
	/**
	 * 增加新用户组
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long addGroup(GroupInfo info) throws ITreasuryDAOException
	{
		long lGroupID = m_dao.add(info);
		info.setId(lGroupID);
		// 增加用户组的权限
		addPrivilegeOfGroup (info ) ;

		return lGroupID;
	}
	/**
	 * 修改用户组
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	public void updateGroup(GroupInfo info)throws ITreasuryDAOException
	{
		m_dao.update(info);
		// 删除用户组原有的权限
		deletePrivilegeOfGroup(info.getId());
		// 增加用户组的新权限
		addPrivilegeOfGroup(info);
	}
	/**
	 * 删除用户组
	 * @param lGroupID
	 * @throws ITreasuryDAOException
	 */
	public void deleteGroup(long lGroupID)throws ITreasuryDAOException
	{
		m_dao.deletePhysically(lGroupID);
		// 删除用户组原有的权限
		deletePrivilegeOfGroup(lGroupID);
	}
	/**
	 * 查找用户
	 * @param lGroupID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public GroupInfo findByGroupID(long lGroupID)throws ITreasuryDAOException
	{
		GroupInfo info = (GroupInfo)m_dao.findByID(lGroupID , (new GroupInfo ( )).getClass ( ));
		info.setPrivilegeOfGroup( findPrivilgeOfGroup(info.getId()) );
		return info;
	}
	/**
	 * 按条件查找用户组
	 * @param condition
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findGroupByCondition(GroupInfo condition)throws ITreasuryDAOException
	{
		return m_dao.findByCondition(condition);
	}
	/**
	 * 删除用户组的权限
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	private void deletePrivilegeOfGroup ( PrivilgeOfGroupInfo[] info ) throws ITreasuryDAOException
	{
		if (info != null && info.length > 0)
		{
			PrivilegeOfGroupDAO dao = new PrivilegeOfGroupDAO ( ) ;
			for (int i = 0; i < info.length; i++)
			{
				dao.deletePhysically(info[i].getId());
			}
		}
	}
	/**
	 * 删除用户组的权限
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	private void deletePrivilegeOfGroup ( PrivilgeOfGroupInfo info ) throws ITreasuryDAOException
	{
		if (info != null )
		{
			PrivilegeOfGroupDAO dao = new PrivilegeOfGroupDAO ( ) ;
			dao.deletePhysically(info.getId());
		}
	}	
	/**
	 * 删除用户组的权限
	 * @param lGroupID
	 * @throws ITreasuryDAOException
	 */
	private void deletePrivilegeOfGroup( long lGroupID) throws ITreasuryDAOException
	{
		PrivilegeOfGroupDAO dao = new PrivilegeOfGroupDAO();
		PrivilgeOfGroupInfo condition = new PrivilgeOfGroupInfo();
		condition.setGroupID(lGroupID);
		Collection c = dao.findByCondition(condition);
		if( c != null )
		{
			Iterator it = c.iterator();
			while(it.hasNext())
			{
				PrivilgeOfGroupInfo deletedInfo = (PrivilgeOfGroupInfo)it.next();
				dao.deletePhysically(deletedInfo.getId());
			}
		}
	}
	/**
	 * 增加用户组的权限
	 * @param info
	 * @throws ITreasuryDAOException
	 */
	private void addPrivilegeOfGroup (GroupInfo info ) throws ITreasuryDAOException
	{
		if (info.getPrivilegeOfGroup() != null && info.getPrivilegeOfGroup().length > 0)
		{
			PrivilegeOfGroupDAO dao = new PrivilegeOfGroupDAO ( ) ;
			for (int i = 0; i < info.getPrivilegeOfGroup().length; i++)
			{
				info.getPrivilegeOfGroup()[i].setGroupID ( info.getId() ) ;
				dao.add ( info.getPrivilegeOfGroup()[i] ) ;
			}
		}
	}
	/**
	 * 查找用户组的权限
	 * @param lGroupID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	private PrivilgeOfGroupInfo[] findPrivilgeOfGroup(long lGroupID)throws ITreasuryDAOException
	{
		PrivilgeOfGroupInfo [] array = null;
		PrivilegeOfGroupDAO privilegeOfGroupDAO = new PrivilegeOfGroupDAO();
		PrivilegeDAO privilegeDAO = new PrivilegeDAO();
		PrivilgeOfGroupInfo info = new PrivilgeOfGroupInfo();
		//
		info.setGroupID(lGroupID);
		Collection c = privilegeOfGroupDAO.findByCondition(info);
		//
		if( c != null && c.size() > 0)
		{
			array = new PrivilgeOfGroupInfo[c.size()];
			Iterator it = c.iterator();
			for( int i=0; it.hasNext(); i++)
			{
				array[i] = (PrivilgeOfGroupInfo)it.next();
				array[i].setPrivilegeInfo((PrivilegeInfo)privilegeDAO.findByID( array[i].getPrivilegeID() , (new PrivilegeInfo ( )).getClass ( ) ));
			}
		}
		return array;
	}	
}

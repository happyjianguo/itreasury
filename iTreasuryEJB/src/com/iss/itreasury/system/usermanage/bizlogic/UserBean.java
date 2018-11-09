/*
 * Created on 2004-7-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.bizlogic ;
import java.util.Collection;
import java.util.Iterator;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.system.usermanage.dao.GroupOfUserDAO;
import com.iss.itreasury.system.usermanage.dao.UserDAO;
import com.iss.itreasury.system.usermanage.dataentity.GroupOfUserInfo;
import com.iss.itreasury.system.usermanage.dataentity.UserInfo;
import com.iss.itreasury.util.Constant;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class UserBean
{
	UserDAO	m_dao ;
	/**
	 *  
	 */
	public UserBean ( )
	{
		super ( ) ;
		// TODO Auto-generated constructor stub
		m_dao = new UserDAO ( ) ;
	}
	/**
	 * 查找用户
	 * @param lUserID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public UserInfo findUserByID ( long lUserID ) throws ITreasuryDAOException
	{
		UserInfo info = (UserInfo) m_dao.findByID ( lUserID , (new UserInfo ( )).getClass ( ) ) ;
		info.setGroupOfUser(findGroupOfUser(lUserID));
		return info;
	}
	/**
	 * 查找所有用户
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findAllUsers ( ) throws ITreasuryDAOException
	{
		UserInfo condition = new UserInfo ( ) ;
		//
		condition.setStatusID ( Constant.RecordStatus.VALID ) ;
		return m_dao.findByCondition ( condition ) ;
	}
	/**
	 * 按条件查找用户
	 * @param condition
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection findUserByCondition ( UserInfo condition ) throws ITreasuryDAOException
	{
		return m_dao.findByCondition ( condition ) ;
	}
	/**
	 * 修改用户
	 * @param condition
	 * @throws ITreasuryDAOException
	 */
	public void updateUser ( UserInfo condition ) throws ITreasuryDAOException
	{
		deleteUserByID(condition.getId());
		condition.setStatusID(Constant.RecordStatus.VALID);
		m_dao.update ( condition ) ;
		addGroupOfUser(condition);
		
	}
	/**
	 * 删除用户
	 * @param lUserID
	 * @throws ITreasuryDAOException
	 */
	public void deleteUserByID ( long lUserID ) throws ITreasuryDAOException
	{
		UserInfo condition = new UserInfo ( ) ;
		//
		condition.setStatusID ( Constant.RecordStatus.INVALID ) ;
		condition.setId ( lUserID ) ;
		m_dao.update ( condition ) ;
		//
		GroupOfUserDAO gouDAO = new GroupOfUserDAO();
		GroupOfUserInfo gouInfo = new GroupOfUserInfo();
		gouInfo.setUserID(lUserID);
		Collection c = gouDAO.findByCondition(gouInfo);
		if( c != null )
		{
			Iterator it = c.iterator();
			while(it.hasNext())
			{
				GroupOfUserInfo info = (GroupOfUserInfo)it.next();
				gouDAO.deletePhysically(info.getId());
			}
		}
	}
	/**
	 * 得到用户的权限集合
	 * @param lUserID
	 * @param lModuleID
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection getPrivilegeOfUser ( long lUserID , long lModuleID ) throws ITreasuryDAOException
	{
//		Vector v = new Vector();
//		HashMap hmPrivilege = new HashMap();
//		GroupBean groupBean = new GroupBean();
//		//
//		UserInfo userInfo = findUserByID(lUserID);
//		//
//		if( userInfo != null && userInfo.getGroupOfUser() != null )
//		{
//			for( int i=0; i<userInfo.getGroupOfUser().length; i++ )
//			{
//				GroupOfUserInfo info = userInfo.getGroupOfUser()[i];
//				if( info != null )
//				{
//					GroupInfo groupInfo = groupBean.findByGroupID(info.getGroupID());
//					if( groupInfo != null && groupInfo.getPrivilegeOfGroup() != null )
//					{
//						for( int j=0; j<groupInfo.getPrivilegeOfGroup().length ; i++)
//						{
//							PrivilegeInfo privilegeInfo = groupInfo.getPrivilegeOfGroup()[j].getPrivilegeInfo();
//							if( privilegeInfo.getModuleID() == lModuleID && v.contains(privilegeInfo) == false )
//								v.add(privilegeInfo);
//						}
//					}
//				}
//			}
//			// 排序
//		}
		return m_dao.getPrivilegeOfUser(lUserID,lModuleID);
	}
	/**
	 * 新增用户
	 * @param info
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public long addUser ( UserInfo info ) throws ITreasuryDAOException
	{
		long lUserID = m_dao.add ( info ) ;
		info.setId(lUserID);
		addGroupOfUser (info) ;
		return lUserID;
	}
	private void deleteGroupOfUser ( GroupOfUserInfo[] gouInfo ) throws ITreasuryDAOException
	{
		if (gouInfo != null && gouInfo.length > 0)
		{
			GroupOfUserDAO dao = new GroupOfUserDAO ( ) ;
			for (int i = 0; i < gouInfo.length; i++)
			{
				dao.deletePhysically(gouInfo[i].getId());
			}
		}
	}
	private void deleteGroupOfUser ( GroupOfUserInfo gouInfo ) throws ITreasuryDAOException
	{
		if (gouInfo != null )
		{
			GroupOfUserDAO dao = new GroupOfUserDAO ( ) ;
			dao.deletePhysically(gouInfo.getId());
		}
	}	
	private void addGroupOfUser ( UserInfo info ) throws ITreasuryDAOException
	{
		if (info != null && info.getGroupOfUser().length > 0)
		{
			GroupOfUserDAO dao = new GroupOfUserDAO ( ) ;
			for (int i = 0; i < info.getGroupOfUser().length; i++)
			{
				info.getGroupOfUser()[i].setUserID ( info.getId() ) ;
				dao.add ( info.getGroupOfUser()[i] ) ;
			}
		}
	}
	private GroupOfUserInfo[] findGroupOfUser(long lUserID)throws ITreasuryDAOException
	{
		GroupOfUserInfo [] array = null;
		GroupOfUserDAO dao = new GroupOfUserDAO();
		GroupOfUserInfo gouInfo = new GroupOfUserInfo();
		gouInfo.setUserID(lUserID);
		Collection c = dao.findByCondition(gouInfo);
		if( c != null && c.size() > 0 )
		{
			array = new GroupOfUserInfo[c.size()];
			Iterator it = c.iterator();
			for( int i=0; it.hasNext();i++)
			{
				array[i] = (GroupOfUserInfo)it.next();
			}
		}
		return array;
	}
}
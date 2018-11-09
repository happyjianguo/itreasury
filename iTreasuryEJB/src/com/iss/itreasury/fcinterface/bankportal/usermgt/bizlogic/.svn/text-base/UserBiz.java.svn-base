package com.iss.itreasury.fcinterface.bankportal.usermgt.bizlogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.business.BusinessException;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.usermgt.PrivilegeInfo;
import com.iss.itreasury.fcinterface.bankportal.usermgt.User;
import com.iss.itreasury.fcinterface.bankportal.usermgt.UserManager;
import com.iss.itreasury.fcinterface.bankportal.usermgt.dao.UserDAO;
import com.iss.itreasury.fcinterface.bankportal.usermgt.dao.UserPrivilegeDAO;
import com.iss.itreasury.fcinterface.bankportal.usermgt.dataentity.UserInfo;
import com.iss.itreasury.fcinterface.bankportal.usermgt.dataentity.UserPrivilegeInfo;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;
import com.iss.itreasury.fcinterface.bankportal.util.HashMapCache;
import com.iss.itreasury.fcinterface.bankportal.util.Logger;

/**
 * 用户Biz
 * @author mxzhou
 *
 */
public class UserBiz
{
	/** 日志对象 */
	private Logger log = new Logger(UserBiz.class);
	/**用户实现类*/
	private User userImpl = null;
	
	private static HashMapCache hmCache = new HashMapCache();

	/**
	 * 用户登陆校验
	 * @param userNo
	 * @param userPassword
	 * @return
	 * @throws BusinessException
	 */
	public long login(String userNo, String userPassword)
		throws BusinessException
	{
		if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}

		if (!userImpl.authenticate(userNo, userPassword))
		{
			throw new BusinessException("用户和密码不匹配，请重新登录");
		}

		long userId = -1;
		String userName = userImpl.getName(userNo);

		/**对于成功登陆的用户，在本系统中建立id与userNo的对应关系*/
		//先查询是否已经记录了对应关系
		UserInfo queryInfo = new UserInfo();
		UserInfo[] resultInfos = null;
		queryInfo.setNo(userNo);
		resultInfos = this.findUserInfosByCondition(queryInfo, null);
		if (resultInfos != null && resultInfos.length > 0)
		{
			userId = resultInfos[0].getId();
			resultInfos[0].setName(userName);
			this.updateUserInfo(resultInfos[0]);
		}
		//否则添加对应关系
		else
		{
			//此处id设置无实际涵义，只是保证数据库会插入id字段，其具体的值会根据id的取值方式自己决定
			queryInfo.setId(-1);
			queryInfo.setName(userName);
			userId = this.addUserInfo(queryInfo);
		}
		return userId;
	}

	/**
	 * 用户登陆校验
	 * @param userNo
	 * @return
	 * @throws BusinessException
	 */
	public long login(String userNo)
		throws BusinessException
	{
		if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}

		long userId = -1;
		String userName = userImpl.getName(userNo);

		/**对于成功登陆的用户，在本系统中建立id与userNo的对应关系*/
		//先查询是否已经记录了对应关系
		UserInfo queryInfo = new UserInfo();
		UserInfo[] resultInfos = null;
		queryInfo.setNo(userNo);
		resultInfos = this.findUserInfosByCondition(queryInfo, null);
		if (resultInfos != null && resultInfos.length > 0)
		{
			userId = resultInfos[0].getId();
			resultInfos[0].setName(userName);
			this.updateUserInfo(resultInfos[0]);
		}
		//否则添加对应关系
		else
		{
			//此处id设置无实际涵义，只是保证数据库会插入id字段，其具体的值会根据id的取值方式自己决定
			queryInfo.setId(-1);
			queryInfo.setName(userName);
			userId = this.addUserInfo(queryInfo);
		}
		return userId;
	}
	/**
	 * 修改密码
	 * @param userNo
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws BusinessException
	 */
	public void changeUserPassword(String userNo, String oldPassword,String newPassword)
		throws BusinessException
	{
		if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}

		if (!userImpl.authenticate(userNo, oldPassword))
		{
			throw new BusinessException("旧密码不正确");
		}
        if(!userImpl.changePassword(userNo,newPassword))
        {
        	throw new BusinessException("修改密码失败");
        }		
	}
	/**
	 * 根据用户No获取用户名
	 * @param userNo
	 * @return
	 * @throws BusinessException
	 */
	public String getUserName(String userNo) throws BusinessException
	{
		String userName = "";

		UserInfo queryInfo = new UserInfo();
		UserInfo[] resultInfos = null;
		queryInfo.setNo(userNo);
		resultInfos = this.findUserInfosByCondition(queryInfo, null);
		if (resultInfos != null && resultInfos.length > 0)
		{
			userName = resultInfos[0].getName();
		}
		else
		{
			log.info("根据用户no：[" + userNo + "]找不到对应的用户信息");
		}

		return userName;
	}
	/**
	 * 根据用户No获取用户办事处ID
	 * @param userNo
	 * @return
	 * @throws BusinessException
	 */
	public long getUserOfficeID(String userNo) throws BusinessException
	{
		long officeID = -1;
		if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}

		officeID = userImpl.getOfficeID(userNo);
		return officeID;
	}
	
	public String getUserOfficeName(long officeID) throws BusinessException
	{
		if(officeID<=0) return null;
		
		String officeName = null;
		if(userImpl == null)
		{
			try{
				userImpl = UserManager.getUserImpl();
				if(userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch(Exception ex)
			{
				throw new BusinessException(
						"获取用户实现类时出现异常，出错原因：" + ex.getMessage(),
						ex);
			}
		}
		
		officeName = userImpl.getOfficeName(officeID);
		return officeName;
	}
	
	/**
	 * 根据用户no获取用户权限信息
	 * @param userNo
	 * @return
	 * @throws BusinessException
	 */
	public UserPrivilegeInfo[] getUserPrivilege(String userNo)
		throws BusinessException
	{
		if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}

		//通过用户实现类获取用户权限信息
		PrivilegeInfo[] privilegeInfos = null;
		privilegeInfos = userImpl.getPrivilege(userNo);
		

		//转换用户权限信息为本系统权限信息
		ArrayList al = new ArrayList();
		UserPrivilegeInfo queryInfo = null;
		UserPrivilegeInfo[] resultInfos = null;
		if (privilegeInfos != null && privilegeInfos.length > 0)
		{
			HashMap hmTemp = new HashMap(128);
			log.debug("Get privilege info is " + privilegeInfos.length + ":");
			for (int i = 0; i < privilegeInfos.length; i++)
			{
				log.debug(privilegeInfos[i].getCode());

				hmTemp.put(privilegeInfos[i].getCode(), null);
			}

			//			for(int i = 0; i < privilegeInfos.length; i++)
			//			{
			//				queryInfo = new UserPrivilegeInfo();
			//				queryInfo.setCode(privilegeInfos[i].getCode());
			//				resultInfos = this.findUserPrivilegeInfosByCondition(queryInfo, null);
			//				if(resultInfos != null && resultInfos.length > 0)
			//				{
			//					al.add(resultInfos[0]);
			//				}
			//			}

			queryInfo = new UserPrivilegeInfo();
			resultInfos = this.findUserPrivilegeInfosByCondition(queryInfo, " order by S_CODE");

			if (resultInfos != null && resultInfos.length > 0)
			{
				for (int i = 0; i < resultInfos.length; i++)
				{
					if(hmTemp.containsKey(resultInfos[i].getCode()))
					{
						al.add(resultInfos[i]);
					}
				}
			}
		}

		resultInfos = new UserPrivilegeInfo[0];
		if (al.size() > 0)
		{
			resultInfos = (UserPrivilegeInfo[]) al.toArray(resultInfos);
		}

		return resultInfos;
	}
	/**
	 * 获得userID
	 * 检查数据库时候有对应用户信息，有就修改，没有就新增
	 * @param info
	 * @return
	 */
    public long generateUserID(UserInfo info) throws BusinessException
	{
    	if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}

		long userId = -1;

		/**对于成功登陆的用户，在本系统中建立id与userNo的对应关系*/
		//先查询是否已经记录了对应关系
		UserInfo queryInfo = new UserInfo();
		UserInfo[] resultInfos = null;
		queryInfo.setNo(info.getNo());
		resultInfos = this.findUserInfosByCondition(queryInfo, null);
		if (resultInfos != null && resultInfos.length > 0)
		{
			if(!(info.equals(resultInfos[0])))
			{
				info.setId(resultInfos[0].getId());
			    this.updateUserInfo(info);
			}
			userId = resultInfos[0].getId();
		}
		//否则添加对应关系
		else
		{			
			info.setId(-1);			
			userId = this.addUserInfo(info);
		}
		return userId;
    }
    
    public long getImplIDByUserNo(String userNo) throws BusinessException
    {
        long id = -1;
       	if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}
       	id = userImpl.getIDByUserNo(userNo);
        return id;
    }
    
    public UserInfo[] getAllUserInfo() throws BusinessException
	{
    	UserInfo[] results = null;
    	if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}
        results = userImpl.getAllUserInfo();
		return results;
    }
	/**
	 * 添加用户信息
	 * @param info
	 * @return
	 * @throws BusinessException
	 */
	private long addUserInfo(UserInfo info) throws BusinessException
	{
		long lID = -1;
		try
		{
			UserDAO bs_UserDAO =
				(UserDAO) DAOFactory.getDAOImpl(UserDAO.class, null);
			/**System.out.println("****start*************");
			System.out.println("userNo="+info.getNo());
			if(info.getUsedField()!=null && info.getUsedField().length>0)
			{
				String[] usedFields=info.getUsedField();
				for(int i=0;i<usedFields.length;i++)
				{
					System.out.println("usedFields["+i+"]="+usedFields[i]);
				}
 			}
			System.out.println("officeid="+info.getOfficeID());
			System.out.println("****end*********");*/
			lID = bs_UserDAO.add(info);
			findUserInfoByID(lID);
		}
		catch (SystemException e)
		{
			e.printStackTrace();
			throw new BusinessException(
				"保存用户信息时出现异常，出错原因：" + e.getMessage(),
				e);
		}
		return lID;
	}

	/**
	 * 修改用户信息
	 * @param info
	 * @return
	 * @throws BusinessException
	 */
	private void updateUserInfo(UserInfo info) throws BusinessException
	{
		try
		{
			UserDAO bs_UserDAO =
				(UserDAO) DAOFactory.getDAOImpl(UserDAO.class, null);
			bs_UserDAO.update(info);
			hmCache.remove(String.valueOf(info.getId()));
			findUserInfoByID(info.getId());
		}
		catch (SystemException e)
		{
			e.printStackTrace();
			throw new BusinessException(
				"修改用户信息时出现异常，出错原因：" + e.getMessage(),
				e);
		}
	}

	/**
	 * 根据条件查询用户信息
	 * @param info
	 * @param orderByString
	 * @return
	 * @throws BusinessException
	 */
	public UserInfo[] findUserInfosByCondition(
		UserInfo info,
		String orderByString)
		throws BusinessException
	{
		Collection colResult = null;
		UserInfo[] userInfos = null;
		try
		{
			UserDAO bs_UserDAO =
				(UserDAO) DAOFactory.getDAOImpl(UserDAO.class, null);
			colResult = bs_UserDAO.findByCondition(info, orderByString);
			if (colResult != null && colResult.size() > 0)
			{
				userInfos = new UserInfo[0];
				userInfos = (UserInfo[]) colResult.toArray(userInfos);
			}
		}
		catch (SystemException e)
		{
			e.printStackTrace();
			throw new BusinessException(
				"查询用户信息时出现异常，出错原因：" + e.getMessage(),
				e);
		}
		return userInfos;
	}

	/**
	 * 根据条件查找用户权限信息
	 * @param info
	 * @param orderByString
	 * @return
	 * @throws BusinessException
	 */
	public UserPrivilegeInfo[] findUserPrivilegeInfosByCondition(
		UserPrivilegeInfo info,
		String orderByString)
		throws BusinessException
	{
		Collection colResult = null;
		UserPrivilegeInfo[] userInfos = null;
		try
		{
			UserPrivilegeDAO bs_UserPrivilegeDAO =
				(UserPrivilegeDAO) DAOFactory.getDAOImpl(
					UserPrivilegeDAO.class,
					null);
			colResult =
				bs_UserPrivilegeDAO.findByCondition(info, orderByString);
			if (colResult != null && colResult.size() > 0)
			{
				userInfos = new UserPrivilegeInfo[0];
				userInfos = (UserPrivilegeInfo[]) colResult.toArray(userInfos);
			}
		}
		catch (SystemException e)
		{
			e.printStackTrace();
			throw new BusinessException(
				"查询用户权限信息时出现异常，出错原因：" + e.getMessage(),
				e);
		}
		return userInfos;
	}

	public static void main(String[] args)
	{
		UserBiz userBiz = new UserBiz();
		try
		{
//			String userNo = "bptest";
//
//			long loginId = userBiz.login(userNo, "123456");
//			System.out.println("loginId: " + loginId);
//
//			String userName = userBiz.getUserName(userNo);
//			System.out.println("userName: " + userName);
//
//			UserPrivilegeInfo[] upi = userBiz.getUserPrivilege(userNo);
//			if (upi != null && upi.length > 0)
//			{
//				System.out.println("PrivilegeInfo is " + upi.length + ":");
//				for (int i = 0; i < upi.length; i++)
//				{
//					System.out.println("Code: " + upi[i].getCode());
//					System.out.println("Name: " + upi[i].getName());
//					System.out.println(
//						"PrivilegeUrl: " + upi[i].getPrivilegeUrl());
//				}
//			}
//			else
//			{
//				System.out.println("PrivilegeInfo is null");
//			}
//			UserInfo info = new UserInfo();
//			info.setId(-1);
//			info.setNo("first2");
//			info.setName("系统");
			UserBiz biz = new UserBiz();
//			System.out.println("ID:"+biz.generateUserID(info));
			UserInfo infos[] = biz.getAllUserInfo();
			for(int i=0;i<infos.length;i++)
			{
				System.out.println(i+"   NO:"+infos[i].getNo()+";Name:"+infos[i].getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public UserInfo findUserInfoByID(long id)
	   throws BusinessException
	{
		UserInfo result = null;
		try
		{
			result = (UserInfo)hmCache.get(String.valueOf(id));
			if(result == null)
			{
				UserDAO bs_UserDAO = (UserDAO) DAOFactory.getDAOImpl(UserDAO.class, null);
				result = (UserInfo)bs_UserDAO.findByID(id,UserInfo.class);
				hmCache.put(String.valueOf(id),result);
			}
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new BusinessException("查找用户信息出错：："+e.getMessage(), e);
		}
		return result;
	}
	
	public static void loadAllValidInfo()
	{
		try
		{
			UserDAO dao = (UserDAO)DAOFactory.getDAOImpl(UserDAO.class, null);
			Collection colResult = dao.findAll(UserInfo.class,null);
			if(colResult != null && colResult.size()>0)
			{
				UserInfo[] infos = (UserInfo[])colResult.toArray(new UserInfo[0]);
			    for(int i=0;i<infos.length;i++)
			    {
			    	hmCache.put(String.valueOf(infos[i].getId()),infos[i]);
			    }
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param userID
	 * @return
	 * @throws BusinessException
	 */
	public UserInfo findUserInfoByIDFromImpl(long userID) 
		throws BusinessException 
	{	
	   	if (userImpl == null)
		{
			try
			{
				userImpl = UserManager.getUserImpl();
				if (userImpl == null)
				{
					throw new Exception("userImpl is null");
				}
			}
			catch (Exception e)
			{
				throw new BusinessException(
					"获取用户实现类时出现异常，出错原因：" + e.getMessage(),
					e);
			}
		}
        UserInfo result =  userImpl.getUserInfoByID(userID);
		return result;
	}
}

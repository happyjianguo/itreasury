/*
 * Created on 2005-6-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.usermgt;

import com.iss.itreasury.fcinterface.bankportal.usermgt.dataentity.UserInfo;

/**
 * 用户接口<br>
 * 提供用户合法性的校验、根据用户no获取用户名以及用户权限等
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface User
{
	/**
	 * 根据用户no和用户password校验用户的有效性
	 * @param userNo
	 * @param userPassword
	 * @return
	 */
	public boolean authenticate(String userNo, String userPassword);
	
	/**
	 * 修改指定用户密码
	 * @param userNo
	 * @param userPassword
	 * @return
	 */
	public boolean changePassword(String userNo, String userPassword);
	
	/**
	 * 根据用户no获取用户名
	 * @param userNo
	 * @return
	 */
	public String getName(String userNo);
	
	/**
	 * 根据用户登录名获得id
	 * @param userNo
	 * @return
	 */
	public long getIDByUserNo(String userNo);
	
	/**
	 * 根据用户no获取用户权限<br>
	 * 用户权限字符串采用类似“1-100-100”的方式定义权限的层次、顺序与归属关系
	 * @param userNo
	 * @return
	 */
	public PrivilegeInfo[] getPrivilege(String userNo);
	/**
	 * 获取所有用户信息
	 * @return
	 */
	public UserInfo[] getAllUserInfo();
	/**
	 * 根据用户no获取用户办事处ID
	 * @param userNo
	 * @return
	 */
	public long getOfficeID(String userNo);

	
	/**
	 * 根据officeId获取用户办事处名称
	 * @param officeID
	 * @return
	 */
	public String getOfficeName(long officeID);

	/**
	 * 根据userid获取用户名称职
	 * @param userID
	 * @return
	 */
	public UserInfo getUserInfoByID(long userID);
}

/* Copyright ? 2005 Isoftstone All Rights Reserved*/

package com.iss.itreasury.dataentity;

import java.util.Collection;

/**
 * 初始化用户时所需配置数据
 * 
 * @author xgzhang
 * @version 1.0
 */
public class InitUserInfo {
	long userNumber = -1;
	/**
	 * 初始化用户所属权限组的名称
	 */
	private String groupName;

	/**
	 * 初始化用户的名称
	 */
	private String userName;

	/**
	 * 初始化用户所属权限组中的模块ID
	 */
	private int moduleId;

	/**
	 * 初始化用户所属权限组的权限集合
	 */
	private Collection privileges;

	/**
	 * 该初始化数据的状态，1为有效，0为无效
	 */
	private boolean status;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public Collection getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection privileges) {
		this.privileges = privileges;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public long getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(long userNumber) {
		this.userNumber = userNumber;
	}

}

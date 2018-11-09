/* Copyright ? 2005 Isoftstone All Rights Reserved*/

package com.iss.itreasury.dataentity;

import java.util.Collection;

/**
 * ��ʼ���û�ʱ������������
 * 
 * @author xgzhang
 * @version 1.0
 */
public class InitUserInfo {
	long userNumber = -1;
	/**
	 * ��ʼ���û�����Ȩ���������
	 */
	private String groupName;

	/**
	 * ��ʼ���û�������
	 */
	private String userName;

	/**
	 * ��ʼ���û�����Ȩ�����е�ģ��ID
	 */
	private int moduleId;

	/**
	 * ��ʼ���û�����Ȩ�����Ȩ�޼���
	 */
	private Collection privileges;

	/**
	 * �ó�ʼ�����ݵ�״̬��1Ϊ��Ч��0Ϊ��Ч
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

/*
 * Created on 2004-11-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.dataentity;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChildClientPrivilegeInfo implements java.io.Serializable {
	private long id = -1; //���
	private long clientID = -1; //�ϼ���λid
	private long childClientID = -1; //������λid
	private String childClientName = ""; //������λ����
	private String childClientCode = ""; //������λ���
	private long privilegeType = -1; //Ȩ������,Ŀǰ������ֵ��1Ϊ��ѯȨ�ޣ�2Ϊ����Ȩ��
	private long privilegeValue = -1; //Ȩ��ֵ��Ŀǰ������ֵ��1Ϊ�ǣ�2Ϊ��

	private long privilege1Value = -1; //��ѯȨ��ֵ
	private long privilege2Value = -1; //����Ȩ��ֵ

	/**
	 * @return Returns the childClientCode.
	 */
	public String getChildClientCode() {
		return childClientCode;
	}
	/**
	 * @param childClientCode The childClientCode to set.
	 */
	public void setChildClientCode(String childClientCode) {
		this.childClientCode = childClientCode;
	}
	/**
	 * @return Returns the privilegeType.
	 */
	public long getPrivilegeType() {
		return privilegeType;
	}
	/**
	 * @param privilegeType The privilegeType to set.
	 */
	public void setPrivilegeType(long privilegeType) {
		this.privilegeType = privilegeType;
	}
	/**
	 * @return Returns the privilegeValue.
	 */
	public long getPrivilegeValue() {
		return privilegeValue;
	}
	/**
	 * @param privilegeValue The privilegeValue to set.
	 */
	public void setPrivilegeValue(long privilegeValue) {
		this.privilegeValue = privilegeValue;
	}
	/**
	 * privilegeType
	 * 
	 * @return Returns the childClientID.
	 */
	public long getChildClientID() {
		return childClientID;
	}
	/**
	 * @param childClientID
	 *            The childClientID to set.
	 */
	public void setChildClientID(long childClientID) {
		this.childClientID = childClientID;
	}
	/**
	 * @return Returns the clientID.
	 */
	public long getClientID() {
		return clientID;
	}
	/**
	 * @param clientID
	 *            The clientID to set.
	 */
	public void setClientID(long clientID) {
		this.clientID = clientID;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the privilegeType.
	 */

	/**
	 * @return Returns the childClientName.
	 */
	public String getChildClientName() {
		return childClientName;
	}
	/**
	 * @param childClientName
	 *            The childClientName to set.
	 */
	public void setChildClientName(String childClientName) {
		this.childClientName = childClientName;
	}
	/**
	 * @return Returns the privilege1Value.
	 */
	public long getPrivilege1Value() {
		return privilege1Value;
	}
	/**
	 * @param privilege1Value
	 *            The privilege1Value to set.
	 */
	public void setPrivilege1Value(long privilege1Value) {
		this.privilege1Value = privilege1Value;
	}
	/**
	 * @return Returns the privilege2Value.
	 */
	public long getPrivilege2Value() {
		return privilege2Value;
	}
	/**
	 * @param privilege2Value
	 *            The privilege2Value to set.
	 */
	public void setPrivilege2Value(long privilege2Value) {
		this.privilege2Value = privilege2Value;
	}
}
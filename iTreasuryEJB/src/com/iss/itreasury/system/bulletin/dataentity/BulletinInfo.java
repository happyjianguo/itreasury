/*
 * Created on 2006-9-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.bulletin.dataentity;

import java.sql.Date;
import java.sql.Timestamp;

import com.iss.itreasury.util.DataFormat;

/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BulletinInfo implements java.io.Serializable{
	
	private long id = -1; 
	private long modifyId=-1;
	private long moduleID = -1;//ģ���ʶ
	private String clients = "";//����ͻ�
	private String header = "";//�������
	private String content = "";//��������
	private long statusID = -1;//����״̬
	private Timestamp releaseDate =null;//��������
	private long userID = -1;//������
	private String userName="";//����������
	private long officeId=-1;//����officeid
	//Ϊ�˲�ѯ���������
	String fromReleaseDate ="";//����������
	String endReleaseDate ="";//�������ڵ�

	/**
	 * @return Returns the endReleaseDate.
	 */
	public String getEndReleaseDate() {
		return endReleaseDate;
	}
	/**
	 * @param endReleaseDate The endReleaseDate to set.
	 */
	public void setEndReleaseDate(String endReleaseDate) {
		this.endReleaseDate = endReleaseDate;
	}
	/**
	 * @return Returns the fromReleaseDate.
	 */
	public String getFromReleaseDate() {
		return fromReleaseDate;
	}
	/**
	 * @param fromReleaseDate The fromReleaseDate to set.
	 */
	public void setFromReleaseDate(String fromReleaseDate) {
		this.fromReleaseDate = fromReleaseDate;
	}
	/**
	 * @return Returns the clients.
	 */
	public String getClients() {
		return clients;
	}
	/**
	 * @param clients The clients to set.
	 */
	public void setClients(String clients) {
		this.clients = clients;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the header.
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header The header to set.
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID() {
		return moduleID;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
	}
	/**
	 * @return Returns the releaseDate.
	 */
//	public String getReleaseDate() {
//		return releaseDate;
//	}
//	/**
//	 * @param releaseDate The releaseDate to set.
//	 */
//	public void setReleaseDate(String releaseDate) {
//		this.releaseDate = releaseDate;
//	}
//	public void setReleaseDate(Date releaseDate) {
//		this.releaseDate =releaseDate.toString();
//	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
	/**
	 * @return Returns the userID.
	 */
	public long getUserID() {
		return userID;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	public long getModifyId() {
		return modifyId;
	}
	public void setModifyId(long modifyId) {
		this.modifyId = modifyId;
	}
	public Timestamp getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}

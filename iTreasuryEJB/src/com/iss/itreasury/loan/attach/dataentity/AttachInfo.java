/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attach.dataentity;

import java.sql.Timestamp;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachInfo implements java.io.Serializable
{
	private long lID; //标示 
	private String sShowName; //在客户端的名称
	private String sRealName; //在服务器端的名称
	private String sServerPath; //文件的内部路径
	private String sContentType; //文档的ContentType类型
	private String sMime; // 文档的Mime类型
	private long lFileID = -1;      //文件ID       
	private long lType =-1;       //文件类型     
	private long lParentType = -1; //所属指令类型 
	private long lParentID = -1;    //所属指令ID   
	private long lStatus = -1;    //状态         
	
	
	private String remark="";
	private Timestamp inputTime=null;
	private long inputUser=-1;
	private long ContractID=-1;
	private long ClientID=-1;
	private long PayFormID=-1;
	private long ArchivesTyepID=-1;
	private long officeID=-1;
	
	private String code=null;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}

	/**
	 * @return
	 */
	public long getID()
	{
		return lID;
	}

	/**
	 * @return
	 */
	public String getContentType()
	{
		return sContentType;
	}


	/**
	 * @return
	 */
	public String getMime()
	{
		return sMime;
	}

	/**
	 * @return
	 */
	public String getRealName()
	{
		return sRealName;
	}

	/**
	 * @return
	 */
	public String getShowName()
	{
		return sShowName;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		lID = l;
	}

	/**
	 * @param string
	 */
	public void setContentType(String string)
	{
		sContentType = string;
	}


	/**
	 * @param string
	 */
	public void setMime(String string)
	{
		sMime = string;
	}

	/**
	 * @param string
	 */
	public void setRealName(String string)
	{
		sRealName = string;
	}

	/**
	 * @param string
	 */
	public void setShowName(String string)
	{
		sShowName = string;
	}

	/**
	 * @return
	 */
	public String getServerPath()
	{
		return sServerPath;
	}

	/**
	 * @param string
	 */
	public void setServerPath(String string)
	{
		sServerPath = string;
	}

	/**
	 * @return
	 */
	public long getFileID()
	{
		return lFileID;
	}

	/**
	 * @return
	 */
	public long getParentID()
	{
		return lParentID;
	}

	/**
	 * @return
	 */
	public long getParentType()
	{
		return lParentType;
	}

	/**
	 * @return
	 */
	public long getStatus()
	{
		return lStatus;
	}

	/**
	 * @return
	 */
	public long getType()
	{
		return lType;
	}

	/**
	 * @param l
	 */
	public void setFileID(long l)
	{
		lFileID = l;
	}

	/**
	 * @param l
	 */
	public void setParentID(long l)
	{
		lParentID = l;
	}

	/**
	 * @param l
	 */
	public void setParentType(long l)
	{
		lParentType = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l)
	{
		lStatus = l;
	}

	/**
	 * @param l
	 */
	public void setType(long l)
	{
		lType = l;
	}

	public long getArchivesTyepID() {
		return ArchivesTyepID;
	}

	public void setArchivesTyepID(long archivesTyepID) {
		ArchivesTyepID = archivesTyepID;
	}

	public long getClientID() {
		return ClientID;
	}

	public void setClientID(long clientID) {
		ClientID = clientID;
	}

	public long getContractID() {
		return ContractID;
	}

	public void setContractID(long contractID) {
		ContractID = contractID; 
	}

	public long getPayFormID() {
		return PayFormID;
	}

	public void setPayFormID(long payFormID) {
		PayFormID = payFormID;
	}

	public Timestamp getInputTime() {
		return inputTime;
	}

	public void setInputTime(Timestamp inputTime) {
		this.inputTime = inputTime;
	}

	public long getInputUser() {
		return inputUser;
	}

	public void setInputUser(long inputUser) {
		this.inputUser = inputUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.common.attach.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachInfo extends ITreasuryBaseDataEntity implements java.io.Serializable  
{ 
	private long id;
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
	
	//产品化新加
	private long nModuleID = -1;//模块id
	private long nTransTypeID = -1;//交易类型ID
	private long nTransSubTypeID = -1;//交易子类型id，如果是结算业务，交易类型=交易子类型
	private long nCurrencyID = -1;
	private long nOfficeID = -1;
	private String transCode = "";   //交易号
	private long nClientID = -1;    //客户ID  （add 2007-04-30）
	

	public long getLFileID() {
		return lFileID;
	}

	public long getLID() {
		return lID;
	}

	public long getLParentID() {
		return lParentID;
	}

	public long getLParentType() {
		return lParentType;
	}

	public long getLStatus() {
		return lStatus;
	}

	public long getLType() {
		return lType;
	}

	public long getNCurrencyID() {
		return nCurrencyID;
	}

	public long getNModuleID() {
		return nModuleID;
	}

	public long getNOfficeID() {
		return nOfficeID;
	}

	

	public long getNTransSubTypeID() {
		return nTransSubTypeID;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
		putUsedField("ntransCode", transCode);
	}

	public long getNTransTypeID() {
		return nTransTypeID;
	}

	public String getSContentType() {
		return sContentType;
	}

	public String getSMime() {
		return sMime;
	}

	public String getSRealName() {
		return sRealName;
	}

	public String getSServerPath() {
		return sServerPath;
	}

	public String getSShowName() {
		return sShowName;
	}

	public void setLFileID(long fileID) {
		lFileID = fileID;
		putUsedField("nfiled", fileID);
	}

	public void setLID(long lid) {
		lID = lid;
	}

	public void setLParentID(long parentID) {
		lParentID = parentID;
	}

	public void setLParentType(long parentType) {
		lParentType = parentType;
	}

	public void setLStatus(long status) {
		lStatus = status;
	}

	public void setLType(long type) {
		lType = type;
	}

	public void setNCurrencyID(long currencyID) {
		nCurrencyID = currencyID;
	}

	public void setNModuleID(long moduleID) {
		nModuleID = moduleID;
	}

	public void setNOfficeID(long officeID) {
		nOfficeID = officeID;
	}



	public void setNTransSubTypeID(long transSubTypeID) {
		nTransSubTypeID = transSubTypeID;
	}

	public void setNTransTypeID(long transTypeID) {
		nTransTypeID = transTypeID;
	}

	public void setSContentType(String contentType) {
		sContentType = contentType;
	}

	public void setSMime(String mime) {
		sMime = mime;
	}

	public void setSRealName(String realName) {
		sRealName = realName;
	}

	public void setSServerPath(String serverPath) {
		sServerPath = serverPath;
	}

	public void setSShowName(String showName) {
		sShowName = showName;
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
	 * @return
	 */
	public long getModuleID()
	{
		return nModuleID;
	}

	/**
	 * @return
	 */
	public long getTransTypeID()
	{
		return nTransTypeID;
	}

	/**
	 * @return
	 */
	public long getTransSubTypeID()
	{
		return nTransSubTypeID;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return nCurrencyID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return nOfficeID;
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
		putUsedField("nParentID", lParentID);
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

	/**
	 * @param l
	 */
	public void setModuleID(long l)
	{
		nModuleID = l;
	}

	/**
	 * @param l
	 */
	public void setTransTypeID(long l)
	{
		nTransTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setTransSubTypeID(long l)
	{
		nTransSubTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		nCurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		nOfficeID = l;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		putUsedField("ID", id);
	}

	public long getNClientID() {
		return nClientID;
	}

	public void setNClientID(long clientID) {
		nClientID = clientID;
	}
}

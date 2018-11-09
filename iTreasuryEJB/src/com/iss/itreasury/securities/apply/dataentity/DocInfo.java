package com.iss.itreasury.securities.apply.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.sql.Timestamp;

public class DocInfo extends SECBaseDataEntity {

/*
 * SEC_DocInfo表
 */
	private long id;				//ID
	private long fileId;			//文件表对应的ID
	private long typeId;			//文件类型
	private long parentTypeId;		//所属指令类型
	private long parentId;			//所属指令
	private long statusId;			//状态

/*
 * FileInfo表
 */
	private long nFileType; 			//文件类型
	private String sServerPath; 		//在服务器上的路径
	private String sClientPath; 		//在客户端的路径
	private String sServerFileName;		//在服务器端的名称
	private String sClientFileName; 	//在客户端的名称
	private long nStatus; 				//状态
	private long nInputUserId; 			//录入人
	private Timestamp dtInput; 			//录入时间
	private long nModifiedId; 			//修改人id
	private Timestamp dtModified; 		//修改时间
	private String sFilemImeType; 		//文件内容类型
	private String sFileContentType; 	//文件类型
	private long bFileSucc; 			//文件操作是否成功
	private String sFiledeniedExt; 		//不允许上传的文件扩展名
	private String sFilealLowedExt; 	//允许上传的文件扩展名
	private long nMaxFileSize;			//文件大小

	/**
	 * @return
	 */
	public long getFileSucc() {
		return bFileSucc;
	}

	/**
	 * @return
	 */
	public Timestamp getInput() {
		return dtInput;
	}

	/**
	 * @return
	 */
	public Timestamp getModified() {
		return dtModified;
	}

	/**
	 * @return
	 */
	public long getFileId() {
		return fileId;
	}

	/**
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return
	 */
	public long getFileType() {
		return nFileType;
	}

	/**
	 * @return
	 */
	public long getInputUserId() {
		return nInputUserId;
	}

	/**
	 * @return
	 */
	public long getMaxFileSize() {
		return nMaxFileSize;
	}

	/**
	 * @return
	 */
	public long getModifiedId() {
		return nModifiedId;
	}

	/**
	 * @return
	 */
	public long getStatus() {
		return nStatus;
	}

	/**
	 * @return
	 */
	public long getParentId() {
		return parentId;
	}

	/**
	 * @return
	 */
	public long getParentTypeId() {
		return parentTypeId;
	}

	/**
	 * @return
	 */
	public String getClientFileName() {
		return sClientFileName;
	}

	/**
	 * @return
	 */
	public String getClientPath() {
		return sClientPath;
	}

	/**
	 * @return
	 */
	public String getFilealLowedExt() {
		return sFilealLowedExt;
	}

	/**
	 * @return
	 */
	public String getFileContentType() {
		return sFileContentType;
	}

	/**
	 * @return
	 */
	public String getFiledeniedExt() {
		return sFiledeniedExt;
	}

	/**
	 * @return
	 */
	public String getFilemImeType() {
		return sFilemImeType;
	}

	/**
	 * @return
	 */
	public String getServerFileName() {
		return sServerFileName;
	}

	/**
	 * @return
	 */
	public String getServerPath() {
		return sServerPath;
	}

	/**
	 * @return
	 */
	public long getStatusId() {
		return statusId;
	}

	/**
	 * @return
	 */
	public long getTypeId() {
		return typeId;
	}

	/**
	 * @param l
	 */
	public void setFileSucc(long l) {
		bFileSucc = l;
	}

	/**
	 * @param timestamp
	 */
	public void setInput(Timestamp timestamp) {
		dtInput = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setModified(Timestamp timestamp) {
		dtModified = timestamp;
	}

	/**
	 * @param l
	 */
	public void setFileId(long l) {
		fileId = l;
	}

	/**
	 * @param l
	 */
	public void setId(long l) {
		id = l;
	}
	
	/**
	 * @param l
	 */
	public void setFileType(long l) {
		nFileType = l;
	}

	/**
	 * @param l
	 */
	public void setInputUserId(long l) {
		nInputUserId = l;
	}

	/**
	 * @param l
	 */
	public void setMaxFileSize(long l) {
		nMaxFileSize = l;
	}

	/**
	 * @param l
	 */
	public void setModifiedId(long l) {
		nModifiedId = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l) {
		nStatus = l;
	}

	/**
	 * @param l
	 */
	public void setParentId(long l) {
		parentId = l;
	}

	/**
	 * @param l
	 */
	public void setParentTypeId(long l) {
		parentTypeId = l;
	}

	/**
	 * @param string
	 */
	public void setClientFileName(String string) {
		sClientFileName = string;
	}

	/**
	 * @param string
	 */
	public void setClientPath(String string) {
		sClientPath = string;
	}

	/**
	 * @param string
	 */
	public void setFilealLowedExt(String string) {
		sFilealLowedExt = string;
	}

	/**
	 * @param string
	 */
	public void setFileContentType(String string) {
		sFileContentType = string;
	}

	/**
	 * @param string
	 */
	public void setFiledeniedExt(String string) {
		sFiledeniedExt = string;
	}

	/**
	 * @param string
	 */
	public void setFilemImeType(String string) {
		sFilemImeType = string;
	}

	/**
	 * @param string
	 */
	public void setServerFileName(String string) {
		sServerFileName = string;
	}

	/**
	 * @param string
	 */
	public void setServerPath(String string) {
		sServerPath = string;
	}

	/**
	 * @param l
	 */
	public void setStatusId(long l) {
		statusId = l;
	}

	/**
	 * @param l
	 */
	public void setTypeId(long l) {
		typeId = l;
	}

}

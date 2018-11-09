package com.iss.sysframe.util;

import java.sql.Timestamp;
import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class DraftUploadFileInfo extends BaseDataEntity
{
	private long id = -1;
	private String sFileName = ""; //�ļ�����
	private String sServerFilePath = ""; //�������ļ�·��
	private String sFileDiscription = ""; //��������
	private Timestamp dtUpLoadDate = null;//�ϴ�����
	private long nUpLoadUserID = -1; //�ϴ���ID
	private Timestamp dtModifyDate = null;//�޸�ʱ��
	private long nModifyUserID = -1; //�޸���ID
	private long nClientId = -1; //�ͻ�ID
	private long nDraftOperationType = -1; //��Ʊҵ������
	private long nBusinessId = -1;  //��Ʊҵ��ID
	private long nStatus = -1; //״̬
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	
	public String getSFileName() {
		return sFileName;
	}
	public void setSFileName(String fileName) {
		sFileName = fileName;
		putUsedField("sFileName", sFileName);
	}
	public String getSServerFilePath() {
		return sServerFilePath;
	}
	public void setSServerFilePath(String serverFilePath) {
		sServerFilePath = serverFilePath;
		putUsedField("sServerFilePath", sServerFilePath);
	}
	public String getSFileDiscription() {
		return sFileDiscription;
	}
	public void setSFileDiscription(String fileDiscription) {
		sFileDiscription = fileDiscription;
		putUsedField("sFileDiscription", sFileDiscription);
	}
	public Timestamp getDtUpLoadDate() {
		return dtUpLoadDate;
	}
	public void setDtUpLoadDate(Timestamp dtUpLoadDate) {
		this.dtUpLoadDate = dtUpLoadDate;
		putUsedField("dtUpLoadDate", dtUpLoadDate);
	}
	public long getNUpLoadUserID() {
		return nUpLoadUserID;
	}
	public void setNUpLoadUserID(long upLoadUserID) {
		nUpLoadUserID = upLoadUserID;
		putUsedField("nUpLoadUserID", nUpLoadUserID);
	}
	public Timestamp getDtModifyDate() {
		return dtModifyDate;
	}
	public void setDtModifyDate(Timestamp dtModifyDate) {
		this.dtModifyDate = dtModifyDate;
		putUsedField("dtModifyDate", dtModifyDate);
	}
	public long getNModifyUserID() {
		return nModifyUserID;
	}
	public void setNModifyUserID(long modifyUserID) {
		nModifyUserID = modifyUserID;
		putUsedField("nModifyUserID", nModifyUserID);
	}
	public long getNClientId() {
		return nClientId;
	}
	public void setNClientId(long clientId) {
		nClientId = clientId;
		putUsedField("nClientId", nClientId);
	}
	public long getNDraftOperationType() {
		return nDraftOperationType;
	}
	public void setNDraftOperationType(long draftOperationType) {
		nDraftOperationType = draftOperationType;
		putUsedField("nDraftOperationType", nDraftOperationType);
	}
	public long getNBusinessId() {
		return nBusinessId;
	}
	public void setNBusinessId(long businessId) {
		nBusinessId = businessId;
		putUsedField("nBusinessId", nBusinessId);
	}
	public long getNStatus() {
		return nStatus;
	}
	public void setNStatus(long status) {
		nStatus = status;
		putUsedField("nStatus", nStatus);
	}
}

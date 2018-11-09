
package com.iss.itreasury.settlement.setting.dataentity;



import com.iss.itreasury.util.ITreasuryBaseDataEntity;


public class ExtSystemSettingInfo extends ITreasuryBaseDataEntity
{
	
	private long id = -1; //id
	private String sCode = "";  //�ⲿϵͳ��
	private String sName = "";  //�ⲿϵͳ����
	private String sNote = "";  //��ע
	private long lStatus = -1;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	public String getSCode() {
		return sCode;
	}
	public void setSCode(String sCode) {
		this.sCode = sCode;
		putUsedField("sCode", sCode);
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String sName) {
		this.sName = sName;
		putUsedField("sName", sName);
	}
	public String getSNote() {
		return sNote;
	}
	public void setSNote(String sNote) {
		this.sNote = sNote;
		putUsedField("sNote", sNote);
	}
	public long getLStatus() {
		return lStatus;
	}
	public void setLStatus(long lStatus) {
		this.lStatus = lStatus;
		putUsedField("lStatus", lStatus);
	}
	
	
	
	

	
}

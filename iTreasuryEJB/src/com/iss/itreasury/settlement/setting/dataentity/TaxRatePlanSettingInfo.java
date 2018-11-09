
package com.iss.itreasury.settlement.setting.dataentity;
import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

/**
* �˴���������˵����
* �������ڣ�()
* @author��luyh
*/
public class TaxRatePlanSettingInfo extends SettlementBaseDataEntity {
    /**
     * TaxRatePlanSettingInfo 
     */
	private long officeID;//	���´�ID
	private String name;//	��Ϣ˰���ʼƻ�����
	private String code;//	��Ϣ˰���ʼƻ����
	private long inputUserID;//	¼����ID
	private Timestamp inputDate;//	¼��ʱ��
	private long statusID;//	״̬
	private long modifyUserID;//�޸���ID	MODIFYUSETID;
	private Timestamp modifyDate;//�޸�ʱ�� MODIFYDATE

	public void setOfficeID(long officeID){
	this.officeID = officeID;
	putUsedField("officeID",officeID);
	}
	public long getOfficeID(){
	return officeID;
	}
	public void setName(String name){
	this.name = name;
	putUsedField("name",name);
	}
	public String getName(){
	return name;
	}
	public void setCode(String code){
	this.code = code;
	putUsedField("code",code);
	}
	public String getCode(){
	return code;
	}
	public void setInputUserID(long inputUserID){
	this.inputUserID = inputUserID;
	putUsedField("inputUserID",inputUserID);
	}
	public long getInputUserID(){
	return inputUserID;
	}
	public void setInputDate(Timestamp inputDate){
	this.inputDate = inputDate;
	putUsedField("inputDate",inputDate);
	}
	public Timestamp getInputDate(){
	return inputDate;
	}
	public void setStatusID(long statusID){
	this.statusID = statusID;
	putUsedField("statusID",statusID);
	}
	public long getStatusID(){
	return statusID;
	}
	public void setModifyUserID(long modifyUserID){
	this.modifyUserID = modifyUserID;
	putUsedField("modifyUserID",modifyUserID);
	}
	public long getModifyUserID(){
	return modifyUserID;
	}
	public void setModifyDate(Timestamp modifyDate){
	this.modifyDate = modifyDate;
	putUsedField("modifyDate",modifyDate);
	}
	public Timestamp getModifyDate(){
	return modifyDate;
	}
}

package com.iss.itreasury.settlement.setting.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
public class TaxRatePlanSettingItemInfo extends SettlementBaseDataEntity  {

 	private long officeID		;//���´�id
	private long taxRatePlanID	;//	��Ϣ˰���ʼƻ�ID
	private long serialNo		;//	���
	private double taxRate		;//	��Ϣ˰����
	private long inputUserID	;//¼����id
	private Timestamp inputDate	;//¼��ʱ��
	private long statusID		;//״̬
	private long modifyUserID	;//�޸���ID	MODIFYUSETID;
	private Timestamp modifyDate;//�޸�ʱ�� MODIFYDATE

	public void setTaxRatePlanID(long taxRatePlanID){
	this.taxRatePlanID = taxRatePlanID;
	putUsedField("taxRatePlanID",taxRatePlanID);
	}
	public long getTaxRatePlanID(){
	return taxRatePlanID;
	}
	public void setSerialNo(long serialNo){
	this.serialNo = serialNo;
	putUsedField("serialNo",serialNo);
	}
	public long getSerialNo(){
	return serialNo;
	}
	public void setTaxRate(double taxRate){
	this.taxRate = taxRate;
	putUsedField("taxRate",taxRate);
	}
	public double getTaxRate(){
	return taxRate;
	}
	public void setOfficeID(long officeID){
	this.officeID = officeID;
	putUsedField("officeID",officeID);
	}
	public long getOfficeID(){
	return officeID;
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
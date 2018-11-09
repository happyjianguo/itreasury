package com.iss.itreasury.ebank.fundplan.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author xintan
 *
 *	�ʽ�ƻ�ģ������Info
 *  ��ӦOB_CAPITALPLANCONFIGMODEL���е��ֶ�
 */
public class CapitalPlanConfigModelInfo extends ITreasuryBaseDataEntity{

	private long Id = -1;              //ID
	private long currencyId = -1;      //����ID
	private long officeId = -1;        //���´�ID
	private String name = null;          //����ģ������
	private String code = null;          //����ģ�����
	private long statusId = -1;        //״̬
	private long modelType = -1 ;		//ģ������
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
		putUsedField("code", code);
	}
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
		putUsedField("currencyId", currencyId);		
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
		putUsedField("id", id);				
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);				
	}
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
		putUsedField("officeId", officeId);				
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);				
	}
	public long getModelType() {
		return modelType;
	}
	
	public void setModelType(long modelType) {
		this.modelType = modelType;
		putUsedField("modelType", modelType);				
	}
}

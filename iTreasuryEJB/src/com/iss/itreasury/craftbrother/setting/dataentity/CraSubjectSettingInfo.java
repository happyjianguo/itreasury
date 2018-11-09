package com.iss.itreasury.craftbrother.setting.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CraSubjectSettingInfo extends ITreasuryBaseDataEntity{

	private long id = -1;// 主键
	
	private long businessTypeID = -1;//同业业务类型
	
	private long isExistSubclass = -1;//是否下级分类
	
	private long isCounterPart = -1;//是否按交易对手分类
	
	private long isSubType = -1;//交易子类型分类
	
	private long officeID = -1;//办事处
	
	private long currencyID = -1;//币种
	
	private long statusID = -1;//记录状态1：有效 0：无效
	
	
	
	public long getBusinessTypeID() {
		return businessTypeID;
	}

	public void setBusinessTypeID(long businessTypeID) {
		this.businessTypeID = businessTypeID;
		putUsedField("businessTypeID", businessTypeID);
	}

	public long getIsExistSubclass() {
		return isExistSubclass;
	}

	public void setIsExistSubclass(long isExistSubclass) {
		this.isExistSubclass = isExistSubclass;
		putUsedField("isExistSubclass", isExistSubclass);
	}

	public long getIsCounterPart() {
		return isCounterPart;
	}

	public void setIsCounterPart(long isCounterPart) {
		this.isCounterPart = isCounterPart;
		putUsedField("isCounterPart", isCounterPart);
	}


	public long getIsSubType() {
		return isSubType;
	}

	public void setIsSubType(long isSubType) {
		this.isSubType = isSubType;
		putUsedField("isSubType", isSubType);
	}

	public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
		putUsedField("officeID", officeID);
	}

	public long getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
		putUsedField("currencyID", currencyID);
	}

	public long getStatusID() {
		return statusID;
	}

	public void setStatusID(long statusID) {
		this.statusID = statusID;
		putUsedField("statusID", statusID);
	}

	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(long id) {
		
		this.id = id;
		putUsedField("id", id);
		
	}

}

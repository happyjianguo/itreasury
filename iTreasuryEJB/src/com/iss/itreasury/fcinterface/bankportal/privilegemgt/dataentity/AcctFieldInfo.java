package com.iss.itreasury.fcinterface.bankportal.privilegemgt.dataentity;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;

public class AcctFieldInfo extends BaseDataEntity 
{
	private long privilegeSettingID = -1;
	private String fieldName = null;
	private String fieldValue = null;
	
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
		putUsedField("fieldname", this.fieldName);
	}
	public String getFieldValue()
	{
		return fieldValue;
	}
	public void setFieldValue(String fieldValue)
	{
		this.fieldValue = fieldValue;
		putUsedField("fieldvalue", this.fieldValue);
	}
	public long getPrivilegeSettingID()
	{
		return privilegeSettingID;
	}
	public void setPrivilegeSettingID(long privilegeSettingID)
	{
		this.privilegeSettingID = privilegeSettingID;
		putUsedField("privilegesettingid", this.privilegeSettingID);
	}
	public long getId()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id)
	{
		// TODO Auto-generated method stub
		
	}	
}

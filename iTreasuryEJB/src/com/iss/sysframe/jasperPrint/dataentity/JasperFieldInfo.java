package com.iss.sysframe.jasperPrint.dataentity;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class JasperFieldInfo extends BaseDataEntity{
	
	private String fieldName="";

	private String fildType="";
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFildType() {
		return fildType;
	}

	public void setFildType(String fildType) {
		this.fildType = fildType;
	}

	
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setId(long arg0) {
		// TODO Auto-generated method stub
		
	}

}

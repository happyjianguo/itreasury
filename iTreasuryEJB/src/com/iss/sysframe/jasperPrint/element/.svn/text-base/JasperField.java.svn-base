package com.iss.sysframe.jasperPrint.element;

import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperFieldInfo;

public class JasperField {

	private String fieldText="";
	private JasperFieldInfo fieldInfo=new JasperFieldInfo();

	public String getFieldText() {
		return fieldText;
	}

	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	public JasperFieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(JasperFieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}


	public void createField(JasperFieldInfo info) 
	{
	StringBuffer fieldBuffer=null;
	try{
			
		fieldBuffer=new StringBuffer();		
		fieldBuffer.append("<field name=\""+info.getFieldName()+"\" class=\""+info.getFildType()+"\"/>");
		fieldText=fieldBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

}

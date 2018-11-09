package com.iss.sysframe.jasperPrint.dataentity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
import com.iss.sysframe.jasperPrint.util.FontStyle;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperBandConfigInfo  extends BaseDataEntity{

	private long bandHeight=0;
	private String splitType="";
	private String bandContext="";

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long arg0) {
		// TODO Auto-generated method stub
		
	}

	public long getBandHeight() {
		return bandHeight;
	}

	public void setBandHeight(long bandHeight) {
		this.bandHeight = bandHeight;
	}
	
	public String getSplitType() {
		return splitType;
	}

	public void setSplitType(String splitType) {
		this.splitType = splitType;
	}

	public String getBandContext() {
		return bandContext;
	}

	public void setBandContext(String bandContext) {
		this.bandContext = bandContext;
	}

	public String jasperPrintElementConfigSetting(String fileContext) 
	{
	String newFileContext=fileContext;
	try{
			
			Field[] fields = this.getClass().getDeclaredFields(); 
			Field curField = null;
			String fieldName = null;
			
		    String getMethodName = null;
		    Method curGetMethod = null;
		    Object objValue = null;
			for(int i=0; i<fields.length; i++){
				curField = fields[i];
				fieldName = curField.getName();
				
				getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				curGetMethod = this.getClass().getMethod(getMethodName, new Class[]{});
				objValue = curGetMethod.invoke(this, new Object[]{});
				
				newFileContext=JasperPrintUtil.replaceSomeOldWithNew("#"+fieldName ,objValue.toString(),newFileContext);
			
			}
		
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		return newFileContext;
		
	}
	


}

package com.iss.sysframe.jasperPrint.dataentity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.iss.sysframe.base.dataentity.BaseDataEntity;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperPageConfigInfo extends BaseDataEntity{

	private long reportPageWidth=595;
	
	private long reportPageHeight=842;
	
	private long reportColumnWidth=555;
	
	private long reportLeftMargin=20;
	
	private long reportRightMargin=20;
	
	private long reportTopMargin=50;
	
	private long reportBottomMargin=50;
	
	private String reportName="";
	 
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	 
	public void setId(long arg0) {
		// TODO Auto-generated method stub
		
	}

	public long getReportPageWidth() {
		return reportPageWidth;
	}

	public void setReportPageWidth(long reportPageWidth) {
		this.reportPageWidth = reportPageWidth;
	}

	public long getReportPageHeight() {
		return reportPageHeight;
	}

	public void setReportPageHeight(long reportPageHeight) {
		this.reportPageHeight = reportPageHeight;
	}

	public long getReportColumnWidth() {
		return reportColumnWidth;
	}

	public void setReportColumnWidth(long reportColumnWidth) {
		this.reportColumnWidth = reportColumnWidth;
	}

	public long getReportLeftMargin() {
		return reportLeftMargin;
	}

	public void setReportLeftMargin(long reportLeftMargin) {
		this.reportLeftMargin = reportLeftMargin;
	}

	public long getReportRightMargin() {
		return reportRightMargin;
	}

	public void setReportRightMargin(long reportRightMargin) {
		this.reportRightMargin = reportRightMargin;
	}

	public long getReportTopMargin() {
		return reportTopMargin;
	}

	public void setReportTopMargin(long reportTopMargin) {
		this.reportTopMargin = reportTopMargin;
	}

	public long getReportBottomMargin() {
		return reportBottomMargin;
	}

	public void setReportBottomMargin(long reportBottomMargin) {
		this.reportBottomMargin = reportBottomMargin;
	}

	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String jasperPrintPageConfigSetting(String fileContext) 
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

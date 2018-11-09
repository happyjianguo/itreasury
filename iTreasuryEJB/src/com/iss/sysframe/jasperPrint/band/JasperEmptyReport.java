package com.iss.sysframe.jasperPrint.band;

import java.util.Collection;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperPageConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperEmptyReport {

	private String txtReport="";
	private JasperPageConfigInfo pageInfo=new JasperPageConfigInfo();

	public String getTxtReport() {
		return txtReport;
	}

	public void setTxtReport(String txtReport) {
		this.txtReport = txtReport;
	}

	public JasperPageConfigInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(JasperPageConfigInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public void createEmptyJasperReport(JasperPageConfigInfo info) 
	{
	StringBuffer txtJasperReportBuffer=null;
	try{
			
		txtJasperReportBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtJasperReportBuffer.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+"<!-- Created with Jaspersoft Studio -->\n"
				+"<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" " 
				+"name=\""+info.getReportName()+"\" " 
				+"language=\"groovy\" "
				+"pageWidth=\""+info.getReportPageWidth()+"\" "
				+"pageHeight=\""+info.getReportPageHeight()+"\" "
				+"columnWidth=\""+info.getReportColumnWidth()+"\" " 
				+"leftMargin=\""+info.getReportLeftMargin()+"\" " 
				+"rightMargin=\""+info.getReportRightMargin()+"\" " 
				+"topMargin=\""+info.getReportTopMargin()+"\" " 
				+"bottomMargin=\""+info.getReportBottomMargin()+"\">\n"
				+"<property name=\"com.jaspersoft.studio.data.defaultdataadapter\" value=\"One Empty Record\"/>\n"	
				+"###reportContext\n"
				+"</jasperReport>");
		

		txtReport=txtJasperReportBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}

	}

	public void addContextToJasperRepoet(String elementContext) 
	{
	try{

		txtReport=JasperPrintUtil.replaceSomeOldWithNew("###reportContext" ,elementContext,txtReport);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToTitleBand(Collection elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtReport=JasperPrintUtil.replaceSomeOldWithNew("###reportContext" ,elements.toString(),txtReport);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}


}

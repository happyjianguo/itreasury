package com.iss.sysframe.jasperPrint.band;

import java.util.ArrayList;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperPageFooter {

	private String txtPageFooterBand="";
	
	private ArrayList pageFooterArrayList = null;
	
	private JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();
	
	public String getTxtPageFooterBand() {
		return txtPageFooterBand;
	}

	public void setTxtPageFooterBand(String txtPageFooterBand) {
		this.txtPageFooterBand = txtPageFooterBand;
	}

	public JasperBandConfigInfo getBandInfo() {
		return bandInfo;
	}

	public void setBandInfo(JasperBandConfigInfo bandInfo) {
		this.bandInfo = bandInfo;
	}

	public void createPageFooterBand(JasperBandConfigInfo info) 
	{
	StringBuffer txtPageFooterBandBuffer=null;
	try{
			
		txtPageFooterBandBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtPageFooterBandBuffer.append(
				"<pageFooter>\n" 
				+"<band height=\""+info.getBandHeight()+"\" splitType=\""+info.getSplitType()+"\">\n" 
				+"##PageFooterContext" 
				+"</band>\n" 
				+"</pageFooter>\n");

		txtPageFooterBand=txtPageFooterBandBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToPageFooterBand(String elementContext) 
	{
	try{

		txtPageFooterBand=JasperPrintUtil.replaceSomeOldWithNew("##PageFooterContext" ,elementContext,txtPageFooterBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToPageFooterBand(ArrayList elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtPageFooterBand=JasperPrintUtil.replaceSomeOldWithNew("##PageFooterContext" ,elements.toString(),txtPageFooterBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public ArrayList getPageFooterArrayList() {
		return pageFooterArrayList;
	}

	public void setPageFooterArrayList(ArrayList pageFooterArrayList) {
		this.pageFooterArrayList = pageFooterArrayList;
	}

}

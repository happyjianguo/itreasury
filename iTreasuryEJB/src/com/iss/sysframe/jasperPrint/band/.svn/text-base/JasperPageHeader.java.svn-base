package com.iss.sysframe.jasperPrint.band;

import java.util.ArrayList;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperPageHeader {

	private String txtPageHeaderBand="";
	
	private ArrayList pageHeaderArrayList = null;
	
	
	private JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();

	
	public String getTxtPageHeaderBand() {
		return txtPageHeaderBand;
	}

	public void setTxtPageHeaderBand(String txtPageHeaderBand) {
		this.txtPageHeaderBand = txtPageHeaderBand;
	}
	
	public ArrayList getPageHeaderArrayList() {
		return pageHeaderArrayList;
	}

	public void setPageHeaderArrayList(ArrayList pageHeaderArrayList) {
		this.pageHeaderArrayList = pageHeaderArrayList;
	}

	public JasperBandConfigInfo getBandInfo() {
		return bandInfo;
	}

	public void setBandInfo(JasperBandConfigInfo bandInfo) {
		this.bandInfo = bandInfo;
	}

	public void createPageHeaderBand(JasperBandConfigInfo info) 
	{
	StringBuffer txtPageHeaderBandBuffer=null;
	try{
			
		txtPageHeaderBandBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtPageHeaderBandBuffer.append(
				"<pageHeader>\n" 
				+"<band height=\""+info.getBandHeight()+"\" splitType=\""+info.getSplitType()+"\">\n" 
				+"##PageHeaderContext" 
				+"</band>\n" 
				+"</pageHeader>\n");

		txtPageHeaderBand=txtPageHeaderBandBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToPageHeaderBand(String elementContext) 
	{
	try{

		txtPageHeaderBand=JasperPrintUtil.replaceSomeOldWithNew("##PageHeaderContext" ,elementContext,txtPageHeaderBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToPageHeaderBand(ArrayList elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtPageHeaderBand=JasperPrintUtil.replaceSomeOldWithNew("##PageHeaderContext" ,elements.toString(),txtPageHeaderBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

}

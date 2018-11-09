package com.iss.sysframe.jasperPrint.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperColumnHeader {

	private String txtColHeaderBand="";
	
	private ArrayList titleArrayList = null;
	
	public String getTxtColHeaderBand() {
		return txtColHeaderBand;
	}

	public void setTxtColHeaderBand(String txtColHeaderBand) {
		this.txtColHeaderBand = txtColHeaderBand;
	}
	
	public ArrayList getTitleArrayList() {
		return titleArrayList;
	}

	public void setTitleArrayList(ArrayList titleArrayList) {
		this.titleArrayList = titleArrayList;
	}
	
	private JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();

	
	public JasperBandConfigInfo getBandInfo() {
		return bandInfo;
	}

	public void setBandInfo(JasperBandConfigInfo bandInfo) {
		this.bandInfo = bandInfo;
	}

	public void createColHeaderBand(JasperBandConfigInfo info) 
	{
	StringBuffer txtColHeaderBandBuffer=null;
	try{
			
		txtColHeaderBandBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtColHeaderBandBuffer.append(
				"<columnHeader>\n" 
				+"<band height=\""+info.getBandHeight()+"\" splitType=\""+info.getSplitType()+"\">\n" 
				+"##ColHeaderContext" 
				+"</band>\n" 
				+"</columnHeader>\n");

		txtColHeaderBand=txtColHeaderBandBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToColHeaderBand(String elementContext) 
	{
	try{

		txtColHeaderBand=JasperPrintUtil.replaceSomeOldWithNew("##ColHeaderContext" ,elementContext,txtColHeaderBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToColHeaderBand(ArrayList elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtColHeaderBand=JasperPrintUtil.replaceSomeOldWithNew("##ColHeaderContext" ,elements.toString(),txtColHeaderBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

}

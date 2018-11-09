package com.iss.sysframe.jasperPrint.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperColumnFooter {

	private String txtColFooterBand="";
	
	private ArrayList colFooterArrayList = null;
	
	private JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();
	
	public String getTxtColFooterBand() {
		return txtColFooterBand;
	}

	public void setTxtColFooterBand(String txtColFooterBand) {
		this.txtColFooterBand = txtColFooterBand;
	}
	
	public ArrayList getColFooterArrayList() {
		return colFooterArrayList;
	}

	public void setColFooterArrayList(ArrayList colFooterArrayList) {
		this.colFooterArrayList = colFooterArrayList;
	}

	public JasperBandConfigInfo getBandInfo() {
		return bandInfo;
	}

	public void setBandInfo(JasperBandConfigInfo bandInfo) {
		this.bandInfo = bandInfo;
	}


	public void createColFooterBand(JasperBandConfigInfo info) 
	{
	StringBuffer txtColFooterBandBuffer=null;
	try{
			
		txtColFooterBandBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtColFooterBandBuffer.append(
				"<columnFooter>\n" 
				+"<band height=\""+info.getBandHeight()+"\" splitType=\""+info.getSplitType()+"\">\n" 
				+"##ColFooterContext" 
				+"</band>\n" 
				+"</columnFooter>\n");

		txtColFooterBand=txtColFooterBandBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToColFooterBand(String elementContext) 
	{
	try{

		txtColFooterBand=JasperPrintUtil.replaceSomeOldWithNew("##ColFooterContext" ,elementContext,txtColFooterBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToColFooterBand(ArrayList elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtColFooterBand=JasperPrintUtil.replaceSomeOldWithNew("##ColFooterContext" ,elements.toString(),txtColFooterBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}



}

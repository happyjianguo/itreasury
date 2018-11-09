package com.iss.sysframe.jasperPrint.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperDetail {

	private String txtDetailBand="";
	
	private ArrayList rowArrayList = null;
	
	private ArrayList txtFieldArrayList = null;
	
	private ArrayList txtStaticArrayList = null; 
	
	private JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();
	
	public String getTxtDetailBand() {
		return txtDetailBand;
	}

	public void setTxtDetailBand(String txtDetailBand) {
		this.txtDetailBand = txtDetailBand;
	}
	
	public ArrayList getRowArrayList() {
		return rowArrayList;
	}

	public void setRowArrayList(ArrayList rowArrayList) {
		this.rowArrayList = rowArrayList;
	}

	public ArrayList getTxtFieldArrayList() {
		return txtFieldArrayList;
	}

	public void setTxtFieldArrayList(ArrayList txtFieldArrayList) {
		this.txtFieldArrayList = txtFieldArrayList;
	}

	
	public ArrayList getTxtStaticArrayList() {
		return txtStaticArrayList;
	}

	public void setTxtStaticArrayList(ArrayList txtStaticArrayList) {
		this.txtStaticArrayList = txtStaticArrayList;
	}

	public JasperBandConfigInfo getBandInfo() {
		return bandInfo;
	}

	public void setBandInfo(JasperBandConfigInfo bandInfo) {
		this.bandInfo = bandInfo;
	}

	public void createDetailBand(JasperBandConfigInfo info) 
	{
	StringBuffer txtDetailBandBuffer=null;
	try{
			
		txtDetailBandBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtDetailBandBuffer.append(
				"<detail>\n" 
				+"<band height=\""+info.getBandHeight()+"\" splitType=\""+info.getSplitType()+"\">\n" 
				+"##DetailContext" 
				+"</band>\n" 
				+"</detail>\n");

		txtDetailBand=txtDetailBandBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToDetailBand(String elementContext) 
	{
	try{

		txtDetailBand=JasperPrintUtil.replaceSomeOldWithNew("##DetailContext" ,elementContext,txtDetailBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToDetailBand(Collection elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtDetailBand=JasperPrintUtil.replaceSomeOldWithNew("##DetailContext" ,elements.toString(),txtDetailBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

}

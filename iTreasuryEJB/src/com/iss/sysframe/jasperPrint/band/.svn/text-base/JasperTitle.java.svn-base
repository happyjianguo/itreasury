package com.iss.sysframe.jasperPrint.band;


import java.util.ArrayList;
import java.util.Iterator;
import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperTitle {

	private String txtTitleBand="";
	
	private JasperBandConfigInfo bandInfo=new JasperBandConfigInfo();
	
	private ArrayList staticTexts=new ArrayList();
	
	public String getTxtTitleBand() {
		return txtTitleBand;
	}

	public void setTxtTitleBand(String txtTitleBand) {
		this.txtTitleBand = txtTitleBand;
	}
	
	public JasperBandConfigInfo getBandInfo() {
		return bandInfo;
	}

	public void setBandInfo(JasperBandConfigInfo bandInfo) {
		this.bandInfo = bandInfo;
	}

	public ArrayList getStaticTexts() {
		return staticTexts;
	}

	public void setStaticTexts(ArrayList staticTexts) {
		this.staticTexts = staticTexts;
	}

	public void createTitleBand(JasperBandConfigInfo info) 
	{
	StringBuffer txtTitleBandBuffer=null;
	try{
			
		txtTitleBandBuffer=new StringBuffer();
		//System.out.println(colTableTitleinfo.getTitleName());
		txtTitleBandBuffer.append(
				"<title>\n" 
				+"<band height=\""+info.getBandHeight()+"\" splitType=\""+info.getSplitType()+"\">\n" 
				+"##TitleContext" 
				+"</band>\n" 
				+"</title>\n");

		txtTitleBand=txtTitleBandBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}

	}

	public void addElementToTitleBand(String elementContext) 
	{
	try{

		txtTitleBand=JasperPrintUtil.replaceSomeOldWithNew("##TitleContext" ,elementContext,txtTitleBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void addElementToTitleBand(ArrayList elementContexts) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(elementContexts!=null){
			Iterator it=elementContexts.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtTitleBand=JasperPrintUtil.replaceSomeOldWithNew("##TitleContext" ,elements.toString(),txtTitleBand);

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

}

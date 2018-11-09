package com.iss.sysframe.jasperPrint.band;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.iss.sysframe.jasperPrint.dataentity.JasperBandConfigInfo;
import com.iss.sysframe.jasperPrint.util.JasperPrintUtil;

public class JasperFieldBand {

	private String txtFieldBand="";

	private ArrayList fieldArrayList = null;	

	public String getTxtFieldBand() {
		return txtFieldBand;
	}

	public void setTxtFieldBand(String txtFieldBand) {
		this.txtFieldBand = txtFieldBand;
	}

	public ArrayList getFieldArrayList() {
		return fieldArrayList;
	}

	public void setFieldArrayList(ArrayList fieldArrayList) {
		this.fieldArrayList = fieldArrayList;
	}

	public void addFieldToFieldBand(Collection fieldColl) 
	{
	try{
		StringBuffer elements=new StringBuffer();
		if(fieldColl!=null){
			Iterator it=fieldColl.iterator();
			
			while(it.hasNext()){
				elements.append((String)it.next());
			}
		}
		txtFieldBand=elements.toString();

	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

}

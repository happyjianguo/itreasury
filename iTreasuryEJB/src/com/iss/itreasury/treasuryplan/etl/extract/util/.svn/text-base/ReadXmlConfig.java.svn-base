/*
 * Created on 2004-12-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.etl.extract.util;

import org.apache.commons.digester.Digester;


import com.iss.itreasury.treasuryplan.etl.extract.dataentity.conf.*;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadXmlConfig {

	private static String xmlFileName="plan_transConfig.xml";
	/**
	 * 
	 */
	public ReadXmlConfig() {

	}
	
	public static XmlConfigInfo getXmlConfig() throws Exception{
		
		//java.io.File srcfile = new java.io.File(ReadXmlConfig.class.getResource("").getPath()+"/"+xmlFileName);
		java.io.File srcfile = new java.io.File(xmlFileName);
		Digester digester = new Digester();
		digester.addObjectCreate("xmlConfig",XmlConfigInfo.class);
		digester.addSetProperties("xmlConfig");
		//digester.addCallMethod("xmlConfig/title","setTitle",0);
		digester.addObjectCreate("xmlConfig/trans",TransConfigInfo.class);
		digester.addSetProperties("xmlConfig/trans");
		digester.addSetNext("xmlConfig/trans","addTrans");

		digester.addCallMethod("xmlConfig/trans/strSql","setStrSql",0);
		digester.addObjectCreate("xmlConfig/trans/field",FieldInfo.class);
		digester.addSetProperties("xmlConfig/trans/field");
		digester.addSetNext("xmlConfig/trans/field","addFields");
		//digester.addCallParam("page/form/component/name", 0);
		
		digester.addCallMethod("xmlConfig/trans/field/entityFieldName","setEntityFieldName",0);
		digester.addCallMethod("xmlConfig/trans/field/databaseFieldName","setDatabaseFieldName",0);
		digester.addCallMethod("xmlConfig/trans/field/isGetDataFromDatabase","setIsGetDataFromDatabase",0,new java.lang.Class[]{boolean.class});
		digester.addCallMethod("xmlConfig/trans/field/defaultValue","setDefaultValue",0);
		
		return (XmlConfigInfo)digester.parse(srcfile); 
	}

	public static void main(String[] args) {
		try {
			XmlConfigInfo x = ReadXmlConfig.getXmlConfig();
			if(x==null){
				
			}else{
				if(x.getTrans()==null || x.getTrans().size()==0){
					
				}else{
					java.util.Vector vtrans = x.getTrans();
					
					for(int i = 0 ;i<vtrans.size();i++){
						TransConfigInfo tranInfo = (TransConfigInfo)vtrans.get(i);
						System.out.println("======trans begin====");
						System.out.println("transName:"+tranInfo.getTransName());
						System.out.println("transId:"+tranInfo.getTransId());
						System.out.println("strSql:"+tranInfo.getStrSql());
						java.util.Vector vfield = tranInfo.getFields();
						if(vfield==null){
							
						}else{
							for(int j = 0 ; j<vfield.size();j++){
								System.out.println("=====field begin===");
								FieldInfo fieldInfo = (FieldInfo)vfield.get(j);
								System.out.println("EntityFieldName:"+fieldInfo.getEntityFieldName());
								System.out.println("DatabaseFieldName:"+fieldInfo.getDatabaseFieldName());
								System.out.println("isGetDataFromDatabase:"+fieldInfo.isGetDataFromDatabase());
								System.out.println("DefaultValue:"+fieldInfo.getDefaultValue());
								System.out.println("=====field end===");
							}
						}
						System.out.println("======trans end====");
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

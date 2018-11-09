/*
 * Created on 2004-12-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.treasuryplan.etl.extract.dataentity.conf;

import java.util.Vector;

/**
 * @author ycliu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransConfigInfo {

	private String transName="";
	private String transId="";
	private String strSql ="";
	
	private Vector fields = new Vector();
	
	
	/**
	 * @return Returns the fields.
	 */
	public Vector getFields() {
		return fields;
	}

	public void addFields(FieldInfo fieldInfo) {
		if(fieldInfo!=null)
		   fields.add(fieldInfo);
	}
	/**
	 * @return Returns the strSql.
	 */
	public String getStrSql() {
		return strSql.trim();
	}
	/**
	 * @param strSql The strSql to set.
	 */
	public void setStrSql(String strSql) {
		this.strSql = strSql;
	}
	/**
	 * @return Returns the transId.
	 */
	public String getTransId() {
		return transId.trim();
	}
	/**
	 * @param transId The transId to set.
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}
	/**
	 * @return Returns the transName.
	 */
	public String getTransName() {
		return transName.trim();
	}
	/**
	 * @param transName The transName to set.
	 */
	public void setTransName(String transName) {
		this.transName = transName;
	}
	/**
	 * 
	 */
	public TransConfigInfo() {
		
	}

	public static void main(String[] args) {
	}
}

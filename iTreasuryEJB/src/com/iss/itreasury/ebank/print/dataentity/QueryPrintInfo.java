/*
 * Created on 2006-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.ebank.print.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryPrintInfo extends ITreasuryBaseDataEntity
{
	private long id = -1;  //û������
	private long setid = -1;
	private String setname = "";
	private long templateid = -1;
	private String templatename = "";
	private long templateno = -1;
	private long relationid = -1;
	private long relationtypeid = -1;
	private long relationdeptid = -1;
	private long billsegment = -1;
	private long printnum = -1;
	private long countnum = -1;  //������������
	
	/**
	 * @return Returns the countnum.
	 */
	public long getCountnum() {
		return countnum;
	}
	/**
	 * @param countnum The countnum to set.
	 */
	public void setCountnum(long countnum) {
		this.countnum = countnum;
		putUsedField("countnum",countnum);
	}
	/**
	 * @return Returns the printnum.
	 */
	public long getPrintnum() {
		return printnum;
	}
	/**
	 * @param printnum The printnum to set.
	 */
	public void setPrintnum(long printnum) {
		this.printnum = printnum;
		putUsedField("printnum",printnum);
	}
	/**
	 * @return Returns the billsegment.
	 */
	public long getBillsegment() {
		return billsegment;
	}
	/**
	 * @param billsegment The billsegment to set.
	 */
	public void setBillsegment(long billsegment) {
		this.billsegment = billsegment;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id",id);
	}
	/**
	 * @return Returns the relationdeptid.
	 */
	public long getRelationdeptid() {
		return relationdeptid;
	}
	/**
	 * @param relationdeptid The relationdeptid to set.
	 */
	public void setRelationdeptid(long relationdeptid) {
		this.relationdeptid = relationdeptid;
		putUsedField("relationdeptid",relationdeptid);
	}
	/**
	 * @return Returns the relationid.
	 */
	public long getRelationid() {
		return relationid;
	}
	/**
	 * @param relationid The relationid to set.
	 */
	public void setRelationid(long relationid) {
		this.relationid = relationid;
		putUsedField("relationid",relationid);
	}
	/**
	 * @return Returns the relationtypeid.
	 */
	public long getRelationtypeid() {
		return relationtypeid;
	}
	/**
	 * @param relationtypeid The relationtypeid to set.
	 */
	public void setRelationtypeid(long relationtypeid) {
		this.relationtypeid = relationtypeid;
		putUsedField("relationtypeid",relationtypeid);
	}
	/**
	 * @return Returns the setid.
	 */
	public long getSetid() {
		return setid;
	}
	/**
	 * @param setid The setid to set.
	 */
	public void setSetid(long setid) {
		this.setid = setid;
		putUsedField("setid",setid);
	}
	/**
	 * @return Returns the setname.
	 */
	public String getSetname() {
		return setname;
	}
	/**
	 * @param setname The setname to set.
	 */
	public void setSetname(String setname) {
		this.setname = setname;
		putUsedField("setname",setname);
	}
	/**
	 * @return Returns the templateid.
	 */
	public long getTemplateid() {
		return templateid;
	}
	/**
	 * @param templateid The templateid to set.
	 */
	public void setTemplateid(long templateid) {
		this.templateid = templateid;
		putUsedField("templateid",templateid);
	}
	/**
	 * @return Returns the templatename.
	 */
	public String getTemplatename() {
		return templatename;
	}
	/**
	 * @param templatename The templatename to set.
	 */
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
		putUsedField("templatename",templatename);
	}
	/**
	 * @return Returns the templateno.
	 */
	public long getTemplateno() {
		return templateno;
	}
	/**
	 * @param templateno The templateno to set.
	 */
	public void setTemplateno(long templateno) {
		this.templateno = templateno;
		putUsedField("templateno",templateno);
	}

}
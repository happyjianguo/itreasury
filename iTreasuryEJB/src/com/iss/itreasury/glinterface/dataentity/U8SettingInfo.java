/*
 * Created on 2004-2-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.dataentity;
import java.io.Serializable;
/**
 * @author lixr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class U8SettingInfo implements Serializable
{
	
	private long		Id						= -1 ;     
	private long		officeID				= -1 ;     //办事处
	private long 		currencyID			    = -1 ;     //币种
	private String		GLNAME				= "U850" ; 	 //总账类型
	private String		GLSENDER		        = "" ;   //用友远程登录注册号
	private String		GLVOUCHERTYPE	        = "记" ;	   //用友记账类型	
	private String		GLUSERNAME			= "iss" ;     //总账用户
	private String		GLPASSWORD			= "isoftstone" ; //总账用户密码
	private String		GLDBIP		        = "" ;     //数据库IP
	private String		GLDBPORT	        = "1433" ;	   //数据库端口	
	private String		GLDBUSERNAME		= "sa" ;     //用户
	private String		GLDBPASSWORD		= "" ;     //密码
	private String		GLDBDATABASENAME    = "" ;	   //数据库名称
	private String		GLRESPONSEHTTP      = "" ;	   //用友Eai地址
	private long		statusID			    = -1 ;     //状态
	
	
	

	/**
	 * @return Returns the currencyID.
	 */
	public long getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID The currencyID to set.
	 */
	public void setCurrencyID(long currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return Returns the gLDBDATABASENAME.
	 */
	public String getGLDBDATABASENAME() {
		return GLDBDATABASENAME;
	}
	/**
	 * @param gldbdatabasename The gLDBDATABASENAME to set.
	 */
	public void setGLDBDATABASENAME(String gldbdatabasename) {
		GLDBDATABASENAME = gldbdatabasename;
	}
	/**
	 * @return Returns the gLDBIP.
	 */
	public String getGLDBIP() {
		return GLDBIP;
	}
	/**
	 * @param gldbip The gLDBIP to set.
	 */
	public void setGLDBIP(String gldbip) {
		GLDBIP = gldbip;
	}
	/**
	 * @return Returns the gLDBPASSWORD.
	 */
	public String getGLDBPASSWORD() {
		return GLDBPASSWORD;
	}
	/**
	 * @param gldbpassword The gLDBPASSWORD to set.
	 */
	public void setGLDBPASSWORD(String gldbpassword) {
		GLDBPASSWORD = gldbpassword;
	}
	/**
	 * @return Returns the gLDBPORT.
	 */
	public String getGLDBPORT() {
		return GLDBPORT;
	}
	/**
	 * @param gldbport The gLDBPORT to set.
	 */
	public void setGLDBPORT(String gldbport) {
		GLDBPORT = gldbport;
	}
	/**
	 * @return Returns the gLDBUSERNAME.
	 */
	public String getGLDBUSERNAME() {
		return GLDBUSERNAME;
	}
	/**
	 * @param gldbusername The gLDBUSERNAME to set.
	 */
	public void setGLDBUSERNAME(String gldbusername) {
		GLDBUSERNAME = gldbusername;
	}
	/**
	 * @return Returns the gLNAME.
	 */
	public String getGLNAME() {
		return GLNAME;
	}
	/**
	 * @param glname The gLNAME to set.
	 */
	public void setGLNAME(String glname) {
		GLNAME = glname;
	}
	/**
	 * @return Returns the gLPASSWORD.
	 */
	public String getGLPASSWORD() {
		return GLPASSWORD;
	}
	/**
	 * @param glpassword The gLPASSWORD to set.
	 */
	public void setGLPASSWORD(String glpassword) {
		GLPASSWORD = glpassword;
	}
	/**
	 * @return Returns the gLRESPONSEHTTP.
	 */
	public String getGLRESPONSEHTTP() {
		return GLRESPONSEHTTP;
	}
	/**
	 * @param glresponsehttp The gLRESPONSEHTTP to set.
	 */
	public void setGLRESPONSEHTTP(String glresponsehttp) {
		GLRESPONSEHTTP = glresponsehttp;
	}
	/**
	 * @return Returns the gLSENDER.
	 */
	public String getGLSENDER() {
		return GLSENDER;
	}
	/**
	 * @param glsender The gLSENDER to set.
	 */
	public void setGLSENDER(String glsender) {
		GLSENDER = glsender;
	}
	/**
	 * @return Returns the gLUSERNAME.
	 */
	public String getGLUSERNAME() {
		return GLUSERNAME;
	}
	/**
	 * @param glusername The gLUSERNAME to set.
	 */
	public void setGLUSERNAME(String glusername) {
		GLUSERNAME = glusername;
	}
	/**
	 * @return Returns the gLVOUCHERTYPE.
	 */
	public String getGLVOUCHERTYPE() {
		return GLVOUCHERTYPE;
	}
	/**
	 * @param glvouchertype The gLVOUCHERTYPE to set.
	 */
	public void setGLVOUCHERTYPE(String glvouchertype) {
		GLVOUCHERTYPE = glvouchertype;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		Id = id;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID() {
		return officeID;
	}
	/**
	 * @param officeID The officeID to set.
	 */
	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return statusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		this.statusID = statusID;
	}
}

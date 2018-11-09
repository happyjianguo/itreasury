package com.iss.itreasury.system.setting.dataentity ;

import java.io.Serializable;

import com.iss.itreasury.securities.util.SECBaseDataEntity;


public class  GlSettingInfo extends SECBaseDataEntity implements Serializable
{
	private long		Id						= -1 ;     
	private long		officeID				= -1 ;     //办事处
	private long 		currencyID			    = -1 ;     //币种
	
	private String		glDBIP		        = "" ;     //数据库IP
	
	private String		glDBUserName		        = "" ;     //用户
	private String		glDBPassWord		        = "" ;     //用户密码
	private String		glDBPort		        = "" ;     //库端口
	private String		glDBDatabaseName		        = "" ;     //库名称
	private String		glResponseHTTP		        = "" ;     //EAI地址
	
	private long		nStatusID			    = -1 ;     //删除否状态1:存在 0:删除
	
	private String		glName		        = "" ;     //总账类型
	private String		glSender		        = "" ;     //用友远程登入注册号
	private String		glVoucherType		        = "" ;     //用友记账类型
	private String		glUserName		        = "" ;     //总账用户
	private String		glPassWord		        = "" ;     //总账用户密码
	
	private long        nImportType			=-1;    //判断导出的是凭证是汇总的还是明细的
	
	private long 		glOperationType		=-1;	//总账操作方式（导入导出)
	
	private String     glZDRForGener ="";		//浪潮制单人
	
	private String     branChcode = "";       //账套编号
	
	private String		receiver= "" ;     //主体账簿
	
	private String		pk_corp = "" ;     //公司主键 （根据此SQL查询 select pk_corp ,unitcode from bd_corp;)

	private long		sendnum = -1 ;     //发送条数
	
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
		putUsedField("currencyID", currencyID);
	}
	/**
	 * @return Returns the databaseIP.
	 */
	public String getGlDBIP() {
		return glDBIP;
	}
	/**
	 * @param databaseIP The databaseIP to set.
	 */
	public void setGlDBIP(String glDBIP) {
		this.glDBIP = glDBIP;
		putUsedField("glDBIP", glDBIP);  //往HashTable填值
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
		putUsedField("id", id);
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
		putUsedField("officeID", officeID);
	}
	/**
	 * @return Returns the nStatusID.
	 */
	public long getNStatusID() {
		return nStatusID;
	}
	/**
	 * @param statusID The nStatusID to set.
	 */
	public void setNStatusID(long nStatusID) {
		this.nStatusID = nStatusID;
		putUsedField("nStatusID", nStatusID);
	}
	/**
	 * @return Returns the glDBUserName.
	 */
	public String getGlDBUserName() {
		return glDBUserName;
	}
	/**
	 * @param databaseIP The glDBUserName to set.
	 */
	public void setGlDBUserName(String glDBUserName) {
		this.glDBUserName = glDBUserName;
		putUsedField("glDBUserName", glDBUserName);
	}
	/**
	 * @return Returns the glDBPassWord.
	 */
	public String getGlDBPassWord() {
		return glDBPassWord;
	}
	/**
	 * @param databaseIP The glDBPassWord to set.
	 */
	public void setGlDBPassWord(String glDBPassWord) {
		this.glDBPassWord = glDBPassWord;
		putUsedField("glDBPassWord", glDBPassWord);
	}
	/**
	 * @return Returns the glDBPort.
	 */
	public String getGlDBPort() {
		return glDBPort;
	}
	/**
	 * @param databaseIP The glDBPort to set.
	 */
	public void setGlDBPort(String glDBPort) {
		this.glDBPort = glDBPort;
		putUsedField("glDBPort", glDBPort);
	}
	/**
	 * @return Returns the glDBDatabaseName.
	 */
	public String getGlDBDatabaseName() {
		return glDBDatabaseName;
	}
	/**
	 * @param databaseIP The glDBDatabaseName to set.
	 */
	public void setGlDBDatabaseName(String glDBDatabaseName) {
		this.glDBDatabaseName = glDBDatabaseName;
		putUsedField("glDBDatabaseName", glDBDatabaseName);
	}
	/**
	 * @return Returns the glResponseHTTP.
	 */
	public String getGlResponseHTTP() {
		return glResponseHTTP;
	}
	/**
	 * @param databaseIP The glResponseHTTP to set.
	 */
	public void setGlResponseHTTP(String glResponseHTTP) {
		this.glResponseHTTP = glResponseHTTP;
		putUsedField("glResponseHTTP", glResponseHTTP);
	}
	/**
	 * @return Returns the glName.
	 */
	public String getGlName() {
		return glName;
	}
	/**
	 * @param databaseIP The glName to set.
	 */
	public void setGlName(String glName) {
		this.glName = glName;
		putUsedField("glName", glName);
	}
	/**
	 * @return Returns the glSender.
	 */
	public String getGlSender() {
		return glSender;
	}
	/**
	 * @param databaseIP The glSender to set.
	 */
	public void setGlSender(String glSender) {
		this.glSender = glSender;
		putUsedField("glSender", glSender);
	}
	/**
	 * @return Returns the glVoucherType.
	 */
	public String getGlVoucherType() {
		return glVoucherType;
	}
	/**
	 * @param databaseIP The glVoucherType to set.
	 */
	public void setGlVoucherType(String glVoucherType) {
		this.glVoucherType = glVoucherType;
		putUsedField("glVoucherType", glVoucherType);
	}
	/**
	 * @return Returns the glUserName.
	 */
	public String getGlUserName() {
		return glUserName;
	}
	/**
	 * @param databaseIP The glUserName to set.
	 */
	public void setGlUserName(String glUserName) {
		this.glUserName = glUserName;
		putUsedField("glUserName", glUserName);
	}
	/**
	 * @return Returns the glPassWord.
	 */
	public String getGlPassWord() {
		return glPassWord;
	}
	/**
	 * @param databaseIP The glPassWord to set.
	 */
	public void setGlPassWord(String glPassWord) {
		this.glPassWord = glPassWord;
		putUsedField("glPassWord", glPassWord);
	}
	/**
	 * @return Returns the nImportType.
	 */
	public long getNImportType() {
		return nImportType;
	}
	
	
	/**
	 * @param statusID The nImportType to set.
	 */
	public void setNImportType(long nImportType) {
		this.nImportType = nImportType;
		putUsedField("nImportType", nImportType);
	}
	
	/**
	 * @return Returns the glOperationType.
	 */
	public long getGlOperationType() {
		return glOperationType;
	}
	/**
	 * @param currencyID The glOperationType to set.
	 */
	public void setGlOperationType(long glOperationType) {
		this.glOperationType = glOperationType;
		putUsedField("glOperationType", glOperationType);
	}
	
	/**
	 * @return Returns the glZDRForGener.
	 */
	public String getGLZDRForGener() {
		return glZDRForGener;
	}
	/**
	 * @param databaseIP The glZDRForGener to set.
	 */
	public void setGLZDRForGener(String glZDRForGener) {
		this.glZDRForGener = glZDRForGener;
		putUsedField("glZDRForGener", glZDRForGener);
	}
	
	public String getBranChcode() {
		return branChcode;
	}
	public void setBranChcode(String branChcode) {
		this.branChcode = branChcode;
		putUsedField("branChcode", branChcode);
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
		putUsedField("receiver", receiver);
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
		putUsedField("pk_corp", pk_corp);
	}
	public long getSendnum() {
		return sendnum;
	}
	public void setSendnum(long sendnum) {
		this.sendnum = sendnum;
		putUsedField("sendnum", sendnum);
	}
	
}
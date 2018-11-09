package com.iss.itreasury.clientmanage.client.dataentity;
import java.sql.Timestamp;
public class AnnexInfo {

	public AnnexInfo()
	{
		
	}
	private long id=-1;           //id
	private String code = "";  //客户编号
	private String name = ""; // 客户名称
	private long clientid=-1;  //客户id
	private String sclientfilename=null;//文件名称
	private long nfileid=-1;  //文件id
	Timestamp signstart=null; //印签生效日
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getClientid() {
		return clientid;
	}
	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	public String getSclientfilename() {
		return sclientfilename;
	}
	public void setSclientfilename(String sclientfilename) {
		this.sclientfilename = sclientfilename;
	}
	public long getNfileid() {
		return nfileid;
	}
	public void setNfileid(long nfileid) {
		this.nfileid = nfileid;
	}
	public Timestamp getSignstart() {
		return signstart;
	}
	public void setSignstart(Timestamp signstart) {
		this.signstart = signstart;
	}
}

/**
 * jlzhang
 * Oct 15, 2008
 */
package com.iss.itreasury.ebank.fundplan.dataentity;

/**
 * @author Colin
 *
 */
public class FundPlanInfo {

	private long id;
	private long modelid;
	private long currencyid;
	private long officeid;
	private String name;
	private String code;
	private long layerid;
	private long parentid;
	private long statusid;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getModelid() {
		return modelid;
	}
	public void setModelid(long modelid) {
		this.modelid = modelid;
	}
	public long getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(long currencyid) {
		this.currencyid = currencyid;
	}
	public long getOfficeid() {
		return officeid;
	}
	public void setOfficeid(long officeid) {
		this.officeid = officeid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getLayerid() {
		return layerid;
	}
	public void setLayerid(long layerid) {
		this.layerid = layerid;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public long getStatusid() {
		return statusid;
	}
	public void setStatusid(long statusid) {
		this.statusid = statusid;
	}
	
	
}

/*
 * Created on 2005-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.constantmanage.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConstantInfo extends ITreasuryBaseDataEntity{
	
	private long id = -1;
	private String name = "";
	private String moduleName = "";
	private long moduleID = -1;
	private String status = "";
	private long statusID = -1;//管理状态：0未设置/1管理/2不管理
	private String constantDesc = ""; 
	
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
		putUsedField("id", id);
	}
	
	/**
	 * @return Returns the moduleID.
	 */
	public long getModuleID() {
		return moduleID;
	}
	/**
	 * @param moduleID The moduleID to set.
	 */
	public void setModuleID(long moduleID) {
		this.moduleID = moduleID;
		putUsedField("moduleID", moduleID);
	}
	/**
	 * @return Returns the moduleName.
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * @param moduleName The moduleName to set.
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
		putUsedField("name", name);
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
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
		putUsedField("statusID", statusID);
	}
	
	/**
	 * @return Returns the constantDesc.
	 */
	public String getConstantDesc() {
		return constantDesc;
	}
	/**
	 * @param constantDesc The constantDesc to set.
	 */
	public void setConstantDesc(String constantDesc) {
		this.constantDesc = constantDesc;
		putUsedField("constantDesc", constantDesc);
	}
}

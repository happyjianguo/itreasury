package com.iss.itreasury.securities.setting.dataentity;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import java.io.Serializable;
/**
 * 
 * @author zpli
 *
 */
public class ActionSettingInfo extends SECBaseDataEntity implements Serializable {
	long 		id 				= -1;	
	long 		loantypeid		= -1;	
	String 		name 			= "";	
	long 		status 				= -1;	
	
	public long getId() {
		return this.id;
	}
	public void setId(long lID){
		this.id=lID;
	}
	/**
	 * @return Returns the loantypeid.
	 */
	public long getLoantypeId() {
		return loantypeid;
	}
	/**
	 * @param loantypeid The loantypeid to set.
	 */
	public void setLoantypeId(long loantypeid) {
		this.loantypeid = loantypeid;
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
	}
	/**
	 * @return Returns the status.
	 */
	public long getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(long status) {
		this.status = status;
	}
}

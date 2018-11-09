package com.iss.itreasury.fcinterface.bankportal.usermgt.dataentity;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;

public class UserPrivilegeInfo extends BaseDataEntity
{
	private long id = -1;
	private String code = "";
	private String name = "";
	private String privilegeUrl = "";
    private long systemModuleID = -1;   //系统模块ID，对应类SystemModuleType
	
	/**
	 * @return Returns the code.
	 */
	public String getCode()
	{
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code)
	{
		this.code = code;
		putUsedField("code", this.code);
	}
	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		this.id = id;
		putUsedField("id", this.id);
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
		putUsedField("name", this.name);
	}
	/**
	 * @return Returns the privilegeUrl.
	 */
	public String getPrivilegeUrl()
	{
		return privilegeUrl;
	}
	/**
	 * @param privilegeUrl The privilegeUrl to set.
	 */
	public void setPrivilegeUrl(String privilegeUrl)
	{
		this.privilegeUrl = privilegeUrl;
		putUsedField("privilegeUrl", this.privilegeUrl);
	}
    /**
     * @return Returns the systemModuleID.
     */
    public long getSystemModuleID() {
        return systemModuleID;
    }
    /**
     * @param systemModuleID The systemModuleID to set.
     */
    public void setSystemModuleID(long systemModuleID) {
        this.systemModuleID = systemModuleID;
        putUsedField("systemModuleID", systemModuleID);
    }
    
    
}

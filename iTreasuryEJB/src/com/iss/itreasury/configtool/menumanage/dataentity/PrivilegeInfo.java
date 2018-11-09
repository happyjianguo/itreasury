/*
 * Created on 2005-3-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.configtool.menumanage.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author hyzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrivilegeInfo extends ITreasuryBaseDataEntity{

    private long Id;
    private String name;
    private long officeID;
    private long moduleID;
    private long plevel;
    private String menuUrl;
    private long statusID;
    private String privilegeNo;

    public PrivilegeInfo()
    {
        Id = -1L;
        name = "";
        officeID = -1L;
        moduleID = -1L;
        plevel = -1L;
        menuUrl = "";
        statusID = -1L;
        privilegeNo = "";
    }

    public long getId()
    {
        return Id;
    }

    public void setId(long id)
    {
        Id = id;
        putUsedField("Id", id);
    }

    public long getPlevel()
    {
        return plevel;
    }

    public void setPlevel(long level)
    {
        plevel = level;
        putUsedField("plevel", level);
    }

    public String getMenuUrl()
    {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl)
    {
        this.menuUrl = menuUrl;
        putUsedField("menuUrl", menuUrl);
    }

    public long getModuleID()
    {
        return moduleID;
    }

    public void setModuleID(long moduleID)
    {
        this.moduleID = moduleID;
        putUsedField("moduleID", moduleID);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        putUsedField("name", name);
    }

    public long getOfficeID()
    {
        return officeID;
    }

    public void setOfficeID(long officeID)
    {
        this.officeID = officeID;
        putUsedField("officeID", officeID);
    }

    public long getStatusID()
    {
        return statusID;
    }

    public void setStatusID(long statusID)
    {
        this.statusID = statusID;
        putUsedField("statusID", statusID);
    }

    public String getPrivilegeNo()
    {
        return privilegeNo;
    }

    public void setPrivilegeNo(String privilegeNo)
    {
        this.privilegeNo = privilegeNo;
        putUsedField("privilegeNo", privilegeNo);
    }
}

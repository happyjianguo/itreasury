package com.iss.itreasury.custommenu.dataentity;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

public class CustomMenu_PrivilegeInfo extends ITreasuryBaseDataEntity
{

	    private long Id;
	    private String name;
	    private long officeID;
	    private long moduleID;
	    private long plevel;
	    private String menuUrl;
	    private long statusID;
	    private String privilegeNo;
	    private String byname;
	    private long uId;
	    public CustomMenu_PrivilegeInfo()
	    {
	        Id = -1L;
	        name = "";
	        officeID = -1L;
	        moduleID = -1L;
	        plevel = -1L;
	        menuUrl = "";
	        statusID = -1L;
	        privilegeNo = "";
	        byname="";
	        uId = -1L;
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

		public String getByname() {
			return byname;
		}

		public void setByname(String byname) {
			this.byname = byname;
			 putUsedField("byname", byname);
		}

		public long getUId() {
			return uId;
		}

		public void setUId(long id) {
			uId = id;
		}
}


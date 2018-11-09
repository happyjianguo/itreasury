package com.iss.itreasury.fcinterface.bankportal.usermgt.dataentity;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;

public class UserInfo extends BaseDataEntity
{
	private long id	             = -1;		//唯一标识	ID	     	>0		
	private String no	    	 = "";	//客户代码	No		Not ull
	private String name          = "";
	
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
	 * @return Returns the no.
	 */
	public String getNo()
	{
		return no;
	}
	/**
	 * @param no The no to set.
	 */
	public void setNo(String no)
	{
		this.no = no;
		putUsedField("no", this.no);
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
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(!(obj instanceof UserInfo))
		{
			return false;
		}
		UserInfo info = (UserInfo)obj;
		if(!(this.no.equals(info.getNo())))
		{
			return false;
		}
		if(!(this.name.equals(info.getName())))
		{
			return false;
		}
		return true;
	}
}

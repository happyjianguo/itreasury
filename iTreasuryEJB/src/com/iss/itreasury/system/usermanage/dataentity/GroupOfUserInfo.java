/*
 * Created on 2004-7-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.usermanage.dataentity;

import java.io.Serializable;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class GroupOfUserInfo extends ITreasuryBaseDataEntity implements Serializable
{
	private long Id = -1;	
	private long groupID = -1;
	private long userID = -1;
	/**
	 * @return Returns the id.
	 */
	public long getId ( )
	{
		return Id ;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId ( long id )
	{
		Id = id ;
		putUsedField ( "id" , id ) ;
	}
	/**
	 * @return Returns the groupID.
	 */
	public long getGroupID ( )
	{
		return groupID ;
	}
	/**
	 * @param groupID The groupID to set.
	 */
	public void setGroupID ( long groupID )
	{
		this.groupID = groupID ;
		putUsedField ( "groupID" , groupID ) ;
	}
	/**
	 * @return Returns the userID.
	 */
	public long getUserID ( )
	{
		return userID ;
	}
	/**
	 * @param userID The userID to set.
	 */
	public void setUserID ( long userID )
	{
		this.userID = userID ;
		putUsedField ( "userID" , userID ) ;
	}
}

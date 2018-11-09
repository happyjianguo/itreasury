/*
 * Created on 2003-10-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.clientmanage.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ClientSealPicInfo implements Serializable
{
	/**
	 * 
	 */
	public ClientSealPicInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long SealPicID = -1;
	private long ClientID  = -1; 
	private String Path = "";
	private String PicName = "";
	private Timestamp StartDate = null;
	private long StatusID = 0;
	private long ModifyUserID = -1;	
	/**
	 * @return
	 */
	public long getClientID()
	{
		return this.ClientID;
	}

	/**
	 * @param clientID
	 */
	public void setClientID(long clientID)
	{
		this.ClientID = clientID;
	}

	/**
	 * @return
	 */
	public Timestamp getStartDate()
	{
		return this.StartDate;
	}

	/**
	 * @param dtStart
	 */
	public void setStartDate(Timestamp dtStart)
	{
		this.StartDate = dtStart;
	}

	/**
	 * @return
	 */
	public String getPath()
	{
		return this.Path;
	}

	/**
	 * @param fullName
	 */
	public void setPath(String fullName)
	{
		this.Path = fullName;
	}

	/**
	 * @return
	 */
	public long getModifyUserID()
	{
		return this.ModifyUserID;
	}

	/**
	 * @param modifyUserID
	 */
	public void setModifyUserID(long modifyUserID)
	{
		this.ModifyUserID = modifyUserID;
	}

	/**
	 * @return
	 */
	public String getPicName()
	{
		return this.PicName;
	}

	/**
	 * @param picName
	 */
	public void setPicName(String picName)
	{
		this.PicName = picName;
	}

	/**
	 * @return
	 */
	public long getSealPicID()
	{
		return this.SealPicID;
	}

	/**
	 * @param sealPicID
	 */
	public void setSealPicID(long sealPicID)
	{
		this.SealPicID = sealPicID;
	}

	/**
	 * @return
	 */
	public long getStatusID()
	{
		return this.StatusID;
	}

	/**
	 * @param statusID
	 */
	public void setStatusID(long statusID)
	{
		this.StatusID = statusID;
	}

}

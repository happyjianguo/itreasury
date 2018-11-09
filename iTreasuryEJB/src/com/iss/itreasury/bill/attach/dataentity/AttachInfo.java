/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.bill.attach.dataentity;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachInfo implements java.io.Serializable
{
	private long lID; //标示
	private String sShowName; //在客户端的名称
	private String sRealName; //在服务器端的名称
	private String sServerPath; //文件的内部路径
	private String sContentType; //文档的ContentType类型
	private String sMime; // 文档的Mime类型
	private long lFileID = -1;      //文件ID       
	private long lType =-1;       //文件类型     
	private long lParentType = -1; //所属指令类型 
	private long lParentID = -1;    //所属指令ID   
	private long lStatus = -1;    //状态         

	

	/**
	 * @return
	 */
	public long getID()
	{
		return lID;
	}

	/**
	 * @return
	 */
	public String getContentType()
	{
		return sContentType;
	}


	/**
	 * @return
	 */
	public String getMime()
	{
		return sMime;
	}

	/**
	 * @return
	 */
	public String getRealName()
	{
		return sRealName;
	}

	/**
	 * @return
	 */
	public String getShowName()
	{
		return sShowName;
	}

	/**
	 * @param l
	 */
	public void setID(long l)
	{
		lID = l;
	}

	/**
	 * @param string
	 */
	public void setContentType(String string)
	{
		sContentType = string;
	}


	/**
	 * @param string
	 */
	public void setMime(String string)
	{
		sMime = string;
	}

	/**
	 * @param string
	 */
	public void setRealName(String string)
	{
		sRealName = string;
	}

	/**
	 * @param string
	 */
	public void setShowName(String string)
	{
		sShowName = string;
	}

	/**
	 * @return
	 */
	public String getServerPath()
	{
		return sServerPath;
	}

	/**
	 * @param string
	 */
	public void setServerPath(String string)
	{
		sServerPath = string;
	}

	/**
	 * @return
	 */
	public long getFileID()
	{
		return lFileID;
	}

	/**
	 * @return
	 */
	public long getParentID()
	{
		return lParentID;
	}

	/**
	 * @return
	 */
	public long getParentType()
	{
		return lParentType;
	}

	/**
	 * @return
	 */
	public long getStatus()
	{
		return lStatus;
	}

	/**
	 * @return
	 */
	public long getType()
	{
		return lType;
	}

	/**
	 * @param l
	 */
	public void setFileID(long l)
	{
		lFileID = l;
	}

	/**
	 * @param l
	 */
	public void setParentID(long l)
	{
		lParentID = l;
	}

	/**
	 * @param l
	 */
	public void setParentType(long l)
	{
		lParentType = l;
	}

	/**
	 * @param l
	 */
	public void setStatus(long l)
	{
		lStatus = l;
	}

	/**
	 * @param l
	 */
	public void setType(long l)
	{
		lType = l;
	}

}

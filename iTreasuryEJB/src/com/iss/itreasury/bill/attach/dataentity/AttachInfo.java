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
	private long lID; //��ʾ
	private String sShowName; //�ڿͻ��˵�����
	private String sRealName; //�ڷ������˵�����
	private String sServerPath; //�ļ����ڲ�·��
	private String sContentType; //�ĵ���ContentType����
	private String sMime; // �ĵ���Mime����
	private long lFileID = -1;      //�ļ�ID       
	private long lType =-1;       //�ļ�����     
	private long lParentType = -1; //����ָ������ 
	private long lParentID = -1;    //����ָ��ID   
	private long lStatus = -1;    //״̬         

	

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

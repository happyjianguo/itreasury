/*
 * Created on 2004-2-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLVoucherInfo implements Serializable
{
	private ArrayList list = new ArrayList();
	private long ModelID = -1;
	private long PostStatusID = -1;
	private String TransNo = "";
	private String VoucherNo = "";
	private Timestamp PostDate = null;
	private String Content = "";
	/**
	 * @return
	 */
	public ArrayList getList()
	{
		return list;
	}
	/**
	 * @param list
	 */
	public void setList(ArrayList list)
	{
		this.list = list;
	}
	/**
	 * @return
	 */
	public long getModelID()
	{
		return ModelID;
	}
	/**
	 * @param l
	 */
	public void setModelID(long l)
	{
		ModelID = l;
	}
	/**
		 * @return
		 */
	public GLEntryInfo getEntryInfo(long i)
	{
		if (this.list.size() > i && i >= 0)
		{
			return (GLEntryInfo) this.list.get((int) i);
		}
		else
		{
			return null;
		}
	}
	/**
	 * @param l
	 */
	public void addEntryInfo(GLEntryInfo entry)
	{
		this.list.add(entry);
	}
	/**
	 * @return
	 */
	public String getTransNo()
	{
		return TransNo;
	}

	/**
	 * @param string
	 */
	public void setTransNo(String string)
	{
		TransNo = string;
	}

	/**
	 * @return
	 */
	public Timestamp getPostDate()
	{
		return PostDate;
	}

	/**
	 * @param timestamp
	 */
	public void setPostDate(Timestamp timestamp)
	{
		PostDate = timestamp;
	}

	/**
	 * @return
	 */
	public long getPostStatusID()
	{
		return PostStatusID;
	}

	/**
	 * @param l
	 */
	public void setPostStatusID(long l)
	{
		PostStatusID = l;
	}

	/**
	 * @return
	 */
	public String getVoucherNo()
	{
		return VoucherNo;
	}

	/**
	 * @param string
	 */
	public void setVoucherNo(String string)
	{
		VoucherNo = string;
	}
	public String getContent()
	{
		return Content;
	}
	public void setContent(String content)
	{
		Content = content;
	}
	
	

}

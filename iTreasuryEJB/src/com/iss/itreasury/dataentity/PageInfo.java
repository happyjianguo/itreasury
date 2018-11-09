/*
 * Created on 2004-3-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.dataentity;

import com.iss.itreasury.util.Constant;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PageInfo implements java.io.Serializable
{
	private long lPageNo = 1; //Ò³Âë
	private long lPageSize = Constant.PageControl.CODE_PAGELINECOUNT; //Ò³Ãæ¼ÇÂ¼Êý
	private String sOrderBy = ""; //ÅÅÐò×Ö¶ÎÃû³Æ
	private long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;//ÉýÐò»ò½µÐò
	private boolean bChange = false;
	
	



	/**
	 * @return
	 */
	public boolean getChange()
	{
		return bChange;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return lPageNo;
	}

	/**
	 * @return
	 */
	public long getPageSize()
	{
		return lPageSize;
	}

	/**
	 * @return
	 */
	public String getOrderBy()
	{
		return sOrderBy;
	}

	/**
	 * @param b
	 */
	public void setChange(boolean b)
	{
		bChange = b;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		lPageNo = l;
	}

	/**
	 * @param l
	 */
	public void setPageSize(long l)
	{
		lPageSize = l;
	}

	/**
	 * @param string
	 */
	public void setOrderBy(String string)
	{
		sOrderBy = string;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return lDesc;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		lDesc = l;
	}

}

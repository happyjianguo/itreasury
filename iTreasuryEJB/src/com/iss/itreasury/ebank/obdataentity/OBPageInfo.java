/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obdataentity;

import com.iss.itreasury.util.Constant;
import java.io.Serializable;
/**
 * @author gdzhao
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBPageInfo implements Serializable
{
	private long pageLineCount=Constant.PageControl.CODE_PAGELINECOUNT;
	private long pageNo=1;
	private long orderParam=-1;
	private long desc=Constant.PageControl.CODE_ASCORDESC_ASC;
	
	
	/**
	 * @return
	 */
	public long getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public long getOrderParam()
	{
		return orderParam;
	}

	/**
	 * @return
	 */
	public long getPageLineCount()
	{
		return pageLineCount;
	}

	/**
	 * @return
	 */
	public long getPageNo()
	{
		return pageNo;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		desc = l;
	}

	/**
	 * @param l
	 */
	public void setOrderParam(long l)
	{
		orderParam = l;
	}

	/**
	 * @param l
	 */
	public void setPageLineCount(long l)
	{
		pageLineCount = l;
	}

	/**
	 * @param l
	 */
	public void setPageNo(long l)
	{
		pageNo = l;
	}

}

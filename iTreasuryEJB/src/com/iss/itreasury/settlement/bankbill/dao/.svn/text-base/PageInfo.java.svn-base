/*
 * Created on 2003-11-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.bankbill.dao;
import com.iss.itreasury.settlement.util.SETTConstant;
/**
 * @author lgwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
//inner class to memory and caculate the page info
public class PageInfo
{
	private long lPageCount = 1; //总页数
	private long lRowCount = 0; //总纪录数
	private long lPageLineCount = SETTConstant.PageControl.CODE_PAGELINECOUNT; //每页行数
	private long lRowNumStart = -1; //起始行号
	private long lRowNumEnd = -1; //结束行号
	private long lPageCurrent = 1; //当前页数

	/**			caculate all parameters
	* @param rowCount    the num of all rows
	* @param currentPage     current page number
	*/
	public void caculatePageInfo(long rowCount, long currentPage)
	{
		lRowCount = rowCount;
		//caculate the page number
		lPageCount = lRowCount / lPageLineCount;
		if (lRowCount % lPageLineCount != 0)
			lPageCount++;
		//if page num = 0,set it to 1
		if (lPageCount==0) lPageCount=1;
		//current page,if current page > page number,set current page = page number
		if (currentPage <= lPageCount)
		{
			lPageCurrent = currentPage;
		}
		else
		{
			lPageCurrent = lPageCount;
		}

		//caculate the start and end of the row number
		lRowNumStart = (lPageCurrent - 1) * lPageLineCount + 1;
		lRowNumEnd = lPageCurrent * lPageLineCount; //????
	}

	public long getLRowCount()
	{
		return lRowCount;
	}

	/**get the number of pages
	 * @return Returns the lPageCount.
	 */
	public long getLPageCount()
	{
		return lPageCount;
	}

	/**
	 * @return Returns the lRowNumEnd.
	 */
	public long getLRowNumEnd()
	{
		return lRowNumEnd;
	}

	/**
	 * @return Returns the lRowNumStart.
	 */
	public long getLRowNumStart()
	{
		return lRowNumStart;
	}

}
//Source file: D:\\project\\iAMPro\\src\\com\\iss\\iam\\common\\PageLoaderInfo.java
package com.iss.system.dao;
import java.io.Serializable;
import java.text.MessageFormat;

import com.iss.system.dataentity.BaseDataEntityBean;
/**
 * 记录分页信息，可以加入ActionForm作为其属性
 */
public class PageLoaderInfo extends BaseDataEntityBean implements Serializable
{
    /**
     * 查询结果总页数.
     * 第一次进入查询时这个值为-1,代表没有查询结果.
     * 进行翻页查询时参照这个值.
     * 
     * nPageCount=nRowCount)/nRowPerPage + 
     * (Math.Fraction(((float)nRowCount)/nRowPerPage)) > 0.0f ? 1:0)
     */
    private int m_nPageCount = 0;
    /**
     * 每页查询显示数据行数.
     * 这个值可能根据用户需要进行更改,但是不小于20.默认值是20.
     */
    private int m_nRowPerPage = 100;
    /**
     * 查询结果总行数.
     * 第一次进入查询时这个值为-1,代表没有查询结果.
     * 进行翻页查询时参照这个值.
     */
    private int m_nRowCount = 0;
    /**
     * 当前页号.
     */
    private int m_nPageNo = 0;
    /**
     * @roseuid 3EC9BA8F0365
     */
    public PageLoaderInfo()
    {
    }
    /**
     * Returns the pageCount.
     * @return int
     */
    public int getPageCount()
    {
        return m_nPageCount;
    }
    /**
     * Returns the pageNo.
     * @return int
     */
    public int getPageNo()
    {
        return m_nPageNo;
    }
    /**
     * Returns the rowCount.
     * @return int
     */
    public int getRowCount()
    {
        return m_nRowCount;
    }
    /**
     * Returns the rowPerPage.
     * @return int
     */
    public int getRowPerPage()
    {
        return m_nRowPerPage;
    }
    /**
     * 这个属性方法只是一个Stub,不可以修改pageCount属性值，只可以通过其它属性自动计算得到这个属性。
     * @param pageCount The pageCount to set
     */
    public void setPageCount(int pageCount)
    {
        //m_nPageCount = pageCount;
    }
    /**
     * Sets the pageNo.
     * @param pageNo The pageNo to set
     */
    public void setPageNo(int pageNo)
    {
        m_nPageNo = pageNo;
    }
    /**
     * Sets the rowCount.
     * @param rowCount The rowCount to set
     */
    public void setRowCount(int rowCount)
    {
        m_nRowCount = rowCount;
        this.m_nPageCount = m_nRowCount / this.m_nRowPerPage + ((m_nRowCount % m_nRowPerPage) > 0 ? 1 : 0);
        if (rowCount < 1)
        {
            this.m_nPageCount = 0;
            this.m_nPageNo = 0;
        }
        else 
        {
            if (this.m_nPageNo <1)
            {
                this.m_nPageNo = 1;
            }
            else if (this.m_nPageNo >this.m_nPageCount)
            {
                this.m_nPageNo = this.m_nPageCount;
            }
        }
    }
    /**
     * Sets the rowPerPage.
     * @param rowPerPage The rowPerPage to set
     */
    public void setRowPerPage(int rowPerPage)
    {
        m_nRowPerPage = rowPerPage;
        this.m_nPageNo = 1;
        this.m_nPageCount = m_nRowCount / this.m_nRowPerPage + ((m_nRowCount % m_nRowPerPage) > 0 ? 1 : 0);
    }
    /**
     * 页号向后加1。
     * @return boolean false 如果是最后一页。
     */
    public boolean incressPageNo()
    {
        if (this.m_nPageNo < this.m_nPageCount)
        {
            this.m_nPageNo++;
            return true;
        }
        return false;
    }
    /**
     * 页号向前减1。
     * @return boolean false 如果是第一页。
     * 
     * @return boolean
     */
    public boolean decreasePageNo()
    {
        if (this.m_nPageNo > 1)
        {
            this.m_nPageNo--;
            return true;
        }
        return false;
    }
    /**
     * 页号转为每一页。
     */
    public void firstPage()
    {
        this.m_nPageNo = 1;
    }
    /**
     * 页号转为最后一页。
     */
    public void lastPage()
    {
        this.m_nPageNo = this.m_nPageCount;
    }
    /**
     * 查询某一指定页的数据.
     * @param nPageNo 要转到的页号。
     * @return boolean false 如果页号超出范围。
     */
    public boolean gotoPage(int nPageNo)
    {
        if (nPageNo >= 1 && nPageNo <= this.m_nPageCount)
        {
            this.m_nPageNo = nPageNo;
            return true;
        }
        else if (nPageNo < 1)
        {
            this.m_nPageNo = 1;
        }
        else if (nPageNo > this.m_nPageCount)
        {
            this.m_nPageNo = this.m_nPageCount;
        }
        return true;
    }
    public String toString()
    {
        return MessageFormat.format(
            "RowCount={0}, RowPerPage={1}, PageCount={2}, PageNo={3}",
            new Object[] {
                new Integer(this.m_nRowCount),
                new Integer(this.m_nRowPerPage),
                new Integer(this.m_nPageCount),
                new Integer(this.m_nPageNo)});
    }
}

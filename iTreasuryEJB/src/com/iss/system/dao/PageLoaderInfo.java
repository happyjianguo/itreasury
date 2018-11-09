//Source file: D:\\project\\iAMPro\\src\\com\\iss\\iam\\common\\PageLoaderInfo.java
package com.iss.system.dao;
import java.io.Serializable;
import java.text.MessageFormat;

import com.iss.system.dataentity.BaseDataEntityBean;
/**
 * ��¼��ҳ��Ϣ�����Լ���ActionForm��Ϊ������
 */
public class PageLoaderInfo extends BaseDataEntityBean implements Serializable
{
    /**
     * ��ѯ�����ҳ��.
     * ��һ�ν����ѯʱ���ֵΪ-1,����û�в�ѯ���.
     * ���з�ҳ��ѯʱ�������ֵ.
     * 
     * nPageCount=nRowCount)/nRowPerPage + 
     * (Math.Fraction(((float)nRowCount)/nRowPerPage)) > 0.0f ? 1:0)
     */
    private int m_nPageCount = 0;
    /**
     * ÿҳ��ѯ��ʾ��������.
     * ���ֵ���ܸ����û���Ҫ���и���,���ǲ�С��20.Ĭ��ֵ��20.
     */
    private int m_nRowPerPage = 100;
    /**
     * ��ѯ���������.
     * ��һ�ν����ѯʱ���ֵΪ-1,����û�в�ѯ���.
     * ���з�ҳ��ѯʱ�������ֵ.
     */
    private int m_nRowCount = 0;
    /**
     * ��ǰҳ��.
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
     * ������Է���ֻ��һ��Stub,�������޸�pageCount����ֵ��ֻ����ͨ�����������Զ�����õ�������ԡ�
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
     * ҳ������1��
     * @return boolean false ��������һҳ��
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
     * ҳ����ǰ��1��
     * @return boolean false ����ǵ�һҳ��
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
     * ҳ��תΪÿһҳ��
     */
    public void firstPage()
    {
        this.m_nPageNo = 1;
    }
    /**
     * ҳ��תΪ���һҳ��
     */
    public void lastPage()
    {
        this.m_nPageNo = this.m_nPageCount;
    }
    /**
     * ��ѯĳһָ��ҳ������.
     * @param nPageNo Ҫת����ҳ�š�
     * @return boolean false ���ҳ�ų�����Χ��
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

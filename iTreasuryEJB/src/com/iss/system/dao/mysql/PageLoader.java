/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   PageLoader.java

package com.iss.system.dao.mysql;

import com.iss.system.IAppContext;
import com.iss.system.action.ActionException;
import com.iss.system.dao.PageLoaderInfo;
import com.iss.system.dao.SqlUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.StringTokenizer;

public class PageLoader
    implements com.iss.system.dao.PageLoader
{

    protected PageLoader()
    {
        m_appcontext = null;
        m_strCritia = null;
        m_strSelectionExpression = null;
        m_strTableExpression = null;
        m_strResultType = null;
        m_objUpperBounder = null;
        m_objLowerBounder = null;
        m_strPrefix = null;
        m_strOrderBy = null;
        m_strGroupBy = null;
        m_pageLoaderInfo = new PageLoaderInfo();
    }

    public void initPageLoader(IAppContext appContext, String strTableExpression, String strSelectionExpression, String strCritia, int nRowPerPage, String strResultType, String strPrefix)
    {
        m_appcontext = appContext;
        m_strTableExpression = strTableExpression;
        m_strSelectionExpression = strSelectionExpression;
        m_strCritia = strCritia;
        if(m_strCritia != null && m_strCritia.trim().equals(""))
            m_strCritia = null;
        m_pageLoaderInfo.setRowPerPage(nRowPerPage);
        m_strResultType = strResultType;
        m_strPrefix = strPrefix;
        if(m_strPrefix == null || m_strPrefix.trim().equals(""))
            m_strPrefix = "";
        try
        {
            m_pageLoaderInfo.setRowCount(getRowCount());
        }
        catch(ActionException e)
        {
            m_pageLoaderInfo.setRowCount(0);
        }
    }

    public Object[] nextPage()
        throws ActionException
    {
        if(m_pageLoaderInfo.incressPageNo())
            return fetchData();
        else
            return null;
    }

    public Object[] previousPage()
        throws ActionException
    {
        if(m_pageLoaderInfo.decreasePageNo())
            return fetchData();
        else
            return null;
    }

    public Object[] firstPage()
        throws ActionException
    {
        m_pageLoaderInfo.firstPage();
        return fetchData();
    }

    public Object[] lastPage()
        throws ActionException
    {
        m_pageLoaderInfo.lastPage();
        return fetchData();
    }

    public Object[] gotoPage(int nPageNo)
        throws ActionException
    {
        if(m_pageLoaderInfo.gotoPage(nPageNo))
            return fetchData();
        else
            return null;
    }

    public Object[] gotoPage()
        throws ActionException
    {
        return fetchData();
    }

    public Object[] listAll()
        throws ActionException
    {
        Object result[] = (Object[])null;
        if(m_pageLoaderInfo.getRowCount() > 0)
        {
            int nRowPerPage = m_pageLoaderInfo.getRowPerPage();
            m_pageLoaderInfo.setRowPerPage(m_pageLoaderInfo.getRowCount());
            result = fetchData();
            m_pageLoaderInfo.setRowPerPage(nRowPerPage);
        }
        return result;
    }

    private Object[] fetchData()
        throws ActionException
    {
        StringBuffer sbSQL = new StringBuffer(1024);
        sbSQL.append(MessageFormat.format("SELECT {0} FROM {1} ", new Object[] {
            m_strSelectionExpression, m_strTableExpression
        }));
        if(m_strCritia != null)
            sbSQL.append(MessageFormat.format(" WHERE {0} ", new Object[] {
                m_strCritia
            }));
        if(m_strGroupBy != null)
            sbSQL.append(m_strGroupBy + " ");
        if(m_strOrderBy != null)
            sbSQL.append(m_strOrderBy);
        if(m_pageLoaderInfo.getRowPerPage() > 0)
        {
            int nRowStart = (m_pageLoaderInfo.getPageNo() - 1) * m_pageLoaderInfo.getRowPerPage();
            sbSQL.append(MessageFormat.format(" LIMIT {0,number,#},{1,number,#}", new Object[] {
                new Integer(nRowStart), new Integer(m_pageLoaderInfo.getRowPerPage())
            }));
        }
        Connection conn = null;
        PreparedStatement psGetPage = null;
        ResultSet rsGetPage = null;
        try
        {
            conn = m_appcontext.getConnection();
            psGetPage = conn.prepareStatement(sbSQL.toString());
            rsGetPage = psGetPage.executeQuery();
            Object objResults[] = SqlUtil.parseDataEntityBeans(rsGetPage, m_strPrefix, m_strResultType);
            sbSQL.setLength(0);
            if(rsGetPage != null)
                rsGetPage.close();
            if(psGetPage != null)
                psGetPage.close();
            Object aobj[] = objResults;
            return aobj;
        }
        catch(Exception e)
        {
            throw new ActionException(null, "error.com.iss.iam.dao.pageloader.fetchdata.failed", e.getMessage());
        }
        finally
        {
            m_appcontext.releaseConnection(conn);
        }
    }

    public Object getLowerBounder()
    {
        return m_objLowerBounder;
    }

    public String getCritia()
    {
        return m_strCritia;
    }

    public String getPrefix()
    {
        return m_strPrefix;
    }

    public String getResultType()
    {
        return m_strResultType;
    }

    public String getSelectionExpression()
    {
        return m_strSelectionExpression;
    }

    public String getTableExpression()
    {
        return m_strTableExpression;
    }

    public void setResultType(String resultType)
    {
        m_strResultType = resultType;
    }

    public void setSelectionExpression(String selectionExpression)
    {
        m_strSelectionExpression = selectionExpression;
    }

    public void setTableExpression(String tableExpression)
    {
        m_strTableExpression = tableExpression;
    }

    public Object getUpperBounder()
    {
        return m_objUpperBounder;
    }

    public String getGroupBy()
    {
        return m_strGroupBy;
    }

    public String getOrderBy()
    {
        return m_strOrderBy;
    }

    public void setGroupBy(String groupBy)
    {
        m_strGroupBy = groupBy;
    }

    public void setOrderBy(String orderBy)
    {
        m_strOrderBy = orderBy;
    }

    public PageLoaderInfo getPageLoaderInfo()
    {
        return m_pageLoaderInfo;
    }

    public void setPageLoaderInfo(PageLoaderInfo pageLoaderInfo)
    {
        m_pageLoaderInfo = pageLoaderInfo;
    }

    private int getRowCount()
        throws ActionException
    {
        Connection conn = null;
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        int nRowCounts = 0;
        StringBuffer sbSelectionExpression = new StringBuffer(512);
        String strSQL = null;
        String strToken = null;
        String strTemp = null;
        boolean bFirst = true;
        try
        {
            conn = m_appcontext.getConnection();
            for(StringTokenizer stSelection = new StringTokenizer(m_strSelectionExpression, ","); stSelection.hasMoreTokens(); sbSelectionExpression.append("IFNULL(" + strTemp + ",0)"))
            {
                strToken = stSelection.nextToken().trim();
                int intIndex;
                int intStart;
                for(intStart = 0; (intIndex = strToken.indexOf(" ", intStart)) != -1; intStart = intIndex + 1)
                {
                    strTemp = strToken.substring(intStart, intIndex);
                    if(!strTemp.equalsIgnoreCase("DISTINCT") && !strTemp.equalsIgnoreCase("ALL") && !strTemp.equalsIgnoreCase("DISTINCTROW") && !strTemp.equalsIgnoreCase("STRAIGHT_JOIN") && !strTemp.equalsIgnoreCase("SQL_SMALL_RESULT") && !strTemp.equalsIgnoreCase("SQL_BIG_RESULT") && !strTemp.equalsIgnoreCase("HIGH_PRIORITY"))
                        break;
                }

                if(intIndex == -1)
                {
                    intIndex = strToken.length();
                    strTemp = strToken.substring(intStart, intIndex);
                }
                if(bFirst)
                    bFirst = false;
                else
                    sbSelectionExpression.append(",");
            }

            if(m_strCritia == null)
                strSQL = MessageFormat.format("SELECT COUNT( DISTINCT {0}) FROM {1}", new Object[] {
                    sbSelectionExpression.toString(), m_strTableExpression
                });
            else
                strSQL = MessageFormat.format("SELECT COUNT( DISTINCT {0}) FROM {1} WHERE {2}", new Object[] {
                    sbSelectionExpression.toString(), m_strTableExpression, m_strCritia
                });
            psCount = conn.prepareStatement(strSQL);
            rsCount = psCount.executeQuery();
            if(rsCount.next())
                nRowCounts = rsCount.getInt(1);
            if(rsCount != null)
                rsCount.close();
            if(psCount != null)
                psCount.close();
        }
        catch(Exception e)
        {
            throw new ActionException(null, "error.com.iss.iam.dao.pageloader.getrowcount.failed", e.getMessage());
        }
        finally
        {
            m_appcontext.releaseConnection(conn);
        }
        return nRowCounts;
    }

    private IAppContext m_appcontext;
    private String m_strCritia;
    private String m_strSelectionExpression;
    private String m_strTableExpression;
    private String m_strResultType;
    private Object m_objUpperBounder;
    private Object m_objLowerBounder;
    private String m_strPrefix;
    private String m_strOrderBy;
    private String m_strGroupBy;
    private PageLoaderInfo m_pageLoaderInfo;
    
	public void initPageLoaderCombine(IAppContext iappcontext, String s,
			String s1, String s2, int i, String s3, String s4, String s5,String s6,String s7) {
		// TODO Auto-generated method stub
		
	}

	public String getMainOrderBy() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMainOrderBy(String mainOrderBy) {
		// TODO Auto-generated method stub
		
	}
}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/
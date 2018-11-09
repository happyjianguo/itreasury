/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   PageLoader.java

package com.iss.system.dao.oracle;

import com.iss.system.BaseObject;
import com.iss.system.IAppContext;
import com.iss.system.action.ActionException;
import com.iss.system.dao.ConnectionFactory;
import com.iss.system.dao.PageLoaderInfo;
import com.iss.system.dao.SqlUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;


public class PageLoader extends BaseObject
	implements com.iss.system.dao.PageLoader, Serializable
{
    private IAppContext m_appcontext;
	/**
	 * 查询使用的SQL语句,只有条件语句部分,没有关键字"WHERE"
	 */
	private String m_strCritia = null;
	/**
	 * 查询语句中除去条件的部分,即关键字"WHERE"前面部分.
	 * 
	 * 如:
	 * 1.Select * From Product
	 * 2.Select A.* , B.* From Product A, ProductCatalog B
	 * 3.Select Product.* , ProductCatalog.* From Product INNER JION ProductCatalog ON 
	 * ProductCatalog.ProductSeqenceNo=Product.SequenceNo
	 */
	private String m_strSelectionExpression = null;
    private Object condition;
	private String m_strTableExpression = null;
	/**
	 * 查询结果对象类型,每次查询时返回这个类型的数据集或数组.
	 * 
	 * 查询过程中只对与字段名同名的类成员附值.类成员变量名称不包含数据库表字段前缀.
	 */
	private String m_strResultType = null;
	/**
	 * 查询中用于分页的关键列值,当前页所显示的最大关键值.
	 * 
	 * 对于iAM中大多数表,这个值是当前页中最大的SequenceNo,只有asset表比较特殊使用Serial
	 * No.
	 */
	private Object m_objUpperBounder = null;
	/**
	 * 查询中用于分页的关键列值,当前页所显示的最小关键值.
	 * 
	 * 对于iAM中大多数表,这个值是当前页中最小的SequenceNo,只有asset表比较特殊使用Serial
	 * No.
	 */
	private Object m_objLowerBounder = null;
	private String m_strPrefix = null;
	private String m_strOrderBy = null;
	private String m_strGroupBy = null;
	/**
	 * 分页信息。
	 */
	private PageLoaderInfo m_pageLoaderInfo = new PageLoaderInfo();
	/**
	 * 不要使用默认构造方法。
	 * @roseuid 3E69EB9B0296
	 */
    public PageLoader()
    {
        m_appcontext = null;
        m_strCritia = null;
        m_strSelectionExpression = null;
        m_strTableExpression = null;
        condition = null;
        m_strResultType = null;
        m_objUpperBounder = null;
        m_objLowerBounder = null;
        m_strPrefix = null;
        m_strOrderBy = null;
        m_strGroupBy = null;
        m_pageLoaderInfo = new PageLoaderInfo();
    }
	/**
	 * @param strTableExpression - 数据表表达式。
	 * 由于我们要访问的数据可能来自于一个数据库表或多个表的联合，所以要用表表达式。<br>
	 * 当使用一个表时，可能是直接写入表名，当是多个表是可是SQL 
	 * FROM语句后的表达式，例子见类说明。
	 * @param strSelectionExpression - 查询表达式。<br>
	 * 主要是查询语句中Select关键字后与FROM关键字前的部分.
	 * <br>
	 * 注意:如果是复合查询,要注意返回结果集列名前缀与strPrefix一致.
	 * @param strCritia - 查询条件.<br>
	 * 查询条件中不包含关键字Where.
	 * @param nRowPerPage - 每页显示数据的行数.
	 * @param strResultType - 
	 * 查询结果对象类型,每次查询时返回这个类型的数据集或数组.<br>
	 * 查询过程中只对与字段名同名的类成员附值.类成员变量名称不包含数据库表字段前缀.
	 * @param strPrefix - 数据库表字段前缀.<br>
	 * 可以为null,表示所有数据结果集中的列名与结果类型中的属性名一致，如果不一致的情况?
	 * ，要将结果集列名去掉这个前缀来做返回类型中属性名。
	 * @roseuid 3E6551330246
	 */
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
    }
	/**
	 * 用相同的查询条件,将当前nPageNo加1查询下一页数据.<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.如果当前页号(m_nPageNo)小于数页数m_nPageCount,则nPageNo加1.<br>
	 * 2.组织查询语句,查询下一页.
	 * <br> 3.根据m_strResultType构造结果的runtime class.<br>
	 * 4.循环结果集<br>
	 * nbsp;nbsp;nbsp;nbsp;复制列值到结 果类型的对应属性中.<br>
	 * 5.返回结果数组.
	 * </p>
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为长度为0的数组。
	 * @roseuid 3E65517E02A4
	 */
    public Object[] nextPage()
        throws ActionException
    {
        if(m_pageLoaderInfo.incressPageNo())
            return fetchData();
        else
            return null;
    }
	/**
	 * 用相同的查询条件,将当前m_nPageNo加1查询下一页数据.<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.如果当前页号(m_nPageNo)大于1,则m_nPageNo减1.<br>
	 * 2.组织查询语句,查询上一页.<br>
	 * 3.根据m_strResultType构造结果的runtime class.<br>
	 * 4.循环结果集<br>
	 *        复制列值到结果类型对应属性中.<br>
	 * 5.返回结果数组.</p>
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为长度为0的数组。
	 * @roseuid 3E65518E02B2
	 */
    public Object[] previousPage()
        throws ActionException
    {
        if(m_pageLoaderInfo.decreasePageNo())
            return fetchData();
        else
            return null;
    }
	/**
	 * 用相同的查询条件查询第一页数据.<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.如果当前页号(m_nPageNo)为1.<br>
	 * 2.组织查询语句,查询上一页.<br>
	 * 3.根据m_strResultType构造结果的runtime class.<br>
	 * 4.循环结果集<br>
	 *        复制列值到结果类型中对应属性中.<br>
	 * 5.返回结果数组.</p>
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为长度为0的数组。
	 * @roseuid 3E655194003B
	 */
    public Object[] firstPage()
        throws ActionException
    {
        try
        {
            m_pageLoaderInfo.setRowCount(getRowCount());
        }
        catch(ActionException e)
        {
            m_pageLoaderInfo.setRowCount(0);
        }
        m_pageLoaderInfo.firstPage();
        return fetchData();
    }
	/**
	 * 用相同的查询条件查询最后一页数据.<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.如果当前页号(pageNo)为pageCount-1.<br>
	 * 2.组织查询语句,查询上一页.<br>
	 * 3.根据m_strResultType构造结果的runtime class.<br>
	 * 4.循环结果集<br>
	 *        复制列值到结果类型对应属性中.<br>
	 * 5.返回结果数组.</p>
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为长度为0的数组。
	 * @roseuid 3E655198023F
	 */
    public Object[] lastPage()
        throws ActionException
    {
        m_pageLoaderInfo.lastPage();
        return fetchData();
    }
	/**
	 * 用相同的查询条件查询某一指定页的数据.<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.如果指定页号超出范围，则返回null。<br>
	 * 2.组织查询语句,查询一页数据.<br>
	 * 3.根据m_strResultType构造结果的runtime class.<br>
	 * 4.循环结果集<br>
	 *        复制列值到结果类型对应属性中.<br>
	 * 5.返回结果数组.</p>
	 * @param nPageNo
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为长度为0的数组。
	 * @roseuid 3E6C36E301C9
	 */
    public Object[] gotoPage(int nPageNo)
        throws ActionException
    {
        if(m_pageLoaderInfo.gotoPage(nPageNo))
            return fetchData();
        else
            return null;
    }
	/**
	 * 用相同的查询条件查询PageLoderInfo中指定页的当前页数据.<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.如果指定页号超出范围，则返回null。<br>
	 * 2.组织查询语句,查询一页数据.<br>
	 * 3.根据m_strResultType构造结果的runtime class.<br>
	 * 4.循环结果集<br>
	 *        复制列值到结果类型对应属性中.<br>
	 * 5.返回结果数组.</p>
	 * @param nPageNo
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为长度为0的数组。
	 * @roseuid 3E6C36E301C9
	 */
    public Object[] gotoPage()
        throws ActionException
    {
        return fetchData();
    }
	/**
	 * 用相同的查询条件查询所有数据.建议如果不知道数据量的情况下不要使用这个方法，因为可能因为
	 * 数据量太大而死机。<br>
	 * <p>
	 * 处理过程:<br>
	 * 1.组织查询语句,查询全部数据.<br>
	 * 2.根据m_strResultType构造结果的runtime class.<br>
	 * 3.循环结果集<br>
	 * 	复制列值到结果类型对应属性中.<br>
	 * 4.返回结果数组.</p>
	 * @param nPageNo
	 * @return 
	 * 以目标数据类型数组形式返回查询到的结果集。如果发生了错误，返回值为null,否为如果?
	 * 有结果并且没有错误发生，返回值为null。
	 * @roseuid 3E6C36E301C9
	 */
    public Object[] listAll()
        throws ActionException
    {
        Object result[] = (Object[])null;
        try
        {
            m_pageLoaderInfo.setRowCount(getRowCount());
        }
        catch(ActionException e)
        {
            m_pageLoaderInfo.setRowCount(0);
        }
        if(m_pageLoaderInfo.getRowCount() > 0)
        {
            int nRowPerPage = m_pageLoaderInfo.getRowPerPage();
            int nPageNo = m_pageLoaderInfo.getPageNo();
            m_pageLoaderInfo.setRowPerPage(m_pageLoaderInfo.getRowCount());
            result = fetchData();
            m_pageLoaderInfo.setRowPerPage(nRowPerPage);
            m_pageLoaderInfo.setPageNo(nPageNo);
        }
        return result;
    }
	/**
	 * 根据设置的信息读取数。
	 * @return Object[] 返回设置数据类型BEAN数组，可以直接CAST成指定类型使用。
	 * @throws ActionException
	 */
    private Object[] fetchData()
        throws ActionException
    {
        StringBuffer sbSQL = new StringBuffer(1024);
        sbSQL.append(MessageFormat.format(" SELECT * FROM (SELECT aa.*,ROWNUM r  FROM ( SELECT {0} FROM {1} ", new Object[] {
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
        int nRowStart = (m_pageLoaderInfo.getPageNo() - 1) * m_pageLoaderInfo.getRowPerPage();
        sbSQL.append(MessageFormat.format("  ) aa )  WHERE r  BETWEEN {0,number,#} AND {1,number,#}", new Object[] {
            new Integer(nRowStart + 1), new Integer(m_pageLoaderInfo.getRowPerPage() + nRowStart)
        }));
        Connection conn = null;
        PreparedStatement psGetPage = null;
        ResultSet rsGetPage = null;
        try
        {
            conn = m_appcontext.getConnection();
            System.out.println("pageLoaderSQL=" + sbSQL.toString());
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
            e.printStackTrace();
            throw new ActionException("errors.com.iss.iam.dao.pageloader.fetchdata.failed", e.getMessage());
        }
        finally
        {
            ConnectionFactory.releaseConnection(conn);
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

    public int getRowCount()
        throws ActionException
    {
        Connection conn = null;
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        int nRowCounts = 0;
        try
        {
            conn = m_appcontext.getConnection();
            StringBuffer sbSQL = new StringBuffer(1024);
            sbSQL.append(MessageFormat.format(" SELECT COUNT(*) FROM (SELECT {0} FROM {1} ", new Object[] {
                m_strSelectionExpression, m_strTableExpression
            }));
            if(m_strCritia != null)
                sbSQL.append(MessageFormat.format(" WHERE {0} ", new Object[] {
                    m_strCritia
                }));
            if(m_strGroupBy != null)
                sbSQL.append(m_strGroupBy + " ");
            sbSQL.append(")");
            System.out.println("pageLoaderSQL get Row Count =" + sbSQL.toString());
            Statement st = conn.createStatement();
            rsCount = st.executeQuery(sbSQL.toString());
            if(rsCount.next())
                nRowCounts = rsCount.getInt(1);
            if(rsCount != null)
                rsCount.close();
            if(psCount != null)
                psCount.close();
        }
        catch(Exception e)
        {
            throw new ActionException(null, "errors.com.iss.iam.ecatalog.pageloaderimpl.getrowcount.failed");
        }
        finally
        {
            ConnectionFactory.releaseConnection(conn);
        }
        return nRowCounts;
    }

    public Object getCondition()
    {
        return condition;
    }

    public void setCondition(Object condition)
    {
        this.condition = condition;
    }
    
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
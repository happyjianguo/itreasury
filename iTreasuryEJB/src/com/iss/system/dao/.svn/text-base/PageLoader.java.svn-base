//Source file: D:\\iAMPro-Product\\DevelopCode\\javasource\\com\\iss\\iam\\dao\\PageLoader.java
package com.iss.system.dao;

import com.iss.system.IAppContext;
import com.iss.system.action.ActionException;
/**
 * <p>用于查询时分页显示的工具类，当进行分页数据查询时，可能使用这个类进行代理查询?
 * </p>
 * <p>每次进行多页数据查询时,先要将查询结果的Schema、查询的数据源表或表的表达示（主
 * 要是指多表连接）及查询过滤条件作为参数传递给PageLoader，如：</p>
 * <pre>
 *     //在这个例子中假datasource是一个已经存在的javax.sql.DataSource.
 *     String strSelectExpression = "Select Staff.SequenceNo as SequenceNo, 
 * Staff.Name as StaffName, Company.Name CompanyName, Department.Name as 
 * DepartName";
 *     String strTableExpression = "Staff INNER JOIN Company ON 
 * Staff.CompanySequenceNo = Company.SequenceNo INNER JOIN Department on 
 * Staff.DepartmentSequenceNO = Department.SequenceNo";
 *     String strCritia = "(Staff.SequenceNo = '00000010')";
 *     PageLoader pl = com.iss.iam.common.PageLoaderFactory.newPageLoader(datasource, strTableExpression, 
 * strSelectExpression, strCritia, 20, "com.iss.iam.dataentity.Staff", null);
 * </pre>
 * <p>很多情况下，查询的结果可能有多页，这时可能将PageLoader对象放入Session缓存起来
 * 其对应的Session 
 * Key值是SessionConstants.PageLoader_KEY，下次查询时可以直接使用其操作方法。如：
 * <code>
 * //这个示例中假设session是已经存在的HTTPSession,　pl是一个已经初始化的PageLoader?
 * 象。
 * session.setAttribute(SessionConstants.PageLoader_KEY, pl);
 * </code></p>
 * <p>注意事项：<br>
 * 由在PageLoader放在Session中缓存起来后，可能会有两次不同的List操作混乱情况发生，?
 * 免方法是在每次进入一个List查询介面前要清除其中的PageLoader对象。<code>
 * ////这个示例中假设session是已经存在的HTTPSession。
 * session.setAttribute(SessionConstants.PageLoader_KEY, null);
 * </code></p>
 */
public interface PageLoader
{
	public abstract void initPageLoader(IAppContext iappcontext, String s, String s1, String s2, int i, String s3, String s4);
	
	public abstract void initPageLoaderCombine(IAppContext iappcontext, String s, String s1, String s2, int i, String s3, String s4,String s5,String s6,String s7);
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
    public Object[] nextPage() throws ActionException;
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
    public Object[] previousPage() throws ActionException;
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
    public Object[] firstPage() throws ActionException;
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
    public Object[] lastPage() throws ActionException;
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
    public Object[] gotoPage(int nPageNo) throws ActionException;
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
    public Object[] gotoPage() throws ActionException;
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
     * 有结果并且没有错误发生，返回值为长度为0的数组。
     * @roseuid 3E6C36E301C9
     */
    public Object[] listAll() throws ActionException;
    /**
     * Returns the lowerBounder.
     * @return Object
     */
    public Object getLowerBounder();
    /**
     * Returns the critia.
     * @return String
     */
    public String getCritia();
    /**
     * Returns the prefix.
     * @return String
     */
    public String getPrefix();
    /**
     * Returns the resultType.
     * @return String
     */
    public String getResultType();
    /**
     * Returns the selectionExpression.
     * @return String
     */
    public String getSelectionExpression();
    /**
     * Returns the tableExpression.
     * @return String
     */
    public String getTableExpression();
    /**
     * Sets the resultType.
     * @param resultType The resultType to set
     */
    public void setResultType(String resultType);
    /**
     * Sets the selectionExpression.
     * @param selectionExpression The selectionExpression to set
     */
    public void setSelectionExpression(String selectionExpression);
    /**
     * Sets the tableExpression.
     * @param tableExpression The tableExpression to set
     */
    public void setTableExpression(String tableExpression);
    /**
     * Returns the upperBounder.
     * @return Object
     */
    public Object getUpperBounder();
    /**
     * Returns the groupBy.
     * @return String
     */
    public String getGroupBy();
    /**
     * Returns the orderBy.
     * @return String
     */
    public String getOrderBy();
    
    public String getMainOrderBy();
    /**
     * Sets the groupBy.
     * @param groupBy The groupBy to set
     */
    public void setGroupBy(String groupBy);
    /**
     * Sets the orderBy.
     * @param orderBy The orderBy to set
     */
    public void setOrderBy(String orderBy);
    
    public void setMainOrderBy(String mainOrderBy);
    /**
     * Returns the pageLoaderInfo.
     * @return PageLoaderInfo
     */
    public PageLoaderInfo getPageLoaderInfo();
    /**
     * Sets the pageLoaderInfo.
     * @param pageLoaderInfo The pageLoaderInfo to set
     */
    public void setPageLoaderInfo(PageLoaderInfo pageLoaderInfo);
}

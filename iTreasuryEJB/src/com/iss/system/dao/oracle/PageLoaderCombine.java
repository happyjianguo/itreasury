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
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

public class PageLoaderCombine extends BaseObject
	implements com.iss.system.dao.PageLoader, Serializable
{
    private IAppContext m_appcontext;
	/**
	 * ��ѯʹ�õ�SQL���,ֻ��������䲿��,û�йؼ���"WHERE"
	 */
	private String m_strCritia = null;
	/**
	 * ��ѯ����г�ȥ�����Ĳ���,���ؼ���"WHERE"ǰ�沿��.
	 * 
	 * ��:
	 * 1.Select * From Product
	 * 2.Select A.* , B.* From Product A, ProductCatalog B
	 * 3.Select Product.* , ProductCatalog.* From Product INNER JION ProductCatalog ON 
	 * ProductCatalog.ProductSeqenceNo=Product.SequenceNo
	 */
	private String m_mainTable = null;
	private String m_mainCondition = null;
	private String m_MainOrderBy = null;
	private String m_strSelectionExpression = null;
    private Object condition;
	private String m_strTableExpression = null;
	/**
	 * ��ѯ�����������,ÿ�β�ѯʱ����������͵����ݼ�������.
	 * 
	 * ��ѯ������ֻ�����ֶ���ͬ�������Ա��ֵ.���Ա�������Ʋ��������ݿ���ֶ�ǰ׺.
	 */
	private String m_strResultType = null;
	/**
	 * ��ѯ�����ڷ�ҳ�Ĺؼ���ֵ,��ǰҳ����ʾ�����ؼ�ֵ.
	 * 
	 * ����iAM�д������,���ֵ�ǵ�ǰҳ������SequenceNo,ֻ��asset��Ƚ�����ʹ��Serial
	 * No.
	 */
	private Object m_objUpperBounder = null;
	/**
	 * ��ѯ�����ڷ�ҳ�Ĺؼ���ֵ,��ǰҳ����ʾ����С�ؼ�ֵ.
	 * 
	 * ����iAM�д������,���ֵ�ǵ�ǰҳ����С��SequenceNo,ֻ��asset��Ƚ�����ʹ��Serial
	 * No.
	 */
	private Object m_objLowerBounder = null;
	private String m_strPrefix = null;
	private String m_strOrderBy = null;
	private String m_strGroupBy = null;
	/**
	 * ��ҳ��Ϣ��
	 */
	private PageLoaderInfo m_pageLoaderInfo = new PageLoaderInfo();
	/**
	 * ��Ҫʹ��Ĭ�Ϲ��췽����
	 * @roseuid 3E69EB9B0296
	 */
    public PageLoaderCombine()
    {
        m_appcontext = null;
        m_strCritia = null;
        m_mainTable = null;
        m_mainCondition = null;
        m_MainOrderBy = null;
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
	 * @param strTableExpression - ���ݱ���ʽ��
	 * ��������Ҫ���ʵ����ݿ���������һ�����ݿ�����������ϣ�����Ҫ�ñ���ʽ��<br>
	 * ��ʹ��һ����ʱ��������ֱ��д����������Ƕ�����ǿ���SQL 
	 * FROM����ı��ʽ�����Ӽ���˵����
	 * @param strSelectionExpression - ��ѯ���ʽ��<br>
	 * ��Ҫ�ǲ�ѯ�����Select�ؼ��ֺ���FROM�ؼ���ǰ�Ĳ���.
	 * <br>
	 * ע��:����Ǹ��ϲ�ѯ,Ҫע�ⷵ�ؽ��������ǰ׺��strPrefixһ��.
	 * @param strCritia - ��ѯ����.<br>
	 * ��ѯ�����в������ؼ���Where.
	 * @param nRowPerPage - ÿҳ��ʾ���ݵ�����.
	 * @param strResultType - 
	 * ��ѯ�����������,ÿ�β�ѯʱ����������͵����ݼ�������.<br>
	 * ��ѯ������ֻ�����ֶ���ͬ�������Ա��ֵ.���Ա�������Ʋ��������ݿ���ֶ�ǰ׺.
	 * @param strPrefix - ���ݿ���ֶ�ǰ׺.<br>
	 * ����Ϊnull,��ʾ�������ݽ�����е��������������е�������һ�£������һ�µ����?
	 * ��Ҫ�����������ȥ�����ǰ׺����������������������
	 * @roseuid 3E6551330246
	 */
    
    public void initPageLoaderCombine(IAppContext appContext, String strTableExpression, String strSelectionExpression, String strCritia, int nRowPerPage, String strResultType, String strPrefix,String mainCondition,String mainTable,String mainOrderBy)
    {	
    	m_appcontext = appContext;
        m_strTableExpression = strTableExpression;
        m_strSelectionExpression = strSelectionExpression;
        m_mainTable = mainTable;
        m_mainCondition = mainCondition;
        if(m_mainCondition!=null && m_mainCondition.trim().equals(""))
        {
        	m_mainCondition = null;
        }
        m_MainOrderBy = mainOrderBy;
        if(m_MainOrderBy!=null && m_MainOrderBy.trim().equals(""))
        {
        	m_MainOrderBy = null;
        }
        m_strCritia = strCritia;
        if(m_strCritia != null && m_strCritia.trim().equals(""))
            m_strCritia = null;
        m_pageLoaderInfo.setRowPerPage(nRowPerPage);
        m_strResultType = strResultType;
        m_strPrefix = strPrefix;
        if(m_strPrefix == null || m_strPrefix.trim().equals(""))
            m_strPrefix = "";
        
	}
    
    public void initPageLoader(IAppContext appContext, String strTableExpression, String strSelectionExpression, String strCritia, int nRowPerPage, String strResultType, String strPrefix)
    {
       
    }
    
    
	/**
	 * ����ͬ�Ĳ�ѯ����,����ǰnPageNo��1��ѯ��һҳ����.<br>
	 * <p>
	 * �������:<br>
	 * 1.�����ǰҳ��(m_nPageNo)С����ҳ��m_nPageCount,��nPageNo��1.<br>
	 * 2.��֯��ѯ���,��ѯ��һҳ.
	 * <br> 3.����m_strResultType��������runtime class.<br>
	 * 4.ѭ�������<br>
	 * nbsp;nbsp;nbsp;nbsp;������ֵ���� �����͵Ķ�Ӧ������.<br>
	 * 5.���ؽ������.
	 * </p>
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪ����Ϊ0�����顣
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
	 * ����ͬ�Ĳ�ѯ����,����ǰm_nPageNo��1��ѯ��һҳ����.<br>
	 * <p>
	 * �������:<br>
	 * 1.�����ǰҳ��(m_nPageNo)����1,��m_nPageNo��1.<br>
	 * 2.��֯��ѯ���,��ѯ��һҳ.<br>
	 * 3.����m_strResultType��������runtime class.<br>
	 * 4.ѭ�������<br>
	 *        ������ֵ��������Ͷ�Ӧ������.<br>
	 * 5.���ؽ������.</p>
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪ����Ϊ0�����顣
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
	 * ����ͬ�Ĳ�ѯ������ѯ��һҳ����.<br>
	 * <p>
	 * �������:<br>
	 * 1.�����ǰҳ��(m_nPageNo)Ϊ1.<br>
	 * 2.��֯��ѯ���,��ѯ��һҳ.<br>
	 * 3.����m_strResultType��������runtime class.<br>
	 * 4.ѭ�������<br>
	 *        ������ֵ����������ж�Ӧ������.<br>
	 * 5.���ؽ������.</p>
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪ����Ϊ0�����顣
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
	 * ����ͬ�Ĳ�ѯ������ѯ���һҳ����.<br>
	 * <p>
	 * �������:<br>
	 * 1.�����ǰҳ��(pageNo)ΪpageCount-1.<br>
	 * 2.��֯��ѯ���,��ѯ��һҳ.<br>
	 * 3.����m_strResultType��������runtime class.<br>
	 * 4.ѭ�������<br>
	 *        ������ֵ��������Ͷ�Ӧ������.<br>
	 * 5.���ؽ������.</p>
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪ����Ϊ0�����顣
	 * @roseuid 3E655198023F
	 */
    public Object[] lastPage()
        throws ActionException
    {
        m_pageLoaderInfo.lastPage();
        return fetchData();
    }
	/**
	 * ����ͬ�Ĳ�ѯ������ѯĳһָ��ҳ������.<br>
	 * <p>
	 * �������:<br>
	 * 1.���ָ��ҳ�ų�����Χ���򷵻�null��<br>
	 * 2.��֯��ѯ���,��ѯһҳ����.<br>
	 * 3.����m_strResultType��������runtime class.<br>
	 * 4.ѭ�������<br>
	 *        ������ֵ��������Ͷ�Ӧ������.<br>
	 * 5.���ؽ������.</p>
	 * @param nPageNo
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪ����Ϊ0�����顣
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
	 * ����ͬ�Ĳ�ѯ������ѯPageLoderInfo��ָ��ҳ�ĵ�ǰҳ����.<br>
	 * <p>
	 * �������:<br>
	 * 1.���ָ��ҳ�ų�����Χ���򷵻�null��<br>
	 * 2.��֯��ѯ���,��ѯһҳ����.<br>
	 * 3.����m_strResultType��������runtime class.<br>
	 * 4.ѭ�������<br>
	 *        ������ֵ��������Ͷ�Ӧ������.<br>
	 * 5.���ؽ������.</p>
	 * @param nPageNo
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪ����Ϊ0�����顣
	 * @roseuid 3E6C36E301C9
	 */
    public Object[] gotoPage()
        throws ActionException
    {
        return fetchData();
    }
	/**
	 * ����ͬ�Ĳ�ѯ������ѯ��������.���������֪��������������²�Ҫʹ�������������Ϊ������Ϊ
	 * ������̫���������<br>
	 * <p>
	 * �������:<br>
	 * 1.��֯��ѯ���,��ѯȫ������.<br>
	 * 2.����m_strResultType��������runtime class.<br>
	 * 3.ѭ�������<br>
	 * 	������ֵ��������Ͷ�Ӧ������.<br>
	 * 4.���ؽ������.</p>
	 * @param nPageNo
	 * @return 
	 * ��Ŀ����������������ʽ���ز�ѯ���Ľ��������������˴��󣬷���ֵΪnull,��Ϊ���?
	 * �н������û�д�����������ֵΪnull��
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
	 * �������õ���Ϣ��ȡ����
	 * @return Object[] ����������������BEAN���飬����ֱ��CAST��ָ������ʹ�á�
	 * @throws ActionException
	 */
    private Object[] fetchData() throws ActionException
	{
		System.out.println("-------------------------�µ�ʵ����-----------------------------------");
	    String strTable = "";
	    try
	    {
	    	strTable = queryClient();
	    
	    	Object aobj[] = queryInformation(strTable);
	        return aobj;
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        throw new ActionException("errors.com.iss.iam.dao.pageloader.fetchdata.failed", e.getMessage());
	    }
	    
	}

	private String queryClient() throws Exception
	{
		
		String strTable = "";
		StringBuffer sbSQL = new StringBuffer();
		 try
	     {
			   
			   sbSQL.append(" SELECT * FROM  ");
			   sbSQL.append(" (SELECT aa.*, ROWNUM r FROM ");
			   sbSQL.append(" (select * from "+m_mainTable);
			   if(m_mainCondition!=null)
			   {
				   sbSQL.append(" "+m_mainCondition);
			   }
			   if(m_MainOrderBy!=null)
			   {
				   sbSQL.append(" "+m_MainOrderBy);
			   }

			   sbSQL.append(" ) aa)");
			   int nRowStart = (m_pageLoaderInfo.getPageNo() - 1) * m_pageLoaderInfo.getRowPerPage();
		        sbSQL.append(MessageFormat.format("  WHERE r  BETWEEN {0,number,#} AND {1,number,#}", new Object[] {
		            new Integer(nRowStart + 1), new Integer(m_pageLoaderInfo.getRowPerPage() + nRowStart)
		        }));
		        
		        strTable = "("+sbSQL.toString()+")";
		        System.out.println("mainTable="+sbSQL.toString());
		  
	        
	      }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        throw new ActionException("errors.com.iss.iam.dao.pageloader.fetchdata.failed", e.getMessage());
	    }
	    return strTable;
	}
	
	private Object[] queryInformation(String strTable) throws ActionException
	{
		System.out.println("-------------------------�µ�ʵ����-----------------------------------");
		
	    StringBuffer sbSQL = new StringBuffer(1024);
	    sbSQL.append(MessageFormat.format("select {0} from {1} ", new Object[] {
	        m_strSelectionExpression, strTable+m_strTableExpression}));

	    if(m_strCritia != null)
	        sbSQL.append(MessageFormat.format(" WHERE {0} ", new Object[] {
	            m_strCritia
	        }));
	    if(m_strGroupBy != null)
	        sbSQL.append(m_strGroupBy + " ");
	    if(m_strOrderBy != null)
	        sbSQL.append(m_strOrderBy);
	    
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

    public String getMainOrderBy()
    {
    	return m_MainOrderBy;
    }
    public void setGroupBy(String groupBy)
    {
        m_strGroupBy = groupBy;
    }
    
    public void setMainOrderBy(String mainOderBy)
    {
    	m_MainOrderBy = mainOderBy;
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

    public int getRowCount() throws ActionException
    {
    	
        Connection conn = null;
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        int nRowCounts = 0;
        try
        {
        	
            conn = m_appcontext.getConnection();
            StringBuffer sbSQL = new StringBuffer(1024);
            sbSQL.append(" select count(*) from (");
            sbSQL.append(" select * from "+m_mainTable);
            if(m_mainCondition!=null)
            {
            	sbSQL.append(" "+m_mainCondition);
            }
           
            sbSQL.append(" ) ");
   
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


}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/
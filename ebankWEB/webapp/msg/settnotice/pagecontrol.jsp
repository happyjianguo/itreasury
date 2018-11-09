<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.system.dao.PageLoaderInfo"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//ҳ����Ʊ���
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    try
	{
		//������
		//if(!SETTHTML.validateRequest(out, request,response)) return;
		
        //�������
		String key = null;
		PageLoader pageLoader = null; 
		PageLoaderInfo pageLoaderInfo = null;
		Object[] searchResults = null;

        //��ȡ����		
        strAction = (String)request.getParameter("strAction");
		strSuccessPageURL = (String)request.getParameter("strSuccessPageURL");
		strFailPageURL = (String)request.getParameter("strFailPageURL");
		key = (String)request.getAttribute("_pageLoaderKey");

		pageLoader = sessionMng.getPageLoader(key); 
		pageLoaderInfo = (pageLoader == null)?null:pageLoader.getPageLoaderInfo();
		
		Log.print(" PageControl - pageLoader : " + pageLoader);
		Log.print(" PageControl - strSuccessPageURL : " + strSuccessPageURL);
		Log.print(" PageControl - strFailPageURL : " + strFailPageURL);
		Log.print(" PageControl - strAction : " + strAction);
		Log.print(" PageControl - Key : " + key);

		if(pageLoaderInfo != null)
		{
			String strTemp = (String)request.getAttribute("pageLoaderInfo.rowPerPage");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setRowPerPage(Integer.valueOf(strTemp).intValue());
			}
			strTemp = (String)request.getAttribute("pageLoaderInfo.pageNo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				pageLoaderInfo.setPageNo(Integer.valueOf(strTemp).intValue());
			}
		}

        //����������������ҵ����ĵ���
        if(String.valueOf(Constant.PageControl.FIRSTPAGE).equals(strAction))
        {			
			if (pageLoader != null)
			{
				//��FORM�иı��ҳ����Ϣ���µ�PAGELOADER��
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//ȡ�ص�һҳ���
				searchResults = pageLoader.firstPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.LASTPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//��FORM�иı��ҳ����Ϣ���µ�PAGELOADER��
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//ȡ�����һҳ���
				searchResults = pageLoader.lastPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.NEXTPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//��FORM�иı��ҳ����Ϣ���µ�PAGELOADER��
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//ȡ����һҳ���
				searchResults = pageLoader.nextPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.PREVIOUSPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//��FORM�иı��ҳ����Ϣ���µ�PAGELOADER��
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//ȡ����һҳ���
				searchResults = pageLoader.previousPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.GOTOPAGE).equals(strAction))
        {
			if (pageLoader != null)
			{
				//��FORM�иı��ҳ����Ϣ���µ�PAGELOADER��
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//ȡ��ָ��ҳ���
				searchResults = pageLoader.gotoPage(pageLoaderInfo.getPageNo());

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
		else if(String.valueOf(Constant.PageControl.LISTALL).equals(strAction))
        {
			if (pageLoader != null)
			{
				//ȡ�����н��
				searchResults = pageLoader.listAll();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
   
		}
        else
        {
            //δָ����ҳ����ʱ��Ĭ��ִ�з�����ִ�в�ѯ����
			//ҵ�����߼�...
			//��ѯ����.
			//pageLoader = dao.search();

			//ȡ�ص�һҳ���,���ұ�����FORM��,Ŀ����ҳ������ʾʹ��.
			//this.setSearchResult(pageLoader.firstPage());


			//��pageLoader������SESSION��.
			//request.getSession().setAttribute(SessionConstants.PAGELOADER_KEY, pageLoader);

			//if (pageLoader.getPageLoaderInfo().getRowCount() <= 0)
			//{
			//	sessionMng.getActionMessages().addMessage("�޲�ѯ���");
			//}

			//strActionResult = Constant.ActionResult.SUCCESS;

			if (pageLoader != null)
			{
				//��FORM�иı��ҳ����Ϣ���µ�PAGELOADER��
				pageLoader.setPageLoaderInfo(pageLoaderInfo);

				//ȡ�ص�һҳ���
				searchResults = pageLoader.firstPage();

				strActionResult = Constant.ActionResult.SUCCESS;
			}
        }

		request.setAttribute(Constant.PageControl.SearchResults,searchResults);
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;	
	}
	
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;

	//ת����һҳ��
	Log.print("Next Page URL:"+strNextPageURL);	
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	if(rd != null)
	{
		rd.forward( request,response );
	}
	else
	{
		String strMessage = "\""+strNextPageURL+"\"���ܲ���ȷ���޷����ҳ����ת��\r\n��ע����תĿ��·�������Ǿ���·����";
		out.print(strMessage);
		System.out.println(strMessage);
	}
%>
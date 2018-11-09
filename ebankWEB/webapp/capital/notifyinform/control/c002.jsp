<%--
 ҳ������ ��c001.jsp
 ҳ�湦�� : ֪֧ͨȡָ�� �ύ����ҳ��
 ��    �� ��YuanHuang
 ��    �� ��2006-11-03
 ����˵�� ��
--%>
<%@ page contentType="text/html;charset=gbk"%>

<%@ page import="com.iss.itreasury.settlement.util.*,
					com.iss.itreasury.util.*,
					com.iss.itreasury.util.DataFormat,
					com.iss.itreasury.ebank.util.OBHtml,
					com.iss.system.dao.PageLoader,
					com.iss.itreasury.settlement.transfixeddeposit.dataentity.*,
					com.iss.itreasury.ebank.obnotifydepositinform.bizlogic.*,
					com.iss.itreasury.ebank.util.OBConstant,
					com.iss.itreasury.settlement.util.SETTConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
 <%@ include file="/common/common.jsp" %>


<%
String strNextPageURL = "";

long lShowMenu = OBConstant.ShowMenu.YES;
String strTitle = "֪ͨ���֧ȡָ���ύ";
  try 
	{
      //�û���¼��� 
      if (sessionMng.isLogin() == false)
      {
      	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
      	out.flush();
      	return;
      }

      // �ж��û��Ƿ���Ȩ�� 
      if (sessionMng.hasRight(request) == false)
      {
          out.println(sessionMng.hasRight(request));
      	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
      	out.flush();
      	return;
      }
	System.out.println("-------------------------c002.jsp---------------------------");
	
	String strSuccessPageURL ="/capital/notifyinform/view/v002.jsp";
	String strFailPageURL = "/capital/notifyinform/view/v002.jsp";
	String searchAction = "search";
	
	OBNotifyDepositInformBiz biz = new OBNotifyDepositInformBiz();
	NotifyDepositInformInfo info = new NotifyDepositInformInfo();
	
	PageLoader pageLoader = null;
	 	
	 String strPageLoaderKey = null;
	 if(request.getAttribute("_pageLoaderKey")!=null)
	 {
		 strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");		 
	 }
	 	 
	 if( searchAction.equals("search"))
	 { 
		 info.setModuleID(Constant.ModuleType.EBANK);
		 info.setUserID(sessionMng.m_lUserID);
		 pageLoader = biz.findWithPage(info);	
			 
		 if( strPageLoaderKey==null)
			 
		 {			
			 strPageLoaderKey = sessionMng.setPageLoader(pageLoader);	
		 }		
		 sessionMng.setQueryCondition(strPageLoaderKey,info);		
	 	}
		else
		{		
			pageLoader = sessionMng.getPageLoader(strPageLoaderKey);			
		}	
	 	request.setAttribute("_pageLoaderKey",strPageLoaderKey);
	 	
	 	strNextPageURL = "/pagecontrol.jsp?strSuccessPageURL="+strSuccessPageURL+"&strFailPageURL="+strFailPageURL;	
	     
		/* ��ȡ�����Ļ��� */
		//ServletContext sc = getServletContext();
	     System.out.println(strNextPageURL);
		/* ���÷��ص�ַ */
		
		
		
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		/* forward�����ҳ�� */
	    rd.forward(request, response);
	 

}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}

%>

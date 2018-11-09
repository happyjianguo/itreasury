<%--
/*
 * �������ƣ�c110.jsp
 * ����˵��������ָ����ϸ��Ϣ�쿴����ҳ��
 * �������ߣ�niweinan
 * ������ڣ�2010-10-21
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<%
    //�������
    String strTitle = "�۽��������ѯ��";
    //��ʱ��
    String sTemp = null;
    long lShowMenu = OBConstant.ShowMenu.YES;
    sTemp = (String)request.getParameter("menu");
    if ((sTemp != null) && (sTemp.equals("hidden"))) {
        lShowMenu = OBConstant.ShowMenu.NO;
    }
    request.setAttribute("menu", sTemp);
    Log.print("menu:"+lShowMenu);
    long iTransType = 0;     //��������
    sTemp = (String) request.getParameter("txtTransType");
    if (sTemp != null && sTemp.trim().length() > 0) {
        iTransType = Long.parseLong(sTemp);
    }
    Log.print("txtTransType:"+iTransType);
    long lID = 0;           //ָ�����
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }
   
    Log.print("lID:"+lID);
    String strReturn = "";           //��һҳ
    sTemp = (String) request.getParameter("strReturn");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strReturn = sTemp;
    }
    Log.print("strReturn:"+strReturn);
    request.setAttribute("strReturn", strReturn);
    String strSuccessPageURL = "";           //�ɹ�ҳ
    sTemp = (String) request.getParameter("strSuccessPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strSuccessPageURL = sTemp;
    }
    
    String strFailPageURL = "";           //ʧ��ҳ
    sTemp = (String) request.getParameter("strFailPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strFailPageURL = sTemp;
    }
    
    //��"�ҵĹ���"���ݵĿ��Ʊ���
	String strTempAction = "";
	if (request.getParameter("strTempAction") != null)
	{
		strTempAction = (String)request.getParameter("strTempAction");
		request.setAttribute("strTempAction", strTempAction);
	}
    
    request.setAttribute("search",request.getParameter("search"));
    System.out.println("search------------"+(String)request.getAttribute("search"));
    request.setAttribute("strAction", "query");
    String strFrom = null;  //forwardҳ��
    strFrom = "/bankpay/view/v004.jsp?src=1";
      
    
    
    
    //��������תҳ��
	    if(strSuccessPageURL != null && !"".equals(strSuccessPageURL))
	    {
	            strFrom = strSuccessPageURL;  
	    }

    try {
        /* ��ʼ����Ϣ��ѯ�� */
        OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
     //   FinanceInfo info = new FinanceInfo();
        OBBankPayInfo oBBankPayInfo = new OBBankPayInfo();
        /*�������з�����ѯ��Ϣ����*/
       oBBankPayInfo = biz.findEbankById(lID);
       
        	
        
        
        if (oBBankPayInfo == null || strFrom == null) {//Ϊ����ʾ����ҳ��
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
            return;
        }

        //�������б���������
        request.setAttribute("obbankpayinfo",oBBankPayInfo);
   //     request.setAttribute("financeInfo",info);
    //    request.setAttribute("resultInfo",info);
        //��ȡ�����Ļ���
        //ServletContext sc = getServletContext();
        //���÷��ص�ַ
        
       
       
       //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		System.out.println("############################^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+strContext + strFrom);
		pageControllerInfo.setUrl(strContext + strFrom);
		//�ַ�
		
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	       
	    //forward�����ҳ��
	    rd.forward(request, response);
    } catch(IException ie) {
        //����ҳ��
        OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>
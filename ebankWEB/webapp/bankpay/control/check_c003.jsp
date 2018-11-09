<%--
/*
 * �������ƣ�check_c006.jsp
 * ����˵��������ָ����ϸ��Ϣ�쿴����ҳ��
 * �������ߣ�
 * ������ڣ�2010-10-12
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>

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
    long lID = 0;           //ָ�����
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }
    Log.print("lID:"+lID);
    String strSuccessPageURL = "";           //�ɹ�ҳ
    sTemp = (String) request.getParameter("strSuccessPageURL");
    if (sTemp != null && sTemp.trim().length() > 0) {
        strSuccessPageURL = sTemp;
    }
    
    request.setAttribute("search",request.getParameter("search"));
    System.out.println("search------------"+(String)request.getAttribute("search"));
    request.setAttribute("strAction", "query");
    
    String strFrom = null;  //forwardҳ��    
    strFrom = "../view/check_v003.jsp?src=1";
    
    //��������תҳ��
    if(strSuccessPageURL != null && !"".equals(strSuccessPageURL))
    {
            strFrom = strSuccessPageURL;  
    }

    try {
       	//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
       	/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
       	/* ��ʼ����Ϣ��ѯ��*/
        //OBBankPayDao obBankPayDao = new  OBBankPayDao();
       	OBBankPayInfo info = new OBBankPayInfo();
       	/*�������з�����ѯ��Ϣ����*/
       	info=financeInstr.findByID(lID);
       	if (info == null || strFrom == null) {//Ϊ����ʾ����ҳ��
          	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
         	return;
     	}

        //�������б���������
       	request.setAttribute("info",info);
        //��ȡ�����Ļ���
        //ServletContext sc = getServletContext();
        //���÷��ص�ַ
        
       //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		System.out.println("############################^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + strFrom);
        pageControllerInfo.setUrl(strFrom);
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	       
	    //forward�����ҳ��
	    rd.forward(request, response);
    } catch(IException ie) {
        //����ҳ��
       OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "", 1);
    }
%>
<%--
/**
 * ҳ������ ��a1001-c.jsp
 * ҳ�湦�� : ��֤��������ҳ��
 * ��    �� ��kewen hu
 * ��    �� ��2006-05-07
 * �޸���ʷ ��
 */
--%>

<%@ page contentType = "text/html;charset=gbk"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.transmargindeposit.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransOpenMarginDepositDAO"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //ҳ����Ʊ���
    String strNextPageURL = "";
    String strSuccessPageURL = "";
    String strFailPageURL = "";
    String strAction = null;
    String strActionResult = Constant.ActionResult.FAIL;
    //�������
    String strTitle = "[�˻���ʷ��Ϣ]";
    try {
         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        //�������
        String strTemp = "";
        String strTransNo = ""; //���ױ��
        long lID = 0;           //

        //��ȡ����
        strAction = (String)request.getAttribute("strAction");
        strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
        strFailPageURL = (String)request.getAttribute("strFailPageURL");

        //ҵ������
        strTemp = (String)request.getAttribute("strTransNo");
        if (strTemp != null && strTemp.trim().length() > 0) {
            strTransNo = strTemp;
        }
        strTemp = (String)request.getAttribute("lID");
        if (strTemp != null && strTemp.trim().length() > 0) {
            lID = Long.parseLong(strTemp);
        }

        Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		if(strTransNo==null || strTransNo.equals(""))
		{
			if (lID > 0)
			{
				TransMarginOpenInfo openInfo = null;
				openInfo = dao.findByID(lID);	
				if(openInfo != null)
            	{
       	        	strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("strAction","FixedQuery");
					request.setAttribute("searchResults",openInfo);
					request.setAttribute("strTransNo",openInfo.getTransNo());
       		    }
			}
			else
			{
				sessionMng.getActionMessages().addMessage("��ƥ���¼");
				strActionResult = Constant.ActionResult.FAIL;
			}
		}
		else
		{
			TransMarginOpenInfo openInfo = null;
			openInfo = dao.findByTransNo(strTransNo);
			if(openInfo != null)
        	{
   	        	strActionResult = Constant.ActionResult.SUCCESS;
				request.setAttribute("strAction","FixedQuery");
				request.setAttribute("searchResults",openInfo);
				request.setAttribute("strTransNo",openInfo.getTransNo());
   		    }
		}
        
        request.setAttribute("strActionResult",strActionResult);
        //���ݴ�����������һ����ת��Ŀ��ҳ��
        strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
        //ת����һҳ��
        Log.print("Next Page URL:"+strNextPageURL);
        //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
        rd.forward(request, response);
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
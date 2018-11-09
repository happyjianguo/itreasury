<%--
 ҳ������ ��d020-c.jsp
 ҳ�湦�� : ������������-�ύ�������� ����ҳ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
				 com.iss.itreasury.ebank.obdiscountapply.dao.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[��������]";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/loan/discountapply/d020-c.jsp*******");
	
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	
		//�������
		String strTemp = "";
		long lID = -1;
		long lStatusID = -1;
		String strAction = (String)request.getAttribute("txtAction");
		
		String[] strDocumentTypes = null;
		String strDocumentType = "z";
		
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		
		strDocumentTypes = (String[])request.getParameterValues("DocumentType");
		if(strDocumentTypes != null && strDocumentTypes.length >0)
		{
	      	for(int i=0;i<strDocumentTypes.length;i++)
			{
			   strDocumentType +=  strDocumentTypes[i];
			}
		}
		
		OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		
		
		if(obDiscountApply.updateStatus(lID,lStatusID) > 0)
		{
		  //��������͵��������
		  Log.print("==========");
		  DiscountInfo discountInfo = new DiscountInfo();
		  discountInfo.setID(lID);
		  discountInfo.setCurrencyID(sessionMng.m_lCurrencyID);
		  discountInfo.setInputUserID(sessionMng.m_lUserID);
		  discountInfo.setDocumentType(strDocumentType);
		  OBDiscountApplyDao obDiscountApplyDao = new OBDiscountApplyDao();
		//  if (obDiscountApply.updateDiscount(discountInfo) > 0)
		  if (obDiscountApplyDao.updateDiscount(discountInfo) > 0)
		  {
		     Log.print("updateDiscount success!");
		  }
		    /* ��ȡ�����Ļ��� */
	       ServletContext sc = getServletContext();
	      
	     if(strAction != null && strAction.equals("modify"))
		 {
		    /* ���÷��ص�ַ */
		   RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/query/q003-c.jsp")));
		    /* forward�����ҳ�� */
	       rd.forward(request, response);
		 }
		 else
		 {
		    /* ���÷��ص�ַ */
		   RequestDispatcher rd1 = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d027-v.jsp")));
		    /* forward�����ҳ�� */
	       rd1.forward(request, response);
		 }
		  
		}
		else
		{
		   OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		   return;
		}

    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>



<%--
 ҳ������ ��d013-c.jsp
 ҳ�湦�� : ������������-��ѯһ������Ʊ����Ϣ ����ҳ��
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
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo,
				 java.util.Vector"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[��������]";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/loan/discountapply/d013-c.jsp*******");
	
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
		String strTmp ="";
		long lDiscountBillID = -1;
		String subtypeid="";
		
		 strTmp = (String)request.getAttribute("lDiscountBillID");
		 if( strTmp != null && strTmp.length() > 0 )
		 {
			 lDiscountBillID = Long.parseLong(strTmp.trim());
			 Log.print("\n����������Ʊ��id:"+lDiscountBillID);
		 }
		 strTmp = (String)request.getAttribute("subtypeid");
		 if( strTmp != null && strTmp.length() > 0 )
		 {
			 subtypeid = strTmp;
		 }
		OBDiscountApplyHome  obDiscountApplyHome = null;
        OBDiscountApply      obDiscountApply = null;
        obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
        obDiscountApply = obDiscountApplyHome.create();
		//modify by wjliu 2007/3/20 �޸���������-����ҳ���е�����кŵĽ�����ϸҳ��ķ���
		DiscountBillInfo discountBillInfo = null;
		//discountBillInfo = obDiscountApply.findDiscountBillByID(lDiscountBillID);
		//OBDiscountApplyDao obDiscountApplyDao = new OBDiscountApplyDao();//d
		//discountBillInfo = obDiscountApplyDao.findDiscountBillByID(lDiscountBillID);//d
		
		DiscountBillQueryInfo discountBillQueryInfo = new DiscountBillQueryInfo();
		discountBillQueryInfo.setDiscountID(lDiscountBillID);
		Vector v = null;
		v = obDiscountApply.findByID(discountBillQueryInfo);
		
		if(null != v && v.size() > 0)
		{
			discountBillInfo = (DiscountBillInfo)v.get(0);
		}
		
	
		if(discountBillInfo != null)
		{
		   Log.print("=====discountBillInfo:"+discountBillInfo.getID());
		   request.setAttribute("resultInfo",discountBillInfo);
		   request.setAttribute("subtypeid",subtypeid);
		 
		   /* ��ȡ�����Ļ��� */
	       ServletContext sc = getServletContext();
	       /* ���÷��ص�ַ */
	       RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d014-v.jsp")));
	       /* forward�����ҳ�� */
	       rd.forward(request, response);
		}
		else
		{
		   OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		   return;
		}

%>
<%	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


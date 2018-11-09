<%--
/*
 * �������ƣ�fi_c006.jsp
 * ����˵�����ʽ𻮲��޸Ŀ���ҳ��
 * �������ߣ�niweinan
 * ������ڣ�2011��03��23��
 */
--%>
 
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*
			     " 
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--Header start-->

<%!
	/* ����̶����� */
	String strTitle = "[�ʽ𻮲�]";
	String strTemp = "";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	


%>

<%  
	/* �û���¼�����Ȩ��У�� */
	try 
	{
	
        //�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	
	
%>

<!--Get Data start-->
<%
		/* ָ����� */
		long lInstructionID = -1;
		strTemp = request.getParameter("lInstructionID");
		if(strTemp != null && strTemp.length() > 0){
			lInstructionID = Long.parseLong(strTemp);
		}
		Log.print("------------------------------lInstructionID="+lInstructionID);
		
		//FinanceInfo tempFinanceInfo = null;
		//if(session.getAttribute("financeInfo") != null)
		//	tempFinanceInfo = (FinanceInfo)session.getAttribute("financeInfo");
		//session.setAttribute("financeInfo", null);
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* ʵ������Ϣ�� */
		FinanceInfo financeInfo = null;
		
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		/* ���÷�����ȡ��Ϣ���� */	
		financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		if(financeInfo.getSBatchNo()==null)
		{
			financeInfo.setSBatchNo("");
		}

		System.out.println("��ѯ����3"+financeInfo.getSBatchNo());

%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
		
	    /* ��ȡ�����Ļ���*/
	    //ServletContext sc = getServletContext();
		RequestDispatcher rd = null;
	    /* ���÷��ص�ַ */
		String 	strNextPageURL = "../view/fi_v001.jsp";
	
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strNextPageURL);
		//�ַ�
		rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));		
	    
		/* forward�����ҳ�� */
	    rd.forward(request, response);
	} 
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->


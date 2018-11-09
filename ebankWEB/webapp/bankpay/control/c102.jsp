<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->

<%!
	/* ����̶����� */
	String strTitle = "[ҵ�񸴺�]";
%>

<%

		double txtMinAmount = 0.00;   // ���׽��-��Сֵ
        double txtMaxAmount = 0.00;   // ���׽��-���ֵ
        String txtExecuteA = "";    // ִ������-��
        String txtExecuteB="";      //ִ�����ڵ�
	/* �û���¼�����Ȩ��У�� */
	try 
	{
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
        //�������Form��Ϣ�ı���
		//�����ʼ����ѯ��Ϣ����
    	QueryCapForm qcf=new QueryCapForm();
    	
	     String strTemp = "";
		strTemp = (String) request.getParameter("txtMinAmount");
		 if(strTemp != null && strTemp.trim().length() > 0) {
            txtMinAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // �����Сֵ
           
        }
        strTemp = (String) request.getParameter("txtMaxAmount");
		 if(strTemp != null && strTemp.trim().length() > 0) {
            txtMaxAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)); // �����Сֵ
           
        }
        
         strTemp = (String) request.getParameter("txtExecuteA");
        if(strTemp != null && strTemp.trim().length() > 0) {
            txtExecuteA = strTemp; // ִ������-��
        }
        strTemp = (String) request.getParameter("txtExecuteB");
        if(strTemp != null && strTemp.trim().length() > 0) {
            txtExecuteB = strTemp; // ִ������-��
           
        }
		
		if (request.getParameter("txtMinAmount") != null && (!request.getParameter("txtMinAmount").trim().equalsIgnoreCase("")))
		{
			
			qcf.setMinAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(GetParam(request,"txtMinAmount").trim())) );// ���׽��-��Сֵ
			Log.print("MinAmount:   "+qcf.getMinAmount());
		}	
		System.out.print("*************************begin1");
		if (request.getParameter("txtMaxAmount") != null && (!request.getParameter("txtMaxAmount").trim().equalsIgnoreCase("")))
		{
			qcf.setMaxAmount ( Double.parseDouble(DataFormat.reverseFormatAmount(GetParam(request,"txtMaxAmount").trim())) );// ���׽��-���ֵ
			//Log.print("MaxAmount:   "+qcf.getMaxAmount());
		}	
		String tmp=GetParam(request,"txtExecuteA");
		if(tmp!=null)
		{
			qcf.setStartExe (tmp); // ִ������-��
			//Log.print("StartExe :   "+qcf.getStartExe ());
		}
		tmp= GetParam(request,"txtExecuteB");
		if(tmp!=null)
		{
			qcf.setEndExe ( tmp); // ִ������-��
		//Log.print("EndExe :   "+qcf.getEndExe ());      
		}
		qcf.setClientID ( sessionMng.m_lClientID );
		qcf.setCurrencyID ( sessionMng.m_lCurrencyID );
		qcf.setStatus(OBConstant.SettInstrStatus.CHECK);
		qcf.setOperationTypeID ( OBConstant.QueryOperationType.CHECK );
		qcf.setUserID ( sessionMng.m_lUserID );
		OBFinanceInstrBiz obiz=new OBFinanceInstrBiz();
		Collection result=obiz.query_uncheck(qcf);
		System.out.println("*****************��ȡ��ѯ���������");
		request.setAttribute("rcoll",result);

	    if(result!=null)
		{
			System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssssssss");
		}
		
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl("../view/v104.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		 /* forward�����ҳ�� */
		rd.forward(request, response);	
		
%>

<%
}
catch(IException ie) 
	{
		
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
	catch(Exception e) 
	{
		Log.print("e:"+e.toString());
		return;
    }%>
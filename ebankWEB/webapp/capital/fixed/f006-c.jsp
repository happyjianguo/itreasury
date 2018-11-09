<!--
/*
 * �������ƣ�f006-c.jsp
 * ����˵�������������޸Ŀ���ҳ��
 * �������ߣ�����
 * ������ڣ�2007��04��13��
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.SETTConstant,
			     javax.servlet.*,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*" 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header start-->

<%!
	/* ����̶����� */
	String strTitle = "[��������]";
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

		String dPayerEndDate="";
		if(request.getAttribute("dPayerEndDate")!=null)
		{
			dPayerEndDate =(String) request.getAttribute("dPayerEndDate");   //���ڴ�������
		}
		
		String lEndDate="";
		if(request.getAttribute("endDate")!=null)
		{
			lEndDate =(String) request.getAttribute("endDate");   //���ڴ�������
		}
	
	
		/* ָ����� */
		long lInstructionID = -1;
		lInstructionID = GetNumParam(request,"lInstructionID");
		Log.print("------------------------------lInstructionID="+lInstructionID);
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* ʵ������Ϣ�� */
		FinanceInfo financeInfo = null;
		PayerOrPayeeInfo payeeInfo = null;
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}
		

		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
	
		/* ���÷�����ȡ��Ϣ���� */	
		financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);//������Ϣ
			if(financeInfo.getSubAccountID() > 0)
		{
		request.setAttribute("lSubAccount",String.valueOf(financeInfo.getSubAccountID()));
		}
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
		payeeInfo = biz.getLoanAccountSelectInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED,sessionMng.m_lUserID);//�տ��Ϣ
		if(payeeInfo == null)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "../../LoginMain.html",1, "OB_E002");
			return;
		}
	    
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
		request.setAttribute("payeeInfo", payeeInfo);
		
	    /* ��ȡ�����Ļ���*/
	    //ServletContext sc = getServletContext();
	    
	    
	    //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/fixed/f006-v.jsp");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    
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


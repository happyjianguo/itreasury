<!--
/*
 * �������ƣ�ck006-c.jsp
 * ����˵��������ƥ��ҵ��ѡ�����ҳ��
 * �������ߣ�����
 * ������ڣ�2004��02��10��
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.settlement.util.*,
				   com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				   com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				   javax.servlet.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[ҵ�񸴺�]";
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
	long lTransType = -1; //��������
	long lInstructionID = -1; //����ID
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
        System.out.println("-------------------------ck 006----------------------------------");
%>

<!--Access DB start-->
<%
		lInstructionID = GetNumParam(request,"lInstructionID");
		
		/* ʵ������Ϣ�� */
		PayerOrPayeeInfo payerOrPayeeInfo = null;
		FinanceInfo financeInfo = new FinanceInfo();
		
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();		
		if(lInstructionID>0)
		{
			financeInfo = financeInstr.findByID(lInstructionID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
			
		}
		
		if( (request.getParameter("SelectType") != null) && (request.getParameter("SelectType").length()>0) )
		{			
			lTransType = GetNumParam(request,"SelectType");
		}

		else if (lInstructionID>0)
		{
			if ( financeInfo.getTransType() == OBConstant.SettInstrType.CAPTRANSFER_OTHER )
			{
				lTransType = 1;
			}
			else
			lTransType = OBConstant.getQueryTypeByTransType( financeInfo.getTransType() );
		}
		
		if(lTransType == OBConstant.QueryInstrType.OPENFIXDEPOSIT || lTransType == OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
		if(lTransType == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT || lTransType == OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW)

		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY);
		}
		if(lTransType == OBConstant.QueryInstrType.TRUSTLOANRECEIVE || lTransType == OBConstant.QueryInstrType.YTLOANRECEIVE)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.TRUST);
		}
		if(lTransType == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.CONSIGN);
		}
		if(lTransType == OBConstant.QueryInstrType.DRIVEFIXDEPOSIT)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
		if(lTransType == OBConstant.QueryInstrType.CHANGEFIXDEPOSIT)
		{
			payerOrPayeeInfo = financeInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.FIXED);
		}
%>
<!--Access DB end-->

<!--Forward start-->
<%	
		/* �������б��������� */
	    request.setAttribute("lTransType", Long.toString(lTransType) );
		request.setAttribute("payerOrPayeeInfo", payerOrPayeeInfo );
		/*
		//Υ��¼��ƥ������
		if(lInstructionID>0)
		{
			request.setAttribute("financeInfo", financeInfo );	
		}
		*/
		
		/* ��ȡ�����Ļ���*/
	    //ServletContext sc = getServletContext();
		/* ���÷��ص�ַ */
	   	RequestDispatcher rd = null;
		Log.print("---*******lTransType="+lTransType);
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		
		switch((int)lTransType)
		{
			case (int)OBConstant.QueryInstrType.CAPTRANSFER:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck002-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.CHILDCAPTRANSFER:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck002-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				
				request.setAttribute("child", "1" );
				break;
			case (int)OBConstant.QueryInstrType.OPENFIXDEPOSIT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck003-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.FIXEDTOCURRENTTRANSFER:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck004-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.OPENNOTIFYACCOUNT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck003-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck004-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.TRUSTLOANRECEIVE:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.CONSIGNLOANRECEIVE:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.INTERESTFEEPAYMENT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.YTLOANRECEIVE:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck005-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT:
				pageControllerInfo.setUrl(strContext + "/capital/check/ck008-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.DRIVEFIXDEPOSIT://��������
				pageControllerInfo.setUrl(strContext + "/capital/check/ck010-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			case (int)OBConstant.QueryInstrType.CHANGEFIXDEPOSIT://����ת��
				pageControllerInfo.setUrl(strContext + "/capital/check/ck011-v.jsp");
				rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				break;
			default :
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
				return;		
		}
		
		Log.print("-----rd="+rd.toString());		
		/* forward�����ҳ�� */
	    rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("---------"+ie.toString());
		return;
	}	
	
%>


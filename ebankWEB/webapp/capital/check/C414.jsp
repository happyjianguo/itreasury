<%--
/*
 * �������ƣ�C414.jsp
 * ����˵��������ָ����ϸ��Ϣ�쿴����ҳ��
 * �������ߣ�����
 * ������ڣ�2003��09��25��
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%
	String strTitle = "[ҵ�񸴺�]";
	/* �û���¼�����Ȩ��У�� */
	try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
        	out.flush();
        	return;
        }

        //�ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
		}
	 } 
	 catch (Exception e) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>

<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = -1;
	String strMenu = (String)request.getParameter("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	


%>
<%
	String strIsCheck = "";
	int iTransType = 0;
	long lID = 0;
	
	strIsCheck = GetParam(request,"txtisCheck");
	iTransType = GetIntParam(request,"txtTransType");
	lID = GetNumParam(request,"txtID");
		
	Log.print("----------lID:   "+lID);
	Log.print("----------iTransType="+iTransType);
	String strFrom = null; //forwardҳ��
	
	switch (iTransType)
	{
		case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF:
		case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY:
		case (int)OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER:
		case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT://1;�ʽ𻮲� 
			strFrom = "/capital/captransfer/c004-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT:
			strFrom = "/capital/fixed/f004-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER:			
			strFrom = "/capital/fixed/f014-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT:
			strFrom = "/capital/notify/n004-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW:
			strFrom = "/capital/notify/n014-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE:
			strFrom = "/capital/loanrepayment/l006-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE:
			strFrom = "/capital/loanrepayment/l016-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT:
			strFrom = "/capital/loanrepayment/l026-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.YTLOANRECEIVE:
			strFrom = "/capital/loanrepayment/l006-v.jsp?src=1&isYT=1";
			break;
		case (int)OBConstant.SettInstrType.DRIVEFIXDEPOSIT:
			strFrom = "/capital/fixed/f008-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.CHANGEFIXDEPOSIT:
			strFrom = "/capital/fixed/f011-v.jsp?src=1";
			break;
		default :
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
			return;
	}

	try
	{
		/* ��ʼ����Ϣ��ѯ�� */
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		FinanceInfo info = new FinanceInfo();
           
		/* �����෽����ѯ */
		info = obFinanceInstrDao.findByID(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);//���û�����lID��ˮ����info ��null
		if (info == null || strFrom == null)//Ϊ����ʾ����ҳ��
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
			return;
		}
		
		//�������б���������
		request.setAttribute("lID",String.valueOf(lID));
		request.setAttribute("financeInfo",info);
		request.setAttribute("resultInfo",info);
		//��ȡ�����Ļ���
		//ServletContext sc = getServletContext();
		//���÷��ص�ַ
		
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + strFrom);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		//forward�����ҳ��
		rd.forward(request, response);
	}
	catch(IException ie)
	{
		//����ҳ��
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	}

%>


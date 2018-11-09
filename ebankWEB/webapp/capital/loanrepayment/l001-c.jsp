<%--
 ҳ������ ��l001-c.jsp
 ҳ�湦�� : [�����]--��Ӫ����
 ��    �� ��gqzhang
 ��    �� ��2004��2��
 ����˵�� ��ʵ�ֲ���˵����
            ���ݲ�����ָ��ID����ѯָ����Ϣ
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* ����̶����� */
	String strTitle = "[�����]--��Ӫ����";
%>					
<%
  try
  {
	   Log.print("*******����ҳ��--ebank/capital/loanrepayment/l001-c.jsp*******");
	
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
		long lInstructionID = -1;//����ָ��id
		long lUserID = sessionMng.m_lUserID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		
		 strTemp = (String)request.getAttribute("lID");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			lInstructionID = Long.valueOf(strTemp).longValue();
		 }
		
		 OBFinanceInstrHome  obFinanceInstrHome = null;
         OBFinanceInstr      obFinanceInstr = null;
         obFinanceInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
         obFinanceInstr = obFinanceInstrHome.create();
		
		 FinanceInfo financeInfo = null;
		 financeInfo = obFinanceInstr.findByID(lInstructionID,lUserID,lCurrencyID);
		 
		 if(financeInfo != null)
		 {
		   request.setAttribute("resultInfo",financeInfo);
		   
		 }
		 
		 //��ȡ�����˺�
		PayerOrPayeeInfo payerOrPayeeInfo = obFinanceInstr.getLoanAccountInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.TRUST);
		if(payerOrPayeeInfo != null)
		{
		  request.setAttribute("payerOrPayeeInfo",payerOrPayeeInfo);
		}
           /* ��ȡ�����Ļ��� */
	       //ServletContext sc = getServletContext();
	       /* ���÷��ص�ַ */
		  
		   
		   
		   //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl("/capital/loanrepayment/l002-v.jsp");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		   /* forward�����ҳ�� */
		   rd.forward(request, response);
%>
<%	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>

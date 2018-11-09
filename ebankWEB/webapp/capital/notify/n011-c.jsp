<!--
/*
 * �������ƣ�n011-c.jsp
 * ����˵����֪֧ͨȡ�޸Ŀ���ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��13��
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.settlement.util.SETTConstant,
				 com.iss.itreasury.ebank.approval.dataentity.*,
			     javax.servlet.*,
			     com.iss.itreasury.safety.util.*"
%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.OBCommitTime"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header start-->
<% String strContext = request.getContextPath();%>
<%!
	/* ����̶����� */
	String strTitle = "[֪֧ͨȡ]";
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
	
	/* ��ʼ����Ϣ�� */
	String operate = "";
	FinanceInfo financeInfo = null;
	TransInfo transinfo = new TransInfo();
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
		long strAction=-1;
		
		lInstructionID = GetNumParam(request,"lInstructionID");
		strAction = GetNumParam(request,"strAction");
		Log.print("------------------------------lInstructionID="+lInstructionID);
		
		FinanceInfo tempFinanceInfo = null;
		if(session.getAttribute("financeInfo") != null)
			tempFinanceInfo = (FinanceInfo)session.getAttribute("financeInfo");
		session.setAttribute("financeInfo", null);
		
		String lsign = null;
		String sign = "";
		lsign = request.getParameter("sign");
		if(lsign!=null&&lsign.trim().length()>0)
		{
			sign = lsign;
		}

		
		
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* ʵ������Ϣ�� */
		PayerOrPayeeInfo payerInfo = null;

		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
	
		/* ���÷�����ȡ��Ϣ���� */	
		financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		if(financeInfo.getSubAccountID() > 0)
		{
		request.setAttribute("lSubAccount",String.valueOf(financeInfo.getSubAccountID()));
		}
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
		payerInfo = biz.getLoanAccountSelectInfo(sessionMng.m_lClientID,sessionMng.m_lCurrencyID,SETTConstant.AccountGroupType.NOTIFY,sessionMng.m_lUserID);
		if(payerInfo == null)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "../../LoginMain.html",1, "OB_E003");
			return;
		}		
		
		operate = (String) request.getParameter("operate");
		String strNextPage = "/capital/notify/n012-v.jsp";
		long iTransType = OBConstant.SettInstrType.NOTIFYDEPOSITDRAW;
		if(operate != null && operate.equals("doApproval"))
		{
		
			//Modify by leiyang date 2007/07/25
			Timestamp tsExecute = financeInfo.getExecuteDate();
			Timestamp timeNow = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			
			//��ִ���յ��ڿ�����
			if(tsExecute.compareTo(timeNow) == 0){
				String strCommitTime = "";
				long isControl = -1;
		
				OBCommitTime cTime = new OBCommitTime();
				cTime.setOfficeId(sessionMng.m_lOfficeID);
				cTime.setCurrencyId(sessionMng.m_lCurrencyID);
				OBCommitTime result = OBCommitTimeBiz.findOBCommitTime(cTime);
				
				if(result != null){
					strCommitTime = result.getCommitTime();
					isControl = result.getIsControl();
					
					timeNow = Env.getSystemDateTime();
					
					//��ǰСʱ�ͷ���
					int lTNHours =  timeNow.getHours();
					int lTNMinutes = timeNow.getMinutes();
					
					String commitTimes[] = strCommitTime.split(":");
					//ֹͣ���յ�Сʱ�ͷ���
					int lCTHours = Integer.parseInt(commitTimes[0]);
					int lCTMinutes = Integer.parseInt(commitTimes[1]);
					
					if(lCTHours < lTNHours){
						throw new IException("����ʱ���ѳ���������ٽ���ʱ��");
					}
					else if(lCTHours == lTNHours) {
						if(lCTMinutes < lTNMinutes){
							throw new IException("����ʱ���ѳ���������ٽ���ʱ��");
						}
					}
				}
			}
			
		    String strSuccessPageURL1="/capital/notify/vAppN011.jsp";
			String strFailPageURL1="/capital/notify/n013-c.jsp";
			
			
			

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
					//����������Ĵ������������޸�Ϊv033.jsp��ʹ�������ַ    mingfang
					strNextPage ="/approval/view/v033.jsp";
			
			
			
			//String strAction1 = "FixedQuery";
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=" + lInstructionID;
			//���������
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
			
			financeInfo.setInutParameterInfo(pInfo);

			
			//����
			//Boxu Add 2009��5��22�� ������־��¼
			try
			{
				financeInstr.doApproval(financeInfo);
				sessionMng.getActionMessages().addMessage("�����ɹ�");
				transinfo.setStatus(Constant.SUCCESSFUL);
			}
			catch(Exception ex)
			{
				transinfo.setStatus(Constant.FAIL);
				ex.printStackTrace();
				throw new IException(ex.getMessage());
			}
			//try
			//{
			//	financeInstr.doApproval(financeInfo);
			//	sessionMng.getActionMessages().addMessage("�����ɹ�");
			//}
			//catch(Exception e) 
			//{
			//	Log.print("EJB�쳣�׵�ǰ̨����");	
			//	e.printStackTrace();
			//}
		}
		//ȡ������
		if(operate != null && operate.equals("cancelApproval"))
		{
			
		    String strSuccessPageURL1="/capital/notify/vAppN011.jsp";
			String strFailPageURL1="/capital/notify/n013-c.jsp";
			
			
			

			//strNextPage ="/currentStep.do?operate=selectCurrentStepList";										
				
			strNextPage ="/approval/view/v036.jsp";
			
			
			
			//String strAction1 = "FixedQuery";
			String surl= strContext + "/capital/query/q003-c.jsp?strSuccessPageURL="+strSuccessPageURL1
						+"&&strFailPageURL="+strFailPageURL1+"&&txtTransType="+iTransType+"&&txtID=" + lInstructionID;
			//���������
			InutParameterInfo pInfo = new InutParameterInfo();
			pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
			pInfo.setRequest(request);
			pInfo.setSessionMng(sessionMng);
			pInfo.setUrl(surl);
			pInfo.setTransTypeID(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
			
			financeInfo.setInutParameterInfo(pInfo);

			//����
			
			String strTemp = (String)request.getAttribute("rsStatus");
			long statusid = -1;	 	
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    statusid = Long.valueOf(strTemp).longValue();
			    if(statusid>=0)
			    {
			    	financeInfo.setStatus(statusid);
			    }
			}
			
			//Boxu Add 2009��5��22�� ������־��¼
			try
			{
				financeInstr.cancelApproval(financeInfo);
				sessionMng.getActionMessages().addMessage("ȡ�������ɹ�");
				transinfo.setStatus(Constant.SUCCESSFUL);
			}
			catch(Exception ex)
			{
				transinfo.setStatus(Constant.FAIL);
				ex.printStackTrace();
				throw new IException(ex.getMessage());
			}
			//try
			//{
			//	financeInstr.cancelApproval(financeInfo);
			//	sessionMng.getActionMessages().addMessage("ȡ�������ɹ�");
			//}
			//catch(Exception e) 
			//{
			//	Log.print("EJB�쳣�׵�ǰ̨����");	
			//	e.printStackTrace();
			//}
		}
		/*�����׳��쳣��������������*/
		if(lInstructionID < 0 && tempFinanceInfo!=null && tempFinanceInfo.getTransType()==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW)
			financeInfo = tempFinanceInfo;
	
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
		request.setAttribute("payerInfo", payerInfo);
		
	    /* ��ȡ�����Ļ���*/
	    //ServletContext sc = getServletContext();
		
	    /* ���÷��ص�ַ */
	  
	   //����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPage);
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
	finally
	{
		//Boxu Add 2009��5��22�� ������־��¼
		if(operate != null && operate.equals("doApproval"))
		{
			transinfo.setActionType(Constant.TransLogActionType.approval);
		}
		else if(operate != null && operate.equals("cancelApproval"))
		{
			transinfo.setActionType(Constant.TransLogActionType.cancelApproval);
		}
		
		if(transinfo.getStatus() != -1)
		{
			TranslogBiz translofbiz= new TranslogBiz();
			transinfo.setHostip(request.getRemoteAddr());
			transinfo.setHostname(request.getRemoteHost());
			transinfo.setTransType(OBConstant.SettInstrType.NOTIFYDEPOSITDRAW);
			translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
		}
	}
%>
<!--Forward end-->
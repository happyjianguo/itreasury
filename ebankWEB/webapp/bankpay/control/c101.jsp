<%
/*
 ���� ƥ��
*/
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,				 
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
				 com.iss.itreasury.budget.util.*,
                 com.iss.itreasury.budget.executecontrol.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%!
	/* ����̶����� */
	String strTitle="[���л��]";
%>
<%
try{
	/* ʵ�ֲ˵����� */
	 OBHtml.validateRequest(out,request,response);
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

		OBBankPayInfo  info=new OBBankPayInfo();
		String strTemp="";
		String sPayeeAcctNoZoomCtrl="";
		String sPayerAccountNoZoomCtrl="";
		String npayeracctid="";
		String npayeeacctid="";	
		String sChineseAmount="";
		info.convertRequestToDataEntity(request);
		//״̬
		//info.setNstatus(1);
		//�ͻ�id
		info.setNclientid(sessionMng.m_lClientID);
		//����
		info.setNcurrencyid(sessionMng.m_lCurrencyID);
		//ȷ����(��ǰ�û�)
		//info.setNconfirmuserid(sessionMng.m_lUserID);
		//ȷ��ʱ�䣨ϵͳ��ǰʱ�䣩
		//info.setDtconfirm(DataFormat.getDateTime(DataFormat.getDateString().substring(0,10)));
		
		strTemp = (String)request.getParameter("sPayerAccountNoZoomCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)					 //����˺�
		{				
			sPayerAccountNoZoomCtrl = strTemp.trim();
		}
		strTemp = (String)request.getParameter("sPayeeAcctNoZoomCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)					 //�տ�˺�
		{				
			sPayeeAcctNoZoomCtrl = strTemp.trim();
		}
		strTemp = (String)request.getParameter("npayeracctid");
		if (strTemp != null && strTemp.trim().length() > 0)					 //���ID
		{				
			npayeracctid = strTemp.trim();
		}
		strTemp = (String)request.getParameter("npayeeacctid");
		if (strTemp != null && strTemp.trim().length() > 0)					 //���ID
		{				
			npayeeacctid = strTemp.trim();
		}
		strTemp = (String)request.getParameter("sChineseAmount");
		if (strTemp != null && strTemp.trim().length() > 0)					 // ��д���
		{				
			sChineseAmount = strTemp.trim();
		}
		
		
%>
<!--Get Data end-->

<!--Check start-->
<!--Check end-->


<!--Set FinanceInfo Attribute end-->

<!--Access DB start-->
<%
	/* �޸ķ��ؽ�� */
		long lUpdateResult = -1;
	
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		//Log.print("--------------------lInstructionID="+lInstructionID);
		//lInstructionID = financeInstr.addCapitalTrans(financeInfo);
		//out.print("<script>alert(\"�ύ�ɹ�\")</script>");	
		//OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		String act="";
		act=request.getParameter("act");
		long lInstructionID=-1;
		if(act!=null && act.equals("match"))
		{
			//match
			System.out.println("userID---------->" + sessionMng.m_lUserID);
			lInstructionID=financeInstr.matching(info,sessionMng.m_lUserID);
			if(lInstructionID==-1 ) 
			{   
				request.setAttribute("info",info);
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl("../view/v101.jsp?report=report");
				//�ַ�
				RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
			   /* forward�����ҳ�� */
				rd.forward(request, response);
			}
			else{
				info=financeInstr.findByID(lInstructionID);
				request.setAttribute("info",info);

				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl("../view/v102.jsp");
				//�ַ�
				RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));	
			    /* forward�����ҳ�� */
				rd.forward(request, response);	
			}
			
		}
		if(act!=null && act.equals("check"))
		{
			//check
			lInstructionID=financeInstr.checkBankPay(info.getId(),sessionMng.m_lUserID);
			if(lInstructionID!=-1)
			{
				info=financeInstr.findByID(lInstructionID);
				request.setAttribute("info",info);
				request.setAttribute("isCheck","1");
				
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
				PageControllerInfo pageControllerInfo = new PageControllerInfo();
				pageControllerInfo.setSessionMng(sessionMng);
				pageControllerInfo.setUrl("../view/v103.jsp");
				//�ַ�
				RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					
			   	/* forward�����ҳ�� */
				rd.forward(request, response);
			}
		}
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
    }

%>
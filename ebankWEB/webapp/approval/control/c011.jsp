<%--
 ҳ������ ��c011.jsp
 ҳ�湦�� : ���������ò�ѯ--����ҳ��
 ��    �� ��gyzhao
 ��    �� ��2005��05��09��
 ����˵�� ��ʵ�ֲ���˵����
				1������
				2������
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>	
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.Log" %>

<%@ page import="com.iss.itreasury.ebank.util.*" %>	
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.ApprovalSettingBiz" %>
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.*" %>	

<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<%	
	//ҳ�������
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	try
	{
		String strTitle = "����������";
         //�û���¼��� 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // �ж��û��Ƿ���Ȩ�� 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//��request�л��ҳ�������Ϣ
		pageInfo.convertRequestToDataEntity(request);
		
		ApprovalQueryInfo queryInfo = new ApprovalQueryInfo();
		queryInfo.convertRequestToDataEntity(request);
        
		ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
		Collection c = null;
		ApprovalSettingInfo relustInfo = null;
		
		long lResult = -1;
		long lApprovalID = -1;
		String strTmp = (String)request.getAttribute("lApprovalID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lApprovalID = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lApprovalID = -1;
			}
		}
		
		if(pageInfo.getStrAction().equalsIgnoreCase("toModify"))
		{	
			relustInfo = AppBiz.findApprovalSetting(lApprovalID);
			request.setAttribute("relustInfo",relustInfo);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toBack"))
		{
			queryInfo = (ApprovalQueryInfo)sessionMng.getQueryCondition("queryInfo");
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toDelete"))
		{
			AppBiz.deleteApprovalSetting(lApprovalID);
			queryInfo = (ApprovalQueryInfo)sessionMng.getQueryCondition("queryInfo");
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toValid"))
		{
			AppBiz.changeApprovalSetting(lApprovalID);
			queryInfo = (ApprovalQueryInfo)sessionMng.getQueryCondition("queryInfo");
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toUpdate"))
		{
			//long lApprovalID = -1;	//�������ñ�ʾ
    		long lTotalLevel = 0;	//�����ܼ���
			long lLowLevel = 0;		//��С��������
   			long lIsPass = 0;		//�Ƿ�����Խ��
			String strApproveName = "";	//����������
    		String strApproveDesc = "";	//����������
    	
			// ��ȡlApprovalID
			strTmp = (String)request.getAttribute("lApprovalID");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lApprovalID = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lApprovalID = -1;
				}
			}

			// ��ȡlTotalLevel
			strTmp = (String)request.getAttribute("lTotalLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lTotalLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lTotalLevel = -1;
				}
			}
		
			// ��ȡlLowLevel
			strTmp = (String)request.getAttribute("lLowLevel");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lLowLevel = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lLowLevel = -1;
				}
			}

			// ��ȡlIsPass
			strTmp = (String)request.getAttribute("lIsPass");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					lIsPass = Long.parseLong(strTmp);
				}
				catch(Exception e)
				{
					lIsPass = -1;
				}
			}
			// ��ȡapproveName
			strTmp = (String)request.getAttribute("strApproveName");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strApproveName = strTmp;
				}
				catch(Exception e)
				{
					strApproveName = "";
				}
			}

			// ��ȡapproveDesc
			strTmp = (String)request.getAttribute("strApproveDesc");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					strApproveDesc = strTmp;
				}
				catch(Exception e)
				{
					strApproveDesc = "";
				}
			}
			ApprovalSettingInfo info = new ApprovalSettingInfo();
			info.setID(lApprovalID);
			info.setName(strApproveName);
			info.setDesc(strApproveDesc);
			info.setTotalLevel(lTotalLevel);
			info.setLowLevel(lLowLevel);
			info.setIsPass(lIsPass);

		String strErrorMessage ="";
		lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lResult == 1)
		{
			System.out.println("======1=111===========ret="+lResult);
			//��ô�����Ϣ
			strErrorMessage = "����������δ���������ҵ�񣬲������棡";
			request.setAttribute("Error", strErrorMessage);
			System.out.println("=======1===========strErrorMessage="+strErrorMessage);
			relustInfo = AppBiz.findApprovalSetting(lApprovalID);
			request.setAttribute("relustInfo",relustInfo);
%>
					<script language="JavaScript">
						alert("<%=strErrorMessage%>");
						eval("history.back(-1)");
					</script>
<%
			pageInfo.fail();
			
		}
		else
		{
			System.out.println("======2============ret="+lResult);
			System.out.println("======2============strErrorMessage="+strErrorMessage);
			long lTemp = AppBiz.saveApprovalSetting(info);
			if(lTemp == -2)
				sessionMng.getActionMessages().addMessage("������ͬ���Ƶ�������,���ܱ���!");
			relustInfo = AppBiz.findApprovalSetting(lApprovalID);
			request.setAttribute("relustInfo",relustInfo);
			pageInfo.success();

		}
		}
		else if(pageInfo.getStrAction().equalsIgnoreCase("toDispatch"))
		{
			//��ѯ�Ƿ���δ������ɵ�ҵ��
			lResult = AppBiz.checkApprovalTracing(lApprovalID);		
			if(lResult==1)          //�����ת����һҳ��������δ�������ҵ���򴫱�ʶisOverΪ2
			{
				request.setAttribute("lApprovalID",Long.toString(lApprovalID));
				request.setAttribute("isOver",new String("2"));				
			}
			else
			{
				request.setAttribute("isOver",new String("1"));
			}
			request.setAttribute("fromPage","c011");
			pageInfo.setStrSuccessPageURL("../control/c002.jsp?control=view");
			pageInfo.success();
		}
		else
		{	
			sessionMng.setQueryCondition("queryInfo",queryInfo);
			c = AppBiz.findApprovalSetting(queryInfo);
			request.setAttribute("searchResults",c);
			pageInfo.success();
		}
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		//�����쳣,���������Ϊʧ��	
		pageInfo.fail();
	}	
	if(pageInfo.getStrActionResult().equals(Constant.ActionResult.SUCCESS))
	{
		request.setAttribute("strActionResult",pageInfo.getStrActionResult());	
		Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURLs(pageControllerInfo));
		rd.forward( request,response );
	}
	else
	{
%>
					<script language="JavaScript">
						alert("����������δ���������ҵ�񣬲������棡");
						eval("history.back(-1)");
					</script>
<%
	}
%>
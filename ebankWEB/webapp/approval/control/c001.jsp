<%--
/*
 * �������ƣ�c001.jsp
 * ����˵���������������ÿ���ҳ��
 */
--%>

<!--Header start-->
<%
/* ����ҳ�����ԡ�session������ĳ���� */
%>
<%@ page contentType="text/html;charset=gbk"%> 
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@page import="com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.approval.bizlogic.*,
				com.iss.itreasury.ebank.approval.dataentity.*,
                java.util.Collection,
				java.sql.Timestamp"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<%   
    response.setHeader("Cache-Control","no-stored");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<!--Header end-->

<%
	Log.print("---------���� c001.jsp �����������ÿ���ҳ��---------");
	
	
	/* ����̶����� */
	String strTitle = "��������"; 
%>

<%	
    //��Session��ȡ�ñ���Ĳ���
    long lCurrencyID = sessionMng.m_lCurrencyID;
    
	//���ò���
	ApprovalSettingBiz AppBiz = new ApprovalSettingBiz();
	String strTmp = "";
    
	long lApprovalID = -1;	//�������ñ�ʾ
    long lTotalLevel = 0;	//�����ܼ���
	long lLowLevel = 0;		//��С��������
    long lIsPass = 0;		//�Ƿ�����Խ��
	String strApproveName = "";	//����������
    String strApproveDesc = "";	//����������
    
	long lStatusID = 1;		//��ǰ����״̬��1�������У�2���������
	long lResult = -1;		//���÷������ؽ��
	long lType = 0;         //������ʶ
	String strErrorMessage = "";
	
	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try {
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
        
		//��ȡ�����ݣ�����currentTransInfo����
		Log.print("��ȡ��ѯ����");

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
		
		// ��ȡlStatusID
		strTmp = (String)request.getAttribute("lStatusID");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lStatusID = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lStatusID = -1;
			}
		}

		// ��ȡlType
		strTmp = (String)request.getAttribute("lType");
		if( strTmp != null && strTmp.length() > 0 )
		{
			try
			{
				lType = Long.parseLong(strTmp);
			}
			catch(Exception e)
			{
				lType = -1;
			}
		}
        
        //���ݻ�ȡ�Ĳ�������֯����
        String strURL = "";
		//��ѯ�Ƿ���δ������ɵ�ҵ��
		//lResult = AppBiz.checkApprovalTracing(lApprovalID);
		if (lType == 1)
		{
			if(lResult==1)          //�����ת����һҳ��������δ�������ҵ���򴫱�ʶisOverΪ2
			{
				request.setAttribute("lApprovalID",Long.toString(lApprovalID));
				request.setAttribute("isOver",new String("2"));
				strURL = "../control/c002.jsp?control=view";
			}
			else
			{
				request.setAttribute("isOver",new String("1"));
				strURL = "../control/c002.jsp?control=view";
			}
		}
		else
		{
			System.out.println("lApprovalID="+lApprovalID);
			//����ApprovalSettingInfo()
			ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
			ASInfo.setID(lApprovalID);
			ASInfo.setName(strApproveName);
			ASInfo.setDesc(strApproveDesc);
			ASInfo.setTotalLevel(lTotalLevel);
			ASInfo.setLowLevel(lLowLevel);
			ASInfo.setIsPass(lIsPass);
			//��װ̬��Ϊ������
			ASInfo.setStatusID(Constant.ApprovalStatus.SAVE);
			ASInfo.setOfficeID(sessionMng.m_lOfficeID);
			ASInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			ASInfo.setInputUserID(sessionMng.m_lUserID);
			ASInfo.setInputDate(Env.getSystemDate());
			//���������Ϣ
			lResult = AppBiz.saveApprovalSetting(ASInfo);
			Log.print("#################lResult = " + lResult);
			if(lResult == -2)
			{	
				sessionMng.getActionMessages().addMessage("������ͬ���Ƶ�������,���ܱ���!");
			}			
			else if (lResult == -1)
			{
				//��ô�����Ϣ
				sessionMng.getActionMessages().addMessage("���ñ��治�ɹ�!");
				//���ر�����ǰ��ֵ��ͨ��lApprovalID��ѯ����ֵ��
				//ApprovalSettingInfo ASInfo = new ApprovalSettingInfo();
				ASInfo = AppBiz.findApprovalSetting(lApprovalID);
				request.setAttribute("lApprovalID",Long.toString(lApprovalID));
				request.setAttribute("strApproveName",strApproveName);
				request.setAttribute("strApproveDesc",strApproveDesc);				
				request.setAttribute("lTotalLevel",Long.toString(lTotalLevel));
				request.setAttribute("lLowLevel",Long.toString(lLowLevel));
				request.setAttribute("lIsPass",Long.toString(ASInfo.getIsPass()));
				request.setAttribute("lStatusID",Long.toString(ASInfo.getStatusID()));
				
				request.setAttribute("strStatusName", Constant.ApprovalStatus.getName(ASInfo.getStatusID()));
			}
			else
			{
				sessionMng.getActionMessages().addMessage("����ɹ�!");
				//�������ڵ�ֵ��Ҳ����ҳ���ֵ��
				request.setAttribute("lApprovalID",Long.toString(lResult));
				request.setAttribute("strApproveName",strApproveName);
				request.setAttribute("strApproveDesc",strApproveDesc);		
				request.setAttribute("lTotalLevel",Long.toString(lTotalLevel));
				request.setAttribute("lLowLevel",Long.toString(lLowLevel));
				request.setAttribute("lIsPass",Long.toString(lIsPass));				
				request.setAttribute("lStatusID",Long.toString(lStatusID));				
				request.setAttribute("strStatusName",Constant.ApprovalStatus.getName(lStatusID));
			}
			strURL = "../view/v014.jsp?control=view";
		}
		
		//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	    rd.forward(request, response);
		return;
		
    } catch (Exception e) {
		Log.print(e.toString());
        //CPFHTML.dealException(out,request,response,e.getMessage(),sessionMng,strTitle,1,0);
		return;
    }
%>
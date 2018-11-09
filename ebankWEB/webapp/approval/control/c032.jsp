<%--
 ҳ������ ��c032.jsp
 ҳ�湦�� : �������������ñ��������ɾ����������
 ��    �� ��ypxu
 ��    �� ��2007-4-16
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>	
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo" %>
<%@ page import="com.iss.itreasury.util.Log" %>	
<%@ page import="com.iss.itreasury.util.Constant" %>		
<%@ page import="com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz" %>	
<%@ page import="com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo" %>
<%@ page import="com.iss.itreasury.system.util.SYSHTML" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>	
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	String strTitle = "�������������ã���ϸ";
	//ҳ�������
	PageCtrlInfo pageInfo = new PageCtrlInfo();	
	InutApprovalRelationInfo commonInfo = new InutApprovalRelationInfo();
	InutApprovalRelationBiz appRelationBiz = new InutApprovalRelationBiz();
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
		
		//��request�л��ҳ�������Ϣ
		pageInfo.convertRequestToDataEntity(request);
		
		//��ʼ����ѯ��\������\�����
		
		

		//��request�л�ò�ѯ������Ϣ
		commonInfo.convertRequestToDataEntity(request);	


System.out.println("---------------------------->" + request.getParameter("isLowerUnit"));
		long islowerunit = Long.parseLong(request.getParameter("isLowerUnit")) ;  

		
		commonInfo.setIslowerunit(islowerunit);




				
		//qInfo = (ApprovalRelationInfo)sessionMng.getQueryCondition("qInfo");
		Vector v_Infos = new Vector();
				

			//��ȡ���е�ҵ������
			String[] strTransTypes = request.getParameterValues("transTypeID");		
			//��ȡ���еĸ�ѡ���IDֵ
			String[] txtIDCheckbox = request.getParameterValues("txtIDCheckbox");			
			//��ȡID
			String[] strID = request.getParameterValues("id");
			//��ȡ�����Ĳ�������
			String operation = request.getParameter("operation");
			//��������
			long transType = -1;
			//������ID
			long approvalID = -1;
			//����ID
			long lID = -1;
			
			
			//�������
		if(operation.equals("save"))
		{
			for(int i = 0;i < strTransTypes.length; i++)
			{
				//��ȡ������ID
				String strApprovalID = request.getParameter("approvalID" + i);
				
				//����Ը�ҵ������������������
				if(strApprovalID != null && !"".equals(strApprovalID) && !"-1".equals(strApprovalID))
				{
					transType = Long.parseLong(strTransTypes[i]);				
					approvalID = Long.parseLong(strApprovalID);
					lID = Long.parseLong(strID[i]);
					InutApprovalRelationInfo info = new InutApprovalRelationInfo();
					info.setOfficeID(sessionMng.m_lOfficeID);
					info.setCurrencyID(sessionMng.m_lCurrencyID);
					info.setClientID(sessionMng.m_lClientID);
					info.setModuleID(commonInfo.getModuleID());
					info.setTransTypeID(transType);
					info.setApprovalID(approvalID);
					info.setId(lID);
					info.setIslowerunit(islowerunit);
					//�Ƿ��ύ�ϼ�����
					long lIsSendToUpClient = -1;
					String strIsSendToUpClient = request.getParameter("isSendToUpClientCheckbox" + i);
					if(islowerunit==OBConstant.IsLowerun.ISNO && strIsSendToUpClient!=null)
					{
						lIsSendToUpClient = Long.valueOf(strIsSendToUpClient).longValue();
					}
					//
					//�Ƿ��ύ�ϼ�����
					if(islowerunit==OBConstant.IsLowerun.ISNO)
					{
						info.setIssendtoupclient(lIsSendToUpClient);
					}
					//
					v_Infos.add(info);
				}	
			}
			
			appRelationBiz.batchSave(v_Infos);
			sessionMng.getActionMessages().addMessage("���ù����ɹ�");	
		}
		if(operation.equals("del"))
		{			
				if(txtIDCheckbox != null && txtIDCheckbox.length > 0)
				{
					for(int i = 0; i < txtIDCheckbox.length; i++)
					{
						int j = Integer.parseInt(txtIDCheckbox[i]);
						lID = Long.parseLong(strID[j]);
						appRelationBiz.delete(lID);					
					}
					sessionMng.getActionMessages().addMessage("ȡ�������ɹ�");	
				}
				else
				{
					sessionMng.getActionMessages().addMessage("��ѡ��Ҫȡ�������ļ�¼��");				
				}							
		}
		
    	pageInfo.success();
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);	
		//�����쳣,���������Ϊʧ��	
		pageInfo.fail();
	}
	finally
	{
		Collection c_Result = null;
		c_Result = appRelationBiz.queryByConditions(commonInfo);
		request.setAttribute("c_Result",c_Result);
		//
	}
	//�������������request�� 
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());

	//ת����һҳ�� 
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	rd.forward( request,response );
%>
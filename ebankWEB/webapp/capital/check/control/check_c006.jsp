<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page
	import="java.util.*"%>
	 <%@ page import="com.iss.itreasury.util.Env" %>
	

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 java.rmi.*,
                 java.sql.*,
                 com.iss.itreasury.settlement.util.NameRef,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
                 com.iss.itreasury.ebank.bizdelegation.*"
%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%String strContext = request.getContextPath();%>

<%!/* ����̶����� */
	String strTitle = "[��������]";%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String) request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden"))) {
		lShowMenu = OBConstant.ShowMenu.NO;
	}
%>
<%
	/* ʵ������Ϣ�� */
	//ʵ��
	FinanceInfo info = new FinanceInfo();
	OBFinanceInstrDao obstr = new OBFinanceInstrDao();//��ѯ����
	List infoList = null;
	long lID = -1;//ָ��id
	String[] lID1 = new String []{};//ָ��id����
	int i = -1;//�����±����
	long lRet = -1 ;//����check()�ķ���ֵ
	TransInfo transinfo = new TransInfo();
	String sign ="";
	//��ѯ��

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
	try {
		//�û���¼��� 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// �ж��û��Ƿ���Ȩ�� 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
		
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);	
%>
<%
		lID1 =  request.getParameterValues("ccheck");
		System.out.println("++++++++++++���˵�id++++++++"+lID1);
		i = lID1.length;//�ύ�ĸ���
		System.out.println("�ύָ��ĸ�����"+ i);
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		String strTemp = new String();//������Ϣ
		String strTemp1 = new String();//������Ϣ
		Vector vcResult = new Vector(1);//������Ϣ
		System.out.println("��ʼ��������");
		FinanceInfo financeinfo =null;
		Timestamp dtmodify = null;
		if(request.getParameter("sign")!= null&&request.getParameter("sign").trim().length()>0)
		{
		sign = request.getParameter("sign");
		}
		for(int j=0;j<i;j++)
		{
			System.out.println("��ʼ����");	
			financeinfo = new FinanceInfo();
			lID = Long.parseLong(lID1[j].split("####")[0]);
			if(request.getParameter("dtmodify")!=null){
		    	dtmodify =DataFormat.getDateTime(request.getParameter("dtmodify"));
		}
			if(lID1[j].split("####")[2]!=null){
				dtmodify = DataFormat.getDateTime(lID1[j].split("####")[2]);
			}
			financeinfo.setID(lID);
			financeinfo.setDtModify(dtmodify);	
			financeinfo.setCheckUserID(sessionMng.m_lUserID);
			financeinfo.setCurrencyID(sessionMng.m_lCurrencyID);
			System.out.println("===================���˵�id��"+lID);
			//��ʼ���ϣ�����ֵ����0Ϊ���ϳɹ���С����Ϊʧ��	
			try
			{						
				lRet = financeInstr.check(financeinfo);	
				transinfo.setStatus(Constant.SUCCESSFUL);
				transinfo.setActionType(Constant.TransLogActionType.check);	
			}catch(Exception ex)
			{
				transinfo.setStatus(Constant.FAIL);
				transinfo.setActionType(Constant.TransLogActionType.check);
				ex.printStackTrace();
				throw new IException(ex.getMessage());
			}
			finally
			{
				if(transinfo.getStatus()!=-1)
				{
					TranslogBiz translofbiz= new TranslogBiz();
					transinfo.setHostip(request.getRemoteAddr());
					transinfo.setHostname(request.getRemoteHost());
					FinanceInfo financeInfo = financeInstr.findByID(lID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
					transinfo.setTransType(financeInfo.getTransType());
					translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
				
				}
			}
			if(obstr.getSignUserID(lID)> 0 ){
					strTemp =" ���ҵ����Ϊ�Ѹ���״̬����Ҫǩ�Ϻ�����ύ��"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"��<br>";					
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ�� "+lID+" </b><br>";
					vcResult.add(strTemp);
				}
		   else{
						strTemp = " ���ҵ����Ϊ�Ѹ���״̬���Ѿ��ύ��"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"��<br>";
						vcResult.add(strTemp);		
						strTemp = "<b>ָ�����Ϊ�� "+lID+" </b><br>";
				
					vcResult.add(strTemp);		
					}
				
		}
	
		request.setAttribute("return",vcResult);
		//�����ת
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext +"/capital/check/view/check_v006.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}	
		
 %>

<%--
/*
 * �������ƣ�batsign003-c.jsp
 * ����˵��������ǩ�ϲ�ѯҳ��
 * �������ߣ�����ξ
 * ������ڣ�2008��03��31��
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page
	import="com.iss.itreasury.ebank.util.*,com.iss.itreasury.settlement.util.*,com.iss.itreasury.util.*"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo"%>
<%@ page
	import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page
	import="java.util.*"%>

<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 java.rmi.*,
                 java.lang.*,
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
	String strTitle = "[����ǩ��]";%>
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
	String tempArr[] = new String[]{};
	String[] strID = new String []{};//ָ��id����
	Timestamp dtModify[] = new Timestamp[]{};
	int i = -1;//�����±����
	long lRet = -1 ;//����check()�ķ���ֵ
	String sbatchno = "";
	String  straction=null;
	  TransInfo transinfo = new TransInfo();
	  FinanceInfo financeInfo = new FinanceInfo();
	//��ѯ��
    //��ʱ��
        String sTemp = null;
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
		if(request.getParameter("sbatchno")!=null)
		{
			sbatchno = DataFormat.formatString(request.getParameter("sbatchno"));
		}
		
		  sTemp = (String) request.getParameter("Straction");
	        if (sTemp != null && sTemp.trim().length() > 0) {
	        	straction = sTemp.trim();
	            System.out.println("Straction==============:"+straction);
	        }

%>
<%
		tempArr =  request.getParameterValues("strID");
		if(tempArr.length>0){
			strID = new String[tempArr.length];
			dtModify = new Timestamp[tempArr.length];
		}
		for(int k = 0 ; k<tempArr.length ; k++){
			String[] arr = tempArr[k].split("####");
			String id = arr[0];
			Timestamp date = DataFormat.getDateTime(arr[2]);
			strID[k] = id;
			dtModify[k] = date;
		}
		System.out.println("++++++++++++���˵�id++++++++��"+strID);
		i = strID.length;//�ύ�ĸ���
		System.out.println("�ύָ��ĸ�����"+ i);
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		String strTemp = new String();//������Ϣ
		String strTemp1 = new String();//������Ϣ
		Vector vcResult = new Vector(1);//������Ϣ	
		if(straction.equals("sign")){
		for(int j=0;j<i;j++)
		{
			System.out.println("��ʼǩ��");	
			lID = Long.parseLong(strID[j]);
			System.out.println("===================���˵�id��"+lID);
			//��ʼǩ�ϣ�����ֵ����0Ϊǩ�ϳɹ���С����Ϊʧ��							
			//lRet = financeInstr.check(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);	
			  try
              {
                 FinanceInfo financeInfo_tmp = new FinanceInfo();
                 Timestamp dtmodify=null;
                 financeInfo_tmp.setID(lID);
                 financeInfo_tmp.setSignUserID(sessionMng.m_lUserID);
                 if(request.getAttribute(lID+"_dtmodify")!=null){
                 	dtmodify = DataFormat.getDateTime((String) request.getParameter(lID+"_dtmodify"));
                 	System.out.println("#######################----"+lID+"----dtmodifydtmodifydtmodify==="+dtmodify);
                 }
                 if(dtmodify==null&&request.getParameter("dtmodify")!=null){
                 	dtmodify = DataFormat.getDateTime((String) request.getParameter("dtmodify"));
                 }
                 if(dtModify[j]!=null){
				     dtmodify = dtModify[j];
				  }
                 financeInfo_tmp.setDtModify(dtmodify);
				 lRet = financeInstr.sign(financeInfo_tmp);		
				 transinfo.setStatus(Constant.SUCCESSFUL);
			   }
		       catch(Exception ex)
			         {
			              transinfo.setStatus(Constant.FAIL);
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
				           financeInfo = financeInstr.findByID( lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
				          transinfo.setTransType(financeInfo.getTransType());
				          transinfo.setActionType(Constant.TransLogActionType.sign);				
				          translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			         	 }
		        	  }
			 if(lRet<0){
					strTemp = "����ǩ��ʧ�ܣ�<br>";
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
					vcResult.add(strTemp);
				}else{
					strTemp ="�������ύ��"+NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
					"��<br>";
					vcResult.add(strTemp);
					strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
					vcResult.add(strTemp);
					/*Ҷ����˵����Ҫע�͵�*/
					//strTemp = NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+
						//"�����촦����µ�ָ��!<br>";
					//vcResult.add(strTemp);
				}
				
		}
		}else if(straction.equals("cancelsign")){

			for(int j=0;j<i;j++)
			{
				System.out.println("��ʼȡ��ǩ��");	
				lID = Long.parseLong(strID[j]);
				System.out.println("===================ȡ��ǩ��id��"+lID);
				//��ʼǩ�ϣ�����ֵ����0Ϊǩ�ϳɹ���С����Ϊʧ��							
				//lRet = financeInstr.check(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);	
				try
              {	
				 lRet = financeInstr.cancelSign(lID, sessionMng.m_lUserID);
				  transinfo.setStatus(Constant.SUCCESSFUL);
			   }
		       catch(Exception ex)
			         {
			              transinfo.setStatus(Constant.FAIL);
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
				           financeInfo = financeInstr.findByID( lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
				          transinfo.setTransType(financeInfo.getTransType());
				          transinfo.setActionType(Constant.TransLogActionType.cancelSign);				
				          translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo);
			
			         	 }
		        	  }
				 if(lRet<0){
						strTemp = "����ȡ��ǩ��ʧ�ܣ�<br>";
						vcResult.add(strTemp);
						strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
						vcResult.add(strTemp);
					}else{
						 //������Ϣ����
						strTemp = "���ҵ����Ϊ�Ѹ���״̬����Ҫǩ�Ϻ�����ύ��"+
						NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)+"��<br>";
						vcResult.add(strTemp);
						strTemp = "<b>ָ�����Ϊ��"+lID+"</b><br>";
						vcResult.add(strTemp);
					}
					
			}
			
			
		}
		request.setAttribute("return",vcResult);
		System.out.println("#######################"+vcResult.size());
		//�����ת
		PageControllerInfo pageControllerInfo = new PageControllerInfo();
		pageControllerInfo.setSessionMng(sessionMng);
		pageControllerInfo.setUrl(strContext +"/capital/sign/batsign006-v.jsp");
		//�ַ�
		RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		rd.forward(request,response);
	}
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		Log.print("batsign005-c�쳣��"+ie.toString());
		return;
	}	
		
 %>

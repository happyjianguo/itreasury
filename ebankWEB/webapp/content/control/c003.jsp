<%--
 ҳ������ ��c461.jsp
 ҳ�湦�� : �������´����������ÿ���ҳ��
 ��    �� ��jinchen
 ��    �� ��2004-09-19
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@page import="	java.sql.*,
                    java.rmi.RemoteException,
					com.iss.itreasury.loan.contractcontent.bizlogic.*,
					com.iss.itreasury.loan.util.LOANHTML,
					com.iss.itreasury.loan.contractcontent.dao.ContractContentDao,
					com.iss.itreasury.loan.contractcontent.dataentity.*,
					com.iss.itreasury.util.*,
					com.iss.itreasury.ebank.util.*,
                    java.util.*"%>				   
   
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/**
	 * ҳ����Ʊ���
	 */
	String strSourcePage 		= "";		//��Դҳ��
    String strNextPageURL 		= "";		//��һ����ת��ҳ��
	String strSuccessPageURL 	= "";		//�����ɹ���ת����ҳ��
	String strFailPageURL 		= "";		//����ʧ����ת����ҳ��
	String strAction 			= "";		//��������
	String strActionResult		= "";		//�������
	String strParentPageURL		= "";		//��ҳ��

	/**
	 * ����ҵ��dataentity
	 */
	ContractContentQueryInfo qInfo = new ContractContentQueryInfo();
	
	
try
{	
	
	/**
		* isLogin start
		*/
		//�ж��Ƿ��¼
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//�ж��Ƿ���Ȩ��
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	
	/**
	 * ��request�л�ȡҳ����Ʋ���
	 */
	String strTemp = "";
	
	strTemp = (String)request.getAttribute("strSourcePage");
	if (strTemp != null && strTemp.trim().length()>0){					//��Դҳ��
		strSourcePage = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strSuccessPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//�ɹ�ҳ��
		strSuccessPageURL = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strFailPageURL");
	if (strTemp != null && strTemp.trim().length() > 0){				//ʧ��ҳ��
		strFailPageURL = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strAction");
	if (strTemp != null && strTemp.trim().length() > 0){				//��������
		strAction = strTemp.trim();
	}
	strTemp = (String)request.getAttribute("strParentPageURL");
	if (strTemp != null && strTemp.trim().length()>0){					//��ҳ��
		strParentPageURL = strTemp.trim();
	}
		
	

	/**
	 * ����ύ����
	 */

	//�������
	 
	 
	 long lID=-1;
	 long lResult = -1;
	
	 long lTypeId = -1;
	 String strYear="";
	 String strSeason="";
	 String strCode="";
	 long lClientID = -1;
	 String strControl="";
	
	String strClientName ="";
	long lParentID = -1;
	long lContentID = -1; //��ͬ�ı���ID
	String PageName = "";
	String prePageName = "";
	long PageNo = 1;
	String strYearNo = "";
	String sAction = "";
	 
	
	//������ֵ

	 strTemp = (String)request.getAttribute("strYear");
		if (strTemp != null && strTemp.trim().length() > 0){				//���
			strYear = strTemp.trim();
			qInfo.setYear(strYear);
		}
	
	 strTemp = (String)request.getAttribute("strSeason");
		if (strTemp != null && strTemp.trim().length() > 0){				//����
			strSeason = strTemp.trim();
			qInfo.setSeason(strSeason);
		}

     strTemp = (String)request.getAttribute("hdnTypeId");
		if (strTemp != null && strTemp.trim().length() > 0){				//����
			lTypeId = Long.parseLong(strTemp.trim());
			qInfo.setDocType(lTypeId);
			
		}
	
	  strTemp = (String)request.getAttribute("hdnlClientID");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			lClientID = Long.parseLong(strTemp.trim());
			qInfo.setParentId(lClientID);
		}
		strTemp = (String)request.getAttribute("hdnlContentID");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			lContentID = Long.parseLong(strTemp.trim());
			
		}
		
		strTemp = (String)request.getAttribute("hdnControl");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			strControl = strTemp.trim();		
		}


		
		strTemp = (String)request.getAttribute("strClientName");
		if (strTemp != null && strTemp.trim().length() > 0){				//
			strClientName = strTemp.trim();
			
			
		}
	

	




	/**
	 * ����Delegation
	 */
	
	ContractContentOperation delegation = new ContractContentOperation();

	

	/**
	 * ���ݲ���������в���
	 */
	if (strAction.equals("add")){			//��������
		
		Log.print("add@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		lContentID  = delegation.getContractContentID(qInfo);


		if(lContentID > 0 )
			{
				ContractContentOperation operation = new ContractContentOperation();
				String sContent = "";
				PageNo=1;
				try
				{
					sContent = operation.getContractContent(lContentID,PageNo);
				}
				catch(IException e)
				{
					//LOANHTML.showExceptionMessage(out, sessionMng, e, request, response, "�������ͳ�Ʊ�", Constant.RecordStatus.VALID);
					//LOANHTML.showMessage(out,sessionMng,request,response,"�������ͳ�Ʊ�",Constant.RecordStatus.VALID,"Loan_E010");
					//e.printStackTrace();
					out.flush();
					return;
				}
				request.setAttribute("lClientID", String.valueOf(lParentID));
				request.setAttribute("sContent",sContent);
				request.setAttribute("lContentID",String.valueOf(lContentID));
			}
			request.setAttribute("year",strYear);
			request.setAttribute("season",strSeason);
			request.setAttribute("lTypeId",lTypeId+"");
			request.setAttribute("lClientID",lClientID+"");
			request.setAttribute("strClientName",strClientName);
			request.setAttribute("control",strControl);
			strActionResult = Constant.ActionResult.SUCCESS;
	}	
	






	else if (strAction.equals("modify")){			//�޸ı���
		
		Log.print("�޸�@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
			/**
		 * ��ֵdataentity
		 */
		

		
	
		
			if(lContentID > 0 )
			{
				ContractContentOperation operation = new ContractContentOperation();
				String sContent = "";
				PageNo=1;
				try
				{
					sContent = operation.getContractContent(lContentID,PageNo);
				}
				catch(IException e)
				{
					//LOANHTML.showExceptionMessage(out, sessionMng, e, request, response, "�������ͳ�Ʊ�", Constant.RecordStatus.VALID);
					//LOANHTML.showMessage(out,sessionMng,request,response,"�������ͳ�Ʊ�",Constant.RecordStatus.VALID,"Loan_E010");
					//e.printStackTrace();
					out.flush();
					return;
				}
				request.setAttribute("lClientID", String.valueOf(lParentID));
				request.setAttribute("sContent",sContent);
				request.setAttribute("lContentID",String.valueOf(lContentID));

				
			}
			

			request.setAttribute("year",strYear);
			request.setAttribute("season",strSeason);
			request.setAttribute("lTypeId",lTypeId+"");
			request.setAttribute("lClientID",lClientID+"");
			request.setAttribute("strClientName",strClientName);
			request.setAttribute("control",strControl);
			strActionResult = Constant.ActionResult.SUCCESS;
	}
		



}catch( Exception exp ){


	
	
	exp.printStackTrace();
	/**
	 * �����쳣,��ӱ�����Ϣ
	 */
	sessionMng.getActionMessages().addMessage(exp); 
	/**
	 * �����쳣,���������Ϊʧ��
	 */
	strActionResult = Constant.ActionResult.FAIL;
}
	
	/**
	 * �������������request��
	 */
	request.setAttribute("strActionResult",strActionResult);
	
	/**
	 * ���ݴ�����������һ����ת��Ŀ��ҳ��
	 */
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult)) ? strSuccessPageURL : strFailPageURL;

	/** 
	 * ת����һҳ�� 
	 **/
	Log.print("Next Page URL:" + strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>
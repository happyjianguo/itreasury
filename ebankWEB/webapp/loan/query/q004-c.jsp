<%
/**
 * ҳ������ ��q004-c.jsp
 * ҳ�湦�� : �쿴����������Ϣ������
 * ��    �� ��gump
 * ��    �� ��2003-09-27
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.ebank.obsystem.bizlogic.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loanapply.bizlogic.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%	
	long        loanID=-1;          //����������ˮ��
	long		userID=-1;
	
    long pageNo=1;
    long orderParam=99;
    long lDesc=Constant.PageControl.CODE_ASCORDESC_DESC;

	/* �û���¼�����Ȩ��У�鼰�ļ�ͷ��ʾ */
    try{
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
		loanID=GetNumParam(request,"lLoanID",-1);
		userID=sessionMng.m_lUserID;
		orderParam=GetNumParam(request,"nOrderParam",99);
		lDesc=GetNumParam(request,"nDesc",Constant.PageControl.CODE_ASCORDESC_DESC);
		pageNo=GetNumParam(request,"nPageNo",1);
		
		String strURL="/loan/query/q005-v.jsp?lLoanID="+loanID
			+"&nOrderParam="+orderParam
			+"&nDesc="+lDesc
			+"&nPageNo="+pageNo;
		
		OBLoanQuery lla=new OBLoanQuery();
		OBSystemHome home = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
		OBSystem lcs = home.create();
		
		/*��ѯ������Ϣ*/
		LoanApplyInfo laInfo=lla.findByID(loanID);		
		
		//��ѯ�ƻ���Ϣ
		Collection c=lla.findPlanByLoanID(loanID,pageNo,Constant.PageControl.CODE_PAGELINECOUNT,orderParam,lDesc);
		long clientID=laInfo.getBorrowClientID();
		long wtClientID=laInfo.getConsignClientID();
		
		ClientInfo clientInfo=lcs.findClientByID(clientID);
		ClientInfo wtClientInfo=lcs.findClientByID(wtClientID);
		
		request.setAttribute("LoanApplyInfo",laInfo);
		request.setAttribute("wtClientInfo",wtClientInfo);
		request.setAttribute("Collection",c);
		request.setAttribute("ClientInfo",clientInfo);
		request.setAttribute("nOrderParam",String.valueOf(orderParam));
		request.setAttribute("nDesc",String.valueOf(lDesc));
		request.setAttribute("nPageNo",String.valueOf(pageNo));
		
		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward�����ҳ�� */
	    rd.forward(request, response);
		return;
		
     }catch (Exception ie) {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"�ͻ�����", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
     }
%>
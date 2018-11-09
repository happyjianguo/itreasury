<%
/**
 * ҳ������ ��q091-c.jsp
 * ҳ�湦�� : ��ѯ����ͬ�ƻ���ѯ-������ҳ��
 * ��    �� ��qqgd
 * ��    �� ��2004-2-20
 * �޸���ʷ ��
 */
%>
<%@page contentType="text/html;charset=gbk"%>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.system.dao.PageLoader,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,			
				com.iss.itreasury.ebank.obdataentity.*,	
				com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.ebank.obquery.dataentity.*,
				com.iss.itreasury.loan.query.bizlogic.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<%
	try
	{
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

		OBContractQuery contractejb = new OBContractQuery();
		ContractInfo contractinfo = new ContractInfo();
		long lContractID=GetNumParam(request,"lContractID",-1);

		contractinfo = contractejb.findByID(lContractID);
		String strContractCode=contractinfo.getContractCode();//��ͬ���
				
		OBQueryTermInfo qInfo=new OBQueryTermInfo();
		qInfo.setContractID(lContractID);
		qInfo.setClientID(sessionMng.m_lClientID);
		Collection c=contractejb.queryContractPlan(qInfo);

		request.setAttribute("ContractCode",strContractCode);
		request.setAttribute("queryResult",c);
		String strURL = "/loan/query/q092-v.jsp";
	
		/* ��ȡ�����Ļ��� */
		ServletContext sc = getServletContext();
	    
		/* ���÷��ص�ַ */
		RequestDispatcher rd=null;
		rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,strURL)));
		
		/* forward�����ҳ�� */
	    rd.forward(request, response);
		return;
	}
	catch(Exception exp)
	{
		exp.printStackTrace();
		out.flush();
		return;
	}
%>
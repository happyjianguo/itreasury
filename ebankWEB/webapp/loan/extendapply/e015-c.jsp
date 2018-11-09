<%
/**
 * ҳ������ ��S614.jsp
 * ҳ�湦�� : ����չ�����봦��-����
 * ��    �� ����Զ��
 * ��    �� ��2003-10-23
 * ����˵�� ���÷Ŵ������ѯ����
 *			  
 * ת��ҳ�� : S611.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>

<%
	/* ����̶����� */
	String strTitle = "[չ������]";
%>		

<%
	try
	{
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		long lModuleID = Constant.ModuleType.LOAN;
		//ģ������
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER ;
		long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
		
    	// �������
		String strcontrol = "";//���ƶ���
		String strFirst = "yes";
		long lContractID = -1;
		long lExtendID = -1;
		long lAType = 1;
		long lEType = 1;
		String strTmp = "";
		String sTitle = "һ�����";
		String sAction = "";
		String sEType = "չ��";

        String sAmount = "";
		double dAmount = 0;
		double dBalance = 0;
		Timestamp dtExtendStartDate = null;
		Timestamp dtInputDate = null;
		double dRate = 0;
		String sReason = "",sSource = "",sOther = "";
		long nNum = -1;
		String sLiborName = "";
		boolean libor = false;
		long nLength = 0;
		long lBankRateTypeID = 0;

		OBSecurityInfo secinfo = new OBSecurityInfo();
		
		long lInterestType = -1;
		long lLiborRateID  = -1;
		double dRateAdjust = 0.0;
		
//----------------------------------------------do add------------------------------------------------------------
			
		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply e_ejb = extendApplyHome.create();
		

		OBRepayPlanInfo rp_info = new OBRepayPlanInfo();  // �����ύ�󱣴�չ���� or action = save
		ArrayList alist = new ArrayList();

//--------------------------------------------do add end----------------------------------------------------------
		strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) 
		{
			lExtendID = Long.valueOf(strTmp).longValue();
		} 
		
		//��ָ���ѯ�����Ĳ���
		String txtSearch = "";
		if( (String)request.getAttribute("txtSearch") != null )
			txtSearch = (String)request.getAttribute("txtSearch");

		//cancel����
		long lResult = e_ejb.updateStatus(lExtendID,OBConstant.LoanInstrStatus.SUBMIT,secinfo);
		
		OBExtendApplyInfo e_info = e_ejb.findExtendByID(lExtendID,-1,secinfo);

		response.sendRedirect("../extendapply/e017-v.jsp?control=view&txtSearch="+txtSearch+"&lID="+lExtendID+"&strCode="+e_info.getInstructionNo());

		//lLoanTypeID = e_info.getLoanTypeID();


		//RepayPlanInfo r_info = new RepayPlanInfo();  //  ҳ����ʾ��
		
		//request.setAttribute("c_info",c_info);
		//request.setAttribute("e_info",e_info);
		//request.setAttribute("attribtype",lAType+"");
		//request.setAttribute("txtAction",sAction);
		//request.setAttribute("control",strcontrol);
		//request.setAttribute("first",strFirst);
		//request.setAttribute("lExtendID",lExtendID+"");
		//request.setAttribute("lContractID",e_info.getContractID()+"");
		
		/* ��ȡ�����Ļ���*/
		//ServletContext sc = getServletContext();
		/* ���÷��ص�ַ */
		//RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e017-v.jsp")));
		/* forward�����ҳ�� */
		//rd.forward(request, response);
		
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>
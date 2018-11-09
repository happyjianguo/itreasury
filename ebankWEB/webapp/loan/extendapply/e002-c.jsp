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
<%String strContext = request.getContextPath();%>
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

		OBSecurityInfo secinfo = new OBSecurityInfo();

		//��ȡstrcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

		strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lExtendID = Long.valueOf(strTmp).longValue();
		} else {
			out.println("����û��չ�ڱ�ʶ.");
			strcontrol = "";
		}

		OBExtendApplyHome extendApplyHome = (OBExtendApplyHome)EJBObject.getEJBHome("OBExtendApplyHome");
		OBExtendApply e_ejb = extendApplyHome.create();
		OBExtendApplyInfo e_info = e_ejb.findExtendByID(lExtendID,-1,secinfo);
		
		lLoanTypeID = e_info.getLoanTypeID();
		
		//ContractHome contractHome = (ContractHome)EJBObject.getEJBHome("ContractHome");
		//Contract c_ejb = contractHome.create();
		
        //ContractInfo c_info = c_ejb.findByID(e_info.m_lContractID);
		ContractInfo c_info = e_ejb.findExtendByID(-1,e_info.getContractID(),secinfo).getC_Info();

		strTmp = (String)request.getAttribute("attribtype");
		if (strTmp != null && strTmp.length() > 0) {
			lAType = Long.valueOf(strTmp).longValue();
		}

		strTmp = (String)request.getAttribute("txtAction");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  sAction = strTmp;
		}

		//��ָ���ѯ�����Ĳ���
		String txtSearch = "";
		if( (String)request.getAttribute("txtSearch") != null )
			txtSearch = (String)request.getAttribute("txtSearch");
			
		//���ָ���ѯ�����Ĳ���,���Ϊ"yes",���ʾ��ѯ���޸Ĺ�
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");

		//RepayPlanInfo r_info = new RepayPlanInfo();  //  ҳ����ʾ��
		
		request.setAttribute("c_info",c_info);
		request.setAttribute("e_info",e_info);
		request.setAttribute("attribtype",lAType+"");
		request.setAttribute("txtAction",sAction);
		request.setAttribute("control",strcontrol);
		request.setAttribute("first",strFirst);
		request.setAttribute("lExtendID",lExtendID+"");
		request.setAttribute("lContractID",e_info.getContractID()+"");
		request.setAttribute("txtSearch",txtSearch);
		
		/* ��ȡ�����Ļ���*/
		ServletContext sc = getServletContext();
		/* ���÷��ص�ַ */
		RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/extendapply/e013-v.jsp")));
//����,����

			//�����ָ���ύ������ҳ��,���ҵ�¼�˲���׫д��,��ֻ�ܿ�
			if(txtSearch.equals("modify"))
			{
				if(e_info.getInputUserID()==sessionMng.m_lUserID&&!txtChanged.equals("yes")&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.ACCEPT&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.REFUSE&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.CANCEL&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.APPROVE)
				{
					System.out.println("inininiininini="+txtChanged);
					
					//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
					PageControllerInfo pageControllerInfo = new PageControllerInfo();
					pageControllerInfo.setSessionMng(sessionMng);
					pageControllerInfo.setUrl(strContext + "/loan/extendapply/e013-v.jsp");
					//�ַ�
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
					//request.setAttribute("lID", lID+"");
					request.setAttribute("txtChanged","yes");
				}else
				{
					//�������
					//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
					PageControllerInfo pageControllerInfo = new PageControllerInfo();
					pageControllerInfo.setSessionMng(sessionMng);
					pageControllerInfo.setUrl(strContext + "/loan/extendapply/e009-v.jsp");
					//�ַ�
					rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
				}
			}

		/* forward�����ҳ�� */
		rd.forward(request, response);
		
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>
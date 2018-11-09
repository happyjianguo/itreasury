<%--
/*
 * �������ƣ�c001-c.jsp
 * ����˵�����ʽ𻮲��޸Ŀ���ҳ��
 * �������ߣ�����
 * ������ڣ�2004��01��06��
 */
--%>
 
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*
			     " 
%>
<%@ page import="com.iss.itreasury.project.wisgfc.ebank.special.bizlogic.ConsignReceiveBiz" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--Header start-->

<%!
	/* ����̶����� */
	String strTitle = "[�ʽ𻮲�]";
	String strTemp = "";
%>
<%
	/* ʵ�ֲ˵����� */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	
	String lsign = null;
	String sign = "";
	lsign = request.getParameter("sign");
	if(lsign!=null&&lsign.trim().length()>0)
	{
		sign = lsign;
	}

	String lreport=null;
	String report="";
	lreport = request.getParameter("report");
	if(lreport!=null&&lreport.trim().length()>0)
	{
	      report=lreport;
	}

	
	String lupdate = null;
	String update = "";
	lupdate = request.getParameter("update");
	if(lupdate!=null&&lupdate.trim().length()>0)
	{
		update = lupdate;
	}
	

%>

<%  
	/* �û���¼�����Ȩ��У�� */
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
	
	
%>

<!--Get Data start-->
<%
		/* ָ����� */
		long lInstructionID = -1;
		strTemp = request.getParameter("lInstructionID");
		if(strTemp != null && strTemp.length() > 0){
			lInstructionID = Long.parseLong(strTemp);
		}
		Log.print("------------------------------lInstructionID="+lInstructionID);
		
		//FinanceInfo tempFinanceInfo = null;
		//if(session.getAttribute("financeInfo") != null)
		//	tempFinanceInfo = (FinanceInfo)session.getAttribute("financeInfo");
		//session.setAttribute("financeInfo", null);
%>
<!--Get Data end-->

<!--Access DB start-->
<%
		/* ʵ������Ϣ�� */
		FinanceInfo financeInfo = null;
		
		/* ��ʼ��EJB */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		
		/* ���÷�����ȡ��Ϣ���� */	
		financeInfo = financeInstr.findByID(lInstructionID, sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
	//	tempFinanceInfo.setSBatchNo(financeInfo.getSBatchNo());
		System.out.println("��ѯ����3"+financeInfo.getSBatchNo());
		//if(	lInstructionID < 0 && tempFinanceInfo!=null && tempFinanceInfo.getTransType()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY) 
		//	financeInfo = tempFinanceInfo;
		
		 //��ѯ�Ƿ�����ί���տ����ɵ���ת add by xlchang 2010-12-06 �������
        ConsignReceiveBiz crBiz = new ConsignReceiveBiz();
        if ( crBiz.isConsignReceive(lInstructionID)) {
        	request.setAttribute("isConsignReceive", "true");
        }
        
%>
<!--Access DB end-->

<!--Forward start-->
<%
		/* �������б��������� */
	    request.setAttribute("financeInfo", financeInfo);
		
	    /* ��ȡ�����Ļ���*/
	    //ServletContext sc = getServletContext();
		RequestDispatcher rd = null;
	    /* ���÷��ص�ַ */
		String flag = "";
		flag=(String)request.getAttribute("flag");
		if(report.equals("report"))
		{
		request.setAttribute("modify","modify");
		}
		if(flag!=null&&flag.equals("delete")){
			request.setAttribute("delete","delete");
		}
		String strNextPageURL="";
		if(sign.equals("again"))
		{
			strNextPageURL="/capital/captransfer/c002-v.jsp?sign="+sign;
		}
		else
		{
			strNextPageURL="/capital/captransfer/c002-v.jsp";
		}
		
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));		
	    
		/* forward�����ҳ�� */
	    rd.forward(request, response);
	} 
	catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}
%>
<!--Forward end-->


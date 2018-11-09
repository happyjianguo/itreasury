<%
/**
 * ҳ������ ��c012_p.jsp
 * ҳ�湦�� : ���׼�¼��ӡ��ϸ��ѯ��������ҳ��
 * ��    �� ��Boxu
 * ��    �� ��2006-12-15
 * ����˵�� ��
 *			
 * �޸���ʷ ��
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionSumInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QTransaction"%>
<%@ page import="com.iss.itreasury.settlement.util.UtilOperation"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant"%>
<%@ page import="com.iss.itreasury.ebank.print.dataentity.PrintrecordInfo"%>
<%@ page import="com.iss.itreasury.ebank.print.bizlogic.EbankPrintApplyBiz"%>
<%@ page import="com.iss.itreasury.ebank.print.dao.EbankQTransaction"%>
<%@ page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@ page import="com.iss.itreasury.util.PageController"%>
<%@ page import="com.iss.system.dao.PageLoader"%>
<%@ page session="true"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% String strContext = request.getContextPath();%>
<%
	/* ����̶����� */
	String strTitle = "[�ص���ӡ����]";
%>
<%
	//ҳ����Ʊ���
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = "";
	String strActionResult = Constant.ActionResult.FAIL;
	
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
		
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		//��ȡ����
		String strTemp = "";
		long lID = -1;
		long TransactionTypeID = -1;
		String TransNo = "";
		String payaccountno = "";
		Collection coll = null;
		
		strTemp = (String)request.getAttribute("strAction");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			strAction = strTemp;
		}
		System.out.println("strAction===="+strAction);
		strTemp = (String)request.getAttribute("lID");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			lID = Long.parseLong(strTemp);
		}
		System.out.println("lID===="+lID);
		strTemp = (String)request.getAttribute("TransactionTypeID");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			TransactionTypeID = Long.parseLong(strTemp);
		}
		System.out.println("TransactionTypeID===="+TransactionTypeID);
		strTemp = (String)request.getAttribute("TransNo");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			TransNo = strTemp;;
		}
		System.out.println("TransNo===="+TransNo);
		strTemp = (String)request.getAttribute("payaccountno");
		if ( strTemp != null && strTemp.trim().length() > 0 )
		{
			payaccountno = strTemp;
		}
		System.out.println("payaccountno===="+payaccountno);
		
		//���Է�ҳ
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		if ( strPageLoaderKey != null )
		{
			request.setAttribute("_pageLoaderKey",strPageLoaderKey);
		}
		
		//��ѯ��ӡ��¼��Ϣ��ʼ
		if ( strAction.equals("toModify") )
		{
			EbankPrintApplyBiz papplybiz = new EbankPrintApplyBiz();
			QueryTransactionInfo queryinfo = new QueryTransactionInfo();
			queryinfo.setID(lID);
			queryinfo.setTransactionTypeID(TransactionTypeID);
			queryinfo.setTransNo(TransNo);
			queryinfo.setPayAccountNo(payaccountno);
			request.setAttribute("QueryTransaction",queryinfo);
			
			//��ѯ��ӡ��Ϣ
			PrintrecordInfo prinfo = new PrintrecordInfo();
			prinfo.setNclientid(sessionMng.m_lClientID);
			prinfo.setNofficeid(sessionMng.m_lOfficeID);
			prinfo.setNcurrency(sessionMng.m_lCurrencyID);
			prinfo.setNtransactiontypeid(TransactionTypeID);
			prinfo.setNprintcontentid(lID);
			prinfo.setNdeptid(VOUConstant.PrintSection.EBANKCUSTOMER);
			
			//boxu update ��ѯ���������ӡ��������Ҫ����"���´�"��"����"
			coll = papplybiz.getPrintDetailByTransID(lID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			
			request.setAttribute("printresult",coll);
			
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
		{
			strActionResult = Constant.ActionResult.FAIL;
		}
		
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		sessionMng.getActionMessages().addMessage(exp);
	}
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = strActionResult.equals(Constant.ActionResult.SUCCESS) ? strSuccessPageURL:strFailPageURL;
	System.out.println("strNextPageURL :  " + strNextPageURL);

	//ת����һҳ��
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	rd.forward( request,response );
%>
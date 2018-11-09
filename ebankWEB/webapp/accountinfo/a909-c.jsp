<%--
 ҳ������ ��a909-c.jsp
 ҳ�湦�� : ���� ������ʾһ����¼�Ŀ���ҳ��
 ��    �� ��qqgd
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
				1����ѯĳһ����¼
 �޸���ʷ ��
--%>

<%//@ page import="com.iss.itreasury.settlement.util.EJBHomeFactory" %>
<%@ page import="com.iss.itreasury.settlement.transdiscount.bizlogic.TransDiscountHome" %>


<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dao.Sett_TransRepaymentDiscountDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<% String strContext = request.getContextPath();%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script> 

	<%
System.out.println("\n\n ���� c017.jsp \n\n");
	//ҳ����Ʊ���
	String strNextPageURL = "";
	String strSuccessPageURL = "../view/v016.jsp";
	String strFailPageURL = "../view/v017.jsp";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;
	long lnDiscountNoteID=-1;
    
	//����ҳ�����
	long lID = -1; // ���ַ���ҵ��ID	
	String strTransNo = "";
	
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
		
		//TransDiscountDelegation delegation=new TransDiscountDelegation();
		Sett_TransRepaymentDiscountDAO loanDelegation = new Sett_TransRepaymentDiscountDAO();
		
		
		
		//Ϊ�޸�ҳ���ѯ����
		String strTemp="";
		strTemp=(String)request.getAttribute("strAction");
		if (strTemp != null && strTemp.trim().length()>0 )
		{
			strAction = strTemp;
		}
		
		if ("Query".equals(strAction))
		{
			strTemp=(String)request.getAttribute("strTransNo");
			if (strTemp != null && strTemp.trim().length()>0 )
			{
				strTransNo = strTemp;
			}
	        strAction = (String)request.getAttribute("strAction");
			strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
			strFailPageURL = (String)request.getAttribute("strFailPageURL");
		}
		
        strTemp=(String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length()>0 )
		{
		    lID = Long.valueOf(strTemp).longValue();
		}
		
		if (strTransNo != null && !strTransNo.equals(""))
		{
			//lID = delegation.repaymentGetIDByTransNo(strTransNo);
			lID=loanDelegation.getIDByTransNo(strTransNo);
		}
		Log.print("lID = " + lID);
		
		/*----------------Ϊ���ж��ո������-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		Log.print("lOBReturn="+lOBReturn);
		/*--------end of qqgd adding fragment---------------*/
		
		if(lID > 0)
		{
			//����������������ҵ����ĵ���
			//TransRepaymentDiscountInfo info = delegation.repaymentFindDetailByID(lID);	
			TransRepaymentDiscountInfo info=loanDelegation.findByID(lID);
			if(info!=null)
			{
				request.setAttribute("matchResult",info);
				strActionResult=Constant.ActionResult.SUCCESS;
			}
			else
			{
               // sessionMng.getActionMessages().addMessage("��ѯ�������ݣ�");	     				
			    strActionResult=Constant.ActionResult.SUCCESS;
			}
		}	
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		//sessionMng.getActionMessages().addMessage(exp);
		strActionResult = Constant.ActionResult.FAIL;		
	}	
	request.setAttribute("strActionResult",strActionResult);
	//���ݴ�����������һ����ת��Ŀ��ҳ��
	strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
	//ת����һҳ��
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));

	rd.forward( request,response);
%>
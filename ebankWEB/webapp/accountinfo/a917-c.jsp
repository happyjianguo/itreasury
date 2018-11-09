<%--
 ҳ������ ��a917-c.jsp
 ҳ�湦�� : ������/ί�У������ջش������ҳ��
 ��    �� ��qqgd
 ��    �� ��
 ����˵�� ��ʵ�ֲ���˵����
				1������
				2��ȡ������
 �޸���ʷ ��
--%>
	<%@ page contentType = "text/html;charset=gbk" %>
	<%@ page import="java.sql.Timestamp"%>
	<%@ page import="java.util.Collection"%>
	<%@ page import="com.iss.itreasury.util.Log"%>
	<%@ page import="com.iss.itreasury.util.Env"%>
	<%@ page import="com.iss.itreasury.ebank.util.*"%>
	<%@ page import="com.iss.itreasury.util.Constant"%>
	<%@ page import="com.iss.itreasury.util.DataFormat"%>
	<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
	<%@ page import="com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo"%>
	<%@ page import="com.iss.itreasury.settlement.transloan.dao.Sett_TransRepaymentLoanDAO"%>
	<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
	<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<%
	//ҳ����Ʊ���
    String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

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
			
	      //�������		
	long lCheckUserID = sessionMng.m_lUserID;
	long lID = -1;
	String strCheckAbstract = "";
	java.sql.Timestamp tsModify = null;
	
	String strModify = null;
	String strTransNo = "";
	
	String strTemp = null;
	
	strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCheckAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("tsModify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsModify = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
			
	//��ȡ����
	strAction = (String)request.getAttribute("strAction");
	strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		TransRepaymentLoanInfo transRepaymentLoanInfo = new TransRepaymentLoanInfo();
		//TransLoanDelegation transLoanDelegation = new TransLoanDelegation();
		Sett_TransRepaymentLoanDAO transLoanDelegation = new Sett_TransRepaymentLoanDAO();		
		
		if("Query".equals(strAction))//�޸�
		{
			if ("Query".equals(strAction) && strTransNo != null && !strTransNo.equals(""))
			{
			//	lID = transLoanDelegation.repaymentGetIDByTransNo(strTransNo);
				lID=transLoanDelegation.getIDByTransNo(strTransNo);
			}
			Log.print("lID = " + lID);
		}
	

		
		//transRepaymentLoanInfo = transLoanDelegation.repaymentFindDetailByID(lID);
		transRepaymentLoanInfo = transLoanDelegation.findByID(lID);
		System.out.println("TransactionTypeID="+transRepaymentLoanInfo.getTransactionTypeID());
		
		/*----------------Ϊ���ж��ո������-----------*/
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lOBReturn = -1;
		if (transRepaymentLoanInfo.getTransactionTypeID()==SETTConstant.TransactionType.CONSIGNLOANRECEIVE)
		{
			lOBReturn = obdao.getConsnTransDirect(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		}
		else 
		{
			lOBReturn = obdao.getTransDirect(strTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		}
		request.setAttribute("lReturn",String.valueOf(lOBReturn));
		/*--------end of qqgd adding fragment---------------*/
		
		
		Log.print("���˼�¼ID��"+lID);

		if(transRepaymentLoanInfo == null)
		{
			throw new Exception("Gen_E001");
		} 		
	
		//��������д��request�У���������ִ�з����쳣ʱ��ת��viewҳ�����������ݻ���
		request.setAttribute("searchResults",transRepaymentLoanInfo);
		
		  //����������������ҵ����ĵ���
        if(String.valueOf(SETTConstant.Actions.CHECK).equals(strAction))
        {
        /*
			transRepaymentLoanInfo.setCheckAbstract(strCheckAbstract);
			transRepaymentLoanInfo.setCheckUserID(lCheckUserID);
			transRepaymentLoanInfo.setModify(tsModify);
			
			Log.print(transRepaymentLoanInfo);
            if(transLoanDelegation.repaymentCheck(transRepaymentLoanInfo) == lID )
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("���˳ɹ�");
            }
			else
			{
				 strActionResult = Constant.ActionResult.FAIL;
                //sessionMng.getActionMessages().addMessage("����ʧ��");
			}
		*/
        }
		else if(String.valueOf(SETTConstant.Actions.CANCELCHECK).equals(strAction))
        {
        /*
			transRepaymentLoanInfo.setCheckAbstract(strCheckAbstract);
			transRepaymentLoanInfo.setCheckUserID(lCheckUserID);
			transRepaymentLoanInfo.setModify(tsModify);
			
			Log.print(transRepaymentLoanInfo);
			//Log.print("ȡ������ID:"+lID);
            if(transLoanDelegation.repaymentCancelCheck(transRepaymentLoanInfo) == lID)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
                //sessionMng.getActionMessages().addMessage("ȡ�����˳ɹ�");
				strSuccessPageURL = "../control/c013.jsp?lStatusID="+SETTConstant.TransactionStatus.CHECK+"&lTransactionTypeID="+SETTConstant.TransactionType.TRUSTLOANRECEIVE+"&strSuccessPageURL=../view/v017.jsp&strFailPageURL=../view/v017.jsp&strAction="+SETTConstant.Actions.LINKSEARCH;
				request.setAttribute("strAction",String.valueOf(SETTConstant.Actions.LINKSEARCH));
            }
			else
			{
				 strActionResult = Constant.ActionResult.FAIL;
                 //sessionMng.getActionMessages().addMessage("ȡ������ʧ��");
			}
		*/	
        }
		else if("toCancelCheck".equals(strAction) || "Query".equals(strAction))
		{
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
        {
            Log.print("��Ч����");
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
	Log.print("Next Page URL:"+strNextPageURL);	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>
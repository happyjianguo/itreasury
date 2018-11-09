<%--
 ҳ������ ��c161.jsp
 ҳ�湦�� : ��������������������ҳ��
 ��    �� ��liuyang
 ��    �� ��
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.loan.util.*" %>
<%@ page import="com.iss.itreasury.bill.util.*" %>
<%@ page import="com.iss.itreasury.budget.query.queryobj.QBudget" %>
<%@ page import="com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo" %>
<%@ page import="com.iss.itreasury.budget.bizdelegation.ConstituteDelegation" %>
<%@ page import="com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo" %>
<%@ page import="com.iss.itreasury.budget.util.BUDGETConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml" %>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/**
	 * ҳ�������
	 */
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	QueryBudgetInfo qInfo = null;
	String strTitle = "Ԥ��ṹ����"; 

try
{	
	
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
	

	/**
	 * ��request�л��ҳ�������Ϣ
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	 * ����ҵ��dataentity
	 */
	qInfo = new QueryBudgetInfo();
	qInfo.convertRequestToDataEntity(request);
	
	/**
	 * ����Delegation
	 */
	ConstituteDelegation delegation = new ConstituteDelegation();

	/**
	 * ���ݲ���������в���
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.MATCHSEARCH)))	//
	{
			qInfo.setShowColumn(new long[]{
			BUDGETConstant.BudgetColumnList.CURRENTBUDGET,
			BUDGETConstant.BudgetColumnList.BUDGETSTRUCTURE,
			BUDGETConstant.BudgetColumnList.CURRENTEXECUTE,
			BUDGETConstant.BudgetColumnList.EXECUTESTRUCTURE
			});
		Collection c = delegation.findAll(qInfo);
		long level = delegation.getItemMaxLevel(qInfo.getClientID(),qInfo.getBudgetSystemID());

		request.setAttribute("searchResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));
		/**
		* ���������Ϊ�ɹ�
		*/
		pageInfo.success();
	}


}
catch( Exception exp )
{
	exp.printStackTrace();
	/**
	 * �����쳣,��ӱ�����Ϣ
	 */
	sessionMng.getActionMessages().addMessage(exp); 
	/**
	 * �����쳣,���������Ϊʧ��
	 */
	pageInfo.fail();
}
	
	/**
	 * �������������request��
	 */
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	request.setAttribute("QueryBudgetInfo",qInfo);
	
	/** 
	 * ת����һҳ�� 
	 **/
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>
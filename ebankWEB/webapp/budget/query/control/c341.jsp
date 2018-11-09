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
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	/**
	 * ҳ�������
	 */
	PageCtrlInfo pageInfo = new PageCtrlInfo();
	String strAction = "";

try
{	
	
	/** Ȩ�޼�� **/
	if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"��¼",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
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
	QueryBudgetInfo qInfo = new QueryBudgetInfo();
	qInfo.convertRequestToDataEntity(request);
	
	/**
	 * ����Delegation
	 */
	QBudget delegation = new QBudget();
	ConstituteDelegation delega= new ConstituteDelegation();
	long versionNo=-1;
	/**
	 * ���ݲ���������в���
	 */
	 BudgetPlanInfo planinfo=new BudgetPlanInfo();
	strAction = (String)request.getAttribute("strAction");
	Collection diffClientColl= new ArrayList();
	if(String.valueOf(Constant.Actions.NEXTSTEP).equals(strAction)){

			planinfo.setShowColumn(new long[]{
			BUDGETConstant.BudgetColumnList.ABUDGETAMOUNT,
			BUDGETConstant.BudgetColumnList.AEXCUTEAMOUNT,
			BUDGETConstant.BudgetColumnList.BBUDGETAMOUNT,
			BUDGETConstant.BudgetColumnList.BEXCUTEAMOUNT,
			BUDGETConstant.BudgetColumnList.BUDGETSUB,
			BUDGETConstant.BudgetColumnList.BUDGETRATE,
			BUDGETConstant.BudgetColumnList.EXCUTESUB,
			BUDGETConstant.BudgetColumnList.EXCUTERATE
			});
        planinfo.setBudgetFlag(BUDGETConstant.BudgetFlag.ADJUST);
		request.setAttribute("BudgetPlanInfo",planinfo);
		long level = delega.getItemMaxLevel(qInfo.getClientID(),qInfo.getBudgetSystemID());
		
		request.setAttribute("versionNo",String.valueOf(versionNo));
		//��ѯ���з��������ļ�¼����λ�������⣩
		diffClientColl=delegation.DiffClientBudget(qInfo);
		request.setAttribute("maxLevel",String.valueOf(level));
		System.out.println(qInfo.getBudgetSystemID()+"��λ ---"+qInfo.getClientID());
		request.setAttribute("diffClientColl",diffClientColl);
		pageInfo.setStrSuccessPageURL("../view/v342.jsp");
	}
	/**
	 * �����ɹ�
	 */
	pageInfo.success();

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
	request.setAttribute("strActionResult", pageInfo.getStrActionResult());

	/**
	 * ����ӡ��Ϣ����request��
	 */
	if (!pageInfo.getStrPrintMsg().equals(""))request.setAttribute("strPrintMsg",pageInfo.getStrPrintMsg());

	/** 
	 * ת����һҳ�� 
	 **/
	Log.print("Next Page URL:" + pageInfo.getStrNextPageURL());	
				//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>
<%--
 ҳ������ ��c301.jsp
 ҳ�湦�� : Ԥ��汾��ѯ���Ʋ�
 ��    �� ��leiliu	
 ��    �� ��2005-08-04
 ����˵�� ��  
 ʵ�ֲ���˵����
 �޸���ʷ ��
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->
<%@page import="java.util.Collection,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.query.queryobj.QBudget,
				com.iss.itreasury.ebank.util.OBHtml"
%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * ҳ�������
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** ����ҵ��ʵ���� */
QueryBudgetInfo info = null;

try {
	//������
	/** Ȩ�޼�� **/
	String strTableTitle = "Ԥ��汾��ѯ";
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

	/** 
	 * ��request�л��ҳ�������Ϣ 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	* ��ʼ��ҵ��ʵ���� 
	*/
	info = new QueryBudgetInfo();

	/**
	* �����ҳ����
	*/
	info.convertRequestToDataEntity(request);

	/** 
	 * [����ҵ�����]
	 */
	 long showColumn = -1;
	 String versionNo = info.getVersionNo();
	 /*String strTmp = (String)request.getAttribute("showcolumn");
	 if (strTmp != null && strTmp.length()>0 )
	 {
		showColumn = Long.parseLong(strTmp);
	 }*/
	
	/**
	* ��ʼ��ҵ���߼���
	*/
    ConstituteDelegation delegation = new ConstituteDelegation();
	QBudget qBudget = new QBudget();

	/**
	 * ���ݲ����������ҵ�����
	 */
	 

	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.MATCHSEARCH)))	//��������
	{

		System.out.println("********************");

		Collection c = qBudget.queryBudgetVersion(info);
		request.setAttribute("srcheaResult",c);
		/**
		* ���������Ϊ�ɹ�
		*/
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.NEXTSTEP)))	//��һ��
	{
		//������ʾ����
		if (showColumn > 0)
			info.setShowColumn(new long[]{showColumn,BUDGETConstant.BudgetColumnList.UNCHECKBUDGET});
		else
			info.setShowColumn(new long[]{BUDGETConstant.BudgetColumnList.UNCHECKBUDGET});
		
		Collection c = delegation.findAll(info);

		long level = -1;
		level = delegation.getItemMaxLevel(info.getClientID(),info.getBudgetSystemID());
        
		request.setAttribute("srcheaResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));
		request.setAttribute("versionNo",versionNo);
		/**
		* ���������Ϊ�ɹ�
		*/
		pageInfo.success();
	}
	
} 
/**
* �쳣����
*/
catch ( Exception exp ) {
	/**
	* �û��ύ��Ϣ����request��
	*/
	
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
	request.setAttribute("QueryBudgetInfo",info);
	
	/** 
	 * ת����һҳ�� 
	 **/
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	rd.forward( request,response );
%>
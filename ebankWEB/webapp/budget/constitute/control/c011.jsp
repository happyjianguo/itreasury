<%--
 ҳ������ ��c001.jsp
 ҳ�湦�� : Ԥ����ƿ��Ʋ�
 ��    �� ��weilu
 ��    �� ��2005-7-14
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->
<%@page import="java.util.Collection,
				com.iss.itreasury.util.Env,
				com.iss.itreasury.util.Log,
				com.iss.itreasury.util.Constant,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.util.PageCtrlInfo,
				com.iss.itreasury.budget.constitute.dataentity.BudgetPlanInfo,
				com.iss.itreasury.budget.util.BUDGETNameRef,
				com.iss.itreasury.budget.util.BUDGETConstant,
				com.iss.itreasury.budget.util.BUDGETHTML,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.budget.bizdelegation.ConstituteDelegation"
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
BudgetPlanInfo info = null;

try {
	 /** Ȩ�޼�� **/
    Log.print("=================����ҳ��budget/constitute/control/c011.jsp=========");
    String strTitle = "Ԥ�����"; 
    //������
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

	/** 
	 * ��request�л��ҳ�������Ϣ 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	* ��ʼ��ҵ��ʵ���� 
	*/
	info = new BudgetPlanInfo();

	/**
	* �����ҳ����
	*/
	info.convertRequestToDataEntity(request);

	info.setBudgetFlag(BUDGETConstant.BudgetFlag.TOTAL);
	/** 
	 * [����ҵ�����]
	 */
	 long showColumn = -1;
	 String strTmp = (String)request.getAttribute("showcolumn");
	 if (strTmp != null && strTmp.length()>0 )
	 {
		showColumn = Long.parseLong(strTmp);
	 }
	
	/**
	* ��ʼ��ҵ���߼���
	*/
	ConstituteDelegation delegation = new ConstituteDelegation();

	/**
	 * ���ݲ����������ҵ�����
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.MATCHSEARCH)))	//����
	{
		//������ʾ����
		Collection c = delegation.findByCondition(info);
		
		request.setAttribute("searchResult",c);
		
		/**
		* ���������Ϊ�ɹ�
		*/
		pageInfo.success();
		
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.LINKSEARCH)))	//��һ��
	{
		//������ʾ����
		info.setShowColumn(new long[]{BUDGETConstant.BudgetColumnList.UNCHECKBUDGET});		
		
		Collection c = delegation.findAll(info);
				
		long level = delegation.getItemMaxLevel(info.getClientID(),info.getBudgetSystemID());
		
		if (info.getId()>0)
		{
			BudgetPlanInfo ThePlanInfo = delegation.findByPlanID(info.getId());
			ThePlanInfo.setShowColumn(info.getShowColumn());
			info = ThePlanInfo;
		}

		request.setAttribute("searchResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));

		/**
		* ���������Ϊ�ɹ�
		*/
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.COMMIT)))	//�ύ
	{
		System.out.println("�ύ");
		info.setUpdateUserID(sessionMng.m_lUserID);
		info.setUpdateDate(Env.getSystemDate());
		info.setBudgetFlag(BUDGETConstant.BudgetFlag.TOTAL);
		long l = delegation.commitBudget(info);
		sessionMng.getActionMessages().addMessage("�ύ�ɹ�");
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.NEXTSTEP)))	//��һ��
	{
		Collection c = delegation.getLowerClientBudgetStatus(info);
		String versionNo = delegation.getNewVersionNo(info);
		request.setAttribute("searchResult",c);
		request.setAttribute("versionNo",versionNo);
		/**
		* ���������Ϊ�ɹ�
		*/
		pageInfo.success();
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(Constant.Actions.SUM)))	//����
	{
		info.setInputUserID(sessionMng.m_lUserID);
		info.setInputDate(Env.getSystemDate());
		info.setConstituteDate(Env.getSystemDate());
		long lPlanID = delegation.collect(info);
		info.setId(lPlanID);		
		sessionMng.getActionMessages().addMessage("���ܳɹ�");
		
		
		//������ʾ����
		
		info.setShowColumn(new long[]{BUDGETConstant.BudgetColumnList.UNCHECKBUDGET});		
		
		Collection c = delegation.findAll(info);
	
		long level = delegation.getItemMaxLevel(info.getClientID(),info.getBudgetSystemID());
		
		BudgetPlanInfo ThePlanInfo = delegation.findByPlanID(lPlanID);
		ThePlanInfo.setShowColumn(info.getShowColumn());
		info = ThePlanInfo;

		request.setAttribute("searchResult",c);
		request.setAttribute("maxLevel",String.valueOf(level));
		//request.setAttribute("versionNo",versionNo);
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
	 * �����쳣,���ӱ�����Ϣ
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
	request.setAttribute("BudgetPlanInfo",info);
	
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
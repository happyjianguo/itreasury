<%--
 ҳ������ ��c001.jsp
 ҳ�湦�� :  ��ʧҵ�������ҳ��
 ��    �� ��jinchen
 ��    �� ��2004-11-23
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
           
--%>
<%@ page contentType="text/html;charset=gbk" %>

<%@page import="
				java.util.*,
				java.sql.*,
				com.iss.itreasury.util.*,
				com.iss.system.dao.PageLoader,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.*,
				com.iss.itreasury.settlement.reportlossorfreeze.dao.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * ҳ�������
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

try {
		/** Ȩ�޼�� **/
		/* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
            out.flush();
            return;
        }
	   String strTableTitle = "ҵ���� ���� ��ʧ";
	   Log.print("=================����ҳ��reportlossorfreeze/control/c001.jsp=========");
	   //������
       //if(!SETTHTML.validateRequest(out, request,response)) return;

	/** 
	 * ��request�л��ҳ�������Ϣ 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/** 
	 * ����ҵ����� 
	 */
	
	
	 String strTemp = null;
	 long lOrder = -1;	
	 long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;

	 ReportLossOrFreezeInfo info=new ReportLossOrFreezeInfo();	
	 ReportLossOrFreezeInfo upInfo=new ReportLossOrFreezeInfo();
	 ReportLossOrFreezeInfo tpInfo=new ReportLossOrFreezeInfo();
	 ReportLossOrFreezeQueryInfo qInfo =new ReportLossOrFreezeQueryInfo();
	/**
	* �����ҳ����
	*/
	info.convertRequestToDataEntity(request);
	
	info.setOfficeId(sessionMng.m_lOfficeID);
	info.setCurrencyId(sessionMng.m_lCurrencyID);
	String strTmp="";
	strTmp = (String)request.getAttribute("subAccountIdCtrl");
	if( strTmp != null && strTmp.length() > 0 )
	{
		info.setOldDepositNo(strTmp.trim());
	}
	
	ReportLossOrFreezeBean delegation = new ReportLossOrFreezeBean();
	long ltemp = -1;
	/**
	 * ���ݲ���������в���
	 */
	if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.CREATESAVE)))	//��������
	{			
		info.setStatus(SETTConstant.TransactionStatus.SAVE);
		info.setSubAccountOldStatus(SETTConstant.SubAccountStatus.NORMAL);
		info.setSubAccountNewStatus(SETTConstant.SubAccountStatus.REPORTLOSS);
		info.setTransActionType(SETTConstant.TransactionType.REPORTLOSS);
		info.setModifyDate(Env.getSystemDateTime()) ;
		ltemp =delegation.save(info);
		upInfo = delegation.findByID(ltemp);
		upInfo.setCheckUserId(sessionMng.m_lUserID);
		upInfo.setCheckDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
		ltemp = delegation.check(upInfo);
		
		
		if(ltemp>0)
		{
			request.setAttribute("reportLossOrFreezeInfo",info);
			/**
			* �����ɹ�
			*/
			request.setAttribute("SucInfo","����ɹ���");
			pageInfo.success();
		}
		else 
		{
			request.setAttribute("SucInfo","��ʧҵ����������ʧ��,�����ԣ�");
			pageInfo.fail();
		}
	}
	/**
	 * ���ݲ���������в���
	 */
	else if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.LINKSEARCH)))	//���Ӳ���
	{			
		
		Collection c = null;
		strTemp = (String)request.getAttribute("Order");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lOrder = Long.parseLong(strTemp.trim());
		}
		strTemp = (String)request.getAttribute("Desc");
		if( strTemp != null && strTemp.length() > 0 )
		{
			lDesc = Long.parseLong(strTemp.trim());
		}
		qInfo.setDesc(lDesc);
		qInfo.setOrderField(lOrder);
		qInfo.setCheckDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
		qInfo.setCheckUserId(sessionMng.m_lUserID);
		qInfo.setStatus(SETTConstant.TransactionStatus.CHECK);
		qInfo.setTransActionType(SETTConstant.TransactionType.REPORTLOSS);
		c = delegation.findByConditions(qInfo);
		request.setAttribute("SearchResult",c);
		request.setAttribute("Order",String.valueOf(lOrder));
		request.setAttribute("Desc",String.valueOf(lDesc));
		/**
		* �����ɹ�
		*/	
		pageInfo.success();	
	}
	/**
	 * ���ݲ���������в���
	 */
	else if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.TODETAIL)))	//�㽻�׺ŵ���ϸҳ��
	{	
		long lId = -1;
		strTmp = (String)request.getAttribute("hdnId");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lId = Long.parseLong(strTmp.trim());
		}
		ReportLossOrFreezeInfo rInfo = new ReportLossOrFreezeInfo();
		rInfo = delegation.findByID(lId);
		request.setAttribute("ResultInfo",rInfo);
		/**
		* �����ɹ�
		*/	
		pageInfo.success();	
	}
	else if (pageInfo.getStrAction().equals(String.valueOf(SETTConstant.Actions.DELETE)))	// ɾ��
	{	
		long lId = -1;
		String strModifyDate = "";
		strTmp = (String)request.getAttribute("hdnId");
		if( strTmp != null && strTmp.length() > 0 )
		{
			lId = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("hdnModifyDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			strModifyDate = strTmp;
		}
		
		ReportLossOrFreezeInfo rInfo = new ReportLossOrFreezeInfo();
		ReportLossOrFreezeInfo cInfo = new ReportLossOrFreezeInfo();
		rInfo = delegation.findByID(lId);
		rInfo.setModifyDate(new Timestamp(Long.parseLong(strModifyDate)));
		ltemp = delegation.cancelCheck(rInfo);
		cInfo = delegation.findByID(lId);
		ltemp = delegation.delete(cInfo);
		

		if(ltemp>0)
		{
			
			/**
			* �����ɹ�
			*/
			request.setAttribute("SucInfo","ɾ���ɹ���");
			pageInfo.success();
		}
		else 
		{
			request.setAttribute("SucInfo","��ʧҵ��ɾ������ʧ��,�����ԣ�");
			pageInfo.fail();
		}
		/**
		* �����ɹ�
		*/	
		pageInfo.success();	
	}


	
} catch ( Exception exp ) {
	exp.printStackTrace();
	/**
	 * �����쳣,��ӱ�����Ϣ
	 */
	//sessionMng.getActionMessages().addMessage(exp);
	/**
	 * �����쳣,���������Ϊʧ��
	 */
	pageInfo.fail();
}
	/**
	 * �������������request��
	 */
	request.setAttribute("strActionResult",pageInfo.getStrActionResult());
	/** 
	 * ת����һҳ�� 
	 **/
	
	Log.print("Next Page URL:"+pageInfo.getStrNextPageURL());	
	
	
	
	
	
	//����ҳ��ַ�ʱ��Ҫ�õ���ʵ��
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(pageInfo.getStrNextPageURL());
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	rd.forward( request,response );
%>
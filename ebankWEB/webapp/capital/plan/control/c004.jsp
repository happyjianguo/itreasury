<%--
 ҳ������ ��c004.jsp
 ҳ�湦�� : �ʽ�ƻ�ά��
 ��    �� ��jiamiao
 ��    �� ��2006-3-24
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->

<%@ page import="java.util.*"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.bizlogic.OBCapitalPlanBiz"%>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.dataentity.OBCapitalPlanInfo"%>
<jsp:directive.page import="com.iss.itreasury.util.PageController"/>
<jsp:directive.page import="com.iss.itreasury.util.PageControllerInfo"/>
%>
<!-- ������û���ǰ�ػ���Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
/**
 * ҳ�������
 */
PageCtrlInfo pageInfo = new PageCtrlInfo();

/** ����ҵ��ʵ���� */
OBCapitalPlanInfo info = null;

try {
	 /** Ȩ�޼�� **/
    Log.print("=================����ҳ��/capital/plan/control/c004.jsp=========");
    String strTitle = "�ʽ�ƻ�ά��"; 
    /*
     * У��ͻ����������Ч��
     */
    OBHtml.validateRequest(out,request,response);

	/** 
	 * ��request�л��ҳ�������Ϣ 
	 */
	pageInfo.convertRequestToDataEntity(request);

	/**
	* ��ʼ��ҵ��ʵ���� 
	*/
	info = new OBCapitalPlanInfo();
	info.setClientID(sessionMng.m_lClientID);

	/**
	* �����ҳ����
	*/
	info.convertRequestToDataEntity(request);
	
	/**
	* ʵ�����߼���
	*/
	OBCapitalPlanBiz biz = new OBCapitalPlanBiz();

	/**
	* ���÷���
	*/
	long retlong = 0;
	if(info.getId()>0)
	{
		retlong = biz.deleteById(info.getId());
	}
	if(retlong > 0)
	{
		sessionMng.getActionMessages().addMessage("ɾ���ɹ�!");
	}else
	{
		pageInfo.fail();
		sessionMng.getActionMessages().addMessage("ɾ��ʧ��!");
	}

	pageInfo.setStrNextPageURL("../control/c001.jsp");
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
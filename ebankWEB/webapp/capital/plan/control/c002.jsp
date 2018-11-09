<%--
 ҳ������ ��c002.jsp
 ҳ�湦�� : �ʽ�ƻ�ά��
 ��    �� ��jiamiao
 ��    �� ��2005-7-14
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
           
--%>
<%@ page contentType="text/html;charset=gbk" %>
<!-- ������Ҫ����,��������.* -->

<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.util.PageCtrlInfo"%>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.dao.OBCapitalPlanDao"%>
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
    Log.print("=================����ҳ��/capital/plan/control/c002.jsp=========");
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

	/**
	* �����ҳ����
	*/
	String _periodid = request.getParameter("periodid");//����ID
	String value = request.getParameter("value");
	info.convertRequestToDataEntity(request);
	info.setClientID(sessionMng.m_lClientID);
	info.setInputUserID(sessionMng.m_lUserID);
	info.setOfficeID(sessionMng.m_lOfficeID);
	info.setCurrencyID(sessionMng.m_lCurrencyID);
	String PayAmount = request.getParameter("PayAmount");
	if(!PayAmount.equals("") && PayAmount != null)
		info.setPayAmount(DataFormat.parseNumber(PayAmount));
	String ReceivAmount = request.getParameter("ReceivAmount");
	if(!ReceivAmount.equals("") && ReceivAmount != null)
		info.setReceivAmount(DataFormat.parseNumber(ReceivAmount));
	if(info.getId() > -1)
	{
		info.setModifyUserID(sessionMng.m_lUserID);
	}
	info.setPeriod(new Long(_periodid).longValue());
	System.out.println(info);

	/**
	* ʵ�����߼���
	*/
	OBCapitalPlanBiz biz = new OBCapitalPlanBiz();
	Timestamp thisDate = info.getStartDate();
	Timestamp nextDate = info.getEndDate();
	if(biz.isDate(info.getId(),thisDate,nextDate,sessionMng.m_lClientID) == false)
	{
		System.out.println("�������Ѵ��������ص�!");
		sessionMng.getActionMessages().addMessage("�������Ѵ��������ص�!");
		if(value.equals("add"))
			pageInfo.setStrNextPageURL("../view/v002.jsp?value=add");
		else if(value.equals("update"))
			pageInfo.setStrNextPageURL("../control/c003.jsp?id=" + info.getId());
			
	}else{
		/**
		* ���÷���
		*/
		long retlong = biz.save(info);
		if(retlong > 0)
		{
			sessionMng.getActionMessages().addMessage("����ɹ�!");
		}else
		{
			sessionMng.getActionMessages().addMessage("����ʧ��!");
		}
	
		pageInfo.setStrNextPageURL("../control/c001.jsp");
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
<%--
 ҳ������ ��c003.jsp
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
    Log.print("=================����ҳ��/capital/plan/control/c003.jsp=========");
	String type = request.getParameter("type");
    String strTitle = ""; 
	if(type == null)
		strTitle = "�ʽ�ƻ�ά��";
	else if(type.equals("1"))
		strTitle = "�ʽ�ƻ�����";
    long id = new Long(request.getParameter("id")).longValue();
    /*
     * У��ͻ����������Ч��
     */
    OBHtml.validateRequest(out,request,response);

	/**
	* ��ʼ��ҵ��ʵ���� 
	*/
	info = new OBCapitalPlanInfo();
	info.setClientID(sessionMng.m_lClientID);

	/**
	* ʵ�����߼���
	*/
	OBCapitalPlanBiz biz = new OBCapitalPlanBiz();

	/**
	* ���÷���
	*/
	info = biz.findById(id,sessionMng.m_lClientID);
	
	System.out.println("������c003"+info);
	
	request.setAttribute("info",info);

	if(type == null)
		pageInfo.setStrNextPageURL("../view/v002.jsp?value=update");
	else if(type.equals("1"))
		pageInfo.setStrNextPageURL("../view/v002.jsp?type=1&userid="+info.getInputUserID()+"&CheckuserID="+info.getCheckUserID());
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
<%--
 ҳ������ �� exp_show.jsp
 ҳ�湦�� :  ����������ʾҳ��
 ��    �� �� niweinan
 ��    �� �� 2011-2-24
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.ebank.obbatchpayment.bizlogic.OBBatchPayment" %>


<%
	OBBatchPayment obBatchPayment = new OBBatchPayment();
	String strJOSN = "";
	String fileName = new String(request.getParameter("fileName").getBytes("GBK"));

	String path = Env.UPLOAD_PATH + "ebank/batchpay/"+fileName;
	
	strJOSN=obBatchPayment.getJSONString(path);

	response.getWriter().print(strJOSN);

%>
    
<%--
 页面名称 ： exp_show.jsp
 页面功能 :  批量导入显示页面
 作    者 ： niweinan
 日    期 ： 2011-2-24
 特殊说明 ：
 实现操作说明：
 修改历史 ：
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
    
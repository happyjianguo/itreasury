<%--
 页面名称 ： batchpay_c.jsp
 页面功能 :  批量导入控制页面
 作    者 ： niweinan
 日    期 ： 2011-2-25
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obbatchpayment.bizlogic.OBBatchPayment" %>
<%@ page import="com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.jspsmart.upload.File" %>

<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<%@ page import="java.sql.Timestamp" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%

	/* 标题固定变量 */
	String strTitle = "批量支付";


	
try{
	//用户登录检测 
    if (sessionMng.isLogin() == false)
    {
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
    	out.flush();
    	return;
    }

    // 判断用户是否有权限 
    if (sessionMng.hasRight(request) == false)
    {
        out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
    	out.flush();
    	return;
    }

	OBBatchPayment obBatchPayment = new OBBatchPayment();

    //获得页面参数
    double dAmount = 0.0;			//总金额
    long lCount = 0;				//总笔数
    String strTime = "";
    String fileName = "";
    String strTemp = null;
  
    strTemp = (String)request.getParameter("dAmount");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		dAmount = DataFormat.parseNumber(strTemp);
	}
	strTemp = (String)request.getParameter("lCount");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		lCount = Long.valueOf(strTemp).longValue();
	}
	
	strTime = String.valueOf(System.currentTimeMillis());
	
    //上传文件
	mySmartUpload.initialize(pageContext);
	mySmartUpload.upload();
	
	File myFile = mySmartUpload.getFiles().getFile(0);
	fileName = strTime + myFile.getFileName().substring(myFile.getFileName().lastIndexOf("."));
	java.io.File file = new java.io.File(Env.UPLOAD_PATH + "ebank/batchpay/");
	if(!file.exists())
	{
		file.mkdirs();
	}
	if (!myFile.isMissing())
	{
		myFile.saveAs(Env.UPLOAD_PATH + "ebank/batchpay/"+fileName);
	}
	
	
	
	UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
	//网银最晚提交时间效验
	long lOfficeID = sessionMng.m_lOfficeID;
	long lCurrencyID = sessionMng.m_lCurrencyID;
	Timestamp timeExecute = Env.getSystemDate(lOfficeID,lCurrencyID);
	OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
	commitTime.validateOBCommitTime(timeExecute,lOfficeID,lCurrencyID);
	upLoanReturnInfo = obBatchPayment.validateUploadFile(fileName,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,dAmount,lCount);

	
	/* 在请求中保存结果对象 */
    request.setAttribute("upLoanReturnInfo",upLoanReturnInfo);
	
    
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	//模板数据错误，跳回导入页面
	if(upLoanReturnInfo==null)
	{
		pageControllerInfo.setUrl(strContext + "exp_show.jsp?fileName="+fileName);
	}
	else{
		pageControllerInfo.setUrl(strContext + "batchpay_v.jsp");
	}

	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

    
	/* forward到结果页面 */
    rd.forward(request, response);

	
}
catch(Exception exp)
{
	sessionMng.getActionMessages().addMessage(exp.getMessage());
	exp.printStackTrace();
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "batchpay_v.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
    
	/* forward到结果页面 */
    rd.forward(request, response);
}
%>
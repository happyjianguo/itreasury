<%--
 ҳ������ �� batchpay_c.jsp
 ҳ�湦�� :  �����������ҳ��
 ��    �� �� niweinan
 ��    �� �� 2011-2-25
 ����˵�� ��
 ʵ�ֲ���˵����
 �޸���ʷ ��
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

	/* ����̶����� */
	String strTitle = "����֧��";


	
try{
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

	OBBatchPayment obBatchPayment = new OBBatchPayment();

    //���ҳ�����
    double dAmount = 0.0;			//�ܽ��
    long lCount = 0;				//�ܱ���
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
	
    //�ϴ��ļ�
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
	//���������ύʱ��Ч��
	long lOfficeID = sessionMng.m_lOfficeID;
	long lCurrencyID = sessionMng.m_lCurrencyID;
	Timestamp timeExecute = Env.getSystemDate(lOfficeID,lCurrencyID);
	OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
	commitTime.validateOBCommitTime(timeExecute,lOfficeID,lCurrencyID);
	upLoanReturnInfo = obBatchPayment.validateUploadFile(fileName,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,dAmount,lCount);

	
	/* �������б��������� */
    request.setAttribute("upLoanReturnInfo",upLoanReturnInfo);
	
    
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	//ģ�����ݴ������ص���ҳ��
	if(upLoanReturnInfo==null)
	{
		pageControllerInfo.setUrl(strContext + "exp_show.jsp?fileName="+fileName);
	}
	else{
		pageControllerInfo.setUrl(strContext + "batchpay_v.jsp");
	}

	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));

    
	/* forward�����ҳ�� */
    rd.forward(request, response);

	
}
catch(Exception exp)
{
	sessionMng.getActionMessages().addMessage(exp.getMessage());
	exp.printStackTrace();
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "batchpay_v.jsp");
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
    
	/* forward�����ҳ�� */
    rd.forward(request, response);
}
%>
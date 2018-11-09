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
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.ebank.obbatchpayment.bizlogic.OBBatchPayment" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo" %>
<%@ page import="com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.safety.util.*" %>
<%@ page import="com.iss.itreasury.safety.info.*" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%

	String strSuccessURL = "";
	String strFailureURL = "";
	String strNextURL = "";
	String remoteAddr = request.getRemoteAddr();
	String remoteHost = request.getRemoteHost();
	boolean isSuccess = false;
	try{
		/* ����̶����� */
		String strTitle = "����֧��";
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
		
		OBBatchPayment obBatchPayment =new OBBatchPayment();
		String temp = "";
		String strJSON = "";
		
		
		temp = (String)request.getAttribute("strSuccessURL");
		if(temp!=null&&temp.trim().length()>0)
		{
			strSuccessURL = temp;
		}
		
		temp = (String)request.getAttribute("strFailureURL");
		if(temp!=null&&temp.trim().length()>0)
		{
			strFailureURL = temp;
		}
		
//		temp = new String(request.getParameter("jsonString").getBytes("ISO8859_1"));
		temp = (String)request.getAttribute("jsonString");
		if(temp!=null&&temp.trim().length()>0)
		{
			strJSON=temp;
		}

		//��ʼ��EJB
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		FinanceInfo[] financeInfoArray = null;
		FinanceInfo financeInfo = new FinanceInfo();
		int first = strJSON.indexOf("[");
		int last = strJSON.lastIndexOf("]");
		String sBatchNo = "";  //���κ�

		strJSON = strJSON.substring(first,last+1);
		sBatchNo=obBatchPayment.getBatchNo(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		financeInfoArray=obBatchPayment.translateJSONString(strJSON,sBatchNo,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lClientID);
	
		for(int i=0;i<financeInfoArray.length;i++)
		{
			financeInfo = financeInfoArray[i];
			
			/* ����ǩ�����⴦��(ʹ�÷�����֤��˽Կ) begin*/					
		  	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		  	//ʹ�õ����������Ű�ȫ��֤,�����ǩ������
			if(strTroyName.equals(Constant.GlobalTroyName.ITrus))
			{
				String[] nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
				String[] valueArray =  new String[nameArray.length];
				valueArray[0] = String.valueOf(financeInfo.getPayerAcctID());
				valueArray[1] = String.valueOf(DataFormat.format(financeInfo.getAmount(), 2));
				valueArray[2] = String.valueOf(sessionMng.m_lUserID);
				valueArray[3] = "-1";
				
				SignatureInfo signatureInfo = new SignatureInfo();			
				signatureInfo.setNameArray(nameArray);
				signatureInfo.setValueArray(valueArray);
				String strSignatureOriginalValue = SignatureUtil.formatData(signatureInfo);
				//System.out.println("33333333"+strSignatureOriginalValue);
				String strSignatureValue = OBSignatureUtil.doSignatureUseKeyStore(strSignatureOriginalValue);
				//����ʵ��,���������ݿ�
				financeInfo.setSignatureValue(strSignatureValue);	
				financeInfo.setSignatureOriginalValue(strSignatureOriginalValue);
				
				
				
			}
			/* ����ǩ�����⴦��(ʹ�÷�����֤��˽Կ) end*/	
		}
		isSuccess = financeInstr.addBatchCapitalTrans(financeInfoArray,sessionMng,remoteAddr,remoteHost);
		if(isSuccess)
		{
			sessionMng.getActionMessages().addMessage("����ɹ���");
		}
		strNextURL = strSuccessURL;
		
		
		
	}
	catch(Exception e)
	{
		strNextURL = strFailureURL;
		e.printStackTrace();
		sessionMng.getActionMessages().addMessage("����ʧ�ܣ�");
	}
			
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//�ַ�
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	/* forward�����ҳ�� */
    rd.forward(request, response);
	
%>
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
		/* 标题固定变量 */
		String strTitle = "批量支付";
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

		//初始化EJB
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		FinanceInfo[] financeInfoArray = null;
		FinanceInfo financeInfo = new FinanceInfo();
		int first = strJSON.indexOf("[");
		int last = strJSON.lastIndexOf("]");
		String sBatchNo = "";  //批次号

		strJSON = strJSON.substring(first,last+1);
		sBatchNo=obBatchPayment.getBatchNo(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		financeInfoArray=obBatchPayment.translateJSONString(strJSON,sBatchNo,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lClientID);
	
		for(int i=0;i<financeInfoArray.length;i++)
		{
			financeInfo = financeInfoArray[i];
			
			/* 数字签名特殊处理(使用服务器证书私钥) begin*/					
		  	String strTroyName = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
		  	//使用的是天威诚信安全认证,则进行签名处理
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
				//存入实体,保存入数据库
				financeInfo.setSignatureValue(strSignatureValue);	
				financeInfo.setSignatureOriginalValue(strSignatureOriginalValue);
				
				
				
			}
			/* 数字签名特殊处理(使用服务器证书私钥) end*/	
		}
		isSuccess = financeInstr.addBatchCapitalTrans(financeInfoArray,sessionMng,remoteAddr,remoteHost);
		if(isSuccess)
		{
			sessionMng.getActionMessages().addMessage("导入成功！");
		}
		strNextURL = strSuccessURL;
		
		
		
	}
	catch(Exception e)
	{
		strNextURL = strFailureURL;
		e.printStackTrace();
		sessionMng.getActionMessages().addMessage("导入失败！");
	}
			
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
	
	/* forward到结果页面 */
    rd.forward(request, response);
	
%>
<%--
/*
 * 程序名称：showDepositVoucherPrintPage.jsp
 * 功能说明：存款支取打印页面
 * 作　　者：刘琰
 * 完成日期：2004年02月29日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.*,
				 java.sql.*,
				 java.lang.*,
				 com.iss.itreasury.ebank.obaccountinfo.dao.*,
				 com.iss.itreasury.ebank.obprint.dao.*,
				 com.iss.itreasury.ebank.obprint.dataentity.*,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"
%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %> 
<% String strContext = request.getContextPath();%>
<!--Header end-->

<%
	String strTitle = "[存款支取凭证打印]";
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
%>
<%
		
		/* 初始化dao */
		OBPrintDao obPrintDao = new OBPrintDao();	
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();	
		
		//初始化信息类
		FinanceInfo objFinanceInfo = null;
		OBPrintInfo obPrintInfo = new OBPrintInfo();
		
		//定义变量
		String sTransNo = "";
		long lOBTransType = -1;
		long lSettTransType = -1;
		long lSettID = -1;
		long lReturn = -1;
		
		if ( request.getParameter("lTransType")!=null )
        {
    	   	lOBTransType = Long.valueOf(request.getParameter("lTransType")).longValue();
			Log.print("lOBTransType="+lOBTransType);
    	}
		if ( request.getParameter("sTransNo")!=null )
    	{
    		sTransNo = String.valueOf(request.getParameter("sTransNo"));
			Log.print("sTransNo="+sTransNo);
		}
%>

<%
		/*查询打印信息*/
		obPrintInfo = obPrintDao.getSettInfoByTransNo(sTransNo,lOBTransType);
		lSettTransType = obPrintInfo.getSettTransActionTypeID();
		Log.print("lSettTransType="+lSettTransType);
		lSettID = obPrintInfo.getSettID();
		
		lReturn = obdao.CheckAccountID(sTransNo, "", sessionMng.m_lClientID, sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		
		/* 获取上下文环境*/
	    //ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    RequestDispatcher rd = null;
	    
	    
	    	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
		
		if (lOBTransType==OBConstant.SettInstrType.CHILDCAPTRANSFER)
		{
			lOBTransType = OBConstant.SettInstrType.CAPTRANSFER_BANKPAY;
		}
		if (lSettTransType == SETTConstant.TransactionType.SUBCLIENT_BANKPAY)
		{
			lSettTransType = SETTConstant.TransactionType.BANKPAY;
		}
		
		switch ((int)lOBTransType)
		{
			case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF:
			case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY:
			case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT:
				
				
				//构建页面分发时需要用到的实体

	pageControllerInfo.setUrl( "/accountinfo/a401-v.jsp?lID=" + lSettID + "&lTransactionTypeID=" + lSettTransType +"&lReturn=" + lReturn );
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
				
				break;
				
			case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT:
			case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT:
				
				
				
				pageControllerInfo.setUrl( "/accountinfo/a402-v.jsp?lID=" + lSettID + "&lTransactionTypeID=" + lSettTransType +"&lReturn=" + lReturn );
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
				break;
				
			case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER:
			case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW:
				
				
	pageControllerInfo.setUrl( "/accountinfo/a403-v.jsp?lID=" + lSettID + "&lTransactionTypeID=" + lSettTransType +"&lReturn=" + lReturn );
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
				
				
				break;
				
			case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE:
			case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE:
				
				
				
				
		pageControllerInfo.setUrl( "/accountinfo/a919-v.jsp?lID=" + lSettID + "&lTransactionTypeID=" + lSettTransType +"&lReturn=" + lReturn );
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));			
				
				break;

			case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT:
				
				
				
		pageControllerInfo.setUrl("/accountinfo/a903-v.jsp?lID=" + lSettID + "&lTransactionTypeID=" + lSettTransType +"&lReturn=" + lReturn);
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo))	;			
				
				break;
			case (int)OBConstant.SettInstrType.CAPTRANSFER_OTHER:
				System.out.println(lSettID+":"+lSettTransType+":"+lReturn);

			pageControllerInfo.setUrl("/accountinfo/a409-v.jsp?lID=" + lSettID + "&lTransactionTypeID=" + lSettTransType +"&lReturn=" + lReturn);
	//分发
	rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo))	;		
				
				
				break;
				
			default:
				OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");																						
	    }
		/* forward到结果页面 */
	    rd.forward(request, response);
%>
		
<%
	}
	catch (Exception e)
	{
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
	}
%>
    	

<%--
/*
 * 程序名称：C414.jsp
 * 功能说明：交易指令详细信息察看控制页面
 * 作　　者：刘琰
 * 完成日期：2003年09月25日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<%String strContext = request.getContextPath();%>
<%
	String strTitle = "[业务复核]";
	/* 用户登录检测与权限校验 */
	try 
	{
        /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
        	out.flush();
        	return;
        }

        //判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng,strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
		}
	 } 
	 catch (Exception e) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
    }
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = -1;
	String strMenu = (String)request.getParameter("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	


%>
<%
	String strIsCheck = "";
	int iTransType = 0;
	long lID = 0;
	
	strIsCheck = GetParam(request,"txtisCheck");
	iTransType = GetIntParam(request,"txtTransType");
	lID = GetNumParam(request,"txtID");
		
	Log.print("----------lID:   "+lID);
	Log.print("----------iTransType="+iTransType);
	String strFrom = null; //forward页面
	
	switch (iTransType)
	{
		case (int)OBConstant.SettInstrType.CAPTRANSFER_SELF:
		case (int)OBConstant.SettInstrType.CAPTRANSFER_BANKPAY:
		case (int)OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER:
		case (int)OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT://1;资金划拨 
			strFrom = "/capital/captransfer/c004-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.OPENFIXDEPOSIT:
			strFrom = "/capital/fixed/f004-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER:			
			strFrom = "/capital/fixed/f014-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.OPENNOTIFYACCOUNT:
			strFrom = "/capital/notify/n004-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.NOTIFYDEPOSITDRAW:
			strFrom = "/capital/notify/n014-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.TRUSTLOANRECEIVE:
			strFrom = "/capital/loanrepayment/l006-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.CONSIGNLOANRECEIVE:
			strFrom = "/capital/loanrepayment/l016-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.INTERESTFEEPAYMENT:
			strFrom = "/capital/loanrepayment/l026-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.YTLOANRECEIVE:
			strFrom = "/capital/loanrepayment/l006-v.jsp?src=1&isYT=1";
			break;
		case (int)OBConstant.SettInstrType.DRIVEFIXDEPOSIT:
			strFrom = "/capital/fixed/f008-v.jsp?src=1";
			break;
		case (int)OBConstant.SettInstrType.CHANGEFIXDEPOSIT:
			strFrom = "/capital/fixed/f011-v.jsp?src=1";
			break;
		default :
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
			return;
	}

	try
	{
		/* 初始化信息查询类 */
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		FinanceInfo info = new FinanceInfo();
           
		/* 调用类方法查询 */
		info = obFinanceInstrDao.findByID(lID,sessionMng.m_lUserID,sessionMng.m_lCurrencyID);//如果没有这个lID流水号则info ＝null
		if (info == null || strFrom == null)//为空显示出错页面
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E001");
			return;
		}
		
		//在请求中保存结果对象
		request.setAttribute("lID",String.valueOf(lID));
		request.setAttribute("financeInfo",info);
		request.setAttribute("resultInfo",info);
		//获取上下文环境
		//ServletContext sc = getServletContext();
		//设置返回地址
		
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + strFrom);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
		//forward到结果页面
		rd.forward(request, response);
	}
	catch(IException ie)
	{
		//出错页面
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	}

%>


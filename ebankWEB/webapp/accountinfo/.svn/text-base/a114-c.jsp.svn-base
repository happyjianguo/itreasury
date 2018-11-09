<%--
/**
 程序名称 ：a114-c.jsp
 功能说明 : 历史明细查询控制页面
 作    者 ：kewen hu
 日    期 ：2004-02-25
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.query.queryobj.*,
				 com.iss.itreasury.settlement.query.paraminfo.*,
				 com.iss.itreasury.settlement.query.resultinfo.*,
				 java.rmi.RemoteException,
				 java.sql.*,
				 java.util.*"
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
    //标题变量
	String strTitle = "[历史明细]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        //定义变量
		long lCurrencyID = -1;
		String sAccountNo = "";//账号
		long lAccountID = -1;//账户ID
		long lAccountTypeID = -1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID
		Timestamp tsStartDate = null;//开始日期
		Timestamp tsEndDate = null;//结束日期
		double dBalance = 0.0;//期初余额
		long lOrder = -1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;

		String sTemp = null;	// 临时量
		sTemp = (String) request.getAttribute("lCurrencyID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lCurrencyID = Long.parseLong(sTemp);
		}
		Log.print("========lCurrencyID="+lCurrencyID);
		sTemp = (String) request.getAttribute("lAccountID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lAccountID = Long.parseLong(sTemp);
		}
		Log.print("========lAccountID="+lAccountID);
		sTemp = (String) request.getAttribute("sAccountNo");
		if (sTemp != null && sTemp.trim().length() > 0) {
			sAccountNo = sTemp;
		}
		Log.print("========sAccountNo="+sAccountNo);
		sTemp = (String) request.getAttribute("tsStart");
		if (sTemp != null && sTemp.trim().length() > 0) {
			tsStartDate = DataFormat.getDateTime(sTemp);
		}
		Log.print("========tsStartDate="+tsStartDate);
		sTemp = (String) request.getAttribute("tsEnd");
		if (sTemp != null && sTemp.trim().length() > 0) {
			tsEndDate = DataFormat.getDateTime(sTemp);
		}
		Log.print("========tsEndDate="+tsEndDate);
		sTemp = (String) request.getAttribute("lAccountGroupID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lAccountGroupID = Long.parseLong(sTemp);
		}
		Log.print("========lAccountGroupID="+lAccountGroupID);
		sTemp = (String) request.getAttribute("lAccountTypeID");
		if (sTemp != null && sTemp.trim().length() > 0) {
			lAccountTypeID = Long.parseLong(sTemp);
		}
		Log.print("========lAccountTypeID="+lAccountTypeID);

		//初始化并设置查询条件类
        QueryTransAccountDetailWhereInfo qtai = new QueryTransAccountDetailWhereInfo();
		QTransAccountDetail qobj = new QTransAccountDetail();

        qtai.setOfficeID(sessionMng.m_lOfficeID);
		qtai.setCurrencyID(lCurrencyID);
		qtai.setClientCode(sessionMng.m_strClientCode);//客户号
		qtai.setAccountNo(sAccountNo);//账号
		qtai.setAccountID(lAccountID);//账户ID
		qtai.setAccountTypeID(lAccountTypeID);//账户类型ID
		qtai.setStartDate(tsStartDate);
		qtai.setEndDate(tsEndDate);
		qtai.setOrderField(1);
        //根据请求操作，完成业务处理的调用
		Collection coll = null;
        coll = qobj.queryTransAccountDetail(qtai);
		dBalance = qobj.queryTransAccountBalance(qtai);//账户的期初余额

		/* 在请求中保存结果对象 */
		request.setAttribute("whereInfo",qtai);
		request.setAttribute("coll_resultInfo",coll);
		request.setAttribute("balance",String.valueOf(dBalance));//账户的期初余额
Log.print("===========whereInfo="+qtai);
Log.print("===========coll_resultInfo="+coll);
Log.print("===========balance="+String.valueOf(dBalance));
		/* 获取上下文环境 */
	   // ServletContext sc = getServletContext();
	    /* 设置返回地址 */
	    //构建页面分发时需要用到的实体
	    String strNextURL = "/accountinfo/a113-v.jsp";
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
	    /* forward到结果页面 */
	    rd.forward(request, response);
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>
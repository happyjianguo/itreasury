<%--
/**
 页面名称 ：a307-c.jsp
 页面功能 : 特殊业务复核页面的控制类页面
 作    者 ：kewen hu
 日    期 ：2004-02-25
 特殊说明 ：实现操作说明：
				1、复核处理
				2、取消复核处理
 修改历史 ：
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>	
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.transspecial.dataentity.SpecialOperationInfo"%>
<%@ page import="com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>
	
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%

	//页面控制变量
	String strNextPageURL = "";
	String strSuccessPageURL = "";
	String strFailPageURL = "";
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

    //标题变量
    String strTitle = "[账户历史明细]";
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
		long lNcheckuserid = sessionMng.m_lUserID;
		long lId = -1;
		String strTransNo = "";
		String strScanclecheckabstract = "";
		Timestamp tsModify = null;

		//读取数据
		strAction = (String) request.getAttribute("strAction");
		strSuccessPageURL = (String) request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String) request.getAttribute("strFailPageURL");

		String strTemp = null;
		strTemp = (String) request.getParameter("lId");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lId = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getParameter("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0 && strTemp.trim().toLowerCase().compareTo("null") != 0)
		{
			strTransNo = strTemp;
		}
		strTemp = (String) request.getParameter("strScanclecheckabstract");
		if (strTemp != null && strTemp.trim().length() > 0 && strTemp.trim().toLowerCase().compareTo("null") != 0)
		{
			strScanclecheckabstract = strTemp;
		}
		strTemp = (String)request.getAttribute("tsDtmodify");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsModify = DataFormat.getDateTime(strTemp);
		}

		TransSpecialOperationInfo tsoi = null;

		//TransSpecialOperationDelegation tsod = new TransSpecialOperationDelegation();
		Sett_TransSpecialOperationDAO dao = new Sett_TransSpecialOperationDAO();
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();

		long lReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, 
			sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lReturn));
		Log.print("=================lReturn="+lReturn);

		if (strTransNo != null && !strTransNo.trim().equals(""))
		{
			//lId = tsod.getIDByTransNo(strTransNo);
			lId = dao.getIDByTransNo(strTransNo);
		}
		if (lId > 0)
		{
			//tsoi = tsod.findDetailByID(lId);
			tsoi = dao.findByID(lId);
			if (tsoi == null)
			{
				throw new Exception("系统忙，请稍后再试。如果仍然有问题，请通知系统管理员!");
			}
		}
		if(tsModify != null)
		{
			tsoi.setDtmodify(tsModify);
		}
		
		//将数据填写到request中，后续代码执行发生异常时跳转到view页面可以完成数据回显
		request.setAttribute("cuurentResult", tsoi);

		//TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(tsoi);        

		//根据请求操作，完成业务处理的调用
		if ("Query".equals(strAction))
		{
			//从查询连接过来
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("lId",String.valueOf(lId));
			request.setAttribute("strAction",strAction);
			request.setAttribute("lNoperationtypeid",String.valueOf(tsoi.getNoperationtypeid()));
		}
		else if ("toUndoCheck".equals(strAction))
		{
			strActionResult = Constant.ActionResult.SUCCESS;
		}
		else
		{
			Log.print("无效操作");
		}

        request.setAttribute("strActionResult",strActionResult);
        //根据处理结果设置下一步跳转的目标页面
        strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
        //转向下一页面
        Log.print("Next Page URL:"+strNextPageURL);
        //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
        rd.forward(request, response);
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }

%>
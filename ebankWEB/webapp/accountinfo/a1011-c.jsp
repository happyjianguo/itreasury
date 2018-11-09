<%--
/**
 * 页面名称 ：a1001-c.jsp
 * 页面功能 : 保证金开立控制页面
 * 作    者 ：kewen hu
 * 日    期 ：2006-05-07
 * 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.transmargindeposit.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.transmargindeposit.dao.Sett_TransOpenMarginDepositDAO"%>
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
    String strTitle = "[账户历史信息]";
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
        String strTemp = "";
        String strTransNo = ""; //交易编号
        long lID = 0;           //

        //读取数据
        strAction = (String)request.getAttribute("strAction");
        strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
        strFailPageURL = (String)request.getAttribute("strFailPageURL");

        //业务数据
        strTemp = (String)request.getAttribute("strTransNo");
        if (strTemp != null && strTemp.trim().length() > 0) {
            strTransNo = strTemp;
        }
        strTemp = (String)request.getAttribute("lID");
        if (strTemp != null && strTemp.trim().length() > 0) {
            lID = Long.parseLong(strTemp);
        }

        Sett_TransOpenMarginDepositDAO dao = new Sett_TransOpenMarginDepositDAO();
		if(strTransNo==null || strTransNo.equals(""))
		{
			if (lID > 0)
			{
				TransMarginOpenInfo openInfo = null;
				openInfo = dao.findByID(lID);	
				if(openInfo != null)
            	{
       	        	strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("strAction","FixedQuery");
					request.setAttribute("searchResults",openInfo);
					request.setAttribute("strTransNo",openInfo.getTransNo());
       		    }
			}
			else
			{
				sessionMng.getActionMessages().addMessage("无匹配记录");
				strActionResult = Constant.ActionResult.FAIL;
			}
		}
		else
		{
			TransMarginOpenInfo openInfo = null;
			openInfo = dao.findByTransNo(strTransNo);
			if(openInfo != null)
        	{
   	        	strActionResult = Constant.ActionResult.SUCCESS;
				request.setAttribute("strAction","FixedQuery");
				request.setAttribute("searchResults",openInfo);
				request.setAttribute("strTransNo",openInfo.getTransNo());
   		    }
		}
        
        request.setAttribute("strActionResult",strActionResult);
        //根据处理结果设置下一步跳转的目标页面
        strNextPageURL = (Constant.ActionResult.SUCCESS.equals(strActionResult))?strSuccessPageURL:strFailPageURL;
        //转向下一页面
        Log.print("Next Page URL:"+strNextPageURL);
        //构建页面分发时需要用到的实体
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strNextPageURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
        rd.forward(request, response);
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
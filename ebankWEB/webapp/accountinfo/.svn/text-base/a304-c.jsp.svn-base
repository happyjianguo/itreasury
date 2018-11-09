<%--
/**
 * 页面名称 ：a303-c.jsp
 * 页面功能 : 定期开立复核匹配链结查找页面的控制类页面
 * 作    者 ：kewen hu
 * 日    期 ：2004-02-19
 * 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk"%>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransOpenFixedDepositDAO"%>
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
        long lPutAccountID = 0; //
        long lGetAccountID = 0; //
        long lTransactionTypeID = 0;    //
        long lStatusID = 0;     //

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
        strTemp = (String)request.getAttribute("lPutAccountID");
        if (strTemp != null && strTemp.trim().length() > 0) {
            lPutAccountID = Long.parseLong(strTemp);
        }
        strTemp = (String)request.getAttribute("lGetAccountID");
        if (strTemp != null && strTemp.trim().length() > 0) {
            lGetAccountID = Long.parseLong(strTemp);
        }
        strTemp = (String)request.getAttribute("lTransactionTypeID");
        if (strTemp != null && strTemp.trim().length() > 0) {
            lTransactionTypeID = Long.parseLong(strTemp);
        }
        strTemp = (String)request.getAttribute("lStatusID");
        if (strTemp != null && strTemp.trim().length() > 0) {
            lStatusID = Long.parseLong(strTemp);
        }

        TransFixedOpenInfo info = new TransFixedOpenInfo();

        info.setID(lID);
        info.setTransNo(strTransNo);
        info.setTransactionTypeID(lTransactionTypeID);
        //缺省条件
        info.setCurrencyID(sessionMng.m_lCurrencyID);
        info.setOfficeID(sessionMng.m_lOfficeID);
        info.setInputUserID(sessionMng.m_lUserID);
        info.setStatusID(lStatusID);//SETTConstant.TransactionStatus.SAVE

        //TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
		Sett_TransOpenFixedDepositDAO dao = new Sett_TransOpenFixedDepositDAO();
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();

		long lReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, 
			sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lReturn));
		Log.print("=================lReturn="+lReturn);

        //根据请求操作，完成业务处理的调用
        if(String.valueOf(SETTConstant.Actions.LINKSEARCH).equals(strAction)) {
			Log.print("=============strAction="+strAction);
            long[] lStatusIDs = null;
            QueryByStatusConditionInfo depositInfo = new QueryByStatusConditionInfo();
            depositInfo.setOfficeID(sessionMng.m_lOfficeID);
            depositInfo.setCurrencyID(sessionMng.m_lCurrencyID);
            depositInfo.setUserID(sessionMng.m_lUserID);
            depositInfo.setTransactionTypeID(lTransactionTypeID);
            depositInfo.setDate(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));

            //复核
            depositInfo.setTypeID(1);
            lStatusIDs = new long[1];
            lStatusIDs[0] = SETTConstant.TransactionStatus.CHECK;
            depositInfo.setStatus(lStatusIDs);

            Collection resultColl = null;
            //resultColl = depositDelegation.openFindByStatus(depositInfo);
			resultColl = dao.findByStatus(depositInfo);
            strActionResult = Constant.ActionResult.SUCCESS;
            request.setAttribute("searchResults",resultColl);
        } else if ("FixedQuery".equals(strAction)) {
			Log.print("=============strAction="+strAction);
            if(strTransNo==null || strTransNo.trim().length() <= 0) {
                if (lID > 0) {
                    TransFixedOpenInfo openInfo = null;
                    //openInfo = depositDelegation.openFindByID(lID);
					openInfo = dao.findByID(lID);
                    if(openInfo != null) {
                    	strActionResult = Constant.ActionResult.SUCCESS;
                        request.setAttribute("strAction","FixedQuery");
                        request.setAttribute("searchResults",openInfo);
                        request.setAttribute("strTransNo",openInfo.getTransNo());
                    }
                } else {
                    //OBHtml.showExceptionMessage(out,sessionMng, ie, "无匹配记录","",1);
                    strActionResult = Constant.ActionResult.FAIL;
                }
            } else {
                TransFixedOpenInfo openInfo = null;
                //openInfo = depositDelegation.openFindByTransNo(strTransNo);
				openInfo = dao.findByTransNo(strTransNo);
                if(openInfo != null) {
                    strActionResult = Constant.ActionResult.SUCCESS;
                    request.setAttribute("strAction","FixedQuery");
                    request.setAttribute("searchResults",openInfo);
                    request.setAttribute("strTransNo",openInfo.getTransNo());
                }
            }
        } else if (String.valueOf(SETTConstant.Actions.MATCHSEARCH).equals(strAction)) {
            Collection resultColl = null;
            //resultColl = depositDelegation.openMatch(info);
			resultColl = dao.match(info);
            if (resultColl != null && !resultColl.isEmpty()) {
                TransFixedOpenInfo resultInfo = null;
                Iterator iterator=resultColl.iterator();
                if (iterator.hasNext()) {
                    resultInfo = (TransFixedOpenInfo)iterator.next();
                }
                if (resultInfo != null) {
                    strActionResult = Constant.ActionResult.SUCCESS;
                    request.setAttribute("searchResults",resultInfo);
                    request.setAttribute("strTransNo",resultInfo.getTransNo());
                    request.setAttribute("strModifyTime",String.valueOf(resultInfo.getModifyDate().getTime()));
                }
            } else {
                //OBHtml.showExceptionMessage(out,sessionMng, ie, "无匹配记录","",1);
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
	pageControllerInfo.setUrl(strNextURL);
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL

(pageControllerInfo));
        rd.forward(request, response);
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
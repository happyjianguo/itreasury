<%--
/**
 页面名称 ：a313-c.jsp
 页面功能 : 一付多收复核匹配查找、链接查找页面的控制类页面
 作    者 ：kewen hu
 日    期 ：2004-02-24
 特殊说明 ：实现操作说明：
				1、复核匹配查找
				2、链接查找
 修改历史 ：
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>

<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransOnePayMultiReceiveInfo"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransOnePayMultiReceiveDAO"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBCheckTransDirectionDao"%>
<%@page import="com.iss.itreasury.util.PageControllerInfo"%>
<%@page import="com.iss.itreasury.util.PageController"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	//页面控制变量
    String strNextPageURL = null;
	String strSuccessPageURL = null;
	String strFailPageURL = null;
	String strAction = null;
	String strActionResult = Constant.ActionResult.FAIL;

	String strContext = request.getContextPath();

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
		long lTypeID = SETTConstant.ReceiveOrPay.RECEIVE;
		long lAccountID = -1;
		long lBankID = -1;
		long lPayGL = -1;
		double dAmount = 0.0;
		String strDeclarationNo = "";
		Timestamp tsInterestStartDate = null;
		long lStatusID = -1;
		long lSerialNo = -1;
		String strTransNo = "";
		//页面辅助变量
		String strInterestStartDate = null;

		int  nOrderByCode = -1;
		boolean isDesc = true;

		//读取数据		
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");

		String strTemp = null;
		strTemp = (String)request.getAttribute("lTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayGL");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lPayGL = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dAmount = DataFormat.parseNumber(strTemp);
		}
		strTemp = (String)request.getAttribute("strDeclarationNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strDeclarationNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strInterestStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsInterestStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lSerialNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSerialNo = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}

		strTemp = (String)request.getAttribute("nOrderByCode");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			nOrderByCode = Integer.valueOf(strTemp).intValue();
		}
		else
		{
			nOrderByCode = Sett_TransOnePayMultiReceiveDAO.ORDERBY_ID;
			request.setAttribute("nOrderByCode",String.valueOf(nOrderByCode));
		}

		strTemp = (String)request.getAttribute("isDesc");
		if(strTemp != null && strTemp.length()>0)
		{
			isDesc = Boolean.valueOf(strTemp).booleanValue();
		}

        TransOnePayMultiReceiveInfo depositInfo = new TransOnePayMultiReceiveInfo();

		depositInfo.setAmount(dAmount);
		depositInfo.setAccountID(lAccountID);
		depositInfo.setBankID(lBankID);
		depositInfo.setTypeID(lTypeID);
		depositInfo.setStatusID(lStatusID);
		depositInfo.setPayGL(lPayGL);
		depositInfo.setDeclarationNo(strDeclarationNo);
		depositInfo.setInterestStartDate(tsInterestStartDate);
		depositInfo.setSerialNo(lSerialNo);
		depositInfo.setTransNo(strTransNo);
		depositInfo.setTransactionTypeID(SETTConstant.TransactionType.ONETOMULTI);

        //TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();		
		Sett_TransOnePayMultiReceiveDAO dao = new Sett_TransOnePayMultiReceiveDAO();
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();
		long lReturn = -1;
		if (lSerialNo == -1) {
			lReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, 
				sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		} else {
			lReturn = obdao.CheckAccountID(strTransNo, String.valueOf(lSerialNo), sessionMng.m_lClientID, 
				sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		}
		request.setAttribute("lReturn",String.valueOf(lReturn));
		Log.print("=================lReturn="+lReturn);

        //根据请求操作，完成业务处理的调用
        if(String.valueOf(SETTConstant.Actions.LINKSEARCH).equals(strAction))
        {
			//链接查找成功与否都返回链接查找的视图页面
			strFailPageURL = strSuccessPageURL;

			TransOnePayMultiReceiveInfo searchInfo = new TransOnePayMultiReceiveInfo(); 
			searchInfo.setOfficeID(sessionMng.m_lOfficeID);
			searchInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			searchInfo.setCheckUserID(sessionMng.m_lUserID);
			searchInfo.setTransactionTypeID(depositInfo.getTransactionTypeID());
			searchInfo.setStatusIDs(depositInfo.getStatusIDs());

			Collection resultColl = null;
            //resultColl = depositDelegation.findByConditions(searchInfo, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(searchInfo, nOrderByCode, isDesc);
            if(resultColl != null && !resultColl.isEmpty())
            {
                strActionResult = Constant.ActionResult.SUCCESS;				
            }
			request.setAttribute("searchResults",resultColl);
        }
		else if(String.valueOf(SETTConstant.Actions.MATCHSEARCH).equals(strAction))
        {
			depositInfo.setOfficeID(sessionMng.m_lOfficeID);
			depositInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			depositInfo.setInputUserID(sessionMng.m_lUserID);

			Log.print(depositInfo);

			TransOnePayMultiReceiveInfo resultInfo = null;
			//resultInfo = depositDelegation.match(depositInfo);
			resultInfo = dao.match(depositInfo);
            if(resultInfo != null)
            {
                strActionResult = Constant.ActionResult.SUCCESS;				
            }
			else
			{
				//sessionMng.getActionMessages().addMessage("无匹配记录");
			}
			request.setAttribute("searchResults",resultInfo);
        }
		else if("Query".equals(strAction) || "ForPrint".equals(strAction))
        {
			TransOnePayMultiReceiveInfo resultInfo = null;

			TransOnePayMultiReceiveInfo searchInfo = new TransOnePayMultiReceiveInfo(); 
			searchInfo.setTransNo(depositInfo.getTransNo());
			searchInfo.setTransactionTypeID(depositInfo.getTransactionTypeID());
			searchInfo.setSerialNo(depositInfo.getSerialNo());

			Collection resultColl = null;
            //resultColl = depositDelegation.findByConditions(searchInfo, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(searchInfo, nOrderByCode, isDesc);
            if(resultColl != null && !resultColl.isEmpty() && resultColl.size() == 1)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
				
				Iterator itTemp = resultColl.iterator();
				resultInfo = (TransOnePayMultiReceiveInfo)itTemp.next();

				if(resultInfo.getTypeID() == SETTConstant.ReceiveOrPay.PAY)
				{
					strSuccessPageURL = "../accountinfo/a398-v.jsp";
				}
				else
				{
					strSuccessPageURL = "../accountinfo/a397-v.jsp";
				}
            }
			request.setAttribute("searchResults",resultInfo);
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
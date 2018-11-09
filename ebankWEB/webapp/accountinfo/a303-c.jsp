<%--
/**
 页面名称 ：a303-c.jsp
 页面功能 : 交易费用复核匹配查找、链接查找页面的控制类页面
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
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.transfee.dataentity.TransFeeInfo"%>
<%@ page import="com.iss.itreasury.settlement.transfee.dao.Sett_TransFeeDAO"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFeeDelegation"%>
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
		double dAmount = 0.0;
		long lAbstractID = -1;
		long lAccountID = -1;
		long lBillBankID = -1;
		long lBillTypeID = -1;
		long lCashFlowID = -1;
		long lCheckUserID = -1;
		long lConfirmOfficeID = -1;
		long lConfirmUserID = -1;
		long lCurrencyID = -1;
		long lFeeBankID = -1;
		long lFeeTypeID = -1;
		long lID = -1;
		long lInputUserID = -1;
		long lOfficeID = -1;
		long lRelatedAccountID = -1;
		long lRelatedClientID = -1;
		long lRelatedContractID = -1;
		long lRelatedLoanNoteID = -1;
		long lRelatedSubAccountID = -1;
		long lRelatedTransNo = -1;
		long lRelatedTransTypeID = -1;
		long lRemitInBankID = -1;
		long lSignUserID = -1;
		long lStatusID = -1;
		long lTransactionTypeID = -1;
		String strAbstract = "";
		String strBillNo = "";
		String strCancleCheckAbstract = "";
		String strCheckAbstract = "";
		String strConfirmAbstract = "";
		String strExtAcctName = "";
		String strExtAcctNo = "";
		String strPayExtBankNo = "";
		String strRelatedFixedDepositNo = "";
		String strTransNo = "";
		java.sql.Timestamp tsExecuteDate = null;
		java.sql.Timestamp tsInputDate = null;
		java.sql.Timestamp tsInterestStartDate = null;
		java.sql.Timestamp tsModifyDate = null;

		//页面辅助变量
		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

		int  nOrderByCode = -1;
		boolean isDesc = true;

		//读取数据		
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		
		String strTemp = null;
		strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dAmount = DataFormat.parseNumber(strTemp);
		}	
		strTemp = (String)request.getAttribute("lAbstractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAbstractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lBillBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBillBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lBillTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBillTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lCashFlowID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCashFlowID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lCheckUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCheckUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lConfirmOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lConfirmOfficeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lConfirmUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lConfirmUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lCurrencyID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCurrencyID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lFeeBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lFeeBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lFeeTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lFeeTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lInputUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lInputUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lOfficeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedContractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedContractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedLoanNoteID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedLoanNoteID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedSubAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedSubAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedTransNo = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRelatedTransTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRelatedTransTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lRemitInBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lRemitInBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSignUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSignUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lAbstractIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("strBillNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBillNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strCancleCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCancleCheckAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCheckAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("strConfirmAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strConfirmAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("strExtAcctName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strExtAcctName = strTemp;
		}
		strTemp = (String)request.getAttribute("strExtAcctNoCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strExtAcctNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strPayExtBankNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPayExtBankNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strRelatedFixedDepositNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strRelatedFixedDepositNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strExecuteDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsExecuteDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strInputDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsInputDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strInterestStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsInterestStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strModifyTime");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsModifyDate = new Timestamp(Long.valueOf(strTemp).longValue());
		}

		
        TransFeeInfo paramInfo = new TransFeeInfo();

		paramInfo.setAmount(dAmount);
		paramInfo.setAbstractID(lAbstractID);
		paramInfo.setAccountID(lAccountID);
		paramInfo.setBillBankID(lBillBankID);
		paramInfo.setBillTypeID(lBillTypeID);
		paramInfo.setCashFlowID(lCashFlowID);
		paramInfo.setCheckUserID(lCheckUserID);
		paramInfo.setConfirmOfficeID(lConfirmOfficeID);
		paramInfo.setConfirmUserID(lConfirmUserID);
		paramInfo.setCurrencyID(lCurrencyID);
		paramInfo.setFeeBankID(lFeeBankID);
		paramInfo.setFeeTypeID(lFeeTypeID);
		paramInfo.setID(lID);
		paramInfo.setInputUserID(lInputUserID);
		paramInfo.setOfficeID(lOfficeID);
		paramInfo.setRelatedAccountID(lRelatedAccountID);
		paramInfo.setRelatedClientID(lRelatedClientID);
		paramInfo.setRelatedContractID(lRelatedContractID);
		paramInfo.setRelatedLoanNoteID(lRelatedLoanNoteID);
		paramInfo.setRelatedSubAccountID(lRelatedSubAccountID);
		paramInfo.setRelatedTransNo(lRelatedTransNo);
		paramInfo.setRelatedTransTypeID(lRelatedTransTypeID);
		paramInfo.setRemitInBankID(lRemitInBankID);
		paramInfo.setSignUserID(lSignUserID);
		paramInfo.setStatusID(lStatusID);
		paramInfo.setTransactionTypeID(lTransactionTypeID);
		paramInfo.setAbstract(strAbstract);
		paramInfo.setBillNo(strBillNo);
		paramInfo.setCancleCheckAbstract(strCancleCheckAbstract);
		paramInfo.setCheckAbstract(strCheckAbstract);
		paramInfo.setConfirmAbstract(strConfirmAbstract);
		paramInfo.setExtAcctName(strExtAcctName);
		paramInfo.setExtAcctNo(strExtAcctNo);
		paramInfo.setPayExtBankNo(strPayExtBankNo);
		paramInfo.setRelatedFixedDepositNo(strRelatedFixedDepositNo);
		paramInfo.setTransNo(strTransNo);
		paramInfo.setExecuteDate(tsExecuteDate);
		paramInfo.setInputDate(tsInputDate);
		paramInfo.setInterestStartDate(tsInterestStartDate);
		paramInfo.setModifyDate(tsModifyDate);
		
        //TransFeeDelegation feeDelegation = new TransFeeDelegation();
		Sett_TransFeeDAO dao = new Sett_TransFeeDAO();
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();

		long lReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, 
			sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lReturn));
		Log.print("=================lReturn="+lReturn);

        //根据请求操作，完成业务处理的调用
        if(String.valueOf(SETTConstant.Actions.LINKSEARCH).equals(strAction))
        {
			//链接查找成功与否都返回链接查找的视图页面
			strFailPageURL = strSuccessPageURL;
			
			TransFeeInfo searchInfo = new TransFeeInfo();
			searchInfo.setOfficeID(sessionMng.m_lOfficeID);
			searchInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			searchInfo.setCheckUserID(sessionMng.m_lUserID);
			searchInfo.setTransactionTypeID(SETTConstant.TransactionType.TRANSFEE);
			searchInfo.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
			searchInfo.setStatusID(paramInfo.getStatusID());

			Collection resultColl = null;
            //resultColl = feeDelegation.findByConditions(searchInfo, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(searchInfo, nOrderByCode, isDesc);
            if(resultColl != null && !resultColl.isEmpty())
            {
                strActionResult = Constant.ActionResult.SUCCESS;
            }
			request.setAttribute("searchResults",resultColl);
			
        }
		else if(String.valueOf(SETTConstant.Actions.MATCHSEARCH).equals(strAction))
        {
			paramInfo.setOfficeID(sessionMng.m_lOfficeID);
			paramInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			paramInfo.setInputUserID(sessionMng.m_lUserID);

			TransFeeInfo resultInfo = null;
			//resultInfo = feeDelegation.match(paramInfo);
			resultInfo = dao.match(paramInfo);
            if(resultInfo != null)
            {
                strActionResult = Constant.ActionResult.SUCCESS;
            }
			else
			{
				//sessionMng.getActionMessages().addMessage("无匹配记录");
				//OBHtml.showExceptionMessage(out,sessionMng, ie, "无匹配记录","",1);
			}
			request.setAttribute("searchResults",resultInfo);
        }
		else if("Query".equals(strAction))
        {
			//链接查找成功与否都返回链接查找的视图页面
			//strFailPageURL = strSuccessPageURL;
			
			TransFeeInfo searchInfo = new TransFeeInfo();
			searchInfo.setID(paramInfo.getID());
			searchInfo.setTransNo(paramInfo.getTransNo());
			
			TransFeeInfo resultInfo = null;
			Collection resultColl = null;
            //resultColl = feeDelegation.findByConditions(searchInfo, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(searchInfo, nOrderByCode, isDesc);
            if(resultColl != null && !resultColl.isEmpty())
            {
                strActionResult = Constant.ActionResult.SUCCESS;
				
				Iterator itTemp = resultColl.iterator();
				resultInfo = (TransFeeInfo)itTemp.next();
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
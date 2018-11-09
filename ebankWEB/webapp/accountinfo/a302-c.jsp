<%--
/**
 页面名称 ：a302-c.jsp
 页面功能 : 总账类复核匹配查找、链接查找页面的控制类页面
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
<%@ page import="com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransGeneralLedgerDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO"%>
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
		double dAmountOne = 0.0;
		double dAmountThree = 0.0;
		double dAmountTwo = 0.0;
		double dSumForSearch = 0.0;
		long lAbstractID = -1;
		long lAccountID = -1;
		long lBillBankID = -1;
		long lBillTypeID = -1;
		long lCheckUserID = -1;
		long lClientID = -1;
		long lConfirmOfficeID = -1;
		long lConfirmUserID = -1;
		long lCurrencyID = -1;
		long lDirOne = -1;
		long lDirThree = -1;
		long lDirTwo = -1;
		long lDirection = -1;
		long lGeneralLedgerOne = -1;
		long lGeneralLedgerThree = -1;
		long lGeneralLedgerTwo = -1;
		long lID = -1;
		long lInputUserID = -1;
		long lOfficeID = -1;
		long lSignUserID = -1;
		long lStatusID = -1;
		long lTransActionTypeID = -1;
		String strAbstract = "";
		String strBillNo = "";
		String strCancelCheckAbstract = "";
		String strCheckAbstract = "";
		String strConfirmAbstract = "";
		String strPayExtBankNo = "";
		String strReceiveExtBankNo = "";
		String strTransNo = "";
		String strVoucherNo = "";
		String strVoucherPWD = "";
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
		strTemp = (String)request.getAttribute("dSumForSearch");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dSumForSearch = Double.valueOf(strTemp).doubleValue();
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
		strTemp = (String)request.getAttribute("lCheckUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCheckUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lClientID = Long.valueOf(strTemp).longValue();
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
		strTemp = (String)request.getAttribute("lDirection");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lDirection = Long.valueOf(strTemp).longValue();
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
		strTemp = (String)request.getAttribute("lTransActionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTransActionTypeID = Long.valueOf(strTemp).longValue();
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
		strTemp = (String)request.getAttribute("strCancelCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCancelCheckAbstract = strTemp;
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
		strTemp = (String)request.getAttribute("strPayExtBankNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strPayExtBankNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strReceiveExtBankNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strReceiveExtBankNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strTransNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strTransNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strVoucherNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strVoucherNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strVoucherPWD");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strVoucherPWD = strTemp;
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
		strTemp = (String)request.getAttribute("strModifyDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsModifyDate = new Timestamp(Long.valueOf(strTemp).longValue());
		}
		

		String[] strTempArray = request.getParameterValues("GLCheckBox");
		Log.print("选定的总账类类型:" + strTempArray);
		boolean bGLOne = false;
		boolean bGLTwo = false;
		boolean bGLThree = false;

		if(strTempArray != null)
		{
			for(int i = 0; i < strTempArray.length; i++)
			{
				Log.print(strTempArray[i]);
				if("1".equalsIgnoreCase(strTempArray[i]))
				{
					bGLOne = true;
					continue;
				}
				if("2".equalsIgnoreCase(strTempArray[i]))
				{
					bGLTwo = true;
					continue;
				}
				if("3".equalsIgnoreCase(strTempArray[i]))
				{
					bGLThree = true;
					continue;
				}
			}
		}

		if(bGLOne)
		{
			strTemp = (String)request.getAttribute("lGeneralLedgerOne");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lGeneralLedgerOne = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lDirOne");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lDirOne = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("dAmountOne");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dAmountOne = DataFormat.parseNumber(strTemp);
			}
		}
		if(bGLTwo)
		{
			strTemp = (String)request.getAttribute("lGeneralLedgerTwo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lGeneralLedgerTwo = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lDirTwo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lDirTwo = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("dAmountTwo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dAmountTwo = DataFormat.parseNumber(strTemp);
			}		
		}
		if(bGLThree)
		{
			strTemp = (String)request.getAttribute("lGeneralLedgerThree");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lGeneralLedgerThree = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lDirThree");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lDirThree = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("dAmountThree");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				dAmountThree = DataFormat.parseNumber(strTemp);
			}
		}

		strTemp = (String)request.getAttribute("nOrderByCode");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			nOrderByCode = Integer.valueOf(strTemp).intValue();
		}
		else
		{
			nOrderByCode = Sett_TransGeneralLedgerDAO.ORDERBY_ID;
			request.setAttribute("nOrderByCode",String.valueOf(nOrderByCode));
		}

		strTemp = (String)request.getAttribute("isDesc");
		if(strTemp != null && strTemp.length()>0)
		{
			isDesc = Boolean.valueOf(strTemp).booleanValue();
		}

        TransGeneralLedgerInfo paramInfo = new TransGeneralLedgerInfo();

		paramInfo.setAmount(dAmount);
		paramInfo.setAmountOne(dAmountOne);
		paramInfo.setAmountThree(dAmountThree);
		paramInfo.setAmountTwo(dAmountTwo);
		paramInfo.setSumForSearch(dSumForSearch);
		paramInfo.setAbstractID(lAbstractID);
		paramInfo.setAccountID(lAccountID);
		paramInfo.setBillBankID(lBillBankID);
		paramInfo.setBillTypeID(lBillTypeID);
		paramInfo.setCheckUserID(lCheckUserID);
		paramInfo.setClientID(lClientID);
		paramInfo.setConfirmOfficeID(lConfirmOfficeID);
		paramInfo.setConfirmUserID(lConfirmUserID);
		paramInfo.setCurrencyID(lCurrencyID);
		paramInfo.setDirOne(lDirOne);
		paramInfo.setDirThree(lDirThree);
		paramInfo.setDirTwo(lDirTwo);
		paramInfo.setDirection(lDirection);
		paramInfo.setGeneralLedgerOne(lGeneralLedgerOne);
		paramInfo.setGeneralLedgerThree(lGeneralLedgerThree);
		paramInfo.setGeneralLedgerTwo(lGeneralLedgerTwo);
		paramInfo.setID(lID);
		paramInfo.setInputUserID(lInputUserID);
		paramInfo.setOfficeID(lOfficeID);
		paramInfo.setSignUserID(lSignUserID);
		paramInfo.setStatusID(lStatusID);
		paramInfo.setTransActionTypeID(lTransActionTypeID);
		paramInfo.setAbstract(strAbstract);
		paramInfo.setBillNo(strBillNo);
		paramInfo.setCancelCheckAbstract(strCancelCheckAbstract);
		paramInfo.setCheckAbstract(strCheckAbstract);
		paramInfo.setConfirmAbstract(strConfirmAbstract);
		paramInfo.setPayExtBankNo(strPayExtBankNo);
		paramInfo.setReceiveExtBankNo(strReceiveExtBankNo);
		paramInfo.setTransNo(strTransNo);
		paramInfo.setVoucherNo(strVoucherNo);
		paramInfo.setVoucherPWD(strVoucherPWD);
		paramInfo.setExecuteDate(tsExecuteDate);
		paramInfo.setInputDate(tsInputDate);
		paramInfo.setInterestStartDate(tsInterestStartDate);
		paramInfo.setModifyDate(tsModifyDate);

		Log.print(paramInfo);

        //TransGeneralLedgerDelegation glDelegation = new TransGeneralLedgerDelegation();
		Sett_TransGeneralLedgerDAO dao = new Sett_TransGeneralLedgerDAO();
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
			
			TransGeneralLedgerInfo searchInfo = new TransGeneralLedgerInfo();
			searchInfo.setOfficeID(sessionMng.m_lOfficeID);
			searchInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			searchInfo.setCheckUserID(sessionMng.m_lUserID);
			searchInfo.setTransActionTypeID(SETTConstant.TransactionType.GENERALLEDGER);
			searchInfo.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));
			searchInfo.setStatusID(paramInfo.getStatusID());

			Collection resultColl = null;
            //resultColl = glDelegation.findByConditions(searchInfo, nOrderByCode, isDesc);
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

			TransGeneralLedgerInfo resultInfo = null;
			//resultInfo = glDelegation.match(paramInfo);
			resultInfo = dao.match(paramInfo);
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
		else if("Query".equals(strAction))
        {
			//链接查找成功与否都返回链接查找的视图页面
			//strFailPageURL = strSuccessPageURL;
			
			TransGeneralLedgerInfo searchInfo = new TransGeneralLedgerInfo();
			searchInfo.setID(paramInfo.getID());
			searchInfo.setTransNo(paramInfo.getTransNo());
			
			TransGeneralLedgerInfo resultInfo = null;
			Collection resultColl = null;
            //resultColl = glDelegation.findByConditions(searchInfo, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(searchInfo, nOrderByCode, isDesc);
            if(resultColl != null && !resultColl.isEmpty())
            {
                strActionResult = Constant.ActionResult.SUCCESS;
				
				Iterator itTemp = resultColl.iterator();
				resultInfo = (TransGeneralLedgerInfo)itTemp.next();
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
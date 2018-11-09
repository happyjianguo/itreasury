<%--
/**
 页面名称 ：a301-c.jsp
 页面功能 : 活期业务复核匹配查找、链接查找页面的控制类页面
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
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO"%>
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
		String strBankChequeNo = "";
		long lBankID = -1;
		long lBillBankID = -1;
		String strBillNo = "";
		long lBillTypeID = -1;
		long lCashFlowID = -1;
		String strCheckAbstractStr = "";
		String strConfirmAbstractStr = "";
		long lConfirmOfficeID = -1;
		long lConfirmUserID = -1;
		String strConsignPassword = "";
		long lConsignReceiveTypeID = -1;
		String strConsignVoucherNo = "";
		String strDeclarationNo = "";
		String strExtBankNo = "";
		String strInstructionNo = "";
		java.sql.Timestamp tsModifyTime = null;
		long lPayAccountID = -1;
		long lReceiveAccountID = -1;
		long lSignUserID = -1;
		long lSingleBankAccountTypeID = -1;
		long lStatusID = -1;
		long lReceiveClientID = -1;
		long lPayClientID = -1;
		long lAbstractID = -1;
		String strExtAccountNo = "";
		String strExtClientName = "";
		String strRemitInBank = "";
		String strRemitInCity = "";
		String strRemitInProvince = "";
		String strAbstractStr = "";
		long lCheckUserID = -1;
		long lCurrencyID = -1;
		java.sql.Timestamp tsExecuteDate = null;
		long lId = -1;
		long lInputUserID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		long lOfficeID = -1;
		long lTransactionTypeID = -1;
		String strTransNo = "";

		//页面辅助变量
		String strExecuteDate = null;
		String strInterestStartDate = null;
		String strModifyTime = null;

		int  nOrderByCode = -1;
		boolean isDesc = true;
		String strTemp = null;
		long lAccountID = -1;

		//读取数据
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAccountID = Long.valueOf(strTemp).longValue();
		}
		request.setAttribute("lAccountID",String.valueOf(lAccountID));
		
		strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			dAmount = DataFormat.parseNumber(strTemp);
		}
		strTemp = (String)request.getAttribute("strBankChequeNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBankChequeNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lBillBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lBillBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lBillBankIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strBillNo = strTemp;
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
		strTemp = (String)request.getAttribute("strCheckAbstractStr");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strCheckAbstractStr = strTemp;
		}
		strTemp = (String)request.getAttribute("strConfirmAbstractStr");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strConfirmAbstractStr = strTemp;
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
		strTemp = (String)request.getAttribute("strConsignPassword");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strConsignPassword = strTemp;
		}
		strTemp = (String)request.getAttribute("lConsignReceiveTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lConsignReceiveTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strConsignVoucherNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strConsignVoucherNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strDeclarationNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strDeclarationNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strExtBankNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strExtBankNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strInstructionNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strInstructionNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strModifyTime");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strModifyTime = strTemp;				
			tsModifyTime = new Timestamp(Long.valueOf(strTemp).longValue());
		}
		strTemp = (String)request.getAttribute("lPayAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lPayAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lReceiveAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSignUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSignUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSingleBankAccountTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lSingleBankAccountTypeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lReceiveAccountIDClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lReceiveClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lPayAccountIDClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lPayClientID = Long.valueOf(strTemp).longValue();
		}
		else if(lPayClientID == -1)
		{
			strTemp = (String)request.getAttribute("lPayClientID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				lPayClientID = Long.valueOf(strTemp).longValue();
			}
		}
		strTemp = (String)request.getAttribute("lAbstractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lAbstractID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strExtAccountNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strExtAccountNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strExtClientName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strExtClientName = strTemp;
		}
		strTemp = (String)request.getAttribute("strRemitInBank");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strRemitInBank = strTemp;
		}
		strTemp = (String)request.getAttribute("strRemitInCity");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strRemitInCity = strTemp;
		}
		strTemp = (String)request.getAttribute("strRemitInProvince");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strRemitInProvince = strTemp;
		}
		strTemp = (String)request.getAttribute("strAbstractStr");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strAbstractStr = strTemp;
		}
		strTemp = (String)request.getAttribute("lCheckUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCheckUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lCurrencyID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lCurrencyID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strExecuteDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsExecuteDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lId");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lId = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lInputUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lInputUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strInterestStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			tsInterestStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lOfficeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lTransactionTypeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lTransactionTypeID = Long.valueOf(strTemp).longValue();
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
			nOrderByCode = Sett_TransCurrentDepositDAO.ORDERBY_TRANSACTIONNOID;
			request.setAttribute("nOrderByCode",String.valueOf(nOrderByCode));
		}

		strTemp = (String)request.getAttribute("isDesc");
		if(strTemp != null && strTemp.length()>0)
		{
			isDesc = Boolean.valueOf(strTemp).booleanValue();
		}

		//如果单据未输入，将单据相关信息设置为无效值
		if("".equals(strBillNo))
		{
			strBillNo = "";
			lBillBankID = -1;
			lBillTypeID = -1;
		}

        TransCurrentDepositInfo depositInfo = new TransCurrentDepositInfo();

        depositInfo.setAmount(dAmount);
		depositInfo.setBankChequeNo(strBankChequeNo);
		depositInfo.setBankID(lBankID);
		depositInfo.setBillBankID(lBillBankID);
		depositInfo.setBillNo(strBillNo);
		depositInfo.setBillTypeID(lBillTypeID);
		depositInfo.setCashFlowID(lCashFlowID);
		depositInfo.setCheckAbstractStr(strCheckAbstractStr);
		depositInfo.setConfirmAbstractStr(strConfirmAbstractStr);
		depositInfo.setConfirmOfficeID(lConfirmOfficeID);
		depositInfo.setConfirmUserID(lConfirmUserID);
		depositInfo.setConsignPassword(strConsignPassword);
		depositInfo.setConsignReceiveTypeID(lConsignReceiveTypeID);
		depositInfo.setConsignVoucherNo(strConsignVoucherNo);
		depositInfo.setDeclarationNo(strDeclarationNo);
		depositInfo.setExtBankNo(strExtBankNo);
		depositInfo.setInstructionNo(strInstructionNo);
		depositInfo.setModifyTime(tsModifyTime);
		depositInfo.setPayAccountID(lPayAccountID);
		depositInfo.setReceiveAccountID(lReceiveAccountID);
		depositInfo.setSignUserID(lSignUserID);
		depositInfo.setSingleBankAccountTypeID(lSingleBankAccountTypeID);
		depositInfo.setStatusID(lStatusID);
		depositInfo.setReceiveClientID(lReceiveClientID);
		depositInfo.setPayClientID(lPayClientID);
		depositInfo.setAbstractID(lAbstractID);
		depositInfo.setExtAccountNo(strExtAccountNo);
		depositInfo.setExtClientName(strExtClientName);
		depositInfo.setRemitInBank(strRemitInBank);
		depositInfo.setRemitInCity(strRemitInCity);
		depositInfo.setRemitInProvince(strRemitInProvince);
		depositInfo.setAbstractStr(strAbstractStr);
		depositInfo.setCheckUserID(lCheckUserID);
		depositInfo.setCurrencyID(lCurrencyID);
		depositInfo.setExecuteDate(tsExecuteDate);
		depositInfo.setId(lId);
		depositInfo.setInputUserID(lInputUserID);
		depositInfo.setInterestStartDate(tsInterestStartDate);
		depositInfo.setOfficeID(lOfficeID);
		depositInfo.setTransactionTypeID(lTransactionTypeID);
		depositInfo.setTransNo(strTransNo);

		Log.print(depositInfo);

        //TransCurrentDepositDelegation depositDelegation = new TransCurrentDepositDelegation();		
		Sett_TransCurrentDepositDAO dao = new Sett_TransCurrentDepositDAO();
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

			TransCurrentDepositInfo depositInfoTemp = new TransCurrentDepositInfo(); 

			depositInfoTemp.setOfficeID(sessionMng.m_lOfficeID);
			depositInfoTemp.setCurrencyID(sessionMng.m_lCurrencyID);
			depositInfoTemp.setCheckUserID(sessionMng.m_lUserID);
			depositInfoTemp.setTransactionTypeID(depositInfo.getTransactionTypeID());
			depositInfoTemp.setStatusID(depositInfo.getStatusID());
			depositInfoTemp.setExecuteDate(DataFormat.getDateTime(Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)));

			TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfoTemp);
			Collection resultColl = null;
            //resultColl = depositDelegation.findByConditions(dataEntity, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(dataEntity.getSett_TransCurrentDepositInfo(), nOrderByCode, isDesc);
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

			TransCurrentDepositInfo resultInfo = null;
			//resultInfo = depositDelegation.match(lTransactionTypeID, depositInfo);
			resultInfo = dao.match(lTransactionTypeID, depositInfo);
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
			TransCurrentDepositInfo resultInfo = null;
			TransCurrentDepositInfo depositInfoTemp = new TransCurrentDepositInfo(); 
			if (depositInfo.getId() <= 0)
			{
				depositInfoTemp.setTransNo(depositInfo.getTransNo());
				depositInfoTemp.setTransactionTypeID(depositInfo.getTransactionTypeID());
			}
			else
			{
	            depositInfoTemp.setId(depositInfo.getId());
			}
			TransCurrentDepositAssembler dataEntity = new TransCurrentDepositAssembler(depositInfoTemp);
			Collection resultColl = null;
            //resultColl = depositDelegation.findByConditions(dataEntity, nOrderByCode, isDesc);
            resultColl = dao.findByConditions(dataEntity.getSett_TransCurrentDepositInfo(), nOrderByCode, isDesc);
            if(resultColl != null && !resultColl.isEmpty())
            {
                strActionResult = Constant.ActionResult.SUCCESS;
				Iterator itTemp = resultColl.iterator();
				resultInfo = (TransCurrentDepositInfo)itTemp.next();
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
		rd.forward( request,response );
    } catch( IException ie ) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
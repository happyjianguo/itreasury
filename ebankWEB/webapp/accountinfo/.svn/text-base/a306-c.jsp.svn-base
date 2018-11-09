<%--
/**
 页面名称 ：a306-c.jsp
 页面功能 : 定期续期转存复核匹配链结查找页面的控制类页面
 作    者 ：kewen hu
 日    期 ：2004-02-25
 特殊说明 ：简单实现说明：
				1、复核匹配
				2、复核链接查找
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
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dataentity.*"%>
<%@ page import="com.iss.itreasury.settlement.bizdelegation.TransFixedDepositDelegation"%>
<%@ page import="com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_TransFixedContinueDAO"%>
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
		String strAbstract = "";
		long lAccountID = -1;
		String strAccountNo = "";
		double dAmount = 0.0;
		long lCertificationBankID = -1;
		String strCertificationNo = "";
		String strCheckAbstract = "";
		long lCheckUserID = -1;
		String strCheckUserName = "";
		long lClientID = -1;
		String strClientName = "";
		String strClientNo = "";
		long lCurrencyID = -1;
		String strDepositNo = "";
		long lDepositTerm = -1;
		java.sql.Timestamp tsEndDate = null;
		java.sql.Timestamp tsExecuteDate = null;
		long lID = -1;
		long lInputUserID = -1;
		String strInputUserName = "";
		String strInstructionNo = "";
		long lInterestPlanID = -1;
		java.sql.Timestamp tsInterestStartDate = null;
		java.sql.Timestamp tsModifyDate = null;
		long lOfficeID = -1;
		double dPreDrawInterest = 0.0;
		double dRate = 0.0;
		long lSealBankID = -1;
		String strSealNo = "";
		java.sql.Timestamp tsStartDate = null;
		long lStatusID = -1;
		long lSubAccountID = -1;
		long lTransactionTypeID = -1;
		String strTransNo = "";
		long lAbstractID = -1;
		long lInterestBankID = -1;
		String strInterestBankName = "";
		String strInterestCashFlowDesc = "";
		long lInterestCashFlowID = -1;
		String strInterestRemitInCity = "";
		String strInterestExtClientName = "";
		String strInterestExtAcctNo = "";
		String strInterestRemitInBank = "";
		String strInterestExtBankNo = "";
		long lInterestPayTypeID = -1;
		String strInterestRemitInProvince = "";
		long lIsCapitalAndInterestTransfer = -1;
		double dNewAmount = 0.0;
		long lNewCertificationBankID = -1;
		String strNewCertificationNo = "";
		String strNewDepositNo = "";
		long lNewDepositTerm = -1;
		java.sql.Timestamp tsNewEndDate = null;
		long lNewInterestPlanID = -1;
		double dNewRate = 0.0;
		java.sql.Timestamp tsNewStartDate = null;
		double dPayableInterest = 0.0;
		long lReceiveInterestAccountID = -1;
		String strReceiveInterestAccountNo = "";
		double dWithDrawInterest = 0.0;
		long lNewSealBankID = -1;
		String strNewSealNo = "";
		java.sql.Timestamp tsInputDate = null;
		
		//页面辅助变量		
		int  nOrderByCode = -1;  //排序字段

		String strModifyTime = null;

		//读取数据		
        strAction = (String)request.getAttribute("strAction");
		strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		strFailPageURL = (String)request.getAttribute("strFailPageURL");
				
		String strTemp = null;		
		strTemp = (String)request.getAttribute("nOrderByCode");
		if(strTemp != null && strTemp.trim().length() > 0)
		{
			nOrderByCode = Integer.valueOf(strTemp).intValue();
		}
		else
		{
			//Sett_TransOpenDepositDAO dao =new Sett_TransOpenDepositDAO();
			nOrderByCode = Sett_TransFixedContinueDAO.ORDERBY_TRANSACTIONNOID;
			request.setAttribute("nOrderByCode",String.valueOf(nOrderByCode));
		}
		//信息设置
		strTemp = (String)request.getAttribute("rdoInterest");
		if(strTemp !=null && strTemp.equals("1"))
		{
			strTemp = (String)request.getAttribute("lIsCapitalAndInterestTransfer");
			strTemp = "1";
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lIsCapitalAndInterestTransfer = Long.valueOf(strTemp).longValue();
			}
		}
		else if(strTemp !=null && strTemp.equals("2"))
		{
			strTemp = (String)request.getAttribute("strInterestExtClientName");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestExtClientName = strTemp;
			}
			strTemp = (String)request.getAttribute("lReceiveInterestAccountID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lReceiveInterestAccountID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lReceiveInterestAccountIDCtrl");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strReceiveInterestAccountNo = strTemp;
			}
		}
		else if(strTemp !=null && strTemp.equals("3"))
		{
			strTemp = (String)request.getAttribute("lInterestPayTypeID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lInterestPayTypeID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lInterestBankID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lInterestBankID = Long.valueOf(strTemp).longValue();
			}
			strTemp = (String)request.getAttribute("lInterestBankIDCtrl");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestBankName = strTemp;
			}			
			strTemp = (String)request.getAttribute("strInterestExtAcctNoCtrl");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestExtAcctNo = strTemp;
			}
			strTemp = (String)request.getAttribute("strInterestExtClientName");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestExtClientName = strTemp;
			}
			strTemp = (String)request.getAttribute("strInterestRemitInProvince");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestRemitInProvince = strTemp;
			}
			strTemp = (String)request.getAttribute("strInterestRemitInCity");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestRemitInCity = strTemp;
			}
			strTemp = (String)request.getAttribute("lInterestRemitInBankIDCtrl");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestRemitInBank = strTemp;
			}
			strTemp = (String)request.getAttribute("strInterestExtBankNo");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    strInterestExtBankNo = strTemp;
			}	
		}
		strTemp = (String)request.getAttribute("lAbstractIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("lAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAccountID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lAccountIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strAccountNo = strTemp;
		}
		strTemp = (String)request.getAttribute("dAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
//		    dAmount = Double.valueOf(strTemp).doubleValue();
			dAmount = DataFormat.parseNumber(strTemp);
		}
		strTemp = (String)request.getAttribute("lCertificationBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lCertificationBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lCertificationBankIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strCertificationNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strCheckAbstract");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strCheckAbstract = strTemp;
		}
		strTemp = (String)request.getAttribute("lCheckUserID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lCheckUserID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strCheckUserName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strCheckUserName = strTemp;
		}
		strTemp = (String)request.getAttribute("lClientID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lClientID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strClientName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strClientName = strTemp;
		}
		strTemp = (String)request.getAttribute("lClientIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strClientNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lCurrencyID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lCurrencyID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSubAccountIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strDepositNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lDepositTerm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lDepositTerm = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strEndDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsEndDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strExecuteDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsExecuteDate = DataFormat.getDateTime(strTemp);
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
		strTemp = (String)request.getAttribute("strInputUserName");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strInputUserName = strTemp;
		}
		strTemp = (String)request.getAttribute("strInstructionNo");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strInstructionNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lInterestPlanID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lInterestPlanID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strInterestStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInterestStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("strModifyTime");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			strModifyTime = strTemp;
			tsModifyDate = new Timestamp(Long.valueOf(strTemp).longValue());
		    //tsModifyDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lOfficeID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lOfficeID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("dPreDrawInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dPreDrawInterest = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("dRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("lSealBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSealBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSealBankIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strSealNo = strTemp;
		}
		strTemp = (String)request.getAttribute("strStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lStatusID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lSubAccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lSubAccountID = Long.valueOf(strTemp).longValue();
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
		strTemp = (String)request.getAttribute("lAbstractID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lAbstractID = Long.valueOf(strTemp).longValue();
		}		
		strTemp = (String)request.getAttribute("strInterestCashFlowDesc");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strInterestCashFlowDesc = strTemp;
		}
		strTemp = (String)request.getAttribute("lInterestCashFlowID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lInterestCashFlowID = Long.valueOf(strTemp).longValue();
		}		
		strTemp = (String)request.getAttribute("dNewAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    //dNewAmount = Double.valueOf(strTemp).doubleValue();
			dNewAmount = DataFormat.parseNumber(strTemp);
		}
		strTemp = (String)request.getAttribute("lNewCertificationBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNewCertificationBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNewCertificationBankIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strNewCertificationNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lNewCertificationBankIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strNewDepositNo = strTemp;
		}
		strTemp = (String)request.getAttribute("lNewDepositTerm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNewDepositTerm = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("strNewEndDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsNewEndDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("lNewInterestPlanID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNewInterestPlanID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNewInterestPlanIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dNewRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("strNewStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsNewStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("dPayableInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dPayableInterest = Double.valueOf(strTemp).doubleValue();
		}		
		strTemp = (String)request.getAttribute("dWithDrawInterest");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    dWithDrawInterest = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("lNewSealBankID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    lNewSealBankID = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("lNewSealBankIDCtrl");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    strNewSealNo = strTemp;
		}
		strTemp = (String)request.getAttribute("InputDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
		    tsInputDate = DataFormat.getDateTime(strTemp);
		}
		

        TransFixedContinueInfo info = new TransFixedContinueInfo();

		info.setAbstract(strAbstract);
		info.setAccountID(lAccountID);
		info.setAccountNo(strAccountNo);
		info.setAmount(dAmount);
		info.setCertificationBankID(lCertificationBankID);
		info.setCertificationNo(strCertificationNo);
		info.setCheckAbstract(strCheckAbstract);
		info.setCheckUserID(lCheckUserID);
		info.setCheckUserName(strCheckUserName);
		info.setClientID(lClientID);
		info.setClientName(strClientName);
		info.setClientNo(strClientNo);
		info.setCurrencyID(lCurrencyID);
		info.setDepositNo(strDepositNo);
		info.setDepositTerm(lDepositTerm);
		info.setEndDate(tsEndDate);
		info.setExecuteDate(tsExecuteDate);
		info.setID(lID);
		info.setInputUserID(lInputUserID);
		info.setInputUserName(strInputUserName);
		info.setInstructionNo(strInstructionNo);
		info.setInterestPlanID(lInterestPlanID);
		info.setInterestStartDate(tsInterestStartDate);
		info.setModifyDate(tsModifyDate);
		info.setOfficeID(lOfficeID);
		info.setPreDrawInterest(dPreDrawInterest);
		info.setRate(dRate);
		info.setSealBankID(lSealBankID);
		info.setSealNo(strSealNo);
		info.setStartDate(tsStartDate);
		info.setStatusID(lStatusID);
		info.setSubAccountID(lSubAccountID);
		info.setTransactionTypeID(lTransactionTypeID);
		info.setTransNo(strTransNo);
		info.setAbstractID(lAbstractID);
		info.setInterestBankID(lInterestBankID);
		info.setInterestBankName(strInterestBankName);
		info.setInterestCashFlowDesc(strInterestCashFlowDesc);
		info.setInterestCashFlowID(lInterestCashFlowID);
		info.setInterestRemitInCity(strInterestRemitInCity);
		info.setInterestExtClientName(strInterestExtClientName);
		info.setInterestExtAcctNo(strInterestExtAcctNo);
		info.setInterestRemitInBank(strInterestRemitInBank);
		info.setInterestExtBankNo(strInterestExtBankNo);
		info.setInterestPayTypeID(lInterestPayTypeID);
		info.setInterestRemitInProvince(strInterestRemitInProvince);
		info.setIsCapitalAndInterestTransfer(lIsCapitalAndInterestTransfer);
		info.setNewAmount(dNewAmount);
		info.setNewCertificationBankID(lNewCertificationBankID);
		info.setNewCertificationNo(strNewCertificationNo);
		info.setNewDepositNo(strNewDepositNo);
		info.setNewDepositTerm(lNewDepositTerm);
		info.setNewEndDate(tsNewEndDate);
		info.setNewInterestPlanID(lNewInterestPlanID);
		info.setNewRate(dNewRate);
		info.setNewStartDate(tsNewStartDate);
		info.setPayableInterest(dPayableInterest);
		info.setReceiveInterestAccountID(lReceiveInterestAccountID);
		info.setReceiveInterestAccountNo(strReceiveInterestAccountNo);
		info.setWithDrawInterest(dWithDrawInterest);
		info.setNewSealBankID(lNewSealBankID);
		info.setNewSealNo(strNewSealNo);
		info.setInputDate(tsInputDate);
			
		info.setTransactionTypeID(lTransactionTypeID);
		//缺省条件
		info.setCurrencyID(sessionMng.m_lCurrencyID);
		info.setOfficeID(sessionMng.m_lOfficeID);
		info.setInputUserID(sessionMng.m_lUserID);
		info.setStatusID(SETTConstant.TransactionStatus.SAVE);
		
		/*System.out.println("*********off="+info.getOfficeID());
		System.out.println("*********cur="+info.getCurrencyID());
		System.out.println("*********sta="+info.getStatusID());
		System.out.println("*********ipuser="+info.getInputUserID());
		System.out.println("*********acc="+info.getAccountID());
		System.out.println("*********depno="+info.getDepositNo());
		System.out.println("*********newstartDt="+info.getNewStartDate());
		System.out.println("*********depterm="+info.getNewDepositTerm());
		System.out.println("*********newdepno="+info.getNewDepositNo());
		System.out.println("*********newrate="+info.getNewRate());
		System.out.println("*********newAmount="+info.getNewAmount());
		System.out.println("*********exedt="+info.getExecuteDate());
		System.out.println("*********iscap="+info.getIsCapitalAndInterestTransfer());
		System.out.println("*********iAcc=="+info.getReceiveInterestAccountID());
		System.out.println("*********ipay="+info.getInterestPayTypeID());
		System.out.println("*********ibank="+info.getInterestBankID());
		System.out.println("*********"+info.getTransactionTypeID());*/
		
		Log.print(info);
		
        //TransFixedDepositDelegation depositDelegation = new TransFixedDepositDelegation();
		Sett_TransFixedContinueDAO dao = new Sett_TransFixedContinueDAO();
		OBCheckTransDirectionDao obdao= new OBCheckTransDirectionDao();

		long lReturn = obdao.CheckAccountID(strTransNo, "", sessionMng.m_lClientID, 
			sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		request.setAttribute("lReturn",String.valueOf(lReturn));
		Log.print("=================lReturn="+lReturn);

        //根据请求操作，完成业务处理的调用
        if(String.valueOf(SETTConstant.Actions.LINKSEARCH).equals(strAction))
        {	
			long[] lStatusIDs = null;
			QueryByStatusConditionInfo depositInfo = new QueryByStatusConditionInfo();
	        depositInfo.setOfficeID(sessionMng.m_lOfficeID);
			depositInfo.setCurrencyID(sessionMng.m_lCurrencyID);
			depositInfo.setUserID(sessionMng.m_lUserID);
			depositInfo.setTransactionTypeID(lTransactionTypeID);
			depositInfo.setOrderByType(nOrderByCode);
			
			boolean isDesc = true;
			String strTemp1 = (String)request.getAttribute("isDesc");
			if(strTemp1 != null && strTemp1.length()>0)
			{
				isDesc = Boolean.valueOf(strTemp1).booleanValue();
			}
			
			depositInfo.setDesc(isDesc);
			depositInfo.setDate(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID));		
			
			//复核
			depositInfo.setTypeID(1);
			lStatusIDs = new long[1];
			lStatusIDs[0] = SETTConstant.TransactionStatus.CHECK;
			depositInfo.setStatus(lStatusIDs);
				
			Collection resultColl = null;
            //resultColl = depositDelegation.continueFindByStatus(depositInfo);
            resultColl = dao.findByStatus(depositInfo);
			strActionResult = Constant.ActionResult.SUCCESS;
			request.setAttribute("searchResults",resultColl);
        }
		else if("FixedQuery".equals(strAction))
        {
			strTemp = (String)request.getAttribute("lID");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
			    lID = Long.valueOf(strTemp).longValue();
			}
			strTransNo = (String)request.getAttribute("strTransNo");
			if(strTransNo==null || strTransNo.equals(""))
			{
				if (lID > 0)
				{
					TransFixedContinueInfo continueInfo = null;
					//continueInfo = depositDelegation.continueFindByID(lID);	
					continueInfo = dao.findByID(lID);	
					if(continueInfo != null)
	            	{
	       	        	strActionResult = Constant.ActionResult.SUCCESS;
						request.setAttribute("strAction","FixedQuery");
						request.setAttribute("searchResults",continueInfo);
						request.setAttribute("strTransNo",continueInfo.getTransNo());
	       		    }
				}
				else
				{
					//sessionMng.getActionMessages().addMessage("无匹配记录");
					strActionResult = Constant.ActionResult.FAIL;
				}
			}
			else
			{
				TransFixedContinueInfo continueInfo = null;
				//continueInfo = depositDelegation.continueFindByTransNo(strTransNo);
				continueInfo = dao.findByTransNo(strTransNo);
				if(continueInfo != null)
            	{
       	        	strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("strAction","FixedQuery");
					request.setAttribute("searchResults",continueInfo);
					request.setAttribute("strTransNo",continueInfo.getTransNo());
       		    }
			}
		}
		else if(String.valueOf(SETTConstant.Actions.MATCHSEARCH).equals(strAction))
        {
			Collection resultColl = null;		
            //resultColl = depositDelegation.continueMatch(info);
            resultColl = dao.match(info);
           	if(resultColl != null && !resultColl.isEmpty())
	        {
				TransFixedContinueInfo resultInfo = null;
				Iterator iterator=resultColl.iterator();
				if(iterator.hasNext())
				{
					resultInfo = (TransFixedContinueInfo)iterator.next();
				}
	          	if(resultInfo != null)
            	{
       	        	strActionResult = Constant.ActionResult.SUCCESS;
					request.setAttribute("searchResults",resultInfo);
					request.setAttribute("strTransNo",resultInfo.getTransNo());
					request.setAttribute("strModifyTime",String.valueOf(resultInfo.getModifyDate().getTime()));
       		    }
			}
			else
			{
				//sessionMng.getActionMessages().addMessage("无匹配记录");
			}
        }
        else
        {
            Log.print("");
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
<%
	/*
	*页面名称：f002-c.jsp
	*功能：免还申请
	//*/
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.bizlogic.*,
				com.iss.itreasury.loan.loanpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.dataentity.*,
				com.iss.itreasury.ebank.obfreeapply.bizlogic.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%
	/* 标题固定变量 */
	String strTitle = "[免还申请提交]";
%>					
<%
  try
  {
  
  	String strOfficeName = sessionMng.m_strOfficeName;
	long lCurrencyID=sessionMng.m_lCurrencyID;//货币标识
	long lOfficeID=sessionMng.m_lOfficeID;//办事处标识
	long lUserID = sessionMng.m_lUserID;
	String strTmp = "";
	String strConsignClientAccount = "";
	double mFreeAmount = 0;
	double mFreeRate = 0;
	Timestamp dtFreeDate = null; 
	String strFreeReason = "";
	String strAction = "";
	long lContractID = -1;
	long lLoanPayID = -1;
	long lFreeApplyID = -1;
	long lstatusID = -1;
	long lInputUserID = -1;
	FreeApplyInfo info = new FreeApplyInfo();

	//Log.print("*******进入页面--ebank/loan/discountapply/d011-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

///////////////////////////////////////////////////////////////////////////////////
		OBFreeApplyHome home  = (OBFreeApplyHome) EJBObject.getEJBHome("OBFreeApplyHome");
		OBFreeApply ejb = home.create();
		FreeApplyInfo freeinfo = new FreeApplyInfo ();
		FreeApplyQueryInfo queryInfo = new FreeApplyQueryInfo();

////////////////////////////////////////////////////////////////////
	strConsignClientAccount=GetParam(request,"strConsignClientAccount");
	
	strTmp = (String)request.getParameter("mFreeAmount");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					mFreeAmount = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
					Log.print("免还金额："+mFreeAmount);
				}
				catch(Exception e)
				{
					;
				}
			}
	strTmp = (String)request.getParameter("dtFreeDate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					dtFreeDate = DataFormat.getDateTime(strTmp.trim());
					Log.print("免还日期："+dtFreeDate);
				}
				catch(Exception e)
				{
					;
				}
			}
	strTmp = (String)request.getParameter("mFreeRate");
			if( strTmp != null && strTmp.length() > 0 )
			{
				try
				{
					mFreeRate = Double.parseDouble(DataFormat.reverseFormatAmount(strTmp.trim()));
					Log.print("免还利息："+mFreeRate);
				}
				catch(Exception e)
				{
					;
				}
			}
	
	strFreeReason = GetParam(request,"strFreeReason");
	strAction = GetParam(request,"strAction");
	lContractID = GetNumParam(request,"lContractID");
	lLoanPayID = GetNumParam(request,"lLoanPayID");
	lFreeApplyID = GetNumParam(request,"lFreeApplyID");
	lstatusID = OBConstant.LoanInstrStatus.SUBMIT;
	lInputUserID = lUserID;
	////////////////////////////////////////////////////////////////////
	info.setFreeReason(strFreeReason);
	info.setFreeAmount(mFreeAmount);
	info.setFreeRate(mFreeRate);
	info.setFreeDate(dtFreeDate);
	info.setConsignClientAccount(strConsignClientAccount);
	info.setContractID(lContractID);
	info.setLoanPayID(lLoanPayID);
	info.setID(lFreeApplyID);
	info.setStatusID(lstatusID);
	info.setInputUserID(lInputUserID);
					

	/////////////////////////////////////////////////////////////////////////////		
	if(strAction.equals("modify")){
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+info.getID());
	
		long lReturn = ejb.updateFreeApply(info);
			//lFreeApplyID = ejb.saveFreeApply((int)lTypeID,lFreeApplyID,lLoanPayID,dAmount,nIntervalNum,dBalance,dtEndDate,mFreeAmount,strConsignClientAccount,dtFreeDate,strFreeReason,mFreeRate,lUserID,dtInputDate);

			if(lReturn>0)
			{
%>
				<script language="JavaScript">
					alert("保存成功！");
				</script>
<%
					//log4j.info("申请免还成功");
				response.sendRedirect("f002-c.jsp?strAction=view&lFreeApplyID="+lFreeApplyID+"&lLoanPayID="+lLoanPayID+"&lContractID="+lContractID);
			return;
			}
			else
			{
					//log4j.info("申请免还失败");
%>
					<script language="JavaScript">
						alert("保存免还失败！");
						eval("history.back(-1)");
					</script>
<%
			}
	}else
	{
	lFreeApplyID = ejb.saveFreeApply(info);
			
			
			//lFreeApplyID = ejb.saveFreeApply((int)lTypeID,lFreeApplyID,lLoanPayID,dAmount,nIntervalNum,dBalance,dtEndDate,mFreeAmount,strConsignClientAccount,dtFreeDate,strFreeReason,mFreeRate,lUserID,dtInputDate);

			if(lFreeApplyID>0)
			{
%>
				<script language="JavaScript">
					alert("申请成功！");
				</script>
<%
					//log4j.info("申请免还成功");
				response.sendRedirect("f002-c.jsp?strAction=view&lFreeApplyID="+lFreeApplyID+"&lLoanPayID="+lLoanPayID+"&lContractID="+lContractID);
			return;
			}
			else
			{
					//log4j.info("申请免还失败");
%>
					<script language="JavaScript">
						alert("申请免还失败！");
						eval("history.back(-1)");
					</script>
<%
			}
		}
	}catch(IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		out.flush();
		return;
	}
	%>
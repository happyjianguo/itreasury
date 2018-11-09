<!--
/**
 * 页面名称 ：v034_bankpay.jsp
 * 页面功能 : 网银—已办业务查询显示页面
 * 作    者 ：mingfang
 * 日    期 ：2007-05-17
 * 特殊说明 ：配合inut审批流使用
 *			
 * 修改历史 ：
 */
-->
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection,
                 com.iss.itreasury.util.*,
                 java.util.Iterator,
                 com.iss.itreasury.ebank.util.NameRef,
                 com.iss.itreasury.ebank.approval.bizlogic.OBQueryApprovalRecordBiz,
                 com.iss.itreasury.ebank.approval.dataentity.OBInutWorkInfo,
                 com.iss.itreasury.ebank.approval.dataentity.OBInutResultInfo,
                 com.iss.itreasury.ebank.util.SessionOB,
                 com.iss.itreasury.ebank.util.OBHtml,
                 com.iss.itreasury.ebank.util.OBConstant,
                 com.iss.itreasury.util.Constant,
                 com.iss.itreasury.system.util.SYSHTML            
                 " 
                 %>
	
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%
String strTableTitle = null;
String strContext = request.getContextPath();
try
{       //请求检测
		/** 权限检查 **/
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTableTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		/* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);
        
		long lTransactionTypeID = -1;              		   //交易类型
		long lUserID = sessionMng.m_lUserID;  		       //用户
		long lClientID = sessionMng.m_lClientID;           //客户
		long lOfficeID = sessionMng.m_lOfficeID;           //办事处
		long lCurrencyID = sessionMng.m_lCurrencyID;	   //币种
		long lModuleID = sessionMng.m_lModuleID; 		   //模块
		String dtExecuteFrom = "";   //执行日   由
		String dtExecuteTo = "";     //执行日   到 
		
		if(request.getParameter("transactionTypeID") !=null && request.getParameter("transactionTypeID").length()>0)
		{
			lTransactionTypeID = Long.parseLong(request.getParameter("transactionTypeID"));
		}
		if(request.getParameter("dtExecuteFrom") !=null && request.getParameter("dtExecuteFrom").length()>0)
		{
			dtExecuteFrom = String.valueOf(request.getParameter("dtExecuteFrom"));
		}
		if(request.getParameter("dtExecuteTo") !=null && request.getParameter("dtExecuteTo").length()>0)
		{
			dtExecuteTo = String.valueOf(request.getParameter("dtExecuteTo"));
		}
				//构造查询参数
		OBInutWorkInfo qInfo = new OBInutWorkInfo();
		qInfo.setUserID(lUserID);
		qInfo.setOfficeID(lOfficeID);
		qInfo.setClientID(lClientID);
		qInfo.setCurrencyID(lCurrencyID);
		qInfo.setModuleID(lModuleID);
		qInfo.setTransactionTypeID(lTransactionTypeID);
		qInfo.setSessionMng(sessionMng);
		
		//查询并返回,collection的元素为OBInutWorkInfo
		OBQueryApprovalRecordBiz workBiz = new OBQueryApprovalRecordBiz();
		//Collection c_CurrentWork = workBiz.queryHistoryWork(qInfo);
		
		//翻页和排序
		String strTransDetailsURL = "";
        long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		long lOrder = 1;
		long lID=-1;
		String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
		
		if(request.getAttribute("lDesc") !=null)
		{
			lDesc = Long.parseLong((String)request.getAttribute("lDesc"));
		}
		
		Object[] queryResults = null;
		queryResults = (Object[])request.getAttribute(Constant.PageControl.SearchResults);
		Log.print("queryResults :  " + queryResults);
	
		String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		String strFailPageURL = (String)request.getAttribute("strFailPageURL");
		lDesc = (lDesc == Constant.PageControl.CODE_ASCORDESC_ASC? Constant.PageControl.CODE_ASCORDESC_DESC:Constant.PageControl.CODE_ASCORDESC_ASC);
		
		
		
%>

<jsp:include page="/ShowMessage.jsp"/>
<!-- 页面使用js声明 -->
<script language="JavaScript" src="/websys/js/Control.js" ></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>
<!-- 页面使用js声明结束 -->

<form name="frmCurrent" method="post" action="<%=strContext%>/approval/control/c034.jsp">
<input type="hidden" name="osTaskId">
<input type="hidden" name="osentryId">
<input type="hidden" name="osActionId">
<input type="hidden" name="osStepId">
<input type="hidden" name="operate">

<input type="hidden" name="strSuccessPageURL" value="ebank/approval/view/v034.jsp">
<input type="hidden" name="strFailPageURL" value="ebank/approval/view/v034.jsp">
<input type="hidden" name="lOrderField" value="">
<input type="hidden" name="lDesc" value="<%= lDesc %>">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="_pageLoaderKey"  value="<%= strPageLoaderKey %>" >

<TABLE width=80% border="1"  cellpadding="0" cellspacing="0" class=normal style="display:none">
<thead>
        		 <TR>
          			<TD height=22 width="10%"><DIV align=center>业务类型</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>业务编号</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>审批环节</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>流程名称</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>送办人</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>付款帐户号</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>收款帐户号</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>总金额</DIV></TD>         			         			          			
          			<TD height=22 width="10%"><DIV align=center>录入人</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>录入日期</DIV></TD>
          		 </TR>	
</thead>
<%	
		if(queryResults!=null && queryResults.length>0)
		{
		for( int i=0; queryResults != null && i<queryResults.length; i++ )
			{
				OBInutResultInfo workInfo = ( OBInutResultInfo ) queryResults[i];
%> 
				<TR>
					<TD  height=22 >
						<DIV align=center><%=OBConstant.SettInstrType.getName(workInfo.getTransactionTypeID()) %></DIV>
					</TD>
					<TD height=22 >
						<DIV align=center>
						<%
						String url = workInfo.getLinkUrl()+"&osentryId="+workInfo.getEntryID()+"&osStepId="+workInfo.getStepCode()+"&osTaskId=-1&strTempAction=finished";
						%>
							<A href="<%=url%>" target="_parent">
								<%=String.valueOf(workInfo.getFinanceinstrID()) %>
							</A>
						</DIV>
					</TD>
					<TD height=22 >
						<DIV align=center><%=workInfo.getStepName() %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=workInfo.getWfDefineName() %></DIV>
					</TD>
					<TD height=22 >
						<DIV align=center><%=NameRef.getUserNameByID(Long.parseLong(workInfo.getOwner())) %></DIV>
					</TD>
					<TD height=22 >
						<DIV align=center><%=workBiz.getPayAccountNOByIDForJSP(workInfo.getPayAccountID(),workInfo.getFixedInterestToAccountID(),workInfo.getTransactionTypeID()) %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=workBiz.getRecAccountNOByIDForJSP(workInfo.getReceiveAccountID(),workInfo.getFixedInterestToAccountID(),workInfo.getTransactionTypeID()) %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=DataFormat.formatDisabledAmount(workInfo.getPayAmount()) %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=NameRef.getUserNameByID(workInfo.getInputUserID()) %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=DataFormat.getDateString(workInfo.getExecute()) %></DIV>
					</TD>
          		</TR>
<%
			}
		}
		else
		{
%> 
				<TR>
					<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>         			         			          			
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          			<TD  height=20 width="10%"><DIV align=center>&nbsp;</DIV></TD>
          		</TR>	
<%
		}
%>           		

			<tr> 
				<td width="99%" border="0" valign="">
			         <TABLE border="0" cellPadding=1 height=20 width="80%" >
			         <TBODY>
			             <TR>
			                <TD height=20 width=99%>
			                    <DIV align=right> 
			                       <jsp:include page="/pagenavigator.jsp"  />  
			                  </DIV>
							</TD>
						  </TR>
					  </TBODY>
					  </TABLE>
				</td>
				</tr>


			</TABLE>
			</form>
	
	
		<!-- 分页控件 -->
 
  
	
<%
	/**
	* 显示文件尾
	*/
	OBHtml.showOBHomeEnd(out);
%>

<%
}
catch(IException ie)
{
	
	ie.printStackTrace();
	out.flush();
	return; 
}
%>	
<script language="JavaScript">
	function doQuery(form)
	{   
		frmCurrent.submit();
		showSending();//显示正在执行
	}
	
	function toApproval(id,entryId,actioncode,stepcode)
	{
	
		document.frmCurrent.action = "../../currentStep.do";
		document.frmCurrent.osTaskId.value = id ;
		document.frmCurrent.osentryId.value=entryId;
		document.frmCurrent.osActionId.value=actioncode;
		document.frmCurrent.osStepId.value=stepcode;
		document.frmCurrent.operate.value="toTransactionForm";
		document.frmCurrent.submit();
	}
</script>
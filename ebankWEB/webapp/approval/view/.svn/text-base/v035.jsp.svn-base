<!--
/**
 * 页面名称 ：v035.jsp
 * 页面功能 : 网银——办结业务查询显示页面
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
                 com.iss.itreasury.ebank.util.SessionOB,
                 com.iss.itreasury.ebank.util.OBHtml,
                 com.iss.itreasury.ebank.util.OBConstant,
                 com.iss.itreasury.util.Constant,
                 com.iss.itreasury.system.util.SYSHTML            
                 " 
                 %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.ListType" %>
<%@ page import="com.iss.itreasury.util.Constant.ModuleType" %><%@ page import="com.iss.itreasury.util.SessionMng" %><%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>		
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
String strTableTitle = null;
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
		
		if(request.getParameter("transactionTypeID") !=null && request.getParameter("transactionTypeID").length()>0)
		{
			lTransactionTypeID = Long.parseLong(request.getParameter("transactionTypeID"));
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
		Collection c_CurrentWork = workBiz.queryFinishedWork(qInfo);
%>

<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!-- 页面使用js声明 -->
<script language="JavaScript" src="/websys/js/Control.js" ></script>
<script language="JavaScript" src="/websys/js/Check.js"></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<!-- 页面使用js声明结束 -->

<form name="frmCurrent" method="post" action="v035.jsp">
<input type="hidden" name="osTaskId">
<input type="hidden" name="osentryId">
<input type="hidden" name="osActionId">
<input type="hidden" name="osStepId">
<input type="hidden" name="operate">

<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="txt_black">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="24" background="/webob/graphics/infor_til_bgpic.jpg">
		<table width="760" border="0" align="center" cellpadding="0" cellspacing="0" class="txt_black">
      		<tr>
        		<td width="180" class="txt_til2">办结业务查询</td>
        		<td width="580" align="right"></td>
      		</tr>
    	</table>
	</td>
  </tr>
  <tr>
    <td height="10"></td>
  </tr>
</table>

<TABLE border=0  height=18 width=774 align="center">
  	<TR>
    	<TD  height=2>
			<TABLE border="0" cellspacing="0" cellpadding="0" class=normal align="center" width="100%">
				<TR>						
					<TD height=25 width="2"></TD>
					<TD width="10%">业务类型：</TD>
					<TD>
<%
						workBiz.showAllRelatedTransTypeList(out,"transactionTypeID",lClientID,sessionMng.m_lCurrencyID,lTransactionTypeID,false,"",true);
%>					
					</TD>
					<TD>&nbsp;</TD>
					<TD>&nbsp;</TD>
					<TD width="2">&nbsp;</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	<TR>						
					<TD colspan="4" align="right" height=25>
						<INPUT class="button1" name=toQuery type="button" value=" 查询 " onClick="doQuery();" onkeydown="if(event.keyCode == 13) doQuery();">
					&nbsp;&nbsp;</TD>				
	</TR>
	<TR>
		<TD height=20 vAlign=top>
		</TD>
	</TR>
  	<TR>
    	<TD height=3 vAlign=top>
      		  <table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal >
    			<thead>
        		 <TR>
          			<TD height=22 width="10%"><DIV align=center>业务类型</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>交易编号</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>审批环节</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>流程名称</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>送办人</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>付款帐户号</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>收款帐户号</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>交易金额</DIV></TD>         			         			          			
          			<TD height=22 width="10%"><DIV align=center>录入人</DIV></TD>
          			<TD height=22 width="10%"><DIV align=center>交易日期</DIV></TD>
          		 </TR>	
				</thead>
<%	
		if( c_CurrentWork != null && c_CurrentWork.size()>0 )
		{
			Iterator it = c_CurrentWork.iterator();
			while (it.hasNext() )
			{
				OBInutWorkInfo workInfo = ( OBInutWorkInfo ) it.next();
%> 
				<TR>
					<TD  height=22 >
						<DIV align=center><%=OBConstant.SettInstrType.getName(workInfo.getTransactionTypeID()) %></DIV>
					</TD>
					<TD height=22 >
						<DIV align=center>
							<A href="#" onclick="toApproval(<%=workInfo.getInutWorkInfo().getTaskID()%>,<%=workInfo.getInutWorkInfo().getEntryID() %>,<%=workInfo.getInutWorkInfo().getActionCode() %>,<%=workInfo.getInutWorkInfo().getStepCode() %>)">
								<%=workInfo.getTransNo() %>
							</A>
						</DIV>
					</TD>
					<TD height=22 >
						<DIV align=center><%=workInfo.getInutWorkInfo().getStepName() %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=workInfo.getInutWorkInfo().getWfDefineName() %></DIV>
					</TD>
					<TD height=22 >
						<DIV align=center><%=NameRef.getUserNameByID(Long.parseLong(workInfo.getInutWorkInfo().getOwner())) %></DIV>
					</TD>
					<TD height=22 >
						<DIV align=center><%=DataFormat.formatString(workInfo.getPayAccountNo()) %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=DataFormat.formatString(workInfo.getReceiveAccountNo()) %></DIV>
					</TD>
					<TD  height=22 >
						<DIV align=center><%=DataFormat.formatDisabledAmount(workInfo.getPayAmount()>0?workInfo.getPayAmount():workInfo.getReceiveAmount()) %></DIV>
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
			</TABLE>
		</TD>
	</TR>
</TABLE>		
</form>		
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
<%@ include file="/common/SignValidate.inc" %>
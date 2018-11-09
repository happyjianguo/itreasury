<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:directive.page import="com.iss.itreasury.util.Env"/>
<%--
/**页面功能说明
 * 页面名称 ：v090.jsp
 * 页面功能 : 账户对账单
 * 作    者 ：zcwang
 * 日    期 ：2008-4-29
 * 简单实现说明：
 * 1、查询条件录入
 * 特殊说明 ：
 * 修改历史 ：
 */
--%>
<!--类导入部分开始-->
<%@ page contentType = "text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.*" %>

<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<!--类导入部分结束-->
<%
	//String clientID = request.getParameter("clientid");
	//String officeid = request.getParameter("officeid");
	//String moduleid = request.getParameter("moduleid");
	//session.setAttribute("eofficeID",String.valueOf(officeid).toString());
	//session.setAttribute("emoduleid",String.valueOf(moduleid).toString());
	//session.setAttribute("eclientid",String.valueOf(clientID).toString());
	
    try
	{
		//emoduleid等于6代表网银模块
		//if ( officeid == null || !moduleid.equals("6") || clientID ==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
       // 		out.flush();
		//		return;
		//}
		String strTitle = null;
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
        
		/**页面校验开始(用户登录校验、用户权限校验、重复请求校验)**/
		JSPLogger.info("****************进入页面--bankportal\\v090-account_ebank.jsp");
		
		/**页面校验结束**/
		
		/**业务逻辑开始**/
		
		long clientId = -1;
		
		
		clientId = sessionMng.m_lClientID;
		
		QueryBillPrintParam queryInfo    = new QueryBillPrintParam();
		queryInfo.convertRequestToDataEntity(request);
		if(clientId == -1) clientId = queryInfo.getClientIdFrom();
		
		/**业务逻辑结束**/
		
		/**页面显示开始*/
   // HTMLHelper.showHomeHead(out,sessionMng,"账户对账单",BooleanValue.TRUE);//显示页面头
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>



<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webbp/js/Check.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<!--引入js文件-->
<script type="text/javascript" src="/webob/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/webob/js/flexigrid.js"></script>
<script type="text/javascript" src="/webob/js/flexigridEncapsulation.js"></script>
<script type="text/javascript" src="/webob/js/suggest.js"></script>
<script type="text/javascript" src="/webob/js/jquery-ui-1.7.2.custom.min.js"></script>


<!--页面表单开始-->
<form name="frmV090" method="post" action="">
<!--页面控制元素开始-->
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="p_Action" type="hidden" value="findFirst">
	<input name="p_SuccessPageURL" type="hidden" value="/bankportal/v091-account_ebank.jsp">
	<input name="p_FailPageURL" type="hidden" value="/bankportal/v090-account_ebank.jsp">
	<input name="systemDate" type="hidden" value="<%=Env.getSystemDateString()%>">
	<input name="isCheck" type="hidden" value="1"/>
	<input name="isDirectLink" type="hidden" value="1"/>		
	<input name="accountStatus" type="hidden" value="1"/>	
	<input name="clientId" type="hidden" value="<%=clientId%>"/>	
	<input name="lUserID" type="hidden" value="<%=sessionMng.m_lUserID %>"/>
	<input name="lCurrencyID" type="hidden" value="<%=sessionMng.m_lCurrencyID %>"/>
<!--页面控制元素结束-->
<table width="98%" cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="24">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">账户对账单</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
<br/>
 <table width=100% border="0" cellpadding="0" cellspacing="1" class=normal id="table1">		
  		<TBODY>
  		<TR>
    		<TD height=63>      
      			<TABLE align=center border=0 height=80 width="90%" >
        			<TBODY> 
        				<TR>
							<TD borderColor=#E8E8E8 width="19%">&nbsp;&nbsp;客户编号：</TD>
							<TD width="38%"><input type=text class = box name="text" value ="<%=NameRef.getClientCodeByID(clientId)%>" disabled >				
							</TD>
		    				<TD borderColor=#E8E8E8 width="15%"></TD>
            				<TD borderColor=#E8E8E8 width="28%"></TD>
						</TR>			   						 
						 <TR>
							<TD borderColor=#E8E8E8 >&nbsp;&nbsp;银行：</TD>
            				<TD borderColor=#E8E8E8 >
								<%
								   SETTConstant.BankType.showList4ebank(out,"bankType",0,"",false,false," onFocus=\"nextfield='accountIdCtrl';\" ",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
							    %>
							</TD>
							<TD borderColor=#E8E8E8 ></TD>
            				<TD borderColor=#E8E8E8 ></TD>
						 </TR>
					  <TR>						  											
							<%
							String strFormName="frmV090";
								String strCtrlName = "accountId";
								long lAccountID = queryInfo.getAccountId();
								String strAccountNo = NameRef.getAccountNOByAccountID(lAccountID);
								String strAccountName = NameRef.getAccountNameByAccountID(lAccountID);
								String strRtnAccountNameCtrlName= "accountName";
								String[] sNextControls = new String[]{"transactionStartDateString"};
								String[] sConditionCtrlName = new String[15];
								sConditionCtrlName[6]="isDirectLink";
								sConditionCtrlName[10]="isCheck";
								sConditionCtrlName[11]="accountStatus";
								sConditionCtrlName[12]="bankType";
								sConditionCtrlName[0]="clientId";
								sConditionCtrlName[13]="lUserID";
								sConditionCtrlName[14]="lCurrencyID";
																				
								/*		OBMagnifier.createAccountCtrlReturnCodeByAuthority(
								out,
								strFormName,
								strCtrlName,
						        lAccountID,
								strAccountNo,
								strRtnAccountNameCtrlName,
								sNextControls,
								sConditionCtrlName,
								sessionMng.m_lOfficeID);*/
							%>		
							<input type="hidden" name='lOfficeID' value=<%=sessionMng.m_lOfficeID %>>
							   <td width="130" height="25" align="left">&nbsp;&nbsp;账号：</td>
							   <td>
									<fs_c:dic id="accountIdCtrl" size="22" form="frmV090" title="账号" sqlFunction="getAccountSQLByAuthority"  sqlParams='frmV090.accountIdCtrl.value,frmV090.lOfficeID.value,frmV090.clientId.value,-1,-1,-1,-1,-1,frmV090.isDirectLink.value,-1,-1,-1,frmV090.isCheck.value,frmV090.accountStatus.value,frmV090.bankType.value,frmV090.lUserID.value,frmV090.lCurrencyID.value' value="<%=NameRef.getAccountNameByAccountID(lAccountID)%>" nextFocus="transactionStartDateString" width="650">
										<fs_c:columns> 
											<fs_c:column display="账号" name="AccountNO" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="账户名称" name="AccountName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="银行名称" name="BankName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="accountIdCtrl" name="AccountNO" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="accountName" name="AccountName" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="accountId" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic>
									<input type="hidden" name='AccountID' value="<%=String.valueOf(lAccountID) %>">
							   </td>	
							   <td></td>
							   <td></td>																			
         				  </TR>	
         				  <tr>
         				  	<td borderColor=#E8E8E8 >&nbsp;&nbsp;账户名称：</td>
							<td borderColor=#E8E8E8 >
								<input class=box type=text name="accountName" value="<%=strAccountName%>" size=35 readonly>
							</td>
							<TD borderColor=#E8E8E8 ></TD>
            				<TD borderColor=#E8E8E8 ></TD>
         				  </tr>					  
	  				  </TBODY>
	              </TABLE>
   			  </TD>
				<td width="5" height="25" colspan="3"  class="MsoNormal"></td>
          </TR> 
		   <TR>
    		<TD height=20>      
      			<TABLE align=center border=0 height=20 width="95%">
        			<TBODY> 
					  <TR>
						<TD colspan="4"><hr></TD>
					  </TR>
					  </TBODY>
	              </TABLE>
   			  </TD>
   			  <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
          </TR>
		  
		  
		  <TR>
    		<TD height=20>      
      			<TABLE align=center border=0 height=20 width="90%">
        			<TBODY> 
					  <TR>
						<TD width="14%"><font color="#CC0000">* </font>查询日期：</TD>
						<TD width="5%" align="right">由</td>
						<td width="38%">
							 <fs_c:calendar 
				          	    name="transactionStartDateString"
					          	value="" 
					          	properties="nextfield ='transactionEndDateString'" 
					          	size="20"/>
					         	  <script>
					          		$('#transactionStartDateString').val('<%=HtmlKit.getStringFromInfo(queryInfo, "transactionStartDate",-1)%>');
					          	</script>
						</TD>
						<TD width="5%" align="right">至</td>
						<td width="38%">
						 	<fs_c:calendar 
				          	    name="transactionEndDateString"
					          	value="" 
					          	properties="nextfield ='butSearch'" 
					          	size="20"/>
					        <script>
				          		$('#transactionEndDateString').val('<%=HtmlKit.getStringFromInfo(queryInfo, "transactionEndDate",-1)%>');
				          	</script>
						</TD>
					  </TR>
					  </TBODY>
	              </TABLE>
   			  </TD>
   			  <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
   			  
          </TR>
		
		
        <TR borderColor=#ffffff>
            <TD colspan="3" align=right> 
              <input class=button1 type=button name="butSearch" value=" 查 找 " onClick="doSearch();" onfocus="nextfield='submitfunction';">
            </TD>
			<TD align=right>&nbsp;</TD>
        </TR>
		<TR borderColor=#ffffff>
            <TD colspan="4" align=right>&nbsp;</TD>
        </TR>
		
	</TBODY>
</TABLE>
</td></tr></table></form>
<!--页面表单结束-->

<!--页面脚本开始-->
<script language="JavaScript">
	firstFocus(document.frmV090.bankType);
	//setSubmitFunction("doSearch(frmV090)");
	setFormName("frmV090");
</script>

<script language="javascript">
function doSearch()
{
	//if (!validateFields(frmV090)) return;
	if( !(compareDate(frmV090.transactionStartDateString.value,frmV090.transactionEndDateString.value)) )
	{
		alert("查询起始日期不能大于查询截至日期！");
		return false;
	}
	if( !(compareDate(frmV090.transactionEndDateString.value,frmV090.systemDate.value)) )
	{
		alert("截至日期不能大于系统日期！");
		return false;
	}
	
	frmV090.action = "<%=strContext%>/bankportal/c090-account_ebank.jsp"
	frmV090.submit();
}    
/**
 * 所有需要校验的栏位
 */
function allFields()
{
	this.ah = new Array("accountId","账号","magnifier",0);
	this.ai = new Array("transactionStartDateString","查询起始日期","date",1);
	this.aj = new Array("transactionEndDateString","查询截至日期","date",1);
}
</script>
<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	OBHtml.showOBHomeEnd(out);
	/**页面显示结束*/
%>
<jsp:include page="/ShowMessage.jsp"/>
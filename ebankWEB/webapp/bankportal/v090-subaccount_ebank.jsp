<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/**页面功能说明
	 * 页面名称 ：v090.jsp
	 * 页面功能 : 账户对账单
	 * 作    者 ：zcwang
	 * 日    期 ：2008-4-29
	 * 简单实现说明：
	 *				1、查询条件录入
	 * 特殊说明 ：
	 * 修改历史 ：
	 */
%>
<!--类导入部分开始-->
<jsp:directive.page import="com.iss.itreasury.util.DataFormat" />
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:directive.page import="com.iss.itreasury.util.Env" />
<jsp:directive.page import="com.iss.itreasury.ebank.system.util.HtmlKit" />
<%@ page contentType="text/html;charset=GBK"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<jsp:directive.page
	import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam" />
<%
	String strContext = request.getContextPath();
%>
<!--类导入部分结束-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%
	//String clientID = request.getParameter("clientid");
	//String officeid = request.getParameter("officeid");
	//String moduleid = request.getParameter("moduleid");
	//session.setAttribute("eofficeID",String.valueOf(officeid).toString());
	//session.setAttribute("emoduleid",String.valueOf(moduleid).toString());
	//session.setAttribute("eclientid",String.valueOf(clientID).toString());
	try {
		//emoduleid等于6代表网银模块
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
		// 		out.flush();
		//		return;
		//}
		String strTitle = null;
		//用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
					"Gen_E003");
			out.flush();
			return;
		}
		/**页面校验开始(用户登录校验、用户权限校验、重复请求校验)**/
		JSPLogger
				.info("****************进入页面--bankportal\\v090-subaccount_ebank.jsp");

		/**页面校验结束**/

		/**业务逻辑开始**/

		long clientId = -1;
		long subclientId = -1;
		/*
		try{
			String strTemp = (String)session.getAttribute("eclientid");
			if(strTemp!=null && strTemp.length()>0)
			{
		  	 	clientId = Long.parseLong(strTemp);
		    }
		   
		}catch(Exception ex){
		}
		 */
		clientId = sessionMng.m_lClientID;
		QueryBillPrintParam queryInfo = new QueryBillPrintParam();
		queryInfo.convertRequestToDataEntity(request);
		if (clientId == -1)
			clientId = queryInfo.getClientIdFrom();

		/**业务逻辑结束**/

		/**页面显示开始*/
		// HTMLHelper.showHomeHead(out,sessionMng,"账户对账单",BooleanValue.TRUE);//显示页面头
%>

<jsp:include page="/ShowMessage.jsp" />

<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webbp/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script type="text/javascript" src="/webob/js/jquery-1.4.2.min.js"></script>
	  <script type=text/javascript src=/webob/js/wpCalendar.js></script>
	  
	  <script type="text/javascript" src="/webob/js/flexigrid.js"></script>
<script type="text/javascript" src="/webob/js/flexigridEncapsulation.js"></script>
<script type="text/javascript" src="/webob/js/suggest.js"></script>
<script type="text/javascript" src="/webob/js/jquery-ui-1.7.2.custom.min.js"></script>
<link rel="stylesheet" href="/webob/css/jquery-ui-1.7.2.custom.css" type="text/css">
<link rel="stylesheet" href="/webob/css/suggest.css" type="text/css">
<link rel="stylesheet" href="/webob/css/flexigrid.css" type="text/css">
	  

<!--引入js文件-->

<!--页面表单开始-->
<form name="frmV090" method="post" action="">
	<!--页面控制元素开始-->
	<input name="ActionID" type="hidden"
		value="<%=new java.util.Date().getTime()%>">
	<input name="p_Action" type="hidden" value="findFirst">
	<input name="p_SuccessPageURL" type="hidden"
		value="/bankportal/v091-subaccount_ebank.jsp">
	<input name="p_FailPageURL" type="hidden"
		value="/bankportal/v090-subaccount_ebank.jsp">
	<input name="systemDate" type="hidden"
		value="<%=DataFormat.formatDate(Env.getSystemDateTime(), 1)%>">
	<!----账号放大镜默认的匹配值---->
	<input name="clientId" type="hidden" value="<%=clientId%>">
	<input name="countryId" type="hidden" value="-1">
	<input name="isCheck" type="hidden" value="1">
	<input name="accounttype" type="hidden" value="1">
	<input name="isDirectLink" type="hidden" value="1">
	<input name="accountStatus" type="hidden" value="1">
	<input name="officeID" type="hidden"
		value="<%=(String) session.getAttribute("eofficeID")%>">
	<!--页面控制元素结束-->
	<input type="hidden" name="m_lOfficeID" id="m_lOfficeID" value="<%=sessionMng.m_lOfficeID %>"/>
		<input type="hidden" name="m_lCurrencyID" id="m_lCurrencyID" value="<%=sessionMng.m_lCurrencyID%>" />
		<input type="hidden" name="m_lClientID" value="<%=sessionMng.m_lClientID%>" />
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title  nowrap ><span class="txt_til2">下属单位银行账户</span></td>
			       <td class=title_right   width="17"><a class=img_title></td>
				</TR>
			</TABLE>
			
		<br/>
		
		<table width=100% border="0 cellpadding="0" cellspacing="1" class=normal id="table1">	
  		<TBODY>
  		<TR>
    		<TD height=63>      
      			<TABLE align=center border=0 height=80 width="90%" >
        			<TBODY> 
        				<TR>
        					  		
								<%
										long officeId = sessionMng.m_lOfficeID;
										long currencyId = sessionMng.m_lCurrencyID;
										String strFormName = "frmV090";
										String strCtrlName = "subclientId";
										strTitle = "下属单位编号";
										String strRtnAccountNameCtrlName = "";
										String strClientCode = "";
									//	long lParentClientID = clientId;
										String strFirstTD = "";// 第一个TD的属性
										String strSecondTD = "";// 第二个TD的属性
										String[] strNextControls = new String[] { "subaccountIdCtrl" };

									/*	OBMagnifier
												.createChildClientCtrl(out, 
														officeId, 
														currencyId,
														strFormName, 
														strCtrlName, 
														strTitle,
														subclientId, 
														strClientCode, 
														clientId,
														strFirstTD, 
														strSecondTD, 
														strNextControls,
														strRtnAccountNameCtrlName);*/
								%>
								 <td width="15%" height="25" align="left">&nbsp;&nbsp;下属单位编号：</td>
							  	 <td >
							
									<fs_c:dic id="subclientIdCtrl" size="22" form="frmV090" title="下属单位编号" sqlFunction="getChildClientSQL"  sqlParams='frmV090.m_lOfficeID.value,frmV090.m_lClientID.value,frmV090.subclientIdCtrl.value' value="<%=strClientCode%>" nextFocus="subaccountIdCtrl" width="500">
										<fs_c:columns> 
											<fs_c:column display="客户编号" name="clientNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="客户名称" name="clientName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="subclientIdCtrl" name="clientNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="subclientId" name="ClientID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="subclientIdFromClient" name="FromClient" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic> 
									<input type="hidden" value="" name="subclientId" value="<%=subclientId %>"/>
									<input type="hidden" value="" name="subclientIdFromClient" value="<%=subclientId > 0?1:0 %>"/>
							     </td>			
						</TR>
			    
						
					  <TR>						  
							<%
	  								strFormName = "frmV090";
	  									strCtrlName = "subaccountId";
	  									strRtnAccountNameCtrlName = "";
	  									long lAccountId = -1;
	  									String strAccountCode = "";
	  									strNextControls = new String[] { "transactionStartDateString" };

	  									//放大镜过滤字段：
	  									//客户编号 cong、客户编号 到、国家id、银行id、币种id、所有者类型id、收支属性id、是否直连、账户属性一、二、三
	  									//clientId,countryId,bankId,currencyType,ownerType,inputOrOutput,isDirectLink,
	  									//accountPropertyOne,Two,Three,isCheck,accountStatus
	  									String[] sConditions = new String[] { "subclientId",
	  											"countryId", null, null, null, null, "isDirectLink",
	  											null, null, null, "isCheck", "accountStatus" };

	  							/*		OBMagnifier.createSubAccountCtrlReturnCode(out, 
	  											strFormName,
	  											strCtrlName, 
	  											lAccountId, 
	  											strAccountCode,
	  											strRtnAccountNameCtrlName, 
	  											strNextControls,
	  											sConditions, 
	  											officeId, 
	  											clientId);*/
	  							%>
	  							 <td width="130" height="25" align="left">&nbsp;&nbsp;下属账号：</td>
							  	 <td>
									<fs_c:dic id="subaccountIdCtrl" size="22" form="frmV090" title="下属账号" sqlFunction="getSubAccountSQLForEbank"  sqlParams='frmV090.subaccountIdCtrl.value,frmV090.m_lClientID.value,frmV090.m_lOfficeID.value,frmV090.subclientId.value,frmV090.countryId.value,-1,-1,-1,-1,frmV090.isDirectLink.value,-1,-1,-1,frmV090.isCheck.value,frmV090.accountStatus.value' value="<%=strAccountCode%>" nextFocus="transactionStartDateString" width="650">
										<fs_c:columns> 
											<fs_c:column display="账号" name="AccountNO" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="账户名称" name="AccountName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="银行名称" name="BankName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="subaccountIdCtrl" name="AccountNO" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="subaccountId" name="AccountID" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>	
									</fs_c:dic> 
									<input type="hidden" name="subaccountId" value="<%=String.valueOf(lAccountId) %>">									
							     </td>		
         				  </TR>						  
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
						<TD width="10%"><font class=txt_red >* </font>查询日期：</TD>
						<TD width="5%" align="right">由</td>
						<td width="40%">
						 <fs_c:calendar 
			          	    name="transactionStartDateString"
				          	value="" 
				          	properties="nextfield ='transactionEndDateString'" 
				          	size="20"/>
							<script>
				          		$('#transactionStartDateString').val('<%=HtmlKit.getStringFromInfo(queryInfo,
											"transactionStartDate", -1)%>');
				          	</script>
						</TD>
						<TD width="5%" align="right">至</td>
						<td width="40%">
							 <fs_c:calendar 
				          	    name="transactionEndDateString"
					          	value="" 
					          	properties="nextfield ='butSearch'" 
					          	size="20"/>
						    <script>
				          		$('#transactionEndDateString').val('<%=HtmlKit.getStringFromInfo(queryInfo,"transactionEndDate", -1)%>');
				          	</script>
						</TD>
						<TD></TD>
					  </TR>
					  
					  </TBODY>
	              </TABLE>
   			  </TD>
   			  <td width="5" height="25" colspan="3"  class="MsoNormal"></td>
          </TR>
		
		
        <TR>
            <TD colspan="3" align=right> 
              <input class=button1 type=button name="butSearch" value=" 查 找 " onClick="doSearch();" onfocus="nextfield='submitfunction';">
            </TD>
			<TD align=right>&nbsp;</TD>
        </TR>
		<TR>
            <TD colspan="4" align=right>&nbsp;</TD>
        </TR>
		
	</TBODY>
</TABLE>
</td></tr></table>
</form>
<!--页面表单结束-->

<!--页面脚本开始-->
<script language="JavaScript">
	firstFocus(document.frmV090.subclientIdCtrl);
	//setSubmitFunction("doSearch(frmV090)");
	setFormName("frmV090");
</script>

<script language="javascript">
function doSearch()
{
	if (!validateFields(frmV090)) return;
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
	
	frmV090.action = "<%=strContext%>/bankportal/c090-subaccount_ebank.jsp"
	frmV090.submit();
}    
/**
 * 所有需要校验的栏位
 */
function allFields()
{
	this.ad = new Array("subclientId","下级客户编号","magnifier",0);
	this.ah = new Array("subaccountId","下级账号","magnifier",0);
	this.ai = new Array("transactionStartDateString","查询起始日期","date",1);
	this.aj = new Array("transactionEndDateString","查询截至日期","date",1);
}
</script>
<!--页面脚本元素结束-->
<%
	} catch (Exception exp) {
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	OBHtml.showOBHomeEnd(out);
	/**页面显示结束*/
%>
<%--
/*
 * 程序名称：check_v014.jsp
 * 功能说明：新奥项目活期业务批量复核
 * 作　　者：wangzhen
 * 完成日期：2011年04月06日
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"%>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.*"%>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<head><link rel="stylesheet" href="/webob/css/style.css" type="text/css"></head>
<% 
    try {
    
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
        
        String strCurrency = sessionMng.m_strCurrencySymbol;
        String systemDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
        
        String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
        
        String ordertype = (String)request.getAttribute("ordertype");
		JSPLogger.info("****************进入页面--/ebank/webapp/capital/check/view/check_v005.jsp");
		
        long lClientID = sessionMng.m_lClientID;    //客户ID
        long lTransType = -1;           //交易类型
        long NSTATUS = -1;              //状态
        String sStartSubmit = "";       //提交日期-从
        String sEndSubmit = "";         //提交日期-到
        double dMinAmount = 0.0;        //交易金额-最小值
        double dMaxAmount = 0.0;        //交易金额-最大值
        String sStartExe = "";          //执行日期-从
        String sEndExe = "";            //执行日期-到
        sStartSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-从
        sEndSubmit = DataFormat.getDateString(Env.getSystemDateTime()); //提交日期-到
      	sStartExe = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);//执行日期-从
        sEndExe = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);//执行日期-从
        //从请求中获取查询结果信息
		Query_FinanceInfo  info = null;
		Query_FinanceInfo[] queryResults = (Query_FinanceInfo[])request.getAttribute(Constant.PageControl.SearchResults);
		Query_FinanceInfo queryCondition = (Query_FinanceInfo)sessionMng.getQueryCondition(strPageLoaderKey);
		Log.print("queryResults :  " + queryResults);
		
		 if(request.getParameter("dMinAmount") != null && request.getParameter("dMinAmount").trim().length() > 0) {
       dMinAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("dMinAmount")));
       }
        if(request.getParameter("dMaxAmount") != null && request.getParameter("dMaxAmount").trim().length() > 0) {
       dMaxAmount=Double.parseDouble(DataFormat.reverseFormatAmount((String) request.getParameter("dMaxAmount")));
       }
       if(request.getParameter("sStartExe") != null ) {
       sStartExe= request.getParameter("sStartExe");
       }
       if(request.getParameter("sEndExe") != null ) {
       sEndExe= request.getParameter("sEndExe");
       }
       if(request.getParameter("sStartSubmit") != null) {
       sStartSubmit= request.getParameter("sStartSubmit");
       }
       if(request.getParameter("sEndSubmit") != null ) {
       sEndSubmit= request.getParameter("sEndSubmit");
       }
  
        if(request.getParameter("NSTATUS") != null && request.getParameter("NSTATUS").trim().length() > 0) {
            NSTATUS = Long.parseLong(request.getParameter("NSTATUS")); // 交易指令状态
            Log.print("交易指令状态=" + NSTATUS);
        }
        if(request.getParameter("lTransType") != null && request.getParameter("lTransType").trim().length() > 0) {
            lTransType = Long.parseLong(request.getParameter("lTransType")); // 交易指令状态
            Log.print("交易类型=" + lTransType);
        }
          
      	if(queryCondition!=null)
		{
			lTransType = queryCondition.getNtranstype();       //网上银行交易类型
			NSTATUS = queryCondition.getNSTATUS();             //交易指令状态
			sStartSubmit = queryCondition.getStartSubmit();   //提交日期-从
			sEndSubmit = queryCondition.getEndSubmit();       //提交日期-到
			dMinAmount = queryCondition.getMinAmount();       //交易金额-最小值      
            dMaxAmount = queryCondition.getMaxAmount();       //交易金额-最大值
            sStartExe = queryCondition.getStartExe();         //执行日期-从       
            sEndExe = queryCondition.getEndExe();             //执行日期-到   
		}
          
          
		String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		String strFailPageURL = (String)request.getAttribute("strFailPageURL");
        OBHtml.showOBHomeHead(out, sessionMng, "", 1);
        // 系统时间
        Timestamp dtSysDate = Env.getSystemDateTime();
        String strNote = "";
%>
<jsp:include page="/ShowMessage.jsp"/>
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webbp/js/Check.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/safety/js/fgVilidate.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td>
<form name="frmjysqcx" method="post" action="">
	<input type="hidden" name="lClientID" value="<%=lClientID%>">
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="p_Action" type="hidden" value="findFirst">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="_pageLoaderKey" value="<%=strPageLoaderKey %>">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="ordertype" value="<%=ordertype==null?"asc":ordertype%>">
	<input type="hidden" name="orderfield" value="">
	<input name="systemDate" type="hidden" value="<%=Env.getSystemDateString()%>">
	<input name="isCheck" type="hidden" value="1"/>
	<input name="isDirectLink" type="hidden" value="1"/>		
	<input name="accountStatus" type="hidden" value="1"/>
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="sign" type="hidden" value=""/>	
		
	<!--start  指纹认证相关html -->
  	<input name="Ver" id="Ver" type="hidden" value="">
    <!--end  指纹认证相关html -->
	
	
<table width=100% cellpadding="0" cellspacing="0" class="title_top">
  <tr>
    <td height="24">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">批量复核-活期</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
         <tr>
          <td colspan="6" height="5"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" class="graytext" colspan="2">交易类型：</td>
          <td height="25" class="graytext" colspan="3">
         <%
		OBFinanceInstrDao dao = new OBFinanceInstrDao();
		OBConstant.SettInstrType.showList(out,"lTransType",2,lTransType,true,false,"   onfocus=\"nextfield ='NSTATUS';\" ",null,false);

            //业务
            
%>
           </td>
          <td width="8" height="25"></td>
        </tr>
		<tr >
          <td colspan="6" height="1"></td>
        </tr>
		<tr id="commonStatus">
			<td width="5" height="25"></td>
			<td height="25" class="graytext" colspan="2">状态：</td>
			<td height="25" class="graytext" colspan="3">
<%
			//状态
		OBHtmlCom.showCheckStatusListControl(
										out,
										"NSTATUS",
										NSTATUS,
										" onfocus=\"nextfield ='sStartSubmit';\" "
									);
%>
			</td>
			<td width="8" height="25"></td>
		</tr>

        <tr  id="commonStatusLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="submitDate">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext" >提交日期：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext">
          	     <fs_c:calendar 
			          	    name="sStartSubmit"
				          	value="" 
				          	properties="nextfield ='sEndSubmit'" 
				          	size="18"/>
				    	  <script>
	          		$('#sStartSubmit').val('<%=sStartSubmit%>');
	          	</script>
          </td>
          <td width="98" height="25" class="graytext" align="right">
            <span class="graytext">至</span>
            </td>
            <td width="330" height="25" class="graytext">
            	<fs_c:calendar 
			          	    name="sEndSubmit"
				          	value="" 
				          	properties="nextfield ='dMinAmount'" 
				          	size="18"/>
			 <script>
	          		$('#sEndSubmit').val('<%=sEndSubmit%>');
	          	</script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="submitDateLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="transBalance">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">金额：</td>
          <td width="67" height="25" class="graytext" >
            <div align="right">最小值<%= strCurrency /*金额最小值*/%></div>
          </td>
          <td width="188" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMinAmount",
                    '<%=dMinAmount==0.0?"":DataFormat.formatDisabledAmount(dMinAmount)%>',
                    "dMaxAmount",
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
            </td>
            <td width="98" height="25" class="graytext" colspan="1" align="right">
            <span class="graytext">最大值<%= strCurrency /*金额最大值*/%></span>
            </td>
            <td width="330" height="25" class="graytext" colspan="1">
            <script  language="JavaScript">
                createAmountCtrl(
                    "frmjysqcx",
                    "dMaxAmount",
                    '<%=dMaxAmount==0.0?"":DataFormat.formatDisabledAmount(dMaxAmount)%>',
                    "sStartExe",
                    "",
                    <%=sessionMng.m_lCurrencyID%>
                );
            </script>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="transBalanceLine">
          <td colspan="6" height="1"></td>
        </tr>
        <tr  id="executeDate">
          <td width="5" height="25"></td>
          <td width="78" height="25" class="graytext">执行日期：</td>
          <td width="67" height="25" class="graytext">
            <div align="right">由</div>
          </td>
          <td width="188" height="25" class="graytext" >
          	     <fs_c:calendar 
			          	    name="sStartExe"
				          	value="<%=sStartExe%>" 
				          	properties="nextfield ='sEndExe'" 
				          	size="18"/>
          </td>
          <td width="98" height="25" class="graytext" align="right">
            <span class="graytext">至</span>
            </td>
            <td width="330" height="25" class="graytext">
            	<fs_c:calendar 
		          	    name="sEndExe"
			          	value="<%=sEndExe%>" 
			          	properties="nextfield ='butSearch'" 
			          	size="18"/>
          </td>
          <td width="8"></td>
        </tr>
        <tr  id="executeDateLine">
          <td colspan="6" height="1"></td>
        </tr>
      <tr>
          <td  colspan="6">
            <div align="right">
			  <input  class=button1 type="button" name="butSearch" value=" 查 找 " onClick="doSearch();" onfocus="nextfield='';">
				&nbsp;&nbsp;</div>
          </td>
      </tr>
        <tr>
          <td colspan="6" height="5"></td>
        </tr>
      </table>
    <br>


  <table width=100% border="1"  cellpadding="0" cellspacing="0" class=normal >
  <thead>
  
    <tr>
		<td width="40" align="center" height="18" rowspan="2" nowrap><div>全选</div>
		<%
			if (queryResults != null && queryResults.length > 0) {
			%>
				<input type="checkbox" onclick="checkAll(this)" name="lID" value="on"> </td>
			<%
			}else{
			%>
				<input type="checkbox" onclick="checkAll(this)" name="lID" value="on" disabled="disabled"> </td>
			<%
			}
		%>
		<td align="center" height="18" rowspan="2" nowrap><div>指令序号</div></td>
        <td align="center" height="18" rowspan="2" nowrap>交易类型</td>
        <td align="center" height="18" rowspan="2" nowrap><div>账户号</div></td>
		<td align="center" rowspan="2" nowrap><div>借贷方向</div></td>
		<td align="center" rowspan="2" nowrap><div>金额</div></td>
		<td align="center" colspan="3" nowrap><div>对方资料</div></td>
		<td align="center" height="18" rowspan="2" nowrap><div>状态</div></td>
		<td align="center" height="18" rowspan="2" nowrap><div>汇款用途</div></td>
	    <td align="center" height="18" rowspan="2" nowrap><div>执行日期</div></td>
		<td align="center" height="18" rowspan="2" nowrap><div>提交日期</div></td>
	</tr>
	<tr border="0">
		<td  align="center" nowrap><div>账户</div></td>
		<td  align="center" nowrap><div>账号名称</div></td>
		<td  align="center" nowrap><div>汇入行</div></td>
	</tr>
	</thead>
	 <%
	            
				if (queryResults != null && queryResults.length > 0) 
				{
					info  = new Query_FinanceInfo();
					for( int i=0; queryResults != null && i < queryResults.length; i++ )
					{
						info = (Query_FinanceInfo)queryResults[i];
						//text-algin:center margin:0 auto
						strNote = info.getNote()== null?"":info.getNote().trim();
%>
			<tr> 
				<td width="40" align="center" nowrap><input type="checkbox" name="lID1" value="<%=info.getID()%>####<%=info.getMAmount() %>####<%=info.getDtModify() %>" onclick="dosum();isCheckedAll();"></td>
				
				<input type="hidden" name="dtmodify" size="12" value="<%=info.getDtModify()==null?"":info.getDtModify()+"" %>">
				<td width="65" align="center" nowrap>
					<a href="#" onclick = "toDetail(<%=info.getNtranstype()%>,<%=info.getID()%>);"><%=info.getID()%></a>
				</td>
				<td height="20" ><DIV align=center><%=OBConstant.SettInstrType.getName(info.getNtranstype())%></DIV></td>
				<td height="20" ><DIV align=center><%= info.getSaccountno()%></DIV></td> 
				<td height="20" ><DIV align=center>借</DIV></td>
				<td height="20" ><DIV align=right><%=strCurrency%><%=info.getFormatMAmount()%></DIV></td>
				<%if(info.getNtranstype()==OBConstant.SettInstrType.DRIVEFIXDEPOSIT){ %>
				<td height="20" ><DIV align=center></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<%}
				else if(info.getNtranstype()==OBConstant.SettInstrType.OPENFIXDEPOSIT) { %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getNBSNAME()%></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<%} else if(info.getNtranstype()==OBConstant.SettInstrType.OPENNOTIFYACCOUNT){ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<%}else if(info.getNtranstype()==OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT){ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<%}else if(info.getNtranstype()==OBConstant.SettInstrType.CAPTRANSFER_BANKPAY){ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td width=100 height="20" ><DIV align=center><%=info.getSpayeebankname() %></DIV></td>
				<%} else if(info.getNtranstype()==OBConstant.SettInstrType.NOTIFYDEPOSITDRAW){
				if(info.getSpayeebankname()==null){ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<%}else{ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td width=100 height="20" ><DIV align=center><%=info.getSpayeebankname() %></DIV></td>
				<%} }else if(info.getNtranstype()==OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER){
				if(info.getSpayeebankname()==null){ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td height="20" nowrap><DIV align=center></DIV></td>
				<% }else{ %>
				<td height="20" ><DIV align=center><%=info.getSpayeeacctno()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getSpayeename()%></DIV></td>
				<td width=100 height="20" ><DIV align=center><%=info.getSpayeebankname() %></DIV></td>
				<% } } %>
				<td height="20" nowrap><DIV align=center><%=OBConstant.SettInstrStatus.getName(info.getNSTATUS())%></DIV></td>
				<%
					if(strNote.length()<=6){
				%>
						<td height="20" nowrap align="center"><%=strNote%></td>
				<%
					}else{
						%>
						<td height="20" nowrap align="center" id="<%=info.getID()%>"
						 	onmouseover="showHelper('<%="#"+info.getID()%>', '汇款用途', '<%=strNote %>',50)" onmouseout="$('#_Popup_help').remove()" >
							<%=strNote.length()>6?strNote.substring(0,6)+"<font color='red'>...</font>":strNote %>
						</td>
						<%
					}
				%>
				<td height="20" nowrap><DIV align=center><%=info.getFormatDTEXECUTE()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=info.getFormatDtconfirm()%></DIV></td>
			</tr>
<%  
					}}
				else
				{
%>
					<tr> 
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			
					</tr>
<% 
				} 
%>
  </table>
  <br/>
	<table width=100% border="0" align="" cellspacing="0" cellpadding="0">
	<tr>
		<td width="450">
			<div align="left">
				<span class="graytext">金额合计：<%= strCurrency /*金额最小值*/%></span>
				<span id=amountsum>0.00</span>
			</div>
		</td>
		<td></td>
	</tr>
	<tr>
		<td width="450">
			<div align="left">
				<span class="graytext">查询时间：<%=DataFormat.getDateString(dtSysDate)%></span>
			</div>
		</td>
		<td align="right">
		<%
			if (queryResults != null && queryResults.length > 0) {
			%>
				<input class="button1" name=add type=button value=" 复 核 " onClick="Javascript:doCheck();" onKeyDown="Javascript:doCheck();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<%
			}else{
			%>
				<input class="button1" name=add type=button value=" 复 核 " disabled="disabled" onClick="Javascript:doCheck();" onKeyDown="Javascript:doCheck();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<%
			}
		%>
		</td>
	</tr>
</table>			
</table>
</form>
</td>
</tr>
	<TR>
		<td width="99%" border="0" valign="bottom">
         <TABLE border="0" cellPadding=1 height=20 width=80% >
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
	 </TD>
	</TR>
<form name="listReport" method="post" >
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" >
	<input name="strFailPageURL"  type="hidden" >
	<input name="_pageLoaderKey"  type="hidden" value="<%=strPageLoaderKey%>">
</form>
</table>
<form name="form3" method="post" style="display:none">
   <input type="hidden" name="txtID" size="24" value="" style="display:none">
   <input type="hidden" name="txtTransType" size="24" value="" style="display:none">
   <input type="hidden" name="search" value="">
   
</form>

<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
<!--  指纹控件-->
<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
		 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
<% } %>

<script language="javascript">

firstFocus(frmjysqcx.lTransType);
//setSubmitFunction("doSearch()");
 setFormName("frmjysqcx");

 /* 查询处理函数 */
	function doSearch()
	{
	if (validate() == true) {
	frmjysqcx.sign.value="current";
	frmjysqcx.strAction.value="search";
	frmjysqcx.action = "<%=strContext%>/capital/check/control/check_c005.jsp";
	frmjysqcx.strSuccessPageURL.value="<%=strContext%>/capital/check/view/check_v014.jsp";
	frmjysqcx.strFailPageURL.value="<%=strContext%>/capital/check/view/check_v014.jsp";
	showSending();
	frmjysqcx.submit();
	}   
	 }
	 
	 function toDetail(id,name) {
		form3.txtTransType.value = id;
		form3.txtID.value = name;
    	form3.action = "<%=strContext%>/capital/check/control/check_c007.jsp?menu=hidden";
        window.open("","_formwin","toolbar=no, menubar=no, scrollbars=yes,resizable=yes,location=no, status=no");
        form3.target = "_formwin"; 
        //showSending();   
        form3.submit(); 
    }

	function doCheck()/*批量复核*/
	 {
	       var isCheck = false;
	       for(i=0; i<document.frmjysqcx.elements.length; i++)
	       {
	             if(document.frmjysqcx.elements[i].type=="checkbox")
	             {
	                   if (document.frmjysqcx.elements[i].checked == true)
	                   {
	                          isCheck = true;
	                          break;
	                   }
	              }
	       }
	       if (!isCheck)
	       {
	             alert("请选择记录");
	             return false;
	       }
	       
	       //-------------------添加指纹认证---开始----------------
			<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
		    var fpFlag = true;
		    //指纹验证
			$.ajax(
			{
				  type:'post',
				  url:"<%=request.getContextPath()%>/fingerprintControl.jsp",
				  data:"strAction=fingerprint&Ver="+$("#Ver").val(),
				  async:false,
				  success:function(returnValue){
				  	 var result = $(returnValue).filter("div#returnValue").text();
					 if(result=='success'){
						  fpFlag = true;
					 }
					 else if(result=="needFPCert"){
				  		getFingerPrint(frmjysqcx,1);
						if($("#Ver").val()!=""){
					  	    doCheck();// 再次提交
						}
						fpFlag = false;
					 }
					 else if(result=="fpiswrong"){
				  		alert("指纹认证错误，请重新采集");	
						$("#Ver").val("");
					  	getFingerPrint(frmjysqcx,1);//加载控件
						if($("#Ver").val()!=""){
					  	    doCheck();// 再次提交
						}
						fpFlag = false;
					}
					else{
						if(result != null && result != "null" && result != "" ){
							alert(result);	
							$("#Ver").val("");
							fpFlag = false;
						}else{
							fpFlag = true;
						}
					}
				  }
			}
			);
			if(!fpFlag){return;}
			<%}%>
			//-------------------添加指纹认证---结束----------------
	       
	       if (!confirm("是否复核？"))
			{
				$("#Ver").val("");
				return false;
			}
	       frmjysqcx.sign.value="current";
	       frmjysqcx.action = "<%=strContext%>/capital/check/control/check_c006.jsp";
	       showSending(); 
		   frmjysqcx.submit();
		   
	 }
	
	/*金额汇总函数*/
	function dosum(){
	
		var tmp = 0;
		var aa = "0.00";
		
		var s = document.getElementsByName("lID1");
		
		for( var i = 0; i < s.length; i++ )
		{
			if ( s[i].checked )
			{
				tmp += Number(s[i].value.split('####')[1]);
			}
		}
		tmp = tmp + "";
		
		if(tmp==0)
		{
		document.getElementById("amountsum").innerText =aa;
		}
		else{
		document.getElementById("amountsum").innerText = formatAmount(tmp);
		}
	
	}

    /* 校验函数 */
    function validate() {
            var starSubmit = frmjysqcx.sStartSubmit.value;
            var endSubmit = frmjysqcx.sEndSubmit.value;
            if (starSubmit != "") {
                if(chkdate(starSubmit) == 0) {
                    alert("请输入正确的提交开始日期");
                    frmjysqcx.sStartSubmit.focus();
                    return false;
                }
            }
            if (endSubmit != "") {
                if(chkdate(endSubmit) == 0) {
                    alert("请输入正确的提交结束日期");
                    frmjysqcx.sEndSubmit.focus();
                    return false;
                }
            }
            if ((starSubmit != "") && (endSubmit != "")) {
                if (!CompareDate(frmjysqcx.sStartSubmit, frmjysqcx.sEndSubmit, 
                    "提交日期：起始日期不能大于结束日期")) {
                    return false;
                }
            }
			if(!checkAmount(frmjysqcx.dMinAmount,"0","最小金额")){
				return false;
			}
			if(!checkAmount(frmjysqcx.dMaxAmount,"0","最大金额")){
				return false;
			}

            if ((parseFloat(reverseFormatAmount(frmjysqcx.dMinAmount.value))) > (parseFloat(reverseFormatAmount(frmjysqcx.dMaxAmount.value)))) {
                alert("最小金额不能大于最大金额");
                return false;
            }

            /* 执行日期校验 */
            var startExe = frmjysqcx.sStartExe.value;
            var endExe = frmjysqcx.sEndExe.value;
            if (startExe != "") {
                if(chkdate(startExe) == 0) {
                    alert("请输入正确的执行开始日期");
                    frmjysqcx.sStartExe.focus();
                    return false;
                }
            }
            if (endExe != "") {
                if(chkdate(endExe) == 0)
                {
                    alert("请输入正确的执行结束日期");
                    frmjysqcx.sEndExe.focus();
                    return false;
                }
            }
            if ((startExe != "") && (endExe != "")) {
                if (!CompareDate(frmjysqcx.sStartExe, frmjysqcx.sEndExe, 
                    "执行日期：起始日期不能大于结束日期")) {
                    return false;
                }
            }
            return true;
    }
    

/*全选函数*/
function checkAll(o){
	var checkboxes = document.getElementsByName("lID1");
	if(checkboxes.length==0)
		return;
	for(var i=0;i<checkboxes.length;i++){
		checkboxes[i].checked=o.checked;
	}
	dosum();
}

function isCheckedAll()
{
	var isCheck = true;
	for(var i=0;i<document.frmjysqcx.lID1.length;i++)
	{
		if(document.frmjysqcx.lID1[i].checked == false)
			isCheck = false;
	}
	if(isCheck)
		document.frmjysqcx.lID.checked = true;
	else
		document.frmjysqcx.lID.checked = false;
	if(document.frmjysqcx.lID1.length == undefined){
		document.frmjysqcx.lID.checked = document.frmjysqcx.lID1.checked;
	}		
}

</script>

<%
        /* 显示文件尾 */
    }    
   catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	
	OBHtml.showOBHomeEnd(out);
	
%>

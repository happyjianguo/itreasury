<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Log4j"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*"%>
<%@ page import="com.iss.itreasury.util.Database"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();

	try {
		// 用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1, "Gen_E003");
			out.flush();
			return;
		}
		OBHtml.showOBHomeHead(out, sessionMng, "", 1);

		//定义变量  对应后台的DataEntity
		double currentSum = 0.0; ///活期合计
		double depositSum = 0.0; //存款余额合计
		double loanSum = 0.0; //存款余额合计

		long lStatusID = -1;

		//活期账户
		double crt_dAc_mcapitallimitamount = 0.0;
		double crt_dMbalance = 0.0;
		long crt_lNaccounttypeid = -1;
		String crt_strSaccountno = "";
		double crt_dSubSum = 0.0;
		double crt_dSum = 0.0;

		//活期账户页面链接
		long lQueryType = -1;
		long lPayAccountIDEndCtrl = -1;
		long lPayAccountIDStartCtrl = -1;
		long lReceiveAccountIDEndCtrl = -1;
		long lReceiveAccountIDStartCtrl = -1;

		//委托账户
		double cgn_dAc_mcapitallimitamount = 0.0;
		double cgn_dMbalance = 0.0;
		long cgn_lNaccounttypeid = -1;
		String cgn_strSaccountno = "";
		double cgn_dSubSum = 0.0;

		//定期账户
		java.sql.Timestamp fixed_tsAf_dtend = null;
		java.sql.Timestamp fixed_tsAf_dtstart = null;
		double fixed_dAf_mrate = 0.0;
		long fixed_lAf_ndepositterm = -1;
		double fixed_dMbalance = 0.0;
		double fixed_dMopenamount = 0.0;
		long fixed_lNaccounttypeid = -1;
		long fixed_lNstatusid = -1;
		String fixed_strSaccountno = "";
		double fixed_dSubSum = 0.0;
		double fixed_dSum = 0.0;
		long fixed_lNaccountID = -1;
		long fixed_lNtype = -1;

		//贷款账户
		java.sql.Timestamp loan_tsDtEndDate = null;
		java.sql.Timestamp loan_tsDtStartDate = null;
		double loan_dLoanBalance = 0.0;
		double loan_dMAmount = 0.0;
		long loan_lNaccounttypeid = -1;
		long loan_lNborrowclientid = -1;
		long loan_lNIntervalNum = -1;
		long loan_lNstatusid = -1;
		double loan_dRate = 0.0;
		String loan_strSaccountno = "";
		String loan_strSCONTRACTCODE = "";
		double loan_dSubSum = 0.0;

		//页面跳转变量
		String strFailPageURL = "";
		String strSuccessPageURL = "";

		//页面辅助变量
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strPreSaveResult = null;

		String strExecuteDate = Env.getSystemDateString(
		sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strInterestStartDate = Env.getSystemDateString(
		sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = null;

		//从Request中获得参数
		//页面控制参数
		if (request.getAttribute("strActionResult") != null) {
			strActionResult = (String) request
			.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null) {
			strAction = (String) request.getAttribute("strAction");
		}
		if (request.getAttribute("strPreSaveResult") != null) {
			strPreSaveResult = (String) request
			.getAttribute("strPreSaveResult");
		}

		//业务参数
		String strTemp = null;

		strTemp = (String) request.getParameter("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0) {
			lStatusID = Long.valueOf(strTemp).longValue();
		}
		//活期账户数据
		strTemp = (String) request
		.getAttribute("crt_dAc_mcapitallimitamount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			crt_dAc_mcapitallimitamount = Double.valueOf(strTemp)
			.doubleValue();
		}
		strTemp = (String) request.getAttribute("crt_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0) {
			crt_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("crt_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			crt_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("crt_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0) {
			crt_strSaccountno = strTemp;
		}
		strTemp = (String) request.getAttribute("crt_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			crt_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("crt_dSum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			crt_dSum = Double.valueOf(strTemp).doubleValue();
		}

		//委托账户数据
		strTemp = (String) request
		.getAttribute("cgn_dAc_mcapitallimitamount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			cgn_dAc_mcapitallimitamount = Double.valueOf(strTemp)
			.doubleValue();
		}
		strTemp = (String) request.getAttribute("cgn_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0) {
			cgn_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("cgn_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			cgn_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("cgn_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0) {
			cgn_strSaccountno = strTemp;
		}
		strTemp = (String) request.getAttribute("cgn_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			cgn_dSubSum = Double.valueOf(strTemp).doubleValue();
		}

		//定期账户数据
		strTemp = (String) request.getAttribute("fixed_tsAf_dtend");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_tsAf_dtend = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) request.getAttribute("fixed_tsAf_dtstart");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_tsAf_dtstart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) request.getAttribute("fixed_dAf_mrate");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_dAf_mrate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request
		.getAttribute("fixed_lAf_ndepositterm");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_lAf_ndepositterm = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("fixed_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("fixed_dMopenamount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_dMopenamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request
		.getAttribute("fixed_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("fixed_lNstatusid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_lNstatusid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("fixed_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_strSaccountno = strTemp;
		}
		strTemp = (String) request.getAttribute("fixed_dSubsum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("fixed_dSum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_dSum = Double.valueOf(strTemp).doubleValue();
		}
		//定期账户的主账号
		strTemp = (String) request.getAttribute("fixed_lNaccountID");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_lNaccountID = Long.valueOf(strTemp).longValue();
		}
		//期限或者品种的类型
		strTemp = (String) request.getAttribute("fixed_lNtype");
		if (strTemp != null && strTemp.trim().length() > 0) {
			fixed_lNtype = Long.valueOf(strTemp).longValue();
		}

		//贷款数据
		strTemp = (String) request.getAttribute("loan_tsDtEndDate");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_tsDtEndDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) request.getAttribute("loan_tsDtStartDate");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_tsDtStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String) request.getAttribute("loan_dLoanBalance");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_dLoanBalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("loan_dMAmount");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_dMAmount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("loan_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request
		.getAttribute("loan_lNborrowclientid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_lNborrowclientid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("loan_lNIntervalNum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_lNIntervalNum = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("loan_lNstatusid");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_lNstatusid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String) request.getAttribute("loan_dRate");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_dRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String) request.getAttribute("loan_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_strSaccountno = strTemp;
		}
		strTemp = (String) request
		.getAttribute("loan_strSCONTRACTCODE");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_strSCONTRACTCODE = strTemp;
		}
		strTemp = (String) request.getAttribute("loan_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0) {
			loan_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		//add by xwhe 2008-11-24
		long lNoperationtypeid = 1;
		strTemp = (String) request.getAttribute("lNoperationtypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNoperationtypeid = Long.valueOf(strTemp).longValue();
		}
		//获取客户信息
		long lUserID = sessionMng.m_lUserID;
		
%>

<form name='formV002' method='post' action='a002_v.jsp'>
<input type="hidden" name="strSuccessPageURL" value="a002_v.jsp">
<input type="hidden" name="strFailPageURL" value="a002_v.jsp">
<input type="hidden" name="noperationtypeid" value="<%=lNoperationtypeid%>">
<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID%>">
<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID%>">
<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID%>">
<input type="hidden" name="lUserID" value="<%=sessionMng.m_lUserID%>">
<!-- <input type="hidden" name="lStatusID" value="<%=lStatusID%>"> -->
	<!--活期账户-->
  <table cellpadding="0" cellspacing="0" border=0 class="title_top">
  <tr>
       <td class=title><span class="txt_til2">账户余额</span></td>
       <td class=title_right><a class=img_title></td>
	  </tr>
  <tr>
    <td height="5"></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
   <tr>
      <TD>&nbsp;&nbsp;&nbsp;&nbsp;账户类型：
			<select name="lNoperationtypeid" class="select" >									
				<option value=1 <%if(lNoperationtypeid==1){%>selected<%}%>>&nbsp活期帐户&nbsp</option>
				<option value=2 <%if(lNoperationtypeid==2){%>selected<%}%>>&nbsp定期帐户&nbsp</option>
				<option value=3 <%if(lNoperationtypeid==3){%>selected<%}%>>&nbsp贷款帐户&nbsp</option>
				<option value=4 <%if(lNoperationtypeid==4){%>selected<%}%>>&nbsp全部&nbsp</option>
			</select>
	 &nbsp;&nbsp;&nbsp;&nbsp;							
	 <input type="Button" class="button1" value=" 查 找 "  height="18"   onclick="javascript:query()">					
	  </TD>	
   </tr>
   <tr>
    <td height="5"></td>
  </tr>
<tr>
<td>
  <br/>
  	<div id="div1" <%if(lNoperationtypeid != 1 && lNoperationtypeid!= 4){%>style="display:none;"<%} %>>
	 <TABLE border="0" width="100%" class="top">
			<TBODY>
				<tr>
				   <TD width="2%">&nbsp;</TD>
					<TD width="*%">
						<br><TABLE width="100%" id="flexlist1"></TABLE><br>
					</TD>
					<TD width="1%">&nbsp;</TD>
				</tr>
			</TBODY>
		</TABLE>
		</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<div id="div2" <%if(lNoperationtypeid != 2 && lNoperationtypeid!= 4){%>style="display:none;"<%} %>>
	<!--定期账户-->
  	  <table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td colspan="4" height="22" class="ItemBody">
				<%
				if (lStatusID > 0) {
				%>
				<input type="Checkbox" checked id="lStatusID" name="lStatusID" value="1"
					onclick="doSearch();">
				<%
				} else {
				%>
				<input type="Checkbox" id="lStatusID" name="lStatusID" value="1"
					onclick="doSearch();">
				<%
				}
				%>
				显示已结清子账户信息
			</td>
		</tr>
	</table>
   <TABLE border="0" width="100%" class="top">
		<TBODY>
			<tr>
			   <TD width="5%">&nbsp;</TD>
				<TD width="*%">
					<br><TABLE width="100%" id="flexlist2"></TABLE><br>
				</TD>
				<TD width="5%">&nbsp;</TD>
			</tr>
		</TBODY>
	</TABLE>
	</div>
    &nbsp;&nbsp;&nbsp;&nbsp;
   	<div id="div3" <%if(lNoperationtypeid != 3 && lNoperationtypeid!= 4){%>style="display:none;"<%} %>>
	<!--贷款账户-->
	   <TABLE border="0" width="100%" class="top">
		<TBODY>
			<tr>
			   <TD width="5%">&nbsp;</TD>
				<TD width="*%">
					<br><TABLE width="100%" id="flexlist3"></TABLE><br>
				</TD>
				<TD width="5%">&nbsp;</TD>
			</tr>
		</TBODY>
	</TABLE>
	</div>
	
	<br>

	<table width=100% border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td colspan="3" align="left" valign="top" height="16" >
				<br>
				<%
				Timestamp ts = Env.getSystemDateTime();
				%>
				<span class="ItemBody">查询日期：<%=ts.toString().substring(0, 10)%>&nbsp&nbsp
					查询时间：<%=ts.toString().substring(10, 19)%>
				</span>
				<br>
			</td>
		</tr>
	</table>
		
	<table width=100% border="0" cellspacing="2" cellpadding="2" height="15" align="">
		<tr>
			<td height="19" nowrap>
				<div align="right">
					<input type="button" name="Submit23" value=" 下载查询结果 " class="button1" onClick="doDownLoad();">&nbsp;&nbsp;
					<input type="button" name="Submit24" value=" 打 印 " class="button1" onClick="doReport();">
					
				</div>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	</table>
</form>

<script language="JavaScript">

$(document).ready(function() {
    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
	if(<%=lNoperationtypeid%> == 1 ||<%=lNoperationtypeid%>== 4){
		$("#flexlist1").flexigridenc({
			colModel : [
				{display: '账户类型',  name : 'clientname', width : 265, sortable : false, align: 'center'},
				{display: '账号', name: 'clientcode', elType : 'link', elName : 'username', methodName : 'doLink1("?")', width: 265, sortable: false, align: 'center'},
				{display: '资金余额',  name : 'officename', width : 265, sortable : false, align: 'center'},
				{display: '最低余额限制',  name : 'loginno', width : 265, sortable : false, align: 'center'}
			],//列参数
			title:'活期账户',
			classMethod : 'com.iss.itreasury.ebank.obaccountinfo.action.OBAccountQueryAction.queryCurrentBalace',//要调用的方法
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
			userFunc : getFormData1,
			usepager : false,
			printbutton : false,
			exportbutton : false,
			height : 'auto'
		});
	}
});
function getFormData1() 
{
	if(<%=lNoperationtypeid%> == 1 ||<%=lNoperationtypeid%>== 4){
		return $.addFormData("formV002","flexlist1");
	}
}
function doLink1(id){
	var url = "a004_c.jsp?strAction=current&lAccountID=" + id + "&strSuccessPageURL=a004_v.jsp&strFailPageURL=a004_v.jsp&lOfficeID="+<%=sessionMng.m_lOfficeID%>+"&lCurrencyID="+<%=sessionMng.m_lCurrencyID%>;
	window.location.href = url;
}

$(document).ready(function() {
    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
	if(<%=lNoperationtypeid%> == 2 || <%=lNoperationtypeid%>== 4){
		$("#flexlist2").flexigridenc({
			colModel : [
				{display: '账户类型',  name : 'clientname', width : 100, sortable : false, align: 'center'},
				{display: '账号/子账号', name: 'clientcode', elType : 'link', elName : 'username', methodName : 'doLink2("?","?","?","?")', width: 150, sortable: false, align: 'center'},
				{display: '存入日',  name : 'officename', width : 100, sortable : false, align: 'center'},
				{display: '到期日',  name : 'loginno', width : 100, sortable : false, align: 'center'},
				{display: '期限/品种',  name : 'isbelongtoclient', width : 100, sortable : false, align: 'center'},
				{display: '利率',  name : 'dtchangepassword', width : 100, sortable : false, align: 'center'},
				{display: '存款金额',  name : 'isbelongtoclient', width : 100, sortable : false, align: 'center'},
				{display: '存款余额',  name : 'isbelongtoclient', width : 100, sortable : false, align: 'center'},
				{display: '备注',  name : 'isbelongtoclient', width : 110, sortable : false, align: 'center'}
			],//列参数
			title:'定期账户',
			classMethod : 'com.iss.itreasury.ebank.obaccountinfo.action.OBAccountQueryAction.queryFixedBalace',//要调用的方法
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
			userFunc : getFormData2,
			usepager : false,
			printbutton : false,
			exportbutton : false,
			height : 'auto'
		});
	}
});
function getFormData2() 
{
	if(document.getElementById('lStatusID').checked == true){
		document.getElementById('lStatusID').value = 1;
	}
	if(<%=lNoperationtypeid%> == 2 ||<%=lNoperationtypeid%>== 4){
		return $.addFormData("formV002","flexlist2");
	}
}
function doLink2(ntype,strSaccountno,subAccountID,accountID){
	var url;
	if (ntype == 5) {
		url = "a004_c.jsp?strAction=fixed&strDepositNo=" + strSaccountno + "&lSubAccountID=" + subAccountID + "&lAccountID=" + accountID
						+ "&strSuccessPageURL=a005_v.jsp&strFailPageURL=a005_v.jsp";
	} else if (ntype == 6) {
	    url = "a004_c.jsp?strAction=notice&strDepositNo=" + strSaccountno + "&lSubAccountID=" + subAccountID + "&lAccountID=" + accountID
						+ "&strSuccessPageURL=a006_v.jsp&strFailPageURL=a006_v.jsp";
	}
	window.location.href = url;
}

$(document).ready(function() {
    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
	if(<%=lNoperationtypeid%> == 3 || <%=lNoperationtypeid%>== 4){
		$("#flexlist3").flexigridenc({
			colModel : [
				{display: '账户类型',  name : 'clientname', width : 100, sortable : false, align: 'center'},
				{display: '账号/合同号', name: 'clientcode', elType : 'link', elName : 'username', methodName : 'doLink3("?","?")', width: 120, sortable: false, align: 'center'},
				{display: '借款单位',  name : 'officename', width : 80, sortable : false, align: 'center'},
				{display: '起始日',  name : 'loginno', width : 80, sortable : false, align: 'center'},
				{display: '到期日',  name : 'isbelongtoclient', width : 80, sortable : false, align: 'center'},
				{display: '期限',  name : 'dtchangepassword', width : 80, sortable : false, align: 'center'},
				{display: '贷款金额',  name : 'dtchangepassword', width : 110, sortable : false, align: 'center'},
				{display: '贷款余额',  name : 'dtchangepassword', width : 110, sortable : false, align: 'center'},
				{display: '利率',  name : 'dtchangepassword', width : 100, sortable : false, align: 'center'},
				{display: '合同状态',  name : 'dtchangepassword', width : 80, sortable : false, align: 'center'}
			],//列参数
			title:'贷款账户',
			classMethod : 'com.iss.itreasury.ebank.obaccountinfo.action.OBAccountQueryAction.queryLoanBalace',//要调用的方法
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
			userFunc : getFormData3,
			usepager : false,
			printbutton : false,
			exportbutton : false,
			height : 'auto'
		});
	}
});
function getFormData3() 
{
	if(<%=lNoperationtypeid%> == 3 ||<%=lNoperationtypeid%>== 4){
		return $.addFormData("formV002","flexlist3");
	}
}
function doLink3(accountID,contractID){
	var url = "a004_c.jsp?strAction=loan&lAccountID=" + accountID + "&lContractID=" + contractID + "&strSuccessPageURL=a007_v.jsp&strFailPageURL=a007_v.jsp";
	window.location.href = url;
}

function query()
{	
	if(document.getElementById('lStatusID').checked == true){
		document.getElementById('lStatusID').value = 1;
	}
	document.formV002.action = "<%=strContext%>/accountinfo/a002_v.jsp";
	document.formV002.submit();
}

//活期账户
/*settlement/query/view/v312-1.jsp  v0222-1.jsp*/
function doSearch()
{
	document.formV002.target="";
	document.formV002.action = "<%=strContext%>/accountinfo/a002_v.jsp";
   	document.formV002.strSuccessPageURL.value = "../a002_v.jsp";
   	document.formV002.strFailPageURL.value = "../a002_v.jsp";
	document.formV002.submit();
}
    
function doReport() {
	window.open('<%=strContext%>/accountinfo/a002-p.jsp?operationtypeid=<%=lNoperationtypeid%>&lStatusID=<%=lStatusID%>',"NewWin",'width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;');
}

function doDownLoad()
{
    document.formV002.target='blank_';
    document.formV002.action = "<%=strContext%>/accountinfo/a002-e.jsp?operationtypeid="+<%=lNoperationtypeid%>;
    document.formV002.strSuccessPageURL.value = "/accountinfo/a002-e.jsp";
    document.formV002.strFailPageURL.value = "/accountinfo/a002-e.jsp";
    document.formV002.submit();
}
function queryme()
{
	window.location="a002_v.jsp?lNoperationtypeid="+ formV002.lNoperationtypeid.value;
}

</script>

<%
	OBHtml.showOBHomeEnd(out);
	} catch (Exception exp) {
		Log.print(exp.getMessage());
		System.out.print(exp.getMessage());
	}
%>
<%@ include file="/common/SignValidate.inc" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

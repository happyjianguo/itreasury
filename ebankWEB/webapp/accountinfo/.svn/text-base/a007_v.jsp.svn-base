<%--
 页面名称 ：a007_v.jsp
 页面功能 : 贷款账户交易明细界面
 作    者 ：xirenli
 日    期 ：2004-02-24
 特殊说明 ：简单实现说明：
				1、
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo" %>
<%@ page import="com.iss.itreasury.loan.contract.dao.ContractDao"%>
<%@ page import="com.iss.itreasury.loan.contract.dataentity.ContractAmountInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<% String strContext = request.getContextPath();%>
<%
try
{
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	/* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }
		

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
	
	Collection resultColl = (Collection)request.getAttribute("searchResults");
	Iterator itResult = null;
	
	String strAccountNo = "";
	String strContractNo="";
	String strDepositNo="";
	long lAccountID =-1;
	long lSubAccountID =-1;
	long lContractID =-1;
	String strTemp = null;
	strTemp = (String)request.getAttribute("lAccountID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lAccountID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("strDepositNo");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    strDepositNo = strTemp;
	}
	strTemp = (String)request.getAttribute("lSubAccountID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lSubAccountID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("lContractID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lContractID = Long.valueOf(strTemp).longValue();
	}
	strAccountNo = NameRef.getNoLineAccountNoByID(lAccountID);
	strContractNo = DataFormat.formatString(NameRef.getContractNoByID(lContractID));
	if(resultColl != null)
	{
		itResult = resultColl.iterator();
	}
	String strEarlyBanlance = ""; //期初余额
	double mEarlyBanlance = 0.0;
	String strExecuteDate = "";    //执行日
	strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	ContractDao dao = new ContractDao();
	ContractAmountInfo caInfo = new ContractAmountInfo();
	 caInfo = dao.getLateAmount(lContractID);
	mEarlyBanlance = caInfo.getBalanceAmount();
	strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
	//显示文件头
    OBHtml.showOBHomeHead(out, sessionMng, "", OBConstant.ShowMenu.YES);
        //跳转用
   String next="";
   if(request.getAttribute("next")!=null){
      next=(String)request.getAttribute("next");
   }
    String accountType="";
   if(request.getAttribute("accountType")!=null){
      accountType=(String)request.getAttribute("accountType");
   }
%>


	<form name="frmV002"  action="../a004_c.jsp">		
		<input type="hidden" name="strSuccessPageURL" value="../a004_v.jsp">
		<input type="hidden" name="strFailPageURL" value="../a004_v.jsp">
		<input type="hidden" name="strAction" value="loan">
		<input type="hidden" name="strDepositNo" value="<%=strDepositNo%>">
		<input type="hidden" name="lAccountID" value="<%=lAccountID%>">
		<input type="hidden" name="lSubAccountID" value="<%=lSubAccountID%>">
		<input type="hidden" name="lContractID" value="<%=lContractID%>">
		<input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID %>">
		<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>">
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">账户明细</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		<br/>	
		<TABLE border="0" width="100%" class="top">
			<TBODY>
				<tr>
				   <TD width="1%">&nbsp;</TD>
					<TD width="*%">
						<br><TABLE width="100%" id="flexlist"></TABLE><br>
					</TD>
					<TD width="1%">&nbsp;</TD>
				</tr>
			</TBODY>
		</TABLE>
		
		
	
		<br/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		
				<tr> 
				    <td  colspan="3" align="left" valign="top" height="16" class="graytext"><br>
			<%
						Timestamp ts=Env.getSystemDateTime(); 
			%>
				      <span class="graytext">查询日期：<%=ts.toString().substring(0,10)%> 查询时间：<%=ts.toString().substring(10,19)%></span><br>
				    </td>
			   </tr>
		</table>
		<br/>
			<table width="100%" border="0" cellspacing="2" cellpadding="2" height="15" >
				<tr>
					<td height="19" nowrap>
						<div align="right">
							<input type="button" name="Submit23" value="下载查询结果" class="button1" onClick="doDownLoad();">&nbsp;&nbsp;
							<input type="button" name="Submit24" value=" 打 印 " class="button1" onClick="doReport();">&nbsp;&nbsp;
							<input type="button" name="Submit25" value=" 返 回 " class="button1"  onClick="goBack('<%=next%>','<%=accountType%>');">
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
    
	$("#flexlist").flexigridenc({
		colModel : [
			{display: '交易编号', name: 'clientcode', elType : 'link', elName : 'username', methodName : 'doLink("?","?")', width: 125, sortable: false, align: 'center'},
			{display: '交易类型',  name : 'officename', width : 125, sortable : false, align: 'center'},
			{display: '放款通知单',  name : 'loginno', width : 125, sortable : false, align: 'center'},
			{display: '汇款用途',  name : 'loginno', width : 125, sortable : false, align: 'center'},
			{display: '本期提款',  name : 'loginno', width : 125, sortable : false, align: 'center'},
			{display: '本期还款',  name : 'loginno', width : 125, sortable : false, align: 'center'},
			{display: '本期还息',  name : 'loginno', width : 125, sortable : false, align: 'center'},
			{display: '贷款余额',  name : 'loginno', width : 125, sortable : false, align: 'center'}
		],//列参数
		title:'贷款账户：<%=strAccountNo%> 合同号：<%=strContractNo%>',
		classMethod : 'com.iss.itreasury.ebank.obaccountinfo.action.OBAccountQueryAction.queryLoanBalaceDetail',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false,
		printbutton : false,
		exportbutton : false
	});
	
});

function getFormData() 
{
	return $.addFormData("frmV002","flexlist");
}

function doLink(transactionTypeID,strTransNo){
	var url = "<%=strContext%>/accountinfo/querycontrol.jsp?TransactionTypeID="+transactionTypeID+"&TransNo="+strTransNo+"";
	window.open(url);
}

function goBack(a,b){
if(a==8){
location.href='a008-v.jsp?accountType='+b;
}else{
 location.href='a002_v.jsp';
}
}
function doReport() {
<%--    document.frmV002.target='blank_';--%>
<%--    document.frmV002.action = "<%=strContext%>/accountinfo/a004_c.jsp";--%>
<%--    document.frmV002.strSuccessPageURL.value = "/accountinfo/a007-p.jsp";--%>
<%--    document.frmV002.strFailPageURL.value = "/accountinfo/a007-p.jsp";--%>
<%--    document.frmV002.submit();--%>
window.open('<%=strContext%>/accountinfo/a004_c.jsp?strSuccessPageURL=/accountinfo/a007-p.jsp&strFailPageURL=/accountinfo/a007-p.jsp&strAction=loan&strDepositNo=<%=strDepositNo%>&lSubAccountID=<%=lSubAccountID%>&lContractID=<%=lContractID%>&lAccountID=<%=lAccountID%>',"NewWin",'width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;');
}

function doDownLoad()
{
    document.frmV002.target='blank_';
    document.frmV002.action = "<%=strContext%>/accountinfo/a004_c.jsp";
    document.frmV002.strSuccessPageURL.value = "/accountinfo/a007-e.jsp";
    document.frmV002.strFailPageURL.value = "/accountinfo/a007-e.jsp";
    document.frmV002.submit();
}
</script>
	<%	    
	OBHtml.showOBHomeEnd(out);
%>
<%
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<safety:resources />
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc" %>
<%--
/*
 * 程序名称：v007.jsp
 * 功能说明：网银预算查询页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.OBConstant,
				   com.iss.itreasury.ebank.util.OBConstant.OBBudgetStatus,
				   com.iss.itreasury.ebank.util.OBHtml,
				   com.iss.itreasury.ebank.util.NameRef,
				   com.iss.itreasury.util.Constant,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   com.iss.itreasury.util.Constant.PageControl,
				   com.iss.itreasury.util.PageCtrlInfo,
				   java.util.Calendar,
				   java.sql.Timestamp,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo,
				   com.iss.system.dao.PageLoader,
				   com.iss.system.dao.PageLoaderInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<%String strContext = request.getContextPath();%>

<%!
	/* 标题固定变量 */
	String strTitle = "[预算查询]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	 
%>
 
<%
	
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
         // 用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
	/**
	 * 公共参数
	 */
	String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	String strFailPageURL = (String)request.getAttribute("strFailPageURL");
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//办事处ID
	String strTemp = "";
	OBBudgetInfo Info=new OBBudgetInfo();
	
	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	Object[] queryResults = null;
	queryResults = (Object[])request.getAttribute(Constant.PageControl.SearchResults);
	 
	/**
	 * 页面控制类
	 */
	PageCtrlInfo pageInfo		= new PageCtrlInfo();
	pageInfo.convertRequestToDataEntity(request);
	
	/**
	 * 从request得到页面控制参数
	 */

	
	/**
	 * 如果操作结果不是成功,从request中获得所有和dataentity字段绑定的数据,完成页面回显
	 */
	 //if (pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
	//	transAbatementInfo.convertRequestToDataEntity(request);

//显示前页传过的提示
 
%>
<!--jsp:include page="/ShowMessage.jsp"/-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<!--引入js文件-->
<form name="form_1" action="" method="get">
	<!--隐含业务数据-->
		
	<!--页面控制变量-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="strCtrlPageURL" value="">
	<input type="hidden" name="pageLoaderKey" value="<%=strPageLoaderKey%>">	
	<!--重复请求控制-->
	<!--页面控制变量-->	
	<!--隐含业务数据-->
	<input type="hidden" name="isChange" value="">
	<input type="hidden" name="hdnId" value="">
	<input type="hidden" name="Order" value="">
	<input type="hidden" name="Desc" value="">
	<input type="hidden" name="_pageLoaderKey"  value="<%= strPageLoaderKey %>">
	<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用款查询</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>		   
            <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >            
                <tr>
                  <td height="20" nowrap class="ItemTitle">
                    <div align="center">用款名称</div>
                  </td>
                  <TD height=20 align="center"  class=ItemTitle>用款账户</TD>
                  <td  class="ItemTitle" align="center" >用款区间</td>
                  <td nowrap class="ItemTitle" align="center" >用款调整日期</td>
                  <td height="20" nowrap class="ItemTitle" align="center" >用款调整金额</td>
                  <td height="20" class="ItemTitle" align="center" >编制人</td>
                  <td nowrap class="ItemTitle" align="center">编制时间</td>
                  <td nowrap class="ItemTitle" align="center">状态</td>
                  <td nowrap class="ItemTitle" align="center">最后审核人</td>
                </tr>  
<%
	String strDetailPageURL = "";
	if(queryResults!=null && queryResults.length>0)
	{
		for( int i=0; queryResults != null && i<queryResults.length; i++ )
		{
			OBBudgetInfo info = (OBBudgetInfo)queryResults[i];			
%>
                 <tr>
                   <td class="ItemBody" height="20" align="center" nowrap><A href="<%=strContext%>/obbudget/control/c008.jsp?action=budgetfind&id=<%=info.getId()%>"  target="_blank">
				   <%=DataFormat.formatString(info.getSname())%> </a></td>
                   <td class="ItemBody" height="20" align="center" nowrap><%= !(info.getSname().trim().length()>0)?"":NameRef.getBankAcctNameByAcctID(info.getAccountID())%></td>
                   <td class="ItemBody" height="20" align="center" nowrap><%
                   if(info.getSname().trim().length()>0)
                   {
                   		out.print(info.getStartdate().toString().substring(0,10)+"到"+info.getEnddate().toString().substring(0,10));
                   }
                   %></td>
                   <td class="ItemBody" height="20" align="center" nowrap><A href="<%=strContext%>/obbudget/control/c008.jsp?action=adjustfind&id=<%=info.getBudgetadjustid()%>"  target="blank"><%=DataFormat.formatString(info.getAdjustdate()==null?"":info.getAdjustdate().toString().substring(0,10))%></A></td>
                   <td class="ItemBody" nowrap height="20" align="right"><%=  info.getSname().trim().length()>0 ?"":DataFormat.formatDisabledAmount(info.getAdjustamount())%></td>
                   <td class="ItemBody" align="center" height="20" nowrap><%=DataFormat.formatString( NameRef.getUserNameByID(info.getInputuser()))%></td>
                   <td class="ItemBody" align="center" height="20" nowrap><%=DataFormat.formatString(info.getInputdate().toString().substring(0,10))%></td>
                   <td class="ItemBody" align="center" height="20" nowrap><%=DataFormat.formatString(OBConstant.OBBudgetStatus.getName(info.getStatus()))%></td>
				   <td class="ItemBody"  align="left" height="20" nowrap><%
				   if(info.getCheckuser() > 0)
				   {
				   		out.print(NameRef.getUserNameByID(info.getCheckuser()));
				   }
				   %></td>
                 </tr>
                 <tr width="100%" height="1"><td colspan="9" bgcolor="#C5E7F8" height="1"></td></tr>
<%
		}
	}
%>
				

     
     </TABLE>
	<br/>
	<!-- 分页控件 -->
        <table width=100% cellspacing="3" cellpadding="0" class="SearchBar" height="19" >
		  <TR>
	           <TD height=20 width=999 class=ItemTitle>
	             <DIV align=right>
				    <jsp:include page="/pagenavigator.jsp"  />  
				 </DIV>
			   </TD>
		  </TR>
        </table>  
        <br/>
       <table width="100%">
       	<tr>  
          <td>
            <div align="right">
				<input class=button1 name=add type=button value=" 返 回 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
			</div>
          </td>
        </tr>
      </table>
     </td>
    </tr>
   </table>
</form>

<script language="JavaScript">
//firstFocus(document.form_1.lClientIDCtrl);
////setSubmitFunction("doGoNext()");
setFormName("form_1"); 
</script>

<script language="javascript">
var isSubmited=false;
//js 函数定义
/*
 *  查询1条记录，即详细查询
 *
 */
function queryDetail(id)
{
	
	
	form_1.hdnId.value = id;
	
	form_1.strSuccessPageURL.value="<%=strContext%>/obbudget/view/v009.jsp";
	form_1.strFailPageURL.value="<%=strContext%>/obbudget/view/v009.jsp";
	form_1.strCtrlPageURL.value="<%=strContext%>/obbudget/control/c001.jsp";
	
	showSending();
	isSubmited=true;
	form_1.submit();

	
}
 
 
function allFields()
{
	this.aa=new Array("lClientID","客户编号","magnifier",1);
	this.ab=new Array("lAccountID","账号","magnifier",1);
	this.ac=new Array("lSubAccountID","存单","magnifier",1);
	this.ad=new Array("strExecuteDate","生效日期","date",1);
	
}

    
    /* 取消处理函数 */
    function cancelme()
    {
		form_1.action="<%=strContext%>/obbudget/view/v007.jsp";
		form_1.submit();		 
    }
</script>
<%
	//显示文件尾
	//SETTHTML.showHomeEnd(out);
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(Exception e)
    {
		
		e.printStackTrace();
		out.flush();
		return; 
    }
%>

<%@ include file="/common/SignValidate.inc" %>

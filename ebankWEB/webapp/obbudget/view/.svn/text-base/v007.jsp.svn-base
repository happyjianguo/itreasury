<%--
/*
 * 程序名称：v007.jsp
 * 功能说明：网银预算查询页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:directive.page import="com.iss.itreasury.util.Constant"/>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />


<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[用款查询]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	sessionMng.clearPageLoader();
	 
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
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

<form name="form_1" action="" method="get">
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strAction" value="findFirst">
<input type="hidden" name="strCtrlPageURL" value="../control/c007.jsp">
<input type="hidden" name="strSuccessPageURL" value="<%=strContext%>/obbudget/view/v008.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="<%=strContext%>/obbudget/view/v008.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
<input type="hidden" name="officeId" value="<%=sessionMng.m_lOfficeID%>">
<input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID%>">
<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>

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
    <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal align=center>
        <tr><td colspan="6" height="5"></td></tr>  
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
            long sPeriod=-1;
              String strMagnifierNamePeriod = URLEncoder.encode("用款名称");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"sname"};
              String[] strMainFieldsPeriod= {"sname"};
              String[] strReturnNamesPeriod = {"id"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= new Long(sPeriod).toString();
              if(strReturnInitValuesPeriod.equals("-1"))
            	  strReturnInitValuesPeriod = new String("");
              String[] strReturnValuesPeriod = {""};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("用款名称"),URLEncoder.encode("用款账户"),URLEncoder.encode("用款时间区间"),URLEncoder.encode("用款计划状态")};
              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area","status"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              long type = Constant.TRUE;
              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.sname,-1,"+sessionMng.m_lOfficeID+","+sessionMng.m_lCurrencyID+","+type+")";
              String strMatchValuePeriod = "sname";
              String strNextControlsPeriod = "budgetaccount";
              String strTitlePeriod = "用款名称";
              String strFirstTDPeriod = "15%";
              String strSecondTDPeriod= "25%";

              OBMagnifier.showZoomCtrl(out
              ,strMagnifierNamePeriod
              ,strFormNamePeriod
              ,strPrefixPeriod
              ,strMainNamesPeriod
              ,strMainFieldsPeriod
              ,strReturnNamesPeriod
              ,strReturnFieldsPeriod
              ,strReturnInitValuesPeriod
              ,strReturnValuesPeriod
              ,strDisplayNamesPeriod
              ,strDisplayFieldsPeriod
              ,nIndexPeriod
              ,strMainPropertyPeriod
              ,strSQLPeriod
              ,strMatchValuePeriod
              ,strNextControlsPeriod
              ,strTitlePeriod
              ,strFirstTDPeriod
              ,strSecondTDPeriod);
           
%>          
 		 <%
             
              String strMagnifierName  = URLEncoder.encode("用款账户");
              String strFormName  = "form_1";
              String strPrefix = "";
              String[] strMainNames = {"budgetaccount"};
              String[] strMainFields= {"accountno"};
              String[] strReturnNames = {"accountID"};
              String[] strReturnFields = {"id"};
              String strReturnInitValues= new Long(sPeriod).toString();
              if(strReturnInitValues.equals("-1"))
            	  strReturnInitValues = new String("");
              String[] strReturnValues = {"-1"};
              String[] strDisplayNames = {URLEncoder.encode("账户名称"),URLEncoder.encode("账号")};
              String[] strDisplayFields = {"name","accountno"};
              int nIndex = 0;
              String strMainProperty = "";
              String strSQL = "getBudgetPayerAccountNoSQL(form_1.budgetaccount.value,"+sessionMng.m_lUserID+","+sessionMng.m_lClientID+","+sessionMng.m_lCurrencyID+",1,"+sessionMng.m_lOfficeID+")";
              String strMatchValue = "accountno";
              String strNextControls = "startdate";
              String strTitle = "用款账户";
              String strFirstTD = "15%";
              String strSecondTD= "25%";

              OBMagnifier.showZoomCtrl(out
              ,strMagnifierName
              ,strFormName
              ,strPrefix
              ,strMainNames
              ,strMainFields
              ,strReturnNames
              ,strReturnFields
              ,strReturnInitValues
              ,strReturnValues
              ,strDisplayNames
              ,strDisplayFields
              ,nIndex
              ,strMainProperty
              ,strSQL
              ,strMatchValue
              ,strNextControls
              ,strTitle
              ,strFirstTD
              ,strSecondTD);
           
%>          
 		 
          
        </tr>
         
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>开始日期：从</td>
          <td width="25%">
         	 <fs_c:calendar 
	         	    name="startdate"
		          	value="" 
		          	properties="nextfield ='enddate'" 
		          	size="12"/>
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
		  	 <fs_c:calendar 
	         	    name="enddate"
		          	value="" 
		          	size="12"/>
		  </td>
        </tr>
        <tr>
          <td colspan="6">
            <div align="right">
			<input class=button1 name=add type=button value=" 查 找 " onClick="Javascript:searchme();">&nbsp;&nbsp;
			</div>
          </td>
         </tr>
        <tr><td colspan="6" height="5"></td></tr>        
      </table>
    </td>
   </tr>
  </table>
<br>
       
  <input name="sysdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%>>
	  <!--汇款方式动态显示收款方资料-->

<%
		
%>
	 <!-- 刷新问题 -->
<input type="hidden" name="clickCount" value="0">
<!-- 刷新问题 --> 
</form>
 
<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(form_1.sname);
	////setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">
 /* 修改提交处理函数 */
    function searchme()
    {  	
		form_1.action = "../control/c007.jsp";
		var accountid = form_1.accountID.value;
		var budgetId = form_1.id.value;
		if(budgetId<=0 && form_1.sname.value!=""){
			alert("用款名称请从放大镜中选择");
			return false;
		}
		if(accountid<=0 && form_1.budgetaccount.value!=""){
			alert("用款账户请从放大镜中选择");
			return false;
		}
		if (validate() == true)
        {	  
        	 //alert(form_1.budgetid.value);
			/* 确认提交 */
			  
			 
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
            
        }
    }
 
function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        **/
        if(!checkDate(form_1.startdate,0,"开始日期从"))
        {
        	return false;
        }
        if(!checkDate(form_1.enddate,0,"开始日期到"))
        {
        	return false;
        }
        if(form_1.startdate.value.length>0 && form_1.enddate.value.length>0)
        {
        	if(!CompareDate(form_1.startdate,form_1.enddate,"开始日期到应晚于开始日期从"))
			{
				return false;
			}
        }
		
    	return true;
		
    }
</script>
<%

	  }
	catch (IException ie)
	{
		
		
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
	/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);	
%>
 
<%@ include file="/common/SignValidate.inc" %>
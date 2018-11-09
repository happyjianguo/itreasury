 <%--
/*
 * 程序名称：v004.jsp
 * 功能说明：网银用款调整新增页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:directive.page import="com.iss.itreasury.util.Constant"/>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.OBBudgetStatus" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[用款调整新增]";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	if((String)request.getParameter("ID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("ID"))>0)
		{
	 %>
	 		<script language="JavaScript">
			alert("操作成功！");
			</script>
	 <%
		}
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
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v004.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v004.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="modifyDate" value="">
		<%
		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjj"+sessionMng.m_lClientID);
		%>

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用款计划调整</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>		
    </td>
  </tr>
    </table>	
    <br/>
       <table width="80%" border="0" cellspacing="0" cellpadding="0" class=normal align=""> 
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
            long sPeriod=-1;
              String strMagnifierNamePeriod = URLEncoder.encode("用款名称");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetname","accountID","accountname","budgetdtstart","budgetdtend","budgetid","modifyDate"};
              String[] strMainFieldsPeriod= {"sname","accountid","s_accountno","startdate","enddate","id","modifydate"};
              String[] strReturnNamesPeriod = {"budgetID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= new Long(sPeriod).toString();
              if(strReturnInitValuesPeriod.equals("-1"))
            	  strReturnInitValuesPeriod = new String("");
              String[] strReturnValuesPeriod = {"-1"};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("用款名称"),URLEncoder.encode("用款账户"),URLEncoder.encode("用款时间区间")};
              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              long type = Constant.FALSE;
              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.budgetname,"+OBConstant.OBBudgetStatus.APPROVE+","+sessionMng.m_lOfficeID+","+sessionMng.m_lCurrencyID+","+type+")";
              String strMatchValuePeriod = "sname";
              String strNextControlsPeriod = "adjustdate";
              String strTitlePeriod = "<font color=red>*</font>用款名称";
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
 		 
 		<input name="accountID"   type="hidden">
          <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
        <tr>
        <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal">用款账号：</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="accountname" type="text" readonly></td>
          <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款区间：从</td>
          <td width="25%">
          <input type="text" name="budgetdtstart" size="12" readonly >
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
		  <input type="text" name="budgetdtend"  readonly size="12">
		  </td>
        </tr> 
        <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款调整说明：</td>
          <td width="25%" colspan="3"><input class="box"   type="text" name="adjustNote" size="50" maxlength="50"></td>
		  
        </tr> 
 
<input name="inputuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="inputdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >

        <tr>
         <td colspan="7"> </td>
            <td align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" 下一步 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</td>
          <td >&nbsp;</td>
        </tr>
        <tr><td >&nbsp;</td></tr>
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
	firstFocus(form_1.budgetname);
	////setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">
 /* 修改提交处理函数 */
    function addme()
    {  	
		form_1.action = "../view/v010.jsp";
		if (validate() == true)
        {	  
        	 //alert(form_1.budgetid.value);
			showSending();
            form_1.submit();
            
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		 	
			if (confirm("是否取消？"))
			{
				form_1.action="../view/v004.jsp";
				form_1.submit();
			}
		 
    }
function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        **/
        if (form_1.budgetID.value < 0)
		{
			alert("用款名称请从放大镜中选择");
			form_1.budgetID.focus();
			return false;
		}

		if (!InputValid(form_1.adjustNote, 0, "", 1, 0, 100,"用款调整说明"))
		{
			return false;
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
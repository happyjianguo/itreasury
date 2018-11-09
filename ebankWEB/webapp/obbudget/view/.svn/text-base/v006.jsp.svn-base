<%--
/*
 * 程序名称：v006.jsp
 * 功能说明：网银用款调整复核页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />


<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "";
	String strLine = "";
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	OBBudgetAdjustInfo info = new OBBudgetAdjustInfo();
	if(request.getAttribute("rInfo")!=null)
	{
		info = (OBBudgetAdjustInfo)request.getAttribute("rInfo");
	}
	OBBudgetInfo budgetinfo = new OBBudgetInfo();
	if(request.getAttribute("rBudgetInfo")!=null)
	{
		budgetinfo = (OBBudgetInfo)request.getAttribute("rBudgetInfo");
	}
	String type="readonly";
	String adtype="true";
	String act = "";
	String check = null;
 if(request.getParameter("check")!=null)
	{
		check = (String)request.getParameter("check");
	}
	if(sessionMng.m_lUserID==info.getInputuser() && info.getStatus()==OBConstant.OBBudgetStatus.SAVE)//修改删除操作
	{
		type="";
		adtype="false";
		lShowMenu = OBConstant.ShowMenu.NO;
		act = "modify";
		strTitle = "[用款调整修改]";
		strLine = "用款设置 - 用款调整修改";
	}else if(sessionMng.m_lUserID==info.getCheckuser() && info.getStatus()==OBConstant.OBBudgetStatus.CHECK)//取消复核
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.NO;
		act = "canclecheck";
		strTitle = "[用款调整取消复核]";
		strLine = "用款设置 - 用款调整取消复核";
	}
	else if(check!=null  && check.equals("true") 
			&& sessionMng.m_lUserID!=info.getInputuser() && info.getStatus()==OBConstant.OBBudgetStatus.SAVE)//复核
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.YES;
		act="check";
		strTitle = "[用款调整复核]";
		strLine = "用款设置 - 用款调整复核";
	}
	else
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.NO;
		act="query";
		strTitle = "[用款调整明细]";
		strLine = "用款设置 - 用款调整明细";
	}
	
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
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v005.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v005.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="id" value=<%=info.getId()%>>
		<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="250" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="210" background="/webob/graphics/lab_conner2.gif" class="txt_til2"><%=strLine%></td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align=center>
	<!-- 	
     <table width="98%" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr bgcolor="#456795" class="tableHeader">
          <td height="25" bgcolor="#456795" colspan="5" class=FormTitle><font size="3" color="#FFFFFF" ><%=strLine%></font></td>   
        </tr>
        -->
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <% 
			if(type.equals("no"))
              {
           		  long sPeriod=-1;
	              String strMagnifierNamePeriod = URLEncoder.encode("用款名称");
	              String strFormNamePeriod = "form_1";
	              String strPrefixPeriod = "";
	              String[] strMainNamesPeriod = {"budgetname","accountID","accountname","budgetamount","budgetdtstart","budgetdtend","budgetid"};
	              String[] strMainFieldsPeriod= {"sname","accountid","s_accountno","amount","startdate","enddate","id"};
	              String[] strReturnNamesPeriod = {"budgetID"};
	              String[] strReturnFieldsPeriod = {"id"};
	              String strReturnInitValuesPeriod= budgetinfo.getSname();
	              if(strReturnInitValuesPeriod.equals("-1"))
	            	  strReturnInitValuesPeriod = new String("");
	              String[] strReturnValuesPeriod = {String.valueOf(info.getBudgetID())};
	              String[] strDisplayNamesPeriod = {URLEncoder.encode("用款名称"),URLEncoder.encode("用款账户"),URLEncoder.encode("用款时间区间")};
	              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area"};
	              int nIndexPeriod = 0;
	              String strMainPropertyPeriod = type;
	              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.budgetname)";
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
           }
              else
              {
              	%>
              		<td width="15%" class="MsoNormal" height=25>用款名称：</td>
         			<td width="25%"class="MsoNormal"><input class="box" type="text" name="budgetname"   readonly value=<%=budgetinfo.getSname()%>  ></td>
		  			<input type=hidden name=budgetID value=<%=info.getBudgetID()%>>
              	<%
              }
       
%>          
 		 
 		<input name="accountID"   type="hidden">
          <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
        <tr>
        <td  height="25" class="MsoNormal"></td>
          <td  height="25" class="MsoNormal">用款账号：</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="accountname" value=<%=NameRef.getBankAcctNameByAcctID(budgetinfo.getAccountID())%> type="text" readonly></td>
          <td  height="25" class="MsoNormal">用款金额：</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="budgetamount" value=<%=DataFormat.formatDisabledAmount(budgetinfo.getAmount(),2)%> type="text" readonly></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款区间：从</td>
          <td width="25%">
          <input type="text" name="budgetdtstart" value=<%=budgetinfo.getStartdate().toString().substring(0,10)%> size="12" readonly >
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
		  <input type="text" name="budgetdtend"  value=<%=budgetinfo.getEnddate().toString().substring(0,10)%> readonly size="12">
		  </td>
        </tr> 
         <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>用款调整生效日期：</td>
          <td width="25%" class="MsoNormal" >
            <%
          if(type.equalsIgnoreCase("no"))
          {  
          %>
          <fs_c:calendar 
			         	    name="adjustdate"
				          	value="" 
				          	properties="nextfield ='amount'" 
				          	size="12"/>
				          	 <script>
	          		$('#adjustdate').val('<%=info.getAdjustdate().toString().substring(0,10)%>');
	          	</script>
				<script>
	          		$('#adjustdate').attr('readonly','true');
	          	</script>
				          	<!-- 
          	<a href="javascript:show_calendar('form_1.adjustdate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>
			</a>
			 -->
          <%
          }else{
          %>
                    <input type="text" name="adjustdate" onfocus="nextfield ='amount';" size="12" readonly value=<%=info.getAdjustdate().toString().substring(0,10)%>   >
          <%} %>
			</td>
          <td  height="25" class="MsoNormal"><font color=red>*</font>用款调整金额：</td>
          <td  height="25" class="MsoNormal"><%=sessionMng.m_strCurrencySymbol%>
           <%
          	if(act.equalsIgnoreCase("modify"))
          	{
          	%>
          		<script  language="JavaScript">
				createAmountCtrl("form_1","amount","<%=DataFormat.formatDisabledAmount(info.getAmount(),2)%>","note","","<%=sessionMng.m_lCurrencyID%>");
				</script>
          		<%
          	}
          	else 
          	{  
          		%>
          			<input type=text name=amount readonly value=<%=DataFormat.formatDisabledAmount(info.getAmount(),2)%> onfocus="nextfield ='note';"  >
          		<%
          	}
          %>
                   <%
	 
%>
			</td>
        </tr> 
        <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款说明：</td>
          <td width="25%" colspan="3"><input class="box" onfocus="nextfield =' ';"  type="text" name="note" size="50"  maxlength="50"   <%=type%> value=<%= info.getNote()!=null?info.getNote():""%>></td>  
        </tr> 
 
<input name="checkuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="checkdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >
      </table>
      <br>

	  <!--汇款方式动态显示收款方资料-->

      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
           <%
          	if(act.equals("modify"))
          	{
          	%>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" 修  改 " onClick="Javascript:modifyme();" onKeyDown="Javascript:modifyme();"> 
				</div>
	         	 </td>
	         	 <td width="9" height="17"></td>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" 删  除 " onClick="Javascript:delme();" onKeyDown="Javascript:delme();"> 
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" 关  闭 " onClick="Javascript:closeme();" onKeyDown="Javascript:closeme();"> 
				</div>
	          </td>
          	<%
          	}else if(act.equals("canclecheck"))
          	{
          	%>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" 取 消 复 核 " onClick="Javascript:canchekme();" onKeyDown="Javascript:canchekme();"> 
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" 关  闭 " onClick="Javascript:closeme();" onKeyDown="Javascript:closeme();"> 
				</div>
	          </td>
          	<%
          	}
          	else if(act.equals("check"))
          	{
          	%>
          		<td width="63">
	            <div align="right">
				<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
				<input class=button1 name=add type=button value=" 复  核 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" 取  消 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
				</div>
	          </td>
          	<%
          	}
          	else
          	{
          	%>
          		<td width="63">
	            <div align="right">
				</div>
	          </td>
			  <td width="9" height="17"></td>
	          <td width="62">
	            <div align="right">
				<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
				<input class=button1 name=add type=button value=" 关  闭 " onClick="Javascript:closeme();" onKeyDown="Javascript:closeme();"> 
				</div>
	          </td>
          	<%
          	}
          %>
        </tr>
      </table>
<%
		 System.out.println("banrey@@@@@@@@@@@77777777777");
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
		form_1.action = "../control/c005.jsp?action=check";
		if (validate() == true)
        {	  
        	 //alert(form_1.budgetid.value);
			/* 确认提交 */
			 if (!confirm("是否提交？"))
			{
				return false;
			}
			form_1.adjustdate.value = formatedate(form_1.adjustdate.value);
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
            
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		 	
			if (confirm("是否取消？"))
			{
				form_1.action="../view/v005.jsp";
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
		if (!checkDate(form_1.adjustdate,1,"用款调整生效日期"))
		{
			return false;
		}
		if(!checkAmount(form_1.amount, 1, "交易金额"))
		{
			return false;
		}
	 
		if (!InputValid(form_1.note, 0, "", 1, 0, 100,"用款说明"))
		{
			return false;
		}
		 
		
		if(( !CompareDate(form_1.budgetdtstart,form_1.adjustdate,"用款调整生效日期超出用款区间")
		  ||!CompareDate(form_1.adjustdate,form_1.budgetdtend,"用款调整生效日期超出用款区间")))
		{
			form_1.adjustdate.focus();
			return false;
		} 
    	return true;
		
    }
      function closeme()
    {
    	window.close();
    }
    
    function modifyme()
    {
    	form_1.action = "../control/c008.jsp?action=adjustmodify";
		if (validate() == true)
        {		 
			/* 确认提交 */
			if (!confirm("是否修改？"))
			{
				return false;
			}
			
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
            
        }
    }
    
    function delme()
    {
    	form_1.action = "../control/c008.jsp?action=adjustdelete&id=<%=info.getId()%>";
		 		 
			/* 确认提交 */
			if (!confirm("是否删除？"))
			{
				return false;
			}
			
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
 
    }
    
    function canchekme()
    {
    	form_1.action = "../control/c008.jsp?action=adjustcancle&id=<%=info.getId()%>";
		 		 
			/* 确认提交 */
			if (!confirm("是否取消复核？"))
			{
				return false;
			}
			
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
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

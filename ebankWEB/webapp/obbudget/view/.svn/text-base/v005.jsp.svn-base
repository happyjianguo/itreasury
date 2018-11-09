<%--
/*
 * 程序名称：v004.jsp
 * 功能说明：网银用款调整匹配页
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
				   java.util.Calendar,
				   java.sql.Timestamp,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetAdjustInfo,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />


<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[用款调整复核]";
	OBBudgetAdjustInfo cinfo = new OBBudgetAdjustInfo();
	OBBudgetInfo rBudgetInfo = new OBBudgetInfo();
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	
	if((String)request.getParameter("RID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("RID"))==-1)
		{
	 %>
	 		<script language="JavaScript">
			alert("没有相匹配的用款，请重新录入！");
			</script>
	 <%
		 if(request.getAttribute("CInfo")!=null)
			{
				cinfo = (OBBudgetAdjustInfo)request.getAttribute("CInfo");	
				rBudgetInfo = (OBBudgetInfo)request.getAttribute("rBudgetInfo");
			}
		}
		if(Long.parseLong((String)request.getParameter("RID"))==-2)
		{
	 %>
	 		<script language="JavaScript">
			alert("复核失败！");
			</script>
	 <%
		}
		if(Long.parseLong((String)request.getParameter("RID"))>0)
		{
	 %>
	 		<script language="JavaScript">
			alert("复核成功！");
			</script>
	 <%
		}
	}else{
		cinfo = new OBBudgetAdjustInfo();
		rBudgetInfo = new OBBudgetInfo();
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
<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v006.jsp?check=true">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v005.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="250" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="210" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 用款设置 - 用款调整新增 - 复核</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>	
	<table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align=center>
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
          	  long sPeriod=cinfo.getBudgetID()>0?cinfo.getBudgetID():-1;
              String strAccname = cinfo.getBudgetID()>0?rBudgetInfo.getSname():"";
              
              String strMagnifierNamePeriod = URLEncoder.encode("用款名称");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetname","accountID","accountname","budgetamount","budgetdtstart","budgetdtend","budgetid"};
              String[] strMainFieldsPeriod= {"sname","accountid","s_accountno","amount","startdate","enddate","id"};
              String[] strReturnNamesPeriod = {"budgetID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= strAccname;
              String[] strReturnValuesPeriod = {String.valueOf(sPeriod)};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("用款名称"),URLEncoder.encode("用款账户"),URLEncoder.encode("用款时间区间")};
              String[] strDisplayFieldsPeriod = {"sname","s_accountno","area"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              String strSQLPeriod = "getOBBudget("+sessionMng.m_lClientID+",form_1.budgetname,"+OBConstant.OBBudgetStatus.AUTHED+")";
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
          <td  height="25" class="MsoNormal"><input class="box"  name="accountname" type="text" readonly value="<%=rBudgetInfo.getAccountID()>0?NameRef.getBankAcctNameByAcctID(rBudgetInfo.getAccountID()):""%>"></td>
          <td  height="25" class="MsoNormal">用款金额：</td>
          <td  height="25" class="MsoNormal"><input class="box"  name="budgetamount" type="text" readonly value="<%=rBudgetInfo.getAmount()>0.00?DataFormat.formatDisabledAmount(rBudgetInfo.getAmount(),2):""%>"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款区间：从</td>
          <td width="25%">
          <input type="text" name="budgetdtstart" size="12" readonly value="<%=rBudgetInfo.getStartdate()!=null?rBudgetInfo.getStartdate().toString().substring(0,10):""%>">
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
		  <input type="text" name="budgetdtend"  readonly size="12" value="<%=rBudgetInfo.getEnddate()!=null?rBudgetInfo.getEnddate().toString().substring(0,10):""%>">
		  </td>
        </tr> 
         <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>   
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>用款调整生效日期：</td>
          <td width="25%" class="MsoNormal" >
          		<fs_c:calendar 
	         	    name="adjustdate"
		          	value="" 
		          	properties="nextfield ='amount'" 
		          	size="12"/>
		    	<script>
	          		$('#adjustdate').val('<%=cinfo.getAdjustdate()!=null?cinfo.getAdjustdate().toString().substring(0,10):""%>');
	          	</script>
		</td>
          <td  height="25" class="MsoNormal"><font color=red>*</font>用款调整金额：</td>
          <td  height="25" class="MsoNormal"><%=sessionMng.m_strCurrencySymbol%>
            <script  language="JavaScript">
				createAmountCtrl("form_1","amount","<%=cinfo.getAmount()>0.00?DataFormat.formatDisabledAmount(cinfo.getAmount(),2):""%>","","","<%=sessionMng.m_lCurrencyID%>");
			</script>
			</td>
		  
        </tr> 
        
 
<input name="inputuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="inputdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >
      </table>
      <br>
       
  <input name="sysdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%>>
	  <!--汇款方式动态显示收款方资料-->


      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" 匹  配 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</div>
          </td>
		  <td width="9" height="17"></td>
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 name=add type=button value=" 取  消 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
			</div>
          </td>
        </tr>
      </table>
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
		form_1.action = "../control/c005.jsp?action=match";
		if (validate() == true)
        {	  
 
			/* 确认提交 */
			 if (!confirm("是否匹配？"))
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
			form_1.budgetname.focus();
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
 
		if(( !CompareDate(form_1.budgetdtstart,form_1.adjustdate,"用款调整生效日期超出用款区间")
		  ||!CompareDate(form_1.adjustdate,form_1.budgetdtend,"用款调整生效日期超出用款区间")))
		{
			form_1.adjustdate.focus();
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
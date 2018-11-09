<%--
/*
 * 程序名称：v002.jsp
 * 功能说明：网银预算匹配页
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
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[预算复核]";
	OBBudgetInfo cinfo = new OBBudgetInfo();
%>
<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	System.out.println("@@@@@@@@@@@@@@@@@@@@"+(String)request.getParameter("RID"));
	if((String)request.getParameter("RID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("RID"))==-1)
		{
	 %>
	 		<script language="JavaScript">
			alert("没有相匹配的预算，请重新录入！");
			</script>
	 <%
		if(request.getAttribute("CInfo")!=null)
		{
			cinfo = (OBBudgetInfo)request.getAttribute("CInfo");			 
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
	}
	else{
		cinfo = new OBBudgetInfo();
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

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="form_2" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v003.jsp?check=true">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v002.jsp">		<!--操作失败转向页面-->

		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
     <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="210" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="190" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 预算设置 - 预算新增 - 复核</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
    <table width="774" border="0" cellspacing="0" cellpadding="0" class=normal align=center>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>预算名称：</td>
		  <td width="25%" height="25" class="MsoNormal"><input class="box"  type="text" name="sname"  onfocus="nextfield ='budgetaccount';" size="20"
		  												value="<%=cinfo.getSname()!=null?cinfo.getSname():""%>" maxlength="15"></td>
		  <td width="15%" height="25" class="MsoNormal"></td>
		 <td width="25%"></td>
        </tr> 
        
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
              long sPeriod=cinfo.getAccountID()>0?cinfo.getAccountID():-1;
              String strAccname = cinfo.getAccountID()>0?NameRef.getBankAcctNameByAcctID(cinfo.getAccountID()):"";
              System.out.println(sPeriod+"BBBB#@@@@@@@@"+strAccname);
              String strMagnifierNamePeriod = URLEncoder.encode("预算账户");
              String strFormNamePeriod = "form_2";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetaccount"};
              String[] strMainFieldsPeriod= {"accountno"};
              String[] strReturnNamesPeriod = {"accountID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= strAccname;
              String[] strReturnValuesPeriod = {String.valueOf(sPeriod)};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("账户名称"),URLEncoder.encode("账号")};
              String[] strDisplayFieldsPeriod = {"name","accountno"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              String strSQLPeriod = "getBankAccount("+sessionMng.m_lCurrencyID+","+sessionMng.m_lClientID+",form_2.budgetaccount)";
              String strMatchValuePeriod = "accountno";
              String strNextControlsPeriod = "amount";
              String strTitlePeriod = "<font color=red>*</font>预算账户";
              String strFirstTDPeriod = "";
              String strSecondTDPeriod= "";

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
 
          <td  height="25" class="MsoNormal"><font color=red>*</font>预算金额：</td>
          <td  height="25" class="MsoNormal"><%=sessionMng.m_strCurrencySymbol%>
          <script  language="JavaScript">
				createAmountCtrl("form_2","amount","<%=cinfo.getAmount()>0.00?DataFormat.formatDisabledAmount(cinfo.getAmount(),2):""%>","startdate","","<%=sessionMng.m_lCurrencyID%>");
			</script></td>
          <td  height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>预算区间：从</td>
          <td width="25%">
          		<fs_c:calendar 
		         	    name="startdate"
			          	value="" 
			          	properties="nextfield ='enddate'" 
			          	size="12"/>
			     <script>
	          		$('#startdate').val('<%=cinfo.getStartdate()!=null?cinfo.getStartdate().toString().substring(0,10):""%>');
	          	</script>
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
		  		<fs_c:calendar 
		         	    name="enddate"
			          	value="" 
			          	properties="nextfield =''" 
			          	size="12"/>
			     <script>
	          		$('#enddate').val('<%=cinfo.getEnddate()!=null?cinfo.getEnddate().toString().substring(0,10):""%>');
	          	</script>
		  </td>
        </tr> 
         
         
      </table>
      <br>
       
  
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
	firstFocus(form_2.sname);
	////setSubmitFunction("addme()");
	setFormName("form_2");
</script>

<script language="Javascript">
 /* 修改提交处理函数 */
    function addme()
    {  	
		form_2.action = "../control/c002.jsp?action=match";
		if (validate() == true)
        {		 
			/* 确认提交 */
			if (!confirm("是否匹配？"))
			{
				return false;
			}
			
			//form_2.clickCount.value = parseInt(form_2.clickCount.value) +1 ;
			showSending();
            form_2.submit();
            
        }
    }
    /* 取消处理函数 */
    function cancelme()
    {
		 	
			if (confirm("是否取消？"))
			{
				form_2.action="../view/v002.jsp";
				form_2.submit();
			}
		 
    }
function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        **/
        
		if (form_2.sname.value == "")
		{
			alert("预算名称不能为空");
			form_2.sname.focus();
			return false;
		}
		if (form_2.accountID.value < 0)
		{
			alert("预算账号请从放大镜中选择");
			form_2.budgetaccount.focus();
			return false;
		}
		/* 划款资料非空校验 */
		/* 金额校验 */
		if(!checkAmount(form_2.amount, 1, "交易金额"))
		{
			return false;
		}
		/* 预算说明 */
	 
		if(!CompareDate(form_2.startdate,form_2.enddate,"预算区间到应晚于预算区间从"))
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

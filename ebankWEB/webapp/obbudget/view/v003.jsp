 <%--
/*
 * 程序名称：v003.jsp
 * 功能说明：网银用款查询详细信息页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo,
				   com.iss.itreasury.ebank.obbudget.bizlogic.*,
				   java.util.Calendar,
				   java.sql.Timestamp,
				   com.iss.itreasury.util.*"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.OBBudgetStatus" %>
<%@ page import="java.util.*"%>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%String strContext = request.getContextPath();%>

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
	OBBudgetInfo info = new OBBudgetInfo();
	if(request.getAttribute("rInfo")!=null)
	{
		info = (OBBudgetInfo)request.getAttribute("rInfo");
	}
	String type="readonly";
	String adtype="true";
	String act = "";
	String check = null;
	long settInstrType = -1;
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
		strTitle = "[用款修改]";
		strLine = "用款设置 - 用款修改";
	}else if(sessionMng.m_lUserID==info.getCheckuser() && info.getStatus()==OBConstant.OBBudgetStatus.CHECK)//取消复核
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.NO;
		act = "canclecheck";
		strTitle = "[用款取消复核]";
		strLine = "用款设置 - 用款取消复核";
	}
	else if(check!=null  && check.equals("true") 
			&& sessionMng.m_lUserID!=info.getInputuser() && info.getStatus()==OBConstant.OBBudgetStatus.SAVE)//复核
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.YES;
		act="check";
		strTitle = "[用款复核]";
		strLine = "用款设置 - 用款复核";
	}
	else
	{
		type="readonly";
		adtype="true";
		lShowMenu = OBConstant.ShowMenu.NO;
		act="query";
		strTitle = "[用款明细]";
		strLine = "用款设置 - 用款明细";
	}
	OBBudgetBiz biz = new OBBudgetBiz();
	List list = biz.findAllSubRecords(info.getId());
	System.out.println("ContextPath"+ strContext);
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
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />


<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v002.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v002.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="id" value=<%=info.getId()%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="OfficeID" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="CurrencyID" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
		<input type="hidden" name="modifyuser" value=<%=sessionMng.m_lUserID%>>
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
          <td width="15%" class="MsoNormal" height=25>用款客户：</td>
          <td width="25%"><input readonly class="box"   type="text" name="txtClientName" value=<%=sessionMng.m_strClientName%> size="30"></td>
		  <td width="15%" height="25" class="MsoNormal"><font color=red>*</font>用款名称：</td>
		  <td width="25%" height="25" class="MsoNormal"><input class="box"  type="text" name="sname"  onfocus="nextfield ='budgetaccount';" size="20" value=<%=info.getSname()%> <%=type%> maxlength="15" ></td>
        </tr> 
        
        <tr class="MsoNormal"> 
        <%
              if(act.equals("modify"))
              {
	              long sPeriod=-1;
	              String strMagnifierNamePeriod = URLEncoder.encode("用款账户");
	              String strFormNamePeriod = "form_1";
	              String strPrefixPeriod = "";
	              String[] strMainNamesPeriod = {"budgetaccount"};
	              String[] strMainFieldsPeriod= {"accountno"};
	              String[] strReturnNamesPeriod = {"accountID"};
	              String[] strReturnFieldsPeriod = {"id"};
	              String strReturnInitValuesPeriod= NameRef.getBankAcctNameByAcctID(info.getAccountID());
	              if(strReturnInitValuesPeriod.equals("-1"))
	            	  strReturnInitValuesPeriod = new String("");
	              String[] strReturnValuesPeriod = {String.valueOf(info.getAccountID())};
	              String[] strDisplayNamesPeriod = {URLEncoder.encode("账户名称"),URLEncoder.encode("账号")};
	              String[] strDisplayFieldsPeriod = {"name","accountno"};
	              int nIndexPeriod = 0;
	              String strMainPropertyPeriod = "";
	              String strSQLPeriod = "getBudgetPayerAccountNoSQL(form_1.budgetaccount.value,"+sessionMng.m_lUserID+","+sessionMng.m_lClientID+","+sessionMng.m_lCurrencyID+",1,"+sessionMng.m_lOfficeID+")";
	              String strMatchValuePeriod = "accountno";
	              String strNextControlsPeriod = "startdate";
	              String strTitlePeriod = "<font color=red>*</font>用款账户";
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
              }
              else
              {
              	%>
              		<td width="15%" class="MsoNormal" height=25>用款账户：</td>
         			<td width="25%"class="MsoNormal"><input class="box" type="text" name="budgetaccount"   readonly value=<%=NameRef.getBankAcctNameByAcctID(info.getAccountID())%>  ></td>
		  			<input type=hidden name=accountID value=<%=info.getAccountID()%>>
              	<%
              }
           
%>          

          <td  height="25" class="MsoNormal"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>用款区间：从</td>
          <td width="25%">
         
          <%
          if(act.equalsIgnoreCase("no"))
          {
          %>
	          <fs_c:calendar 
	         	    name="startdate"
		          	value="" 
		          	properties="nextfield ='enddate'" 
		          	size="12"/>
		          	 <script>
	          		$('#startdate').val('<%= info.getStartdate().toString().substring(0,10)%>');
	          	</script>
		          	<script>
	          			$('#startdate').attr('readonly','true');
		          	</script>
				    <!-- 
          	<a href="javascript:show_calendar('form_1.startdate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>
			</a>
			 -->
          <%
          }else{
          %>
			 <input type="text" name="startdate" readonly  onfocus="nextfield ='enddate';" size="12" value="<%= info.getStartdate().toString().substring(0,10)%>"   >
		 <%} %>	
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
		   <%
          if(act.equalsIgnoreCase("no"))
          {
          %>
          <fs_c:calendar 
				         	    name="enddate"
					          	value="" 
					          	properties="nextfield ='note'" 
					          	size="12"/>
						          	  <script>
	          		$('#enddate').val('<%=info.getEnddate().toString().substring(0,10)%>');
	          	</script>
					          	<!-- 
          
          	<a href="javascript:show_calendar('form_1.enddate');" onmouseout="window.status='';return true;" onmouseover="window.status='Date Picker';return true;">
				<img src="\webob\graphics\calendar.gif"  width="15" height="18" border=0>
			</a> -->
          <%
          }else{
          %>
          		  <input type="text" name="enddate"  readonly value="<%=info.getEnddate().toString().substring(0,10)%>" onfocus="nextfield ='note';" size="12"   >
          
          <%} %>
			
		  </td>
        </tr> 
        <tr class="MsoNormal">
          <td width="15%" class="MsoNormal" height=25>用款说明：</td>
          <td width="25%" colspan="3"><input class="box"   type="text" name="note" size="50" <%=type%> maxlength="50" value=<%=info.getNote()!=null?info.getNote():""%>  ></td>
		  
        </tr>
        
        
        <%
		for(int i = 0 ; i < list.size() ; i ++){
			OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
%>
		<tr >
		<td height="25" class="MsoNormal" width="25%"><%=DataFormat.getDateString(subInfo.getStartdate())%></td>
		<td  height="25" class="MsoNormal" width="25%"><font color=red>*</font>用款金额：</td>
        <td  height="25" class="MsoNormal" width="25%"><%=sessionMng.m_strCurrencySymbol%> 
<%   
		if(subInfo.getStartdate().before(Env.getCurrentSystemDate()) || (subInfo.getStartdate().equals(Env.getCurrentSystemDate()) && (subInfo.getStatus() != OBConstant.OBBudgetStatus.APPROVE && subInfo.getStatus() != OBConstant.OBBudgetStatus.NOTDEAL && subInfo.getStatus() != OBConstant.OBBudgetStatus.FAILEDDEAL))){
%>		    
        <script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","readonly","");
		</script>
<%
		}else {
%>
		<script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","<%=type%>","");
		</script>
<%
		}
%>
		</td>
		<td height="25" class="MsoNormal" width="25%"><%=OBConstant.OBBudgetStatus.getName(subInfo.getStatus())%></td>
		</tr>
<%		
		}
 %>
 
 
         <tr>
           <td colspan="4">
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode = '<%=String.valueOf(info.getAdjunctID())%>' 
		        	caption = "上传" 
		        	lid = '-1'
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
           </td>
        </tr>
        <TR>
		  <TD colspan="4">
			 <%--<iframe src="/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=<%=info.getId()%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		 <% String strtransNo = info.getId()>0?String.valueOf(info.getId()):"";%>
		  <fs:HistoryOpinionFrame
					  strTransNo='<%=strtransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.BUDGETNEW%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
		  </TD>
	  </TR>
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
			<fs:obApprovalinitbutton controlName="approvalInit" 
				value="修改并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.BUDGETNEW%>"/>
				</div>          		
          		</td>
	         	<td width="9" height="17"></td>
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
    function addme()
    {  	
		form_1.action = "../control/c002.jsp?action=check";
		if (validate() == true)
        {		 
			/* 确认提交 */
			if (!confirm("是否复核？"))
			{
				return false;
			}
			
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
				form_1.action="../view/v002.jsp";
				form_1.submit();
			}
		 
    }
function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        **/
        
		if (form_1.sname.value == "")
		{
			alert("用款名称不能为空");
			form_1.sname.focus();
			return false;
		}
		if (form_1.accountID.value < 0)
		{
			alert("用款账号请从放大镜中选择");
			form_1.accountID.focus();
			return false;
		}
		/* 用款说明 */
		if (!InputValid(form_1.note, 0, "", 1, 0, 100,"用款说明"))
		{
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
    	form_1.action = "../control/c008.jsp?action=budgetmodify";
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
     //保存并提交审批, Added by zwsun, 2007/7/18/
    function doSaveAndApprovalInit()
	{
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		modifyme();
	}  
	    
    function delme()
    {
    	form_1.action = "../control/c008.jsp?action=budgetdelete&id=<%=info.getId()%>";
		 		 
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
    	form_1.action = "../control/c008.jsp?action=budgetcancle&id=<%=info.getId()%>";
		 		 
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
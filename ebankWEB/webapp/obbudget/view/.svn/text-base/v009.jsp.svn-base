<%--
/*
 * 程序名称：v009.jsp
 * 功能说明：网银预算新增页
 * 作　　者：liangpan
 * 完成日期：2007-07-17
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:directive.page import="java.text.DateFormat"/>
<!-- 审批流 Added by zwsun, 2007/7/18 -->
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="wf"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.*,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.*" %>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<%@ page import="com.iss.itreasury.ebank.obbudget.dataentity.OBBudgetInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%@ include file="/common/common.jsp"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[用款新增]";
%>
<%
  
    OBHtml.validateRequest(out,request,response);
    
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	   // transTypeID = Constant.ModuleType.BUDGET
	}
	
	long period = -1;
	String strTemp = "";
	String startDate = "";
	String originalStartDate = "";
	strTemp = (String)request.getParameter("period");
	if(strTemp != null && !strTemp.equals("")){
		period = Long.parseLong(strTemp);
	}
	strTemp = (String)request.getParameter("startdate");
	if(strTemp != null && !strTemp.equals("")){
		startDate = strTemp;
		originalStartDate = startDate;
	}
	List list = new ArrayList();
	List listAmountFormName = new ArrayList();
	for(int i=0;i<period;i++){
		list.add(startDate);
		Timestamp date = DataFormat.getDateTime(startDate);
		date.setTime(date.getTime()+24*3600*1000);
		startDate = DataFormat.getDateString(date);
		listAmountFormName.add("amount"+i);		
	}
	OBBudgetInfo info = new OBBudgetInfo();
	info.convertRequestToDataEntity(request);
%>
<%
	
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
        //   OBHtml.validateRequest(out,request,response);
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
<input type="hidden" name="strSuccessPageURL" value="../view/v001.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="officeId" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="currencyId" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
		<input type="hidden" name="sname" value="<%=info.getSname()%>">
		<input type="hidden" name="accountID" value="<%=info.getAccountID()%>">
		<input type="hidden" name="note" value="<%=info.getNote()%>">
<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="210" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="190" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 用款设置 - 用款新增</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
</table>
<table width="774" border="1" cellspacing="0" cellpadding="0" class=normal align=center>
<%
		for(int i = 0 ; i < period ; i ++){
%>
		<tr align="center">
		<td height="25" class="MsoNormal" width="30%"><%=(String)list.get(i)%></td>
		<td  height="25" class="MsoNormal" width="35%"><font color=red>*</font>用款金额：</td>
        <td  height="25" class="MsoNormal" width="35%"><%=sessionMng.m_strCurrencySymbol%> 
        <script  language="JavaScript">
			createAmountCtrl("form_1","amount"+<%=i%>,"","","","<%=sessionMng.m_lCurrencyID%>");
		</script></td>
		</tr>
<%		
		}
		long longtime= Env.getSystemDateTime().getTime();
 %>
<input name="adjunctID" type="hidden" value=<%=longtime%> >
<input name="inputuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="inputdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >
    </table>
  <%--  	
    <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">链接附件</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
    </table> 
    <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>--%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='false'
		        	transCode ='<%=String.valueOf(longtime)%>' 
		        	caption = "上传" 
		        	lid = '-1'
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
        <%--   </td>
        </tr>
    </table>--%>
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
			<input class=button1 name="add" type=button value=" 提 交 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</div>
          </td>
		  <td width="9" height="17"></td>
			<!--  Added by zwsun ,2007/7/18, 审批流 -->
			<td>
			<wf:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.BUDGETNEW%>"/>
		  	</td>
		  <td width="9" height="17"></td>		  
          <td width="62">
            <div align="right">
			<!--img src="\webob\graphics\button_quxiao.gif" width="46" height="18" onclick="Javascript:cancelme();"-->
			<input class=button1 name="cancel" type=button value=" 取  消 " onClick="Javascript:cancelme();" onKeyDown="Javascript:cancelme();"> 
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
	firstFocus(form_1.amount0);
	//setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">

 /* 修改提交处理函数 */
    function add()
    {  	
    	var txtColl = document.getElementsByTagName("INPUT");
    	for(var i=0;i<txtColl.length;i++){
    		var subInput = txtColl[i];
    		if(subInput.type == "text") {
    			if(subInput.value == 0.0 || subInput.value == ""){
    				alert("用款金额不能为空或0");
    				return false;
    			}
    		}
    	}    	    	
		form_1.action = "../control/c001.jsp?period=<%=period%>&startDate=<%=originalStartDate%>";
		if(confirm("确定提交？")){
			showSending();
        	form_1.submit();
        }
    }
    function addme()
	{
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		add();
	}  
    //保存并提交审批, Added by zwsun, 2007/7/18/
    function doSaveAndApprovalInit()
	{
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		add();
	}   
    
    /* 取消处理函数 */
    function cancelme()
    {
		 	
			if (confirm("是否取消？"))
			{
				form_1.action="../view/v001.jsp";
				form_1.submit();
			}
		 
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
<%--
/*
 * 程序名称：v010.jsp
 * 功能说明：网银预算调整页
 * 作　　者：liangpan
 * 完成日期：2007-07-18
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:directive.page import="java.text.DateFormat"/>
<!-- 审批流 Added by zwsun, 2007/7/18 -->
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
<%@ page import="com.iss.itreasury.ebank.obbudget.bizlogic.*"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%String strContext = request.getContextPath();%>

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
	
	long id = -1;
	String strTemp = "";
	String adjustNote = "";
	Timestamp modifyDate = null;
	
	strTemp = (String)request.getParameter("budgetID");
	if(strTemp != null && !strTemp.equals("")){
		id = Long.parseLong(strTemp);
	}
	
	strTemp = (String)request.getParameter("adjustNote");
	if(strTemp != null && !strTemp.equals("")){
		adjustNote = strTemp;
	}
	
	strTemp = (String)request.getParameter("modifyDate");
	if(strTemp != null && !strTemp.equals("")){
		modifyDate = DataFormat.getDateTime(strTemp);
	}

	OBBudgetInfo info = new OBBudgetInfo();
	OBBudgetBiz biz = new OBBudgetBiz();
	if(id > 0){
		info = biz.findByID(id);
	}
	List list = biz.findAllSubRecords(id);
	Timestamp currentDate = DataFormat.getDateTime(DataFormat.getDateString(Env.getSystemDateTime(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)));
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
<input type="hidden" name="strSuccessPageURL" value="../view/v004.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v004.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="id" value=<%=info.getId()%>>
		<input type="hidden" name="adjustNote" value=<%=adjustNote%>>
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="officeId" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="currencyId" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
		<input type="hidden" name="sname" value="<%=info.getSname()%>">
		<input type="hidden" name="accountID" value="<%=info.getAccountID()%>">
		<input type="hidden" name="note" value="<%=info.getNote()%>">
		<input type="hidden" name="modifyDate" value="<%=modifyDate%>">
<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table width="220" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="210" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 用款设置 - 用款调整新增</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
</table>
<table width="774" border="1" cellspacing="0" cellpadding="0" class=normal align=center>
<%
		long isReadonly = Constant.TRUE;
		for(int i = 0 ; i < list.size() ; i ++){
			OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
%>
		<tr align="center">
		<td height="25" class="MsoNormal" width="25%"><%=DataFormat.getDateString(subInfo.getStartdate())%></td>
		<td  height="25" class="MsoNormal" width="25%"><font color=red>*</font>用款金额：</td>
        <td  height="25" class="MsoNormal" width="25%"><%=sessionMng.m_strCurrencySymbol%> 
<%   
		if(subInfo.getStartdate().before(currentDate) || (subInfo.getStartdate().equals(currentDate) && (subInfo.getStatus() != OBConstant.OBBudgetStatus.NOTDEAL && subInfo.getStatus() != OBConstant.OBBudgetStatus.FAILEDDEAL))){
%>		    
        <script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","readonly","");
		</script>
<%
		}else {
%>
		<script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","","");
		</script>
<%
		isReadonly = Constant.FALSE;
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
    </table>	

      <br>
       
  <input name="sysdate" type="hidden" value=<%=currentDate%>>
	  <!--汇款方式动态显示收款方资料-->

      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="605">
            <div align="right"></div>
          </td>
          <td width="63">
            <div align="right">
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name="add" type=button value=" 保 存 " onClick="Javascript:addme(<%=isReadonly%>);" onKeyDown="Javascript:addme();"> 
			</div>
          </td>
		  <td width="9" height="17"></td>
			<!--  Added by zwsun ,2007/7/18, 审批流 -->
			<td>
			<fs:obApprovalinitbutton controlName="approvalInit" 
				value="保存并提交审批" 
				classType="button1" 
				onClickMethod="doSaveAndApprovalInit();" 
				officeID="<%=sessionMng.m_lOfficeID%>" 
				currencyID="<%=sessionMng.m_lCurrencyID%>" 
				clientID="<%=sessionMng.m_lClientID%>" 
				moduleID="<%=Constant.ModuleType.SETTLEMENT%>" 
				transTypeID="<%=OBConstant.SettInstrType.BUDGETADJUST%>"/>
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
    function add(isReadonly)
    {  	
    	if(isReadonly == <%=Constant.TRUE%>){
    		alert("没有可以调整的记录");
    		return false;
    	}
    	var txtColl = document.getElementsByTagName("INPUT");
    	for(var i=0;i<txtColl.length;i++){
    		var subInput = txtColl[i];
    		if(subInput.type == "text") {
    			if(subInput.value == 0.0 || subInput.value == ""){
    				alert("请依次输入用款金额");
    				return false;
    			}
    		}
    	}    	    	
		form_1.action = "../control/c004.jsp";
		if(confirm("确定提交？")){
			showSending();
        	form_1.submit();
        }
    }
     //保存并提交审批, Added by zwsun, 2007/7/18/
    function doSaveAndApprovalInit()
	{
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVEANDINITAPPROVAL%>";
		add();
	} 
	function addme(isReadonly){
		form_1.strAction.value="<%=OBConstant.SettInstrStatus.SAVE%>";
		add(isReadonly);		
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

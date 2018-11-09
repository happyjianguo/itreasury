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
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!-- 审批流 Added by zwsun, 2007/7/18 -->
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="wf"%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
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
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

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

	OBBudgetInfo info = request.getAttribute("info")==null?(new OBBudgetInfo()):(OBBudgetInfo)request.getAttribute("info");
	List list = (List)request.getAttribute("subInfoList");
       	//取消审批列表页面获取操作类型
		long operation = -1;
		if(request.getParameter("operation")!=null && ((String)request.getParameter("operation")).length()>0 && !(((String)request.getParameter("operation")).equals("null")))
		{
			operation = Long.parseLong(request.getParameter("operation"));
		}	
		
	//从"我的工作"传递的控制变量
	String strTempAction = "";
	if (request.getAttribute("strTempAction") != null) 
	{
		strTempAction = (String)request.getAttribute("strTempAction");
	}
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

<!-- Added by zwsun, 2007/7/19, 审批流 -->
<isswf:init/>
<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="id" value=<%=info.getId()%>>
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="OfficeID" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="CurrencyID" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
		<input type="hidden" name="sname" value="<%=info.getSname()%>">
		<input type="hidden" name="accountID" value="<%=info.getAccountID()%>">
		<input type="hidden" name="note" value="<%=info.getNote()%>">
<table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td><table border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 
            <!-- Added by zwsun, 2007/7/19, 审批流 -->
            <% if(operation == OBConstant.SettInstrStatus.CANCELAPPRVOAL){ %>
            		用款设置 - 用款新增 - 取消审批<%}else{ %>
           			用款设置 - 用款新增 - 审批
           	<%} %>
            </td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
</table>
<table width="774" border="1" cellspacing="0" cellpadding="0" class=normal align=center>
<%
		for(int i = 0 ; i < list.size() ; i ++){
			OBBudgetInfo subInfo = (OBBudgetInfo)list.get(i);
%>
		<tr align="center">
		<td height="25" class="MsoNormal" width="30%"><%=DataFormat.getDateString(subInfo.getStartdate())%></td>
		<td  height="25" class="MsoNormal" width="35%"><font color=red>*</font>用款金额：</td>
        <td  height="25" class="MsoNormal" width="35%"><%=sessionMng.m_strCurrencySymbol%> 
		<script  language="JavaScript">
			createAmountCtrl_standard("form_1","amount"+<%=i%>,false,"<%=subInfo.getAmount()%>","","","<%=sessionMng.m_lCurrencyID%>","disabled","");
		</script>
		</td>
		</tr>
<%		
		}
		long longtime= Env.getSystemDateTime().getTime();
 %>
 		<tr>
 			<td align="center" width="30%">导入文件：</td>
			<td valign="center" align="center" colspan="2">	
	           <wf:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=String.valueOf(info.getAdjunctID())%>' 
		        	caption = "上传" 
		        	lid = '-1'
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.BUDGETNEW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'/>						
			</td>
<input name="adjunctID" type="hidden" value=<%=longtime%> >
<input name="inputuser" type="hidden" value=<%=sessionMng.m_lUserID%> >
<input name="inputdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%> >
 		</tr>
    </table>	

      <br>
       
  <input name="sysdate" type="hidden" value=<%=DataFormat.getStringDateTime().substring(0,10)%>>
	  <!--汇款方式动态显示收款方资料-->

	  <!-- Added by zwsun, 2007/7/19, 审批流 -->
	  <!-- begin -->
	  <table border="0" width="774" cellspacing="0" cellpadding="0" align="center">
	  <TR>
		  <TD colspan="3">
			 <%--<iframe src="/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=<%=info.getId()+ ""%>&&transType=<%=OBConstant.SettInstrType.BUDGETNEW%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		  <wf:HistoryOpinionFrame
					  strTransNo='<%=info.getId()>0?String.valueOf(info.getId()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.BUDGETNEW%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					/>
		  </TD>
	  </TR>
	  <tr>
		    <%
		   	if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
			{
				//对已审批的任务，不再显示审批意见录入框
				if(info.getStatus() != OBConstant.OBBudgetStatus.APPROVE){
						String strMagnifierNameRemark = "审批意见";
						String strFormNameRemark = "form_1";
						String strMainPropertyRemark = "";
						String strPrefixRemark = "";
						String[] strMainNamesRemark = {com.iss.inut.workflow.constants.Constants.WF_ADVISE};
						String[] strMainFieldsRemark = {"Description"};
						String strReturnInitValuesRemark="";
						String[] strReturnNamesRemark = {"txtsOptionID"};
						String[] strReturnFieldsRemark = {"id"};
						String[] strReturnValuesRemark = {""};
						String[] strDisplayNamesRemark = {"审批意见编号","审批意见内容"};
						String[] strDisplayFieldsRemark = {"Code","Description"};
						int nIndexRemark = 1;
						String strSQLRemark = "select * from sys_approvalopinion where officeid="+sessionMng.m_lOfficeID+" and currencyid="+sessionMng.m_lCurrencyID+" and moduleid="+sessionMng.m_lModuleID+" and statusid="+Constant.RecordStatus.VALID;
						String strMatchValueRemark = "Description";
						//String[] strNextControlsRemark = {"strGeneratorCapacity","isShareHolder"};
						String strNextControlsRemark = "checkNextUser";
						String strTitleRemark = "审批意见";
						String strFirstTDRemark="align='center'";
						String strSecondTDRemark="colspan='2'";	
						Magnifier.showTextAreaCtrlForEbank(out,strMagnifierNameRemark,strFormNameRemark,strPrefixRemark,strMainNamesRemark,strMainFieldsRemark,strReturnNamesRemark,strReturnFieldsRemark,strReturnInitValuesRemark,strReturnValuesRemark,strDisplayNamesRemark,strDisplayFieldsRemark,nIndexRemark,strMainPropertyRemark,strSQLRemark,strMatchValueRemark,strNextControlsRemark,strTitleRemark,strFirstTDRemark,strSecondTDRemark);
				}		
			}
			%>   
		</tr> 
		<tr>
		<td colspan=3><br></td>
		</tr>
 	</table>		
   	<table width="100%"><tr><td align="right">
	<%
   	//当从取消审批列表进入时,显示取消审批按钮
   	if(operation==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
   		{
   	%>
		<input class="button1" name="ca" type="button" value=" 取消审批 " onClick="javascript:cancelApproval()" onKeyDown="javascript:cancelApproval()">

	     <isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval();" />

	<%	}
	else
		{
	%>
	     <isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval();" />

	<%	}
	%>	
	<input type="button" class="button1" value=" 返 回 " onclick="history.back();" />
	&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>


 	<!-- end -->
 
	 <!-- 刷新问题 -->
<input type="hidden" name="clickCount" value="0">
<!-- 刷新问题 --> 
</form>
 
<script language="javascript">
	/* 页面焦点及回车控制 */
	setFormName("form_1");
</script>

<script language="Javascript">
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//审批流审批
	function doApproval()
	{
		
		var frm=form_1;
		if(frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
		{
			alert("请输入审批意见");
			frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
			return;
		}
	    frm.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
		if (confirm("是否审批?")) 
		{					
			frm.strSuccessPageURL.value='<%=strContext%>/approval/view/v033.jsp';
			frm.strFailPageURL.value='../view/vApp009.jsp';
			frm.action= "../control/c001.jsp";
			showSending();			
			frm.submit();
		}
	}	

//取消审批
	function cancelApproval()
	{
		var frm=form_1;
	 	frm.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
		if (confirm("是否取消审批?")) 
		{						
			frm.strSuccessPageURL.value='<%=strContext%>/approval/view/v036.jsp';
			frm.strFailPageURL.value='../view/vApp009.jsp';
			frm.action= "../control/c001.jsp";
			showSending();			
			frm.submit();
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
<%--
/*
 * 程序名称：v005.jsp
 * 功能说明：资金计划复核入口  v005.jsp ————>c005.jsp ————> v006.jsp
 * 作　　者：ylguo
 * 完成日期：2008-10-25
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />

<%
String strContext = request.getContextPath();
%>
<%
	//标题变量
	String strTitle = null;
	try {
		/* 用户登录检测 */
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		/* 判断用户是否有权限 */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

		//显示文件头
		OBHtml.showOBHomeHead(out, sessionMng, strTitle,
		OBConstant.ShowMenu.YES);
		String startdate = "";
		String enddate = "";
		// Date _date = new Date();
        //下周一
		startdate = DataFormat.formatDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()));
    	//下周五日期
 		enddate = DataFormat.formatDate(DataFormat.getNextDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()),4));
%>
<form name="frm_fund_plan" method="post" action="">
	
	<!-- 成功与失败页面 -->
<input name="p_SuccessPageURL" type="hidden" value="../view/v005.jsp">
<input name="p_FailPageURL" type="hidden" value="../view/v005.jsp">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">资金计划复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>
</table>
<br/>
	<table width="80%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr><td></td></tr>
		<tr id="submitDate">
			<td width="5" height="25"></td>
			<td width="9%" height="25" class="graytext">
				计划日期：
			</td>
			<td width="67" height="25" class="graytext">
				<div align="right">
					由
				</div>
			</td>
			<td width="188" height="25" class="graytext">
			<fs_c:calendar 
         	    name="startdate"
	          	value="" 
	          	properties="nextfield ='enddate'" 
	          	size="18"/>
	            <script>
	          		$('#startdate').val('<%=startdate%>');
	          	</script>
			</td>
			<td width="98" height="25" class="graytext" align="right">
				<span class="graytext">至</span>
			</td>
			<td width="330" height="25" class="graytext">
				<fs_c:calendar 
	          	    name="enddate"
		          	value="" 
		          	properties="nextfield ='statusId'" 
		          	size="18"/>
		          	<script>
	          		$('#enddate').val('<%=enddate%>');
	          	</script>
			</td>
			<td width="8"></td>
		</tr>
		<tr id="commonStatus">
			<td width="5" height="25"></td>
			<td height="25" class="graytext" colspan="2">
				状态：
			</td>
			<td height="25" class="graytext" colspan="3">
				<%
						//状态
						OBHtmlCom.showQueryStatusListControlForfundPlan(out, "statusId", -1,"","submitfunction");
				%>
			</td>
			<td width="8" height="25"></td>
		</tr>

		<tr>
			<td colspan="7"></td>
			
			<td width="60">
					<input type="Button" class="button1" name="find" value=" 查 找 " width="46" height="18" onclick="javascript:toFind()">
			</td>
			<td>&nbsp;&nbsp;</td>
		</tr>
		<tr><td></td></tr>
	</table>
	<br>
<!-- 	<table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
		<tr>
			<td width="476">
				<%="&nbsp;"%>
			</td>
			
			<td width="60">
				<div align="right">
					<input type="Button" class="button1" name="find" value=" 查找 " width="46" height="18" onclick="javascript:toFind()">
				</div>
			</td>
		</tr>
	</table> -->

<script language="javascript">
    /* 页面焦点及回车控制 */
   firstFocus(frm_fund_plan.startdate);
   setFormName("frm_fund_plan");
   //setSubmitFunction("toFind()");
   function toFind()
   {
	   if (validate()) {
		   frm_fund_plan.action ="../control/c005.jsp?&clientId=<%=sessionMng.m_lClientID%>";
		   frm_fund_plan.p_SuccessPageURL.value ="../view/v006.jsp";
		   showSending();
		   frm_fund_plan.submit();
		}
	}

    /* 校验函数 */
    function validate() {
            var starSubmit = frm_fund_plan.startdate.value;
            var endSubmit = frm_fund_plan.enddate.value;
            if (starSubmit != "") {
                if(chkdate(starSubmit) == 0) {
                    alert("请输入正确的申报提交开始日期");
                    frm_fund_plan.startdate.focus();
                    return false;
                }
            }
            if (endSubmit != "") {
                if(chkdate(endSubmit) == 0) {
                    alert("请输入正确的申报提交结束日期");
                    frm_fund_plan.enddate.focus();
                    return false;
                }
            }
            if ((starSubmit != "") && (endSubmit != "")) {
                if (!CompareDate(frm_fund_plan.startdate, frm_fund_plan.enddate, 
                    "提交日期：起始日期不能大于结束日期")) {
                    return false;
                }
            }
			


            /* 执行日期校验 */
            var startExe = frm_fund_plan.startdate.value;
            var endExe = frm_fund_plan.enddate.value;
            if (startExe != "") {
                if(chkdate(startExe) == 0) {
                    alert("请输入正确的执行开始日期");
                    frm_fund_plan.startdate.focus();
                    return false;
                }
            }
            if (endExe != "") {
                if(chkdate(endExe) == 0)
                {
                    alert("请输入正确的执行结束日期");
                    frm_fund_plan.enddate.focus();
                    return false;
                }
            }
            if ((startExe != "") && (endExe != "")) {
                if (!CompareDate(frm_fund_plan.startdate, frm_fund_plan.enddate, 
                    "执行日期：起始日期不能大于结束日期")) {
                    return false;
                }
            }
            return true;
    }    
</script>

	<%
			/* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		} catch (IException ie) {
			OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",
			1);
		}
	%>
<%@ include file="/common/SignValidate.inc" %>
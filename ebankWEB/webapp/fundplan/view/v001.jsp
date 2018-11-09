<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：资金计划申报入口
 * 作　　者：ylguo
 * 完成日期：2008-10-21
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom,com.iss.itreasury.util.DataFormat,java.util.Date"%>

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
%>
<form name="frm_fund_plan" method="post" action="">
	<!-- 成功与失败页面 -->
	<input name="p_SuccessPageURL" type="hidden" value="../view/v003.jsp">
	<input name="p_FailPageURL" type="hidden" value="../view/v001.jsp">	
	<input type="hidden" name="clientId" value="<%=sessionMng.m_lClientID%>">
<table  cellpadding="0" cellspacing="0" class="title_top">
	<tr>
		<td height="22">
			<table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
					<td class=title><span class="txt_til2">资金计划申报</span></td>
					<td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

	<br/>
	
<%
	String startdate = "";       //提交日期-从
	String enddate = "";         //提交日期-到
	long statusId = -1;              //状态
	long clientId = sessionMng.m_lClientID;  //当前客户的ID
    // Date _date = Env.getSystemDateTime();//应该是数据库时间
       //下周一
	startdate = DataFormat.formatDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()));
   
    //下周五日期
 	enddate = DataFormat.formatDate(DataFormat.getNextDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()),4));
 	
 %>	
	 <tr>
	 <td>		
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr><td></td></tr>
		<tr id="submitDate">
			<td width="5" height="25"></td>
			<td width="9%" height="25" class="graytext">计划日期：</td>
			<td width="67" height="25" class="graytext"><div align="right">由</div></td>
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
			<td width="98" height="25" class="graytext" align="right"><span class="graytext">至</span></td>
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
		<tr>
			<td width="5" height="25"></td>
			<td height="25" class="graytext" colspan="2">
				状态：
			</td>
			<td height="25" class="graytext" colspan="3">
			<%
				//状态
				OBHtmlCom.showQueryStatusListControlForfundPlan(out, "statusId", statusId,"","");
			%>
			</td>
			<td width="8" height="25"></td>
		</tr>
		
		<tr>
			<td colspan="7">&nbsp;</td>
			<td width="60">
					<input type="Button" class="button1" name="btnFind" value=" 查 找 " width="46"  height="18" onclick="javascript:toFind()" onfocus="nextfield ='submitfunction'">
			</td>
			<td width="60">
					<input type="Button" class="button1" name="btnAdd" value=" 新 增 " onclick="javascript:toAdd()" onfocus="nextfield ='toAdd'">
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr><td>&nbsp;</td></tr>		
	</table>
	</td>
	</tr>
</td>
</tr>
</table>
</form>

<script language="javascript">
    /* 页面焦点及回车控制 */
   firstFocus(frm_fund_plan.startdate);
   setFormName("frm_fund_plan");
   //setSubmitFunction("toFind()");
    /*跳转至新增页面资金计划申报页面*/
	function toAdd()
	{	frm_fund_plan.action ="../control/c001.jsp";
		frm_fund_plan.p_SuccessPageURL.value ="../view/v004.jsp";
		frm_fund_plan.p_FailPageURL.value ="../view/v001.jsp";		   
		showSending();
		frm_fund_plan.submit();
	}
	
	function toFind()
	{
	   if (validate()) 
	   {
		   frm_fund_plan.action ="../control/c003.jsp";
		   frm_fund_plan.p_SuccessPageURL.value ="../view/v003.jsp";
		   frm_fund_plan.p_FailPageURL.value ="../view/v001.jsp";		   
		   showSending();
		   frm_fund_plan.submit();
		}
	}

    /* 校验函数 */
    function validate() 
    {
		var strStartDate = frm_fund_plan.startdate.value;
        var strEndDate = frm_fund_plan.enddate.value;
        if (strStartDate != "") {
           if(chkdate(strStartDate) == 0) {
               alert("请输入正确的申报提交开始日期");
               frm_fund_plan.startdate.focus();
               return false;
           }
        }
        if (strEndDate != "") {
            if(chkdate(strEndDate) == 0) {
                alert("请输入正确的申报提交结束日期");
                frm_fund_plan.enddate.focus();
                return false;
            }
        }
        if ((strStartDate != "") && (strEndDate != "")) {
            if (!CompareDate(frm_fund_plan.startdate, frm_fund_plan.enddate, 
                "提交日期：起始日期不能大于结束日期")) {
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
		OBHtml.showExceptionMessage(out, sessionMng, ie, strTitle, "",1);
	}
%>
<%@ include file="/common/SignValidate.inc" %>
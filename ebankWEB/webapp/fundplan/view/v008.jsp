<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
/*
 * 程序名称：v008.jsp     资金计划－查找————>当前页面————>c008.jsp
 * 功能说明：资金计划-查询入口页面
 * 作　　者：郭英亮
 * 完成日期：200８年1２月１日
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom,com.iss.itreasury.util.DataFormat,java.util.Date"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier,java.net.URLEncoder,com.iss.itreasury.ebank.fundplan.dataentity.CapitalPlanCondition" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include page="/ShowMessage.jsp"/>


<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<script type="text/javascript" src="/webob/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/webob/js/flexigrid.js"></script>
<script type="text/javascript" src="/webob/js/flexigridEncapsulation.js"></script>
<script type="text/javascript" src="/webob/js/suggest.js"></script>
<script type="text/javascript" src="/webob/js/jquery-ui-1.7.2.custom.min.js"></script>
<link rel="stylesheet" href="/webob/css/jquery-ui-1.7.2.custom.css" type="text/css">
<link rel="stylesheet" href="/webob/css/suggest.css" type="text/css">
<link rel="stylesheet" href="/webob/css/flexigrid.css" type="text/css">
<safety:resources />
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>

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
		String strContext = request.getContextPath();
%>
<form name="frm_fund_plan" method="post" action="">
	<!-- 成功与失败页面 -->
	<input name="strSuccessPageURL" type="hidden" value="<%=strContext %>/fundplan/view/v009.jsp">
	<input name="strFailPageURL" type="hidden" value="<%=strContext %>/fundplan/view/v008.jsp">	
	<input type="hidden" name="clientId" value="<%=sessionMng.m_lClientID%>">
	<input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID%>">
	<input type="hidden" name="officeId" value="<%=sessionMng.m_lOfficeID%>">
	<input type="hidden" name="cpCode" value="">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">资金计划查询</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	<br/>
<%
	
	String startdate = "";       //提交日期-从
	String enddate = "";         //提交日期-到
	long statusId = -1;              //状态
	// Date _date = Env.getSystemDateTime();//应该是数据库时间
       //下周一
	startdate = DataFormat.formatDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()));
   
    //下周五日期
 	enddate = DataFormat.formatDate(DataFormat.getNextDate(DataFormat.getFirstDateOfNextWeek(Env.getSystemDateTime()),4));
 	
 %>	
 <% 

		
		long lOfficeID = sessionMng.m_lOfficeID;
		long lCurrencyID = sessionMng.m_lCurrencyID;
		String strFormName = "frm_fund_plan";
		String strCtrlName = "cPIDStart";           //放大镜控件的名称：FundPlan+Ctrl
		String strTitle2 = "资金计划";             //放大镜控件图标前的label属性
		long lClientID = sessionMng.m_lClientID;
		String strFirstTD = "";	   //第一格的属性
		String strSecondTD = " ";	   //第二格的属性
		String[] strNextControls = {"cPIDEndCtrl"};  //下一个即将获取焦点的页面元素的名称
		//String strRtnClientNameCtrl = "FundPlanID";          //返回值（计划编号）对应的控件名
		/*OBMagnifier.createFundPlanCtrl(
		     out,
			 lOfficeID,
		     lCurrencyID,
		     strFormName,
		     strCtrlName,
		     strTitle2,
			 lClientID,
		     strFirstTD,
		     strSecondTD,
		     strNextControls
		);*/
		
%>	
	<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
		<TR><TD height="5" colspan="7"></TD></TR>
		<TR>						  											
			<td width="5" height="25"></td>
			<td width="130" height="25" align="left">资金计划：</td>
							  	 <td>
							
									<fs_c:dic id="cPIDStartCtrl" size="22" form="frm_fund_plan" title="资金计划" sqlFunction="getFundPlanSQL"  sqlParams='frm_fund_plan.cPIDStartCtrl.value,frm_fund_plan.currencyId.value,frm_fund_plan.clientId.value,frm_fund_plan.officeId.value' value="" nextFocus="cPIDEndCtrl" width="500">
										<fs_c:columns> 
											<fs_c:column display="序号" name="id" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="资金计划编号" name="cpcode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="cPIDStartCtrl" name="cpcode" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="cPIDStart" name="id" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic> 
							     </td>			
	  <% 
		long lOfficeID2 = sessionMng.m_lOfficeID;
		long lCurrencyID2 = sessionMng.m_lCurrencyID;
		String strFormName2 = "frm_fund_plan";
		String strCtrlName2 = "cPIDEnd";           //放大镜控件的名称：FundPlan+Ctrl
		String strTitle22 = "至";             //放大镜控件图标前的label属性
		long lClientID2 = sessionMng.m_lClientID;
		String strFirstTD2 = "";	   //第一格的属性
		String strSecondTD2 = " ";	   //第二格的属性
		String[] strNextControls2 = {"startdate"};  //下一个即将获取焦点的页面元素的名称
	/*	OBMagnifier.createFundPlanCtrl(
		     out,
			 lOfficeID2,
		     lCurrencyID2,
		     strFormName2,
		     strCtrlName2,
		     strTitle22,
			 lClientID2,
		     strFirstTD2,
		     strSecondTD2,
		     strNextControls2
		);*/
%>	
<td width="130" height="25" align="left">至：</td>
							  	 <td>
							
										
									<fs_c:dic id="cPIDEndCtrl" size="22" form="frm_fund_plan" title="资金计划" sqlFunction="getFundPlanSQL"  sqlParams='frm_fund_plan.cPIDEndCtrl.value,frm_fund_plan.currencyId.value,frm_fund_plan.clientId.value,frm_fund_plan.officeId.value' value="" nextFocus="startdate" width="500">
										<fs_c:columns> 
											<fs_c:column display="序号" name="id" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
											<fs_c:column display="资金计划编号" name="cpcode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
									</fs_c:columns>
										<fs_c:pageElements>
											<fs_c:pageElement elName="cPIDEndCtrl" name="cpcode" type="<%=PagerTypeConstant.STRING %>" elType="text" />
											<fs_c:pageElement elName="cPIDEnd" name="id" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
										</fs_c:pageElements>							
									</fs_c:dic> 
							     </td>			
      
	    <td width="8" height="25"></td>
	    <td width="8"></td>																													
   </TR>	
		
		<tr id="submitDate">
			<td width="5" height="25"></td>
			<td height="25" class="graytext">计划日期： 由</td>
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
			<td width="20" height="25" class="graytext" align="right"><span class="graytext">至：</span></td>
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
			<td width="5" height="25" colspan="1"  class="MsoNormal"></td>
		</tr>
		<tr>
			<td width="5" height="25"></td>
			<td height="25" class="graytext" >
				状态：
			</td>
			<td height="25" class="graytext" colspan="4">
			<%
				//状态
				OBHtmlCom.showQueryStatusListControlForfundPlan(out, "statusId", statusId,"","btnFind");
			%>
			</td>
			<td width="8" height="25"></td>
		</tr>
		<tr>
			<td colspan="7">
				<div align="right">
					<input type="Button" class="button1" name="btnFind" onkeydown="if(event.keyCode==13) document.frm_fund_plan.btnFind.click()" value=" 查 找 " width="46"  height="18" onclick="javascript:toFind()" onfocus="nextfield ='submitfunction'">&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</td>
		</tr>
		<TR><TD height="5" colspan="7"></TD></TR>
	</table>
   </td>
  </tr>
 </table>
</form>

<script language="javascript">
    /* 页面焦点及回车控制 */
   firstFocus(frm_fund_plan.cPIDStartCtrl);
   setFormName("frm_fund_plan");
   ////setSubmitFunction("toFind()");
   function toFind()
	{
	   if (validate()) 
	   {
		   frm_fund_plan.action ="../control/c008.jsp";
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
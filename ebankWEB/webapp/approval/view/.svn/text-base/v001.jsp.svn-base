<%
/**
 * 页面名称 ：v011.jsp
 * 页面功能 : 审批流设置－查询(新增)
 * 作    者 ：ygzhao
 * 日    期 ：2005-05-08
 * 特殊说明 ：
 * 转入页面 : 
 */
%>

<%@ page contentType="text/html;charset=gbk" %><%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>	
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier" %>	
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

	<% String strContext = request.getContextPath();%>
	<%	
    try{
		//请求检测
		/** 权限检查 **/
		String strTableTitle = "审批流设置";
		//用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTableTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		/* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);
	%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

	<FORM name="frm" method="post" action="../control/c011.jsp">		
		<input name="strAction" type="hidden">
		<input name="strSuccessPageURL" type="hidden" value="../view/v012.jsp">
		<input name="strFailPageURL" type="hidden" value="../view/v011.jsp">
		<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">审批设置</span></td>
        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>
	 <table width="774" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">

          <tr>
            <td width="3"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
            <td width="90" background="/webob/graphics/lab_conner2.gif" class="txt_til2"> 审批流设置</td>
            <td width="17"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
          </tr>
        </table></td>
      </tr>
    </table>
		<TABLE width="774" height="10" border="0" class="normal" align="center">
			<TBODY>
				<TR>
					<TD width="100%" height="40" vAlign="bottom">
						<fieldset>
						<TABLE align="center" border="0" width="100%">
							<TBODY>
								<TR>									
											<%
		long lOfficeID = -1;//sessionMng.m_lOfficeID;
		long lCurrencyID = -1;//sessionMng.m_lCurrencyID;
		String strFormName = "frm";
		String strCtrlName = "inputUserID";		
		String strTitle = "录入人";
		long lUserID = -1;
		String strUserName = "";
		String strFirstTD = "";
		String strSecondTD = "";
		String[] strNextControls = {"startDate"};
	OBMagnifier.createUserCtrl(
		out,
		lOfficeID,
		lCurrencyID,
		sessionMng.m_lClientID,
		strFormName,
		strCtrlName,
		strTitle,
		lUserID,
		strUserName,
		strFirstTD,
		strSecondTD,
		strNextControls);
 %>
									
									<TD width="18%" height="20">	
									</TD>
									<TD width="29%" height="20">										
									</TD>									
								</TR>
								<TR>									
									<td>	录入起始日期：
									</td>
									<td>
										<fs_c:calendar 
							          	    name="startDate"
								          	value="" 
								          	properties="nextfield ='endDate'" 
								          	size="20"/>
									</td>									
									<td>	录入终止日期：
									</td>
									<td>
										<fs_c:calendar 
							          	    name="endDate"
								          	value="" 
								          	properties="nextfield ='submitfunction'" 
								          	size="20"/>
							        </td>
								</TR>	
								<TR>									
									<td>	状态：
									</td>
									<td>
									<%
										//Constant.ApprovalStatus.showList(out,"statusID",-1,false,true,"");
									%>
									<select class='box' name="statusID">
									<option value='-1' selected>&nbsp;</option>
									<option value='<%=Constant.ApprovalStatus.SAVE%>'><%=Constant.ApprovalStatus.getName(Constant.ApprovalStatus.SAVE)%></option>
									<option value='<%=Constant.ApprovalStatus.SUBMIT%>'><%=Constant.ApprovalStatus.getName(Constant.ApprovalStatus.SUBMIT)%></option>
									</select>											
									</td>									
									<td>	
									</td>
									<td>										
									</td>
								</TR>							
							</TBODY>
						</TABLE></fieldset>
					</TD>
				</TR>
				<TR>
					<TD height="10" width="100%">
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<br>
		<TABLE align="center" height="15" width="97%">
							<TBODY>
								<TR>
									<TD colSpan='4' height="10">
										<DIV align=right>
                       						<INPUT name="btnSearch" type="button" class="button1" onClick="doSearch(frm);"  value=" 查询 ">
                  							<INPUT name="btnAdd" type="button" class="button1" onClick="location.href='../view/v014.jsp';"  value=" 新增 ">
										</DIV>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
	</form>
<script language="javascript">
var isSubmited = false;
function doSearch(frm)
{	
	if(isSubmited == true)
    {
        alert("请求已提交，请等候");
        return;
    }	
	//if(!validateFields(frm)) return;
	
	if(frm.startDate.value > frm.endDate.value)
	{
		alert("起始日期不能大于终止日期");
		frm.startDate.focus();
		return false;
	}
	isSubmited = true;
	showSending();
	document.frm.submit();	
}
function allFields()
{					
		this.aa = new Array("inputUserID","录入人","magnifier",0);
		this.ab = new Array("startDate","起始日期","date",0);
		this.ac = new Array("endDate","终止日期","date",0);		
} 
firstFocus(frm.inputUserIDCtrl);
//setSubmitFunction("doSearch(frm)");
setFormName("frm");
</script>
<%	
	/**
	* 现实文件尾
	*/
	OBHtml.showOBHomeEnd(out);	
}
//异常处理
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>
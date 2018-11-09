<%--
 页面名称 ：v201.jsp
 页面功能 : 用户密码设置
 作    者 ：liangpan
 日    期 ：2007-07-1
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.pwconfig.dataentity.*,
				com.iss.itreasury.ebank.pwconfig.dao.*,
				com.iss.itreasury.ebank.pwconfig.bizlogic.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.util.* ,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<%	
	//固定变量
	String strTitle = "";
	try{
	Log.print("进入用户密码设置页面----ebank/pwconfig/view/v201.jsp");
	
	//登录检测
	if( sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E002");
		out.flush();
		return;
	}
	//判断是否有权限
	if(sessionMng.hasRight(request)==false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		out.flush();
		return;
	}
	String title = "系统管理";
	/** 显示文件头 **/
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

	 /**
	 * 从request中取出页面相关的变量
	 */	
	PWConfigBean bean = new PWConfigBean();
    PasswordInfo passwordInfo = bean.getPasswordConfigInfo(sessionMng.m_lOfficeID); 	
%>
<script language="JavaScript">
function selectValue(lNumber,lLowercase,lCapital,lTerm,lSpecial,id,lForcePerfect){
	if(lNumber==1){
		document.getElementById("lNumber").checked=true;
	}
	if(lLowercase==1){
		document.getElementById("lLowercase").checked=true;
	}
	if(lCapital==1){
		document.getElementById("lCapital").checked=true;
	}
	if(lSpecial==1){
		document.getElementById("lSpecial").checked=true;
	}
	if(lTerm==1){
		document.getElementById("lTerm").checked=true;
		document.getElementById("dTermDate").value=<%=passwordInfo.getTermDate()%>;
		document.getElementById("dRemindDate").value=<%=passwordInfo.getRemindDate()%>;
	}else{
		document.getElementById("dTermDate").readOnly=true;
		document.getElementById("dRemindDate").readOnly=true;
		document.getElementById("dTermDate").value="";
		document.getElementById("dRemindDate").value="";
	}
	if(id == -1){
		document.getElementById("cancelButton").disabled = true;
	}
	if(lForcePerfect==1){
		document.getElementById("lForcePerfect").checked=true;
	}
}
</script>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<safety:resources />

 <table border="0" width="80%" bgcolor="#0099CC" cellspacing="0" cellpadding="0" align="">
 <form name="form1"  method="post">
	<!--页面控制变量-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="compriseNumber" value="">
	<input type="hidden" name="compriseCapital" value="">
	<input type="hidden" name="compriseLowercase" value="">
	<input type="hidden" name="compriseSpecial" value="">
	<input type="hidden" name="compriseTerm" value="">
	<input type="hidden" name="compriseForce" value="">
	<input type="hidden" name="isCancelConfig" value="">
 
  
  
  <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用户密码设置</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>
  
</table>
<br/>

    <table align="left" width="80%" valign="top" class=normal> 
  <tr align="center">
    <td width="100%" bgcolor=""  align="" >
      <table border="0" width="100%" cellspacing="1" align=""  >       
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" width="25%" nowrap colspan="5">
        		<input name="passwordLength" id="lPasswordLength" type="text" onchange="inputValidate()" maxlength="2" class="tar" size="12" value="<%=passwordInfo.getMinPassword()%>">&nbsp;密码长度(最小位数)</input>
        	</td>
        </tr>
        
        <tr><td colspan="6">&nbsp;</td></tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" width="15%">
        		<input name="number" id="lNumber" type="checkbox" onclick="generateExamplePassword()">&nbsp;数字</input>
        	</td>
        	<td height="25" width="20%">
        		<input name="lowercase" id="lLowercase" type="checkbox" onclick="generateExamplePassword()">&nbsp;小写字符</input>
        	</td>
        	<td height="25" width="20%">
        		<input name="capital" id="lCapital" type="checkbox" onclick="generateExamplePassword()">&nbsp;大写字符</input>
        	</td>
        	<td height="25" colspan="2">
        		<input name="special" id="lSpecial" type="checkbox" onclick="generateExamplePassword()">&nbsp;特殊字符</input>
        	</td>
        </tr>
        
        <tr><td colspan="6">&nbsp;</td></tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" width="20%" nowrap>
        		<input name="term" id="lTerm" type="checkbox" onclick="selectterm()">&nbsp;密码期限</input>
        	</td>
        	<td height="25" width="20%">
        		<input name="termDate" id="dTermDate" type="text" maxlength="2" class="box" size="12">&nbsp;天
        	</td>
        	<td height="25" width="25%">
        		到期提醒天数
        	</td>
        	<td height="25" width="25%">
        		<input name="remindDate" id="dRemindDate" type="text" maxlength="2" class="box" size="12">&nbsp;天
        	</td>
        </tr>
        
        <tr><td colspan="6">&nbsp;</td></tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		<input name="forcePerfect" id="lForcePerfect" type="checkbox">&nbsp;密码设置变更后是否强制不符合设置用户登录时修改密码</input>
        	</td>
        </tr>
        
        <tr><td colspan="6">&nbsp;</td></tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		密码示例：&nbsp;<input name="examplePassword" id="examplePassword" class="box" readonly>
        	</td>
        </tr>
        
        <tr><td colspan="6">&nbsp;</td></tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		密码设置说明：&nbsp;
        	</td>
        </tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		&nbsp;&nbsp;&nbsp;&nbsp;1.请尽量设置长密码，密码长度至少为6位，最大为20位。
        	</td>
        </tr>

        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		&nbsp;&nbsp;&nbsp;&nbsp;2.请尽量使用数字、字符、特殊字符等组合密码。
        	</td>
        </tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		&nbsp;&nbsp;&nbsp;&nbsp;3.请不要在您的密码中出现您的登录账号。如姓名、生日、身份证号码、电话号码等。
        	</td>
        </tr>
        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		&nbsp;&nbsp;&nbsp;&nbsp;4.请您每隔一段时间更新一次账号的密码。新密码不应与旧密码相似。
        	</td>
        </tr>      
          <tr>
        	<td width="1%">&nbsp;</td>
        	<td height="25" colspan="5">
        		&nbsp;&nbsp;&nbsp;&nbsp;5.[数字]、[小写字符]、[大写字符]、[特殊字符]勾选中则密码中必须包含，不勾选则可包含可不包含。
        	</td>
        </tr>  
        <tr><td colspan="6">&nbsp;</td></tr>        
        <tr>
        	<td width="1%">&nbsp;</td>
        	<td colspan="5" align="right">
        		<input name="shilipw1" type="button" class="button1" value=" 设 置 " onclick="setPWConfig()">&nbsp;
        		<input name="shilipw2" id="cancelButton" type="button" class="button1" value=" 取消设置 " onclick="cancelPWConfig()">&nbsp;&nbsp;&nbsp;&nbsp;
        	</td>
        </tr>
        <tr>
        <td height="5"></td></tr>
  </table>
</td>
</tr>
</table>
  </form>
<script language="JavaScript">
//去掉左右空格
String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

//style="background-color:#EFEFEF" border="0"
selectValue(<%=passwordInfo.getCompriseNumber()%>,<%=passwordInfo.getCompriseLowercase()%>,<%=passwordInfo.getCompriseCapital()%>,<%=passwordInfo.getCompriseTerm()%>,<%=passwordInfo.getCompriseSpecial()%>,<%=passwordInfo.getId()%>,<%=passwordInfo.getForcePerfect()%>);
generateExamplePassword();
function inputValidate(){
	var lPasswordLength = document.getElementById("lPasswordLength").value.Trim();
	if(!pattern.test(lPasswordLength) || lPasswordLength< <%=Constant.PASSWORD_MIN_LENGTH%> || lPasswordLength> <%=Constant.PASSWORD_MAX_LENGTH%>){
		alert("密码最小长度请输入<%=Constant.PASSWORD_MIN_LENGTH%>到<%=Constant.PASSWORD_MAX_LENGTH%>的整数");
		document.getElementById("examplePassword").value = "";
	}else{
		generateExamplePassword();
	}
}
function generateExamplePassword(){
	var strPassword = "0";
	var lPasswordLength;
	lPasswordLength = document.getElementById("lPasswordLength").value;
	lPasswordLength--;
	if(document.getElementById("lNumber").checked==true){
		strPassword += "1";
		lPasswordLength--;
	}
	if(document.getElementById("lLowerCase").checked==true){
		strPassword += "a";
		lPasswordLength--;
	}
	if(document.getElementById("lCapital").checked==true){
		strPassword += "A";
		lPasswordLength--;
	}
	if(document.getElementById("lSpecial").checked==true){
		strPassword += "@";
		lPasswordLength--;
	}
	for(var i=0;i<lPasswordLength;i++){
		var randomNumber = Math.floor(Math.random()*10);
		strPassword += randomNumber;
	}
	document.getElementById("examplePassword").value = strPassword;
}

function cancelPWConfig(){
	if(confirm("是否取消密码设置?")){
		form1.isCancelConfig.value = "1";
		form1.action="../control/c201.jsp";
		form1.strSuccessPageURL.value="../view/v201.jsp";
		form1.strFailPageURL.value="../view/v201.jsp";
		form1.submit();
	}
}

var pattern = /^[0-9]+$/;
function setPWConfig(){	
	var passwordLength = document.getElementById("lPasswordLength").value.Trim();
	var termDate = document.getElementById("dTermDate").value.Trim();
	var remindDate = document.getElementById("dRemindDate").value.Trim();

	passwordLength = parseInt(passwordLength);
	document.getElementById("lPasswordLength").value = passwordLength;
	document.getElementById("dTermDate").value = termDate;
	document.getElementById("dRemindDate").value = remindDate;
	if(passwordLength==""){
		alert("请输入密码最小长度");
		return;
	}
	if(!pattern.test(passwordLength)||passwordLength<6||passwordLength>20){
		alert("密码最小长度请输入6到20的整数");
		return;
	}
	if(document.getElementById("lTerm").checked == true){
		if(termDate == "" || remindDate == ""){
			alert("请输入密码期限天数或到期提醒天数");
			return;
		}
		if(termDate != "" && remindDate != ""){
			termDate = parseInt(termDate);
			remindDate = parseInt(remindDate);
		}
		if(!pattern.test(termDate) || !pattern.test(remindDate)||termDate==0||remindDate==0){
			alert("密码期限天数或到期提醒天数请输入正整数");
			return;
		}
		if(termDate<remindDate){
			alert("密码期限天数必须不小于到期提醒天数");
			return;
		}
	}
	if(document.getElementById("lNumber").checked == true){
		form1.compriseNumber.value = 1;
	}else{
		form1.compriseNumber.value = 0;
	}

	if(document.getElementById("lLowercase").checked == true){
		form1.compriseLowercase.value = 1;
	}else{
		form1.compriseLowercase.value = 0;
	}
	if(document.getElementById("lCapital").checked == true){
		form1.compriseCapital.value = 1;
	}else{
		form1.compriseCapital.value = 0;
	}
	if(document.getElementById("lSpecial").checked == true){
		form1.compriseSpecial.value = 1;
	}else{
		form1.compriseSpecial.value = 0;
	}
	if(document.getElementById("lTerm").checked == true){
		form1.compriseTerm.value = 1;
	}else{
		form1.compriseTerm.value = 0;
	}
	if(document.getElementById("lForcePerfect").checked == true){
		form1.compriseForce.value = 1;
	}else{
		form1.compriseForce.value = 0;
	}
	if(confirm("确定设置吗？")){
		form1.action="../control/c201.jsp";
		form1.strSuccessPageURL.value="../view/v201.jsp";
		form1.strFailPageURL.value="../view/v201.jsp";
		form1.submit();
	}
}

function selectterm(){
	if(document.getElementById("lTerm").checked==false){
		document.getElementById("dTermDate").readOnly=true;
		document.getElementById("dRemindDate").readOnly=true;
		document.getElementById("dTermDate").value="";
		document.getElementById("dRemindDate").value="";
	}else{
		document.getElementById("dTermDate").readOnly=false;
		document.getElementById("dRemindDate").readOnly=false;
	}
}



</script>
<%
		/** 显示文件尾 **/
		OBHtml.showOBHomeEnd(out);		
%>
<% 
	}catch(Exception e)
	{
	
		e.printStackTrace();
	}
%>

<%@ include file="/common/SignValidate.inc" %>
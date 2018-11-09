<%--
 页面名称 ：illustation.jsp
 页面功能 : 各银行收款方名称信息详细说明 
 作    者 : niweinan
 日    期 ：2011-4-02
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
String strTitle = "各银行收款方名称信息详细说明";


try{
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	//登录检测
   	if (!sessionMng.isLogin()){
       	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
       	OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
   	//检测权限
   	if (!sessionMng.hasRight(request)){
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
		OBHtml.showOBHomeEnd(out);
       	out.flush();
       	return;
   	}
%>



	<tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title nowrap><span class="txt_til2"><%=strTitle%></span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		</td>
	</tr>
	<tr>
		<td height="20">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal" style="word-break:break-all">
				<thead>
			        <tr>
			        	<td  height="18" align="center" rowspan="2" width="20%">银行名称</td>
						<td  height="18" align="center" rowspan="2" width="50%">收款方帐户长度限制</td>
						<td  height="18" align="center" rowspan="2" width="30%">备注</td>
						
			        </tr>
			          
			    </thead>
				<tbody>
					<tr>
			        	<td height="25" align="center" width="20%">中国银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-70个字符，最多能填写35个汉字</td>
						<td height="25" align="center" width="30%"></td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">中国农业银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-70个字符，最多能填写34个汉字</td>
						<td height="25" align="center" width="30%"></td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">中国工商银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-60个字符，最多能填写30个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">中国建设银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-60个字符，最多能填写30个汉字</td>
						<td height="25" align="center" width="30%">建设银行核心最多能保存40个字符，合计20个汉字</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">中国交通银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-60个字符，最多能填写30个汉字</td>
						<td height="25" align="center" width="30%"></td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">浦发银行</td>
						<td height="25" align="center" width="50%">可以为空，长度为1-62个字符，最多能填写31个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">北京银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-52个字符，最多能填写26个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">招商银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-124个字符，最多能填写62个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">中信银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-60个字符，最多能填写30个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">深圳发展银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-120个字符，最多能填写60个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
			        <tr>
			        	<td height="25" align="center" width="20%">光大银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-62个字符，最多能填写31个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
					<tr>
			        	<td height="25" align="center" width="20%">民生银行</td>
						<td height="25" align="center" width="50%">不能为空，长度为1-60个字符，最多能填写30个汉字</td>
						<td height="25" align="center" width="30%">&nbsp;</td>
					
			        </tr>
	
			    </tbody>
			</table>
		</td>
	</tr>


	<tr>
		<td height="10">&nbsp;</td>
	</tr>



<%
}
catch (IException ie){
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    Log.print(ie.getMessage());
}

%>

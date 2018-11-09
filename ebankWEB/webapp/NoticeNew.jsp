<%--
/*
 * 程序名称：NoticeNew.jsp
 * 功能说明：网银与SDC集成后，在网银端显示公告栏。页面对应的菜单为：公告栏。
 * 作    者 ：wangzhen
 * 日   期 ：2011-04-08
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.system.bulletin.dataentity.BulletinInfo"%>
<%@ page import="com.iss.itreasury.system.bulletin.bizlogic.BulletinBiz"%>
<%@ page import="com.iss.sysframe.pager.dataentity.FlexiGridInfo"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%
try{
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
	
	String strTitle = Env.getClientName() + "金融服务系统";
	String strBulletin = "";
    //用户登录检测 
    if (sessionMng.isLogin() == false)
    {
    	response.sendRedirect(strContext + "/Index.jsp");
    	out.flush();
    	return;
    }

    // 判断用户是否有权限 
    if (sessionMng.hasRight(request) == false)
    {
        out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
    	out.flush();
    	return;
    }
	
	//初始化公告栏
	BulletinBiz bulletinBiz=new BulletinBiz();
	BulletinInfo bulletinInfo = new BulletinInfo();
	bulletinInfo.setClients(String.valueOf(sessionMng.m_lClientID));
	bulletinInfo.setOfficeId(sessionMng.m_lOfficeID);
	strBulletin = bulletinBiz.findByBulletinString(bulletinInfo);
	 //显示文件头
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>
<form name="frm" method="get">
<input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID %>">
<input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID %>">
<table width=98% cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	   	  <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">公告信息栏</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
			
	<table class="top" width="100%" >
        <tbody>
        <tr>
          <td width="1%">&nbsp;</td>
	      <td>
		    <br> <TABLE width="100%" id="flexlist"></TABLE><br>
		  </td>
          <td width="1%">&nbsp;</td>
	   </tr>
	  </tbody>
	</table>
			
		</td>
	</tr>
</table>
<script language="javascript">
$(document).ready(function() {

	$("#flexlist").flexigridenc({
		colModel : [
        	{display: '公告名称',  name : 'SNOTE', width : 540, sortable : false, align: 'center'},
        	{display: '公告内容',  name : 'CPF_SREJECT', width : 540, sortable : false, align: 'center'}
		],//列参数
		title:'公告信息栏',
		classMethod : 'com.iss.itreasury.system.bulletin.Action.BulletinAction.notice',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false,
		height : 100,
		printbutton : false,
		exportbutton : false
	});
	
});
function getFormData() 
{
	return $.addFormData("frm","flexlist");
}
</script>
<%
}
catch(Exception ex){
	ex.printStackTrace();
	sessionMng.getActionMessages().addMessage("登录失败");
	response.sendRedirect(strContext + "/Index.jsp");
}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
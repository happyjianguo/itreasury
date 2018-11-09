<%--
 页面名称 ：v001.jsp
 页面功能 : 公告页面
 作    者 ：ruiwang
 日    期 ：2005-7-13
 特殊说明 ：转入页面c002.jsp
 修改历史 ：
--%>

<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.system.bulletin.dataentity.*" %>
<%
try
{
	String strTitle = "";
	 //用户登录检测 
	if (sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
		out.flush();
		return;
	}

	// 判断用户是否有权限 
	if (sessionMng.hasRight(request) == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
		out.flush();
		return;
	}
	/* 显示文件头 */
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.YES);
	%>
	<form name=frm001>
	<input type="hidden" name="strSuccessPageURL" value="/ebank/bulletin/v001.jsp">
	<input type="hidden" name="strFailPageURL" value="/ebank/bulletin/v001.jsp">
	<input type="hidden" name="searchAction" value="search">
	</form>
	<TABLE border=0 class=top width="90%" >
	<TR><TD class=FormTitle  height=21 colspan="3" align="left"><B><B>公告栏</B></B></TD></TR>
	<tr>
	<td class="ItemTitle" width="15%" height="20">
		<div align="center">	发布时间</div>
	</td>
	<td class="ItemTitle" width="20%" height="20">
		<div align="center">	公告标题</div>
	</td>
	<td class="ItemTitle" width="65%" height="20">
		<div align="center">	公告内容</div>
	</td>
	</tr>
	

	
	
		<% /**************************分页查询显示*********************/
						
		String strContext = request.getContextPath();
		String strPageLoaderKey=(String)request.getAttribute("_pageLoaderKey");
		%>
				
	
						<form name="frmV022" method="post" action="<%=strContext%>/bulletin/control/c001.jsp">
					  	<input type="hidden" name="strSuccessPageURL" value="/ebank/bulletin/view/v001.jsp">
						<input type="hidden" name="strFailPageURL" value="/ebank/bulletin/view/v001.jsp">
						<input type="hidden" name="lOrderParam" value="">
						 <input type="hidden" name="seqId" value="">
						<input type="hidden" name="strAction" value="">
						<input type="hidden" name="_pageLoaderKey" value="<%=strPageLoaderKey%>">
						
						<input type="hidden" name="code" value=''>
						</form>
						<%
						String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
						String strFailPageURL = (String)request.getAttribute("strFailPageURL");
					
						BulletinInfo[] infos =  null;
						if(request.getAttribute(Constant.PageControl.SearchResults)!=null)
						{
							infos = (BulletinInfo[]) request.getAttribute(Constant.PageControl.SearchResults);
							if(infos != null && infos.length>0)
							{
								for( int i=0; infos != null && i<infos.length; i++ )
								{
									System.out.println("**********************v001******************"+infos[i].getReleaseDate());
								 	%> 
								 	<tr class="ItemBody">
									 	<td align="center" height=21><%= infos[i].getReleaseDate() %></td>
									 	<td align="center" height=21><%= infos[i].getHeader() %></td>
									 	<td align="center" height=21><%= infos[i].getContent() %></td>
									 </tr>
							<%	}														
							}
						}
						else{
							//out.print("getAttribute(Constant.PageControl.SearchResults)=null");
						}
						%>
<!-- 分页控件 -->
  <tr height="20"> 
	<td width="100%" border="0" valign="bottom" colspan="5">
         <TABLE border="0" cellPadding=0 height=20 width="100%" class="ItemList">
         <TBODY>
             <TR>
                <TD height=20  class="ItemTitle">
                    <DIV align=right> 
                       <jsp:include page="/pagenavigator.jsp"  />  
                  </DIV>
				</TD>
			  </TR>
		  </TBODY>
		  </TABLE>
	 </TD>
	</TR>
  <tr> 
  </TABLE>
 
<%	/**
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
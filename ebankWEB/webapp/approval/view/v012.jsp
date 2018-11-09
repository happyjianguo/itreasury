<%--
 页面名称 ：v002.jsp
 页面功能 : 链接查找页面
 作    者 ：ygzhao
 日    期 ：2005-05-09
 特殊说明 ：简单实现说明：
				1、
 修改历史 ：
--%>
	<%@ page contentType="text/html;charset=gbk" %>
	<%@ page import="java.util.Collection" %>
	<%@ page import="java.util.Iterator" %>
	<%@ page import="java.sql.Timestamp" %>
	<%@ page import="com.iss.itreasury.util.Constant" %>
	<%@ page import="com.iss.itreasury.util.Log" %>
	<%@ page import="com.iss.itreasury.util.Env" %>
	<%@ page import="com.iss.itreasury.util.DataFormat" %>
	<%@ page import="com.iss.itreasury.ebank.util.*" %>
	<%@ page import="com.iss.itreasury.ebank.util.NameRef" %>
	<%@ page import="com.iss.itreasury.ebank.approval.dataentity.ApprovalSettingInfo" %>	
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
try
{
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

	Collection resultColl = (Collection)request.getAttribute("searchResults");
	Iterator itResult = null;
	if(resultColl != null)
	{
		itResult = resultColl.iterator();
	}
	String strContext = request.getContextPath();
%>
	<jsp:include page="/ShowMessage.jsp"/>
	
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<safety:resources />
	
	<form name="frm" method="post" action="../control/c011.jsp">		
		<input type="hidden" name="strSuccessPageURL" value="../view/v002.jsp">
		<input type="hidden" name="strFailPageURL" value="../view/v002.jsp">		
		<TABLE class="top" height="100" width="80%">
			<TBODY>
				<TR>
					<TD class="FormTitle" height="13"><B>审批流设置</B>
					</TD>
				</TR>				
				<TR>
					<TD height="100" valign="top">
						<TABLE align="center" border="0" class="ItemList" height="70" width="99%">
							<TBODY>
								<TR align="center">
									<td class="ItemTitle"  height="20">
										<div align="center">	编号
										</div>
									</td>
									<TD class="ItemTitle" height="20" nowrap>	审批流名称
									</TD>
									<TD class="ItemTitle" height="20" nowrap>	审批最大级别
									</TD>
									<TD class="ItemTitle" height="20" nowrap>	录入人
									</TD>
									<TD class="ItemTitle" height="20" nowrap>	录入时间
									</TD>
									<TD class="ItemTitle" height="20" nowrap>	状态
									</TD>									
								</TR>
								<%
if(itResult != null && itResult.hasNext())
{	
	while(itResult.hasNext())
	{
		ApprovalSettingInfo info = (ApprovalSettingInfo)itResult.next();
		
		long ID = info.getID();//审批设置标示
		String Name = info.getName();	//审批流名称
    	long lTotalLevel = info.getTotalLevel();//最大审批级别
    	long InputUserID = info.getInputUserID();//录入人
    	Timestamp InputDate = info.getInputDate();//录入时间
		long StatusID = info.getStatusID();//状态
		
		String strPageURL = "../control/c011.jsp?strSuccessPageURL=../view/v013.jsp&strFailPageURL=../view/v013.jsp&strAction=toModify&lApprovalID="+info.getID();
	
%>
								<TR align="center">
									<td class="ItemBody" width="9%" height="20">
										<div align="center"><A href="<%=strPageURL%>">
											<%=ID%></A>
										</div>
									</td>
									<TD class="ItemBody" height="20">
										<%=DataFormat.formatEmptyString(Name)%>
									</TD>
									<TD class="ItemBody" height="20">
										<%=lTotalLevel%>
									</TD>
									<TD class="ItemBody" height="20">
										<%=NameRef.getUserNameByID(info.getInputUserID())%>
									</TD>
									<TD class="ItemBody" height="20" nowrap> 
										<%=DataFormat.formatDate(InputDate)%>
									</TD>
									<TD class="ItemBody" height="20" align="center">
										<%=Constant.ApprovalStatus.getName(StatusID)%>
									</TD>									
								</TR>
								<%
	}
}
else
{
%>
							<TR>
								<TD class="ItemBody" height="20">	&nbsp;
								</TD>
								<TD class="ItemBody" height="20">	&nbsp;
								</TD>
								<TD class="ItemBody" height="20">	&nbsp;
								</TD>
								<TD class="ItemBody" height="20">	&nbsp;
								</TD>
								<TD class="ItemBody" height="20">	&nbsp;
								</TD>
								<TD class="ItemBody" height="20">	&nbsp;
								</TD>								
							</TR>
<%
}
%>
							</TBODY>
						</TABLE>
						<TABLE align="center" height="26" width="99%">
							<TBODY>
								<TR valign=middle>
									<TD width="70%" height="20">										
										<DIV align="right">											
											<INPUT class="button" name="btnReturn" type="button" value=" 返 回 " onClick="doReturn();">
										</DIV>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>
	<%
	/**
	* 现实文件尾
	*/
	OBHtml.showOBHomeEnd(out);	
%>
<script language="JavaScript">
function doReturn()
{
    document.location.href="../view/v001.jsp";
}
</script>
<%
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>
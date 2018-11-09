<%--
 页面名称 ：v050.jsp
 页面功能 : 定期开立复核-链接查找页面
 作    者 ：xirenli
 日    期 ：2003-09-21
 特殊说明 ：简单实现说明：
				1、
 修改历史 ：
--%>
	<%@ page contentType="text/html;charset=gbk" %>
	<%@ page import="java.util.Collection" %>
	<%@ page import="java.net.URI" %>
	<%@ page import="java.util.Iterator" %>
	<%@ page import="com.iss.itreasury.util.Log" %>
	<%@ page import="com.iss.itreasury.ebank.util.*" %>
	<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
	<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*" %>
	<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<jsp:include page="/ShowMessage.jsp"/>

	<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

	<%
try
{
	/* 标题固定变量 */
	String strTitle = "[换开定期存单]";
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	String strTamp = request.getParameter("type");
	String type = "";
	if(strTamp == null)
		System.out.println("type为null，非链接查询页面");
	else
		type = strTamp;
	strTamp = request.getParameter("lID");
	String lID ="";
	if(strTamp == null)
		System.out.println("lID为null，非链接查询页面");
	else{
		lID = strTamp;
	}
		
	
	/* 用户登录检测 
    if (sessionMng.isLogin() == false)
    {
    	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
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
    }*/
	long lStatusID = -1;
	boolean isDesc = false;
	String strActionText="";

	String strTemp = null;	
	strTemp = (String)request.getAttribute("isDesc");
	if(strTemp != null && strTemp.length()>0)
	{
		isDesc = Boolean.valueOf(strTemp).booleanValue();
	}
	
	strTemp = (String)request.getAttribute("strActionText");
	if(strTemp != null && strTemp.length()>0)
	{
		strActionText = strTemp.trim();
	}
	
	Collection resultColl = (Collection)request.getAttribute("searchResults");
	Iterator itResult = null;

	if(resultColl != null)
	{
		itResult = resultColl.iterator();
	}
	OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>

<safety:resources />

	<form name="frmf022" method="post" action="/f021-c.jsp">
		<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
		<input name="strAction" type="hidden">
		<input name="lTransactionTypeID" type="hidden" value="<%=SETTConstant.TransactionType.OPENFIXEDDEPOSIT%>">
		<input type="hidden" name="nOrderByCode">
		<input type="hidden" name="isDesc" value="<%=isDesc%>">
		<table width="99%" border="0" class="top" height="100">
			<tr>
				<td height="2" class="FormTitle" width="100%" colspan="2"><b>业务处理 —— 换开定期存单</b>
				</td>
			</tr>
			<tr>
			<td height="5" width="100%" valign="bottom" colspan="2">
			</td>
		</tr>
			<tr>
				<td height="5" width="100%" valign="bottom" colspan="2">
<hr>
					<table width="99%" border="0" align="center" height="70" class="ItemList">
						<tr>
							<td class="ItemTitle" width="7%" height="20">
								<div align="center">交易号
								</div>
							</td>
							<td class="ItemTitle" width="6%" height="20">
								<div align="center">业务类型
								</div>
							</td>
							<td class="ItemTitle" width="9%" height="20">
								<div align="center">定期账号
								</div>
							</td>
							<td class="ItemTitle" width="10%" height="20">
								<div align="center">定期客户名称
								</div>
							</td>
							<td class="ItemTitle" width="9%" height="20">
	  
              <div align="center">定期存款期限（月）
							</div>
						</td>
				<td class="ItemTitle" width="10%" height="20">
				<div align="center">活期账号
				  </div>

						  </td>
						<td class="ItemTitle" width="7%" height="20">
							<div align="center">活期客户名称
						  </div>
				</td>
				<td class="ItemTitle" width="8%" height="20">
					<div align="center">交易金额
					</div>
				</td>
				<td class="ItemTitle" width="8%" height="20">
					<div align="center">执行日
					</div>
				</td>
				<td class="ItemTitle" width="8%" height="20">
					<div align="center">汇款用途/摘要
					</div>
				</td>
				<%if(type.equals("linkSearch")){ %>				
				<td class="ItemTitle" width="8%" height="20">
					<div align="center">换开状态
					</div>
				</td>
				<%} %>
			</tr>
			<%
if(itResult != null && itResult.hasNext())
{
	while(itResult.hasNext())
	{
		FinanceInfo info = (FinanceInfo)itResult.next();
		
		String strId = new Long(info.getID()).toString();   //交易号
		String strTransactionType = new Long(info.getTransType()).toString();  //交易类型		
		String strPayeeAcctNo = info.getPayeeAcctNo();   //定期账号
		String strPayeeName = info.getPayeeName();   //定期客户名称
		String strFixedDepositTime = new Long(info.getFixedDepositTime()).toString();//定期存款期限（月）
		String strPayerAcctNo = info.getPayerAcctNo();//活期账号
		String strPayerName = info.getPayerName();   //活期客户名称
		String strAmount = info.getFormatAmount();//交易金额
		String strExecuteDate = info.getExecuteDate().toString().substring(0,10);//执行日
		String strNote = info.getNote();//汇款用途/摘要
		long DepositBillStatusId = info.getNDepositBillStatusId();//换开定期存单交易状态
%>
			<tr>
				<td class="ItemBody" width="7%" height="20">
					<div align="center">
					<%
					String strUri = "";
					if(type.equals("linkSearch"))
						strUri = "toModify";
					else
						strUri = "toCreate";
					URI uri = new URI("f023-c.jsp?lID=" + strId + "&strAction=" + strUri); %>
						<A href="<%=uri%>">
							<%=strId%>
						</A>
					</div>
				</td>
				<td class="ItemBody" width="6%" height="20">
					<div align="center">
						定期开立
					</div>
				</td>
				<td class="ItemBody" width="9%" height="20">
					<div align="center">
						<%=strPayeeAcctNo%>
					</div>
				</td>
				<td class="ItemBody" width="10%" height="20">
					<div align="center">
						<%=strPayeeName%>
					</div>
				</td>
				<td class="ItemBody" width="9%" height="20">
					<div align="center">
						<%=strFixedDepositTime%>
					</div>
				</td>
				<td class="ItemBody" width="10%" height="20">
					<div align="center">
						<%=strPayerAcctNo%>
					</div>
				</td>
				<td class="ItemBody" width="7%" height="20">
					<div align="center">
						<%=strPayerName==null?"&nbsp;":strPayerName%>
					</div>
				</td>
				<td class="ItemBody" width="8%" height="20">
					<div align="right">
						<%= sessionMng.m_strCurrencySymbol %><%=strAmount%>
					</div>
				</td>
				<td class="ItemBody" width="8%" height="20">
					<div align="center">
						<%=strExecuteDate%>
					</div>
				</td>
				<td class="ItemBody" width="8%" height="20">
					<div align="center">
						<%=strNote != null?strNote:"无"%>
					</div>
				</td>				
				<%if(type.equals("linkSearch")){ %>				
				<td class="ItemBody" width="8%" height="20">
					<div align="center">
						<%=OBConstant.SettInstrStatus.getName(DepositBillStatusId) %>
					</div>
				</td>
				<%} %>
			</tr>
			<%
	}
}
else
{
%>
					<tr>
						<td class="ItemBody" width="7%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="6%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="9%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="10%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="9%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="10%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
				
						<td class="ItemBody" width="7%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="8%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="8%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<td class="ItemBody" width="8%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>									
						<%if(type.equals("linkSearch")){ %>				
						<td class="ItemBody" width="8%" height="20">
							<div align="center">&nbsp;
							</div>
						</td>
						<%} %>		
					</tr>

<%
}		
			if(type.equals("linkSearch")){
%>
		
			<tr align="right">
				<td align="right"> 
				<%
				String strUri = "toCreate&lID=" + sessionMng.getCurrentKey();
				%>
				<input name="retrun" type="submit" class="button" id="retrun"  onClick="document.frmf022.action='f023-c.jsp?strAction=<%=strUri %>';
					showSending();//显示正在执行
					frmf022.submit();" value="返回" />
				</td>
			</tr>
			<%} %>
		</table>
</td>
</tr>
		
</table>
	</form>

<%

if(lShowMenu == OBConstant.ShowMenu.YES)
{	/* 显示文件尾 */
	OBHtml.showOBHomeEnd(out);
}
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%	    
%>
<%@ include file="/common/SignValidate.inc" %>
<%/**
			 * 页面名称 ：q073-v.jsp
			 * 页面功能 : 查询——贷款展期申请查询——结果显示的页面
			 * 作    者 ：ninghao
			 * 日    期 ：2003-11-18
			 * 特殊说明 ：
			 *			
			 * 修改历史 ：
			 */
%>
<%@page contentType="text/html;charset=gbk"%>

<%@page
	import="java.util.*,java.sql.*,java.net.URLEncoder,java.rmi.RemoteException,com.iss.itreasury.util.*,com.iss.itreasury.loan.util.*,com.iss.itreasury.ebank.util.*,com.iss.itreasury.ebank.obdataentity.*,com.iss.itreasury.ebank.obquery.bizlogic.*,com.iss.itreasury.ebank.obquery.dataentity.*,com.iss.itreasury.loan.query.dataentity.*,com.iss.itreasury.loan.query.bizlogic.*"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB" />
<jsp:useBean id="termInfo" scope="session"
	class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<%String strOfficeName = sessionMng.m_strOfficeName;
			String strUserName = sessionMng.m_strUserName;
			long lUserID = sessionMng.m_lUserID;
			String strTableTitle = "贷款展期申请查询";
			//////////////////////////////////////////////////////////////////////////////////////////////

			try {
				//判断是否登录//CODE_COMMONMESSAGE_LOGIN=1
				if (!sessionMng.isLogin()) {
					OBHtml.showMessage(out, sessionMng, null, "登录", null,
							Constant.RecordStatus.INVALID, "Gen_E002");
					OBHtml.showOBHomeEnd(out);
					out.flush();
					return;
				}
				//判断是否有权限
				if (sessionMng.hasRight(request) == false) {
					OBHtml.showMessage(out, sessionMng, null, "登录", null,
							Constant.RecordStatus.INVALID, "Gen_E003");
					OBHtml.showOBHomeEnd(out);
					out.flush();
					return;
				}

				//控制层传递过来的查询条件和查询结果

				ArrayList queryResults = null;
				queryResults = (ArrayList) request.getAttribute("Result");
				QuerySumInfo sumInfo = (QuerySumInfo) request
						.getAttribute("SumInfo");
				String strSumAmount = DataFormat.formatDisabledAmount(sumInfo
						.getTotalApplyAmount(), 0);

				//显示文件头
				String queryLevel = GetParam(request, "queryLevel", "low");
				OBHtml.showOBHomeHead(out, sessionMng, strTableTitle,
						Constant.YesOrNo.YES);
				String actType = GetParam(request, "actType", "");

				%>


<form name="frmV001"><input type='hidden' name="actType"
	value="<%=actType%>"><!--标志位，即可以标志是带有请求的页面迁移，还可以标志当前要求的行动类型-->
<table width="99%" border="0" class="top">

	<TR class="tableHeader">
		<TD class=FormTitle><B><%=strTableTitle%></B></TD>
	</TR>
	<tr>
		<td height="50" valign="top" width="100%">
		<table width="99%" border="5" align="center" class="ItemList">
			<tr class="tableHeader">
				<td align="center" class="ItemTitle">贷款类型</td>
				<td align="center" class="ItemTitle">合同编号</td>
				<td align="center" class="ItemTitle">借款单位</td>
				<td align="center" class="ItemTitle">委托单位</td>
				<td align="center" class="ItemTitle">展期申请号</td>
				<td align="center" class="ItemTitle">贷款金额</td>
				<td align="center" class="ItemTitle">展期金额</td>
				<td align="center" class="ItemTitle">合同利率</td>
				<td align="center" class="ItemTitle">执行利率</td>
				<td align="center" class="ItemTitle">展期利率</td>
				<td align="center" class="ItemTitle">贷款日期</td>
				<td align="center" class="ItemTitle">展期状态</td>
				<td align="center" class="ItemTitle">下一级审核人</td>
			</tr>
			<%if ((queryResults != null) && (queryResults.size() > 0)) {
					//	System.out.println(queryResults.size());
					for (int i = 0; i < queryResults.size(); i++) {
						QuestExtendInfo info = new QuestExtendInfo();
						info = (QuestExtendInfo) queryResults.get(i);
						String ExtendCode = "";
						if (info.getExtendNo() < 10) {
							ExtendCode = "0" + info.getExtendNo();
						} else {
							ExtendCode = "" + info.getExtendNo();
						}

						%>
			<tr>
				<td align="center" class="ItemBody"><%=DataFormat.formatString(info.getLoanType())%>
				</td>
				<td align="center" class="ItemBody"><%=DataFormat.formatString(info
										.getContractCode())%></td>
				<td align="center" class="ItemBody"><%=DataFormat.formatString(info
												.getClientName())%></td>
				<td align="center" class="ItemBody"><%=DataFormat.formatString(info
										.getConsignClientName())%></td>
				<td align="center" class="ItemBody"><a
					href="javascript:frmGoto(<%=info.getExtendID()%>,<%=info.getTypeID()%>)">
				<%=DataFormat.formatString(ExtendCode)%> </a></td>
				<td align="right" class="ItemBody"><%=sessionMng.m_strCurrencySymbol
										+ DataFormat.formatListAmount(info
												.getAmount())%></td>
				<td align="right" class="ItemBody"><%=sessionMng.m_strCurrencySymbol
								+ DataFormat.formatListAmount(info
										.getExtendAmount())%></td>
				<td align="right" class="ItemBody"><%=DataFormat.formatRate(info
												.getContractRate())%>%</td>
				<td align="right" class="ItemBody"><%=DataFormat.formatRate(info.getRate())%>%
				</td>
				<td align="right" class="ItemBody"><%=DataFormat.formatRate(info.getExtendRate())%>%
				</td>
				<td align="center" class="ItemBody"><%=DataFormat.getDateString(info.getDateFrom())
								+ "至"
								+ DataFormat.getDateString(info.getDateTo())%></td>
				<td align="center" class="ItemBody"><%=LOANConstant.ExtendStatus.getName(info
										.getStatusID())%></td>
				<td align="center" class="ItemBody"><%=DataFormat.formatString(info
										.getNextCheckUserName())%></td>
			</tr>
			<%}
				} else {

				%>
			<tr>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
				<td align="center" class="ItemBody">&nbsp;</td>
			</tr>
			<%}

				%>
			<tr>
				<td colspan='13'><%//输出分页控件
				OBHtml.showTurnPageControl(out, "turnPageFrm",
						"q072-c.jsp?queryLevel=" + queryLevel, sumInfo
								.getAllRecord(), (termInfo == null ? 1
								: termInfo.getTxtPageNo()),
						Constant.PageControl.CODE_PAGELINECOUNT,
						(termInfo == null ? 99 : termInfo.getLOrderParam()),
						(termInfo == null ? 1 : termInfo.getLDesc()));
%></td>
			</tr>
			<TR >
				<td colspan='13'>
				<table width="100%" border="0" cellpadding="5" bgcolor="#ffffff">
					<tr>
						<td><B>总申请贷款金额:</B> <INPUT class=tar maxLength=25
							name=textfield32222 size=25
							value="<%=sessionMng.m_strCurrencySymbol+strSumAmount%>" disabled>
						</TD>
						<TD>&nbsp;</TD>
					</TR>
				</table>
				</TD>
			</TR>
			<TR>
		</table>
		</td>
	</tr>

	<tr class="tableHeader">
		<td colspan=13></td>
	</tr>
	<!--  	<TR borderColor=#999999>
		<TD class=SearchBar colSpan=13 height=2>
	  	<TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar height=50
			width="100%"> -->




	<tr>
		<td>
		<table width="100%" border="0" cellpadding="5">
			<tr>
				<td>查询数据时间为：<%=DataFormat.getDateString(Env.getSystemDate())%></td>
				<td align="right"><input type="button" name="print"
					onclick="javasctript:window.print();" value=" 打印 " class="button"
					onClick="dailyReport()"> <%if (actType.equals("contract")) {%> <input
					type="button" name="butReturn" value=" 关闭 " class="button"
					onClick="closePage()"> <%} else {

				%> <input type="button" name="butReturn" value=" 返回 " class="button"
					onClick="returnPage()"> <%}

				%></td>
			</tr>
		</table>
		</td>
	</tr>


</table>
</form>

<script language='javascript'>
function closePage()
{
	window.close();
}
function frmGoto(ExtendID,LoanTypeID)
{
	window.open('q075-v.jsp?control=view&attribtype=4&isSM=<%=Constant.YesOrNo.NO%>&lExtendID='+ExtendID+'&loantype='+LoanTypeID,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}
function returnPage()
{
<%
	if ( queryLevel.equals("low") ){
%>	
	window.location.href="../query/q070-v.jsp";
<%
	}else{
%>	
	window.location.href="../query/q071-v.jsp";
<%
	}
%>	
}
</script>
<%OBHtml.showOBHomeEnd(out);
			} catch (IException ie) {
				//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,strTableTitle, Constant.RecordStatus.VALID);
				out.flush();
				return;
			}

		%>
<%@ include file="/common/SignValidate.inc" %>
<%@ page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session"
	class="com.iss.itreasury.ebank.util.SessionOB" />
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>	
<%@ page
	import="com.iss.itreasury.util.*,java.util.*,java.sql.*,java.lang.*,com.iss.itreasury.loan.util.*,com.iss.itreasury.ebank.util.*,com.iss.itreasury.ebank.obdataentity.*,com.iss.itreasury.ebank.obquery.bizlogic.*,com.iss.itreasury.ebank.obquery.dataentity.*,com.iss.itreasury.loan.repayplan.dataentity.*,com.iss.itreasury.loan.contract.dataentity.*,com.iss.itreasury.loan.repayplan.bizlogic.*"%>
<%try {
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

				// 定义变量
				long lPageCount = 1;
				long lPageNo = 1;
				long lOrderParam = 1;
				long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
				long lDescTmp = Constant.PageControl.CODE_ASCORDESC_DESC;//临时传输变量
				String strcontrol = "";//控制动作
				String strTmp = "";

				long lID = -1;

				//获取strcontrol
				if (request.getParameter("control") != null)
					strcontrol = request.getParameter("control");

				strTmp = request.getParameter("nTmpID");
				if (strTmp != null && strTmp.length() > 0) {
					try {
						lID = Long.parseLong(strTmp);
					} catch (Exception e) {
						lID = -1;
					}
				} else {
					out.println("没有传标识！");
					strcontrol = "";
				}

				long lShowMenu = Constant.YesOrNo.YES;
				strTmp = request.getParameter("isSM");
				if (strTmp != null && strTmp.length() > 0) {
					lShowMenu = Long.parseLong(strTmp);
				}
				long lYU = -1;
				strTmp = request.getParameter("isYU");
				if (strTmp != null && strTmp.length() > 0) {
					lYU = Long.parseLong(strTmp);
				}

				//判断正序和反序
				if (request.getParameter("nDesc") != null) {
					lDesc = Long.valueOf(request.getParameter("nDesc"))
							.longValue();
					if (lDesc == Constant.PageControl.CODE_ASCORDESC_ASC) {
						lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
						lDescTmp = Constant.PageControl.CODE_ASCORDESC_ASC;
					} else {
						lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
						lDescTmp = Constant.PageControl.CODE_ASCORDESC_DESC;
					}

				}
				//判断排序条件
				if (request.getParameter("nOrderParam") != null) {
					lOrderParam = Long.valueOf(
							request.getParameter("nOrderParam")).longValue();
				}

				//显示页数
				if (request.getParameter("nPageNo") != null) {
					lPageNo = Long.valueOf(request.getParameter("nPageNo"))
							.longValue();
				}

				if (strcontrol.equals("view")) {
					// 初始化EJB

					OBContractQuery repayplan = new OBContractQuery();

					%>
<%OBHtml.showOBHomeHead(out, sessionMng, "执行计划", lShowMenu);

					%>

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<table width="87%" border="0" class="top" height="60">
	<tr class="tableHeader">
		<td class="FormTitle" height="29" colspan=4><b>执行计划——查看</b></td>
	</tr>
	<tr>
	<tr>
		<td height="75" align="center" colspan=4>
		<hr>
		<form name="frmOrder" action="q094-v.jsp?control=view" method="post">
		<input type="hidden" name="nTmpID" value="<%=lID%>"> <input
			type="hidden" name="nPageNo" value="<%=lPageNo%>"> <input
			type="hidden" name="nOrderParam" value="<%=lOrderParam%>"> <input
			type="hidden" name="nDesc" value="<%=lDesc%>"> <input type="hidden"
			name="nTmpID" value="<%=lID%>"> <input type="hidden" name="isSM"
			value="<%=lShowMenu%>"> <input type="hidden" name="isYU"
			value="<%=lYU%>">
		</form>
		<table border="0" bordercolor="#999999" class="ItemList" width="750">
			<tr bordercolor="#999999" bgcolor="#CCCCCC" align="center"
				class="tableHeader">
				<td class="ItemTitle" width="18%" height="14" align="center"><a
					href="javascript:order(frmOrder,1)">执行计划日期</a></td>
				<td class="ItemTitle" width="10%" height="14" align="center"><a
					href="javascript:order(frmOrder,2)">放款/还款</a></td>
				<td class="ItemTitle" width="20%" height="14" align="center"><a
					href="javascript:order(frmOrder,3)">金额</a></td>
				<td class="ItemTitle" width="10%" height="14" align="center"><a
					href="javascript:order(frmOrder,4)">类型</a></td>
				<td class="ItemTitle" width="15%" height="14" align="center">计划执行利率</td>
				<td class="ItemTitle" width="25%" height="14" align="center"><a
					href="javascript:order(frmOrder,6)">修改日期</a></td>
			</tr>

			<%String showRepay = "";
					Collection c = null;
					if (lYU == 2) {
						c = repayplan.findPlanByVer(lID,
								Constant.PageControl.CODE_PAGELINECOUNT,
								lPageNo, lOrderParam, lDesc,
								sessionMng.m_lUserID, sessionMng.m_lOfficeID);
					} else {
						c = repayplan.findPlanByContract(lID,
								Constant.PageControl.CODE_PAGELINECOUNT,
								lPageNo, lOrderParam, lDesc);
					}
					RepayPlanInfo rp_info = new RepayPlanInfo();
					if (c != null) {
						Iterator iter = c.iterator();
						while (iter.hasNext()) {
							rp_info = (RepayPlanInfo) iter.next();
							lPageCount = rp_info.lCount;

							showRepay = LOANConstant.PlanType
									.getName(rp_info.nLoanOrRepay);
%>
			<tr align="center">
				<td class="ItemBody" width="18%" height="23"><%=DataFormat
													.formatDate(rp_info.tsPlanDate)%></td>
				<td class="ItemBody" width="10%" height="23"><%=showRepay%></td>
				<td class="ItemBody" width="20%" height="23"><%=DataFormat
											.formatListAmount(rp_info.dAmount)%></td>
				<td class="ItemBody" width="10%" height="23"><%=rp_info.sType%></td>
				<td class="ItemBody" width="15%" height="23"><%=rp_info.sExecuteInterestRate%>%</td>
				<td class="ItemBody" height="25" width="20%"><%=DataFormat
													.getDateString(rp_info.tsInputDate)%></td>

			</tr>
			<%}
					} else {

					%>
			<tr align="center">
				<td class="ItemBody" width="18%" height="23">&nbsp;</td>
				<td class="ItemBody" width="10%" height="23">&nbsp;</td>
				<td class="ItemBody" width="20%" height="23">&nbsp;</td>
				<td class="ItemBody" width="10%" height="23">&nbsp;</td>
				<td class="ItemBody" width="15%" height="23">&nbsp;</td>
				<td class="ItemBody" height="25" width="20%">&nbsp;</td>
			</tr>
			<%}

					%>
			<!-- ejb end -->
			<tr bordercolor="#999999">
				<td height="2" class="SearchBar" colspan="9">
				<form name="frmPage" action="q094-v.jsp?control=view" method="post">
				<input type="hidden" name="nTmpID" value="<%=lID%>"> 
				<input type="hidden" name="nOrderParam" value="<%=lOrderParam%>"> 
				<input type="hidden" name="nDesc" value="<%=lDescTmp%>">
				<input type="hidden" name="nTmpID" value="<%=lID%>"> 
				<input type="hidden" name="isSM" value="<%=lShowMenu%>"> 
				<input type="hidden" name="isYU" value="<%=lYU%>">

				<table border="0" cellspacing="3" cellpadding="0" class="SearchBar" height="50">
					<tr>
						<td width="730" height="2">
						<div align="right">
						<P><B>第 <input type="text" name="nPageNo" size="3"
							value="<%=lPageNo%>" class="SearchBar_Page"
							onfocus="nextfield ='submitfunction';"> 页 / 共 <%=lPageCount%> 页 <input
							type="button" name="Submit822" value="go" class="SearchBar_Btn"
							onclick="frmSubmit2(frmPage);"> <%if (lPageNo > 1) {%><input
							type="button" name="Submit4222" value="|&lt;"
							class="SearchBar_Btn" onclick="go(1);"><%}%> <%if (lPageNo > 1) {%><input
							type="button" name="Submit5222" value="&lt;"
							class="SearchBar_Btn" onclick="go(<%=lPageNo-1%>);"><%}%> <%if (lPageNo < lPageCount) {%><input
							type="button" name="Submit6222" value="&gt;"
							class="SearchBar_Btn" onclick="go(<%=lPageNo+1%>);"><%}%> <%if (lPageNo < lPageCount) {%><input
							type="button" name="Submit7222" value="&gt;|"
							class="SearchBar_Btn" onclick="go(<%=lPageCount%>);"><%}%> <input
							type=hidden name=hdnPageCount value="<%=lPageCount%>"> 
							<SPAN class=SearchBar></SPAN>  </B></P>
						</div>
						</td>
					</tr>
				</table>
				</form>
				</td>
			</tr>
	
		</table>
		</td>
		</tr>
				<tr>
						<td align=right><input type="button" name="Submit15"
							value="  关闭  " class="button"
							onClick="confirmClose(frmPage,'是否关闭窗口?')">
						</td>

			</tr>
		</table>

		<script language="javascript">
// firstFocus(frmPage.Submit15);
// //setSubmitFunction("confirmClose(frmPage,'是否关闭窗口?');");
// setFormName("frmPage");        
</script> <%OBHtml.showOBHomeEnd(out);
				}
			} catch (Exception e) {
				//LOANHTML.showMessage(out,sessionMng,request,response,"合同执行计划更改",Constant.RecordStatus.VALID,"Gen_E001");
			}

		%> <script language="JavaScript">
<!--
function frmSubmit2(form)
{
    var lMax;
	lMax = parseInt(form.hdnPageCount.value);
	if (InputValid(form.nPageNo,1, "int", 1, 1, lMax,"页数")) 
	{
		showSending();
                form.submit();
        }
}

	function go(i)
{
	frmPage.nPageNo.value=parseInt(i);
	frmSubmit2(frmPage);
}
function order(form,i)
{
    form.nOrderParam.value=parseInt(i);
	showSending();
form.submit();
}
// back
function confirmClose(URL,msg)
{
	if (confirm(msg))
	{
		window.close();
	}
}
</script>
<%@ include file="/common/SignValidate.inc" %>
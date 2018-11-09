<%
/**
 * 页面名称 ：q082-v.jsp
 * 页面功能 : 免还申请查询列表
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 转入页面 : 
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.loan.loanapply.dataentity.*,
	com.iss.itreasury.loan.loaninterestsetting.dataentity.*,
	com.iss.itreasury.loan.query.dataentity.*,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.loan.freeapply.dataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
    try{
		//判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
   		Timestamp tsToday = Env.getSystemDate();	
   		
   		Vector vector = null;
		vector = (Vector)request.getAttribute("Result");

        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"免还申请查询",Constant.YesOrNo.YES);
        boolean isdq=false;
        long lAllRecord = 0;//所有记录
		double dAllLoanAmount = 0;//总申请贷款金额
		double dAllFreeAmount = 0;//总免还贷款金额
		String actType=GetParam(request,"actType","");


%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources /> 
<TABLE border=0 class=top width="98%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle colSpan=2 height=29><B>免还申请查询</B></TD></TR>
  <TR>
    <TD colSpan=2 >

			<TABLE align=center border=0 class=ItemList height=20 width="99%">
				<TBODY>
				<TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
					<TD class=ItemTitle height=20 width="5%">
						<DIV align=center>序列号</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="6%">
						<DIV align=center>免还编号</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="10%">
						<DIV align=center>贷款种类</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="8%">
						<DIV align=center>合同编号</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="10%">
						<DIV align=center>借款单位</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="8%">
						<DIV align=center>委托单位</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="10%">
						<DIV align=center>贷款金额</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="8%">
						<DIV align=center>贷款日期</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="10%">
						<DIV align=center>免还金额</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="8%">
						<DIV align=center>免还日期</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="4%">
						<DIV align=center>免还状态</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="4%">
						<DIV align=center>负责人</DIV>
					</TD>
					<TD class=ItemTitle height=20 width="6%">
						<DIV align=center>下一级审核人</DIV>
					</TD>
				</TR>
<%
	FreeApplyInfo Info=null;//合同Info类
	if (vector != null )
	{
		int count=vector.size();
		for ( int i=0;i<count;i++)
		{
			Info = (FreeApplyInfo)vector.get(i);//免还Info类
			
			lAllRecord = Info.getRecordCount();//所有记录
			dAllLoanAmount = Info.getAllLoanAmount();//总申请贷款金额
			dAllFreeAmount = Info.getAllFreeAmount();//总免还贷款金额
%>
			<TR align=center borderColor=#999999>
				<TD class=ItemBody>
					<DIV align=center>
					<a href="javascript:frmNext(<%=Info.getID()%>,<%=Info.getContractID()%>,<%=Info.getLoanPayID()%>,<%=Info.getLoanTypeID()%>)"><%=DataFormat.formatString(""+Info.getID())%>
					</a></DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.formatString(Info.getFreeCode())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=LOANConstant.LoanType.getName(Info.getLoanTypeID())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.formatString(Info.getContractCode())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.formatString(Info.getClientName())%></DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.formatString(Info.getConsignClientName())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=right>
					<%=sessionMng.m_strCurrencySymbol+DataFormat.formatDisabledAmount(Info.getAmount(),0)%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center>
					<%=DataFormat.getDateString(Info.getStartDate())+"到"+DataFormat.getDateString(Info.getEndDate())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=right>
					<%=sessionMng.m_strCurrencySymbol+DataFormat.formatDisabledAmount(Info.getFreeAmount(),0)%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.getDateString(Info.getFreeDate())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=LOANConstant.FreeApplyStatus.getName(Info.getStatusID())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.formatString(Info.getInputUserName())%>
				</DIV></TD>
				<TD class=ItemBody>
				<DIV align=center><%=DataFormat.formatString(Info.getCheckUserName())%>
				</DIV></TD>
			</TR>
<%		}
	}
	else
	{
%>
		<TR align=center borderColor=#999999>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
			<TD class=ItemBody>&nbsp;</TD>
		</TR>
<%
	}
%>
		<TR borderColor=#999999>
			<TD class=SearchBar colSpan=13 height=46>
			<TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar width="100%">
				<TBODY>
					<TR>
						<TD height=2 ><B><B>共找到
						<INPUT class=box maxLength=3 name=textfield3222 size=3 value="<%=lAllRecord%>" disabled>
						笔记录</B></B>
						</TD>
						<TD height=2 >&nbsp;</TD>
					</TR>
					<TR>
						<TD height=14 >
						<B>总申请贷款金额:<%=sessionMng.m_strCurrencySymbol%></B>
						<INPUT type="text" class="tar" name=dAllAmount size=20 align=right value="<%=DataFormat.formatListAmount(dAllLoanAmount)%>" disabled >
											<B>总免还金额:<%=sessionMng.m_strCurrencySymbol%>
						<INPUT type="text" class="tar" name=dAllAmount size=20 align=right value="<%=DataFormat.formatListAmount(dAllFreeAmount)%>" disabled >
</B></TD>
						<TD height=14 align=right>
						</TD>
					</TR>
					<tr>
					<td colspan=2>
 <%
	//输出分页控件
	OBHtml.showTurnPageControl(out,"turnPageFrm","q081-c.jsp",lAllRecord,(termInfo==null?1:termInfo.getTxtPageNo()),Constant.PageControl.CODE_PAGELINECOUNT,(termInfo==null?99:termInfo.getLOrderParam()),(termInfo==null?1:termInfo.getLDesc()));
%>
					</td>
					</tr>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
  <TR>
    <TD height=36><FONT size=2><BR>查询数据时间为: <%=DataFormat.getDateString(tsToday)%></FONT> 
      <DIV align=right></DIV></TD>
    <TD height=36>
      <DIV align=right><B>
	  <INPUT class=button name=Submit22 onclick="window.print()" type=button value=" 打 印 "> 
      </B><% if (actType.equals("contract")){%>
					<input type="button" name="butReturn" value=" 关闭 " class="button" onClick="closePage()">
<% }else{ %>
					<input type="button" name="butReturn" value=" 返回 " class="button" onClick="backto()">
<% } %>			
      </DIV></TD></TR></TBODY></TABLE>

<script language="JavaScript">
function closePage()
{
	window.close();
}
function backto()
{
	window.location.href="../query/q080-v.jsp";
}
function frmNext(lFreeApply,lContract,lLoanPay,lType)
{
	window.open('q084-v.jsp?control=view&lFreeApplyID='+lFreeApply+'&type='+lType+'&isSM=<%=Constant.YesOrNo.NO%>','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
}

</script>
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		OBHtml.showExceptionMessage(out,sessionMng,ie,"免还申请查询","", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 <%@ include file="/common/SignValidate.inc" %>
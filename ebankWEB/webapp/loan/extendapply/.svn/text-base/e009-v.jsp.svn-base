<%
/**
 * 页面名称 ：S614.jsp
 * 页面功能 : 贷款展期申请处理-新增
 * 作    者 ：方远明
 * 日    期 ：2003-10-23
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : S611.jsp			  
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.lang.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.obextendapply.bizlogic.*,
	com.iss.itreasury.ebank.obextendapply.dataentity.*,
	com.iss.itreasury.ebank.util.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.ebank.obrepayplan.dataentity.*,
	com.iss.itreasury.loan.contract.bizlogic.*,
	com.iss.itreasury.loan.contract.dataentity.*,
	com.iss.itreasury.ebank.obdataentity.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[展期申请]";
%>		

<%
	try
	{
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }

		long lModuleID = Constant.ModuleType.LOAN;
		//模块类型
		long lLoanTypeID = Constant.ApprovalLoanType.OTHER ;
		long lActionID = Constant.ApprovalAction.EXTEND_APPLY;
		
    	// 定义变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";
		long lContractID = -1;
		long lExtendID = -1;
		long lAType = 1;
		long lEType = 1;
		String strTmp = "";
		String sTitle = "一般贷款";
		String sAction = "";
		String sEType = "展期";

        String sAmount = "";
		double dAmount = 0;
		double dBalance = 0;
		Timestamp dtExtendStartDate = null;
		Timestamp dtInputDate = null;
		double dRate = 0;
		String sReason = "",sSource = "",sOther = "";
		long nNum = -1;
		String sLiborName = "";
		boolean libor = false;


		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");

		strTmp = (String)request.getAttribute("lExtendID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lExtendID = Long.valueOf(strTmp).longValue();
		} else {
			out.println("出错：没传展期标识.");
			strcontrol = "";
		}

		OBExtendApplyInfo e_info = new OBExtendApplyInfo();
		
		lLoanTypeID = e_info.getLoanTypeID();
		
		
        ContractInfo c_info = new ContractInfo();
		
       	if (request.getAttribute("c_info") != null)
       	{
           	c_info = (ContractInfo)request.getAttribute("c_info");
       	}       	
		if (request.getAttribute("e_info") != null)
       	{
           	e_info = (OBExtendApplyInfo)request.getAttribute("e_info");
       	}

		strTmp = (String)request.getAttribute("attribtype");
		if (strTmp != null && strTmp.length() > 0) {
			lAType = Long.valueOf(strTmp).longValue();
		}

		strTmp = (String)request.getAttribute("txtAction");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  sAction = strTmp;
		}


		//从指令查询过来的参数
		String txtSearch = "";
		if( (String)request.getAttribute("txtSearch") != null )
			txtSearch = (String)request.getAttribute("txtSearch");
			
		//配合指令查询过来的参数,如果为"yes",则表示查询后并修改过
		String txtChanged = "";
		if( (String)request.getAttribute("txtChanged") != null )
			txtChanged = (String)request.getAttribute("txtChanged");

		OBRepayPlanInfo r_info = new OBRepayPlanInfo();  //  页面显示用
		
	 	OBHtml.showOBHomeHead(out,sessionMng,"展期新增",Constant.ShowMenu.YES); 
			/***************************************************************************/

			//LOANHTML.showHomeHead(out,sessionMng,"内容显示",Constant.YesOrNo.YES);
%>


<!--*************************************************************************************-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

<!--*************************************************************************************-->
<p>

<table width=740 border="0" class="top" height="113">

  <tr class="tableHeader"> 
    <td class="FormTitle" height="13"><b>  以下<%= sEType %>申请已完成！</b></td>
  </tr>
  <tr> 
    <td height="51" valign="bottom"> 
      <table width="100%" border="0" align="center" height="25">
        <tr> 
          <td width="20%">合同编号：</td>
          <td width="25%"><u><%= e_info.getContractCode() %></u></td>
          <td width="55%">&nbsp;</td>
        </tr>
		<tr> 
          <td width="20%"><%=sEType%>申请编号：</td>
          <td width="25%"><u><%= e_info.getSerialNo()>9?(""+e_info.getSerialNo()):("0"+e_info.getSerialNo()) %></u></td>
          <td width="55%" align="right">
 <!-- ------------------------------------------------------------------------------------------------------显示1  -->
        <input type="button" name="Submit11" value="新增<%= sEType %>" class="button" onClick="location.href='<%=Env.EBANK_URL%>loan/extendapply/e001-v.jsp?control=view' ">
<%if(e_info.getStatusID() != OBConstant.LoanInstrStatus.ACCEPT && e_info.getStatusID() != OBConstant.LoanInstrStatus.REFUSE&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.CANCEL&&e_info.getStatusID()!=OBConstant.LoanInstrStatus.APPROVE)
{
		if (e_info.getStatusID() == OBConstant.LoanInstrStatus.SUBMIT) { %>			
			<% if (e_info.getInputUserID() == sessionMng.m_lUserID) { %>
				<input type="button" name="Submit12" value="取消<%= sEType %>申请" class="button" onClick="location.href='<%=Env.EBANK_URL%>loan/extendapply/e014-c.jsp?lExtendID=<%=lExtendID%>&txtSearch=<%=txtSearch%>&control=view' ">
			<% } %>
		<% }else//如果是撰写状态,则显示提交按纽
		{ %>
			<% if (e_info.getInputUserID() == sessionMng.m_lUserID) { %>			
				<input type="button" name="Submit12" value="提交<%= sEType %>申请" class="button" onClick="location.href='<%=Env.EBANK_URL%>loan/extendapply/e015-c.jsp?lExtendID=<%=lExtendID%>&txtSearch=<%=txtSearch%>&control=view' ">
				<input type="button" name="Submit13" value="取消<%= sEType %>申请" class="button" onClick="location.href='<%=Env.EBANK_URL%>loan/extendapply/e014-c.jsp?lExtendID=<%=lExtendID%>&txtSearch=<%=txtSearch%>&control=view' ">
			<% } %>	
		<%}
}%>
<%if(e_info.getStatusID() != OBConstant.LoanInstrStatus.REFUSE && e_info.getStatusID() != OBConstant.LoanInstrStatus.CANCEL)
{%>	
        <INPUT class=button name=btnExportContract onclick="window.open('<%=Env.EBANK_URL%>loan/extendapply/e012-c.jsp?lExtendApplyID=<%=lExtendID%>&lContractID=<%=e_info.getContractID()%>');" type="button" value="导出并打印">
<%}%>
		<%if(txtSearch.equals("modify"))
		{%>
		<input type="button" name="Submit13" value="返 回" class="button" onClick="location.href='<%=Env.EBANK_URL%>loan/query/q003-c.jsp?control=view' ">
		<%}else{%>
		<input type="button" name="Submit13" value="返 回" class="button" onClick="location.href='<%=Env.EBANK_URL%>loan/extendapply/e001-v.jsp?control=view' ">
		<%}%>
		
		<!--input type="button" name="Submit13" value="test" class="button" onClick="location.href='e002-c.jsp?lExtendID=<%=lExtendID%>&control=view' "-->
		<% //} %>
		&nbsp;<br>
 <!-- ------------------------------------------------------------------------------------------------------显示2  -->
        </td>
        </tr>
		<% if (lAType == 4 ) { %>
        <tr> 
          <td width="20%"><%=sEType%>合同编号：</td>
          <td width="25%">
	<% if (e_info.getInputUserID() == sessionMng.m_lUserID && e_info.getContractID() >0 && e_info.getExContractID() > 0) { %>
<a href="S85.jsp?lExtendApplyID=<%=lExtendID%>&lContractID=<%=e_info.getContractID()%>&control=view&ContractType=<%=e_info.getLoanTypeID()%>">
<% } %><%= (e_info.getExCode() == null ? "" : e_info.getExCode())  %></a>
		</td>
          <td width="55%">&nbsp;</td>
        </tr>
		<% } %>
      </table>
      <table width="97%" height="401" border="1" align="center" bordercolor="999999">
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="25"> <u><%= sEType %>申请资料</u></td>
          <td height="20" colspan="3">&nbsp;</td>
          <td width="16%" height="20">&nbsp;</td>
          <td width="32%" height="20">&nbsp;</td>
        </tr>
<%

     Collection c2 = e_info.getExtendList();
	 Iterator iter2 = c2.iterator();
	 while (iter2.hasNext()) {
		 r_info = (OBRepayPlanInfo)iter2.next();
%>
<!-- -------------------------------------------------------------------------------------- Loop -->
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="20">计划余额：</td>
          <td height="20" colspan="3"> 
		  <input name="txtdBalance" type="text" class="tar" size="20" value="<%= DataFormat.formatListAmount(r_info.getPlanBalance()) %>" disabled>
          </td>
          <td width="16%" height="20"> <%= sEType %>金额： </td>
          <td width="32%" height="20"> 
		  <input name="txtdBalance" type="text" class="tar" size="20" value="<%= DataFormat.formatListAmount(r_info.getAmount()) %>" disabled>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="20"><%= sEType %>日：</td>
          <td height="20" colspan="3"> 
            <input name="txtExtendStartDate" type="text" class="box" size="20" value="<%= DataFormat.formatDate(r_info.getExtendStartDate()) %>" disabled>
          </td>
          <td width="16%" height="20"><%= sEType %>到期日：</td>
          <td width="32%" height="20"> 
          <input name="txtExtendStartDate" type="text" class="box" size="20" value="<%= DataFormat.formatDate(r_info.getExtendEndDate()) %>" disabled>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td width="16%" height="20"><%= sEType %>期限：</td>
          <td height="20" colspan="3"> 
            <input name="txtNum" type="text" class="box" size="10" value="<%= r_info.getExtendPeriod() %>" disabled>
            月</td>
          <td width="16%" height="20">&nbsp;</td>
          <td width="32%" height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <hr>
          </td>
        </tr>
		<!-- ------------------------------------------------------------------------------------------ Loop -->
		<% } %>

	<tr bordercolor="#E8E8E8"> 
	  <td height="20" colspan="6"> 
		<table>
        <tr bordercolor="#E8E8E8"> 
            <td height="20"><%= sEType %>基准利率：</td>
          	  <td nowrap><input type="text" name="textfield324" size="8" class="box" value="<%=(e_info.getBasicInterest())%>" disabled>% 
              <select class='box' name="select1" disabled>
                <option>　</option>
                <option value=1 <%if(c_info.getAdjustRate()>=0) out.println("selected");%>>+</option>
                <option value=2 <%if(c_info.getAdjustRate()<0) out.println("selected");%>>-</option>
              </select>
              浮动比例 
              <input type="text" name="textfield3222" size="5" class="box" value="<%=c_info.getAdjustRate()>=0?c_info.getAdjustRate():-c_info.getAdjustRate()%>" disabled>
              % </td>
            <td width="23%" nowrap align="right"><%= sEType %>执行利率 ： </td>
            <td width="20%"> 
              <input type="text" name="ExtendValue" size="8" class="box" value="<%out.println(e_info.getInterestRate());%>"   readonly>
              % </td>
        </tr></table>
	  </td>
	</tr>


        <tr bordercolor="#E8E8E8"> 
          <td height="21" colspan="4"><%= sEType %>原因：</td>
          <td height="21">&nbsp;</td>
          <td height="21">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtReason" cols="90" rows="2" class="box" disabled><%= e_info.getExtendReason() %></textarea>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="4">归还延期还款本息资金来源：</td>
          <td height="20">&nbsp;</td>
          <td height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtSource" cols="90" rows="2" class="box" disabled><%= e_info.getReturnPostPend() %></textarea>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="4"> 其他事项：</td>
          <td height="20">&nbsp;</td>
          <td height="20">&nbsp;</td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
            <textarea name="txtExtOther" cols="90" rows="2" class="box" disabled><%= e_info.getOtherContent() %></textarea>
          </td>
        </tr>
        <tr bordercolor="#E8E8E8"> 
          <td height="20" colspan="6"> 
<hr>
         
          </td>
        </tr>
      </table>
    <tr>
    <td height="30" valign="top"> 
      <table width="100%" border="0" align="center" height="25">
        <tr> 
          <td width="13%">
		  <p align=right>录入人:<%=e_info.getInputUserName()%>&nbsp;&nbsp;&nbsp;录入日期:<%=DataFormat.getDateString(e_info.getInputDate())%> &nbsp;&nbsp;&nbsp;状态:<%=OBConstant.LoanInstrStatus.getName(e_info.getStatusID())%><br> </p>
		  </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script language="JavaScript">

	// 取消展期
function formCancel(form,msg)
{
	if(confirm(msg))
	{
		//form.txtAction.value = "cancel";
		form.action = "../extendapply/e014-c.jsp";
        showSending();//显示正在执行
		form.submit();
	}
}
</script>

<%		
	OBHtml.showOBHomeEnd(out);
	} 
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
	}

%>
<%@ include file="/common/SignValidate.inc" %>
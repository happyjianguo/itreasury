<%
/**
 * 页面名称 ：S612.jsp
 * 页面功能 : 贷款展期申请处理-新增-计划查找
 * 作    者 ：方远明
 * 日    期 ：2003-10-21
 * 特殊说明 ：用放大镜输入查询条件
 *			  
 * 转入页面 : S613.jsp			  
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
System.out.println("herhehreh");
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

    	// 定义变量
		long lPageCount = 1;
		long lPageNo = 1;
		long lOrderParam = 1;
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
		long lDescTmp = Constant.PageControl.CODE_ASCORDESC_DESC;//临时传输变量
		String strcontrol = "";//控制动作
		String strFirst = "yes";

		long lContractID = -1;
		long lLoanTypeID = -1;
		String strTmp = "";
		String sTitle = "贷款";
		String sDisabled = "";

		// ---------------------------------------------------------------------------------end
		ContractInfo c_info = new ContractInfo();
		Collection c = null;
       	if (request.getAttribute("c_info") != null)
       	{
           	c_info = (ContractInfo)request.getAttribute("c_info");
       	}       	
		if (request.getAttribute("c") != null)
       	{
           	c = (Collection)request.getAttribute("c");
       	}
		//获取strcontrol
		if( (String)request.getAttribute("control") != null )
			strcontrol = (String)request.getAttribute("control");
		if( (String)request.getAttribute("first") != null)
			strFirst = (String)request.getAttribute("first");
			
		strTmp = (String)request.getAttribute("lContractID");
		if ( strTmp != null && strTmp.length() > 0 ) {
                  lContractID = Long.valueOf((String)request.getAttribute("lContractID")).longValue();
		} else {
			out.println("没传合同标识");
			strcontrol = "";
		}
%>



<% OBHtml.showOBHomeHead(out,sessionMng,"展期新增查找",Constant.ShowMenu.YES); %>

<!--*************************************************************************************-->
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="form1" action="../extendapply/e006-c.jsp?control=save&lContractID=<%= lContractID %>" method="post">
<table width=740 border="0" class="top" height="265">
  <tr class="tableHeader"> 
    <td class="FormTitle" height="29"><b><%= sTitle %>展期查找(计划)</b></td>
  </tr>
  <tr> 
    <td height="185">
        <input type="hidden" name="extype">
        <input type="hidden" name="attribtype" value="1">
        <input type="hidden" name="balance" value="0"> <!-- 用来判断是否为计划余额 -->

      <table width="97%" border="0" align="center" bordercolor="999999">
        <tr>   
          <td width="150" height="33" colspan="6">&nbsp;</td>
        </tr>
  
<!-- --------------------------------------------------------------------------------------------------------- -->
        <tr> 
          <td>&nbsp;</td>
          <td>合同编号：</td>
          <td> <u><%= c_info.getContractCode() %></u></td>
          <td nowrap> 贷款类型：</td>
          <td align=left><input type="text" name="txt1" class="box" value="<%= LOANConstant.LoanType.getName(c_info.getLoanTypeID()) %>" disabled></td>
          <td width="184">&nbsp; </td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>借款单位：</td>
          <td colspan=4> <input type="text" class="box" size=80 name="txt1" value="<%= c_info.getBorrowClientName() %>" disabled></td>        
        </tr>
		<tr> 
          <td>&nbsp;</td>
          <td>贷款日期：</td>
          <td>  <input type="text" name="txt1" class="box" value="<%= DataFormat.formatDate(c_info.getLoanStart()) %>" disabled></td>
          <td align=right> 到：</td>
          <td> <input type="text" name="txt1" class="box" value="<%= DataFormat.formatDate(c_info.getLoanEnd()) %>" disabled></td>
          <td >&nbsp; </td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>期限：</td>
          <td>  <input type="text" name="txt1" class="box" value="<%= c_info.getIntervalNum() %>" disabled>个月</td>
          <td>&nbsp; </td>
          <td>&nbsp;</td>
          <td >&nbsp; </td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>贷款金额：</td>
          <td>  <input type="text" name="txt1" class="box" value="<%= DataFormat.formatListAmount(c_info.getExamineAmount()) %>" disabled></td>
          <td>&nbsp; </td>
          <td>&nbsp;</td>
          <td >&nbsp; </td>
        </tr>
       <tr> 
          <td>&nbsp;</td>
          <td>执行利率：</td>
          <td><input type="text" name="txt1" class="box" value="<%=c_info.getFormatInterestRate()%> %" disabled></td>
          <td>&nbsp; </td>
          <td>&nbsp;</td>
          <td >&nbsp; </td>
        </tr>
		<tr> 
          <td>&nbsp;</td>
          <td>委托单位：</td>
          <td colspan=4>  <input type="text" class="box" size=80 name="txt1" value="<%= c_info.getClientName()==null?"":c_info.getClientName() %> " disabled></td>
        </tr>
        <tr> 
		  <td  height="47" align="right" colspan="6">
		  <input type="button" name="Submit1" value=" 新增展期申请 " class="button" onclick="frmSubmit(form1)"></td>		
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td height="75" align="center"> 
      <hr>
      <table border="0" bordercolor="#999999" class="ItemList" width="740">
        <tr bordercolor="#999999" bgcolor="#CCCCCC" align="center" class="tableHeader"> 
          <td class="ItemTitle" width="25">&nbsp;</td>
          <td class="ItemTitle" width="100">还款计划序号</td>
          <td class="ItemTitle" width="150">计划还款金额</td>
          <td class="ItemTitle" width="150">计划余额</td>
          <td class="ItemTitle" width="150">计划还款日期</td>
          <td class="ItemTitle" width="100">利率 %</td>
        </tr>

<!-- ejb begin -->
<%  
	    int nCounter = 0; // 用来判断是否为计划余额
	    OBRepayPlanInfo le_info = new OBRepayPlanInfo();
	    if ( c != null ) {
			  Iterator iter = c.iterator();
			  while (iter.hasNext()) {
			  le_info = (OBRepayPlanInfo)iter.next();
//余额没有了,或者做过愈期
			  if (le_info.getPlanBalance() == 0||le_info.getLastOverDueID()>0) { sDisabled = "disabled"; }
%>
		  <tr bordercolor="#999999" align="center"> 
		   <td class="ItemBody" width="25"><input type="checkbox" name="checkbox" value="<%= le_info.getID() %>" <%= sDisabled %>> </td>
		   <td class="ItemBody" width="100"><%= le_info.getCount() %></td>
          <td class="ItemBody" width="150"><%= DataFormat.formatListAmount(le_info.getAmount()) %></td>
          <td class="ItemBody" width="150"><%if (le_info.getPlanBalance() == 0) {out.print("0.00");} else {out.print(DataFormat.formatListAmount(le_info.getPlanBalance()));}  %></td>
          <td class="ItemBody" width="100"><%= DataFormat.formatDate(le_info.getPlanDate()) %></td>
          <td class="ItemBody" width="100"><%=c_info.getFormatInterestRate()%>		  <input type="hidden" name="balance<%= nCounter++ %>" value="<%= le_info.getHiddenBalance() %>"> <!-- // 用来判断是否为计划余额 --></td>

		  </tr>
<%
			  sDisabled = "";
		} 
	  } else {
%>

		  <tr>
		  <td class="ItemBody" width="25">&nbsp;</td>
		  <td class="ItemBody" width="100">&nbsp;</td>
		  <td class="ItemBody" width="150">&nbsp;</td>
          <td class="ItemBody" width="150">&nbsp;</td>
          <td class="ItemBody" width="100">&nbsp;</td>
          <td class="ItemBody" width="111">&nbsp;</td>
		  </tr>
<% } %>
<!-- ejb end -->


        <tr bordercolor="#999999"> 
          <td height="2" class="SearchBar" colspan="9"> 
            <table width="188%" border="0" cellspacing="3" cellpadding="0" class="SearchBar" height="50">


              <tr> 
                <td width="740" height="2">
				<% if (c!=null) { %>
                <input class="button" type="button" name="analog" onclick="selectAll()" value="全 选">
				<% }  else { out.print("&nbsp;"); }%>
                </td>
              </tr>
            </table>
          </td></tr></table></td></tr></table></form>

<script language="JavaScript">
<!--
var flag = 1;
function frmSubmit(form)
{

	// 处理是否checkbox为空
		var i;
		var empty = new Boolean(true);
        var isBalZero = false;

    if (isNaN(form.checkbox.length) == true)
	{
		if (form.checkbox.checked == true)
		{

			if (eval("form1.balance0").value > 0)   // 用来判断是否为计划余额
				form.balance.value = eval("form1.balance0").value; 
            else
                isBalZero = true;
			empty = new Boolean(false);
		}
	} else { // 有多个checkbox
		for(i=0;i<form.checkbox.length;i++)
		{
			//alert(form.checkbox[i].checked);
			if (form.checkbox[i].checked == true)
			{
				if (eval("form1.balance"+i).value > 0)   // 用来判断是否为计划余额
                {
					form.balance.value = eval("form1.balance"+i).value; 
                }
                else
                {
                    isBalZero = true;
                }

				//alert("have a false.");
				empty = new Boolean(false);
			}
		}
	}
		//alert("empty: " + empty);
		if (empty == true)
		{
			alert("请选择一条计划做展期!");
			return (false);
		}
        /*
        if (isBalZero)
        {
            alert("有余额为0的计划被选中，请检查！");
            return (false);
        }
        */

	form.extype.value = "1";
	form.submit();
}

function frmSubmit2(form) // 转期
{

	// 处理是否checkbox为空
		var i;
		var empty = new Boolean(true);

    if (isNaN(form.checkbox.length) == true)
	{
		if (form.checkbox.checked == true)
		{

			if (eval("form1.balance0").value > 0)   // 用来判断是否为计划余额
				form.balance.value = eval("form1.balance0").value; 

			empty = new Boolean(false);
		}
	} else { // 有多个checkbox
		for(i=0;i<form.checkbox.length;i++)
		{
			//alert(form.checkbox[i].checked);
			if (form.checkbox[i].checked == true)
			{

				if (eval("form1.balance"+i).value > 0)   // 用来判断是否为计划余额
					form.balance.value = eval("form1.balance"+i).value;

				//alert("have a false.");
				empty = new Boolean(false);
			}
		}
	}
		//alert("empty: " + empty);
		if (empty == true)
		{
			alert("请选择一条计划做转期!");
			return (false);
		}

    form.extype.value = "2";
    showSending();//显示正在执行
	
	form.submit();
}
function selectAll(frm)
{
	var i;
	//只选择了一个checkBox,则checkBox不成为数组,checkBox[i]不可用
	if(flag==1)
	{
		if( isNaN(form1.checkbox.length) == true )	
		{		
			if (form1.checkbox.disabled == false)
			{
				form1.checkbox.checked = true;
			}
		}
		else
		{
			for(i = 0; i < form1.checkbox.length; i++)
			{
				if (form1.checkbox[i] != null && form1.checkbox[i].disabled == false)
				{
					form1.checkbox[i].checked = true;
				}
			}
		}
		flag=0;
	}
	else
	{
       if( isNaN(form1.checkbox.length) == true )	
		{		
			if (form1.checkbox.disabled == false)
			{
				form1.checkbox.checked = false;
			}
		}
		else
		{
			for(i = 0; i < form1.checkbox.length; i++)
			{
				if (form1.checkbox[i] != null && form1.checkbox[i].disabled == false)
				{
					form1.checkbox[i].checked = false;
				}
			}
		}
		flag=1;
	}

}

//-->
</script>

<%		
	OBHtml.showOBHomeEnd(out);
		//}
	}
	catch (IException ie)
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"展期查找(计划)",1);
		ie.printStackTrace();
		out.flush();
		return; 
	}

%>
<%@ include file="/common/SignValidate.inc" %>
<%--
 页面名称 ：v201.jsp
 页面功能 : 利息费用匡算 - 输入条件的页面
 作    者 ：xrli
 日    期 ：2003-11-20
 特殊说明 ：
 修改历史 ：modify by wjliu --2007/3/28 注释掉几个不要的查询条件
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.SessionMng"%>   
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier,com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.*"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<% String strContext = request.getContextPath();%>
   <%
try
{
	sessionMng.clearPageLoader();
       //定义变量
	String strAccountNoFrom = "";
	String strAccountNoTo = "";
	java.sql.Timestamp tsClearInterestDate = null;
	long lClientIDFrom = -1;
	long lClientIDTo = -1;
	String strClientNoFrom = "";
	String strClientNoTo = "";
	long lConsignerIDFrom =-1;
	long lConsignerIDTo =-1;
	String strConsignerFrom = "";
	String strConsignerTo = "";
	long lConsignLoanSort = -1;	
	String strContractNoFrom = "";
	String strContractNoTo = "";
	long lCurrencyID = -1;
	java.sql.Timestamp tsDateFrom = null;
	java.sql.Timestamp tsDateTo = null;
	long lFeeType = -1;
	long lIsSelectCircleLoan = -1;
	long lIsSelectClearInterestDate = -1;
	long lIsSelectClientNo = -1;
	long lIsSelectCompoundInterest = -1;
	long lIsSelectConsigner = -1;
	long lIsSelectConsignLoanSort = -1;
	long lIsSelectContractNo = -1;
	long lIsSelectForfeitInterest = -1;
	long lIsSelectInterest = -1;
	long lIsSelectPayFormNo = -1;
	long lIsSelectSelfLoanSort = -1;
	long lIsSelectSelfLoanTerm = -1;
	long lIsSelectShortLoan = -1;
	long lIsSelectUnClearInterest = -1;
	long lNoticeDays = -1;
	long lOfficeID = -1;
	String strPayFormNoFrom = "";
	String strPayFormNoTo = "";
	double dRate = 0.0;
	long lSelfLoanSort = -1;
	long lSelfLoanTermFrom = -1;
	long lSelfLoanTermTo = -1;
	
	String strClearInterestDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	String strDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);


	String strTemp = null;
	//页面辅助变量
	String strActionResult = Constant.ActionResult.FAIL;
	String strAction = null;
	if (request.getAttribute("strActionResult") != null)
	{
		  strActionResult = (String)request.getAttribute("strActionResult");
	}
	if (request.getAttribute("strAction") != null)
	{
		  strAction = (String)request.getAttribute("strAction");
	}
	
	//SETTHTML.showHomeHead(out,sessionMng,Env.getClientName());
	OBHtml.showOBHomeHead(out, sessionMng, "应付利息和费用匡算", OBConstant.ShowMenu.YES);
%>
<form name="frmV201" action="q022-c.jsp" method="post" >	
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="strAction" type="hidden">
	<input type="hidden" name="strSuccessPageURL" value="/settlement/other/view/v202.jsp">
	<input type="hidden" name="strFailPageURL" value="/settlement/other/view/v202.jsp">
	<table cellpadding="0" cellspacing="0" class="title_top">
		  <tr>
		    <td height="24">
			    <table cellspacing="0" cellpadding="0" class=title_Top1>
					<TR>
				       <td class=title ><span class="txt_til2" style="width:150px">应付利息和费用匡算</span></td>
				       <td class=title_right><a class=img_title></td>
					</TR>
				</TABLE>
			<br/>
      <table width="100%" border="0" cellspacing="3" cellpadding="0" class=normal >
          
          <tr> 
            <td height="20"  nowrap>&nbsp;<input type="checkbox" name="lIsSelectSelfLoanSort" value="1" onfocus="nextfield ='lSelfLoanSort';">
              自营贷款种类：</td>
            <TD height=20 width="5%">&nbsp;</TD>
            <td height="20" colspan="4" > <%
				String strPropertyZYZL = "class='box' onFocus=nextfield='lSelfLoanTermFrom';";
				SETTConstant.SettLoanType.showList11(out,"lSelfLoanSort",(int)SETTConstant.AccountGroupType.TRUST,lSelfLoanSort,true,false,strPropertyZYZL,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %> </td>
          </tr>
          <tr> 
            <td height="20"  nowrap>&nbsp;<input type="checkbox" name="lIsSelectConsignLoanSort" value="1" onfocus="nextfield ='lConsignLoanSort';">
              委托贷款种类：</td>
            <TD height=20 width="5%">&nbsp;</TD>
            <td height="20" colspan="4" > <%
				String strPropertyWTZL = "class='box' onFocus=nextfield='lIsSelectInterest';";
				SETTConstant.SettLoanType.showList11(out,"lConsignLoanSort",(int)SETTConstant.AccountGroupType.CONSIGN,lConsignLoanSort,true,false,strPropertyWTZL,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		  %> </td>
          </tr>
          
          <tr> 
            <td height="20" >&nbsp;&nbsp;&nbsp;<font color="#FF0000">*</font>
              结息日期：</td>
            <TD height=20 width="5%">&nbsp;</TD>
            <TD  width=20% nowrap colspan="2">
            	<fs_c:calendar 
		          	    name="strClearInterestDate"
			          	value="<%=strClearInterestDate%>" 
			          	properties="nextfield ='lIsSelectSelfLoanSort'" 
			          	size="20"/>
            </TD>
          </tr>
          
          <TR borderColor=#ffffff> 
            <TD noWrap height=1>&nbsp;&nbsp;&nbsp;<font color="#FF0000">* </font>利息费用类型：</TD>
            <TD height=1 width="5%">&nbsp;</TD>
            <TD noWrap colSpan=3 height=1>
			 <input type="checkbox" name="lIsSelectInterest" value="1" onFocus="nextfield='lIsSelectCompoundInterest';">
              利息 
              <input type="checkbox" name="lIsSelectCompoundInterest" value="1" onFocus="nextfield='lIsSelectForfeitInterest';">
              复利 
              <input type="checkbox" name="lIsSelectForfeitInterest" value="1" onFocus="nextfield='lFeeType[0]';">
              罚息 
              <input type="radio" name="lFeeType" value="2" onFocus="nextfield='lFeeType[1]';">
              手续费 
              <input type="radio" name="lFeeType" value="3" onFocus="nextfield='interest';">
              担保费</TD>
            <TD noWrap height=1>&nbsp;</TD>
          </TR>
          <tr> 
            <td height="38" width="1%" nowrap>&nbsp;</td>
            <td height="38"></td>
            <td height="38">&nbsp;</td>
            <td height="38">&nbsp;</td>
			<td height="38" align="right"> 
                <input type="button" name="interest" value=" 利息费用匡算表 "  class="button1" onFocus="nextfield='submitfunction';" onClick="doInterestEstimate();">
              &nbsp;&nbsp;</td>
            <td height="38">&nbsp;</td>
       </tr>
      </table>
     </td>
    </tr>
   </table>
 </form>
<script language="JavaScript">

var lTemp = 0;
function doSearchAccount()
{	
	if(!validataInterest()) return;		
	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v202.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="searchAccount";
	showSending();//显示正在执行
    document.frmV201.submit();
}
function doSearchClient()
{	
	if(!validataInterest()) return;		
	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v204.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="searchClient";
	showSending();//显示正在执行
    document.frmV201.submit();
}
function doInterestEstimate()
{
	if(!validataInterest()) return;
			
	/*document.frmV201.strSuccessPageURL.value="q021-v.jsp";
	document.frmV201.strFailPageURL.value="q021-v.jsp";
	document.frmV201.strAction.value="printInterestEstimate";
	showSending();//显示正在执行*/
	
	document.frmV201.strSuccessPageURL.value="/capital/query/q023-v.jsp";
	document.frmV201.strFailPageURL.value="/capital/query/q023-v.jsp";
	document.frmV201.strAction.value="printInterestEstimate";
	document.frmV201.target="_blank";
    document.frmV201.submit();
	//window.open("q022-c.jsp?strAction=interestEstimate&strSuccessPageURL=q023-v.jsp&strFailPageURL=q023-v.jsp");    
}
function doNoticeEstimate()
{	
	lTemp = 1;
	if(!validateFields(frmV201)) return;
	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="printNoticeEstimate";
	showSending();//显示正在执行
    document.frmV201.submit();
}
function doLoanDunNotice()
{
  //贷款催收通知书
  	if(!validataDun()) return;	
	lTemp = 2;
	 if(!validateFields(frmV201)) return;	
 	document.frmV201.strSuccessPageURL.value="/settlement/other/view/v203.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="LoanDunNotice";
	showSending();//显示正在执行
    document.frmV201.submit();
}
function doLoanMatureNotice()
{
 if(!validataMature()) return;	
  //到期贷款通知书
  lTemp = 2;
  if(!validateFields(frmV201)) return;
  document.frmV201.strSuccessPageURL.value="/settlement/other/view/v203.jsp";
	document.frmV201.strFailPageURL.value="/settlement/other/view/v201.jsp";
	document.frmV201.strAction.value="LoanMatureNotice";
	showSending();//显示正在执行
    document.frmV201.submit();
}
function validataInterest()
{
	var smsg = "";
	var bResult = true;
	if(bResult)
	{
	}
	
	if(document.frmV201.strClearInterestDate.value=='')
	{
		smsg = smsg + "结息日期不能为空，请重新输入！\n";
		bResult = false;
	}
	else if(chkdate(document.frmV201.strClearInterestDate.value)==0)
	{
		smsg = smsg + "结息日期不正确，请重新输入！\n";
		bResult = false;
	}
	if(!CompareDateString('<%=Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)%>',document.frmV201.strClearInterestDate.value))
	{
		alert("结息日不能小于当前系统日期，请重新输入！");
		bResult = false;
	}
	
	if(bResult)
	{
		if(frmV201.lFeeType(0).checked == false && frmV201.lFeeType(1).checked == false && frmV201.lIsSelectInterest.checked == false && frmV201.lIsSelectCompoundInterest.checked == false && frmV201.lIsSelectForfeitInterest.checked == false)
		{
			//alert("必须选择一种利息类型，请重新输入！");
			smsg = smsg + "必须选择一种利息类型，请重新输入！\n";
			bResult = false;
		}		
	}
	if (smsg !="")
	{
		alert(smsg);
	}	
	return bResult;
}
function validataDun()
{
	var bResult = true;
	if(bResult)
	{
		if(frmV201.lIsSelectClearInterestDate.checked == true)
		{
			if(document.frmV201.strClearInterestDate.value=='')
			{
				alert("结息日期不能为空，请重新输入！");
				bResult = false;
			}
		}
		else
		{
			alert("必须勾选结息日期！");
			bResult = false;
		}		
	}
	if(bResult)
	{
		if(frmV201.lIsSelectContractNo.checked == false)
		{
			alert("必须勾选合同号！");
			bResult = false;
		}
	}	
	if(bResult)
	{
		if(frmV201.lIsSelectPayFormNo.checked == false)
		{
			alert("必须勾选放款单号！");
			bResult = false;
		}
	}
	if(bResult)
	{
		if(frmV201.lFeeType(0).checked == false && frmV201.lFeeType(1).checked == false && frmV201.lIsSelectInterest.checked == false && frmV201.lIsSelectCompoundInterest.checked == false && frmV201.lIsSelectForfeitInterest.checked == false)
		{
			alert("必须选择一种利息类型，请重新输入！");
			bResult = false;
		}
	}		
	return bResult;
}
function validataMature()
{
	var bResult = true;
	if(bResult)
	{
		if(frmV201.lIsSelectContractNo.checked == false)
		{
			alert("必须勾选合同号！");
			bResult = false;
		}
	}	
	if(bResult)
	{
		if(frmV201.lIsSelectPayFormNo.checked == false)
		{
			alert("必须勾选放款单号！");
			bResult = false;
		}
	}
	if(bResult)
	{
		if(frmV201.lFeeType(0).checked == false && frmV201.lFeeType(1).checked == false && frmV201.lIsSelectInterest.checked == false && frmV201.lIsSelectCompoundInterest.checked == false && frmV201.lIsSelectForfeitInterest.checked == false)
		{
			alert("必须选择一种利息类型，请重新输入！");
			bResult = false;
		}
	}		
	return bResult;
}
function allFields()
{	
	if (lTemp == 1)
	{
		this.ag = new Array("dRate","利率","rate",1);
		this.ar = new Array("strNotifyStartDateFrom","起息日","date",1);
		this.as = new Array("strNotifyStartDateTo","到期日","date",1);		
	}
	if (lTemp == 2)
	{		
		this.a1 = new Array("lPayFormIDTo","初始放款单","magnifier",1);
		this.a2 = new Array("lPayFormIDFrom","终止放款单","magnifier",1);	
		this.a3 = new Array("lContractIDFrom","初始合同号","magnifier",1);
		this.a4 = new Array("lContractIDTo","终止合同号","magnifier",1);			
	}
}
	
</script>
<script language="javascript">
	firstFocus(document.frmV201.lIsSelectSelfLoanSort);
 	//setSubmitFunction("doInterestEstimate()");
	setFormName("frmV201");
</script>
<%
	
	if(Constant.ActionResult.SUCCESS.equals(strActionResult) && "printInterestEstimate".equals(strAction))
	{
	%>
	<!--	
		<script language="JavaScript">
			window.open("q022-c.jsp?strAction=interestEstimate&strSuccessPageURL=q023-v.jsp&strFailPageURL=q023-v.jsp");    
		</script>
	-->		
	<%
	}	
	//SETTHTML.showHomeEnd(out);
	OBHtml.showOBHomeEnd(out);
%>
<%
}
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
%>
<%@ include file="/common/SignValidate.inc" %>
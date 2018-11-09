<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <%--/**
			 * 页面名称 ：v001.jsp
			 * 页面功能 : 通知存款支取通知新增
			 * 作    者 ：YuanHuang
			 * 日    期 ：2006-10-31
			 * 特殊说明 ：通知存款支取通知
			 *			  
			 * 修改历史 ：
			 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.ebank.util.OBHtml,
				com.iss.itreasury.ebank.util.OBConstant,
 				java.net.*"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

  <% 
  long lShowMenu = OBConstant.ShowMenu.YES;
  String strTitle = "通知存款支取通知";
    try 
	{
        //用户登录检测 
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
        }
        /* 显示文件头 */
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
    System.out.println("------------通知存款支取通知/v001.jsp-----------------------------");

    double dDrawAmount = 0;//支取金额
     
   
%>
<form name="frmV001">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">通知存款支取通知</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	   
<br/>
<table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
    	<tr><td width="5" height="25" colspan="7"  class="MsoNormal"></td></tr>
        <tr class="MsoNormal">
		  <td width="5" height="25"></td>
	  	  <td width="130" height="25" align="left">通知存款单据号：</td>
		  <td >
			<fs:dic id="lSubAccountID" size="22" form="frmV001" title="通知存款单据号" sqlFunction="getSQL"  sqlParams='' value="" nextFocus="dDrawAmount" width="600">
				<fs:columns>  
					<fs:column display="通知存款单据号" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs:column display="余额" name="Balance" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					<fs:column display="开户日" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
				</fs:columns>
				<fs:pageElements>
					<fs:pageElement elName="lSubAccountID" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="strStartDate" name="OpenDate" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="dAmount" name="Capital" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="dDepositBalance" name="Balance" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					<fs:pageElement elName="DepositNo" name="DepositNo" type="<%=PagerTypeConstant.STRING %>" elType="hidden" />
				</fs:pageElements>							
			</fs:dic> 
		  </td>
		<% 	
	/*	String strMagnifierName = "通知存款单据号";
		String strFormName = "frmV001";
		String strPrefix = " ";
		String[] strMainNames = {"lSubAccountID","strStartDate","dAmount","dDepositBalance"}; //回显栏名称
		String[] strMainFields = {"DepositNo","OpenDate","Capital","Balance"}; //回显栏对应表字段名
		String[] strReturnNames = {"DepositNo"}; //返回隐含值
		String[] strReturnFields = {"DepositNo"};
		String strReturnInitValues = "";
		String[] strReturnValues = {"-1"};
		String[] strDisplayNames = { URLEncoder.encode("通知存款单据号"),URLEncoder.encode("余额"),URLEncoder.encode("开户日")};
		String[] strDisplayFields = {"DepositNo","Balance","OpenDate"};
		int nIndex = 0;
		String strMainProperty = "size='20'";
		
		String strMatchValue = "DepositNo";
		String strNextControls = "dDrawAmount";
		String strTitlem = "通知存款单据号";
		String strFirstTD = " ";
		String strSecondTD = " ";
		
		OBMagnifier.showZoomCtrl
		(	out,
			strMagnifierName,
			strFormName,
			strPrefix,
			strMainNames,
			strMainFields,
			strReturnNames,
			strReturnFields,
			strReturnInitValues,
			strReturnValues,
			strDisplayNames,
			strDisplayFields,
			nIndex,
			strMainProperty,
			"getSQL()",
			strMatchValue,
			strNextControls,
			strTitlem,
			strFirstTD,
			strSecondTD			
		);
				*/
	%>
  		  			
			<TD width="110">通知存款起始日：</TD>
			<TD >
				<INPUT class=box type="text"  readonly name="strStartDate" value=>
			</TD>
			<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
		</TR>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
			<TD  width="120">
				通知存款本金：
			</TD>
			<TD >
				<fs:amount 
		       		form="frmV001"
	       			name="dAmount"
	       			readonly="true"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
			</TD>
			<TD >
				通知存款余额：							
			<TD >
			  	<fs:amount 
		       		form="frmV001"
	       			name="dDepositBalance"
	       			readonly="true"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
			</TD>			
			<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
			</TR>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
			<TD>
				支取金额：
			</TD>			
			<TD>	
				<fs:amount 
		       		form="frmV001"
	       			name="dDrawAmount"
	       			value="<%=dDrawAmount%>"
	       			nextFocus="notifyDate"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>"/>
			</TD>
			<!-- Added by zwsun 2007-6-19 增加通知日期 -->
			<!-- begin -->
			<td>
				通知日期：
			</td>
			<td>
				<fs_c:calendar 
	          	    name="notifyDate"
		          	value="" 
		          	properties="nextfield ='submitfunction'" 
		          	size="20"/>
			</td>
			<td width="5" height="25" colspan="2"  class="MsoNormal"></td>
			<!-- end -->
		</TR>
		<tr><td width="5" height="25" colspan="7"  class="MsoNormal"></td></tr>
		<tr>
          <td colspan="4" align="right">
				<DIV align=right></DIV>
				<td align=right>
				
				 <input class=button1 name="next" type="button" value=" 保 存 " onClick="doNext();" onKeydown="if(event.keyCode==13) { doNext();}"> 
				 <input class=button1 name="Submit32" type="button" value=" 链接查找 " onClick="doLinkSearch();"></td>
          </td>
          <td>&nbsp;</td>
          <td width="5" height="25" colspan="1"  class="MsoNormal"></td>
        </tr>
        <tr><td>&nbsp;</td><td width="5" height="25" colspan="6"  class="MsoNormal"></td></tr>
	</TABLE>
 </td>
	  </tr>
</table>
</form>
<script>
setFormName("frmV001");
firstFocus(frmV001.lSubAccountID);
////setSubmitFunction("doNext(frmV001)");

function doNext()
{
	if(!check()) return false;
	if(confirm('是否保存？')) 
	{ 
		frmV001.action = "../control/c001.jsp";
		showSending();
		frmV001.submit();
	}
}
function check()
{   
	var lSubAccountID = frmV001.lSubAccountID.value;
	var DepositNo = frmV001.DepositNo.value;
	var notifyDate = frmV001.notifyDate.value;
	var strStartDate = frmV001.strStartDate.value;
	//alert(strStartDate);
	if( strStartDate> notifyDate)
	{    
	   alert("通知日期应该大于通知存款起始日")
	   frmV001.notifyDate.focus();
	   return false;
	 }
	if(lSubAccountID != DepositNo)
	{	
		alert("通知存款单据号，请从放大镜中选择");
		frmV001.lSubAccountID.focus();
		return false;					
	}
	
	if(Trim(frmV001.dDrawAmount.value)=="")
	{
		alert("支取金额不能为空");
		frmV001.dDrawAmount.focus();
		return false;
	}	
	if(parseFloat(reverseFormatAmount(frmV001.dDrawAmount.value))<0.01){
		alert("支取金额不能小于0.01");
		frmV001.dDrawAmount.focus();
		return false;
	}
	if(checkAmount(frmV001.dDrawAmount,1,"支取金额") == false)
	{
		return false;
	}
	if(!validate()) return false;
	//Added by zwsun , 2007-06-18, 增加日期校验
	if(checkDate(frmV001.notifyDate,1,"通知日期") == false){
		return false;
	}
	
	return true;
}

function validate()
{			
	if(parseFloat(reverseFormatAmount(document.frmV001.dDepositBalance.value))-parseFloat(reverseFormatAmount(document.frmV001.dDrawAmount.value))<0)
	{	
		alert("支取金额不能大于存单余额，请重新输入！");
		document.frmV001.dDrawAmount.focus();
		bResult = false;
		return false;
	}

	return true;
}

function doLinkSearch()
{
	frmV001.action = "../view/v002.jsp";
	showSending();
	frmV001.submit();
}


function getSQL()
{
	var sql = "SELECT  a.af_sDepositNo  DepositNo ,to_char((a.mBalance-a.mUncheckPaymentAmount),'9,999,999,999,999.99')  Balance,to_char(a.dtOpen,'yyyy-mm-dd')  OpenDate , to_char(a.mOpenAmount,'9,999,999,999,999.99')Capital";
	sql += " from sett_SubAccount  a , sett_Account  ma ,sett_accountType  sat ";
	sql += " where a.nAccountID = ma.ID ";
	sql += " and  ma.nAccountTypeID=sat.id ";
	sql += " and  (a.mBalance  -  a.mUncheckPaymentAmount)  >  0 ";
	sql += " and sat.nstatusid = 1 " ;
	sql += " and sat.naccountgroupid =3"; //通知存款为3
	sql += " and ma.nclientid = <%= sessionMng.m_lClientID%>";
	
	return sql;
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
<jsp:include page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp" %>

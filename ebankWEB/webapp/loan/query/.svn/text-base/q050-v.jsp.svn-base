<%
/**
 * 页面名称 ：q050-v.jsp
 * 页面功能 : 贴现查询-查询条件
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
	java.util.*,
	java.net.URLEncoder,
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
	com.iss.itreasury.ebank.obquery.dataentity.*,
	com.iss.itreasury.loan.loancommonsetting.dataentity.*,
	com.iss.itreasury.loan.util.*,
	com.iss.itreasury.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="termInfo" scope="session" class="com.iss.itreasury.ebank.obquery.dataentity.OBQueryTermInfo"></jsp:useBean>
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

   		
        //显示文件头
        OBHtml.showOBHomeHead(out,sessionMng,"贴现合同查询",Constant.YesOrNo.YES);
        termInfo.reset();
%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=265 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现合同——查询</B></TD></TR>
  <TR>
    <TD height=185>
      <TABLE align=center border=0 width="100%">
        <TBODY>
        <TR>      
		<%
 		//申请书编号放大镜，来自loan_loanForm
		String strMagnifierName1 = URLEncoder.encode("贴现合同编号");							//放大镜的名称
		String strFormName1 = "frm";										//主页面表单名称
		String strPrefix1 ="";												////控件名称前缀
		String[] strMainNames1 = {"contractCodeFrom"};				//放大镜回显栏位值列表
		String[] strMainFields1 = { "sContractCode"};				//放大镜回显栏位对应的表格字段
		String[] strReturnNames1 = {"codeBegin"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields1 = {"sContractCode"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues1="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues1 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames1 = {URLEncoder.encode("贴现合同编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields1 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex1 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty1 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue1="sContractCode";									////放大镜要模糊匹配的字段
		String strNextControls1 = "contractCodeTo";								//设置下一个焦点
		String strTitle1="贴现合同编号 由";
		String strFirstTD1=" width=150 ";
		String strSecondTD1=" align='left'";
			
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName1,strFormName1,strPrefix1,strMainNames1,strMainFields1,
			strReturnNames1,strReturnFields1, strReturnInitValues1, strReturnValues1,strDisplayNames1,strDisplayFields1,
			intIndex1,strMainProperty1,"getContractCode("+strMainNames1[0]+".value)", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
		%> 
	
		<%
 		//申请书编号放大镜，来自loan_loanForm
		String strMagnifierName2 = URLEncoder.encode("贴现合同编号");							//放大镜的名称
		String strFormName2 = "frm";										//主页面表单名称
		String strPrefix2 ="";											////控件名称前缀
		String[] strMainNames2 = {"contractCodeTo"};								//放大镜回显栏位值列表
		String[] strMainFields2 = { "sContractCode"};							//放大镜回显栏位对应的表格字段
		String[] strReturnNames2 = {"codeEnd"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields2 = {"sContractCode"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues2="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues2 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames2 = {URLEncoder.encode("贴现合同编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields2 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex2 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty2 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue2="sContractCode";									////放大镜要模糊匹配的字段
		String strNextControls2 = " inputUser ";								//设置下一个焦点
		String strTitle2="到";
		String strFirstTD2=" align='right' colspan=1 ";
		String strSecondTD2=" ";
	
		OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
			strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
			intIndex2,strMainProperty2,"getContractCode("+strMainNames2[0]+".value)", strMatchValue2,strNextControls2,strTitle2, strFirstTD2, strSecondTD2);
		%>
          <TD  height=28>&nbsp;</TD></TR><tr>
          
        <%
       	String strMagnifierName9 = URLEncoder.encode("合同管理人");							//放大镜的名称
		String strFormName9 = "frm";										//主页面表单名称
		String strPrefix9 ="";											////控件名称前缀
		String[] strMainNames9 = {"inputUser"};	//放大镜回显栏位值列表
		String[] strMainFields9 = { "sName"};		//放大镜回显栏位对应的表格字段
		String[] strReturnNames9 = {"inputUserID"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields9 = {"ID"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues9="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues9 = {"-1"};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames9 = {URLEncoder.encode("编号"),URLEncoder.encode("姓名")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields9 = {"ID","sName"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex9 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty9 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue9="ID";									////放大镜要模糊匹配的字段
		String strNextControls9 = "amountBeginStr";								//设置下一个焦点
		String strTitle9="<FONT size=2>合同管理人</FONT>";
		String strFirstTD9=" colspan=1 ";
		String strSecondTD9=" ";
		boolean blnIsOptional9=false;
		OBMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
			strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
			intIndex9,strMainProperty9,"getContractUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
		%>  
          <TD colSpan=2>&nbsp;</TD></TR>
                    
        <TR>
           <TD height=33 width=150><FONT size=2>贴现金额：</FONT></TD>          
          <TD height=33 width=170>￥
		  <script language="javascript">
        	createAmountCtrl("frm","amountBeginStr","","amountEndStr","");
          </script>
		  </TD>
          <TD width=41 align=right><FONT size=2>到：&nbsp;&nbsp;￥</FONT></TD>
          <TD width=259>
 		  <script language="javascript">
        	createAmountCtrl("frm","amountEndStr","","statusID","");
          </script>
		  </TD>              
        <TR>
           <TD height=33 width=150><FONT size=2>状态：</FONT></TD>          
          <TD height=33 width=170>
  		  <select class=box name="statusID" onfocus="nextfield='search1';">
					<option value="99"></option>
                <%
              		long contractStatusVal[]=LOANConstant.ContractStatus.getAllCode();
              		String contractStatusName="";
              		for ( int i=0;i<contractStatusVal.length;i++ )
              		{
              			contractStatusName=LOANConstant.ContractStatus.getName(contractStatusVal[i]);
              	%> 
					<option value="<%=contractStatusVal[i]%>"><%=contractStatusName%></option>
				<%
					}
				%>	
		 </select>		  
		  </TD>
          <TD colSpan=2>&nbsp;</TD></TR>
		<TR>
		  <TD colSpan=4><HR></TD>
		</TR>
        <TR>
           <TD colSpan=1 height=2>&nbsp;</TD>
          <TD align=right colSpan=3 height=2 width=200>
			<INPUT class=button name="search1" onclick="search()" onfocus="nextfield='submitfunction';" type="button" value=" 查 找 "> 
          </TD></TR></TBODY></TABLE></TD></TR>
 </TBODY></TABLE>
<P><BR></P>
<input type="hidden" name="control" value="view">
<input type="hidden" name="queryLevel" value="high">
<input type="hidden" name="searchAction" value="search">
<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
<input name="purpose" type="hidden" value="TX">
</form>

<script language="JavaScript">

function search()
{
	confirmSave(frm); 
}

function  confirmSave(frm)
{
	
/////////////////////////////
	if (!checkAmount(frm.amountBeginStr,0,"起始贴现金额 "))
		return(false);
	if (!checkAmount(frm.amountEndStr,0,"终止贴现金额 "))
		return(false);
	if ((frm.amountBeginStr.value.length>0) && (frm.amountEndStr.value.length>0))
	{
		if (parseFloat(reverseFormatAmount1(frm.amountBeginStr.value)) > parseFloat(reverseFormatAmount1(frm.amountEndStr.value)))
		{
			alert("起始贴现金额应小于等于终止贴现金额！");
			return(false);
		}
	}
/////////////////////////////
	
	frm.action="q032-c.jsp";
	frm.submit();		
}
firstFocus(frm.contractCodeFrom);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
	
	function  getClient(client)
	{
		var sql="select id,scode,sName from client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";

		return sql;
	}
	function  getContractCode(code)
	{
		var sql="select id, sContractCode from loan_ContractForm where nTypeID=<%=LOANConstant.LoanType.TX%> and (nBorrowClientID=<%=sessionMng.m_lClientID%> or nConsignClientID=<%=sessionMng.m_lClientID%>) and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode ";
		return sql;	
	}
	function getContractUser()
	{
		var sql="select distinct a.nInputUserID as ID,b.sName from Loan_ContractForm a,userInfo b where b.id=a.nInputUserID";
		return sql;
	}
</script>			 
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"委托客户选择", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 <%@ include file="/common/SignValidate.inc" %>


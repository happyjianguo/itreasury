<%
/**
 * 页面名称 ：q020-v.jsp
 * 页面功能 : 贷款申请查询-普通查询条件页面
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
	com.iss.itreasury.ebank.util.*,			
	com.iss.itreasury.ebank.obdataentity.*,	
	com.iss.itreasury.ebank.obquery.bizlogic.*,
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
        OBHtml.showOBHomeHead(out,sessionMng,"贷款申请查询",Constant.YesOrNo.YES);
        long officeID=sessionMng.m_lOfficeID;
        
        termInfo.reset();
%>	

<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<form name="frm" >
<TABLE border=0 class=top width="87%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贷款申请查询</B></TD></TR>
  <TR>
    <TD>
      <TABLE align=center border=0 width=730>
        <TR>
          <TD width="80"><FONT size=2>贷款类型：</FONT></TD>
		  <TD>&nbsp;</TD>
          <TD width="100">
		    <select name="typeID" class="box" size="1" onfocus="nextfield='sCodeBegin';">
		       <option value="99"></option>
                <%
              		long loanTypeVal[]=LOANConstant.LoanType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
              		String loanTypeName="";
              		for ( int i=0;i<loanTypeVal.length;i++ )
              		{
              			loanTypeName=LOANConstant.LoanType.getName(loanTypeVal[i]);
              	%> 
                   <option value="<%=loanTypeVal[i]%>"><%=loanTypeName%></option>
                <%      
                	}
                %>		   
           </select>
		  </TD>
		  <td colspan=3>&nbsp;</td>
        </TR>
        <TR>
          <TD><FONT size=2>申请书编号：</FONT></TD>
                  <%
 		//申请书编号放大镜，来自loan_loanForm
		
		String strMagnifierName1 = URLEncoder.encode("申请书编号");							//放大镜的名称
		String strFormName1 = "frm";										//主页面表单名称
		String strPrefix1 ="";												////控件名称前缀
		String[] strMainNames1 = {"sCodeBegin"};	//放大镜回显栏位值列表
		String[] strMainFields1 = { "sApplyCode"};		//放大镜回显栏位对应的表格字段
		String[] strReturnNames1 = {"codeBegin"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields1 = {"sApplyCode"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues1="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues1 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames1 = {URLEncoder.encode("申请书编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields1 = {"sApplyCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex1 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty1 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue1="sApplyCode";									////放大镜要模糊匹配的字段
		String strNextControls1 = "sCodeEnd";								//设置下一个焦点
		String strTitle1="由";
		String strFirstTD1=" width=50 ";
		String strSecondTD1=" ";
			
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName1,strFormName1,strPrefix1,strMainNames1,strMainFields1,
			strReturnNames1,strReturnFields1, strReturnInitValues1, strReturnValues1,strDisplayNames1,strDisplayFields1,
			intIndex1,strMainProperty1,"getApplyCode()", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
		%> 

		<%
 		//申请书编号放大镜，来自loan_loanForm
 		 		
		String strMagnifierName2 = URLEncoder.encode("申请书编号");							//放大镜的名称
		String strFormName2 = "frm";										//主页面表单名称
		String strPrefix2 ="";											////控件名称前缀
		String[] strMainNames2 = {"sCodeEnd"};								//放大镜回显栏位值列表
		String[] strMainFields2 = { "sApplyCode"};							//放大镜回显栏位对应的表格字段
		String[] strReturnNames2 = {"codeEnd"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields2 = {"sApplyCode"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues2="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues2 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames2 = {URLEncoder.encode("申请书编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields2 = {"sApplyCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex2 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty2 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue2="sApplyCode";									////放大镜要模糊匹配的字段
		String strNextControls2 = " statusID ";								//设置下一个焦点
		String strTitle2="到";
		String strFirstTD2=" align='right' colspan=2 ";
		String strSecondTD2=" ";
	
		OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
			strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
			intIndex2,strMainProperty2,"getApplyCode()", strMatchValue2,strNextControls2,strTitle2, strFirstTD2, strSecondTD2);
		%>
		</TR>	
			  
        <TR>
          <TD><FONT size=2>申请状态：</FONT></TD>
          <td>&nbsp;</td>
          <TD colspan=5>
  		  		  
  		  <select class=box name="statusID" onfocus="nextfield='inputUser';">
			<option value="99"></option>
                <%
              		long loanStatusVal[]=LOANConstant.LoanStatus.getAllCode();
              		String loanStatusName="";
              		for ( int i=0;i<loanStatusVal.length;i++ )
              		{
              			loanStatusName=LOANConstant.LoanStatus.getName(loanStatusVal[i]);
              	%> 
					<option value="<%=loanStatusVal[i]%>"><%=loanStatusName%></option>
				<%
					}
				%>	
		</select>
		</TD>
        </TR>
         <TR>
         
        <%
       	String strMagnifierName9 = URLEncoder.encode("申请书管理人");							//放大镜的名称
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
		String strNextControls9 = "searchlow";								//设置下一个焦点
		String strTitle9="<FONT size=2>申请书管理人</FONT>";
		String strFirstTD9=" colspan=2 ";
		String strSecondTD9=" ";
		boolean blnIsOptional9=false;
		OBMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
			strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
			intIndex9,strMainProperty9,"getLoanUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
		%>  
       <td colspan=3></td>
       </TR>
       <tr>
       <td colspan=6><HR></td>
       </tr>
	<tr>
	<td colspan="5"></td>
 	<TD><FONT size=2>
	 <INPUT class=button name=searchlow onclick="searchto()" type="button" value=" 查 找 " onfocus="nextfield='submitfunction';">
 	<INPUT class=button name=searchhign onclick="location.href='q021-v.jsp'" type="button" value=" 高 级 查 找 ">
 	</FONT></TD>
		</tr>

		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>	   
	  </TABLE>
		  
	</TD></TR></TBODY></TABLE>
		  
<input type="hidden" name="control" value="view">
<input type="hidden" name="queryLevel" value="low">
<input type="hidden" name="searchAction" value="search">

</form>
<script language="JavaScript">
function searchto()
{
frm.action="q022-c.jsp";
frm.submit();
}

function  confirmSave(frm)
	{
    frm.control.value="view";
    frm.submit();		
	}
firstFocus(frm.typeID);
//setSubmitFunction("searchto()");
setFormName("frm");
</script> 
<script language="javascript">
	
	function  getClient(client)
	{
		var sql="select id,scode,sName from client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";

		return sql;
	}
	function  getApplyCode(code)
	{
		var sql="select id, sApplyCode from loan_loanForm where (nBorrowClientID=<%=sessionMng.m_lClientID%> or nConsignClientID=<%=sessionMng.m_lClientID%> ) and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sApplyCode ";
		return sql;	
	}
	function  getAccount(account)
	{
		var sql="select ID,SACCOUNTNO from sett_account where nStatusID=<%=Constant.RecordStatus.VALID%> order by id";
		return sql;
	}
	function getLoanUser()
	{
		//modified by mzh_fu 2007-3-13 加了办事处过滤条件
		var sql="select distinct a.nInputUserID as ID,b.sName from Loan_loanForm a,userInfo b where b.id=a.nInputUserID and a.nofficeid=<%=sessionMng.m_lOfficeID%> and b.nofficeid=a.nofficeid";
		return sql;
	}
</script>		 
<%
	OBHtml.showOBHomeEnd(out);
	}
	catch( IException ie )
	{
		//OBHtml.showExceptionMessage(out,sessionMng,ie,,"贷款申请查询", Constant.RecordStatus.VALID); 
		ie.printStackTrace();
		out.flush();
		return; 
	}
%>
 <%@ include file="/common/SignValidate.inc" %>


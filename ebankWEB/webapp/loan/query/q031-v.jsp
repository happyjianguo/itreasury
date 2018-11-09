<%
/**
 * 页面名称 ：q031-v.jsp
 * 页面功能 : 合同查询，高级查询页面
 * 作    者 ：qqgd
 * 日    期 ：2003-09-27
 * 特殊说明 ：
 *			  
 * 转入页面 : 
 */
%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
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
        OBHtml.showOBHomeHead(out,sessionMng,"合同高级查询",Constant.YesOrNo.YES);
        
		boolean isdq=false;
		termInfo.reset();	              
%>	

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<form name="frm" >
<TABLE border=0 class=top width="100%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贷款合同查询</B></TD></TR>
	<TR>
	<TD>
     <table align=center border=0 width=100%>
            <TR>
          <TD width="80"><FONT size=2>贷款类型：</FONT></TD>
		  <TD>&nbsp;</TD>
          <TD width="100">
		  		  
		  	<select name="typeID" class="box" size="1" onfocus="nextfield='contractCodeFrom';">
		       <option value="99"></option>
                <%
              		long loanTypeVal[]=LOANConstant.LoanType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
              		String loanTypeName="";
              		for ( int i=0;i<loanTypeVal.length;i++ )
              		{
              			if ( loanTypeVal[i]==LOANConstant.LoanType.TX )
              				continue;
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
          <TD><FONT size=2>合同编号：</FONT></td>
        <%
 		//申请书编号放大镜，来自loan_loanForm
		String strMagnifierName1 = URLEncoder.encode("合同编号");							//放大镜的名称
		String strFormName1 = "frm";										//主页面表单名称
		String strPrefix1 ="";												////控件名称前缀
		String[] strMainNames1 = {"contractCodeFrom"};				//放大镜回显栏位值列表
		String[] strMainFields1 = { "sContractCode"};				//放大镜回显栏位对应的表格字段
		String[] strReturnNames1 = {"codeBegin"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields1 = {"sContractCode"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues1="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues1 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames1 = {URLEncoder.encode("合同编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields1 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex1 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty1 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue1="sContractCode";									////放大镜要模糊匹配的字段
		String strNextControls1 = "contractCodeTo";								//设置下一个焦点
		String strTitle1="由";
		String strFirstTD1=" width=50 ";
		String strSecondTD1=" ";
			
		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName1,strFormName1,strPrefix1,strMainNames1,strMainFields1,
			strReturnNames1,strReturnFields1, strReturnInitValues1, strReturnValues1,strDisplayNames1,strDisplayFields1,
			intIndex1,strMainProperty1,"getContractCode("+strMainNames1[0]+".value)", strMatchValue1,strNextControls1 ,strTitle1, strFirstTD1, strSecondTD1 );
		%> 

		<%
 		//申请书编号放大镜，来自loan_loanForm
 		 		
		String strMagnifierName2 = URLEncoder.encode("合同编号");							//放大镜的名称
		String strFormName2 = "frm";										//主页面表单名称
		String strPrefix2 ="";											////控件名称前缀
		String[] strMainNames2 = {"contractCodeTo"};								//放大镜回显栏位值列表
		String[] strMainFields2 = { "sContractCode"};							//放大镜回显栏位对应的表格字段
		String[] strReturnNames2 = {"codeEnd"};							//放大镜返回值列表(隐含值)
		String[] strReturnFields2 = {"sContractCode"};								//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues2="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues2 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames2 = {URLEncoder.encode("合同编号")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields2 = {"sContractCode"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex2 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty2 = " ";									//放大镜的对应控件栏位属性
		String strMatchValue2="sContractCode";									////放大镜要模糊匹配的字段
		String strNextControls2 = " statusID ";								//设置下一个焦点
		String strTitle2="到";
		String strFirstTD2=" align='right' colspan=2 ";
		String strSecondTD2=" ";
	
		OBMagnifier.showZoomCtrl(out,strMagnifierName2,strFormName2,strPrefix2,strMainNames2,strMainFields2,
			strReturnNames2,strReturnFields2, strReturnInitValues2, strReturnValues2,strDisplayNames2,strDisplayFields2,
			intIndex2,strMainProperty2,"getContractCode("+strMainNames2[0]+".value)", strMatchValue2,strNextControls2,strTitle2, strFirstTD2, strSecondTD2);
		%>
		</TR>	
			  
        <TR>
          <TD><FONT size=2>合同状态：</FONT></TD>
          <td>&nbsp;</td>
          <TD colspan=5>
  		  		  
  		  <select class=box name="statusID" onfocus="nextfield='inputUser';">
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
          </TR>
        <tr>
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
		String strFirstTD9=" colspan=2 ";
		String strSecondTD9=" ";
		boolean blnIsOptional9=false;
		LOANMagnifier.showZoomCtrl(out,strMagnifierName9,strFormName9,strPrefix9,strMainNames9,strMainFields9,
			strReturnNames9,strReturnFields9, strReturnInitValues9, strReturnValues9,strDisplayNames9,strDisplayFields9,
			intIndex9,strMainProperty9,"getContractUser()", strMatchValue9,strNextControls9 ,strTitle9, strFirstTD9, strSecondTD9 );
		%>  
		
        </TR>
        
        <TR><td colspan = 6>
            <HR></td>
          </TR>		
	     <TR>
          <TD><FONT size=2>贷款金额:</FONT></TD>
          <td align="right"><FONT size=2>由￥</Font></td>
          <TD>
		  <script language="javascript">
        	createAmountCtrl("frm","amountBeginStr","","amountEndStr","");
          </script>
		  </TD>
		  <td>&nbsp;</td>
		  <TD align="right"><FONT size=2>到￥</FONT></TD>
		  <TD>
 		  <script language="javascript">
        	createAmountCtrl("frm","amountEndStr","","loanDateBegin","");
          </script>
		  </TD>
		  
          </TR>
        <TR>
          <TD><FONT size=2>贷款日期:</FONT></TD>
          <td align="right"><FONT size=2>由</Font></td>
          <TD>
          <FONT size=2>
          <fs_c:calendar 
			         	    name="loanDateBegin"
				          	value="" 
				          	properties="nextfield ='loanDateEnd'" 
				          	size="12"/>
		  </FONT>
		  </TD>
		  <td>&nbsp;</td>
		  <TD align="right"><FONT size=2>到</FONT></TD>
		  <TD>
          <FONT size=2>
          <fs_c:calendar 
			         	    name="loanDateEnd"
				          	value="" 
				          	properties="nextfield ='intervalNum'" 
				          	size="12"/>
		  </FONT>
		  </TD>
          </TR>	   
        <TR>
          <TD><FONT size=2>贷款期限:</FONT></TD>
          <td align="right"><FONT size=2>&nbsp;</Font></td>
          <TD>
          <input class=box  type="text" name="intervalNum" size=8 maxlength="5" onfocus="nextfield='assureTypeID'">月
		  </TD>
		  <td colspan=2>&nbsp;</td>
          </TR>
    <tr> 
      <td colspan=9 height=18> 
        <hr>
      </td>
    </tr>
    <tr> 
      <td colspan=2>保证方式:</td>
      <td  >
        <select name="assureTypeID" class="BOX" size="1" onFocus="nextfield='creditLevelID';">
          <option value=-1> </option>
                <%
              		long assTypeVal[]=LOANConstant.AssureType.getAllCode();
              		String assTypeName="";
              		for ( int i=0;i<assTypeVal.length;i++ )
              		{
              			assTypeName=LOANConstant.AssureType.getName(assTypeVal[i]);
              	%>           
          <option value=<%=assTypeVal[i]%> ><%=assTypeName%></option>
          		<%
          			}
          		%>	
        </select>
	  </td>
      <td > 
          信用等级:
      </td>
      <td > 
	  <select name="creditLevelID" class="BOX" size="1" onFocus="nextfield='isPartner'" >
                <option value="-1"></option>
			<%
				long[] CreditTmp = LOANConstant.CreditLevel.getAllCode();
				for(int i =0;i< CreditTmp.length;i++)
				{

			%>
						<OPTION value="<%=CreditTmp[i]%>">
							<%=LOANConstant.CreditLevel.getName(CreditTmp[i])%>
						</OPTION>
			<%
				}
			%>
              </select> 
      </td>
      <td >股东: </td>
      <td > 
        <select name="isPartner" class="BOX" size="1" onFocus="nextfield='isTechnical';">
          <option value=-1> </option>
          <option value=1>是</option>
          <option value=2>否</option>
        </select>
      </td>
	  <td colspan=2>&nbsp;</td>
    </tr>
    <tr> 
      <td  colspan=2>技改贷款: </td>
      <td > 
        <select name="isTechnical" class="BOX" size="1" onFocus="nextfield='isCircle';">
          <option value=-1> </option>
          <option value=1>是</option>
          <option value=2>否</option>
        </select>
      </td>
      <td >循环贷款:</td>
      <td > 
        <select name="isCircle" class="BOX" size="1" onFocus="nextfield='ParentCorpName';">
          <option value=-1>   </option>
          <option value=1>是</option>
          <option value=2>否</option>
        </select>
      </td>
      <td ></td>
      <td ></td>
      <td colspan=2> </td>
      </tr>
 
      <tr>
      <%
 		//上级主管部门放大镜
		String strMagnifierName7 = URLEncoder.encode("上级主管单位");							//放大镜的名称
		String strFormName7 = "frm";											//主页面表单名称
		String strPrefix7 ="";												////控件名称前缀
		String[] strMainNames7 = {"ParentCorpName"};							//放大镜回显栏位值列表
		String[] strMainFields7 = { "sName"};								//放大镜回显栏位对应的表格字段
		String[] strReturnNames7 = {"parentCorpID"};						//放大镜返回值列表(隐含值)
		String[] strReturnFields7 = {"ID"};									//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues7="";						////放大镜回显栏位对应的初始值
		String[] strReturnValues7 = {""};								//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames7 = {URLEncoder.encode("客户编号"),URLEncoder.encode("客户名称")};				//放大镜小窗口显示的栏位名称
		String[] strDisplayFields7 = {"sCode","sName"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex7 = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty7 = "";									//放大镜的对应控件栏位属性
		String strMatchValue7="sCode";									////放大镜要模糊匹配的字段
		String strNextControls7 = "riskLevelID";								//设置下一个焦点
		String strTitle7="上级主管部门";
		String strFirstTD7=" colspan=2 ";
		String strSecondTD7=" ";
		String strSQL = "SELECT id,sCode,sName,SLOANCARDNO,SLOANCARDPWD FROM client where nStatusID="
						+ Constant.RecordStatus.VALID
						+ " and nOfficeID= "
						+ sessionMng.m_lOfficeID + " order by id";

		//OBMagnifier.showZoomCtrl(out,strMagnifierName7,strFormName7,strPrefix7,strMainNames7,strMainFields7,
		//	strReturnNames7,strReturnFields7, strReturnInitValues7, strReturnValues7,strDisplayNames7,strDisplayFields7,
		//	intIndex7,strMainProperty7,"getClient("+strMainNames7[0]+".value)", strMatchValue7,strNextControls7 ,strTitle7, strFirstTD7, strSecondTD7 );	
			
			OBMagnifier.showZoomCtrl(out,strMagnifierName7,strFormName7,strPrefix7,strMainNames7,strMainFields7,
			strReturnNames7,strReturnFields7, strReturnInitValues7, strReturnValues7,strDisplayNames7,strDisplayFields7,
			intIndex7,strMainProperty7, strSQL, strMatchValue7,strNextControls7 ,strTitle7, strFirstTD7, strSecondTD7 );					
		%>
		 
      <td height=13 >贷款风险状态：</td>
            <td height=13 > 
              <select class='box' name="riskLevelID" onfocus="nextfield='typdID1';">
                <option value=-1>　</option>
              <%
               	long ventureVal[]=LOANConstant.VentureLevel.getAllCode();
              	String ventureName="";
              	for ( int i=0;i<ventureVal.length;i++ )
              	{
              		ventureName=LOANConstant.VentureLevel.getName(ventureVal[i]);
              %> 
           		<option value="<%=ventureVal[i]%>"><%=ventureName%></option>
                <%	
              	}
              %> 
             </select>
            </td>
            <td height=16 >按地区分类：</td>
            <td height=13 > 
            <%LOANHTML.showAreaTypeListControl(out,"typeID1", -1, " onfocus=nextfield='typeID2';" );%>
            </td>
            <td colspan=2>&nbsp;</td>
          </tr>
           <tr> 
            <td height=16 colspan=2><% if (!isdq) { %>   按行业分类1：<%}%></td>
            <td height=13 colspan=3> 
            <% if (!isdq) { %>   
            <%LOANHTML.showIndustryType1ListControl(out,"typeID2", -1, " onfocus=nextfield='document.frm.typeID3'; ");%>                          
            <%}%>
            </td>
            <td height=13 ><% if (!isdq) { %>   按行业分类2：<%}%></td>
           
            <td>
            <% if (!isdq) { %>   
            <%LOANHTML.showIndustryType2ListControl(out,"typeID3", -1, " onfocus=nextfield='searchlow';" );%>
            <%}%>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
      
      </tr>
    <!--
**********************************************************************************************************
**********************************************************************************************************
-->
    <tr> 
      <td colspan="6">&nbsp;</td>
      <td colspan="3"> 
        <input class=button name=searchlow onClick="confirmSave(frm)" type="button" value=" 查 找 " onFocus="nextfield='submitfunction';">
        <input class=button name=searchhign onClick="location.href='q030-v.jsp?txtAction=First'" type="button" value=" 返 回 ">
      </td>
    </tr>
  </table>
  </TD>
  </TR>
  </TBODY></TABLE>
<input type="hidden" name="control" value="view">
<input type="hidden" name="queryLevel" value="high">
<input type="hidden" name="searchAction" value="search">
<input type=hidden name=txtAction value=query>


</form>
<script language="JavaScript">
function  confirmSave(frm)
{
  if (frm.loanDateBegin.value!="")
  {
	if (!checkDateStart(frm.loanDateBegin,frm.loanDateBegin.value,"贷款起始日期"))
	  {
		 return false;
	  }
  }   
 if (frm.loanDateEnd.value!="")
  {
 
	if (!checkDateEnd(frm.loanDateEnd,frm.loanDateEnd.value,"贷款结束日期"))
	  {
		 return false;
	  }  
  }	  
///////////////////////////
	if (!checkAmount(frm.amountBeginStr,0,"金额"))
	  {
		 return false;
	  }  
		if (!checkAmount(frm.amountEndStr,0,"金额"))
	  {
		 return false;
	  }  
///////////////////////////	    
   if (!InputValid(frm.intervalNum, 0, "int", 0, 0, 0,"贷款期限")) 
		{
			return false;
		}
		//////////////////////////////   
    frm.control.value="view";
	frm.action="q032-c.jsp";
    frm.submit();
    
	}
firstFocus(frm.typeID);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");
</script> 

<script language="javascript">
	
	function  getClient(client)
	{
		var sql="select id,scode,sName from client where nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";

		return sql;
	}
	function  getContractCode(code)
	{
		var sql="select id, sContractCode from loan_ContractForm where nTypeID<><%=LOANConstant.LoanType.TX%>  and NCURRENCYID=<%=sessionMng.m_lCurrencyID%> order by sContractCode";
		return sql;	
	}
	function  getAccount(account)
	{
		var sql="select ID,SACCOUNTNO from sett_account where nStatusID=<%=Constant.RecordStatus.VALID%> order by id";
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


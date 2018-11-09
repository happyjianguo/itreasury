<!--Header start-->
<%
/**
 * 页面名称 ：l001-v.jsp
 * 页面功能 : 显示借款单位资料，如果是委托贷款也显示委托单位资料
 * 特殊说明 ：
 */
%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.net.URLEncoder,
			com.iss.itreasury.loan.util.*,
			com.iss.itreasury.ebank.util.*,			
			com.iss.itreasury.ebank.obsystem.bizlogic.*,
			com.iss.itreasury.loan.loancommonsetting.dataentity.*,			
			java.rmi.RemoteException,
			com.iss.itreasury.util.*"
%>
<!--Header end-->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%

    String tmp="";
	String wtClientID="-1";			//委托客户ID
	String wtClientName="";			//委托客户名称
	long loanID=-1;
	
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

		//定义变量
		long clientID=sessionMng.m_lClientID;
		String type=(String)request.getAttribute("lLoanType");
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
    	String action="";
    	long statusID=-1;
    	
    	tmp=(String)request.getAttribute("txtAction");
    	if ( (tmp!=null)&&(tmp.length()>0) )
	    	action=tmp;
    	
    	tmp=(String)request.getAttribute("nStatusID");
    	if ( (tmp!=null)&&(tmp.length()>0) )
	    	statusID=Long.valueOf(tmp).longValue();
    
		tmp=(String)request.getAttribute("wtClientID");		//借款客户ID
		if ( (tmp!=null)&&(tmp.length())>0 )
			wtClientID=tmp;
		

		tmp=(String)request.getAttribute("wtClientName");		//借款客户名称
		if ( (tmp!=null)&&(tmp.length())>0 )
			wtClientName=tmp;
    		
		tmp=(String)request.getAttribute("lLoanID");		//借款客户名称
		if ( (tmp!=null)&&(tmp.length())>0 )
			loanID=Long.valueOf(tmp).longValue();;

	
        //显示文件头
        if ( (action.equals("modify"))||(action.equals("check")) )
        	OBHtml.showOBHomeHead(out,sessionMng,"[借款单位选择]",Constant.YesOrNo.NO);
        else
        	OBHtml.showOBHomeHead(out,sessionMng,"[借款单位选择]",Constant.YesOrNo.YES);
        
%>	


<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<safety:resources />
<form name="frm">
<TABLE border=0 class=top height=24 width="730">
<TR class="tableHeader">
   <TD class=FormTitle height=29><B><%=loanTypeName%></B></TD>
</TR>
<TR>
  <TD>
  <TABLE align=center border=0 width="100%">
  <TR>
     <TD colSpan=6 height=49><U><%=loanTypeName%>基本资料</U></TD>
  </TR>
  <TR>
<%
		String strMagnifierName = URLEncoder.encode("借款单位");							//放大镜的名称
		String strFormName = "frm";										//主页面表单名称
		String strPrefix ="";											////控件名称前缀
		String[] strMainNames = {"wtclientname"};						//放大镜回显栏位值列表
		String[] strMainFields = { "sName"};							//放大镜回显栏位对应的表格字段
		String[] strReturnNames = {"wtClientID"};						//放大镜返回值列表(隐含值)
		String[] strReturnFields = {"ID"};	
		
									//放大镜返回值(隐含值)对应的表格字段列表
		String   strReturnInitValues=wtClientName;
			
					////放大镜回显栏位对应的初始值
		String[] strReturnValues = {""+wtClientID};						//放大镜返回值(隐含值)对应的初始值
		String[] strDisplayNames = {URLEncoder.encode("借款单位编号"),URLEncoder.encode("借款单位名称")};		//放大镜小窗口显示的栏位名称
		String[] strDisplayFields = {"sCode","sName"};					//放大镜小窗口显示栏位对应的表格字段
		int intIndex = 0; 												//确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,
		String strMainProperty = " ";									//放大镜的对应控件栏位属性
		String strMatchValue="sCode";									////放大镜要模糊匹配的字段
		String strNextControls = "xyb";								////设置下一个焦点
		String strTitle="<font color=#FF0000>*</font> 借款单位";
		String strFirstTD=" ";
		String strSecondTD=" ";

		//调用产生放大镜的方法
		OBMagnifier.showZoomCtrl(out,strMagnifierName,strFormName,strPrefix,strMainNames,strMainFields,
			strReturnNames,strReturnFields, strReturnInitValues, strReturnValues,strDisplayNames,strDisplayFields,
			intIndex,strMainProperty,"getClient()", strMatchValue,strNextControls ,strTitle, strFirstTD, strSecondTD );
%>
   <TR>
     <TD colSpan=6 height=15> <HR></TD>
   </TR>
   <TR>
     <TD colSpan=3 height=41>&nbsp;</TD>
     <TD colSpan=2 height=41></TD>
     <TD align=right height=41 width="35%">
            <% if (action.equals("modify")) {%>
					<INPUT class=button name="xyb" onclick="confirmClose()" type="button" value=" 关 闭 "> 
			<%  	if (statusID==OBConstant.LoanInstrStatus.SAVE){ %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 完 成 "> 
			<%		}else if(statusID==OBConstant.LoanInstrStatus.SUBMIT) {    %>
					<INPUT class=button name="subnext"  onclick="confirmSave(frm)" type="button" value=" 修改并提交 "> 
			<%      }         %>
			<% }else { %>
	    <INPUT class=button name="xyb" onclick="confirmSave(frm)" type=button value=" 下一步 " onKeyDown="if(event.keyCode==13) confirmSave(frm);">            
			<% } %>
     </TD>
   </TR>
   </TABLE>
   </TD>
 </TR>
 </TABLE>

<input type=hidden name="lLoanType" value="<%=type%>">
<input type=hidden name="lLoanID" value="<%=loanID%>">
<input type="hidden" name="txtAction" value="<%=action%>">

</form>
<script language="javascript">
function confirmClose()
{
	window.close();
}
	function  confirmSave(frm)
	{
		if(frm.wtclientname.value == "")
		{
			alert("借款单位不能为空");
			frm.wtclientname.focus();
			return false;
		}
		if (!checkMagnifier("frm","wtClientID","wtclientname","借款单位"))
		{
			frm.wtclientname.focus();
		
			return false;
		}	
		if (frm.wtClientID.value==<%=clientID%>)
		{
			alert("借款单位不能是委托单位!");
			frm.wtclientname.focus();
			return false;
		}
		frm.action="l035-c.jsp"
		showSending();
		frm.submit();
		return true;
	}
function getClient()
{
	var sql = "SELECT id,nofficeID,sCode,sName,SLOANCARDNO,SLOANCARDPWD FROM client where nofficeID=<%=sessionMng.m_lOfficeID%> and nStatusID=<%=Constant.RecordStatus.VALID%> order by sCode";
	return sql ;
}
firstFocus(frm.wtclientname);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 	
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
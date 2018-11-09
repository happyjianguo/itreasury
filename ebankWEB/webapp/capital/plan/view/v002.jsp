<%--
 页面名称 ：v002.jsp
 页面功能 : 资金计划维护
 作    者 ：jiamiao
 日    期 ：2006-3-23
 特殊说明 ：实现操作说明：
 修改历史 ： 格式化金额 modify by wjliu --2007-3-31 
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.net.URI"%> 
<%@ page import="java.net.URLEncoder"%>

<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.obcapitalplan.dataentity.OBCapitalPlanInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
try
{
	String type = request.getParameter("type");
	String userid = request.getParameter("userid");
	String CheckuserID = request.getParameter("CheckuserID");
	System.out.println("-----------------------CheckuserID="+CheckuserID);
	System.out.println("-----------------------sessionMng.m_lUserID="+sessionMng.m_lUserID);
	System.out.println("-----------------------userid="+userid);
    String strTitle = ""; 
	if(type == null)
		strTitle = "用款计划维护";
	else if(type.equals("1"))
		strTitle = "用款计划复核";
	Log.print("*******进入页面--ias_hotdeploy_iTreasury-ebank/iTreasury-ebank/capital/plan/view/v002.jsp*******");
	
	//获得String类型的系统开机时间
	String startDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	OBCapitalPlanInfo info = new OBCapitalPlanInfo();
	String value = request.getParameter("value");
	if(value == null){
		if(type.equals("1"))
			info = (OBCapitalPlanInfo)request.getAttribute("info");
	}
	else if(value.equals("update")){
		strTitle = strTitle + "--修改";
		info = (OBCapitalPlanInfo)request.getAttribute("info");
		}
	else if(value.equals("add"))
		strTitle = strTitle + "--新增";
	
	java.math.BigDecimal bd = null;
	long id = info.getId();
	long sPeriod = info.getPeriod();//计划周期
	Timestamp sStartDate = info.getStartDate();//开始日期
	Timestamp sEndDate = info.getEndDate();//结束日期
	System.out.println("%%%%%%%%%%%%%%%%%"+sEndDate);
    double dPayAmount = info.getPayAmount();//支出金额
    bd = new java.math.BigDecimal(dPayAmount);
	
    String sPayAmount = bd.toString();
	sPayAmount = DataFormat.formatDisabledAmount(dPayAmount);
	double dReceivAmount = info.getReceivAmount();//收入金额
    bd = new java.math.BigDecimal(dReceivAmount);
    String sReceivAmount = bd.toString();
	sReceivAmount = DataFormat.formatDisabledAmount(dReceivAmount);
	
	long statusID = info.getStatusID();//状态ID
	Timestamp pStartDate = info.getPeriodStartDate();//周期开始日期
	System.out.println("状态ID="+id);
    /*
     * 校验客户端请求的有效性
     */
     //判断是否登录
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,strTitle,null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,strTitle,null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
	OBHtml.showOBHomeHead(out, sessionMng,strTitle, Constant.YesOrNo.YES);
%>

<jsp:include page="/ShowMessage.jsp"/>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />

<form name="formV002" method="post" onsubmit="return checkSubmit();">
<input type="hidden" name="id" value="<%=id%>">	
<input name="ActionID"  type="hidden" value="<%=new java.util.Date().getTime()%>">
  <table width="99%" border="0" cellspacing="0" cellpadding="0" class=top>
    <tr>
      <td align="center">  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
	<tr bgcolor="#FFFFFF"> 
		<td colspan="4" height="1"></td>
	</tr>
	<tr class="tableHeader">                
		<td colspan="4"height="22" class="FormTitle">
			<font size="3" color="#FFFFFF" ><b><%=strTitle%></b></font>
		</td>                
	</tr>
	<tr bgcolor="#FFFFFF"> 
		<td colspan="4" height="1"></td>
	</tr>
  </table>
  <table width="100%" border="0"  cellspacing="0" cellpadding="0">
        <tr class="MsoNormal">
          <%if(type == null && statusID != 2){
              String strMagnifierNamePeriod = URLEncoder.encode("周期计划");
              String strFormNamePeriod = "formV002";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"periodtxt","startDatetxt","idtxt"};
              String[] strMainFieldsPeriod= {"period","startdate","id"};
              String[] strReturnNamesPeriod = {"periodid"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= new Long(sPeriod).toString();
              if(strReturnInitValuesPeriod.equals("-1"))
            	  strReturnInitValuesPeriod = new String("");
              String[] strReturnValuesPeriod = {"-1"};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("周期计划"),URLEncoder.encode("周期名称")};
              String[] strDisplayFieldsPeriod = {"period","periodname"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              String strSQLPeriod = "getPeriodSetting()";
              String strMatchValuePeriod = "";
              String strNextControlsPeriod = "startDate";
              String strTitlePeriod = "&nbsp;&nbsp;&nbsp;计划周期";
              String strFirstTDPeriod = "";
              String strSecondTDPeriod= "";

              OBMagnifier.showZoomCtrl(out
              ,strMagnifierNamePeriod
              ,strFormNamePeriod
              ,strPrefixPeriod
              ,strMainNamesPeriod
              ,strMainFieldsPeriod
              ,strReturnNamesPeriod
              ,strReturnFieldsPeriod
              ,strReturnInitValuesPeriod
              ,strReturnValuesPeriod
              ,strDisplayNamesPeriod
              ,strDisplayFieldsPeriod
              ,nIndexPeriod
              ,strMainPropertyPeriod
              ,strSQLPeriod
              ,strMatchValuePeriod
              ,strNextControlsPeriod
              ,strTitlePeriod
              ,strFirstTDPeriod
              ,strSecondTDPeriod);
%>           <input type="hidden" name="idtxt" >



         <% }else {%>
          <td width="15%" height="4">&nbsp;&nbsp;&nbsp;计划周期：
          	<a href=#>
          		<img name="button" src='/webob/graphics/icon.gif' border=0>
          	</a>
          </td>
          <td width="35%">
          <input type="text" name="periodtxt" value="<%=sPeriod %>" class="box" disabled>
          <%} %>
          </td>
          <td width="15%">&nbsp;&nbsp;&nbsp;周期开始日期：</td>
          <td width="35%">
          <input type="text" class="box" name="startDatetxt" value="<%if(pStartDate != null){%><%=pStartDate.toString().substring(0,10) %><%} %>" disabled>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4">&nbsp;</td>
          </tr>
        <tr class="MsoNormal">
          <td height="4" colspan="4"><hr></td>
          </tr>
        <tr>
          <td height="8">&nbsp;&nbsp;&nbsp;用款开始日期：</td>
          <td><%if(value == null){ 
          			if(type == null){%>
          		<fs_c:calendar 
	          	    name="startDate"
		          	value="" 
		          	properties="nextfield='endDate';" 
		          	size="20"/>
		          	<script>
		          		$('#startDate').val('<%=sStartDate.toString().substring(0,10)%>');
		          		$('#startDate').attr('disabled','true');
		          	</script>
          			<%}else{ %>
            	<input class=box name="startDate"  value="<%=sStartDate.toString().substring(0,10)%>"  onFocus="nextfield ='endDate';" disabled >
            <%}
            }else { 
            	if(type == null){%>
          		<fs_c:calendar 
	          	    name="startDate"
		          	value="" 
		          	properties="nextfield='endDate';" 
		          	size="20"/>
				<script>
	          		$('#startDate').val('<%if(value.equals("update")){%><%=sStartDate.toString().substring(0,10)%><%}else if(value.equals("add")){%><%=""%><%} %>');
	          	</script>		          	
			          	<%if(statusID == 2) {%>
			          	<script>
			          		$('#startDate').attr('disabled','true');
			          	</script>
			          	<%} %>
          			<%}else{ %>
          		<input class=box name="startDate"  value="<%if(value.equals("update")){%><%=sStartDate.toString().substring(0,10)%><%}else if(value.equals("add")){%><%=""%><%} %>"  onFocus="nextfield ='endDate';" <%if(statusID == 2) {%>disabled<%} %> >
            <%}}
         //    String _href = "";
         //    if(type == null){ 
	      //       if(statusID == 1 || statusID == -1) 
	             {
	  //          	 _href = "javascript:show_calendar('formV002.startDate');";
	   //          }
	 //    //        else if(statusID == 2)
	  //           {
	  //          	 _href = "#";
	             }
     //        }else 
     //       	 _href = "#";
     //        %>
  <!--           <a href=<//%=_href %> 
              <%
       //       if(type == null){ if(statusID == 1 || statusID == -1) 
           //   { %>
            onMouseOut="window.status='';return true;" 
            onMouseOver="window.status='Date Picker';return true;"<%//}}%>> <img border=0 height=16 src="/webob/graphics/calendar.gif" width=17></a> --> 
          </td>
			<td height="8">&nbsp;&nbsp;&nbsp;用款结束日期：</td>
          <td><%if(value == null){ 
          			if(type == null){%>
		          		<fs_c:calendar 
			          	    name="endDate"
				          	value="" 
				          	properties="nextfield='PayAmount';" 
				          	size="20"/>
				          	<script>
             	          		$('#endDate').val('<%=sEndDate.toString().substring(0,10)%>');
				          		$('#endDate').attr('disabled','true');
				          	</script>
          			<%}else{ %>
            			<input class=box name="endDate"  value="<%=sEndDate.toString().substring(0,10)%>"  onFocus=";nextfield ='PayAmount';" disabled >
            <%		  }
          		}else { 
          		if(type == null){%>
          		<fs_c:calendar 
	          	    name="startDate"
		          	value="" 
		          	properties="nextfield='endDate';" 
		          	size="20"/>
		         <script>
	          		$('#startDate').val('<%if(value.equals("update")){%><%=sEndDate.toString().substring(0,10)%><%}else if(value.equals("add")){%><%=""%><%} %>');
	          	</script>
			          	<%if(statusID == 2) {%>
			          	<script>
			          		$('#endDate').attr('disabled','true');
			          	</script>
			          	<%} %>
          			<%}else{ %>
          				<input class=box name="endDate"  value="<%if(value.equals("update")){%><%=sEndDate.toString().substring(0,10)%><%}else if(value.equals("add")){%><%=""%><%} %>"  onFocus="nextfield ='PayAmount';" <%if(statusID == 2) {%>disabled<%} %> >
            <%		  }
          		}
            // String _href1 = "";
       // if(type == null){ 
	 //            if(statusID == 1 || statusID == -1) 
	  //           {
	 //           	 _href1 = "javascript:show_calendar('formV002.endDate');";
	   //          }
	 ///            else if(statusID == 2)
	             {
	   //         	 _href1 = "#";
	 //            }
     //        }else 
     //       	 _href1 = "#";
     //        %>
            <a href=<%=//_href1 %> 
              <%
     //         if(type == null){ if(statusID == 1 || statusID == -1) 
    //          { %>
    <!-- 
            onMouseOut="window.status='';return true;" 
            onMouseOver="window.status='Date Picker';return true;"<%//}}%>> <img border=0 height=16 src="/webob/graphics/calendar.gif" width=17></a> 
         --></td> 
        </tr>
        <tr>
          <td height="3">&nbsp;</td>
          <td>&nbsp;</td>
          <td height="3">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="4">&nbsp;&nbsp;&nbsp;收入金额：</td>
          <td><%
          if(type == null){ 
          	if(statusID == 1 || statusID == -1){%>
            <script  language="JavaScript">
				createAmountCtrl("formV002","ReceivAmount","<%if(value.equals("update")){%><%=sReceivAmount%><%}%>","save","","<%=sessionMng.m_lCurrencyID%>");
			</script>
            <%}else if(statusID == 2){%>
            <input type="text" name="ReceivAmount" value="<%=sReceivAmount %>" size="18" class=tar disabled>
            <%}
          	}else{%>
            <input type="text" name="ReceivAmount" value="<%=sReceivAmount %>" size="18" class=tar disabled>
            <%} %></td>
           <td height="8">&nbsp;&nbsp;&nbsp;支出金额：</td>
          <td><%
          if(type == null){
        	  if(statusID == 1 || statusID == -1){%>
            <script  language="JavaScript">
				createAmountCtrl("formV002","PayAmount","<%if(value.equals("update")){%><%=sPayAmount%><%}%>","ReceiveAmount","","<%=sessionMng.m_lCurrencyID%>");
		    </script>
            <%}else if(statusID == 2){%>
            <input type="text" name="PayAmount" value="<%=sPayAmount %>" size="18" class=tar disabled>
            <%}
          }else{%>
            <input type="text" name="PayAmount" value="<%=sPayAmount %>" size="18" class=tar disabled>
            <%} %>
          </td>
        </tr>
        <tr>
          <td height="15" colspan="2"></td>
          <td colspan="2"></td>
        </tr>
      </table>
	  <br>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr align="right">
            <td width="20%">&nbsp;</td>
            <td></td>
            <td width="20%">&nbsp;&nbsp;&nbsp;
            <%if(type == null) {%>
                <input name="save" type="button" class="button" value="保 存" onClick="javascript:dosave();" <%if(statusID == 2){%>disabled<%} %>>
                <%if(value.equals("update") && statusID != 2){%>
                &nbsp;&nbsp;<input name="del" type="button" class="button" value="删 除" onClick="javascript:dodel();">
                <%}
                }
                else if(type.equals("1")){
                	if(info.getStatusID() == 1){
                %>
                	<input name="lcheck" type="button" class="button" value="复 核" onClick="javascript:docheck(1);" <%if(sessionMng.m_lUserID == new Long(userid).longValue()){%>disabled<%} %>>
                <%}else if(info.getStatusID() == 2){%>
                	<input name="lcancelcheck" type="button" class="button" value="取消复核" onClick="javascript:docheck(2);" <%if(sessionMng.m_lUserID != new Long(CheckuserID).longValue()){%>disabled<%} %>>
                	<%}
                	}%>
                &nbsp;&nbsp;<input name="back" type="button" class="button" value="返 回" onClick="javascript:doback();">&nbsp;&nbsp;
            </td>
          </tr>
        </table>
		 <br></td>
    </tr>
  </table>
 
  </form>

<script language="JavaScript">
<%if(value != null && statusID != 2){%>
firstFocus(document.formV002.periodtxt);
<%}%>
////setSubmitFunction("doSearch()");
setFormName("formV002"); 
	function dodel()
	{
		if(confirm("是否删除?"))
		{
			formV002.action="../control/c004.jsp";
			formV002.submit();
		}
	}
		    
	var checkSubmitFlg = false;
    function checkSubmit()
	{
      if (checkSubmitFlg == true)
	  {
         return false;
      }
      checkSubmitFlg = true;
      return true;
   }
   document.ondblclick = 
   function docondblclick()
   {
    window.event.returnValue = false;
   }
   document.onclick =
   function doconclick()
   {
       if (checkSubmitFlg)
	   {
         window.event.returnValue = false;
       }
   }
	function docheck(s)
	{		
		if(s == 1)
		{
			if(confirm("是否复核?"))
			{
				formV002.action="../control/c014.jsp?type=check&id=<%=id%>";
				formV002.submit();
			}	
		}else if(s == 2)
		{			
			if(confirm("是否取消复核?"))
			{
				formV002.action="../control/c014.jsp?type=cancel&id=<%=id%>";
				formV002.submit();
			}	
		}
	}
	function doback()
	{	
	<%if(type == null){%>
		formV002.action="../control/c001.jsp";
	<%}else if(type.equals("1")){%>
		formV002.action="../control/c001.jsp?type=1";
	<%}%>
		formV002.submit();
	}
	
	function dosave()
	{	
		<%if(value != null)
		{
			if(value.equals("update"))
			{%>
				formV002.periodid.value=1;
		  <%}
	  	}%>
		if(formV002.periodid.value < 0)
        {
            alert('请使用放大镜输入周期！');
            formV002.periodtxt.focus();
            return false;
        }
		
		if(formV002.startDate.value=="")
		{
			alert("请选择开始日期!");
			return false;
		}
		
		if(!getTrueDate(formV002.startDate,"用款开始日期"))
			return false;
		
		if(!getTrueDate(formV002.endDate,"用款结束日期"))
			return false;
		
		if(!isTrueDate())
		{
			alert("用款开始日期不得大于、等于结束日期!");		
			return false;
		}
		
		if(!isDigit(formV002.PayAmount.value))	
		{	
			alert("支出金额必须为数字!");
			formV002.PayAmount.focus();			
			return false;
		}
		if(!isDigit(formV002.ReceivAmount.value))	
		{		
			alert("收入金额必须为数字!");		
			formV002.ReceivAmount.focus();	
			return false;
		}
		formV002.startDatetxt.disabled=false;
		formV002.action="../control/c002.jsp?value=<%=value%>";
		formV002.submit();
	}
	
	function isTrueDate()
	{		
		if
		(	
			new Date
			(
				formV002.startDate.value.substring(0,4)
				,new Number(formV002.startDate.value.substring(5,7)) - 1
				,new Number(formV002.startDate.value.substring(8,10))
			)
		    - 
			new Date
			(
				formV002.endDate.value.substring(0,4)
				,new Number(formV002.endDate.value.substring(5,7)) - 1
			    ,new Number(formV002.endDate.value.substring(8,10))
			)
			>=0
		)
			return false;
		return true;
	}
	
	function isDigit(s)
	{
		if(s=="")
			return true;
		else
		{
			var wordArray = s.split(",");
			var str="";
			var i = 0;
			for(;i<wordArray.length;i++)
			{
				str = str + wordArray[i];
			}
			var patrn=/\d+\.\d{2}/;
			if (!patrn.exec(str))
				return false;
			return true;
		}
	}
	
	function getTrueDate(strobject,str)
	{
	
		var strStartDate = strobject.value;
		var periodStartDate = formV002.startDatetxt.value;
		var periodDays = formV002.periodtxt.value;
		if(!validate(strStartDate,periodStartDate,periodDays))
		{
		    alert(str+"应为周期开始日期+周期天数的整数倍！");
			strobject.value="";
			strobject.focus();
			return false;
		}
		return true;
	}
	
	function validate(startdate,pstartdate,diffdays)
	{
		var startDate = new Date(startdate.substring(0,4),new Number(startdate.substring(5,7)) - 1,new Number(startdate.substring(8,10)));
		var pstartDate=new Date(pstartdate.substring(0,4),new Number(pstartdate.substring(5,7)) - 1,new Number(pstartdate.substring(8,10)));
		var diff=startDate-pstartDate;
		var strdiff=Number(diffdays)*1000*60*60*24;
		var last=Number(diff)%Number(strdiff);
		if(last!=0)
			return false;
		return true;
	}
</script>
<%				
	    }
		catch( Exception exp )
		{
			Log.print(exp.getMessage());
		}
		OBHtml.showOBHomeEnd(out);
%>
<%@ include file="/common/SignValidate.inc" %>
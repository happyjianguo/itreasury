<!-- 
/**页面功能说明
 * 页面名称 ：v001.jsp
 * 页面功能 : 业务日志查询页面
 * 作    者 ：li liang
 * 日    期 ：2009-5-25
 */ 
 -->
<jsp:directive.page import="com.iss.itreasury.util.Env"/>

<!--类导入部分开始-->
<%@ page contentType = "text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="com.iss.itreasury.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.system.translog.dataentity.QueryTransLogInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<% String strContext = request.getContextPath();%>
<!--类导入部分结束-->
<head><link rel="stylesheet" href="/webob/css/style.css" type="text/css"></head>
<%
	
    try
	{
		String strTitle = null;
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
        
        String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
        
        String ordertype = (String)request.getAttribute("ordertype");
		/**页面校验开始(用户登录校验、用户权限校验、重复请求校验)**/
		JSPLogger.info("****************进入页面--/ebank/webapp/translog/view/v001.jsp");
		/**页面校验结束**/
		
		/**业务逻辑开始**/
        long userid = -1;
        String username = ""; 
        String startDate = Env.getSystemDateString(); 	//操作日期-从
        String endDate = Env.getSystemDateString();  	//操作日期-到
        String startTimeHour = ""; 					//操作时间-从-时
        String startTimeMinute = "";					//操作时间-从-分
        String startTimeSecond = "";					//操作时间-从-秒
        String endTimeHour = ""; 						//操作时间-到-时
        String endTimeMinute = "";					//操作时间-到-分
        String endTimeSecond = "";					//操作时间-到-秒
        long logType = 0;								//日志类型
        long status = 2;								//操作状态
		
		QueryTransLogInfo queryTransLogInfo = null;
		QueryTransLogInfo[] queryResults = (QueryTransLogInfo[])request.getAttribute(Constant.PageControl.SearchResults);
		QueryTransLogInfo queryCondition = (QueryTransLogInfo)sessionMng.getQueryCondition(strPageLoaderKey);
		Log.print("queryResults :  " + queryResults);
		
		if(queryCondition!=null)
		{
			userid = Long.parseLong(queryCondition.getQueryuserid());
			username = queryCondition.getUsername();
			startDate = queryCondition.getStartdate();
			endDate = queryCondition.getEnddate();
			startTimeHour = queryCondition.getStartTimeHour();
			startTimeMinute = queryCondition.getStartTimeMinute();
			startTimeSecond = queryCondition.getStartTimeSecond();
			endTimeHour = queryCondition.getEndTimeHour();
			endTimeMinute = queryCondition.getEndTimeMinute();
			endTimeSecond = queryCondition.getEndTimeSecond();
			logType = Long.parseLong(queryCondition.getQuerylogtype());
			status = Long.parseLong(queryCondition.getQuerystatus());
		}
		
		String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
		String strFailPageURL = (String)request.getAttribute("strFailPageURL");
		/**业务逻辑结束**/
		
		/**页面显示开始*/
   		OBHtml.showOBHomeHead(out, sessionMng, "", 1);
%>

<jsp:include page="/ShowMessage.jsp"/>

<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webbp/js/Check.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<!--引入js文件-->
<table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td>
<!--页面表单开始-->
<form name="form001" method="post" action="">
<!--页面控制元素开始-->
	<input name="ActionID" type="hidden" value="<%=new java.util.Date().getTime()%>">
	<input name="p_Action" type="hidden" value="findFirst">
	<input type="hidden" name="strSuccessPageURL" value="<%=strSuccessPageURL%>">
	<input type="hidden" name="strFailPageURL" value="<%=strFailPageURL %>">
	<input type="hidden" name="_pageLoaderKey" value="<%=strPageLoaderKey %>">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="ordertype" value="<%=ordertype==null?"asc":ordertype%>">
	<input type="hidden" name="orderfield" value="">
	<input name="systemDate" type="hidden" value="<%=Env.getSystemDateString()%>">
	<input name="isCheck" type="hidden" value="1"/>
	<input name="isDirectLink" type="hidden" value="1"/>		
	<input name="accountStatus" type="hidden" value="1"/>	
<!--页面控制元素结束-->
<table cellpadding="0" cellspacing="0" class="title_top" >
  	<tr>
  		<td height="24" valign="top">
	  		<table cellspacing="0" cellpadding="0" class=title_Top1>
				<tr>
			       <td class=title><span class="txt_til2">日志查询</span></td>
			       <td class=title_right><a class=img_title></td>
				</tr> 		
			</TABLE>
<br/>

 <table width="100%" border="0"  cellpadding="0" cellspacing="1" class=normal id="table1">		
  		<TBODY >
  		<TR>
    		<TD height=63 align="left">      
      			<TABLE align="left" border="0" height=80 width="100%" >
        			<TBODY> 
        			<%
        				if(sessionMng.m_lSAID==sessionMng.m_lUserID)
        				{
        				%>
        				
        				<TR>
							<%
				              String strMagnifierName  = "操作人选择放大镜";
				              String strFormName  = "form001";
				              String strPrefix = "";
				              String[] strMainNames = {"username"};
				              String[] strMainFields= {"sname"};
				              String[] strReturnNames = {"hiduserid"};
				              String[] strReturnFields = {"id"};
				              String strReturnInitValues= "";
				              if(!username.equals(""))
							  {
							   	strReturnInitValues = username;
							  }
							  String[] strReturnValues = {""+userid+""};
				              String[] strDisplayNames = {"用户id","用户名","登录名","客户名称"};
				              String[] strDisplayFields = {"id","sname","sloginno","name"};
				              int nIndex = 0;
				              String strMainProperty = "maxlength=20";
				              String strSQL = "getOperationUser("+sessionMng.m_lOfficeID+","+sessionMng.m_lCurrencyID+","+Constant.RecordStatus.VALID+","+sessionMng.m_lUserID+","+sessionMng.m_lClientID+")";			              
				              String strMatchValue = "sname";
				              String strNextControls = "startDate";
				              String strTitle1 = "操作人";
				              String strFirstTD = "width=10% nowrap ";
				              String strSecondTD= "width=10% nowrap colspan=3 ";
				
				              OBMagnifier.showZoomCtrl(out
				              ,strMagnifierName
				              ,strFormName
				              ,strPrefix
				              ,strMainNames
				              ,strMainFields
				              ,strReturnNames
				              ,strReturnFields
				              ,strReturnInitValues
				              ,strReturnValues
				              ,strDisplayNames
				              ,strDisplayFields
				              ,nIndex
				              ,strMainProperty
				              ,strSQL
				              ,strMatchValue
				              ,strNextControls
				              ,strTitle1
				              ,strFirstTD
				              ,strSecondTD);
								%>
			    				<TD borderColor=#E8E8E8 ></TD>
            					<TD borderColor=#E8E8E8 ></TD>
						</TR>			   		
        				<%
        				}
        				else
        				{
        			 %>
        				 <TR>
							<td width="5%" align="left">操作人：</td>
							<td width="1%"></td>
							<td width="10%" colspan="3" align="left"><input class=box name="username" value="<%=sessionMng.m_strUserName%>" readonly ></td>
			    			<TD borderColor=#E8E8E8 ><input name="hiduserid" type="hidden" value="<%=sessionMng.m_lUserID %>"></TD>
            				<TD borderColor=#E8E8E8 ></TD>
						</TR>	
        			 <%
        			 }
        			  %>
        			
        								 
						 <tr>
					          <td width="5%" align="left">操作日期：</td>
					          <td width="1%">由</td>
					          <td width="10%" height="25" class="graytext" colspan="3" align="left">
					          <fs_c:calendar 
				         	    name="startDate"
					          	value="" 
					          	properties="nextfield ='endDate'" 
					          	size="12"/>
					          	 <script>
	          		$('#startDate').val('<%=startDate%>');
	          	</script>
					            </td>
					          <td width="10%" height="25" class="graytext" align="right">
					            <span class="graytext">至</span>
					            </td>
					          <td width="10%" height="25" class="graytext" colspan="3">
					           <fs_c:calendar 
				         	    name="endDate"
					          	value="" 
					          	properties="nextfield ='startTimeHour'" 
					          	size="12"/>
					         <script>
	          		$('#endDate').val('<%=endDate%>');
	          	</script>
					           </td>
					          <td width="10%"></td>
					        </tr>
					        <tr id="submitDate">
					          	<td width="5%" align="left">操作时间：</td>
					          	<td width="1%">由</td>
					          	<td width="2%" height="25" class="graytext" nowrap align="left">
					          		<input onkeyup="movetime(this,startTimeMinute)" type="text" name="startTimeHour" class="box" value="<%=startTimeHour%>" 
					                	onfocus="nextfield ='startTimeMinute';" size="2" maxlength="2" onkeypress="checknum()" >&nbsp;时
					          	</td>
					          	<td width="4%" height="25" class="graytext" nowrap>
					            	<input onkeyup="movetime(this,startTimeSecond);backup(this,startTimeHour)"  type="text" name="startTimeMinute" class="box" value="<%=startTimeMinute%>" 
					                onfocus="nextfield ='startTimeSecond';" size="2" maxlength="2" onkeypress="checknum()">&nbsp;分
					            </td>
					            <td width="4%" height="25" class="graytext" nowrap>
					            	<input onkeyup="movetime(this,endTimeHour);backup(this,startTimeMinute)" type="text" name="startTimeSecond" class="box" value="<%=startTimeSecond%>" 
					                	onfocus="nextfield ='endTimeHour';" size="2" maxlength="2" onkeypress="checknum()">&nbsp;秒
					            </td>
					          	<td width="2%" height="25" class="graytext" align="right">
					            	<span class="graytext">至</span>
					            </td>
					            <td width="4%" height="25" class="graytext" nowrap>
					            	<input onkeyup="movetime(this,endTimeMinute);backup(this,startTimeSecond)" type="text" name="endTimeHour" class="box" value="<%=endTimeHour%>" 
					                	onfocus="nextfield ='endTimeMinute';" size="2" maxlength="2" onkeypress="checknum()">&nbsp;时
					            </td>
					            <td width="4%" height="25" class="graytext" nowrap>
					            	<input onkeyup="movetime(this,endTimeSecond);backup(this,endTimeHour)"  type="text" name="endTimeMinute" class="box" value="<%=endTimeMinute%>" 
					                	onfocus="nextfield ='endTimeSecond';" size="2" maxlength="2" onkeypress="checknum()">&nbsp;分
					            </td>
					            <td width="4%" height="25" class="graytext" nowrap>
					            	<input onkeyup="backup(this,endTimeMinute)" type="text" name="endTimeSecond" class="box" value="<%=endTimeSecond%>" 
					                	onfocus="nextfield ='trasLogType';" size="2" maxlength="2" onkeypress="checknum()">&nbsp;秒
					            </td>
					          	<td width="11"></td>
					        </tr>
						 	<tr>
						 		<td width="5%" align="left">日志类型：</td>
						 		<td width="1%"></td>
						 		<td colspan=3 align="left"><%Constant.SystemLogType.showList(out,"trasLogType", 0, logType, false, false, "日志类型");%></td>
						 	</tr>
						 	<tr>
						 		<td width="5%" align="left">操作结果：</td>
						 		<td width="1%"></td>
						 		<td colspan=3 align="left"><%Constant.LogActionResult.showList(out,"actionResult", 0, status, false, false, "操作结果");%></td>
						 	</tr>
         				  		  
	  				  </TBODY>
	              </TABLE>
   			  </TD>
          </TR> 
		
        <TR >
            <TD colspan="3" align=right> 
              <input  class=button1 type="button" name="butSearch" value=" 查 找 " onClick="doSearch();" onfocus="nextfield='submitfunction';">
            </TD>
			<TD align=right>&nbsp;</TD>
        </TR>
		<TR >
            <TD colspan="4" align=right height="5">&nbsp;</TD>
        </TR>
		
		
		
<tr><!-- 查询结果列表 -->
	<td colspan="5">
		<HR>
      	<table width="100%" border="1" align="center" class=normal>
			<thead>
			<tr align="center">
		    	<td nowrap height="20"><DIV align=center><A href="javascript:go('USERNAME');">操作用户</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('FUNCTIONPOINTDESCRIPTION');">菜单</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('MAININFO');">主要信息</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('STATUS');">状态</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('ACCESSTIME');">日期</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('TIME');">时间</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('CLIENTIP');">访问IP</A></DIV></td>
		        <td nowrap height="20"><DIV align=center><A href="javascript:go('CURRENCYID');">币种</A></DIV></td>
	        </tr>
	        </thead>
 <%
				if (queryResults != null && queryResults.length > 0) 
				{
					queryTransLogInfo = new QueryTransLogInfo();
					for( int i=0; queryResults != null && i < queryResults.length; i++ )
					{
						queryTransLogInfo = (QueryTransLogInfo)queryResults[i];
						//获取元素-操作时间
						String datetime = queryTransLogInfo.getAccesstime().toString();
						//用“空格”分离原为Timestamp格式的字符串，index为标记
						int index = datetime.indexOf(" ");
						String date = datetime.substring(0,index);
						String time = datetime.substring(index,datetime.length()-2);
						System.out.println(" time"+time);
%>
			<tr align="center" bgColor=#cccccc bordercolor="#999999"  class="ItemBody"> 
				<td height="20" nowrap><DIV align=center><%=queryTransLogInfo.getUsername()%></DIV></td>
				<td height="20" ><DIV align=center><%=queryTransLogInfo.getFunctionpointdescription()%></DIV></td>
				<td height="20" ><DIV align=center><%=queryTransLogInfo.getMaininfo()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=Constant.LogActionResult.getName(queryTransLogInfo.getStatus())%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=date%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=time%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=queryTransLogInfo.getClientip()%></DIV></td>
				<td height="20" nowrap><DIV align=center><%=NameRef.getCurrencyNameByID(queryTransLogInfo.getCurrencyid())%></DIV></td>
			</tr>
<%  
					}
				}
				else
				{
%>
					<tr bordercolor="#999999" align="center" class="ItemBody"> 
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
			          <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					  <td height="20" class="ItemBody"><DIV align=center>&nbsp;</DIV></td>
					</tr>
<% 
				} 
%>
		</table>
	</td> 
</tr>		
		 
	</TBODY>
</TABLE>
</td></tr></table></form>
</td></tr>
</table>
<table width="80%">
<!-- 分页控件 -->
	<TR>
		<td width="100%" align="right">
			<table width=100% cellspacing="3" cellpadding="0" class="SearchBar" height="19" >
				  <TR>
			           <TD height=20 width=100% align="right">
			             <DIV align=right>
						    <jsp:include page="/pagenavigator.jsp"  />
						 </DIV>
					   </TD>
				  </TR>
		     </table> 
	     </td>
     </TR> 
</table>
	
<form name="listReport" method="post" >
	<input name="strAction"  type="hidden">
	<input name="strSuccessPageURL"  type="hidden" >
	<input name="strFailPageURL"  type="hidden" >
	<input name="_pageLoaderKey"  type="hidden" value="<%=strPageLoaderKey%>">
</form>
<table width="80%"  border="0">
	<tr align="right">
		<td width=80%></td>
		<td><input class=button1 type=button name="btnDownload" value=" 下载查询结果 " onClick="doDownload();" onfocus="nextfield='submitfunction';"></td>
		<td><input class=button1 type=button name="btnPrint" value=" 打 印 " onClick="doPrint();" onfocus="nextfield='submitfunction';"></td>
	</tr>
</table>
<!--页面表单结束-->

<!--页面脚本开始-->
<script language="JavaScript">
	firstFocus(document.form001.username);
	//setSubmitFunction("doSearch()");
	setFormName("form001");
</script>

<script language="javascript">
function doSearch()
{
	allFields();
	if (!validateFields(form001)) return;
	
	if( !(compareDate(form001.startDate.value,form001.endDate.value)) )
	{
		alert("“操作日期-由”不能大于“操作日期-至！”");
		return false;
	}
	var startTimeHour = Trim(form001.startTimeHour.value);
	var startTimeMinute = Trim(form001.startTimeMinute.value);
	var startTimeSecond = Trim(form001.startTimeSecond.value);
	var endTimeHour = Trim(form001.endTimeHour.value);
	var endTimeMinute = Trim(form001.endTimeMinute.value);
	var endTimeSecond = Trim(form001.endTimeSecond.value);
	
	if(startTimeHour!=""||startTimeMinute!=""||startTimeSecond!="")
	{
		if(startTimeHour == "")
		{
			alert("操作时间-由-时 为空！");
			return;
		}
		if(startTimeMinute == "")
		{
			alert("操作时间-由-分 为空！");
			return;
		}
		if(startTimeSecond == "")
		{
			alert("操作时间-由-秒 为空！");
			return;
		}
	}
	if(endTimeHour!=""||endTimeMinute!=""||endTimeSecond!="")
	{
		if(endTimeHour == "")
		{
			alert("操作时间-至-时 为空！");
			return;
		}
		if(endTimeMinute == "")
		{
			alert("操作时间-至-分 为空！");
			return;
		}
		if(endTimeSecond == "")
		{
			alert("操作时间-至-秒 为空！");
			return;
		}
	}
	if(form001.startTimeHour.value>23||form001.startTimeHour.value<0||form001.startTimeHour.value.length>2)
	{
		alert("“操作时间-由-时”填写有误！");
		return;
	}
	if(form001.startTimeMinute.value>59||form001.startTimeMinute.value<0||form001.startTimeMinute.value.length>2)
	{
		alert("“操作时间-由-分”填写有误！");
		return;
	}
	if(form001.startTimeSecond.value>59||form001.startTimeSecond.value<0||form001.startTimeSecond.value.length>2)
	{
		alert("“操作时间-由-秒”填写有误！");
		return;
	}
	if(form001.endTimeHour.value>23||form001.endTimeHour.value<0||form001.endTimeHour.value.length>2)
	{
		alert("“操作时间-至-时”填写有误！");
		return;
	}
	if(form001.endTimeMinute.value>59||form001.endTimeMinute.value<0||form001.endTimeMinute.value.length>2)
	{
		alert("“操作时间-至-分”填写有误！");
		return;
	}
	if(form001.endTimeSecond.value>59||form001.endTimeSecond.value<0||form001.endTimeSecond.value.length>2)
	{
		alert("“操作时间-至-秒”填写有误！");
		return;
	}
	
	form001.strAction.value="search";
	form001.action = "<%=strContext%>/translog/control/c001.jsp?officeId="+$('#agencyName', window.parent.document).val();
	form001.strSuccessPageURL.value="<%=strContext%>/translog/view/v001.jsp";
	form001.strFailPageURL.value="<%=strContext%>/translog/view/v001.jsp";
	showSending();
	form001.submit();
}    
/**
 * 所有需要校验的栏位
 */
function allFields()
{
	this.ai = new Array("startDate","操作日期-由","date",1);
	this.aj = new Array("endDate","操作日期-至","date",1);
	this.ak = new Array("startTimeHour","操作时间-由-时","int",0)
	this.al = new Array("startTimeMinute","操作时间-由-分","int",0)
	this.am = new Array("startTimeSecond","操作时间-由-秒","int",0)
	this.an = new Array("endTimeHour","操作时间-至-时","int",0)
	this.ao = new Array("endTimeMinute","操作时间-至-分","int",0)
	this.ap = new Array("endTimeSecond","操作时间-至-秒","int",0)
}

function go(field)
{
	if(form001._pageLoaderKey.value!="null")
	{
		form001.strAction.value="order";
		form001.action = "<%=strContext%>/translog/control/c001.jsp";
		alert(form001.action );
		form001.orderfield.value=field;
		showSending();//正在执行
		form001.submit();
	}
}

function doDownload()
{
    listReport.action = '<%=strContext%>'+"/pagecontrol.jsp";
	listReport.target='blank_';
	listReport.strAction.value='<%=Constant.PageControl.LISTALL%>';
	listReport.strSuccessPageURL.value = '<%=strContext%>/translog/view/v001-e.jsp';
	listReport.strFailPageURL.value = '<%=strContext%>/translog/view/v001-e.jsp';
	listReport.submit();
}

function doPrint() 
{
	listReport.action = '<%=strContext%>'+"/pagecontrol.jsp";
	listReport.target='blank_';
	listReport.strAction.value='<%=Constant.PageControl.LISTALL%>';
	listReport.strSuccessPageURL.value = '<%=strContext%>/translog/view/v001-p.jsp';
	listReport.strFailPageURL.value = '<%=strContext%>/translog/view/v001-p.jsp';
	listReport.submit();
}

function checknum()
{
	if(event.keyCode>57||event.keyCode<48)
		{
			event.keyCode=0;
		}
}

	function movetime(strname,filed)
	{
		if(strname.value.length>=2)
		{
			filed.focus();
		}
	}
	function backup(strname,filed)
	{
		if(event.keyCode==8&&strname.value.length==0)
		{
			filed.focus();
			 var r=filed.createTextRange();  
 			 r.moveStart('character',filed.value.length);  
 			 r.collapse(true);  
  			 r.select();  
			
		}
	}

	
</script>
<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾
	OBHtml.showOBHomeEnd(out);
	/**页面显示结束*/
%>
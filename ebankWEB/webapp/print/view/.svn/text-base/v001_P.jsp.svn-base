<%--
 页面名称 ：v001_P.jsp
 页面功能 : 电子回单打印
 作    者 ：xubo
 日    期 ：2006-12-12
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
				 java.net.URLEncoder,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
                 com.iss.itreasury.ebank.util.*"
%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="common"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[电子回单打印]";
%>

<% String strContext = request.getContextPath();%>
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

	   //显示文件头
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
      
      long lTransactionStatusID = 3;
      //计算开机日之前的一天
      String queryDate = "";
      Timestamp systemDate = Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(systemDate);
      calendar.add(Calendar.DAY_OF_MONTH, -1);
      SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
      queryDate=dateformat.format(calendar.getTime());
      
%>		

<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>
<script language="JavaScript" src="/itreasury/js/jquery-1.3.2.js"></script>

<form name="frm" method="post">
<input name="strSuccessPageURL" type="hidden" >
<input name="strFailPageURL" type="hidden" >
<input type="hidden" name="strAction" >
<input type="hidden" name="lTransactionStatusID" value="<%= lTransactionStatusID %>"><!--记录状态 3 已复核-->
<input type="hidden" name="hideOpenDate" value="">
<input type="hidden" name="currencyId" value="<%=sessionMng.m_lCurrencyID %>">

<table cellpadding="0" cellspacing="0" class="title_top" >
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">单据打印</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
			<table width=100% border="0" cellspacing="0" cellpadding="0" class=normal >
				  <tr>
				  	<td>
				  		<table align=center border=0 width="97%">
				  			<tr>
				  				<TD width="13%">&nbsp;机构：</td>
								<td>
<%
									OBHtmlCom.showOfficeListControl(out,"officeList",sessionMng.m_lOfficeID,"onchange=\"showExcuteDate(this.value,frm.currencyId.value);\"",false,false,sessionMng.m_lClientID);
 %>
								</td>				  					
				  			</tr>
				  		</table>
				  	</td>
				  </tr>
				  <tr>
				    <td height="10"></td>
				  </tr>				 
				 <TR class="MsoNormal">
					<TD class="MsoNormal">
				  		<TABLE align=center border=0 width="97%">
							<TR>
						  		<TD height=20  colspan = "2" width="80%">
						  			<common:ConstantMultipleSelect form="frm" name="strTransactionType" label="业务类型：" constantName="com.iss.itreasury.settlement.util.SETTConstant$TransactionType" width="280" size="6" office="<%= String.valueOf(sessionMng.m_lOfficeID) %>" currency="<%= String.valueOf(sessionMng.m_lCurrencyID) %>"/>
						  		</TD>
						  		<td>&nbsp;</td>
							</TR>
				  		</TABLE>
					</TD>
			  	 </TR>
			  <tr>
			    <td height="10"></td>
			  </tr>
				 <TR>
					<TD>
				  		<TABLE align=center border=0 width="97%">
							<TR>
						  		<TD width="12%">&nbsp;执行日期：</td>
						  		<td>
						  		   由</td>
						  		   <td width="20%">
						  		   <fs_c:calendar 
						         	    name="tsExecuteStartDate"
							          	value="" 
							          	properties="nextfield ='tsExecuteEndDate'" 
							          	size="18"/>
							        <script>
						          		$('#tsExecuteStartDate').val('<%=queryDate %>');
						          	</script>
							  	</td>
							  	<td width='6%'>&nbsp;</td>
							  	<td>	
							  	   至
							  	   </td><td width="25%">
							  	   <fs_c:calendar 
						         	    name="tsExecuteEndDate"
							          	value="" 
							          	properties="nextfield ='search'" 
							          	size="18"/>
							          	<script>
	          		$('#tsExecuteEndDate').val('<%=queryDate %>');
	          	</script>
							  	</TD>
							  	<td width="42%">&nbsp;</td>
							</TR>
				  		</TABLE>
					</TD>
			  	 </TR>
				<tr>
			          <td>
			            <div align="right">
						<input class=button1 name="search" type=button value=" 查 找 " onClick="doSearch(frm);">&nbsp;&nbsp;&nbsp;&nbsp;
						</div>
			          </td>
		        </tr>
		        <tr><td height="5"></td></tr>
		      </table>
		</td>
	</tr>
</table>
</form>

<script language="javascript">
String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

function doSearch(form)
{	
	if(!check(frm.tsExecuteStartDate.value,frm.tsExecuteEndDate.value,frm.hideOpenDate.value)) return;
	form.strSuccessPageURL.value = "<%=strContext%>/print/view/v002_P.jsp";
	form.strFailPageURL.value = "<%=strContext%>/print/view/v001_P.jsp";
	form.strAction.value = '<%=SETTConstant.PageControl.FIRSTPAGE%>';
	
	form.action = "<%=strContext%>/print/view/v002_P.jsp";
	showSending();
	form.submit();
}

function check(startDate,endDate,systemDate){
	//开始日期
	var startDateArray = startDate.split("-");
	var starttime = new Date(startDateArray[0],startDateArray[1],startDateArray[2]);
	var start = starttime.getTime(); 
	//结束日期
	var endDateArray = endDate.split("-");
	var endtime = new Date(endDateArray[0],endDateArray[1],endDateArray[2]);
	var end = endtime.getTime(); 
	//开机日期
	var systemDateArray = systemDate.split("-");
	var systemtime = new Date(systemDateArray[0],systemDateArray[1],systemDateArray[2]);
	var system = systemtime.getTime(); 	
	if(start>=system)
	{
		alert("起始日期不能大于等于开机日");
		frm.tsExecuteStartDate.focus();
		return false;
	}
	if(end>=system)
	{
		alert("结束日期不能大于等于开机日");
		frm.tsExecuteEndDate.focus();
		return false;
	}
	return true;

}

function showExcuteDate(lOfficeId,currencyId)
{
	$.ajax(
		{
			type:'post',
			url:'<%=strContext%>/capital/fixed/returnOpendate.jsp',
			data:'officeId='+lOfficeId+'&currenctId='+currencyId,
			async:false,
			success:function(result){
				var date = $(result).filter("div#result").text();
				frm.hideOpenDate.value = date;
				date = date.replace(/-/g,"/");
				var openDate = new Date(date);
				openDate.setDate(openDate.getDate()-1);
				var year = new String(openDate.getYear());
				var month = new String(openDate.getMonth()+1);
				var day = new String(openDate.getDate());
				if(month.length==1)
				{
					month = "0" + month;
				}
				if(day.length==1)
				{
					day = "0" + day;
				}
				var previousDate = year + "-" + month + "-" + day;
				frm.tsExecuteStartDate.value = previousDate;
				frm.tsExecuteEndDate.value = previousDate;
			}
		}
	);
}

//firstFocus(frmzjhb.wtclientname);
////setSubmitFunction("addme(frmzjhb)");
setFormName("frm");
</script>			

<%	
    //显示文件尾
	OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/SignValidate.inc" %>
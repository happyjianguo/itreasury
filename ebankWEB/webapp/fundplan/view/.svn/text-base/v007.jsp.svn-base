<!--
/**
 * 页面名称 ：v007.jsp
 * 页面功能 : 资金计划-复核-查找-超链接后得到的申报详情的页面； v006.jsp ————> 当前页面
 * 作    者 ：ylguo
 * 日    期 ：2008-10-27
 * 		
 * 修改历史 ：
 */
-->
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection,
				 java.util.Date,
				 com.iss.itreasury.util.*,
				 java.text.DateFormat,
				 java.text.SimpleDateFormat,				 
                 com.iss.itreasury.ebank.util.SessionOB,
                 com.iss.itreasury.ebank.util.OBHtml,
                 com.iss.itreasury.ebank.util.OBConstant,
                 com.iss.itreasury.util.Constant,
                 com.iss.itreasury.system.util.SYSHTML,
                 java.sql.Timestamp           
                 " 
                 %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.ListType" %>
<%@ page import="com.iss.itreasury.util.Constant.ModuleType" %>
<%@ page import="com.iss.itreasury.util.SessionMng" %>
		
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
String strTableTitle = null;
String strContext = request.getContextPath();
try
{       //请求检测是否登录
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,
			"Gen_E002");
			out.flush();
			return;
		}
		/* 判断用户是否有权限 */
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, strTableTitle, "", 1,
			"Gen_E003");
			out.flush();
			return;
		}

		//显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTableTitle, Constant.YesOrNo.YES);
%>

<jsp:include page="/ShowMessage.jsp"/>



<!-- 页面使用js声明 -->
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<%@ taglib uri="/WEB-INF/tlds/iss-fundplan-tags.tld" prefix="fundplan"%>
<safety:resources />

<!-- 页面使用js声明结束 -->



<form method="post" name="frm001">
<table  width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">资金计划复核</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<!-- 产生申报编号 -->
<%
	String strTemp = "";
    //客户名称
    String clientName = "";
    strTemp = request.getParameter("clientName") ;
    if(strTemp != "" && strTemp.length()>0){
       //clientName = new String(strTemp.getBytes("ISO-8859-1"),"GB2312");
       clientName = strTemp;
    }
    //申报编号
    String cpCode = "";
    strTemp = request.getParameter("cpCode");
    if(strTemp != "" && strTemp.length()>0){
       cpCode = strTemp;
    }
    
    Timestamp startTs = null;
    Timestamp endTs = null;
    //申报起始时间
    String startDate = "";
    strTemp = request.getParameter("startDate");
    if(strTemp != "" && strTemp.length()>0){
       startTs = DataFormat.getDateTime(strTemp);
       startDate = DataFormat.getDateString(startTs);
    }
    //申报结束时间
    String endDate = "";
    strTemp = request.getParameter("endDate");
    if(strTemp != "" && strTemp.length()>0){
       endTs = DataFormat.getDateTime(strTemp);
       endDate = DataFormat.getDateString(endTs);
    }
    
    //得到申报的状态
    long statusId = -1;
    strTemp = request.getParameter("statusId");
    if(strTemp != "" && strTemp.length()>0){
       statusId = Long.parseLong(strTemp);
    }
    
    //子表中的字段CapitalplanId
    long CapitalplanId = 0;
    strTemp = ""+request.getParameter("id");
    if(strTemp != "" && strTemp.length()>0){
      CapitalplanId = Long.parseLong(strTemp);
    }
    
    //获得当前复核人身份
    long checkP = sessionMng.m_lUserID;
    //获得录入人身份
    long inputerP = 0;
    strTemp = request.getParameter("inputerId");
    if(strTemp != "" && strTemp.length()>0){
      inputerP = Long.parseLong(strTemp);
    }
    
    //如果已经复核。则获得复核人身份
    long isCheckedP = 0;
    strTemp = request.getParameter("isCheckedP");
    if(strTemp != "" && strTemp.length()>0){
      isCheckedP = Long.parseLong(strTemp);
    }
    
    long modelId = -1;
    strTemp = request.getParameter("modelId");
    if(strTemp != "" && strTemp.length()>0){
      modelId = Long.parseLong(strTemp);
    }    
    //复核时间
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    Date _date = new Date();
    String checkdate = df.format(_date);
    
 %>
 <input name="p_SuccessPageURL" type="hidden" value="../view/v005.jsp">
<input name="p_FailPageURL" type="hidden" value="../view/v005.jsp">

<br/>

     <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr>
          <td colspan="4" height="1"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">申报单位</td>
          <td width="586">
		  	<input type="text" class="box" name="clientName" size="32" value="<%=clientName%>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">申报范围</td>
          <td width="586">
		  		<input type="text" class="box" name="dateFrom" size="16" value="<%=startDate %>" disabled="disabled">
				&nbsp;至&nbsp;
				<input type="text" class="box" name="dateTo" size="16" value="<%=endDate %>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>
        <tr>  
		  		<td width="4" height="25" ></td>
          <td width="100" align="left">申报编号</td>
          <td width="586">
		  		<input type="text" class="box" name="cpCode" size="20" value="<%=cpCode%>" disabled="disabled">
          </td>
          <td width="100" class="MsoNormal"></td>
        </tr>        
        
        <tr>
		  <td width="4" height="25" ></td>
          <td width="100" align="left">&nbsp;</td>
          <td width="686" colspan="2" align="right">
		  		单位：万元
          </td>
        </tr>
		
        <tr>
		  <td width="4" height="25"></td>
          <td width="100%" colspan="3">
          	<!-- 项目显示组件 -->
          	<fundplan:FundPlanWidget capitalplanId="<%=CapitalplanId %>" 
          							 disabled="true"
          							 type="submit"
          							 modelId="<%=modelId %>"
          							 office="<%=sessionMng.m_lOfficeID%>"
          							 currency="<%=sessionMng.m_lCurrencyID%>"
          							 clientId="<%=sessionMng.m_lClientID%>"
          							 dateFrom="<%=startDate%>"
          							 dateTo="<%=endDate%>" />
		  </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
        <tr>
        <td colspan=3></td>
        	<td>
          <div align="right">
          <%
          	if(statusId == 1 && inputerP != sessionMng.m_lUserID)
          	{
           %>
          	<input class="button1" name="strcheck" type="button" value=" 复 核 " onclick="toCheck()">&nbsp;&nbsp;
          <% } %>
		  <%
          	if(statusId == 2 && inputerP != sessionMng.m_lUserID)
          	{
           %>
          	<input class="button1" name="discheck" type="button" value=" 取消复核 " onclick="javascript:toDisCheck()">&nbsp;&nbsp;
		  <% } %>
		  	<input class="button1" name="back" type="button" value=" 返 回 " onclick="javascript:toBack()">&nbsp;&nbsp;
          </div>
          </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
</table> 


          
</td>
</tr>
</table>
</form>


<script language="javascript">
   window.onload = function ss(){
   		var statusId = <%=statusId%>;
   		var inputerP = <%=inputerP%>;
   		var uid = <%=sessionMng.m_lUserID%>;
   		setFormName("frm001");
   		if(statusId == 1 && inputerP != uid){
   			//setSubmitFunction("toCheck()");
   		}
   		if(statusId == 2 && inputerP != uid){
   			//setSubmitFunction("toDisCheck()");
   		}
   }
   
	function toCheck(){
		var checker =<%=checkP %>;
		var inputer =<%=inputerP%>;
		if(checker!=inputer)
	    {  
	    	if(confirm("确定要复核吗？"))
	    	{
	    		showSending();
				frm001.action ="../control/c006.jsp?&method=toCheck&capitalplanId=<%=CapitalplanId%>&checker=<%=checkP%>&checkDate=<%=checkdate%>";
				frm001.submit();
			}
		}
		else{
			alert("对不起，您是该资金计划的申报人，不能复核其内容！");
		}
	}
	
	function toDisCheck(){
		var checker =<%=checkP %>;
		var isCheckedP =<%=isCheckedP%>;
	    if(checker==isCheckedP){ 
			if(confirm("确定取消复核吗？"))
			{
				showSending();
				frm001.action ="../control/c006.jsp?&method=toDisCheck&capitalplanId=<%=CapitalplanId%>";
				frm001.submit();
		    }
	    }
	    else{
			alert("对不起，您不是该资金计划的复核人，不能取消复核！");
		}
	}
	function toBack(){
		window.history.back();
	}
	
	
</script>
<%
	/**
	* 显示文件尾
	*/
	OBHtml.showOBHomeEnd(out);
%>

<%
}
catch(IException ie)
{
	
	ie.printStackTrace();
	out.flush();
	return; 
}
%>	

<%@ include file="/common/SignValidate.inc" %>
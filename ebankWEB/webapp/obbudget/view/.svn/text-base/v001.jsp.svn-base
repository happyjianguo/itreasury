<%--
/*
 * 程序名称：v001.jsp
 * 功能说明：网银预算新增页
 * 作　　者：banreyliu
 * 完成日期：2006-09-04
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/taglib.tld" prefix="fs"%>
<%@ page import = "com.iss.itreasury.ebank.util.*,
				   java.net.URLEncoder,
				   com.iss.itreasury.util.*,
				   com.iss.itreasury.util.DataFormat,
				   com.iss.itreasury.util.IException,
				   java.util.Calendar,
				   java.sql.Timestamp"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.*" %>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.AttachParentType" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[用款新增]";
%>
<%
    OBHtml.validateRequest(out,request,response);
    
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	   // transTypeID = Constant.ModuleType.BUDGET
	}
	
	if((String)request.getParameter("ID")!=null)
	{
		if(Long.parseLong((String)request.getParameter("ID"))>0)
		{
	 %>
	 		<script language="JavaScript">
			alert("操作成功");
			</script>
	 <%
		}else if(Long.parseLong((String)request.getParameter("ID")) == Constant.FALSE){
	 %>
	 		<script language="JavaScript">
			alert("该账户下存在重叠的用款区间，请重新选择");
			</script>
	 <%		
		}
	}
	long period = -1;
	SettPeriodSetBiz biz = new SettPeriodSetBiz();
	PeriodSetInfo info = new PeriodSetInfo();
	info.setCurrencyId(sessionMng.m_lCurrencyID);
	info.setOfficeId(sessionMng.m_lOfficeID);
	info.setStatusId(1);
	info = biz.findByCondition(info);
	if(info.getId()>-1){
		period = info.getPeriod();
	}
	Timestamp endDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
	long endTime = endDate.getTime()+period*24*3600*1000-1;
	endDate.setTime(endTime);
	String strEndDate = DataFormat.formatDate(endDate,1);
%>
<%
	
	/* 用户登录检测与权限校验及文件头显示 */
    try 
	{
          // 用户登录检测 
		if (sessionMng.isLogin() == false) {
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E002");
			out.flush();
			return;
		}

		// 判断用户是否有权限 
		if (sessionMng.hasRight(request) == false) {
			out.println(sessionMng.hasRight(request));
			OBHtml.showCommonMessage(out, sessionMng, "", "", 1,
			"Gen_E003");
			out.flush();
			return;
		}
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webbudget/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="form_1" action="" method="post">
<input type="hidden" name="strSuccessPageURL" value="../view/v009.jsp">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="../view/v001.jsp">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">			<!--操作代码-->
		<input type="hidden" name="clientID" value=<%=sessionMng.m_lClientID%>>
		<input type="hidden" name="ModuleID" value=<%=sessionMng.m_lModuleID%>>
		<input type="hidden" name="OfficeID" value=<%=sessionMng.m_lOfficeID%>>
		<input type="hidden" name="CurrencyID" value=<%=sessionMng.m_lCurrencyID%>>
		<input type="hidden" name="transCode" value="">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">用款计划新增</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>		
    </td>
  </tr>
    </table>	
    <br/>
     <table width="80%" border="0" cellspacing="0" cellpadding="0" class=normal align="">
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款单位：</td>
          <td width="25%"><input readonly class="box"   type="text" name="txtClientName" value=<%=sessionMng.m_strClientName%> size="30"></td>
		  <td width="15%" height="25" class="MsoNormal"><font color=red>*</font>用款名称：</td>
		  <td width="25%" height="25" class="MsoNormal"><input class="box"  type="text" name="sname"  onfocus="nextfield ='budgetaccount';" size="20" maxlength="15"></td>
        </tr> 
        
        <tr class="MsoNormal"> 
        <td width="2%" height="25" class="MsoNormal"></td>
        <%
            long sPeriod=-1;
              String strMagnifierNamePeriod = URLEncoder.encode("用款账户");
              String strFormNamePeriod = "form_1";
              String strPrefixPeriod = "";
              String[] strMainNamesPeriod = {"budgetaccount"};
              String[] strMainFieldsPeriod= {"accountno"};
              String[] strReturnNamesPeriod = {"accountID"};
              String[] strReturnFieldsPeriod = {"id"};
              String strReturnInitValuesPeriod= new Long(sPeriod).toString();
              if(strReturnInitValuesPeriod.equals("-1"))
            	  strReturnInitValuesPeriod = new String("");
              String[] strReturnValuesPeriod = {"-1"};
              String[] strDisplayNamesPeriod = {URLEncoder.encode("账户名称"),URLEncoder.encode("账号")};
              String[] strDisplayFieldsPeriod = {"name","accountno"};
              int nIndexPeriod = 0;
              String strMainPropertyPeriod = "";
              String strSQLPeriod = "getBudgetPayerAccountNoSQL(form_1.budgetaccount.value,"+sessionMng.m_lUserID+","+sessionMng.m_lClientID+","+sessionMng.m_lCurrencyID+",1,"+sessionMng.m_lOfficeID+")";
              String strMatchValuePeriod = "accountno";
              String strNextControlsPeriod = "startdate";
              String strTitlePeriod = "<font color=red>*</font>用款账户";
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
           
%>          
 
          <td  height="25"></td>
          <td  height="25"></td>
          <td  height="25"></td>
        </tr>
		<tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25><font color=red>*</font>用款区间：从</td>
          <td width="25%">
         	 <fs_c:calendar 
	         	    name="startdate"
		          	value="" 
		          	properties="nextfield ='enddate'" 
		          	size="12"/>
		      <script>
	          		$('#startdate').val('<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>');
	          </script>
          </td>
		  <td width="15%" height="25" class="MsoNormal">到：</td>
		  <td width="25%" height="25" class="MsoNormal">
			 <fs_c:calendar 
	         	    name="enddate"
		          	value="" 
		          	properties="nextfield ='note'" 
		          	size="12"/>
		      <script>
	          		$('#enddate').val('<%=strEndDate%>');
	          </script>
		  </td>
        </tr> 
        <tr class="MsoNormal">
		  <td width="2%" height="25" class="MsoNormal"></td>
          <td width="15%" class="MsoNormal" height=25>用款说明：</td>
          <td width="25%" colspan="3"><input class="box"   type="text" name="note" size="50" onfocus="nextfield ='';" maxlength="50"></td>
		  
        </tr> 
        <tr>
        <td> </td>

</TR>
		<tr>
          <td colspan="7"> </td>
          
            <td align="right" >
			<!--img src="\webob\graphics\button_tijiao.gif" width="46" height="18" border="0" onclick="Javascript:addme();"-->
			<input class=button1 name=add type=button value=" 下一步 " onClick="Javascript:addme();" onKeyDown="Javascript:addme();"> 
			</td>
          <td >&nbsp;</td>
        </tr>
        <tr><td >&nbsp;</td></tr>
      </table>
      <br>
       
  <input name="sysdate" type="hidden" value=<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%>>
	  <!--汇款方式动态显示收款方资料-->


<%
		
%>
	 <!-- 刷新问题 -->
<input type="hidden" name="clickCount" value="0">
<!-- 刷新问题 --> 
</form>
 
<script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(form_1.sname);
	//setSubmitFunction("addme()");
	setFormName("form_1");
</script>

<script language="Javascript">
    //计算天数差的函数，通用  
   function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
       var  aDate,  oDate1,  oDate2,  iDays  
       aDate  =  sDate1.split("-")  
       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
       aDate  =  sDate2.split("-")  
       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
       return  iDays  
   } 

 /* 修改提交处理函数 */
    function addme()
    {  	
    	if(<%=period%> == -1){
    		alert("用款周期未设置，该业务还不可用，请联系系统管理员");
    		return false;
    	}
    	var periodDate = DateDiff(form_1.startdate.value,form_1.enddate.value)+1;	 
		form_1.action = "../control/c003.jsp?period="+periodDate;
		if (validate() == true)
        { 
        	if((!CompareDateString(form_1.sysdate.value,form_1.startdate.value)))
        	{
        		alert("开始日期小于当前日期，请重新选择");
        		return false;
        	}else if(periodDate > <%=period%>){
        		alert("选择的周期大于设置的周期数<%=period%>天，请重新选择");
        		return false;
        	} 
			form_1.startdate.value = formatedate(form_1.startdate.value);
			form_1.enddate.value = formatedate(form_1.enddate.value);
			//form_1.clickCount.value = parseInt(form_1.clickCount.value) +1 ;
			showSending();
            form_1.submit();
            
        }
    }
    

    
    /* 取消处理函数 */
    function cancelme()
    {
		 	
			if (confirm("是否取消？"))
			{
				form_1.action="../view/v001.jsp";
				form_1.submit();
			}
		 
    }
function validate()
    {
       /**
        * 如果所有条件符合，return ture
        * 如果校验出错，return false
        **/
        
		if (form_1.sname.value == "")
		{
			alert("用款名称不能为空");
			form_1.sname.focus();
			return false;
		}
		if (form_1.accountID.value < 0)
		{
			alert("用款账号请从放大镜中选择");
			form_1.accountID.focus();
			return false;
		}
		/* 划款资料非空校验 */
		/* 预算说明 */
		if (!InputValid(form_1.note, 0, "", 1, 0, 100,"用款说明"))
		{
			return false;
		}
		if(!CompareDate(form_1.startdate,form_1.enddate,"用款区间终止时间应该晚于起始时间"))
		{
			return false;
		}
    	return true;
		
    }
</script>
<%

	  }
	catch (IException ie)
	{
		
		
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);		
		return;
    }
	/* 显示文件尾 */
		OBHtml.showOBHomeEnd(out);	
%>
<%@ include file="/common/SignValidate.inc" %> 

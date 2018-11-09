<%
/**页面功能说明
 * 页面名称 ：v011.jsp
 * 页面功能 : 历史余额查询  显示层
 * 作    者 ：barneyliu
 * 日    期 ：2005-11-28
 * 简单实现说明：
 *				1、
 * 特殊说明 ：
 * 修改历史 ：
 */
%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="common" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	 response.setHeader("Pragma","no-cache");
	 int i = 0;
     String strRootPath = request.getContextPath();//http://xxxx/../iTreasury-ebank
 	 String colsname  = null;//en zh
    try
	{
         
        //登录检测
		//请求检测
		 /*if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        	out.flush();
        	return;
        }*/
		

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
        	out.flush();
        	return;
        }
       OBHtml.showOBHomeHead(out, sessionMng, "汇总账户历史余额", 1);
       
       String strDateStart = DataFormat.getDateString();
	   String strDateEnd = DataFormat.getDateString();
%>
<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>
<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/Check.js" ></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>
<safety:resources />

<!--form 开始-->
<form name="frmV011" method=post>
<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="findFirst">
<input type="hidden" name="strCtrlPageURL" value="a131-c.jsp">
 
<TABLE  align=center border="0" cellpadding="2" cellspacing="0" width=98% class="top">

		<tr>
		<td colspan="4" height="1" class=FormTitle>汇总账户历史余额查询</td>
		</tr>
			<!--tag--multipleselect-->
          
			<TR>
			<td>
					<% String sql="SELECT n_id id,s_name name FROM BS_COUNTRYSETTING WHERE n_rdStatus = " + Constant.RecordStatus.VALID;%>
					<common:multipleSelect   form="frmV011" name="countryId"  label="国家"  sql="<%=sql%>" sqlKey="id" sqlValue="name"/>			 
		 	</td>
		 	</TR>
			 <TR>
			 <td>
				 	
						<%sql="SELECT n_Id id,s_name name FROM bs_accountpropertyonesetting WHERE n_rdStatus =  " + Constant.RecordStatus.VALID;%>
						<common:multipleSelect   form="frmV011" name="areaCenter"  label="区域中心" sql="<%=sql%>"  sqlKey="id" sqlValue="name" />
			</td>	 
		 	</TR>
 			<TR>
 			<td>	 
						<%sql="SELECT  n_Id id,s_name name FROM  BS_BANKSETTING  WHERE  n_rdStatus  = " + Constant.RecordStatus.VALID+"union select -9,'财务公司' from dual";%>
						<common:multipleSelect   form="frmV011" name="bankId"   label="银行名称"  sql="<%=sql%>"  sqlKey="id" sqlValue="name" />
			</td>	 
		 	</TR>
		 	<TR>
		 	<td>	 
						<%sql="select t.n_id id,t.s_name_zh name from bs_currencysetting t where N_RDSTATUS="+Constant.RecordStatus.VALID;%>
		    			<common:multipleSelect form="frmV011" name="currencyId"   label="币种" sql="<%=sql%>" sqlKey="id" sqlValue="name"/>			 
			</td>
			</TR>
			
			<TR>
			<td>
				 	
						<%sql="select t.id id,t.saccountno name from sett_account t where t.ncheckstatusid = 4  and t.nclientid ="+sessionMng.m_lClientID+" union select b.n_id id,b.s_accountno name from bs_bankaccountinfo b where b.n_ischeck = 1 and b.n_rdstatus =1 and b.n_clientid="+sessionMng.m_lClientID+" order by name";
						//sql="select t.naccountid id,t.saccountno name from ob_accountownedbyuser t where t.nuserid="+sessionMng.m_lUserID;%>
						<common:multipleSelect form="frmV011" name="accountId"    sqlKey="id" sqlValue="name" label="账户"  sql="<%=sql%>"/>
				 
			</td>
			</TR>
			</table>
			
					 	
          <!--tag---dateInput-->
      <table width=98% class="top">
      <tr class="MsoNormal"> 
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">查询日期：</td>
          <td width="60" height="25" class="MsoNormal">
            <div align="right">由</div>
          </td>
          <td  height="25" class="MsoNormal"> 
          	 <fs_c:calendar 
          	    name="startDate"
	          	value="" 
	          	size="20"/>
	          	<script>
	          		$('#startDate').val('<%=strDateStart%>');
	          	</script>
			至
		     <fs_c:calendar 
          	    name="endDate"
	          	value="" 
	          	size="20"/>
	            <script>
	          		$('#endDate').val('<%=strDateEnd%>');
	          	</script>
		  </td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        </table>


<hr>
	<!--tag--button-->
	<table width=75%>
		<TR>
			<TD align="center">
		        <TABLE align=center border=0 width=99%>
					<TR align="right">
						<TD align="right">
							<input type="button" class="button" name="button1" value="查 询" onclick="javascript:doSearch();" onfocus="javascript:nextField='submitfunction'"/>						
						</TD>
					</TR>
				</TABLE>
			</TD>
	    </TR>
	    </table>
		
  
		<input type="hidden" name="control" value="view">
 
	 </form>

  <script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frmV011.countryIdLeft);
	//setSubmitFunction("doSearch()");
	setFormName("frmV011");
</script>

<script language="javascript">

function doSearch()
{
	var strDate = "<%=strDateStart%>";
	
	//检查日期
	if( !(CompareDate(frmV011.startDate,frmV011.endDate,"结束日期不能早于开始日期")) )
	{
		return false;
	}
	frmV011.startDate.value = formatedate(frmV011.startDate.value);//格式化日期
	frmV011.endDate.value = formatedate(frmV011.endDate.value);
	if(!(CompareDateString(frmV011.startDate.value,strDate)))
	{
		alert("查询起始日期不能晚于当前日期");
		return false;
	}
	if(!(CompareDateString(frmV011.endDate.value,strDate)))
	{
		alert("查询结束日期不能晚于当前日期");
		return false;
	}
		
		showSending();//显示正在执行
		frmV011.action = "a131-c.jsp"
		frmV011.strSuccessPageURL.value="/accountinfo/a132-v.jsp";	//定义操作成功后跳往的页面
		frmV011.strFailPageURL.value="/accountinfo/a130-v.jsp";		//定义失败后跳往的页面
		frmV011.strAction.value="findFirst";	//定义操作代码
		frmV011.submit();
	
} 
</script>   
<%	
	/**
	* 显示文件尾
	*/
	OBHtml.showOBHomeEnd(out);
}
//异常处理
catch(Exception exp)
{
	Log.print(exp.getMessage());
	OBHtml.showCommonMessage(out,sessionMng,"","",1,"Gen_E001");
}
%>

<%@ include file="/common/SignValidate.inc" %>

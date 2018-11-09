<%
/**页面功能说明
 * 页面名称 ：v001.jsp
 * 页面功能 : 汇总账户当日余额查询  显示层
 * 作    者 ：barneyliu
 * 日    期 ：2005-11-14
 * 简单实现说明：
 *				1、
 * 特殊说明 ：
 * 修改历史 ：
 */
%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="common" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
	
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	 response.setHeader("Pragma","no-cache");
	 int i = 0;
     String strRootPath = request.getContextPath();//http://xxxx/../ iTreasury-ebank
     String colsname  = null;//en zh
    try
	{   
        //登录检测
	   Log.print("----------------- 进入页面 a120-v.jsp -----------------");
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
       OBHtml.showOBHomeHead(out, sessionMng, "汇总账户当日余额", 1);

%>
<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<jsp:include page="/ShowMessage.jsp"/>
<!--引入js文件-->
<script language="JavaScript" src="/webob/js/Control.js" ></script>
<script language="JavaScript" src="/webob/js/SettCheck.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="JavaScript" src="/webscript/taglib.js"></script>
<safety:resources />
<!--form 开始-->
<form name="frmV001" method=post >
<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="findFirst">
<input type="hidden" name="strCtrlPageURL" value="a121-c.jsp">

	<table   align=center border="0" cellpadding="2" cellspacing="0" width=98% class="top"> 
		<tr>
		<td colspan="4" height="1" class=FormTitle>汇总账户当日余额查询</td>
		</tr>
		 <TR>
		 <td>
			<% String sql="SELECT n_Id id,s_name name FROM bs_accountpropertyonesetting WHERE n_rdStatus =  " + Constant.RecordStatus.VALID;%>
			<common:multipleSelect   form="frmV001" name="areaCenter"  label="区域中心"  sql="<%=sql%>"/>			 
		 </td>
		 </TR>
       	  <TR>
       	  <td>
			<%sql="SELECT  n_Id id,s_name name FROM  BS_BANKSETTING  WHERE  n_rdStatus  = " + Constant.RecordStatus.VALID+"union select -9,'财务公司' from dual";%>
			<common:multipleSelect   form="frmV001" name="bankId"  label="银行名称" sql="<%=sql%>" sqlKey="id" sqlValue="name"/>			 
		 </td>
		 </TR>
		 <TR>
		 <td>	 
			<%sql="select t.n_id id,t.s_name_zh name from bs_currencysetting t where N_RDSTATUS="+Constant.RecordStatus.VALID;%>
		    <common:multipleSelect form="frmV001" name="currencyId"   label="币种" sql="<%=sql%>" sqlKey="id" sqlValue="name"/>			 
		</td>
		</TR>   
 	</table>
<hr>
<table width=100%>
		<TR>
			<TD align="center">
		        <TABLE align=center border=0 width=99%>
					<TR align="right">
						<TD align="right">
							<input type="button" class="button" name="button1" value="查询" onclick="javascript:doSearch();" onfocus="javascript:nextField='submitfunction'">						
						</TD>
					</TR>
				</TABLE>
			</TD>
	    </TR>
	    </table>
	 
  
		<input type="hidden" name="control" value="view" >
 
	 </form>

  <script language="javascript">
	/* 页面焦点及回车控制 */
	firstFocus(frmV001.areaCenterLeft);
	//setSubmitFunction("doSearch()");
	setFormName("frmV001");
</script>

<script language="javascript">

function doSearch()
{
	 
		showSending();//显示正在执行
		frmV001.action = "a121-c.jsp"
		frmV001.strSuccessPageURL.value="a122-v.jsp";	//定义操作成功后跳往的页面
		frmV001.strFailPageURL.value="a120-v.jsp";		//定义失败后跳往的页面
		frmV001.strAction.value="findFirst";	//定义操作代码
		frmV001.submit();
	
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
}
%>

<%@ include file="/common/SignValidate.inc" %>

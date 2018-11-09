<%--
 页面名称 ：v009.jsp
 页面功能 : 挂失业务处理详细资料浏览页面2
 作    者 ：jinchen
 日    期 ：2004-11-23
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@page import="
				java.util.*,
				java.sql.*,
				com.iss.itreasury.util.*,
				com.iss.itreasury.settlement.util.SETTHTML,
				com.iss.itreasury.settlement.util.SETTConstant,
				com.iss.itreasury.settlement.util.NameRef,
				com.iss.itreasury.settlement.reportlossorfreeze.dataentity.*,
				com.iss.itreasury.settlement.reportlossorfreeze.bizlogic.*,
				com.iss.itreasury.settlement.reportlossorfreeze.dao.*,
				com.iss.itreasury.settlement.bizdelegation.TransAbatementDelegation,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementDetailInfo,
				com.iss.itreasury.settlement.transabatement.dataentity.TransAbatementInfo
				"
%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	try{

	Log.print("=================进入页面reportlossorfreeze/view/v009.jsp=========");
	/** 权限检查 **/
	String strTableTitle = "业务处理 —— 挂失";
	   
	//请求检测
	//if(!SETTHTML.validateRequest(out, request,response)) return;
		
	//显示文件头
	//SETTHTML.showHomeHead(out,sessionMng,Env.getClientName());
	OBHtml.showOBHomeHead(out, sessionMng, "挂失详细信息", OBConstant.ShowMenu.NO);
	/**
	 * 公共参数
	 */
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//办事处ID
	String strTemp = "";
	TransAbatementInfo transAbatementInfo=new TransAbatementInfo();
	String isBack="false";
	/**
	 * 页面控制类
	 */
	PageCtrlInfo pageInfo		= new PageCtrlInfo();
	pageInfo.convertRequestToDataEntity(request);
	
	/**
	 * 从request得到页面控制参数
	 */
	 if (!pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
	 {
		 if(request.getAttribute("isBack")!=null)
		 {
			isBack=String.valueOf(request.getAttribute("isBack"));
			if(isBack.equalsIgnoreCase("true"))
			 {
				//transAbatementInfo.convertRequestToDataEntity(request);
			 }
				
		 }
	 }
	ReportLossOrFreezeInfo info = new ReportLossOrFreezeInfo();
	info = (ReportLossOrFreezeInfo)request.getAttribute("ResultInfo");
	if(info==null)
	{
		info = new ReportLossOrFreezeInfo();
	}
	/**
	 * 如果操作结果不是成功,从request中获得所有和dataentity字段绑定的数据,完成页面回显
	 */
	//if (pageInfo.getStrActionResult().equals(Constant.ActionResult.FAIL))
	//	transAbatementInfo.convertRequestToDataEntity(request);

//显示前页传过的提示
String SucInfo=(String) request.getAttribute("SucInfo");
if(SucInfo!=null&&SucInfo.length()>0)
{
%>
<script language="JavaScript">
alert("<%=SucInfo%>");
</script>
<%}%>

<!--jsp:include page="/ShowMessage.jsp"/-->

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<!--引入js文件-->
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>

<safety:resources />

<!--引入js文件-->
<form method="post" name="frm">
	<!--页面控制变量-->
	<input type="hidden" name="strSourcePage" value="">
	<input type="hidden" name="strSuccessPageURL" value="">
	<input type="hidden" name="strFailPageURL" value="">
	<input type="hidden" name="strAction" value="">
	<input type="hidden" name="strCtrlPageURL" value="">
	
	<!--重复请求控制-->
	<!--页面控制变量-->
	
	<!--隐含业务数据-->
	<input type="hidden" name="isChange" value="">
	<input type="hidden" name="hdnId" value="<%=info.getId()%>">
	<input type="hidden" name="hdnTransActionType" value="<%=SETTConstant.TransactionType.REPORTLOSS%>">
	<!--隐含业务数据-->

  <TABLE width="100%" height="10" border=0 class=top>
       <TBODY>
	
		 <TR>
           <TD class=FormTitle height=2 width="100%"><B>挂失 —— 详细信息</B></TD>
         </TR>
         <TR>
           <TD width="100%" height=40 vAlign=bottom>
             <fieldset>
             <TABLE align=center border=0 width="100%">
               <TBODY>
                 <TR>
                   <td >交易编号：</td>
                   <td><input type="text" name="lClientIDCtrl3" class="box"  maxlength='30' value="<%=DataFormat.formatString(info.getTransNo())%>" disabled>
                   </td>
                   <TD height=20>&nbsp;</TD>
                   <TD height=20>&nbsp;</TD>
                 </TR>
                 <TR>
                   <input type="hidden" name="lClientID" value="-1">
                   <input type="hidden" name="lClientIDFromClient" value="0">
                   <td width="19%" > 客户编号：&nbsp;</td>
                   <td width="27%"><input type="text" name="lClientIDCtrl" class="box"  maxlength='30' value="<%=DataFormat.formatString(NameRef.getClientCodeByID(info.getClientId()))%>" disabled>
                   </td>
                   <script language="javascript">showDisableAccountCtrl("strAccountNo","<%=DataFormat.formatString(NameRef.getAccountNoByID(info.getAccountId()))%>","账号","width=15%","width=30%");
					</script>
                 </TR>
                 <TR>
                  
                   <td > 旧存单号：</td>
                   <td ><input type="text" name="lClientIDCtrl4" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getOldDepositNo())%>' disabled>
					</td>

					<td > 新存单号：</td>
                   <td ><input type="text" name="lClientIDCtrl41" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getNewDepositNo())%>' disabled>
					</td>


                 
                 </TR>
                 <TR>
                  
					  <TD width="18%" height=20 vAlign=middle> 生效日期：</TD>
                   <TD width="36%" height=20 vAlign=top><input type="text" name="lClientIDCtrl42" class="box"  maxlength='30' value='<%=DataFormat.getDateString(info.getEffectiveDate())%>' disabled>                   
                   </TD>

                   <td > 摘要：</td>
                   <td>                     <input type="text" name="lClientIDCtrl43" class="box"  maxlength='30' value='<%=DataFormat.formatString(info.getAbstract())%>' disabled>
                 </td>
                  
                  
                 
                 </TR>
				 <tr>
					 <td >执行日期：</td>
                   <td><input type="text" name="lClientIDCtrl422" class="box"  maxlength='30' value='<%=DataFormat.getDateString(info.getExecuteDate())%>' disabled></td>
				 </tr>
               </TBODY>
             </TABLE>
             </fieldset>
           </TD>
         </TR>
         <TR>
         <TD height=10 vAlign=top width="100%">
             <TABLE align=center height="15" width="97%">
               <TBODY>
                 <TR>
                   <TD colSpan='6' height="10"><DIV align=right>
                      
                       <input name="Submit32" type="button" class="button" onClick="doBack();" value=" 关 闭 " >
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
             </TABLE>
             <table width="100%" border="0">
               <tr>
                 <td width="8%">操作员：</td>
                 <td><%=DataFormat.formatString(NameRef.getUserNameByID(info.getCheckUserId()))%></td>
                 <td width="11%"><p>&nbsp;</p></td>
                 <td>&nbsp;</td>
               </tr>
             </table></TD>
         </TR>



       </TBODY>
    </TABLE>
</form>	

<script language="JavaScript">
//firstFocus(document.frm.lClientIDCtrl);
//setSubmitFunction("doGoNext()");
setFormName("frm"); 
</script>

<script language="javascript">
var isSubmited=false;
//js 函数定义
function doBack()
{
	if(confirm("是否确定关闭窗口？"))
	window.close();
}
function doGoNext()
{
	
	
		if(isSubmited)	
		{
			alert("请求已提交，请稍候！");
			return;
		}
		

		
			   
		   frm.action = "../control/c003.jsp";
		   frm.strSuccessPageURL.value="/settlement/tran/reportlossorfreeze/view/v008.jsp";
		   frm.strFailPageURL.value="/settlement/tran/reportlossorfreeze/view/v008.jsp";
		   //frm.strCtrlPageURL.value="/settlement/tran/reportlossorfreeze/control/c003.jsp";
		   frm.strAction.value = "<%=SETTConstant.Actions.MATCHSEARCH%>";//操作--匹配查找
		   showSending();
		   isSubmited=true;
		   frm.submit();
		
}
function doGoSearch()
{
	if(isSubmited)	
   {
   		alert("请求已提交，请稍候！");
		return;
   }
	frm.action="../control/c001.jsp";
	frm.strSuccessPageURL.value="../view/v007.jsp";
	frm.strFailPageURL.value="../view/v007.jsp";
	frm.strCtrlPageURL.value="../control/c001.jsp";
	frm.strAction.value = "<%=SETTConstant.Actions.LINKSEARCH%>";//操作--链接查询
	showSending();
	isSubmited=true;
	frm.submit();
}
function allFields()
{
	
}
</script>
<%
	//显示文件尾
	//SETTHTML.showHomeEnd(out);
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(Exception e)
    {
		
		e.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>
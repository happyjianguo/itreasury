<%--
 页面名称 ：v008.jsp
 页面功能 : 挂失业务查找结果浏览页面
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
<% String strContext = request.getContextPath();%>

<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>


<%
	try{

	Log.print("=================进入页面reportlossorfreeze/view/v008.jsp=========");
	/** 权限检查 **/
	String strTableTitle = "业务处理 —— 挂失";
	   
	//请求检测
	//if(!SETTHTML.validateRequest(out, request,response)) return;
	/* 判断用户是否有权限 */
	if (sessionMng.hasRight(request) == false) {
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
		out.flush();
		return;
	}
		
	//显示文件头
	//SETTHTML.showHomeHead(out,sessionMng,Env.getClientName());
	OBHtml.showOBHomeHead(out, sessionMng, "冻结业务查询", OBConstant.ShowMenu.YES);
	/**
	 * 公共参数
	 */
	String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
	String strFailPageURL = (String)request.getAttribute("strFailPageURL");
	long lCurrencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long lOfficeId				    = sessionMng.m_lOfficeID;			//办事处ID
	String strTemp = "";
	TransAbatementInfo transAbatementInfo=new TransAbatementInfo();
	String isBack="false";
	long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	long lOrder = 1;
	
	String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");
	Object[] queryResults = null;
	queryResults = (Object[])request.getAttribute(Constant.PageControl.SearchResults);
	strTemp = (String)request.getAttribute("Desc");
	if(strTemp != null && strTemp.length()>0)
	{
		lDesc = Long.valueOf(strTemp).longValue();
	}
	if( lDesc == Constant.PageControl.CODE_ASCORDESC_ASC )
	{
		lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
	}
	else
	{
		lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	}
	strTemp = (String)request.getAttribute("Order");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		lOrder = Long.valueOf(strTemp).longValue();
	}	
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

	<!--隐含业务数据-->
	<table width="99%" class="top" height="140">
  
       <TBODY>
        
		 <TR>
           <TD class=FormTitle height=2 width="100%"><B> 	冻结业务查询</B></TD>
         </TR>

         <TR>
           <TD height=20 vAlign=top width="100%">
		   
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
	<input type="hidden" name="hdnId" value="">
	<input type="hidden" name="Order" value="">
	<input type="hidden" name="Desc" value="">

	<input type="hidden" name="_pageLoaderKey"  value="<%= strPageLoaderKey %>" >
	
             <TABLE width="100%" height=64 border=0 align=center cellspacing="1" class=ItemList>
               <TBODY>
                 <TR>
                   <td height="20" nowrap class="ItemTitle">
                     <div align="center"><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_TransNo%>)">交易编号</a></div>
                   </td>
                   <TD height=20 align="center" nowrap class=ItemTitle>交易类型</TD>
                   <td nowrap class="ItemTitle" align="center" ><a href="#">生效日期</a></td>
                   <td height="20" nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_ClientCode%>)">客户编号</a></td>
                   <td nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_ClientName%>)">客户名称</a></td>
                   <td height="20" nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_AccountNo%>)">账号</a></td>
                   <td height="20" nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_OldDepositNo%>)">存单号</a></td>
                   <td nowrap class="ItemTitle" align="center">冻结方式</td>
				    <td nowrap class="ItemTitle" align="center"><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_FreezeAmount%>)">金额</a></td>
                   <td nowrap class="ItemTitle" align="center" ><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_ExecuteDate%>)">执行日期</a></td>
                   <td nowrap class="ItemTitle" align="center"><A href="javascript:OrderPage(<%=ReportLossOrFreezeBean.OrderBy_CheckUserName%>)">操作员</a></td>
<%
	String strDetailPageURL = null;
	if(queryResults!=null && queryResults.length>0)
	{
		for( int i=0; queryResults != null && i<queryResults.length; i++ )
		{
			ReportLossOrFreezeInfo info = (ReportLossOrFreezeInfo)queryResults[i];
			strDetailPageURL = strContext+"/capital/query/q051-c.jsp?hdnId="+info.getId()+"&strAction="+SETTConstant.Actions.TODETAIL+"&strSuccessPageURL=/capital/query/q052-v.jsp&strFailPageURL=/capital/query/q052-v.jsp";
%>
                 <TR>
                   <td class="ItemBody" height="20" align="right"><A href="<%= strDetailPageURL %>"  target="blank"><%=DataFormat.formatString(info.getTransNo())%> </a></td>
                   
                   <td class="ItemBody" height="20" align="left"><%=SETTConstant.TransactionType.getName(info.getTransActionType())%></td>
                   <td class="ItemBody" height="20" align="center"><%=DataFormat.getDateString(info.getEffectiveDate())%></td>
                   <td class="ItemBody" align="right"><%=DataFormat.formatString(info.getClientCode())%></td>
                   <td class="ItemBody" height="20" align="left"><%=DataFormat.formatString(info.getClientName())%></td>
                   <td class="ItemBody" height="20" align="right"><%=DataFormat.formatString(info.getAccountNo())%></td>
                   <td class="ItemBody" align="right" height="20"><%=DataFormat.formatString(info.getOldDepositNo())%></td>
                   <td class="ItemBody" align="left" height="20"><%=DataFormat.formatString(SETTConstant.SubAccountStatus.getName(info.getSubAccountNewStatus()))%></td>
				   <td class="ItemBody" align="right" height="20"><%=DataFormat.formatAmount(info.getFreezeAmount())%></td>
                   <td class="ItemBody" align="center" height="20"><%=DataFormat.getDateString(info.getExecuteDate())%></td>
				   <td class="ItemBody"  align="left" height="20"><%=DataFormat.formatString(info.getCheckUserName())%></td>
                 </TR>
<%
		}
	}
%>
				
               
                       
				



                       
               
               



       </TBODY>
     </TABLE>
	</form>
</td>
</tr>
	<!-- 分页控件 -->
				  <tr  align="center" > 
					<td width="99%"  valign="bottom">
						 <TABLE border="0" cellPadding=1 height=20 width="99%" class="ItemList">
						 <TBODY>
							 <TR>
								<TD height=20 width=99% class="ItemTitle">
									<DIV align=right> 
									   <jsp:include page="/pagenavigator.jsp"  />  
								  </DIV>
								</TD>
							  </TR>
						  </TBODY>
						  </TABLE>
					 </TD>
					</TR>
				  
 <tr>
 <td>
<TABLE align=center height=26 width="99%">
               <TBODY>
                 <TR vAlign=middle>
                   <TD width="70%" height=20>
                     <DIV align=right></DIV>
                     <DIV align=right>
                       <INPUT name="cmdBack" type=button class=button onclick="javascript:location.href='<%=strContext%>/capital/query/q041-v.jsp';" value=" 返 回 ">
                     </DIV>
                   </TD>
                 </TR>
               </TBODY>
</TABLE>
</td>
</tr>
</TBODY>	
</table>


<script language="JavaScript">
//firstFocus(document.frm.lClientIDCtrl);
////setSubmitFunction("doGoNext()");
setFormName("frm"); 
</script>

<script language="javascript">
var isSubmited=false;
//js 函数定义
/*
 *  查询1条记录，即详细查询
 *
 */
function queryDetail(id)
{
	
	
	frm.hdnId.value = id;
	frm.action = "<%=strContext%>/settlement/tran/reportlossorfreeze/control/c001.jsp";
	frm.strSuccessPageURL.value="../view/v029.jsp";
	frm.strFailPageURL.value="../view/v029.jsp";
	frm.strCtrlPageURL.value="../control/c001.jsp";
	frm.strAction.value = "<%=SETTConstant.Actions.TODETAIL%>";//操作--点交易号到详细页面
	showSending();
	isSubmited=true;
	frm.submit();

	
}

function OrderPage(nOrderBy)
{
	frm.action = "q053-c.jsp";
    frm.strSuccessPageURL.value="/capital/query/q043-v.jsp";
     frm.strFailPageURL.value="/capital/query/q043-v.jsp";
    //frm.strCtrlPageURL.value="..control/c001.jsp";
    //frm.strAction.value = "<%=SETTConstant.Actions.MATCHSEARCH%>";//操作--匹配查找
	frm.Desc.value="<%=lDesc%>";
    frm.Order.value=nOrderBy;
	frm._pageLoaderKey.value='<%=strPageLoaderKey%>'
	frm.strAction.value="<%= Constant.PageControl.FIRSTPAGE %>";
	showSending();//显示正在执行
	frm.submit();
} 


function doGoNext()
{
	if(isSubmited)	
   {
   		alert("请求已提交，请稍候！");
		return;
   }
	if(!validateFields(frm)) return;
	   frm.action = "../control/c001.jsp";
	   frm.strSuccessPageURL.value="../view/v001.jsp";
	   frm.strFailPageURL.value="../view/v001.jsp";
	   frm.strCtrlPageURL.value="../control/c001.jsp";
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
	frm.action="../control/c004.jsp";
	frm.strSuccessPageURL.value="../view/v003.jsp";
	frm.strFailPageURL.value="../view/v001.jsp";
	frm.strCtrlPageURL.value="../control/c004.jsp";
	showSending();
	isSubmited=true;
	frm.submit();
}
function allFields()
{
	this.aa=new Array("lClientID","客户编号","magnifier",1);
	this.ab=new Array("lAccountID","账号","magnifier",1);
	this.ac=new Array("lSubAccountID","存单","magnifier",1);
	this.ad=new Array("strExecuteDate","生效日期","date",1);
	
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
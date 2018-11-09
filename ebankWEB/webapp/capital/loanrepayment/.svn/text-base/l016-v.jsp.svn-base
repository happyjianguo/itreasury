<%--
 页面名称 ：l016-v.jsp
 页面功能 : [贷款还款]--委托贷款
 作    者 ：gqzhang
 日    期 ：2004年2月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
				  com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款]--委托贷款";
%>
<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();	
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    
	long lSourceType = 0;//头信息显示
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
	
	long lCheckType = -1;//复核于复核匹配别
	String strCheckType = request.getParameter("checktype");
    if ((strCheckType != null) && (strCheckType.length()>0))
	{
	    lCheckType = Long.parseLong(strCheckType);
	}
    String lTransType = "";
    lTransType= (String)request.getParameter("lTransType");
    if(lTransType==null)
       	lTransType = (String)request.getParameter("txtTransType");
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/capital/loanrepayment/l016-v.jsp.jsp*******");
	
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
		
		//定义变量
		//用于查询返回的参数
		String strTemp = "";
		String strAction = "";
        String strReturn = "";
		
		 strTemp = (String)request.getAttribute("strAction");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strAction = strTemp.trim();
		 }
		 
		  strTemp = (String)request.getAttribute("strReturn");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strReturn = strTemp.trim();
		 }
		
	    FinanceInfo financeInfo = (FinanceInfo) request.getAttribute("resultInfo");
	    if(financeInfo == null)
	    {
	     financeInfo = new FinanceInfo();
	    } 
	    String strID = (String)request.getAttribute("lID");
	    long lID = -1;
	    if((strID != null) && (strID.length()>0)){
	    	lID = Long.parseLong(strID);
	    }
		 
		//显示文件头
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		%>	

<safety:resources />

<form name="frm" method="post" >
<input type="Hidden" name="lID" value="<%=financeInfo.getID()%>">
<input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
<input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
<input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
<input type="hidden" name="txtisCheck" value="">
<input type="hidden" name="txtTransType" value="<%=lTransType%>">
<input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
<!--以下用于查询返回!-->
<input type="hidden" name="strAction" value="<%=strAction%>">
<input type="hidden" name="strReturn" value="<%=strReturn%>">
<!--以上用于查询返回!-->
<table>
<tr>
<td>
<%
String strTag = null;
strTag = (String)request.getAttribute("ShowTag");
if(strTag != null)
{
%>
<table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr  class="FormTitle">
          <td  width="5" valign="top" align="left" height="25"></td>
          <td width="560"height="25" ><font size="3">委托贷款确认</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr >
          <td colspan="3" height="1"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td height="25" >
            <p>委托贷款指令将于复核后才提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
			  <br>
              该笔交易有待复核人复核！
			  <br>
              <br>
              <b>指令序号为：<%=financeInfo.getID()%> </b><br>
              <br>
            </p>
            </td>
          <td width="5" height="25"></td>
        </tr>
        <tr >
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
<%
}
%>	  
</td>
</tr>
<tr>
<td>    <table width="730" border="0" cellspacing="0" cellpadding="0" class = "top">
          <tr class="FormTitle"> 
            <td  width="5" valign="top" align="left" height="25"></td>
            <td width="560"height="25"  colspan="2"><font size="3" >付款方资料 
              </font></td>
            <td width="5" valign="top" align="right" height="25"> <div align="right"></div></td>
          </tr>
          <tr > 
            <td colspan="4" height="1"></td>
          </tr>
          <tr > 
            <td width="5" height="25"></td>
            <td width="130" height="25"  >付款方账户名称：</td>
            <td width="430" height="25"  ><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
            <td width="5" height="25"></td>
          </tr>
          <tr > 
            <td colspan="4" height="1"></td>
          </tr>
          <tr > 
            <td width="5" height="25"></td>
            <td width="130" height="25" class="graytext">付款方账号：</td>
            <td width="430" height="25" class="graytext"> <%=financeInfo.getPayerAcctNo()%></td>
            <td width="5"></td>
          </tr>
		  <tr >
          <td colspan="5" height="1"></td>
        </tr>
          <tr > 
            <td height="25"></td>
            <td height="25" class="graytext">当前余额：</td>
            <td height="25" class="graytext"><%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(financeInfo.getCurrentBalance())%></td>
            <td></td>
          </tr>
		  <tr >
          <td colspan="5" height="1"></td>
        </tr>
          <tr >
            <td height="25"></td>
            <td height="25" class="graytext">可用余额：</td>
            <td height="25" class="graytext"><%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(financeInfo.getUsableBalance())%></td>
            <td></td>
          </tr>
          <tr > 
            <td colspan="4" height="1"></td>
          </tr>
        </table>
      <br>
</td>
</tr>
<tr>
<td>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = "top">
        <tr class="FormTitle">
          <td  width="5" valign="top" align="left" height="25"></td>
          <td width="560"height="25" ><font size="3" >收款方资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = "top">
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p>贷款账号：</p>
          </td>
          <td width="430" height="25" class="graytext"><%=financeInfo.getPayeeAcctNo()%></td>
          <td width="1" height="25"></td>
        </tr>
    
        <tr >
          <td colspan="4" height="1"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">
            <p> 合同号： </p>
          </td>
          <td width="430" height="25" class="graytext"><%=financeInfo.getLoanContractNo()%></td>
          <td width="1" height="25"></td>
        </tr>
      
          <tr > 
            <td colspan="4" height="1"></td>
          </tr>
          <tr > 
            <td width="5" height="25"></td>
            <td width="130" height="25" class="graytext"> <p>放款日期：</p></td>
            <td width="430" height="25" class="graytext"><%=DataFormat.formatDate(financeInfo.getPayDate())%></td>
            <td width="1" height="25"></td>
          </tr>
		  <tr >
          <td colspan="5" height="1"></td>
        </tr>
          <tr >
            <td height="25"></td>
            <td height="25" class="graytext">贷款余额：</td>
            <td height="25" class="graytext"><%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(financeInfo.getBalance())%></td>
            <td height="25"></td>
          </tr>
		  <tr >
          <td colspan="5" height="1"></td>
        </tr>
          <tr >
            <td height="25"></td>
            <td height="25" class="graytext">利率：</td>
            <td height="25" class="graytext"><%=DataFormat.formatRate(financeInfo.getRate())%>%</td>
            <td height="25"></td>
          </tr>
        </table>

      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = "top">
        <tr  class="FormTitle">
          <td  width="5" valign="top" align="left" height="25"></td>
          <td width="560"height="25"  colspan="3"><font size="3" >划款资料</font></td>
          <td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
            <td height="25"  class="graytext" width="110">本金金额：</td>
          <td width="20" height="25"  class="graytext">
            
          </td>
          <td width="430" height="25"  class="graytext"><%=sessionMng.m_strCurrencySymbol%><%=DataFormat.formatDisabledAmount(financeInfo.getAmount())%></td>
          <td width="5" height="25"></td>
        </tr>
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25"  class="graytext" colspan="2">执行日：</td>
          <td width="430" height="25"  class="graytext"><%=DataFormat.formatDate(financeInfo.getExecuteDate())%></td>
          <td width="5" height="25"></td>
        </tr>
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="graytext"><%=DataFormat.formatString(financeInfo.getNote())%></td>
          <td width="5"></td>
        </tr>
        <tr >
          <td colspan="5" height="1"></td>
        </tr>
      </table>
	    <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = "top">
          <tr class="FormTitle"> 
            <td  width="5" valign="top" align="left" height="25"></td>
            <td width="560"height="25"  colspan="4"><font size="3" >利息费用资料</font></td>
            <td width="5" valign="top" align="right" height="25"> <div align="right"></div></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td width="5" height="25"></td>
            <td width="130" height="25"  class="graytext" colspan="2">&nbsp;</td>
            <td width="430" height="25"  class="graytext">应付金额</td>
            <td width="430"  class="graytext">实付金额</td>
            <td width="5" height="25"></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td width="5" height="23"></td>
            <td width="130" height="23"  class="graytext" colspan="2" nowrap> 
              贷款利息：</td>
            <td width="430" height="23"  class="graytext"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getInterest() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getInterest()):"0.00"%></td>
            <td width="430"  class="graytext"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getRealInterest() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getRealInterest()):"0.00"%></td>
            <td width="5"></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td height="25"></td>
            <td height="25" class="graytext"   width="130" colspan="2"> 
              复利：</td>
            <td height="25" class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getCompoundInterest() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getCompoundInterest()):"0.00"%></td>
            <td class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getRealCompoundInterest() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getRealCompoundInterest()):"0.00"%></td>
            <td></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td height="25"></td>
            <td height="25" class="graytext"   width="130" colspan="2">逾期罚息：</td>
            <td height="25" class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getOverdueInterest() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getOverdueInterest()):"0.00"%></td>
            <td class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getRealOverdueInterest() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getRealOverdueInterest()):"0.00"%></td>
            <td></td>
          </tr>
		   <tr > 
            <td colspan="6" height="1"></td>
          </tr>
		   <tr > 
            <td height="25"></td>
            <td height="25" class="graytext"   width="130" colspan="2">利息税费：</td>
            <td height="25" class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getInterestTax() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getInterestTax()):"0.00"%></td>
            <td class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getRealInterestTax() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getRealInterestTax()):"0.00"%></td>
            <td></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td height="25"></td>
            <td height="25" class="graytext"    width="130" colspan="2"> 
              手续费：</td>
            <td height="25" class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getCommission() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getCommission()):"0.00"%></td>
            <td class="graytext"   width="430"><%=sessionMng.m_strCurrencySymbol%><%=financeInfo.getRealCommission() > 0 ? DataFormat.formatDisabledAmount(financeInfo.getRealCommission()):"0.00"%></td>
            <td></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
          <tr > 
            <td height="25"></td>
            <td height="25" class="graytext"   width="130" colspan="2">利息费用合计：</td>
            <td height="25" class="graytext"  width="430"><%=sessionMng.m_strCurrencySymbol%><%=(financeInfo.getInterest()+financeInfo.getCompoundInterest()+financeInfo.getOverdueInterest()+financeInfo.getSuretyFee())> 0 ? DataFormat.formatDisabledAmount((financeInfo.getInterest()+financeInfo.getCompoundInterest()+financeInfo.getOverdueInterest()+financeInfo.getCommission())):"0.00"%></td>
            <td class="graytext"  width="430"><%=sessionMng.m_strCurrencySymbol%><%=(financeInfo.getRealInterest()+financeInfo.getRealCompoundInterest()+financeInfo.getRealOverdueInterest()+financeInfo.getRealSuretyFee())> 0 ? DataFormat.formatDisabledAmount((financeInfo.getRealInterest()+financeInfo.getRealCompoundInterest()+financeInfo.getRealOverdueInterest()+financeInfo.getRealCommission())):"0.00"%></td>
            <td></td>
          </tr>
          <tr > 
            <td colspan="6" height="1"></td>
          </tr>
        </table>
		<br>
      
 </td>
</tr>
<tr>
<td>

<form name="frmbizapp" method="post">
	<input type="hidden" name="lID" value="<%= lID %>">
	<input type="hidden" name="lUserID" value="<%= sessionMng.m_lUserID %>">
	<input type="hidden" name="lCurrencyID" value="<%= sessionMng.m_lCurrencyID %>">
	<input type="hidden" name="transType" value="<%=lTransType %>">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" >
		<tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> 交易申请处理详情</td>
			<td width="17"><a class=lab_title3></td>
		</tr>
		<tr>
			<td colspan="3">
				<TABLE border="0" width="100%">
					<TBODY>
						<tr>
							<TD width="5">&nbsp;</TD>
							<TD width="*%">
								<TABLE width="100%" id="flexlist"></TABLE>
							</TD>
							<TD width="5">&nbsp;</TD>
						</tr>
					</TBODY>
				</TABLE>
			</td>
		</tr>
	</table>		
</form>	
 <br>
	  <br>
	  <table border="0" width="730" cellspacing="0" cellpadding="0">
        <tr>
		<td width="730" align="right">

<%
/* 从指令查询页面过来的 */
String search="";
if(request.getAttribute("search")!=null){
search=(String)request.getAttribute("search");
}
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>

            <input class=button1 name=add type=button value=" 修  改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">

			<input class=button1 name=add type=button value=" 删  除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
            <% if ( strTag != null) {%>
            <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" 返  回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
            <%}%>

				<% }
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1)&!search.equals("1") ) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复  核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
			<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&!search.equals("1") ) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复  核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
				<input class=button1 name=add type=button value=" 返  回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
            <% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())&!search.equals("1")) {// 已复核、登录人＝复核人 %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">

		<%}
			/* 签认及提交 */
		  		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)&!search.equals("1")){// 已复核、需要被登录人签认=true %>

            	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
			 <input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

		<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())&!search.equals("1")){// 已签认、登录人＝签认人 %>

            	<!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		   <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <%
					///add by liuguang 2007-10-19  设置业务是否需要打印的提示
					if (Config.getBoolean(ConfigConstant.EBANK_ISPRINT, false)) {
			%>
		   <input type="Button" class="button1" value=" 打  印 " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		   <%if(a!=null && !a.contains(strStatus))
               { %>
			   disabled
			   <%
			   }
			   %>
		   >
		   <%
				}
				%>
               <!--
            <img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();">
         	<%}%>
         </td>
        </tr>
      </table>
	  </td>
	  </tr>
</table>
</form>
<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("../../common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
}

	/*返回处理函数 */
	function returnme()
	{
		if(confirm("是否返回？"))
		{
		<%
		if(strAction != null && strAction.equals("query") && strReturn != null)
		{
		%>	
		   frm.action= "<%=strReturn%>";
		<%
		}
		else
		{
		%>	
			frm.lID.value="";
			frm.action="../loanrepayment/l011-c.jsp";
		<%
		}
		%>	
			showSending();
			frm.submit();
		}
	}

	/* 修改处理函数 */
	function updateme()
	{
		if(confirm("是否修改？"))
		{
			frm.strAction.value="toModify";
			frm.action="../loanrepayment/l011-c.jsp";
			showSending();
			frm.submit();
		}	
	}
	/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		showSending();
		frm.action="../loanrepayment/l017-c.jsp";
		frm.submit();
	}
		/* 复核匹配函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="../check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		frm.submit();
	}	
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		frm.action="../check/C415.jsp";
		frm.txtisCheck.value = "1";
		frm.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			frm.submit();
		}
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frm.action="../sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		frm.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frm.action="../sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			frm.submit();
		}
	}
	$(document).ready(function() {
	 	$(".FormTitle").click(function(){
	      	$("#iTable").toggle();
	    });
	    $("#flexlist").flexigridenc({
			colModel : [
				{display: '序列号',  name : 'ncurrencyid', width : 200, sortable : false, align: 'center'},
				{display: '用户',  name : 'payeracctno', width : 230, sortable : false, align: 'center'},
				{display: '工作描述',  name : 'payername', width : 230, sortable : false, align: 'center'},
				{display: '时间和日期',  name : 'ntranstype', width : 230, sortable : false, align: 'center'}
			],//列参数
			//title:'交易申请处理详情',
			classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//要调用的方法
			page : <%=flexiGridInfo.getFlexigrid_page()%>,
			rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
			//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
			//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
			usepager : false,
			userFunc : getFormData,
			height : 100
		});
		
	});
	
	function getFormData() 
	{
		return $.addFormData("frmbizapp","flexlist");
	}
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
<%@ include file="/common/SignValidate.inc" %>
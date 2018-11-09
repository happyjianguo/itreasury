<%--
/*
 * 程序名称：vAppN011-v.jsp
 * 功能说明：通知支取提交页面
 * 作　　者：ypxu
 * 完成日期：07-04-25
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"
%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[通知支取]";
%>

<%
	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getAttribute("menu");
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
    long lSourceType = 0;
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
       	
    //从"我的工作"传递的控制变量
	String strTempAction = "";
	if (request.getAttribute("strTempAction") != null) 
	{
		strTempAction = (String)request.getAttribute("strTempAction");
	}
%>

<%
	/* 实例化信息类 */
	FinanceInfo financeInfo = null;

	/* 用户登录检测与权限校验及文件头显示 */
	try 
	{
        /* 用户登录检测 
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
        }*/

		/* 从请求中获取信息 */
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		
		
		//取消审批列表页面获取操作类型
		String operation = "-1";
		if(request.getParameter("operation")!=null && ((String)request.getParameter("operation")).length()>0 && !(((String)request.getParameter("operation")).equals("null")))
		{
			operation =(String)request.getParameter("operation");
		}
		
		
		
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
%>
 <% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} 
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
/*		if(blnUseITrusCert)
		{
			String[] nameArray = OBSignatureConstant.NotifyDepositDraw.getSignNameArray();
			String[] valueArray = OBSignatureConstant.NotifyDepositDraw.getSignValueArrayFromInfo(financeInfo);		
			if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused()){
				//特殊处理
			 	valueArray[7] = "-1";
			}
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
				 
			SignatureInfo signatureInfo = new SignatureInfo();
			signatureInfo.setNameArray(nameArray);
			signatureInfo.setValueArray(valueArray);
			signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
			
			blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}*/
%>
<safety:resources />

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<isswf:init/>

<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%} %>
<table width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">通知存款支取</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	    </td>
	  </tr>
	</table>
	<br/>
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> 通知支取确认</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
    </table> 
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <!-- 
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->          
		 <!-- td width="560" class=FormTitle height="25" colspan="3"><font size="3" >通知支取确认</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"></td>
        </tr>
        -->
        
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>通知支取将<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>于复核后才<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>于审批并复核后才<%} }%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
              <!--br>
              已通知复核人复核！
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <%if(!OBFSWorkflowManager.isAutoCheck()){
			  	if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){
			  %>
			  <br>
              该笔交易有待复核人复核！
			  <br>
			  <%}
			  else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){
			  %>
			  <br>
              该笔交易有待审批人审批！
			  <br>
			  <%}
			  }
			  %>
              <br>
              <b>指令序号为：<%= financeInfo.getID() %></b><br>
              <br>
            </p>
            </td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
      
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">通知账户资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table> 
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <!-- 
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->          
		<!-- td width="560"height="25" class=FormTitle colspan="4"><font size="3" >通知账户资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        -->
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25"  class="MsoNormal">通知账户名称：</td>
          <td width="430" height="25"  class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
       
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25" class="MsoNormal">通知账号：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getPayerAcctNo() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25" class="MsoNormal">通知存款单据号：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getDepositNo() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
		
      </table>
      <br>
      <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">通知账户资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table>
     <!-- 
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <!-- td width="560"height="25" colspan="3" class=FormTitle><font size="3" >收款方资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
      </table>
      -->
     <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr class="MsoNormal">
          <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">本金：</span></p>
          </td>
          <td width="240" height="25"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇款方式：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getFormatRemitType() %></td>
        </tr>
        <tr id="payeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNo" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getPayeeAcctNo() %></td>
        </tr>
        <tr id="payeeBankNoNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNoName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方名称：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getPayeeName() %></td>
        </tr>
        <tr id="payeePlaceLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeePlace" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入地：</span></p>
          </td>
          <td width="240" height="25"> <%= ((financeInfo.getPayeeProv() == null) ? "　" : financeInfo.getPayeeProv()) + "省" + ((financeInfo.getPayeeCity() == null) ? "　" : financeInfo.getPayeeCity()) + "市（县）" %> 
          </td>
        </tr>
        <tr id="payeeBankNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入行名称：</span></p>
          </td>
          <td width="240" height="25"> <%= (financeInfo.getPayeeBankName() == null) ? "" : financeInfo.getPayeeBankName() %> 
          </td>
        </tr>
        <tr id="line1" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        
      </table>  
		  </td>
		  <td width="1" class="MsoNormal">
		  </td>
		  <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">利息：</span></p>
          </td>
          <td width="240" height="25"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇款方式：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getFormatInterestRemitType() %></td>
        </tr>
        
        <tr id="InterestPayeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNO" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getInterestPayeeAcctNo() %></td>
        </tr>
        <tr id="InterestPayeeBankNoNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNoName" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方名称：</span></p>
          </td>
          <td width="240" height="25"><%= financeInfo.getInterestPayeeName() %></td>
        </tr>
        <tr id="InterestPayeePlaceLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeePlace" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入地：</span></p>
          </td>
          <td width="240" height="25"> <%= ((financeInfo.getInterestPayeeProv() == null) ? "　" : financeInfo.getInterestPayeeProv()) + "省" + ((financeInfo.getInterestPayeeCity() == null) ? "　" : financeInfo.getInterestPayeeCity()) + "市（县）" %> 
          </td>
        </tr>
        <tr id="InterestPayeeBankLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBank" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">汇入行名称：</span></p>
          </td>
          <td width="240" height="25"> <%= (financeInfo.getInterestPayeeBankName() == null) ? "" : financeInfo.getInterestPayeeBankName() %>	
          </td>
        </tr>
        
      </table>  
		  </td>
        </tr>
      </table>
	   
	  <br>
	  <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> 划款资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table>
      <table width="80%" border="0" cellspacing="0" cellpadding="0"class ="normal" align="">
        <!-- 
        <tr  class="tableHeader">
          <!--td  width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <!-- td width="560"height="25"  class=FormTitle colspan="5"><font size="3" >划款资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        -->
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25"  class="MsoNormal" width="110">金额：</td>
          <td width="20" height="25"  class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25"  class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="60" height="25"  class="MsoNormal" colspan="2">执行日：</td>
          <td width="430" height="25" align="left"  class="MsoNormal"><%= financeInfo.getFormatExecuteDate() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getNote()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝      		   
		{ 
%>
      <table align="" width="80%" border="0" cellspacing="0" cellpadding="0" >
	  	<tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">交易申请处理详情</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
     </table>
     <!-- 
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr  class="tableHeader">
          <!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
          <!-- td colspan="5" height="22" class=FormTitle bgcolor="#0C3869"><font size="3" ><b>交易申请处理详情</b></font></td>
          <!--td width="5" height="22" bgcolor="#0C3869"></td>
        </tr>        
      </table>
      -->
      <table align="" width="80%" border="0" class="normal">
        <tr class="tableHeader">
          <td width="50" bgcolor="#456795" height="19" class="ItemTitle">
            <p align="center"><font size="2" >序列号</font></p>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle" class="ItemTitle" width="150">
            <div align="center">用户</div>
          </td>
         
          <td bgcolor="#456795" height="19" valign="middle" class="ItemTitle" width="150">
            <div align="center">工作描述</div>
          </td>
          
          <td bgcolor="#456795" height="19" valign="middle" class="ItemTitle" width="217">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        
        <tr valign="middle">
          <td width="50" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center">1</div>
          </td>
         
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center">录入</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="215" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        
<% 
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
        	{ 
%>
        <tr valign="middle">
          <td width="50" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center" class="ItemBody">2</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center">复核</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="217" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        
<% 
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="50" align="left" bgcolor="#C8D7EC" class="ItemBody" height="25">
            <div align="center" class="ItemBody">3</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="150" height="25">
            <div align="center">签认</div>
          </td>
         
          <td bgcolor="#C8D7EC" class="ItemBody" width="217" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				} 
%>
<%
 			} 
%>
 </table>
 <br>

<%
 		} 
%>
<%-- <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">链接附件</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>--%>
	            <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	            <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " 上 传 " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'/>
         <%--  </td>
        </tr>
      </table>--%>
	  <br>
      <form name="frm" method="post">
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px">历史审批意见</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
	  </table>
	  <table border="0" align="" width="80%" cellspacing="0" cellpadding="0" class="normal">
	  <TR>
		  <TD colspan="3">
			 <%--<iframe src="/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		 	<fs:HistoryOpinionFrame
					  strTransNo='<%=financeInfo.getID()>0?String.valueOf(financeInfo.getID()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					/>
		  </TD>
	  </TR>
	  <tr>
		    <%
		    if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
			{
				//added by mzh_fu 2007-05-03 对已审批、已复核的任务，不再显示审批意见录入框
				if(financeInfo.getStatus() != OBConstant.SettInstrStatus.APPROVALED && financeInfo.getStatus() != OBConstant.SettInstrStatus.CHECK){

						String strMagnifierNameRemark = "审批意见";
						String strFormNameRemark = "frm";
						String strMainPropertyRemark = "";
						String strPrefixRemark = "";
						String[] strMainNamesRemark = {com.iss.inut.workflow.constants.Constants.WF_ADVISE};
						String[] strMainFieldsRemark = {"Description"};
						String strReturnInitValuesRemark="";
						String[] strReturnNamesRemark = {"txtsOptionID"};
						String[] strReturnFieldsRemark = {"id"};
						String[] strReturnValuesRemark = {""};
						String[] strDisplayNamesRemark = {"审批意见编号","审批意见内容"};
						String[] strDisplayFieldsRemark = {"Code","Description"};
						int nIndexRemark = 1;
						String strSQLRemark = "select * from sys_approvalopinion where officeid="+sessionMng.m_lOfficeID+" and currencyid="+sessionMng.m_lCurrencyID+" and moduleid="+sessionMng.m_lModuleID+" and statusid="+Constant.RecordStatus.VALID;
						String strMatchValueRemark = "Description";
						//String[] strNextControlsRemark = {"strGeneratorCapacity","isShareHolder"};
						String strNextControlsRemark = "checkNextUser";
						String strTitleRemark = "审批意见";
						String strFirstTDRemark="align='center'";
						String strSecondTDRemark="colspan='2'";	
						Magnifier.showTextAreaCtrlForEbank(out,strMagnifierNameRemark,strFormNameRemark,strPrefixRemark,strMainNamesRemark,strMainFieldsRemark,strReturnNamesRemark,strReturnFieldsRemark,strReturnInitValuesRemark,strReturnValuesRemark,strDisplayNamesRemark,strDisplayFieldsRemark,nIndexRemark,strMainPropertyRemark,strSQLRemark,strMatchValueRemark,strNextControlsRemark,strTitleRemark,strFirstTDRemark,strSecondTDRemark);
				}
			}
			%>   
		</tr> 
		<tr>
		<td colspan=3><br></td>
		</tr>
	    </table>
	    <br/>
		<table width="80%"><tr><td align="right">
			<%
	    	//当从取消审批列表进入时,显示取消审批按钮
	    	if(operation!=null && operation.length()>0 && Long.parseLong(operation)==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
	    		{
	    	%>
	    		<input class="button1" name="ca" type="button" value=" 取消审批 " onClick="javascript:cancelApproval()" onKeyDown="javascript:cancelApproval()">
			     <isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval();" />
			<%	}
			else
				{
			%>
			     <isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval();" />
			<%	}
			if(strTempAction.equals("finished") || strTempAction.equals("cancelApproval"))
			{
			%>
			<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:history.back();" onKeyDown="javascript:history.back();"/>
			<%  }
			else
			{
			 %>
			<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:doReturn();" onKeyDown="javascript:doReturn();"/>
			<%} %>
            </td>
        </tr>
	  <%--
        <tr>
		<td width="730" align="right">

<%
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>

<!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
		    <input class=button1 name=add type=button value=" 修改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">

			<input class=button1 name=add type=button value=" 删除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->

            
            <% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO ) {%>            
			<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" 返回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">
            <%}%>

		<% }
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// 已确认、登录人<>确认人 %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">
			<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// 已确认、登录人<>确认人 %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
		  <% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// 已复核、登录人＝复核人 %>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">
		<%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// 已复核、需要被登录人签认=true %>

			 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
			 <input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">

		<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// 已签认、登录人＝签认人 %>

				 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		  
		   <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <input type="Button" class="button1" value=" 打印 " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		   <%
			   if(a!=null && !a.contains(strStatus))
               {
			   // 开放打印权限 2007/3/16 modify by wjliu 把disabled去掉
		   %>
				
		   <%
			   }
			%>
		   >
               <!--
            <img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" 关闭 " width="46" height="18"   onclick="window.close();">
         	<%}%>
		 </td>
        </tr>
        --%>
      </table>
      <br/>

	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="txtTransType" value="<%=OBConstant.QueryInstrType.NOTIFYDEPOSITDRAW %>">

	 <!-- 签名用字段 -->
	  <input type="hidden" name="sNotifyDepositNoCtrl" value="<%=financeInfo.getDepositNo() %>">
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nInterestPayeeAccountID" value="<%=financeInfo.getInterestPayeeAcctID() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">	  
	  <input type="hidden" name="nRemitTypeHidden" value="<%=financeInfo.getRemitType() %>">	  
	  <input type="hidden" name="nInterestRemitTypeHidden" value="<%=financeInfo.getInterestRemitType() %>">
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="strAction" value="">
	  
	  	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>">  
	  </form>
<!--presentation end-->

<script language="javascript">
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//审批流审批
	function doApproval()
	{
		if(frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
		{
			alert("请输入审批意见");
			frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
			return;
		}
	document.frm.strAction.value = <%=OBConstant.SettInstrStatus.DOAPPRVOAL%>;
		if (confirm("是否审批?")) 
		{
		
			//add by mingfang 数字签名 2007-05-25 
			<%if(blnUseITrusCert){
				OBSignatureConstant.NotifyDepositDraw.outSignNameArrayToView(out);
				OBSignatureConstant.NotifyDepositDraw.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			frm.action="<%=strContext%>" + '/capital/notify/n011-c.jsp?operate=doApproval';
			frm.submit();
		}
	}


//取消审批   add by mingfang
	function cancelApproval()
	{
	
		document.frm.strAction.value = <%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>;
		if (confirm("是否取消审批?")) 
		{
			<%if(blnUseITrusCert){
				OBSignatureConstant.NotifyDepositDraw.outSignNameArrayToView(out);
				OBSignatureConstant.NotifyDepositDraw.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>		
			frm.action="<%=strContext%>" + '/capital/notify/n011-c.jsp?operate=cancelApproval';
			frm.submit();
		}
	}	
	
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("../../common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
}


	/* 菜单控制处理函数 */
	function showMenu()
	{
		<%  if (lShowMenu == OBConstant.ShowMenu.NO)
		    {   %>
		        frm.menu.value="hidden";
		<%  }   %>
	}
	/*返回处理函数 */
	function returnme()
	{
		frm.lInstructionID.value = "";
		frm.action="../notify/n011-c.jsp";
		frm.submit();
	}

    function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}

	/* 确认处理函数 */
	function confirmme()
	{
		//showMenu();
		frm.action="../notify/C13.jsp";
		frm.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		frm.action="../notify/n011-c.jsp";
		frm.submit();
	}
	/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		//showMenu();
		frm.action="../notify/n015-c.jsp";
		showSending();
		frm.submit();
	}
	/* 复核匹配函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="../check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		frm.action="../check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("是否取消复核？"))
		{
			//showMenu();
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frm.action="../sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("是否取消签认？"))
		{
			//showMenu();
			frm.action="../sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}
	/* 打印处理函数 */
	function printme()
	{
		frm.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frm.target="new_window";
		frm.submit();
		frm.target="";
	}

</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(IException e)
	{
		OBHtml.showExceptionMessage(out,sessionMng, e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
<!--
/*
 * 程序名称：vAppF003.jsp
 * 功能说明：定期开立提交审批页面
 * 作　　者：ypxu
 * 完成日期：07-04-25
 */
-->

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				  com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				  com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ include file="/common/common.jsp" %>
<!--Header end-->
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期开立]";
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
    String type = "";
    String _type = request.getParameter("type");
    if ((type != null) && (type.length()>0))
	{
    	type = _type;
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
       	
    //取消审批列表页面获取操作类型
	String operation = "-1";
	if(request.getParameter("operation")!=null && ((String)request.getParameter("operation")).length()>0 && !(((String)request.getParameter("operation")).equals("null")))
	{
		operation =(String)request.getParameter("operation");
	}
		
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
        System.out.println("financeInfo.getNDepositBillStatusId()的值是================"+financeInfo.getNDepositBillStatusId());
        boolean isbill = false;
        if(financeInfo.getNDepositBillInputuserId() > 0)
        	isbill = true;
        if(isbill)
        	strTitle = "[换开定期存单]";
        else
        	strTitle = "[定期开立]";
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		long doApproval = OBConstant.SettInstrStatus.DOAPPRVOAL;
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		if(blnUseITrusCert)
		{
			String[] nameArray = OBSignatureConstant.OpenFixDeposit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.OpenFixDeposit.getSignValueArrayFromInfo(financeInfo);		
			if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused()){
				//特殊处理
			 	valueArray[5] = "-1";
			}
			//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setNameArray(nameArray);
		signatureInfo.setValueArray(valueArray);
		signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
		
		blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);				
		}
%>
<safety:resources />
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

<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
      <form name="frm" method="post">
      <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">定期开立</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>
</table>
<br/>
	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"><%if(isbill) {%>换开定期存单确认<%}else{ %>定期开立确认<%} %></td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	  <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr >
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p><br><%if(isbill) {%>换开定期存单确认<%}else{ %>定期开立确认<%} %>将<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>于复核后才<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>于审批并复核后才<%} }%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
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
          <td width="5" height="25"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
      </table>
      <br>
	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 活期账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>  
        </table></td>
      </tr>
    </table>
	  <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          
		  <td width="130" height="25" class="MsoNormal">活期账户名称：</td>
          <td width="430" height="25" class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
		<!--
        <tr >
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">银行账号：</td>
          <td width="430" height="25" class="graytext"><%= financeInfo.getPayerBankNo() %></td>
          <td width="5"></td>
        </tr>
		-->
        <tr class="MsoNormal">
          <td colspan="4" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          
    <td width="130" height="25" class="MsoNormal">活期账号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 定期账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>

      
  <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal"> <p>定期账号：</p></td>
      <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo().toString()) %></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    
<%if(Config.getBoolean(ConfigConstant.SETT_TRAN_FIXED_ISAUTOCONTINUE,true))	

{ %>
		<%
			if(financeInfo.getIsAutoContinue() == 1)
			{
		%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					是否自动续存：
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">是
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>
		<%
				if(financeInfo.getAutocontinuetype() == 1)
				{
		%>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							自动续存类型：
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal">本息续存
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>		
		<%			
				}else if(financeInfo.getAutocontinuetype() == 2) {
		%>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							自动续存类型：
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal">本金续存
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							利息转至活期账户：
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getAutocontinueaccountid()) %>
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>
				<tr class="MsoNormal">
					<td width="5" height="25" class="MsoNormal"></td>
					<td height="25" width="130" class="MsoNormal">
						<p>
							活期客户名称：
						</p>
					</td>
					<td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNameByID(financeInfo.getAutocontinueaccountid())%>
					</td>
					<td width="10" height="25" class="MsoNormal"></td>
				</tr>										
		<%			
				}		
			}else if(financeInfo.getIsAutoContinue() == 2) {
		%>
		<tr class="MsoNormal">
			<td width="5" height="25" class="MsoNormal"></td>
			<td height="25" width="130" class="MsoNormal">
				<p>
					是否自动续存：
				</p>
			</td>
			<td width="430" height="25" class="MsoNormal">否
			</td>
			<td width="10" height="25" class="MsoNormal"></td>
		</tr>			
		<%		
			} 
		%> 
<%} %>   
    <!-- 
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" class="MsoNormal" width="141">定期留存金额：</td>
      <td class="MsoNormal" width="26">
      <div align="right"><%//= sessionMng.m_strCurrencySymbol %>&nbsp;</div>
      </td>
      <td width="547" height="25" class="MsoNormal"><%//= financeInfo.getMamOuntForTrans() %></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
     -->
    <tr class="MsoNormal"> 
      <td colspan="6" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal"> <p>定期存款期限：</p></td>
      <td width="430" height="25" class="MsoNormal"><%=financeInfo.getFixedDepositTime() > 10000?financeInfo.getFixedDepositTime()-10000:financeInfo.getFixedDepositTime() %><%=(financeInfo.getFixedDepositTime() > 10000)?"天":"个月"%></td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <%-- 
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal"> <p>到期是否续存：</p></td>
      <td width="547" height="25" class="MsoNormal">
        <% long isFixContinue =financeInfo.getIsFixContinue();System.out.print("MMMMMMMMMMMMMM"+financeInfo.getIsFixContinue()); if(isFixContinue > 1){%>
        到期不续存
        <%}else{  %>
        到期续存
        <% }%>
      </td>
      <td width="10" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="6" height="25" class="MsoNormal"></td>
      <td height="25" colspan="2" class="MsoNormal" >备注：</td>
      <td width="547" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getFixEdremark()) %></td>
      <td width="10" class="MsoNormal"></td>
    </tr>
    --%>
  </table>

      <br>
      	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width="80%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">金额：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">执行日：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatExecuteDate() %></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="MsoNormal"><%= DataFormat.formatString(financeInfo.getNote()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      </table>
	  <br>
<% 
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
			(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH ) || //已完成
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) || //已拒绝 
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SAVE) || // 已确认,未复核(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) || // 已复核(换开)
			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) || // 已签认(换开)
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL) || //处理中(换开)
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH ) || //已完成(换开)
        	(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝(换开)  		   
		{
%>
		 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="180" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td style="width:160px" class="lab_title2"> 交易申请处理详情</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width=80% border="1" align="" cellpadding="0" cellspacing="0" class=normal >
       <thead>
        <tr >
          <td width="63" height="19" class="ItemTitle">
            <p align="center">序列号</p>
          </td>
          
          <td  height="19" valign="middle"  width="190" >
            <div align="center">用户</div>
          </td>
         
          <td  height="19" valign="middle"  width="198" >
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19" valign="middle" width="269">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        </thead>
        <tr valign="middle">
          <td width="63" align="left"  height="25" >
            <div align="center">1</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%if(isbill){%><%= NameRef.getUserNameByID(financeInfo.getNDepositBillInputuserId()) %><%}else{%><%= financeInfo.getConfirmUserName() %><%} %></div>
          </td>
          
          <td   width="198" height="25">
            <div align="center">录入</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%if(isbill){%><%=financeInfo.getDtDepositBillInputdate().toString().substring(0,19)%><%}else{ %><%= financeInfo.getFormatConfirmDate() %><%} %></div>
          </td>
        </tr>
        
<% }
			if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝 
        		&&(financeInfo.getNDepositBillInputuserId() == 0))
        	{
%>
        <tr valign="middle">
          <td width="63" align="left"   height="25">
            <div align="center">2</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName() %>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">复核</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        <% }
			if (
        		((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) || // 已复核(换开)
				(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) || // 已签认(换开)
        		(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL) || //处理中(换开)
        		(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)|| //已完成(换开)
        		(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))&& //已拒绝(换开)
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && 
        		(financeInfo.getDtDepositBillCheckdate() != null)) 
        	{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">2</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%if(isbill){%><%= NameRef.getUserNameByID(financeInfo.getNDepositBillCheckuserId()) %><%}%>
            </div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">复核</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%if(isbill){%><%=financeInfo.getDtDepositBillCheckdate().toString().substring(0,19)%><%}%></div>
          </td>
        </tr>
       
<% }
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE))&&//已拒绝
        			(financeInfo.getSignUserName() != null)&&
        			(financeInfo.getNDepositBillInputuserId() == 0))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">签认</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatSignDate() %></div>
          </td>
        </tr>
<% 
				}
%>
<% 
				if (((financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) || // 已签认(换开)
        			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.DEAL) || //处理中(换开)
        			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.FINISH)|| //已完成(换开)
        			(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.REFUSE))&&//已拒绝(换开)
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)&& 
            		(financeInfo.getDtDepositBillSignDate() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td  width="190" height="25">
            <div align="center"><%if((isbill)){%><%=NameRef.getUserNameByID(financeInfo.getNDepositBillSignUserID()) %><%}%></div>
          </td>
          
          <td  width="198" height="25">
            <div align="center">签认</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%if((isbill)){%><%=financeInfo.getDtDepositBillSignDate().toString().substring(0,19)%><%}%></div>
          </td>
        </tr>
<% 
			}
%>
 </table>

<%
	if(isbill){
%>     
	<table width="80%" border="0" align="" cellspacing="0" cellpadding="0" class=normal>
        <tr class="tableHeader">
          <!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
          <td colspan="2" height="22" class=FormTitle><font size="3" ><b>换开定期存单详情</b></font></td>
          <!--td width="5" height="22" bgcolor="#0C3869"></td-->
        </tr>

        <tr>
          <td width="23%" height="25" class="MsoNormal">&nbsp;换开定期存单录入摘要：</td>
          <td width="77%"><%=financeInfo.getSDepositBillAbstract() == null?"":financeInfo.getSDepositBillAbstract() %></td>
        </tr>
        <%if(financeInfo.getNDepositBillStatusId() != OBConstant.SettInstrStatus.SAVE || lCheckType > -1) {%>
        <tr>
          <td width="23%" height="25" class="MsoNormal">&nbsp;换开定期存单复核摘要：</td>
          <td width="77%"><span class="MsoNormal">
            <%if(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK || financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN ) {%>
         <%=financeInfo.getSDepositBillCheckAbstract() == null?"":financeInfo.getSDepositBillCheckAbstract() %>
         <%} else{%><textarea name="lAbstractID" class="box" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield ='';" ><%=financeInfo.getSDepositBillCheckAbstract() == null?"":financeInfo.getSDepositBillCheckAbstract() %></textarea>
         <%} %>
          </span></td>
        </tr>
        <%} %>
      </table>
      <br/>
      <%} %>
	
	  <%--
	  <table align="center" width="80%" border="0" cellspacing="0" cellpadding="0" >
	   <tr>
	      <td width="1"><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
	      <td width="100" background="/webob/graphics/lab_conner2.gif" class="txt_til2">链接附件</td>
	      <td width="650"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
	   </tr>
      </table> 
      <table align="center" width="80%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
           
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = " 上 传 " 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'/>
      <%--     </td>
        </tr>
      </table>
      --%>
<br/>
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">历史审批意见</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
	  <table border="0" width="80%" cellspacing="0" cellpadding="0" align="" class=normal>
	  <TR>
		  <TD colspan="3">
		<%-- 	 <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>&&transType=<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
		 --%>
		  <fs:HistoryOpinionFrame
					  strTransNo='<%=financeInfo.getID()>0?String.valueOf(financeInfo.getID()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.OPENFIXDEPOSIT%>'
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
						String strFirstTDRemark="align='left'";
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
		<table width="80%">
		<tr>
		<td colspan="7"></td>
		<td align="right">
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
			<%	
			}
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
		<td width="80%" align="right">

<%
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>

            <input class=button1 name=add type=button value=" 修改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;

			<input class=button1 name=add type=button value=" 删除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->
            
            <% if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {%>
            <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" 返回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
            <%}%>

		<% }
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
			<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  <% }
			/* 换开复核 */
			System.out.println("sessionMng.m_lUserID="+sessionMng.m_lUserID);
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID != financeInfo.getNDepositBillInputuserId()) && (lCheckType == 1) && isbill ) {// 已确认、登录人<>确认人 %>

           		<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  <% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// 已复核、登录人＝复核人 %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
		  	/* 换开取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID == financeInfo.getNDepositBillCheckuserId()) && isbill ) {// 已完成、登录人＝复核人 %>

           		<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// 已复核、需要被登录人签认=true %>

            	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* 换开签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.CHECK) && isbill  && (financeInfo.getIsNeedSign() == true)){// 已完成、需要被登录人签认=true %>

           	 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
				 <input class=button1 name=add type=button value=" 签认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// 已签认、登录人＝签认人 %>

            	 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

	      <%} 
			/* 换开取消签认 */
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH) && (sessionMng.m_lUserID == financeInfo.getNDepositBillSignUserID()) && (financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SIGN) && isbill ){// 已完成、登录人＝签认人 %>

            	 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->

				 <input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

	      <%
	       } 
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus();
               %>
		   <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"-->
		   <input type="Button" class="button1" value=" 打印 " width="46" height="18"   onclick="javascript:PrintConsignVoucher()" 
		   <%  if(a!=null && !a.contains(strStatus))
               {%>
					disabled
			   <%}
			%>>&nbsp;&nbsp;

               <!--
            <img src="\webob\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
			<input type="Button" class="button1" value=" 关闭 " width="46" height="18"   onclick="window.close();">&nbsp;&nbsp;
         	<%}%>
		 </td>
        </tr>
        --%>
      </table>
      <br/>

	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="billstatusid" value="<%=financeInfo.getNDepositBillStatusId()%>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="strAction" value="">

	  <!-- add by mingfang 2007-05-24 -->
	  <!-- 签名用字段 -->
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>">
	   <input type="hidden" name="nFixedDepositTime" value="<%=financeInfo.getFixedDepositTime() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  

	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	   
	  <safety:certFilterHidden />
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
	    frm.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
		if (confirm("是否审批?")) 
		{
		
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f003-c.jsp?operate=doApproval';
			frm.submit();
		}
	}	

//取消审批   add by mingfang
	function cancelApproval()
	{
	
	 	frm.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
		if (confirm("是否取消审批?")) 
		{
			//add by mingfang 2007-05-24 
			<%if(blnUseITrusCert){
				OBSignatureConstant.OpenFixDeposit.outSignNameArrayToView(out);
				OBSignatureConstant.OpenFixDeposit.outSignValueArrayToView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f003-c.jsp?operate=cancelApproval';
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
		frm.lInstructionID.value = "-1";
		frm.action="../fixed/f001-c.jsp";
		frm.submit();
	}
	
	function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}
	
	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		frm.action="../fixed/f001-c.jsp?isupdate=isupdate";
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
		frm.action="../fixed/f005-c.jsp?operate=delete";
		showSending();
		frm.submit();
	}
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		frm.action="../check/C415.jsp?fuhe=fuhe";
		frm.txtisCheck.value = "1";
		frm.txtTransType.value = "2";
		frm.submit();
	}
	
	/* 复核处理函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="../check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
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
		if (confirm("是否取消？"))
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
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
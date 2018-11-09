<!--
/*
 * 程序名称：check_v011.jsp
 * 功能说明：定期续存输出页面
 * 作　　者：
 * 完成日期：2010年10月12日
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
				 com.iss.itreasury.safety.info.*,
				 com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"
%>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp"%>
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[到期续存]";
%>

<%

com.iss.itreasury.ebank.util.NameRef  eBankNameRef = new com.iss.itreasury.ebank.util.NameRef();

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

      	
	//是否需要审批流 by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if(isNeedApproval == null) isNeedApproval = "";
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
        //信息重新从数据库读取，主要用于更新dtmodify字段   add by zhanglei  2010.06.01
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		financeInfo=financeInstr.findByID(financeInfo.getID(), sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		
		
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		String strTransNo = financeInfo.getID() + "";
		
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;

		
		//CFCA证书验签
		//boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);		
    	String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   		boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);	
		if(isUseCertification)
		{
			
			String temp = (String)request.getParameter("blnNotBeFalsified");
			if(temp!=null&&temp.trim().length()>0)
			{
				blnNotBeFalsified = new Boolean(temp).booleanValue();
			}
		}	
			
%>


<%if(blnUseITrusCert && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%} else if(isUseCertification&&!blnNotBeFalsified){%>
	<script type="text/javascript">
		showRecordBeFalsifiedPrompt(); 
	</script>
<%} %>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

 <form name="frm" method="post">
<table width="98%"  class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
				<TR>
			       <td class=title><span class="txt_til2">&nbsp;&nbsp;到期续存</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
<br/>
<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
     
     <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
      <%
		if (lSourceType != 1 && financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) 
		{
%>


	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2">  到期续存确认</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	  <table width="98%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr >
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>到期续存将<%
	        if(isNeedApproval.equals("true"))
	        {
	        	%>于审批后<%
	        	if(!OBFSWorkflowManager.isAutoCheck())
	        	{
	        		%>等待复核人复核！<%
	        	}
	        	else
	        	{
	        		%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        	}
	        }
	        else if(!OBFSWorkflowManager.isAutoCheck())
	        {
	        	%>于复核后才提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
	        else
	        {
	        	%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<%
	        }
        %>
        <br>
              <!--br>
              已通知复核人复核！
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			 <%if(isNeedApproval.equals("true"))
			  	{
			  		%><br>该笔交易有待审批人审批！<br><%
			  	}else if(!OBFSWorkflowManager.isAutoCheck())
			  	{
			  		%><br>该笔交易有待复核人复核！<br><%
			 	}%>
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
<%
		}
%>
	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" nowrap> &nbsp;&nbsp;到期存款账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
	  <table width="98%" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
      <tr class="MsoNormal">
          <td width="5" height="25"></td>  
    <td width="150" height="25" class="MsoNormal">定期账户名称：</td>
          <td width="430" height="25"class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="150" height="25"class="MsoNormal">定期账号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
     	<tr class="MsoNormal">
          <td width="5" height="25"></td>
    	<td width="150" height="25"class="MsoNormal">定期存单号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getSDepositBillNo().toString()) %></td>
          <td width="5"></td>
        </tr>
<!-- 自动续存 -->
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
<!-- End -->		        
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2">&nbsp; 到期续存</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>

      
  <table width="98%" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="150" height="25" class="MsoNormal"> <p>金额：</p></td>
      <td width="430" height="25" class="MsoNormal">
       <%=sessionMng.m_strCurrencySymbol%> <%= financeInfo.getFormatAmount() %>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="150" height="25" class="MsoNormal"> <p>定期存款期限：</p></td>
      <td width="430" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod()>10000?financeInfo.getSDepositBillPeriod()-10000:financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"天":"个月"%></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="150" height="25" class="MsoNormal"> <p>新定期存款起始日：</p></td>
      <td width="430" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;新定期存款到期日：<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
  </table>

      <br>
      	 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> &nbsp;&nbsp;利息处理</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width="98%" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%if(financeInfo.getSDepositInterestDeal() == 1 ) {%>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">本利续存</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%}else if(financeInfo.getSDepositInterestDeal() == 2 ){ %>
           <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="130">利息转至活期账号：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%=NameRef.getAccountNoByID(financeInfo.getSDepositInterestToAccountID())%>&nbsp;&nbsp;</td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <%} %>
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
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
		{ 
%>
		 <table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="180" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:140px"> &nbsp;&nbsp;交易申请处理详情</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table></td>
      </tr>
    </table>
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class=list_table >
        <tr class="itemtitle">
          <td width="10%" height="26">
            <div align="center">序列号</div>
          </td>
          
          <td>
            <div align="center">用户</div>
          </td>
         
          <td>
            <div align="center">工作描述</div>
          </td>
          
          <td>
            <div align="center">时间和日期</div>
          </td>
        </tr>
        <tr>
          <td width="10%" align="center"  height="25">
            <div align="center">1</div>
          </td>
          
          <td   width="20%" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td   width="20%" height="25">
            <div align="center">录入</div>
          </td>
          
          <td  align="center" width="30%" height="25">
            <div align="center"><%= financeInfo.getFormatConfirmDate() %></div>
          </td>
        </tr>
        
<% }
			if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) || // 已复核
				(financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        		(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ) //已拒绝
        	{ 
%>
        <tr>
          <td width="63" align="center"   height="25">
            <div align="center">2</div>
          </td>
          
          <td>
            <div align="center"><%= financeInfo.getCheckUserName() %>
            </div>
          </td>
          
          <td>
            <div align="center">复核</div>
          </td>
          
          <td  width="269" height="25">
            <div align="center"><%= financeInfo.getFormatCheckDate() %></div>
          </td>
        </tr>
        <% }

				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{ 
%>
        <tr valign="middle">
          <td width="63" align="center"  height="25">
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
 </table>
 <br/>
 <%--
      <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr>
           <td>
           --%>
           <% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
	           <fs:uploadFile strContext = '<%=strContext%>'
		        	showOnly='true'
		        	transCode = '<%=strtransNo%>' 
		        	caption = "上传" 
		        	lid = '-1'  
		        	moduleID = '<%=Constant.ModuleType.SETTLEMENT%>'
		        	transTypeID = '<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
       <%--    </td>
        </tr>
      </table>
      --%>
       <% if(isNeedApproval.equals("true")){ %>
   <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
   	<tr>
		<td valign="top" colspan="3">
			<table  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">&nbsp;历史审批意见</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
			
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top" colspan="3">
			<!-- 历史审批意见 -->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
				<TR>
					<TD colspan="3">
      
	  <!-- 历史审批意见 -->
	 
			 <%--<iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
	  
		  </TD>
	  </TR>
	  <!-- 历史审批意见 -->
	  </table>
	  </td>
	  </tr>
	  </table>
	  <%} %>
		<br/>
		<table border="0" width=100% align="" cellspacing="0" cellpadding="0">
        <tr>
		<td width="100%" align="right">

<%
	/* 从指令查询页面过来的 */
String search="";
if(request.getAttribute("search")!=null){
search=(String)request.getAttribute("search");
}
String isSign="";
if(request.getParameter("sign")!=null){
isSign=request.getParameter("sign");
}
%>
<%			/* 复核 */
            System.out.println("financeInfo.getStatus() "+financeInfo.getStatus()+"sessionMng.m_lUserID "+sessionMng.m_lUserID+"financeInfo.getConfirmUserID() "+financeInfo.getConfirmUserID()+"lCheckType "+lCheckType+"search "+search);
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE|| financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) &&!search.equals("1")) {// 已确认、登录人<>确认人 %>
				<input class=button1 name=add type=button value=" 复核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">
			<% } %>
		  	
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<input type="Button" class="button1" value=" 关闭 " width="46" height="18"   onclick="window.close();">			
        	<%}%>
		 </td>
        </tr>
      </table>

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

<!-- add by mingfang 2007-05-24 -->
	  <!-- 签名用字段 -->
	  
   <input type="hidden" name="nPayeeAccountID"         value="<%=financeInfo.getPayeeAcctID()%>">     
   <input type="hidden" name="sPayerAccountNoZoomCtrl" value="<%=financeInfo.getSDepositBillNo()%>">
   <input type="hidden" name="dAmount"                 value="<%=financeInfo.getAmount()%>">
   <input type="hidden" name="nFixedDepositTime1"      value="<%=financeInfo.getSDepositBillPeriod()%>">
   <input type="hidden" name="tsExecute"               value="<%=financeInfo.getExecuteDate()%>">
   <input type="hidden" name="dPayerStartDate"         value="<%=financeInfo.getSDepositBillStartDate()%>">
   <input type="hidden" name="interesttype"            value="<%=financeInfo.getSDepositInterestDeal()%>">
   <input type="hidden" name="lInterestToAccountID"    value="<%=financeInfo.getSDepositInterestToAccountID()%>">
   <input type="hidden" name="submitUserId"            value="<%=financeInfo.getConfirmUserID()%>">
   <input type="hidden" name="dPayerCurrStartDate"     value="<%=financeInfo.getSDepositBillStartDate().toString().substring(0,10)%>">

	<!-- 供c415.jsp判断业务类型用-->
	<input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">  
	  
	   </td>
  </tr>
</table>
 </td>
  </tr>
</table>
 </form>
<!--presentation end-->

<script language="javascript">

		function checkme()
	{
			<%if(blnUseITrusCert){
				OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
				OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
			%>
			
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
		var msg = "是否复核？";	
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
				{
		%>
					msg = "该数据已被篡改，是否复核？"
					if(!confirm(msg))
					{
						return false;
					}
		<%
				}else if(remindType==OBConstant.VerifySignatureType.RIGIDITY)  //刚性
				{
		%>
					msg = "该数据已被篡改!"
					alert(msg);
					return false;
		<%
				}
			}else
			{
		%>		
					if(!confirm(msg))
					{
						return false;
					}				
		<%
			}
		%>	
		frm.action="../control/check_c006_1.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	
</script>

<%
		if(lShowMenu == OBConstant.ShowMenu.YES)
		{	/* 显示文件尾 */
			OBHtml.showOBHomeEnd(out);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
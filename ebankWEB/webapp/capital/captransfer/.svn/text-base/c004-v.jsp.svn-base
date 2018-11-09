<%--
/*
 * 程序名称：c004-v.jsp
 * 功能说明：资金划拨提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.safety.util.*,
				  com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				  com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				  com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@ page import="com.iss.itreasury.settlement.setting.dataentity.ExtSystemSettingInfo"%>
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.ExtSystemSettingBiz"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant.SettInstrType" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = null;
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
    
	long signForCheck = -1;//复核标识
	String strCheckSign = request.getParameter("signForCheck");
    if ((strCheckSign != null) && (strCheckSign.length()>0))
	{
    	signForCheck = Long.parseLong(strCheckSign);
	}
	
	boolean blnNotBeFalsified = true;
	String temp = null;	
	
    //String strID = (String)request.getAttribute("lID");
    //long lID = -1;
    //if((strID != null) && (strID.length()>0)){
    //	lID = Long.parseLong(strID);
    //}
    
	//是否需要审批流 by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
   //modify by xwhe 2008-07-23
    String flagCommit ="";
	temp = (String)request.getParameter("flagCommit");
	if(temp!=null && temp.equals("success"))
	{
	 flagCommit = temp;
	}
    String lTransType = "";
    lTransType= (String)request.getAttribute("lTransType");
    if(lTransType==null)
       	{
       	 	lTransType = (String)request.getParameter("txtTransType");
       	}
    //是否为申请指令查询
    String lOperate = null;
    String operate="";
	lOperate = (String)request.getParameter("operate");
	if (lOperate != null)
	{
	    operate = lOperate;
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
		long lID = financeInfo.getID();

		
		String strSourceType = "";

		ExtSystemSettingInfo extSystemSettingInfo = new ExtSystemSettingInfo();
		ExtSystemSettingBiz extSystemSettingBiz = new ExtSystemSettingBiz();
		if(financeInfo.getSource()!=-1)
		{
			extSystemSettingInfo = extSystemSettingBiz.findExtSystemSettingByID(financeInfo.getSource());
			strSourceType = extSystemSettingInfo.getSName();
		}
	
		long l = financeInfo.getRemitType();

		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
		//收款方账户ID
		long payeeAcctIDTemp = financeInfo.getPayeeAcctID();
		//收款方账户名称
		String payeeAcctName = "";
		com.iss.itreasury.ebank.util.NameRef nf = new com.iss.itreasury.ebank.util.NameRef();
		if(payeeAcctIDTemp>0)
		{
			payeeAcctName = nf.getRecAccountNameByID(payeeAcctIDTemp);
		}
		
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
	
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
	ClientAccountInfo accountInfo=null;	
	accountInfo=(ClientAccountInfo)request.getAttribute("accountInfo");		
	if (accountInfo==null) accountInfo=new ClientAccountInfo();
	
	String strPayerAccountNo = 	financeInfo.getPayerAcctNo();
	String strPayeeAccountNo = 	financeInfo.getPayeeAcctNo();
	//SEFC新增
	
	String strTransNo = financeInfo.getID() + "";
	if(isNeedApproval == null) isNeedApproval = "";
	
	boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
	int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);
    String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
   	boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
	OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
	//boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
	//boolean useNetSign = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign);  //是否使用新安世纪证书验签
	if(isUseCertification)
	{
		temp = (String)request.getParameter("blnNotBeFalsified");
		if(temp!=null&&temp.trim().length()>0)
		{
			blnNotBeFalsified = new Boolean(temp).booleanValue();
		}
	}
%>

<safety:resources />

<%
System.out.println("blnUseITrusCert"+blnUseITrusCert);
System.out.println("blnNotBeFalsified"+blnNotBeFalsified);
if(isUseCertification &&!blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt(); 
	//-->
	</script>
<%
	}
 %>
 
 
<form name="frmzjhb" method="post">
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title ><span class="txt_til2">逐笔付款确认</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
  
<br/>
<% 
		if (lSourceType != 1 && financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) 
		{
%>

	  <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p ><br>
        逐笔付款-<%= financeInfo.getFormatRemitType() %>将<%
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
			    <%
			    if(isNeedApproval.equals("true")&& flagCommit.equals("success"))
			  	{
			  	%>
			  	    <br>该笔交易已保存还未提交！<br>
			  	<%
			  	}
			    else if(isNeedApproval.equals("true"))
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
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
<%
		}
%>

<table border="0" cellspacing="0" cellpadding="0" >
     <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 付款方资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
	  <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方名称：</td>
          <%--
          <td width="430" height="25" class="MsoNormal"> <%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":sessionMng.m_strClientName%></td>
          --%>
          <td width="430" height="25" class="MsoNormal"> <%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<!--
        <tr bgcolor="#FFFFFF">
          <td colspan="4" height="1"></td>
        </tr>
        <tr bgcolor="#C8D7EC">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="graytext">银行账号：</td>
          <td width="430" height="25" class="graytext"><%//= financeInfo.getPayerBankNo() %></td>
          <td width="5"></td>
        </tr>
		-->
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方账号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(strPayerAccountNo) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
<table  border="0" cellspacing="0" cellpadding="0">
      <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 收款方资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
<table width=100% border="0" cellspacing="0" cellpadding="0"class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            汇款方式：
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatRemitType() %></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
      
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            收款方账号：
          </td>
          <td width="430" height="25" class="MsoNormal"><%=(strPayeeAccountNo==null)?"":NameRef.getNoLineAccountNo(strPayeeAccountNo)%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            收款方名称：
          </td>
          <td width="430" height="25" class="MsoNormal">
		   <%		  
		  if (financeInfo.getRemitType()==OBConstant.SettRemitType.OTHER)
		  {
					out.println(Env.getClientName());
			}
			else
			{
					   	out.println((financeInfo.getPayeeName()==null)?payeeAcctName:financeInfo.getPayeeName());
			} 
		   %></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        
        <% 
        if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
        {%>
        <tr id="payeePlace" class="MsoNormal">
        <td width="5" height="25" class="MsoNormal"></td>
        <td height="25" width="130" class="MsoNormal" align="left">汇入地：</td>
        <td width="430" height="25"  class="MsoNormal">
         <%=  (financeInfo.getPayeeProv() ==null)?"":financeInfo.getPayeeProv() %>
            省
         <%= ( financeInfo.getPayeeCity()== null)?"":financeInfo.getPayeeCity() %>
        市（县）</td>
          <td height="25" width="9" class="MsoNormal"></td>
        </tr>
         <tr id="payeeBankName">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行名称：</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
         <tr id="payeeBankCNAPSNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行CNAPS号：</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankCNAPSNO()==null)?"":financeInfo.getSPayeeBankCNAPSNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
         <tr id="payeeBankOrgNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行机构号：</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankOrgNO()==null)?"":financeInfo.getSPayeeBankOrgNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
         <tr id="payeeBankExchangeNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行联行号：</td>
          <td height="25" width="430" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankExchangeNO()==null)?"":financeInfo.getSPayeeBankExchangeNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
        
      <% }
        %>

        
		</table>
      <br>
	  <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>
</table>
      <table width=100% border="0" cellspacing="0" cellpadding="0" class=normal id="table1">
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
        <tr>
            <td width="5" height="25" class="MsoNormal"></td>
            <!--modify by xwhe 2008-11-10-->
            <td width="130" height="25" class="MsoNormal" colspan="2" >大写金额(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)：</td>
			<td width="430" height="25" class="MsoNormal"><%=ChineseCurrency.showChinese(DataFormat.formatAmount(financeInfo.getAmount()),sessionMng.m_lCurrencyID)%></td> 	
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
         <tr>
            <td width="5" height="25" class="MsoNormal"></td>
       
            <td width="130" height="25" class="MsoNormal" colspan="2" >数据来源</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getSource()==-1?"":strSourceType %>
			</td> 	
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
         <%
		       if(financeInfo.getSource()!=SETTConstant.ExtSystemSource.EBANK)
		        {
		  %>
        
         <tr>
            <td width="5" height="25" class="MsoNormal"></td>
       
            <td width="130" height="25" class="MsoNormal" colspan="2" >外部系统指令序号</td>
			<td width="430" height="25" class="MsoNormal"><%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %></td> 	
		  <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%
        	}
         %>
        
        <!-- Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"，使用手续费的配置文件 -->
        <%
        	if ( Config.getBoolean(ConfigConstant.SETT_TRANSCOMMISSION,false)
        	  && financeInfo.getRemitType() == OBConstant.SettRemitType.BANKPAY )
        	{
        %>
        	<tr>
				<td width="5" height="25" class="MsoNormal"></td>
	          	<td width="130" height="25" class="MsoNormal" colspan="2">汇款区域/速度：</td>
	          	<td width="430" height="25" class="MsoNormal">
	            	<%=Constant.remitAreaType.getName(financeInfo.getRemitArea()) %>&nbsp;
	            	<%=Constant.remitSpeedType.getName(financeInfo.getRemitSpeed()) %>
	          	</td>
	          	<td width="5" height="25" class="MsoNormal"></td>
        	</tr>
        <%
        	}
        %>
        
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
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getNote()==null)?"":financeInfo.getNote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <% if (financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE){ %>
 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">失败原因：</td>
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getReject() ==null)?"":financeInfo.getReject() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%}%>
      </table>
      
      
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
<br/>
      <table   border="0" cellspacing="0" cellpadding="0">
      <tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> 交易申请处理详情</td>
			<td width="17"><a class=lab_title3></td>
	  </tr>
    </table>
      <table width=100% border="0" cellpadding="0" cellspacing="1" class=list_table id="table1">
		<tr class="itemtitle">
          <td height="26" width="10%"  align="center" >
            序列号
          </td>
          
          <td height="19" width="30%" align="center">
           用户
          </td>
          
          <td  height="19"  width="30%" align="center">
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19"   width="30%" align="center">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        <tr valign="middle">
          <td width="10%" align="center"  height="25">
            <div align="center">1</div>
          </td>
          
          <td width="30%" height="25" >
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>          
          <td  width="30%" height="25">
            <div align="center">录入</div>
          </td>
          
          <td  width="30%" height="25">
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
          <td width="10%" align="center"  height="25">
            <div align="center" >2</div>
          </td>
          
          <td width="30%" height="25" align="center">
            <div align="center"><%= financeInfo.getCheckUserName() %></div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center">复核</div>
          </td>
          
          <td  width="30%" height="25">
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
          <td width="10%" align="center"  height="25">
            <div align="center" >3</div>
          </td>
         
          <td  width="30%" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td width="30%" height="25">
            <div align="center">签认</div>
          </td>
          
          <td  width="30%" height="25">
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
<%
 		} 
%>
<br/>
<% if(isNeedApproval.equals("true")){ %>
<table  border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> 历史审批意见</td>
	<td width="17"><a class=lab_title3></td>
</tr>

</table>
	 
	  <!-- 历史审批意见 -->
	  <TR>
		  <TD colspan="3">
			<%--  <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&lID=<%=lID %>&transType=<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<%String tempTransNo = financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";
			if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
          		 { %>
					<fs:HistoryOpinionFrame
					  strTransNo='<%=tempTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
					<%
		        	}
		        	else if(financeInfo.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
		        	{
		        	 %>
		        	 <fs:HistoryOpinionFrame
					  strTransNo='<%=tempTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
					 <%
		        	 }
		        	  %>
		  </TD>
	  </TR>
	  <!-- 历史审批意见 -->
<%} %>
<br />

        <tr>
		
      <td width="100%" align="right"> 
        <%
        String search="";
if(request.getAttribute("search")!=null){
search=(String)request.getAttribute("search");
}
String isSign="";
if(request.getParameter("sign")!=null){
isSign=request.getParameter("sign");
}
		if(financeInfo.getStatus()==OBConstant.SettInstrStatus.REFUSE)
		{
			if(financeInfo.getConfirmUserID()==sessionMng.m_lUserID&&biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
				{
		%>

		<input class="button1" name=add type=button value=" 重新提交 " onClick="Javascript:submitagain();" onKeyDown="Javascript:submitagain();">&nbsp;&nbsp;
		<% 
				}
		}
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 
        	if(biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
        		{
        %>
        <!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
        <!--打印委托付款凭证-->
        <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();"> 
        <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"--> 
        	
		<input class="button1" name=add type=button value=" 修 改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;
		<input class="button1" name=add type=button value=" 删 除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
        <% 
        	}
        	if ( lSourceType != 1 &&  lShowMenu != OBConstant.ShowMenu.NO) {%>
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class="button1" name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
        <%}%>
        <% }
        if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID()))
		{// 未、已确认、登录人＝确认人 
			if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) {
		%>
			<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
			
		<%}
		}
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE ) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"--> 
		<input class="button1" name=add type=button value=" 复 核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
        <% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"--> 
		<input class="button1" name=add type=button value=" 复 核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		        <%
		        if(signForCheck>0){
		        %>
		         <input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnCheck()" onKeyDown="javascript:returnCheck()">&nbsp;&nbsp;
		        <% 
		        }else{
		        %>	 
		        <input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
		        <% 
		        }
		   	}
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())&&!search.equals("1") && !isSign.equals("1")) {// 已复核、登录人＝复核人 %>
        <!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onClick="Javascript:cancelcheckme();"-->
		<input class="button1" name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
        <%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)&&!search.equals("1") && isSign.equals("1")){// 已复核、需要被登录人签认=true %>
        <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"--> 
		<input class="button1" name=add type=button value=" 签 认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;
        <%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())&&!search.equals("1") && isSign.equals("1")){// 已签认、登录人＝签认人 %>
        <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"--> 
		<input class="button1" name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;
        <%
	       }
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus(); 
        %>
        <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"--> 
		<%
		//add by zcwang 2007-10-19  设置业务是否需要打印的提示
		if(Config.getBoolean(ConfigConstant.EBANK_ISPRINT,false))
		{
		 %>
		<input type="Button" class="button1" value=" 打 印 " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		<%if(a!=null && !a.contains(strStatus))
               {
		%>
			   disabled
		<%
			   }
		%>
		>
		<% 
		}
		//
		%>	
		
        <!--
            <img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
        <% if ( lShowMenu == OBConstant.ShowMenu.NO) 
        	{
        		
        		if(operate.equals("query"))
        		{
        	%>
        			<!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
					<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();"> &nbsp;&nbsp;
        	<%
        		}
        		else
        		{
        	%>
        			<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();window.opener.doQuery();"> &nbsp;&nbsp;
        	<% 		
        		}
        		
        	}
        	%>
      </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
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
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify() +"" %>">
	  <!-- add by mingfang 供c415.jsp判断业务类型用-->
	  <input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">
	  	  
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  
	  <!-- 申请指令查询标志字段 -->
	  <input type="hidden" name="newOpen" value="">
	  <!-- 重新提交标志字段 -->
	  <input type="hidden" name="submitAgain" value="">

 		<!-- 签名用字段 -->
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">	  
	  
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  <!--start  指纹认证相关html -->
   	  <input name="Ver" id="Ver" type="hidden" value="">
	  <!--end  指纹认证相关html -->
	  </td>
  </tr>
</table>  
</form>
<!--presentation end-->
	<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	<!--  指纹控件-->
	<OBJECT  style='display:none' id='ObjFinger' class='height:100' classid='clsid:04793CDE-C768-449B-BE87-40147B56032D'"
			 +"codebase='FpDevOcx_TESO.ocx' width=140 height=180 border=1></OBJECT>
	<% } %>
<script language="javascript">
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
		        frmzjhb.menu.value="hidden";
		<%  }   %>
	}
	/*返回处理函数 */
	function returnme()
	{
		frmzjhb.lInstructionID.value = "";
		frmzjhb.action="../captransfer/c001-c.jsp";
	    showSending();
		frmzjhb.submit();
	}
	/*返回处理函数 */
	function returnCheck()
	{
		frmzjhb.lInstructionID.value = "";
		frmzjhb.action="../check/ck006-c.jsp?SelectType=<%=OBConstant.QueryInstrType.CAPTRANSFER%>";
	    showSending();
		frmzjhb.submit();
	}
	/* 确认处理函数 */
	function confirmme()
	{
		//showMenu();
		frmzjhb.action="../captransfer/C13.jsp";
		frmzjhb.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
		var msg;
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
				{
		%>
					msg = "该数据已被篡改，是否修改？"
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
			}
		%>
		frmzjhb.newOpen.value = "newOpen";
		frmzjhb.action="../financeinstr/control/fi_c006.jsp";
		showSending();
		frmzjhb.submit();
	}
		function submitagain()
	{
		//showMenu();
		frmzjhb.submitAgain.value = "submitAgain";
		frmzjhb.newOpen.value = "newOpen";
		frmzjhb.action="../financeinstr/control/fi_c006.jsp";
		showSending();
		frmzjhb.submit();
	}
	/* 删除处理函数 */
	function deleteme()
	{
		if (!confirm("是否删除？"))
		{
			return false;
		}
		//showMenu();
		frmzjhb.action="../captransfer/c005-c.jsp?flag=delete";
		showSending();
		frmzjhb.submit();
	}
	/* 复核匹配函数 */
	function checkmatchme()
	{
		//showMenu();
		frmzjhb.action="../check/ck006-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}	
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}%>
		
		//-------------------添加指纹认证---开始----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //指纹验证
		$.ajax(
		{
			  type:'post',
			  url:"<%=strContext%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
			  async:false,
			  success:function(returnValue){
			  	 var result = $(returnValue).filter("div#returnValue").text();
				 if(result=='success'){
					  fpFlag = true;
				 }
				 else if(result=="needFPCert"){
			  		getFingerPrint(frmzjhb,1);
					if($("#Ver").val()!=""){
				  	    checkme();// 再次提交
					}
					fpFlag = false;
				 }
				 else if(result=="fpiswrong"){
			  		alert("指纹认证错误，请重新采集");	
					$("#Ver").val("");
				  	getFingerPrint(frmzjhb,1);//加载控件
					if($("#Ver").val()!=""){
				  	    checkme();// 再次提交
					}
					fpFlag = false;
				}
				else{
					if(result != null && result != "null" && result != "" ){
						alert(result);	
						$("#Ver").val("");
						fpFlag = false;
					}else{
						fpFlag = true;
					}
				}
			  }
		}
		);
		if(!fpFlag){return;}
		<%}%>
		//-------------------添加指纹认证---结束----------------
		
		frmzjhb.action="../check/C415.jsp?flag=checked";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		var msg = "是否取消复核？"
		if(!confirm(msg))
		{
			return false;
		}				
		<%

			if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
		%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}%>
		frmzjhb.action="../check/C415.jsp";
		frmzjhb.txtisCheck.value = "0";
		showSending();
		frmzjhb.submit();
		
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		
		//-------------------添加指纹认证---开始----------------
		<%if ( Config.getBoolean(ConfigConstant.GLOBAL_PRIVILEGE_FINGERPRINT,false) ) {%>
	    var fpFlag = true;
	    //指纹验证
		$.ajax(
		{
			  type:'post',
			  url:"<%=strContext%>/fingerprintControl.jsp",
			  data:"strAction=fingerprint&Ver="+encodeURIComponent($("#Ver").val()),
			  async:false,
			  success:function(returnValue){
			  	 var result = $(returnValue).filter("div#returnValue").text();
				 if(result=='success'){
					  fpFlag = true;
				 }
				 else if(result=="needFPCert"){
			  		getFingerPrint(frmzjhb,1);
					if($("#Ver").val()!=""){
				  	    signme();// 再次提交
					}
					fpFlag = false;
				 }
				 else if(result=="fpiswrong"){
			  		alert("指纹认证错误，请重新采集");	
					$("#Ver").val("");
				  	getFingerPrint(frmzjhb,1);//加载控件
					if($("#Ver").val()!=""){
				  	    signme();// 再次提交
					}
					fpFlag = false;
				}
				else{
					if(result != null && result != "null" && result != "" ){
						alert(result);	
						$("#Ver").val("");
						fpFlag = false;
					}else{
						fpFlag = true;
					}
				}
			  }
		}
		);
		if(!fpFlag){return;}
		<%}%>
		
		var msg = "是否签认？"
		<%
			if(isUseCertification&&!blnNotBeFalsified)
			{
				if(remindType==OBConstant.VerifySignatureType.FLEXIBILITY)  //柔性
				{
		%>
					msg = "该数据已被篡改，是否签认？"
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
		//-------------------添加指纹认证---结束----------------
		
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		var msg = "是否取消签认？";
		if(!confirm(msg))
		{
			return false;
		}				
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "0";
		showSending();
		frmzjhb.submit();
		
	}
	/* 打印处理函数 */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frmzjhb.target="new_window";
		frmzjhb.submit();
		frmzjhb.target="";
	}
$(document).ready(function() {
 	$(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });
    $("#flexlist").flexigridenc({
		colModel : [
			{display: '序列号',  name : 'ncurrencyid', width : 300, sortable : false, align: 'center'},
			{display: '用户',  name : 'payeracctno', width : 300, sortable : false, align: 'center'},
			{display: '工作描述',  name : 'payername', width : 340, sortable : false, align: 'center'},
			{display: '时间和日期',  name : 'ntranstype', width : 350, sortable : false, align: 'center'}
		],//列参数
		//title:'交易申请处理详情',
		classMethod : 'com.iss.itreasury.ebank.obfinanceinstr.action.QueryCheckInfoAction.queryUncheckDetailInfo',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "strID" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "desc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		usepager : false,
		userFunc : getFormData,
		height : 100,
		printbutton : false,
		exportbutton : false
	});
	
});

function getFormData() 
{
	return $.addFormData("frmbizapp","flexlist");
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
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/SignValidate.inc" %>
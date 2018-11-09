<%--
/*
 * 程序名称：n014-v.jsp
 * 功能说明：通知支取提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月12日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				  com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"
%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[通知支取]";
%>

<%
	//分页信息
	FlexiGridInfo flexiGridInfo = new FlexiGridInfo();	/* 实现菜单控制 */
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
       	
     
	//是否需要审批流 by ypxu 2007-05-10
	String isNeedApproval = request.getParameter("isNeedApproval");
	if(isNeedApproval == null) isNeedApproval = "";
	
	//是否为申请指令查询
    String lOperate = null;
    String operate="";
	lOperate = (String)request.getParameter("operate");
	if (lOperate != null)
	{
	    operate = lOperate;
	}
	String strID = (String)request.getAttribute("lID");
    long lID = -1;
    if((strID != null) && (strID.length()>0)){
    	lID = Long.parseLong(strID);
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
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		//信息重新读取，主要更新dtmodify信息 add by zhanglei  2010.05.31
		financeInfo = financeInstr.findByID(financeInfo.getID(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);		
		OBFinanceInstrBiz biz = new OBFinanceInstrBiz();
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		String strTransNo = financeInfo.getID() + "";
		
		
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
		int remindType = Config.getInteger(ConfigConstant.EBANK_CERTIFICATE_REMIND,OBConstant.VerifySignatureType.RIGIDITY);		
	//	boolean isUseCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
	//	boolean useNetSign = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NetSign);  //是否使用新安世纪证书验签
        String certificationType = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate);
        boolean isUseCertification = !certificationType.equals(Constant.GlobalTroyName.NotUseCertificate);
		
		
		if(isUseCertification)
		{
			String temp = null;	
			temp = (String)request.getParameter("blnNotBeFalsified");
			if(temp!=null&&temp.trim().length()>0)
			{
				blnNotBeFalsified = new Boolean(temp).booleanValue();
			}			
		}			
		
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<form name="frm" method="post">
<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">通知存款支取</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
	  
	<br/>
<%if(isUseCertification && !blnNotBeFalsified){ %>
	<script type="text/javascript">
	<!--
		showRecordBeFalsifiedPrompt();
	//-->
	</script>
<%

	}
 %>

<% 
		if (lSourceType != 1) 
		{
%>
	  <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 通知支取确认</td>
	<td width="800"><a class=lab_title3></td>
</tr>	   
    </table> 
	  <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
       
        
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>通知支取将<%
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
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1"></td>
        </tr>
      </table>
      <br>
<%
		}
%>
	  <table align="" width="170" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 通知账户资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>	   
     </table> 
	  <table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
     
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
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo()) %></td>
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
      <table align="" width="170" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 通知账户资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>	   
     </table>
    
     <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
        <tr class="MsoNormal">
          <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class ="normal">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="230" height="25"> 
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
          <input type="hidden" id="aaa" value="<%= financeInfo.getFormatRemitType() %>">
        </tr>
        <tr id="payeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNo" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo())%></td>
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
        <tbody id="aa">
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
        </tbody>
         <tbody id="cc">
        <tr id="payeePlace" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25"> 
          </td>
        </tr>
        <tr id="payeeBankNameLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankName" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25">
          </td>
        </tr>
        <tr id="line1" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        </tbody>
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
           <input type="hidden" id="bbb" value="<%= financeInfo.getFormatInterestRemitType() %>">
        </tr>
        
        <tr id="InterestPayeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNO" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%=NameRef.getNoLineAccountNo(financeInfo.getInterestPayeeAcctNo()) %></td>
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
       <tbody id="bb">
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
        </tbody>
        <tbody id="dd">
        <tr id="InterestPayeePlace" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25"> 
          </td>
        </tr>
        <tr id="InterestPayeeBankLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBank" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal"></span></p>
          </td>
          <td width="240" height="25"> 
          </td>
        </tr>
        </tbody>
      </table>  
      	   <script type="text/javascript">
   	   document.getElementById("cc").style.display= "none";
   	   document.getElementById("dd").style.display= "none";
		var aaa=document.getElementById("aaa").value;
		var bbb=document.getElementById("bbb").value;
		if(aaa == "内部转账"){
			document.getElementById("aa").style.display= "none";
			document.getElementById("cc").style.display= "";
		}
		if(bbb == "内部转账"){
			document.getElementById("bb").style.display= "none";
			document.getElementById("dd").style.display= "";
		}
		</script>
		  </td>
        </tr>
      </table>
	   
	  <br>
	  <table align="" width="170" border="0" cellspacing="0" cellpadding="0" >
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 划款资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>	   
     </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0"class ="normal" align="">
        
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="15"  class="MsoNormal" width="110">金额：</td>
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
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE) ||//已拒绝   
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED)||//已审批
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING))	//审批中		   
		{ 
%>
      <table align="" width="170" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> 交易申请处理详情</td>
			<td width="17"><a class=lab_title3></td>
		</tr>	   
     </table>
    
       <table width=100% border="0" align="" cellpadding="0" cellspacing="1" class=list_table align="center">
        <tr class="itemtitle">
          <td width="50" height="19">
            <p align="center">序列号</p>
          </td>
          
          <td  height="19" valign="middle"  width="150">
            <div align="center">用户</div>
          </td>
         
          <td height="19" valign="middle"  width="150">
            <div align="center">工作描述</div>
          </td>
          
          <td height="19" valign="middle" width="217">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        <tr valign="middle">
          <td width="50" align="left" height="25">
            <div align="center">1</div>
          </td>
         
          <td width="150" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td width="150" height="25">
            <div align="center">录入</div>
          </td>
          
          <td width="215" height="25">
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
          <td width="50" align="left" height="25">
            <div align="center" >2</div>
          </td>
          
          <td width="150" height="25">
            <div align="center"><%= financeInfo.getCheckUserName()==null?"机核":financeInfo.getCheckUserName() %></div>
          </td>
          
          <td width="150" height="25">
            <div align="center">复核</div>
          </td>
          
          <td width="217" height="25">
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
          <td width="50" align="left" height="25">
            <div align="center">3</div>
          </td>
          
          <td width="150" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td width="150" height="25">
            <div align="center">签认</div>
          </td>
         
          <td width="217" height="25">
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
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
       
	  <br>
     <% if(isNeedApproval.equals("true")){ %> 
      
	   <table width="170" border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">历史审批意见</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
		</table>
			<%-- <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.NOTIFYDEPOSITDRAW%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'
					/>
					<%} %>
		<br />
		<table border="0" width=100% align="" cellspacing="0" cellpadding="0">
        <tr>
		<td width="100%" align="right">
			<%
				if(financeInfo.getStatus()==OBConstant.SettInstrStatus.REFUSE)
					{
						if(financeInfo.getConfirmUserID()==sessionMng.m_lUserID&&biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
						{
							
			 %>
			 				<input class="button1" name=add type=button value=" 重新提交 " onClick="Javascript:submitagain();" onKeyDown="Javascript:submitagain();">&nbsp;&nbsp;
			 <%
			 			}
			 		}
			  %>

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
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人
			if(biz.hasUserAuthority(financeInfo.getPayerAcctNo(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
				{
				
		 %>

<!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
		    <input class=button1 name=add type=button value=" 修 改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;

			<input class=button1 name=add type=button value=" 删 除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
			
		   <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();">
            <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"-->

            
            <% 
            	}
            if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO ) {%>            
			<!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
			<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
            <%}%>

		<% }
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALING)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID()))
		{// 未、已确认、登录人＝确认人 
		if ( lSourceType != 1 && lShowMenu != OBConstant.ShowMenu.NO) 
		{%>
			<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
			
		<%}
		}
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"-->
				<input class=button1 name=add type=button value=" 复 核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
			<% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>

				<!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"-->
				<input class=button1 name=add type=button value=" 复 核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
		  		<input class=button1 name=add type=button value=" 返 回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
			<% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())&&!search.equals("1") && !isSign.equals("1")) {// 已复核、登录人＝复核人 %>

				<!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onclick="Javascript:cancelcheckme();"-->
				<input class=button1 name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
		<%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)&&!search.equals("1") && isSign.equals("1")){// 已复核、需要被登录人签认=true %>

			 <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"-->
			 <input class=button1 name=add type=button value=" 签 认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;

		<%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())&&!search.equals("1") && isSign.equals("1")){// 已签认、登录人＝签认人 %>

				 <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"-->
				 <input class=button1 name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;

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
		   <input type="Button" class="button1" value=" 打 印 " disabled="disabled" width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		   <%
			   if(a!=null && !a.contains(strStatus))
               {
			   // 开放打印权限 2007/3/16 modify by wjliu 把disabled去掉
		   %>
				
		   <%
			   }
			%>
		   >&nbsp;&nbsp;
		   <%
		   }
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
        			<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();window.opener.queryme();"> &nbsp;&nbsp;
        	<% 		
        		}
        		
        	}
        	%>
		 </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
      </table>
      <input type="hidden" name="dtmodify" value="<%=(financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"") %>"> 
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
	  <!-- 供c415.jsp判断业务类型用-->
	  <input type="hidden" name="SelectType" value="<%=financeInfo.getTransType() %>">
  </td>
  </tr>
</table>
	  </form>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
	window.open("<%=strContext%>/capital/common/showDepositVoucherPrintPage.jsp?lTransType=<%=financeInfo.getTransType()%>&sTransNo=<%=financeInfo.getTransNo()%>");
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
	<%
		/* 复核 */
		if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo
		.getStatus() == OBConstant.SettInstrStatus.APPROVALED)
		&& (sessionMng.m_lUserID != financeInfo.getConfirmUserID())
		&& (lCheckType == 1)&& !search.equals("1")) {// 已确认、登录人<>确认人
	%>
		frm.action="<%=strContext%>/capital/check/ck001-v.jsp";
		
	<% }else{%>	
		frm.action="<%=strContext%>/capital/notify/n011-c.jsp";
	<% } %>	
		frm.submit();
	}

	/* 确认处理函数 */
	function confirmme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/notify/C13.jsp";
		frm.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
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
		frm.action="<%=strContext%>/capital/notify/n011-c.jsp";
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
		frm.action="<%=strContext%>/capital/notify/n015-c.jsp";
		showSending();
		frm.submit();
	}
	/* 复核匹配函数 */
	function checkmatchme()
	{
		//showMenu();
		frm.action="<%=strContext%>/capital/check/ck006-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		
		
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.NotifyDepositDraw.outSignNameArrayToView(out);
				OBSignatureConstant.NotifyDepositDraw.outSignValueArrayToView(out);
		%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}%>
		if (!confirm("是否复核？"))
		{
			return false;
		}
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		var msg = "是否取消复核？";
	
		if(!confirm(msg))
		{
			return false;
		}				
		<%


			if(blnUseITrusCert){
				OBSignatureConstant.NotifyDepositDraw.outSignNameArrayToView(out);
				OBSignatureConstant.NotifyDepositDraw.outSignValueArrayToView(out);
		%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单	
		<%}
		%>
			

					
		frm.action="<%=strContext%>/capital/check/C415.jsp";
		frm.txtisCheck.value = "0";
		showSending();
		frm.submit();
		
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		var msg = "是否签认？";
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

		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		var msg = "是否取消签认？";

		if(!confirm(msg))
		{
			return false;
		}				
		frm.action="<%=strContext%>/capital/sign/s004-c.jsp";
		frm.txtisCheck.value = "0";
		showSending();
		frm.submit();
		
	}
	/* 打印处理函数 */
	function printme()
	{
		frm.action="<%=strContext%>/capital/captransfer/S00-Ipt.jsp";
		frm.target="new_window";
		frm.submit();
		frm.target="";
	}
	
	function submitagain()
	{
		
		frm.action="<%=strContext%>/capital/notify/n011-c.jsp?sign=again";
		frm.submit();
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
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />

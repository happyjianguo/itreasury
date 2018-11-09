<!--
/*
 * 程序名称：vAppc004-v.jsp
 * 功能说明：资金划拨提交,修改输出页面
 * 作　　者：刘琰
 * 完成日期：2004年01月06日
 */
-->

<!--Header start-->
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
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.safety.info.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<% String strContext = request.getContextPath();%>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = null;
%>

<%
	/* 实现菜单控制 */
	
    //显示保存操作成功
    String str = (String)request.getAttribute("TJCG");
	if(str!=null&&str.equals("success")){
		out.println("<script language='javascript'>");
		out.println("alert('提交成功')");
		out.println("</script>");
	}
		
		
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
		long l = financeInfo.getRemitType();
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
	
	boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
	boolean blnNotBeFalsified = true;

	if(blnUseITrusCert)
	{
		String[] nameArray = OBSignatureConstant.CapTransfer.getSignNameArray();
		String[] valueArray = OBSignatureConstant.CapTransfer.getSignValueArrayFromInfo(financeInfo);	
		if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused())
		{
			//特殊处理
			//modified by mzh_fu 2007/12/20
		 	//valueArray[5] = "-1";
		 	valueArray[OBSignatureConstant.CapTransfer.iArrayLength-1] = "-1";	 	
		}
		
		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setNameArray(nameArray);
		signatureInfo.setValueArray(valueArray);
		signatureInfo.setSignatureValue(financeInfo.getSignatureValue());
		
		blnNotBeFalsified = SignatureAuthentication.validateFromDB(signatureInfo);			
		//blnNotBeFalsified = SignatureAuthentication.validateFromDB(nameArray,valueArray,financeInfo.getSignatureValue());					
	}
	
%>

<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />

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
			       <td class=title><span class="txt_til2">逐笔付款</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
    </td>
  </tr>

</table>
<br/>
<table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">逐笔付款确认</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
	  <table width="80%" border="0" cellspacing="0" align="" cellpadding="0" class=normal id="table1">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p ><br>
        逐笔付款-<%= financeInfo.getFormatRemitType() %>将<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>于复核后才<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>于审批并复核后才<%} }%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
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
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>

<table width="80%" border="0" cellspacing="0" cellpadding="0" align="">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 付款方资料</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
	  <table width="80%" border="0" align="" cellspacing="0" cellpadding="0" class=normal id="table1">
		<%
		long lChild = GetNumParam(request,"child");
		if (lChild == 1 || financeInfo.getChildClientID()>0) {
		%>
		<tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">下属单位客户编号：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getChildClientNo() %></td>
          <td width="5" height="25"></td>
        </tr>
		<tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal" >
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">下属单位名称：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getChildClientName() %></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		<%}%>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方名称：</td>
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
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
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
<table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
	<tr>
		<td width="1"><a class=lab_title1></td>
		<td class="lab_title2" style="width:150px">收款方资料</td>
		<td width="800"><a class=lab_title3></td>
	</tr>
</table>
<table width="80%" border="0" align="" cellspacing="0" cellpadding="0"class=normal id="table1">
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
          <td colspan="4" height="1" class="MsoNormal"></td>
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
          <td colspan="4" height="1" class="MsoNormal"></td>
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
					   	out.println((financeInfo.getPayeeName()==null)?"":financeInfo.getPayeeName());
			} 
		   %></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <%		  
		  if (financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
		  {
		  %>
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
          <td height="25" colspan="2" class="MsoNormal">
           <%= ( financeInfo.getPayeeBankName()==null)?"":financeInfo.getPayeeBankName() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankCNAPSNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行CNAPS号：</td>
          <td height="25" colspan="2" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankCNAPSNO()==null)?"":financeInfo.getSPayeeBankCNAPSNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankOrgNO">
         <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行机构号：</td>
          <td height="25" colspan="2" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankOrgNO()==null)?"":financeInfo.getSPayeeBankOrgNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
        <tr id="payeeBankExchangeNO">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" width="130" class="MsoNormal" align="left">汇入行联行号：</td>
          <td height="25" colspan="2" class="MsoNormal">
           <%= ( financeInfo.getSPayeeBankExchangeNO()==null)?"":financeInfo.getSPayeeBankExchangeNO() %>
          </td>
        <td  height="25" width="7" class="MsoNormal"></td>
        </tr>
        <% }%>
        
		</table>
      <br>
<table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2"> 划款资料</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0" class=normal id="table1">
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
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
         <tr class="MsoNormal">
            <td width="5" height="25" class="MsoNormal"></td>
         
            <td width="130" height="25" class="MsoNormal" colspan="2" >外部系统指令序号：</td>
			<td width="430" height="25" class="MsoNormal">
				<%=financeInfo.getApplyCode()==null?"":financeInfo.getApplyCode() %>
			</td> 	
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
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getNote()==null)?"":financeInfo.getNote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <% if (financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE){%>
	<tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">失败原因：</td>
          <td width="430" height="25" class="MsoNormal"><%= (financeInfo.getReturnMsg()==null)?"":financeInfo.getReturnMsg() %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <%}%>
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
      <table width="80%" align="" border="0" cellspacing="0" cellpadding="0">
 
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 交易申请处理详情</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
      <table width="80%" border="1" align="" cellpadding="0" cellspacing="1" class=normal id="table1">
        <thead>
		<tr >
          <td height="19" width="10%"  align="center" >
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
       </thead>
        <tr valign="middle">
          <td width="10%" align="left"  height="25">
            <div align="center">1</div>
          </td>
          
          <td width="30%" height="25">
            <div align="left"><%= financeInfo.getConfirmUserName() %></div>
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
          <td width="10%" align="left"  height="25">
            <div align="center" >2</div>
          </td>
          
          <td width="30%" height="25">
            <div align="left"><%= financeInfo.getCheckUserName() %></div>
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
          <td width="10%" align="left"  height="25">
            <div align="center" >3</div>
          </td>
         
          <td  width="30%" height="25">
            <div align="left"><%= financeInfo.getSignUserName() %></div>
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
<br>
<%
 		} 
%>


<form name="frmzjhb" method="post">
<table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">历史审批意见</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
<table border="0" width="80%" align="" cellspacing="0" cellpadding="0" class=normal>
	 <TR>
		  <TD colspan="3">
		<%-- 	 <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>&&transType=<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
		--%>
		<% String strtransNo =financeInfo.getID()>0?String.valueOf(financeInfo.getID()):"";%>
				<%if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY)
          		 { %>
					<fs:HistoryOpinionFrame
					  strTransNo='<%=strtransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_BANKPAY%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					/>
					<%
		        	}
		        	else if(financeInfo.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
		        	{
		        	 %>
		        	 <fs:HistoryOpinionFrame
					  strTransNo='<%=strtransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					/>
					 <%
		        	 }
		        	  %>
		  </TD>
	  </TR>
	  <tr>
		    <%
		    if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
			{
				//added by mzh_fu 2007-05-03 对已审批、已复核的任务，不再显示审批意见录入框
				if(financeInfo.getStatus() != OBConstant.SettInstrStatus.APPROVALED && financeInfo.getStatus() != OBConstant.SettInstrStatus.CHECK){

						String strMagnifierNameRemark = "审批意见";
						String strFormNameRemark = "frmzjhb";
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
<br />
<isswf:init />
<table border="0" width="80%" align="" cellspacing="0" cellpadding="0">
<tr>
<td align="right">
	      <%
	    	//当从取消审批列表进入时,显示取消审批按钮
	    	if(operation!=null && operation.length()>0 && Long.parseLong(operation)==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
	    		{
	    	%>
	    		<input class="button1" name="ca" type="button" value=" 取消审批 " onClick="javascript:cancelApproval()" onKeyDown="javascript:cancelApproval()">
	        	<isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval(this);" />
			<%	}
			else
				{
			%>
	        <isswf:submit styleClass="button1" value=" 审 批 " history=" 返 回 "  onclick="doApproval(this);" />	
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
</table>
<br/>
       <%--
       <tr>
       	<td>
       <%
		/* 确认、修改、删除 */
		if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) || (financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE)) && (sessionMng.m_lUserID == financeInfo.getConfirmUserID())){// 未、已确认、登录人＝确认人 %>
        <!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
        <!--打印委托付款凭证-->
        <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();"> 
        <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"--> 
		<input class="button1" name=add type=button value=" 修改 " onClick="Javascript:updateme();" onKeyDown="Javascript:updateme();">&nbsp;&nbsp;
		<input class="button1" name=add type=button value=" 删除 " onClick="Javascript:deleteme();" onKeyDown="Javascript:deleteme();">&nbsp;&nbsp;
        <% if ( lSourceType != 1 &&  lShowMenu != OBConstant.ShowMenu.NO) {%>
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class="button1" name=add type=button value=" 返回 " onClick="javascript:returnme()" onKeyDown="javascript:returnme()">&nbsp;&nbsp;
        <%}%>
        <% }
			/* 复核匹配*/
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType != 1) ) {// 已确认、登录人<>确认人 %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkmatchme();"--> 
		<input class="button1" name=add type=button value=" 复核 " onClick="Javascript:checkmatchme();" onKeyDown="Javascript:checkmatchme();">&nbsp;&nbsp;
        <% }
			/* 复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1) ) {// 已确认、登录人<>确认人 %>
        <!--img src="\webob\graphics\button_fuhe.gif" width="46" height="18" border="0" onclick="Javascript:checkme();"--> 
		<input class="button1" name=add type=button value=" 复核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">&nbsp;&nbsp;
        <% }
		  	/* 取消复核 */
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (sessionMng.m_lUserID == financeInfo.getCheckUserID())) {// 已复核、登录人＝复核人 %>
        <!--img src="\webob\graphics\button_QuXiaoFuHe.gif" width="101" height="20" border="0" onClick="Javascript:cancelcheckme();"-->
		<input class="button1" name=add type=button value=" 取消复核 " onClick="Javascript:cancelcheckme();" onKeyDown="Javascript:cancelcheckme();">&nbsp;&nbsp;
        <%}
			/* 签认及提交 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.CHECK) && (financeInfo.getIsNeedSign() == true)){// 已复核、需要被登录人签认=true %>
        <!--img src="\webob\graphics\button_qianrentijiao.gif" width="101" height="18" onclick="Javascript:signme();"--> 
		<input class="button1" name=add type=button value=" 签认 " onClick="Javascript:signme();" onKeyDown="Javascript:signme();">&nbsp;&nbsp;
        <%}
			/* 取消签认 */
		  	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) && (sessionMng.m_lUserID == financeInfo.getSignUserID())){// 已签认、登录人＝签认人 %>
        <!--img src="\webob\graphics\button_QuxiaoQianRen.gif" width="101" height="18" onclick="Javascript:cancelsignme();"--> 
		<input class="button1" name=add type=button value=" 取消签认 " onClick="Javascript:cancelsignme();" onKeyDown="Javascript:cancelsignme();">&nbsp;&nbsp;
        <%
	       }
			  ArrayList a = Config.getArray(ConfigConstant.EBANK_TRANS_PROCESSVOUCHERPRINT,null);
			  String strStatus = ""+financeInfo.getStatus(); 
        %>
        <!--img src="\webob\graphics\button_dayin.gif"  height="18" border="0" onclick="javascript:PrintConsignVoucher()"--> 
		<input type="Button" class="button1" value=" 打印 " width="46" height="18"   onclick="javascript:PrintConsignVoucher()"
		<%if(a!=null && !a.contains(strStatus))
               {
		%>
			   disabled
		<%
			   }
		%>
		>&nbsp;&nbsp; 
        <!--
            <img src="\webob\graphics\button_dayin.gif" width="46" height="18" border="0" onclick="Javascript:printme();">
                -->
        <% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
        <!--img src="\webob\graphics\button_guanbi.gif" width="46" height="18" border="0" onclick="Javascript:window.close();"-->
		<input type="Button" class="button1" value=" 关闭 " width="46" height="18"   onclick="window.close();"> &nbsp;&nbsp;
        <%}%>
      </td>
        </tr>
        --%>
   
	  <input type="hidden" name="RemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="Amount" value="<%= financeInfo.getAmount() %>">
	  <input type="hidden" name="ExecuteDate" value="<%= financeInfo.getExecuteDate() %>">
	  <input type="hidden" name="ConfirmUserID" value="<%= financeInfo.getConfirmUserID() %>">
	  <input type="hidden" name="ConfirmDate" value="<%= financeInfo.getConfirmDate() %>">
	  <input type="hidden" name="PayerAcctID" value="<%= financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="PayeeAcctID" value="<%= financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="lInstructionID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="txtID" value="<%= financeInfo.getID() %>">
	  <input type="hidden" name="nRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="strSuccessPageURL" value="">
	  <input type="hidden" name="strFailPageURL" value="">
	  <input type="hidden" name="strAction" value="">
	  <input name="dtmodify" value="<%=financeInfo.getDtModify()%>" type="hidden"/>
	  
	  <!-- add by mingfang 2007-05-23 -->
	  <!-- 签名用字段 -->
	  <input type="hidden" name="nPayeeAccountID" value="<%=financeInfo.getPayeeAcctID() %>">
	  <input type="hidden" name="nPayerAccountID" value="<%=financeInfo.getPayerAcctID() %>">
	  <input type="hidden" name="dAmount" value="<%=financeInfo.getAmount() %>">
	  <input type="hidden" name="tsExecute" value="<%=financeInfo.getFormatExecuteDate() %>">
	  <input type="hidden" name="submitUserId" value="<%=financeInfo.getConfirmUserID() %>">
	  
	  <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
	  <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	  <safety:certFilterHidden />
	  	  
	  </form>
<!--presentation end-->

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
		frmzjhb.action="../captransfer/c001-c.jsp?child=<%=lChild%>";
		frmzjhb.submit();
	}

	/* 确认处理函数 */
	function confirmme()
	{
		//showMenu();
		frmzjhb.action="../captransfer/C13.jsp";
		frmzjhb.submit();
	}
	
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//审批流审批
	function doApproval(currentButtonName)
	{
	    //modify by xwhe 2008-11-11 去掉审批意见必须项
	//	if(frmzjhb.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
	//	{
	//		alert("请输入审批意见");
	//		frmzjhb.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
	//		return;
	//	}
	    var currentButtonNameValue = currentButtonName.value;
	//	var ispassORrefuse;
	//    if(document.all('osIsPass').value=='0')
	//    {
	//      ispassORrefuse = frmzjhb.cmd0.value
	//    }
	//    else
	//    {	   
	//      ispassORrefuse = frmzjhb.cmd1.value
    //    }
		if (confirm("是否"+currentButtonNameValue+"?")) 
		{
		
		     //add by mingfang 2007-05-23 
			<%if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
			%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			frmzjhb.strSuccessPageURL.value='/capital/captransfer/c002-v.jsp';
			frmzjhb.strFailPageURL.value='/capital/captransfer/vAppc004-v.jsp';
			frmzjhb.action="<%=strContext%>" + '/capital/captransfer/c003-c.jsp';
			frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
			showSending();//显示正在执行
			frmzjhb.submit();
		}
	}	
	
	
			//取消审批   add by mingfang
	function cancelApproval()
	{
		if (confirm("是否取消审批?")) 
		{
		     //add by mingfang 2007-05-23 
			<%if(blnUseITrusCert){
				OBSignatureConstant.CapTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.CapTransfer.outSignValueArrayToView(out, "frmzjhb");
			%>
				var signatureValue = DoSign(frmzjhb,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>	
			frmzjhb.strSuccessPageURL.value='/capital/captransfer/c002-v.jsp';
			frmzjhb.strFailPageURL.value='/capital/captransfer/vAppc004-v.jsp';
			frmzjhb.action="<%=strContext%>" + '/capital/captransfer/c003-c.jsp';
			frmzjhb.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
			frmzjhb.submit();
		}
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
		frmzjhb.action="../captransfer/c001-c.jsp?child=<%=lChild%>";
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
		frmzjhb.action="../captransfer/c005-c.jsp?child=<%=lChild%>";
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
		frmzjhb.action="../check/C415.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frmzjhb.action="../check/C415.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* 签认处理函数 */
	function signme()
	{
		//showMenu();
		frmzjhb.action="../sign/s004-c.jsp";
		frmzjhb.txtisCheck.value = "1";
		showSending();
		frmzjhb.submit();
	}
	/* 取消签认处理函数 */
	function cancelsignme()
	{
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frmzjhb.action="../sign/s004-c.jsp";
			frmzjhb.txtisCheck.value = "0";
			showSending();
			frmzjhb.submit();
		}
	}
	/* 打印处理函数 */
	function printme()
	{
		frmzjhb.action="<%=Env.EBANK_URL%>capital/captransfer/S00-Ipt.jsp";
		frmzjhb.target="new_window";
		frmzjhb.submit();
		frmzjhb.target="";
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
<%@ include file="/common/SignValidate.inc" %>
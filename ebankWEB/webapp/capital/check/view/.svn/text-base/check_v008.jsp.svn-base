<%--
/*
 * 程序名称：check_v008.jsp
 * 功能说明：定期支取输出页面
 * 作　　者：
 * 完成日期：2010年10月12日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*,
				 com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.safety.signature.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.safety.info.*,
				  com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo"
%>
<%@ page import="com.iss.itreasury.util.OBFSWorkflowManager" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%	
	session.setMaxInactiveInterval(600); // 如果登录人在10分钟内不做任何操作，则退出登录。
	response.setHeader("Cache-Control","public");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<!--Header end-->
<% String strContext = request.getContextPath();%>
<%!
	/* 标题固定变量 */
	String strTitle = "[定期支取]";
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

		/* 从请求中获取信息 */
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();
		financeInfo = (FinanceInfo) request.getAttribute("financeInfo");
		financeInfo = financeInstr.findByID(financeInfo.getID(),sessionMng.m_lUserID,sessionMng.m_lCurrencyID);
		boolean blnIsNeedApproval = OBSignatureUtil.isNeedApproval(sessionMng,financeInfo);
		
        /**
         * presentation start
         */
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
		String strTransNo = financeInfo.getID() + "";
%>

<%

		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;

		
		//CFCA证书验签
	//	boolean useCFCACert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.CFCA);
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


<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<link rel="stylesheet" href="/webloan/css/approve.css" type="text/css">
<form method="post" name="frm">
<table width="100%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1 width="98%" align="center">
				<TR>
			       <td class=title><span class="txt_til2">定期支取</span></td>
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
<%} %>


<% 
		if (lSourceType != 1) 
		{
%>


	 <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
          <tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 定期支取确认</td>
				<td width="17"><a class=lab_title3></td>
			</tr>
        </table></td>
      </tr>
    </table>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr class="MsoNormal">
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>定期支取将<%
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" nowrap> &nbsp;&nbsp;定期账户资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>          
        </table></td>
      </tr>
    </table>
	  <table width="98%" border="0" cellspacing="0" cellpadding="0" class =normal align="center">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25" class="MsoNormal">定期账户名称：</td>
          <td width="430" height="25"class="MsoNormal"><%=financeInfo.getPayerName()==null || financeInfo.getPayerName()==""?"&nbsp;":financeInfo.getPayerName()%></td>
          <td width="5" height="25"></td>
        </tr>
       
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          
    <td width="130" height="25"class="MsoNormal">定期账号：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getNoLineAccountNo(financeInfo.getPayerAcctNo().toString()) %></td>
          <td width="5"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
		<tr class="MsoNormal">
          <td width="5" height="25"></td>
          
		<td width="130" height="25" class="MsoNormal">定期存款单据号：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getDepositNo() %></td>
          <td width="5"></td>
        </tr>
<!-- 自动续存 -->
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
<!-- End -->		        
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
      </table>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> &nbsp;&nbsp;收款方资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>          
        </table></td>
      </tr>
    </table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr class="MsoNormal">
          <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
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
        </tr>
        <input type="hidden" id="aaa" value="<%= financeInfo.getFormatRemitType() %>">
        <tr id="payeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="payeeBankNo" class="MsoNormal"> 
          <td width="5" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= NameRef.getNoLineAccountNo(financeInfo.getPayeeAcctNo().toString()) %></td>
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
        <tbody id="aa" style="">
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
        </tbody>
        <tbody id="cc" style="">
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
        </tbody>
        <tr id="line1" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        
      </table>  
		  </td>
		  <td width="1" class="MsoNormal">
		  </td>
		  <td width="49%">
		  	
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class=normal align="center">
        <tr class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="graytext">利息：</span></p>
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
        <input type="hidden" id="bbb" value="<%= financeInfo.getFormatInterestRemitType() %>">
        <tr id="InterestPayeeBankNoLine" class="MsoNormal"> 
          <td colspan="3" height="1"></td>
        </tr>
        <tr id="InterestPayeeBankNO" class="MsoNormal"> 
          <td width="4" height="25"></td>
          <td width="120" height="25"> 
            <p><span class="MsoNormal">收款方账号：</span></p>
          </td>
          <td width="240" height="25"><%= NameRef.getNoLineAccountNo(financeInfo.getInterestPayeeAcctNo().toString()) %></td>
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
            <p><span class="graytext">汇入行名称：</span></p>
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
            <p><span class="graytext"></span></p>
          </td>
          <td width="240" height="25"> 
          </td>
        </tr>
        </tbody>
      </table>  
		  </td>
        </tr>
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
	  <br>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="110" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2"> &nbsp;&nbsp;划款资料</td>
	<td width="17"><a class=lab_title3></td>
</tr>         
        </table></td>
      </tr>
    </table>
      <table width="98%" border="0" cellspacing="0" cellpadding="0" class = normal align="center">
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td height="25" class="MsoNormal" width="110">金额：</td>
          <td width="3%" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatAmount() %></td>
          <td width="5" height="25"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">执行日：</td>
          <td width="430" height="25" class="MsoNormal"><%= financeInfo.getFormatExecuteDate() %></td>
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
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
      <tr>
        <td><table width="170" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> &nbsp;&nbsp;交易申请处理详情</td>
	<td width="17"><a class=lab_title3></td>
</tr>          
        </table></td>
      </tr>
    </table>
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" class=list_table >
        <tr class="itemtitle">
          <td width="20%" height="26">
            <p align="center">序列号</p>
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
        <tr valign="middle">
          <td width="20%" align="center"  height="25">
            <div align="center">1</div>
          </td>
          
          <td width="190" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td>
            <div align="center">录入</div>
          </td>
          
          <td     width="277" height="25">
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
          <td width="20%" align="center"     height="25">
            <div align="center" class="graytext">2</div>
          </td>
          
          <td     width="190" height="25">
            <div align="center"><%= financeInfo.getCheckUserName()==null?"机核":financeInfo.getCheckUserName() %></div>
          </td>
          
          <td     width="190" height="25">
            <div align="center">复核</div>
          </td>
          
          <td     width="277" height="25">
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
          <td width="20%" align="center"     height="25">
            <div align="center" class="graytext">3</div>
          </td>
          
          <td     width="190" height="25">
            <div align="center"><%= financeInfo.getSignUserName() %></div>
          </td>
          
          <td     width="190" height="25">
            <div align="center">签认</div>
          </td>
          
          <td     width="277" height="25">
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
    <%--  <table align="center" width="774" border="0" cellspacing="0" cellpadding="0" class="normal">
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
		        	transTypeID = '<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>' 
		        	transSubTypeID  ='<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>' 
		        	currencyID = '<%=sessionMng.m_lCurrencyID%>' 
		        	officeID = '<%=sessionMng.m_lOfficeID%>'
		        	clientID = '<%=sessionMng.m_lClientID%>'
		        	islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
     <%--      </td>
        </tr>
      </table>
--%>
	 <% if(isNeedApproval.equals("true")){ %>
	 
		<tr>
			<td valign="top" colspan="3">
				<table  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="1"><a class=lab_title1></td>
					<td class="lab_title2" style="width:150px">&nbsp;历史审批意见</td>
					<td width="800"><a class=lab_title3></td>
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
		
			<%-- <iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=strTransNo%>&&transType=<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER%>'
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
		   	if ((financeInfo.getStatus() == OBConstant.SettInstrStatus.SAVE || financeInfo.getStatus() == OBConstant.SettInstrStatus.APPROVALED) && (sessionMng.m_lUserID != financeInfo.getConfirmUserID()) && (lCheckType == 1)&&!search.equals("1") ) {// 已确认、登录人<>确认人 %>
				<input class=button1 name=add type=button value=" 复 核 " onClick="Javascript:checkme();" onKeyDown="Javascript:checkme();">				
		  <% } %>		  	
			<% if ( lShowMenu == OBConstant.ShowMenu.NO) {%>
			<input type="Button" class="button1" value=" 关 闭 " width="46" height="18"   onclick="window.close();">			
        	<%}%>
		 </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
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
	  <input type="hidden" name="hdnRemitType" value="<%= financeInfo.getRemitType() %>">
	  <input type="hidden" name="txtisCheck" value="">
	  <input type="hidden" name="txtTransType" value="<%=lTransType %>">
	  <input type="hidden" name="menu" value="<%=lShowMenu == OBConstant.ShowMenu.NO?"hidden":""%>">
	  <input type="hidden" name="txtTransType" value="<%=financeInfo.getTransType() %>">
	  <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">
	  	   <!-- 签名用字段 -->
	  <input type="hidden" name="sFixedDepositNoCtrl" value="<%=financeInfo.getDepositNo() %>">
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
	/* 复核处理函数 */
	function checkme()
	{
		//showMenu();
		
		<%
			if(blnUseITrusCert){
				OBSignatureConstant.FixedToCurrentTransfer.outSignNameArrayToView(out);
				OBSignatureConstant.FixedToCurrentTransfer.outSignValueArrayToView(out);
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
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
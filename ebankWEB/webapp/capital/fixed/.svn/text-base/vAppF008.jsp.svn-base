<!--
/*
 * 程序名称：vAppF008.jsp
 * 功能说明：定期续存提交控制页面
 * 作　　者：ypxu
 * 完成日期：07-04-27
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
				  com.iss.itreasury.util.OBFSWorkflowManager,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.settlement.util.NameRef"
%>
<jsp:directive.page import="java.text.DateFormat"/>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ include file="/common/common.jsp" %>
<head>
	<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
</head>
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
		
		boolean blnUseITrusCert = Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.ITrus);
		boolean blnNotBeFalsified = true;
/*		if(blnUseITrusCert)
		{
			String[] nameArray = OBSignatureConstant.DriveFixdePosit.getSignNameArray();
			String[] valueArray = OBSignatureConstant.DriveFixdePosit.getSignValueArrayFromInfo(financeInfo);		
			if(financeInfo.getNextLevel()==1  && !financeInfo.isRefused()){
				//特殊处理 //modify by xwhe 2009-1-4
			 	valueArray[OBSignatureConstant.DriveFixdePosit.iArrayLength-1] = "-1";
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
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<isswf:init/>
<% if(request.getAttribute("msg")!=null && !request.getAttribute("msg").equals("")){%>
<script language="javascript">
	alert("<%=(String)request.getAttribute("msg")%>");
</script>
<%} %>
      <form name="frm" method="post">
      <input type="hidden" name="dtmodify" value="<%=financeInfo.getDtModify()==null?"":financeInfo.getDtModify()+"" %>">

    
    <table width="80%" cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">到期续存</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
		</td>
	 </tr>
	</table>
   
<br/>
       <table width="170" border="0" cellspacing="0" cellpadding="0">
       
          
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px"> 到期续存通知</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table>
	  <table width=80% border="0" cellspacing="0" cellpadding="0" class = normal align="">
        <tr >
          <td colspan="3" height="1"class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25"class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p><br>到期续存将将<%if(!OBFSWorkflowManager.isAutoCheck()){ if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALED){%>于复核后才<%} else if(financeInfo.getStatus()==OBConstant.SettInstrStatus.APPROVALING){%>于审批并复核后才<%} }%>提交到<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
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
         <table width="170" border="0" cellspacing="0" cellpadding="0">
       
          
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">到期存款账户</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table>
	  <table width=80% border="0" cellspacing="0" cellpadding="0" class = normal align="">
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
    	<td width="130" height="25"class="MsoNormal">定期存款子账号：</td>
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
  

            <table width="170" border="0" cellspacing="0" cellpadding="0">
       
          
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">到期续存</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table>
  <table width=80% border="0" cellspacing="0" cellpadding="0" class = normal align="">
    <tr class="MsoNormal"> 
      <td colspan="5" height="1" class="MsoNormal"></td>
    </tr>
	<tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25"  class="MsoNormal"> <p>新定期存款起始日：</p></td>
      <td width="430" height="25" class="MsoNormal"><%= String.valueOf(financeInfo.getSDepositBillStartDate()).substring(0,10)%>&nbsp;&nbsp;新定期存款到期日：<%= String.valueOf(financeInfo.getSDepositBillEndDate()).substring(0,10)%></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal"> <p>定期存款期限：</p></td>
      <td width="430" height="25" class="MsoNormal"><%= financeInfo.getSDepositBillPeriod()>10000?financeInfo.getSDepositBillPeriod()-10000:financeInfo.getSDepositBillPeriod() %><%=(financeInfo.getSDepositBillPeriod() > 10000)?"天":"个月"%></td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
    <tr class="MsoNormal"> 
      <td width="5" height="25" class="MsoNormal"></td>
      <td width="130" height="25" class="MsoNormal"> <p>金额：</p></td>
      <td width="430" height="25" class="MsoNormal">
       <%= financeInfo.getFormatAmount() %>
      </td>
      <td width="5" height="25" class="MsoNormal"></td>
    </tr>
  </table>

      <br>
   
    
        <table width="170" border="0" cellspacing="0" cellpadding="0">      
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">利息处理</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table>
      <table width=80% border="0" cellspacing="0" cellpadding="0" class = normal align="">
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
          <td height="25" class="MsoNormal" width="110">利息转至活期账户：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"></div>
          </td>
          <td width="430" height="25" class="MsoNormal">利息转至活期账号：<%=NameRef.getNoLineAccountNo(NameRef.getAccountNoByID(financeInfo.getSDepositInterestToAccountID()).toString())%>&nbsp;&nbsp;</td>
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
        	(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) //已拒绝 		   
		{
%>
	
    
      <table width="170" border="0" cellspacing="0" cellpadding="0">      
          <tr>
	<td width="3"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">交易申请处理详情</td>
	<td width="17"><a class=lab_title3></td>
</tr>
        </table>
      <table width=80% border="1" align="" cellpadding="0" cellspacing="0" class=normal >
       <thead>
        <tr >
          <td width="63" height="19">
            <p align="center">序列号</p>
          </td>
          
          <td  height="19" valign="middle"  width="190">
            <div align="center">用户</div>
          </td>
         
          <td  height="19" valign="middle"  width="198">
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19" valign="middle" width="269">
            <div align="center">时间和日期</div>
          </td>
        </tr>
        </thead>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">1</div>
          </td>
          
          <td   width="190" height="25">
            <div align="center"><%= financeInfo.getConfirmUserName() %></div>
          </td>
          
          <td   width="198" height="25">
            <div align="center">录入</div>
          </td>
          
          <td  width="269" height="25">
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
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
         		{  
%>
        <tr valign="middle">
          <td width="63" align="left"  height="25">
            <div align="center">2</div>
          </td>
          
          <td  width="190" height="25">
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
				if (((financeInfo.getStatus() == OBConstant.SettInstrStatus.SIGN) || // 已签认
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.DEAL) || //处理中
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.FINISH)|| //已完成
        			(financeInfo.getStatus() == OBConstant.SettInstrStatus.REFUSE)) &&//已拒绝
					(financeInfo.getSignUserName() != null))
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
 </table>
      <br>
    
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
		        	clientID = '<%=sessionMng.m_lClientID%>'/>
     
     
      <br>
      <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">历史审批意见</td>
				<td width="800"><a class=lab_title3></td>
			</tr>
		</table>    
	  <table border="0" width=80% cellspacing="0" cellpadding="0" align="" class=normal>
	  <TR>
		  <TD colspan="3"><%--<iframe src="<%=strContext%>/HistoryOpinionFrame.jsp?transNo=<%=financeInfo.getID()+ ""%>&&transType=<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>--%>
		   <fs:HistoryOpinionFrame
					  strTransNo='<%=financeInfo.getID()>0?String.valueOf(financeInfo.getID()):""%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.DRIVEFIXDEPOSIT%>'
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
		<table width="80%" >	
		<tr>
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
	  
   <input type="hidden" name="nPayeeAccountID"         value="<%=financeInfo.getPayeeAcctID()%>">     
   <input type="hidden" name="sPayerAccountNoZoomCtrl" value="<%=financeInfo.getSDepositBillNo()%>">
   <input type="hidden" name="dAmount"                 value="<%=financeInfo.getAmount()%>">
   <input type="hidden" name="nFixedDepositTime1"      value="<%=financeInfo.getSDepositBillPeriod() %>">
   <input type="hidden" name="tsExecute"               value="<%=DataFormat.getDateString(financeInfo.getExecuteDate())%>">
   <input type="hidden" name="dPayerStartDate"         value="<%=DataFormat.getDateString(financeInfo.getSDepositBillStartDate())%>">
   <input type="hidden" name="interesttype"            value="<%=financeInfo.getSDepositInterestDeal()%>">
   <input type="hidden" name="lInterestToAccountID"    value="<%=financeInfo.getSDepositInterestToAccountID()%>">
   <input type="hidden" name="submitUserId"            value="<%=financeInfo.getConfirmUserID()%>">
   <input type="hidden" name="dPayerCurrStartDate"     value="<%=DataFormat.getDateString(financeInfo.getSDepositBillStartDate())%>">
  
  	  
   <input type="hidden" name="actionStatus" value="<%=financeInfo.getActionStatus() %>">
   <input type="hidden" name="rsStatus" value="<%=financeInfo.getStatus() %>"> 
	 </td>
  </tr>
  
</table>  
	  
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
				OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
				OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f008-1-c.jsp?operate=doApproval';
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
				OBSignatureConstant.DriveFixdePosit.outSignNameArrayToView(out);
				OBSignatureConstant.DriveFixdePosit.outSignValueArrayToViewForView(out);
			%>
				var signatureValue = DoSign(frm,nameArray,valueArray);
				if(signatureValue == "") return;//签名不成功，不允许提交表单		
								
			<%}%>
			
			
			//frm.strSuccessPageURL.value='/capital/query/f001-c.jsp';
			//frm.strFailPageURL.value='vAppF003.jsp';
			frm.action="<%=strContext%>" + '/capital/fixed/f008-1-c.jsp?operate=cancelApproval';
			frm.submit();
		}
	}	
	

	/*返回处理函数 */
	function returnme()
	{
		frm.lInstructionID.value = "-1";
		frm.action="../fixed/f006-c.jsp";
		frm.submit();
	}

    function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}

	/* 取消复核处理函数 */
	function cancelcheckme()
	{
		if (confirm("确定取消复核吗？"))
		{
			//showMenu();
			frm.action="../check/C415.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
	}

	function updateme()
	{
		//showMenu();
		frm.action="../fixed/f006-c.jsp";
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
		frm.action="../fixed/f009-1-c.jsp?operate=delete";
		showSending();
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
		function checkme()
	{
		//showMenu();
		frm.action="../check/C415.jsp";
		frm.txtisCheck.value = "1";
		showSending();
		frm.submit();
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
		if (confirm("确定取消吗？"))
		{
			//showMenu();
			frm.action="../sign/s004-c.jsp";
			frm.txtisCheck.value = "0";
			showSending();
			frm.submit();
		}
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
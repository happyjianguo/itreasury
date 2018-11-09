<%
/**页面功能说明
 * 页面名称 ：v093.jsp
 * 页面功能 : 对账单打印
 * 作    者 ：wenboshang
 * 日    期 ：2005-10-28
 * 简单实现说明：
 *	1、对账单信息打印
 * 特殊说明 ：
 * 修改历史 ：
 */
%>

<%@ page contentType = "text/html;charset=GBK" %>

<!--类导入部分开始-->
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AcctTransInfo"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.AccountInfo"/>
<jsp:directive.page import="com.iss.itreasury.util.Env"/>
<%@ page import="com.iss.itreasury.ebank.util.*"%>
<%@ page import="com.iss.itreasury.ebank.system.util.*"%>
<%@ page import="com.iss.itreasury.ebank.util.NameRef"%>
<%@ page import="com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountSignatureBiz" %>
<%@ page import="java.util.*" %>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="com.iss.itreasury.ebank.system.dataentity.QueryBillPrintParam"/>
<jsp:directive.page import="com.iss.itreasury.util.DataFormat"/>
<%@ page import="java.util.*"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<!--类导入部分结束-->

<%
        		
		//签章相关设置//////////////////////////////////////////////////// add by zhanglei 2010-05-20
     	String basePath = request.getScheme() +"://"+request.getServerName()+":"+request.getServerPort();//服务器端请求路径
		String nowDate = Env.getSystemDateTimeString();//当前日期
		String officeName = Env.getClientName();//办事处名称
		//查看是否有签章权限--start----
		long officeId = sessionMng.m_lOfficeID;
		long clientId = sessionMng.m_lClientID;
		long bzid = sessionMng.m_lCurrencyID;
		long userId = sessionMng.m_lUserID;
		//String nbilltypeId = EBankDocRiht.ebankDocType[5][0];
		String nbilltypeId = String.valueOf(EBankDocRiht.ZHJYMX);
		OBAccountSignatureBiz osb  =new OBAccountSignatureBiz();
		boolean hasRight = osb.checkHasRight(clientId,officeId,bzid,nbilltypeId,userId);
		//查看是否有签章权限--end----
		double px=300;//横坐标
		double py = 200;//纵坐标
		//////////////////////////////////////////////////////////////
    try
	{
		//emoduleid等于6代表网银模块
		//String emoduleid = (String)session.getAttribute("emoduleid");
		//if ( session.getAttribute("eofficeID")==null || !emoduleid.equals("6") || session.getAttribute("eclientid")==null) {
		//		OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
        //		out.flush();
		//		return;
		//}
		String strTitle = null;
		//用户登录检测 
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
        }
		/**页面校验开始（用户登录校验、用户权限校验、重复请求校验）*/
		JSPLogger.info("*******进入页面--account\\view\\v093-account_ebank.jsp*******");
		/**页面校验结束*/
		//获得PageLoaderKey
		//String strPageLoaderKey = (String)request.getAttribute("_pageLoaderKey");		
		/**业务逻辑开始*/		
		String strContext = request.getContextPath();	
		/**返回结果对象之一：账户期初余额**/
		double beginBalance  = Double.NaN;
		beginBalance = ((Double)request.getAttribute("balanceCol")).doubleValue();		
		/**返回结果对象之二：账户历史交易信息**/
		Object[] queryResults = null;
		queryResults = (AcctTransInfo[])request.getAttribute("transInfos");
		JSPLogger.info("账户历史交易    queryResults :  " + queryResults + " length:" + ((queryResults != null)?queryResults.length:-1));		
		/**返回结果对象之三：获得银行账户基本信息**/
		AccountInfo acctInfo = new AccountInfo();
		//acctInfo = (AccountInfo)request.getAttribute("acctInfo");	
		/**查询条件**/
		QueryBillPrintParam param = new QueryBillPrintParam();
		param = (QueryBillPrintParam)request.getAttribute("param");	
		if(param!=null)
		{
			long acctId = param.getAccountId();
		    String strAccountNo = NameRef.getAccountNOByAccountID(acctId);
			acctInfo.setAccountNo(strAccountNo);
			String strAccountName = NameRef.getAccountNameByAccountID(acctId);
			acctInfo.setAccountName(strAccountName);
			acctInfo.setClientId(param.getClientIdFrom());
			acctInfo.setCurrencyType(param.getCurrencyType());	
		}
		/**业务逻辑结束*/		
		//定义统计变量
		double startBalance          = 0.00;  //期初余额
		double sumDebitAmount  		 = 0.00;  //借方金额合计
		double sumCreditAmount 		 = 0.00;  //贷方金额合计
		double sumDebitAmountPerDay  = 0.00;  //每日借方金额合计
		double sumCreditAmountPerDay = 0.00;  //每日贷方金额合计
		double beginBalancePerDay	=0.00;	//每日的期初余额		
		String strDebitAmount = null;
		String strCreditAmount = null;		
		Date   statDate       		 = null;  //日期
		OBHtml.showLOANPrintHead(out,true, "A4", "", -1);		
		/**页面显示开始*/       
%>
<%
	//IPrintTemplate.showPrintHead(out,false,"A4","",1,sessionMng.m_lOfficeID);
	eBankPrint.showPrintReport(out,sessionMng,"A4",2,false);
%>
<TABLE class="" width="950" align="center">	
	  <TR>
    		<TD class=FormTitle align="center"><B>账户交易明细</B></TD>
		</TR>
			<TD>
				<TABLE  width="950" align="center" border="0" cellspacing="0" cellpadding="0" align="center">
					<TBODY>
						<TR>
						<BR>
							<TD valign="top" width="100%">
							<TABLE   width="950"  border="0" cellspacing="0" cellpadding="0" class="table1"  align="center">
							<TBODY>
						<TR>							
							<TD class="td-right"> <font style="font-size: 12px">&nbsp;客户编号:</font></TD>
							<TD class="td-right"> <font style="font-size: 12px">&nbsp;<%=NameRef.getClientCodeByID( acctInfo.getClientId() )%></font></TD>
							<TD class="td-right"><font style="font-size: 12px">&nbsp;客户名称:</font></TD>
							<TD class="td-right"><font style="font-size: 12px">&nbsp;<%=NameRef.getClientNameByID( acctInfo.getClientId() )%></font></TD>							
						</TR>						
						<TR>							
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;账号:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=acctInfo.getAccountNo()%></font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;账户名称:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=acctInfo.getAccountName()%></font></TD>
						</TR>						
						<TR>							
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;币种:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=NameRef.getCurrencyNameByID( acctInfo.getCurrencyType() )%></font></TD>
							<TD class=td-topright >&nbsp;</td>
							<TD class=td-topright >&nbsp;</td>
  						</TR>
						<TR>						
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;日期 从:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=  DataFormat.formatDate(param.getTransactionStartDate(),1)%></font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;到:</font></TD>
							<TD class=td-topright ><font style="font-size: 12px">&nbsp;<%=  DataFormat.formatDate(param.getTransactionEndDate(),1)%></font></TD>
					</TABLE>
			</TD>
		</TR>			
	</TBODY>
</TABLE>
<BR>
<TABLE  width="950" border="0" cellspacing="0" cellpadding="0" align="center">
<TBODY> 		
		<TR>
			<TD>
				<TABLE  width="950" align="center" border="0" cellspacing="0" cellpadding="0"  class="table1" >
					<TBODY >						
						<TR >
				            <TD  class="td-right" width="8%" align="center">&nbsp;日期</TD>
				            <TD  class="td-right" width="11%" align="center">&nbsp;摘要</TD>
				            <TD  class="td-right" width="10%" align="center">&nbsp;单据号</TD>
						    <TD  class="td-right" width="16%" align="center">&nbsp;对方账号</TD>
							<TD  class="td-right" width="16%" align="center">&nbsp;对方账户名称</TD>
							<TD  class="td-right" width="13%" align="center" nowrap>&nbsp;借方金额</TD>
							<TD  class="td-right" width="13%" align="center" nowrap>&nbsp;贷方金额</TD>
							<TD  class="td-right" width="13%" align="center" nowrap>&nbsp;余额</TD>
						</TR>	
						<TR >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright align="center">&nbsp;<b>期初余额</b></TD>
				            <TD class=td-topright >&nbsp;</TD>
						   	<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright  align="right">&nbsp;<b>
													<%		
										startBalance = beginBalance;
										beginBalancePerDay = beginBalance;										
										%>
										<b>
										<%
										out.println( DataFormat.formatNumber(beginBalance,2) );
										%>
										</b>&nbsp;</TD>
						</TR>
						
						<%
						if( queryResults != null && queryResults.length >0 )
						{
							
							//临时日期
							statDate = ( (AcctTransInfo)queryResults[0]).getTransactionTime();
							
							for(int i = 0;i<queryResults.length;i++)
							{
								AcctTransInfo info = (AcctTransInfo)queryResults[i];
								
								if( info.getDirection() == AccountInfo.DEBIT )
								{
									sumDebitAmount   += info.getDebitAmount();
									strDebitAmount = DataFormat.formatNumber(info.getDebitAmount(),2);
									strCreditAmount = "";
								}
								else if( info.getDirection() == AccountInfo.CREDIT )
								{
									sumCreditAmount  += info.getCreditAmount();
									strCreditAmount = DataFormat.formatNumber(info.getCreditAmount(),2);
									strDebitAmount = "";																	
								}
								
								if( queryResults.length == 1 || info.getTransactionTime().equals(statDate) )
								{
									//汇总账户同一天的借贷方金额
									sumDebitAmountPerDay   += info.getDebitAmount();
									sumCreditAmountPerDay  += info.getCreditAmount();
								}
								
								if(  !info.getTransactionTime().equals(statDate) )
								{
									beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );								
									%>
									<TR >
							            <TD class=td-topright >&nbsp;<%=DataFormat.formatDate(statDate,1)%></TD>
							            <TD class=td-topright align="center"><b>&nbsp;本日合计</b></TD>
							            <TD class=td-topright >&nbsp;</TD>
									   	<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumDebitAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumCreditAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap><b><%=DataFormat.formatNumber(beginBalancePerDay ,2  )%></b></TD>
									</TR>
									<%
									//如果不是同一天的数据,则再重新痴思化统计数据
									statDate = info.getTransactionTime();
									sumDebitAmountPerDay   = info.getDebitAmount();
									sumCreditAmountPerDay  = info.getCreditAmount();
								}
								String strBalance ="";
								double balance = beginBalance+info.getCreditAmount()-info.getDebitAmount();
								beginBalance = balance;
								strBalance = DataFormat.formatNumber(balance , 2);
								if(i==queryResults.length-1){
									%>
						<TR  height="0" >
				            <TD height="0"  width="8%" align="center"></TD>
				            <TD height="0"  width="11%" align="center"></TD>
				            <TD height="0" width="10%" align="center"></TD>
						    <TD height="0"   width="16%" align="center"></TD>
							<TD height="0" width="16%" align="center"></TD>
							<TD height="0"  width="13%" align="center" ></TD>
							<TD height="0"   width="0" align="right"  ></TD>
							<TD height="0"  width="13%" align="center" id="signaturePosition_5" ></TD>
						</TR>
									<%
								}							
						%>
						<TR >
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatDate(info.getTransactionTime(),1)%></TD>
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getAbstractInfo())%></TD>
				            <%
				                  String infoCheckNo = "";
				                  if(info.getCheckNo() != null && info.getCheckNo().trim().length() > 0 && !info.getCheckNo().endsWith("0")){
				                         infoCheckNo = info.getCheckNo();
				                  }
				             %>
				            <TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(infoCheckNo) %></TD>
						   	<TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getOppAccountNo())%></TD>
							<TD class=td-topright  style="word-break :break-all;">&nbsp;<%=DataFormat.formatString(info.getOppAccountName())%></TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap>&nbsp;<%=strDebitAmount%>&nbsp;</TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap>&nbsp;<%=strCreditAmount%>&nbsp;</TD>
							<TD class=td-topright  style="word-break :break-all;" align="right" nowrap><%=strBalance%></TD>
						</TR>
						<%
								/** 如果只有一条记录，则在这条记录后面输出一行本日合计
								 * &如果已经是最后一条记录了，则在这条记录后面输出一行本日合计
								 **/
								if( queryResults.length == 1 || i == queryResults.length -1 )
								{
									beginBalancePerDay = Arithmetic.add( beginBalancePerDay,Arithmetic.sub(sumCreditAmountPerDay,sumDebitAmountPerDay) );																
									%>
									<TR >
							            <TD class=td-topright >&nbsp;<%=DataFormat.formatDate(statDate,1)%></TD>
							            <TD class=td-topright align="center"> &nbsp;<b>本日合计</b></TD>
							            <TD class=td-topright >&nbsp;</TD>
									   	<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright >&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumDebitAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumCreditAmountPerDay,2)%></b>&nbsp;</TD>
										<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(	beginBalancePerDay ,2  )%></b>&nbsp;</TD>
									</TR>
									<%
									//痴思化统计数据清零
									statDate = info.getTransactionTime();
									sumDebitAmountPerDay  = 0.00;
									sumCreditAmountPerDay = 0.00;
								}								
							}
						}
						else
						{
						%>
						<TR  height="0" >
				            <TD height="0"  width="8%" align="center"></TD>
				            <TD height="0"  width="11%" align="center"></TD>
				            <TD height="0" width="10%" align="center"></TD>
						    <TD height="0"   width="16%" align="center"></TD>
							<TD height="0" width="16%" align="center"></TD>
							<TD height="0"  width="13%" align="center" ></TD>
							<TD height="0"   width="0" align="right"  ></TD>
							<TD height="0"  width="13%" align="center" id="signaturePosition_5" ></TD>
						</TR>
						<TR >
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
						</TR>
						<%
						}
						%>

						<TR >
						    <TD class=td-topright >&nbsp;</TD>
				            <TD class=td-topright align="center">&nbsp;<b>期末余额</b></TD>
				            <TD class=td-topright >&nbsp;</TD>
						   	<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright >&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumDebitAmount,2)%></b>&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(sumCreditAmount,2)%></b>&nbsp;</TD>
							<TD class=td-topright  align="right" nowrap> &nbsp;<b><%=DataFormat.formatNumber(Arithmetic.add( startBalance,Arithmetic.sub(sumCreditAmount,sumDebitAmount) ),2)%></b>&nbsp;</TD>
						</TR>						
						</TBODY>
				</TABLE>
			</TD>
		</TR>		
</TBODY>
</TABLE>
</TD>
</TABLE>
<BR>
<table   width="950"  border="0" align="center">
	<tr>				
        <td width="70%" > &nbsp;</td>
		<td width="30%" align="right" id="printDate">打印时间：<%=DataFormat.formatDate(Env.getSystemDateTime(),DataFormat.FMT_DATE_YYYYMMDD)%></td>
	</tr>		
</table>
<!--页面表单结束-->
<!--页面脚本开始-->
<!--页面脚本元素结束-->
<%
	}
	catch( Exception exp )
	{
		exp.printStackTrace();
		JSPLogger.error(exp.getMessage());
	}
	//显示页面尾	
	/**页面显示结束*/
%>

<%

	if(hasRight){

 %>
<BODY language="javascript" onResize="ReSetSignaturePosition()" style="margin-top:0;margin-bottom:0;padding-top:0;padding-bottom:0">	
<OBJECT id="SignatureControl"  codebase="/websignature/cab/iSignatureHTML.cab#Version=7,1,0,196"  classid="clsid:D85C89BE-263C-472D-9B6B-5264CD85B36E" width=0 height=0>
<param name="ServiceUrl" value="<%=basePath%>/NASApp/SignatureServlet">                       <!--读去数据库相关信息-->
<param name="WebAutoSign" value="0">                     <!--是否自动数字签名(0:不启用，1:启用)-->
<param name="PrintControlType" value=2>                  <!--打印控制方式（0:不控制  1：签章服务器控制  2：开发商控制）-->
<param name="MenuDocVerify" value=false>                 <!--菜单文档验证文档-->
<param name="MenuServerVerify" value=false>              <!--菜单在线验证,网络版本专用-->
<param name="MenuDigitalCert" value=false>               <!--菜单数字证书-->
<param name="MenuDocLocked" value=false>                 <!--菜单文档锁定-->
<param name="MenuDeleteSign" value=false>                <!--菜单撤消签章-->
<param name="MenuMoveSetting" value=true>                <!--菜单禁止移动-->
<param name="PrintWater" value=false>                    <!--是否打印水印-->
</OBJECT>
</BODY>
<script language="javascript">
	function setPirntPosition(){
		    oldScrollTop=document.body.scrollTop;
			document.body.scrollTop=0;
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = 899;
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
	window.onbeforeprint=setPirntPosition;
	window.onafterprint=ReSetSignaturePosition;
		try{
			var   x,y;   
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-100);
		    document.all.SignatureControl.EnableMove = "false";          //设置签章是否可以移动
		    document.all.SignatureControl.PassWord = "123456";           //签章密码,系统默认为"",当设置改参数后点签章后弹出的选择签章窗体中的密码将默认为该密码      
		    document.all.SignatureControl.ShowSignatureWindow = "0";     //读取、设置选择签章的窗口是否可见
		    document.all.SignatureControl.FieldsList = "strTransNos=组合业务编号";          //读取、设置签章保护信息列表
		    document.all.SignatureControl.SaveHistory = "false";         //读取、设置是否保存历史记录true-false
		    document.all.SignatureControl.UserName = "<%=Env.getClientName()%>"; //读取、设置签章的用户名称
		    document.all.SignatureControl.PositionByTagType = 0;
		     	document.all.SignatureControl.Position(sx,sy);      //设置签章什么位置在Position处(0:左上角、1:中间、2:右上角)
		    document.all.SignatureControl.ValidateCertTime = false;      //设置是否验证证书过期或未到期
		    document.all.SignatureControl.ExtParam = "11111111|11";//transNo
		    document.all.SignatureControl.ExtParam1 = "<%=nowDate%>";          //设置签章附加信息
		    //document.all.SignatureControl.SetWaterInfo("网络专用","隶书",0X0000FF,0);//设置签章数字水印信息
		    document.all.SignatureControl.WebSetFontOther(true,"","0","宋体",7,"$0000FF",false);//设置签章图案附属信息(日期时间、签章人员、意见等)显示模式
		    document.all.SignatureControl.DefaultSignTimeFormat = 8;    //设置签章附加时间默认格式
		    document.all.SignatureControl.SetSignTextOffset(0,30);      //设置盖章是的附加信息(包括时间)的偏移量
		  }catch(e){
		    alert(e);
		  }
		    try{
		    	document.all.SignatureControl.RunSignature();               //执行签章  
		    }catch(e){
		    	alert("添加签章错误，请联系开发人员");
		    }
		  		 	//如果窗口大小变化了，签章的位置也要改变。  add by zhanglei  2010.06.11	    
	function ReSetSignaturePosition(){
			var sx;
			var sy;
			oRect   =   document.getElementById("printDate").getBoundingClientRect();   
			var width = document.getElementById("printDate").clientWidth;
			x=oRect.left   
			y=oRect.top   
			sy=parseInt(y)-80;
			sx = (parseInt(x)+parseInt(width)-120);
			document.all.SignatureControl.MovePositionToNoSave(sx,sy); 
	}
</script>
<%
	}
%>
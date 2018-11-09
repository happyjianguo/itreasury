<%--
 页面名称 ：d024-v.jsp
 页面功能 : 新增贴现申请-银行承兑汇票贴现申请打印 显示页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				 java.util.*,
                 javax.servlet.*,
				 com.iss.itreasury.system.approval.bizlogic.ApprovalBiz,
				 com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d024-v.jsp*******");
	
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
		String strTmp = "";
		int nTmp = 0;
		int TRACINGNUM = 5;
		String[] strApprovalTracing = new String[TRACINGNUM];
		String[] strApprovalDate = new String[TRACINGNUM];
		String[] strApprovalUserName = new String[TRACINGNUM];
		for (int i=0; i<TRACINGNUM; i++)
		{
			strApprovalTracing[i] = "";
			strApprovalDate[i] = DataFormat.getDateString(Env.getSystemDate());
			strApprovalUserName[i] = "";
		}
		
        
		//贴现标示
		long lDiscountID = -1;		
		long lClientID = -1;
		
		//模块类型
		long lModuleID = Constant.ModuleType.EBANK;
		//业务类型
		long lLoanTypeID = OBConstant.SubModuleType.LOAN;
		//操作类型
		long lActionID = OBConstant.LoanInstrType.DISCOUNT;
		
		Collection temp = null;
		
		//贴现信息
		DiscountInfo  dli = null;
        dli = (DiscountInfo)request.getAttribute("resultInfo");
		
		long lDiscountPO = 0;//贴现张数
		String strDiscountType = "银行";
		lDiscountPO = dli.getBankAccepPO();
		if(lDiscountPO <= 0)
		{
			lDiscountPO = dli.getBizAcceptPO();
			strDiscountType ="商业";
		}
		String strDocumentType = "";
		strDocumentType = dli.getDocumentType();
		String[] strDocumentTypes = OBConstant.DocumentType.getAllCode();
		String[] strDocumentCheck = new String[10];
		
		if (strDocumentType == null )
		{
			strDocumentType = "z";
		}
		for(int i=0;i<strDocumentTypes.length;i++)
		{
			if (strDocumentType.indexOf(strDocumentTypes[i]) >= 0)
			{
				strDocumentCheck[i]=" checked ";
			}
			else
			{
				strDocumentCheck[i]="";
			}
		}
		//客户信息
		OBSystemHome  obSystemHome = null;
        OBSystem      obSystem = null;
        obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
        obSystem = obSystemHome.create();
        ClientInfo ci = null;
		lClientID = sessionMng.m_lClientID;
		ci = obSystem.findClientByID(lClientID);
		
		ApprovalBiz appBiz = new ApprovalBiz();
		ApprovalTracingInfo tracinginfo = new ApprovalTracingInfo();
      
	    temp = appBiz.findApprovalTracing(lModuleID,lLoanTypeID,lActionID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,lDiscountID,Constant.PageControl.CODE_ASCORDESC_DESC);
		

			if( (temp != null) && (temp.size() > 0) )
            {
				nTmp = TRACINGNUM-1;
				Iterator it = temp.iterator();
                while (it.hasNext() )
				{
					tracinginfo = (ApprovalTracingInfo) it.next();
					strApprovalTracing[nTmp] = DataFormat.formatString(tracinginfo.getOpinion());
					if (tracinginfo.getApprovalDate() != null)
					{
						strApprovalDate[nTmp] = DataFormat.getDateString(tracinginfo.getApprovalDate());
					} 
					else 
					{
						strApprovalDate[nTmp] = DataFormat.getDateString(Env.getSystemDate());
					}
					strApprovalUserName[nTmp] = DataFormat.formatString(tracinginfo.getUserName());
					Log.print(nTmp);
					Log.print(strApprovalTracing[nTmp]);
					Log.print(strApprovalDate[nTmp]);
					nTmp--;
					if (nTmp < 0)
					{
						break;
					}
				}
			}
           //显示文件头
    //    OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
%>		

<html>

<head>
<meta http-equiv=Content-Type content="text/html; charset=gbk">
<title><%=strDiscountType%>承兑汇票贴现申请书</title>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<style type="text/css">
<!--
.table1 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
-->
</style>

<!--
<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://www.meadroid.com/scriptx/ScriptX.cab#Version=5,60,0,360"></object>
<script defer>
function window.onload()
{
	factory.printing.header = "";
	factory.printing.footer = "";
	factory.printing.leftMargin = 0;
	factory.printing.topMargin = 0;
	factory.printing.rightMargin = 0;
	factory.printing.bottomMargin = 0;
	factory.printing.portrait = true;	//竖打
	factory.printing.paperSize = "A4";
}

function document.onkeydown(DnEvents)
{
	k =  window.event.keyCode;
	if(k==13)
	{
		if (confirm("是否打印？"))
		{
			//factory.printing.printer="";可以写打印机的名称
			factory.printing.Print(true);
		}
	}
	if(k==32)
	{
		if (confirm("是否预览？"))
		{
			//factory.printing.printer="";可以写打印机的名称
			factory.printing.Preview();
		}
	}
}	
</script>
-->
<%
     eBankPrint.showPrintReport(out,sessionMng,"A4",1,true);
%>
</head>
<body bgcolor="#FFFFFF">

<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td height="40" align="center"><font size="5"><b><%=Env.getClientName()%></b></font></td>
</tr>
<tr>
	<td height="40" align="center"><font size="5"><b><%=strDiscountType%>承兑汇票贴现申请书</b></font></td>
</tr>
</table>
<table width="100%" border="0" cellpadding="4" cellspacing="0" class="table1">
	<tr>
		<td height="30" class="td-right">申请单位名称</td>
		<td colspan="6">&nbsp;<%=DataFormat.formatString(ci.getName())%></td>
    </tr>
	<tr>
		<td height="30" class="td-topright">法定地址</td>
		<td colspan="6" class="td-top">&nbsp;<%=DataFormat.formatString(ci.getAddress())%></td>
	</tr>
	<tr>
		<td height="30" class="td-topright">邮政编码</td>
		<td colspan="3" class="td-topright">&nbsp;<%=DataFormat.formatString(ci.getZipCode())%></td>
		<td colspan="2" class="td-topright" width="15%">传真号码</td>
		<td class="td-top">&nbsp;<%=DataFormat.formatString(ci.getFax())%></td>
	</tr>
	<tr>
		<td height="30" class="td-topright">法定代表人</td>
		<td colspan="3" class="td-topright">&nbsp;<%=DataFormat.formatString(ci.getLegalPerson())%></td>
		<td colspan="2" class="td-topright">联系电话</td>
		<td class="td-top">&nbsp;<%=DataFormat.formatString(ci.getPhone())%></td>
	</tr>
	<tr>
		<td height="30" class="td-topright">财务主管</td>
		<td colspan="3" class="td-topright">&nbsp;<%=DataFormat.formatString(ci.getContacter())%></td>
		<td colspan="2" class="td-topright">联系电话</td>
		<td class="td-top">&nbsp;<%=DataFormat.formatString(ci.getPhone())%></td>
	</tr>
	<tr>
		<td height="30" class="td-topright">经办人、职务</td>
		<td colspan="3" class="td-topright">&nbsp;<%=DataFormat.formatString(dli.getInputUserName())%></td>
		<td colspan="2" class="td-topright">联系电话</td>
		<td class="td-top">&nbsp;<%=DataFormat.formatString(ci.getPhone())%></td>
	</tr>
	<tr>
		<td height="30" class="td-topright">营业执照号</td>
		<td colspan="3" class="td-topright">&nbsp;<%=DataFormat.formatString(ci.getLicenceCode())%></td>
		<td colspan="2" class="td-topright">贷款证号</td>
		<td class="td-top">&nbsp;<%=DataFormat.formatString(ci.getLoanCardNo())%></td>
	</tr>
	<tr>
		<td height="30" class="td-topright">经营范围</td>
		<td colspan="6" class="td-top">&nbsp;<%=DataFormat.formatString(ci.getDealScope())%></td>
	</tr>
	<tr>
		<td width="15%" height="30" class="td-topright">开户银行</td>
		<td class="td-topright">&nbsp;<%=DataFormat.formatString(ci.getBank1())%></td>
		<td class="td-topright" width="10%">户名</td>
		<td colspan="2" class="td-topright">&nbsp;<%=DataFormat.formatString(ci.getName())%></td>
		<td class="td-topright" width="8%">账号</td>
		<td class="td-top">&nbsp;<%=DataFormat.formatString(ci.getBankAccount1())%></td>
	</tr>
	<tr>
		<td height="35" colspan="7" class="td-top">随表报送的书面材料（□ 内打勾）</td>
	</tr>
	<tr>
		<td colspan="7" class="td-top"><table width="100%" height="132" border="0">
			<tr valign="top">
				<td width="50%">
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[0]%> > 营业执照(复印件)</td>
			  <td width="50%">
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[5]%> > 商品购销合同(复印件)</td>
			</tr>
			<tr valign="top">
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[1]%> > 资产负债表</td>
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[6]%> > 增值税发票(复印件)</td>
			</tr>
			<tr valign="top">
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[2]%> > 损益表</td>
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[7]%> > 银行承兑汇票</td>
			</tr>
			<tr valign="top">
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[3]%> > 现金流量表</td>
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[8]%> > 公司章程</td>
			</tr>
			<tr valign="top">
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[4]%> > 法定代表人身份证明</td>
				<td>
				<input class=box disabled type="Checkbox" name="DocumentType"  <%=strDocumentCheck[9]%> > 董事会决议</td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td colspan="7" class="td-top"><table width="100%" height="217" border="0">
			<tr>
				<td height="40" colspan="3" align="center"><font size="3"><strong>申请单位的声明与保证</strong></font></td>
			</tr>
			<tr>
				<td colspan="3">　　本单位在此所做的一切陈述和提供的一切书面材料都是真实无误的。否则，愿意承担一切由此带来的法律责任。<br><br></td>
			</tr>
			<tr>
				<td width="52%">&nbsp;</td>
			    <td width="48%" colspan="2"><p>申请单位（公章）：<%=DataFormat.formatString(ci.getName())%></p><br>
		  		<p>法定代表人（签章）：<br><br><br></p>
				</td>
			</tr>
			<tr>
				<td width="52%">&nbsp;</td>
				<td width="48%" colspan="2">日期：<%=DataFormat.getDateString(dli.getInputDate()).substring(0,4)%>年 <%=DataFormat.getDateString(dli.getInputDate()).substring(5,7)%>月 <%=DataFormat.getDateString(dli.getInputDate()).substring(8,10)%>日</td>
			</tr>
		</table></td>
	</tr>
</table>

<br clear=all style='page-break-before:always'>

<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
	<td height="30" align="center"><font size="5"><b><%=Env.getClientName()%></b></font></td>
</tr>
<tr>
	<td height="30" align="center"><font size="5"><b><%=strDiscountType%>承兑汇票贴现审批表</b></font></td>
</tr>
</table>
<table width="100%" border="0" cellpadding="4" cellspacing="0" class="table1">
	<tr>
		<td><table width="100%" border="0" cellpadding="4">
			<tr>
			  <td width="8%" height="25">内容</td>
			  <td colspan="2">贴现申请单位：<%=DataFormat.formatString(ci.getName())%></td>
		  </tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td width="40%">票据贴现金额：<%=DataFormat.formatListAmount(dli.getExamineAmount())%></td>
				<td width="52%">贴现利率：<%=dli.getDiscountRate() <=0?"0":DataFormat.formatRate(DataFormat.formatRate(dli.getDiscountRate()))+"%"%></td>
			</tr>
			<tr>
				<td height="25">&nbsp;</td>
				<td>贴现票据张数：<%=dli.getApplyDiscountPO()%>&nbsp;张</td>
				<td>贴现款用途：<%=dli.getDiscountPurpose()%></td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="100" valign="top" class="td-top"><table width="100%" height="100%" border="0" cellpadding="4">
			<tr valign="top">
				<td colspan="2"><p>调查岗意见：</p>
				<p>&nbsp;<%=strApprovalTracing[0]%></p></td>
		  </tr>
			<tr>
				<td width="67%">&nbsp;</td>
			  <td width="33%" height="25">调查岗签名：<%=strApprovalUserName[0]%><br><%=strApprovalDate[0].substring(0,4)%>年 <%=strApprovalDate[0].substring(5,7)%>月 <%=strApprovalDate[0].substring(8,10)%>日</td>
			</tr>
		</table></td>
	</tr>
	<tr>
		<td height="170" class="td-top"><table width="100%" height="100%" border="0" cellpadding="4">
    	<tr valign="top">
    		<td colspan="2"><p>审查岗意见：</p>
      			<p>&nbsp;<%=strApprovalTracing[1]%></p>
 			</td>
 		</tr>
    	<tr>
    		<td width="67%">&nbsp;</td>
    		<td width="33%" height="25">审查岗签名：<%=strApprovalUserName[1]%><br><%=strApprovalDate[1].substring(0,4)%>年 <%=strApprovalDate[1].substring(5,7)%>月 <%=strApprovalDate[1].substring(8,10)%>日</td>
 		</tr>
    	</table></td>
	</tr>
	<tr>
		<td height="170" class="td-top"><table width="100%" height="100%" border="0" cellpadding="4">
    	<tr valign="top">
    		<td colspan="2"><p>部门经理意见：</p>
      			<p>&nbsp;<%=strApprovalTracing[2]%></p>
 			</td>
 		</tr>
    	<tr>
    		<td width="67%">&nbsp;</td>
    		<td width="33%" height="25">部门经理签名：<%=strApprovalUserName[2]%><br><%=strApprovalDate[2].substring(0,4)%>年 <%=strApprovalDate[2].substring(5,7)%>月 <%=strApprovalDate[2].substring(8,10)%>日</td>
 		</tr>
    	</table></td>
	</tr>
	<tr>
		<td height="170" class="td-top"><table width="100%" height="100%" border="0" cellpadding="4">
    	<tr valign="top">
    		<td colspan="2"><p>计划部经理意见：</p>
      			<p>&nbsp;<%=strApprovalTracing[3]%></p>
 			</td>
 		</tr>
    	<tr>
    		<td width="67%">&nbsp;</td>
    		<td width="33%" height="25">计划部经理签名：<%=strApprovalUserName[3]%><br><%=strApprovalDate[3].substring(0,4)%>年 <%=strApprovalDate[3].substring(5,7)%>月 <%=strApprovalDate[3].substring(8,10)%>日</td>
 		</tr>
    	</table></td>
	</tr>
	<tr>
		<td height="170" class="td-top"><table width="100%" height="100%" border="0" cellpadding="4">
    	<tr valign="top">
    		<td colspan="2"><p>主管总/副总经理意见：</p>
      			<p>&nbsp;<%=strApprovalTracing[4]%></p>
 			</td>
 		</tr>
    	<tr>
    		<td width="67%">&nbsp;</td>
    		<td width="33%" height="25">主管总/副总经理签名：<%=strApprovalUserName[4]%><br><%=strApprovalDate[4].substring(0,4)%>年 <%=strApprovalDate[4].substring(5,7)%>月 <%=strApprovalDate[4].substring(8,10)%>日</td>
 		</tr>
    	</table></td>
	</tr>
</table>

</body>
</html>
<%	
   //显示文件尾
		//OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>


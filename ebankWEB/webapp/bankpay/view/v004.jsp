<!--

author: niweinan
2010.10.16
功能:网银－资金拨划 银行汇款 明细页面
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
				 com.iss.itreasury.ebank.obfinanceinstr.dao.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[银行汇款]";
%>

<%
System.out.println("**************************Enter v004.jsp");
	/* 实现菜单控制 */
	
	
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
	
	String lTransType = "";
	lTransType = (String) request.getParameter("lTransType");
	if (lTransType == null)
		lTransType = (String) request.getParameter("txtTransType");
		
	long lCheckType = -1;//复核于复核匹配别
	String strCheckType = request.getParameter("checktype");
	if ((strCheckType != null) && (strCheckType.length() > 0)) {
		lCheckType = Long.parseLong(strCheckType);
	}
	String sTemp = null;
	 long lID = 0;           //指令序号
    sTemp = (String) request.getParameter("txtID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lID = Long.parseLong(sTemp);
    }

%>

<%
	/* 实例化信息类 */
	

	/* 用户登录检测与权限校验及文件头显示 */
	try 
	{
        
     OBHtml.validateRequest(out,request,response);

		/* 从请求中获取信息 */
		OBBankPayInfo info = new OBBankPayInfo();
		info=(OBBankPayInfo)request.getAttribute("obbankpayinfo");
		
		
      
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		String strTransNo = info.getId() + "";
		
		String payerAccountNo = com.iss.itreasury.ebank.util.NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid());  //付款方账号
		OBFinanceInstrBiz obFinanceInstrBiz = new OBFinanceInstrBiz();
		boolean hasAuthority = false;
	

	//SEFC新增
%>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">银行汇款</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>

  	    </td>
  </tr>
  
</table>
<br/>
	
		
      

		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 付款方资料</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方名称：</td>
          <td width="430" height="25" class="MsoNormal"> <%=info.getName()%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
		
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方账号：</td>
          <td width="430" height="25" class="MsoNormal"><%=payerAccountNo%></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方银行名称：</td>
          <td width="430" height="25" class="MsoNormal"><%= com.iss.itreasury.ebank.util.NameRef.getBankNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 收款方资料</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            汇款方式：
          </td>
          <td width="430" height="25" class="MsoNormal">银行汇款</td>
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
          <td width="430" height="25" class="MsoNormal"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
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
		  <%=info.getSpayeeacctname()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            汇入行CNAPS号：
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankCNAPSNo() == null?"":info.getBankCNAPSNo()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            银行联行号：
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getBankconnectnumber() == null?"":info.getBankconnectnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
                 <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">
            机构号：
          </td>
          <td width="430" height="25" class="MsoNormal">
		  <%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%></td>
          <td width="1" height="25" class="MsoNormal"></td>
        </tr>
		</table>
      <br>
		<table  border="0" cellspacing="0" cellpadding="0" class="">
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2"> 划款资料</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
		<table width=80% border="0" cellspacing="0" cellpadding="0" class=normal id="table1" align="">
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal" width="110">金额：</td>
          <td width="20" height="25" class="MsoNormal">
            <div align="right"><%= sessionMng.m_strCurrencySymbol %></div>
          </td>

          <td width="430" height="25" class="MsoNormal" id="ss"></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <script>
          		var a = <%=info.getMamount()%>+"";
          		document.getElementById("ss").innerText = formatAmount1(a);
          </script>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">提交日期：</td>
          <td width="430" height="25" class="MsoNormal"><%=info.getDtconfirm().toString().substring(0,10)%></td>
          <td width="5" height="25" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="MsoNormal"><%= (info.getSnote()==null)?"":info.getSnote() %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
      

      </table>
	  <br>
	<% 
	  if ((info.getNstatus() == OBConstant.SettInstrStatus.SAVE)
			|| // 已保存
			(info.getNstatus() == OBConstant.SettInstrStatus.CHECK)
			|| // 已复核
			(info.getNstatus() == OBConstant.SettInstrStatus.SIGN)
			|| // 已签认
			(info.getNstatus() == OBConstant.SettInstrStatus.DEAL)
			|| //处理中
			(info.getNstatus() == OBConstant.SettInstrStatus.FINISH)
			|| //已完成
			(info.getNstatus()==OBConstant.SettInstrStatus.APPROVALING)
			||//审批中
			(info.getNstatus()==OBConstant.SettInstrStatus.APPROVALED)
			||//已审批
			(info.getNstatus() == OBConstant.SettInstrStatus.REFUSE))
			//已拒绝 
		{
	%>
		<table  border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td width="3"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px"> 交易申请处理详情</td>
				<td width="650"><a class=lab_title3></td>
			</tr>
		</table>
      <table width=80% border="0" class="normal">
        <tr class="tableHeader">
          <td height="19" width="10%"  align="center" class="ItemTitle">
            <div align="center"><font size="2" >序列号</font></div>
          </td>
          
          <td height="19" class="ItemTitle" width="30%" align="center">
           用户
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center">
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center">
            <div align="center">时间和日期</div>
          </td>
        </tr>
       
       
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">1</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNconfirmuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">录入</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        <%
        }
        if ((info.getNstatus() == OBConstant.SettInstrStatus.CHECK)
				|| // 已复核
				(info.getNstatus() == OBConstant.SettInstrStatus.SIGN)
				|| // 已签认
				(info.getNstatus() == OBConstant.SettInstrStatus.DEAL)
				|| //处理中
				(info.getNstatus()== OBConstant.SettInstrStatus.FINISH) 
				|| //已完成
				(info.getNstatus() == OBConstant.SettInstrStatus.REFUSE)) 
				{
         %>
         <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">2</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNcheckuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">复核</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtcheck().toString().substring(0,10) %></div>
          </td>
        </tr>
		<%
		}
		if ((info.getNstatus() == OBConstant.SettInstrStatus.SIGN)
				|| // 已签认
				(info.getNstatus() == OBConstant.SettInstrStatus.DEAL)
				|| //处理中
				(info.getNstatus() == OBConstant.SettInstrStatus.FINISH) 
				|| //已完成
				(info.getNstatus() == OBConstant.SettInstrStatus.REFUSE)) 
				{
			
		%>
		 <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">3</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=com.iss.itreasury.ebank.util.NameRef.getUserNameByID(info.getNsignuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">签认</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtsign().toString().substring(0,10) %></div>
          </td>
        </tr>
        <%} %>

 </table>
 
 <br>
 <%String strContext = request.getContextPath(); %>
 
	  <table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="1"><a class=lab_title1></td>
				<td class="lab_title2" style="width:150px">历史审批意见</td>
				<td width="800"><a class=lab_title3></td>
			</tr>
		</table>
	<table border="0" width="80%" cellspacing="0" cellpadding="0" align="" class=normal>
		<!-- 历史审批意见 -->
		<TR>
			<TD colspan="3">
				
			<fs:HistoryOpinionFrame
					  strTransNo='<%=strTransNo%>'
					  moduleID='<%=Constant.ModuleType.SETTLEMENT%>'
					  transTypeID='<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>'
					  clientID='<%=sessionMng.m_lClientID%>'
					  currencyID='<%=sessionMng.m_lCurrencyID%>'
					  officeID='<%=sessionMng.m_lOfficeID%>'
					  islowerunit = '<%=OBConstant.IsLowerun.ISNO%>'/>
			</TD>
		</TR>
		<!-- 历史审批意见 -->
		</table>
		<br />
      <form name="frmzjhb" method="post">
	  <table border="0" width=80% cellspacing="0" cellpadding="0">
        <tr>
		
      <td width="80%" align="right"> 
        

		<%
			
		if(info.getNstatus() == OBConstant.SettInstrStatus.SAVE){
			if(info.getNconfirmuserid()==sessionMng.m_lUserID&&obFinanceInstrBiz.hasAuthority(payerAccountNo,sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
				{
		
		%>

		<input class=button1 name=add type=button value=" 修 改 " onClick="Javascript:updateme();" >
		<input class=button1 name=add type=button value=" 删 除 " onClick="Javascript:deleteme();" >

		<%
				}
		}
		if(info.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
		{
			if(info.getNconfirmuserid()==sessionMng.m_lUserID&&obFinanceInstrBiz.hasAuthority(payerAccountNo,sessionMng.m_lUserID,sessionMng.m_lCurrencyID))
			{
		%>
		<input class=button1 name=cancel type=button value=" 重新提交 " onClick="Javascript:cancelme();" >
		<%
			}
		}
		 %>
		
       
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class=button1 name=add type=button value=" 关 闭 " onClick="window.close();" >







        
      </td>
        </tr>
      </table>

	  <input type="hidden" name="mamount" value="<%= info.getMamount() %>">
	  <input type="hidden" name="dtexecute" value="<%= info.getDtexecute() %>">
	  <input type="hidden" name="nconfirmuserid" value="<%= info.getNconfirmuserid() %>">
	  <input type="hidden" name="dtconfirm" value="<%= info.getDtconfirm() %>">
	  <input type="hidden" name="npayeracctid" value="<%= info.getNpayeracctid() %>">
	  <input type="hidden" name="npayeeacctid" value="<%= info.getNpayeeacctid() %>">
	  <input type="hidden" name="id" value="<%= info.getId() %>">
	  <INPUT type="hidden" name="ncurrencyid" value="<%=info.getNcurrencyid()%>">
	  <INPUT type="hidden" name="nclientid" value="<%=info.getNclientid()%>"> 
	  <INPUT type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
	  <INPUT type="hidden" name="spayeeprov" value="<%=info.getSpayeeprov()%>">
	  <INPUT type="hidden" name="spayeecity" value="<%=info.getSpayeecity()%>">
	  <INPUT type="hidden" name="spayeebankname" value="<%=info.getSpayeebankname()%>">
	  <INPUT type="hidden" name="spayeeacctname" value="<%=info.getSpayeeacctname()%>">
	  <INPUT type="hidden" name="spayeeacctno" value="<%=info.getSpayeeacctno()%>">
	  <INPUT type="hidden" name=snote value="<%=info.getSnote()%>">
		
	  </form>
<!--presentation end-->

<script language="javascript">
//打印委托付款凭证
function PrintConsignVoucher()
{
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
		frmzjhb.action="../view/v001.jsp";
		frmzjhb.submit();
	}

	
	/* 修改处理函数 */
	function updateme()
	{
		frmzjhb.action="<%=strContext%>/bankpay/view/v001.jsp?lID=<%=lID%>&src=<%=lSourceType%>";
		frmzjhb.submit();
	}
	//撤销处理函数
	function cancelme()
	{
		frmzjhb.action="<%=strContext%>/bankpay/view/v001.jsp?lID=<%=lID%>&nEbankStatus=<%=info.getNEbankStatus()%>";
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
		frmzjhb.action="../control/c003.jsp?doact=querydelete";
		showSending();
		frmzjhb.submit();
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
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
	
	
%>
<%@ include file="/common/SignValidate.inc" %>
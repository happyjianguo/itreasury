<%--
/*
 * 程序名称：
 * 功能说明：审核页面
 * 作　　者：baihuili
 * 日期：2006年09月15日
 */
--%>
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*"


%>
<%@ taglib uri="/WEB-INF/issworkflow-operate.tld" prefix="isswf"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<!--Header end-->

<%!
	/* 标题固定变量 */
	String strTitle = "[银行汇款]";
%>

<%
System.out.println("**************************Enter v002.jsp");
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
	//从"我的工作"传递的控制变量
	String strTempAction = "";
	if (request.getParameter("strTempAction") != null)
	{
		strTempAction = (String)request.getParameter("strTempAction");
		request.setAttribute("strTempAction", strTempAction);
	}

%>

<%
	/* 实例化信息类 */
	

	/* 用户登录检测与权限校验及文件头显示 */
	try 
	{
        
     OBHtml.validateRequest(out,request,response);

		/* 从请求中获取信息 */
		//OBBankPayInfo  info=(OBBankPayInfo)request.getAttribute("info");
		
		OBFinanceInstrEJB ejb = new OBFinanceInstrEJB();
		
		OBBankPayInfo info = new OBBankPayInfo();
		String lId = request.getParameter("txtID");
		info = ejb.findByID(Long.parseLong(lId));
		System.out.println("**************************"+info);
		
      
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
	
	

	//SEFC新增
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<link rel="stylesheet" href="/webob/css/style.css" type="text/css">
<safety:resources />

<form action="" method="post" name="form_1">
<input type="hidden" name="lID" value="<%= info.getId() %>">
     <table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="22">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title><span class="txt_til2">银行汇款确认</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
        <tr class="MsoNormal">
          <td colspan="3" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td height="25" class="MsoNormal">
            
      <p ><br>
        银行汇款 将于复核后才提交到<%=com.iss.itreasury.settlement.util.NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>！<br>
              <!--br>
              已通知复核人复核！
			  <br-->
			  <!--update by hyzeng 2003-4-10-->
			  <br>
              该笔交易有待复核人复核！
			  <br>
              <br>
              <b>指令序号为：<%= info.getId() %></b><br>
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

	   <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> 付款方资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
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
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getBankPayAcctNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
         <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal">付款方银行名称：</td>
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getBankNameByAcctID(info.getNpayeracctid()) %></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td colspan="4" height="1" class="MsoNormal"></td>
        </tr>
      </table>
      <br>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> 收款方资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2"> 划款资料</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">        
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
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
        <tr class="MsoNormal">
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="130" height="25" class="MsoNormal" colspan="2">提交日期：</td>
          <td width="430" height="25" class="MsoNormal"><%=info.getDtexecute().toString().substring(0,10)%></td>
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

      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
			<td width="1"><a class=lab_title1></td>
			<td class="lab_title2" style="width:150px"> 交易申请处理详情</td>
			<td width="800"><a class=lab_title3></td>
		</tr>
		</table>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="normal">
		<thead>     	        
        <tr>
          <td height="19" width="10%"  align="center" >
            <div align="center"><font size="2" >序列号</font></div>
          </td>
          
          <td height="19"  width="30%" align="center">
           用户
          </td>
          
          <td  height="19"   width="30%" align="center">
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
          
          <td   width="30%" height="25">
            <div align="center"><%=NameRef.getUserNameByID(info.getNconfirmuserid())%></div>
          </td>          
          <td   width="30%" height="25">
            <div align="center">录入</div>
          </td>
          
          <td  width="30%" height="25">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>

 </table>
 <br/>

<table width="80%" border="0" align="" cellspacing="0" cellpadding="0">
  <tr>
	<td width="1"><a class=lab_title1></td>
	<td class="lab_title2" style="width:150px">历史审批意见</td>
	<td width="800"><a class=lab_title3></td>
</tr>
</table>
 	  <table border="0" width="100%" cellspacing="0" cellpadding="0" align="" class=normal>
	  <TR>
		  <TD colspan="3">
			 <iframe src="/NASApp/iTreasury-ebank/HistoryOpinionFrame.jsp?transNo=<%=info.getId()+ ""%>&&transType=<%=OBConstant.SettInstrType.ONLINEBANK_BANKPAY%>" width="100%" height="100" scrolling="Auto" frameborder="0" name="currentSignID"></iframe>
		  </TD>
	  </TR>
	  <tr>
		     <%
		     if ( !(strTempAction.equals("finished") || strTempAction.equals("cancelApproval")) )
		     {
				//对已审批的任务，不再显示审批意见录入框
				if(info.getNstatus() != OBConstant.OBBudgetStatus.APPROVE){
						String strMagnifierNameRemark = "审批意见";
						String strFormNameRemark = "form_1";
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
   	<table width="100%"><tr><td align="right">
	<%
   	//当从取消审批列表进入时,显示取消审批按钮
   	
   		int operation = -1;
	String strTemp = request.getParameter("operation");	
	
	
	if(strTemp!=null && !strTemp.equals("")){
		operation = Integer.parseInt(strTemp);
	}
   	
   	if(operation==OBConstant.SettInstrStatus.CANCELAPPRVOAL)
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
	
	<input type="hidden" name="strSuccessPageURL" value="">		<!--操作成功转向页面-->
<input type="hidden" name="strFailPageURL" value="">		<!--操作失败转向页面-->
<input type="hidden" name="strAction" value="">	
			</td>
		</tr>
	</table>
			</td>
		</tr>
	</table>

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
	{	if (!confirm("是否返回？"))
			{
				return false;
			}
		frmzjhb.action="../view/v101.jsp";
		frmzjhb.submit();
	}

    function doReturn()
	{
	    showSending();
	    window.location.href="../../approval/view/v033.jsp";
	}

	/* 确认处理函数 */
	function checkme()
	{
		//showMenu();
		if (!confirm("是否复核？"))
			{
				return false;
			}
		frmzjhb.act.value="check";
		frmzjhb.action="../control/c101.jsp";
		showSending();
		frmzjhb.submit();
	}
	/* 修改处理函数 */
	function updateme()
	{
		//showMenu();
		frmzjhb.action="../control/c002.jsp";
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
		frmzjhb.action="../control/c003.jsp";
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

<script language="Javascript">
	String.prototype.Trim = function()
    {
	    return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	//审批流审批
	function doApproval()
	{
		var frm=form_1;
		if(frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.value.Trim()=="")
		{
			alert("请输入审批意见");
			frm.<%=com.iss.inut.workflow.constants.Constants.WF_ADVISE%>.focus();
			return;
		}
	    frm.strAction.value="<%=OBConstant.SettInstrStatus.DOAPPRVOAL%>";
		if (confirm("是否审批?")) 
		{					
			frm.strSuccessPageURL.value='/approval/view/v033.jsp';
			frm.strFailPageURL.value='../view/c004_v.jsp';
			frm.action= "../control/c901.jsp";
			showSending();			
			frm.submit();
		}
	}	

//取消审批
	function cancelApproval()
	{
		var frm=form_1;
	 	frm.strAction.value="<%=OBConstant.SettInstrStatus.CANCELAPPRVOAL%>";
		if (confirm("是否取消审批?")) 
		{						
			frm.strSuccessPageURL.value='/approval/view/v036.jsp';
			frm.strFailPageURL.value='../view/c004_v.jsp';
			frm.action= "../control/c901.jsp";
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
		OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
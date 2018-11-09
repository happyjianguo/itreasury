<%--
/*
 * 程序名称：
 * 功能说明：业务审核 取消审核
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
	long lShowMenu = OBConstant.ShowMenu.NO;
	
    
	long lSourceType = 0;//头信息显示
    String strSource = request.getParameter("src");
    if ((strSource != null) && (strSource.length()>0))
	{
	    lSourceType = Long.parseLong(strSource);
	}
	

%>

<%
	/* 实例化信息类 */
	

	/* 用户登录检测与权限校验及文件头显示 */
	try 
	{
        
     OBHtml.validateRequest(out,request,response);

		/* 从请求中获取信息 */
		String id=(String)request.getAttribute("id");
		/*
		OBFinanceInstrHome financeInstrHome = null;
		OBFinanceInstr financeInstr = null;
		financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
		financeInstr = financeInstrHome.create();  
		*/
		OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		OBBankPayInfo  info=financeInstr.findByID(Long.parseLong(id));;
		System.out.println("**************************"+info);
		
      	String strdisable="";
      	strdisable=(String)request.getParameter("strdisable");
        /* 显示文件头 */
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
		
	
	

	//SEFC新增
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

	  <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr  class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="560"height="25" class="FormTitle" colspan="4"><font size="3" color="#FFFFFF" >付款方资料</font></td>
          <!--td width="5" valign="top" align="right" height="25" class="FormTitle">
            <div align="right"></div>
          </td-->
        </tr>
	
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
          <td width="430" height="25" class="MsoNormal"><%= NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
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
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr bgcolor="#456795" class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="560"height="25" class="FormTitle" colspan="4"><font size="3" color="#FFFFFF" >收款方资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
      </table>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
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
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr  class="tableHeader">
          <!--td bgcolor="#456795" width="5" valign="top" align="left" height="25"><img src="\webob\graphics\blue_top_left.gif" width="3" height="3"></td-->
          <td width="560"height="25" class="FormTitle" colspan="5"><font size="3" color="#FFFFFF" class="whitetext">划款资料</font></td>
          <!--td width="5" valign="top" align="right" height="25">
            <div align="right"></div>
          </td-->
        </tr>
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

      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="tableHeader">
          <td colspan="4" height="1"></td>
        </tr>
        <tr class="tableHeader">
          
    <!--td bgcolor="#0C3869" width="4" valign="top" align="left" height="22"><img src="../images/blue_top_left.gif" width="3" height="3"></td-->
          <td height="22" colspan="4" class="FormTitle" ><font size="3" ><b>交易申请处理详情</b></font></td>
    	 </tr>        
      </table>
      <table width="730" border="0" class="ItemList">
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
            <div align="center"><%=NameRef.getUserNameByID(info.getNconfirmuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">录入</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">2</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=NameRef.getUserNameByID(info.getNcheckuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">复核</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtcheck().toString().substring(0,10) %></div>
          </td>
        </tr>
        <%if(info.getNstatus()==OBConstant.OBBankPayStatus.SIGN)
        {
        %>
			 <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25">
            <div align="center">3</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%=NameRef.getUserNameByID(info.getNsignuserid())%></div>
          </td>          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center">审核</div>
          </td>
          
          <td bgcolor="#C8D7EC" class="ItemBody" width="30%" height="25">
            <div align="center"><%= info.getDtsign().toString().substring(0,10) %></div>
          </td>
        </tr>
        
       <%
       	}
       	%>

 </table>
 <br>


	  <br>
      <form name="frmzjhb" method="post">
	  <table border="0" width="730" cellspacing="0" cellpadding="0">
        <tr>
		
      <td width="730" align="right"> 
   
		<INPUT type="hidden" name="act">
        <!--
         <img src="\webob\graphics\button_queren.gif" width="46" height="18" border="0" onclick="Javascript:confirmme();">
-->
        <!--打印委托付款凭证-->
        <!--img src="\webob\graphics\button_xiugai.gif" width="46" height="18" border="0" onclick="Javascript:updateme();"> 
        <img src="\webob\graphics\button_shanchu.gif" width="46" height="18" border="0" onclick="Javascript:deleteme();"--> 
        <%if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK)
        {
        %>
		<input class=button name=add type=button value=" 审  核 " <%=strdisable%> onClick="Javascript:signme();"  >
       <%
       	}
       	else
       	{
       %>
       <input class=button name=add type=button value=" 取消审核 " <%=strdisable%> onClick="Javascript:doCancelSign();" >
       <%}%>
        <!--img src="\webob\graphics\button_fanhui.gif" width="46" height="18" border="0" onclick="javascript:returnme()"-->
		<input class=button name=add type=button value=" 关  闭 " onClick="javascript:window.close()"  >
        
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
	  <INPUT type="hidden" name="doact">	
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
		frmzjhb.action="../view/v101.jsp";
		frmzjhb.submit();
	}

	
	function signme()
	{
		//showMenu();
		if(!confirm("开始审核?"))
		{
			return false;
		}
		frmzjhb.doact.value="oneauditing";
		frmzjhb.action="../control/c103.jsp";
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
	 function doCancelSign()/**/
 {
       
	   var checkOrUncheck ;
	 
	   		checkOrUncheck = "取消审核？"
	   
	   if(confirm(checkOrUncheck))
	   {
	       frmzjhb.action = "../control/c103.jsp";
	       frmzjhb.doact.value="canceloneauditing";
	       showSending(); 
		   frmzjhb.submit();
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
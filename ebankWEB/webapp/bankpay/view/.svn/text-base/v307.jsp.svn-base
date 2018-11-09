
<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.util.ArrayList,
				 com.iss.itreasury.util.ConfigConstant,
				 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.obquery.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
				 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
                 com.iss.itreasury.settlement.util.SETTHTML,
                 java.rmi.*,
                 java.lang.*,
                 com.iss.itreasury.util.*,
                 java.sql.*,
                 java.util.*,
                 com.iss.itreasury.bankportal.integration.constant.InstructionStatus"
%>
<%@ page import="com.iss.itreasury.settlement.util.SETTHTML" %>
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
	long lShowMenu =OBConstant.ShowMenu.NO;
	
    
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
        if(!OBHtml.validateRequest(out, request,response)) return;

	
    // OBHtml.validateRequest(out,request,response);

		/* 从请求中获取信息 */
		String id=(String)request.getAttribute("id");
		OBFinanceInstrEJB financeInstr=new OBFinanceInstrEJB();
		OBBankPayInfo  info=financeInstr.findByID(Long.parseLong(id));;
		System.out.println("**************************"+info);
		
      	String isDisable="";
      	if(info!=null && info.getBankPortalStatus()!=-1 && info.getBankPortalStatus()!=0 && info.getBankPortalStatus()!=10)
      	{
      		isDisable="disabled";
      	}
        /* 显示文件头 */
		
	eBankPrint.showPrintReport(out,sessionMng,"A4",2,true);
	

	//SEFC新增
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>

<safety:resources />

<form>	 

	    <table width="730" cellspacing="0" cellpadding="0" class=top>
		<tr class="tableHeader" align="center">
          <td width="730"height="25" class="FormTitle" colspan="6"><font size="5">交易明细单</font></td>
        </tr>
		</table>
	    <br>
	   <table width="730" cellspacing="0" cellpadding="0" class=top>
		<tr class="tableHeader">
          <td width="730"height="25" class="FormTitle" colspan="4"><font size="3">付款方资料:</font></td>
        </tr>
		</table>
		<table width="730" border="0" bgcolor="#000000" cellspacing="2" cellpadding="1" >
        <tr>
          <td width="160" colspan="2" height="25" bgcolor="#FFFFFF" class="MsoNormal">付款方名称：</td>
          <td width="570" colspan="2" height="25" bgcolor="#FFFFFF" class="MsoNormal" > <%=info.getName()%></td>
        </tr>
        <tr>
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">付款方账号：</td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2"><%= NameRef.getBankAcctNameByAcctID(info.getNpayeracctid()) %></td>
        </tr>
         <tr  class="MsoNormal">
          <td width="160" colspan="2" height="25" bgcolor="#FFFFFF" class="MsoNormal">付款方银行名称：</td>
          <td width="570" colspan="2" height="25" bgcolor="#FFFFFF" class="MsoNormal" ><%= NameRef.getBankNameByAcctID(info.getNpayeracctid()) %></td>
        </tr>
  
      </table>
      
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="tableHeader">
          <td width="730"height="25" class="FormTitle" colspan="4"><font size="3" >收款方资料:</font></td>
        </tr>
      </table>
      <table width="730" border="0" cellspacing="2" cellpadding="1" class=top bgcolor="#000000">
        <tr class="MsoNormal">
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
            汇款方式：
          </td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">银行汇款</td>
        </tr>
        <tr class="MsoNormal">
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
            收款方账号：
          </td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2"><%=(info.getSpayeeacctno()==null)?"":info.getSpayeeacctno()%></td>
        </tr>
        <tr class="MsoNormal">
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
            收款方名称：
          </td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
		  <%=info.getSpayeeacctname()%></td>
        </tr>
		<tr class="MsoNormal">
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
            银行名称：
          </td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
		  <%=info.getSpayeebankname()%></td>
        </tr>
        <tr class="MsoNormal">
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
            银行联行号：
          </td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
		  <%=info.getBankconnectnumber()==null?"":info.getBankconnectnumber()%></td>
        </tr>
        <tr class="MsoNormal">
          <td width="160" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
           机构号：
          </td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">
		  <%=info.getDepartmentnumber() == null?"":info.getDepartmentnumber()%></td>
        </tr>
		</table>
      <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class = top>
        <tr  class="tableHeader">
          <td width="560" height="25" class="FormTitle" colspan="5"><font size="3" class="whitetext">划款资料:</font></td>
        </tr>
	  </table>
	   <table width="730" border="0" cellspacing="2" cellpadding="1" class=top bgcolor="#000000">
        <tr class="MsoNormal">
          <td height="25" class="MsoNormal" width="160" bgcolor="#FFFFFF" colspan="2">金额：</td>
          <td width="570" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2"><%= sessionMng.m_strCurrencySymbol %>  <%= DataFormat.formatEAmount(info.getMamount()) %></td>
        </tr>
        <tr class="MsoNormal">
          <td width="130" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">提交日期：</td>
          <td width="430" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2"><%=info.getDtexecute().toString().substring(0,10)%></td>
        </tr>
        <tr class="MsoNormal">
          <td width="130" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2">汇款用途：</td>
          <td width="430" height="25" class="MsoNormal" bgcolor="#FFFFFF" colspan="2"><%= (info.getSnote()==null)?"":info.getSnote() %></td>
        </tr>
      </table>
	  
	  <br>
      <table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr class="tableHeader">
          <td height="22" colspan="4" class="FormTitle" ><font size="3" >交易申请处理详情:</font></td>
    	 </tr>        
      </table>
      <table width="730" border="0" cellspacing="2" cellpadding="1" class=top bgcolor="#000000">
        <tr class="tableHeader">
          <td height="19" width="10%"  align="center" class="ItemTitle" bgcolor="#FFFFFF">
            <div align="center">序列号</div>
          </td>
          
          <td height="19" class="ItemTitle" width="30%" align="center" bgcolor="#FFFFFF">
           用户
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center" bgcolor="#FFFFFF">
            <div align="center">工作描述</div>
          </td>
          
          <td  height="19"  class="ItemTitle" width="30%" align="center" bgcolor="#FFFFFF">
            <div align="center">时间和日期</div>
          </td>
        </tr>
   <%
   	if(info.getNstatus()==OBConstant.OBBankPayStatus.DELETE)
   	 {	
   %>    
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">1</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNconfirmuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNconfirmuserid()));					
            					%>
            </div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">录入</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">2</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNdeleteuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNdeleteuserid()));					
            					%>
            </div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">删除</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
     <%
     	}
     	if(info.getNstatus()==OBConstant.OBBankPayStatus.SAVE)
   	 {	
   %>    
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">1</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNconfirmuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNconfirmuserid()));					
            					%>
            </div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">录入</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
     <%
     	}
     	if(info.getNstatus()==OBConstant.OBBankPayStatus.CHECK)
     	{
     %>   
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">1</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNconfirmuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNconfirmuserid()));					
            					%>
            	</div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">录入</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">2</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">
           						 <% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNcheckuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNcheckuserid()));					
            					%>
            					
            					</div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">复核</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtcheck().toString().substring(0,10) %></div>
          </td>
        </tr>
      <%
      	}
      	if(info.getNstatus()==OBConstant.OBBankPayStatus.SIGN)
     	{
     %>   
     	<tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">1</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">
						<% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNconfirmuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNconfirmuserid()));					
            			%>
			</div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">录入</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtconfirm().toString().substring(0,10) %></div>
          </td>
        </tr>
        
        <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">2</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><% if(info.getNmodule()==-1) out.print(NameRef.getUserNameByID(info.getNcheckuserid()));
            							else out.print(NameRef.getUserNameByIDForSett(info.getNcheckuserid()));					
            					%>
            </div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">复核</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtcheck().toString().substring(0,10) %></div>
          </td>
        </tr>
         <tr valign="middle">
          <td width="10%" align="left" class="ItemBody" height="25" bgcolor="#FFFFFF">
            <div align="center">2</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%=NameRef.getUserNameByIDForSett(info.getNsignuserid())	
            					%>
            </div>
          </td>          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center">签认</div>
          </td>
          
          <td class="ItemBody" width="30%" height="25" bgcolor="#FFFFFF">
            <div align="center"><%= info.getDtsign().toString().substring(0,10) %></div>
          </td>
        </tr>
        
	<%}%>
 </table>
 </form>
<%
	}
	catch(Exception e)
	{
	e.printStackTrace();
		//OBHtml.showExceptionMessage(out,sessionMng, (IException)e, strTitle,"",1);
		return;
	}
%>
<%@ include file="/common/SignValidate.inc" %>
 <%/**
			 * 页面名称 ：v003.jsp
			 * 页面功能 : 通知存款支取通知修改
			 * 作    者 ：YuanHuang
			 * 日    期 ：2006-10-31
			 * 特殊说明 ：通知存款支取通知显示
			 *			  
			 * 修改历史 ：
			 */
%>
<%@ page contentType = "text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.util.*,
				com.iss.itreasury.util.DataFormat,
				com.iss.itreasury.settlement.transfixeddeposit.dataentity.*,
				com.iss.itreasury.settlement.util.*,
				com.iss.itreasury.ebank.util.OBHtml,
				com.iss.itreasury.ebank.util.OBConstant
				"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
  <% 
  long lShowMenu = OBConstant.ShowMenu.YES;
  String strTitle = "通知存款支取通知修改";
    try 
	{
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
        /* 显示文件头 */
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, lShowMenu);
    System.out.println("------------通知存款支取通知修改/v003.jsp-----------------------------");
 

 
 		//定义变量	
			long id = -1;// id值
			String clientCode = ""; //客户编号
			String clientName = "" ;//客户名称
			String saccountno = "" ;// 账号
			String DepositNo="";//通知存款单据号
			String startdate = "";//起始日
			double mAmount = 0;//本金
			double balance = 0;//余额	
			double amount=0;//支取金额
			String notifyDate="";//通知日期
			
			NotifyDepositInformInfo info = (NotifyDepositInformInfo) request.getAttribute("info");
			if(info!=null)
			{
				String strTemp = null;
				double douTemp = 0.00;
				id= info.getID();
				strTemp = info.getClientCode();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					clientCode = strTemp;
				}
				strTemp = info.getClientName();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					clientName = strTemp;
				}
				strTemp = info.getSaccountno();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					saccountno = strTemp;
				}
				strTemp = info.getDepositNo();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					DepositNo = strTemp;
				}
				strTemp = info.getStartdate();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					startdate = strTemp;
				}
				douTemp = info.getmAmount();
				if( douTemp >0)
				{
					mAmount = douTemp;
				}
				douTemp = info.getBalance();
				if( douTemp >0)
				{
					balance = douTemp;
				}
				douTemp = info.getAmount();
				if( douTemp >0)
				{
					amount = douTemp;
				}
				strTemp = info.getNotifyDate();
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					notifyDate = strTemp;
				}
			}						
			%>
<form name="frmV003">
<input type="hidden" value="<%=id %>" name="id">

<table width="98%" cellpadding="0" cellspacing="0" class="title_top" >
	  <tr>
	    <td height="22" >
		    <table width="100%" cellspacing="0" cellpadding="0" class=title_Top1 >
				<TR>
			       <td class=title><span class="txt_til2" style="width:150px">通知存款支取通知修改</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
		</table>	
	    
	<br/>
	<table align="" width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
 		<tr><td></td></tr>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
       		<TD>通知存款单据号：</TD>
      		<TD align="left">
       			<INPUT class="box" type='text' name='DepositNo' disabled value="<%= DepositNo %>">
       		</TD>
			<input type="hidden" name= "DepositNo1" value="<%= DepositNo %>">  			
     		<TD>通知存款起始日：</TD>
       		<TD>
        		<INPUT class=box type="text" disabled name="strStartDate" value="<%=startdate%>">
			</TD>
		</TR>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
			<TD>通知存款本金：</TD>
			<TD align="left">
				<fs:amount 
		       		form="frmV003"
	       			name="dAmount"
	       			readonly="true"
	       			value="<%=mAmount %>"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>" />
			</TD>
			<TD>通知存款余额：</TD>
			<TD>
				<fs:amount 
		       		form="frmV003"
	       			name="dDepositBalance"
	       			readonly="true"
	       			value="<%=balance %>"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>" />
   			</TD>			
 		</TR>
        <tr class="MsoNormal">
		  	<td width="5" height="25"></td>
			<TD>支取金额：</TD>			
			<TD>
				<fs:amount 
		       		form="frmV003"
	       			name="dDrawAmount"
	       			value="<%=amount %>"
	       			nextFocus="notifyDate"
	       			currencyID="<%=sessionMng.m_lCurrencyID%>" />
			</TD>
			<!-- Added by zwsun 2007-6-19 增加通知日期 -->
			<!-- begin -->
			<td>
				通知日期：
			</td>
			<td>
				<fs:calendar 
	          	    name="notifyDate"
		          	value="" 
		          	properties=""
		          	size="20"/>
		        <script>
	          		$('#notifyDate').val('<%= notifyDate %>');
	          	</script>
			</td>
			<!-- end -->						
		</TR>
		<tr><td>&nbsp;</td></tr>
		<TR vAlign=center>
			<TD colSpan=6 height=2>
			<DIV align=right></DIV>
			<DIV align=right>						
			 <input class="button1" name="next" type="button" value=" 保 存 " onClick="doNext();"  onKeydown="if(event.keyCode==13) {if(confirm('是否保存？')) doNext();}"> 
			 <input class="button1" name="Submit32" type="button" value=" 返 回 " onClick="window.location.href='../control/c002.jsp';"></DIV>
			</TD>
			<td>&nbsp;&nbsp;</td>
		</TR>
		<tr><td>&nbsp;</td></tr>
	</TABLE>
	
	</td>
	</tr>
	</table>
</form>
<script>
setFormName("frmV003");
firstFocus(frmV003.dDrawAmount);

function doNext()
{
	if(!check()) return false;
	if(confirm('是否保存？')) 
	{
		frmV003.action = "../control/c005.jsp";
		showSending();
		frmV003.submit();
	}
}

function check()
{
	if(!validate()) return false;
	//支取金额检验
	if(Trim(frmV003.dDrawAmount.value)=="")
	{
		alert("支取金额金额不能为空。");
		document.frmV003.dDrawAmount.focus();
		return false;
	}	
	if(checkAmount(frmV003.dDrawAmount,1,"支取金额") == false)
	{
		return false;
	}
	//Added by zwsun , 2007-06-18, 增加日期校验
	if(checkDate(frmV003.notifyDate,1,"通知日期") == false){
		return false;
	}
	return true;
}

	function validate()
	{			
		if(parseFloat(reverseFormatAmount(document.frmV003.dDepositBalance.value))-parseFloat(reverseFormatAmount(document.frmV003.dDrawAmount.value))<0)
		{	
			alert("支取金额不能大于存单余额，请重新输入！");
			document.frmV003.dDrawAmount.focus();
			bResult = false;
			return false;
		}
	
		return true;
	}
</script>
 
 
 
 <%    
        
	OBHtml.showOBHomeEnd(out);	
}
catch (IException ie)
{
    OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
	return;
}
  
  %>
<safety:resources />
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/SignValidate.inc"%>
<%--
 页面名称 ：d003-v.jsp
 页面功能 : 新增贴现申请-一条贴现票据信息 显示页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d014-v.jsp*******");
	
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
		 String strAction = (String)request.getAttribute("txtAction");
		 long lDiscountBillID = -1; //贴现票据标识,
		 long lDiscountApplyID = -1; //贴现标识,
		 long lDiscountCredenceID = -1; //贴现凭证标识,
		 String strUser = ""; //原始出票人,
		 String strBank = ""; //承兑银行,
		 long lSerialNo = -1; //序列号,
		 long lIsLocal = -1; //是否在本地,
		 Timestamp tsCreate = null; //出票日,
		 Timestamp tsEnd = null; // 到期日,
		 String strCode = ""; //汇票号码,
		 double dAmount = 0.0; //汇票金额,
		 long lAddDay = -1; //节假日增加天数,
		 long lAcceptPOTypeID = -1; //票据类型,
		 String strFormerOwner = ""; //贴现单位直接前手
		 long lID = -1; //贴现申请指令ID
		 long loanType=OBConstant.LoanInstrType.DISCOUNT;
		String subtypeid="";
		long[] loanTypeid={loanType};
		 //贴现日期
		 Timestamp tsDiscountStartDate = null;
		 //申请指令编号
		 String strDiscountCode = "";
		 //贴现日期
	    strTmp = (String)request.getAttribute("tsDiscountStartDate");
		if (strTmp != null && strTmp.trim().length() > 0)
		{
			tsDiscountStartDate =  DataFormat.getDateTime(strTmp);
		}
		
		//贴现编号
	   strTmp = (String)request.getAttribute("strDiscountCode");
	   if (strTmp != null && strTmp.trim().length() > 0)
	   {
		  strDiscountCode = strTmp.trim();
	   }
		
	    DiscountBillInfo discountBillInfo = null;
		discountBillInfo = (DiscountBillInfo)request.getAttribute("resultInfo");
		if(discountBillInfo != null)
		{
		  Log.print("=======查询结果不为空！");
		  lDiscountBillID = discountBillInfo.getDiscountBillID(); //贴现票据标识,
		  Log.print("=======票据id："+lDiscountBillID);
		  lDiscountApplyID = discountBillInfo.getDiscountApplyID() ; //贴现标识,
		  
		  lDiscountCredenceID = discountBillInfo.getDiscountCredenceID() ; //贴现凭证标识,
		  strUser = discountBillInfo.getUser() ; //原始出票人,
		  strBank = discountBillInfo.getBank() ; //承兑银行,
		  lSerialNo = discountBillInfo.getSerialNo() ; //序列号,
		  lIsLocal = discountBillInfo.getIsLocal() ; //是否在本地,
		  tsCreate = discountBillInfo.getCreate() ; //出票日,
		  tsEnd = discountBillInfo.getEnd() ; // 到期日,
		  strCode = discountBillInfo.getCode() ; //汇票号码,
		  dAmount = discountBillInfo.getAmount() ; //汇票金额,
		  lAddDay = discountBillInfo.getAddDay() ; //节假日增加天数,
		  lAcceptPOTypeID = discountBillInfo.getAcceptPOTypeID() ; //票据类型,
		  strFormerOwner = discountBillInfo.getFormerOwner() ; //贴现单位直接前手
		  lID = discountBillInfo.getNLoanID(); 
		 
		  
		 
		}
		strTmp = (String)request.getAttribute("subtypeid");
		if (strTmp != null && strTmp.trim().length() > 0)
		{
			subtypeid =  strTmp;
		}
	    
		long lTemp = OBConstant.ShowMenu.YES;
	  
	   String modifyFlag = (String)request.getAttribute("modifyFlag");
	   
	  	if (modifyFlag != null && modifyFlag.equals("1"))
		{
			lTemp = OBConstant.ShowMenu.NO;
		}
		
		//显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, lTemp);
%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm">
<%//modify by wjliu --2007-3-21把lID传的值由lDiscountApplyID改为lID--贴现申请指令ID%>
<input type="hidden" name="lID" value="<%=lID%>">
<input type="hidden" name="lDiscountBillID" value="<%=lDiscountBillID%>">
<input type="hidden" name="lIDs" value="<%=lDiscountBillID%>"><!--用于删除!-->
<input type="hidden" name="lSerialNo" value="<%=lSerialNo%>">
<input type="Hidden" name="tsDiscountStartDate" value="<%=tsDiscountStartDate%>"><!--贴现日期-->
<input type="Hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<!--用于返回的参数!-->
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<TABLE border=0 class=top height=127 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=2><B>贴现申请——
	  <%
		  if (lDiscountBillID > 0) out.println("修改");
		  else out.println("新增");
	  %>票据明细</B></TD></TR>
  <TR>
    <TD height=227>
      <TABLE align=left border=0 height=81 width=100%>
        <TBODY>
        <TR borderColor=#ffffff>
          <TD height=30 width=1>&nbsp;</TD>
          <TD height=30 width=125><U>贴现票据明细：</U></TD>
          <TD colSpan=4 height=30></TD>
          <TD height=30 width=3>&nbsp;</TD></TR>
		   <TR borderColor=#ffffff>
          <TD height=30 width=1>&nbsp;</TD>
          <TD colSpan=2 height=17>贷款子类名称：
		  <select class='box'><option>
<%=LOANConstant.SubLoanType.getName(Long.valueOf(subtypeid).longValue())%>
			</option></select>
			</TD>
          <TD colSpan=4 height=30>&nbsp;</TD>
          <TD height=30 width=3>&nbsp;</TD></TR>
        <TR borderColor=#ffffff>
          <TD height=17 width=1>&nbsp;</TD>
          <TD colSpan=6 height=17>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR borderColor=#ffffff>
          <TD height=17 width=1>&nbsp;</TD>
          <TD height=17 width=125><font color='#FF0000'>* </font>原始出票人：</TD>
          <TD height=17 width=168>
			<INPUT name="strUser" class=tar onfocus="nextfield='lAddDay'" value="<%=strUser%>" size=30 maxlength="100"></TD>
          <TD colSpan=2 height=17>节假日增加计息天数：</TD>
          <TD colSpan=2 height=17><INPUT class=tar onfocus="this.select();nextfield='sBank'"
            name="lAddDay" size=8 value="<%=lAddDay >0 ? lAddDay : 0%>"></TD></TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=7 width=125><font color='#FF0000'>* </font>承兑方：</TD>
          <TD height=7 width=168>
			<INPUT name="strBank" class=tar onfocus="nextfield='lIsLocal'" value="<%=strBank%>" size=30 maxlength="100"></TD>
          <TD colSpan=2 height=31><font color='#FF0000'>* </font>承兑方所在地：</TD>
          <TD colSpan=2 height=31>
			<SELECT class='box' name="lIsLocal" onfocus="nextfield='strFormerOwner'">
				<OPTION value="<%=Constant.YesOrNo.NO%>" 
				<%if (lIsLocal == Constant.YesOrNo.NO) {out.println(" selected");} else {out.println("");}%>>非本地</OPTION>
				<OPTION value="<%=Constant.YesOrNo.YES%>" 
				<%if (lIsLocal == Constant.YesOrNo.YES) {out.println(" selected");} else {out.println("");}%>>本地</OPTION></SELECT>
		  </TD>
		</TR>
		<TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=7 width=125><font color='#FF0000'>* </font>贴现单位直接前手：</TD>
          <TD height=7 width=168>
			<INPUT name="strFormerOwner" class=tar onfocus="nextfield='lAcceptPOTypeID'" value="<%=strFormerOwner%>" size=30 maxlength="100">
		  </TD>
          <TD colSpan=2 height=31><font color='#FF0000'>* </font>票据类型：</TD>
          <TD colSpan=2 height=31>
		    <SELECT class='box' name="lAcceptPOTypeID" onfocus="nextfield='tsCreate'">
				<OPTION value=<%=OBConstant.DraftType.BANK%> 
				<%if (lAcceptPOTypeID == OBConstant.DraftType.BANK) {out.println(" selected");} else {out.println("");}%>><%=OBConstant.DraftType.getName(OBConstant.DraftType.BANK)%></OPTION>
				<OPTION value=<%=OBConstant.DraftType.BIZ%>
				<%if (lAcceptPOTypeID == OBConstant.DraftType.BIZ) {out.println(" selected");} else {out.println("");}%>><%=OBConstant.DraftType.getName(OBConstant.DraftType.BIZ)%></OPTION>
			</SELECT>
		  </TD>
		</TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=31 width=125><font color='#FF0000'>* </font>出票日：</TD>
          <TD height=31 width=168>
          		<fs_c:calendar 
	          	    name="tsCreate"
		          	value="" 
		          	properties="nextfield ='tsEnd'" 
		          	size="17"/>
		         <script>
	          		$('#tsCreate').val('<%=tsCreate != null ? DataFormat.getDateString(tsCreate) : ""%>');
	          	</script>
          </TD>
          <TD colSpan=2 height=31><font color='#FF0000'>* </font>到期日：</TD>
          <TD colSpan=2 height=31>
                   <fs_c:calendar 
						          	    name="tsEnd"
							          	value="" 
							          	properties="nextfield ='strCode'" 
							          	size="17"/>
				   <script>
	          		$('#tsEnd').val('<%=tsEnd != null ? DataFormat.getDateString(tsEnd) : ""%>');
	          	</script>
          </TD>
		</TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD height=31 width=125><font color='#FF0000'>* </font>汇票号码：</TD>
          <TD height=31 width=168><INPUT            name="strCode" class=tar onfocus="nextfield='dAmount'" value="<%=DataFormat.formatString(strCode)%>" size=17 maxlength="100"> </TD>
          <TD height=31 width=103><font color='#FF0000'>* </font>汇票金额：</TD>
          <TD height=31 width=36>
            <DIV align=right>￥</DIV></TD>
          <TD colSpan=2 height=31>
			<script language="javascript">
			createAmountCtrl("frm", "dAmount", "<%=DataFormat.formatAmount(dAmount)%>", "insertbill1", null);
			</script>			
		  </TD>
		</TR>		
        <TR borderColor=#ffffff>
          <TD height=31 width=1>&nbsp;</TD>
          <TD colSpan=6 height=31>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR borderColor=#ffffff>
          <TD height=31 width=3>&nbsp;</TD>
		  <TD colSpan=6 height=31>
            <DIV align=right>
			<%
		if (modifyFlag != null && modifyFlag.equals("1"))
		{
	%>
			<INPUT class=button name="submitApply" onclick="history.back(-1);" type=button value=" 返 回 ">
	<%}else{%>
				<INPUT class=button type=button name="insertbill1" onFocus="nextfield='submitfunction';" onclick="confirmSave(frm);" value=" 完 成 "> 
				<%if (lDiscountBillID > 0) { %>
				<INPUT class=button type=button name="deletebill1" onclick="confirmDel(frm)" value=" 删 除 ">
				<% } %>
				<!-- modify by wjliu 2007/3/20 把返回的方法改了
				<INPUT class=button type=button name="comeback"  onclick="goBack(<%=lDiscountApplyID%>);" value=" 返 回 "> 
				-->
				<INPUT class=button name="submitApply" onclick="history.back(-1);" type=button value=" 返 回 ">
				<%}%>
            </DIV></TD>
          </TR></TBODY></TABLE></TD></TR></TBODY></TABLE><P><BR></P>
</form>

<script language="javascript">
//返回
function goBack(lID)
{
   if (confirm("是否返回？"))
   {
     document.location.href = "d008-c.jsp?lID="+lID+"&tsDiscountStartDate=<%=tsDiscountStartDate%>";
   }	 
}

//编号比较
function CodeCompare(d_input1,d_input2,d_str,focusCtl)
{
	if (d_input1.value.length >0 && d_input2.value.length>0)
	{
		if (d_input1.value > d_input2.value)
		{
			alert(d_str);
			if (focusCtl!=null) 
				focusCtl.focus();
			else
				d_input1.focus();
			return (false);
		}
	}
	return true;
}

function confirmDel(frm)
{
  	if(confirm("是否要删除此票据信息？"))
	{
		frm.action="d012-c.jsp";
		showSending();
		frm.submit();
		return true;
	}						  
}

function confirmSave(frm)
{
	
	if (!InputValid(eval("frm.strUser"), 1, "string", 0, 0, 0,"原始出票人")) 
	{
		return false;
	}

	if (!InputValid(eval("frm.lAddDay"), 0, "Long", 0, 0, 0,"节假日增加计息天数")) 
	{
		return false;
	}
	
	
	if (eval("frm.lAddDay.value.length") > 0 && eval("frm.lAddDay.value") < 0)	
	{
		alert("节假日增加计息天数应大于等于0！");
		return false;
	}
	
	if (!InputValid(eval("frm.strBank"), 1, "string", 0, 0, 0,"承兑方")) 
	{
		return false;
	}

	if (!InputValid(eval("frm.strFormerOwner"), 1, "string", 0, 0, 0,"贴现单位直接前手")) 
	{
		return false;
	}

	if (!checkDate(eval("frm.tsCreate"),1,"出票日 "))
	{
		return(false);
	}

	if (!checkDate(eval("frm.tsEnd"),1,"到期日 "))
	{	
		return(false);
	}

	if (!CodeCompare(eval("frm.tsCreate"),eval("frm.tsEnd"),"出票日应该小于到期日！",null))
	{
		return(false);
	}
	
	if (!CodeCompare( eval("frm.tsCreate"),eval("frm.tsDiscountStartDate"), "出票日应该小于贴现日！",frm.tsCreate))
	{
		return(false);
	}
	
	if (!CodeCompare( eval("frm.tsDiscountStartDate"),eval("frm.tsEnd"), "到期日应该大于贴现日！",frm.tsEnd))
	{
		return(false);
	}
	
						
	if (!InputValid(eval("frm.strCode"), 1, "string", 0, 0, 0,"汇票号码")) 
	{
		return false;
	}

	if (!checkAmount(eval("frm.dAmount"),1,"汇票金额")) 
	{
		return false;
	}
	
	
	
	if(confirm("是否保存申请？"))
	{
		frm.action="d015-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
	
	
	
}

firstFocus(frm.strUser);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
</script>				
<%	
   //显示文件尾
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>


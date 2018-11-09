<%--
 页面名称 ：d006-v.jsp
 页面功能 : 新增贴现申请-贴现票据统计信息 显示页面
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
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.loan.util.LOANMagnifier"/>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d006-v.jsp*******");
	
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
		long loanType=OBConstant.LoanInstrType.DISCOUNT;
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
		String strAction = (String)request.getAttribute("txtAction");
		String frompage = (String)request.getAttribute("frompage");
		long lID = -1;
		long lInputUserID = -1;
		Timestamp tsDate = null;
		double dApplyDiscountAmount = 0.0;
		String strDiscountCode = "";
	
		DiscountInfo discountInfo = null;
		
		discountInfo = (DiscountInfo)request.getAttribute("resultInfo");
		if(discountInfo != null)
		{
		   lID = discountInfo.getID();
		   lInputUserID = discountInfo.getInputUserID();
		   tsDate = discountInfo.getInputDate();
		   dApplyDiscountAmount = discountInfo.getApplyDiscountAmount();
		   strDiscountCode = discountInfo.getDiscountCode();
		   Log.print("==============lID:"+lID);
		   Log.print("==============DiscountStartDate:"+discountInfo.getDiscountStartDate());
		   Log.print("==============strDiscountCode:"+discountInfo.getDiscountCode());
		}
		
	   //显示文件头
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	  
%>		
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post" action="d007-c.jsp">
<input type="Hidden" name="lID" value="<%=lID%>">
<input type="Hidden" name="lInputUserID" value="<%=lInputUserID%>">
<input type="Hidden" name="tsInputDate" value="<%=tsDate%>">
<input type="Hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<!--用于返回的参数!-->
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<TABLE border=0 class=top height=100 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现申请－新增</B></TD></TR>
  <TR>
    <TD height=333 vAlign=top>
      <TABLE height=54 width="100%">
        <TBODY>
        <TR vAlign=middle>
          <TD height=23 width="15%">贴现申请编号：</TD>
          <TD height=23 width="11%"><%if ((discountInfo!=null)&&(discountInfo.getDiscountCode()!=null)) {out.println(discountInfo.getDiscountCode());} else {out.println("");}%></TD>
          <TD height=23 width="9%">&nbsp;</TD>
           <TD colSpan=3 height=28>贷款子类名称：
<%LOANConstant.SubLoanType.showList(out,"subLoanType",-1,false,false,"",sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,loanTypeid,null );%>
			
			</TD>
         </TR>
        <TR vAlign=middle>
          <TD colSpan=7 height=15>
            <HR>
          </TD></TR>
        <TR vAlign=middle>
          <TD height=28 width="15%"><U>贴现申请详情：</U></TD>
          <TD height=28 width="11%">&nbsp;</TD>
          <TD height=28 width="9%">&nbsp;</TD>
          <TD height=28 width="15%">&nbsp;</TD>
          <TD height=28 width="11%">&nbsp;</TD>
          <TD height=28 width="14%">&nbsp;</TD>
          <TD height=28 vAlign=middle width="25%">&nbsp;</TD></TR>
        <TR vAlign=middle>
          <TD height=29 width="15%"><font color='#FF0000'>*</font>申请贴现汇票： 
            </TD>
          <TD height=29 width="11%">共 
			<INPUT class=box onfocus="this.select();nextfield='lBankAccepPO'" name="lApplyDiscountPO" size=3 maxlength="3" value="<%if ((discountInfo!=null)&&(discountInfo.getApplyDiscountPO()>0)) {out.println(discountInfo.getApplyDiscountPO());} else {out.println("0");}%>"> 张 </TD>
          <TD height=29 width="9%">其中：</TD>
          <TD height=29 width="15%"><font color='#FF0000'>*</font>银行承兑汇票：</TD>
          <TD height=29 width="11%">
			<INPUT class=box onfocus="this.select();nextfield='lBizAcceptPO'" maxlength="3" name="lBankAccepPO" size=3 value="<%if ((discountInfo!=null)&&(discountInfo.getBankAccepPO()>0)) {out.println(discountInfo.getBankAccepPO());} else {out.println("0");}%>"> 张</TD>
          <TD height=29 width="14%"><font color='#FF0000'>*</font>商业承兑汇票：</TD>
          <TD height=29 vAlign=middle width="25%">
			<INPUT class=box onfocus="this.select();nextfield='dApplyDiscountAmount'" maxlength="3" name="lBizAcceptPO" size=3 value="<%if ((discountInfo!=null)&&(discountInfo.getBizAcceptPO()>0)) {out.println(discountInfo.getBizAcceptPO());} else {out.println("0");}%>"> 张</TD></TR></TBODY></TABLE>
      <TABLE align=center border=0 height=60 width=100%>
        <TBODY>
        <TR>
          <TD colSpan=2 height=29 nowrap><font color='#FF0000'>*</font>申请贴现金额：</TD>
          <TD align=right height=29 width=22>￥</TD>
          <TD colSpan=2 height=29>
			<script language="javascript">
			createAmountCtrl("frm", "dApplyDiscountAmount", "<%=DataFormat.formatAmount(dApplyDiscountAmount)%>", "tsDiscountStartDate", null);
			</script>
			</TD>
          <TD height=29 width=106>&nbsp;</TD>
          <TD height=29 width=18>&nbsp;</TD>
          <TD colSpan=3 height=29>&nbsp;</TD></TR>		
		<TR>
          <TD colSpan=3 height=29><font color='#FF0000'>*</font>贴现开始日期：</TD>
          <TD colSpan=2 height=29>
          <fs_c:calendar 
						          	    name="tsDiscountStartDate"
							          	value="" 
							          	properties="nextfield ='strDiscountReason'" 
							          	size="18"/>
			 <script>
	          		$('#tsDiscountStartDate').val('<%if ((discountInfo!=null)&&(discountInfo.getDiscountStartDate()!=null)) out.print(DataFormat.getDateString(discountInfo.getDiscountStartDate()));%>');
	          	</script>
		</TR>
        <TR>
          <TD colSpan=3 height=30><font color='#FF0000'>*</font>贴现原因：</TD>
          <TD colSpan=7 height=30><TEXTAREA  cols=65  onfocus="nextfield='strDiscountPurpose'"  onchange="if(this.value.length>200) this.value=this.value.substr(0,200)"  name="strDiscountReason"><%if ((discountInfo!=null)&&(discountInfo.getDiscountReason()!=null)) {out.println(discountInfo.getDiscountReason());} else {out.println("");}%></TEXTAREA> 
          </TD></TR>
        <TR>
          <TD colSpan=3 height=2><font color='#FF0000'>*</font>贴现用途：</TD>
          <TD colSpan=7 height=2><TEXTAREA  cols=65 onfocus="nextfield='isPurchaserInterest'"  onchange="if(this.value.length>200) this.value=this.value.substr(0,200)"  name="strDiscountPurpose"><%if ((discountInfo!=null)&&(discountInfo.getDiscountPurpose()!=null)) {out.println(discountInfo.getDiscountPurpose());} else {out.println("");}%></TEXTAREA> 
            </TD></TR>
			<TR>
				<TD colSpan=3 height=29><font color='#FF0000'>* </font>是否买方付息：</TD>
				<TD colSpan=2 height=29>
					<SELECT class='box' name="isPurchaserInterest" onfocus="nextfield='discountClientName'">
						<OPTION value="<%=Constant.YesOrNo.YES%>" 
						<%if ((discountInfo!=null)&&(discountInfo.getIsPurchaserInterest()==Constant.YesOrNo.YES)) {out.println(" selected");} else {out.println("");}%>>是</OPTION>
						<OPTION value="<%=Constant.YesOrNo.NO%>" 
						<%if ((discountInfo!=null)&&(discountInfo.getIsPurchaserInterest()==Constant.YesOrNo.NO)) {out.println(" selected");} else {out.println("");}%>>否</OPTION>
					</SELECT>
				</TD>
				<TD height=29 width=106>&nbsp;</TD>
				<TD height=29 width=18>&nbsp;</TD>
				<TD colSpan=3 height=29>&nbsp;</TD>
			</TR>

			<TR>
<%
				String strMagnifierNameClientName = "出票人";
				String strFormNameClientName = "frm";
				String strMainPropertyClientName = " size='50' ";
				String strPrefixClientName = "";
				String[] strMainNamesClientName = {"discountClientName"};
				String[] strMainFieldsClientName = {"ClientName"};
				String strReturnInitValuesClientName=DataFormat.formatString(discountInfo.getDiscountClientName());
				String[] strReturnNamesClientName = {"discountClientID"};
				String[] strReturnFieldsClientName = {"ID"};
				String[] strReturnValuesClientName = {""};
				String[] strDisplayNamesClientName = {"客户编号","客户名称"};
				String[] strDisplayFieldsClientName = {"ClientCode","ClientName"};
				int nIndexClientName = 1;
				long lOfficeIDCReceive  = sessionMng.m_lOfficeID;
				String strSQLClientName = "getClientSQL(" +  lOfficeIDCReceive + ",'' )";
				String[] strMatchValueClientName = {"ClientCode","ClientName"};
				String strNextControlsClientName = "NextStep";
				String strTitleClientName = "<font color='#FF0000'>* </font>出票人";
				String strFirstTDClientName="  colSpan=3 height=30";
				String strSecondTDClientName=" colSpan=7 height=30";

				Magnifier.showZoomCtrl(out,strMagnifierNameClientName,strFormNameClientName,strPrefixClientName,strMainNamesClientName,strMainFieldsClientName,strReturnNamesClientName,strReturnFieldsClientName,strReturnInitValuesClientName,strReturnValuesClientName,strDisplayNamesClientName,strDisplayFieldsClientName,nIndexClientName,strMainPropertyClientName,strSQLClientName,strMatchValueClientName,strNextControlsClientName,strTitleClientName,strFirstTDClientName,strSecondTDClientName);
%>
			</TR>
        <TR>
          <TD colSpan=10 height=13>
            <HR>
          </TD></TR>
        <TR>
          <TD height=17 width=98>&nbsp;</TD>
          <TD align=left colSpan=2 height=17></TD>
          <TD align=right height=17 width=55>
           </TD>
          <TD align=right height=17 width=121></TD>
          <TD align=right colSpan=2 height=17></TD>
          <TD align=right height=17 width=6></TD>
          <TD align=right height=17 width=327>
            <DIV align=right>
			<INPUT class=button name="LastStep"  type=button value=" 上一步 " onfocus="doLastStep();" onclick="doLastStep();"> 
            <INPUT class=button name="NextStep" type=button value=" 下一步 " onfocus="nextfield='submitfunction'" onclick="confirmSave(frm);">	
            </DIV></TD>
          <TD align=right height=17 width=15></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
</form>
<script language="javascript">
function doLastStep()
{
   if(confirm("是否返回上一步?"))
   {
	   <%if(frompage != null && frompage.equals("query")){%>
		document.location.href="<%=Env.EBANK_URL%>loan/query/q001-c.jsp?instrTypeID=3&parentID=<%=lID%>";
	 <%}else{%>
		 document.location.href="d002-c.jsp";
	  <%}%>
    
   }
}


function confirmSave(frm)
{
  	if (!InputValid(frm.lApplyDiscountPO, 1, "int", 0, 0, 0,"申请贴现汇票张数")) 
	{
		return false;
	}

	if (parseInt(frm.lApplyDiscountPO.value) < 1) 
	{
		alert("申请贴现汇票张数应大于0！");
		return false;
	}

	if (!InputValid(frm.lBankAccepPO, 1, "int", 0, 0, 0,"银行承兑汇票张数")) 
	{
		return false;
	}

	if (parseInt(frm.lBankAccepPO.value) < 0) 
	{
		alert("银行承兑汇票张数应大于等于0！");
		return false;
	}

	if (!InputValid(frm.lBizAcceptPO, 1, "int", 0, 0, 0,"商业承兑汇票张数")) 
	{
		return false;
	}
			
	if (parseInt(frm.lBizAcceptPO.value) < 0) 
	{
		alert("商业承兑汇票张数应大于等于0！");
		return false;
	}
					
	if (parseInt(frm.lApplyDiscountPO.value) != (parseInt(frm.lBizAcceptPO.value) + parseInt(frm.lBankAccepPO.value))) 
	{
		alert("申请贴现汇票张数应等于银行承兑汇票张数加上商业承兑汇票张数！");
		return false;
	}
	if ( (parseInt(frm.lBizAcceptPO.value) != 0) && (parseInt(frm.lBankAccepPO.value) != 0) ) 
	{
		alert("同一笔贴现申请不允许有不同类型的汇票");
		return false;
	}
	if (!checkAmount(frm.dApplyDiscountAmount,1,"申请贴现金额")) 
	{
		return false;
	}

	if (!checkDate(frm.tsDiscountStartDate,1,"贴现开始日期 "))
	{
		return(false);
	}
	

	if (!InputValid(frm.strDiscountReason, 1, "string", 0, 0, 0,"贴现原因")) 
	{
		return false;
	}

	if (!InputValid(frm.strDiscountPurpose, 1, "string", 0, 0, 0,"贴现用途")) 
	{
		return false;
	}
	if (frm.isPurchaserInterest.value == <%=Constant.YesOrNo.YES%>)
	{
		if( (frm.discountClientID.value < 0 ||frm.discountClientID.value=="") && (frm.discountClientName.value == "") )
		{
                alert('请使用放大镜输入出票人');
				return false;
        }
	}
	if(confirm("是否保存申请？"))
	{
		frm.submit();
	}
}

firstFocus(frm.lApplyDiscountPO);
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


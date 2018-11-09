 <%@page contentType="text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<!--Header start-->
<%
//response.setHeader("Pragma", "no-cache");
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", -1); 
%>
<!--Header end-->
<%@page import="java.util.*,
com.iss.itreasury.loan.util.*,
com.iss.itreasury.ebank.util.*,
com.iss.itreasury.util.*,
com.iss.itreasury.ebank.obaheadrepaynotice.dataentity.*
" 
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* 标题固定变量 */
	String strTitle = "[贷款还款通知单]";
%>	
<%
	  try
	  {
           /**
            * isLogin start
           */
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
		
		AheadRepayNoticeInfo info  = (AheadRepayNoticeInfo) request.getAttribute("info");
		
		long lAction = -1;
		//取参数变量
		String strTemp = "";
		 strTemp = String.valueOf(request.getAttribute( "action" ));
        if( strTemp != null && !strTemp.equals("") )
        {
			try
			{
            	lAction = Long.parseLong(strTemp);
			}
			catch(Exception e)
            {
					lAction = -1;
             }
        }
		
		long lActionC = -1;
		 strTemp = String.valueOf(request.getAttribute( "actionControl" ));
        if( strTemp != null && !strTemp.equals("") )
        {
			try
			{
            	lActionC = Long.parseLong(strTemp);
			}
			catch(Exception e)
            {
					lActionC = -1;
             }
        }
		//为了返回指令查询
		String txtAction = "";
		if( (String)request.getAttribute("txtAction") != null )
			txtAction = (String)request.getAttribute("txtAction");
		//显示文件头
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
%>
<SCRIPT language="javascript" src="/webob/js/Control.js"></SCRIPT>
<SCRIPT language=JavaScript src="/webob/js/Check.js"></SCRIPT>
<safety:resources />
<form name="frm" action="../aheadrepaynotice/a006-c.jsp" method="post">
	
<table width="740" border="0" class="top" height="113">  
<tr class="tableHeader">    
      <td class="FormTitle" height="13"><b>贷款还款通知单——提交</b>
	  <input name="action" type="hidden">
	  <input name="lID" type="hidden" value="<%=info.getID()%>">
	  <input type="hidden" name="actionControl"  value="<%=lActionC%>">
	  <input type="hidden" name="txtAction" value="<%=txtAction%>">
	  </td>
</tr>  
<tr>     
      <td valign="bottom"> 
        <table width="100%" border="0">
          <tr> 
            <td width="16%"><u>贷款详情</u>：</td>
            <td width="33%" height="30">&nbsp;</td>
            <td width="13%" height="29">通知单编号：</td>
            <td width="38%" height="29"><%=info.getCode()%></td>
          </tr>
          <tr> 
            <td width="17%">借款单位：</td>
            <td colspan=3> 
              <input type="text" name="textfield" size="76" class="box" value="<%=info.getClientName()%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%">合同编号：</td>
            <td width="19%"> 
              <input type="text" name="textfield3" size="18" class="box" value="<%=info.getContractCode()%>" disabled>
            </td>
            <td width="15%">贷款期限：</td>
            <td width="49%"> 
              <input type="text" name="textfield33" size="18" class="box" value="<%=info.getIntervalNum()%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%">合同金额：￥</td>
            <td width="19%"> 
              <input type="text" size=18 name="Input222" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getContractAmount())%>" disabled>
            </td>
            <td width="15%">合同余额：￥</td>
            <td width="49%"> 
              <input type="text" size=18 name="Input2222" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getContractBalance())%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%">放款通知单编号：</td>
            <td width="19%"> 
              <input type="text" name="textfield22" size="18" class="box" value="<%=info.getLetoutNoticeCode()%>" disabled>
            </td>
            <td width="15%">放款通知单利率：</td>
            <td width="49%"> 
              <input type="text" name="txt1" class="tar" size="18" value="<%=DataFormat.formatRate(info.getLetoutNoticeRate())%>" disabled>
              % </td>
          </tr>
          <tr> 
            <td width="17%" nowrap>放款通知单金额：￥</td>
            <td width="19%"> 
              <input type="text" size=18 name="Input2" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getLetoutNoticeAmount())%>" disabled>
            </td>
            <td width="15%" nowrap>放款通知单余额：￥</td>
            <td width="49%"> 
              <input type="text" size=18 name="Input22" class="tar" value="<%=DataFormat.formatDisabledAmount(info.getLetoutNoticeBalance())%>" disabled>
            </td>
          </tr>
          <tr> 
            <td width="17%"><font color="#CC0000">*</font> 还款金额：￥</td>
            <td width="19%"> 
			<!--script language="javascript">
				createAmountCtrl("frm", "dAheadRepayAmount", "<%=DataFormat.formatDisabledAmount(info.getAmount())%>", "submitfunction", null);
			</script-->
			<input type="text" name="txt11" class="tar" size="18" value="<%=DataFormat.formatDisabledAmount(info.getAmount())%>" disabled>
			<input type="hidden" name="lID"  value="<%=info.getID()%>" >
            </td>
            <td width="15%">还款利率：</td>
            <td width="49%"> 
              <input type="text" name="txt12" class="tar" size="18" value="<%=DataFormat.formatRate(info.getLetoutNoticeRate())%>" disabled>
              % </td>
          </tr>
		   <tr> 
            <td width="17%"><font color="#CC0000">*</font> 是否提前还款：</td>
            <td width="19%"> 
<%
			LOANConstant.YesOrNo.showList(out,"nIsAhead",0,info.getIsAhead(),false,false,"disabled");
%>      
            </td>
            <td width="15%">&nbsp;</td>
            <td width="49%">&nbsp;</td>
          </tr>
          <tr> 
            <td colspan="4"> 
              <hr>
            </td>
          </tr>
          <tr> 
            <td width="16%"> 
              <INPUT class=button name=btnModifyExPlan onclick="Javascript: frmModifyExPlan('<%=info.getPlanID()%>');" type="button" value=" 查看执行计划 ">
            </td>
            <td width="33%">&nbsp; </td>
            <td width="13%"></td>
            <td width="38%">&nbsp;</td>
          </tr>
        </table>
      <tr>    
		
      <td valign="top"> 
        <hr>        
        <table width="100%" border="0">
          <tr align="right">
            <td width="97%">
<%if(info.getStatusID() != OBConstant.LoanInstrStatus.ACCEPT && info.getStatusID() != OBConstant.LoanInstrStatus.REFUSE && info.getStatusID() != OBConstant.LoanInstrStatus.CANCEL && info.getStatusID() != OBConstant.LoanInstrStatus.APPROVE)
{
		 if (info.getStatusID() == OBConstant.LoanInstrStatus.SUBMIT) { %>			
			<% if (info.getInputUserID() == sessionMng.m_lUserID) { %>
				<input class=button name="submitCancel" onClick="cancelto()" type=button value="取消通知单">	
			<% } %>
		<% }//如果是撰写状态,则显示提交按纽
		else if (info.getStatusID() == OBConstant.LoanInstrStatus.SAVE && info.getInputUserID() == sessionMng.m_lUserID)
		{ %>			
			  <input class=button name="submitAdd" onClick="saveto()" type=button value=" 提 交 ">
		<%}
}%>

              <input class=button name="submitAdd" onClick="addto()" type=button value="新增通知单">  
<%if(info.getStatusID() != OBConstant.LoanInstrStatus.REFUSE && info.getStatusID() != OBConstant.LoanInstrStatus.CANCEL)
{%>
              <input class=button name="submitPrint" onClick="frmPrint()" type=button value=" 打 印 ">
<%}%>
              <input class=button onClick="returnto()" type=button value=" 返 回 " name="breturn">
			  <!--input class=bitton onClick="testit()" type=button value="test" name="test"-->
            </td>
            <td colspan="2" width="3%">&nbsp; </td>
          </tr>
        </table>
		<TABLE width="100%" border="0">
		<TR>
		  <TD colSpan=9><HR></TD>
		</TR>
		<TR>
          <TD height=2 width=30>&nbsp;</TD>
          <TD align=left height=2 width=222>录入人：<%=info.getInputUserName()%></TD>
          <TD align=center height=2 width=310>录入时间：<%=info.getInputDate()%></TD>
          <TD align=right height=2 width=252>状态：           			
		  </TD>
		    <TD height=2 width=70><%=info.getStatus()%></TD>
		</TR>
		</TABLE>
      </td>
</tr>
</table>
<P><BR></P>
<input type="hidden" name="lStatusID" value="<%=OBConstant.LoanInstrStatus.SUBMIT%>">
</form>

<script language="javascript">

function frmModifyExPlan(planID) //执行计划  action on the Form : frmPage
{
	//var url = "../loan/S443-R.jsp?control=view&isSM=<%=Constant.YesOrNo.NO%>&isYU=<%=Constant.YesOrNo.NO%>&nContractID=" + contractID +"&nTmpID="+planID;
	//window.open(url, "执行计划", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	var url = "../query/q094-v.jsp?control=view&isYU=2&nTmpID="+planID;
	window.open(url, "执行计划", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
}
function testit()
{
	//window.location = "../aheadrepaynotice/a002-c.jsp?lID=<%=info.getID()%>&lIsChange=2";
}

function frmPrint()
{
	var url = "../aheadrepaynotice/a007-c.jsp?action=print&lID=<%=info.getID()%>";
	window.open(url,"popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");
}

function returnto()
{
	<%if(txtAction.equals("modify"))
	{%>
		window.location="../query/q003-c.jsp";
	<%}else{%>
		window.location = "../aheadrepaynotice/a001-v.jsp";
	<%}%>
}

function addto()
{
	window.location = "../aheadrepaynotice/a001-v.jsp";
}

function checkto()
{
	if(confirm("是否审核通知单？"))
	{
		frm.action.value="check";
		frm.submit();
	}
}

function saveto()
{
	if(confirm("是否提交通知单？"))
	{
		window.location = "../aheadrepaynotice/a009-c.jsp?lStatusID=<%=OBConstant.LoanInstrStatus.SUBMIT%>&lID=<%=info.getID()%>&txtAction=<%=txtAction%>";
		/*frm.action="a009-c.jsp";
		frm.lStatusID.value="<%=OBConstant.LoanInstrStatus.SUBMIT%>";
		frm.action.value="save";
		frm.submit();*/
	}
}

function cancelto()
{
	if(confirm("是否取消通知单？"))
	{
		window.location = "../aheadrepaynotice/a009-c.jsp?lStatusID=<%=OBConstant.LoanInstrStatus.CANCEL%>&lID=<%=info.getID()%>&txtAction=<%=txtAction%>";
		/*frm.action="a009-c.jsp";
		frm.lStatusID.value="<%=OBConstant.LoanInstrStatus.CANCEL%>";
		frm.action.value="cancel";
		frm.submit();*/
	}
}

setFormName("frm");

</script>
<%
		OBHtml.showOBHomeEnd(out);	
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
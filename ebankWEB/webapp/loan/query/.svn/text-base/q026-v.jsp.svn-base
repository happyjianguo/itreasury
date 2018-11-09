<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
                com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.obsystem.bizlogic.*,
                com.iss.itreasury.ebank.obquery.bizlogic.*,
                com.iss.itreasury.loan.loancommonsetting.bizlogic.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
				com.iss.itreasury.loan.contract.bizlogic.*,
				com.iss.itreasury.loan.contract.dao.*,
                com.iss.itreasury.loan.contract.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
//////////////////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
		if( !sessionMng.isLogin() )
		{	
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E002");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		//判断是否有权限
		if (sessionMng.hasRight(request) == false)
		{
			OBHtml.showMessage(out,sessionMng,null,"登录",null,Constant.RecordStatus.INVALID,"Gen_E003");
			OBHtml.showOBHomeEnd(out);
			out.flush();
			return;
		}
		
         
		//定义变量，获取请求参数

		String strTmp = "";
		String strControl = "";
		String backurl = "";
		String backpage = "";

		long lCount = 0;
		double dTotalAmount = 0;
					
		long lDiscountID = -1;		//贴现标示
		long lContractID = -1;		//贴现标示

		String strCode = "";
		String strClientName = "";
											
		Collection temp = null;

		DiscountLoanInfo  dli = new DiscountLoanInfo ();
		OBLoanQuery Discount=new OBLoanQuery();			
		
		ContractInfo cinfo=new ContractInfo();
		OBContractQuery contract=new OBContractQuery();

		// 分页参数
		long lPageCount = 1;                   //几页
		long lPageNo = 1;                      //第几页
		long lOrderParam = 1;                  //根据什么排序
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序

		//判断正序和反序
		strTmp = (String)request.getAttribute("lDesc");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lDesc = Long.parseLong(strTmp.trim());
		}
		
		//判断排序条件
		strTmp = (String)request.getAttribute("lOrderParam");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lOrderParam = Long.parseLong(strTmp.trim());
		}

		//显示页数
		strTmp = (String)request.getAttribute("lPageNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
		     lPageNo = Long.parseLong(strTmp.trim());
		}
				
///////control///////////////////////////////////////////////////////////////////////////////				
	    strTmp = (String)request.getAttribute("control");
        if( strTmp != null && strTmp.length() > 0 )
		{
			strControl = strTmp.trim();
		}

		strTmp = (String)request.getAttribute("lDiscountID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lDiscountID = Long.parseLong(strTmp.trim());
		}
		
		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}

		strTmp = (String)request.getAttribute("backurl");
        if(strTmp != null && strTmp.length() > 0)
        {
             backurl = strTmp.trim();
        }

		strTmp = (String)request.getAttribute("backpage");
        if(strTmp != null && strTmp.length() > 0)
        {
             backpage = strTmp.trim();
        }

////////view/////////////////////////////////////////////////////////////////////////////		
		if (strControl.equals("view"))
		{
			dli = Discount.findDiscountByID(lDiscountID);
			if (lContractID > 0)
			{
				cinfo = contract.findByID(lContractID);
				strCode = "贴现合同编号：" + DataFormat.formatString(cinfo.getContractCode());
				strClientName = cinfo.getBorrowClientName();
				temp = Discount.findDiscountBillByContractID(lContractID, Constant.PageControl.CODE_PAGELINECOUNT, lPageNo, lOrderParam, lDesc);
			}
			else if (lDiscountID > 0)
			{
				dli = Discount.findDiscountByID(lDiscountID);
				strCode = "贴现申请编号：" + DataFormat.formatString(dli.getDiscountCode());
				strClientName = dli.getApplyClientName();
				temp = Discount.findDiscountBillByDiscountID(lDiscountID, Constant.PageControl.CODE_PAGELINECOUNT, lPageNo, lOrderParam, lDesc);
			}
       	}		

///////////////////////////////////////////////////////////////////////////////////////////////////

		//如果是弹出窗口
		long lShowMenu = Constant.YesOrNo.NO;
  		strTmp = (String)request.getAttribute("isSM");
		if(strTmp != null && strTmp.length() > 0)
		{
			lShowMenu = Long.parseLong(strTmp);
		}

		OBHtml.showOBHomeHead(out,sessionMng,"贴现申请",lShowMenu);

%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm">

<TABLE border=0 class=top height=133 width="95%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现票据明细</B></TD></TR>
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width="730">
        <TBODY>
        <TR>
          <TD colSpan=4 height=17><%=strCode%></TD></TR>
        <TR>
          <TD colSpan=4 height=20 vAlign=top>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR>
          <TD height=20 width="50%"><U>贴现票据明细表</U></TD>
          <TD height=20 align=right width="50%" colSpan=3>&nbsp;</TD>
        </TR>
        <TR>
          <TD colSpan=4 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width="100%">
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
                <TD class=ItemTitle colSpan=11 height=27>
                  <DIV align=left>贴现申请单位：<%=strClientName%></DIV></TD></TR>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
                <TD class=ItemTitle height=20 width="5%"><A href="javascript:go('1');">序列号</A></TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('2');">原始出票人</A></TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('3');">承兑方</A></TD>
                <TD class=ItemTitle height=20 width="6%"><A href="javascript:go('4');">承兑方所在地</A></TD>
                <TD class=ItemTitle height=20 width="7%"><A href="javascript:go('11');">贴现单位直接前手</A></TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('5');">出票日</A></TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('6');">到期日</A></TD>
                <TD class=ItemTitle height=20 width="10%"><A href="javascript:go('7');">汇票号码</A></TD>
                <TD class=ItemTitle height=20 width="12%"><A href="javascript:go('8');">汇票金额</A></TD>
                <TD class=ItemTitle height=20 width="7%"><A href="javascript:go('9');">节假日增加天数</A></TD>
				<TD class=ItemTitle height=20 width="10%"><A href="javascript:go('10');">汇票类型</A></TD>
              </TR>

<% /************************* Information Display********************************/ %>	
	<%	
                if( (temp != null) && (temp.size() > 0) )
                {
					Iterator it = temp.iterator();
                    while (it.hasNext() )
					{
						DiscountBillInfo info = ( DiscountBillInfo ) it.next();                     
						lPageCount = info.getCount() / Constant.PageControl.CODE_PAGELINECOUNT;
						//lPageCount = info.lCount / 1;
            
						//if ((info.lCount % 1) != 0)
						if ((info.getCount() %  Constant.PageControl.CODE_PAGELINECOUNT) != 0)
						{
							lPageCount ++;
						}

						lCount = info.getCount();
						dTotalAmount = info.getTotalAmount();
	%>
        <TR align="center" borderColor=#999999>
          <TD class=ItemBody>
		  <%=DataFormat.formatInt(info.getSerialNo(),3,true)%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%if ((info!=null)&&(info.getUserName()!=null)) {out.println(info.getUserName());} else {out.println("");}%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%if ((info!=null)&&(info.getBank()!=null)) {out.println(info.getBank());} else {out.println("");}%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%
				if (info.getIsLocal()==Constant.YesOrNo.NO) 
				{
					out.println("非本地");
				} 
				else if (info.getIsLocal()==Constant.YesOrNo.YES) 
				{
					out.println("本地");
				}
		  %>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.formatString(info.getFormerOwner())%> 
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getCreate())%> 
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getEnd())%>
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%if ((info!=null)&&(info.getCode()!=null)){out.println(info.getCode());} else {out.println("");}%>
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%if ((info!=null)&&(info.getAmount()>0)) {out.println(DataFormat.formatDisabledAmount(info.getAmount()));} else {out.println("0.00");}%>		 
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%if ((info!=null)&&(info.getAddDays()>0)) {out.println(info.getAddDays());} else {out.println("0");}%> 
		  </TD>
          <TD align="center" class=ItemBody>
		  <%if ((info!=null)&&(info.getAcceptPOTypeID()>0)) {out.println(LOANConstant.DraftType.getName(info.getAcceptPOTypeID()));} else {out.println("&nbsp;");}%>
		  </TD>
		</TR>
<% /*************************The If and while 's End ********************************/ %>
        <%
		           }
	            }
			   else{
			   %>
         <TR align=center borderColor=#999999>          
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody>&nbsp;</TD></TR>			   
			   <%			   
			   }
        %>
				  
        <TR borderColor=#999999>
          <TD class=SearchBar colSpan=11 height=25>
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar height=50 width="730">
              <TBODY>
              <TR>                
				<td height="2">笔数总计：<%=lCount%>&nbsp;&nbsp;金额总计：￥<% if (dTotalAmount > 0) out.println(DataFormat.formatListAmount(dTotalAmount)); else out.println("0.00"); %>
				</td>
				<TD height=2 align=right>
<INPUT class=SearchBar_Page maxLength=3 name="cz" size=3 onkeydown="key_down()" value="<%=lPageNo%>"> of <%=lPageCount%> 
<INPUT class=SearchBar_Btn name="aaaaa" type=button onclick="goto()" value=go> 

<%if(lPageNo > 1) {%>
<input type="button" name="Submit4222" value="|&lt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value='1';gohere();">
<%}%>
<%if(lPageNo > 1) {%>
<input type="button" name="Submit5222" value="&lt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageNo-1%>;gohere();">
<%}%>
<%if(lPageNo < lPageCount) {%>
<input type="button" name="Submit6222" value="&gt;" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageNo+1%>;gohere();">
<%}%>
<%if(lPageNo < lPageCount) {%>
<input type="button" name="Submit7222" value="&gt;|" class="SearchBar_Btn" onclick="javascript:frm.lPageNo.value=<%=lPageCount%>;gohere();">
<%}%>
</TD></TR>
   </TBODY></TABLE></TD></TR></TBODY></TABLE>
            <HR>
            <DIV align=right>
			<%if ((backurl != null) && (backurl.length() > 0)) {%>
				<INPUT class=button name="comeBack" onclick="MM_goToURL('self','<%=backurl%>.jsp?lDiscountID=<%=lDiscountID%>&control=view&backpage=<%=backpage%>');" type=button value=" 返 回 ">
			<%} else {%>
				<INPUT class=button name="comeBack" onclick="window.close();" type=button value=" 关 闭 ">
			<%}%>
            </DIV>
</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="control" value="view">
<input type="hidden" name="lDiscountID" value="<%=lDiscountID%>">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="backurl" value="<%=backurl%>">
<input type="hidden" name="backpage" value="<%=backpage%>">

<P><BR></P>
</form>

<script language="javascript">

function key_down()
{
	if (window.event.keyCode == 13)
	{
		if (InputValid(frm.cz,1, "int", 1, 1, frm.lPageCount.value,"页数")) 
		{
			frm.lPageNo.value=frm.cz.value;
		}
		else	return (false);
		confirmSave(frm);
	}
}

function go(para)
{  
	if (frm.lOrderParam.value==para)
	{
		if (frm.lDesc.value=="<%=Constant.PageControl.CODE_ASCORDESC_ASC%>")
		{
			frm.lDesc.value="<%=Constant.PageControl.CODE_ASCORDESC_DESC%>";
		}
		else
		{
			frm.lDesc.value="<%=Constant.PageControl.CODE_ASCORDESC_ASC%>"; 
		}
	}
	frm.lOrderParam.value=para;
	confirmSave(frm);
}

function goto()
{
	var lMax=frm.lPageCount.value;
	if (InputValid(frm.cz,1, "int", 1, 1, lMax,"页数"))
	{
		frm.lPageNo.value=frm.cz.value;
	}
	else return (false);
	confirmSave(frm);
}

function gohere()
{
	confirmSave(frm);
}

function confirmSave(frm)
{  			
	frm.control.value="view";
	frm.action="q026-v.jsp";
	showSending();
	frm.submit();			  
}

firstFocus(frm.comeBack);
//setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 

</script>			
<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"贴现申请", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>
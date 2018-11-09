<%--
 页面名称 ：d026-v.jsp
 页面功能 : 新增贴现申请--贴现票据明细打印 显示页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
				java.util.Vector,
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
<%
	/* 标题固定变量 */
	String strTitle = "[贴现申请]";
%>					
<%
  try
  {
	Log.print("*******进入页面--ebank/loan/discountapply/d026-v.jsp*******");
	
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
		long lPrintType = -1;//1:套打
		
		long lCount = 0;
		long lPageCount = 0;
		double dTotalAmount = 0;
					
		long lDiscountID = -1;		//贴现标示
		Vector temp = null;
	
		DiscountInfo  discountInfo = null;
		discountInfo = (DiscountInfo)request.getAttribute("resultDiscountInfo");
		
		
		if(discountInfo != null)
		{
		  	lCount = discountInfo.getBillCount();
			dTotalAmount = discountInfo.getBillAmount();
		}
		
		temp = (Vector)request.getAttribute("resultInfo");
	    
		//显示文件头
       // OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
%>
<HTML><HEAD><TITLE>贴现票据明细</TITLE>
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
body,td,p {
	font-size:12px;
}
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
	factory.printing.portrait = false;	//横打
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

</HEAD>
<%
	eBankPrint.showPrintVoucher(out,sessionMng,lPrintType) ;
%>
<BODY>
<table width="850" border="0" cellpadding="0" cellspacing="0" align="center">
  <TR>
    <TD>
      <TABLE cellPadding=0 cellspacing="0" width="100%">
        <TR>
          <TD height=17 width=2>
            <P>&nbsp;</P></TD>
          <TD colSpan=3 height=17>
            <P>贴现申请编号：<%=DataFormat.formatString(discountInfo.getDiscountCode())%></P></TD></TR>
        <TR>
          <TD height=30 width=2>&nbsp;</TD>
          <TD colSpan=3 height=30 vAlign=top>
            <HR align=center SIZE=1 width="100%">
          </TD></TR>
        <TR>
          <TD height=30 width=2>&nbsp;</TD>
          <TD height=30 vAlign=top width=412><U>贴现票据明细表</U></TD>
          <TD height=30 vAlign=top width=413>&nbsp;</TD>
          <TD height=30 vAlign=top width=8>&nbsp;</TD></TR>
	  </TABLE>
	  <TABLE cellPadding=2 cellspacing="0" width="100%" class="table1">
              <TR align=center>
				<TD class="td-right" height=30 width="42">序列号</TD>
                <TD class="td-right" height=30>原始出票人</TD>
                <TD class="td-right" height=30>承兑方</TD>
                <TD class="td-right" height=30 width="60">承兑方所在地</TD>
                <TD class="td-right" height=30 width="90">出票日</TD>
                <TD class="td-right" height=30 width="90">到期日</TD>
                <TD class="td-right" height=30 width="84">汇票号码</TD>
                <TD class="td-right" height=30 width="120">汇票金额</TD>
                <TD class="td-right" height=30 width="59">节假日增加天数</TD>
				<TD height=30 width="85">汇票类型</TD>
              </TR>
	<%	
                if( (temp != null) && (temp.size() > 0) )
                {
					
                    for(int i=0;i< temp.size();i++)
					{
						DiscountBillInfo info = ( DiscountBillInfo ) temp.elementAt(i);                     
						lPageCount = info.getCount() / Constant.PageControl.CODE_PAGELINECOUNT;
						if ((info.getCount() %  Constant.PageControl.CODE_PAGELINECOUNT) != 0)
						{
							lPageCount ++;
						}

		
	%>
        <TR align="center">
          <TD class="td-topright" height=25 width="42">
		  <%=DataFormat.formatInt(info.getSerialNo(),3,true)%>
		  </TD>
          <TD align="center" class="td-topright">
		  <%if ((info!=null)&&(info.getUser()!=null)) {out.println(info.getUser());} else {out.println("");}%>
		  </TD>
          <TD align="center" class="td-topright">
		  <%if ((info!=null)&&(info.getBank()!=null)) {out.println(info.getBank());} else {out.println("");}%>
		  </TD>
          <TD align="center" class="td-topright" width="60">
		  <%
				if (info.getIsLocal() == Constant.YesOrNo.NO) 
				{
					out.println("非本地");
				} 
				else if (info.getIsLocal() == Constant.YesOrNo.YES) 
				{
					out.println("本地");
				}
		  %>
		  </TD>
          <TD align="center" class="td-topright" width="90">
		  <%=DataFormat.getDateString(info.getCreate())%> 
		  </TD>
          <TD align="center" class="td-topright" width="90">
		  <%=DataFormat.getDateString(info.getEnd())%>
		  </TD>
		  <TD align="center" class="td-topright" width="84">
		  <%if ((info!=null)&&(info.getCode()!=null)){out.println(info.getCode());} else {out.println("&nbsp;");}%>
		  </TD>
		  <TD align="right" class="td-topright" width="120">
		  <%if ((info!=null)&&(info.getAmount()>0)) {out.println(DataFormat.formatDisabledAmount(info.getAmount()));} else {out.println("0.00");}%>		 
		  </TD>
		  <TD align="center" class="td-topright" width="59">
		  <%if ((info!=null)&&(info.getAddDay()>0)) {out.println(info.getAddDay());} else {out.println("0");}%> 
		  </TD>
          <TD align="center" class="td-top" width="85">
		  <%if ((info!=null)&&(info.getAcceptPOTypeID()>0)) {out.println(LOANConstant.DraftType.getName(info.getAcceptPOTypeID()));} else {out.println("&nbsp;");}%>
		  </TD>
		</TR>
        <%
		           }
	            }
			   else{
			   %>
         <TR align=center>
          <TD class="td-topright" height=25>&nbsp;</TD>
          <TD class="td-topright">&nbsp;</TD>
          <TD class="td-topright">&nbsp;</TD>
          <TD class="td-topright">&nbsp;</TD>
          <TD class="td-topright">&nbsp;</TD>
          <TD class="td-topright">&nbsp;</TD>
		  <TD class="td-topright">&nbsp;</TD>
		  <TD class="td-topright">&nbsp;</TD>
		  <TD class="td-topright">&nbsp;</TD>
          <TD class="td-top">&nbsp;</TD></TR>
			   <%			   
			   }
        %>
				  
        <TR>
          <td class="td-top" width="100%" height=30 colspan="10">笔数总计：<%=lCount%>&nbsp;&nbsp;金额总计：￥<% if (dTotalAmount > 0) out.println(DataFormat.formatListAmount(dTotalAmount)); else out.println("0.00"); %>
		  </td>
		</TR>
   </TABLE>
            
</TD></TR></TABLE>

<P><BR></P>

</BODY></HTML>		
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
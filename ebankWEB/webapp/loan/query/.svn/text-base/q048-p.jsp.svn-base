<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.loan.util.*,
                com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.ebank.obsystem.bizlogic.*,
                com.iss.itreasury.ebank.obquery.bizlogic.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
    			com.iss.itreasury.loan.discount.bizlogic.*,
                com.iss.itreasury.loan.discount.dataentity.*,
				com.iss.itreasury.system.bizlogic.*,
				com.iss.itreasury.system.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>

<%
/////////////////////////////////////////////////////////////////////////////////////////
	
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
		
		int MY_PAGELINECOUNT = 1000;
		String strTmp = "";
		String strControl = "";
		String backurl = "";

		long lCount = 0;
		double dTotalAmount = 0;
					
		long lDiscountID = -1;		//贴现标示
											
		Collection temp = null;

			//贴现EJB
		DiscountLoanInfo  dli = new DiscountLoanInfo ();
		OBLoanQuery Discount=new OBLoanQuery();
		
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
		
		if (lDiscountID == -1)
		{
		%>
		<SCRIPT language="JavaScript">
			alert("贴现申请编号错误！");
			window.close();
		</SCRIPT>
		<%
		}

////////view/////////////////////////////////////////////////////////////////////////////		
		if (strControl.equals("view"))
		{
			dli = Discount.findDiscountByID(lDiscountID);
						
			temp = Discount.findDiscountBillByDiscountID(lDiscountID, MY_PAGELINECOUNT, 1, lOrderParam, lDesc);
						
       	}		

/////////////////////////////////////////////////////////////////////////////////////////

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

<BODY>
<table width="850" border="0" cellpadding="0" cellspacing="0" align="center">
  <TR>
    <TD>
      <TABLE cellPadding=0 cellspacing="0" width="100%">
        <TR>
          <TD height=17 width=2>
            <P>&nbsp;</P></TD>
          <TD colSpan=3 height=17>
            <P>贴现申请编号：<%=DataFormat.formatString(dli.getDiscountCode())%></P></TD></TR>
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
				<TD class="td-right" height=30 width="5%">序列号</TD>
                <TD class="td-right" height=30 width="10%">原始出票人</TD>
                <TD class="td-right" height=30 width="10%">承兑方</TD>
                <TD class="td-right" height=30 width="6%">承兑方所在地</TD>
                <TD class="td-right" height=30 width="7%">贴现单位直接前手</TD>
                <TD class="td-right" height=30 width="10%">出票日</TD>
                <TD class="td-right" height=30 width="10%">到期日</TD>
                <TD class="td-right" height=30 width="10%">汇票号码</TD>
                <TD class="td-right" height=30 width="15%">汇票金额</TD>
                <TD class="td-right" height=30 width="7%">节假日增加天数</TD>
				<TD height=30 width="10%">汇票类型</TD>
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
        <TR align="center">
          <TD class="td-topright" height=25>
		  <%=DataFormat.formatInt(info.getSerialNo(),3,true)%>
		  </TD>
          <TD align="center" class="td-topright">
		  <%if ((info!=null)&&(info.getUserName()!=null)) {out.println(info.getUserName());} else {out.println("");}%>
		  </TD>
          <TD align="center" class="td-topright">
		  <%if ((info!=null)&&(info.getBank()!=null)) {out.println(info.getBank());} else {out.println("");}%>
		  </TD>
          <TD align="center" class="td-topright">
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
          <TD align="center" class="td-topright">
		  <%=DataFormat.formatString(info.getFormerOwner())%>
		  </TD>
          <TD align="center" class="td-topright">
		  <%=DataFormat.getDateString(info.getCreate())%> 
		  </TD>
          <TD align="center" class="td-topright">
		  <%=DataFormat.getDateString(info.getEnd())%>
		  </TD>
		  <TD align="center" class="td-topright">
		  <%if ((info!=null)&&(info.getCode()!=null)){out.println(info.getCode());} else {out.println("");}%>
		  </TD>
		  <TD align="right" class="td-topright">
		  <%if ((info!=null)&&(info.getAmount()>0)) {out.println(DataFormat.formatDisabledAmount(info.getAmount()));} else {out.println("0.00");}%>		 
		  </TD>
		  <TD align="center" class="td-topright">
		  <%if ((info!=null)&&(info.getAddDays()>0)) {out.println(info.getAddDays());} else {out.println("0");}%> 
		  </TD>
          <TD align="center" class="td-top">
		  <%if ((info!=null)&&(info.getAcceptPOTypeID()>0)) {out.println(LOANConstant.DraftType.getName(info.getAcceptPOTypeID()));} else {out.println("&nbsp;");}%>
		  </TD>
		</TR>
<% /*************************The If and while 's End ********************************/ %>
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
		  <TD class="td-topright">&nbsp;</TD>
          <TD class="td-top">&nbsp;</TD></TR>
			   <%			   
			   }
        %>
				  
        <TR>
          <td class="td-top" width="100%" height=30 colspan="11">笔数总计：<%=lCount%>&nbsp;&nbsp;金额总计：￥<% if (dTotalAmount > 0) out.println(DataFormat.formatListAmount(dTotalAmount)); else out.println("0.00"); %>
		  </td>
		</TR>
   </TABLE>
            
</TD></TR></TABLE>

<P><BR></P>

<SCRIPT language="JavaScript">
	window.print();
</SCRIPT>

</BODY></HTML>

<%	}
	catch(IException ie)
    {
		//LOANHTML.showExceptionMessage(out,sessionMng,ie,request,response,"贴现申请", Constant.RecordStatus.VALID);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>


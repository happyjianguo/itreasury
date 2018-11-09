<%@ page contentType="text/html;charset=gbk" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"/>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"/>
<%@ page import="com.iss.itreasury.util.*,
                com.iss.itreasury.ebank.util.*,
    			com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
    			java.sql.*,
                java.rmi.RemoteException,
				java.util.*"
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
 //response.setHeader("Cache-Control","no-stored");
 //response.setHeader("Pragma","no-cache");
 //response.setDateHeader("Expires",0);
%>
<%
	/* 标题固定变量 */
	String strTitle = "[贴现凭证]";
%>	
<%!
public int IsInVector (Vector v,long lID)
{
    int i = 0;
    long lNum = -1;
	boolean flag = false;
	
	try{  	
		if (v != null && v.size() > 0)
		{
	 		while (i < v.size()) 
 	 		{
	      		Long lTmp = (Long) v.get(i);
				lNum = lTmp.longValue();
				if (lNum == lID)
				{					
					flag = true;
					break;
				}
				i++;
			}
		}
	}
	catch(Exception e) 
	{
    	System.out.println(e.toString());
    }
    
    return ((flag == true) ? i : -1);    
}
%>
<%
//////////////////////////////////////////////////////////////////////////////////////
	
	try{    
		
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

		//定义变量，获取请求参数

		String strTmp = "";
		String strControl = "";
		
		int tmpInt = 0;
		int lastPageNum = 0;
		int delNum = 0;
		
		long lBillID = -1;
		String strBillID = "";
		Vector v = null;

		long lContractID = -1;			//贴现合同
		long lLoanID = -1;				//贴现申请
		long lCredenceID = -1;

		double llv = 0;					//贴现利率
		Timestamp rq = null;			//贴现日期
		Timestamp tsEnd = null;			//贴现日期
		String strEnd = "";				//贴现日期
		int nDays = 0;					//实际贴现天数
		double dAccrual = 0;			//贴现利息
		double dRealAmount = 0;			//实付贴现金额
		long lCount = 0;				//票据总笔数
		double dTotalAccrual = 0;		//汇总贴现利息
		double dTotalRealAmount = 0;	//汇总实付贴现金额

		Collection temp = null;

		// 分页参数
		long lPageCount = 1;                   //几页
		long lPageNo = 1;                      //第几页
		long lOrderParam = 1;                  //根据什么排序
		long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序
		//为查询贴现票据明细表服务
		String BillInfo = "";
	    strTmp = (String)request.getAttribute("BillInfo");
        if( strTmp != null && strTmp.length() > 0 )
		{
		     BillInfo = strTmp;
		}

       	if (request.getAttribute("temp") != null)
       	{
           	temp = (Collection)request.getAttribute("temp");
       	}


///////control////////////////////////////////////////////////////////////////////////
		strTmp = (String)request.getAttribute("lContractID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lContractID = Long.parseLong(strTmp.trim());
		}
		strTmp = (String)request.getAttribute("lCredenceID");
        if( strTmp != null && strTmp.length() > 0 )
		{
			 lCredenceID = Long.parseLong(strTmp.trim());
		}
//排序
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

		OBHtml.showOBHomeHead(out,sessionMng,"贴现票据计息明细表",Constant.ShowMenu.YES);
%>

<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<safety:resources />
<form name="frm" method="post">

<TABLE border=0 class=top height=133 width=740>
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现票据计息明细表</B></TD></TR>  
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width="99%">
        <TBODY>    
        <TR>          
          <TD colSpan=3 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width=740>
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">                
				<TD class=ItemTitle height=20 width=43>&nbsp;</TD>
				<TD class=ItemTitle height=20 width=50><A href="javascript:go('1');">序列号</A></TD>
                <TD class=ItemTitle height=20 width=96><A href="javascript:go('8');">汇票号码</A></TD>
                <TD class=ItemTitle height=20 width=116><A href="javascript:go('9');">汇票金额</A></TD>
                <TD class=ItemTitle height=20 width=87>贴现日</TD>
                <TD class=ItemTitle height=20 width=87><A href="javascript:go('6');">到期日</A></TD>
			    <TD class=ItemTitle height=20 width=87><A href="javascript:go('7');">节假日增加天数</A></TD>
                <TD class=ItemTitle height=20 width=92>实际贴现天数</TD>
                <TD class=ItemTitle height=20 width=101>贴现利率</TD>
			    <TD class=ItemTitle height=20 width=111>贴现利息</TD>
                <TD class=ItemTitle height=20 width=111>实付贴现金额</TD></TR>

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
						if ((info.getCount() % Constant.PageControl.CODE_PAGELINECOUNT) != 0)
						{
							lPageCount ++;
						}

						lCount = info.getCount();
						dTotalAccrual = info.getTotalInterest();
						dTotalRealAmount = info.getTotalAmount();
	%>
        <TR align="center"  borderColor=#999999>
		  <TD class=ItemBody height=24 width=43>
<%if(BillInfo.equals("yes"))
{%>
		  	<INPUT name="checkbox" type="checkbox" value="<%=info.getID()%>" 
	<%	//此贴现票据被其他凭证选用
		if (info.getOBDiscountCredenceID() > 0 && info.getOBDiscountCredenceID() != lCredenceID){
			out.println("disabled");
		}
		//此贴现票据被当前凭证选用
		else if (info.getOBDiscountCredenceID() > 0 && info.getOBDiscountCredenceID() == lCredenceID)
		{
			out.println("checked"); 
		}
		//此贴现票据未被选用
		else
		{
			out.println("");
		}
	%>>
<%}else{%>
		  	<INPUT name="checkbox" type="checkbox" value="<%=info.getID()%>" <% if (IsInVector(v,info.getID()) > -1) 
		out.println("checked"); else if (info.getDiscountCredenceID() > 0||info.getOBDiscountCredenceID()>0) out.println("disabled"); %>>
<%}%>		 
		 </TD>
		  <TD class=ItemBody>
		  <%=DataFormat.formatInt(info.getSerialNo(),3,true)%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.formatString(info.getCode())%>
		  </TD>
          <TD align="center" class=ItemBody>￥
		  <%if ((info!=null)&&(info.getAmount()>0)) {out.println(DataFormat.formatDisabledAmount(info.getAmount()));} else {out.println("0.00");}%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getDiscountDate())%>
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=DataFormat.getDateString(info.getEnd())%> 
		  </TD>
          <TD align="center" class=ItemBody>
		  <%=info.getAddDay()%> 天
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=info.getDays()%> 天
		  </TD>
		  <TD align="center" class=ItemBody>
		  <%=DataFormat.formatRate(info.getDiscountRate())%> %
		  </TD>
		  <TD align="center" class=ItemBody>￥
		  <%if (info.getInterest()>0) {out.println(DataFormat.formatDisabledAmount(info.getInterest()));} else {out.println("0.00");}%>
		  </TD>
		  <TD align="center" class=ItemBody>￥
		  <%if (info.getCheckAmount()>0) {out.println(DataFormat.formatDisabledAmount(info.getCheckAmount()));} else {out.println("0.00");}%>	  
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
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar 
            height=50 width="100%">
              <TBODY>
              <TR>
                <td width="500" height="2">汇总贴现笔数：　　<%=lCount%><BR>
汇总贴现利息：　　￥ <%if (dTotalAccrual>0) {out.println(DataFormat.formatDisabledAmount(dTotalAccrual));} else {out.println("0.00");}%><BR>汇总实付贴现金额：￥ <%if (dTotalRealAmount>0) {out.println(DataFormat.formatDisabledAmount(dTotalRealAmount));} else {out.println("0.00");}%>
</td>
<TD height=2 width="428" valign=bottom></TD>
     </TR>
   </TBODY></TABLE></TD></TR></TBODY></TABLE>            
</TD></TR>
			
  <TR>
    <TD height=40 vAlign=bottom>
      <TABLE align=center border=0 height=40 width="99%">
        <TBODY>
        <TR borderColor=#ffffff>
          <TD colSpan=4 height=1>
		  <input type="button" name="chkall" value=" 全 选 " onclick="selectAll(this.form,1)">
            
			<DIV align=right>
				<!--INPUT class=button name="backstep" onClick="MM_goToURL('self','d030-v.jsp?lContractID=<%=lContractID%>&control=view');" onfocus="nextfield='submitfunction';" type="button" value=" 上一步 "-->
				<INPUT class=button name="backstep" onClick="backto(frm);" onfocus="nextfield='submitfunction';" type="button" value=" 上一步 ">
				<input type="button" name="nextstep" value=" 下一步 " class="button" onclick="confirmSave(frm);">
            </DIV></TD></TR></TBODY></TABLE></TD></TR>

</TBODY></TABLE></TD></TR></TBODY></TABLE>

<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="hidden" name="control" value="view">
<input type="hidden" name="lContractID" value="<%=lContractID%>">
<input type="hidden" name="lCredenceID" value="<%=lCredenceID%>">
<input type="hidden" name="BillInfo" value="<%=BillInfo%>">
<input type="hidden" name="nextPage" value="">

<P><BR></P>
</form>

<script language="javascript">

/*function printPage()
{	
	window.open("S117-p.jsp?lContractID=<%=lContractID%>&lPageNo=<%=lPageNo%>&lOrderParam=<%=lOrderParam%>&lDesc=<%=lDesc%>&llv=<%=llv%>&rq=<%=DataFormat.getDateString(rq)%>&control=view&isSM=<%=Constant.YesOrNo.NO%>","popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=10,top=10");	
}*/
/*
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
*/
function go(para)
{  
	if (frm.lOrderParam.value == para)
	{
		if (frm.lDesc.value == "<%=Constant.PageControl.CODE_ASCORDESC_ASC%>")
		{
			frm.lDesc.value = "<%=Constant.PageControl.CODE_ASCORDESC_DESC%>";
		}
		else
		{
	   		frm.lDesc.value = "<%=Constant.PageControl.CODE_ASCORDESC_ASC%>"; 
		}
	}
	frm.lOrderParam.value = para;
	frm.control.value = "view";
	frm.action = "../discountapply/d031-c.jsp";
	showSending();
	frm.submit();
}
/*
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
*/
function selectAll(frm,str){
		for (var i=0;i<frm.elements.length;i++){
			if(document.frm.elements[i].disabled==false){
			if (frm.elements[i].type == "checkbox" && str=="1"){
				frm.elements[i].checked = true;
			}
			}
		}
}

function countAll()
{
	frm.lPageNo.value="";
	confirmSave(frm);
}

function confirmSave(frm)
{		
	var bSelect;
	bSelect = false;
	var isCheckAll = 0;
	for (var i = 0; i < document.frm.elements.length; i++) {
			if (document.frm.elements[i].checked == true){
				isCheckAll++;
		}
	}
	//只选择了一个checkBox,则checkBox不成为数组,checkBox[i]不可用
	if(isCheckAll=="0"){
		alert("请选择");
	}else{
		
			frm.control.value="save";
			frm.action="../discountapply/d033-c.jsp";
			showSending();
			frm.submit();
			return true;
	}
}

function backto(frm)
{
	frm.control.value="view";
	//如果从修改页面来
	if(frm.BillInfo.value=="yes")
	{
		frm.BillInfo.value="";
		frm.nextPage.value="d037-v.jsp";
		frm.action="../discountapply/d033-c.jsp";
	}else
	{
		frm.action="d030-v.jsp";
	}
	showSending();
	frm.submit();
	return true;
}

<% if( (strBillID == null) || (strBillID.length() == 0) ) { %>
	selectAll(frm,"0");
<% } %>
//firstFocus(frm.txtContractCtrl);
////setSubmitFunction("confirmSave(frm)");
setFormName("frm");	 
selectAll(frm,"0");
</script>			
<%
	OBHtml.showOBHomeEnd(out);
%>
<%	}
	catch(IException ie)
    {
		//OBHtml.showExceptionMessage(out,sessionMng,ie,request,response,"贴现凭证", Constant.RecordStatus.VALID);
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"贴现凭证",1);
		ie.printStackTrace();
		out.flush();
		return; 
    }
%>
<%@ include file="/common/SignValidate.inc" %>
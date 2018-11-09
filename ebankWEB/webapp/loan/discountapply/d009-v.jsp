<%--
 页面名称 ：d009-v.jsp
 页面功能 : 新增贴现申请-贴现票据列表 显示页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>
<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 java.util.Iterator,
				 java.util.Collection,
				 java.util.*,
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
	   Log.print("*******进入页面--ebank/loan/discountapply/d009-v.jsp*******");
	
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
		String strAction = (String)request.getAttribute("txtAction");
		String frompage = (String)request.getAttribute("frompage");
		 int tmpInt = 0;
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
	     long lAddDay = 0; //节假日增加天数,
	     long lAcceptPOTypeID = -1; //票据类型,
	     String strFormerOwner = ""; //贴现单位直接前手
		
	     long lID = -1; //贴现申请id
         long lPageNo = 1;
         long lOrderParam = -1;
         long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //正序还是反序;
		 
		 String strDiscountCode = "";
         
		 long lPageCount = 0;
		 long lCount = 0;
		
		 double dTotalAmount = 0.0;//贴现统计金额
		 long lBankCount = -1;//银行承兑汇票统计张数
		 long lBizCount = -1;//商业承兑汇票统计张数
		 
		 //贴现申请统计信息
		 double dApplyDiscountAmount = 0.0;//贴现汇票总金额(申请)
		 long lBankAccepPO = -1;//银行承兑汇票张数(申请)
		 long lBizAcceptPO = -1;//商业承兑汇票张数(申请)
		 long lApplyDiscountPO = -1;//汇票总张数(申请)
		 String subtypeid="";
		 //贴现日期
		 Timestamp tsDiscountStartDate = null;	 
		 DiscountInfo discountInfo = null;
		 String type="3";
    	long loanType=Long.valueOf(type).longValue();
    	String loanTypeName=OBConstant.LoanInstrType.getName(loanType);
		long[] loanTypeid={loanType};
		 discountInfo = (DiscountInfo)request.getAttribute("resultDiscountInfo");
		 if (discountInfo!=null){
			tsDiscountStartDate =  discountInfo.getDiscountStartDate();
			subtypeid=String.valueOf(discountInfo.getSubTypeId());
		 }
		 		 
		 if(discountInfo != null)
		 {
		    dTotalAmount = discountInfo.getBillAmount();
			lBankCount = discountInfo.getBankCount();
			lBizCount = discountInfo.getBizCount();
			dApplyDiscountAmount = discountInfo.getApplyDiscountAmount();
			lBankAccepPO = discountInfo.getBankAccepPO();
			lBizAcceptPO = discountInfo.getBizAcceptPO();
			lApplyDiscountPO = discountInfo.getApplyDiscountPO();
			
			lPageCount = discountInfo.getBillCount() / Constant.PageControl.CODE_PAGELINECOUNT;
			
			if ((discountInfo.getBillCount() %  Constant.PageControl.CODE_PAGELINECOUNT) != 0)
			{
				lPageCount ++;
			}
			Log.print("\n＝＝＝＝＝总页数:"+lPageCount);
			
			lCount = discountInfo.getBillCount();
			
			Log.print("\n＝＝＝＝＝总笔数:"+lCount);
			
		 }
		 
		 
		 
		//贴现申请id  
		   String strTemp = "";
		   strTemp = (String)request.getAttribute("lID");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lID = Long.valueOf(strTemp).longValue();
		   }
		
		   //第几页
		   strTemp = (String)request.getAttribute("lPageNo");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lPageNo = Long.valueOf(strTemp).longValue();
		   }
		   
		   //排序参数
		   strTemp = (String)request.getAttribute("lOrderParam");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			 lOrderParam = Long.valueOf(strTemp).longValue();
		   }
		   
		   //正序/反序
		   strTemp = (String)request.getAttribute("lDesc");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			  lDesc = Long.valueOf(strTemp).longValue();
		   }
		   
		   //贴现编号
		   strTemp = (String)request.getAttribute("strDiscountCode");
		   if (strTemp != null && strTemp.trim().length() > 0)
		   {
			  strDiscountCode = strTemp.trim();
		   }
	     
		  Vector vctResult = (Vector)request.getAttribute("resultInfo");
		  
		  //贴现日期
		    strTemp = (String)request.getAttribute("tsDiscountStartDate");
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				tsDiscountStartDate =  DataFormat.getDateTime(strTemp);
			}

		
	  //显示文件头
	  long lTemp = OBConstant.ShowMenu.YES;
	  
	   String modifyFlag = (String)request.getAttribute("modifyFlag");
	   
	  	if (modifyFlag != null && modifyFlag.equals("1"))
		{
			lTemp = OBConstant.ShowMenu.NO;
		}
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, lTemp);
	  System.out.println(subtypeid+"^^^^^^^^^^^^^^^^^^^"+loanType);
	
%>		
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post">
<input type="hidden" name="lID" value="<%=lID%>"><!--贴现申请id-->
<input type="hidden" name="lPageCount" value="<%=lPageCount%>">
<input type="hidden" name="lPageNo" value="<%=lPageNo%>">
<input type="hidden" name="lOrderParam" value="<%=lOrderParam%>">
<input type="hidden" name="lDesc" value="<%=lDesc%>">
<input type="Hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<input type="Hidden" name="tsDiscountStartDate" value="<%=tsDiscountStartDate%>"><!--贴现日期-->
<input type="Hidden" name="subtypeid" value="<%=subtypeid%>"><!--子类型-->

<!--用于返回的参数!-->
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<TABLE border=0 class=top height=133 width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=29><B>贴现申请——新增</B></TD></TR>
  <TR>
    <TD height=195>
      <TABLE cellPadding=0 height=112 width=730>
        <TBODY>
        <TR>
          <TD colSpan=2 height=17>贴现申请编号：<%=strDiscountCode%></TD>
		   <TD colSpan=2 height=17>贷款子类名称：
		   <select class='box'><option>
<%=LOANConstant.SubLoanType.getName(discountInfo.getSubTypeId())%>
			</option></select></TD>
		  </TR>
        <TR>
          <TD colSpan=4 height=20 vAlign=top>
            <HR align=center SIZE=2 width="100%">
          </TD></TR>
        <TR>
          <TD height=20 width="50%"><U>贴现票据明细表</U></TD>
          <TD height=20 align=right width="50%" colSpan=3>
            <DIV align=right>
			<%
		if (modifyFlag == null )
		{
	%>
			  <INPUT class=button type=button name="LastStep" onClick="doGoLast();" value=" 上一步 "> 
			  <INPUT class=button type=button name="addBill"  value=" 新 增 "  onClick="DoAddBill();"> 
			  <INPUT class=button type=button name="import" onclick="openBrWindow('d016-v.jsp?lID=<%=lID%>');" value=" 导 入 ">
			  <%}%>
            </DIV>
		  </TD>
        </TR>
        <TR>
          <TD colSpan=4 height=170 vAlign=top>
            <TABLE border=0 borderColor=#999999 class=ItemList height=73 
            width="730">
              <TBODY>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
                <TD class=ItemTitle colSpan=11 height=27>
                  <DIV align=left>贴现申请单位：<%=sessionMng.m_strClientName%></DIV></TD></TR>
              <TR align=center bgColor=#cccccc borderColor=#999999 class="tableHeader">
                <TD class=ItemTitle height=20 >&nbsp;</TD>
                <TD class=ItemTitle height=20 nowrap><A href="javascript:go('1');">序列号</A></TD>
                <TD class=ItemTitle height=20 width="50"><A href="javascript:go('2');">原始出票人</A></TD>
                <TD class=ItemTitle height=20 width="50"><A href="javascript:go('3');">承兑方</A></TD>
                <TD class=ItemTitle height=20><A href="javascript:go('4');">承兑方所在地</A></TD>
                <TD class=ItemTitle height=20><A href="javascript:go('5');">出票日</A></TD>
                <TD class=ItemTitle height=20><A href="javascript:go('6');">到期日</A></TD>
                <TD class=ItemTitle height=20 width="80"><A href="javascript:go('7');">汇票号码</A></TD>
                <TD class=ItemTitle height=20 width="80"><A href="javascript:go('8');">汇票金额</A></TD>
                <TD class=ItemTitle height=20 width="80"><A href="javascript:go('9');">节假日增加天数</A></TD>
				<TD class=ItemTitle height=20><A href="javascript:go('10');">汇票类型</A></TD>
              </TR>

	<%	
			    if( (vctResult != null) && (vctResult.size() > 0) )
                {
					Log.print("\n\n============查询结果大小："+vctResult.size()+"\n\n");
                    for(int i=0; i<vctResult.size();i++)
					{
						DiscountBillInfo discountBillInfo = ( DiscountBillInfo ) vctResult.elementAt(i);                     
						
						
						
						if(discountBillInfo != null)
						{
						  lDiscountBillID = discountBillInfo.getDiscountBillID(); 
	     				  lDiscountApplyID = discountBillInfo.getDiscountApplyID();
						  Log.print("===========贴现申请id:"+lDiscountApplyID);
                 	      lDiscountCredenceID = discountBillInfo.getDiscountCredenceID(); 
	                      strUser = discountBillInfo.getUser(); 
	                      strBank = discountBillInfo.getBank(); 
	                      lSerialNo = discountBillInfo.getSerialNo(); 
	                      lIsLocal = discountBillInfo.getIsLocal(); 
	                      tsCreate = discountBillInfo.getCreate(); 
	                      tsEnd = discountBillInfo.getEnd(); 
	                      strCode = discountBillInfo.getCode(); 
	                      dAmount = discountBillInfo.getAmount(); 
	                      lAddDay = discountBillInfo.getAddDay(); 
	                      lAcceptPOTypeID = discountBillInfo.getAcceptPOTypeID(); 
	                      strFormerOwner = discountBillInfo.getFormerOwner(); 
						  
						}
	%>

        <TR align="center" borderColor=#999999>
		  <TD class=ItemBody height=24>
		  	<INPUT name="lIDs" type=checkbox value="<%=lDiscountBillID%>" onClick="check(this);">
		  </TD>
          <TD class=ItemBody>
          <a href="d013-c.jsp?lDiscountBillID=<%=lDiscountBillID%>&lID=<%=lDiscountApplyID%>&tsDiscountStartDate=<%=tsDiscountStartDate%>&strDiscountCode=<%=strDiscountCode%>&modifyFlag=<%=modifyFlag%>&subtypeid=<%=subtypeid%>">
		  <%=lSerialNo%>
		  </a>
		  </TD>
          <TD align="center" width="50" class=ItemBody>
		  <%=strUser%>&nbsp;
		  </TD>
          <TD align="center" width="50" class=ItemBody>
		  <%=strBank%>
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%
				if (lIsLocal ==Constant.YesOrNo.NO) 
				{
					out.println("非本地");
				} 
				else if (lIsLocal == Constant.YesOrNo.YES) 
				{
					out.println("本地");
				}
		  %>
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%=DataFormat.getDateString(tsCreate)%> 
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%=DataFormat.getDateString(tsEnd)%>
		  </TD>
		  <TD align="center" class=ItemBody width="80">
		  <%=strCode%>
		  </TD>
		  <TD align="center" class=ItemBody width="80">
		  <%=DataFormat.formatNumber(dAmount,2)%>		 
		  </TD>
		  <TD align="center" class=ItemBody width="80">
		  <%if (lAddDay>0) {out.println(lAddDay);} else {out.println("0");}%> 
		  </TD>
          <TD align="center" class=ItemBody nowrap>
		  <%if (lAcceptPOTypeID>0) {out.println(LOANConstant.DraftType.getName(lAcceptPOTypeID));} else {out.println("&nbsp;");}%>
		  </TD>
		</TR>
        <%
		           }
	            }
			   else{
			   %>
         <TR align=center borderColor=#999999>
          <TD class=ItemBody height=24>
			<INPUT name=checkbox2 type=checkbox value=checkbox onClick="doTag(this);">
		  </TD>
		  <TD class=ItemBody>&nbsp;</TD>
          <TD class=ItemBody width="50">&nbsp;</TD>
          <TD class=ItemBody width="50">&nbsp;</TD>
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
            <TABLE border=0 cellPadding=0 cellSpacing=3 class=SearchBar height=50 width="100%">
              <TBODY>
              <TR>                
				<td width="500" height="2">笔数总计：<%=lCount%>&nbsp;&nbsp;金额总计：￥<% if (dTotalAmount > 0) out.println(DataFormat.formatListAmount(dTotalAmount)); else out.println("0.00"); %>
				</td>
				<TD height=2 width="500" align=right>
                 P. 
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
	<BR>
	<%
		if (modifyFlag == null )
		{
	%>
	<INPUT class=button name="submitDel" onclick="confirmDel(frm);" type=button value=" 删 除 ">
	<%}%>
	<BR>
    <HR>
    <DIV align=right>
	<%
		if (modifyFlag != null && modifyFlag.equals("1"))
		{
	%>
		<INPUT class=button name="submitApply" onclick="window.close();" type=button value=" 关 闭 ">
	<%
	}
	else
	{
	%>
		<INPUT class=button name="submitApply" onclick="return confirmSave(frm);" type=button value=" 保 存 ">
    <%}%>
	</DIV>
</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
</form>

<script language="javascript">
var num =0;
var tag = 0;
function check(box)
{
  if(box.checked == true)
  {
   num++;
  }
  else
  {
    num --;
  }
 // alert(num);

}

function doTag(box)
{
  tag++;
}

function doGoLast()//上一步
{
  if(confirm("是否返回?"))
  {
	   <%if(frompage != null && frompage.equals("query")){%>
		document.location.href="<%=Env.EBANK_URL%>loan/query/q001-c.jsp?instrTypeID=3&parentID=<%=lID%>";
	 <%}else{%>
		 document.location.href ="d005-c.jsp?lID=<%=lID%>&tsDiscountStartDate=<%=tsDiscountStartDate%>";
	  <%}%>
  }
}

function DoAddBill()//新增
{
   if(confirm("是否新增？"))
   {
	    frm.action="d010-v.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
   }	 
}


function openBrWindow(theURL)
{
	window.open(theURL,"popup", "width=800,height=400,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0,screenX=0,screenY=0,left=150,top=150");
}

function key_down()
{
	if (window.event.keyCode == 13)
	{
		if (InputValid(frm.cz,1, "int", 1, 1, frm.lPageCount.value,"页数")) 
		{
			frm.lPageNo.value=frm.cz.value;
		}
		else	
		{
			return (false);
		}
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
	 frm.action="d008-c.jsp";
		showSending();
		frm.submit();
		return true;
}

function goto()
{
	var lMax=frm.lPageCount.value;
	if (InputValid(frm.cz,1, "int", 1, 1, lMax,"页数"))
	{
		frm.lPageNo.value=frm.cz.value;
	}
	else 
	{
		return (false);
	}
	
	   frm.action="d008-c.jsp";
		showSending();
		frm.submit();
		return true;
}

function gohere()
{
	    frm.action="d008-c.jsp";
		showSending();
		frm.submit();
		return true;
}

function confirmDel(frm)
{  			
	if(tag > 0)
	{
	  alert("没有可删除的票据记录！")
	  return;
	}
	
	if(num <= 0)
	{
	  alert("请选中需要删除的票据记录！")
	  return;
	
	}
	if(confirm("是否要删除选中的票据信息？"))
	{
		frm.action="d012-c.jsp";
		showSending();
		frm.submit();
		return true;
		
	}			  
}

function confirmSave(frm)
{  	
   if(<%=dApplyDiscountAmount%> != <%=dTotalAmount%>)
	{
		alert("贴现汇票总金额应与贴现申请金额相等！");
		return false;
	}
	if(<%=lBankAccepPO%> != <%=lBankCount%>)
	{
		alert("银行承兑汇票张数应与申请张数相等！");
		return false;
	}
	if(<%=lBizAcceptPO%> != <%=lBizCount%>)
	{
		alert("商业承兑汇票张数应与申请张数相等！");
		return false;
	}
	if(<%=lApplyDiscountPO%> != <%=lBankCount+lBizCount%>)
	{
		alert("汇票张数应与申请张数相等！");
		return false;
	}

	if(confirm("是否保存？"))
	{
		frm.action="d017-c.jsp";
		showSending();
		frm.submit();
	}
	
}

/*
function confirmApply(frm)
{
  confirmSave(frm);
  if(confirm("是否保存？"))
  {
	frm.action="d020-c.jsp?lID=<%=lID%>&lStatusID=<%=OBConstant.LoanInstrStatus.SUBMIT%>";
	showSending();
	frm.submit();
	return true;
  }
}
*/
firstFocus(frm.submitApply);
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


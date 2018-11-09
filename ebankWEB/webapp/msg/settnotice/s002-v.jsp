<%--
页面名称 ：v024.jsp
 页面功能 : 查询 - 贷款通知书查询 - 结果显示页面
 作    者 ：xrli
 日    期 ：2003-11-12
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.lang.Integer"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTMagnifier"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryLoanNoticeInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QLoanNotice"%>
<%@ page import="com.iss.itreasury.settlement.interest.dataentity.*"%>	
<%@ page import="com.iss.system.dao.PageLoader"%>		

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/websett/js/Control.js" ></script>
<script language="JavaScript" src="/websett/js/SettCheck.js"></script>
<script language="JavaScript" src="/websett/js/MagnifierSQL.js"></script>
<safety:resources />
<%
long lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
long lOrderField = 1;

try
{
	/* 用户登录检测 */
	if (sessionMng.isLogin() == false)
	{
		OBHtml.showCommonMessage(out, sessionMng, "贷款通知书详细情况", "",1, "Gen_E002");
		out.flush();
		return;
	}

	/* 判断用户是否有权限 */
	if (sessionMng.hasRight(request) == false)
	{
		out.println(sessionMng.hasRight(request));
		OBHtml.showCommonMessage(out, sessionMng, "贷款通知书详细情况", "",1, "Gen_E003");
		out.flush();
		return;
	}
	Timestamp tsToday = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);			


	String strContraceNo = "";             //合同号
	String strPayFormNo = ""	;          //放款单号
	String strFormYear = "";               //通知书年份
	String strFormNo = ""	;              //通知书编号    
	String strFormNum  = "";               //催收次数
	
	
	//String strFixedDetailsURL = ""; //链结条件
	//取查询条件key 
	String key = (String)request.getAttribute("_pageLoaderKey");	
	String strTemp = null;
	String strPrintPage = "";
	Object[] queryResults = null;
	queryResults = (LoanNoticeInfo[])request.getAttribute(Constant.PageControl.SearchResults);
	Log.print("queryResults :  " + queryResults);
	
	PageLoader loader= sessionMng.getPageLoader(key);
	
	//处理查询数据
	QueryLoanNoticeInfo info = (QueryLoanNoticeInfo)sessionMng.getQueryCondition(key);
	String strNoticeType = "";
	if(info.getNoticeTypeID()==SETTConstant.LoanNoticeType.LoanDunNotice)
	{
		strNoticeType = "贷款催收通知书";
		strPrintPage = "3";
	}
	else if(info.getNoticeTypeID()==SETTConstant.LoanNoticeType.LoanMatureNotice)
	{
		strNoticeType = "贷款到期通知书";
		strPrintPage = "2";
	}
	else if(info.getNoticeTypeID()==SETTConstant.LoanNoticeType.LoanInterestNotice)
	{
		strNoticeType = "应付贷款利息通知书";
		strPrintPage = "1";
	}
	
	QLoanNotice qNotice = new QLoanNotice();
	Vector noticeVec = new Vector();
	if(queryResults != null && queryResults.length>0)
	{
		for( int i=0;i<queryResults.length; i++)
		{
			LoanNoticeInfo resultInfo = (LoanNoticeInfo )queryResults[i];
			noticeVec.addElement(resultInfo);
		}
	}	
	session.setAttribute("resultList",noticeVec);

	strTemp = (String)request.getAttribute("lDesc");
	if(strTemp != null && strTemp.length()>0)
	{
		lDesc = Long.valueOf(strTemp).longValue();
	}
	if( lDesc == Constant.PageControl.CODE_ASCORDESC_ASC )
	{
		lDesc = Constant.PageControl.CODE_ASCORDESC_DESC;
	}
	else
	{
		lDesc = Constant.PageControl.CODE_ASCORDESC_ASC;
	}
	strTemp = (String)request.getAttribute("lOrderField");
	if(strTemp != null && strTemp.trim().length() > 0)
	{
		lOrderField = Long.valueOf(strTemp).longValue();
	}
	
	OBHtml.showOBHomeHead(out,sessionMng,"[贷款通知书详细情况]",Constant.YesOrNo.YES);
%>

<table width="99%" class="top" height="140">
  <tr class="tableHeader"> 
    <td class="FormTitle" width="100%"><b>贷款通知书详细情况</b></td>
  </tr>
  <tr> 
    <td height="100" width="100%" valign="bottom"> <br>
		<form name="frmV024" method="post" action="s002-v.jsp">
		<input type="hidden" name="strSuccessPageURL" value="s002-v.jsp">
		<input type="hidden" name="strFailPageURL" value="s002-v.jsp">
		<input type="hidden" name="lOrderField" value="">
		<input type="hidden" name="lDesc" value="<%= lDesc %>">
		<input type="hidden" name="strAction">
		<input name="_pageLoaderKey" type="hidden" value="<%=key%>">
		<input type="hidden" name="strPrintType">
		<input type="hidden" name="strPrintPage" value="<%=strPrintPage%>">
	<table width="99%" border="0" cellspacing="3" cellpadding="0" height="8">
	
		<TR borderColor=#ffffff> 
            <TD noWrap width="15%" height=25>通知书种类：</TD>
            <TD noWrap width="20%" height=25> 
              <INPUT name="sfffff" class=box value="<%=strNoticeType%>">              
            </TD>
			<TD noWrap width="15%" height=25>&nbsp;</TD>		
			<td noWrap width="500" height="20" colSpan=3> 		
	            <div align="right"> 
				<input type="button" name="btPrint" value="打印"  class="button" onClick="doPrintNotice();" >	
				<input type="button" name="btPrint" value="批量打印"  class="button" onClick="doPrintNotices();" >		
	            </div>
	          </td>
          </TR>
	
	</table>		  
		
      <table width="99%" border="0" align="center" height="60" class="ItemList">
        <tr class="tableHeader">	
			<TD class=ItemTitle noWrap  height=20> 
	                <DIV align=center>选择<input type="checkbox" name="allck" onclick="doBoxControl(allck,ck);"></DIV>
	              </TD> 		
			<td class="ItemTitle" noWrap height="20">
				<div align="center">合同号
				</div>
			</td>
			<td class="ItemTitle" noWrap height="20">
				<div align="center">放款单号
				</div>
			</td>
			<td class="ItemTitle" noWrap height="20">										  
		       	<div align="center">年份						
				</div>
			</td>						
			<td class="ItemTitle" noWrap height="20">
				<div align="center">编号
				</div>
			</td>
			<td class="ItemTitle" noWrap height="20">
				<div align="center">催收次数
				</div>
			</td>							
		</tr>

<%  
    if(noticeVec != null && noticeVec.size()>0)
	{
%>
<input type="hidden" name="hdnSize" value="<%= noticeVec.size()%>">
<%
		for( int i=0; i<noticeVec.size(); i++ )
		{
			LoanNoticeInfo obj = (LoanNoticeInfo )noticeVec.elementAt(i);			
			strContraceNo = "";             //合同号
	        strPayFormNo = ""	;          //放款单号
            strFormYear = "";               //通知书年份
            strFormNo = ""	;              //通知书编号    
            strFormNum  = "";               //催收次数
	
			String FormNo = obj.getFormNo();
			int j = Integer.valueOf(FormNo).intValue();
			int k= (loader.getPageLoaderInfo().getPageNo()-1)*loader.getPageLoaderInfo().getRowPerPage()+j;
			String strReturn = DataFormat.formatInt(k, 4);
			obj.setFormNo(strReturn);
			strContraceNo = DataFormat.formatString(obj.getContractNo());	
			strPayFormNo = DataFormat.formatString(obj.getPayFormNo());
			strFormYear = DataFormat.formatString(obj.getFormYear());
			strFormNo = DataFormat.formatString(obj.getFormNo());
			if(obj.getFormNum()>0)
			{
				strFormNum = DataFormat.formatNumber(obj.getFormNum());			
			}
			
%>		
       <tr>
	   		<input type="hidden" name="hdnContractID" value="<%= obj.getContractID()%>">	
		   <input type="hidden" name="hdnFormNo" value="<%= obj.getFormNo()%>">				
			<TD class=ItemBody noWrap height=20><input type="checkbox" name="ck" value="<%=obj.getID()%>" onClick="check(this,<%=i%>)"></TD>			  
              <TD class=ItemBody noWrap ><%= strContraceNo%></TD>
              <TD class=ItemBody noWrap ><%= strPayFormNo%></TD>
              <TD class=ItemBody noWrap ><%= strFormYear%></TD>
              <TD class=ItemBody noWrap ><%= strFormNo%></TD>
              <TD class=ItemBody noWrap ><%= strFormNum%></TD>              
		</tr>
          
<%  
		}
	}
	else
	{	
%>
	<tr>
			<TD class=ItemBody noWrap height=20><input type="checkbox" name="ck" value=""></TD>			  
	              <TD class=ItemBody noWrap >&nbsp;</TD>
	              <TD class=ItemBody noWrap >&nbsp;</TD>
	              <TD class=ItemBody noWrap >&nbsp;</TD>
	              <TD class=ItemBody noWrap >&nbsp;</TD>
	              <TD class=ItemBody noWrap >&nbsp;</TD>	      
		</tr>		
<%
	}
%>  

      </table>
	  </form>
    </td>
  </tr>
  <!-- 分页控件 -->
  <tr  align="center" > 
	<td width="99%" valign="bottom">
         <TABLE border="0" cellPadding=1 height=20 width="99%" class="ItemList">
         <TBODY>
             <TR class="tableHeader">
                <TD height=20 width=99% class="ItemTitle">
                    <DIV align=right> 
                       <jsp:include page="pagenavigator.jsp"  />  
                  </DIV>
				</TD>
			  </TR>
		  </TBODY>
		  </TABLE>
	 </TD>
	</TR>  
</table>

<script language="JavaScript">
var isSubmited = false;
var num = 0;
function check(box,ind)
{
  if (box.checked == false) 
  {
  	num = num + 1;
	if (frmV024.hdnSize.value > 1)
	{	
		checkSame(frmV024.hdnContractID[ind].value,frmV024.hdnFormNo[ind].value,false);
	}
  }
  else
  {
  	num = num - 1;
	if (frmV024.hdnSize.value > 1)
	{	
		checkSame(frmV024.hdnContractID[ind].value,frmV024.hdnFormNo[ind].value,true);
	}
  }
}
function checkSame(nContractID,strFormNo,isChecked)
{
	
	for (var i=0;i<frmV024.hdnSize.value;i++)
	{
		//alert (frmV024.hdnContractID[i].value);
		//alert ('nContractID='+nContractID);
		if (frmV024.hdnContractID[i].value == nContractID && frmV024.hdnFormNo[i].value == strFormNo)
		{
			//alert (isChecked);
<%			
if(info.getNoticeTypeID()!=SETTConstant.LoanNoticeType.LoanMatureNotice && info.getNoticeTypeID()!=SETTConstant.LoanNoticeType.LoanDunNotice)			
{
%>
			frmV024.ck[i].checked = isChecked;
<%
	}
%>			
		}
	}
	
}

function doPrintNotice()
{
   
   if(num == 0 && document.frmV024.allck.checked == false)
   {
   	alert("请勾选需要打印的单据！");
	return;
   }
     
   
   //判断是否已经存在选中的选项
	if(confirm("是否打印选中的<%=strNoticeType%>?"))
	{  
		document.frmV024.strPrintType.value = "1";
		<%
		if(info.getNoticeTypeID()==SETTConstant.LoanNoticeType.LoanInterestNotice)
		{
		%>
		document.frmV024.action = "s004-1-c.jsp";
		<%
		}
		else
		{
		%>
		document.frmV024.action = "s004-c.jsp";
		<%
		}
		%>
		document.frmV024.target="blank";		
		document.frmV024.submit();
	}
}


function doPrintNotices()
{
	if(confirm("是否批量打印<%=strNoticeType%>?"))
	{	
		 document.frmV024.strPrintType.value = 2;
	   <%
	   if(info.getNoticeTypeID()==SETTConstant.LoanNoticeType.LoanInterestNotice)
	   {
	   %>	   
	   document.frmV024.action = "pagecontrol.jsp";
	   document.frmV024.strAction.value='<%=Constant.PageControl.LISTALL%>';
	   document.frmV024.strSuccessPageURL.value = 's004-1-c.jsp';
	   document.frmV024.strFailPageURL.value = 's004-1-c.jsp';
	   document.frmV024.target="blank";		
	   document.frmV024.submit();
	   <%
	   }
	   else
	   {
	   %>	   
	    document.frmV024.action = "pagecontrol.jsp";
	   	document.frmV024.strAction.value='<%=Constant.PageControl.LISTALL%>';
	   	document.frmV024.strSuccessPageURL.value = 's003-c.jsp';
	   	document.frmV024.strFailPageURL.value = 's003-c.jsp';
	   	document.frmV024.target="blank";		
	   	document.frmV024.submit();
	   <%
	   }
	   %>
	}
}

function doBoxControl(controlbox, sequencebox)
{
		var isControlSelected = controlbox.checked
		if(sequencebox==undefined)return;
		if(isNaN(sequencebox.length))
		{
				sequencebox.checked = isControlSelected;
		} else {
				for(var i=0;i<sequencebox.length;i++)
				{
						sequencebox[i].checked = isControlSelected;
				}
		}
}
</script>
<!---------------------------------------------bottom----------------------------------------------->
<%  	
		OBHtml.showOBHomeEnd(out);
	}
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, "贷款通知书详细情况","",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>


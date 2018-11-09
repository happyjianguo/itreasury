<%--
 页面名称 ：a115-v.jsp
 页面功能 : 下属单位账户历史明细查询
 作    者 ：xgzhang
 日    期 ：2005-8-17
 特殊说明 ：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<!-- 引入需要的类,尽量不用.* -->
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBMagnifier"%> 
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryTransAccountDetailResultInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QTransAccountDetail"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
 
<!-- 引入此用户当前回话的Session -->
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />

<!--引入js文件-->
<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

<%
//标题变量
String strTitle = "[下属单位账户历史明细]";
try
{
	//请求检测
	/** 判断是否登录系统 **/
	if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, strTitle,1, "Gen_E002");
            return;
        }
    /** 权限检查 **/
    if (sessionMng.hasRight(request) == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, strTitle,1, "Gen_E003");
            return;
        }
   //显示文件头
    OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
	
	/**
	 * 公共参数
	 */
	long operatorId				= sessionMng.m_lUserID;				//当前操作用户ID
	long currencyId				= sessionMng.m_lCurrencyID;			//当前系统使用币种ID
	long officeId				= sessionMng.m_lOfficeID;			//办事处ID
	long lParentCorpID 			= sessionMng.m_lClientID;           //母公司ID
	/**
	* 定义业务变量
	*/
		Collection coll = null;
		Iterator it = null;
		long lCurrencyID = -1;
		String strClientCode = "";//客户编号
		long lClientID = -1;//客户ID
		String strAccountNo = "";//账号
		long lAccountID = -1;//账户ID
		long lAccountTypeID =-1;//账户类型ID
		long lAccountGroupID = -1;//账户组ID
		String strFixedDepositNo = "";//定期存款单据号
		long lSubAccountID = -1;//定期存款单据号对应的子账户ID
		long lFixedDepositID = -1;//定期存款单据号ID
		String strContractCode = "";//合同号
		long lContractID = -1;//合同ID
		String strLoanNoteNo = "";//放款单号
		long lLoanNoteID = -1;//放款单号ID
		String strDateStart = "";
		String strDateEnd = "";
		Timestamp tsStartDate = null;
		Timestamp tsEndDate = null;
		String strFirstTD = " height=\"25\" class=\"MsoNormal\" colspan=2 ";
		String strSecondTD = " height=\"25\" ";
 
    	OBAccountQueryWhere obaqw = new OBAccountQueryWhere(); //查询条件对象
    	obaqw.setCurrencyID(currencyId);
   		obaqw.setOfficeID(officeId);
   	
   		double dBalance = 0.0;//期初余额
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		long lCheckStatusID = -1; //复核状态
		long lStatusID = -1; //账户状态
		
		QueryTransAccountDetailWhereInfo qtwi = new QueryTransAccountDetailWhereInfo();
		QueryTransAccountDetailResultInfo qtri = new QueryTransAccountDetailResultInfo();
		QTransAccountDetail qobj = new QTransAccountDetail();
		String strBalance = "";
		
		if( request.getAttribute("whereInfo")!=null )
	 	{
        	qtwi = (QueryTransAccountDetailWhereInfo)request.getAttribute("whereInfo");
			lAccountID = qtwi.getAccountID();
			strDateStart = DataFormat.getDateString(qtwi.getStartDate());
			strDateEnd = DataFormat.getDateString(qtwi.getEndDate());
			tsStartDate = qtwi.getStartDate();
			tsEndDate = qtwi.getEndDate(); 
			lCurrencyID = qtwi.getCurrencyID();
			lAccountTypeID = qtwi.getAccountTypeID();
			//lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
			strFixedDepositNo = qtwi.getFixedDepositNo();
			lFixedDepositID = qtwi.getFixedDepositID();
			strContractCode = qtwi.getContractCode();
			lContractID = qtwi.getContractID();
			strLoanNoteNo = qtwi.getLoanNoteNo();
			lLoanNoteID = qtwi.getLoanNoteID();
			lSubAccountID = qtwi.getSubAccountID();
	 	}
		else
		{
			strDateStart = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			Log.print("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+strDateStart);
			strDateEnd = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);	
		}	 
 		String  sTemp = null;
		   sTemp = (String)request.getParameter("lAccountGroupID");
    	if (sTemp != null && sTemp.trim().length() > 0) {
        	lAccountGroupID = Long.parseLong(sTemp);
        }
           sTemp = (String)request.getParameter("lClientID");
    	if (sTemp != null && sTemp.trim().length() > 0) {
        	lClientID = Long.parseLong(sTemp);
        } 
		if( request.getAttribute("coll_resultInfo")!=null )
	 	{
        	coll = (Collection)request.getAttribute("coll_resultInfo");
	 	}
		
		if( request.getAttribute("balance")!=null)
		{
			strBalance = (String)request.getAttribute("balance");
			if (strBalance != null && strBalance.trim().length() > 0)
			{
				dBalance = Double.valueOf(strBalance).doubleValue();
			}
		}
%>
<!--引入信息处理页面,此页面会以弹出窗口的形式弹出已经捕捉到的异常-->
<!--jsp:include page="/ShowMessage.jsp"/-->
 
<!--form 开始-->
<form name="frmQry" method=get action="a115-c.jsp"  >
<!-- 定义页面控制参数 -->
<input type="hidden" name="strSourcePage" value="">
<input type="hidden" name="strSuccessPageURL" value="">
<input type="hidden" name="strFailPageURL" value="">
<input type="hidden" name="strAction" value="">
<input type="hidden" name="strCtrlPageURL" value="">

<!-- 定义业务逻辑参数 --> 
<input type="hidden" name="fromAccountType" value=""> 
<input type="hidden" name="lAccountID" value="-1">
<input type="hidden" name="lAccountTypeID" value="-1">   
<input type="hidden" name="lContractTypeIDs" value="-1"> 


<table width="730" border="0" cellspacing="0" cellpadding="0" class=top>
        <tr > 
          <td colspan="4" class=FormTitle>下属单位账户历史明细</td>
        </tr>
</table>

<table width="730" border="0" cellspacing="0" cellpadding="0"class=top>
        <tr class="MsoNormal"> 
          	<td colspan="5" height="1"></td>
        </tr>  
        <tr>  
        	<td width="5" class="MsoNormal"></td>
        	<td height="25" class="MsoNormal">账户类型：</td>
        	<td width="50" height="25" class="MsoNormal"><div align="right"></div></td>
        	<td height="25" class="MsoNormal"> 
            	<%
        			OBHtmlCom.showAccountGroupList(out, "lAccountGroupID","slctChange()", obaqw,lAccountGroupID);
				%>
        	</td>
        	<td width="5"></td>
        </tr>  
        
        <tr >
           <td width="5" ></td>
          	<td  class="MsoNormal" >下属单位：</td>
          	<td width="50" height="25" class="MsoNormal"><div align="right"></div></td>
        	<td  class="MsoNormal">
				<%
        			OBHtmlCom.showHisClientListControl(out, "lClientID", obaqw, lParentCorpID, lAccountGroupID, lClientID,sessionMng.m_lOfficeID,true);
				%>
           </td>
          <td width="5"></td>
        </tr>
          
        <tr>
        	<td width="5" ></td>
          <td height="25" class="MsoNormal" >下属单位账号：</td>
          <td width="60" height="25" class="MsoNormal"> <div align="right"></div></td>
          <td height="25"  class="MsoNormal">
      		<%
      		OBHtmlCom.showHisAccountListControl(
      		out, "lAccountID_T", obaqw, lAccountGroupID, lParentCorpID, lClientID, lAccountID,"onchange=\"jump();\" ");
      		Log.print("\n===========================lAccountGroupID:"+lAccountGroupID);
			Log.print("\n===========================lClientID:"+lClientID);
			Log.print("\n《《《《《《《《《《《》》》》》》》》》》》》》lAccountID:"+lAccountID); 
			%>
          </td>
          <td width="5" height="25"></td>
        </tr>
        
        <tr id="AccountIDLine" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr id="ContractCode" class="MsoNormal"> 
          <td width="5" height="25" class="MsoNormal"></td>
<%
	//合同号放大镜
	String strClientCtrl = "lClientID";
	String[] strNextControlsContract = { "strLoanNoteNoCtrl" };

	OBMagnifier.createContractCtrl1(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frmQry","strContractCode","合同号"
		,sessionMng.m_lClientID,lContractID,strContractCode,-1,-1,
		"lClientID",strFirstTD,strSecondTD,strNextControlsContract);
%>
          <td width="5" class="MsoNormal"></td>
        </tr>
		<tr id="ContractCodeLine" height="25" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr id="LoanNoteNo" height="25" class="MsoNormal"> 
          <td width="5" height="25" class="MsoNormal"></td>
<%
	//放款通知单放大镜
	String strTitlePayForm = "放款通知单";
	String strContractCtrlPayForm = "strContractCode";
	String[] strNextControlsPayForm = { "tsStart" };

	OBMagnifier.createPayFormNOCtrl(out,sessionMng.m_lClientID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frmQry","strLoanNoteNo","放款通知单",
		lContractID,lLoanNoteID,strLoanNoteNo,0,0,
		"strContractCode",strFirstTD,strSecondTD,strNextControlsPayForm,"","","","");
	
%>
          <td width="5" class="MsoNormal"></td>
        </tr>
		<tr id="LoanNoteNoLine" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr id="FixedDepositNo" class="MsoNormal"> 
          <td width="5" height="0" class="MsoNormal"></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
		<tr  id="FixedDepositNoLine" class="MsoNormal"> 
          <td colspan="5" height="0" class="MsoNormal"></td>
        </tr>

		<tr id="NotifyDepositNo" class="MsoNormal"> 
          <td width="5" height="0" class="MsoNormal"></td>
          <td width="5" class="MsoNormal"></td>
        </tr>

        <tr id="NotifyDepositNoLine" > 
          <td colspan="5" height="0"></td>
        </tr>
        
        <tr> 
          <td width="5"></td>
          <td class="MsoNormal">执行日期：</td>
          <td width="60" height="25" class="MsoNormal"> <div align="right">由</div></td>
          <td  class="MsoNormal"> 
          <fs_c:calendar 
			          	    name="tsStart"
				          	value="" 
				          	size="20"/>
				<script>
	          		$('#tsStart').val('<%=strDateStart%>');
	          	</script>
			至
			<fs_c:calendar 
          	    name="tsEnd"
	          	value="" 
	          	size="20"/>
	         <script>
	          		$('#tsEnd').val('<%=strDateEnd%>');
	         </script>
		  </td>
          <td width="5"></td>
        </tr>
</table>
<br>
<table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="471"></td>
          <td width="49"> </td>
          <td width="50"> 
            <div align="right">
				<input class=button name=add type=button value=" 查  找 " onClick="javascript:Queryform(document.frmQry)" onKeyDown="javascript:Queryform(document.frmQry)">	
			</div>
          </td>
        </tr>
</table>
<br>
<%
	if (coll!=null && coll.size()<2)
	{
%>
	  <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr class="tableHeader">          
          <td height="22" class=FormTitle colSpan=2><font size="3" color="#FFFFFF"><b><%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%> 
            </b></font></td>
        </tr>
      </table>


	  
	<table width="732" border="0"  height="20" class="ItemList">
        <tr align="center" class="tableHeader">
          <td width="10%" height="20" class="ItemTitle">日期</td>
          <td width="10%" height="20" class="ItemTitle">交易编号</td>
<%
	
%>		  
          <td width="15%" height="20" class="ItemTitle">摘要</td>
<%
		if(  SETTConstant.AccountGroupType.CURRENT != lAccountGroupID
				&& SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
		{
%>    
          <td width="10%" height="20" class="ItemTitle">业务类型</td>
<%
			if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
			||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID)
			{
%>	 
		  <td width="10%" height="20" class="ItemTitle">单据号</td>
<%
			}
			if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
				||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
				||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
				||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID)
			{
%>
		 <td width="10%" height="20" class="ItemTitle">合同号</td>
<%
			}
		}
%>      
      <td width="10%" height="20" class="ItemTitle">对方账号</td>
<%
		if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
		{
%>          
      <td width="10%" height="20" class="ItemTitle">对方账户名称</td>
<%
		}
%>
          
      <td width="10%" height="20" class="ItemTitle">付款金额</td>
          
      <td width="10%" height="20" class="ItemTitle">收款金额</td>
          <td width="15%" height="20" class="ItemTitle">余额</td>
        </tr>
	 <tr bordercolor="#999999" align="center" class="ItemBody">
		<td  height="23"><%=DataFormat.formatDate(tsStartDate)%></td>
		<td  height="23"><b>期初余额</b></td>
<%
		if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
		<td  height="23"></td>
<%
		}
%>
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23"></td>
		<td height="23"></td>
		<td  height="23" align="right"><b><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></b></td>
	 </tr>
	 <tr bordercolor="#999999" align="center" class="ItemBody">
		<td  height="23"></td>
		<td  height="23"></td>
		<td height="23"><b>本月合计</b></td>
		<td  height="23"></td>
<%
		if( SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
		<td  height="23"></td>
<%
		}
%>
		<td  height="23"></td>
		<td  height="23" align="right"><B>0.00</B></td>
		<td  height="23" align="right"><B>0.00</B></td>
		<td  height="23" align="right"><B><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></B></td>
	 </tr>
	 <tr bordercolor="#999999" align="center" class="ItemBody">
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23"><b>本年合计</b></td>
		<td  height="23"></td>
<%
		if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
		<td  height="23"></td>
<%
		}
%>
		<td  height="23"></td>
		<td  height="23" align="right"><B>0.00</B></td>
		<td  height="23" align="right"><B>0.00</B></td>
		<td  height="23" align="right"><B><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></B></td>
	 </tr>
	</table>
	<br>
<%
	}
	else if(coll!=null && coll.size()>1)
	{
%>	  
	  <table width="730" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#C8D7EC" class="tableHeader"> 
          <!--td bgcolor="#0C3869" width="5" valign="top" align="left" height="22" class="FormTitle"><img src="/webob/graphics/blue_top_left.gif" width="3" height="3"></td-->
          <td height="22" bgcolor="#0C3869" class="FormTitle" colspan="4"><font size="3" color="#FFFFFF" ><b><%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%> 
            </b></font></td>
          <!--td class="FormTitle" width="5" height="22" bgcolor="#0C3869"></td-->
        </tr>
      </table>


	  
	<table width="732" border="0" height="60" class="ItemList">
        <tr align="center" class="tableHeader">
          <td width="10%" height="20" class="ItemTitle">日期</td>
          <td width="10%" height="20" class="ItemTitle">交易编号</td>
          <td width="15%" height="20" class="ItemTitle">摘要</td>
<%
		if(  SETTConstant.AccountGroupType.CURRENT != lAccountGroupID
				&& SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
		{
%>    
          <td width="10%" height="20" class="ItemTitle">业务类型</td>
<%
			if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
			||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID)
			{
%>	 
		  <td width="10%" height="20" class="ItemTitle">单据号</td>
<%
			}
			if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
				||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
				||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
				||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID)
			{
%>
		 <td width="10%" height="20" class="ItemTitle">合同号</td>
<%
			}
		}
%>             
      <td width="10%" height="20" class="ItemTitle">对方账号</td>
<%
		if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
		{
%>              
      <td width="10%" height="20" class="ItemTitle">对方账户名称</td>
<%
		}
%>          
      <td width="10%" height="20" class="ItemTitle">付款金额</td>
          
      <td width="10%" height="20" class="ItemTitle">收款金额</td>
          <td width="15%" height="20" class="ItemTitle">余额</td>
        </tr>
		<tr bordercolor="#999999" align="center" class="ItemBody">
		<td  height="23"><%=DataFormat.formatDate(tsStartDate)%></td>
		<td  height="23"><b>期初余额</b></td>
		<td  height="23"></td>
<%
		if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
		<td  height="23"></td>
<%
		}
%>
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23" align="right"><b><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></b></td>
<%
Timestamp tsTempDate = null;
Timestamp tsYearLoopDate = tsStartDate;
Log.print("tsStartDate="+tsStartDate);
Log.print("tsEndDate="+tsEndDate);
Log.print("tsEndDate.before(tsStartDate)="+tsEndDate.before(tsStartDate));

	for(tsYearLoopDate = tsStartDate ; (tsYearLoopDate.before(tsEndDate)||tsYearLoopDate.equals(tsEndDate)) ;  )//tsTempDate = DataFormat.getNextMonth(tsTempDate,12)
	{
		Log.print("in year loop");
		Log.print("-----------date="+tsYearLoopDate.toString());
		dYearPayBalance = 0.0;
		dYearReceiveBalance = 0.0;
		int nYears = Integer.valueOf(DataFormat.getYearString(tsYearLoopDate)).intValue();
		Log.print("-----------year="+nYears);
		for(tsTempDate = tsYearLoopDate ;Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == nYears && ((DataFormat.getMonthString(tsTempDate).equals(DataFormat.getMonthString(tsEndDate))||tsTempDate.before(tsEndDate)||DataFormat.formatDate(tsTempDate).equals(DataFormat.formatDate(tsEndDate)))) ; tsTempDate = DataFormat.getNextMonth(tsTempDate,1) )
		{//将月份从开始日期开始循环 以取得没有交易的月的合计
			Log.print("in month loop");
			dMonthPayBalance = 0.0;
			dMonthReceiveBalance = 0.0;
			//Collection coll = (Collection)request.getAttribute("searchResults");
			//Iterator it = null;
			if(coll != null)
			{
				it = coll.iterator();
			}
			if(it != null && it.hasNext())
			{
				while(it.hasNext())
				{
					qtri = (QueryTransAccountDetailResultInfo)it.next();
					Log.print("---------end year="+coll.size());
					if(Long.valueOf(DataFormat.getMonthString(tsTempDate)).longValue() != Long.valueOf(DataFormat.getMonthString(qtri.getExecuteDate())).longValue())
					{//如果不是本月的则不显示在本月范围之内
						continue;
					}
					if(Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() != Long.valueOf(DataFormat.getYearString(qtri.getExecuteDate())).longValue())
					{//如果不是本年的则不显示在本年范围之内
						continue;
					}
					Log.print("-----------开始显示当前月的交易");
					if(qtri.getTransTypeID() > -1000)//如果不是日合计的 金额
					{
						dMonthPayBalance = dMonthPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
						dMonthReceiveBalance = dMonthReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
						dYearPayBalance = dYearPayBalance + DataFormat.formatDouble(qtri.getPayAmount(),2);
						dYearReceiveBalance = dYearReceiveBalance + DataFormat.formatDouble(qtri.getReceiveAmount(),2);
					}
%>
		          <tr bordercolor="#999999" align="center" class="ItemBody">
		          <td  height="23"><%= qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;"%></td>
				  
				  <%if(SETTConstant.TransactionType.isInterestTransaction(qtri.getTransTypeID()))
				  	{%>
			          	<td  height="23"><%= qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;"%></td>
					<%}else{%>
			          	<td  height="23"><a href="javascript:doFindTransDetail(<%=qtri.getAccountID()%>,<%=qtri.getTransTypeID()%>,'<%=qtri.getTransNo()%>',<%=qtri.getGroupID()%>);"><%= qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;"%></a></td>
					<%}%>
				  <td  height="23"><%= qtri.getAbstract() != null ? qtri.getAbstract() : "&nbsp;"%></td>
<%
				if( SETTConstant.AccountGroupType.CURRENT != lAccountGroupID
					&& SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
				{
%>    
         		 	<td  height="23"><%=SETTConstant.TransactionType.getName(qtri.getTransTypeID())%></td>
<%
					if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
					||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID)
					{
%>	 
					<td  height="23" ><%= qtri.getDepositNo() != null ? qtri.getDepositNo() : "&nbsp;"%></td>
<%
					}
					if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
					||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
					||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
					||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID)
					{
%>
					<td  height="23" ><%= qtri.getContractCode() != null ? qtri.getContractCode() : "&nbsp;"%></td>
<%
					}
				}
%>             
		          <td  height="23"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNoByID(qtri.getOppAccountID()): qtri.getOppAccountNo()%></td> 
<%
				if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
				{
%>      		    
				  <td  height="23"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNameByID(qtri.getOppAccountID()) : qtri.getOppAccountName()%></td> 
<%
				 }			
				  if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
				    {%>
			          <td  height="23" align="right"><B><%= qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00"%></B></td>
			          <td  height="23" align="right"><B><%= qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00"%></B></td>
			          <td  height="23" align="right"><B><%= DataFormat.formatDisabledAmount(qtri.getBalance())%></B></td>
				  <%}else{%>
			          <td  height="23" align="right"><%= qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "&nbsp;"%></td>
			          <td  height="23" align="right"><%= qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "&nbsp;"%></td>
			          <td  height="23" align="right"><%= DataFormat.formatDisabledAmount(qtri.getBalance())%></td>
				  <%}%>
<%
					dBalance = qtri.getBalance();
				}
			}
%>
			<tr bordercolor="#999999" align="center" class="ItemBody">
			<td  height="23"><%=DataFormat.getLastDateString(tsTempDate)%></td>
			<td  height="23"></td>
			<td  height="23"><b>本月合计</b></td>
<%
		if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
			<td  height="23"></td>
<%
		}
%>
			<td  height="23"></td>
			<td  height="23"></td>
			<td  height="23" align="right"><B><%= dMonthPayBalance != 0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00"%></B></td>
			<td  height="23" align="right"><B><%= dMonthReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00"%></B></td>
			<td  height="23" align="right"><B><%= dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></B></td>

<%
		}//end month
%>
			<tr bordercolor="#999999" align="center" class="ItemBody">
			<td  height="23"><b><%=DataFormat.getYearString(DataFormat.getPreviousMonth(tsTempDate,1))%></b></td>
			<td  height="23"></td>
			<td  height="23"><b>本年合计</b></td>
			<td  height="23"></td>
<%
		if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
			<td  height="23"></td>
<%
		}
%>
			<td  height="23"></td>
			<td  height="23" align="right"><B><%= dYearPayBalance != 0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00"%></B></td>
			<td  height="23" align="right"><B><%= dYearReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00"%></B></td>
			<td  height="23" align="right"><B><%= dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></B></td>
<%
		tsYearLoopDate = DataFormat.getDateTime(nYears+1,1,1,0,0,0);
		
	}//end year
%>
        </tr>
      </table>
	  <br>
<%
	}//end coll!=null
	if(coll!=null)
	{
%>	          
      <table width="730" border="0" cellspacing="0" cellpadding="0">
		<tr><td>
      <div align="left"><br>
        <span class="graytext">查询日期：<%=DataFormat.getDateString()%> <!--查询时间：14：20--></span><br>
      </div></td></tr>
        </table>
        <table width="730" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="490" height="17"> 
              <div align="left"></div>
            </td>
          <td width="46"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
		   <div align="right">
		  	<input class=button name=add type=button value=" 下载查找结果 " onClick="javascript:downloadresult();" onKeyDown="javascript:downloadresult();"> </div>         
          </td>
			 <td width="9" height="17"></td>
            <td width="60" height="17"> 
            <div align="right">
			<input class=button name=add type=button value=" 打  印 " onClick="javascript:printme();" onKeyDown="javascript:printme();">  </div> 
            </td>
          </tr>
        </table>
<%
	}//end coll!=null
%>	 
      
 <input type="hidden" name="strTransNo" value="-1">  
</form>
<SCRIPT language=JavaScript>
var sAccount_t = frmQry.lAccountID_T.value;
var lAccountID = sAccount_t.substring( 0, sAccount_t.indexOf(';') );
var lAccountGroupID = parseInt( sAccount_t.substring( sAccount_t.indexOf(';')+1 ) ); 
var lAccountTypeID = sAccount_t.substring( sAccount_t.indexOf('@')+1 );
var lClientID = sAccount_t.substring( sAccount_t.indexOf('$')+1 );
frmQry.lAccountID.value = lAccountID;
//frmQry.lAccountTypeID.value = lAccountTypeID;
//frmQry.lAccountGroupID.value = lAccountGroupID;
//frmQry.lClientID.value = lClientID;

 
 jump();
function slctChange() {
	 
	    document.frmQry.fromAccountType.value = "YES";
	    //frmQry.lAccountID.value = lAccountID;
	    showSending();
	   	frmQry.strSuccessPageURL.value="/accountinfo/a115-v.jsp";	//定义操作成功后跳往的页面
		frmQry.strFailPageURL.value="/accountinfo/a115-v.jsp";		//定义失败后跳往的页面
		document.frmQry.submit();
	}
 
function jump()
{
	var sAccount_t = frmQry.lAccountID_T.value;
	var lAccountID = sAccount_t.substring( 0, sAccount_t.indexOf(';') );
	var lAccountGroupID = parseInt( sAccount_t.substring( sAccount_t.indexOf(';')+1 ) ); 
	var lAccountTypeID = sAccount_t.substring( sAccount_t.indexOf('@')+1 );
	var lClientID = sAccount_t.substring( sAccount_t.indexOf('$')+1 );
	
	frmQry.lAccountID.value = lAccountID;
	//frmQry.lAccountTypeID.value = lAccountTypeID;
	//frmQry.lAccountGroupID.value = lAccountGroupID;
	//frmQry.lClientID.value = lClientID;
	
	ContractCode.style.display = "none";
	ContractCodeLine.style.display = "none";
	LoanNoteNo.style.display = "none";
	LoanNoteNoLine.style.display = "none";
	FixedDepositNo.style.display = "none";
	FixedDepositNoLine.style.display = "none";
	NotifyDepositNo.style.display = "none";
	NotifyDepositNoLine.style.display = "none";
	
	if(<%=SETTConstant.AccountGroupType.TRUST%> == lAccountGroupID
		||<%=SETTConstant.AccountGroupType.CONSIGN%> == lAccountGroupID
		||<%=SETTConstant.AccountGroupType.DISCOUNT%> == lAccountGroupID
		||<%=SETTConstant.AccountGroupType.OTHERLOAN%> == lAccountGroupID)
	{
		ContractCode.style.display = "";
		ContractCodeLine.style.display = "";
		LoanNoteNo.style.display = "";
		LoanNoteNoLine.style.display = "";
		changeAccountType(lAccountGroupID);
	}
	if( lAccountGroupID==<%=SETTConstant.AccountGroupType.FIXED%>
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.NOTIFY%>)
	{
		if(lAccountGroupID==<%=SETTConstant.AccountGroupType.FIXED%>)
		{
			FixedDepositNo.style.display = "";
			FixedDepositNoLine.style.display = "";
		}
		if(lAccountGroupID==<%=SETTConstant.AccountGroupType.NOTIFY%>)
		{
			NotifyDepositNo.style.display = "";
			NotifyDepositNoLine.style.display = "";
		}
	}	
}

function changeAccountType(lAccountGroupID)
{
	var lContractTypeIDs = "" ;
	if (lAccountGroupID == <%=SETTConstant.AccountGroupType.TRUST %>)
	{
		lContractTypeIDs = "<%=LOANConstant.LoanType.ZY%>,<%=LOANConstant.LoanType.ZGXE%>" ;
	}
	else if (lAccountGroupID == <%=SETTConstant.AccountGroupType.CONSIGN%> )
	{
		lContractTypeIDs = "<%=LOANConstant.LoanType.WT%>";
	}
	//贴现 : TX
	else if (lAccountGroupID == <%=SETTConstant.AccountGroupType.DISCOUNT%> )
	{
		lContractTypeIDs = "<%=LOANConstant.LoanType.TX %>";
	}
	frmQry.lContractTypeIDs.value = lContractTypeIDs;
}

function Queryform(form)
{ 
  document.frmQry.fromAccountType.value = "NO ";
	var strDateStart = "";
	var strDateEnd = "";
	if (checkDate(form.tsStart,1,"执行日期由") == false)
		return false; 
	if (checkDate(form.tsEnd,1,"执行日期至") == false)
		return false; 
	if (!CompareDateString(form.tsStart.value,form.tsEnd.value))
	{
		alert("执行日期至不能早于执行日期由");
		return false; 
	}
	if(form.lAccountID_T.value == null || form.lAccountID_T.value == "-1")
	{
		alert("请选择下属单位账号");
		return false;
	}
	strDateStart = form.tsStart.value;
	strDateEnd = form.tsEnd.value;
	var strTemp = frmQry.lAccountID_T.value;
	var lAccountID = strTemp.substring( 0, strTemp.indexOf(';') );
	var lAccountGroupID = parseInt( strTemp.substring( strTemp.indexOf(';')+1 ) ); 
	var lAccountTypeID = strTemp.substring( strTemp.indexOf('@')+1 );
	var lClientID = parseInt( strTemp.substring( strTemp.indexOf('$')+1 ) );
	
	frmQry.lAccountID.value = lAccountID;
	//frmQry.lAccountTypeID.value = lAccountTypeID;
	frmQry.lAccountGroupID.value = lAccountGroupID;
	frmQry.lClientID.value = lClientID;

	showSending();
	form.strSuccessPageURL.value="/accountinfo/a115-v.jsp";	//定义操作成功后跳往的页面
	form.strFailPageURL.value="/accountinfo/a115-v.jsp";		//定义失败后跳往的页面
	form.submit();
}

function downloadresult()
{
	frmQry.action="Khdzd_d.jsp";
	frmQry.target = "NewWindow_S";
	frmQry.submit();
	frmQry.target = "";
}

function doFindTransDetail(lAccountID,lTransTypeID,strTransNo,lGroupID)
{
	if(lTransTypeID == <%=SETTConstant.TransactionType.UPGATHERING%>)
	{
		window.open("../accountinfo/v726-d.jsp?&TransNo="+strTransNo+"&SerialNo="+lGroupID,'','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');
	
	}else{
		window.open("../accountinfo/querycontrol.jsp?lAccountID="+lAccountID+"&TransactionTypeID="+lTransTypeID+"&TransNo="+strTransNo+"&SerialNo="+lGroupID);
	}
}

function printme()
{
	frmQry.action="Khdzd.jsp";
	frmQry.target = "NewWindow_S";
	frmQry.submit();
	frmQry.target = "";
}
</script>
<%	
}
//异常处理
catch(Exception exp)
{
	Log.print(exp.getMessage());
}
OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>

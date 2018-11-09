<%--
/*
 * 程序名称：LiShi.jsp
 * 功能说明：账户历史明细查询页面
 * 作　　者：刘琰
 * 完成日期：2003年11月25日
 */
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs"%>
<%@ page import="com.iss.itreasury.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.settlement.query.queryobj.*,
				 com.iss.itreasury.settlement.query.paraminfo.*,
				 com.iss.itreasury.settlement.query.resultinfo.*,
				 java.rmi.RemoteException,
				 java.sql.*,com.iss.itreasury.loan.util.LOANConstant,
				 java.util.*,
				 com.iss.sysframe.pager.dataentity.FlexiGridInfo"  
%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<%!
	/* 标题固定变量 */
	String strTitle = "";
%>
<%

	
	try
	{
		//分页信息
		FlexiGridInfo flexiGridInfo = new FlexiGridInfo();
		
		// 用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");;
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
		//显示文件头
		OBHtml.showOBHomeHead(out, sessionMng, strTitle, 1);
		
		//定义变量
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
		String strSecondTD = " width=\"590\" height=\"25\" ";
		
		double dBalance = 0.0;//期初余额
		double dMonthPayBalance = 0.0;
		double dYearPayBalance = 0.0;
		double dMonthReceiveBalance = 0.0;
		double dYearReceiveBalance = 0.0;
		long lCheckStatusID = -1; //复核状态
		long lStatusID = -1; //账户状态
		long strAccountType=-1;
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
			lAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(lAccountTypeID);
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
			strDateEnd = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
			lAccountGroupID = SETTConstant.AccountGroupType.CURRENT;
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
		String tableDivFlag = "";
		if(request.getAttribute("tableDivFlag")!=null){
			tableDivFlag = (String)request.getAttribute("tableDivFlag");
		}

%>

<script language="JavaScript">

/* 详细信息查看处理函数 */
    function detailme(strTransNo,lID,lPutAccountID,lGetAccountID)
    {
    /*
        frmQry.strTransNo.value = strTransNo;
		frmQry.action = "ShowTrans/S330.jsp";
		frmQry.target = "_blank";
        frmQry.submit();
		frmQry.target = "";
    */
    //alert(lPutAccountID);
        window.open("ShowTrans/S330.jsp?strTransNo=" + strTransNo + "&lID=" + lID + "&lPutAccountID=" + lPutAccountID + "&lGetAccountID=" + lGetAccountID);
    }
</script>  
<form name='frmQry' method='get' action='queryLiShi.jsp' >
    
<table cellpadding="0" cellspacing="0" class="title_top" width="98%">
<tr>
    <td height="24">
	    <table cellspacing="0" cellpadding="0" class=title_Top1>
			<TR>
		       <td class=title><span class="txt_til2">账户历史明细</span></td>
		       <td class=title_right><a class=img_title></td>
			</TR>
		</TABLE>
		<br/>
      <table width=100% cellpadding="0" cellspacing="1" class=normal id="table1" >
        <tr> 
          <td colspan="5" height="5"></td>
        </tr>
        <tr id="AccountID" cellPadding=0> 
          <td width="5" height="25"></td>
          <td height="25" width="10%">账号：</td>
          <td width="10" height="25" > 
            <div align="right"></div>
          </td>
          <td height="25"> 
		  <input type="hidden" name="lAccountID" value="-1">
		  <input type="hidden" name="lAccountTypeID" value="-1"> 
		  <input type="hidden" name="lAccountGroupID" value="-1"> 
		  <input type="hidden" name="lClientID" value="<%=sessionMng.m_lClientID%>"> 
		  <input type="hidden" name="lOfficeID" value="<%=sessionMng.m_lOfficeID%>"> 
	      <input type="hidden" name="lCurrencyID" value="<%=sessionMng.m_lCurrencyID%>"> 
	      <input type="hidden" name="sClientCode" value="<%=sessionMng.m_strClientCode%>"> 
		  <input type="hidden" name="lContractTypeIDs" value="-1"> 
		  <input type="hidden" name="lStatusIDs" value="1,2,3,4,5,6,7,8,9,10,11,12"> 
		  <input type="hidden" name="lContractTypeIDs" value="1,2,4,5"> 
		  <input type="hidden" name="lStatusIDArray" value="3,4"> 
<%
			Log.print("账户控件");
			OBHtmlCom.showAccountListControl( out, "lAccountID_T", sessionMng.m_lClientID,sessionMng.m_lCurrencyID, lAccountID,sessionMng.m_lUserID,"onchange=\"jump();\" ");
%>
		  </td>
          <td width="5"></td>
        </tr>
		<tr  id="AccountIDLine" > 
          <td colspan="5" height="1" ></td>
        </tr>
		<tr id="ContractCode" > 
          	<td width="5" height="25" ></td>
          	<td width="145" height="25" align="left"><font color='#FF0000'>* </font>合同号：</td>
			<td></td>
			<td width="430">
				<fs:dic id="strContractCodeCtrl" size="22" form="frmQry" title="合同号" sqlFunction="getContractSQL"  sqlParams='frmQry.lOfficeID.value,frmQry.lCurrencyID.value,frmQry.lContractTypeIDs.value,frmQry.lStatusIDs.value,frmQry.strContractCodeCtrl.value,frmQry.lClientID.value' value="<%=qtwi.getContractCode() %>" nextFocus="strLoanNoteNoCtrl" width="500">
					<fs:columns> 
						<fs:column display="合同号"  name="ContractCode" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
						<fs:column display="客户名称" name="ClientName" type="<%=PagerTypeConstant.STRING %>" width="200" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="strContractCodeCtrl" name="ContractCode" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="strContractCode" name="ContractID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="strContractCodeClientID" name="ClientID" type="<%=PagerTypeConstant.AMOUNT_2 %>" elType="text" />
					</fs:pageElements>							
				</fs:dic> 
				<input type="hidden" name="strContractCode" value=""> 
			</td>
<%
	
	/*
	//合同号放大镜
	String strClientCtrl = "lClientID";
	String[] strNextControlsContract = { "strLoanNoteNoCtrl" };

	OBMagnifier.createContractCtrl1(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frmQry","strContractCode","合同号"
		,sessionMng.m_lClientID,lContractID,strContractCode,-1,-1,
		"lClientID",strFirstTD,strSecondTD,strNextControlsContract);
	*/
%>
          <td width="5" class="MsoNormal"></td>
        </tr>
		<tr bgcolor="#FFFFFF" id="ContractCodeLine" height="25" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr id="LoanNoteNo" height="25" class="MsoNormal"> 
          	<td width="5" height="25" class="MsoNormal"></td>
          	<td width="145" height="25" align="left"><font color='#FF0000'>* </font>放款通知单：</td>
			<td></td>
			<td width="430">
				<fs:dic id="strLoanNoteNoCtrl" size="22" form="frmQry" title="放款通知单" sqlFunction="getPayFormNOSQL"  sqlParams='frmQry.lClientID.value,frmQry.lOfficeID.value,frmQry.lCurrencyID.value,frmQry.strContractCode.value,frmQry.lContractTypeIDs.value,frmQry.lStatusIDArray.value,frmQry.strLoanNoteNoCtrl.value' value="<%=qtwi.getLoanNoteNo() %>" nextFocus="tsStart" width="500">
					<fs:columns> 
						<fs:column display="放款通知单号"  name="PayFormCode" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
						<fs:column display="起始日期" name="StartDate" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
						<fs:column display="放款日期" name="PayDate" type="<%=PagerTypeConstant.STRING %>" width="150" sort="true" align="center"/>
					</fs:columns>
					<fs:pageElements>
						<fs:pageElement elName="strLoanNoteNo" name="PayFormID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="strLoanNoteNoContractID" name="ContractID" type="<%=PagerTypeConstant.STRING %>" elType="text" />
						<fs:pageElement elName="strLoanNoteNoCtrl" name="PayFormCode" type="<%=PagerTypeConstant.STRING %>" elType="text" />
					</fs:pageElements>
					<input type="hidden" name="strLoanNoteNo" value=""> 							
				</fs:dic> 
			</td>
<%
	/*
	//放款通知单放大镜
	String strTitlePayForm = "放款通知单";
	String strContractCtrlPayForm = "strContractCode";
	String[] strNextControlsPayForm = { "tsStart" };

	OBMagnifier.createPayFormNOCtrl(out,sessionMng.m_lClientID,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frmQry","strLoanNoteNo","放款通知单",
		lContractID,lLoanNoteID,strLoanNoteNo,0,0,
		"strContractCode",strFirstTD,strSecondTD,strNextControlsPayForm,"","","","");
	*/
%>
          <td width="5" class="MsoNormal"></td>
        </tr>
		<tr bgcolor="#FFFFFF" id="LoanNoteNoLine" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>
		<tr id="FixedDepositNo" class="MsoNormal"> 
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="5" class="MsoNormal"></td>
        </tr>
		<tr  id="FixedDepositNoLine" class="MsoNormal"> 
          <td colspan="5" height="1" class="MsoNormal"></td>
        </tr>

		<tr id="NotifyDepositNo" class="MsoNormal"> 
          <td width="5" height="25" class="MsoNormal"></td>
          <td width="5" class="MsoNormal"></td>
        </tr>

        <tr id="NotifyDepositNoLine" cellPadding=0> 
          <td colspan="5" height="1"></td>
        </tr>
        <tr cellPadding=0> 
          <td width="5" height="25"></td>
          <td height="25" width="10%">执行日期：</td>
          <td width="10" height="25" > 
            <div align="right">由</div>
          </td>
          <td  height="25" class="MsoNormal"> 
          	<fs_c:calendar 
          	    name="tsStart"
	          	value="" 
	     		properties="nextfield =''" 
	          	size="20"/>
	    <script>
	          $('#tsStart').val('<%=strDateStart%>');
	    </script>
	    </td>
	    <td align="right">至</td>
		<td>
			<fs_c:calendar name="tsEnd" value="" properties="nextfield =''" size="20"/>
			<script>$('#tsEnd').val('<%=strDateEnd%>');</script>
		</td>
      	<td width="5"></td>
        </tr>
        <tr > 
          <td colspan="5" height="2"></td>
        </tr>
        <Tr>
        	<td colspan="6" align="right"> 
			<input class="button1" name=add type=button value=" 查 找 " onClick="ConfirmSearch(frmQry)" onKeyDown="ConfirmSearch(frmQry)">&nbsp;&nbsp;&nbsp;&nbsp;		
          </td>
        </Tr>
        <tr > 
          <td colspan="5" height="5"></td>
        </tr>
       </table>
      <br>
      
      <%if(tableDivFlag.equals("1")){ %>
      <div id="tableDiv" style="display:block">
      <%}else{ %>
      <div id="tableDiv" style="display:none">
      <%} %>
	<table class="top" width="100%" >
        <tbody>
        <tr>
          <td width="1%">&nbsp;</td>
	      <td>
		      <br><TABLE width="100%" id="flexlist"></TABLE><br>
		  </td>
          <td width="1%">&nbsp;</td>
	   </tr>
	  </tbody>
	</table>
	<br>
	
<%
	if(coll!=null)
	{
%>	 
                 
      <table width="730" border="0" cellspacing="0" cellpadding="0" >
      <div align="left"><br>
        <span class="graytext">查询日期：<%=DataFormat.getDateString()%> <!--查询时间：14：20--></span><br>
      </div>
	</table>
		<table width=100% border="0" cellspacing="0" cellpadding="0">
			<tr> 
            	<td width="490" height="17"> 
              		<div align="left"></div>
            	</td>
          		<td width="46"> 
            		<div align="right"></div>
          		</td>
          		<td width="49"> 
		   			<input class="button1" name=add type=button value=" 下载查找结果 " onClick="javascript:downloadresult();" onKeyDown="javascript:downloadresult();">&nbsp;&nbsp;      
       			</td>
          <%
				 strAccountNo = NameRef.getAccountNoByID(lAccountID);
                
			      strAccountType=Long.parseLong(strAccountNo.substring(3,5));
				 //String strAccountType1=strAccountNo.substring(3,5);
				 //System.out.println("strAccountType1=========:"+strAccountType1);
				if (strAccountType==10 || strAccountType==11 || strAccountType==13 || strAccountType==14 ||strAccountType==16)
				{
			
			%>
				<td width="9" height="17"></td>
		 		<td width="9" height="17">
				 	<input class=button1 name=add type=button value=" 打 印 " onClick="javascript:printme();" onKeyDown="javascript:printme();">&nbsp;&nbsp;
			   	</td>
			   	<td width="9" height="17"></td>
         		<td width="60" height="17"> 
           			<INPUT class=button1 name=Submit23 type=button onclick="doPrintAccountBalanceDetail()" type=button1 value=" 账户余额对账单 ">
            	</td>
			
				<%}
			   else
		       {%>
				<td width="9" height="17"></td>
            	<td width="60" height="17"> 
            		<input class=button1 name=add type=button value=" 打印 " onClick="javascript:printme();" onKeyDown="javascript:printme();">
            	</td>
				<%
			   }				
			   %>
          	</tr>
          	<tr height="10"><td></td></tr>
		</table>
<%
	}//end coll!=null
%>	 
</div>

    </td>
    </tr>
   </table>
 <input type="hidden" name="strTransNo" value="-1">  
 </form>
<SCRIPT language=JavaScript>
var sAccount_t = frmQry.lAccountID_T.value;
var lAccountID = sAccount_t.substring( 0, sAccount_t.indexOf(';') );
var lAccountGroupID = parseInt( sAccount_t.substring( sAccount_t.indexOf(';')+1 ) ); 
var lAccountTypeID = sAccount_t.substring( sAccount_t.indexOf('@')+1 );
frmQry.lAccountID.value = lAccountID;
frmQry.lAccountTypeID.value = lAccountTypeID;
frmQry.lAccountGroupID.value = lAccountGroupID;

jump();
 
function jump()
{
	var sAccount_t = frmQry.lAccountID_T.value;
	var lAccountID = sAccount_t.substring( 0, sAccount_t.indexOf(';') );
	var lAccountGroupID = parseInt( sAccount_t.substring( sAccount_t.indexOf(';')+1 ) ); 
	var lAccountTypeID = sAccount_t.substring( sAccount_t.indexOf('@')+1 );
	
	frmQry.lAccountID.value = lAccountID;
	frmQry.lAccountTypeID.value = lAccountTypeID;
	frmQry.lAccountGroupID.value = lAccountGroupID;
	
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
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.NOTIFY%>
		||lAccountGroupID==<%=SETTConstant.AccountGroupType.MARGIN%>)
	{
		if(lAccountGroupID==<%=SETTConstant.AccountGroupType.FIXED%>)
		{
			//FixedDepositNo.style.display = "";
			//FixedDepositNoLine.style.display = "";
		}
		if(lAccountGroupID==<%=SETTConstant.AccountGroupType.NOTIFY%>)
		{
			//NotifyDepositNo.style.display = "";
			//NotifyDepositNoLine.style.display = "";
		}
	}	
}
<%if(tableDivFlag.equals("1")){ %>
    
$(document).ready(function() {

    $(".FormTitle").click(function(){
      	$("#iTable").toggle();
    });

	$("#flexlist").flexigridenc({
		colModel : [
			{display: '日期',  name : 'sloginno', width : 175, sortable : false, align: 'center'},
			{display: '交易编号',  name : 'nofficeid', width : 175, sortable : false, align: 'center'},
			{display: '摘要',  name : 'NINPUTUSERID', width : 175, sortable : false, align: 'center'},
	<%
			if( SETTConstant.AccountGroupType.CURRENT != lAccountGroupID && SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
				{
	%>	
					{display: '业务类型',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
	<%
					if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID 
							||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID
							||SETTConstant.AccountGroupType.MARGIN == lAccountGroupID)
					{
	%>	
						{display: '单据号',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
	<%
					}
					if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
							||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
							||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
							||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID
							||SETTConstant.AccountGroupType.YT == lAccountGroupID)
					{
	%>
						{display: '合同号',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
	<%
					}
				}
%>
			{display: '对方账号',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
<%
			if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
			{
%> 
				{display: '对方账户名称',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
<%
			}
%>
			{display: '借方金额',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
			{display: '贷方金额',  name : 'dtinput', width : 175, sortable : false, align: 'center'},
			{display: '余额',  name : 'dtinput', width : 175, sortable : false, align: 'center'}
		],//列参数
		title:'<%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>',
		classMethod : 'com.iss.itreasury.ebank.obquery.action.OBQueryTransAccountAction.queryTransAccountDetail',//要调用的方法
		page : <%=flexiGridInfo.getFlexigrid_page()%>,
		rp : <%=flexiGridInfo.getFlexigrid_rp()%>,
		//sortname : '<%=flexiGridInfo.getFlexigrid_sortname().equals("") == true ? "sname" : flexiGridInfo.getFlexigrid_sortname()%>',// 预设指定哪列进行排序
		//sortorder : '<%=flexiGridInfo.getFlexigrid_sortorder().equals("") == true ? "asc" : flexiGridInfo.getFlexigrid_sortorder()%>',// 预设排序的方式
		userFunc : getFormData,
		usepager : false,
		printbutton : false,
		exportbutton : false
	});
	
});
  <%} %>
function getFormData() 
{
	return $.addFormData("frmQry","flexlist");
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

function ConfirmSearch(form)
{ 
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
	if(frmQry.lAccountID_T.value == -1)
	{
		alert("请选择账号");
		return false;
	}
	strDateStart = form.tsStart.value;
	strDateEnd = form.tsEnd.value;
	var strTemp = frmQry.lAccountID_T.value;
	var lAccountID = strTemp.substring( 0, strTemp.indexOf(';') );
	var lAccountGroupID = parseInt( strTemp.substring( strTemp.indexOf(';')+1 ) ); 
	var lAccountTypeID = strTemp.substring( strTemp.indexOf('@')+1 );
	
	frmQry.lAccountID.value = lAccountID;
	frmQry.lAccountTypeID.value = lAccountTypeID;
	frmQry.lAccountGroupID.value = lAccountGroupID;
		
	showSending();
	form.action = "queryLiShi.jsp";
	frmQry.target = "";
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
	window.open("","_formwin","scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;");
	frmQry.target = "_formwin";
	frmQry.submit();
	frmQry.target = "";
<%--	window.open('/NASApp/iTreasury-ebank/accountinfo/Khdzd.jsp?lAccountID=<%=lAccountID%>&lAccountTypeID=<%=lAccountTypeID%>&lClientID=<%=sessionMng.m_lClientID%>',"NewWin",'width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;');--%>
}
function doPrintAccountBalanceDetail()//账户余额对账单

{	
window.open('/NASApp/iTreasury-settlement/settlement/query/control/c043_1.jsp?strStartClientCode=<%=NameRef.getClientCodeByID(sessionMng.m_lClientID)%>&strEndClientCode=<%=NameRef.getClientCodeByID(sessionMng.m_lClientID)%>&strAccountType=<%=strAccountType%>&strDate='+frmQry.tsEnd.value+'&strSuccessPageURL=/settlement/query/view/v043_1.jsp&strFailPageURL=/NASApp/iTreasury-ebank/accountinfo/LiShi.jsp&lAccountStatusID=1&lIsFiltrate=-1&UrlType=ebank&e_lOfficeID=<%=sessionMng.m_lOfficeID%>&e_lCurrencyID=<%=sessionMng.m_lCurrencyID%>','popup', 'width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=0,top=0;');
}
 </SCRIPT>
<%
OBHtml.showOBHomeEnd(out);
}
catch(Exception e)
{
	out.println( e.getMessage() );
}
%>

<%@ include file="/common/SignValidate.inc" %>

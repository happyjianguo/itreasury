<%--
/*
 * 程序名称：LiShi.jsp
 * 功能说明：账户历史明细查询页面
 * 作　　者：刘琰
 * 完成日期：2003年11月25日
 */
--%>

<!--Header start-->
<%@ page contentType = "text/html;charset=gbk" %>
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
				 java.util.Iterator,
				 com.iss.itreasury.ebank.util.FindSubHistory"
%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.ebank.privilege.dao.OB_UserDAO"%>
<%@ page import="com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ include file="/common/common.jsp" %>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<!--Header end-->
<%!
	/* 标题固定变量 */
	String strTitle = "[历史明细]";
%>
<%


	try
	{
		
		String clientCode="";//客户编号
		
		clientCode=sessionMng.m_strClientCode;
		
		
		
		
		FindSubHistory fsh = new FindSubHistory();
		
		HashMap map = new HashMap();
		
		map = (HashMap)fsh.findabc(clientCode,1);

		
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
		String seAccountName = request.getParameter("selectAccountName");

%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />

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


	function getAccount(val)
	{
		<%
		String lAccount_t = request.getParameter("lAccountID_T");
			if(map != null && map.size() >0)
			{
				for(Iterator ita = map.keySet().iterator(); ita.hasNext();)
				{
					String keys = (String)ita.next();
			%>
					if(val == "<%= keys%>")
					{
						<%
							HashMap mapValue = new HashMap();
							mapValue = (HashMap)map.get(keys);
							if(mapValue != null && mapValue.size() > 0)
							{
								%>
									document.frmQry.lAccountID_T.length=<%= mapValue.size()%>;
								<%
								int i = 1;
								for(Iterator itc = mapValue.keySet().iterator(); itc.hasNext();)
								{	
									String keysc = (String)itc.next();
								%>
									document.frmQry.lAccountID_T.options[<%= i%>] = new Option("<%= (String)mapValue.get(keysc)%>","<%= keysc%>"); 
								<%
									if(lAccount_t != null && keysc.equals(lAccount_t))
									{
								%>
										document.frmQry.lAccountID_T.options[<%= i%>].selected = true;
								<%
									}
									i++;
								}
							}
							else{
							%>
							document.frmQry.lAccountID_T.length=0;
							document.frmQry.lAccountID_T.options[0] = new Option("请选择","0"); 
							<%
							}
											
						%>
					}else if(val == "0")
					{
						document.frmQry.lAccountID_T.length=0;
						document.frmQry.lAccountID_T.options[0] = new Option("请选择","0"); 
					}
			<%
				}
			}
		%>
	}

</script>  
<form name='frmQry' method='get' action='queryLiShi.jsp' >
<table width="774" border="0" align="center" cellpadding="0" cellspacing="0" class="txt_black">
  <tr>
    <td height="4"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#47BBD2"></td>
  </tr>
  <tr>
    <td height="24" valign="top"><table width="150" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1" bgcolor="#47BBD2"></td>
        <td width="124" background="/webob/graphics/new_til_bg.gif">　<span class="txt_til2">下属单位历史明细</span></td>
        <td width="25" align="right"><img src="/webob/graphics/newconner.gif" width="30" height="22"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
</table>
		<br/>
  <table width="774" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td width="3" ><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
    <td width="90" background="/webob/graphics/lab_conner2.gif"  class="txt_til2">账户历史明细</td>
    <td width="554"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>
 </table>	
	
	<table width=774 border="0" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
	   
        <tr bgcolor="#FFFFFF" class="MsoNormal"> 
          <td colspan="5" height="1"></td>
        </tr>
        <tr id="AccountID" cellPadding=0> 
          <td width="5" height="25"></td>
          
     	  <td height="25" width="70" class="MsoNormal" >客户名称：</td>
          <td width="30" height="10" class="MsoNormal"> 
            <div align="right"></div>
          </td>
          <td height="25" class="MsoNormal"> 
		  <input type="hidden" name="lAccountID" value="-1">
		  <input type="hidden" name="lAccountTypeID" value="-1"> 
		  <input type="hidden" name="lAccountGroupID" value="-1"> 
		  <input type="hidden" name="lClientID" value="-1"> 
		  <input type="hidden" name="lContractTypeIDs" value="-1"> 
		  <input type="hidden" name="pageType" value="1"> 
		   <select class='box' name="selectAccountName" onchange="getAccount(this.value);">
 			<option value="0" selected >请选译</option>
			<%
				ArrayList lista = new ArrayList();
				Collection c = null;
				Iterator its = null;
				
				OB_UserDAO dao = new OB_UserDAO();
			    c=dao.findUserByCondition(sessionMng.m_lClientID,1,1);
		if (c != null)
		{
			its = c.iterator();
		}		
		OB_UserInfo info;
		while(its.hasNext()){
		info= (OB_UserInfo) its.next();
		lista.add(info);
		}
				
				NameRef refa = new NameRef();
				
				if(lista != null && lista.size() > 0)
				{
				
					for(int i=0;i<lista.size();i++)
					{
					
				
			%>
					<option value="<%= ((OB_UserInfo)lista.get(i)).getNClientId()%>" <%= seAccountName != null&&seAccountName.equals(String.valueOf(((OB_UserInfo)lista.get(i)).getNClientId()))?"selected":"" %>><%= refa.findClientNameByID(((OB_UserInfo)lista.get(i)).getNClientId())%></option>
			<%
					}
				}
			%>
 		   </select>
 		   
		  </td>
		  </tr>
 		 <tr id="AccountID" cellPadding=0> 
          <td width="5" height="25"></td>
 		 <td height="25" width="70" class="MsoNormal" >账号：</td> 	
 		<td width="30" height="10" class="MsoNormal"> 
            <div align="right"></div>
          </td>
 		<td height="25" class="MsoNormal">
 		   <select name="lAccountID_T" onchange="jump();">
         	 <option value="0" selected >请选译</option>
 		  	</select>
 		  	</td>
 		  	
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
		<tr bgcolor="#FFFFFF" id="ContractCodeLine" height="25" class="MsoNormal"> 
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
          <td height="25" class="MsoNormal">执行日期：</td>
          <td width="60" height="25" class="MsoNormal"> 
            <div align="right">由</div>
          </td>
          <td  height="25" class="MsoNormal"> 
            <fs_c:calendar 
          	    name="tsStart"
	          	value="" 
	          	size="12"/>
	          <script>
	          		$('#tsStart').val('<%=strDateStart%>');
	          	</script>
			至
			<fs_c:calendar 
          	    name="tsEnd"
	          	value="" 
	          	size="12"/>
	          <script>
	          		$('#tsEnd').val('<%=strDateEnd%>');
	          	</script>
		  </td>
          <td width="5"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
      </table>
      <br>
	   <table width="774" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="366"> 
            <div align="right"></div>
          </td>
          <td width="284"> 
            <div align="right"></div>
          </td>
          <td width="60"> 
            <div align="right">
			<input class=button1 name=add type=button value=" 查找 " onClick="ConfirmSearch(frmQry)" onKeyDown="ConfirmSearch(frmQry)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
			</div>
          </td>
        </tr>
      </table> 
	  <br>
<%
	if (coll!=null && coll.size()<2)
	{
%>
<table width="774" border="0"  cellpadding="0" cellspacing="0" align=center>
  <tr>
    <td width="3" ><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
    <td width="90" background="/webob/graphics/lab_conner2.gif"  class="txt_til2"> <%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%></td>
    <td width="554"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>
	 
      </table>


	  
	<table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
	    <thead>
        <tr align="center" >
          <td width="10%" height="20" >日期</td>
          <td width="10%" height="20" >交易编号</td>
<%
	
%>		  
          <td width="15%" height="20" >摘要</td>
<%
		if(  SETTConstant.AccountGroupType.CURRENT != lAccountGroupID
				&& SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
		{
%>    
          <td width="10%" height="20" >业务类型</td>
<%
			if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
			||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID
			||SETTConstant.AccountGroupType.MARGIN == lAccountGroupID)
			{
%>	 
		  <td width="10%" height="20" >单据号</td>
<%
			}
			if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
				||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
				||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
				||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID)
			{
%>
		 <td width="10%" height="20" >合同号</td>
<%
			}
		}
%>      
      <td width="10%" height="20" >对方账号</td>
<%
		if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
		{
%>          
      <td width="10%" height="20" >对方账户名称</td>
<%
		}
%>
          
      <td width="10%" height="20" >付款金额</td>
          
      <td width="10%" height="20" >收款金额</td>
          <td width="15%" height="20" >余额</td>
        </tr>
        </thead>
	 <tr  align="center" >
		<td  height="23"><%=DataFormat.formatDate(tsStartDate)%></td>
		<td  height="23"></td>
<%
		if(SETTConstant.AccountGroupType.CURRENT != lAccountGroupID)
		{
%>    
		<td  height="23"></td>
<%
		}
%>
		<td  height="23">期初余额</td>
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23"></td>
		<td height="23"></td>
		<td  height="23" align="right"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
	 </tr>
	 <tr  align="center" bgcolor="#EBEBEB">
		<td  height="23"></td>
		<td  height="23"></td>
		<td height="23">本月合计</td>
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
		<td  height="23" align="right">0.00</td>
		<td  height="23" align="right">0.00</td>
		<td  height="23" align="right"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
	 </tr>
	 <tr  align="center" bgcolor="#EBEBEB" >
		<td  height="23"></td>
		<td  height="23"></td>
		<td  height="23">本年合计</td>
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
		<td  height="23" align="right">0.00</td>
		<td  height="23" align="right">0.00</td>
		<td  height="23" align="right"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
	 </tr>
	</table>
	<br>
<%
	}
	else if(coll!=null && coll.size()>1)
	{
%>	  
<table width="774" border="0"  cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td width="3" ><img src="/webob/graphics/lab_conner1.gif" width="3" height="23"></td>
    <td width="90" background="/webob/graphics/lab_conner2.gif"  class="txt_til2"> <%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%> </td>
    <td width="554"><img src="/webob/graphics/lab_conner3.gif" width="16" height="23"></td>
  </tr>
	 
      </table>


	<table width=774 border="1" align="center" cellpadding="0" cellspacing="0" class=normal id="table1">
	    <thead>
        <tr align="center" >
          <td width="10%" height="20" >日期</td>
          <td width="10%" height="20" >交易编号</td>
          <td width="15%" height="20" >摘要</td>
<%
		if(  SETTConstant.AccountGroupType.CURRENT != lAccountGroupID
				&& SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
		{
%>    
          <td width="10%" height="20" >业务类型</td>
<%
			if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
			||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID
			||SETTConstant.AccountGroupType.MARGIN == lAccountGroupID)
			{
%>	 
		  <td width="10%" height="20" >单据号</td>
<%
			}
			if(SETTConstant.AccountGroupType.TRUST == lAccountGroupID
				||SETTConstant.AccountGroupType.CONSIGN == lAccountGroupID
				||SETTConstant.AccountGroupType.DISCOUNT == lAccountGroupID
				||SETTConstant.AccountGroupType.OTHERLOAN == lAccountGroupID)
			{
%>
		 <td width="10%" height="20" >合同号</td>
<%
			}
		}
%>             
      <td width="10%" height="20" >对方账号</td>
<%
		if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
		{
%>              
      <td width="10%" height="20" >对方账户名称</td>
<%
		}
%>          
      <td width="10%" height="20" >付款金额</td>
          
      <td width="10%" height="20" >收款金额</td>
          <td width="15%" height="20" >余额</td>
        </tr>
        </thead>
		<tr bordercolor="#999999" align="center" >
		<td  height="23"><%=DataFormat.formatDate(tsStartDate)%></td>
		<td  height="23"></td>
		<td  height="23">期初余额</td>
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
		<td  height="23" align="right"><%=dBalance == 0 ? "0.00" :DataFormat.formatDisabledAmount(dBalance)%></td>
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
		for(tsTempDate = tsYearLoopDate ;
			Long.valueOf(DataFormat.getYearString(tsTempDate)).longValue() == nYears 
			&& (DataFormat.getMonthString(tsTempDate).equals(DataFormat.getMonthString(tsEndDate))
					||tsTempDate.before(tsEndDate)||DataFormat.formatDate(tsTempDate).equals(DataFormat.formatDate(tsEndDate))) ; 
			tsTempDate = DataFormat.getNextDate(DataFormat.getLastDateOfMonth(tsTempDate)) )
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
			if(it != null)
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
		          <tr align="center" bgcolor="<%=(qtri.getAbstract() != null&&qtri.getAbstract().equals("<b>本日合计</b>"))?"#EBEBEB":"FFFFFF"%>" >
		          <td  height="23"><%= qtri.getExecuteDate() != null ? DataFormat.formatDate(qtri.getExecuteDate()) : "&nbsp;"%></td>
				  
				  <%if(SETTConstant.TransactionType.isInterestTransaction(qtri.getTransTypeID()))
				  	{%>
			          	<td  height="23"><%= qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;"%></td>
					<%}else{%>
			          	<td  height="23">
			          	
			          	<!--  <a href="javascript:doFindTransDetail(--><%//=qtri.getAccountID()%><!--,--><%//=qtri.getTransTypeID()%><!--,'--><%//=qtri.getTransNo()%><!--',--><%//=qtri.getGroupID()%><!--  );">--><%= qtri.getTransNo() != null ? qtri.getTransNo() : "&nbsp;"%><!--</a>-->
			          	</td>
					<%}%>
				  <td  height="23"  align="<%=(qtri.getAbstract() != null&&qtri.getAbstract().equals("<b>本日合计</b>"))?"center":"left" %>"><%=qtri.getAbstract() != null? (qtri.getAbstract().equals("<b>本日合计</b>")?"本日合计":qtri.getAbstract()): "&nbsp;"%></td>
<%
				if( SETTConstant.AccountGroupType.CURRENT != lAccountGroupID
					&& SETTConstant.AccountGroupType.OTHERDEPOSIT != lAccountGroupID)
				{
%>    
         		 	<td  height="23"><%=SETTConstant.TransactionType.getName(qtri.getTransTypeID())%></td>
<%
					if(SETTConstant.AccountGroupType.FIXED == lAccountGroupID
					||SETTConstant.AccountGroupType.NOTIFY == lAccountGroupID
					||SETTConstant.AccountGroupType.MARGIN == lAccountGroupID)
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
		          <td  height="23"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccumulationOldAcctNoByAcctID(qtri.getOppAccountID()): (qtri.getOppAccountNo()!=null?qtri.getOppAccountNo():"")%></td> 
<%
				if(SETTConstant.AccountGroupType.CURRENT == lAccountGroupID)
				{
%>      		    
				  <td  height="23"><%= qtri.getOppAccountID() > 0 ?  NameRef.getAccountNameByID(qtri.getOppAccountID()) : (qtri.getOppAccountName()!=null?qtri.getOppAccountName():"")%></td> 
<%
				 }			
				  if(qtri.getTransTypeID() == -1000)//日合计需要加粗显示
				    {%>
			          <td  height="23" align="right"><%= qtri.getPayAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getPayAmount()) : "0.00"%></td>
			          <td  height="23" align="right"><%= qtri.getReceiveAmount() != 0 ? DataFormat.formatDisabledAmount(qtri.getReceiveAmount()) : "0.00"%></td>
			          <td  height="23" align="right"><%= DataFormat.formatDisabledAmount(qtri.getBalance())%></td>
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
			<tr  align="center" bgcolor="#EBEBEB">
			<td  height="23"><%=DataFormat.getLastDateString(tsTempDate)%></td>
			<td  height="23"></td>
			<td  height="23">本月合计</td>
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
			<td  height="23" align="right"><%= dMonthPayBalance != 0 ? DataFormat.formatDisabledAmount(dMonthPayBalance) : "0.00"%></td>
			<td  height="23" align="right"><%= dMonthReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dMonthReceiveBalance) : "0.00"%></td>
			<td  height="23" align="right"><%= dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></td>

<%
		}//end month
%>
			<tr  align="center"  bgcolor="#EBEBEB">
			<td  height="23"><%=DataFormat.getYearString(DataFormat.getPreviousMonth(tsTempDate,1))%></td>
			<td  height="23"></td>
			<td  height="23">本年合计</td>
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
			<td  height="23" align="right"><%= dYearPayBalance != 0 ? DataFormat.formatDisabledAmount(dYearPayBalance) : "0.00"%></td>
			<td  height="23" align="right"><%= dYearReceiveBalance != 0 ? DataFormat.formatDisabledAmount(dYearReceiveBalance) : "0.00"%></td>
			<td  height="23" align="right"><%= dBalance != 0 ? DataFormat.formatDisabledAmount(dBalance) : "0.00"%></td>
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
    
                 
       <table width="730" border="0" cellspacing="0" cellpadding="0" align="center">
      <div align="left"><br>
        <span class="graytext">查询日期：<%=DataFormat.getDateString()%> <!--查询时间：14：20--></span><br>
      </div>
        </table>
         <table width="730" border="0" cellspacing="0" cellpadding="0" align="center">
          <tr> 
            <td width="490" height="17"> 
              <div align="left"></div>
            </td>
          <td width="46"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
		   <div align="right">
		  	<input class=button1 name=add type=button value=" 下载查找结果 " onClick="javascript:downloadresult();" onKeyDown="javascript:downloadresult();"> </div>      
         </td> 
           
            <td width="70" height="17"> 
            <div align="right">
			<input class=button1 name=add type=button value=" 打印 " onClick="javascript:printme();" onKeyDown="javascript:printme();">  </div> 
            </td>&nbsp;&nbsp;
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
	frmQry.lAccountID.value = lAccountID;
	frmQry.lAccountTypeID.value = lAccountTypeID;
	frmQry.lAccountGroupID.value = lAccountGroupID;

    <%
    	if(seAccountName != null ) 
    	{
    %>
    	var seAccountName = "<%= seAccountName%>";
    	getAccount(seAccountName);
    <%
    	}
    %>

jump();
 
function jump()
{
	
	if(frmQry.lAccountID_T.length > 1)
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
	}else{
		frmQry.lAccountID_T.options[0]=new Option("请选择","0"); 
		ContractCode.style.display = "none";
		ContractCodeLine.style.display = "none";
		LoanNoteNo.style.display = "none";
		LoanNoteNoLine.style.display = "none";
		FixedDepositNo.style.display = "none";
		FixedDepositNoLine.style.display = "none";
		NotifyDepositNo.style.display = "none";
		NotifyDepositNoLine.style.display = "none";
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
	
	strDateStart = form.tsStart.value;
	strDateEnd = form.tsEnd.value;
	var strTemp = frmQry.lAccountID_T.value;
	var lAccountID = strTemp.substring( 0, strTemp.indexOf(';') );
	var lAccountGroupID = parseInt( strTemp.substring( strTemp.indexOf(';')+1 ) ); 
	var lAccountTypeID = strTemp.substring( strTemp.indexOf('@')+1 );
	
	frmQry.lAccountID.value = lAccountID;
	frmQry.lAccountTypeID.value = lAccountTypeID;
	frmQry.lAccountGroupID.value = lAccountGroupID;
	var seAc = frmQry.selectAccountName.value;
	var lat = frmQry.lAccountID_T.value;
	if(seAc == "0"){
		alert("请选择客户");
		frmQry.selectAccountName.selected;
		return false;
	}
	if(lat == "0"){
		alert("请选择账号");
		frmQry.lAccountID_T.selected;
		return false;
	}
	frmQry.lClientID.value = frmQry.selectAccountName.value;
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
<%--	frmQry.action="Khdzd.jsp";--%>
<%--	frmQry.target = "NewWindow_S";--%>
<%--	frmQry.submit();--%>
<%--	frmQry.target = "";--%>
	frmQry.action="Khdzd.jsp";
	window.open("","_formwin","toolbar=no, menubar=no, scrollbars=no,resizable=yes,location=no, status=no");
	frmQry.target = "_formwin";
	frmQry.submit();
	frmQry.target = "";
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

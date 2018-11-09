<%--
/**
 页面名称 ：a110-v.jsp
 页面功能 : 下属单位账户余额
 作    者 ： kewen hu
 日    期 ： 2004-01-13
 特殊说明 ：实现操作说明：
 修改历史 ：
 */
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
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
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo"%>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:include flush="true" page="/ShowMessage.jsp" />
<% String strContext = request.getContextPath();%>
<% System.out.println("进入页面:"+strContext);%>
<%
    //标题变量
	String strTitle = "[下属单位账户余额]";
    try {
         /* 用户登录检测 */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* 显示文件头 */
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    // 页面控制变量
    String sAction = null;
    String sActionResult = Constant.ActionResult.FAIL;
    String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
    String strFailPageURL = (String)request.getAttribute("strFailPageURL");

    long lClientID = -1;                                                //客户标识
    long lClientID2 = -1;
    long lAccountGroupID = -1;                                          //账户类型标识
    long lAccountID = -1;                                               //账号标识
    long lCurrencyID = sessionMng.m_lCurrencyID;                        //币种标识
    long lParentCorpID = sessionMng.m_lClientID;                        //母公司ID
    Timestamp tsEnd = Env.getSystemDate(
        sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);               //截至时间

    String sTemp = null;
    sTemp = (String)request.getParameter("lAccountGroupID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lAccountGroupID = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("lClientID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lClientID = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("lClientID");
    if (sTemp != null && sTemp.trim().length() > 0) {
    	lClientID2 = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("lClientID2");
    if (sTemp != null && sTemp.trim().length() > 0) {
    	lClientID2 = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("lAccountID");
    if (sTemp != null && sTemp.trim().length() > 0) {
        lAccountID = Long.parseLong(sTemp);
    }
    sTemp = (String)request.getParameter("tsEnd");
    if (sTemp != null && sTemp.trim().length() > 0) {
        tsEnd = DataFormat.getDateTime(sTemp);
    }
    String fromStr = null;
    sTemp = (String)request.getParameter("fromAccountType");
    if (sTemp != null && sTemp.trim().length() > 0) {
        fromStr = sTemp;
    }

    Collection listType = null;
    Collection listAccount = null;
    Iterator iterator = null;
    OBAccountQueryAmountInfo obaqai = null;
    Connection conn = null;
    OBAccountQueryAmountDao obaqad = new OBAccountQueryAmountDao(conn);
    OBAccountQueryWhere obaqw = new OBAccountQueryWhere();
    obaqw.setCurrencyID(lCurrencyID);
    obaqw.setOfficeID(sessionMng.m_lOfficeID);
    obaqw.setDateTo(tsEnd);
%>
<script language="JavaScript">

function chgCurrency() {
    document.frmQry.fromAccountType.value = "yes";
    frmQry.submit();
}
function slctChange() {
	//document.frmQry.fromAccountType.value = "yes";
    //showSending();
    //document.frmQry.submit();
}
function Queryform(form) {
    if (!checkDate(document.frmQry.tsEnd,1,"截至日期"))
        return (false);
    showSending();
    form.submit();
}
</script>
<form name="frmQry" method="post" action="a110-v.jsp">
    <input type="hidden" name="fromAccountType" value="">
	<input type="hidden" name="lClientID2" value="<%=lClientID2 %>">
	<table cellpadding="0" cellspacing="0" class="title_top">
	  <tr>
	    <td height="24">
		    <table cellspacing="0" cellpadding="0" class=title_Top1>
				<TR>
			       <td class=title nowrap="nowrap"><span class="txt_til2">下属单位账户余额查询</span></td>
			       <td class=title_right><a class=img_title></td>
				</TR>
			</TABLE>
			<br/>
		<table width=100% border="0"  cellpadding="0" cellspacing="0" class=normal id="table1">
	         <tr> 
	          	<td colspan="5" height="5"></td>
	        </tr>
	        <tr> 
	          <td width="4" height="25" >&nbsp;&nbsp;</td>
	          <td width="84" height="25" >账户类型：</td>
	          <td width="59" height="25" > 
	            <div align="right"></div>
	          </td>
	          <td width="416" height="25" > 
	            <select name="lAccountGroupID" class='select' onChange="slctChange()">
	              <option value="-1">全部</option>
	<%
            //modified by fxzhang 2006-12-28  
            listType = obaqad.getCodeInfo(SETTConstant.AccountGroupType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID));
            //listType = obaqad.getCodeInfo(SETTConstant.AccountGroupType.getCodeForZJ());
            
            if(listType != null) {
                iterator = listType.iterator();
            }
            while (iterator != null && iterator.hasNext()) {
                obaqai = (OBAccountQueryAmountInfo) iterator.next();
%>
              <option value="<%=obaqai.getAccountGroupID()%>" <%=obaqai.getAccountGroupID()==lAccountGroupID?"selected":""%>>
              <%=obaqai.getAccountGroupName()%></option>
<%
            }
%>
          </select>
          </td>
          <td width="6"></td>
        </tr>
        <tr> 
          <td colspan="5" height="1"></td>
        </tr>
        <tr >
          <td width="4" height="25">&nbsp;&nbsp;</td>
          <td colspan="2" height="25" class="MsoNormal" >下属单位：</td>
          <td width="416" height="25" class="MsoNormal">
<%
		//modify by zcwang 2007-3-20  增加区分办事处
        obaqad.showClientListControl(
            out, "lClientID", obaqw, lParentCorpID, lAccountGroupID, lClientID,sessionMng.m_lOfficeID,true);
        //
%>
          </td>
          <td width="6"></td>
        </tr>
        <tr> 
          <td colspan="5" height="1"></td>
        </tr>
        <tr > 
          <td width="4" height="25">&nbsp;&nbsp;</td>
          <td colspan="2" height="25" class="MsoNormal">下属单位账号：</td>
          <td width="416" height="25" class="MsoNormal">
<%
        //obaqad.showAccountListControl(
           // out, "lAccountID", obaqw, lAccountGroupID, lClientID, lAccountID);
//--------------------------------中交修改-------------------------------------
//modify by zcwang 2007-3-20 增加一个本帐户的客户编号sessionMng.m_lClientID
		obaqad.showAccountListControl(
           out, "lAccountID", obaqw, lAccountGroupID, lClientID2, lAccountID,lParentCorpID,sessionMng.m_lClientID);
Log.print("\n===========================lAccountGroupID:"+lAccountGroupID);
Log.print("\n===========================lClientID:"+lClientID);
Log.print("\n===========================lAccountID:"+lAccountID);
Log.print("\n===========================fromStr:"+fromStr);
%>
          </td>
          <td width="6"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <tr > 
          <td width="4" height="25">&nbsp;&nbsp;</td>
          <td colspan="2" height="25" class="MsoNormal" >截至日期：</td>
          <td width="416" height="25" class="MsoNormal"> 
        	  <fs_c:calendar 
	          	    name="tsEnd"
		          	value="" 
		          	properties="nextfield =''" 
		          	size="20"/>
		       <script>
	          $('#tsEnd').val('<%=DataFormat.getDateString(tsEnd)%>');
	    </script>
          </td>
          <td width="6"></td>
        </tr>
        <tr > 
          <td colspan="5" height="1"></td>
        </tr>
        <TR>
	         <td colspan="5">
	         <div align="right">
				<input class=button1 name=add type=button value=" 查 找 " onClick="javascript:Queryform(document.frmQry)" onKeyDown="javascript:Queryform(document.frmQry)">&nbsp;&nbsp;&nbsp;&nbsp;	
	         </div>
	         </td>
        </TR>
         <tr> 
          	<td colspan="5" height="5"></td>
        </tr>
      </table>
      <br/>
<%
if (request.getParameter("fromAccountType") != null && 
    !request.getParameter("fromAccountType").equals("yes")) {
    int iItem = 0;
    double Balance = 0.0;
    double SubSumBalance = 0.0;
    double SumBalance = 0.0;
    double Total = 0.0;
    Collection clientList = null;
    Iterator iterList = null;
    Iterator iterClient = null;
    Iterator iterAccount = null;
    boolean bLoop = false;
    boolean  isNotnull = false;
    
    //modified by xfma 2009-02-18 
    if(lAccountGroupID == -1){
        bLoop = true;
    }
    long[] allCode=SETTConstant.AccountGroupType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
	if(allCode!=null){//张雷 添加   原来没有判断此数组是否为空就直接使用.length方法，导致程序异常，现在进行判断
	    for(int i=1; i<=(bLoop?allCode.length:1); i++) 
	    {
	        Collection list = new ArrayList();
	        if(bLoop) lAccountGroupID = (long) i;
	        if (lClientID == -1) {
	            if (lAccountID == -1) {
	                clientList = obaqad.getChildClientID(obaqw,lParentCorpID,sessionMng.m_lOfficeID,true);
	                if (clientList != null) {
	                    iterClient = clientList.iterator();
	                }
	                boolean self=false;
	                while (iterClient != null && iterClient.hasNext()) {
	                    OBAccountQueryAmountInfo infoClient = (OBAccountQueryAmountInfo) iterClient.next();
	                    if(sessionMng.m_lClientID==infoClient.getClientID()) self=true;
	                    //Collection accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, infoClient.getClientID(),lParentCorpID,self);
	                    Collection accountList = null;
	                    if(self==false)
	                   		accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, infoClient.getClientID());
                    	if (accountList != null) {
	                        iterAccount = accountList.iterator();
	                        while (iterAccount != null && iterAccount.hasNext()) {
	                            OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
	                            list.add(infoAccount);
	                        }
	                    }
	                    self=false;
	                }
	                list = obaqad.compositByAccountTypeID(list);
	                list = obaqad.compositByLoanClientTypeID(list);
	            } else {
	           		Collection accountList = obaqad.getAccountInfoByAccountID(obaqw, lAccountGroupID, lAccountID);
	                if (accountList != null) {
	                    iterAccount = accountList.iterator();
	                    while (iterAccount != null && iterAccount.hasNext()) {
	                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
	                        list.add(infoAccount);
	                    }
	                }
	            }
	        } else {
	         	boolean self=false;
	             	if(sessionMng.m_lClientID==lClientID) self=true;
	            if (lAccountID == -1) {
	                Collection accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, lClientID);
	                if (accountList != null) {
	                    iterAccount = accountList.iterator();
	                    while (iterAccount != null && iterAccount.hasNext()) {
	                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
	                        list.add(infoAccount);
	                     self=false;
	                    }
	                }
	                list = obaqad.compositByAccountTypeID(list);
	                list = obaqad.compositByLoanClientTypeID(list);
	            } else {
	                Collection accountList = obaqad.getAccountInfoByAccountID(obaqw, lAccountGroupID, lAccountID);
	                if (accountList != null) {
	                    iterAccount = accountList.iterator();
	                    while (iterAccount != null && iterAccount.hasNext()) {
	                        OBAccountQueryAmountInfo infoAccount = (OBAccountQueryAmountInfo) iterAccount.next();
	                        list.add(infoAccount);
	                    }
	                }
	            }
	        }
	        
	        if(list!=null && list.size()>0)
	        {
	        isNotnull = true;
	%>
	  <table width=100% border="0"  cellpadding="0" cellspacing="0" >
	  <tr>
	    	<td width="3"><a class=lab_title1></td>
			<td class="lab_title2" nowrap="nowrap" style="width:300px"><%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>截至日期：<%=DataFormat.getDateString(tsEnd)%>账户信息</td>
			<td width=""><a class=lab_title3></td>
	  </tr>
	 </table>
	          
	   <table width=100% border="1"  cellpadding="0" cellspacing="0" class=list_table id="table1">
	              <tr class="itemtitle"> 
	                <td height="26" width="20%" >
	                  <p align="center">账户类型</p>
	                </td>
	
	                <td width="20%"> 
	                  <p align="center">开户名称</p>
	                </td>
	                
	                <td width="20%"> 
	                  <p align="center">资金余额</p>
	                </td>
	                
	                <td width="20%"> 
	                  <p align="center">开户地</p>
	                </td>
	                
	                <td width="20%"> 
	                  <p align="center">账号</p>
	                </td>
	              </tr>
	             
	<%
	        long lLoanClientTypeID = 0;
	        long lAccountTypeID = 0;
	        String strLoanClientTypeName = "";
	        Balance = 0.0;
	        SubSumBalance = 0.0;
	        //SumBalance = 0.0;
	        Total = 0.0;
	        if (list != null) {
	            iterList = list.iterator();
	        }
	        int k=0;
	        while (iterList != null && iterList.hasNext()) {
	        k++;
	            OBAccountQueryAmountInfo info = (OBAccountQueryAmountInfo) iterList.next();
	           
	            String strSefcBankAccountNo = NameRef.getBankAccountNOByCurrenctAccountID(info.getOpenAccountNo());
	           // liuguang 2007-11-13 判断是贷款账户，还是存款账户
	         if(SETTConstant.AccountType.isLoanAccountType(info.getAccountTypeID())){
	         	Balance = obaqad.getBalanceByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID) - obaqad.getAmountByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID);  
	         }else{
	           Balance = obaqad.getBalanceByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID) + obaqad.getAmountByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID);
	         }
	            if (lAccountTypeID == 0 || lAccountTypeID != info.getAccountTypeID()) {
	                lAccountTypeID = info.getAccountTypeID();
	                if(lLoanClientTypeID != 0) {
	%>
	              <tr >
	                <td  valign="middle" align="left" height="26" >&nbsp;</td>
	                
	               <td  valign="middle" align="left"  > 
	                  <div align="right"><%=strLoanClientTypeName==null?"&nbsp;":strLoanClientTypeName%></div>
	                </td>
	                
	                <td  valign="middle" align="right" >
	                <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%></td>
	                
	                <td  valign="middle"  >&nbsp;</td>
	                
	                <td  valign="middle" align="left">&nbsp;</td>
	              </tr>
	<%
	                    SubSumBalance = 0.0;
	                }
	%>
	              <tr bgcolor="#EBEBEB">
	                <td  valign="middle" align="left"  height="26">
	                    <span class="graytext"><%=SETTConstant.AccountType.getName(lAccountTypeID)%></span>
	                </td>
	                
	                <td  valign="middle" align="right" > 
	                  <div align="right">总计</div>
	                </td>
	                
	                <td  valign="middle" align="right" >
	                    <span id="sum<%=++iItem%>"></span>
	                    <%
	                if (iItem > 1) {
	                    %>
	                    <script language="JavaScript">
	                        sum<%=iItem-1%>.innerHTML = "<%=obaqad.formatAmount(SumBalance)%>";
	                    </script>
	                    <%
	                }
	                    %>
	                </td>
	                
	                <td valign="middle" align="left" >&nbsp;</td>
	                
	                
	                <td valign="middle" align="left" >&nbsp;</td>
	              </tr>
	
	              <tr>
	                <td valign="middle" align="left"   height="26">&nbsp;</td>
	              
	                <td valign="middle" align="left"  > 
	                  <div align="left"><%=info.getOpenAccountName()%></div>
	                </td>
	                
	                <td valign="middle" align="right" >
	                <%=Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2)%></td>
	                
	                <td valign="middle" align="center" > 
	                  <div align="center"><%="财务公司"%></div>
	                </td>
	                
	                <td valign="middle" align="center" >
	                    <a href="a114-c.jsp?
						lCurrencyID=<%=lCurrencyID%>
						&lAccountGroupID=<%=lAccountGroupID%>
						&lAccountTypeID=<%=lAccountTypeID%>
						&lAccountID=<%=info.getOpenAccountID()%>
						&sAccountNo=<%=info.getOpenAccountNo()%>
						&tsStart=<%=DataFormat.getDateString(tsEnd)%>
						&tsEnd=<%=DataFormat.getDateString(tsEnd)%>" >
						<%=NameRef.getNoLineAccountNo(info.getOpenAccountNo())%></a>
	                </td>
	              </tr>
	          
	<%
	                lLoanClientTypeID = info.getLoanClientTypeID();
	                strLoanClientTypeName = info.getLoanClientTypeName();
	                SumBalance = 0.0;
	            } else {
	                if(lLoanClientTypeID != 0 && lLoanClientTypeID != info.getLoanClientTypeID()) {
	%>
	              <tr>
	                <td  valign="middle" align="left" height="26" >&nbsp;</td>
	                
	               <td  valign="middle" align="left"  > 
	                  <div align="right"><%=strLoanClientTypeName%></div>
	                </td>
	                
	                <td  valign="middle" align="right"  >
	                <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%></td>
	                
	                <td  valign="middle" align="left" >&nbsp;</td>
	                
	                <td  valign="middle" align="left">&nbsp;</td>
	              </tr>
	<%
	                    SubSumBalance = 0.0;
	                }
	%>
	              <tr>
	                <td valign="middle" align="left" height="26">&nbsp;</td>
	                
	                <td valign="middle" align="left" > 
	                  <div align="left"><%=info.getOpenAccountName()%></div>
	                </td>
	                
	                <td valign="middle" align="right">
	                <%=Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2)%></td>
	               
	                <td valign="middle" align="center" > 
	                  <div align="center"><%="财务公司"%></div>
	                </td>
	                
	                <td valign="middle" align="center" >
	                    <a href="a114-c.jsp?
						lCurrencyID=<%=lCurrencyID%>
						&lAccountGroupID=<%=lAccountGroupID%>
						&lAccountTypeID=<%=lAccountTypeID%>
						&lAccountID=<%=info.getOpenAccountID()%>
						&sAccountNo=<%=info.getOpenAccountNo()%>
						&tsStart=<%=DataFormat.getDateString(tsEnd)%>
						&tsEnd=<%=DataFormat.getDateString(tsEnd)%>">
						<%=NameRef.getNoLineAccountNo(info.getOpenAccountNo())%></a>
	                </td>
	              </tr>
	 
	<%
	                lLoanClientTypeID = info.getLoanClientTypeID();
	                strLoanClientTypeName = info.getLoanClientTypeName();
	            }
	            SubSumBalance += Balance;
	            SumBalance += Balance;
	            Total += Balance;
	            //if (iterList == null || !iterList.hasNext()) {
	%>
	              <!--<tr>
	                <td  valign="top" align="left" height="18" >&nbsp;</td>
	                
	               <td  valign="top" align="left" height="18"> 
	                  <div align="right"><%=strLoanClientTypeName%></div>
	                </td>
	                
	                <td  valign="top" align="right"  height="18">
	                 <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%> </td>
	                
	                <td  valign="top" align="left"  height="18">&nbsp;</td>
	               
	                <td  valign="top" align="left"  height="18">&nbsp;</td>
	              </tr>-->
	<%
	                SubSumBalance = 0.0;
	           // }
	        }
	%>
	           <tr bgcolor="#EBEBEB">
	                <td valign="middle" height="26"  colspan="5">
	                  <%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>总计：
	                &nbsp;
	                <%=Total==0.0?"0.00":DataFormat.formatDisabledAmount(Total,2)%>
	                </td>
	              </tr>
	              
	            </table>
	            
	      <br>
	<%
	        Total = 0.0;
	    }
	    }
	   }
    // 断开连接
    obaqad.closeConn();
    //end of FOR
    if(!isNotnull)
    {
%>
		<table border="0"  cellpadding="0" cellspacing="0" >
		  <tr>
		    <td width="3"><a class=lab_title1></td>
			<td class="lab_title2" style="width:160px;">截至日期：<%=DataFormat.getDateString(tsEnd)%></td>
			<td width="394"><a class=lab_title3></td>
		  </tr>
		 </table>
          <br/>
           <table width=100% border="1"  cellpadding="0" cellspacing="0" class=list_table id="table1">
              <tr class="itemtitle"> 
                <td height="26" >
                  <p align="center">账户类型</p>
                </td>

                <td > 
                  <p align="center">开户名称</p>
                </td>
                
                <td  > 
                  <p align="center">资金余额</p>
                </td>
                
                <td > 
                  <p align="center">开户地</p>
                </td>
                
                <td   > 
                  <p align="center">账号</p>
                </td>
              </tr>
               <tr > 
                <td height="18" >
                  <p align="center"></p>
                </td>

                <td height="26"> 
                  <p align="center"></p>
                </td>
                
                <td  > 
                  <p align="center"></p>
                </td>
                
                <td > 
                  <p align="center"></p>
                </td>
                
                <td   > 
                  <p align="center"></p>
                </td>
              </tr>
              </table>
<%
}
                if (iItem > 0) {
                    %>
<script language="JavaScript">
    sum<%=iItem%>.innerHTML = "<%=obaqad.formatAmount(SumBalance)%>";
</script>
<%}%>
  	<table width="99%" border="0" cellspacing="0" cellpadding="0" >
	  <tr>
	    <td width="582" align="left" valign="top" height="16"><br>
	     <% String queryDate=Env.getSystemDateString(); %> 
	      查询日期：<%=queryDate %>
	      <input type="hidden" name="queryDate" value="<%=queryDate %>"/>
	    </td>
	  </tr>
      </table>
      <br/>
		<table width="100%" border="0" cellspacing="2" cellpadding="2" height="15" >
			<tr>
				<td height="19" nowrap>
					<div align="right">
						<!--img src="/webob/graphics/button_xiazaichazhao.gif" width="119" height="18" onclick="doDownLoad()"-->
	                <input class=button1 name=add type=button value=" 下载查找结果 " onClick="doDownLoad()" onKeyDown="doDownLoad()"> 
						<!--img src="/webob/graphics/button_dayin.gif" width="46" height="18" onclick="doReport()"-->
	               <input class=button1 name=add type=button value=" 打 印 " onClick="doReport();" onKeyDown="doReport();">
					</div>
				</td>
			</tr>
		</table>
  </td>
  </tr>
  </table>
      </form>
<form name="frmNext" method="get" action="<%=strContext%>/accountinfo/a110-v.jsp">
        <!--利用隐藏控件，来控制页面-->
        <input type="hidden" name="strSuccessPageURL" value="/iTreasury-ebank/accountinfo/a110-v.jsp">
        <input type="hidden" name="strFailPageURL" value="/iTreasury-ebank/accountinfo/a110-v.jsp">
        <input type="hidden" name="strAction" value="<%= Constant.PageControl.LISTALL %>">
        <input type="hidden" name="lAccountGroupID" value="">
        <input type="hidden" name="lClientID" value="">
        <input type="hidden" name="lAccountID" value="">
        <input type="hidden" name="tsEnd" value="">
        <input type="hidden" name="queryDate" value="<%=queryDate %>"/>
</form>
<script language="JavaScript">
function doReport() {
    document.frmNext.target='blank_';
    document.frmNext.strAction.value='<%=Constant.PageControl.LISTALL%>';
    document.frmNext.action = "<%=strContext%>/accountinfo/a112-v.jsp";
    document.frmNext.strSuccessPageURL.value = "/iTreasury-ebank/accountinfo/a112-v.jsp";
    document.frmNext.strFailPageURL.value = "/iTreasury-ebank/accountinfo/a110-v.jsp";
    document.frmNext.lAccountGroupID.value=document.frmQry.lAccountGroupID.value;
    document.frmNext.lClientID.value=document.frmQry.lClientID.value;
    document.frmNext.lAccountID.value=document.frmQry.lAccountID.value;
    document.frmNext.tsEnd.value=document.frmQry.tsEnd.value;
    document.frmNext.submit();
}

function doDownLoad() {
    document.frmNext.target='blank_';
    document.frmNext.strAction.value='<%=Constant.PageControl.LISTALL%>';
    document.frmNext.action = "<%=strContext%>/accountinfo/a111-v.jsp";
    document.frmNext.strSuccessPageURL.value = "/iTreasury-ebank/accountinfo/a111-v.jsp";
    document.frmNext.strFailPageURL.value = "/iTreasury-ebank/accountinfo/a110-v.jsp";
    document.frmNext.lAccountGroupID.value=document.frmQry.lAccountGroupID.value;
    document.frmNext.lClientID.value=document.frmQry.lClientID.value;
    document.frmNext.lAccountID.value=document.frmQry.lAccountID.value;
    document.frmNext.tsEnd.value=document.frmQry.tsEnd.value;
    document.frmNext.submit();
}
</script>
<%
}
        //end of IF
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>

<%@ include file="/common/SignValidate.inc" %>
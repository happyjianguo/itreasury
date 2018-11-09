<%--
/**
 页面名称 ：a111-v.jsp
 页面功能 : 下属单位账户余额下载
 作    者 ： kewen hu
 日    期 ： 2004-01-13
 特殊说明 ：实现操作说明：
 修改历史 ：

 */
--%>

<%@ page contentType="application/msexcel;charset=UTF-8" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountQueryAmountDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.bizlogic.OBAccountQueryWhere"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.OBAccountQueryAmountInfo"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	response.setContentType("application/msexcel;charset=UTF-8");
    response.setHeader("Content-Disposition",";filename=\treport.xls");
%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Zoom.js"></script>

<% String strContext = request.getContextPath();%>
<% System.out.println("进入页面:"+strContext);%>
<%
//标题变量
String strTitle = "[下属单位账户余额]";
try {
    Log.print("\n*******进入页面--ebank/accountinfo/a111-v.jsp******\n");

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
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

    // 页面控制变量
    String sAction = null;
    String sActionResult = Constant.ActionResult.FAIL;
    String strSuccessPageURL = (String)request.getAttribute("strSuccessPageURL");
    String strFailPageURL = (String)request.getAttribute("strFailPageURL");
	
	boolean isNotnull = false;
	
    long lClientID = -1;                                                //客户标识
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
    String queryDate = null;
    sTemp = (String)request.getParameter("queryDate");
    if (sTemp != null && sTemp.trim().length() > 0) {
        queryDate = sTemp;
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
<HTML>
<HEAD><TITLE></TITLE>
<style type="text/css">
<!--
.table1 {  border: 1px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 12px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 12px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 12px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 12px}
-->
</style>
</HEAD>
<BODY text="#000000">
      <table width="570" class=top border=0>
        <tr>
          <td height="25">
            <table width="570" border="0" cellspacing="0" cellpadding="3" class=table1>
                <tr>
                    <td align="center" height="28" colspan="5" class="td-rightbottom">
                        <b><font size="3"><%=Env.getClientName()%>下属单位人民币账户余额表</font></b>
                    </td>
                </tr>
                <tr>
                    <td align="center"  height="28" class="td-rightbottom">
                    截至日期：<%=DataFormat.getDateString(tsEnd)%></td>
                    <td align="center"  height="18" colspan="3" class="td-rightbottom"></td>
                    <td align="center"  height="28" class="td-rightbottom">单位：元</td>
                </tr>
            </table>
            <table width="570" border="0" cellspacing="0" cellpadding="3" class=table1>
              <tr> 
                <td height="18" valign="bottom" class="td-rightbottom">
                  <p align="center"><font size="2">账户类型</font></p>
                </td>
                <td width="50" valign="bottom" height="18" class="td-rightbottom"> 
                  <div align="center">开户名称</div>
                </td>
                <td valign="bottom" height="18" class="td-rightbottom"> 
                  <p align="center"><font size="2">资金余额</font></p>
                </td>
                <td valign="bottom" height="18" class="td-rightbottom"> 
                  <div align="center">开户地</div>
                </td>
                <td valign="bottom" height="18" class="td-rightbottom"> 
                  <p align="center"><font size="2">账号</font></p>
                </td>
              </tr>
<%


//*******此代码从a110-v.jsp中copy过来***begin**/
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
    if(lAccountGroupID == -1 && lClientID == -1 && lAccountID == -1)
        bLoop = true;
        //modified by fxzhang 2006-12-28  
    for(int i=1; i<=(bLoop?SETTConstant.AccountGroupType.getAllCode(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID).length:1); i++) 
    //for(int i=1; i<=(bLoop?SETTConstant.AccountGroupType.getCodeForZJ().length:1); i++) 
    
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
                    Collection accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, infoClient.getClientID(),lParentCorpID,self);
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
                Collection accountList = obaqad.getAccountInfoByClientID(obaqw, lAccountGroupID, lClientID,lParentCorpID,self);
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
//*******此代码从a110-v.jsp中copy过来***end**/
	if(list!=null && list.size()>0)
	 {
	    isNotnull = true;

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
        while (iterList != null && iterList.hasNext()) {
            OBAccountQueryAmountInfo info = (OBAccountQueryAmountInfo) iterList.next();
            Balance = obaqad.getBalanceByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID) + obaqad.getAmountByAccountID(obaqw, info.getOpenAccountID(), lAccountGroupID);
            if (lAccountTypeID == 0 || lAccountTypeID != info.getAccountTypeID()) {
                lAccountTypeID = info.getAccountTypeID();
                if(lLoanClientTypeID != 0) {
%>
              <tr>
                <td  valign="top" align="left" height="18" class="td-topright">&nbsp;</td>
               <td  valign="top" align="left" class="td-topright" height="18"> 
                  <div align="right"><%=strLoanClientTypeName==null?"&nbsp;":strLoanClientTypeName%></div>
                </td>
                <td  valign="top" align="right" class="td-topright" height="18">
                <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%></td>
                <td  valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td  valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
              </tr>
<%
                    SubSumBalance = 0.0;
                }
%>
              <tr>
                <td  valign="top" align="left" class="td-topright" height="18">
                    <span><%=SETTConstant.AccountType.getName(lAccountTypeID)%></span>
                </td>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
              </tr>

              <tr>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td valign="top" align="left" class="td-topright" height="18"> 
                  <div align="left"><%=info.getOpenAccountName()%></div>
                </td>
                <td valign="top" align="right" class="td-topright" height="18">
                <%=Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2)%></td>
                <td valign="top" align="left" class="td-topright" height="18"> 
                  <div align="left"><%="财务公司"%></div>
                </td>
                <td valign="top" align="center" class="td-topright" height="18">
                <%=NameRef.getNoLineAccountNo(info.getOpenAccountNo())%>&nbsp;
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
                <td  valign="top" align="left" height="18" class="td-topright">&nbsp;</td>
               <td  valign="top" align="left" class="td-topright" height="18"> 
                  <div align="right"><%=strLoanClientTypeName==null?"&nbsp;":strLoanClientTypeName%></div>
                </td>
                <td  valign="top" align="right" class="td-topright" height="18">
                <%=SubSumBalance==0.0?"0.00":DataFormat.formatDisabledAmount(SubSumBalance,2)%></td>
                <td  valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td  valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
              </tr>
<%
                    SubSumBalance = 0.0;
                }
%>
              <tr>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td valign="top" align="left" class="td-topright" height="18"> 
                  <div align="left"><%=info.getOpenAccountName()%></div>
                </td>
                <td valign="top" align="right" class="td-topright" height="18">
                <%=Balance==0.0?"0.00":DataFormat.formatDisabledAmount(Balance, 2)%></td>
                <td valign="top" align="left" class="td-topright" height="18"> 
                  <div align="left"><%="财务公司"%></div>
                </td>
                <td valign="top" align="center" class="td-topright" height="18">
                <%=NameRef.getNoLineAccountNo(info.getOpenAccountNo())%>&nbsp;
                </td>
              </tr>
<%
                lLoanClientTypeID = info.getLoanClientTypeID();
                strLoanClientTypeName = info.getLoanClientTypeName();
            }
            SubSumBalance += Balance;
            SumBalance += Balance;
            Total += Balance;

            
        }
%>
              <tr>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
               <td valign="top" align="left" class="td-topright" height="18" width="30%"> 
                  <div align="right"><%=SETTConstant.AccountGroupType.getName(lAccountGroupID)%>总计：</div>
                </td>
                <td valign="top" align="right" class="td-topright" height="18" width="70%">
                <%=Total==0.0?"0.00":DataFormat.formatDisabledAmount(Total,2)%></td>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
                <td valign="top" align="left" class="td-topright" height="18">&nbsp;</td>
              </tr>
<%
        Total = 0.0;
        }
    }
    // 断开连接
    obaqad.closeConn();
    //end of FOR
    if(!isNotnull)
    {
%>
		 	<tr> 
                <td height="18" valign="bottom" class="td-rightbottom">
                  <p align="center"><font size="2"></font></p>
                </td>
                <td width="50" valign="bottom" height="18" class="td-rightbottom"> 
                  <div align="center"></div>
                </td>
                <td valign="bottom" height="18" class="td-rightbottom"> 
                  <p align="center"><font size="2"></font></p>
                </td>
                <td valign="bottom" height="18" class="td-rightbottom"> 
                  <div align="center"></div>
                </td>
                <td valign="bottom" height="18" class="td-rightbottom"> 
                  <p align="center"><font size="2"></font></p>
                </td>
              </tr>

<%
    }
%>
            </table>
        </td>
    </tr>
                <tr>
                    <td align="center"  height="18"  colspan="5" >
                    查询日期：<%=queryDate%>  操作人：<%=Env.getClientName(sessionMng.m_lClientID)%> 
                    </td>
                </tr>    
</table>
</BODY>
</HTML>
<%
    } catch(IException ie) {
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
    OBHtml.showOBHomeEnd(out);
%>
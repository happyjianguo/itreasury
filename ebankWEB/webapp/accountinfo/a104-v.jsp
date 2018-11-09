<%--
/**
 页面名称 ：a104-v.jsp
 页面功能 : 华能集团公司及控股电厂存款每日变动情况结果页面下载
 作    者 ： kewen hu
 日    期 ： 2003-12-25
 特殊说明 ：实现操作说明：
 修改历史 ：
 */
--%>

<%@ page contentType="application/msexcel;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QCurrencyDepositInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QDailyAmountVaryDao"%>
<%@ page import="com.iss.itreasury.settlement.query.paraminfo.QueryDailyCapitalWhereInfo"%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/glass.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/Zoom.js"></script>

<%
	response.setContentType("application/msexcel;charset=UTF-8");
    response.setHeader("Content-Disposition",";filename=\treport.xls");
    //response.setHeader("Cache-Control","no-stored");
    //response.setHeader("Pragma","no-cache");
    //response.setDateHeader("Expires",0);
%>

<% String strContext = request.getContextPath();%>

<%
Log.print("\n\n*******进入页面--ebank/accountinfo/a104-v.jsp*******\n\n");
    //标题变量
	String strTitle = "[每日资金变动表和周报]";
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
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

        //定义页面控制变量
        String strActionResult="";
        String strAction="";

        //页面控制参数
        if (request.getAttribute("strActionResult") != null) {
            strActionResult = (String)request.getAttribute("strActionResult");
        }
        if (request.getAttribute("strAction") != null) {
            strAction = (String)request.getAttribute("strAction");
        }

    	Timestamp  tsQueryDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
        QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
        //定义变量
        String strTemp = null;
        //取得查询日期
        Timestamp queryDateFrom = null;
        strTemp = (String)request.getParameter("queryDateFrom");
        if (strTemp != null && strTemp.trim().length() > 0) {
            queryDateFrom = DataFormat.getDateTime(strTemp);
	        qdcwi.setDateFrom(queryDateFrom);
        } else {
            qdcwi.setDateFrom(tsQueryDate);
		}
        Timestamp queryDateTo = null;
        strTemp = (String)request.getParameter("queryDateTo");
        if (strTemp != null && strTemp.trim().length() > 0) {
            queryDateTo = DataFormat.getDateTime(strTemp);
        	qdcwi.setDateTo(queryDateTo);
        } else {
            qdcwi.setDateTo(tsQueryDate);
		}
        qdcwi.setOfficeID(sessionMng.m_lOfficeID);
        qdcwi.setCurrencyID(sessionMng.m_lCurrencyID);

        QDailyAmountVaryDao qavd = new QDailyAmountVaryDao();
        //取全部指定公司数据-成员单位合计
        Collection currencyDepositWeeklyInfo = qavd.getCurrencyDepositWeeklyInfo(qdcwi);
		Iterator iCurrencyDepositWeeklyInfo = null;
		if (currencyDepositWeeklyInfo != null) {
			iCurrencyDepositWeeklyInfo = currencyDepositWeeklyInfo.iterator();
		}
        //取集团控股电厂成员数据
        Collection groupHoldingWeeklyInfo = qavd.getGroupHoldingWeeklyInfo(qdcwi);
		Iterator iGroupHoldingWeeklyInfo = null;
		if (groupHoldingWeeklyInfo != null) {
			iGroupHoldingWeeklyInfo = groupHoldingWeeklyInfo.iterator();
		}
        //取开发控股电厂成员数据
        Collection empolderHoldingWeeklyInfo = qavd.getEmpolderHoldingWeeklyInfo(qdcwi);
		Iterator iEmpolderHoldingWeeklyInfo = null;
		if (empolderHoldingWeeklyInfo != null) {
			iEmpolderHoldingWeeklyInfo = empolderHoldingWeeklyInfo.iterator();
		}
        //取集团电厂成员数据
        Collection groupElectricWeeklyInfo = qavd.getGroupElectricWeeklyInfo(qdcwi);
		Iterator iGroupElectricWeeklyInfo = null;
		if (groupElectricWeeklyInfo != null) {
			iGroupElectricWeeklyInfo = groupElectricWeeklyInfo.iterator();
		}
		//取开发电厂成员数据
        Collection empolderElectricWeeklyInfo = qavd.getEmpolderElectricWeeklyInfo(qdcwi);
		Iterator iEmpolderElectricWeeklyInfo = null;
		if (empolderElectricWeeklyInfo != null) {
			iEmpolderElectricWeeklyInfo = empolderElectricWeeklyInfo.iterator();
		}
		//取股份控股电厂成员数据
        Collection holdingElectricWeeklyInfo = qavd.getHoldingElectricWeeklyInfo(qdcwi);
		Iterator iHoldingElectricWeeklyInfo = null;
		if (holdingElectricWeeklyInfo != null) {
			iHoldingElectricWeeklyInfo = holdingElectricWeeklyInfo.iterator();
		}
        //取子公司合计数据通过科目表
        QCurrencyDepositInfo allSubClientWeeklyInfo = qavd.getAllSubClientWeeklyInfo(qdcwi);
        //断开连接
        qavd.CloseDailyAmountVaryDao();
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
<BODY  text="#000000" bgcolor="#FFFFFF">
  <TABLE border=0 class=top height=100 width="99%" >
    <TBODY>
      <TR>
        <TD class="td-rightbottom" align="center" ><B><font size="5">人民币存款周报</font></B> 
        </TD>
      </TR>
      <TR>
        <TD>
        <TABLE align=center border=0 cellspacing="0" cellpadding="3" class=table1>
        <TR>
        <TD class="td-rightbottom"><font size="3">单位：<%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%>
        </font>
        </TD>
        <TD class="td-rightbottom"></TD>
        <TD class="td-rightbottom" align="center"><font size="3">
         <%=DataFormat.getDateString(qdcwi.getDateFrom())%>---<%=DataFormat.getDateString(qdcwi.getDateTo())%>
        </font>
        </TD>
        <TD class="td-rightbottom" colspan="5"></TD>
        <TD class="td-rightbottom"><font size="3">金额单位：万元</font>
        </TD>
        </TR>
        </TABLE>
        </TD>
      </TR>
      <TR> 
        <TD>
          <TABLE align=center border=0 cellspacing="0" cellpadding="3" class=table1 height=220   width="99%">
            <TR> 
              <TD rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong>序<BR>号</strong></TD>
              <td rowspan="2" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                存款单位名称</strong></td>
              <TD rowspan="2" align="center" nowrap class="td-rightbottom"><strong>上周存款余额</strong></TD>
              <td colspan="2" align="center" nowrap class="td-rightbottom"><strong>本周发生额</strong></td>
              <td colspan="4" align="center" nowrap class="td-rightbottom"><strong>本周存款余额</strong></td>
            </TR>
            <TR> 
              <TD align="center" nowrap class="td-rightbottom"><strong>存入</strong></TD>
              <td align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>活期存款</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>通知存款</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>定期存款</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>合计</strong></td>
            </TR>
<%
        long No=0;                      // 序号
        String DepositCorp="";          // 存款单位
        double PreWeeklyBalance=0.0;    // 上周存款余额
        double WeeklyHandIn=0.0;        // 本周-存入
        double WeeklyCost=0.0;          // 本周-支取
        double CurrencyBalance=0.0;     // 本周存款余额-活期存款
        double FixedBalance=0.0;        // 本周存款余额-定期存款
        double NotifyBalance=0.0;       // 本周存款余额-通知存款
        double SumBalance=0.0;          // 合计
        double SumPreWeeklyBalance=0.0; // 上周存款余额
        double SumWeeklyHandIn=0.0;     // 本周-存入
        double SumWeeklyCost=0.0;       // 本周-支取
        double SumCurrencyBalance=0.0;  // 本周存款余额-活期存款
        double SumFixedBalance=0.0;     // 本周存款余额-定期存款
        double SumNotifyBalance=0.0;    // 本周存款余额-通知存款
        double SumSumBalance=0.0;       // 合计
    QCurrencyDepositInfo qcdi = null;
    if (iCurrencyDepositWeeklyInfo != null || iGroupHoldingWeeklyInfo != null || 
		iEmpolderHoldingWeeklyInfo != null || iGroupElectricWeeklyInfo != null || 
		iEmpolderElectricWeeklyInfo != null || iHoldingElectricWeeklyInfo != null ||
		allSubClientWeeklyInfo != null) {
        //取全部指定公司数据-成员单位合计
        if (iCurrencyDepositWeeklyInfo != null) {
            while (iCurrencyDepositWeeklyInfo.hasNext()) {
                qcdi = (QCurrencyDepositInfo) iCurrencyDepositWeeklyInfo.next();
                No=qcdi.getNo();                                                // 序号
                DepositCorp=qcdi.getDepositCorp();                              // 存款单位
                PreWeeklyBalance=PreWeeklyBalance+qcdi.getPreWeeklyBalance();   // 上周存款余额
                WeeklyHandIn=WeeklyHandIn+qcdi.getWeeklyHandIn();               // 本周-存入
                WeeklyCost=WeeklyCost+qcdi.getWeeklyCost();                     // 本周-支取
                CurrencyBalance=CurrencyBalance+qcdi.getCurrencyBalance();      // 本周存款余额-活期存款
                FixedBalance=FixedBalance+qcdi.getFixedBalance();               // 本周存款余额-定期存款
                NotifyBalance=NotifyBalance+qcdi.getNotifyBalance();            // 本周存款余额-通知存款
                SumBalance=SumBalance+qcdi.getSumBalance();                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%>
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%>
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
            }
        //取全部指定公司数据-成员单位合计--合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="成员单位合计"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(PreWeeklyBalance)%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyHandIn)%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyCost)%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(CurrencyBalance)%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(NotifyBalance)%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(FixedBalance)%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(SumBalance)%> 
              </td>
            </TR>
<%
            SumPreWeeklyBalance=SumPreWeeklyBalance+PreWeeklyBalance;   // 上周存款余额
            SumWeeklyHandIn=SumWeeklyHandIn+WeeklyHandIn;               // 本周-存入
            SumWeeklyCost=SumWeeklyCost+WeeklyCost;                     // 本周-支取
            SumCurrencyBalance=SumCurrencyBalance+CurrencyBalance;      // 本周存款余额-活期存款
            SumFixedBalance=SumFixedBalance+FixedBalance;               // 本周存款余额-定期存款
            SumNotifyBalance=SumNotifyBalance+NotifyBalance;            // 本周存款余额-通知存款
            SumSumBalance=SumSumBalance+SumBalance;                     // 合计
            PreWeeklyBalance=0.0; // 上周存款余额
            WeeklyHandIn=0.0;     // 本周-存入
            WeeklyCost=0.0;       // 本周-支取
            CurrencyBalance=0.0;  // 本周存款余额-活期存款
            FixedBalance=0.0;     // 本周存款余额-定期存款
            NotifyBalance=0.0;    // 本周存款余额-通知存款
            SumBalance=0.0;       // 合计
        }
        //取集团控股电厂成员数据
        if (iGroupHoldingWeeklyInfo != null) {
            while (iGroupHoldingWeeklyInfo.hasNext()) {
                qcdi = (QCurrencyDepositInfo) iGroupHoldingWeeklyInfo.next();
                No=qcdi.getNo();                                                // 序号
                DepositCorp=qcdi.getDepositCorp();                              // 存款单位
                PreWeeklyBalance=PreWeeklyBalance+qcdi.getPreWeeklyBalance();   // 上周存款余额
                WeeklyHandIn=WeeklyHandIn+qcdi.getWeeklyHandIn();               // 本周-存入
                WeeklyCost=WeeklyCost+qcdi.getWeeklyCost();                     // 本周-支取
                CurrencyBalance=CurrencyBalance+qcdi.getCurrencyBalance();      // 本周存款余额-活期存款
                FixedBalance=FixedBalance+qcdi.getFixedBalance();               // 本周存款余额-定期存款
                NotifyBalance=NotifyBalance+qcdi.getNotifyBalance();            // 本周存款余额-通知存款
                SumBalance=SumBalance+qcdi.getSumBalance();                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
            }
        //取集团控股电厂成员数据--合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="集团控股电厂小计"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(PreWeeklyBalance)%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyHandIn)%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyCost)%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(CurrencyBalance)%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(NotifyBalance)%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(FixedBalance)%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(SumBalance)%> 
              </td>
            </TR>
<%
            SumPreWeeklyBalance=SumPreWeeklyBalance+PreWeeklyBalance;   // 上周存款余额
            SumWeeklyHandIn=SumWeeklyHandIn+WeeklyHandIn;               // 本周-存入
            SumWeeklyCost=SumWeeklyCost+WeeklyCost;                     // 本周-支取
            SumCurrencyBalance=SumCurrencyBalance+CurrencyBalance;      // 本周存款余额-活期存款
            SumFixedBalance=SumFixedBalance+FixedBalance;               // 本周存款余额-定期存款
            SumNotifyBalance=SumNotifyBalance+NotifyBalance;            // 本周存款余额-通知存款
            SumSumBalance=SumSumBalance+SumBalance;                     // 合计
            PreWeeklyBalance=0.0; // 上周存款余额
            WeeklyHandIn=0.0;     // 本周-存入
            WeeklyCost=0.0;       // 本周-支取
            CurrencyBalance=0.0;  // 本周存款余额-活期存款
            FixedBalance=0.0;     // 本周存款余额-定期存款
            NotifyBalance=0.0;    // 本周存款余额-通知存款
            SumBalance=0.0;       // 合计
        }
        //取开发控股电厂成员数据
        if (iEmpolderHoldingWeeklyInfo != null) {
            while (iEmpolderHoldingWeeklyInfo.hasNext()) {
                qcdi = (QCurrencyDepositInfo) iEmpolderHoldingWeeklyInfo.next();
                No=qcdi.getNo();                                                // 序号
                DepositCorp=qcdi.getDepositCorp();                              // 存款单位
                PreWeeklyBalance=PreWeeklyBalance+qcdi.getPreWeeklyBalance();   // 上周存款余额
                WeeklyHandIn=WeeklyHandIn+qcdi.getWeeklyHandIn();               // 本周-存入
                WeeklyCost=WeeklyCost+qcdi.getWeeklyCost();                     // 本周-支取
                CurrencyBalance=CurrencyBalance+qcdi.getCurrencyBalance();      // 本周存款余额-活期存款
                FixedBalance=FixedBalance+qcdi.getFixedBalance();               // 本周存款余额-定期存款
                NotifyBalance=NotifyBalance+qcdi.getNotifyBalance();            // 本周存款余额-通知存款
                SumBalance=SumBalance+qcdi.getSumBalance();                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
            }
        	//取开发控股电厂成员数据--合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="开发控股电厂小计"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(PreWeeklyBalance)%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyHandIn)%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyCost)%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(CurrencyBalance)%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(NotifyBalance)%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(FixedBalance)%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(SumBalance)%> 
              </td>
            </TR>
<%
            SumPreWeeklyBalance=SumPreWeeklyBalance+PreWeeklyBalance;   // 上周存款余额
            SumWeeklyHandIn=SumWeeklyHandIn+WeeklyHandIn;               // 本周-存入
            SumWeeklyCost=SumWeeklyCost+WeeklyCost;                     // 本周-支取
            SumCurrencyBalance=SumCurrencyBalance+CurrencyBalance;      // 本周存款余额-活期存款
            SumFixedBalance=SumFixedBalance+FixedBalance;               // 本周存款余额-定期存款
            SumNotifyBalance=SumNotifyBalance+NotifyBalance;            // 本周存款余额-通知存款
            SumSumBalance=SumSumBalance+SumBalance;                     // 合计
            PreWeeklyBalance=0.0; // 上周存款余额
            WeeklyHandIn=0.0;     // 本周-存入
            WeeklyCost=0.0;       // 本周-支取
            CurrencyBalance=0.0;  // 本周存款余额-活期存款
            FixedBalance=0.0;     // 本周存款余额-定期存款
            NotifyBalance=0.0;    // 本周存款余额-通知存款
            SumBalance=0.0;       // 合计
        }
        //取集团电厂成员数据和取开发电厂成员数据
        if (iGroupElectricWeeklyInfo != null || iEmpolderElectricWeeklyInfo != null) {
            while (iGroupElectricWeeklyInfo != null && iGroupElectricWeeklyInfo.hasNext()) {
                qcdi = (QCurrencyDepositInfo) iGroupElectricWeeklyInfo.next();
                No=qcdi.getNo();                                                // 序号
                DepositCorp=qcdi.getDepositCorp();                              // 存款单位
                PreWeeklyBalance=PreWeeklyBalance+qcdi.getPreWeeklyBalance();   // 上周存款余额
                WeeklyHandIn=WeeklyHandIn+qcdi.getWeeklyHandIn();               // 本周-存入
                WeeklyCost=WeeklyCost+qcdi.getWeeklyCost();                     // 本周-支取
                CurrencyBalance=CurrencyBalance+qcdi.getCurrencyBalance();      // 本周存款余额-活期存款
                FixedBalance=FixedBalance+qcdi.getFixedBalance();               // 本周存款余额-定期存款
                NotifyBalance=NotifyBalance+qcdi.getNotifyBalance();            // 本周存款余额-通知存款
                SumBalance=SumBalance+qcdi.getSumBalance();                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
            }
            while (iEmpolderElectricWeeklyInfo != null && iEmpolderElectricWeeklyInfo.hasNext()) {
                qcdi = (QCurrencyDepositInfo) iEmpolderElectricWeeklyInfo.next();
                No=qcdi.getNo();                                                // 序号
                DepositCorp=qcdi.getDepositCorp();                              // 存款单位
                PreWeeklyBalance=PreWeeklyBalance+qcdi.getPreWeeklyBalance();   // 上周存款余额
                WeeklyHandIn=WeeklyHandIn+qcdi.getWeeklyHandIn();               // 本周-存入
                WeeklyCost=WeeklyCost+qcdi.getWeeklyCost();                     // 本周-支取
                CurrencyBalance=CurrencyBalance+qcdi.getCurrencyBalance();      // 本周存款余额-活期存款
                FixedBalance=FixedBalance+qcdi.getFixedBalance();               // 本周存款余额-定期存款
                NotifyBalance=NotifyBalance+qcdi.getNotifyBalance();            // 本周存款余额-通知存款
                SumBalance=SumBalance+qcdi.getSumBalance();                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
            }
        	//取股份代管小计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="股份代管小计"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(PreWeeklyBalance)%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyHandIn)%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyCost)%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(CurrencyBalance)%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(NotifyBalance)%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(FixedBalance)%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(SumBalance)%> 
              </td>
            </TR>
<%
            SumPreWeeklyBalance=SumPreWeeklyBalance+PreWeeklyBalance;   // 上周存款余额
            SumWeeklyHandIn=SumWeeklyHandIn+WeeklyHandIn;               // 本周-存入
            SumWeeklyCost=SumWeeklyCost+WeeklyCost;                     // 本周-支取
            SumCurrencyBalance=SumCurrencyBalance+CurrencyBalance;      // 本周存款余额-活期存款
            SumFixedBalance=SumFixedBalance+FixedBalance;               // 本周存款余额-定期存款
            SumNotifyBalance=SumNotifyBalance+NotifyBalance;            // 本周存款余额-通知存款
            SumSumBalance=SumSumBalance+SumBalance;                     // 合计
            PreWeeklyBalance=0.0; // 上周存款余额
            WeeklyHandIn=0.0;     // 本周-存入
            WeeklyCost=0.0;       // 本周-支取
            CurrencyBalance=0.0;  // 本周存款余额-活期存款
            FixedBalance=0.0;     // 本周存款余额-定期存款
            NotifyBalance=0.0;    // 本周存款余额-通知存款
            SumBalance=0.0;       // 合计
        }
        //取股份控股电厂成员数据
        if (iHoldingElectricWeeklyInfo != null) {
            while (iHoldingElectricWeeklyInfo.hasNext()) {
                qcdi = (QCurrencyDepositInfo) iHoldingElectricWeeklyInfo.next();
                No=qcdi.getNo();                                                // 序号
                DepositCorp=qcdi.getDepositCorp();                              // 存款单位
                PreWeeklyBalance=PreWeeklyBalance+qcdi.getPreWeeklyBalance();   // 上周存款余额
                WeeklyHandIn=WeeklyHandIn+qcdi.getWeeklyHandIn();               // 本周-存入
                WeeklyCost=WeeklyCost+qcdi.getWeeklyCost();                     // 本周-支取
                CurrencyBalance=CurrencyBalance+qcdi.getCurrencyBalance();      // 本周存款余额-活期存款
                FixedBalance=FixedBalance+qcdi.getFixedBalance();               // 本周存款余额-定期存款
                NotifyBalance=NotifyBalance+qcdi.getNotifyBalance();            // 本周存款余额-通知存款
                SumBalance=SumBalance+qcdi.getSumBalance();                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
            }
        //取股份控股电厂成员数据--合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="股份控股电厂小计"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(PreWeeklyBalance)%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyHandIn)%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyCost)%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(CurrencyBalance)%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(NotifyBalance)%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(FixedBalance)%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(SumBalance)%> 
              </td>
            </TR>
<%
            SumPreWeeklyBalance=SumPreWeeklyBalance+PreWeeklyBalance;   // 上周存款余额
            SumWeeklyHandIn=SumWeeklyHandIn+WeeklyHandIn;               // 本周-存入
            SumWeeklyCost=SumWeeklyCost+WeeklyCost;                     // 本周-支取
            SumCurrencyBalance=SumCurrencyBalance+CurrencyBalance;      // 本周存款余额-活期存款
            SumFixedBalance=SumFixedBalance+FixedBalance;               // 本周存款余额-定期存款
            SumNotifyBalance=SumNotifyBalance+NotifyBalance;            // 本周存款余额-通知存款
            SumSumBalance=SumSumBalance+SumBalance;                     // 合计
            PreWeeklyBalance=0.0; // 上周存款余额
            WeeklyHandIn=0.0;     // 本周-存入
            WeeklyCost=0.0;       // 本周-支取
            CurrencyBalance=0.0;  // 本周存款余额-活期存款
            FixedBalance=0.0;     // 本周存款余额-定期存款
            NotifyBalance=0.0;    // 本周存款余额-通知存款
            SumBalance=0.0;       // 合计
        }
        //其他及合计(取子公司合计数据通过科目表)
        if (allSubClientWeeklyInfo != null) {
                qcdi = allSubClientWeeklyInfo;
                DepositCorp=qcdi.getDepositCorp();                                 // 存款单位
                PreWeeklyBalance=qcdi.getPreWeeklyBalance()-SumPreWeeklyBalance;   // 上周存款余额
                WeeklyHandIn=qcdi.getWeeklyHandIn()-SumWeeklyHandIn;               // 本周-存入
                WeeklyCost=qcdi.getWeeklyCost()-SumWeeklyCost;                     // 本周-支取
                CurrencyBalance=qcdi.getCurrencyBalance()-SumCurrencyBalance;      // 本周存款余额-活期存款
                FixedBalance=qcdi.getFixedBalance()-SumFixedBalance;               // 本周存款余额-定期存款
                NotifyBalance=qcdi.getNotifyBalance()-SumNotifyBalance;            // 本周存款余额-通知存款
                SumBalance=qcdi.getSumBalance()-SumSumBalance;                     // 合计
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="其他"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(PreWeeklyBalance)%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyHandIn)%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(WeeklyCost)%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(CurrencyBalance)%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(NotifyBalance)%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(FixedBalance)%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(SumBalance)%> 
              </td>
            </TR>

            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="合计"%></strong></td>
              <!--上周存款余额-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getPreWeeklyBalance())%> 
              </td>
              <!--本周-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyHandIn())%> 
              </td>
              <!--本周-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getWeeklyCost())%> 
              </td>
              <!--本周存款余额-活期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getCurrencyBalance())%> 
              </td>
              <!--本周存款余额-通知存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getNotifyBalance())%> 
              </td>
              <!--本周存款余额-定期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getFixedBalance())%> 
              </td>
              <!--合计-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.amountTrimForExcel(qcdi.getSumBalance())%> 
              </td>
            </TR>
<%
        }
    } else {
%>
            <TR> 
              <td width="8%"  height="20" colspan="2" class="td-topright"><strong><%="合计"%></strong></td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
            </TR>
<%
    }
%>
    </TABLE>
    </TD>
    </TR>
      <TR>
            <TD height=20 vAlign=top width="100%">
                <TABLE align=center border=0 cellspacing="0" cellpadding="3" class=table1 height=22 width="97%">
                    <TBODY>
                    <TR vAlign=center>
                        <TD height=25 width="8%" nowrap><FONT size="2">开户银行:</FONT></TD>
                        <TD height=25 width="8%" nowrap><FONT size="2"><%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%></FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><FONT size="2">部门经理:</FONT></TD>
                        <TD height=25 width="5%" nowrap><FONT size="2"><%="&nbsp;"%></FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="6%" nowrap><FONT size="2">制单:</FONT></TD>
                        <TD height=25 width="6%" nowrap><FONT size="2"><%=sessionMng.m_strUserName%><FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                    </TR>
                    </TBODY>
                </TABLE>
            </TD>
        </TR>
    </TBODY>
  </TABLE>
</BODY>
</HTML>
<%
        } catch( Exception exp ) {
            Log.print(exp.getMessage());
        }
%>
<%--
/**
 页面名称 ：a103-v.jsp
 页面功能 : 华能集团公司及下属电厂存款每日变动情况表结果页面下载
 作    者 ： kewen hu
 日    期 ： 2003-12-20
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
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryOuterAccountInfo"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryInnerAccountInfo"%>
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
Log.print("\n\n*******进入页面--ebank/accountinfo/a103-v.jsp*******\n\n");
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
        if (request.getParameter("strActionResult") != null) {
            strActionResult = (String)request.getParameter("strActionResult");
        }
        if (request.getParameter("strAction") != null) {
            strAction = (String)request.getParameter("strAction");
        }

    	Timestamp  tsQueryDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
        //定义变量
        String strTemp = null;
        //取得查询日期
        Timestamp queryDate = null;
        QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
        strTemp = (String)request.getParameter("queryDate");
        if (strTemp != null && strTemp.trim().length() > 0) {
            queryDate = DataFormat.getDateTime(strTemp);
        } else {
            qdcwi.setQueryDate(tsQueryDate);
		}
        qdcwi.setQueryDate(queryDate);
        qdcwi.setOfficeID(sessionMng.m_lOfficeID);
        qdcwi.setCurrencyID(sessionMng.m_lCurrencyID);

        QDailyAmountVaryDao qavd= new QDailyAmountVaryDao();
		//取华能集团公司的结果集
		QueryInnerAccountInfo groupCompanyInfo = qavd.getGroupCompanyInfo(qdcwi);
		//取集团全资电厂记录的结果集
		Collection groupCapitalElectricInfo = qavd.getGroupCapitalElectricInfo(qdcwi);
		Iterator iGroupCapitalElectricInfo = null;
		if (groupCapitalElectricInfo != null) {
			iGroupCapitalElectricInfo = groupCapitalElectricInfo.iterator();
		}
		//取华能国际电力开发公司的结果集
		QueryInnerAccountInfo empolderCompanyInfo = qavd.getEmpolderCompanyInfo(qdcwi);
		//取开发全资电厂记录的结果集
		Collection empolderCapitalElectricInfo = qavd.getEmpolderCapitalElectricInfo(qdcwi);
		Iterator iEmpolderCapitalElectricInfo = null;
		if (empolderCapitalElectricInfo != null) {
			iEmpolderCapitalElectricInfo = empolderCapitalElectricInfo.iterator();
		}
		//取集团控股电厂记录的结果集
		Collection groupHoldingElectricInfo = qavd.getGroupHoldingElectricInfo(qdcwi);
		Iterator iGroupHoldingElectricInfo = null;
		if (groupHoldingElectricInfo != null) {
			iGroupHoldingElectricInfo = groupHoldingElectricInfo.iterator();
		}
		//取开发控股电厂记录的结果集
		Collection empolderHoldingElectricInfo = qavd.getEmpolderHoldingElectricInfo(qdcwi);
		Iterator iEmpolderHoldingElectricInfo = null;
		if (empolderHoldingElectricInfo != null) {
			iEmpolderHoldingElectricInfo = empolderHoldingElectricInfo.iterator();
		}
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
        <TD colSpan=2 height=11 vAlign=top>
          <TABLE align=center border=0 cellspacing="0" cellpadding="3" class=table1 height=220 width="99%">
              <TR>
        		<TD height=2 colspan=15 align="center" class=td-rightbottom><B><font size="3"><%=Env.getClientName()%>及下属电厂存款每日变动情况表</font></B></TD>
              </TR>
              <TR> 
                <TD height=2 colspan=15 align="center" class=td-rightbottom><font size="3">
                <%=DataFormat.getDateString(qdcwi.getQueryDate())%></font></TD>
              </TR>
            <TR> 
              <TD height="40" rowspan="2" align="center" valign="middle" class=td-rightbottom><strong>序<BR>号</strong></TD>
              <td height="40" rowspan="2" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                存款单位</strong></td>
              <TD height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>本月累计</strong></TD>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>当日变动</strong></td>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>本月合计</strong></td>
              <td height="40" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                当期存款</strong></td>
              <td height="40" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                上月余额</strong></td>
              <td height="40" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                当日余额</strong></td>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>本月预算</strong></td>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>与预算差额</strong></td>
            </TR>
            <TR> 
              <TD width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></TD>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
            </TR>
<%
            long   No=0;                // 序号
            String DepositCorp="";      // 存款单位
            double MonthHandIn=0.0;     // 本月累计-存入
            double MonthCost=0.0;       // 本月累计-支取
            double DailyHandIn=0.0;     // 当日变动-存入
            double DailyCost=0.0;       // 当日变动-支取
            double MonthSumHandIn=0.0;  // 本月合计-存入
            double MonthSumCost=0.0;    // 本月合计-支取
            double NowDeposit=0.0;      // 当期存款
            double PreMonthBalance=0.0; // 上月余额
            double NowDayBalance=0.0;   // 当日余额
            double NowMonthHandIn=0.0;  // 本月预算-存入
            double NowMonthCost=0.0;    // 本月预算-支取
            double MarginHandIn=0.0;    // 与预算差额-上交
            double MarginCost=0.0;      // 与预算差额-支取

            double SumMonthHandIn=0.0;     // 本月累计-存入
            double SumMonthCost=0.0;       // 本月累计-支取
            double SumDailyHandIn=0.0;     // 当日变动-存入
            double SumDailyCost=0.0;       // 当日变动-支取
            double SumMonthSumHandIn=0.0;  // 本月合计-存入
            double SumMonthSumCost=0.0;    // 本月合计-支取
            double SumNowDeposit=0.0;      // 当期存款
            double SumPreMonthBalance=0.0; // 上月余额
            double SumNowDayBalance=0.0;   // 当日余额
            double SumNowMonthHandIn=0.0;  // 本月预算-存入
            double SumNowMonthCost=0.0;    // 本月预算-支取
            double SumMarginHandIn=0.0;    // 与预算差额-上交
            double SumMarginCost=0.0;      // 与预算差额-支取
        //关系运算列
%>
            <TR>
              <td  height="20"  align="center" nowrap class="td-topright">&nbsp;</td>
              <td  height="20"  align="center" nowrap class="td-topright">&nbsp;</td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">1</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">2</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">3</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">4</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">5=1+3</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">6=2+4</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">7=5-6</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">8</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">9=7+8</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">10</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">11</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">12=10-5</font></td>
              <td  height="20" align="center" nowrap class="td-topright"><font size="3">13=11-6</font></td>
            </TR>
<%
    //取华能集团公司的结果集
    QueryInnerAccountInfo qiai = null;
    if (groupCompanyInfo != null) {
                qiai = groupCompanyInfo;
                No=qiai.getNo();                                            // 序号
                DepositCorp=qiai.getDepositCorp();                          // 存款单位
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                MonthCost=MonthCost+qiai.getMonthCost();                    // 本月累计-支取
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                DailyCost=DailyCost+qiai.getDailyCost();                    // 当日变动-支取
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // 当期存款
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// 本月预算-存入
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// 本月预算-支取
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// 与预算差额-上交
            	MarginCost=MarginCost+qiai.getMarginCost();					// 与预算差额-支取

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // 本月累计-支取
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // 当日变动-支取
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // 当期存款
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // 本月预算-存入
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // 本月预算-支取
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // 与预算差额-上交
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // 与预算差额-支取
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
        }
		//取集团全资电厂记录的结果集
        if (iGroupCapitalElectricInfo != null) {
            while (iGroupCapitalElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iGroupCapitalElectricInfo.next();
                No=qiai.getNo();                                            // 序号
                DepositCorp=qiai.getDepositCorp();                          // 存款单位
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                MonthCost=MonthCost+qiai.getMonthCost();                    // 本月累计-支取
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                DailyCost=DailyCost+qiai.getDailyCost();                    // 当日变动-支取
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // 当期存款
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// 本月预算-存入
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// 本月预算-支取
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// 与预算差额-上交
            	MarginCost=MarginCost+qiai.getMarginCost();					// 与预算差额-支取

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // 本月累计-支取
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // 当日变动-支取
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // 当期存款
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // 本月预算-存入
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // 本月预算-支取
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // 与预算差额-上交
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // 与预算差额-支取
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
            }
        }
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="集团本部及全资电厂小计"%></strong></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // 本月累计-存入
                MonthCost=0.0;      // 本月累计-支取
                DailyHandIn=0.0;    // 当日变动-存入
                DailyCost=0.0;      // 当日变动-支取
                MonthSumHandIn=0.0; // 本月合计-存入
                MonthSumCost=0.0;   // 本月合计-支取
                NowDeposit=0.0;     // 当期存款
                PreMonthBalance=0.0;// 上月余额
                NowDayBalance=0.0;  // 当日余额
            	NowMonthHandIn=0.0;	// 本月预算-存入
            	NowMonthCost=0.0;	// 本月预算-支取
            	MarginHandIn=0.0;	// 与预算差额-上交
            	MarginCost=0.0;		// 与预算差额-支取
		//取华能国际电力开发公司的结果集
        if (empolderCompanyInfo != null) {
                qiai = empolderCompanyInfo;
                No=qiai.getNo();                                            // 序号
                DepositCorp=qiai.getDepositCorp();                          // 存款单位
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                MonthCost=MonthCost+qiai.getMonthCost();                    // 本月累计-支取
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                DailyCost=DailyCost+qiai.getDailyCost();                    // 当日变动-支取
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // 当期存款
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// 本月预算-存入
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// 本月预算-支取
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// 与预算差额-上交
            	MarginCost=MarginCost+qiai.getMarginCost();					// 与预算差额-支取

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // 本月累计-支取
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // 当日变动-支取
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // 当期存款
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // 本月预算-存入
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // 本月预算-支取
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // 与预算差额-上交
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // 与预算差额-支取
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
        }
		//取开发全资电厂记录的结果集
        if (iEmpolderCapitalElectricInfo != null) {
            while (iEmpolderCapitalElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iEmpolderCapitalElectricInfo.next();
                No=qiai.getNo();                                            // 序号
                DepositCorp=qiai.getDepositCorp();                          // 存款单位
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                MonthCost=MonthCost+qiai.getMonthCost();                    // 本月累计-支取
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                DailyCost=DailyCost+qiai.getDailyCost();                    // 当日变动-支取
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // 当期存款
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// 本月预算-存入
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// 本月预算-支取
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// 与预算差额-上交
            	MarginCost=MarginCost+qiai.getMarginCost();					// 与预算差额-支取

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // 本月累计-支取
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // 当日变动-支取
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // 当期存款
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // 本月预算-存入
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // 本月预算-支取
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // 与预算差额-上交
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // 与预算差额-支取
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
            }
        }
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="开发本部及全资电厂小计"%></strong></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // 本月累计-存入
                MonthCost=0.0;      // 本月累计-支取
                DailyHandIn=0.0;    // 当日变动-存入
                DailyCost=0.0;      // 当日变动-支取
                MonthSumHandIn=0.0; // 本月合计-存入
                MonthSumCost=0.0;   // 本月合计-支取
                NowDeposit=0.0;     // 当期存款
                PreMonthBalance=0.0;// 上月余额
                NowDayBalance=0.0;  // 当日余额
            	NowMonthHandIn=0.0;	// 本月预算-存入
            	NowMonthCost=0.0;	// 本月预算-支取
            	MarginHandIn=0.0;	// 与预算差额-上交
            	MarginCost=0.0;		// 与预算差额-支取
		//取集团控股电厂记录的结果集
        if (iGroupHoldingElectricInfo != null) {
            while (iGroupHoldingElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iGroupHoldingElectricInfo.next();
                No=qiai.getNo();                                            // 序号
                DepositCorp=qiai.getDepositCorp();                          // 存款单位
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                MonthCost=MonthCost+qiai.getMonthCost();                    // 本月累计-支取
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                DailyCost=DailyCost+qiai.getDailyCost();                    // 当日变动-支取
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // 当期存款
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// 本月预算-存入
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// 本月预算-支取
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// 与预算差额-上交
            	MarginCost=MarginCost+qiai.getMarginCost();					// 与预算差额-支取

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // 本月累计-支取
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // 当日变动-支取
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // 当期存款
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // 本月预算-存入
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // 本月预算-支取
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // 与预算差额-上交
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // 与预算差额-支取
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
            }
        }
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="集团控股电厂小计"%></strong></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // 本月累计-存入
                MonthCost=0.0;      // 本月累计-支取
                DailyHandIn=0.0;    // 当日变动-存入
                DailyCost=0.0;      // 当日变动-支取
                MonthSumHandIn=0.0; // 本月合计-存入
                MonthSumCost=0.0;   // 本月合计-支取
                NowDeposit=0.0;     // 当期存款
                PreMonthBalance=0.0;// 上月余额
                NowDayBalance=0.0;  // 当日余额
            	NowMonthHandIn=0.0;	// 本月预算-存入
            	NowMonthCost=0.0;	// 本月预算-支取
            	MarginHandIn=0.0;	// 与预算差额-上交
            	MarginCost=0.0;		// 与预算差额-支取
		//取开发控股电厂记录的结果集
        if (iEmpolderHoldingElectricInfo != null) {
            while (iEmpolderHoldingElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iEmpolderHoldingElectricInfo.next();
                No=qiai.getNo();                                            // 序号
                DepositCorp=qiai.getDepositCorp();                          // 存款单位
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                MonthCost=MonthCost+qiai.getMonthCost();                    // 本月累计-支取
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                DailyCost=DailyCost+qiai.getDailyCost();                    // 当日变动-支取
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // 当期存款
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// 本月预算-存入
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// 本月预算-支取
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// 与预算差额-上交
            	MarginCost=MarginCost+qiai.getMarginCost();					// 与预算差额-支取

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // 本月累计-存入
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // 本月累计-支取
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // 当日变动-存入
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // 当日变动-支取
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // 本月合计-存入
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // 本月合计-支取
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // 当期存款
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // 上月余额
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // 当日余额
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // 本月预算-存入
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // 本月预算-支取
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // 与预算差额-上交
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // 与预算差额-支取
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // 本月累计-存入
                MonthCost=0.0;      // 本月累计-支取
                DailyHandIn=0.0;    // 当日变动-存入
                DailyCost=0.0;      // 当日变动-支取
                MonthSumHandIn=0.0; // 本月合计-存入
                MonthSumCost=0.0;   // 本月合计-支取
                NowDeposit=0.0;     // 当期存款
                PreMonthBalance=0.0;// 上月余额
                NowDayBalance=0.0;  // 当日余额
            	NowMonthHandIn=0.0;	// 本月预算-存入
            	NowMonthCost=0.0;	// 本月预算-支取
            	MarginHandIn=0.0;	// 与预算差额-上交
            	MarginCost=0.0;		// 与预算差额-支取
            }
        }
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="开发控股电厂小计"%></strong></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // 本月累计-存入
                MonthCost=0.0;      // 本月累计-支取
                DailyHandIn=0.0;    // 当日变动-存入
                DailyCost=0.0;      // 当日变动-支取
                MonthSumHandIn=0.0; // 本月合计-存入
                MonthSumCost=0.0;   // 本月合计-支取
                NowDeposit=0.0;     // 当期存款
                PreMonthBalance=0.0;// 上月余额
                NowDayBalance=0.0;  // 当日余额
            	NowMonthHandIn=0.0;	// 本月预算-存入
            	NowMonthCost=0.0;	// 本月预算-支取
            	MarginHandIn=0.0;	// 与预算差额-上交
            	MarginCost=0.0;		// 与预算差额-支取
    		//合计余额
 %>
            <TR> 
              <!--序号-->
              <!--存款单位-->
              <td width="8%"  height="20" colspan="2" align="center" nowrap class="td-topright">
              <strong><%="合计"%></strong></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthHandIn/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthCost/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumDailyHandIn/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumDailyCost/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthSumHandIn/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthSumCost/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowDeposit/10000)%> 
              </td>
              <!--上月余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumPreMonthBalance/10000)%>
              </td>
              <!--当日余额-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowDayBalance/10000)%> 
              </td>
              <!--本月预算-存入-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowMonthHandIn/10000)%> 
              </td>
              <!--本月预算-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowMonthCost/10000)%> 
              </td>
              <!--与预算差额-上交-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMarginHandIn/10000)%> 
              </td>
              <!--与预算差额-支取-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMarginCost/10000)%> 
              </td>
            </TR>
            </TABLE>
            </TD>
        </TR>
        <TR>
            <TD height=20 vAlign=top width="100%">
                <TABLE align=center border=0 height=22 width="97%">
                    <TBODY>
                    <TR vAlign=center>
                        <TD height=25 width="8%" nowrap><FONT size="2">开户银行:</FONT></TD>
                        <TD height=25 width="8%" nowrap><FONT size="2"><%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%></FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><FONT size="2">部门经理:</FONT></TD>
                        <TD height=25 width="5%" nowrap><FONT size="2"><%="&nbsp;"%></FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="6%" nowrap><FONT size="2">制单:</FONT></TD>
                        <TD height=25 width="6%" nowrap><FONT size="2"><%=sessionMng.m_strUserName%><FONT></TD>
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
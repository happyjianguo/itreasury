<%--
/**
 页面名称 ：a102-v.jsp
 页面功能 : 股份公司代管电厂每日资金变动查询结果页面下载
 作    者 ： kewen hu
 日    期 ： 2003-12-15
 特殊说明 ：实现操作说明：
 修改历史 ：
 */
--%>

<%@ page contentType="application/msexcel;charset=gbk" %>

<%@ page import="java.util.Collection"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
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

<%
Log.print("\n\n*******进入页面--ebank/accountinfo/a102-v.jsp*******\n\n");
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
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

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
        //取得集团1下属单位客户结果集数据
        Collection groupOneInfo = qavd.getGroupOneInfo(qdcwi);
		Iterator iGroupOneInfo = null;
		if (groupOneInfo != null) {
			iGroupOneInfo = groupOneInfo.iterator();
		}
        //取得集团1合计数据
        QueryOuterAccountInfo groupOneCompanyInfo = qavd.getGroupOneCompanyInfo(qdcwi);
        //取得开发1下属单位客户结果集数据
        Collection empolderOneInfo = qavd.getEmpolderOneInfo(qdcwi);
		Iterator iEmpolderOneInfo = null;
		if (empolderOneInfo != null) {
			iEmpolderOneInfo = empolderOneInfo.iterator();
		}
        //取得开发1合计数据
        QueryOuterAccountInfo empolderOneCompanyInfo = qavd.getEmpolderOneCompanyInfo(qdcwi);
        //取内部客户数据(集团控股电厂)
        Collection groupElectricInfo = qavd.getGroupElectricInfo(qdcwi);
		Iterator iGroupElectricInfo = null;
		if (groupElectricInfo != null) {
			iGroupElectricInfo = groupElectricInfo.iterator();
		}
        //取内部客户数据(开发控股电厂)
        Collection empolderElectricInfo = qavd.getEmpolderElectricInfo(qdcwi);
		Iterator iEmpolderElectricInfo = null;
		if (empolderElectricInfo != null) {
			iEmpolderElectricInfo = empolderElectricInfo.iterator();
		}
        //断开连接
        qavd.CloseDailyAmountVaryDao();

        String dateStr = DataFormat.getDateString(queryDate);
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
                <TD colspan="15" align="center" class="td-rightbottom"><B><font size="3">股份公司代管电厂每日资金变动表</font></B> 
                </TD>
              </TR>
            <TR>
              <TD colspan="15" align="center" class="td-rightbottom"><FONT size="3"><%=dateStr%></FONT></TD>
            </TR>
            <TR> 
              <TD rowspan="2" align="center" class="td-rightbottom"><strong>序<BR>号</strong></TD>
              <TD rowspan="2" align="center" class="td-rightbottom"><strong>存款单位</strong></TD>
              <TD colspan="2" align="center" class="td-rightbottom"><strong>本月累计</strong></TD>
              <TD colspan="2" align="center" class="td-rightbottom"><strong>当日变动</strong></TD>
              <TD colspan="2" align="center" class="td-rightbottom"><strong>本月合计</strong></TD>
              <TD rowspan="2" align="center" class="td-rightbottom"><strong>当期存款</strong></TD>
              <TD colspan="2" align="center" class="td-rightbottom"><strong>本年累计</strong></TD>
              <TD colspan="3" align="center" class="td-rightbottom"><strong>本年合计</strong></TD>
              <TD rowspan="2" align="center" class="td-rightbottom"><strong>备注</strong></TD>
            </TR>
            <TR> 
              <TD width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></TD>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>存入</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>支取</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>上交</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>拨款</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>上交</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>拨款</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>差额</strong></td>
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
            double YearHandIn=0.0;      // 本年累计-上交
            double YearCost=0.0;        // 本年累计-拨款
            double YearSumHandIn=0.0;   // 本年合计-上交
            double YearSumCost=0.0;     // 本年合计-拨款
            double YearSumMargin=0.0;   // 本年合计-差额
            String Abstract="";         // 备注
        //关系运算列
         %>
            <TR> 
              <td  align="center" nowrap class="td-rightbottom" id="tdIndex">&nbsp;</td>
              <td  height="20" align="center" nowrap class="td-rightbottom">&nbsp;</td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">1</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">2</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">3</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">4</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">5=1+3</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">6=2+4</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">7=5-6</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">8</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">9</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">10=3+8</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">11=4+9</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">12=10-11</font></td>
              <td  height="20" class="td-rightbottom" align="center"><font size="3"><%="&nbsp;"%></font></td>
            </TR>
<%
    QueryOuterAccountInfo qoai = null;
    //集团1数据
    if(iGroupOneInfo !=null) {
        while (iGroupOneInfo.hasNext()) {
            qoai = (QueryOuterAccountInfo) iGroupOneInfo.next();
            No=qoai.getNo();                                        // 序号
            DepositCorp=qoai.getDepositCorp();                      // 存款单位
            MonthHandIn=MonthHandIn+qoai.getMonthHandIn();          // 本月累计-存入
            MonthCost=MonthCost+qoai.getMonthCost();                // 本月累计-支取
            DailyHandIn=DailyHandIn+qoai.getDailyHandIn();          // 当日变动-存入
            DailyCost=DailyCost+qoai.getDailyCost();                // 当日变动-支取
            MonthSumHandIn=MonthSumHandIn+qoai.getMonthSumHandIn(); // 本月合计-存入
            MonthSumCost=MonthSumCost+qoai.getMonthSumCost();       // 本月合计-支取
            NowDeposit=NowDeposit+qoai.getNowDeposit();             // 当期存款
            YearHandIn=YearHandIn+qoai.getYearHandIn();             // 本年累计-上交
            YearCost=YearCost+qoai.getYearCost();                   // 本年累计-拨款
            YearSumHandIn=YearSumHandIn+qoai.getYearSumHandIn();    // 本年合计-上交
            YearSumCost=YearSumCost+qoai.getYearSumCost();          // 本年合计-拨款
            YearSumMargin=YearSumMargin+qoai.getYearSumMargin();    // 本年合计-差额
            Abstract=qoai.getAbstract();                            // 备注
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex">
              <strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getNowDeposit()/10000)%> 
              </td>
              <!--本年累计-上交-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearHandIn()/10000)%> 
              </td>
              <!--本年累计-拨款-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearCost()/10000)%> 
              </td>
              <!--本年合计-上交-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearSumHandIn()/10000)%> 
              </td>
              <!--本年合计-拨款-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearSumCost()/10000)%> 
              </td>
              <!--本年合计-差额-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearSumMargin()/10000)%> 
              </td>
              <!--备注-->
              <td  height="20" class="td-topright" align="center">
              <%=Abstract==null || Abstract == ""?"&nbsp;":Abstract%> 
              </td>
            </TR>
<%
        }
    //集团1记录合计
%>
            <TR>
              <!--序号-->
              <td height="20" align="center" class="td-topright"><%="&nbsp;"%></td>
              <!--存款单位-->
              <td width="8%"  height="20" align="center" class="td-topright"><%="&nbsp;"%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--当期存款-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--本年累计-上交-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearHandIn/10000)%> 
              </td>
              <!--本年累计-拨款-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearCost/10000)%> 
              </td>
              <!--本年合计-上交-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearSumHandIn/10000)%> 
              </td>
              <!--本年合计-拨款-->
              <td height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearSumCost/10000)%> 
              </td>
              <!--本年合计-差额-->
              <td height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearSumMargin/10000)%> 
              </td>
              <!--备注-->
              <td height="20" class="td-topright" align="center"><%="&nbsp;"%> 
              </td>
            </TR>
<%
    }
	double dmYesterdayBalance = 0.0;
	double dmTodayBalance = 0.0;
	double dmDailyHandIn = 0.0;
	double dmDailyCost = 0.0;
    //集团1合计
    if(groupOneCompanyInfo != null) {
        qoai = groupOneCompanyInfo;
		//昨日余额
		dmYesterdayBalance = qoai.getYesterdayBalance();
		//今日余额
		dmTodayBalance = qoai.getTodayBalance();
		//集团1支款
		dmDailyCost = qoai.getDailyCost();
		//集团1收款
		dmDailyHandIn = qoai.getDailyHandIn();
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20" align="center" class="td-topright"><strong><%="集团1合计"%></strong></td>
              <!--昨日余额-->
              <td height="20" align="center" nowrap class="td-topright"><strong>
              <%="昨日余额"%></strong></td>
              <td height="20" align="right" colspan="2" class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmYesterdayBalance/10000)%> 
              </td>
              <!--今日余额-->
              <td  height="20" align="center" nowrap class="td-topright"><strong>
              <%="今日余额"%></strong></td>
              <td  height="20" align="right" colspan="2" class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmTodayBalance/10000)%> 
              </td>
              <!--转集团-->
              <td  height="20" align="center" colspan="3" class="td-topright"><strong><%="集团1支款"%></strong>
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmDailyCost/10000)%><strong><%="万元"%></strong></td>
              <td  height="20" align="center" colspan="3" class="td-topright"><strong><%="集团1收款"%></strong>
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmDailyHandIn/10000)%><strong><%="万元"%></strong></td>
              <td width="8%" align="right" height="20" nowrap class="td-topright">&nbsp;</td>
            </TR>
<%
    } else {
%>
            <TR> 
              <td width="4%" align="center" height="20" nowrap class="td-topright"><%="&nbsp;"%></td>
              <td width="8%" align="center" height="20" colspan="2" class="td-topright"><strong><%="集团1合计"%></strong></td>
              <td width="8%" align="center" height="20" nowrap class="td-topright"><strong><%="昨日余额"%></strong></td>
              <td width="8%" align="right" height="20" colspan="2" class="td-topright"><%="&nbsp;"%></td>
              <td width="8%" align="center" height="20" nowrap class="td-topright"><strong><%="今日余额"%></strong></td>
              <td width="8%" align="right" height="20" colspan="2" class="td-topright"><%="&nbsp;"%></td>
              <td width="8%" align="center" height="20" colspan="3" class="td-topright">
                <strong><%="集团1支款"%></strong><%="&nbsp;"%><strong><%="万元"%></strong></td>
              <td width="8%" align="center" height="20" colspan="3" class="td-topright">
                <strong><%="集团1收款"%></strong><%="&nbsp;"%><strong><%="万元"%></strong></td>
              <td width="8%" align="right" height="20" nowrap class="td-topright">&nbsp; </td>
            </TR>
<%
    }
    MonthHandIn=0.0;
    MonthCost=0.0;
    DailyHandIn=0.0;
    DailyCost=0.0;
    MonthSumHandIn=0.0;
    MonthSumCost=0.0;
    NowDeposit=0.0;
    YearHandIn=0.0;
    YearCost=0.0;
    YearSumHandIn=0.0;
    YearSumCost=0.0;
    YearSumMargin=0.0;
    //开发1数据
    if(iEmpolderOneInfo !=null) {
        while (iEmpolderOneInfo.hasNext()) {
            qoai = (QueryOuterAccountInfo) iEmpolderOneInfo.next();
            No=qoai.getNo();                                        // 序号
            DepositCorp=qoai.getDepositCorp();                      // 存款单位
            MonthHandIn=MonthHandIn+qoai.getMonthHandIn();          // 本月累计-存入
            MonthCost=MonthCost+qoai.getMonthCost();                // 本月累计-支取
            DailyHandIn=DailyHandIn+qoai.getDailyHandIn();          // 当日变动-存入
            DailyCost=DailyCost+qoai.getDailyCost();                // 当日变动-支取
            MonthSumHandIn=MonthSumHandIn+qoai.getMonthSumHandIn(); // 本月合计-存入
            MonthSumCost=MonthSumCost+qoai.getMonthSumCost();       // 本月合计-支取
            NowDeposit=NowDeposit+qoai.getNowDeposit();             // 当期存款
            YearHandIn=YearHandIn+qoai.getYearHandIn();             // 本年累计-上交
            YearCost=YearCost+qoai.getYearCost();                   // 本年累计-拨款
            YearSumHandIn=YearSumHandIn+qoai.getYearSumHandIn();    // 本年合计-上交
            YearSumCost=YearSumCost+qoai.getYearSumCost();          // 本年合计-拨款
            YearSumMargin=YearSumMargin+qoai.getYearSumMargin();    // 本年合计-差额
            Abstract=qoai.getAbstract();                            // 备注
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex">
              <strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--本月累计-存入-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthHandIn()/10000)%> 
              </td>
              <!--本月累计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthCost()/10000)%> 
              </td>
              <!--当日变动-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getDailyHandIn()/10000)%> 
              </td>
              <!--当日变动-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getDailyCost()/10000)%> 
              </td>
              <!--本月合计-存入-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--本月合计-支取-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getMonthSumCost()/10000)%> 
              </td>
              <!--当期存款-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getNowDeposit()/10000)%> 
              </td>
              <!--本年累计-上交-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearHandIn()/10000)%> 
              </td>
              <!--本年累计-拨款-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearCost()/10000)%> 
              </td>
              <!--本年合计-上交-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearSumHandIn()/10000)%> 
              </td>
              <!--本年合计-拨款-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearSumCost()/10000)%> 
              </td>
              <!--本年合计-差额-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qoai.getYearSumMargin()/10000)%> 
              </td>
              <!--备注-->
              <td  height="20" class="td-topright" align="center">
              <%=Abstract==null || Abstract == ""?"&nbsp;":Abstract%> 
              </td>
            </TR>
<%
        }
    //开发1记录合计
%>
            <TR>
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%" height="20" align="center" class="td-topright"><%="&nbsp;"%></td>
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
              <!--本年累计-上交-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearHandIn/10000)%> 
              </td>
              <!--本年累计-拨款-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearCost/10000)%> 
              </td>
              <!--本年合计-上交-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearSumHandIn/10000)%> 
              </td>
              <!--本年合计-拨款-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearSumCost/10000)%> 
              </td>
              <!--本年合计-差额-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(YearSumMargin/10000)%> 
              </td>
              <!--备注-->
              <td  height="20" class="td-topright" align="center"><%="&nbsp;"%> 
              </td>
            </TR>
<%
    }
    //开发1合计
    if(empolderOneCompanyInfo != null) {
		qoai = empolderOneCompanyInfo;
		//昨日余额
		dmYesterdayBalance = qoai.getYesterdayBalance();
		//今日余额
		dmTodayBalance = qoai.getTodayBalance();
		//集团1支款
		dmDailyCost = qoai.getDailyCost();
		//集团1收款
		dmDailyHandIn = qoai.getDailyHandIn();
%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright"><strong><%="开发1合计"%></strong></td>
              <!--昨日余额-->
              <td height="20" align="center" nowrap class="td-topright"><strong>
              <%="昨日余额"%></strong></td>
              <td height="20" align="right" colspan="2" class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmYesterdayBalance/10000)%> 
              </td>
              <!--今日余额-->
              <td  height="20" align="center" nowrap class="td-topright"><strong>
              <%="今日余额"%></strong></td>
              <td  height="20" align="right" colspan="2" class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmTodayBalance/10000)%> 
              </td>
              <!--转集团-->
              <td  height="20" align="center" colspan="3" class="td-topright"><strong><%="开发1支款"%></strong>
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmDailyCost/10000)%><strong><%="万元"%></strong></td>
              <td  height="20" align="center" colspan="3" class="td-topright"><strong><%="开发1收款"%></strong>
              <%=QDailyAmountVaryDao.roundAmountForExcel(dmDailyHandIn/10000)%><strong><%="万元"%></strong></td>
              <td width="8%" align="right" height="20" nowrap class="td-topright">&nbsp; </td>
            </TR>
<%
    } else {
%>
            <TR> 
              <td width="4%" align="center" height="20" nowrap class="td-topright"><%="&nbsp;"%></td>
              <td width="8%" align="center" height="20" class="td-topright"><strong><%="开发1合计"%></strong></td>
              <td width="8%" align="center" height="20" nowrap class="td-topright"><strong><%="昨日余额"%></strong></td>
              <td width="8%" align="right" height="20" colspan="2" class="td-topright"><%="&nbsp;"%></td>
              <td width="8%" align="center" height="20" nowrap class="td-topright"><strong><%="今日余额"%></strong></td>
              <td width="8%" align="right" height="20" colspan="2" class="td-topright"><%="&nbsp;"%></td>
              <td width="8%" align="center" height="20" colspan="3" class="td-topright">
                <strong><%="开发1支款"%></strong><%="&nbsp;"%><strong><%="万元"%></strong></td>
              <td width="8%" align="center" height="20" colspan="3" class="td-topright">
                <strong><%="开发1收款"%></strong><%="&nbsp;"%><strong><%="万元"%></strong></td>
              <td width="8%" align="right" height="20" nowrap class="td-topright">&nbsp; </td>
            </TR>
<%
    }
%>
            <TR>
            <TD colspan="15" class="td-topright"></TD>
            </TR>
            <TR>
            <TD colspan="15" class="td-rightbottom"></TD>
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
            //long sNo="";                // 序号
            //String sDepositCorp="";     // 存款单位
            MonthHandIn=0.0;
            MonthCost=0.0;
            DailyHandIn=0.0;
            DailyCost=0.0;
            MonthSumHandIn=0.0;
            MonthSumCost=0.0;
            NowDeposit=0.0;
            double PreMonthBalance=0.0; // 上月余额
            double NowDayBalance=0.0;   // 当日余额
            double NowMonthHandIn=0.0;  // 本月预算-存入
            double NowMonthCost=0.0;    // 本月预算-支取
            double MarginHandIn=0.0;    // 与预算差额-上交
            double MarginCost=0.0;      // 与预算差额-支取
        //关系运算列
%>
            <TR> 
              <td  height="20"  align="center" nowrap class="td-rightbottom">&nbsp;</td>
              <td  height="20"  align="center" nowrap class="td-rightbottom">&nbsp;</td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">1</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">2</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">3</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">4</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">5=1+3</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">6=2+4</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">7=5-6</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">8</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">9=7+8</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">10</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">11</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">12=5-10</font></td>
              <td  height="20" align="center" nowrap class="td-rightbottom"><font size="3">13=6-11</font></td>
            </TR>
<%
    //取内部客户数据
    QueryInnerAccountInfo qiai = null;
    if (iGroupElectricInfo != null) {
        while (iGroupElectricInfo.hasNext()) {
            qiai = (QueryInnerAccountInfo) iGroupElectricInfo.next();
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

%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex">
              <strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" class="td-topright">
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
    //集团电厂合计
 %>
            <TR> 
              <!--序号-->
              <td  align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20" align="center" class="td-topright">
              <strong><%="集团电厂合计"%></strong></td>
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
    } else {
%>
            <TR> 
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" class="td-topright"><strong><%="集团电厂合计"%></strong></td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
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
    MonthHandIn=0.0;        // 本月累计-存入
    MonthCost=0.0;          // 本月累计-支取
    DailyHandIn=0.0;        // 当日变动-存入
    DailyCost=0.0;          // 当日变动-支取
    MonthSumHandIn=0.0;     // 本月合计-存入
    MonthSumCost=0.0;       // 本月合计-支取
    NowDeposit=0.0;         // 当期存款
    PreMonthBalance=0.0;    // 上月余额
    NowDayBalance=0.0;      // 当日余额
    NowMonthHandIn=0.0;	// 本月预算-存入
    NowMonthCost=0.0;	// 本月预算-支取
    MarginHandIn=0.0;	// 与预算差额-上交
    MarginCost=0.0;		// 与预算差额-支取
    if (iEmpolderElectricInfo != null) {
        while (iEmpolderElectricInfo.hasNext()) {
            qiai = (QueryInnerAccountInfo) iEmpolderElectricInfo.next();
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

%>
            <TR> 
              <!--序号-->
              <td align="center" nowrap class="td-topright" id="tdIndex">
              <strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20"  align="center" class="td-topright">
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
    //集团电厂合计
 %>
            <TR> 
              <!--序号-->
              <td  align="center" nowrap class="td-topright" id="tdIndex"><strong><%="&nbsp;"%></strong></td>
              <!--存款单位-->
              <td width="8%"  height="20" align="center" class="td-topright">
              <strong><%="开发电厂合计"%></strong></td>
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
    } else {
%>
            <TR> 
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" class="td-topright"><strong><%="开发电厂合计"%></strong></td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
              <td width="8%"  height="20" nowrap class="td-topright">&nbsp; </td>
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
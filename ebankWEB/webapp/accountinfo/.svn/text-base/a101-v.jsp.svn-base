<%--
/**
 页面名称 ：a101-v.jsp
 页面功能 : 股份公司每日资金变动情况查询结果页面下载
 作    者 ： kewen hu
 日    期 ：
 特殊说明 ：实现操作说明：
 修改历史 ：
 */
--%>

<%@ page contentType="application/msexcel;charset=gbk" %>

<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtmlCom"%>
<%@ page import="com.iss.itreasury.ebank.util.OBOperation"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.Log" %>
<%@ page import="com.iss.itreasury.util.Env" %>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant" %>
<%@ page import="com.iss.itreasury.util.DataFormat" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.settlement.transdiscount.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.query.queryobj.QDailyAmountVaryDao"%>
<%@ page import="com.iss.itreasury.settlement.query.resultinfo.QueryDailyCapitalInfo" %>
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
Log.print("\n\n*******进入页面--ebank/accountinfo/a101-v.jsp*******\n\n");
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
        QueryDailyCapitalWhereInfo qdcwi = new QueryDailyCapitalWhereInfo();
        qdcwi.setOfficeID(sessionMng.m_lOfficeID);
        qdcwi.setCurrencyID(sessionMng.m_lCurrencyID);
        strTemp = (String)request.getAttribute("queryDate");
        if (strTemp != null && strTemp.trim().length() > 0) {
            qdcwi.setQueryDate(DataFormat.getDateTime(strTemp));
        } else {
            qdcwi.setQueryDate(tsQueryDate);
		}
        //通过日期查询
        QDailyAmountVaryDao dao=new QDailyAmountVaryDao();
        //股份公司支款
        QueryDailyCapitalInfo holdingCompanyInfo=dao.getHoldingCompanyInfo(qdcwi);
        //全资电厂数据
        Collection capitalElectricInfo=dao.getCapitalElectricInfo(qdcwi);
		Iterator iCapitalElectricInfo = null;
		if (capitalElectricInfo != null) {
			iCapitalElectricInfo = capitalElectricInfo.iterator();
		}
		//全资电厂客户数量
		int iClientCount = dao.getCapitalElectricClientCount(qdcwi);
        //控股电厂数据
        Collection holdingElectricInfo=dao.getHoldingElectricInfo(qdcwi);
		Iterator iHoldingElectricInfo = null;
		if (holdingElectricInfo != null) {
			iHoldingElectricInfo = holdingElectricInfo.iterator();
		}
        //断开连接
        dao.CloseDailyAmountVaryDao();
%>
<HTML>
<HEAD><TITLE></TITLE>
<style type="text/css">
<!--
.table1 {  border: 1px #000000 solid}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 10px}
.td-topright {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 10px}
.td-top {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 10px}
.td-right2 {  border-color: black black black #000000; border-style: double; border-top-width: 0px; border-right-width: 3px; border-bottom-width: 0px; border-left-width: 0px;FONT-SIZE: 10px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 10px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 10px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 10px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 10px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px;FONT-SIZE: 10px}
.td-rightbottomleft {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px;FONT-SIZE: 10px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px;FONT-SIZE: 10px}
-->
</style>
</HEAD>
<BODY  text="#000000" bgcolor="#FFFFFF">
  <TABLE border=0 class=top width="99%" >
    <TBODY>
      <TR>
        <TD>
          <TABLE align=center border=0 cellspacing="0" cellpadding="3" class="table1" width="99%">
      		<TR>
        		<TD colspan="12" class="td-rightbottom" align="center"><B><font size="5"><%=Env.getClientName()%>每日资金变动情况表</font></B> 
        		</TD>
      		</TR>
      		<TR> 
        		<TD colspan="12"  class="td-rightbottom" align="center"><font size="3">
        		 <%=DataFormat.getDateString(qdcwi.getQueryDate())%></font></TD>
      		</TR>

            <TR> 
              <td colspan="2" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                单位名称</strong></td>
              <TD colspan="2" align="center" nowrap class="td-rightbottom"><strong>当日变动</strong></TD>
              <td colspan="2" align="center" nowrap class="td-rightbottom"><strong>本月累计</strong></td>
              <td colspan="2" align="center" nowrap class="td-rightbottom"><strong>本月预算</strong></td>
              <td colspan="2" align="center" nowrap class="td-rightbottom"><strong>与预算差额</strong></td>
              <td rowspan="2" align="center" nowrap class="td-rightbottom"><strong>月初余额</strong></td>
              <td rowspan="2" align="center" nowrap class="td-rightbottom"><strong>今日余额</strong></td>
            </TR>
			<TR>
              <TD align="center" nowrap class="td-rightbottom"><strong>上交</strong></TD>
              <td align="center" nowrap class="td-rightbottom"><strong>支款</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>上交</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>支款</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>上交</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>支款</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>上交</strong></td>
              <td align="center" nowrap class="td-rightbottom"><strong>支款</strong></td>
			</TR>
<%
            String sName="";                // 单位名称
            double DailyHandIn=0.0;         // 当日变动-上交
            double DailyCost=0.0;           // 当日变动-支款
            double MonthHandIn=0.0;         // 本月累计-上交
            double MonthCost=0.0;           // 本月累计-支款
            double MonthBudgetHandIn=0.0;   // 本月预算-上交
            double MonthBudgetCost=0.0;     // 本月预算-支款
            double MarginHandIn=0.0;        // 与预算差额-上交
            double MarginCost=0.0;          // 与预算差额-支款
            double MonthBalance=0.0;        // 月初余额
            double TodayBalance=0.0;        // 今日余额
            String sAbstract="";            // 备注

			//总计
            double SumDailyHandIn=0.0;         // 当日变动-上交
            double SumDailyCost=0.0;           // 当日变动-支款
            double SumMonthHandIn=0.0;         // 本月累计-上交
            double SumMonthCost=0.0;           // 本月累计-支款
            double SumMonthBudgetHandIn=0.0;   // 本月预算-上交
            double SumMonthBudgetCost=0.0;     // 本月预算-支款
            double SumMarginHandIn=0.0;        // 与预算差额-上交
            double SumMarginCost=0.0;          // 与预算差额-支款
            double SumMonthBalance=0.0;        // 月初余额
            double SumTodayBalance=0.0;        // 今日余额
        //股份公司支款
        if(holdingCompanyInfo != null ) {
            sName=holdingCompanyInfo.getName();
            DailyHandIn=DailyHandIn+holdingCompanyInfo.getDailyHandIn();
            DailyCost=DailyCost+holdingCompanyInfo.getDailyCost();
            MonthHandIn=MonthHandIn+holdingCompanyInfo.getMonthHandIn();
            MonthCost=MonthCost+holdingCompanyInfo.getMonthCost();
            MonthBudgetHandIn=MonthBudgetHandIn+holdingCompanyInfo.getMonthBudgetHandIn();
            MonthBudgetCost=MonthBudgetCost+holdingCompanyInfo.getMonthBudgetCost();
            MarginHandIn=MarginHandIn+holdingCompanyInfo.getMarginHandIn();
            MarginCost=MarginCost+holdingCompanyInfo.getMarginCost();
            MonthBalance=MonthBalance+holdingCompanyInfo.getMonthBalance();
            TodayBalance=TodayBalance+holdingCompanyInfo.getTodayBalance();
            sAbstract=holdingCompanyInfo.getAbstract();

            SumDailyHandIn=SumDailyHandIn+holdingCompanyInfo.getDailyHandIn();
            SumDailyCost=SumDailyCost+holdingCompanyInfo.getDailyCost();
            SumMonthHandIn=SumMonthHandIn+holdingCompanyInfo.getMonthHandIn();
            SumMonthCost=SumMonthCost+holdingCompanyInfo.getMonthCost();
            SumMonthBudgetHandIn=SumMonthBudgetHandIn+holdingCompanyInfo.getMonthBudgetHandIn();
            SumMonthBudgetCost=SumMonthBudgetCost+holdingCompanyInfo.getMonthBudgetCost();
            SumMarginHandIn=SumMarginHandIn+holdingCompanyInfo.getMarginHandIn();
            SumMarginCost=SumMarginCost+holdingCompanyInfo.getMarginCost();
            SumMonthBalance=SumMonthBalance+holdingCompanyInfo.getMonthBalance();
            SumTodayBalance=SumTodayBalance+holdingCompanyInfo.getTodayBalance();
%>
            <TR> 
              <td align="center" nowrap class="td-rightbottom">&nbsp;&nbsp;</td>
              <td align="center" nowrap class="td-rightbottom">&nbsp;&nbsp;</td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">1&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">2&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">3=sum(1)</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">4=sum(2)</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">5&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">6&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">7=3-5&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">8=4-6&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">9&nbsp;</font></td>
              <td align="center" nowrap class="td-rightbottom"><font size="3">10=9+3-4</font></td>
            </TR>
            <TR>
              <td rowspan="<%=iClientCount+1%>" align="center" nowrap class="td-topright"><strong>
              华<br>能<br>国<br>际</strong></td>
              <!--单位名称-->
              <td align="center" nowrap class="td-topright"><%=sName==""?"&nbsp;&nbsp;":sName%></td>
              <!--当日变动-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getDailyHandIn())%> 
              </td>
              <!--当日变动-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getDailyCost())%> 
              </td>
              <!--本月累计-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMonthHandIn())%> 
              </td>
              <!--本月累计-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMonthCost())%> 
              </td>
              <!--本月预算-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMonthBudgetHandIn())%> 
              </td>
              <!--本月预算-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMonthBudgetCost())%> 
              </td>
              <!--与预算差额-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMarginHandIn())%> 
              </td>
              <!--与预算差额-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMarginCost())%> 
              </td>
              <!--月初余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getMonthBalance())%> 
              </td>
              <!--今日余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(holdingCompanyInfo.getTodayBalance())%> 
              </td>
            </TR>
<%
        }
		QueryDailyCapitalInfo info = null;

        DailyHandIn=0.0;         // 当日变动-上交
		DailyCost=0.0;           // 当日变动-支款
        MonthHandIn=0.0;         // 本月累计-上交
        MonthCost=0.0;           // 本月累计-支款
        MonthBudgetHandIn=0.0;   // 本月预算-上交
        MonthBudgetCost=0.0;     // 本月预算-支款
        MarginHandIn=0.0;        // 与预算差额-上交
        MarginCost=0.0;          // 与预算差额-支款
        //MonthBalance=0.0;        // 月初余额
        //TodayBalance=0.0;        // 今日余额
        //外部客户信息
        while(iCapitalElectricInfo != null && iCapitalElectricInfo.hasNext()) {
            info = (QueryDailyCapitalInfo)iCapitalElectricInfo.next();
            sName=info.getSimpleName();
            DailyHandIn=DailyHandIn+info.getDailyHandIn();
            DailyCost=DailyCost+info.getDailyCost();
            MonthHandIn=MonthHandIn+info.getMonthHandIn();
            MonthCost=MonthCost+info.getMonthCost();
            MonthBudgetHandIn=MonthBudgetHandIn+info.getMonthBudgetHandIn();
            MonthBudgetCost=MonthBudgetCost+info.getMonthBudgetCost();
            MarginHandIn=MarginHandIn+info.getMarginHandIn();
            MarginCost=MarginCost+info.getMarginCost();
            MonthBalance=MonthBalance+info.getMonthBalance();
            TodayBalance=TodayBalance+info.getTodayBalance();
            sAbstract=info.getAbstract();

            SumDailyHandIn=SumDailyHandIn+info.getDailyHandIn();
            SumDailyCost=SumDailyCost+info.getDailyCost();
            SumMonthHandIn=SumMonthHandIn+info.getMonthHandIn();
            SumMonthCost=SumMonthCost+info.getMonthCost();
            SumMonthBudgetHandIn=SumMonthBudgetHandIn+info.getMonthBudgetHandIn();
            SumMonthBudgetCost=SumMonthBudgetCost+info.getMonthBudgetCost();
            SumMarginHandIn=SumMarginHandIn+info.getMarginHandIn();
            SumMarginCost=SumMarginCost+info.getMarginCost();
            SumMonthBalance=SumMonthBalance+info.getMonthBalance();
            SumTodayBalance=SumTodayBalance+info.getTodayBalance();
%>
            <TR> 
              <!--单位名称-->
              <td align="center" nowrap class="td-topright" ><%=sName=="" || sName==null?"&nbsp;&nbsp;":sName%></td>
              <!--当日变动-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getDailyHandIn())%> 
              </td>
              <!--当日变动-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getDailyCost())%> 
              </td>
              <!--本月累计-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthHandIn())%> 
              </td>
              <!--本月累计-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthCost())%> 
              </td>
              <!--本月预算-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthBudgetHandIn())%> 
              </td>
              <!--本月预算-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthBudgetCost())%> 
              </td>
              <!--与预算差额-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMarginHandIn())%> 
              </td>
              <!--与预算差额-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMarginCost())%> 
              </td>
              <!--月初余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthBalance())%> 
              </td>
              <!--今日余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getTodayBalance())%> 
              </td>
            </TR>
<%
        }
		//全资电厂小计数据
%>
            <TR> 
              <!--单位名称-->
              <td align="center" colspan="2" nowrap class="td-topright"><strong><%="全资电厂小计"%></strong></td>
              <!--当日变动-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(DailyHandIn)%> 
              </td>
              <!--当日变动-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(DailyCost)%> 
              </td>
              <!--本月累计-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthHandIn)%> 
              </td>
              <!--本月累计-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthCost)%> 
              </td>
              <!--本月预算-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthBudgetHandIn)%> 
              </td>
              <!--本月预算-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthBudgetCost)%> 
              </td>
              <!--与预算差额-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MarginHandIn)%> 
              </td>
              <!--与预算差额-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MarginCost)%> 
              </td>
              <!--月初余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthBalance)%> 
              </td>
              <!--今日余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(TodayBalance)%> 
              </td>
            </TR>
<%
        DailyHandIn=0.0;         // 当日变动-上交
        DailyCost=0.0;           // 当日变动-支款
        MonthHandIn=0.0;         // 本月累计-上交
        MonthCost=0.0;           // 本月累计-支款
        MonthBudgetHandIn=0.0;   // 本月预算-上交
        MonthBudgetCost=0.0;     // 本月预算-支款
        MarginHandIn=0.0;        // 与预算差额-上交
        MarginCost=0.0;          // 与预算差额-支款
        MonthBalance=0.0;        // 月初余额
        TodayBalance=0.0;        // 今日余额

		//SumMonthBalance = 0.0;

       	//内部客户信息
        while(iHoldingElectricInfo != null && iHoldingElectricInfo.hasNext()) {
            info = (QueryDailyCapitalInfo)iHoldingElectricInfo.next();
            sName=info.getName();
            DailyHandIn=DailyHandIn+info.getDailyHandIn();
            DailyCost=DailyCost+info.getDailyCost();
            MonthHandIn=MonthHandIn+info.getMonthHandIn();
            MonthCost=MonthCost+info.getMonthCost();
            MonthBudgetHandIn=MonthBudgetHandIn+info.getMonthBudgetHandIn();
            MonthBudgetCost=MonthBudgetCost+info.getMonthBudgetCost();
            MarginHandIn=MarginHandIn+info.getMarginHandIn();
            MarginCost=MarginCost+info.getMarginCost();
            MonthBalance=MonthBalance+info.getMonthBalance();
            TodayBalance=TodayBalance+info.getTodayBalance();
            sAbstract=info.getAbstract();

            SumDailyHandIn=SumDailyHandIn+info.getDailyHandIn();
            SumDailyCost=SumDailyCost+info.getDailyCost();
            SumMonthHandIn=SumMonthHandIn+info.getMonthHandIn();
            SumMonthCost=SumMonthCost+info.getMonthCost();
            SumMonthBudgetHandIn=SumMonthBudgetHandIn+info.getMonthBudgetHandIn();
            SumMonthBudgetCost=SumMonthBudgetCost+info.getMonthBudgetCost();
            SumMarginHandIn=SumMarginHandIn+info.getMarginHandIn();
            SumMarginCost=SumMarginCost+info.getMarginCost();
            SumMonthBalance=SumMonthBalance+info.getMonthBalance();
            SumTodayBalance=SumTodayBalance+info.getTodayBalance();
%>
            <TR> 
              <!--单位名称-->
              <td colspan="2" align="center" nowrap class="td-topright"><%=sName==""?"&nbsp;&nbsp;":sName%></td>
              <!--当日变动-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getDailyHandIn())%> 
              </td>
              <!--当日变动-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getDailyCost())%> 
              </td>
              <!--本月累计-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthHandIn())%> 
              </td>
              <!--本月累计-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthCost())%> 
              </td>
              <!--本月预算-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthBudgetHandIn())%> 
              </td>
              <!--本月预算-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthBudgetCost())%> 
              </td>
              <!--与预算差额-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMarginHandIn())%> 
              </td>
              <!--与预算差额-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMarginCost())%> 
              </td>
              <!--月初余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getMonthBalance())%> 
              </td>
              <!--今日余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(info.getTodayBalance())%> 
              </td>
            </TR>
<%
        }
		//控股电厂小计数据
%>
            <TR> 
              <!--单位名称-->
              <td align="center" colspan="2" nowrap class="td-topright"><strong><%="控股电厂小计"%></strong></td>
              <!--当日变动-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(DailyHandIn)%> 
              </td>
              <!--当日变动-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(DailyCost)%> 
              </td>
              <!--本月累计-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthHandIn)%> 
              </td>
              <!--本月累计-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthCost)%> 
              </td>
              <!--本月预算-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthBudgetHandIn)%> 
              </td>
              <!--本月预算-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthBudgetCost)%> 
              </td>
              <!--与预算差额-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MarginHandIn)%> 
              </td>
              <!--与预算差额-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MarginCost)%> 
              </td>
              <!--月初余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(MonthBalance)%> 
              </td>
              <!--今日余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(TodayBalance)%> 
              </td>
            </TR>

			<!--总计-->
            <TR> 
              <!--单位名称-->
              <td colspan="2"  align="center" nowrap class="td-topright"><strong><%="总计"%></strong></td>
              <!--当日变动-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumDailyHandIn)%> 
              </td>
              <!--当日变动-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumDailyCost)%> 
              </td>
              <!--本月累计-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMonthHandIn)%> 
              </td>
              <!--本月累计-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMonthCost)%> 
              </td>
              <!--本月预算-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMonthBudgetHandIn)%> 
              </td>
              <!--本月预算-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMonthBudgetCost)%> 
              </td>
              <!--与预算差额-上交-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMarginHandIn)%> 
              </td>
              <!--与预算差额-支款-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMarginCost)%> 
              </td>
              <!--月初余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumMonthBalance)%> 
              </td>
              <!--今日余额-->
              <td align="right" nowrap class="td-topright"><%=QDailyAmountVaryDao.formatAmountForExcel(SumTodayBalance)%> 
              </td>
            </TR>
          </TABLE>
         </TD>
      </TR>
      <TR>
         <TD>
          <TABLE align=center border=0 cellspacing="0" cellpadding="3" class="table1" width="100%">
                    <TBODY>
                    <TR vAlign=center>
                        <TD width="8%" nowrap><FONT size="2">开户银行:</FONT></TD>
                        <TD width="8%" nowrap><FONT size="2"><%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%></FONT></TD>
                        <TD width="5%" nowrap></TD>
                        <TD width="5%" nowrap></TD>
                        <TD width="5%" nowrap></TD>
                        <TD width="5%" nowrap><FONT size="2">部门经理:</FONT></TD>
                        <TD width="5%" nowrap><FONT size="2">&nbsp;</FONT></TD>
                        <TD width="5%" nowrap></TD>
                        <TD width="5%" nowrap></TD>
                        <TD width="5%" nowrap></TD>
                        <TD width="6%" nowrap><FONT size="2">制单:</FONT></TD>
                        <TD width="6%" nowrap><FONT size="2"><%=sessionMng.m_strUserName%><FONT></TD>
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
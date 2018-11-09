<%--
/**
 ҳ������ ��a103-v.jsp
 ҳ�湦�� : ���ܼ��Ź�˾�������糧���ÿ�ձ䶯�������ҳ������
 ��    �� �� kewen hu
 ��    �� �� 2003-12-20
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
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
Log.print("\n\n*******����ҳ��--ebank/accountinfo/a103-v.jsp*******\n\n");
    //�������
	String strTitle = "[ÿ���ʽ�䶯����ܱ�]";
    try {
         /* �û���¼��� */
        if (sessionMng.isLogin() == false) {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) {
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }
        /* ��ʾ�ļ�ͷ */
        //OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

        //����ҳ����Ʊ���
        String strActionResult="";
        String strAction="";

        //ҳ����Ʋ���
        if (request.getParameter("strActionResult") != null) {
            strActionResult = (String)request.getParameter("strActionResult");
        }
        if (request.getParameter("strAction") != null) {
            strAction = (String)request.getParameter("strAction");
        }

    	Timestamp  tsQueryDate = Env.getSystemDate(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
        //�������
        String strTemp = null;
        //ȡ�ò�ѯ����
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
		//ȡ���ܼ��Ź�˾�Ľ����
		QueryInnerAccountInfo groupCompanyInfo = qavd.getGroupCompanyInfo(qdcwi);
		//ȡ����ȫ�ʵ糧��¼�Ľ����
		Collection groupCapitalElectricInfo = qavd.getGroupCapitalElectricInfo(qdcwi);
		Iterator iGroupCapitalElectricInfo = null;
		if (groupCapitalElectricInfo != null) {
			iGroupCapitalElectricInfo = groupCapitalElectricInfo.iterator();
		}
		//ȡ���ܹ��ʵ���������˾�Ľ����
		QueryInnerAccountInfo empolderCompanyInfo = qavd.getEmpolderCompanyInfo(qdcwi);
		//ȡ����ȫ�ʵ糧��¼�Ľ����
		Collection empolderCapitalElectricInfo = qavd.getEmpolderCapitalElectricInfo(qdcwi);
		Iterator iEmpolderCapitalElectricInfo = null;
		if (empolderCapitalElectricInfo != null) {
			iEmpolderCapitalElectricInfo = empolderCapitalElectricInfo.iterator();
		}
		//ȡ���ſعɵ糧��¼�Ľ����
		Collection groupHoldingElectricInfo = qavd.getGroupHoldingElectricInfo(qdcwi);
		Iterator iGroupHoldingElectricInfo = null;
		if (groupHoldingElectricInfo != null) {
			iGroupHoldingElectricInfo = groupHoldingElectricInfo.iterator();
		}
		//ȡ�����عɵ糧��¼�Ľ����
		Collection empolderHoldingElectricInfo = qavd.getEmpolderHoldingElectricInfo(qdcwi);
		Iterator iEmpolderHoldingElectricInfo = null;
		if (empolderHoldingElectricInfo != null) {
			iEmpolderHoldingElectricInfo = empolderHoldingElectricInfo.iterator();
		}
        //�Ͽ�����
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
        		<TD height=2 colspan=15 align="center" class=td-rightbottom><B><font size="3"><%=Env.getClientName()%>�������糧���ÿ�ձ䶯�����</font></B></TD>
              </TR>
              <TR> 
                <TD height=2 colspan=15 align="center" class=td-rightbottom><font size="3">
                <%=DataFormat.getDateString(qdcwi.getQueryDate())%></font></TD>
              </TR>
            <TR> 
              <TD height="40" rowspan="2" align="center" valign="middle" class=td-rightbottom><strong>��<BR>��</strong></TD>
              <td height="40" rowspan="2" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                ��λ</strong></td>
              <TD height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>�����ۼ�</strong></TD>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>���ձ䶯</strong></td>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>���ºϼ�</strong></td>
              <td height="40" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                ���ڴ��</strong></td>
              <td height="40" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                �������</strong></td>
              <td height="40" rowspan="2" align="center" valign="middle" class="td-rightbottom"><strong> 
                �������</strong></td>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>����Ԥ��</strong></td>
              <td height="20" colspan="2" align="center" nowrap class="td-rightbottom"><strong>��Ԥ����</strong></td>
            </TR>
            <TR> 
              <TD width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>����</strong></TD>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>֧ȡ</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>����</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>֧ȡ</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>����</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>֧ȡ</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>����</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>֧ȡ</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>����</strong></td>
              <td width="8%" height="20" align="center" nowrap class="td-rightbottom"><strong>֧ȡ</strong></td>
            </TR>
<%
            long   No=0;                // ���
            String DepositCorp="";      // ��λ
            double MonthHandIn=0.0;     // �����ۼ�-����
            double MonthCost=0.0;       // �����ۼ�-֧ȡ
            double DailyHandIn=0.0;     // ���ձ䶯-����
            double DailyCost=0.0;       // ���ձ䶯-֧ȡ
            double MonthSumHandIn=0.0;  // ���ºϼ�-����
            double MonthSumCost=0.0;    // ���ºϼ�-֧ȡ
            double NowDeposit=0.0;      // ���ڴ��
            double PreMonthBalance=0.0; // �������
            double NowDayBalance=0.0;   // �������
            double NowMonthHandIn=0.0;  // ����Ԥ��-����
            double NowMonthCost=0.0;    // ����Ԥ��-֧ȡ
            double MarginHandIn=0.0;    // ��Ԥ����-�Ͻ�
            double MarginCost=0.0;      // ��Ԥ����-֧ȡ

            double SumMonthHandIn=0.0;     // �����ۼ�-����
            double SumMonthCost=0.0;       // �����ۼ�-֧ȡ
            double SumDailyHandIn=0.0;     // ���ձ䶯-����
            double SumDailyCost=0.0;       // ���ձ䶯-֧ȡ
            double SumMonthSumHandIn=0.0;  // ���ºϼ�-����
            double SumMonthSumCost=0.0;    // ���ºϼ�-֧ȡ
            double SumNowDeposit=0.0;      // ���ڴ��
            double SumPreMonthBalance=0.0; // �������
            double SumNowDayBalance=0.0;   // �������
            double SumNowMonthHandIn=0.0;  // ����Ԥ��-����
            double SumNowMonthCost=0.0;    // ����Ԥ��-֧ȡ
            double SumMarginHandIn=0.0;    // ��Ԥ����-�Ͻ�
            double SumMarginCost=0.0;      // ��Ԥ����-֧ȡ
        //��ϵ������
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
    //ȡ���ܼ��Ź�˾�Ľ����
    QueryInnerAccountInfo qiai = null;
    if (groupCompanyInfo != null) {
                qiai = groupCompanyInfo;
                No=qiai.getNo();                                            // ���
                DepositCorp=qiai.getDepositCorp();                          // ��λ
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                MonthCost=MonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                DailyCost=DailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // �������
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // �������
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// ����Ԥ��-����
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// ����Ԥ��-֧ȡ
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// ��Ԥ����-�Ͻ�
            	MarginCost=MarginCost+qiai.getMarginCost();					// ��Ԥ����-֧ȡ

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // �������
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // �������
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // ����Ԥ��-����
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // ����Ԥ��-֧ȡ
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // ��Ԥ����-�Ͻ�
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // ��Ԥ����-֧ȡ
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
        }
		//ȡ����ȫ�ʵ糧��¼�Ľ����
        if (iGroupCapitalElectricInfo != null) {
            while (iGroupCapitalElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iGroupCapitalElectricInfo.next();
                No=qiai.getNo();                                            // ���
                DepositCorp=qiai.getDepositCorp();                          // ��λ
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                MonthCost=MonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                DailyCost=DailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // �������
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // �������
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// ����Ԥ��-����
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// ����Ԥ��-֧ȡ
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// ��Ԥ����-�Ͻ�
            	MarginCost=MarginCost+qiai.getMarginCost();					// ��Ԥ����-֧ȡ

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // �������
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // �������
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // ����Ԥ��-����
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // ����Ԥ��-֧ȡ
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // ��Ԥ����-�Ͻ�
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // ��Ԥ����-֧ȡ
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
            }
        }
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="���ű�����ȫ�ʵ糧С��"%></strong></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // �����ۼ�-����
                MonthCost=0.0;      // �����ۼ�-֧ȡ
                DailyHandIn=0.0;    // ���ձ䶯-����
                DailyCost=0.0;      // ���ձ䶯-֧ȡ
                MonthSumHandIn=0.0; // ���ºϼ�-����
                MonthSumCost=0.0;   // ���ºϼ�-֧ȡ
                NowDeposit=0.0;     // ���ڴ��
                PreMonthBalance=0.0;// �������
                NowDayBalance=0.0;  // �������
            	NowMonthHandIn=0.0;	// ����Ԥ��-����
            	NowMonthCost=0.0;	// ����Ԥ��-֧ȡ
            	MarginHandIn=0.0;	// ��Ԥ����-�Ͻ�
            	MarginCost=0.0;		// ��Ԥ����-֧ȡ
		//ȡ���ܹ��ʵ���������˾�Ľ����
        if (empolderCompanyInfo != null) {
                qiai = empolderCompanyInfo;
                No=qiai.getNo();                                            // ���
                DepositCorp=qiai.getDepositCorp();                          // ��λ
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                MonthCost=MonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                DailyCost=DailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // �������
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // �������
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// ����Ԥ��-����
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// ����Ԥ��-֧ȡ
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// ��Ԥ����-�Ͻ�
            	MarginCost=MarginCost+qiai.getMarginCost();					// ��Ԥ����-֧ȡ

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // �������
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // �������
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // ����Ԥ��-����
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // ����Ԥ��-֧ȡ
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // ��Ԥ����-�Ͻ�
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // ��Ԥ����-֧ȡ
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
        }
		//ȡ����ȫ�ʵ糧��¼�Ľ����
        if (iEmpolderCapitalElectricInfo != null) {
            while (iEmpolderCapitalElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iEmpolderCapitalElectricInfo.next();
                No=qiai.getNo();                                            // ���
                DepositCorp=qiai.getDepositCorp();                          // ��λ
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                MonthCost=MonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                DailyCost=DailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // �������
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // �������
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// ����Ԥ��-����
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// ����Ԥ��-֧ȡ
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// ��Ԥ����-�Ͻ�
            	MarginCost=MarginCost+qiai.getMarginCost();					// ��Ԥ����-֧ȡ

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // �������
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // �������
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // ����Ԥ��-����
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // ����Ԥ��-֧ȡ
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // ��Ԥ����-�Ͻ�
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // ��Ԥ����-֧ȡ
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
            }
        }
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="����������ȫ�ʵ糧С��"%></strong></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // �����ۼ�-����
                MonthCost=0.0;      // �����ۼ�-֧ȡ
                DailyHandIn=0.0;    // ���ձ䶯-����
                DailyCost=0.0;      // ���ձ䶯-֧ȡ
                MonthSumHandIn=0.0; // ���ºϼ�-����
                MonthSumCost=0.0;   // ���ºϼ�-֧ȡ
                NowDeposit=0.0;     // ���ڴ��
                PreMonthBalance=0.0;// �������
                NowDayBalance=0.0;  // �������
            	NowMonthHandIn=0.0;	// ����Ԥ��-����
            	NowMonthCost=0.0;	// ����Ԥ��-֧ȡ
            	MarginHandIn=0.0;	// ��Ԥ����-�Ͻ�
            	MarginCost=0.0;		// ��Ԥ����-֧ȡ
		//ȡ���ſعɵ糧��¼�Ľ����
        if (iGroupHoldingElectricInfo != null) {
            while (iGroupHoldingElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iGroupHoldingElectricInfo.next();
                No=qiai.getNo();                                            // ���
                DepositCorp=qiai.getDepositCorp();                          // ��λ
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                MonthCost=MonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                DailyCost=DailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // �������
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // �������
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// ����Ԥ��-����
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// ����Ԥ��-֧ȡ
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// ��Ԥ����-�Ͻ�
            	MarginCost=MarginCost+qiai.getMarginCost();					// ��Ԥ����-֧ȡ

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // �������
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // �������
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // ����Ԥ��-����
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // ����Ԥ��-֧ȡ
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // ��Ԥ����-�Ͻ�
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // ��Ԥ����-֧ȡ
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
            }
        }
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="���ſعɵ糧С��"%></strong></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // �����ۼ�-����
                MonthCost=0.0;      // �����ۼ�-֧ȡ
                DailyHandIn=0.0;    // ���ձ䶯-����
                DailyCost=0.0;      // ���ձ䶯-֧ȡ
                MonthSumHandIn=0.0; // ���ºϼ�-����
                MonthSumCost=0.0;   // ���ºϼ�-֧ȡ
                NowDeposit=0.0;     // ���ڴ��
                PreMonthBalance=0.0;// �������
                NowDayBalance=0.0;  // �������
            	NowMonthHandIn=0.0;	// ����Ԥ��-����
            	NowMonthCost=0.0;	// ����Ԥ��-֧ȡ
            	MarginHandIn=0.0;	// ��Ԥ����-�Ͻ�
            	MarginCost=0.0;		// ��Ԥ����-֧ȡ
		//ȡ�����عɵ糧��¼�Ľ����
        if (iEmpolderHoldingElectricInfo != null) {
            while (iEmpolderHoldingElectricInfo.hasNext()) {
                qiai = (QueryInnerAccountInfo) iEmpolderHoldingElectricInfo.next();
                No=qiai.getNo();                                            // ���
                DepositCorp=qiai.getDepositCorp();                          // ��λ
                MonthHandIn=MonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                MonthCost=MonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                DailyHandIn=DailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                DailyCost=DailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                MonthSumHandIn=MonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                MonthSumCost=MonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                NowDeposit=NowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                PreMonthBalance=PreMonthBalance+qiai.getPreMonthBalance();  // �������
                NowDayBalance=NowDayBalance+qiai.getNowDayBalance();        // �������
            	NowMonthHandIn=NowMonthHandIn+qiai.getNowMonthHandIn();		// ����Ԥ��-����
            	NowMonthCost=NowMonthCost+qiai.getNowMonthCost();			// ����Ԥ��-֧ȡ
            	MarginHandIn=MarginHandIn+qiai.getMarginHandIn();			// ��Ԥ����-�Ͻ�
            	MarginCost=MarginCost+qiai.getMarginCost();					// ��Ԥ����-֧ȡ

                SumMonthHandIn=SumMonthHandIn+qiai.getMonthHandIn();              // �����ۼ�-����
                SumMonthCost=SumMonthCost+qiai.getMonthCost();                    // �����ۼ�-֧ȡ
                SumDailyHandIn=SumDailyHandIn+qiai.getDailyHandIn();              // ���ձ䶯-����
                SumDailyCost=SumDailyCost+qiai.getDailyCost();                    // ���ձ䶯-֧ȡ
                SumMonthSumHandIn=SumMonthSumHandIn+qiai.getMonthSumHandIn();     // ���ºϼ�-����
                SumMonthSumCost=SumMonthSumCost+qiai.getMonthSumCost();           // ���ºϼ�-֧ȡ
                SumNowDeposit=SumNowDeposit+qiai.getNowDeposit();                 // ���ڴ��
                SumPreMonthBalance=SumPreMonthBalance+qiai.getPreMonthBalance();  // �������
                SumNowDayBalance=SumNowDayBalance+qiai.getNowDayBalance();        // �������
            	SumNowMonthHandIn=SumNowMonthHandIn+qiai.getNowMonthHandIn();	  // ����Ԥ��-����
            	SumNowMonthCost=SumNowMonthCost+qiai.getNowMonthCost();			  // ����Ԥ��-֧ȡ
            	SumMarginHandIn=SumMarginHandIn+qiai.getMarginHandIn();			  // ��Ԥ����-�Ͻ�
            	SumMarginCost=SumMarginCost+qiai.getMarginCost();				  // ��Ԥ����-֧ȡ
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright" id="tdIndex"><strong><%=No==0?"&nbsp;":String.valueOf(No)%></strong></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <%=DepositCorp==null?"&nbsp;":DepositCorp%></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthHandIn()/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthCost()/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyHandIn()/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getDailyCost()/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumHandIn()/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMonthSumCost()/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDeposit()/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getPreMonthBalance()/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowDayBalance()/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthHandIn()/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getNowMonthCost()/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginHandIn()/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(qiai.getMarginCost()/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // �����ۼ�-����
                MonthCost=0.0;      // �����ۼ�-֧ȡ
                DailyHandIn=0.0;    // ���ձ䶯-����
                DailyCost=0.0;      // ���ձ䶯-֧ȡ
                MonthSumHandIn=0.0; // ���ºϼ�-����
                MonthSumCost=0.0;   // ���ºϼ�-֧ȡ
                NowDeposit=0.0;     // ���ڴ��
                PreMonthBalance=0.0;// �������
                NowDayBalance=0.0;  // �������
            	NowMonthHandIn=0.0;	// ����Ԥ��-����
            	NowMonthCost=0.0;	// ����Ԥ��-֧ȡ
            	MarginHandIn=0.0;	// ��Ԥ����-�Ͻ�
            	MarginCost=0.0;		// ��Ԥ����-֧ȡ
            }
        }
%>
            <TR> 
              <!--���-->
              <td align="center" nowrap class="td-topright"><%="&nbsp;"%></td>
              <!--��λ-->
              <td width="8%"  height="20"  align="center" nowrap class="td-topright">
              <strong><%="�����عɵ糧С��"%></strong></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthHandIn/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthCost/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyHandIn/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(DailyCost/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumHandIn/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MonthSumCost/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDeposit/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(PreMonthBalance/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowDayBalance/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthHandIn/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(NowMonthCost/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginHandIn/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(MarginCost/10000)%> 
              </td>
            </TR>
<%
                MonthHandIn=0.0;    // �����ۼ�-����
                MonthCost=0.0;      // �����ۼ�-֧ȡ
                DailyHandIn=0.0;    // ���ձ䶯-����
                DailyCost=0.0;      // ���ձ䶯-֧ȡ
                MonthSumHandIn=0.0; // ���ºϼ�-����
                MonthSumCost=0.0;   // ���ºϼ�-֧ȡ
                NowDeposit=0.0;     // ���ڴ��
                PreMonthBalance=0.0;// �������
                NowDayBalance=0.0;  // �������
            	NowMonthHandIn=0.0;	// ����Ԥ��-����
            	NowMonthCost=0.0;	// ����Ԥ��-֧ȡ
            	MarginHandIn=0.0;	// ��Ԥ����-�Ͻ�
            	MarginCost=0.0;		// ��Ԥ����-֧ȡ
    		//�ϼ����
 %>
            <TR> 
              <!--���-->
              <!--��λ-->
              <td width="8%"  height="20" colspan="2" align="center" nowrap class="td-topright">
              <strong><%="�ϼ�"%></strong></td>
              <!--�����ۼ�-����-->
              <td height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthHandIn/10000)%> 
              </td>
              <!--�����ۼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthCost/10000)%> 
              </td>
              <!--���ձ䶯-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumDailyHandIn/10000)%> 
              </td>
              <!--���ձ䶯-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumDailyCost/10000)%> 
              </td>
              <!--���ºϼ�-����-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthSumHandIn/10000)%> 
              </td>
              <!--���ºϼ�-֧ȡ-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMonthSumCost/10000)%> 
              </td>
              <!--���ڴ��-->
              <td  height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowDeposit/10000)%> 
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumPreMonthBalance/10000)%>
              </td>
              <!--�������-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowDayBalance/10000)%> 
              </td>
              <!--����Ԥ��-����-->
              <td   height="20" align="right" nowrap class="td-topright">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowMonthHandIn/10000)%> 
              </td>
              <!--����Ԥ��-֧ȡ-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumNowMonthCost/10000)%> 
              </td>
              <!--��Ԥ����-�Ͻ�-->
              <td  height="20" class="td-topright" align="right">
              <%=QDailyAmountVaryDao.roundAmountForExcel(SumMarginHandIn/10000)%> 
              </td>
              <!--��Ԥ����-֧ȡ-->
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
                        <TD height=25 width="8%" nowrap><FONT size="2">��������:</FONT></TD>
                        <TD height=25 width="8%" nowrap><FONT size="2"><%=NameRef.getOfficeNameByID(sessionMng.m_lOfficeID)%></FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><FONT size="2">���ž���:</FONT></TD>
                        <TD height=25 width="5%" nowrap><FONT size="2"><%="&nbsp;"%></FONT></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="5%" nowrap><%="&nbsp;"%></TD>
                        <TD height=25 width="6%" nowrap><FONT size="2">�Ƶ�:</FONT></TD>
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
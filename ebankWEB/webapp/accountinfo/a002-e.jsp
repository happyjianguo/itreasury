<%--
/*
 * �������ƣ��˻������˵�.jsp
 * ����˵�����˻�����ѯҳ������
 * �������ߣ�liwang
 * ������ڣ�2007-01-26
 */
--%>

<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Config"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.iss.itreasury.util.Log4j"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.settlement.util.SETTConstant"%>
<%@ page import="com.iss.itreasury.ebank.util.OBHtml"%>
<%@ page import="com.iss.itreasury.settlement.util.NameRef"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.OBAccountBalanceQueryDao"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*"%>
<%@ page import="com.iss.itreasury.util.Database"%>
<%@ page import="com.iss.itreasury.loan.util.LOANConstant"%>

<%@ page import="com.iss.itreasury.settlement.print.IPrintTemplate" %>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
	<%
	response.setContentType("application/msexcel;charset=gbk");
    response.setHeader("Content-Disposition",";filename=\treport.xls");
	IPrintTemplate.showPrintHeadForExcel(out,false,"A4","",1,sessionMng.m_lOfficeID);
%>
<META http-equiv=Content-Type content="application/msexcel; charset=gbk"> 
	<%
        try
        {
        	
			

		//�������  ��Ӧ��̨��DataEntity
		double currentSum = 0.0;      ///���ںϼ�
		double depositSum = 0.0;     //������ϼ�
		double loanSum = 0.0;     //������ϼ�
		
		long lStatusID= -1; 
		
        //�����˻�
        double crt_dAc_mcapitallimitamount = 0.0;
		double crt_dMbalance = 0.0;
		long crt_lNaccounttypeid = -1;
		String crt_strSaccountno = "";
		double crt_dSubSum = 0.0;
		double crt_dSum = 0.0;

		//�����˻�ҳ������
		long lQueryType=-1;
		long lPayAccountIDEndCtrl=-1;
		long lPayAccountIDStartCtrl=-1;
		long lReceiveAccountIDEndCtrl=-1;
		long lReceiveAccountIDStartCtrl=-1;


		//ί���˻�
		double cgn_dAc_mcapitallimitamount = 0.0;
		double cgn_dMbalance = 0.0;
		long cgn_lNaccounttypeid = -1;
		String cgn_strSaccountno = "";
		double cgn_dSubSum = 0.0;

		//�����˻�
		java.sql.Timestamp fixed_tsAf_dtend = null;
		java.sql.Timestamp fixed_tsAf_dtstart = null;
		double fixed_dAf_mrate = 0.0;
		long fixed_lAf_ndepositterm = -1;
		double fixed_dMbalance = 0.0;
		double fixed_dMopenamount = 0.0;
		long fixed_lNaccounttypeid = -1;
		long fixed_lNstatusid = -1;
		String fixed_strSaccountno = "";
		double fixed_dSubSum = 0.0;
		double fixed_dSum = 0.0;
		long fixed_lNaccountID = -1;
		long fixed_lNtype=-1;

		//�����˻�
		java.sql.Timestamp loan_tsDtEndDate = null;
		java.sql.Timestamp loan_tsDtStartDate = null;
		double loan_dLoanBalance = 0.0;
		double loan_dMAmount = 0.0;
		long loan_lNaccounttypeid = -1;
		long loan_lNborrowclientid = -1;
		long loan_lNIntervalNum = -1;
		long loan_lNstatusid = -1;
		double loan_dRate = 0.0;
		String loan_strSaccountno = "";
		String loan_strSCONTRACTCODE = "";
		double loan_dSubSum = 0.0;

		//ҳ����ת����
		String strFailPageURL="";
		String strSuccessPageURL="";

		//ҳ�渨������
		String strAction = null;
		String strActionResult = Constant.ActionResult.FAIL;
		String strPreSaveResult = null;

		String strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strInterestStartDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
		String strModifyTime = null;		

		//��Request�л�ò���
		//ҳ����Ʋ���
		if (request.getAttribute("strActionResult") != null)
		{
				 strActionResult = (String)request.getAttribute("strActionResult");
		}
		if (request.getAttribute("strAction") != null)
		{
				 strAction = (String)request.getAttribute("strAction");
		}
		if (request.getAttribute("strPreSaveResult") != null)
		{
				 strPreSaveResult = (String)request.getAttribute("strPreSaveResult");
		}
        
		//ҵ�����
		String strTemp = null;
		
		strTemp = (String)request.getParameter("lStatusID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lStatusID = Long.valueOf(strTemp).longValue();
		}
        //�����˻�����
		strTemp = (String)request.getAttribute("crt_dAc_mcapitallimitamount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dAc_mcapitallimitamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("crt_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("crt_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("crt_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("crt_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("crt_dSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			crt_dSum = Double.valueOf(strTemp).doubleValue();
		}

		//ί���˻�����
		strTemp = (String)request.getAttribute("cgn_dAc_mcapitallimitamount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_dAc_mcapitallimitamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("cgn_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("cgn_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("cgn_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("cgn_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			cgn_dSubSum = Double.valueOf(strTemp).doubleValue();
		}

		//�����˻�����
		strTemp = (String)request.getAttribute("fixed_tsAf_dtend");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_tsAf_dtend = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("fixed_tsAf_dtstart");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_tsAf_dtstart = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("fixed_dAf_mrate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dAf_mrate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_lAf_ndepositterm");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lAf_ndepositterm = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("fixed_dMbalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dMbalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_dMopenamount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dMopenamount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("fixed_lNstatusid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNstatusid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("fixed_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("fixed_dSubsum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("fixed_dSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_dSum = Double.valueOf(strTemp).doubleValue();
		}
        //�����˻������˺�
		strTemp = (String)request.getAttribute("fixed_lNaccountID");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNaccountID = Long.valueOf(strTemp).longValue();
		}	
		//���޻���Ʒ�ֵ�����
		strTemp = (String)request.getAttribute("fixed_lNtype");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			fixed_lNtype = Long.valueOf(strTemp).longValue();
		}	
		

		//��������
		strTemp = (String)request.getAttribute("loan_tsDtEndDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_tsDtEndDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("loan_tsDtStartDate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_tsDtStartDate = DataFormat.getDateTime(strTemp);
		}
		strTemp = (String)request.getAttribute("loan_dLoanBalance");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dLoanBalance = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("loan_dMAmount");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dMAmount = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("loan_lNaccounttypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNaccounttypeid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_lNborrowclientid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNborrowclientid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_lNIntervalNum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNIntervalNum = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_lNstatusid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_lNstatusid = Long.valueOf(strTemp).longValue();
		}
		strTemp = (String)request.getAttribute("loan_dRate");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dRate = Double.valueOf(strTemp).doubleValue();
		}
		strTemp = (String)request.getAttribute("loan_strSaccountno");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_strSaccountno = strTemp;
		}
		strTemp = (String)request.getAttribute("loan_strSCONTRACTCODE");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_strSCONTRACTCODE = strTemp;
		}
		strTemp = (String)request.getAttribute("loan_dSubSum");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			loan_dSubSum = Double.valueOf(strTemp).doubleValue();
		}
		//add by xwhe 2008-11-24
		long lNoperationtypeid = 1;
		strTemp = (String) request.getParameter("operationtypeid");
		if (strTemp != null && strTemp.trim().length() > 0)
		{
			lNoperationtypeid = Long.valueOf(strTemp).longValue();
		}
		
		IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
	%>

	

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<style type="text/css">
<!--
.table1 {  border: 2px solid #000000}
.table2 {  border: 1px solid #000000}
.td-right {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
.td-leftbottom {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 1px}
.td-leftright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 1px}
.td-leftbottomright {  border-color: black black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom2right {  border-color: #000000 black black; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 0px}
.td-topbottom2 {  border-color: black black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-rightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 0px}
.td-right2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 0px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-toprightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 2px; border-left-width: 1px}
.td-topright2bottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 1px; border-left-width: 0px}
.td-topleftright2bottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 2px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom2 {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 2px; border-left-width: 1px}
.td-topleftrightbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
.td-topbottom {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 0px; border-bottom-width: 1px; border-left-width: 0px}
.td-topright {  border-color: black black #000000; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 0px; border-left-width: 0px}
body {
	font-family: "����";
	font-size: 14px;
}
td {
	font-family: "����";
	font-size: 12px;
}
.f16 {
	font-family: "����_GB2312";
	font-size: 16px;
}
.f14 {
	font-family: "����";
	font-size: 14px;
}
.f10 {
	font-family: "����";
	font-size: 10px;
	line-height:10px;
}
.f12 {
	font-family: "����";
	font-size: 12px;
}
.f22
{
  font-family:"����";
  font-size:22px;
}
.f15 {
	font-family: "����_GB2312";
	font-size: 16px;
}
.tnt {Writing-mode:tb-rl;Text-align:center;font-size:12px}
-->
</style>
<body>

     <table border="0" bordercolor="#999999"  width="65%"   cellspacing="0" cellpadding="0">
     <tr><td colspan="10" class="f22" align="center" height="50" >
     <b>�˻����</b>
     </td></tr>
     <tr><td class="f10" align="left" height="30" colspan="2">
     ������<%=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID))%>
     </td>
     <td class="f10" align="right" height="30" colspan="2">
     ���֣�<%=Constant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>
     </td>
     </tr>
     </table>

    <br>
 <%
 //��ʼ������
 	Collection resultColl = null;
    OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao();
    Iterator it = null;
    //�˻�����
	String sAcctType="";
	String sAcctNo="";
	String sAmount="";
	String sLimited="";
	//�����
    String sFixedAmount="";
	//������
	String sFixedBalance="";
	//������
	String sTerm="";
	//����
	String sRate="";
	//��ע
	String sTatus="";
 if(lNoperationtypeid == 1||lNoperationtypeid== 4)
 {
  %>
	<!--�����˻�-->
            <table border="0" bordercolor="#999999" class="table1" width="65%"  border="0" cellspacing="0" cellpadding="0" <%if(lNoperationtypeid != 1&&lNoperationtypeid != 4){ %>style="display:none;"<%} %>>
              
              <tr>                
                <td class="td-bottom"   colspan="4"  height="23" >&nbsp;&nbsp;&nbsp;&nbsp;�����˻�</td>                
              </tr>
              <tr> 
                <td class="td-rightbottom" nowrap colspan="1" height="23" align="center">�˻�����</td>
                <td class="td-rightbottom" nowrap colspan="1" height="23" align="center">�˺�</td>
                <td class="td-rightbottom" nowrap colspan="1" height="23" align="center">�ʽ����</td>
				<td class="td-bottom" nowrap colspan="1" height="23" align="center">����������</td>
              </tr>
<%    
       
		if(lNoperationtypeid == 1||lNoperationtypeid== 4)
        {
	    	resultColl = dao.seekCurrentBalace2(sessionMng.m_lClientID, sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,sessionMng.m_lUserID);
        }
		

	
		if (resultColl != null && (lNoperationtypeid == 1||lNoperationtypeid== 4))
		{
			it = resultColl.iterator();
		}		
		
		while( it != null && it.hasNext()&& (lNoperationtypeid == 1||lNoperationtypeid== 4) )
		{
			
			OBAccountBalanceCurrentInfo crt_info = (OBAccountBalanceCurrentInfo ) it.next();
			crt_dAc_mcapitallimitamount = crt_info.getAc_mcapitallimitamount();
			crt_dMbalance = crt_info.getMbalance();
			crt_lNaccounttypeid = crt_info.getNaccounttypeid();
			crt_strSaccountno = crt_info.getSaccountno();
			crt_dSubSum = crt_info.getSubSum();
			crt_dSum = crt_info.getSum();
			
			String strURL= "a004_c.jsp?strAction=current&lAccountID=" + crt_info.getAccountID() + "&strSuccessPageURL=a004_v.jsp&strFailPageURL=a004_v.jsp";
						
			sAcctType=SETTConstant.AccountType.getName(crt_lNaccounttypeid);
			if(crt_lNaccounttypeid==0)
				sAcctType="";
		    
		    //crt_strSaccountno -1 ��ʾ"С��"  -2 ��ʾ"����С��"
			if(crt_strSaccountno.equals("-1"))
			{
				sAcctNo="С��";
			}
			else if(crt_strSaccountno.equals("-2"))
			{
				//sAcctNo="����С��";
				sAcctNo="�ϼ�";
				currentSum = currentSum+crt_info.getMbalance();
			}
			else sAcctNo=crt_strSaccountno;

			//�ʽ����
			sAmount=DataFormat.formatDisabledAmount(crt_dMbalance); 
			if(sAmount.equals("&nbsp;"))
				sAmount="0.00";
			//����������
			sLimited=DataFormat.formatDisabledAmount(crt_dAc_mcapitallimitamount); 
			if(sLimited.equals("&nbsp;") )
				sLimited="0.00";
			if(crt_strSaccountno.equals("-1") || crt_strSaccountno.equals("-2") )
				sLimited="";

			//
			
			
			
			if(sAcctNo.equals("С��") || sAcctNo.equals("�ϼ�") )			
			{
				
%>
	        <tr align="center"  > 
                <td align="center"  class="td-rightbottom" align="left" height="18"><%=(sAcctType==null?"&nbsp;":sAcctType)%></td>
                <td align="center"  class="td-rightbottom" height="18"><%=(sAcctNo==null ? "&nbsp;" : sAcctNo)%></td>
                <td class="td-rightbottom" height="18" align="right" nowrap><%=(sAmount==null?"&nbsp;":sAmount)%></td>
	            <td class="td-bottom" height="18" align="right" nowrap><%=(sLimited==null?"&nbsp;":sLimited)%>
	            </td>
            </tr>
<%}
			
			else
			{

			    /*if(!Config.getBoolean(EBANK_DEPOSITNO_NOLINE,false))
			    {
			    
					String[] straccountno = crt_strSaccountno.split("-");
					int len = straccountno.length;	
					String[] straccountno1 = new String[len];
					String straccountno2 ="";
					
					for (int s=0;s<len;s++)
					{
						
						straccountno1[s] = straccountno[s];
						straccountno2+=straccountno1[s];
					}
					crt_strSaccountno==null ? "&nbsp;" : straccountno2;
				}*/
				//ȥ���˺ŵġ�������
				String straccountno = NameRef.getNoLineAccountNo(crt_strSaccountno);
				
%>
            <tr align="center"  > 
                <td class="td-rightbottom" height="18" align="left"><%=(sAcctType==null?"&nbsp;":sAcctType)%>&nbsp;</td>
                <td class="td-rightbottom" height="18">
					
					   
						<%=(crt_strSaccountno==null ? "&nbsp;" : straccountno)%>&nbsp;
					
				</td>
                <td class="td-rightbottom" height="18" align="right" nowrap><%=(sAmount==null?"&nbsp;":sAmount)%>
	            </td>
	            <td class="td-bottom" height="18" align="right" nowrap><%=(sLimited==null?"&nbsp;":sLimited)%>
	            </td>
            </tr>
<%	
			}
		}
		
%>         
      </table>	
<%
}
if(lNoperationtypeid == 2||lNoperationtypeid== 4)
{
 %>		
			
	<br>
	<!--�����˻�-->		

      <table border="0" bordercolor="#999999" class="table1" width="65%"  border="0" cellspacing="0" cellpadding="0" <%if(lNoperationtypeid != 2&&lNoperationtypeid!= 4){%>style="display:none;"<%} %>>
        
        <tr >           
          <td colspan="9" height="22" class="td-bottom" >&nbsp;&nbsp;&nbsp;&nbsp;�����˻�</td>          
        </tr>
		<tr  >           	  
          <td colspan="9"height="22"  class="td-bottom">		
		 ��ʾ�ѽ������˻���Ϣ</td>
        </tr>
        
      
      
        <tr align=center > 
          <td class="td-rightbottom" height="23" >�˻�����</td>
          <td class="td-rightbottom" height="23" >�˺�/���˺�</td>
          <td class="td-rightbottom" height="23" >������</td>
          <td class="td-rightbottom" height="23" >������</td>
          <td class="td-rightbottom" height="23" >����/Ʒ��</td>
		  <td class="td-rightbottom" height="23" >����</td>		  
          <td class="td-rightbottom" height="23" >�����</td>
		  <td class="td-rightbottom" height="23" >������</td>
          <td class="td-bottom" height="23" >��ע</td>
        </tr>
<%
		
	    if(lNoperationtypeid == 2||lNoperationtypeid== 4)
        {
			resultColl = dao.seekFixedBalace(sessionMng.m_lClientID,sessionMng.m_lCurrencyID, sessionMng.m_lOfficeID,lStatusID,sessionMng.m_lUserID);
        }
		it = null;		
	
		if (resultColl != null &&(lNoperationtypeid == 2||lNoperationtypeid== 4))
		{
			it = resultColl.iterator();
		}	
		
        while( it != null && it.hasNext()&&(lNoperationtypeid == 2||lNoperationtypeid== 4) )
		{
			OBAccountBalanceFixedInfo fixed_info = (OBAccountBalanceFixedInfo) it.next();
			fixed_tsAf_dtend = fixed_info.getAf_dtend();
			fixed_tsAf_dtstart = fixed_info.getAf_dtstart();
			fixed_dAf_mrate = fixed_info.getAf_mrate();
			fixed_lAf_ndepositterm = fixed_info.getAf_ndepositterm();
			
			fixed_dMbalance = fixed_info.getMbalance();
			fixed_dMopenamount = fixed_info.getMopenamount();
			fixed_lNaccounttypeid = fixed_info.getNaccounttypeid();
			fixed_lNstatusid = fixed_info.getNstatusid();
			fixed_strSaccountno = fixed_info.getSaccountno();
			fixed_dSubSum = fixed_info.getSubsum();
			fixed_dSum = fixed_info.getSum();
			fixed_lNtype = fixed_info.getNtype();
			System.out.println("fixed_info.getNtype()��ֵ��:"+fixed_info.getNtype());
            
			
			//�˻�����
			sAcctType=SETTConstant.AccountType.getName(fixed_lNaccounttypeid);
			if(fixed_lNaccounttypeid==0 || fixed_lNaccounttypeid ==-1 )
				sAcctType="";
		    
		    //�˺�
			sAcctNo=fixed_strSaccountno;
			if(fixed_strSaccountno.equals("-2"))
			{
				sAcctNo="С��";	
				//���������
				//depositSum = depositSum+fixed_dMbalance;
			}	
			//�����
			if(fixed_dMopenamount==-1)
				sFixedAmount="";
			else 
				sFixedAmount=DataFormat.formatDisabledAmount(fixed_dMopenamount); 
			//������
			if(fixed_dMbalance==-1)
				sFixedBalance="";
			else
				sFixedBalance=DataFormat.formatDisabledAmount(fixed_dMbalance); 
			if(sFixedBalance.equals("&nbsp;") && fixed_tsAf_dtstart!=null )
				sFixedBalance="0.00";
			//����
			if(fixed_lAf_ndepositterm==-1 || fixed_lAf_ndepositterm==-2)
			{
			    sTerm="";
			}
			else if(fixed_lNtype==6)
			{
				fixed_lAf_ndepositterm = fixed_lAf_ndepositterm - 10000;
				sTerm=fixed_lAf_ndepositterm+"��";
			}	
			else if(fixed_lNtype==5)
			{
				sTerm=fixed_lAf_ndepositterm+"����";
			}

			//�ж϶��ڻ���֪ͨ
			String strURL = "" ;
			if(fixed_lNtype == 5)
			{
			  	System.out.println("���붨���ж�" );
				strURL= "a004_c.jsp?strAction=fixed&strDepositNo=" + fixed_strSaccountno + "&lSubAccountID=" + fixed_info.getSubAccountID() + "&lAccountID=" + fixed_info.getAccountID() + "&strSuccessPageURL=a005_v.jsp&strFailPageURL=a005_v.jsp";
			}
			else if(fixed_lNtype == 6)
			{
				System.out.println("����֪ͨ�ж�" );
				strURL= "a004_c.jsp?strAction=notice&strDepositNo=" + fixed_strSaccountno + "&lSubAccountID=" + fixed_info.getSubAccountID() + "&lAccountID=" + fixed_info.getAccountID() + "&strSuccessPageURL=a006_v.jsp&strFailPageURL=a006_v.jsp";
			}

			//����
			if(fixed_dAf_mrate==-1)
			{
				sRate="";
			}
			else
			{
				if(fixed_dAf_mrate>0)			
				{
					sRate=DataFormat.formatListAmount(fixed_dAf_mrate)+"%"; 
				}
				else
				{
					sRate=DataFormat.formatListAmount(fixed_dAf_mrate);
				}
			}
			if(fixed_dAf_mrate==-2)
			{
				sRate="С��";
				depositSum = depositSum+fixed_dMbalance;
			}
			//else if(sRate.equals("&nbsp;%") && fixed_tsAf_dtstart!=null )
			//	sRate="0.00%";
			//��ע
			if(fixed_lNstatusid==-1)
			{
				sTatus="";
			}	
			else
			{
				if(SETTConstant.AccountType.isFixAccountType(fixed_lNtype))
				{
					if(fixed_lNstatusid==SETTConstant.SubAccountStatus.FINISH)
					{
						sTatus=SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
					}
					else
					{
						if(fixed_info.getAf_dtend()!=null)
						{
							if(fixed_info.getAf_dtend().after(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)))
							{
								sTatus="δ����";
							}
							else
							{
								sTatus="�ѵ���";
							}
						}
					}
				}
				else
				{
					sTatus=SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
				}
			}
%>		
		<tr align="center" > 
	      <!--�˻�����-->
		  <td class="td-rightbottom" align="left" height="22"><%=(sAcctType==null?"":sAcctType)%>&nbsp;</td>
		  <!--�˺�/���ݺ�-->
          <td class="td-rightbottom" height="22"  align=center> 
		  <%
		    /*String[] strfixedaccountno = fixed_strSaccountno.split("-");
			int fixedlen = strfixedaccountno.length;	
			String[] strfixedaccountno1 = new String[fixedlen];
			String strfixedaccountno2 ="";
			
			for (int t=0;t<fixedlen;t++)
			{
				
				strfixedaccountno1[t] = strfixedaccountno[t];
				strfixedaccountno2+=strfixedaccountno1[t];
			}*/
		  String strfixedaccountno2 = NameRef.getNoLineAccountNo(fixed_strSaccountno);
		  if (fixed_tsAf_dtstart!=null)
		  {
			  System.out.println("����strURL��ֵ��:\"" + strURL + "\"");
		  %>
			<%=(fixed_strSaccountno.equals("0")?"":fixed_strSaccountno)%>
		  <%
		  }
          else
		  {
		  %>
			
		     <%=(fixed_strSaccountno.equals("0")?"":strfixedaccountno2)%>
          <%
		  }	
		  %>&nbsp;
		  </td>
		  <!--������-->
          <td class="td-rightbottom"   height="22"><%=(fixed_tsAf_dtstart==null?"":DataFormat.formatDate(fixed_tsAf_dtstart))%></td>
		  <!--������-->
          <td class="td-rightbottom"  height="22"><%=(fixed_tsAf_dtend==null?"":DataFormat.formatDate(fixed_tsAf_dtend))%></td>
		  <!--����/Ʒ��-->
          <td class="td-rightbottom"  align="right" height="22" ><%=sTerm%></td>
		  <!--����-->
		  <td class="td-rightbottom"  height="22"  <%if(sRate!=null&&sRate.equals("С��")) {%>align=center<% }else {%>align=right<%}%>><%=sRate%></td>
		  <!--�����-->
          <td class="td-rightbottom"  height="18" align="right" nowrap><%=sFixedAmount%></td>
		  <!--������-->
		  <td class="td-rightbottom"  height="22" align="right" nowrap><%=sFixedBalance%></td>
		  <!--��ע-->
          <td class="td-bottom"  height="22"><%=sTatus%></td>
        </tr>      
<%
		}
        
%>
		<tr align="center" > 
	      <!--�˻�����-->
		  <td class="td-rightbottom"  align="left" height="22">&nbsp;</td>
		  <!--�˺�/���ݺ�-->
          <td class="td-rightbottom" height="22" >&nbsp; 		  
		  </td>
		  <!--������-->
          <td class="td-rightbottom"  height="22">&nbsp;</td>
		  <!--������-->
          <td class="td-rightbottom"  height="22">&nbsp;</td>
		  <!--����/Ʒ��-->
          <td class="td-rightbottom"  height="22" >&nbsp;</td>
		  <!--����-->
		  <td class="td-rightbottom"  height="22" >&nbsp;</td>
		  <!--�����-->
          
      <td class="td-rightbottom"  height="22" align="right" nowrap><strong>�������ܼƣ�</strong></td>
		  <!--������-->
		  <td class="td-rightbottom"  height="22" align="right" nowrap><%=DataFormat.formatDisabledAmount(depositSum)%></td>
		  <!--��ע-->
          <td class="td-bottom"  height="22">&nbsp;</td>
        </tr>	
      </table>
 <%
 }
 if(lNoperationtypeid == 3 ||lNoperationtypeid== 4)
 {
  %>
      <br>
	  <!--�����˻�-->
      <table border="0" bordercolor="#999999" class="table1" width="65%"  border="0" cellspacing="0" cellpadding="0" <%if(lNoperationtypeid != 3&&lNoperationtypeid!= 4){%>style="display:none;"<%} %>>
        
        <tr >          
          <td colspan="10"height="25" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;�����˻�</td>
        </tr>
        
      
        <tr align="center" > 
          <td class="td-rightbottom" height="23" >�˻�����</td>
          <td class="td-rightbottom" height="23"  >�˺�/��ͬ��</td>
          <td class="td-rightbottom" height="23" >��λ</td>
          <td class="td-rightbottom" height="23" >��ʼ��</td>
          <td class="td-rightbottom" height="23" >������</td>
          <td class="td-rightbottom" height="23">����</td>
          <td class="td-rightbottom"  height="23">������</td>
          <td class="td-rightbottom"  height="23">�������</td>
          <td class="td-rightbottom" height="23">����</td>
		  <td class="td-bottom" height="23">��ͬ״̬</td>
        </tr>
<%
		//������
        String sLoanAmount="";
		//�������
		String sLoanBalance="";
		//��λ
		String sBrwClient="";
		//��ͬ״̬
		String sContractStatus="";
        if(lNoperationtypeid == 3 ||lNoperationtypeid== 4)
        {
        	resultColl = dao.seekLoanBalace(sessionMng.m_lClientID,sessionMng.m_lCurrencyID, sessionMng.m_lOfficeID,sessionMng.m_lUserID);
        }
		it = null;		
	
		if (resultColl != null && (lNoperationtypeid == 3 ||lNoperationtypeid== 4 ))
		{
			it = resultColl.iterator();
		}	
		
        while( it != null && it.hasNext()&& (lNoperationtypeid == 3 ||lNoperationtypeid== 4 ) )
		{
			OBAccountBalanceLoanInfo loan_info = (OBAccountBalanceLoanInfo) it.next();
            loan_tsDtEndDate = loan_info.getDtEndDate();
			loan_tsDtStartDate = loan_info.getDtStartDate();
			loan_dLoanBalance = loan_info.getLoanBalance();
			loan_dMAmount = loan_info.getMAmount();
			loan_lNaccounttypeid = loan_info.getNaccounttypeid();
			loan_lNborrowclientid = loan_info.getNborrowclientid();
			loan_lNIntervalNum = loan_info.getNIntervalNum();
			loan_lNstatusid = loan_info.getNstatusid();
			loan_dRate = loan_info.getRate();
			loan_strSaccountno = loan_info.getSaccountno();
			loan_strSCONTRACTCODE = loan_info.getSCONTRACTCODE();
			loan_dSubSum = loan_info.getSubSum();
			
			//�˻�����
			sAcctType=SETTConstant.AccountType.getName(loan_lNaccounttypeid);
			if(loan_lNaccounttypeid==0 || loan_lNaccounttypeid==-1 )
				sAcctType="";	
			//��λ
            if(loan_lNborrowclientid==-1)
				sBrwClient="";
			else
				sBrwClient=NameRef.getClientNameByID(loan_lNborrowclientid);

			//������
			if(loan_dMAmount==-1)
				sLoanAmount="";
			else 
				sLoanAmount=DataFormat.formatListAmount(loan_dMAmount); 
			//�������
			if(loan_dLoanBalance==-1)
				sLoanBalance="";
			else
				sLoanBalance=DataFormat.formatListAmount(loan_dLoanBalance);
			if(sLoanBalance.equals("&nbsp;") && loan_tsDtStartDate!=null && loan_tsDtEndDate!=null)
				sLoanBalance="0.00";
			//����
			if(loan_lNIntervalNum==-1 )
			    sTerm="";
			else if(loan_lNIntervalNum==-2 )
			{
				sTerm="С��";
				loanSum=loanSum+loan_dLoanBalance;
			}
			else 
				sTerm=loan_lNIntervalNum+"����";

			if(sTerm.equals("С��") && !loan_strSaccountno.equals("") )
				sTerm="";
			//����
			if(loan_dRate==-1 || loan_tsDtStartDate == null || loan_tsDtEndDate == null )
				sRate="";
			else 
				sRate=DataFormat.formatRate(loan_dRate)+"%"; 
			if(sRate.equals("&nbsp;%"))
				sRate="0.00%";
				
			String strURL= "a004_c.jsp?strAction=loan&lAccountID=" + loan_info.getAccountID() + "&lContractID=" + loan_info.getContractID() + "&strSuccessPageURL=a007_v.jsp&strFailPageURL=a007_v.jsp";	
			//��ͬ״̬  
			if(loan_lNstatusid==-1)
				sContractStatus="";
			else
				sContractStatus=""+LOANConstant.ContractStatus.getName(loan_lNstatusid);
			    //sContractStatus=""+loan_lNstatusid;
			    if(loan_dMAmount==0.0)
				
				continue;	
%>
          <tr align="center"> 
	        <!--�˻�����-->
            <td class="td-rightbottom"  height="18"><%=sAcctType%>&nbsp;</td>
		    <!--�˺�/��ͬ��  ����ֵ����-->
<%
/*
String[] strloanaccountno = loan_strSaccountno.split("-");
int loanlen = strloanaccountno.length;	
String[] strloanaccountno1 = new String[loanlen];
String strloanaccountno2 ="";

for (int m=0;m<loanlen;m++)
{
	
	strloanaccountno1[m] = strloanaccountno[m];
	strloanaccountno2+=strloanaccountno1[m];
}
*/
String strloanaccountno2 = NameRef.getNoLineAccountNo(loan_strSaccountno);
          if(loan_tsDtStartDate!=null && loan_tsDtEndDate!=null)
			{
%>
            <td class="td-rightbottom" height="18" >
		    
			<%=(loan_strSaccountno==null?"":loan_strSaccountno)%>&nbsp;		 
		    </td>
<%
			}
            else
			{
%>
            <td class="td-rightbottom"  height="18">
		    <%=(loan_strSaccountno==null?"":strloanaccountno2)%>&nbsp;		 
		    </td>
<%
			}
%>
		    <!--��λ-->
            <td class="td-rightbottom"  align="left"  height="18"><%=sBrwClient%>&nbsp;</td>
	        <!--��ʼ��-->
            <td class="td-rightbottom"  height="18"><%=(loan_tsDtStartDate==null?"":DataFormat.formatDate(loan_tsDtStartDate))%></td>
		    <!--������-->
            <td class="td-rightbottom"  height="18"><%=(loan_tsDtEndDate==null?"":DataFormat.formatDate(loan_tsDtEndDate))%></td>
		    <!--����-->
            <td class="td-rightbottom" <%if(sTerm!=null&&sTerm.equals("С��")) {%>align=center<% }else {%>align=right<%}%> height="18"><%=sTerm%></td>
		    <!--������-->
            <td class="td-rightbottom"  height="18" align="right" nowrap> 
              <div align="right"><%=sLoanAmount%></div>
            </td>
		    <!--�������-->
            <td class="td-rightbottom"  height="18" align="right" nowrap> 
              <div align="right"><%=sLoanBalance%></div>
            </td>
		    <!--����-->
            <td class="td-rightbottom"  height="18" align="right"><%=sRate%></td>
		    <!--��ͬ״̬-->
		    <td class="td-bottom"  height="18" align="center"><%=sContractStatus%></td>
        </tr>
<%
		}
       
%>     
	<tr align="center" > 
	        <!--�˻�����-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--�˺�/��ͬ��  ����ֵ����-->
            <td class="td-rightbottom"  height="18" >&nbsp;
		    </td>
		    <!--��λ-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
	        <!--��ʼ��-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--������-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--����-->
            <td class="td-rightbottom"  height="18">&nbsp;</td>
		    <!--������-->
            
      <td class="td-rightbottom"  height="18" align="right" nowrap> <strong>��������ܼƣ�</strong> 
      </td>
		    <!--�������-->
            <td class="td-rightbottom"  height="18" align="right" nowrap> 
              <div align="right"><%=DataFormat.formatListAmount(loanSum)%></div>
            </td>
		    <!--����-->
            <td class="td-rightbottom"  height="18" align="right">&nbsp;</td>
		    <!--��ͬ״̬-->
		    <td class="td-bottom"  height="18" align="right">&nbsp;</td>
        </tr>
   
      </table>
<br>
<%} %>
      <%-- 
      <!--��֤���˻�-->	
      <!--�˴��������˻�����ȥ����,Ҫ�����ʹ�ã�����VSS�ϻ��2007-02-01�汾���޸ģ��������ص㣺����-->		
<table border="0" bordercolor="#999999" class="table1" width="65%"  border="0" cellspacing="0" cellpadding="0">
        
        <tr  >          
          <td colspan="8" height="25" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;��֤���˻�</td>
        </tr>
		<tr align="center" > 
          <td class="td-rightbottom" height="23" >�˻�����</td>
          <td class="td-rightbottom" height="23"  >�˺�/���ݺ�</td>
          <td class="td-rightbottom" height="23" >������</td>
          <td class="td-rightbottom" height="23" >������</td>
          <td class="td-rightbottom" height="23" >����</td>
          <td class="td-rightbottom" height="23">�����</td>
          <td class="td-rightbottom"  height="23">������</td>
          <td class="td-rightbottom"  height="23">��ע</td>
        </tr>	
	
		<% 
				double marginDepositSum = 0.00;
				Collection resultMarginColl = dao.seekMarginBalace(
				sessionMng.m_lClientID, sessionMng.m_lCurrencyID,
				sessionMng.m_lOfficeID, lStatusID);

				it = null;

				if (resultMarginColl != null) {
					it = resultMarginColl.iterator();
				}

				while (it != null && it.hasNext()) {
					OBAccountBalanceFixedInfo fixed_info = (OBAccountBalanceFixedInfo) it
					.next();
					fixed_tsAf_dtend = fixed_info.getAf_dtend();
					fixed_tsAf_dtstart = fixed_info.getAf_dtstart();
					fixed_dAf_mrate = fixed_info.getAf_mrate();
					fixed_lAf_ndepositterm = fixed_info.getAf_ndepositterm();

					double fixed_dMbalance1 = fixed_info.getMbalance();
					fixed_dMopenamount = fixed_info.getMopenamount();
					fixed_lNaccounttypeid = fixed_info.getNaccounttypeid();
					fixed_lNstatusid = fixed_info.getNstatusid();
					fixed_strSaccountno = fixed_info.getSaccountno();
					fixed_dSubSum = fixed_info.getSubsum();
					fixed_dSum = fixed_info.getSum();
					fixed_lNtype = fixed_info.getNtype();
					System.out.println("fixed_lNaccounttypeid��ֵ��:"
					+ fixed_lNaccounttypeid);
			
					//�˻�����
					sAcctType = SETTConstant.AccountType
					.getName(fixed_lNaccounttypeid);
					if (fixed_lNaccounttypeid == 0
					|| fixed_lNaccounttypeid == -1)
				sAcctType = "";

					//�˺�
					sAcctNo = fixed_strSaccountno;
					if (fixed_strSaccountno.equals("-2")) {
				sAcctNo = "С��";
				//���������
				//depositSum = depositSum+fixed_dMbalance;
					}
					//�����
					if (fixed_dMopenamount == -1)
				sFixedAmount = "";
					else
				sFixedAmount = DataFormat
						.formatListAmount(fixed_dMopenamount);
					//������
					if (fixed_dMbalance1 == -1)
				sFixedBalance = "";
					else
				sFixedBalance = DataFormat
						.formatListAmount(fixed_dMbalance1);
					if (sFixedBalance.equals("&nbsp;")
					&& fixed_tsAf_dtstart != null)
				sFixedBalance = "0.00";

					//�жϱ�֤��
					String strURL = "";
					//if(SETTConstant.AccountType.isMarginAccountType(fixed_lNaccounttypeid))
					//{ 
					System.out.println("���뱣֤���ж�");
					strURL = "a004_c.jsp?strAction=fixed&strDepositNo="
					+ fixed_strSaccountno
					+ "&lSubAccountID="
					+ fixed_info.getSubAccountID()
					+ "&lAccountID="
					+ fixed_info.getAccountID()
					+ "&strSuccessPageURL=a005_v.jsp&strFailPageURL=a005_v.jsp";
					//}

					//����
					if (fixed_dAf_mrate == -1) {
				sRate = "";
					} else {
				if (fixed_dAf_mrate > 0) {
					sRate = DataFormat
					.formatListAmount(fixed_dAf_mrate)
					+ "%";
				} else {
					sRate = DataFormat
					.formatListAmount(fixed_dAf_mrate);
				}
					}
					if (fixed_dAf_mrate == -2) {
				sRate = "С��";
				marginDepositSum = marginDepositSum + fixed_dMbalance1;
					}
					//else if(sRate.equals("&nbsp;%") && fixed_tsAf_dtstart!=null )
					//	sRate="0.00%";
					//��ע
					if (fixed_lNstatusid == -1) {
				sTatus = "";
					} else {
				if (SETTConstant.AccountType
						.isFixAccountType(fixed_lNtype)) {
					if (fixed_lNstatusid == SETTConstant.SubAccountStatus.FINISH) {
						sTatus = SETTConstant.SubAccountStatus
						.getName(fixed_lNstatusid);
					} else {
						if (fixed_info.getAf_dtend().after(
						Env.getSystemDate(
						sessionMng.m_lOfficeID,
						sessionMng.m_lCurrencyID))) {
					sTatus = "δ����";
						} else {
					sTatus = "�ѵ���";
						}
					}
				} else {
					sTatus = SETTConstant.SubAccountStatus
					.getName(fixed_lNstatusid);
				}
					}
		%>
		<tr align="center" >
			<!--�˻�����-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=(sAcctType == null ? "" : sAcctType)%>
			</td>
			<!--�˺�/���ݺ�-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<% 
						if (fixed_tsAf_dtstart != null) {
						System.out.println("strURL��ֵ��:" + strURL);
				%>
				<%=(fixed_strSaccountno.equals("0") ? ""
										: fixed_strSaccountno)%>
				
				<% 
				} else {
				%>

				<%=(fixed_strSaccountno.equals("0") ? ""
										: fixed_strSaccountno)%>
				<% 
				}
				%>
			</td>
			<!--������-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=(fixed_tsAf_dtstart == null ? "" : DataFormat
									.formatDate(fixed_tsAf_dtstart))%>
			</td>
			<!--������-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=(fixed_tsAf_dtend == null ? "" : DataFormat
									.formatDate(fixed_tsAf_dtend))%>
			</td>
			<!--����-->
			<td class="td-rightbottom"  align="left" height="18" >&nbsp;
				<%=sRate%>
			</td>
			<!--�����-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=sFixedAmount%>
			</td>
			<!--������-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=sFixedBalance%>
			</td>
			<!--��ע-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;
				<%=sTatus%>
			</td>
		</tr>
		<%
		}
		
		%>
		<tr align="center" >
			<!--�˻�����-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--�˺�/���ݺ�-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;
			</td>
			<!--������-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--������-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--����-->
			<td  class="td-rightbottom"  align="left" height="18">&nbsp;</td>
			<!--�����-->

			<td  class="td-rightbottom"  align="left" height="18">
				&nbsp;<strong>�������ܼƣ�</strong>
			</td>
			<!--������-->
			<td  class="td-rightbottom"  align="left" height="18">
				&nbsp;<%=DataFormat.formatListAmount(marginDepositSum)%>
			</td>
			<!--��ע-->
			<td class="td-rightbottom"  align="left" height="18">&nbsp;</td>
		</tr>
	</table>
	

      --%>
            

      
      <table border="0" bordercolor="#999999"  width="65%"  border="0" cellspacing="0" cellpadding="0">
	    <tr> 
	    <td  colspan="3" align="left" valign="top" height="16" ><br>
<%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      <span class="td-rightbottom">��ѯ���ڣ�<%=ts.toString().substring(0,10)%> ��ѯʱ�䣺<%=ts.toString().substring(10,19)%></span><br>
	    </td>
	   </tr>
        <tr> 
          <td width="471"> 
            <div align="right"></div>
          </td>
          <td width="49"> 
            <div align="right"></div>
          </td>
          <td width="50"> 
            
          </td>
        </tr>
      </table>
    </form> 

</BODY>
</HTML>


<%				
		OBHtml.showOBHomeEnd(out);
	    }
		catch( Exception exp )
		{
			Log.print(exp.getMessage());
		}  
		
%>
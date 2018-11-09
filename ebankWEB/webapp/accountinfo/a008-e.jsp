<%@ page contentType="text/html;charset=gbk"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Log"%>
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
<%@ page import="com.iss.itreasury.ebank.privilege.dao.OB_UserDAO"%>
<%@ page import="com.iss.itreasury.ebank.privilege.dataentity.OB_UserInfo"%>

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
					// �û���¼��� 
				if (sessionMng.isLogin() == false)
				{
					OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E002");
					out.flush();
					return;
				}
		

				// �ж��û��Ƿ���Ȩ�� 
				if (sessionMng.hasRight(request) == false)
				{
					out.println(sessionMng.hasRight(request));
					OBHtml.showCommonMessage(out, sessionMng, "", "",1, "Gen_E003");
					out.flush();
					return;
				}
					

				//�������  ��Ӧ��̨��DataEntity
				
				double currentSum = 0.0;      ///���ںϼ�
				double depositSum = 0.0;     //������ϼ�
				double loanSum = 0.0;     //������ϼ�
				
				long lStatusID= -1; 
				//�˻�����
				long accountType=-1;
				//����ҳ���д������˻����ͷֱ���ʾ�����˻��������˻��������˻� ���ж���
				//��1�������˻����� 2.�����˻����͡�3.�����˻�����
				String clientCode="";//�ͻ����
				
				//�����˻�
				double crt_dAc_mcapitallimitamount = 0.0;
				double crt_dMbalance = 0.0;
				long crt_lNaccounttypeid = -1;
				String crt_strSaccountno = "";
				double crt_dSubSum = 0.0;
				double crt_dSum = 0.0;
				String crt_strSaccountName="";

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
				String Temp = null;
               //�˻�����
               
                Temp = (String)request.getAttribute("accountType");
				if (Temp != null && Temp.trim().length() > 0)
				{
					accountType=Long.valueOf(Temp).longValue();
				}
				
				//�ͻ����
				
				Temp = (String)request.getAttribute("clientCode");
				if (Temp != null && Temp.trim().length() > 0)
				{
					clientCode=Temp;
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
				IPrintTemplate.showPrintHead(out,true,"A4","",1,sessionMng.m_lOfficeID);
				long lPageLine = 20;
				long lLine = 0;
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



<%
		
		OB_UserDAO dao = new OB_UserDAO();
		Collection c = null;
		Iterator it = null;
	    String	sloginno = "";
		long clientID=0;
	 String clientName="";



		
		c=dao.findUserByCondition(sessionMng.m_lClientID,1,1);
		if (c != null)
		{
			it = c.iterator();
		}		
		OBAccountBalanceQueryDao Accountdao = new OBAccountBalanceQueryDao();
		   Collection resultColl=null;
			
			
			Iterator it1 = null;
//			�˻�����
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
	
	
	%>
<form name='formV002' method='post' action='a008-v.jsp'>
<input type="hidden" name="strSuccessPageURL" value="../a008-v.jsp">
		<input type="hidden" name="strFailPageURL" value="../a008-v.jsp">
 
   <%if (accountType==1) 
   {
   %> 
	<!--�����˻�-->
	<table border="0" bordercolor="#999999"  width="85%"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="4">
     <b>
     �˻����</b>
     </td></tr>
    
     </table>
	<table width="99%" border="0" cellspacing="0" cellpadding="0" ��class="table1">
              
              
              <tr >                
                <td colspan="4"height="22" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;
				�����˻�:
				
				</td>                
              </tr>
              
            
              <tr align=center > 
                <td class=td-rightbottom width="15%" height="23" nowrap colspan="1">�˻�����</td>
                <td class=td-rightbottom width="10%" height="23" nowrap colspan="1">�˺�</td>
                <td class=td-rightbottom width="50%" height="23" nowrap colspan="1">�˻�����</td>
                <td class=td-bottom width="25%" height="23" nowrap colspan="1">�ʽ����</td>
				
              </tr>
   <%
			
		
			
			while( it != null && it.hasNext() )
			{
				OB_UserInfo info = (OB_UserInfo ) it.next();
				clientID=info.getNClientId();
			    clientName=info.getClientName();
				resultColl = Accountdao.seekCurrentBalace2(clientID,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID);
				if (resultColl != null)
				{					
					it1 = resultColl.iterator();
				}	
				while( it1 != null && it1.hasNext() )
				{
					OBAccountBalanceCurrentInfo crt_info = (OBAccountBalanceCurrentInfo ) it1.next();
					
					
					crt_dAc_mcapitallimitamount = crt_info.getAc_mcapitallimitamount();
					
					crt_dMbalance = crt_info.getMbalance();
					sAmount=DataFormat.formatListAmount(crt_dMbalance);
					crt_lNaccounttypeid = crt_info.getNaccounttypeid();
					crt_strSaccountno = crt_info.getSaccountno();
					crt_dSubSum = crt_info.getSubSum();
					crt_dSum = crt_info.getSum();
					crt_strSaccountName=crt_info.getSname();
					String strURL= "a004_c.jsp?strAction=current&lAccountID=" + crt_info.getAccountID() + "&strSuccessPageURL=a004_v.jsp&strFailPageURL=a004_v.jsp";
					sAcctType=SETTConstant.AccountType.getName(crt_lNaccounttypeid);
					
					if(crt_lNaccounttypeid==0)
						sAcctType="";
				    
				    //crt_strSaccountno -1 ��ʾ"С��"  -2 ��ʾ"����С��"
					if(crt_strSaccountno.equals("-1"))
					{
						sAcctNo="����С��";
					}
					else if(crt_strSaccountno.equals("-2"))
					{
						//sAcctNo="����С��";
						sAcctNo="С��";
						currentSum = currentSum+crt_info.getMbalance();
					}
					else sAcctNo=crt_strSaccountno;

					//�ʽ����
					if(sAmount.equals("&nbsp;"))
						sAmount="0.00";
					if (sAmount.equals("0.00"))
						crt_strSaccountName=DataFormat.formatString(NameRef.getClientNameByID(sessionMng.m_lClientID));
					
					//����������
					sLimited=DataFormat.formatListAmount(crt_dAc_mcapitallimitamount); 
					if(sLimited.equals("&nbsp;") )
						sLimited="0.00";
					if(crt_strSaccountno.equals("-1") || crt_strSaccountno.equals("-2") )
						sLimited="";
					
					//��ҳ�ж�
					
			
					
					
					
					if (sAcctNo.equals("С��"))
					{
									
					%>
					   <tr align="center"  borderColor=#999999> 
	                         <td align="center"  class=td-rightbottom height="18" nowrap colspan="1"><%=(sAcctType==null?"":sAcctType)%>&nbsp;</td>
	                         <td align="center"  class=td-rightbottom height="18" nowrap colspan="1"><%=(sAcctNo==null ? "" : sAcctNo)%>&nbsp;</td>
	                         <td class=td-rightbottom height="18" align="left" nowrap colspan="1"><%=(crt_strSaccountName==null?"":crt_strSaccountName)%>&nbsp;</td>
	                         <td class=td-bottom height="18" align="right" nowrap colspan="1"><%=(sAmount==null?"":sAmount)%>&nbsp;</td>
		                     
		                     
	                       </tr>  
					
					<% 
					
						
				      }
					else if (sAcctNo.equals("����С��"))
						continue;	
					else
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
					%>
					<tr align="center"  > 
	                         <td class=td-rightbottom height="18" align="center"><%=(sAcctType==null?"":sAcctType)%>&nbsp;</td>
	                         <td class=td-rightbottom height="18" align="center"><%=(crt_strSaccountno==null ? "&nbsp;" : straccountno2)%>&nbsp;</td>
					         <td class=td-rightbottom height="18" align="left" nowrap><%=(clientName==null?"&nbsp;":clientName)%>
		                     </td>
	                         <td class=td-bottom height="18" align="right" nowrap><%=(sAmount==null?"&nbsp;":sAmount)%>&nbsp;</td>
		                     
	                         </tr>
					<%	
					}	
							
						
					
					
					
				}
          }//�����ѭ������
	
   
   %>
   <tr align="center"  borderColor=#999999> 
               <td class=td-rightbottom height="18"></td>
               <td class=td-rightbottom height="18" align=center>
					�ϼ�
				</td>
               <td class=td-rightbottom height="18" align="left" nowrap>
	            </td>
	            <td class=td-bottom height="18" align="right" nowrap><%=DataFormat.formatListAmount(currentSum)%>&nbsp;</td>
           </tr>
  <% 
   
   
   }
   
   %> 
   </table>
   <!--�����˻� END--> 
     
   <!--�����˻�-->
   
   <%if (accountType==2)
   {
	   
	 %>
	   
	   <table border="0" bordercolor="#999999"  width="85%"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="9"><b>
     �˻����</b>
     </td></tr>
    
     </table>
	      <table width="99%" bordercolor="#999999" class="table1"   border="0" cellspacing="0" cellpadding="0">
        
        <tr >           
          <td colspan="9" height="22" class="td-bottom">&nbsp;&nbsp;&nbsp;&nbsp;�����˻�</td>          
        </tr>
		
        
      
        <tr align=center class="tableHeader"> 
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
      
		
		//resultColl = Accountdao.seekFixedBalace(sessionMng.m_lClientID, sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,lStatusID);
		it1=null;
		
		while( it != null && it.hasNext() )
		{
			OB_UserInfo info = (OB_UserInfo ) it.next();
			clientID=info.getNClientId();
			resultColl = Accountdao.seekFixedBalace(clientID,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,lStatusID);
			if (resultColl != null)
			{					
				it1 = resultColl.iterator();
			}	
			while( it1 != null && it1.hasNext() )
			{
				OBAccountBalanceFixedInfo fixed_info = (OBAccountBalanceFixedInfo) it1.next();
		        
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
					sFixedAmount=DataFormat.formatListAmount(fixed_dMopenamount); 
				//������
				if(fixed_dMbalance==-1)
					sFixedBalance="";
				else
					sFixedBalance=DataFormat.formatListAmount(fixed_dMbalance); 
				if(sFixedBalance.equals("&nbsp;") && fixed_tsAf_dtstart!=null )
					sFixedBalance="0.00";
				//����
				if(fixed_lAf_ndepositterm==-1 || fixed_lAf_ndepositterm==-2)
				{
				    sTerm="";
				}
				else if(fixed_lNtype==6)
				{
					fixed_lAf_ndepositterm = fixed_lAf_ndepositterm;
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
					else
					{
						sTatus=SETTConstant.SubAccountStatus.getName(fixed_lNstatusid);
					}
				}
				if(fixed_dMopenamount==0.0)					
					continue;
		
		%>
		    <tr align="center" borderColor=#999999> 
	      <!--�˻�����-->
		  <td class="td-rightbottom"  height="18"><%=(sAcctType==null?"":sAcctType)%>&nbsp;</td>
		  <!--�˺�/���ݺ�-->
          <td class="td-rightbottom" height="18"  align=center> 
		  <%
		  String[] strfixedaccountno = fixed_strSaccountno.split("-");
			int fixedlen = strfixedaccountno.length;	
			String[] strfixedaccountno1 = new String[fixedlen];
			String strfixedaccountno2 ="";
			
			for (int t=0;t<fixedlen;t++)
			{
				
				strfixedaccountno1[t] = strfixedaccountno[t];
				strfixedaccountno2+=strfixedaccountno1[t];
			}
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
          <td class="td-rightbottom"  height="18"><%=(fixed_tsAf_dtstart==null?"":DataFormat.formatDate(fixed_tsAf_dtstart))%>&nbsp;</td>
		  <!--������-->
          <td class="td-rightbottom"  height="18"><%=(fixed_tsAf_dtend==null?"":DataFormat.formatDate(fixed_tsAf_dtend))%>&nbsp;</td>
		  <!--����/Ʒ��-->
          <td class="td-rightbottom"  height="18" ><%=sTerm%>&nbsp;</td>
		  <!--����-->
		  <td class="td-rightbottom"  height="18" ><%=sRate%>&nbsp;</td>
		  <!--�����-->
          <td class="td-rightbottom"  height="18" align="right" nowrap><%=sFixedAmount%>&nbsp;</td>
		  <!--������-->
		  <td class="td-rightbottom"  height="18" align="right" nowrap><%=sFixedBalance%>&nbsp;</td>
		  <!--��ע-->
          <td class="td-bottom"  height="18"><%=sTatus%>&nbsp;</td>
        </tr> 
		
		<%	
			}	//��ѭ������
			%>	
			
			
			
		<%}//��ѭ�����
		
   
       %>
       <tr align="center" borderColor=#999999 > 
	      <!--�˻�����-->
		  <td class="td-rightbottom"  height="18">&nbsp;</td>
		  <!--�˺�/���ݺ�-->
          <td class="td-rightbottom" height="18" >&nbsp; 		  
		  </td>
		  <!--������-->
          <td class="td-rightbottom"  height="18">&nbsp;</td>
		  <!--������-->
          <td class="td-rightbottom"  height="18">&nbsp;</td>
		  <!--����/Ʒ��-->
          <td class="td-rightbottom"  height="18" >&nbsp;</td>
		  <!--����-->
		  <td class="td-rightbottom"  height="18" >&nbsp;</td>
		  <!--�����-->
          
      <td class="td-rightbottom"  height="18" align="right" nowrap><strong>�������ܼƣ�</strong></td>
		  <!--������-->
		  <td class="td-rightbottom"  height="18" align="right" nowrap><%=DataFormat.formatListAmount(depositSum)%>&nbsp;</td>
		  <!--��ע-->
          <td class="td-bottom"  height="18">&nbsp;</td>
        </tr>	
       </table> 
   <%
   }
   
   %>
   
   
   <!--�����˻� END-->
   
   
   
   
   
   <!--�����˻�-->
   
   <%if (accountType==3)
   {
   %>   
	   
	   <table border="0" bordercolor="#999999"  width="85%"   cellspacing="0" cellpadding="0">
     <tr><td class="f22" align="center" height="50" colspan="10">
     <b>
     �˻����</b>
     </td></tr>
    
     </table>
	  <table bordercolor="#999999" class="table1" width="99%"  border="0" cellspacing="0" cellpadding="0">
        
        <tr  >          
          <td colspan="10"height="25" class=td-bottom>&nbsp;&nbsp;&nbsp;&nbsp;�����˻�</td>
        </tr>
        
      
        <tr align="center" class="tableHeader"> 
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
    //	������
    String sLoanAmount="";
	//�������
	String sLoanBalance="";
	//��λ
	String sBrwClient="";
	//��ͬ״̬
	String sContractStatus="";
	    
	it1=null;
	
	while( it != null && it.hasNext() )
	{
		OB_UserInfo info = (OB_UserInfo ) it.next();
		clientID=info.getNClientId();
		resultColl = Accountdao.seekLoanBalace(clientID,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID);
		if (resultColl != null)
		{					
			it1 = resultColl.iterator();
		}	
		while( it1 != null && it1.hasNext() )
		{
			OBAccountBalanceLoanInfo loan_info = (OBAccountBalanceLoanInfo) it1.next();			
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
			if(loan_dMAmount==0.0)
				
				continue;	
		
			%> 
			<tr align="center"> 
	        <!--�˻�����-->
            <td class="td-rightbottom"  height="18"><%=sAcctType%>&nbsp;</td>
		    <!--�˺�/��ͬ��  ����ֵ����-->
			
			<%
String[] strloanaccountno = loan_strSaccountno.split("-");
int loanlen = strloanaccountno.length;	
String[] strloanaccountno1 = new String[loanlen];
String strloanaccountno2 ="";

for (int m=0;m<loanlen;m++)
{
	
	strloanaccountno1[m] = strloanaccountno[m];
	strloanaccountno2+=strloanaccountno1[m];
}	
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
            <td class="td-rightbottom"  height="18"><%=sBrwClient%>&nbsp;</td>
	        <!--��ʼ��-->
            <td class="td-rightbottom"  height="18"><%=(loan_tsDtStartDate==null?"":DataFormat.formatDate(loan_tsDtStartDate))%>&nbsp;</td>
		    <!--������-->
            <td class="td-rightbottom"  height="18"><%=(loan_tsDtEndDate==null?"":DataFormat.formatDate(loan_tsDtEndDate))%>&nbsp;</td>
		    <!--����-->
            <td class="td-rightbottom"  height="18"><%=sTerm%>&nbsp;</td>
		    <!--������-->
            <td class="td-rightbottom"  height="18" align="right" nowrap> 
              <div align="right"><%=sLoanAmount%></div>
            &nbsp;</td>
		    <!--�������-->
            <td class="td-rightbottom"  height="18" align="right" nowrap> 
              <div align="right"><%=sLoanBalance%></div>
            &nbsp;</td>
		    <!--����-->
            <td class="td-rightbottom"  height="18" align="right"><%=sRate%>&nbsp;</td>
		    <!--��ͬ״̬-->
		    <td class="td-bottom"  height="18" align="right"><%=sContractStatus%>&nbsp;</td>
        </tr>
			
		 <%	
		}//�����˻���ѭ������
		  %>
		  
		
	 <%}	//�����˻���ѭ������
	
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
            &nbsp;</td>
		    <!--����-->
            <td class="td-rightbottom"  height="18" align="right"></td>
		    <!--��ͬ״̬-->
		    <td class="td-bottom"  height="18" align="right"></td>
        </tr>
	 </table> 
	   
  <%
  }
  %>
   
   <!--�����˻� END-->		
   
   
    <br>
     
	<table border="0" bordercolor="#999999"  width="85%"   cellspacing="0" cellpadding="0">
     <tr><td height="50" colspan="10" style="font-size:12px">
    
     <%
			Timestamp ts=Env.getSystemDateTime(); 
%>
	      ��ѯ���ڣ�<%=ts.toString().substring(0,10)%> ��ѯʱ�䣺<%=ts.toString().substring(10,19)%>
     </td></tr>
    
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

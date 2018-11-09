<%--
/*
 * �������ƣ��ͻ����˵�.jsp
 * ����˵�����˻�����ѯҳ������
 * �������ߣ�xiren li
 * ������ڣ�2004-03-02
 */
--%>

<%@ page contentType="text/html;charset=gbk" %>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.iss.itreasury.util.Log"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.util.Constant"%>
<%@ page import="com.iss.itreasury.util.DataFormat"%>
<%@ page import="com.iss.itreasury.util.IException"%>
<%@ page import="com.iss.itreasury.util.EJBObject"%>
<%@ page import="com.iss.itreasury.util.Env"%>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.obaccountinfo.dao.*"%>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%
	response.setContentType("application/msexcel;charset=GBK");
    response.setHeader("Content-Disposition",";filename=\treport.xls");
%>

<script language="javascript" src="/webob/js/Check.js"></script>
<script language="javascript" src="/webob/js/Control.js"></script>
<script language="javascript" src="/webob/js/date-picker.js"></script>
<script language="javascript" src="/webob/js/DropDownData.js"></script>
<script language="javascript" src="/webob/js/MagnifierSQL.js"></script>

<%
    //�������
    String strTitle = "�ۿͻ����˵���";
    try 
	{
        /* �û���¼��� */
        if (sessionMng.isLogin() == false)
		 {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
            out.flush();
            return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false) 
		{
            out.println(sessionMng.hasRight(request));
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
            out.flush();
            return;
        }

        
        //�������л�ȡ��ѯ�����Ϣ
        Collection resultColl = (Collection)request.getAttribute("searchResults");
		Iterator itResult = null;
		if(resultColl != null)
		{
			itResult = resultColl.iterator();
		}
		 double[] MorY=new double[4];
		if(request.getAttribute("MorY")!=null){
		MorY = (double[])request.getAttribute("MorY");
		}else{
		MorY[0]=0;
		MorY[1]=0;
		MorY[2]=0;
		MorY[3]=0;
		
		}
        // ϵͳʱ��
  
        Timestamp dtSysDate = Env.getSystemDateTime();
%>
<HTML>
<META http-equiv=Content-Type content="application/msexcel; charset=GBK"> 
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
<%
    String strAccountNo = "";
	String strDepositNo="";
	long lAccountID =-1;	
	String strTemp = null;
	strTemp = (String)request.getAttribute("lAccountID");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    lAccountID = Long.valueOf(strTemp).longValue();
	}
	strTemp = (String)request.getAttribute("strDepositNo");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
	    strDepositNo = strTemp;
	}	
	//strAccountNo = NameRef.getNoLineAccountNoByID(lAccountID);
	//modify by xwhe 2008-12-10
	  strAccountNo = NameRef.getAccountNoByID(lAccountID);
	  
	String strEarlyBanlance = ""; //�ڳ����
	double mEarlyBanlance = 0.0;
	double mEveryBanlance = 0.0;//ÿ���Ժ�����
	String strExecuteDate = "";    //ִ����
	strExecuteDate = Env.getSystemDateString(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID);
	OBAccountBalanceQueryDao dao = new OBAccountBalanceQueryDao(); 
	mEarlyBanlance = dao.findEarlyBalance(lAccountID,UtilOperation.getNextNDay(Env.getSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID),-1));
	mEveryBanlance = mEarlyBanlance;
	strEarlyBanlance=DataFormat.formatListAmount(mEarlyBanlance);
   		
      
%>
<table width="630" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="15%" colspan="1">&nbsp;</td>
		<td width="70%" align="center" colspan="3"><b><font style="font-size:22px"><%=Env.getClientName()%>�ͻ����˵�</font></b></td>
		<td width="15%">&nbsp;</td>
	</tr>
</table>

	<table width="630" border="0">
    <tr>
	<br><br>	
	<td colspan="3" height="19">����������<%=sessionMng.m_strOfficeName%></td>
    <td colspan="2" height="19">�˺ţ�<%=strAccountNo%></td>
    </tr>
	<tr>
		<td  colspan="3">��������<%=sessionMng.m_strClientName%></td>
		<td  colspan="2">���֣�<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%></td>
	</tr>
</table>
    <table width="99%" border="0" cellspacing="0" cellpadding="3" class=table1>
       <tr>
         <td align="center" class="td-rightbottom" nowrap>���ױ��</td> 
         <td align="center" class="td-rightbottom" nowrap>��������</td> 
         <td align="center" class="td-rightbottom" nowrap>����</td>        
         <td align="center" class="td-rightbottom" nowrap>ժҪ</td>
         <td align="center" class="td-rightbottom" nowrap>�Է��˺�</td>							
		 <td align="center" class="td-rightbottom" nowrap>�Է��˻�����</td>
         <%--
         <td align="center"  class="td-rightbottom" nowrap>���ݺ�</td>
         <td align="center"  class="td-rightbottom" nowrap>֧Ʊ��</td>
         --%><td align="center"  class="td-rightbottom" nowrap>�跽���</td>
         <td align="center"  class="td-rightbottom" nowrap>�������</td>        
         <td align="center"  class="td-rightbottom" nowrap>���</td>
       </tr> 
	   		<tr>
	   		     
	   		   <td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>	
				<td class="td-rightbottom" nowrap height="20">
					<div align="center"><%=strExecuteDate%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">�ڳ����
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>	
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>						
				<td class="td-rightbottom" nowrap height="20">
					<div align="right">
					<%=mEarlyBanlance==0?"0.00":DataFormat.formatListAmount(mEarlyBanlance)%>
					</div>
				</td>				
			</tr>      
	<%
	if(itResult != null && itResult.hasNext())
	{
		double dbtAmountSum=0.0;   //�跽�ϼ�
		double cdtAmountSum=0.0;   //�����ϼ�
		double banlanceSum=0.0;    //���ϼ�
		while(itResult.hasNext())
		{
			OBAccountResultInfo info = (OBAccountResultInfo)itResult.next();
			
			String strTransNo = "";   //���׺�
			String strTransactionType = "";  //��������	
			strDepositNo = "";	       //���ݺ�
			String strAmount = "" ;         //���
			strExecuteDate = "";    //������
			
			String strBillNo = "";    //ƾ֤��/֧Ʊ��

			String strDebitAmount = "" ;         //�跽���
			String strCreditAmount = "" ;         //�������
			
			String strBanlance = "";  //���
			String strAbstract = ""; //ժҪ
			
			String strOppAccountNo = "";   //�Է��˺�
			String strOppAccountName = "";   //�Է��˻�����
			
			strOppAccountNo = NameRef.getAccountNoByID(info.getOppAccountID());
			strOppAccountName = DataFormat.formatString(NameRef.getAccountNameByID(info.getOppAccountID()));			
			strBillNo = DataFormat.formatString(info.getBillNo());	
			if(info.getTransDirection()==SETTConstant.DebitOrCredit.DEBIT)
			{
				strDebitAmount=DataFormat.formatListAmount(info.getAmount());
				dbtAmountSum = dbtAmountSum+info.getAmount();
				mEveryBanlance = mEveryBanlance-info.getAmount();
			}
			if(info.getTransDirection()==SETTConstant.DebitOrCredit.CREDIT)
			{
				strCreditAmount=DataFormat.formatListAmount(info.getAmount());
				cdtAmountSum = cdtAmountSum+info.getAmount();
				mEveryBanlance = mEveryBanlance+info.getAmount();
			}
			strBanlance = DataFormat.formatListAmount(mEveryBanlance);			
			banlanceSum=info.getBalance();
			
			strTransNo = DataFormat.formatEmptyString(info.getTransNo());
			strTransactionType = DataFormat.formatString(SETTConstant.TransactionType.getName(info.getTransactionTypeID()));
			
			strDepositNo = DataFormat.formatString(info.getDepositNo());
			strAmount=DataFormat.formatListAmount(info.getAmount());
			strExecuteDate = DataFormat.formatString(DataFormat.formatDate(info.getExecuteDate()));	
			strAbstract = DataFormat.formatString(info.getAbstract());	
			
			
	%>
		<tr>
               <td class="td-rightbottom" nowrap height="20">
					<div align="center">		
					<%=strTransNo.equals("") ? "&nbsp;" : strTransNo+"&nbsp;"%>		
					</div>
				</td>
				<td class="td-rightbottom"  nowrap height="20">
					<div align="center">
					<%=strTransactionType.equals("") ? "&nbsp;" : strTransactionType%>
					</div>
				</td>
				
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">		
					<%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate%>		
					</div>
				</td>
				<td class="td-rightbottom"  nowrap height="20">
					<div align="center">
					<%=strAbstract.equals("") ? "&nbsp;" : strAbstract+"&nbsp;"%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">
						<%=strOppAccountNo%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="left">
						<%=strOppAccountName%>
					</div>
				</td>
				<%--<td class="td-rightbottom"  nowrap height="20">
					<div align="center">
					    &nbsp;<%=strDepositNo.equals("") ? "&nbsp;" : strDepositNo%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">
					    <%=strBillNo.equals("") ? "&nbsp;" : strBillNo%>
					</div>
				</td>
				--%><td class="td-rightbottom" nowrap height="20">
					<div align="right">
					    <%=strDebitAmount.equals("") ? "0.00" : strDebitAmount%>
					</div>
				</td>	
				<td class="td-rightbottom"  nowrap height="20">
					<div align="right">
					    <%=strCreditAmount.equals("") ? "0.00" : strCreditAmount%>
					</div>
				</td>	
				<td class="td-rightbottom"  nowrap height="20">
					<div align="right">
					    <%=strBanlance.equals("") ? "0.00" : strBanlance%>
					</div>
				</td>				
			</tr>
		<%
		}
	%>	

			<tr>
			    <td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center"><%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">���պϼ�
					</div>
				</td>
				<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom" nowrap height="20">
					<div align="right"><%=dbtAmountSum==0?"0.00":DataFormat.formatListAmount(dbtAmountSum)%>
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="right"><%=cdtAmountSum==0?"0.00":DataFormat.formatListAmount(cdtAmountSum)%>
					</div>
				</td>						
				<td class="td-rightbottom" nowrap height="20">
					<div align="right"><%=banlanceSum==0?"0.00":DataFormat.formatListAmount(banlanceSum)%>
					</div>
				</td>				
			</tr>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center"><%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate.substring(0,7)%>&nbsp;	--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">���ºϼ�--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=MorY[0] != 0 ? DataFormat.formatListAmount(MorY[0]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="right"><%=MorY[1] != 0 ? DataFormat.formatListAmount(MorY[1]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=banlanceSum==0?"0.00":DataFormat.formatListAmount(banlanceSum)%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center"><%=strExecuteDate.equals("") ? "&nbsp;" : strExecuteDate.substring(0,4)%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">����ϼ�--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=MorY[2] != 0 ? DataFormat.formatListAmount(MorY[2]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="right"><%=MorY[3] != 0 ? DataFormat.formatListAmount(MorY[3]):"0.00"%>--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=banlanceSum==0?"0.00":DataFormat.formatListAmount(banlanceSum)%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%			
	}
	else
	{		
		%>	
<%--	<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center"><%=strExecuteDate%>--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">�ڳ����--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="right"><%=strEarlyBanlance%>--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
			<tr>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">���պϼ�
					</div>
				</td>
				<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<%--<td class="td-rightbottom"nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>		
				--%><td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>
				<td class="td-rightbottom" nowrap height="20">
					<div align="center">&nbsp;
					</div>
				</td>						
				<td class="td-rightbottom" nowrap height="20">
					<div align="right"><%=mEarlyBanlance==0?"0.00":DataFormat.formatListAmount(mEarlyBanlance)%>
					</div>
				</td>				
			</tr>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">���ºϼ�--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%--			<tr>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">����ϼ�--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>		--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>--%>
<%--				<td class="td-rightbottom" nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>						--%>
<%--				<td class="td-rightbottom"nowrap height="20">--%>
<%--					<div align="center">&nbsp;--%>
<%--					</div>--%>
<%--				</td>				--%>
<%--			</tr>--%>
<%
	}
%>				
    <table width="99%" border="0" cellspacing="0" cellpadding="3">
      <tr>
	    <td align="left" >�����ˣ�<%=sessionMng.m_strUserName%></td>
	    <td>&nbsp
	    </td><%--
	     <td>&nbsp
	    </td>
	     <td>&nbsp
	    </td>
	     --%><td>&nbsp
	    </td>
	     <td>&nbsp
	    </td>
	    
        <td align="right" >��ӡʱ�䣺<%=Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID)%></td>
      </tr>
    </table>
</BODY>
</HTML>
<%
    }
	catch (IException ie) 
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
    }
%>
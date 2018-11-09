<%--
 ҳ������ ��l012-v.jsp
 ҳ�湦�� :  [�����]--ί�д���,��ͼҳ��һ
 ��    �� ��gqzhang
 ��    �� ��2004��2��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@ page import="java.sql.*,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.settlement.util.NameRef,
				 com.iss.itreasury.loan.util.*,
				 com.iss.itreasury.settlement.util.*,
				 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*,
                 com.iss.itreasury.ebank.obfinanceinstr.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*"
                 
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="Magnifier" scope="request" class="com.iss.itreasury.ebank.util.OBMagnifier"></jsp:useBean>

<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>

<%
	/* ����̶����� */
	String strTitle = "[�����]--ί�д���";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/capital/loanrepayment/l012-v.jsp*******");
	
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
		
		//�������
		String strTemp = "";
		String strAction = "";
		String strReturn = "";
		
		String strOpenSystem = Env.getSystemDateString(sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
		
		long lID = -1; // ָ�����
	    String sPayerName = ""; // �������
		String sPayerAcctNo = ""; // ����˺� 
		long lPayerAcctID = -1;//����˻�ID
		double dCurrentBalance = 0.00; // ��ǰ���
	    double dUsableBalance = 0.00; // ���ý��
		String sPayeeName = ""; // �տ����
	    String sPayeeAcctNo = ""; // �տ�˺ţ��������˺ţ�
	    long lPayeeAcctID = -1; // �տ�˻�ID���������˻�id��
		long lContractID = -1;//�����ͬid
		String sLoanContractNo = ""; // �����ͬ��
		Timestamp tsPayDate = null; // ����ſ�����
		long lLoanNoteID = -1;
	    String  sLetOutCode = ""; // �ſ�֪ͨ����
		double dBalance = 0.00;//�������
		double dRate = 0.00;//�ſ�֪ͨ������
		double dAmount = 0.00; // ������
		Timestamp tsExecuteDate = null; // ִ����
		String sNote = ""; // �����;/ժҪ
		long lSubAccountID = -1;//���˻�id
		
		double dRealInterest = 0.0; //ʵ��������Ϣ
	    double dRealCompoundInterest = 0.0; //ʵ������
	    double dRealOverDueInterest = 0.0; //ʵ�����ڷ�Ϣ
	    double dRealCommission = 0.0; //Ӧ��������
	    double dRealTotal = 0.0; //ʵ����Ϣ�ͷ���֮��
		//��ȡ��Ϣ���ڷ���
		
		 strTemp = (String)request.getAttribute("strAction");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strAction = strTemp.trim();
		 }
		 Log.print("===========strAction:"+strAction);
		 
		  strTemp = (String)request.getAttribute("strReturn");
		 if (strTemp != null && strTemp.trim().length() > 0)
		 {
			strReturn = strTemp.trim();
		 }
		
		 //����ӵڶ�ҳ�淵��
		 if(strAction.equals("toCancel") || strAction.equals("toCancelModify") )
		 {
				  //��ȡҳ��һ����Ϣ,���ڷŴ󾵵���Ϣ��ȡ��Ҫ�����޸�
				 strTemp = (String)request.getAttribute("lID");// ָ�����
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					lID = Long.valueOf(strTemp).longValue();
				 }
				
				 strTemp = (String)request.getAttribute("sPayerName");// �������
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					sPayerName = strTemp.trim();
				 }
				 
				 strTemp = (String)request.getAttribute("sPayerAcctNoCtrl");// ����˺� 
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					sPayerAcctNo = strTemp.trim();
				 }
				 
				 strTemp = (String)request.getAttribute("lPayerAcctID");// ����˻�id
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					lPayerAcctID = Long.valueOf(strTemp).longValue();
				 }
				
				strTemp = (String)request.getAttribute("dCurrentBalance");//��ǰ���
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					dCurrentBalance = DataFormat.parseNumber(strTemp);
				}
				
				strTemp = (String)request.getAttribute("dUsableBalance");//���ý��
				if (strTemp != null && strTemp.trim().length() > 0)
				{
					dUsableBalance = DataFormat.parseNumber(strTemp);
				}
			
			     strTemp = (String)request.getAttribute("sPayeeName");//�տ����
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					sPayeeName = strTemp.trim();
				 }
					
				 strTemp = (String)request.getAttribute("sPayeeAcctNo");//�տ�˺ţ��������˺ţ� 
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					sPayeeAcctNo = strTemp.trim();
				 }
				 
				  strTemp = (String)request.getAttribute("lPayeeAcctID");//�տ�˻�ID���������˻�id��
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					lPayeeAcctID = Long.valueOf(strTemp).longValue();
				 }
				 
				 strTemp = (String)request.getAttribute("lContractID");//�����ͬid
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					lContractID = Long.valueOf(strTemp).longValue();
					sLoanContractNo = NameRef.getContractNoByID(lContractID);
				 }
				 
				 strTemp = (String)request.getAttribute("lContractIDCtrl");//�����ͬ���
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					sLoanContractNo = strTemp.trim();
				 }
				 
				 
			 	 strTemp = (String)request.getAttribute("lLoanNoteIDCtrl");//����ſ�����
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					tsPayDate =  DataFormat.getDateTime(strTemp);
				 }
		         
				  strTemp = (String)request.getAttribute("lLoanNoteID");// �ſ�֪ͨ��id
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					lLoanNoteID = Long.valueOf(strTemp).longValue();
					sLetOutCode = NameRef.getPayFormNoByID(lLoanNoteID);
				 }
				 
				 strTemp = (String)request.getAttribute("dBalance");//�������
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					dBalance = DataFormat.parseNumber(strTemp);
				 }
				 
				 strTemp = (String)request.getAttribute("dAmount");//������
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					dAmount = DataFormat.parseNumber(strTemp);
				 }
				 
				  strTemp = (String)request.getAttribute("tsExecuteDate");//ִ����
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					tsExecuteDate =  DataFormat.getDateTime(strTemp);
				 }
				 
				 strTemp = (String)request.getAttribute("sNote");// �����;/ժҪ
				 if (strTemp != null && strTemp.trim().length() > 0)
				 {
					sNote = strTemp.trim();
				 }
				 
				 if(!strAction.equals("toCancel"))
				 {
				  	strTemp = (String)request.getAttribute("dRealInterest");
				 	if (strTemp != null && strTemp.trim().length() > 0)
				 	{
						 dRealInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
				 	}
				 
				 	strTemp = (String)request.getAttribute("dRealCompoundInterest");
				 	if (strTemp != null && strTemp.trim().length() > 0)
				 	{
						 dRealCompoundInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
				 	}
				 
				  	strTemp = (String)request.getAttribute("dRealOverDueInterest");
				 	if (strTemp != null && strTemp.trim().length() > 0)
				 	{
						 dRealOverDueInterest = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
				 	}
				 
				 	strTemp = (String)request.getAttribute("dRealCommission");
				 	if (strTemp != null && strTemp.trim().length() > 0)
				 	{
						 dRealCommission = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
				 	} 
				 
				 	strTemp = (String)request.getAttribute("dRealTotal");
				 	if (strTemp != null && strTemp.trim().length() > 0)
				 	{
						 dRealTotal = Double.valueOf(DataFormat.reverseFormatAmount(strTemp)).doubleValue();
				 	} 
				 }
                
		 }
		 else if (strAction.equals("toModify"))
		 {		
		        Log.print("=======��ȡ��ѯ���");
				FinanceInfo financeInfo = null;
				financeInfo = (FinanceInfo)request.getAttribute("resultInfo");
				
				if(financeInfo != null)
				{
				 Log.print("=======not null");
				 lID = financeInfo.getID();
				 lPayerAcctID = financeInfo.getPayerAcctID();//����˻�id
				 sPayerName = financeInfo.getPayerName(); // �������
				 sPayerAcctNo = financeInfo.getPayerAcctNo(); // ����˺� 
				 dCurrentBalance = financeInfo.getCurrentBalance(); // ��ǰ���
			     dUsableBalance = financeInfo.getUsableBalance(); // ���ý��
				 sPayeeName = financeInfo.getPayeeName(); // �տ����
			     sPayeeAcctNo = financeInfo.getPayeeAcctNo(); // �տ�˺ţ��������˺ţ�
			     lPayeeAcctID = financeInfo.getPayeeAcctID(); // �տ�˻�ID���������˻�id��
				 lContractID = financeInfo.getContractID();//�����ͬID
				 sLoanContractNo = financeInfo.getLoanContractNo(); // �����ͬ���
				 tsPayDate = financeInfo.getPayDate(); // ����ſ�����
				 lLoanNoteID = financeInfo.getLoanNoteID();//�ſ�֪ͨ��id
			     sLetOutCode = financeInfo.getLetOutCode(); // �ſ�֪ͨ����
				 dBalance = financeInfo.getBalance();//�������
				 dRate = financeInfo.getRate();//�ſ�֪ͨ������
				 dAmount = financeInfo.getAmount(); // ������
				 tsExecuteDate = financeInfo.getExecuteDate(); // ִ����
				 sNote = financeInfo.getNote(); // �����;/ժҪ
				 dRealInterest = financeInfo.getRealInterest();//ʵ��������Ϣ
	   			 dRealCompoundInterest = financeInfo.getRealCompoundInterest(); //ʵ������
	   			 dRealOverDueInterest = financeInfo.getRealOverdueInterest(); //ʵ�����ڷ�Ϣ
	   			 dRealCommission = financeInfo.getRealCommission(); //Ӧ��������
	   			 dRealTotal = DataFormat.formatDouble(DataFormat.formatDouble(dRealInterest)
				             + DataFormat.formatDouble(dRealCompoundInterest)
							 + DataFormat.formatDouble(dRealOverDueInterest)
							 + DataFormat.formatDouble(dRealCommission));//ʵ����Ϣ�ͷ���֮��
				Log.print("====dRealTotal:"+dRealTotal);			 
				}
		 }	
		
		//��ʼ�������˺�
		PayerOrPayeeInfo payerOrPayeeInfo = (PayerOrPayeeInfo)request.getAttribute("payerOrPayeeInfo"); 
		if(payerOrPayeeInfo != null)
		{
		  lPayeeAcctID = payerOrPayeeInfo.getAccountID();
		  sPayeeAcctNo = payerOrPayeeInfo.getAccountNo();
		  sPayeeName = payerOrPayeeInfo.getAccountName();
		}  
		
		//��ʾ�ļ�ͷ
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
		%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>

<safety:resources />

<FORM name=frm method=post>
<input type="Hidden" name="lID" value="<%=lID%>">
<!--�������ڲ�ѯ����!-->
<input type="hidden" name="strAction" value="<%=strAction%>">
<input type="hidden" name="strReturn" value="<%=strReturn%>">
<!--�������ڲ�ѯ����!-->
<!--���������޸�!-->
<input type="hidden" name="dRealInterest" value="<%=dRealInterest%>">
<input type="hidden" name="dRealCompoundInterest" value="<%=dRealCompoundInterest%>">
<input type="hidden" name="dRealOverDueInterest" value="<%=dRealOverDueInterest%>">
<input type="hidden" name="dRealCommission" value="<%=dRealCommission%>">
<input type="hidden" name="dRealTotal" value="<%=dRealTotal%>">
<!--���������޸�!-->		
        <TABLE cellSpacing=0 cellPadding=0  border=0 width="730" class=top>
          <TR  class="FormTitle">
          <!--TD vAlign=top align=left width=6 bgColor=#456795 height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
            <TD colSpan=7 height=25><FONT 
            >�������</FONT></TD>
          <!--TD vAlign=top align=right width=3 height=25>
            <DIV align=right></DIV></TD-->
			</TR>
        <TR >
          <TD colSpan=4 height=1></TD></TR>
        <TR >
          <TD width=6 height=25></TD>
            <TD class=graytext width=116  height=25>�����˻����ƣ�</TD>
          <TD class=graytext width=605  height=25 colSpan=2><INPUT class=rebox 
            readOnly size=30 name=sPayerName value="<%=sPayeeName%>"></TD>
          <TD width=3 height=25></TD></TR>
        <TR >
          
      <TD colSpan=4 height=3></TD>
    </TR>
        <TR >
          <TD width=6 height=25><input type="Hidden" name="lPayerAcctID" value="<%=lPayerAcctID%>"></TD>
		  
<%
		//����˺ŷŴ�
		Magnifier.createPayerAccountNoCtrl(out,sessionMng.m_lUserID,sessionMng.m_lCurrencyID,lID,sessionMng.m_lClientID,"lPayerAcctID","dCurrentBalance","dUsableBalance","frm",sPayerAcctNo,"sPayerAcctNo","����˺�"," width=\"130\" height=\"25\" class=\"graytext\""," colSpan=2 width=\"590\" height=\"25\" class=\"graytext\"");	
%>		        
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
          <TR > 
            <TD width=4 height=25></TD>
            <TD width=118 height=25>��ǰ��</TD>
            <TD width="238" height=25 class=graytext>
             <%=sessionMng.m_strCurrencySymbol%><INPUT class=amountbox readOnly size=16 name=dCurrentBalance value="<%=DataFormat.formatDisabledAmount(dCurrentBalance)%>">
            </TD>
            <TD width="355" class=leftwhiteline><font class=graytext>������ </font> 
               <%=sessionMng.m_strCurrencySymbol%><input class=amountbox readonly size=16 name=dUsableBalance value="<%=DataFormat.formatDisabledAmount(dUsableBalance)%>"></TD>
            <TD width=15 colSpan=4></TD>
          </TR>
        </table>
		<br>
		<br>
        <TABLE cellSpacing=0 cellPadding=0 width=730 border=0 class = top>
          
          <TR  class="FormTitle"> 
            <!--TD vAlign=top align=left width=5 bgColor=#456795 height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
            <TD colSpan=4 height=25> <DIV align=left><FONT
            size=3>��������</FONT></DIV></TD>
            <!--TD width=17 colSpan=4></TD-->
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25></TD>
            <TD class=graytext width=119  height=25>�����˺ţ�</TD>
            <TD class=graytext width=589  height=25>
			<input type="Hidden" name="lPayeeAcctID" value="<%=lPayeeAcctID%>">
			<input type="Hidden" name="sPayeeName" value="<%=sPayeeName%>">
			<INPUT class=rebox readOnly size=22 name=sPayeeAcctNo value="<%=sPayeeAcctNo%>"> </TD>
            <TD width=17 height=25></TD>
          </TR>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25><input type="Hidden" name="strClientCtrl" value="<%=sessionMng.m_lClientID%>"></TD>
			
	        <%
			String[] strNextControls = {"lLoanNoteIDCtrl"};
			//CONSIGNLOANRECEIVE
		    Magnifier.createContractCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lContractID","��ͬ��",sessionMng.m_lClientID,lContractID,sLoanContractNo,SETTConstant.TransactionType.CONSIGNLOANRECEIVE,2,"strClientCtrl","","",strNextControls);
			%>
            <TD width=119></TD>
          </TR>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25><input type="hidden" name="strRtnContractIDCtrl"></TD>
          <%
          String[] strNextControls1 = {"dAmount"};
		// Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,"frm","lLoanNoteID","�ſ�����",lContractID,lLoanNoteID,tsPayDate,sLetOutCode,2,3,"lContractID","","",strNextControls1,"","","","","","dBalance");
		  Magnifier.createPayFormCtrl(out,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID,sessionMng.m_lClientID,"frm","lLoanNoteID","�ſ�����",lContractID,lLoanNoteID,lPayeeAcctID,tsPayDate,sLetOutCode,2,3,"lContractID","","",strNextControls1,"","strRtnContractIDCtrl","","","","dBalance");
		  
		  %>
            <TD width=119></TD>
          </TR>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
          <TR > 
            <TD width=5 height=25></TD>
            <TD class=graytext width=119 height=25>������</TD>
            <TD class=graytext width=589 height=25><INPUT type="hidden" size=16 name=dBalance value="<%=DataFormat.formatDisabledAmount(dBalance)%>" onchange="document.all.dSeeBalance.value=formatAmount(this.value)">
			<%=sessionMng.m_strCurrencySymbol%><INPUT class=amountbox readOnly size=16 name=dSeeBalance value="<%=DataFormat.formatDisabledAmount(dBalance)%>"> </TD>
            <TD width=17></TD>
          </TR>
		  <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
		  <tr >
		  <td></td>
		  <td>���ʣ�</td>
		  <td colspan="3"><input  size="8" type="text"  value="<%=DataFormat.formatRate(dRate)%>" name="lLoanNoteIDrate" disabled>%</td>
		  </tr>
          <TR > 
            <TD colSpan=4 height=1></TD>
          </TR>
        </TABLE>
		<br>
		<br>
        <TABLE cellSpacing=0 cellPadding=0 width=730 border=0 class=top>
          
          <TR class="FormTitle"> 
            <!--TD vAlign=top align=left width=3 bgColor=#456795 height=25><IMG 
            height=3 src="\webob\graphics\blue_top_left.gif" width=3></TD-->
            <TD  colSpan=5 height=25><FONT 
            >��������</FONT></TD>
            <!--TD vAlign=top align=right width=4 height=25> <DIV align=right></DIV></TD-->
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
          <TR > 
            <TD width=3 height=25></TD>
            <TD class=graytext width=102  height=25>�����</TD>
            <TD class=graytext width=17  height=25> <DIV align=right><%=sessionMng.m_strCurrencySymbol%></DIV></TD>
            <TD class=graytext  colSpan=2 height=25> 
			<SCRIPT language=JavaScript>
				
				createAmountCtrl('frm','dAmount',<%=dAmount%>,'tsExecuteDate','sChineseAmount',<%=sessionMng.m_lCurrencyID%>);
				
			</SCRIPT></TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
          <TR > 
            <TD width=3 height=25></TD>
            <TD class=graytext width=250  colSpan=2 
          height=25>��д���(<%=OBConstant.CurrencyType.getName(sessionMng.m_lCurrencyID)%>)��</TD>
            <TD class=graytext width=450  height=25>
			<input type="text" class="rebox" name="sChineseAmount" size="30" value="<%=ChineseCurrency.showChinese(String.valueOf(dAmount),sessionMng.m_lCurrencyID)%>" readonly>
			</TD>
            <TD width=4 height=25></TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>		  
          <TR > 
            <TD width=3 height=25></TD>
            <TD class=graytext  colSpan=2 
          height=25>ִ���գ�</TD>
            <TD class=graytext width=604  height=25>
				<fs_c:calendar 
	          	    name="tsExecuteDate"
		          	value="" 
		          	properties="nextfield ='sNote'" 
		          	size="18"/>
		        <script>
	          		$('#tsExecuteDate').val('<%=tsExecuteDate == null ? DataFormat.getDateString(Env.getSystemDate(1,1)):DataFormat.getDateString(tsExecuteDate)%>');
	          	</script>
			</TD>
            <TD width=4 height=25></TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
          <TR > 
            <TD width=3 height=25></TD>
            <TD class=graytext colSpan=2 height=25>�����;��</TD>
            <TD class=graytext noWrap width=604 height=25>
			<textarea name="sNote" cols="65" rows="2" onkeypress="if (this.value.length>50) event.keyCode=0;" onchange="if(this.value.length>50) this.value=this.value.substr(0,50)" onfocus="nextfield = 'submitfunction'"><%=sNote == "" || sNote == null ? sessionMng.m_strClientShortName:DataFormat.formatString(sNote)%></textarea>
      </TD>
            <TD width=4></TD>
          </TR>
          <TR > 
            <TD colSpan=5 height=1></TD>
          </TR>
        </TABLE>
        <br>
        <TABLE cellSpacing=0 cellPadding=0 width=730 border=0>
          <TR> 
            <TD width=445> <DIV align=right></DIV></TD>
            <TD width=63> <DIV align=right> </DIV></TD>
            <TD width=62>
			<div align="right">	
				<input type="button" name="GoNext" value=" �� �� " class = button onfocus="nextfield='submitfunction';" onClick="goNext();">
			</div>
			</TD>
          </TR>
        </TABLE>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
      </form>
<script language="javascript">
function validate()
{
   //����˺�
   if(frm.sPayerAcctNoCtrl.value=="")
   {
     alert("��ѡ�񸶿�˺ţ�");
	 frm.sPayerAcctNoCtrl.focus();
	 return false;
   }
   
   //��ͬ��
   if(frm.lContractID.value=="" || frm.lContractID.value== "-1")
   {
      alert("��ѡ���ͬ��ţ�");
	 frm.lContractIDCtrl.focus();
	 return false;
   
   }
   
   //�ſ�֪ͨ��
   if(frm.lLoanNoteIDCtrl.value=="" || frm.lLoanNoteID.value=="-1")
   {
      alert("��ѡ��ſ����ڣ�");
	  frm.lLoanNoteIDCtrl.focus();
	  return false;
   }
   
   //������
   if(!checkAmount(frm.dAmount, 1, "������"))
   {
	  return false;
   }
   
   //ִ����
   if (!checkInterestExecuteDate(frm.tsExecuteDate,"<%=DataFormat.getDateString(Env.getSystemDate(1,1))%>"))
	{
		return false;
	}
	
	if (frm.tsExecuteDate.value < "<%=strOpenSystem%>")
	{
		alert("ִ���ղ������ڿ����գ�");
		return false;
	}
		/* �����; */
		if (!InputValid(frm.sNote, 0, "textarea", 1, 0, 100,"�����;"))
		{
			return false;
		}		
	
	//ҵ��У��
	//У���ͬ���ڷſ�֪ͨ����ͬ���Ƿ�һ��
	if(frm.lContractID.value != frm.strRtnContractIDCtrl.value)
	{
	   alert("�ſ�֪ͨ�����ͬ�Ų�ƥ�䣡");
	   frm.lLoanNoteIDCtrl.focus();
	   return false;
	}
	
	
	var dBalance1 = parseFloat(reverseFormatAmount(frm.dAmount.value)) -
				   parseFloat(reverseFormatAmount(frm.dUsableBalance.value)) ;
	 
	
	 if(dBalance1 > 0)
	{
	   alert("������ܳ���������");
	   frm.dAmount.focus();	
	   return false;
	}
	
	
	var dBalance2 = parseFloat(reverseFormatAmount(frm.dAmount.value)) -
				   parseFloat(reverseFormatAmount(frm.dBalance.value)) ;
				   
	if(dBalance2 > 0)
	{
	   alert("������ܳ���������");
	   frm.dAmount.focus();
	   return false;
	}			   
   
      return true;
}

function goNext()
{     
	 if(!validate())
	 {
	   return;
	 }
	
	 if(confirm("�Ƿ������"))
	 {
	 <%
	 if(strAction.equals("toCancelModify"))
	 {
	 %>
	 frm.strAction.value="toModify";
	 <%
	 }
	 %>
	  frm.action="l013-c.jsp"
	  showSending();
	  frm.submit();
	  }

}

firstFocus(frm.sPayerAcctNoCtrl);
//setSubmitFunction("goNext()");
setFormName("frm");	 

</script>	
<%	
   //��ʾ�ļ�β
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>
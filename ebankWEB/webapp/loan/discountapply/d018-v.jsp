<%--
 ҳ������ ��d018-v.jsp
 ҳ�湦�� : ������������-����������ϸ��Ϣ ��ʾҳ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.sql.*,
                 java.util.Iterator,
				 java.util.Collection,
                 javax.servlet.*,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%
	/* ����̶����� */
	String strTitle = "[��������]";
%>					
<%
  try
  {
	   Log.print("*******����ҳ��--ebank/loan/discountapply/d018-v.jsp*******");
	
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
		 String strAction = (String)request.getAttribute("txtAction");
		 String frompage = (String)request.getAttribute("frompage");
		
		//������Ϣ
		 long lID = -1; //����ID��ʶ
         String strDiscountCode =  ""; //���ֱ��
         String strContractCode = ""; //���ֺ�ͬ���
         long lApplyClientID =  -1; //���뵥λ���
         String strApplyClientName = ""; //���뵥λ����
         String strApplyAccount = ""; //���뵥λ�˺�
		 String strApplyBank = ""; //���뵥λ��������
		 long lApplyOfficeID =  -1; //���뵥λ�������´���ʾ
		 String strApplyOfficeName = ""; //���뵥λ�������´�����
		 String strApplyLOfficeAccount = ""; //���뵥λ�������´��˺�
		 long lTypeID =  -1; //��������
		 long lCurrencyID =  -1; //����
		 long lOfficeID =  -1; //���´���ʾ
		 String strOfficeName = ""; //���´�����
		 String strLOfficeAccount = ""; //���´��˺�
		 long lStatusID =  -1; //״̬
		 long lInputUserID =  -1; //¼���˱�ʾ
		 String strInputUserName = ""; //¼��������
		 Timestamp tsInputDate =  null; //¼��ʱ��
		 long lNextCheckUserID =  -1; //��һ������˱�ʾ
		 long lLsReviewUserID =  -1; //��������ID
		 String strLsReviewUserName = ""; //������������
		 long lReviewStatusID =  -1; //������״̬
		 long lIsCheck =  -1; //�Ƿ���˹�
		 double dApplyDiscountAmount = 0.0; //�������ֽ��
		 double dExamineAmount = 0.0; //��׼���
		 double dCheckAmount = 0.0; //�˶����
		 double dDiscountRate = 0.0; //����
		 String strDiscountPurpose = ""; //������;
		 String strDiscountReason = ""; //����ԭ��
		 Timestamp tsDiscountDate = null; //���ּ�Ϣʱ��
		 Timestamp tsDiscountStartDate = null; //���ֿ�ʼʱ��
		 Timestamp tsDiscountEndDate = null; //���ֵ���ʱ��
		 long lApplyDiscountPO =  -1; //�������ֻ�Ʊ��������
		 long lBankAccepPO =  -1; //���гжһ�Ʊ��������(ҳ��)
		 long lBizAcceptPO =  -1; //��ҵ�жһ�Ʊ��������(ҳ��)
		 long lBankCount =  -1; //���гжһ�Ʊ��������
		 long lBizCount =  -1; //��ҵ�жһ�Ʊ��������
		 double dInterest =  0.0; //������Ϣ
		 long lBillCount =  -1; //��Ʊ������
		 double dBillAmount =  0.0; //��Ʊ�ܽ��
		 long lCount =  -1; //��¼��
		 String strDocumentType = "";//������������
		 long subtypeid=-1;
		 
		 //�ͻ���Ϣ
		 String strName = "";
		 String strCode = "";
		 String strLicenceCode = "";
		 String strAccount = "";
		 String strBank1 = "";
		 String strBankAccount1 = "";
		 long lCorpNatureID = -1;
		 String strParentCorpName = "";
		 long lIsPartner = -1;
		 long lLoanClientTypeID = -1;
	    long loanType=-1;
		

		
		DiscountInfo discountInfo = new DiscountInfo();
		
		discountInfo = (DiscountInfo)request.getAttribute("resultInfo");
		
		if(discountInfo != null)
		{
          lID = discountInfo.getID(); //����ID��ʶ
          strDiscountCode = discountInfo.getDiscountCode(); //���ֱ��
          strContractCode = discountInfo.getContractCode(); //���ֺ�ͬ���
		  lApplyClientID = discountInfo.getApplyClientID(); //���뵥λ���
		  strApplyClientName = discountInfo.getApplyClientName(); //���뵥λ����
		  strApplyAccount = discountInfo.getApplyAccount(); //���뵥λ�˺�
		  strApplyBank = discountInfo.getApplyBank(); //���뵥λ��������
		  lApplyOfficeID = discountInfo.getApplyOfficeID(); //���뵥λ�������´���ʾ
		  strApplyOfficeName = discountInfo.getApplyOfficeName(); //���뵥λ�������´�����
		  strApplyLOfficeAccount = discountInfo.getApplyLOfficeAccount(); //���뵥λ�������´��˺�
		  lTypeID = discountInfo.getTypeID(); //��������
		  lCurrencyID = discountInfo.getCurrencyID(); //����
		  lOfficeID = discountInfo.getOfficeID(); //���´���ʾ
		  strOfficeName = discountInfo.getOfficeName(); //���´�����
		  strLOfficeAccount = discountInfo.getLOfficeAccount(); //���´��˺�
		  lStatusID = discountInfo.getStatusID(); //״̬
		  lInputUserID = discountInfo.getInputUserID(); //¼���˱�ʾ
		  loanType=discountInfo.getTypeID();

		  
		  Log.print("\n===========¼���ˣ�"+lInputUserID+"======��¼�ˣ�"+sessionMng.m_lUserID);
		  
		  strInputUserName = discountInfo.getInputUserName(); //¼��������
		  tsInputDate = discountInfo.getInputDate(); //¼��ʱ��
		  
		  Log.print("\n===========¼��ʱ�䣺"+tsInputDate);
		  
		  lNextCheckUserID = discountInfo.getNextCheckUserID(); //��һ������˱�ʾ
		  lLsReviewUserID = discountInfo.getLsReviewUserID(); //��������ID
		  strLsReviewUserName = discountInfo.getLsReviewUserName(); //������������
		  lReviewStatusID = discountInfo.getReviewStatusID(); //������״̬
		  lIsCheck = discountInfo.getIsCheck(); //�Ƿ���˹�
		  dApplyDiscountAmount = discountInfo.getApplyDiscountAmount(); //�������ֽ��
		  dExamineAmount = discountInfo.getExamineAmount(); //��׼���
		  dCheckAmount = discountInfo.getCheckAmount(); //�˶����
		  dDiscountRate = discountInfo.getDiscountRate(); //����
		  strDiscountPurpose = discountInfo.getDiscountPurpose(); //������;
		  strDiscountReason = discountInfo.getDiscountReason(); //����ԭ��
		  tsDiscountDate = discountInfo.getDiscountDate(); //���ּ�Ϣʱ��
		  tsDiscountStartDate = discountInfo.getDiscountStartDate(); //���ֿ�ʼʱ��
		  tsDiscountEndDate = discountInfo.getDiscountEndDate(); //���ֵ���ʱ��
		  lApplyDiscountPO = discountInfo.getApplyDiscountPO(); //�������ֻ�Ʊ��������
		  lBankAccepPO = discountInfo.getBankAccepPO(); //���гжһ�Ʊ��������(ҳ��)
		  lBizAcceptPO = discountInfo.getBizAcceptPO(); //��ҵ�жһ�Ʊ��������(ҳ��)
		  lBankCount = discountInfo.getBankCount(); //���гжһ�Ʊ��������
		  lBizCount = discountInfo.getBizCount(); //��ҵ�жһ�Ʊ��������
		  dInterest = discountInfo.getInterest(); //������Ϣ
		  lBillCount = discountInfo.getBillCount(); //��Ʊ������
		  dBillAmount = discountInfo.getBillAmount(); //��Ʊ�ܽ��
		  lCount = discountInfo.getCount(); //��¼��
		  strDocumentType = discountInfo.getDocumentType();
        }
		long[] loanTypeid={loanType};
			//����ClientID�������뵥λ��Ϣ
			long lClientID = sessionMng.m_lClientID;
			Log.print("=============�ͻ�ID��"+lClientID);
			
			OBSystemHome  obSystemHome = null;
	        OBSystem      obSystem = null;
	        obSystemHome = (OBSystemHome)EJBObject.getEJBHome("OBSystemHome");
	        obSystem = obSystemHome.create();
	        ClientInfo clientInfo = null;
			
			clientInfo = obSystem.findClientByID(lClientID);
			if(clientInfo != null)
			{
			 strName = clientInfo.getName();
			 strCode = clientInfo.getCode();
			 strLicenceCode = clientInfo.getLicenceCode();
			 strAccount = clientInfo.getAccount();
			 strBank1 = clientInfo.getBank1();
			 strBankAccount1 = clientInfo.getBankAccount1();
			 lCorpNatureID = clientInfo.getCorpNatureID();
			 strParentCorpName = clientInfo.getParentCorpName();
			 lIsPartner = clientInfo.getIsPartner();
			 lLoanClientTypeID = clientInfo.getLoanClientTypeID();
			}
		

	  //��ʾ�ļ�ͷ
      OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);

%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<form name="frm" method="post"  target="_self">
<input type="hidden" name="lID" value="<%=lID%>">
<input type="hidden" name="lStatusID" value="<%=OBConstant.LoanInstrStatus.SUBMIT%>"><!--�����ύ����!-->
<input type="hidden" name="strDiscountCode" value="<%=strDiscountCode%>">
<input type="Hidden" name="txtAction" value="<%=strAction%>">
<input type="hidden" name="strBackPage" value="/loan/discountapply/d018-v.jsp">
<TABLE border=0 class=top width="730">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B>�������롪�������鱣��</B></TD></TR>
  <TR>
    <TD height=616 vAlign=top>
      <TABLE align=center border=0 width=100%>
        <TBODY>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=10 height=2><B></B></TD></TR>
        <TR>
          <TD colSpan=11 height=207>
            <TABLE cellPadding=0 width=100%>
              <TBODY>
              <TR>
                <TD height=24 width=200 nowrap>���������ţ�<%=strDiscountCode%></TD>
                <TD colSpan=5 height=24>
                  <DIV align=right>
			<% if (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT) { %>
				<INPUT class=button name="newApply" onclick="doCreateApply();" type=button value="������������">
				<% if (lStatusID == OBConstant.LoanInstrStatus.SAVE && lInputUserID == sessionMng.m_lUserID) { %>	
				<INPUT class=button name="submitApply" onclick="confirmSave(frm);" type=button value="�ύ��������">
				<% } %>
				<% if (lInputUserID == sessionMng.m_lUserID &&(lStatusID == OBConstant.LoanInstrStatus.SUBMIT||lStatusID == OBConstant.LoanInstrStatus.SAVE) ) { %>
				<INPUT class=button name="submitCancel" onclick="confirmCancel(frm);" type=button value="ȡ����������">
				<% } %>
				<%if (lInputUserID == sessionMng.m_lUserID)
				{
				%>
				<INPUT class=button name=doPrint onclick="printIt('../discountapply/d022-v.jsp?lID=<%=lID%>');" type="button" value=" �� ӡ "> 
				<%
				}
				%>
				<% } %>
				<INPUT class=button name="backTo" onclick="doGoBack();" type=button value=" �� ��">
				 </DIV></TD></TR>
				  <HR align=center SIZE=2 width="100%">
				 <tr>
          <TD colSpan=6 nowrap>�����������ƣ�
		  <select class='box'><option>
<%=LOANConstant.SubLoanType.getName(discountInfo.getSubTypeId())%>
			</option></select> </TD>	  
		  </tr>
              <TR>
                <TD colSpan=6 height=24>
                  <DIV align=center>
                 
                  </DIV>
                  <P><U>�������뵥λ����</U></P></TD></TR>
              <TR>
                <TD height=19 width=129>
                  <P>��λ���ƣ�</P></TD>
                <TD height=19 width=251 colSpan=5>
                  <P><INPUT class=tar disabled 
                  name=tf_dw3 size="80" value="<%=DataFormat.formatString(strName)%>"></P></TD>
			  </TR>
			  <TR>
                <TD height=19 width=126>
				<%
				String sOfficeName = "�ͻ�����";
				%>
				<%=sOfficeName%>

				</TD>
                <TD colSpan=5 height=19><INPUT class=tar disabled 
                  name=tf_dw33 value="<%=DataFormat.formatString(Env.getClientName())%>"></TD></TR>
              <TR>
                <TD height=2 width=129>
                  <P>�ͻ���ţ�</P></TD>
                <TD height=2 width=251>
                  <P><INPUT class=tar disabled name=tf_dw32 
                  value="<%=DataFormat.formatString(strCode)%>"></P></TD>
                <TD height=2 width=126>
                  <P>Ӫҵִ�պ��룺</P></TD>
                <TD height=2 width=101><INPUT 
                  class=tar disabled name=textfield223 value="<%=DataFormat.formatString(strLicenceCode)%>"> 
                  </TD>
                <TD colSpan=2 height=2>&nbsp;</TD></TR>
              <TR>
                <TD height=8 width=129>
				<%
				String strMagnifierNameAccount = "�˺� ";
				%>
				<%=strMagnifierNameAccount%>
				</TD>
                <TD height=8 width=251><INPUT 
                 class=tar disabled value="<%=DataFormat.formatString(strAccount)%>"></TD>
                <TD height=8 width=126>&nbsp;</TD>
                <TD height=8 width=101>&nbsp;</TD>
                <TD colSpan=2 height=8>&nbsp;</TD></TR>
              <TR>
                <TD height=30 width=129>
                  <P>�������У�</P></TD>
                <TD height=30 width=251>
                  <P><INPUT class=tar disabled value="<%=DataFormat.formatString(strBank1)%>"></P></TD>
                <TD height=30 width=126>
                  <P>���������˺ţ�</P></TD>
                <TD colSpan=3 height=30>
				  <INPUT class=tar disabled value="<%=DataFormat.formatString(strBankAccount1)%>"></TD></TR>
              <TR>
                <TD colSpan=2 height=2>
				<%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
				{
				%>
				<!--INPUT class=button name=Submit32 onclick="doModifyUnit();" type=button value="�޸����ֵ�λ����"--> 
				<%
				}
				//������,�Ƿ���ʾ����
				//Ŀǰ�������:�Ϻ�������ʾ,��������Ŀ��ʾ.
				if (Config.getBoolean(ConfigConstant.LOAN_CLIENT_REPORT,true))
				{
	            %>
                &nbsp;&nbsp;&nbsp;&nbsp;<!--input class=button name="loanDCB" type=button value="��������" onclick="Javascript:window.open('../../content/c220-c.jsp?ParentID=<%=lID%>&control=view','','width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes');"-->
				<%
				}
				%>
				  </TD>
                <TD colSpan=3 height=2>
                  <P><BR></P></TD>
                <TD height=2 width=174>&nbsp;</TD></TR></TBODY></TABLE>
            <HR>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=104>
            <TABLE width="104%">
              <TBODY>
              <TR vAlign=middle>
                <TD height=31 width="14%"><U>������������</U></TD>
                <TD height=31 width="13%">&nbsp;</TD>
                <TD height=31 width="8%">&nbsp;</TD>
                <TD height=31 width="15%">&nbsp;</TD>
                <TD height=31 width="11%">&nbsp;</TD>
                <TD height=31 width="15%">&nbsp;</TD>
                <TD height=31 vAlign=middle width="24%">&nbsp;</TD></TR>
              <TR vAlign=middle>
                <TD height=29 width="14%">�������ֻ�Ʊ��</TD>
                <TD height=29 width="13%">�� <INPUT class=tar disabled 
                  name=textfield282242 size=3 value="<%=lApplyDiscountPO>0 ? lApplyDiscountPO : 0%>"> ��</TD>
                <TD height=29 width="8%">���У�</TD>
                <TD height=29 width="15%">���гжһ�Ʊ��</TD>
                <TD height=29 width="11%"><INPUT class=tar disabled 
                  name=textfield2822322 size=3 value="<%=lBankAccepPO>0 ? lBankAccepPO : 0%>"> ��</TD>
                <TD height=29 width="15%">��ҵ�жһ�Ʊ��</TD>
                <TD height=29 vAlign=middle width="24%"><INPUT class=tar disabled 
                  name=textfield28222242 size=3 value="<%=lBizAcceptPO>0 ? lBizAcceptPO : 0%>"> 
            ��</TD></TR></TBODY></TABLE>
            <TABLE border=0 width="100%">
             <TBODY>
              <TR>
                <TD height=41 width="14%">�������ֽ�</TD>
                <TD height=41 width="2%">
                  <DIV align=right>��</DIV></TD>
                <TD height=41 width="24%"><INPUT class=tar 
                  disabled name=textfield2822232 size=18 value="<%=dApplyDiscountAmount>0 ? DataFormat.formatDisabledAmount(dApplyDiscountAmount) : "0.00"%>"> </TD>
                <TD height=41 width="17%">&nbsp;</TD>
                <TD height=41 width="3%">&nbsp;</TD>
                <TD height=41 width="40%">&nbsp;</TD>
			  </TR>
			  <TR>
                <TD height=41 width="14%">���ֿ�ʼ���ڣ�</TD>
                <TD height=41 width="2%">&nbsp;</TD>
				<TD height=41 width="24%">
			    <input type="Hidden" name="tsDiscountStartDate" value="<%=tsDiscountStartDate%>"><!--��������-->
				<INPUT class=tar disabled name=textfield2822232 size=18 value="<%=tsDiscountStartDate !=null ? DataFormat.getDateString(tsDiscountStartDate):""%>"></TD>
                <TD height=41 width="17%">���ֵ������ڣ�</TD>
                <TD height=41 colspan="2"><INPUT class=tar 
                  disabled name=textfield28222321 size=18 value="<%=tsDiscountEndDate !=null ? DataFormat.getDateString(tsDiscountEndDate):""%>"> </TD>
			  </TR>
			 </TBODY></TABLE></TD></TR>
        <TR>
          <TD colSpan=2 height=14>����ԭ��</TD>
          <TD colSpan=9 height=14><TEXTAREA class=box cols=65 disabled name=textarea><%=DataFormat.formatString(strDiscountReason)%></TEXTAREA> 
            </TD></TR>
        <TR>
          <TD colSpan=2 height=14>������;��</TD>
          <TD colSpan=9 height=14><TEXTAREA class=box cols=65 disabled name=textarea2><%=DataFormat.formatString(strDiscountPurpose)%></TEXTAREA> 
            </TD></TR>
        <TR>
		<TR>
				<TD colSpan=2 height=41>�Ƿ��򷽸�Ϣ��</TD>
				<TD colSpan=9 height=41>
				<SELECT class='box' name="isPurchaserInterest" onfocus="nextfield='discountClientName'" disabled>
					<OPTION value="<%=Constant.YesOrNo.YES%>" 
					<%if ((discountInfo!=null)&&(discountInfo.getIsPurchaserInterest()==Constant.YesOrNo.YES)) {out.println(" selected");} else {out.println("");}%>>��</OPTION>
					<OPTION value="<%=Constant.YesOrNo.NO%>" 
					<%if ((discountInfo!=null)&&(discountInfo.getIsPurchaserInterest()==Constant.YesOrNo.NO)) {out.println(" selected");} else {out.println("");}%>>��</OPTION>
				</SELECT>
				</TD>
			</TR>
			<TR>
				<TD colSpan=2 height=41>��Ʊ�ˣ�</TD>
				<TD colSpan=9 height=41>
					<INPUT class=box disabled name=tf_dw3 size="80" value="<%=DataFormat.formatString(discountInfo.getDiscountClientName())%>">
				</TD>
			</TR>



		<tr>

          <TD colSpan=11 height=14>
		    <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
		   {
		   %>
		  <INPUT class=button name=Submit322 onclick="doModifyApply();" type=button value="�޸�������������"> 
		   <%
		  }
		  %>
            </TD></TR>
        <TR>
          <TD colSpan=11 height=14>
            <HR>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=2><U>����Ʊ����ϸ��</U></TD></TR>
        <TR>
          <TD colSpan=11 height=2>
		  <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
		   {
		   %>
		  <INPUT class=button name=Submit2 onclick="doModifyBillDetail();" type=button value="�޸�����Ʊ����ϸ��"> 
		  <%
		  }
		  else
		  {
		  %>
		  <INPUT class=button name=Submit2 onclick="doViewBillDetail();" type=button value="�鿴����Ʊ����ϸ��"> 
		  <%
		  }
		  %>
          </TD></TR>
        <TR>
          <TD colSpan=11 height=2>
            <HR>
          </TD></TR>
		   <TR>
          <TD colSpan=11 height=2><U>����͵��������</U></TD></TR>
       
		<%
		
		String[] strDocumentTypes = OBConstant.DocumentType.getAllCode();
		
		if (strDocumentType == null )
		{
		  strDocumentType = "z";
		}
		
		
		for(int i=0;i<strDocumentTypes.length;i++)
		{
		 
		  %>
		   <TR>
		   <TD colSpan=11 height=2>
		   &nbsp;&nbsp;&nbsp;&nbsp;
		   <%
		    
		   if (strDocumentType.indexOf(strDocumentTypes[i]) > 0)
		   {
		   %> 
		      <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
				{
				%>
		         <input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>" checked>
			    <%
				}
				else
				{
				%>
				<input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>" checked disabled>
				<%
				}
				%>
		   <%
		   }
		   else
		   {
		   %>
		        <%if (lInputUserID == sessionMng.m_lUserID && (lStatusID == OBConstant.LoanInstrStatus.SAVE || lStatusID == OBConstant.LoanInstrStatus.SUBMIT))
				{
				%>
		       <input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>">
			   <%
			   }
			   else
			   {
			   %>
			   <input  type="Checkbox" name="DocumentType" value="<%=strDocumentTypes[i]%>" disabled>
			   <%
			   }
			   %>
		   <%
		   }
		   %>
		   &nbsp;&nbsp;<%=OBConstant.DocumentType.getName(strDocumentTypes[i])%>
          </TD>
		  </TR>
		  <%
		 }
		%>
		  
        <TR>
          <TD colSpan=11 height=2>
            <HR>
          </TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left height=2 width=122 nowrap>¼���ˣ�<%=DataFormat.formatString(strInputUserName)%></TD>
          <TD align=center height=2 width=410>¼��ʱ�䣺<%=tsInputDate!=null ? DataFormat.getDateString(tsInputDate) : ""%></TD>
          <TD align=center height=2 width=252>״̬�� 
            <%=OBConstant.LoanInstrStatus.getName(lStatusID)%>
		  </TD>
          <TD align=right colSpan=2 height=2></TD>
          <TD align=right colSpan=2 height=2></TD>
          <TD align=right height=2 width=0>
            <DIV align=left></DIV></TD>
          <TD align=right colSpan=2 height=2 width=9>
            <DIV align=center></DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR></P>
</form>
	
<script language="JavaScript">
function doGoBack()
{
   if(confirm("�Ƿ񷵻أ�"))
	{
	
	  <%if(strAction != null && strAction.equals("modify"))//�Ӳ�ѯ����
	  {
	  %>
		frm.action="../query/q003-c.jsp";
	  <%
	  }
	  else
	  {
	  %>
	  frm.action="../discountapply/d008-c.jsp";
	  <%
	  }
	  %>
		frm.target="_self";	
		showSending();
		frm.submit();
		return true;
	}
}

function doCreateApply()
{
  if(confirm("�Ƿ������������룿"))
	{
		document.location.href="../discountapply/d002-c.jsp";
		showSending();
		return true;
	}
}


function doModifyUnit()
{
   if(confirm("�Ƿ��޸����ֵ�λ������Ϣ��"))
	{
		frm.action="../discountapply/d002-c.jsp";
		showSending();
		frm.submit();
	}
}



function doModifyApply()
{
   if(confirm("�Ƿ��޸������������飿"))
	{
		frm.action="../discountapply/d005-c.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
		return true;
	}
}

function doModifyBillDetail()
{
   if(confirm("�Ƿ��޸�����Ʊ����ϸ��"))
	{
		frm.action="../discountapply/d008-c.jsp?frompage=<%=frompage%>";
		showSending();
		frm.submit();
		return true;
	}
}

function doViewBillDetail()
{
   		frm.target="_blank";
		frm.action="../discountapply/d008-c.jsp?modifyFlag=1";
		frm.submit();	
}

function confirmSave(frm)
{
	/*if(<%=dApplyDiscountAmount%> != <%=dBillAmount%>)
	{
		alert("���ֻ�Ʊ�ܽ��Ӧ��������������ȣ�");
		return false;
	}
	if(<%=lBankAccepPO%> != <%=lBankCount%>)
	{
		alert("���гжһ�Ʊ����Ӧ������������ȣ�");
		return false;
	}
	if(<%=lBizAcceptPO%> != <%=lBizCount%>)
	{
		alert("��ҵ�жһ�Ʊ����Ӧ������������ȣ�");
		return false;
	}
	if(<%=lApplyDiscountPO%> != <%=lBankCount+lBizCount%>)
	{
		alert("��Ʊ����Ӧ������������ȣ�");
		return false;
	}*/
	if(confirm("�Ƿ��ύ���룿"))
	{
		frm.action="../discountapply/d020-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

function confirmCancel(frm)
{
	if(confirm("�Ƿ�ȡ�����룿"))
	{
		frm.action="../discountapply/d021-c.jsp";
		showSending();
		frm.submit();
		return true;
	}
}

function printIt(url)
{
	if(confirm("�Ƿ��ӡ��"))
	{
	 window.open(url,"popup","width=800,height=600,scrollbars=1,resizable=1,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=300,top=180");
	} 
}

<% if (lStatusID == OBConstant.LoanInstrStatus.SAVE  && lInputUserID == sessionMng.m_lUserID) 
{ %>	
firstFocus(frm.submitApply);
//setSubmitFunction("confirmSave(frm)");
<%
}
%>
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


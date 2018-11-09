<%
/*
���Ʊ���
d001-v
Control
*/
%>
<%@page contentType="text/html;charset=gbk"%>
<%@ taglib uri="/WEB-INF/tlds/common.tld" prefix="fs_c" %>
<%@page	import="java.util.*,
				java.sql.*,
				java.net.URLEncoder,
				java.rmi.RemoteException,
				com.iss.itreasury.util.*,
				com.iss.itreasury.loan.util.*,
				com.iss.itreasury.ebank.util.*,
				com.iss.itreasury.loan.contract.dataentity.*,
				com.iss.itreasury.loan.loancommonsetting.dataentity.*,
				com.iss.itreasury.settlement.util.*,			
				com.iss.itreasury.ebank.obpaynotice.dataentity.*,
				com.iss.itreasury.ebank.obpaynotice.bizlogic.*"	
%>

<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>

<%@ include file="/common/common.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<script language="javascript" src="/itreasury/js/jquery-1.3.2.js"></script>

<safety:resources />
<%


	String strOfficeName = sessionMng.m_strOfficeName;
	long lClientID = sessionMng.m_lClientID;
	String strUserName = sessionMng.m_strUserName;
	long lUserID = sessionMng.m_lUserID;
	String strTitle = "�������";
//////////////////////////////////////////////////////////////////////////////////////////////

	try
	{
		//�ж��Ƿ��¼//CODE_COMMONMESSAGE_LOGIN=1
	        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		//�������
		
		//��ʾ�ļ�ͷ
       OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.YES);
                
                //�������
                Timestamp tsSystem = Env.getSystemDate(); 
				Timestamp tsInput = tsSystem; 
                long lID = -1;  
                long lCurrencyID = sessionMng.m_lCurrencyID; 
                long lOfficeID = sessionMng.m_lOfficeID; 
                long lInputUserID = sessionMng.m_lUserID; 
                String strTmp = ""; 
                long lContractID = -1; //��ͬ��
                long lPayID = -1;      //�ſ�֪ͨ��ID
                String strType = "";   //��������
                String strAction ="";
                String txtAction = "";
                //��ͬID
                strTmp = (String)request.getAttribute("lContractID");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     lContractID = Long.parseLong(strTmp.trim());
                }
                System.out.println("lContractID = "+lContractID);
                
                //�ſ�֪ͨ��ID
                strTmp = (String)request.getAttribute("lPayID");
                if( strTmp != null && strTmp.length() > 0 )
                {
                     lPayID = Long.parseLong(strTmp.trim());
                }
				
				strTmp = (String)request.getAttribute("txtAction");
				if( strTmp != null && strTmp.length() > 0 )
				{
					txtAction = strTmp.trim();
				}
				
				strTmp = (String)request.getAttribute("strAction");
				if( strTmp != null && strTmp.length() > 0 )
				{
					strAction = strTmp.trim();
				}
                
				else
                {
                    lPayID = -1;
                }
                System.out.println("lPayID = "+lPayID);
                
//////////////////////////////////////////////////////////////////////////////////////
				PayNoticeInfo info = null;
				info = (PayNoticeInfo)request.getAttribute("info");
//////////////////////////////////////////////////////////////////////////////////////

%>
<form name="frm" method="post" action=""  >
<TABLE border=0 class=top height=60 >
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=35><B><%=strTitle%></B></TD></TR>
  <TR>
    <TD height=572 vAlign=top>
      <TABLE align=center border=0 height=60 width=700>
        <TBODY>
        <TR>
          <TD height=23 width=1>&nbsp;</TD>
          <TD colSpan=2 height=23><SPAN lang=ZH-CN 
            style="FONT-SIZE: 10pt"><FONT size=2>��ͬ��ţ�<%=info.getContractCode()%></FONT></SPAN></TD>
          <TD colSpan=1 height=23></TD>
                
          </TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD colSpan=10 height=2>
            <HR>
            <FONT size=2><U>�����ͬ��������</U></FONT></TD>
          <TD height=2 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=146 width=1>&nbsp;</TD>
          <TD colSpan=10 height=146>
              <TABLE cellPadding=0 cellSpacing=0 >
                <TBODY> 
                <TR> 
                  <TD height=31 ><FONT size=2>��λ��</FONT></TD>
                  <TD height=31 colspan=5><FONT size=2> 
                    <INPUT class=box  size=80 disabled name=strBorrowClientName value=<%=info.getLoanClientName()%>>
                    </FONT></TD>
                  <TD height=31 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=32 ><FONT size=2>����� ��</FONT></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dLoanAmount size=18 value=<%=DataFormat.formatListAmount(info.getExamineAmount())%>>
                  <input type="hidden" name="dMoney" value="<%=info.getUnPayAmount()%>">
                    </FONT></TD>
                  <TD height=32 ><font size="2">�ѷ��Ž� ��</font></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dAmountDone size=18 value=<%=(info.getOpenAmount()>0?DataFormat.formatListAmount(info.getOpenAmount()):"0.00")%>>
                    </FONT></TD>
                  <TD height=32 ><font size="2">δ���Ž� ��</font></TD>
                  <TD height=32 ><font size=2> 
                    <input class=tar 
                  disabled name=dAmountLeft size=18 value=<%=DataFormat.formatListAmount(info.getUnPayAmount())%>>
                    </font></TD>
                  <TD height=32 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=32 ><FONT size=2>�������ޣ�</FONT></TD>
                  <TD height=32><FONT size=2> 
                    <INPUT class=box 
                  disabled name=lInterValNum size=3 value=<%=info.getIntervalNum()%>>
                    ��</FONT></TD>
                  <TD height=32 ><FONT size=2>�������ʣ�</FONT></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dInterestRate size=10 value=<%=DataFormat.formatRate(info.getInterestRate())%>>
                    %</FONT></TD>
                  <TD height=32 ><font size="2">�ѻ���� ��</font></TD>
                  <TD height=32 ><FONT size=2> 
                    <INPUT class=tar 
                  disabled name=dAmountDone size=18 value=<%=(info.getRepayAmount()>0?DataFormat.formatListAmount(info.getRepayAmount()):"0.00")%>>
                    </FONT></TD>
                  <TD height=32 >&nbsp;</TD>
                </TR>
                <TR> 
                  <TD height=29 ><FONT size=2>�����;:</FONT></TD>
                  <TD height=29 colspan="5"><FONT size=2> 
                    <INPUT class=box 
                  disabled name=strLoanPurpose size=45 value=<%=DataFormat.formatString(info.getLoanPurpose())%>>
                    </FONT></TD>
                  <TD height=29 >&nbsp;</TD>
                </TR>
                </TBODY> 
              </TABLE>
            </TD>
          <TD height=146 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=24 width=1>&nbsp;</TD>
          <TD colSpan=10 height=24>
            <HR>
          </TD>
          <TD align=right height=24 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=30 width=1>&nbsp;</TD>
          <TD colSpan=10 height=30>
            <DIV align=center><B><FONT size=4>�ſ�֪ͨ��</FONT></B></DIV></TD>
          <TD align=right height=30 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=13 width=1>&nbsp;</TD>
          <TD colSpan=10 height=13>
            <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 
            height=341 >
              <TBODY>
              <TR>
                <TD height=411>

                    <TABLE border=0 height=136 width="98%">
                      <TBODY> 
                      <TR> 
                        <TD colSpan=2 height=36><font color=#FF0000>*</font> <FONT size=2>�ſ����ڣ�</FONT></TD>
                        <TD height=32 colSpan=2 > 
                <DIV align=left><FONT size=2>
                 <fs_c:calendar 
	         	    name="dtoutdate"
		          	value="" 
		          	properties="nextfield ='txtBA'" 
		          	size="20"/>
		        <script>
	          		$('#dtoutdate').val('<%=DataFormat.formatDate(info.getOutDate())%>');
	          	</script>
                 </FONT></DIV></TD>
                        <TD height=36>&nbsp;</TD>
                        <TD height=36 >&nbsp; 
                          
                         
                          
                          
                          
                        </TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>��λ���ƣ�</FONT></TD>
                        <TD height=36 colspan=4><FONT size=2> 
                          <INPUT class=box size=80 disabled
                        name="" value =<%=DataFormat.formatString(info.getLoanClientName())%>>
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>��λ�˺ţ�</FONT></TD>
                        <TD colSpan=2 height=36><FONT size=2> 
                          <INPUT class=box onfocus="nextfield ='';" disabled
                        name="sloanaccount" size=25 value=<%=DataFormat.formatString(info.getLoanAccount())%>>
                          </FONT> </TD>
                        <TD height=36></TD>
                        <TD height=36 width=240></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>��λ�ʱࣺ</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled name=LoanZipCode size=25 value="<%=DataFormat.formatString(info.getLoanZipCode())%>">
                          </FONT></TD>
                        <TD height=36><FONT size=2>��λ�绰��</FONT></TD>
                        <TD height=36 width=240><FONT size=2  > 
                          <INPUT class=box  disabled name=LoanPhone size=25 value="<%=DataFormat.formatString(info.getLoanPhone())%>">
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>��λ��ַ��</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled
                        name=strLoanAddress size=25 value="<%=DataFormat.formatString(info.getLoanAddress())%>">
                          </FONT></TD>
                        <TD height=36><font size="2">�������ࣺ</font></TD>
                        <TD height=36 width=240><FONT size=2> 
                          <INPUT class=box  disabled
                        name=strLoanTypeName size=25 value="<%=DataFormat.formatString(LOANConstant.LoanType.getName(info.getLoanTypeID()))%>">
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD height=36 colspan="2"><font size="2"><font color=#FF0000>*</font> �ſʽ��</font></TD>
                        <TD height=36 colSpan=2>&nbsp;</TD>
                        <TD height=36>&nbsp;</TD>
                        <TD height=36 width=240>&nbsp;</TD>
                      </TR>
                      <TR> 
                        <TD height=36 colspan="6"> 
                        <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 height="100%" width="100%" >
                        <tr><td>
                          <table align=left border=0 bordercolor=#999999 height="100%"  width="100%">
                            <tbody> 
                            <tr bordercolor=#D7DFE5> 
                              <td colspan=4 height=20 valign=middle> 
                               <%if( info.getGrantTypeID() == LOANConstant.TransType.Bank )
                                {%>
                                <input type="checkbox" name="bankpay" value="checkbox" checked >
                                <%}else{%>
                                <input type="checkbox" name="bankpay" value="checkbox">
                                <%}%>
                                <font size="2">���и���</font> </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
<%
                            String sMagnifierNameBank = URLEncoder.encode("��������");
                            String sFormNameBank = "frm";
                            String sPrefixBank = "";
                            String[] sMainNamesBank = {"txtBA","BankCode"};
                            String[] sMainFieldsBank = {"BranchName","BranchNo"};
                            String[] sReturnNamesBank = {"naccountbankid"};
                            String[] sReturnFieldsBank = {"BranchID"};
  							String sReturnInitValuesBank = info.getAccountBankName();
                            String[] sReturnValuesBank = {""+info.getAccountBankID()+""};
                            String[] sDisplayNamesBank = {URLEncoder.encode("���"),URLEncoder.encode("����")};
                            String[] sDisplayFieldsBank = {"BranchNo","BranchName"};
                            int nIndexBank = 0;
                            String sMainPropertyBank = "value='"+ info.getAccountBankName() + "'";
                            String strSQLBank = "getBranchSQL("+lOfficeID+","+lCurrencyID+",-1,-1,frm.txtBA.value)";
                            String strMatchValueBank = "BranchName";
                            String strNextControlsBank = "txtAccount";
                            String strTitleBank = "��������"; 
                            String strFirstTDBank = "";
                            String strSecondTDBank = "";
                            
                            OBMagnifier.showZoomCtrl(out
                            ,sMagnifierNameBank
                            ,sFormNameBank
                            ,sPrefixBank
                            ,sMainNamesBank
                            ,sMainFieldsBank
                            ,sReturnNamesBank
                            ,sReturnFieldsBank
                            ,sReturnInitValuesBank
                            ,sReturnValuesBank
                            ,sDisplayNamesBank
                            ,sDisplayFieldsBank
                            ,nIndexBank
                            ,sMainPropertyBank
                            ,strSQLBank
                            ,strMatchValueBank
                            ,strNextControlsBank
                            ,strTitleBank
                            ,strFirstTDBank
                            ,strSecondTDBank);
%>
                              
                              <td height=20 width="20%"><font size="2">�����˺ţ� 
                                </font></td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="BankCode" size=20 maxlength="100" value="<%=DataFormat.formatString(com.iss.itreasury.settlement.util.NameRef.getBankNoByID(info.getAccountBankID()))%>" disabled>
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font size="2">�տ���ϣ�</font></td>
                              <td height=20 width="30%">&nbsp;</td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
<%
                                 String sMagnifierNameAccount = URLEncoder.encode("�տ�˺�");
                            String sFormNameAccount = "frm";
                            String sPrefixAccount = "";
                            String[] sMainNamesAccount = {"txtAccount","sreceiveclientname","sremitinprovince","sremitincity","sremitbank"};
                            String[] sMainFieldsAccount = {"ExtAcctNo","ExtAcctName","ExtProvince","ExtCity","ExtBankName"};
                            String[] sReturnNamesAccount = {"sreceiveaccount"};
                            String[] sReturnFieldsAccount = {"ExtAcctNo"};
                            //String sReturnInitValuesAccount = (info.getReceiveClientCode()==null?"":""+info.getReceiveClientCode());
							String sReturnInitValuesAccount = info.getReceiveAccount();
                            String[] sReturnValuesAccount = {info.getReceiveAccount()==null?"-1":""+info.getReceiveAccount()};
                            String[] sDisplayNamesAccount = {URLEncoder.encode("�˺�"),URLEncoder.encode("����")};
                            String[] sDisplayFieldsAccount = {"ExtAcctNo","ExtAcctName"};
                            int nIndexAccount1 = 0;
                            String sMainPropertyAccount = " value='"+ info.getReceiveAccount() + "'";      
							String sSQLAccount = "getExtAcctSQL("+sessionMng.m_lOfficeID+",frm.txtAccount.value,"+sessionMng.m_lClientID+")";
                            String sMatchValueAccount = "ExtAcctNo";
                            String sNextControlsAccount = "sremitinprovince";
                            String sTitleAccount = "<font color=#FF0000>*</font> �տ�˺�";
                            String sFirstTDAccount = "";
                            String sSecondTDAccount = ""; 
                            
                            OBMagnifier.showZoomCtrl(out
                            ,sMagnifierNameAccount
                            ,sFormNameAccount
                            ,sPrefixAccount
                            ,sMainNamesAccount
                            ,sMainFieldsAccount
                            ,sReturnNamesAccount
                            ,sReturnFieldsAccount
                            ,sReturnInitValuesAccount
                            ,sReturnValuesAccount
                            ,sDisplayNamesAccount
                            ,sDisplayFieldsAccount
                            ,nIndexAccount1
                            ,sMainPropertyAccount
                            ,sSQLAccount
                            ,sMatchValueAccount
                            ,sNextControlsAccount
                            ,sTitleAccount
                            ,sFirstTDAccount
                            ,sSecondTDAccount);
%>                              
                              <td height=20 width="20%"><font size="2"><font color=#FF0000>*</font> �տ���ƣ� 
                                </font></td>
                              <td height=20 width="30%"> 
                                <p> 
                                  <input class=box name="sreceiveclientname" size=20 maxlength="100" value="<%=DataFormat.formatString(info.getReceiveClientName())%>">
                                </p>
                              </td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font size="2"><font color=#FF0000>*</font> ����أ�ʡ����</font></td>
                              <td height=20 width="30%"> <font size="2"> 
                                <input class=box name="sremitinprovince" size=20 maxlength="100" value="<%=DataFormat.formatString(info.getRemitinProvince())%>" onfocus="nextfield ='sremitincity';" >
                                </font></td>
                              <td height=20 width="20%"><font size="2"><font color=#FF0000>*</font> ����أ��У���</font></td>
                              <td height=20 width="30%"> <font size="2"> 
                                <input class=box name="sremitincity" size=20 maxlength="100" value="<%=DataFormat.formatString(info.getRemitinCity())%>" onfocus="nextfield ='sremitbank';" >
                                </font></td>
                            </tr>
                            <tr bordercolor=#D7DFE5 valign=middle> 
                              <td height=20 width="20%"><font size="2"><font color=#FF0000>*</font> ���������ƣ�</font></td>
                              <td height=20 width="30%"><font size="2"> 
                                <input class=box name="sremitbank" size=20 maxlength="100" value="<%=DataFormat.formatString(info.getRemitBank())%>" onfocus="nextfield ='mamount';" >
                                </font></td>
                              <td height=20 width="20%">&nbsp;</td>
                              <td height=20 width="30%">&nbsp;</td>
                            </tr>
                            </tbody> 
                          </table>
                          </td></tr></table>
                        </TD>
                      </TR>
                      <TR> 
                        <TD height=36 colspan="6"> 
                        <TABLE border=1 borderColor=#999999 cellPadding=0 cellSpacing=0 height="100%"  width="100%">
                        <tr><td>
                          <table align=left border=0 bordercolor=#999999 height="100%" 
            width="100%">
                            <tbody> 
                            <tr bordercolor=#D7DFE5> 
                              <td colspan=4 height=20 valign=middle> 
                              <%if( info.getGrantTypeID() == LOANConstant.TransType.CurrentAccount )
                                {%>
                                <input type="checkbox" name="currentaccount" value="checkbox" checked >
                                <%}else{%>
                                <input type="checkbox" name="currentaccount" value="checkbox">
                                <%}%>
                                <font size="2">�����˻�</font></td>
                            </tr>
                            
                            <tr bordercolor=#D7DFE5 valign=middle> 
<%
                                String strMagnifierNameAccount = URLEncoder.encode("�˺�");
                                String strFormNameAccount = "frm";
                                String strPrefixAccount = "";
                                int nCaseNumberAccount = 4;
                                String[] strMainNamesAccount = {"txtAccountCode"};
                                String[] strMainFieldsAccount = {"sAccountNo"};
                                String[] strReturnNamesAccount = {"ngrantcurrentaccountid"};
                                String[] strReturnFieldsAccount = {"id"};
                                 String[] strReturnValuesAccount = {""+info.getGrantCurrentAccountID()+""};
                                String[] strDisplayNamesAccount = {URLEncoder.encode("�˺�"),URLEncoder.encode("�ͻ�����")};
                                String[] strDisplayFieldsAccount = {"sAccountNo","sName"};
                                int nIndexAccount = 0;
                                String strMainPropertyAccount = "";
                                String strSQLAccount = "getAccountSQL("+lOfficeID+","+lCurrencyID+","+sessionMng.m_lClientID+",100,"+SETTConstant.AccountGroupType.CURRENT+")";
                                String strNextControlsAccount = "mamount";
                                String strAccountNoAccount = ""+DataFormat.formatString(info.getGrantCurrentAccount());
                                String strTitleAccount = "<font color=#FF0000>*</font> �˺�";
                                String strFirstTDAccount = "";
                                String strSecondTDAccount = "";

                                OBMagnifier.showSpecialZoomCtrl(out
                                ,sessionMng.m_lOfficeID
                                ,sessionMng.m_lCurrencyID
                                ,strMagnifierNameAccount
                                ,strFormNameAccount
                                ,strPrefixAccount
                                ,nCaseNumberAccount
                                ,strMainNamesAccount
                                ,strMainFieldsAccount
                                ,strReturnNamesAccount
                                ,strReturnFieldsAccount
                                ,strReturnValuesAccount
                                ,strDisplayNamesAccount
                                ,strDisplayFieldsAccount
                                ,nIndexAccount
                                ,strMainPropertyAccount
                                ,strSQLAccount
                                ,strNextControlsAccount
                                ,strAccountNoAccount
                                ,strTitleAccount
                                ,strFirstTDAccount
                                ,strSecondTDAccount);
%>

                            </tr>
                            
                            </tbody> 
                          </table>
                          </td></tr></table>
                        </TD>
                      </TR>
                      <TR> 
                              <TD height=36 width=92 ><font color=#FF0000>*</font> 
                                <FONT size=2>��׼�� </FONT></TD>
								<td width="13" align="right"><FONT size=2>��</FONT></td>
                        
                        <TD height=36 colSpan=2><FONT size=2> 
                          <script language="javascript">
                                                  createAmountCtrl("frm","mamount","<%=DataFormat.formatAmount(info.getAmount())%>","sconsignaccount","mAmountFomat","1");
                                          </script>
                          </FONT></TD>
                        <TD height=36><FONT size=2>&nbsp;</FONT> 
                          <DIV align=right><FONT size=2>(��д)</FONT></DIV>
                        </TD>
                        <TD height=36 width=240><FONT size=2>
                          <INPUT class=box disabled
                        name=mAmountFomat size=26  value="<%=ChineseCurrency.showChinese(DataFormat.formatAmount(info.getAmount()),sessionMng.m_lCurrencyID)%>">
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>ί�з����ƣ�</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled
                        name=strClientName size=25 value="<%=DataFormat.formatString(info.getConsignClientName())%>">
                          </FONT></TD>
                        <TD height=36><FONT size=2>ί�з��˺ţ�</FONT></TD>
                        <TD height=36 width=240><FONT size=2> 
                          <INPUT class=box 
                        name="sconsignaccount" size=25 value="<%=DataFormat.formatString(info.getConsignAccount())%>" onfocus="nextfield ='dtstart';">
                          </FONT></TD>
                      </TR>
                      <TR> 
                                          <td colspan="2"><font color=#FF0000>*</font> <FONT size=2>��׼����</FONT></td>
<%/*
                                        String sMagnifierNameRate = "";
                                        String sFormNameRate = "frm";
                                        String sPrefixRate = "";
                                        String[] sMainNamesRate = {"mRate"};
                                        String[] sMainFieldsRate = {"mRate"};
                                        String[] sReturnNamesRate = {"nbankinterestid"};
                                        String[] sReturnFieldsRate = {"RateID"};
                                        String sReturnInitValuesRate = ""+DataFormat.formatRate(info.getWTInterestRate())+""; 
                                        String[] sReturnValuesRate = {""+info.getBankInterestID()+""};
                                        String[] sDisplayNamesRate = {URLEncoder.encode("���ʱ��"),URLEncoder.encode("��������"),URLEncoder.encode("����(%)")};
                                        String[] sDisplayFieldsRate = {"RateCode","RateName","RateValue"};
                                        int nIndexRate = 0;
                                        String sMainPropertyRate = "";
                                        String strSQLRate = "getRateSQL("+lCurrencyID+")";
                                        String strMatchValueRate = "RateCode";
                                        String strNextControlsRate = "mcommissionrate";
                                        String strTitleRate = "";
                                        String strFirstTDRate = "";
                                        String strSecondTDRate = "";
                                        
                                        OBMagnifier.showZoomCtrl(out
                                        ,sMagnifierNameRate
                                        ,sFormNameRate
                                        ,sPrefixRate
                                        ,sMainNamesRate
                                        ,sMainFieldsRate
                                        ,sReturnNamesRate
                                        ,sReturnFieldsRate
                                        ,sReturnInitValuesRate
                                        ,sReturnValuesRate
                                        ,sDisplayNamesRate
                                        ,sDisplayFieldsRate
                                        ,nIndexRate
                                        ,sMainPropertyRate
                                        ,strSQLRate
                                        ,strMatchValueRate
                                        ,strNextControlsRate
                                        ,strTitleRate
                                        ,strFirstTDRate
                                        ,strSecondTDRate
                                        ,false
                                        ,true);*/
%>
							<TD height=33 width=97><FONT size=2> 
							<input type="hidden" name="nbankinterestid"  value="<%=info.getBankInterestID()%>">
							<input type="hidden" name="mRate"  value="<%=info.getWTInterestRate()%>">
                          <input type="text" name="mRate1" size="8" class="box" value="<%=DataFormat.formatRate(info.getWTInterestRate())%>"   disabled>
                          %</FONT>
						  </TD>

                        <TD width="214" nowrap><select class='box' name="select1" disabled>
                                  <option value=1 <%if(info.getAdjustRate()>=0) out.println("selected");%>>+</option>
                                  <option value=2 <%if(info.getAdjustRate()<0) out.println("selected");%>>-</option>
                                </select> <font size="2">����</font> 
                                <input type="text" name="textfield3222" size="8" class="box" value="<%=info.getAdjustRate()>=0?DataFormat.formatRate(info.getAdjustRate()):DataFormat.formatRate(-info.getAdjustRate())%>" disabled>
              			% </TD>
                              <TD height=33 width=96><font size="2">ִ������</font>��</TD>
                        <TD height=33 width=240><FONT size=2> 
						<input type="hidden" name="ExecuteValue"  value="<%=info.getInterestRate()%>">
                          <input type="text" name="ExecuteValue1" size="8" class="box" value="<%=DataFormat.formatRate(info.getInterestRate())%>"   disabled>
                          %</FONT></TD>
                      </TR>
                      <TR> 
                                          <TD colSpan=2 height=33><FONT size=2>�������ʣ�</FONT></TD>
                        <TD colSpan=2 height=33><FONT size=2>
						<INPUT  type="hidden" name="mcommissionrate"   value="<%=info.getCommissionRate()%>"> 
                          <INPUT class=tar name="mcommissionrate1"  disabled  size=16 onfocus="nextfield ='msuretyfeerate';setExecuteValue(<%=info.getAdjustRate()%>)"  value="<%=DataFormat.formatRate(info.getCommissionRate())%>">
                          % </FONT></TD>
                        <TD height=33 width=96><FONT size=2>�������ʣ�</FONT></TD>
                        <TD height=33 width=240><FONT size=2> 
						<INPUT  type="hidden" name="msuretyfeerate"   value="<%=info.getSuretyFeeRate()%>"> 
                          <INPUT class=tar  onfocus="nextfield ='dtstart';" name="msuretyfeerate1" size=16  disabled value="<%=DataFormat.formatRate(info.getSuretyFeeRate())%>" >
                          %</FONT></TD>
                      </TR>
                      <TR>
                        <TD height=33 width=92><FONT size=2>�������ڣ�</FONT></TD>
                        <TD height=33 >
                          <DIV align=right><FONT size=2>��</FONT></DIV>
                        </TD>
                        <TD height=33 colspan=2>
                                        <DIV align=left><FONT size=2>
                                        <fs_c:calendar 
							          	    name="dtstart"
								          	value="" 
								          	properties="nextfield='dtend';" 
								          	size="20"/>
				<script>
	          		$('#dtstart').val('<%=DataFormat.getDateString(info.getStart())%>');
	          	</script>
                                       	</FONT></DIV></TD>
                        <TD width=96> <FONT size=2>��</FONT></TD>
                        <TD> 
                                        <DIV align=left><FONT size=2>
                                        <fs_c:calendar 
							          	    name="dtend"
								          	value="" 
								          	properties="nextfield='SubmitFrm';" 
								          	size="20"/>
								        <script>
							          		$('#dtend').val('<%=DataFormat.getDateString(info.getEnd())%>');
							          	</script>
                                     	</FONT></DIV></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=24><FONT size=2>�������ޣ�</FONT></TD>
                        <TD colSpan=4 height=24><FONT size=2> 
                          <INPUT class=box  disabled
                                name=textfield24222 size=8  value=<%=info.getIntervalNum()%>>
                          ��</FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=24><FONT size=2>ԭ�����;��</FONT></TD>
                        <TD colSpan=4 height=24><FONT size=2> 
                          <TEXTAREA  disabled cols=70 name=LoanPurpose><%=DataFormat.formatString(info.getLoanPurpose())%></TEXTAREA>
                          </FONT></TD>
                      </TR>
                      <TR> 
                        <TD colSpan=2 height=36><FONT size=2>�����ͬ��ţ�</FONT></TD>
                        <TD height=36 colSpan=2><FONT size=2> 
                          <INPUT class=box  disabled
                                name=strContractCode size=25 value=<%=DataFormat.formatString(info.getContractCode())%>>
                          </FONT></TD>
                        <TD height=36>&nbsp;</TD>
                        <TD height=36 width=240>&nbsp;</TD>
                      </TR>
                      </TBODY> 
                    </TABLE>

                  </TD></TR></TBODY></TABLE>
            <HR>
          </TD>
          <TD align=right height=13 width=1><FONT size=2>&nbsp;</FONT></TD></TR>
        <TR>
          <TD height=2 width=1>&nbsp;</TD>
          <TD align=left  height=2><FONT 
          size=2><U>ִ�мƻ���ϸ</U></FONT></TD>
          <td align = right colSpan =2 height = 29  nowrap>
		<font size=2><%if (info.getStatusID()<1){info.setStatusID(1);}%>
		¼���ˣ�<%=sessionMng.m_strUserName%>&nbsp;&nbsp;&nbsp;&nbsp;
		¼��ʱ�䣺<%=DataFormat.getDateString(tsInput)%>&nbsp;&nbsp;&nbsp;&nbsp;
		״̬��<%=OBConstant.LoanInstrStatus.getName(info.getStatusID())%></font>
		</td>
          <TD align=right height=2 width=1>&nbsp;</TD></TR>
        <TR>
          <TD height=29 width=1>&nbsp;</TD>
          <TD align=left colSpan=1 height=29><FONT size=2>
          <INPUT class=button name=btnModifyExPlan onclick="Javascript: frmModifyExPlan('<%=lContractID%>');" type="button" value=" ִ�мƻ� ">
            </FONT></TD>
                        
          <TD align=right colSpan=2 height=29>
            <DIV align=left><FONT size=2></FONT></DIV><FONT size=2>&nbsp;</FONT>
            <DIV align=right><FONT size=2>
<%if( info.getLoanTypeID() == LOANConstant.LoanType.WT){%>
                        <INPUT class=button name=SubmitFrm onclick="clickSubmitModify(frm)" type=button value=����ſ�֪ͨ�� onKeydown="if(event.keyCode==13) clickSubmitModify(frm);">
                        <%}else{%>
                        <INPUT class=button name=SubmitFrm onclick="clickSubmit(frm)" type=button value=����������뵥 onKeydown="if(event.keyCode==13) clickSubmit(frm);">
                        <%}%>
                        <INPUT class=button name=back onclick="clickBack();" type=button value=" �� �� "> 

            </FONT></DIV></TD>
                        
          <TD align=right height=29 width=1>&nbsp;
		   <input type="hidden" name="strType">       
        <input type="hidden" name="strAction" value="<%=strAction%>">
        <input type="hidden" name="lContractID" value=<%=lContractID%>> 
        <input type="hidden" name="ngranttypeid" value="">      
        <input type="hidden" name="lPayID" value="<%=lPayID%>">      
 		<input type="hidden" name="txtAction" value="<%=txtAction%>">
		  </TD></TR>
  </TBODY>
  </TABLE>
</TD></TR>
  </TBODY>
  </TABLE>
</form>
   
<script language="javascript">
 
 ////setSubmitFunction("clickSubmit(frm)");
 setFormName("frm");        
</script>    


<script language="JavaScript">
		
		/**
		 * �ⲿ�˻��Ŵ�
		 * nOfficeID ���´�ID
		 * sExtAcctNo �ⲿ�˺�
		 */
		function getExtAcctSQL(nOfficeID,sExtAcctNo,nClientID)
		{      
			var sql = "select ID ExtAcctID,sPayeeacctno ExtAcctNo,sPayeename ExtAcctName,sPayeeprov ExtProvince,sPayeecity ExtCity,sPayeebankname ExtBankName ";
			sql += " from ob_payeeinfo where nIsCpfAcct=2";
			sql += "  and  ncurrencyid=" +<%=sessionMng.m_lCurrencyID%>;
			sql += "  and  nStatusid=" +<%=OBConstant.RecordStatus.VALID%>;
			
			if (nClientID > 0)
			{
				sql += " and nClientID = " + nClientID;
			}
			if (sExtAcctNo != null && sExtAcctNo.length > 0)
			{
				sql += " and sPayeeacctno like '%25" + sExtAcctNo + "%25'";
			}
			sql += " order by sPayeeacctno";
			
			return sql;
		}

        function clickBack()
        {	
		var act = "<%=txtAction%>";	
		if(act=="modify")
                {
				frm.action="../query/q003-c.jsp";	
				}else
				frm.action="../paynotice/p001-v.jsp";
                
		frm.submit();
        }
        
        function clickSubmit(frm)
        {
          	if(confirm("ȷ�ϱ��棿"))
			{	      
				if(!checkPage(frm))
                {
                        return false;
                }
				frm.strType.value="submit";
                frm.action="../paynotice/p004-c.jsp";
                frm.submit();   
			}
        }
        
        function clickSubmitModify(frm)
        {
            if(confirm("ȷ�ϱ��棿"))
			{	      
				if(!checkPageWT(frm))
                {
                        return false;
                }
                frm.strType.value="submit";
                frm.action="../paynotice/p004-c.jsp";
                frm.submit();   
			}
        }
        
        //���ҳ�������Ƿ�Ϲ淶
        function checkPage(frm) 
        {
                if(frm.bankpay.checked == false && frm.currentaccount.checked == false)
                {
                    alert('��ѡ��һ�ַſʽ');
                    return false;
                }
                
                if(frm.bankpay.checked == true && frm.currentaccount.checked == true)
                {
                    alert('ֻ��ѡ��һ�ַſʽ');
                    return false;
                }
                
                if(frm.bankpay.checked == true)
                {
                    frm.ngranttypeid.value = 1;

                   /* if (!checkMagnifier("frm","naccountbankid","txtBA","��������"))
                    {
                        return false;
                    }*/
                        
                    if (!checkString(frm.txtAccount,"�տ�˺�"))
                    {
                        return false;
                    }
					    
                    if (!checkString(frm.sreceiveclientname,"�տ����"))
                    {
                        return false;
                    }
                    
                    if (!checkString(frm.sremitinprovince,"����أ�ʡ��"))
                    {
                        return false;
                    } 
                     
                    if (!checkString(frm.sremitincity,"����أ��У�"))
                    {
                        return false;
                    } 
                    
                    if (!checkString(frm.sremitbank,"����������"))
                    {
                        return false;
                    }  

                }
                
                if(frm.currentaccount.checked == true)
                {
                    frm.ngranttypeid.value = 2;

                        if (!checkMagnifier("frm","ngrantcurrentaccountid","txtAccountCodeCtrl1","�˺�"))
                        {
                            return false;
                        }     

                }
				
				if(eval("frm.mamount.value.length>frm.dAmountLeft.value.length"))
				{
					alert("��׼���ܴ���δ���Ž�");
					eval("frm.mamount.focus()");
					return(false);
				}
				else if(eval("frm.mamount.value.length==frm.dAmountLeft.value.length")&&eval("frm.mamount.value>frm.dAmountLeft.value"))
				{
					alert("��׼���ܴ���δ���Ž�");
					eval("frm.mamount.focus()");
					return(false);
				}
                /* modify by zcwang 2007-3-23 ��׼����������ģ�鲻�����ӣ�������������޸�
                //��׼����
                if (!checkMagnifier("frm","nbankinterestid","mRate","��׼����"))
                {
                    return false;
                }
                */
                //�ſ�����
                if(!checkDate(frm.dtoutdate,1,"�ſ�����"))                      
                {
                    return false;
                }
                
                //��׼���
                if(!checkAmount(frm.mamount,1,"��׼���"))
                {
                    return false;
                }
                
                //�������� 
                if(!checkRate(frm.mcommissionrate,0,"��������"))
                {
                    return false;
                }
                
                //��������
                if(!checkRate(frm.msuretyfeerate,0,"��������"))
                {
                    return false;
                }
                
                //��������dtstart
                if(!checkDate(frm.dtstart,0,"��������"))                        
                {
                    return false;
                }
                
                //��������dtend
                if(!checkDate(frm.dtend,0,"��������"))                  
                {
                    return false;
                }
                
                return true; 
        }
        
        //���ҳ�������Ƿ�Ϲ淶
        function checkPageWT(frm) 
        {
                if(frm.bankpay.checked == false && frm.currentaccount.checked == false)
                {
                    alert('��ѡ��һ�ַſʽ');
                    return false;
                }
                
                if(frm.bankpay.checked == true && frm.currentaccount.checked == true)
                {
                    alert('ֻ��ѡ��һ�ַſʽ');
                    return false;
                }

                if(frm.bankpay.checked == true)
                {
                    frm.ngranttypeid.value = 1;

                   /* if (!checkMagnifier("frm","naccountbankid","txtBA","��������"))
                    {
                        return false;
                    }*/
                    
					if (!checkString(frm.txtAccount,"�տ�˺�"))
                    {
                        return false;
                    }
					    
                    if (!checkString(frm.sreceiveclientname,"�տ����"))
                    {
                        return false;
                    }
                    
                    if (!checkString(frm.sremitinprovince,"����أ�ʡ��"))
                    {
                        return false;
                    } 
                    
                    if (!checkString(frm.sremitincity,"����أ��У�"))
                    {
                        return false;
                    } 
                    
                    if (!checkString(frm.sremitbank,"����������"))
                    {
                        return false;
                    }  

                }
                
                if(frm.currentaccount.checked == true)
                {
                    frm.ngranttypeid.value = 2;

                        if (!checkMagnifier("frm","ngrantcurrentaccountid","txtAccountCodeCtrl1","�˺�"))
                        {
                            return false;
                        }     

                }    
				
				if(eval("frm.mamount.value.length>frm.dAmountLeft.value.length"))
				{
					alert("��׼���ܴ���δ���Ž�");
					eval("frm.mamount.focus()");
					return(false);
				}
				else if(eval("frm.mamount.value.length==frm.dAmountLeft.value.length")&&eval("frm.mamount.value>frm.dAmountLeft.value"))
				{
					alert("��׼���ܴ���δ���Ž�");
					eval("frm.mamount.focus()");
					return(false);
				}          

                if (!checkMagnifier("frm","mRate","mRate","��׼����"))
                {
                    return false;
                }

                //�ſ�����
                if(!checkDate(frm.dtoutdate,1,"�ſ�����"))                      
                {
                    return false;
                }
                
                //��׼���
                if(!checkAmount(frm.mamount,1,"��׼���"))
                {
                    return false;
                }
                
                //�������� 
                if(!checkRate(frm.mcommissionrate,0,"��������"))
                {
                    return false;
                }
                
                //��������
                if(!checkRate(frm.msuretyfeerate,0,"��������"))
                {
                    return false;
                }
                
                //��������dtstart
                if(!checkDate(frm.dtstart,0,"��������"))                        
                {
                    return false;
                }
                
                //��������dtend
                if(!checkDate(frm.dtend,0,"��������"))                  
                {
                    return false;
                }
                
                return true; 
        }
        
        function frmModifyExPlan(planID) //ִ�мƻ�  action on the Form : frmPage
	{
		var url = "../query/q094-v.jsp?control=view&isYU=2&nTmpID=<%=info.getPlanVersionID()%>&isSM=<%=Constant.YesOrNo.NO%>";
		window.open(url, "ִ�мƻ�", "width=800,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	}
 function setExecuteValue(floatRateValue)
{
	eval("document.frm.ExecuteValue.value = document.frm.mRate.value*(1 + floatRateValue*1/100)");
	eval("document.frm.ExecuteValue.value = Math.round(document.frm.ExecuteValue.value*1000000)/1000000");
}
</script>



<%
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>